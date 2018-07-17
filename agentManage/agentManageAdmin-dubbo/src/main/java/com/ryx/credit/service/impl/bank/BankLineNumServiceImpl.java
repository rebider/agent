package com.ryx.credit.service.impl.bank;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.bank.BankLineNumMapper;
import com.ryx.credit.pojo.admin.bank.BankLineNum;
import com.ryx.credit.pojo.admin.bank.BankLineNumExample;
import com.ryx.credit.service.bank.BankLineNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by RYX on 2018/7/17.
 */
@Service("bankLineNumService")
public class BankLineNumServiceImpl implements BankLineNumService {

    @Autowired
    private BankLineNumMapper bankLineNumMapper;

    /**
     * 根据城市代码和银行代码
     * @param regionCode
     * @param bankId
     * @return
     */
    @Override
    public List<BankLineNum> findLineByRegionAndBank(String regionCode,BigDecimal bankId){
        if(StringUtils.isBlank(regionCode)){
            return null;
        }
        BankLineNumExample example = new BankLineNumExample();
        BankLineNumExample.Criteria criteria = example.createCriteria();
        criteria.andRegionCodeEqualTo(regionCode);
        criteria.andBankIdEqualTo(bankId);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<BankLineNum> bankLineNums = bankLineNumMapper.selectByExample(example);
        return bankLineNums;
    }
}
