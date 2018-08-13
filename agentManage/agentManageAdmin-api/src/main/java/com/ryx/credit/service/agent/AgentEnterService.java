package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.AgentoutVo;
import sun.management.resources.agent;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by cx on 2018/5/28.
 */
public interface AgentEnterService {
    /**
     * 商户入网
     * @param agentVo
     * @return
     */
    public ResultVO agentEnterIn(AgentVo agentVo)throws ProcessException;


    /**
     * 启动一个代理商入网审批
     * @param agentId
     * @param cuser
     * @return
     * @throws ProcessException
     */
    public ResultVO startAgentEnterActivity(String agentId,String cuser)throws ProcessException;


    /**
     * 启动代理商新签业务审批
     * @param agentId
     * @param cuser
     * @return
     * @throws ProcessException
     */
    public ResultVO startAgentBusiEnterActivity(String agentId,String cuser)throws ProcessException;


    /**
     * 提交审批
     * @param agentVo
     * @return
     * @throws ProcessException
     */
    AgentResult completeTaskEnterActivity(AgentVo agentVo,String userId)throws ProcessException;


    /**
     * 完成流程
     * @param processingId
     * @return
     * @throws ProcessException
     */
    ResultVO completeProcessing(String processingId,String processingStatus)throws ProcessException;


    /**
     * 代理商修改
     * @param agent
     * @param userId
     * @return
     */
    public ResultVO updateAgentVo(AgentVo agent,String userId)throws Exception;


    public Map startPar(String cuserId);

    /**
     * 代理商入网导出
     */

    public List<AgentoutVo> exportAgent(Agent agent) throws ParseException;

}
