package com.ryx.credit.pojo.admin.bank;

import com.ryx.credit.common.util.Page;

import java.util.ArrayList;
import java.util.List;

public class BankRegionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public BankRegionExample() {
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

        public Criteria andBRegionIsNull() {
            addCriterion("B_REGION is null");
            return (Criteria) this;
        }

        public Criteria andBRegionIsNotNull() {
            addCriterion("B_REGION is not null");
            return (Criteria) this;
        }

        public Criteria andBRegionEqualTo(String value) {
            addCriterion("B_REGION =", value, "bRegion");
            return (Criteria) this;
        }

        public Criteria andBRegionNotEqualTo(String value) {
            addCriterion("B_REGION <>", value, "bRegion");
            return (Criteria) this;
        }

        public Criteria andBRegionGreaterThan(String value) {
            addCriterion("B_REGION >", value, "bRegion");
            return (Criteria) this;
        }

        public Criteria andBRegionGreaterThanOrEqualTo(String value) {
            addCriterion("B_REGION >=", value, "bRegion");
            return (Criteria) this;
        }

        public Criteria andBRegionLessThan(String value) {
            addCriterion("B_REGION <", value, "bRegion");
            return (Criteria) this;
        }

        public Criteria andBRegionLessThanOrEqualTo(String value) {
            addCriterion("B_REGION <=", value, "bRegion");
            return (Criteria) this;
        }

        public Criteria andBRegionLike(String value) {
            addCriterion("B_REGION like", value, "bRegion");
            return (Criteria) this;
        }

        public Criteria andBRegionNotLike(String value) {
            addCriterion("B_REGION not like", value, "bRegion");
            return (Criteria) this;
        }

        public Criteria andBRegionIn(List<String> values) {
            addCriterion("B_REGION in", values, "bRegion");
            return (Criteria) this;
        }

        public Criteria andBRegionNotIn(List<String> values) {
            addCriterion("B_REGION not in", values, "bRegion");
            return (Criteria) this;
        }

        public Criteria andBRegionBetween(String value1, String value2) {
            addCriterion("B_REGION between", value1, value2, "bRegion");
            return (Criteria) this;
        }

        public Criteria andBRegionNotBetween(String value1, String value2) {
            addCriterion("B_REGION not between", value1, value2, "bRegion");
            return (Criteria) this;
        }

        public Criteria andBLevelIsNull() {
            addCriterion("B_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andBLevelIsNotNull() {
            addCriterion("B_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andBLevelEqualTo(String value) {
            addCriterion("B_LEVEL =", value, "bLevel");
            return (Criteria) this;
        }

        public Criteria andBLevelNotEqualTo(String value) {
            addCriterion("B_LEVEL <>", value, "bLevel");
            return (Criteria) this;
        }

        public Criteria andBLevelGreaterThan(String value) {
            addCriterion("B_LEVEL >", value, "bLevel");
            return (Criteria) this;
        }

        public Criteria andBLevelGreaterThanOrEqualTo(String value) {
            addCriterion("B_LEVEL >=", value, "bLevel");
            return (Criteria) this;
        }

        public Criteria andBLevelLessThan(String value) {
            addCriterion("B_LEVEL <", value, "bLevel");
            return (Criteria) this;
        }

        public Criteria andBLevelLessThanOrEqualTo(String value) {
            addCriterion("B_LEVEL <=", value, "bLevel");
            return (Criteria) this;
        }

        public Criteria andBLevelLike(String value) {
            addCriterion("B_LEVEL like", value, "bLevel");
            return (Criteria) this;
        }

        public Criteria andBLevelNotLike(String value) {
            addCriterion("B_LEVEL not like", value, "bLevel");
            return (Criteria) this;
        }

        public Criteria andBLevelIn(List<String> values) {
            addCriterion("B_LEVEL in", values, "bLevel");
            return (Criteria) this;
        }

        public Criteria andBLevelNotIn(List<String> values) {
            addCriterion("B_LEVEL not in", values, "bLevel");
            return (Criteria) this;
        }

        public Criteria andBLevelBetween(String value1, String value2) {
            addCriterion("B_LEVEL between", value1, value2, "bLevel");
            return (Criteria) this;
        }

        public Criteria andBLevelNotBetween(String value1, String value2) {
            addCriterion("B_LEVEL not between", value1, value2, "bLevel");
            return (Criteria) this;
        }

        public Criteria andBCityIsNull() {
            addCriterion("B_CITY is null");
            return (Criteria) this;
        }

        public Criteria andBCityIsNotNull() {
            addCriterion("B_CITY is not null");
            return (Criteria) this;
        }

        public Criteria andBCityEqualTo(String value) {
            addCriterion("B_CITY =", value, "bCity");
            return (Criteria) this;
        }

        public Criteria andBCityNotEqualTo(String value) {
            addCriterion("B_CITY <>", value, "bCity");
            return (Criteria) this;
        }

        public Criteria andBCityGreaterThan(String value) {
            addCriterion("B_CITY >", value, "bCity");
            return (Criteria) this;
        }

        public Criteria andBCityGreaterThanOrEqualTo(String value) {
            addCriterion("B_CITY >=", value, "bCity");
            return (Criteria) this;
        }

        public Criteria andBCityLessThan(String value) {
            addCriterion("B_CITY <", value, "bCity");
            return (Criteria) this;
        }

        public Criteria andBCityLessThanOrEqualTo(String value) {
            addCriterion("B_CITY <=", value, "bCity");
            return (Criteria) this;
        }

        public Criteria andBCityLike(String value) {
            addCriterion("B_CITY like", value, "bCity");
            return (Criteria) this;
        }

        public Criteria andBCityNotLike(String value) {
            addCriterion("B_CITY not like", value, "bCity");
            return (Criteria) this;
        }

        public Criteria andBCityIn(List<String> values) {
            addCriterion("B_CITY in", values, "bCity");
            return (Criteria) this;
        }

        public Criteria andBCityNotIn(List<String> values) {
            addCriterion("B_CITY not in", values, "bCity");
            return (Criteria) this;
        }

        public Criteria andBCityBetween(String value1, String value2) {
            addCriterion("B_CITY between", value1, value2, "bCity");
            return (Criteria) this;
        }

        public Criteria andBCityNotBetween(String value1, String value2) {
            addCriterion("B_CITY not between", value1, value2, "bCity");
            return (Criteria) this;
        }

        public Criteria andBProvinceIsNull() {
            addCriterion("B_PROVINCE is null");
            return (Criteria) this;
        }

        public Criteria andBProvinceIsNotNull() {
            addCriterion("B_PROVINCE is not null");
            return (Criteria) this;
        }

        public Criteria andBProvinceEqualTo(String value) {
            addCriterion("B_PROVINCE =", value, "bProvince");
            return (Criteria) this;
        }

        public Criteria andBProvinceNotEqualTo(String value) {
            addCriterion("B_PROVINCE <>", value, "bProvince");
            return (Criteria) this;
        }

        public Criteria andBProvinceGreaterThan(String value) {
            addCriterion("B_PROVINCE >", value, "bProvince");
            return (Criteria) this;
        }

        public Criteria andBProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("B_PROVINCE >=", value, "bProvince");
            return (Criteria) this;
        }

        public Criteria andBProvinceLessThan(String value) {
            addCriterion("B_PROVINCE <", value, "bProvince");
            return (Criteria) this;
        }

        public Criteria andBProvinceLessThanOrEqualTo(String value) {
            addCriterion("B_PROVINCE <=", value, "bProvince");
            return (Criteria) this;
        }

        public Criteria andBProvinceLike(String value) {
            addCriterion("B_PROVINCE like", value, "bProvince");
            return (Criteria) this;
        }

        public Criteria andBProvinceNotLike(String value) {
            addCriterion("B_PROVINCE not like", value, "bProvince");
            return (Criteria) this;
        }

        public Criteria andBProvinceIn(List<String> values) {
            addCriterion("B_PROVINCE in", values, "bProvince");
            return (Criteria) this;
        }

        public Criteria andBProvinceNotIn(List<String> values) {
            addCriterion("B_PROVINCE not in", values, "bProvince");
            return (Criteria) this;
        }

        public Criteria andBProvinceBetween(String value1, String value2) {
            addCriterion("B_PROVINCE between", value1, value2, "bProvince");
            return (Criteria) this;
        }

        public Criteria andBProvinceNotBetween(String value1, String value2) {
            addCriterion("B_PROVINCE not between", value1, value2, "bProvince");
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