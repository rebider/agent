package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProfitAdjustExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ProfitAdjustExample() {
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

        public Criteria andAdjustTypeIsNull() {
            addCriterion("ADJUST_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeIsNotNull() {
            addCriterion("ADJUST_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeEqualTo(String value) {
            addCriterion("ADJUST_TYPE =", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeNotEqualTo(String value) {
            addCriterion("ADJUST_TYPE <>", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeGreaterThan(String value) {
            addCriterion("ADJUST_TYPE >", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_TYPE >=", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeLessThan(String value) {
            addCriterion("ADJUST_TYPE <", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_TYPE <=", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeLike(String value) {
            addCriterion("ADJUST_TYPE like", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeNotLike(String value) {
            addCriterion("ADJUST_TYPE not like", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeIn(List<String> values) {
            addCriterion("ADJUST_TYPE in", values, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeNotIn(List<String> values) {
            addCriterion("ADJUST_TYPE not in", values, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeBetween(String value1, String value2) {
            addCriterion("ADJUST_TYPE between", value1, value2, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeNotBetween(String value1, String value2) {
            addCriterion("ADJUST_TYPE not between", value1, value2, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtIsNull() {
            addCriterion("ADJUST_AMT is null");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtIsNotNull() {
            addCriterion("ADJUST_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtEqualTo(BigDecimal value) {
            addCriterion("ADJUST_AMT =", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtNotEqualTo(BigDecimal value) {
            addCriterion("ADJUST_AMT <>", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtGreaterThan(BigDecimal value) {
            addCriterion("ADJUST_AMT >", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ADJUST_AMT >=", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtLessThan(BigDecimal value) {
            addCriterion("ADJUST_AMT <", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ADJUST_AMT <=", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtIn(List<BigDecimal> values) {
            addCriterion("ADJUST_AMT in", values, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtNotIn(List<BigDecimal> values) {
            addCriterion("ADJUST_AMT not in", values, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADJUST_AMT between", value1, value2, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADJUST_AMT not between", value1, value2, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustUserIsNull() {
            addCriterion("ADJUST_USER is null");
            return (Criteria) this;
        }

        public Criteria andAdjustUserIsNotNull() {
            addCriterion("ADJUST_USER is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustUserEqualTo(String value) {
            addCriterion("ADJUST_USER =", value, "adjustUser");
            return (Criteria) this;
        }

        public Criteria andAdjustUserNotEqualTo(String value) {
            addCriterion("ADJUST_USER <>", value, "adjustUser");
            return (Criteria) this;
        }

        public Criteria andAdjustUserGreaterThan(String value) {
            addCriterion("ADJUST_USER >", value, "adjustUser");
            return (Criteria) this;
        }

        public Criteria andAdjustUserGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_USER >=", value, "adjustUser");
            return (Criteria) this;
        }

        public Criteria andAdjustUserLessThan(String value) {
            addCriterion("ADJUST_USER <", value, "adjustUser");
            return (Criteria) this;
        }

        public Criteria andAdjustUserLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_USER <=", value, "adjustUser");
            return (Criteria) this;
        }

        public Criteria andAdjustUserLike(String value) {
            addCriterion("ADJUST_USER like", value, "adjustUser");
            return (Criteria) this;
        }

        public Criteria andAdjustUserNotLike(String value) {
            addCriterion("ADJUST_USER not like", value, "adjustUser");
            return (Criteria) this;
        }

        public Criteria andAdjustUserIn(List<String> values) {
            addCriterion("ADJUST_USER in", values, "adjustUser");
            return (Criteria) this;
        }

        public Criteria andAdjustUserNotIn(List<String> values) {
            addCriterion("ADJUST_USER not in", values, "adjustUser");
            return (Criteria) this;
        }

        public Criteria andAdjustUserBetween(String value1, String value2) {
            addCriterion("ADJUST_USER between", value1, value2, "adjustUser");
            return (Criteria) this;
        }

        public Criteria andAdjustUserNotBetween(String value1, String value2) {
            addCriterion("ADJUST_USER not between", value1, value2, "adjustUser");
            return (Criteria) this;
        }

        public Criteria andAdjustInfoIsNull() {
            addCriterion("ADJUST_INFO is null");
            return (Criteria) this;
        }

        public Criteria andAdjustInfoIsNotNull() {
            addCriterion("ADJUST_INFO is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustInfoEqualTo(String value) {
            addCriterion("ADJUST_INFO =", value, "adjustInfo");
            return (Criteria) this;
        }

        public Criteria andAdjustInfoNotEqualTo(String value) {
            addCriterion("ADJUST_INFO <>", value, "adjustInfo");
            return (Criteria) this;
        }

        public Criteria andAdjustInfoGreaterThan(String value) {
            addCriterion("ADJUST_INFO >", value, "adjustInfo");
            return (Criteria) this;
        }

        public Criteria andAdjustInfoGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_INFO >=", value, "adjustInfo");
            return (Criteria) this;
        }

        public Criteria andAdjustInfoLessThan(String value) {
            addCriterion("ADJUST_INFO <", value, "adjustInfo");
            return (Criteria) this;
        }

        public Criteria andAdjustInfoLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_INFO <=", value, "adjustInfo");
            return (Criteria) this;
        }

        public Criteria andAdjustInfoLike(String value) {
            addCriterion("ADJUST_INFO like", value, "adjustInfo");
            return (Criteria) this;
        }

        public Criteria andAdjustInfoNotLike(String value) {
            addCriterion("ADJUST_INFO not like", value, "adjustInfo");
            return (Criteria) this;
        }

        public Criteria andAdjustInfoIn(List<String> values) {
            addCriterion("ADJUST_INFO in", values, "adjustInfo");
            return (Criteria) this;
        }

        public Criteria andAdjustInfoNotIn(List<String> values) {
            addCriterion("ADJUST_INFO not in", values, "adjustInfo");
            return (Criteria) this;
        }

        public Criteria andAdjustInfoBetween(String value1, String value2) {
            addCriterion("ADJUST_INFO between", value1, value2, "adjustInfo");
            return (Criteria) this;
        }

        public Criteria andAdjustInfoNotBetween(String value1, String value2) {
            addCriterion("ADJUST_INFO not between", value1, value2, "adjustInfo");
            return (Criteria) this;
        }

        public Criteria andAdjustDateIsNull() {
            addCriterion("ADJUST_DATE is null");
            return (Criteria) this;
        }

        public Criteria andAdjustDateIsNotNull() {
            addCriterion("ADJUST_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustDateEqualTo(String value) {
            addCriterion("ADJUST_DATE =", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateNotEqualTo(String value) {
            addCriterion("ADJUST_DATE <>", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateGreaterThan(String value) {
            addCriterion("ADJUST_DATE >", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_DATE >=", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateLessThan(String value) {
            addCriterion("ADJUST_DATE <", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_DATE <=", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateLike(String value) {
            addCriterion("ADJUST_DATE like", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateNotLike(String value) {
            addCriterion("ADJUST_DATE not like", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateIn(List<String> values) {
            addCriterion("ADJUST_DATE in", values, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateNotIn(List<String> values) {
            addCriterion("ADJUST_DATE not in", values, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateBetween(String value1, String value2) {
            addCriterion("ADJUST_DATE between", value1, value2, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateNotBetween(String value1, String value2) {
            addCriterion("ADJUST_DATE not between", value1, value2, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNull() {
            addCriterion("AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNotNull() {
            addCriterion("AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentIdEqualTo(String value) {
            addCriterion("AGENT_ID =", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotEqualTo(String value) {
            addCriterion("AGENT_ID <>", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThan(String value) {
            addCriterion("AGENT_ID >", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_ID >=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThan(String value) {
            addCriterion("AGENT_ID <", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThanOrEqualTo(String value) {
            addCriterion("AGENT_ID <=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLike(String value) {
            addCriterion("AGENT_ID like", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotLike(String value) {
            addCriterion("AGENT_ID not like", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdIn(List<String> values) {
            addCriterion("AGENT_ID in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotIn(List<String> values) {
            addCriterion("AGENT_ID not in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdBetween(String value1, String value2) {
            addCriterion("AGENT_ID between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotBetween(String value1, String value2) {
            addCriterion("AGENT_ID not between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentPidIsNull() {
            addCriterion("AGENT_PID is null");
            return (Criteria) this;
        }

        public Criteria andAgentPidIsNotNull() {
            addCriterion("AGENT_PID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentPidEqualTo(String value) {
            addCriterion("AGENT_PID =", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotEqualTo(String value) {
            addCriterion("AGENT_PID <>", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidGreaterThan(String value) {
            addCriterion("AGENT_PID >", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_PID >=", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLessThan(String value) {
            addCriterion("AGENT_PID <", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLessThanOrEqualTo(String value) {
            addCriterion("AGENT_PID <=", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLike(String value) {
            addCriterion("AGENT_PID like", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotLike(String value) {
            addCriterion("AGENT_PID not like", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidIn(List<String> values) {
            addCriterion("AGENT_PID in", values, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotIn(List<String> values) {
            addCriterion("AGENT_PID not in", values, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidBetween(String value1, String value2) {
            addCriterion("AGENT_PID between", value1, value2, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotBetween(String value1, String value2) {
            addCriterion("AGENT_PID not between", value1, value2, "agentPid");
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