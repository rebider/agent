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
}
