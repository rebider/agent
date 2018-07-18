package com.ryx.credit.service.bank;

import com.ryx.credit.pojo.admin.bank.BankLineNum;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by RYX on 2018/7/17.
 */
public interface BankLineNumService {

    List<BankLineNum> findLineByRegionAndBank(String regionCode, BigDecimal bankId);

}
