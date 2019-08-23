package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.TemplateRecodeMapper;
import com.ryx.credit.profit.pojo.PCityApplicationDetail;
import com.ryx.credit.profit.pojo.PCityApplicationDetailExample;
import com.ryx.credit.profit.pojo.TemplateRecode;
import com.ryx.credit.profit.pojo.TemplateRecodeExample;
import com.ryx.credit.profit.service.ITemplateRecodeService;
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

    private static final String TEMPLATE_NOW = AppConfig.getProperty("template.now");
    private static final String TEMPLATE_APPLY = AppConfig.getProperty("template.apply");
    private static final String TEMPLATE_APPLY_DETAIL = AppConfig.getProperty("template.apply.detail");
    private static final String TEMPLATE_APPLY_PASS = AppConfig.getProperty("template.apply.pass");

    @Override
    public PageInfo getApplyList(Page page, TemplateRecode templateRecode) {
        TemplateRecodeExample example = new TemplateRecodeExample();
        example.setPage(page);
        TemplateRecodeExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(templateRecode.getAgentId())){
            criteria.andAgentIdEqualTo(templateRecode.getAgentId());
        }
        if(StringUtils.isNotBlank(templateRecode.getAgentName())){
            criteria.andAgentNameEqualTo(templateRecode.getAgentName());
        }
        if(StringUtils.isNotBlank(templateRecode.getBusPlatform())){
            criteria.andBusPlatformEqualTo(templateRecode.getBusPlatform());
        }
        if(StringUtils.isNotBlank(templateRecode.getBusNum())){
            criteria.andBusNumEqualTo(templateRecode.getBusNum());
        }
        if(StringUtils.isNotBlank(templateRecode.getTemplateName())){
            criteria.andTemplateNameEqualTo(templateRecode.getTemplateName());
        }
        if(StringUtils.isNotBlank(templateRecode.getApplyResult())){
            criteria.andApplyResultEqualTo(templateRecode.getApplyResult());
        }
        if(StringUtils.isNotBlank(templateRecode.getCreateUser())){
            criteria.andCreateUserEqualTo(templateRecode.getCreateUser());
        }
        example.setOrderByClause("CREATE_DATE DESC");
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(recodeMapper.selectByExample(example));
        pageInfo.setTotal((int)recodeMapper.countByExample(example));
        return pageInfo;
    }

    /**获取代理商当前url*/
    @Override
    public Map<String,Object> getTemplateNow(String orgId) throws MessageException {
        if(StringUtils.isBlank(orgId)){
            throw new MessageException("平台编码不能为空");
        }
        JSONObject param = new JSONObject();
        param.put("orgId", orgId);
        String result = HttpClientUtil.doPostJson(TEMPLATE_NOW, param.toJSONString());
        Map<String,Object> resultMap = JSONObject.parseObject(result);
        if(!(boolean)resultMap.get("result")){
            throw new MessageException(resultMap.get("msg").toString());
        }
        Map<String,Object> objectMap = (Map<String,Object>)resultMap.get("data");
        return objectMap;
    }

    @Override
    public Map<String, String> getAgentInfo(String busNum) {
        List<Map<String,String>> list = recodeMapper.getAgentInfoByBusNum(busNum);
        if(list.size() > 0 ){
            return list.get(0);
        }
        return null;
    }

    /***保存信息，并发起审批***/
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public void saveAndApply(Map<String, String> map1, JSONObject map2) throws MessageException {
        //将数据传送至业务平台
        String result = HttpClientUtil.doPostJson(TEMPLATE_APPLY, map2.toJSONString());
        Map<String,Object> resultMap = JSONObject.parseObject(result);
        if(!(boolean)resultMap.get("result")){
            throw new MessageException(resultMap.get("msg").toString());
        }
        Map<String,Object> objectMap = (Map<String,Object>)resultMap.get("data");
        //将数据申请信息保存至本平台
        TemplateRecode templateRecode = new TemplateRecode();
        templateRecode.setId(idService.genId(TabId.P_TEMPLATE_APPLY_RECORD));
        templateRecode.setAgentId(map1.get("agentId"));
        templateRecode.setAgentName(map1.get("agentName"));
        templateRecode.setBusPlatform(map1.get("busPlatform"));
        templateRecode.setBusNum(map1.get("orgId"));
        templateRecode.setCreateUser(map1.get("userId"));
        templateRecode.setTemplateId(objectMap.get("applyId").toString());
        templateRecode.setTemplateName(objectMap.get("templateName").toString());
        templateRecode.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        templateRecode.setApplyResult("0");  //  0 暂存
        recodeMapper.insertSelective(templateRecode);

        Map startPar = agentEnterService.startPar(map1.get("userId"));
        String proceId = null;
        String workId = "agent_zg_template_2.0";
        try{
            proceId = activityService.createDeloyFlow(null, workId, null, null, startPar);
            if (proceId == null) {
                //启动失败，要删除对应数据
                TemplateRecodeExample pExample = new TemplateRecodeExample();
                pExample.createCriteria().andIdEqualTo(templateRecode.getId());
                recodeMapper.deleteByExample(pExample);
                throw new MessageException("分润模板线上申请审批流启动失败!");
            }
        }catch (Exception e){
            TemplateRecodeExample pExample = new TemplateRecodeExample();
            pExample.createCriteria().andIdEqualTo(templateRecode.getId());
            recodeMapper.deleteByExample(pExample);
            throw new MessageException("分润模板线上申请审批流启动失败!");
        }
        BusActRel record = new BusActRel();
        record.setBusId(templateRecode.getId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(map1.get("userId"));
        record.setAgentId(templateRecode.getAgentId());
        record.setAgentName(templateRecode.getAgentName());
        record.setBusType(BusActRelBusType.profitTempalteApply.name());
        record.setDataShiro(BusActRelBusType.profitTempalteApply.key);
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



    @Override
    public TemplateRecode getTemplateRecodeById(String id) {
        return recodeMapper.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> getTemplateApplyDetail(String recordId){
        JSONObject map = new JSONObject();
        map.put("applyId",recordId);
        String result = HttpClientUtil.doPostJson(TEMPLATE_APPLY_DETAIL, map.toJSONString());
        Map<String,Object> resultMap = JSONObject.parseObject(result);
        return resultMap;
    }


    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException {
        logger.info("审批对象：{}", JSONObject.toJSON(agentVo));

        AgentResult result = new AgentResult(500, "系统异常", "");
        Map<String, Object> reqMap = new HashMap<>();

        if(StringUtils.isNotBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", agentVo.getOrderAprDept());
        }
        //通过
        if(Objects.equals("pass",agentVo.getApprovalResult())
                && StringUtils.isBlank(agentVo.getOrderAprDept())){
           // reqMap.put("dept", "finish");
            reqMap.put("dept", "");
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
     * 修改信息，并保存
     * @param map1
     * @param map2
     * @throws MessageException
     */
    @Override
    public void editInfo(Map<String, String> map1, JSONObject map2) throws MessageException {
        String result = HttpClientUtil.doPostJson(TEMPLATE_APPLY, map2.toJSONString());
        Map<String,Object> resultMap = JSONObject.parseObject(result);
        if(!(boolean)resultMap.get("result")){
            throw new MessageException(resultMap.get("msg").toString());
        }
        Map<String,Object> objectMap = (Map<String,Object>)resultMap.get("data");
        //将数据申请信息保存至本平台
        TemplateRecode templateRecode = new TemplateRecode();
        templateRecode.setId(map1.get("recordId"));
        templateRecode.setTemplateName(objectMap.get("templateName").toString());
        templateRecode.setTemplateId(objectMap.get("applyId").toString());
        recodeMapper.updateByPrimaryKeySelective(templateRecode);
    }

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
                    JSONObject map2 = new JSONObject();
                    map2.put("applyId",templateRecode.getTemplateId());
                    String result = HttpClientUtil.doPostJson(TEMPLATE_APPLY_PASS, map2.toJSONString());
                    Map<String,Object> resultMap = JSONObject.parseObject(result);
                    if(!(boolean)resultMap.get("result")){
                        logger.info("***********分配失败，***********");
                        templateRecode.setAssignResult("1");// 分配失败
                        templateRecode.setAssignReason(resultMap.get("msg").toString());
                    }else{
                        Map<String,Object> objectMap = (Map<String,Object>)resultMap.get("data");
                        templateRecode.setRev1(objectMap.get("templateId").toString());
                        templateRecode.setRev2(objectMap.get("templateName").toString());
                        templateRecode.setAssignResult("0");// 分配成功
                    }
                }else{
                    templateRecode.setApplyResult("3"); // 撤销
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
}
