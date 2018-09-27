package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.util.ArrayList;
import java.util.List;

public class PAgentMergeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PAgentMergeExample() {
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

        public Criteria andMainAgentIdIsNull() {
            addCriterion("MAIN_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdIsNotNull() {
            addCriterion("MAIN_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdEqualTo(String value) {
            addCriterion("MAIN_AGENT_ID =", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdNotEqualTo(String value) {
            addCriterion("MAIN_AGENT_ID <>", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdGreaterThan(String value) {
            addCriterion("MAIN_AGENT_ID >", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("MAIN_AGENT_ID >=", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdLessThan(String value) {
            addCriterion("MAIN_AGENT_ID <", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdLessThanOrEqualTo(String value) {
            addCriterion("MAIN_AGENT_ID <=", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdLike(String value) {
            addCriterion("MAIN_AGENT_ID like", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdNotLike(String value) {
            addCriterion("MAIN_AGENT_ID not like", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdIn(List<String> values) {
            addCriterion("MAIN_AGENT_ID in", values, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdNotIn(List<String> values) {
            addCriterion("MAIN_AGENT_ID not in", values, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdBetween(String value1, String value2) {
            addCriterion("MAIN_AGENT_ID between", value1, value2, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdNotBetween(String value1, String value2) {
            addCriterion("MAIN_AGENT_ID not between", value1, value2, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdIsNull() {
            addCriterion("SUB_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdIsNotNull() {
            addCriterion("SUB_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdEqualTo(String value) {
            addCriterion("SUB_AGENT_ID =", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdNotEqualTo(String value) {
            addCriterion("SUB_AGENT_ID <>", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdGreaterThan(String value) {
            addCriterion("SUB_AGENT_ID >", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUB_AGENT_ID >=", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdLessThan(String value) {
            addCriterion("SUB_AGENT_ID <", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdLessThanOrEqualTo(String value) {
            addCriterion("SUB_AGENT_ID <=", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdLike(String value) {
            addCriterion("SUB_AGENT_ID like", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdNotLike(String value) {
            addCriterion("SUB_AGENT_ID not like", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdIn(List<String> values) {
            addCriterion("SUB_AGENT_ID in", values, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdNotIn(List<String> values) {
            addCriterion("SUB_AGENT_ID not in", values, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdBetween(String value1, String value2) {
            addCriterion("SUB_AGENT_ID between", value1, value2, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdNotBetween(String value1, String value2) {
            addCriterion("SUB_AGENT_ID not between", value1, value2, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andMergeDateIsNull() {
            addCriterion("MERGE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andMergeDateIsNotNull() {
            addCriterion("MERGE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andMergeDateEqualTo(String value) {
            addCriterion("MERGE_DATE =", value, "mergeDate");
            return (Criteria) this;
        }

        public Criteria andMergeDateNotEqualTo(String value) {
            addCriterion("MERGE_DATE <>", value, "mergeDate");
            return (Criteria) this;
        }

        public Criteria andMergeDateGreaterThan(String value) {
            addCriterion("MERGE_DATE >", value, "mergeDate");
            return (Criteria) this;
        }

        public Criteria andMergeDateGreaterThanOrEqualTo(String value) {
            addCriterion("MERGE_DATE >=", value, "mergeDate");
            return (Criteria) this;
        }

        public Criteria andMergeDateLessThan(String value) {
            addCriterion("MERGE_DATE <", value, "mergeDate");
            return (Criteria) this;
        }

        public Criteria andMergeDateLessThanOrEqualTo(String value) {
            addCriterion("MERGE_DATE <=", value, "mergeDate");
            return (Criteria) this;
        }

        public Criteria andMergeDateLike(String value) {
            addCriterion("MERGE_DATE like", value, "mergeDate");
            return (Criteria) this;
        }

        public Criteria andMergeDateNotLike(String value) {
            addCriterion("MERGE_DATE not like", value, "mergeDate");
            return (Criteria) this;
        }

        public Criteria andMergeDateIn(List<String> values) {
            addCriterion("MERGE_DATE in", values, "mergeDate");
            return (Criteria) this;
        }

        public Criteria andMergeDateNotIn(List<String> values) {
            addCriterion("MERGE_DATE not in", values, "mergeDate");
            return (Criteria) this;
        }

        public Criteria andMergeDateBetween(String value1, String value2) {
            addCriterion("MERGE_DATE between", value1, value2, "mergeDate");
            return (Criteria) this;
        }

        public Criteria andMergeDateNotBetween(String value1, String value2) {
            addCriterion("MERGE_DATE not between", value1, value2, "mergeDate");
            return (Criteria) this;
        }

        public Criteria andMergeStatusIsNull() {
            addCriterion("MERGE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andMergeStatusIsNotNull() {
            addCriterion("MERGE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andMergeStatusEqualTo(String value) {
            addCriterion("MERGE_STATUS =", value, "mergeStatus");
            return (Criteria) this;
        }

        public Criteria andMergeStatusNotEqualTo(String value) {
            addCriterion("MERGE_STATUS <>", value, "mergeStatus");
            return (Criteria) this;
        }

        public Criteria andMergeStatusGreaterThan(String value) {
            addCriterion("MERGE_STATUS >", value, "mergeStatus");
            return (Criteria) this;
        }

        public Criteria andMergeStatusGreaterThanOrEqualTo(String value) {
            addCriterion("MERGE_STATUS >=", value, "mergeStatus");
            return (Criteria) this;
        }

        public Criteria andMergeStatusLessThan(String value) {
            addCriterion("MERGE_STATUS <", value, "mergeStatus");
            return (Criteria) this;
        }

        public Criteria andMergeStatusLessThanOrEqualTo(String value) {
            addCriterion("MERGE_STATUS <=", value, "mergeStatus");
            return (Criteria) this;
        }

        public Criteria andMergeStatusLike(String value) {
            addCriterion("MERGE_STATUS like", value, "mergeStatus");
            return (Criteria) this;
        }

        public Criteria andMergeStatusNotLike(String value) {
            addCriterion("MERGE_STATUS not like", value, "mergeStatus");
            return (Criteria) this;
        }

        public Criteria andMergeStatusIn(List<String> values) {
            addCriterion("MERGE_STATUS in", values, "mergeStatus");
            return (Criteria) this;
        }

        public Criteria andMergeStatusNotIn(List<String> values) {
            addCriterion("MERGE_STATUS not in", values, "mergeStatus");
            return (Criteria) this;
        }

        public Criteria andMergeStatusBetween(String value1, String value2) {
            addCriterion("MERGE_STATUS between", value1, value2, "mergeStatus");
            return (Criteria) this;
        }

        public Criteria andMergeStatusNotBetween(String value1, String value2) {
            addCriterion("MERGE_STATUS not between", value1, value2, "mergeStatus");
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