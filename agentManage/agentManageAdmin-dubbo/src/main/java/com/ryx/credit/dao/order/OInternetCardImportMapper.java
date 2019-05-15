package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OInternetCardImport;
import com.ryx.credit.pojo.admin.order.OInternetCardImportExample;

import java.util.List;

public interface OInternetCardImportMapper {
    long countByExample(OInternetCardImportExample example);

    int deleteByExample(OInternetCardImportExample example);

    int insert(OInternetCardImport record);

    int insertSelective(OInternetCardImport record);

    List<OInternetCardImport> selectByExample(OInternetCardImportExample example);

    OInternetCardImport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OInternetCardImport record);

    int updateByPrimaryKey(OInternetCardImport record);
}