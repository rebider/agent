package com.ryx.credit.pojo.admin.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: agentManage
 * @description: 订单调整vo
 * @author: ssx
 * @create: 2019-11-05 12:39
 **/
public class OrderUpModelVo {
    private String orderId;
    private String curArrAmount;
    private String orgStagesAmount;
    private String reson;
    private String refundAmount;
    private String refundMethod;
    private List<AdjProVo> adjPros;
    private List<BigDecimal> calPriceList;
    private List<String> files;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<BigDecimal> getCalPriceList() {
        return calPriceList;
    }

    public void setCalPriceList(List<BigDecimal> calPriceList) {
        this.calPriceList = calPriceList;
    }

    public String getCurArrAmount() {
        return curArrAmount;
    }

    public void setCurArrAmount(String curArrAmount) {
        this.curArrAmount = curArrAmount;
    }

    public String getOrgStagesAmount() {
        return orgStagesAmount;
    }

    public void setOrgStagesAmount(String orgStagesAmount) {
        this.orgStagesAmount = orgStagesAmount;
    }

    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundMethod() {
        return refundMethod;
    }

    public void setRefundMethod(String refundMethod) {
        this.refundMethod = refundMethod;
    }

    public List<AdjProVo> getAdjPros() {
        return adjPros;
    }

    public void setAdjPros(List<AdjProVo> adjPros) {
        this.adjPros = adjPros;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
