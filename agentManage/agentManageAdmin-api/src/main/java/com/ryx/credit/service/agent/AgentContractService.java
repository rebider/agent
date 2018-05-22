package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.pojo.admin.agent.AgentContract;

import java.util.List;

/**
 * 代理商合同信息
 * Created by cx on 2018/5/22.
 */
public interface AgentContractService {


    /**
     * 添加合同信息
     * @param contract
     * @param attr
     * @return
     */
    public AgentContract insertAgentContract(AgentContract contract, List<String> attr)throws ProcessException;


}
