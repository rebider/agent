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
}