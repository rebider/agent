package com.ryx.credit.cms.controller;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.CCondition;
import com.ryx.credit.pojo.admin.CStrategy;
import com.ryx.credit.pojo.admin.CStrategy;
import com.ryx.credit.service.*;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * RuleController
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/6/1
 * @Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/strategy")
public class StrategyController extends BaseController {
    protected Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ICConditionService cConditionService;
    @Autowired
    private ICConditionValueService cConditionValueService;
    @Autowired
    private ICRuleConditionService cRuleConditionService;
    @Autowired
    private ICRulePlatformService cRulePlatformService;
    @Autowired
    private ICRuleService cRuleService;
    @Autowired
    private ICStrategyRuleService cStrategyRuleService;
    @Autowired
    private ICStrategyService cStrategyService;

    @RequestMapping("/strategyList")
    @ResponseBody
    public Object strategyList(final HttpServletRequest request,
                                     final HttpServletResponse response,
                                     CStrategy cstrategy) {
        //返回对象
        TreeMap<String, String> treeMap = getRequestParameter(request);
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), "seq", "desc");
        Map map = cStrategyService.configExample(page, cstrategy);
        List<CStrategy> cstrategys = (List<CStrategy>) map.get("list");
        for (CStrategy cstrategy1 : cstrategys) {
            Map<Integer, String> propertyTypeMap = getTypeMap();
            for (Map.Entry entry : propertyTypeMap.entrySet()) {
                if (entry.getKey().toString().equals(cstrategy1.getType().toString())) {
                    cstrategy1.setTypeName(entry.getValue().toString());
                }
            }
        }
        page = (Page) map.get("page");
        pageInfo.setTotal(page.getCount());
        pageInfo.setRows(cstrategys);
        pageInfo.setFrom(page.getBegin());
        return pageInfo;
    }

    @RequestMapping("/typeList")
    @ResponseBody
    public Object conditionList(final HttpServletRequest request,
                             final HttpServletResponse response) {
        Map<Integer, String> map = getTypeMap();
        ArrayList arrayList = new ArrayList();
        for(Map.Entry entry:map.entrySet()){
            ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap();
            concurrentHashMap.put("type",entry.getKey().toString());
            concurrentHashMap.put("typeName",entry.getValue().toString());
            arrayList.add(concurrentHashMap);
        }
        JSONArray jsonArray = JSONArray.fromObject(arrayList);
        return jsonArray.toString();
    }


    @RequestMapping("/toList")
    public Object toList(final HttpServletRequest request,
                         final HttpServletResponse response
    ) {
        return "strategy/strategyList";
    }

    @RequestMapping("/toAdd")
    public Object toAdd(final HttpServletRequest request,
                        final HttpServletResponse response
    ) {
        return "strategy/addStrategy";
    }

    @RequestMapping("/addOrUpdateStrategy")
    @ResponseBody
    public Object addOrUpdatestrategy(final HttpServletRequest request,
                                            final HttpServletResponse response) {
        TreeMap<String, String> treeMap = getRequestParameter(request);
        try {
            if (treeMap.get("value")==null) {
                return renderSuccess("价格不能为空，请重新定义！");
            }
            CStrategy strategy = new CStrategy();
            strategy.setType(new BigDecimal(treeMap.get("type")));
            strategy.setValue(treeMap.get("value"));
            Map map = cStrategyService.configExample(pageProcessAll(request, 5), strategy);
            List<CStrategy> cstrategys = (List<CStrategy>) map.get("list");
            for(CStrategy cstrategy :cstrategys){
                if(StringUtils.isNotBlank(treeMap.get("id"))){
                    if(!cstrategy.getId().toString().equals(treeMap.get("id"))){
                        return renderSuccess("该规则的设置已经存在，请重新定义！");
                    }
                }else{
                    return renderSuccess("该规则的设置已经存在，请重新定义！");
                }
            }
            strategy.setName(treeMap.get("name"));
            strategy.setCreateTime(new Date());
            if (StringUtils.isNotBlank(treeMap.get("id"))) {
                CStrategy cstrategy = cStrategyService.selectByPrimaryKey(new BigDecimal(treeMap.get("id")));
                if (cstrategy == null) {
                    return renderSuccess("该规则的设置不存在，请重新定义！");
                } else {
                    strategy.setId(new BigDecimal(treeMap.get("id")));
                    cStrategyService.updateByPrimaryKeySelective(strategy);
                }
            } else {
                cStrategyService.insert(strategy);
            }
        } catch (Exception e) {
            logger.error("[module:strategy] [action:strategyAction] [step:save] ", e);
        }
        return renderSuccess("成功！");
    }

    @RequestMapping("/toUpdate")
    public Object toUpdate(final HttpServletRequest request,
                           final HttpServletResponse response,
                           BigDecimal id) {
        CStrategy cstrategy = cStrategyService.selectByPrimaryKey(id);
        request.setAttribute("cStrategy", cstrategy);
        return "strategy/updateStrategy";
    }



    public static Map<Integer, String> getTypeMap() {
        Map<Integer, String> hashMap = new HashMap<Integer, String>();
        hashMap.put(1, "折扣");
        hashMap.put(2, "定价");
        hashMap.put(3, "立减");
        return hashMap;
    }



}
