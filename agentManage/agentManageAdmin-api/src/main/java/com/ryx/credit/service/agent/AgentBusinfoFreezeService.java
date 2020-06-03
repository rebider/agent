package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;

import java.util.Map;

/**
 * @Auther: lrr
 * @Date: 2020/6/3 15:13
 * @Description:业务冻结
 */
public interface AgentBusinfoFreezeService {

    PageInfo abfreezeList(Page page, Map map);
}