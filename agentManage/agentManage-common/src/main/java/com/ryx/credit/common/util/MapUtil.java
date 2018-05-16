package com.ryx.credit.common.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedCaseInsensitiveMap;

/**
 * map工具类
 * <p/>
 * 根据key来对value进行不同类型的数据转换
 * <p/>
 */
public class MapUtil {
	
	private static Logger logger = LoggerFactory.getLogger(MapUtil.class);

	/**
	 * short类型转换
	 * @param map			map
	 * @param key 		    key
	 * @param defaultValue 类型转换失败后默认返回的值
	 * @return	转换成功后的数据 或 默认数据
	 */
	public static short getShort(Map map, Object key, short defaultValue) {
		Object value = map.get(key);
		if(value != null) {
			try {
				return Short.parseShort(value.toString());
			} catch (NumberFormatException e) {
				logger.error("转换错误：无法将" + value + "转换成Short", e);
			}
		}
		return defaultValue;
	}

	/**
	 * short类型转换
	 * @param map			map
	 * @param key 		    key
	 * @return	转换成功后的数据 或 0
	 */
	public static short getShort(Map map, Object key) {
		return getShort(map, key, (short) 0);
	}
	
	/**
	 * int 类型转换
	 * @param map			map
	 * @param key 		    key
	 * @param defaultValue 类型转换失败后默认返回的值
	 * @return	转换成功后的数据 或 默认数据
	 */
	public static int getInt(Map map, Object key, int defaultValue) {
		Object value = map.get(key);
		if(value != null) {
			try {
				return Integer.parseInt(value.toString());
			} catch (NumberFormatException e) {
				logger.error("转换错误：无法将" + value + "转换成Integer", e);
			}
		}
		return defaultValue;
	}
	
	/**
	 * int 类型转换
	 * @param map			map
	 * @param key 		    key
	 * @return	转换成功后的数据 或 0
	 */
	public static int getInt(Map map, Object key) {
		return getInt(map, key, 0);
	}
	
	/**
	 * long 类型转换
	 * @param map			map
	 * @param key 		    key
	 * @param defaultValue 类型转换失败后默认返回的值
	 * @return	转换成功后的数据 或 默认数据
	 */
	public static long getLong(Map map, Object key, long defaultValue) {
		Object value = map.get(key);
		if(value != null) {
			try {
				return Long.parseLong(value.toString());
			} catch (NumberFormatException e) {
				logger.error("转换错误：无法将" + value + "转换成BigDecimal", e);
			}
		}
		return defaultValue;
	}
	
	/**
	 * long 类型转换
	 * @param map			map
	 * @param key 		    key
	 * @return	转换成功后的数据 或 0.0
	 */
	public static long getLong(Map map, Object key) {
		return getLong(map, key, 0L);
	}
	
	/**
	 * float类型转换
	 * @param map			map
	 * @param key 		    key
	 * @param defaultValue 类型转换失败后默认返回的值
	 * @return	转换成功后的数据 或 默认数据
	 */
	public static float getFloat(Map map, Object key, float defaultValue) {
		Object value = map.get(key);
		if(value != null) {
			try {
				return Float.parseFloat(value.toString());
			} catch (NumberFormatException e) {
				logger.error("转换错误：无法将" + value + "转换成Float", e);
			}
		}
		return defaultValue;
	}
	
	/**
	 * float类型转换
	 * @param map			map
	 * @param key 		    key
	 * @return	转换成功后的数据 或 0.0
	 */
	public static float getFloat(Map map, Object key) {
		return getFloat(map, key, 0F);
	}
	
	/**
	 * double类型转换
	 * @param map			map
	 * @param key 		    key
	 * @param defaultValue 类型转换失败后默认返回的值
	 * @return	转换成功后的数据 或 默认数据
	 */
	public static double getDouble(Map map, Object key, double defaultValue) {
		Object value = map.get(key);
		if(value != null) {
			try {
				return Double.parseDouble(value.toString());
			} catch (NumberFormatException e) {
				logger.error("转换错误：无法将" + value + "转换成Double", e);
			}
		}
		return defaultValue;
	}
	
	/**
	 * double类型转换
	 * @param map			map
	 * @param key 		    key
	 * @param defaultValue 类型转换失败后默认返回的值
	 * @return	转换成功后的数据 或 默认数据
	 */
	public static double getDouble(Map map, Object key) {
		return getDouble(map, key, 0.0);
	}
	
	/**
	 * BigDecimal类型转换
	 * @param map			map
	 * @param key 		    key
	 * @param defaultValue 类型转换失败后默认返回的值
	 * @return	转换成功后的数据 或 默认数据
	 */
	public static BigDecimal getBigDecimal(Map map, Object key, BigDecimal defaultValue) {
		Object value = map.get(key);
		if(value != null) {
			try {
				return new BigDecimal(value.toString());
//				return BigDecimal.valueOf(Double.parseDouble(value.toString()));
			} catch (NumberFormatException e) {
				logger.error("转换错误：无法将" + value + "转换成BigDecimal", e);
			}
		}
		return defaultValue;
	}
	
	public static BigDecimal getBigDecimal(Map map, Object key, int scale, BigDecimal defaultValue) {
		Object value = map.get(key);
		if(value != null) {
			try {
				return new BigDecimal(value.toString()).setScale(scale, BigDecimal.ROUND_HALF_UP);
			} catch (NumberFormatException e) {
				logger.error("转换错误：无法将" + value + "转换成BigDecimal", e);
			}
		}
		return defaultValue.setScale(scale, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * BigDecimal类型转换
	 * @param map			map
	 * @param key 		    key
	 * @return	转换成功后的数据 或 BigDecimal.ZERO
	 */
	public static BigDecimal getBigDecimal(Map map, Object key) {
		return getBigDecimal(map, key, BigDecimal.ZERO);
	}

	public static BigDecimal getBigDecimal(Map map, Object key, int scale) {
		return getBigDecimal(map, key, scale, BigDecimal.ZERO);
	}
	
	/**
	 * boolean类型转换
	 * @param map			map
	 * @param key 		    key
	 * @param defaultValue 类型转换失败后默认返回的值
	 * @return	转换成功后的数据 或 默认数据
	 */
	public static boolean getBoolean(Map map, Object key, boolean defaultValue) {
		Object value = map.get(key);
		if(value != null) {
			return Boolean.parseBoolean(value.toString());
		}
		return defaultValue;
	}
	
	/**
	 * boolean类型转换
	 * @param map			map
	 * @param key 		    key
	 * @return	转换成功后的数据 或 false
	 */
	public static boolean getBoolean(Map map, Object key) {
		return getBoolean(map, key, false);
	}
	
	/**
	 * String 类型转换
	 * @param map			map
	 * @param key 		    key
	 * @param defaultValue 类型转换失败后默认返回的值
	 * @return	转换成功后的数据 或 默认数据
	 */
	public static String getString(Map map, Object key, String defaultValue) {
		Object value = map.get(key);
		if(value != null) {
			return value.toString();
		}
		return defaultValue;
	}
	
	/**
	 * String 类型转换
	 * @param map			map
	 * @param key 		    key
	 * @return	转换成功后的数据 或 空字符串
	 */
	public static String getString(Map map, Object key) {
		return getString(map, key, "");
	}
	
	/**
	 * 泛型类型转换
	 * @param map			map
	 * @param key 		    key
	 * @param defaultValue 类型转换失败后默认返回的值
	 * @return	转换成功后的数据 或 默认数据
	 */
	public static <T> T get(Map map, Object key, T defaultValue) {
		Object value = map.get(key);
		if(value != null) {
			try {
				return (T) value;
			} catch (ClassCastException e) {
				logger.error("转换错误：无法将" + value + "转换成" + defaultValue != null ? defaultValue.getClass().getName() : "null", e);
			}
		}
		return defaultValue;
	}
	
	public static Map filter(Map<String, ?> inputMap, String... filterKeys) {
		Map<String, Object> outputMap = null;
		if (inputMap instanceof LinkedCaseInsensitiveMap) outputMap = new LinkedCaseInsensitiveMap<Object>();
		else if (inputMap instanceof LinkedHashMap) outputMap = new LinkedHashMap<String, Object>();
		else if (inputMap instanceof TreeMap) outputMap = new TreeMap<String, Object>();
		else outputMap = new HashMap<String, Object>();
		int filterArrayLength = 0;
		// 如果准备加入的元素是空, 则跳过
		if (inputMap==null || inputMap.isEmpty() || filterKeys==null || (filterArrayLength=filterKeys.length)==0) return outputMap;
		String[] tempKeys = Arrays.copyOf(filterKeys, filterArrayLength);
		for (Entry<String, ?> inputEntry : inputMap.entrySet()) {
			boolean shouldFilter = false;
			String inputKey = inputEntry.getKey();
			for (int j=0; j<filterArrayLength; j++) {
				String filterKey = tempKeys[j];
				if (filterKey==null ? inputKey==null : filterKey.equalsIgnoreCase(inputKey)) {
					shouldFilter = true;
					break;
				}
			}
			if(!shouldFilter) {
				Object inputValue = inputEntry.getValue();
				outputMap.put(inputKey, inputValue);
			}
		}
		return outputMap;
	}
	
	public static Map remain(Map<String, ?> inputMap, String... remainKeys) {
		Map<String, Object> outputMap = null;
		if (inputMap instanceof LinkedCaseInsensitiveMap) outputMap = new LinkedCaseInsensitiveMap<Object>();
		else if (inputMap instanceof LinkedHashMap) outputMap = new LinkedHashMap<String, Object>();
		else if (inputMap instanceof TreeMap) outputMap = new TreeMap<String, Object>();
		else outputMap = new HashMap<String, Object>();
		// 如果准备加入的元素是空, 则跳过
		if (inputMap==null || inputMap.isEmpty() || remainKeys==null || remainKeys.length==0) return outputMap;
		for (String remainKey : remainKeys) {
			if (inputMap.containsKey(remainKey)) {
				outputMap.put(remainKey, inputMap.get(remainKey));
			}
		}
		return outputMap;
	}
	
	public static Map rename(Map<String, ?> inputMap, String... renamePairs) {
		Map<String, Object> outputMap = null;
		if (inputMap instanceof LinkedCaseInsensitiveMap) outputMap = new LinkedCaseInsensitiveMap<Object>();
		else if (inputMap instanceof LinkedHashMap) outputMap = new LinkedHashMap<String, Object>();
		else if (inputMap instanceof TreeMap) outputMap = new TreeMap<String, Object>();
		else outputMap = new HashMap<String, Object>();
		int renameArrayLength = renamePairs.length;
		if (renameArrayLength==0) return outputMap;
		for (String renamePair : renamePairs) {
			if (MapParam.shouldParse(renamePair)) { // 可被拆分
				MapParam param = new MapParam(renamePair);
				if(outputMap.get(param.getNew())==null) { // 如果之前没有放入这个key
					if(inputMap.containsKey(param.getOld())) outputMap.put(param.getNew(), inputMap.get(param.getOld()));
					else if(param.getDefault()!=null) outputMap.put(param.getNew(), param.getDefault());
				}
			} else {
				if(outputMap.get(renamePair)==null) { // 如果之前没有放入这个key
					if(inputMap.containsKey(renamePair)) outputMap.put(renamePair, inputMap.get(renamePair));
				}
			}
		}
		return outputMap;
	}
	
	public static int compareTo(Map<String, ?> oneMap, Map<String, ?> otherMap) {
		int oneMapSize = 0;
		int otherMapSize = 0;
		// 都是null的话返回相等, 有一个是null不相等
		if(oneMap==null && otherMap==null) return 0;
		else if(oneMap==null) return -1*otherMap.size();
		else if(otherMap==null) return oneMap.size();
		
		oneMapSize = oneMap.size();
		otherMapSize = otherMap.size();
		// 元素个数不同不相等, 否则元素相减
		if(oneMapSize!=otherMapSize) return oneMapSize-otherMapSize;
		else {
			Set<String> oneMapKeys = new LinkedHashSet<String>();
			Set<String> otherMapKeys = new LinkedHashSet<String>();
			for(String key : oneMap.keySet()) oneMapKeys.add(key);
			for(String key : otherMap.keySet()) otherMapKeys.add(key);
			
			oneMapKeys.removeAll(otherMapKeys);
			if(oneMapKeys.size()>0) return oneMapKeys.size();
			else {
				oneMapKeys.clear();
				for(String key : oneMap.keySet()) oneMapKeys.add(key);
				
				otherMapKeys.removeAll(oneMapKeys);
				if(otherMapKeys.size()>0) return -1*otherMapKeys.size();
			}
		}
		return 0;
	}
	
	public static void main(String[] args) {
		Map a = new HashMap();
		Map b = new HashMap();
		a.put("a", "1");
		a.put("b", "1");
		b.put("a", "1");
		b.put("b", "2");
		System.out.println(compareTo(a, b));
		System.out.println(a);
		System.out.println(b);
		/*
		int b = 1;
//		a.put("merch_id", 1);
		a.put("amt_sale", BigDecimal.ONE);
		Map result = rename(a, "merch_id.cust_id", "amt_sale", "qty_sale:"+b);
		System.out.println(result);
		*/
	}
}


class MapParam {
	
	private String oldKey;
	private String newKey;
	private String defaultValue;
	
	MapParam(Object param) {
		if(param!=null) {
			String paramEntry = param.toString();
			int index = paramEntry.lastIndexOf(":");
			int stringLength = paramEntry.length();
			if(index > -1 && index < stringLength) {
				newKey = paramEntry.substring(0, index);
				defaultValue = paramEntry.substring(index+1);
			}
			if(newKey!=null) {
				index = newKey.indexOf(".");
				stringLength = newKey.length();
				if(index > -1 && index < stringLength) {
					oldKey = newKey.substring(0, index);
					newKey = newKey.substring(index+1);
				} else {
					oldKey = newKey;
				}
			} else {
				index = paramEntry.indexOf(".");
				stringLength = paramEntry.length();
				if(index > -1 && index < stringLength) {
					oldKey = paramEntry.substring(0, index);
					newKey = paramEntry.substring(index+1);
				} else {
					oldKey = paramEntry;
					newKey = paramEntry;
				}
			}
		}
	}
	
	String getOld() {
		return this.oldKey;
	}
	
	String getNew() {
		return this.newKey;
	}
	
	String getDefault() {
		return this.defaultValue;
	}
	
	public static boolean shouldParse(Object paramKey) {
		if(paramKey!=null) {
			String param = paramKey.toString();
			return param.indexOf(".")+param.lastIndexOf(":") > -2;
		}
		return false;
	}
	
}
