package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.InvoiceApply;

import java.util.List;

/**
 * 欠票线上维护
 * @Author chenqiutian
 * @Date 2019/2/18
 */
public interface IAgeInvoiceApplyService {
    /**
     * 代理商获取发票申请明细
     * @Author chenqiutian
     * @param invoiceApply
     * @param page
     * @return
     */
    PageInfo agentGetInvoiceApplyList(InvoiceApply invoiceApply, Page page);

    /**
     * 保存代理商发票信息
     */
    void insertInvoiceApplyInfo(InvoiceApply invoiceApply);

    /**
     * 获取财务审核数据
     */
    PageInfo getList(Page page,InvoiceApply invoiceApply);

    /**
     * 财务提交审核结果
     */
    void commitSHResult(InvoiceApply invoiceApply) throws MessageException ;

    /**
     * 根据id获得数据
     */
    InvoiceApply getInvoiceApplyById(String id);

    /**
     * 代理商批量导入发票信息
     */
    void volumeImportData(List<List<Object>> list,String agentId,String agentName) throws MessageException;

    /**
     * 根据发票号得到数据
     */
    InvoiceApply getInvoiceApplyByInvoiceNumber(String invoiceNumber,String agentId);


    /**
     *批量上传发票
     */
    void insertInvoiceApply(InvoiceApply invoiceApply);

}
