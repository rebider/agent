package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PosRewardTemplate implements Serializable{
    private static final long serialVersionUID = -6219173161424776471L;
    private String id;

    private String tranContrastMonth;

    private String tranTotal;

    private String creditTranContrastMonth;

    private BigDecimal proportion;

    private Date createTime;

    private Date updateTime;

    private String operUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTranContrastMonth() {
        return tranContrastMonth;
    }

    public void setTranContrastMonth(String tranContrastMonth) {
        this.tranContrastMonth = tranContrastMonth == null ? null : tranContrastMonth.trim();
    }

    public String getTranTotal() {
        return tranTotal;
    }

    public void setTranTotal(String tranTotal) {
        this.tranTotal = tranTotal == null ? null : tranTotal.trim();
    }

    public String getCreditTranContrastMonth() {
        return creditTranContrastMonth;
    }

    public void setCreditTranContrastMonth(String creditTranContrastMonth) {
        this.creditTranContrastMonth = creditTranContrastMonth == null ? null : creditTranContrastMonth.trim();
    }

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperUser() {
        return operUser;
    }

    public void setOperUser(String operUser) {
        this.operUser = operUser == null ? null : operUser.trim();
    }
}