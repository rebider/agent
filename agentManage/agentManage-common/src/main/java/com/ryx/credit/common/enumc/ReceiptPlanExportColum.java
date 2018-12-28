package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by RYX on 2018/8/30.
 */
public enum ReceiptPlanExportColum {

    ReceiptPlanExportColum_column("PLAN_NUM,ORDER_ID,PRO_CODE,PRO_ID,PRO_NAME,AG_UNIQ_NUM,AG_NAME,PLATFORM_NAME,PRO_TYPE,ACTIVITY_NAME,SEND_NUM,PLAN_PRO_NUM,RESIDUE,PRO_COM_STRING,SEND_PRO_NUM,MODEL,ADDR_REALNAME,ADDR_MOBILE,NAME,CITY,DISTRICT,ADDR_DETAIL,REMARK,ZIP_CODE,EXPRESS_REMARK,C_DATE,h,g,a,b,c,d,e,f","排单导出字段"),

    ReceiptPlanExportColum_title("排单编号,订单编号,商品编号,商品ID,商品名称,唯一码,代理商,平台,机具类型,活动,已排单总数量,排单数量,剩余未排单数量,订货厂家,已发货数量,机型,收货人姓名,收货人联系电话,省,市,区,详细地址,地址备注,邮编,快递备注,创建时间,发货时间(2018-01-01),发货数量,物流公司,物流单号,起始SN序列号,结束SN序列号,起始SN位数,结束SN位数","排单导出标题");

    public String code;

    public String msg;

    public String []col;

    ReceiptPlanExportColum(String code, String msg) {
        this.code = code;
        this.msg = msg;
        col= code.split(",");
    }
}
