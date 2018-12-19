package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OSubOrderActivityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OSubOrderActivityExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setPage(Page page) {
        this.page=page;
    }

    public Page getPage() {
        return page;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andActivityIdIsNull() {
            addCriterion("ACTIVITY_ID is null");
            return (Criteria) this;
        }

        public Criteria andActivityIdIsNotNull() {
            addCriterion("ACTIVITY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andActivityIdEqualTo(String value) {
            addCriterion("ACTIVITY_ID =", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotEqualTo(String value) {
            addCriterion("ACTIVITY_ID <>", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdGreaterThan(String value) {
            addCriterion("ACTIVITY_ID >", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_ID >=", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdLessThan(String value) {
            addCriterion("ACTIVITY_ID <", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdLessThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_ID <=", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdLike(String value) {
            addCriterion("ACTIVITY_ID like", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotLike(String value) {
            addCriterion("ACTIVITY_ID not like", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdIn(List<String> values) {
            addCriterion("ACTIVITY_ID in", values, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotIn(List<String> values) {
            addCriterion("ACTIVITY_ID not in", values, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdBetween(String value1, String value2) {
            addCriterion("ACTIVITY_ID between", value1, value2, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotBetween(String value1, String value2) {
            addCriterion("ACTIVITY_ID not between", value1, value2, "activityId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdIsNull() {
            addCriterion("SUB_ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdIsNotNull() {
            addCriterion("SUB_ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdEqualTo(String value) {
            addCriterion("SUB_ORDER_ID =", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdNotEqualTo(String value) {
            addCriterion("SUB_ORDER_ID <>", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdGreaterThan(String value) {
            addCriterion("SUB_ORDER_ID >", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUB_ORDER_ID >=", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdLessThan(String value) {
            addCriterion("SUB_ORDER_ID <", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdLessThanOrEqualTo(String value) {
            addCriterion("SUB_ORDER_ID <=", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdLike(String value) {
            addCriterion("SUB_ORDER_ID like", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdNotLike(String value) {
            addCriterion("SUB_ORDER_ID not like", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdIn(List<String> values) {
            addCriterion("SUB_ORDER_ID in", values, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdNotIn(List<String> values) {
            addCriterion("SUB_ORDER_ID not in", values, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdBetween(String value1, String value2) {
            addCriterion("SUB_ORDER_ID between", value1, value2, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdNotBetween(String value1, String value2) {
            addCriterion("SUB_ORDER_ID not between", value1, value2, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andActivityNameIsNull() {
            addCriterion("ACTIVITY_NAME is null");
            return (Criteria) this;
        }

        public Criteria andActivityNameIsNotNull() {
            addCriterion("ACTIVITY_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andActivityNameEqualTo(String value) {
            addCriterion("ACTIVITY_NAME =", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameNotEqualTo(String value) {
            addCriterion("ACTIVITY_NAME <>", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameGreaterThan(String value) {
            addCriterion("ACTIVITY_NAME >", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_NAME >=", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameLessThan(String value) {
            addCriterion("ACTIVITY_NAME <", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameLessThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_NAME <=", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameLike(String value) {
            addCriterion("ACTIVITY_NAME like", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameNotLike(String value) {
            addCriterion("ACTIVITY_NAME not like", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameIn(List<String> values) {
            addCriterion("ACTIVITY_NAME in", values, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameNotIn(List<String> values) {
            addCriterion("ACTIVITY_NAME not in", values, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameBetween(String value1, String value2) {
            addCriterion("ACTIVITY_NAME between", value1, value2, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameNotBetween(String value1, String value2) {
            addCriterion("ACTIVITY_NAME not between", value1, value2, "activityName");
            return (Criteria) this;
        }

        public Criteria andRuleIdIsNull() {
            addCriterion("RULE_ID is null");
            return (Criteria) this;
        }

        public Criteria andRuleIdIsNotNull() {
            addCriterion("RULE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRuleIdEqualTo(String value) {
            addCriterion("RULE_ID =", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotEqualTo(String value) {
            addCriterion("RULE_ID <>", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdGreaterThan(String value) {
            addCriterion("RULE_ID >", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdGreaterThanOrEqualTo(String value) {
            addCriterion("RULE_ID >=", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdLessThan(String value) {
            addCriterion("RULE_ID <", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdLessThanOrEqualTo(String value) {
            addCriterion("RULE_ID <=", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdLike(String value) {
            addCriterion("RULE_ID like", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotLike(String value) {
            addCriterion("RULE_ID not like", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdIn(List<String> values) {
            addCriterion("RULE_ID in", values, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotIn(List<String> values) {
            addCriterion("RULE_ID not in", values, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdBetween(String value1, String value2) {
            addCriterion("RULE_ID between", value1, value2, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotBetween(String value1, String value2) {
            addCriterion("RULE_ID not between", value1, value2, "ruleId");
            return (Criteria) this;
        }

        public Criteria andActivityRuleIsNull() {
            addCriterion("ACTIVITY_RULE is null");
            return (Criteria) this;
        }

        public Criteria andActivityRuleIsNotNull() {
            addCriterion("ACTIVITY_RULE is not null");
            return (Criteria) this;
        }

        public Criteria andActivityRuleEqualTo(String value) {
            addCriterion("ACTIVITY_RULE =", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleNotEqualTo(String value) {
            addCriterion("ACTIVITY_RULE <>", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleGreaterThan(String value) {
            addCriterion("ACTIVITY_RULE >", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_RULE >=", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleLessThan(String value) {
            addCriterion("ACTIVITY_RULE <", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleLessThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_RULE <=", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleLike(String value) {
            addCriterion("ACTIVITY_RULE like", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleNotLike(String value) {
            addCriterion("ACTIVITY_RULE not like", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleIn(List<String> values) {
            addCriterion("ACTIVITY_RULE in", values, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleNotIn(List<String> values) {
            addCriterion("ACTIVITY_RULE not in", values, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleBetween(String value1, String value2) {
            addCriterion("ACTIVITY_RULE between", value1, value2, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleNotBetween(String value1, String value2) {
            addCriterion("ACTIVITY_RULE not between", value1, value2, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityWayIsNull() {
            addCriterion("ACTIVITY_WAY is null");
            return (Criteria) this;
        }

        public Criteria andActivityWayIsNotNull() {
            addCriterion("ACTIVITY_WAY is not null");
            return (Criteria) this;
        }

        public Criteria andActivityWayEqualTo(String value) {
            addCriterion("ACTIVITY_WAY =", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayNotEqualTo(String value) {
            addCriterion("ACTIVITY_WAY <>", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayGreaterThan(String value) {
            addCriterion("ACTIVITY_WAY >", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_WAY >=", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayLessThan(String value) {
            addCriterion("ACTIVITY_WAY <", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayLessThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_WAY <=", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayLike(String value) {
            addCriterion("ACTIVITY_WAY like", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayNotLike(String value) {
            addCriterion("ACTIVITY_WAY not like", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayIn(List<String> values) {
            addCriterion("ACTIVITY_WAY in", values, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayNotIn(List<String> values) {
            addCriterion("ACTIVITY_WAY not in", values, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayBetween(String value1, String value2) {
            addCriterion("ACTIVITY_WAY between", value1, value2, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayNotBetween(String value1, String value2) {
            addCriterion("ACTIVITY_WAY not between", value1, value2, "activityWay");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("PRICE is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("PRICE =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("PRICE <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("PRICE >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRICE >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("PRICE <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRICE <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("PRICE in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("PRICE not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRICE between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRICE not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andProModelIsNull() {
            addCriterion("PRO_MODEL is null");
            return (Criteria) this;
        }

        public Criteria andProModelIsNotNull() {
            addCriterion("PRO_MODEL is not null");
            return (Criteria) this;
        }

        public Criteria andProModelEqualTo(String value) {
            addCriterion("PRO_MODEL =", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelNotEqualTo(String value) {
            addCriterion("PRO_MODEL <>", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelGreaterThan(String value) {
            addCriterion("PRO_MODEL >", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelGreaterThanOrEqualTo(String value) {
            addCriterion("PRO_MODEL >=", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelLessThan(String value) {
            addCriterion("PRO_MODEL <", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelLessThanOrEqualTo(String value) {
            addCriterion("PRO_MODEL <=", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelLike(String value) {
            addCriterion("PRO_MODEL like", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelNotLike(String value) {
            addCriterion("PRO_MODEL not like", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelIn(List<String> values) {
            addCriterion("PRO_MODEL in", values, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelNotIn(List<String> values) {
            addCriterion("PRO_MODEL not in", values, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelBetween(String value1, String value2) {
            addCriterion("PRO_MODEL between", value1, value2, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelNotBetween(String value1, String value2) {
            addCriterion("PRO_MODEL not between", value1, value2, "proModel");
            return (Criteria) this;
        }

        public Criteria andVenderIsNull() {
            addCriterion("VENDER is null");
            return (Criteria) this;
        }

        public Criteria andVenderIsNotNull() {
            addCriterion("VENDER is not null");
            return (Criteria) this;
        }

        public Criteria andVenderEqualTo(String value) {
            addCriterion("VENDER =", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderNotEqualTo(String value) {
            addCriterion("VENDER <>", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderGreaterThan(String value) {
            addCriterion("VENDER >", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderGreaterThanOrEqualTo(String value) {
            addCriterion("VENDER >=", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderLessThan(String value) {
            addCriterion("VENDER <", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderLessThanOrEqualTo(String value) {
            addCriterion("VENDER <=", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderLike(String value) {
            addCriterion("VENDER like", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderNotLike(String value) {
            addCriterion("VENDER not like", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderIn(List<String> values) {
            addCriterion("VENDER in", values, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderNotIn(List<String> values) {
            addCriterion("VENDER not in", values, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderBetween(String value1, String value2) {
            addCriterion("VENDER between", value1, value2, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderNotBetween(String value1, String value2) {
            addCriterion("VENDER not between", value1, value2, "vender");
            return (Criteria) this;
        }

        public Criteria andPlatformIsNull() {
            addCriterion("PLATFORM is null");
            return (Criteria) this;
        }

        public Criteria andPlatformIsNotNull() {
            addCriterion("PLATFORM is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformEqualTo(String value) {
            addCriterion("PLATFORM =", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotEqualTo(String value) {
            addCriterion("PLATFORM <>", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformGreaterThan(String value) {
            addCriterion("PLATFORM >", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformGreaterThanOrEqualTo(String value) {
            addCriterion("PLATFORM >=", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLessThan(String value) {
            addCriterion("PLATFORM <", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLessThanOrEqualTo(String value) {
            addCriterion("PLATFORM <=", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLike(String value) {
            addCriterion("PLATFORM like", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotLike(String value) {
            addCriterion("PLATFORM not like", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformIn(List<String> values) {
            addCriterion("PLATFORM in", values, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotIn(List<String> values) {
            addCriterion("PLATFORM not in", values, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformBetween(String value1, String value2) {
            addCriterion("PLATFORM between", value1, value2, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotBetween(String value1, String value2) {
            addCriterion("PLATFORM not between", value1, value2, "platform");
            return (Criteria) this;
        }

        public Criteria andGTimeIsNull() {
            addCriterion("G_TIME is null");
            return (Criteria) this;
        }

        public Criteria andGTimeIsNotNull() {
            addCriterion("G_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andGTimeEqualTo(BigDecimal value) {
            addCriterion("G_TIME =", value, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeNotEqualTo(BigDecimal value) {
            addCriterion("G_TIME <>", value, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeGreaterThan(BigDecimal value) {
            addCriterion("G_TIME >", value, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("G_TIME >=", value, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeLessThan(BigDecimal value) {
            addCriterion("G_TIME <", value, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("G_TIME <=", value, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeIn(List<BigDecimal> values) {
            addCriterion("G_TIME in", values, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeNotIn(List<BigDecimal> values) {
            addCriterion("G_TIME not in", values, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("G_TIME between", value1, value2, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("G_TIME not between", value1, value2, "gTime");
            return (Criteria) this;
        }

        public Criteria andCTimeIsNull() {
            addCriterion("C_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCTimeIsNotNull() {
            addCriterion("C_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCTimeEqualTo(Date value) {
            addCriterion("C_TIME =", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotEqualTo(Date value) {
            addCriterion("C_TIME <>", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeGreaterThan(Date value) {
            addCriterion("C_TIME >", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("C_TIME >=", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeLessThan(Date value) {
            addCriterion("C_TIME <", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeLessThanOrEqualTo(Date value) {
            addCriterion("C_TIME <=", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeIn(List<Date> values) {
            addCriterion("C_TIME in", values, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotIn(List<Date> values) {
            addCriterion("C_TIME not in", values, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeBetween(Date value1, Date value2) {
            addCriterion("C_TIME between", value1, value2, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotBetween(Date value1, Date value2) {
            addCriterion("C_TIME not between", value1, value2, "cTime");
            return (Criteria) this;
        }

        public Criteria andUTimeIsNull() {
            addCriterion("U_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUTimeIsNotNull() {
            addCriterion("U_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUTimeEqualTo(Date value) {
            addCriterion("U_TIME =", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeNotEqualTo(Date value) {
            addCriterion("U_TIME <>", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeGreaterThan(Date value) {
            addCriterion("U_TIME >", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("U_TIME >=", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeLessThan(Date value) {
            addCriterion("U_TIME <", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeLessThanOrEqualTo(Date value) {
            addCriterion("U_TIME <=", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeIn(List<Date> values) {
            addCriterion("U_TIME in", values, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeNotIn(List<Date> values) {
            addCriterion("U_TIME not in", values, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeBetween(Date value1, Date value2) {
            addCriterion("U_TIME between", value1, value2, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeNotBetween(Date value1, Date value2) {
            addCriterion("U_TIME not between", value1, value2, "uTime");
            return (Criteria) this;
        }

        public Criteria andCUserIsNull() {
            addCriterion("C_USER is null");
            return (Criteria) this;
        }

        public Criteria andCUserIsNotNull() {
            addCriterion("C_USER is not null");
            return (Criteria) this;
        }

        public Criteria andCUserEqualTo(String value) {
            addCriterion("C_USER =", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotEqualTo(String value) {
            addCriterion("C_USER <>", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserGreaterThan(String value) {
            addCriterion("C_USER >", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserGreaterThanOrEqualTo(String value) {
            addCriterion("C_USER >=", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserLessThan(String value) {
            addCriterion("C_USER <", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserLessThanOrEqualTo(String value) {
            addCriterion("C_USER <=", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserLike(String value) {
            addCriterion("C_USER like", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotLike(String value) {
            addCriterion("C_USER not like", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserIn(List<String> values) {
            addCriterion("C_USER in", values, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotIn(List<String> values) {
            addCriterion("C_USER not in", values, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserBetween(String value1, String value2) {
            addCriterion("C_USER between", value1, value2, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotBetween(String value1, String value2) {
            addCriterion("C_USER not between", value1, value2, "cUser");
            return (Criteria) this;
        }

        public Criteria andUUserIsNull() {
            addCriterion("U_USER is null");
            return (Criteria) this;
        }

        public Criteria andUUserIsNotNull() {
            addCriterion("U_USER is not null");
            return (Criteria) this;
        }

        public Criteria andUUserEqualTo(String value) {
            addCriterion("U_USER =", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotEqualTo(String value) {
            addCriterion("U_USER <>", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserGreaterThan(String value) {
            addCriterion("U_USER >", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserGreaterThanOrEqualTo(String value) {
            addCriterion("U_USER >=", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserLessThan(String value) {
            addCriterion("U_USER <", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserLessThanOrEqualTo(String value) {
            addCriterion("U_USER <=", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserLike(String value) {
            addCriterion("U_USER like", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotLike(String value) {
            addCriterion("U_USER not like", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserIn(List<String> values) {
            addCriterion("U_USER in", values, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotIn(List<String> values) {
            addCriterion("U_USER not in", values, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserBetween(String value1, String value2) {
            addCriterion("U_USER between", value1, value2, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotBetween(String value1, String value2) {
            addCriterion("U_USER not between", value1, value2, "uUser");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("VERSION is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(BigDecimal value) {
            addCriterion("VERSION =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(BigDecimal value) {
            addCriterion("VERSION <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(BigDecimal value) {
            addCriterion("VERSION >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("VERSION >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(BigDecimal value) {
            addCriterion("VERSION <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("VERSION <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<BigDecimal> values) {
            addCriterion("VERSION in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<BigDecimal> values) {
            addCriterion("VERSION not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("VERSION between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("VERSION not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andProIdIsNull() {
            addCriterion("PRO_ID is null");
            return (Criteria) this;
        }

        public Criteria andProIdIsNotNull() {
            addCriterion("PRO_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProIdEqualTo(String value) {
            addCriterion("PRO_ID =", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdNotEqualTo(String value) {
            addCriterion("PRO_ID <>", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdGreaterThan(String value) {
            addCriterion("PRO_ID >", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdGreaterThanOrEqualTo(String value) {
            addCriterion("PRO_ID >=", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdLessThan(String value) {
            addCriterion("PRO_ID <", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdLessThanOrEqualTo(String value) {
            addCriterion("PRO_ID <=", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdLike(String value) {
            addCriterion("PRO_ID like", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdNotLike(String value) {
            addCriterion("PRO_ID not like", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdIn(List<String> values) {
            addCriterion("PRO_ID in", values, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdNotIn(List<String> values) {
            addCriterion("PRO_ID not in", values, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdBetween(String value1, String value2) {
            addCriterion("PRO_ID between", value1, value2, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdNotBetween(String value1, String value2) {
            addCriterion("PRO_ID not between", value1, value2, "proId");
            return (Criteria) this;
        }

        public Criteria andProNameIsNull() {
            addCriterion("PRO_NAME is null");
            return (Criteria) this;
        }

        public Criteria andProNameIsNotNull() {
            addCriterion("PRO_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andProNameEqualTo(String value) {
            addCriterion("PRO_NAME =", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotEqualTo(String value) {
            addCriterion("PRO_NAME <>", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameGreaterThan(String value) {
            addCriterion("PRO_NAME >", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameGreaterThanOrEqualTo(String value) {
            addCriterion("PRO_NAME >=", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameLessThan(String value) {
            addCriterion("PRO_NAME <", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameLessThanOrEqualTo(String value) {
            addCriterion("PRO_NAME <=", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameLike(String value) {
            addCriterion("PRO_NAME like", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotLike(String value) {
            addCriterion("PRO_NAME not like", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameIn(List<String> values) {
            addCriterion("PRO_NAME in", values, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotIn(List<String> values) {
            addCriterion("PRO_NAME not in", values, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameBetween(String value1, String value2) {
            addCriterion("PRO_NAME between", value1, value2, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotBetween(String value1, String value2) {
            addCriterion("PRO_NAME not between", value1, value2, "proName");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(BigDecimal value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(BigDecimal value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(BigDecimal value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(BigDecimal value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<BigDecimal> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<BigDecimal> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andBusProCodeIsNull() {
            addCriterion("BUS_PRO_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBusProCodeIsNotNull() {
            addCriterion("BUS_PRO_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBusProCodeEqualTo(String value) {
            addCriterion("BUS_PRO_CODE =", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeNotEqualTo(String value) {
            addCriterion("BUS_PRO_CODE <>", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeGreaterThan(String value) {
            addCriterion("BUS_PRO_CODE >", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_PRO_CODE >=", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeLessThan(String value) {
            addCriterion("BUS_PRO_CODE <", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeLessThanOrEqualTo(String value) {
            addCriterion("BUS_PRO_CODE <=", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeLike(String value) {
            addCriterion("BUS_PRO_CODE like", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeNotLike(String value) {
            addCriterion("BUS_PRO_CODE not like", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeIn(List<String> values) {
            addCriterion("BUS_PRO_CODE in", values, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeNotIn(List<String> values) {
            addCriterion("BUS_PRO_CODE not in", values, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeBetween(String value1, String value2) {
            addCriterion("BUS_PRO_CODE between", value1, value2, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeNotBetween(String value1, String value2) {
            addCriterion("BUS_PRO_CODE not between", value1, value2, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProNameIsNull() {
            addCriterion("BUS_PRO_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBusProNameIsNotNull() {
            addCriterion("BUS_PRO_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBusProNameEqualTo(String value) {
            addCriterion("BUS_PRO_NAME =", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameNotEqualTo(String value) {
            addCriterion("BUS_PRO_NAME <>", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameGreaterThan(String value) {
            addCriterion("BUS_PRO_NAME >", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_PRO_NAME >=", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameLessThan(String value) {
            addCriterion("BUS_PRO_NAME <", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameLessThanOrEqualTo(String value) {
            addCriterion("BUS_PRO_NAME <=", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameLike(String value) {
            addCriterion("BUS_PRO_NAME like", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameNotLike(String value) {
            addCriterion("BUS_PRO_NAME not like", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameIn(List<String> values) {
            addCriterion("BUS_PRO_NAME in", values, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameNotIn(List<String> values) {
            addCriterion("BUS_PRO_NAME not in", values, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameBetween(String value1, String value2) {
            addCriterion("BUS_PRO_NAME between", value1, value2, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameNotBetween(String value1, String value2) {
            addCriterion("BUS_PRO_NAME not between", value1, value2, "busProName");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeIsNull() {
            addCriterion("TERM_BATCHCODE is null");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeIsNotNull() {
            addCriterion("TERM_BATCHCODE is not null");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeEqualTo(String value) {
            addCriterion("TERM_BATCHCODE =", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeNotEqualTo(String value) {
            addCriterion("TERM_BATCHCODE <>", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeGreaterThan(String value) {
            addCriterion("TERM_BATCHCODE >", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeGreaterThanOrEqualTo(String value) {
            addCriterion("TERM_BATCHCODE >=", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeLessThan(String value) {
            addCriterion("TERM_BATCHCODE <", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeLessThanOrEqualTo(String value) {
            addCriterion("TERM_BATCHCODE <=", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeLike(String value) {
            addCriterion("TERM_BATCHCODE like", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeNotLike(String value) {
            addCriterion("TERM_BATCHCODE not like", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeIn(List<String> values) {
            addCriterion("TERM_BATCHCODE in", values, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeNotIn(List<String> values) {
            addCriterion("TERM_BATCHCODE not in", values, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeBetween(String value1, String value2) {
            addCriterion("TERM_BATCHCODE between", value1, value2, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeNotBetween(String value1, String value2) {
            addCriterion("TERM_BATCHCODE not between", value1, value2, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameIsNull() {
            addCriterion("TERM_BATCHNAME is null");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameIsNotNull() {
            addCriterion("TERM_BATCHNAME is not null");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameEqualTo(String value) {
            addCriterion("TERM_BATCHNAME =", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameNotEqualTo(String value) {
            addCriterion("TERM_BATCHNAME <>", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameGreaterThan(String value) {
            addCriterion("TERM_BATCHNAME >", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameGreaterThanOrEqualTo(String value) {
            addCriterion("TERM_BATCHNAME >=", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameLessThan(String value) {
            addCriterion("TERM_BATCHNAME <", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameLessThanOrEqualTo(String value) {
            addCriterion("TERM_BATCHNAME <=", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameLike(String value) {
            addCriterion("TERM_BATCHNAME like", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameNotLike(String value) {
            addCriterion("TERM_BATCHNAME not like", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameIn(List<String> values) {
            addCriterion("TERM_BATCHNAME in", values, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameNotIn(List<String> values) {
            addCriterion("TERM_BATCHNAME not in", values, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameBetween(String value1, String value2) {
            addCriterion("TERM_BATCHNAME between", value1, value2, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameNotBetween(String value1, String value2) {
            addCriterion("TERM_BATCHNAME not between", value1, value2, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermtypeIsNull() {
            addCriterion("TERMTYPE is null");
            return (Criteria) this;
        }

        public Criteria andTermtypeIsNotNull() {
            addCriterion("TERMTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTermtypeEqualTo(String value) {
            addCriterion("TERMTYPE =", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeNotEqualTo(String value) {
            addCriterion("TERMTYPE <>", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeGreaterThan(String value) {
            addCriterion("TERMTYPE >", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeGreaterThanOrEqualTo(String value) {
            addCriterion("TERMTYPE >=", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeLessThan(String value) {
            addCriterion("TERMTYPE <", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeLessThanOrEqualTo(String value) {
            addCriterion("TERMTYPE <=", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeLike(String value) {
            addCriterion("TERMTYPE like", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeNotLike(String value) {
            addCriterion("TERMTYPE not like", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeIn(List<String> values) {
            addCriterion("TERMTYPE in", values, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeNotIn(List<String> values) {
            addCriterion("TERMTYPE not in", values, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeBetween(String value1, String value2) {
            addCriterion("TERMTYPE between", value1, value2, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeNotBetween(String value1, String value2) {
            addCriterion("TERMTYPE not between", value1, value2, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypenameIsNull() {
            addCriterion("TERMTYPENAME is null");
            return (Criteria) this;
        }

        public Criteria andTermtypenameIsNotNull() {
            addCriterion("TERMTYPENAME is not null");
            return (Criteria) this;
        }

        public Criteria andTermtypenameEqualTo(String value) {
            addCriterion("TERMTYPENAME =", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameNotEqualTo(String value) {
            addCriterion("TERMTYPENAME <>", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameGreaterThan(String value) {
            addCriterion("TERMTYPENAME >", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameGreaterThanOrEqualTo(String value) {
            addCriterion("TERMTYPENAME >=", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameLessThan(String value) {
            addCriterion("TERMTYPENAME <", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameLessThanOrEqualTo(String value) {
            addCriterion("TERMTYPENAME <=", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameLike(String value) {
            addCriterion("TERMTYPENAME like", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameNotLike(String value) {
            addCriterion("TERMTYPENAME not like", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameIn(List<String> values) {
            addCriterion("TERMTYPENAME in", values, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameNotIn(List<String> values) {
            addCriterion("TERMTYPENAME not in", values, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameBetween(String value1, String value2) {
            addCriterion("TERMTYPENAME between", value1, value2, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameNotBetween(String value1, String value2) {
            addCriterion("TERMTYPENAME not between", value1, value2, "termtypename");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceIsNull() {
            addCriterion("ORIGINAL_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceIsNotNull() {
            addCriterion("ORIGINAL_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceEqualTo(BigDecimal value) {
            addCriterion("ORIGINAL_PRICE =", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceNotEqualTo(BigDecimal value) {
            addCriterion("ORIGINAL_PRICE <>", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceGreaterThan(BigDecimal value) {
            addCriterion("ORIGINAL_PRICE >", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORIGINAL_PRICE >=", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceLessThan(BigDecimal value) {
            addCriterion("ORIGINAL_PRICE <", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORIGINAL_PRICE <=", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceIn(List<BigDecimal> values) {
            addCriterion("ORIGINAL_PRICE in", values, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceNotIn(List<BigDecimal> values) {
            addCriterion("ORIGINAL_PRICE not in", values, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORIGINAL_PRICE between", value1, value2, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORIGINAL_PRICE not between", value1, value2, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceIsNull() {
            addCriterion("POS_SPE_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceIsNotNull() {
            addCriterion("POS_SPE_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceEqualTo(BigDecimal value) {
            addCriterion("POS_SPE_PRICE =", value, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceNotEqualTo(BigDecimal value) {
            addCriterion("POS_SPE_PRICE <>", value, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceGreaterThan(BigDecimal value) {
            addCriterion("POS_SPE_PRICE >", value, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_SPE_PRICE >=", value, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceLessThan(BigDecimal value) {
            addCriterion("POS_SPE_PRICE <", value, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_SPE_PRICE <=", value, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceIn(List<BigDecimal> values) {
            addCriterion("POS_SPE_PRICE in", values, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceNotIn(List<BigDecimal> values) {
            addCriterion("POS_SPE_PRICE not in", values, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_SPE_PRICE between", value1, value2, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_SPE_PRICE not between", value1, value2, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosTypeIsNull() {
            addCriterion("POS_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPosTypeIsNotNull() {
            addCriterion("POS_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPosTypeEqualTo(String value) {
            addCriterion("POS_TYPE =", value, "posType");
            return (Criteria) this;
        }

        public Criteria andPosTypeNotEqualTo(String value) {
            addCriterion("POS_TYPE <>", value, "posType");
            return (Criteria) this;
        }

        public Criteria andPosTypeGreaterThan(String value) {
            addCriterion("POS_TYPE >", value, "posType");
            return (Criteria) this;
        }

        public Criteria andPosTypeGreaterThanOrEqualTo(String value) {
            addCriterion("POS_TYPE >=", value, "posType");
            return (Criteria) this;
        }

        public Criteria andPosTypeLessThan(String value) {
            addCriterion("POS_TYPE <", value, "posType");
            return (Criteria) this;
        }

        public Criteria andPosTypeLessThanOrEqualTo(String value) {
            addCriterion("POS_TYPE <=", value, "posType");
            return (Criteria) this;
        }

        public Criteria andPosTypeLike(String value) {
            addCriterion("POS_TYPE like", value, "posType");
            return (Criteria) this;
        }

        public Criteria andPosTypeNotLike(String value) {
            addCriterion("POS_TYPE not like", value, "posType");
            return (Criteria) this;
        }

        public Criteria andPosTypeIn(List<String> values) {
            addCriterion("POS_TYPE in", values, "posType");
            return (Criteria) this;
        }

        public Criteria andPosTypeNotIn(List<String> values) {
            addCriterion("POS_TYPE not in", values, "posType");
            return (Criteria) this;
        }

        public Criteria andPosTypeBetween(String value1, String value2) {
            addCriterion("POS_TYPE between", value1, value2, "posType");
            return (Criteria) this;
        }

        public Criteria andPosTypeNotBetween(String value1, String value2) {
            addCriterion("POS_TYPE not between", value1, value2, "posType");
            return (Criteria) this;
        }

        public Criteria andStandTimeIsNull() {
            addCriterion("STAND_TIME is null");
            return (Criteria) this;
        }

        public Criteria andStandTimeIsNotNull() {
            addCriterion("STAND_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andStandTimeEqualTo(BigDecimal value) {
            addCriterion("STAND_TIME =", value, "standTime");
            return (Criteria) this;
        }

        public Criteria andStandTimeNotEqualTo(BigDecimal value) {
            addCriterion("STAND_TIME <>", value, "standTime");
            return (Criteria) this;
        }

        public Criteria andStandTimeGreaterThan(BigDecimal value) {
            addCriterion("STAND_TIME >", value, "standTime");
            return (Criteria) this;
        }

        public Criteria andStandTimeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STAND_TIME >=", value, "standTime");
            return (Criteria) this;
        }

        public Criteria andStandTimeLessThan(BigDecimal value) {
            addCriterion("STAND_TIME <", value, "standTime");
            return (Criteria) this;
        }

        public Criteria andStandTimeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STAND_TIME <=", value, "standTime");
            return (Criteria) this;
        }

        public Criteria andStandTimeIn(List<BigDecimal> values) {
            addCriterion("STAND_TIME in", values, "standTime");
            return (Criteria) this;
        }

        public Criteria andStandTimeNotIn(List<BigDecimal> values) {
            addCriterion("STAND_TIME not in", values, "standTime");
            return (Criteria) this;
        }

        public Criteria andStandTimeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STAND_TIME between", value1, value2, "standTime");
            return (Criteria) this;
        }

        public Criteria andStandTimeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STAND_TIME not between", value1, value2, "standTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}