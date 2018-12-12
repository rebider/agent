package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OInternetCardExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OInternetCardExample() {
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

        public Criteria andSnNumIsNull() {
            addCriterion("SN_NUM is null");
            return (Criteria) this;
        }

        public Criteria andSnNumIsNotNull() {
            addCriterion("SN_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andSnNumEqualTo(String value) {
            addCriterion("SN_NUM =", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumNotEqualTo(String value) {
            addCriterion("SN_NUM <>", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumGreaterThan(String value) {
            addCriterion("SN_NUM >", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumGreaterThanOrEqualTo(String value) {
            addCriterion("SN_NUM >=", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumLessThan(String value) {
            addCriterion("SN_NUM <", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumLessThanOrEqualTo(String value) {
            addCriterion("SN_NUM <=", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumLike(String value) {
            addCriterion("SN_NUM like", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumNotLike(String value) {
            addCriterion("SN_NUM not like", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumIn(List<String> values) {
            addCriterion("SN_NUM in", values, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumNotIn(List<String> values) {
            addCriterion("SN_NUM not in", values, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumBetween(String value1, String value2) {
            addCriterion("SN_NUM between", value1, value2, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumNotBetween(String value1, String value2) {
            addCriterion("SN_NUM not between", value1, value2, "snNum");
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

        public Criteria andLogisticsIdIsNull() {
            addCriterion("LOGISTICS_ID is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsIdIsNotNull() {
            addCriterion("LOGISTICS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsIdEqualTo(String value) {
            addCriterion("LOGISTICS_ID =", value, "logisticsId");
            return (Criteria) this;
        }

        public Criteria andLogisticsIdNotEqualTo(String value) {
            addCriterion("LOGISTICS_ID <>", value, "logisticsId");
            return (Criteria) this;
        }

        public Criteria andLogisticsIdGreaterThan(String value) {
            addCriterion("LOGISTICS_ID >", value, "logisticsId");
            return (Criteria) this;
        }

        public Criteria andLogisticsIdGreaterThanOrEqualTo(String value) {
            addCriterion("LOGISTICS_ID >=", value, "logisticsId");
            return (Criteria) this;
        }

        public Criteria andLogisticsIdLessThan(String value) {
            addCriterion("LOGISTICS_ID <", value, "logisticsId");
            return (Criteria) this;
        }

        public Criteria andLogisticsIdLessThanOrEqualTo(String value) {
            addCriterion("LOGISTICS_ID <=", value, "logisticsId");
            return (Criteria) this;
        }

        public Criteria andLogisticsIdLike(String value) {
            addCriterion("LOGISTICS_ID like", value, "logisticsId");
            return (Criteria) this;
        }

        public Criteria andLogisticsIdNotLike(String value) {
            addCriterion("LOGISTICS_ID not like", value, "logisticsId");
            return (Criteria) this;
        }

        public Criteria andLogisticsIdIn(List<String> values) {
            addCriterion("LOGISTICS_ID in", values, "logisticsId");
            return (Criteria) this;
        }

        public Criteria andLogisticsIdNotIn(List<String> values) {
            addCriterion("LOGISTICS_ID not in", values, "logisticsId");
            return (Criteria) this;
        }

        public Criteria andLogisticsIdBetween(String value1, String value2) {
            addCriterion("LOGISTICS_ID between", value1, value2, "logisticsId");
            return (Criteria) this;
        }

        public Criteria andLogisticsIdNotBetween(String value1, String value2) {
            addCriterion("LOGISTICS_ID not between", value1, value2, "logisticsId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDetailIdIsNull() {
            addCriterion("LOGISTICS_DETAIL_ID is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsDetailIdIsNotNull() {
            addCriterion("LOGISTICS_DETAIL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsDetailIdEqualTo(String value) {
            addCriterion("LOGISTICS_DETAIL_ID =", value, "logisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDetailIdNotEqualTo(String value) {
            addCriterion("LOGISTICS_DETAIL_ID <>", value, "logisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDetailIdGreaterThan(String value) {
            addCriterion("LOGISTICS_DETAIL_ID >", value, "logisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDetailIdGreaterThanOrEqualTo(String value) {
            addCriterion("LOGISTICS_DETAIL_ID >=", value, "logisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDetailIdLessThan(String value) {
            addCriterion("LOGISTICS_DETAIL_ID <", value, "logisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDetailIdLessThanOrEqualTo(String value) {
            addCriterion("LOGISTICS_DETAIL_ID <=", value, "logisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDetailIdLike(String value) {
            addCriterion("LOGISTICS_DETAIL_ID like", value, "logisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDetailIdNotLike(String value) {
            addCriterion("LOGISTICS_DETAIL_ID not like", value, "logisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDetailIdIn(List<String> values) {
            addCriterion("LOGISTICS_DETAIL_ID in", values, "logisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDetailIdNotIn(List<String> values) {
            addCriterion("LOGISTICS_DETAIL_ID not in", values, "logisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDetailIdBetween(String value1, String value2) {
            addCriterion("LOGISTICS_DETAIL_ID between", value1, value2, "logisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDetailIdNotBetween(String value1, String value2) {
            addCriterion("LOGISTICS_DETAIL_ID not between", value1, value2, "logisticsDetailId");
            return (Criteria) this;
        }

        public Criteria andProComIsNull() {
            addCriterion("PRO_COM is null");
            return (Criteria) this;
        }

        public Criteria andProComIsNotNull() {
            addCriterion("PRO_COM is not null");
            return (Criteria) this;
        }

        public Criteria andProComEqualTo(String value) {
            addCriterion("PRO_COM =", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComNotEqualTo(String value) {
            addCriterion("PRO_COM <>", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComGreaterThan(String value) {
            addCriterion("PRO_COM >", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComGreaterThanOrEqualTo(String value) {
            addCriterion("PRO_COM >=", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComLessThan(String value) {
            addCriterion("PRO_COM <", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComLessThanOrEqualTo(String value) {
            addCriterion("PRO_COM <=", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComLike(String value) {
            addCriterion("PRO_COM like", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComNotLike(String value) {
            addCriterion("PRO_COM not like", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComIn(List<String> values) {
            addCriterion("PRO_COM in", values, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComNotIn(List<String> values) {
            addCriterion("PRO_COM not in", values, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComBetween(String value1, String value2) {
            addCriterion("PRO_COM between", value1, value2, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComNotBetween(String value1, String value2) {
            addCriterion("PRO_COM not between", value1, value2, "proCom");
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

        public Criteria andCardStatusIsNull() {
            addCriterion("CARD_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andCardStatusIsNotNull() {
            addCriterion("CARD_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andCardStatusEqualTo(BigDecimal value) {
            addCriterion("CARD_STATUS =", value, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusNotEqualTo(BigDecimal value) {
            addCriterion("CARD_STATUS <>", value, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusGreaterThan(BigDecimal value) {
            addCriterion("CARD_STATUS >", value, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CARD_STATUS >=", value, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusLessThan(BigDecimal value) {
            addCriterion("CARD_STATUS <", value, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CARD_STATUS <=", value, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusIn(List<BigDecimal> values) {
            addCriterion("CARD_STATUS in", values, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusNotIn(List<BigDecimal> values) {
            addCriterion("CARD_STATUS not in", values, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CARD_STATUS between", value1, value2, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CARD_STATUS not between", value1, value2, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andExceedFlowIsNull() {
            addCriterion("EXCEED_FLOW is null");
            return (Criteria) this;
        }

        public Criteria andExceedFlowIsNotNull() {
            addCriterion("EXCEED_FLOW is not null");
            return (Criteria) this;
        }

        public Criteria andExceedFlowEqualTo(String value) {
            addCriterion("EXCEED_FLOW =", value, "exceedFlow");
            return (Criteria) this;
        }

        public Criteria andExceedFlowNotEqualTo(String value) {
            addCriterion("EXCEED_FLOW <>", value, "exceedFlow");
            return (Criteria) this;
        }

        public Criteria andExceedFlowGreaterThan(String value) {
            addCriterion("EXCEED_FLOW >", value, "exceedFlow");
            return (Criteria) this;
        }

        public Criteria andExceedFlowGreaterThanOrEqualTo(String value) {
            addCriterion("EXCEED_FLOW >=", value, "exceedFlow");
            return (Criteria) this;
        }

        public Criteria andExceedFlowLessThan(String value) {
            addCriterion("EXCEED_FLOW <", value, "exceedFlow");
            return (Criteria) this;
        }

        public Criteria andExceedFlowLessThanOrEqualTo(String value) {
            addCriterion("EXCEED_FLOW <=", value, "exceedFlow");
            return (Criteria) this;
        }

        public Criteria andExceedFlowLike(String value) {
            addCriterion("EXCEED_FLOW like", value, "exceedFlow");
            return (Criteria) this;
        }

        public Criteria andExceedFlowNotLike(String value) {
            addCriterion("EXCEED_FLOW not like", value, "exceedFlow");
            return (Criteria) this;
        }

        public Criteria andExceedFlowIn(List<String> values) {
            addCriterion("EXCEED_FLOW in", values, "exceedFlow");
            return (Criteria) this;
        }

        public Criteria andExceedFlowNotIn(List<String> values) {
            addCriterion("EXCEED_FLOW not in", values, "exceedFlow");
            return (Criteria) this;
        }

        public Criteria andExceedFlowBetween(String value1, String value2) {
            addCriterion("EXCEED_FLOW between", value1, value2, "exceedFlow");
            return (Criteria) this;
        }

        public Criteria andExceedFlowNotBetween(String value1, String value2) {
            addCriterion("EXCEED_FLOW not between", value1, value2, "exceedFlow");
            return (Criteria) this;
        }

        public Criteria andExceedFlowUnitIsNull() {
            addCriterion("EXCEED_FLOW_UNIT is null");
            return (Criteria) this;
        }

        public Criteria andExceedFlowUnitIsNotNull() {
            addCriterion("EXCEED_FLOW_UNIT is not null");
            return (Criteria) this;
        }

        public Criteria andExceedFlowUnitEqualTo(String value) {
            addCriterion("EXCEED_FLOW_UNIT =", value, "exceedFlowUnit");
            return (Criteria) this;
        }

        public Criteria andExceedFlowUnitNotEqualTo(String value) {
            addCriterion("EXCEED_FLOW_UNIT <>", value, "exceedFlowUnit");
            return (Criteria) this;
        }

        public Criteria andExceedFlowUnitGreaterThan(String value) {
            addCriterion("EXCEED_FLOW_UNIT >", value, "exceedFlowUnit");
            return (Criteria) this;
        }

        public Criteria andExceedFlowUnitGreaterThanOrEqualTo(String value) {
            addCriterion("EXCEED_FLOW_UNIT >=", value, "exceedFlowUnit");
            return (Criteria) this;
        }

        public Criteria andExceedFlowUnitLessThan(String value) {
            addCriterion("EXCEED_FLOW_UNIT <", value, "exceedFlowUnit");
            return (Criteria) this;
        }

        public Criteria andExceedFlowUnitLessThanOrEqualTo(String value) {
            addCriterion("EXCEED_FLOW_UNIT <=", value, "exceedFlowUnit");
            return (Criteria) this;
        }

        public Criteria andExceedFlowUnitLike(String value) {
            addCriterion("EXCEED_FLOW_UNIT like", value, "exceedFlowUnit");
            return (Criteria) this;
        }

        public Criteria andExceedFlowUnitNotLike(String value) {
            addCriterion("EXCEED_FLOW_UNIT not like", value, "exceedFlowUnit");
            return (Criteria) this;
        }

        public Criteria andExceedFlowUnitIn(List<String> values) {
            addCriterion("EXCEED_FLOW_UNIT in", values, "exceedFlowUnit");
            return (Criteria) this;
        }

        public Criteria andExceedFlowUnitNotIn(List<String> values) {
            addCriterion("EXCEED_FLOW_UNIT not in", values, "exceedFlowUnit");
            return (Criteria) this;
        }

        public Criteria andExceedFlowUnitBetween(String value1, String value2) {
            addCriterion("EXCEED_FLOW_UNIT between", value1, value2, "exceedFlowUnit");
            return (Criteria) this;
        }

        public Criteria andExceedFlowUnitNotBetween(String value1, String value2) {
            addCriterion("EXCEED_FLOW_UNIT not between", value1, value2, "exceedFlowUnit");
            return (Criteria) this;
        }

        public Criteria andDebtAmtIsNull() {
            addCriterion("DEBT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andDebtAmtIsNotNull() {
            addCriterion("DEBT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andDebtAmtEqualTo(BigDecimal value) {
            addCriterion("DEBT_AMT =", value, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtNotEqualTo(BigDecimal value) {
            addCriterion("DEBT_AMT <>", value, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtGreaterThan(BigDecimal value) {
            addCriterion("DEBT_AMT >", value, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DEBT_AMT >=", value, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtLessThan(BigDecimal value) {
            addCriterion("DEBT_AMT <", value, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DEBT_AMT <=", value, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtIn(List<BigDecimal> values) {
            addCriterion("DEBT_AMT in", values, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtNotIn(List<BigDecimal> values) {
            addCriterion("DEBT_AMT not in", values, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEBT_AMT between", value1, value2, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEBT_AMT not between", value1, value2, "debtAmt");
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

        public Criteria andUTimeIsNull() {
            addCriterion("U_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUTimeIsNotNull() {
            addCriterion("U_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUTimeEqualTo(Date value) {
            addCriterion("U_TIME =", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeNotEqualTo(Date value) {
            addCriterion("U_TIME <>", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeGreaterThan(Date value) {
            addCriterion("U_TIME >", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("U_TIME >=", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeLessThan(Date value) {
            addCriterion("U_TIME <", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeLessThanOrEqualTo(Date value) {
            addCriterion("U_TIME <=", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeIn(List<Date> values) {
            addCriterion("U_TIME in", values, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeNotIn(List<Date> values) {
            addCriterion("U_TIME not in", values, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeBetween(Date value1, Date value2) {
            addCriterion("U_TIME between", value1, value2, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeNotBetween(Date value1, Date value2) {
            addCriterion("U_TIME not between", value1, value2, "uTime");
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