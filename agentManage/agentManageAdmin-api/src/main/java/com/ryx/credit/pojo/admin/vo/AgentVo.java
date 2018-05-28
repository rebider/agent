package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.agent.Agent;

import java.util.List;

/**
 * Created by cx on 2018/5/28.
 */
public class AgentVo {

    private Agent agent;
    private List<CapitalVo> capitalVoList;
    private List<AgentContractVo> contractVoList;
    private List<AgentColinfoVo> colinfoVoList;
    private List<AgentBusInfoVo> busInfoVoList;
    private List<String> agentTableFile;
    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public List<CapitalVo> getCapitalVoList() {
        return capitalVoList;
    }

    public void setCapitalVoList(List<CapitalVo> capitalVoList) {
        this.capitalVoList = capitalVoList;
    }

    public List<AgentContractVo> getContractVoList() {
        return contractVoList;
    }

    public void setContractVoList(List<AgentContractVo> contractVoList) {
        this.contractVoList = contractVoList;
    }

    public List<AgentColinfoVo> getColinfoVoList() {
        return colinfoVoList;
    }

    public void setColinfoVoList(List<AgentColinfoVo> colinfoVoList) {
        this.colinfoVoList = colinfoVoList;
    }

    public List<AgentBusInfoVo> getBusInfoVoList() {
        return busInfoVoList;
    }

    public void setBusInfoVoList(List<AgentBusInfoVo> busInfoVoList) {
        this.busInfoVoList = busInfoVoList;
    }

    public List<String> getAgentTableFile() {
        return agentTableFile;
    }

    public void setAgentTableFile(List<String> agentTableFile) {
        this.agentTableFile = agentTableFile;
    }
}
