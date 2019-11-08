package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;

public class AnnoPlatformRela implements Serializable {
    private String id;

    private String annoId;

    private String rangType;

    private String rangValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAnnoId() {
        return annoId;
    }

    public void setAnnoId(String annoId) {
        this.annoId = annoId == null ? null : annoId.trim();
    }

    public String getRangType() {
        return rangType;
    }

    public void setRangType(String rangType) {
        this.rangType = rangType == null ? null : rangType.trim();
    }

    public String getRangValue() {
        return rangValue;
    }

    public void setRangValue(String rangValue) {
        this.rangValue = rangValue == null ? null : rangValue.trim();
    }
}