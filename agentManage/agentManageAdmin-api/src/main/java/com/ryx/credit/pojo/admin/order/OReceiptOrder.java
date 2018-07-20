package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OReceiptOrder implements Serializable{
    private String id;

    private String orderId;

    private String receiptNum;

    private String addressId;

    private String addrRealname;

    private String addrMobile;

    private String addrProvince;

    private String addrCity;

    private String addrDistrict;

    private String addrDetail;

    private String remark;

    private String zipCode;

    private BigDecimal proNum;

    private String expressRemark;

    private Date expressSucDate;

    private Date cTime;

    private Date cUser;

    private Date uTime;

    private BigDecimal receiptStatus;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    private String agentId;

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

    public String getReceiptNum() {
        return receiptNum;
    }

    public void setReceiptNum(String receiptNum) {
        this.receiptNum = receiptNum == null ? null : receiptNum.trim();
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId == null ? null : addressId.trim();
    }

    public String getAddrRealname() {
        return addrRealname;
    }

    public void setAddrRealname(String addrRealname) {
        this.addrRealname = addrRealname == null ? null : addrRealname.trim();
    }

    public String getAddrMobile() {
        return addrMobile;
    }

    public void setAddrMobile(String addrMobile) {
        this.addrMobile = addrMobile == null ? null : addrMobile.trim();
    }

    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince == null ? null : addrProvince.trim();
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity == null ? null : addrCity.trim();
    }

    public String getAddrDistrict() {
        return addrDistrict;
    }

    public void setAddrDistrict(String addrDistrict) {
        this.addrDistrict = addrDistrict == null ? null : addrDistrict.trim();
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail == null ? null : addrDetail.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public BigDecimal getProNum() {
        return proNum;
    }

    public void setProNum(BigDecimal proNum) {
        this.proNum = proNum;
    }

    public String getExpressRemark() {
        return expressRemark;
    }

    public void setExpressRemark(String expressRemark) {
        this.expressRemark = expressRemark == null ? null : expressRemark.trim();
    }

    public Date getExpressSucDate() {
        return expressSucDate;
    }

    public void setExpressSucDate(Date expressSucDate) {
        this.expressSucDate = expressSucDate;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getcUser() {
        return cUser;
    }

    public void setcUser(Date cUser) {
        this.cUser = cUser;
    }

    public Date getuTime() {
        return uTime;
    }

    public void setuTime(Date uTime) {
        this.uTime = uTime;
    }

    public BigDecimal getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(BigDecimal receiptStatus) {
        this.receiptStatus = receiptStatus;
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

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }
}