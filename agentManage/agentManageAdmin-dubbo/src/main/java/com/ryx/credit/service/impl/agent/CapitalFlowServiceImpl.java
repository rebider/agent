package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.CapitalFlowMapper;
import com.ryx.credit.pojo.admin.agent.CapitalFlow;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.CapitalFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2019/2/12.
 * 资金流水
 */
@Service("capitalFlowService")
public class CapitalFlowServiceImpl implements CapitalFlowService {

    private static Logger logger = LoggerFactory.getLogger(CapitalFlowServiceImpl.class);
    @Autowired
    private CapitalFlowMapper capitalFlowMapper;
    @Autowired
    private IUserService iUserService;

    /**
     * 资金流水列表
     * @param capitalFlow
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    @Override
    public PageInfo queryCapitalFlowList(CapitalFlow capitalFlow, Page page, String dataRole, Long userId) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(capitalFlow.getId())) {
            reqMap.put("id", capitalFlow.getId());
        }
        if (StringUtils.isNotBlank(capitalFlow.getAgentId())) {
            reqMap.put("agentId", capitalFlow.getAgentId());
        }
        if (StringUtils.isNotBlank(capitalFlow.getAgentName())) {
            reqMap.put("agentName", capitalFlow.getAgentName());
        }
//        if(StringUtils.isBlank(dataRole)){
//            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
//            if(orgCodeRes == null && orgCodeRes.size() != 1){
//                return null;
//            }
//            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
//            reqMap.put("orgId", String.valueOf(stringObjectMap.get("ORGID")));
//        }
        List<Map<String, Object>> capitalChangeList = capitalFlowMapper.queryCapitalFlowList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(capitalChangeList);
        pageInfo.setTotal(capitalFlowMapper.queryCapitalFlowCount(reqMap));
        return pageInfo;
    }

}
