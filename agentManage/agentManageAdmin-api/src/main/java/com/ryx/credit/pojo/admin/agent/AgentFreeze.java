package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AgentFreeze implements Serializable{

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

    private BigDecimal freezeType;

    private String agentName;

    private String incomTimeBegin;

    private String incomTimeEnd;

    private String freezeDateBegin;

    private String freezeDateEnd;

    private String unFreezeDateBegin;

    private String unFreezeDateEnd;

    private String freeStatus;


    public String getIncomTimeBegin() {
        return incomTimeBegin;
    }

    public void setIncomTimeBegin(String incomTimeBegin) {
        this.incomTimeBegin = incomTimeBegin;
    }

    public String getIncomTimeEnd() {
        return incomTimeEnd;
    }

    public void setIncomTimeEnd(String incomTimeEnd) {
        this.incomTimeEnd = incomTimeEnd;
    }

    public String getFreezeDateBegin() {
        return freezeDateBegin;
    }

    public void setFreezeDateBegin(String freezeDateBegin) {
        this.freezeDateBegin = freezeDateBegin;
    }

    public String getFreezeDateEnd() {
        return freezeDateEnd;
    }

    public void setFreezeDateEnd(String freezeDateEnd) {
        this.freezeDateEnd = freezeDateEnd;
    }

    public String getUnFreezeDateBegin() {
        return unFreezeDateBegin;
    }

    public void setUnFreezeDateBegin(String unFreezeDateBegin) {
        this.unFreezeDateBegin = unFreezeDateBegin;
    }

    public String getUnFreezeDateEnd() {
        return unFreezeDateEnd;
    }

    public void setUnFreezeDateEnd(String unFreezeDateEnd) {
        this.unFreezeDateEnd = unFreezeDateEnd;
    }

    public String getFreeStatus() {
        return freeStatus;
    }

    public void setFreeStatus(String freeStatus) {
        this.freeStatus = freeStatus;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    private String busPlatform;

    private String busNum;

    private BigDecimal busFreeze;

    private BigDecimal profitFreeze;

    private BigDecimal reflowFreeze;

    private BigDecimal monthlyFreeze;

    private BigDecimal dailyFreeze;

    private BigDecimal stopProfitFreeze;

    private BigDecimal cashFreeze;

    private BigDecimal stopCount;

    private String busId;

    private BigDecimal newBusFreeze;

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

    public BigDecimal getFreezeType() {
        return freezeType;
    }

    public void setFreezeType(BigDecimal freezeType) {
        this.freezeType = freezeType;
    }

    public String getBusPlatform() {
        return busPlatform;
    }

    public void setBusPlatform(String busPlatform) {
        this.busPlatform = busPlatform == null ? null : busPlatform.trim();
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum == null ? null : busNum.trim();
    }

    public BigDecimal getBusFreeze() {
        return busFreeze;
    }

    public void setBusFreeze(BigDecimal busFreeze) {
        this.busFreeze = busFreeze;
    }

    public BigDecimal getProfitFreeze() {
        return profitFreeze;
    }

    public void setProfitFreeze(BigDecimal profitFreeze) {
        this.profitFreeze = profitFreeze;
    }

    public BigDecimal getReflowFreeze() {
        return reflowFreeze;
    }

    public void setReflowFreeze(BigDecimal reflowFreeze) {
        this.reflowFreeze = reflowFreeze;
    }

    public BigDecimal getMonthlyFreeze() {
        return monthlyFreeze;
    }

    public void setMonthlyFreeze(BigDecimal monthlyFreeze) {
        this.monthlyFreeze = monthlyFreeze;
    }

    public BigDecimal getDailyFreeze() {
        return dailyFreeze;
    }

    public void setDailyFreeze(BigDecimal dailyFreeze) {
        this.dailyFreeze = dailyFreeze;
    }

    public BigDecimal getStopProfitFreeze() {
        return stopProfitFreeze;
    }

    public void setStopProfitFreeze(BigDecimal stopProfitFreeze) {
        this.stopProfitFreeze = stopProfitFreeze;
    }

    public BigDecimal getCashFreeze() {
        return cashFreeze;
    }

    public void setCashFreeze(BigDecimal cashFreeze) {
        this.cashFreeze = cashFreeze;
    }

    public BigDecimal getStopCount() {
        return stopCount;
    }

    public void setStopCount(BigDecimal stopCount) {
        this.stopCount = stopCount;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId == null ? null : busId.trim();
    }

    public BigDecimal getNewBusFreeze() {
        return newBusFreeze;
    }

    public void setNewBusFreeze(BigDecimal newBusFreeze) {
        this.newBusFreeze = newBusFreeze;
    }
}