package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.TemplateRecodeMapper;
import com.ryx.credit.profit.pojo.TemplateRecode;
import com.ryx.credit.profit.pojo.TemplateRecodeExample;
import com.ryx.credit.profit.service.ITemplateRecodeService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: cqt
 * @Description: 分润模板申请实现类
 * @Date: 2019/8/15
 */
@Service("templateRecordService")
public class TemplateRecordServiceImpl implements ITemplateRecodeService {

    private static Logger logger = LoggerFactory.getLogger(TemplateRecordServiceImpl.class);

    @Autowired
    private TemplateRecodeMapper recodeMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private DictOptionsService dictOptionsService;

    private static final String TEMPLATE_NOW = AppConfig.getProperty("template.now");
    private static final String TEMPLATE_APPLY = AppConfig.getProperty("template.apply");
    private static final String TEMPLATE_APPLY_DETAIL = AppConfig.getProperty("template.apply.detail");
    private static final String TEMPLATE_APPLY_PASS = AppConfig.getProperty("template.apply.pass");
    private static final String TEMPLATE_APPLY_CHECK = AppConfig.getProperty("template.apply.check");
    private static final String TEMPLATE_APPLY_CHECKNAME = AppConfig.getProperty("template.apply.checkName");
    private static final String TEMPLATE_CHECKPOSREWARD = AppConfig.getProperty("template.checkPOSReward");
    private static final String TEMPLATE_DELAPPLY = AppConfig.getProperty("template.delApply");

    private static final String RJ_TEMPLATE_NOW = AppConfig.getProperty("rj.template.now"); // 获取现有模板
    private static final String RJ_TEMPLATE_APPLY = AppConfig.getProperty("rj.template.apply"); // 申请
    private static final String RJ_TEMPLATE_APPLY_DETAIL = AppConfig.getProperty("rj.template.apply.detail"); //申请明细
    private static final String RJ_TEMPLATE_APPLY_PASS = AppConfig.getProperty("rj.template.apply.pass"); //通过，分配
    private static final String RJ_TEMPLATE_APPLY_CHECK = AppConfig.getProperty("rj.template.apply.check");//校验模板是否相同模板
    private static final String RJ_TEMPLATE_APPLY_CHECKNAME = AppConfig.getProperty("rj.template.apply.checkName");//校验模板名称是否重复

    private static final String RDB_TEMPLATE_NOW = AppConfig.getProperty("rdb.template.now");  // 现使用模板
    private static final String RDB_TEMPLATE_APPLY = AppConfig.getProperty("rdb.template.apply");  // 添加模板
    private static final String RDB_TEMPLATE_UPDATE = AppConfig.getProperty("rdb.template.update"); // 修改信息
    private static final String RDB_TEMPLATE_CHECKRESULT = AppConfig.getProperty("rdb.template.checkResult");  // 查询结果
    private static final String RDB_TEMPLATE_SETEFFECT = AppConfig.getProperty("rdb.template.setEffect"); // 获取申请模板的使用信息

    private static final String SS_TEMPLATE_NOW = AppConfig.getProperty("ss.template.now"); // 获取现有模板
    private static final String SS_TEMPLATE_APPLY = AppConfig.getProperty("ss.template.apply"); // 申请
    private static final String SS_TEMPLATE_APPLY_DETAIL = AppConfig.getProperty("ss.template.apply.detail"); //申请明细
    private static final String SS_TEMPLATE_APPLY_PASS = AppConfig.getProperty("ss.template.apply.pass"); //通过，分配
    private static final String SS_TEMPLATE_APPLY_CHECK = AppConfig.getProperty("ss.template.apply.check");//校验模板是否相同模板
    private static final String SS_TEMPLATE_APPLY_CHECKNAME = AppConfig.getProperty("ss.template.apply.checkName");//校验模板名称是否重复


    /**
     * 获取数据列表
     * @param page
     * @param templateRecode
     * @param map
     * @return
     */
    @Override
    public PageInfo getApplyList(Page page, TemplateRecode templateRecode,Map<String,Object> map) {
        Map<String,String> map1 = new HashMap<String,String>();
        if(map != null){
            String str = map.get("ORGANIZATIONCODE").toString();
            if(str.startsWith("region") && !str.endsWith("city")){
                String agDis = map.get("ORGID").toString();
                map1.put("agDis",agDis);
            }else if(str.endsWith("city")){
                String agDoc = map.get("ORGID").toString();  //省区
                map1.put("agDoc",agDoc);
            }
        }

        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(recodeMapper.getListByTem(page,templateRecode,map1));
        pageInfo.setTotal(recodeMapper.getCountByTem(templateRecode,map1));
        return pageInfo;
    }

    /**
     * 获取POS平台代理商正在使用的分润模板
     * @param orgId
     * @return
     * @throws MessageException
     */
    @Override
    public Map<String,Object> getTemplateNow(String orgId) throws MessageException {
        try{
            JSONObject param = new JSONObject();
            param.put("orgId", orgId);
            String  result = HttpClientUtil.doPostJson(TEMPLATE_NOW, param.toJSONString());
            Map<String,Object> resultMap = JSONObject.parseObject(result);
            if(!(boolean)resultMap.get("result")){
                throw new MessageException(resultMap.get("msg").toString());
            }
            return (Map<String,Object>)resultMap.get("data");
        }catch (MessageException e){
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            throw new MessageException("获取该业务平台模板信息失败，请重试！");
        }
    }

    /**
     * 根据业务平台编码获取对应业务平台信息，代理商信息
     * @param busNum
     * @return
     */
    @Override
    public Map<String, String> getAgentInfo(String busNum) {
        List<Map<String,String>> list = recodeMapper.getAgentInfoByBusNum(busNum);
        if(list.size() > 0 ){
            return list.get(0);
        }
        return null;
    }

    /**
     * 保存模板信息并发起审批
     * @param map1
     * @param map2
     * @throws MessageException
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public void saveAndApply(Map<String, String> map1, JSONObject map2) throws MessageException {

        List<Map<String,String>> stringList = recodeMapper.checkAgentDoc(map1.get("orgId"),map1.get("docDic"));
        if(stringList.size() > 1){
            throw new MessageException("业务平台编码有误！");
        }else if(stringList.size() < 1){
            throw new MessageException("该业务平台编码不属于该省区！");
        }
        List<TemplateRecode> list1 = checkRepeatRecode(map1.get("orgId"));
        if(list1.size() >= 1){
            throw new MessageException("当前代理商存在正在申请的分润模板！");
        }
        String result = null;
        Map<String,String> busInfo = getAgentInfo(map1.get("orgId"));
        TemplateRecode templateRecode = new TemplateRecode();
        templateRecode.setId(idService.genId(TabId.P_TEMPLATE_APPLY_RECORD));
        try {
            if("RDBPOS".equals(busInfo.get("PLATFORM_TYPE"))){
                map2.put("agencyId",map1.get("orgId"));
                map2.put("taskId",templateRecode.getId());
                saveRDBPOSApplyInfo(RDB_TEMPLATE_APPLY,map2);
                templateRecode.setTemplateId(templateRecode.getId());
                templateRecode.setTemplateName(map2.getString("mouldName"));
            }else if("RJPOS".equals(busInfo.get("PLATFORM_TYPE"))){
                reactRJPOSApply(RJ_TEMPLATE_APPLY,map2.toJSONString(),templateRecode);
            }else if("POS".equals(busInfo.get("PLATFORM_TYPE"))||"ZHPOS".equals(busInfo.get("PLATFORM_TYPE"))){
                // todo POS平台信息申请
                result = HttpClientUtil.doPostJson(TEMPLATE_APPLY, map2.toJSONString());
                Map<String,Object> resultMap = JSONObject.parseObject(result);
                if(!(boolean)resultMap.get("result")){
                    throw new MessageException(resultMap.get("msg").toString());
                }
                Map<String,Object> objectMap = (Map<String,Object>)resultMap.get("data");
                templateRecode.setTemplateId(((Map) objectMap.get("applyTemplate")).get("applyId").toString());
                templateRecode.setTemplateName(((Map) objectMap.get("applyTemplate")).get("templateName").toString());
               // result = HttpClientUtil.doPostJson(TEMPLATE_APPLY, map2.toJSONString());
            }else if("SSPOS".equals(busInfo.get("PLATFORM_TYPE"))){
                logger.info("请求参数："+map2.toJSONString());
                reactRJPOSApply(SS_TEMPLATE_APPLY,map2.toJSONString(),templateRecode);
            }
        }catch (MessageException e){
            throw new MessageException(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            throw new MessageException("保存分润模板失败，请重试");
        }
        templateRecode.setAgentId(map1.get("agentId"));
        templateRecode.setAgentName(map1.get("agentName"));
        templateRecode.setBusPlatform(map1.get("busPlatform"));
        templateRecode.setBusNum(map1.get("orgId"));
        templateRecode.setCreateUser(map1.get("userId"));
        templateRecode.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        templateRecode.setApplyResult("0");  //  0 暂存
        templateRecode.setRev2(map1.get("rev2"));

        recodeMapper.insertSelective(templateRecode);

        Map startPar = agentEnterService.startPar(map1.get("userId"));
        String proceId = null;

        List<Dict> actlist = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.PROFIT_TEMPLATE_APPLY3.name());
        String workId = null;
        for (Dict dict : actlist) {
            workId = dict.getdItemvalue();
        }
        if(StringUtils.isEmpty(workId)) {
            logger.info("========用户{}启动数据修改申请{}{}","审批流启动失败字典中未配置部署流程");
            throw new MessageException("审批流启动失败，未获取到数据字典配置部署流程!");
        }

        if("POS".equals(busInfo.get("PLATFORM_TYPE"))||"ZHPOS".equals(busInfo.get("PLATFORM_TYPE"))){
            if(startPar.get("party").toString()=="beijing"){
                try {
                    JSONObject mapJSONObject = new JSONObject();
                    mapJSONObject.put("applyId",templateRecode.getTemplateId());
                    mapJSONObject.put("isStartMonth","1");
                    String CheckResult = HttpClientUtil.doPostJson(TEMPLATE_APPLY_CHECK, mapJSONObject.toJSONString());
                    Map<String,Object> resultMap = JSONObject.parseObject(CheckResult);

                    Map<String,Object> objectMap = (Map<String,Object>)resultMap.get("data");

                    if(objectMap.get("isExist").toString().equals("1")&&map2.get("applyRewardRule")!=null&&((Map<String,String>)map2.get("applyRewardRule")).get("rewardType").equals("1")){
                        startPar.put("party","manager");
                    }else {
                        startPar.put("party","beijing");
                    }
                }catch (Exception e){
                    e.printStackTrace();

                }
            }
        }
        try{
            proceId = activityService.createDeloyFlow(null, workId, null, null, startPar);
            if (proceId == null) {
                TemplateRecodeExample pExample = new TemplateRecodeExample();
                pExample.createCriteria().andIdEqualTo(templateRecode.getId());
                recodeMapper.deleteByExample(pExample);
                throw new MessageException("分润模板线上申请审批流启动失败!");
            }
        }catch (Exception e){
            TemplateRecodeExample pExample = new TemplateRecodeExample();
            pExample.createCriteria().andIdEqualTo(templateRecode.getId());
            recodeMapper.deleteByExample(pExample);
            try {
                JSONObject map = new JSONObject();
                map.put("applyId",templateRecode.getTemplateId());
                String  resultde = HttpClientUtil.doPostJson(TEMPLATE_DELAPPLY, map.toJSONString());
                Map<String,Object> resultMap = JSONObject.parseObject(resultde);
            }catch (Exception e1){
                e1.printStackTrace();

            }
            throw new MessageException("分润模板线上申请审批流启动失败!");
        }
        BusActRel record = new BusActRel();
        record.setBusId(templateRecode.getId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(map1.get("userId"));
        record.setAgentId(templateRecode.getAgentId());
        record.setAgentName(templateRecode.getAgentName());
        record.setActivStatus(AgStatus.Approving.name());
        record.setBusType(BusActRelBusType.profitTempalteApply.name());
        record.setDataShiro(BusActRelBusType.profitTempalteApply.key);  //

        Map<String,String> reMap = stringList.get(0);
        record.setNetInBusType("ACTIVITY_"+reMap.get("BUS_PLATFORM"));
        record.setAgDocPro(reMap.get("AG_DOC_PRO"));
        record.setAgDocDistrict(reMap.get("AG_DOC_DISTRICT"));
        try{
            taskApprovalService.addABusActRel(record);
            logger.info("分润模板线上申请审批流启动成功");
        }catch (Exception e){
            e.printStackTrace();
            logger.info("分润模板线上申请审批流启动失败");
            TemplateRecodeExample pExample = new TemplateRecodeExample();
            pExample.createCriteria().andIdEqualTo(templateRecode.getId());
            recodeMapper.deleteByExample(pExample);
            throw new MessageException("分润模板线上申请审批流启动失败!:{}",e.getMessage());
        }
        templateRecode.setApplyResult("1"); // 申请中
        recodeMapper.updateByPrimaryKeySelective(templateRecode);
    }

    /**
     * 联动RJPOS保存申请信息
     * @param url
     * @param json
     * @param recode
     * @throws MessageException
     */
    private void reactRJPOSApply(String url,String json,TemplateRecode recode) throws MessageException{
        try {
           String result = HttpClientUtil.doPostJson(url, json);
            Map<String,Object> resultMap = JSONObject.parseObject(result);
            if(!(boolean)resultMap.get("result")){
                 throw new MessageException(resultMap.get("msg").toString());
             }
             Map<String,Object> objectMap = (Map<String,Object>)resultMap.get("data");

            recode.setTemplateId(objectMap.get("applyId").toString());
            recode.setTemplateName(objectMap.get("templateName").toString());
        }catch (MessageException e){
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            throw new MessageException("联动平台保存信息失败！");
        }
    }




    /**
     * 根据业务平台编码获取审批中以及退回的申请记录，用于重复申请判断
     * @param orgId
     * @return
     */
    private List<TemplateRecode> checkRepeatRecode(String orgId){
        TemplateRecodeExample example = new TemplateRecodeExample();
        TemplateRecodeExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(orgId)){
            criteria.andBusNumEqualTo(orgId);
        }
        List<String> list = new ArrayList<String>();
        list.add("4");
        list.add("3");
        criteria.andApplyResultNotIn(list);
        return recodeMapper.selectByExample(example);
    }

    /**
     * 根据id获取申请记录信息
     * @param id
     * @return
     */
    @Override
    public TemplateRecode getTemplateRecodeById(String id) {
        return recodeMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据申请id获取老综管模板详细信息
     * @param recordId
     * @return
     * @throws MessageException
     */
    @Override
    public Map<String, Object> getTemplateApplyDetail(String recordId) throws MessageException{
        JSONObject map = new JSONObject();
        map.put("applyId",recordId);
        String result = null;
        try {
            result = HttpClientUtil.doPostJson(TEMPLATE_APPLY_DETAIL, map.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
            throw new MessageException("联动业务系统获取模板信息失败，请重试！");
        }
        Map<String,Object> resultMap = JSONObject.parseObject(result);
        return resultMap;
    }


    @Override
    public String downDept(TemplateRecode recode){
        try {
            JSONObject mapJSONObject = new JSONObject();
            mapJSONObject.put("applyId",recode.getTemplateId());
            mapJSONObject.put("isStartMonth","1");
            String CheckResult = HttpClientUtil.doPostJson(TEMPLATE_APPLY_CHECK, mapJSONObject.toJSONString());
            Map<String,Object> resultMap = JSONObject.parseObject(CheckResult);
            Map<String,Object> objectMap = (Map<String,Object>)resultMap.get("data");
            Map<String, Object> map = getTemplateApplyDetail(recode.getTemplateId());
            Map<String, Object> dataMap = (Map<String, Object>) map.get("data");
            if(objectMap.get("isExist").toString().equals("1")&&dataMap.get("applyRewardRule")!=null&&((Map<String,String>)dataMap.get("applyRewardRule")).get("rewardType").equals("1")){
               return "yuhua";
            }
        }catch (Exception e){
            logger.info("大区获取下级审批部门失败");
            e.printStackTrace();

        }
        return "busness";
    }

    /**
     * 审批流任务处理
     * @param agentVo
     * @param userId
     * @return
     * @throws ProcessException
     */
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId, Map<String,String> downDeptMap) throws ProcessException {
        logger.info("审批对象：{}", JSONObject.toJSON(agentVo));

        AgentResult result = new AgentResult(500, "系统异常", "");
        Map<String, Object> reqMap = new HashMap<>();

        if(downDeptMap==null||downDeptMap.size()==0){
            if(StringUtils.isNotBlank(agentVo.getOrderAprDept())){
                reqMap.put("dept", agentVo.getOrderAprDept());
            }
            //通过
            if(Objects.equals("pass",agentVo.getApprovalResult())
                    && StringUtils.isBlank(agentVo.getOrderAprDept())){
                // reqMap.put("dept", "finish");
                reqMap.put("dept", "");
            }
        }else{
            if("1".equals(downDeptMap.get("daqv")) && downDeptMap.get("downDept")==null){
                reqMap.put("dept", "busness");
            }else {
                reqMap.put("dept", downDeptMap.get("downDept"));
            }
        }

        // if("reject".equals(agentVo.getApprovalResult())
        //        && StringUtils.isBlank(agentVo.getOrderAprDept())){
        if("reject".equals(agentVo.getApprovalResult())){
            reqMap.put("dept", "finish");
            String id = agentVo.getSid();
            TemplateRecode recode = recodeMapper.selectByPrimaryKey(id);
            recode.setApplyResult("3"); // 撤销
            recodeMapper.updateByPrimaryKeySelective(recode);
        }
        //设置为退回状态
        if("back".equals(agentVo.getApprovalResult())){
            String id = agentVo.getSid();
            TemplateRecode recode = recodeMapper.selectByPrimaryKey(id);
            recode.setApplyResult("2"); // 退回
            recodeMapper.updateByPrimaryKeySelective(recode);
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
     * 老综管模板修改信息，并保存
     * @param map1
     * @param map2
     * @throws MessageException
     */
    @Override
    public void editInfo(Map<String, String> map1, JSONObject map2) throws MessageException {
        TemplateRecode recode = recodeMapper.selectByPrimaryKey(map1.get("recordId"));
        String platformType = getAgentInfo(recode.getBusNum()).get("PLATFORM_TYPE");

        String result = null;
        try {
            String busPlatform = map1.get("busPlatform");
            if("RDBPOS".equals(platformType)){
                String taskId = idService.genId(TabId.P_TEMPLATE_APPLY_RECORD);
                map2.put("agencyId",recode.getBusNum());
                map2.put("taskId",taskId);
                recode.setTemplateId(taskId);
                saveRDBPOSApplyInfo(RDB_TEMPLATE_UPDATE,map2);
            }else if("RJPOS".equals(platformType)){
                map2.put("applyId",recode.getTemplateId());
                map2.put("orgId",recode.getBusNum());
                reactRJPOSApply(RJ_TEMPLATE_APPLY,map2.toJSONString(),recode);
            }else if("POS".equals(platformType)||"ZHPOS".equals(platformType)){
                // todo POS平台信息修改
                result = HttpClientUtil.doPostJson(TEMPLATE_APPLY, map2.toJSONString());
                Map<String,Object> resultMap = JSONObject.parseObject(result);
                if(!(boolean)resultMap.get("result")){
                    throw new MessageException(resultMap.get("msg").toString());
                }

            }else if("SSPOS".equals(platformType)){
                map2.put("applyId",recode.getTemplateId());
                map2.put("orgId",recode.getBusNum());
                reactRJPOSApply(SS_TEMPLATE_APPLY,map2.toJSONString(),recode);
            }
        }catch (MessageException e){
            throw new MessageException(e.getMsg());
        } catch (Exception e){
            e.printStackTrace();
            throw new MessageException("联动业务系统失败，请联系管理员");
        }
        recode.setRev2(map1.get("rev2"));
        recodeMapper.updateByPrimaryKeySelective(recode);
    }






    /**
     * 审批流回调
     * @param insid
     * @param status
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void completeTaskEnterActivity(String insid, String status) {
        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {
            BusActRel rel =  taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                TemplateRecode templateRecode = recodeMapper.selectByPrimaryKey(rel.getBusId());
                if("1".equals(status)){ //通过
                    templateRecode.setApplyResult("4"); // 通过
                    //JSONObject map2 = new JSONObject();
                    //map2.put("applyId",templateRecode.getTemplateId());
                    //String result = HttpClientUtil.doPostJson(TEMPLATE_APPLY_PASS, map2.toJSONString());
                    //Map<String,Object> resultMap = JSONObject.parseObject(result);
                    //if(!(boolean)resultMap.get("result")){
                    //    logger.info("***********分配失败，***********");
                     //   templateRecode.setAssignResult("1");// 分配失败
                     //   templateRecode.setAssignReason(resultMap.get("msg").toString());
                    //}else{
                    //    Map<String,Object> objectMap = (Map<String,Object>)resultMap.get("data");
                     //   templateRecode.setRev1(objectMap.get("templateId").toString());
                    //    templateRecode.setRev2(objectMap.get("templateName").toString());
                    //    templateRecode.setAssignResult("0");// 分配成功
                   // }
                }else{
                    templateRecode.setApplyResult("3"); // 撤销
                    try {
                        JSONObject map = new JSONObject();
                        map.put("applyId",templateRecode.getTemplateId());
                        String  result = HttpClientUtil.doPostJson(TEMPLATE_DELAPPLY, map.toJSONString());
                        Map<String,Object> resultMap = JSONObject.parseObject(result);
                        if(!(boolean)resultMap.get("result")) {
                            logger.info("***********修改删除综管数据失败，***********");
                            throw new MessageException(resultMap.get("msg").toString());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        recodeMapper.updateByPrimaryKeySelective(templateRecode);

                    }
                }
                recodeMapper.updateByPrimaryKeySelective(templateRecode);

                logger.info("2更新审批流与业务对象");
                rel.setActivStatus(AgStatus.Approved.name());
                taskApprovalService.updateABusActRel(rel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("其他扣款申请审批流回调异常，activId：{}"+insid);
        }
    }


    /**
     * 联动老综管模板信息验证及模板分配
     * @param id
     * @return
     * @throws MessageException
     */
    @Override
    public Map<String,Object> checkAngAsign(String id) throws MessageException{
        TemplateRecode templateRecode = recodeMapper.selectByPrimaryKey(id);
        if(templateRecode == null){
            throw new MessageException("查询该模板申请信息失败，请联系管理员");
        }
        Map<String,String> stringMap = getAgentInfo(templateRecode.getBusNum());
        String result = null;
        JSONObject map2 = new JSONObject();
        map2.put("applyId",templateRecode.getTemplateId());
        try{
            if("RJPOS".equals(stringMap.get("PLATFORM_TYPE"))){ // 瑞+
                result = HttpClientUtil.doPostJson(RJ_TEMPLATE_APPLY_PASS, map2.toJSONString());
            }else if("POS".equals(stringMap.get("PLATFORM_TYPE"))||"ZHPOS".equals(stringMap.get("PLATFORM_TYPE"))){
                result = HttpClientUtil.doPostJson(TEMPLATE_APPLY_PASS, map2.toJSONString());
            }else if("SSPOS".equals(stringMap.get("PLATFORM_TYPE"))){
                result = HttpClientUtil.doPostJson(SS_TEMPLATE_APPLY_PASS, map2.toJSONString());
            }
            Map<String,Object> resultMap = JSONObject.parseObject(result);
            if(!(boolean)resultMap.get("result")) {
                logger.info("***********分配失败，***********");
                throw new MessageException(resultMap.get("msg").toString());
            }else{//分配成功
                Map<String,Object> objectMap = (Map<String,Object>)resultMap.get("data");
                templateRecode.setRev1(objectMap.get("templateId").toString());
                templateRecode.setTemplateName(objectMap.get("templateName").toString());
                templateRecode.setAssignResult("0");// 分配成功
                recodeMapper.updateByPrimaryKeySelective(templateRecode);
            }
            return resultMap;
        }catch (MessageException e){
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        }catch (NullPointerException e){
            e.printStackTrace();
            throw new MessageException("联动业务平台失败，请重试！");
        }catch (Exception e){
            e.printStackTrace();
            throw new MessageException("校验模板信息失败，请重试！");
        }

    }

    /**
     * 更新申请记录的状态
     * @param status 状态
     * @param id  id
     */
    @Override
    public void updateApplyStatus(String status,String id){
        TemplateRecode templateRecode = recodeMapper.selectByPrimaryKey(id);
        templateRecode.setApplyResult(status);
        recodeMapper.updateByPrimaryKeySelective(templateRecode);
    }

    /**
     * 联动老综管验证模板数据是否存在
     * @param applyId 申请id
     * @return
     * @throws MessageException
     */
    @Override
    public Map<String,Object> checkTemplate(String applyId)throws MessageException{
        TemplateRecode templateRecode = recodeMapper.selectByPrimaryKey(applyId);
        if(templateRecode == null){
            throw new MessageException("查询该模板申请信息失败，请联系管理员");
        }
        Map<String,String> stringMap = getAgentInfo(templateRecode.getBusNum());
        try{
            String result = null;
            JSONObject map2 = new JSONObject();
            map2.put("applyId",templateRecode.getTemplateId());
            if("RJPOS".equals(stringMap.get("PLATFORM_TYPE"))){ // 瑞+
                result = HttpClientUtil.doPostJson(RJ_TEMPLATE_APPLY_CHECK, map2.toJSONString());
            }else if("POS".equals(stringMap.get("PLATFORM_TYPE"))||"ZHPOS".equals(stringMap.get("PLATFORM_TYPE"))){
                result = HttpClientUtil.doPostJson(TEMPLATE_APPLY_CHECK, map2.toJSONString());
            }else if("SSPOS".equals(stringMap.get("PLATFORM_TYPE"))){//实时分润
                result = HttpClientUtil.doPostJson(SS_TEMPLATE_APPLY_CHECK, map2.toJSONString());
            }
            Map<String,Object> resultMap = JSONObject.parseObject(result);
            if(!(boolean)resultMap.get("result")){
                throw new MessageException(resultMap.get("msg").toString());
            }
            Map<String,Object> objectMap = (Map<String,Object>)resultMap.get("data");
            return objectMap;
        }catch (MessageException e){
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            throw new MessageException("校验模板信息失败，请重试！");
        }
    }

    /**
     * 联动老综管校验模板名称是否重复
     * @param applyId  申请id
     * @return
     * @throws MessageException
     */
    @Override
    public Object checkTempalteName(String applyId)throws MessageException{
        TemplateRecode templateRecode = recodeMapper.selectByPrimaryKey(applyId);
        if(templateRecode == null){
            throw new MessageException("查询该模板申请信息失败，请联系管理员");
        }
        Map<String,String> stringMap = getAgentInfo(templateRecode.getBusNum());
        try{
            String result = null;
            JSONObject map2 = new JSONObject();
            map2.put("applyId",templateRecode.getTemplateId());
            if("RJPOS".equals(stringMap.get("PLATFORM_TYPE"))){ // 瑞+
                result = HttpClientUtil.doPostJson(RJ_TEMPLATE_APPLY_CHECKNAME, map2.toJSONString());
            }else if("POS".equals(stringMap.get("PLATFORM_TYPE"))||"ZHPOS".equals(stringMap.get("PLATFORM_TYPE"))){
                result = HttpClientUtil.doPostJson(TEMPLATE_APPLY_CHECKNAME, map2.toJSONString());
            }else if ("SSPOS".equals(stringMap.get("PLATFORM_TYPE"))){
                result = HttpClientUtil.doPostJson(SS_TEMPLATE_APPLY_CHECKNAME, map2.toJSONString());
            }
            Map<String,Object> resultMap = JSONObject.parseObject(result);
            if(!(boolean)resultMap.get("result")) {
                logger.info("***********验证模板名称失败，***********");
                throw new MessageException(resultMap.get("msg").toString());
            }else{
                return true;
            }
        }catch (MessageException e){
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        }catch(Exception e){
            e.printStackTrace();
            throw new MessageException("验证模板名称失败！");
        }
    }

    /**
     * 获取瑞+分润模板
     * @param orgId
     * @return
     * @throws MessageException
     */
    @Override
    public Map<String,Object> getRJTemplateNow(String orgId)throws MessageException{
        try{
            JSONObject param = new JSONObject();
            param.put("orgId", orgId);
            String result = HttpClientUtil.doPostJson(RJ_TEMPLATE_NOW, param.toJSONString());
            Map<String,Object> resultMap = JSONObject.parseObject(result);
            if(!(boolean)resultMap.get("result")){
                throw new MessageException(resultMap.get("msg").toString());
            }
            return (Map<String,Object>)resultMap.get("data");
        }catch (MessageException e){
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            throw new MessageException("联动业务平台获取分润模板数据失败！");
        }
    }
    /**
     * 获实时分润分润模板
     * @param orgId
     * @return
     * @throws MessageException
     */
    @Override
    public Map<String,Object> getSSTemplateNow(String orgId)throws MessageException{
        if (StringUtils.isBlank(orgId)){
            throw new MessageException("业务平台编码不能为空！");
        }
        try{
            JSONObject param = new JSONObject();
            param.put("orgId", orgId);
            String result = HttpClientUtil.doPostJson(SS_TEMPLATE_NOW, param.toJSONString());
            logger.info("=======================实时分润接口数据："+result);
            Map<String,Object> resultMap = JSONObject.parseObject(result);
            if(!(boolean)resultMap.get("result")){
                throw new MessageException(resultMap.get("msg").toString());
            }
            Map<String,Object> objectMap = (Map<String,Object>)resultMap.get("data");
            return objectMap;
        }catch (MessageException e){
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            throw new MessageException("联动实时分润平台获取分润模板数据失败！");
        }
    }

    /**
     * 查看瑞+分润模板申请信息
     * @param applyId
     * @return
     * @throws MessageException
     */
    @Override
    public Map<String,Object> getRJTemplateDetail(String applyId) throws MessageException{
        JSONObject map = new JSONObject();
        map.put("applyId",applyId);
        String result = null;
        try {
            result = HttpClientUtil.doPostJson(RJ_TEMPLATE_APPLY_DETAIL, map.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
            throw new MessageException("联动业务系统获取模板信息失败，请重试！");
        }
        Map<String,Object> resultMap = JSONObject.parseObject(result);
        return resultMap;
    }

    /**查看实时分润模板详情
     * @param applyId
     * @return
     * @throws MessageException
     */
    @Override
    public Map<String,Object> getSSTemplateDetail(String applyId) throws MessageException{
        JSONObject map = new JSONObject();
        map.put("applyId",applyId);
        String result = null;
        try {
            result = HttpClientUtil.doPostJson(SS_TEMPLATE_APPLY_DETAIL, map.toJSONString());
            logger.info("实时分润模板申请详情查询报文："+result);
        }catch (Exception e){
            e.printStackTrace();
            throw new MessageException("联动业务系统获取模板信息失败，请重试！");
        }
        Map<String,Object> resultMap = JSONObject.parseObject(result);
        return resultMap;
    }


    public Map<String,Object>  checkPOSReward(String orgId,String startMonth,String endMonth){
        JSONObject map = new JSONObject();
        map.put("orgId",orgId);
        map.put("startMonth",startMonth);
        map.put("endMonth",endMonth);
        String  result = HttpClientUtil.doPostJson(TEMPLATE_CHECKPOSREWARD, map.toJSONString());
        Map<String,Object> resultMap = JSONObject.parseObject(result);
        return resultMap;
    }



    /**
     * 获取瑞大宝现有模板信息
     * @param  agencyId 业务平台编码
     * @return  模板信息
     * @throws MessageException
     */
    @Override
    public Map<String, Object> getRDBTemplateNow(String agencyId) throws MessageException {
        try {
            JSONObject map = new JSONObject();
            map.put("agencyId",agencyId);
            return useRDBPOSInterface(RDB_TEMPLATE_NOW,map);
        }catch (MessageException e){
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            throw new MessageException("联动瑞大宝平台获取分润模板信息失败！");
        }
    }

    private void saveRDBPOSApplyInfo(String url,JSONObject jsonObject) throws MessageException{
        try{
            String result =  HttpClientUtil.doPostJson(url, jsonObject.toJSONString());
            if(!"0000".equals(JSONObject.parseObject(result).get("code"))){
                throw new MessageException(JSONObject.parseObject(result).getString("msg"));
            }
            JSONObject map = new JSONObject();
            map.put("taskId",jsonObject.getString("taskId"));
            map.put("agencyId",jsonObject.getString("agencyId"));
            // 获取数据处理结果
            while (true){
                String result01 =  HttpClientUtil.doPostJson(RDB_TEMPLATE_CHECKRESULT, map.toJSONString());
                if("0000".equals(JSONObject.parseObject(result01).get("code"))){
                    break;
                }else if("9999".equals(JSONObject.parseObject(result01).get("code"))){
                    throw new MessageException(JSONObject.parseObject(result01).getString("msg"));
                }else if("1000".equals(JSONObject.parseObject(result01).get("code"))){
                    continue;
                }
                Thread.sleep(2000);
            }
        }catch (MessageException e){
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            throw new MessageException("联动瑞大宝平台保存申请信息失败！");
        }
    }



    /**
     * 调用瑞大宝接口处理数据方法
     * @param url  url
     * @param jsonObject  参数
     * @return 处理结果
     * @throws MessageException
     */
    private Map<String,Object> useRDBPOSInterface (String url,JSONObject jsonObject) throws MessageException{
        String result =  HttpClientUtil.doPostJson(url, jsonObject.toJSONString());
        Map<String,Object> resultMap = JSONObject.parseObject(result);
        if(!"0000".equals(resultMap.get("code"))){
            throw new MessageException(resultMap.get("msg").toString());
        }
        return  (Map<String,Object>)resultMap.get("result");
    }

    /**
     * 瑞大宝--获取申请模板处理详情
     * @param agencyId
     * @param taskId
     * @return
     * @throws MessageException
     */
    @Override
    public Map<String, Object> getRDBTemplateApplyInfo(String agencyId, String taskId,String taskType) throws MessageException {
        try {
            JSONObject map = new JSONObject();
            map.put("agencyId",agencyId);
            map.put("taskId",taskId);
            map.put("taskType",taskType);
            String result =  HttpClientUtil.doPostJson(RDB_TEMPLATE_CHECKRESULT, map.toJSONString());
            if(!"0000".equals(JSONObject.parseObject(result).get("code"))){
                throw new MessageException(JSONObject.parseObject(result).get("msg").toString());
            }
            return (Map<String,Object>)JSONObject.parseObject(result).get("result");
        }catch (MessageException e){
            throw new MessageException(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            throw new MessageException("获取瑞大宝申请信息详情失败！");
        }
    }

    /**
     *  瑞大宝模板分配
     * @param id
     * @throws MessageException
     */
    @Override
    public void assignRDBTemplate(String id) throws MessageException {
        logger.info("瑞大宝模板分配联动。。。。。");
        TemplateRecode recode = recodeMapper.selectByPrimaryKey(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("agencyId",recode.getBusNum());
        jsonObject.put("taskId",recode.getTemplateId());
        jsonObject.put("auditDate",new SimpleDateFormat("yyyyMMdd").format(new Date()));
        String result =  HttpClientUtil.doPostJson(RDB_TEMPLATE_SETEFFECT, jsonObject.toJSONString());
        if(!"0000".equals(JSONObject.parseObject(result).get("code"))){
            throw new MessageException(JSONObject.parseObject(result).get("msg").toString());
        }
        recode.setAssignResult("0");// 分配成功
        recodeMapper.updateByPrimaryKeySelective(recode);

    }

    @Override
    public TemplateRecode selectByPrimaryKey(String id) {
        return recodeMapper.selectByPrimaryKey(id);
    }


    public String saveNewTemplateName(JSONObject map) throws MessageException {
        // todo POS平台信息修改

        String result = HttpClientUtil.doPostJson(TEMPLATE_APPLY, map.toJSONString());
        Map<String,Object> resultMap = JSONObject.parseObject(result);
        if(!(boolean)resultMap.get("result")){
            throw new MessageException(resultMap.get("msg").toString());
        }
        return "1";

    }
}
