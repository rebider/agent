package com.ryx.credit.service.bank;

import com.ryx.credit.pojo.admin.bank.BankLineNum;
import com.ryx.credit.pojo.admin.bank.BankLineNums;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by RYX on 2018/7/17.
 */
public interface BankLineNumService {

    List<BankLineNum> findLineByRegionAndBank(String regionCode, BigDecimal bankId);

    List<BankLineNums> findLineByCity(String cityCode,String bankId);

    List<BankLineNums> findLineByProvince(String provinceCode, String bankId);

}
