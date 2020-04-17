package com.ryx.jobOrder.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JoOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public JoOrderExample() {
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

        public Criteria andAgIdIsNull() {
            addCriterion("AG_ID is null");
            return (Criteria) this;
        }

        public Criteria andAgIdIsNotNull() {
            addCriterion("AG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAgIdEqualTo(String value) {
            addCriterion("AG_ID =", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdNotEqualTo(String value) {
            addCriterion("AG_ID <>", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdGreaterThan(String value) {
            addCriterion("AG_ID >", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdGreaterThanOrEqualTo(String value) {
            addCriterion("AG_ID >=", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdLessThan(String value) {
            addCriterion("AG_ID <", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdLessThanOrEqualTo(String value) {
            addCriterion("AG_ID <=", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdLike(String value) {
            addCriterion("AG_ID like", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdNotLike(String value) {
            addCriterion("AG_ID not like", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdIn(List<String> values) {
            addCriterion("AG_ID in", values, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdNotIn(List<String> values) {
            addCriterion("AG_ID not in", values, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdBetween(String value1, String value2) {
            addCriterion("AG_ID between", value1, value2, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdNotBetween(String value1, String value2) {
            addCriterion("AG_ID not between", value1, value2, "agId");
            return (Criteria) this;
        }

        public Criteria andAgNameIsNull() {
            addCriterion("AG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAgNameIsNotNull() {
            addCriterion("AG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAgNameEqualTo(String value) {
            addCriterion("AG_NAME =", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameNotEqualTo(String value) {
            addCriterion("AG_NAME <>", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameGreaterThan(String value) {
            addCriterion("AG_NAME >", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameGreaterThanOrEqualTo(String value) {
            addCriterion("AG_NAME >=", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameLessThan(String value) {
            addCriterion("AG_NAME <", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameLessThanOrEqualTo(String value) {
            addCriterion("AG_NAME <=", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameLike(String value) {
            addCriterion("AG_NAME like", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameNotLike(String value) {
            addCriterion("AG_NAME not like", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameIn(List<String> values) {
            addCriterion("AG_NAME in", values, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameNotIn(List<String> values) {
            addCriterion("AG_NAME not in", values, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameBetween(String value1, String value2) {
            addCriterion("AG_NAME between", value1, value2, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameNotBetween(String value1, String value2) {
            addCriterion("AG_NAME not between", value1, value2, "agName");
            return (Criteria) this;
        }

        public Criteria andBusIdIsNull() {
            addCriterion("BUS_ID is null");
            return (Criteria) this;
        }

        public Criteria andBusIdIsNotNull() {
            addCriterion("BUS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBusIdEqualTo(String value) {
            addCriterion("BUS_ID =", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotEqualTo(String value) {
            addCriterion("BUS_ID <>", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdGreaterThan(String value) {
            addCriterion("BUS_ID >", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_ID >=", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdLessThan(String value) {
            addCriterion("BUS_ID <", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdLessThanOrEqualTo(String value) {
            addCriterion("BUS_ID <=", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdLike(String value) {
            addCriterion("BUS_ID like", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotLike(String value) {
            addCriterion("BUS_ID not like", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdIn(List<String> values) {
            addCriterion("BUS_ID in", values, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotIn(List<String> values) {
            addCriterion("BUS_ID not in", values, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdBetween(String value1, String value2) {
            addCriterion("BUS_ID between", value1, value2, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotBetween(String value1, String value2) {
            addCriterion("BUS_ID not between", value1, value2, "busId");
            return (Criteria) this;
        }

        public Criteria andBusNumIsNull() {
            addCriterion("BUS_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBusNumIsNotNull() {
            addCriterion("BUS_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBusNumEqualTo(String value) {
            addCriterion("BUS_NUM =", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotEqualTo(String value) {
            addCriterion("BUS_NUM <>", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumGreaterThan(String value) {
            addCriterion("BUS_NUM >", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_NUM >=", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumLessThan(String value) {
            addCriterion("BUS_NUM <", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumLessThanOrEqualTo(String value) {
            addCriterion("BUS_NUM <=", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumLike(String value) {
            addCriterion("BUS_NUM like", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotLike(String value) {
            addCriterion("BUS_NUM not like", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumIn(List<String> values) {
            addCriterion("BUS_NUM in", values, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotIn(List<String> values) {
            addCriterion("BUS_NUM not in", values, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumBetween(String value1, String value2) {
            addCriterion("BUS_NUM between", value1, value2, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotBetween(String value1, String value2) {
            addCriterion("BUS_NUM not between", value1, value2, "busNum");
            return (Criteria) this;
        }

        public Criteria andPaltformNumIsNull() {
            addCriterion("PALTFORM_NUM is null");
            return (Criteria) this;
        }

        public Criteria andPaltformNumIsNotNull() {
            addCriterion("PALTFORM_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andPaltformNumEqualTo(String value) {
            addCriterion("PALTFORM_NUM =", value, "paltformNum");
            return (Criteria) this;
        }

        public Criteria andPaltformNumNotEqualTo(String value) {
            addCriterion("PALTFORM_NUM <>", value, "paltformNum");
            return (Criteria) this;
        }

        public Criteria andPaltformNumGreaterThan(String value) {
            addCriterion("PALTFORM_NUM >", value, "paltformNum");
            return (Criteria) this;
        }

        public Criteria andPaltformNumGreaterThanOrEqualTo(String value) {
            addCriterion("PALTFORM_NUM >=", value, "paltformNum");
            return (Criteria) this;
        }

        public Criteria andPaltformNumLessThan(String value) {
            addCriterion("PALTFORM_NUM <", value, "paltformNum");
            return (Criteria) this;
        }

        public Criteria andPaltformNumLessThanOrEqualTo(String value) {
            addCriterion("PALTFORM_NUM <=", value, "paltformNum");
            return (Criteria) this;
        }

        public Criteria andPaltformNumLike(String value) {
            addCriterion("PALTFORM_NUM like", value, "paltformNum");
            return (Criteria) this;
        }

        public Criteria andPaltformNumNotLike(String value) {
            addCriterion("PALTFORM_NUM not like", value, "paltformNum");
            return (Criteria) this;
        }

        public Criteria andPaltformNumIn(List<String> values) {
            addCriterion("PALTFORM_NUM in", values, "paltformNum");
            return (Criteria) this;
        }

        public Criteria andPaltformNumNotIn(List<String> values) {
            addCriterion("PALTFORM_NUM not in", values, "paltformNum");
            return (Criteria) this;
        }

        public Criteria andPaltformNumBetween(String value1, String value2) {
            addCriterion("PALTFORM_NUM between", value1, value2, "paltformNum");
            return (Criteria) this;
        }

        public Criteria andPaltformNumNotBetween(String value1, String value2) {
            addCriterion("PALTFORM_NUM not between", value1, value2, "paltformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNameIsNull() {
            addCriterion("PLATFORM_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPlatformNameIsNotNull() {
            addCriterion("PLATFORM_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformNameEqualTo(String value) {
            addCriterion("PLATFORM_NAME =", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameNotEqualTo(String value) {
            addCriterion("PLATFORM_NAME <>", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameGreaterThan(String value) {
            addCriterion("PLATFORM_NAME >", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameGreaterThanOrEqualTo(String value) {
            addCriterion("PLATFORM_NAME >=", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameLessThan(String value) {
            addCriterion("PLATFORM_NAME <", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameLessThanOrEqualTo(String value) {
            addCriterion("PLATFORM_NAME <=", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameLike(String value) {
            addCriterion("PLATFORM_NAME like", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameNotLike(String value) {
            addCriterion("PLATFORM_NAME not like", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameIn(List<String> values) {
            addCriterion("PLATFORM_NAME in", values, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameNotIn(List<String> values) {
            addCriterion("PLATFORM_NAME not in", values, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameBetween(String value1, String value2) {
            addCriterion("PLATFORM_NAME between", value1, value2, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameNotBetween(String value1, String value2) {
            addCriterion("PLATFORM_NAME not between", value1, value2, "platformName");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumIsNull() {
            addCriterion("JO_FIRST_KEY_NUM is null");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumIsNotNull() {
            addCriterion("JO_FIRST_KEY_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumEqualTo(String value) {
            addCriterion("JO_FIRST_KEY_NUM =", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumNotEqualTo(String value) {
            addCriterion("JO_FIRST_KEY_NUM <>", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumGreaterThan(String value) {
            addCriterion("JO_FIRST_KEY_NUM >", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumGreaterThanOrEqualTo(String value) {
            addCriterion("JO_FIRST_KEY_NUM >=", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumLessThan(String value) {
            addCriterion("JO_FIRST_KEY_NUM <", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumLessThanOrEqualTo(String value) {
            addCriterion("JO_FIRST_KEY_NUM <=", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumLike(String value) {
            addCriterion("JO_FIRST_KEY_NUM like", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumNotLike(String value) {
            addCriterion("JO_FIRST_KEY_NUM not like", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumIn(List<String> values) {
            addCriterion("JO_FIRST_KEY_NUM in", values, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumNotIn(List<String> values) {
            addCriterion("JO_FIRST_KEY_NUM not in", values, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumBetween(String value1, String value2) {
            addCriterion("JO_FIRST_KEY_NUM between", value1, value2, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumNotBetween(String value1, String value2) {
            addCriterion("JO_FIRST_KEY_NUM not between", value1, value2, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumIsNull() {
            addCriterion("JO_SECOND_KEY_NUM is null");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumIsNotNull() {
            addCriterion("JO_SECOND_KEY_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumEqualTo(String value) {
            addCriterion("JO_SECOND_KEY_NUM =", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumNotEqualTo(String value) {
            addCriterion("JO_SECOND_KEY_NUM <>", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumGreaterThan(String value) {
            addCriterion("JO_SECOND_KEY_NUM >", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumGreaterThanOrEqualTo(String value) {
            addCriterion("JO_SECOND_KEY_NUM >=", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumLessThan(String value) {
            addCriterion("JO_SECOND_KEY_NUM <", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumLessThanOrEqualTo(String value) {
            addCriterion("JO_SECOND_KEY_NUM <=", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumLike(String value) {
            addCriterion("JO_SECOND_KEY_NUM like", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumNotLike(String value) {
            addCriterion("JO_SECOND_KEY_NUM not like", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumIn(List<String> values) {
            addCriterion("JO_SECOND_KEY_NUM in", values, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumNotIn(List<String> values) {
            addCriterion("JO_SECOND_KEY_NUM not in", values, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumBetween(String value1, String value2) {
            addCriterion("JO_SECOND_KEY_NUM between", value1, value2, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumNotBetween(String value1, String value2) {
            addCriterion("JO_SECOND_KEY_NUM not between", value1, value2, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelIsNull() {
            addCriterion("PRIORITY_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelIsNotNull() {
            addCriterion("PRIORITY_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelEqualTo(String value) {
            addCriterion("PRIORITY_LEVEL =", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelNotEqualTo(String value) {
            addCriterion("PRIORITY_LEVEL <>", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelGreaterThan(String value) {
            addCriterion("PRIORITY_LEVEL >", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelGreaterThanOrEqualTo(String value) {
            addCriterion("PRIORITY_LEVEL >=", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelLessThan(String value) {
            addCriterion("PRIORITY_LEVEL <", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelLessThanOrEqualTo(String value) {
            addCriterion("PRIORITY_LEVEL <=", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelLike(String value) {
            addCriterion("PRIORITY_LEVEL like", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelNotLike(String value) {
            addCriterion("PRIORITY_LEVEL not like", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelIn(List<String> values) {
            addCriterion("PRIORITY_LEVEL in", values, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelNotIn(List<String> values) {
            addCriterion("PRIORITY_LEVEL not in", values, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelBetween(String value1, String value2) {
            addCriterion("PRIORITY_LEVEL between", value1, value2, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelNotBetween(String value1, String value2) {
            addCriterion("PRIORITY_LEVEL not between", value1, value2, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andLaunchTimeIsNull() {
            addCriterion("LAUNCH_TIME is null");
            return (Criteria) this;
        }

        public Criteria andLaunchTimeIsNotNull() {
            addCriterion("LAUNCH_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andLaunchTimeEqualTo(Date value) {
            addCriterion("LAUNCH_TIME =", value, "launchTime");
            return (Criteria) this;
        }

        public Criteria andLaunchTimeNotEqualTo(Date value) {
            addCriterion("LAUNCH_TIME <>", value, "launchTime");
            return (Criteria) this;
        }

        public Criteria andLaunchTimeGreaterThan(Date value) {
            addCriterion("LAUNCH_TIME >", value, "launchTime");
            return (Criteria) this;
        }

        public Criteria andLaunchTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("LAUNCH_TIME >=", value, "launchTime");
            return (Criteria) this;
        }

        public Criteria andLaunchTimeLessThan(Date value) {
            addCriterion("LAUNCH_TIME <", value, "launchTime");
            return (Criteria) this;
        }

        public Criteria andLaunchTimeLessThanOrEqualTo(Date value) {
            addCriterion("LAUNCH_TIME <=", value, "launchTime");
            return (Criteria) this;
        }

        public Criteria andLaunchTimeIn(List<Date> values) {
            addCriterion("LAUNCH_TIME in", values, "launchTime");
            return (Criteria) this;
        }

        public Criteria andLaunchTimeNotIn(List<Date> values) {
            addCriterion("LAUNCH_TIME not in", values, "launchTime");
            return (Criteria) this;
        }

        public Criteria andLaunchTimeBetween(Date value1, Date value2) {
            addCriterion("LAUNCH_TIME between", value1, value2, "launchTime");
            return (Criteria) this;
        }

        public Criteria andLaunchTimeNotBetween(Date value1, Date value2) {
            addCriterion("LAUNCH_TIME not between", value1, value2, "launchTime");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonIdIsNull() {
            addCriterion("LAUNCH_PERSON_ID is null");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonIdIsNotNull() {
            addCriterion("LAUNCH_PERSON_ID is not null");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonIdEqualTo(String value) {
            addCriterion("LAUNCH_PERSON_ID =", value, "launchPersonId");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonIdNotEqualTo(String value) {
            addCriterion("LAUNCH_PERSON_ID <>", value, "launchPersonId");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonIdGreaterThan(String value) {
            addCriterion("LAUNCH_PERSON_ID >", value, "launchPersonId");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonIdGreaterThanOrEqualTo(String value) {
            addCriterion("LAUNCH_PERSON_ID >=", value, "launchPersonId");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonIdLessThan(String value) {
            addCriterion("LAUNCH_PERSON_ID <", value, "launchPersonId");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonIdLessThanOrEqualTo(String value) {
            addCriterion("LAUNCH_PERSON_ID <=", value, "launchPersonId");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonIdLike(String value) {
            addCriterion("LAUNCH_PERSON_ID like", value, "launchPersonId");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonIdNotLike(String value) {
            addCriterion("LAUNCH_PERSON_ID not like", value, "launchPersonId");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonIdIn(List<String> values) {
            addCriterion("LAUNCH_PERSON_ID in", values, "launchPersonId");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonIdNotIn(List<String> values) {
            addCriterion("LAUNCH_PERSON_ID not in", values, "launchPersonId");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonIdBetween(String value1, String value2) {
            addCriterion("LAUNCH_PERSON_ID between", value1, value2, "launchPersonId");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonIdNotBetween(String value1, String value2) {
            addCriterion("LAUNCH_PERSON_ID not between", value1, value2, "launchPersonId");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonNameIsNull() {
            addCriterion("LAUNCH_PERSON_NAME is null");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonNameIsNotNull() {
            addCriterion("LAUNCH_PERSON_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonNameEqualTo(String value) {
            addCriterion("LAUNCH_PERSON_NAME =", value, "launchPersonName");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonNameNotEqualTo(String value) {
            addCriterion("LAUNCH_PERSON_NAME <>", value, "launchPersonName");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonNameGreaterThan(String value) {
            addCriterion("LAUNCH_PERSON_NAME >", value, "launchPersonName");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonNameGreaterThanOrEqualTo(String value) {
            addCriterion("LAUNCH_PERSON_NAME >=", value, "launchPersonName");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonNameLessThan(String value) {
            addCriterion("LAUNCH_PERSON_NAME <", value, "launchPersonName");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonNameLessThanOrEqualTo(String value) {
            addCriterion("LAUNCH_PERSON_NAME <=", value, "launchPersonName");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonNameLike(String value) {
            addCriterion("LAUNCH_PERSON_NAME like", value, "launchPersonName");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonNameNotLike(String value) {
            addCriterion("LAUNCH_PERSON_NAME not like", value, "launchPersonName");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonNameIn(List<String> values) {
            addCriterion("LAUNCH_PERSON_NAME in", values, "launchPersonName");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonNameNotIn(List<String> values) {
            addCriterion("LAUNCH_PERSON_NAME not in", values, "launchPersonName");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonNameBetween(String value1, String value2) {
            addCriterion("LAUNCH_PERSON_NAME between", value1, value2, "launchPersonName");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonNameNotBetween(String value1, String value2) {
            addCriterion("LAUNCH_PERSON_NAME not between", value1, value2, "launchPersonName");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonEmailIsNull() {
            addCriterion("LAUNCH_PERSON_EMAIL is null");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonEmailIsNotNull() {
            addCriterion("LAUNCH_PERSON_EMAIL is not null");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonEmailEqualTo(String value) {
            addCriterion("LAUNCH_PERSON_EMAIL =", value, "launchPersonEmail");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonEmailNotEqualTo(String value) {
            addCriterion("LAUNCH_PERSON_EMAIL <>", value, "launchPersonEmail");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonEmailGreaterThan(String value) {
            addCriterion("LAUNCH_PERSON_EMAIL >", value, "launchPersonEmail");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonEmailGreaterThanOrEqualTo(String value) {
            addCriterion("LAUNCH_PERSON_EMAIL >=", value, "launchPersonEmail");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonEmailLessThan(String value) {
            addCriterion("LAUNCH_PERSON_EMAIL <", value, "launchPersonEmail");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonEmailLessThanOrEqualTo(String value) {
            addCriterion("LAUNCH_PERSON_EMAIL <=", value, "launchPersonEmail");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonEmailLike(String value) {
            addCriterion("LAUNCH_PERSON_EMAIL like", value, "launchPersonEmail");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonEmailNotLike(String value) {
            addCriterion("LAUNCH_PERSON_EMAIL not like", value, "launchPersonEmail");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonEmailIn(List<String> values) {
            addCriterion("LAUNCH_PERSON_EMAIL in", values, "launchPersonEmail");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonEmailNotIn(List<String> values) {
            addCriterion("LAUNCH_PERSON_EMAIL not in", values, "launchPersonEmail");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonEmailBetween(String value1, String value2) {
            addCriterion("LAUNCH_PERSON_EMAIL between", value1, value2, "launchPersonEmail");
            return (Criteria) this;
        }

        public Criteria andLaunchPersonEmailNotBetween(String value1, String value2) {
            addCriterion("LAUNCH_PERSON_EMAIL not between", value1, value2, "launchPersonEmail");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchIdIsNull() {
            addCriterion("LAUNCH_BRANCH_ID is null");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchIdIsNotNull() {
            addCriterion("LAUNCH_BRANCH_ID is not null");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchIdEqualTo(String value) {
            addCriterion("LAUNCH_BRANCH_ID =", value, "launchBranchId");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchIdNotEqualTo(String value) {
            addCriterion("LAUNCH_BRANCH_ID <>", value, "launchBranchId");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchIdGreaterThan(String value) {
            addCriterion("LAUNCH_BRANCH_ID >", value, "launchBranchId");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchIdGreaterThanOrEqualTo(String value) {
            addCriterion("LAUNCH_BRANCH_ID >=", value, "launchBranchId");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchIdLessThan(String value) {
            addCriterion("LAUNCH_BRANCH_ID <", value, "launchBranchId");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchIdLessThanOrEqualTo(String value) {
            addCriterion("LAUNCH_BRANCH_ID <=", value, "launchBranchId");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchIdLike(String value) {
            addCriterion("LAUNCH_BRANCH_ID like", value, "launchBranchId");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchIdNotLike(String value) {
            addCriterion("LAUNCH_BRANCH_ID not like", value, "launchBranchId");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchIdIn(List<String> values) {
            addCriterion("LAUNCH_BRANCH_ID in", values, "launchBranchId");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchIdNotIn(List<String> values) {
            addCriterion("LAUNCH_BRANCH_ID not in", values, "launchBranchId");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchIdBetween(String value1, String value2) {
            addCriterion("LAUNCH_BRANCH_ID between", value1, value2, "launchBranchId");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchIdNotBetween(String value1, String value2) {
            addCriterion("LAUNCH_BRANCH_ID not between", value1, value2, "launchBranchId");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchNameIsNull() {
            addCriterion("LAUNCH_BRANCH_NAME is null");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchNameIsNotNull() {
            addCriterion("LAUNCH_BRANCH_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchNameEqualTo(String value) {
            addCriterion("LAUNCH_BRANCH_NAME =", value, "launchBranchName");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchNameNotEqualTo(String value) {
            addCriterion("LAUNCH_BRANCH_NAME <>", value, "launchBranchName");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchNameGreaterThan(String value) {
            addCriterion("LAUNCH_BRANCH_NAME >", value, "launchBranchName");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchNameGreaterThanOrEqualTo(String value) {
            addCriterion("LAUNCH_BRANCH_NAME >=", value, "launchBranchName");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchNameLessThan(String value) {
            addCriterion("LAUNCH_BRANCH_NAME <", value, "launchBranchName");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchNameLessThanOrEqualTo(String value) {
            addCriterion("LAUNCH_BRANCH_NAME <=", value, "launchBranchName");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchNameLike(String value) {
            addCriterion("LAUNCH_BRANCH_NAME like", value, "launchBranchName");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchNameNotLike(String value) {
            addCriterion("LAUNCH_BRANCH_NAME not like", value, "launchBranchName");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchNameIn(List<String> values) {
            addCriterion("LAUNCH_BRANCH_NAME in", values, "launchBranchName");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchNameNotIn(List<String> values) {
            addCriterion("LAUNCH_BRANCH_NAME not in", values, "launchBranchName");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchNameBetween(String value1, String value2) {
            addCriterion("LAUNCH_BRANCH_NAME between", value1, value2, "launchBranchName");
            return (Criteria) this;
        }

        public Criteria andLaunchBranchNameNotBetween(String value1, String value2) {
            addCriterion("LAUNCH_BRANCH_NAME not between", value1, value2, "launchBranchName");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupCodeIsNull() {
            addCriterion("ACCEPT_GROUP_CODE is null");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupCodeIsNotNull() {
            addCriterion("ACCEPT_GROUP_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupCodeEqualTo(String value) {
            addCriterion("ACCEPT_GROUP_CODE =", value, "acceptGroupCode");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupCodeNotEqualTo(String value) {
            addCriterion("ACCEPT_GROUP_CODE <>", value, "acceptGroupCode");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupCodeGreaterThan(String value) {
            addCriterion("ACCEPT_GROUP_CODE >", value, "acceptGroupCode");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ACCEPT_GROUP_CODE >=", value, "acceptGroupCode");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupCodeLessThan(String value) {
            addCriterion("ACCEPT_GROUP_CODE <", value, "acceptGroupCode");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupCodeLessThanOrEqualTo(String value) {
            addCriterion("ACCEPT_GROUP_CODE <=", value, "acceptGroupCode");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupCodeLike(String value) {
            addCriterion("ACCEPT_GROUP_CODE like", value, "acceptGroupCode");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupCodeNotLike(String value) {
            addCriterion("ACCEPT_GROUP_CODE not like", value, "acceptGroupCode");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupCodeIn(List<String> values) {
            addCriterion("ACCEPT_GROUP_CODE in", values, "acceptGroupCode");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupCodeNotIn(List<String> values) {
            addCriterion("ACCEPT_GROUP_CODE not in", values, "acceptGroupCode");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupCodeBetween(String value1, String value2) {
            addCriterion("ACCEPT_GROUP_CODE between", value1, value2, "acceptGroupCode");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupCodeNotBetween(String value1, String value2) {
            addCriterion("ACCEPT_GROUP_CODE not between", value1, value2, "acceptGroupCode");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupIsNull() {
            addCriterion("ACCEPT_GROUP is null");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupIsNotNull() {
            addCriterion("ACCEPT_GROUP is not null");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupEqualTo(String value) {
            addCriterion("ACCEPT_GROUP =", value, "acceptGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupNotEqualTo(String value) {
            addCriterion("ACCEPT_GROUP <>", value, "acceptGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupGreaterThan(String value) {
            addCriterion("ACCEPT_GROUP >", value, "acceptGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupGreaterThanOrEqualTo(String value) {
            addCriterion("ACCEPT_GROUP >=", value, "acceptGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupLessThan(String value) {
            addCriterion("ACCEPT_GROUP <", value, "acceptGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupLessThanOrEqualTo(String value) {
            addCriterion("ACCEPT_GROUP <=", value, "acceptGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupLike(String value) {
            addCriterion("ACCEPT_GROUP like", value, "acceptGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupNotLike(String value) {
            addCriterion("ACCEPT_GROUP not like", value, "acceptGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupIn(List<String> values) {
            addCriterion("ACCEPT_GROUP in", values, "acceptGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupNotIn(List<String> values) {
            addCriterion("ACCEPT_GROUP not in", values, "acceptGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupBetween(String value1, String value2) {
            addCriterion("ACCEPT_GROUP between", value1, value2, "acceptGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptGroupNotBetween(String value1, String value2) {
            addCriterion("ACCEPT_GROUP not between", value1, value2, "acceptGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptNowGroupIsNull() {
            addCriterion("ACCEPT_NOW_GROUP is null");
            return (Criteria) this;
        }

        public Criteria andAcceptNowGroupIsNotNull() {
            addCriterion("ACCEPT_NOW_GROUP is not null");
            return (Criteria) this;
        }

        public Criteria andAcceptNowGroupEqualTo(String value) {
            addCriterion("ACCEPT_NOW_GROUP =", value, "acceptNowGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptNowGroupNotEqualTo(String value) {
            addCriterion("ACCEPT_NOW_GROUP <>", value, "acceptNowGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptNowGroupGreaterThan(String value) {
            addCriterion("ACCEPT_NOW_GROUP >", value, "acceptNowGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptNowGroupGreaterThanOrEqualTo(String value) {
            addCriterion("ACCEPT_NOW_GROUP >=", value, "acceptNowGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptNowGroupLessThan(String value) {
            addCriterion("ACCEPT_NOW_GROUP <", value, "acceptNowGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptNowGroupLessThanOrEqualTo(String value) {
            addCriterion("ACCEPT_NOW_GROUP <=", value, "acceptNowGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptNowGroupLike(String value) {
            addCriterion("ACCEPT_NOW_GROUP like", value, "acceptNowGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptNowGroupNotLike(String value) {
            addCriterion("ACCEPT_NOW_GROUP not like", value, "acceptNowGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptNowGroupIn(List<String> values) {
            addCriterion("ACCEPT_NOW_GROUP in", values, "acceptNowGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptNowGroupNotIn(List<String> values) {
            addCriterion("ACCEPT_NOW_GROUP not in", values, "acceptNowGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptNowGroupBetween(String value1, String value2) {
            addCriterion("ACCEPT_NOW_GROUP between", value1, value2, "acceptNowGroup");
            return (Criteria) this;
        }

        public Criteria andAcceptNowGroupNotBetween(String value1, String value2) {
            addCriterion("ACCEPT_NOW_GROUP not between", value1, value2, "acceptNowGroup");
            return (Criteria) this;
        }

        public Criteria andDealTimeStartIsNull() {
            addCriterion("DEAL_TIME_START is null");
            return (Criteria) this;
        }

        public Criteria andDealTimeStartIsNotNull() {
            addCriterion("DEAL_TIME_START is not null");
            return (Criteria) this;
        }

        public Criteria andDealTimeStartEqualTo(Date value) {
            addCriterion("DEAL_TIME_START =", value, "dealTimeStart");
            return (Criteria) this;
        }

        public Criteria andDealTimeStartNotEqualTo(Date value) {
            addCriterion("DEAL_TIME_START <>", value, "dealTimeStart");
            return (Criteria) this;
        }

        public Criteria andDealTimeStartGreaterThan(Date value) {
            addCriterion("DEAL_TIME_START >", value, "dealTimeStart");
            return (Criteria) this;
        }

        public Criteria andDealTimeStartGreaterThanOrEqualTo(Date value) {
            addCriterion("DEAL_TIME_START >=", value, "dealTimeStart");
            return (Criteria) this;
        }

        public Criteria andDealTimeStartLessThan(Date value) {
            addCriterion("DEAL_TIME_START <", value, "dealTimeStart");
            return (Criteria) this;
        }

        public Criteria andDealTimeStartLessThanOrEqualTo(Date value) {
            addCriterion("DEAL_TIME_START <=", value, "dealTimeStart");
            return (Criteria) this;
        }

        public Criteria andDealTimeStartIn(List<Date> values) {
            addCriterion("DEAL_TIME_START in", values, "dealTimeStart");
            return (Criteria) this;
        }

        public Criteria andDealTimeStartNotIn(List<Date> values) {
            addCriterion("DEAL_TIME_START not in", values, "dealTimeStart");
            return (Criteria) this;
        }

        public Criteria andDealTimeStartBetween(Date value1, Date value2) {
            addCriterion("DEAL_TIME_START between", value1, value2, "dealTimeStart");
            return (Criteria) this;
        }

        public Criteria andDealTimeStartNotBetween(Date value1, Date value2) {
            addCriterion("DEAL_TIME_START not between", value1, value2, "dealTimeStart");
            return (Criteria) this;
        }

        public Criteria andDealTimeEndIsNull() {
            addCriterion("DEAL_TIME_END is null");
            return (Criteria) this;
        }

        public Criteria andDealTimeEndIsNotNull() {
            addCriterion("DEAL_TIME_END is not null");
            return (Criteria) this;
        }

        public Criteria andDealTimeEndEqualTo(Date value) {
            addCriterion("DEAL_TIME_END =", value, "dealTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDealTimeEndNotEqualTo(Date value) {
            addCriterion("DEAL_TIME_END <>", value, "dealTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDealTimeEndGreaterThan(Date value) {
            addCriterion("DEAL_TIME_END >", value, "dealTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDealTimeEndGreaterThanOrEqualTo(Date value) {
            addCriterion("DEAL_TIME_END >=", value, "dealTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDealTimeEndLessThan(Date value) {
            addCriterion("DEAL_TIME_END <", value, "dealTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDealTimeEndLessThanOrEqualTo(Date value) {
            addCriterion("DEAL_TIME_END <=", value, "dealTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDealTimeEndIn(List<Date> values) {
            addCriterion("DEAL_TIME_END in", values, "dealTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDealTimeEndNotIn(List<Date> values) {
            addCriterion("DEAL_TIME_END not in", values, "dealTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDealTimeEndBetween(Date value1, Date value2) {
            addCriterion("DEAL_TIME_END between", value1, value2, "dealTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDealTimeEndNotBetween(Date value1, Date value2) {
            addCriterion("DEAL_TIME_END not between", value1, value2, "dealTimeEnd");
            return (Criteria) this;
        }

        public Criteria andDealTimeLengthIsNull() {
            addCriterion("DEAL_TIME_LENGTH is null");
            return (Criteria) this;
        }

        public Criteria andDealTimeLengthIsNotNull() {
            addCriterion("DEAL_TIME_LENGTH is not null");
            return (Criteria) this;
        }

        public Criteria andDealTimeLengthEqualTo(BigDecimal value) {
            addCriterion("DEAL_TIME_LENGTH =", value, "dealTimeLength");
            return (Criteria) this;
        }

        public Criteria andDealTimeLengthNotEqualTo(BigDecimal value) {
            addCriterion("DEAL_TIME_LENGTH <>", value, "dealTimeLength");
            return (Criteria) this;
        }

        public Criteria andDealTimeLengthGreaterThan(BigDecimal value) {
            addCriterion("DEAL_TIME_LENGTH >", value, "dealTimeLength");
            return (Criteria) this;
        }

        public Criteria andDealTimeLengthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DEAL_TIME_LENGTH >=", value, "dealTimeLength");
            return (Criteria) this;
        }

        public Criteria andDealTimeLengthLessThan(BigDecimal value) {
            addCriterion("DEAL_TIME_LENGTH <", value, "dealTimeLength");
            return (Criteria) this;
        }

        public Criteria andDealTimeLengthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DEAL_TIME_LENGTH <=", value, "dealTimeLength");
            return (Criteria) this;
        }

        public Criteria andDealTimeLengthIn(List<BigDecimal> values) {
            addCriterion("DEAL_TIME_LENGTH in", values, "dealTimeLength");
            return (Criteria) this;
        }

        public Criteria andDealTimeLengthNotIn(List<BigDecimal> values) {
            addCriterion("DEAL_TIME_LENGTH not in", values, "dealTimeLength");
            return (Criteria) this;
        }

        public Criteria andDealTimeLengthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEAL_TIME_LENGTH between", value1, value2, "dealTimeLength");
            return (Criteria) this;
        }

        public Criteria andDealTimeLengthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEAL_TIME_LENGTH not between", value1, value2, "dealTimeLength");
            return (Criteria) this;
        }

        public Criteria andJoProgressIsNull() {
            addCriterion("JO_PROGRESS is null");
            return (Criteria) this;
        }

        public Criteria andJoProgressIsNotNull() {
            addCriterion("JO_PROGRESS is not null");
            return (Criteria) this;
        }

        public Criteria andJoProgressEqualTo(String value) {
            addCriterion("JO_PROGRESS =", value, "joProgress");
            return (Criteria) this;
        }

        public Criteria andJoProgressNotEqualTo(String value) {
            addCriterion("JO_PROGRESS <>", value, "joProgress");
            return (Criteria) this;
        }

        public Criteria andJoProgressGreaterThan(String value) {
            addCriterion("JO_PROGRESS >", value, "joProgress");
            return (Criteria) this;
        }

        public Criteria andJoProgressGreaterThanOrEqualTo(String value) {
            addCriterion("JO_PROGRESS >=", value, "joProgress");
            return (Criteria) this;
        }

        public Criteria andJoProgressLessThan(String value) {
            addCriterion("JO_PROGRESS <", value, "joProgress");
            return (Criteria) this;
        }

        public Criteria andJoProgressLessThanOrEqualTo(String value) {
            addCriterion("JO_PROGRESS <=", value, "joProgress");
            return (Criteria) this;
        }

        public Criteria andJoProgressLike(String value) {
            addCriterion("JO_PROGRESS like", value, "joProgress");
            return (Criteria) this;
        }

        public Criteria andJoProgressNotLike(String value) {
            addCriterion("JO_PROGRESS not like", value, "joProgress");
            return (Criteria) this;
        }

        public Criteria andJoProgressIn(List<String> values) {
            addCriterion("JO_PROGRESS in", values, "joProgress");
            return (Criteria) this;
        }

        public Criteria andJoProgressNotIn(List<String> values) {
            addCriterion("JO_PROGRESS not in", values, "joProgress");
            return (Criteria) this;
        }

        public Criteria andJoProgressBetween(String value1, String value2) {
            addCriterion("JO_PROGRESS between", value1, value2, "joProgress");
            return (Criteria) this;
        }

        public Criteria andJoProgressNotBetween(String value1, String value2) {
            addCriterion("JO_PROGRESS not between", value1, value2, "joProgress");
            return (Criteria) this;
        }

        public Criteria andJoTitleIsNull() {
            addCriterion("JO_TITLE is null");
            return (Criteria) this;
        }

        public Criteria andJoTitleIsNotNull() {
            addCriterion("JO_TITLE is not null");
            return (Criteria) this;
        }

        public Criteria andJoTitleEqualTo(String value) {
            addCriterion("JO_TITLE =", value, "joTitle");
            return (Criteria) this;
        }

        public Criteria andJoTitleNotEqualTo(String value) {
            addCriterion("JO_TITLE <>", value, "joTitle");
            return (Criteria) this;
        }

        public Criteria andJoTitleGreaterThan(String value) {
            addCriterion("JO_TITLE >", value, "joTitle");
            return (Criteria) this;
        }

        public Criteria andJoTitleGreaterThanOrEqualTo(String value) {
            addCriterion("JO_TITLE >=", value, "joTitle");
            return (Criteria) this;
        }

        public Criteria andJoTitleLessThan(String value) {
            addCriterion("JO_TITLE <", value, "joTitle");
            return (Criteria) this;
        }

        public Criteria andJoTitleLessThanOrEqualTo(String value) {
            addCriterion("JO_TITLE <=", value, "joTitle");
            return (Criteria) this;
        }

        public Criteria andJoTitleLike(String value) {
            addCriterion("JO_TITLE like", value, "joTitle");
            return (Criteria) this;
        }

        public Criteria andJoTitleNotLike(String value) {
            addCriterion("JO_TITLE not like", value, "joTitle");
            return (Criteria) this;
        }

        public Criteria andJoTitleIn(List<String> values) {
            addCriterion("JO_TITLE in", values, "joTitle");
            return (Criteria) this;
        }

        public Criteria andJoTitleNotIn(List<String> values) {
            addCriterion("JO_TITLE not in", values, "joTitle");
            return (Criteria) this;
        }

        public Criteria andJoTitleBetween(String value1, String value2) {
            addCriterion("JO_TITLE between", value1, value2, "joTitle");
            return (Criteria) this;
        }

        public Criteria andJoTitleNotBetween(String value1, String value2) {
            addCriterion("JO_TITLE not between", value1, value2, "joTitle");
            return (Criteria) this;
        }

        public Criteria andJoContentIsNull() {
            addCriterion("JO_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andJoContentIsNotNull() {
            addCriterion("JO_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andJoContentEqualTo(String value) {
            addCriterion("JO_CONTENT =", value, "joContent");
            return (Criteria) this;
        }

        public Criteria andJoContentNotEqualTo(String value) {
            addCriterion("JO_CONTENT <>", value, "joContent");
            return (Criteria) this;
        }

        public Criteria andJoContentGreaterThan(String value) {
            addCriterion("JO_CONTENT >", value, "joContent");
            return (Criteria) this;
        }

        public Criteria andJoContentGreaterThanOrEqualTo(String value) {
            addCriterion("JO_CONTENT >=", value, "joContent");
            return (Criteria) this;
        }

        public Criteria andJoContentLessThan(String value) {
            addCriterion("JO_CONTENT <", value, "joContent");
            return (Criteria) this;
        }

        public Criteria andJoContentLessThanOrEqualTo(String value) {
            addCriterion("JO_CONTENT <=", value, "joContent");
            return (Criteria) this;
        }

        public Criteria andJoContentLike(String value) {
            addCriterion("JO_CONTENT like", value, "joContent");
            return (Criteria) this;
        }

        public Criteria andJoContentNotLike(String value) {
            addCriterion("JO_CONTENT not like", value, "joContent");
            return (Criteria) this;
        }

        public Criteria andJoContentIn(List<String> values) {
            addCriterion("JO_CONTENT in", values, "joContent");
            return (Criteria) this;
        }

        public Criteria andJoContentNotIn(List<String> values) {
            addCriterion("JO_CONTENT not in", values, "joContent");
            return (Criteria) this;
        }

        public Criteria andJoContentBetween(String value1, String value2) {
            addCriterion("JO_CONTENT between", value1, value2, "joContent");
            return (Criteria) this;
        }

        public Criteria andJoContentNotBetween(String value1, String value2) {
            addCriterion("JO_CONTENT not between", value1, value2, "joContent");
            return (Criteria) this;
        }

        public Criteria andJoAssessLevelIsNull() {
            addCriterion("JO_ASSESS_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andJoAssessLevelIsNotNull() {
            addCriterion("JO_ASSESS_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andJoAssessLevelEqualTo(BigDecimal value) {
            addCriterion("JO_ASSESS_LEVEL =", value, "joAssessLevel");
            return (Criteria) this;
        }

        public Criteria andJoAssessLevelNotEqualTo(BigDecimal value) {
            addCriterion("JO_ASSESS_LEVEL <>", value, "joAssessLevel");
            return (Criteria) this;
        }

        public Criteria andJoAssessLevelGreaterThan(BigDecimal value) {
            addCriterion("JO_ASSESS_LEVEL >", value, "joAssessLevel");
            return (Criteria) this;
        }

        public Criteria andJoAssessLevelGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("JO_ASSESS_LEVEL >=", value, "joAssessLevel");
            return (Criteria) this;
        }

        public Criteria andJoAssessLevelLessThan(BigDecimal value) {
            addCriterion("JO_ASSESS_LEVEL <", value, "joAssessLevel");
            return (Criteria) this;
        }

        public Criteria andJoAssessLevelLessThanOrEqualTo(BigDecimal value) {
            addCriterion("JO_ASSESS_LEVEL <=", value, "joAssessLevel");
            return (Criteria) this;
        }

        public Criteria andJoAssessLevelIn(List<BigDecimal> values) {
            addCriterion("JO_ASSESS_LEVEL in", values, "joAssessLevel");
            return (Criteria) this;
        }

        public Criteria andJoAssessLevelNotIn(List<BigDecimal> values) {
            addCriterion("JO_ASSESS_LEVEL not in", values, "joAssessLevel");
            return (Criteria) this;
        }

        public Criteria andJoAssessLevelBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("JO_ASSESS_LEVEL between", value1, value2, "joAssessLevel");
            return (Criteria) this;
        }

        public Criteria andJoAssessLevelNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("JO_ASSESS_LEVEL not between", value1, value2, "joAssessLevel");
            return (Criteria) this;
        }

        public Criteria andJoAssessorNameIsNull() {
            addCriterion("JO_ASSESSOR_NAME is null");
            return (Criteria) this;
        }

        public Criteria andJoAssessorNameIsNotNull() {
            addCriterion("JO_ASSESSOR_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andJoAssessorNameEqualTo(String value) {
            addCriterion("JO_ASSESSOR_NAME =", value, "joAssessorName");
            return (Criteria) this;
        }

        public Criteria andJoAssessorNameNotEqualTo(String value) {
            addCriterion("JO_ASSESSOR_NAME <>", value, "joAssessorName");
            return (Criteria) this;
        }

        public Criteria andJoAssessorNameGreaterThan(String value) {
            addCriterion("JO_ASSESSOR_NAME >", value, "joAssessorName");
            return (Criteria) this;
        }

        public Criteria andJoAssessorNameGreaterThanOrEqualTo(String value) {
            addCriterion("JO_ASSESSOR_NAME >=", value, "joAssessorName");
            return (Criteria) this;
        }

        public Criteria andJoAssessorNameLessThan(String value) {
            addCriterion("JO_ASSESSOR_NAME <", value, "joAssessorName");
            return (Criteria) this;
        }

        public Criteria andJoAssessorNameLessThanOrEqualTo(String value) {
            addCriterion("JO_ASSESSOR_NAME <=", value, "joAssessorName");
            return (Criteria) this;
        }

        public Criteria andJoAssessorNameLike(String value) {
            addCriterion("JO_ASSESSOR_NAME like", value, "joAssessorName");
            return (Criteria) this;
        }

        public Criteria andJoAssessorNameNotLike(String value) {
            addCriterion("JO_ASSESSOR_NAME not like", value, "joAssessorName");
            return (Criteria) this;
        }

        public Criteria andJoAssessorNameIn(List<String> values) {
            addCriterion("JO_ASSESSOR_NAME in", values, "joAssessorName");
            return (Criteria) this;
        }

        public Criteria andJoAssessorNameNotIn(List<String> values) {
            addCriterion("JO_ASSESSOR_NAME not in", values, "joAssessorName");
            return (Criteria) this;
        }

        public Criteria andJoAssessorNameBetween(String value1, String value2) {
            addCriterion("JO_ASSESSOR_NAME between", value1, value2, "joAssessorName");
            return (Criteria) this;
        }

        public Criteria andJoAssessorNameNotBetween(String value1, String value2) {
            addCriterion("JO_ASSESSOR_NAME not between", value1, value2, "joAssessorName");
            return (Criteria) this;
        }

        public Criteria andJoAssessorTypeIsNull() {
            addCriterion("JO_ASSESSOR_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andJoAssessorTypeIsNotNull() {
            addCriterion("JO_ASSESSOR_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andJoAssessorTypeEqualTo(String value) {
            addCriterion("JO_ASSESSOR_TYPE =", value, "joAssessorType");
            return (Criteria) this;
        }

        public Criteria andJoAssessorTypeNotEqualTo(String value) {
            addCriterion("JO_ASSESSOR_TYPE <>", value, "joAssessorType");
            return (Criteria) this;
        }

        public Criteria andJoAssessorTypeGreaterThan(String value) {
            addCriterion("JO_ASSESSOR_TYPE >", value, "joAssessorType");
            return (Criteria) this;
        }

        public Criteria andJoAssessorTypeGreaterThanOrEqualTo(String value) {
            addCriterion("JO_ASSESSOR_TYPE >=", value, "joAssessorType");
            return (Criteria) this;
        }

        public Criteria andJoAssessorTypeLessThan(String value) {
            addCriterion("JO_ASSESSOR_TYPE <", value, "joAssessorType");
            return (Criteria) this;
        }

        public Criteria andJoAssessorTypeLessThanOrEqualTo(String value) {
            addCriterion("JO_ASSESSOR_TYPE <=", value, "joAssessorType");
            return (Criteria) this;
        }

        public Criteria andJoAssessorTypeLike(String value) {
            addCriterion("JO_ASSESSOR_TYPE like", value, "joAssessorType");
            return (Criteria) this;
        }

        public Criteria andJoAssessorTypeNotLike(String value) {
            addCriterion("JO_ASSESSOR_TYPE not like", value, "joAssessorType");
            return (Criteria) this;
        }

        public Criteria andJoAssessorTypeIn(List<String> values) {
            addCriterion("JO_ASSESSOR_TYPE in", values, "joAssessorType");
            return (Criteria) this;
        }

        public Criteria andJoAssessorTypeNotIn(List<String> values) {
            addCriterion("JO_ASSESSOR_TYPE not in", values, "joAssessorType");
            return (Criteria) this;
        }

        public Criteria andJoAssessorTypeBetween(String value1, String value2) {
            addCriterion("JO_ASSESSOR_TYPE between", value1, value2, "joAssessorType");
            return (Criteria) this;
        }

        public Criteria andJoAssessorTypeNotBetween(String value1, String value2) {
            addCriterion("JO_ASSESSOR_TYPE not between", value1, value2, "joAssessorType");
            return (Criteria) this;
        }

        public Criteria andJoAssessorIdIsNull() {
            addCriterion("JO_ASSESSOR_ID is null");
            return (Criteria) this;
        }

        public Criteria andJoAssessorIdIsNotNull() {
            addCriterion("JO_ASSESSOR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andJoAssessorIdEqualTo(String value) {
            addCriterion("JO_ASSESSOR_ID =", value, "joAssessorId");
            return (Criteria) this;
        }

        public Criteria andJoAssessorIdNotEqualTo(String value) {
            addCriterion("JO_ASSESSOR_ID <>", value, "joAssessorId");
            return (Criteria) this;
        }

        public Criteria andJoAssessorIdGreaterThan(String value) {
            addCriterion("JO_ASSESSOR_ID >", value, "joAssessorId");
            return (Criteria) this;
        }

        public Criteria andJoAssessorIdGreaterThanOrEqualTo(String value) {
            addCriterion("JO_ASSESSOR_ID >=", value, "joAssessorId");
            return (Criteria) this;
        }

        public Criteria andJoAssessorIdLessThan(String value) {
            addCriterion("JO_ASSESSOR_ID <", value, "joAssessorId");
            return (Criteria) this;
        }

        public Criteria andJoAssessorIdLessThanOrEqualTo(String value) {
            addCriterion("JO_ASSESSOR_ID <=", value, "joAssessorId");
            return (Criteria) this;
        }

        public Criteria andJoAssessorIdLike(String value) {
            addCriterion("JO_ASSESSOR_ID like", value, "joAssessorId");
            return (Criteria) this;
        }

        public Criteria andJoAssessorIdNotLike(String value) {
            addCriterion("JO_ASSESSOR_ID not like", value, "joAssessorId");
            return (Criteria) this;
        }

        public Criteria andJoAssessorIdIn(List<String> values) {
            addCriterion("JO_ASSESSOR_ID in", values, "joAssessorId");
            return (Criteria) this;
        }

        public Criteria andJoAssessorIdNotIn(List<String> values) {
            addCriterion("JO_ASSESSOR_ID not in", values, "joAssessorId");
            return (Criteria) this;
        }

        public Criteria andJoAssessorIdBetween(String value1, String value2) {
            addCriterion("JO_ASSESSOR_ID between", value1, value2, "joAssessorId");
            return (Criteria) this;
        }

        public Criteria andJoAssessorIdNotBetween(String value1, String value2) {
            addCriterion("JO_ASSESSOR_ID not between", value1, value2, "joAssessorId");
            return (Criteria) this;
        }

        public Criteria andJoAssessContentIsNull() {
            addCriterion("JO_ASSESS_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andJoAssessContentIsNotNull() {
            addCriterion("JO_ASSESS_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andJoAssessContentEqualTo(String value) {
            addCriterion("JO_ASSESS_CONTENT =", value, "joAssessContent");
            return (Criteria) this;
        }

        public Criteria andJoAssessContentNotEqualTo(String value) {
            addCriterion("JO_ASSESS_CONTENT <>", value, "joAssessContent");
            return (Criteria) this;
        }

        public Criteria andJoAssessContentGreaterThan(String value) {
            addCriterion("JO_ASSESS_CONTENT >", value, "joAssessContent");
            return (Criteria) this;
        }

        public Criteria andJoAssessContentGreaterThanOrEqualTo(String value) {
            addCriterion("JO_ASSESS_CONTENT >=", value, "joAssessContent");
            return (Criteria) this;
        }

        public Criteria andJoAssessContentLessThan(String value) {
            addCriterion("JO_ASSESS_CONTENT <", value, "joAssessContent");
            return (Criteria) this;
        }

        public Criteria andJoAssessContentLessThanOrEqualTo(String value) {
            addCriterion("JO_ASSESS_CONTENT <=", value, "joAssessContent");
            return (Criteria) this;
        }

        public Criteria andJoAssessContentLike(String value) {
            addCriterion("JO_ASSESS_CONTENT like", value, "joAssessContent");
            return (Criteria) this;
        }

        public Criteria andJoAssessContentNotLike(String value) {
            addCriterion("JO_ASSESS_CONTENT not like", value, "joAssessContent");
            return (Criteria) this;
        }

        public Criteria andJoAssessContentIn(List<String> values) {
            addCriterion("JO_ASSESS_CONTENT in", values, "joAssessContent");
            return (Criteria) this;
        }

        public Criteria andJoAssessContentNotIn(List<String> values) {
            addCriterion("JO_ASSESS_CONTENT not in", values, "joAssessContent");
            return (Criteria) this;
        }

        public Criteria andJoAssessContentBetween(String value1, String value2) {
            addCriterion("JO_ASSESS_CONTENT between", value1, value2, "joAssessContent");
            return (Criteria) this;
        }

        public Criteria andJoAssessContentNotBetween(String value1, String value2) {
            addCriterion("JO_ASSESS_CONTENT not between", value1, value2, "joAssessContent");
            return (Criteria) this;
        }

        public Criteria andJoAssessTimeIsNull() {
            addCriterion("JO_ASSESS_TIME is null");
            return (Criteria) this;
        }

        public Criteria andJoAssessTimeIsNotNull() {
            addCriterion("JO_ASSESS_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andJoAssessTimeEqualTo(Date value) {
            addCriterion("JO_ASSESS_TIME =", value, "joAssessTime");
            return (Criteria) this;
        }

        public Criteria andJoAssessTimeNotEqualTo(Date value) {
            addCriterion("JO_ASSESS_TIME <>", value, "joAssessTime");
            return (Criteria) this;
        }

        public Criteria andJoAssessTimeGreaterThan(Date value) {
            addCriterion("JO_ASSESS_TIME >", value, "joAssessTime");
            return (Criteria) this;
        }

        public Criteria andJoAssessTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("JO_ASSESS_TIME >=", value, "joAssessTime");
            return (Criteria) this;
        }

        public Criteria andJoAssessTimeLessThan(Date value) {
            addCriterion("JO_ASSESS_TIME <", value, "joAssessTime");
            return (Criteria) this;
        }

        public Criteria andJoAssessTimeLessThanOrEqualTo(Date value) {
            addCriterion("JO_ASSESS_TIME <=", value, "joAssessTime");
            return (Criteria) this;
        }

        public Criteria andJoAssessTimeIn(List<Date> values) {
            addCriterion("JO_ASSESS_TIME in", values, "joAssessTime");
            return (Criteria) this;
        }

        public Criteria andJoAssessTimeNotIn(List<Date> values) {
            addCriterion("JO_ASSESS_TIME not in", values, "joAssessTime");
            return (Criteria) this;
        }

        public Criteria andJoAssessTimeBetween(Date value1, Date value2) {
            addCriterion("JO_ASSESS_TIME between", value1, value2, "joAssessTime");
            return (Criteria) this;
        }

        public Criteria andJoAssessTimeNotBetween(Date value1, Date value2) {
            addCriterion("JO_ASSESS_TIME not between", value1, value2, "joAssessTime");
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