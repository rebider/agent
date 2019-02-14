package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentQuit;
import com.ryx.credit.pojo.admin.vo.AgentVo;
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


    AgentResult approvalAgentQuitTask(AgentVo agentVo, String userId, String busId) throws Exception;


    AgentResult compressAgentQuitActivity(String proIns, BigDecimal agStatus)throws Exception;


    AgentQuit getAgentQuitById(String quitId);


    AgentResult startAgentMergeActivity(String id, String cUser, Boolean isSave) throws Exception;


    AgentResult editAgentQuit(AgentQuit agentQuit,String cUser,String[] agentMergeFiles, List<OCashReceivablesVo> oCashReceivables) throws Exception;


    AgentResult deleteAgentQuit(String quitId, String cUser) throws Exception;


    AgentResult agentQuitUploadRtc(AgentQuit agentQuit, String cUser, String[] agentMergeFiles) throws Exception;


    List<AgentBusInfo> getBusInfosById(String id);


    AgentResult manualAgentQuitNotify(String busId,String platformCode);
}
