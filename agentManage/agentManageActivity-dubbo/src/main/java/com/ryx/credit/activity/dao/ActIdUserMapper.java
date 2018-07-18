package com.ryx.credit.activity.dao;

import com.ryx.credit.activity.entity.ActIdUser;
import com.ryx.credit.activity.entity.ActIdUserExample;
import java.util.List;

public interface ActIdUserMapper {
    int countByExample(ActIdUserExample example);

    int deleteByExample(ActIdUserExample example);

    int insert(ActIdUser record);

    int insertSelective(ActIdUser record);

    List<ActIdUser> selectByExample(ActIdUserExample example);

    ActIdUser selectByPrimaryKey(Object id);

    int updateByPrimaryKeySelective(ActIdUser record);

    int updateByPrimaryKey(ActIdUser record);

    List<ActIdUser> selectByTaskId(Object taskId);
}