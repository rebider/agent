package com.ryx.credit.pojo.admin.agent;

public class AssProtoColRelKey {
    private String assProtocolId;

    private String agentBusinfoId;

    public String getAssProtocolId() {
        return assProtocolId;
    }

    public void setAssProtocolId(String assProtocolId) {
        this.assProtocolId = assProtocolId == null ? null : assProtocolId.trim();
    }

    public String getAgentBusinfoId() {
        return agentBusinfoId;
    }

    public void setAgentBusinfoId(String agentBusinfoId) {
        this.agentBusinfoId = agentBusinfoId == null ? null : agentBusinfoId.trim();
    }
}