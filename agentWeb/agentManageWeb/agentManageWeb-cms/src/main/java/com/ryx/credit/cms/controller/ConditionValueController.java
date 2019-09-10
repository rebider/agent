package com.ryx.credit.cms.controller;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.CCondition;
import com.ryx.credit.pojo.admin.CConditionValue;
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
@RequestMapping("/conditionValue")
public class ConditionValueController extends BaseController {
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

    @RequestMapping("/conditionValueList")
    @ResponseBody
    public Object conditionValueList(final HttpServletRequest request,
                                     final HttpServletResponse response,
                                     CConditionValue cConditionValue) {
        //返回对象
        TreeMap<String, String> treeMap = getRequestParameter(request);
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), "seq", "desc");
        Map map = cConditionValueService.configExample(page, cConditionValue);
        List<CConditionValue> cConditionValues = (List<CConditionValue>) map.get("list");
        for (CConditionValue cConditionValue1 : cConditionValues) {
            Map<Operate, String> propertyTypeMap = getOperatorMap();
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

    @RequestMapping("/conditionList")
    @ResponseBody
    public Object conditionList(final HttpServletRequest request,
                             final HttpServletResponse response) {
        Map map = cConditionService.configExample(pageProcessAll(request,1000),new CCondition());
        ArrayList<CCondition> cConditionArrayList = (ArrayList<CCondition>) map.get("list");
        ArrayList arrayList = new ArrayList();
        for (CCondition entry : cConditionArrayList) {
            ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap();
            concurrentHashMap.put("conditionId", entry.getId().toString());
            concurrentHashMap.put("conditionName", entry.getName().toString());
            arrayList.add(concurrentHashMap);
        }
        JSONArray jsonArray = JSONArray.fromObject(arrayList);
        return jsonArray.toString();
    }

    @RequestMapping("/operatList")
    @ResponseBody
    public Object operatList(final HttpServletRequest request,
                             final HttpServletResponse response) {
        Map<Operate, String> map = getOperatorMap();
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : map.entrySet()) {
            ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap();
            concurrentHashMap.put("operate", entry.getKey().toString());
            concurrentHashMap.put("operateName", entry.getValue().toString());
            arrayList.add(concurrentHashMap);
        }
        JSONArray jsonArray = JSONArray.fromObject(arrayList);
        return jsonArray.toString();
    }


    @RequestMapping("/toList")
    public Object toList(final HttpServletRequest request,
                         final HttpServletResponse response
    ) {
        return "conditionValue/conditionValueList";
    }

    @RequestMapping("/toAdd")
    public Object toAdd(final HttpServletRequest request,
                        final HttpServletResponse response
    ) {
        return "conditionValue/addConditionValue";
    }

    @RequestMapping("/addOrUpdateConditionValue")
    @ResponseBody
    public Object addOrUpdateConditionValue(final HttpServletRequest request,
                                            final HttpServletResponse response) {
        TreeMap<String, String> treeMap = getRequestParameter(request);
        try {
            CCondition cCondition = cConditionService.selectByPrimaryKey(new BigDecimal(treeMap.get("condition")));
            if (cCondition == null) {
                return renderSuccess("不存在该Id的条件！");
            } else {
                if ("I".equals(cCondition.getType().toString())) {
                    Pattern pattern = Pattern.compile("[0-9]*");
//                    if (!pattern.matcher(treeMap.get("value")).matches()) {
//                        return renderSuccess("请输入数字！");
//                    }
                }
                if ("B".equals(cCondition.getType().toString())) {
                    if (!"EQ".equals(treeMap.get("operate")) && !"NEQ".equals(treeMap.get("operate"))) {
                        return renderSuccess("请选择等于或者不等于！");
                    }
                    if (!"true".equals(treeMap.get("value")) && !"false".equals(treeMap.get("value"))) {
                        return renderSuccess("请输入true或者false！");
                    }
                    if ("D".equals(cCondition.getType().toString())) {
                        if (!isValidDate(treeMap.get("value"))) {
                            return renderSuccess("请输入正确日期格式 如:2013-01-20 12:00:00");
                        }
                    }
                }
                CConditionValue conditionValue = new CConditionValue();
                conditionValue.setOperate(treeMap.get("operate"));
                conditionValue.setConditionId(new BigDecimal(treeMap.get("condition")));
                conditionValue.setValue(treeMap.get("value"));
                Map map = cConditionValueService.configExample(pageProcessAll(request, 5), conditionValue);
                List<CConditionValue> cConditionValues = (List<CConditionValue>) map.get("list");
                for(CConditionValue cConditionValue :cConditionValues){
                    if(StringUtils.isNotBlank(treeMap.get("id"))){
                        if(!cConditionValue.getId().toString().equals(treeMap.get("id"))){
                            return renderSuccess("该条件值的设置已经存在，请重新定义！");
                        }
                    }else{
                        return renderSuccess("该条件值的设置已经存在，请重新定义！");
                    }
                }
                conditionValue.setName(treeMap.get("name"));
                conditionValue.setCreateTime(new Date());
                if (StringUtils.isNotBlank(treeMap.get("id"))) {
                    CConditionValue cConditionValue = cConditionValueService.selectByPrimaryKey(new BigDecimal(treeMap.get("id")));
                    if (cConditionValue == null) {
                        return renderSuccess("该条件值的设置不存在，请重新定义！");
                    } else {
                        conditionValue.setId(new BigDecimal(treeMap.get("id")));
                        cConditionValueService.updateByPrimaryKeySelective(conditionValue);
                    }
                } else {
                    cConditionValueService.insert(conditionValue);
                }
            }
        } catch (Exception e) {
            logger.error("[module:conditionValue] [action:conditionValueAction] [step:save] ", e);
        }
        return renderSuccess("成功！");
    }

    @RequestMapping("/toUpdate")
    public Object toUpdate(final HttpServletRequest request,
                           final HttpServletResponse response,
                           BigDecimal id) {
        CConditionValue cConditionValue = cConditionValueService.selectByPrimaryKey(id);
        request.setAttribute("cConditionValue", cConditionValue);
        return "conditionValue/updateConditionValue";
    }


    public enum Operate {
        EQ, LT, GT, LE, GE, NEQ;
    }


    public static Map<Operate, String> getOperatorMap() {
        Map<Operate, String> hashMap = new HashMap<Operate, String>();
        hashMap.put(Operate.EQ, "等于");
        hashMap.put(Operate.LT, "小于");
        hashMap.put(Operate.GT, "大于");
        hashMap.put(Operate.LE, "小于等于");
        hashMap.put(Operate.GE, "大于等于");
        hashMap.put(Operate.NEQ, "不等于");
        return hashMap;
    }

    public static boolean isValidDate(String s) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setLenient(false);
            dateFormat.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }
}
