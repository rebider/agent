package com.ryx.credit.activity.dao;

import com.ryx.credit.activity.entity.OReturnOrder;
import com.ryx.credit.activity.entity.OReturnOrderExample;
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