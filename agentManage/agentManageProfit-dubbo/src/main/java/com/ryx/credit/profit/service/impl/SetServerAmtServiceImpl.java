package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.ServerAmtDetailMapper;
import com.ryx.credit.profit.dao.SetServerAmtMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.ISetServerAmtService;
import com.ryx.credit.profit.service.ProfitMonthService;
import com.ryx.credit.profit.service.TransProfitDetailService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 服务费相关业务处理
 * @author chenliang
 * 2019/5/10
 *
 */
@Service("setServerAmtService")
public class SetServerAmtServiceImpl implements ISetServerAmtService {
    private static Logger logger = LoggerFactory.getLogger(SetServerAmtServiceImpl.class);
    @Autowired
    SetServerAmtMapper setServerAmtMapper;
    @Autowired
    IdService idService;
    @Autowired
    TransProfitDetailService transProfitDetailService;
    @Autowired
    ServerAmtDetailMapper serverAmtDetailMapper;
    @Autowired
    ProfitMonthService profitMonthService;
    @Autowired
    private RedisService redisService;


    @Override
    public List<Map<String, Object>> queryBumCode() {
        return setServerAmtMapper.queryBumCode();
    }

    @Override
    public List<SetServerAmt> selectByExample(SetServerAmtExample example) {
        return setServerAmtMapper.selectByExample(example);
    }

    @Override
    public PageInfo setServerAmtList(Map<String, Object> param, PageInfo pageInfo) {
        Integer count = setServerAmtMapper.setServerAmtCount(param);
        List<SetServerAmt> list = setServerAmtMapper.setServerAmtList(param);
        pageInfo.setTotal(count);
        pageInfo.setRows(list);
        logger.info("查询============================================服务费设置" + JSONObject.toJSON(list));
        return pageInfo;
    }

    @Override
    public List<Map<String, Object>> queryD(String bumId) {
        return setServerAmtMapper.queryD(bumId);
    }
    @Override
    public int insertSelective(SetServerAmt record){
        if(record.getId()==null||"".equals(record.getId())){
            record.setId(idService.genId(TabId.P_SERVER_AMT));
        }
        return setServerAmtMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(SetServerAmt record) {
        return setServerAmtMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int clearServerAmtDetailData(String profitDate) {
        return setServerAmtMapper.clearServerAmtDetailData(profitDate);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public void calculateServerAmt(String profitDate) {
        //获取现在正在生效的服务费设置代理商
        logger.info("获取现在正在生效的服务费设置代理商{}"+profitDate);
        SetServerAmtExample setServerAmtExample = new SetServerAmtExample();
        SetServerAmtExample.Criteria criteria = setServerAmtExample.createCriteria();
        criteria.andStatusEqualTo("00");
        List<SetServerAmt> setServerAmts = setServerAmtMapper.selectByExample(setServerAmtExample);
        for (SetServerAmt setServerAmt:setServerAmts) {
            TransProfitDetail transProfitDetail = new TransProfitDetail();
            transProfitDetail.setAgentId(setServerAmt.getAgentId());
            transProfitDetail.setBusCode(setServerAmt.getBumId());
            transProfitDetail.setProfitDate(profitDate);
            List<TransProfitDetail> transProfitDetails = transProfitDetailService.getTransProfitDetailList(transProfitDetail);
            if(transProfitDetails.size()!=1){
                logger.info("获取平台月份润明细失败{}，{}"+setServerAmt.getAgentId()+ profitDate);
                throw new RuntimeException("获取平台月份润明细失败{}，{}"+setServerAmt.getAgentId()+ profitDate);
            }
            logger.info("获取总交易量");
            TransProfitDetail tpfd = transProfitDetails.get(0);
            BigDecimal amtSum =  setServerAmtMapper.serverAmtSum(profitDate,tpfd.getAgentId(),transProfitDetails.get(0).getBusCode());
            BigDecimal serverAmt = amtSum.multiply(setServerAmt.getChargeProportion());
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            ServerAmtDetail serverAmtDetail = new ServerAmtDetail();
            serverAmtDetail.setId(uuid);
            serverAmtDetail.setServerAmt(serverAmt);
            serverAmtDetail.setSumAmt(amtSum);
            serverAmtDetail.setPsaId(setServerAmt.getId());
            serverAmtDetail.setServerDate(profitDate);
            serverAmtDetailMapper.insertSelective(serverAmtDetail);
            //向月份润明细表中插入数据
            logger.info("向月份润明细表中插入数据");
            if(tpfd.getServerAmt()==null){
                tpfd.setServerAmt(BigDecimal.ZERO);
            }
            tpfd.setServerAmt(tpfd.getServerAmt().add(serverAmt));
            transProfitDetailService.updateByPrimaryKeySelective(tpfd);

            List<ProfitDetailMonth>  profitDetailMonths= profitMonthService.getAgentProfit(tpfd.getAgentId(),profitDate,tpfd.getParentAgentId());
            if(transProfitDetails.size()!=1){
                logger.info("获取月份润汇总失败{}，{}"+tpfd.getAgentId()+ profitDate);
                throw new RuntimeException("获取月份润汇总失败{}，{}"+tpfd.getAgentId()+ profitDate);
            }
            ProfitDetailMonth profitDetailMonth = profitDetailMonths.get(0);
            //向月份润汇总表表中插入数据
            logger.info("向月份润汇总表中插入数据");
            if(profitDetailMonth.getServerAmtSum()==null){
                profitDetailMonth.setServerAmtSum(BigDecimal.ZERO);
            }
            profitDetailMonth.setServerAmtSum(profitDetailMonth.getServerAmtSum().add(serverAmt));
            profitDetailMonth.setRealProfitAmt(profitDetailMonth.getRealProfitAmt().subtract(serverAmt));
            profitMonthService.updateProfitMonth(profitDetailMonth);


            //判断此条记录是否失效
            if("1".equals(redisService.getValue("commitFinal"))){
                String chargePeriod = setServerAmt.getChargePeriod();
                if ("长期".equals(chargePeriod)){
                    return;
                }
                String[] strs = chargePeriod.split("-");
                if(Integer.parseInt(profitDate)>=Integer.parseInt(strs[1])){
                    setServerAmt.setStatus("99");
                    setServerAmtMapper.updateByPrimaryKeySelective(setServerAmt);
                }
            }
    }
 }

    @Override
    public PageInfo queryDataDetail(Map<String, Object> param,PageInfo pageInfo) {

        Integer count = setServerAmtMapper.queryDataDetailCount(param);
        List<Map<String, Object>>  list = setServerAmtMapper.queryDataDetail(param);
        pageInfo.setTotal(count);
        pageInfo.setRows(list);
        logger.info("查询============================================服务费面明细" + JSONObject.toJSON(list));
        return pageInfo;

    }

}
