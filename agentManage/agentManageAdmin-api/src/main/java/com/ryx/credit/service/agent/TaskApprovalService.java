package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.vo.AgentVo;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/30 16:51
 */
public interface TaskApprovalService {

    List<Map<String, Object>> queryBusInfoAndRemit(AgentBusInfo agentBusInfo);

    List<Map<String, Object>> queryBusInfoAndRemitByBusId(String busId);

    AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception;

    Map findBusActByBusId(String busId,String busType);
}
