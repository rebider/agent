package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BuckleRunExample implements Serializable{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public BuckleRunExample() {
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

        public Criteria andBearAgentIdIsNull() {
            addCriterion("BEAR_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andBearAgentIdIsNotNull() {
            addCriterion("BEAR_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBearAgentIdEqualTo(String value) {
            addCriterion("BEAR_AGENT_ID =", value, "bearAgentId");
            return (Criteria) this;
        }

        public Criteria andBearAgentIdNotEqualTo(String value) {
            addCriterion("BEAR_AGENT_ID <>", value, "bearAgentId");
            return (Criteria) this;
        }

        public Criteria andBearAgentIdGreaterThan(String value) {
            addCriterion("BEAR_AGENT_ID >", value, "bearAgentId");
            return (Criteria) this;
        }

        public Criteria andBearAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("BEAR_AGENT_ID >=", value, "bearAgentId");
            return (Criteria) this;
        }

        public Criteria andBearAgentIdLessThan(String value) {
            addCriterion("BEAR_AGENT_ID <", value, "bearAgentId");
            return (Criteria) this;
        }

        public Criteria andBearAgentIdLessThanOrEqualTo(String value) {
            addCriterion("BEAR_AGENT_ID <=", value, "bearAgentId");
            return (Criteria) this;
        }

        public Criteria andBearAgentIdLike(String value) {
            addCriterion("BEAR_AGENT_ID like", value, "bearAgentId");
            return (Criteria) this;
        }

        public Criteria andBearAgentIdNotLike(String value) {
            addCriterion("BEAR_AGENT_ID not like", value, "bearAgentId");
            return (Criteria) this;
        }

        public Criteria andBearAgentIdIn(List<String> values) {
            addCriterion("BEAR_AGENT_ID in", values, "bearAgentId");
            return (Criteria) this;
        }

        public Criteria andBearAgentIdNotIn(List<String> values) {
            addCriterion("BEAR_AGENT_ID not in", values, "bearAgentId");
            return (Criteria) this;
        }

        public Criteria andBearAgentIdBetween(String value1, String value2) {
            addCriterion("BEAR_AGENT_ID between", value1, value2, "bearAgentId");
            return (Criteria) this;
        }

        public Criteria andBearAgentIdNotBetween(String value1, String value2) {
            addCriterion("BEAR_AGENT_ID not between", value1, value2, "bearAgentId");
            return (Criteria) this;
        }

        public Criteria andBearAgentPidIsNull() {
            addCriterion("BEAR_AGENT_PID is null");
            return (Criteria) this;
        }

        public Criteria andBearAgentPidIsNotNull() {
            addCriterion("BEAR_AGENT_PID is not null");
            return (Criteria) this;
        }

        public Criteria andBearAgentPidEqualTo(String value) {
            addCriterion("BEAR_AGENT_PID =", value, "bearAgentPid");
            return (Criteria) this;
        }

        public Criteria andBearAgentPidNotEqualTo(String value) {
            addCriterion("BEAR_AGENT_PID <>", value, "bearAgentPid");
            return (Criteria) this;
        }

        public Criteria andBearAgentPidGreaterThan(String value) {
            addCriterion("BEAR_AGENT_PID >", value, "bearAgentPid");
            return (Criteria) this;
        }

        public Criteria andBearAgentPidGreaterThanOrEqualTo(String value) {
            addCriterion("BEAR_AGENT_PID >=", value, "bearAgentPid");
            return (Criteria) this;
        }

        public Criteria andBearAgentPidLessThan(String value) {
            addCriterion("BEAR_AGENT_PID <", value, "bearAgentPid");
            return (Criteria) this;
        }

        public Criteria andBearAgentPidLessThanOrEqualTo(String value) {
            addCriterion("BEAR_AGENT_PID <=", value, "bearAgentPid");
            return (Criteria) this;
        }

        public Criteria andBearAgentPidLike(String value) {
            addCriterion("BEAR_AGENT_PID like", value, "bearAgentPid");
            return (Criteria) this;
        }

        public Criteria andBearAgentPidNotLike(String value) {
            addCriterion("BEAR_AGENT_PID not like", value, "bearAgentPid");
            return (Criteria) this;
        }

        public Criteria andBearAgentPidIn(List<String> values) {
            addCriterion("BEAR_AGENT_PID in", values, "bearAgentPid");
            return (Criteria) this;
        }

        public Criteria andBearAgentPidNotIn(List<String> values) {
            addCriterion("BEAR_AGENT_PID not in", values, "bearAgentPid");
            return (Criteria) this;
        }

        public Criteria andBearAgentPidBetween(String value1, String value2) {
            addCriterion("BEAR_AGENT_PID between", value1, value2, "bearAgentPid");
            return (Criteria) this;
        }

        public Criteria andBearAgentPidNotBetween(String value1, String value2) {
            addCriterion("BEAR_AGENT_PID not between", value1, value2, "bearAgentPid");
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

        public Criteria andRunLevelIsNull() {
            addCriterion("RUN_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andRunLevelIsNotNull() {
            addCriterion("RUN_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andRunLevelEqualTo(String value) {
            addCriterion("RUN_LEVEL =", value, "runLevel");
            return (Criteria) this;
        }

        public Criteria andRunLevelNotEqualTo(String value) {
            addCriterion("RUN_LEVEL <>", value, "runLevel");
            return (Criteria) this;
        }

        public Criteria andRunLevelGreaterThan(String value) {
            addCriterion("RUN_LEVEL >", value, "runLevel");
            return (Criteria) this;
        }

        public Criteria andRunLevelGreaterThanOrEqualTo(String value) {
            addCriterion("RUN_LEVEL >=", value, "runLevel");
            return (Criteria) this;
        }

        public Criteria andRunLevelLessThan(String value) {
            addCriterion("RUN_LEVEL <", value, "runLevel");
            return (Criteria) this;
        }

        public Criteria andRunLevelLessThanOrEqualTo(String value) {
            addCriterion("RUN_LEVEL <=", value, "runLevel");
            return (Criteria) this;
        }

        public Criteria andRunLevelLike(String value) {
            addCriterion("RUN_LEVEL like", value, "runLevel");
            return (Criteria) this;
        }

        public Criteria andRunLevelNotLike(String value) {
            addCriterion("RUN_LEVEL not like", value, "runLevel");
            return (Criteria) this;
        }

        public Criteria andRunLevelIn(List<String> values) {
            addCriterion("RUN_LEVEL in", values, "runLevel");
            return (Criteria) this;
        }

        public Criteria andRunLevelNotIn(List<String> values) {
            addCriterion("RUN_LEVEL not in", values, "runLevel");
            return (Criteria) this;
        }

        public Criteria andRunLevelBetween(String value1, String value2) {
            addCriterion("RUN_LEVEL between", value1, value2, "runLevel");
            return (Criteria) this;
        }

        public Criteria andRunLevelNotBetween(String value1, String value2) {
            addCriterion("RUN_LEVEL not between", value1, value2, "runLevel");
            return (Criteria) this;
        }

        public Criteria andRunAmtIsNull() {
            addCriterion("RUN_AMT is null");
            return (Criteria) this;
        }

        public Criteria andRunAmtIsNotNull() {
            addCriterion("RUN_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andRunAmtEqualTo(BigDecimal value) {
            addCriterion("RUN_AMT =", value, "runAmt");
            return (Criteria) this;
        }

        public Criteria andRunAmtNotEqualTo(BigDecimal value) {
            addCriterion("RUN_AMT <>", value, "runAmt");
            return (Criteria) this;
        }

        public Criteria andRunAmtGreaterThan(BigDecimal value) {
            addCriterion("RUN_AMT >", value, "runAmt");
            return (Criteria) this;
        }

        public Criteria andRunAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RUN_AMT >=", value, "runAmt");
            return (Criteria) this;
        }

        public Criteria andRunAmtLessThan(BigDecimal value) {
            addCriterion("RUN_AMT <", value, "runAmt");
            return (Criteria) this;
        }

        public Criteria andRunAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RUN_AMT <=", value, "runAmt");
            return (Criteria) this;
        }

        public Criteria andRunAmtIn(List<BigDecimal> values) {
            addCriterion("RUN_AMT in", values, "runAmt");
            return (Criteria) this;
        }

        public Criteria andRunAmtNotIn(List<BigDecimal> values) {
            addCriterion("RUN_AMT not in", values, "runAmt");
            return (Criteria) this;
        }

        public Criteria andRunAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RUN_AMT between", value1, value2, "runAmt");
            return (Criteria) this;
        }

        public Criteria andRunAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RUN_AMT not between", value1, value2, "runAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyAmtIsNull() {
            addCriterion("SUPPLY_AMT is null");
            return (Criteria) this;
        }

        public Criteria andSupplyAmtIsNotNull() {
            addCriterion("SUPPLY_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyAmtEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_AMT =", value, "supplyAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyAmtNotEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_AMT <>", value, "supplyAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyAmtGreaterThan(BigDecimal value) {
            addCriterion("SUPPLY_AMT >", value, "supplyAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_AMT >=", value, "supplyAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyAmtLessThan(BigDecimal value) {
            addCriterion("SUPPLY_AMT <", value, "supplyAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_AMT <=", value, "supplyAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyAmtIn(List<BigDecimal> values) {
            addCriterion("SUPPLY_AMT in", values, "supplyAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyAmtNotIn(List<BigDecimal> values) {
            addCriterion("SUPPLY_AMT not in", values, "supplyAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPPLY_AMT between", value1, value2, "supplyAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPPLY_AMT not between", value1, value2, "supplyAmt");
            return (Criteria) this;
        }

        public Criteria andRunDateIsNull() {
            addCriterion("RUN_DATE is null");
            return (Criteria) this;
        }

        public Criteria andRunDateIsNotNull() {
            addCriterion("RUN_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andRunDateEqualTo(String value) {
            addCriterion("RUN_DATE =", value, "runDate");
            return (Criteria) this;
        }

        public Criteria andRunDateNotEqualTo(String value) {
            addCriterion("RUN_DATE <>", value, "runDate");
            return (Criteria) this;
        }

        public Criteria andRunDateGreaterThan(String value) {
            addCriterion("RUN_DATE >", value, "runDate");
            return (Criteria) this;
        }

        public Criteria andRunDateGreaterThanOrEqualTo(String value) {
            addCriterion("RUN_DATE >=", value, "runDate");
            return (Criteria) this;
        }

        public Criteria andRunDateLessThan(String value) {
            addCriterion("RUN_DATE <", value, "runDate");
            return (Criteria) this;
        }

        public Criteria andRunDateLessThanOrEqualTo(String value) {
            addCriterion("RUN_DATE <=", value, "runDate");
            return (Criteria) this;
        }

        public Criteria andRunDateLike(String value) {
            addCriterion("RUN_DATE like", value, "runDate");
            return (Criteria) this;
        }

        public Criteria andRunDateNotLike(String value) {
            addCriterion("RUN_DATE not like", value, "runDate");
            return (Criteria) this;
        }

        public Criteria andRunDateIn(List<String> values) {
            addCriterion("RUN_DATE in", values, "runDate");
            return (Criteria) this;
        }

        public Criteria andRunDateNotIn(List<String> values) {
            addCriterion("RUN_DATE not in", values, "runDate");
            return (Criteria) this;
        }

        public Criteria andRunDateBetween(String value1, String value2) {
            addCriterion("RUN_DATE between", value1, value2, "runDate");
            return (Criteria) this;
        }

        public Criteria andRunDateNotBetween(String value1, String value2) {
            addCriterion("RUN_DATE not between", value1, value2, "runDate");
            return (Criteria) this;
        }

        public Criteria andRunStatusIsNull() {
            addCriterion("RUN_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andRunStatusIsNotNull() {
            addCriterion("RUN_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andRunStatusEqualTo(String value) {
            addCriterion("RUN_STATUS =", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusNotEqualTo(String value) {
            addCriterion("RUN_STATUS <>", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusGreaterThan(String value) {
            addCriterion("RUN_STATUS >", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusGreaterThanOrEqualTo(String value) {
            addCriterion("RUN_STATUS >=", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusLessThan(String value) {
            addCriterion("RUN_STATUS <", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusLessThanOrEqualTo(String value) {
            addCriterion("RUN_STATUS <=", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusLike(String value) {
            addCriterion("RUN_STATUS like", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusNotLike(String value) {
            addCriterion("RUN_STATUS not like", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusIn(List<String> values) {
            addCriterion("RUN_STATUS in", values, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusNotIn(List<String> values) {
            addCriterion("RUN_STATUS not in", values, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusBetween(String value1, String value2) {
            addCriterion("RUN_STATUS between", value1, value2, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusNotBetween(String value1, String value2) {
            addCriterion("RUN_STATUS not between", value1, value2, "runStatus");
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