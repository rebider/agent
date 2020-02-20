package com.ryx.jobOrder.dao;

import com.ryx.jobOrder.pojo.JoOrder;
import com.ryx.jobOrder.pojo.JoOrderExample;

import java.util.List;

public interface JoOrderMapper {
    long countByExample(JoOrderExample example);

    int deleteByExample(JoOrderExample example);

    int insert(JoOrder record);

    int insertSelective(JoOrder record);

    List<JoOrder> selectByExample(JoOrderExample example);

    JoOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JoOrder record);

    int updateByPrimaryKey(JoOrder record);
}