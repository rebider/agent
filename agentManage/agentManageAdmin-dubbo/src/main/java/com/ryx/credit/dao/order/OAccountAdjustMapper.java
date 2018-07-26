package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OAccountAdjust;
import com.ryx.credit.pojo.admin.order.OAccountAdjustExample;
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