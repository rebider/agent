package com.ryx.credit.dao.bank;


import com.ryx.credit.pojo.admin.bank.BankRegion;
import com.ryx.credit.pojo.admin.bank.BankRegionExample;

import java.util.List;

public interface BankRegionMapper {
    int countByExample(BankRegionExample example);

    int deleteByExample(BankRegionExample example);

    int insert(BankRegion record);

    int insertSelective(BankRegion record);

    List<BankRegion> selectByExample(BankRegionExample example);
}