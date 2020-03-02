package com.ryx.jobOrder.dao;

import com.ryx.credit.common.util.Page;
import com.ryx.jobOrder.pojo.JoCustomKey;
import com.ryx.jobOrder.pojo.JoCustomKeyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JoCustomKeyMapper {
    long countByExample(JoCustomKeyExample example);

    int deleteByExample(JoCustomKeyExample example);

    int insert(JoCustomKey record);

    int insertSelective(JoCustomKey record);

    List<JoCustomKey> selectByExample(JoCustomKeyExample example);

    JoCustomKey selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JoCustomKey record);

    int updateByPrimaryKey(JoCustomKey record);

    List selectMapByExample(JoCustomKeyExample example);

    List<Map<String, Object>> joCustomKeyList(@Param("map")Map map, @Param("page")Page page);

    int joCustomKeyCount(@Param("map")Map map);

    List<Map> selectKeyWord(@Param("joSecondKeyNum")String joSecondKeyNum);

    int deletejoCustomKeyById(String id);
}