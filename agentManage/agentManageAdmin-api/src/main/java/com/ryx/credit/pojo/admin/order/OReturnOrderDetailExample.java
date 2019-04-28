package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OReturnOrderDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OReturnOrderDetailExample() {
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

        public Criteria andReturnIdIsNull() {
            addCriterion("RETURN_ID is null");
            return (Criteria) this;
        }

        public Criteria andReturnIdIsNotNull() {
            addCriterion("RETURN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andReturnIdEqualTo(String value) {
            addCriterion("RETURN_ID =", value, "returnId");
            return (Criteria) this;
        }

        public Criteria andReturnIdNotEqualTo(String value) {
            addCriterion("RETURN_ID <>", value, "returnId");
            return (Criteria) this;
        }

        public Criteria andReturnIdGreaterThan(String value) {
            addCriterion("RETURN_ID >", value, "returnId");
            return (Criteria) this;
        }

        public Criteria andReturnIdGreaterThanOrEqualTo(String value) {
            addCriterion("RETURN_ID >=", value, "returnId");
            return (Criteria) this;
        }

        public Criteria andReturnIdLessThan(String value) {
            addCriterion("RETURN_ID <", value, "returnId");
            return (Criteria) this;
        }

        public Criteria andReturnIdLessThanOrEqualTo(String value) {
            addCriterion("RETURN_ID <=", value, "returnId");
            return (Criteria) this;
        }

        public Criteria andReturnIdLike(String value) {
            addCriterion("RETURN_ID like", value, "returnId");
            return (Criteria) this;
        }

        public Criteria andReturnIdNotLike(String value) {
            addCriterion("RETURN_ID not like", value, "returnId");
            return (Criteria) this;
        }

        public Criteria andReturnIdIn(List<String> values) {
            addCriterion("RETURN_ID in", values, "returnId");
            return (Criteria) this;
        }

        public Criteria andReturnIdNotIn(List<String> values) {
            addCriterion("RETURN_ID not in", values, "returnId");
            return (Criteria) this;
        }

        public Criteria andReturnIdBetween(String value1, String value2) {
            addCriterion("RETURN_ID between", value1, value2, "returnId");
            return (Criteria) this;
        }

        public Criteria andReturnIdNotBetween(String value1, String value2) {
            addCriterion("RETURN_ID not between", value1, value2, "returnId");
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

        public Criteria andProCodeIsNull() {
            addCriterion("PRO_CODE is null");
            return (Criteria) this;
        }

        public Criteria andProCodeIsNotNull() {
            addCriterion("PRO_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andProCodeEqualTo(String value) {
            addCriterion("PRO_CODE =", value, "proCode");
            return (Criteria) this;
        }

        public Criteria andProCodeNotEqualTo(String value) {
            addCriterion("PRO_CODE <>", value, "proCode");
            return (Criteria) this;
        }

        public Criteria andProCodeGreaterThan(String value) {
            addCriterion("PRO_CODE >", value, "proCode");
            return (Criteria) this;
        }

        public Criteria andProCodeGreaterThanOrEqualTo(String value) {
            addCriterion("PRO_CODE >=", value, "proCode");
            return (Criteria) this;
        }

        public Criteria andProCodeLessThan(String value) {
            addCriterion("PRO_CODE <", value, "proCode");
            return (Criteria) this;
        }

        public Criteria andProCodeLessThanOrEqualTo(String value) {
            addCriterion("PRO_CODE <=", value, "proCode");
            return (Criteria) this;
        }

        public Criteria andProCodeLike(String value) {
            addCriterion("PRO_CODE like", value, "proCode");
            return (Criteria) this;
        }

        public Criteria andProCodeNotLike(String value) {
            addCriterion("PRO_CODE not like", value, "proCode");
            return (Criteria) this;
        }

        public Criteria andProCodeIn(List<String> values) {
            addCriterion("PRO_CODE in", values, "proCode");
            return (Criteria) this;
        }

        public Criteria andProCodeNotIn(List<String> values) {
            addCriterion("PRO_CODE not in", values, "proCode");
            return (Criteria) this;
        }

        public Criteria andProCodeBetween(String value1, String value2) {
            addCriterion("PRO_CODE between", value1, value2, "proCode");
            return (Criteria) this;
        }

        public Criteria andProCodeNotBetween(String value1, String value2) {
            addCriterion("PRO_CODE not between", value1, value2, "proCode");
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

        public Criteria andProTypeIsNull() {
            addCriterion("PRO_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andProTypeIsNotNull() {
            addCriterion("PRO_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andProTypeEqualTo(String value) {
            addCriterion("PRO_TYPE =", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeNotEqualTo(String value) {
            addCriterion("PRO_TYPE <>", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeGreaterThan(String value) {
            addCriterion("PRO_TYPE >", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PRO_TYPE >=", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeLessThan(String value) {
            addCriterion("PRO_TYPE <", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeLessThanOrEqualTo(String value) {
            addCriterion("PRO_TYPE <=", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeLike(String value) {
            addCriterion("PRO_TYPE like", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeNotLike(String value) {
            addCriterion("PRO_TYPE not like", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeIn(List<String> values) {
            addCriterion("PRO_TYPE in", values, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeNotIn(List<String> values) {
            addCriterion("PRO_TYPE not in", values, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeBetween(String value1, String value2) {
            addCriterion("PRO_TYPE between", value1, value2, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeNotBetween(String value1, String value2) {
            addCriterion("PRO_TYPE not between", value1, value2, "proType");
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

        public Criteria andProPriceIsNull() {
            addCriterion("PRO_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andProPriceIsNotNull() {
            addCriterion("PRO_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andProPriceEqualTo(BigDecimal value) {
            addCriterion("PRO_PRICE =", value, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceNotEqualTo(BigDecimal value) {
            addCriterion("PRO_PRICE <>", value, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceGreaterThan(BigDecimal value) {
            addCriterion("PRO_PRICE >", value, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRO_PRICE >=", value, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceLessThan(BigDecimal value) {
            addCriterion("PRO_PRICE <", value, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRO_PRICE <=", value, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceIn(List<BigDecimal> values) {
            addCriterion("PRO_PRICE in", values, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceNotIn(List<BigDecimal> values) {
            addCriterion("PRO_PRICE not in", values, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRO_PRICE between", value1, value2, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRO_PRICE not between", value1, value2, "proPrice");
            return (Criteria) this;
        }

        public Criteria andIsDepositIsNull() {
            addCriterion("IS_DEPOSIT is null");
            return (Criteria) this;
        }

        public Criteria andIsDepositIsNotNull() {
            addCriterion("IS_DEPOSIT is not null");
            return (Criteria) this;
        }

        public Criteria andIsDepositEqualTo(BigDecimal value) {
            addCriterion("IS_DEPOSIT =", value, "isDeposit");
            return (Criteria) this;
        }

        public Criteria andIsDepositNotEqualTo(BigDecimal value) {
            addCriterion("IS_DEPOSIT <>", value, "isDeposit");
            return (Criteria) this;
        }

        public Criteria andIsDepositGreaterThan(BigDecimal value) {
            addCriterion("IS_DEPOSIT >", value, "isDeposit");
            return (Criteria) this;
        }

        public Criteria andIsDepositGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("IS_DEPOSIT >=", value, "isDeposit");
            return (Criteria) this;
        }

        public Criteria andIsDepositLessThan(BigDecimal value) {
            addCriterion("IS_DEPOSIT <", value, "isDeposit");
            return (Criteria) this;
        }

        public Criteria andIsDepositLessThanOrEqualTo(BigDecimal value) {
            addCriterion("IS_DEPOSIT <=", value, "isDeposit");
            return (Criteria) this;
        }

        public Criteria andIsDepositIn(List<BigDecimal> values) {
            addCriterion("IS_DEPOSIT in", values, "isDeposit");
            return (Criteria) this;
        }

        public Criteria andIsDepositNotIn(List<BigDecimal> values) {
            addCriterion("IS_DEPOSIT not in", values, "isDeposit");
            return (Criteria) this;
        }

        public Criteria andIsDepositBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IS_DEPOSIT between", value1, value2, "isDeposit");
            return (Criteria) this;
        }

        public Criteria andIsDepositNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IS_DEPOSIT not between", value1, value2, "isDeposit");
            return (Criteria) this;
        }

        public Criteria andDepositIsNull() {
            addCriterion("DEPOSIT is null");
            return (Criteria) this;
        }

        public Criteria andDepositIsNotNull() {
            addCriterion("DEPOSIT is not null");
            return (Criteria) this;
        }

        public Criteria andDepositEqualTo(BigDecimal value) {
            addCriterion("DEPOSIT =", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotEqualTo(BigDecimal value) {
            addCriterion("DEPOSIT <>", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositGreaterThan(BigDecimal value) {
            addCriterion("DEPOSIT >", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DEPOSIT >=", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositLessThan(BigDecimal value) {
            addCriterion("DEPOSIT <", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DEPOSIT <=", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositIn(List<BigDecimal> values) {
            addCriterion("DEPOSIT in", values, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotIn(List<BigDecimal> values) {
            addCriterion("DEPOSIT not in", values, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEPOSIT between", value1, value2, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEPOSIT not between", value1, value2, "deposit");
            return (Criteria) this;
        }

        public Criteria andModelIsNull() {
            addCriterion("MODEL is null");
            return (Criteria) this;
        }

        public Criteria andModelIsNotNull() {
            addCriterion("MODEL is not null");
            return (Criteria) this;
        }

        public Criteria andModelEqualTo(String value) {
            addCriterion("MODEL =", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotEqualTo(String value) {
            addCriterion("MODEL <>", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThan(String value) {
            addCriterion("MODEL >", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThanOrEqualTo(String value) {
            addCriterion("MODEL >=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThan(String value) {
            addCriterion("MODEL <", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThanOrEqualTo(String value) {
            addCriterion("MODEL <=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLike(String value) {
            addCriterion("MODEL like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotLike(String value) {
            addCriterion("MODEL not like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelIn(List<String> values) {
            addCriterion("MODEL in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotIn(List<String> values) {
            addCriterion("MODEL not in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelBetween(String value1, String value2) {
            addCriterion("MODEL between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotBetween(String value1, String value2) {
            addCriterion("MODEL not between", value1, value2, "model");
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

        public Criteria andBatchNoIsNull() {
            addCriterion("BATCH_NO is null");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNotNull() {
            addCriterion("BATCH_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNoEqualTo(String value) {
            addCriterion("BATCH_NO =", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotEqualTo(String value) {
            addCriterion("BATCH_NO <>", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThan(String value) {
            addCriterion("BATCH_NO >", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThanOrEqualTo(String value) {
            addCriterion("BATCH_NO >=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThan(String value) {
            addCriterion("BATCH_NO <", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThanOrEqualTo(String value) {
            addCriterion("BATCH_NO <=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLike(String value) {
            addCriterion("BATCH_NO like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotLike(String value) {
            addCriterion("BATCH_NO not like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoIn(List<String> values) {
            addCriterion("BATCH_NO in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotIn(List<String> values) {
            addCriterion("BATCH_NO not in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoBetween(String value1, String value2) {
            addCriterion("BATCH_NO between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotBetween(String value1, String value2) {
            addCriterion("BATCH_NO not between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andOrderPriceIsNull() {
            addCriterion("ORDER_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andOrderPriceIsNotNull() {
            addCriterion("ORDER_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andOrderPriceEqualTo(BigDecimal value) {
            addCriterion("ORDER_PRICE =", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceNotEqualTo(BigDecimal value) {
            addCriterion("ORDER_PRICE <>", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceGreaterThan(BigDecimal value) {
            addCriterion("ORDER_PRICE >", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDER_PRICE >=", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceLessThan(BigDecimal value) {
            addCriterion("ORDER_PRICE <", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDER_PRICE <=", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceIn(List<BigDecimal> values) {
            addCriterion("ORDER_PRICE in", values, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceNotIn(List<BigDecimal> values) {
            addCriterion("ORDER_PRICE not in", values, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDER_PRICE between", value1, value2, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDER_PRICE not between", value1, value2, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andReturnPriceIsNull() {
            addCriterion("RETURN_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andReturnPriceIsNotNull() {
            addCriterion("RETURN_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andReturnPriceEqualTo(BigDecimal value) {
            addCriterion("RETURN_PRICE =", value, "returnPrice");
            return (Criteria) this;
        }

        public Criteria andReturnPriceNotEqualTo(BigDecimal value) {
            addCriterion("RETURN_PRICE <>", value, "returnPrice");
            return (Criteria) this;
        }

        public Criteria andReturnPriceGreaterThan(BigDecimal value) {
            addCriterion("RETURN_PRICE >", value, "returnPrice");
            return (Criteria) this;
        }

        public Criteria andReturnPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RETURN_PRICE >=", value, "returnPrice");
            return (Criteria) this;
        }

        public Criteria andReturnPriceLessThan(BigDecimal value) {
            addCriterion("RETURN_PRICE <", value, "returnPrice");
            return (Criteria) this;
        }

        public Criteria andReturnPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RETURN_PRICE <=", value, "returnPrice");
            return (Criteria) this;
        }

        public Criteria andReturnPriceIn(List<BigDecimal> values) {
            addCriterion("RETURN_PRICE in", values, "returnPrice");
            return (Criteria) this;
        }

        public Criteria andReturnPriceNotIn(List<BigDecimal> values) {
            addCriterion("RETURN_PRICE not in", values, "returnPrice");
            return (Criteria) this;
        }

        public Criteria andReturnPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RETURN_PRICE between", value1, value2, "returnPrice");
            return (Criteria) this;
        }

        public Criteria andReturnPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RETURN_PRICE not between", value1, value2, "returnPrice");
            return (Criteria) this;
        }

        public Criteria andReturnCountIsNull() {
            addCriterion("RETURN_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andReturnCountIsNotNull() {
            addCriterion("RETURN_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andReturnCountEqualTo(BigDecimal value) {
            addCriterion("RETURN_COUNT =", value, "returnCount");
            return (Criteria) this;
        }

        public Criteria andReturnCountNotEqualTo(BigDecimal value) {
            addCriterion("RETURN_COUNT <>", value, "returnCount");
            return (Criteria) this;
        }

        public Criteria andReturnCountGreaterThan(BigDecimal value) {
            addCriterion("RETURN_COUNT >", value, "returnCount");
            return (Criteria) this;
        }

        public Criteria andReturnCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RETURN_COUNT >=", value, "returnCount");
            return (Criteria) this;
        }

        public Criteria andReturnCountLessThan(BigDecimal value) {
            addCriterion("RETURN_COUNT <", value, "returnCount");
            return (Criteria) this;
        }

        public Criteria andReturnCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RETURN_COUNT <=", value, "returnCount");
            return (Criteria) this;
        }

        public Criteria andReturnCountIn(List<BigDecimal> values) {
            addCriterion("RETURN_COUNT in", values, "returnCount");
            return (Criteria) this;
        }

        public Criteria andReturnCountNotIn(List<BigDecimal> values) {
            addCriterion("RETURN_COUNT not in", values, "returnCount");
            return (Criteria) this;
        }

        public Criteria andReturnCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RETURN_COUNT between", value1, value2, "returnCount");
            return (Criteria) this;
        }

        public Criteria andReturnCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RETURN_COUNT not between", value1, value2, "returnCount");
            return (Criteria) this;
        }

        public Criteria andReturnAmtIsNull() {
            addCriterion("RETURN_AMT is null");
            return (Criteria) this;
        }

        public Criteria andReturnAmtIsNotNull() {
            addCriterion("RETURN_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andReturnAmtEqualTo(BigDecimal value) {
            addCriterion("RETURN_AMT =", value, "returnAmt");
            return (Criteria) this;
        }

        public Criteria andReturnAmtNotEqualTo(BigDecimal value) {
            addCriterion("RETURN_AMT <>", value, "returnAmt");
            return (Criteria) this;
        }

        public Criteria andReturnAmtGreaterThan(BigDecimal value) {
            addCriterion("RETURN_AMT >", value, "returnAmt");
            return (Criteria) this;
        }

        public Criteria andReturnAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RETURN_AMT >=", value, "returnAmt");
            return (Criteria) this;
        }

        public Criteria andReturnAmtLessThan(BigDecimal value) {
            addCriterion("RETURN_AMT <", value, "returnAmt");
            return (Criteria) this;
        }

        public Criteria andReturnAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RETURN_AMT <=", value, "returnAmt");
            return (Criteria) this;
        }

        public Criteria andReturnAmtIn(List<BigDecimal> values) {
            addCriterion("RETURN_AMT in", values, "returnAmt");
            return (Criteria) this;
        }

        public Criteria andReturnAmtNotIn(List<BigDecimal> values) {
            addCriterion("RETURN_AMT not in", values, "returnAmt");
            return (Criteria) this;
        }

        public Criteria andReturnAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RETURN_AMT between", value1, value2, "returnAmt");
            return (Criteria) this;
        }

        public Criteria andReturnAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RETURN_AMT not between", value1, value2, "returnAmt");
            return (Criteria) this;
        }

        public Criteria andReturnTimeIsNull() {
            addCriterion("RETURN_TIME is null");
            return (Criteria) this;
        }

        public Criteria andReturnTimeIsNotNull() {
            addCriterion("RETURN_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andReturnTimeEqualTo(Date value) {
            addCriterion("RETURN_TIME =", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotEqualTo(Date value) {
            addCriterion("RETURN_TIME <>", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeGreaterThan(Date value) {
            addCriterion("RETURN_TIME >", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("RETURN_TIME >=", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeLessThan(Date value) {
            addCriterion("RETURN_TIME <", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeLessThanOrEqualTo(Date value) {
            addCriterion("RETURN_TIME <=", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeIn(List<Date> values) {
            addCriterion("RETURN_TIME in", values, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotIn(List<Date> values) {
            addCriterion("RETURN_TIME not in", values, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeBetween(Date value1, Date value2) {
            addCriterion("RETURN_TIME between", value1, value2, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotBetween(Date value1, Date value2) {
            addCriterion("RETURN_TIME not between", value1, value2, "returnTime");
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

        public Criteria andActidIsNull() {
            addCriterion("ACTID is null");
            return (Criteria) this;
        }

        public Criteria andActidIsNotNull() {
            addCriterion("ACTID is not null");
            return (Criteria) this;
        }

        public Criteria andActidEqualTo(String value) {
            addCriterion("ACTID =", value, "actid");
            return (Criteria) this;
        }

        public Criteria andActidNotEqualTo(String value) {
            addCriterion("ACTID <>", value, "actid");
            return (Criteria) this;
        }

        public Criteria andActidGreaterThan(String value) {
            addCriterion("ACTID >", value, "actid");
            return (Criteria) this;
        }

        public Criteria andActidGreaterThanOrEqualTo(String value) {
            addCriterion("ACTID >=", value, "actid");
            return (Criteria) this;
        }

        public Criteria andActidLessThan(String value) {
            addCriterion("ACTID <", value, "actid");
            return (Criteria) this;
        }

        public Criteria andActidLessThanOrEqualTo(String value) {
            addCriterion("ACTID <=", value, "actid");
            return (Criteria) this;
        }

        public Criteria andActidLike(String value) {
            addCriterion("ACTID like", value, "actid");
            return (Criteria) this;
        }

        public Criteria andActidNotLike(String value) {
            addCriterion("ACTID not like", value, "actid");
            return (Criteria) this;
        }

        public Criteria andActidIn(List<String> values) {
            addCriterion("ACTID in", values, "actid");
            return (Criteria) this;
        }

        public Criteria andActidNotIn(List<String> values) {
            addCriterion("ACTID not in", values, "actid");
            return (Criteria) this;
        }

        public Criteria andActidBetween(String value1, String value2) {
            addCriterion("ACTID between", value1, value2, "actid");
            return (Criteria) this;
        }

        public Criteria andActidNotBetween(String value1, String value2) {
            addCriterion("ACTID not between", value1, value2, "actid");
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