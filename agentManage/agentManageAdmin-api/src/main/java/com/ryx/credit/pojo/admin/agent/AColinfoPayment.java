package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AColinfoPayment implements Serializable {
    private String id;

    private String colinfoId;

    private String merchId;

    private String merchName;

    private String tranDate;

    private BigDecimal balanceAmt;

    private String balanceRcvAcc;

    private String balanceRcvBank;

    private String balanceRcvName;

    private String balanceRcvCode;

    private String balanceRcvType;

    private String inputTime;

    private String channelId;

    private String remark;

    private String balanceLs;

    private String createBatchTime;

    private String batchCode;

    private String flag;

    private String errDesc;

    private String datasource;

    private String accountId;

    private String payDate;

    private String orgAccountId;

    private Date createTime;

    private String updateTime;

    private String reserve;

    private String memo;

    private String sysflowsource;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getColinfoId() {
        return colinfoId;
    }

    public void setColinfoId(String colinfoId) {
        this.colinfoId = colinfoId == null ? null : colinfoId.trim();
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId == null ? null : merchId.trim();
    }

    public String getMerchName() {
        return merchName;
    }

    public void setMerchName(String merchName) {
        this.merchName = merchName == null ? null : merchName.trim();
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate == null ? null : tranDate.trim();
    }

    public BigDecimal getBalanceAmt() {
        return balanceAmt;
    }

    public void setBalanceAmt(BigDecimal balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

    public String getBalanceRcvAcc() {
        return balanceRcvAcc;
    }

    public void setBalanceRcvAcc(String balanceRcvAcc) {
        this.balanceRcvAcc = balanceRcvAcc == null ? null : balanceRcvAcc.trim();
    }

    public String getBalanceRcvBank() {
        return balanceRcvBank;
    }

    public void setBalanceRcvBank(String balanceRcvBank) {
        this.balanceRcvBank = balanceRcvBank == null ? null : balanceRcvBank.trim();
    }

    public String getBalanceRcvName() {
        return balanceRcvName;
    }

    public void setBalanceRcvName(String balanceRcvName) {
        this.balanceRcvName = balanceRcvName == null ? null : balanceRcvName.trim();
    }

    public String getBalanceRcvCode() {
        return balanceRcvCode;
    }

    public void setBalanceRcvCode(String balanceRcvCode) {
        this.balanceRcvCode = balanceRcvCode == null ? null : balanceRcvCode.trim();
    }

    public String getBalanceRcvType() {
        return balanceRcvType;
    }

    public void setBalanceRcvType(String balanceRcvType) {
        this.balanceRcvType = balanceRcvType == null ? null : balanceRcvType.trim();
    }

    public String getInputTime() {
        return inputTime;
    }

    public void setInputTime(String inputTime) {
        this.inputTime = inputTime == null ? null : inputTime.trim();
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId == null ? null : channelId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getBalanceLs() {
        return balanceLs;
    }

    public void setBalanceLs(String balanceLs) {
        this.balanceLs = balanceLs == null ? null : balanceLs.trim();
    }

    public String getCreateBatchTime() {
        return createBatchTime;
    }

    public void setCreateBatchTime(String createBatchTime) {
        this.createBatchTime = createBatchTime == null ? null : createBatchTime.trim();
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode == null ? null : batchCode.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getErrDesc() {
        return errDesc;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc == null ? null : errDesc.trim();
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource == null ? null : datasource.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate == null ? null : payDate.trim();
    }

    public String getOrgAccountId() {
        return orgAccountId;
    }

    public void setOrgAccountId(String orgAccountId) {
        this.orgAccountId = orgAccountId == null ? null : orgAccountId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve == null ? null : reserve.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getSysflowsource() {
        return sysflowsource;
    }

    public void setSysflowsource(String sysflowsource) {
        this.sysflowsource = sysflowsource == null ? null : sysflowsource.trim();
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

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }
}