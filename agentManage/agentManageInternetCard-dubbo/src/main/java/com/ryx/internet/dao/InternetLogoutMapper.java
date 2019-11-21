package com.ryx.internet.dao;

import com.ryx.internet.pojo.InternetLogout;
import com.ryx.internet.pojo.InternetLogoutExample;
import java.util.List;

public interface InternetLogoutMapper {

    long countByExample(InternetLogoutExample example);

    int deleteByExample(InternetLogoutExample example);

    int insert(InternetLogout record);

    int insertSelective(InternetLogout record);

    List<InternetLogout> selectByExample(InternetLogoutExample example);

    InternetLogout selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InternetLogout record);

    int updateByPrimaryKey(InternetLogout record);
}