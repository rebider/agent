package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lhl on 2019/12/13.
 * 订单数量调整导出VO
 */
public class OrderAdjustVo implements Serializable {
    private String adjId;//订单调整申请编号
    private String refundId;//退款编号
    private String orderId;//订单编号
    private String agentId;//代理商AG码
    private String agName;//代理商名称
    private String busNum;//业务平台编码
    private String platformName;//业务平台
    private String organNum;//顶级机构
    private String activityName;//活动名称
    private String proName;//商品名称
    private String model;//机具型号
    private String proRelPrice;//单价
    private String proNum;//订货数量
    private String payAmo;//总金额
    private String proRefundAmount;//退货手续费
    private String offsetAmount;//冲抵分期金额
    private String realRefundAmo;//线下退款金额
    private String adjUserId;//申请人
    private String agDocDistrict;//对接大区
    private String agDocPro;//对接省区
    private String adjTm;//申请时间
    private String reviewsStat;//审批状态
    private String refundStat;//退款状态
    private String refundTm;//退款日期
    private String maxProNum;//原订单该商品数量
    private String adjNum;//调整数量
    private String difAmount;//调整金额
    private String currentAmo;//调整当前订单金额
    private String reviewsDate;//审批通过时间

    public String getAdjId() {
        return adjId;
    }

    public void setAdjId(String adjId) {
        this.adjId = adjId;
    }

    public String getAgDocDistrict() {
        return agDocDistrict;
    }

    public void setAgDocDistrict(String agDocDistrict) {
        this.agDocDistrict = agDocDistrict;
    }

    public String getAgDocPro() {
        return agDocPro;
    }

    public void setAgDocPro(String agDocPro) {
        this.agDocPro = agDocPro;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getOrganNum() {
        return organNum;
    }

    public void setOrganNum(String organNum) {
        this.organNum = organNum;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
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

    public String getProRelPrice() {
        return proRelPrice;
    }

    public void setProRelPrice(String proRelPrice) {
        this.proRelPrice = proRelPrice;
    }

    public String getProNum() {
        return proNum;
    }

    public void setProNum(String proNum) {
        this.proNum = proNum;
    }

    public String getPayAmo() {
        return payAmo;
    }

    public void setPayAmo(String payAmo) {
        this.payAmo = payAmo;
    }

    public String getProRefundAmount() {
        return proRefundAmount;
    }

    public void setProRefundAmount(String proRefundAmount) {
        this.proRefundAmount = proRefundAmount;
    }

    public String getOffsetAmount() {
        return offsetAmount;
    }

    public void setOffsetAmount(String offsetAmount) {
        this.offsetAmount = offsetAmount;
    }

    public String getRealRefundAmo() {
        return realRefundAmo;
    }

    public void setRealRefundAmo(String realRefundAmo) {
        this.realRefundAmo = realRefundAmo;
    }

    public String getAdjUserId() {
        return adjUserId;
    }

    public void setAdjUserId(String adjUserId) {
        this.adjUserId = adjUserId;
    }

    public String getAdjTm() {
        return adjTm;
    }

    public void setAdjTm(String adjTm) {
        this.adjTm = adjTm;
    }

    public String getReviewsStat() {
        return reviewsStat;
    }

    public void setReviewsStat(String reviewsStat) {
        this.reviewsStat = reviewsStat;
    }

    public String getRefundStat() {
        return refundStat;
    }

    public void setRefundStat(String refundStat) {
        this.refundStat = refundStat;
    }

    public String getRefundTm() {
        return refundTm;
    }

    public void setRefundTm(String refundTm) {
        this.refundTm = refundTm;
    }

    public String getMaxProNum() {
        return maxProNum;
    }

    public void setMaxProNum(String maxProNum) {
        this.maxProNum = maxProNum;
    }

    public String getAdjNum() {
        return adjNum;
    }

    public void setAdjNum(String adjNum) {
        this.adjNum = adjNum;
    }

    public String getDifAmount() {
        return difAmount;
    }

    public void setDifAmount(String difAmount) {
        this.difAmount = difAmount;
    }

    public String getCurrentAmo() {
        return currentAmo;
    }

    public void setCurrentAmo(String currentAmo) {
        this.currentAmo = currentAmo;
    }

    public String getReviewsDate() {
        return reviewsDate;
    }

    public void setReviewsDate(String reviewsDate) {
        this.reviewsDate = reviewsDate;
    }
}