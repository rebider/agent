package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: agentManage
 * @description: 订单调整商品vo
 * @author: ssx
 * @create: 2019-11-05 21:35
 **/
public class AdjProVo implements Serializable {
    private  String oSubId;
    private String adjNum;
    private BigDecimal calPrice;
    private String adjDetailId;

    public String getAdjDetailId() {
        return adjDetailId;
    }

    public void setAdjDetailId(String adjDetailId) {
        this.adjDetailId = adjDetailId;
    }

    public String getoSubId() {
        return oSubId;
    }

    public void setoSubId(String oSubId) {
        this.oSubId = oSubId;
    }

    public String getAdjNum() {
        return adjNum;
    }

    public void setAdjNum(String adjNum) {
        this.adjNum = adjNum;
    }

    public BigDecimal getCalPrice() {
        return calPrice;
    }

    public void setCalPrice(BigDecimal calPrice) {
        this.calPrice = calPrice;
    }
}
