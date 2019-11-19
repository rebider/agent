package com.ryx.credit.machine.dao;

import com.ryx.credit.machine.entity.ImsOrgan;
import com.ryx.credit.machine.entity.ImsOrganExample;
import java.util.List;

public interface ImsOrganMapper {
    long countByExample(ImsOrganExample example);

    int deleteByExample(ImsOrganExample example);

    int insert(ImsOrgan record);

    int insertSelective(ImsOrgan record);

    List<ImsOrgan> selectByExample(ImsOrganExample example);

    ImsOrgan selectByPrimaryKey(String orgId);

    int updateByPrimaryKeySelective(ImsOrgan record);

    int updateByPrimaryKey(ImsOrgan record);

    List<ImsOrgan> selectImsListByOrgId(String orgId);
}