package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProfitDayExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ProfitDayExample() {
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

        public Criteria andTransDateIsNull() {
            addCriterion("TRANS_DATE is null");
            return (Criteria) this;
        }

        public Criteria andTransDateIsNotNull() {
            addCriterion("TRANS_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andTransDateEqualTo(String value) {
            addCriterion("TRANS_DATE =", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateNotEqualTo(String value) {
            addCriterion("TRANS_DATE <>", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateGreaterThan(String value) {
            addCriterion("TRANS_DATE >", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateGreaterThanOrEqualTo(String value) {
            addCriterion("TRANS_DATE >=", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateLessThan(String value) {
            addCriterion("TRANS_DATE <", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateLessThanOrEqualTo(String value) {
            addCriterion("TRANS_DATE <=", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateLike(String value) {
            addCriterion("TRANS_DATE like", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateNotLike(String value) {
            addCriterion("TRANS_DATE not like", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateIn(List<String> values) {
            addCriterion("TRANS_DATE in", values, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateNotIn(List<String> values) {
            addCriterion("TRANS_DATE not in", values, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateBetween(String value1, String value2) {
            addCriterion("TRANS_DATE between", value1, value2, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateNotBetween(String value1, String value2) {
            addCriterion("TRANS_DATE not between", value1, value2, "transDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateIsNull() {
            addCriterion("REMIT_DATE is null");
            return (Criteria) this;
        }

        public Criteria andRemitDateIsNotNull() {
            addCriterion("REMIT_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andRemitDateEqualTo(String value) {
            addCriterion("REMIT_DATE =", value, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateNotEqualTo(String value) {
            addCriterion("REMIT_DATE <>", value, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateGreaterThan(String value) {
            addCriterion("REMIT_DATE >", value, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateGreaterThanOrEqualTo(String value) {
            addCriterion("REMIT_DATE >=", value, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateLessThan(String value) {
            addCriterion("REMIT_DATE <", value, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateLessThanOrEqualTo(String value) {
            addCriterion("REMIT_DATE <=", value, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateLike(String value) {
            addCriterion("REMIT_DATE like", value, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateNotLike(String value) {
            addCriterion("REMIT_DATE not like", value, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateIn(List<String> values) {
            addCriterion("REMIT_DATE in", values, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateNotIn(List<String> values) {
            addCriterion("REMIT_DATE not in", values, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateBetween(String value1, String value2) {
            addCriterion("REMIT_DATE between", value1, value2, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateNotBetween(String value1, String value2) {
            addCriterion("REMIT_DATE not between", value1, value2, "remitDate");
            return (Criteria) this;
        }

        public Criteria andTotalProfitIsNull() {
            addCriterion("TOTAL_PROFIT is null");
            return (Criteria) this;
        }

        public Criteria andTotalProfitIsNotNull() {
            addCriterion("TOTAL_PROFIT is not null");
            return (Criteria) this;
        }

        public Criteria andTotalProfitEqualTo(BigDecimal value) {
            addCriterion("TOTAL_PROFIT =", value, "totalProfit");
            return (Criteria) this;
        }

        public Criteria andTotalProfitNotEqualTo(BigDecimal value) {
            addCriterion("TOTAL_PROFIT <>", value, "totalProfit");
            return (Criteria) this;
        }

        public Criteria andTotalProfitGreaterThan(BigDecimal value) {
            addCriterion("TOTAL_PROFIT >", value, "totalProfit");
            return (Criteria) this;
        }

        public Criteria andTotalProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TOTAL_PROFIT >=", value, "totalProfit");
            return (Criteria) this;
        }

        public Criteria andTotalProfitLessThan(BigDecimal value) {
            addCriterion("TOTAL_PROFIT <", value, "totalProfit");
            return (Criteria) this;
        }

        public Criteria andTotalProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TOTAL_PROFIT <=", value, "totalProfit");
            return (Criteria) this;
        }

        public Criteria andTotalProfitIn(List<BigDecimal> values) {
            addCriterion("TOTAL_PROFIT in", values, "totalProfit");
            return (Criteria) this;
        }

        public Criteria andTotalProfitNotIn(List<BigDecimal> values) {
            addCriterion("TOTAL_PROFIT not in", values, "totalProfit");
            return (Criteria) this;
        }

        public Criteria andTotalProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TOTAL_PROFIT between", value1, value2, "totalProfit");
            return (Criteria) this;
        }

        public Criteria andTotalProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TOTAL_PROFIT not between", value1, value2, "totalProfit");
            return (Criteria) this;
        }

        public Criteria andFrozenMoneyIsNull() {
            addCriterion("FROZEN_MONEY is null");
            return (Criteria) this;
        }

        public Criteria andFrozenMoneyIsNotNull() {
            addCriterion("FROZEN_MONEY is not null");
            return (Criteria) this;
        }

        public Criteria andFrozenMoneyEqualTo(BigDecimal value) {
            addCriterion("FROZEN_MONEY =", value, "frozenMoney");
            return (Criteria) this;
        }

        public Criteria andFrozenMoneyNotEqualTo(BigDecimal value) {
            addCriterion("FROZEN_MONEY <>", value, "frozenMoney");
            return (Criteria) this;
        }

        public Criteria andFrozenMoneyGreaterThan(BigDecimal value) {
            addCriterion("FROZEN_MONEY >", value, "frozenMoney");
            return (Criteria) this;
        }

        public Criteria andFrozenMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FROZEN_MONEY >=", value, "frozenMoney");
            return (Criteria) this;
        }

        public Criteria andFrozenMoneyLessThan(BigDecimal value) {
            addCriterion("FROZEN_MONEY <", value, "frozenMoney");
            return (Criteria) this;
        }

        public Criteria andFrozenMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FROZEN_MONEY <=", value, "frozenMoney");
            return (Criteria) this;
        }

        public Criteria andFrozenMoneyIn(List<BigDecimal> values) {
            addCriterion("FROZEN_MONEY in", values, "frozenMoney");
            return (Criteria) this;
        }

        public Criteria andFrozenMoneyNotIn(List<BigDecimal> values) {
            addCriterion("FROZEN_MONEY not in", values, "frozenMoney");
            return (Criteria) this;
        }

        public Criteria andFrozenMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FROZEN_MONEY between", value1, value2, "frozenMoney");
            return (Criteria) this;
        }

        public Criteria andFrozenMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FROZEN_MONEY not between", value1, value2, "frozenMoney");
            return (Criteria) this;
        }

        public Criteria andSuccessMoneyIsNull() {
            addCriterion("SUCCESS_MONEY is null");
            return (Criteria) this;
        }

        public Criteria andSuccessMoneyIsNotNull() {
            addCriterion("SUCCESS_MONEY is not null");
            return (Criteria) this;
        }

        public Criteria andSuccessMoneyEqualTo(BigDecimal value) {
            addCriterion("SUCCESS_MONEY =", value, "successMoney");
            return (Criteria) this;
        }

        public Criteria andSuccessMoneyNotEqualTo(BigDecimal value) {
            addCriterion("SUCCESS_MONEY <>", value, "successMoney");
            return (Criteria) this;
        }

        public Criteria andSuccessMoneyGreaterThan(BigDecimal value) {
            addCriterion("SUCCESS_MONEY >", value, "successMoney");
            return (Criteria) this;
        }

        public Criteria andSuccessMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUCCESS_MONEY >=", value, "successMoney");
            return (Criteria) this;
        }

        public Criteria andSuccessMoneyLessThan(BigDecimal value) {
            addCriterion("SUCCESS_MONEY <", value, "successMoney");
            return (Criteria) this;
        }

        public Criteria andSuccessMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUCCESS_MONEY <=", value, "successMoney");
            return (Criteria) this;
        }

        public Criteria andSuccessMoneyIn(List<BigDecimal> values) {
            addCriterion("SUCCESS_MONEY in", values, "successMoney");
            return (Criteria) this;
        }

        public Criteria andSuccessMoneyNotIn(List<BigDecimal> values) {
            addCriterion("SUCCESS_MONEY not in", values, "successMoney");
            return (Criteria) this;
        }

        public Criteria andSuccessMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUCCESS_MONEY between", value1, value2, "successMoney");
            return (Criteria) this;
        }

        public Criteria andSuccessMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUCCESS_MONEY not between", value1, value2, "successMoney");
            return (Criteria) this;
        }

        public Criteria andFailMoneyIsNull() {
            addCriterion("FAIL_MONEY is null");
            return (Criteria) this;
        }

        public Criteria andFailMoneyIsNotNull() {
            addCriterion("FAIL_MONEY is not null");
            return (Criteria) this;
        }

        public Criteria andFailMoneyEqualTo(BigDecimal value) {
            addCriterion("FAIL_MONEY =", value, "failMoney");
            return (Criteria) this;
        }

        public Criteria andFailMoneyNotEqualTo(BigDecimal value) {
            addCriterion("FAIL_MONEY <>", value, "failMoney");
            return (Criteria) this;
        }

        public Criteria andFailMoneyGreaterThan(BigDecimal value) {
            addCriterion("FAIL_MONEY >", value, "failMoney");
            return (Criteria) this;
        }

        public Criteria andFailMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FAIL_MONEY >=", value, "failMoney");
            return (Criteria) this;
        }

        public Criteria andFailMoneyLessThan(BigDecimal value) {
            addCriterion("FAIL_MONEY <", value, "failMoney");
            return (Criteria) this;
        }

        public Criteria andFailMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FAIL_MONEY <=", value, "failMoney");
            return (Criteria) this;
        }

        public Criteria andFailMoneyIn(List<BigDecimal> values) {
            addCriterion("FAIL_MONEY in", values, "failMoney");
            return (Criteria) this;
        }

        public Criteria andFailMoneyNotIn(List<BigDecimal> values) {
            addCriterion("FAIL_MONEY not in", values, "failMoney");
            return (Criteria) this;
        }

        public Criteria andFailMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FAIL_MONEY between", value1, value2, "failMoney");
            return (Criteria) this;
        }

        public Criteria andFailMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FAIL_MONEY not between", value1, value2, "failMoney");
            return (Criteria) this;
        }

        public Criteria andRealMoneyIsNull() {
            addCriterion("REAL_MONEY is null");
            return (Criteria) this;
        }

        public Criteria andRealMoneyIsNotNull() {
            addCriterion("REAL_MONEY is not null");
            return (Criteria) this;
        }

        public Criteria andRealMoneyEqualTo(BigDecimal value) {
            addCriterion("REAL_MONEY =", value, "realMoney");
            return (Criteria) this;
        }

        public Criteria andRealMoneyNotEqualTo(BigDecimal value) {
            addCriterion("REAL_MONEY <>", value, "realMoney");
            return (Criteria) this;
        }

        public Criteria andRealMoneyGreaterThan(BigDecimal value) {
            addCriterion("REAL_MONEY >", value, "realMoney");
            return (Criteria) this;
        }

        public Criteria andRealMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_MONEY >=", value, "realMoney");
            return (Criteria) this;
        }

        public Criteria andRealMoneyLessThan(BigDecimal value) {
            addCriterion("REAL_MONEY <", value, "realMoney");
            return (Criteria) this;
        }

        public Criteria andRealMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_MONEY <=", value, "realMoney");
            return (Criteria) this;
        }

        public Criteria andRealMoneyIn(List<BigDecimal> values) {
            addCriterion("REAL_MONEY in", values, "realMoney");
            return (Criteria) this;
        }

        public Criteria andRealMoneyNotIn(List<BigDecimal> values) {
            addCriterion("REAL_MONEY not in", values, "realMoney");
            return (Criteria) this;
        }

        public Criteria andRealMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_MONEY between", value1, value2, "realMoney");
            return (Criteria) this;
        }

        public Criteria andRealMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_MONEY not between", value1, value2, "realMoney");
            return (Criteria) this;
        }

        public Criteria andRedoMoneyIsNull() {
            addCriterion("REDO_MONEY is null");
            return (Criteria) this;
        }

        public Criteria andRedoMoneyIsNotNull() {
            addCriterion("REDO_MONEY is not null");
            return (Criteria) this;
        }

        public Criteria andRedoMoneyEqualTo(BigDecimal value) {
            addCriterion("REDO_MONEY =", value, "redoMoney");
            return (Criteria) this;
        }

        public Criteria andRedoMoneyNotEqualTo(BigDecimal value) {
            addCriterion("REDO_MONEY <>", value, "redoMoney");
            return (Criteria) this;
        }

        public Criteria andRedoMoneyGreaterThan(BigDecimal value) {
            addCriterion("REDO_MONEY >", value, "redoMoney");
            return (Criteria) this;
        }

        public Criteria andRedoMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REDO_MONEY >=", value, "redoMoney");
            return (Criteria) this;
        }

        public Criteria andRedoMoneyLessThan(BigDecimal value) {
            addCriterion("REDO_MONEY <", value, "redoMoney");
            return (Criteria) this;
        }

        public Criteria andRedoMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REDO_MONEY <=", value, "redoMoney");
            return (Criteria) this;
        }

        public Criteria andRedoMoneyIn(List<BigDecimal> values) {
            addCriterion("REDO_MONEY in", values, "redoMoney");
            return (Criteria) this;
        }

        public Criteria andRedoMoneyNotIn(List<BigDecimal> values) {
            addCriterion("REDO_MONEY not in", values, "redoMoney");
            return (Criteria) this;
        }

        public Criteria andRedoMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REDO_MONEY between", value1, value2, "redoMoney");
            return (Criteria) this;
        }

        public Criteria andRedoMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REDO_MONEY not between", value1, value2, "redoMoney");
            return (Criteria) this;
        }

        public Criteria andReturnMoneyIsNull() {
            addCriterion("RETURN_MONEY is null");
            return (Criteria) this;
        }

        public Criteria andReturnMoneyIsNotNull() {
            addCriterion("RETURN_MONEY is not null");
            return (Criteria) this;
        }

        public Criteria andReturnMoneyEqualTo(BigDecimal value) {
            addCriterion("RETURN_MONEY =", value, "returnMoney");
            return (Criteria) this;
        }

        public Criteria andReturnMoneyNotEqualTo(BigDecimal value) {
            addCriterion("RETURN_MONEY <>", value, "returnMoney");
            return (Criteria) this;
        }

        public Criteria andReturnMoneyGreaterThan(BigDecimal value) {
            addCriterion("RETURN_MONEY >", value, "returnMoney");
            return (Criteria) this;
        }

        public Criteria andReturnMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RETURN_MONEY >=", value, "returnMoney");
            return (Criteria) this;
        }

        public Criteria andReturnMoneyLessThan(BigDecimal value) {
            addCriterion("RETURN_MONEY <", value, "returnMoney");
            return (Criteria) this;
        }

        public Criteria andReturnMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RETURN_MONEY <=", value, "returnMoney");
            return (Criteria) this;
        }

        public Criteria andReturnMoneyIn(List<BigDecimal> values) {
            addCriterion("RETURN_MONEY in", values, "returnMoney");
            return (Criteria) this;
        }

        public Criteria andReturnMoneyNotIn(List<BigDecimal> values) {
            addCriterion("RETURN_MONEY not in", values, "returnMoney");
            return (Criteria) this;
        }

        public Criteria andReturnMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RETURN_MONEY between", value1, value2, "returnMoney");
            return (Criteria) this;
        }

        public Criteria andReturnMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RETURN_MONEY not between", value1, value2, "returnMoney");
            return (Criteria) this;
        }

        public Criteria andTotalProfit1IsNull() {
            addCriterion("TOTAL_PROFIT1 is null");
            return (Criteria) this;
        }

        public Criteria andTotalProfit1IsNotNull() {
            addCriterion("TOTAL_PROFIT1 is not null");
            return (Criteria) this;
        }

        public Criteria andTotalProfit1EqualTo(BigDecimal value) {
            addCriterion("TOTAL_PROFIT1 =", value, "totalProfit1");
            return (Criteria) this;
        }

        public Criteria andTotalProfit1NotEqualTo(BigDecimal value) {
            addCriterion("TOTAL_PROFIT1 <>", value, "totalProfit1");
            return (Criteria) this;
        }

        public Criteria andTotalProfit1GreaterThan(BigDecimal value) {
            addCriterion("TOTAL_PROFIT1 >", value, "totalProfit1");
            return (Criteria) this;
        }

        public Criteria andTotalProfit1GreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TOTAL_PROFIT1 >=", value, "totalProfit1");
            return (Criteria) this;
        }

        public Criteria andTotalProfit1LessThan(BigDecimal value) {
            addCriterion("TOTAL_PROFIT1 <", value, "totalProfit1");
            return (Criteria) this;
        }

        public Criteria andTotalProfit1LessThanOrEqualTo(BigDecimal value) {
            addCriterion("TOTAL_PROFIT1 <=", value, "totalProfit1");
            return (Criteria) this;
        }

        public Criteria andTotalProfit1In(List<BigDecimal> values) {
            addCriterion("TOTAL_PROFIT1 in", values, "totalProfit1");
            return (Criteria) this;
        }

        public Criteria andTotalProfit1NotIn(List<BigDecimal> values) {
            addCriterion("TOTAL_PROFIT1 not in", values, "totalProfit1");
            return (Criteria) this;
        }

        public Criteria andTotalProfit1Between(BigDecimal value1, BigDecimal value2) {
            addCriterion("TOTAL_PROFIT1 between", value1, value2, "totalProfit1");
            return (Criteria) this;
        }

        public Criteria andTotalProfit1NotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TOTAL_PROFIT1 not between", value1, value2, "totalProfit1");
            return (Criteria) this;
        }

        public Criteria andPlatformnumIsNull() {
            addCriterion("PLATFORMNUM is null");
            return (Criteria) this;
        }

        public Criteria andPlatformnumIsNotNull() {
            addCriterion("PLATFORMNUM is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformnumEqualTo(String value) {
            addCriterion("PLATFORMNUM =", value, "platformnum");
            return (Criteria) this;
        }

        public Criteria andPlatformnumNotEqualTo(String value) {
            addCriterion("PLATFORMNUM <>", value, "platformnum");
            return (Criteria) this;
        }

        public Criteria andPlatformnumGreaterThan(String value) {
            addCriterion("PLATFORMNUM >", value, "platformnum");
            return (Criteria) this;
        }

        public Criteria andPlatformnumGreaterThanOrEqualTo(String value) {
            addCriterion("PLATFORMNUM >=", value, "platformnum");
            return (Criteria) this;
        }

        public Criteria andPlatformnumLessThan(String value) {
            addCriterion("PLATFORMNUM <", value, "platformnum");
            return (Criteria) this;
        }

        public Criteria andPlatformnumLessThanOrEqualTo(String value) {
            addCriterion("PLATFORMNUM <=", value, "platformnum");
            return (Criteria) this;
        }

        public Criteria andPlatformnumLike(String value) {
            addCriterion("PLATFORMNUM like", value, "platformnum");
            return (Criteria) this;
        }

        public Criteria andPlatformnumNotLike(String value) {
            addCriterion("PLATFORMNUM not like", value, "platformnum");
            return (Criteria) this;
        }

        public Criteria andPlatformnumIn(List<String> values) {
            addCriterion("PLATFORMNUM in", values, "platformnum");
            return (Criteria) this;
        }

        public Criteria andPlatformnumNotIn(List<String> values) {
            addCriterion("PLATFORMNUM not in", values, "platformnum");
            return (Criteria) this;
        }

        public Criteria andPlatformnumBetween(String value1, String value2) {
            addCriterion("PLATFORMNUM between", value1, value2, "platformnum");
            return (Criteria) this;
        }

        public Criteria andPlatformnumNotBetween(String value1, String value2) {
            addCriterion("PLATFORMNUM not between", value1, value2, "platformnum");
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

        public Criteria andParentBusNumIsNull() {
            addCriterion("PARENT_BUS_NUM is null");
            return (Criteria) this;
        }

        public Criteria andParentBusNumIsNotNull() {
            addCriterion("PARENT_BUS_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andParentBusNumEqualTo(String value) {
            addCriterion("PARENT_BUS_NUM =", value, "parentBusNum");
            return (Criteria) this;
        }

        public Criteria andParentBusNumNotEqualTo(String value) {
            addCriterion("PARENT_BUS_NUM <>", value, "parentBusNum");
            return (Criteria) this;
        }

        public Criteria andParentBusNumGreaterThan(String value) {
            addCriterion("PARENT_BUS_NUM >", value, "parentBusNum");
            return (Criteria) this;
        }

        public Criteria andParentBusNumGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_BUS_NUM >=", value, "parentBusNum");
            return (Criteria) this;
        }

        public Criteria andParentBusNumLessThan(String value) {
            addCriterion("PARENT_BUS_NUM <", value, "parentBusNum");
            return (Criteria) this;
        }

        public Criteria andParentBusNumLessThanOrEqualTo(String value) {
            addCriterion("PARENT_BUS_NUM <=", value, "parentBusNum");
            return (Criteria) this;
        }

        public Criteria andParentBusNumLike(String value) {
            addCriterion("PARENT_BUS_NUM like", value, "parentBusNum");
            return (Criteria) this;
        }

        public Criteria andParentBusNumNotLike(String value) {
            addCriterion("PARENT_BUS_NUM not like", value, "parentBusNum");
            return (Criteria) this;
        }

        public Criteria andParentBusNumIn(List<String> values) {
            addCriterion("PARENT_BUS_NUM in", values, "parentBusNum");
            return (Criteria) this;
        }

        public Criteria andParentBusNumNotIn(List<String> values) {
            addCriterion("PARENT_BUS_NUM not in", values, "parentBusNum");
            return (Criteria) this;
        }

        public Criteria andParentBusNumBetween(String value1, String value2) {
            addCriterion("PARENT_BUS_NUM between", value1, value2, "parentBusNum");
            return (Criteria) this;
        }

        public Criteria andParentBusNumNotBetween(String value1, String value2) {
            addCriterion("PARENT_BUS_NUM not between", value1, value2, "parentBusNum");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdIsNull() {
            addCriterion("PARENT_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdIsNotNull() {
            addCriterion("PARENT_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID =", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID <>", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdGreaterThan(String value) {
            addCriterion("PARENT_AGENT_ID >", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID >=", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdLessThan(String value) {
            addCriterion("PARENT_AGENT_ID <", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdLessThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID <=", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdLike(String value) {
            addCriterion("PARENT_AGENT_ID like", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotLike(String value) {
            addCriterion("PARENT_AGENT_ID not like", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdIn(List<String> values) {
            addCriterion("PARENT_AGENT_ID in", values, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotIn(List<String> values) {
            addCriterion("PARENT_AGENT_ID not in", values, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_ID between", value1, value2, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_ID not between", value1, value2, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andAgentTypeIsNull() {
            addCriterion("AGENT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAgentTypeIsNotNull() {
            addCriterion("AGENT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAgentTypeEqualTo(String value) {
            addCriterion("AGENT_TYPE =", value, "agentType");
            return (Criteria) this;
        }

        public Criteria andAgentTypeNotEqualTo(String value) {
            addCriterion("AGENT_TYPE <>", value, "agentType");
            return (Criteria) this;
        }

        public Criteria andAgentTypeGreaterThan(String value) {
            addCriterion("AGENT_TYPE >", value, "agentType");
            return (Criteria) this;
        }

        public Criteria andAgentTypeGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_TYPE >=", value, "agentType");
            return (Criteria) this;
        }

        public Criteria andAgentTypeLessThan(String value) {
            addCriterion("AGENT_TYPE <", value, "agentType");
            return (Criteria) this;
        }

        public Criteria andAgentTypeLessThanOrEqualTo(String value) {
            addCriterion("AGENT_TYPE <=", value, "agentType");
            return (Criteria) this;
        }

        public Criteria andAgentTypeLike(String value) {
            addCriterion("AGENT_TYPE like", value, "agentType");
            return (Criteria) this;
        }

        public Criteria andAgentTypeNotLike(String value) {
            addCriterion("AGENT_TYPE not like", value, "agentType");
            return (Criteria) this;
        }

        public Criteria andAgentTypeIn(List<String> values) {
            addCriterion("AGENT_TYPE in", values, "agentType");
            return (Criteria) this;
        }

        public Criteria andAgentTypeNotIn(List<String> values) {
            addCriterion("AGENT_TYPE not in", values, "agentType");
            return (Criteria) this;
        }

        public Criteria andAgentTypeBetween(String value1, String value2) {
            addCriterion("AGENT_TYPE between", value1, value2, "agentType");
            return (Criteria) this;
        }

        public Criteria andAgentTypeNotBetween(String value1, String value2) {
            addCriterion("AGENT_TYPE not between", value1, value2, "agentType");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameIsNull() {
            addCriterion("PARENT_AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameIsNotNull() {
            addCriterion("PARENT_AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME =", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME <>", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameGreaterThan(String value) {
            addCriterion("PARENT_AGENT_NAME >", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME >=", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameLessThan(String value) {
            addCriterion("PARENT_AGENT_NAME <", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameLessThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME <=", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameLike(String value) {
            addCriterion("PARENT_AGENT_NAME like", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotLike(String value) {
            addCriterion("PARENT_AGENT_NAME not like", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameIn(List<String> values) {
            addCriterion("PARENT_AGENT_NAME in", values, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotIn(List<String> values) {
            addCriterion("PARENT_AGENT_NAME not in", values, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_NAME between", value1, value2, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_NAME not between", value1, value2, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andPayCompanyIsNull() {
            addCriterion("PAY_COMPANY is null");
            return (Criteria) this;
        }

        public Criteria andPayCompanyIsNotNull() {
            addCriterion("PAY_COMPANY is not null");
            return (Criteria) this;
        }

        public Criteria andPayCompanyEqualTo(String value) {
            addCriterion("PAY_COMPANY =", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyNotEqualTo(String value) {
            addCriterion("PAY_COMPANY <>", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyGreaterThan(String value) {
            addCriterion("PAY_COMPANY >", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_COMPANY >=", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyLessThan(String value) {
            addCriterion("PAY_COMPANY <", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyLessThanOrEqualTo(String value) {
            addCriterion("PAY_COMPANY <=", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyLike(String value) {
            addCriterion("PAY_COMPANY like", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyNotLike(String value) {
            addCriterion("PAY_COMPANY not like", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyIn(List<String> values) {
            addCriterion("PAY_COMPANY in", values, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyNotIn(List<String> values) {
            addCriterion("PAY_COMPANY not in", values, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyBetween(String value1, String value2) {
            addCriterion("PAY_COMPANY between", value1, value2, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyNotBetween(String value1, String value2) {
            addCriterion("PAY_COMPANY not between", value1, value2, "payCompany");
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