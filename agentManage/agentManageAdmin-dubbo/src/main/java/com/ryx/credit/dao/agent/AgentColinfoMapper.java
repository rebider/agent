package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.pojo.admin.agent.AgentColinfoExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AgentColinfoMapper {
    int countByExample(AgentColinfoExample example);

    int deleteByExample(AgentColinfoExample example);

    int insert(AgentColinfo record);

    int insertSelective(AgentColinfo record);

    List<AgentColinfo> selectByExample(AgentColinfoExample example);

    AgentColinfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentColinfo record);

    int updateByPrimaryKey(AgentColinfo record);

    List<AgentColinfo> proceedsQuery(String id);

    List<AgentColinfo> queryBusConinfoList(String busId);

    List<Map<String,Object>> synConinfo(@Param("params") Map<String,Object> params);

    List<String> queryAgentHaveColinfo();

    /**
     * 通过业务信息ID查询收款账户信息（RDB预升级）
     * @param agentId
     * @return
     */
    AgentColinfo selectByAgentId(String agentId);
    List<AgentColinfo> selectColInfoByAgent(@Param("agentId") String agentId, @Param("avbList")List<BigDecimal> status);

}