package com.ryx.credit.util;

/**
 * 自定义异常处理类
 * @author ryx
 *
 */
public class ProcessorException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorCode;

	public ProcessorException(){}
	
	public ProcessorException(String msg) {
		super(msg);
	}
	
	
	public ProcessorException(String errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}


	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


}
