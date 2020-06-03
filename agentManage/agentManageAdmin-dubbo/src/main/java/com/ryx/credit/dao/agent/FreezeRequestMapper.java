package com.ryx.credit.dao.agent;

import com.ryx.credit.pojo.admin.agent.FreezeRequest;
import com.ryx.credit.pojo.admin.agent.FreezeRequestExample;
import java.util.List;

public interface FreezeRequestMapper {
    long countByExample(FreezeRequestExample example);

    int deleteByExample(FreezeRequestExample example);

    int insert(FreezeRequest record);

    int insertSelective(FreezeRequest record);

    List<FreezeRequest> selectByExample(FreezeRequestExample example);

    FreezeRequest selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FreezeRequest record);

    int updateByPrimaryKey(FreezeRequest record);
}