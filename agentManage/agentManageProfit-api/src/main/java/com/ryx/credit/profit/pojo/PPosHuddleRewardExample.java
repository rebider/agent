package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PPosHuddleRewardExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PPosHuddleRewardExample() {
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

        public Criteria andGrowAmtIsNull() {
            addCriterion("GROW_AMT is null");
            return (Criteria) this;
        }

        public Criteria andGrowAmtIsNotNull() {
            addCriterion("GROW_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andGrowAmtEqualTo(String value) {
            addCriterion("GROW_AMT =", value, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtNotEqualTo(String value) {
            addCriterion("GROW_AMT <>", value, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtGreaterThan(String value) {
            addCriterion("GROW_AMT >", value, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtGreaterThanOrEqualTo(String value) {
            addCriterion("GROW_AMT >=", value, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtLessThan(String value) {
            addCriterion("GROW_AMT <", value, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtLessThanOrEqualTo(String value) {
            addCriterion("GROW_AMT <=", value, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtLike(String value) {
            addCriterion("GROW_AMT like", value, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtNotLike(String value) {
            addCriterion("GROW_AMT not like", value, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtIn(List<String> values) {
            addCriterion("GROW_AMT in", values, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtNotIn(List<String> values) {
            addCriterion("GROW_AMT not in", values, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtBetween(String value1, String value2) {
            addCriterion("GROW_AMT between", value1, value2, "growAmt");
            return (Criteria) this;
        }

        public Criteria andGrowAmtNotBetween(String value1, String value2) {
            addCriterion("GROW_AMT not between", value1, value2, "growAmt");
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

        public Criteria andMachineryNumEqualTo(String value) {
            addCriterion("MACHINERY_NUM =", value, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumNotEqualTo(String value) {
            addCriterion("MACHINERY_NUM <>", value, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumGreaterThan(String value) {
            addCriterion("MACHINERY_NUM >", value, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumGreaterThanOrEqualTo(String value) {
            addCriterion("MACHINERY_NUM >=", value, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumLessThan(String value) {
            addCriterion("MACHINERY_NUM <", value, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumLessThanOrEqualTo(String value) {
            addCriterion("MACHINERY_NUM <=", value, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumLike(String value) {
            addCriterion("MACHINERY_NUM like", value, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumNotLike(String value) {
            addCriterion("MACHINERY_NUM not like", value, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumIn(List<String> values) {
            addCriterion("MACHINERY_NUM in", values, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumNotIn(List<String> values) {
            addCriterion("MACHINERY_NUM not in", values, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumBetween(String value1, String value2) {
            addCriterion("MACHINERY_NUM between", value1, value2, "machineryNum");
            return (Criteria) this;
        }

        public Criteria andMachineryNumNotBetween(String value1, String value2) {
            addCriterion("MACHINERY_NUM not between", value1, value2, "machineryNum");
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