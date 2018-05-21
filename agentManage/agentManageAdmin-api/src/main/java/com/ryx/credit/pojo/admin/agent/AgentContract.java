package com.ryx.credit.pojo.admin.agent;

import java.math.BigDecimal;
import java.util.Date;

public class AgentContract extends AgentContractKey {
    private String agentId;

    private String contNum;

    private BigDecimal contType;

    private Date contDate;

    private Date contEndDate;

    private String remark;

    private String cUser;

    private Date cUtime;

    private BigDecimal cloReviewStatus;

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
}