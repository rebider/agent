package com.ryx.credit.dao.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.FreezeRequest;
import com.ryx.credit.pojo.admin.agent.FreezeRequestExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FreezeRequestMapper {
    long countByExample(FreezeRequestExample example);

    int deleteByExample(FreezeRequestExample example);

    int insert(FreezeRequest record);

    int insertSelective(FreezeRequest record);

    List<FreezeRequest> selectByExample(FreezeRequestExample example);

    FreezeRequest selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FreezeRequest record);

    int updateByPrimaryKey(FreezeRequest record);

    List<Map<String,String>> queryAgentFreezeRequestList(@Param("map")Map<String,Object> map, @Param("page") Page page);

    int  queryAgentFreezeRequestListCount(@Param("map") Map<String,Object> map);
}