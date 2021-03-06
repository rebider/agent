package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PProfitFactorExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PProfitFactorExample() {
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

        public Criteria andFactorMonthIsNull() {
            addCriterion("FACTOR_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andFactorMonthIsNotNull() {
            addCriterion("FACTOR_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andFactorMonthEqualTo(String value) {
            addCriterion("FACTOR_MONTH =", value, "factorMonth");
            return (Criteria) this;
        }

        public Criteria andFactorMonthNotEqualTo(String value) {
            addCriterion("FACTOR_MONTH <>", value, "factorMonth");
            return (Criteria) this;
        }

        public Criteria andFactorMonthGreaterThan(String value) {
            addCriterion("FACTOR_MONTH >", value, "factorMonth");
            return (Criteria) this;
        }

        public Criteria andFactorMonthGreaterThanOrEqualTo(String value) {
            addCriterion("FACTOR_MONTH >=", value, "factorMonth");
            return (Criteria) this;
        }

        public Criteria andFactorMonthLessThan(String value) {
            addCriterion("FACTOR_MONTH <", value, "factorMonth");
            return (Criteria) this;
        }

        public Criteria andFactorMonthLessThanOrEqualTo(String value) {
            addCriterion("FACTOR_MONTH <=", value, "factorMonth");
            return (Criteria) this;
        }

        public Criteria andFactorMonthLike(String value) {
            addCriterion("FACTOR_MONTH like", value, "factorMonth");
            return (Criteria) this;
        }

        public Criteria andFactorMonthNotLike(String value) {
            addCriterion("FACTOR_MONTH not like", value, "factorMonth");
            return (Criteria) this;
        }

        public Criteria andFactorMonthIn(List<String> values) {
            addCriterion("FACTOR_MONTH in", values, "factorMonth");
            return (Criteria) this;
        }

        public Criteria andFactorMonthNotIn(List<String> values) {
            addCriterion("FACTOR_MONTH not in", values, "factorMonth");
            return (Criteria) this;
        }

        public Criteria andFactorMonthBetween(String value1, String value2) {
            addCriterion("FACTOR_MONTH between", value1, value2, "factorMonth");
            return (Criteria) this;
        }

        public Criteria andFactorMonthNotBetween(String value1, String value2) {
            addCriterion("FACTOR_MONTH not between", value1, value2, "factorMonth");
            return (Criteria) this;
        }

        public Criteria andTatolAmtIsNull() {
            addCriterion("TATOL_AMT is null");
            return (Criteria) this;
        }

        public Criteria andTatolAmtIsNotNull() {
            addCriterion("TATOL_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andTatolAmtEqualTo(BigDecimal value) {
            addCriterion("TATOL_AMT =", value, "tatolAmt");
            return (Criteria) this;
        }

        public Criteria andTatolAmtNotEqualTo(BigDecimal value) {
            addCriterion("TATOL_AMT <>", value, "tatolAmt");
            return (Criteria) this;
        }

        public Criteria andTatolAmtGreaterThan(BigDecimal value) {
            addCriterion("TATOL_AMT >", value, "tatolAmt");
            return (Criteria) this;
        }

        public Criteria andTatolAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TATOL_AMT >=", value, "tatolAmt");
            return (Criteria) this;
        }

        public Criteria andTatolAmtLessThan(BigDecimal value) {
            addCriterion("TATOL_AMT <", value, "tatolAmt");
            return (Criteria) this;
        }

        public Criteria andTatolAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TATOL_AMT <=", value, "tatolAmt");
            return (Criteria) this;
        }

        public Criteria andTatolAmtIn(List<BigDecimal> values) {
            addCriterion("TATOL_AMT in", values, "tatolAmt");
            return (Criteria) this;
        }

        public Criteria andTatolAmtNotIn(List<BigDecimal> values) {
            addCriterion("TATOL_AMT not in", values, "tatolAmt");
            return (Criteria) this;
        }

        public Criteria andTatolAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TATOL_AMT between", value1, value2, "tatolAmt");
            return (Criteria) this;
        }

        public Criteria andTatolAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TATOL_AMT not between", value1, value2, "tatolAmt");
            return (Criteria) this;
        }

        public Criteria andBuckleAmtIsNull() {
            addCriterion("BUCKLE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andBuckleAmtIsNotNull() {
            addCriterion("BUCKLE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andBuckleAmtEqualTo(BigDecimal value) {
            addCriterion("BUCKLE_AMT =", value, "buckleAmt");
            return (Criteria) this;
        }

        public Criteria andBuckleAmtNotEqualTo(BigDecimal value) {
            addCriterion("BUCKLE_AMT <>", value, "buckleAmt");
            return (Criteria) this;
        }

        public Criteria andBuckleAmtGreaterThan(BigDecimal value) {
            addCriterion("BUCKLE_AMT >", value, "buckleAmt");
            return (Criteria) this;
        }

        public Criteria andBuckleAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BUCKLE_AMT >=", value, "buckleAmt");
            return (Criteria) this;
        }

        public Criteria andBuckleAmtLessThan(BigDecimal value) {
            addCriterion("BUCKLE_AMT <", value, "buckleAmt");
            return (Criteria) this;
        }

        public Criteria andBuckleAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BUCKLE_AMT <=", value, "buckleAmt");
            return (Criteria) this;
        }

        public Criteria andBuckleAmtIn(List<BigDecimal> values) {
            addCriterion("BUCKLE_AMT in", values, "buckleAmt");
            return (Criteria) this;
        }

        public Criteria andBuckleAmtNotIn(List<BigDecimal> values) {
            addCriterion("BUCKLE_AMT not in", values, "buckleAmt");
            return (Criteria) this;
        }

        public Criteria andBuckleAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUCKLE_AMT between", value1, value2, "buckleAmt");
            return (Criteria) this;
        }

        public Criteria andBuckleAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUCKLE_AMT not between", value1, value2, "buckleAmt");
            return (Criteria) this;
        }

        public Criteria andSurplusAmtIsNull() {
            addCriterion("SURPLUS_AMT is null");
            return (Criteria) this;
        }

        public Criteria andSurplusAmtIsNotNull() {
            addCriterion("SURPLUS_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andSurplusAmtEqualTo(BigDecimal value) {
            addCriterion("SURPLUS_AMT =", value, "surplusAmt");
            return (Criteria) this;
        }

        public Criteria andSurplusAmtNotEqualTo(BigDecimal value) {
            addCriterion("SURPLUS_AMT <>", value, "surplusAmt");
            return (Criteria) this;
        }

        public Criteria andSurplusAmtGreaterThan(BigDecimal value) {
            addCriterion("SURPLUS_AMT >", value, "surplusAmt");
            return (Criteria) this;
        }

        public Criteria andSurplusAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SURPLUS_AMT >=", value, "surplusAmt");
            return (Criteria) this;
        }

        public Criteria andSurplusAmtLessThan(BigDecimal value) {
            addCriterion("SURPLUS_AMT <", value, "surplusAmt");
            return (Criteria) this;
        }

        public Criteria andSurplusAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SURPLUS_AMT <=", value, "surplusAmt");
            return (Criteria) this;
        }

        public Criteria andSurplusAmtIn(List<BigDecimal> values) {
            addCriterion("SURPLUS_AMT in", values, "surplusAmt");
            return (Criteria) this;
        }

        public Criteria andSurplusAmtNotIn(List<BigDecimal> values) {
            addCriterion("SURPLUS_AMT not in", values, "surplusAmt");
            return (Criteria) this;
        }

        public Criteria andSurplusAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SURPLUS_AMT between", value1, value2, "surplusAmt");
            return (Criteria) this;
        }

        public Criteria andSurplusAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SURPLUS_AMT not between", value1, value2, "surplusAmt");
            return (Criteria) this;
        }

        public Criteria andSumDeductionAmtIsNull() {
            addCriterion("SUM_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andSumDeductionAmtIsNotNull() {
            addCriterion("SUM_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andSumDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("SUM_DEDUCTION_AMT =", value, "sumDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andSumDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("SUM_DEDUCTION_AMT <>", value, "sumDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andSumDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("SUM_DEDUCTION_AMT >", value, "sumDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andSumDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUM_DEDUCTION_AMT >=", value, "sumDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andSumDeductionAmtLessThan(BigDecimal value) {
            addCriterion("SUM_DEDUCTION_AMT <", value, "sumDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andSumDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUM_DEDUCTION_AMT <=", value, "sumDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andSumDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("SUM_DEDUCTION_AMT in", values, "sumDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andSumDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("SUM_DEDUCTION_AMT not in", values, "sumDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andSumDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUM_DEDUCTION_AMT between", value1, value2, "sumDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andSumDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUM_DEDUCTION_AMT not between", value1, value2, "sumDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andAddDeductionAmtIsNull() {
            addCriterion("ADD_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andAddDeductionAmtIsNotNull() {
            addCriterion("ADD_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andAddDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("ADD_DEDUCTION_AMT =", value, "addDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andAddDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("ADD_DEDUCTION_AMT <>", value, "addDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andAddDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("ADD_DEDUCTION_AMT >", value, "addDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andAddDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ADD_DEDUCTION_AMT >=", value, "addDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andAddDeductionAmtLessThan(BigDecimal value) {
            addCriterion("ADD_DEDUCTION_AMT <", value, "addDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andAddDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ADD_DEDUCTION_AMT <=", value, "addDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andAddDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("ADD_DEDUCTION_AMT in", values, "addDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andAddDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("ADD_DEDUCTION_AMT not in", values, "addDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andAddDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADD_DEDUCTION_AMT between", value1, value2, "addDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andAddDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADD_DEDUCTION_AMT not between", value1, value2, "addDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMustDeductionAmtIsNull() {
            addCriterion("MUST_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andMustDeductionAmtIsNotNull() {
            addCriterion("MUST_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andMustDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("MUST_DEDUCTION_AMT =", value, "mustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMustDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("MUST_DEDUCTION_AMT <>", value, "mustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMustDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("MUST_DEDUCTION_AMT >", value, "mustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMustDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MUST_DEDUCTION_AMT >=", value, "mustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMustDeductionAmtLessThan(BigDecimal value) {
            addCriterion("MUST_DEDUCTION_AMT <", value, "mustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMustDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MUST_DEDUCTION_AMT <=", value, "mustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMustDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("MUST_DEDUCTION_AMT in", values, "mustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMustDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("MUST_DEDUCTION_AMT not in", values, "mustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMustDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MUST_DEDUCTION_AMT between", value1, value2, "mustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMustDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MUST_DEDUCTION_AMT not between", value1, value2, "mustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andActualDeductionAmtIsNull() {
            addCriterion("ACTUAL_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andActualDeductionAmtIsNotNull() {
            addCriterion("ACTUAL_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andActualDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("ACTUAL_DEDUCTION_AMT =", value, "actualDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andActualDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("ACTUAL_DEDUCTION_AMT <>", value, "actualDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andActualDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("ACTUAL_DEDUCTION_AMT >", value, "actualDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andActualDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTUAL_DEDUCTION_AMT >=", value, "actualDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andActualDeductionAmtLessThan(BigDecimal value) {
            addCriterion("ACTUAL_DEDUCTION_AMT <", value, "actualDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andActualDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTUAL_DEDUCTION_AMT <=", value, "actualDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andActualDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("ACTUAL_DEDUCTION_AMT in", values, "actualDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andActualDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("ACTUAL_DEDUCTION_AMT not in", values, "actualDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andActualDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTUAL_DEDUCTION_AMT between", value1, value2, "actualDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andActualDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTUAL_DEDUCTION_AMT not between", value1, value2, "actualDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionAmtIsNull() {
            addCriterion("NOT_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andNotDeductionAmtIsNotNull() {
            addCriterion("NOT_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andNotDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("NOT_DEDUCTION_AMT =", value, "notDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("NOT_DEDUCTION_AMT <>", value, "notDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("NOT_DEDUCTION_AMT >", value, "notDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NOT_DEDUCTION_AMT >=", value, "notDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionAmtLessThan(BigDecimal value) {
            addCriterion("NOT_DEDUCTION_AMT <", value, "notDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NOT_DEDUCTION_AMT <=", value, "notDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("NOT_DEDUCTION_AMT in", values, "notDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("NOT_DEDUCTION_AMT not in", values, "notDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NOT_DEDUCTION_AMT between", value1, value2, "notDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NOT_DEDUCTION_AMT not between", value1, value2, "notDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andUpperNotDeductionAmtIsNull() {
            addCriterion("UPPER_NOT_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andUpperNotDeductionAmtIsNotNull() {
            addCriterion("UPPER_NOT_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andUpperNotDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("UPPER_NOT_DEDUCTION_AMT =", value, "upperNotDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andUpperNotDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("UPPER_NOT_DEDUCTION_AMT <>", value, "upperNotDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andUpperNotDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("UPPER_NOT_DEDUCTION_AMT >", value, "upperNotDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andUpperNotDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("UPPER_NOT_DEDUCTION_AMT >=", value, "upperNotDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andUpperNotDeductionAmtLessThan(BigDecimal value) {
            addCriterion("UPPER_NOT_DEDUCTION_AMT <", value, "upperNotDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andUpperNotDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("UPPER_NOT_DEDUCTION_AMT <=", value, "upperNotDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andUpperNotDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("UPPER_NOT_DEDUCTION_AMT in", values, "upperNotDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andUpperNotDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("UPPER_NOT_DEDUCTION_AMT not in", values, "upperNotDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andUpperNotDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("UPPER_NOT_DEDUCTION_AMT between", value1, value2, "upperNotDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andUpperNotDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("UPPER_NOT_DEDUCTION_AMT not between", value1, value2, "upperNotDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionStatusIsNull() {
            addCriterion("DEDUCTION_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andDeductionStatusIsNotNull() {
            addCriterion("DEDUCTION_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andDeductionStatusEqualTo(String value) {
            addCriterion("DEDUCTION_STATUS =", value, "deductionStatus");
            return (Criteria) this;
        }

        public Criteria andDeductionStatusNotEqualTo(String value) {
            addCriterion("DEDUCTION_STATUS <>", value, "deductionStatus");
            return (Criteria) this;
        }

        public Criteria andDeductionStatusGreaterThan(String value) {
            addCriterion("DEDUCTION_STATUS >", value, "deductionStatus");
            return (Criteria) this;
        }

        public Criteria andDeductionStatusGreaterThanOrEqualTo(String value) {
            addCriterion("DEDUCTION_STATUS >=", value, "deductionStatus");
            return (Criteria) this;
        }

        public Criteria andDeductionStatusLessThan(String value) {
            addCriterion("DEDUCTION_STATUS <", value, "deductionStatus");
            return (Criteria) this;
        }

        public Criteria andDeductionStatusLessThanOrEqualTo(String value) {
            addCriterion("DEDUCTION_STATUS <=", value, "deductionStatus");
            return (Criteria) this;
        }

        public Criteria andDeductionStatusLike(String value) {
            addCriterion("DEDUCTION_STATUS like", value, "deductionStatus");
            return (Criteria) this;
        }

        public Criteria andDeductionStatusNotLike(String value) {
            addCriterion("DEDUCTION_STATUS not like", value, "deductionStatus");
            return (Criteria) this;
        }

        public Criteria andDeductionStatusIn(List<String> values) {
            addCriterion("DEDUCTION_STATUS in", values, "deductionStatus");
            return (Criteria) this;
        }

        public Criteria andDeductionStatusNotIn(List<String> values) {
            addCriterion("DEDUCTION_STATUS not in", values, "deductionStatus");
            return (Criteria) this;
        }

        public Criteria andDeductionStatusBetween(String value1, String value2) {
            addCriterion("DEDUCTION_STATUS between", value1, value2, "deductionStatus");
            return (Criteria) this;
        }

        public Criteria andDeductionStatusNotBetween(String value1, String value2) {
            addCriterion("DEDUCTION_STATUS not between", value1, value2, "deductionStatus");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("UPDATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("UPDATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("UPDATE_DATE =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("UPDATE_DATE <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("UPDATE_DATE >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_DATE >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("UPDATE_DATE <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_DATE <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("UPDATE_DATE in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("UPDATE_DATE not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("UPDATE_DATE between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_DATE not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andFactorDateIsNull() {
            addCriterion("FACTOR_DATE is null");
            return (Criteria) this;
        }

        public Criteria andFactorDateIsNotNull() {
            addCriterion("FACTOR_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andFactorDateEqualTo(Date value) {
            addCriterion("FACTOR_DATE =", value, "factorDate");
            return (Criteria) this;
        }

        public Criteria andFactorDateNotEqualTo(Date value) {
            addCriterion("FACTOR_DATE <>", value, "factorDate");
            return (Criteria) this;
        }

        public Criteria andFactorDateGreaterThan(Date value) {
            addCriterion("FACTOR_DATE >", value, "factorDate");
            return (Criteria) this;
        }

        public Criteria andFactorDateGreaterThanOrEqualTo(Date value) {
            addCriterion("FACTOR_DATE >=", value, "factorDate");
            return (Criteria) this;
        }

        public Criteria andFactorDateLessThan(Date value) {
            addCriterion("FACTOR_DATE <", value, "factorDate");
            return (Criteria) this;
        }

        public Criteria andFactorDateLessThanOrEqualTo(Date value) {
            addCriterion("FACTOR_DATE <=", value, "factorDate");
            return (Criteria) this;
        }

        public Criteria andFactorDateIn(List<Date> values) {
            addCriterion("FACTOR_DATE in", values, "factorDate");
            return (Criteria) this;
        }

        public Criteria andFactorDateNotIn(List<Date> values) {
            addCriterion("FACTOR_DATE not in", values, "factorDate");
            return (Criteria) this;
        }

        public Criteria andFactorDateBetween(Date value1, Date value2) {
            addCriterion("FACTOR_DATE between", value1, value2, "factorDate");
            return (Criteria) this;
        }

        public Criteria andFactorDateNotBetween(Date value1, Date value2) {
            addCriterion("FACTOR_DATE not between", value1, value2, "factorDate");
            return (Criteria) this;
        }

        public Criteria andStagingStatusIsNull() {
            addCriterion("STAGING_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStagingStatusIsNotNull() {
            addCriterion("STAGING_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStagingStatusEqualTo(String value) {
            addCriterion("STAGING_STATUS =", value, "stagingStatus");
            return (Criteria) this;
        }

        public Criteria andStagingStatusNotEqualTo(String value) {
            addCriterion("STAGING_STATUS <>", value, "stagingStatus");
            return (Criteria) this;
        }

        public Criteria andStagingStatusGreaterThan(String value) {
            addCriterion("STAGING_STATUS >", value, "stagingStatus");
            return (Criteria) this;
        }

        public Criteria andStagingStatusGreaterThanOrEqualTo(String value) {
            addCriterion("STAGING_STATUS >=", value, "stagingStatus");
            return (Criteria) this;
        }

        public Criteria andStagingStatusLessThan(String value) {
            addCriterion("STAGING_STATUS <", value, "stagingStatus");
            return (Criteria) this;
        }

        public Criteria andStagingStatusLessThanOrEqualTo(String value) {
            addCriterion("STAGING_STATUS <=", value, "stagingStatus");
            return (Criteria) this;
        }

        public Criteria andStagingStatusLike(String value) {
            addCriterion("STAGING_STATUS like", value, "stagingStatus");
            return (Criteria) this;
        }

        public Criteria andStagingStatusNotLike(String value) {
            addCriterion("STAGING_STATUS not like", value, "stagingStatus");
            return (Criteria) this;
        }

        public Criteria andStagingStatusIn(List<String> values) {
            addCriterion("STAGING_STATUS in", values, "stagingStatus");
            return (Criteria) this;
        }

        public Criteria andStagingStatusNotIn(List<String> values) {
            addCriterion("STAGING_STATUS not in", values, "stagingStatus");
            return (Criteria) this;
        }

        public Criteria andStagingStatusBetween(String value1, String value2) {
            addCriterion("STAGING_STATUS between", value1, value2, "stagingStatus");
            return (Criteria) this;
        }

        public Criteria andStagingStatusNotBetween(String value1, String value2) {
            addCriterion("STAGING_STATUS not between", value1, value2, "stagingStatus");
            return (Criteria) this;
        }

        public Criteria andDeductionDescIsNull() {
            addCriterion("DEDUCTION_DESC is null");
            return (Criteria) this;
        }

        public Criteria andDeductionDescIsNotNull() {
            addCriterion("DEDUCTION_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andDeductionDescEqualTo(String value) {
            addCriterion("DEDUCTION_DESC =", value, "deductionDesc");
            return (Criteria) this;
        }

        public Criteria andDeductionDescNotEqualTo(String value) {
            addCriterion("DEDUCTION_DESC <>", value, "deductionDesc");
            return (Criteria) this;
        }

        public Criteria andDeductionDescGreaterThan(String value) {
            addCriterion("DEDUCTION_DESC >", value, "deductionDesc");
            return (Criteria) this;
        }

        public Criteria andDeductionDescGreaterThanOrEqualTo(String value) {
            addCriterion("DEDUCTION_DESC >=", value, "deductionDesc");
            return (Criteria) this;
        }

        public Criteria andDeductionDescLessThan(String value) {
            addCriterion("DEDUCTION_DESC <", value, "deductionDesc");
            return (Criteria) this;
        }

        public Criteria andDeductionDescLessThanOrEqualTo(String value) {
            addCriterion("DEDUCTION_DESC <=", value, "deductionDesc");
            return (Criteria) this;
        }

        public Criteria andDeductionDescLike(String value) {
            addCriterion("DEDUCTION_DESC like", value, "deductionDesc");
            return (Criteria) this;
        }

        public Criteria andDeductionDescNotLike(String value) {
            addCriterion("DEDUCTION_DESC not like", value, "deductionDesc");
            return (Criteria) this;
        }

        public Criteria andDeductionDescIn(List<String> values) {
            addCriterion("DEDUCTION_DESC in", values, "deductionDesc");
            return (Criteria) this;
        }

        public Criteria andDeductionDescNotIn(List<String> values) {
            addCriterion("DEDUCTION_DESC not in", values, "deductionDesc");
            return (Criteria) this;
        }

        public Criteria andDeductionDescBetween(String value1, String value2) {
            addCriterion("DEDUCTION_DESC between", value1, value2, "deductionDesc");
            return (Criteria) this;
        }

        public Criteria andDeductionDescNotBetween(String value1, String value2) {
            addCriterion("DEDUCTION_DESC not between", value1, value2, "deductionDesc");
            return (Criteria) this;
        }

        public Criteria andNextIdIsNull() {
            addCriterion("NEXT_ID is null");
            return (Criteria) this;
        }

        public Criteria andNextIdIsNotNull() {
            addCriterion("NEXT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andNextIdEqualTo(String value) {
            addCriterion("NEXT_ID =", value, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdNotEqualTo(String value) {
            addCriterion("NEXT_ID <>", value, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdGreaterThan(String value) {
            addCriterion("NEXT_ID >", value, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdGreaterThanOrEqualTo(String value) {
            addCriterion("NEXT_ID >=", value, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdLessThan(String value) {
            addCriterion("NEXT_ID <", value, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdLessThanOrEqualTo(String value) {
            addCriterion("NEXT_ID <=", value, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdLike(String value) {
            addCriterion("NEXT_ID like", value, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdNotLike(String value) {
            addCriterion("NEXT_ID not like", value, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdIn(List<String> values) {
            addCriterion("NEXT_ID in", values, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdNotIn(List<String> values) {
            addCriterion("NEXT_ID not in", values, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdBetween(String value1, String value2) {
            addCriterion("NEXT_ID between", value1, value2, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdNotBetween(String value1, String value2) {
            addCriterion("NEXT_ID not between", value1, value2, "nextId");
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