package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OLogisticsDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OLogisticsDetailExample() {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("ORDER_ID like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("ORDER_ID not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("ORDER_ID in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("ORDER_ID not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
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

        public Criteria andProIdIsNull() {
            addCriterion("PRO_ID is null");
            return (Criteria) this;
        }

        public Criteria andProIdIsNotNull() {
            addCriterion("PRO_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProIdEqualTo(String value) {
            addCriterion("PRO_ID =", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdNotEqualTo(String value) {
            addCriterion("PRO_ID <>", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdGreaterThan(String value) {
            addCriterion("PRO_ID >", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdGreaterThanOrEqualTo(String value) {
            addCriterion("PRO_ID >=", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdLessThan(String value) {
            addCriterion("PRO_ID <", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdLessThanOrEqualTo(String value) {
            addCriterion("PRO_ID <=", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdLike(String value) {
            addCriterion("PRO_ID like", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdNotLike(String value) {
            addCriterion("PRO_ID not like", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdIn(List<String> values) {
            addCriterion("PRO_ID in", values, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdNotIn(List<String> values) {
            addCriterion("PRO_ID not in", values, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdBetween(String value1, String value2) {
            addCriterion("PRO_ID between", value1, value2, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdNotBetween(String value1, String value2) {
            addCriterion("PRO_ID not between", value1, value2, "proId");
            return (Criteria) this;
        }

        public Criteria andProNameIsNull() {
            addCriterion("PRO_NAME is null");
            return (Criteria) this;
        }

        public Criteria andProNameIsNotNull() {
            addCriterion("PRO_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andProNameEqualTo(String value) {
            addCriterion("PRO_NAME =", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotEqualTo(String value) {
            addCriterion("PRO_NAME <>", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameGreaterThan(String value) {
            addCriterion("PRO_NAME >", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameGreaterThanOrEqualTo(String value) {
            addCriterion("PRO_NAME >=", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameLessThan(String value) {
            addCriterion("PRO_NAME <", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameLessThanOrEqualTo(String value) {
            addCriterion("PRO_NAME <=", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameLike(String value) {
            addCriterion("PRO_NAME like", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotLike(String value) {
            addCriterion("PRO_NAME not like", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameIn(List<String> values) {
            addCriterion("PRO_NAME in", values, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotIn(List<String> values) {
            addCriterion("PRO_NAME not in", values, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameBetween(String value1, String value2) {
            addCriterion("PRO_NAME between", value1, value2, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotBetween(String value1, String value2) {
            addCriterion("PRO_NAME not between", value1, value2, "proName");
            return (Criteria) this;
        }

        public Criteria andSettlementPriceIsNull() {
            addCriterion("SETTLEMENT_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andSettlementPriceIsNotNull() {
            addCriterion("SETTLEMENT_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andSettlementPriceEqualTo(BigDecimal value) {
            addCriterion("SETTLEMENT_PRICE =", value, "settlementPrice");
            return (Criteria) this;
        }

        public Criteria andSettlementPriceNotEqualTo(BigDecimal value) {
            addCriterion("SETTLEMENT_PRICE <>", value, "settlementPrice");
            return (Criteria) this;
        }

        public Criteria andSettlementPriceGreaterThan(BigDecimal value) {
            addCriterion("SETTLEMENT_PRICE >", value, "settlementPrice");
            return (Criteria) this;
        }

        public Criteria andSettlementPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SETTLEMENT_PRICE >=", value, "settlementPrice");
            return (Criteria) this;
        }

        public Criteria andSettlementPriceLessThan(BigDecimal value) {
            addCriterion("SETTLEMENT_PRICE <", value, "settlementPrice");
            return (Criteria) this;
        }

        public Criteria andSettlementPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SETTLEMENT_PRICE <=", value, "settlementPrice");
            return (Criteria) this;
        }

        public Criteria andSettlementPriceIn(List<BigDecimal> values) {
            addCriterion("SETTLEMENT_PRICE in", values, "settlementPrice");
            return (Criteria) this;
        }

        public Criteria andSettlementPriceNotIn(List<BigDecimal> values) {
            addCriterion("SETTLEMENT_PRICE not in", values, "settlementPrice");
            return (Criteria) this;
        }

        public Criteria andSettlementPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SETTLEMENT_PRICE between", value1, value2, "settlementPrice");
            return (Criteria) this;
        }

        public Criteria andSettlementPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SETTLEMENT_PRICE not between", value1, value2, "settlementPrice");
            return (Criteria) this;
        }

        public Criteria andActivityIdIsNull() {
            addCriterion("ACTIVITY_ID is null");
            return (Criteria) this;
        }

        public Criteria andActivityIdIsNotNull() {
            addCriterion("ACTIVITY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andActivityIdEqualTo(String value) {
            addCriterion("ACTIVITY_ID =", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotEqualTo(String value) {
            addCriterion("ACTIVITY_ID <>", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdGreaterThan(String value) {
            addCriterion("ACTIVITY_ID >", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_ID >=", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdLessThan(String value) {
            addCriterion("ACTIVITY_ID <", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdLessThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_ID <=", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdLike(String value) {
            addCriterion("ACTIVITY_ID like", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotLike(String value) {
            addCriterion("ACTIVITY_ID not like", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdIn(List<String> values) {
            addCriterion("ACTIVITY_ID in", values, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotIn(List<String> values) {
            addCriterion("ACTIVITY_ID not in", values, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdBetween(String value1, String value2) {
            addCriterion("ACTIVITY_ID between", value1, value2, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotBetween(String value1, String value2) {
            addCriterion("ACTIVITY_ID not between", value1, value2, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityNameIsNull() {
            addCriterion("ACTIVITY_NAME is null");
            return (Criteria) this;
        }

        public Criteria andActivityNameIsNotNull() {
            addCriterion("ACTIVITY_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andActivityNameEqualTo(String value) {
            addCriterion("ACTIVITY_NAME =", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameNotEqualTo(String value) {
            addCriterion("ACTIVITY_NAME <>", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameGreaterThan(String value) {
            addCriterion("ACTIVITY_NAME >", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_NAME >=", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameLessThan(String value) {
            addCriterion("ACTIVITY_NAME <", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameLessThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_NAME <=", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameLike(String value) {
            addCriterion("ACTIVITY_NAME like", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameNotLike(String value) {
            addCriterion("ACTIVITY_NAME not like", value, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameIn(List<String> values) {
            addCriterion("ACTIVITY_NAME in", values, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameNotIn(List<String> values) {
            addCriterion("ACTIVITY_NAME not in", values, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameBetween(String value1, String value2) {
            addCriterion("ACTIVITY_NAME between", value1, value2, "activityName");
            return (Criteria) this;
        }

        public Criteria andActivityNameNotBetween(String value1, String value2) {
            addCriterion("ACTIVITY_NAME not between", value1, value2, "activityName");
            return (Criteria) this;
        }

        public Criteria andGTimeIsNull() {
            addCriterion("G_TIME is null");
            return (Criteria) this;
        }

        public Criteria andGTimeIsNotNull() {
            addCriterion("G_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andGTimeEqualTo(BigDecimal value) {
            addCriterion("G_TIME =", value, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeNotEqualTo(BigDecimal value) {
            addCriterion("G_TIME <>", value, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeGreaterThan(BigDecimal value) {
            addCriterion("G_TIME >", value, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("G_TIME >=", value, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeLessThan(BigDecimal value) {
            addCriterion("G_TIME <", value, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("G_TIME <=", value, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeIn(List<BigDecimal> values) {
            addCriterion("G_TIME in", values, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeNotIn(List<BigDecimal> values) {
            addCriterion("G_TIME not in", values, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("G_TIME between", value1, value2, "gTime");
            return (Criteria) this;
        }

        public Criteria andGTimeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("G_TIME not between", value1, value2, "gTime");
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

        public Criteria andOptIdIsNull() {
            addCriterion("OPT_ID is null");
            return (Criteria) this;
        }

        public Criteria andOptIdIsNotNull() {
            addCriterion("OPT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOptIdEqualTo(String value) {
            addCriterion("OPT_ID =", value, "optId");
            return (Criteria) this;
        }

        public Criteria andOptIdNotEqualTo(String value) {
            addCriterion("OPT_ID <>", value, "optId");
            return (Criteria) this;
        }

        public Criteria andOptIdGreaterThan(String value) {
            addCriterion("OPT_ID >", value, "optId");
            return (Criteria) this;
        }

        public Criteria andOptIdGreaterThanOrEqualTo(String value) {
            addCriterion("OPT_ID >=", value, "optId");
            return (Criteria) this;
        }

        public Criteria andOptIdLessThan(String value) {
            addCriterion("OPT_ID <", value, "optId");
            return (Criteria) this;
        }

        public Criteria andOptIdLessThanOrEqualTo(String value) {
            addCriterion("OPT_ID <=", value, "optId");
            return (Criteria) this;
        }

        public Criteria andOptIdLike(String value) {
            addCriterion("OPT_ID like", value, "optId");
            return (Criteria) this;
        }

        public Criteria andOptIdNotLike(String value) {
            addCriterion("OPT_ID not like", value, "optId");
            return (Criteria) this;
        }

        public Criteria andOptIdIn(List<String> values) {
            addCriterion("OPT_ID in", values, "optId");
            return (Criteria) this;
        }

        public Criteria andOptIdNotIn(List<String> values) {
            addCriterion("OPT_ID not in", values, "optId");
            return (Criteria) this;
        }

        public Criteria andOptIdBetween(String value1, String value2) {
            addCriterion("OPT_ID between", value1, value2, "optId");
            return (Criteria) this;
        }

        public Criteria andOptIdNotBetween(String value1, String value2) {
            addCriterion("OPT_ID not between", value1, value2, "optId");
            return (Criteria) this;
        }

        public Criteria andOptTypeIsNull() {
            addCriterion("OPT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andOptTypeIsNotNull() {
            addCriterion("OPT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andOptTypeEqualTo(String value) {
            addCriterion("OPT_TYPE =", value, "optType");
            return (Criteria) this;
        }

        public Criteria andOptTypeNotEqualTo(String value) {
            addCriterion("OPT_TYPE <>", value, "optType");
            return (Criteria) this;
        }

        public Criteria andOptTypeGreaterThan(String value) {
            addCriterion("OPT_TYPE >", value, "optType");
            return (Criteria) this;
        }

        public Criteria andOptTypeGreaterThanOrEqualTo(String value) {
            addCriterion("OPT_TYPE >=", value, "optType");
            return (Criteria) this;
        }

        public Criteria andOptTypeLessThan(String value) {
            addCriterion("OPT_TYPE <", value, "optType");
            return (Criteria) this;
        }

        public Criteria andOptTypeLessThanOrEqualTo(String value) {
            addCriterion("OPT_TYPE <=", value, "optType");
            return (Criteria) this;
        }

        public Criteria andOptTypeLike(String value) {
            addCriterion("OPT_TYPE like", value, "optType");
            return (Criteria) this;
        }

        public Criteria andOptTypeNotLike(String value) {
            addCriterion("OPT_TYPE not like", value, "optType");
            return (Criteria) this;
        }

        public Criteria andOptTypeIn(List<String> values) {
            addCriterion("OPT_TYPE in", values, "optType");
            return (Criteria) this;
        }

        public Criteria andOptTypeNotIn(List<String> values) {
            addCriterion("OPT_TYPE not in", values, "optType");
            return (Criteria) this;
        }

        public Criteria andOptTypeBetween(String value1, String value2) {
            addCriterion("OPT_TYPE between", value1, value2, "optType");
            return (Criteria) this;
        }

        public Criteria andOptTypeNotBetween(String value1, String value2) {
            addCriterion("OPT_TYPE not between", value1, value2, "optType");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIdIsNull() {
            addCriterion("RETURN_ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIdIsNotNull() {
            addCriterion("RETURN_ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIdEqualTo(String value) {
            addCriterion("RETURN_ORDER_ID =", value, "returnOrderId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIdNotEqualTo(String value) {
            addCriterion("RETURN_ORDER_ID <>", value, "returnOrderId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIdGreaterThan(String value) {
            addCriterion("RETURN_ORDER_ID >", value, "returnOrderId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("RETURN_ORDER_ID >=", value, "returnOrderId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIdLessThan(String value) {
            addCriterion("RETURN_ORDER_ID <", value, "returnOrderId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIdLessThanOrEqualTo(String value) {
            addCriterion("RETURN_ORDER_ID <=", value, "returnOrderId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIdLike(String value) {
            addCriterion("RETURN_ORDER_ID like", value, "returnOrderId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIdNotLike(String value) {
            addCriterion("RETURN_ORDER_ID not like", value, "returnOrderId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIdIn(List<String> values) {
            addCriterion("RETURN_ORDER_ID in", values, "returnOrderId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIdNotIn(List<String> values) {
            addCriterion("RETURN_ORDER_ID not in", values, "returnOrderId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIdBetween(String value1, String value2) {
            addCriterion("RETURN_ORDER_ID between", value1, value2, "returnOrderId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderIdNotBetween(String value1, String value2) {
            addCriterion("RETURN_ORDER_ID not between", value1, value2, "returnOrderId");
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

        public Criteria andRecordStatusIsNull() {
            addCriterion("RECORD_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andRecordStatusIsNotNull() {
            addCriterion("RECORD_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andRecordStatusEqualTo(BigDecimal value) {
            addCriterion("RECORD_STATUS =", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusNotEqualTo(BigDecimal value) {
            addCriterion("RECORD_STATUS <>", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusGreaterThan(BigDecimal value) {
            addCriterion("RECORD_STATUS >", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RECORD_STATUS >=", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusLessThan(BigDecimal value) {
            addCriterion("RECORD_STATUS <", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RECORD_STATUS <=", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusIn(List<BigDecimal> values) {
            addCriterion("RECORD_STATUS in", values, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusNotIn(List<BigDecimal> values) {
            addCriterion("RECORD_STATUS not in", values, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RECORD_STATUS between", value1, value2, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RECORD_STATUS not between", value1, value2, "recordStatus");
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

        public Criteria andOrderNumIsNull() {
            addCriterion("ORDER_NUM is null");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNotNull() {
            addCriterion("ORDER_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNumEqualTo(String value) {
            addCriterion("ORDER_NUM =", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotEqualTo(String value) {
            addCriterion("ORDER_NUM <>", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThan(String value) {
            addCriterion("ORDER_NUM >", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_NUM >=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThan(String value) {
            addCriterion("ORDER_NUM <", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThanOrEqualTo(String value) {
            addCriterion("ORDER_NUM <=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLike(String value) {
            addCriterion("ORDER_NUM like", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotLike(String value) {
            addCriterion("ORDER_NUM not like", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumIn(List<String> values) {
            addCriterion("ORDER_NUM in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotIn(List<String> values) {
            addCriterion("ORDER_NUM not in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumBetween(String value1, String value2) {
            addCriterion("ORDER_NUM between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotBetween(String value1, String value2) {
            addCriterion("ORDER_NUM not between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andTerminalidIsNull() {
            addCriterion("TERMINALID is null");
            return (Criteria) this;
        }

        public Criteria andTerminalidIsNotNull() {
            addCriterion("TERMINALID is not null");
            return (Criteria) this;
        }

        public Criteria andTerminalidEqualTo(String value) {
            addCriterion("TERMINALID =", value, "terminalid");
            return (Criteria) this;
        }

        public Criteria andTerminalidNotEqualTo(String value) {
            addCriterion("TERMINALID <>", value, "terminalid");
            return (Criteria) this;
        }

        public Criteria andTerminalidGreaterThan(String value) {
            addCriterion("TERMINALID >", value, "terminalid");
            return (Criteria) this;
        }

        public Criteria andTerminalidGreaterThanOrEqualTo(String value) {
            addCriterion("TERMINALID >=", value, "terminalid");
            return (Criteria) this;
        }

        public Criteria andTerminalidLessThan(String value) {
            addCriterion("TERMINALID <", value, "terminalid");
            return (Criteria) this;
        }

        public Criteria andTerminalidLessThanOrEqualTo(String value) {
            addCriterion("TERMINALID <=", value, "terminalid");
            return (Criteria) this;
        }

        public Criteria andTerminalidLike(String value) {
            addCriterion("TERMINALID like", value, "terminalid");
            return (Criteria) this;
        }

        public Criteria andTerminalidNotLike(String value) {
            addCriterion("TERMINALID not like", value, "terminalid");
            return (Criteria) this;
        }

        public Criteria andTerminalidIn(List<String> values) {
            addCriterion("TERMINALID in", values, "terminalid");
            return (Criteria) this;
        }

        public Criteria andTerminalidNotIn(List<String> values) {
            addCriterion("TERMINALID not in", values, "terminalid");
            return (Criteria) this;
        }

        public Criteria andTerminalidBetween(String value1, String value2) {
            addCriterion("TERMINALID between", value1, value2, "terminalid");
            return (Criteria) this;
        }

        public Criteria andTerminalidNotBetween(String value1, String value2) {
            addCriterion("TERMINALID not between", value1, value2, "terminalid");
            return (Criteria) this;
        }

        public Criteria andTerminalidKeyIsNull() {
            addCriterion("TERMINALID_KEY is null");
            return (Criteria) this;
        }

        public Criteria andTerminalidKeyIsNotNull() {
            addCriterion("TERMINALID_KEY is not null");
            return (Criteria) this;
        }

        public Criteria andTerminalidKeyEqualTo(String value) {
            addCriterion("TERMINALID_KEY =", value, "terminalidKey");
            return (Criteria) this;
        }

        public Criteria andTerminalidKeyNotEqualTo(String value) {
            addCriterion("TERMINALID_KEY <>", value, "terminalidKey");
            return (Criteria) this;
        }

        public Criteria andTerminalidKeyGreaterThan(String value) {
            addCriterion("TERMINALID_KEY >", value, "terminalidKey");
            return (Criteria) this;
        }

        public Criteria andTerminalidKeyGreaterThanOrEqualTo(String value) {
            addCriterion("TERMINALID_KEY >=", value, "terminalidKey");
            return (Criteria) this;
        }

        public Criteria andTerminalidKeyLessThan(String value) {
            addCriterion("TERMINALID_KEY <", value, "terminalidKey");
            return (Criteria) this;
        }

        public Criteria andTerminalidKeyLessThanOrEqualTo(String value) {
            addCriterion("TERMINALID_KEY <=", value, "terminalidKey");
            return (Criteria) this;
        }

        public Criteria andTerminalidKeyLike(String value) {
            addCriterion("TERMINALID_KEY like", value, "terminalidKey");
            return (Criteria) this;
        }

        public Criteria andTerminalidKeyNotLike(String value) {
            addCriterion("TERMINALID_KEY not like", value, "terminalidKey");
            return (Criteria) this;
        }

        public Criteria andTerminalidKeyIn(List<String> values) {
            addCriterion("TERMINALID_KEY in", values, "terminalidKey");
            return (Criteria) this;
        }

        public Criteria andTerminalidKeyNotIn(List<String> values) {
            addCriterion("TERMINALID_KEY not in", values, "terminalidKey");
            return (Criteria) this;
        }

        public Criteria andTerminalidKeyBetween(String value1, String value2) {
            addCriterion("TERMINALID_KEY between", value1, value2, "terminalidKey");
            return (Criteria) this;
        }

        public Criteria andTerminalidKeyNotBetween(String value1, String value2) {
            addCriterion("TERMINALID_KEY not between", value1, value2, "terminalidKey");
            return (Criteria) this;
        }

        public Criteria andTerminalidSeqIsNull() {
            addCriterion("TERMINALID_SEQ is null");
            return (Criteria) this;
        }

        public Criteria andTerminalidSeqIsNotNull() {
            addCriterion("TERMINALID_SEQ is not null");
            return (Criteria) this;
        }

        public Criteria andTerminalidSeqEqualTo(String value) {
            addCriterion("TERMINALID_SEQ =", value, "terminalidSeq");
            return (Criteria) this;
        }

        public Criteria andTerminalidSeqNotEqualTo(String value) {
            addCriterion("TERMINALID_SEQ <>", value, "terminalidSeq");
            return (Criteria) this;
        }

        public Criteria andTerminalidSeqGreaterThan(String value) {
            addCriterion("TERMINALID_SEQ >", value, "terminalidSeq");
            return (Criteria) this;
        }

        public Criteria andTerminalidSeqGreaterThanOrEqualTo(String value) {
            addCriterion("TERMINALID_SEQ >=", value, "terminalidSeq");
            return (Criteria) this;
        }

        public Criteria andTerminalidSeqLessThan(String value) {
            addCriterion("TERMINALID_SEQ <", value, "terminalidSeq");
            return (Criteria) this;
        }

        public Criteria andTerminalidSeqLessThanOrEqualTo(String value) {
            addCriterion("TERMINALID_SEQ <=", value, "terminalidSeq");
            return (Criteria) this;
        }

        public Criteria andTerminalidSeqLike(String value) {
            addCriterion("TERMINALID_SEQ like", value, "terminalidSeq");
            return (Criteria) this;
        }

        public Criteria andTerminalidSeqNotLike(String value) {
            addCriterion("TERMINALID_SEQ not like", value, "terminalidSeq");
            return (Criteria) this;
        }

        public Criteria andTerminalidSeqIn(List<String> values) {
            addCriterion("TERMINALID_SEQ in", values, "terminalidSeq");
            return (Criteria) this;
        }

        public Criteria andTerminalidSeqNotIn(List<String> values) {
            addCriterion("TERMINALID_SEQ not in", values, "terminalidSeq");
            return (Criteria) this;
        }

        public Criteria andTerminalidSeqBetween(String value1, String value2) {
            addCriterion("TERMINALID_SEQ between", value1, value2, "terminalidSeq");
            return (Criteria) this;
        }

        public Criteria andTerminalidSeqNotBetween(String value1, String value2) {
            addCriterion("TERMINALID_SEQ not between", value1, value2, "terminalidSeq");
            return (Criteria) this;
        }

        public Criteria andTerminalidTypeIsNull() {
            addCriterion("TERMINALID_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTerminalidTypeIsNotNull() {
            addCriterion("TERMINALID_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTerminalidTypeEqualTo(String value) {
            addCriterion("TERMINALID_TYPE =", value, "terminalidType");
            return (Criteria) this;
        }

        public Criteria andTerminalidTypeNotEqualTo(String value) {
            addCriterion("TERMINALID_TYPE <>", value, "terminalidType");
            return (Criteria) this;
        }

        public Criteria andTerminalidTypeGreaterThan(String value) {
            addCriterion("TERMINALID_TYPE >", value, "terminalidType");
            return (Criteria) this;
        }

        public Criteria andTerminalidTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TERMINALID_TYPE >=", value, "terminalidType");
            return (Criteria) this;
        }

        public Criteria andTerminalidTypeLessThan(String value) {
            addCriterion("TERMINALID_TYPE <", value, "terminalidType");
            return (Criteria) this;
        }

        public Criteria andTerminalidTypeLessThanOrEqualTo(String value) {
            addCriterion("TERMINALID_TYPE <=", value, "terminalidType");
            return (Criteria) this;
        }

        public Criteria andTerminalidTypeLike(String value) {
            addCriterion("TERMINALID_TYPE like", value, "terminalidType");
            return (Criteria) this;
        }

        public Criteria andTerminalidTypeNotLike(String value) {
            addCriterion("TERMINALID_TYPE not like", value, "terminalidType");
            return (Criteria) this;
        }

        public Criteria andTerminalidTypeIn(List<String> values) {
            addCriterion("TERMINALID_TYPE in", values, "terminalidType");
            return (Criteria) this;
        }

        public Criteria andTerminalidTypeNotIn(List<String> values) {
            addCriterion("TERMINALID_TYPE not in", values, "terminalidType");
            return (Criteria) this;
        }

        public Criteria andTerminalidTypeBetween(String value1, String value2) {
            addCriterion("TERMINALID_TYPE between", value1, value2, "terminalidType");
            return (Criteria) this;
        }

        public Criteria andTerminalidTypeNotBetween(String value1, String value2) {
            addCriterion("TERMINALID_TYPE not between", value1, value2, "terminalidType");
            return (Criteria) this;
        }

        public Criteria andBusProCodeIsNull() {
            addCriterion("BUS_PRO_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBusProCodeIsNotNull() {
            addCriterion("BUS_PRO_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBusProCodeEqualTo(String value) {
            addCriterion("BUS_PRO_CODE =", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeNotEqualTo(String value) {
            addCriterion("BUS_PRO_CODE <>", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeGreaterThan(String value) {
            addCriterion("BUS_PRO_CODE >", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_PRO_CODE >=", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeLessThan(String value) {
            addCriterion("BUS_PRO_CODE <", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeLessThanOrEqualTo(String value) {
            addCriterion("BUS_PRO_CODE <=", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeLike(String value) {
            addCriterion("BUS_PRO_CODE like", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeNotLike(String value) {
            addCriterion("BUS_PRO_CODE not like", value, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeIn(List<String> values) {
            addCriterion("BUS_PRO_CODE in", values, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeNotIn(List<String> values) {
            addCriterion("BUS_PRO_CODE not in", values, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeBetween(String value1, String value2) {
            addCriterion("BUS_PRO_CODE between", value1, value2, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProCodeNotBetween(String value1, String value2) {
            addCriterion("BUS_PRO_CODE not between", value1, value2, "busProCode");
            return (Criteria) this;
        }

        public Criteria andBusProNameIsNull() {
            addCriterion("BUS_PRO_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBusProNameIsNotNull() {
            addCriterion("BUS_PRO_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBusProNameEqualTo(String value) {
            addCriterion("BUS_PRO_NAME =", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameNotEqualTo(String value) {
            addCriterion("BUS_PRO_NAME <>", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameGreaterThan(String value) {
            addCriterion("BUS_PRO_NAME >", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_PRO_NAME >=", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameLessThan(String value) {
            addCriterion("BUS_PRO_NAME <", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameLessThanOrEqualTo(String value) {
            addCriterion("BUS_PRO_NAME <=", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameLike(String value) {
            addCriterion("BUS_PRO_NAME like", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameNotLike(String value) {
            addCriterion("BUS_PRO_NAME not like", value, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameIn(List<String> values) {
            addCriterion("BUS_PRO_NAME in", values, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameNotIn(List<String> values) {
            addCriterion("BUS_PRO_NAME not in", values, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameBetween(String value1, String value2) {
            addCriterion("BUS_PRO_NAME between", value1, value2, "busProName");
            return (Criteria) this;
        }

        public Criteria andBusProNameNotBetween(String value1, String value2) {
            addCriterion("BUS_PRO_NAME not between", value1, value2, "busProName");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeIsNull() {
            addCriterion("TERM_BATCHCODE is null");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeIsNotNull() {
            addCriterion("TERM_BATCHCODE is not null");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeEqualTo(String value) {
            addCriterion("TERM_BATCHCODE =", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeNotEqualTo(String value) {
            addCriterion("TERM_BATCHCODE <>", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeGreaterThan(String value) {
            addCriterion("TERM_BATCHCODE >", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeGreaterThanOrEqualTo(String value) {
            addCriterion("TERM_BATCHCODE >=", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeLessThan(String value) {
            addCriterion("TERM_BATCHCODE <", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeLessThanOrEqualTo(String value) {
            addCriterion("TERM_BATCHCODE <=", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeLike(String value) {
            addCriterion("TERM_BATCHCODE like", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeNotLike(String value) {
            addCriterion("TERM_BATCHCODE not like", value, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeIn(List<String> values) {
            addCriterion("TERM_BATCHCODE in", values, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeNotIn(List<String> values) {
            addCriterion("TERM_BATCHCODE not in", values, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeBetween(String value1, String value2) {
            addCriterion("TERM_BATCHCODE between", value1, value2, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchcodeNotBetween(String value1, String value2) {
            addCriterion("TERM_BATCHCODE not between", value1, value2, "termBatchcode");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameIsNull() {
            addCriterion("TERM_BATCHNAME is null");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameIsNotNull() {
            addCriterion("TERM_BATCHNAME is not null");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameEqualTo(String value) {
            addCriterion("TERM_BATCHNAME =", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameNotEqualTo(String value) {
            addCriterion("TERM_BATCHNAME <>", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameGreaterThan(String value) {
            addCriterion("TERM_BATCHNAME >", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameGreaterThanOrEqualTo(String value) {
            addCriterion("TERM_BATCHNAME >=", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameLessThan(String value) {
            addCriterion("TERM_BATCHNAME <", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameLessThanOrEqualTo(String value) {
            addCriterion("TERM_BATCHNAME <=", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameLike(String value) {
            addCriterion("TERM_BATCHNAME like", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameNotLike(String value) {
            addCriterion("TERM_BATCHNAME not like", value, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameIn(List<String> values) {
            addCriterion("TERM_BATCHNAME in", values, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameNotIn(List<String> values) {
            addCriterion("TERM_BATCHNAME not in", values, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameBetween(String value1, String value2) {
            addCriterion("TERM_BATCHNAME between", value1, value2, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermBatchnameNotBetween(String value1, String value2) {
            addCriterion("TERM_BATCHNAME not between", value1, value2, "termBatchname");
            return (Criteria) this;
        }

        public Criteria andTermtypeIsNull() {
            addCriterion("TERMTYPE is null");
            return (Criteria) this;
        }

        public Criteria andTermtypeIsNotNull() {
            addCriterion("TERMTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTermtypeEqualTo(String value) {
            addCriterion("TERMTYPE =", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeNotEqualTo(String value) {
            addCriterion("TERMTYPE <>", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeGreaterThan(String value) {
            addCriterion("TERMTYPE >", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeGreaterThanOrEqualTo(String value) {
            addCriterion("TERMTYPE >=", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeLessThan(String value) {
            addCriterion("TERMTYPE <", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeLessThanOrEqualTo(String value) {
            addCriterion("TERMTYPE <=", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeLike(String value) {
            addCriterion("TERMTYPE like", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeNotLike(String value) {
            addCriterion("TERMTYPE not like", value, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeIn(List<String> values) {
            addCriterion("TERMTYPE in", values, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeNotIn(List<String> values) {
            addCriterion("TERMTYPE not in", values, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeBetween(String value1, String value2) {
            addCriterion("TERMTYPE between", value1, value2, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypeNotBetween(String value1, String value2) {
            addCriterion("TERMTYPE not between", value1, value2, "termtype");
            return (Criteria) this;
        }

        public Criteria andTermtypenameIsNull() {
            addCriterion("TERMTYPENAME is null");
            return (Criteria) this;
        }

        public Criteria andTermtypenameIsNotNull() {
            addCriterion("TERMTYPENAME is not null");
            return (Criteria) this;
        }

        public Criteria andTermtypenameEqualTo(String value) {
            addCriterion("TERMTYPENAME =", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameNotEqualTo(String value) {
            addCriterion("TERMTYPENAME <>", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameGreaterThan(String value) {
            addCriterion("TERMTYPENAME >", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameGreaterThanOrEqualTo(String value) {
            addCriterion("TERMTYPENAME >=", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameLessThan(String value) {
            addCriterion("TERMTYPENAME <", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameLessThanOrEqualTo(String value) {
            addCriterion("TERMTYPENAME <=", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameLike(String value) {
            addCriterion("TERMTYPENAME like", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameNotLike(String value) {
            addCriterion("TERMTYPENAME not like", value, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameIn(List<String> values) {
            addCriterion("TERMTYPENAME in", values, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameNotIn(List<String> values) {
            addCriterion("TERMTYPENAME not in", values, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameBetween(String value1, String value2) {
            addCriterion("TERMTYPENAME between", value1, value2, "termtypename");
            return (Criteria) this;
        }

        public Criteria andTermtypenameNotBetween(String value1, String value2) {
            addCriterion("TERMTYPENAME not between", value1, value2, "termtypename");
            return (Criteria) this;
        }

        public Criteria andSendStatusIsNull() {
            addCriterion("SEND_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andSendStatusIsNotNull() {
            addCriterion("SEND_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andSendStatusEqualTo(BigDecimal value) {
            addCriterion("SEND_STATUS =", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotEqualTo(BigDecimal value) {
            addCriterion("SEND_STATUS <>", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusGreaterThan(BigDecimal value) {
            addCriterion("SEND_STATUS >", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SEND_STATUS >=", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusLessThan(BigDecimal value) {
            addCriterion("SEND_STATUS <", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SEND_STATUS <=", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusIn(List<BigDecimal> values) {
            addCriterion("SEND_STATUS in", values, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotIn(List<BigDecimal> values) {
            addCriterion("SEND_STATUS not in", values, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SEND_STATUS between", value1, value2, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SEND_STATUS not between", value1, value2, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andTerminalidCheckIsNull() {
            addCriterion("TERMINALID_CHECK is null");
            return (Criteria) this;
        }

        public Criteria andTerminalidCheckIsNotNull() {
            addCriterion("TERMINALID_CHECK is not null");
            return (Criteria) this;
        }

        public Criteria andTerminalidCheckEqualTo(String value) {
            addCriterion("TERMINALID_CHECK =", value, "terminalidCheck");
            return (Criteria) this;
        }

        public Criteria andTerminalidCheckNotEqualTo(String value) {
            addCriterion("TERMINALID_CHECK <>", value, "terminalidCheck");
            return (Criteria) this;
        }

        public Criteria andTerminalidCheckGreaterThan(String value) {
            addCriterion("TERMINALID_CHECK >", value, "terminalidCheck");
            return (Criteria) this;
        }

        public Criteria andTerminalidCheckGreaterThanOrEqualTo(String value) {
            addCriterion("TERMINALID_CHECK >=", value, "terminalidCheck");
            return (Criteria) this;
        }

        public Criteria andTerminalidCheckLessThan(String value) {
            addCriterion("TERMINALID_CHECK <", value, "terminalidCheck");
            return (Criteria) this;
        }

        public Criteria andTerminalidCheckLessThanOrEqualTo(String value) {
            addCriterion("TERMINALID_CHECK <=", value, "terminalidCheck");
            return (Criteria) this;
        }

        public Criteria andTerminalidCheckLike(String value) {
            addCriterion("TERMINALID_CHECK like", value, "terminalidCheck");
            return (Criteria) this;
        }

        public Criteria andTerminalidCheckNotLike(String value) {
            addCriterion("TERMINALID_CHECK not like", value, "terminalidCheck");
            return (Criteria) this;
        }

        public Criteria andTerminalidCheckIn(List<String> values) {
            addCriterion("TERMINALID_CHECK in", values, "terminalidCheck");
            return (Criteria) this;
        }

        public Criteria andTerminalidCheckNotIn(List<String> values) {
            addCriterion("TERMINALID_CHECK not in", values, "terminalidCheck");
            return (Criteria) this;
        }

        public Criteria andTerminalidCheckBetween(String value1, String value2) {
            addCriterion("TERMINALID_CHECK between", value1, value2, "terminalidCheck");
            return (Criteria) this;
        }

        public Criteria andTerminalidCheckNotBetween(String value1, String value2) {
            addCriterion("TERMINALID_CHECK not between", value1, value2, "terminalidCheck");
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