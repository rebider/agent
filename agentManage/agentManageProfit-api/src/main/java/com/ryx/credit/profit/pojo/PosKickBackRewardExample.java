package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PosKickBackRewardExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PosKickBackRewardExample() {
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

        public Criteria andBusNameIsNull() {
            addCriterion("BUS_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBusNameIsNotNull() {
            addCriterion("BUS_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBusNameEqualTo(String value) {
            addCriterion("BUS_NAME =", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameNotEqualTo(String value) {
            addCriterion("BUS_NAME <>", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameGreaterThan(String value) {
            addCriterion("BUS_NAME >", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_NAME >=", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameLessThan(String value) {
            addCriterion("BUS_NAME <", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameLessThanOrEqualTo(String value) {
            addCriterion("BUS_NAME <=", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameLike(String value) {
            addCriterion("BUS_NAME like", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameNotLike(String value) {
            addCriterion("BUS_NAME not like", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameIn(List<String> values) {
            addCriterion("BUS_NAME in", values, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameNotIn(List<String> values) {
            addCriterion("BUS_NAME not in", values, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameBetween(String value1, String value2) {
            addCriterion("BUS_NAME between", value1, value2, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameNotBetween(String value1, String value2) {
            addCriterion("BUS_NAME not between", value1, value2, "busName");
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

        public Criteria andRewardTypeIsNull() {
            addCriterion("REWARD_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andRewardTypeIsNotNull() {
            addCriterion("REWARD_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andRewardTypeEqualTo(String value) {
            addCriterion("REWARD_TYPE =", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeNotEqualTo(String value) {
            addCriterion("REWARD_TYPE <>", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeGreaterThan(String value) {
            addCriterion("REWARD_TYPE >", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeGreaterThanOrEqualTo(String value) {
            addCriterion("REWARD_TYPE >=", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeLessThan(String value) {
            addCriterion("REWARD_TYPE <", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeLessThanOrEqualTo(String value) {
            addCriterion("REWARD_TYPE <=", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeLike(String value) {
            addCriterion("REWARD_TYPE like", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeNotLike(String value) {
            addCriterion("REWARD_TYPE not like", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeIn(List<String> values) {
            addCriterion("REWARD_TYPE in", values, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeNotIn(List<String> values) {
            addCriterion("REWARD_TYPE not in", values, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeBetween(String value1, String value2) {
            addCriterion("REWARD_TYPE between", value1, value2, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeNotBetween(String value1, String value2) {
            addCriterion("REWARD_TYPE not between", value1, value2, "rewardType");
            return (Criteria) this;
        }

        public Criteria andPreteastCycleIsNull() {
            addCriterion("PRETEAST_CYCLE is null");
            return (Criteria) this;
        }

        public Criteria andPreteastCycleIsNotNull() {
            addCriterion("PRETEAST_CYCLE is not null");
            return (Criteria) this;
        }

        public Criteria andPreteastCycleEqualTo(String value) {
            addCriterion("PRETEAST_CYCLE =", value, "preteastCycle");
            return (Criteria) this;
        }

        public Criteria andPreteastCycleNotEqualTo(String value) {
            addCriterion("PRETEAST_CYCLE <>", value, "preteastCycle");
            return (Criteria) this;
        }

        public Criteria andPreteastCycleGreaterThan(String value) {
            addCriterion("PRETEAST_CYCLE >", value, "preteastCycle");
            return (Criteria) this;
        }

        public Criteria andPreteastCycleGreaterThanOrEqualTo(String value) {
            addCriterion("PRETEAST_CYCLE >=", value, "preteastCycle");
            return (Criteria) this;
        }

        public Criteria andPreteastCycleLessThan(String value) {
            addCriterion("PRETEAST_CYCLE <", value, "preteastCycle");
            return (Criteria) this;
        }

        public Criteria andPreteastCycleLessThanOrEqualTo(String value) {
            addCriterion("PRETEAST_CYCLE <=", value, "preteastCycle");
            return (Criteria) this;
        }

        public Criteria andPreteastCycleLike(String value) {
            addCriterion("PRETEAST_CYCLE like", value, "preteastCycle");
            return (Criteria) this;
        }

        public Criteria andPreteastCycleNotLike(String value) {
            addCriterion("PRETEAST_CYCLE not like", value, "preteastCycle");
            return (Criteria) this;
        }

        public Criteria andPreteastCycleIn(List<String> values) {
            addCriterion("PRETEAST_CYCLE in", values, "preteastCycle");
            return (Criteria) this;
        }

        public Criteria andPreteastCycleNotIn(List<String> values) {
            addCriterion("PRETEAST_CYCLE not in", values, "preteastCycle");
            return (Criteria) this;
        }

        public Criteria andPreteastCycleBetween(String value1, String value2) {
            addCriterion("PRETEAST_CYCLE between", value1, value2, "preteastCycle");
            return (Criteria) this;
        }

        public Criteria andPreteastCycleNotBetween(String value1, String value2) {
            addCriterion("PRETEAST_CYCLE not between", value1, value2, "preteastCycle");
            return (Criteria) this;
        }

        public Criteria andCheckMonthIsNull() {
            addCriterion("CHECK_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andCheckMonthIsNotNull() {
            addCriterion("CHECK_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andCheckMonthEqualTo(String value) {
            addCriterion("CHECK_MONTH =", value, "checkMonth");
            return (Criteria) this;
        }

        public Criteria andCheckMonthNotEqualTo(String value) {
            addCriterion("CHECK_MONTH <>", value, "checkMonth");
            return (Criteria) this;
        }

        public Criteria andCheckMonthGreaterThan(String value) {
            addCriterion("CHECK_MONTH >", value, "checkMonth");
            return (Criteria) this;
        }

        public Criteria andCheckMonthGreaterThanOrEqualTo(String value) {
            addCriterion("CHECK_MONTH >=", value, "checkMonth");
            return (Criteria) this;
        }

        public Criteria andCheckMonthLessThan(String value) {
            addCriterion("CHECK_MONTH <", value, "checkMonth");
            return (Criteria) this;
        }

        public Criteria andCheckMonthLessThanOrEqualTo(String value) {
            addCriterion("CHECK_MONTH <=", value, "checkMonth");
            return (Criteria) this;
        }

        public Criteria andCheckMonthLike(String value) {
            addCriterion("CHECK_MONTH like", value, "checkMonth");
            return (Criteria) this;
        }

        public Criteria andCheckMonthNotLike(String value) {
            addCriterion("CHECK_MONTH not like", value, "checkMonth");
            return (Criteria) this;
        }

        public Criteria andCheckMonthIn(List<String> values) {
            addCriterion("CHECK_MONTH in", values, "checkMonth");
            return (Criteria) this;
        }

        public Criteria andCheckMonthNotIn(List<String> values) {
            addCriterion("CHECK_MONTH not in", values, "checkMonth");
            return (Criteria) this;
        }

        public Criteria andCheckMonthBetween(String value1, String value2) {
            addCriterion("CHECK_MONTH between", value1, value2, "checkMonth");
            return (Criteria) this;
        }

        public Criteria andCheckMonthNotBetween(String value1, String value2) {
            addCriterion("CHECK_MONTH not between", value1, value2, "checkMonth");
            return (Criteria) this;
        }

        public Criteria andCreditCardNewAmtIsNull() {
            addCriterion("CREDIT_CARD_NEW_AMT is null");
            return (Criteria) this;
        }

        public Criteria andCreditCardNewAmtIsNotNull() {
            addCriterion("CREDIT_CARD_NEW_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andCreditCardNewAmtEqualTo(BigDecimal value) {
            addCriterion("CREDIT_CARD_NEW_AMT =", value, "creditCardNewAmt");
            return (Criteria) this;
        }

        public Criteria andCreditCardNewAmtNotEqualTo(BigDecimal value) {
            addCriterion("CREDIT_CARD_NEW_AMT <>", value, "creditCardNewAmt");
            return (Criteria) this;
        }

        public Criteria andCreditCardNewAmtGreaterThan(BigDecimal value) {
            addCriterion("CREDIT_CARD_NEW_AMT >", value, "creditCardNewAmt");
            return (Criteria) this;
        }

        public Criteria andCreditCardNewAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CREDIT_CARD_NEW_AMT >=", value, "creditCardNewAmt");
            return (Criteria) this;
        }

        public Criteria andCreditCardNewAmtLessThan(BigDecimal value) {
            addCriterion("CREDIT_CARD_NEW_AMT <", value, "creditCardNewAmt");
            return (Criteria) this;
        }

        public Criteria andCreditCardNewAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CREDIT_CARD_NEW_AMT <=", value, "creditCardNewAmt");
            return (Criteria) this;
        }

        public Criteria andCreditCardNewAmtIn(List<BigDecimal> values) {
            addCriterion("CREDIT_CARD_NEW_AMT in", values, "creditCardNewAmt");
            return (Criteria) this;
        }

        public Criteria andCreditCardNewAmtNotIn(List<BigDecimal> values) {
            addCriterion("CREDIT_CARD_NEW_AMT not in", values, "creditCardNewAmt");
            return (Criteria) this;
        }

        public Criteria andCreditCardNewAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CREDIT_CARD_NEW_AMT between", value1, value2, "creditCardNewAmt");
            return (Criteria) this;
        }

        public Criteria andCreditCardNewAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CREDIT_CARD_NEW_AMT not between", value1, value2, "creditCardNewAmt");
            return (Criteria) this;
        }

        public Criteria andOneMonthCountIsNull() {
            addCriterion("ONE_MONTH_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andOneMonthCountIsNotNull() {
            addCriterion("ONE_MONTH_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andOneMonthCountEqualTo(BigDecimal value) {
            addCriterion("ONE_MONTH_COUNT =", value, "oneMonthCount");
            return (Criteria) this;
        }

        public Criteria andOneMonthCountNotEqualTo(BigDecimal value) {
            addCriterion("ONE_MONTH_COUNT <>", value, "oneMonthCount");
            return (Criteria) this;
        }

        public Criteria andOneMonthCountGreaterThan(BigDecimal value) {
            addCriterion("ONE_MONTH_COUNT >", value, "oneMonthCount");
            return (Criteria) this;
        }

        public Criteria andOneMonthCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ONE_MONTH_COUNT >=", value, "oneMonthCount");
            return (Criteria) this;
        }

        public Criteria andOneMonthCountLessThan(BigDecimal value) {
            addCriterion("ONE_MONTH_COUNT <", value, "oneMonthCount");
            return (Criteria) this;
        }

        public Criteria andOneMonthCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ONE_MONTH_COUNT <=", value, "oneMonthCount");
            return (Criteria) this;
        }

        public Criteria andOneMonthCountIn(List<BigDecimal> values) {
            addCriterion("ONE_MONTH_COUNT in", values, "oneMonthCount");
            return (Criteria) this;
        }

        public Criteria andOneMonthCountNotIn(List<BigDecimal> values) {
            addCriterion("ONE_MONTH_COUNT not in", values, "oneMonthCount");
            return (Criteria) this;
        }

        public Criteria andOneMonthCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ONE_MONTH_COUNT between", value1, value2, "oneMonthCount");
            return (Criteria) this;
        }

        public Criteria andOneMonthCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ONE_MONTH_COUNT not between", value1, value2, "oneMonthCount");
            return (Criteria) this;
        }

        public Criteria andToolsCountIsNull() {
            addCriterion("TOOLS_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andToolsCountIsNotNull() {
            addCriterion("TOOLS_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andToolsCountEqualTo(BigDecimal value) {
            addCriterion("TOOLS_COUNT =", value, "toolsCount");
            return (Criteria) this;
        }

        public Criteria andToolsCountNotEqualTo(BigDecimal value) {
            addCriterion("TOOLS_COUNT <>", value, "toolsCount");
            return (Criteria) this;
        }

        public Criteria andToolsCountGreaterThan(BigDecimal value) {
            addCriterion("TOOLS_COUNT >", value, "toolsCount");
            return (Criteria) this;
        }

        public Criteria andToolsCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TOOLS_COUNT >=", value, "toolsCount");
            return (Criteria) this;
        }

        public Criteria andToolsCountLessThan(BigDecimal value) {
            addCriterion("TOOLS_COUNT <", value, "toolsCount");
            return (Criteria) this;
        }

        public Criteria andToolsCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TOOLS_COUNT <=", value, "toolsCount");
            return (Criteria) this;
        }

        public Criteria andToolsCountIn(List<BigDecimal> values) {
            addCriterion("TOOLS_COUNT in", values, "toolsCount");
            return (Criteria) this;
        }

        public Criteria andToolsCountNotIn(List<BigDecimal> values) {
            addCriterion("TOOLS_COUNT not in", values, "toolsCount");
            return (Criteria) this;
        }

        public Criteria andToolsCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TOOLS_COUNT between", value1, value2, "toolsCount");
            return (Criteria) this;
        }

        public Criteria andToolsCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TOOLS_COUNT not between", value1, value2, "toolsCount");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNull() {
            addCriterion("CHECK_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("CHECK_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(String value) {
            addCriterion("CHECK_STATUS =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(String value) {
            addCriterion("CHECK_STATUS <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(String value) {
            addCriterion("CHECK_STATUS >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(String value) {
            addCriterion("CHECK_STATUS >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(String value) {
            addCriterion("CHECK_STATUS <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(String value) {
            addCriterion("CHECK_STATUS <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLike(String value) {
            addCriterion("CHECK_STATUS like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotLike(String value) {
            addCriterion("CHECK_STATUS not like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<String> values) {
            addCriterion("CHECK_STATUS in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<String> values) {
            addCriterion("CHECK_STATUS not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(String value1, String value2) {
            addCriterion("CHECK_STATUS between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(String value1, String value2) {
            addCriterion("CHECK_STATUS not between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andPretestAmtIsNull() {
            addCriterion("PRETEST_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPretestAmtIsNotNull() {
            addCriterion("PRETEST_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPretestAmtEqualTo(BigDecimal value) {
            addCriterion("PRETEST_AMT =", value, "pretestAmt");
            return (Criteria) this;
        }

        public Criteria andPretestAmtNotEqualTo(BigDecimal value) {
            addCriterion("PRETEST_AMT <>", value, "pretestAmt");
            return (Criteria) this;
        }

        public Criteria andPretestAmtGreaterThan(BigDecimal value) {
            addCriterion("PRETEST_AMT >", value, "pretestAmt");
            return (Criteria) this;
        }

        public Criteria andPretestAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRETEST_AMT >=", value, "pretestAmt");
            return (Criteria) this;
        }

        public Criteria andPretestAmtLessThan(BigDecimal value) {
            addCriterion("PRETEST_AMT <", value, "pretestAmt");
            return (Criteria) this;
        }

        public Criteria andPretestAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRETEST_AMT <=", value, "pretestAmt");
            return (Criteria) this;
        }

        public Criteria andPretestAmtIn(List<BigDecimal> values) {
            addCriterion("PRETEST_AMT in", values, "pretestAmt");
            return (Criteria) this;
        }

        public Criteria andPretestAmtNotIn(List<BigDecimal> values) {
            addCriterion("PRETEST_AMT not in", values, "pretestAmt");
            return (Criteria) this;
        }

        public Criteria andPretestAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRETEST_AMT between", value1, value2, "pretestAmt");
            return (Criteria) this;
        }

        public Criteria andPretestAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRETEST_AMT not between", value1, value2, "pretestAmt");
            return (Criteria) this;
        }

        public Criteria andShouldAmtIsNull() {
            addCriterion("SHOULD_AMT is null");
            return (Criteria) this;
        }

        public Criteria andShouldAmtIsNotNull() {
            addCriterion("SHOULD_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andShouldAmtEqualTo(BigDecimal value) {
            addCriterion("SHOULD_AMT =", value, "shouldAmt");
            return (Criteria) this;
        }

        public Criteria andShouldAmtNotEqualTo(BigDecimal value) {
            addCriterion("SHOULD_AMT <>", value, "shouldAmt");
            return (Criteria) this;
        }

        public Criteria andShouldAmtGreaterThan(BigDecimal value) {
            addCriterion("SHOULD_AMT >", value, "shouldAmt");
            return (Criteria) this;
        }

        public Criteria andShouldAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SHOULD_AMT >=", value, "shouldAmt");
            return (Criteria) this;
        }

        public Criteria andShouldAmtLessThan(BigDecimal value) {
            addCriterion("SHOULD_AMT <", value, "shouldAmt");
            return (Criteria) this;
        }

        public Criteria andShouldAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SHOULD_AMT <=", value, "shouldAmt");
            return (Criteria) this;
        }

        public Criteria andShouldAmtIn(List<BigDecimal> values) {
            addCriterion("SHOULD_AMT in", values, "shouldAmt");
            return (Criteria) this;
        }

        public Criteria andShouldAmtNotIn(List<BigDecimal> values) {
            addCriterion("SHOULD_AMT not in", values, "shouldAmt");
            return (Criteria) this;
        }

        public Criteria andShouldAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SHOULD_AMT between", value1, value2, "shouldAmt");
            return (Criteria) this;
        }

        public Criteria andShouldAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SHOULD_AMT not between", value1, value2, "shouldAmt");
            return (Criteria) this;
        }

        public Criteria andChargeAmtIsNull() {
            addCriterion("CHARGE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andChargeAmtIsNotNull() {
            addCriterion("CHARGE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andChargeAmtEqualTo(BigDecimal value) {
            addCriterion("CHARGE_AMT =", value, "chargeAmt");
            return (Criteria) this;
        }

        public Criteria andChargeAmtNotEqualTo(BigDecimal value) {
            addCriterion("CHARGE_AMT <>", value, "chargeAmt");
            return (Criteria) this;
        }

        public Criteria andChargeAmtGreaterThan(BigDecimal value) {
            addCriterion("CHARGE_AMT >", value, "chargeAmt");
            return (Criteria) this;
        }

        public Criteria andChargeAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CHARGE_AMT >=", value, "chargeAmt");
            return (Criteria) this;
        }

        public Criteria andChargeAmtLessThan(BigDecimal value) {
            addCriterion("CHARGE_AMT <", value, "chargeAmt");
            return (Criteria) this;
        }

        public Criteria andChargeAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CHARGE_AMT <=", value, "chargeAmt");
            return (Criteria) this;
        }

        public Criteria andChargeAmtIn(List<BigDecimal> values) {
            addCriterion("CHARGE_AMT in", values, "chargeAmt");
            return (Criteria) this;
        }

        public Criteria andChargeAmtNotIn(List<BigDecimal> values) {
            addCriterion("CHARGE_AMT not in", values, "chargeAmt");
            return (Criteria) this;
        }

        public Criteria andChargeAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHARGE_AMT between", value1, value2, "chargeAmt");
            return (Criteria) this;
        }

        public Criteria andChargeAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHARGE_AMT not between", value1, value2, "chargeAmt");
            return (Criteria) this;
        }

        public Criteria andLastMonthAmtIsNull() {
            addCriterion("LAST_MONTH_AMT is null");
            return (Criteria) this;
        }

        public Criteria andLastMonthAmtIsNotNull() {
            addCriterion("LAST_MONTH_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andLastMonthAmtEqualTo(BigDecimal value) {
            addCriterion("LAST_MONTH_AMT =", value, "lastMonthAmt");
            return (Criteria) this;
        }

        public Criteria andLastMonthAmtNotEqualTo(BigDecimal value) {
            addCriterion("LAST_MONTH_AMT <>", value, "lastMonthAmt");
            return (Criteria) this;
        }

        public Criteria andLastMonthAmtGreaterThan(BigDecimal value) {
            addCriterion("LAST_MONTH_AMT >", value, "lastMonthAmt");
            return (Criteria) this;
        }

        public Criteria andLastMonthAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("LAST_MONTH_AMT >=", value, "lastMonthAmt");
            return (Criteria) this;
        }

        public Criteria andLastMonthAmtLessThan(BigDecimal value) {
            addCriterion("LAST_MONTH_AMT <", value, "lastMonthAmt");
            return (Criteria) this;
        }

        public Criteria andLastMonthAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("LAST_MONTH_AMT <=", value, "lastMonthAmt");
            return (Criteria) this;
        }

        public Criteria andLastMonthAmtIn(List<BigDecimal> values) {
            addCriterion("LAST_MONTH_AMT in", values, "lastMonthAmt");
            return (Criteria) this;
        }

        public Criteria andLastMonthAmtNotIn(List<BigDecimal> values) {
            addCriterion("LAST_MONTH_AMT not in", values, "lastMonthAmt");
            return (Criteria) this;
        }

        public Criteria andLastMonthAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LAST_MONTH_AMT between", value1, value2, "lastMonthAmt");
            return (Criteria) this;
        }

        public Criteria andLastMonthAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LAST_MONTH_AMT not between", value1, value2, "lastMonthAmt");
            return (Criteria) this;
        }

        public Criteria andRev1IsNull() {
            addCriterion("REV1 is null");
            return (Criteria) this;
        }

        public Criteria andRev1IsNotNull() {
            addCriterion("REV1 is not null");
            return (Criteria) this;
        }

        public Criteria andRev1EqualTo(String value) {
            addCriterion("REV1 =", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotEqualTo(String value) {
            addCriterion("REV1 <>", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1GreaterThan(String value) {
            addCriterion("REV1 >", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1GreaterThanOrEqualTo(String value) {
            addCriterion("REV1 >=", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1LessThan(String value) {
            addCriterion("REV1 <", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1LessThanOrEqualTo(String value) {
            addCriterion("REV1 <=", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1Like(String value) {
            addCriterion("REV1 like", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotLike(String value) {
            addCriterion("REV1 not like", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1In(List<String> values) {
            addCriterion("REV1 in", values, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotIn(List<String> values) {
            addCriterion("REV1 not in", values, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1Between(String value1, String value2) {
            addCriterion("REV1 between", value1, value2, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotBetween(String value1, String value2) {
            addCriterion("REV1 not between", value1, value2, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev2IsNull() {
            addCriterion("REV2 is null");
            return (Criteria) this;
        }

        public Criteria andRev2IsNotNull() {
            addCriterion("REV2 is not null");
            return (Criteria) this;
        }

        public Criteria andRev2EqualTo(String value) {
            addCriterion("REV2 =", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotEqualTo(String value) {
            addCriterion("REV2 <>", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2GreaterThan(String value) {
            addCriterion("REV2 >", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2GreaterThanOrEqualTo(String value) {
            addCriterion("REV2 >=", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2LessThan(String value) {
            addCriterion("REV2 <", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2LessThanOrEqualTo(String value) {
            addCriterion("REV2 <=", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2Like(String value) {
            addCriterion("REV2 like", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotLike(String value) {
            addCriterion("REV2 not like", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2In(List<String> values) {
            addCriterion("REV2 in", values, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotIn(List<String> values) {
            addCriterion("REV2 not in", values, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2Between(String value1, String value2) {
            addCriterion("REV2 between", value1, value2, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotBetween(String value1, String value2) {
            addCriterion("REV2 not between", value1, value2, "rev2");
            return (Criteria) this;
        }

        public Criteria andPretestKAmtIsNull() {
            addCriterion("PRETEST_K_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPretestKAmtIsNotNull() {
            addCriterion("PRETEST_K_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPretestKAmtEqualTo(BigDecimal value) {
            addCriterion("PRETEST_K_AMT =", value, "pretestKAmt");
            return (Criteria) this;
        }

        public Criteria andPretestKAmtNotEqualTo(BigDecimal value) {
            addCriterion("PRETEST_K_AMT <>", value, "pretestKAmt");
            return (Criteria) this;
        }

        public Criteria andPretestKAmtGreaterThan(BigDecimal value) {
            addCriterion("PRETEST_K_AMT >", value, "pretestKAmt");
            return (Criteria) this;
        }

        public Criteria andPretestKAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRETEST_K_AMT >=", value, "pretestKAmt");
            return (Criteria) this;
        }

        public Criteria andPretestKAmtLessThan(BigDecimal value) {
            addCriterion("PRETEST_K_AMT <", value, "pretestKAmt");
            return (Criteria) this;
        }

        public Criteria andPretestKAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRETEST_K_AMT <=", value, "pretestKAmt");
            return (Criteria) this;
        }

        public Criteria andPretestKAmtIn(List<BigDecimal> values) {
            addCriterion("PRETEST_K_AMT in", values, "pretestKAmt");
            return (Criteria) this;
        }

        public Criteria andPretestKAmtNotIn(List<BigDecimal> values) {
            addCriterion("PRETEST_K_AMT not in", values, "pretestKAmt");
            return (Criteria) this;
        }

        public Criteria andPretestKAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRETEST_K_AMT between", value1, value2, "pretestKAmt");
            return (Criteria) this;
        }

        public Criteria andPretestKAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRETEST_K_AMT not between", value1, value2, "pretestKAmt");
            return (Criteria) this;
        }

        public Criteria andShouldKAmtIsNull() {
            addCriterion("SHOULD_K_AMT is null");
            return (Criteria) this;
        }

        public Criteria andShouldKAmtIsNotNull() {
            addCriterion("SHOULD_K_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andShouldKAmtEqualTo(BigDecimal value) {
            addCriterion("SHOULD_K_AMT =", value, "shouldKAmt");
            return (Criteria) this;
        }

        public Criteria andShouldKAmtNotEqualTo(BigDecimal value) {
            addCriterion("SHOULD_K_AMT <>", value, "shouldKAmt");
            return (Criteria) this;
        }

        public Criteria andShouldKAmtGreaterThan(BigDecimal value) {
            addCriterion("SHOULD_K_AMT >", value, "shouldKAmt");
            return (Criteria) this;
        }

        public Criteria andShouldKAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SHOULD_K_AMT >=", value, "shouldKAmt");
            return (Criteria) this;
        }

        public Criteria andShouldKAmtLessThan(BigDecimal value) {
            addCriterion("SHOULD_K_AMT <", value, "shouldKAmt");
            return (Criteria) this;
        }

        public Criteria andShouldKAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SHOULD_K_AMT <=", value, "shouldKAmt");
            return (Criteria) this;
        }

        public Criteria andShouldKAmtIn(List<BigDecimal> values) {
            addCriterion("SHOULD_K_AMT in", values, "shouldKAmt");
            return (Criteria) this;
        }

        public Criteria andShouldKAmtNotIn(List<BigDecimal> values) {
            addCriterion("SHOULD_K_AMT not in", values, "shouldKAmt");
            return (Criteria) this;
        }

        public Criteria andShouldKAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SHOULD_K_AMT between", value1, value2, "shouldKAmt");
            return (Criteria) this;
        }

        public Criteria andShouldKAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SHOULD_K_AMT not between", value1, value2, "shouldKAmt");
            return (Criteria) this;
        }

        public Criteria andNotAmtIsNull() {
            addCriterion("NOT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andNotAmtIsNotNull() {
            addCriterion("NOT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andNotAmtEqualTo(BigDecimal value) {
            addCriterion("NOT_AMT =", value, "notAmt");
            return (Criteria) this;
        }

        public Criteria andNotAmtNotEqualTo(BigDecimal value) {
            addCriterion("NOT_AMT <>", value, "notAmt");
            return (Criteria) this;
        }

        public Criteria andNotAmtGreaterThan(BigDecimal value) {
            addCriterion("NOT_AMT >", value, "notAmt");
            return (Criteria) this;
        }

        public Criteria andNotAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NOT_AMT >=", value, "notAmt");
            return (Criteria) this;
        }

        public Criteria andNotAmtLessThan(BigDecimal value) {
            addCriterion("NOT_AMT <", value, "notAmt");
            return (Criteria) this;
        }

        public Criteria andNotAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NOT_AMT <=", value, "notAmt");
            return (Criteria) this;
        }

        public Criteria andNotAmtIn(List<BigDecimal> values) {
            addCriterion("NOT_AMT in", values, "notAmt");
            return (Criteria) this;
        }

        public Criteria andNotAmtNotIn(List<BigDecimal> values) {
            addCriterion("NOT_AMT not in", values, "notAmt");
            return (Criteria) this;
        }

        public Criteria andNotAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NOT_AMT between", value1, value2, "notAmt");
            return (Criteria) this;
        }

        public Criteria andNotAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NOT_AMT not between", value1, value2, "notAmt");
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