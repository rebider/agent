package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.CapitalFlow;

/**
 * Created by RYX on 2019/2/12.
 * 资金流水
 */
public interface CapitalFlowService {

    PageInfo queryCapitalFlowList(CapitalFlow capitalFlow, Page page, String dataRole, Long userId);
}
