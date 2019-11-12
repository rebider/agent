package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OrderAdjDetail;
import com.ryx.credit.pojo.admin.order.OrderAdjDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderAdjDetailMapper {
    long countByExample(OrderAdjDetailExample example);

    int deleteByExample(OrderAdjDetailExample example);

    int insert(OrderAdjDetail record);

    int insertSelective(OrderAdjDetail record);

    List<OrderAdjDetail> selectByExample(OrderAdjDetailExample example);

    OrderAdjDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderAdjDetail record);

    int updateByPrimaryKey(OrderAdjDetail record);

    long countAdjNum(@Param("map") Map<String,Object> map);

    OrderAdjDetail selectByAdjustId(String adjId);
}