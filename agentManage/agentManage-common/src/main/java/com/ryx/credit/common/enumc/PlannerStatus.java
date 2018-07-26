package com.ryx.credit.common.enumc;

/**
 * 排单状态
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/4/17 18:11
 */
public enum PlannerStatus {

    YesPlanner("1","已排单"),
    YesDeliver("2","已发货"),
    NoDeliver("3","未发货");

    public String code;

    public String msg;

    PlannerStatus(String c, String m){
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
        PlannerStatus[] status = PlannerStatus.values();
        for(PlannerStatus cc : status){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

}
