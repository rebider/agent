package com.ryx.credit.service.agent;


import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;

import java.util.Map;

public interface FreezeRequestDetailService {

    PageInfo agentFreezeDetailList(Map map, Page page);

    PageInfo queryModifyDetail(Map map,Page page);

}
