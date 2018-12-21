package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
/**
 * @Auther: lrr
 * @Date: 2018/9/11 17:38
 * @Description:订单发货的导出实体类
 */
public class OrderoutVo implements Serializable{
    private String num;//单号
    private BigDecimal payAmo;//总金额
    private Date inuretime;//订单日期
    private String agUniqNum;//唯一码
    private String agName;//代理商名称
    private String platformName;//平台
    private BigDecimal proNum;//数量
    private String payMethod;//结算方式
    private String downPaymentUser;//付款人
    private String comName;//收款地方
    private BigDecimal actualReceipt;//付款金额
    private BigDecimal xxAmount;//付款金额
    private Date actualReceiptDate;//收款日期
    private String actualTime;
    private String oinuretime;
    private String deductionType;//抵扣类型
    private BigDecimal deductionAmount;//抵扣金额
    private String amount ;//保证金抵货款金额
    private BigDecimal money;//分期金额
    private BigDecimal planNum;//分期笔数

    private String nuclearUser;
    private Date nuclearTime;
    private String nuclearTimeString;
    private String agDocPpro;

    private String proType;//机具类型
    private String activityName;//活动名称
    private BigDecimal ddAmt;//订单金额
    private BigDecimal ykfrAmt;//应扣分润金额
    private BigDecimal downPaymentCount;//分期扣分润期数
    private BigDecimal downPayment;//首付金额
    private BigDecimal mqykAmt;//每期应扣分润金额
    private BigDecimal skfrAmt;//实扣分润金额
    private BigDecimal fqdkAmt;//分期打款金额
    private BigDecimal frdkCount;//分期打款期数
    private BigDecimal sjdkAmt;//实际打款金额
    private BigDecimal syqkAmt;//剩余欠款

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public BigDecimal getDdAmt() {
        return ddAmt;
    }

    public void setDdAmt(BigDecimal ddAmt) {
        this.ddAmt = ddAmt;
    }

    public BigDecimal getYkfrAmt() {
        return ykfrAmt;
    }

    public void setYkfrAmt(BigDecimal ykfrAmt) {
        this.ykfrAmt = ykfrAmt;
    }

    public BigDecimal getDownPaymentCount() {
        return downPaymentCount;
    }

    public void setDownPaymentCount(BigDecimal downPaymentCount) {
        this.downPaymentCount = downPaymentCount;
    }

    public BigDecimal getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(BigDecimal downPayment) {
        this.downPayment = downPayment;
    }

    public BigDecimal getMqykAmt() {
        return mqykAmt;
    }

    public void setMqykAmt(BigDecimal mqykAmt) {
        this.mqykAmt = mqykAmt;
    }

    public BigDecimal getSkfrAmt() {
        return skfrAmt;
    }

    public void setSkfrAmt(BigDecimal skfrAmt) {
        this.skfrAmt = skfrAmt;
    }

    public BigDecimal getFqdkAmt() {
        return fqdkAmt;
    }

    public void setFqdkAmt(BigDecimal fqdkAmt) {
        this.fqdkAmt = fqdkAmt;
    }

    public BigDecimal getFrdkCount() {
        return frdkCount;
    }

    public void setFrdkCount(BigDecimal frdkCount) {
        this.frdkCount = frdkCount;
    }

    public BigDecimal getSjdkAmt() {
        return sjdkAmt;
    }

    public void setSjdkAmt(BigDecimal sjdkAmt) {
        this.sjdkAmt = sjdkAmt;
    }

    public BigDecimal getSyqkAmt() {
        return syqkAmt;
    }

    public void setSyqkAmt(BigDecimal syqkAmt) {
        this.syqkAmt = syqkAmt;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getDeductionType() {
        return deductionType;
    }

    public void setDeductionType(String deductionType) {
        this.deductionType = deductionType;
    }

    public BigDecimal getDeductionAmount() {
        return deductionAmount;
    }

    public void setDeductionAmount(BigDecimal deductionAmount) {
        this.deductionAmount = deductionAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public BigDecimal getPlanNum() {
        return planNum;
    }

    public void setPlanNum(BigDecimal planNum) {
        this.planNum = planNum;
    }

    public String getOinuretime() {
        return oinuretime;
    }

    public void setOinuretime(String oinuretime) {
        this.oinuretime = oinuretime;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public BigDecimal getPayAmo() {
        return payAmo;
    }

    public void setPayAmo(BigDecimal payAmo) {
        this.payAmo = payAmo;
    }


    public String getAgUniqNum() {
        return agUniqNum;
    }

    public void setAgUniqNum(String agUniqNum) {
        this.agUniqNum = agUniqNum;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public BigDecimal getProNum() {
        return proNum;
    }

    public void setProNum(BigDecimal proNum) {
        this.proNum = proNum;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getDownPaymentUser() {
        return downPaymentUser;
    }

    public void setDownPaymentUser(String downPaymentUser) {
        this.downPaymentUser = downPaymentUser;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public BigDecimal getActualReceipt() {
        return actualReceipt;
    }

    public void setActualReceipt(BigDecimal actualReceipt) {
        this.actualReceipt = actualReceipt;
    }

    public Date getActualReceiptDate() {
        return actualReceiptDate;
    }

    public void setActualReceiptDate(Date actualReceiptDate) {
        this.actualReceiptDate = actualReceiptDate;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Date getInuretime() {
        return inuretime;
    }

    public void setInuretime(Date inuretime) {
        this.inuretime = inuretime;
    }

    public String getNuclearUser() {
        return nuclearUser;
    }

    public void setNuclearUser(String nuclearUser) {
        this.nuclearUser = nuclearUser;
    }

    public Date getNuclearTime() {
        return nuclearTime;
    }

    public void setNuclearTime(Date nuclearTime) {
        this.nuclearTime = nuclearTime;
    }

    public String getNuclearTimeString() {
        return nuclearTimeString;
    }

    public void setNuclearTimeString(String nuclearTimeString) {
        this.nuclearTimeString = nuclearTimeString;
    }

    public String getAgDocPpro() {
        return agDocPpro;
    }

    public void setAgDocPpro(String agDocPpro) {
        this.agDocPpro = agDocPpro;
    }

    public BigDecimal getXxAmount() {
        return xxAmount;
    }

    public void setXxAmount(BigDecimal xxAmount) {
        this.xxAmount = xxAmount;
    }
}