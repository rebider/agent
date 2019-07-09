package com.ryx.credit.profit.service;


import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.InvoiceApply;

import java.util.List;
import java.util.Map;

/**
 * 代理商发票明细 维护
 * @Author chenqiutian
 * @Date 2019/2/18
 */
public interface IAgeInvoiceApplyService {

    PageInfo queryInvoiceDetail(InvoiceApply invoiceApply, Page page,Map<String, Object> department);

    void deleteById(String id);

    void updateExpressInfo(InvoiceApply invoiceApply);

    InvoiceApply getInvoiceApplyById(String id);

    void saveInvoiceApply(List<Map<String,Object>> list,String agentId) throws MessageException;

    //返回所有终审失败的发票信息
    List<Map<String,String>> finalCheckInvoice(List<Map<String,Object>> list);

}
