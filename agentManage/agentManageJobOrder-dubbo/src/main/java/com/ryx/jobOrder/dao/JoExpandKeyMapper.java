package com.ryx.jobOrder.dao;

import com.ryx.jobOrder.pojo.JoExpandKey;
import com.ryx.jobOrder.pojo.JoExpandKeyExample;
import java.util.List;

public interface JoExpandKeyMapper {
    long countByExample(JoExpandKeyExample example);

    int deleteByExample(JoExpandKeyExample example);

    int insert(JoExpandKey record);

    int insertSelective(JoExpandKey record);

    List<JoExpandKey> selectByExample(JoExpandKeyExample example);

    JoExpandKey selectByPrimaryKey(String jid);

    int updateByPrimaryKeySelective(JoExpandKey record);

    int updateByPrimaryKey(JoExpandKey record);
}