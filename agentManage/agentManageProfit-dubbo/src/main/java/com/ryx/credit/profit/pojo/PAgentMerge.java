package com.ryx.credit.profit.pojo;

import java.io.Serializable;

public class PAgentMerge implements Serializable{
    private String id;

    private String mainAgentId;

    private String subAgentId;

    private String mergeDate;

    private String mergeStatus;

    private String mainAgentName;

    private String subAgentName;

    private String mainHead;

    private String mainHeadMobile;

    private String subnHead;

    private String subHeadMobile;

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

    public String getMainAgentName() {
        return mainAgentName;
    }

    public void setMainAgentName(String mainAgentName) {
        this.mainAgentName = mainAgentName == null ? null : mainAgentName.trim();
    }

    public String getSubAgentName() {
        return subAgentName;
    }

    public void setSubAgentName(String subAgentName) {
        this.subAgentName = subAgentName == null ? null : subAgentName.trim();
    }

    public String getMainHead() {
        return mainHead;
    }

    public void setMainHead(String mainHead) {
        this.mainHead = mainHead == null ? null : mainHead.trim();
    }

    public String getMainHeadMobile() {
        return mainHeadMobile;
    }

    public void setMainHeadMobile(String mainHeadMobile) {
        this.mainHeadMobile = mainHeadMobile == null ? null : mainHeadMobile.trim();
    }

    public String getSubnHead() {
        return subnHead;
    }

    public void setSubnHead(String subnHead) {
        this.subnHead = subnHead == null ? null : subnHead.trim();
    }

    public String getSubHeadMobile() {
        return subHeadMobile;
    }

    public void setSubHeadMobile(String subHeadMobile) {
        this.subHeadMobile = subHeadMobile == null ? null : subHeadMobile.trim();
    }
}