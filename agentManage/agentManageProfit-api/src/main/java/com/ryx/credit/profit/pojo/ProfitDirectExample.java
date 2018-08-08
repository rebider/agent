package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProfitDirectExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ProfitDirectExample() {
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

        public Criteria andFristAgentPidIsNull() {
            addCriterion("FRIST_AGENT_PID is null");
            return (Criteria) this;
        }

        public Criteria andFristAgentPidIsNotNull() {
            addCriterion("FRIST_AGENT_PID is not null");
            return (Criteria) this;
        }

        public Criteria andFristAgentPidEqualTo(String value) {
            addCriterion("FRIST_AGENT_PID =", value, "fristAgentPid");
            return (Criteria) this;
        }

        public Criteria andFristAgentPidNotEqualTo(String value) {
            addCriterion("FRIST_AGENT_PID <>", value, "fristAgentPid");
            return (Criteria) this;
        }

        public Criteria andFristAgentPidGreaterThan(String value) {
            addCriterion("FRIST_AGENT_PID >", value, "fristAgentPid");
            return (Criteria) this;
        }

        public Criteria andFristAgentPidGreaterThanOrEqualTo(String value) {
            addCriterion("FRIST_AGENT_PID >=", value, "fristAgentPid");
            return (Criteria) this;
        }

        public Criteria andFristAgentPidLessThan(String value) {
            addCriterion("FRIST_AGENT_PID <", value, "fristAgentPid");
            return (Criteria) this;
        }

        public Criteria andFristAgentPidLessThanOrEqualTo(String value) {
            addCriterion("FRIST_AGENT_PID <=", value, "fristAgentPid");
            return (Criteria) this;
        }

        public Criteria andFristAgentPidLike(String value) {
            addCriterion("FRIST_AGENT_PID like", value, "fristAgentPid");
            return (Criteria) this;
        }

        public Criteria andFristAgentPidNotLike(String value) {
            addCriterion("FRIST_AGENT_PID not like", value, "fristAgentPid");
            return (Criteria) this;
        }

        public Criteria andFristAgentPidIn(List<String> values) {
            addCriterion("FRIST_AGENT_PID in", values, "fristAgentPid");
            return (Criteria) this;
        }

        public Criteria andFristAgentPidNotIn(List<String> values) {
            addCriterion("FRIST_AGENT_PID not in", values, "fristAgentPid");
            return (Criteria) this;
        }

        public Criteria andFristAgentPidBetween(String value1, String value2) {
            addCriterion("FRIST_AGENT_PID between", value1, value2, "fristAgentPid");
            return (Criteria) this;
        }

        public Criteria andFristAgentPidNotBetween(String value1, String value2) {
            addCriterion("FRIST_AGENT_PID not between", value1, value2, "fristAgentPid");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameIsNull() {
            addCriterion("FRIST_AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameIsNotNull() {
            addCriterion("FRIST_AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameEqualTo(String value) {
            addCriterion("FRIST_AGENT_NAME =", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameNotEqualTo(String value) {
            addCriterion("FRIST_AGENT_NAME <>", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameGreaterThan(String value) {
            addCriterion("FRIST_AGENT_NAME >", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("FRIST_AGENT_NAME >=", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameLessThan(String value) {
            addCriterion("FRIST_AGENT_NAME <", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameLessThanOrEqualTo(String value) {
            addCriterion("FRIST_AGENT_NAME <=", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameLike(String value) {
            addCriterion("FRIST_AGENT_NAME like", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameNotLike(String value) {
            addCriterion("FRIST_AGENT_NAME not like", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameIn(List<String> values) {
            addCriterion("FRIST_AGENT_NAME in", values, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameNotIn(List<String> values) {
            addCriterion("FRIST_AGENT_NAME not in", values, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameBetween(String value1, String value2) {
            addCriterion("FRIST_AGENT_NAME between", value1, value2, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameNotBetween(String value1, String value2) {
            addCriterion("FRIST_AGENT_NAME not between", value1, value2, "fristAgentName");
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

        public Criteria andTransAmtIsNull() {
            addCriterion("TRANS_AMT is null");
            return (Criteria) this;
        }

        public Criteria andTransAmtIsNotNull() {
            addCriterion("TRANS_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andTransAmtEqualTo(BigDecimal value) {
            addCriterion("TRANS_AMT =", value, "transAmt");
            return (Criteria) this;
        }

        public Criteria andTransAmtNotEqualTo(BigDecimal value) {
            addCriterion("TRANS_AMT <>", value, "transAmt");
            return (Criteria) this;
        }

        public Criteria andTransAmtGreaterThan(BigDecimal value) {
            addCriterion("TRANS_AMT >", value, "transAmt");
            return (Criteria) this;
        }

        public Criteria andTransAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_AMT >=", value, "transAmt");
            return (Criteria) this;
        }

        public Criteria andTransAmtLessThan(BigDecimal value) {
            addCriterion("TRANS_AMT <", value, "transAmt");
            return (Criteria) this;
        }

        public Criteria andTransAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_AMT <=", value, "transAmt");
            return (Criteria) this;
        }

        public Criteria andTransAmtIn(List<BigDecimal> values) {
            addCriterion("TRANS_AMT in", values, "transAmt");
            return (Criteria) this;
        }

        public Criteria andTransAmtNotIn(List<BigDecimal> values) {
            addCriterion("TRANS_AMT not in", values, "transAmt");
            return (Criteria) this;
        }

        public Criteria andTransAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_AMT between", value1, value2, "transAmt");
            return (Criteria) this;
        }

        public Criteria andTransAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_AMT not between", value1, value2, "transAmt");
            return (Criteria) this;
        }

        public Criteria andTransFeeIsNull() {
            addCriterion("TRANS_FEE is null");
            return (Criteria) this;
        }

        public Criteria andTransFeeIsNotNull() {
            addCriterion("TRANS_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andTransFeeEqualTo(BigDecimal value) {
            addCriterion("TRANS_FEE =", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeNotEqualTo(BigDecimal value) {
            addCriterion("TRANS_FEE <>", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeGreaterThan(BigDecimal value) {
            addCriterion("TRANS_FEE >", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_FEE >=", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeLessThan(BigDecimal value) {
            addCriterion("TRANS_FEE <", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_FEE <=", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeIn(List<BigDecimal> values) {
            addCriterion("TRANS_FEE in", values, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeNotIn(List<BigDecimal> values) {
            addCriterion("TRANS_FEE not in", values, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_FEE between", value1, value2, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_FEE not between", value1, value2, "transFee");
            return (Criteria) this;
        }

        public Criteria andProfitAmtIsNull() {
            addCriterion("PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andProfitAmtIsNotNull() {
            addCriterion("PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andProfitAmtEqualTo(BigDecimal value) {
            addCriterion("PROFIT_AMT =", value, "profitAmt");
            return (Criteria) this;
        }

        public Criteria andProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("PROFIT_AMT <>", value, "profitAmt");
            return (Criteria) this;
        }

        public Criteria andProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("PROFIT_AMT >", value, "profitAmt");
            return (Criteria) this;
        }

        public Criteria andProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_AMT >=", value, "profitAmt");
            return (Criteria) this;
        }

        public Criteria andProfitAmtLessThan(BigDecimal value) {
            addCriterion("PROFIT_AMT <", value, "profitAmt");
            return (Criteria) this;
        }

        public Criteria andProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_AMT <=", value, "profitAmt");
            return (Criteria) this;
        }

        public Criteria andProfitAmtIn(List<BigDecimal> values) {
            addCriterion("PROFIT_AMT in", values, "profitAmt");
            return (Criteria) this;
        }

        public Criteria andProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("PROFIT_AMT not in", values, "profitAmt");
            return (Criteria) this;
        }

        public Criteria andProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_AMT between", value1, value2, "profitAmt");
            return (Criteria) this;
        }

        public Criteria andProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_AMT not between", value1, value2, "profitAmt");
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

        public Criteria andShouldProfitIsNull() {
            addCriterion("SHOULD_PROFIT is null");
            return (Criteria) this;
        }

        public Criteria andShouldProfitIsNotNull() {
            addCriterion("SHOULD_PROFIT is not null");
            return (Criteria) this;
        }

        public Criteria andShouldProfitEqualTo(BigDecimal value) {
            addCriterion("SHOULD_PROFIT =", value, "shouldProfit");
            return (Criteria) this;
        }

        public Criteria andShouldProfitNotEqualTo(BigDecimal value) {
            addCriterion("SHOULD_PROFIT <>", value, "shouldProfit");
            return (Criteria) this;
        }

        public Criteria andShouldProfitGreaterThan(BigDecimal value) {
            addCriterion("SHOULD_PROFIT >", value, "shouldProfit");
            return (Criteria) this;
        }

        public Criteria andShouldProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SHOULD_PROFIT >=", value, "shouldProfit");
            return (Criteria) this;
        }

        public Criteria andShouldProfitLessThan(BigDecimal value) {
            addCriterion("SHOULD_PROFIT <", value, "shouldProfit");
            return (Criteria) this;
        }

        public Criteria andShouldProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SHOULD_PROFIT <=", value, "shouldProfit");
            return (Criteria) this;
        }

        public Criteria andShouldProfitIn(List<BigDecimal> values) {
            addCriterion("SHOULD_PROFIT in", values, "shouldProfit");
            return (Criteria) this;
        }

        public Criteria andShouldProfitNotIn(List<BigDecimal> values) {
            addCriterion("SHOULD_PROFIT not in", values, "shouldProfit");
            return (Criteria) this;
        }

        public Criteria andShouldProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SHOULD_PROFIT between", value1, value2, "shouldProfit");
            return (Criteria) this;
        }

        public Criteria andShouldProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SHOULD_PROFIT not between", value1, value2, "shouldProfit");
            return (Criteria) this;
        }

        public Criteria andActualProfitIsNull() {
            addCriterion("ACTUAL_PROFIT is null");
            return (Criteria) this;
        }

        public Criteria andActualProfitIsNotNull() {
            addCriterion("ACTUAL_PROFIT is not null");
            return (Criteria) this;
        }

        public Criteria andActualProfitEqualTo(BigDecimal value) {
            addCriterion("ACTUAL_PROFIT =", value, "actualProfit");
            return (Criteria) this;
        }

        public Criteria andActualProfitNotEqualTo(BigDecimal value) {
            addCriterion("ACTUAL_PROFIT <>", value, "actualProfit");
            return (Criteria) this;
        }

        public Criteria andActualProfitGreaterThan(BigDecimal value) {
            addCriterion("ACTUAL_PROFIT >", value, "actualProfit");
            return (Criteria) this;
        }

        public Criteria andActualProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTUAL_PROFIT >=", value, "actualProfit");
            return (Criteria) this;
        }

        public Criteria andActualProfitLessThan(BigDecimal value) {
            addCriterion("ACTUAL_PROFIT <", value, "actualProfit");
            return (Criteria) this;
        }

        public Criteria andActualProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTUAL_PROFIT <=", value, "actualProfit");
            return (Criteria) this;
        }

        public Criteria andActualProfitIn(List<BigDecimal> values) {
            addCriterion("ACTUAL_PROFIT in", values, "actualProfit");
            return (Criteria) this;
        }

        public Criteria andActualProfitNotIn(List<BigDecimal> values) {
            addCriterion("ACTUAL_PROFIT not in", values, "actualProfit");
            return (Criteria) this;
        }

        public Criteria andActualProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTUAL_PROFIT between", value1, value2, "actualProfit");
            return (Criteria) this;
        }

        public Criteria andActualProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTUAL_PROFIT not between", value1, value2, "actualProfit");
            return (Criteria) this;
        }

        public Criteria andTransMonthIsNull() {
            addCriterion("TRANS_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andTransMonthIsNotNull() {
            addCriterion("TRANS_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andTransMonthEqualTo(String value) {
            addCriterion("TRANS_MONTH =", value, "transMonth");
            return (Criteria) this;
        }

        public Criteria andTransMonthNotEqualTo(String value) {
            addCriterion("TRANS_MONTH <>", value, "transMonth");
            return (Criteria) this;
        }

        public Criteria andTransMonthGreaterThan(String value) {
            addCriterion("TRANS_MONTH >", value, "transMonth");
            return (Criteria) this;
        }

        public Criteria andTransMonthGreaterThanOrEqualTo(String value) {
            addCriterion("TRANS_MONTH >=", value, "transMonth");
            return (Criteria) this;
        }

        public Criteria andTransMonthLessThan(String value) {
            addCriterion("TRANS_MONTH <", value, "transMonth");
            return (Criteria) this;
        }

        public Criteria andTransMonthLessThanOrEqualTo(String value) {
            addCriterion("TRANS_MONTH <=", value, "transMonth");
            return (Criteria) this;
        }

        public Criteria andTransMonthLike(String value) {
            addCriterion("TRANS_MONTH like", value, "transMonth");
            return (Criteria) this;
        }

        public Criteria andTransMonthNotLike(String value) {
            addCriterion("TRANS_MONTH not like", value, "transMonth");
            return (Criteria) this;
        }

        public Criteria andTransMonthIn(List<String> values) {
            addCriterion("TRANS_MONTH in", values, "transMonth");
            return (Criteria) this;
        }

        public Criteria andTransMonthNotIn(List<String> values) {
            addCriterion("TRANS_MONTH not in", values, "transMonth");
            return (Criteria) this;
        }

        public Criteria andTransMonthBetween(String value1, String value2) {
            addCriterion("TRANS_MONTH between", value1, value2, "transMonth");
            return (Criteria) this;
        }

        public Criteria andTransMonthNotBetween(String value1, String value2) {
            addCriterion("TRANS_MONTH not between", value1, value2, "transMonth");
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