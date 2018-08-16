package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.order.OReceiptOrder;
import com.ryx.credit.pojo.admin.order.OReceiptPro;

import java.util.List;

/**
 * Created by RYX on 2018/7/19.
 */
public class OReceiptOrderVo extends OReceiptOrder {

    private List<OReceiptPro> oReceiptPros;

    public List<OReceiptPro> getoReceiptPros() {
        return oReceiptPros;
    }

    public void setoReceiptPros(List<OReceiptPro> oReceiptPros) {
        this.oReceiptPros = oReceiptPros;
    }
}
