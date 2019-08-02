package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class InvoiceApply implements Serializable {
    private String id;

    private String invoiceCompany;

    private String invoiceDate;

    private String invoiceNumber;

    private String invoiceCode;

    private String invoiceItem;

    private BigDecimal unitPrice;

    private Long numberSl;

    private BigDecimal sumAmt;

    private BigDecimal tax;

    private String expressCompany;

    private String expressNumber;

    private String expressDate;

    private String expressRemark;

    private String ysDate;

    private String esDate;

    private String ysResult;

    private String returnReason;

    private String returnDate;

    private String returnExpressNumber;

    private String returnExpressCompany;

    private String createDate;

    private String createName;

    private String agentId;

    private String agentName;

    private String invoiceAmtMonth;

    private String esResult;

    private BigDecimal amountTax;

    private String status;

    private String serialNo;

    private BigDecimal amount;

    private String expenseStatus;

    private String invoiceType;

    private String sallerName;

    private String sallerNo;

    private String remark;

    private String profitMonth;

    private String rev1;

    private String rev2;

    private String rev3;

    private String rev4;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getInvoiceCompany() {
        return invoiceCompany;
    }

    public void setInvoiceCompany(String invoiceCompany) {
        this.invoiceCompany = invoiceCompany == null ? null : invoiceCompany.trim();
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate == null ? null : invoiceDate.trim();
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber == null ? null : invoiceNumber.trim();
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode == null ? null : invoiceCode.trim();
    }

    public String getInvoiceItem() {
        return invoiceItem;
    }

    public void setInvoiceItem(String invoiceItem) {
        this.invoiceItem = invoiceItem == null ? null : invoiceItem.trim();
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getNumberSl() {
        return numberSl;
    }

    public void setNumberSl(Long numberSl) {
        this.numberSl = numberSl;
    }

    public BigDecimal getSumAmt() {
        return sumAmt;
    }

    public void setSumAmt(BigDecimal sumAmt) {
        this.sumAmt = sumAmt;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany == null ? null : expressCompany.trim();
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber == null ? null : expressNumber.trim();
    }

    public String getExpressDate() {
        return expressDate;
    }

    public void setExpressDate(String expressDate) {
        this.expressDate = expressDate == null ? null : expressDate.trim();
    }

    public String getExpressRemark() {
        return expressRemark;
    }

    public void setExpressRemark(String expressRemark) {
        this.expressRemark = expressRemark == null ? null : expressRemark.trim();
    }

    public String getYsDate() {
        return ysDate;
    }

    public void setYsDate(String ysDate) {
        this.ysDate = ysDate == null ? null : ysDate.trim();
    }

    public String getEsDate() {
        return esDate;
    }

    public void setEsDate(String esDate) {
        this.esDate = esDate == null ? null : esDate.trim();
    }

    public String getYsResult() {
        return ysResult;
    }

    public void setYsResult(String ysResult) {
        this.ysResult = ysResult == null ? null : ysResult.trim();
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason == null ? null : returnReason.trim();
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate == null ? null : returnDate.trim();
    }

    public String getReturnExpressNumber() {
        return returnExpressNumber;
    }

    public void setReturnExpressNumber(String returnExpressNumber) {
        this.returnExpressNumber = returnExpressNumber == null ? null : returnExpressNumber.trim();
    }

    public String getReturnExpressCompany() {
        return returnExpressCompany;
    }

    public void setReturnExpressCompany(String returnExpressCompany) {
        this.returnExpressCompany = returnExpressCompany == null ? null : returnExpressCompany.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
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

    public String getInvoiceAmtMonth() {
        return invoiceAmtMonth;
    }

    public void setInvoiceAmtMonth(String invoiceAmtMonth) {
        this.invoiceAmtMonth = invoiceAmtMonth == null ? null : invoiceAmtMonth.trim();
    }

    public String getEsResult() {
        return esResult;
    }

    public void setEsResult(String esResult) {
        this.esResult = esResult == null ? null : esResult.trim();
    }

    public BigDecimal getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(BigDecimal amountTax) {
        this.amountTax = amountTax;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getExpenseStatus() {
        return expenseStatus;
    }

    public void setExpenseStatus(String expenseStatus) {
        this.expenseStatus = expenseStatus == null ? null : expenseStatus.trim();
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    public String getSallerName() {
        return sallerName;
    }

    public void setSallerName(String sallerName) {
        this.sallerName = sallerName == null ? null : sallerName.trim();
    }

    public String getSallerNo() {
        return sallerNo;
    }

    public void setSallerNo(String sallerNo) {
        this.sallerNo = sallerNo == null ? null : sallerNo.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getProfitMonth() {
        return profitMonth;
    }

    public void setProfitMonth(String profitMonth) {
        this.profitMonth = profitMonth == null ? null : profitMonth.trim();
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

    public String getRev4() {
        return rev4;
    }

    public void setRev4(String rev4) {
        this.rev4 = rev4 == null ? null : rev4.trim();
    }
}