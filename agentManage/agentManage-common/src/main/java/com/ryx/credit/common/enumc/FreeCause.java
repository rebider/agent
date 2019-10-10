package com.ryx.credit.common.enumc;


/***
 * 冻结原因
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/10/10 10:34
 * @Param
 * @return
 **/
public enum FreeCause {

    QPDJ("QPDJ","欠票冻结"),
    RRDJ("RRDJ","分润冻结"),
    RWDJ("RWDJ","入网冻结"),
    RZDJ("RZDJ","认证冻结"),
    QTDJ("QTDJ","其他原因");

    public String code;

    public String msg;

    FreeCause(String c, String m){
        this.code = c;
        this.msg = m;
    }

    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static String getContentByValue(String value) {
        FreeCause[] freeCause = FreeCause.values();
        for (FreeCause cc : freeCause) {
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
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
}
