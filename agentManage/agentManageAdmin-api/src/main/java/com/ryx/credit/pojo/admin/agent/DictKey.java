package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;

public class DictKey implements Serializable {
    private String dGroup;

    private String dArtifact;

    private String dItemvalue;

    public String getdGroup() {
        return dGroup;
    }

    public void setdGroup(String dGroup) {
        this.dGroup = dGroup == null ? null : dGroup.trim();
    }

    public String getdArtifact() {
        return dArtifact;
    }

    public void setdArtifact(String dArtifact) {
        this.dArtifact = dArtifact == null ? null : dArtifact.trim();
    }

    public String getdItemvalue() {
        return dItemvalue;
    }

    public void setdItemvalue(String dItemvalue) {
        this.dItemvalue = dItemvalue == null ? null : dItemvalue.trim();
    }
}