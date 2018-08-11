package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PosRewardExample implements Serializable {
    private static final long serialVersionUID = 8248006142438852943L;
    protected String orderByClause;

    protected boolean distinct; 

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PosRewardExample() {
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

        public Criteria andTotalConsMonthIsNull() {
            addCriterion("TOTAL_CONS_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andTotalConsMonthIsNotNull() {
            addCriterion("TOTAL_CONS_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andTotalConsMonthEqualTo(String value) {
            addCriterion("TOTAL_CONS_MONTH =", value, "totalConsMonth");
            return (Criteria) this;
        }

        public Criteria andTotalConsMonthNotEqualTo(String value) {
            addCriterion("TOTAL_CONS_MONTH <>", value, "totalConsMonth");
            return (Criteria) this;
        }

        public Criteria andTotalConsMonthGreaterThan(String value) {
            addCriterion("TOTAL_CONS_MONTH >", value, "totalConsMonth");
            return (Criteria) this;
        }

        public Criteria andTotalConsMonthGreaterThanOrEqualTo(String value) {
            addCriterion("TOTAL_CONS_MONTH >=", value, "totalConsMonth");
            return (Criteria) this;
        }

        public Criteria andTotalConsMonthLessThan(String value) {
            addCriterion("TOTAL_CONS_MONTH <", value, "totalConsMonth");
            return (Criteria) this;
        }

        public Criteria andTotalConsMonthLessThanOrEqualTo(String value) {
            addCriterion("TOTAL_CONS_MONTH <=", value, "totalConsMonth");
            return (Criteria) this;
        }

        public Criteria andTotalConsMonthLike(String value) {
            addCriterion("TOTAL_CONS_MONTH like", value, "totalConsMonth");
            return (Criteria) this;
        }

        public Criteria andTotalConsMonthNotLike(String value) {
            addCriterion("TOTAL_CONS_MONTH not like", value, "totalConsMonth");
            return (Criteria) this;
        }

        public Criteria andTotalConsMonthIn(List<String> values) {
            addCriterion("TOTAL_CONS_MONTH in", values, "totalConsMonth");
            return (Criteria) this;
        }

        public Criteria andTotalConsMonthNotIn(List<String> values) {
            addCriterion("TOTAL_CONS_MONTH not in", values, "totalConsMonth");
            return (Criteria) this;
        }

        public Criteria andTotalConsMonthBetween(String value1, String value2) {
            addCriterion("TOTAL_CONS_MONTH between", value1, value2, "totalConsMonth");
            return (Criteria) this;
        }

        public Criteria andTotalConsMonthNotBetween(String value1, String value2) {
            addCriterion("TOTAL_CONS_MONTH not between", value1, value2, "totalConsMonth");
            return (Criteria) this;
        }

        public Criteria andGrowAmtIsNull() {
            addCriterion("GROW_AMT is null");
            return (Criteria) this;
        }

        public Criteria andGrowAmtIsNotNull() {
            addCriterion("GROW_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andGrowAmtEqualTo(BigDecimal value) {
            addCriterion("GROW_AMT =", value, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtNotEqualTo(BigDecimal value) {
            addCriterion("GROW_AMT <>", value, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtGreaterThan(BigDecimal value) {
            addCriterion("GROW_AMT >", value, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("GROW_AMT >=", value, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtLessThan(BigDecimal value) {
            addCriterion("GROW_AMT <", value, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("GROW_AMT <=", value, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtIn(List<BigDecimal> values) {
            addCriterion("GROW_AMT in", values, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtNotIn(List<BigDecimal> values) {
            addCriterion("GROW_AMT not in", values, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("GROW_AMT between", value1, value2, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("GROW_AMT not between", value1, value2, "growAmt");
            return (Criteria) this;
        }

        public Criteria andCreditConsMonthIsNull() {
            addCriterion("CREDIT_CONS_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andCreditConsMonthIsNotNull() {
            addCriterion("CREDIT_CONS_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andCreditConsMonthEqualTo(String value) {
            addCriterion("CREDIT_CONS_MONTH =", value, "creditConsMonth");
            return (Criteria) this;
        }

        public Criteria andCreditConsMonthNotEqualTo(String value) {
            addCriterion("CREDIT_CONS_MONTH <>", value, "creditConsMonth");
            return (Criteria) this;
        }

        public Criteria andCreditConsMonthGreaterThan(String value) {
            addCriterion("CREDIT_CONS_MONTH >", value, "creditConsMonth");
            return (Criteria) this;
        }

        public Criteria andCreditConsMonthGreaterThanOrEqualTo(String value) {
            addCriterion("CREDIT_CONS_MONTH >=", value, "creditConsMonth");
            return (Criteria) this;
        }

        public Criteria andCreditConsMonthLessThan(String value) {
            addCriterion("CREDIT_CONS_MONTH <", value, "creditConsMonth");
            return (Criteria) this;
        }

        public Criteria andCreditConsMonthLessThanOrEqualTo(String value) {
            addCriterion("CREDIT_CONS_MONTH <=", value, "creditConsMonth");
            return (Criteria) this;
        }

        public Criteria andCreditConsMonthLike(String value) {
            addCriterion("CREDIT_CONS_MONTH like", value, "creditConsMonth");
            return (Criteria) this;
        }

        public Criteria andCreditConsMonthNotLike(String value) {
            addCriterion("CREDIT_CONS_MONTH not like", value, "creditConsMonth");
            return (Criteria) this;
        }

        public Criteria andCreditConsMonthIn(List<String> values) {
            addCriterion("CREDIT_CONS_MONTH in", values, "creditConsMonth");
            return (Criteria) this;
        }

        public Criteria andCreditConsMonthNotIn(List<String> values) {
            addCriterion("CREDIT_CONS_MONTH not in", values, "creditConsMonth");
            return (Criteria) this;
        }

        public Criteria andCreditConsMonthBetween(String value1, String value2) {
            addCriterion("CREDIT_CONS_MONTH between", value1, value2, "creditConsMonth");
            return (Criteria) this;
        }

        public Criteria andCreditConsMonthNotBetween(String value1, String value2) {
            addCriterion("CREDIT_CONS_MONTH not between", value1, value2, "creditConsMonth");
            return (Criteria) this;
        }

        public Criteria andRewardScaleIsNull() {
            addCriterion("REWARD_SCALE is null");
            return (Criteria) this;
        }

        public Criteria andRewardScaleIsNotNull() {
            addCriterion("REWARD_SCALE is not null");
            return (Criteria) this;
        }

        public Criteria andRewardScaleEqualTo(BigDecimal value) {
            addCriterion("REWARD_SCALE =", value, "rewardScale");
            return (Criteria) this;
        }

        public Criteria andRewardScaleNotEqualTo(BigDecimal value) {
            addCriterion("REWARD_SCALE <>", value, "rewardScale");
            return (Criteria) this;
        }

        public Criteria andRewardScaleGreaterThan(BigDecimal value) {
            addCriterion("REWARD_SCALE >", value, "rewardScale");
            return (Criteria) this;
        }

        public Criteria andRewardScaleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REWARD_SCALE >=", value, "rewardScale");
            return (Criteria) this;
        }

        public Criteria andRewardScaleLessThan(BigDecimal value) {
            addCriterion("REWARD_SCALE <", value, "rewardScale");
            return (Criteria) this;
        }

        public Criteria andRewardScaleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REWARD_SCALE <=", value, "rewardScale");
            return (Criteria) this;
        }

        public Criteria andRewardScaleIn(List<BigDecimal> values) {
            addCriterion("REWARD_SCALE in", values, "rewardScale");
            return (Criteria) this;
        }

        public Criteria andRewardScaleNotIn(List<BigDecimal> values) {
            addCriterion("REWARD_SCALE not in", values, "rewardScale");
            return (Criteria) this;
        }

        public Criteria andRewardScaleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REWARD_SCALE between", value1, value2, "rewardScale");
            return (Criteria) this;
        }

        public Criteria andRewardScaleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REWARD_SCALE not between", value1, value2, "rewardScale");
            return (Criteria) this;
        }

        public Criteria andApplyStatusIsNull() {
            addCriterion("APPLY_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andApplyStatusIsNotNull() {
            addCriterion("APPLY_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andApplyStatusEqualTo(String value) {
            addCriterion("APPLY_STATUS =", value, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusNotEqualTo(String value) {
            addCriterion("APPLY_STATUS <>", value, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusGreaterThan(String value) {
            addCriterion("APPLY_STATUS >", value, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusGreaterThanOrEqualTo(String value) {
            addCriterion("APPLY_STATUS >=", value, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusLessThan(String value) {
            addCriterion("APPLY_STATUS <", value, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusLessThanOrEqualTo(String value) {
            addCriterion("APPLY_STATUS <=", value, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusLike(String value) {
            addCriterion("APPLY_STATUS like", value, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusNotLike(String value) {
            addCriterion("APPLY_STATUS not like", value, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusIn(List<String> values) {
            addCriterion("APPLY_STATUS in", values, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusNotIn(List<String> values) {
            addCriterion("APPLY_STATUS not in", values, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusBetween(String value1, String value2) {
            addCriterion("APPLY_STATUS between", value1, value2, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusNotBetween(String value1, String value2) {
            addCriterion("APPLY_STATUS not between", value1, value2, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andCreditEndMonthIsNull() {
            addCriterion("CREDIT_END_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andCreditEndMonthIsNotNull() {
            addCriterion("CREDIT_END_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andCreditEndMonthEqualTo(String value) {
            addCriterion("CREDIT_END_MONTH =", value, "creditEndMonth");
            return (Criteria) this;
        }

        public Criteria andCreditEndMonthNotEqualTo(String value) {
            addCriterion("CREDIT_END_MONTH <>", value, "creditEndMonth");
            return (Criteria) this;
        }

        public Criteria andCreditEndMonthGreaterThan(String value) {
            addCriterion("CREDIT_END_MONTH >", value, "creditEndMonth");
            return (Criteria) this;
        }

        public Criteria andCreditEndMonthGreaterThanOrEqualTo(String value) {
            addCriterion("CREDIT_END_MONTH >=", value, "creditEndMonth");
            return (Criteria) this;
        }

        public Criteria andCreditEndMonthLessThan(String value) {
            addCriterion("CREDIT_END_MONTH <", value, "creditEndMonth");
            return (Criteria) this;
        }

        public Criteria andCreditEndMonthLessThanOrEqualTo(String value) {
            addCriterion("CREDIT_END_MONTH <=", value, "creditEndMonth");
            return (Criteria) this;
        }

        public Criteria andCreditEndMonthLike(String value) {
            addCriterion("CREDIT_END_MONTH like", value, "creditEndMonth");
            return (Criteria) this;
        }

        public Criteria andCreditEndMonthNotLike(String value) {
            addCriterion("CREDIT_END_MONTH not like", value, "creditEndMonth");
            return (Criteria) this;
        }

        public Criteria andCreditEndMonthIn(List<String> values) {
            addCriterion("CREDIT_END_MONTH in", values, "creditEndMonth");
            return (Criteria) this;
        }

        public Criteria andCreditEndMonthNotIn(List<String> values) {
            addCriterion("CREDIT_END_MONTH not in", values, "creditEndMonth");
            return (Criteria) this;
        }

        public Criteria andCreditEndMonthBetween(String value1, String value2) {
            addCriterion("CREDIT_END_MONTH between", value1, value2, "creditEndMonth");
            return (Criteria) this;
        }

        public Criteria andCreditEndMonthNotBetween(String value1, String value2) {
            addCriterion("CREDIT_END_MONTH not between", value1, value2, "creditEndMonth");
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