package com.ryx.jobOrder.dao;

import com.ryx.jobOrder.pojo.JoCustomKey;
import com.ryx.jobOrder.pojo.JoCustomKeyExample;
import java.util.List;

public interface JoCustomKeyMapper {
    long countByExample(JoCustomKeyExample example);

    int deleteByExample(JoCustomKeyExample example);

    int insert(JoCustomKey record);

    int insertSelective(JoCustomKey record);

    List<JoCustomKey> selectByExample(JoCustomKeyExample example);

    JoCustomKey selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JoCustomKey record);

    int updateByPrimaryKey(JoCustomKey record);
}