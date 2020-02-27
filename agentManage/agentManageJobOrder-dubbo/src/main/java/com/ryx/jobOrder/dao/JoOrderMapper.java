package com.ryx.jobOrder.dao;

import com.ryx.jobOrder.pojo.JoOrder;
import com.ryx.jobOrder.pojo.JoOrderExample;
import com.ryx.jobOrder.vo.JoTaskVo;

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

    List<JoTaskVo> queryJobOrderList(Map<String, Object> param);

    long queryJobOrderListCount(Map<String, Object> param);

    List<Map<String, Object>> queryJobOrderLaunchList(Map<String, Object> param);

    int queryJobOrderLaunchListCount(Map<String, Object> param);
}