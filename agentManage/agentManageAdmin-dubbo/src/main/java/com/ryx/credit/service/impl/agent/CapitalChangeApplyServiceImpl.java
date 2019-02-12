package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.CapitalChangeApplyMapper;
import com.ryx.credit.pojo.admin.agent.CapitalChangeApply;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.CapitalChangeApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2019/2/12.
 * 保证金申请
 */
@Service("capitalChangeApplyService")
public class CapitalChangeApplyServiceImpl implements CapitalChangeApplyService {

    private static Logger logger = LoggerFactory.getLogger(CapitalChangeApplyServiceImpl.class);
    @Autowired
    private CapitalChangeApplyMapper capitalChangeApplyMapper;
    @Autowired
    private IUserService iUserService;

    /**
     * 保证金列表
     * @param capitalChangeApply
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    @Override
    public PageInfo queryCapitalChangeList(CapitalChangeApply capitalChangeApply, Page page, String dataRole, Long userId) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(capitalChangeApply.getId())) {
            reqMap.put("id", capitalChangeApply.getId());
        }
        if (StringUtils.isNotBlank(capitalChangeApply.getAgentId())) {
            reqMap.put("agentId", capitalChangeApply.getAgentId());
        }
        if (StringUtils.isNotBlank(capitalChangeApply.getAgentName())) {
            reqMap.put("agentName", capitalChangeApply.getAgentName());
        }
        if (null != capitalChangeApply.getCloReviewStatus()) {
            reqMap.put("cloReviewStatus", capitalChangeApply.getCloReviewStatus());
        }
//        if(StringUtils.isBlank(dataRole)){
//            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
//            if(orgCodeRes == null && orgCodeRes.size() != 1){
//                return null;
//            }
//            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
//            reqMap.put("orgId", String.valueOf(stringObjectMap.get("ORGID")));
//        }
        List<Map<String, Object>> capitalChangeList = capitalChangeApplyMapper.queryCapitalChangeList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(capitalChangeList);
        pageInfo.setTotal(capitalChangeApplyMapper.queryCapitalChangeCount(reqMap));
        return pageInfo;
    }

    /**
     * 查看申请数据
     * @param capitalId
     * @return
     */
    @Override
    public CapitalChangeApply queryCapitalChangeById(String capitalId) {
        if (StringUtils.isBlank(capitalId)) {
            return null;
        }
        CapitalChangeApply capitalChangeApply = capitalChangeApplyMapper.selectByPrimaryKey(capitalId);
        if (null == capitalChangeApply) {
            return null;
        }
        return capitalChangeApply;
    }

}
