package com.ryx.credit.service.bank;

import com.ryx.credit.pojo.admin.bank.BankLineNums;
import java.util.List;

/**
 * Created by RYX on 2018/7/17.
 */
public interface BankLineNumService {

    List<BankLineNums> findLineByCity(String cityCode,String bankId);

    List<BankLineNums> findLineByProvince(String provinceCode, String bankId);

}
