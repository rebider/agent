package com.ryx.internet.dao;

import com.ryx.internet.pojo.InternetCardImportHistory;
import com.ryx.internet.pojo.InternetCardImportHistoryExample;

import java.util.List;

public interface InternetCardImportHistoryMapper {
    long countByExample(InternetCardImportHistoryExample example);

    int deleteByExample(InternetCardImportHistoryExample example);

    int insert(InternetCardImportHistory record);

    int insertSelective(InternetCardImportHistory record);

    List<InternetCardImportHistory> selectByExample(InternetCardImportHistoryExample example);

    InternetCardImportHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InternetCardImportHistory record);

    int updateByPrimaryKey(InternetCardImportHistory record);
}