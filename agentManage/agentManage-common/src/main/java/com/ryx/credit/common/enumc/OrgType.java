package com.ryx.credit.common.enumc;


/**
 * pos/手刷 机构类型
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/4/17 18:11
 */
public enum OrgType {

    ORG("01","普通机构（默认）"),
    STR("02","直签");

    public String code;

    public String msg;

    OrgType(String c, String m){
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
        OrgType[] fundType = OrgType.values();
        for(OrgType cc : fundType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    /**
     * 是否是直签
     * @param busType
     * @return
     */
    public static Boolean zQ(String busType){
        return !BusType.JG.key.equals(busType) && !BusType.BZYD.key.equals(busType);
    }

}
