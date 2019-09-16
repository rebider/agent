package com.ryx.credit.cms.controller;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.CCondition;
import com.ryx.credit.pojo.admin.CRule;
import com.ryx.credit.service.*;
import net.sf.json.JSONArray;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/condition")
public class ConditionController extends BaseController{
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
    private PropertyType type;

    @RequestMapping("/conditionList")
    @ResponseBody
    public Object conditionList(final HttpServletRequest request,
                           final HttpServletResponse response,
                           CCondition cCondition){
        //返回对象
        TreeMap<String, String> treeMap = getRequestParameter(request);
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), "seq", "desc");
        Map map = cConditionService.configExample(page, cCondition);
        List<CCondition>  cRuleList = (List<CCondition>) map.get("list");
        for(CCondition cCondition1:cRuleList){
            Map<PropertyType,String> propertyTypeMap = getPropertyTypeMap();
            for(Map.Entry entry :propertyTypeMap.entrySet()){
                if(entry.getKey().toString().equals(cCondition1.getType())){
                    cCondition1.setType(entry.getValue());
                }
            }

        }
        page = (Page) map.get("page");
        pageInfo.setTotal(page.getCount());
        pageInfo.setRows(cRuleList);
        pageInfo.setFrom(page.getBegin());
        return pageInfo;
    }

    @RequestMapping("/typeList")
    @ResponseBody
    public Object typeList(final HttpServletRequest request,
                             final HttpServletResponse response){
        Map<PropertyType, String> map = getPropertyTypeMap();
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
                         ){
        return "condition/conditionList";
    }

    @RequestMapping("/toAdd")
    public Object toAdd(final HttpServletRequest request,
                         final HttpServletResponse response
                        ){
        return "condition/addCondition";
    }

    @RequestMapping("/addCondition")
    @ResponseBody
    public Object addCondition(final HttpServletRequest request,
                        final HttpServletResponse response){
        TreeMap<String, String> treeMap = getRequestParameter(request);
        try {
            CCondition condition = new CCondition();
            condition.setName(treeMap.get("name"));
            condition.setConditionKey(treeMap.get("conditionKey"));
            condition.setType(treeMap.get("type"));
            condition.setCreateTime(new Date());
            cConditionService.insert(condition);
        } catch (Exception e) {
            logger.error("addCondition error",e);
        }
        return renderSuccess("添加成功！");
    }

    @RequestMapping("/toUpdate")
    public Object toUpdate(final HttpServletRequest request,
                        final HttpServletResponse response,
                           BigDecimal id){
        CCondition cCondition = cConditionService.selectByPrimaryKey(id);
        request.setAttribute("cCondition",cCondition);
        return "condition/updateCondition";
    }

    @RequestMapping("/updateCondition")
    @ResponseBody
    public Object updateCondition(final HttpServletRequest request,
                          final HttpServletResponse response){
        TreeMap<String, String> treeMap = getRequestParameter(request);
        try {
            CCondition condition = new CCondition();
            condition.setId(new BigDecimal(treeMap.get("id")));
            condition.setName(treeMap.get("name"));
            condition.setConditionKey(treeMap.get("conditionKey"));
            condition.setType(treeMap.get("type"));
            condition.setUpdateTime(new Date());
            cConditionService.updateByPrimaryKeySelective(condition);
        } catch (Exception e) {
            logger.error("updateCondition error",e);
        }
        return renderSuccess("修改成功！");
    }



    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public enum PropertyType {
        S(String.class), I(Integer.class), D(Date.class), B(Boolean.class);

        private Class<?> clazz;

        private PropertyType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Class<?> getValue() {
            return clazz;
        }
    }

    public static final PropertyType INT_TYPE = PropertyType.I;

    public static final PropertyType STRING_TYPE = PropertyType.S;

    public static final PropertyType DATE_TYPE = PropertyType.D;

    public static final PropertyType BOOLEAN_TYPE = PropertyType.B;





    public static Map<PropertyType, String> getPropertyTypeMap() {
        Map<PropertyType, String> hashMap = new HashMap<PropertyType, String>();
        hashMap.put(INT_TYPE, "int型");
        hashMap.put(STRING_TYPE, "string型");
        hashMap.put(DATE_TYPE, "date型");
        hashMap.put(BOOLEAN_TYPE, "boolean型");
        return hashMap;
    }

}
