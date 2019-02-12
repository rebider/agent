package com.ryx.credit.profit.pojo;

import java.io.Serializable;

public class PosHuddleRewardDetail implements Serializable {
    private String posAgentId;

    private String posAgentName;

    private String huddleCode;

    public String getPosAgentId() {
        return posAgentId;
    }

    public void setPosAgentId(String posAgentId) {
        this.posAgentId = posAgentId == null ? null : posAgentId.trim();
    }

    public String getPosAgentName() {
        return posAgentName;
    }

    public void setPosAgentName(String posAgentName) {
        this.posAgentName = posAgentName == null ? null : posAgentName.trim();
    }

    public String getHuddleCode() {
        return huddleCode;
    }

    public void setHuddleCode(String huddleCode) {
        this.huddleCode = huddleCode == null ? null : huddleCode.trim();
    }
}