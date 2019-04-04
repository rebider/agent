package com.ryx.credit.pojo.admin.agent;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ryx.credit.common.util.DateJsonDeserializer;
import com.ryx.credit.common.util.DateJsonSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Capital implements Serializable{
    private String id;

    private String cType;

    private BigDecimal cAmount;

    private BigDecimal cIsin;

    private BigDecimal cInAmount;

    private BigDecimal cBusStatus;

    private String cSrc;


    private Date cPlanintime;

    private Date cIntime;

    @JSONField(format="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date cPaytime;

    private Date cTime;

    private Date cUtime;

    private String remark;

    private BigDecimal status;

    private BigDecimal version;

    private String cAgentId;

    private List<Attachment> attachmentList;

    private String cUser;

    private String cPayuser;

    private BigDecimal cFqCount;

    private String cPayType;

    private String cInCom;

    private BigDecimal cFqInAmount;

    private Date cFqDate;

    private BigDecimal cloReviewStatus;

    private BigDecimal freezeAmt;

    //新增
    private BigDecimal debt;

    //新增时间
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BigDecimal getDebt() {
        return debt;
    }

    @Override
    public String toString() {
        return "Capital{" +
                "id='" + id + '\'' +
                ", cType='" + cType + '\'' +
                ", cAmount=" + cAmount +
                ", cIsin=" + cIsin +
                ", cInAmount=" + cInAmount +
                ", cBusStatus=" + cBusStatus +
                ", cSrc='" + cSrc + '\'' +
                ", cPlanintime=" + cPlanintime +
                ", cIntime=" + cIntime +
                ", cPaytime=" + cPaytime +
                ", cTime=" + cTime +
                ", cUtime=" + cUtime +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", version=" + version +
                ", cAgentId='" + cAgentId + '\'' +
                ", attachmentList=" + attachmentList +
                ", cUser='" + cUser + '\'' +
                ", cPayuser='" + cPayuser + '\'' +
                ", cFqCount=" + cFqCount +
                ", cPayType='" + cPayType + '\'' +
                ", cInCom='" + cInCom + '\'' +
                ", cFqInAmount=" + cFqInAmount +
                ", cFqDate=" + cFqDate +
                ", cloReviewStatus=" + cloReviewStatus +
                ", debt=" + debt +
                '}';
    }

    public BigDecimal getFreezeAmt() {
        return freezeAmt;
    }

    public void setFreezeAmt(BigDecimal freezeAmt) {
        this.freezeAmt = freezeAmt;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType == null ? null : cType.trim();
    }

    public BigDecimal getcAmount() {
        return cAmount;
    }

    public void setcAmount(BigDecimal cAmount) {
        this.cAmount = cAmount;
    }

    public BigDecimal getcIsin() {
        return cIsin;
    }

    public void setcIsin(BigDecimal cIsin) {
        this.cIsin = cIsin;
    }

    public BigDecimal getcInAmount() {
        return cInAmount;
    }

    public void setcInAmount(BigDecimal cInAmount) {
        this.cInAmount = cInAmount;
    }

    public BigDecimal getcBusStatus() {
        return cBusStatus;
    }

    public void setcBusStatus(BigDecimal cBusStatus) {
        this.cBusStatus = cBusStatus;
    }

    public String getcSrc() {
        return cSrc;
    }

    public void setcSrc(String cSrc) {
        this.cSrc = cSrc == null ? null : cSrc.trim();
    }

    public Date getcPlanintime() {
        return cPlanintime;
    }

    public void setcPlanintime(Date cPlanintime) {
        this.cPlanintime = cPlanintime;
    }

    public Date getcIntime() {
        return cIntime;
    }

    public void setcIntime(Date cIntime) {
        this.cIntime = cIntime;
    }

    public Date getcPaytime() {
        return cPaytime;
    }

    public void setcPaytime(Date cPaytime) {
        this.cPaytime = cPaytime;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getcUtime() {
        return cUtime;
    }

    public void setcUtime(Date cUtime) {
        this.cUtime = cUtime;
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

    public String getcAgentId() {
        return cAgentId;
    }

    public void setcAgentId(String cAgentId) {
        this.cAgentId = cAgentId == null ? null : cAgentId.trim();
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public String getcPayuser() {
        return cPayuser;
    }

    public void setcPayuser(String cPayuser) {
        this.cPayuser = cPayuser == null ? null : cPayuser.trim();
    }

    public BigDecimal getcFqCount() {
        return cFqCount;
    }

    public void setcFqCount(BigDecimal cFqCount) {
        this.cFqCount = cFqCount;
    }

    public String getcPayType() {
        return cPayType;
    }

    public void setcPayType(String cPayType) {
        this.cPayType = cPayType == null ? null : cPayType.trim();
    }

    public String getcInCom() {
        return cInCom;
    }

    public void setcInCom(String cInCom) {
        this.cInCom = cInCom == null ? null : cInCom.trim();
    }

    public BigDecimal getcFqInAmount() {
        return cFqInAmount;
    }

    public void setcFqInAmount(BigDecimal cFqInAmount) {
        this.cFqInAmount = cFqInAmount;
    }

    public Date getcFqDate() {
        return cFqDate;
    }

    public void setcFqDate(Date cFqDate) {
        this.cFqDate = cFqDate;
    }

    public BigDecimal getCloReviewStatus() {
        return cloReviewStatus;
    }

    public void setCloReviewStatus(BigDecimal cloReviewStatus) {
        this.cloReviewStatus = cloReviewStatus;
    }
}