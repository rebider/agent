package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.AgentQuitRefund;
import com.ryx.credit.pojo.admin.agent.AgentQuitRefundExample;

import java.util.List;

public interface AgentQuitRefundMapper {
    long countByExample(AgentQuitRefundExample example);

    int deleteByExample(AgentQuitRefundExample example);

    int insert(AgentQuitRefund record);

    int insertSelective(AgentQuitRefund record);

    List<AgentQuitRefund> selectByExample(AgentQuitRefundExample example);

    AgentQuitRefund selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentQuitRefund record);

    int updateByPrimaryKey(AgentQuitRefund record);
}