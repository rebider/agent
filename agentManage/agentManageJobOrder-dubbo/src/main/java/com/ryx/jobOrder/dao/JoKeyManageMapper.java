package com.ryx.jobOrder.dao;

import com.ryx.credit.common.util.Page;
import com.ryx.jobOrder.pojo.JoKeyManage;
import com.ryx.jobOrder.pojo.JoKeyManageExample;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface JoKeyManageMapper {
    long countByExample(JoKeyManageExample example);

    int deleteByExample(JoKeyManageExample example);

    int insert(JoKeyManage record);

    int insertSelective(JoKeyManage record);

    List<JoKeyManage> selectByExample(JoKeyManageExample example);

    JoKeyManage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JoKeyManage record);

    int updateByPrimaryKey(JoKeyManage record);

    List<Map<String,Object>> keywordList(@Param("map")HashMap<String, Object> map,@Param("page") Page page);

    int keywordCount(@Param("map")HashMap<String, Object> map);

    List<Map<String,Object>> selectLevel();

    List<Map<String,Object>> queryJobOrderType();

}