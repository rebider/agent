package com.ryx.credit.profit.service.impl;

import com.ryx.credit.profit.dao.ProfitBalanceSerialMapper;
import com.ryx.credit.profit.pojo.ProfitBalanceSerial;
import com.ryx.credit.profit.service.ProfitBalanceSerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: zhaodw
 * @Date: 2018/8/29 09:38
 * @Description: 分润金额出款业务接口实现
 */
@Service("profitBalanceSerialServiceImpl")
public class ProfitBalanceSerialServiceImpl implements ProfitBalanceSerialService {

    @Autowired
    private ProfitBalanceSerialMapper profitBalanceSerialMapper;

    @Override
    public void insert(ProfitBalanceSerial profitBalanceSerial) {
        profitBalanceSerialMapper.insert(profitBalanceSerial);
    }

    @Override
    public void updateById(ProfitBalanceSerial profitBalanceSerial) {
        profitBalanceSerialMapper.updateByPrimaryKey(profitBalanceSerial);
    }

    @Override
    public ProfitBalanceSerial getProfitBalanceSerialById(String balanceId) {
       return profitBalanceSerialMapper.selectByPrimaryKey(balanceId);
    }
}
