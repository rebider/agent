package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.FreezeAgentMapper;
import com.ryx.credit.profit.dao.FreezeOperationRecordMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IFreezeAgentSercice;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.profit.unitmain.FreezeDayJob;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
import org.apache.commons.collections4.CollectionUtils;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    FreezeDayJob freezeDayJob;

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
        logger.info("================================日分润日冻结开始==========================================");
       if(freezeOperationRecords!=null){
           logger.info("得到月份润冻结集合");
           //得到月份润冻结集合
           List<FreezeOperationRecord> freezeMonth = freezeOperationRecords.stream().filter(f ->"00".equals(f.getFreezeType())).collect(Collectors.toList());
           logger.info("得到日分润冻结集合");
           //得到日分润冻结集合
           Collection dd = CollectionUtils.disjunction(freezeOperationRecords,freezeMonth);
           List<FreezeOperationRecord> freezeDay = new ArrayList<>(dd);

           HashMap<String, Object> map = new HashMap();
           map.put("agencyBlack_type","1");
           map.put("type","2");
           map.put("flag","4");
           logger.info("得到代理商唯一码集合");
           List<String> freezeAgentId = freezeDay.stream().map((freeze)->freeze.getAgentId()).collect(Collectors.toList());
           //将list放入set中对其去重
           Set<String> set = new HashSet<>(freezeAgentId);
           //计算AgentId的差集，此集合为日分润月份润全部冻结；
           Collection rs = CollectionUtils.disjunction(freezeAgentId,set);
           //将collection转换为list  此集合为双冻结类型(日分润和日返现）
           List<String> listBoth = new ArrayList<>(rs);
           //双冻结
           logger.info("得到日分润和日返现全部冻结的集合，并请求远程接口");
           List<String> listBothBus = new ArrayList<>();
           if(listBoth.size()!=0){
              /* for (String str:listBoth) {
                   List<Map<String,Object>> mapList1 = freezeAgentMapper.queryBumId(str);
                   for (Map m:mapList1) {
                       if("5000".equals(m.get("BUS_PLATFORM").toString()) || "6000".equals(m.get("BUS_PLATFORM").toString())){
                           listBothBus.add(m.get("BUS_NUM").toString());
                       }
                   }


               }*/
               map.put("unfreeze","0");
               map.put("batchIds",JsonUtil.objectToJson(listBoth));
               String params1 = JsonUtil.objectToJson(map);
               logger.info("请求报文:"+params1);
               String res = HttpClientUtil.doPostJson(AppConfig.getProperty("busiPlat.refuse"), params1);
               logger.info("调用接口返回数据为:"+res);

               if (!JSONObject.parseObject(res).get("respCode").equals("000000")) {
                   logger.info("双冻结失败");
                  /* throw new RuntimeException("冻结失败");*/
               }

           }

           //得到单冻结集合
           logger.info("得到单冻结集合");
           Collection co = CollectionUtils.disjunction(set,listBoth);
           //将collection转换为list  此集合单冻结类型(日分润和日返现）
           List<String> listOne = new ArrayList<>(rs);
           //日分润
           List<String> listProfit = new ArrayList<>();
           //日返现
           List<String> listm = new ArrayList<>();
           for (String str:listOne) {
               for (FreezeOperationRecord fo :freezeDay) {
                   if (fo.getAgentId().equals(str)&&"01".equals(fo.getFreezeType())){
                       listProfit.add(fo.getAgentId());
                   }else if(fo.getAgentId().equals(str)&&"02".equals(fo.getFreezeType())){
                       listProfit.add(fo.getAgentId());
                   }

               }

           }
           logger.info("日分润请求远程接口");
           //日分润单独冻结
           List<String> listProfitBus = new ArrayList<>();
           if(listProfit.size()!=0){
               /*for (String str:listProfit) {
                   List<Map<String,Object>> mapList2 = freezeAgentMapper.queryBumId(str);
                   for (Map m:mapList2) {
                       if("5000".equals(m.get("BUS_PLATFORM").toString()) || "6000".equals(m.get("BUS_PLATFORM").toString())){
                           listProfitBus.add(m.get("BUS_NUM").toString());
                       }
                   }
               }*/
               map.put("unfreeze","1");
               map.put("batchIds",map.put("batchIds",JsonUtil.objectToJson(listProfit)));
               String params2 = JsonUtil.objectToJson(map);
               logger.info("请求报文:"+params2);
               String res = HttpClientUtil.doPostJson(AppConfig.getProperty("busiPlat.refuse"), params2);
               logger.info("调用接口返回数据为:"+res);
               if (!JSONObject.parseObject(res).get("respCode").equals("000000")) {
                   logger.info("日分润冻结失败");
                   /*throw new RuntimeException("冻结失败");*/
               }

           }
           logger.info("日返现请求远程接口");
           //日返现单独冻结
           List<String> listmBus = new ArrayList<>();
           if(listm.size()!=0){
              /* for (String str:listm) {
                   List<Map<String,Object>> mapList3 = freezeAgentMapper.queryBumId(str);
                   for (Map m:mapList3) {
                       if("5000".equals(m.get("BUS_PLATFORM").toString()) || "6000".equals(m.get("BUS_PLATFORM").toString())){
                           listmBus.add(m.get("BUS_NUM").toString());
                       }
                   }
               }*/
               map.put("unfreeze","2");
               map.put("batchIds", map.put("batchIds",map.put("batchIds",JsonUtil.objectToJson(listm))));
               String params3 = JsonUtil.objectToJson(map);
               logger.info("请求报文:"+params3);
               String res = HttpClientUtil.doPostJson(AppConfig.getProperty("busiPlat.refuse"), params3);
               logger.info("调用接口返回数据为:"+res);
               if (!JSONObject.parseObject(res).get("respCode").equals("000000")) {
                   logger.info("日返现冻结失败");
                  /* throw new RuntimeException("冻结失败");*/
               }

           }

           logger.info("================================月冻结开始==========================================");
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
                       if(profitDetailMonth1==null){
                           logger.info("此代理商"+freezeOperationRecord.getAgentId()+"没有月份润");
                           throw new RuntimeException("此代理商"+freezeOperationRecord.getAgentId()+"没有月份润");
                       }
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

          /* freezeDayJob.queryDayFreeze();*/

       }else{
           logger.info("未选择代理商" );
           throw new RuntimeException("未选择代理商");
       }

    }
@Override
    public List<FreezeOperationRecord> getCheckHistoryDate(FreezeOperationRecord freezeOperationRecord){
        FreezeOperationRecordExample freezeOperationRecordExample = new FreezeOperationRecordExample();
        FreezeOperationRecordExample.Criteria criteria = freezeOperationRecordExample.createCriteria();
        if(StringUtils.isNotBlank(freezeOperationRecord.getAgentId()) && !"undefined".equals(freezeOperationRecord.getAgentId())){
            criteria.andAgentIdEqualTo(freezeOperationRecord.getAgentId());
        }
        if(StringUtils.isNotBlank(freezeOperationRecord.getParentAgentId())&& !"undefined".equals(freezeOperationRecord.getParentAgentId())){
            criteria.andParentAgentIdEqualTo(freezeOperationRecord.getParentAgentId());
        }
        if(StringUtils.isNotBlank(freezeOperationRecord.getFreezeBatch())&& !"undefined".equals(freezeOperationRecord.getFreezeBatch())){
            criteria.andFreezeBatchEqualTo(freezeOperationRecord.getFreezeBatch());
        }
        if(StringUtils.isNotBlank(freezeOperationRecord.getFreezeType())&& !"undefined".equals(freezeOperationRecord.getFreezeType())){
            criteria.andFreezeTypeEqualTo(freezeOperationRecord.getFreezeType());
        }
        freezeOperationRecordExample.setOrderByClause("OPERATION_TIME desc");
         List<FreezeOperationRecord> freezeOperationRecords =freezeOperationRecordMapper.selectByExample(freezeOperationRecordExample);
    return freezeOperationRecords;
}

    @Override
    public Integer queryDayFreezeCount() {
        return freezeAgentMapper.queryDayFreezeCount();
    }

    @Override
    public List<FreezeAgent> queryDayFreezeDate(Integer startNum, Integer endNum) {
        return freezeAgentMapper.queryDayFreezeDate(startNum,endNum);
    }

    @Override
    public List<FreezeOperationRecord> selectByExample(FreezeOperationRecordExample example) {
        return freezeOperationRecordMapper.selectByExample(example);
    }

    @Override
    public int updateByPrimaryKeySelective(FreezeOperationRecord record) {
        return freezeOperationRecordMapper.updateByPrimaryKeySelective(record);
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
    private void updateThawAgentByBatch(String batch, String result){
        String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if ("1".equals(result)){//解冻成功
            freezeAgentMapper.updateThawAgentByBatch(batch,"0");
            logger.info("解冻状态更新成功==========batch:"+batch);
            FreezeOperationRecordExample example=new FreezeOperationRecordExample();
            FreezeOperationRecordExample.Criteria criteria=example.createCriteria();
            criteria.andThawBatchEqualTo(batch);
            List<FreezeOperationRecord> freezeOperationRecords = freezeOperationRecordMapper.selectByExample(example);//解冻申请中的数据
            //获取日分润代理商
            List<String> proAgentIds = freezeOperationRecords.stream().filter(freezeOperationRecord -> freezeOperationRecord.getFreezeType().equals("01")).map(FreezeOperationRecord::getAgentId).collect(Collectors.toList());
            List<String> proBatchIds = new ArrayList<>();
            for (String proAgentId : proAgentIds) {
                List<Map<String,Object>> s = freezeAgentMapper.queryBumId(proAgentId);
                for (Map<String, Object> map : s) {
                    if ("6000".equals(map.get("BUS_PLATFORM").toString())||"5000".equals(map.get("BUS_PLATFORM").toString()))
                    proBatchIds.add(map.get("BUS_NUM")==null?"":map.get("BUS_NUM").toString());
                }
            }
            //获取日返现代理商
            List<String> backAgentIds = freezeOperationRecords.stream().filter(freezeOperationRecord -> freezeOperationRecord.getFreezeType().equals("02")).map(FreezeOperationRecord::getAgentId).collect(Collectors.toList());
            List<String> backBatchIds = new ArrayList<>();
            for (String backAgentId : backAgentIds) {
                List<Map<String,Object>> s = freezeAgentMapper.queryBumId(backAgentId);
                for (Map<String, Object> map : s) {
                    if ("6000".equals(map.get("BUS_PLATFORM").toString())||"5000".equals(map.get("BUS_PLATFORM").toString()))
                        backBatchIds.add(map.get("BUS_NUM")==null?"":map.get("BUS_NUM").toString());
                }
            }
            boolean proTemp=false,backTemp=false;//是否解冻失败的标识
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("agencyBlack_type", "0");//1、冻结 0、解冻
            map.put("type", "2");//1-冻结本身以及下属，2-冻结自身（默认）；3-本身不冻结，冻结所有下级
            map.put("flag", "0");//4冻结;0解冻
            map.put("unfreeze", "1");//0-双冻结（默认）;1-分润冻结; 2-返现冻结
            map.put("batchIds", JsonUtil.objectToJson(proBatchIds));//业务平台编码list
            String params = JsonUtil.objectToJson(map);
            String res = HttpClientUtil.doPostJson
                    (AppConfig.getProperty("busiPlat.refuse"), params);
            logger.debug("请求信息：" + res);
            if (!JSONObject.parseObject(res).get("respCode").equals("000000")) {    //请求失败
                logger.error("请求失败！");
                AppConfig.sendEmails("代理商冻结失败" + res,"代理商冻结失败");
                proTemp=true;//日分润接口解冻失败 标识更新
            }
            map.put("unfreeze", "2");
            map.put("batchIds", JsonUtil.objectToJson(backBatchIds));//业务平台编码list
            params = JsonUtil.objectToJson(map);
            res = HttpClientUtil.doPostJson
                    (AppConfig.getProperty("busiPlat.refuse"), params);
            logger.debug("请求信息：" + res);
            if (!JSONObject.parseObject(res).get("respCode").equals("000000")) {
                logger.error("请求失败！");
                AppConfig.sendEmails("代理商冻结失败" + res,"代理商冻结失败");
                backTemp=true;//日返现接口解冻失败 标识更新
            }

            for (FreezeOperationRecord thawRecord : freezeOperationRecords) {
                thawRecord.setId(idService.genId(TabId.P_FREEZE_OPERATION_RECORD));//设置新的id
                thawRecord.setOperationTime(time);//解冻成功时间
                if ("01".equals(thawRecord.getFreezeType())&&proTemp){  //如果日分润接口解冻失败
                    thawRecord.setStatus("0");//状态设置为冻结
                    thawRecord.setThawBatch(null);
                    thawRecord.setThawOperator(null);
                    thawRecord.setThawTime(null);
                    thawRecord.setThawReason(null);
                }else if ("02".equals(thawRecord.getFreezeType())&&backTemp){   //如果日分润接口解冻失败
                    thawRecord.setStatus("0");//状态设置为冻结
                    thawRecord.setThawBatch(null);
                    thawRecord.setThawOperator(null);
                    thawRecord.setThawTime(null);
                    thawRecord.setThawReason(null);
                }else {
                    thawRecord.setStatus("1");
                }
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
            if (StringUtils.isNotBlank(record.getParentAgentId())){
                freezeCriteria.andParentAgentIdEqualTo(record.getParentAgentId());
            }
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
