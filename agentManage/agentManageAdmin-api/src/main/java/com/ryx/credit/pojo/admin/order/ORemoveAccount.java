package com.ryx.credit.pojo.admin.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ryx.credit.common.util.DateJsonDeserializerMonth;
import com.ryx.credit.common.util.DateJsonSerializerMonth;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ORemoveAccount implements Serializable{
    private String id;

    @JSONField(format="yyyy-MM")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM", timezone = "GMT+8")
    @JsonDeserialize(using = DateJsonDeserializerMonth.class)
    @JsonSerialize(using = DateJsonSerializerMonth.class)
    private Date rmonth;

    private String agId;

    private String agName;

    private String busNum;

    private String busPlatform;

    private BigDecimal machinesAmount;

    private String submitPerson;

    private Date submitTime;

    private String payMethod;

    private BigDecimal ramount;

    private BigDecimal realRamount;

    private BigDecimal rstatus;

    private Date finishTime;

    private String batchNum;

    private BigDecimal rtype;

    private String failCause;

    private String remark;

    private BigDecimal status;

    private BigDecimal version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getRmonth() {
        return rmonth;
    }

    public void setRmonth(Date rmonth) {
        this.rmonth = rmonth;
    }

    public String getAgId() {
        return agId;
    }

    public void setAgId(String agId) {
        this.agId = agId == null ? null : agId.trim();
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName == null ? null : agName.trim();
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum == null ? null : busNum.trim();
    }

    public String getBusPlatform() {
        return busPlatform;
    }

    public void setBusPlatform(String busPlatform) {
        this.busPlatform = busPlatform == null ? null : busPlatform.trim();
    }

    public BigDecimal getMachinesAmount() {
        return machinesAmount;
    }

    public void setMachinesAmount(BigDecimal machinesAmount) {
        this.machinesAmount = machinesAmount;
    }

    public String getSubmitPerson() {
        return submitPerson;
    }

    public void setSubmitPerson(String submitPerson) {
        this.submitPerson = submitPerson == null ? null : submitPerson.trim();
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod == null ? null : payMethod.trim();
    }

    public BigDecimal getRamount() {
        return ramount;
    }

    public void setRamount(BigDecimal ramount) {
        this.ramount = ramount;
    }

    public BigDecimal getRealRamount() {
        return realRamount;
    }

    public void setRealRamount(BigDecimal realRamount) {
        this.realRamount = realRamount;
    }

    public BigDecimal getRstatus() {
        return rstatus;
    }

    public void setRstatus(BigDecimal rstatus) {
        this.rstatus = rstatus;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum == null ? null : batchNum.trim();
    }

    public BigDecimal getRtype() {
        return rtype;
    }

    public void setRtype(BigDecimal rtype) {
        this.rtype = rtype;
    }

    public String getFailCause() {
        return failCause;
    }

    public void setFailCause(String failCause) {
        this.failCause = failCause == null ? null : failCause.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}