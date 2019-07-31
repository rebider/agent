package com.ryx.credit.machine.dao;

import com.ryx.credit.machine.entity.ImsMachineActivity;
import com.ryx.credit.machine.entity.ImsMachineActivityExample;
import java.util.List;

public interface ImsMachineActivityMapper {
    long countByExample(ImsMachineActivityExample example);

    int deleteByExample(ImsMachineActivityExample example);

    int insert(ImsMachineActivity record);

    int insertSelective(ImsMachineActivity record);

    List<ImsMachineActivity> selectByExample(ImsMachineActivityExample example);

    ImsMachineActivity selectByPrimaryKey(String activityId);

    int updateByPrimaryKeySelective(ImsMachineActivity record);

    int updateByPrimaryKey(ImsMachineActivity record);
}