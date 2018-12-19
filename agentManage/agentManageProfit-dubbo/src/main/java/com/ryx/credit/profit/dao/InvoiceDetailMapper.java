package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.InvoiceDetail;
import com.ryx.credit.profit.pojo.InvoiceDetailExample;
import java.util.List;

public interface InvoiceDetailMapper {
    long countByExample(InvoiceDetailExample example);

    int deleteByExample(InvoiceDetailExample example);

    int insert(InvoiceDetail record);

    int insertSelective(InvoiceDetail record);

    List<InvoiceDetail> selectByExample(InvoiceDetailExample example);

    InvoiceDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InvoiceDetail record);

    int updateByPrimaryKey(InvoiceDetail record);
}