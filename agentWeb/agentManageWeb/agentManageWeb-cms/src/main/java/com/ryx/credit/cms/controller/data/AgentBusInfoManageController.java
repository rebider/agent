/*
package com.ryx.credit.cms.controller.data;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.DataHistory;
import com.ryx.credit.service.agent.AgentBusinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

*/
/**
 * @deprecated: 代理商业务信息管理控制层
 * @author: zhaoyd
 *//*

@RequestMapping("/agentBusInfoManage")
@Controller
public class AgentBusInfoManageController extends BaseController{

    @Autowired
    private AgentBusinfoService agentBusinfoService;

    */
/**
     * 代理商业务信息管理，首页
     * @return
     *//*

    @RequestMapping("/index")
    public String agentBusInfoManage() {
        return "data/agentBusInfo";
    }

    */
/**
     * 代理商业务信息管理，分页查询
     * @return
     *//*

    @ResponseBody
    @RequestMapping("/queryForPage")
    public PageInfo queryAgentBusInfo(HttpServletRequest request, AgentBusInfo agentBusInfo, String time) {
        //查询供应商信息
        return agentBusinfoService.queryAgentBusInfoForPage(pageProcess(request), agentBusInfo, time);
    }

}
*/
