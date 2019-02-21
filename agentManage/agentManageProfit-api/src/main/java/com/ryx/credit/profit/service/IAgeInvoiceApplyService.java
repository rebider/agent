package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.InvoiceApply;

import java.util.List;
import java.util.Map;

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
     * 代理商发票线上审批，启动审批流
     * @param invoiceApply
     * @param userId
     * @param workId
     * @param cUser
     */
    void applyInvoice(InvoiceApply invoiceApply, String userId, String workId, String cUser);

    /**
     * 处理审批任务
     */
    public AgentResult approvalTask(AgentVo agentVo, String userId,InvoiceApply invoiceApply) throws ProcessException;

    /**
     * 审批流调回方法
     */
    void completeTaskEnterActivity(String insid, String status);

    /**
     * 重新编辑
     */
    void editCheckRegect(InvoiceApply invoiceApply)throws Exception;

    InvoiceApply getInvoiceApplyById(String id);

    /**
     * 代理商批量导入发票信息
     */
    void volumeImportData(Map<String,String> map, List<List<Object>> list);

    /**
     * 根据发票号得到数据
     */
    InvoiceApply getInvoiceApplyByInvoiceNumber(String invoiceNumber);

    /**
     * 批量上传附件  todo
     */
   //void volumeImportFile();


}
