package com.ryx.credit.common.util;

import org.apache.commons.net.ftp.FTP;

import java.io.Serializable;

/**
 * @Name: FTPConfig.java
 * @Deprecated: FTP配置信息实体
 * @Author: stark
 * @Version: 1.0
 * @Create Date: 2016年08月29
 */
public class FTPConfig implements Serializable {

    public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	private static final long serialVersionUID = 1L;
    // FTP 登录用户名
	private String userName;
	// FTP 登录密码
	private String password;
	// FTP 服务器地址IP地址
	private String ip;
	// FTP 端口
	private int port = 21;
	// FTP 字符集
	private String encode;
	// FTP 超时时间设置
	private Integer timeout = 120000;
	// 设置文件传输类型，默认二进制
	private int fileType=FTP.BINARY_FILE_TYPE;
	// 服务器路径
	private String path;
	//区分Ftp
	private String  ftpType;

	public String getFtpType() {
		return ftpType;
	}

	public void setFtpType(String ftpType) {
		this.ftpType = ftpType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

}
