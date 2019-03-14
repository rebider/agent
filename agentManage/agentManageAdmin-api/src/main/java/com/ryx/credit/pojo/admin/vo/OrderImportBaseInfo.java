package com.ryx.credit.pojo.admin.vo;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者：cx
 * 时间：2019/1/29
 * 描述：
 */
public class OrderImportBaseInfo implements Serializable {

    public static  List<String> colum  = Arrays.asList(
            "order_id",
            "order_orgid",
            "order_platform",
            "order_agname",
            "order_date","order_amt","order_have_amt",
            "order_paymethod","order_shoufu_amt","order_fenqi_count","order_fenqi_date","order_colcomp","order_pay_user","order_is_fp",
            "order_dk_type","order_dk_amt","order_hkr","order_hk_date","order_remark");

    public void loadInfoFromJsonArray(JSONArray data,String importId){
        if(data!=null){
            this.setOrder_id(data.getString(colum.indexOf("order_id")));
            this.setOrder_orgid(data.getString(colum.indexOf("order_orgid")));
            this.setOrder_platform(data.getString(colum.indexOf("order_platform")));
            this.setOrder_agname(data.getString(colum.indexOf("order_agname")));
            this.setOrder_date(data.getString(colum.indexOf("order_date")));
            this.setOrder_amt(data.getString(colum.indexOf("order_amt")));
            this.setOrder_have_amt(data.getString(colum.indexOf("order_have_amt")));
            this.setOrder_paymethod(data.getString(colum.indexOf("order_paymethod")));
            this.setOrder_shoufu_amt(data.getString(colum.indexOf("order_shoufu_amt")));
            this.setOrder_fenqi_count(data.getString(colum.indexOf("order_fenqi_count")));
            this.setOrder_fenqi_date(data.getString(colum.indexOf("order_fenqi_date")));
            this.setOrder_colcomp(data.getString(colum.indexOf("order_colcomp")));
            this.setOrder_pay_user(data.getString(colum.indexOf("order_pay_user")));
            this.setOrder_is_fp(data.getString(colum.indexOf("order_is_fp")));
            this.setOrder_dk_type(data.getString(colum.indexOf("order_dk_type")));
            this.setOrder_dk_amt(data.getString(colum.indexOf("order_dk_amt")));
            this.setOrder_hkr(data.getString(colum.indexOf("order_hkr")));
            this.setOrder_hk_date(data.getString(colum.indexOf("order_hk_date")));
            this.setOrder_remark(data.getString(colum.indexOf("order_remark")));
        }
        this.importId=importId;
    }


    private String importId;
    public  String order_id ;
    public  String order_orgid ;
    public  String order_platform ;
    public  String order_agname ;
    public  String order_date ;
    public  String order_amt ;
    public  String order_have_amt ;
    public  String order_paymethod ;
    public  String order_shoufu_amt ;
    public  String order_fenqi_count ;
    public  String order_fenqi_date ;
    public  String order_colcomp ;
    public  String order_pay_user ;
    public  String order_is_fp ;
    public  String order_dk_type ;
    public  String order_dk_amt ;
    public  String order_hkr ;
    public  String order_hk_date ;
    public  String order_remark ;
    public  List<OrderImportGoodsInfo> orderImportGoodsInfos;

    public String ox_order;

    public String getOx_order() {
        return ox_order;
    }

    public void setOx_order(String ox_order) {
        this.ox_order = ox_order;
    }

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

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_amt() {
        return order_amt;
    }

    public void setOrder_amt(String order_amt) {
        this.order_amt = order_amt;
    }

    public String getOrder_have_amt() {
        return order_have_amt;
    }

    public void setOrder_have_amt(String order_have_amt) {
        this.order_have_amt = order_have_amt;
    }

    public String getOrder_paymethod() {
        return order_paymethod;
    }

    public void setOrder_paymethod(String order_paymethod) {
        this.order_paymethod = order_paymethod;
    }

    public String getOrder_shoufu_amt() {
        return order_shoufu_amt;
    }

    public void setOrder_shoufu_amt(String order_shoufu_amt) {
        this.order_shoufu_amt = order_shoufu_amt;
    }

    public String getOrder_fenqi_count() {
        return order_fenqi_count;
    }

    public void setOrder_fenqi_count(String order_fenqi_count) {
        this.order_fenqi_count = order_fenqi_count;
    }

    public String getOrder_fenqi_date() {
        return order_fenqi_date;
    }

    public void setOrder_fenqi_date(String order_fenqi_date) {
        this.order_fenqi_date = order_fenqi_date;
    }

    public String getOrder_colcomp() {
        return order_colcomp;
    }

    public void setOrder_colcomp(String order_colcomp) {
        this.order_colcomp = order_colcomp;
    }

    public String getOrder_pay_user() {
        return order_pay_user;
    }

    public void setOrder_pay_user(String order_pay_user) {
        this.order_pay_user = order_pay_user;
    }

    public String getOrder_is_fp() {
        return order_is_fp;
    }

    public void setOrder_is_fp(String order_is_fp) {
        this.order_is_fp = order_is_fp;
    }

    public String getOrder_dk_type() {
        return order_dk_type;
    }

    public void setOrder_dk_type(String order_dk_type) {
        this.order_dk_type = order_dk_type;
    }

    public String getOrder_dk_amt() {
        return order_dk_amt;
    }

    public void setOrder_dk_amt(String order_dk_amt) {
        this.order_dk_amt = order_dk_amt;
    }

    public String getOrder_hkr() {
        return order_hkr;
    }

    public void setOrder_hkr(String order_hkr) {
        this.order_hkr = order_hkr;
    }

    public String getOrder_hk_date() {
        return order_hk_date;
    }

    public void setOrder_hk_date(String order_hk_date) {
        this.order_hk_date = order_hk_date;
    }

    public String getOrder_remark() {
        return order_remark;
    }

    public void setOrder_remark(String order_remark) {
        this.order_remark = order_remark;
    }

    public List<OrderImportGoodsInfo> getOrderImportGoodsInfos() {
        return orderImportGoodsInfos;
    }

    public void setOrderImportGoodsInfos(List<OrderImportGoodsInfo> orderImportGoodsInfos) {
        this.orderImportGoodsInfos = orderImportGoodsInfos;
    }
}
