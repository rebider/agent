package com.ryx.credit.dao.order;



import com.ryx.credit.pojo.admin.order.OSubOrderActivity;
import com.ryx.credit.pojo.admin.order.OSubOrderActivityExample;

import java.util.List;

public interface OSubOrderActivityMapper {
    long countByExample(OSubOrderActivityExample example);

    int deleteByExample(OSubOrderActivityExample example);

    int insert(OSubOrderActivity record);

    int insertSelective(OSubOrderActivity record);

    List<OSubOrderActivity> selectByExample(OSubOrderActivityExample example);

    OSubOrderActivity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OSubOrderActivity record);

    int updateByPrimaryKey(OSubOrderActivity record);
}