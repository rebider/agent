package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class InvoiceSum implements Serializable {
    private String id;

    private String profitMonth;

    private String agentName;

    private String agentId;

    private String invoiceCompany;

    private BigDecimal preLeftAmt;

    private BigDecimal dayProfitAmt;

    private BigDecimal dayBackAmt;

    private BigDecimal preProfitMonthAmt;

    private BigDecimal addInvoiceAmt;

    private BigDecimal ownInvoice;

    private BigDecimal adjustAmt;

    private String adjustAccount;

    private String adjustReson;

    private String adjustTime;

    private String topOrgId;

    private String topOrgName;

    private String invoiceStatus;

    private BigDecimal subAddInvoiceAmt;

    private String rev1;

    private String rev2;

    private String rev3;

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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getInvoiceCompany() {
        return invoiceCompany;
    }

    public void setInvoiceCompany(String invoiceCompany) {
        this.invoiceCompany = invoiceCompany == null ? null : invoiceCompany.trim();
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

    public BigDecimal getAddInvoiceAmt() {
        return addInvoiceAmt;
    }

    public void setAddInvoiceAmt(BigDecimal addInvoiceAmt) {
        this.addInvoiceAmt = addInvoiceAmt;
    }

    public BigDecimal getOwnInvoice() {
        return ownInvoice;
    }

    public void setOwnInvoice(BigDecimal ownInvoice) {
        this.ownInvoice = ownInvoice;
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

    public String getTopOrgId() {
        return topOrgId;
    }

    public void setTopOrgId(String topOrgId) {
        this.topOrgId = topOrgId == null ? null : topOrgId.trim();
    }

    public String getTopOrgName() {
        return topOrgName;
    }

    public void setTopOrgName(String topOrgName) {
        this.topOrgName = topOrgName == null ? null : topOrgName.trim();
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus == null ? null : invoiceStatus.trim();
    }

    public BigDecimal getSubAddInvoiceAmt() {
        return subAddInvoiceAmt;
    }

    public void setSubAddInvoiceAmt(BigDecimal subAddInvoiceAmt) {
        this.subAddInvoiceAmt = subAddInvoiceAmt;
    }

    public String getRev1() {
        return rev1;
    }

    public void setRev1(String rev1) {
        this.rev1 = rev1 == null ? null : rev1.trim();
    }

    public String getRev2() {
        return rev2;
    }

    public void setRev2(String rev2) {
        this.rev2 = rev2 == null ? null : rev2.trim();
    }

    public String getRev3() {
        return rev3;
    }

    public void setRev3(String rev3) {
        this.rev3 = rev3 == null ? null : rev3.trim();
    }
}