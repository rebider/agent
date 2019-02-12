package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.util.ArrayList;
import java.util.List;

public class PosHuddleRewardDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PosHuddleRewardDetailExample() {
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

        public Criteria andPosAgentIdIsNull() {
            addCriterion("POS_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdIsNotNull() {
            addCriterion("POS_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdEqualTo(String value) {
            addCriterion("POS_AGENT_ID =", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdNotEqualTo(String value) {
            addCriterion("POS_AGENT_ID <>", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdGreaterThan(String value) {
            addCriterion("POS_AGENT_ID >", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("POS_AGENT_ID >=", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdLessThan(String value) {
            addCriterion("POS_AGENT_ID <", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdLessThanOrEqualTo(String value) {
            addCriterion("POS_AGENT_ID <=", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdLike(String value) {
            addCriterion("POS_AGENT_ID like", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdNotLike(String value) {
            addCriterion("POS_AGENT_ID not like", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdIn(List<String> values) {
            addCriterion("POS_AGENT_ID in", values, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdNotIn(List<String> values) {
            addCriterion("POS_AGENT_ID not in", values, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdBetween(String value1, String value2) {
            addCriterion("POS_AGENT_ID between", value1, value2, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdNotBetween(String value1, String value2) {
            addCriterion("POS_AGENT_ID not between", value1, value2, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameIsNull() {
            addCriterion("POS_AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameIsNotNull() {
            addCriterion("POS_AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameEqualTo(String value) {
            addCriterion("POS_AGENT_NAME =", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameNotEqualTo(String value) {
            addCriterion("POS_AGENT_NAME <>", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameGreaterThan(String value) {
            addCriterion("POS_AGENT_NAME >", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("POS_AGENT_NAME >=", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameLessThan(String value) {
            addCriterion("POS_AGENT_NAME <", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameLessThanOrEqualTo(String value) {
            addCriterion("POS_AGENT_NAME <=", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameLike(String value) {
            addCriterion("POS_AGENT_NAME like", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameNotLike(String value) {
            addCriterion("POS_AGENT_NAME not like", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameIn(List<String> values) {
            addCriterion("POS_AGENT_NAME in", values, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameNotIn(List<String> values) {
            addCriterion("POS_AGENT_NAME not in", values, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameBetween(String value1, String value2) {
            addCriterion("POS_AGENT_NAME between", value1, value2, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameNotBetween(String value1, String value2) {
            addCriterion("POS_AGENT_NAME not between", value1, value2, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andHuddleCodeIsNull() {
            addCriterion("HUDDLE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andHuddleCodeIsNotNull() {
            addCriterion("HUDDLE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andHuddleCodeEqualTo(String value) {
            addCriterion("HUDDLE_CODE =", value, "huddleCode");
            return (Criteria) this;
        }

        public Criteria andHuddleCodeNotEqualTo(String value) {
            addCriterion("HUDDLE_CODE <>", value, "huddleCode");
            return (Criteria) this;
        }

        public Criteria andHuddleCodeGreaterThan(String value) {
            addCriterion("HUDDLE_CODE >", value, "huddleCode");
            return (Criteria) this;
        }

        public Criteria andHuddleCodeGreaterThanOrEqualTo(String value) {
            addCriterion("HUDDLE_CODE >=", value, "huddleCode");
            return (Criteria) this;
        }

        public Criteria andHuddleCodeLessThan(String value) {
            addCriterion("HUDDLE_CODE <", value, "huddleCode");
            return (Criteria) this;
        }

        public Criteria andHuddleCodeLessThanOrEqualTo(String value) {
            addCriterion("HUDDLE_CODE <=", value, "huddleCode");
            return (Criteria) this;
        }

        public Criteria andHuddleCodeLike(String value) {
            addCriterion("HUDDLE_CODE like", value, "huddleCode");
            return (Criteria) this;
        }

        public Criteria andHuddleCodeNotLike(String value) {
            addCriterion("HUDDLE_CODE not like", value, "huddleCode");
            return (Criteria) this;
        }

        public Criteria andHuddleCodeIn(List<String> values) {
            addCriterion("HUDDLE_CODE in", values, "huddleCode");
            return (Criteria) this;
        }

        public Criteria andHuddleCodeNotIn(List<String> values) {
            addCriterion("HUDDLE_CODE not in", values, "huddleCode");
            return (Criteria) this;
        }

        public Criteria andHuddleCodeBetween(String value1, String value2) {
            addCriterion("HUDDLE_CODE between", value1, value2, "huddleCode");
            return (Criteria) this;
        }

        public Criteria andHuddleCodeNotBetween(String value1, String value2) {
            addCriterion("HUDDLE_CODE not between", value1, value2, "huddleCode");
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