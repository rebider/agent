package com.ryx.credit.pojo.admin.order;

import java.math.BigDecimal;
import java.util.Date;

public class CashSummaryMouth extends CashSummaryMouthKey {
    private BigDecimal payAmount;

    private String busPlatform;

    private String busNum;

    private String agentId;

    private String agUniqNum;

    private String busParentId;

    private String busParentBusNum;

    private String busParentAgentId;

    private Date cDate;

    private BigDecimal status;

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
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

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getAgUniqNum() {
        return agUniqNum;
    }

    public void setAgUniqNum(String agUniqNum) {
        this.agUniqNum = agUniqNum == null ? null : agUniqNum.trim();
    }

    public String getBusParentId() {
        return busParentId;
    }

    public void setBusParentId(String busParentId) {
        this.busParentId = busParentId == null ? null : busParentId.trim();
    }

    public String getBusParentBusNum() {
        return busParentBusNum;
    }

    public void setBusParentBusNum(String busParentBusNum) {
        this.busParentBusNum = busParentBusNum == null ? null : busParentBusNum.trim();
    }

    public String getBusParentAgentId() {
        return busParentAgentId;
    }

    public void setBusParentAgentId(String busParentAgentId) {
        this.busParentAgentId = busParentAgentId == null ? null : busParentAgentId.trim();
    }

    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }
}