package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.PProfitFactorMapper;
import com.ryx.credit.profit.pojo.PProfitFactor;
import com.ryx.credit.profit.pojo.PProfitFactorExample;
import com.ryx.credit.profit.service.ProfitFactorService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/08/02
 * 分润管理：商业保理
 */
@Service("profitFactorService")
public class ProfitFactorServiceImpl implements ProfitFactorService{

    private static Logger logger = LoggerFactory.getLogger(ProfitFactorServiceImpl.class);

    @Autowired
    private PProfitFactorMapper pProfitFactorMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private RedisService redisService;

    /**
     * 商业保理:
     * 1、列表查询
     */
    @Override
    public PageInfo getProfitFactorList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = pProfitFactorMapper.getProfitFactorCount(param);
        List<Map<String, Object>> list = pProfitFactorMapper.getProfitFactorList(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
//        System.out.println("查询============================================" + JSONObject.toJSON(list));
        return pageInfo;
    }

    @Override
    public PProfitFactor selectByData(PProfitFactor profitFactor) {
        return pProfitFactorMapper.selectByData(profitFactor);
    }

    /**
     * 清除本月导入数据
     */
    @Override
    public int resetDataFactor() {
        //终审后数据不能清除
        String finalStatus = redisService.getValue("commitFinal");
        if (StringUtils.isBlank(finalStatus)) {
            if("1".equals(finalStatus)){
                logger.info("终审状态不能清除！");
                throw new ProcessException("终审状态不能清除！");
            }
        }
        return pProfitFactorMapper.resetDataFactor();
    }

    @Override
    public Map<String, Object> profitCount(Map<String, Object> param) {
        return pProfitFactorMapper.profitCount(param);
    }

    @Override
    public int insertImportData(PProfitFactor pProfitFactor) {
        return pProfitFactorMapper.insertSelective(pProfitFactor);
    }

    /**
     * 商业保理：
     * 1、导入保理数据
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public List<String> addList(List<List<String>> data, String userId) throws Exception {
        List<String> list = new ArrayList<>();
        if (null == data && data.size() == 0) {
            logger.info("导入数据为空");
            throw new MessageException("导入数据为空");
        }
        for (List<String> factor : data) {
            PProfitFactor profitFactor = new PProfitFactor();
            profitFactor.setFactorDate(Calendar.getInstance().getTime());//导入时间
            profitFactor.setId(idService.genId(TabId.p_profit_factor));//ID序列号
            try {
                profitFactor.setFactorMonth(null!=factor.get(0)?String.valueOf(factor.get(0)).substring(0,6):"");//月份
                profitFactor.setAgentPid(null!=factor.get(1)?String.valueOf(factor.get(1)):"");//代理商唯一码(因业务中无pid，现将pid取值更改为AG码)
                profitFactor.setAgentName(null!=factor.get(2)?String.valueOf(factor.get(2)):"");//代理商名称
                profitFactor.setAgentId(null!=factor.get(1)?String.valueOf(factor.get(1)):"");//代理商编号
                profitFactor.setParentAgentId(null!=factor.get(3)?String.valueOf(factor.get(3)):"");//上级代理商编号
                profitFactor.setParentAgentName(null!=factor.get(4)?String.valueOf(factor.get(4)):"");//上级代理商名称
                profitFactor.setTatolAmt(new BigDecimal(String.valueOf(factor.get(5))));//应还款
                profitFactor.setBuckleAmt(new BigDecimal(String.valueOf(factor.get(6))));//已扣款
                profitFactor.setSurplusAmt(new BigDecimal(String.valueOf(factor.get(7))));//未扣足
                profitFactor.setRemark(null!=factor.get(8)?String.valueOf(factor.get(8)):"");//备注
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            PProfitFactor profit = selectByData(profitFactor);//查询列表中是否有重复数据
            if (profit != null) {
                logger.info(profitFactor.getAgentId() + "此条数据已存在！");
                throw new MessageException(profitFactor.getAgentId() + "此条数据已存在！");
            } else {
                if (insertImportData(profitFactor)==0) {
                    logger.info("导入失败！");
                    throw new MessageException(factor.toString() + "导入失败！");
                }
                logger.info("保理数据信息：" + JSONObject.toJSON(profitFactor));
            }
            list.add(profitFactor.getId());
        }
        return list;
    }

}
