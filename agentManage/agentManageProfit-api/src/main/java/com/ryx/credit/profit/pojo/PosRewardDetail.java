package com.ryx.credit.profit.pojo;

import java.io.Serializable;

/**
 * @author RYX
 */
public class PosRewardDetail implements Serializable {

    private static final long serialVersionUID = -3169932965261141606L;
    private String id;

    private String profitPosDate;

    private String posAgentId;

    private String posAgentName;

    private String posMechanismType;

    private String posMechanismId;

    private String posCompareCount;

    private String posCurrentCount;

    private String posCompareLoanCount;

    private String posCurrentLoanCount;

    private String posAmt;

    private String posStandard;

    private String posOwnReward;

    private String posDownReward;

    private String posReawrdProfit;

    private String posRemark;

    private String posCheckDeductAmt;

    private String childAgentIdList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProfitPosDate() {
        return profitPosDate;
    }

    public void setProfitPosDate(String profitPosDate) {
        this.profitPosDate = profitPosDate == null ? null : profitPosDate.trim();
    }

    public String getPosAgentId() {
        return posAgentId;
    }

    public void setPosAgentId(String posAgentId) {
        this.posAgentId = posAgentId == null ? null : posAgentId.trim();
    }

    public String getPosAgentName() {
        return posAgentName;
    }

    public void setPosAgentName(String posAgentName) {
        this.posAgentName = posAgentName == null ? null : posAgentName.trim();
    }

    public String getPosMechanismType() {
        return posMechanismType;
    }

    public void setPosMechanismType(String posMechanismType) {
        this.posMechanismType = posMechanismType == null ? null : posMechanismType.trim();
    }

    public String getPosMechanismId() {
        return posMechanismId;
    }

    public void setPosMechanismId(String posMechanismId) {
        this.posMechanismId = posMechanismId == null ? null : posMechanismId.trim();
    }

    public String getPosCompareCount() {
        return posCompareCount;
    }

    public void setPosCompareCount(String posCompareCount) {
        this.posCompareCount = posCompareCount == null ? null : posCompareCount.trim();
    }

    public String getPosCurrentCount() {
        return posCurrentCount;
    }

    public void setPosCurrentCount(String posCurrentCount) {
        this.posCurrentCount = posCurrentCount == null ? null : posCurrentCount.trim();
    }

    public String getPosCompareLoanCount() {
        return posCompareLoanCount;
    }

    public void setPosCompareLoanCount(String posCompareLoanCount) {
        this.posCompareLoanCount = posCompareLoanCount == null ? null : posCompareLoanCount.trim();
    }

    public String getPosCurrentLoanCount() {
        return posCurrentLoanCount;
    }

    public void setPosCurrentLoanCount(String posCurrentLoanCount) {
        this.posCurrentLoanCount = posCurrentLoanCount == null ? null : posCurrentLoanCount.trim();
    }

    public String getPosAmt() {
        return posAmt;
    }

    public void setPosAmt(String posAmt) {
        this.posAmt = posAmt == null ? null : posAmt.trim();
    }

    public String getPosStandard() {
        return posStandard;
    }

    public void setPosStandard(String posStandard) {
        this.posStandard = posStandard == null ? null : posStandard.trim();
    }

    public String getPosOwnReward() {
        return posOwnReward;
    }

    public void setPosOwnReward(String posOwnReward) {
        this.posOwnReward = posOwnReward == null ? null : posOwnReward.trim();
    }

    public String getPosDownReward() {
        return posDownReward;
    }

    public void setPosDownReward(String posDownReward) {
        this.posDownReward = posDownReward == null ? null : posDownReward.trim();
    }

    public String getPosReawrdProfit() {
        return posReawrdProfit;
    }

    public void setPosReawrdProfit(String posReawrdProfit) {
        this.posReawrdProfit = posReawrdProfit == null ? null : posReawrdProfit.trim();
    }

    public String getPosRemark() {
        return posRemark;
    }

    public void setPosRemark(String posRemark) {
        this.posRemark = posRemark == null ? null : posRemark.trim();
    }

    public String getPosCheckDeductAmt() {
        return posCheckDeductAmt;
    }

    public void setPosCheckDeductAmt(String posCheckDeductAmt) {
        this.posCheckDeductAmt = posCheckDeductAmt == null ? null : posCheckDeductAmt.trim();
    }

    public String getChildAgentIdList() {
        return childAgentIdList;
    }

    public void setChildAgentIdList(String childAgentIdList) {
        this.childAgentIdList = childAgentIdList == null ? null : childAgentIdList.trim();
    }
}