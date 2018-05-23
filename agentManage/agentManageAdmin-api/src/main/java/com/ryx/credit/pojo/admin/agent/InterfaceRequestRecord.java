package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InterfaceRequestRecord implements Serializable {
    private String id;

    private String type;

    private String url;

    private Date cTime;

    private String cUser;

    private BigDecimal repStatus;

    private BigDecimal status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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

    public BigDecimal getRepStatus() {
        return repStatus;
    }

    public void setRepStatus(BigDecimal repStatus) {
        this.repStatus = repStatus;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }
}