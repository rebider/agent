package com.ryx.credit.machine.dao;

import com.ryx.credit.machine.entity.LmsUser;
import com.ryx.credit.machine.entity.LmsUserExample;
import java.util.List;
import java.util.Map;

public interface LmsUserMapper {
    long countByExample(LmsUserExample example);

    int deleteByExample(LmsUserExample example);

    int insert(LmsUser record);

    int insertSelective(LmsUser record);

    List<LmsUser> selectByExample(LmsUserExample example);

    LmsUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LmsUser record);

    int updateByPrimaryKey(LmsUser record);

    List<Map<String, String>> selectAllLmsUser();

    LmsUser selectByLogin(String loginName);

    List<String> selectByBusNum(String busNum);
}