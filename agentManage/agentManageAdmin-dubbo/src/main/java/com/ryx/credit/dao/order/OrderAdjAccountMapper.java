package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OrderAdjAccount;
import com.ryx.credit.pojo.admin.order.OrderAdjAccountExample;
import com.ryx.credit.pojo.admin.vo.OrderAdjAccountVo;

import java.util.List;

public interface OrderAdjAccountMapper {
    long countByExample(OrderAdjAccountExample example);

    int deleteByExample(OrderAdjAccountExample example);

    int insert(OrderAdjAccount record);

    int insertSelective(OrderAdjAccount record);

    List<OrderAdjAccount> selectByExample(OrderAdjAccountExample example);

    OrderAdjAccount selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderAdjAccount record);

    int updateByPrimaryKey(OrderAdjAccount record);

    List<OrderAdjAccountVo> selectListByExample(OrderAdjAccountExample example);
}