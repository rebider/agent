package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.service.PosRewardSDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Controller
@RequestMapping("/postRewardDetail")
public class PosRewardDetailController extends BaseController {
    Logger LOG = LoggerFactory.getLogger(PosRewardDetailController.class);
    @Autowired
    private PosRewardSDetailService posRewardSDetailService;
    @RequestMapping("/toPostRewardDetailList")
    public String toPostRewardDetailList() {
        return "profit/posDetail/postRewardDetailInfo";
    }

    @RequestMapping(value = "rewardDetailList")
    @ResponseBody
    public Object getrewardDetailList(HttpServletRequest request) {
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(),null,null);
        TreeMap map = getRequestParameter(request);

        Set<String> roles = getShiroUser().getRoles();
        if (roles != null && roles.contains("代理商")) {
            map.put("POS_AGENT_ID",getAgentId());
            map.put("POS_AGENT_NAME",getStaffName());
        }

        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        return posRewardSDetailService.getRewardDetailList(map, pageInfo);
    }

    @RequestMapping(value = "profitCount")
    public ModelAndView profitCount(HttpServletRequest request) {
        String agentName=request.getParameter("POS_AGENT_NAME");
        try {
            if (agentName.equals(new String(agentName.getBytes("iso8859-1"),"iso8859-1"))){
                agentName=new String(agentName.getBytes("iso8859-1"),"utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ModelAndView view = new ModelAndView();
        Map<String, Object> param;
        param = getRequestParameter(request);
        param.put("POS_AGENT_ID",request.getParameter("POS_AGENT_ID"));
        param.put("POS_AGENT_NAME",agentName);
        param.put("PROFIT_POS_DATE_START",request.getParameter("PROFIT_POS_DATE_START"));
        param.put("PROFIT_POS_DATE_END",request.getParameter("PROFIT_POS_DATE_END"));
        param.put("POS_MECHANISM_TYPE",request.getParameter("POS_MECHANISM_TYPE"));
        param.put("POS_REMARK",request.getParameter("POS_REMARK"));
        Map<String,Object> map=posRewardSDetailService.profitCount(param);
        view.addObject("totalNum",map.get("TOTALNUM"));
        view.addObject("totalMoney",map.get("TOTALMONEY")==null?0:map.get("TOTALMONEY"));
        view.setViewName("profit/profitCount");
        return view;
    }
}
