package com.ryx.credit.dao.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.AgentFreeze;
import com.ryx.credit.pojo.admin.agent.AgentFreezeExample;
import com.ryx.credit.pojo.admin.vo.AgentFreezeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgentFreezeMapper {
    long countByExample(AgentFreezeExample example);

    int deleteByExample(AgentFreezeExample example);

    int insert(AgentFreeze record);

    int insertSelective(AgentFreeze record);

    List<AgentFreeze> selectByExample(AgentFreezeExample example);

    AgentFreeze selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentFreeze record);

    int updateByPrimaryKey(AgentFreeze record);

    List<Map<String,String>> queryAgentFreezeList(@Param("map")Map<String,Object> map,@Param("page") Page page);

    int queryAgentFreezeCount(@Param("map")Map<String,Object> map);

    List<AgentFreezeVo> queryAgentBasicLackData();
}