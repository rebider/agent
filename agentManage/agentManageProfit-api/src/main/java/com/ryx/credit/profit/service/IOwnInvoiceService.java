package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.InvoiceApply;
import com.ryx.credit.profit.pojo.InvoiceDetail;

import java.util.List;
import java.util.Map;

import com.ryx.credit.common.util.Page;

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
     * @Author chenqiutian
     */
    PageInfo getInvoiceDetailList(Page page, String agentId, String agentName, String concludeChild, String dateStart, String dateEnd);

    /**
     * 向欠票导入表中导入数据
     * @Author chenqiutian
     */
    void exportData(List<List<Object>> datas, String loginName) throws RuntimeException;

    /**
     * 根据id获取代理商信息
     * @Author chenqiutian
     * @param id
     * @return
     */
    InvoiceDetail getInvoiceById(String id);

    /**
     * 设置调整金额
     * @Author chenqiutian
     * @param invoiceDetail
     * @return
     */
    void setAdjustAMT(InvoiceDetail invoiceDetail,InvoiceDetail adjustDetail);

    /**
     * 导出数据
     * @Author chenqiutian
     * @param agentId
     * @param agentName
     * @param concludeChild
     * @param dateStart
     * @param dateEnd
     * @return
     */
    List<InvoiceDetail> exportInvoiceData(String agentId, String agentName, String concludeChild, String dateStart, String dateEnd);

    Map<String,Object> profitCount(Map<String,Object> param);

    /**
     * 根据AG码或者代理商名称获得本月欠票
     * @Author chenqiutian
     * @param map
     * @return
     */
    Map<String,Object> getOwmInvoice(Map<String,String> map);

    /**
     * 获取代理商的欠票信息
     * @Author chenqiutian
     * @param page
     * @param loginName
     * @return
     */
    List<InvoiceDetail> getAgentInvoiceDetailList(Page page,String loginName);

    /**
     * 线上审批后计算欠票
     */
    void invoiceApplyComputer(InvoiceApply invoiceApply);


}
