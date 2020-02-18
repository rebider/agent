package com.ryx.jobOrder.dao;

import com.ryx.jobOrder.pojo.JoTask;
import com.ryx.jobOrder.pojo.JoTaskExample;
import java.util.List;

public interface JoTaskMapper {
    long countByExample(JoTaskExample example);

    int deleteByExample(JoTaskExample example);

    int insert(JoTask record);

    int insertSelective(JoTask record);

    List<JoTask> selectByExample(JoTaskExample example);

    JoTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JoTask record);

    int updateByPrimaryKey(JoTask record);
}