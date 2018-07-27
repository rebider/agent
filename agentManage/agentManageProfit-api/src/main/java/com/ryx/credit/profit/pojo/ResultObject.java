package com.ryx.credit.profit.pojo;


import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.JsonUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * ResultObject
 * Created by IntelliJ IDEA.
 *
 * @Author WANGY
 * @Date 2018/7/17
 * @Time: 18:06
 * To change this template use File | Settings | File Templates.
 */

public class ResultObject implements Serializable{

    private String code;
    private String msg;
    private Object result;
    private Object page;
    private String sysdate;
    
    public ResultObject()
    {
    }

    public ResultObject(String jsonObject)
    {
        Map<String,String> map = JsonUtils.parseJSON2Map(jsonObject);
        this.sysdate = map.get("sysdate");
        this.code = map.get("code");
        this.msg = map.get("msg");
        this.result = map.get("result");
        this.page = map.get("page"); }

    public ResultObject(String code, String msg) {
    	this.sysdate = DateUtil.getDays();
        this.code = code;
        this.msg = msg; }

    public ResultObject(String code, String msg, Object result) {
    	this.sysdate = DateUtil.getDays();
        this.code = code;
        this.msg = msg;
        this.result = result; }

    public ResultObject(String code, String msg, Object result, Object page) {
    	this.sysdate = DateUtil.getDays();
        this.code = code;
        this.msg = msg;
        this.result = result;
        this.page = page;
    }

    public boolean isSuccess() {
        return "0000".equals(getCode());
    }

    public String getCode()
    {
        return this.code; }

    public void setCode(String code) {
        this.code = code; }

    public String getMsg() {
        return this.msg; }

    public void setMsg(String msg) {
        this.msg = msg; }

    public Object getResult() {
        return this.result; }

    public void setResult(Object result) {
        this.result = result; }

    public Object getPage() {
        return this.page; }

    public void setPage(Object page) {
        this.page = page;
    }

	public String getSysdate() {
		return sysdate;
	}

	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}

}
