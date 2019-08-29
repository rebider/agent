package com.ryx.internet.dao;

import com.ryx.internet.pojo.OInternetCardPostpone;
import com.ryx.internet.pojo.OInternetCardPostponeExample;

import java.util.List;

public interface OInternetCardPostponeMapper {
    long countByExample(OInternetCardPostponeExample example);

    int deleteByExample(OInternetCardPostponeExample example);

    int insert(OInternetCardPostpone record);

    int insertSelective(OInternetCardPostpone record);

    List<OInternetCardPostpone> selectByExample(OInternetCardPostponeExample example);

    OInternetCardPostpone selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OInternetCardPostpone record);

    int updateByPrimaryKey(OInternetCardPostpone record);
}