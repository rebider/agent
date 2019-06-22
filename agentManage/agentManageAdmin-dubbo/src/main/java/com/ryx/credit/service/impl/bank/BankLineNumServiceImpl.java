package com.ryx.credit.service.impl.bank;

import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.bank.BankLineNumsMapper;
import com.ryx.credit.pojo.admin.bank.BankLineNums;
import com.ryx.credit.pojo.admin.bank.BankLineNumsExample;
import com.ryx.credit.service.bank.BankLineNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by RYX on 2018/7/17.
 */
@Service("bankLineNumService")
public class BankLineNumServiceImpl implements BankLineNumService {

    @Autowired
    private BankLineNumsMapper bankLineNumsMapper;

    /**
     * 根据市代码和银行代码
     * @param cityCode
     * @param bankId
     * @return
     */
    @Override
    public List<BankLineNums> findLineByCity(String cityCode,String bankId){
        if(StringUtils.isBlank(cityCode) || StringUtils.isBlank(bankId) ){
            return null;
        }
        BankLineNumsExample example = new BankLineNumsExample();
        BankLineNumsExample.Criteria criteria = example.createCriteria();
        criteria.andCityidEqualTo(cityCode);
        criteria.andBankidEqualTo(bankId);
        List<BankLineNums> bankLineNums = bankLineNumsMapper.selectByExample(example);
        return bankLineNums;
    }

    /**
     * 根据省代码和银行代码
     * @param provinceCode
     * @param bankId
     * @return
     */
    @Override
    public List<BankLineNums> findLineByProvince(String provinceCode,String bankId){
        if(StringUtils.isBlank(provinceCode) || StringUtils.isBlank(bankId) ){
            return null;
        }
        BankLineNumsExample example = new BankLineNumsExample();
        BankLineNumsExample.Criteria criteria = example.createCriteria();
        criteria.andProvinceidEqualTo(provinceCode);
        criteria.andBankidEqualTo(bankId);
        List<BankLineNums> bankLineNums = bankLineNumsMapper.selectByExample(example);
        return bankLineNums;
    }

}
