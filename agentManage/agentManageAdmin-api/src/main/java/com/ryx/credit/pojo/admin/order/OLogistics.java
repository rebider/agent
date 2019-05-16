package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OLogistics implements Serializable{
    private String id;

    private String orderId;

    private String srcId;

    private String receiptPlanId;

    private String proCom;

    private String proId;

    private String proName;

    private String proType;

    private BigDecimal sendNum;

    private BigDecimal proPrice;

    private String proModel;

    private Date sendDate;

    private String logCom;

    private String wNumber;

    private BigDecimal isdeall;

    private String snBeginNum;

    private String snEndNum;

    private String logType;

    private Date cTime;

    private String cUser;

    private BigDecimal status;

    private BigDecimal sendStatus;

    private String sendMsg;

    private BigDecimal version;

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

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId == null ? null : srcId.trim();
    }

    public String getReceiptPlanId() {
        return receiptPlanId;
    }

    public void setReceiptPlanId(String receiptPlanId) {
        this.receiptPlanId = receiptPlanId == null ? null : receiptPlanId.trim();
    }

    public String getProCom() {
        return proCom;
    }

    public void setProCom(String proCom) {
        this.proCom = proCom == null ? null : proCom.trim();
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

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType == null ? null : proType.trim();
    }

    public BigDecimal getSendNum() {
        return sendNum;
    }

    public void setSendNum(BigDecimal sendNum) {
        this.sendNum = sendNum;
    }

    public BigDecimal getProPrice() {
        return proPrice;
    }

    public void setProPrice(BigDecimal proPrice) {
        this.proPrice = proPrice;
    }

    public String getProModel() {
        return proModel;
    }

    public void setProModel(String proModel) {
        this.proModel = proModel == null ? null : proModel.trim();
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getLogCom() {
        return logCom;
    }

    public void setLogCom(String logCom) {
        this.logCom = logCom == null ? null : logCom.trim();
    }

    public String getwNumber() {
        return wNumber;
    }

    public void setwNumber(String wNumber) {
        this.wNumber = wNumber == null ? null : wNumber.trim();
    }

    public BigDecimal getIsdeall() {
        return isdeall;
    }

    public void setIsdeall(BigDecimal isdeall) {
        this.isdeall = isdeall;
    }

    public String getSnBeginNum() {
        return snBeginNum;
    }

    public void setSnBeginNum(String snBeginNum) {
        this.snBeginNum = snBeginNum == null ? null : snBeginNum.trim();
    }

    public String getSnEndNum() {
        return snEndNum;
    }

    public void setSnEndNum(String snEndNum) {
        this.snEndNum = snEndNum == null ? null : snEndNum.trim();
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType == null ? null : logType.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public BigDecimal getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(BigDecimal sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getSendMsg() {
        return sendMsg;
    }

    public void setSendMsg(String sendMsg) {
        this.sendMsg = sendMsg == null ? null : sendMsg.trim();
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }
}