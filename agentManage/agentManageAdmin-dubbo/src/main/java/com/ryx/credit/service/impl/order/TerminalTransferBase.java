package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.dao.order.TerminalTransferMapper;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author cl
 * @Date 2020/6/10 15:28
 * @Version 1.0
 */
@Component
public class TerminalTransferBase {

    private static Logger log = LoggerFactory.getLogger(TerminalTransferBase.class);

    @Autowired
    private TerminalTransferMapper terminalTransferMapper;

    //检验
    final static String TRANSFER_ZG_CHECK = "check";
    //调整
    final static String TRANSFER_ZG_ADJUST = "adjust";



    /**
     * 比较平台编码  并返回
     *
     * @param terminalTransferDetail
     * @return
     * @throws MessageException
     */
    public String disBusCode(TerminalTransferDetail terminalTransferDetail) throws MessageException {
        String originalOrgId = terminalTransferDetail.getOriginalOrgId().trim();
        String goalOrgId = terminalTransferDetail.getGoalOrgId().trim();
        Map<String, Object> mapAgentTypeOriginal = getAgentType(originalOrgId);
        Map<String, Object> platFromOriginalMap = terminalTransferMapper.queryPlatFrom(String.valueOf(mapAgentTypeOriginal.get("BUS_PLATFORM")));
        Map<String, Object> mapAgentTypeGoalOrgId = getAgentType(goalOrgId);
        Map<String, Object> platFromGoalOrgIdMap = terminalTransferMapper.queryPlatFrom(String.valueOf(mapAgentTypeGoalOrgId.get("BUS_PLATFORM")));
        if (!platFromOriginalMap.get("PLATFORM_TYPE").toString().equals(platFromGoalOrgIdMap.get("PLATFORM_TYPE").toString())) {
            log.info("您的机构码不属于同一种平台类型：原：" + originalOrgId + "目标：" + goalOrgId);
            throw new MessageException("您的机构码不属于同一种平台类型：原：" + originalOrgId + "目标：" + goalOrgId);
        }
        return platFromOriginalMap.get("PLATFORM_TYPE").toString();
    }

    /***
     * @Description: 获取AgentType
     * @Param: orgId 代理商id
     * @Author: chen_Liang
     * @Date: 2019/7/25
     */
    public Map<String, Object> getAgentType(String orgId) {

        return terminalTransferMapper.getAgentType(orgId);

    }
}
