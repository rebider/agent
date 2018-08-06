package com.ryx.credit.common.enumc;

/**
 * 数据表
 * Created by cx on 2018/5/22.
 */
public enum TabId {

    a_agent("AG%s%015d"),
    a_agent_colinfo("AC%s%015d"),
    a_agent_contract("AO%s%015d"),
    a_agent_platformsyn("AP%s%015d"),
    a_ass_protocol("AR%s%015d"),
    a_attachment_rel("AE%s%015d"),
    a_attachment("AT%s%015d"),
    a_capital("CA%s%015d"),
    a_pay_comp("PC%s%015d"),
    a_platform("PL%s%015d"),
    data_change_request("DC%s%015d"),
    template_agreement("TA%s%015d"),
    a_agent_colinfo_rel("AR%s%015d"),
    d_InterfaceRequest_Record("IR%s%015d"),
    a_agent_businfo("AB%s%015d"),
    a_import_agent("AI%s%015d"),
    data_history("DH%s%015d"),
    o_address("OA%s%015d"),
    o_activity("OAC%s%014d"),
    o_sub_order_activity("OS%s%015d"),
    o_sub_order("OSU%s%014d"),
    o_order("OO%s%015d"),
    o_receipt_pro("OR%s%015d"),
    o_receipt_order("ORE%s%014d"),
    o_payment("OPA%s%014d"),
    o_payment_detail("OPD%s%014d"),
    o_Supplement("OS%s%014d"),//补款表
    o_product("OP%s%015d"),
    o_logistics("LG%s%014d"),//发货物流
    o_logistics_detail("LD%s%014d"),
    o_Refund_price_diff("ORPD%s%015d"),
    o_Refund_price_diff_detail("ORPDD%s%015d"),
    o_return_order("RO%s%015d"),
    o_return_order_detail("ROD%s%014d"),
    o_return_order_rel("ROR%s%014d"),
    o_deduct_capital("DC%s%014d"),
    o_refund_agent("RF%s%014d"),
    o_account_adjust("AJ%s%014d"),
    o_receipt_plan("ORP%s%015d"),
    o_account_adjust_detail("AJD%s%014d"),
    P_ORGAN_TRAN_MONTH("P_ORGAN_TRAN_MONTH%s%015d"),
    P_IMPORT_DEDUCTION_DETAIL("P_IMPORT_DEDUCTION_DETAIL%s%015d"),
    P_STAGING_DETAIL("PSD%s%015d"),
    P_STAGING("P_STAGING%s%015d"),
    P_ORGAN_TRAN_MONTH_DETAIL("POTMD%s%015d"),
    P_DEDUCTION("P_DEDUCTION%s%015d"),
    P_SETTLE_ERR_LS("P_SETTLE_ERR_LS%s%015d"),
    P_PROFIT_DETAIL("PDE%s%015d"),
    P_PROFIT_M("PRM%s%015d"),
    p_profit_unfreeze("UNF%s%015d"),
    p_profit_adjust("PPA%s%015d");

    TabId(String thePatt){
           this.patt = thePatt;
    }
    public String patt;

    public String getPatt() {
        return patt;
    }

    public void setPatt(String patt) {
        this.patt = patt;
    }
}
