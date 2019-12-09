package com.ryx.internet.dao;

import com.ryx.internet.pojo.InternetLogoutDetail;
import com.ryx.internet.pojo.InternetLogoutDetailExample;

import java.util.List;
import java.util.Map;

public interface InternetLogoutDetailMapper {

    long countByExample(InternetLogoutDetailExample example);

    int deleteByExample(InternetLogoutDetailExample example);

    int insert(InternetLogoutDetail record);

    int insertSelective(InternetLogoutDetail record);

    List<InternetLogoutDetail> selectByExample(InternetLogoutDetailExample example);

    InternetLogoutDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InternetLogoutDetail record);

    int updateByPrimaryKey(InternetLogoutDetail record);

    List<Map<String,Object>> internetCardLogoutDetailList(Map<String,Object> map);

    int internetCardLogoutDetailCount(Map<String,Object> map);

}