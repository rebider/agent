package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DateChangeRequest implements Serializable{
    private String id;

    private String dataId;

    private String dataType;

    private String dataContent;

    private String dataPreContent;

    private Date cTime;

    private Date cUpdate;

    private String cUser;

    private BigDecimal appyStatus;

    private BigDecimal status;

    private BigDecimal version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId == null ? null : dataId.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent == null ? null : dataContent.trim();
    }

    public String getDataPreContent() {
        return dataPreContent;
    }

    public void setDataPreContent(String dataPreContent) {
        this.dataPreContent = dataPreContent == null ? null : dataPreContent.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getcUpdate() {
        return cUpdate;
    }

    public void setcUpdate(Date cUpdate) {
        this.cUpdate = cUpdate;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public BigDecimal getAppyStatus() {
        return appyStatus;
    }

    public void setAppyStatus(BigDecimal appyStatus) {
        this.appyStatus = appyStatus;
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