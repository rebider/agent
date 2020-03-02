package com.ryx.credit.common.enumc;

import java.util.HashMap;
import java.util.Map;

/**
 * 授权中心返回码
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/4/17 18:11
 */
public enum AuthCode {

    SUCCESS("0000","成功"),
    FAIL("1000","失败"),
    EXPIRED("1001","授权码已过期"),
    LACK_SYSTEM_CODE("1002","缺少系统编码"),
    UNKNOWN_SYSTEM_CODE("1003","未知的系统编码"),
    AUTH_CODE_VALID("1004","授权码尚未失效，请勿重复请求"),
    AUTH_CODE_FAIL("1005","获取授权码失败"),
    LACK_IP("1006","缺少请求ip地址"),
    LACK_AUTH("1007","缺少授权码"),
    NO_AUTH("1008","授权码不存在"),
    LACK_LOGIN_NAME("1010","缺少登陆名"),
    LACK_PASS_WORD("1011","缺少密码"),
    LACK_BUS_INFO("1012","缺少业务信息"),
    LACK_TOKEN("1013","缺少令牌"),
    NO_TOKEN("1014","令牌不存在"),
    TOKEN_PAST("1015","令牌已过期"),
    NO_CROSS_PLATFORM("1016","不允许跨平台查询"),
    ACCOUNT_LOCK("1017","账号已锁定"),
    PASS_WORD_ERROR("1018","账号或密码错误"),
    NO_BUS_INFO("1019","没有可用的业务"),
    PLATFORM_NULL("1020","平台不能为空"),
    PASS_KEY("1021","密钥配置错误");

    public String code;

    public String msg;

    AuthCode(String c, String m){
        this.code=c;
        this.msg =m;
    }

    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public String getValue() {
        return this.code;
    }
    /**
     * 取得缓存内容
     * @return 缓存内容
     */
    public String getContent() {
        return this.msg;
    }

    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static String getContentByValue(String value){
        AuthCode[] authCode = AuthCode.values();
        for(AuthCode cc : authCode){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    public static String getValueByContent(String content){
        AuthCode[] authCode = AuthCode.values();
        for(AuthCode cc : authCode){
            if(cc.msg.equals(content)){
                return cc.code;
            }
        }
        return "";
    }

    public static Map<String,Object> getContentMap(){
        Map<String,Object> resultMap = new HashMap<>();
        AuthCode[] fundType = AuthCode.values();
        for(AuthCode cc : fundType){
            resultMap.put(cc.code,cc.msg);
        }
        return resultMap;
    }

}
