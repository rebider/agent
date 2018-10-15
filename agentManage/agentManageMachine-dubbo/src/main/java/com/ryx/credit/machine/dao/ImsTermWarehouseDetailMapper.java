package com.ryx.credit.machine.dao;

import com.ryx.credit.machine.entity.ImsTermWarehouseDetail;
import com.ryx.credit.machine.entity.ImsTermWarehouseDetailExample;
import java.util.List;

public interface ImsTermWarehouseDetailMapper {
    long countByExample(ImsTermWarehouseDetailExample example);

    int deleteByExample(ImsTermWarehouseDetailExample example);

    int insert(ImsTermWarehouseDetail record);

    int insertSelective(ImsTermWarehouseDetail record);

    List<ImsTermWarehouseDetail> selectByExample(ImsTermWarehouseDetailExample example);

    ImsTermWarehouseDetail selectByPrimaryKey(String posSn);

    int updateByPrimaryKeySelective(ImsTermWarehouseDetail record);

    int updateByPrimaryKey(ImsTermWarehouseDetail record);
}