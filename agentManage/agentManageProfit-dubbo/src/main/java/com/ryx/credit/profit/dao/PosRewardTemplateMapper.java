package com.ryx.credit.profit.dao;

import java.util.List;

import com.ryx.credit.profit.pojo.PosRewardTemplate;
import com.ryx.credit.profit.pojo.PosRewardTemplateExample;
import org.apache.ibatis.annotations.Param;

public interface PosRewardTemplateMapper {
    long countByExample(PosRewardTemplateExample example);

    int deleteByExample(PosRewardTemplateExample example);

    int deleteByPrimaryKey(String id);

    int insert(PosRewardTemplate record);

    int insertSelective(PosRewardTemplate record);

    List<PosRewardTemplate> selectByExample(PosRewardTemplateExample example);

    PosRewardTemplate selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PosRewardTemplate record, @Param("example") PosRewardTemplateExample example);

    int updateByExample(@Param("record") PosRewardTemplate record, @Param("example") PosRewardTemplateExample example);

    int updateByPrimaryKeySelective(PosRewardTemplate record);

    int updateByPrimaryKey(PosRewardTemplate record);
}