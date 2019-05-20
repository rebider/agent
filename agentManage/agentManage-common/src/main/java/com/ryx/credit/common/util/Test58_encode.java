package com.ryx.credit.common.util;

import java.io.UnsupportedEncodingException;

public class Test58_encode  
{  
    public static void main(String[] args) throws Exception {
    	String result = encode("89860404191740471183");
    	System.out.println(result);
	}
	
    /*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	private static String hexString = "0123456789ABCDEF";

	public static String encode(String str) throws UnsupportedEncodingException {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes("GBK");
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}
}  
