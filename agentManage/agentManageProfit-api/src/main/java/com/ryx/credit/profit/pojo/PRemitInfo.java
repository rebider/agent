package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PRemitInfo implements Serializable {
    private String id;

    private String inAccountType;

    private String inAccountName;

    private String outAccount;

    private String outAccountBank;

    private BigDecimal remitAmt;

    private String fileName;

    private String filePath;

    private Date remitDate;

    private String citySupplyId;

    private String rev1;

    private String rev2;

    private String rev3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getInAccountType() {
        return inAccountType;
    }

    public void setInAccountType(String inAccountType) {
        this.inAccountType = inAccountType == null ? null : inAccountType.trim();
    }

    public String getInAccountName() {
        return inAccountName;
    }

    public void setInAccountName(String inAccountName) {
        this.inAccountName = inAccountName == null ? null : inAccountName.trim();
    }

    public String getOutAccount() {
        return outAccount;
    }

    public void setOutAccount(String outAccount) {
        this.outAccount = outAccount == null ? null : outAccount.trim();
    }

    public String getOutAccountBank() {
        return outAccountBank;
    }

    public void setOutAccountBank(String outAccountBank) {
        this.outAccountBank = outAccountBank == null ? null : outAccountBank.trim();
    }

    public BigDecimal getRemitAmt() {
        return remitAmt;
    }

    public void setRemitAmt(BigDecimal remitAmt) {
        this.remitAmt = remitAmt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Date getRemitDate() {
        return remitDate;
    }

    public void setRemitDate(Date remitDate) {
        this.remitDate = remitDate;
    }

    public String getCitySupplyId() {
        return citySupplyId;
    }

    public void setCitySupplyId(String citySupplyId) {
        this.citySupplyId = citySupplyId == null ? null : citySupplyId.trim();
    }

    public String getRev1() {
        return rev1;
    }

    public void setRev1(String rev1) {
        this.rev1 = rev1 == null ? null : rev1.trim();
    }

    public String getRev2() {
        return rev2;
    }

    public void setRev2(String rev2) {
        this.rev2 = rev2 == null ? null : rev2.trim();
    }

    public String getRev3() {
        return rev3;
    }

    public void setRev3(String rev3) {
        this.rev3 = rev3 == null ? null : rev3.trim();
    }
}