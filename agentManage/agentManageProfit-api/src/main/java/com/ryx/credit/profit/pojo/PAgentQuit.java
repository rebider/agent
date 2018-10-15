package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PAgentQuit implements Serializable{
    private static final long serialVersionUID = 3587825133059793912L;

    private String id;

    private String agentId;

    private String applyPlat;

    private String ketubbahId;

    private BigDecimal debtAmt;

    private BigDecimal debtBill;

    private BigDecimal supplyDebtAmt;

    private BigDecimal supplyDebtBill;

    private String supplyType;

    private String payCompany;

    private String refundStatus;

    private String flowStatus;

    private String createDate;

    private String applyUser;

    private String passDate;

    private BigDecimal resultAmt;

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
        this.agentId = agentId;
    }

    public String getApplyPlat() {
        return applyPlat;
    }

    public void setApplyPlat(String applyPlat) {
        this.applyPlat = applyPlat == null ? null : applyPlat.trim();
    }

    public String getKetubbahId() {
        return ketubbahId;
    }

    public void setKetubbahId(String ketubbahId) {
        this.ketubbahId = ketubbahId == null ? null : ketubbahId.trim();
    }

    public BigDecimal getDebtAmt() {
        return debtAmt;
    }

    public void setDebtAmt(BigDecimal debtAmt) {
        this.debtAmt = debtAmt;
    }

    public BigDecimal getDebtBill() {
        return debtBill;
    }

    public void setDebtBill(BigDecimal debtBill) {
        this.debtBill = debtBill;
    }

    public BigDecimal getSupplyDebtAmt() {
        return supplyDebtAmt;
    }

    public void setSupplyDebtAmt(BigDecimal supplyDebtAmt) {
        this.supplyDebtAmt = supplyDebtAmt;
    }

    public BigDecimal getSupplyDebtBill() {
        return supplyDebtBill;
    }

    public void setSupplyDebtBill(BigDecimal supplyDebtBill) {
        this.supplyDebtBill = supplyDebtBill;
    }

    public String getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(String supplyType) {
        this.supplyType = supplyType == null ? null : supplyType.trim();
    }

    public String getPayCompany() {
        return payCompany;
    }

    public void setPayCompany(String payCompany) {
        this.payCompany = payCompany == null ? null : payCompany.trim();
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus == null ? null : refundStatus.trim();
    }

    public String getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus == null ? null : flowStatus.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getPassDate() {
        return passDate;
    }

    public void setPassDate(String passDate) {
        this.passDate = passDate == null ? null : passDate.trim();
    }

    public BigDecimal getResultAmt() {
        return resultAmt;
    }

    public void setResultAmt(BigDecimal resultAmt) {
        this.resultAmt = resultAmt;
    }
}