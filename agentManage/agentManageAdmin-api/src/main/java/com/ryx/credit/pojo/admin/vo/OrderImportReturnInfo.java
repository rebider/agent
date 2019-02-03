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
public class OrderImportReturnInfo   implements Serializable {

    public static List<String> colum  = Arrays.asList("returnOrderId",
            "agentUniqId",
            "agentName",
            "returnPayMethod",
            "returnPayAmt",
            "haveReturnPayAmt",
            "returnFqCount",
            "returnFqDate");

    public void loadInfoFromJsonArray(JSONArray data,String importId){
        if(data!=null){
           this.setReturnOrderId(data.getString(colum.indexOf("returnOrderId")));
            this.setAgentUniqId(data.getString(colum.indexOf("agentUniqId")));
            this.setAgentName(data.getString(colum.indexOf("agentName")));
            this.setReturnPayMethod(data.getString(colum.indexOf("returnPayMethod")));
            this.setReturnPayAmt(data.getString(colum.indexOf("returnPayAmt")));
            this.setHaveReturnPayAmt(data.getString(colum.indexOf("haveReturnPayAmt")));
            this.setReturnFqCount(data.getString(colum.indexOf("returnFqCount")));
            this.setReturnFqDate(data.getString(colum.indexOf("returnFqDate")));
        }
        this.importId = importId;
    }

    private String returnOrderId;

    private String agentUniqId;

    private String agentName;

    private String returnPayMethod;

    private String returnPayAmt;

    private String haveReturnPayAmt;

    private String returnFqCount;

    private String returnFqDate;

    private String importId;

    public String getImportId() {
        return importId;
    }

    public void setImportId(String importId) {
        this.importId = importId;
    }

    public String getReturnOrderId() {
        return returnOrderId;
    }

    public void setReturnOrderId(String returnOrderId) {
        this.returnOrderId = returnOrderId;
    }

    public String getAgentUniqId() {
        return agentUniqId;
    }

    public void setAgentUniqId(String agentUniqId) {
        this.agentUniqId = agentUniqId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getReturnPayMethod() {
        return returnPayMethod;
    }

    public void setReturnPayMethod(String returnPayMethod) {
        this.returnPayMethod = returnPayMethod;
    }

    public String getReturnPayAmt() {
        return returnPayAmt;
    }

    public void setReturnPayAmt(String returnPayAmt) {
        this.returnPayAmt = returnPayAmt;
    }

    public String getHaveReturnPayAmt() {
        return haveReturnPayAmt;
    }

    public void setHaveReturnPayAmt(String haveReturnPayAmt) {
        this.haveReturnPayAmt = haveReturnPayAmt;
    }

    public String getReturnFqCount() {
        return returnFqCount;
    }

    public void setReturnFqCount(String returnFqCount) {
        this.returnFqCount = returnFqCount;
    }

    public String getReturnFqDate() {
        return returnFqDate;
    }

    public void setReturnFqDate(String returnFqDate) {
        this.returnFqDate = returnFqDate;
    }
}
