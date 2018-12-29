package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.InvoiceDetail;

import java.util.List;
import java.util.Map;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.InvoiceDetail;

import java.util.List;

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

    /**
     * 获取数据列表
     */
    PageInfo getInvoiceDetailList(Page page, String agentId, String agentName, String concludeChild, String dateStart, String dateEnd);

    /**
     * 向欠票导入表中导入数据
     */
    void exportData(List<List<Object>> datas, String loginName);

    /**
     * 根据id获取代理商信息
     * @param id
     * @return
     */
    InvoiceDetail getInvoiceById(String id);

    /**
     * 设置调整金额
     * @param invoiceDetail
     * @return
     */
    void setAdjustAMT(InvoiceDetail invoiceDetail);

    /**
     * 导出数据
     * @param agentId
     * @param agentName
     * @param concludeChild
     * @param dateStart
     * @param dateEnd
     * @return
     */
    List<InvoiceDetail> exportInvoiceData(String agentId, String agentName, String concludeChild, String dateStart, String dateEnd);

}
