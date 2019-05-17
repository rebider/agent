package com.ryx.credit.dao.order;


import com.ryx.credit.pojo.admin.order.OInternetCard;
import com.ryx.credit.pojo.admin.order.OInternetCardExample;

import java.util.List;

public interface OInternetCardMapper {
    long countByExample(OInternetCardExample example);

    int deleteByExample(OInternetCardExample example);

    int insert(OInternetCard record);

    int insertSelective(OInternetCard record);

    List<OInternetCard> selectByExample(OInternetCardExample example);

    OInternetCard selectByPrimaryKey(String iccidNum);

    int updateByPrimaryKeySelective(OInternetCard record);

    int updateByPrimaryKey(OInternetCard record);
}