package com.ryx.credit.dao.order;


import com.ryx.credit.pojo.admin.order.OProduct;
import com.ryx.credit.pojo.admin.order.OProductExample;

import java.util.List;
import java.util.Map;

public interface OProductMapper {
    int countByExample(OProductExample example);

    int deleteByExample(OProductExample example);

    int insert(OProduct record);

    int insertSelective(OProduct record);

    List<OProduct> selectByExample(OProductExample example);

    OProduct selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OProduct record);

    int updateByPrimaryKey(OProduct record);

    List<Map> queryGroupByProCodeList(Map par);

    int queryGroupByProCodeListCount(Map par);
}