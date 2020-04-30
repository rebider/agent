package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentFreezeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AgentFreezeExample() {
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