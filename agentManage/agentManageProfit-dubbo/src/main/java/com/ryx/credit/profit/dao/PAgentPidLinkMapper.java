package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PAgentPidLink;
import com.ryx.credit.profit.pojo.PAgentPidLinkExample;
import java.util.List;

public interface PAgentPidLinkMapper {
    long countByExample(PAgentPidLinkExample example);

    int deleteByExample(PAgentPidLinkExample example);

    int insert(PAgentPidLink record);

    int insertSelective(PAgentPidLink record);

    List<PAgentPidLink> selectByExample(PAgentPidLinkExample example);

    PAgentPidLink selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PAgentPidLink record);

    int updateByPrimaryKey(PAgentPidLink record);
}