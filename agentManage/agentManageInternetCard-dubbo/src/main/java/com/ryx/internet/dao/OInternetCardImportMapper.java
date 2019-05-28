package com.ryx.internet.dao;

import com.ryx.internet.pojo.OInternetCardImport;
import com.ryx.internet.pojo.OInternetCardImportExample;

import java.util.List;
import java.util.Map;

public interface OInternetCardImportMapper {
    long countByExample(OInternetCardImportExample example);

    int deleteByExample(OInternetCardImportExample example);

    int insert(OInternetCardImport record);

    int insertSelective(OInternetCardImport record);

    List<OInternetCardImport> selectByExample(OInternetCardImportExample example);

    OInternetCardImport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OInternetCardImport record);

    int updateByPrimaryKey(OInternetCardImport record);

    List<String> selectBatchNum(Map map);
}