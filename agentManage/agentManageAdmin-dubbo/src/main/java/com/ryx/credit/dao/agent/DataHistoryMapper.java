package com.ryx.credit.dao.agent;


import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.DataHistory;
import com.ryx.credit.pojo.admin.agent.DataHistoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DataHistoryMapper {
    int countByExample(DataHistoryExample example);

    int deleteByExample(DataHistoryExample example);

    int insert(DataHistory record);

    int insertSelective(DataHistory record);

    List<DataHistory> selectByExampleWithBLOBs(DataHistoryExample example);

    List<DataHistory> selectByExample(DataHistoryExample example);

    DataHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DataHistory record);

    int updateByPrimaryKeyWithBLOBs(DataHistory record);

    int updateByPrimaryKey(DataHistory record);

    List<Map<String,Object>> selectAll(@Param("map") Map<String, Object> map, @Param("page") Page page);

    int getCount(@Param("map")Map<String, Object> map);
}