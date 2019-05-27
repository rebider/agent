package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransProfitDetail implements Serializable {
    private String id;

    private String busNum;

    private String agentId;

    private String agentName;

    private String parentBusNum;

    private String parentAgentId;

    private String profitDate;

    private BigDecimal inTransAmt;

    private BigDecimal outTransAmt;

    private BigDecimal profitAmt;

    private String busCode;

    private BigDecimal inProfitScale;

    private BigDecimal outProfitScale;

    private BigDecimal inProfitAmt;

    private BigDecimal outProfitAmt;

    private BigDecimal posCreditAmt;

    private BigDecimal iposCreditAmt;

    private BigDecimal posRewardAmt;

    private String payCompany;

    private BigDecimal notaxAmt;

    private BigDecimal supplyAmt;

    private BigDecimal transFee;

    private String unicode;

    private String sourceInfo;

    private String agentType;

    private BigDecimal unlineAmt;

    private String parentAgentName;

    private BigDecimal posTranAmt;

    private BigDecimal posTranFee;

    private BigDecimal posPftAmt;

    private BigDecimal qrTranAmt;

    private BigDecimal qrTranFee;

    private BigDecimal qrPftAmt;

    private String remark;

    private BigDecimal serverAmt;

    public BigDecimal getServerAmt() {
        return serverAmt;
    }

    public void setServerAmt(BigDecimal serverAmt) {
        this.serverAmt = serverAmt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum == null ? null : busNum.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public String getParentBusNum() {
        return parentBusNum;
    }

    public void setParentBusNum(String parentBusNum) {
        this.parentBusNum = parentBusNum == null ? null : parentBusNum.trim();
    }

    public String getParentAgentId() {
        return parentAgentId;
    }

    public void setParentAgentId(String parentAgentId) {
        this.parentAgentId = parentAgentId == null ? null : parentAgentId.trim();
    }

    public String getProfitDate() {
        return profitDate;
    }

    public void setProfitDate(String profitDate) {
        this.profitDate = profitDate == null ? null : profitDate.trim();
    }

    public BigDecimal getInTransAmt() {
        return inTransAmt;
    }

    public void setInTransAmt(BigDecimal inTransAmt) {
        this.inTransAmt = inTransAmt;
    }

    public BigDecimal getOutTransAmt() {
        return outTransAmt;
    }

    public void setOutTransAmt(BigDecimal outTransAmt) {
        this.outTransAmt = outTransAmt;
    }

    public BigDecimal getProfitAmt() {
        return profitAmt;
    }

    public void setProfitAmt(BigDecimal profitAmt) {
        this.profitAmt = profitAmt;
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode == null ? null : busCode.trim();
    }

    public BigDecimal getInProfitScale() {
        return inProfitScale;
    }

    public void setInProfitScale(BigDecimal inProfitScale) {
        this.inProfitScale = inProfitScale;
    }

    public BigDecimal getOutProfitScale() {
        return outProfitScale;
    }

    public void setOutProfitScale(BigDecimal outProfitScale) {
        this.outProfitScale = outProfitScale;
    }

    public BigDecimal getInProfitAmt() {
        return inProfitAmt;
    }

    public void setInProfitAmt(BigDecimal inProfitAmt) {
        this.inProfitAmt = inProfitAmt;
    }

    public BigDecimal getOutProfitAmt() {
        return outProfitAmt;
    }

    public void setOutProfitAmt(BigDecimal outProfitAmt) {
        this.outProfitAmt = outProfitAmt;
    }

    public BigDecimal getPosCreditAmt() {
        return posCreditAmt;
    }

    public void setPosCreditAmt(BigDecimal posCreditAmt) {
        this.posCreditAmt = posCreditAmt;
    }

    public BigDecimal getIposCreditAmt() {
        return iposCreditAmt;
    }

    public void setIposCreditAmt(BigDecimal iposCreditAmt) {
        this.iposCreditAmt = iposCreditAmt;
    }

    public BigDecimal getPosRewardAmt() {
        return posRewardAmt;
    }

    public void setPosRewardAmt(BigDecimal posRewardAmt) {
        this.posRewardAmt = posRewardAmt;
    }

    public String getPayCompany() {
        return payCompany;
    }

    public void setPayCompany(String payCompany) {
        this.payCompany = payCompany == null ? null : payCompany.trim();
    }

    public BigDecimal getNotaxAmt() {
        return notaxAmt;
    }

    public void setNotaxAmt(BigDecimal notaxAmt) {
        this.notaxAmt = notaxAmt;
    }

    public BigDecimal getSupplyAmt() {
        return supplyAmt;
    }

    public void setSupplyAmt(BigDecimal supplyAmt) {
        this.supplyAmt = supplyAmt;
    }

    public BigDecimal getTransFee() {
        return transFee;
    }

    public void setTransFee(BigDecimal transFee) {
        this.transFee = transFee;
    }

    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode == null ? null : unicode.trim();
    }

    public String getSourceInfo() {
        return sourceInfo;
    }

    public void setSourceInfo(String sourceInfo) {
        this.sourceInfo = sourceInfo == null ? null : sourceInfo.trim();
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType == null ? null : agentType.trim();
    }

    public BigDecimal getUnlineAmt() {
        return unlineAmt;
    }

    public void setUnlineAmt(BigDecimal unlineAmt) {
        this.unlineAmt = unlineAmt;
    }

    public String getParentAgentName() {
        return parentAgentName;
    }

    public void setParentAgentName(String parentAgentName) {
        this.parentAgentName = parentAgentName == null ? null : parentAgentName.trim();
    }

    public BigDecimal getPosTranAmt() {
        return posTranAmt;
    }

    public void setPosTranAmt(BigDecimal posTranAmt) {
        this.posTranAmt = posTranAmt;
    }

    public BigDecimal getPosTranFee() {
        return posTranFee;
    }

    public void setPosTranFee(BigDecimal posTranFee) {
        this.posTranFee = posTranFee;
    }

    public BigDecimal getPosPftAmt() {
        return posPftAmt;
    }

    public void setPosPftAmt(BigDecimal posPftAmt) {
        this.posPftAmt = posPftAmt;
    }

    public BigDecimal getQrTranAmt() {
        return qrTranAmt;
    }

    public void setQrTranAmt(BigDecimal qrTranAmt) {
        this.qrTranAmt = qrTranAmt;
    }

    public BigDecimal getQrTranFee() {
        return qrTranFee;
    }

    public void setQrTranFee(BigDecimal qrTranFee) {
        this.qrTranFee = qrTranFee;
    }

    public BigDecimal getQrPftAmt() {
        return qrPftAmt;
    }

    public void setQrPftAmt(BigDecimal qrPftAmt) {
        this.qrPftAmt = qrPftAmt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}