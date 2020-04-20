package com.ryx.internet.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OInternetRenewDetail implements Serializable {
    private String id;

    private String renewId;

    private String iccidNum;

    private String orderId;

    private String snNum;

    private String internetCardNum;

    private Date openAccountTime;

    private Date expireTime;

    private String merName;

    private String merId;

    private String agentId;

    private String agentName;

    private String renewWay;

    private BigDecimal offsetAmt;

    private BigDecimal renewAmt;

    private BigDecimal oughtAmt;

    private BigDecimal realityAmt;

    private String renewStatus;

    private BigDecimal status;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal version;

    private String renewWayName;

    private BigDecimal theRealityAmt;

    private String busNum;

    private String busPlatform;

    private String agDocDistrict;

    private String agDocPro;

    private String busContactPerson;

    private String openAccountTimeBeginStr;

    private String openAccountTimeEndStr;

    private String expireTimeBeginStr;

    private String expireTimeEndStr;

    private List<String> renewStatusList;

    public List<String> getRenewStatusList() {
        return renewStatusList;
    }

    public void setRenewStatusList(List<String> renewStatusList) {
        this.renewStatusList = renewStatusList;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public String getBusPlatform() {
        return busPlatform;
    }

    public void setBusPlatform(String busPlatform) {
        this.busPlatform = busPlatform;
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

    public String getBusContactPerson() {
        return busContactPerson;
    }

    public void setBusContactPerson(String busContactPerson) {
        this.busContactPerson = busContactPerson;
    }

    public BigDecimal getTheRealityAmt() {
        return theRealityAmt;
    }

    public void setTheRealityAmt(BigDecimal theRealityAmt) {
        this.theRealityAmt = theRealityAmt;
    }

    public String getRenewWayName() {
        return renewWayName;
    }

    public void setRenewWayName(String renewWayName) {
        this.renewWayName = renewWayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRenewId() {
        return renewId;
    }

    public void setRenewId(String renewId) {
        this.renewId = renewId == null ? null : renewId.trim();
    }

    public String getIccidNum() {
        return iccidNum;
    }

    public void setIccidNum(String iccidNum) {
        this.iccidNum = iccidNum == null ? null : iccidNum.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getSnNum() {
        return snNum;
    }

    public void setSnNum(String snNum) {
        this.snNum = snNum == null ? null : snNum.trim();
    }

    public String getInternetCardNum() {
        return internetCardNum;
    }

    public void setInternetCardNum(String internetCardNum) {
        this.internetCardNum = internetCardNum == null ? null : internetCardNum.trim();
    }

    public Date getOpenAccountTime() {
        return openAccountTime;
    }

    public void setOpenAccountTime(Date openAccountTime) {
        this.openAccountTime = openAccountTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName == null ? null : merName.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public String getRenewWay() {
        return renewWay;
    }

    public void setRenewWay(String renewWay) {
        this.renewWay = renewWay == null ? null : renewWay.trim();
    }

    public BigDecimal getOffsetAmt() {
        return offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

    public BigDecimal getRenewAmt() {
        return renewAmt;
    }

    public void setRenewAmt(BigDecimal renewAmt) {
        this.renewAmt = renewAmt;
    }

    public BigDecimal getOughtAmt() {
        return oughtAmt;
    }

    public void setOughtAmt(BigDecimal oughtAmt) {
        this.oughtAmt = oughtAmt;
    }

    public BigDecimal getRealityAmt() {
        return realityAmt;
    }

    public void setRealityAmt(BigDecimal realityAmt) {
        this.realityAmt = realityAmt;
    }

    public String getRenewStatus() {
        return renewStatus;
    }

    public void setRenewStatus(String renewStatus) {
        this.renewStatus = renewStatus == null ? null : renewStatus.trim();
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

    public String getOpenAccountTimeBeginStr() {
        return openAccountTimeBeginStr;
    }

    public void setOpenAccountTimeBeginStr(String openAccountTimeBeginStr) {
        this.openAccountTimeBeginStr = openAccountTimeBeginStr;
    }

    public String getOpenAccountTimeEndStr() {
        return openAccountTimeEndStr;
    }

    public void setOpenAccountTimeEndStr(String openAccountTimeEndStr) {
        this.openAccountTimeEndStr = openAccountTimeEndStr;
    }

    public String getExpireTimeBeginStr() {
        return expireTimeBeginStr;
    }

    public void setExpireTimeBeginStr(String expireTimeBeginStr) {
        this.expireTimeBeginStr = expireTimeBeginStr;
    }

    public String getExpireTimeEndStr() {
        return expireTimeEndStr;
    }

    public void setExpireTimeEndStr(String expireTimeEndStr) {
        this.expireTimeEndStr = expireTimeEndStr;
    }

    @Override
    public String toString() {
        return "OInternetRenewDetail{" +
                "id='" + id + '\'' +
                ", renewId='" + renewId + '\'' +
                ", iccidNum='" + iccidNum + '\'' +
                ", orderId='" + orderId + '\'' +
                ", snNum='" + snNum + '\'' +
                ", internetCardNum='" + internetCardNum + '\'' +
                ", openAccountTime=" + openAccountTime +
                ", expireTime=" + expireTime +
                ", merName='" + merName + '\'' +
                ", merId='" + merId + '\'' +
                ", agentId='" + agentId + '\'' +
                ", agentName='" + agentName + '\'' +
                ", renewWay='" + renewWay + '\'' +
                ", offsetAmt=" + offsetAmt +
                ", renewAmt=" + renewAmt +
                ", oughtAmt=" + oughtAmt +
                ", realityAmt=" + realityAmt +
                ", renewStatus='" + renewStatus + '\'' +
                ", status=" + status +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", cUser='" + cUser + '\'' +
                ", uUser='" + uUser + '\'' +
                ", version=" + version +
                ", renewWayName='" + renewWayName + '\'' +
                ", openAccountTimeBeginStr='" + openAccountTimeBeginStr + '\'' +
                ", openAccountTimeEndStr='" + openAccountTimeEndStr + '\'' +
                ", expireTimeBeginStr='" + expireTimeBeginStr + '\'' +
                ", expireTimeEndStr='" + expireTimeEndStr + '\'' +
                ", theRealityAmt=" + theRealityAmt +
                '}';
    }
}