package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentColinfoRelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AgentColinfoRelExample() {
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

        public Criteria andAgentidIsNull() {
            addCriterion("AGENTID is null");
            return (Criteria) this;
        }

        public Criteria andAgentidIsNotNull() {
            addCriterion("AGENTID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentidEqualTo(String value) {
            addCriterion("AGENTID =", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidNotEqualTo(String value) {
            addCriterion("AGENTID <>", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidGreaterThan(String value) {
            addCriterion("AGENTID >", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidGreaterThanOrEqualTo(String value) {
            addCriterion("AGENTID >=", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidLessThan(String value) {
            addCriterion("AGENTID <", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidLessThanOrEqualTo(String value) {
            addCriterion("AGENTID <=", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidLike(String value) {
            addCriterion("AGENTID like", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidNotLike(String value) {
            addCriterion("AGENTID not like", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidIn(List<String> values) {
            addCriterion("AGENTID in", values, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidNotIn(List<String> values) {
            addCriterion("AGENTID not in", values, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidBetween(String value1, String value2) {
            addCriterion("AGENTID between", value1, value2, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidNotBetween(String value1, String value2) {
            addCriterion("AGENTID not between", value1, value2, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentbusidIsNull() {
            addCriterion("AGENTBUSID is null");
            return (Criteria) this;
        }

        public Criteria andAgentbusidIsNotNull() {
            addCriterion("AGENTBUSID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentbusidEqualTo(String value) {
            addCriterion("AGENTBUSID =", value, "agentbusid");
            return (Criteria) this;
        }

        public Criteria andAgentbusidNotEqualTo(String value) {
            addCriterion("AGENTBUSID <>", value, "agentbusid");
            return (Criteria) this;
        }

        public Criteria andAgentbusidGreaterThan(String value) {
            addCriterion("AGENTBUSID >", value, "agentbusid");
            return (Criteria) this;
        }

        public Criteria andAgentbusidGreaterThanOrEqualTo(String value) {
            addCriterion("AGENTBUSID >=", value, "agentbusid");
            return (Criteria) this;
        }

        public Criteria andAgentbusidLessThan(String value) {
            addCriterion("AGENTBUSID <", value, "agentbusid");
            return (Criteria) this;
        }

        public Criteria andAgentbusidLessThanOrEqualTo(String value) {
            addCriterion("AGENTBUSID <=", value, "agentbusid");
            return (Criteria) this;
        }

        public Criteria andAgentbusidLike(String value) {
            addCriterion("AGENTBUSID like", value, "agentbusid");
            return (Criteria) this;
        }

        public Criteria andAgentbusidNotLike(String value) {
            addCriterion("AGENTBUSID not like", value, "agentbusid");
            return (Criteria) this;
        }

        public Criteria andAgentbusidIn(List<String> values) {
            addCriterion("AGENTBUSID in", values, "agentbusid");
            return (Criteria) this;
        }

        public Criteria andAgentbusidNotIn(List<String> values) {
            addCriterion("AGENTBUSID not in", values, "agentbusid");
            return (Criteria) this;
        }

        public Criteria andAgentbusidBetween(String value1, String value2) {
            addCriterion("AGENTBUSID between", value1, value2, "agentbusid");
            return (Criteria) this;
        }

        public Criteria andAgentbusidNotBetween(String value1, String value2) {
            addCriterion("AGENTBUSID not between", value1, value2, "agentbusid");
            return (Criteria) this;
        }

        public Criteria andAgentColinfoidIsNull() {
            addCriterion("AGENT_COLINFOID is null");
            return (Criteria) this;
        }

        public Criteria andAgentColinfoidIsNotNull() {
            addCriterion("AGENT_COLINFOID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentColinfoidEqualTo(String value) {
            addCriterion("AGENT_COLINFOID =", value, "agentColinfoid");
            return (Criteria) this;
        }

        public Criteria andAgentColinfoidNotEqualTo(String value) {
            addCriterion("AGENT_COLINFOID <>", value, "agentColinfoid");
            return (Criteria) this;
        }

        public Criteria andAgentColinfoidGreaterThan(String value) {
            addCriterion("AGENT_COLINFOID >", value, "agentColinfoid");
            return (Criteria) this;
        }

        public Criteria andAgentColinfoidGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_COLINFOID >=", value, "agentColinfoid");
            return (Criteria) this;
        }

        public Criteria andAgentColinfoidLessThan(String value) {
            addCriterion("AGENT_COLINFOID <", value, "agentColinfoid");
            return (Criteria) this;
        }

        public Criteria andAgentColinfoidLessThanOrEqualTo(String value) {
            addCriterion("AGENT_COLINFOID <=", value, "agentColinfoid");
            return (Criteria) this;
        }

        public Criteria andAgentColinfoidLike(String value) {
            addCriterion("AGENT_COLINFOID like", value, "agentColinfoid");
            return (Criteria) this;
        }

        public Criteria andAgentColinfoidNotLike(String value) {
            addCriterion("AGENT_COLINFOID not like", value, "agentColinfoid");
            return (Criteria) this;
        }

        public Criteria andAgentColinfoidIn(List<String> values) {
            addCriterion("AGENT_COLINFOID in", values, "agentColinfoid");
            return (Criteria) this;
        }

        public Criteria andAgentColinfoidNotIn(List<String> values) {
            addCriterion("AGENT_COLINFOID not in", values, "agentColinfoid");
            return (Criteria) this;
        }

        public Criteria andAgentColinfoidBetween(String value1, String value2) {
            addCriterion("AGENT_COLINFOID between", value1, value2, "agentColinfoid");
            return (Criteria) this;
        }

        public Criteria andAgentColinfoidNotBetween(String value1, String value2) {
            addCriterion("AGENT_COLINFOID not between", value1, value2, "agentColinfoid");
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

        public Criteria andCUseIsNull() {
            addCriterion("C_USE is null");
            return (Criteria) this;
        }

        public Criteria andCUseIsNotNull() {
            addCriterion("C_USE is not null");
            return (Criteria) this;
        }

        public Criteria andCUseEqualTo(String value) {
            addCriterion("C_USE =", value, "cUse");
            return (Criteria) this;
        }

        public Criteria andCUseNotEqualTo(String value) {
            addCriterion("C_USE <>", value, "cUse");
            return (Criteria) this;
        }

        public Criteria andCUseGreaterThan(String value) {
            addCriterion("C_USE >", value, "cUse");
            return (Criteria) this;
        }

        public Criteria andCUseGreaterThanOrEqualTo(String value) {
            addCriterion("C_USE >=", value, "cUse");
            return (Criteria) this;
        }

        public Criteria andCUseLessThan(String value) {
            addCriterion("C_USE <", value, "cUse");
            return (Criteria) this;
        }

        public Criteria andCUseLessThanOrEqualTo(String value) {
            addCriterion("C_USE <=", value, "cUse");
            return (Criteria) this;
        }

        public Criteria andCUseLike(String value) {
            addCriterion("C_USE like", value, "cUse");
            return (Criteria) this;
        }

        public Criteria andCUseNotLike(String value) {
            addCriterion("C_USE not like", value, "cUse");
            return (Criteria) this;
        }

        public Criteria andCUseIn(List<String> values) {
            addCriterion("C_USE in", values, "cUse");
            return (Criteria) this;
        }

        public Criteria andCUseNotIn(List<String> values) {
            addCriterion("C_USE not in", values, "cUse");
            return (Criteria) this;
        }

        public Criteria andCUseBetween(String value1, String value2) {
            addCriterion("C_USE between", value1, value2, "cUse");
            return (Criteria) this;
        }

        public Criteria andCUseNotBetween(String value1, String value2) {
            addCriterion("C_USE not between", value1, value2, "cUse");
            return (Criteria) this;
        }

        public Criteria andCSortIsNull() {
            addCriterion("C_SORT is null");
            return (Criteria) this;
        }

        public Criteria andCSortIsNotNull() {
            addCriterion("C_SORT is not null");
            return (Criteria) this;
        }

        public Criteria andCSortEqualTo(BigDecimal value) {
            addCriterion("C_SORT =", value, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortNotEqualTo(BigDecimal value) {
            addCriterion("C_SORT <>", value, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortGreaterThan(BigDecimal value) {
            addCriterion("C_SORT >", value, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("C_SORT >=", value, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortLessThan(BigDecimal value) {
            addCriterion("C_SORT <", value, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortLessThanOrEqualTo(BigDecimal value) {
            addCriterion("C_SORT <=", value, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortIn(List<BigDecimal> values) {
            addCriterion("C_SORT in", values, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortNotIn(List<BigDecimal> values) {
            addCriterion("C_SORT not in", values, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_SORT between", value1, value2, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_SORT not between", value1, value2, "cSort");
            return (Criteria) this;
        }

        public Criteria andIsdefaultIsNull() {
            addCriterion("ISDEFAULT is null");
            return (Criteria) this;
        }

        public Criteria andIsdefaultIsNotNull() {
            addCriterion("ISDEFAULT is not null");
            return (Criteria) this;
        }

        public Criteria andIsdefaultEqualTo(BigDecimal value) {
            addCriterion("ISDEFAULT =", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultNotEqualTo(BigDecimal value) {
            addCriterion("ISDEFAULT <>", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultGreaterThan(BigDecimal value) {
            addCriterion("ISDEFAULT >", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ISDEFAULT >=", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultLessThan(BigDecimal value) {
            addCriterion("ISDEFAULT <", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ISDEFAULT <=", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultIn(List<BigDecimal> values) {
            addCriterion("ISDEFAULT in", values, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultNotIn(List<BigDecimal> values) {
            addCriterion("ISDEFAULT not in", values, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ISDEFAULT between", value1, value2, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ISDEFAULT not between", value1, value2, "isdefault");
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