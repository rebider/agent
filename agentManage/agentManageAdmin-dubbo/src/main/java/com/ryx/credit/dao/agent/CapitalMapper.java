package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.Capital;
import com.ryx.credit.pojo.admin.agent.CapitalExample;

import java.util.List;

public interface CapitalMapper {
    int countByExample(CapitalExample example);

    int deleteByExample(CapitalExample example);

    int insert(Capital record);

    int insertSelective(Capital record);

    List<Capital> selectByExample(CapitalExample example);

    Capital selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Capital record);

    int updateByPrimaryKey(Capital record);
}