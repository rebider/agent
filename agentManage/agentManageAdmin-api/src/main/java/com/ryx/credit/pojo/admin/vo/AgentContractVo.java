package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.agent.AgentContract;

import java.util.List;

/**
 * Created by cx on 2018/5/28.
 */
public class AgentContractVo extends AgentContract {

    private List<String> contractTableFile;

    private String agentAssProtocol;

    private String protocolRuleValue;

    public List<String> getContractTableFile() {
        return contractTableFile;
    }

    public void setContractTableFile(List<String> contractTableFile) {
        this.contractTableFile = contractTableFile;
    }

    public String getAgentAssProtocol() {
        return agentAssProtocol;
    }

    public void setAgentAssProtocol(String agentAssProtocol) {
        this.agentAssProtocol = agentAssProtocol;
    }

    public String getProtocolRuleValue() {
        return protocolRuleValue;
    }

    public void setProtocolRuleValue(String protocolRuleValue) {
        this.protocolRuleValue = protocolRuleValue;
    }
}
