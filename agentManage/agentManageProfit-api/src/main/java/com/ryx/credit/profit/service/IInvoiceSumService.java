package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.InvoiceSum;

import java.util.List;
import java.util.Map;

public interface IInvoiceSumService {
    PageInfo selectByMap(PageInfo pageInfo, Map<String, String> param,Map<String,Object> map);

    InvoiceSum selectByPrimaryKey(String id);

    Map<String, Object> getInvoiceFinalData(Map<String, Object> param);

    void AdjustFreeze(Map<String, Object> param);

    void againFreeze(Map<String, Object> param);

    int updateByPrimaryKeySelective(InvoiceSum record);

  void invoicePreLeftAmt(List<List<Object>> param,String profitMonth)throws Exception;
}
