package com.ryx.credit.machine.entity;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImsMachineActivityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ImsMachineActivityExample() {
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

        public Criteria andBrandCodeIsNull() {
            addCriterion("BRAND_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBrandCodeIsNotNull() {
            addCriterion("BRAND_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBrandCodeEqualTo(String value) {
            addCriterion("BRAND_CODE =", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeNotEqualTo(String value) {
            addCriterion("BRAND_CODE <>", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeGreaterThan(String value) {
            addCriterion("BRAND_CODE >", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BRAND_CODE >=", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeLessThan(String value) {
            addCriterion("BRAND_CODE <", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeLessThanOrEqualTo(String value) {
            addCriterion("BRAND_CODE <=", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeLike(String value) {
            addCriterion("BRAND_CODE like", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeNotLike(String value) {
            addCriterion("BRAND_CODE not like", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeIn(List<String> values) {
            addCriterion("BRAND_CODE in", values, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeNotIn(List<String> values) {
            addCriterion("BRAND_CODE not in", values, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeBetween(String value1, String value2) {
            addCriterion("BRAND_CODE between", value1, value2, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeNotBetween(String value1, String value2) {
            addCriterion("BRAND_CODE not between", value1, value2, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandNameIsNull() {
            addCriterion("BRAND_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBrandNameIsNotNull() {
            addCriterion("BRAND_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBrandNameEqualTo(String value) {
            addCriterion("BRAND_NAME =", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotEqualTo(String value) {
            addCriterion("BRAND_NAME <>", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameGreaterThan(String value) {
            addCriterion("BRAND_NAME >", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameGreaterThanOrEqualTo(String value) {
            addCriterion("BRAND_NAME >=", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLessThan(String value) {
            addCriterion("BRAND_NAME <", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLessThanOrEqualTo(String value) {
            addCriterion("BRAND_NAME <=", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLike(String value) {
            addCriterion("BRAND_NAME like", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotLike(String value) {
            addCriterion("BRAND_NAME not like", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameIn(List<String> values) {
            addCriterion("BRAND_NAME in", values, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotIn(List<String> values) {
            addCriterion("BRAND_NAME not in", values, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameBetween(String value1, String value2) {
            addCriterion("BRAND_NAME between", value1, value2, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotBetween(String value1, String value2) {
            addCriterion("BRAND_NAME not between", value1, value2, "brandName");
            return (Criteria) this;
        }

        public Criteria andActivityStartTimeIsNull() {
            addCriterion("ACTIVITY_START_TIME is null");
            return (Criteria) this;
        }

        public Criteria andActivityStartTimeIsNotNull() {
            addCriterion("ACTIVITY_START_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andActivityStartTimeEqualTo(String value) {
            addCriterion("ACTIVITY_START_TIME =", value, "activityStartTime");
            return (Criteria) this;
        }

        public Criteria andActivityStartTimeNotEqualTo(String value) {
            addCriterion("ACTIVITY_START_TIME <>", value, "activityStartTime");
            return (Criteria) this;
        }

        public Criteria andActivityStartTimeGreaterThan(String value) {
            addCriterion("ACTIVITY_START_TIME >", value, "activityStartTime");
            return (Criteria) this;
        }

        public Criteria andActivityStartTimeGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_START_TIME >=", value, "activityStartTime");
            return (Criteria) this;
        }

        public Criteria andActivityStartTimeLessThan(String value) {
            addCriterion("ACTIVITY_START_TIME <", value, "activityStartTime");
            return (Criteria) this;
        }

        public Criteria andActivityStartTimeLessThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_START_TIME <=", value, "activityStartTime");
            return (Criteria) this;
        }

        public Criteria andActivityStartTimeLike(String value) {
            addCriterion("ACTIVITY_START_TIME like", value, "activityStartTime");
            return (Criteria) this;
        }

        public Criteria andActivityStartTimeNotLike(String value) {
            addCriterion("ACTIVITY_START_TIME not like", value, "activityStartTime");
            return (Criteria) this;
        }

        public Criteria andActivityStartTimeIn(List<String> values) {
            addCriterion("ACTIVITY_START_TIME in", values, "activityStartTime");
            return (Criteria) this;
        }

        public Criteria andActivityStartTimeNotIn(List<String> values) {
            addCriterion("ACTIVITY_START_TIME not in", values, "activityStartTime");
            return (Criteria) this;
        }

        public Criteria andActivityStartTimeBetween(String value1, String value2) {
            addCriterion("ACTIVITY_START_TIME between", value1, value2, "activityStartTime");
            return (Criteria) this;
        }

        public Criteria andActivityStartTimeNotBetween(String value1, String value2) {
            addCriterion("ACTIVITY_START_TIME not between", value1, value2, "activityStartTime");
            return (Criteria) this;
        }

        public Criteria andActivityEndTimeIsNull() {
            addCriterion("ACTIVITY_END_TIME is null");
            return (Criteria) this;
        }

        public Criteria andActivityEndTimeIsNotNull() {
            addCriterion("ACTIVITY_END_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andActivityEndTimeEqualTo(String value) {
            addCriterion("ACTIVITY_END_TIME =", value, "activityEndTime");
            return (Criteria) this;
        }

        public Criteria andActivityEndTimeNotEqualTo(String value) {
            addCriterion("ACTIVITY_END_TIME <>", value, "activityEndTime");
            return (Criteria) this;
        }

        public Criteria andActivityEndTimeGreaterThan(String value) {
            addCriterion("ACTIVITY_END_TIME >", value, "activityEndTime");
            return (Criteria) this;
        }

        public Criteria andActivityEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_END_TIME >=", value, "activityEndTime");
            return (Criteria) this;
        }

        public Criteria andActivityEndTimeLessThan(String value) {
            addCriterion("ACTIVITY_END_TIME <", value, "activityEndTime");
            return (Criteria) this;
        }

        public Criteria andActivityEndTimeLessThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_END_TIME <=", value, "activityEndTime");
            return (Criteria) this;
        }

        public Criteria andActivityEndTimeLike(String value) {
            addCriterion("ACTIVITY_END_TIME like", value, "activityEndTime");
            return (Criteria) this;
        }

        public Criteria andActivityEndTimeNotLike(String value) {
            addCriterion("ACTIVITY_END_TIME not like", value, "activityEndTime");
            return (Criteria) this;
        }

        public Criteria andActivityEndTimeIn(List<String> values) {
            addCriterion("ACTIVITY_END_TIME in", values, "activityEndTime");
            return (Criteria) this;
        }

        public Criteria andActivityEndTimeNotIn(List<String> values) {
            addCriterion("ACTIVITY_END_TIME not in", values, "activityEndTime");
            return (Criteria) this;
        }

        public Criteria andActivityEndTimeBetween(String value1, String value2) {
            addCriterion("ACTIVITY_END_TIME between", value1, value2, "activityEndTime");
            return (Criteria) this;
        }

        public Criteria andActivityEndTimeNotBetween(String value1, String value2) {
            addCriterion("ACTIVITY_END_TIME not between", value1, value2, "activityEndTime");
            return (Criteria) this;
        }

        public Criteria andActivityTypeIsNull() {
            addCriterion("ACTIVITY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andActivityTypeIsNotNull() {
            addCriterion("ACTIVITY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andActivityTypeEqualTo(String value) {
            addCriterion("ACTIVITY_TYPE =", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeNotEqualTo(String value) {
            addCriterion("ACTIVITY_TYPE <>", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeGreaterThan(String value) {
            addCriterion("ACTIVITY_TYPE >", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_TYPE >=", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeLessThan(String value) {
            addCriterion("ACTIVITY_TYPE <", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeLessThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_TYPE <=", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeLike(String value) {
            addCriterion("ACTIVITY_TYPE like", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeNotLike(String value) {
            addCriterion("ACTIVITY_TYPE not like", value, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeIn(List<String> values) {
            addCriterion("ACTIVITY_TYPE in", values, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeNotIn(List<String> values) {
            addCriterion("ACTIVITY_TYPE not in", values, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeBetween(String value1, String value2) {
            addCriterion("ACTIVITY_TYPE between", value1, value2, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivityTypeNotBetween(String value1, String value2) {
            addCriterion("ACTIVITY_TYPE not between", value1, value2, "activityType");
            return (Criteria) this;
        }

        public Criteria andActivateTypeIsNull() {
            addCriterion("ACTIVATE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andActivateTypeIsNotNull() {
            addCriterion("ACTIVATE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andActivateTypeEqualTo(String value) {
            addCriterion("ACTIVATE_TYPE =", value, "activateType");
            return (Criteria) this;
        }

        public Criteria andActivateTypeNotEqualTo(String value) {
            addCriterion("ACTIVATE_TYPE <>", value, "activateType");
            return (Criteria) this;
        }

        public Criteria andActivateTypeGreaterThan(String value) {
            addCriterion("ACTIVATE_TYPE >", value, "activateType");
            return (Criteria) this;
        }

        public Criteria andActivateTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVATE_TYPE >=", value, "activateType");
            return (Criteria) this;
        }

        public Criteria andActivateTypeLessThan(String value) {
            addCriterion("ACTIVATE_TYPE <", value, "activateType");
            return (Criteria) this;
        }

        public Criteria andActivateTypeLessThanOrEqualTo(String value) {
            addCriterion("ACTIVATE_TYPE <=", value, "activateType");
            return (Criteria) this;
        }

        public Criteria andActivateTypeLike(String value) {
            addCriterion("ACTIVATE_TYPE like", value, "activateType");
            return (Criteria) this;
        }

        public Criteria andActivateTypeNotLike(String value) {
            addCriterion("ACTIVATE_TYPE not like", value, "activateType");
            return (Criteria) this;
        }

        public Criteria andActivateTypeIn(List<String> values) {
            addCriterion("ACTIVATE_TYPE in", values, "activateType");
            return (Criteria) this;
        }

        public Criteria andActivateTypeNotIn(List<String> values) {
            addCriterion("ACTIVATE_TYPE not in", values, "activateType");
            return (Criteria) this;
        }

        public Criteria andActivateTypeBetween(String value1, String value2) {
            addCriterion("ACTIVATE_TYPE between", value1, value2, "activateType");
            return (Criteria) this;
        }

        public Criteria andActivateTypeNotBetween(String value1, String value2) {
            addCriterion("ACTIVATE_TYPE not between", value1, value2, "activateType");
            return (Criteria) this;
        }

        public Criteria andDepositIsNull() {
            addCriterion("DEPOSIT is null");
            return (Criteria) this;
        }

        public Criteria andDepositIsNotNull() {
            addCriterion("DEPOSIT is not null");
            return (Criteria) this;
        }

        public Criteria andDepositEqualTo(BigDecimal value) {
            addCriterion("DEPOSIT =", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotEqualTo(BigDecimal value) {
            addCriterion("DEPOSIT <>", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositGreaterThan(BigDecimal value) {
            addCriterion("DEPOSIT >", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DEPOSIT >=", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositLessThan(BigDecimal value) {
            addCriterion("DEPOSIT <", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DEPOSIT <=", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositIn(List<BigDecimal> values) {
            addCriterion("DEPOSIT in", values, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotIn(List<BigDecimal> values) {
            addCriterion("DEPOSIT not in", values, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEPOSIT between", value1, value2, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEPOSIT not between", value1, value2, "deposit");
            return (Criteria) this;
        }

        public Criteria andCheckTypeIsNull() {
            addCriterion("CHECK_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCheckTypeIsNotNull() {
            addCriterion("CHECK_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTypeEqualTo(String value) {
            addCriterion("CHECK_TYPE =", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeNotEqualTo(String value) {
            addCriterion("CHECK_TYPE <>", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeGreaterThan(String value) {
            addCriterion("CHECK_TYPE >", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeGreaterThanOrEqualTo(String value) {
            addCriterion("CHECK_TYPE >=", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeLessThan(String value) {
            addCriterion("CHECK_TYPE <", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeLessThanOrEqualTo(String value) {
            addCriterion("CHECK_TYPE <=", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeLike(String value) {
            addCriterion("CHECK_TYPE like", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeNotLike(String value) {
            addCriterion("CHECK_TYPE not like", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeIn(List<String> values) {
            addCriterion("CHECK_TYPE in", values, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeNotIn(List<String> values) {
            addCriterion("CHECK_TYPE not in", values, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeBetween(String value1, String value2) {
            addCriterion("CHECK_TYPE between", value1, value2, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeNotBetween(String value1, String value2) {
            addCriterion("CHECK_TYPE not between", value1, value2, "checkType");
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

        public Criteria andStandAmtIsNull() {
            addCriterion("STAND_AMT is null");
            return (Criteria) this;
        }

        public Criteria andStandAmtIsNotNull() {
            addCriterion("STAND_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andStandAmtEqualTo(BigDecimal value) {
            addCriterion("STAND_AMT =", value, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtNotEqualTo(BigDecimal value) {
            addCriterion("STAND_AMT <>", value, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtGreaterThan(BigDecimal value) {
            addCriterion("STAND_AMT >", value, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STAND_AMT >=", value, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtLessThan(BigDecimal value) {
            addCriterion("STAND_AMT <", value, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STAND_AMT <=", value, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtIn(List<BigDecimal> values) {
            addCriterion("STAND_AMT in", values, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtNotIn(List<BigDecimal> values) {
            addCriterion("STAND_AMT not in", values, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STAND_AMT between", value1, value2, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STAND_AMT not between", value1, value2, "standAmt");
            return (Criteria) this;
        }

        public Criteria andSettlementTypeIsNull() {
            addCriterion("SETTLEMENT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSettlementTypeIsNotNull() {
            addCriterion("SETTLEMENT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSettlementTypeEqualTo(String value) {
            addCriterion("SETTLEMENT_TYPE =", value, "settlementType");
            return (Criteria) this;
        }

        public Criteria andSettlementTypeNotEqualTo(String value) {
            addCriterion("SETTLEMENT_TYPE <>", value, "settlementType");
            return (Criteria) this;
        }

        public Criteria andSettlementTypeGreaterThan(String value) {
            addCriterion("SETTLEMENT_TYPE >", value, "settlementType");
            return (Criteria) this;
        }

        public Criteria andSettlementTypeGreaterThanOrEqualTo(String value) {
            addCriterion("SETTLEMENT_TYPE >=", value, "settlementType");
            return (Criteria) this;
        }

        public Criteria andSettlementTypeLessThan(String value) {
            addCriterion("SETTLEMENT_TYPE <", value, "settlementType");
            return (Criteria) this;
        }

        public Criteria andSettlementTypeLessThanOrEqualTo(String value) {
            addCriterion("SETTLEMENT_TYPE <=", value, "settlementType");
            return (Criteria) this;
        }

        public Criteria andSettlementTypeLike(String value) {
            addCriterion("SETTLEMENT_TYPE like", value, "settlementType");
            return (Criteria) this;
        }

        public Criteria andSettlementTypeNotLike(String value) {
            addCriterion("SETTLEMENT_TYPE not like", value, "settlementType");
            return (Criteria) this;
        }

        public Criteria andSettlementTypeIn(List<String> values) {
            addCriterion("SETTLEMENT_TYPE in", values, "settlementType");
            return (Criteria) this;
        }

        public Criteria andSettlementTypeNotIn(List<String> values) {
            addCriterion("SETTLEMENT_TYPE not in", values, "settlementType");
            return (Criteria) this;
        }

        public Criteria andSettlementTypeBetween(String value1, String value2) {
            addCriterion("SETTLEMENT_TYPE between", value1, value2, "settlementType");
            return (Criteria) this;
        }

        public Criteria andSettlementTypeNotBetween(String value1, String value2) {
            addCriterion("SETTLEMENT_TYPE not between", value1, value2, "settlementType");
            return (Criteria) this;
        }

        public Criteria andActiveReturnTypeIsNull() {
            addCriterion("ACTIVE_RETURN_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andActiveReturnTypeIsNotNull() {
            addCriterion("ACTIVE_RETURN_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andActiveReturnTypeEqualTo(String value) {
            addCriterion("ACTIVE_RETURN_TYPE =", value, "activeReturnType");
            return (Criteria) this;
        }

        public Criteria andActiveReturnTypeNotEqualTo(String value) {
            addCriterion("ACTIVE_RETURN_TYPE <>", value, "activeReturnType");
            return (Criteria) this;
        }

        public Criteria andActiveReturnTypeGreaterThan(String value) {
            addCriterion("ACTIVE_RETURN_TYPE >", value, "activeReturnType");
            return (Criteria) this;
        }

        public Criteria andActiveReturnTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVE_RETURN_TYPE >=", value, "activeReturnType");
            return (Criteria) this;
        }

        public Criteria andActiveReturnTypeLessThan(String value) {
            addCriterion("ACTIVE_RETURN_TYPE <", value, "activeReturnType");
            return (Criteria) this;
        }

        public Criteria andActiveReturnTypeLessThanOrEqualTo(String value) {
            addCriterion("ACTIVE_RETURN_TYPE <=", value, "activeReturnType");
            return (Criteria) this;
        }

        public Criteria andActiveReturnTypeLike(String value) {
            addCriterion("ACTIVE_RETURN_TYPE like", value, "activeReturnType");
            return (Criteria) this;
        }

        public Criteria andActiveReturnTypeNotLike(String value) {
            addCriterion("ACTIVE_RETURN_TYPE not like", value, "activeReturnType");
            return (Criteria) this;
        }

        public Criteria andActiveReturnTypeIn(List<String> values) {
            addCriterion("ACTIVE_RETURN_TYPE in", values, "activeReturnType");
            return (Criteria) this;
        }

        public Criteria andActiveReturnTypeNotIn(List<String> values) {
            addCriterion("ACTIVE_RETURN_TYPE not in", values, "activeReturnType");
            return (Criteria) this;
        }

        public Criteria andActiveReturnTypeBetween(String value1, String value2) {
            addCriterion("ACTIVE_RETURN_TYPE between", value1, value2, "activeReturnType");
            return (Criteria) this;
        }

        public Criteria andActiveReturnTypeNotBetween(String value1, String value2) {
            addCriterion("ACTIVE_RETURN_TYPE not between", value1, value2, "activeReturnType");
            return (Criteria) this;
        }

        public Criteria andStandReturnTypeIsNull() {
            addCriterion("STAND_RETURN_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andStandReturnTypeIsNotNull() {
            addCriterion("STAND_RETURN_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andStandReturnTypeEqualTo(String value) {
            addCriterion("STAND_RETURN_TYPE =", value, "standReturnType");
            return (Criteria) this;
        }

        public Criteria andStandReturnTypeNotEqualTo(String value) {
            addCriterion("STAND_RETURN_TYPE <>", value, "standReturnType");
            return (Criteria) this;
        }

        public Criteria andStandReturnTypeGreaterThan(String value) {
            addCriterion("STAND_RETURN_TYPE >", value, "standReturnType");
            return (Criteria) this;
        }

        public Criteria andStandReturnTypeGreaterThanOrEqualTo(String value) {
            addCriterion("STAND_RETURN_TYPE >=", value, "standReturnType");
            return (Criteria) this;
        }

        public Criteria andStandReturnTypeLessThan(String value) {
            addCriterion("STAND_RETURN_TYPE <", value, "standReturnType");
            return (Criteria) this;
        }

        public Criteria andStandReturnTypeLessThanOrEqualTo(String value) {
            addCriterion("STAND_RETURN_TYPE <=", value, "standReturnType");
            return (Criteria) this;
        }

        public Criteria andStandReturnTypeLike(String value) {
            addCriterion("STAND_RETURN_TYPE like", value, "standReturnType");
            return (Criteria) this;
        }

        public Criteria andStandReturnTypeNotLike(String value) {
            addCriterion("STAND_RETURN_TYPE not like", value, "standReturnType");
            return (Criteria) this;
        }

        public Criteria andStandReturnTypeIn(List<String> values) {
            addCriterion("STAND_RETURN_TYPE in", values, "standReturnType");
            return (Criteria) this;
        }

        public Criteria andStandReturnTypeNotIn(List<String> values) {
            addCriterion("STAND_RETURN_TYPE not in", values, "standReturnType");
            return (Criteria) this;
        }

        public Criteria andStandReturnTypeBetween(String value1, String value2) {
            addCriterion("STAND_RETURN_TYPE between", value1, value2, "standReturnType");
            return (Criteria) this;
        }

        public Criteria andStandReturnTypeNotBetween(String value1, String value2) {
            addCriterion("STAND_RETURN_TYPE not between", value1, value2, "standReturnType");
            return (Criteria) this;
        }

        public Criteria andActiveReturnDepositIsNull() {
            addCriterion("ACTIVE_RETURN_DEPOSIT is null");
            return (Criteria) this;
        }

        public Criteria andActiveReturnDepositIsNotNull() {
            addCriterion("ACTIVE_RETURN_DEPOSIT is not null");
            return (Criteria) this;
        }

        public Criteria andActiveReturnDepositEqualTo(BigDecimal value) {
            addCriterion("ACTIVE_RETURN_DEPOSIT =", value, "activeReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andActiveReturnDepositNotEqualTo(BigDecimal value) {
            addCriterion("ACTIVE_RETURN_DEPOSIT <>", value, "activeReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andActiveReturnDepositGreaterThan(BigDecimal value) {
            addCriterion("ACTIVE_RETURN_DEPOSIT >", value, "activeReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andActiveReturnDepositGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTIVE_RETURN_DEPOSIT >=", value, "activeReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andActiveReturnDepositLessThan(BigDecimal value) {
            addCriterion("ACTIVE_RETURN_DEPOSIT <", value, "activeReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andActiveReturnDepositLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTIVE_RETURN_DEPOSIT <=", value, "activeReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andActiveReturnDepositIn(List<BigDecimal> values) {
            addCriterion("ACTIVE_RETURN_DEPOSIT in", values, "activeReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andActiveReturnDepositNotIn(List<BigDecimal> values) {
            addCriterion("ACTIVE_RETURN_DEPOSIT not in", values, "activeReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andActiveReturnDepositBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTIVE_RETURN_DEPOSIT between", value1, value2, "activeReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andActiveReturnDepositNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTIVE_RETURN_DEPOSIT not between", value1, value2, "activeReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andActiveBackTypeIsNull() {
            addCriterion("ACTIVE_BACK_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andActiveBackTypeIsNotNull() {
            addCriterion("ACTIVE_BACK_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andActiveBackTypeEqualTo(String value) {
            addCriterion("ACTIVE_BACK_TYPE =", value, "activeBackType");
            return (Criteria) this;
        }

        public Criteria andActiveBackTypeNotEqualTo(String value) {
            addCriterion("ACTIVE_BACK_TYPE <>", value, "activeBackType");
            return (Criteria) this;
        }

        public Criteria andActiveBackTypeGreaterThan(String value) {
            addCriterion("ACTIVE_BACK_TYPE >", value, "activeBackType");
            return (Criteria) this;
        }

        public Criteria andActiveBackTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVE_BACK_TYPE >=", value, "activeBackType");
            return (Criteria) this;
        }

        public Criteria andActiveBackTypeLessThan(String value) {
            addCriterion("ACTIVE_BACK_TYPE <", value, "activeBackType");
            return (Criteria) this;
        }

        public Criteria andActiveBackTypeLessThanOrEqualTo(String value) {
            addCriterion("ACTIVE_BACK_TYPE <=", value, "activeBackType");
            return (Criteria) this;
        }

        public Criteria andActiveBackTypeLike(String value) {
            addCriterion("ACTIVE_BACK_TYPE like", value, "activeBackType");
            return (Criteria) this;
        }

        public Criteria andActiveBackTypeNotLike(String value) {
            addCriterion("ACTIVE_BACK_TYPE not like", value, "activeBackType");
            return (Criteria) this;
        }

        public Criteria andActiveBackTypeIn(List<String> values) {
            addCriterion("ACTIVE_BACK_TYPE in", values, "activeBackType");
            return (Criteria) this;
        }

        public Criteria andActiveBackTypeNotIn(List<String> values) {
            addCriterion("ACTIVE_BACK_TYPE not in", values, "activeBackType");
            return (Criteria) this;
        }

        public Criteria andActiveBackTypeBetween(String value1, String value2) {
            addCriterion("ACTIVE_BACK_TYPE between", value1, value2, "activeBackType");
            return (Criteria) this;
        }

        public Criteria andActiveBackTypeNotBetween(String value1, String value2) {
            addCriterion("ACTIVE_BACK_TYPE not between", value1, value2, "activeBackType");
            return (Criteria) this;
        }

        public Criteria andActiveReturnOrgIsNull() {
            addCriterion("ACTIVE_RETURN_ORG is null");
            return (Criteria) this;
        }

        public Criteria andActiveReturnOrgIsNotNull() {
            addCriterion("ACTIVE_RETURN_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andActiveReturnOrgEqualTo(String value) {
            addCriterion("ACTIVE_RETURN_ORG =", value, "activeReturnOrg");
            return (Criteria) this;
        }

        public Criteria andActiveReturnOrgNotEqualTo(String value) {
            addCriterion("ACTIVE_RETURN_ORG <>", value, "activeReturnOrg");
            return (Criteria) this;
        }

        public Criteria andActiveReturnOrgGreaterThan(String value) {
            addCriterion("ACTIVE_RETURN_ORG >", value, "activeReturnOrg");
            return (Criteria) this;
        }

        public Criteria andActiveReturnOrgGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVE_RETURN_ORG >=", value, "activeReturnOrg");
            return (Criteria) this;
        }

        public Criteria andActiveReturnOrgLessThan(String value) {
            addCriterion("ACTIVE_RETURN_ORG <", value, "activeReturnOrg");
            return (Criteria) this;
        }

        public Criteria andActiveReturnOrgLessThanOrEqualTo(String value) {
            addCriterion("ACTIVE_RETURN_ORG <=", value, "activeReturnOrg");
            return (Criteria) this;
        }

        public Criteria andActiveReturnOrgLike(String value) {
            addCriterion("ACTIVE_RETURN_ORG like", value, "activeReturnOrg");
            return (Criteria) this;
        }

        public Criteria andActiveReturnOrgNotLike(String value) {
            addCriterion("ACTIVE_RETURN_ORG not like", value, "activeReturnOrg");
            return (Criteria) this;
        }

        public Criteria andActiveReturnOrgIn(List<String> values) {
            addCriterion("ACTIVE_RETURN_ORG in", values, "activeReturnOrg");
            return (Criteria) this;
        }

        public Criteria andActiveReturnOrgNotIn(List<String> values) {
            addCriterion("ACTIVE_RETURN_ORG not in", values, "activeReturnOrg");
            return (Criteria) this;
        }

        public Criteria andActiveReturnOrgBetween(String value1, String value2) {
            addCriterion("ACTIVE_RETURN_ORG between", value1, value2, "activeReturnOrg");
            return (Criteria) this;
        }

        public Criteria andActiveReturnOrgNotBetween(String value1, String value2) {
            addCriterion("ACTIVE_RETURN_ORG not between", value1, value2, "activeReturnOrg");
            return (Criteria) this;
        }

        public Criteria andStandReturnDepositIsNull() {
            addCriterion("STAND_RETURN_DEPOSIT is null");
            return (Criteria) this;
        }

        public Criteria andStandReturnDepositIsNotNull() {
            addCriterion("STAND_RETURN_DEPOSIT is not null");
            return (Criteria) this;
        }

        public Criteria andStandReturnDepositEqualTo(BigDecimal value) {
            addCriterion("STAND_RETURN_DEPOSIT =", value, "standReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andStandReturnDepositNotEqualTo(BigDecimal value) {
            addCriterion("STAND_RETURN_DEPOSIT <>", value, "standReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andStandReturnDepositGreaterThan(BigDecimal value) {
            addCriterion("STAND_RETURN_DEPOSIT >", value, "standReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andStandReturnDepositGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STAND_RETURN_DEPOSIT >=", value, "standReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andStandReturnDepositLessThan(BigDecimal value) {
            addCriterion("STAND_RETURN_DEPOSIT <", value, "standReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andStandReturnDepositLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STAND_RETURN_DEPOSIT <=", value, "standReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andStandReturnDepositIn(List<BigDecimal> values) {
            addCriterion("STAND_RETURN_DEPOSIT in", values, "standReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andStandReturnDepositNotIn(List<BigDecimal> values) {
            addCriterion("STAND_RETURN_DEPOSIT not in", values, "standReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andStandReturnDepositBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STAND_RETURN_DEPOSIT between", value1, value2, "standReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andStandReturnDepositNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STAND_RETURN_DEPOSIT not between", value1, value2, "standReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andStandBackTypeIsNull() {
            addCriterion("STAND_BACK_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andStandBackTypeIsNotNull() {
            addCriterion("STAND_BACK_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andStandBackTypeEqualTo(String value) {
            addCriterion("STAND_BACK_TYPE =", value, "standBackType");
            return (Criteria) this;
        }

        public Criteria andStandBackTypeNotEqualTo(String value) {
            addCriterion("STAND_BACK_TYPE <>", value, "standBackType");
            return (Criteria) this;
        }

        public Criteria andStandBackTypeGreaterThan(String value) {
            addCriterion("STAND_BACK_TYPE >", value, "standBackType");
            return (Criteria) this;
        }

        public Criteria andStandBackTypeGreaterThanOrEqualTo(String value) {
            addCriterion("STAND_BACK_TYPE >=", value, "standBackType");
            return (Criteria) this;
        }

        public Criteria andStandBackTypeLessThan(String value) {
            addCriterion("STAND_BACK_TYPE <", value, "standBackType");
            return (Criteria) this;
        }

        public Criteria andStandBackTypeLessThanOrEqualTo(String value) {
            addCriterion("STAND_BACK_TYPE <=", value, "standBackType");
            return (Criteria) this;
        }

        public Criteria andStandBackTypeLike(String value) {
            addCriterion("STAND_BACK_TYPE like", value, "standBackType");
            return (Criteria) this;
        }

        public Criteria andStandBackTypeNotLike(String value) {
            addCriterion("STAND_BACK_TYPE not like", value, "standBackType");
            return (Criteria) this;
        }

        public Criteria andStandBackTypeIn(List<String> values) {
            addCriterion("STAND_BACK_TYPE in", values, "standBackType");
            return (Criteria) this;
        }

        public Criteria andStandBackTypeNotIn(List<String> values) {
            addCriterion("STAND_BACK_TYPE not in", values, "standBackType");
            return (Criteria) this;
        }

        public Criteria andStandBackTypeBetween(String value1, String value2) {
            addCriterion("STAND_BACK_TYPE between", value1, value2, "standBackType");
            return (Criteria) this;
        }

        public Criteria andStandBackTypeNotBetween(String value1, String value2) {
            addCriterion("STAND_BACK_TYPE not between", value1, value2, "standBackType");
            return (Criteria) this;
        }

        public Criteria andStandReturnOrgIsNull() {
            addCriterion("STAND_RETURN_ORG is null");
            return (Criteria) this;
        }

        public Criteria andStandReturnOrgIsNotNull() {
            addCriterion("STAND_RETURN_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andStandReturnOrgEqualTo(String value) {
            addCriterion("STAND_RETURN_ORG =", value, "standReturnOrg");
            return (Criteria) this;
        }

        public Criteria andStandReturnOrgNotEqualTo(String value) {
            addCriterion("STAND_RETURN_ORG <>", value, "standReturnOrg");
            return (Criteria) this;
        }

        public Criteria andStandReturnOrgGreaterThan(String value) {
            addCriterion("STAND_RETURN_ORG >", value, "standReturnOrg");
            return (Criteria) this;
        }

        public Criteria andStandReturnOrgGreaterThanOrEqualTo(String value) {
            addCriterion("STAND_RETURN_ORG >=", value, "standReturnOrg");
            return (Criteria) this;
        }

        public Criteria andStandReturnOrgLessThan(String value) {
            addCriterion("STAND_RETURN_ORG <", value, "standReturnOrg");
            return (Criteria) this;
        }

        public Criteria andStandReturnOrgLessThanOrEqualTo(String value) {
            addCriterion("STAND_RETURN_ORG <=", value, "standReturnOrg");
            return (Criteria) this;
        }

        public Criteria andStandReturnOrgLike(String value) {
            addCriterion("STAND_RETURN_ORG like", value, "standReturnOrg");
            return (Criteria) this;
        }

        public Criteria andStandReturnOrgNotLike(String value) {
            addCriterion("STAND_RETURN_ORG not like", value, "standReturnOrg");
            return (Criteria) this;
        }

        public Criteria andStandReturnOrgIn(List<String> values) {
            addCriterion("STAND_RETURN_ORG in", values, "standReturnOrg");
            return (Criteria) this;
        }

        public Criteria andStandReturnOrgNotIn(List<String> values) {
            addCriterion("STAND_RETURN_ORG not in", values, "standReturnOrg");
            return (Criteria) this;
        }

        public Criteria andStandReturnOrgBetween(String value1, String value2) {
            addCriterion("STAND_RETURN_ORG between", value1, value2, "standReturnOrg");
            return (Criteria) this;
        }

        public Criteria andStandReturnOrgNotBetween(String value1, String value2) {
            addCriterion("STAND_RETURN_ORG not between", value1, value2, "standReturnOrg");
            return (Criteria) this;
        }

        public Criteria andExtraBonusTypeIsNull() {
            addCriterion("EXTRA_BONUS_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andExtraBonusTypeIsNotNull() {
            addCriterion("EXTRA_BONUS_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andExtraBonusTypeEqualTo(String value) {
            addCriterion("EXTRA_BONUS_TYPE =", value, "extraBonusType");
            return (Criteria) this;
        }

        public Criteria andExtraBonusTypeNotEqualTo(String value) {
            addCriterion("EXTRA_BONUS_TYPE <>", value, "extraBonusType");
            return (Criteria) this;
        }

        public Criteria andExtraBonusTypeGreaterThan(String value) {
            addCriterion("EXTRA_BONUS_TYPE >", value, "extraBonusType");
            return (Criteria) this;
        }

        public Criteria andExtraBonusTypeGreaterThanOrEqualTo(String value) {
            addCriterion("EXTRA_BONUS_TYPE >=", value, "extraBonusType");
            return (Criteria) this;
        }

        public Criteria andExtraBonusTypeLessThan(String value) {
            addCriterion("EXTRA_BONUS_TYPE <", value, "extraBonusType");
            return (Criteria) this;
        }

        public Criteria andExtraBonusTypeLessThanOrEqualTo(String value) {
            addCriterion("EXTRA_BONUS_TYPE <=", value, "extraBonusType");
            return (Criteria) this;
        }

        public Criteria andExtraBonusTypeLike(String value) {
            addCriterion("EXTRA_BONUS_TYPE like", value, "extraBonusType");
            return (Criteria) this;
        }

        public Criteria andExtraBonusTypeNotLike(String value) {
            addCriterion("EXTRA_BONUS_TYPE not like", value, "extraBonusType");
            return (Criteria) this;
        }

        public Criteria andExtraBonusTypeIn(List<String> values) {
            addCriterion("EXTRA_BONUS_TYPE in", values, "extraBonusType");
            return (Criteria) this;
        }

        public Criteria andExtraBonusTypeNotIn(List<String> values) {
            addCriterion("EXTRA_BONUS_TYPE not in", values, "extraBonusType");
            return (Criteria) this;
        }

        public Criteria andExtraBonusTypeBetween(String value1, String value2) {
            addCriterion("EXTRA_BONUS_TYPE between", value1, value2, "extraBonusType");
            return (Criteria) this;
        }

        public Criteria andExtraBonusTypeNotBetween(String value1, String value2) {
            addCriterion("EXTRA_BONUS_TYPE not between", value1, value2, "extraBonusType");
            return (Criteria) this;
        }

        public Criteria andBonusNumIsNull() {
            addCriterion("BONUS_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBonusNumIsNotNull() {
            addCriterion("BONUS_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBonusNumEqualTo(BigDecimal value) {
            addCriterion("BONUS_NUM =", value, "bonusNum");
            return (Criteria) this;
        }

        public Criteria andBonusNumNotEqualTo(BigDecimal value) {
            addCriterion("BONUS_NUM <>", value, "bonusNum");
            return (Criteria) this;
        }

        public Criteria andBonusNumGreaterThan(BigDecimal value) {
            addCriterion("BONUS_NUM >", value, "bonusNum");
            return (Criteria) this;
        }

        public Criteria andBonusNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BONUS_NUM >=", value, "bonusNum");
            return (Criteria) this;
        }

        public Criteria andBonusNumLessThan(BigDecimal value) {
            addCriterion("BONUS_NUM <", value, "bonusNum");
            return (Criteria) this;
        }

        public Criteria andBonusNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BONUS_NUM <=", value, "bonusNum");
            return (Criteria) this;
        }

        public Criteria andBonusNumIn(List<BigDecimal> values) {
            addCriterion("BONUS_NUM in", values, "bonusNum");
            return (Criteria) this;
        }

        public Criteria andBonusNumNotIn(List<BigDecimal> values) {
            addCriterion("BONUS_NUM not in", values, "bonusNum");
            return (Criteria) this;
        }

        public Criteria andBonusNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BONUS_NUM between", value1, value2, "bonusNum");
            return (Criteria) this;
        }

        public Criteria andBonusNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BONUS_NUM not between", value1, value2, "bonusNum");
            return (Criteria) this;
        }

        public Criteria andDaysPerPeriodIsNull() {
            addCriterion("DAYS_PER_PERIOD is null");
            return (Criteria) this;
        }

        public Criteria andDaysPerPeriodIsNotNull() {
            addCriterion("DAYS_PER_PERIOD is not null");
            return (Criteria) this;
        }

        public Criteria andDaysPerPeriodEqualTo(BigDecimal value) {
            addCriterion("DAYS_PER_PERIOD =", value, "daysPerPeriod");
            return (Criteria) this;
        }

        public Criteria andDaysPerPeriodNotEqualTo(BigDecimal value) {
            addCriterion("DAYS_PER_PERIOD <>", value, "daysPerPeriod");
            return (Criteria) this;
        }

        public Criteria andDaysPerPeriodGreaterThan(BigDecimal value) {
            addCriterion("DAYS_PER_PERIOD >", value, "daysPerPeriod");
            return (Criteria) this;
        }

        public Criteria andDaysPerPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DAYS_PER_PERIOD >=", value, "daysPerPeriod");
            return (Criteria) this;
        }

        public Criteria andDaysPerPeriodLessThan(BigDecimal value) {
            addCriterion("DAYS_PER_PERIOD <", value, "daysPerPeriod");
            return (Criteria) this;
        }

        public Criteria andDaysPerPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DAYS_PER_PERIOD <=", value, "daysPerPeriod");
            return (Criteria) this;
        }

        public Criteria andDaysPerPeriodIn(List<BigDecimal> values) {
            addCriterion("DAYS_PER_PERIOD in", values, "daysPerPeriod");
            return (Criteria) this;
        }

        public Criteria andDaysPerPeriodNotIn(List<BigDecimal> values) {
            addCriterion("DAYS_PER_PERIOD not in", values, "daysPerPeriod");
            return (Criteria) this;
        }

        public Criteria andDaysPerPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAYS_PER_PERIOD between", value1, value2, "daysPerPeriod");
            return (Criteria) this;
        }

        public Criteria andDaysPerPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAYS_PER_PERIOD not between", value1, value2, "daysPerPeriod");
            return (Criteria) this;
        }

        public Criteria andTransactionLimitIsNull() {
            addCriterion("TRANSACTION_LIMIT is null");
            return (Criteria) this;
        }

        public Criteria andTransactionLimitIsNotNull() {
            addCriterion("TRANSACTION_LIMIT is not null");
            return (Criteria) this;
        }

        public Criteria andTransactionLimitEqualTo(BigDecimal value) {
            addCriterion("TRANSACTION_LIMIT =", value, "transactionLimit");
            return (Criteria) this;
        }

        public Criteria andTransactionLimitNotEqualTo(BigDecimal value) {
            addCriterion("TRANSACTION_LIMIT <>", value, "transactionLimit");
            return (Criteria) this;
        }

        public Criteria andTransactionLimitGreaterThan(BigDecimal value) {
            addCriterion("TRANSACTION_LIMIT >", value, "transactionLimit");
            return (Criteria) this;
        }

        public Criteria andTransactionLimitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANSACTION_LIMIT >=", value, "transactionLimit");
            return (Criteria) this;
        }

        public Criteria andTransactionLimitLessThan(BigDecimal value) {
            addCriterion("TRANSACTION_LIMIT <", value, "transactionLimit");
            return (Criteria) this;
        }

        public Criteria andTransactionLimitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANSACTION_LIMIT <=", value, "transactionLimit");
            return (Criteria) this;
        }

        public Criteria andTransactionLimitIn(List<BigDecimal> values) {
            addCriterion("TRANSACTION_LIMIT in", values, "transactionLimit");
            return (Criteria) this;
        }

        public Criteria andTransactionLimitNotIn(List<BigDecimal> values) {
            addCriterion("TRANSACTION_LIMIT not in", values, "transactionLimit");
            return (Criteria) this;
        }

        public Criteria andTransactionLimitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANSACTION_LIMIT between", value1, value2, "transactionLimit");
            return (Criteria) this;
        }

        public Criteria andTransactionLimitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANSACTION_LIMIT not between", value1, value2, "transactionLimit");
            return (Criteria) this;
        }

        public Criteria andBonusReturnDepositIsNull() {
            addCriterion("BONUS_RETURN_DEPOSIT is null");
            return (Criteria) this;
        }

        public Criteria andBonusReturnDepositIsNotNull() {
            addCriterion("BONUS_RETURN_DEPOSIT is not null");
            return (Criteria) this;
        }

        public Criteria andBonusReturnDepositEqualTo(BigDecimal value) {
            addCriterion("BONUS_RETURN_DEPOSIT =", value, "bonusReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andBonusReturnDepositNotEqualTo(BigDecimal value) {
            addCriterion("BONUS_RETURN_DEPOSIT <>", value, "bonusReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andBonusReturnDepositGreaterThan(BigDecimal value) {
            addCriterion("BONUS_RETURN_DEPOSIT >", value, "bonusReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andBonusReturnDepositGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BONUS_RETURN_DEPOSIT >=", value, "bonusReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andBonusReturnDepositLessThan(BigDecimal value) {
            addCriterion("BONUS_RETURN_DEPOSIT <", value, "bonusReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andBonusReturnDepositLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BONUS_RETURN_DEPOSIT <=", value, "bonusReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andBonusReturnDepositIn(List<BigDecimal> values) {
            addCriterion("BONUS_RETURN_DEPOSIT in", values, "bonusReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andBonusReturnDepositNotIn(List<BigDecimal> values) {
            addCriterion("BONUS_RETURN_DEPOSIT not in", values, "bonusReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andBonusReturnDepositBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BONUS_RETURN_DEPOSIT between", value1, value2, "bonusReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andBonusReturnDepositNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BONUS_RETURN_DEPOSIT not between", value1, value2, "bonusReturnDeposit");
            return (Criteria) this;
        }

        public Criteria andBonusBackTypeIsNull() {
            addCriterion("BONUS_BACK_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBonusBackTypeIsNotNull() {
            addCriterion("BONUS_BACK_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBonusBackTypeEqualTo(String value) {
            addCriterion("BONUS_BACK_TYPE =", value, "bonusBackType");
            return (Criteria) this;
        }

        public Criteria andBonusBackTypeNotEqualTo(String value) {
            addCriterion("BONUS_BACK_TYPE <>", value, "bonusBackType");
            return (Criteria) this;
        }

        public Criteria andBonusBackTypeGreaterThan(String value) {
            addCriterion("BONUS_BACK_TYPE >", value, "bonusBackType");
            return (Criteria) this;
        }

        public Criteria andBonusBackTypeGreaterThanOrEqualTo(String value) {
            addCriterion("BONUS_BACK_TYPE >=", value, "bonusBackType");
            return (Criteria) this;
        }

        public Criteria andBonusBackTypeLessThan(String value) {
            addCriterion("BONUS_BACK_TYPE <", value, "bonusBackType");
            return (Criteria) this;
        }

        public Criteria andBonusBackTypeLessThanOrEqualTo(String value) {
            addCriterion("BONUS_BACK_TYPE <=", value, "bonusBackType");
            return (Criteria) this;
        }

        public Criteria andBonusBackTypeLike(String value) {
            addCriterion("BONUS_BACK_TYPE like", value, "bonusBackType");
            return (Criteria) this;
        }

        public Criteria andBonusBackTypeNotLike(String value) {
            addCriterion("BONUS_BACK_TYPE not like", value, "bonusBackType");
            return (Criteria) this;
        }

        public Criteria andBonusBackTypeIn(List<String> values) {
            addCriterion("BONUS_BACK_TYPE in", values, "bonusBackType");
            return (Criteria) this;
        }

        public Criteria andBonusBackTypeNotIn(List<String> values) {
            addCriterion("BONUS_BACK_TYPE not in", values, "bonusBackType");
            return (Criteria) this;
        }

        public Criteria andBonusBackTypeBetween(String value1, String value2) {
            addCriterion("BONUS_BACK_TYPE between", value1, value2, "bonusBackType");
            return (Criteria) this;
        }

        public Criteria andBonusBackTypeNotBetween(String value1, String value2) {
            addCriterion("BONUS_BACK_TYPE not between", value1, value2, "bonusBackType");
            return (Criteria) this;
        }

        public Criteria andBonusReturnOrgIsNull() {
            addCriterion("BONUS_RETURN_ORG is null");
            return (Criteria) this;
        }

        public Criteria andBonusReturnOrgIsNotNull() {
            addCriterion("BONUS_RETURN_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andBonusReturnOrgEqualTo(String value) {
            addCriterion("BONUS_RETURN_ORG =", value, "bonusReturnOrg");
            return (Criteria) this;
        }

        public Criteria andBonusReturnOrgNotEqualTo(String value) {
            addCriterion("BONUS_RETURN_ORG <>", value, "bonusReturnOrg");
            return (Criteria) this;
        }

        public Criteria andBonusReturnOrgGreaterThan(String value) {
            addCriterion("BONUS_RETURN_ORG >", value, "bonusReturnOrg");
            return (Criteria) this;
        }

        public Criteria andBonusReturnOrgGreaterThanOrEqualTo(String value) {
            addCriterion("BONUS_RETURN_ORG >=", value, "bonusReturnOrg");
            return (Criteria) this;
        }

        public Criteria andBonusReturnOrgLessThan(String value) {
            addCriterion("BONUS_RETURN_ORG <", value, "bonusReturnOrg");
            return (Criteria) this;
        }

        public Criteria andBonusReturnOrgLessThanOrEqualTo(String value) {
            addCriterion("BONUS_RETURN_ORG <=", value, "bonusReturnOrg");
            return (Criteria) this;
        }

        public Criteria andBonusReturnOrgLike(String value) {
            addCriterion("BONUS_RETURN_ORG like", value, "bonusReturnOrg");
            return (Criteria) this;
        }

        public Criteria andBonusReturnOrgNotLike(String value) {
            addCriterion("BONUS_RETURN_ORG not like", value, "bonusReturnOrg");
            return (Criteria) this;
        }

        public Criteria andBonusReturnOrgIn(List<String> values) {
            addCriterion("BONUS_RETURN_ORG in", values, "bonusReturnOrg");
            return (Criteria) this;
        }

        public Criteria andBonusReturnOrgNotIn(List<String> values) {
            addCriterion("BONUS_RETURN_ORG not in", values, "bonusReturnOrg");
            return (Criteria) this;
        }

        public Criteria andBonusReturnOrgBetween(String value1, String value2) {
            addCriterion("BONUS_RETURN_ORG between", value1, value2, "bonusReturnOrg");
            return (Criteria) this;
        }

        public Criteria andBonusReturnOrgNotBetween(String value1, String value2) {
            addCriterion("BONUS_RETURN_ORG not between", value1, value2, "bonusReturnOrg");
            return (Criteria) this;
        }

        public Criteria andActivityEndTypeIsNull() {
            addCriterion("ACTIVITY_END_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andActivityEndTypeIsNotNull() {
            addCriterion("ACTIVITY_END_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andActivityEndTypeEqualTo(String value) {
            addCriterion("ACTIVITY_END_TYPE =", value, "activityEndType");
            return (Criteria) this;
        }

        public Criteria andActivityEndTypeNotEqualTo(String value) {
            addCriterion("ACTIVITY_END_TYPE <>", value, "activityEndType");
            return (Criteria) this;
        }

        public Criteria andActivityEndTypeGreaterThan(String value) {
            addCriterion("ACTIVITY_END_TYPE >", value, "activityEndType");
            return (Criteria) this;
        }

        public Criteria andActivityEndTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_END_TYPE >=", value, "activityEndType");
            return (Criteria) this;
        }

        public Criteria andActivityEndTypeLessThan(String value) {
            addCriterion("ACTIVITY_END_TYPE <", value, "activityEndType");
            return (Criteria) this;
        }

        public Criteria andActivityEndTypeLessThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_END_TYPE <=", value, "activityEndType");
            return (Criteria) this;
        }

        public Criteria andActivityEndTypeLike(String value) {
            addCriterion("ACTIVITY_END_TYPE like", value, "activityEndType");
            return (Criteria) this;
        }

        public Criteria andActivityEndTypeNotLike(String value) {
            addCriterion("ACTIVITY_END_TYPE not like", value, "activityEndType");
            return (Criteria) this;
        }

        public Criteria andActivityEndTypeIn(List<String> values) {
            addCriterion("ACTIVITY_END_TYPE in", values, "activityEndType");
            return (Criteria) this;
        }

        public Criteria andActivityEndTypeNotIn(List<String> values) {
            addCriterion("ACTIVITY_END_TYPE not in", values, "activityEndType");
            return (Criteria) this;
        }

        public Criteria andActivityEndTypeBetween(String value1, String value2) {
            addCriterion("ACTIVITY_END_TYPE between", value1, value2, "activityEndType");
            return (Criteria) this;
        }

        public Criteria andActivityEndTypeNotBetween(String value1, String value2) {
            addCriterion("ACTIVITY_END_TYPE not between", value1, value2, "activityEndType");
            return (Criteria) this;
        }

        public Criteria andActivityEndLimitIsNull() {
            addCriterion("ACTIVITY_END_LIMIT is null");
            return (Criteria) this;
        }

        public Criteria andActivityEndLimitIsNotNull() {
            addCriterion("ACTIVITY_END_LIMIT is not null");
            return (Criteria) this;
        }

        public Criteria andActivityEndLimitEqualTo(BigDecimal value) {
            addCriterion("ACTIVITY_END_LIMIT =", value, "activityEndLimit");
            return (Criteria) this;
        }

        public Criteria andActivityEndLimitNotEqualTo(BigDecimal value) {
            addCriterion("ACTIVITY_END_LIMIT <>", value, "activityEndLimit");
            return (Criteria) this;
        }

        public Criteria andActivityEndLimitGreaterThan(BigDecimal value) {
            addCriterion("ACTIVITY_END_LIMIT >", value, "activityEndLimit");
            return (Criteria) this;
        }

        public Criteria andActivityEndLimitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTIVITY_END_LIMIT >=", value, "activityEndLimit");
            return (Criteria) this;
        }

        public Criteria andActivityEndLimitLessThan(BigDecimal value) {
            addCriterion("ACTIVITY_END_LIMIT <", value, "activityEndLimit");
            return (Criteria) this;
        }

        public Criteria andActivityEndLimitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTIVITY_END_LIMIT <=", value, "activityEndLimit");
            return (Criteria) this;
        }

        public Criteria andActivityEndLimitIn(List<BigDecimal> values) {
            addCriterion("ACTIVITY_END_LIMIT in", values, "activityEndLimit");
            return (Criteria) this;
        }

        public Criteria andActivityEndLimitNotIn(List<BigDecimal> values) {
            addCriterion("ACTIVITY_END_LIMIT not in", values, "activityEndLimit");
            return (Criteria) this;
        }

        public Criteria andActivityEndLimitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTIVITY_END_LIMIT between", value1, value2, "activityEndLimit");
            return (Criteria) this;
        }

        public Criteria andActivityEndLimitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTIVITY_END_LIMIT not between", value1, value2, "activityEndLimit");
            return (Criteria) this;
        }

        public Criteria andBackWayIsNull() {
            addCriterion("BACK_WAY is null");
            return (Criteria) this;
        }

        public Criteria andBackWayIsNotNull() {
            addCriterion("BACK_WAY is not null");
            return (Criteria) this;
        }

        public Criteria andBackWayEqualTo(String value) {
            addCriterion("BACK_WAY =", value, "backWay");
            return (Criteria) this;
        }

        public Criteria andBackWayNotEqualTo(String value) {
            addCriterion("BACK_WAY <>", value, "backWay");
            return (Criteria) this;
        }

        public Criteria andBackWayGreaterThan(String value) {
            addCriterion("BACK_WAY >", value, "backWay");
            return (Criteria) this;
        }

        public Criteria andBackWayGreaterThanOrEqualTo(String value) {
            addCriterion("BACK_WAY >=", value, "backWay");
            return (Criteria) this;
        }

        public Criteria andBackWayLessThan(String value) {
            addCriterion("BACK_WAY <", value, "backWay");
            return (Criteria) this;
        }

        public Criteria andBackWayLessThanOrEqualTo(String value) {
            addCriterion("BACK_WAY <=", value, "backWay");
            return (Criteria) this;
        }

        public Criteria andBackWayLike(String value) {
            addCriterion("BACK_WAY like", value, "backWay");
            return (Criteria) this;
        }

        public Criteria andBackWayNotLike(String value) {
            addCriterion("BACK_WAY not like", value, "backWay");
            return (Criteria) this;
        }

        public Criteria andBackWayIn(List<String> values) {
            addCriterion("BACK_WAY in", values, "backWay");
            return (Criteria) this;
        }

        public Criteria andBackWayNotIn(List<String> values) {
            addCriterion("BACK_WAY not in", values, "backWay");
            return (Criteria) this;
        }

        public Criteria andBackWayBetween(String value1, String value2) {
            addCriterion("BACK_WAY between", value1, value2, "backWay");
            return (Criteria) this;
        }

        public Criteria andBackWayNotBetween(String value1, String value2) {
            addCriterion("BACK_WAY not between", value1, value2, "backWay");
            return (Criteria) this;
        }

        public Criteria andBackTypeIsNull() {
            addCriterion("BACK_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBackTypeIsNotNull() {
            addCriterion("BACK_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBackTypeEqualTo(String value) {
            addCriterion("BACK_TYPE =", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeNotEqualTo(String value) {
            addCriterion("BACK_TYPE <>", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeGreaterThan(String value) {
            addCriterion("BACK_TYPE >", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeGreaterThanOrEqualTo(String value) {
            addCriterion("BACK_TYPE >=", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeLessThan(String value) {
            addCriterion("BACK_TYPE <", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeLessThanOrEqualTo(String value) {
            addCriterion("BACK_TYPE <=", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeLike(String value) {
            addCriterion("BACK_TYPE like", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeNotLike(String value) {
            addCriterion("BACK_TYPE not like", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeIn(List<String> values) {
            addCriterion("BACK_TYPE in", values, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeNotIn(List<String> values) {
            addCriterion("BACK_TYPE not in", values, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeBetween(String value1, String value2) {
            addCriterion("BACK_TYPE between", value1, value2, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeNotBetween(String value1, String value2) {
            addCriterion("BACK_TYPE not between", value1, value2, "backType");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeOneIsNull() {
            addCriterion("NO_STAND_FINE_TYPE_ONE is null");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeOneIsNotNull() {
            addCriterion("NO_STAND_FINE_TYPE_ONE is not null");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeOneEqualTo(String value) {
            addCriterion("NO_STAND_FINE_TYPE_ONE =", value, "noStandFineTypeOne");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeOneNotEqualTo(String value) {
            addCriterion("NO_STAND_FINE_TYPE_ONE <>", value, "noStandFineTypeOne");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeOneGreaterThan(String value) {
            addCriterion("NO_STAND_FINE_TYPE_ONE >", value, "noStandFineTypeOne");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeOneGreaterThanOrEqualTo(String value) {
            addCriterion("NO_STAND_FINE_TYPE_ONE >=", value, "noStandFineTypeOne");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeOneLessThan(String value) {
            addCriterion("NO_STAND_FINE_TYPE_ONE <", value, "noStandFineTypeOne");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeOneLessThanOrEqualTo(String value) {
            addCriterion("NO_STAND_FINE_TYPE_ONE <=", value, "noStandFineTypeOne");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeOneLike(String value) {
            addCriterion("NO_STAND_FINE_TYPE_ONE like", value, "noStandFineTypeOne");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeOneNotLike(String value) {
            addCriterion("NO_STAND_FINE_TYPE_ONE not like", value, "noStandFineTypeOne");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeOneIn(List<String> values) {
            addCriterion("NO_STAND_FINE_TYPE_ONE in", values, "noStandFineTypeOne");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeOneNotIn(List<String> values) {
            addCriterion("NO_STAND_FINE_TYPE_ONE not in", values, "noStandFineTypeOne");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeOneBetween(String value1, String value2) {
            addCriterion("NO_STAND_FINE_TYPE_ONE between", value1, value2, "noStandFineTypeOne");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeOneNotBetween(String value1, String value2) {
            addCriterion("NO_STAND_FINE_TYPE_ONE not between", value1, value2, "noStandFineTypeOne");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeTwoIsNull() {
            addCriterion("NO_STAND_FINE_TYPE_TWO is null");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeTwoIsNotNull() {
            addCriterion("NO_STAND_FINE_TYPE_TWO is not null");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeTwoEqualTo(String value) {
            addCriterion("NO_STAND_FINE_TYPE_TWO =", value, "noStandFineTypeTwo");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeTwoNotEqualTo(String value) {
            addCriterion("NO_STAND_FINE_TYPE_TWO <>", value, "noStandFineTypeTwo");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeTwoGreaterThan(String value) {
            addCriterion("NO_STAND_FINE_TYPE_TWO >", value, "noStandFineTypeTwo");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeTwoGreaterThanOrEqualTo(String value) {
            addCriterion("NO_STAND_FINE_TYPE_TWO >=", value, "noStandFineTypeTwo");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeTwoLessThan(String value) {
            addCriterion("NO_STAND_FINE_TYPE_TWO <", value, "noStandFineTypeTwo");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeTwoLessThanOrEqualTo(String value) {
            addCriterion("NO_STAND_FINE_TYPE_TWO <=", value, "noStandFineTypeTwo");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeTwoLike(String value) {
            addCriterion("NO_STAND_FINE_TYPE_TWO like", value, "noStandFineTypeTwo");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeTwoNotLike(String value) {
            addCriterion("NO_STAND_FINE_TYPE_TWO not like", value, "noStandFineTypeTwo");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeTwoIn(List<String> values) {
            addCriterion("NO_STAND_FINE_TYPE_TWO in", values, "noStandFineTypeTwo");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeTwoNotIn(List<String> values) {
            addCriterion("NO_STAND_FINE_TYPE_TWO not in", values, "noStandFineTypeTwo");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeTwoBetween(String value1, String value2) {
            addCriterion("NO_STAND_FINE_TYPE_TWO between", value1, value2, "noStandFineTypeTwo");
            return (Criteria) this;
        }

        public Criteria andNoStandFineTypeTwoNotBetween(String value1, String value2) {
            addCriterion("NO_STAND_FINE_TYPE_TWO not between", value1, value2, "noStandFineTypeTwo");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckTypeIsNull() {
            addCriterion("NO_STAND_CHECK_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckTypeIsNotNull() {
            addCriterion("NO_STAND_CHECK_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckTypeEqualTo(String value) {
            addCriterion("NO_STAND_CHECK_TYPE =", value, "noStandCheckType");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckTypeNotEqualTo(String value) {
            addCriterion("NO_STAND_CHECK_TYPE <>", value, "noStandCheckType");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckTypeGreaterThan(String value) {
            addCriterion("NO_STAND_CHECK_TYPE >", value, "noStandCheckType");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckTypeGreaterThanOrEqualTo(String value) {
            addCriterion("NO_STAND_CHECK_TYPE >=", value, "noStandCheckType");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckTypeLessThan(String value) {
            addCriterion("NO_STAND_CHECK_TYPE <", value, "noStandCheckType");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckTypeLessThanOrEqualTo(String value) {
            addCriterion("NO_STAND_CHECK_TYPE <=", value, "noStandCheckType");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckTypeLike(String value) {
            addCriterion("NO_STAND_CHECK_TYPE like", value, "noStandCheckType");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckTypeNotLike(String value) {
            addCriterion("NO_STAND_CHECK_TYPE not like", value, "noStandCheckType");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckTypeIn(List<String> values) {
            addCriterion("NO_STAND_CHECK_TYPE in", values, "noStandCheckType");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckTypeNotIn(List<String> values) {
            addCriterion("NO_STAND_CHECK_TYPE not in", values, "noStandCheckType");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckTypeBetween(String value1, String value2) {
            addCriterion("NO_STAND_CHECK_TYPE between", value1, value2, "noStandCheckType");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckTypeNotBetween(String value1, String value2) {
            addCriterion("NO_STAND_CHECK_TYPE not between", value1, value2, "noStandCheckType");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckDaysIsNull() {
            addCriterion("NO_STAND_CHECK_DAYS is null");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckDaysIsNotNull() {
            addCriterion("NO_STAND_CHECK_DAYS is not null");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckDaysEqualTo(BigDecimal value) {
            addCriterion("NO_STAND_CHECK_DAYS =", value, "noStandCheckDays");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckDaysNotEqualTo(BigDecimal value) {
            addCriterion("NO_STAND_CHECK_DAYS <>", value, "noStandCheckDays");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckDaysGreaterThan(BigDecimal value) {
            addCriterion("NO_STAND_CHECK_DAYS >", value, "noStandCheckDays");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckDaysGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NO_STAND_CHECK_DAYS >=", value, "noStandCheckDays");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckDaysLessThan(BigDecimal value) {
            addCriterion("NO_STAND_CHECK_DAYS <", value, "noStandCheckDays");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckDaysLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NO_STAND_CHECK_DAYS <=", value, "noStandCheckDays");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckDaysIn(List<BigDecimal> values) {
            addCriterion("NO_STAND_CHECK_DAYS in", values, "noStandCheckDays");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckDaysNotIn(List<BigDecimal> values) {
            addCriterion("NO_STAND_CHECK_DAYS not in", values, "noStandCheckDays");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckDaysBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NO_STAND_CHECK_DAYS between", value1, value2, "noStandCheckDays");
            return (Criteria) this;
        }

        public Criteria andNoStandCheckDaysNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NO_STAND_CHECK_DAYS not between", value1, value2, "noStandCheckDays");
            return (Criteria) this;
        }

        public Criteria andNoStandFineDepositIsNull() {
            addCriterion("NO_STAND_FINE_DEPOSIT is null");
            return (Criteria) this;
        }

        public Criteria andNoStandFineDepositIsNotNull() {
            addCriterion("NO_STAND_FINE_DEPOSIT is not null");
            return (Criteria) this;
        }

        public Criteria andNoStandFineDepositEqualTo(BigDecimal value) {
            addCriterion("NO_STAND_FINE_DEPOSIT =", value, "noStandFineDeposit");
            return (Criteria) this;
        }

        public Criteria andNoStandFineDepositNotEqualTo(BigDecimal value) {
            addCriterion("NO_STAND_FINE_DEPOSIT <>", value, "noStandFineDeposit");
            return (Criteria) this;
        }

        public Criteria andNoStandFineDepositGreaterThan(BigDecimal value) {
            addCriterion("NO_STAND_FINE_DEPOSIT >", value, "noStandFineDeposit");
            return (Criteria) this;
        }

        public Criteria andNoStandFineDepositGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NO_STAND_FINE_DEPOSIT >=", value, "noStandFineDeposit");
            return (Criteria) this;
        }

        public Criteria andNoStandFineDepositLessThan(BigDecimal value) {
            addCriterion("NO_STAND_FINE_DEPOSIT <", value, "noStandFineDeposit");
            return (Criteria) this;
        }

        public Criteria andNoStandFineDepositLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NO_STAND_FINE_DEPOSIT <=", value, "noStandFineDeposit");
            return (Criteria) this;
        }

        public Criteria andNoStandFineDepositIn(List<BigDecimal> values) {
            addCriterion("NO_STAND_FINE_DEPOSIT in", values, "noStandFineDeposit");
            return (Criteria) this;
        }

        public Criteria andNoStandFineDepositNotIn(List<BigDecimal> values) {
            addCriterion("NO_STAND_FINE_DEPOSIT not in", values, "noStandFineDeposit");
            return (Criteria) this;
        }

        public Criteria andNoStandFineDepositBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NO_STAND_FINE_DEPOSIT between", value1, value2, "noStandFineDeposit");
            return (Criteria) this;
        }

        public Criteria andNoStandFineDepositNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NO_STAND_FINE_DEPOSIT not between", value1, value2, "noStandFineDeposit");
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