package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.InvoiceSum;
import com.ryx.credit.profit.pojo.InvoiceSumExample;

import java.util.List;

public interface InvoiceSumMapper {
    long countByExample(InvoiceSumExample example);

    int deleteByExample(InvoiceSumExample example);

    int insert(InvoiceSum record);

    int insertSelective(InvoiceSum record);

    List<InvoiceSum> selectByExample(InvoiceSumExample example);

    InvoiceSum selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InvoiceSum record);

    int updateByPrimaryKey(InvoiceSum record);
}