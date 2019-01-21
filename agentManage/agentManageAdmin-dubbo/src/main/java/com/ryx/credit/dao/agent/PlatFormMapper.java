package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.agent.PlatFormExample;

import java.util.List;
import java.util.Map;

public interface PlatFormMapper {
    int countByExample(PlatFormExample example);

    int deleteByExample(PlatFormExample example);

    int insert(PlatForm record);

    int insertSelective(PlatForm record);

    List<PlatForm> selectByExample(PlatFormExample example);

    PlatForm selectByPlatFormNum(String platFormNum);

    int countPlatForm(Map<String, Object> condition);

    List<PlatForm> selectPlatForm(Map<String, Object> condition);

    PlatForm selectByPrimaryKey(String id);

    long seqId();   // id自增

    int updateByPrimaryKeySelective(PlatForm record);  // 删除（编辑）状态

    int updateByPrimaryKey(PlatForm record);

    String selectPlatType(String platformNum);
}