package com.ryx.credit.machine.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ImsOrganReturnTemplate implements Serializable{
    private String templateId;

    private String orgId;

    private String activityId;

    private String activeTemplateType;

    private BigDecimal activeBackAmount;

    private BigDecimal activeBackPercentage;

    private String standTemplateType;

    private BigDecimal standBackAmount;

    private BigDecimal standBackPercentage;

    private String bonusTemplateType;

    private BigDecimal bonusBackAmount;

    private BigDecimal bonusBackPercentage;

    private String status;

    private String remark;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public String getActiveTemplateType() {
        return activeTemplateType;
    }

    public void setActiveTemplateType(String activeTemplateType) {
        this.activeTemplateType = activeTemplateType == null ? null : activeTemplateType.trim();
    }

    public BigDecimal getActiveBackAmount() {
        return activeBackAmount;
    }

    public void setActiveBackAmount(BigDecimal activeBackAmount) {
        this.activeBackAmount = activeBackAmount;
    }

    public BigDecimal getActiveBackPercentage() {
        return activeBackPercentage;
    }

    public void setActiveBackPercentage(BigDecimal activeBackPercentage) {
        this.activeBackPercentage = activeBackPercentage;
    }

    public String getStandTemplateType() {
        return standTemplateType;
    }

    public void setStandTemplateType(String standTemplateType) {
        this.standTemplateType = standTemplateType == null ? null : standTemplateType.trim();
    }

    public BigDecimal getStandBackAmount() {
        return standBackAmount;
    }

    public void setStandBackAmount(BigDecimal standBackAmount) {
        this.standBackAmount = standBackAmount;
    }

    public BigDecimal getStandBackPercentage() {
        return standBackPercentage;
    }

    public void setStandBackPercentage(BigDecimal standBackPercentage) {
        this.standBackPercentage = standBackPercentage;
    }

    public String getBonusTemplateType() {
        return bonusTemplateType;
    }

    public void setBonusTemplateType(String bonusTemplateType) {
        this.bonusTemplateType = bonusTemplateType == null ? null : bonusTemplateType.trim();
    }

    public BigDecimal getBonusBackAmount() {
        return bonusBackAmount;
    }

    public void setBonusBackAmount(BigDecimal bonusBackAmount) {
        this.bonusBackAmount = bonusBackAmount;
    }

    public BigDecimal getBonusBackPercentage() {
        return bonusBackPercentage;
    }

    public void setBonusBackPercentage(BigDecimal bonusBackPercentage) {
        this.bonusBackPercentage = bonusBackPercentage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}