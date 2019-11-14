package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 * @program: agentManage
 * @description: 订单调整vo
 * @author: ssx
 * @create: 2019-11-05 12:39
 **/
public class OrderUpModelVo implements Serializable {
    private String id;//订单调整ID
    private String orderId;//订单号
    private String agentId;
    private String curArrAmount;//当前欠款金额
    private String orgStagesAmount;//当前分期金额
    private String adjRepayment;//调整后分期金额
    private String reson;//原因
    private String refundAmount;//退款金额
    private String refundMethod;//退款方式
    private List<AdjProVo> adjPros;//子商品清单
    private List<BigDecimal> calPriceList;//差价汇总
    private List<String> files;//附件
    private String isApproveWhenSubmit;
    private String taskId;
    private String approvalOpinion;
    private String approvalResult;
    private String dept;
    private String flag;
    private String orderAdjAprDept;//订单调整审批下个审批部门参数

    public String getOrderAdjAprDept() {
        return orderAdjAprDept;
    }

    public void setOrderAdjAprDept(String orderAdjAprDept) {
        this.orderAdjAprDept = orderAdjAprDept;
    }

    public String getApprovalOpinion() {
        return approvalOpinion;
    }

    public void setApprovalOpinion(String approvalOpinion) {
        this.approvalOpinion = approvalOpinion;
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getIsApproveWhenSubmit() {
        return isApproveWhenSubmit;
    }

    public void setIsApproveWhenSubmit(String isApproveWhenSubmit) {
        this.isApproveWhenSubmit = isApproveWhenSubmit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAdjRepayment() {
        return adjRepayment;
    }

    public void setAdjRepayment(String adjRepayment) {
        this.adjRepayment = adjRepayment;
    }
}
