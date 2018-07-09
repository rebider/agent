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

public class AgentContract implements Serializable {
    private String id;

    private String agentId;

    private String contNum;

    private BigDecimal contType;

    @JSONField(format="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date contDate;

    @JSONField(format="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date contEndDate;

    private String remark;

    private Date cTime;

    private String cUser;

    private Date cUtime;

    private BigDecimal cloReviewStatus;

    private BigDecimal status;

    private BigDecimal version;

    private BigDecimal appendAgree;

    private List<Attachment> attachmentList;

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

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getContNum() {
        return contNum;
    }

    public void setContNum(String contNum) {
        this.contNum = contNum == null ? null : contNum.trim();
    }

    public BigDecimal getContType() {
        return contType;
    }

    public void setContType(BigDecimal contType) {
        this.contType = contType;
    }

    public Date getContDate() {
        return contDate;
    }

    public void setContDate(Date contDate) {
        this.contDate = contDate;
    }

    public Date getContEndDate() {
        return contEndDate;
    }

    public void setContEndDate(Date contEndDate) {
        this.contEndDate = contEndDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public Date getcUtime() {
        return cUtime;
    }

    public void setcUtime(Date cUtime) {
        this.cUtime = cUtime;
    }

    public BigDecimal getCloReviewStatus() {
        return cloReviewStatus;
    }

    public void setCloReviewStatus(BigDecimal cloReviewStatus) {
        this.cloReviewStatus = cloReviewStatus;
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

    public BigDecimal getAppendAgree() {
        return appendAgree;
    }

    public void setAppendAgree(BigDecimal appendAgree) {
        this.appendAgree = appendAgree;
    }
}