package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrganTranMonthDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OrganTranMonthDetailExample() {
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

        public Criteria andZPosTranAmtIsNull() {
            addCriterion("Z_POS_TRAN_AMT is null");
            return (Criteria) this;
        }

        public Criteria andZPosTranAmtIsNotNull() {
            addCriterion("Z_POS_TRAN_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andZPosTranAmtEqualTo(BigDecimal value) {
            addCriterion("Z_POS_TRAN_AMT =", value, "zPosTranAmt");
            return (Criteria) this;
        }

        public Criteria andZPosTranAmtNotEqualTo(BigDecimal value) {
            addCriterion("Z_POS_TRAN_AMT <>", value, "zPosTranAmt");
            return (Criteria) this;
        }

        public Criteria andZPosTranAmtGreaterThan(BigDecimal value) {
            addCriterion("Z_POS_TRAN_AMT >", value, "zPosTranAmt");
            return (Criteria) this;
        }

        public Criteria andZPosTranAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Z_POS_TRAN_AMT >=", value, "zPosTranAmt");
            return (Criteria) this;
        }

        public Criteria andZPosTranAmtLessThan(BigDecimal value) {
            addCriterion("Z_POS_TRAN_AMT <", value, "zPosTranAmt");
            return (Criteria) this;
        }

        public Criteria andZPosTranAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Z_POS_TRAN_AMT <=", value, "zPosTranAmt");
            return (Criteria) this;
        }

        public Criteria andZPosTranAmtIn(List<BigDecimal> values) {
            addCriterion("Z_POS_TRAN_AMT in", values, "zPosTranAmt");
            return (Criteria) this;
        }

        public Criteria andZPosTranAmtNotIn(List<BigDecimal> values) {
            addCriterion("Z_POS_TRAN_AMT not in", values, "zPosTranAmt");
            return (Criteria) this;
        }

        public Criteria andZPosTranAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Z_POS_TRAN_AMT between", value1, value2, "zPosTranAmt");
            return (Criteria) this;
        }

        public Criteria andZPosTranAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Z_POS_TRAN_AMT not between", value1, value2, "zPosTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosTranAmtIsNull() {
            addCriterion("POS_TRAN_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPosTranAmtIsNotNull() {
            addCriterion("POS_TRAN_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPosTranAmtEqualTo(BigDecimal value) {
            addCriterion("POS_TRAN_AMT =", value, "posTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosTranAmtNotEqualTo(BigDecimal value) {
            addCriterion("POS_TRAN_AMT <>", value, "posTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosTranAmtGreaterThan(BigDecimal value) {
            addCriterion("POS_TRAN_AMT >", value, "posTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosTranAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_TRAN_AMT >=", value, "posTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosTranAmtLessThan(BigDecimal value) {
            addCriterion("POS_TRAN_AMT <", value, "posTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosTranAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_TRAN_AMT <=", value, "posTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosTranAmtIn(List<BigDecimal> values) {
            addCriterion("POS_TRAN_AMT in", values, "posTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosTranAmtNotIn(List<BigDecimal> values) {
            addCriterion("POS_TRAN_AMT not in", values, "posTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosTranAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_TRAN_AMT between", value1, value2, "posTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosTranAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_TRAN_AMT not between", value1, value2, "posTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosJlTranAmtIsNull() {
            addCriterion("POS_JL_TRAN_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPosJlTranAmtIsNotNull() {
            addCriterion("POS_JL_TRAN_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPosJlTranAmtEqualTo(BigDecimal value) {
            addCriterion("POS_JL_TRAN_AMT =", value, "posJlTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosJlTranAmtNotEqualTo(BigDecimal value) {
            addCriterion("POS_JL_TRAN_AMT <>", value, "posJlTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosJlTranAmtGreaterThan(BigDecimal value) {
            addCriterion("POS_JL_TRAN_AMT >", value, "posJlTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosJlTranAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_JL_TRAN_AMT >=", value, "posJlTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosJlTranAmtLessThan(BigDecimal value) {
            addCriterion("POS_JL_TRAN_AMT <", value, "posJlTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosJlTranAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_JL_TRAN_AMT <=", value, "posJlTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosJlTranAmtIn(List<BigDecimal> values) {
            addCriterion("POS_JL_TRAN_AMT in", values, "posJlTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosJlTranAmtNotIn(List<BigDecimal> values) {
            addCriterion("POS_JL_TRAN_AMT not in", values, "posJlTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosJlTranAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_JL_TRAN_AMT between", value1, value2, "posJlTranAmt");
            return (Criteria) this;
        }

        public Criteria andPosJlTranAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_JL_TRAN_AMT not between", value1, value2, "posJlTranAmt");
            return (Criteria) this;
        }

        public Criteria andProfitIdIsNull() {
            addCriterion("PROFIT_ID is null");
            return (Criteria) this;
        }

        public Criteria andProfitIdIsNotNull() {
            addCriterion("PROFIT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProfitIdEqualTo(String value) {
            addCriterion("PROFIT_ID =", value, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdNotEqualTo(String value) {
            addCriterion("PROFIT_ID <>", value, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdGreaterThan(String value) {
            addCriterion("PROFIT_ID >", value, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdGreaterThanOrEqualTo(String value) {
            addCriterion("PROFIT_ID >=", value, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdLessThan(String value) {
            addCriterion("PROFIT_ID <", value, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdLessThanOrEqualTo(String value) {
            addCriterion("PROFIT_ID <=", value, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdLike(String value) {
            addCriterion("PROFIT_ID like", value, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdNotLike(String value) {
            addCriterion("PROFIT_ID not like", value, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdIn(List<String> values) {
            addCriterion("PROFIT_ID in", values, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdNotIn(List<String> values) {
            addCriterion("PROFIT_ID not in", values, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdBetween(String value1, String value2) {
            addCriterion("PROFIT_ID between", value1, value2, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdNotBetween(String value1, String value2) {
            addCriterion("PROFIT_ID not between", value1, value2, "profitId");
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