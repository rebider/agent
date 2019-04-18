package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PToolSupply implements Serializable {
    private String id;

    private String profitDate;

    private String agentName;

    private String agentId;

    private String parenterAgentName;

    private String parenterAgentId;

    private String busCode;

    private String netInDate;

    private BigDecimal toolsInvoiceAmt;

    private BigDecimal monthProfitAmt;

    private BigDecimal repaymentPeriod;

    private BigDecimal remitAmt;

    private BigDecimal parenterSupplyAmt;

    private String cUser;

    private Date cTime;

    private String examinrId;

    private String examinrStatus;

    private String deductionId;

    private String rev1;

    private String rev2;

    private String rev3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProfitDate() {
        return profitDate;
    }

    public void setProfitDate(String profitDate) {
        this.profitDate = profitDate == null ? null : profitDate.trim();
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getParenterAgentName() {
        return parenterAgentName;
    }

    public void setParenterAgentName(String parenterAgentName) {
        this.parenterAgentName = parenterAgentName == null ? null : parenterAgentName.trim();
    }

    public String getParenterAgentId() {
        return parenterAgentId;
    }

    public void setParenterAgentId(String parenterAgentId) {
        this.parenterAgentId = parenterAgentId == null ? null : parenterAgentId.trim();
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode == null ? null : busCode.trim();
    }

    public String getNetInDate() {
        return netInDate;
    }

    public void setNetInDate(String netInDate) {
        this.netInDate = netInDate == null ? null : netInDate.trim();
    }

    public BigDecimal getToolsInvoiceAmt() {
        return toolsInvoiceAmt;
    }

    public void setToolsInvoiceAmt(BigDecimal toolsInvoiceAmt) {
        this.toolsInvoiceAmt = toolsInvoiceAmt;
    }

    public BigDecimal getMonthProfitAmt() {
        return monthProfitAmt;
    }

    public void setMonthProfitAmt(BigDecimal monthProfitAmt) {
        this.monthProfitAmt = monthProfitAmt;
    }

    public BigDecimal getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public void setRepaymentPeriod(BigDecimal repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    public BigDecimal getRemitAmt() {
        return remitAmt;
    }

    public void setRemitAmt(BigDecimal remitAmt) {
        this.remitAmt = remitAmt;
    }

    public BigDecimal getParenterSupplyAmt() {
        return parenterSupplyAmt;
    }

    public void setParenterSupplyAmt(BigDecimal parenterSupplyAmt) {
        this.parenterSupplyAmt = parenterSupplyAmt;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public String getExaminrId() {
        return examinrId;
    }

    public void setExaminrId(String examinrId) {
        this.examinrId = examinrId == null ? null : examinrId.trim();
    }

    public String getExaminrStatus() {
        return examinrStatus;
    }

    public void setExaminrStatus(String examinrStatus) {
        this.examinrStatus = examinrStatus == null ? null : examinrStatus.trim();
    }

    public String getDeductionId() {
        return deductionId;
    }

    public void setDeductionId(String deductionId) {
        this.deductionId = deductionId == null ? null : deductionId.trim();
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