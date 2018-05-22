package com.ryx.credit.activity.dao;

import com.ryx.credit.activity.entity.ActRuTask;
import com.ryx.credit.activity.entity.ActRuTaskExample;
import java.util.List;

public interface ActRuTaskMapper {
    int countByExample(ActRuTaskExample example);

    int deleteByExample(ActRuTaskExample example);

    int insert(ActRuTask record);

    int insertSelective(ActRuTask record);

    List<ActRuTask> selectByExample(ActRuTaskExample example);

    ActRuTask selectByPrimaryKey(Object id);

    int updateByPrimaryKeySelective(ActRuTask record);

    int updateByPrimaryKey(ActRuTask record);
}