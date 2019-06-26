package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.pojo.admin.agent.Attachment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TerminalTransfer implements Serializable{
    private String id;

    private String remark;

    private BigDecimal reviewStatus;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    private String agentId;

    private String platformType;

    private List<TerminalTransferDetail> terminalTransferDetailList;

    private String terTranFile;

    private List<Attachment> attachments;

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getTerTranFile() {
        return terTranFile;
    }

    public void setTerTranFile(String terTranFile) {
        this.terTranFile = terTranFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(BigDecimal reviewStatus) {
        this.reviewStatus = reviewStatus;
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

    public List<TerminalTransferDetail> getTerminalTransferDetailList() {
        return terminalTransferDetailList;
    }

    public void setTerminalTransferDetailList(List<TerminalTransferDetail> terminalTransferDetailList) {
        this.terminalTransferDetailList = terminalTransferDetailList;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}