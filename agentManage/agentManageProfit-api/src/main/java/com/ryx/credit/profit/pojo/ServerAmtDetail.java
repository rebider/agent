package com.ryx.credit.profit.pojo;

import java.math.BigDecimal;

public class ServerAmtDetail extends SetServerAmt{
    private String id;

    private String psaId;

    private String serverDate;

    private BigDecimal sumAmt;

    private BigDecimal serverAmt;

    private String rev1;

    private String rev2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPsaId() {
        return psaId;
    }

    public void setPsaId(String psaId) {
        this.psaId = psaId == null ? null : psaId.trim();
    }

    public String getServerDate() {
        return serverDate;
    }

    public void setServerDate(String serverDate) {
        this.serverDate = serverDate == null ? null : serverDate.trim();
    }

    public BigDecimal getSumAmt() {
        return sumAmt;
    }

    public void setSumAmt(BigDecimal sumAmt) {
        this.sumAmt = sumAmt;
    }

    public BigDecimal getServerAmt() {
        return serverAmt;
    }

    public void setServerAmt(BigDecimal serverAmt) {
        this.serverAmt = serverAmt;
    }

    public String getRev1() {
        return rev1;
    }

    public void setRev1(String rev1) {
        this.rev1 = rev1 == null ? null : rev1.trim();
    }

    public String getRev2() {
        return rev2;
    }

    public void setRev2(String rev2) {
        this.rev2 = rev2 == null ? null : rev2.trim();
    }
}