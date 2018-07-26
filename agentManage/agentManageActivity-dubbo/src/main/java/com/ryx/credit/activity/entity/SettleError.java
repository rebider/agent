package com.ryx.credit.activity.entity;

import java.math.BigDecimal;

public class SettleError {
    private String id;

    private String errCode;

    private String tranLs;

    private String tranDate;

    private String businessType;

    private String cooperationMode;

    private String merchId;

    private String merchType;

    private String errDate;

    private String chargebackDate;

    private String errType;

    private String settleDate;

    private String tranTime;

    private String cardNo;

    private BigDecimal tranAmt;

    private String hostLs;

    private String tranType;

    private String errDesc;

    private String errLs;

    private String oldErrLs;

    private String errFlag;

    private String freezeDate;

    private BigDecimal offsetBalanceAmt;

    private BigDecimal balanceAmt;

    private BigDecimal netAmt;

    private String repaymentType;

    private String longShortType;

    private String recordStatus;

    private String chargeoffType;

    private String hbMerchId;

    private String hbOrg;

    private String hbPhone;

    private String hbName;

    private String hbTermId;

    private String balanceDate;

    private String nettingStatus;

    private BigDecimal realDeductAmt;

    private BigDecimal makeAmt;

    private BigDecimal lossAmt;

    private String hbOrgId;

    private BigDecimal offsetLossAmt;

    private String provinces;

    private String fenrunFlag;

    private String instId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode == null ? null : errCode.trim();
    }

    public String getTranLs() {
        return tranLs;
    }

    public void setTranLs(String tranLs) {
        this.tranLs = tranLs == null ? null : tranLs.trim();
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate == null ? null : tranDate.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getCooperationMode() {
        return cooperationMode;
    }

    public void setCooperationMode(String cooperationMode) {
        this.cooperationMode = cooperationMode == null ? null : cooperationMode.trim();
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId == null ? null : merchId.trim();
    }

    public String getMerchType() {
        return merchType;
    }

    public void setMerchType(String merchType) {
        this.merchType = merchType == null ? null : merchType.trim();
    }

    public String getErrDate() {
        return errDate;
    }

    public void setErrDate(String errDate) {
        this.errDate = errDate == null ? null : errDate.trim();
    }

    public String getChargebackDate() {
        return chargebackDate;
    }

    public void setChargebackDate(String chargebackDate) {
        this.chargebackDate = chargebackDate == null ? null : chargebackDate.trim();
    }

    public String getErrType() {
        return errType;
    }

    public void setErrType(String errType) {
        this.errType = errType == null ? null : errType.trim();
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate == null ? null : settleDate.trim();
    }

    public String getTranTime() {
        return tranTime;
    }

    public void setTranTime(String tranTime) {
        this.tranTime = tranTime == null ? null : tranTime.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public BigDecimal getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(BigDecimal tranAmt) {
        this.tranAmt = tranAmt;
    }

    public String getHostLs() {
        return hostLs;
    }

    public void setHostLs(String hostLs) {
        this.hostLs = hostLs == null ? null : hostLs.trim();
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType == null ? null : tranType.trim();
    }

    public String getErrDesc() {
        return errDesc;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc == null ? null : errDesc.trim();
    }

    public String getErrLs() {
        return errLs;
    }

    public void setErrLs(String errLs) {
        this.errLs = errLs == null ? null : errLs.trim();
    }

    public String getOldErrLs() {
        return oldErrLs;
    }

    public void setOldErrLs(String oldErrLs) {
        this.oldErrLs = oldErrLs == null ? null : oldErrLs.trim();
    }

    public String getErrFlag() {
        return errFlag;
    }

    public void setErrFlag(String errFlag) {
        this.errFlag = errFlag == null ? null : errFlag.trim();
    }

    public String getFreezeDate() {
        return freezeDate;
    }

    public void setFreezeDate(String freezeDate) {
        this.freezeDate = freezeDate == null ? null : freezeDate.trim();
    }

    public BigDecimal getOffsetBalanceAmt() {
        return offsetBalanceAmt;
    }

    public void setOffsetBalanceAmt(BigDecimal offsetBalanceAmt) {
        this.offsetBalanceAmt = offsetBalanceAmt;
    }

    public BigDecimal getBalanceAmt() {
        return balanceAmt;
    }

    public void setBalanceAmt(BigDecimal balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

    public BigDecimal getNetAmt() {
        return netAmt;
    }

    public void setNetAmt(BigDecimal netAmt) {
        this.netAmt = netAmt;
    }

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType == null ? null : repaymentType.trim();
    }

    public String getLongShortType() {
        return longShortType;
    }

    public void setLongShortType(String longShortType) {
        this.longShortType = longShortType == null ? null : longShortType.trim();
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus == null ? null : recordStatus.trim();
    }

    public String getChargeoffType() {
        return chargeoffType;
    }

    public void setChargeoffType(String chargeoffType) {
        this.chargeoffType = chargeoffType == null ? null : chargeoffType.trim();
    }

    public String getHbMerchId() {
        return hbMerchId;
    }

    public void setHbMerchId(String hbMerchId) {
        this.hbMerchId = hbMerchId == null ? null : hbMerchId.trim();
    }

    public String getHbOrg() {
        return hbOrg;
    }

    public void setHbOrg(String hbOrg) {
        this.hbOrg = hbOrg == null ? null : hbOrg.trim();
    }

    public String getHbPhone() {
        return hbPhone;
    }

    public void setHbPhone(String hbPhone) {
        this.hbPhone = hbPhone == null ? null : hbPhone.trim();
    }

    public String getHbName() {
        return hbName;
    }

    public void setHbName(String hbName) {
        this.hbName = hbName == null ? null : hbName.trim();
    }

    public String getHbTermId() {
        return hbTermId;
    }

    public void setHbTermId(String hbTermId) {
        this.hbTermId = hbTermId == null ? null : hbTermId.trim();
    }

    public String getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(String balanceDate) {
        this.balanceDate = balanceDate == null ? null : balanceDate.trim();
    }

    public String getNettingStatus() {
        return nettingStatus;
    }

    public void setNettingStatus(String nettingStatus) {
        this.nettingStatus = nettingStatus == null ? null : nettingStatus.trim();
    }

    public BigDecimal getRealDeductAmt() {
        return realDeductAmt;
    }

    public void setRealDeductAmt(BigDecimal realDeductAmt) {
        this.realDeductAmt = realDeductAmt;
    }

    public BigDecimal getMakeAmt() {
        return makeAmt;
    }

    public void setMakeAmt(BigDecimal makeAmt) {
        this.makeAmt = makeAmt;
    }

    public BigDecimal getLossAmt() {
        return lossAmt;
    }

    public void setLossAmt(BigDecimal lossAmt) {
        this.lossAmt = lossAmt;
    }

    public String getHbOrgId() {
        return hbOrgId;
    }

    public void setHbOrgId(String hbOrgId) {
        this.hbOrgId = hbOrgId == null ? null : hbOrgId.trim();
    }

    public BigDecimal getOffsetLossAmt() {
        return offsetLossAmt;
    }

    public void setOffsetLossAmt(BigDecimal offsetLossAmt) {
        this.offsetLossAmt = offsetLossAmt;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces == null ? null : provinces.trim();
    }

    public String getFenrunFlag() {
        return fenrunFlag;
    }

    public void setFenrunFlag(String fenrunFlag) {
        this.fenrunFlag = fenrunFlag == null ? null : fenrunFlag.trim();
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId == null ? null : instId.trim();
    }
}