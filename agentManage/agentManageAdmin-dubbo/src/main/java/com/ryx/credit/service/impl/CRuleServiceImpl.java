package com.ryx.credit.service.impl;

import com.ryx.credit.common.util.ConvertUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.commons.utils.BeanUtils;
import com.ryx.credit.dao.*;
import com.ryx.credit.pojo.admin.*;
import com.ryx.credit.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CRuleServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/31
 * @Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
@Service("cRuleService")
public class CRuleServiceImpl implements ICRuleService {
    protected Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private CRuleMapper cRuleMapper;
    @Autowired
    private ICRuleConditionService icRuleConditionService;
    @Autowired
    private CRuleConditionMapper cRuleConditionMapper;
    @Autowired
    private ICStrategyRuleService icStrategyRuleService;
    @Autowired
    private ICStrategyService icStrategyService;
    @Autowired
    private ICConditionService icConditionService;
    @Autowired
    private ICConditionValueService icConditionValueService;


    @Override
    public int insert(CRule record) {
        return cRuleMapper.insert(record);
    }

    @Override
    public int insertSelective(CRule record) {
        return cRuleMapper.insertSelective(record);
    }

    @Override
    public CRule selectByPrimaryKey(BigDecimal id) {
        return cRuleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CRule record) {
        return cRuleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CRule record) {
        return cRuleMapper.updateByPrimaryKey(record);
    }

    @Override
    public Map configExample(Page page, CRule cRule) {
        if (cRule != null && page != null) {
            CRuleExample example = new CRuleExample();
            CRuleExample.Criteria criteria = example.or();
            if (cRule.getId() != null)
                criteria.andIdEqualTo(cRule.getId());
            if(cRule.getName()!=null && StringUtils.isNotBlank(String.valueOf(cRule.getName())))
                criteria.andNameEqualTo(cRule.getName());
            if (cRule.getOperator() != null)
                criteria.andOperatorEqualTo(cRule.getOperator());
            if (cRule.getProductId() != null)
                criteria.andProductIdEqualTo(cRule.getProductId());
            if (cRule.getStatus() != null)
                criteria.andStatusEqualTo(cRule.getStatus());
            int count = cRuleMapper.countByExample(example);
            page.setCount(count);
            example.setPage(page);
            example.setOrderByClause("CREATE_TIME " + Page.ORDER_DIRECTION_DESC);
            HashMap hashMap = new HashMap();
            hashMap.put("list", cRuleMapper.selectByExample(example));
            hashMap.put("page", page);
            return hashMap;
        } else {
            return null;
        }
    }

    public String computeDiscountPrice(Object product, String field, Object user, Map<String, Object> params) {
        Object id = null;
        Object result = null;
        try {
            id = BeanUtils.getProperty(product, "id");
            result = BeanUtils.getProperty(product, field);
//            Method method  = product.getClass().getMethod(methods,tClass,null);
//            Object object =method.invoke(product, null);
        } catch (Exception e) {
            logger.error("computeDiscountPrice BeanUtils error", e);
        }

        try {
            if (params == null) {
                params = new HashMap<String, Object>();
            }
            params.put(field, result.toString());
            CRuleExample example = new CRuleExample();
            CRuleExample.Criteria criteria = example.or();
            criteria.andProductIdEqualTo((BigDecimal) id);
            List<CRule> cRuleList = cRuleMapper.selectByExample(example);   //查询有效的规则
            CRule real_rule = null;
            for (CRule cRule : cRuleList) {
                try {
                    if(cRule.getValidStartTime().after(new Date())||cRule.getValidEndTime().before(new Date())){
                        continue;
                    }
                    if (real_rule!=null && real_rule.getPriority().intValue() < cRule.getPriority().intValue()) {
                        continue;
                    }
                    result = BeanUtils.getProperty(product, field);
                } catch (Exception e) {
                    logger.error("getValidStartTime",e);
                }
                Map<String,Object> map_match = match(cRule, user, params);
                boolean result_match = (boolean) map_match.get("result");
                if (result_match) {
                    if(map_match.containsKey("amount")){
                        result = new BigDecimal(result.toString()).add((BigDecimal) map_match.get("amount"));
                    }
                    if (real_rule == null) {
                        real_rule = cRule;
                    } else {
                        if (real_rule.getPriority().intValue() < cRule.getPriority().intValue()) {
                            break;
                        }
                    }
                    CStrategyRule strategyRule = new CStrategyRule();
                    strategyRule.setRuleId(cRule.getId());
                    Map map = icStrategyRuleService.configExample(pageProcessAll(1), strategyRule);
                    List<CStrategyRule> cStrategyRules = (List<CStrategyRule>) map.get("list");
                    if (cStrategyRules.size() > 0) {
                        strategyRule = cStrategyRules.get(0);
                        CStrategy strategy = icStrategyService.selectByPrimaryKey(strategyRule.getStrategyId());
                        if (strategy != null) {
                            switch (strategy.getType().intValue()) {
                                case CStrategy.STRATEGY_TYPE_DISCOUNT: //打折
                                    BigDecimal price = new BigDecimal(strategy.getValue().toString()).multiply(new BigDecimal(result.toString()));
                                    result = price;
                                    break;
                                case CStrategy.STRATEGY_TYPE_PRICE: //定价
                                    result = strategy.getValue();
                                    break;
                                case CStrategy.STRATEGY_TYPE_DECREASE: //立减
                                    result = new BigDecimal(result.toString()).subtract(new BigDecimal(strategy.getValue().toString()));
                                    break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[product:{" + id + "}] [amount:{" + result + "}] [params:{" + params + "}] [result:{" + result + "}]", e);
            return result.toString();
        }

        logger.debug("[product:{" + id + "}] [amount:{" + result + "}] [params:{" + params + "}] [result:{" + result + "}]");
        return result.toString();
    }

    /**
     * 判断规则是否符合
     *
     * @param cRule  规则
     * @param user   用户
     * @param params 参数
     * @return true:满足规则，false:不满足规则
     */
    private Map<String,Object> match(CRule cRule, Object user, Map<String, Object> params) {
        CRuleConditionExample example = new CRuleConditionExample();
        CRuleConditionExample.Criteria criteria = example.or();
        criteria.andRuleIdEqualTo(cRule.getId());
        List<CRuleCondition> ruleConditionList = cRuleConditionMapper.selectByExample(example);
        Map<String,Object> result_map = new HashMap<>();
        BigDecimal result = BigDecimal.ZERO;
        if (ruleConditionList.size() == 0) {
            result_map.put("result",false);
            return result_map;
        }
        for (CRuleCondition ruleCondition : ruleConditionList) {
            CConditionValue conditionValue = icConditionValueService.selectByPrimaryKey(ruleCondition.getConditionValueId());
            CCondition condition = icConditionService.selectByPrimaryKey(conditionValue.getConditionId());
            String value = String.valueOf(params.get(condition.getConditionKey()));

            if (condition.getConditionKey().toString().indexOf("@") > 0) {   //如果是用户条件
                String[] objectString = condition.getConditionKey().toString().split("@");
                String attr = objectString[1];
                //todo 包含=
                if(objectString[1].indexOf("=")>=0){
                    attr = objectString[1].split("=")[0];
                }
                try {
                    if ("user".equals(objectString[0])) {
                        value = String.valueOf(BeanUtils.getProperty(user, attr));
                    } else {
                        value = String.valueOf(BeanUtils.getProperty(params.get(objectString[0]), attr));
                    }
                } catch (Exception e) {
                    result_map.put("result",false);
                    return result_map;
                }
            } else if (condition.getConditionKey().toString().indexOf("#") > 0) {   //如果是队列
                try {
                    String[] objectString = condition.getConditionKey().toString().split("#");
                    String attr = objectString[1];
                    //todo 包含=
                    if(objectString[1].indexOf("=")>=0){
                        attr = objectString[1].split("=")[0];
                    }
                    List list = (List) params.get(objectString[0]);
                    boolean status = false;
                    for (Object o : list) {
                        value = String.valueOf(BeanUtils.getProperty(o, attr));
                        Map<String,Object> map = match(conditionValue, value);
                        if ((boolean)map.get("result")) {
                            status = true;
                            if(map.containsKey("amount"))
                                result = result.add(new BigDecimal((String) map.get("amount")));
                            break;
                        }
                    }
                    if (!status) {
                        result_map.put("result",false);
                        return result_map;
                    }
                } catch (Exception e) {
                    result_map.put("result",false);
                    return result_map;
                }
            } else {
                if (StringUtils.isBlank(value)) {
                    result_map.put("result",false);
                    return result_map;
                }
            }
//            else {
//                if(condition.getConditionKey().equals(CCondition.FIRST_FAVOUR)){
//                    if (user != null) {
//                        Long favorId = Long.parseLong(conditionValue.getValue());
//                        if(firstFavorManager.isExistFirstFavorRecordByAct(favorId, user.getId())){
//                            return false;
//                        }
//                    }
//                    continue;
//                }
//            }
            Map<String,Object> map = match(conditionValue, value);
            if (!(boolean)map.get("result")) {
                result_map.put("result",false);
                return result_map;
            }else{
                if(map.containsKey("amount"))
                    result = result.add((BigDecimal) map.get("amount"));
                //todo 包含=
                if(condition.getConditionKey().toString().indexOf("=")>=0){
                    result = result.add(new BigDecimal(condition.getConditionKey().toString().split("=")[1]));
                }
            }
        }
        result_map.put("amount",result);
        result_map.put("result",true);
        return result_map;
    }

    private Map<String,Object> match(CConditionValue conditionValue, String value) {
        Map<String,Object> map = new HashMap<>();
        map.put("result",false);
        try {
            if (value == null)
                return map;
            CCondition condition = icConditionService.selectByPrimaryKey(conditionValue.getConditionId());
            Class<?> propertyTypeClass = Enum.valueOf(CCondition.PropertyType.class, condition.getType().toString()).getValue();
            Object value1 = ConvertUtils.convertStringToObject((String) conditionValue.getValue(), propertyTypeClass);
            Object value2 = ConvertUtils.convertStringToObject(value, propertyTypeClass);
            if(value2 instanceof String && ((String) value1).indexOf(",")>0){ //包含in判断
                for(String v: ((String) value1).split(",")){
                    //todo 包含=
                    String vv = v;
                    if(v.indexOf("=")>=0){
                        vv = v.split("=")[0];
                    }
                    if(compareCondition(vv, value2, CConditionValue.Operate.valueOf((String) conditionValue.getOperate()), CCondition.PropertyType.valueOf(condition.getType().toString()))){
                        if(v.indexOf("=")>=0)
                            map.put("amount",new BigDecimal(v.split("=")[1]));
                        map.put("result",true);
                        return map;
                    }
                }
                return map;
            }
            String vvv = value1.toString();
            if(value1.toString().indexOf("=")>=0){
                vvv = value1.toString().split("=")[0];
            }
            if(compareCondition(vvv, value2, CConditionValue.Operate.valueOf((String) conditionValue.getOperate()), CCondition.PropertyType.valueOf(condition.getType().toString()))){
                if(value1.toString().indexOf("=")>=0)
                    map.put("amount",new BigDecimal(value1.toString().split("=")[1]));
                map.put("result",true);
            }
            return map;
        } catch (Exception e) {
            logger.error("[module:ConditionValue] [action:RuleNewManager] [step:match] ", e);
        }
        return map;
    }

    /**
     * @param conditionValue 条件值
     * @param compareValue   比较值
     * @param operator       操作符
     * @param propertyType   属性类型
     * @return
     * @throws Exception
     */
    public static boolean compareCondition(Object conditionValue, Object compareValue, CConditionValue.Operate operator, CCondition.PropertyType propertyType) throws Exception {
        if (operator.equals(CConditionValue.Operate.EQ)) {
            if (propertyType.equals(CCondition.PropertyType.D)) {
                return ((Date) compareValue).getTime() == ((Date) conditionValue).getTime();
            } else {
                return compareValue.equals(conditionValue);
            }

        } else if (operator.equals(CConditionValue.Operate.NEQ)) {
            if (propertyType.equals(CCondition.PropertyType.D)) {
                return ((Date) compareValue).getTime() != ((Date) conditionValue).getTime();
            } else {
                return !compareValue.equals(conditionValue);
            }
        } else if (operator.equals(CConditionValue.Operate.LT)) {
            if (propertyType.equals(CCondition.PropertyType.D)) {
                return ((Date) compareValue).getTime() < ((Date) conditionValue).getTime();
            } else if (propertyType.equals(CCondition.PropertyType.I)) {
                return ((Integer) compareValue).intValue() < ((Integer) conditionValue).intValue();
            }else{
                return  new BigDecimal(compareValue.toString()).compareTo(new BigDecimal(conditionValue.toString()))<0;
            }
        } else if (operator.equals(CConditionValue.Operate.GT)) {
            if (propertyType.equals(CCondition.PropertyType.D)) {
                return ((Date) compareValue).getTime() > ((Date) conditionValue).getTime();
            } else if (propertyType.equals(CCondition.PropertyType.I)) {
                return ((Integer) compareValue).intValue() > ((Integer) conditionValue).intValue();
            }else{
                return  new BigDecimal(compareValue.toString()).compareTo(new BigDecimal(conditionValue.toString()))>0;
            }
        } else if (operator.equals(CConditionValue.Operate.LE)) {
            if (propertyType.equals(CCondition.PropertyType.D)) {
                return ((Date) compareValue).getTime() <= ((Date) conditionValue).getTime();
            } else if (propertyType.equals(CCondition.PropertyType.I)) {
                return ((Integer) compareValue).intValue() <= ((Integer) conditionValue).intValue();
            }else{
                return  new BigDecimal(compareValue.toString()).compareTo(new BigDecimal(conditionValue.toString()))<=0;
            }
        } else if (operator.equals(CConditionValue.Operate.GE)) {
            if (propertyType.equals(CCondition.PropertyType.D)) {
                return ((Date) compareValue).getTime() >= ((Date) conditionValue).getTime();
            } else if (propertyType.equals(CCondition.PropertyType.I)) {
                return ((Integer) compareValue).intValue() >= ((Integer) conditionValue).intValue();
            }else{
                return  new BigDecimal(compareValue.toString()).compareTo(new BigDecimal(conditionValue.toString()))>=0;
            }
        }
        return false;
    }

    /**
     * 处理分页用到的信息
     *
     * @return
     */
    protected Page pageProcessAll(int size) {
        int numPerPage = size;
        int currentPage = 1;
        Page page = new Page();
        page.setCurrent(currentPage);
        page.setLength(numPerPage);
        page.setBegin((currentPage - 1) * numPerPage);
        page.setEnd(currentPage * numPerPage);
        return page;
    }
}
