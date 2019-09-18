package com.ryx.credit.activity.entity;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class KafkaSendMessageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public KafkaSendMessageExample() {
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

        public Criteria andBusidIsNull() {
            addCriterion("BUSID is null");
            return (Criteria) this;
        }

        public Criteria andBusidIsNotNull() {
            addCriterion("BUSID is not null");
            return (Criteria) this;
        }

        public Criteria andBusidEqualTo(String value) {
            addCriterion("BUSID =", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidNotEqualTo(String value) {
            addCriterion("BUSID <>", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidGreaterThan(String value) {
            addCriterion("BUSID >", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidGreaterThanOrEqualTo(String value) {
            addCriterion("BUSID >=", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidLessThan(String value) {
            addCriterion("BUSID <", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidLessThanOrEqualTo(String value) {
            addCriterion("BUSID <=", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidLike(String value) {
            addCriterion("BUSID like", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidNotLike(String value) {
            addCriterion("BUSID not like", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidIn(List<String> values) {
            addCriterion("BUSID in", values, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidNotIn(List<String> values) {
            addCriterion("BUSID not in", values, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidBetween(String value1, String value2) {
            addCriterion("BUSID between", value1, value2, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidNotBetween(String value1, String value2) {
            addCriterion("BUSID not between", value1, value2, "busid");
            return (Criteria) this;
        }

        public Criteria andBusnumIsNull() {
            addCriterion("BUSNUM is null");
            return (Criteria) this;
        }

        public Criteria andBusnumIsNotNull() {
            addCriterion("BUSNUM is not null");
            return (Criteria) this;
        }

        public Criteria andBusnumEqualTo(String value) {
            addCriterion("BUSNUM =", value, "busnum");
            return (Criteria) this;
        }

        public Criteria andBusnumNotEqualTo(String value) {
            addCriterion("BUSNUM <>", value, "busnum");
            return (Criteria) this;
        }

        public Criteria andBusnumGreaterThan(String value) {
            addCriterion("BUSNUM >", value, "busnum");
            return (Criteria) this;
        }

        public Criteria andBusnumGreaterThanOrEqualTo(String value) {
            addCriterion("BUSNUM >=", value, "busnum");
            return (Criteria) this;
        }

        public Criteria andBusnumLessThan(String value) {
            addCriterion("BUSNUM <", value, "busnum");
            return (Criteria) this;
        }

        public Criteria andBusnumLessThanOrEqualTo(String value) {
            addCriterion("BUSNUM <=", value, "busnum");
            return (Criteria) this;
        }

        public Criteria andBusnumLike(String value) {
            addCriterion("BUSNUM like", value, "busnum");
            return (Criteria) this;
        }

        public Criteria andBusnumNotLike(String value) {
            addCriterion("BUSNUM not like", value, "busnum");
            return (Criteria) this;
        }

        public Criteria andBusnumIn(List<String> values) {
            addCriterion("BUSNUM in", values, "busnum");
            return (Criteria) this;
        }

        public Criteria andBusnumNotIn(List<String> values) {
            addCriterion("BUSNUM not in", values, "busnum");
            return (Criteria) this;
        }

        public Criteria andBusnumBetween(String value1, String value2) {
            addCriterion("BUSNUM between", value1, value2, "busnum");
            return (Criteria) this;
        }

        public Criteria andBusnumNotBetween(String value1, String value2) {
            addCriterion("BUSNUM not between", value1, value2, "busnum");
            return (Criteria) this;
        }

        public Criteria andKtypeIsNull() {
            addCriterion("KTYPE is null");
            return (Criteria) this;
        }

        public Criteria andKtypeIsNotNull() {
            addCriterion("KTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andKtypeEqualTo(String value) {
            addCriterion("KTYPE =", value, "ktype");
            return (Criteria) this;
        }

        public Criteria andKtypeNotEqualTo(String value) {
            addCriterion("KTYPE <>", value, "ktype");
            return (Criteria) this;
        }

        public Criteria andKtypeGreaterThan(String value) {
            addCriterion("KTYPE >", value, "ktype");
            return (Criteria) this;
        }

        public Criteria andKtypeGreaterThanOrEqualTo(String value) {
            addCriterion("KTYPE >=", value, "ktype");
            return (Criteria) this;
        }

        public Criteria andKtypeLessThan(String value) {
            addCriterion("KTYPE <", value, "ktype");
            return (Criteria) this;
        }

        public Criteria andKtypeLessThanOrEqualTo(String value) {
            addCriterion("KTYPE <=", value, "ktype");
            return (Criteria) this;
        }

        public Criteria andKtypeLike(String value) {
            addCriterion("KTYPE like", value, "ktype");
            return (Criteria) this;
        }

        public Criteria andKtypeNotLike(String value) {
            addCriterion("KTYPE not like", value, "ktype");
            return (Criteria) this;
        }

        public Criteria andKtypeIn(List<String> values) {
            addCriterion("KTYPE in", values, "ktype");
            return (Criteria) this;
        }

        public Criteria andKtypeNotIn(List<String> values) {
            addCriterion("KTYPE not in", values, "ktype");
            return (Criteria) this;
        }

        public Criteria andKtypeBetween(String value1, String value2) {
            addCriterion("KTYPE between", value1, value2, "ktype");
            return (Criteria) this;
        }

        public Criteria andKtypeNotBetween(String value1, String value2) {
            addCriterion("KTYPE not between", value1, value2, "ktype");
            return (Criteria) this;
        }

        public Criteria andKtopicIsNull() {
            addCriterion("KTOPIC is null");
            return (Criteria) this;
        }

        public Criteria andKtopicIsNotNull() {
            addCriterion("KTOPIC is not null");
            return (Criteria) this;
        }

        public Criteria andKtopicEqualTo(String value) {
            addCriterion("KTOPIC =", value, "ktopic");
            return (Criteria) this;
        }

        public Criteria andKtopicNotEqualTo(String value) {
            addCriterion("KTOPIC <>", value, "ktopic");
            return (Criteria) this;
        }

        public Criteria andKtopicGreaterThan(String value) {
            addCriterion("KTOPIC >", value, "ktopic");
            return (Criteria) this;
        }

        public Criteria andKtopicGreaterThanOrEqualTo(String value) {
            addCriterion("KTOPIC >=", value, "ktopic");
            return (Criteria) this;
        }

        public Criteria andKtopicLessThan(String value) {
            addCriterion("KTOPIC <", value, "ktopic");
            return (Criteria) this;
        }

        public Criteria andKtopicLessThanOrEqualTo(String value) {
            addCriterion("KTOPIC <=", value, "ktopic");
            return (Criteria) this;
        }

        public Criteria andKtopicLike(String value) {
            addCriterion("KTOPIC like", value, "ktopic");
            return (Criteria) this;
        }

        public Criteria andKtopicNotLike(String value) {
            addCriterion("KTOPIC not like", value, "ktopic");
            return (Criteria) this;
        }

        public Criteria andKtopicIn(List<String> values) {
            addCriterion("KTOPIC in", values, "ktopic");
            return (Criteria) this;
        }

        public Criteria andKtopicNotIn(List<String> values) {
            addCriterion("KTOPIC not in", values, "ktopic");
            return (Criteria) this;
        }

        public Criteria andKtopicBetween(String value1, String value2) {
            addCriterion("KTOPIC between", value1, value2, "ktopic");
            return (Criteria) this;
        }

        public Criteria andKtopicNotBetween(String value1, String value2) {
            addCriterion("KTOPIC not between", value1, value2, "ktopic");
            return (Criteria) this;
        }

        public Criteria andCDateStrIsNull() {
            addCriterion("C_DATE_STR is null");
            return (Criteria) this;
        }

        public Criteria andCDateStrIsNotNull() {
            addCriterion("C_DATE_STR is not null");
            return (Criteria) this;
        }

        public Criteria andCDateStrEqualTo(String value) {
            addCriterion("C_DATE_STR =", value, "cDateStr");
            return (Criteria) this;
        }

        public Criteria andCDateStrNotEqualTo(String value) {
            addCriterion("C_DATE_STR <>", value, "cDateStr");
            return (Criteria) this;
        }

        public Criteria andCDateStrGreaterThan(String value) {
            addCriterion("C_DATE_STR >", value, "cDateStr");
            return (Criteria) this;
        }

        public Criteria andCDateStrGreaterThanOrEqualTo(String value) {
            addCriterion("C_DATE_STR >=", value, "cDateStr");
            return (Criteria) this;
        }

        public Criteria andCDateStrLessThan(String value) {
            addCriterion("C_DATE_STR <", value, "cDateStr");
            return (Criteria) this;
        }

        public Criteria andCDateStrLessThanOrEqualTo(String value) {
            addCriterion("C_DATE_STR <=", value, "cDateStr");
            return (Criteria) this;
        }

        public Criteria andCDateStrLike(String value) {
            addCriterion("C_DATE_STR like", value, "cDateStr");
            return (Criteria) this;
        }

        public Criteria andCDateStrNotLike(String value) {
            addCriterion("C_DATE_STR not like", value, "cDateStr");
            return (Criteria) this;
        }

        public Criteria andCDateStrIn(List<String> values) {
            addCriterion("C_DATE_STR in", values, "cDateStr");
            return (Criteria) this;
        }

        public Criteria andCDateStrNotIn(List<String> values) {
            addCriterion("C_DATE_STR not in", values, "cDateStr");
            return (Criteria) this;
        }

        public Criteria andCDateStrBetween(String value1, String value2) {
            addCriterion("C_DATE_STR between", value1, value2, "cDateStr");
            return (Criteria) this;
        }

        public Criteria andCDateStrNotBetween(String value1, String value2) {
            addCriterion("C_DATE_STR not between", value1, value2, "cDateStr");
            return (Criteria) this;
        }

        public Criteria andCTimeStrIsNull() {
            addCriterion("C_TIME_STR is null");
            return (Criteria) this;
        }

        public Criteria andCTimeStrIsNotNull() {
            addCriterion("C_TIME_STR is not null");
            return (Criteria) this;
        }

        public Criteria andCTimeStrEqualTo(String value) {
            addCriterion("C_TIME_STR =", value, "cTimeStr");
            return (Criteria) this;
        }

        public Criteria andCTimeStrNotEqualTo(String value) {
            addCriterion("C_TIME_STR <>", value, "cTimeStr");
            return (Criteria) this;
        }

        public Criteria andCTimeStrGreaterThan(String value) {
            addCriterion("C_TIME_STR >", value, "cTimeStr");
            return (Criteria) this;
        }

        public Criteria andCTimeStrGreaterThanOrEqualTo(String value) {
            addCriterion("C_TIME_STR >=", value, "cTimeStr");
            return (Criteria) this;
        }

        public Criteria andCTimeStrLessThan(String value) {
            addCriterion("C_TIME_STR <", value, "cTimeStr");
            return (Criteria) this;
        }

        public Criteria andCTimeStrLessThanOrEqualTo(String value) {
            addCriterion("C_TIME_STR <=", value, "cTimeStr");
            return (Criteria) this;
        }

        public Criteria andCTimeStrLike(String value) {
            addCriterion("C_TIME_STR like", value, "cTimeStr");
            return (Criteria) this;
        }

        public Criteria andCTimeStrNotLike(String value) {
            addCriterion("C_TIME_STR not like", value, "cTimeStr");
            return (Criteria) this;
        }

        public Criteria andCTimeStrIn(List<String> values) {
            addCriterion("C_TIME_STR in", values, "cTimeStr");
            return (Criteria) this;
        }

        public Criteria andCTimeStrNotIn(List<String> values) {
            addCriterion("C_TIME_STR not in", values, "cTimeStr");
            return (Criteria) this;
        }

        public Criteria andCTimeStrBetween(String value1, String value2) {
            addCriterion("C_TIME_STR between", value1, value2, "cTimeStr");
            return (Criteria) this;
        }

        public Criteria andCTimeStrNotBetween(String value1, String value2) {
            addCriterion("C_TIME_STR not between", value1, value2, "cTimeStr");
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