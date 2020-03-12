package com.ryx.jobOrder.service.impl;

import com.ryx.credit.common.enumc.QueryAcceptType;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.CResource;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.jobOrder.dao.JobOrderAuthMapper;
import com.ryx.jobOrder.service.JobOrderAuthService;
import com.ryx.jobOrder.vo.JobKeyManageNodeVo;
import com.ryx.jobOrder.vo.JobKeyManageVo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("jobOrderAuthService")
public class JobOrderAuthServiceImpl implements JobOrderAuthService {

    private static final Logger logger = LoggerFactory.getLogger(JobOrderAuthServiceImpl.class);

    @Autowired
    private AgentService agentService;

    @Autowired
    private JobOrderAuthMapper jobOrderAuthMapper;


    @Override
    public List<Map<String,Object>> getReqJobOrderAuth(Long userId) {
        List<Map<String, Object>> reqJobOrderAuth = jobOrderAuthMapper.getReqJobOrderAuth(userId);
        return reqJobOrderAuth;
    }

    @Override
    public Map<String, Object> getJobOrderType(String JobOrderKey) {
        if (StringUtils.isBlank(JobOrderKey)){
            logger.error("根据工单类型查找可以受理该工单的对应工单组参数为空");
            return null;
        }
        Map<String, Object> acceptCode = jobOrderAuthMapper.getAcceptCode(JobOrderKey);
        return acceptCode;
    }

    @Override
    public Map<String, Object> getAcceptGroup(String userId) {
        if (StringUtils.isBlank(userId)){
            logger.error("根据用户id获取用户的工单组参数为空");
            return  null;
        }
        Map<String, Object> acceptCode = jobOrderAuthMapper.getAcceptGroup(userId);
        logger.info("根据用户id获取用户的工单组"+userId+",工单组信息:"+acceptCode);
        return acceptCode;
    }

    @Override
    public List<Map<String, Object>> getAllAcceptGroup() {
        List<Map<String, Object>> allAcceptGroup = jobOrderAuthMapper.getAllAcceptGroup();
        return allAcceptGroup;
    }

    @Override
    public Map<String, Object> getAcceptInfo(String code,String quertTYpe) {
        Map<String,Object> acceptInfo = null;
        if (quertTYpe.equals(QueryAcceptType.userId.code)){
            acceptInfo = jobOrderAuthMapper.getAcceptByuserid(code);
        }else if (quertTYpe.equals(QueryAcceptType.acceCode.code)){
            acceptInfo = jobOrderAuthMapper.getAcceptByAcceptCode(code);
        }
        return acceptInfo;
    }

    @Override
    public List<JobKeyManageNodeVo> getViewJobKeyManageNodes(String userId) {
        //判断是否为代理商
        Agent agent = agentService.queryAgentByUserId(userId);
        if (agent != null){
            logger.info("该用户为代理商{}",userId);
            List<Map<String, Object>> viewJobKeyManageModesByAgent = jobOrderAuthMapper.getViewJobKeyManageModesByAgent();
            List<JobKeyManageNodeVo> jobKeyManageNodeVos = mapTovo(viewJobKeyManageModesByAgent);
            logger.info("返回代理商可发起的工单类型{}",JsonUtil.objectToJson(jobKeyManageNodeVos));
            return jobKeyManageNodeVos;
        }
        List<Map<String,Object>> jobKeyManageNodes = jobOrderAuthMapper.getViewJobKeyManageNodesByUserId(userId);
        List<JobKeyManageNodeVo> jobKeyManageNodeVos = mapTovo(jobKeyManageNodes);
        logger.info("{}为非代理商,可申请工单类型{}",userId, JsonUtil.objectToJson(jobKeyManageNodes));
        return jobKeyManageNodeVos;
    }

    public static List<JobKeyManageNodeVo> mapTovo(List<Map<String,Object>> nodes){
        List<JobKeyManageNodeVo> thirdNodes = new ArrayList<JobKeyManageNodeVo>();
        List<JobKeyManageNodeVo> secondNodes = new ArrayList<JobKeyManageNodeVo>();
        List<JobKeyManageNodeVo> firstNodes = new ArrayList<JobKeyManageNodeVo>();

        for (int i=0; i < nodes.size();i++){
            String jm3_id           = (String) nodes.get(i).get("JM3_ID");
            String jm3_jo_key_type  = (String) nodes.get(i).get("JM3_JO_KEY_TYPE");
            String jm3_jo_key_name  = (String) nodes.get(i).get("JM3_JO_KEY_NAME");
            String jm3_jo_key_back_num = (String) nodes.get(i).get("JM3_JO_KEY_BACK_NUM");
            JobKeyManageNodeVo jobKeyManageNodeVoThird = new JobKeyManageNodeVo();
            jobKeyManageNodeVoThird.setChildNodes(null);
            jobKeyManageNodeVoThird.setId(jm3_id);
            jobKeyManageNodeVoThird.setJoKeyType(jm3_jo_key_type);
            jobKeyManageNodeVoThird.setJoKeyName(jm3_jo_key_name);
            jobKeyManageNodeVoThird.setJoKeyBackNum(jm3_jo_key_back_num);
            jobKeyManageNodeVoThird.setChildNodes(new ArrayList<JobKeyManageNodeVo>());
            if (i==0 || (thirdNodes.get(thirdNodes.size()-1)!=null && !thirdNodes.get(thirdNodes.size()-1).getId().equals(jobKeyManageNodeVoThird.getId()))){
                thirdNodes.add(jobKeyManageNodeVoThird);
            }

        }
        for (int i=0; i < nodes.size();i++){
            String jm2_id           = (String) nodes.get(i).get("JM2_ID");
            String jm2_jo_key_type  = (String) nodes.get(i).get("JM2_JO_KEY_TYPE");
            String jm2_jo_key_name  = (String) nodes.get(i).get("JM2_JO_KEY_NAME");
            String jm2_jo_key_back_num = (String) nodes.get(i).get("JM2_JO_KEY_BACK_NUM");
            JobKeyManageNodeVo jobKeyManageNodeVoSecond = new JobKeyManageNodeVo();
            jobKeyManageNodeVoSecond.setId(jm2_id);
            jobKeyManageNodeVoSecond.setJoKeyType(jm2_jo_key_type);
            jobKeyManageNodeVoSecond.setJoKeyName(jm2_jo_key_name);
            jobKeyManageNodeVoSecond.setJoKeyBackNum(jm2_jo_key_back_num);
            jobKeyManageNodeVoSecond.setChildNodes(new ArrayList<JobKeyManageNodeVo>());
            if (i ==0 || (secondNodes.get(secondNodes.size()-1)!=null && !secondNodes.get(secondNodes.size()-1).getId().equals(jobKeyManageNodeVoSecond.getId()))){
                secondNodes.add(jobKeyManageNodeVoSecond);
            }

        }
        for (int i=0; i < nodes.size();i++){
            String jm1_id           = (String) nodes.get(i).get("JM1_ID");
            String jm1_jo_key_type  = (String) nodes.get(i).get("JM1_JO_KEY_TYPE");
            String jm1_jo_key_name  = (String) nodes.get(i).get("JM1_JO_KEY_NAME");
            String jm1_jo_key_back_num = (String) nodes.get(i).get("JM1_JO_KEY_BACK_NUM");
            JobKeyManageNodeVo jobKeyManageNodeVoFirst = new JobKeyManageNodeVo();
            jobKeyManageNodeVoFirst.setId(jm1_id);
            jobKeyManageNodeVoFirst.setJoKeyType(jm1_jo_key_type);
            jobKeyManageNodeVoFirst.setJoKeyName(jm1_jo_key_name);
            jobKeyManageNodeVoFirst.setJoKeyBackNum(jm1_jo_key_back_num);
            jobKeyManageNodeVoFirst.setChildNodes(new ArrayList<JobKeyManageNodeVo>());
            if (i == 0 || (firstNodes.get(firstNodes.size()-1)!=null && !firstNodes.get(firstNodes.size()-1).getId().equals(jobKeyManageNodeVoFirst.getId()))){
                firstNodes.add(jobKeyManageNodeVoFirst);
            }

        }

        for (JobKeyManageNodeVo second:secondNodes){
            for (JobKeyManageNodeVo third:thirdNodes){
                if ( third.getJoKeyBackNum().equals(second.getId())){
                    second.getChildNodes().add(third);
                    continue;
                }
            }
        }

        for (JobKeyManageNodeVo first:firstNodes){
            for (JobKeyManageNodeVo second:secondNodes){
                if (second.getJoKeyBackNum().equals(first.getId())){
                    first.getChildNodes().add(second);
                    continue;
                }
            }
        }

        return  firstNodes;
    }

}
