package com.ryx.credit.common.enumc;


/**
 * 申请推出业务平台
 * Created by liudh on 2018/9/58.
 */
public enum QuitPlatform {

    POS("1","POS"),
    MPOS("2","手刷"),
    POSANDMPOS("3","POS+手刷");


    public String key;
    public String msg;

    QuitPlatform(String k, String s){
        key = k;
        msg = s;
    }

    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public String getValue() {
        return this.key;
    }
    /**
     * 取得缓存内容
     * @return 缓存内容
     */
    public String getContent() {
        return this.msg;
    }


    public static String getContentByValue(String value){
        QuitPlatform[] busType = QuitPlatform.values();
        for(QuitPlatform bt : busType){
            if(bt.key.equals(value)){
                return bt.msg;
            }
        }
        return "";
    }
}
