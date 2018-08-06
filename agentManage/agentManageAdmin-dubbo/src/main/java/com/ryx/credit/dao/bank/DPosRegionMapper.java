package com.ryx.credit.dao.bank;

import com.ryx.credit.pojo.admin.bank.DPosRegion;
import com.ryx.credit.pojo.admin.bank.DPosRegionExample;

import java.util.List;

public interface DPosRegionMapper {
    long countByExample(DPosRegionExample example);

    int deleteByExample(DPosRegionExample example);

    int insert(DPosRegion record);

    int insertSelective(DPosRegion record);

    List<DPosRegion> selectByExample(DPosRegionExample example);

    DPosRegion selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(DPosRegion record);

    int updateByPrimaryKey(DPosRegion record);
}