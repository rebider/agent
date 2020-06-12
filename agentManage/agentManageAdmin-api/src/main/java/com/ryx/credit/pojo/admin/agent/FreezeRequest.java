package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FreezeRequest implements Serializable {
    private String id;

    private BigDecimal reqType;

    private Date cTm;

    private String cUserId;

    private String freezeCause;

    private String reqReason;

    private BigDecimal reviewsStat;

    private Date reviewsDate;

    private String reviewsUser;

    private BigDecimal status;

    private BigDecimal version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public BigDecimal getReqType() {
        return reqType;
    }

    public void setReqType(BigDecimal reqType) {
        this.reqType = reqType;
    }

    public Date getcTm() {
        return cTm;
    }

    public void setcTm(Date cTm) {
        this.cTm = cTm;
    }

    public String getcUserId() {
        return cUserId;
    }

    public void setcUserId(String cUserId) {
        this.cUserId = cUserId == null ? null : cUserId.trim();
    }

    public String getFreezeCause() {
        return freezeCause;
    }

    public void setFreezeCause(String freezeCause) {
        this.freezeCause = freezeCause == null ? null : freezeCause.trim();
    }

    public String getReqReason() {
        return reqReason;
    }

    public void setReqReason(String reqReason) {
        this.reqReason = reqReason == null ? null : reqReason.trim();
    }

    public BigDecimal getReviewsStat() {
        return reviewsStat;
    }

    public void setReviewsStat(BigDecimal reviewsStat) {
        this.reviewsStat = reviewsStat;
    }

    public Date getReviewsDate() {
        return reviewsDate;
    }

    public void setReviewsDate(Date reviewsDate) {
        this.reviewsDate = reviewsDate;
    }

    public String getReviewsUser() {
        return reviewsUser;
    }

    public void setReviewsUser(String reviewsUser) {
        this.reviewsUser = reviewsUser == null ? null : reviewsUser.trim();
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