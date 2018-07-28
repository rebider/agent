package com.ryx.credit.common.util;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Zhang Lei
 * @Description: 机具SN号解析工具类
 * @Date: 10:35 2018/7/26
 */
public class SNUtils {

    public static Map<String, Object> analysisSn(String startSn, String endSn) {

        Map<String, Object> map = new HashMap<>();

        //最后一个字母位置
        int lastLetterIndex = getLastLetterIndex(startSn);

        //SN前缀
        String snPrefix = startSn.substring(0, lastLetterIndex + 1);
        System.out.println("snPrefix:" + snPrefix);

        //起始数字
        String startNmStr = startSn.substring(lastLetterIndex + 1, startSn.length());
        long startNm = Long.parseLong(startNmStr);

        //结束数字
        String endNmStr = endSn.substring(lastLetterIndex + 1, endSn.length());
        long endNm = Long.parseLong(endNmStr);

        //总个数
        long count = endNm - startNm + 1;

        //如果是0开始，表示固定长度
        int length = 0;
        if (startNmStr.startsWith("0")) {
            length = startNmStr.length();
        }

        //sn明细列表
        List<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String nowSn = "";
            if (length != 0) {
                //需要补0
                nowSn = snPrefix + (startNm + i);
            } else {
                nowSn = snPrefix + (startNm + i);
            }
            list.add(nowSn);
        }
        map.put("count", count);
        map.put("list", list);

        System.out.println("startNm:" + startNm);
        System.out.println("endNm:" + endNm);
        System.out.println("count:" + count);
        return map;
    }


    public String addZero(long num, int length) {
        int less = length - (num + "").length();
        for (int i = 0; i < less; i++) {

        }

        return null;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 获取最后一个字母位置
     * @Date: 11:01 2018/7/26
     */
    public static int getLastLetterIndex(String str) {
        List<String> digitList = new ArrayList<String>();
        Pattern p = Pattern.compile("[^(A-Za-z)]");
        Matcher m = p.matcher(str);
        String result = m.replaceAll("");
        for (int i = 0; i < result.length(); i++) {
            digitList.add(result.substring(i, i + 1));
        }
        String lastLetter = digitList.get(digitList.size() - 1);
        return str.lastIndexOf(lastLetter);
    }

    public static void main(String[] args) {
        String a = "we356gfrw23w3423";
        System.out.println(getLastLetterIndex(a));

        System.out.println(JSONObject.toJSONString(analysisSn("3545NW335N10004578", "3545NW335N10005642")));
    }

}
