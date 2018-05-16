package com.ryx.credit.common.result;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RequestPayMsg{

    //交易码
	private String txCode;
	
	//交易流水号  合作方编码(4位) + 时间（yyyyMMddHHmmss） + 8位流水号
	private String requestSN;
	
	//交易时间
	private String transTime;
	
	//交易日期
	private String transDate;
	
	//交易币种
	private String currency;
	
	//身份证号
	private String identityNum;
	
	//卡号
	private String cardNo;
	
	//持卡人
	private String cardName;
	
	//金额(代付金额，以元为单位，精确到小数点后两位)
	private String amt;
	
	//开户行联行号
	private String bankNo;
	
	//开户行名称
	private String bankName;
	
	//收款人手机号
	private String phone;
	
	//备注
	private String remark;
	
	private String inchannel;
	
	public String getInchannel() {
		return inchannel;
	}

	public void setInchannel(String inchannel) {
		this.inchannel = inchannel;
	}

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getRequestSN() {
		return requestSN;
	}

	public void setRequestSN(String requestSN) {
		this.requestSN = requestSN;
	}
	
	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getIdentityNum() {
		return identityNum;
	}

	public void setIdentityNum(String identityNum) {
		this.identityNum = identityNum;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
