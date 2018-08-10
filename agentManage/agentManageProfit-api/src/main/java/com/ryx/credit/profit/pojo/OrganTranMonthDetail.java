package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrganTranMonthDetail implements Serializable {
    private static final long serialVersionUID = -6970085386873020157L;
    private String id;

    private String agentPid;

    private String agentId;

    private String agentName;

    private BigDecimal zPosTranAmt;

    private BigDecimal posTranAmt;

    private BigDecimal posJlTranAmt;

    private String profitId;

    private String agentType;

    private BigDecimal allchildJlTranAmt;

    private BigDecimal allchildTranAmt;

    private String profitDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAgentPid() {
        return agentPid;
    }

    public void setAgentPid(String agentPid) {
        this.agentPid = agentPid == null ? null : agentPid.trim();
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

    public BigDecimal getzPosTranAmt() {
        return zPosTranAmt;
    }

    public void setzPosTranAmt(BigDecimal zPosTranAmt) {
        this.zPosTranAmt = zPosTranAmt;
    }

    public BigDecimal getPosTranAmt() {
        return posTranAmt;
    }

    public void setPosTranAmt(BigDecimal posTranAmt) {
        this.posTranAmt = posTranAmt;
    }

    public BigDecimal getPosJlTranAmt() {
        return posJlTranAmt;
    }

    public void setPosJlTranAmt(BigDecimal posJlTranAmt) {
        this.posJlTranAmt = posJlTranAmt;
    }

    public String getProfitId() {
        return profitId;
    }

    public void setProfitId(String profitId) {
        this.profitId = profitId == null ? null : profitId.trim();
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType == null ? null : agentType.trim();
    }

    public BigDecimal getAllchildJlTranAmt() {
        return allchildJlTranAmt;
    }

    public void setAllchildJlTranAmt(BigDecimal allchildJlTranAmt) {
        this.allchildJlTranAmt = allchildJlTranAmt;
    }

    public BigDecimal getAllchildTranAmt() {
        return allchildTranAmt;
    }

    public void setAllchildTranAmt(BigDecimal allchildTranAmt) {
        this.allchildTranAmt = allchildTranAmt;
    }

    public String getProfitDate() {
        return profitDate;
    }

    public void setProfitDate(String profitDate) {
        this.profitDate = profitDate == null ? null : profitDate.trim();
    }
}