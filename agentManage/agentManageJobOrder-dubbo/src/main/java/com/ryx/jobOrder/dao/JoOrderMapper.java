package com.ryx.jobOrder.dao;

import com.ryx.jobOrder.pojo.JoOrder;
import com.ryx.jobOrder.pojo.JoOrderExample;

import java.util.List;
import java.util.Map;

public interface JoOrderMapper {
    long countByExample(JoOrderExample example);

    int deleteByExample(JoOrderExample example);

    int insert(JoOrder record);

    int insertSelective(JoOrder record);

    List<JoOrder> selectByExample(JoOrderExample example);

    JoOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JoOrder record);

    int updateByPrimaryKey(JoOrder record);

    List<Map<String, Object>> queryJobOrderList(Map<String, Object> param);

    int queryJobOrderListCount(Map<String, Object> param);

    List<Map<String, Object>> queryJobOrderLaunchList(Map<String, Object> param);

    int queryJobOrderLaunchListCount(Map<String, Object> param);
}