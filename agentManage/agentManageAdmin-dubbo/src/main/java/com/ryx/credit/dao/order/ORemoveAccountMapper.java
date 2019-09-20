package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.ORemoveAccount;
import com.ryx.credit.pojo.admin.order.ORemoveAccountExample;

import java.util.List;

public interface ORemoveAccountMapper {
    long countByExample(ORemoveAccountExample example);

    int deleteByExample(ORemoveAccountExample example);

    int insert(ORemoveAccount record);

    int insertSelective(ORemoveAccount record);

    List<ORemoveAccount> selectByExample(ORemoveAccountExample example);

    ORemoveAccount selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ORemoveAccount record);

    int updateByPrimaryKey(ORemoveAccount record);
}