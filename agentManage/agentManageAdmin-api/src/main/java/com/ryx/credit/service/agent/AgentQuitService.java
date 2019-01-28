package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AgentQuit;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2019/1/26.
 */
public interface AgentQuitService {

    /**
     * 退出列表
     * @param agentQuit
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    PageInfo queryAgentQuitList(AgentQuit agentQuit, Page page, String dataRole, Long userId);


    AgentResult saveAgentQuit(AgentQuit agentQuit, String[] agentQuitFiles, String cUser, String saveFlag,List<OCashReceivablesVo> oCashReceivables)throws Exception;


    BigDecimal getCapitalSumAmt(String agentId);


    Map<String,Object> calculateSuppDept(String agentId);


    AgentQuit queryAgentQuit(String id);
}
