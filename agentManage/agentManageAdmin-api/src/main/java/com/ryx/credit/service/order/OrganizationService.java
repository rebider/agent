package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OorganizationVo;
import com.ryx.credit.pojo.admin.vo.OsupplementVo;

/**
 * @Auther: lrr
 * @Date: 2019/6/12 17:56
 * @Description:
 */
public interface OrganizationService {

    PageInfo organizationList(Page page, Organization organization);

    ResultVO organizationAdd(AgentVo agentVo);

    AgentResult organizationDelete(String id, String user);

}