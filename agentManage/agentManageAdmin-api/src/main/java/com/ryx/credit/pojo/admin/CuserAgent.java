package com.ryx.credit.pojo.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CuserAgent extends CuserAgentKey implements Serializable  {
    private Date cTime;

    private String cUser;

    private BigDecimal status;

    private BigDecimal version;

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