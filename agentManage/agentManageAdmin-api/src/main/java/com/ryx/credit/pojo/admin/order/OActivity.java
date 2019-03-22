package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OActivity implements Serializable{
    private String id;

    private String productId;

    private String ruleId;

    private String activityName;

    private String proType;

    private String activityWay;

    private String activityRule;

    private String platform;

    private BigDecimal price;

    private Date beginTime;

    private Date endTime;

    private String vender;

    private String proModel;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    private BigDecimal gTime;

    private String activityCondition;

    private String busProCode;

    private String busProName;

    private String termBatchcode;

    private String termBatchname;

    private String termtype;

    private String termtypename;

    private String actCode;

    private BigDecimal originalPrice;

    private String proTypeName;

    private BigDecimal posSpePrice;

    private String posType;

    private BigDecimal standTime;

    private String venderName;

    private BigDecimal standAmt;

    private String backType;

    public BigDecimal getStandAmt() {
        return standAmt;
    }

    public void setStandAmt(BigDecimal standAmt) {
        this.standAmt = standAmt;
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType == null ? null : backType.trim();
    }

    public String getProTypeName() {
        return proTypeName;
    }

    public void setProTypeName(String proTypeName) {
        this.proTypeName = proTypeName;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId == null ? null : ruleId.trim();
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType == null ? null : proType.trim();
    }

    public String getActivityWay() {
        return activityWay;
    }

    public void setActivityWay(String activityWay) {
        this.activityWay = activityWay == null ? null : activityWay.trim();
    }

    public String getActivityRule() {
        return activityRule;
    }

    public void setActivityRule(String activityRule) {
        this.activityRule = activityRule == null ? null : activityRule.trim();
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender == null ? null : vender.trim();
    }

    public String getProModel() {
        return proModel;
    }

    public void setProModel(String proModel) {
        this.proModel = proModel == null ? null : proModel.trim();
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

    public BigDecimal getgTime() {
        return gTime;
    }

    public void setgTime(BigDecimal gTime) {
        this.gTime = gTime;
    }

    public String getActivityCondition() {
        return activityCondition;
    }

    public void setActivityCondition(String activityCondition) {
        this.activityCondition = activityCondition == null ? null : activityCondition.trim();
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

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode == null ? null : actCode.trim();
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OActivity activity = (OActivity) o;

        return id.equals(activity.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}