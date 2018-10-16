package com.ryx.credit.machine.dao;

import com.ryx.credit.machine.entity.ImsTermWarehouseLog;
import com.ryx.credit.machine.entity.ImsTermWarehouseLogExample;
import java.util.List;

public interface ImsTermWarehouseLogMapper {
    long countByExample(ImsTermWarehouseLogExample example);

    int deleteByExample(ImsTermWarehouseLogExample example);

    int insert(ImsTermWarehouseLog record);

    int insertSelective(ImsTermWarehouseLog record);

    List<ImsTermWarehouseLog> selectByExample(ImsTermWarehouseLogExample example);
}