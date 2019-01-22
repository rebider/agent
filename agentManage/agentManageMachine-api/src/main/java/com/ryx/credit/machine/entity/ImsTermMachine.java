package com.ryx.credit.machine.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ImsTermMachine implements Serializable{
    private String machineId;

    private String model;

    private BigDecimal price;

    private BigDecimal minOrdQty;

    private String status;

    private String remark;

    private String createTime;

    private String createPerson;

    private String updateTime;

    private String updatePerson;

    private String termType;

    private String commType;

    private String machineManuf;

    private String isOuterPinpad;

    private String isNonConn;

    private String isElectSign;

    private BigDecimal deposit;

    private BigDecimal standAmt;

    private String machineType;

    private String backType;

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId == null ? null : machineId.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMinOrdQty() {
        return minOrdQty;
    }

    public void setMinOrdQty(BigDecimal minOrdQty) {
        this.minOrdQty = minOrdQty;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson == null ? null : createPerson.trim();
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

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType == null ? null : termType.trim();
    }

    public String getCommType() {
        return commType;
    }

    public void setCommType(String commType) {
        this.commType = commType == null ? null : commType.trim();
    }

    public String getMachineManuf() {
        return machineManuf;
    }

    public void setMachineManuf(String machineManuf) {
        this.machineManuf = machineManuf == null ? null : machineManuf.trim();
    }

    public String getIsOuterPinpad() {
        return isOuterPinpad;
    }

    public void setIsOuterPinpad(String isOuterPinpad) {
        this.isOuterPinpad = isOuterPinpad == null ? null : isOuterPinpad.trim();
    }

    public String getIsNonConn() {
        return isNonConn;
    }

    public void setIsNonConn(String isNonConn) {
        this.isNonConn = isNonConn == null ? null : isNonConn.trim();
    }

    public String getIsElectSign() {
        return isElectSign;
    }

    public void setIsElectSign(String isElectSign) {
        this.isElectSign = isElectSign == null ? null : isElectSign.trim();
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getStandAmt() {
        return standAmt;
    }

    public void setStandAmt(BigDecimal standAmt) {
        this.standAmt = standAmt;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType;
    }
}