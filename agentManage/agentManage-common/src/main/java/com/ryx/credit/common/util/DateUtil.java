package com.ryx.credit.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;


/**
 * 日期工具类
 * 
 * @author xql
 * 
 */
public class DateUtil {

    public final static String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_FORMAT_2 = "yyyyMMddHHmmss";
    public final static String DATE_FORMAT_3 = "yyyyMMdd";
    public final static String DATE_FORMAT_4 = "yyyyMMddHHmmssss";
    public final static String DATE_FORMAT_yyyyMM = "yyyyMM";
    public final static String DATE_FORMAT_yyyy_MM = "yyyy-MM";
    public final static String DATE_FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
    public final static String DATE_FORMAT_yyyy_MM_dd2 = "yyyy/MM/dd";


    public final static SimpleDateFormat sdfDays = new SimpleDateFormat(
            "yyyyMMdd");

    public final static SimpleDateFormat sdf_Days = new SimpleDateFormat(
            "yyyy-MM-dd");

    public final static SimpleDateFormat sdf_g_Days = new SimpleDateFormat(
            "yyyy/MM/dd");

    /**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @return 日期字符串
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @return 日期字符串
	 */
	public static String formatDay(Date date) {
		return format(date, "yyyy-MM-dd");
	}
 
	
	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @param pattern
	 *            字符串格式
	 * @return 日期字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "null";
		}
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 日期加减運算
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

    /**
     * 日期加减運算
     * @param date
     * @param month
     * @return
     */
    public static Date addMonth(Date date, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }


    public static Date addMonth(String sdate, int month) {
        Date date = format(sdate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

	/**
	 * 将字符串转换为Date类型
	 *
	 * @param date
	 *            字符串类型
	 * @return 日期类型
	 */
	public static Date format(String date) {
		return format(date, null);
	}

	/**
	 * 将字符串转换为Date类型
	 *
	 * @param date
	 *            字符串类型
	 * @param pattern
	 *            格式
	 * @return 日期类型
	 */
	public static Date format(String date, String pattern) {
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		if (date == null || date.equals("") || date.equals("null")) {
			return new Date();
		}
		Date d = null;
		try {
			d = new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException pe) {
		}
		return d;
	}
	/**
	 * 
	 * yyyy-MM-dd
	 * */
	public static Date StrToDate(String str) {
		  
		   SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		   Date date = null;
		   try {
		    date = format.parse(str);
		    System.out.println(date);
		   } catch (ParseException e) {
		    e.printStackTrace();
		   }
		   return date;
		}

    /**
     * 获取YYYYMM格式的上个月日期，也是YYYYMM
     * @param source
     * @return
     */
    public static String convert ( String source )
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMM");
            Date date = sdf.parse (source);
            Calendar calendar = Calendar.getInstance ();
            calendar.setTime (date);
            calendar.add (Calendar.MONTH, -1);
            return sdf.format (calendar.getTime ());
        }
        catch (ParseException e)
        {
            e.printStackTrace ();
        }
        return "";
    }

    /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    } 
    
    /**  
     * 比较两个日期的先后
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static boolean compareDate(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        if(time1>time2){
        	return true;
        }
            
       return false;           
    }
    /**
     * 比较日期大小
     * 
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static boolean compareDate_2(Date smdate,Date bdate) throws ParseException
    {
        
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        if(time1>=time2){
            return true;
        }
            
       return false;           
    }

    public static String getDateDandom(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowDate = dateFormat.format(now);
        String str = "";
        Random ra =new Random();
        for (int i=0;i<5;i++){
            str = ra.nextInt(9)+1+"";
        }
        return nowDate+str;
    }


    /**
     * 得到本月的第一天
     *
     * @return
     */
    public static Timestamp getMonthFirstDay(Integer monthoffset) {
        return getMonthFirstDay(monthoffset, null);
    }

    /**
     * 得到某月的第一天
     *
     * @return
     */
    public static Timestamp getMonthFirstDay(Integer monthoffset, Timestamp time) {
        Calendar calendar = Calendar.getInstance();
        if (time != null) {
            calendar.setTime(time);
        }
        if (monthoffset != null) {
            calendar.add(Calendar.MONTH, monthoffset);
        }
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMinimum(Calendar.DAY_OF_MONTH));

        Date date = getDateFromStr(getFormatDate(calendar.getTime()));
        return new Timestamp(date.getTime());
    }

    /**
     * 得到本月的最后一天
     *
     * @return
     */
    public static Timestamp getMonthLastDay(Integer monthoffset) {
        return getMonthLastDay(monthoffset, null);
    }

    /**
     * 得到本月的最后一天
     *
     * @return
     */
    public static Timestamp getMonthLastDay(Integer monthoffset, Timestamp time) {
        Calendar calendar = Calendar.getInstance();
        if (time != null) {
            calendar.setTime(time);
        }
        if (monthoffset != null) {
            calendar.add(Calendar.MONTH, monthoffset);
        }
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH));

        Date date = getDateFromStr(getFormatDate(calendar.getTime()) + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        return new Timestamp(date.getTime());
    }

    /**
     * 把日期格式化为yyyy-MM-dd格式的字符串
     * @param date 日期对象
     * @return yyyy-MM-dd
     */
    public static String getFormatDate(Date date) {
        try {
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            String result = formater.format(date);
            return result;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 把字符串转换成日期对象
     *
     * @param source        字符串
     * @param sourcePattern 字符串格式
     * @return 日期对象
     */
    public static Date getDateFromStr(String source, String sourcePattern) {
        SimpleDateFormat formator = new SimpleDateFormat(sourcePattern);
        try {
            Date date = formator.parse(source);
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 把yyyy-MM-dd格式的字符串转换成日期
     *
     * @param source yyyy-MM-dd格式的字符串
     * @return Date
     */
    public static Date getDateFromStr(String source) {
        SimpleDateFormat formator = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formator.parse(source);
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    public static Date zero(){
        long current=System.currentTimeMillis();//当前时间毫秒数
        long zero=current/(1000*3600*24)*(1000*3600*24)- TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        return new Timestamp(zero);
    }

    public static Date twelve(){
        long current=System.currentTimeMillis();//当前时间毫秒数
        long zero=current/(1000*3600*24)*(1000*3600*24)- TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long twelve=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数
        return new Timestamp(twelve);
    }

    public static String getDateToString(Date date){
    	SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
		if(null==date){
			return null;
		}
		String parse = sdfDays.format(date);
		return parse;
	}
    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays(){

        return sdfDays.format(new Date());
    }

    /**
     * 得到n天之后的日期
     * @param days
     * "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getAfterDayDate(String days , SimpleDateFormat starttime2) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util�?
        canlendar.add(Calendar.DATE, daysInt); // 日期�?如果不够减会将月变动
        Date date = canlendar.getTime();

        String dateStr = starttime2.format(date);

        return dateStr;
    }

    /**
     * 查询当前时间前一年
     * @param date
     * @return
     */
    public static Date aYearAgoDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,+1);
        Date yDate = calendar.getTime();
        return yDate;
    }

    /**
     * 获取下个月
     * @return
     */
    public static String getPerFirstDayOfMonth() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }

    /**
     * 得到几天后的时间
     * @param d
     * @param day
     * @return
     */
    public static String getDateAfter(Date d, int day) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);//+后 -前
        return dft.format(now.getTime());
    }

    /**
     * 获取一年以后
     * @param d
     * @return
     */
    public static Date getOneYearLater(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.YEAR, now.get(Calendar.YEAR)+1);//+后 -前
        return now.getTime();
    }



}
