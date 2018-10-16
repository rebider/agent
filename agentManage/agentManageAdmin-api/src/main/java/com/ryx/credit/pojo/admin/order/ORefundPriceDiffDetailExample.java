package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ORefundPriceDiffDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ORefundPriceDiffDetailExample() {
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

        public Criteria andSubOrderIdIsNull() {
            addCriterion("SUB_ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdIsNotNull() {
            addCriterion("SUB_ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdEqualTo(String value) {
            addCriterion("SUB_ORDER_ID =", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdNotEqualTo(String value) {
            addCriterion("SUB_ORDER_ID <>", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdGreaterThan(String value) {
            addCriterion("SUB_ORDER_ID >", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUB_ORDER_ID >=", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdLessThan(String value) {
            addCriterion("SUB_ORDER_ID <", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdLessThanOrEqualTo(String value) {
            addCriterion("SUB_ORDER_ID <=", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdLike(String value) {
            addCriterion("SUB_ORDER_ID like", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdNotLike(String value) {
            addCriterion("SUB_ORDER_ID not like", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdIn(List<String> values) {
            addCriterion("SUB_ORDER_ID in", values, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdNotIn(List<String> values) {
            addCriterion("SUB_ORDER_ID not in", values, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdBetween(String value1, String value2) {
            addCriterion("SUB_ORDER_ID between", value1, value2, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdNotBetween(String value1, String value2) {
            addCriterion("SUB_ORDER_ID not between", value1, value2, "subOrderId");
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

        public Criteria andActivityFrontIdIsNull() {
            addCriterion("ACTIVITY_FRONT_ID is null");
            return (Criteria) this;
        }

        public Criteria andActivityFrontIdIsNotNull() {
            addCriterion("ACTIVITY_FRONT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andActivityFrontIdEqualTo(String value) {
            addCriterion("ACTIVITY_FRONT_ID =", value, "activityFrontId");
            return (Criteria) this;
        }

        public Criteria andActivityFrontIdNotEqualTo(String value) {
            addCriterion("ACTIVITY_FRONT_ID <>", value, "activityFrontId");
            return (Criteria) this;
        }

        public Criteria andActivityFrontIdGreaterThan(String value) {
            addCriterion("ACTIVITY_FRONT_ID >", value, "activityFrontId");
            return (Criteria) this;
        }

        public Criteria andActivityFrontIdGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_FRONT_ID >=", value, "activityFrontId");
            return (Criteria) this;
        }

        public Criteria andActivityFrontIdLessThan(String value) {
            addCriterion("ACTIVITY_FRONT_ID <", value, "activityFrontId");
            return (Criteria) this;
        }

        public Criteria andActivityFrontIdLessThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_FRONT_ID <=", value, "activityFrontId");
            return (Criteria) this;
        }

        public Criteria andActivityFrontIdLike(String value) {
            addCriterion("ACTIVITY_FRONT_ID like", value, "activityFrontId");
            return (Criteria) this;
        }

        public Criteria andActivityFrontIdNotLike(String value) {
            addCriterion("ACTIVITY_FRONT_ID not like", value, "activityFrontId");
            return (Criteria) this;
        }

        public Criteria andActivityFrontIdIn(List<String> values) {
            addCriterion("ACTIVITY_FRONT_ID in", values, "activityFrontId");
            return (Criteria) this;
        }

        public Criteria andActivityFrontIdNotIn(List<String> values) {
            addCriterion("ACTIVITY_FRONT_ID not in", values, "activityFrontId");
            return (Criteria) this;
        }

        public Criteria andActivityFrontIdBetween(String value1, String value2) {
            addCriterion("ACTIVITY_FRONT_ID between", value1, value2, "activityFrontId");
            return (Criteria) this;
        }

        public Criteria andActivityFrontIdNotBetween(String value1, String value2) {
            addCriterion("ACTIVITY_FRONT_ID not between", value1, value2, "activityFrontId");
            return (Criteria) this;
        }

        public Criteria andActivityRealIdIsNull() {
            addCriterion("ACTIVITY_REAL_ID is null");
            return (Criteria) this;
        }

        public Criteria andActivityRealIdIsNotNull() {
            addCriterion("ACTIVITY_REAL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andActivityRealIdEqualTo(String value) {
            addCriterion("ACTIVITY_REAL_ID =", value, "activityRealId");
            return (Criteria) this;
        }

        public Criteria andActivityRealIdNotEqualTo(String value) {
            addCriterion("ACTIVITY_REAL_ID <>", value, "activityRealId");
            return (Criteria) this;
        }

        public Criteria andActivityRealIdGreaterThan(String value) {
            addCriterion("ACTIVITY_REAL_ID >", value, "activityRealId");
            return (Criteria) this;
        }

        public Criteria andActivityRealIdGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_REAL_ID >=", value, "activityRealId");
            return (Criteria) this;
        }

        public Criteria andActivityRealIdLessThan(String value) {
            addCriterion("ACTIVITY_REAL_ID <", value, "activityRealId");
            return (Criteria) this;
        }

        public Criteria andActivityRealIdLessThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_REAL_ID <=", value, "activityRealId");
            return (Criteria) this;
        }

        public Criteria andActivityRealIdLike(String value) {
            addCriterion("ACTIVITY_REAL_ID like", value, "activityRealId");
            return (Criteria) this;
        }

        public Criteria andActivityRealIdNotLike(String value) {
            addCriterion("ACTIVITY_REAL_ID not like", value, "activityRealId");
            return (Criteria) this;
        }

        public Criteria andActivityRealIdIn(List<String> values) {
            addCriterion("ACTIVITY_REAL_ID in", values, "activityRealId");
            return (Criteria) this;
        }

        public Criteria andActivityRealIdNotIn(List<String> values) {
            addCriterion("ACTIVITY_REAL_ID not in", values, "activityRealId");
            return (Criteria) this;
        }

        public Criteria andActivityRealIdBetween(String value1, String value2) {
            addCriterion("ACTIVITY_REAL_ID between", value1, value2, "activityRealId");
            return (Criteria) this;
        }

        public Criteria andActivityRealIdNotBetween(String value1, String value2) {
            addCriterion("ACTIVITY_REAL_ID not between", value1, value2, "activityRealId");
            return (Criteria) this;
        }

        public Criteria andRefundPriceDiffIdIsNull() {
            addCriterion("REFUND_PRICE_DIFF_ID is null");
            return (Criteria) this;
        }

        public Criteria andRefundPriceDiffIdIsNotNull() {
            addCriterion("REFUND_PRICE_DIFF_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRefundPriceDiffIdEqualTo(String value) {
            addCriterion("REFUND_PRICE_DIFF_ID =", value, "refundPriceDiffId");
            return (Criteria) this;
        }

        public Criteria andRefundPriceDiffIdNotEqualTo(String value) {
            addCriterion("REFUND_PRICE_DIFF_ID <>", value, "refundPriceDiffId");
            return (Criteria) this;
        }

        public Criteria andRefundPriceDiffIdGreaterThan(String value) {
            addCriterion("REFUND_PRICE_DIFF_ID >", value, "refundPriceDiffId");
            return (Criteria) this;
        }

        public Criteria andRefundPriceDiffIdGreaterThanOrEqualTo(String value) {
            addCriterion("REFUND_PRICE_DIFF_ID >=", value, "refundPriceDiffId");
            return (Criteria) this;
        }

        public Criteria andRefundPriceDiffIdLessThan(String value) {
            addCriterion("REFUND_PRICE_DIFF_ID <", value, "refundPriceDiffId");
            return (Criteria) this;
        }

        public Criteria andRefundPriceDiffIdLessThanOrEqualTo(String value) {
            addCriterion("REFUND_PRICE_DIFF_ID <=", value, "refundPriceDiffId");
            return (Criteria) this;
        }

        public Criteria andRefundPriceDiffIdLike(String value) {
            addCriterion("REFUND_PRICE_DIFF_ID like", value, "refundPriceDiffId");
            return (Criteria) this;
        }

        public Criteria andRefundPriceDiffIdNotLike(String value) {
            addCriterion("REFUND_PRICE_DIFF_ID not like", value, "refundPriceDiffId");
            return (Criteria) this;
        }

        public Criteria andRefundPriceDiffIdIn(List<String> values) {
            addCriterion("REFUND_PRICE_DIFF_ID in", values, "refundPriceDiffId");
            return (Criteria) this;
        }

        public Criteria andRefundPriceDiffIdNotIn(List<String> values) {
            addCriterion("REFUND_PRICE_DIFF_ID not in", values, "refundPriceDiffId");
            return (Criteria) this;
        }

        public Criteria andRefundPriceDiffIdBetween(String value1, String value2) {
            addCriterion("REFUND_PRICE_DIFF_ID between", value1, value2, "refundPriceDiffId");
            return (Criteria) this;
        }

        public Criteria andRefundPriceDiffIdNotBetween(String value1, String value2) {
            addCriterion("REFUND_PRICE_DIFF_ID not between", value1, value2, "refundPriceDiffId");
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

        public Criteria andChangeCountIsNull() {
            addCriterion("CHANGE_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andChangeCountIsNotNull() {
            addCriterion("CHANGE_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andChangeCountEqualTo(BigDecimal value) {
            addCriterion("CHANGE_COUNT =", value, "changeCount");
            return (Criteria) this;
        }

        public Criteria andChangeCountNotEqualTo(BigDecimal value) {
            addCriterion("CHANGE_COUNT <>", value, "changeCount");
            return (Criteria) this;
        }

        public Criteria andChangeCountGreaterThan(BigDecimal value) {
            addCriterion("CHANGE_COUNT >", value, "changeCount");
            return (Criteria) this;
        }

        public Criteria andChangeCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CHANGE_COUNT >=", value, "changeCount");
            return (Criteria) this;
        }

        public Criteria andChangeCountLessThan(BigDecimal value) {
            addCriterion("CHANGE_COUNT <", value, "changeCount");
            return (Criteria) this;
        }

        public Criteria andChangeCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CHANGE_COUNT <=", value, "changeCount");
            return (Criteria) this;
        }

        public Criteria andChangeCountIn(List<BigDecimal> values) {
            addCriterion("CHANGE_COUNT in", values, "changeCount");
            return (Criteria) this;
        }

        public Criteria andChangeCountNotIn(List<BigDecimal> values) {
            addCriterion("CHANGE_COUNT not in", values, "changeCount");
            return (Criteria) this;
        }

        public Criteria andChangeCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHANGE_COUNT between", value1, value2, "changeCount");
            return (Criteria) this;
        }

        public Criteria andChangeCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHANGE_COUNT not between", value1, value2, "changeCount");
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

        public Criteria andActivityWayIsNull() {
            addCriterion("ACTIVITY_WAY is null");
            return (Criteria) this;
        }

        public Criteria andActivityWayIsNotNull() {
            addCriterion("ACTIVITY_WAY is not null");
            return (Criteria) this;
        }

        public Criteria andActivityWayEqualTo(String value) {
            addCriterion("ACTIVITY_WAY =", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayNotEqualTo(String value) {
            addCriterion("ACTIVITY_WAY <>", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayGreaterThan(String value) {
            addCriterion("ACTIVITY_WAY >", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_WAY >=", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayLessThan(String value) {
            addCriterion("ACTIVITY_WAY <", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayLessThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_WAY <=", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayLike(String value) {
            addCriterion("ACTIVITY_WAY like", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayNotLike(String value) {
            addCriterion("ACTIVITY_WAY not like", value, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayIn(List<String> values) {
            addCriterion("ACTIVITY_WAY in", values, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayNotIn(List<String> values) {
            addCriterion("ACTIVITY_WAY not in", values, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayBetween(String value1, String value2) {
            addCriterion("ACTIVITY_WAY between", value1, value2, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityWayNotBetween(String value1, String value2) {
            addCriterion("ACTIVITY_WAY not between", value1, value2, "activityWay");
            return (Criteria) this;
        }

        public Criteria andActivityRuleIsNull() {
            addCriterion("ACTIVITY_RULE is null");
            return (Criteria) this;
        }

        public Criteria andActivityRuleIsNotNull() {
            addCriterion("ACTIVITY_RULE is not null");
            return (Criteria) this;
        }

        public Criteria andActivityRuleEqualTo(String value) {
            addCriterion("ACTIVITY_RULE =", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleNotEqualTo(String value) {
            addCriterion("ACTIVITY_RULE <>", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleGreaterThan(String value) {
            addCriterion("ACTIVITY_RULE >", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_RULE >=", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleLessThan(String value) {
            addCriterion("ACTIVITY_RULE <", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleLessThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_RULE <=", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleLike(String value) {
            addCriterion("ACTIVITY_RULE like", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleNotLike(String value) {
            addCriterion("ACTIVITY_RULE not like", value, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleIn(List<String> values) {
            addCriterion("ACTIVITY_RULE in", values, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleNotIn(List<String> values) {
            addCriterion("ACTIVITY_RULE not in", values, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleBetween(String value1, String value2) {
            addCriterion("ACTIVITY_RULE between", value1, value2, "activityRule");
            return (Criteria) this;
        }

        public Criteria andActivityRuleNotBetween(String value1, String value2) {
            addCriterion("ACTIVITY_RULE not between", value1, value2, "activityRule");
            return (Criteria) this;
        }

        public Criteria andFrontPriceIsNull() {
            addCriterion("FRONT_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andFrontPriceIsNotNull() {
            addCriterion("FRONT_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andFrontPriceEqualTo(BigDecimal value) {
            addCriterion("FRONT_PRICE =", value, "frontPrice");
            return (Criteria) this;
        }

        public Criteria andFrontPriceNotEqualTo(BigDecimal value) {
            addCriterion("FRONT_PRICE <>", value, "frontPrice");
            return (Criteria) this;
        }

        public Criteria andFrontPriceGreaterThan(BigDecimal value) {
            addCriterion("FRONT_PRICE >", value, "frontPrice");
            return (Criteria) this;
        }

        public Criteria andFrontPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FRONT_PRICE >=", value, "frontPrice");
            return (Criteria) this;
        }

        public Criteria andFrontPriceLessThan(BigDecimal value) {
            addCriterion("FRONT_PRICE <", value, "frontPrice");
            return (Criteria) this;
        }

        public Criteria andFrontPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FRONT_PRICE <=", value, "frontPrice");
            return (Criteria) this;
        }

        public Criteria andFrontPriceIn(List<BigDecimal> values) {
            addCriterion("FRONT_PRICE in", values, "frontPrice");
            return (Criteria) this;
        }

        public Criteria andFrontPriceNotIn(List<BigDecimal> values) {
            addCriterion("FRONT_PRICE not in", values, "frontPrice");
            return (Criteria) this;
        }

        public Criteria andFrontPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FRONT_PRICE between", value1, value2, "frontPrice");
            return (Criteria) this;
        }

        public Criteria andFrontPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FRONT_PRICE not between", value1, value2, "frontPrice");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("PRICE is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("PRICE =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("PRICE <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("PRICE >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRICE >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("PRICE <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRICE <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("PRICE in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("PRICE not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRICE between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRICE not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andSTimeIsNull() {
            addCriterion("S_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSTimeIsNotNull() {
            addCriterion("S_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSTimeEqualTo(Date value) {
            addCriterion("S_TIME =", value, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeNotEqualTo(Date value) {
            addCriterion("S_TIME <>", value, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeGreaterThan(Date value) {
            addCriterion("S_TIME >", value, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("S_TIME >=", value, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeLessThan(Date value) {
            addCriterion("S_TIME <", value, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeLessThanOrEqualTo(Date value) {
            addCriterion("S_TIME <=", value, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeIn(List<Date> values) {
            addCriterion("S_TIME in", values, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeNotIn(List<Date> values) {
            addCriterion("S_TIME not in", values, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeBetween(Date value1, Date value2) {
            addCriterion("S_TIME between", value1, value2, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeNotBetween(Date value1, Date value2) {
            addCriterion("S_TIME not between", value1, value2, "sTime");
            return (Criteria) this;
        }

        public Criteria andBeginSnIsNull() {
            addCriterion("BEGIN_SN is null");
            return (Criteria) this;
        }

        public Criteria andBeginSnIsNotNull() {
            addCriterion("BEGIN_SN is not null");
            return (Criteria) this;
        }

        public Criteria andBeginSnEqualTo(String value) {
            addCriterion("BEGIN_SN =", value, "beginSn");
            return (Criteria) this;
        }

        public Criteria andBeginSnNotEqualTo(String value) {
            addCriterion("BEGIN_SN <>", value, "beginSn");
            return (Criteria) this;
        }

        public Criteria andBeginSnGreaterThan(String value) {
            addCriterion("BEGIN_SN >", value, "beginSn");
            return (Criteria) this;
        }

        public Criteria andBeginSnGreaterThanOrEqualTo(String value) {
            addCriterion("BEGIN_SN >=", value, "beginSn");
            return (Criteria) this;
        }

        public Criteria andBeginSnLessThan(String value) {
            addCriterion("BEGIN_SN <", value, "beginSn");
            return (Criteria) this;
        }

        public Criteria andBeginSnLessThanOrEqualTo(String value) {
            addCriterion("BEGIN_SN <=", value, "beginSn");
            return (Criteria) this;
        }

        public Criteria andBeginSnLike(String value) {
            addCriterion("BEGIN_SN like", value, "beginSn");
            return (Criteria) this;
        }

        public Criteria andBeginSnNotLike(String value) {
            addCriterion("BEGIN_SN not like", value, "beginSn");
            return (Criteria) this;
        }

        public Criteria andBeginSnIn(List<String> values) {
            addCriterion("BEGIN_SN in", values, "beginSn");
            return (Criteria) this;
        }

        public Criteria andBeginSnNotIn(List<String> values) {
            addCriterion("BEGIN_SN not in", values, "beginSn");
            return (Criteria) this;
        }

        public Criteria andBeginSnBetween(String value1, String value2) {
            addCriterion("BEGIN_SN between", value1, value2, "beginSn");
            return (Criteria) this;
        }

        public Criteria andBeginSnNotBetween(String value1, String value2) {
            addCriterion("BEGIN_SN not between", value1, value2, "beginSn");
            return (Criteria) this;
        }

        public Criteria andEndSnIsNull() {
            addCriterion("END_SN is null");
            return (Criteria) this;
        }

        public Criteria andEndSnIsNotNull() {
            addCriterion("END_SN is not null");
            return (Criteria) this;
        }

        public Criteria andEndSnEqualTo(String value) {
            addCriterion("END_SN =", value, "endSn");
            return (Criteria) this;
        }

        public Criteria andEndSnNotEqualTo(String value) {
            addCriterion("END_SN <>", value, "endSn");
            return (Criteria) this;
        }

        public Criteria andEndSnGreaterThan(String value) {
            addCriterion("END_SN >", value, "endSn");
            return (Criteria) this;
        }

        public Criteria andEndSnGreaterThanOrEqualTo(String value) {
            addCriterion("END_SN >=", value, "endSn");
            return (Criteria) this;
        }

        public Criteria andEndSnLessThan(String value) {
            addCriterion("END_SN <", value, "endSn");
            return (Criteria) this;
        }

        public Criteria andEndSnLessThanOrEqualTo(String value) {
            addCriterion("END_SN <=", value, "endSn");
            return (Criteria) this;
        }

        public Criteria andEndSnLike(String value) {
            addCriterion("END_SN like", value, "endSn");
            return (Criteria) this;
        }

        public Criteria andEndSnNotLike(String value) {
            addCriterion("END_SN not like", value, "endSn");
            return (Criteria) this;
        }

        public Criteria andEndSnIn(List<String> values) {
            addCriterion("END_SN in", values, "endSn");
            return (Criteria) this;
        }

        public Criteria andEndSnNotIn(List<String> values) {
            addCriterion("END_SN not in", values, "endSn");
            return (Criteria) this;
        }

        public Criteria andEndSnBetween(String value1, String value2) {
            addCriterion("END_SN between", value1, value2, "endSn");
            return (Criteria) this;
        }

        public Criteria andEndSnNotBetween(String value1, String value2) {
            addCriterion("END_SN not between", value1, value2, "endSn");
            return (Criteria) this;
        }

        public Criteria andVenderIsNull() {
            addCriterion("VENDER is null");
            return (Criteria) this;
        }

        public Criteria andVenderIsNotNull() {
            addCriterion("VENDER is not null");
            return (Criteria) this;
        }

        public Criteria andVenderEqualTo(String value) {
            addCriterion("VENDER =", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderNotEqualTo(String value) {
            addCriterion("VENDER <>", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderGreaterThan(String value) {
            addCriterion("VENDER >", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderGreaterThanOrEqualTo(String value) {
            addCriterion("VENDER >=", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderLessThan(String value) {
            addCriterion("VENDER <", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderLessThanOrEqualTo(String value) {
            addCriterion("VENDER <=", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderLike(String value) {
            addCriterion("VENDER like", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderNotLike(String value) {
            addCriterion("VENDER not like", value, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderIn(List<String> values) {
            addCriterion("VENDER in", values, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderNotIn(List<String> values) {
            addCriterion("VENDER not in", values, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderBetween(String value1, String value2) {
            addCriterion("VENDER between", value1, value2, "vender");
            return (Criteria) this;
        }

        public Criteria andVenderNotBetween(String value1, String value2) {
            addCriterion("VENDER not between", value1, value2, "vender");
            return (Criteria) this;
        }

        public Criteria andProModelIsNull() {
            addCriterion("PRO_MODEL is null");
            return (Criteria) this;
        }

        public Criteria andProModelIsNotNull() {
            addCriterion("PRO_MODEL is not null");
            return (Criteria) this;
        }

        public Criteria andProModelEqualTo(String value) {
            addCriterion("PRO_MODEL =", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelNotEqualTo(String value) {
            addCriterion("PRO_MODEL <>", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelGreaterThan(String value) {
            addCriterion("PRO_MODEL >", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelGreaterThanOrEqualTo(String value) {
            addCriterion("PRO_MODEL >=", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelLessThan(String value) {
            addCriterion("PRO_MODEL <", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelLessThanOrEqualTo(String value) {
            addCriterion("PRO_MODEL <=", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelLike(String value) {
            addCriterion("PRO_MODEL like", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelNotLike(String value) {
            addCriterion("PRO_MODEL not like", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelIn(List<String> values) {
            addCriterion("PRO_MODEL in", values, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelNotIn(List<String> values) {
            addCriterion("PRO_MODEL not in", values, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelBetween(String value1, String value2) {
            addCriterion("PRO_MODEL between", value1, value2, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelNotBetween(String value1, String value2) {
            addCriterion("PRO_MODEL not between", value1, value2, "proModel");
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