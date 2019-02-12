package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.CapitalChangeApply;

/**
 * Created by RYX on 2019/2/12.
 * 保证金申请
 */
public interface CapitalChangeApplyService {

    PageInfo queryCapitalChangeList(CapitalChangeApply capitalChangeApply, Page page, String dataRole, Long userId);

    CapitalChangeApply queryCapitalChangeById(String capitalId);
}
