package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PmsProfit implements Serializable {
    private String id;

    private String month;

    private String uniqueFlag;

    private String agentId;

    private String busCode;

    private String busName;

    private String sheetName;

    private BigDecimal sheetColumn;

    private String sheetHead;

    private String sheetData;

    private String profitType;

    private String profitHz;

    private String payCondition;

    private String status;

    private String freeReason;

    private BigDecimal payTranMoney;

    private String importTime;

    private String importPerson;

    private String updateTime;

    private String updatePerson;

    private String freeTime;

    private String payTime;

    private BigDecimal sheetOrder;

    private BigDecimal orderNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getUniqueFlag() {
        return uniqueFlag;
    }

    public void setUniqueFlag(String uniqueFlag) {
        this.uniqueFlag = uniqueFlag == null ? null : uniqueFlag.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode == null ? null : busCode.trim();
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName == null ? null : busName.trim();
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

    public String getProfitHz() {
        return profitHz;
    }

    public void setProfitHz(String profitHz) {
        this.profitHz = profitHz == null ? null : profitHz.trim();
    }

    public String getPayCondition() {
        return payCondition;
    }

    public void setPayCondition(String payCondition) {
        this.payCondition = payCondition == null ? null : payCondition.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getFreeReason() {
        return freeReason;
    }

    public void setFreeReason(String freeReason) {
        this.freeReason = freeReason == null ? null : freeReason.trim();
    }

    public BigDecimal getPayTranMoney() {
        return payTranMoney;
    }

    public void setPayTranMoney(BigDecimal payTranMoney) {
        this.payTranMoney = payTranMoney;
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

    public String getFreeTime() {
        return freeTime;
    }

    public void setFreeTime(String freeTime) {
        this.freeTime = freeTime == null ? null : freeTime.trim();
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
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
}