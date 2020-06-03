package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FreezeRequestDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public FreezeRequestDetailExample() {
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

        public Criteria andFreezeReqIdIsNull() {
            addCriterion("FREEZE_REQ_ID is null");
            return (Criteria) this;
        }

        public Criteria andFreezeReqIdIsNotNull() {
            addCriterion("FREEZE_REQ_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeReqIdEqualTo(String value) {
            addCriterion("FREEZE_REQ_ID =", value, "freezeReqId");
            return (Criteria) this;
        }

        public Criteria andFreezeReqIdNotEqualTo(String value) {
            addCriterion("FREEZE_REQ_ID <>", value, "freezeReqId");
            return (Criteria) this;
        }

        public Criteria andFreezeReqIdGreaterThan(String value) {
            addCriterion("FREEZE_REQ_ID >", value, "freezeReqId");
            return (Criteria) this;
        }

        public Criteria andFreezeReqIdGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_REQ_ID >=", value, "freezeReqId");
            return (Criteria) this;
        }

        public Criteria andFreezeReqIdLessThan(String value) {
            addCriterion("FREEZE_REQ_ID <", value, "freezeReqId");
            return (Criteria) this;
        }

        public Criteria andFreezeReqIdLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_REQ_ID <=", value, "freezeReqId");
            return (Criteria) this;
        }

        public Criteria andFreezeReqIdLike(String value) {
            addCriterion("FREEZE_REQ_ID like", value, "freezeReqId");
            return (Criteria) this;
        }

        public Criteria andFreezeReqIdNotLike(String value) {
            addCriterion("FREEZE_REQ_ID not like", value, "freezeReqId");
            return (Criteria) this;
        }

        public Criteria andFreezeReqIdIn(List<String> values) {
            addCriterion("FREEZE_REQ_ID in", values, "freezeReqId");
            return (Criteria) this;
        }

        public Criteria andFreezeReqIdNotIn(List<String> values) {
            addCriterion("FREEZE_REQ_ID not in", values, "freezeReqId");
            return (Criteria) this;
        }

        public Criteria andFreezeReqIdBetween(String value1, String value2) {
            addCriterion("FREEZE_REQ_ID between", value1, value2, "freezeReqId");
            return (Criteria) this;
        }

        public Criteria andFreezeReqIdNotBetween(String value1, String value2) {
            addCriterion("FREEZE_REQ_ID not between", value1, value2, "freezeReqId");
            return (Criteria) this;
        }

        public Criteria andFreezaIdIsNull() {
            addCriterion("FREEZA_ID is null");
            return (Criteria) this;
        }

        public Criteria andFreezaIdIsNotNull() {
            addCriterion("FREEZA_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFreezaIdEqualTo(String value) {
            addCriterion("FREEZA_ID =", value, "freezaId");
            return (Criteria) this;
        }

        public Criteria andFreezaIdNotEqualTo(String value) {
            addCriterion("FREEZA_ID <>", value, "freezaId");
            return (Criteria) this;
        }

        public Criteria andFreezaIdGreaterThan(String value) {
            addCriterion("FREEZA_ID >", value, "freezaId");
            return (Criteria) this;
        }

        public Criteria andFreezaIdGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZA_ID >=", value, "freezaId");
            return (Criteria) this;
        }

        public Criteria andFreezaIdLessThan(String value) {
            addCriterion("FREEZA_ID <", value, "freezaId");
            return (Criteria) this;
        }

        public Criteria andFreezaIdLessThanOrEqualTo(String value) {
            addCriterion("FREEZA_ID <=", value, "freezaId");
            return (Criteria) this;
        }

        public Criteria andFreezaIdLike(String value) {
            addCriterion("FREEZA_ID like", value, "freezaId");
            return (Criteria) this;
        }

        public Criteria andFreezaIdNotLike(String value) {
            addCriterion("FREEZA_ID not like", value, "freezaId");
            return (Criteria) this;
        }

        public Criteria andFreezaIdIn(List<String> values) {
            addCriterion("FREEZA_ID in", values, "freezaId");
            return (Criteria) this;
        }

        public Criteria andFreezaIdNotIn(List<String> values) {
            addCriterion("FREEZA_ID not in", values, "freezaId");
            return (Criteria) this;
        }

        public Criteria andFreezaIdBetween(String value1, String value2) {
            addCriterion("FREEZA_ID between", value1, value2, "freezaId");
            return (Criteria) this;
        }

        public Criteria andFreezaIdNotBetween(String value1, String value2) {
            addCriterion("FREEZA_ID not between", value1, value2, "freezaId");
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

        public Criteria andAgentFreezeStatusIsNull() {
            addCriterion("AGENT_FREEZE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andAgentFreezeStatusIsNotNull() {
            addCriterion("AGENT_FREEZE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andAgentFreezeStatusEqualTo(String value) {
            addCriterion("AGENT_FREEZE_STATUS =", value, "agentFreezeStatus");
            return (Criteria) this;
        }

        public Criteria andAgentFreezeStatusNotEqualTo(String value) {
            addCriterion("AGENT_FREEZE_STATUS <>", value, "agentFreezeStatus");
            return (Criteria) this;
        }

        public Criteria andAgentFreezeStatusGreaterThan(String value) {
            addCriterion("AGENT_FREEZE_STATUS >", value, "agentFreezeStatus");
            return (Criteria) this;
        }

        public Criteria andAgentFreezeStatusGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_FREEZE_STATUS >=", value, "agentFreezeStatus");
            return (Criteria) this;
        }

        public Criteria andAgentFreezeStatusLessThan(String value) {
            addCriterion("AGENT_FREEZE_STATUS <", value, "agentFreezeStatus");
            return (Criteria) this;
        }

        public Criteria andAgentFreezeStatusLessThanOrEqualTo(String value) {
            addCriterion("AGENT_FREEZE_STATUS <=", value, "agentFreezeStatus");
            return (Criteria) this;
        }

        public Criteria andAgentFreezeStatusLike(String value) {
            addCriterion("AGENT_FREEZE_STATUS like", value, "agentFreezeStatus");
            return (Criteria) this;
        }

        public Criteria andAgentFreezeStatusNotLike(String value) {
            addCriterion("AGENT_FREEZE_STATUS not like", value, "agentFreezeStatus");
            return (Criteria) this;
        }

        public Criteria andAgentFreezeStatusIn(List<String> values) {
            addCriterion("AGENT_FREEZE_STATUS in", values, "agentFreezeStatus");
            return (Criteria) this;
        }

        public Criteria andAgentFreezeStatusNotIn(List<String> values) {
            addCriterion("AGENT_FREEZE_STATUS not in", values, "agentFreezeStatus");
            return (Criteria) this;
        }

        public Criteria andAgentFreezeStatusBetween(String value1, String value2) {
            addCriterion("AGENT_FREEZE_STATUS between", value1, value2, "agentFreezeStatus");
            return (Criteria) this;
        }

        public Criteria andAgentFreezeStatusNotBetween(String value1, String value2) {
            addCriterion("AGENT_FREEZE_STATUS not between", value1, value2, "agentFreezeStatus");
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

        public Criteria andFreezeTypeEqualTo(BigDecimal value) {
            addCriterion("FREEZE_TYPE =", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeNotEqualTo(BigDecimal value) {
            addCriterion("FREEZE_TYPE <>", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeGreaterThan(BigDecimal value) {
            addCriterion("FREEZE_TYPE >", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FREEZE_TYPE >=", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeLessThan(BigDecimal value) {
            addCriterion("FREEZE_TYPE <", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FREEZE_TYPE <=", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeIn(List<BigDecimal> values) {
            addCriterion("FREEZE_TYPE in", values, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeNotIn(List<BigDecimal> values) {
            addCriterion("FREEZE_TYPE not in", values, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FREEZE_TYPE between", value1, value2, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FREEZE_TYPE not between", value1, value2, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusIsNull() {
            addCriterion("FREEZE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusIsNotNull() {
            addCriterion("FREEZE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusEqualTo(String value) {
            addCriterion("FREEZE_STATUS =", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusNotEqualTo(String value) {
            addCriterion("FREEZE_STATUS <>", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusGreaterThan(String value) {
            addCriterion("FREEZE_STATUS >", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_STATUS >=", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusLessThan(String value) {
            addCriterion("FREEZE_STATUS <", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_STATUS <=", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusLike(String value) {
            addCriterion("FREEZE_STATUS like", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusNotLike(String value) {
            addCriterion("FREEZE_STATUS not like", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusIn(List<String> values) {
            addCriterion("FREEZE_STATUS in", values, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusNotIn(List<String> values) {
            addCriterion("FREEZE_STATUS not in", values, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusBetween(String value1, String value2) {
            addCriterion("FREEZE_STATUS between", value1, value2, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusNotBetween(String value1, String value2) {
            addCriterion("FREEZE_STATUS not between", value1, value2, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezePersonIsNull() {
            addCriterion("FREEZE_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andFreezePersonIsNotNull() {
            addCriterion("FREEZE_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andFreezePersonEqualTo(String value) {
            addCriterion("FREEZE_PERSON =", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonNotEqualTo(String value) {
            addCriterion("FREEZE_PERSON <>", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonGreaterThan(String value) {
            addCriterion("FREEZE_PERSON >", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_PERSON >=", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonLessThan(String value) {
            addCriterion("FREEZE_PERSON <", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_PERSON <=", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonLike(String value) {
            addCriterion("FREEZE_PERSON like", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonNotLike(String value) {
            addCriterion("FREEZE_PERSON not like", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonIn(List<String> values) {
            addCriterion("FREEZE_PERSON in", values, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonNotIn(List<String> values) {
            addCriterion("FREEZE_PERSON not in", values, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonBetween(String value1, String value2) {
            addCriterion("FREEZE_PERSON between", value1, value2, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonNotBetween(String value1, String value2) {
            addCriterion("FREEZE_PERSON not between", value1, value2, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezeDateIsNull() {
            addCriterion("FREEZE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andFreezeDateIsNotNull() {
            addCriterion("FREEZE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeDateEqualTo(Date value) {
            addCriterion("FREEZE_DATE =", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateNotEqualTo(Date value) {
            addCriterion("FREEZE_DATE <>", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateGreaterThan(Date value) {
            addCriterion("FREEZE_DATE >", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateGreaterThanOrEqualTo(Date value) {
            addCriterion("FREEZE_DATE >=", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateLessThan(Date value) {
            addCriterion("FREEZE_DATE <", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateLessThanOrEqualTo(Date value) {
            addCriterion("FREEZE_DATE <=", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateIn(List<Date> values) {
            addCriterion("FREEZE_DATE in", values, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateNotIn(List<Date> values) {
            addCriterion("FREEZE_DATE not in", values, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateBetween(Date value1, Date value2) {
            addCriterion("FREEZE_DATE between", value1, value2, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateNotBetween(Date value1, Date value2) {
            addCriterion("FREEZE_DATE not between", value1, value2, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseIsNull() {
            addCriterion("FREEZE_CAUSE is null");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseIsNotNull() {
            addCriterion("FREEZE_CAUSE is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseEqualTo(String value) {
            addCriterion("FREEZE_CAUSE =", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseNotEqualTo(String value) {
            addCriterion("FREEZE_CAUSE <>", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseGreaterThan(String value) {
            addCriterion("FREEZE_CAUSE >", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_CAUSE >=", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseLessThan(String value) {
            addCriterion("FREEZE_CAUSE <", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_CAUSE <=", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseLike(String value) {
            addCriterion("FREEZE_CAUSE like", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseNotLike(String value) {
            addCriterion("FREEZE_CAUSE not like", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseIn(List<String> values) {
            addCriterion("FREEZE_CAUSE in", values, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseNotIn(List<String> values) {
            addCriterion("FREEZE_CAUSE not in", values, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseBetween(String value1, String value2) {
            addCriterion("FREEZE_CAUSE between", value1, value2, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseNotBetween(String value1, String value2) {
            addCriterion("FREEZE_CAUSE not between", value1, value2, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonIsNull() {
            addCriterion("UNFREEZE_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonIsNotNull() {
            addCriterion("UNFREEZE_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonEqualTo(String value) {
            addCriterion("UNFREEZE_PERSON =", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonNotEqualTo(String value) {
            addCriterion("UNFREEZE_PERSON <>", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonGreaterThan(String value) {
            addCriterion("UNFREEZE_PERSON >", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonGreaterThanOrEqualTo(String value) {
            addCriterion("UNFREEZE_PERSON >=", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonLessThan(String value) {
            addCriterion("UNFREEZE_PERSON <", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonLessThanOrEqualTo(String value) {
            addCriterion("UNFREEZE_PERSON <=", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonLike(String value) {
            addCriterion("UNFREEZE_PERSON like", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonNotLike(String value) {
            addCriterion("UNFREEZE_PERSON not like", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonIn(List<String> values) {
            addCriterion("UNFREEZE_PERSON in", values, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonNotIn(List<String> values) {
            addCriterion("UNFREEZE_PERSON not in", values, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonBetween(String value1, String value2) {
            addCriterion("UNFREEZE_PERSON between", value1, value2, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonNotBetween(String value1, String value2) {
            addCriterion("UNFREEZE_PERSON not between", value1, value2, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateIsNull() {
            addCriterion("UNFREEZE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateIsNotNull() {
            addCriterion("UNFREEZE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateEqualTo(Date value) {
            addCriterion("UNFREEZE_DATE =", value, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateNotEqualTo(Date value) {
            addCriterion("UNFREEZE_DATE <>", value, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateGreaterThan(Date value) {
            addCriterion("UNFREEZE_DATE >", value, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateGreaterThanOrEqualTo(Date value) {
            addCriterion("UNFREEZE_DATE >=", value, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateLessThan(Date value) {
            addCriterion("UNFREEZE_DATE <", value, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateLessThanOrEqualTo(Date value) {
            addCriterion("UNFREEZE_DATE <=", value, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateIn(List<Date> values) {
            addCriterion("UNFREEZE_DATE in", values, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateNotIn(List<Date> values) {
            addCriterion("UNFREEZE_DATE not in", values, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateBetween(Date value1, Date value2) {
            addCriterion("UNFREEZE_DATE between", value1, value2, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateNotBetween(Date value1, Date value2) {
            addCriterion("UNFREEZE_DATE not between", value1, value2, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseIsNull() {
            addCriterion("UNFREEZE_CAUSE is null");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseIsNotNull() {
            addCriterion("UNFREEZE_CAUSE is not null");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseEqualTo(String value) {
            addCriterion("UNFREEZE_CAUSE =", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseNotEqualTo(String value) {
            addCriterion("UNFREEZE_CAUSE <>", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseGreaterThan(String value) {
            addCriterion("UNFREEZE_CAUSE >", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseGreaterThanOrEqualTo(String value) {
            addCriterion("UNFREEZE_CAUSE >=", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseLessThan(String value) {
            addCriterion("UNFREEZE_CAUSE <", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseLessThanOrEqualTo(String value) {
            addCriterion("UNFREEZE_CAUSE <=", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseLike(String value) {
            addCriterion("UNFREEZE_CAUSE like", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseNotLike(String value) {
            addCriterion("UNFREEZE_CAUSE not like", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseIn(List<String> values) {
            addCriterion("UNFREEZE_CAUSE in", values, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseNotIn(List<String> values) {
            addCriterion("UNFREEZE_CAUSE not in", values, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseBetween(String value1, String value2) {
            addCriterion("UNFREEZE_CAUSE between", value1, value2, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseNotBetween(String value1, String value2) {
            addCriterion("UNFREEZE_CAUSE not between", value1, value2, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeNumIsNull() {
            addCriterion("FREEZE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andFreezeNumIsNotNull() {
            addCriterion("FREEZE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeNumEqualTo(String value) {
            addCriterion("FREEZE_NUM =", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumNotEqualTo(String value) {
            addCriterion("FREEZE_NUM <>", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumGreaterThan(String value) {
            addCriterion("FREEZE_NUM >", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_NUM >=", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumLessThan(String value) {
            addCriterion("FREEZE_NUM <", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_NUM <=", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumLike(String value) {
            addCriterion("FREEZE_NUM like", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumNotLike(String value) {
            addCriterion("FREEZE_NUM not like", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumIn(List<String> values) {
            addCriterion("FREEZE_NUM in", values, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumNotIn(List<String> values) {
            addCriterion("FREEZE_NUM not in", values, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumBetween(String value1, String value2) {
            addCriterion("FREEZE_NUM between", value1, value2, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumNotBetween(String value1, String value2) {
            addCriterion("FREEZE_NUM not between", value1, value2, "freezeNum");
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

        public Criteria andBusFreezeIsNull() {
            addCriterion("BUS_FREEZE is null");
            return (Criteria) this;
        }

        public Criteria andBusFreezeIsNotNull() {
            addCriterion("BUS_FREEZE is not null");
            return (Criteria) this;
        }

        public Criteria andBusFreezeEqualTo(BigDecimal value) {
            addCriterion("BUS_FREEZE =", value, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeNotEqualTo(BigDecimal value) {
            addCriterion("BUS_FREEZE <>", value, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeGreaterThan(BigDecimal value) {
            addCriterion("BUS_FREEZE >", value, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_FREEZE >=", value, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeLessThan(BigDecimal value) {
            addCriterion("BUS_FREEZE <", value, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_FREEZE <=", value, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeIn(List<BigDecimal> values) {
            addCriterion("BUS_FREEZE in", values, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeNotIn(List<BigDecimal> values) {
            addCriterion("BUS_FREEZE not in", values, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_FREEZE between", value1, value2, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_FREEZE not between", value1, value2, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeIsNull() {
            addCriterion("PROFIT_FREEZE is null");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeIsNotNull() {
            addCriterion("PROFIT_FREEZE is not null");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeEqualTo(BigDecimal value) {
            addCriterion("PROFIT_FREEZE =", value, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeNotEqualTo(BigDecimal value) {
            addCriterion("PROFIT_FREEZE <>", value, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeGreaterThan(BigDecimal value) {
            addCriterion("PROFIT_FREEZE >", value, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_FREEZE >=", value, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeLessThan(BigDecimal value) {
            addCriterion("PROFIT_FREEZE <", value, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_FREEZE <=", value, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeIn(List<BigDecimal> values) {
            addCriterion("PROFIT_FREEZE in", values, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeNotIn(List<BigDecimal> values) {
            addCriterion("PROFIT_FREEZE not in", values, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_FREEZE between", value1, value2, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_FREEZE not between", value1, value2, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeIsNull() {
            addCriterion("REFLOW_FREEZE is null");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeIsNotNull() {
            addCriterion("REFLOW_FREEZE is not null");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeEqualTo(BigDecimal value) {
            addCriterion("REFLOW_FREEZE =", value, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeNotEqualTo(BigDecimal value) {
            addCriterion("REFLOW_FREEZE <>", value, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeGreaterThan(BigDecimal value) {
            addCriterion("REFLOW_FREEZE >", value, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFLOW_FREEZE >=", value, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeLessThan(BigDecimal value) {
            addCriterion("REFLOW_FREEZE <", value, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFLOW_FREEZE <=", value, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeIn(List<BigDecimal> values) {
            addCriterion("REFLOW_FREEZE in", values, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeNotIn(List<BigDecimal> values) {
            addCriterion("REFLOW_FREEZE not in", values, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFLOW_FREEZE between", value1, value2, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFLOW_FREEZE not between", value1, value2, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeIsNull() {
            addCriterion("MONTHLY_FREEZE is null");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeIsNotNull() {
            addCriterion("MONTHLY_FREEZE is not null");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeEqualTo(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE =", value, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeNotEqualTo(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE <>", value, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeGreaterThan(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE >", value, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE >=", value, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeLessThan(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE <", value, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE <=", value, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeIn(List<BigDecimal> values) {
            addCriterion("MONTHLY_FREEZE in", values, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeNotIn(List<BigDecimal> values) {
            addCriterion("MONTHLY_FREEZE not in", values, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MONTHLY_FREEZE between", value1, value2, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MONTHLY_FREEZE not between", value1, value2, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeIsNull() {
            addCriterion("DAILY_FREEZE is null");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeIsNotNull() {
            addCriterion("DAILY_FREEZE is not null");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeEqualTo(BigDecimal value) {
            addCriterion("DAILY_FREEZE =", value, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeNotEqualTo(BigDecimal value) {
            addCriterion("DAILY_FREEZE <>", value, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeGreaterThan(BigDecimal value) {
            addCriterion("DAILY_FREEZE >", value, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DAILY_FREEZE >=", value, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeLessThan(BigDecimal value) {
            addCriterion("DAILY_FREEZE <", value, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DAILY_FREEZE <=", value, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeIn(List<BigDecimal> values) {
            addCriterion("DAILY_FREEZE in", values, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeNotIn(List<BigDecimal> values) {
            addCriterion("DAILY_FREEZE not in", values, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAILY_FREEZE between", value1, value2, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAILY_FREEZE not between", value1, value2, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeIsNull() {
            addCriterion("STOP_PROFIT_FREEZE is null");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeIsNotNull() {
            addCriterion("STOP_PROFIT_FREEZE is not null");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeEqualTo(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE =", value, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeNotEqualTo(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE <>", value, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeGreaterThan(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE >", value, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE >=", value, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeLessThan(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE <", value, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE <=", value, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeIn(List<BigDecimal> values) {
            addCriterion("STOP_PROFIT_FREEZE in", values, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeNotIn(List<BigDecimal> values) {
            addCriterion("STOP_PROFIT_FREEZE not in", values, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STOP_PROFIT_FREEZE between", value1, value2, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STOP_PROFIT_FREEZE not between", value1, value2, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeIsNull() {
            addCriterion("CASH_FREEZE is null");
            return (Criteria) this;
        }

        public Criteria andCashFreezeIsNotNull() {
            addCriterion("CASH_FREEZE is not null");
            return (Criteria) this;
        }

        public Criteria andCashFreezeEqualTo(BigDecimal value) {
            addCriterion("CASH_FREEZE =", value, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeNotEqualTo(BigDecimal value) {
            addCriterion("CASH_FREEZE <>", value, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeGreaterThan(BigDecimal value) {
            addCriterion("CASH_FREEZE >", value, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CASH_FREEZE >=", value, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeLessThan(BigDecimal value) {
            addCriterion("CASH_FREEZE <", value, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CASH_FREEZE <=", value, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeIn(List<BigDecimal> values) {
            addCriterion("CASH_FREEZE in", values, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeNotIn(List<BigDecimal> values) {
            addCriterion("CASH_FREEZE not in", values, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CASH_FREEZE between", value1, value2, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CASH_FREEZE not between", value1, value2, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andStopCountIsNull() {
            addCriterion("STOP_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andStopCountIsNotNull() {
            addCriterion("STOP_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andStopCountEqualTo(BigDecimal value) {
            addCriterion("STOP_COUNT =", value, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountNotEqualTo(BigDecimal value) {
            addCriterion("STOP_COUNT <>", value, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountGreaterThan(BigDecimal value) {
            addCriterion("STOP_COUNT >", value, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STOP_COUNT >=", value, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountLessThan(BigDecimal value) {
            addCriterion("STOP_COUNT <", value, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STOP_COUNT <=", value, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountIn(List<BigDecimal> values) {
            addCriterion("STOP_COUNT in", values, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountNotIn(List<BigDecimal> values) {
            addCriterion("STOP_COUNT not in", values, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STOP_COUNT between", value1, value2, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STOP_COUNT not between", value1, value2, "stopCount");
            return (Criteria) this;
        }

        public Criteria andBusFreezeOrgIsNull() {
            addCriterion("BUS_FREEZE_ORG is null");
            return (Criteria) this;
        }

        public Criteria andBusFreezeOrgIsNotNull() {
            addCriterion("BUS_FREEZE_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andBusFreezeOrgEqualTo(BigDecimal value) {
            addCriterion("BUS_FREEZE_ORG =", value, "busFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andBusFreezeOrgNotEqualTo(BigDecimal value) {
            addCriterion("BUS_FREEZE_ORG <>", value, "busFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andBusFreezeOrgGreaterThan(BigDecimal value) {
            addCriterion("BUS_FREEZE_ORG >", value, "busFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andBusFreezeOrgGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_FREEZE_ORG >=", value, "busFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andBusFreezeOrgLessThan(BigDecimal value) {
            addCriterion("BUS_FREEZE_ORG <", value, "busFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andBusFreezeOrgLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_FREEZE_ORG <=", value, "busFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andBusFreezeOrgIn(List<BigDecimal> values) {
            addCriterion("BUS_FREEZE_ORG in", values, "busFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andBusFreezeOrgNotIn(List<BigDecimal> values) {
            addCriterion("BUS_FREEZE_ORG not in", values, "busFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andBusFreezeOrgBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_FREEZE_ORG between", value1, value2, "busFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andBusFreezeOrgNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_FREEZE_ORG not between", value1, value2, "busFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeOrgIsNull() {
            addCriterion("PROFIT_FREEZE_ORG is null");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeOrgIsNotNull() {
            addCriterion("PROFIT_FREEZE_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeOrgEqualTo(BigDecimal value) {
            addCriterion("PROFIT_FREEZE_ORG =", value, "profitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeOrgNotEqualTo(BigDecimal value) {
            addCriterion("PROFIT_FREEZE_ORG <>", value, "profitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeOrgGreaterThan(BigDecimal value) {
            addCriterion("PROFIT_FREEZE_ORG >", value, "profitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeOrgGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_FREEZE_ORG >=", value, "profitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeOrgLessThan(BigDecimal value) {
            addCriterion("PROFIT_FREEZE_ORG <", value, "profitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeOrgLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_FREEZE_ORG <=", value, "profitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeOrgIn(List<BigDecimal> values) {
            addCriterion("PROFIT_FREEZE_ORG in", values, "profitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeOrgNotIn(List<BigDecimal> values) {
            addCriterion("PROFIT_FREEZE_ORG not in", values, "profitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeOrgBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_FREEZE_ORG between", value1, value2, "profitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeOrgNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_FREEZE_ORG not between", value1, value2, "profitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeOrgIsNull() {
            addCriterion("REFLOW_FREEZE_ORG is null");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeOrgIsNotNull() {
            addCriterion("REFLOW_FREEZE_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeOrgEqualTo(BigDecimal value) {
            addCriterion("REFLOW_FREEZE_ORG =", value, "reflowFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeOrgNotEqualTo(BigDecimal value) {
            addCriterion("REFLOW_FREEZE_ORG <>", value, "reflowFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeOrgGreaterThan(BigDecimal value) {
            addCriterion("REFLOW_FREEZE_ORG >", value, "reflowFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeOrgGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFLOW_FREEZE_ORG >=", value, "reflowFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeOrgLessThan(BigDecimal value) {
            addCriterion("REFLOW_FREEZE_ORG <", value, "reflowFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeOrgLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFLOW_FREEZE_ORG <=", value, "reflowFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeOrgIn(List<BigDecimal> values) {
            addCriterion("REFLOW_FREEZE_ORG in", values, "reflowFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeOrgNotIn(List<BigDecimal> values) {
            addCriterion("REFLOW_FREEZE_ORG not in", values, "reflowFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeOrgBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFLOW_FREEZE_ORG between", value1, value2, "reflowFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeOrgNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFLOW_FREEZE_ORG not between", value1, value2, "reflowFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeOrgIsNull() {
            addCriterion("MONTHLY_FREEZE_ORG is null");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeOrgIsNotNull() {
            addCriterion("MONTHLY_FREEZE_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeOrgEqualTo(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE_ORG =", value, "monthlyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeOrgNotEqualTo(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE_ORG <>", value, "monthlyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeOrgGreaterThan(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE_ORG >", value, "monthlyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeOrgGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE_ORG >=", value, "monthlyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeOrgLessThan(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE_ORG <", value, "monthlyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeOrgLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE_ORG <=", value, "monthlyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeOrgIn(List<BigDecimal> values) {
            addCriterion("MONTHLY_FREEZE_ORG in", values, "monthlyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeOrgNotIn(List<BigDecimal> values) {
            addCriterion("MONTHLY_FREEZE_ORG not in", values, "monthlyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeOrgBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MONTHLY_FREEZE_ORG between", value1, value2, "monthlyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeOrgNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MONTHLY_FREEZE_ORG not between", value1, value2, "monthlyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeOrgIsNull() {
            addCriterion("DAILY_FREEZE_ORG is null");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeOrgIsNotNull() {
            addCriterion("DAILY_FREEZE_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeOrgEqualTo(BigDecimal value) {
            addCriterion("DAILY_FREEZE_ORG =", value, "dailyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeOrgNotEqualTo(BigDecimal value) {
            addCriterion("DAILY_FREEZE_ORG <>", value, "dailyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeOrgGreaterThan(BigDecimal value) {
            addCriterion("DAILY_FREEZE_ORG >", value, "dailyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeOrgGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DAILY_FREEZE_ORG >=", value, "dailyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeOrgLessThan(BigDecimal value) {
            addCriterion("DAILY_FREEZE_ORG <", value, "dailyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeOrgLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DAILY_FREEZE_ORG <=", value, "dailyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeOrgIn(List<BigDecimal> values) {
            addCriterion("DAILY_FREEZE_ORG in", values, "dailyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeOrgNotIn(List<BigDecimal> values) {
            addCriterion("DAILY_FREEZE_ORG not in", values, "dailyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeOrgBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAILY_FREEZE_ORG between", value1, value2, "dailyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeOrgNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAILY_FREEZE_ORG not between", value1, value2, "dailyFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeOrgIsNull() {
            addCriterion("STOP_PROFIT_FREEZE_ORG is null");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeOrgIsNotNull() {
            addCriterion("STOP_PROFIT_FREEZE_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeOrgEqualTo(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE_ORG =", value, "stopProfitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeOrgNotEqualTo(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE_ORG <>", value, "stopProfitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeOrgGreaterThan(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE_ORG >", value, "stopProfitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeOrgGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE_ORG >=", value, "stopProfitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeOrgLessThan(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE_ORG <", value, "stopProfitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeOrgLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE_ORG <=", value, "stopProfitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeOrgIn(List<BigDecimal> values) {
            addCriterion("STOP_PROFIT_FREEZE_ORG in", values, "stopProfitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeOrgNotIn(List<BigDecimal> values) {
            addCriterion("STOP_PROFIT_FREEZE_ORG not in", values, "stopProfitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeOrgBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STOP_PROFIT_FREEZE_ORG between", value1, value2, "stopProfitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeOrgNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STOP_PROFIT_FREEZE_ORG not between", value1, value2, "stopProfitFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andCashFreezeOrgIsNull() {
            addCriterion("CASH_FREEZE_ORG is null");
            return (Criteria) this;
        }

        public Criteria andCashFreezeOrgIsNotNull() {
            addCriterion("CASH_FREEZE_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andCashFreezeOrgEqualTo(BigDecimal value) {
            addCriterion("CASH_FREEZE_ORG =", value, "cashFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andCashFreezeOrgNotEqualTo(BigDecimal value) {
            addCriterion("CASH_FREEZE_ORG <>", value, "cashFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andCashFreezeOrgGreaterThan(BigDecimal value) {
            addCriterion("CASH_FREEZE_ORG >", value, "cashFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andCashFreezeOrgGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CASH_FREEZE_ORG >=", value, "cashFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andCashFreezeOrgLessThan(BigDecimal value) {
            addCriterion("CASH_FREEZE_ORG <", value, "cashFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andCashFreezeOrgLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CASH_FREEZE_ORG <=", value, "cashFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andCashFreezeOrgIn(List<BigDecimal> values) {
            addCriterion("CASH_FREEZE_ORG in", values, "cashFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andCashFreezeOrgNotIn(List<BigDecimal> values) {
            addCriterion("CASH_FREEZE_ORG not in", values, "cashFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andCashFreezeOrgBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CASH_FREEZE_ORG between", value1, value2, "cashFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andCashFreezeOrgNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CASH_FREEZE_ORG not between", value1, value2, "cashFreezeOrg");
            return (Criteria) this;
        }

        public Criteria andStopCountOrgIsNull() {
            addCriterion("STOP_COUNT_ORG is null");
            return (Criteria) this;
        }

        public Criteria andStopCountOrgIsNotNull() {
            addCriterion("STOP_COUNT_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andStopCountOrgEqualTo(BigDecimal value) {
            addCriterion("STOP_COUNT_ORG =", value, "stopCountOrg");
            return (Criteria) this;
        }

        public Criteria andStopCountOrgNotEqualTo(BigDecimal value) {
            addCriterion("STOP_COUNT_ORG <>", value, "stopCountOrg");
            return (Criteria) this;
        }

        public Criteria andStopCountOrgGreaterThan(BigDecimal value) {
            addCriterion("STOP_COUNT_ORG >", value, "stopCountOrg");
            return (Criteria) this;
        }

        public Criteria andStopCountOrgGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STOP_COUNT_ORG >=", value, "stopCountOrg");
            return (Criteria) this;
        }

        public Criteria andStopCountOrgLessThan(BigDecimal value) {
            addCriterion("STOP_COUNT_ORG <", value, "stopCountOrg");
            return (Criteria) this;
        }

        public Criteria andStopCountOrgLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STOP_COUNT_ORG <=", value, "stopCountOrg");
            return (Criteria) this;
        }

        public Criteria andStopCountOrgIn(List<BigDecimal> values) {
            addCriterion("STOP_COUNT_ORG in", values, "stopCountOrg");
            return (Criteria) this;
        }

        public Criteria andStopCountOrgNotIn(List<BigDecimal> values) {
            addCriterion("STOP_COUNT_ORG not in", values, "stopCountOrg");
            return (Criteria) this;
        }

        public Criteria andStopCountOrgBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STOP_COUNT_ORG between", value1, value2, "stopCountOrg");
            return (Criteria) this;
        }

        public Criteria andStopCountOrgNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STOP_COUNT_ORG not between", value1, value2, "stopCountOrg");
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