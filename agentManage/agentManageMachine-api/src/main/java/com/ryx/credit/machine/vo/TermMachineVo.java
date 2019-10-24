package com.ryx.credit.machine.vo;

import java.io.Serializable;

/**
 * 作者：cx
 * 时间：2018/10/10
 * 描述：POS极具vo 手刷活动vo
 */
public class TermMachineVo implements Serializable{

    public String id;

    public String mechineName;

    public String standAmt;

    public String backType;

    public String model;

    private String manufactor;

    private String standTime;

    private String activityStartTime;

    private String activityEndTime;

    private String price;

    private String posType;

    private String posSpePrice;

    public String getPosSpePrice() {
        return posSpePrice;
    }

    public void setPosSpePrice(String posSpePrice) {
        this.posSpePrice = posSpePrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMechineName() {
        return mechineName;
    }

    public void setMechineName(String mechineName) {
        this.mechineName = mechineName;
    }

    public String getStandAmt() {
        return standAmt;
    }

    public void setStandAmt(String standAmt) {
        this.standAmt = standAmt;
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufactor() {
        return manufactor;
    }

    public void setManufactor(String manufactor) {
        this.manufactor = manufactor;
    }

    public String getStandTime() {
        return standTime;
    }

    public void setStandTime(String standTime) {
        this.standTime = standTime;
    }

    public String getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(String activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public String getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(String activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }
}
