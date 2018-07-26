package com.ryx.credit.activity.entity;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProfitSupplyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ProfitSupplyExample() {
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

        public Criteria andAgentNameIsNull() {
            addCriterion("AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAgentNameIsNotNull() {
            addCriterion("AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAgentNameEqualTo(String value) {
            addCriterion("AGENT_NAME =", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotEqualTo(String value) {
            addCriterion("AGENT_NAME <>", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThan(String value) {
            addCriterion("AGENT_NAME >", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_NAME >=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThan(String value) {
            addCriterion("AGENT_NAME <", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThanOrEqualTo(String value) {
            addCriterion("AGENT_NAME <=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLike(String value) {
            addCriterion("AGENT_NAME like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotLike(String value) {
            addCriterion("AGENT_NAME not like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameIn(List<String> values) {
            addCriterion("AGENT_NAME in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotIn(List<String> values) {
            addCriterion("AGENT_NAME not in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameBetween(String value1, String value2) {
            addCriterion("AGENT_NAME between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotBetween(String value1, String value2) {
            addCriterion("AGENT_NAME not between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeIsNull() {
            addCriterion("SUPPLY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeIsNotNull() {
            addCriterion("SUPPLY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeEqualTo(String value) {
            addCriterion("SUPPLY_TYPE =", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeNotEqualTo(String value) {
            addCriterion("SUPPLY_TYPE <>", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeGreaterThan(String value) {
            addCriterion("SUPPLY_TYPE >", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeGreaterThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TYPE >=", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeLessThan(String value) {
            addCriterion("SUPPLY_TYPE <", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeLessThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TYPE <=", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeLike(String value) {
            addCriterion("SUPPLY_TYPE like", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeNotLike(String value) {
            addCriterion("SUPPLY_TYPE not like", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeIn(List<String> values) {
            addCriterion("SUPPLY_TYPE in", values, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeNotIn(List<String> values) {
            addCriterion("SUPPLY_TYPE not in", values, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeBetween(String value1, String value2) {
            addCriterion("SUPPLY_TYPE between", value1, value2, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeNotBetween(String value1, String value2) {
            addCriterion("SUPPLY_TYPE not between", value1, value2, "supplyType");
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

        public Criteria andSupplyDateIsNull() {
            addCriterion("SUPPLY_DATE is null");
            return (Criteria) this;
        }

        public Criteria andSupplyDateIsNotNull() {
            addCriterion("SUPPLY_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyDateEqualTo(String value) {
            addCriterion("SUPPLY_DATE =", value, "supplyDate");
            return (Criteria) this;
        }

        public Criteria andSupplyDateNotEqualTo(String value) {
            addCriterion("SUPPLY_DATE <>", value, "supplyDate");
            return (Criteria) this;
        }

        public Criteria andSupplyDateGreaterThan(String value) {
            addCriterion("SUPPLY_DATE >", value, "supplyDate");
            return (Criteria) this;
        }

        public Criteria andSupplyDateGreaterThanOrEqualTo(String value) {
            addCriterion("SUPPLY_DATE >=", value, "supplyDate");
            return (Criteria) this;
        }

        public Criteria andSupplyDateLessThan(String value) {
            addCriterion("SUPPLY_DATE <", value, "supplyDate");
            return (Criteria) this;
        }

        public Criteria andSupplyDateLessThanOrEqualTo(String value) {
            addCriterion("SUPPLY_DATE <=", value, "supplyDate");
            return (Criteria) this;
        }

        public Criteria andSupplyDateLike(String value) {
            addCriterion("SUPPLY_DATE like", value, "supplyDate");
            return (Criteria) this;
        }

        public Criteria andSupplyDateNotLike(String value) {
            addCriterion("SUPPLY_DATE not like", value, "supplyDate");
            return (Criteria) this;
        }

        public Criteria andSupplyDateIn(List<String> values) {
            addCriterion("SUPPLY_DATE in", values, "supplyDate");
            return (Criteria) this;
        }

        public Criteria andSupplyDateNotIn(List<String> values) {
            addCriterion("SUPPLY_DATE not in", values, "supplyDate");
            return (Criteria) this;
        }

        public Criteria andSupplyDateBetween(String value1, String value2) {
            addCriterion("SUPPLY_DATE between", value1, value2, "supplyDate");
            return (Criteria) this;
        }

        public Criteria andSupplyDateNotBetween(String value1, String value2) {
            addCriterion("SUPPLY_DATE not between", value1, value2, "supplyDate");
            return (Criteria) this;
        }

        public Criteria andRemerkIsNull() {
            addCriterion("REMERK is null");
            return (Criteria) this;
        }

        public Criteria andRemerkIsNotNull() {
            addCriterion("REMERK is not null");
            return (Criteria) this;
        }

        public Criteria andRemerkEqualTo(String value) {
            addCriterion("REMERK =", value, "remerk");
            return (Criteria) this;
        }

        public Criteria andRemerkNotEqualTo(String value) {
            addCriterion("REMERK <>", value, "remerk");
            return (Criteria) this;
        }

        public Criteria andRemerkGreaterThan(String value) {
            addCriterion("REMERK >", value, "remerk");
            return (Criteria) this;
        }

        public Criteria andRemerkGreaterThanOrEqualTo(String value) {
            addCriterion("REMERK >=", value, "remerk");
            return (Criteria) this;
        }

        public Criteria andRemerkLessThan(String value) {
            addCriterion("REMERK <", value, "remerk");
            return (Criteria) this;
        }

        public Criteria andRemerkLessThanOrEqualTo(String value) {
            addCriterion("REMERK <=", value, "remerk");
            return (Criteria) this;
        }

        public Criteria andRemerkLike(String value) {
            addCriterion("REMERK like", value, "remerk");
            return (Criteria) this;
        }

        public Criteria andRemerkNotLike(String value) {
            addCriterion("REMERK not like", value, "remerk");
            return (Criteria) this;
        }

        public Criteria andRemerkIn(List<String> values) {
            addCriterion("REMERK in", values, "remerk");
            return (Criteria) this;
        }

        public Criteria andRemerkNotIn(List<String> values) {
            addCriterion("REMERK not in", values, "remerk");
            return (Criteria) this;
        }

        public Criteria andRemerkBetween(String value1, String value2) {
            addCriterion("REMERK between", value1, value2, "remerk");
            return (Criteria) this;
        }

        public Criteria andRemerkNotBetween(String value1, String value2) {
            addCriterion("REMERK not between", value1, value2, "remerk");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNull() {
            addCriterion("SOURCE_ID is null");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNotNull() {
            addCriterion("SOURCE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSourceIdEqualTo(String value) {
            addCriterion("SOURCE_ID =", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotEqualTo(String value) {
            addCriterion("SOURCE_ID <>", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThan(String value) {
            addCriterion("SOURCE_ID >", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThanOrEqualTo(String value) {
            addCriterion("SOURCE_ID >=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThan(String value) {
            addCriterion("SOURCE_ID <", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThanOrEqualTo(String value) {
            addCriterion("SOURCE_ID <=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLike(String value) {
            addCriterion("SOURCE_ID like", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotLike(String value) {
            addCriterion("SOURCE_ID not like", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdIn(List<String> values) {
            addCriterion("SOURCE_ID in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotIn(List<String> values) {
            addCriterion("SOURCE_ID not in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdBetween(String value1, String value2) {
            addCriterion("SOURCE_ID between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotBetween(String value1, String value2) {
            addCriterion("SOURCE_ID not between", value1, value2, "sourceId");
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