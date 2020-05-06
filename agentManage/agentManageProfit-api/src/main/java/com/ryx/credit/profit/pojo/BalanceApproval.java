package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class BalanceApproval implements Serializable {
    private String balanceId;

    private String batchNo;

    private String settleMonth;

    private String applyDate;

    private String applyUser;

    private String applyTime;

    private String agentId;

    private String agentName;

    private String busCode;

    private String requestNo;

    private BigDecimal balanceAmt;

    private String approvalStatus;

    private String balanceRemark;

    private String balanceAcctNo;

    private String balanceAcctName;

    private String balanceBankNo;

    private String balanceBankName;

    private String realityAgId;

    private String realityAgName;

    private String endTime;

    public String getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(String balanceId) {
        this.balanceId = balanceId == null ? null : balanceId.trim();
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getSettleMonth() {
        return settleMonth;
    }

    public void setSettleMonth(String settleMonth) {
        this.settleMonth = settleMonth == null ? null : settleMonth.trim();
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate == null ? null : applyDate.trim();
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser == null ? null : applyUser.trim();
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime == null ? null : applyTime.trim();
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

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode == null ? null : busCode.trim();
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo == null ? null : requestNo.trim();
    }

    public BigDecimal getBalanceAmt() {
        return balanceAmt;
    }

    public void setBalanceAmt(BigDecimal balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus == null ? null : approvalStatus.trim();
    }

    public String getBalanceRemark() {
        return balanceRemark;
    }

    public void setBalanceRemark(String balanceRemark) {
        this.balanceRemark = balanceRemark == null ? null : balanceRemark.trim();
    }

    public String getBalanceAcctNo() {
        return balanceAcctNo;
    }

    public void setBalanceAcctNo(String balanceAcctNo) {
        this.balanceAcctNo = balanceAcctNo == null ? null : balanceAcctNo.trim();
    }

    public String getBalanceAcctName() {
        return balanceAcctName;
    }

    public void setBalanceAcctName(String balanceAcctName) {
        this.balanceAcctName = balanceAcctName == null ? null : balanceAcctName.trim();
    }

    public String getBalanceBankNo() {
        return balanceBankNo;
    }

    public void setBalanceBankNo(String balanceBankNo) {
        this.balanceBankNo = balanceBankNo == null ? null : balanceBankNo.trim();
    }

    public String getBalanceBankName() {
        return balanceBankName;
    }

    public void setBalanceBankName(String balanceBankName) {
        this.balanceBankName = balanceBankName == null ? null : balanceBankName.trim();
    }

    public String getRealityAgId() {
        return realityAgId;
    }

    public void setRealityAgId(String realityAgId) {
        this.realityAgId = realityAgId == null ? null : realityAgId.trim();
    }

    public String getRealityAgName() {
        return realityAgName;
    }

    public void setRealityAgName(String realityAgName) {
        this.realityAgName = realityAgName == null ? null : realityAgName.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }
}