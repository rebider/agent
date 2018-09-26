package com.ryx.credit.common.enumc;

/**
 * @Author Lihl
 * @Date 2018/9/26
 * @Desc 代理商退出申请表的退款状态
 */
public enum RefundStatus {

    NOT_REFUND("1","未打款"),
    YES_REFUND("2","已打款");

    public String code;

    public String msg;

    RefundStatus(String c, String m){
        this.code = c;
        this.msg = m;
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
    public static String getRefundStatus(String value){
        RefundStatus[] status = RefundStatus.values();
        for(RefundStatus rs : status){
            if(rs.code.equals(value)){
                return rs.msg;
            }
        }
        return "";
    }

}
