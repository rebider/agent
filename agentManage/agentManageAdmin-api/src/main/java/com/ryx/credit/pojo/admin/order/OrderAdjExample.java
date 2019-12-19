package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderAdjExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OrderAdjExample() {
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

        public Criteria andAdjUserIdIsNull() {
            addCriterion("ADJ_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andAdjUserIdIsNotNull() {
            addCriterion("ADJ_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAdjUserIdEqualTo(String value) {
            addCriterion("ADJ_USER_ID =", value, "adjUserId");
            return (Criteria) this;
        }

        public Criteria andAdjUserIdNotEqualTo(String value) {
            addCriterion("ADJ_USER_ID <>", value, "adjUserId");
            return (Criteria) this;
        }

        public Criteria andAdjUserIdGreaterThan(String value) {
            addCriterion("ADJ_USER_ID >", value, "adjUserId");
            return (Criteria) this;
        }

        public Criteria andAdjUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("ADJ_USER_ID >=", value, "adjUserId");
            return (Criteria) this;
        }

        public Criteria andAdjUserIdLessThan(String value) {
            addCriterion("ADJ_USER_ID <", value, "adjUserId");
            return (Criteria) this;
        }

        public Criteria andAdjUserIdLessThanOrEqualTo(String value) {
            addCriterion("ADJ_USER_ID <=", value, "adjUserId");
            return (Criteria) this;
        }

        public Criteria andAdjUserIdLike(String value) {
            addCriterion("ADJ_USER_ID like", value, "adjUserId");
            return (Criteria) this;
        }

        public Criteria andAdjUserIdNotLike(String value) {
            addCriterion("ADJ_USER_ID not like", value, "adjUserId");
            return (Criteria) this;
        }

        public Criteria andAdjUserIdIn(List<String> values) {
            addCriterion("ADJ_USER_ID in", values, "adjUserId");
            return (Criteria) this;
        }

        public Criteria andAdjUserIdNotIn(List<String> values) {
            addCriterion("ADJ_USER_ID not in", values, "adjUserId");
            return (Criteria) this;
        }

        public Criteria andAdjUserIdBetween(String value1, String value2) {
            addCriterion("ADJ_USER_ID between", value1, value2, "adjUserId");
            return (Criteria) this;
        }

        public Criteria andAdjUserIdNotBetween(String value1, String value2) {
            addCriterion("ADJ_USER_ID not between", value1, value2, "adjUserId");
            return (Criteria) this;
        }

        public Criteria andOrgOAmoIsNull() {
            addCriterion("ORG_O_AMO is null");
            return (Criteria) this;
        }

        public Criteria andOrgOAmoIsNotNull() {
            addCriterion("ORG_O_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andOrgOAmoEqualTo(BigDecimal value) {
            addCriterion("ORG_O_AMO =", value, "orgOAmo");
            return (Criteria) this;
        }

        public Criteria andOrgOAmoNotEqualTo(BigDecimal value) {
            addCriterion("ORG_O_AMO <>", value, "orgOAmo");
            return (Criteria) this;
        }

        public Criteria andOrgOAmoGreaterThan(BigDecimal value) {
            addCriterion("ORG_O_AMO >", value, "orgOAmo");
            return (Criteria) this;
        }

        public Criteria andOrgOAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORG_O_AMO >=", value, "orgOAmo");
            return (Criteria) this;
        }

        public Criteria andOrgOAmoLessThan(BigDecimal value) {
            addCriterion("ORG_O_AMO <", value, "orgOAmo");
            return (Criteria) this;
        }

        public Criteria andOrgOAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORG_O_AMO <=", value, "orgOAmo");
            return (Criteria) this;
        }

        public Criteria andOrgOAmoIn(List<BigDecimal> values) {
            addCriterion("ORG_O_AMO in", values, "orgOAmo");
            return (Criteria) this;
        }

        public Criteria andOrgOAmoNotIn(List<BigDecimal> values) {
            addCriterion("ORG_O_AMO not in", values, "orgOAmo");
            return (Criteria) this;
        }

        public Criteria andOrgOAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORG_O_AMO between", value1, value2, "orgOAmo");
            return (Criteria) this;
        }

        public Criteria andOrgOAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORG_O_AMO not between", value1, value2, "orgOAmo");
            return (Criteria) this;
        }

        public Criteria andOrgIncentiveAmoIsNull() {
            addCriterion("ORG_INCENTIVE_AMO is null");
            return (Criteria) this;
        }

        public Criteria andOrgIncentiveAmoIsNotNull() {
            addCriterion("ORG_INCENTIVE_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andOrgIncentiveAmoEqualTo(BigDecimal value) {
            addCriterion("ORG_INCENTIVE_AMO =", value, "orgIncentiveAmo");
            return (Criteria) this;
        }

        public Criteria andOrgIncentiveAmoNotEqualTo(BigDecimal value) {
            addCriterion("ORG_INCENTIVE_AMO <>", value, "orgIncentiveAmo");
            return (Criteria) this;
        }

        public Criteria andOrgIncentiveAmoGreaterThan(BigDecimal value) {
            addCriterion("ORG_INCENTIVE_AMO >", value, "orgIncentiveAmo");
            return (Criteria) this;
        }

        public Criteria andOrgIncentiveAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORG_INCENTIVE_AMO >=", value, "orgIncentiveAmo");
            return (Criteria) this;
        }

        public Criteria andOrgIncentiveAmoLessThan(BigDecimal value) {
            addCriterion("ORG_INCENTIVE_AMO <", value, "orgIncentiveAmo");
            return (Criteria) this;
        }

        public Criteria andOrgIncentiveAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORG_INCENTIVE_AMO <=", value, "orgIncentiveAmo");
            return (Criteria) this;
        }

        public Criteria andOrgIncentiveAmoIn(List<BigDecimal> values) {
            addCriterion("ORG_INCENTIVE_AMO in", values, "orgIncentiveAmo");
            return (Criteria) this;
        }

        public Criteria andOrgIncentiveAmoNotIn(List<BigDecimal> values) {
            addCriterion("ORG_INCENTIVE_AMO not in", values, "orgIncentiveAmo");
            return (Criteria) this;
        }

        public Criteria andOrgIncentiveAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORG_INCENTIVE_AMO between", value1, value2, "orgIncentiveAmo");
            return (Criteria) this;
        }

        public Criteria andOrgIncentiveAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORG_INCENTIVE_AMO not between", value1, value2, "orgIncentiveAmo");
            return (Criteria) this;
        }

        public Criteria andOrgPayAmoIsNull() {
            addCriterion("ORG_PAY_AMO is null");
            return (Criteria) this;
        }

        public Criteria andOrgPayAmoIsNotNull() {
            addCriterion("ORG_PAY_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andOrgPayAmoEqualTo(BigDecimal value) {
            addCriterion("ORG_PAY_AMO =", value, "orgPayAmo");
            return (Criteria) this;
        }

        public Criteria andOrgPayAmoNotEqualTo(BigDecimal value) {
            addCriterion("ORG_PAY_AMO <>", value, "orgPayAmo");
            return (Criteria) this;
        }

        public Criteria andOrgPayAmoGreaterThan(BigDecimal value) {
            addCriterion("ORG_PAY_AMO >", value, "orgPayAmo");
            return (Criteria) this;
        }

        public Criteria andOrgPayAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORG_PAY_AMO >=", value, "orgPayAmo");
            return (Criteria) this;
        }

        public Criteria andOrgPayAmoLessThan(BigDecimal value) {
            addCriterion("ORG_PAY_AMO <", value, "orgPayAmo");
            return (Criteria) this;
        }

        public Criteria andOrgPayAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORG_PAY_AMO <=", value, "orgPayAmo");
            return (Criteria) this;
        }

        public Criteria andOrgPayAmoIn(List<BigDecimal> values) {
            addCriterion("ORG_PAY_AMO in", values, "orgPayAmo");
            return (Criteria) this;
        }

        public Criteria andOrgPayAmoNotIn(List<BigDecimal> values) {
            addCriterion("ORG_PAY_AMO not in", values, "orgPayAmo");
            return (Criteria) this;
        }

        public Criteria andOrgPayAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORG_PAY_AMO between", value1, value2, "orgPayAmo");
            return (Criteria) this;
        }

        public Criteria andOrgPayAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORG_PAY_AMO not between", value1, value2, "orgPayAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoIsNull() {
            addCriterion("O_AMO is null");
            return (Criteria) this;
        }

        public Criteria andOAmoIsNotNull() {
            addCriterion("O_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andOAmoEqualTo(BigDecimal value) {
            addCriterion("O_AMO =", value, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoNotEqualTo(BigDecimal value) {
            addCriterion("O_AMO <>", value, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoGreaterThan(BigDecimal value) {
            addCriterion("O_AMO >", value, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("O_AMO >=", value, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoLessThan(BigDecimal value) {
            addCriterion("O_AMO <", value, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("O_AMO <=", value, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoIn(List<BigDecimal> values) {
            addCriterion("O_AMO in", values, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoNotIn(List<BigDecimal> values) {
            addCriterion("O_AMO not in", values, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("O_AMO between", value1, value2, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("O_AMO not between", value1, value2, "oAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoIsNull() {
            addCriterion("INCENTIVE_AMO is null");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoIsNotNull() {
            addCriterion("INCENTIVE_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoEqualTo(BigDecimal value) {
            addCriterion("INCENTIVE_AMO =", value, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoNotEqualTo(BigDecimal value) {
            addCriterion("INCENTIVE_AMO <>", value, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoGreaterThan(BigDecimal value) {
            addCriterion("INCENTIVE_AMO >", value, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("INCENTIVE_AMO >=", value, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoLessThan(BigDecimal value) {
            addCriterion("INCENTIVE_AMO <", value, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("INCENTIVE_AMO <=", value, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoIn(List<BigDecimal> values) {
            addCriterion("INCENTIVE_AMO in", values, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoNotIn(List<BigDecimal> values) {
            addCriterion("INCENTIVE_AMO not in", values, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("INCENTIVE_AMO between", value1, value2, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("INCENTIVE_AMO not between", value1, value2, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoIsNull() {
            addCriterion("PAY_AMO is null");
            return (Criteria) this;
        }

        public Criteria andPayAmoIsNotNull() {
            addCriterion("PAY_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andPayAmoEqualTo(BigDecimal value) {
            addCriterion("PAY_AMO =", value, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoNotEqualTo(BigDecimal value) {
            addCriterion("PAY_AMO <>", value, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoGreaterThan(BigDecimal value) {
            addCriterion("PAY_AMO >", value, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_AMO >=", value, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoLessThan(BigDecimal value) {
            addCriterion("PAY_AMO <", value, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_AMO <=", value, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoIn(List<BigDecimal> values) {
            addCriterion("PAY_AMO in", values, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoNotIn(List<BigDecimal> values) {
            addCriterion("PAY_AMO not in", values, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_AMO between", value1, value2, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_AMO not between", value1, value2, "payAmo");
            return (Criteria) this;
        }

        public Criteria andResonIsNull() {
            addCriterion("RESON is null");
            return (Criteria) this;
        }

        public Criteria andResonIsNotNull() {
            addCriterion("RESON is not null");
            return (Criteria) this;
        }

        public Criteria andResonEqualTo(String value) {
            addCriterion("RESON =", value, "reson");
            return (Criteria) this;
        }

        public Criteria andResonNotEqualTo(String value) {
            addCriterion("RESON <>", value, "reson");
            return (Criteria) this;
        }

        public Criteria andResonGreaterThan(String value) {
            addCriterion("RESON >", value, "reson");
            return (Criteria) this;
        }

        public Criteria andResonGreaterThanOrEqualTo(String value) {
            addCriterion("RESON >=", value, "reson");
            return (Criteria) this;
        }

        public Criteria andResonLessThan(String value) {
            addCriterion("RESON <", value, "reson");
            return (Criteria) this;
        }

        public Criteria andResonLessThanOrEqualTo(String value) {
            addCriterion("RESON <=", value, "reson");
            return (Criteria) this;
        }

        public Criteria andResonLike(String value) {
            addCriterion("RESON like", value, "reson");
            return (Criteria) this;
        }

        public Criteria andResonNotLike(String value) {
            addCriterion("RESON not like", value, "reson");
            return (Criteria) this;
        }

        public Criteria andResonIn(List<String> values) {
            addCriterion("RESON in", values, "reson");
            return (Criteria) this;
        }

        public Criteria andResonNotIn(List<String> values) {
            addCriterion("RESON not in", values, "reson");
            return (Criteria) this;
        }

        public Criteria andResonBetween(String value1, String value2) {
            addCriterion("RESON between", value1, value2, "reson");
            return (Criteria) this;
        }

        public Criteria andResonNotBetween(String value1, String value2) {
            addCriterion("RESON not between", value1, value2, "reson");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIsNull() {
            addCriterion("REFUND_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIsNotNull() {
            addCriterion("REFUND_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andRefundAmountEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMOUNT =", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMOUNT <>", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountGreaterThan(BigDecimal value) {
            addCriterion("REFUND_AMOUNT >", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMOUNT >=", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountLessThan(BigDecimal value) {
            addCriterion("REFUND_AMOUNT <", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMOUNT <=", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIn(List<BigDecimal> values) {
            addCriterion("REFUND_AMOUNT in", values, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotIn(List<BigDecimal> values) {
            addCriterion("REFUND_AMOUNT not in", values, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_AMOUNT between", value1, value2, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_AMOUNT not between", value1, value2, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundTypeIsNull() {
            addCriterion("REFUND_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andRefundTypeIsNotNull() {
            addCriterion("REFUND_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andRefundTypeEqualTo(BigDecimal value) {
            addCriterion("REFUND_TYPE =", value, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeNotEqualTo(BigDecimal value) {
            addCriterion("REFUND_TYPE <>", value, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeGreaterThan(BigDecimal value) {
            addCriterion("REFUND_TYPE >", value, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_TYPE >=", value, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeLessThan(BigDecimal value) {
            addCriterion("REFUND_TYPE <", value, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_TYPE <=", value, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeIn(List<BigDecimal> values) {
            addCriterion("REFUND_TYPE in", values, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeNotIn(List<BigDecimal> values) {
            addCriterion("REFUND_TYPE not in", values, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_TYPE between", value1, value2, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_TYPE not between", value1, value2, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundStatIsNull() {
            addCriterion("REFUND_STAT is null");
            return (Criteria) this;
        }

        public Criteria andRefundStatIsNotNull() {
            addCriterion("REFUND_STAT is not null");
            return (Criteria) this;
        }

        public Criteria andRefundStatEqualTo(BigDecimal value) {
            addCriterion("REFUND_STAT =", value, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatNotEqualTo(BigDecimal value) {
            addCriterion("REFUND_STAT <>", value, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatGreaterThan(BigDecimal value) {
            addCriterion("REFUND_STAT >", value, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_STAT >=", value, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatLessThan(BigDecimal value) {
            addCriterion("REFUND_STAT <", value, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_STAT <=", value, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatIn(List<BigDecimal> values) {
            addCriterion("REFUND_STAT in", values, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatNotIn(List<BigDecimal> values) {
            addCriterion("REFUND_STAT not in", values, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_STAT between", value1, value2, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_STAT not between", value1, value2, "refundStat");
            return (Criteria) this;
        }

        public Criteria andOrgPaymentIdIsNull() {
            addCriterion("ORG_PAYMENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrgPaymentIdIsNotNull() {
            addCriterion("ORG_PAYMENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrgPaymentIdEqualTo(String value) {
            addCriterion("ORG_PAYMENT_ID =", value, "orgPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrgPaymentIdNotEqualTo(String value) {
            addCriterion("ORG_PAYMENT_ID <>", value, "orgPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrgPaymentIdGreaterThan(String value) {
            addCriterion("ORG_PAYMENT_ID >", value, "orgPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrgPaymentIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_PAYMENT_ID >=", value, "orgPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrgPaymentIdLessThan(String value) {
            addCriterion("ORG_PAYMENT_ID <", value, "orgPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrgPaymentIdLessThanOrEqualTo(String value) {
            addCriterion("ORG_PAYMENT_ID <=", value, "orgPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrgPaymentIdLike(String value) {
            addCriterion("ORG_PAYMENT_ID like", value, "orgPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrgPaymentIdNotLike(String value) {
            addCriterion("ORG_PAYMENT_ID not like", value, "orgPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrgPaymentIdIn(List<String> values) {
            addCriterion("ORG_PAYMENT_ID in", values, "orgPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrgPaymentIdNotIn(List<String> values) {
            addCriterion("ORG_PAYMENT_ID not in", values, "orgPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrgPaymentIdBetween(String value1, String value2) {
            addCriterion("ORG_PAYMENT_ID between", value1, value2, "orgPaymentId");
            return (Criteria) this;
        }

        public Criteria andOrgPaymentIdNotBetween(String value1, String value2) {
            addCriterion("ORG_PAYMENT_ID not between", value1, value2, "orgPaymentId");
            return (Criteria) this;
        }

        public Criteria andNewPaymentIdIsNull() {
            addCriterion("NEW_PAYMENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andNewPaymentIdIsNotNull() {
            addCriterion("NEW_PAYMENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andNewPaymentIdEqualTo(String value) {
            addCriterion("NEW_PAYMENT_ID =", value, "newPaymentId");
            return (Criteria) this;
        }

        public Criteria andNewPaymentIdNotEqualTo(String value) {
            addCriterion("NEW_PAYMENT_ID <>", value, "newPaymentId");
            return (Criteria) this;
        }

        public Criteria andNewPaymentIdGreaterThan(String value) {
            addCriterion("NEW_PAYMENT_ID >", value, "newPaymentId");
            return (Criteria) this;
        }

        public Criteria andNewPaymentIdGreaterThanOrEqualTo(String value) {
            addCriterion("NEW_PAYMENT_ID >=", value, "newPaymentId");
            return (Criteria) this;
        }

        public Criteria andNewPaymentIdLessThan(String value) {
            addCriterion("NEW_PAYMENT_ID <", value, "newPaymentId");
            return (Criteria) this;
        }

        public Criteria andNewPaymentIdLessThanOrEqualTo(String value) {
            addCriterion("NEW_PAYMENT_ID <=", value, "newPaymentId");
            return (Criteria) this;
        }

        public Criteria andNewPaymentIdLike(String value) {
            addCriterion("NEW_PAYMENT_ID like", value, "newPaymentId");
            return (Criteria) this;
        }

        public Criteria andNewPaymentIdNotLike(String value) {
            addCriterion("NEW_PAYMENT_ID not like", value, "newPaymentId");
            return (Criteria) this;
        }

        public Criteria andNewPaymentIdIn(List<String> values) {
            addCriterion("NEW_PAYMENT_ID in", values, "newPaymentId");
            return (Criteria) this;
        }

        public Criteria andNewPaymentIdNotIn(List<String> values) {
            addCriterion("NEW_PAYMENT_ID not in", values, "newPaymentId");
            return (Criteria) this;
        }

        public Criteria andNewPaymentIdBetween(String value1, String value2) {
            addCriterion("NEW_PAYMENT_ID between", value1, value2, "newPaymentId");
            return (Criteria) this;
        }

        public Criteria andNewPaymentIdNotBetween(String value1, String value2) {
            addCriterion("NEW_PAYMENT_ID not between", value1, value2, "newPaymentId");
            return (Criteria) this;
        }

        public Criteria andReviewsStatIsNull() {
            addCriterion("REVIEWS_STAT is null");
            return (Criteria) this;
        }

        public Criteria andReviewsStatIsNotNull() {
            addCriterion("REVIEWS_STAT is not null");
            return (Criteria) this;
        }

        public Criteria andReviewsStatEqualTo(BigDecimal value) {
            addCriterion("REVIEWS_STAT =", value, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatNotEqualTo(BigDecimal value) {
            addCriterion("REVIEWS_STAT <>", value, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatGreaterThan(BigDecimal value) {
            addCriterion("REVIEWS_STAT >", value, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REVIEWS_STAT >=", value, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatLessThan(BigDecimal value) {
            addCriterion("REVIEWS_STAT <", value, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REVIEWS_STAT <=", value, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatIn(List<BigDecimal> values) {
            addCriterion("REVIEWS_STAT in", values, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatNotIn(List<BigDecimal> values) {
            addCriterion("REVIEWS_STAT not in", values, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REVIEWS_STAT between", value1, value2, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REVIEWS_STAT not between", value1, value2, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsDateIsNull() {
            addCriterion("REVIEWS_DATE is null");
            return (Criteria) this;
        }

        public Criteria andReviewsDateIsNotNull() {
            addCriterion("REVIEWS_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andReviewsDateEqualTo(Date value) {
            addCriterion("REVIEWS_DATE =", value, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateNotEqualTo(Date value) {
            addCriterion("REVIEWS_DATE <>", value, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateGreaterThan(Date value) {
            addCriterion("REVIEWS_DATE >", value, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateGreaterThanOrEqualTo(Date value) {
            addCriterion("REVIEWS_DATE >=", value, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateLessThan(Date value) {
            addCriterion("REVIEWS_DATE <", value, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateLessThanOrEqualTo(Date value) {
            addCriterion("REVIEWS_DATE <=", value, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateIn(List<Date> values) {
            addCriterion("REVIEWS_DATE in", values, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateNotIn(List<Date> values) {
            addCriterion("REVIEWS_DATE not in", values, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateBetween(Date value1, Date value2) {
            addCriterion("REVIEWS_DATE between", value1, value2, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateNotBetween(Date value1, Date value2) {
            addCriterion("REVIEWS_DATE not between", value1, value2, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andAdjTmIsNull() {
            addCriterion("ADJ_TM is null");
            return (Criteria) this;
        }

        public Criteria andAdjTmIsNotNull() {
            addCriterion("ADJ_TM is not null");
            return (Criteria) this;
        }

        public Criteria andAdjTmEqualTo(Date value) {
            addCriterion("ADJ_TM =", value, "adjTm");
            return (Criteria) this;
        }

        public Criteria andAdjTmNotEqualTo(Date value) {
            addCriterion("ADJ_TM <>", value, "adjTm");
            return (Criteria) this;
        }

        public Criteria andAdjTmGreaterThan(Date value) {
            addCriterion("ADJ_TM >", value, "adjTm");
            return (Criteria) this;
        }

        public Criteria andAdjTmGreaterThanOrEqualTo(Date value) {
            addCriterion("ADJ_TM >=", value, "adjTm");
            return (Criteria) this;
        }

        public Criteria andAdjTmLessThan(Date value) {
            addCriterion("ADJ_TM <", value, "adjTm");
            return (Criteria) this;
        }

        public Criteria andAdjTmLessThanOrEqualTo(Date value) {
            addCriterion("ADJ_TM <=", value, "adjTm");
            return (Criteria) this;
        }

        public Criteria andAdjTmIn(List<Date> values) {
            addCriterion("ADJ_TM in", values, "adjTm");
            return (Criteria) this;
        }

        public Criteria andAdjTmNotIn(List<Date> values) {
            addCriterion("ADJ_TM not in", values, "adjTm");
            return (Criteria) this;
        }

        public Criteria andAdjTmBetween(Date value1, Date value2) {
            addCriterion("ADJ_TM between", value1, value2, "adjTm");
            return (Criteria) this;
        }

        public Criteria andAdjTmNotBetween(Date value1, Date value2) {
            addCriterion("ADJ_TM not between", value1, value2, "adjTm");
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

        public Criteria andOrgPlanNumIsNull() {
            addCriterion("ORG_PLAN_NUM is null");
            return (Criteria) this;
        }

        public Criteria andOrgPlanNumIsNotNull() {
            addCriterion("ORG_PLAN_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andOrgPlanNumEqualTo(BigDecimal value) {
            addCriterion("ORG_PLAN_NUM =", value, "orgPlanNum");
            return (Criteria) this;
        }

        public Criteria andOrgPlanNumNotEqualTo(BigDecimal value) {
            addCriterion("ORG_PLAN_NUM <>", value, "orgPlanNum");
            return (Criteria) this;
        }

        public Criteria andOrgPlanNumGreaterThan(BigDecimal value) {
            addCriterion("ORG_PLAN_NUM >", value, "orgPlanNum");
            return (Criteria) this;
        }

        public Criteria andOrgPlanNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORG_PLAN_NUM >=", value, "orgPlanNum");
            return (Criteria) this;
        }

        public Criteria andOrgPlanNumLessThan(BigDecimal value) {
            addCriterion("ORG_PLAN_NUM <", value, "orgPlanNum");
            return (Criteria) this;
        }

        public Criteria andOrgPlanNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORG_PLAN_NUM <=", value, "orgPlanNum");
            return (Criteria) this;
        }

        public Criteria andOrgPlanNumIn(List<BigDecimal> values) {
            addCriterion("ORG_PLAN_NUM in", values, "orgPlanNum");
            return (Criteria) this;
        }

        public Criteria andOrgPlanNumNotIn(List<BigDecimal> values) {
            addCriterion("ORG_PLAN_NUM not in", values, "orgPlanNum");
            return (Criteria) this;
        }

        public Criteria andOrgPlanNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORG_PLAN_NUM between", value1, value2, "orgPlanNum");
            return (Criteria) this;
        }

        public Criteria andOrgPlanNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORG_PLAN_NUM not between", value1, value2, "orgPlanNum");
            return (Criteria) this;
        }

        public Criteria andStagesAmountIsNull() {
            addCriterion("STAGES_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andStagesAmountIsNotNull() {
            addCriterion("STAGES_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andStagesAmountEqualTo(BigDecimal value) {
            addCriterion("STAGES_AMOUNT =", value, "stagesAmount");
            return (Criteria) this;
        }

        public Criteria andStagesAmountNotEqualTo(BigDecimal value) {
            addCriterion("STAGES_AMOUNT <>", value, "stagesAmount");
            return (Criteria) this;
        }

        public Criteria andStagesAmountGreaterThan(BigDecimal value) {
            addCriterion("STAGES_AMOUNT >", value, "stagesAmount");
            return (Criteria) this;
        }

        public Criteria andStagesAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STAGES_AMOUNT >=", value, "stagesAmount");
            return (Criteria) this;
        }

        public Criteria andStagesAmountLessThan(BigDecimal value) {
            addCriterion("STAGES_AMOUNT <", value, "stagesAmount");
            return (Criteria) this;
        }

        public Criteria andStagesAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STAGES_AMOUNT <=", value, "stagesAmount");
            return (Criteria) this;
        }

        public Criteria andStagesAmountIn(List<BigDecimal> values) {
            addCriterion("STAGES_AMOUNT in", values, "stagesAmount");
            return (Criteria) this;
        }

        public Criteria andStagesAmountNotIn(List<BigDecimal> values) {
            addCriterion("STAGES_AMOUNT not in", values, "stagesAmount");
            return (Criteria) this;
        }

        public Criteria andStagesAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STAGES_AMOUNT between", value1, value2, "stagesAmount");
            return (Criteria) this;
        }

        public Criteria andStagesAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STAGES_AMOUNT not between", value1, value2, "stagesAmount");
            return (Criteria) this;
        }

        public Criteria andRefundTmIsNull() {
            addCriterion("REFUND_TM is null");
            return (Criteria) this;
        }

        public Criteria andRefundTmIsNotNull() {
            addCriterion("REFUND_TM is not null");
            return (Criteria) this;
        }

        public Criteria andRefundTmEqualTo(Date value) {
            addCriterion("REFUND_TM =", value, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmNotEqualTo(Date value) {
            addCriterion("REFUND_TM <>", value, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmGreaterThan(Date value) {
            addCriterion("REFUND_TM >", value, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmGreaterThanOrEqualTo(Date value) {
            addCriterion("REFUND_TM >=", value, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmLessThan(Date value) {
            addCriterion("REFUND_TM <", value, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmLessThanOrEqualTo(Date value) {
            addCriterion("REFUND_TM <=", value, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmIn(List<Date> values) {
            addCriterion("REFUND_TM in", values, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmNotIn(List<Date> values) {
            addCriterion("REFUND_TM not in", values, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmBetween(Date value1, Date value2) {
            addCriterion("REFUND_TM between", value1, value2, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmNotBetween(Date value1, Date value2) {
            addCriterion("REFUND_TM not between", value1, value2, "refundTm");
            return (Criteria) this;
        }

        public Criteria andSettleAmountIsNull() {
            addCriterion("SETTLE_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andSettleAmountIsNotNull() {
            addCriterion("SETTLE_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andSettleAmountEqualTo(BigDecimal value) {
            addCriterion("SETTLE_AMOUNT =", value, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountNotEqualTo(BigDecimal value) {
            addCriterion("SETTLE_AMOUNT <>", value, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountGreaterThan(BigDecimal value) {
            addCriterion("SETTLE_AMOUNT >", value, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SETTLE_AMOUNT >=", value, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountLessThan(BigDecimal value) {
            addCriterion("SETTLE_AMOUNT <", value, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SETTLE_AMOUNT <=", value, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountIn(List<BigDecimal> values) {
            addCriterion("SETTLE_AMOUNT in", values, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountNotIn(List<BigDecimal> values) {
            addCriterion("SETTLE_AMOUNT not in", values, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SETTLE_AMOUNT between", value1, value2, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andSettleAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SETTLE_AMOUNT not between", value1, value2, "settleAmount");
            return (Criteria) this;
        }

        public Criteria andRealRefundAmoIsNull() {
            addCriterion("REAL_REFUND_AMO is null");
            return (Criteria) this;
        }

        public Criteria andRealRefundAmoIsNotNull() {
            addCriterion("REAL_REFUND_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andRealRefundAmoEqualTo(BigDecimal value) {
            addCriterion("REAL_REFUND_AMO =", value, "realRefundAmo");
            return (Criteria) this;
        }

        public Criteria andRealRefundAmoNotEqualTo(BigDecimal value) {
            addCriterion("REAL_REFUND_AMO <>", value, "realRefundAmo");
            return (Criteria) this;
        }

        public Criteria andRealRefundAmoGreaterThan(BigDecimal value) {
            addCriterion("REAL_REFUND_AMO >", value, "realRefundAmo");
            return (Criteria) this;
        }

        public Criteria andRealRefundAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_REFUND_AMO >=", value, "realRefundAmo");
            return (Criteria) this;
        }

        public Criteria andRealRefundAmoLessThan(BigDecimal value) {
            addCriterion("REAL_REFUND_AMO <", value, "realRefundAmo");
            return (Criteria) this;
        }

        public Criteria andRealRefundAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_REFUND_AMO <=", value, "realRefundAmo");
            return (Criteria) this;
        }

        public Criteria andRealRefundAmoIn(List<BigDecimal> values) {
            addCriterion("REAL_REFUND_AMO in", values, "realRefundAmo");
            return (Criteria) this;
        }

        public Criteria andRealRefundAmoNotIn(List<BigDecimal> values) {
            addCriterion("REAL_REFUND_AMO not in", values, "realRefundAmo");
            return (Criteria) this;
        }

        public Criteria andRealRefundAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_REFUND_AMO between", value1, value2, "realRefundAmo");
            return (Criteria) this;
        }

        public Criteria andRealRefundAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_REFUND_AMO not between", value1, value2, "realRefundAmo");
            return (Criteria) this;
        }

        public Criteria andRefundMethodIsNull() {
            addCriterion("REFUND_METHOD is null");
            return (Criteria) this;
        }

        public Criteria andRefundMethodIsNotNull() {
            addCriterion("REFUND_METHOD is not null");
            return (Criteria) this;
        }

        public Criteria andRefundMethodEqualTo(BigDecimal value) {
            addCriterion("REFUND_METHOD =", value, "refundMethod");
            return (Criteria) this;
        }

        public Criteria andRefundMethodNotEqualTo(BigDecimal value) {
            addCriterion("REFUND_METHOD <>", value, "refundMethod");
            return (Criteria) this;
        }

        public Criteria andRefundMethodGreaterThan(BigDecimal value) {
            addCriterion("REFUND_METHOD >", value, "refundMethod");
            return (Criteria) this;
        }

        public Criteria andRefundMethodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_METHOD >=", value, "refundMethod");
            return (Criteria) this;
        }

        public Criteria andRefundMethodLessThan(BigDecimal value) {
            addCriterion("REFUND_METHOD <", value, "refundMethod");
            return (Criteria) this;
        }

        public Criteria andRefundMethodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_METHOD <=", value, "refundMethod");
            return (Criteria) this;
        }

        public Criteria andRefundMethodIn(List<BigDecimal> values) {
            addCriterion("REFUND_METHOD in", values, "refundMethod");
            return (Criteria) this;
        }

        public Criteria andRefundMethodNotIn(List<BigDecimal> values) {
            addCriterion("REFUND_METHOD not in", values, "refundMethod");
            return (Criteria) this;
        }

        public Criteria andRefundMethodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_METHOD between", value1, value2, "refundMethod");
            return (Criteria) this;
        }

        public Criteria andRefundMethodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_METHOD not between", value1, value2, "refundMethod");
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