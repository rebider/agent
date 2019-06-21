package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.PageInfo;

import java.util.Map;

public interface IInvoiceSumService {
    PageInfo selectByMap(PageInfo pageInfo, Map<String,String> param);
}
