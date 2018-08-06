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

    List<DPosRegion> findRegionByProvinceName(String provinceName);
}