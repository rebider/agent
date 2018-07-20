package com.ryx.credit.dao.order;


import com.ryx.credit.pojo.admin.order.OReceiptOrder;
import com.ryx.credit.pojo.admin.order.OReceiptOrderExample;

import java.util.List;

public interface OReceiptOrderMapper {
    long countByExample(OReceiptOrderExample example);

    int deleteByExample(OReceiptOrderExample example);

    int insert(OReceiptOrder record);

    int insertSelective(OReceiptOrder record);

    List<OReceiptOrder> selectByExample(OReceiptOrderExample example);

    OReceiptOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OReceiptOrder record);

    int updateByPrimaryKey(OReceiptOrder record);
}