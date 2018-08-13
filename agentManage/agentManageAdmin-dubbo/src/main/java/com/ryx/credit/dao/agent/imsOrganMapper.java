package com.ryx.credit.dao.agent;



import com.ryx.credit.pojo.admin.agent.imsOrgan;
import com.ryx.credit.pojo.admin.agent.imsOrganExample;

import java.util.List;

public interface imsOrganMapper {
    long countByExample(imsOrganExample example);

    int deleteByExample(imsOrganExample example);

    int insert(imsOrgan record);

    int insertSelective(imsOrgan record);

    List<imsOrgan> selectByExample(imsOrganExample example);

    imsOrgan selectByPrimaryKey(String orgId);

    int updateByPrimaryKeySelective(imsOrgan record);

    int updateByPrimaryKey(imsOrgan record);
}