package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PosCheckExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    protected String innerJoinDepartment;

    public void setInnerJoinDepartment(String innerJoinDepartment, String corId) {
        if (Objects.equals("south", innerJoinDepartment )|| Objects.equals("north", innerJoinDepartment)) {
            this.innerJoinDepartment = " INNER JOIN A_AGENT AGENT ON N.AGENT_ID = AGENT.ID AND AGENT.AG_DOC_DISTRICT= "+corId;
        } else if (innerJoinDepartment.contains("south") || innerJoinDepartment.contains("north")) {
            this.innerJoinDepartment = " INNER JOIN A_AGENT AGENT ON N.AGENT_ID = AGENT.ID AND AGENT.AG_DOC_PRO= "+corId;
        }
    }

    public String getInnerJoinDepartment(){
        return innerJoinDepartment;
    }

    public PosCheckExample() {
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

        public Criteria andAgentPidIsNull() {
            addCriterion("AGENT_PID is null");
            return (Criteria) this;
        }

        public Criteria andAgentPidIsNotNull() {
            addCriterion("AGENT_PID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentPidEqualTo(String value) {
            addCriterion("AGENT_PID =", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotEqualTo(String value) {
            addCriterion("AGENT_PID <>", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidGreaterThan(String value) {
            addCriterion("AGENT_PID >", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_PID >=", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLessThan(String value) {
            addCriterion("AGENT_PID <", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLessThanOrEqualTo(String value) {
            addCriterion("AGENT_PID <=", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLike(String value) {
            addCriterion("AGENT_PID like", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotLike(String value) {
            addCriterion("AGENT_PID not like", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidIn(List<String> values) {
            addCriterion("AGENT_PID in", values, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotIn(List<String> values) {
            addCriterion("AGENT_PID not in", values, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidBetween(String value1, String value2) {
            addCriterion("AGENT_PID between", value1, value2, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotBetween(String value1, String value2) {
            addCriterion("AGENT_PID not between", value1, value2, "agentPid");
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

        public Criteria andCheckDateSIsNull() {
            addCriterion("CHECK_DATE_S is null");
            return (Criteria) this;
        }

        public Criteria andCheckDateSIsNotNull() {
            addCriterion("CHECK_DATE_S is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDateSEqualTo(String value) {
            addCriterion("CHECK_DATE_S =", value, "checkDateS");
            return (Criteria) this;
        }

        public Criteria andCheckDateSNotEqualTo(String value) {
            addCriterion("CHECK_DATE_S <>", value, "checkDateS");
            return (Criteria) this;
        }

        public Criteria andCheckDateSGreaterThan(String value) {
            addCriterion("CHECK_DATE_S >", value, "checkDateS");
            return (Criteria) this;
        }

        public Criteria andCheckDateSGreaterThanOrEqualTo(String value) {
            addCriterion("CHECK_DATE_S >=", value, "checkDateS");
            return (Criteria) this;
        }

        public Criteria andCheckDateSLessThan(String value) {
            addCriterion("CHECK_DATE_S <", value, "checkDateS");
            return (Criteria) this;
        }

        public Criteria andCheckDateSLessThanOrEqualTo(String value) {
            addCriterion("CHECK_DATE_S <=", value, "checkDateS");
            return (Criteria) this;
        }

        public Criteria andCheckDateSLike(String value) {
            addCriterion("CHECK_DATE_S like", value, "checkDateS");
            return (Criteria) this;
        }

        public Criteria andCheckDateSNotLike(String value) {
            addCriterion("CHECK_DATE_S not like", value, "checkDateS");
            return (Criteria) this;
        }

        public Criteria andCheckDateSIn(List<String> values) {
            addCriterion("CHECK_DATE_S in", values, "checkDateS");
            return (Criteria) this;
        }

        public Criteria andCheckDateSNotIn(List<String> values) {
            addCriterion("CHECK_DATE_S not in", values, "checkDateS");
            return (Criteria) this;
        }

        public Criteria andCheckDateSBetween(String value1, String value2) {
            addCriterion("CHECK_DATE_S between", value1, value2, "checkDateS");
            return (Criteria) this;
        }

        public Criteria andCheckDateSNotBetween(String value1, String value2) {
            addCriterion("CHECK_DATE_S not between", value1, value2, "checkDateS");
            return (Criteria) this;
        }

        public Criteria andCheckDateEIsNull() {
            addCriterion("CHECK_DATE_E is null");
            return (Criteria) this;
        }

        public Criteria andCheckDateEIsNotNull() {
            addCriterion("CHECK_DATE_E is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDateEEqualTo(String value) {
            addCriterion("CHECK_DATE_E =", value, "checkDateE");
            return (Criteria) this;
        }

        public Criteria andCheckDateENotEqualTo(String value) {
            addCriterion("CHECK_DATE_E <>", value, "checkDateE");
            return (Criteria) this;
        }

        public Criteria andCheckDateEGreaterThan(String value) {
            addCriterion("CHECK_DATE_E >", value, "checkDateE");
            return (Criteria) this;
        }

        public Criteria andCheckDateEGreaterThanOrEqualTo(String value) {
            addCriterion("CHECK_DATE_E >=", value, "checkDateE");
            return (Criteria) this;
        }

        public Criteria andCheckDateELessThan(String value) {
            addCriterion("CHECK_DATE_E <", value, "checkDateE");
            return (Criteria) this;
        }

        public Criteria andCheckDateELessThanOrEqualTo(String value) {
            addCriterion("CHECK_DATE_E <=", value, "checkDateE");
            return (Criteria) this;
        }

        public Criteria andCheckDateELike(String value) {
            addCriterion("CHECK_DATE_E like", value, "checkDateE");
            return (Criteria) this;
        }

        public Criteria andCheckDateENotLike(String value) {
            addCriterion("CHECK_DATE_E not like", value, "checkDateE");
            return (Criteria) this;
        }

        public Criteria andCheckDateEIn(List<String> values) {
            addCriterion("CHECK_DATE_E in", values, "checkDateE");
            return (Criteria) this;
        }

        public Criteria andCheckDateENotIn(List<String> values) {
            addCriterion("CHECK_DATE_E not in", values, "checkDateE");
            return (Criteria) this;
        }

        public Criteria andCheckDateEBetween(String value1, String value2) {
            addCriterion("CHECK_DATE_E between", value1, value2, "checkDateE");
            return (Criteria) this;
        }

        public Criteria andCheckDateENotBetween(String value1, String value2) {
            addCriterion("CHECK_DATE_E not between", value1, value2, "checkDateE");
            return (Criteria) this;
        }

        public Criteria andTotalAmtIsNull() {
            addCriterion("TOTAL_AMT is null");
            return (Criteria) this;
        }

        public Criteria andTotalAmtIsNotNull() {
            addCriterion("TOTAL_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andTotalAmtEqualTo(BigDecimal value) {
            addCriterion("TOTAL_AMT =", value, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtNotEqualTo(BigDecimal value) {
            addCriterion("TOTAL_AMT <>", value, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtGreaterThan(BigDecimal value) {
            addCriterion("TOTAL_AMT >", value, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TOTAL_AMT >=", value, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtLessThan(BigDecimal value) {
            addCriterion("TOTAL_AMT <", value, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TOTAL_AMT <=", value, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtIn(List<BigDecimal> values) {
            addCriterion("TOTAL_AMT in", values, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtNotIn(List<BigDecimal> values) {
            addCriterion("TOTAL_AMT not in", values, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TOTAL_AMT between", value1, value2, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TOTAL_AMT not between", value1, value2, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andPosOrdersIsNull() {
            addCriterion("POS_ORDERS is null");
            return (Criteria) this;
        }

        public Criteria andPosOrdersIsNotNull() {
            addCriterion("POS_ORDERS is not null");
            return (Criteria) this;
        }

        public Criteria andPosOrdersEqualTo(BigDecimal value) {
            addCriterion("POS_ORDERS =", value, "posOrders");
            return (Criteria) this;
        }

        public Criteria andPosOrdersNotEqualTo(BigDecimal value) {
            addCriterion("POS_ORDERS <>", value, "posOrders");
            return (Criteria) this;
        }

        public Criteria andPosOrdersGreaterThan(BigDecimal value) {
            addCriterion("POS_ORDERS >", value, "posOrders");
            return (Criteria) this;
        }

        public Criteria andPosOrdersGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_ORDERS >=", value, "posOrders");
            return (Criteria) this;
        }

        public Criteria andPosOrdersLessThan(BigDecimal value) {
            addCriterion("POS_ORDERS <", value, "posOrders");
            return (Criteria) this;
        }

        public Criteria andPosOrdersLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_ORDERS <=", value, "posOrders");
            return (Criteria) this;
        }

        public Criteria andPosOrdersIn(List<BigDecimal> values) {
            addCriterion("POS_ORDERS in", values, "posOrders");
            return (Criteria) this;
        }

        public Criteria andPosOrdersNotIn(List<BigDecimal> values) {
            addCriterion("POS_ORDERS not in", values, "posOrders");
            return (Criteria) this;
        }

        public Criteria andPosOrdersBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_ORDERS between", value1, value2, "posOrders");
            return (Criteria) this;
        }

        public Criteria andPosOrdersNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_ORDERS not between", value1, value2, "posOrders");
            return (Criteria) this;
        }

        public Criteria andProfitScaleIsNull() {
            addCriterion("PROFIT_SCALE is null");
            return (Criteria) this;
        }

        public Criteria andProfitScaleIsNotNull() {
            addCriterion("PROFIT_SCALE is not null");
            return (Criteria) this;
        }

        public Criteria andProfitScaleEqualTo(BigDecimal value) {
            addCriterion("PROFIT_SCALE =", value, "profitScale");
            return (Criteria) this;
        }

        public Criteria andProfitScaleNotEqualTo(BigDecimal value) {
            addCriterion("PROFIT_SCALE <>", value, "profitScale");
            return (Criteria) this;
        }

        public Criteria andProfitScaleGreaterThan(BigDecimal value) {
            addCriterion("PROFIT_SCALE >", value, "profitScale");
            return (Criteria) this;
        }

        public Criteria andProfitScaleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_SCALE >=", value, "profitScale");
            return (Criteria) this;
        }

        public Criteria andProfitScaleLessThan(BigDecimal value) {
            addCriterion("PROFIT_SCALE <", value, "profitScale");
            return (Criteria) this;
        }

        public Criteria andProfitScaleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_SCALE <=", value, "profitScale");
            return (Criteria) this;
        }

        public Criteria andProfitScaleIn(List<BigDecimal> values) {
            addCriterion("PROFIT_SCALE in", values, "profitScale");
            return (Criteria) this;
        }

        public Criteria andProfitScaleNotIn(List<BigDecimal> values) {
            addCriterion("PROFIT_SCALE not in", values, "profitScale");
            return (Criteria) this;
        }

        public Criteria andProfitScaleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_SCALE between", value1, value2, "profitScale");
            return (Criteria) this;
        }

        public Criteria andProfitScaleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_SCALE not between", value1, value2, "profitScale");
            return (Criteria) this;
        }

        public Criteria andAppDateIsNull() {
            addCriterion("APP_DATE is null");
            return (Criteria) this;
        }

        public Criteria andAppDateIsNotNull() {
            addCriterion("APP_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andAppDateEqualTo(String value) {
            addCriterion("APP_DATE =", value, "appDate");
            return (Criteria) this;
        }

        public Criteria andAppDateNotEqualTo(String value) {
            addCriterion("APP_DATE <>", value, "appDate");
            return (Criteria) this;
        }

        public Criteria andAppDateGreaterThan(String value) {
            addCriterion("APP_DATE >", value, "appDate");
            return (Criteria) this;
        }

        public Criteria andAppDateGreaterThanOrEqualTo(String value) {
            addCriterion("APP_DATE >=", value, "appDate");
            return (Criteria) this;
        }

        public Criteria andAppDateLessThan(String value) {
            addCriterion("APP_DATE <", value, "appDate");
            return (Criteria) this;
        }

        public Criteria andAppDateLessThanOrEqualTo(String value) {
            addCriterion("APP_DATE <=", value, "appDate");
            return (Criteria) this;
        }

        public Criteria andAppDateLike(String value) {
            addCriterion("APP_DATE like", value, "appDate");
            return (Criteria) this;
        }

        public Criteria andAppDateNotLike(String value) {
            addCriterion("APP_DATE not like", value, "appDate");
            return (Criteria) this;
        }

        public Criteria andAppDateIn(List<String> values) {
            addCriterion("APP_DATE in", values, "appDate");
            return (Criteria) this;
        }

        public Criteria andAppDateNotIn(List<String> values) {
            addCriterion("APP_DATE not in", values, "appDate");
            return (Criteria) this;
        }

        public Criteria andAppDateBetween(String value1, String value2) {
            addCriterion("APP_DATE between", value1, value2, "appDate");
            return (Criteria) this;
        }

        public Criteria andAppDateNotBetween(String value1, String value2) {
            addCriterion("APP_DATE not between", value1, value2, "appDate");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNull() {
            addCriterion("CHECK_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("CHECK_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(String value) {
            addCriterion("CHECK_STATUS =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(String value) {
            addCriterion("CHECK_STATUS <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(String value) {
            addCriterion("CHECK_STATUS >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(String value) {
            addCriterion("CHECK_STATUS >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(String value) {
            addCriterion("CHECK_STATUS <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(String value) {
            addCriterion("CHECK_STATUS <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLike(String value) {
            addCriterion("CHECK_STATUS like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotLike(String value) {
            addCriterion("CHECK_STATUS not like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<String> values) {
            addCriterion("CHECK_STATUS in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<String> values) {
            addCriterion("CHECK_STATUS not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(String value1, String value2) {
            addCriterion("CHECK_STATUS between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(String value1, String value2) {
            addCriterion("CHECK_STATUS not between", value1, value2, "checkStatus");
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