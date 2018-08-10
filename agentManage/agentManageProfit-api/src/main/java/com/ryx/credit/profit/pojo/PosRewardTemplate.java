package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author RYX
 */
public class PosRewardTemplate implements Serializable {
    private static final long serialVersionUID = 4685250900953322756L;
    private String id;

    private String tranContrastMonth;

    private BigDecimal tranTotalStart;

    private BigDecimal tranTotalEnd;

    private String creditTranContrastMonth;

    private BigDecimal proportion;

    private Date createTime;

    private String activityValid;

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

    public BigDecimal getTranTotalStart() {
        return tranTotalStart;
    }

    public void setTranTotalStart(BigDecimal tranTotalStart) {
        this.tranTotalStart = tranTotalStart;
    }

    public BigDecimal getTranTotalEnd() {
        return tranTotalEnd;
    }

    public void setTranTotalEnd(BigDecimal tranTotalEnd) {
        this.tranTotalEnd = tranTotalEnd;
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

    public String getActivityValid() {
        return activityValid;
    }

    public void setActivityValid(String activityValid) {
        this.activityValid = activityValid == null ? null : activityValid.trim();
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