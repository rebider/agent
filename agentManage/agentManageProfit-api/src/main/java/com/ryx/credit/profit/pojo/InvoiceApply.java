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

    private String attachment;

    private String shDate;

    private String shName;

    private BigDecimal currentInvoice;

    private String shResult;

    private String returnReason;

    private String returnDate;

    private String returnExpressNumber;

    private String returnExpressCompany;

    private String createDate;

    private String createName;

    private String filename;

    private String agentId;

    private String agentName;

    private String profitMonth;

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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }

    public String getShDate() {
        return shDate;
    }

    public void setShDate(String shDate) {
        this.shDate = shDate == null ? null : shDate.trim();
    }

    public String getShName() {
        return shName;
    }

    public void setShName(String shName) {
        this.shName = shName == null ? null : shName.trim();
    }

    public BigDecimal getCurrentInvoice() {
        return currentInvoice;
    }

    public void setCurrentInvoice(BigDecimal currentInvoice) {
        this.currentInvoice = currentInvoice;
    }

    public String getShResult() {
        return shResult;
    }

    public void setShResult(String shResult) {
        this.shResult = shResult == null ? null : shResult.trim();
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
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

    public String getProfitMonth() {
        return profitMonth;
    }

    public void setProfitMonth(String profitMonth) {
        this.profitMonth = profitMonth == null ? null : profitMonth.trim();
    }
}