package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentPlatFormSynExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AgentPlatFormSynExample() {
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

        public Criteria andBusIdIsNull() {
            addCriterion("BUS_ID is null");
            return (Criteria) this;
        }

        public Criteria andBusIdIsNotNull() {
            addCriterion("BUS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBusIdEqualTo(String value) {
            addCriterion("BUS_ID =", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotEqualTo(String value) {
            addCriterion("BUS_ID <>", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdGreaterThan(String value) {
            addCriterion("BUS_ID >", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_ID >=", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdLessThan(String value) {
            addCriterion("BUS_ID <", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdLessThanOrEqualTo(String value) {
            addCriterion("BUS_ID <=", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdLike(String value) {
            addCriterion("BUS_ID like", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotLike(String value) {
            addCriterion("BUS_ID not like", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdIn(List<String> values) {
            addCriterion("BUS_ID in", values, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotIn(List<String> values) {
            addCriterion("BUS_ID not in", values, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdBetween(String value1, String value2) {
            addCriterion("BUS_ID between", value1, value2, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotBetween(String value1, String value2) {
            addCriterion("BUS_ID not between", value1, value2, "busId");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeIsNull() {
            addCriterion("PLATFORM_CODE is null");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeIsNotNull() {
            addCriterion("PLATFORM_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeEqualTo(String value) {
            addCriterion("PLATFORM_CODE =", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeNotEqualTo(String value) {
            addCriterion("PLATFORM_CODE <>", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeGreaterThan(String value) {
            addCriterion("PLATFORM_CODE >", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeGreaterThanOrEqualTo(String value) {
            addCriterion("PLATFORM_CODE >=", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeLessThan(String value) {
            addCriterion("PLATFORM_CODE <", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeLessThanOrEqualTo(String value) {
            addCriterion("PLATFORM_CODE <=", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeLike(String value) {
            addCriterion("PLATFORM_CODE like", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeNotLike(String value) {
            addCriterion("PLATFORM_CODE not like", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeIn(List<String> values) {
            addCriterion("PLATFORM_CODE in", values, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeNotIn(List<String> values) {
            addCriterion("PLATFORM_CODE not in", values, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeBetween(String value1, String value2) {
            addCriterion("PLATFORM_CODE between", value1, value2, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeNotBetween(String value1, String value2) {
            addCriterion("PLATFORM_CODE not between", value1, value2, "platformCode");
            return (Criteria) this;
        }

        public Criteria andSendJsonIsNull() {
            addCriterion("SEND_JSON is null");
            return (Criteria) this;
        }

        public Criteria andSendJsonIsNotNull() {
            addCriterion("SEND_JSON is not null");
            return (Criteria) this;
        }

        public Criteria andSendJsonEqualTo(String value) {
            addCriterion("SEND_JSON =", value, "sendJson");
            return (Criteria) this;
        }

        public Criteria andSendJsonNotEqualTo(String value) {
            addCriterion("SEND_JSON <>", value, "sendJson");
            return (Criteria) this;
        }

        public Criteria andSendJsonGreaterThan(String value) {
            addCriterion("SEND_JSON >", value, "sendJson");
            return (Criteria) this;
        }

        public Criteria andSendJsonGreaterThanOrEqualTo(String value) {
            addCriterion("SEND_JSON >=", value, "sendJson");
            return (Criteria) this;
        }

        public Criteria andSendJsonLessThan(String value) {
            addCriterion("SEND_JSON <", value, "sendJson");
            return (Criteria) this;
        }

        public Criteria andSendJsonLessThanOrEqualTo(String value) {
            addCriterion("SEND_JSON <=", value, "sendJson");
            return (Criteria) this;
        }

        public Criteria andSendJsonLike(String value) {
            addCriterion("SEND_JSON like", value, "sendJson");
            return (Criteria) this;
        }

        public Criteria andSendJsonNotLike(String value) {
            addCriterion("SEND_JSON not like", value, "sendJson");
            return (Criteria) this;
        }

        public Criteria andSendJsonIn(List<String> values) {
            addCriterion("SEND_JSON in", values, "sendJson");
            return (Criteria) this;
        }

        public Criteria andSendJsonNotIn(List<String> values) {
            addCriterion("SEND_JSON not in", values, "sendJson");
            return (Criteria) this;
        }

        public Criteria andSendJsonBetween(String value1, String value2) {
            addCriterion("SEND_JSON between", value1, value2, "sendJson");
            return (Criteria) this;
        }

        public Criteria andSendJsonNotBetween(String value1, String value2) {
            addCriterion("SEND_JSON not between", value1, value2, "sendJson");
            return (Criteria) this;
        }

        public Criteria andNotifyJsonIsNull() {
            addCriterion("NOTIFY_JSON is null");
            return (Criteria) this;
        }

        public Criteria andNotifyJsonIsNotNull() {
            addCriterion("NOTIFY_JSON is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyJsonEqualTo(String value) {
            addCriterion("NOTIFY_JSON =", value, "notifyJson");
            return (Criteria) this;
        }

        public Criteria andNotifyJsonNotEqualTo(String value) {
            addCriterion("NOTIFY_JSON <>", value, "notifyJson");
            return (Criteria) this;
        }

        public Criteria andNotifyJsonGreaterThan(String value) {
            addCriterion("NOTIFY_JSON >", value, "notifyJson");
            return (Criteria) this;
        }

        public Criteria andNotifyJsonGreaterThanOrEqualTo(String value) {
            addCriterion("NOTIFY_JSON >=", value, "notifyJson");
            return (Criteria) this;
        }

        public Criteria andNotifyJsonLessThan(String value) {
            addCriterion("NOTIFY_JSON <", value, "notifyJson");
            return (Criteria) this;
        }

        public Criteria andNotifyJsonLessThanOrEqualTo(String value) {
            addCriterion("NOTIFY_JSON <=", value, "notifyJson");
            return (Criteria) this;
        }

        public Criteria andNotifyJsonLike(String value) {
            addCriterion("NOTIFY_JSON like", value, "notifyJson");
            return (Criteria) this;
        }

        public Criteria andNotifyJsonNotLike(String value) {
            addCriterion("NOTIFY_JSON not like", value, "notifyJson");
            return (Criteria) this;
        }

        public Criteria andNotifyJsonIn(List<String> values) {
            addCriterion("NOTIFY_JSON in", values, "notifyJson");
            return (Criteria) this;
        }

        public Criteria andNotifyJsonNotIn(List<String> values) {
            addCriterion("NOTIFY_JSON not in", values, "notifyJson");
            return (Criteria) this;
        }

        public Criteria andNotifyJsonBetween(String value1, String value2) {
            addCriterion("NOTIFY_JSON between", value1, value2, "notifyJson");
            return (Criteria) this;
        }

        public Criteria andNotifyJsonNotBetween(String value1, String value2) {
            addCriterion("NOTIFY_JSON not between", value1, value2, "notifyJson");
            return (Criteria) this;
        }

        public Criteria andNotifyStatusIsNull() {
            addCriterion("NOTIFY_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andNotifyStatusIsNotNull() {
            addCriterion("NOTIFY_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyStatusEqualTo(BigDecimal value) {
            addCriterion("NOTIFY_STATUS =", value, "notifyStatus");
            return (Criteria) this;
        }

        public Criteria andNotifyStatusNotEqualTo(BigDecimal value) {
            addCriterion("NOTIFY_STATUS <>", value, "notifyStatus");
            return (Criteria) this;
        }

        public Criteria andNotifyStatusGreaterThan(BigDecimal value) {
            addCriterion("NOTIFY_STATUS >", value, "notifyStatus");
            return (Criteria) this;
        }

        public Criteria andNotifyStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NOTIFY_STATUS >=", value, "notifyStatus");
            return (Criteria) this;
        }

        public Criteria andNotifyStatusLessThan(BigDecimal value) {
            addCriterion("NOTIFY_STATUS <", value, "notifyStatus");
            return (Criteria) this;
        }

        public Criteria andNotifyStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NOTIFY_STATUS <=", value, "notifyStatus");
            return (Criteria) this;
        }

        public Criteria andNotifyStatusIn(List<BigDecimal> values) {
            addCriterion("NOTIFY_STATUS in", values, "notifyStatus");
            return (Criteria) this;
        }

        public Criteria andNotifyStatusNotIn(List<BigDecimal> values) {
            addCriterion("NOTIFY_STATUS not in", values, "notifyStatus");
            return (Criteria) this;
        }

        public Criteria andNotifyStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NOTIFY_STATUS between", value1, value2, "notifyStatus");
            return (Criteria) this;
        }

        public Criteria andNotifyStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NOTIFY_STATUS not between", value1, value2, "notifyStatus");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeIsNull() {
            addCriterion("NOTIFY_TIME is null");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeIsNotNull() {
            addCriterion("NOTIFY_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeEqualTo(Date value) {
            addCriterion("NOTIFY_TIME =", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeNotEqualTo(Date value) {
            addCriterion("NOTIFY_TIME <>", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeGreaterThan(Date value) {
            addCriterion("NOTIFY_TIME >", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("NOTIFY_TIME >=", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeLessThan(Date value) {
            addCriterion("NOTIFY_TIME <", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("NOTIFY_TIME <=", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeIn(List<Date> values) {
            addCriterion("NOTIFY_TIME in", values, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeNotIn(List<Date> values) {
            addCriterion("NOTIFY_TIME not in", values, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeBetween(Date value1, Date value2) {
            addCriterion("NOTIFY_TIME between", value1, value2, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("NOTIFY_TIME not between", value1, value2, "notifyTime");
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

        public Criteria andSuccesTimeIsNull() {
            addCriterion("SUCCES_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSuccesTimeIsNotNull() {
            addCriterion("SUCCES_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSuccesTimeEqualTo(Date value) {
            addCriterion("SUCCES_TIME =", value, "succesTime");
            return (Criteria) this;
        }

        public Criteria andSuccesTimeNotEqualTo(Date value) {
            addCriterion("SUCCES_TIME <>", value, "succesTime");
            return (Criteria) this;
        }

        public Criteria andSuccesTimeGreaterThan(Date value) {
            addCriterion("SUCCES_TIME >", value, "succesTime");
            return (Criteria) this;
        }

        public Criteria andSuccesTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("SUCCES_TIME >=", value, "succesTime");
            return (Criteria) this;
        }

        public Criteria andSuccesTimeLessThan(Date value) {
            addCriterion("SUCCES_TIME <", value, "succesTime");
            return (Criteria) this;
        }

        public Criteria andSuccesTimeLessThanOrEqualTo(Date value) {
            addCriterion("SUCCES_TIME <=", value, "succesTime");
            return (Criteria) this;
        }

        public Criteria andSuccesTimeIn(List<Date> values) {
            addCriterion("SUCCES_TIME in", values, "succesTime");
            return (Criteria) this;
        }

        public Criteria andSuccesTimeNotIn(List<Date> values) {
            addCriterion("SUCCES_TIME not in", values, "succesTime");
            return (Criteria) this;
        }

        public Criteria andSuccesTimeBetween(Date value1, Date value2) {
            addCriterion("SUCCES_TIME between", value1, value2, "succesTime");
            return (Criteria) this;
        }

        public Criteria andSuccesTimeNotBetween(Date value1, Date value2) {
            addCriterion("SUCCES_TIME not between", value1, value2, "succesTime");
            return (Criteria) this;
        }

        public Criteria andNotifyCountIsNull() {
            addCriterion("NOTIFY_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andNotifyCountIsNotNull() {
            addCriterion("NOTIFY_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyCountEqualTo(BigDecimal value) {
            addCriterion("NOTIFY_COUNT =", value, "notifyCount");
            return (Criteria) this;
        }

        public Criteria andNotifyCountNotEqualTo(BigDecimal value) {
            addCriterion("NOTIFY_COUNT <>", value, "notifyCount");
            return (Criteria) this;
        }

        public Criteria andNotifyCountGreaterThan(BigDecimal value) {
            addCriterion("NOTIFY_COUNT >", value, "notifyCount");
            return (Criteria) this;
        }

        public Criteria andNotifyCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NOTIFY_COUNT >=", value, "notifyCount");
            return (Criteria) this;
        }

        public Criteria andNotifyCountLessThan(BigDecimal value) {
            addCriterion("NOTIFY_COUNT <", value, "notifyCount");
            return (Criteria) this;
        }

        public Criteria andNotifyCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NOTIFY_COUNT <=", value, "notifyCount");
            return (Criteria) this;
        }

        public Criteria andNotifyCountIn(List<BigDecimal> values) {
            addCriterion("NOTIFY_COUNT in", values, "notifyCount");
            return (Criteria) this;
        }

        public Criteria andNotifyCountNotIn(List<BigDecimal> values) {
            addCriterion("NOTIFY_COUNT not in", values, "notifyCount");
            return (Criteria) this;
        }

        public Criteria andNotifyCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NOTIFY_COUNT between", value1, value2, "notifyCount");
            return (Criteria) this;
        }

        public Criteria andNotifyCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NOTIFY_COUNT not between", value1, value2, "notifyCount");
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