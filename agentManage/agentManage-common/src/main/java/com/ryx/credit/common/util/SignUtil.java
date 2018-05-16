package com.ryx.credit.common.util;

import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class SignUtil {

    /**
     * @Description: 签名算法
     * @param map
     * @param key
     * @return
     * @author wangqi
     * @date 2015年6月10日 下午2:46:16   
     */
    public static String getSign(Map<String,String> map,String key){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,String> entry:map.entrySet()){
            if(StringUtils.isNotBlank(entry.getValue())&& !"sign".equals(entry.getKey())){
                list.add(entry.getKey() + "=" + entry.getValue().trim() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        sb.append(key);
        String upperCase = Md5Tools.MD5(sb.toString()).toUpperCase();
        return upperCase;
    }
    public static String getSignStr(Map<String,String> map,String key){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,String> entry:map.entrySet()){
            if(StringUtils.isNotBlank(entry.getValue())&& !"sign".equals(entry.getKey())){
                list.add(entry.getKey() + "=" + entry.getValue().trim() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String upperCase = Md5Tools.MD5(sb.toString()+key).toUpperCase();
        sb.append("sign="+upperCase);
        return sb.toString();
    }

    public static void main(String[] args) throws IllegalAccessException {
        Map<String,String> map = new HashMap<String,String>();
        map.put("dealNo", "zm53345678911242227");
        map.put("code", "zm_money");
        map.put("totalCount", "3.00");
        map.put("productName", "蓝马庄");
        map.put("userId", "0987654321");
        map.put("sign", "EC93DB73F3E393F4A2685863634EA779");
        String key="74c3872f01fb43d9bf6363ad6ecbf3a5";
        String sign = getSignStr(map, key);
        String sign2 = getSign(map, key);

        System.out.println(sign+"   "+sign2);
        t();

    }
    public static void t(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("orderId", "YSPf0b2000003800-2");
        map.put("status", "success");
        map.put("userId", "13ad335920e455e0e053268d640abf0b");

        String sign = getSignStr(map, "74c3872f01fb43d9bf6363ad6ecbf3a5");

        System.out.println(Md5Tools.MD5("amt=1000&code=889948&custMobile=13111111112&merchId=13111111112&orderId=2111111111&parentProductId=61&payAccount=6212260200077617724&period=1&platform=shandong1&repayAccount=6212260200077617724&subProductId=606004&gole_pool_wq").toUpperCase());
        System.out.println(Md5Tools.MD5("money=1000000&name=wq&platform=shandong1&gole_pool_wq").toUpperCase());
        System.out.println(Md5Tools.MD5("amount=16000&bankNo=6212260200077617724&contractId=CO201706120000006932&loanId=LO201707310000000868&mobile=13111111112&orderId=2222212222&payType=DK&planId=AM201708020000001406&platform=shandong1&type=R&gole_pool_wq").toUpperCase());
        System.out.println(Md5Tools.MD5("platform=shandong1&userName=15001050853&gole_pool_wq").toUpperCase());
        System.out.println(Md5Tools.MD5("mobile=13111111112&platform=shandong1&gole_pool_wq").toUpperCase());
        System.out.println(Md5Tools.MD5("platform=shandong&tobaccoId=123111111&userName=13111111113&gole_pool_wq").toUpperCase());
        System.out.println(Md5Tools.MD5("bankNo=6212260200077617724&identity=140402198510253211&mobile=13111111112&platform=shangdong1&userName=王琪&gole_pool_wq").toUpperCase());
        System.out.println(Md5Tools.MD5("code=11111&mobile=13111111112&gole_pool_wq").toUpperCase());
        System.out.println(Md5Tools.MD5("identity=140402198510253211&mobile=13111111112&platform=shangdong1&userName=王琪&gole_pool_wq").toUpperCase());
        System.out.println(JSONObject.parseObject("{'user_id':'13111111112','lice_id':'123111111'}",Map.class));
        System.out.println(Md5Tools.MD5("id=271&name=wq&gole_pool_wq").toUpperCase());
        System.out.println(Md5Tools.MD5("bank=gongshang-工商银行-102&name=wq&type=add&gole_pool_wq").toUpperCase());
        System.out.println("gongshang-工商银行-102".split("\\-")[1]);
        Map map1 = new HashMap();
        map1.put("execCode", JSONArray.fromArray(new String[]{"00"}));
        System.out.println(String.valueOf(JSONArray.fromObject(map1.get("execCode")).get(0)));
        String aa ="abc";
        String cc ="abc";
        String e ="ab";
        String f ="c";
        String bb = new String("a")+"bc";
        String g = e+f;
        System.out.println(aa==g);
        System.out.println(aa.hashCode());
        System.out.println(g.hashCode());
        String s = new String("abc");
        String s1 = "abc";
        String s2 = new String("abc");
        System.out.println(s == s1.intern());
        System.out.println(s == s2.intern());
        System.out.println(s1 == s2.intern());
    }

}
