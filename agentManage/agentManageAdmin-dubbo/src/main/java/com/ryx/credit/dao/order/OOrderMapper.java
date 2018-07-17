package com.ryx.credit.dao.order;


import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.OOrderExample;

import java.util.List;

public interface OOrderMapper {
    int countByExample(OOrderExample example);

    int deleteByExample(OOrderExample example);

    int insert(OOrder record);

    int insertSelective(OOrder record);

    List<OOrder> selectByExample(OOrderExample example);

    OOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OOrder record);

    int updateByPrimaryKey(OOrder record);
}