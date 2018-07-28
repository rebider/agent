package com.ryx.credit.activity.dao;

import com.ryx.credit.activity.entity.OAccountAdjust;
import com.ryx.credit.activity.entity.OAccountAdjustExample;
import java.util.List;

public interface OAccountAdjustMapper {
    long countByExample(OAccountAdjustExample example);

    int deleteByExample(OAccountAdjustExample example);

    int insert(OAccountAdjust record);

    int insertSelective(OAccountAdjust record);

    List<OAccountAdjust> selectByExample(OAccountAdjustExample example);

    OAccountAdjust selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OAccountAdjust record);

    int updateByPrimaryKey(OAccountAdjust record);
}