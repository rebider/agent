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
public class OrderImportReturnLogincInfo   implements Serializable {


    public static List<String> colum  = Arrays.asList(
            "returnOrderId",
            "platformOrgNum",
            "agentName",
            "returnDate",
            "returnCS",
            "snStart",
            "snEnd",
            "snStartNum",
            "snEndNum");

    public void loadInfoFromJsonArray(JSONArray data){
        if(data!=null){
            this.setReturnOrderId(data.getString(colum.indexOf("returnOrderId")));
            this.setPlatformOrgNum(data.getString(colum.indexOf("platformOrgNum")));
            this.setAgentName(data.getString(colum.indexOf("agentName")));
            this.setReturnDate(data.getString(colum.indexOf("returnDate")));
            this.setReturnCS(data.getString(colum.indexOf("returnCS")));
            this.setSnStart(data.getString(colum.indexOf("snStart")));
            this.setSnEnd(data.getString(colum.indexOf("snEnd")));
            this.setSnStartNum(data.getString(colum.indexOf("snStartNum")));
            this.setSnEndNum(data.getString(colum.indexOf("snEndNum")));
        }
    }

    private String returnOrderId;

    private String platformOrgNum;

    private String agentName;

    private String returnDate;

    private String returnCS;

    private String snStart;
    private String snEnd;
    private String snStartNum;
    private String snEndNum;


    public String getReturnOrderId() {
        return returnOrderId;
    }

    public void setReturnOrderId(String returnOrderId) {
        this.returnOrderId = returnOrderId;
    }

    public String getPlatformOrgNum() {
        return platformOrgNum;
    }

    public void setPlatformOrgNum(String platformOrgNum) {
        this.platformOrgNum = platformOrgNum;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnCS() {
        return returnCS;
    }

    public void setReturnCS(String returnCS) {
        this.returnCS = returnCS;
    }

    public String getSnStart() {
        return snStart;
    }

    public void setSnStart(String snStart) {
        this.snStart = snStart;
    }

    public String getSnStartNum() {
        return snStartNum;
    }

    public void setSnStartNum(String snStartNum) {
        this.snStartNum = snStartNum;
    }

    public String getSnEnd() {
        return snEnd;
    }

    public void setSnEnd(String snEnd) {
        this.snEnd = snEnd;
    }

    public String getSnEndNum() {
        return snEndNum;
    }

    public void setSnEndNum(String snEndNum) {
        this.snEndNum = snEndNum;
    }
}
