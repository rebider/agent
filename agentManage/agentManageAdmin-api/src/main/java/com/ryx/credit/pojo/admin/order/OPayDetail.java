package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OPayDetail implements Serializable {
    private String id;

    private String arrId;

    private BigDecimal amount;

    private String payType;

    private String srcId;

    private BigDecimal busStat;

    private BigDecimal status;

    private BigDecimal version;

    private String cUser;

    private Date cTm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getArrId() {
        return arrId;
    }

    public void setArrId(String arrId) {
        this.arrId = arrId == null ? null : arrId.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId == null ? null : srcId.trim();
    }

    public BigDecimal getBusStat() {
        return busStat;
    }

    public void setBusStat(BigDecimal busStat) {
        this.busStat = busStat;
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

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public Date getcTm() {
        return cTm;
    }

    public void setcTm(Date cTm) {
        this.cTm = cTm;
    }
}