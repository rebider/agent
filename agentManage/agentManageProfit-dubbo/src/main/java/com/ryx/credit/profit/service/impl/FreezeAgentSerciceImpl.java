package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.FreezeAgentMapper;
import com.ryx.credit.profit.dao.FreezeOperationRecordMapper;
import com.ryx.credit.profit.pojo.FreezeAgent;
import com.ryx.credit.profit.pojo.FreezeAgentExample;
import com.ryx.credit.profit.pojo.FreezeOperationRecord;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.service.IFreezeAgentSercice;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * chenliang
 * 2019/4/22
 */
@Service("freezeAgentSercice")
public class FreezeAgentSerciceImpl implements IFreezeAgentSercice {
    Logger logger = LoggerFactory.getLogger(FreezeAgentSerciceImpl.class);
    @Autowired
    FreezeAgentMapper freezeAgentMapper;
    @Autowired
    ProfitDetailMonthService profitDetailMonthService;
    @Autowired
    private IdService idService;
    @Autowired
    FreezeOperationRecordMapper freezeOperationRecordMapper;


    @Override
    public PageInfo getselectFreezeDate(Map<String, Object> param, PageInfo pageInfo){
       Integer count = 0;
        List<FreezeAgent> listAll;
        if ("true".equals(param.get("isQuerySubordinate"))){
            logger.info("下级查询");
            listAll = freezeAgentMapper.selectAllNotFreezeLower(param);
            count = freezeAgentMapper.selectAllNotFreezeLowerCount(param);
        }else{
            logger.info("不含下级查询");
            listAll = freezeAgentMapper.selectAllNotFreeze(param);
            count = freezeAgentMapper.selectAllNotFreezeCount(param);
        }

        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(listAll);
        logger.info("查询============================================" + JSONObject.toJSON(listAll));
        return pageInfo;

  }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public void operationFreezeDate(List<FreezeOperationRecord> freezeOperationRecords,String user){
       if(freezeOperationRecords!=null){
           for (FreezeOperationRecord freezeOperationRecord:freezeOperationRecords) {
               String uuid = UUID.randomUUID().toString().replaceAll("-", "");
               //更新月份润状态
               ProfitDetailMonth profitDetailMonth1;
               if("00".equals(freezeOperationRecord.getFreezeType())){
                   try{
                       ProfitDetailMonth profitDetailMonth = new ProfitDetailMonth();
                       profitDetailMonth.setAgentId(freezeOperationRecord.getAgentId());
                       profitDetailMonth.setParentAgentId(freezeOperationRecord.getParentAgentId());
                       profitDetailMonth.setParentAgentId(LocalDate.now().plusMonths(-1).toString().substring(0, 7).replaceAll("-", ""));
                       profitDetailMonth1 =  profitDetailMonthService.selectByPIdAndMonth(profitDetailMonth);
                       profitDetailMonth1.setStatus("1");
                       profitDetailMonthService.updateByPrimaryKeySelective(profitDetailMonth1);
                       freezeOperationRecord.setFreezeAmt(profitDetailMonth1.getBasicsProfitAmt());
                   }catch (Exception e){
                       e.printStackTrace();
                       logger.info("更新月份润冻结状态失败");
                       throw new RuntimeException("更新月份润冻结状态失败");
                   }


               }

               //插入操作表新的数据
               try{

                   Date date = new Date();
                   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   freezeOperationRecord.setOperationTime(sdf.format(date));
                   freezeOperationRecord.setFreezeBatch(uuid);
                   freezeOperationRecord.setId(idService.genId(TabId.P_FREEZE_OPERATION_RECORD));
                   freezeOperationRecord.setStatus("0");
                   freezeOperationRecordMapper.insertSelective(freezeOperationRecord);
               }catch (Exception e){
                   e.printStackTrace();
                   logger.info("插入操作明细失败");
                   throw new RuntimeException("插入操作明细失败");
               }

               //更新或者插入冻结解冻表
               try{
                   FreezeAgentExample freezeAgentExample = new FreezeAgentExample();
                   FreezeAgentExample.Criteria criteria = freezeAgentExample.createCriteria();
                   FreezeAgent freezeAgent = new FreezeAgent();
                   if(StringUtils.isNotBlank(freezeOperationRecord.getAgentId())){
                       criteria.andAgentIdEqualTo(freezeOperationRecord.getAgentId());
                       freezeAgent.setAgentId(freezeOperationRecord.getAgentId());
                   }
                   if(StringUtils.isNotBlank(freezeOperationRecord.getAgentName())){
                       criteria.andAgentNameEqualTo(freezeOperationRecord.getAgentName());
                       freezeAgent.setAgentName(freezeOperationRecord.getAgentName());
                   }
                   if(StringUtils.isNotBlank(freezeOperationRecord.getParentAgentId())){
                       criteria.andParentAgentIdEqualTo(freezeOperationRecord.getParentAgentId());
                       freezeAgent.setParentAgentId(freezeOperationRecord.getParentAgentId());
                   }
                   if(StringUtils.isNotBlank(freezeOperationRecord.getParentAgentName())){
                       criteria.andParentAgentNameEqualTo(freezeOperationRecord.getParentAgentName());
                       freezeAgent.setParentAgentName(freezeOperationRecord.getParentAgentName());
                   }
                   if(StringUtils.isNotBlank(freezeOperationRecord.getFreezeType())){
                       criteria.andFreezeTypeEqualTo(freezeOperationRecord.getFreezeType());
                       freezeAgent.setFreezeType(freezeOperationRecord.getFreezeType());
                   }
                   List<FreezeAgent> freezeAgents=  freezeAgentMapper.selectByExample(freezeAgentExample);
                   if(freezeAgents.size() ==1 ){
                       if(freezeAgents.get(0).getStatus()=="0"){
                           freezeAgents.get(0).setStatus("1");
                           freezeAgents.get(0).setOperationBatch(uuid);
                           freezeAgentMapper.updateByPrimaryKeySelective(freezeAgents.get(0));
                       }

                   }else if(freezeAgents.size()>1){
                       logger.info("查询冻结解冻数据时失败");
                       throw new RuntimeException("查询冻结解冻数据时失败");
                   }else{
                       freezeAgent.setId((idService.genId(TabId.P_FREEZE_AGENT)));
                       freezeAgent.setOperationBatch(uuid);
                       freezeAgentMapper.insertSelective(freezeAgent);
                   }



               }catch (Exception e){
                   e.printStackTrace();

               }
           }
       }else{
           logger.info("未选择代理商" );
           throw new RuntimeException("未选择代理商");
       }

    }





    @Override
    public PageInfo getFreezeData(FreezeAgent freezeAgent, String isQuerySubordinate, Page page, String orgId) {
        List<FreezeAgent> listAll;
        long count;
        if ("false".equals(isQuerySubordinate)){
            FreezeAgentExample example=new FreezeAgentExample();
            FreezeAgentExample.Criteria criteria=example.createCriteria();
            criteria.andStatusEqualTo("1");
            if(StringUtils.isNotBlank(freezeAgent.getAgentId())){
                criteria.andAgentIdEqualTo(freezeAgent.getAgentId());
            }
            if(StringUtils.isNotBlank(freezeAgent.getAgentName())){
                criteria.andAgentNameEqualTo(freezeAgent.getAgentName());
            }
            if(StringUtils.isNotBlank(freezeAgent.getParentAgentId())){
                criteria.andParentAgentIdEqualTo(freezeAgent.getParentAgentId());
            }
            if(StringUtils.isNotBlank(freezeAgent.getParentAgentName())){
                criteria.andParentAgentNameEqualTo(freezeAgent.getParentAgentName());
            }
            example.setPage(page);

            listAll=freezeAgentMapper.selectByExampleWithCity(freezeAgent,page,orgId);
            count = freezeAgentMapper.countByExampleWithCity(freezeAgent,orgId);

        }else{
            listAll=freezeAgentMapper.selectAllFreezeWithSubordinate(freezeAgent,page,orgId);
            count=freezeAgentMapper.countAllFreezeWithSubordinate(freezeAgent,orgId);
        }
        PageInfo pageInfo=new PageInfo();
        pageInfo.setRows(listAll);
        pageInfo.setTotal((int) count);
        return pageInfo;
    }
}
