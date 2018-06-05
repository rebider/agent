package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;

public class Region implements Serializable{

    private BigDecimal id;

    private String rName;

    private String rCode;

    private String pCode;

    private BigDecimal tType;

    private BigDecimal rSort;

    private BigDecimal status;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName == null ? null : rName.trim();
    }

    public String getrCode() {
        return rCode;
    }

    public void setrCode(String rCode) {
        this.rCode = rCode == null ? null : rCode.trim();
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode == null ? null : pCode.trim();
    }

    public BigDecimal gettType() {
        return tType;
    }

    public void settType(BigDecimal tType) {
        this.tType = tType;
    }

    public BigDecimal getrSort() {
        return rSort;
    }

    public void setrSort(BigDecimal rSort) {
        this.rSort = rSort;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }
}