package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by RYX on 2018/8/30.
 */
public enum ReceiptPlanExportColum {

    ReceiptPlanExportColum_column("PLAN_NUM,ORDER_ID,AG_UNIQ_NUM,AG_NAME,PRO_NAME,PLATFORM_NAME,PRO_TYPE,ACTIVITY_NAME,PRO_NUM,PRO_COM,MODEL,SEND_NUM,PLAN_PRO_NUM,RESIDUE,ADDR_REALNAME,ADDR_MOBILE,NAME,CITY,DISTRICT,ADDR_DETAIL,C_DATE","排单导出字段"),

    ReceiptPlanExportColum_title("排单编号,订单编号,唯一码,商品名称,代理商,平台,机具类型,活动,订货数量,订货厂家,机型,已排单总数量,排单数量,剩余未排单数量,收货人姓名, 收货人联系电话,省,市,区,详细地址,创建时间","排单导出标题");

    public String code;

    public String msg;

    public String []col;

    ReceiptPlanExportColum(String code, String msg) {
        this.code = code;
        this.msg = msg;
        col= code.split(",");
    }
}
