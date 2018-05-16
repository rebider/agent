package com.ryx.credit.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonUtils {
	// 将数组转换成JSON
		public static String array2json(Object object) {
			JSONArray jsonArray = JSONArray.fromObject(object);
			return jsonArray.toString();
		}

		// 将JSON转换成数组,其中valueClz为数组中存放的对象的Class
		public static Object json2Array(String json, Class valueClz) {
			JSONArray jsonArray = JSONArray.fromObject(json);
			return JSONArray.toArray(jsonArray, valueClz);
		}

		// 将Collection转换成JSON
		public static String collection2json(Object object) {
			JSONArray jsonArray = JSONArray.fromObject(object);
			return jsonArray.toString();
		}

		// 将Map转换成JSON
		public static String map2json(Object object) {
			JSONObject jsonObject = JSONObject.fromObject(object);
			return jsonObject.toString();
		}
		// 将POJO转换成JSON
		public static String bean2json(Object object) {
			JSONObject jsonObject = JSONObject.fromObject(object);
			return jsonObject.toString();
		}

		// 将JSON转换成POJO,其中beanClz为POJO的Class
		public static Object json2Object(String json, Class beanClz) {
			return JSONObject.toBean(JSONObject.fromObject(json), beanClz);
		}
		
		// 将String转换成JSON
		public static String string2json(String key, String value) {
			JSONObject object = new JSONObject();
			object.put(key, value);
			return object.toString();
		}
		// 将JSON转换成String
		public static String json2String(String json, String key) {
			JSONObject jsonObject = JSONObject.fromObject(json);
			return jsonObject.get(key).toString();
		}

	public static Map parseJSON2Map(String jsonStr) {
		HashMap<String, String> data = new HashMap<String, String>();
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Iterator it = jsonObject.keys();
		while (it.hasNext())
		{
			String key = String.valueOf(it.next());
			String value = (String) jsonObject.get(key);
			data.put(key, value);
		}
		return data;
	}
}
