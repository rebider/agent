package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OReturnOrder;
import com.ryx.credit.pojo.admin.order.OReturnOrderExample;
import java.util.List;

public interface OReturnOrderMapper {
    long countByExample(OReturnOrderExample example);

    int deleteByExample(OReturnOrderExample example);

    int insert(OReturnOrder record);

    int insertSelective(OReturnOrder record);

    List<OReturnOrder> selectByExample(OReturnOrderExample example);

    OReturnOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OReturnOrder record);

    int updateByPrimaryKey(OReturnOrder record);
}