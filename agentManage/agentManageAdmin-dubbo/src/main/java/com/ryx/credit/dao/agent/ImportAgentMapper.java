package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.ImportAgent;
import com.ryx.credit.pojo.admin.agent.ImportAgentExample;

import java.util.List;

public interface ImportAgentMapper {
    int countByExample(ImportAgentExample example);

    int deleteByExample(ImportAgentExample example);

    int insert(ImportAgent record);

    int insertSelective(ImportAgent record);

    List<ImportAgent> selectByExampleWithBLOBs(ImportAgentExample example);

    List<ImportAgent> selectByExample(ImportAgentExample example);

    ImportAgent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ImportAgent record);

    int updateByPrimaryKeyWithBLOBs(ImportAgent record);

    int updateByPrimaryKey(ImportAgent record);
}