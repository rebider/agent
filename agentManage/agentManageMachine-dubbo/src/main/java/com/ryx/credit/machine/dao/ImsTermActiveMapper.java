package com.ryx.credit.machine.dao;

import com.ryx.credit.machine.entity.ImsTermActive;
import com.ryx.credit.machine.entity.ImsTermActiveExample;
import java.util.List;

public interface ImsTermActiveMapper {
    long countByExample(ImsTermActiveExample example);

    int deleteByExample(ImsTermActiveExample example);

    int insert(ImsTermActive record);

    int insertSelective(ImsTermActive record);

    List<ImsTermActive> selectByExample(ImsTermActiveExample example);

    ImsTermActive selectByPrimaryKey(String posSn);

    int updateByPrimaryKeySelective(ImsTermActive record);

    int updateByPrimaryKey(ImsTermActive record);
}