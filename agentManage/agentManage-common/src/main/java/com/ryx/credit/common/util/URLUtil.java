package com.ryx.credit.common.util;

public class URLUtil {
	
	public static String getPayUrl(String fileName){
		String url = URLUtil.class.getClassLoader().getResource(fileName).getPath();
		return url;
	}

}
