package com.ryx.credit.machine.dao;

import com.ryx.credit.machine.entity.ImsTermAdjust;
import com.ryx.credit.machine.entity.ImsTermAdjustExample;
import java.util.List;

public interface ImsTermAdjustMapper {
    long countByExample(ImsTermAdjustExample example);

    int deleteByExample(ImsTermAdjustExample example);

    int insert(ImsTermAdjust record);

    int insertSelective(ImsTermAdjust record);

    List<ImsTermAdjust> selectByExample(ImsTermAdjustExample example);

    ImsTermAdjust selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ImsTermAdjust record);

    int updateByPrimaryKey(ImsTermAdjust record);
}