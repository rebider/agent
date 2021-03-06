package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;

import java.io.Serializable;
import java.util.List;

/**
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/6/11 15:11
 */
public class AgentNotifyVo implements Serializable{

    private String uniqueId;
    private String useOrgan;  //885:自营，886：代理商，A00：机构，887：手刷
    private String orgId;
    private String orgName;
    private String province;
    private String city;
    private String cityArea;
    private String orgType;
    private String supDorgId;
    private String busPlatform;
    private String agHeadMobile;
    private Agent baseMessage;
    private AgentBusInfo busMessage;
    private AgentColinfo colinfoMessage;
    private String[] busiAreas;
    private String hasS0;
    private String busiType;
    private String loginName;
    private String remark;
    private java.util.List<String> batchIds;
    private String debitTop;//借记封顶额上限
    private String ckDebitRate;//借记出款费率
    private String lowDebitRate;//借记费率下限
    private String activityType;
    private String creditRateLower;//贷记费率下限
    private String creditRateCeiling;//贷记费率上限
    private String debitRateCapping;//借记费率上限
    private String debitCappingLower;//借记封顶额下限

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUseOrgan() {
        return useOrgan;
    }

    public void setUseOrgan(String useOrgan) {
        this.useOrgan = useOrgan;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityArea() {
        return cityArea;
    }

    public void setCityArea(String cityArea) {
        this.cityArea = cityArea;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getSupDorgId() {
        return supDorgId;
    }

    public void setSupDorgId(String supDorgId) {
        this.supDorgId = supDorgId;
    }

    public String getBusPlatform() {
        return busPlatform;
    }

    public void setBusPlatform(String busPlatform) {
        this.busPlatform = busPlatform;
    }

    public String getAgHeadMobile() {
        return agHeadMobile;
    }

    public void setAgHeadMobile(String agHeadMobile) {
        this.agHeadMobile = agHeadMobile;
    }

    public Agent getBaseMessage() {
        return baseMessage;
    }

    public void setBaseMessage(Agent baseMessage) {
        this.baseMessage = baseMessage;
    }

    public AgentBusInfo getBusMessage() {
        return busMessage;
    }

    public void setBusMessage(AgentBusInfo busMessage) {
        this.busMessage = busMessage;
    }

    public String[] getBusiAreas() {
        return busiAreas;
    }

    public void setBusiAreas(String[] busiAreas) {
        this.busiAreas = busiAreas;
    }

    public String getHasS0() {
        return hasS0;
    }

    public void setHasS0(String hasS0) {
        this.hasS0 = hasS0;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public AgentColinfo getColinfoMessage() {
        return colinfoMessage;
    }

    public void setColinfoMessage(AgentColinfo colinfoMessage) {
        this.colinfoMessage = colinfoMessage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getBatchIds() {
        return batchIds;
    }

    public void setBatchIds(List<String> batchIds) {
        this.batchIds = batchIds;
    }

    public String getDebitTop() {
        return debitTop;
    }

    public void setDebitTop(String debitTop) {
        this.debitTop = debitTop;
    }

    public String getCkDebitRate() {
        return ckDebitRate;
    }

    public void setCkDebitRate(String ckDebitRate) {
        this.ckDebitRate = ckDebitRate;
    }

    public String getLowDebitRate() {
        return lowDebitRate;
    }

    public void setLowDebitRate(String lowDebitRate) {
        this.lowDebitRate = lowDebitRate;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getCreditRateLower() {
        return creditRateLower;
    }

    public void setCreditRateLower(String creditRateLower) {
        this.creditRateLower = creditRateLower;
    }

    public String getCreditRateCeiling() {
        return creditRateCeiling;
    }

    public void setCreditRateCeiling(String creditRateCeiling) {
        this.creditRateCeiling = creditRateCeiling;
    }

    public String getDebitRateCapping() {
        return debitRateCapping;
    }

    public void setDebitRateCapping(String debitRateCapping) {
        this.debitRateCapping = debitRateCapping;
    }

    public String getDebitCappingLower() {
        return debitCappingLower;
    }

    public void setDebitCappingLower(String debitCappingLower) {
        this.debitCappingLower = debitCappingLower;
    }
}
