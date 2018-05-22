package com.ryx.credit.activity.dao;

import com.ryx.credit.activity.entity.ActHiVarinst;
import com.ryx.credit.activity.entity.ActHiVarinstExample;
import java.util.List;

public interface ActHiVarinstMapper {
    int countByExample(ActHiVarinstExample example);

    int deleteByExample(ActHiVarinstExample example);

    int insert(ActHiVarinst record);

    int insertSelective(ActHiVarinst record);

    List<ActHiVarinst> selectByExample(ActHiVarinstExample example);

    ActHiVarinst selectByPrimaryKey(Object id);

    int updateByPrimaryKeySelective(ActHiVarinst record);

    int updateByPrimaryKey(ActHiVarinst record);
}