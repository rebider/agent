package com.ryx.credit.machine.dao;

import com.ryx.credit.machine.entity.ImsTermAdjustDetail;
import com.ryx.credit.machine.entity.ImsTermAdjustDetailExample;
import java.util.List;

public interface ImsTermAdjustDetailMapper {
    long countByExample(ImsTermAdjustDetailExample example);

    int deleteByExample(ImsTermAdjustDetailExample example);

    int insert(ImsTermAdjustDetail record);

    int insertSelective(ImsTermAdjustDetail record);

    List<ImsTermAdjustDetail> selectByExample(ImsTermAdjustDetailExample example);

    ImsTermAdjustDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ImsTermAdjustDetail record);

    int updateByPrimaryKey(ImsTermAdjustDetail record);
}