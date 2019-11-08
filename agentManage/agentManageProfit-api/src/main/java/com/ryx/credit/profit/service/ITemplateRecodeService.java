package com.ryx.credit.profit.service;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.TemplateRecode;

import java.util.Map;

/**
 * @Author: cqt
 * @Description: 分润模板申请
 * @Date: 2019/8/15
 */
public interface ITemplateRecodeService {

    PageInfo getApplyList(Page page, TemplateRecode templateRecode,Map<String,Object> map);

    Map<String,Object> getTemplateNow(String orgId)throws MessageException;

    Map<String,String> getAgentInfo(String busNum);

    void saveAndApply(Map<String,String> map1, JSONObject map2) throws MessageException;

    String downDept(TemplateRecode recode);

    AgentResult approvalTask(AgentVo agentVo, String userId,Map<String,String> downDeptMap) throws ProcessException;

    TemplateRecode getTemplateRecodeById(String id);

    Map<String,Object> getTemplateApplyDetail(String recordId) throws MessageException;

    void editInfo(Map<String,String> map1, JSONObject map2) throws MessageException;

    //审批流回调方法
    void completeTaskEnterActivity(String insid, String status);

    Map<String,Object> checkAngAsign(String id)throws MessageException;

    void updateApplyStatus(String status,String id);

    Map<String,Object> checkTemplate(String applyId) throws MessageException;

    Object checkTempalteName(String applyId)throws MessageException;

    Map<String,Object> getRJTemplateNow(String orgId)throws MessageException;

    Map<String,Object> getSSTemplateNow(String orgId)throws MessageException;

    Map<String,Object> getRJTemplateDetail(String applyId) throws MessageException;

    Map<String,Object>  checkPOSReward(String orgId,String startMonth,String endMonth);

    Map<String,Object> getRDBTemplateNow(String agencyId) throws MessageException;

    Map<String,Object> getRDBTemplateApplyInfo(String agencyId,String taskId,String taskType) throws MessageException;

    void assignRDBTemplate(String id) throws MessageException;

    Map<String,Object> getSSTemplateDetail(String applyId) throws MessageException;
    TemplateRecode selectByPrimaryKey(String id);

    String saveNewTemplateName(JSONObject map) throws MessageException;


}
