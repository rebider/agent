package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OActivity;
import com.ryx.credit.pojo.admin.order.OActivityExample;

import java.util.List;
import java.util.Map;

public interface OActivityMapper {
    long countByExample(OActivityExample example);

    int deleteByExample(OActivityExample example);

    int insert(OActivity record);

    int insertSelective(OActivity record);

    List<OActivity> selectByExample(OActivityExample example);

    OActivity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OActivity record);

    int updateByPrimaryKey(OActivity record);

    List<Map<String,Object>> productActivityOrderBuild(Map par);

    List<OActivity> planChoiseProComAndModel(Map par);
}