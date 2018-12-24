package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.PageInfo;

import java.util.Map;

public interface ProfitSupplyTaxService {
    PageInfo getProfitSupplyTaxList(Map<String, Object> param, PageInfo pageInfo);
}
