package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OPaymentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OPaymentExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("USER_ID like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("USER_ID not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
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

        public Criteria andPayMethodIsNull() {
            addCriterion("PAY_METHOD is null");
            return (Criteria) this;
        }

        public Criteria andPayMethodIsNotNull() {
            addCriterion("PAY_METHOD is not null");
            return (Criteria) this;
        }

        public Criteria andPayMethodEqualTo(String value) {
            addCriterion("PAY_METHOD =", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodNotEqualTo(String value) {
            addCriterion("PAY_METHOD <>", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodGreaterThan(String value) {
            addCriterion("PAY_METHOD >", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_METHOD >=", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodLessThan(String value) {
            addCriterion("PAY_METHOD <", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodLessThanOrEqualTo(String value) {
            addCriterion("PAY_METHOD <=", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodLike(String value) {
            addCriterion("PAY_METHOD like", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodNotLike(String value) {
            addCriterion("PAY_METHOD not like", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodIn(List<String> values) {
            addCriterion("PAY_METHOD in", values, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodNotIn(List<String> values) {
            addCriterion("PAY_METHOD not in", values, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodBetween(String value1, String value2) {
            addCriterion("PAY_METHOD between", value1, value2, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodNotBetween(String value1, String value2) {
            addCriterion("PAY_METHOD not between", value1, value2, "payMethod");
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

        public Criteria andPayAmountIsNull() {
            addCriterion("PAY_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andPayAmountIsNotNull() {
            addCriterion("PAY_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andPayAmountEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT =", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountNotEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT <>", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountGreaterThan(BigDecimal value) {
            addCriterion("PAY_AMOUNT >", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT >=", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountLessThan(BigDecimal value) {
            addCriterion("PAY_AMOUNT <", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT <=", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountIn(List<BigDecimal> values) {
            addCriterion("PAY_AMOUNT in", values, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountNotIn(List<BigDecimal> values) {
            addCriterion("PAY_AMOUNT not in", values, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_AMOUNT between", value1, value2, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_AMOUNT not between", value1, value2, "payAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountIsNull() {
            addCriterion("REAL_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andRealAmountIsNotNull() {
            addCriterion("REAL_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andRealAmountEqualTo(BigDecimal value) {
            addCriterion("REAL_AMOUNT =", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountNotEqualTo(BigDecimal value) {
            addCriterion("REAL_AMOUNT <>", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountGreaterThan(BigDecimal value) {
            addCriterion("REAL_AMOUNT >", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_AMOUNT >=", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountLessThan(BigDecimal value) {
            addCriterion("REAL_AMOUNT <", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_AMOUNT <=", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountIn(List<BigDecimal> values) {
            addCriterion("REAL_AMOUNT in", values, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountNotIn(List<BigDecimal> values) {
            addCriterion("REAL_AMOUNT not in", values, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_AMOUNT between", value1, value2, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_AMOUNT not between", value1, value2, "realAmount");
            return (Criteria) this;
        }

        public Criteria andPayCompletTimeIsNull() {
            addCriterion("PAY_COMPLET_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPayCompletTimeIsNotNull() {
            addCriterion("PAY_COMPLET_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPayCompletTimeEqualTo(Date value) {
            addCriterion("PAY_COMPLET_TIME =", value, "payCompletTime");
            return (Criteria) this;
        }

        public Criteria andPayCompletTimeNotEqualTo(Date value) {
            addCriterion("PAY_COMPLET_TIME <>", value, "payCompletTime");
            return (Criteria) this;
        }

        public Criteria andPayCompletTimeGreaterThan(Date value) {
            addCriterion("PAY_COMPLET_TIME >", value, "payCompletTime");
            return (Criteria) this;
        }

        public Criteria andPayCompletTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("PAY_COMPLET_TIME >=", value, "payCompletTime");
            return (Criteria) this;
        }

        public Criteria andPayCompletTimeLessThan(Date value) {
            addCriterion("PAY_COMPLET_TIME <", value, "payCompletTime");
            return (Criteria) this;
        }

        public Criteria andPayCompletTimeLessThanOrEqualTo(Date value) {
            addCriterion("PAY_COMPLET_TIME <=", value, "payCompletTime");
            return (Criteria) this;
        }

        public Criteria andPayCompletTimeIn(List<Date> values) {
            addCriterion("PAY_COMPLET_TIME in", values, "payCompletTime");
            return (Criteria) this;
        }

        public Criteria andPayCompletTimeNotIn(List<Date> values) {
            addCriterion("PAY_COMPLET_TIME not in", values, "payCompletTime");
            return (Criteria) this;
        }

        public Criteria andPayCompletTimeBetween(Date value1, Date value2) {
            addCriterion("PAY_COMPLET_TIME between", value1, value2, "payCompletTime");
            return (Criteria) this;
        }

        public Criteria andPayCompletTimeNotBetween(Date value1, Date value2) {
            addCriterion("PAY_COMPLET_TIME not between", value1, value2, "payCompletTime");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNull() {
            addCriterion("PAY_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNotNull() {
            addCriterion("PAY_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andPayStatusEqualTo(BigDecimal value) {
            addCriterion("PAY_STATUS =", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotEqualTo(BigDecimal value) {
            addCriterion("PAY_STATUS <>", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThan(BigDecimal value) {
            addCriterion("PAY_STATUS >", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_STATUS >=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThan(BigDecimal value) {
            addCriterion("PAY_STATUS <", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_STATUS <=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusIn(List<BigDecimal> values) {
            addCriterion("PAY_STATUS in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotIn(List<BigDecimal> values) {
            addCriterion("PAY_STATUS not in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_STATUS between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_STATUS not between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPlanSucTimeIsNull() {
            addCriterion("PLAN_SUC_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPlanSucTimeIsNotNull() {
            addCriterion("PLAN_SUC_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPlanSucTimeEqualTo(Date value) {
            addCriterion("PLAN_SUC_TIME =", value, "planSucTime");
            return (Criteria) this;
        }

        public Criteria andPlanSucTimeNotEqualTo(Date value) {
            addCriterion("PLAN_SUC_TIME <>", value, "planSucTime");
            return (Criteria) this;
        }

        public Criteria andPlanSucTimeGreaterThan(Date value) {
            addCriterion("PLAN_SUC_TIME >", value, "planSucTime");
            return (Criteria) this;
        }

        public Criteria andPlanSucTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("PLAN_SUC_TIME >=", value, "planSucTime");
            return (Criteria) this;
        }

        public Criteria andPlanSucTimeLessThan(Date value) {
            addCriterion("PLAN_SUC_TIME <", value, "planSucTime");
            return (Criteria) this;
        }

        public Criteria andPlanSucTimeLessThanOrEqualTo(Date value) {
            addCriterion("PLAN_SUC_TIME <=", value, "planSucTime");
            return (Criteria) this;
        }

        public Criteria andPlanSucTimeIn(List<Date> values) {
            addCriterion("PLAN_SUC_TIME in", values, "planSucTime");
            return (Criteria) this;
        }

        public Criteria andPlanSucTimeNotIn(List<Date> values) {
            addCriterion("PLAN_SUC_TIME not in", values, "planSucTime");
            return (Criteria) this;
        }

        public Criteria andPlanSucTimeBetween(Date value1, Date value2) {
            addCriterion("PLAN_SUC_TIME between", value1, value2, "planSucTime");
            return (Criteria) this;
        }

        public Criteria andPlanSucTimeNotBetween(Date value1, Date value2) {
            addCriterion("PLAN_SUC_TIME not between", value1, value2, "planSucTime");
            return (Criteria) this;
        }

        public Criteria andPayRuleIsNull() {
            addCriterion("PAY_RULE is null");
            return (Criteria) this;
        }

        public Criteria andPayRuleIsNotNull() {
            addCriterion("PAY_RULE is not null");
            return (Criteria) this;
        }

        public Criteria andPayRuleEqualTo(String value) {
            addCriterion("PAY_RULE =", value, "payRule");
            return (Criteria) this;
        }

        public Criteria andPayRuleNotEqualTo(String value) {
            addCriterion("PAY_RULE <>", value, "payRule");
            return (Criteria) this;
        }

        public Criteria andPayRuleGreaterThan(String value) {
            addCriterion("PAY_RULE >", value, "payRule");
            return (Criteria) this;
        }

        public Criteria andPayRuleGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_RULE >=", value, "payRule");
            return (Criteria) this;
        }

        public Criteria andPayRuleLessThan(String value) {
            addCriterion("PAY_RULE <", value, "payRule");
            return (Criteria) this;
        }

        public Criteria andPayRuleLessThanOrEqualTo(String value) {
            addCriterion("PAY_RULE <=", value, "payRule");
            return (Criteria) this;
        }

        public Criteria andPayRuleLike(String value) {
            addCriterion("PAY_RULE like", value, "payRule");
            return (Criteria) this;
        }

        public Criteria andPayRuleNotLike(String value) {
            addCriterion("PAY_RULE not like", value, "payRule");
            return (Criteria) this;
        }

        public Criteria andPayRuleIn(List<String> values) {
            addCriterion("PAY_RULE in", values, "payRule");
            return (Criteria) this;
        }

        public Criteria andPayRuleNotIn(List<String> values) {
            addCriterion("PAY_RULE not in", values, "payRule");
            return (Criteria) this;
        }

        public Criteria andPayRuleBetween(String value1, String value2) {
            addCriterion("PAY_RULE between", value1, value2, "payRule");
            return (Criteria) this;
        }

        public Criteria andPayRuleNotBetween(String value1, String value2) {
            addCriterion("PAY_RULE not between", value1, value2, "payRule");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAgentIsNull() {
            addCriterion("GUARANTEE_AGENT is null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAgentIsNotNull() {
            addCriterion("GUARANTEE_AGENT is not null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAgentEqualTo(String value) {
            addCriterion("GUARANTEE_AGENT =", value, "guaranteeAgent");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAgentNotEqualTo(String value) {
            addCriterion("GUARANTEE_AGENT <>", value, "guaranteeAgent");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAgentGreaterThan(String value) {
            addCriterion("GUARANTEE_AGENT >", value, "guaranteeAgent");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAgentGreaterThanOrEqualTo(String value) {
            addCriterion("GUARANTEE_AGENT >=", value, "guaranteeAgent");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAgentLessThan(String value) {
            addCriterion("GUARANTEE_AGENT <", value, "guaranteeAgent");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAgentLessThanOrEqualTo(String value) {
            addCriterion("GUARANTEE_AGENT <=", value, "guaranteeAgent");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAgentLike(String value) {
            addCriterion("GUARANTEE_AGENT like", value, "guaranteeAgent");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAgentNotLike(String value) {
            addCriterion("GUARANTEE_AGENT not like", value, "guaranteeAgent");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAgentIn(List<String> values) {
            addCriterion("GUARANTEE_AGENT in", values, "guaranteeAgent");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAgentNotIn(List<String> values) {
            addCriterion("GUARANTEE_AGENT not in", values, "guaranteeAgent");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAgentBetween(String value1, String value2) {
            addCriterion("GUARANTEE_AGENT between", value1, value2, "guaranteeAgent");
            return (Criteria) this;
        }

        public Criteria andGuaranteeAgentNotBetween(String value1, String value2) {
            addCriterion("GUARANTEE_AGENT not between", value1, value2, "guaranteeAgent");
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

        public Criteria andShareTempletIsNull() {
            addCriterion("SHARE_TEMPLET is null");
            return (Criteria) this;
        }

        public Criteria andShareTempletIsNotNull() {
            addCriterion("SHARE_TEMPLET is not null");
            return (Criteria) this;
        }

        public Criteria andShareTempletEqualTo(String value) {
            addCriterion("SHARE_TEMPLET =", value, "shareTemplet");
            return (Criteria) this;
        }

        public Criteria andShareTempletNotEqualTo(String value) {
            addCriterion("SHARE_TEMPLET <>", value, "shareTemplet");
            return (Criteria) this;
        }

        public Criteria andShareTempletGreaterThan(String value) {
            addCriterion("SHARE_TEMPLET >", value, "shareTemplet");
            return (Criteria) this;
        }

        public Criteria andShareTempletGreaterThanOrEqualTo(String value) {
            addCriterion("SHARE_TEMPLET >=", value, "shareTemplet");
            return (Criteria) this;
        }

        public Criteria andShareTempletLessThan(String value) {
            addCriterion("SHARE_TEMPLET <", value, "shareTemplet");
            return (Criteria) this;
        }

        public Criteria andShareTempletLessThanOrEqualTo(String value) {
            addCriterion("SHARE_TEMPLET <=", value, "shareTemplet");
            return (Criteria) this;
        }

        public Criteria andShareTempletLike(String value) {
            addCriterion("SHARE_TEMPLET like", value, "shareTemplet");
            return (Criteria) this;
        }

        public Criteria andShareTempletNotLike(String value) {
            addCriterion("SHARE_TEMPLET not like", value, "shareTemplet");
            return (Criteria) this;
        }

        public Criteria andShareTempletIn(List<String> values) {
            addCriterion("SHARE_TEMPLET in", values, "shareTemplet");
            return (Criteria) this;
        }

        public Criteria andShareTempletNotIn(List<String> values) {
            addCriterion("SHARE_TEMPLET not in", values, "shareTemplet");
            return (Criteria) this;
        }

        public Criteria andShareTempletBetween(String value1, String value2) {
            addCriterion("SHARE_TEMPLET between", value1, value2, "shareTemplet");
            return (Criteria) this;
        }

        public Criteria andShareTempletNotBetween(String value1, String value2) {
            addCriterion("SHARE_TEMPLET not between", value1, value2, "shareTemplet");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceIsNull() {
            addCriterion("IS_CLO_INVOICE is null");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceIsNotNull() {
            addCriterion("IS_CLO_INVOICE is not null");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceEqualTo(BigDecimal value) {
            addCriterion("IS_CLO_INVOICE =", value, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceNotEqualTo(BigDecimal value) {
            addCriterion("IS_CLO_INVOICE <>", value, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceGreaterThan(BigDecimal value) {
            addCriterion("IS_CLO_INVOICE >", value, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("IS_CLO_INVOICE >=", value, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceLessThan(BigDecimal value) {
            addCriterion("IS_CLO_INVOICE <", value, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("IS_CLO_INVOICE <=", value, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceIn(List<BigDecimal> values) {
            addCriterion("IS_CLO_INVOICE in", values, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceNotIn(List<BigDecimal> values) {
            addCriterion("IS_CLO_INVOICE not in", values, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IS_CLO_INVOICE between", value1, value2, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IS_CLO_INVOICE not between", value1, value2, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andDeductionTypeIsNull() {
            addCriterion("DEDUCTION_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andDeductionTypeIsNotNull() {
            addCriterion("DEDUCTION_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andDeductionTypeEqualTo(String value) {
            addCriterion("DEDUCTION_TYPE =", value, "deductionType");
            return (Criteria) this;
        }

        public Criteria andDeductionTypeNotEqualTo(String value) {
            addCriterion("DEDUCTION_TYPE <>", value, "deductionType");
            return (Criteria) this;
        }

        public Criteria andDeductionTypeGreaterThan(String value) {
            addCriterion("DEDUCTION_TYPE >", value, "deductionType");
            return (Criteria) this;
        }

        public Criteria andDeductionTypeGreaterThanOrEqualTo(String value) {
            addCriterion("DEDUCTION_TYPE >=", value, "deductionType");
            return (Criteria) this;
        }

        public Criteria andDeductionTypeLessThan(String value) {
            addCriterion("DEDUCTION_TYPE <", value, "deductionType");
            return (Criteria) this;
        }

        public Criteria andDeductionTypeLessThanOrEqualTo(String value) {
            addCriterion("DEDUCTION_TYPE <=", value, "deductionType");
            return (Criteria) this;
        }

        public Criteria andDeductionTypeLike(String value) {
            addCriterion("DEDUCTION_TYPE like", value, "deductionType");
            return (Criteria) this;
        }

        public Criteria andDeductionTypeNotLike(String value) {
            addCriterion("DEDUCTION_TYPE not like", value, "deductionType");
            return (Criteria) this;
        }

        public Criteria andDeductionTypeIn(List<String> values) {
            addCriterion("DEDUCTION_TYPE in", values, "deductionType");
            return (Criteria) this;
        }

        public Criteria andDeductionTypeNotIn(List<String> values) {
            addCriterion("DEDUCTION_TYPE not in", values, "deductionType");
            return (Criteria) this;
        }

        public Criteria andDeductionTypeBetween(String value1, String value2) {
            addCriterion("DEDUCTION_TYPE between", value1, value2, "deductionType");
            return (Criteria) this;
        }

        public Criteria andDeductionTypeNotBetween(String value1, String value2) {
            addCriterion("DEDUCTION_TYPE not between", value1, value2, "deductionType");
            return (Criteria) this;
        }

        public Criteria andDeductionAmountIsNull() {
            addCriterion("DEDUCTION_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andDeductionAmountIsNotNull() {
            addCriterion("DEDUCTION_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andDeductionAmountEqualTo(BigDecimal value) {
            addCriterion("DEDUCTION_AMOUNT =", value, "deductionAmount");
            return (Criteria) this;
        }

        public Criteria andDeductionAmountNotEqualTo(BigDecimal value) {
            addCriterion("DEDUCTION_AMOUNT <>", value, "deductionAmount");
            return (Criteria) this;
        }

        public Criteria andDeductionAmountGreaterThan(BigDecimal value) {
            addCriterion("DEDUCTION_AMOUNT >", value, "deductionAmount");
            return (Criteria) this;
        }

        public Criteria andDeductionAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DEDUCTION_AMOUNT >=", value, "deductionAmount");
            return (Criteria) this;
        }

        public Criteria andDeductionAmountLessThan(BigDecimal value) {
            addCriterion("DEDUCTION_AMOUNT <", value, "deductionAmount");
            return (Criteria) this;
        }

        public Criteria andDeductionAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DEDUCTION_AMOUNT <=", value, "deductionAmount");
            return (Criteria) this;
        }

        public Criteria andDeductionAmountIn(List<BigDecimal> values) {
            addCriterion("DEDUCTION_AMOUNT in", values, "deductionAmount");
            return (Criteria) this;
        }

        public Criteria andDeductionAmountNotIn(List<BigDecimal> values) {
            addCriterion("DEDUCTION_AMOUNT not in", values, "deductionAmount");
            return (Criteria) this;
        }

        public Criteria andDeductionAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEDUCTION_AMOUNT between", value1, value2, "deductionAmount");
            return (Criteria) this;
        }

        public Criteria andDeductionAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEDUCTION_AMOUNT not between", value1, value2, "deductionAmount");
            return (Criteria) this;
        }

        public Criteria andDownPaymentIsNull() {
            addCriterion("DOWN_PAYMENT is null");
            return (Criteria) this;
        }

        public Criteria andDownPaymentIsNotNull() {
            addCriterion("DOWN_PAYMENT is not null");
            return (Criteria) this;
        }

        public Criteria andDownPaymentEqualTo(BigDecimal value) {
            addCriterion("DOWN_PAYMENT =", value, "downPayment");
            return (Criteria) this;
        }

        public Criteria andDownPaymentNotEqualTo(BigDecimal value) {
            addCriterion("DOWN_PAYMENT <>", value, "downPayment");
            return (Criteria) this;
        }

        public Criteria andDownPaymentGreaterThan(BigDecimal value) {
            addCriterion("DOWN_PAYMENT >", value, "downPayment");
            return (Criteria) this;
        }

        public Criteria andDownPaymentGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DOWN_PAYMENT >=", value, "downPayment");
            return (Criteria) this;
        }

        public Criteria andDownPaymentLessThan(BigDecimal value) {
            addCriterion("DOWN_PAYMENT <", value, "downPayment");
            return (Criteria) this;
        }

        public Criteria andDownPaymentLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DOWN_PAYMENT <=", value, "downPayment");
            return (Criteria) this;
        }

        public Criteria andDownPaymentIn(List<BigDecimal> values) {
            addCriterion("DOWN_PAYMENT in", values, "downPayment");
            return (Criteria) this;
        }

        public Criteria andDownPaymentNotIn(List<BigDecimal> values) {
            addCriterion("DOWN_PAYMENT not in", values, "downPayment");
            return (Criteria) this;
        }

        public Criteria andDownPaymentBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DOWN_PAYMENT between", value1, value2, "downPayment");
            return (Criteria) this;
        }

        public Criteria andDownPaymentNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DOWN_PAYMENT not between", value1, value2, "downPayment");
            return (Criteria) this;
        }

        public Criteria andDownPaymentCountIsNull() {
            addCriterion("DOWN_PAYMENT_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andDownPaymentCountIsNotNull() {
            addCriterion("DOWN_PAYMENT_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andDownPaymentCountEqualTo(BigDecimal value) {
            addCriterion("DOWN_PAYMENT_COUNT =", value, "downPaymentCount");
            return (Criteria) this;
        }

        public Criteria andDownPaymentCountNotEqualTo(BigDecimal value) {
            addCriterion("DOWN_PAYMENT_COUNT <>", value, "downPaymentCount");
            return (Criteria) this;
        }

        public Criteria andDownPaymentCountGreaterThan(BigDecimal value) {
            addCriterion("DOWN_PAYMENT_COUNT >", value, "downPaymentCount");
            return (Criteria) this;
        }

        public Criteria andDownPaymentCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DOWN_PAYMENT_COUNT >=", value, "downPaymentCount");
            return (Criteria) this;
        }

        public Criteria andDownPaymentCountLessThan(BigDecimal value) {
            addCriterion("DOWN_PAYMENT_COUNT <", value, "downPaymentCount");
            return (Criteria) this;
        }

        public Criteria andDownPaymentCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DOWN_PAYMENT_COUNT <=", value, "downPaymentCount");
            return (Criteria) this;
        }

        public Criteria andDownPaymentCountIn(List<BigDecimal> values) {
            addCriterion("DOWN_PAYMENT_COUNT in", values, "downPaymentCount");
            return (Criteria) this;
        }

        public Criteria andDownPaymentCountNotIn(List<BigDecimal> values) {
            addCriterion("DOWN_PAYMENT_COUNT not in", values, "downPaymentCount");
            return (Criteria) this;
        }

        public Criteria andDownPaymentCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DOWN_PAYMENT_COUNT between", value1, value2, "downPaymentCount");
            return (Criteria) this;
        }

        public Criteria andDownPaymentCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DOWN_PAYMENT_COUNT not between", value1, value2, "downPaymentCount");
            return (Criteria) this;
        }

        public Criteria andDownPaymentDateIsNull() {
            addCriterion("DOWN_PAYMENT_DATE is null");
            return (Criteria) this;
        }

        public Criteria andDownPaymentDateIsNotNull() {
            addCriterion("DOWN_PAYMENT_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andDownPaymentDateEqualTo(Date value) {
            addCriterion("DOWN_PAYMENT_DATE =", value, "downPaymentDate");
            return (Criteria) this;
        }

        public Criteria andDownPaymentDateNotEqualTo(Date value) {
            addCriterion("DOWN_PAYMENT_DATE <>", value, "downPaymentDate");
            return (Criteria) this;
        }

        public Criteria andDownPaymentDateGreaterThan(Date value) {
            addCriterion("DOWN_PAYMENT_DATE >", value, "downPaymentDate");
            return (Criteria) this;
        }

        public Criteria andDownPaymentDateGreaterThanOrEqualTo(Date value) {
            addCriterion("DOWN_PAYMENT_DATE >=", value, "downPaymentDate");
            return (Criteria) this;
        }

        public Criteria andDownPaymentDateLessThan(Date value) {
            addCriterion("DOWN_PAYMENT_DATE <", value, "downPaymentDate");
            return (Criteria) this;
        }

        public Criteria andDownPaymentDateLessThanOrEqualTo(Date value) {
            addCriterion("DOWN_PAYMENT_DATE <=", value, "downPaymentDate");
            return (Criteria) this;
        }

        public Criteria andDownPaymentDateIn(List<Date> values) {
            addCriterion("DOWN_PAYMENT_DATE in", values, "downPaymentDate");
            return (Criteria) this;
        }

        public Criteria andDownPaymentDateNotIn(List<Date> values) {
            addCriterion("DOWN_PAYMENT_DATE not in", values, "downPaymentDate");
            return (Criteria) this;
        }

        public Criteria andDownPaymentDateBetween(Date value1, Date value2) {
            addCriterion("DOWN_PAYMENT_DATE between", value1, value2, "downPaymentDate");
            return (Criteria) this;
        }

        public Criteria andDownPaymentDateNotBetween(Date value1, Date value2) {
            addCriterion("DOWN_PAYMENT_DATE not between", value1, value2, "downPaymentDate");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
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