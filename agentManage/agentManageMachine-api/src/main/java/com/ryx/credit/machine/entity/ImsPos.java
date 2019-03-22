package com.ryx.credit.machine.entity;

import java.io.Serializable;
import java.util.Date;

public class ImsPos implements Serializable{
    private String id;

    private String posModel;

    private String manuf;

    private String deviceType;

    private String commType;

    private String tmsInfo;

    private Date tmsTime;

    private String tmsModel;

    private String status;

    private String jdModel;

    private String isOwnerVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPosModel() {
        return posModel;
    }

    public void setPosModel(String posModel) {
        this.posModel = posModel == null ? null : posModel.trim();
    }

    public String getManuf() {
        return manuf;
    }

    public void setManuf(String manuf) {
        this.manuf = manuf == null ? null : manuf.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getCommType() {
        return commType;
    }

    public void setCommType(String commType) {
        this.commType = commType == null ? null : commType.trim();
    }

    public String getTmsInfo() {
        return tmsInfo;
    }

    public void setTmsInfo(String tmsInfo) {
        this.tmsInfo = tmsInfo == null ? null : tmsInfo.trim();
    }

    public Date getTmsTime() {
        return tmsTime;
    }

    public void setTmsTime(Date tmsTime) {
        this.tmsTime = tmsTime;
    }

    public String getTmsModel() {
        return tmsModel;
    }

    public void setTmsModel(String tmsModel) {
        this.tmsModel = tmsModel == null ? null : tmsModel.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getJdModel() {
        return jdModel;
    }

    public void setJdModel(String jdModel) {
        this.jdModel = jdModel == null ? null : jdModel.trim();
    }

    public String getIsOwnerVersion() {
        return isOwnerVersion;
    }

    public void setIsOwnerVersion(String isOwnerVersion) {
        this.isOwnerVersion = isOwnerVersion == null ? null : isOwnerVersion.trim();
    }
}