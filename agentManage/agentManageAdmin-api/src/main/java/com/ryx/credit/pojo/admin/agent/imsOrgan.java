package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;

public class imsOrgan implements Serializable{
    private String orgId;

    private String supOrgId;

    private Integer orgLevel;

    private String useOrgan;

    private String orgName;

    private String province;

    private String city;

    private String cityArea;

    private String isLeaf;

    private String createTime;

    private String cancelTime;

    private String status;

    private String topOrgId;

    private BigDecimal atmLimit;

    private String oldOrgId;

    private String merSeq;

    private String merPrefix;

    private String orgType;

    private String supDorgId;

    private String uniqueId;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getSupOrgId() {
        return supOrgId;
    }

    public void setSupOrgId(String supOrgId) {
        this.supOrgId = supOrgId == null ? null : supOrgId.trim();
    }

    public Integer getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(Integer orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getUseOrgan() {
        return useOrgan;
    }

    public void setUseOrgan(String useOrgan) {
        this.useOrgan = useOrgan == null ? null : useOrgan.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCityArea() {
        return cityArea;
    }

    public void setCityArea(String cityArea) {
        this.cityArea = cityArea == null ? null : cityArea.trim();
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf == null ? null : isLeaf.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime == null ? null : cancelTime.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getTopOrgId() {
        return topOrgId;
    }

    public void setTopOrgId(String topOrgId) {
        this.topOrgId = topOrgId == null ? null : topOrgId.trim();
    }

    public BigDecimal getAtmLimit() {
        return atmLimit;
    }

    public void setAtmLimit(BigDecimal atmLimit) {
        this.atmLimit = atmLimit;
    }

    public String getOldOrgId() {
        return oldOrgId;
    }

    public void setOldOrgId(String oldOrgId) {
        this.oldOrgId = oldOrgId == null ? null : oldOrgId.trim();
    }

    public String getMerSeq() {
        return merSeq;
    }

    public void setMerSeq(String merSeq) {
        this.merSeq = merSeq == null ? null : merSeq.trim();
    }

    public String getMerPrefix() {
        return merPrefix;
    }

    public void setMerPrefix(String merPrefix) {
        this.merPrefix = merPrefix == null ? null : merPrefix.trim();
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType == null ? null : orgType.trim();
    }

    public String getSupDorgId() {
        return supDorgId;
    }

    public void setSupDorgId(String supDorgId) {
        this.supDorgId = supDorgId == null ? null : supDorgId.trim();
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId == null ? null : uniqueId.trim();
    }
}