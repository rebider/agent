package com.ryx.credit.activity.entity;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ActIdUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ActIdUserExample() {
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
            addCriterion("ID_ is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID_ is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Object value) {
            addCriterion("ID_ =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Object value) {
            addCriterion("ID_ <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Object value) {
            addCriterion("ID_ >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Object value) {
            addCriterion("ID_ >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Object value) {
            addCriterion("ID_ <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Object value) {
            addCriterion("ID_ <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Object> values) {
            addCriterion("ID_ in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Object> values) {
            addCriterion("ID_ not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Object value1, Object value2) {
            addCriterion("ID_ between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Object value1, Object value2) {
            addCriterion("ID_ not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRevIsNull() {
            addCriterion("REV_ is null");
            return (Criteria) this;
        }

        public Criteria andRevIsNotNull() {
            addCriterion("REV_ is not null");
            return (Criteria) this;
        }

        public Criteria andRevEqualTo(BigDecimal value) {
            addCriterion("REV_ =", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevNotEqualTo(BigDecimal value) {
            addCriterion("REV_ <>", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevGreaterThan(BigDecimal value) {
            addCriterion("REV_ >", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REV_ >=", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevLessThan(BigDecimal value) {
            addCriterion("REV_ <", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REV_ <=", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevIn(List<BigDecimal> values) {
            addCriterion("REV_ in", values, "rev");
            return (Criteria) this;
        }

        public Criteria andRevNotIn(List<BigDecimal> values) {
            addCriterion("REV_ not in", values, "rev");
            return (Criteria) this;
        }

        public Criteria andRevBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REV_ between", value1, value2, "rev");
            return (Criteria) this;
        }

        public Criteria andRevNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REV_ not between", value1, value2, "rev");
            return (Criteria) this;
        }

        public Criteria andFirstIsNull() {
            addCriterion("FIRST_ is null");
            return (Criteria) this;
        }

        public Criteria andFirstIsNotNull() {
            addCriterion("FIRST_ is not null");
            return (Criteria) this;
        }

        public Criteria andFirstEqualTo(Object value) {
            addCriterion("FIRST_ =", value, "first");
            return (Criteria) this;
        }

        public Criteria andFirstNotEqualTo(Object value) {
            addCriterion("FIRST_ <>", value, "first");
            return (Criteria) this;
        }

        public Criteria andFirstGreaterThan(Object value) {
            addCriterion("FIRST_ >", value, "first");
            return (Criteria) this;
        }

        public Criteria andFirstGreaterThanOrEqualTo(Object value) {
            addCriterion("FIRST_ >=", value, "first");
            return (Criteria) this;
        }

        public Criteria andFirstLessThan(Object value) {
            addCriterion("FIRST_ <", value, "first");
            return (Criteria) this;
        }

        public Criteria andFirstLessThanOrEqualTo(Object value) {
            addCriterion("FIRST_ <=", value, "first");
            return (Criteria) this;
        }

        public Criteria andFirstIn(List<Object> values) {
            addCriterion("FIRST_ in", values, "first");
            return (Criteria) this;
        }

        public Criteria andFirstNotIn(List<Object> values) {
            addCriterion("FIRST_ not in", values, "first");
            return (Criteria) this;
        }

        public Criteria andFirstBetween(Object value1, Object value2) {
            addCriterion("FIRST_ between", value1, value2, "first");
            return (Criteria) this;
        }

        public Criteria andFirstNotBetween(Object value1, Object value2) {
            addCriterion("FIRST_ not between", value1, value2, "first");
            return (Criteria) this;
        }

        public Criteria andLastIsNull() {
            addCriterion("LAST_ is null");
            return (Criteria) this;
        }

        public Criteria andLastIsNotNull() {
            addCriterion("LAST_ is not null");
            return (Criteria) this;
        }

        public Criteria andLastEqualTo(Object value) {
            addCriterion("LAST_ =", value, "last");
            return (Criteria) this;
        }

        public Criteria andLastNotEqualTo(Object value) {
            addCriterion("LAST_ <>", value, "last");
            return (Criteria) this;
        }

        public Criteria andLastGreaterThan(Object value) {
            addCriterion("LAST_ >", value, "last");
            return (Criteria) this;
        }

        public Criteria andLastGreaterThanOrEqualTo(Object value) {
            addCriterion("LAST_ >=", value, "last");
            return (Criteria) this;
        }

        public Criteria andLastLessThan(Object value) {
            addCriterion("LAST_ <", value, "last");
            return (Criteria) this;
        }

        public Criteria andLastLessThanOrEqualTo(Object value) {
            addCriterion("LAST_ <=", value, "last");
            return (Criteria) this;
        }

        public Criteria andLastIn(List<Object> values) {
            addCriterion("LAST_ in", values, "last");
            return (Criteria) this;
        }

        public Criteria andLastNotIn(List<Object> values) {
            addCriterion("LAST_ not in", values, "last");
            return (Criteria) this;
        }

        public Criteria andLastBetween(Object value1, Object value2) {
            addCriterion("LAST_ between", value1, value2, "last");
            return (Criteria) this;
        }

        public Criteria andLastNotBetween(Object value1, Object value2) {
            addCriterion("LAST_ not between", value1, value2, "last");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("EMAIL_ is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("EMAIL_ is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(Object value) {
            addCriterion("EMAIL_ =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(Object value) {
            addCriterion("EMAIL_ <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(Object value) {
            addCriterion("EMAIL_ >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(Object value) {
            addCriterion("EMAIL_ >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(Object value) {
            addCriterion("EMAIL_ <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(Object value) {
            addCriterion("EMAIL_ <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<Object> values) {
            addCriterion("EMAIL_ in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<Object> values) {
            addCriterion("EMAIL_ not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(Object value1, Object value2) {
            addCriterion("EMAIL_ between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(Object value1, Object value2) {
            addCriterion("EMAIL_ not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andPwdIsNull() {
            addCriterion("PWD_ is null");
            return (Criteria) this;
        }

        public Criteria andPwdIsNotNull() {
            addCriterion("PWD_ is not null");
            return (Criteria) this;
        }

        public Criteria andPwdEqualTo(Object value) {
            addCriterion("PWD_ =", value, "pwd");
            return (Criteria) this;
        }

        public Criteria andPwdNotEqualTo(Object value) {
            addCriterion("PWD_ <>", value, "pwd");
            return (Criteria) this;
        }

        public Criteria andPwdGreaterThan(Object value) {
            addCriterion("PWD_ >", value, "pwd");
            return (Criteria) this;
        }

        public Criteria andPwdGreaterThanOrEqualTo(Object value) {
            addCriterion("PWD_ >=", value, "pwd");
            return (Criteria) this;
        }

        public Criteria andPwdLessThan(Object value) {
            addCriterion("PWD_ <", value, "pwd");
            return (Criteria) this;
        }

        public Criteria andPwdLessThanOrEqualTo(Object value) {
            addCriterion("PWD_ <=", value, "pwd");
            return (Criteria) this;
        }

        public Criteria andPwdIn(List<Object> values) {
            addCriterion("PWD_ in", values, "pwd");
            return (Criteria) this;
        }

        public Criteria andPwdNotIn(List<Object> values) {
            addCriterion("PWD_ not in", values, "pwd");
            return (Criteria) this;
        }

        public Criteria andPwdBetween(Object value1, Object value2) {
            addCriterion("PWD_ between", value1, value2, "pwd");
            return (Criteria) this;
        }

        public Criteria andPwdNotBetween(Object value1, Object value2) {
            addCriterion("PWD_ not between", value1, value2, "pwd");
            return (Criteria) this;
        }

        public Criteria andPictureIdIsNull() {
            addCriterion("PICTURE_ID_ is null");
            return (Criteria) this;
        }

        public Criteria andPictureIdIsNotNull() {
            addCriterion("PICTURE_ID_ is not null");
            return (Criteria) this;
        }

        public Criteria andPictureIdEqualTo(Object value) {
            addCriterion("PICTURE_ID_ =", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdNotEqualTo(Object value) {
            addCriterion("PICTURE_ID_ <>", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdGreaterThan(Object value) {
            addCriterion("PICTURE_ID_ >", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdGreaterThanOrEqualTo(Object value) {
            addCriterion("PICTURE_ID_ >=", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdLessThan(Object value) {
            addCriterion("PICTURE_ID_ <", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdLessThanOrEqualTo(Object value) {
            addCriterion("PICTURE_ID_ <=", value, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdIn(List<Object> values) {
            addCriterion("PICTURE_ID_ in", values, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdNotIn(List<Object> values) {
            addCriterion("PICTURE_ID_ not in", values, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdBetween(Object value1, Object value2) {
            addCriterion("PICTURE_ID_ between", value1, value2, "pictureId");
            return (Criteria) this;
        }

        public Criteria andPictureIdNotBetween(Object value1, Object value2) {
            addCriterion("PICTURE_ID_ not between", value1, value2, "pictureId");
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