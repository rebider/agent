package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Attachment implements Serializable {
    private String id;

    private String attName;

    private BigDecimal attSize;

    private String attType;

    private String attDbpath;

    private Date cTime;

    private String cUser;

    private BigDecimal status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAttName() {
        return attName;
    }

    public void setAttName(String attName) {
        this.attName = attName == null ? null : attName.trim();
    }

    public BigDecimal getAttSize() {
        return attSize;
    }

    public void setAttSize(BigDecimal attSize) {
        this.attSize = attSize;
    }

    public String getAttType() {
        return attType;
    }

    public void setAttType(String attType) {
        this.attType = attType == null ? null : attType.trim();
    }

    public String getAttDbpath() {
        return attDbpath;
    }

    public void setAttDbpath(String attDbpath) {
        this.attDbpath = attDbpath == null ? null : attDbpath.trim();
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

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }
}