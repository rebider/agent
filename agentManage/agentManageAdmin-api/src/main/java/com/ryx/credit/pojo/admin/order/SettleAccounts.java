package com.ryx.credit.pojo.admin.order;

import java.math.BigDecimal;
import java.util.Date;

public class SettleAccounts {
    private String id;

    private BigDecimal sType;

    private BigDecimal sAmount;

    private Date sTm;

    private BigDecimal cStatus;

    private String agentId;

    private String srcId;

    private String tarId;

    private Date cTm;

    private String cUser;

    private Date cUtm;

    private String cOperator;

    private String remark;

    private BigDecimal status;

    private BigDecimal version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public BigDecimal getsType() {
        return sType;
    }

    public void setsType(BigDecimal sType) {
        this.sType = sType;
    }

    public BigDecimal getsAmount() {
        return sAmount;
    }

    public void setsAmount(BigDecimal sAmount) {
        this.sAmount = sAmount;
    }

    public Date getsTm() {
        return sTm;
    }

    public void setsTm(Date sTm) {
        this.sTm = sTm;
    }

    public BigDecimal getcStatus() {
        return cStatus;
    }

    public void setcStatus(BigDecimal cStatus) {
        this.cStatus = cStatus;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId == null ? null : srcId.trim();
    }

    public String getTarId() {
        return tarId;
    }

    public void setTarId(String tarId) {
        this.tarId = tarId == null ? null : tarId.trim();
    }

    public Date getcTm() {
        return cTm;
    }

    public void setcTm(Date cTm) {
        this.cTm = cTm;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public Date getcUtm() {
        return cUtm;
    }

    public void setcUtm(Date cUtm) {
        this.cUtm = cUtm;
    }

    public String getcOperator() {
        return cOperator;
    }

    public void setcOperator(String cOperator) {
        this.cOperator = cOperator == null ? null : cOperator.trim();
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