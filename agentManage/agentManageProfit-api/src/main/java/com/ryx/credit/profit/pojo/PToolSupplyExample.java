package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PToolSupplyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PToolSupplyExample() {
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

        public Criteria andProfitDateIsNull() {
            addCriterion("PROFIT_DATE is null");
            return (Criteria) this;
        }

        public Criteria andProfitDateIsNotNull() {
            addCriterion("PROFIT_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andProfitDateEqualTo(String value) {
            addCriterion("PROFIT_DATE =", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateNotEqualTo(String value) {
            addCriterion("PROFIT_DATE <>", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateGreaterThan(String value) {
            addCriterion("PROFIT_DATE >", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateGreaterThanOrEqualTo(String value) {
            addCriterion("PROFIT_DATE >=", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateLessThan(String value) {
            addCriterion("PROFIT_DATE <", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateLessThanOrEqualTo(String value) {
            addCriterion("PROFIT_DATE <=", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateLike(String value) {
            addCriterion("PROFIT_DATE like", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateNotLike(String value) {
            addCriterion("PROFIT_DATE not like", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateIn(List<String> values) {
            addCriterion("PROFIT_DATE in", values, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateNotIn(List<String> values) {
            addCriterion("PROFIT_DATE not in", values, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateBetween(String value1, String value2) {
            addCriterion("PROFIT_DATE between", value1, value2, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateNotBetween(String value1, String value2) {
            addCriterion("PROFIT_DATE not between", value1, value2, "profitDate");
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

        public Criteria andParenterAgentNameIsNull() {
            addCriterion("PARENTER_AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andParenterAgentNameIsNotNull() {
            addCriterion("PARENTER_AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andParenterAgentNameEqualTo(String value) {
            addCriterion("PARENTER_AGENT_NAME =", value, "parenterAgentName");
            return (Criteria) this;
        }

        public Criteria andParenterAgentNameNotEqualTo(String value) {
            addCriterion("PARENTER_AGENT_NAME <>", value, "parenterAgentName");
            return (Criteria) this;
        }

        public Criteria andParenterAgentNameGreaterThan(String value) {
            addCriterion("PARENTER_AGENT_NAME >", value, "parenterAgentName");
            return (Criteria) this;
        }

        public Criteria andParenterAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("PARENTER_AGENT_NAME >=", value, "parenterAgentName");
            return (Criteria) this;
        }

        public Criteria andParenterAgentNameLessThan(String value) {
            addCriterion("PARENTER_AGENT_NAME <", value, "parenterAgentName");
            return (Criteria) this;
        }

        public Criteria andParenterAgentNameLessThanOrEqualTo(String value) {
            addCriterion("PARENTER_AGENT_NAME <=", value, "parenterAgentName");
            return (Criteria) this;
        }

        public Criteria andParenterAgentNameLike(String value) {
            addCriterion("PARENTER_AGENT_NAME like", value, "parenterAgentName");
            return (Criteria) this;
        }

        public Criteria andParenterAgentNameNotLike(String value) {
            addCriterion("PARENTER_AGENT_NAME not like", value, "parenterAgentName");
            return (Criteria) this;
        }

        public Criteria andParenterAgentNameIn(List<String> values) {
            addCriterion("PARENTER_AGENT_NAME in", values, "parenterAgentName");
            return (Criteria) this;
        }

        public Criteria andParenterAgentNameNotIn(List<String> values) {
            addCriterion("PARENTER_AGENT_NAME not in", values, "parenterAgentName");
            return (Criteria) this;
        }

        public Criteria andParenterAgentNameBetween(String value1, String value2) {
            addCriterion("PARENTER_AGENT_NAME between", value1, value2, "parenterAgentName");
            return (Criteria) this;
        }

        public Criteria andParenterAgentNameNotBetween(String value1, String value2) {
            addCriterion("PARENTER_AGENT_NAME not between", value1, value2, "parenterAgentName");
            return (Criteria) this;
        }

        public Criteria andParenterAgentIdIsNull() {
            addCriterion("PARENTER_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andParenterAgentIdIsNotNull() {
            addCriterion("PARENTER_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andParenterAgentIdEqualTo(String value) {
            addCriterion("PARENTER_AGENT_ID =", value, "parenterAgentId");
            return (Criteria) this;
        }

        public Criteria andParenterAgentIdNotEqualTo(String value) {
            addCriterion("PARENTER_AGENT_ID <>", value, "parenterAgentId");
            return (Criteria) this;
        }

        public Criteria andParenterAgentIdGreaterThan(String value) {
            addCriterion("PARENTER_AGENT_ID >", value, "parenterAgentId");
            return (Criteria) this;
        }

        public Criteria andParenterAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("PARENTER_AGENT_ID >=", value, "parenterAgentId");
            return (Criteria) this;
        }

        public Criteria andParenterAgentIdLessThan(String value) {
            addCriterion("PARENTER_AGENT_ID <", value, "parenterAgentId");
            return (Criteria) this;
        }

        public Criteria andParenterAgentIdLessThanOrEqualTo(String value) {
            addCriterion("PARENTER_AGENT_ID <=", value, "parenterAgentId");
            return (Criteria) this;
        }

        public Criteria andParenterAgentIdLike(String value) {
            addCriterion("PARENTER_AGENT_ID like", value, "parenterAgentId");
            return (Criteria) this;
        }

        public Criteria andParenterAgentIdNotLike(String value) {
            addCriterion("PARENTER_AGENT_ID not like", value, "parenterAgentId");
            return (Criteria) this;
        }

        public Criteria andParenterAgentIdIn(List<String> values) {
            addCriterion("PARENTER_AGENT_ID in", values, "parenterAgentId");
            return (Criteria) this;
        }

        public Criteria andParenterAgentIdNotIn(List<String> values) {
            addCriterion("PARENTER_AGENT_ID not in", values, "parenterAgentId");
            return (Criteria) this;
        }

        public Criteria andParenterAgentIdBetween(String value1, String value2) {
            addCriterion("PARENTER_AGENT_ID between", value1, value2, "parenterAgentId");
            return (Criteria) this;
        }

        public Criteria andParenterAgentIdNotBetween(String value1, String value2) {
            addCriterion("PARENTER_AGENT_ID not between", value1, value2, "parenterAgentId");
            return (Criteria) this;
        }

        public Criteria andBusCodeIsNull() {
            addCriterion("BUS_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBusCodeIsNotNull() {
            addCriterion("BUS_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBusCodeEqualTo(String value) {
            addCriterion("BUS_CODE =", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotEqualTo(String value) {
            addCriterion("BUS_CODE <>", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeGreaterThan(String value) {
            addCriterion("BUS_CODE >", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_CODE >=", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeLessThan(String value) {
            addCriterion("BUS_CODE <", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeLessThanOrEqualTo(String value) {
            addCriterion("BUS_CODE <=", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeLike(String value) {
            addCriterion("BUS_CODE like", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotLike(String value) {
            addCriterion("BUS_CODE not like", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeIn(List<String> values) {
            addCriterion("BUS_CODE in", values, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotIn(List<String> values) {
            addCriterion("BUS_CODE not in", values, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeBetween(String value1, String value2) {
            addCriterion("BUS_CODE between", value1, value2, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotBetween(String value1, String value2) {
            addCriterion("BUS_CODE not between", value1, value2, "busCode");
            return (Criteria) this;
        }

        public Criteria andNetInDateIsNull() {
            addCriterion("NET_IN_DATE is null");
            return (Criteria) this;
        }

        public Criteria andNetInDateIsNotNull() {
            addCriterion("NET_IN_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andNetInDateEqualTo(String value) {
            addCriterion("NET_IN_DATE =", value, "netInDate");
            return (Criteria) this;
        }

        public Criteria andNetInDateNotEqualTo(String value) {
            addCriterion("NET_IN_DATE <>", value, "netInDate");
            return (Criteria) this;
        }

        public Criteria andNetInDateGreaterThan(String value) {
            addCriterion("NET_IN_DATE >", value, "netInDate");
            return (Criteria) this;
        }

        public Criteria andNetInDateGreaterThanOrEqualTo(String value) {
            addCriterion("NET_IN_DATE >=", value, "netInDate");
            return (Criteria) this;
        }

        public Criteria andNetInDateLessThan(String value) {
            addCriterion("NET_IN_DATE <", value, "netInDate");
            return (Criteria) this;
        }

        public Criteria andNetInDateLessThanOrEqualTo(String value) {
            addCriterion("NET_IN_DATE <=", value, "netInDate");
            return (Criteria) this;
        }

        public Criteria andNetInDateLike(String value) {
            addCriterion("NET_IN_DATE like", value, "netInDate");
            return (Criteria) this;
        }

        public Criteria andNetInDateNotLike(String value) {
            addCriterion("NET_IN_DATE not like", value, "netInDate");
            return (Criteria) this;
        }

        public Criteria andNetInDateIn(List<String> values) {
            addCriterion("NET_IN_DATE in", values, "netInDate");
            return (Criteria) this;
        }

        public Criteria andNetInDateNotIn(List<String> values) {
            addCriterion("NET_IN_DATE not in", values, "netInDate");
            return (Criteria) this;
        }

        public Criteria andNetInDateBetween(String value1, String value2) {
            addCriterion("NET_IN_DATE between", value1, value2, "netInDate");
            return (Criteria) this;
        }

        public Criteria andNetInDateNotBetween(String value1, String value2) {
            addCriterion("NET_IN_DATE not between", value1, value2, "netInDate");
            return (Criteria) this;
        }

        public Criteria andToolsInvoiceAmtIsNull() {
            addCriterion("TOOLS_INVOICE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andToolsInvoiceAmtIsNotNull() {
            addCriterion("TOOLS_INVOICE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andToolsInvoiceAmtEqualTo(BigDecimal value) {
            addCriterion("TOOLS_INVOICE_AMT =", value, "toolsInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andToolsInvoiceAmtNotEqualTo(BigDecimal value) {
            addCriterion("TOOLS_INVOICE_AMT <>", value, "toolsInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andToolsInvoiceAmtGreaterThan(BigDecimal value) {
            addCriterion("TOOLS_INVOICE_AMT >", value, "toolsInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andToolsInvoiceAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TOOLS_INVOICE_AMT >=", value, "toolsInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andToolsInvoiceAmtLessThan(BigDecimal value) {
            addCriterion("TOOLS_INVOICE_AMT <", value, "toolsInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andToolsInvoiceAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TOOLS_INVOICE_AMT <=", value, "toolsInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andToolsInvoiceAmtIn(List<BigDecimal> values) {
            addCriterion("TOOLS_INVOICE_AMT in", values, "toolsInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andToolsInvoiceAmtNotIn(List<BigDecimal> values) {
            addCriterion("TOOLS_INVOICE_AMT not in", values, "toolsInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andToolsInvoiceAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TOOLS_INVOICE_AMT between", value1, value2, "toolsInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andToolsInvoiceAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TOOLS_INVOICE_AMT not between", value1, value2, "toolsInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andMonthProfitAmtIsNull() {
            addCriterion("MONTH_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andMonthProfitAmtIsNotNull() {
            addCriterion("MONTH_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andMonthProfitAmtEqualTo(BigDecimal value) {
            addCriterion("MONTH_PROFIT_AMT =", value, "monthProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMonthProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("MONTH_PROFIT_AMT <>", value, "monthProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMonthProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("MONTH_PROFIT_AMT >", value, "monthProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMonthProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MONTH_PROFIT_AMT >=", value, "monthProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMonthProfitAmtLessThan(BigDecimal value) {
            addCriterion("MONTH_PROFIT_AMT <", value, "monthProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMonthProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MONTH_PROFIT_AMT <=", value, "monthProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMonthProfitAmtIn(List<BigDecimal> values) {
            addCriterion("MONTH_PROFIT_AMT in", values, "monthProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMonthProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("MONTH_PROFIT_AMT not in", values, "monthProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMonthProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MONTH_PROFIT_AMT between", value1, value2, "monthProfitAmt");
            return (Criteria) this;
        }

        public Criteria andMonthProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MONTH_PROFIT_AMT not between", value1, value2, "monthProfitAmt");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodIsNull() {
            addCriterion("REPAYMENT_PERIOD is null");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodIsNotNull() {
            addCriterion("REPAYMENT_PERIOD is not null");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodEqualTo(BigDecimal value) {
            addCriterion("REPAYMENT_PERIOD =", value, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodNotEqualTo(BigDecimal value) {
            addCriterion("REPAYMENT_PERIOD <>", value, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodGreaterThan(BigDecimal value) {
            addCriterion("REPAYMENT_PERIOD >", value, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REPAYMENT_PERIOD >=", value, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodLessThan(BigDecimal value) {
            addCriterion("REPAYMENT_PERIOD <", value, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REPAYMENT_PERIOD <=", value, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodIn(List<BigDecimal> values) {
            addCriterion("REPAYMENT_PERIOD in", values, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodNotIn(List<BigDecimal> values) {
            addCriterion("REPAYMENT_PERIOD not in", values, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REPAYMENT_PERIOD between", value1, value2, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRepaymentPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REPAYMENT_PERIOD not between", value1, value2, "repaymentPeriod");
            return (Criteria) this;
        }

        public Criteria andRemitAmtIsNull() {
            addCriterion("REMIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andRemitAmtIsNotNull() {
            addCriterion("REMIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andRemitAmtEqualTo(BigDecimal value) {
            addCriterion("REMIT_AMT =", value, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtNotEqualTo(BigDecimal value) {
            addCriterion("REMIT_AMT <>", value, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtGreaterThan(BigDecimal value) {
            addCriterion("REMIT_AMT >", value, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REMIT_AMT >=", value, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtLessThan(BigDecimal value) {
            addCriterion("REMIT_AMT <", value, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REMIT_AMT <=", value, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtIn(List<BigDecimal> values) {
            addCriterion("REMIT_AMT in", values, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtNotIn(List<BigDecimal> values) {
            addCriterion("REMIT_AMT not in", values, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REMIT_AMT between", value1, value2, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REMIT_AMT not between", value1, value2, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andParenterSupplyAmtIsNull() {
            addCriterion("PARENTER_SUPPLY_AMT is null");
            return (Criteria) this;
        }

        public Criteria andParenterSupplyAmtIsNotNull() {
            addCriterion("PARENTER_SUPPLY_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andParenterSupplyAmtEqualTo(BigDecimal value) {
            addCriterion("PARENTER_SUPPLY_AMT =", value, "parenterSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andParenterSupplyAmtNotEqualTo(BigDecimal value) {
            addCriterion("PARENTER_SUPPLY_AMT <>", value, "parenterSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andParenterSupplyAmtGreaterThan(BigDecimal value) {
            addCriterion("PARENTER_SUPPLY_AMT >", value, "parenterSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andParenterSupplyAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PARENTER_SUPPLY_AMT >=", value, "parenterSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andParenterSupplyAmtLessThan(BigDecimal value) {
            addCriterion("PARENTER_SUPPLY_AMT <", value, "parenterSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andParenterSupplyAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PARENTER_SUPPLY_AMT <=", value, "parenterSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andParenterSupplyAmtIn(List<BigDecimal> values) {
            addCriterion("PARENTER_SUPPLY_AMT in", values, "parenterSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andParenterSupplyAmtNotIn(List<BigDecimal> values) {
            addCriterion("PARENTER_SUPPLY_AMT not in", values, "parenterSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andParenterSupplyAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PARENTER_SUPPLY_AMT between", value1, value2, "parenterSupplyAmt");
            return (Criteria) this;
        }

        public Criteria andParenterSupplyAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PARENTER_SUPPLY_AMT not between", value1, value2, "parenterSupplyAmt");
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

        public Criteria andExaminrIdIsNull() {
            addCriterion("EXAMINR_ID is null");
            return (Criteria) this;
        }

        public Criteria andExaminrIdIsNotNull() {
            addCriterion("EXAMINR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andExaminrIdEqualTo(String value) {
            addCriterion("EXAMINR_ID =", value, "examinrId");
            return (Criteria) this;
        }

        public Criteria andExaminrIdNotEqualTo(String value) {
            addCriterion("EXAMINR_ID <>", value, "examinrId");
            return (Criteria) this;
        }

        public Criteria andExaminrIdGreaterThan(String value) {
            addCriterion("EXAMINR_ID >", value, "examinrId");
            return (Criteria) this;
        }

        public Criteria andExaminrIdGreaterThanOrEqualTo(String value) {
            addCriterion("EXAMINR_ID >=", value, "examinrId");
            return (Criteria) this;
        }

        public Criteria andExaminrIdLessThan(String value) {
            addCriterion("EXAMINR_ID <", value, "examinrId");
            return (Criteria) this;
        }

        public Criteria andExaminrIdLessThanOrEqualTo(String value) {
            addCriterion("EXAMINR_ID <=", value, "examinrId");
            return (Criteria) this;
        }

        public Criteria andExaminrIdLike(String value) {
            addCriterion("EXAMINR_ID like", value, "examinrId");
            return (Criteria) this;
        }

        public Criteria andExaminrIdNotLike(String value) {
            addCriterion("EXAMINR_ID not like", value, "examinrId");
            return (Criteria) this;
        }

        public Criteria andExaminrIdIn(List<String> values) {
            addCriterion("EXAMINR_ID in", values, "examinrId");
            return (Criteria) this;
        }

        public Criteria andExaminrIdNotIn(List<String> values) {
            addCriterion("EXAMINR_ID not in", values, "examinrId");
            return (Criteria) this;
        }

        public Criteria andExaminrIdBetween(String value1, String value2) {
            addCriterion("EXAMINR_ID between", value1, value2, "examinrId");
            return (Criteria) this;
        }

        public Criteria andExaminrIdNotBetween(String value1, String value2) {
            addCriterion("EXAMINR_ID not between", value1, value2, "examinrId");
            return (Criteria) this;
        }

        public Criteria andExaminrStatusIsNull() {
            addCriterion("EXAMINR_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andExaminrStatusIsNotNull() {
            addCriterion("EXAMINR_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andExaminrStatusEqualTo(String value) {
            addCriterion("EXAMINR_STATUS =", value, "examinrStatus");
            return (Criteria) this;
        }

        public Criteria andExaminrStatusNotEqualTo(String value) {
            addCriterion("EXAMINR_STATUS <>", value, "examinrStatus");
            return (Criteria) this;
        }

        public Criteria andExaminrStatusGreaterThan(String value) {
            addCriterion("EXAMINR_STATUS >", value, "examinrStatus");
            return (Criteria) this;
        }

        public Criteria andExaminrStatusGreaterThanOrEqualTo(String value) {
            addCriterion("EXAMINR_STATUS >=", value, "examinrStatus");
            return (Criteria) this;
        }

        public Criteria andExaminrStatusLessThan(String value) {
            addCriterion("EXAMINR_STATUS <", value, "examinrStatus");
            return (Criteria) this;
        }

        public Criteria andExaminrStatusLessThanOrEqualTo(String value) {
            addCriterion("EXAMINR_STATUS <=", value, "examinrStatus");
            return (Criteria) this;
        }

        public Criteria andExaminrStatusLike(String value) {
            addCriterion("EXAMINR_STATUS like", value, "examinrStatus");
            return (Criteria) this;
        }

        public Criteria andExaminrStatusNotLike(String value) {
            addCriterion("EXAMINR_STATUS not like", value, "examinrStatus");
            return (Criteria) this;
        }

        public Criteria andExaminrStatusIn(List<String> values) {
            addCriterion("EXAMINR_STATUS in", values, "examinrStatus");
            return (Criteria) this;
        }

        public Criteria andExaminrStatusNotIn(List<String> values) {
            addCriterion("EXAMINR_STATUS not in", values, "examinrStatus");
            return (Criteria) this;
        }

        public Criteria andExaminrStatusBetween(String value1, String value2) {
            addCriterion("EXAMINR_STATUS between", value1, value2, "examinrStatus");
            return (Criteria) this;
        }

        public Criteria andExaminrStatusNotBetween(String value1, String value2) {
            addCriterion("EXAMINR_STATUS not between", value1, value2, "examinrStatus");
            return (Criteria) this;
        }

        public Criteria andDeductionIdIsNull() {
            addCriterion("DEDUCTION_ID is null");
            return (Criteria) this;
        }

        public Criteria andDeductionIdIsNotNull() {
            addCriterion("DEDUCTION_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDeductionIdEqualTo(String value) {
            addCriterion("DEDUCTION_ID =", value, "deductionId");
            return (Criteria) this;
        }

        public Criteria andDeductionIdNotEqualTo(String value) {
            addCriterion("DEDUCTION_ID <>", value, "deductionId");
            return (Criteria) this;
        }

        public Criteria andDeductionIdGreaterThan(String value) {
            addCriterion("DEDUCTION_ID >", value, "deductionId");
            return (Criteria) this;
        }

        public Criteria andDeductionIdGreaterThanOrEqualTo(String value) {
            addCriterion("DEDUCTION_ID >=", value, "deductionId");
            return (Criteria) this;
        }

        public Criteria andDeductionIdLessThan(String value) {
            addCriterion("DEDUCTION_ID <", value, "deductionId");
            return (Criteria) this;
        }

        public Criteria andDeductionIdLessThanOrEqualTo(String value) {
            addCriterion("DEDUCTION_ID <=", value, "deductionId");
            return (Criteria) this;
        }

        public Criteria andDeductionIdLike(String value) {
            addCriterion("DEDUCTION_ID like", value, "deductionId");
            return (Criteria) this;
        }

        public Criteria andDeductionIdNotLike(String value) {
            addCriterion("DEDUCTION_ID not like", value, "deductionId");
            return (Criteria) this;
        }

        public Criteria andDeductionIdIn(List<String> values) {
            addCriterion("DEDUCTION_ID in", values, "deductionId");
            return (Criteria) this;
        }

        public Criteria andDeductionIdNotIn(List<String> values) {
            addCriterion("DEDUCTION_ID not in", values, "deductionId");
            return (Criteria) this;
        }

        public Criteria andDeductionIdBetween(String value1, String value2) {
            addCriterion("DEDUCTION_ID between", value1, value2, "deductionId");
            return (Criteria) this;
        }

        public Criteria andDeductionIdNotBetween(String value1, String value2) {
            addCriterion("DEDUCTION_ID not between", value1, value2, "deductionId");
            return (Criteria) this;
        }

        public Criteria andRev1IsNull() {
            addCriterion("REV1 is null");
            return (Criteria) this;
        }

        public Criteria andRev1IsNotNull() {
            addCriterion("REV1 is not null");
            return (Criteria) this;
        }

        public Criteria andRev1EqualTo(String value) {
            addCriterion("REV1 =", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotEqualTo(String value) {
            addCriterion("REV1 <>", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1GreaterThan(String value) {
            addCriterion("REV1 >", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1GreaterThanOrEqualTo(String value) {
            addCriterion("REV1 >=", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1LessThan(String value) {
            addCriterion("REV1 <", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1LessThanOrEqualTo(String value) {
            addCriterion("REV1 <=", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1Like(String value) {
            addCriterion("REV1 like", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotLike(String value) {
            addCriterion("REV1 not like", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1In(List<String> values) {
            addCriterion("REV1 in", values, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotIn(List<String> values) {
            addCriterion("REV1 not in", values, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1Between(String value1, String value2) {
            addCriterion("REV1 between", value1, value2, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotBetween(String value1, String value2) {
            addCriterion("REV1 not between", value1, value2, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev2IsNull() {
            addCriterion("REV2 is null");
            return (Criteria) this;
        }

        public Criteria andRev2IsNotNull() {
            addCriterion("REV2 is not null");
            return (Criteria) this;
        }

        public Criteria andRev2EqualTo(String value) {
            addCriterion("REV2 =", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotEqualTo(String value) {
            addCriterion("REV2 <>", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2GreaterThan(String value) {
            addCriterion("REV2 >", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2GreaterThanOrEqualTo(String value) {
            addCriterion("REV2 >=", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2LessThan(String value) {
            addCriterion("REV2 <", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2LessThanOrEqualTo(String value) {
            addCriterion("REV2 <=", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2Like(String value) {
            addCriterion("REV2 like", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotLike(String value) {
            addCriterion("REV2 not like", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2In(List<String> values) {
            addCriterion("REV2 in", values, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotIn(List<String> values) {
            addCriterion("REV2 not in", values, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2Between(String value1, String value2) {
            addCriterion("REV2 between", value1, value2, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotBetween(String value1, String value2) {
            addCriterion("REV2 not between", value1, value2, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev3IsNull() {
            addCriterion("REV3 is null");
            return (Criteria) this;
        }

        public Criteria andRev3IsNotNull() {
            addCriterion("REV3 is not null");
            return (Criteria) this;
        }

        public Criteria andRev3EqualTo(String value) {
            addCriterion("REV3 =", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3NotEqualTo(String value) {
            addCriterion("REV3 <>", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3GreaterThan(String value) {
            addCriterion("REV3 >", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3GreaterThanOrEqualTo(String value) {
            addCriterion("REV3 >=", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3LessThan(String value) {
            addCriterion("REV3 <", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3LessThanOrEqualTo(String value) {
            addCriterion("REV3 <=", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3Like(String value) {
            addCriterion("REV3 like", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3NotLike(String value) {
            addCriterion("REV3 not like", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3In(List<String> values) {
            addCriterion("REV3 in", values, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3NotIn(List<String> values) {
            addCriterion("REV3 not in", values, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3Between(String value1, String value2) {
            addCriterion("REV3 between", value1, value2, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3NotBetween(String value1, String value2) {
            addCriterion("REV3 not between", value1, value2, "rev3");
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