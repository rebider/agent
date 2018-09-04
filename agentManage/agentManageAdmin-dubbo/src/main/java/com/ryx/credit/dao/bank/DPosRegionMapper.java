package com.ryx.credit.dao.bank;

import com.ryx.credit.pojo.admin.bank.DPosRegion;
import com.ryx.credit.pojo.admin.bank.DPosRegionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface DPosRegionMapper {
    long countByExample(DPosRegionExample example);

    int deleteByExample(DPosRegionExample example);

    int insert(DPosRegion record);

    int insertSelective(DPosRegion record);

    List<DPosRegion> selectByExample(DPosRegionExample example);

    List<DPosRegion> findRegionByProvinceName(String provinceName);

    List<DPosRegion> findByPcode(String pCode);

    List<DPosRegion> findByPosRegion(DPosRegion dPosRegions);

    int findCountByCode(String pCode);

    Set<String> queryNationwide();

    Set<DPosRegion> queryCityByCode(String code);

    List<String> queryPosRegionProviceByCity(@Param("codes") List<String> codes);
}