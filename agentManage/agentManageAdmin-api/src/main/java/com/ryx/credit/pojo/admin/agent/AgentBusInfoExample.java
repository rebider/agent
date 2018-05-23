package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentBusInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AgentBusInfoExample() {
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

        public Criteria andBusTypeIsNull() {
            addCriterion("BUS_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBusTypeIsNotNull() {
            addCriterion("BUS_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBusTypeEqualTo(String value) {
            addCriterion("BUS_TYPE =", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeNotEqualTo(String value) {
            addCriterion("BUS_TYPE <>", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeGreaterThan(String value) {
            addCriterion("BUS_TYPE >", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_TYPE >=", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeLessThan(String value) {
            addCriterion("BUS_TYPE <", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeLessThanOrEqualTo(String value) {
            addCriterion("BUS_TYPE <=", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeLike(String value) {
            addCriterion("BUS_TYPE like", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeNotLike(String value) {
            addCriterion("BUS_TYPE not like", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeIn(List<String> values) {
            addCriterion("BUS_TYPE in", values, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeNotIn(List<String> values) {
            addCriterion("BUS_TYPE not in", values, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeBetween(String value1, String value2) {
            addCriterion("BUS_TYPE between", value1, value2, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeNotBetween(String value1, String value2) {
            addCriterion("BUS_TYPE not between", value1, value2, "busType");
            return (Criteria) this;
        }

        public Criteria andBusParentIsNull() {
            addCriterion("BUS_PARENT is null");
            return (Criteria) this;
        }

        public Criteria andBusParentIsNotNull() {
            addCriterion("BUS_PARENT is not null");
            return (Criteria) this;
        }

        public Criteria andBusParentEqualTo(String value) {
            addCriterion("BUS_PARENT =", value, "busParent");
            return (Criteria) this;
        }

        public Criteria andBusParentNotEqualTo(String value) {
            addCriterion("BUS_PARENT <>", value, "busParent");
            return (Criteria) this;
        }

        public Criteria andBusParentGreaterThan(String value) {
            addCriterion("BUS_PARENT >", value, "busParent");
            return (Criteria) this;
        }

        public Criteria andBusParentGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_PARENT >=", value, "busParent");
            return (Criteria) this;
        }

        public Criteria andBusParentLessThan(String value) {
            addCriterion("BUS_PARENT <", value, "busParent");
            return (Criteria) this;
        }

        public Criteria andBusParentLessThanOrEqualTo(String value) {
            addCriterion("BUS_PARENT <=", value, "busParent");
            return (Criteria) this;
        }

        public Criteria andBusParentLike(String value) {
            addCriterion("BUS_PARENT like", value, "busParent");
            return (Criteria) this;
        }

        public Criteria andBusParentNotLike(String value) {
            addCriterion("BUS_PARENT not like", value, "busParent");
            return (Criteria) this;
        }

        public Criteria andBusParentIn(List<String> values) {
            addCriterion("BUS_PARENT in", values, "busParent");
            return (Criteria) this;
        }

        public Criteria andBusParentNotIn(List<String> values) {
            addCriterion("BUS_PARENT not in", values, "busParent");
            return (Criteria) this;
        }

        public Criteria andBusParentBetween(String value1, String value2) {
            addCriterion("BUS_PARENT between", value1, value2, "busParent");
            return (Criteria) this;
        }

        public Criteria andBusParentNotBetween(String value1, String value2) {
            addCriterion("BUS_PARENT not between", value1, value2, "busParent");
            return (Criteria) this;
        }

        public Criteria andBusRiskParentIsNull() {
            addCriterion("BUS_RISK_PARENT is null");
            return (Criteria) this;
        }

        public Criteria andBusRiskParentIsNotNull() {
            addCriterion("BUS_RISK_PARENT is not null");
            return (Criteria) this;
        }

        public Criteria andBusRiskParentEqualTo(String value) {
            addCriterion("BUS_RISK_PARENT =", value, "busRiskParent");
            return (Criteria) this;
        }

        public Criteria andBusRiskParentNotEqualTo(String value) {
            addCriterion("BUS_RISK_PARENT <>", value, "busRiskParent");
            return (Criteria) this;
        }

        public Criteria andBusRiskParentGreaterThan(String value) {
            addCriterion("BUS_RISK_PARENT >", value, "busRiskParent");
            return (Criteria) this;
        }

        public Criteria andBusRiskParentGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_RISK_PARENT >=", value, "busRiskParent");
            return (Criteria) this;
        }

        public Criteria andBusRiskParentLessThan(String value) {
            addCriterion("BUS_RISK_PARENT <", value, "busRiskParent");
            return (Criteria) this;
        }

        public Criteria andBusRiskParentLessThanOrEqualTo(String value) {
            addCriterion("BUS_RISK_PARENT <=", value, "busRiskParent");
            return (Criteria) this;
        }

        public Criteria andBusRiskParentLike(String value) {
            addCriterion("BUS_RISK_PARENT like", value, "busRiskParent");
            return (Criteria) this;
        }

        public Criteria andBusRiskParentNotLike(String value) {
            addCriterion("BUS_RISK_PARENT not like", value, "busRiskParent");
            return (Criteria) this;
        }

        public Criteria andBusRiskParentIn(List<String> values) {
            addCriterion("BUS_RISK_PARENT in", values, "busRiskParent");
            return (Criteria) this;
        }

        public Criteria andBusRiskParentNotIn(List<String> values) {
            addCriterion("BUS_RISK_PARENT not in", values, "busRiskParent");
            return (Criteria) this;
        }

        public Criteria andBusRiskParentBetween(String value1, String value2) {
            addCriterion("BUS_RISK_PARENT between", value1, value2, "busRiskParent");
            return (Criteria) this;
        }

        public Criteria andBusRiskParentNotBetween(String value1, String value2) {
            addCriterion("BUS_RISK_PARENT not between", value1, value2, "busRiskParent");
            return (Criteria) this;
        }

        public Criteria andBusActivationParentIsNull() {
            addCriterion("BUS_ACTIVATION_PARENT is null");
            return (Criteria) this;
        }

        public Criteria andBusActivationParentIsNotNull() {
            addCriterion("BUS_ACTIVATION_PARENT is not null");
            return (Criteria) this;
        }

        public Criteria andBusActivationParentEqualTo(String value) {
            addCriterion("BUS_ACTIVATION_PARENT =", value, "busActivationParent");
            return (Criteria) this;
        }

        public Criteria andBusActivationParentNotEqualTo(String value) {
            addCriterion("BUS_ACTIVATION_PARENT <>", value, "busActivationParent");
            return (Criteria) this;
        }

        public Criteria andBusActivationParentGreaterThan(String value) {
            addCriterion("BUS_ACTIVATION_PARENT >", value, "busActivationParent");
            return (Criteria) this;
        }

        public Criteria andBusActivationParentGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_ACTIVATION_PARENT >=", value, "busActivationParent");
            return (Criteria) this;
        }

        public Criteria andBusActivationParentLessThan(String value) {
            addCriterion("BUS_ACTIVATION_PARENT <", value, "busActivationParent");
            return (Criteria) this;
        }

        public Criteria andBusActivationParentLessThanOrEqualTo(String value) {
            addCriterion("BUS_ACTIVATION_PARENT <=", value, "busActivationParent");
            return (Criteria) this;
        }

        public Criteria andBusActivationParentLike(String value) {
            addCriterion("BUS_ACTIVATION_PARENT like", value, "busActivationParent");
            return (Criteria) this;
        }

        public Criteria andBusActivationParentNotLike(String value) {
            addCriterion("BUS_ACTIVATION_PARENT not like", value, "busActivationParent");
            return (Criteria) this;
        }

        public Criteria andBusActivationParentIn(List<String> values) {
            addCriterion("BUS_ACTIVATION_PARENT in", values, "busActivationParent");
            return (Criteria) this;
        }

        public Criteria andBusActivationParentNotIn(List<String> values) {
            addCriterion("BUS_ACTIVATION_PARENT not in", values, "busActivationParent");
            return (Criteria) this;
        }

        public Criteria andBusActivationParentBetween(String value1, String value2) {
            addCriterion("BUS_ACTIVATION_PARENT between", value1, value2, "busActivationParent");
            return (Criteria) this;
        }

        public Criteria andBusActivationParentNotBetween(String value1, String value2) {
            addCriterion("BUS_ACTIVATION_PARENT not between", value1, value2, "busActivationParent");
            return (Criteria) this;
        }

        public Criteria andBusRegionIsNull() {
            addCriterion("BUS_REGION is null");
            return (Criteria) this;
        }

        public Criteria andBusRegionIsNotNull() {
            addCriterion("BUS_REGION is not null");
            return (Criteria) this;
        }

        public Criteria andBusRegionEqualTo(String value) {
            addCriterion("BUS_REGION =", value, "busRegion");
            return (Criteria) this;
        }

        public Criteria andBusRegionNotEqualTo(String value) {
            addCriterion("BUS_REGION <>", value, "busRegion");
            return (Criteria) this;
        }

        public Criteria andBusRegionGreaterThan(String value) {
            addCriterion("BUS_REGION >", value, "busRegion");
            return (Criteria) this;
        }

        public Criteria andBusRegionGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_REGION >=", value, "busRegion");
            return (Criteria) this;
        }

        public Criteria andBusRegionLessThan(String value) {
            addCriterion("BUS_REGION <", value, "busRegion");
            return (Criteria) this;
        }

        public Criteria andBusRegionLessThanOrEqualTo(String value) {
            addCriterion("BUS_REGION <=", value, "busRegion");
            return (Criteria) this;
        }

        public Criteria andBusRegionLike(String value) {
            addCriterion("BUS_REGION like", value, "busRegion");
            return (Criteria) this;
        }

        public Criteria andBusRegionNotLike(String value) {
            addCriterion("BUS_REGION not like", value, "busRegion");
            return (Criteria) this;
        }

        public Criteria andBusRegionIn(List<String> values) {
            addCriterion("BUS_REGION in", values, "busRegion");
            return (Criteria) this;
        }

        public Criteria andBusRegionNotIn(List<String> values) {
            addCriterion("BUS_REGION not in", values, "busRegion");
            return (Criteria) this;
        }

        public Criteria andBusRegionBetween(String value1, String value2) {
            addCriterion("BUS_REGION between", value1, value2, "busRegion");
            return (Criteria) this;
        }

        public Criteria andBusRegionNotBetween(String value1, String value2) {
            addCriterion("BUS_REGION not between", value1, value2, "busRegion");
            return (Criteria) this;
        }

        public Criteria andBusSentDirectlyIsNull() {
            addCriterion("BUS_SENT_DIRECTLY is null");
            return (Criteria) this;
        }

        public Criteria andBusSentDirectlyIsNotNull() {
            addCriterion("BUS_SENT_DIRECTLY is not null");
            return (Criteria) this;
        }

        public Criteria andBusSentDirectlyEqualTo(BigDecimal value) {
            addCriterion("BUS_SENT_DIRECTLY =", value, "busSentDirectly");
            return (Criteria) this;
        }

        public Criteria andBusSentDirectlyNotEqualTo(BigDecimal value) {
            addCriterion("BUS_SENT_DIRECTLY <>", value, "busSentDirectly");
            return (Criteria) this;
        }

        public Criteria andBusSentDirectlyGreaterThan(BigDecimal value) {
            addCriterion("BUS_SENT_DIRECTLY >", value, "busSentDirectly");
            return (Criteria) this;
        }

        public Criteria andBusSentDirectlyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_SENT_DIRECTLY >=", value, "busSentDirectly");
            return (Criteria) this;
        }

        public Criteria andBusSentDirectlyLessThan(BigDecimal value) {
            addCriterion("BUS_SENT_DIRECTLY <", value, "busSentDirectly");
            return (Criteria) this;
        }

        public Criteria andBusSentDirectlyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_SENT_DIRECTLY <=", value, "busSentDirectly");
            return (Criteria) this;
        }

        public Criteria andBusSentDirectlyIn(List<BigDecimal> values) {
            addCriterion("BUS_SENT_DIRECTLY in", values, "busSentDirectly");
            return (Criteria) this;
        }

        public Criteria andBusSentDirectlyNotIn(List<BigDecimal> values) {
            addCriterion("BUS_SENT_DIRECTLY not in", values, "busSentDirectly");
            return (Criteria) this;
        }

        public Criteria andBusSentDirectlyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_SENT_DIRECTLY between", value1, value2, "busSentDirectly");
            return (Criteria) this;
        }

        public Criteria andBusSentDirectlyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_SENT_DIRECTLY not between", value1, value2, "busSentDirectly");
            return (Criteria) this;
        }

        public Criteria andBusDirectCashbackIsNull() {
            addCriterion("BUS_DIRECT_CASHBACK is null");
            return (Criteria) this;
        }

        public Criteria andBusDirectCashbackIsNotNull() {
            addCriterion("BUS_DIRECT_CASHBACK is not null");
            return (Criteria) this;
        }

        public Criteria andBusDirectCashbackEqualTo(BigDecimal value) {
            addCriterion("BUS_DIRECT_CASHBACK =", value, "busDirectCashback");
            return (Criteria) this;
        }

        public Criteria andBusDirectCashbackNotEqualTo(BigDecimal value) {
            addCriterion("BUS_DIRECT_CASHBACK <>", value, "busDirectCashback");
            return (Criteria) this;
        }

        public Criteria andBusDirectCashbackGreaterThan(BigDecimal value) {
            addCriterion("BUS_DIRECT_CASHBACK >", value, "busDirectCashback");
            return (Criteria) this;
        }

        public Criteria andBusDirectCashbackGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_DIRECT_CASHBACK >=", value, "busDirectCashback");
            return (Criteria) this;
        }

        public Criteria andBusDirectCashbackLessThan(BigDecimal value) {
            addCriterion("BUS_DIRECT_CASHBACK <", value, "busDirectCashback");
            return (Criteria) this;
        }

        public Criteria andBusDirectCashbackLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_DIRECT_CASHBACK <=", value, "busDirectCashback");
            return (Criteria) this;
        }

        public Criteria andBusDirectCashbackIn(List<BigDecimal> values) {
            addCriterion("BUS_DIRECT_CASHBACK in", values, "busDirectCashback");
            return (Criteria) this;
        }

        public Criteria andBusDirectCashbackNotIn(List<BigDecimal> values) {
            addCriterion("BUS_DIRECT_CASHBACK not in", values, "busDirectCashback");
            return (Criteria) this;
        }

        public Criteria andBusDirectCashbackBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_DIRECT_CASHBACK between", value1, value2, "busDirectCashback");
            return (Criteria) this;
        }

        public Criteria andBusDirectCashbackNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_DIRECT_CASHBACK not between", value1, value2, "busDirectCashback");
            return (Criteria) this;
        }

        public Criteria andBusIndeAssIsNull() {
            addCriterion("BUS_INDE_ASS is null");
            return (Criteria) this;
        }

        public Criteria andBusIndeAssIsNotNull() {
            addCriterion("BUS_INDE_ASS is not null");
            return (Criteria) this;
        }

        public Criteria andBusIndeAssEqualTo(BigDecimal value) {
            addCriterion("BUS_INDE_ASS =", value, "busIndeAss");
            return (Criteria) this;
        }

        public Criteria andBusIndeAssNotEqualTo(BigDecimal value) {
            addCriterion("BUS_INDE_ASS <>", value, "busIndeAss");
            return (Criteria) this;
        }

        public Criteria andBusIndeAssGreaterThan(BigDecimal value) {
            addCriterion("BUS_INDE_ASS >", value, "busIndeAss");
            return (Criteria) this;
        }

        public Criteria andBusIndeAssGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_INDE_ASS >=", value, "busIndeAss");
            return (Criteria) this;
        }

        public Criteria andBusIndeAssLessThan(BigDecimal value) {
            addCriterion("BUS_INDE_ASS <", value, "busIndeAss");
            return (Criteria) this;
        }

        public Criteria andBusIndeAssLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_INDE_ASS <=", value, "busIndeAss");
            return (Criteria) this;
        }

        public Criteria andBusIndeAssIn(List<BigDecimal> values) {
            addCriterion("BUS_INDE_ASS in", values, "busIndeAss");
            return (Criteria) this;
        }

        public Criteria andBusIndeAssNotIn(List<BigDecimal> values) {
            addCriterion("BUS_INDE_ASS not in", values, "busIndeAss");
            return (Criteria) this;
        }

        public Criteria andBusIndeAssBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_INDE_ASS between", value1, value2, "busIndeAss");
            return (Criteria) this;
        }

        public Criteria andBusIndeAssNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_INDE_ASS not between", value1, value2, "busIndeAss");
            return (Criteria) this;
        }

        public Criteria andBusContactIsNull() {
            addCriterion("BUS_CONTACT is null");
            return (Criteria) this;
        }

        public Criteria andBusContactIsNotNull() {
            addCriterion("BUS_CONTACT is not null");
            return (Criteria) this;
        }

        public Criteria andBusContactEqualTo(String value) {
            addCriterion("BUS_CONTACT =", value, "busContact");
            return (Criteria) this;
        }

        public Criteria andBusContactNotEqualTo(String value) {
            addCriterion("BUS_CONTACT <>", value, "busContact");
            return (Criteria) this;
        }

        public Criteria andBusContactGreaterThan(String value) {
            addCriterion("BUS_CONTACT >", value, "busContact");
            return (Criteria) this;
        }

        public Criteria andBusContactGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_CONTACT >=", value, "busContact");
            return (Criteria) this;
        }

        public Criteria andBusContactLessThan(String value) {
            addCriterion("BUS_CONTACT <", value, "busContact");
            return (Criteria) this;
        }

        public Criteria andBusContactLessThanOrEqualTo(String value) {
            addCriterion("BUS_CONTACT <=", value, "busContact");
            return (Criteria) this;
        }

        public Criteria andBusContactLike(String value) {
            addCriterion("BUS_CONTACT like", value, "busContact");
            return (Criteria) this;
        }

        public Criteria andBusContactNotLike(String value) {
            addCriterion("BUS_CONTACT not like", value, "busContact");
            return (Criteria) this;
        }

        public Criteria andBusContactIn(List<String> values) {
            addCriterion("BUS_CONTACT in", values, "busContact");
            return (Criteria) this;
        }

        public Criteria andBusContactNotIn(List<String> values) {
            addCriterion("BUS_CONTACT not in", values, "busContact");
            return (Criteria) this;
        }

        public Criteria andBusContactBetween(String value1, String value2) {
            addCriterion("BUS_CONTACT between", value1, value2, "busContact");
            return (Criteria) this;
        }

        public Criteria andBusContactNotBetween(String value1, String value2) {
            addCriterion("BUS_CONTACT not between", value1, value2, "busContact");
            return (Criteria) this;
        }

        public Criteria andBusContactMobileIsNull() {
            addCriterion("BUS_CONTACT_MOBILE is null");
            return (Criteria) this;
        }

        public Criteria andBusContactMobileIsNotNull() {
            addCriterion("BUS_CONTACT_MOBILE is not null");
            return (Criteria) this;
        }

        public Criteria andBusContactMobileEqualTo(String value) {
            addCriterion("BUS_CONTACT_MOBILE =", value, "busContactMobile");
            return (Criteria) this;
        }

        public Criteria andBusContactMobileNotEqualTo(String value) {
            addCriterion("BUS_CONTACT_MOBILE <>", value, "busContactMobile");
            return (Criteria) this;
        }

        public Criteria andBusContactMobileGreaterThan(String value) {
            addCriterion("BUS_CONTACT_MOBILE >", value, "busContactMobile");
            return (Criteria) this;
        }

        public Criteria andBusContactMobileGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_CONTACT_MOBILE >=", value, "busContactMobile");
            return (Criteria) this;
        }

        public Criteria andBusContactMobileLessThan(String value) {
            addCriterion("BUS_CONTACT_MOBILE <", value, "busContactMobile");
            return (Criteria) this;
        }

        public Criteria andBusContactMobileLessThanOrEqualTo(String value) {
            addCriterion("BUS_CONTACT_MOBILE <=", value, "busContactMobile");
            return (Criteria) this;
        }

        public Criteria andBusContactMobileLike(String value) {
            addCriterion("BUS_CONTACT_MOBILE like", value, "busContactMobile");
            return (Criteria) this;
        }

        public Criteria andBusContactMobileNotLike(String value) {
            addCriterion("BUS_CONTACT_MOBILE not like", value, "busContactMobile");
            return (Criteria) this;
        }

        public Criteria andBusContactMobileIn(List<String> values) {
            addCriterion("BUS_CONTACT_MOBILE in", values, "busContactMobile");
            return (Criteria) this;
        }

        public Criteria andBusContactMobileNotIn(List<String> values) {
            addCriterion("BUS_CONTACT_MOBILE not in", values, "busContactMobile");
            return (Criteria) this;
        }

        public Criteria andBusContactMobileBetween(String value1, String value2) {
            addCriterion("BUS_CONTACT_MOBILE between", value1, value2, "busContactMobile");
            return (Criteria) this;
        }

        public Criteria andBusContactMobileNotBetween(String value1, String value2) {
            addCriterion("BUS_CONTACT_MOBILE not between", value1, value2, "busContactMobile");
            return (Criteria) this;
        }

        public Criteria andBusContactEmailIsNull() {
            addCriterion("BUS_CONTACT_EMAIL is null");
            return (Criteria) this;
        }

        public Criteria andBusContactEmailIsNotNull() {
            addCriterion("BUS_CONTACT_EMAIL is not null");
            return (Criteria) this;
        }

        public Criteria andBusContactEmailEqualTo(String value) {
            addCriterion("BUS_CONTACT_EMAIL =", value, "busContactEmail");
            return (Criteria) this;
        }

        public Criteria andBusContactEmailNotEqualTo(String value) {
            addCriterion("BUS_CONTACT_EMAIL <>", value, "busContactEmail");
            return (Criteria) this;
        }

        public Criteria andBusContactEmailGreaterThan(String value) {
            addCriterion("BUS_CONTACT_EMAIL >", value, "busContactEmail");
            return (Criteria) this;
        }

        public Criteria andBusContactEmailGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_CONTACT_EMAIL >=", value, "busContactEmail");
            return (Criteria) this;
        }

        public Criteria andBusContactEmailLessThan(String value) {
            addCriterion("BUS_CONTACT_EMAIL <", value, "busContactEmail");
            return (Criteria) this;
        }

        public Criteria andBusContactEmailLessThanOrEqualTo(String value) {
            addCriterion("BUS_CONTACT_EMAIL <=", value, "busContactEmail");
            return (Criteria) this;
        }

        public Criteria andBusContactEmailLike(String value) {
            addCriterion("BUS_CONTACT_EMAIL like", value, "busContactEmail");
            return (Criteria) this;
        }

        public Criteria andBusContactEmailNotLike(String value) {
            addCriterion("BUS_CONTACT_EMAIL not like", value, "busContactEmail");
            return (Criteria) this;
        }

        public Criteria andBusContactEmailIn(List<String> values) {
            addCriterion("BUS_CONTACT_EMAIL in", values, "busContactEmail");
            return (Criteria) this;
        }

        public Criteria andBusContactEmailNotIn(List<String> values) {
            addCriterion("BUS_CONTACT_EMAIL not in", values, "busContactEmail");
            return (Criteria) this;
        }

        public Criteria andBusContactEmailBetween(String value1, String value2) {
            addCriterion("BUS_CONTACT_EMAIL between", value1, value2, "busContactEmail");
            return (Criteria) this;
        }

        public Criteria andBusContactEmailNotBetween(String value1, String value2) {
            addCriterion("BUS_CONTACT_EMAIL not between", value1, value2, "busContactEmail");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonIsNull() {
            addCriterion("BUS_CONTACT_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonIsNotNull() {
            addCriterion("BUS_CONTACT_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonEqualTo(String value) {
            addCriterion("BUS_CONTACT_PERSON =", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonNotEqualTo(String value) {
            addCriterion("BUS_CONTACT_PERSON <>", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonGreaterThan(String value) {
            addCriterion("BUS_CONTACT_PERSON >", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_CONTACT_PERSON >=", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonLessThan(String value) {
            addCriterion("BUS_CONTACT_PERSON <", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonLessThanOrEqualTo(String value) {
            addCriterion("BUS_CONTACT_PERSON <=", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonLike(String value) {
            addCriterion("BUS_CONTACT_PERSON like", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonNotLike(String value) {
            addCriterion("BUS_CONTACT_PERSON not like", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonIn(List<String> values) {
            addCriterion("BUS_CONTACT_PERSON in", values, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonNotIn(List<String> values) {
            addCriterion("BUS_CONTACT_PERSON not in", values, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonBetween(String value1, String value2) {
            addCriterion("BUS_CONTACT_PERSON between", value1, value2, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonNotBetween(String value1, String value2) {
            addCriterion("BUS_CONTACT_PERSON not between", value1, value2, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusRiskEmailIsNull() {
            addCriterion("BUS_RISK_EMAIL is null");
            return (Criteria) this;
        }

        public Criteria andBusRiskEmailIsNotNull() {
            addCriterion("BUS_RISK_EMAIL is not null");
            return (Criteria) this;
        }

        public Criteria andBusRiskEmailEqualTo(String value) {
            addCriterion("BUS_RISK_EMAIL =", value, "busRiskEmail");
            return (Criteria) this;
        }

        public Criteria andBusRiskEmailNotEqualTo(String value) {
            addCriterion("BUS_RISK_EMAIL <>", value, "busRiskEmail");
            return (Criteria) this;
        }

        public Criteria andBusRiskEmailGreaterThan(String value) {
            addCriterion("BUS_RISK_EMAIL >", value, "busRiskEmail");
            return (Criteria) this;
        }

        public Criteria andBusRiskEmailGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_RISK_EMAIL >=", value, "busRiskEmail");
            return (Criteria) this;
        }

        public Criteria andBusRiskEmailLessThan(String value) {
            addCriterion("BUS_RISK_EMAIL <", value, "busRiskEmail");
            return (Criteria) this;
        }

        public Criteria andBusRiskEmailLessThanOrEqualTo(String value) {
            addCriterion("BUS_RISK_EMAIL <=", value, "busRiskEmail");
            return (Criteria) this;
        }

        public Criteria andBusRiskEmailLike(String value) {
            addCriterion("BUS_RISK_EMAIL like", value, "busRiskEmail");
            return (Criteria) this;
        }

        public Criteria andBusRiskEmailNotLike(String value) {
            addCriterion("BUS_RISK_EMAIL not like", value, "busRiskEmail");
            return (Criteria) this;
        }

        public Criteria andBusRiskEmailIn(List<String> values) {
            addCriterion("BUS_RISK_EMAIL in", values, "busRiskEmail");
            return (Criteria) this;
        }

        public Criteria andBusRiskEmailNotIn(List<String> values) {
            addCriterion("BUS_RISK_EMAIL not in", values, "busRiskEmail");
            return (Criteria) this;
        }

        public Criteria andBusRiskEmailBetween(String value1, String value2) {
            addCriterion("BUS_RISK_EMAIL between", value1, value2, "busRiskEmail");
            return (Criteria) this;
        }

        public Criteria andBusRiskEmailNotBetween(String value1, String value2) {
            addCriterion("BUS_RISK_EMAIL not between", value1, value2, "busRiskEmail");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointIsNull() {
            addCriterion("CLO_TAX_POINT is null");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointIsNotNull() {
            addCriterion("CLO_TAX_POINT is not null");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointEqualTo(BigDecimal value) {
            addCriterion("CLO_TAX_POINT =", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointNotEqualTo(BigDecimal value) {
            addCriterion("CLO_TAX_POINT <>", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointGreaterThan(BigDecimal value) {
            addCriterion("CLO_TAX_POINT >", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_TAX_POINT >=", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointLessThan(BigDecimal value) {
            addCriterion("CLO_TAX_POINT <", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_TAX_POINT <=", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointIn(List<BigDecimal> values) {
            addCriterion("CLO_TAX_POINT in", values, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointNotIn(List<BigDecimal> values) {
            addCriterion("CLO_TAX_POINT not in", values, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_TAX_POINT between", value1, value2, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_TAX_POINT not between", value1, value2, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceIsNull() {
            addCriterion("CLO_INVOICE is null");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceIsNotNull() {
            addCriterion("CLO_INVOICE is not null");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceEqualTo(BigDecimal value) {
            addCriterion("CLO_INVOICE =", value, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceNotEqualTo(BigDecimal value) {
            addCriterion("CLO_INVOICE <>", value, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceGreaterThan(BigDecimal value) {
            addCriterion("CLO_INVOICE >", value, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_INVOICE >=", value, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceLessThan(BigDecimal value) {
            addCriterion("CLO_INVOICE <", value, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_INVOICE <=", value, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceIn(List<BigDecimal> values) {
            addCriterion("CLO_INVOICE in", values, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceNotIn(List<BigDecimal> values) {
            addCriterion("CLO_INVOICE not in", values, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_INVOICE between", value1, value2, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_INVOICE not between", value1, value2, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloReceiptIsNull() {
            addCriterion("CLO_RECEIPT is null");
            return (Criteria) this;
        }

        public Criteria andCloReceiptIsNotNull() {
            addCriterion("CLO_RECEIPT is not null");
            return (Criteria) this;
        }

        public Criteria andCloReceiptEqualTo(BigDecimal value) {
            addCriterion("CLO_RECEIPT =", value, "cloReceipt");
            return (Criteria) this;
        }

        public Criteria andCloReceiptNotEqualTo(BigDecimal value) {
            addCriterion("CLO_RECEIPT <>", value, "cloReceipt");
            return (Criteria) this;
        }

        public Criteria andCloReceiptGreaterThan(BigDecimal value) {
            addCriterion("CLO_RECEIPT >", value, "cloReceipt");
            return (Criteria) this;
        }

        public Criteria andCloReceiptGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_RECEIPT >=", value, "cloReceipt");
            return (Criteria) this;
        }

        public Criteria andCloReceiptLessThan(BigDecimal value) {
            addCriterion("CLO_RECEIPT <", value, "cloReceipt");
            return (Criteria) this;
        }

        public Criteria andCloReceiptLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_RECEIPT <=", value, "cloReceipt");
            return (Criteria) this;
        }

        public Criteria andCloReceiptIn(List<BigDecimal> values) {
            addCriterion("CLO_RECEIPT in", values, "cloReceipt");
            return (Criteria) this;
        }

        public Criteria andCloReceiptNotIn(List<BigDecimal> values) {
            addCriterion("CLO_RECEIPT not in", values, "cloReceipt");
            return (Criteria) this;
        }

        public Criteria andCloReceiptBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_RECEIPT between", value1, value2, "cloReceipt");
            return (Criteria) this;
        }

        public Criteria andCloReceiptNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_RECEIPT not between", value1, value2, "cloReceipt");
            return (Criteria) this;
        }

        public Criteria andCloPayCompanyIsNull() {
            addCriterion("CLO_PAY_COMPANY is null");
            return (Criteria) this;
        }

        public Criteria andCloPayCompanyIsNotNull() {
            addCriterion("CLO_PAY_COMPANY is not null");
            return (Criteria) this;
        }

        public Criteria andCloPayCompanyEqualTo(String value) {
            addCriterion("CLO_PAY_COMPANY =", value, "cloPayCompany");
            return (Criteria) this;
        }

        public Criteria andCloPayCompanyNotEqualTo(String value) {
            addCriterion("CLO_PAY_COMPANY <>", value, "cloPayCompany");
            return (Criteria) this;
        }

        public Criteria andCloPayCompanyGreaterThan(String value) {
            addCriterion("CLO_PAY_COMPANY >", value, "cloPayCompany");
            return (Criteria) this;
        }

        public Criteria andCloPayCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("CLO_PAY_COMPANY >=", value, "cloPayCompany");
            return (Criteria) this;
        }

        public Criteria andCloPayCompanyLessThan(String value) {
            addCriterion("CLO_PAY_COMPANY <", value, "cloPayCompany");
            return (Criteria) this;
        }

        public Criteria andCloPayCompanyLessThanOrEqualTo(String value) {
            addCriterion("CLO_PAY_COMPANY <=", value, "cloPayCompany");
            return (Criteria) this;
        }

        public Criteria andCloPayCompanyLike(String value) {
            addCriterion("CLO_PAY_COMPANY like", value, "cloPayCompany");
            return (Criteria) this;
        }

        public Criteria andCloPayCompanyNotLike(String value) {
            addCriterion("CLO_PAY_COMPANY not like", value, "cloPayCompany");
            return (Criteria) this;
        }

        public Criteria andCloPayCompanyIn(List<String> values) {
            addCriterion("CLO_PAY_COMPANY in", values, "cloPayCompany");
            return (Criteria) this;
        }

        public Criteria andCloPayCompanyNotIn(List<String> values) {
            addCriterion("CLO_PAY_COMPANY not in", values, "cloPayCompany");
            return (Criteria) this;
        }

        public Criteria andCloPayCompanyBetween(String value1, String value2) {
            addCriterion("CLO_PAY_COMPANY between", value1, value2, "cloPayCompany");
            return (Criteria) this;
        }

        public Criteria andCloPayCompanyNotBetween(String value1, String value2) {
            addCriterion("CLO_PAY_COMPANY not between", value1, value2, "cloPayCompany");
            return (Criteria) this;
        }

        public Criteria andCloAgentColinfoIsNull() {
            addCriterion("CLO_AGENT_COLINFO is null");
            return (Criteria) this;
        }

        public Criteria andCloAgentColinfoIsNotNull() {
            addCriterion("CLO_AGENT_COLINFO is not null");
            return (Criteria) this;
        }

        public Criteria andCloAgentColinfoEqualTo(String value) {
            addCriterion("CLO_AGENT_COLINFO =", value, "cloAgentColinfo");
            return (Criteria) this;
        }

        public Criteria andCloAgentColinfoNotEqualTo(String value) {
            addCriterion("CLO_AGENT_COLINFO <>", value, "cloAgentColinfo");
            return (Criteria) this;
        }

        public Criteria andCloAgentColinfoGreaterThan(String value) {
            addCriterion("CLO_AGENT_COLINFO >", value, "cloAgentColinfo");
            return (Criteria) this;
        }

        public Criteria andCloAgentColinfoGreaterThanOrEqualTo(String value) {
            addCriterion("CLO_AGENT_COLINFO >=", value, "cloAgentColinfo");
            return (Criteria) this;
        }

        public Criteria andCloAgentColinfoLessThan(String value) {
            addCriterion("CLO_AGENT_COLINFO <", value, "cloAgentColinfo");
            return (Criteria) this;
        }

        public Criteria andCloAgentColinfoLessThanOrEqualTo(String value) {
            addCriterion("CLO_AGENT_COLINFO <=", value, "cloAgentColinfo");
            return (Criteria) this;
        }

        public Criteria andCloAgentColinfoLike(String value) {
            addCriterion("CLO_AGENT_COLINFO like", value, "cloAgentColinfo");
            return (Criteria) this;
        }

        public Criteria andCloAgentColinfoNotLike(String value) {
            addCriterion("CLO_AGENT_COLINFO not like", value, "cloAgentColinfo");
            return (Criteria) this;
        }

        public Criteria andCloAgentColinfoIn(List<String> values) {
            addCriterion("CLO_AGENT_COLINFO in", values, "cloAgentColinfo");
            return (Criteria) this;
        }

        public Criteria andCloAgentColinfoNotIn(List<String> values) {
            addCriterion("CLO_AGENT_COLINFO not in", values, "cloAgentColinfo");
            return (Criteria) this;
        }

        public Criteria andCloAgentColinfoBetween(String value1, String value2) {
            addCriterion("CLO_AGENT_COLINFO between", value1, value2, "cloAgentColinfo");
            return (Criteria) this;
        }

        public Criteria andCloAgentColinfoNotBetween(String value1, String value2) {
            addCriterion("CLO_AGENT_COLINFO not between", value1, value2, "cloAgentColinfo");
            return (Criteria) this;
        }

        public Criteria andBusStatusIsNull() {
            addCriterion("BUS_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andBusStatusIsNotNull() {
            addCriterion("BUS_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andBusStatusEqualTo(BigDecimal value) {
            addCriterion("BUS_STATUS =", value, "busStatus");
            return (Criteria) this;
        }

        public Criteria andBusStatusNotEqualTo(BigDecimal value) {
            addCriterion("BUS_STATUS <>", value, "busStatus");
            return (Criteria) this;
        }

        public Criteria andBusStatusGreaterThan(BigDecimal value) {
            addCriterion("BUS_STATUS >", value, "busStatus");
            return (Criteria) this;
        }

        public Criteria andBusStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_STATUS >=", value, "busStatus");
            return (Criteria) this;
        }

        public Criteria andBusStatusLessThan(BigDecimal value) {
            addCriterion("BUS_STATUS <", value, "busStatus");
            return (Criteria) this;
        }

        public Criteria andBusStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_STATUS <=", value, "busStatus");
            return (Criteria) this;
        }

        public Criteria andBusStatusIn(List<BigDecimal> values) {
            addCriterion("BUS_STATUS in", values, "busStatus");
            return (Criteria) this;
        }

        public Criteria andBusStatusNotIn(List<BigDecimal> values) {
            addCriterion("BUS_STATUS not in", values, "busStatus");
            return (Criteria) this;
        }

        public Criteria andBusStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_STATUS between", value1, value2, "busStatus");
            return (Criteria) this;
        }

        public Criteria andBusStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_STATUS not between", value1, value2, "busStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusIsNull() {
            addCriterion("CLO_REVIEW_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusIsNotNull() {
            addCriterion("CLO_REVIEW_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS =", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS <>", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusGreaterThan(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS >", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS >=", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusLessThan(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS <", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS <=", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusIn(List<BigDecimal> values) {
            addCriterion("CLO_REVIEW_STATUS in", values, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotIn(List<BigDecimal> values) {
            addCriterion("CLO_REVIEW_STATUS not in", values, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_REVIEW_STATUS between", value1, value2, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_REVIEW_STATUS not between", value1, value2, "cloReviewStatus");
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

        public Criteria andCUtimeIsNull() {
            addCriterion("C_UTIME is null");
            return (Criteria) this;
        }

        public Criteria andCUtimeIsNotNull() {
            addCriterion("C_UTIME is not null");
            return (Criteria) this;
        }

        public Criteria andCUtimeEqualTo(Date value) {
            addCriterion("C_UTIME =", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeNotEqualTo(Date value) {
            addCriterion("C_UTIME <>", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeGreaterThan(Date value) {
            addCriterion("C_UTIME >", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("C_UTIME >=", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeLessThan(Date value) {
            addCriterion("C_UTIME <", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeLessThanOrEqualTo(Date value) {
            addCriterion("C_UTIME <=", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeIn(List<Date> values) {
            addCriterion("C_UTIME in", values, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeNotIn(List<Date> values) {
            addCriterion("C_UTIME not in", values, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeBetween(Date value1, Date value2) {
            addCriterion("C_UTIME between", value1, value2, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeNotBetween(Date value1, Date value2) {
            addCriterion("C_UTIME not between", value1, value2, "cUtime");
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