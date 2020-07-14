package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BalanceApprovalExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public BalanceApprovalExample() {
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

        public Criteria andBalanceIdIsNull() {
            addCriterion("BALANCE_ID is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIdIsNotNull() {
            addCriterion("BALANCE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceIdEqualTo(String value) {
            addCriterion("BALANCE_ID =", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdNotEqualTo(String value) {
            addCriterion("BALANCE_ID <>", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdGreaterThan(String value) {
            addCriterion("BALANCE_ID >", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_ID >=", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdLessThan(String value) {
            addCriterion("BALANCE_ID <", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_ID <=", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdLike(String value) {
            addCriterion("BALANCE_ID like", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdNotLike(String value) {
            addCriterion("BALANCE_ID not like", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdIn(List<String> values) {
            addCriterion("BALANCE_ID in", values, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdNotIn(List<String> values) {
            addCriterion("BALANCE_ID not in", values, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdBetween(String value1, String value2) {
            addCriterion("BALANCE_ID between", value1, value2, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdNotBetween(String value1, String value2) {
            addCriterion("BALANCE_ID not between", value1, value2, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNull() {
            addCriterion("BATCH_NO is null");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNotNull() {
            addCriterion("BATCH_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNoEqualTo(String value) {
            addCriterion("BATCH_NO =", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotEqualTo(String value) {
            addCriterion("BATCH_NO <>", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThan(String value) {
            addCriterion("BATCH_NO >", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThanOrEqualTo(String value) {
            addCriterion("BATCH_NO >=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThan(String value) {
            addCriterion("BATCH_NO <", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThanOrEqualTo(String value) {
            addCriterion("BATCH_NO <=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLike(String value) {
            addCriterion("BATCH_NO like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotLike(String value) {
            addCriterion("BATCH_NO not like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoIn(List<String> values) {
            addCriterion("BATCH_NO in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotIn(List<String> values) {
            addCriterion("BATCH_NO not in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoBetween(String value1, String value2) {
            addCriterion("BATCH_NO between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotBetween(String value1, String value2) {
            addCriterion("BATCH_NO not between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andSettleMonthIsNull() {
            addCriterion("SETTLE_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andSettleMonthIsNotNull() {
            addCriterion("SETTLE_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andSettleMonthEqualTo(String value) {
            addCriterion("SETTLE_MONTH =", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthNotEqualTo(String value) {
            addCriterion("SETTLE_MONTH <>", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthGreaterThan(String value) {
            addCriterion("SETTLE_MONTH >", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthGreaterThanOrEqualTo(String value) {
            addCriterion("SETTLE_MONTH >=", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthLessThan(String value) {
            addCriterion("SETTLE_MONTH <", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthLessThanOrEqualTo(String value) {
            addCriterion("SETTLE_MONTH <=", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthLike(String value) {
            addCriterion("SETTLE_MONTH like", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthNotLike(String value) {
            addCriterion("SETTLE_MONTH not like", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthIn(List<String> values) {
            addCriterion("SETTLE_MONTH in", values, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthNotIn(List<String> values) {
            addCriterion("SETTLE_MONTH not in", values, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthBetween(String value1, String value2) {
            addCriterion("SETTLE_MONTH between", value1, value2, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthNotBetween(String value1, String value2) {
            addCriterion("SETTLE_MONTH not between", value1, value2, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNull() {
            addCriterion("APPLY_DATE is null");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNotNull() {
            addCriterion("APPLY_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andApplyDateEqualTo(String value) {
            addCriterion("APPLY_DATE =", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotEqualTo(String value) {
            addCriterion("APPLY_DATE <>", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThan(String value) {
            addCriterion("APPLY_DATE >", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThanOrEqualTo(String value) {
            addCriterion("APPLY_DATE >=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThan(String value) {
            addCriterion("APPLY_DATE <", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThanOrEqualTo(String value) {
            addCriterion("APPLY_DATE <=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLike(String value) {
            addCriterion("APPLY_DATE like", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotLike(String value) {
            addCriterion("APPLY_DATE not like", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateIn(List<String> values) {
            addCriterion("APPLY_DATE in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotIn(List<String> values) {
            addCriterion("APPLY_DATE not in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateBetween(String value1, String value2) {
            addCriterion("APPLY_DATE between", value1, value2, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotBetween(String value1, String value2) {
            addCriterion("APPLY_DATE not between", value1, value2, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyUserIsNull() {
            addCriterion("APPLY_USER is null");
            return (Criteria) this;
        }

        public Criteria andApplyUserIsNotNull() {
            addCriterion("APPLY_USER is not null");
            return (Criteria) this;
        }

        public Criteria andApplyUserEqualTo(String value) {
            addCriterion("APPLY_USER =", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserNotEqualTo(String value) {
            addCriterion("APPLY_USER <>", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserGreaterThan(String value) {
            addCriterion("APPLY_USER >", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserGreaterThanOrEqualTo(String value) {
            addCriterion("APPLY_USER >=", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserLessThan(String value) {
            addCriterion("APPLY_USER <", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserLessThanOrEqualTo(String value) {
            addCriterion("APPLY_USER <=", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserLike(String value) {
            addCriterion("APPLY_USER like", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserNotLike(String value) {
            addCriterion("APPLY_USER not like", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserIn(List<String> values) {
            addCriterion("APPLY_USER in", values, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserNotIn(List<String> values) {
            addCriterion("APPLY_USER not in", values, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserBetween(String value1, String value2) {
            addCriterion("APPLY_USER between", value1, value2, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserNotBetween(String value1, String value2) {
            addCriterion("APPLY_USER not between", value1, value2, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNull() {
            addCriterion("APPLY_TIME is null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNotNull() {
            addCriterion("APPLY_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeEqualTo(String value) {
            addCriterion("APPLY_TIME =", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotEqualTo(String value) {
            addCriterion("APPLY_TIME <>", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThan(String value) {
            addCriterion("APPLY_TIME >", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThanOrEqualTo(String value) {
            addCriterion("APPLY_TIME >=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThan(String value) {
            addCriterion("APPLY_TIME <", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThanOrEqualTo(String value) {
            addCriterion("APPLY_TIME <=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLike(String value) {
            addCriterion("APPLY_TIME like", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotLike(String value) {
            addCriterion("APPLY_TIME not like", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIn(List<String> values) {
            addCriterion("APPLY_TIME in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotIn(List<String> values) {
            addCriterion("APPLY_TIME not in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeBetween(String value1, String value2) {
            addCriterion("APPLY_TIME between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotBetween(String value1, String value2) {
            addCriterion("APPLY_TIME not between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNull() {
            addCriterion("P.AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNotNull() {
            addCriterion("P.AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentIdEqualTo(String value) {
            addCriterion("P.AGENT_ID =", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotEqualTo(String value) {
            addCriterion("P.AGENT_ID <>", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThan(String value) {
            addCriterion("P.AGENT_ID >", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("P.AGENT_ID >=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThan(String value) {
            addCriterion("P.AGENT_ID <", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThanOrEqualTo(String value) {
            addCriterion("P.AGENT_ID <=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLike(String value) {
            addCriterion("P.AGENT_ID like", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotLike(String value) {
            addCriterion("P.AGENT_ID not like", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdIn(List<String> values) {
            addCriterion("P.AGENT_ID in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotIn(List<String> values) {
            addCriterion("P.AGENT_ID not in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdBetween(String value1, String value2) {
            addCriterion("P.AGENT_ID between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotBetween(String value1, String value2) {
            addCriterion("P.AGENT_ID not between", value1, value2, "agentId");
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

        public Criteria andBusCodeIsNull() {
            addCriterion("BUS_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBusCodeIsNotNull() {
            addCriterion("BUS_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBusCodeEqualTo(String value) {
            addCriterion("BUS_CODE =", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotEqualTo(String value) {
            addCriterion("BUS_CODE <>", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeGreaterThan(String value) {
            addCriterion("BUS_CODE >", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_CODE >=", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeLessThan(String value) {
            addCriterion("BUS_CODE <", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeLessThanOrEqualTo(String value) {
            addCriterion("BUS_CODE <=", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeLike(String value) {
            addCriterion("BUS_CODE like", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotLike(String value) {
            addCriterion("BUS_CODE not like", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeIn(List<String> values) {
            addCriterion("BUS_CODE in", values, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotIn(List<String> values) {
            addCriterion("BUS_CODE not in", values, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeBetween(String value1, String value2) {
            addCriterion("BUS_CODE between", value1, value2, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotBetween(String value1, String value2) {
            addCriterion("BUS_CODE not between", value1, value2, "busCode");
            return (Criteria) this;
        }

        public Criteria andRequestNoIsNull() {
            addCriterion("REQUEST_NO is null");
            return (Criteria) this;
        }

        public Criteria andRequestNoIsNotNull() {
            addCriterion("REQUEST_NO is not null");
            return (Criteria) this;
        }

        public Criteria andRequestNoEqualTo(String value) {
            addCriterion("REQUEST_NO =", value, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoNotEqualTo(String value) {
            addCriterion("REQUEST_NO <>", value, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoGreaterThan(String value) {
            addCriterion("REQUEST_NO >", value, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoGreaterThanOrEqualTo(String value) {
            addCriterion("REQUEST_NO >=", value, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoLessThan(String value) {
            addCriterion("REQUEST_NO <", value, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoLessThanOrEqualTo(String value) {
            addCriterion("REQUEST_NO <=", value, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoLike(String value) {
            addCriterion("REQUEST_NO like", value, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoNotLike(String value) {
            addCriterion("REQUEST_NO not like", value, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoIn(List<String> values) {
            addCriterion("REQUEST_NO in", values, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoNotIn(List<String> values) {
            addCriterion("REQUEST_NO not in", values, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoBetween(String value1, String value2) {
            addCriterion("REQUEST_NO between", value1, value2, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoNotBetween(String value1, String value2) {
            addCriterion("REQUEST_NO not between", value1, value2, "requestNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtIsNull() {
            addCriterion("BALANCE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtIsNotNull() {
            addCriterion("BALANCE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT =", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtNotEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT <>", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtGreaterThan(BigDecimal value) {
            addCriterion("BALANCE_AMT >", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT >=", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtLessThan(BigDecimal value) {
            addCriterion("BALANCE_AMT <", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT <=", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtIn(List<BigDecimal> values) {
            addCriterion("BALANCE_AMT in", values, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtNotIn(List<BigDecimal> values) {
            addCriterion("BALANCE_AMT not in", values, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BALANCE_AMT between", value1, value2, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BALANCE_AMT not between", value1, value2, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusIsNull() {
            addCriterion("APPROVAL_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusIsNotNull() {
            addCriterion("APPROVAL_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusEqualTo(String value) {
            addCriterion("APPROVAL_STATUS =", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusNotEqualTo(String value) {
            addCriterion("APPROVAL_STATUS <>", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusGreaterThan(String value) {
            addCriterion("APPROVAL_STATUS >", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusGreaterThanOrEqualTo(String value) {
            addCriterion("APPROVAL_STATUS >=", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusLessThan(String value) {
            addCriterion("APPROVAL_STATUS <", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusLessThanOrEqualTo(String value) {
            addCriterion("APPROVAL_STATUS <=", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusLike(String value) {
            addCriterion("APPROVAL_STATUS like", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusNotLike(String value) {
            addCriterion("APPROVAL_STATUS not like", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusIn(List<String> values) {
            addCriterion("APPROVAL_STATUS in", values, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusNotIn(List<String> values) {
            addCriterion("APPROVAL_STATUS not in", values, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusBetween(String value1, String value2) {
            addCriterion("APPROVAL_STATUS between", value1, value2, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusNotBetween(String value1, String value2) {
            addCriterion("APPROVAL_STATUS not between", value1, value2, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andBalanceRemarkIsNull() {
            addCriterion("BALANCE_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andBalanceRemarkIsNotNull() {
            addCriterion("BALANCE_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceRemarkEqualTo(String value) {
            addCriterion("BALANCE_REMARK =", value, "balanceRemark");
            return (Criteria) this;
        }

        public Criteria andBalanceRemarkNotEqualTo(String value) {
            addCriterion("BALANCE_REMARK <>", value, "balanceRemark");
            return (Criteria) this;
        }

        public Criteria andBalanceRemarkGreaterThan(String value) {
            addCriterion("BALANCE_REMARK >", value, "balanceRemark");
            return (Criteria) this;
        }

        public Criteria andBalanceRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_REMARK >=", value, "balanceRemark");
            return (Criteria) this;
        }

        public Criteria andBalanceRemarkLessThan(String value) {
            addCriterion("BALANCE_REMARK <", value, "balanceRemark");
            return (Criteria) this;
        }

        public Criteria andBalanceRemarkLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_REMARK <=", value, "balanceRemark");
            return (Criteria) this;
        }

        public Criteria andBalanceRemarkLike(String value) {
            addCriterion("BALANCE_REMARK like", value, "balanceRemark");
            return (Criteria) this;
        }

        public Criteria andBalanceRemarkNotLike(String value) {
            addCriterion("BALANCE_REMARK not like", value, "balanceRemark");
            return (Criteria) this;
        }

        public Criteria andBalanceRemarkIn(List<String> values) {
            addCriterion("BALANCE_REMARK in", values, "balanceRemark");
            return (Criteria) this;
        }

        public Criteria andBalanceRemarkNotIn(List<String> values) {
            addCriterion("BALANCE_REMARK not in", values, "balanceRemark");
            return (Criteria) this;
        }

        public Criteria andBalanceRemarkBetween(String value1, String value2) {
            addCriterion("BALANCE_REMARK between", value1, value2, "balanceRemark");
            return (Criteria) this;
        }

        public Criteria andBalanceRemarkNotBetween(String value1, String value2) {
            addCriterion("BALANCE_REMARK not between", value1, value2, "balanceRemark");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoIsNull() {
            addCriterion("BALANCE_ACCT_NO is null");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoIsNotNull() {
            addCriterion("BALANCE_ACCT_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NO =", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoNotEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NO <>", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoGreaterThan(String value) {
            addCriterion("BALANCE_ACCT_NO >", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NO >=", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoLessThan(String value) {
            addCriterion("BALANCE_ACCT_NO <", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NO <=", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoLike(String value) {
            addCriterion("BALANCE_ACCT_NO like", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoNotLike(String value) {
            addCriterion("BALANCE_ACCT_NO not like", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoIn(List<String> values) {
            addCriterion("BALANCE_ACCT_NO in", values, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoNotIn(List<String> values) {
            addCriterion("BALANCE_ACCT_NO not in", values, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoBetween(String value1, String value2) {
            addCriterion("BALANCE_ACCT_NO between", value1, value2, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoNotBetween(String value1, String value2) {
            addCriterion("BALANCE_ACCT_NO not between", value1, value2, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameIsNull() {
            addCriterion("BALANCE_ACCT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameIsNotNull() {
            addCriterion("BALANCE_ACCT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NAME =", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameNotEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NAME <>", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameGreaterThan(String value) {
            addCriterion("BALANCE_ACCT_NAME >", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NAME >=", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameLessThan(String value) {
            addCriterion("BALANCE_ACCT_NAME <", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NAME <=", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameLike(String value) {
            addCriterion("BALANCE_ACCT_NAME like", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameNotLike(String value) {
            addCriterion("BALANCE_ACCT_NAME not like", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameIn(List<String> values) {
            addCriterion("BALANCE_ACCT_NAME in", values, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameNotIn(List<String> values) {
            addCriterion("BALANCE_ACCT_NAME not in", values, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameBetween(String value1, String value2) {
            addCriterion("BALANCE_ACCT_NAME between", value1, value2, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameNotBetween(String value1, String value2) {
            addCriterion("BALANCE_ACCT_NAME not between", value1, value2, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoIsNull() {
            addCriterion("BALANCE_BANK_NO is null");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoIsNotNull() {
            addCriterion("BALANCE_BANK_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoEqualTo(String value) {
            addCriterion("BALANCE_BANK_NO =", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoNotEqualTo(String value) {
            addCriterion("BALANCE_BANK_NO <>", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoGreaterThan(String value) {
            addCriterion("BALANCE_BANK_NO >", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_BANK_NO >=", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoLessThan(String value) {
            addCriterion("BALANCE_BANK_NO <", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_BANK_NO <=", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoLike(String value) {
            addCriterion("BALANCE_BANK_NO like", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoNotLike(String value) {
            addCriterion("BALANCE_BANK_NO not like", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoIn(List<String> values) {
            addCriterion("BALANCE_BANK_NO in", values, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoNotIn(List<String> values) {
            addCriterion("BALANCE_BANK_NO not in", values, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoBetween(String value1, String value2) {
            addCriterion("BALANCE_BANK_NO between", value1, value2, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoNotBetween(String value1, String value2) {
            addCriterion("BALANCE_BANK_NO not between", value1, value2, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameIsNull() {
            addCriterion("BALANCE_BANK_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameIsNotNull() {
            addCriterion("BALANCE_BANK_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameEqualTo(String value) {
            addCriterion("BALANCE_BANK_NAME =", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameNotEqualTo(String value) {
            addCriterion("BALANCE_BANK_NAME <>", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameGreaterThan(String value) {
            addCriterion("BALANCE_BANK_NAME >", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_BANK_NAME >=", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameLessThan(String value) {
            addCriterion("BALANCE_BANK_NAME <", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_BANK_NAME <=", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameLike(String value) {
            addCriterion("BALANCE_BANK_NAME like", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameNotLike(String value) {
            addCriterion("BALANCE_BANK_NAME not like", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameIn(List<String> values) {
            addCriterion("BALANCE_BANK_NAME in", values, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameNotIn(List<String> values) {
            addCriterion("BALANCE_BANK_NAME not in", values, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameBetween(String value1, String value2) {
            addCriterion("BALANCE_BANK_NAME between", value1, value2, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameNotBetween(String value1, String value2) {
            addCriterion("BALANCE_BANK_NAME not between", value1, value2, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdIsNull() {
            addCriterion("REALITY_AG_ID is null");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdIsNotNull() {
            addCriterion("REALITY_AG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdEqualTo(String value) {
            addCriterion("REALITY_AG_ID =", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdNotEqualTo(String value) {
            addCriterion("REALITY_AG_ID <>", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdGreaterThan(String value) {
            addCriterion("REALITY_AG_ID >", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdGreaterThanOrEqualTo(String value) {
            addCriterion("REALITY_AG_ID >=", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdLessThan(String value) {
            addCriterion("REALITY_AG_ID <", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdLessThanOrEqualTo(String value) {
            addCriterion("REALITY_AG_ID <=", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdLike(String value) {
            addCriterion("REALITY_AG_ID like", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdNotLike(String value) {
            addCriterion("REALITY_AG_ID not like", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdIn(List<String> values) {
            addCriterion("REALITY_AG_ID in", values, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdNotIn(List<String> values) {
            addCriterion("REALITY_AG_ID not in", values, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdBetween(String value1, String value2) {
            addCriterion("REALITY_AG_ID between", value1, value2, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdNotBetween(String value1, String value2) {
            addCriterion("REALITY_AG_ID not between", value1, value2, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameIsNull() {
            addCriterion("REALITY_AG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameIsNotNull() {
            addCriterion("REALITY_AG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameEqualTo(String value) {
            addCriterion("REALITY_AG_NAME =", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameNotEqualTo(String value) {
            addCriterion("REALITY_AG_NAME <>", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameGreaterThan(String value) {
            addCriterion("REALITY_AG_NAME >", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameGreaterThanOrEqualTo(String value) {
            addCriterion("REALITY_AG_NAME >=", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameLessThan(String value) {
            addCriterion("REALITY_AG_NAME <", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameLessThanOrEqualTo(String value) {
            addCriterion("REALITY_AG_NAME <=", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameLike(String value) {
            addCriterion("REALITY_AG_NAME like", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameNotLike(String value) {
            addCriterion("REALITY_AG_NAME not like", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameIn(List<String> values) {
            addCriterion("REALITY_AG_NAME in", values, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameNotIn(List<String> values) {
            addCriterion("REALITY_AG_NAME not in", values, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameBetween(String value1, String value2) {
            addCriterion("REALITY_AG_NAME between", value1, value2, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameNotBetween(String value1, String value2) {
            addCriterion("REALITY_AG_NAME not between", value1, value2, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("END_TIME is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("END_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(String value) {
            addCriterion("END_TIME =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(String value) {
            addCriterion("END_TIME <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(String value) {
            addCriterion("END_TIME >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("END_TIME >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(String value) {
            addCriterion("END_TIME <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(String value) {
            addCriterion("END_TIME <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLike(String value) {
            addCriterion("END_TIME like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotLike(String value) {
            addCriterion("END_TIME not like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<String> values) {
            addCriterion("END_TIME in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<String> values) {
            addCriterion("END_TIME not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(String value1, String value2) {
            addCriterion("END_TIME between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(String value1, String value2) {
            addCriterion("END_TIME not between", value1, value2, "endTime");
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