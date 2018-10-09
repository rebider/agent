package com.ryx.credit.machine.dao;

import com.ryx.credit.machine.entity.ImsTermMachine;
import com.ryx.credit.machine.entity.ImsTermMachineExample;

import java.util.List;

public interface ImsTermMachineMapper {
    long countByExample(ImsTermMachineExample example);

    int deleteByExample(ImsTermMachineExample example);

    int insert(ImsTermMachine record);

    int insertSelective(ImsTermMachine record);

    List<ImsTermMachine> selectByExample(ImsTermMachineExample example);

    ImsTermMachine selectByPrimaryKey(String machineId);

    int updateByPrimaryKeySelective(ImsTermMachine record);

    int updateByPrimaryKey(ImsTermMachine record);
}