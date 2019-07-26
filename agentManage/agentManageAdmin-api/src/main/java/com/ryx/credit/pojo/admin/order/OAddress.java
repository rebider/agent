package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OAddress implements Serializable {
    private String id;

    private String uId;

    private BigDecimal uType;

    private String addrRealname;

    private String addrMobile;

    private String addrProvince;

    private String addrCity;

    private String addrDistrict;

    private String addrDetail;

    private String zipCode;

    private BigDecimal isdefault;

    private String remark;

    private BigDecimal status;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal version;

    private BigDecimal flag;

    public BigDecimal getFlag() {
        return flag;
    }

    public void setFlag(BigDecimal flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId == null ? null : uId.trim();
    }

    public BigDecimal getuType() {
        return uType;
    }

    public void setuType(BigDecimal uType) {
        this.uType = uType;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public BigDecimal getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(BigDecimal isdefault) {
        this.isdefault = isdefault;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getuTime() {
        return uTime;
    }

    public void setuTime(Date uTime) {
        this.uTime = uTime;
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

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }
}