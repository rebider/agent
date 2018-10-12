package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PAgentQuit;
import com.ryx.credit.profit.pojo.PAgentQuitExample;
import java.util.List;
import java.util.Map;

public interface PAgentQuitMapper {
    long countByExample(PAgentQuitExample example);

    int deleteByExample(PAgentQuitExample example);

    int insert(PAgentQuit record);

    int insertSelective(PAgentQuit record);

    List<PAgentQuit> selectByExample(PAgentQuitExample example);

    PAgentQuit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PAgentQuit record);

    int updateByPrimaryKey(PAgentQuit record);


    List<Map<String, Object>> getAgentQuitList(Map<String, Object> param);

    Long getAgentQuitCount(Map<String, Object> param);

    PAgentQuit getAgentQuitById(String id);

    List<Map<String,Object>> queryBusPlat(Map<String, Object> param);

}