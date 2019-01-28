package com.ryx.credit.common.enumc;


/**
 * 代理商退出补缴类型
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/9/12 9:11
 */
public enum SuppType {

    D("1","代理商打款"),
    G("2","公司打款");

    public String code;

    public String msg;

    SuppType(String c, String m){
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
        SuppType[] fundType = SuppType.values();
        for(SuppType cc : fundType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

}
