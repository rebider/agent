package com.ryx.credit.profit.pojo;

import java.io.Serializable;

public class TemplateRecode implements Serializable {
    private String id;

    private String agentId;

    private String agentName;

    private String busNum;

    private String busPlatform;

    private String createDate;

    private String applyResult;

    private String templateName;

    private String templateId;

    private String createUser;

    private String assignResult;

    private String assignReason;

    private String creType;

    private String temType;

    private String rev1;

    private String rev2;

    private String busNumS;

    private String changeflag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum == null ? null : busNum.trim();
    }

    public String getBusPlatform() {
        return busPlatform;
    }

    public void setBusPlatform(String busPlatform) {
        this.busPlatform = busPlatform == null ? null : busPlatform.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

    public String getApplyResult() {
        return applyResult;
    }

    public void setApplyResult(String applyResult) {
        this.applyResult = applyResult == null ? null : applyResult.trim();
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getAssignResult() {
        return assignResult;
    }

    public void setAssignResult(String assignResult) {
        this.assignResult = assignResult == null ? null : assignResult.trim();
    }

    public String getAssignReason() {
        return assignReason;
    }

    public void setAssignReason(String assignReason) {
        this.assignReason = assignReason == null ? null : assignReason.trim();
    }

    public String getCreType() {
        return creType;
    }

    public void setCreType(String creType) {
        this.creType = creType == null ? null : creType.trim();
    }

    public String getTemType() {
        return temType;
    }

    public void setTemType(String temType) {
        this.temType = temType == null ? null : temType.trim();
    }

    public String getRev1() {
        return rev1;
    }

    public void setRev1(String rev1) {
        this.rev1 = rev1 == null ? null : rev1.trim();
    }

    public String getRev2() {
        return rev2;
    }

    public void setRev2(String rev2) {
        this.rev2 = rev2 == null ? null : rev2.trim();
    }

    public String getBusNumS() {
        return busNumS;
    }

    public void setBusNumS(String busNumS) {
        this.busNumS = busNumS == null ? null : busNumS.trim();
    }

    public String getChangeflag() {
        return changeflag;
    }

    public void setChangeflag(String changeflag) {
        this.changeflag = changeflag == null ? null : changeflag.trim();
    }
}