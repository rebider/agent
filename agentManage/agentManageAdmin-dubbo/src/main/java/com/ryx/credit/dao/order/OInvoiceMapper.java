package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OInvoice;
import com.ryx.credit.pojo.admin.order.OInvoiceExample;

import java.util.List;

public interface OInvoiceMapper {
    long countByExample(OInvoiceExample example);

    int deleteByExample(OInvoiceExample example);

    int insert(OInvoice record);

    int insertSelective(OInvoice record);

    List<OInvoice> selectByExample(OInvoiceExample example);

    OInvoice selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OInvoice record);

    int updateByPrimaryKey(OInvoice record);
}