package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.pojo.admin.agent.AgentContract;

import java.math.BigDecimal;
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


    /**
     * 删除合同
     * @param id
     * @return
     */
    public int removeAgentContract(String id);


    /**
     * 查询代理商的合同信息
     * @param agentId
     * @param contractId
     * @param approveStatus
     * @return
     */
    public List<AgentContract> queryAgentContract(String agentId, String contractId,BigDecimal approveStatus);



    public int update(AgentContract a);


}
