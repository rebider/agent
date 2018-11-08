package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.ProfitSupplyTaxMapper;
import com.ryx.credit.profit.service.ProfitSupplyTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("profitSupplyTaxService")
public class ProfitSupplyTaxServiceImpl implements ProfitSupplyTaxService {
    @Autowired
    private ProfitSupplyTaxMapper profitSupplyTaxMapper;
    @Override
    public PageInfo getProfitSupplyTaxList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = profitSupplyTaxMapper.getProfitSupplyTaxCount(param);
        List<Map<String, Object>> list = profitSupplyTaxMapper.getProfitSupplyTaxList(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        System.out.println("查询============================================" + JSONObject.toJSON(list));
        return pageInfo;
    }
}
