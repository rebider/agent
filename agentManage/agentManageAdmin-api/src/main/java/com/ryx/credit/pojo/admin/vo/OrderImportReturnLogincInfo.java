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

    public void loadInfoFromJsonArray(JSONArray data,String importId){
        if(data!=null){
            this.setReturnOrderId((colum.indexOf("returnOrderId")<data.size())?data.getString(colum.indexOf("returnOrderId")):"");
            this.setPlatformOrgNum((colum.indexOf("platformOrgNum")<data.size())?data.getString(colum.indexOf("platformOrgNum")):"");
            this.setAgentName((colum.indexOf("agentName")<data.size())?data.getString(colum.indexOf("agentName")):"");
            this.setReturnDate((colum.indexOf("returnDate")<data.size())?data.getString(colum.indexOf("returnDate")):"");
            this.setReturnCS((colum.indexOf("returnCS")<data.size())?data.getString(colum.indexOf("returnCS")):"");
            this.setSnStart((colum.indexOf("snStart")<data.size())?data.getString(colum.indexOf("snStart")):"");
            this.setSnEnd((colum.indexOf("snEnd")<data.size())?data.getString(colum.indexOf("snEnd")):"");
            this.setSnStartNum((colum.indexOf("snStartNum")<data.size())?data.getString(colum.indexOf("snStartNum")):"");
            this.setSnEndNum((colum.indexOf("snEndNum")<data.size())?data.getString(colum.indexOf("snEndNum")):"");
        }
        this.importId = importId;
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
