package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.CapitalChangeApply;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by RYX on 2019/2/12.
 * 保证金申请
 */
public interface CapitalChangeApplyService {

    PageInfo queryCapitalChangeList(CapitalChangeApply capitalChangeApply, Page page, String dataRole, Long userId);

    CapitalChangeApply queryCapitalChangeById(String capitalId);

    AgentResult saveCapitalChange(CapitalChangeApply capitalChangeApply, String[] capitalChangeFiles, String cUser, String saveFlag, List<OCashReceivablesVo> oCashReceivables)throws Exception;

    AgentResult startAgentMergeActivity(String id, String cUser, Boolean isSave) throws Exception;

    AgentResult approvalCapitalChangeTask(AgentVo agentVo, String userId, String busId) throws Exception;

    AgentResult compressCapitalChangeActivity(String proIns, BigDecimal agStatus) throws Exception;
}
