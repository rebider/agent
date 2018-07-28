package com.ryx.credit.activity.dao;

import com.ryx.credit.activity.entity.OReturnOrderRel;
import com.ryx.credit.activity.entity.OReturnOrderRelExample;
import java.util.List;

public interface OReturnOrderRelMapper {
    long countByExample(OReturnOrderRelExample example);

    int deleteByExample(OReturnOrderRelExample example);

    int insert(OReturnOrderRel record);

    int insertSelective(OReturnOrderRel record);

    List<OReturnOrderRel> selectByExample(OReturnOrderRelExample example);
}