package com.ryx.credit.dao.order;


import com.ryx.credit.pojo.admin.order.OSubOrder;
import com.ryx.credit.pojo.admin.order.OSubOrderExample;

import java.util.List;
import java.util.Map;

public interface OSubOrderMapper {
    int countByExample(OSubOrderExample example);

    int deleteByExample(OSubOrderExample example);

    int insert(OSubOrder record);

    int insertSelective(OSubOrder record);

    List<OSubOrder> selectByExample(OSubOrderExample example);

    OSubOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OSubOrder record);

    int updateByPrimaryKey(OSubOrder record);

    Long selectProNumByBusNum(Map<String, Object> paramMap);
}