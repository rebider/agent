package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PDataAdjust;
import com.ryx.credit.profit.pojo.PDataAdjustExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PDataAdjustMapper {
    long countByExample(PDataAdjustExample example);

    int deleteByExample(PDataAdjustExample example);

    int insert(PDataAdjust record);

    int insertSelective(PDataAdjust record);

    List<PDataAdjust> selectByExample(PDataAdjustExample example);

    PDataAdjust selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PDataAdjust record, @Param("example") PDataAdjustExample example);

    int updateByExample(@Param("record") PDataAdjust record, @Param("example") PDataAdjustExample example);

    int updateByPrimaryKeySelective(PDataAdjust record);

    int updateByPrimaryKey(PDataAdjust record);

    List<Map<String,Object>> selectAdjustDetail(Map<String,Object> param);
}