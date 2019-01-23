package com.ryx.credit.common.common;

import com.ryx.credit.common.util.FastMap;

import java.util.Map;

/**
 * 作者：cx
 * 时间：2018/9/12
 * 描述：字段翻译
 */
public class FieldTranslate {

    public static Map<String,String> field;

    static {
        //代理商基本信息
        field = FastMap.fastMap("id","编号")
                .putKeyV("agUniqNum","代理商唯一编号")
                .putKeyV("agName","代理商名称")
                .putKeyV("agNature","公司性质")
                .putKeyV("agCapital","注册资本(元)")
                .putKeyV("agBusLic","营业执照")
                .putKeyV("agBusLicb","营业执照起始时间")
                .putKeyV("agBusLice","营业执照结束时间")
                .putKeyV("agLegal","法人姓名")
                .putKeyV("agLegalCertype","法人证件类型")
                .putKeyV("agLegalCernum","法人证件号码")
                .putKeyV("agLegalMobile","法人联系电话")
                .putKeyV("agHead","负责人")
                .putKeyV("agHeadMobile","负责人电话")
                .putKeyV("agRegAdd","注册地址")
                .putKeyV("agBusScope","营业范围")
                .putKeyV("cloTaxPoint","税点")
                .putKeyV("agStatus","代理商状态")
                .putKeyV("agDocPro","业务对接省区(分公司)")
                .putKeyV("agDocDistrict","业务对接大区(分公司)")
                .putKeyV("agRemark","备注")
                .putKeyV("cIncomTime","入网时间")
                .putKeyV("cTime","创建时间")
                .putKeyV("cUtime","更新时间")
                .putKeyV("cIncomStatus","入网状态")
                .putKeyV("agZbh","财务自编号")
                .putKeyV("caStatus","工商认证状态")


                //业务信息
                .putKeyV("agentId","代理商ID")
                .putKeyV("busNum","业务平台号(平台机构编号)")
                .putKeyV("busPlatform","业务平台")
                .putKeyV("busType","业务类型(一个)")
                .putKeyV("busParent","所属上级代理")
                .putKeyV("busRiskParent","风险上级")
                .putKeyV("busActivationParent","激活返现上级")
                .putKeyV("busRegion","业务范围")
                .putKeyV("busSentDirectly","是否直发")
                .putKeyV("busDirectCashback","是否直接返现")
                .putKeyV("busIndeAss","是否独立考核")
                .putKeyV("busContact","业务联系人")
                .putKeyV("busContactMobile","业务联系电话")
                .putKeyV("busContactEmail","分润对接邮箱")
                .putKeyV("busContactPerson","业务对接人")
                .putKeyV("busRiskEmail","投诉及风险风控对接邮箱")
                .putKeyV("cloTaxPoint","税点")
                .putKeyV("cloInvoice","是否开具分润发票")
                .putKeyV("cloReceipt","是否要求收据")
                .putKeyV("cloPayCompany","打款公司")
                .putKeyV("cloAgentColinfo","收款账户")
                .putKeyV("busStatus","业务状态")
                .putKeyV("cloReviewStatus","审核状态")
                .putKeyV("busUseOrgan","使用范围")
                .putKeyV("busScope","业务范围")
                .putKeyV("dredgeS0","是否开通s0")
                .putKeyV("busLoginNum","业务系统登录账号")

                //缴款信息
                .putKeyV("cType","资金类型")
                .putKeyV("cAmount","资金金额")

                //合同信息
                .putKeyV("contNum","合同编号")
                .putKeyV("contType","合同类型")
                .putKeyV("contDate","合同日期")
                .putKeyV("contEndDate","合同结束日")
                .putKeyV("appendAgree","是否附加协议")

                //收款账户
                .putKeyV("cloType","收款账户类型")
                .putKeyV("cloRealname","收款账户名")
                .putKeyV("cloBank","收款开户行")
                .putKeyV("cloBankBranch","收款开户行支行")
                .putKeyV("cloBankAccount","收款账号")
                .putKeyV("cloReviewStatus","审核状态")
                .putKeyV("branchLineNum","支行联行号")
                .putKeyV("allLineNum","总行联行号")
                .putKeyV("bankRegion","开户行地区")
                .putKeyV("cloTaxPoint","税点")
                .putKeyV("cloInvoice","是否开具分润发票")
                .putKeyV("attachmentList","附件列表")

                .putKeyV("status","记录状态")
                .putKeyV("varsion","版本号")
                .putKeyV("cUser","创建用户")


        ;

    }

    public static String getNameByField(String fieldIn){
        return field.get(fieldIn);
    }

}
