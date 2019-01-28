package com.ryx.credit.dao.agent;


import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.AgentQuitRefund;
import com.ryx.credit.pojo.admin.agent.AgentQuitRefundExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgentQuitRefundMapper {
    long countByExample(AgentQuitRefundExample example);

    int deleteByExample(AgentQuitRefundExample example);

    int insert(AgentQuitRefund record);

    int insertSelective(AgentQuitRefund record);

    List<AgentQuitRefund> selectByExample(AgentQuitRefundExample example);

    AgentQuitRefund selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentQuitRefund record);

    int updateByPrimaryKey(AgentQuitRefund record);

    List<Map<String, Object>> queryQuitRefundList(@Param("map") Map<String, Object> map, @Param("page") Page page);

    int queryQuitRefundCount(@Param("map") Map<String, Object> map);
}