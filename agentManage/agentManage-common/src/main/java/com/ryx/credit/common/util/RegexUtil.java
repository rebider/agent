package com.ryx.credit.common.util;

import java.util.regex.Pattern;

/**
 * 正则工具类
 * 
 * @author wangqi
 * 
 */
public class RegexUtil {

	private enum regexp {

		EMAIL("(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)"), 
		INT("^-?\\d+$"), 
		NUM("^-?\\d*\\.{0,1}\\d+$"), 
		MOBILE("^13[0-9]{9}|1[578][0-9][0-9]{8}|14[57][0-9]{8}$"),
		PASSWORD("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$"), 
		REALNAME("[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*"),
		PASSPORTNUM("(P\\d{7})|(G\\d{8})"), 
		ID("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$"),
		NICKNAME("^[\u4E00-\u9FA5|0-9|a-z|A-Z]{2,10}$"),
		//密码中必须包含字母（不区分大小写）、数字、特称字符，至少8个字符，最多16个字符；
		COMP_PASSWORD("^(?=.*?[a-zA-Z]{1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*[\\u4e00-\\u9fa5])(?!.*\\s).{8,16}$");

		private String value;

		private regexp(String value) {
			this.value = value;
		}

	}

	public static boolean checkEmail(String email) {
		if(null==email)
			return false;
		return Pattern.matches(regexp.EMAIL.value, email);
	}
	
	public static boolean checkNickName(String nickname) {
		if(null==nickname)
			return false;
		return Pattern.matches(regexp.NICKNAME.value, nickname);
	}

	public static boolean checkInt(String num) {
		if(null==num)
			return false;
		return Pattern.matches(regexp.INT.value, num);
	}

	public static boolean checkNum(String num) {
		if(null==num)
			return false;
		return Pattern.matches(regexp.NUM.value, num);
	}

	public static boolean checkMobile(String mobile) {
		if(null==mobile)
			return false;
		return Pattern.matches(regexp.MOBILE.value, mobile);
	}

	public static boolean checkPassword(String password) {
		if(null==password)
			return false;
		return Pattern.matches(regexp.PASSWORD.value, password);
	}

	public static boolean checkRealName(String realName) {
		if(null==realName)
			return false;
		return Pattern.matches(regexp.REALNAME.value, realName);
	}

	public static boolean checkPersonId(String perosonId) {
		if(null==perosonId)
			return false;

		return Pattern.matches(regexp.ID.value, perosonId);

	}

	public static boolean checkPassportNum(String passPortNum) {
		if(null==passPortNum)
			return false;
		return Pattern.matches(regexp.PASSPORTNUM.value, passPortNum);

	}

	public static boolean checkCompPassword(String pass) {
		if(null==pass)
			return false;
		return Pattern.matches(regexp.COMP_PASSWORD.value, pass);

	}

	public static String rvZeroAndDot(String s){
		if (s.isEmpty()) {
			return null;
		}
		if(s.indexOf(".") > 0){
			s = s.replaceAll("0+?$", "");//去掉多余的0
			s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
		}
		return s;
	}

	public static void main(String[] args) {
		System.out.println(RegexUtil.checkCompPassword("Test123$"));
	}
}
