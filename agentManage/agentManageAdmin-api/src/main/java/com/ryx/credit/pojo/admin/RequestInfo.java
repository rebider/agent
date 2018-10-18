package com.ryx.credit.pojo.admin;

import java.io.Serializable;

/**
 * 请求报文
 * @author wangqi
 *
 */
public class RequestInfo implements Serializable{
	/**
	 * 交易码
	 */
	private String trancode;
	/**
	 * 短信类型
	 */
	private String smsType;
	/**
	 * 系统来源
	 */
	private String sysId;
	/**
	 * 手机号
	 */
	private String mobileNos;
	/**
	 * 短信模板ID
	 */
	private String templateId;
	/**
	 * params
	 */
	private String params;
	
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getTrancode() {
		return trancode;
	}
	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public String getMobileNos() {
		return mobileNos;
	}
	public void setMobileNos(String mobileNos) {
		this.mobileNos = mobileNos;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	public String getSmsType() {
		return smsType == null ? "01" : smsType;
	}
	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}
	@Override
	public String toString() {
		return "RequestInfo [smsType=" + smsType + ", sysId=" + sysId + ", mobileNos=" + mobileNos + ", templateId="
				+ templateId + "]";
	}
}
