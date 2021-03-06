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

public class OReturnOrder implements Serializable {
    private String id;

    private String agentId;

    private String applyRemark;

    private BigDecimal retInvoice;

    private BigDecimal retReceipt;

    private BigDecimal retSchedule;

    private BigDecimal returnAmo;

    private BigDecimal goodsReturnAmo;

    private BigDecimal cutAmo;

    private BigDecimal relReturnAmo;

    private BigDecimal takeOutAmo;

    private String returnAddress;

    private Date retTime;

    private String remark;

    private String batchCode;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    @JSONField(
            format = "yyyy-MM-dd"
    )
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = DateJsonDeserializer.class
    )
    @JsonSerialize(
            using = DateJsonSerializer.class
    )
    private Date refundtime;

    private String refundpeople;

    private String auditor;

    private String oreturntype;

    private String logicalVersion;

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
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark == null ? null : applyRemark.trim();
    }

    public BigDecimal getRetInvoice() {
        return retInvoice;
    }

    public void setRetInvoice(BigDecimal retInvoice) {
        this.retInvoice = retInvoice;
    }

    public BigDecimal getRetReceipt() {
        return retReceipt;
    }

    public void setRetReceipt(BigDecimal retReceipt) {
        this.retReceipt = retReceipt;
    }

    public BigDecimal getRetSchedule() {
        return retSchedule;
    }

    public void setRetSchedule(BigDecimal retSchedule) {
        this.retSchedule = retSchedule;
    }

    public BigDecimal getReturnAmo() {
        return returnAmo;
    }

    public void setReturnAmo(BigDecimal returnAmo) {
        this.returnAmo = returnAmo;
    }

    public BigDecimal getGoodsReturnAmo() {
        return goodsReturnAmo;
    }

    public void setGoodsReturnAmo(BigDecimal goodsReturnAmo) {
        this.goodsReturnAmo = goodsReturnAmo;
    }

    public BigDecimal getCutAmo() {
        return cutAmo;
    }

    public void setCutAmo(BigDecimal cutAmo) {
        this.cutAmo = cutAmo;
    }

    public BigDecimal getRelReturnAmo() {
        return relReturnAmo;
    }

    public void setRelReturnAmo(BigDecimal relReturnAmo) {
        this.relReturnAmo = relReturnAmo;
    }

    public BigDecimal getTakeOutAmo() {
        return takeOutAmo;
    }

    public void setTakeOutAmo(BigDecimal takeOutAmo) {
        this.takeOutAmo = takeOutAmo;
    }

    public String getReturnAddress() {
        return returnAddress;
    }

    public void setReturnAddress(String returnAddress) {
        this.returnAddress = returnAddress == null ? null : returnAddress.trim();
    }

    public Date getRetTime() {
        return retTime;
    }

    public void setRetTime(Date retTime) {
        this.retTime = retTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode == null ? null : batchCode.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getuTime() {
        return uTime;
    }

    public void setuTime(Date uTime) {
        this.uTime = uTime;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public String getuUser() {
        return uUser;
    }

    public void setuUser(String uUser) {
        this.uUser = uUser == null ? null : uUser.trim();
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

    public Date getRefundtime() {
        return refundtime;
    }

    public void setRefundtime(Date refundtime) {
        this.refundtime = refundtime;
    }

    public String getRefundpeople() {
        return refundpeople;
    }

    public void setRefundpeople(String refundpeople) {
        this.refundpeople = refundpeople == null ? null : refundpeople.trim();
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public String getOreturntype() {
        return oreturntype;
    }

    public void setOreturntype(String oreturntype) {
        this.oreturntype = oreturntype == null ? null : oreturntype.trim();
    }

    public String getLogicalVersion() {
        return logicalVersion;
    }

    public void setLogicalVersion(String logicalVersion) {
        this.logicalVersion = logicalVersion == null ? null : logicalVersion.trim();
    }
}