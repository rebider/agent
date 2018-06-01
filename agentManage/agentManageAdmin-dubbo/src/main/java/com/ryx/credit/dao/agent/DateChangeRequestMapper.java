package com.ryx.credit.dao.agent;

import com.ryx.credit.pojo.admin.agent.DateChangeRequest;
import com.ryx.credit.pojo.admin.agent.DateChangeRequestExample;

import java.util.List;

public interface DateChangeRequestMapper {
    int countByExample(DateChangeRequestExample example);

    int deleteByExample(DateChangeRequestExample example);

    int insert(DateChangeRequest record);

    int insertSelective(DateChangeRequest record);

    List<DateChangeRequest> selectByExample(DateChangeRequestExample example);

    DateChangeRequest selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DateChangeRequest record);

    int updateByPrimaryKey(DateChangeRequest record);
}