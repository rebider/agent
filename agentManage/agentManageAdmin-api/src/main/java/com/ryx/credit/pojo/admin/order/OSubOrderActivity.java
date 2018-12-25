package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OSubOrderActivity implements Serializable{
    private String id;

    private String activityId;

    private String subOrderId;

    private String activityName;

    private String ruleId;

    private String activityRule;

    private String activityWay;

    private BigDecimal price;

    private String proModel;

    private String vender;

    private String platform;

    private BigDecimal gTime;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal version;

    private String proId;

    private String proName;

    private BigDecimal status;

    private String busProCode;

    private String busProName;

    private String termBatchcode;

    private String termBatchname;

    private String termtype;

    private String termtypename;

    private BigDecimal originalPrice;

    private BigDecimal posSpePrice;

    private String posType;

    private BigDecimal standTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public String getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(String subOrderId) {
        this.subOrderId = subOrderId == null ? null : subOrderId.trim();
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId == null ? null : ruleId.trim();
    }

    public String getActivityRule() {
        return activityRule;
    }

    public void setActivityRule(String activityRule) {
        this.activityRule = activityRule == null ? null : activityRule.trim();
    }

    public String getActivityWay() {
        return activityWay;
    }

    public void setActivityWay(String activityWay) {
        this.activityWay = activityWay == null ? null : activityWay.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProModel() {
        return proModel;
    }

    public void setProModel(String proModel) {
        this.proModel = proModel == null ? null : proModel.trim();
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender == null ? null : vender.trim();
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public BigDecimal getgTime() {
        return gTime;
    }

    public void setgTime(BigDecimal gTime) {
        this.gTime = gTime;
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

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId == null ? null : proId.trim();
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName == null ? null : proName.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public String getBusProCode() {
        return busProCode;
    }

    public void setBusProCode(String busProCode) {
        this.busProCode = busProCode == null ? null : busProCode.trim();
    }

    public String getBusProName() {
        return busProName;
    }

    public void setBusProName(String busProName) {
        this.busProName = busProName == null ? null : busProName.trim();
    }

    public String getTermBatchcode() {
        return termBatchcode;
    }

    public void setTermBatchcode(String termBatchcode) {
        this.termBatchcode = termBatchcode == null ? null : termBatchcode.trim();
    }

    public String getTermBatchname() {
        return termBatchname;
    }

    public void setTermBatchname(String termBatchname) {
        this.termBatchname = termBatchname == null ? null : termBatchname.trim();
    }

    public String getTermtype() {
        return termtype;
    }

    public void setTermtype(String termtype) {
        this.termtype = termtype == null ? null : termtype.trim();
    }

    public String getTermtypename() {
        return termtypename;
    }

    public void setTermtypename(String termtypename) {
        this.termtypename = termtypename == null ? null : termtypename.trim();
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getPosSpePrice() {
        return posSpePrice;
    }

    public void setPosSpePrice(BigDecimal posSpePrice) {
        this.posSpePrice = posSpePrice;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }

    public BigDecimal getStandTime() {
        return standTime;
    }

    public void setStandTime(BigDecimal standTime) {
        this.standTime = standTime;
    }
}