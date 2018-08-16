package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author RYX
 */
public class TemplateAgreement implements Serializable{

    private static final long serialVersionUID = 473836471697788554L;
    private String id;

    private String agreName;

    private String agreVersion;

    private String agreType;

    private String attrid;

    private String agreViewType;

    private Date cTime;

    private Date cUtime;

    private String status;

    private String agreContent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAgreName() {
        return agreName;
    }

    public void setAgreName(String agreName) {
        this.agreName = agreName == null ? null : agreName.trim();
    }

    public String getAgreVersion() {
        return agreVersion;
    }

    public void setAgreVersion(String agreVersion) {
        this.agreVersion = agreVersion == null ? null : agreVersion.trim();
    }

    public String getAgreType() {
        return agreType;
    }

    public void setAgreType(String agreType) {
        this.agreType = agreType == null ? null : agreType.trim();
    }

    public String getAttrid() {
        return attrid;
    }

    public void setAttrid(String attrid) {
        this.attrid = attrid == null ? null : attrid.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getcUtime() {
        return cUtime;
    }

    public void setcUtime(Date cUtime) {
        this.cUtime = cUtime;
    }

    public String getAgreContent() {
        return agreContent;
    }

    public void setAgreContent(String agreContent) {
        this.agreContent = agreContent == null ? null : agreContent.trim();
    }

    public String getAgreViewType() {
        return agreViewType;
    }

    public void setAgreViewType(String agreViewType) {
        this.agreViewType = agreViewType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}