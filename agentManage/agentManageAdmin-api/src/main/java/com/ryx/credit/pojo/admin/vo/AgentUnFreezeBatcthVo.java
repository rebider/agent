package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;
import java.util.List;

public class AgentUnFreezeBatcthVo extends AgentFreezePort implements Serializable {
    private List<String> freezeList;

    public List<String> getFreezeList() {
        return freezeList;
    }

    public void setFreezeList(List<String> freezeList) {
        this.freezeList = freezeList;
    }
}
