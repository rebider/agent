package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentQuitMapper;
import com.ryx.credit.pojo.admin.agent.AgentQuit;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentQuitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author RYX
 * @Date 2019/1/26
 * @Desc 代理商退出
 */
@Service("agentQuitService")
public class AgentQuitServiceImpl implements AgentQuitService {

    private static Logger logger = LoggerFactory.getLogger(AgentQuitServiceImpl.class);
    @Autowired
    private AgentQuitMapper agentQuitMapper;
    @Autowired
    private IUserService iUserService;


    /**
     * 退出列表
     * @param agentQuit
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    @Override
    public PageInfo queryAgentQuitList(AgentQuit agentQuit, Page page, String dataRole, Long userId) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(agentQuit.getId())) {
            reqMap.put("id", agentQuit.getId());
        }
        if (StringUtils.isNotBlank(agentQuit.getAgentId())) {
            reqMap.put("agentId", agentQuit.getAgentId());
        }
        if (StringUtils.isNotBlank(agentQuit.getAgentName())) {
            reqMap.put("agentName", agentQuit.getAgentName());
        }
        if (null != agentQuit.getCloReviewStatus()) {
            reqMap.put("cloReviewStatus", agentQuit.getCloReviewStatus());
        }
//        if(StringUtils.isBlank(dataRole)){
//            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
//            if(orgCodeRes == null && orgCodeRes.size() != 1){
//                return null;
//            }
//            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
//            reqMap.put("orgId", String.valueOf(stringObjectMap.get("ORGID")));
//        }
        List<Map<String, Object>> agentMergeList = agentQuitMapper.queryAgentQuitList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentMergeList);
        pageInfo.setTotal(agentQuitMapper.queryAgentQuitCount(reqMap));
        return pageInfo;
    }

}
