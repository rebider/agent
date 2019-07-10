package com.ryx.internet.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InternetRenewOffsetExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public InternetRenewOffsetExample() {
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

        public Criteria andFlowIdIsNull() {
            addCriterion("FLOW_ID is null");
            return (Criteria) this;
        }

        public Criteria andFlowIdIsNotNull() {
            addCriterion("FLOW_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFlowIdEqualTo(String value) {
            addCriterion("FLOW_ID =", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotEqualTo(String value) {
            addCriterion("FLOW_ID <>", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdGreaterThan(String value) {
            addCriterion("FLOW_ID >", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdGreaterThanOrEqualTo(String value) {
            addCriterion("FLOW_ID >=", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLessThan(String value) {
            addCriterion("FLOW_ID <", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLessThanOrEqualTo(String value) {
            addCriterion("FLOW_ID <=", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLike(String value) {
            addCriterion("FLOW_ID like", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotLike(String value) {
            addCriterion("FLOW_ID not like", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdIn(List<String> values) {
            addCriterion("FLOW_ID in", values, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotIn(List<String> values) {
            addCriterion("FLOW_ID not in", values, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdBetween(String value1, String value2) {
            addCriterion("FLOW_ID between", value1, value2, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotBetween(String value1, String value2) {
            addCriterion("FLOW_ID not between", value1, value2, "flowId");
            return (Criteria) this;
        }

        public Criteria andRenewIdIsNull() {
            addCriterion("RENEW_ID is null");
            return (Criteria) this;
        }

        public Criteria andRenewIdIsNotNull() {
            addCriterion("RENEW_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRenewIdEqualTo(String value) {
            addCriterion("RENEW_ID =", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdNotEqualTo(String value) {
            addCriterion("RENEW_ID <>", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdGreaterThan(String value) {
            addCriterion("RENEW_ID >", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdGreaterThanOrEqualTo(String value) {
            addCriterion("RENEW_ID >=", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdLessThan(String value) {
            addCriterion("RENEW_ID <", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdLessThanOrEqualTo(String value) {
            addCriterion("RENEW_ID <=", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdLike(String value) {
            addCriterion("RENEW_ID like", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdNotLike(String value) {
            addCriterion("RENEW_ID not like", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdIn(List<String> values) {
            addCriterion("RENEW_ID in", values, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdNotIn(List<String> values) {
            addCriterion("RENEW_ID not in", values, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdBetween(String value1, String value2) {
            addCriterion("RENEW_ID between", value1, value2, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdNotBetween(String value1, String value2) {
            addCriterion("RENEW_ID not between", value1, value2, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdIsNull() {
            addCriterion("RENEW_DETAIL_ID is null");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdIsNotNull() {
            addCriterion("RENEW_DETAIL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdEqualTo(String value) {
            addCriterion("RENEW_DETAIL_ID =", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdNotEqualTo(String value) {
            addCriterion("RENEW_DETAIL_ID <>", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdGreaterThan(String value) {
            addCriterion("RENEW_DETAIL_ID >", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdGreaterThanOrEqualTo(String value) {
            addCriterion("RENEW_DETAIL_ID >=", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdLessThan(String value) {
            addCriterion("RENEW_DETAIL_ID <", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdLessThanOrEqualTo(String value) {
            addCriterion("RENEW_DETAIL_ID <=", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdLike(String value) {
            addCriterion("RENEW_DETAIL_ID like", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdNotLike(String value) {
            addCriterion("RENEW_DETAIL_ID not like", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdIn(List<String> values) {
            addCriterion("RENEW_DETAIL_ID in", values, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdNotIn(List<String> values) {
            addCriterion("RENEW_DETAIL_ID not in", values, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdBetween(String value1, String value2) {
            addCriterion("RENEW_DETAIL_ID between", value1, value2, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdNotBetween(String value1, String value2) {
            addCriterion("RENEW_DETAIL_ID not between", value1, value2, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andIccidNumIsNull() {
            addCriterion("ICCID_NUM is null");
            return (Criteria) this;
        }

        public Criteria andIccidNumIsNotNull() {
            addCriterion("ICCID_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andIccidNumEqualTo(String value) {
            addCriterion("ICCID_NUM =", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumNotEqualTo(String value) {
            addCriterion("ICCID_NUM <>", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumGreaterThan(String value) {
            addCriterion("ICCID_NUM >", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumGreaterThanOrEqualTo(String value) {
            addCriterion("ICCID_NUM >=", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumLessThan(String value) {
            addCriterion("ICCID_NUM <", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumLessThanOrEqualTo(String value) {
            addCriterion("ICCID_NUM <=", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumLike(String value) {
            addCriterion("ICCID_NUM like", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumNotLike(String value) {
            addCriterion("ICCID_NUM not like", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumIn(List<String> values) {
            addCriterion("ICCID_NUM in", values, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumNotIn(List<String> values) {
            addCriterion("ICCID_NUM not in", values, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumBetween(String value1, String value2) {
            addCriterion("ICCID_NUM between", value1, value2, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumNotBetween(String value1, String value2) {
            addCriterion("ICCID_NUM not between", value1, value2, "iccidNum");
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

        public Criteria andMerIdIsNull() {
            addCriterion("MER_ID is null");
            return (Criteria) this;
        }

        public Criteria andMerIdIsNotNull() {
            addCriterion("MER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMerIdEqualTo(String value) {
            addCriterion("MER_ID =", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotEqualTo(String value) {
            addCriterion("MER_ID <>", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdGreaterThan(String value) {
            addCriterion("MER_ID >", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdGreaterThanOrEqualTo(String value) {
            addCriterion("MER_ID >=", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdLessThan(String value) {
            addCriterion("MER_ID <", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdLessThanOrEqualTo(String value) {
            addCriterion("MER_ID <=", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdLike(String value) {
            addCriterion("MER_ID like", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotLike(String value) {
            addCriterion("MER_ID not like", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdIn(List<String> values) {
            addCriterion("MER_ID in", values, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotIn(List<String> values) {
            addCriterion("MER_ID not in", values, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdBetween(String value1, String value2) {
            addCriterion("MER_ID between", value1, value2, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotBetween(String value1, String value2) {
            addCriterion("MER_ID not between", value1, value2, "merId");
            return (Criteria) this;
        }

        public Criteria andMerNameIsNull() {
            addCriterion("MER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andMerNameIsNotNull() {
            addCriterion("MER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andMerNameEqualTo(String value) {
            addCriterion("MER_NAME =", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameNotEqualTo(String value) {
            addCriterion("MER_NAME <>", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameGreaterThan(String value) {
            addCriterion("MER_NAME >", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameGreaterThanOrEqualTo(String value) {
            addCriterion("MER_NAME >=", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameLessThan(String value) {
            addCriterion("MER_NAME <", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameLessThanOrEqualTo(String value) {
            addCriterion("MER_NAME <=", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameLike(String value) {
            addCriterion("MER_NAME like", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameNotLike(String value) {
            addCriterion("MER_NAME not like", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameIn(List<String> values) {
            addCriterion("MER_NAME in", values, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameNotIn(List<String> values) {
            addCriterion("MER_NAME not in", values, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameBetween(String value1, String value2) {
            addCriterion("MER_NAME between", value1, value2, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameNotBetween(String value1, String value2) {
            addCriterion("MER_NAME not between", value1, value2, "merName");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtIsNull() {
            addCriterion("OFFSET_AMT is null");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtIsNotNull() {
            addCriterion("OFFSET_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtEqualTo(BigDecimal value) {
            addCriterion("OFFSET_AMT =", value, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtNotEqualTo(BigDecimal value) {
            addCriterion("OFFSET_AMT <>", value, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtGreaterThan(BigDecimal value) {
            addCriterion("OFFSET_AMT >", value, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OFFSET_AMT >=", value, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtLessThan(BigDecimal value) {
            addCriterion("OFFSET_AMT <", value, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OFFSET_AMT <=", value, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtIn(List<BigDecimal> values) {
            addCriterion("OFFSET_AMT in", values, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtNotIn(List<BigDecimal> values) {
            addCriterion("OFFSET_AMT not in", values, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OFFSET_AMT between", value1, value2, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OFFSET_AMT not between", value1, value2, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtIsNull() {
            addCriterion("ALREADY_OFFSET_AMT is null");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtIsNotNull() {
            addCriterion("ALREADY_OFFSET_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtEqualTo(BigDecimal value) {
            addCriterion("ALREADY_OFFSET_AMT =", value, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtNotEqualTo(BigDecimal value) {
            addCriterion("ALREADY_OFFSET_AMT <>", value, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtGreaterThan(BigDecimal value) {
            addCriterion("ALREADY_OFFSET_AMT >", value, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ALREADY_OFFSET_AMT >=", value, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtLessThan(BigDecimal value) {
            addCriterion("ALREADY_OFFSET_AMT <", value, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ALREADY_OFFSET_AMT <=", value, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtIn(List<BigDecimal> values) {
            addCriterion("ALREADY_OFFSET_AMT in", values, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtNotIn(List<BigDecimal> values) {
            addCriterion("ALREADY_OFFSET_AMT not in", values, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ALREADY_OFFSET_AMT between", value1, value2, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ALREADY_OFFSET_AMT not between", value1, value2, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andProcessTimeIsNull() {
            addCriterion("PROCESS_TIME is null");
            return (Criteria) this;
        }

        public Criteria andProcessTimeIsNotNull() {
            addCriterion("PROCESS_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andProcessTimeEqualTo(Date value) {
            addCriterion("PROCESS_TIME =", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeNotEqualTo(Date value) {
            addCriterion("PROCESS_TIME <>", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeGreaterThan(Date value) {
            addCriterion("PROCESS_TIME >", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("PROCESS_TIME >=", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeLessThan(Date value) {
            addCriterion("PROCESS_TIME <", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeLessThanOrEqualTo(Date value) {
            addCriterion("PROCESS_TIME <=", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeIn(List<Date> values) {
            addCriterion("PROCESS_TIME in", values, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeNotIn(List<Date> values) {
            addCriterion("PROCESS_TIME not in", values, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeBetween(Date value1, Date value2) {
            addCriterion("PROCESS_TIME between", value1, value2, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeNotBetween(Date value1, Date value2) {
            addCriterion("PROCESS_TIME not between", value1, value2, "processTime");
            return (Criteria) this;
        }

        public Criteria andCTimeIsNull() {
            addCriterion("C_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCTimeIsNotNull() {
            addCriterion("C_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCTimeEqualTo(Date value) {
            addCriterion("C_TIME =", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotEqualTo(Date value) {
            addCriterion("C_TIME <>", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeGreaterThan(Date value) {
            addCriterion("C_TIME >", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("C_TIME >=", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeLessThan(Date value) {
            addCriterion("C_TIME <", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeLessThanOrEqualTo(Date value) {
            addCriterion("C_TIME <=", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeIn(List<Date> values) {
            addCriterion("C_TIME in", values, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotIn(List<Date> values) {
            addCriterion("C_TIME not in", values, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeBetween(Date value1, Date value2) {
            addCriterion("C_TIME between", value1, value2, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotBetween(Date value1, Date value2) {
            addCriterion("C_TIME not between", value1, value2, "cTime");
            return (Criteria) this;
        }

        public Criteria andCUserIsNull() {
            addCriterion("C_USER is null");
            return (Criteria) this;
        }

        public Criteria andCUserIsNotNull() {
            addCriterion("C_USER is not null");
            return (Criteria) this;
        }

        public Criteria andCUserEqualTo(String value) {
            addCriterion("C_USER =", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotEqualTo(String value) {
            addCriterion("C_USER <>", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserGreaterThan(String value) {
            addCriterion("C_USER >", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserGreaterThanOrEqualTo(String value) {
            addCriterion("C_USER >=", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserLessThan(String value) {
            addCriterion("C_USER <", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserLessThanOrEqualTo(String value) {
            addCriterion("C_USER <=", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserLike(String value) {
            addCriterion("C_USER like", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotLike(String value) {
            addCriterion("C_USER not like", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserIn(List<String> values) {
            addCriterion("C_USER in", values, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotIn(List<String> values) {
            addCriterion("C_USER not in", values, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserBetween(String value1, String value2) {
            addCriterion("C_USER between", value1, value2, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotBetween(String value1, String value2) {
            addCriterion("C_USER not between", value1, value2, "cUser");
            return (Criteria) this;
        }

        public Criteria andUUserIsNull() {
            addCriterion("U_USER is null");
            return (Criteria) this;
        }

        public Criteria andUUserIsNotNull() {
            addCriterion("U_USER is not null");
            return (Criteria) this;
        }

        public Criteria andUUserEqualTo(String value) {
            addCriterion("U_USER =", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotEqualTo(String value) {
            addCriterion("U_USER <>", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserGreaterThan(String value) {
            addCriterion("U_USER >", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserGreaterThanOrEqualTo(String value) {
            addCriterion("U_USER >=", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserLessThan(String value) {
            addCriterion("U_USER <", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserLessThanOrEqualTo(String value) {
            addCriterion("U_USER <=", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserLike(String value) {
            addCriterion("U_USER like", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotLike(String value) {
            addCriterion("U_USER not like", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserIn(List<String> values) {
            addCriterion("U_USER in", values, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotIn(List<String> values) {
            addCriterion("U_USER not in", values, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserBetween(String value1, String value2) {
            addCriterion("U_USER between", value1, value2, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotBetween(String value1, String value2) {
            addCriterion("U_USER not between", value1, value2, "uUser");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(BigDecimal value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(BigDecimal value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(BigDecimal value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(BigDecimal value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<BigDecimal> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<BigDecimal> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("VERSION is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(BigDecimal value) {
            addCriterion("VERSION =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(BigDecimal value) {
            addCriterion("VERSION <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(BigDecimal value) {
            addCriterion("VERSION >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("VERSION >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(BigDecimal value) {
            addCriterion("VERSION <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("VERSION <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<BigDecimal> values) {
            addCriterion("VERSION in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<BigDecimal> values) {
            addCriterion("VERSION not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("VERSION between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("VERSION not between", value1, value2, "version");
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