package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：cx
 * 时间：2019/9/18
 * 描述：
 */
public class PaymentSendBusPlatformVo implements Serializable{


    /*代理商AG码*/
    private String ag;
    /*平台类型
    RDBPOS（瑞大宝）
    POS(大POS平台)
    RJQZ （瑞+（条码前置））
    RHPOS（瑞花宝）
    SSPOS（实时分润）
    RJPOS（瑞+）
    ZHPOS 智慧POS
    ZPOS  智能POS
    MPOS  手刷平台
    */
    private String platform;
    /*
    *业务编号
    * 瑞大宝代理商A码
    * POS O码
    * */
    private String busNum;
    /*打款1，撤销0*/
    private String optType;

    /*首付款，
    *
    *  SF   "首付"
    *  DK   "打款"
    * */
    private String amountType;
    /*代理商平台订单创建时间yyyyMMddHH24mmss*/
    private String createTime;
    /*操作金额分*/
    private String amount;
    /*订单号（代理商平台订单编号）*/
    private String orderNum;
    /*交易流水（分期流水）*/
    private String fqflow;
    /*支付方式（线下打款）*/
    private String payType;
    /*打款信息（那个图片url连接*/
    private List<String> imageList;



    public String getAg() {
        return ag;
    }

    public void setAg(String ag) {
        this.ag = ag;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getFqflow() {
        return fqflow;
    }

    public void setFqflow(String fqflow) {
        this.fqflow = fqflow;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
