package com.ryx.credit.profit.pojo;

import java.math.BigDecimal;

public class ProfitDetailMonth {
    private String id;

    private String profitId;

    private BigDecimal tranAmt;

    private BigDecimal payAmt;

    private String tranProfitScale;

    private String payProfitScale;

    private BigDecimal tranProfitAmt;

    private BigDecimal payProfitAmt;

    private BigDecimal ryxProfitAmt;

    private BigDecimal ryxHdProfitAmt;

    private BigDecimal tpProfitAmt;

    private BigDecimal rsProfitAmt;

    private BigDecimal rsHdProfitAmt;

    private BigDecimal rhbProfitAmt;

    private BigDecimal zfProfitAmt;

    private BigDecimal posZqSupplyProfitAmt;

    private BigDecimal mposZqSupplyProfitAmt;

    private BigDecimal profitSumAmt;

    private BigDecimal posTdMustDeductionAmt;

    private BigDecimal posTdRealDeductionAmt;

    private BigDecimal mposTdMustDeductionAmt;

    private BigDecimal mposTdRealDeductionAmt;

    private BigDecimal rhbDgMustDeductionAmt;

    private BigDecimal rhbDgRealDeductionAmt;

    private BigDecimal posDgMustDeductionAmt;

    private BigDecimal posDgRealDeductionAmt;

    private BigDecimal zposDgMustDeductionAmt;

    private BigDecimal zposTdRealDeductionAmt;

    private BigDecimal posKhDeductionAmt;

    private BigDecimal mposKhDeductionAmt;

    private BigDecimal buDeductionAmt;

    private BigDecimal otherDeductionAmt;

    private BigDecimal posTdSupplyAmt;

    private BigDecimal mposTdSupplyAmt;

    private BigDecimal otherSupplyAmt;

    private BigDecimal posRewardAmt;

    private BigDecimal deductionTaxMonthAgoAmt;

    private BigDecimal deductionTaxMonthAmt;

    private BigDecimal supplyTaxAmt;

    private BigDecimal realProfitAmt;

    private BigDecimal profitMonthAmt;

    private String accountId;

    private String accountName;

    private String openBankName;

    private String email;

    private String bankCode;

    private String payStatus;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProfitId() {
        return profitId;
    }

    public void setProfitId(String profitId) {
        this.profitId = profitId == null ? null : profitId.trim();
    }

    public BigDecimal getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(BigDecimal tranAmt) {
        this.tranAmt = tranAmt;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public String getTranProfitScale() {
        return tranProfitScale;
    }

    public void setTranProfitScale(String tranProfitScale) {
        this.tranProfitScale = tranProfitScale == null ? null : tranProfitScale.trim();
    }

    public String getPayProfitScale() {
        return payProfitScale;
    }

    public void setPayProfitScale(String payProfitScale) {
        this.payProfitScale = payProfitScale == null ? null : payProfitScale.trim();
    }

    public BigDecimal getTranProfitAmt() {
        return tranProfitAmt;
    }

    public void setTranProfitAmt(BigDecimal tranProfitAmt) {
        this.tranProfitAmt = tranProfitAmt;
    }

    public BigDecimal getPayProfitAmt() {
        return payProfitAmt;
    }

    public void setPayProfitAmt(BigDecimal payProfitAmt) {
        this.payProfitAmt = payProfitAmt;
    }

    public BigDecimal getRyxProfitAmt() {
        return ryxProfitAmt;
    }

    public void setRyxProfitAmt(BigDecimal ryxProfitAmt) {
        this.ryxProfitAmt = ryxProfitAmt;
    }

    public BigDecimal getRyxHdProfitAmt() {
        return ryxHdProfitAmt;
    }

    public void setRyxHdProfitAmt(BigDecimal ryxHdProfitAmt) {
        this.ryxHdProfitAmt = ryxHdProfitAmt;
    }

    public BigDecimal getTpProfitAmt() {
        return tpProfitAmt;
    }

    public void setTpProfitAmt(BigDecimal tpProfitAmt) {
        this.tpProfitAmt = tpProfitAmt;
    }

    public BigDecimal getRsProfitAmt() {
        return rsProfitAmt;
    }

    public void setRsProfitAmt(BigDecimal rsProfitAmt) {
        this.rsProfitAmt = rsProfitAmt;
    }

    public BigDecimal getRsHdProfitAmt() {
        return rsHdProfitAmt;
    }

    public void setRsHdProfitAmt(BigDecimal rsHdProfitAmt) {
        this.rsHdProfitAmt = rsHdProfitAmt;
    }

    public BigDecimal getRhbProfitAmt() {
        return rhbProfitAmt;
    }

    public void setRhbProfitAmt(BigDecimal rhbProfitAmt) {
        this.rhbProfitAmt = rhbProfitAmt;
    }

    public BigDecimal getZfProfitAmt() {
        return zfProfitAmt;
    }

    public void setZfProfitAmt(BigDecimal zfProfitAmt) {
        this.zfProfitAmt = zfProfitAmt;
    }

    public BigDecimal getPosZqSupplyProfitAmt() {
        return posZqSupplyProfitAmt;
    }

    public void setPosZqSupplyProfitAmt(BigDecimal posZqSupplyProfitAmt) {
        this.posZqSupplyProfitAmt = posZqSupplyProfitAmt;
    }

    public BigDecimal getMposZqSupplyProfitAmt() {
        return mposZqSupplyProfitAmt;
    }

    public void setMposZqSupplyProfitAmt(BigDecimal mposZqSupplyProfitAmt) {
        this.mposZqSupplyProfitAmt = mposZqSupplyProfitAmt;
    }

    public BigDecimal getProfitSumAmt() {
        return profitSumAmt;
    }

    public void setProfitSumAmt(BigDecimal profitSumAmt) {
        this.profitSumAmt = profitSumAmt;
    }

    public BigDecimal getPosTdMustDeductionAmt() {
        return posTdMustDeductionAmt;
    }

    public void setPosTdMustDeductionAmt(BigDecimal posTdMustDeductionAmt) {
        this.posTdMustDeductionAmt = posTdMustDeductionAmt;
    }

    public BigDecimal getPosTdRealDeductionAmt() {
        return posTdRealDeductionAmt;
    }

    public void setPosTdRealDeductionAmt(BigDecimal posTdRealDeductionAmt) {
        this.posTdRealDeductionAmt = posTdRealDeductionAmt;
    }

    public BigDecimal getMposTdMustDeductionAmt() {
        return mposTdMustDeductionAmt;
    }

    public void setMposTdMustDeductionAmt(BigDecimal mposTdMustDeductionAmt) {
        this.mposTdMustDeductionAmt = mposTdMustDeductionAmt;
    }

    public BigDecimal getMposTdRealDeductionAmt() {
        return mposTdRealDeductionAmt;
    }

    public void setMposTdRealDeductionAmt(BigDecimal mposTdRealDeductionAmt) {
        this.mposTdRealDeductionAmt = mposTdRealDeductionAmt;
    }

    public BigDecimal getRhbDgMustDeductionAmt() {
        return rhbDgMustDeductionAmt;
    }

    public void setRhbDgMustDeductionAmt(BigDecimal rhbDgMustDeductionAmt) {
        this.rhbDgMustDeductionAmt = rhbDgMustDeductionAmt;
    }

    public BigDecimal getRhbDgRealDeductionAmt() {
        return rhbDgRealDeductionAmt;
    }

    public void setRhbDgRealDeductionAmt(BigDecimal rhbDgRealDeductionAmt) {
        this.rhbDgRealDeductionAmt = rhbDgRealDeductionAmt;
    }

    public BigDecimal getPosDgMustDeductionAmt() {
        return posDgMustDeductionAmt;
    }

    public void setPosDgMustDeductionAmt(BigDecimal posDgMustDeductionAmt) {
        this.posDgMustDeductionAmt = posDgMustDeductionAmt;
    }

    public BigDecimal getPosDgRealDeductionAmt() {
        return posDgRealDeductionAmt;
    }

    public void setPosDgRealDeductionAmt(BigDecimal posDgRealDeductionAmt) {
        this.posDgRealDeductionAmt = posDgRealDeductionAmt;
    }

    public BigDecimal getZposDgMustDeductionAmt() {
        return zposDgMustDeductionAmt;
    }

    public void setZposDgMustDeductionAmt(BigDecimal zposDgMustDeductionAmt) {
        this.zposDgMustDeductionAmt = zposDgMustDeductionAmt;
    }

    public BigDecimal getZposTdRealDeductionAmt() {
        return zposTdRealDeductionAmt;
    }

    public void setZposTdRealDeductionAmt(BigDecimal zposTdRealDeductionAmt) {
        this.zposTdRealDeductionAmt = zposTdRealDeductionAmt;
    }

    public BigDecimal getPosKhDeductionAmt() {
        return posKhDeductionAmt;
    }

    public void setPosKhDeductionAmt(BigDecimal posKhDeductionAmt) {
        this.posKhDeductionAmt = posKhDeductionAmt;
    }

    public BigDecimal getMposKhDeductionAmt() {
        return mposKhDeductionAmt;
    }

    public void setMposKhDeductionAmt(BigDecimal mposKhDeductionAmt) {
        this.mposKhDeductionAmt = mposKhDeductionAmt;
    }

    public BigDecimal getBuDeductionAmt() {
        return buDeductionAmt;
    }

    public void setBuDeductionAmt(BigDecimal buDeductionAmt) {
        this.buDeductionAmt = buDeductionAmt;
    }

    public BigDecimal getOtherDeductionAmt() {
        return otherDeductionAmt;
    }

    public void setOtherDeductionAmt(BigDecimal otherDeductionAmt) {
        this.otherDeductionAmt = otherDeductionAmt;
    }

    public BigDecimal getPosTdSupplyAmt() {
        return posTdSupplyAmt;
    }

    public void setPosTdSupplyAmt(BigDecimal posTdSupplyAmt) {
        this.posTdSupplyAmt = posTdSupplyAmt;
    }

    public BigDecimal getMposTdSupplyAmt() {
        return mposTdSupplyAmt;
    }

    public void setMposTdSupplyAmt(BigDecimal mposTdSupplyAmt) {
        this.mposTdSupplyAmt = mposTdSupplyAmt;
    }

    public BigDecimal getOtherSupplyAmt() {
        return otherSupplyAmt;
    }

    public void setOtherSupplyAmt(BigDecimal otherSupplyAmt) {
        this.otherSupplyAmt = otherSupplyAmt;
    }

    public BigDecimal getPosRewardAmt() {
        return posRewardAmt;
    }

    public void setPosRewardAmt(BigDecimal posRewardAmt) {
        this.posRewardAmt = posRewardAmt;
    }

    public BigDecimal getDeductionTaxMonthAgoAmt() {
        return deductionTaxMonthAgoAmt;
    }

    public void setDeductionTaxMonthAgoAmt(BigDecimal deductionTaxMonthAgoAmt) {
        this.deductionTaxMonthAgoAmt = deductionTaxMonthAgoAmt;
    }

    public BigDecimal getDeductionTaxMonthAmt() {
        return deductionTaxMonthAmt;
    }

    public void setDeductionTaxMonthAmt(BigDecimal deductionTaxMonthAmt) {
        this.deductionTaxMonthAmt = deductionTaxMonthAmt;
    }

    public BigDecimal getSupplyTaxAmt() {
        return supplyTaxAmt;
    }

    public void setSupplyTaxAmt(BigDecimal supplyTaxAmt) {
        this.supplyTaxAmt = supplyTaxAmt;
    }

    public BigDecimal getRealProfitAmt() {
        return realProfitAmt;
    }

    public void setRealProfitAmt(BigDecimal realProfitAmt) {
        this.realProfitAmt = realProfitAmt;
    }

    public BigDecimal getProfitMonthAmt() {
        return profitMonthAmt;
    }

    public void setProfitMonthAmt(BigDecimal profitMonthAmt) {
        this.profitMonthAmt = profitMonthAmt;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getOpenBankName() {
        return openBankName;
    }

    public void setOpenBankName(String openBankName) {
        this.openBankName = openBankName == null ? null : openBankName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus == null ? null : payStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}