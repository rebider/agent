package com.ryx.credit.machine.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ImsTermActive implements Serializable{
    private String posSn;

    private String merId;

    private String termId;

    private String machineId;

    private String activeTime;

    private BigDecimal amt;

    private String updateTime;

    private String status;

    private String amtTime;

    private String insertTime;

    private String serialNo;

    private String ckStatus;

    private String ckResult;

    private String paymentCard;

    private String paymentOpenBank;

    private String paymentTime;

    private BigDecimal posSpePrice;

    private BigDecimal standAmt;

    private String backType;

    public String getPosSn() {
        return posSn;
    }

    public void setPosSn(String posSn) {
        this.posSn = posSn == null ? null : posSn.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId == null ? null : termId.trim();
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId == null ? null : machineId.trim();
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime == null ? null : activeTime.trim();
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getAmtTime() {
        return amtTime;
    }

    public void setAmtTime(String amtTime) {
        this.amtTime = amtTime == null ? null : amtTime.trim();
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime == null ? null : insertTime.trim();
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public String getCkStatus() {
        return ckStatus;
    }

    public void setCkStatus(String ckStatus) {
        this.ckStatus = ckStatus == null ? null : ckStatus.trim();
    }

    public String getCkResult() {
        return ckResult;
    }

    public void setCkResult(String ckResult) {
        this.ckResult = ckResult == null ? null : ckResult.trim();
    }

    public String getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(String paymentCard) {
        this.paymentCard = paymentCard == null ? null : paymentCard.trim();
    }

    public String getPaymentOpenBank() {
        return paymentOpenBank;
    }

    public void setPaymentOpenBank(String paymentOpenBank) {
        this.paymentOpenBank = paymentOpenBank == null ? null : paymentOpenBank.trim();
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime == null ? null : paymentTime.trim();
    }

    public BigDecimal getPosSpePrice() {
        return posSpePrice;
    }

    public void setPosSpePrice(BigDecimal posSpePrice) {
        this.posSpePrice = posSpePrice;
    }

    public BigDecimal getStandAmt() {
        return standAmt;
    }

    public void setStandAmt(BigDecimal standAmt) {
        this.standAmt = standAmt;
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType == null ? null : backType.trim();
    }
}