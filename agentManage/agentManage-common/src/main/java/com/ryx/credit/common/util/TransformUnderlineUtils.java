package com.ryx.credit.common.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bean 》 Map
 * 驼峰规范属性转下划线
 * 支持递归转换，如属性为JavaBean时
 */
public class TransformUnderlineUtils {

    private static String compile = "[A-Z]";
    private TransformUnderlineUtils(){}

    public static<T> Map transform(T object){
        if(object == null){
            return null;
        }

        Map<String, Object> map = new HashMap<>();
        // 获取实体类所有属性，返回Field数组
        Field[] fields = object.getClass().getDeclaredFields();

        try {
            for (int i = 0 ; i < fields.length; i++){
                // 属性名称
                String fieldName = fields[i].getName();
                // 转换驼峰形式属性名称成下划线风格，获取map的key 例：fieldName 》 field_name
                String transformFieldName =  TransformUnderlineUtils.getTransformFieldName(fieldName);
                // map 的 value ，属性的值
                Object FieldValue = null;

                // 将属性的首字符大写，方便构造get，set方法
                String name =  fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                // 获取属性的类型
                String type = fields[i].getGenericType().toString();
                Method m = object.getClass().getMethod("get" + name);

                switch (type){
                    // 如果有需要,可以仿照下面继续进行扩充,再增加对其它类型的判断
                    case "class java.lang.String":
                        // 调用getter方法获取属性值
                        FieldValue =  m.invoke(object);
                        break;
                    case "class java.lang.Boolean":
                        // 调用getter方法获取属性值
                        FieldValue =  m.invoke(object);
                        break;
                    case "class java.util.Date":
                        // 调用getter方法获取属性值
                        FieldValue =  m.invoke(object);
                        break;
                    case "class java.lang.Integer":
                        // 调用getter方法获取属性值
                        FieldValue =  m.invoke(object);
                        break;
                    case "class java.lang.Long":
                        // 调用getter方法获取属性值
                        FieldValue =  m.invoke(object);
                        break;
                    case "class java.math.BigDecimal":
                        // 调用getter方法获取属性值
                        FieldValue =  m.invoke(object);
                        break;
                    default:
                        // 属性类型为bean,则递归
                        Object obj =  m.invoke(object);
                        FieldValue = TransformUnderlineUtils.transform(obj);
                }
                map.put(transformFieldName,FieldValue);
            }
        }catch (Exception e){
            // 系统异常
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 转换风格 驼峰转下划线
     * @param fieldName 属性名称
     * @return
     */
    private static String getTransformFieldName(String fieldName) {
        Pattern humpPattern = Pattern.compile(compile);
        Matcher matcher = humpPattern.matcher(fieldName);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString().toUpperCase();
    }


}