package com.ryx.credit.common.result;

import java.io.Serializable;

public class RespBean6W8020 implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 交易码
	 */
	String hTxCode;
	/**
	 * 应答码
	 */
	String hReturnCode;
	/**
	 * 应答描述
	 */
	String hReturnMsg;
	/**
	 * 合作方流水号a
	 */
	String hRequestSn;
	/**
	 * 应答码类型
	 * E错误;
	   S成功;
	   R不确定（对于不确定交易，可以通过账务交易结果查询，一般3分钟后会有准确结果）
	 */
	String hReturnType;
	/**
	 * 合作方编码
	 */
	String hPartner;
	/**
	 * 清算日期
	 */
	String bSettleDate;
	/**
	 * 渠道标示
	 */
	String bChannel ;
	/**
	 * 服务端流水号
	 */
	String bRemoteSerial;
	/**
	 * 备注
	 */
	String bRemark;
	
	String bDFSerial;
	
	
	public String getbDFSerial() {
		return bDFSerial;
	}
	public void setbDFSerial(String bDFSerial) {
		this.bDFSerial = bDFSerial;
	}
	public String gethTxCode() {
		return hTxCode;
	}
	public void sethTxCode(String hTxCode) {
		this.hTxCode = hTxCode;
	}
	public String gethReturnCode() {
		return hReturnCode;
	}
	public void sethReturnCode(String hReturnCode) {
		this.hReturnCode = hReturnCode;
	}
	public String gethReturnMsg() {
		return hReturnMsg;
	}
	public void sethReturnMsg(String hReturnMsg) {
		this.hReturnMsg = hReturnMsg;
	}
	public String gethRequestSn() {
		return hRequestSn;
	}
	public void sethRequestSn(String hRequestSn) {
		this.hRequestSn = hRequestSn;
	}
	public String gethReturnType() {
		return hReturnType;
	}
	public void sethReturnType(String hReturnType) {
		this.hReturnType = hReturnType;
	}
	public String gethPartner() {
		return hPartner;
	}
	public void sethPartner(String hPartner) {
		this.hPartner = hPartner;
	}
	public String getbSettleDate() {
		return bSettleDate;
	}
	public void setbSettleDate(String bSettleDate) {
		this.bSettleDate = bSettleDate;
	}
	public String getbChannel() {
		return bChannel;
	}
	public void setbChannel(String bChannel) {
		this.bChannel = bChannel;
	}
	public String getbRemoteSerial() {
		return bRemoteSerial;
	}
	public void setbRemoteSerial(String bRemoteSerial) {
		this.bRemoteSerial = bRemoteSerial;
	}
	public String getbRemark() {
		return bRemark;
	}
	public void setbRemark(String bRemark) {
		this.bRemark = bRemark;
	}

}
