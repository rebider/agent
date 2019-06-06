package com.ryx.credit.dao.bank;

import com.ryx.credit.pojo.admin.bank.BankLineNums;
import com.ryx.credit.pojo.admin.bank.BankLineNumsExample;

import java.util.List;

public interface BankLineNumsMapper {
    long countByExample(BankLineNumsExample example);

    int deleteByExample(BankLineNumsExample example);

    int insert(BankLineNums record);

    int insertSelective(BankLineNums record);

    List<BankLineNums> selectByExample(BankLineNumsExample example);

    BankLineNums selectByBankName(String bankName);
}