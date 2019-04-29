package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PToolSupply;
import com.ryx.credit.profit.pojo.PToolSupplyExample;

import java.util.List;

public interface PToolSupplyMapper {
    long countByExample(PToolSupplyExample example);

    int deleteByExample(PToolSupplyExample example);

    int insert(PToolSupply record);

    int insertSelective(PToolSupply record);

    List<PToolSupply> selectByExample(PToolSupplyExample example);

    PToolSupply selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PToolSupply record);

    int updateByPrimaryKey(PToolSupply record);
}