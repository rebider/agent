package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author Lihl
 * @Date 2018/07/21
 * 排单管理：物流信息
 */
public class OLogisticsUtil implements Serializable{
    private String orderId;    // 订单编号 （排单表）

    private String receiptId;    // 子订单编号

    private BigDecimal sendProNum;    // 发货数量

    private Date sendDate;    // 发货日期

    private String agentId;    // 代理商ID（订单表）

    private Date oApytime;    // 订单日期

    private String agName;    // 代理商名称 （代理商表）

    private String proName;    // 商品名称 （商品表）

    private String model;    // 机型（）

    private String proCom;    // 厂家 （物流表）

    private BigDecimal sendNum;    // 数量

    private String logCom;    // 物流公司

    private String wNumber;    // 物流单号

    private String snBeginNum;    // 开始SN序列号

    private String snEndNum;    // 结束SN序列号

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public BigDecimal getSendProNum() {
        return sendProNum;
    }

    public void setSendProNum(BigDecimal sendProNum) {
        this.sendProNum = sendProNum;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public Date getoApytime() {
        return oApytime;
    }

    public void setoApytime(Date oApytime) {
        this.oApytime = oApytime;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProCom() {
        return proCom;
    }

    public void setProCom(String proCom) {
        this.proCom = proCom;
    }

    public BigDecimal getSendNum() {
        return sendNum;
    }

    public void setSendNum(BigDecimal sendNum) {
        this.sendNum = sendNum;
    }

    public String getLogCom() {
        return logCom;
    }

    public void setLogCom(String logCom) {
        this.logCom = logCom;
    }

    public String getwNumber() {
        return wNumber;
    }

    public void setwNumber(String wNumber) {
        this.wNumber = wNumber;
    }

    public String getSnBeginNum() {
        return snBeginNum;
    }

    public void setSnBeginNum(String snBeginNum) {
        this.snBeginNum = snBeginNum;
    }

    public String getSnEndNum() {
        return snEndNum;
    }

    public void setSnEndNum(String snEndNum) {
        this.snEndNum = snEndNum;
    }
}
