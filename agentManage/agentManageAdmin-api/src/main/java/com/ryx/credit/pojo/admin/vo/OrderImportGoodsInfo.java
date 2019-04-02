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
public class OrderImportGoodsInfo implements Serializable {

    public static List<String> colum  = Arrays.asList("order_id",
            "order_orgid",
            "order_platform",
            "order_agname","goodsCode","goodsName","goodsNum",
            "goodsRemark","actId","actCode","actName");

    public void loadInfoFromJsonArray(JSONArray data,String importId){
        if(data!=null){
            this.setOrder_id((colum.indexOf("order_id")<data.size())?data.getString(colum.indexOf("order_id")):"");
            this.setOrder_orgid((colum.indexOf("order_orgid")<data.size())?data.getString(colum.indexOf("order_orgid")):"");
            this.setOrder_platform((colum.indexOf("order_platform")<data.size())?data.getString(colum.indexOf("order_platform")):"");
            this.setOrder_agname((colum.indexOf("order_agname")<data.size())?data.getString(colum.indexOf("order_agname")):"");
            this.setGoodsCode((colum.indexOf("goodsCode")<data.size())?data.getString(colum.indexOf("goodsCode")):"");
            this.setGoodsName((colum.indexOf("goodsName")<data.size())?data.getString(colum.indexOf("goodsName")):"");
            this.setGoodsNum((colum.indexOf("goodsNum")<data.size())?data.getString(colum.indexOf("goodsNum")):"");
            this.setGoodsRemark((colum.indexOf("goodsRemark")<data.size())?data.getString(colum.indexOf("goodsRemark")):"");
            this.setActId((colum.indexOf("actId")<data.size())?data.getString(colum.indexOf("actId")):"");
            this.setActCode((colum.indexOf("actCode")<data.size())?data.getString(colum.indexOf("actCode")):"");
            this.setActName((colum.indexOf("actName")<data.size())?data.getString(colum.indexOf("actName")):"");
        }
        this.importId=importId;
    }

    public  String order_id ;
    public  String order_orgid ;
    public  String order_platform ;
    public  String order_agname ;

    public String goodsCode;
    public String goodsName;
    public String goodsNum;
    public String goodsRemark;
    public String actId;
    public String actCode;
    public String actName;

    private List<OrderLogicInfo> logicInfos;

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

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsRemark() {
        return goodsRemark;
    }

    public void setGoodsRemark(String goodsRemark) {
        this.goodsRemark = goodsRemark;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public List<OrderLogicInfo> getLogicInfos() {
        return logicInfos;
    }

    public void setLogicInfos(List<OrderLogicInfo> logicInfos) {
        this.logicInfos = logicInfos;
    }
}
