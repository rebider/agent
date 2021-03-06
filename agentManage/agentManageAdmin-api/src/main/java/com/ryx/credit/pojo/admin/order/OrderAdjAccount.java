package com.ryx.credit.pojo.admin.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ryx.credit.common.util.DateJsonDeserializer;
import com.ryx.credit.common.util.DateJsonSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderAdjAccount implements Serializable {
    private String id;

    private String adjId;

    private String orderId;

    private BigDecimal type;

    private String refundAccount;

    private String accountName;

    private String accountBank;

    private String branchLineNum;

    private String allLineNum;

    private BigDecimal refundAmo;
    @JSONField(format="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date refundTm;

    private BigDecimal refundStat;

    private BigDecimal status;

    private BigDecimal version;

    private String refundCompany;

    private String refundUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAdjId() {
        return adjId;
    }

    public void setAdjId(String adjId) {
        this.adjId = adjId == null ? null : adjId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public BigDecimal getType() {
        return type;
    }

    public void setType(BigDecimal type) {
        this.type = type;
    }

    public String getRefundAccount() {
        return refundAccount;
    }

    public void setRefundAccount(String refundAccount) {
        this.refundAccount = refundAccount == null ? null : refundAccount.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank == null ? null : accountBank.trim();
    }

    public String getBranchLineNum() {
        return branchLineNum;
    }

    public void setBranchLineNum(String branchLineNum) {
        this.branchLineNum = branchLineNum == null ? null : branchLineNum.trim();
    }

    public String getAllLineNum() {
        return allLineNum;
    }

    public void setAllLineNum(String allLineNum) {
        this.allLineNum = allLineNum == null ? null : allLineNum.trim();
    }

    public BigDecimal getRefundAmo() {
        return refundAmo;
    }

    public void setRefundAmo(BigDecimal refundAmo) {
        this.refundAmo = refundAmo;
    }

    public Date getRefundTm() {
        return refundTm;
    }

    public void setRefundTm(Date refundTm) {
        this.refundTm = refundTm;
    }

    public BigDecimal getRefundStat() {
        return refundStat;
    }

    public void setRefundStat(BigDecimal refundStat) {
        this.refundStat = refundStat;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    public String getRefundCompany() {
        return refundCompany;
    }

    public void setRefundCompany(String refundCompany) {
        this.refundCompany = refundCompany == null ? null : refundCompany.trim();
    }

    public String getRefundUser() {
        return refundUser;
    }

    public void setRefundUser(String refundUser) {
        this.refundUser = refundUser == null ? null : refundUser.trim();
    }
}