package com.ryx.credit.dao.order;


import com.ryx.credit.pojo.admin.order.OActivity;
import com.ryx.credit.pojo.admin.order.OActivityExample;

import java.util.List;

public interface OActivityMapper {
    int countByExample(OActivityExample example);

    int deleteByExample(OActivityExample example);

    int insert(OActivity record);

    int insertSelective(OActivity record);

    List<OActivity> selectByExample(OActivityExample example);

    OActivity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OActivity record);

    int updateByPrimaryKey(OActivity record);
}