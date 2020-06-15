package com.ryx.kafka.pojo;

import java.math.BigDecimal;

public class KfkSendMessage {
    private String id;

    private String agentId;

    private String agentName;

    private String busid;

    private String busnum;

    private String ktype;

    private String ktopic;

    private String cDateStr;

    private String cTimeStr;

    private BigDecimal status;

    private String kmessage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid == null ? null : busid.trim();
    }

    public String getBusnum() {
        return busnum;
    }

    public void setBusnum(String busnum) {
        this.busnum = busnum == null ? null : busnum.trim();
    }

    public String getKtype() {
        return ktype;
    }

    public void setKtype(String ktype) {
        this.ktype = ktype == null ? null : ktype.trim();
    }

    public String getKtopic() {
        return ktopic;
    }

    public void setKtopic(String ktopic) {
        this.ktopic = ktopic == null ? null : ktopic.trim();
    }

    public String getcDateStr() {
        return cDateStr;
    }

    public void setcDateStr(String cDateStr) {
        this.cDateStr = cDateStr == null ? null : cDateStr.trim();
    }

    public String getcTimeStr() {
        return cTimeStr;
    }

    public void setcTimeStr(String cTimeStr) {
        this.cTimeStr = cTimeStr == null ? null : cTimeStr.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public String getKmessage() {
        return kmessage;
    }

    public void setKmessage(String kmessage) {
        this.kmessage = kmessage == null ? null : kmessage.trim();
    }
}