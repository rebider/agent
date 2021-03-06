package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OLogisticsDetail implements Serializable {
    private String id;

    private String orderId;

    private String logisticsId;

    private String proId;

    private String proName;

    private BigDecimal settlementPrice;

    private String activityId;

    private String activityName;

    private BigDecimal gTime;

    private String snNum;

    private String agentId;

    private String optId;

    private String optType;

    private String returnOrderId;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal recordStatus;

    private BigDecimal version;

    private String orderNum;

    private String terminalid;

    private String terminalidKey;

    private String terminalidSeq;

    private String terminalidType;

    private String busProCode;

    private String busProName;

    private String termBatchcode;

    private String termBatchname;

    private String termtype;

    private String termtypename;

    private BigDecimal sendStatus;

    private String terminalidCheck;

    private BigDecimal posSpePrice;

    private String posType;

    private BigDecimal standTime;

    private String busId;

    private BigDecimal sbusStatus;

    private BigDecimal sbusBatch;

    private String sbusMsg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId == null ? null : logisticsId.trim();
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId == null ? null : proId.trim();
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName == null ? null : proName.trim();
    }

    public BigDecimal getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(BigDecimal settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    public BigDecimal getgTime() {
        return gTime;
    }

    public void setgTime(BigDecimal gTime) {
        this.gTime = gTime;
    }

    public String getSnNum() {
        return snNum;
    }

    public void setSnNum(String snNum) {
        this.snNum = snNum == null ? null : snNum.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getOptId() {
        return optId;
    }

    public void setOptId(String optId) {
        this.optId = optId == null ? null : optId.trim();
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType == null ? null : optType.trim();
    }

    public String getReturnOrderId() {
        return returnOrderId;
    }

    public void setReturnOrderId(String returnOrderId) {
        this.returnOrderId = returnOrderId == null ? null : returnOrderId.trim();
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

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public BigDecimal getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(BigDecimal recordStatus) {
        this.recordStatus = recordStatus;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid == null ? null : terminalid.trim();
    }

    public String getTerminalidKey() {
        return terminalidKey;
    }

    public void setTerminalidKey(String terminalidKey) {
        this.terminalidKey = terminalidKey == null ? null : terminalidKey.trim();
    }

    public String getTerminalidSeq() {
        return terminalidSeq;
    }

    public void setTerminalidSeq(String terminalidSeq) {
        this.terminalidSeq = terminalidSeq == null ? null : terminalidSeq.trim();
    }

    public String getTerminalidType() {
        return terminalidType;
    }

    public void setTerminalidType(String terminalidType) {
        this.terminalidType = terminalidType == null ? null : terminalidType.trim();
    }

    public String getBusProCode() {
        return busProCode;
    }

    public void setBusProCode(String busProCode) {
        this.busProCode = busProCode == null ? null : busProCode.trim();
    }

    public String getBusProName() {
        return busProName;
    }

    public void setBusProName(String busProName) {
        this.busProName = busProName == null ? null : busProName.trim();
    }

    public String getTermBatchcode() {
        return termBatchcode;
    }

    public void setTermBatchcode(String termBatchcode) {
        this.termBatchcode = termBatchcode == null ? null : termBatchcode.trim();
    }

    public String getTermBatchname() {
        return termBatchname;
    }

    public void setTermBatchname(String termBatchname) {
        this.termBatchname = termBatchname == null ? null : termBatchname.trim();
    }

    public String getTermtype() {
        return termtype;
    }

    public void setTermtype(String termtype) {
        this.termtype = termtype == null ? null : termtype.trim();
    }

    public String getTermtypename() {
        return termtypename;
    }

    public void setTermtypename(String termtypename) {
        this.termtypename = termtypename == null ? null : termtypename.trim();
    }

    public BigDecimal getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(BigDecimal sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getTerminalidCheck() {
        return terminalidCheck;
    }

    public void setTerminalidCheck(String terminalidCheck) {
        this.terminalidCheck = terminalidCheck == null ? null : terminalidCheck.trim();
    }

    public BigDecimal getPosSpePrice() {
        return posSpePrice;
    }

    public void setPosSpePrice(BigDecimal posSpePrice) {
        this.posSpePrice = posSpePrice;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType == null ? null : posType.trim();
    }

    public BigDecimal getStandTime() {
        return standTime;
    }

    public void setStandTime(BigDecimal standTime) {
        this.standTime = standTime;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId == null ? null : busId.trim();
    }

    public BigDecimal getSbusStatus() {
        return sbusStatus;
    }

    public void setSbusStatus(BigDecimal sbusStatus) {
        this.sbusStatus = sbusStatus;
    }

    public BigDecimal getSbusBatch() {
        return sbusBatch;
    }

    public void setSbusBatch(BigDecimal sbusBatch) {
        this.sbusBatch = sbusBatch;
    }

    public String getSbusMsg() {
        return sbusMsg;
    }

    public void setSbusMsg(String sbusMsg) {
        this.sbusMsg = sbusMsg == null ? null : sbusMsg.trim();
    }
}