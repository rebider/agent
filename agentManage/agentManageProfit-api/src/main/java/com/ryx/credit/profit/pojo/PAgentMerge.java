package com.ryx.credit.profit.pojo;

public class PAgentMerge {
    private String id;

    private String mainAgentId;

    private String subAgentId;

    private String mergeDate;

    private String mergeStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMainAgentId() {
        return mainAgentId;
    }

    public void setMainAgentId(String mainAgentId) {
        this.mainAgentId = mainAgentId == null ? null : mainAgentId.trim();
    }

    public String getSubAgentId() {
        return subAgentId;
    }

    public void setSubAgentId(String subAgentId) {
        this.subAgentId = subAgentId == null ? null : subAgentId.trim();
    }

    public String getMergeDate() {
        return mergeDate;
    }

    public void setMergeDate(String mergeDate) {
        this.mergeDate = mergeDate == null ? null : mergeDate.trim();
    }

    public String getMergeStatus() {
        return mergeStatus;
    }

    public void setMergeStatus(String mergeStatus) {
        this.mergeStatus = mergeStatus == null ? null : mergeStatus.trim();
    }
}