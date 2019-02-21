package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Capital;
import com.ryx.credit.pojo.admin.agent.CapitalFlow;

import java.math.BigDecimal;

/**
 * Created by RYX on 2019/2/12.
 * 资金流水
 */
public interface CapitalFlowService {

    PageInfo queryCapitalFlowList(CapitalFlow capitalFlow, Page page, String dataRole, Long userId);

    void insertCapitalFlow(Capital capital, BigDecimal beforeAmount, String srcId, String remark)throws Exception;
}
