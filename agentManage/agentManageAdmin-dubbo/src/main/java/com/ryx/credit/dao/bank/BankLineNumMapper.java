package com.ryx.credit.dao.bank;


import com.ryx.credit.pojo.admin.bank.BankLineNum;
import com.ryx.credit.pojo.admin.bank.BankLineNumExample;

import java.util.List;

public interface BankLineNumMapper {
    int countByExample(BankLineNumExample example);

    int deleteByExample(BankLineNumExample example);

    int insert(BankLineNum record);

    int insertSelective(BankLineNum record);

    List<BankLineNum> selectByExample(BankLineNumExample example);
}