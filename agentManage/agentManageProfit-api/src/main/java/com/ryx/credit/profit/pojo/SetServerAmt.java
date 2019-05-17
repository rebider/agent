package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class SetServerAmt implements Serializable {
    private String id;

    private String createDate;

    private String agentId;

    private String agentName;

    private String bumCode;

    private String bumId;

    private String serverType;

    private String chargePeriod;

    private BigDecimal chargeProportion;

    private String chargeType;

    private String chargeBase;

    private String isNoSon;

    private String status;

    private String cUser;

    private String rev1;

    private String rev2;

    private String rev3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
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

    public String getBumCode() {
        return bumCode;
    }

    public void setBumCode(String bumCode) {
        this.bumCode = bumCode == null ? null : bumCode.trim();
    }

    public String getBumId() {
        return bumId;
    }

    public void setBumId(String bumId) {
        this.bumId = bumId == null ? null : bumId.trim();
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType == null ? null : serverType.trim();
    }

    public String getChargePeriod() {
        return chargePeriod;
    }

    public void setChargePeriod(String chargePeriod) {
        this.chargePeriod = chargePeriod == null ? null : chargePeriod.trim();
    }

    public BigDecimal getChargeProportion() {
        return chargeProportion;
    }

    public void setChargeProportion(BigDecimal chargeProportion) {
        this.chargeProportion = chargeProportion;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType == null ? null : chargeType.trim();
    }

    public String getChargeBase() {
        return chargeBase;
    }

    public void setChargeBase(String chargeBase) {
        this.chargeBase = chargeBase == null ? null : chargeBase.trim();
    }

    public String getIsNoSon() {
        return isNoSon;
    }

    public void setIsNoSon(String isNoSon) {
        this.isNoSon = isNoSon == null ? null : isNoSon.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
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