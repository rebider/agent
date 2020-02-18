package com.ryx.jobOrder.dao;

import com.ryx.jobOrder.pojo.JoKeyManage;
import com.ryx.jobOrder.pojo.JoKeyManageExample;
import java.util.List;

public interface JoKeyManageMapper {
    long countByExample(JoKeyManageExample example);

    int deleteByExample(JoKeyManageExample example);

    int insert(JoKeyManage record);

    int insertSelective(JoKeyManage record);

    List<JoKeyManage> selectByExample(JoKeyManageExample example);

    JoKeyManage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JoKeyManage record);

    int updateByPrimaryKey(JoKeyManage record);
}