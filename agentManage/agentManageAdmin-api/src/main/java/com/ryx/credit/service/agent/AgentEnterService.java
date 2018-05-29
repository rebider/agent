package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.vo.AgentVo;

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
}
