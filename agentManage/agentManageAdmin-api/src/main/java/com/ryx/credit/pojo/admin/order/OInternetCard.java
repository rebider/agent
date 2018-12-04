package com.ryx.credit.pojo.admin.order;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OInternetCard implements Serializable{
    private String id;

    private String snNum;

    private String iccidNum;

    private String logisticsId;

    private String logisticsDetailId;

    private String proCom;

    private String agentId;

    private String agentName;

    private BigDecimal cardStatus;

    private String exceedFlow;

    private String exceedFlowUnit;

    private BigDecimal debtAmt;

    private Date cTime;

    private Date uTime;

    private BigDecimal version;

    private BigDecimal status;

    private String cUser;

    private String uUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSnNum() {
        return snNum;
    }

    public void setSnNum(String snNum) {
        this.snNum = snNum == null ? null : snNum.trim();
    }

    public String getIccidNum() {
        return iccidNum;
    }

    public void setIccidNum(String iccidNum) {
        this.iccidNum = iccidNum == null ? null : iccidNum.trim();
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId == null ? null : logisticsId.trim();
    }

    public String getLogisticsDetailId() {
        return logisticsDetailId;
    }

    public void setLogisticsDetailId(String logisticsDetailId) {
        this.logisticsDetailId = logisticsDetailId == null ? null : logisticsDetailId.trim();
    }

    public String getProCom() {
        return proCom;
    }

    public void setProCom(String proCom) {
        this.proCom = proCom == null ? null : proCom.trim();
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

    public BigDecimal getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(BigDecimal cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getExceedFlow() {
        return exceedFlow;
    }

    public void setExceedFlow(String exceedFlow) {
        this.exceedFlow = exceedFlow == null ? null : exceedFlow.trim();
    }

    public String getExceedFlowUnit() {
        return exceedFlowUnit;
    }

    public void setExceedFlowUnit(String exceedFlowUnit) {
        this.exceedFlowUnit = exceedFlowUnit == null ? null : exceedFlowUnit.trim();
    }

    public BigDecimal getDebtAmt() {
        return debtAmt;
    }

    public void setDebtAmt(BigDecimal debtAmt) {
        this.debtAmt = debtAmt;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getuTime() {
        return uTime;
    }

    public void setuTime(Date uTime) {
        this.uTime = uTime;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public String getuUser() {
        return uUser;
    }

    public void setuUser(String uUser) {
        this.uUser = uUser == null ? null : uUser.trim();
    }
}