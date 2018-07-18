package com.ryx.credit.pojo.admin.bank;

import java.io.Serializable;

public class BankRegion implements Serializable {
    private String id;

    private String bRegion;

    private String bLevel;

    private String bCity;

    private String bProvince;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getbRegion() {
        return bRegion;
    }

    public void setbRegion(String bRegion) {
        this.bRegion = bRegion == null ? null : bRegion.trim();
    }

    public String getbLevel() {
        return bLevel;
    }

    public void setbLevel(String bLevel) {
        this.bLevel = bLevel == null ? null : bLevel.trim();
    }

    public String getbCity() {
        return bCity;
    }

    public void setbCity(String bCity) {
        this.bCity = bCity == null ? null : bCity.trim();
    }

    public String getbProvince() {
        return bProvince;
    }

    public void setbProvince(String bProvince) {
        this.bProvince = bProvince == null ? null : bProvince.trim();
    }
}