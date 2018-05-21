package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.AssProtoCol;
import com.ryx.credit.pojo.admin.agent.AssProtoColExample;

import java.util.List;

public interface AssProtoColMapper {
    int countByExample(AssProtoColExample example);

    int deleteByExample(AssProtoColExample example);

    int insert(AssProtoCol record);

    int insertSelective(AssProtoCol record);

    List<AssProtoCol> selectByExample(AssProtoColExample example);

    AssProtoCol selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AssProtoCol record);

    int updateByPrimaryKey(AssProtoCol record);
}