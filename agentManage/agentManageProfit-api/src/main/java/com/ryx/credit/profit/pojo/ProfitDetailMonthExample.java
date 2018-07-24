package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProfitDetailMonthExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ProfitDetailMonthExample() {
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

        public Criteria andTranAmtIsNull() {
            addCriterion("TRAN_AMT is null");
            return (Criteria) this;
        }

        public Criteria andTranAmtIsNotNull() {
            addCriterion("TRAN_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andTranAmtEqualTo(BigDecimal value) {
            addCriterion("TRAN_AMT =", value, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtNotEqualTo(BigDecimal value) {
            addCriterion("TRAN_AMT <>", value, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtGreaterThan(BigDecimal value) {
            addCriterion("TRAN_AMT >", value, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TRAN_AMT >=", value, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtLessThan(BigDecimal value) {
            addCriterion("TRAN_AMT <", value, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TRAN_AMT <=", value, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtIn(List<BigDecimal> values) {
            addCriterion("TRAN_AMT in", values, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtNotIn(List<BigDecimal> values) {
            addCriterion("TRAN_AMT not in", values, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRAN_AMT between", value1, value2, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRAN_AMT not between", value1, value2, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andPayAmtIsNull() {
            addCriterion("PAY_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPayAmtIsNotNull() {
            addCriterion("PAY_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPayAmtEqualTo(BigDecimal value) {
            addCriterion("PAY_AMT =", value, "payAmt");
            return (Criteria) this;
        }

        public Criteria andPayAmtNotEqualTo(BigDecimal value) {
            addCriterion("PAY_AMT <>", value, "payAmt");
            return (Criteria) this;
        }

        public Criteria andPayAmtGreaterThan(BigDecimal value) {
            addCriterion("PAY_AMT >", value, "payAmt");
            return (Criteria) this;
        }

        public Criteria andPayAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_AMT >=", value, "payAmt");
            return (Criteria) this;
        }

        public Criteria andPayAmtLessThan(BigDecimal value) {
            addCriterion("PAY_AMT <", value, "payAmt");
            return (Criteria) this;
        }

        public Criteria andPayAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_AMT <=", value, "payAmt");
            return (Criteria) this;
        }

        public Criteria andPayAmtIn(List<BigDecimal> values) {
            addCriterion("PAY_AMT in", values, "payAmt");
            return (Criteria) this;
        }

        public Criteria andPayAmtNotIn(List<BigDecimal> values) {
            addCriterion("PAY_AMT not in", values, "payAmt");
            return (Criteria) this;
        }

        public Criteria andPayAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_AMT between", value1, value2, "payAmt");
            return (Criteria) this;
        }

        public Criteria andPayAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_AMT not between", value1, value2, "payAmt");
            return (Criteria) this;
        }

        public Criteria andTranProfitScaleIsNull() {
            addCriterion("TRAN_PROFIT_SCALE is null");
            return (Criteria) this;
        }

        public Criteria andTranProfitScaleIsNotNull() {
            addCriterion("TRAN_PROFIT_SCALE is not null");
            return (Criteria) this;
        }

        public Criteria andTranProfitScaleEqualTo(String value) {
            addCriterion("TRAN_PROFIT_SCALE =", value, "tranProfitScale");
            return (Criteria) this;
        }

        public Criteria andTranProfitScaleNotEqualTo(String value) {
            addCriterion("TRAN_PROFIT_SCALE <>", value, "tranProfitScale");
            return (Criteria) this;
        }

        public Criteria andTranProfitScaleGreaterThan(String value) {
            addCriterion("TRAN_PROFIT_SCALE >", value, "tranProfitScale");
            return (Criteria) this;
        }

        public Criteria andTranProfitScaleGreaterThanOrEqualTo(String value) {
            addCriterion("TRAN_PROFIT_SCALE >=", value, "tranProfitScale");
            return (Criteria) this;
        }

        public Criteria andTranProfitScaleLessThan(String value) {
            addCriterion("TRAN_PROFIT_SCALE <", value, "tranProfitScale");
            return (Criteria) this;
        }

        public Criteria andTranProfitScaleLessThanOrEqualTo(String value) {
            addCriterion("TRAN_PROFIT_SCALE <=", value, "tranProfitScale");
            return (Criteria) this;
        }

        public Criteria andTranProfitScaleLike(String value) {
            addCriterion("TRAN_PROFIT_SCALE like", value, "tranProfitScale");
            return (Criteria) this;
        }

        public Criteria andTranProfitScaleNotLike(String value) {
            addCriterion("TRAN_PROFIT_SCALE not like", value, "tranProfitScale");
            return (Criteria) this;
        }

        public Criteria andTranProfitScaleIn(List<String> values) {
            addCriterion("TRAN_PROFIT_SCALE in", values, "tranProfitScale");
            return (Criteria) this;
        }

        public Criteria andTranProfitScaleNotIn(List<String> values) {
            addCriterion("TRAN_PROFIT_SCALE not in", values, "tranProfitScale");
            return (Criteria) this;
        }

        public Criteria andTranProfitScaleBetween(String value1, String value2) {
            addCriterion("TRAN_PROFIT_SCALE between", value1, value2, "tranProfitScale");
            return (Criteria) this;
        }

        public Criteria andTranProfitScaleNotBetween(String value1, String value2) {
            addCriterion("TRAN_PROFIT_SCALE not between", value1, value2, "tranProfitScale");
            return (Criteria) this;
        }

        public Criteria andPayProfitScaleIsNull() {
            addCriterion("PAY_PROFIT_SCALE is null");
            return (Criteria) this;
        }

        public Criteria andPayProfitScaleIsNotNull() {
            addCriterion("PAY_PROFIT_SCALE is not null");
            return (Criteria) this;
        }

        public Criteria andPayProfitScaleEqualTo(String value) {
            addCriterion("PAY_PROFIT_SCALE =", value, "payProfitScale");
            return (Criteria) this;
        }

        public Criteria andPayProfitScaleNotEqualTo(String value) {
            addCriterion("PAY_PROFIT_SCALE <>", value, "payProfitScale");
            return (Criteria) this;
        }

        public Criteria andPayProfitScaleGreaterThan(String value) {
            addCriterion("PAY_PROFIT_SCALE >", value, "payProfitScale");
            return (Criteria) this;
        }

        public Criteria andPayProfitScaleGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_PROFIT_SCALE >=", value, "payProfitScale");
            return (Criteria) this;
        }

        public Criteria andPayProfitScaleLessThan(String value) {
            addCriterion("PAY_PROFIT_SCALE <", value, "payProfitScale");
            return (Criteria) this;
        }

        public Criteria andPayProfitScaleLessThanOrEqualTo(String value) {
            addCriterion("PAY_PROFIT_SCALE <=", value, "payProfitScale");
            return (Criteria) this;
        }

        public Criteria andPayProfitScaleLike(String value) {
            addCriterion("PAY_PROFIT_SCALE like", value, "payProfitScale");
            return (Criteria) this;
        }

        public Criteria andPayProfitScaleNotLike(String value) {
            addCriterion("PAY_PROFIT_SCALE not like", value, "payProfitScale");
            return (Criteria) this;
        }

        public Criteria andPayProfitScaleIn(List<String> values) {
            addCriterion("PAY_PROFIT_SCALE in", values, "payProfitScale");
            return (Criteria) this;
        }

        public Criteria andPayProfitScaleNotIn(List<String> values) {
            addCriterion("PAY_PROFIT_SCALE not in", values, "payProfitScale");
            return (Criteria) this;
        }

        public Criteria andPayProfitScaleBetween(String value1, String value2) {
            addCriterion("PAY_PROFIT_SCALE between", value1, value2, "payProfitScale");
            return (Criteria) this;
        }

        public Criteria andPayProfitScaleNotBetween(String value1, String value2) {
            addCriterion("PAY_PROFIT_SCALE not between", value1, value2, "payProfitScale");
            return (Criteria) this;
        }

        public Criteria andTranProfitAmtIsNull() {
            addCriterion("TRAN_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andTranProfitAmtIsNotNull() {
            addCriterion("TRAN_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andTranProfitAmtEqualTo(BigDecimal value) {
            addCriterion("TRAN_PROFIT_AMT =", value, "tranProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTranProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("TRAN_PROFIT_AMT <>", value, "tranProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTranProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("TRAN_PROFIT_AMT >", value, "tranProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTranProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TRAN_PROFIT_AMT >=", value, "tranProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTranProfitAmtLessThan(BigDecimal value) {
            addCriterion("TRAN_PROFIT_AMT <", value, "tranProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTranProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TRAN_PROFIT_AMT <=", value, "tranProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTranProfitAmtIn(List<BigDecimal> values) {
            addCriterion("TRAN_PROFIT_AMT in", values, "tranProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTranProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("TRAN_PROFIT_AMT not in", values, "tranProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTranProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRAN_PROFIT_AMT between", value1, value2, "tranProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTranProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRAN_PROFIT_AMT not between", value1, value2, "tranProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPayProfitAmtIsNull() {
            addCriterion("PAY_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPayProfitAmtIsNotNull() {
            addCriterion("PAY_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPayProfitAmtEqualTo(BigDecimal value) {
            addCriterion("PAY_PROFIT_AMT =", value, "payProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPayProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("PAY_PROFIT_AMT <>", value, "payProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPayProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("PAY_PROFIT_AMT >", value, "payProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPayProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_PROFIT_AMT >=", value, "payProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPayProfitAmtLessThan(BigDecimal value) {
            addCriterion("PAY_PROFIT_AMT <", value, "payProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPayProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_PROFIT_AMT <=", value, "payProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPayProfitAmtIn(List<BigDecimal> values) {
            addCriterion("PAY_PROFIT_AMT in", values, "payProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPayProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("PAY_PROFIT_AMT not in", values, "payProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPayProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_PROFIT_AMT between", value1, value2, "payProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPayProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_PROFIT_AMT not between", value1, value2, "payProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxProfitAmtIsNull() {
            addCriterion("RYX_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andRyxProfitAmtIsNotNull() {
            addCriterion("RYX_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andRyxProfitAmtEqualTo(BigDecimal value) {
            addCriterion("RYX_PROFIT_AMT =", value, "ryxProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("RYX_PROFIT_AMT <>", value, "ryxProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("RYX_PROFIT_AMT >", value, "ryxProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RYX_PROFIT_AMT >=", value, "ryxProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxProfitAmtLessThan(BigDecimal value) {
            addCriterion("RYX_PROFIT_AMT <", value, "ryxProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RYX_PROFIT_AMT <=", value, "ryxProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxProfitAmtIn(List<BigDecimal> values) {
            addCriterion("RYX_PROFIT_AMT in", values, "ryxProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("RYX_PROFIT_AMT not in", values, "ryxProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RYX_PROFIT_AMT between", value1, value2, "ryxProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RYX_PROFIT_AMT not between", value1, value2, "ryxProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxHdProfitAmtIsNull() {
            addCriterion("RYX_HD_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andRyxHdProfitAmtIsNotNull() {
            addCriterion("RYX_HD_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andRyxHdProfitAmtEqualTo(BigDecimal value) {
            addCriterion("RYX_HD_PROFIT_AMT =", value, "ryxHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxHdProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("RYX_HD_PROFIT_AMT <>", value, "ryxHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxHdProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("RYX_HD_PROFIT_AMT >", value, "ryxHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxHdProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RYX_HD_PROFIT_AMT >=", value, "ryxHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxHdProfitAmtLessThan(BigDecimal value) {
            addCriterion("RYX_HD_PROFIT_AMT <", value, "ryxHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxHdProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RYX_HD_PROFIT_AMT <=", value, "ryxHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxHdProfitAmtIn(List<BigDecimal> values) {
            addCriterion("RYX_HD_PROFIT_AMT in", values, "ryxHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxHdProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("RYX_HD_PROFIT_AMT not in", values, "ryxHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxHdProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RYX_HD_PROFIT_AMT between", value1, value2, "ryxHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRyxHdProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RYX_HD_PROFIT_AMT not between", value1, value2, "ryxHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTpProfitAmtIsNull() {
            addCriterion("TP_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andTpProfitAmtIsNotNull() {
            addCriterion("TP_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andTpProfitAmtEqualTo(BigDecimal value) {
            addCriterion("TP_PROFIT_AMT =", value, "tpProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTpProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("TP_PROFIT_AMT <>", value, "tpProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTpProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("TP_PROFIT_AMT >", value, "tpProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTpProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TP_PROFIT_AMT >=", value, "tpProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTpProfitAmtLessThan(BigDecimal value) {
            addCriterion("TP_PROFIT_AMT <", value, "tpProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTpProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TP_PROFIT_AMT <=", value, "tpProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTpProfitAmtIn(List<BigDecimal> values) {
            addCriterion("TP_PROFIT_AMT in", values, "tpProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTpProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("TP_PROFIT_AMT not in", values, "tpProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTpProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TP_PROFIT_AMT between", value1, value2, "tpProfitAmt");
            return (Criteria) this;
        }

        public Criteria andTpProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TP_PROFIT_AMT not between", value1, value2, "tpProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsProfitAmtIsNull() {
            addCriterion("RS_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andRsProfitAmtIsNotNull() {
            addCriterion("RS_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andRsProfitAmtEqualTo(BigDecimal value) {
            addCriterion("RS_PROFIT_AMT =", value, "rsProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("RS_PROFIT_AMT <>", value, "rsProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("RS_PROFIT_AMT >", value, "rsProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RS_PROFIT_AMT >=", value, "rsProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsProfitAmtLessThan(BigDecimal value) {
            addCriterion("RS_PROFIT_AMT <", value, "rsProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RS_PROFIT_AMT <=", value, "rsProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsProfitAmtIn(List<BigDecimal> values) {
            addCriterion("RS_PROFIT_AMT in", values, "rsProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("RS_PROFIT_AMT not in", values, "rsProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RS_PROFIT_AMT between", value1, value2, "rsProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RS_PROFIT_AMT not between", value1, value2, "rsProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsHdProfitAmtIsNull() {
            addCriterion("RS_HD_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andRsHdProfitAmtIsNotNull() {
            addCriterion("RS_HD_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andRsHdProfitAmtEqualTo(BigDecimal value) {
            addCriterion("RS_HD_PROFIT_AMT =", value, "rsHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsHdProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("RS_HD_PROFIT_AMT <>", value, "rsHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsHdProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("RS_HD_PROFIT_AMT >", value, "rsHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsHdProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RS_HD_PROFIT_AMT >=", value, "rsHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsHdProfitAmtLessThan(BigDecimal value) {
            addCriterion("RS_HD_PROFIT_AMT <", value, "rsHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsHdProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RS_HD_PROFIT_AMT <=", value, "rsHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsHdProfitAmtIn(List<BigDecimal> values) {
            addCriterion("RS_HD_PROFIT_AMT in", values, "rsHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsHdProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("RS_HD_PROFIT_AMT not in", values, "rsHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsHdProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RS_HD_PROFIT_AMT between", value1, value2, "rsHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRsHdProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RS_HD_PROFIT_AMT not between", value1, value2, "rsHdProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRhbProfitAmtIsNull() {
            addCriterion("RHB_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andRhbProfitAmtIsNotNull() {
            addCriterion("RHB_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andRhbProfitAmtEqualTo(BigDecimal value) {
            addCriterion("RHB_PROFIT_AMT =", value, "rhbProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRhbProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("RHB_PROFIT_AMT <>", value, "rhbProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRhbProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("RHB_PROFIT_AMT >", value, "rhbProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRhbProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RHB_PROFIT_AMT >=", value, "rhbProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRhbProfitAmtLessThan(BigDecimal value) {
            addCriterion("RHB_PROFIT_AMT <", value, "rhbProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRhbProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RHB_PROFIT_AMT <=", value, "rhbProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRhbProfitAmtIn(List<BigDecimal> values) {
            addCriterion("RHB_PROFIT_AMT in", values, "rhbProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRhbProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("RHB_PROFIT_AMT not in", values, "rhbProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRhbProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RHB_PROFIT_AMT between", value1, value2, "rhbProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRhbProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RHB_PROFIT_AMT not between", value1, value2, "rhbProfitAmt");
            return (Criteria) this;
        }

        public Criteria andZfProfitAmtIsNull() {
            addCriterion("ZF_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andZfProfitAmtIsNotNull() {
            addCriterion("ZF_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andZfProfitAmtEqualTo(BigDecimal value) {
            addCriterion("ZF_PROFIT_AMT =", value, "zfProfitAmt");
            return (Criteria) this;
        }

        public Criteria andZfProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("ZF_PROFIT_AMT <>", value, "zfProfitAmt");
            return (Criteria) this;
        }

        public Criteria andZfProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("ZF_PROFIT_AMT >", value, "zfProfitAmt");
            return (Criteria) this;
        }

        public Criteria andZfProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZF_PROFIT_AMT >=", value, "zfProfitAmt");
            return (Criteria) this;
        }

        public Criteria andZfProfitAmtLessThan(BigDecimal value) {
            addCriterion("ZF_PROFIT_AMT <", value, "zfProfitAmt");
            return (Criteria) this;
        }

        public Criteria andZfProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZF_PROFIT_AMT <=", value, "zfProfitAmt");
            return (Criteria) this;
        }

        public Criteria andZfProfitAmtIn(List<BigDecimal> values) {
            addCriterion("ZF_PROFIT_AMT in", values, "zfProfitAmt");
            return (Criteria) this;
        }

        public Criteria andZfProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("ZF_PROFIT_AMT not in", values, "zfProfitAmt");
            return (Criteria) this;
        }

        public Criteria andZfProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZF_PROFIT_AMT between", value1, value2, "zfProfitAmt");
            return (Criteria) this;
        }

        public Criteria andZfProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZF_PROFIT_AMT not between", value1, value2, "zfProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPosZqSupplyProfitAmtIsNull() {
            addCriterion("POS_ZQ_SUPPLY_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPosZqSupplyProfitAmtIsNotNull() {
            addCriterion("POS_ZQ_SUPPLY_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPosZqSupplyProfitAmtEqualTo(BigDecimal value) {
            addCriterion("POS_ZQ_SUPPLY_PROFIT_AMT =", value, "posZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPosZqSupplyProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("POS_ZQ_SUPPLY_PROFIT_AMT <>", value, "posZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPosZqSupplyProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("POS_ZQ_SUPPLY_PROFIT_AMT >", value, "posZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPosZqSupplyProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_ZQ_SUPPLY_PROFIT_AMT >=", value, "posZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPosZqSupplyProfitAmtLessThan(BigDecimal value) {
            addCriterion("POS_ZQ_SUPPLY_PROFIT_AMT <", value, "posZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPosZqSupplyProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_ZQ_SUPPLY_PROFIT_AMT <=", value, "posZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPosZqSupplyProfitAmtIn(List<BigDecimal> values) {
            addCriterion("POS_ZQ_SUPPLY_PROFIT_AMT in", values, "posZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPosZqSupplyProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("POS_ZQ_SUPPLY_PROFIT_AMT not in", values, "posZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPosZqSupplyProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_ZQ_SUPPLY_PROFIT_AMT between", value1, value2, "posZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPosZqSupplyProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_ZQ_SUPPLY_PROFIT_AMT not between", value1, value2, "posZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMposZqSupplyProfitAmtIsNull() {
            addCriterion("MPOS_ZQ_SUPPLY_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andMposZqSupplyProfitAmtIsNotNull() {
            addCriterion("MPOS_ZQ_SUPPLY_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andMposZqSupplyProfitAmtEqualTo(BigDecimal value) {
            addCriterion("MPOS_ZQ_SUPPLY_PROFIT_AMT =", value, "mposZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMposZqSupplyProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("MPOS_ZQ_SUPPLY_PROFIT_AMT <>", value, "mposZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMposZqSupplyProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("MPOS_ZQ_SUPPLY_PROFIT_AMT >", value, "mposZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMposZqSupplyProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MPOS_ZQ_SUPPLY_PROFIT_AMT >=", value, "mposZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMposZqSupplyProfitAmtLessThan(BigDecimal value) {
            addCriterion("MPOS_ZQ_SUPPLY_PROFIT_AMT <", value, "mposZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMposZqSupplyProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MPOS_ZQ_SUPPLY_PROFIT_AMT <=", value, "mposZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMposZqSupplyProfitAmtIn(List<BigDecimal> values) {
            addCriterion("MPOS_ZQ_SUPPLY_PROFIT_AMT in", values, "mposZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMposZqSupplyProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("MPOS_ZQ_SUPPLY_PROFIT_AMT not in", values, "mposZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMposZqSupplyProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MPOS_ZQ_SUPPLY_PROFIT_AMT between", value1, value2, "mposZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMposZqSupplyProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MPOS_ZQ_SUPPLY_PROFIT_AMT not between", value1, value2, "mposZqSupplyProfitAmt");
            return (Criteria) this;
        }

        public Criteria andProfitSumAmtIsNull() {
            addCriterion("PROFIT_SUM_AMT is null");
            return (Criteria) this;
        }

        public Criteria andProfitSumAmtIsNotNull() {
            addCriterion("PROFIT_SUM_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andProfitSumAmtEqualTo(BigDecimal value) {
            addCriterion("PROFIT_SUM_AMT =", value, "profitSumAmt");
            return (Criteria) this;
        }

        public Criteria andProfitSumAmtNotEqualTo(BigDecimal value) {
            addCriterion("PROFIT_SUM_AMT <>", value, "profitSumAmt");
            return (Criteria) this;
        }

        public Criteria andProfitSumAmtGreaterThan(BigDecimal value) {
            addCriterion("PROFIT_SUM_AMT >", value, "profitSumAmt");
            return (Criteria) this;
        }

        public Criteria andProfitSumAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_SUM_AMT >=", value, "profitSumAmt");
            return (Criteria) this;
        }

        public Criteria andProfitSumAmtLessThan(BigDecimal value) {
            addCriterion("PROFIT_SUM_AMT <", value, "profitSumAmt");
            return (Criteria) this;
        }

        public Criteria andProfitSumAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_SUM_AMT <=", value, "profitSumAmt");
            return (Criteria) this;
        }

        public Criteria andProfitSumAmtIn(List<BigDecimal> values) {
            addCriterion("PROFIT_SUM_AMT in", values, "profitSumAmt");
            return (Criteria) this;
        }

        public Criteria andProfitSumAmtNotIn(List<BigDecimal> values) {
            addCriterion("PROFIT_SUM_AMT not in", values, "profitSumAmt");
            return (Criteria) this;
        }

        public Criteria andProfitSumAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_SUM_AMT between", value1, value2, "profitSumAmt");
            return (Criteria) this;
        }

        public Criteria andProfitSumAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_SUM_AMT not between", value1, value2, "profitSumAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdMustDeductionAmtIsNull() {
            addCriterion("POS_TD_MUST_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPosTdMustDeductionAmtIsNotNull() {
            addCriterion("POS_TD_MUST_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPosTdMustDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("POS_TD_MUST_DEDUCTION_AMT =", value, "posTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdMustDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("POS_TD_MUST_DEDUCTION_AMT <>", value, "posTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdMustDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("POS_TD_MUST_DEDUCTION_AMT >", value, "posTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdMustDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_TD_MUST_DEDUCTION_AMT >=", value, "posTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdMustDeductionAmtLessThan(BigDecimal value) {
            addCriterion("POS_TD_MUST_DEDUCTION_AMT <", value, "posTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdMustDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_TD_MUST_DEDUCTION_AMT <=", value, "posTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdMustDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("POS_TD_MUST_DEDUCTION_AMT in", values, "posTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdMustDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("POS_TD_MUST_DEDUCTION_AMT not in", values, "posTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdMustDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_TD_MUST_DEDUCTION_AMT between", value1, value2, "posTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdMustDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_TD_MUST_DEDUCTION_AMT not between", value1, value2, "posTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdRealDeductionAmtIsNull() {
            addCriterion("POS_TD_REAL_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPosTdRealDeductionAmtIsNotNull() {
            addCriterion("POS_TD_REAL_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPosTdRealDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("POS_TD_REAL_DEDUCTION_AMT =", value, "posTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdRealDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("POS_TD_REAL_DEDUCTION_AMT <>", value, "posTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdRealDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("POS_TD_REAL_DEDUCTION_AMT >", value, "posTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdRealDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_TD_REAL_DEDUCTION_AMT >=", value, "posTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdRealDeductionAmtLessThan(BigDecimal value) {
            addCriterion("POS_TD_REAL_DEDUCTION_AMT <", value, "posTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdRealDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_TD_REAL_DEDUCTION_AMT <=", value, "posTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdRealDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("POS_TD_REAL_DEDUCTION_AMT in", values, "posTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdRealDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("POS_TD_REAL_DEDUCTION_AMT not in", values, "posTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdRealDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_TD_REAL_DEDUCTION_AMT between", value1, value2, "posTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdRealDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_TD_REAL_DEDUCTION_AMT not between", value1, value2, "posTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdMustDeductionAmtIsNull() {
            addCriterion("MPOS_TD_MUST_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andMposTdMustDeductionAmtIsNotNull() {
            addCriterion("MPOS_TD_MUST_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andMposTdMustDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("MPOS_TD_MUST_DEDUCTION_AMT =", value, "mposTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdMustDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("MPOS_TD_MUST_DEDUCTION_AMT <>", value, "mposTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdMustDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("MPOS_TD_MUST_DEDUCTION_AMT >", value, "mposTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdMustDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MPOS_TD_MUST_DEDUCTION_AMT >=", value, "mposTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdMustDeductionAmtLessThan(BigDecimal value) {
            addCriterion("MPOS_TD_MUST_DEDUCTION_AMT <", value, "mposTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdMustDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MPOS_TD_MUST_DEDUCTION_AMT <=", value, "mposTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdMustDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("MPOS_TD_MUST_DEDUCTION_AMT in", values, "mposTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdMustDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("MPOS_TD_MUST_DEDUCTION_AMT not in", values, "mposTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdMustDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MPOS_TD_MUST_DEDUCTION_AMT between", value1, value2, "mposTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdMustDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MPOS_TD_MUST_DEDUCTION_AMT not between", value1, value2, "mposTdMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdRealDeductionAmtIsNull() {
            addCriterion("MPOS_TD_REAL_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andMposTdRealDeductionAmtIsNotNull() {
            addCriterion("MPOS_TD_REAL_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andMposTdRealDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("MPOS_TD_REAL_DEDUCTION_AMT =", value, "mposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdRealDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("MPOS_TD_REAL_DEDUCTION_AMT <>", value, "mposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdRealDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("MPOS_TD_REAL_DEDUCTION_AMT >", value, "mposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdRealDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MPOS_TD_REAL_DEDUCTION_AMT >=", value, "mposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdRealDeductionAmtLessThan(BigDecimal value) {
            addCriterion("MPOS_TD_REAL_DEDUCTION_AMT <", value, "mposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdRealDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MPOS_TD_REAL_DEDUCTION_AMT <=", value, "mposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdRealDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("MPOS_TD_REAL_DEDUCTION_AMT in", values, "mposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdRealDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("MPOS_TD_REAL_DEDUCTION_AMT not in", values, "mposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdRealDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MPOS_TD_REAL_DEDUCTION_AMT between", value1, value2, "mposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdRealDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MPOS_TD_REAL_DEDUCTION_AMT not between", value1, value2, "mposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgMustDeductionAmtIsNull() {
            addCriterion("RHB_DG_MUST_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andRhbDgMustDeductionAmtIsNotNull() {
            addCriterion("RHB_DG_MUST_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andRhbDgMustDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("RHB_DG_MUST_DEDUCTION_AMT =", value, "rhbDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgMustDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("RHB_DG_MUST_DEDUCTION_AMT <>", value, "rhbDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgMustDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("RHB_DG_MUST_DEDUCTION_AMT >", value, "rhbDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgMustDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RHB_DG_MUST_DEDUCTION_AMT >=", value, "rhbDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgMustDeductionAmtLessThan(BigDecimal value) {
            addCriterion("RHB_DG_MUST_DEDUCTION_AMT <", value, "rhbDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgMustDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RHB_DG_MUST_DEDUCTION_AMT <=", value, "rhbDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgMustDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("RHB_DG_MUST_DEDUCTION_AMT in", values, "rhbDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgMustDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("RHB_DG_MUST_DEDUCTION_AMT not in", values, "rhbDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgMustDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RHB_DG_MUST_DEDUCTION_AMT between", value1, value2, "rhbDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgMustDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RHB_DG_MUST_DEDUCTION_AMT not between", value1, value2, "rhbDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgRealDeductionAmtIsNull() {
            addCriterion("RHB_DG_REAL_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andRhbDgRealDeductionAmtIsNotNull() {
            addCriterion("RHB_DG_REAL_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andRhbDgRealDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("RHB_DG_REAL_DEDUCTION_AMT =", value, "rhbDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgRealDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("RHB_DG_REAL_DEDUCTION_AMT <>", value, "rhbDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgRealDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("RHB_DG_REAL_DEDUCTION_AMT >", value, "rhbDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgRealDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RHB_DG_REAL_DEDUCTION_AMT >=", value, "rhbDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgRealDeductionAmtLessThan(BigDecimal value) {
            addCriterion("RHB_DG_REAL_DEDUCTION_AMT <", value, "rhbDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgRealDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RHB_DG_REAL_DEDUCTION_AMT <=", value, "rhbDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgRealDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("RHB_DG_REAL_DEDUCTION_AMT in", values, "rhbDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgRealDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("RHB_DG_REAL_DEDUCTION_AMT not in", values, "rhbDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgRealDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RHB_DG_REAL_DEDUCTION_AMT between", value1, value2, "rhbDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andRhbDgRealDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RHB_DG_REAL_DEDUCTION_AMT not between", value1, value2, "rhbDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgMustDeductionAmtIsNull() {
            addCriterion("POS_DG_MUST_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPosDgMustDeductionAmtIsNotNull() {
            addCriterion("POS_DG_MUST_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPosDgMustDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("POS_DG_MUST_DEDUCTION_AMT =", value, "posDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgMustDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("POS_DG_MUST_DEDUCTION_AMT <>", value, "posDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgMustDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("POS_DG_MUST_DEDUCTION_AMT >", value, "posDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgMustDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_DG_MUST_DEDUCTION_AMT >=", value, "posDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgMustDeductionAmtLessThan(BigDecimal value) {
            addCriterion("POS_DG_MUST_DEDUCTION_AMT <", value, "posDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgMustDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_DG_MUST_DEDUCTION_AMT <=", value, "posDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgMustDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("POS_DG_MUST_DEDUCTION_AMT in", values, "posDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgMustDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("POS_DG_MUST_DEDUCTION_AMT not in", values, "posDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgMustDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_DG_MUST_DEDUCTION_AMT between", value1, value2, "posDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgMustDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_DG_MUST_DEDUCTION_AMT not between", value1, value2, "posDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgRealDeductionAmtIsNull() {
            addCriterion("POS_DG_REAL_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPosDgRealDeductionAmtIsNotNull() {
            addCriterion("POS_DG_REAL_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPosDgRealDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("POS_DG_REAL_DEDUCTION_AMT =", value, "posDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgRealDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("POS_DG_REAL_DEDUCTION_AMT <>", value, "posDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgRealDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("POS_DG_REAL_DEDUCTION_AMT >", value, "posDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgRealDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_DG_REAL_DEDUCTION_AMT >=", value, "posDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgRealDeductionAmtLessThan(BigDecimal value) {
            addCriterion("POS_DG_REAL_DEDUCTION_AMT <", value, "posDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgRealDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_DG_REAL_DEDUCTION_AMT <=", value, "posDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgRealDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("POS_DG_REAL_DEDUCTION_AMT in", values, "posDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgRealDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("POS_DG_REAL_DEDUCTION_AMT not in", values, "posDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgRealDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_DG_REAL_DEDUCTION_AMT between", value1, value2, "posDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosDgRealDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_DG_REAL_DEDUCTION_AMT not between", value1, value2, "posDgRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposDgMustDeductionAmtIsNull() {
            addCriterion("ZPOS_DG_MUST_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andZposDgMustDeductionAmtIsNotNull() {
            addCriterion("ZPOS_DG_MUST_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andZposDgMustDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("ZPOS_DG_MUST_DEDUCTION_AMT =", value, "zposDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposDgMustDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("ZPOS_DG_MUST_DEDUCTION_AMT <>", value, "zposDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposDgMustDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("ZPOS_DG_MUST_DEDUCTION_AMT >", value, "zposDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposDgMustDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZPOS_DG_MUST_DEDUCTION_AMT >=", value, "zposDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposDgMustDeductionAmtLessThan(BigDecimal value) {
            addCriterion("ZPOS_DG_MUST_DEDUCTION_AMT <", value, "zposDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposDgMustDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZPOS_DG_MUST_DEDUCTION_AMT <=", value, "zposDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposDgMustDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("ZPOS_DG_MUST_DEDUCTION_AMT in", values, "zposDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposDgMustDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("ZPOS_DG_MUST_DEDUCTION_AMT not in", values, "zposDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposDgMustDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZPOS_DG_MUST_DEDUCTION_AMT between", value1, value2, "zposDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposDgMustDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZPOS_DG_MUST_DEDUCTION_AMT not between", value1, value2, "zposDgMustDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposTdRealDeductionAmtIsNull() {
            addCriterion("ZPOS_TD_REAL_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andZposTdRealDeductionAmtIsNotNull() {
            addCriterion("ZPOS_TD_REAL_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andZposTdRealDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("ZPOS_TD_REAL_DEDUCTION_AMT =", value, "zposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposTdRealDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("ZPOS_TD_REAL_DEDUCTION_AMT <>", value, "zposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposTdRealDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("ZPOS_TD_REAL_DEDUCTION_AMT >", value, "zposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposTdRealDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ZPOS_TD_REAL_DEDUCTION_AMT >=", value, "zposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposTdRealDeductionAmtLessThan(BigDecimal value) {
            addCriterion("ZPOS_TD_REAL_DEDUCTION_AMT <", value, "zposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposTdRealDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ZPOS_TD_REAL_DEDUCTION_AMT <=", value, "zposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposTdRealDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("ZPOS_TD_REAL_DEDUCTION_AMT in", values, "zposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposTdRealDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("ZPOS_TD_REAL_DEDUCTION_AMT not in", values, "zposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposTdRealDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZPOS_TD_REAL_DEDUCTION_AMT between", value1, value2, "zposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andZposTdRealDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ZPOS_TD_REAL_DEDUCTION_AMT not between", value1, value2, "zposTdRealDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosKhDeductionAmtIsNull() {
            addCriterion("POS_KH_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPosKhDeductionAmtIsNotNull() {
            addCriterion("POS_KH_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPosKhDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("POS_KH_DEDUCTION_AMT =", value, "posKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosKhDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("POS_KH_DEDUCTION_AMT <>", value, "posKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosKhDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("POS_KH_DEDUCTION_AMT >", value, "posKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosKhDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_KH_DEDUCTION_AMT >=", value, "posKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosKhDeductionAmtLessThan(BigDecimal value) {
            addCriterion("POS_KH_DEDUCTION_AMT <", value, "posKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosKhDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_KH_DEDUCTION_AMT <=", value, "posKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosKhDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("POS_KH_DEDUCTION_AMT in", values, "posKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosKhDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("POS_KH_DEDUCTION_AMT not in", values, "posKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosKhDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_KH_DEDUCTION_AMT between", value1, value2, "posKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosKhDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_KH_DEDUCTION_AMT not between", value1, value2, "posKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposKhDeductionAmtIsNull() {
            addCriterion("MPOS_KH_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andMposKhDeductionAmtIsNotNull() {
            addCriterion("MPOS_KH_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andMposKhDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("MPOS_KH_DEDUCTION_AMT =", value, "mposKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposKhDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("MPOS_KH_DEDUCTION_AMT <>", value, "mposKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposKhDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("MPOS_KH_DEDUCTION_AMT >", value, "mposKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposKhDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MPOS_KH_DEDUCTION_AMT >=", value, "mposKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposKhDeductionAmtLessThan(BigDecimal value) {
            addCriterion("MPOS_KH_DEDUCTION_AMT <", value, "mposKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposKhDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MPOS_KH_DEDUCTION_AMT <=", value, "mposKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposKhDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("MPOS_KH_DEDUCTION_AMT in", values, "mposKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposKhDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("MPOS_KH_DEDUCTION_AMT not in", values, "mposKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposKhDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MPOS_KH_DEDUCTION_AMT between", value1, value2, "mposKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andMposKhDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MPOS_KH_DEDUCTION_AMT not between", value1, value2, "mposKhDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andBuDeductionAmtIsNull() {
            addCriterion("BU_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andBuDeductionAmtIsNotNull() {
            addCriterion("BU_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andBuDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("BU_DEDUCTION_AMT =", value, "buDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andBuDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("BU_DEDUCTION_AMT <>", value, "buDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andBuDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("BU_DEDUCTION_AMT >", value, "buDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andBuDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BU_DEDUCTION_AMT >=", value, "buDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andBuDeductionAmtLessThan(BigDecimal value) {
            addCriterion("BU_DEDUCTION_AMT <", value, "buDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andBuDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BU_DEDUCTION_AMT <=", value, "buDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andBuDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("BU_DEDUCTION_AMT in", values, "buDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andBuDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("BU_DEDUCTION_AMT not in", values, "buDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andBuDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BU_DEDUCTION_AMT between", value1, value2, "buDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andBuDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BU_DEDUCTION_AMT not between", value1, value2, "buDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andOtherDeductionAmtIsNull() {
            addCriterion("OTHER_DEDUCTION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andOtherDeductionAmtIsNotNull() {
            addCriterion("OTHER_DEDUCTION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andOtherDeductionAmtEqualTo(BigDecimal value) {
            addCriterion("OTHER_DEDUCTION_AMT =", value, "otherDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andOtherDeductionAmtNotEqualTo(BigDecimal value) {
            addCriterion("OTHER_DEDUCTION_AMT <>", value, "otherDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andOtherDeductionAmtGreaterThan(BigDecimal value) {
            addCriterion("OTHER_DEDUCTION_AMT >", value, "otherDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andOtherDeductionAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OTHER_DEDUCTION_AMT >=", value, "otherDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andOtherDeductionAmtLessThan(BigDecimal value) {
            addCriterion("OTHER_DEDUCTION_AMT <", value, "otherDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andOtherDeductionAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OTHER_DEDUCTION_AMT <=", value, "otherDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andOtherDeductionAmtIn(List<BigDecimal> values) {
            addCriterion("OTHER_DEDUCTION_AMT in", values, "otherDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andOtherDeductionAmtNotIn(List<BigDecimal> values) {
            addCriterion("OTHER_DEDUCTION_AMT not in", values, "otherDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andOtherDeductionAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OTHER_DEDUCTION_AMT between", value1, value2, "otherDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andOtherDeductionAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OTHER_DEDUCTION_AMT not between", value1, value2, "otherDeductionAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdSupplyAmtIsNull() {
            addCriterion("POS_TD_SUPPLY_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPosTdSupplyAmtIsNotNull() {
            addCriterion("POS_TD_SUPPLY_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPosTdSupplyAmtEqualTo(BigDecimal value) {
            addCriterion("POS_TD_SUPPLY_AMT =", value, "posTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdSupplyAmtNotEqualTo(BigDecimal value) {
            addCriterion("POS_TD_SUPPLY_AMT <>", value, "posTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdSupplyAmtGreaterThan(BigDecimal value) {
            addCriterion("POS_TD_SUPPLY_AMT >", value, "posTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdSupplyAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_TD_SUPPLY_AMT >=", value, "posTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdSupplyAmtLessThan(BigDecimal value) {
            addCriterion("POS_TD_SUPPLY_AMT <", value, "posTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdSupplyAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_TD_SUPPLY_AMT <=", value, "posTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdSupplyAmtIn(List<BigDecimal> values) {
            addCriterion("POS_TD_SUPPLY_AMT in", values, "posTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdSupplyAmtNotIn(List<BigDecimal> values) {
            addCriterion("POS_TD_SUPPLY_AMT not in", values, "posTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdSupplyAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_TD_SUPPLY_AMT between", value1, value2, "posTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andPosTdSupplyAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_TD_SUPPLY_AMT not between", value1, value2, "posTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdSupplyAmtIsNull() {
            addCriterion("MPOS_TD_SUPPLY_AMT is null");
            return (Criteria) this;
        }

        public Criteria andMposTdSupplyAmtIsNotNull() {
            addCriterion("MPOS_TD_SUPPLY_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andMposTdSupplyAmtEqualTo(BigDecimal value) {
            addCriterion("MPOS_TD_SUPPLY_AMT =", value, "mposTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdSupplyAmtNotEqualTo(BigDecimal value) {
            addCriterion("MPOS_TD_SUPPLY_AMT <>", value, "mposTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdSupplyAmtGreaterThan(BigDecimal value) {
            addCriterion("MPOS_TD_SUPPLY_AMT >", value, "mposTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdSupplyAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MPOS_TD_SUPPLY_AMT >=", value, "mposTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdSupplyAmtLessThan(BigDecimal value) {
            addCriterion("MPOS_TD_SUPPLY_AMT <", value, "mposTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdSupplyAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MPOS_TD_SUPPLY_AMT <=", value, "mposTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdSupplyAmtIn(List<BigDecimal> values) {
            addCriterion("MPOS_TD_SUPPLY_AMT in", values, "mposTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdSupplyAmtNotIn(List<BigDecimal> values) {
            addCriterion("MPOS_TD_SUPPLY_AMT not in", values, "mposTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdSupplyAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MPOS_TD_SUPPLY_AMT between", value1, value2, "mposTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andMposTdSupplyAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MPOS_TD_SUPPLY_AMT not between", value1, value2, "mposTdSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andOtherSupplyAmtIsNull() {
            addCriterion("OTHER_SUPPLY_AMT is null");
            return (Criteria) this;
        }

        public Criteria andOtherSupplyAmtIsNotNull() {
            addCriterion("OTHER_SUPPLY_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andOtherSupplyAmtEqualTo(BigDecimal value) {
            addCriterion("OTHER_SUPPLY_AMT =", value, "otherSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andOtherSupplyAmtNotEqualTo(BigDecimal value) {
            addCriterion("OTHER_SUPPLY_AMT <>", value, "otherSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andOtherSupplyAmtGreaterThan(BigDecimal value) {
            addCriterion("OTHER_SUPPLY_AMT >", value, "otherSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andOtherSupplyAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OTHER_SUPPLY_AMT >=", value, "otherSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andOtherSupplyAmtLessThan(BigDecimal value) {
            addCriterion("OTHER_SUPPLY_AMT <", value, "otherSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andOtherSupplyAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OTHER_SUPPLY_AMT <=", value, "otherSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andOtherSupplyAmtIn(List<BigDecimal> values) {
            addCriterion("OTHER_SUPPLY_AMT in", values, "otherSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andOtherSupplyAmtNotIn(List<BigDecimal> values) {
            addCriterion("OTHER_SUPPLY_AMT not in", values, "otherSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andOtherSupplyAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OTHER_SUPPLY_AMT between", value1, value2, "otherSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andOtherSupplyAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OTHER_SUPPLY_AMT not between", value1, value2, "otherSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andPosRewardAmtIsNull() {
            addCriterion("POS_REWARD_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPosRewardAmtIsNotNull() {
            addCriterion("POS_REWARD_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPosRewardAmtEqualTo(BigDecimal value) {
            addCriterion("POS_REWARD_AMT =", value, "posRewardAmt");
            return (Criteria) this;
        }

        public Criteria andPosRewardAmtNotEqualTo(BigDecimal value) {
            addCriterion("POS_REWARD_AMT <>", value, "posRewardAmt");
            return (Criteria) this;
        }

        public Criteria andPosRewardAmtGreaterThan(BigDecimal value) {
            addCriterion("POS_REWARD_AMT >", value, "posRewardAmt");
            return (Criteria) this;
        }

        public Criteria andPosRewardAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_REWARD_AMT >=", value, "posRewardAmt");
            return (Criteria) this;
        }

        public Criteria andPosRewardAmtLessThan(BigDecimal value) {
            addCriterion("POS_REWARD_AMT <", value, "posRewardAmt");
            return (Criteria) this;
        }

        public Criteria andPosRewardAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_REWARD_AMT <=", value, "posRewardAmt");
            return (Criteria) this;
        }

        public Criteria andPosRewardAmtIn(List<BigDecimal> values) {
            addCriterion("POS_REWARD_AMT in", values, "posRewardAmt");
            return (Criteria) this;
        }

        public Criteria andPosRewardAmtNotIn(List<BigDecimal> values) {
            addCriterion("POS_REWARD_AMT not in", values, "posRewardAmt");
            return (Criteria) this;
        }

        public Criteria andPosRewardAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_REWARD_AMT between", value1, value2, "posRewardAmt");
            return (Criteria) this;
        }

        public Criteria andPosRewardAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_REWARD_AMT not between", value1, value2, "posRewardAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAgoAmtIsNull() {
            addCriterion("DEDUCTION_TAX_MONTH_AGO_AMT is null");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAgoAmtIsNotNull() {
            addCriterion("DEDUCTION_TAX_MONTH_AGO_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAgoAmtEqualTo(BigDecimal value) {
            addCriterion("DEDUCTION_TAX_MONTH_AGO_AMT =", value, "deductionTaxMonthAgoAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAgoAmtNotEqualTo(BigDecimal value) {
            addCriterion("DEDUCTION_TAX_MONTH_AGO_AMT <>", value, "deductionTaxMonthAgoAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAgoAmtGreaterThan(BigDecimal value) {
            addCriterion("DEDUCTION_TAX_MONTH_AGO_AMT >", value, "deductionTaxMonthAgoAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAgoAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DEDUCTION_TAX_MONTH_AGO_AMT >=", value, "deductionTaxMonthAgoAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAgoAmtLessThan(BigDecimal value) {
            addCriterion("DEDUCTION_TAX_MONTH_AGO_AMT <", value, "deductionTaxMonthAgoAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAgoAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DEDUCTION_TAX_MONTH_AGO_AMT <=", value, "deductionTaxMonthAgoAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAgoAmtIn(List<BigDecimal> values) {
            addCriterion("DEDUCTION_TAX_MONTH_AGO_AMT in", values, "deductionTaxMonthAgoAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAgoAmtNotIn(List<BigDecimal> values) {
            addCriterion("DEDUCTION_TAX_MONTH_AGO_AMT not in", values, "deductionTaxMonthAgoAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAgoAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEDUCTION_TAX_MONTH_AGO_AMT between", value1, value2, "deductionTaxMonthAgoAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAgoAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEDUCTION_TAX_MONTH_AGO_AMT not between", value1, value2, "deductionTaxMonthAgoAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAmtIsNull() {
            addCriterion("DEDUCTION_TAX_MONTH_AMT is null");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAmtIsNotNull() {
            addCriterion("DEDUCTION_TAX_MONTH_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAmtEqualTo(BigDecimal value) {
            addCriterion("DEDUCTION_TAX_MONTH_AMT =", value, "deductionTaxMonthAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAmtNotEqualTo(BigDecimal value) {
            addCriterion("DEDUCTION_TAX_MONTH_AMT <>", value, "deductionTaxMonthAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAmtGreaterThan(BigDecimal value) {
            addCriterion("DEDUCTION_TAX_MONTH_AMT >", value, "deductionTaxMonthAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DEDUCTION_TAX_MONTH_AMT >=", value, "deductionTaxMonthAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAmtLessThan(BigDecimal value) {
            addCriterion("DEDUCTION_TAX_MONTH_AMT <", value, "deductionTaxMonthAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DEDUCTION_TAX_MONTH_AMT <=", value, "deductionTaxMonthAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAmtIn(List<BigDecimal> values) {
            addCriterion("DEDUCTION_TAX_MONTH_AMT in", values, "deductionTaxMonthAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAmtNotIn(List<BigDecimal> values) {
            addCriterion("DEDUCTION_TAX_MONTH_AMT not in", values, "deductionTaxMonthAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEDUCTION_TAX_MONTH_AMT between", value1, value2, "deductionTaxMonthAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionTaxMonthAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEDUCTION_TAX_MONTH_AMT not between", value1, value2, "deductionTaxMonthAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtIsNull() {
            addCriterion("SUPPLY_TAX_AMT is null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtIsNotNull() {
            addCriterion("SUPPLY_TAX_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_TAX_AMT =", value, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtNotEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_TAX_AMT <>", value, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtGreaterThan(BigDecimal value) {
            addCriterion("SUPPLY_TAX_AMT >", value, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_TAX_AMT >=", value, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtLessThan(BigDecimal value) {
            addCriterion("SUPPLY_TAX_AMT <", value, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_TAX_AMT <=", value, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtIn(List<BigDecimal> values) {
            addCriterion("SUPPLY_TAX_AMT in", values, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtNotIn(List<BigDecimal> values) {
            addCriterion("SUPPLY_TAX_AMT not in", values, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPPLY_TAX_AMT between", value1, value2, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPPLY_TAX_AMT not between", value1, value2, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andRealProfitAmtIsNull() {
            addCriterion("REAL_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andRealProfitAmtIsNotNull() {
            addCriterion("REAL_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andRealProfitAmtEqualTo(BigDecimal value) {
            addCriterion("REAL_PROFIT_AMT =", value, "realProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRealProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("REAL_PROFIT_AMT <>", value, "realProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRealProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("REAL_PROFIT_AMT >", value, "realProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRealProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_PROFIT_AMT >=", value, "realProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRealProfitAmtLessThan(BigDecimal value) {
            addCriterion("REAL_PROFIT_AMT <", value, "realProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRealProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_PROFIT_AMT <=", value, "realProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRealProfitAmtIn(List<BigDecimal> values) {
            addCriterion("REAL_PROFIT_AMT in", values, "realProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRealProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("REAL_PROFIT_AMT not in", values, "realProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRealProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_PROFIT_AMT between", value1, value2, "realProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRealProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_PROFIT_AMT not between", value1, value2, "realProfitAmt");
            return (Criteria) this;
        }

        public Criteria andProfitMonthAmtIsNull() {
            addCriterion("PROFIT_MONTH_AMT is null");
            return (Criteria) this;
        }

        public Criteria andProfitMonthAmtIsNotNull() {
            addCriterion("PROFIT_MONTH_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andProfitMonthAmtEqualTo(BigDecimal value) {
            addCriterion("PROFIT_MONTH_AMT =", value, "profitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andProfitMonthAmtNotEqualTo(BigDecimal value) {
            addCriterion("PROFIT_MONTH_AMT <>", value, "profitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andProfitMonthAmtGreaterThan(BigDecimal value) {
            addCriterion("PROFIT_MONTH_AMT >", value, "profitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andProfitMonthAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_MONTH_AMT >=", value, "profitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andProfitMonthAmtLessThan(BigDecimal value) {
            addCriterion("PROFIT_MONTH_AMT <", value, "profitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andProfitMonthAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_MONTH_AMT <=", value, "profitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andProfitMonthAmtIn(List<BigDecimal> values) {
            addCriterion("PROFIT_MONTH_AMT in", values, "profitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andProfitMonthAmtNotIn(List<BigDecimal> values) {
            addCriterion("PROFIT_MONTH_AMT not in", values, "profitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andProfitMonthAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_MONTH_AMT between", value1, value2, "profitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andProfitMonthAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_MONTH_AMT not between", value1, value2, "profitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNull() {
            addCriterion("ACCOUNT_ID is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNotNull() {
            addCriterion("ACCOUNT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(String value) {
            addCriterion("ACCOUNT_ID =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotEqualTo(String value) {
            addCriterion("ACCOUNT_ID <>", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThan(String value) {
            addCriterion("ACCOUNT_ID >", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_ID >=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThan(String value) {
            addCriterion("ACCOUNT_ID <", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_ID <=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLike(String value) {
            addCriterion("ACCOUNT_ID like", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotLike(String value) {
            addCriterion("ACCOUNT_ID not like", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIn(List<String> values) {
            addCriterion("ACCOUNT_ID in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotIn(List<String> values) {
            addCriterion("ACCOUNT_ID not in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdBetween(String value1, String value2) {
            addCriterion("ACCOUNT_ID between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotBetween(String value1, String value2) {
            addCriterion("ACCOUNT_ID not between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountNameIsNull() {
            addCriterion("ACCOUNT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAccountNameIsNotNull() {
            addCriterion("ACCOUNT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAccountNameEqualTo(String value) {
            addCriterion("ACCOUNT_NAME =", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotEqualTo(String value) {
            addCriterion("ACCOUNT_NAME <>", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameGreaterThan(String value) {
            addCriterion("ACCOUNT_NAME >", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameGreaterThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_NAME >=", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLessThan(String value) {
            addCriterion("ACCOUNT_NAME <", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLessThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_NAME <=", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLike(String value) {
            addCriterion("ACCOUNT_NAME like", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotLike(String value) {
            addCriterion("ACCOUNT_NAME not like", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameIn(List<String> values) {
            addCriterion("ACCOUNT_NAME in", values, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotIn(List<String> values) {
            addCriterion("ACCOUNT_NAME not in", values, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameBetween(String value1, String value2) {
            addCriterion("ACCOUNT_NAME between", value1, value2, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotBetween(String value1, String value2) {
            addCriterion("ACCOUNT_NAME not between", value1, value2, "accountName");
            return (Criteria) this;
        }

        public Criteria andOpenBankNameIsNull() {
            addCriterion("OPEN_BANK_NAME is null");
            return (Criteria) this;
        }

        public Criteria andOpenBankNameIsNotNull() {
            addCriterion("OPEN_BANK_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andOpenBankNameEqualTo(String value) {
            addCriterion("OPEN_BANK_NAME =", value, "openBankName");
            return (Criteria) this;
        }

        public Criteria andOpenBankNameNotEqualTo(String value) {
            addCriterion("OPEN_BANK_NAME <>", value, "openBankName");
            return (Criteria) this;
        }

        public Criteria andOpenBankNameGreaterThan(String value) {
            addCriterion("OPEN_BANK_NAME >", value, "openBankName");
            return (Criteria) this;
        }

        public Criteria andOpenBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("OPEN_BANK_NAME >=", value, "openBankName");
            return (Criteria) this;
        }

        public Criteria andOpenBankNameLessThan(String value) {
            addCriterion("OPEN_BANK_NAME <", value, "openBankName");
            return (Criteria) this;
        }

        public Criteria andOpenBankNameLessThanOrEqualTo(String value) {
            addCriterion("OPEN_BANK_NAME <=", value, "openBankName");
            return (Criteria) this;
        }

        public Criteria andOpenBankNameLike(String value) {
            addCriterion("OPEN_BANK_NAME like", value, "openBankName");
            return (Criteria) this;
        }

        public Criteria andOpenBankNameNotLike(String value) {
            addCriterion("OPEN_BANK_NAME not like", value, "openBankName");
            return (Criteria) this;
        }

        public Criteria andOpenBankNameIn(List<String> values) {
            addCriterion("OPEN_BANK_NAME in", values, "openBankName");
            return (Criteria) this;
        }

        public Criteria andOpenBankNameNotIn(List<String> values) {
            addCriterion("OPEN_BANK_NAME not in", values, "openBankName");
            return (Criteria) this;
        }

        public Criteria andOpenBankNameBetween(String value1, String value2) {
            addCriterion("OPEN_BANK_NAME between", value1, value2, "openBankName");
            return (Criteria) this;
        }

        public Criteria andOpenBankNameNotBetween(String value1, String value2) {
            addCriterion("OPEN_BANK_NAME not between", value1, value2, "openBankName");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("EMAIL is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("EMAIL is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("EMAIL =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("EMAIL <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("EMAIL >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("EMAIL >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("EMAIL <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("EMAIL <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("EMAIL like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("EMAIL not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("EMAIL in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("EMAIL not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("EMAIL between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("EMAIL not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andBankCodeIsNull() {
            addCriterion("BANK_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBankCodeIsNotNull() {
            addCriterion("BANK_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBankCodeEqualTo(String value) {
            addCriterion("BANK_CODE =", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotEqualTo(String value) {
            addCriterion("BANK_CODE <>", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeGreaterThan(String value) {
            addCriterion("BANK_CODE >", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_CODE >=", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLessThan(String value) {
            addCriterion("BANK_CODE <", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLessThanOrEqualTo(String value) {
            addCriterion("BANK_CODE <=", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLike(String value) {
            addCriterion("BANK_CODE like", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotLike(String value) {
            addCriterion("BANK_CODE not like", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeIn(List<String> values) {
            addCriterion("BANK_CODE in", values, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotIn(List<String> values) {
            addCriterion("BANK_CODE not in", values, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeBetween(String value1, String value2) {
            addCriterion("BANK_CODE between", value1, value2, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotBetween(String value1, String value2) {
            addCriterion("BANK_CODE not between", value1, value2, "bankCode");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNull() {
            addCriterion("PAY_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNotNull() {
            addCriterion("PAY_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andPayStatusEqualTo(String value) {
            addCriterion("PAY_STATUS =", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotEqualTo(String value) {
            addCriterion("PAY_STATUS <>", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThan(String value) {
            addCriterion("PAY_STATUS >", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_STATUS >=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThan(String value) {
            addCriterion("PAY_STATUS <", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThanOrEqualTo(String value) {
            addCriterion("PAY_STATUS <=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLike(String value) {
            addCriterion("PAY_STATUS like", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotLike(String value) {
            addCriterion("PAY_STATUS not like", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusIn(List<String> values) {
            addCriterion("PAY_STATUS in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotIn(List<String> values) {
            addCriterion("PAY_STATUS not in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusBetween(String value1, String value2) {
            addCriterion("PAY_STATUS between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotBetween(String value1, String value2) {
            addCriterion("PAY_STATUS not between", value1, value2, "payStatus");
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