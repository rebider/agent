package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.AColinfoPayment;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.pojo.admin.agent.AgentColinfoRel;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by cx on 2018/5/28.
 * com.ryx.credit.service.agent.AgentColinfoService
 */
public interface AgentColinfoService {
    /**
     * 代理商入网添加代理商交款项
     * @param ac
     * @param att
     * @return
     * @throws ProcessException
     */
    AgentColinfo agentColinfoInsert(AgentColinfo ac, List<String> att)throws ProcessException;

    AgentResult saveAgentColinfoRel(AgentColinfoRel agentColinfoRel,String cUser)throws Exception;

    public List<AgentColinfo> queryAgentColinfoService(String agentId,String colId,BigDecimal appStatus);

    public int update(AgentColinfo a);

    public ResultVO updateAgentColinfoVo(List<AgentColinfoVo> colinfoVoList, Agent agent,String userId)throws Exception;

    public AgentColinfo queryPoint(AgentColinfo agentColinfo);

    int updateByPrimaryKeySelective(AgentColinfo record);

    public AgentResult checkColInfo(AgentColinfo agentColinfo);

    void insertByPayment(AColinfoPayment colinfoPayment) throws Exception;

    void updateByPaymentResult(AColinfoPayment aColinfoPayment, Map<String, Object> resultMap) throws Exception;

    AgentColinfo selectByPrimaryKey(String id);

    AgentColinfo selectByAgentId(String agentId);

    AgentColinfo selectByAgentIdAndBusId(String agentId,String agentbusId);

    AgentResult updateAgentColinfo(AgentColinfo agentColinfo);

    AgentResult saveAgentColinfo(AgentColinfo agentColinfo);

    AgentResult checkAgentColinfo();
}
