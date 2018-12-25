package com.ryx.credit.profit.service;

import java.util.Map;

/**
 * @Author: Zhang Lei
 * @Description: 欠票管理
 * @Date: 9:39 2018/12/20
 */
public interface IOwnInvoiceService {

    /**
     * @Author: Zhang Lei
     * @Description: 代理商欠票计算
     * @Date: 11:57 2018/12/22
     */
    void invoiceOwnComputer(Map<String,Object> params);
}
