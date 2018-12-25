package com.ryx.credit.machine.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ImsTermWarehouseDetail implements Serializable{
    private String posSn;

    private String wdId;

    private String machineId;

    private String orgId;

    private String merId;

    private String termId;

    private String useStatus;

    private String status;

    private String createTime;

    private String createPerson;

    private String updateTime;

    private String posType;

    private String payStatus;

    private String payTime;

    private BigDecimal posSpePrice;

    private String deliveryTime;

    private BigDecimal standTime;

    public String getPosSn() {
        return posSn;
    }

    public void setPosSn(String posSn) {
        this.posSn = posSn == null ? null : posSn.trim();
    }

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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId == null ? null : termId.trim();
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus == null ? null : useStatus.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson == null ? null : createPerson.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType == null ? null : posType.trim();
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus == null ? null : payStatus.trim();
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }

    public BigDecimal getPosSpePrice() {
        return posSpePrice;
    }

    public void setPosSpePrice(BigDecimal posSpePrice) {
        this.posSpePrice = posSpePrice;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime == null ? null : deliveryTime.trim();
    }

    public BigDecimal getStandTime() {
        return standTime;
    }

    public void setStandTime(BigDecimal standTime) {
        this.standTime = standTime;
    }
}