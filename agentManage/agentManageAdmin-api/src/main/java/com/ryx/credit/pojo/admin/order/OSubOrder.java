package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.pojo.admin.vo.OSubOrderExtends;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OSubOrder extends OSubOrderExtends implements Serializable {
    private String id;

    private String orderId;

    private String proId;

    private String proCode;

    private String proName;

    private String proType;

    private BigDecimal proPrice;

    private BigDecimal isDeposit;

    private BigDecimal deposit;

    private String model;

    private BigDecimal proNum;

    private BigDecimal proRelPrice;

    private BigDecimal sendNum;

    private String remark;

    private String cUser;

    private Date cTime;

    private String uUser;

    private Date uTime;

    private BigDecimal status;

    private BigDecimal version;

    private String agentId;

    private OSubOrderActivity subOrderActivity;

    private BigDecimal sumProPrice;

    private String snBegin;

    private String snEnd;

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

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId == null ? null : proId.trim();
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode == null ? null : proCode.trim();
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

    public BigDecimal getProPrice() {
        return proPrice;
    }

    public void setProPrice(BigDecimal proPrice) {
        this.proPrice = proPrice;
    }

    public BigDecimal getIsDeposit() {
        return isDeposit;
    }

    public void setIsDeposit(BigDecimal isDeposit) {
        this.isDeposit = isDeposit;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public BigDecimal getProNum() {
        return proNum;
    }

    public void setProNum(BigDecimal proNum) {
        this.proNum = proNum;
    }

    public BigDecimal getProRelPrice() {
        return proRelPrice;
    }

    public void setProRelPrice(BigDecimal proRelPrice) {
        this.proRelPrice = proRelPrice;
    }

    public BigDecimal getSendNum() {
        return sendNum;
    }

    public void setSendNum(BigDecimal sendNum) {
        this.sendNum = sendNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public String getuUser() {
        return uUser;
    }

    public void setuUser(String uUser) {
        this.uUser = uUser == null ? null : uUser.trim();
    }

    public Date getuTime() {
        return uTime;
    }

    public void setuTime(Date uTime) {
        this.uTime = uTime;
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

    public OSubOrderActivity getSubOrderActivity() {
        return subOrderActivity;
    }

    public void setSubOrderActivity(OSubOrderActivity subOrderActivity) {
        this.subOrderActivity = subOrderActivity;
    }

    public BigDecimal getSumProPrice() {
        return sumProPrice;
    }

    public void setSumProPrice(BigDecimal sumProPrice) {
        this.sumProPrice = sumProPrice;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getSnBegin() {
        return snBegin;
    }

    public void setSnBegin(String snBegin) {
        this.snBegin = snBegin;
    }

    public String getSnEnd() {
        return snEnd;
    }

    public void setSnEnd(String snEnd) {
        this.snEnd = snEnd;
    }
}