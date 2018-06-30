package com.ryx.credit.pojo.admin.vo;

/**
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/6/11 15:11
 */
public class AgentNotifyVo {

    private String uniqueId;
    private String useOrgan;  //885:自营，886：代理商，A00：机构，887：手刷
    private String orgName;
    private String province;
    private String city;
    private String cityArea;
    private String orgType;
    private String supDorgId;
    private String busPlatform;
    private String agHeadMobile;
    private String baseMessage;
    private String busMessage;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
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

    public String getBusMessage() {
        return busMessage;
    }

    public void setBusMessage(String busMessage) {
        this.busMessage = busMessage;
    }

    public String getBaseMessage() {
        return baseMessage;
    }

    public void setBaseMessage(String baseMessage) {
        this.baseMessage = baseMessage;
    }
}
