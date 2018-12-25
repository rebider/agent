package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by RYX on 2018/8/30.
 */
public enum ReceiptPlanExportColum {

    ReceiptPlanExportColum_column("C_DATE,ORDER_ID,TIME,AG_UNIQ_NUM,AG_NAME,PLATFORM_NAME,PRO_TYPE,ACTIVITY_NAME,PRO_COM_STRING,MODEL,SEND_NUM,PLAN_PRO_NUM,RESIDUE,ADDR_REALNAME,ADDR_DETAIL,ADDR_MOBILE","排单导出字段"),

    ReceiptPlanExportColum_title("排单日期,订单编号,订单日期,唯一码,代理商,平台,机具类型,活动,订货厂家,机型,总数量,排单数量,剩余未排单数量,收货人,收货人地址,收货人联系方式","排单导出标题");

    public String code;

    public String msg;

    public String []col;

    ReceiptPlanExportColum(String code, String msg) {
        this.code = code;
        this.msg = msg;
        col= code.split(",");
    }
}
