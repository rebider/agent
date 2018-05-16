package com.ryx.credit.common.result;

import java.io.Serializable;

public class PropertyMsg implements Serializable{
	private static final long serialVersionUID = 1L;

	//出款地址
	private String settUrl;
	
	//出款端口号
	private String settPort;

	public String getSettUrl() {
		return settUrl;
	}

	public void setSettUrl(String settUrl) {
		this.settUrl = settUrl;
	}

	public String getSettPort() {
		return settPort;
	}

	public void setSettPort(String settPort) {
		this.settPort = settPort;
	}
}
