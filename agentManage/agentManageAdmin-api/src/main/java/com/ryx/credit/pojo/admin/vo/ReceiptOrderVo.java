package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lhl on 2019/8/8.
 * 排单管理：未排单数据导出实体
 */
public class ReceiptOrderVo implements Serializable {

    private String orderId;//订单编号

    private String receiptNum;//子订单编号

    private String oinuretime;//订单时间

    private String agentId;//代理商唯一编码

    private String agName;//代理商名称

    private String addrRealname;//收货姓名

    private String activityName;//活动名称

    private String proId;//商品ID

    private String proCode;//商品编号

    private String proName;//商品名称

    private BigDecimal proNum;//订货量

    private BigDecimal sendNum;//已排量

    private BigDecimal forSendNum;//待排单

    private String proCom;//厂家

    private String model;//机型

    private String busProName;

    private String backType;

    private String standTime;

    private String standAmt;

    private BigDecimal planProNum;//数量

    private String orderRemark;//订单备注

    private String rpCTime;//配货时间

    private String addrProvince;//省

    private String addrCity;//市

    private String addrDistinct;//区

    private String addrDetail;//详细地址

    private String addrMobile;//电话

    public void setAddrMobile(String addrMobile) {
        this.addrMobile = addrMobile;
    }

    public String getAddrMobile() {
        return addrMobile;
    }

    public String getAddrProvince() {
        return addrProvince;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public String getAddrDistinct() {
        return addrDistinct;
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    public void setAddrDistinct(String addrDistinct) {
        this.addrDistinct = addrDistinct;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getRpCTime() {
        return rpCTime;
    }

    public void setRpCTime(String rpCTime) {
        this.rpCTime = rpCTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReceiptNum() {
        return receiptNum;
    }

    public void setReceiptNum(String receiptNum) {
        this.receiptNum = receiptNum;
    }

    public String getOinuretime() {
        return oinuretime;
    }

    public void setOinuretime(String oinuretime) {
        this.oinuretime = oinuretime;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }

    public String getAddrRealname() {
        return addrRealname;
    }

    public void setAddrRealname(String addrRealname) {
        this.addrRealname = addrRealname;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public BigDecimal getProNum() {
        return proNum;
    }

    public void setProNum(BigDecimal proNum) {
        this.proNum = proNum;
    }

    public BigDecimal getSendNum() {
        return sendNum;
    }

    public void setSendNum(BigDecimal sendNum) {
        this.sendNum = sendNum;
    }

    public BigDecimal getForSendNum() {
        return forSendNum;
    }

    public void setForSendNum(BigDecimal forSendNum) {
        this.forSendNum = forSendNum;
    }

    public String getProCom() {
        return proCom;
    }

    public void setProCom(String proCom) {
        this.proCom = proCom;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBusProName() {
        return busProName;
    }

    public void setBusProName(String busProName) {
        this.busProName = busProName;
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType;
    }

    public String getStandTime() {
        return standTime;
    }

    public void setStandTime(String standTime) {
        this.standTime = standTime;
    }

    public String getStandAmt() {
        return standAmt;
    }

    public void setStandAmt(String standAmt) {
        this.standAmt = standAmt;
    }

    public BigDecimal getPlanProNum() {
        return planProNum;
    }

    public void setPlanProNum(BigDecimal planProNum) {
        this.planProNum = planProNum;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

}
