package com.ryx.credit.service.bank;

import com.ryx.credit.pojo.admin.bank.EtbSysCardBinNo;
import com.ryx.credit.util.ProcessorException;

/**
 * Created by RYX on 2018/7/17.
 */
public interface EtbSyscardinfoService {

    EtbSysCardBinNo findCardBinByCardNo(String cardNo) throws ProcessorException;
}
