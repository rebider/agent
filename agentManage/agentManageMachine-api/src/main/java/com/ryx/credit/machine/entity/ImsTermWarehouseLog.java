package com.ryx.credit.machine.entity;

public class ImsTermWarehouseLog {
    private String wdId;

    private String machineId;

    private String posSn;

    private String oldWarehouseDetail;

    private String newWarehouseDetail;

    private String operDescribe;

    private String operType;

    private String operTime;

    private String operUser;

    public String getWdId() {
        return wdId;
    }

    public void setWdId(String wdId) {
        this.wdId = wdId == null ? null : wdId.trim();
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId == null ? null : machineId.trim();
    }

    public String getPosSn() {
        return posSn;
    }

    public void setPosSn(String posSn) {
        this.posSn = posSn == null ? null : posSn.trim();
    }

    public String getOldWarehouseDetail() {
        return oldWarehouseDetail;
    }

    public void setOldWarehouseDetail(String oldWarehouseDetail) {
        this.oldWarehouseDetail = oldWarehouseDetail == null ? null : oldWarehouseDetail.trim();
    }

    public String getNewWarehouseDetail() {
        return newWarehouseDetail;
    }

    public void setNewWarehouseDetail(String newWarehouseDetail) {
        this.newWarehouseDetail = newWarehouseDetail == null ? null : newWarehouseDetail.trim();
    }

    public String getOperDescribe() {
        return operDescribe;
    }

    public void setOperDescribe(String operDescribe) {
        this.operDescribe = operDescribe == null ? null : operDescribe.trim();
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType == null ? null : operType.trim();
    }

    public String getOperTime() {
        return operTime;
    }

    public void setOperTime(String operTime) {
        this.operTime = operTime == null ? null : operTime.trim();
    }

    public String getOperUser() {
        return operUser;
    }

    public void setOperUser(String operUser) {
        this.operUser = operUser == null ? null : operUser.trim();
    }
}