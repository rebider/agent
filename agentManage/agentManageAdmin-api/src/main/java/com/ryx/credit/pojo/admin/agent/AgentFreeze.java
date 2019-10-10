package com.ryx.credit.pojo.admin.agent;

import java.math.BigDecimal;
import java.util.Date;

public class AgentFreeze {
    private String id;

    private String agentId;


    private String freezeStatus;

    private String freezePerson;

    private Date freezeDate;

    private String freezeCause;

    private String unfreezePerson;

    private Date unfreezeDate;

    private String unfreezeCause;

    private String freezeNum;

    private String remark;

    private BigDecimal status;

    private BigDecimal version;

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

    public String getFreezeStatus() {
        return freezeStatus;
    }

    public void setFreezeStatus(String freezeStatus) {
        this.freezeStatus = freezeStatus == null ? null : freezeStatus.trim();
    }

    public String getFreezePerson() {
        return freezePerson;
    }

    public void setFreezePerson(String freezePerson) {
        this.freezePerson = freezePerson == null ? null : freezePerson.trim();
    }

    public Date getFreezeDate() {
        return freezeDate;
    }

    public void setFreezeDate(Date freezeDate) {
        this.freezeDate = freezeDate;
    }

    public String getFreezeCause() {
        return freezeCause;
    }

    public void setFreezeCause(String freezeCause) {
        this.freezeCause = freezeCause == null ? null : freezeCause.trim();
    }

    public String getUnfreezePerson() {
        return unfreezePerson;
    }

    public void setUnfreezePerson(String unfreezePerson) {
        this.unfreezePerson = unfreezePerson == null ? null : unfreezePerson.trim();
    }

    public Date getUnfreezeDate() {
        return unfreezeDate;
    }

    public void setUnfreezeDate(Date unfreezeDate) {
        this.unfreezeDate = unfreezeDate;
    }

    public String getUnfreezeCause() {
        return unfreezeCause;
    }

    public void setUnfreezeCause(String unfreezeCause) {
        this.unfreezeCause = unfreezeCause == null ? null : unfreezeCause.trim();
    }

    public String getFreezeNum() {
        return freezeNum;
    }

    public void setFreezeNum(String freezeNum) {
        this.freezeNum = freezeNum == null ? null : freezeNum.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }
}