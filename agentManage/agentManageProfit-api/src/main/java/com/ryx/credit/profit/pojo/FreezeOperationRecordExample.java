package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FreezeOperationRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public FreezeOperationRecordExample() {
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

        public Criteria andFreezeTypeIsNull() {
            addCriterion("FREEZE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeIsNotNull() {
            addCriterion("FREEZE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeEqualTo(String value) {
            addCriterion("FREEZE_TYPE =", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeNotEqualTo(String value) {
            addCriterion("FREEZE_TYPE <>", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeGreaterThan(String value) {
            addCriterion("FREEZE_TYPE >", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_TYPE >=", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeLessThan(String value) {
            addCriterion("FREEZE_TYPE <", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_TYPE <=", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeLike(String value) {
            addCriterion("FREEZE_TYPE like", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeNotLike(String value) {
            addCriterion("FREEZE_TYPE not like", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeIn(List<String> values) {
            addCriterion("FREEZE_TYPE in", values, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeNotIn(List<String> values) {
            addCriterion("FREEZE_TYPE not in", values, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeBetween(String value1, String value2) {
            addCriterion("FREEZE_TYPE between", value1, value2, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeNotBetween(String value1, String value2) {
            addCriterion("FREEZE_TYPE not between", value1, value2, "freezeType");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("STATUS like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("STATUS not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtIsNull() {
            addCriterion("FREEZE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtIsNotNull() {
            addCriterion("FREEZE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtEqualTo(BigDecimal value) {
            addCriterion("FREEZE_AMT =", value, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtNotEqualTo(BigDecimal value) {
            addCriterion("FREEZE_AMT <>", value, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtGreaterThan(BigDecimal value) {
            addCriterion("FREEZE_AMT >", value, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FREEZE_AMT >=", value, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtLessThan(BigDecimal value) {
            addCriterion("FREEZE_AMT <", value, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FREEZE_AMT <=", value, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtIn(List<BigDecimal> values) {
            addCriterion("FREEZE_AMT in", values, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtNotIn(List<BigDecimal> values) {
            addCriterion("FREEZE_AMT not in", values, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FREEZE_AMT between", value1, value2, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FREEZE_AMT not between", value1, value2, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andThawAmtIsNull() {
            addCriterion("THAW_AMT is null");
            return (Criteria) this;
        }

        public Criteria andThawAmtIsNotNull() {
            addCriterion("THAW_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andThawAmtEqualTo(BigDecimal value) {
            addCriterion("THAW_AMT =", value, "thawAmt");
            return (Criteria) this;
        }

        public Criteria andThawAmtNotEqualTo(BigDecimal value) {
            addCriterion("THAW_AMT <>", value, "thawAmt");
            return (Criteria) this;
        }

        public Criteria andThawAmtGreaterThan(BigDecimal value) {
            addCriterion("THAW_AMT >", value, "thawAmt");
            return (Criteria) this;
        }

        public Criteria andThawAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("THAW_AMT >=", value, "thawAmt");
            return (Criteria) this;
        }

        public Criteria andThawAmtLessThan(BigDecimal value) {
            addCriterion("THAW_AMT <", value, "thawAmt");
            return (Criteria) this;
        }

        public Criteria andThawAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("THAW_AMT <=", value, "thawAmt");
            return (Criteria) this;
        }

        public Criteria andThawAmtIn(List<BigDecimal> values) {
            addCriterion("THAW_AMT in", values, "thawAmt");
            return (Criteria) this;
        }

        public Criteria andThawAmtNotIn(List<BigDecimal> values) {
            addCriterion("THAW_AMT not in", values, "thawAmt");
            return (Criteria) this;
        }

        public Criteria andThawAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("THAW_AMT between", value1, value2, "thawAmt");
            return (Criteria) this;
        }

        public Criteria andThawAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("THAW_AMT not between", value1, value2, "thawAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeTimeIsNull() {
            addCriterion("FREEZE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andFreezeTimeIsNotNull() {
            addCriterion("FREEZE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeTimeEqualTo(String value) {
            addCriterion("FREEZE_TIME =", value, "freezeTime");
            return (Criteria) this;
        }

        public Criteria andFreezeTimeNotEqualTo(String value) {
            addCriterion("FREEZE_TIME <>", value, "freezeTime");
            return (Criteria) this;
        }

        public Criteria andFreezeTimeGreaterThan(String value) {
            addCriterion("FREEZE_TIME >", value, "freezeTime");
            return (Criteria) this;
        }

        public Criteria andFreezeTimeGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_TIME >=", value, "freezeTime");
            return (Criteria) this;
        }

        public Criteria andFreezeTimeLessThan(String value) {
            addCriterion("FREEZE_TIME <", value, "freezeTime");
            return (Criteria) this;
        }

        public Criteria andFreezeTimeLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_TIME <=", value, "freezeTime");
            return (Criteria) this;
        }

        public Criteria andFreezeTimeLike(String value) {
            addCriterion("FREEZE_TIME like", value, "freezeTime");
            return (Criteria) this;
        }

        public Criteria andFreezeTimeNotLike(String value) {
            addCriterion("FREEZE_TIME not like", value, "freezeTime");
            return (Criteria) this;
        }

        public Criteria andFreezeTimeIn(List<String> values) {
            addCriterion("FREEZE_TIME in", values, "freezeTime");
            return (Criteria) this;
        }

        public Criteria andFreezeTimeNotIn(List<String> values) {
            addCriterion("FREEZE_TIME not in", values, "freezeTime");
            return (Criteria) this;
        }

        public Criteria andFreezeTimeBetween(String value1, String value2) {
            addCriterion("FREEZE_TIME between", value1, value2, "freezeTime");
            return (Criteria) this;
        }

        public Criteria andFreezeTimeNotBetween(String value1, String value2) {
            addCriterion("FREEZE_TIME not between", value1, value2, "freezeTime");
            return (Criteria) this;
        }

        public Criteria andFreezeReasonIsNull() {
            addCriterion("FREEZE_REASON is null");
            return (Criteria) this;
        }

        public Criteria andFreezeReasonIsNotNull() {
            addCriterion("FREEZE_REASON is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeReasonEqualTo(String value) {
            addCriterion("FREEZE_REASON =", value, "freezeReason");
            return (Criteria) this;
        }

        public Criteria andFreezeReasonNotEqualTo(String value) {
            addCriterion("FREEZE_REASON <>", value, "freezeReason");
            return (Criteria) this;
        }

        public Criteria andFreezeReasonGreaterThan(String value) {
            addCriterion("FREEZE_REASON >", value, "freezeReason");
            return (Criteria) this;
        }

        public Criteria andFreezeReasonGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_REASON >=", value, "freezeReason");
            return (Criteria) this;
        }

        public Criteria andFreezeReasonLessThan(String value) {
            addCriterion("FREEZE_REASON <", value, "freezeReason");
            return (Criteria) this;
        }

        public Criteria andFreezeReasonLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_REASON <=", value, "freezeReason");
            return (Criteria) this;
        }

        public Criteria andFreezeReasonLike(String value) {
            addCriterion("FREEZE_REASON like", value, "freezeReason");
            return (Criteria) this;
        }

        public Criteria andFreezeReasonNotLike(String value) {
            addCriterion("FREEZE_REASON not like", value, "freezeReason");
            return (Criteria) this;
        }

        public Criteria andFreezeReasonIn(List<String> values) {
            addCriterion("FREEZE_REASON in", values, "freezeReason");
            return (Criteria) this;
        }

        public Criteria andFreezeReasonNotIn(List<String> values) {
            addCriterion("FREEZE_REASON not in", values, "freezeReason");
            return (Criteria) this;
        }

        public Criteria andFreezeReasonBetween(String value1, String value2) {
            addCriterion("FREEZE_REASON between", value1, value2, "freezeReason");
            return (Criteria) this;
        }

        public Criteria andFreezeReasonNotBetween(String value1, String value2) {
            addCriterion("FREEZE_REASON not between", value1, value2, "freezeReason");
            return (Criteria) this;
        }

        public Criteria andThawInitiateTimeIsNull() {
            addCriterion("THAW_INITIATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andThawInitiateTimeIsNotNull() {
            addCriterion("THAW_INITIATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andThawInitiateTimeEqualTo(String value) {
            addCriterion("THAW_INITIATE_TIME =", value, "thawInitiateTime");
            return (Criteria) this;
        }

        public Criteria andThawInitiateTimeNotEqualTo(String value) {
            addCriterion("THAW_INITIATE_TIME <>", value, "thawInitiateTime");
            return (Criteria) this;
        }

        public Criteria andThawInitiateTimeGreaterThan(String value) {
            addCriterion("THAW_INITIATE_TIME >", value, "thawInitiateTime");
            return (Criteria) this;
        }

        public Criteria andThawInitiateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("THAW_INITIATE_TIME >=", value, "thawInitiateTime");
            return (Criteria) this;
        }

        public Criteria andThawInitiateTimeLessThan(String value) {
            addCriterion("THAW_INITIATE_TIME <", value, "thawInitiateTime");
            return (Criteria) this;
        }

        public Criteria andThawInitiateTimeLessThanOrEqualTo(String value) {
            addCriterion("THAW_INITIATE_TIME <=", value, "thawInitiateTime");
            return (Criteria) this;
        }

        public Criteria andThawInitiateTimeLike(String value) {
            addCriterion("THAW_INITIATE_TIME like", value, "thawInitiateTime");
            return (Criteria) this;
        }

        public Criteria andThawInitiateTimeNotLike(String value) {
            addCriterion("THAW_INITIATE_TIME not like", value, "thawInitiateTime");
            return (Criteria) this;
        }

        public Criteria andThawInitiateTimeIn(List<String> values) {
            addCriterion("THAW_INITIATE_TIME in", values, "thawInitiateTime");
            return (Criteria) this;
        }

        public Criteria andThawInitiateTimeNotIn(List<String> values) {
            addCriterion("THAW_INITIATE_TIME not in", values, "thawInitiateTime");
            return (Criteria) this;
        }

        public Criteria andThawInitiateTimeBetween(String value1, String value2) {
            addCriterion("THAW_INITIATE_TIME between", value1, value2, "thawInitiateTime");
            return (Criteria) this;
        }

        public Criteria andThawInitiateTimeNotBetween(String value1, String value2) {
            addCriterion("THAW_INITIATE_TIME not between", value1, value2, "thawInitiateTime");
            return (Criteria) this;
        }

        public Criteria andThawTimeIsNull() {
            addCriterion("THAW_TIME is null");
            return (Criteria) this;
        }

        public Criteria andThawTimeIsNotNull() {
            addCriterion("THAW_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andThawTimeEqualTo(String value) {
            addCriterion("THAW_TIME =", value, "thawTime");
            return (Criteria) this;
        }

        public Criteria andThawTimeNotEqualTo(String value) {
            addCriterion("THAW_TIME <>", value, "thawTime");
            return (Criteria) this;
        }

        public Criteria andThawTimeGreaterThan(String value) {
            addCriterion("THAW_TIME >", value, "thawTime");
            return (Criteria) this;
        }

        public Criteria andThawTimeGreaterThanOrEqualTo(String value) {
            addCriterion("THAW_TIME >=", value, "thawTime");
            return (Criteria) this;
        }

        public Criteria andThawTimeLessThan(String value) {
            addCriterion("THAW_TIME <", value, "thawTime");
            return (Criteria) this;
        }

        public Criteria andThawTimeLessThanOrEqualTo(String value) {
            addCriterion("THAW_TIME <=", value, "thawTime");
            return (Criteria) this;
        }

        public Criteria andThawTimeLike(String value) {
            addCriterion("THAW_TIME like", value, "thawTime");
            return (Criteria) this;
        }

        public Criteria andThawTimeNotLike(String value) {
            addCriterion("THAW_TIME not like", value, "thawTime");
            return (Criteria) this;
        }

        public Criteria andThawTimeIn(List<String> values) {
            addCriterion("THAW_TIME in", values, "thawTime");
            return (Criteria) this;
        }

        public Criteria andThawTimeNotIn(List<String> values) {
            addCriterion("THAW_TIME not in", values, "thawTime");
            return (Criteria) this;
        }

        public Criteria andThawTimeBetween(String value1, String value2) {
            addCriterion("THAW_TIME between", value1, value2, "thawTime");
            return (Criteria) this;
        }

        public Criteria andThawTimeNotBetween(String value1, String value2) {
            addCriterion("THAW_TIME not between", value1, value2, "thawTime");
            return (Criteria) this;
        }

        public Criteria andThawReasonIsNull() {
            addCriterion("THAW_REASON is null");
            return (Criteria) this;
        }

        public Criteria andThawReasonIsNotNull() {
            addCriterion("THAW_REASON is not null");
            return (Criteria) this;
        }

        public Criteria andThawReasonEqualTo(String value) {
            addCriterion("THAW_REASON =", value, "thawReason");
            return (Criteria) this;
        }

        public Criteria andThawReasonNotEqualTo(String value) {
            addCriterion("THAW_REASON <>", value, "thawReason");
            return (Criteria) this;
        }

        public Criteria andThawReasonGreaterThan(String value) {
            addCriterion("THAW_REASON >", value, "thawReason");
            return (Criteria) this;
        }

        public Criteria andThawReasonGreaterThanOrEqualTo(String value) {
            addCriterion("THAW_REASON >=", value, "thawReason");
            return (Criteria) this;
        }

        public Criteria andThawReasonLessThan(String value) {
            addCriterion("THAW_REASON <", value, "thawReason");
            return (Criteria) this;
        }

        public Criteria andThawReasonLessThanOrEqualTo(String value) {
            addCriterion("THAW_REASON <=", value, "thawReason");
            return (Criteria) this;
        }

        public Criteria andThawReasonLike(String value) {
            addCriterion("THAW_REASON like", value, "thawReason");
            return (Criteria) this;
        }

        public Criteria andThawReasonNotLike(String value) {
            addCriterion("THAW_REASON not like", value, "thawReason");
            return (Criteria) this;
        }

        public Criteria andThawReasonIn(List<String> values) {
            addCriterion("THAW_REASON in", values, "thawReason");
            return (Criteria) this;
        }

        public Criteria andThawReasonNotIn(List<String> values) {
            addCriterion("THAW_REASON not in", values, "thawReason");
            return (Criteria) this;
        }

        public Criteria andThawReasonBetween(String value1, String value2) {
            addCriterion("THAW_REASON between", value1, value2, "thawReason");
            return (Criteria) this;
        }

        public Criteria andThawReasonNotBetween(String value1, String value2) {
            addCriterion("THAW_REASON not between", value1, value2, "thawReason");
            return (Criteria) this;
        }

        public Criteria andFreezeOperatorIsNull() {
            addCriterion("FREEZE_OPERATOR is null");
            return (Criteria) this;
        }

        public Criteria andFreezeOperatorIsNotNull() {
            addCriterion("FREEZE_OPERATOR is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeOperatorEqualTo(String value) {
            addCriterion("FREEZE_OPERATOR =", value, "freezeOperator");
            return (Criteria) this;
        }

        public Criteria andFreezeOperatorNotEqualTo(String value) {
            addCriterion("FREEZE_OPERATOR <>", value, "freezeOperator");
            return (Criteria) this;
        }

        public Criteria andFreezeOperatorGreaterThan(String value) {
            addCriterion("FREEZE_OPERATOR >", value, "freezeOperator");
            return (Criteria) this;
        }

        public Criteria andFreezeOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_OPERATOR >=", value, "freezeOperator");
            return (Criteria) this;
        }

        public Criteria andFreezeOperatorLessThan(String value) {
            addCriterion("FREEZE_OPERATOR <", value, "freezeOperator");
            return (Criteria) this;
        }

        public Criteria andFreezeOperatorLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_OPERATOR <=", value, "freezeOperator");
            return (Criteria) this;
        }

        public Criteria andFreezeOperatorLike(String value) {
            addCriterion("FREEZE_OPERATOR like", value, "freezeOperator");
            return (Criteria) this;
        }

        public Criteria andFreezeOperatorNotLike(String value) {
            addCriterion("FREEZE_OPERATOR not like", value, "freezeOperator");
            return (Criteria) this;
        }

        public Criteria andFreezeOperatorIn(List<String> values) {
            addCriterion("FREEZE_OPERATOR in", values, "freezeOperator");
            return (Criteria) this;
        }

        public Criteria andFreezeOperatorNotIn(List<String> values) {
            addCriterion("FREEZE_OPERATOR not in", values, "freezeOperator");
            return (Criteria) this;
        }

        public Criteria andFreezeOperatorBetween(String value1, String value2) {
            addCriterion("FREEZE_OPERATOR between", value1, value2, "freezeOperator");
            return (Criteria) this;
        }

        public Criteria andFreezeOperatorNotBetween(String value1, String value2) {
            addCriterion("FREEZE_OPERATOR not between", value1, value2, "freezeOperator");
            return (Criteria) this;
        }

        public Criteria andThawOperatorIsNull() {
            addCriterion("THAW_OPERATOR is null");
            return (Criteria) this;
        }

        public Criteria andThawOperatorIsNotNull() {
            addCriterion("THAW_OPERATOR is not null");
            return (Criteria) this;
        }

        public Criteria andThawOperatorEqualTo(String value) {
            addCriterion("THAW_OPERATOR =", value, "thawOperator");
            return (Criteria) this;
        }

        public Criteria andThawOperatorNotEqualTo(String value) {
            addCriterion("THAW_OPERATOR <>", value, "thawOperator");
            return (Criteria) this;
        }

        public Criteria andThawOperatorGreaterThan(String value) {
            addCriterion("THAW_OPERATOR >", value, "thawOperator");
            return (Criteria) this;
        }

        public Criteria andThawOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("THAW_OPERATOR >=", value, "thawOperator");
            return (Criteria) this;
        }

        public Criteria andThawOperatorLessThan(String value) {
            addCriterion("THAW_OPERATOR <", value, "thawOperator");
            return (Criteria) this;
        }

        public Criteria andThawOperatorLessThanOrEqualTo(String value) {
            addCriterion("THAW_OPERATOR <=", value, "thawOperator");
            return (Criteria) this;
        }

        public Criteria andThawOperatorLike(String value) {
            addCriterion("THAW_OPERATOR like", value, "thawOperator");
            return (Criteria) this;
        }

        public Criteria andThawOperatorNotLike(String value) {
            addCriterion("THAW_OPERATOR not like", value, "thawOperator");
            return (Criteria) this;
        }

        public Criteria andThawOperatorIn(List<String> values) {
            addCriterion("THAW_OPERATOR in", values, "thawOperator");
            return (Criteria) this;
        }

        public Criteria andThawOperatorNotIn(List<String> values) {
            addCriterion("THAW_OPERATOR not in", values, "thawOperator");
            return (Criteria) this;
        }

        public Criteria andThawOperatorBetween(String value1, String value2) {
            addCriterion("THAW_OPERATOR between", value1, value2, "thawOperator");
            return (Criteria) this;
        }

        public Criteria andThawOperatorNotBetween(String value1, String value2) {
            addCriterion("THAW_OPERATOR not between", value1, value2, "thawOperator");
            return (Criteria) this;
        }

        public Criteria andFreezeBatchIsNull() {
            addCriterion("FREEZE_BATCH is null");
            return (Criteria) this;
        }

        public Criteria andFreezeBatchIsNotNull() {
            addCriterion("FREEZE_BATCH is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeBatchEqualTo(String value) {
            addCriterion("FREEZE_BATCH =", value, "freezeBatch");
            return (Criteria) this;
        }

        public Criteria andFreezeBatchNotEqualTo(String value) {
            addCriterion("FREEZE_BATCH <>", value, "freezeBatch");
            return (Criteria) this;
        }

        public Criteria andFreezeBatchGreaterThan(String value) {
            addCriterion("FREEZE_BATCH >", value, "freezeBatch");
            return (Criteria) this;
        }

        public Criteria andFreezeBatchGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_BATCH >=", value, "freezeBatch");
            return (Criteria) this;
        }

        public Criteria andFreezeBatchLessThan(String value) {
            addCriterion("FREEZE_BATCH <", value, "freezeBatch");
            return (Criteria) this;
        }

        public Criteria andFreezeBatchLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_BATCH <=", value, "freezeBatch");
            return (Criteria) this;
        }

        public Criteria andFreezeBatchLike(String value) {
            addCriterion("FREEZE_BATCH like", value, "freezeBatch");
            return (Criteria) this;
        }

        public Criteria andFreezeBatchNotLike(String value) {
            addCriterion("FREEZE_BATCH not like", value, "freezeBatch");
            return (Criteria) this;
        }

        public Criteria andFreezeBatchIn(List<String> values) {
            addCriterion("FREEZE_BATCH in", values, "freezeBatch");
            return (Criteria) this;
        }

        public Criteria andFreezeBatchNotIn(List<String> values) {
            addCriterion("FREEZE_BATCH not in", values, "freezeBatch");
            return (Criteria) this;
        }

        public Criteria andFreezeBatchBetween(String value1, String value2) {
            addCriterion("FREEZE_BATCH between", value1, value2, "freezeBatch");
            return (Criteria) this;
        }

        public Criteria andFreezeBatchNotBetween(String value1, String value2) {
            addCriterion("FREEZE_BATCH not between", value1, value2, "freezeBatch");
            return (Criteria) this;
        }

        public Criteria andThawBatchIsNull() {
            addCriterion("THAW_BATCH is null");
            return (Criteria) this;
        }

        public Criteria andThawBatchIsNotNull() {
            addCriterion("THAW_BATCH is not null");
            return (Criteria) this;
        }

        public Criteria andThawBatchEqualTo(String value) {
            addCriterion("THAW_BATCH =", value, "thawBatch");
            return (Criteria) this;
        }

        public Criteria andThawBatchNotEqualTo(String value) {
            addCriterion("THAW_BATCH <>", value, "thawBatch");
            return (Criteria) this;
        }

        public Criteria andThawBatchGreaterThan(String value) {
            addCriterion("THAW_BATCH >", value, "thawBatch");
            return (Criteria) this;
        }

        public Criteria andThawBatchGreaterThanOrEqualTo(String value) {
            addCriterion("THAW_BATCH >=", value, "thawBatch");
            return (Criteria) this;
        }

        public Criteria andThawBatchLessThan(String value) {
            addCriterion("THAW_BATCH <", value, "thawBatch");
            return (Criteria) this;
        }

        public Criteria andThawBatchLessThanOrEqualTo(String value) {
            addCriterion("THAW_BATCH <=", value, "thawBatch");
            return (Criteria) this;
        }

        public Criteria andThawBatchLike(String value) {
            addCriterion("THAW_BATCH like", value, "thawBatch");
            return (Criteria) this;
        }

        public Criteria andThawBatchNotLike(String value) {
            addCriterion("THAW_BATCH not like", value, "thawBatch");
            return (Criteria) this;
        }

        public Criteria andThawBatchIn(List<String> values) {
            addCriterion("THAW_BATCH in", values, "thawBatch");
            return (Criteria) this;
        }

        public Criteria andThawBatchNotIn(List<String> values) {
            addCriterion("THAW_BATCH not in", values, "thawBatch");
            return (Criteria) this;
        }

        public Criteria andThawBatchBetween(String value1, String value2) {
            addCriterion("THAW_BATCH between", value1, value2, "thawBatch");
            return (Criteria) this;
        }

        public Criteria andThawBatchNotBetween(String value1, String value2) {
            addCriterion("THAW_BATCH not between", value1, value2, "thawBatch");
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