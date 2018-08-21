package com.ryx.credit.activity.dao;

import com.ryx.credit.activity.entity.ActRuTask;
import com.ryx.credit.activity.entity.ActRuTaskExample;
import com.ryx.credit.common.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ActRuTaskMapper {
    int countByExample(ActRuTaskExample example);

    int deleteByExample(ActRuTaskExample example);

    int insert(ActRuTask record);

    int insertSelective(ActRuTask record);

    List<ActRuTask> selectByExample(ActRuTaskExample example);

    ActRuTask selectByPrimaryKey(Object id);

    int updateByPrimaryKeySelective(ActRuTask record);

    int updateByPrimaryKey(ActRuTask record);

    List<Map<String,Object>> queryMyTask(@Param("params") Map<String,Object> params);

    List<Map<String,Object>> queryMyTaskPage(@Param("params") Map<String,Object> params,@Param("page") Page page);

    int queryMyTaskCount(@Param("params") Map<String,Object> params);
}