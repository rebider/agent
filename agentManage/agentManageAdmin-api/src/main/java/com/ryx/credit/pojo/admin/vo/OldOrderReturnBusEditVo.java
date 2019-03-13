package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;

/**
 * 作者：cx
 * 时间：2019/3/12
 * 描述：
 */
public class OldOrderReturnBusEditVo implements Serializable {
    private String returnid;
    private String returndetailid;
    private String orderid;
    private String productid;
    private String platform;
    private String activity;
    private String modeltype;
    private String proprice;
    private String manufacturer;
    private String promode;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getModeltype() {
        return modeltype;
    }

    public void setModeltype(String modeltype) {
        this.modeltype = modeltype;
    }

    public String getProprice() {
        return proprice;
    }

    public void setProprice(String proprice) {
        this.proprice = proprice;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPromode() {
        return promode;
    }

    public void setPromode(String promode) {
        this.promode = promode;
    }

    public String getReturnid() {
        return returnid;
    }

    public void setReturnid(String returnid) {
        this.returnid = returnid;
    }

    public String getReturndetailid() {
        return returndetailid;
    }

    public void setReturndetailid(String returndetailid) {
        this.returndetailid = returndetailid;
    }
}
