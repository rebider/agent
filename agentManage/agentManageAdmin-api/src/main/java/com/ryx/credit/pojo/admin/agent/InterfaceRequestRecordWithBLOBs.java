package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;

public class InterfaceRequestRecordWithBLOBs extends InterfaceRequestRecord implements Serializable{
    private String reqJson;

    private String repJson;

    public String getReqJson() {
        return reqJson;
    }

    public void setReqJson(String reqJson) {
        this.reqJson = reqJson == null ? null : reqJson.trim();
    }

    public String getRepJson() {
        return repJson;
    }

    public void setRepJson(String repJson) {
        this.repJson = repJson == null ? null : repJson.trim();
    }
}