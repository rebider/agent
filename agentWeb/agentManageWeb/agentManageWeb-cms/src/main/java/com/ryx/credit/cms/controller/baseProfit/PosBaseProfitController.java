package com.ryx.credit.cms.controller.baseProfit;

import com.ryx.credit.cms.controller.BaseController;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;

import com.ryx.credit.profit.service.TransProfitDetailService;
import com.ryx.credit.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * chenliang
 * 2019/2/26
 */
@Controller
@RequestMapping("/posBaseProfit")
public class PosBaseProfitController extends BaseController {
    @Autowired
    private TransProfitDetailService transProfitDetailService;
    @Autowired
    private IUserService userService;

    private static final String AGENT = "agent";


    private static Logger Log = LoggerFactory.getLogger(PosBaseProfitController.class);

    @RequestMapping("/posBaseProfitPage")
    public String posBaseProfitPage(){
        Log.info("加载POS基础分润页面...");
        return "profit/baseProfit/posBaseProfit";
    }

    @RequestMapping("/mobileBaseProfitPage")
    public String mobileBaseProfitPage(){
        Log.info("加载手刷基础分润页面...");
        return "profit/baseProfit/mobileBaseProfit";
    }



    /**
     * 获取数据列表
     * @param request
     * @return
     */
    @RequestMapping("/getBaseProfitList")
    @ResponseBody
    public PageInfo getBaseProfitList(HttpServletRequest request,String type) {
        //获取传入参数
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(),null,null);
        TreeMap map = getRequestParameter(request);
        if(!"".equals(map.get("BUS_NAME"))){
            if((map.get("BUS_CODE")==null||"".equals(map.get("BUS_CODE")))){
                map.put("BUS_CODE",map.get("BUS_NAME"));
            }else{
                if(map.get("BUS_CODE").equals(map.get("BUS_NAME"))){
                    map.put("BUS_CODE",map.get("BUS_NAME"));
                }else{
                    map.put("BUS_CODE",map.get("BUS_CODE")+""+map.get("BUS_NAME"));
                }
            }
        }

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
            map.put("AGENT_ID",getAgentId());
        }

        map.put("SOURCE_INFO", type);
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        return  transProfitDetailService.posBaseProfitList(map,pageInfo);
    }

    /**
     * 查询平台
     * @param type
     * @return
     */
    @RequestMapping("/queryBusNum")
    @ResponseBody
    public List<Map<String,Object>> queryBusNum(String type) {
         List<Map<String,Object>> list = transProfitDetailService.queryBusNum(type);
        Map<String,Object> map = new HashMap<>();
        map.put("PLATFORM_NUM","");
        map.put("PLATFORM_NAME","---------请选择---------");
         list.add(0,map);
        return list;
    }
}
