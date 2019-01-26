package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentQuitExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AgentQuitExample() {
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

        public Criteria andQuitPlatformIsNull() {
            addCriterion("QUIT_PLATFORM is null");
            return (Criteria) this;
        }

        public Criteria andQuitPlatformIsNotNull() {
            addCriterion("QUIT_PLATFORM is not null");
            return (Criteria) this;
        }

        public Criteria andQuitPlatformEqualTo(String value) {
            addCriterion("QUIT_PLATFORM =", value, "quitPlatform");
            return (Criteria) this;
        }

        public Criteria andQuitPlatformNotEqualTo(String value) {
            addCriterion("QUIT_PLATFORM <>", value, "quitPlatform");
            return (Criteria) this;
        }

        public Criteria andQuitPlatformGreaterThan(String value) {
            addCriterion("QUIT_PLATFORM >", value, "quitPlatform");
            return (Criteria) this;
        }

        public Criteria andQuitPlatformGreaterThanOrEqualTo(String value) {
            addCriterion("QUIT_PLATFORM >=", value, "quitPlatform");
            return (Criteria) this;
        }

        public Criteria andQuitPlatformLessThan(String value) {
            addCriterion("QUIT_PLATFORM <", value, "quitPlatform");
            return (Criteria) this;
        }

        public Criteria andQuitPlatformLessThanOrEqualTo(String value) {
            addCriterion("QUIT_PLATFORM <=", value, "quitPlatform");
            return (Criteria) this;
        }

        public Criteria andQuitPlatformLike(String value) {
            addCriterion("QUIT_PLATFORM like", value, "quitPlatform");
            return (Criteria) this;
        }

        public Criteria andQuitPlatformNotLike(String value) {
            addCriterion("QUIT_PLATFORM not like", value, "quitPlatform");
            return (Criteria) this;
        }

        public Criteria andQuitPlatformIn(List<String> values) {
            addCriterion("QUIT_PLATFORM in", values, "quitPlatform");
            return (Criteria) this;
        }

        public Criteria andQuitPlatformNotIn(List<String> values) {
            addCriterion("QUIT_PLATFORM not in", values, "quitPlatform");
            return (Criteria) this;
        }

        public Criteria andQuitPlatformBetween(String value1, String value2) {
            addCriterion("QUIT_PLATFORM between", value1, value2, "quitPlatform");
            return (Criteria) this;
        }

        public Criteria andQuitPlatformNotBetween(String value1, String value2) {
            addCriterion("QUIT_PLATFORM not between", value1, value2, "quitPlatform");
            return (Criteria) this;
        }

        public Criteria andCapitalDebtIsNull() {
            addCriterion("CAPITAL_DEBT is null");
            return (Criteria) this;
        }

        public Criteria andCapitalDebtIsNotNull() {
            addCriterion("CAPITAL_DEBT is not null");
            return (Criteria) this;
        }

        public Criteria andCapitalDebtEqualTo(BigDecimal value) {
            addCriterion("CAPITAL_DEBT =", value, "capitalDebt");
            return (Criteria) this;
        }

        public Criteria andCapitalDebtNotEqualTo(BigDecimal value) {
            addCriterion("CAPITAL_DEBT <>", value, "capitalDebt");
            return (Criteria) this;
        }

        public Criteria andCapitalDebtGreaterThan(BigDecimal value) {
            addCriterion("CAPITAL_DEBT >", value, "capitalDebt");
            return (Criteria) this;
        }

        public Criteria andCapitalDebtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CAPITAL_DEBT >=", value, "capitalDebt");
            return (Criteria) this;
        }

        public Criteria andCapitalDebtLessThan(BigDecimal value) {
            addCriterion("CAPITAL_DEBT <", value, "capitalDebt");
            return (Criteria) this;
        }

        public Criteria andCapitalDebtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CAPITAL_DEBT <=", value, "capitalDebt");
            return (Criteria) this;
        }

        public Criteria andCapitalDebtIn(List<BigDecimal> values) {
            addCriterion("CAPITAL_DEBT in", values, "capitalDebt");
            return (Criteria) this;
        }

        public Criteria andCapitalDebtNotIn(List<BigDecimal> values) {
            addCriterion("CAPITAL_DEBT not in", values, "capitalDebt");
            return (Criteria) this;
        }

        public Criteria andCapitalDebtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CAPITAL_DEBT between", value1, value2, "capitalDebt");
            return (Criteria) this;
        }

        public Criteria andCapitalDebtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CAPITAL_DEBT not between", value1, value2, "capitalDebt");
            return (Criteria) this;
        }

        public Criteria andOrderDebtIsNull() {
            addCriterion("ORDER_DEBT is null");
            return (Criteria) this;
        }

        public Criteria andOrderDebtIsNotNull() {
            addCriterion("ORDER_DEBT is not null");
            return (Criteria) this;
        }

        public Criteria andOrderDebtEqualTo(BigDecimal value) {
            addCriterion("ORDER_DEBT =", value, "orderDebt");
            return (Criteria) this;
        }

        public Criteria andOrderDebtNotEqualTo(BigDecimal value) {
            addCriterion("ORDER_DEBT <>", value, "orderDebt");
            return (Criteria) this;
        }

        public Criteria andOrderDebtGreaterThan(BigDecimal value) {
            addCriterion("ORDER_DEBT >", value, "orderDebt");
            return (Criteria) this;
        }

        public Criteria andOrderDebtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDER_DEBT >=", value, "orderDebt");
            return (Criteria) this;
        }

        public Criteria andOrderDebtLessThan(BigDecimal value) {
            addCriterion("ORDER_DEBT <", value, "orderDebt");
            return (Criteria) this;
        }

        public Criteria andOrderDebtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDER_DEBT <=", value, "orderDebt");
            return (Criteria) this;
        }

        public Criteria andOrderDebtIn(List<BigDecimal> values) {
            addCriterion("ORDER_DEBT in", values, "orderDebt");
            return (Criteria) this;
        }

        public Criteria andOrderDebtNotIn(List<BigDecimal> values) {
            addCriterion("ORDER_DEBT not in", values, "orderDebt");
            return (Criteria) this;
        }

        public Criteria andOrderDebtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDER_DEBT between", value1, value2, "orderDebt");
            return (Criteria) this;
        }

        public Criteria andOrderDebtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDER_DEBT not between", value1, value2, "orderDebt");
            return (Criteria) this;
        }

        public Criteria andProfitDebtIsNull() {
            addCriterion("PROFIT_DEBT is null");
            return (Criteria) this;
        }

        public Criteria andProfitDebtIsNotNull() {
            addCriterion("PROFIT_DEBT is not null");
            return (Criteria) this;
        }

        public Criteria andProfitDebtEqualTo(BigDecimal value) {
            addCriterion("PROFIT_DEBT =", value, "profitDebt");
            return (Criteria) this;
        }

        public Criteria andProfitDebtNotEqualTo(BigDecimal value) {
            addCriterion("PROFIT_DEBT <>", value, "profitDebt");
            return (Criteria) this;
        }

        public Criteria andProfitDebtGreaterThan(BigDecimal value) {
            addCriterion("PROFIT_DEBT >", value, "profitDebt");
            return (Criteria) this;
        }

        public Criteria andProfitDebtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_DEBT >=", value, "profitDebt");
            return (Criteria) this;
        }

        public Criteria andProfitDebtLessThan(BigDecimal value) {
            addCriterion("PROFIT_DEBT <", value, "profitDebt");
            return (Criteria) this;
        }

        public Criteria andProfitDebtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_DEBT <=", value, "profitDebt");
            return (Criteria) this;
        }

        public Criteria andProfitDebtIn(List<BigDecimal> values) {
            addCriterion("PROFIT_DEBT in", values, "profitDebt");
            return (Criteria) this;
        }

        public Criteria andProfitDebtNotIn(List<BigDecimal> values) {
            addCriterion("PROFIT_DEBT not in", values, "profitDebt");
            return (Criteria) this;
        }

        public Criteria andProfitDebtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_DEBT between", value1, value2, "profitDebt");
            return (Criteria) this;
        }

        public Criteria andProfitDebtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_DEBT not between", value1, value2, "profitDebt");
            return (Criteria) this;
        }

        public Criteria andAgentDeptIsNull() {
            addCriterion("AGENT_DEPT is null");
            return (Criteria) this;
        }

        public Criteria andAgentDeptIsNotNull() {
            addCriterion("AGENT_DEPT is not null");
            return (Criteria) this;
        }

        public Criteria andAgentDeptEqualTo(BigDecimal value) {
            addCriterion("AGENT_DEPT =", value, "agentDept");
            return (Criteria) this;
        }

        public Criteria andAgentDeptNotEqualTo(BigDecimal value) {
            addCriterion("AGENT_DEPT <>", value, "agentDept");
            return (Criteria) this;
        }

        public Criteria andAgentDeptGreaterThan(BigDecimal value) {
            addCriterion("AGENT_DEPT >", value, "agentDept");
            return (Criteria) this;
        }

        public Criteria andAgentDeptGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AGENT_DEPT >=", value, "agentDept");
            return (Criteria) this;
        }

        public Criteria andAgentDeptLessThan(BigDecimal value) {
            addCriterion("AGENT_DEPT <", value, "agentDept");
            return (Criteria) this;
        }

        public Criteria andAgentDeptLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AGENT_DEPT <=", value, "agentDept");
            return (Criteria) this;
        }

        public Criteria andAgentDeptIn(List<BigDecimal> values) {
            addCriterion("AGENT_DEPT in", values, "agentDept");
            return (Criteria) this;
        }

        public Criteria andAgentDeptNotIn(List<BigDecimal> values) {
            addCriterion("AGENT_DEPT not in", values, "agentDept");
            return (Criteria) this;
        }

        public Criteria andAgentDeptBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AGENT_DEPT between", value1, value2, "agentDept");
            return (Criteria) this;
        }

        public Criteria andAgentDeptNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AGENT_DEPT not between", value1, value2, "agentDept");
            return (Criteria) this;
        }

        public Criteria andSuppTypeIsNull() {
            addCriterion("SUPP_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSuppTypeIsNotNull() {
            addCriterion("SUPP_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSuppTypeEqualTo(String value) {
            addCriterion("SUPP_TYPE =", value, "suppType");
            return (Criteria) this;
        }

        public Criteria andSuppTypeNotEqualTo(String value) {
            addCriterion("SUPP_TYPE <>", value, "suppType");
            return (Criteria) this;
        }

        public Criteria andSuppTypeGreaterThan(String value) {
            addCriterion("SUPP_TYPE >", value, "suppType");
            return (Criteria) this;
        }

        public Criteria andSuppTypeGreaterThanOrEqualTo(String value) {
            addCriterion("SUPP_TYPE >=", value, "suppType");
            return (Criteria) this;
        }

        public Criteria andSuppTypeLessThan(String value) {
            addCriterion("SUPP_TYPE <", value, "suppType");
            return (Criteria) this;
        }

        public Criteria andSuppTypeLessThanOrEqualTo(String value) {
            addCriterion("SUPP_TYPE <=", value, "suppType");
            return (Criteria) this;
        }

        public Criteria andSuppTypeLike(String value) {
            addCriterion("SUPP_TYPE like", value, "suppType");
            return (Criteria) this;
        }

        public Criteria andSuppTypeNotLike(String value) {
            addCriterion("SUPP_TYPE not like", value, "suppType");
            return (Criteria) this;
        }

        public Criteria andSuppTypeIn(List<String> values) {
            addCriterion("SUPP_TYPE in", values, "suppType");
            return (Criteria) this;
        }

        public Criteria andSuppTypeNotIn(List<String> values) {
            addCriterion("SUPP_TYPE not in", values, "suppType");
            return (Criteria) this;
        }

        public Criteria andSuppTypeBetween(String value1, String value2) {
            addCriterion("SUPP_TYPE between", value1, value2, "suppType");
            return (Criteria) this;
        }

        public Criteria andSuppTypeNotBetween(String value1, String value2) {
            addCriterion("SUPP_TYPE not between", value1, value2, "suppType");
            return (Criteria) this;
        }

        public Criteria andSuppDeptIsNull() {
            addCriterion("SUPP_DEPT is null");
            return (Criteria) this;
        }

        public Criteria andSuppDeptIsNotNull() {
            addCriterion("SUPP_DEPT is not null");
            return (Criteria) this;
        }

        public Criteria andSuppDeptEqualTo(BigDecimal value) {
            addCriterion("SUPP_DEPT =", value, "suppDept");
            return (Criteria) this;
        }

        public Criteria andSuppDeptNotEqualTo(BigDecimal value) {
            addCriterion("SUPP_DEPT <>", value, "suppDept");
            return (Criteria) this;
        }

        public Criteria andSuppDeptGreaterThan(BigDecimal value) {
            addCriterion("SUPP_DEPT >", value, "suppDept");
            return (Criteria) this;
        }

        public Criteria andSuppDeptGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPP_DEPT >=", value, "suppDept");
            return (Criteria) this;
        }

        public Criteria andSuppDeptLessThan(BigDecimal value) {
            addCriterion("SUPP_DEPT <", value, "suppDept");
            return (Criteria) this;
        }

        public Criteria andSuppDeptLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPP_DEPT <=", value, "suppDept");
            return (Criteria) this;
        }

        public Criteria andSuppDeptIn(List<BigDecimal> values) {
            addCriterion("SUPP_DEPT in", values, "suppDept");
            return (Criteria) this;
        }

        public Criteria andSuppDeptNotIn(List<BigDecimal> values) {
            addCriterion("SUPP_DEPT not in", values, "suppDept");
            return (Criteria) this;
        }

        public Criteria andSuppDeptBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPP_DEPT between", value1, value2, "suppDept");
            return (Criteria) this;
        }

        public Criteria andSuppDeptNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPP_DEPT not between", value1, value2, "suppDept");
            return (Criteria) this;
        }

        public Criteria andRealitySuppDeptIsNull() {
            addCriterion("REALITY_SUPP_DEPT is null");
            return (Criteria) this;
        }

        public Criteria andRealitySuppDeptIsNotNull() {
            addCriterion("REALITY_SUPP_DEPT is not null");
            return (Criteria) this;
        }

        public Criteria andRealitySuppDeptEqualTo(BigDecimal value) {
            addCriterion("REALITY_SUPP_DEPT =", value, "realitySuppDept");
            return (Criteria) this;
        }

        public Criteria andRealitySuppDeptNotEqualTo(BigDecimal value) {
            addCriterion("REALITY_SUPP_DEPT <>", value, "realitySuppDept");
            return (Criteria) this;
        }

        public Criteria andRealitySuppDeptGreaterThan(BigDecimal value) {
            addCriterion("REALITY_SUPP_DEPT >", value, "realitySuppDept");
            return (Criteria) this;
        }

        public Criteria andRealitySuppDeptGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REALITY_SUPP_DEPT >=", value, "realitySuppDept");
            return (Criteria) this;
        }

        public Criteria andRealitySuppDeptLessThan(BigDecimal value) {
            addCriterion("REALITY_SUPP_DEPT <", value, "realitySuppDept");
            return (Criteria) this;
        }

        public Criteria andRealitySuppDeptLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REALITY_SUPP_DEPT <=", value, "realitySuppDept");
            return (Criteria) this;
        }

        public Criteria andRealitySuppDeptIn(List<BigDecimal> values) {
            addCriterion("REALITY_SUPP_DEPT in", values, "realitySuppDept");
            return (Criteria) this;
        }

        public Criteria andRealitySuppDeptNotIn(List<BigDecimal> values) {
            addCriterion("REALITY_SUPP_DEPT not in", values, "realitySuppDept");
            return (Criteria) this;
        }

        public Criteria andRealitySuppDeptBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REALITY_SUPP_DEPT between", value1, value2, "realitySuppDept");
            return (Criteria) this;
        }

        public Criteria andRealitySuppDeptNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REALITY_SUPP_DEPT not between", value1, value2, "realitySuppDept");
            return (Criteria) this;
        }

        public Criteria andAgentOweTicketIsNull() {
            addCriterion("AGENT_OWE_TICKET is null");
            return (Criteria) this;
        }

        public Criteria andAgentOweTicketIsNotNull() {
            addCriterion("AGENT_OWE_TICKET is not null");
            return (Criteria) this;
        }

        public Criteria andAgentOweTicketEqualTo(BigDecimal value) {
            addCriterion("AGENT_OWE_TICKET =", value, "agentOweTicket");
            return (Criteria) this;
        }

        public Criteria andAgentOweTicketNotEqualTo(BigDecimal value) {
            addCriterion("AGENT_OWE_TICKET <>", value, "agentOweTicket");
            return (Criteria) this;
        }

        public Criteria andAgentOweTicketGreaterThan(BigDecimal value) {
            addCriterion("AGENT_OWE_TICKET >", value, "agentOweTicket");
            return (Criteria) this;
        }

        public Criteria andAgentOweTicketGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AGENT_OWE_TICKET >=", value, "agentOweTicket");
            return (Criteria) this;
        }

        public Criteria andAgentOweTicketLessThan(BigDecimal value) {
            addCriterion("AGENT_OWE_TICKET <", value, "agentOweTicket");
            return (Criteria) this;
        }

        public Criteria andAgentOweTicketLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AGENT_OWE_TICKET <=", value, "agentOweTicket");
            return (Criteria) this;
        }

        public Criteria andAgentOweTicketIn(List<BigDecimal> values) {
            addCriterion("AGENT_OWE_TICKET in", values, "agentOweTicket");
            return (Criteria) this;
        }

        public Criteria andAgentOweTicketNotIn(List<BigDecimal> values) {
            addCriterion("AGENT_OWE_TICKET not in", values, "agentOweTicket");
            return (Criteria) this;
        }

        public Criteria andAgentOweTicketBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AGENT_OWE_TICKET between", value1, value2, "agentOweTicket");
            return (Criteria) this;
        }

        public Criteria andAgentOweTicketNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AGENT_OWE_TICKET not between", value1, value2, "agentOweTicket");
            return (Criteria) this;
        }

        public Criteria andSuppTicketIsNull() {
            addCriterion("SUPP_TICKET is null");
            return (Criteria) this;
        }

        public Criteria andSuppTicketIsNotNull() {
            addCriterion("SUPP_TICKET is not null");
            return (Criteria) this;
        }

        public Criteria andSuppTicketEqualTo(BigDecimal value) {
            addCriterion("SUPP_TICKET =", value, "suppTicket");
            return (Criteria) this;
        }

        public Criteria andSuppTicketNotEqualTo(BigDecimal value) {
            addCriterion("SUPP_TICKET <>", value, "suppTicket");
            return (Criteria) this;
        }

        public Criteria andSuppTicketGreaterThan(BigDecimal value) {
            addCriterion("SUPP_TICKET >", value, "suppTicket");
            return (Criteria) this;
        }

        public Criteria andSuppTicketGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPP_TICKET >=", value, "suppTicket");
            return (Criteria) this;
        }

        public Criteria andSuppTicketLessThan(BigDecimal value) {
            addCriterion("SUPP_TICKET <", value, "suppTicket");
            return (Criteria) this;
        }

        public Criteria andSuppTicketLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPP_TICKET <=", value, "suppTicket");
            return (Criteria) this;
        }

        public Criteria andSuppTicketIn(List<BigDecimal> values) {
            addCriterion("SUPP_TICKET in", values, "suppTicket");
            return (Criteria) this;
        }

        public Criteria andSuppTicketNotIn(List<BigDecimal> values) {
            addCriterion("SUPP_TICKET not in", values, "suppTicket");
            return (Criteria) this;
        }

        public Criteria andSuppTicketBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPP_TICKET between", value1, value2, "suppTicket");
            return (Criteria) this;
        }

        public Criteria andSuppTicketNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPP_TICKET not between", value1, value2, "suppTicket");
            return (Criteria) this;
        }

        public Criteria andSubtractAmtIsNull() {
            addCriterion("SUBTRACT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andSubtractAmtIsNotNull() {
            addCriterion("SUBTRACT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andSubtractAmtEqualTo(BigDecimal value) {
            addCriterion("SUBTRACT_AMT =", value, "subtractAmt");
            return (Criteria) this;
        }

        public Criteria andSubtractAmtNotEqualTo(BigDecimal value) {
            addCriterion("SUBTRACT_AMT <>", value, "subtractAmt");
            return (Criteria) this;
        }

        public Criteria andSubtractAmtGreaterThan(BigDecimal value) {
            addCriterion("SUBTRACT_AMT >", value, "subtractAmt");
            return (Criteria) this;
        }

        public Criteria andSubtractAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUBTRACT_AMT >=", value, "subtractAmt");
            return (Criteria) this;
        }

        public Criteria andSubtractAmtLessThan(BigDecimal value) {
            addCriterion("SUBTRACT_AMT <", value, "subtractAmt");
            return (Criteria) this;
        }

        public Criteria andSubtractAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUBTRACT_AMT <=", value, "subtractAmt");
            return (Criteria) this;
        }

        public Criteria andSubtractAmtIn(List<BigDecimal> values) {
            addCriterion("SUBTRACT_AMT in", values, "subtractAmt");
            return (Criteria) this;
        }

        public Criteria andSubtractAmtNotIn(List<BigDecimal> values) {
            addCriterion("SUBTRACT_AMT not in", values, "subtractAmt");
            return (Criteria) this;
        }

        public Criteria andSubtractAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUBTRACT_AMT between", value1, value2, "subtractAmt");
            return (Criteria) this;
        }

        public Criteria andSubtractAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUBTRACT_AMT not between", value1, value2, "subtractAmt");
            return (Criteria) this;
        }

        public Criteria andMigrationPlatformIsNull() {
            addCriterion("MIGRATION_PLATFORM is null");
            return (Criteria) this;
        }

        public Criteria andMigrationPlatformIsNotNull() {
            addCriterion("MIGRATION_PLATFORM is not null");
            return (Criteria) this;
        }

        public Criteria andMigrationPlatformEqualTo(String value) {
            addCriterion("MIGRATION_PLATFORM =", value, "migrationPlatform");
            return (Criteria) this;
        }

        public Criteria andMigrationPlatformNotEqualTo(String value) {
            addCriterion("MIGRATION_PLATFORM <>", value, "migrationPlatform");
            return (Criteria) this;
        }

        public Criteria andMigrationPlatformGreaterThan(String value) {
            addCriterion("MIGRATION_PLATFORM >", value, "migrationPlatform");
            return (Criteria) this;
        }

        public Criteria andMigrationPlatformGreaterThanOrEqualTo(String value) {
            addCriterion("MIGRATION_PLATFORM >=", value, "migrationPlatform");
            return (Criteria) this;
        }

        public Criteria andMigrationPlatformLessThan(String value) {
            addCriterion("MIGRATION_PLATFORM <", value, "migrationPlatform");
            return (Criteria) this;
        }

        public Criteria andMigrationPlatformLessThanOrEqualTo(String value) {
            addCriterion("MIGRATION_PLATFORM <=", value, "migrationPlatform");
            return (Criteria) this;
        }

        public Criteria andMigrationPlatformLike(String value) {
            addCriterion("MIGRATION_PLATFORM like", value, "migrationPlatform");
            return (Criteria) this;
        }

        public Criteria andMigrationPlatformNotLike(String value) {
            addCriterion("MIGRATION_PLATFORM not like", value, "migrationPlatform");
            return (Criteria) this;
        }

        public Criteria andMigrationPlatformIn(List<String> values) {
            addCriterion("MIGRATION_PLATFORM in", values, "migrationPlatform");
            return (Criteria) this;
        }

        public Criteria andMigrationPlatformNotIn(List<String> values) {
            addCriterion("MIGRATION_PLATFORM not in", values, "migrationPlatform");
            return (Criteria) this;
        }

        public Criteria andMigrationPlatformBetween(String value1, String value2) {
            addCriterion("MIGRATION_PLATFORM between", value1, value2, "migrationPlatform");
            return (Criteria) this;
        }

        public Criteria andMigrationPlatformNotBetween(String value1, String value2) {
            addCriterion("MIGRATION_PLATFORM not between", value1, value2, "migrationPlatform");
            return (Criteria) this;
        }

        public Criteria andContractStatusIsNull() {
            addCriterion("CONTRACT_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andContractStatusIsNotNull() {
            addCriterion("CONTRACT_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andContractStatusEqualTo(BigDecimal value) {
            addCriterion("CONTRACT_STATUS =", value, "contractStatus");
            return (Criteria) this;
        }

        public Criteria andContractStatusNotEqualTo(BigDecimal value) {
            addCriterion("CONTRACT_STATUS <>", value, "contractStatus");
            return (Criteria) this;
        }

        public Criteria andContractStatusGreaterThan(BigDecimal value) {
            addCriterion("CONTRACT_STATUS >", value, "contractStatus");
            return (Criteria) this;
        }

        public Criteria andContractStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CONTRACT_STATUS >=", value, "contractStatus");
            return (Criteria) this;
        }

        public Criteria andContractStatusLessThan(BigDecimal value) {
            addCriterion("CONTRACT_STATUS <", value, "contractStatus");
            return (Criteria) this;
        }

        public Criteria andContractStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CONTRACT_STATUS <=", value, "contractStatus");
            return (Criteria) this;
        }

        public Criteria andContractStatusIn(List<BigDecimal> values) {
            addCriterion("CONTRACT_STATUS in", values, "contractStatus");
            return (Criteria) this;
        }

        public Criteria andContractStatusNotIn(List<BigDecimal> values) {
            addCriterion("CONTRACT_STATUS not in", values, "contractStatus");
            return (Criteria) this;
        }

        public Criteria andContractStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CONTRACT_STATUS between", value1, value2, "contractStatus");
            return (Criteria) this;
        }

        public Criteria andContractStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CONTRACT_STATUS not between", value1, value2, "contractStatus");
            return (Criteria) this;
        }

        public Criteria andRefundAmtStatusIsNull() {
            addCriterion("REFUND_AMT_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andRefundAmtStatusIsNotNull() {
            addCriterion("REFUND_AMT_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andRefundAmtStatusEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMT_STATUS =", value, "refundAmtStatus");
            return (Criteria) this;
        }

        public Criteria andRefundAmtStatusNotEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMT_STATUS <>", value, "refundAmtStatus");
            return (Criteria) this;
        }

        public Criteria andRefundAmtStatusGreaterThan(BigDecimal value) {
            addCriterion("REFUND_AMT_STATUS >", value, "refundAmtStatus");
            return (Criteria) this;
        }

        public Criteria andRefundAmtStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMT_STATUS >=", value, "refundAmtStatus");
            return (Criteria) this;
        }

        public Criteria andRefundAmtStatusLessThan(BigDecimal value) {
            addCriterion("REFUND_AMT_STATUS <", value, "refundAmtStatus");
            return (Criteria) this;
        }

        public Criteria andRefundAmtStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMT_STATUS <=", value, "refundAmtStatus");
            return (Criteria) this;
        }

        public Criteria andRefundAmtStatusIn(List<BigDecimal> values) {
            addCriterion("REFUND_AMT_STATUS in", values, "refundAmtStatus");
            return (Criteria) this;
        }

        public Criteria andRefundAmtStatusNotIn(List<BigDecimal> values) {
            addCriterion("REFUND_AMT_STATUS not in", values, "refundAmtStatus");
            return (Criteria) this;
        }

        public Criteria andRefundAmtStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_AMT_STATUS between", value1, value2, "refundAmtStatus");
            return (Criteria) this;
        }

        public Criteria andRefundAmtStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_AMT_STATUS not between", value1, value2, "refundAmtStatus");
            return (Criteria) this;
        }

        public Criteria andRefundAmtDeadlineIsNull() {
            addCriterion("REFUND_AMT_DEADLINE is null");
            return (Criteria) this;
        }

        public Criteria andRefundAmtDeadlineIsNotNull() {
            addCriterion("REFUND_AMT_DEADLINE is not null");
            return (Criteria) this;
        }

        public Criteria andRefundAmtDeadlineEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMT_DEADLINE =", value, "refundAmtDeadline");
            return (Criteria) this;
        }

        public Criteria andRefundAmtDeadlineNotEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMT_DEADLINE <>", value, "refundAmtDeadline");
            return (Criteria) this;
        }

        public Criteria andRefundAmtDeadlineGreaterThan(BigDecimal value) {
            addCriterion("REFUND_AMT_DEADLINE >", value, "refundAmtDeadline");
            return (Criteria) this;
        }

        public Criteria andRefundAmtDeadlineGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMT_DEADLINE >=", value, "refundAmtDeadline");
            return (Criteria) this;
        }

        public Criteria andRefundAmtDeadlineLessThan(BigDecimal value) {
            addCriterion("REFUND_AMT_DEADLINE <", value, "refundAmtDeadline");
            return (Criteria) this;
        }

        public Criteria andRefundAmtDeadlineLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMT_DEADLINE <=", value, "refundAmtDeadline");
            return (Criteria) this;
        }

        public Criteria andRefundAmtDeadlineIn(List<BigDecimal> values) {
            addCriterion("REFUND_AMT_DEADLINE in", values, "refundAmtDeadline");
            return (Criteria) this;
        }

        public Criteria andRefundAmtDeadlineNotIn(List<BigDecimal> values) {
            addCriterion("REFUND_AMT_DEADLINE not in", values, "refundAmtDeadline");
            return (Criteria) this;
        }

        public Criteria andRefundAmtDeadlineBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_AMT_DEADLINE between", value1, value2, "refundAmtDeadline");
            return (Criteria) this;
        }

        public Criteria andRefundAmtDeadlineNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_AMT_DEADLINE not between", value1, value2, "refundAmtDeadline");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIsNull() {
            addCriterion("APPROVE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIsNotNull() {
            addCriterion("APPROVE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andApproveTimeEqualTo(Date value) {
            addCriterion("APPROVE_TIME =", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotEqualTo(Date value) {
            addCriterion("APPROVE_TIME <>", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeGreaterThan(Date value) {
            addCriterion("APPROVE_TIME >", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("APPROVE_TIME >=", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeLessThan(Date value) {
            addCriterion("APPROVE_TIME <", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeLessThanOrEqualTo(Date value) {
            addCriterion("APPROVE_TIME <=", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIn(List<Date> values) {
            addCriterion("APPROVE_TIME in", values, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotIn(List<Date> values) {
            addCriterion("APPROVE_TIME not in", values, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeBetween(Date value1, Date value2) {
            addCriterion("APPROVE_TIME between", value1, value2, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotBetween(Date value1, Date value2) {
            addCriterion("APPROVE_TIME not between", value1, value2, "approveTime");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusIsNull() {
            addCriterion("CLO_REVIEW_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusIsNotNull() {
            addCriterion("CLO_REVIEW_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS =", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS <>", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusGreaterThan(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS >", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS >=", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusLessThan(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS <", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS <=", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusIn(List<BigDecimal> values) {
            addCriterion("CLO_REVIEW_STATUS in", values, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotIn(List<BigDecimal> values) {
            addCriterion("CLO_REVIEW_STATUS not in", values, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_REVIEW_STATUS between", value1, value2, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_REVIEW_STATUS not between", value1, value2, "cloReviewStatus");
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

        public Criteria andQuitBusIdIsNull() {
            addCriterion("QUIT_BUS_ID is null");
            return (Criteria) this;
        }

        public Criteria andQuitBusIdIsNotNull() {
            addCriterion("QUIT_BUS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andQuitBusIdEqualTo(String value) {
            addCriterion("QUIT_BUS_ID =", value, "quitBusId");
            return (Criteria) this;
        }

        public Criteria andQuitBusIdNotEqualTo(String value) {
            addCriterion("QUIT_BUS_ID <>", value, "quitBusId");
            return (Criteria) this;
        }

        public Criteria andQuitBusIdGreaterThan(String value) {
            addCriterion("QUIT_BUS_ID >", value, "quitBusId");
            return (Criteria) this;
        }

        public Criteria andQuitBusIdGreaterThanOrEqualTo(String value) {
            addCriterion("QUIT_BUS_ID >=", value, "quitBusId");
            return (Criteria) this;
        }

        public Criteria andQuitBusIdLessThan(String value) {
            addCriterion("QUIT_BUS_ID <", value, "quitBusId");
            return (Criteria) this;
        }

        public Criteria andQuitBusIdLessThanOrEqualTo(String value) {
            addCriterion("QUIT_BUS_ID <=", value, "quitBusId");
            return (Criteria) this;
        }

        public Criteria andQuitBusIdLike(String value) {
            addCriterion("QUIT_BUS_ID like", value, "quitBusId");
            return (Criteria) this;
        }

        public Criteria andQuitBusIdNotLike(String value) {
            addCriterion("QUIT_BUS_ID not like", value, "quitBusId");
            return (Criteria) this;
        }

        public Criteria andQuitBusIdIn(List<String> values) {
            addCriterion("QUIT_BUS_ID in", values, "quitBusId");
            return (Criteria) this;
        }

        public Criteria andQuitBusIdNotIn(List<String> values) {
            addCriterion("QUIT_BUS_ID not in", values, "quitBusId");
            return (Criteria) this;
        }

        public Criteria andQuitBusIdBetween(String value1, String value2) {
            addCriterion("QUIT_BUS_ID between", value1, value2, "quitBusId");
            return (Criteria) this;
        }

        public Criteria andQuitBusIdNotBetween(String value1, String value2) {
            addCriterion("QUIT_BUS_ID not between", value1, value2, "quitBusId");
            return (Criteria) this;
        }

        public Criteria andAppRefundIsNull() {
            addCriterion("APP_REFUND is null");
            return (Criteria) this;
        }

        public Criteria andAppRefundIsNotNull() {
            addCriterion("APP_REFUND is not null");
            return (Criteria) this;
        }

        public Criteria andAppRefundEqualTo(BigDecimal value) {
            addCriterion("APP_REFUND =", value, "appRefund");
            return (Criteria) this;
        }

        public Criteria andAppRefundNotEqualTo(BigDecimal value) {
            addCriterion("APP_REFUND <>", value, "appRefund");
            return (Criteria) this;
        }

        public Criteria andAppRefundGreaterThan(BigDecimal value) {
            addCriterion("APP_REFUND >", value, "appRefund");
            return (Criteria) this;
        }

        public Criteria andAppRefundGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("APP_REFUND >=", value, "appRefund");
            return (Criteria) this;
        }

        public Criteria andAppRefundLessThan(BigDecimal value) {
            addCriterion("APP_REFUND <", value, "appRefund");
            return (Criteria) this;
        }

        public Criteria andAppRefundLessThanOrEqualTo(BigDecimal value) {
            addCriterion("APP_REFUND <=", value, "appRefund");
            return (Criteria) this;
        }

        public Criteria andAppRefundIn(List<BigDecimal> values) {
            addCriterion("APP_REFUND in", values, "appRefund");
            return (Criteria) this;
        }

        public Criteria andAppRefundNotIn(List<BigDecimal> values) {
            addCriterion("APP_REFUND not in", values, "appRefund");
            return (Criteria) this;
        }

        public Criteria andAppRefundBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APP_REFUND between", value1, value2, "appRefund");
            return (Criteria) this;
        }

        public Criteria andAppRefundNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APP_REFUND not between", value1, value2, "appRefund");
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