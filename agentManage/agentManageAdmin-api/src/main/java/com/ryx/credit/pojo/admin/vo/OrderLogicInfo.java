package com.ryx.credit.pojo.admin.vo;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 作者：cx
 * 时间：2019/1/29
 * 描述：
 */
public class OrderLogicInfo implements Serializable{

    public static List<String> colum  = Arrays.asList("order_id",
            "order_orgid",
            "order_platform",
            "order_agname","goodsCode","goodsName","goodsSendNum",
            "logicComp","logicCode","sendTime","sendAddress","snStart","snEnd","snStartNum","snEndNum");


    public void loadInfoFromJsonArray(JSONArray data,String importId){
        if(data!=null){
            this.setOrder_id(data.getString(colum.indexOf("order_id")));
            this.setOrder_orgid(data.getString(colum.indexOf("order_orgid")));
            this.setOrder_platform(data.getString(colum.indexOf("order_platform")));
            this.setOrder_agname(data.getString(colum.indexOf("order_agname")));
            this.setGoodsCode(data.getString(colum.indexOf("goodsCode")));
            this.setGoodsName(data.getString(colum.indexOf("goodsName")));
            this.setGoodsSendNum(data.getString(colum.indexOf("goodsSendNum")));
            this.setLogicComp(data.getString(colum.indexOf("logicComp")));
            this.setLogicCode(data.getString(colum.indexOf("logicCode")));
            this.setSendTime(data.getString(colum.indexOf("sendTime")));
            this.setSendAddress(data.getString(colum.indexOf("sendAddress")));
            this.setSnStart(data.getString(colum.indexOf("snStart")));
            this.setSnEnd(data.getString(colum.indexOf("snEnd")));
            this.setSnStartNum(data.getString(colum.indexOf("snStartNum")));
            this.setSnEndNum(data.getString(colum.indexOf("snEndNum")));
        }

        this.importId = importId;
    }

    public  String order_id ;
    public  String order_orgid ;
    public  String order_platform ;
    public  String order_agname ;

    private String goodsCode;
    private String goodsName;
    private String goodsSendNum;
    private String logicComp;
    private String logicCode;
    private String sendTime;
    private String sendAddress;
    private String snStart;
    private String snEnd;
    private String snStartNum;
    private String snEndNum;
    private String importId;

    public String getImportId() {
        return importId;
    }

    public void setImportId(String importId) {
        this.importId = importId;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_orgid() {
        return order_orgid;
    }

    public void setOrder_orgid(String order_orgid) {
        this.order_orgid = order_orgid;
    }

    public String getOrder_platform() {
        return order_platform;
    }

    public void setOrder_platform(String order_platform) {
        this.order_platform = order_platform;
    }

    public String getOrder_agname() {
        return order_agname;
    }

    public void setOrder_agname(String order_agname) {
        this.order_agname = order_agname;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSendNum() {
        return goodsSendNum;
    }

    public void setGoodsSendNum(String goodsSendNum) {
        this.goodsSendNum = goodsSendNum;
    }

    public String getLogicComp() {
        return logicComp;
    }

    public void setLogicComp(String logicComp) {
        this.logicComp = logicComp;
    }

    public String getLogicCode() {
        return logicCode;
    }

    public void setLogicCode(String logicCode) {
        this.logicCode = logicCode;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getSnStart() {
        return snStart;
    }

    public void setSnStart(String snStart) {
        this.snStart = snStart;
    }

    public String getSnEnd() {
        return snEnd;
    }

    public void setSnEnd(String snEnd) {
        this.snEnd = snEnd;
    }

    public String getSnStartNum() {
        return snStartNum;
    }

    public void setSnStartNum(String snStartNum) {
        this.snStartNum = snStartNum;
    }

    public String getSnEndNum() {
        return snEndNum;
    }

    public void setSnEndNum(String snEndNum) {
        this.snEndNum = snEndNum;
    }
}
