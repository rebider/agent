package com.ryx.credit.common.util;

import java.io.Serializable;

/**
 * 用户登录
 *
 * @author wangqi
 * @version 1.0
 * @date 2015年8月3日 下午6:08:17
 * @since 1.0
 */
public class ResultVO implements Serializable{
	public static final String SUCCESS="1";
	public static final String FAIL="0";
	public static final String REDIRECT="302";//此时obj中存放的是url
	private String resCode; // 调用结果,1-成功,0-失败
	private String resInfo; // 调用信息
	private Object obj; // 调用信息
	
	public ResultVO(String resCode, String resInfo) {
		this.resCode = resCode;
		this.resInfo = resInfo;
	}
	
	public ResultVO(String resCode, String resInfo, Object obj) {
		this.resCode = resCode;
		this.resInfo = resInfo;
		this.obj=obj;
	}

	public ResultVO() {
	}

	public static ResultVO success(Object data){
		ResultVO res = new ResultVO(SUCCESS,"成功");
		res.setObj(data);
		return res;
	}

	public static ResultVO fail(String  msg){
		ResultVO res = new ResultVO(FAIL,msg);
		return res;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResInfo() {
		return resInfo;
	}

	public void setResInfo(String resInfo) {
		this.resInfo = resInfo;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isSuccess() {
		return SUCCESS.equals(this.getResCode());
	}
	
}
