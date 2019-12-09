package com.ryx.credit.dao.order;


import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.order.OrderAdj;
import com.ryx.credit.pojo.admin.order.OrderAdjExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderAdjMapper {
    long countByExample(OrderAdjExample example);

    int deleteByExample(OrderAdjExample example);

    int insert(OrderAdj record);

    int insertSelective(OrderAdj record);

    List<OrderAdj> selectByExample(OrderAdjExample example);

    OrderAdj selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderAdj record);

    int updateByPrimaryKey(OrderAdj record);

    List<Map<String,Object>> selectAgentUpModelView(@Param("map") Map<String,Object> map, @Param("page") Page page);

    int  selectAgentUpModelViewCount(@Param("map") Map<String,Object> map);
}