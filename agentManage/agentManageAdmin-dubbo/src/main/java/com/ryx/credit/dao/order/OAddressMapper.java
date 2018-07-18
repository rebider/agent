package com.ryx.credit.dao.order;


import com.ryx.credit.pojo.admin.order.OAddress;
import com.ryx.credit.pojo.admin.order.OAddressExample;

import java.util.List;

public interface OAddressMapper {
    int countByExample(OAddressExample example);

    int deleteByExample(OAddressExample example);

    int insert(OAddress record);

    int insertSelective(OAddress record);

    List<OAddress> selectByExample(OAddressExample example);

    OAddress selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OAddress record);

    int updateByPrimaryKey(OAddress record);
}