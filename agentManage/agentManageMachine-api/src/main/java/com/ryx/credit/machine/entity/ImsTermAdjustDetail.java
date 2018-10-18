package com.ryx.credit.machine.entity;

import java.io.Serializable;

public class ImsTermAdjustDetail  implements Serializable {
    private String id;

    private String posSn;

    private String machineId;

    private String adId;

    private String yOrgId;

    private String nOrgId;

    private String createPerson;

    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPosSn() {
        return posSn;
    }

    public void setPosSn(String posSn) {
        this.posSn = posSn == null ? null : posSn.trim();
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId == null ? null : machineId.trim();
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId == null ? null : adId.trim();
    }

    public String getyOrgId() {
        return yOrgId;
    }

    public void setyOrgId(String yOrgId) {
        this.yOrgId = yOrgId == null ? null : yOrgId.trim();
    }

    public String getnOrgId() {
        return nOrgId;
    }

    public void setnOrgId(String nOrgId) {
        this.nOrgId = nOrgId == null ? null : nOrgId.trim();
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson == null ? null : createPerson.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }
}