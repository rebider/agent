package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.agent.PlatFormExample;

import java.util.List;

public interface PlatFormMapper {
    int countByExample(PlatFormExample example);

    int deleteByExample(PlatFormExample example);

    int insert(PlatForm record);

    int insertSelective(PlatForm record);

    List<PlatForm> selectByExample(PlatFormExample example);
}