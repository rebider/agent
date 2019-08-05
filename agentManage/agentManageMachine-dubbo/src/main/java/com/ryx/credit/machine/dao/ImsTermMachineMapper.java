package com.ryx.credit.machine.dao;

import com.ryx.credit.machine.entity.ImsTermMachine;
import com.ryx.credit.machine.entity.ImsTermMachineExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ImsTermMachineMapper {
    long countByExample(ImsTermMachineExample example);

    int deleteByExample(ImsTermMachineExample example);

    int insert(ImsTermMachine record);

    int insertSelective(ImsTermMachine record);

    List<ImsTermMachine> selectByExample(ImsTermMachineExample example);

    ImsTermMachine selectByPrimaryKey(String machineId);

    int updateByPrimaryKeySelective(ImsTermMachine record);

    int updateByPrimaryKey(ImsTermMachine record);

    List<Map> querySSIMS_TERM_MACHINE();

    Map<String,String> queryIMS_POS_ACTIVITY(@Param("id")String id);
}