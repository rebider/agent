package com.ryx.credit.dao.agent;


import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.AgentMergeBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentMergeBusInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgentMergeBusInfoMapper {
    long countByExample(AgentMergeBusInfoExample example);

    int deleteByExample(AgentMergeBusInfoExample example);

    int insert(AgentMergeBusInfo record);

    int insertSelective(AgentMergeBusInfo record);

    List<AgentMergeBusInfo> selectByExample(AgentMergeBusInfoExample example);

    AgentMergeBusInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentMergeBusInfo record);

    int updateByPrimaryKey(AgentMergeBusInfo record);

    List<Map<String, Object>> selectMergeBusinfoList(@Param("map") Map<String, Object> map, @Param("page") Page page);

    int selectMergeBusinfoCount(@Param("map") Map<String, Object> map);
}