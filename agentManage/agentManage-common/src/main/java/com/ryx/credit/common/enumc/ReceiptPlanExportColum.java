package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by RYX on 2018/8/30.
 */
public enum ReceiptPlanExportColum {

    //导出排单信息
    ReceiptPlanExportColum_column("PLAN_NUM,ORDER_ID,PRO_CODE,PRO_ID,PRO_NAME,AG_UNIQ_NUM,AG_NAME,PLATFORM_NAME,PRO_TYPE,ACTIVITY_NAME,SEND_NUM,PLAN_PRO_NUM,RESIDUE,PRO_COM_STRING,SEND_PRO_NUM,MODEL,ADDR_REALNAME,ADDR_MOBILE,NAME,CITY,DISTRICT,ADDR_DETAIL,REMARK,ZIP_CODE,EXPRESS_REMARK,C_TIME,C_DATE,h,g,a,b,c,d,e,f,isSend,APPROVAL_OPINION","排单导出字段"),
    ReceiptPlanExportColum_title("排单编号,订单号,商品编号,商品ID,商品名称,自编唯一码,代理商,平台,机具类型,活动名称,已排单总数量,排单数量,剩余未排单数量,订货厂家,已发货数量,机型,收货人姓名,收货人联系电话,省,市,区,详细地址,地址备注,邮编,快递备注,配货时间,创建时间,发货时间(2018-01-01),发货数量,物流公司,物流单号,起始SN序列号,结束SN序列号,起始SN位数,结束SN位数,是否联动,领导审批意见","排单导出标题"),

    //导出历史排单记录
    ReceiptPlan_ViewColumn("C_DATE,ORDER_ID,TIME,AG_UNIQ_NUM,AG_NAME,PLATFORM_NAME,PRO_TYPE,ACTIVITY_NAME,PRO_COM_STRING,MODEL,SEND_NUM,PLAN_PRO_NUM,RESIDUE,ADDR_REALNAME,ADDR_DETAIL,ADDR_MOBILE,C_TIME","排单导出字段"),
    ReceiptPlan_ViewTitle("排单日期,订单号,订单日期,自编唯一码,代理商,平台,机具类型,活动名称,订货厂家,机型,总数量,排单数量,剩余未排单数量,收货人,收货人地址,收货人联系方式,配货时间","排单导出标题"),

    //导出欠款记录
    ARREARAGE_Column("TM,AG_UNIQ_NUM,AG_NAME,BUS_NUM,PLATFORM_NAME,JJQK","欠款导出字段"),
    ARREARAGE_Title("月份,代理商唯一编码,代理商名称,业务平台编码,业务平台,机具欠款","欠款导出标题"),

    //导出活动调整
    RefundPriceDiff_Column("ID,AGENT_ID,AG_NAME,DEDUCT_AMT,BELOW_PAY_AMT,SHARE_DEDUCT_AMT,GATHER_TIME,GATHER_AMT,APPLY_REMARK,REVIEW_STATUS,ORDER_TYPE,S_TIME,U_TIME","活动调整导出字段"),
    RefundPriceDiff_Title("补差价编号,代理商唯一编码,代理商名称,扣除总金额,线下打款金额,分润抵扣金额,收款时间,收款金额,申请备注,审批状态,订单类型,申请时间,更新时间","活动调整导出标题"),

    //导出活动调整明细
    RefundPriceDiffDetail_Column("id,refundPriceDiffId,proName,changeCount,activityName,frontPrice,price,beginSn,endSn,pre_activity_activityName,pre_activity_act_code,pre_activity_bus_pro_code,pre_activity_price,orderType,oldOrgId,oldOrgName,newOrgId,newOrgName,oldSupdOrgId,oldSupdOrgName,newSupdOrgId,newSupdOrgName,uTime","活动调整明细导出字段"),
    RefundPriceDiffDetail_Title("编号,申请单号,商品名称,变更数量,活动名称,前价格,价格,开始SN,结束SN,原活动,原活动代码,原机具编号,原价格,订单类型,原机构编号,原机构名称,目标机构编号,目标机构名称,原机构直签类上级平台码,原机构直签类上级,目标机构直签类上级平台码,目标机构直签类上级,更新时间","活动调整明细导出标题");

    public String code;

    public String msg;

    public String []col;

    ReceiptPlanExportColum(String code, String msg) {
        this.code = code;
        this.msg = msg;
        col= code.split(",");
    }
}
