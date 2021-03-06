package com.ryx.credit.dao.order;


import com.ryx.credit.pojo.admin.order.OActivityVisible;
import com.ryx.credit.pojo.admin.order.OActivityVisibleExample;

import java.util.List;
import java.util.Map;

public interface OActivityVisibleMapper {
    long countByExample(OActivityVisibleExample example);

    int deleteByExample(OActivityVisibleExample example);

    int insert(OActivityVisible record);

    int insertSelective(OActivityVisible record);

    List<OActivityVisible> selectByExample(OActivityVisibleExample example);

    List<Map<String,String>> selectConfigured(String activityId);

    List<String> selectConfiguredReturnAgentId(String activityId);
}