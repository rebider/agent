package com.ryx.credit.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

public final class DateKit {
	
	public static final SimpleDateFormat COMPACT_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat COMPACT_TIME_FORMAT = new SimpleDateFormat("HHmmss");
	private static final SimpleDateFormat COMPACT_DATETIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat COMPACT_MONTH_FORMAT = new SimpleDateFormat("yyyyMM");
	private static final SimpleDateFormat COMPACT_YEAR_FORMAT = new SimpleDateFormat("yyyy");

	public static final SimpleDateFormat STANDARD_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat STANDARD_DATEM_FORMAT = new SimpleDateFormat("yyyy-MM");
	public static final SimpleDateFormat STANDARD_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final long ONE_HOUR_MILLIS = 3600*1000L;
	public static final long ONE_DAY_MILLIS = 24*3600*1000L;
	public static final long SEVEN_DAYS_MILLIS = 7*24*3600*1000L;
	public static final long THIRTY_DAYS_MILLIS = 30*24*3600*1000L;
	
	private static int growingNumber;
	private static long growingNumberForSms;
	
	
	public static String generateId() {
		if(growingNumber>999) growingNumber=0;
		return Long.toString(getCurrentMillis()) + StringUtils.leftPad(Integer.toString(growingNumber++), 3, "0");
	}
	/**
	 * 用于短信验证码id生成
	 * @return
	 */
	public static String generateIdForSms() {
		if(growingNumberForSms>99999) growingNumberForSms=0;
		return getCompactDatetimeString(getNow())+ StringUtils.leftPad(Integer.toString(growingNumber++), 6, "0");
	}
	public static long getCurrentMillis() {
		return Calendar.getInstance().getTimeInMillis();
	}
	
	/**
	 * 获取指定格式的日期
	 * @author zhupeng
	 * @param pattern
	 * @return
	 */
	public static String getCurrentDateFormat(SimpleDateFormat pattern) {
		return pattern.format(getCurrentDate());
	}
	
	/**
	 * 取得当前系统日期
	 * @return
	 */
	public static Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}
	
	public static Date getRandomTime() {
		return getRandomTime(parseDatetime(0L), parseDatetime(ONE_DAY_MILLIS-1L));
	}
	
	public static Date getRandomTime(Date floorTime, Date ceilingTime) {
		floorTime = parseDatetime(floorTime.getTime() % ONE_DAY_MILLIS);
		ceilingTime = parseDatetime(ceilingTime.getTime() % ONE_DAY_MILLIS);
		long gap = ceilingTime.getTime() - floorTime.getTime();
		long baseMillis = floorTime.getTime();
		long offset = (long) (gap * RandomUtils.nextDouble());
		return parseDatetime(baseMillis + offset, true);
	}
	
	public static Date getNow(boolean ignoreMillis) {
		if(ignoreMillis) {
			Calendar now = Calendar.getInstance();
			now.setTimeInMillis(now.getTimeInMillis()/1000*1000);
			return now.getTime();
		} else {
			return Calendar.getInstance().getTime();
		}
	}
	
	public static Date getNow() {
		return getNow(false);
	}
	
	public static Date getToday() {
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(now.getTimeInMillis()/1000*1000);
		now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), 0, 0, 0);
		return now.getTime();
	}
	
	public static Date getYesterday() {
		return getYesterday(DateKit.getToday());
	}
	
	public static Date getYesterday(Date originalDate) {
		return getLastDay(originalDate, 1);
	}
	
	public static Date getLastDay(Date originalDate, int times) {
		return getNextDay(originalDate, -1*times);
	}
	
	public static Date getTomorrow() {
		return getTomorrow(getToday());
	}
	
	public static Date getTomorrow(Date originalDate) {
		return getNextDay(originalDate, 1);
	}
	
	public static Date getNextDay(Date originalDate, int times) {
		if(originalDate==null) return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(originalDate);
		calendar.add(Calendar.DATE, times);
		return calendar.getTime();
	}
	
	public static Date getEndOfDay(Date originalDate) {
		if(originalDate==null) return null;
		originalDate = getDay(originalDate);
		return parseDatetime(originalDate.getTime() + ONE_DAY_MILLIS - 1, true);
	}
	
	public static Date getCurrentMonth() {
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(now.getTimeInMillis()/1000*1000);
		now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 0, 0, 0, 0);
		return now.getTime();
	}
	
	public static Date getMonth(Date originalDate) {
		if(originalDate==null) return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(originalDate);
		calendar.setTimeInMillis(calendar.getTimeInMillis()/1000*1000);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
		return calendar.getTime();
	}
	
	public static Date getDay(Date originalDate) {
		if(originalDate==null) return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(originalDate);
		calendar.setTimeInMillis(calendar.getTimeInMillis()/1000*1000);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return calendar.getTime();
	}
	
	public static Date parseDatetime(int year, int month, int day, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month-1, day, hour, minute, second);
		return calendar.getTime();
	}
	
	public static Date parseDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month-1, day);
		return calendar.getTime();
	}
	
	public static Date parseDatetime(long millis) {
		return parseDatetime(millis, false);
	}
	
	public static Date parseDatetime(long millis, boolean ignoreMillis) {
		Calendar calendar = Calendar.getInstance();
		if(ignoreMillis) {
			millis = millis/1000*1000;
		}
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
	
	public static Date getLastMonth(Date originalMonth, int times) {
		return getNextMonth(originalMonth, -1*times);
	}
	
	public static Date getLastMonth(Date originalMonth) {
		return getNextMonth(originalMonth, -1);
	}
	
	public static Date getNextMonth(Date originalMonth) {
		return getNextMonth(originalMonth, 1);
	}
	
	public static Date getNextMonth(Date originalMonth, int times) {
		if(originalMonth==null) return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(originalMonth);
		calendar.add(Calendar.MONTH, times);
		return calendar.getTime();
	}
	
	public static Date getFirstDayOfMonth(Date date) {
		if(date==null) return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}
	
	/**
	 * 获取上个月的第一天
	 * @author zhupeng
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfLastMonth(SimpleDateFormat pattern) {
		Calendar cale = Calendar.getInstance();  
        cale.add(Calendar.MONTH, -1);  
        cale.set(Calendar.DAY_OF_MONTH, 1);  
        return pattern.format(cale.getTime()); 
	}
	
	/**
	 * 获取指定间隔月份的第一天
	 * @author zhupeng
	 * @param pattern
	 * @param factor
	 * @return
	 */
	public static String getFirstDayOfMonth(String date, int factor) {
		Calendar calendar1 = Calendar.getInstance();
		try {
			calendar1.setTime(STANDARD_DATEM_FORMAT.parse(date));
			calendar1.add(Calendar.MONTH, - factor);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return STANDARD_DATEM_FORMAT.format(calendar1.getTime());
	}
	
	public static Date getLastDayOfMonth(Date date) {
		if(date==null) return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}
	
	public static Date getCompactDate(String originalDateString) {
		return getCompactDate(originalDateString, null);
	}
	
	public static Date getCompactDate(String originalDateString, Date defaultValue) {
		if(originalDateString==null) return defaultValue;
		try {
			return COMPACT_DATE_FORMAT.parse(originalDateString);
		} catch (ParseException e) {
			return defaultValue;
		}
	}
	
	public static Date getStandardDate(String originalDateString) {
		return getStandardDate(originalDateString, null);
	}
	
	public static Date getStandardDate(String originalDateString, Date defaultValue) {
		if(originalDateString==null) return defaultValue;
		try {
			return STANDARD_DATE_FORMAT.parse(originalDateString);
		} catch (ParseException e) {
			return defaultValue;
		}
	}
	
	public static Date getCompactDatetime(String originalDateString) {
		return getCompactDatetime(originalDateString, null);
	}
	
	public static Date getCompactDatetime(String originalDateString, Date defaultValue) {
		if(originalDateString==null) return defaultValue;
		try {
			return COMPACT_DATETIME_FORMAT.parse(originalDateString);
		} catch (ParseException e) {
			return defaultValue;
		}
	}
	
	public static Date getStandardDatetime(String originalDateString) {
		return getStandardDatetime(originalDateString, null);
	}
	
	public static Date getStandardDatetime(String originalDateString, Date defaultValue) {
		if(originalDateString==null) return defaultValue;
		try {
			return STANDARD_DATETIME_FORMAT.parse(originalDateString);
		} catch (ParseException e) {
			return defaultValue;
		}
	}
	
	public static Date getCompactMonth(String originalMonthString) {
		return getCompactMonth(originalMonthString, null);
	}
	
	public static Date getCompactMonth(String originalMonthString, Date defaultValue) {
		if(originalMonthString==null) return defaultValue;
		try {
			return COMPACT_MONTH_FORMAT.parse(originalMonthString);
		} catch (ParseException e) {
			return defaultValue;
		}
	}
	
	public static Date getCompactYear(String originalYearString) {
		return getCompactYear(originalYearString, null);
	}
	
	public static Date getCompactYear(String originalYearString, Date defaultValue) {
		if(originalYearString==null) return defaultValue;
		try {
			return COMPACT_YEAR_FORMAT.parse(originalYearString);
		} catch (ParseException e) {
			return defaultValue;
		}
	}
	
	public static List<Date> betweenMonths(Date floorMonth, Date ceilingMonth) {
		floorMonth = getMonth(floorMonth);
		ceilingMonth = getMonth(ceilingMonth);
		ArrayList<Date> months = new ArrayList<Date>();
		if(isEarlierThan(floorMonth, ceilingMonth)) return months;
		Date middleMonth = floorMonth;
		while(isNotLaterThan(middleMonth, ceilingMonth)) {
			months.add(middleMonth);
			middleMonth = getMonth(getNextMonth(months.get(months.size()-1)));
		}
		return months;
	}
	
	public static List<Date> betweenDays(Date floorDate, Date ceilingDate) {
		floorDate = getDay(floorDate);
		ceilingDate = getDay(ceilingDate);
		ArrayList<Date> dates = new ArrayList<Date>();
		if(isLaterThan(floorDate, ceilingDate)) return dates;
		Date middleDate = floorDate;
		while(isNotLaterThan(middleDate, ceilingDate)) {
			dates.add(middleDate);
			middleDate = getDay(getTomorrow(dates.get(dates.size()-1)));
		}
		return dates;
	}

	
	public static List<Date> betweenDays(Date floorDate, Date ceilingDate, int size) {
		List<Date> dates = betweenDays(floorDate, ceilingDate);
		final int dateSize = dates.size();
		// 时间列表中超过一个元素时再混淆顺序
		if(dateSize>1) {
			for(int i=0; i<dateSize; i++) {
				int positionA = RandomUtils.nextInt(dateSize);
				int positionB = RandomUtils.nextInt(dateSize-1);
				dates.add(positionB, dates.remove(positionA));
			}
		}
		List<Date> result = new ArrayList<Date>(size);
		result.addAll(dates.subList(0, Math.min(dates.size(), size)));
		if(size>dates.size()) {
			for(int i=dates.size(); i<size; i++) {
				result.add(dates.get(RandomUtils.nextInt(dates.size())));
			}
		}
		for(int i=0; i<size; i++) {
			int positionA = RandomUtils.nextInt(size);
			int positionB = RandomUtils.nextInt(size-1);
			result.add(positionB, result.remove(positionA));
		}
		return result;
	}
	
	public static String getCompactDateString(Date originalDate) {
		if(originalDate==null) return null;
		return COMPACT_DATE_FORMAT.format(originalDate);
	}
	
	public static String getCompactTimeString(Date originalDate) {
		if(originalDate==null) return null;
		return COMPACT_TIME_FORMAT.format(originalDate);
	}
	
	public static String getCompactDatetimeString(Date originalDate) {
		if(originalDate==null) return null;
		return COMPACT_DATETIME_FORMAT.format(originalDate);
	}
	
	public static String getCompactMonthString(Date originalDate) {
		if(originalDate==null) return null;
		return COMPACT_MONTH_FORMAT.format(originalDate);
	}
	
	public static String getCompactYearString(Date originalDate) {
		if(originalDate==null) return null;
		return COMPACT_YEAR_FORMAT.format(originalDate);
	}
	
	public static String getStandardDateString(Date originalDate) {
		if(originalDate==null) return null;
		return STANDARD_DATE_FORMAT.format(originalDate);
	}
	
	public static String getStandardDatetimeString(Date originalDate) {
		if(originalDate==null) return null;
		return STANDARD_DATETIME_FORMAT.format(originalDate);
	}
	
	public static Date plus(Date originalDate, long addend) {
		while(addend>Integer.MAX_VALUE) {
			addend -= Integer.MAX_VALUE;
			originalDate = plus(originalDate, Integer.MAX_VALUE);
		}
		while(addend<Integer.MIN_VALUE) {
			addend -= Integer.MIN_VALUE;
			originalDate = plus(originalDate, Integer.MIN_VALUE);
		}
		return plus(originalDate, (int) addend);
	}
	
	public static Date plus(Date originalDate, int addend) {
		if(addend==0) {
			return originalDate;
		}
		Calendar originalCal = Calendar.getInstance();
		originalCal.setTime(originalDate);
		originalCal.add(Calendar.MILLISECOND, addend);
		return originalCal.getTime();
	}

	public static Date minus(Date originalDate, long subtrahend) {
		return plus(originalDate, -1L * subtrahend);
	}

	public static Date minus(Date originalDate, int subtrahend) {
		return plus(originalDate, -1 * subtrahend);
	}
	
	public static long minus(Date subtrahend) {
		return minus(subtrahend, false);
	}
	
	public static long minus(Date subtrahend, boolean ignoreMillis) {
		Calendar subtrahendCal = Calendar.getInstance();
		subtrahendCal.setTime(subtrahend);
		long difference = Calendar.getInstance().getTimeInMillis() - subtrahendCal.getTimeInMillis();
		if(ignoreMillis) {
			return difference/1000*1000;
		} else {
			return difference;
		}
	}
	
	public static long minus(Date minuend, Date subtrahend) {
		return minus(minuend, subtrahend, false);
	}
	
	public static long minus(Date minuend, Date subtrahend, boolean ignoreMillis) {
		Calendar minuendCal = Calendar.getInstance();
		minuendCal.setTime(minuend);
		Calendar subtrahendCal = Calendar.getInstance();
		subtrahendCal.setTime(subtrahend);
		long difference = minuendCal.getTimeInMillis() - subtrahendCal.getTimeInMillis();
		if(ignoreMillis) {
			return difference/1000*1000;
		} else {
			return difference;
		}
	}
	
	public static int gap(Date minuend, Date subtrahend) {
		long defference = minus(getDay(minuend), getDay(subtrahend), true);
		return (int) (defference / 1000 / 3600 / 24);
	}
	
	public static boolean isFirstDayOfMonth(Date date) {
		if(date==null) return false;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH)==1;
	}
	
	public static boolean isLastDayOfMonth(Date date) {
		if(date==null) return false;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH)==calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static boolean isEarlierThan(Date oneDate, Date otherDate) {
		return oneDate.before(otherDate);
	}
	
	public static boolean isNotEarlierThan(Date oneDate, Date otherDate) {
		return !oneDate.before(otherDate);
	}
	
	public static boolean isLaterThan(Date oneDate, Date otherDate) {
		return oneDate.after(otherDate);
	}
	
	public static boolean isNotLaterThan(Date oneDate, Date otherDate) {
		return !oneDate.after(otherDate);
	}
	
	/**
	 * 获取两个日期之间相差的月份
	 * @author zhupeng
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
	public static List<String> getMonthBetween(String minDate, String maxDate){
		ArrayList<String> result = new ArrayList<String>();
		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();
		try {
			min.setTime(STANDARD_DATEM_FORMAT.parse(minDate));
			min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
			max.setTime(STANDARD_DATEM_FORMAT.parse(maxDate));
			max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
			Calendar curr = min;
			while (curr.before(max)) {
				result.add(STANDARD_DATE_FORMAT.format(curr.getTime()));
				curr.add(Calendar.MONTH, 1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
//		System.out.println(24*3600*1000L);
//		System.out.println((long) (ONE_DAY_MILLIS * new Random().nextDouble()));
//		long l1 = 102L;
//		long l2 = 10L;
//		System.out.println(l1);
//		System.out.println(new Long(l1%l2));
//		for(int i=0; i<1000; i++)
//			System.out.println(getRandomTime(parseDate(2015, 5, 10, 20, 11, 11), parseDate(2015, 5, 21, 21, 0, 0)));
//		System.out.println(parseDate(0L));
//		System.out.println(parseDate(ONE_DAY_MILLIS));
//		Calendar c = Calendar.getInstance();
//		c.setTimeInMillis(0L);
//		System.out.println(c.getTime());
//		System.out.println(c.getTimeInMillis());
//		System.out.println(StringUtils.repeat("300", ",", 3));
//		System.out.println(StringUtils.leftPad("3000", 3, "0"));
//		System.out.println(generateId());
		
//		List<Date> dates = betweenDays(parseDate(2015, 5, 10), parseDate(2015, 5, 21), 5);
//		for(Date d : dates) {
//			System.out.println(getStandardDateString(d));
//		}
		
	}
	
}
