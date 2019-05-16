package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DataHistoryRecord implements Serializable{
    private String id;

    private String dataClass;

    private String dataMethod;

    private BigDecimal dataVersion;

    private Date cTime;

    private String cUser;

    private BigDecimal status;

    private String busId;

    private String dataParameter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDataClass() {
        return dataClass;
    }

    public void setDataClass(String dataClass) {
        this.dataClass = dataClass == null ? null : dataClass.trim();
    }

    public String getDataMethod() {
        return dataMethod;
    }

    public void setDataMethod(String dataMethod) {
        this.dataMethod = dataMethod == null ? null : dataMethod.trim();
    }

    public BigDecimal getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(BigDecimal dataVersion) {
        this.dataVersion = dataVersion;
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

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId == null ? null : busId.trim();
    }

    public String getDataParameter() {
        return dataParameter;
    }

    public void setDataParameter(String dataParameter) {
        this.dataParameter = dataParameter == null ? null : dataParameter.trim();
    }
}