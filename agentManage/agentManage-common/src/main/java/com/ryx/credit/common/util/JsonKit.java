package com.ryx.credit.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

public class JsonKit {
	
	public static <T> T fromJson(Object json, TypeReference<T> type) {
		if(json==null) return null;
//		return JSON.parseObject(json.toString(), type.getType());
		return JSON.parseObject(json.toString(), type);
	}
	
	public static <T> T fromJson(Object json, Class<T> clazz) {
		if(json==null) return null;
		return JSON.parseObject(json.toString(), clazz);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> fromJson(Object json) {
		if(json==null) return null;
		return fromJson(json.toString(), Map.class);
	}
	
	public static <T> List<T> fromArrayJson(Object json, Class<T> clazz) {
		if(json==null) return null;
		return JSON.parseArray(json.toString(), clazz);
	}
	
	public static String toJson(Object obj) {
		String str = JSON.toJSONStringWithDateFormat(obj, "yyyyMMddHHmmss");
//		String str = JSON.toJSONStringWithDateFormat(obj, JSON.DEFFAULT_DATE_FORMAT);
		// 将后缀为Date的参数值中的时分秒去掉 暂时没找到在JsonKit中直接做的方法
		String str2 = str.replaceAll("([Date|DATE])\":\"(\\d{8})000000", "$1\":\"$2");
		return str2;
//		return str.replaceAll("([Date|DATE])\":\"(\\d{4}-\\d{2}-\\d{2}) 00:00:00", "$1\":\"$2");
	}
	
}
