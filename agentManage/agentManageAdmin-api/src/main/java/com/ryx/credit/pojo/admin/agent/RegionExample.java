package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RegionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public RegionExample() {
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

        public Criteria andIdEqualTo(BigDecimal value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(BigDecimal value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(BigDecimal value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(BigDecimal value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<BigDecimal> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<BigDecimal> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRNameIsNull() {
            addCriterion("R_NAME is null");
            return (Criteria) this;
        }

        public Criteria andRNameIsNotNull() {
            addCriterion("R_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andRNameEqualTo(String value) {
            addCriterion("R_NAME =", value, "rName");
            return (Criteria) this;
        }

        public Criteria andRNameNotEqualTo(String value) {
            addCriterion("R_NAME <>", value, "rName");
            return (Criteria) this;
        }

        public Criteria andRNameGreaterThan(String value) {
            addCriterion("R_NAME >", value, "rName");
            return (Criteria) this;
        }

        public Criteria andRNameGreaterThanOrEqualTo(String value) {
            addCriterion("R_NAME >=", value, "rName");
            return (Criteria) this;
        }

        public Criteria andRNameLessThan(String value) {
            addCriterion("R_NAME <", value, "rName");
            return (Criteria) this;
        }

        public Criteria andRNameLessThanOrEqualTo(String value) {
            addCriterion("R_NAME <=", value, "rName");
            return (Criteria) this;
        }

        public Criteria andRNameLike(String value) {
            addCriterion("R_NAME like", value, "rName");
            return (Criteria) this;
        }

        public Criteria andRNameNotLike(String value) {
            addCriterion("R_NAME not like", value, "rName");
            return (Criteria) this;
        }

        public Criteria andRNameIn(List<String> values) {
            addCriterion("R_NAME in", values, "rName");
            return (Criteria) this;
        }

        public Criteria andRNameNotIn(List<String> values) {
            addCriterion("R_NAME not in", values, "rName");
            return (Criteria) this;
        }

        public Criteria andRNameBetween(String value1, String value2) {
            addCriterion("R_NAME between", value1, value2, "rName");
            return (Criteria) this;
        }

        public Criteria andRNameNotBetween(String value1, String value2) {
            addCriterion("R_NAME not between", value1, value2, "rName");
            return (Criteria) this;
        }

        public Criteria andRCodeIsNull() {
            addCriterion("R_CODE is null");
            return (Criteria) this;
        }

        public Criteria andRCodeIsNotNull() {
            addCriterion("R_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andRCodeEqualTo(String value) {
            addCriterion("R_CODE =", value, "rCode");
            return (Criteria) this;
        }

        public Criteria andRCodeNotEqualTo(String value) {
            addCriterion("R_CODE <>", value, "rCode");
            return (Criteria) this;
        }

        public Criteria andRCodeGreaterThan(String value) {
            addCriterion("R_CODE >", value, "rCode");
            return (Criteria) this;
        }

        public Criteria andRCodeGreaterThanOrEqualTo(String value) {
            addCriterion("R_CODE >=", value, "rCode");
            return (Criteria) this;
        }

        public Criteria andRCodeLessThan(String value) {
            addCriterion("R_CODE <", value, "rCode");
            return (Criteria) this;
        }

        public Criteria andRCodeLessThanOrEqualTo(String value) {
            addCriterion("R_CODE <=", value, "rCode");
            return (Criteria) this;
        }

        public Criteria andRCodeLike(String value) {
            addCriterion("R_CODE like", value, "rCode");
            return (Criteria) this;
        }

        public Criteria andRCodeNotLike(String value) {
            addCriterion("R_CODE not like", value, "rCode");
            return (Criteria) this;
        }

        public Criteria andRCodeIn(List<String> values) {
            addCriterion("R_CODE in", values, "rCode");
            return (Criteria) this;
        }

        public Criteria andRCodeNotIn(List<String> values) {
            addCriterion("R_CODE not in", values, "rCode");
            return (Criteria) this;
        }

        public Criteria andRCodeBetween(String value1, String value2) {
            addCriterion("R_CODE between", value1, value2, "rCode");
            return (Criteria) this;
        }

        public Criteria andRCodeNotBetween(String value1, String value2) {
            addCriterion("R_CODE not between", value1, value2, "rCode");
            return (Criteria) this;
        }

        public Criteria andPCodeIsNull() {
            addCriterion("P_CODE is null");
            return (Criteria) this;
        }

        public Criteria andPCodeIsNotNull() {
            addCriterion("P_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andPCodeEqualTo(String value) {
            addCriterion("P_CODE =", value, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeNotEqualTo(String value) {
            addCriterion("P_CODE <>", value, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeGreaterThan(String value) {
            addCriterion("P_CODE >", value, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeGreaterThanOrEqualTo(String value) {
            addCriterion("P_CODE >=", value, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeLessThan(String value) {
            addCriterion("P_CODE <", value, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeLessThanOrEqualTo(String value) {
            addCriterion("P_CODE <=", value, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeLike(String value) {
            addCriterion("P_CODE like", value, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeNotLike(String value) {
            addCriterion("P_CODE not like", value, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeIn(List<String> values) {
            addCriterion("P_CODE in", values, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeNotIn(List<String> values) {
            addCriterion("P_CODE not in", values, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeBetween(String value1, String value2) {
            addCriterion("P_CODE between", value1, value2, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeNotBetween(String value1, String value2) {
            addCriterion("P_CODE not between", value1, value2, "pCode");
            return (Criteria) this;
        }

        public Criteria andTTypeIsNull() {
            addCriterion("T_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTTypeIsNotNull() {
            addCriterion("T_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTTypeEqualTo(BigDecimal value) {
            addCriterion("T_TYPE =", value, "tType");
            return (Criteria) this;
        }

        public Criteria andTTypeNotEqualTo(BigDecimal value) {
            addCriterion("T_TYPE <>", value, "tType");
            return (Criteria) this;
        }

        public Criteria andTTypeGreaterThan(BigDecimal value) {
            addCriterion("T_TYPE >", value, "tType");
            return (Criteria) this;
        }

        public Criteria andTTypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("T_TYPE >=", value, "tType");
            return (Criteria) this;
        }

        public Criteria andTTypeLessThan(BigDecimal value) {
            addCriterion("T_TYPE <", value, "tType");
            return (Criteria) this;
        }

        public Criteria andTTypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("T_TYPE <=", value, "tType");
            return (Criteria) this;
        }

        public Criteria andTTypeIn(List<BigDecimal> values) {
            addCriterion("T_TYPE in", values, "tType");
            return (Criteria) this;
        }

        public Criteria andTTypeNotIn(List<BigDecimal> values) {
            addCriterion("T_TYPE not in", values, "tType");
            return (Criteria) this;
        }

        public Criteria andTTypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("T_TYPE between", value1, value2, "tType");
            return (Criteria) this;
        }

        public Criteria andTTypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("T_TYPE not between", value1, value2, "tType");
            return (Criteria) this;
        }

        public Criteria andRSortIsNull() {
            addCriterion("R_SORT is null");
            return (Criteria) this;
        }

        public Criteria andRSortIsNotNull() {
            addCriterion("R_SORT is not null");
            return (Criteria) this;
        }

        public Criteria andRSortEqualTo(BigDecimal value) {
            addCriterion("R_SORT =", value, "rSort");
            return (Criteria) this;
        }

        public Criteria andRSortNotEqualTo(BigDecimal value) {
            addCriterion("R_SORT <>", value, "rSort");
            return (Criteria) this;
        }

        public Criteria andRSortGreaterThan(BigDecimal value) {
            addCriterion("R_SORT >", value, "rSort");
            return (Criteria) this;
        }

        public Criteria andRSortGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("R_SORT >=", value, "rSort");
            return (Criteria) this;
        }

        public Criteria andRSortLessThan(BigDecimal value) {
            addCriterion("R_SORT <", value, "rSort");
            return (Criteria) this;
        }

        public Criteria andRSortLessThanOrEqualTo(BigDecimal value) {
            addCriterion("R_SORT <=", value, "rSort");
            return (Criteria) this;
        }

        public Criteria andRSortIn(List<BigDecimal> values) {
            addCriterion("R_SORT in", values, "rSort");
            return (Criteria) this;
        }

        public Criteria andRSortNotIn(List<BigDecimal> values) {
            addCriterion("R_SORT not in", values, "rSort");
            return (Criteria) this;
        }

        public Criteria andRSortBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("R_SORT between", value1, value2, "rSort");
            return (Criteria) this;
        }

        public Criteria andRSortNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("R_SORT not between", value1, value2, "rSort");
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