package com.ryx.credit.cms.controller;

import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.*;
import com.ryx.credit.service.*;
import net.sf.json.JSONArray;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
@RequestMapping("/rule")
public class RuleController extends BaseController {
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
    @Autowired
    private ICRulePlatformService icRulePlatformService;

    @RequestMapping("/ruleList")
    @ResponseBody
    public Object ruleList(final HttpServletRequest request,
                           final HttpServletResponse response,
                           CRule cRule) {
        //返回对象
        TreeMap<String, String> treeMap = getRequestParameter(request);
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), "seq", "desc");
        Map map = cRuleService.configExample(page, cRule);
        List<CRule> cRuleList = (List<CRule>) map.get("list");
        Map map_product = icRulePlatformService.configExample(pageProcessAll(1000), new CRulePlatform());
        List<CRulePlatform> productList = (List<CRulePlatform>) map_product.get("list");
        for (CRule cRule1 : cRuleList) {
            for (CRulePlatform cRulePlatform : productList) {
                if (cRulePlatform.getId().toString().equals(cRule1.getProductId().toString())) {
                    cRule1.setProductName(cRulePlatform.getName().toString());
                }
            }
        }
        page = (Page) map.get("page");
        pageInfo.setTotal(page.getCount());
        pageInfo.setRows(cRuleList);
        pageInfo.setFrom(page.getBegin());
        return pageInfo;
    }


    @RequestMapping("/productList")
    @ResponseBody
    public Object productList(final HttpServletRequest request,
                              final HttpServletResponse response) {
        //返回对象
        TreeMap<String, String> treeMap = getRequestParameter(request);
        Map map = icRulePlatformService.configExample(pageProcessAll(1000), new CRulePlatform());
        List<CRulePlatform> list = (List<CRulePlatform>) map.get("list");
        if (list.size() > 0) {
            List<ConcurrentHashMap> concurrentHashMapArrayList = new ArrayList<>();
            for (CRulePlatform cRulePlatform : list) {
                ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<>();
                hashMap.put("productId", cRulePlatform.getId().toString());
                hashMap.put("productName", cRulePlatform.getName().toString());
                concurrentHashMapArrayList.add(hashMap);
            }
            JSONArray jsonArray = JSONArray.fromObject(concurrentHashMapArrayList);
            return jsonArray.toString();
        }
        return null;
    }

    @RequestMapping("/statusList")
    @ResponseBody
    public Object statusList(final HttpServletRequest request,
                             final HttpServletResponse response) {
        JSONArray jsonArray = JSONArray.fromObject(getStatusList());
        return jsonArray.toString();
    }

    @RequestMapping("/toList")
    public Object toList(final HttpServletRequest request,
                         final HttpServletResponse response
    ) {
        return "rule/ruleList";
    }

    @RequestMapping("/toAdd")
    public Object toAdd(final HttpServletRequest request,
                        final HttpServletResponse response
    ) {
        return "rule/addRule";
    }

    @RequestMapping("/addRule")
    @ResponseBody
    public Object addRule(final HttpServletRequest request,
                          final HttpServletResponse response) {
        TreeMap<String, String> treeMap = getRequestParameter(request);
        try {
            CRule cRule = new CRule();
            cRule.setName(treeMap.get("name"));
            cRule.setProductId(new BigDecimal(treeMap.get("productId")));
            Map map = cRuleService.configExample(pageProcessAll(request, 2), cRule);
            List<CRule> list = (List<CRule>) map.get("list");
            if (list != null && list.size() > 0) {
                return renderSuccess("该Id的产品已经有该命名的规则，请重新定义规则名称！");
            }
            cRule.setValidStartTime(DateUtil.format(treeMap.get("validStartTime"), "yyyy-MM-dd HH:mm:ss"));
            cRule.setValidEndTime(DateUtil.format(treeMap.get("validEndTime"), "yyyy-MM-dd HH:mm:ss"));
            cRule.setCreateTime(new Date());
            cRule.setStatus(new BigDecimal(treeMap.get("status")));
            cRule.setPriority(new BigDecimal(treeMap.get("priority")));
            cRuleService.insert(cRule);
        } catch (Exception e) {
            logger.error("addRule error", e);
        }
        return renderSuccess("添加成功！");
    }

    @RequestMapping("/toUpdate")
    public Object toUpdate(final HttpServletRequest request,
                           final HttpServletResponse response,
                           BigDecimal id) {
        CRule cRule = cRuleService.selectByPrimaryKey(id);
        request.setAttribute("cRule", cRule);
        return "rule/updateRule";
    }

    @RequestMapping("/updateRule")
    @ResponseBody
    public Object updateRule(final HttpServletRequest request,
                             final HttpServletResponse response) {
        TreeMap<String, String> treeMap = getRequestParameter(request);
        try {
            CRule cRule = new CRule();
            cRule.setName(treeMap.get("name"));
            cRule.setProductId(new BigDecimal(treeMap.get("productId")));
            Map map = cRuleService.configExample(pageProcessAll(request, 1), cRule);
            List<CRule> list = (List<CRule>) map.get("list");
            if (list != null && list.size() > 0) {
                for (CRule rule : list) {
                    if (!rule.getId().equals(new BigDecimal(treeMap.get("id")))) {        //修改时，判断非本身的数据是否重复
                        return renderSuccess("该Id的产品已经有该命名的规则，请重新定义规则名称！");
                    }
                }
            }

            /**
             * 查询规则关联条件，策略是否存在
             */
            CRuleCondition cRuleCondition = new CRuleCondition();
            cRuleCondition.setRuleId(new BigDecimal(treeMap.get("id")));
            Map cRuleConditionMap = cRuleConditionService.configExample(pageProcessAll(request, 1), cRuleCondition);
            List<CRuleCondition> cRuleConditions = (List<CRuleCondition>) cRuleConditionMap.get("list");
            if (cRuleConditions == null || cRuleConditions.size() == 0) {
                return renderSuccess("该规则还没有关联条件，请关联条件后执行修改！");
            }
            CStrategyRule cStrategyRule = new CStrategyRule();
            cStrategyRule.setRuleId(new BigDecimal(treeMap.get("id")));
            Map cStrategyRuleMap = cStrategyRuleService.configExample(pageProcessAll(request, 1), cStrategyRule);
            List<CStrategyRule> strategyRulelist = (List<CStrategyRule>) cStrategyRuleMap.get("list");
            if (strategyRulelist == null || strategyRulelist.size() == 0) {
                return renderSuccess("该规则还没有关联策略，请关联策略后执行修改！");
            }
            cRule.setId(new BigDecimal(treeMap.get("id")));
            cRule.setValidStartTime(DateUtil.format(treeMap.get("validStartTime"), "yyyy-MM-dd HH:mm:ss"));
            cRule.setValidEndTime(DateUtil.format(treeMap.get("validEndTime"), "yyyy-MM-dd HH:mm:ss"));
            cRule.setStatus(new BigDecimal(treeMap.get("status")));
            cRule.setPriority(new BigDecimal(treeMap.get("priority")));
            cRuleService.updateByPrimaryKeySelective(cRule);
        } catch (Exception e) {
            logger.error("updateRule error", e);
        }
        return renderSuccess("修改成功！");
    }

    @RequestMapping("/deleteRule")
    @ResponseBody
    public Object deleteRule(final HttpServletRequest request,
                             final HttpServletResponse response) {
        TreeMap<String, String> treeMap = getRequestParameter(request);
        try {
            CRule cRule = new CRule();
            cRule.setId(new BigDecimal(treeMap.get("id")));
        } catch (Exception e) {
            logger.error("deleteRule error", e);
        }
        return renderSuccess("删除成功！");
    }

    @RequestMapping("/toUpdateCondition")
    public Object toUpdateCondition(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    BigDecimal id) {
        CRule cRule = cRuleService.selectByPrimaryKey(id);
        request.setAttribute("cRule", cRule);
        return "rule/updateCondition";
    }

    @RequestMapping("/toUpdateStrategy")
    public Object toUpdateStrategy(final HttpServletRequest request,
                                   final HttpServletResponse response,
                                   BigDecimal id) {
        CRule cRule = cRuleService.selectByPrimaryKey(id);
        request.setAttribute("cRule", cRule);
        return "rule/updateStrategy";
    }

    @RequestMapping("/addCondition")
    @ResponseBody
    public Object addCondition(final HttpServletRequest request,
                               final HttpServletResponse response) {
        TreeMap<String, String> treeMap = getRequestParameter(request);
        try {
            CRuleCondition cRuleCondition = new CRuleCondition();
            cRuleCondition.setConditionValueId(new BigDecimal(treeMap.get("conditionId")));
            cRuleCondition.setRuleId(new BigDecimal(treeMap.get("ruleId")));
            Map map = cRuleConditionService.configExample(pageProcessAll(request, 1), cRuleCondition);
            List<CRuleCondition> cRuleConditions = (List<CRuleCondition>) map.get("list");
            if (cRuleConditions.size() > 0) {
                return renderSuccess("不能重复添加，请重新添加！");
            }
            cRuleCondition.setUpdateTime(new Date());
            cRuleConditionService.insert(cRuleCondition);
        } catch (Exception e) {
            logger.error("addCondition error", e);
            return renderSuccess("添加失败！");
        }
        return renderSuccess("添加成功！");
    }

    @RequestMapping("/addStrategy")
    @ResponseBody
    public Object addStrategy(final HttpServletRequest request,
                              final HttpServletResponse response) {
        TreeMap<String, String> treeMap = getRequestParameter(request);
        try {
            CStrategyRule cStrategyRule = new CStrategyRule();
            cStrategyRule.setRuleId(new BigDecimal(treeMap.get("ruleId")));
            Map map = cStrategyRuleService.configExample(pageProcessAll(request, 1), cStrategyRule);
            List<CStrategyRule> cStrategyRules = (List<CStrategyRule>) map.get("list");
            if (cStrategyRules.size() > 0) {
                return renderSuccess("不能重复添加，请重新添加！");
            }
            cStrategyRule.setStrategyId(new BigDecimal(treeMap.get("strategyId")));
            cStrategyRule.setUpdateTime(new Date());
            cStrategyRuleService.insert(cStrategyRule);
        } catch (Exception e) {
            logger.error("addStrategy error", e);
            return renderSuccess("添加失败！");
        }
        return renderSuccess("添加成功！");
    }


    public static List getStatusList() {
        ArrayList list = new ArrayList();
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("status", "1");
        hashMap.put("statusName", "有效");
        list.add(hashMap);
        Map<String, String> hashMap2 = new HashMap<String, String>();
        hashMap2.put("status", "0");
        hashMap2.put("statusName", "无效");
        list.add(hashMap2);
        return list;
    }

    @RequestMapping("/ruleConditionList")
    @ResponseBody
    public Object ruleConditionList(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    CRuleCondition cRuleCondition) {
        //返回对象
        TreeMap<String, String> treeMap = getRequestParameter(request);
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), "seq", "desc");
        Map map = cRuleConditionService.configExample(page, cRuleCondition);
        List<CRuleCondition> cRuleConditions = (List<CRuleCondition>) map.get("list");
        List<CConditionValue> cConditionValues = new ArrayList<>();
        for (CRuleCondition cRuleCondition1 : cRuleConditions) {
            cConditionValues.add(cConditionValueService.selectByPrimaryKey(cRuleCondition1.getConditionValueId()));
        }
        for (CConditionValue cConditionValue1 : cConditionValues) {
            Map<ConditionValueController.Operate, String> propertyTypeMap = ConditionValueController.getOperatorMap();
            for (Map.Entry entry : propertyTypeMap.entrySet()) {
                if (entry.getKey().toString().equals(cConditionValue1.getOperate())) {
                    cConditionValue1.setOperate(entry.getValue());
                }
            }
            CCondition cCondition = cConditionService.selectByPrimaryKey(cConditionValue1.getConditionId());
            if (cCondition != null) {
                cConditionValue1.setConditionName((String) cCondition.getName());
            }
        }
        page = (Page) map.get("page");
        pageInfo.setTotal(page.getCount());
        pageInfo.setRows(cConditionValues);
        pageInfo.setFrom(page.getBegin());
        return pageInfo;
    }

    @RequestMapping("/ruleStrategyList")
    @ResponseBody
    public Object ruleStrategyList(final HttpServletRequest request,
                                   final HttpServletResponse response,
                                   CStrategyRule cStrategyRule) {
        //返回对象
        TreeMap<String, String> treeMap = getRequestParameter(request);
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), "seq", "desc");
        Map map = cStrategyRuleService.configExample(page, cStrategyRule);
        List<CStrategyRule> cStrategyRules = (List<CStrategyRule>) map.get("list");
        List<CStrategy> cStrategies = new ArrayList<>();
        for (CStrategyRule cStrategyRule1 : cStrategyRules) {
            cStrategies.add(cStrategyService.selectByPrimaryKey(cStrategyRule1.getStrategyId()));
        }
        for (CStrategy cStrategy : cStrategies) {
            Map<Integer, String> propertyTypeMap = StrategyController.getTypeMap();
            for (Map.Entry entry : propertyTypeMap.entrySet()) {
                if (entry.getKey().toString().equals(cStrategy.getType().toString())) {
                    cStrategy.setTypeName(entry.getValue().toString());
                }
            }
        }
        page = (Page) map.get("page");
        pageInfo.setTotal(page.getCount());
        pageInfo.setRows(cStrategies);
        pageInfo.setFrom(page.getBegin());
        return pageInfo;
    }

    @RequestMapping("/deleteCondition")
    @ResponseBody
    public Object deleteCondition(final HttpServletRequest request,
                                  final HttpServletResponse response) {
        TreeMap<String, String> treeMap = getRequestParameter(request);
        try {
            CRuleCondition cRuleCondition = new CRuleCondition();
            cRuleCondition.setRuleId(new BigDecimal(treeMap.get("ruleId")));
            cRuleCondition.setConditionValueId(new BigDecimal(treeMap.get("conditionId")));
            cRuleConditionService.deleteByExample(cRuleCondition);
        } catch (Exception e) {
            logger.error("deleteCondition error", e);
            return renderSuccess("删除失败！");
        }
        return renderSuccess("删除成功！");
    }

    @RequestMapping("/deleteStrategy")
    @ResponseBody
    public Object deleteStrategy(final HttpServletRequest request,
                                 final HttpServletResponse response) {
        TreeMap<String, String> treeMap = getRequestParameter(request);
        try {
            CStrategyRule cStrategyRule = new CStrategyRule();
            cStrategyRule.setRuleId(new BigDecimal(treeMap.get("ruleId")));
            cStrategyRule.setStrategyId(new BigDecimal(treeMap.get("strategyId")));
            cStrategyRuleService.deleteByExample(cStrategyRule);
        } catch (Exception e) {
            logger.error("deleteStrategy error", e);
            return renderSuccess("删除失败！");
        }
        return renderSuccess("删除成功！");
    }

}
