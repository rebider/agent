package com.ryx.iom.plugin.entinfo.proxy;

import java.util.Map;

public interface IEntInfoProxy {

	public Map<String, String> queryEntInfo(String regNo, String entName, String isCache);

}
