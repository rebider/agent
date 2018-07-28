package com.ryx.credit.activity.dao;

import com.ryx.credit.activity.entity.OAccountAdjustDetail;
import com.ryx.credit.activity.entity.OAccountAdjustDetailExample;
import java.util.List;

public interface OAccountAdjustDetailMapper {
    long countByExample(OAccountAdjustDetailExample example);

    int deleteByExample(OAccountAdjustDetailExample example);

    int insert(OAccountAdjustDetail record);

    int insertSelective(OAccountAdjustDetail record);

    List<OAccountAdjustDetail> selectByExample(OAccountAdjustDetailExample example);

    OAccountAdjustDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OAccountAdjustDetail record);

    int updateByPrimaryKey(OAccountAdjustDetail record);
}