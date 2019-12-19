package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.SettleAccounts;
import com.ryx.credit.pojo.admin.order.SettleAccountsExample;
import java.util.List;

public interface SettleAccountsMapper {
    long countByExample(SettleAccountsExample example);

    int deleteByExample(SettleAccountsExample example);

    int insert(SettleAccounts record);

    int insertSelective(SettleAccounts record);

    List<SettleAccounts> selectByExample(SettleAccountsExample example);

    SettleAccounts selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SettleAccounts record);

    int updateByPrimaryKey(SettleAccounts record);
}