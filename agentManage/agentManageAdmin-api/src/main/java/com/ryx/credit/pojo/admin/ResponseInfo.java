package com.ryx.credit.pojo.admin;

import java.io.Serializable;

/**
 * 应答报文
 * @author wangqi
 *
 */
public class ResponseInfo implements Serializable{
	/**
	 * 交易码
	 */
	private String trancode;
	/**
	 * 响应码
	 */
	private String rspCode;
	/**
	 * 响应信息
	 */
	private String rspMsg;
	
	public ResponseInfo(){
		
	}
	
	public ResponseInfo(String rspCode, String rspMsg){
		this.rspCode = rspCode;
		this.rspMsg = rspMsg;
	}
	
	public String getTrancode() {
		return trancode;
	}


	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}


	public String getRspCode() {
		return rspCode;
	}


	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}


	public String getRspMsg() {
		return rspMsg;
	}


	public void setRspMsg(String rspMsg) {
		this.rspMsg = rspMsg;
	}

	@Override
	public String toString() {
		return "ResponseInfo [trancode=" + trancode + ", rspCode=" + rspCode + ", rspMsg=" + rspMsg + "]";
	}
	
}
