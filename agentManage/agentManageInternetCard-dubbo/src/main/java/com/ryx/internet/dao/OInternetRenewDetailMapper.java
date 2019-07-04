package com.ryx.internet.dao;


import com.ryx.internet.pojo.OInternetRenewDetail;
import com.ryx.internet.pojo.OInternetRenewDetailExample;

import java.util.List;

public interface OInternetRenewDetailMapper {
    long countByExample(OInternetRenewDetailExample example);

    int deleteByExample(OInternetRenewDetailExample example);

    int insert(OInternetRenewDetail record);

    int insertSelective(OInternetRenewDetail record);

    List<OInternetRenewDetail> selectByExample(OInternetRenewDetailExample example);

    OInternetRenewDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OInternetRenewDetail record);

    int updateByPrimaryKey(OInternetRenewDetail record);
}