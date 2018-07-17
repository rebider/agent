package com.ryx.credit.pojo.admin.bank;

import java.math.BigDecimal;

public class EtbSysCardBinNo {
    private String bankId;

    private String issUsers;

    private String cardNo;

    private BigDecimal cardLen;

    private String cardBin;

    private String cardName;

    private String bankName;

    private String branchId;

    private String branchId2;

    private String cardType;

    private String cardOrg;

    private BigDecimal cardTag;

    private BigDecimal cardTag2;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    public String getIssUsers() {
        return issUsers;
    }

    public void setIssUsers(String issUsers) {
        this.issUsers = issUsers == null ? null : issUsers.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public BigDecimal getCardLen() {
        return cardLen;
    }

    public void setCardLen(BigDecimal cardLen) {
        this.cardLen = cardLen;
    }

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin == null ? null : cardBin.trim();
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName == null ? null : cardName.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId == null ? null : branchId.trim();
    }

    public String getBranchId2() {
        return branchId2;
    }

    public void setBranchId2(String branchId2) {
        this.branchId2 = branchId2 == null ? null : branchId2.trim();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getCardOrg() {
        return cardOrg;
    }

    public void setCardOrg(String cardOrg) {
        this.cardOrg = cardOrg == null ? null : cardOrg.trim();
    }

    public BigDecimal getCardTag() {
        return cardTag;
    }

    public void setCardTag(BigDecimal cardTag) {
        this.cardTag = cardTag;
    }

    public BigDecimal getCardTag2() {
        return cardTag2;
    }

    public void setCardTag2(BigDecimal cardTag2) {
        this.cardTag2 = cardTag2;
    }
}