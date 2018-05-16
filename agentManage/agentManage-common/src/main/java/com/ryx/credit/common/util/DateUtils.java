package com.ryx.credit.common.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间String  Date之间转换
 * @author RYX
 *
 */
public class DateUtils {

	/**
	 * Date类型时间转换将Date时间只有年月日
	 * @throws ParseException 
	 */
	public static Date dealTimeYMD(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String format = sdf.format(date);
		Date parse = null;
		try {
			parse = sdf.parse(format);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parse;
	};
	
	/**
	 * 获取传入日期的前一天
	 */
	public static String beforeDate(Date date){
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");  
		String beforeDate = df.format(new Date(date.getTime()-(long)24 * 60 * 60 * 1000));
		return beforeDate;
	}
	
	/**
	 * 将String时间（20170402）类型转换成Date时间
	 */
	public static Date timeStrDate(String time){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		Date parse = null;
		try {
			parse = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date = dealTimeYMD(parse);
		return date;
	}
	
	/**
	 * 将date时间转换成String时间类型
	 */
	public static String dateToString(Date date){
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");  
		String format = df.format(date);
		return format;
	}
	/**
	 * 将date时间转换成String - 时间类型
	 */
	public static String dateToStrings(Date date){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");  
		String formats = df.format(date);
		return formats;
	}
	/**
	 * 将date时间转换成String - 时间类型
	 */
	public static String dateToStringss(Date date){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String formats = df.format(date);
		return formats;
	}
	
	/**
	 * 将String时间（20170402）类型转换成Date时间
	 */
	public static Date timelongStrDate(String time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date parse = null;
		try {
			parse = formatter.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parse;
	}
	
	/**
	 * 当前时间减去一定的分钟数
	 */
	public static Date getbeforeDateTime(int minuts){
	    Calendar c = Calendar.getInstance();//实例化
		c.add(Calendar.MINUTE, -minuts);//分钟-3
	    return c.getTime();
	}
	
	public static Date dateSubDay(Date bigDecimal , Integer day){
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(bigDecimal);
		rightNow.add(Calendar.DAY_OF_YEAR,day);
		Date dt1=rightNow.getTime();
		return dt1;
	}
}
