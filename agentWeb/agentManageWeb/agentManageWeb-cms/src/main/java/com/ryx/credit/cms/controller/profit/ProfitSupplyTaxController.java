package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.service.ProfitSupplyTaxService;
import com.ryx.credit.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Controller
@RequestMapping("/profitSupplyTaxController")
public class ProfitSupplyTaxController extends BaseController {
    @Autowired
    private ProfitSupplyTaxService profitSupplyTaxService;
    @Autowired
    private IUserService userService;

    private static final String AGENT = "agent";

    private static final Logger logger = LoggerFactory.getLogger(ProfitSupplyTaxController.class);

    @RequestMapping(value = {"SupplyTaxPage"})
    public String profitBySupplyTax(){
        return "profit/supplyTax/profitSupplyTax";
    }

    /**
     * @Description: 补税点明细list
     * @author: chenliang
     * @date: 2018/11/6
     */
    @RequestMapping(value = "getProfitSupplyTaxList")
    @ResponseBody
    public Object getProfitSupplyTaxList(HttpServletRequest request,String sign) {
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(),null,null);
        TreeMap map = getRequestParameter(request);

        List<Map<String, Object>> list = userService.orgCode(getUserId());
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        Map<String, Object> mapList = list.get(0);
        if(mapList.get("ORGANIZATIONCODE").toString().contains("south") || mapList.get("ORGANIZATIONCODE").toString().contains("north")){
            map.put("orgId",mapList.get("ORGID").toString());  // 省区
        }else if(Objects.equals("south", mapList.get("ORGANIZATIONCODE").toString() )|| Objects.equals("north", mapList.get("ORGANIZATIONCODE").toString())){
            map.put("area",mapList.get("ORGID").toString());   // 大区
        }else if(Objects.equals(mapList.get("ORGANIZATIONCODE"), AGENT)){  // 代理商
            map.put("SUPPLY_TAX_AGENT_ID",getAgentId());
        }
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        map.put("SUPPLY_TAX_PLATFORM",sign);
        return profitSupplyTaxService.getProfitSupplyTaxList(map, pageInfo);
    }

    @RequestMapping(value = "profitCount")
    public ModelAndView profitCount(HttpServletRequest request) {
        String agentName=request.getParameter("SUPPLY_TAX_AGENT_NAME");
        try {
            if (agentName.equals(new String(agentName.getBytes("iso8859-1"),"iso8859-1"))){
                agentName=new String(agentName.getBytes("iso8859-1"),"utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ModelAndView view = new ModelAndView();
        Map<String, Object> param ;
        param = getRequestParameter(request);
        param.put("SUPPLY_TAX_PLATFORM","1".equals(request.getParameter("temp"))?"01":"02");
        param.put("SUPPLY_TAX_AGENT_NAME",agentName);
        boolean isQuerySubordinate="1".equals(request.getParameter("flage"));
        Map<String, Object> count = profitSupplyTaxService.profitCount(param, isQuerySubordinate);
        view.addObject("totalNum",count.get("TOTALNUM"));
        view.addObject("totalMoney",count.get("TOTALMONEY")==null?0:count.get("TOTALMONEY"));
        view.setViewName("profit/profitCount");
        return view;
    }
}


