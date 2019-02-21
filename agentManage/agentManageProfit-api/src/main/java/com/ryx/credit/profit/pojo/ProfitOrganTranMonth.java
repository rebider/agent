package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProfitOrganTranMonth implements Serializable {
    private String profitDateStart;
    private String profitDateEnd;

    public String getProfitDateStart() {
        return profitDateStart;
    }

    public void setProfitDateStart(String profitDateStart) {
        this.profitDateStart = profitDateStart;
    }

    public String getProfitDateEnd() {
        return profitDateEnd;
    }

    public void setProfitDateEnd(String profitDateEnd) {
        this.profitDateEnd = profitDateEnd;
    }

    private String id;

    private String productType;

    private String productName;

    private BigDecimal tranAmt;

    private BigDecimal settleAmt;

    private String checkDate;

    private String profitDate;

    private BigDecimal differenceAmt;

    private String status;

    private String remark;

    private BigDecimal tranFee;

    private BigDecimal settleFee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public BigDecimal getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(BigDecimal tranAmt) {
        this.tranAmt = tranAmt;
    }

    public BigDecimal getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(BigDecimal settleAmt) {
        this.settleAmt = settleAmt;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate == null ? null : checkDate.trim();
    }

    public String getProfitDate() {
        return profitDate;
    }

    public void setProfitDate(String profitDate) {
        this.profitDate = profitDate == null ? null : profitDate.trim();
    }

    public BigDecimal getDifferenceAmt() {
        return differenceAmt;
    }

    public void setDifferenceAmt(BigDecimal differenceAmt) {
        this.differenceAmt = differenceAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getTranFee() {
        return tranFee;
    }

    public void setTranFee(BigDecimal tranFee) {
        this.tranFee = tranFee;
    }

    public BigDecimal getSettleFee() {
        return settleFee;
    }

    public void setSettleFee(BigDecimal settleFee) {
        this.settleFee = settleFee;
    }
}