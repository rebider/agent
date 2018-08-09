package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PosRewardTemplate;
import com.ryx.credit.profit.pojo.PosRewardTemplateExample;
import java.util.List;

public interface PosRewardTemplateMapper {
    long countByExample(PosRewardTemplateExample example);

    int deleteByExample(PosRewardTemplateExample example);

    int insert(PosRewardTemplate record);

    int insertSelective(PosRewardTemplate record);

    List<PosRewardTemplate> selectByExample(PosRewardTemplateExample example);

    PosRewardTemplate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PosRewardTemplate record);

    int updateByPrimaryKey(PosRewardTemplate record);

    String selectCreditMonth(String record);
}