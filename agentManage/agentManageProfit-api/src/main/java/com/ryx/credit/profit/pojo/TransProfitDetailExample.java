package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransProfitDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public TransProfitDetailExample() {
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

        public Criteria andProfitDateIsNull() {
            addCriterion("PROFIT_DATE is null");
            return (Criteria) this;
        }

        public Criteria andProfitDateIsNotNull() {
            addCriterion("PROFIT_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andProfitDateEqualTo(String value) {
            addCriterion("PROFIT_DATE =", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateNotEqualTo(String value) {
            addCriterion("PROFIT_DATE <>", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateGreaterThan(String value) {
            addCriterion("PROFIT_DATE >", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateGreaterThanOrEqualTo(String value) {
            addCriterion("PROFIT_DATE >=", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateLessThan(String value) {
            addCriterion("PROFIT_DATE <", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateLessThanOrEqualTo(String value) {
            addCriterion("PROFIT_DATE <=", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateLike(String value) {
            addCriterion("PROFIT_DATE like", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateNotLike(String value) {
            addCriterion("PROFIT_DATE not like", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateIn(List<String> values) {
            addCriterion("PROFIT_DATE in", values, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateNotIn(List<String> values) {
            addCriterion("PROFIT_DATE not in", values, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateBetween(String value1, String value2) {
            addCriterion("PROFIT_DATE between", value1, value2, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateNotBetween(String value1, String value2) {
            addCriterion("PROFIT_DATE not between", value1, value2, "profitDate");
            return (Criteria) this;
        }

        public Criteria andInTransAmtIsNull() {
            addCriterion("IN_TRANS_AMT is null");
            return (Criteria) this;
        }

        public Criteria andInTransAmtIsNotNull() {
            addCriterion("IN_TRANS_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andInTransAmtEqualTo(BigDecimal value) {
            addCriterion("IN_TRANS_AMT =", value, "inTransAmt");
            return (Criteria) this;
        }

        public Criteria andInTransAmtNotEqualTo(BigDecimal value) {
            addCriterion("IN_TRANS_AMT <>", value, "inTransAmt");
            return (Criteria) this;
        }

        public Criteria andInTransAmtGreaterThan(BigDecimal value) {
            addCriterion("IN_TRANS_AMT >", value, "inTransAmt");
            return (Criteria) this;
        }

        public Criteria andInTransAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("IN_TRANS_AMT >=", value, "inTransAmt");
            return (Criteria) this;
        }

        public Criteria andInTransAmtLessThan(BigDecimal value) {
            addCriterion("IN_TRANS_AMT <", value, "inTransAmt");
            return (Criteria) this;
        }

        public Criteria andInTransAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("IN_TRANS_AMT <=", value, "inTransAmt");
            return (Criteria) this;
        }

        public Criteria andInTransAmtIn(List<BigDecimal> values) {
            addCriterion("IN_TRANS_AMT in", values, "inTransAmt");
            return (Criteria) this;
        }

        public Criteria andInTransAmtNotIn(List<BigDecimal> values) {
            addCriterion("IN_TRANS_AMT not in", values, "inTransAmt");
            return (Criteria) this;
        }

        public Criteria andInTransAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IN_TRANS_AMT between", value1, value2, "inTransAmt");
            return (Criteria) this;
        }

        public Criteria andInTransAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IN_TRANS_AMT not between", value1, value2, "inTransAmt");
            return (Criteria) this;
        }

        public Criteria andOutTransAmtIsNull() {
            addCriterion("OUT_TRANS_AMT is null");
            return (Criteria) this;
        }

        public Criteria andOutTransAmtIsNotNull() {
            addCriterion("OUT_TRANS_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andOutTransAmtEqualTo(BigDecimal value) {
            addCriterion("OUT_TRANS_AMT =", value, "outTransAmt");
            return (Criteria) this;
        }

        public Criteria andOutTransAmtNotEqualTo(BigDecimal value) {
            addCriterion("OUT_TRANS_AMT <>", value, "outTransAmt");
            return (Criteria) this;
        }

        public Criteria andOutTransAmtGreaterThan(BigDecimal value) {
            addCriterion("OUT_TRANS_AMT >", value, "outTransAmt");
            return (Criteria) this;
        }

        public Criteria andOutTransAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OUT_TRANS_AMT >=", value, "outTransAmt");
            return (Criteria) this;
        }

        public Criteria andOutTransAmtLessThan(BigDecimal value) {
            addCriterion("OUT_TRANS_AMT <", value, "outTransAmt");
            return (Criteria) this;
        }

        public Criteria andOutTransAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OUT_TRANS_AMT <=", value, "outTransAmt");
            return (Criteria) this;
        }

        public Criteria andOutTransAmtIn(List<BigDecimal> values) {
            addCriterion("OUT_TRANS_AMT in", values, "outTransAmt");
            return (Criteria) this;
        }

        public Criteria andOutTransAmtNotIn(List<BigDecimal> values) {
            addCriterion("OUT_TRANS_AMT not in", values, "outTransAmt");
            return (Criteria) this;
        }

        public Criteria andOutTransAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OUT_TRANS_AMT between", value1, value2, "outTransAmt");
            return (Criteria) this;
        }

        public Criteria andOutTransAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OUT_TRANS_AMT not between", value1, value2, "outTransAmt");
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

        public Criteria andInProfitScaleIsNull() {
            addCriterion("IN_PROFIT_SCALE is null");
            return (Criteria) this;
        }

        public Criteria andInProfitScaleIsNotNull() {
            addCriterion("IN_PROFIT_SCALE is not null");
            return (Criteria) this;
        }

        public Criteria andInProfitScaleEqualTo(BigDecimal value) {
            addCriterion("IN_PROFIT_SCALE =", value, "inProfitScale");
            return (Criteria) this;
        }

        public Criteria andInProfitScaleNotEqualTo(BigDecimal value) {
            addCriterion("IN_PROFIT_SCALE <>", value, "inProfitScale");
            return (Criteria) this;
        }

        public Criteria andInProfitScaleGreaterThan(BigDecimal value) {
            addCriterion("IN_PROFIT_SCALE >", value, "inProfitScale");
            return (Criteria) this;
        }

        public Criteria andInProfitScaleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("IN_PROFIT_SCALE >=", value, "inProfitScale");
            return (Criteria) this;
        }

        public Criteria andInProfitScaleLessThan(BigDecimal value) {
            addCriterion("IN_PROFIT_SCALE <", value, "inProfitScale");
            return (Criteria) this;
        }

        public Criteria andInProfitScaleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("IN_PROFIT_SCALE <=", value, "inProfitScale");
            return (Criteria) this;
        }

        public Criteria andInProfitScaleIn(List<BigDecimal> values) {
            addCriterion("IN_PROFIT_SCALE in", values, "inProfitScale");
            return (Criteria) this;
        }

        public Criteria andInProfitScaleNotIn(List<BigDecimal> values) {
            addCriterion("IN_PROFIT_SCALE not in", values, "inProfitScale");
            return (Criteria) this;
        }

        public Criteria andInProfitScaleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IN_PROFIT_SCALE between", value1, value2, "inProfitScale");
            return (Criteria) this;
        }

        public Criteria andInProfitScaleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IN_PROFIT_SCALE not between", value1, value2, "inProfitScale");
            return (Criteria) this;
        }

        public Criteria andOutProfitScaleIsNull() {
            addCriterion("OUT_PROFIT_SCALE is null");
            return (Criteria) this;
        }

        public Criteria andOutProfitScaleIsNotNull() {
            addCriterion("OUT_PROFIT_SCALE is not null");
            return (Criteria) this;
        }

        public Criteria andOutProfitScaleEqualTo(BigDecimal value) {
            addCriterion("OUT_PROFIT_SCALE =", value, "outProfitScale");
            return (Criteria) this;
        }

        public Criteria andOutProfitScaleNotEqualTo(BigDecimal value) {
            addCriterion("OUT_PROFIT_SCALE <>", value, "outProfitScale");
            return (Criteria) this;
        }

        public Criteria andOutProfitScaleGreaterThan(BigDecimal value) {
            addCriterion("OUT_PROFIT_SCALE >", value, "outProfitScale");
            return (Criteria) this;
        }

        public Criteria andOutProfitScaleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OUT_PROFIT_SCALE >=", value, "outProfitScale");
            return (Criteria) this;
        }

        public Criteria andOutProfitScaleLessThan(BigDecimal value) {
            addCriterion("OUT_PROFIT_SCALE <", value, "outProfitScale");
            return (Criteria) this;
        }

        public Criteria andOutProfitScaleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OUT_PROFIT_SCALE <=", value, "outProfitScale");
            return (Criteria) this;
        }

        public Criteria andOutProfitScaleIn(List<BigDecimal> values) {
            addCriterion("OUT_PROFIT_SCALE in", values, "outProfitScale");
            return (Criteria) this;
        }

        public Criteria andOutProfitScaleNotIn(List<BigDecimal> values) {
            addCriterion("OUT_PROFIT_SCALE not in", values, "outProfitScale");
            return (Criteria) this;
        }

        public Criteria andOutProfitScaleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OUT_PROFIT_SCALE between", value1, value2, "outProfitScale");
            return (Criteria) this;
        }

        public Criteria andOutProfitScaleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OUT_PROFIT_SCALE not between", value1, value2, "outProfitScale");
            return (Criteria) this;
        }

        public Criteria andInProfitAmtIsNull() {
            addCriterion("IN_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andInProfitAmtIsNotNull() {
            addCriterion("IN_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andInProfitAmtEqualTo(BigDecimal value) {
            addCriterion("IN_PROFIT_AMT =", value, "inProfitAmt");
            return (Criteria) this;
        }

        public Criteria andInProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("IN_PROFIT_AMT <>", value, "inProfitAmt");
            return (Criteria) this;
        }

        public Criteria andInProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("IN_PROFIT_AMT >", value, "inProfitAmt");
            return (Criteria) this;
        }

        public Criteria andInProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("IN_PROFIT_AMT >=", value, "inProfitAmt");
            return (Criteria) this;
        }

        public Criteria andInProfitAmtLessThan(BigDecimal value) {
            addCriterion("IN_PROFIT_AMT <", value, "inProfitAmt");
            return (Criteria) this;
        }

        public Criteria andInProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("IN_PROFIT_AMT <=", value, "inProfitAmt");
            return (Criteria) this;
        }

        public Criteria andInProfitAmtIn(List<BigDecimal> values) {
            addCriterion("IN_PROFIT_AMT in", values, "inProfitAmt");
            return (Criteria) this;
        }

        public Criteria andInProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("IN_PROFIT_AMT not in", values, "inProfitAmt");
            return (Criteria) this;
        }

        public Criteria andInProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IN_PROFIT_AMT between", value1, value2, "inProfitAmt");
            return (Criteria) this;
        }

        public Criteria andInProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IN_PROFIT_AMT not between", value1, value2, "inProfitAmt");
            return (Criteria) this;
        }

        public Criteria andOutProfitAmtIsNull() {
            addCriterion("OUT_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andOutProfitAmtIsNotNull() {
            addCriterion("OUT_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andOutProfitAmtEqualTo(BigDecimal value) {
            addCriterion("OUT_PROFIT_AMT =", value, "outProfitAmt");
            return (Criteria) this;
        }

        public Criteria andOutProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("OUT_PROFIT_AMT <>", value, "outProfitAmt");
            return (Criteria) this;
        }

        public Criteria andOutProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("OUT_PROFIT_AMT >", value, "outProfitAmt");
            return (Criteria) this;
        }

        public Criteria andOutProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OUT_PROFIT_AMT >=", value, "outProfitAmt");
            return (Criteria) this;
        }

        public Criteria andOutProfitAmtLessThan(BigDecimal value) {
            addCriterion("OUT_PROFIT_AMT <", value, "outProfitAmt");
            return (Criteria) this;
        }

        public Criteria andOutProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OUT_PROFIT_AMT <=", value, "outProfitAmt");
            return (Criteria) this;
        }

        public Criteria andOutProfitAmtIn(List<BigDecimal> values) {
            addCriterion("OUT_PROFIT_AMT in", values, "outProfitAmt");
            return (Criteria) this;
        }

        public Criteria andOutProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("OUT_PROFIT_AMT not in", values, "outProfitAmt");
            return (Criteria) this;
        }

        public Criteria andOutProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OUT_PROFIT_AMT between", value1, value2, "outProfitAmt");
            return (Criteria) this;
        }

        public Criteria andOutProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OUT_PROFIT_AMT not between", value1, value2, "outProfitAmt");
            return (Criteria) this;
        }

        public Criteria andPosCreditAmtIsNull() {
            addCriterion("POS_CREDIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPosCreditAmtIsNotNull() {
            addCriterion("POS_CREDIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPosCreditAmtEqualTo(BigDecimal value) {
            addCriterion("POS_CREDIT_AMT =", value, "posCreditAmt");
            return (Criteria) this;
        }

        public Criteria andPosCreditAmtNotEqualTo(BigDecimal value) {
            addCriterion("POS_CREDIT_AMT <>", value, "posCreditAmt");
            return (Criteria) this;
        }

        public Criteria andPosCreditAmtGreaterThan(BigDecimal value) {
            addCriterion("POS_CREDIT_AMT >", value, "posCreditAmt");
            return (Criteria) this;
        }

        public Criteria andPosCreditAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_CREDIT_AMT >=", value, "posCreditAmt");
            return (Criteria) this;
        }

        public Criteria andPosCreditAmtLessThan(BigDecimal value) {
            addCriterion("POS_CREDIT_AMT <", value, "posCreditAmt");
            return (Criteria) this;
        }

        public Criteria andPosCreditAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_CREDIT_AMT <=", value, "posCreditAmt");
            return (Criteria) this;
        }

        public Criteria andPosCreditAmtIn(List<BigDecimal> values) {
            addCriterion("POS_CREDIT_AMT in", values, "posCreditAmt");
            return (Criteria) this;
        }

        public Criteria andPosCreditAmtNotIn(List<BigDecimal> values) {
            addCriterion("POS_CREDIT_AMT not in", values, "posCreditAmt");
            return (Criteria) this;
        }

        public Criteria andPosCreditAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_CREDIT_AMT between", value1, value2, "posCreditAmt");
            return (Criteria) this;
        }

        public Criteria andPosCreditAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_CREDIT_AMT not between", value1, value2, "posCreditAmt");
            return (Criteria) this;
        }

        public Criteria andIposCreditAmtIsNull() {
            addCriterion("IPOS_CREDIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andIposCreditAmtIsNotNull() {
            addCriterion("IPOS_CREDIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andIposCreditAmtEqualTo(BigDecimal value) {
            addCriterion("IPOS_CREDIT_AMT =", value, "iposCreditAmt");
            return (Criteria) this;
        }

        public Criteria andIposCreditAmtNotEqualTo(BigDecimal value) {
            addCriterion("IPOS_CREDIT_AMT <>", value, "iposCreditAmt");
            return (Criteria) this;
        }

        public Criteria andIposCreditAmtGreaterThan(BigDecimal value) {
            addCriterion("IPOS_CREDIT_AMT >", value, "iposCreditAmt");
            return (Criteria) this;
        }

        public Criteria andIposCreditAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("IPOS_CREDIT_AMT >=", value, "iposCreditAmt");
            return (Criteria) this;
        }

        public Criteria andIposCreditAmtLessThan(BigDecimal value) {
            addCriterion("IPOS_CREDIT_AMT <", value, "iposCreditAmt");
            return (Criteria) this;
        }

        public Criteria andIposCreditAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("IPOS_CREDIT_AMT <=", value, "iposCreditAmt");
            return (Criteria) this;
        }

        public Criteria andIposCreditAmtIn(List<BigDecimal> values) {
            addCriterion("IPOS_CREDIT_AMT in", values, "iposCreditAmt");
            return (Criteria) this;
        }

        public Criteria andIposCreditAmtNotIn(List<BigDecimal> values) {
            addCriterion("IPOS_CREDIT_AMT not in", values, "iposCreditAmt");
            return (Criteria) this;
        }

        public Criteria andIposCreditAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IPOS_CREDIT_AMT between", value1, value2, "iposCreditAmt");
            return (Criteria) this;
        }

        public Criteria andIposCreditAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IPOS_CREDIT_AMT not between", value1, value2, "iposCreditAmt");
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

        public Criteria andNotaxAmtIsNull() {
            addCriterion("NOTAX_AMT is null");
            return (Criteria) this;
        }

        public Criteria andNotaxAmtIsNotNull() {
            addCriterion("NOTAX_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andNotaxAmtEqualTo(BigDecimal value) {
            addCriterion("NOTAX_AMT =", value, "notaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotaxAmtNotEqualTo(BigDecimal value) {
            addCriterion("NOTAX_AMT <>", value, "notaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotaxAmtGreaterThan(BigDecimal value) {
            addCriterion("NOTAX_AMT >", value, "notaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotaxAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NOTAX_AMT >=", value, "notaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotaxAmtLessThan(BigDecimal value) {
            addCriterion("NOTAX_AMT <", value, "notaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotaxAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NOTAX_AMT <=", value, "notaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotaxAmtIn(List<BigDecimal> values) {
            addCriterion("NOTAX_AMT in", values, "notaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotaxAmtNotIn(List<BigDecimal> values) {
            addCriterion("NOTAX_AMT not in", values, "notaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotaxAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NOTAX_AMT between", value1, value2, "notaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotaxAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NOTAX_AMT not between", value1, value2, "notaxAmt");
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

        public Criteria andUnicodeIsNull() {
            addCriterion("UNICODE is null");
            return (Criteria) this;
        }

        public Criteria andUnicodeIsNotNull() {
            addCriterion("UNICODE is not null");
            return (Criteria) this;
        }

        public Criteria andUnicodeEqualTo(String value) {
            addCriterion("UNICODE =", value, "unicode");
            return (Criteria) this;
        }

        public Criteria andUnicodeNotEqualTo(String value) {
            addCriterion("UNICODE <>", value, "unicode");
            return (Criteria) this;
        }

        public Criteria andUnicodeGreaterThan(String value) {
            addCriterion("UNICODE >", value, "unicode");
            return (Criteria) this;
        }

        public Criteria andUnicodeGreaterThanOrEqualTo(String value) {
            addCriterion("UNICODE >=", value, "unicode");
            return (Criteria) this;
        }

        public Criteria andUnicodeLessThan(String value) {
            addCriterion("UNICODE <", value, "unicode");
            return (Criteria) this;
        }

        public Criteria andUnicodeLessThanOrEqualTo(String value) {
            addCriterion("UNICODE <=", value, "unicode");
            return (Criteria) this;
        }

        public Criteria andUnicodeLike(String value) {
            addCriterion("UNICODE like", value, "unicode");
            return (Criteria) this;
        }

        public Criteria andUnicodeNotLike(String value) {
            addCriterion("UNICODE not like", value, "unicode");
            return (Criteria) this;
        }

        public Criteria andUnicodeIn(List<String> values) {
            addCriterion("UNICODE in", values, "unicode");
            return (Criteria) this;
        }

        public Criteria andUnicodeNotIn(List<String> values) {
            addCriterion("UNICODE not in", values, "unicode");
            return (Criteria) this;
        }

        public Criteria andUnicodeBetween(String value1, String value2) {
            addCriterion("UNICODE between", value1, value2, "unicode");
            return (Criteria) this;
        }

        public Criteria andUnicodeNotBetween(String value1, String value2) {
            addCriterion("UNICODE not between", value1, value2, "unicode");
            return (Criteria) this;
        }

        public Criteria andSourceInfoIsNull() {
            addCriterion("SOURCE_INFO is null");
            return (Criteria) this;
        }

        public Criteria andSourceInfoIsNotNull() {
            addCriterion("SOURCE_INFO is not null");
            return (Criteria) this;
        }

        public Criteria andSourceInfoEqualTo(String value) {
            addCriterion("SOURCE_INFO =", value, "sourceInfo");
            return (Criteria) this;
        }

        public Criteria andSourceInfoNotEqualTo(String value) {
            addCriterion("SOURCE_INFO <>", value, "sourceInfo");
            return (Criteria) this;
        }

        public Criteria andSourceInfoGreaterThan(String value) {
            addCriterion("SOURCE_INFO >", value, "sourceInfo");
            return (Criteria) this;
        }

        public Criteria andSourceInfoGreaterThanOrEqualTo(String value) {
            addCriterion("SOURCE_INFO >=", value, "sourceInfo");
            return (Criteria) this;
        }

        public Criteria andSourceInfoLessThan(String value) {
            addCriterion("SOURCE_INFO <", value, "sourceInfo");
            return (Criteria) this;
        }

        public Criteria andSourceInfoLessThanOrEqualTo(String value) {
            addCriterion("SOURCE_INFO <=", value, "sourceInfo");
            return (Criteria) this;
        }

        public Criteria andSourceInfoLike(String value) {
            addCriterion("SOURCE_INFO like", value, "sourceInfo");
            return (Criteria) this;
        }

        public Criteria andSourceInfoNotLike(String value) {
            addCriterion("SOURCE_INFO not like", value, "sourceInfo");
            return (Criteria) this;
        }

        public Criteria andSourceInfoIn(List<String> values) {
            addCriterion("SOURCE_INFO in", values, "sourceInfo");
            return (Criteria) this;
        }

        public Criteria andSourceInfoNotIn(List<String> values) {
            addCriterion("SOURCE_INFO not in", values, "sourceInfo");
            return (Criteria) this;
        }

        public Criteria andSourceInfoBetween(String value1, String value2) {
            addCriterion("SOURCE_INFO between", value1, value2, "sourceInfo");
            return (Criteria) this;
        }

        public Criteria andSourceInfoNotBetween(String value1, String value2) {
            addCriterion("SOURCE_INFO not between", value1, value2, "sourceInfo");
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

        public Criteria andUnlineAmtIsNull() {
            addCriterion("UNLINE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andUnlineAmtIsNotNull() {
            addCriterion("UNLINE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andUnlineAmtEqualTo(BigDecimal value) {
            addCriterion("UNLINE_AMT =", value, "unlineAmt");
            return (Criteria) this;
        }

        public Criteria andUnlineAmtNotEqualTo(BigDecimal value) {
            addCriterion("UNLINE_AMT <>", value, "unlineAmt");
            return (Criteria) this;
        }

        public Criteria andUnlineAmtGreaterThan(BigDecimal value) {
            addCriterion("UNLINE_AMT >", value, "unlineAmt");
            return (Criteria) this;
        }

        public Criteria andUnlineAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("UNLINE_AMT >=", value, "unlineAmt");
            return (Criteria) this;
        }

        public Criteria andUnlineAmtLessThan(BigDecimal value) {
            addCriterion("UNLINE_AMT <", value, "unlineAmt");
            return (Criteria) this;
        }

        public Criteria andUnlineAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("UNLINE_AMT <=", value, "unlineAmt");
            return (Criteria) this;
        }

        public Criteria andUnlineAmtIn(List<BigDecimal> values) {
            addCriterion("UNLINE_AMT in", values, "unlineAmt");
            return (Criteria) this;
        }

        public Criteria andUnlineAmtNotIn(List<BigDecimal> values) {
            addCriterion("UNLINE_AMT not in", values, "unlineAmt");
            return (Criteria) this;
        }

        public Criteria andUnlineAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("UNLINE_AMT between", value1, value2, "unlineAmt");
            return (Criteria) this;
        }

        public Criteria andUnlineAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("UNLINE_AMT not between", value1, value2, "unlineAmt");
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

        public Criteria andPosTranFeeIsNull() {
            addCriterion("POS_TRAN_FEE is null");
            return (Criteria) this;
        }

        public Criteria andPosTranFeeIsNotNull() {
            addCriterion("POS_TRAN_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andPosTranFeeEqualTo(BigDecimal value) {
            addCriterion("POS_TRAN_FEE =", value, "posTranFee");
            return (Criteria) this;
        }

        public Criteria andPosTranFeeNotEqualTo(BigDecimal value) {
            addCriterion("POS_TRAN_FEE <>", value, "posTranFee");
            return (Criteria) this;
        }

        public Criteria andPosTranFeeGreaterThan(BigDecimal value) {
            addCriterion("POS_TRAN_FEE >", value, "posTranFee");
            return (Criteria) this;
        }

        public Criteria andPosTranFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_TRAN_FEE >=", value, "posTranFee");
            return (Criteria) this;
        }

        public Criteria andPosTranFeeLessThan(BigDecimal value) {
            addCriterion("POS_TRAN_FEE <", value, "posTranFee");
            return (Criteria) this;
        }

        public Criteria andPosTranFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_TRAN_FEE <=", value, "posTranFee");
            return (Criteria) this;
        }

        public Criteria andPosTranFeeIn(List<BigDecimal> values) {
            addCriterion("POS_TRAN_FEE in", values, "posTranFee");
            return (Criteria) this;
        }

        public Criteria andPosTranFeeNotIn(List<BigDecimal> values) {
            addCriterion("POS_TRAN_FEE not in", values, "posTranFee");
            return (Criteria) this;
        }

        public Criteria andPosTranFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_TRAN_FEE between", value1, value2, "posTranFee");
            return (Criteria) this;
        }

        public Criteria andPosTranFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_TRAN_FEE not between", value1, value2, "posTranFee");
            return (Criteria) this;
        }

        public Criteria andPosPftAmtIsNull() {
            addCriterion("POS_PFT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPosPftAmtIsNotNull() {
            addCriterion("POS_PFT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPosPftAmtEqualTo(BigDecimal value) {
            addCriterion("POS_PFT_AMT =", value, "posPftAmt");
            return (Criteria) this;
        }

        public Criteria andPosPftAmtNotEqualTo(BigDecimal value) {
            addCriterion("POS_PFT_AMT <>", value, "posPftAmt");
            return (Criteria) this;
        }

        public Criteria andPosPftAmtGreaterThan(BigDecimal value) {
            addCriterion("POS_PFT_AMT >", value, "posPftAmt");
            return (Criteria) this;
        }

        public Criteria andPosPftAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_PFT_AMT >=", value, "posPftAmt");
            return (Criteria) this;
        }

        public Criteria andPosPftAmtLessThan(BigDecimal value) {
            addCriterion("POS_PFT_AMT <", value, "posPftAmt");
            return (Criteria) this;
        }

        public Criteria andPosPftAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_PFT_AMT <=", value, "posPftAmt");
            return (Criteria) this;
        }

        public Criteria andPosPftAmtIn(List<BigDecimal> values) {
            addCriterion("POS_PFT_AMT in", values, "posPftAmt");
            return (Criteria) this;
        }

        public Criteria andPosPftAmtNotIn(List<BigDecimal> values) {
            addCriterion("POS_PFT_AMT not in", values, "posPftAmt");
            return (Criteria) this;
        }

        public Criteria andPosPftAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_PFT_AMT between", value1, value2, "posPftAmt");
            return (Criteria) this;
        }

        public Criteria andPosPftAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_PFT_AMT not between", value1, value2, "posPftAmt");
            return (Criteria) this;
        }

        public Criteria andQrTranAmtIsNull() {
            addCriterion("QR_TRAN_AMT is null");
            return (Criteria) this;
        }

        public Criteria andQrTranAmtIsNotNull() {
            addCriterion("QR_TRAN_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andQrTranAmtEqualTo(BigDecimal value) {
            addCriterion("QR_TRAN_AMT =", value, "qrTranAmt");
            return (Criteria) this;
        }

        public Criteria andQrTranAmtNotEqualTo(BigDecimal value) {
            addCriterion("QR_TRAN_AMT <>", value, "qrTranAmt");
            return (Criteria) this;
        }

        public Criteria andQrTranAmtGreaterThan(BigDecimal value) {
            addCriterion("QR_TRAN_AMT >", value, "qrTranAmt");
            return (Criteria) this;
        }

        public Criteria andQrTranAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("QR_TRAN_AMT >=", value, "qrTranAmt");
            return (Criteria) this;
        }

        public Criteria andQrTranAmtLessThan(BigDecimal value) {
            addCriterion("QR_TRAN_AMT <", value, "qrTranAmt");
            return (Criteria) this;
        }

        public Criteria andQrTranAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("QR_TRAN_AMT <=", value, "qrTranAmt");
            return (Criteria) this;
        }

        public Criteria andQrTranAmtIn(List<BigDecimal> values) {
            addCriterion("QR_TRAN_AMT in", values, "qrTranAmt");
            return (Criteria) this;
        }

        public Criteria andQrTranAmtNotIn(List<BigDecimal> values) {
            addCriterion("QR_TRAN_AMT not in", values, "qrTranAmt");
            return (Criteria) this;
        }

        public Criteria andQrTranAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("QR_TRAN_AMT between", value1, value2, "qrTranAmt");
            return (Criteria) this;
        }

        public Criteria andQrTranAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("QR_TRAN_AMT not between", value1, value2, "qrTranAmt");
            return (Criteria) this;
        }

        public Criteria andQrTranFeeIsNull() {
            addCriterion("QR_TRAN_FEE is null");
            return (Criteria) this;
        }

        public Criteria andQrTranFeeIsNotNull() {
            addCriterion("QR_TRAN_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andQrTranFeeEqualTo(BigDecimal value) {
            addCriterion("QR_TRAN_FEE =", value, "qrTranFee");
            return (Criteria) this;
        }

        public Criteria andQrTranFeeNotEqualTo(BigDecimal value) {
            addCriterion("QR_TRAN_FEE <>", value, "qrTranFee");
            return (Criteria) this;
        }

        public Criteria andQrTranFeeGreaterThan(BigDecimal value) {
            addCriterion("QR_TRAN_FEE >", value, "qrTranFee");
            return (Criteria) this;
        }

        public Criteria andQrTranFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("QR_TRAN_FEE >=", value, "qrTranFee");
            return (Criteria) this;
        }

        public Criteria andQrTranFeeLessThan(BigDecimal value) {
            addCriterion("QR_TRAN_FEE <", value, "qrTranFee");
            return (Criteria) this;
        }

        public Criteria andQrTranFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("QR_TRAN_FEE <=", value, "qrTranFee");
            return (Criteria) this;
        }

        public Criteria andQrTranFeeIn(List<BigDecimal> values) {
            addCriterion("QR_TRAN_FEE in", values, "qrTranFee");
            return (Criteria) this;
        }

        public Criteria andQrTranFeeNotIn(List<BigDecimal> values) {
            addCriterion("QR_TRAN_FEE not in", values, "qrTranFee");
            return (Criteria) this;
        }

        public Criteria andQrTranFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("QR_TRAN_FEE between", value1, value2, "qrTranFee");
            return (Criteria) this;
        }

        public Criteria andQrTranFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("QR_TRAN_FEE not between", value1, value2, "qrTranFee");
            return (Criteria) this;
        }

        public Criteria andQrPftAmtIsNull() {
            addCriterion("QR_PFT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andQrPftAmtIsNotNull() {
            addCriterion("QR_PFT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andQrPftAmtEqualTo(BigDecimal value) {
            addCriterion("QR_PFT_AMT =", value, "qrPftAmt");
            return (Criteria) this;
        }

        public Criteria andQrPftAmtNotEqualTo(BigDecimal value) {
            addCriterion("QR_PFT_AMT <>", value, "qrPftAmt");
            return (Criteria) this;
        }

        public Criteria andQrPftAmtGreaterThan(BigDecimal value) {
            addCriterion("QR_PFT_AMT >", value, "qrPftAmt");
            return (Criteria) this;
        }

        public Criteria andQrPftAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("QR_PFT_AMT >=", value, "qrPftAmt");
            return (Criteria) this;
        }

        public Criteria andQrPftAmtLessThan(BigDecimal value) {
            addCriterion("QR_PFT_AMT <", value, "qrPftAmt");
            return (Criteria) this;
        }

        public Criteria andQrPftAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("QR_PFT_AMT <=", value, "qrPftAmt");
            return (Criteria) this;
        }

        public Criteria andQrPftAmtIn(List<BigDecimal> values) {
            addCriterion("QR_PFT_AMT in", values, "qrPftAmt");
            return (Criteria) this;
        }

        public Criteria andQrPftAmtNotIn(List<BigDecimal> values) {
            addCriterion("QR_PFT_AMT not in", values, "qrPftAmt");
            return (Criteria) this;
        }

        public Criteria andQrPftAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("QR_PFT_AMT between", value1, value2, "qrPftAmt");
            return (Criteria) this;
        }

        public Criteria andQrPftAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("QR_PFT_AMT not between", value1, value2, "qrPftAmt");
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