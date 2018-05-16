package com.ryx.credit.common.result;

import java.io.Serializable;

public class CCardMessageInfo implements Serializable{
	
	//手刷客户ID
	private String userId;
	
	//小贷客户ID
	private String customerId;
	
	//卡号
	private String cardNo;
	
	//卡名称
	private String aliasName;
	
	//客户姓名
	private String customerName;
	
	//身份证号
	private String custPid;
	
	//银行号
	private String bankCode;
	
	//银行支行号
	private String bankBranchCode;
	
	//省编号
	private String bankProvince;
	
	//城市编号
	private String bankCity;
	
	//银行名称
	private String bankName;
	
	//卡类型
	private String cardType;
	
	//电话号码
	private String phoneNum;
	
	
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustPid() {
		return custPid;
	}

	public void setCustPid(String custPid) {
		this.custPid = custPid;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankBranchCode() {
		return bankBranchCode;
	}

	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
}
