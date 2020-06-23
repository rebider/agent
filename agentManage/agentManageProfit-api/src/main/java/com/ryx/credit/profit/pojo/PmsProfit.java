package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PmsProfit implements Serializable {
    private String balanceId;

    private String settleMonth;

    private String uniqueFlag;

    private String agentName;

    private String busCode;

    private String sheetName;

    private BigDecimal sheetColumn;

    private String sheetHead;

    private String sheetData;

    private String profitType;

    private String billStatus;

    private BigDecimal balanceAmt;

    private String importTime;

    private String importPerson;

    private String updateTime;

    private String updatePerson;

    private BigDecimal sheetOrder;

    private BigDecimal orderNumber;

    private String batchNo;

    private String remitFailReason;

    private String balanceBankNo;

    private String balanceBankName;

    private String balanceAcctName;

    private String balanceAcctNo;

    private String sunbmitCheckTime;

    private String sunbmitRemitTime;

    private String settleDate;

    private String importBatch;

    private String realityAgId;

    private String realityAgName;

    private String remark;

    private String orgId;

    private String renitStatus;

    public String getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(String balanceId) {
        this.balanceId = balanceId == null ? null : balanceId.trim();
    }

    public String getSettleMonth() {
        return settleMonth;
    }

    public void setSettleMonth(String settleMonth) {
        this.settleMonth = settleMonth == null ? null : settleMonth.trim();
    }

    public String getUniqueFlag() {
        return uniqueFlag;
    }

    public void setUniqueFlag(String uniqueFlag) {
        this.uniqueFlag = uniqueFlag == null ? null : uniqueFlag.trim();
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

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName == null ? null : sheetName.trim();
    }

    public BigDecimal getSheetColumn() {
        return sheetColumn;
    }

    public void setSheetColumn(BigDecimal sheetColumn) {
        this.sheetColumn = sheetColumn;
    }

    public String getSheetHead() {
        return sheetHead;
    }

    public void setSheetHead(String sheetHead) {
        this.sheetHead = sheetHead;
    }

    public String getSheetData() {
        return sheetData;
    }

    public void setSheetData(String sheetData) {
        this.sheetData = sheetData;
    }

    public String getProfitType() {
        return profitType;
    }

    public void setProfitType(String profitType) {
        this.profitType = profitType == null ? null : profitType.trim();
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus == null ? null : billStatus.trim();
    }

    public BigDecimal getBalanceAmt() {
        return balanceAmt;
    }

    public void setBalanceAmt(BigDecimal balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

    public String getImportTime() {
        return importTime;
    }

    public void setImportTime(String importTime) {
        this.importTime = importTime == null ? null : importTime.trim();
    }

    public String getImportPerson() {
        return importPerson;
    }

    public void setImportPerson(String importPerson) {
        this.importPerson = importPerson == null ? null : importPerson.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson == null ? null : updatePerson.trim();
    }

    public BigDecimal getSheetOrder() {
        return sheetOrder;
    }

    public void setSheetOrder(BigDecimal sheetOrder) {
        this.sheetOrder = sheetOrder;
    }

    public BigDecimal getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(BigDecimal orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getRemitFailReason() {
        return remitFailReason;
    }

    public void setRemitFailReason(String remitFailReason) {
        this.remitFailReason = remitFailReason == null ? null : remitFailReason.trim();
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

    public String getBalanceAcctName() {
        return balanceAcctName;
    }

    public void setBalanceAcctName(String balanceAcctName) {
        this.balanceAcctName = balanceAcctName == null ? null : balanceAcctName.trim();
    }

    public String getBalanceAcctNo() {
        return balanceAcctNo;
    }

    public void setBalanceAcctNo(String balanceAcctNo) {
        this.balanceAcctNo = balanceAcctNo == null ? null : balanceAcctNo.trim();
    }

    public String getSunbmitCheckTime() {
        return sunbmitCheckTime;
    }

    public void setSunbmitCheckTime(String sunbmitCheckTime) {
        this.sunbmitCheckTime = sunbmitCheckTime == null ? null : sunbmitCheckTime.trim();
    }

    public String getSunbmitRemitTime() {
        return sunbmitRemitTime;
    }

    public void setSunbmitRemitTime(String sunbmitRemitTime) {
        this.sunbmitRemitTime = sunbmitRemitTime == null ? null : sunbmitRemitTime.trim();
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate == null ? null : settleDate.trim();
    }

    public String getImportBatch() {
        return importBatch;
    }

    public void setImportBatch(String importBatch) {
        this.importBatch = importBatch == null ? null : importBatch.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getRenitStatus() {
        return renitStatus;
    }

    public void setRenitStatus(String renitStatus) {
        this.renitStatus = renitStatus == null ? null : renitStatus.trim();
    }
}