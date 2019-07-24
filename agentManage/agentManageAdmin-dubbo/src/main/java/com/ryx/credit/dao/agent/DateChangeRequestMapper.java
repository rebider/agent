package com.ryx.credit.dao.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.DateChangeRequest;
import com.ryx.credit.pojo.admin.agent.DateChangeRequestExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DateChangeRequestMapper {
    int countByExample(DateChangeRequestExample example);

    int deleteByExample(DateChangeRequestExample example);

    int insert(DateChangeRequest record);

    int insertSelective(DateChangeRequest record);

    List<DateChangeRequest> selectByExample(DateChangeRequestExample example);

    DateChangeRequest selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DateChangeRequest record);

    int updateByPrimaryKey(DateChangeRequest record);

    List<Map<String,Object>> queryData(@Param("map") Map<String, Object> map,@Param("page") Page page);

    int queryDataCount(@Param("map")Map<String, Object> map);

    List<Map<String,Object>> exportDcColinfo(@Param("map") Map map);
}