package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by RYX on 2018/8/30.
 */
public enum ReceiptPlanExportColum {

    ReceiptPlanExportColum_column("PLAN_NUM,ORDER_ID,PRO_CODE,PRO_ID,PRO_NAME,PRO_NUM,SEND_NUM,PRO_COM,PLAN_PRO_NUM,MODEL,ADDR_REALNAME,ADDR_MOBILE,NAME,CITY,DISTRICT,ADDR_DETAIL,REMARK,ZIP_CODE,EXPRESS_REMARK,h,g,a,b,c,d,e,f","排单导出字段"),

    ReceiptPlanExportColum_title("排单编号,订单编号,商品编号,商品ID,商品名称,商品数量,已排单数量,订货厂家,订货数量,机型,收货人姓名,收货人联系电话,省,市,区,详细地址,地址备注,邮编,快递备注,发货时间,发货数量,物流公司,物流单号,起始SN序列号,结束SN序列号,起始SN位数,结束SN位数","排单导出标题");

    public String code;

    public String msg;

    ReceiptPlanExportColum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
