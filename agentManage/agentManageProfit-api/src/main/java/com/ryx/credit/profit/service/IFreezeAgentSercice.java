package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.PageInfo;

import java.util.Map;

public interface IFreezeAgentSercice {
    PageInfo getselectFreezeDate(Map<String, Object> param, PageInfo pageInfo);

}
