package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.Invoice;
import com.ryx.credit.profit.pojo.InvoiceExample;
import java.util.List;

public interface InvoiceMapper {
    long countByExample(InvoiceExample example);

    int deleteByExample(InvoiceExample example);

    int insert(Invoice record);

    int insertSelective(Invoice record);

    List<Invoice> selectByExample(InvoiceExample example);

    Invoice selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Invoice record);

    int updateByPrimaryKey(Invoice record);

    /**根据id和月份，以及状态判断用户数据是否存在*/
    Invoice getInvoiceByInvoice(Invoice record);
    /**设置数据有效性*/
    int setStatusToInvoice(Invoice record);


}