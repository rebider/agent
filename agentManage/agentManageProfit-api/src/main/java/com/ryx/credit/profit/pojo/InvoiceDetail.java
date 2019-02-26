package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class InvoiceDetail implements Serializable {
    private String id;

    private String profitMonth;

    private String agentPid;

    private String agentId;

    private String agentName;

    private String fristAgentId;

    private String fristAgentName;

    private String parentAgentId;

    private String parentAgentName;

    private BigDecimal preLeftAmt;

    private BigDecimal dayProfitAmt;

    private BigDecimal dayBackAmt;

    private BigDecimal preProfitMonthAmt;

    private BigDecimal preProfitMonthBlAmt;

    private BigDecimal addInvoiceAmt;

    private BigDecimal adjustAmt;

    private String adjustAccount;

    private String adjustReson;

    private String adjustTime;

    private BigDecimal ownInvoice;

    private BigDecimal taxRate;

    private BigDecimal addTaxAmt;

    private BigDecimal realTaxAmt;

    private BigDecimal notDeductionTaxAmt;

    private BigDecimal preNotDeductionAmt1;

    private String createTime;

    private String updateTime;

    private BigDecimal drAddInvoiceAmt; //线下导入

    private  BigDecimal shAddInvoiceAmt;//线上维护

    public BigDecimal getDrAddInvoiceAmt() {
        return drAddInvoiceAmt;
    }

    public void setDrAddInvoiceAmt(BigDecimal drAddInvoiceAmt) {
        this.drAddInvoiceAmt = drAddInvoiceAmt;
    }

    public BigDecimal getShAddInvoiceAmt() {
        return shAddInvoiceAmt;
    }

    public void setShAddInvoiceAmt(BigDecimal shAddInvoiceAmt) {
        this.shAddInvoiceAmt = shAddInvoiceAmt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProfitMonth() {
        return profitMonth;
    }

    public void setProfitMonth(String profitMonth) {
        this.profitMonth = profitMonth == null ? null : profitMonth.trim();
    }

    public String getAgentPid() {
        return agentPid;
    }

    public void setAgentPid(String agentPid) {
        this.agentPid = agentPid == null ? null : agentPid.trim();
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

    public String getFristAgentId() {
        return fristAgentId;
    }

    public void setFristAgentId(String fristAgentId) {
        this.fristAgentId = fristAgentId == null ? null : fristAgentId.trim();
    }

    public String getFristAgentName() {
        return fristAgentName;
    }

    public void setFristAgentName(String fristAgentName) {
        this.fristAgentName = fristAgentName == null ? null : fristAgentName.trim();
    }

    public String getParentAgentId() {
        return parentAgentId;
    }

    public void setParentAgentId(String parentAgentId) {
        this.parentAgentId = parentAgentId == null ? null : parentAgentId.trim();
    }

    public String getParentAgentName() {
        return parentAgentName;
    }

    public void setParentAgentName(String parentAgentName) {
        this.parentAgentName = parentAgentName == null ? null : parentAgentName.trim();
    }

    public BigDecimal getPreLeftAmt() {
        return preLeftAmt;
    }

    public void setPreLeftAmt(BigDecimal preLeftAmt) {
        this.preLeftAmt = preLeftAmt;
    }

    public BigDecimal getDayProfitAmt() {
        return dayProfitAmt;
    }

    public void setDayProfitAmt(BigDecimal dayProfitAmt) {
        this.dayProfitAmt = dayProfitAmt;
    }

    public BigDecimal getDayBackAmt() {
        return dayBackAmt;
    }

    public void setDayBackAmt(BigDecimal dayBackAmt) {
        this.dayBackAmt = dayBackAmt;
    }

    public BigDecimal getPreProfitMonthAmt() {
        return preProfitMonthAmt;
    }

    public void setPreProfitMonthAmt(BigDecimal preProfitMonthAmt) {
        this.preProfitMonthAmt = preProfitMonthAmt;
    }

    public BigDecimal getPreProfitMonthBlAmt() {
        return preProfitMonthBlAmt;
    }

    public void setPreProfitMonthBlAmt(BigDecimal preProfitMonthBlAmt) {
        this.preProfitMonthBlAmt = preProfitMonthBlAmt;
    }

    public BigDecimal getAddInvoiceAmt() {
        return addInvoiceAmt;
    }

    public void setAddInvoiceAmt(BigDecimal addInvoiceAmt) {
        this.addInvoiceAmt = addInvoiceAmt;
    }

    public BigDecimal getAdjustAmt() {
        return adjustAmt;
    }

    public void setAdjustAmt(BigDecimal adjustAmt) {
        this.adjustAmt = adjustAmt;
    }

    public String getAdjustAccount() {
        return adjustAccount;
    }

    public void setAdjustAccount(String adjustAccount) {
        this.adjustAccount = adjustAccount == null ? null : adjustAccount.trim();
    }

    public String getAdjustReson() {
        return adjustReson;
    }

    public void setAdjustReson(String adjustReson) {
        this.adjustReson = adjustReson == null ? null : adjustReson.trim();
    }

    public String getAdjustTime() {
        return adjustTime;
    }

    public void setAdjustTime(String adjustTime) {
        this.adjustTime = adjustTime == null ? null : adjustTime.trim();
    }

    public BigDecimal getOwnInvoice() {
        return ownInvoice;
    }

    public void setOwnInvoice(BigDecimal ownInvoice) {
        this.ownInvoice = ownInvoice;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getAddTaxAmt() {
        return addTaxAmt;
    }

    public void setAddTaxAmt(BigDecimal addTaxAmt) {
        this.addTaxAmt = addTaxAmt;
    }

    public BigDecimal getRealTaxAmt() {
        return realTaxAmt;
    }

    public void setRealTaxAmt(BigDecimal realTaxAmt) {
        this.realTaxAmt = realTaxAmt;
    }

    public BigDecimal getNotDeductionTaxAmt() {
        return notDeductionTaxAmt;
    }

    public void setNotDeductionTaxAmt(BigDecimal notDeductionTaxAmt) {
        this.notDeductionTaxAmt = notDeductionTaxAmt;
    }

    public BigDecimal getPreNotDeductionAmt1() {
        return preNotDeductionAmt1;
    }

    public void setPreNotDeductionAmt1(BigDecimal preNotDeductionAmt1) {
        this.preNotDeductionAmt1 = preNotDeductionAmt1;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }
}