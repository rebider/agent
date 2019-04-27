package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.FreezeAgentMapper;
import com.ryx.credit.profit.dao.FreezeOperationRecordMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IFreezeAgentSercice;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.TaskApprovalService;
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
import java.util.*;

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
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private AgentEnterService agentEnterService;

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
    public PageInfo getselectDetailFreezeDate(Map<String, Object> param, PageInfo pageInfo){
        Integer count = 0;
        List<Map<String,Object>> listAll;
        if ("true".equals(param.get("isNo"))){
            logger.info("下级查询");
           listAll = freezeAgentMapper.freezeDetailLower(param);
           count = freezeAgentMapper.freezeDetailLowerCount(param);
        }else{
            logger.info("不含下级查询");
            listAll = freezeAgentMapper.freezeDetail(param);
            count = freezeAgentMapper.freezeDetailCount(param);
        }
        pageInfo.setTotal(count);
        pageInfo.setRows(listAll);
        logger.info("查询============================================" + JSONObject.toJSON(listAll));
        return pageInfo;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public void operationFreezeDate(List<FreezeOperationRecord> freezeOperationRecords,String user){
       if(freezeOperationRecords!=null){
           String uuid = UUID.randomUUID().toString().replaceAll("-", "");
           for (FreezeOperationRecord freezeOperationRecord:freezeOperationRecords) {
               //更新月份润状态
               ProfitDetailMonth profitDetailMonth1;
               if("00".equals(freezeOperationRecord.getFreezeType())){
                   try{
                       ProfitDetailMonth profitDetailMonth = new ProfitDetailMonth();
                       profitDetailMonth.setAgentId(freezeOperationRecord.getAgentId());
                       profitDetailMonth.setParentAgentId(freezeOperationRecord.getParentAgentId());
                       profitDetailMonth.setProfitDate(LocalDate.now().plusMonths(-1).toString().substring(0, 7).replaceAll("-", ""));
                       profitDetailMonth1 =  profitDetailMonthService.selectByIdAndParent(profitDetailMonth);
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
                   freezeOperationRecord.setFreezeOperator(user);
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
                       freezeAgent.setStatus("1");
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


    /**
     * 解冻审批流发起
     * @param records
     * @param userId
     * @param batch
     * @param workId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public boolean applyThawAgent(List<FreezeOperationRecord> records,String userId,String batch,String workId) {
        List<FreezeAgent> freezeAgents=new ArrayList<>();
        for (FreezeOperationRecord record:records){

            FreezeAgent freezeAgent=new FreezeAgent();
            freezeAgent.setAgentId(record.getAgentId());
            freezeAgent.setParentAgentId(record.getParentAgentId());
            freezeAgent.setFreezeType(record.getFreezeType());
            freezeAgent.setRev1(batch);//解冻批次号

            freezeAgents.add(freezeAgent);
        }
        try {
            //修改两张表的数据
            updateThawAgentData(records,freezeAgents);
        }catch (Exception e){
            e.getStackTrace();
            logger.error("分润解冻审批流启动失败=========数据保存异常");
            throw new ProcessException("分润解冻数据保存异常!:{}",e.getMessage());
        }

        Map<String,Object> map=agentEnterService.startPar(userId);
        Map<String,Object> param=new HashMap<String,Object>();
        if(map!=null&&map.get("party")!=null){
            param.put("dept",map.get("party"));
        }

        String proceId = activityService.createDeloyFlow(null, workId, null, null, param);
        if (proceId == null) {
            logger.error("代理商分润解冻审批流启动失败！");
            throw new ProcessException("代理商分润解冻审批流启动失败!");
        }
        BusActRel record = new BusActRel();
        record.setBusId(batch);
        record.setActivId(proceId);
        record.setAgentId(records.get(0).getAgentId());
        record.setAgentName(records.get(0).getAgentName());
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);

        if ("thawAgentByCity".equals(workId)){
            record.setBusType(BusActRelBusType.thawAgentByCity.name());
            record.setDataShiro(BusActRelBusType.thawAgentByCity.key);
        }else {
            record.setBusType(BusActRelBusType.thawAgentByBusiness.name());
            record.setDataShiro(BusActRelBusType.thawAgentByBusiness.key);
        }
        try {
            taskApprovalService.addABusActRel(record);
            logger.info("分润解冻审批流启动成功");
        } catch (Exception e) {
            e.getStackTrace();
            logger.error("分润解冻审批流启动失败");
            throw new ProcessException("分润解冻审批流启动失败!:{}",e.getMessage());
        }

        return true;
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

    /**
     * 获取月分润冻结数据
     * @return
     */
    @Override
    public List<FreezeAgent> getFreezeList(){
        FreezeAgentExample example = new FreezeAgentExample();
        FreezeAgentExample.Criteria criteria = example.createCriteria();
        criteria.andFreezeTypeEqualTo("00");
        return freezeAgentMapper.selectByExample(example);
    }

    /**
     * 监听回调方法
     * @param activId
     * @param result
     */
    @Override
    public void completeTaskEnterActivity(String activId, String result) {
        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(activId);
        try {
            BusActRel rel =  taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                if("1".equals(result)){
                    rel.setActivStatus(AgStatus.Approved.name());
                }else if ("2".equals(result)){
                    rel.setActivStatus(AgStatus.Refuse.name());
                }
                updateThawAgentByBatch(rel.getBusId(),result);
                logger.info("更新审批流与业务对象");
                taskApprovalService.updateABusActRel(rel);
                logger.info("解冻审批流完成========================batch:"+rel.getBusId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("代理商分润解冻审批流回调异常，activId：{}" + activId);
        }
    }

    /**
     * 审批流结束修改、添加两个表的数据
     * @param batch
     * @param result
     */
    private void updateThawAgentByBatch(String batch, String result) {
        String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if ("1".equals(result)){//解冻成功
            freezeAgentMapper.updateThawAgentByBatch(batch,"0");
            logger.info("解冻状态更新成功==========batch:"+batch);
            FreezeOperationRecordExample example=new FreezeOperationRecordExample();
            FreezeOperationRecordExample.Criteria criteria=example.createCriteria();
            criteria.andThawBatchEqualTo(batch);
            List<FreezeOperationRecord> freezeOperationRecords = freezeOperationRecordMapper.selectByExample(example);//解冻申请中的数据
            for (FreezeOperationRecord thawRecord : freezeOperationRecords) {
                thawRecord.setId(idService.genId(TabId.P_FREEZE_OPERATION_RECORD));//设置新的id
                thawRecord.setStatus("1");//状态设置为解冻
                thawRecord.setOperationTime(time);//解冻成功时间
                freezeOperationRecordMapper.insert(thawRecord);
            }

        }else{
            freezeAgentMapper.updateThawAgentByBatch(batch,"1");
            logger.info("解冻状态更新成功==========batch:"+batch);
            FreezeOperationRecordExample example=new FreezeOperationRecordExample();
            FreezeOperationRecordExample.Criteria criteria=example.createCriteria();
            criteria.andThawBatchEqualTo(batch);
            List<FreezeOperationRecord> freezeOperationRecords = freezeOperationRecordMapper.selectByExample(example);//解冻申请中的数据
            for (FreezeOperationRecord thawRecord : freezeOperationRecords) {
                thawRecord.setId(idService.genId(TabId.P_FREEZE_OPERATION_RECORD));//设置新的id
                thawRecord.setStatus("0");//状态设置为冻结
                thawRecord.setOperationTime(time);//设置最新的冻结时间
                thawRecord.setThawBatch(null);
                thawRecord.setThawOperator(null);
                thawRecord.setThawTime(null);
                thawRecord.setThawReason(null);
                freezeOperationRecordMapper.insert(thawRecord);
            }
        }
    }

    /**
     * 解冻申请发起时修改两个表的数据
     * @param records
     * @param freezeAgents
     * @return
     * @throws Exception
     */
    @Override
    public int updateThawAgentData(List<FreezeOperationRecord> records, List<FreezeAgent> freezeAgents) throws Exception {

        for (FreezeAgent freezeAgent :freezeAgents){
            FreezeAgentExample example=new FreezeAgentExample();
            FreezeAgentExample.Criteria criteria=example.createCriteria();
            criteria.andStatusEqualTo("1");//查询已冻结
            criteria.andAgentIdEqualTo(freezeAgent.getAgentId());
            criteria.andFreezeTypeEqualTo(freezeAgent.getFreezeType());
            if(StringUtils.isNotBlank(freezeAgent.getParentAgentId())){
                criteria.andParentAgentIdEqualTo(freezeAgent.getParentAgentId());
            }
            List<FreezeAgent> list = freezeAgentMapper.selectByExample(example);
            if(list.size()!=1){
                throw new Exception("数据查询异常！");
            }
            FreezeAgent thawAgent = list.get(0);
            thawAgent.setStatus("2");//设置状态：解冻审批中
            thawAgent.setRev1(freezeAgent.getRev1());
            freezeAgentMapper.updateByPrimaryKeySelective(thawAgent);
        }
        for (FreezeOperationRecord record:records){


            FreezeOperationRecordExample freezeExample=new FreezeOperationRecordExample();
            FreezeOperationRecordExample.Criteria freezeCriteria=freezeExample.createCriteria();
            freezeCriteria.andAgentIdEqualTo(record.getAgentId());
            freezeCriteria.andParentAgentIdEqualTo(record.getParentAgentId());
            freezeCriteria.andFreezeTypeEqualTo(record.getFreezeType());
            freezeCriteria.andStatusEqualTo("0");//查询已冻结
            List<FreezeOperationRecord> list = freezeOperationRecordMapper.selectByExample(freezeExample);
            if(list.size()!=1){
                throw new Exception("数据查询异常！");
            }
            FreezeOperationRecord freezeRecord=list.get(0);
            freezeRecord.setThawTime(record.getThawTime());
            freezeRecord.setThawOperator(record.getThawOperator());
            freezeRecord.setThawBatch(record.getThawBatch());
            freezeRecord.setThawReason(record.getThawReason());
            freezeRecord.setStatus("2");
            freezeOperationRecordMapper.updateByPrimaryKeySelective(freezeRecord);
        }

        return 0;
    }

    @Override
    public List<FreezeAgent> getThawDataByBatch(String batch) {
        logger.info("根据解冻批次号查询解冻代理商及冻结金额,批次号:{}",batch);
        List<FreezeAgent> thawData = freezeAgentMapper.getThawDataByBatch(batch);//根据解冻批次号查询解冻代理商及冻结金额
        return thawData;
    }

    @Override
    public Map<String, Object> getThawOperator(String batch) {
        logger.info("根据解冻批次号查询解冻代理商及冻结金额,批次号:{}",batch);
        Map<String, Object> thawOperator = freezeAgentMapper.getThawOperator(batch);//根据解冻批次号查询解冻代理商及冻结金额
        return thawOperator;
    }

    /**
     * 处理任务
     * @param agentVo
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalThawTask(AgentVo agentVo, String userId) throws Exception {
        logger.info("审批对象：{}", JSONObject.toJSON(agentVo));
        AgentResult result = new AgentResult(500, "系统异常", "");
        Map<String, Object> reqMap = new HashMap<>();
        if(StringUtils.isNotBlank(agentVo.getDept())){//其他用户无法取到dept
            String dept = agentVo.getDept();//此字符串为省区Id
            Map<String,Object> map=agentEnterService.startPar(dept);
            if(map!=null&&map.get("party")!=null){
                reqMap.put("dept", map.get("party"));
            }
        }else if(Objects.equals("pass",agentVo.getApprovalResult())
                && StringUtils.isBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", "finish");
        }
        reqMap.put("rs", agentVo.getApprovalResult());
        reqMap.put("approvalOpinion", agentVo.getApprovalOpinion());
        reqMap.put("approvalPerson", userId);
        reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
        reqMap.put("taskId", agentVo.getTaskId());

        logger.info("创建下一审批流对象：{}", reqMap.toString());
        Map resultMap = activityService.completeTask(agentVo.getTaskId(), reqMap);
        Boolean rs = (Boolean) resultMap.get("rs");
        String msg = String.valueOf(resultMap.get("msg"));
        if (resultMap == null) {
            return result;
        }
        if (!rs) {
            result.setMsg(msg);
            return result;
        }
        return AgentResult.ok(resultMap);
    }

    /**
     * 解冻发起人修改申请信息
     * @param list
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public void delFreezeAgentById(List<String> list) {
        for (String freezeAgentId : list) {
            //1. 将操作表中的最新的一条操作还原：(状态、thaw相关)
            // 根据freezeAgentId 查到其agentId、parentAgentId、freezeType、rev1(解冻批次号)
            freezeOperationRecordMapper.updateByFreezeAgentId(freezeAgentId);
            freezeAgentMapper.delteThawOperationById(freezeAgentId);
            logger.info("更新成功 freezeAgentID:"+freezeAgentId);
        }
    }
}
