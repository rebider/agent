package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.InvoiceSum;
import org.springframework.mail.MailException;

import java.util.List;
import java.util.Map;

public interface IInvoiceSumService {
    PageInfo selectByMap(Page page, Map<String, String> param, Map<String,Object> map);

    InvoiceSum selectByPrimaryKey(String id);

    Map<String, Object> getInvoiceFinalData(Map<String, Object> param);

    void AdjustFreeze(Map<String, Object> param);

    void againFreeze(Map<String, Object> param);

    int updateByPrimaryKeySelective(InvoiceSum record);

    void invoicePreLeftAmt(List<List<Object>> param,String profitMonth,String userId)throws MessageException;

    boolean invocationIntUnFreeze(String agentId, String user,String reason) throws MessageException;

    boolean invocationIntFreeze(String agentId, String user,String id,String reason) throws MessageException;


    List<Map> exports ( Map<String, String> param, Map<String,Object> map);

}
