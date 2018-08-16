package com.ryx.credit.service.profit;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;

/**
 * @Author: Zhang Lei
 * @Description: POS分润数据获取服务
 * @Date: 20:19 2018/7/31
 */
public interface IPosProfitDataService {
    AgentResult getPosProfitDate(String settleMonth) throws ProcessException;
}
