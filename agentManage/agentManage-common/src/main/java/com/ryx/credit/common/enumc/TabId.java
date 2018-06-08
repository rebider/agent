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
    data_history("DH%s%015d");

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
