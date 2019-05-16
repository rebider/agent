package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

        public Criteria andTotalEndMonthIsNull() {
            addCriterion("TOTAL_END_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andTotalEndMonthIsNotNull() {
            addCriterion("TOTAL_END_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andTotalEndMonthEqualTo(String value) {
            addCriterion("TOTAL_END_MONTH =", value, "totalEndMonth");
            return (Criteria) this;
        }

        public Criteria andTotalEndMonthNotEqualTo(String value) {
            addCriterion("TOTAL_END_MONTH <>", value, "totalEndMonth");
            return (Criteria) this;
        }

        public Criteria andTotalEndMonthGreaterThan(String value) {
            addCriterion("TOTAL_END_MONTH >", value, "totalEndMonth");
            return (Criteria) this;
        }

        public Criteria andTotalEndMonthGreaterThanOrEqualTo(String value) {
            addCriterion("TOTAL_END_MONTH >=", value, "totalEndMonth");
            return (Criteria) this;
        }

        public Criteria andTotalEndMonthLessThan(String value) {
            addCriterion("TOTAL_END_MONTH <", value, "totalEndMonth");
            return (Criteria) this;
        }

        public Criteria andTotalEndMonthLessThanOrEqualTo(String value) {
            addCriterion("TOTAL_END_MONTH <=", value, "totalEndMonth");
            return (Criteria) this;
        }

        public Criteria andTotalEndMonthLike(String value) {
            addCriterion("TOTAL_END_MONTH like", value, "totalEndMonth");
            return (Criteria) this;
        }

        public Criteria andTotalEndMonthNotLike(String value) {
            addCriterion("TOTAL_END_MONTH not like", value, "totalEndMonth");
            return (Criteria) this;
        }

        public Criteria andTotalEndMonthIn(List<String> values) {
            addCriterion("TOTAL_END_MONTH in", values, "totalEndMonth");
            return (Criteria) this;
        }

        public Criteria andTotalEndMonthNotIn(List<String> values) {
            addCriterion("TOTAL_END_MONTH not in", values, "totalEndMonth");
            return (Criteria) this;
        }

        public Criteria andTotalEndMonthBetween(String value1, String value2) {
            addCriterion("TOTAL_END_MONTH between", value1, value2, "totalEndMonth");
            return (Criteria) this;
        }

        public Criteria andTotalEndMonthNotBetween(String value1, String value2) {
            addCriterion("TOTAL_END_MONTH not between", value1, value2, "totalEndMonth");
            return (Criteria) this;
        }

        public Criteria andMachineryNumIsNull() {
            addCriterion("MACHINERY_NUM is null");
            return (Criteria) this;
        }

        public Criteria andMachineryNumIsNotNull() {
            addCriterion("MACHINERY_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andMachineryNumEqualTo(BigDecimal value) {
            addCriterion("MACHINERY_NUM =", value, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumNotEqualTo(BigDecimal value) {
            addCriterion("MACHINERY_NUM <>", value, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumGreaterThan(BigDecimal value) {
            addCriterion("MACHINERY_NUM >", value, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MACHINERY_NUM >=", value, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumLessThan(BigDecimal value) {
            addCriterion("MACHINERY_NUM <", value, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MACHINERY_NUM <=", value, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumIn(List<BigDecimal> values) {
            addCriterion("MACHINERY_NUM in", values, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumNotIn(List<BigDecimal> values) {
            addCriterion("MACHINERY_NUM not in", values, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MACHINERY_NUM between", value1, value2, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MACHINERY_NUM not between", value1, value2, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andCreateTmIsNull() {
            addCriterion("CREATE_TM is null");
            return (Criteria) this;
        }

        public Criteria andCreateTmIsNotNull() {
            addCriterion("CREATE_TM is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTmEqualTo(String value) {
            addCriterion("CREATE_TM =", value, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmNotEqualTo(String value) {
            addCriterion("CREATE_TM <>", value, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmGreaterThan(String value) {
            addCriterion("CREATE_TM >", value, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_TM >=", value, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmLessThan(String value) {
            addCriterion("CREATE_TM <", value, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmLessThanOrEqualTo(String value) {
            addCriterion("CREATE_TM <=", value, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmLike(String value) {
            addCriterion("CREATE_TM like", value, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmNotLike(String value) {
            addCriterion("CREATE_TM not like", value, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmIn(List<String> values) {
            addCriterion("CREATE_TM in", values, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmNotIn(List<String> values) {
            addCriterion("CREATE_TM not in", values, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmBetween(String value1, String value2) {
            addCriterion("CREATE_TM between", value1, value2, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmNotBetween(String value1, String value2) {
            addCriterion("CREATE_TM not between", value1, value2, "createTm");
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

        public Criteria andBusPlatformIsNull() {
            addCriterion("BUS_PLATFORM is null");
            return (Criteria) this;
        }

        public Criteria andBusPlatformIsNotNull() {
            addCriterion("BUS_PLATFORM is not null");
            return (Criteria) this;
        }

        public Criteria andBusPlatformEqualTo(String value) {
            addCriterion("BUS_PLATFORM =", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformNotEqualTo(String value) {
            addCriterion("BUS_PLATFORM <>", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformGreaterThan(String value) {
            addCriterion("BUS_PLATFORM >", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_PLATFORM >=", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformLessThan(String value) {
            addCriterion("BUS_PLATFORM <", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformLessThanOrEqualTo(String value) {
            addCriterion("BUS_PLATFORM <=", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformLike(String value) {
            addCriterion("BUS_PLATFORM like", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformNotLike(String value) {
            addCriterion("BUS_PLATFORM not like", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformIn(List<String> values) {
            addCriterion("BUS_PLATFORM in", values, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformNotIn(List<String> values) {
            addCriterion("BUS_PLATFORM not in", values, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformBetween(String value1, String value2) {
            addCriterion("BUS_PLATFORM between", value1, value2, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformNotBetween(String value1, String value2) {
            addCriterion("BUS_PLATFORM not between", value1, value2, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andAppraisalCycleIsNull() {
            addCriterion("APPRAISAL_CYCLE is null");
            return (Criteria) this;
        }

        public Criteria andAppraisalCycleIsNotNull() {
            addCriterion("APPRAISAL_CYCLE is not null");
            return (Criteria) this;
        }

        public Criteria andAppraisalCycleEqualTo(String value) {
            addCriterion("APPRAISAL_CYCLE =", value, "appraisalCycle");
            return (Criteria) this;
        }

        public Criteria andAppraisalCycleNotEqualTo(String value) {
            addCriterion("APPRAISAL_CYCLE <>", value, "appraisalCycle");
            return (Criteria) this;
        }

        public Criteria andAppraisalCycleGreaterThan(String value) {
            addCriterion("APPRAISAL_CYCLE >", value, "appraisalCycle");
            return (Criteria) this;
        }

        public Criteria andAppraisalCycleGreaterThanOrEqualTo(String value) {
            addCriterion("APPRAISAL_CYCLE >=", value, "appraisalCycle");
            return (Criteria) this;
        }

        public Criteria andAppraisalCycleLessThan(String value) {
            addCriterion("APPRAISAL_CYCLE <", value, "appraisalCycle");
            return (Criteria) this;
        }

        public Criteria andAppraisalCycleLessThanOrEqualTo(String value) {
            addCriterion("APPRAISAL_CYCLE <=", value, "appraisalCycle");
            return (Criteria) this;
        }

        public Criteria andAppraisalCycleLike(String value) {
            addCriterion("APPRAISAL_CYCLE like", value, "appraisalCycle");
            return (Criteria) this;
        }

        public Criteria andAppraisalCycleNotLike(String value) {
            addCriterion("APPRAISAL_CYCLE not like", value, "appraisalCycle");
            return (Criteria) this;
        }

        public Criteria andAppraisalCycleIn(List<String> values) {
            addCriterion("APPRAISAL_CYCLE in", values, "appraisalCycle");
            return (Criteria) this;
        }

        public Criteria andAppraisalCycleNotIn(List<String> values) {
            addCriterion("APPRAISAL_CYCLE not in", values, "appraisalCycle");
            return (Criteria) this;
        }

        public Criteria andAppraisalCycleBetween(String value1, String value2) {
            addCriterion("APPRAISAL_CYCLE between", value1, value2, "appraisalCycle");
            return (Criteria) this;
        }

        public Criteria andAppraisalCycleNotBetween(String value1, String value2) {
            addCriterion("APPRAISAL_CYCLE not between", value1, value2, "appraisalCycle");
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

        public Criteria andIscontainotherposIsNull() {
            addCriterion("ISCONTAINOTHERPOS is null");
            return (Criteria) this;
        }

        public Criteria andIscontainotherposIsNotNull() {
            addCriterion("ISCONTAINOTHERPOS is not null");
            return (Criteria) this;
        }

        public Criteria andIscontainotherposEqualTo(String value) {
            addCriterion("ISCONTAINOTHERPOS =", value, "iscontainotherpos");
            return (Criteria) this;
        }

        public Criteria andIscontainotherposNotEqualTo(String value) {
            addCriterion("ISCONTAINOTHERPOS <>", value, "iscontainotherpos");
            return (Criteria) this;
        }

        public Criteria andIscontainotherposGreaterThan(String value) {
            addCriterion("ISCONTAINOTHERPOS >", value, "iscontainotherpos");
            return (Criteria) this;
        }

        public Criteria andIscontainotherposGreaterThanOrEqualTo(String value) {
            addCriterion("ISCONTAINOTHERPOS >=", value, "iscontainotherpos");
            return (Criteria) this;
        }

        public Criteria andIscontainotherposLessThan(String value) {
            addCriterion("ISCONTAINOTHERPOS <", value, "iscontainotherpos");
            return (Criteria) this;
        }

        public Criteria andIscontainotherposLessThanOrEqualTo(String value) {
            addCriterion("ISCONTAINOTHERPOS <=", value, "iscontainotherpos");
            return (Criteria) this;
        }

        public Criteria andIscontainotherposLike(String value) {
            addCriterion("ISCONTAINOTHERPOS like", value, "iscontainotherpos");
            return (Criteria) this;
        }

        public Criteria andIscontainotherposNotLike(String value) {
            addCriterion("ISCONTAINOTHERPOS not like", value, "iscontainotherpos");
            return (Criteria) this;
        }

        public Criteria andIscontainotherposIn(List<String> values) {
            addCriterion("ISCONTAINOTHERPOS in", values, "iscontainotherpos");
            return (Criteria) this;
        }

        public Criteria andIscontainotherposNotIn(List<String> values) {
            addCriterion("ISCONTAINOTHERPOS not in", values, "iscontainotherpos");
            return (Criteria) this;
        }

        public Criteria andIscontainotherposBetween(String value1, String value2) {
            addCriterion("ISCONTAINOTHERPOS between", value1, value2, "iscontainotherpos");
            return (Criteria) this;
        }

        public Criteria andIscontainotherposNotBetween(String value1, String value2) {
            addCriterion("ISCONTAINOTHERPOS not between", value1, value2, "iscontainotherpos");
            return (Criteria) this;
        }

        public Criteria andAssessWayIsNull() {
            addCriterion("ASSESS_WAY is null");
            return (Criteria) this;
        }

        public Criteria andAssessWayIsNotNull() {
            addCriterion("ASSESS_WAY is not null");
            return (Criteria) this;
        }

        public Criteria andAssessWayEqualTo(String value) {
            addCriterion("ASSESS_WAY =", value, "assessWay");
            return (Criteria) this;
        }

        public Criteria andAssessWayNotEqualTo(String value) {
            addCriterion("ASSESS_WAY <>", value, "assessWay");
            return (Criteria) this;
        }

        public Criteria andAssessWayGreaterThan(String value) {
            addCriterion("ASSESS_WAY >", value, "assessWay");
            return (Criteria) this;
        }

        public Criteria andAssessWayGreaterThanOrEqualTo(String value) {
            addCriterion("ASSESS_WAY >=", value, "assessWay");
            return (Criteria) this;
        }

        public Criteria andAssessWayLessThan(String value) {
            addCriterion("ASSESS_WAY <", value, "assessWay");
            return (Criteria) this;
        }

        public Criteria andAssessWayLessThanOrEqualTo(String value) {
            addCriterion("ASSESS_WAY <=", value, "assessWay");
            return (Criteria) this;
        }

        public Criteria andAssessWayLike(String value) {
            addCriterion("ASSESS_WAY like", value, "assessWay");
            return (Criteria) this;
        }

        public Criteria andAssessWayNotLike(String value) {
            addCriterion("ASSESS_WAY not like", value, "assessWay");
            return (Criteria) this;
        }

        public Criteria andAssessWayIn(List<String> values) {
            addCriterion("ASSESS_WAY in", values, "assessWay");
            return (Criteria) this;
        }

        public Criteria andAssessWayNotIn(List<String> values) {
            addCriterion("ASSESS_WAY not in", values, "assessWay");
            return (Criteria) this;
        }

        public Criteria andAssessWayBetween(String value1, String value2) {
            addCriterion("ASSESS_WAY between", value1, value2, "assessWay");
            return (Criteria) this;
        }

        public Criteria andAssessWayNotBetween(String value1, String value2) {
            addCriterion("ASSESS_WAY not between", value1, value2, "assessWay");
            return (Criteria) this;
        }

        public Criteria andAccountingCycleIsNull() {
            addCriterion("ACCOUNTING_CYCLE is null");
            return (Criteria) this;
        }

        public Criteria andAccountingCycleIsNotNull() {
            addCriterion("ACCOUNTING_CYCLE is not null");
            return (Criteria) this;
        }

        public Criteria andAccountingCycleEqualTo(String value) {
            addCriterion("ACCOUNTING_CYCLE =", value, "accountingCycle");
            return (Criteria) this;
        }

        public Criteria andAccountingCycleNotEqualTo(String value) {
            addCriterion("ACCOUNTING_CYCLE <>", value, "accountingCycle");
            return (Criteria) this;
        }

        public Criteria andAccountingCycleGreaterThan(String value) {
            addCriterion("ACCOUNTING_CYCLE >", value, "accountingCycle");
            return (Criteria) this;
        }

        public Criteria andAccountingCycleGreaterThanOrEqualTo(String value) {
            addCriterion("ACCOUNTING_CYCLE >=", value, "accountingCycle");
            return (Criteria) this;
        }

        public Criteria andAccountingCycleLessThan(String value) {
            addCriterion("ACCOUNTING_CYCLE <", value, "accountingCycle");
            return (Criteria) this;
        }

        public Criteria andAccountingCycleLessThanOrEqualTo(String value) {
            addCriterion("ACCOUNTING_CYCLE <=", value, "accountingCycle");
            return (Criteria) this;
        }

        public Criteria andAccountingCycleLike(String value) {
            addCriterion("ACCOUNTING_CYCLE like", value, "accountingCycle");
            return (Criteria) this;
        }

        public Criteria andAccountingCycleNotLike(String value) {
            addCriterion("ACCOUNTING_CYCLE not like", value, "accountingCycle");
            return (Criteria) this;
        }

        public Criteria andAccountingCycleIn(List<String> values) {
            addCriterion("ACCOUNTING_CYCLE in", values, "accountingCycle");
            return (Criteria) this;
        }

        public Criteria andAccountingCycleNotIn(List<String> values) {
            addCriterion("ACCOUNTING_CYCLE not in", values, "accountingCycle");
            return (Criteria) this;
        }

        public Criteria andAccountingCycleBetween(String value1, String value2) {
            addCriterion("ACCOUNTING_CYCLE between", value1, value2, "accountingCycle");
            return (Criteria) this;
        }

        public Criteria andAccountingCycleNotBetween(String value1, String value2) {
            addCriterion("ACCOUNTING_CYCLE not between", value1, value2, "accountingCycle");
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

        public Criteria andRev3IsNull() {
            addCriterion("REV3 is null");
            return (Criteria) this;
        }

        public Criteria andRev3IsNotNull() {
            addCriterion("REV3 is not null");
            return (Criteria) this;
        }

        public Criteria andRev3EqualTo(String value) {
            addCriterion("REV3 =", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3NotEqualTo(String value) {
            addCriterion("REV3 <>", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3GreaterThan(String value) {
            addCriterion("REV3 >", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3GreaterThanOrEqualTo(String value) {
            addCriterion("REV3 >=", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3LessThan(String value) {
            addCriterion("REV3 <", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3LessThanOrEqualTo(String value) {
            addCriterion("REV3 <=", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3Like(String value) {
            addCriterion("REV3 like", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3NotLike(String value) {
            addCriterion("REV3 not like", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3In(List<String> values) {
            addCriterion("REV3 in", values, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3NotIn(List<String> values) {
            addCriterion("REV3 not in", values, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3Between(String value1, String value2) {
            addCriterion("REV3 between", value1, value2, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3NotBetween(String value1, String value2) {
            addCriterion("REV3 not between", value1, value2, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev4IsNull() {
            addCriterion("REV4 is null");
            return (Criteria) this;
        }

        public Criteria andRev4IsNotNull() {
            addCriterion("REV4 is not null");
            return (Criteria) this;
        }

        public Criteria andRev4EqualTo(String value) {
            addCriterion("REV4 =", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4NotEqualTo(String value) {
            addCriterion("REV4 <>", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4GreaterThan(String value) {
            addCriterion("REV4 >", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4GreaterThanOrEqualTo(String value) {
            addCriterion("REV4 >=", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4LessThan(String value) {
            addCriterion("REV4 <", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4LessThanOrEqualTo(String value) {
            addCriterion("REV4 <=", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4Like(String value) {
            addCriterion("REV4 like", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4NotLike(String value) {
            addCriterion("REV4 not like", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4In(List<String> values) {
            addCriterion("REV4 in", values, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4NotIn(List<String> values) {
            addCriterion("REV4 not in", values, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4Between(String value1, String value2) {
            addCriterion("REV4 between", value1, value2, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4NotBetween(String value1, String value2) {
            addCriterion("REV4 not between", value1, value2, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev5IsNull() {
            addCriterion("REV5 is null");
            return (Criteria) this;
        }

        public Criteria andRev5IsNotNull() {
            addCriterion("REV5 is not null");
            return (Criteria) this;
        }

        public Criteria andRev5EqualTo(String value) {
            addCriterion("REV5 =", value, "rev5");
            return (Criteria) this;
        }

        public Criteria andRev5NotEqualTo(String value) {
            addCriterion("REV5 <>", value, "rev5");
            return (Criteria) this;
        }

        public Criteria andRev5GreaterThan(String value) {
            addCriterion("REV5 >", value, "rev5");
            return (Criteria) this;
        }

        public Criteria andRev5GreaterThanOrEqualTo(String value) {
            addCriterion("REV5 >=", value, "rev5");
            return (Criteria) this;
        }

        public Criteria andRev5LessThan(String value) {
            addCriterion("REV5 <", value, "rev5");
            return (Criteria) this;
        }

        public Criteria andRev5LessThanOrEqualTo(String value) {
            addCriterion("REV5 <=", value, "rev5");
            return (Criteria) this;
        }

        public Criteria andRev5Like(String value) {
            addCriterion("REV5 like", value, "rev5");
            return (Criteria) this;
        }

        public Criteria andRev5NotLike(String value) {
            addCriterion("REV5 not like", value, "rev5");
            return (Criteria) this;
        }

        public Criteria andRev5In(List<String> values) {
            addCriterion("REV5 in", values, "rev5");
            return (Criteria) this;
        }

        public Criteria andRev5NotIn(List<String> values) {
            addCriterion("REV5 not in", values, "rev5");
            return (Criteria) this;
        }

        public Criteria andRev5Between(String value1, String value2) {
            addCriterion("REV5 between", value1, value2, "rev5");
            return (Criteria) this;
        }

        public Criteria andRev5NotBetween(String value1, String value2) {
            addCriterion("REV5 not between", value1, value2, "rev5");
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