package com.ryx.credit.machine.entity;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImsOrganReturnTemplateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ImsOrganReturnTemplateExample() {
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

        public Criteria andTemplateIdIsNull() {
            addCriterion("TEMPLATE_ID is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("TEMPLATE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(String value) {
            addCriterion("TEMPLATE_ID =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(String value) {
            addCriterion("TEMPLATE_ID <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(String value) {
            addCriterion("TEMPLATE_ID >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(String value) {
            addCriterion("TEMPLATE_ID >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(String value) {
            addCriterion("TEMPLATE_ID <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(String value) {
            addCriterion("TEMPLATE_ID <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLike(String value) {
            addCriterion("TEMPLATE_ID like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotLike(String value) {
            addCriterion("TEMPLATE_ID not like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<String> values) {
            addCriterion("TEMPLATE_ID in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<String> values) {
            addCriterion("TEMPLATE_ID not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(String value1, String value2) {
            addCriterion("TEMPLATE_ID between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(String value1, String value2) {
            addCriterion("TEMPLATE_ID not between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNull() {
            addCriterion("ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNotNull() {
            addCriterion("ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrgIdEqualTo(String value) {
            addCriterion("ORG_ID =", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotEqualTo(String value) {
            addCriterion("ORG_ID <>", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThan(String value) {
            addCriterion("ORG_ID >", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_ID >=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThan(String value) {
            addCriterion("ORG_ID <", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThanOrEqualTo(String value) {
            addCriterion("ORG_ID <=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLike(String value) {
            addCriterion("ORG_ID like", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotLike(String value) {
            addCriterion("ORG_ID not like", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIn(List<String> values) {
            addCriterion("ORG_ID in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotIn(List<String> values) {
            addCriterion("ORG_ID not in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdBetween(String value1, String value2) {
            addCriterion("ORG_ID between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotBetween(String value1, String value2) {
            addCriterion("ORG_ID not between", value1, value2, "orgId");
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

        public Criteria andActiveTemplateTypeIsNull() {
            addCriterion("ACTIVE_TEMPLATE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andActiveTemplateTypeIsNotNull() {
            addCriterion("ACTIVE_TEMPLATE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andActiveTemplateTypeEqualTo(String value) {
            addCriterion("ACTIVE_TEMPLATE_TYPE =", value, "activeTemplateType");
            return (Criteria) this;
        }

        public Criteria andActiveTemplateTypeNotEqualTo(String value) {
            addCriterion("ACTIVE_TEMPLATE_TYPE <>", value, "activeTemplateType");
            return (Criteria) this;
        }

        public Criteria andActiveTemplateTypeGreaterThan(String value) {
            addCriterion("ACTIVE_TEMPLATE_TYPE >", value, "activeTemplateType");
            return (Criteria) this;
        }

        public Criteria andActiveTemplateTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVE_TEMPLATE_TYPE >=", value, "activeTemplateType");
            return (Criteria) this;
        }

        public Criteria andActiveTemplateTypeLessThan(String value) {
            addCriterion("ACTIVE_TEMPLATE_TYPE <", value, "activeTemplateType");
            return (Criteria) this;
        }

        public Criteria andActiveTemplateTypeLessThanOrEqualTo(String value) {
            addCriterion("ACTIVE_TEMPLATE_TYPE <=", value, "activeTemplateType");
            return (Criteria) this;
        }

        public Criteria andActiveTemplateTypeLike(String value) {
            addCriterion("ACTIVE_TEMPLATE_TYPE like", value, "activeTemplateType");
            return (Criteria) this;
        }

        public Criteria andActiveTemplateTypeNotLike(String value) {
            addCriterion("ACTIVE_TEMPLATE_TYPE not like", value, "activeTemplateType");
            return (Criteria) this;
        }

        public Criteria andActiveTemplateTypeIn(List<String> values) {
            addCriterion("ACTIVE_TEMPLATE_TYPE in", values, "activeTemplateType");
            return (Criteria) this;
        }

        public Criteria andActiveTemplateTypeNotIn(List<String> values) {
            addCriterion("ACTIVE_TEMPLATE_TYPE not in", values, "activeTemplateType");
            return (Criteria) this;
        }

        public Criteria andActiveTemplateTypeBetween(String value1, String value2) {
            addCriterion("ACTIVE_TEMPLATE_TYPE between", value1, value2, "activeTemplateType");
            return (Criteria) this;
        }

        public Criteria andActiveTemplateTypeNotBetween(String value1, String value2) {
            addCriterion("ACTIVE_TEMPLATE_TYPE not between", value1, value2, "activeTemplateType");
            return (Criteria) this;
        }

        public Criteria andActiveBackAmountIsNull() {
            addCriterion("ACTIVE_BACK_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andActiveBackAmountIsNotNull() {
            addCriterion("ACTIVE_BACK_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andActiveBackAmountEqualTo(BigDecimal value) {
            addCriterion("ACTIVE_BACK_AMOUNT =", value, "activeBackAmount");
            return (Criteria) this;
        }

        public Criteria andActiveBackAmountNotEqualTo(BigDecimal value) {
            addCriterion("ACTIVE_BACK_AMOUNT <>", value, "activeBackAmount");
            return (Criteria) this;
        }

        public Criteria andActiveBackAmountGreaterThan(BigDecimal value) {
            addCriterion("ACTIVE_BACK_AMOUNT >", value, "activeBackAmount");
            return (Criteria) this;
        }

        public Criteria andActiveBackAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTIVE_BACK_AMOUNT >=", value, "activeBackAmount");
            return (Criteria) this;
        }

        public Criteria andActiveBackAmountLessThan(BigDecimal value) {
            addCriterion("ACTIVE_BACK_AMOUNT <", value, "activeBackAmount");
            return (Criteria) this;
        }

        public Criteria andActiveBackAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTIVE_BACK_AMOUNT <=", value, "activeBackAmount");
            return (Criteria) this;
        }

        public Criteria andActiveBackAmountIn(List<BigDecimal> values) {
            addCriterion("ACTIVE_BACK_AMOUNT in", values, "activeBackAmount");
            return (Criteria) this;
        }

        public Criteria andActiveBackAmountNotIn(List<BigDecimal> values) {
            addCriterion("ACTIVE_BACK_AMOUNT not in", values, "activeBackAmount");
            return (Criteria) this;
        }

        public Criteria andActiveBackAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTIVE_BACK_AMOUNT between", value1, value2, "activeBackAmount");
            return (Criteria) this;
        }

        public Criteria andActiveBackAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTIVE_BACK_AMOUNT not between", value1, value2, "activeBackAmount");
            return (Criteria) this;
        }

        public Criteria andActiveBackPercentageIsNull() {
            addCriterion("ACTIVE_BACK_PERCENTAGE is null");
            return (Criteria) this;
        }

        public Criteria andActiveBackPercentageIsNotNull() {
            addCriterion("ACTIVE_BACK_PERCENTAGE is not null");
            return (Criteria) this;
        }

        public Criteria andActiveBackPercentageEqualTo(BigDecimal value) {
            addCriterion("ACTIVE_BACK_PERCENTAGE =", value, "activeBackPercentage");
            return (Criteria) this;
        }

        public Criteria andActiveBackPercentageNotEqualTo(BigDecimal value) {
            addCriterion("ACTIVE_BACK_PERCENTAGE <>", value, "activeBackPercentage");
            return (Criteria) this;
        }

        public Criteria andActiveBackPercentageGreaterThan(BigDecimal value) {
            addCriterion("ACTIVE_BACK_PERCENTAGE >", value, "activeBackPercentage");
            return (Criteria) this;
        }

        public Criteria andActiveBackPercentageGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTIVE_BACK_PERCENTAGE >=", value, "activeBackPercentage");
            return (Criteria) this;
        }

        public Criteria andActiveBackPercentageLessThan(BigDecimal value) {
            addCriterion("ACTIVE_BACK_PERCENTAGE <", value, "activeBackPercentage");
            return (Criteria) this;
        }

        public Criteria andActiveBackPercentageLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTIVE_BACK_PERCENTAGE <=", value, "activeBackPercentage");
            return (Criteria) this;
        }

        public Criteria andActiveBackPercentageIn(List<BigDecimal> values) {
            addCriterion("ACTIVE_BACK_PERCENTAGE in", values, "activeBackPercentage");
            return (Criteria) this;
        }

        public Criteria andActiveBackPercentageNotIn(List<BigDecimal> values) {
            addCriterion("ACTIVE_BACK_PERCENTAGE not in", values, "activeBackPercentage");
            return (Criteria) this;
        }

        public Criteria andActiveBackPercentageBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTIVE_BACK_PERCENTAGE between", value1, value2, "activeBackPercentage");
            return (Criteria) this;
        }

        public Criteria andActiveBackPercentageNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTIVE_BACK_PERCENTAGE not between", value1, value2, "activeBackPercentage");
            return (Criteria) this;
        }

        public Criteria andStandTemplateTypeIsNull() {
            addCriterion("STAND_TEMPLATE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andStandTemplateTypeIsNotNull() {
            addCriterion("STAND_TEMPLATE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andStandTemplateTypeEqualTo(String value) {
            addCriterion("STAND_TEMPLATE_TYPE =", value, "standTemplateType");
            return (Criteria) this;
        }

        public Criteria andStandTemplateTypeNotEqualTo(String value) {
            addCriterion("STAND_TEMPLATE_TYPE <>", value, "standTemplateType");
            return (Criteria) this;
        }

        public Criteria andStandTemplateTypeGreaterThan(String value) {
            addCriterion("STAND_TEMPLATE_TYPE >", value, "standTemplateType");
            return (Criteria) this;
        }

        public Criteria andStandTemplateTypeGreaterThanOrEqualTo(String value) {
            addCriterion("STAND_TEMPLATE_TYPE >=", value, "standTemplateType");
            return (Criteria) this;
        }

        public Criteria andStandTemplateTypeLessThan(String value) {
            addCriterion("STAND_TEMPLATE_TYPE <", value, "standTemplateType");
            return (Criteria) this;
        }

        public Criteria andStandTemplateTypeLessThanOrEqualTo(String value) {
            addCriterion("STAND_TEMPLATE_TYPE <=", value, "standTemplateType");
            return (Criteria) this;
        }

        public Criteria andStandTemplateTypeLike(String value) {
            addCriterion("STAND_TEMPLATE_TYPE like", value, "standTemplateType");
            return (Criteria) this;
        }

        public Criteria andStandTemplateTypeNotLike(String value) {
            addCriterion("STAND_TEMPLATE_TYPE not like", value, "standTemplateType");
            return (Criteria) this;
        }

        public Criteria andStandTemplateTypeIn(List<String> values) {
            addCriterion("STAND_TEMPLATE_TYPE in", values, "standTemplateType");
            return (Criteria) this;
        }

        public Criteria andStandTemplateTypeNotIn(List<String> values) {
            addCriterion("STAND_TEMPLATE_TYPE not in", values, "standTemplateType");
            return (Criteria) this;
        }

        public Criteria andStandTemplateTypeBetween(String value1, String value2) {
            addCriterion("STAND_TEMPLATE_TYPE between", value1, value2, "standTemplateType");
            return (Criteria) this;
        }

        public Criteria andStandTemplateTypeNotBetween(String value1, String value2) {
            addCriterion("STAND_TEMPLATE_TYPE not between", value1, value2, "standTemplateType");
            return (Criteria) this;
        }

        public Criteria andStandBackAmountIsNull() {
            addCriterion("STAND_BACK_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andStandBackAmountIsNotNull() {
            addCriterion("STAND_BACK_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andStandBackAmountEqualTo(BigDecimal value) {
            addCriterion("STAND_BACK_AMOUNT =", value, "standBackAmount");
            return (Criteria) this;
        }

        public Criteria andStandBackAmountNotEqualTo(BigDecimal value) {
            addCriterion("STAND_BACK_AMOUNT <>", value, "standBackAmount");
            return (Criteria) this;
        }

        public Criteria andStandBackAmountGreaterThan(BigDecimal value) {
            addCriterion("STAND_BACK_AMOUNT >", value, "standBackAmount");
            return (Criteria) this;
        }

        public Criteria andStandBackAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STAND_BACK_AMOUNT >=", value, "standBackAmount");
            return (Criteria) this;
        }

        public Criteria andStandBackAmountLessThan(BigDecimal value) {
            addCriterion("STAND_BACK_AMOUNT <", value, "standBackAmount");
            return (Criteria) this;
        }

        public Criteria andStandBackAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STAND_BACK_AMOUNT <=", value, "standBackAmount");
            return (Criteria) this;
        }

        public Criteria andStandBackAmountIn(List<BigDecimal> values) {
            addCriterion("STAND_BACK_AMOUNT in", values, "standBackAmount");
            return (Criteria) this;
        }

        public Criteria andStandBackAmountNotIn(List<BigDecimal> values) {
            addCriterion("STAND_BACK_AMOUNT not in", values, "standBackAmount");
            return (Criteria) this;
        }

        public Criteria andStandBackAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STAND_BACK_AMOUNT between", value1, value2, "standBackAmount");
            return (Criteria) this;
        }

        public Criteria andStandBackAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STAND_BACK_AMOUNT not between", value1, value2, "standBackAmount");
            return (Criteria) this;
        }

        public Criteria andStandBackPercentageIsNull() {
            addCriterion("STAND_BACK_PERCENTAGE is null");
            return (Criteria) this;
        }

        public Criteria andStandBackPercentageIsNotNull() {
            addCriterion("STAND_BACK_PERCENTAGE is not null");
            return (Criteria) this;
        }

        public Criteria andStandBackPercentageEqualTo(BigDecimal value) {
            addCriterion("STAND_BACK_PERCENTAGE =", value, "standBackPercentage");
            return (Criteria) this;
        }

        public Criteria andStandBackPercentageNotEqualTo(BigDecimal value) {
            addCriterion("STAND_BACK_PERCENTAGE <>", value, "standBackPercentage");
            return (Criteria) this;
        }

        public Criteria andStandBackPercentageGreaterThan(BigDecimal value) {
            addCriterion("STAND_BACK_PERCENTAGE >", value, "standBackPercentage");
            return (Criteria) this;
        }

        public Criteria andStandBackPercentageGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STAND_BACK_PERCENTAGE >=", value, "standBackPercentage");
            return (Criteria) this;
        }

        public Criteria andStandBackPercentageLessThan(BigDecimal value) {
            addCriterion("STAND_BACK_PERCENTAGE <", value, "standBackPercentage");
            return (Criteria) this;
        }

        public Criteria andStandBackPercentageLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STAND_BACK_PERCENTAGE <=", value, "standBackPercentage");
            return (Criteria) this;
        }

        public Criteria andStandBackPercentageIn(List<BigDecimal> values) {
            addCriterion("STAND_BACK_PERCENTAGE in", values, "standBackPercentage");
            return (Criteria) this;
        }

        public Criteria andStandBackPercentageNotIn(List<BigDecimal> values) {
            addCriterion("STAND_BACK_PERCENTAGE not in", values, "standBackPercentage");
            return (Criteria) this;
        }

        public Criteria andStandBackPercentageBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STAND_BACK_PERCENTAGE between", value1, value2, "standBackPercentage");
            return (Criteria) this;
        }

        public Criteria andStandBackPercentageNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STAND_BACK_PERCENTAGE not between", value1, value2, "standBackPercentage");
            return (Criteria) this;
        }

        public Criteria andBonusTemplateTypeIsNull() {
            addCriterion("BONUS_TEMPLATE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBonusTemplateTypeIsNotNull() {
            addCriterion("BONUS_TEMPLATE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBonusTemplateTypeEqualTo(String value) {
            addCriterion("BONUS_TEMPLATE_TYPE =", value, "bonusTemplateType");
            return (Criteria) this;
        }

        public Criteria andBonusTemplateTypeNotEqualTo(String value) {
            addCriterion("BONUS_TEMPLATE_TYPE <>", value, "bonusTemplateType");
            return (Criteria) this;
        }

        public Criteria andBonusTemplateTypeGreaterThan(String value) {
            addCriterion("BONUS_TEMPLATE_TYPE >", value, "bonusTemplateType");
            return (Criteria) this;
        }

        public Criteria andBonusTemplateTypeGreaterThanOrEqualTo(String value) {
            addCriterion("BONUS_TEMPLATE_TYPE >=", value, "bonusTemplateType");
            return (Criteria) this;
        }

        public Criteria andBonusTemplateTypeLessThan(String value) {
            addCriterion("BONUS_TEMPLATE_TYPE <", value, "bonusTemplateType");
            return (Criteria) this;
        }

        public Criteria andBonusTemplateTypeLessThanOrEqualTo(String value) {
            addCriterion("BONUS_TEMPLATE_TYPE <=", value, "bonusTemplateType");
            return (Criteria) this;
        }

        public Criteria andBonusTemplateTypeLike(String value) {
            addCriterion("BONUS_TEMPLATE_TYPE like", value, "bonusTemplateType");
            return (Criteria) this;
        }

        public Criteria andBonusTemplateTypeNotLike(String value) {
            addCriterion("BONUS_TEMPLATE_TYPE not like", value, "bonusTemplateType");
            return (Criteria) this;
        }

        public Criteria andBonusTemplateTypeIn(List<String> values) {
            addCriterion("BONUS_TEMPLATE_TYPE in", values, "bonusTemplateType");
            return (Criteria) this;
        }

        public Criteria andBonusTemplateTypeNotIn(List<String> values) {
            addCriterion("BONUS_TEMPLATE_TYPE not in", values, "bonusTemplateType");
            return (Criteria) this;
        }

        public Criteria andBonusTemplateTypeBetween(String value1, String value2) {
            addCriterion("BONUS_TEMPLATE_TYPE between", value1, value2, "bonusTemplateType");
            return (Criteria) this;
        }

        public Criteria andBonusTemplateTypeNotBetween(String value1, String value2) {
            addCriterion("BONUS_TEMPLATE_TYPE not between", value1, value2, "bonusTemplateType");
            return (Criteria) this;
        }

        public Criteria andBonusBackAmountIsNull() {
            addCriterion("BONUS_BACK_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andBonusBackAmountIsNotNull() {
            addCriterion("BONUS_BACK_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andBonusBackAmountEqualTo(BigDecimal value) {
            addCriterion("BONUS_BACK_AMOUNT =", value, "bonusBackAmount");
            return (Criteria) this;
        }

        public Criteria andBonusBackAmountNotEqualTo(BigDecimal value) {
            addCriterion("BONUS_BACK_AMOUNT <>", value, "bonusBackAmount");
            return (Criteria) this;
        }

        public Criteria andBonusBackAmountGreaterThan(BigDecimal value) {
            addCriterion("BONUS_BACK_AMOUNT >", value, "bonusBackAmount");
            return (Criteria) this;
        }

        public Criteria andBonusBackAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BONUS_BACK_AMOUNT >=", value, "bonusBackAmount");
            return (Criteria) this;
        }

        public Criteria andBonusBackAmountLessThan(BigDecimal value) {
            addCriterion("BONUS_BACK_AMOUNT <", value, "bonusBackAmount");
            return (Criteria) this;
        }

        public Criteria andBonusBackAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BONUS_BACK_AMOUNT <=", value, "bonusBackAmount");
            return (Criteria) this;
        }

        public Criteria andBonusBackAmountIn(List<BigDecimal> values) {
            addCriterion("BONUS_BACK_AMOUNT in", values, "bonusBackAmount");
            return (Criteria) this;
        }

        public Criteria andBonusBackAmountNotIn(List<BigDecimal> values) {
            addCriterion("BONUS_BACK_AMOUNT not in", values, "bonusBackAmount");
            return (Criteria) this;
        }

        public Criteria andBonusBackAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BONUS_BACK_AMOUNT between", value1, value2, "bonusBackAmount");
            return (Criteria) this;
        }

        public Criteria andBonusBackAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BONUS_BACK_AMOUNT not between", value1, value2, "bonusBackAmount");
            return (Criteria) this;
        }

        public Criteria andBonusBackPercentageIsNull() {
            addCriterion("BONUS_BACK_PERCENTAGE is null");
            return (Criteria) this;
        }

        public Criteria andBonusBackPercentageIsNotNull() {
            addCriterion("BONUS_BACK_PERCENTAGE is not null");
            return (Criteria) this;
        }

        public Criteria andBonusBackPercentageEqualTo(BigDecimal value) {
            addCriterion("BONUS_BACK_PERCENTAGE =", value, "bonusBackPercentage");
            return (Criteria) this;
        }

        public Criteria andBonusBackPercentageNotEqualTo(BigDecimal value) {
            addCriterion("BONUS_BACK_PERCENTAGE <>", value, "bonusBackPercentage");
            return (Criteria) this;
        }

        public Criteria andBonusBackPercentageGreaterThan(BigDecimal value) {
            addCriterion("BONUS_BACK_PERCENTAGE >", value, "bonusBackPercentage");
            return (Criteria) this;
        }

        public Criteria andBonusBackPercentageGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BONUS_BACK_PERCENTAGE >=", value, "bonusBackPercentage");
            return (Criteria) this;
        }

        public Criteria andBonusBackPercentageLessThan(BigDecimal value) {
            addCriterion("BONUS_BACK_PERCENTAGE <", value, "bonusBackPercentage");
            return (Criteria) this;
        }

        public Criteria andBonusBackPercentageLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BONUS_BACK_PERCENTAGE <=", value, "bonusBackPercentage");
            return (Criteria) this;
        }

        public Criteria andBonusBackPercentageIn(List<BigDecimal> values) {
            addCriterion("BONUS_BACK_PERCENTAGE in", values, "bonusBackPercentage");
            return (Criteria) this;
        }

        public Criteria andBonusBackPercentageNotIn(List<BigDecimal> values) {
            addCriterion("BONUS_BACK_PERCENTAGE not in", values, "bonusBackPercentage");
            return (Criteria) this;
        }

        public Criteria andBonusBackPercentageBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BONUS_BACK_PERCENTAGE between", value1, value2, "bonusBackPercentage");
            return (Criteria) this;
        }

        public Criteria andBonusBackPercentageNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BONUS_BACK_PERCENTAGE not between", value1, value2, "bonusBackPercentage");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("STATUS like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("STATUS not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("CREATE_USER is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("CREATE_USER is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("CREATE_USER =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("CREATE_USER <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("CREATE_USER >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_USER >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("CREATE_USER <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("CREATE_USER <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("CREATE_USER like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("CREATE_USER not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("CREATE_USER in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("CREATE_USER not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("CREATE_USER between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("CREATE_USER not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("UPDATE_USER is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("UPDATE_USER is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("UPDATE_USER =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("UPDATE_USER <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("UPDATE_USER >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_USER >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("UPDATE_USER <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_USER <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("UPDATE_USER like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("UPDATE_USER not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("UPDATE_USER in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("UPDATE_USER not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("UPDATE_USER between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("UPDATE_USER not between", value1, value2, "updateUser");
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