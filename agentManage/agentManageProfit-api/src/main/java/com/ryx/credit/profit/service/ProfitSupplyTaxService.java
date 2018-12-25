package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.PageInfo;

import java.util.Map;

public interface ProfitSupplyTaxService {
    PageInfo getProfitSupplyTaxList(Map<String, Object> param, PageInfo pageInfo);

    /**
     * @Author: Zhang Lei
     * @Description: 代理商补税点计算
     * @Date: 0:12 2018/12/21
     */
    void taxSupplyComputer(Map<String,Object> params);
}
