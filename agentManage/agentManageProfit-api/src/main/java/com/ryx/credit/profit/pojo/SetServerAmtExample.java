package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SetServerAmtExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public SetServerAmtExample() {
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

        public Criteria andCreateDateIsNull() {
            addCriterion("CREATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("CREATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(String value) {
            addCriterion("CREATE_DATE =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(String value) {
            addCriterion("CREATE_DATE <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(String value) {
            addCriterion("CREATE_DATE >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_DATE >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(String value) {
            addCriterion("CREATE_DATE <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(String value) {
            addCriterion("CREATE_DATE <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLike(String value) {
            addCriterion("CREATE_DATE like", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotLike(String value) {
            addCriterion("CREATE_DATE not like", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<String> values) {
            addCriterion("CREATE_DATE in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<String> values) {
            addCriterion("CREATE_DATE not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(String value1, String value2) {
            addCriterion("CREATE_DATE between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(String value1, String value2) {
            addCriterion("CREATE_DATE not between", value1, value2, "createDate");
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

        public Criteria andBumCodeIsNull() {
            addCriterion("BUM_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBumCodeIsNotNull() {
            addCriterion("BUM_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBumCodeEqualTo(String value) {
            addCriterion("BUM_CODE =", value, "bumCode");
            return (Criteria) this;
        }

        public Criteria andBumCodeNotEqualTo(String value) {
            addCriterion("BUM_CODE <>", value, "bumCode");
            return (Criteria) this;
        }

        public Criteria andBumCodeGreaterThan(String value) {
            addCriterion("BUM_CODE >", value, "bumCode");
            return (Criteria) this;
        }

        public Criteria andBumCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BUM_CODE >=", value, "bumCode");
            return (Criteria) this;
        }

        public Criteria andBumCodeLessThan(String value) {
            addCriterion("BUM_CODE <", value, "bumCode");
            return (Criteria) this;
        }

        public Criteria andBumCodeLessThanOrEqualTo(String value) {
            addCriterion("BUM_CODE <=", value, "bumCode");
            return (Criteria) this;
        }

        public Criteria andBumCodeLike(String value) {
            addCriterion("BUM_CODE like", value, "bumCode");
            return (Criteria) this;
        }

        public Criteria andBumCodeNotLike(String value) {
            addCriterion("BUM_CODE not like", value, "bumCode");
            return (Criteria) this;
        }

        public Criteria andBumCodeIn(List<String> values) {
            addCriterion("BUM_CODE in", values, "bumCode");
            return (Criteria) this;
        }

        public Criteria andBumCodeNotIn(List<String> values) {
            addCriterion("BUM_CODE not in", values, "bumCode");
            return (Criteria) this;
        }

        public Criteria andBumCodeBetween(String value1, String value2) {
            addCriterion("BUM_CODE between", value1, value2, "bumCode");
            return (Criteria) this;
        }

        public Criteria andBumCodeNotBetween(String value1, String value2) {
            addCriterion("BUM_CODE not between", value1, value2, "bumCode");
            return (Criteria) this;
        }

        public Criteria andBumIdIsNull() {
            addCriterion("BUM_ID is null");
            return (Criteria) this;
        }

        public Criteria andBumIdIsNotNull() {
            addCriterion("BUM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBumIdEqualTo(String value) {
            addCriterion("BUM_ID =", value, "bumId");
            return (Criteria) this;
        }

        public Criteria andBumIdNotEqualTo(String value) {
            addCriterion("BUM_ID <>", value, "bumId");
            return (Criteria) this;
        }

        public Criteria andBumIdGreaterThan(String value) {
            addCriterion("BUM_ID >", value, "bumId");
            return (Criteria) this;
        }

        public Criteria andBumIdGreaterThanOrEqualTo(String value) {
            addCriterion("BUM_ID >=", value, "bumId");
            return (Criteria) this;
        }

        public Criteria andBumIdLessThan(String value) {
            addCriterion("BUM_ID <", value, "bumId");
            return (Criteria) this;
        }

        public Criteria andBumIdLessThanOrEqualTo(String value) {
            addCriterion("BUM_ID <=", value, "bumId");
            return (Criteria) this;
        }

        public Criteria andBumIdLike(String value) {
            addCriterion("BUM_ID like", value, "bumId");
            return (Criteria) this;
        }

        public Criteria andBumIdNotLike(String value) {
            addCriterion("BUM_ID not like", value, "bumId");
            return (Criteria) this;
        }

        public Criteria andBumIdIn(List<String> values) {
            addCriterion("BUM_ID in", values, "bumId");
            return (Criteria) this;
        }

        public Criteria andBumIdNotIn(List<String> values) {
            addCriterion("BUM_ID not in", values, "bumId");
            return (Criteria) this;
        }

        public Criteria andBumIdBetween(String value1, String value2) {
            addCriterion("BUM_ID between", value1, value2, "bumId");
            return (Criteria) this;
        }

        public Criteria andBumIdNotBetween(String value1, String value2) {
            addCriterion("BUM_ID not between", value1, value2, "bumId");
            return (Criteria) this;
        }

        public Criteria andServerTypeIsNull() {
            addCriterion("SERVER_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andServerTypeIsNotNull() {
            addCriterion("SERVER_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andServerTypeEqualTo(String value) {
            addCriterion("SERVER_TYPE =", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeNotEqualTo(String value) {
            addCriterion("SERVER_TYPE <>", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeGreaterThan(String value) {
            addCriterion("SERVER_TYPE >", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeGreaterThanOrEqualTo(String value) {
            addCriterion("SERVER_TYPE >=", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeLessThan(String value) {
            addCriterion("SERVER_TYPE <", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeLessThanOrEqualTo(String value) {
            addCriterion("SERVER_TYPE <=", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeLike(String value) {
            addCriterion("SERVER_TYPE like", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeNotLike(String value) {
            addCriterion("SERVER_TYPE not like", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeIn(List<String> values) {
            addCriterion("SERVER_TYPE in", values, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeNotIn(List<String> values) {
            addCriterion("SERVER_TYPE not in", values, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeBetween(String value1, String value2) {
            addCriterion("SERVER_TYPE between", value1, value2, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeNotBetween(String value1, String value2) {
            addCriterion("SERVER_TYPE not between", value1, value2, "serverType");
            return (Criteria) this;
        }

        public Criteria andChargePeriodIsNull() {
            addCriterion("CHARGE_PERIOD is null");
            return (Criteria) this;
        }

        public Criteria andChargePeriodIsNotNull() {
            addCriterion("CHARGE_PERIOD is not null");
            return (Criteria) this;
        }

        public Criteria andChargePeriodEqualTo(String value) {
            addCriterion("CHARGE_PERIOD =", value, "chargePeriod");
            return (Criteria) this;
        }

        public Criteria andChargePeriodNotEqualTo(String value) {
            addCriterion("CHARGE_PERIOD <>", value, "chargePeriod");
            return (Criteria) this;
        }

        public Criteria andChargePeriodGreaterThan(String value) {
            addCriterion("CHARGE_PERIOD >", value, "chargePeriod");
            return (Criteria) this;
        }

        public Criteria andChargePeriodGreaterThanOrEqualTo(String value) {
            addCriterion("CHARGE_PERIOD >=", value, "chargePeriod");
            return (Criteria) this;
        }

        public Criteria andChargePeriodLessThan(String value) {
            addCriterion("CHARGE_PERIOD <", value, "chargePeriod");
            return (Criteria) this;
        }

        public Criteria andChargePeriodLessThanOrEqualTo(String value) {
            addCriterion("CHARGE_PERIOD <=", value, "chargePeriod");
            return (Criteria) this;
        }

        public Criteria andChargePeriodLike(String value) {
            addCriterion("CHARGE_PERIOD like", value, "chargePeriod");
            return (Criteria) this;
        }

        public Criteria andChargePeriodNotLike(String value) {
            addCriterion("CHARGE_PERIOD not like", value, "chargePeriod");
            return (Criteria) this;
        }

        public Criteria andChargePeriodIn(List<String> values) {
            addCriterion("CHARGE_PERIOD in", values, "chargePeriod");
            return (Criteria) this;
        }

        public Criteria andChargePeriodNotIn(List<String> values) {
            addCriterion("CHARGE_PERIOD not in", values, "chargePeriod");
            return (Criteria) this;
        }

        public Criteria andChargePeriodBetween(String value1, String value2) {
            addCriterion("CHARGE_PERIOD between", value1, value2, "chargePeriod");
            return (Criteria) this;
        }

        public Criteria andChargePeriodNotBetween(String value1, String value2) {
            addCriterion("CHARGE_PERIOD not between", value1, value2, "chargePeriod");
            return (Criteria) this;
        }

        public Criteria andChargeProportionIsNull() {
            addCriterion("CHARGE_PROPORTION is null");
            return (Criteria) this;
        }

        public Criteria andChargeProportionIsNotNull() {
            addCriterion("CHARGE_PROPORTION is not null");
            return (Criteria) this;
        }

        public Criteria andChargeProportionEqualTo(BigDecimal value) {
            addCriterion("CHARGE_PROPORTION =", value, "chargeProportion");
            return (Criteria) this;
        }

        public Criteria andChargeProportionNotEqualTo(BigDecimal value) {
            addCriterion("CHARGE_PROPORTION <>", value, "chargeProportion");
            return (Criteria) this;
        }

        public Criteria andChargeProportionGreaterThan(BigDecimal value) {
            addCriterion("CHARGE_PROPORTION >", value, "chargeProportion");
            return (Criteria) this;
        }

        public Criteria andChargeProportionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CHARGE_PROPORTION >=", value, "chargeProportion");
            return (Criteria) this;
        }

        public Criteria andChargeProportionLessThan(BigDecimal value) {
            addCriterion("CHARGE_PROPORTION <", value, "chargeProportion");
            return (Criteria) this;
        }

        public Criteria andChargeProportionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CHARGE_PROPORTION <=", value, "chargeProportion");
            return (Criteria) this;
        }

        public Criteria andChargeProportionIn(List<BigDecimal> values) {
            addCriterion("CHARGE_PROPORTION in", values, "chargeProportion");
            return (Criteria) this;
        }

        public Criteria andChargeProportionNotIn(List<BigDecimal> values) {
            addCriterion("CHARGE_PROPORTION not in", values, "chargeProportion");
            return (Criteria) this;
        }

        public Criteria andChargeProportionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHARGE_PROPORTION between", value1, value2, "chargeProportion");
            return (Criteria) this;
        }

        public Criteria andChargeProportionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHARGE_PROPORTION not between", value1, value2, "chargeProportion");
            return (Criteria) this;
        }

        public Criteria andChargeTypeIsNull() {
            addCriterion("CHARGE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andChargeTypeIsNotNull() {
            addCriterion("CHARGE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andChargeTypeEqualTo(String value) {
            addCriterion("CHARGE_TYPE =", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeNotEqualTo(String value) {
            addCriterion("CHARGE_TYPE <>", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeGreaterThan(String value) {
            addCriterion("CHARGE_TYPE >", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("CHARGE_TYPE >=", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeLessThan(String value) {
            addCriterion("CHARGE_TYPE <", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeLessThanOrEqualTo(String value) {
            addCriterion("CHARGE_TYPE <=", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeLike(String value) {
            addCriterion("CHARGE_TYPE like", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeNotLike(String value) {
            addCriterion("CHARGE_TYPE not like", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeIn(List<String> values) {
            addCriterion("CHARGE_TYPE in", values, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeNotIn(List<String> values) {
            addCriterion("CHARGE_TYPE not in", values, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeBetween(String value1, String value2) {
            addCriterion("CHARGE_TYPE between", value1, value2, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeNotBetween(String value1, String value2) {
            addCriterion("CHARGE_TYPE not between", value1, value2, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeBaseIsNull() {
            addCriterion("CHARGE_BASE is null");
            return (Criteria) this;
        }

        public Criteria andChargeBaseIsNotNull() {
            addCriterion("CHARGE_BASE is not null");
            return (Criteria) this;
        }

        public Criteria andChargeBaseEqualTo(String value) {
            addCriterion("CHARGE_BASE =", value, "chargeBase");
            return (Criteria) this;
        }

        public Criteria andChargeBaseNotEqualTo(String value) {
            addCriterion("CHARGE_BASE <>", value, "chargeBase");
            return (Criteria) this;
        }

        public Criteria andChargeBaseGreaterThan(String value) {
            addCriterion("CHARGE_BASE >", value, "chargeBase");
            return (Criteria) this;
        }

        public Criteria andChargeBaseGreaterThanOrEqualTo(String value) {
            addCriterion("CHARGE_BASE >=", value, "chargeBase");
            return (Criteria) this;
        }

        public Criteria andChargeBaseLessThan(String value) {
            addCriterion("CHARGE_BASE <", value, "chargeBase");
            return (Criteria) this;
        }

        public Criteria andChargeBaseLessThanOrEqualTo(String value) {
            addCriterion("CHARGE_BASE <=", value, "chargeBase");
            return (Criteria) this;
        }

        public Criteria andChargeBaseLike(String value) {
            addCriterion("CHARGE_BASE like", value, "chargeBase");
            return (Criteria) this;
        }

        public Criteria andChargeBaseNotLike(String value) {
            addCriterion("CHARGE_BASE not like", value, "chargeBase");
            return (Criteria) this;
        }

        public Criteria andChargeBaseIn(List<String> values) {
            addCriterion("CHARGE_BASE in", values, "chargeBase");
            return (Criteria) this;
        }

        public Criteria andChargeBaseNotIn(List<String> values) {
            addCriterion("CHARGE_BASE not in", values, "chargeBase");
            return (Criteria) this;
        }

        public Criteria andChargeBaseBetween(String value1, String value2) {
            addCriterion("CHARGE_BASE between", value1, value2, "chargeBase");
            return (Criteria) this;
        }

        public Criteria andChargeBaseNotBetween(String value1, String value2) {
            addCriterion("CHARGE_BASE not between", value1, value2, "chargeBase");
            return (Criteria) this;
        }

        public Criteria andIsNoSonIsNull() {
            addCriterion("IS_NO_SON is null");
            return (Criteria) this;
        }

        public Criteria andIsNoSonIsNotNull() {
            addCriterion("IS_NO_SON is not null");
            return (Criteria) this;
        }

        public Criteria andIsNoSonEqualTo(String value) {
            addCriterion("IS_NO_SON =", value, "isNoSon");
            return (Criteria) this;
        }

        public Criteria andIsNoSonNotEqualTo(String value) {
            addCriterion("IS_NO_SON <>", value, "isNoSon");
            return (Criteria) this;
        }

        public Criteria andIsNoSonGreaterThan(String value) {
            addCriterion("IS_NO_SON >", value, "isNoSon");
            return (Criteria) this;
        }

        public Criteria andIsNoSonGreaterThanOrEqualTo(String value) {
            addCriterion("IS_NO_SON >=", value, "isNoSon");
            return (Criteria) this;
        }

        public Criteria andIsNoSonLessThan(String value) {
            addCriterion("IS_NO_SON <", value, "isNoSon");
            return (Criteria) this;
        }

        public Criteria andIsNoSonLessThanOrEqualTo(String value) {
            addCriterion("IS_NO_SON <=", value, "isNoSon");
            return (Criteria) this;
        }

        public Criteria andIsNoSonLike(String value) {
            addCriterion("IS_NO_SON like", value, "isNoSon");
            return (Criteria) this;
        }

        public Criteria andIsNoSonNotLike(String value) {
            addCriterion("IS_NO_SON not like", value, "isNoSon");
            return (Criteria) this;
        }

        public Criteria andIsNoSonIn(List<String> values) {
            addCriterion("IS_NO_SON in", values, "isNoSon");
            return (Criteria) this;
        }

        public Criteria andIsNoSonNotIn(List<String> values) {
            addCriterion("IS_NO_SON not in", values, "isNoSon");
            return (Criteria) this;
        }

        public Criteria andIsNoSonBetween(String value1, String value2) {
            addCriterion("IS_NO_SON between", value1, value2, "isNoSon");
            return (Criteria) this;
        }

        public Criteria andIsNoSonNotBetween(String value1, String value2) {
            addCriterion("IS_NO_SON not between", value1, value2, "isNoSon");
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