package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InvoiceSumExample {
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

    public InvoiceSumExample() {
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

        public Criteria andProfitMonthIsNull() {
            addCriterion("PROFIT_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andProfitMonthIsNotNull() {
            addCriterion("PROFIT_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andProfitMonthEqualTo(String value) {
            addCriterion("PROFIT_MONTH =", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthNotEqualTo(String value) {
            addCriterion("PROFIT_MONTH <>", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthGreaterThan(String value) {
            addCriterion("PROFIT_MONTH >", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthGreaterThanOrEqualTo(String value) {
            addCriterion("PROFIT_MONTH >=", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthLessThan(String value) {
            addCriterion("PROFIT_MONTH <", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthLessThanOrEqualTo(String value) {
            addCriterion("PROFIT_MONTH <=", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthLike(String value) {
            addCriterion("PROFIT_MONTH like", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthNotLike(String value) {
            addCriterion("PROFIT_MONTH not like", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthIn(List<String> values) {
            addCriterion("PROFIT_MONTH in", values, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthNotIn(List<String> values) {
            addCriterion("PROFIT_MONTH not in", values, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthBetween(String value1, String value2) {
            addCriterion("PROFIT_MONTH between", value1, value2, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthNotBetween(String value1, String value2) {
            addCriterion("PROFIT_MONTH not between", value1, value2, "profitMonth");
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

        public Criteria andInvoiceCompanyIsNull() {
            addCriterion("INVOICE_COMPANY is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyIsNotNull() {
            addCriterion("INVOICE_COMPANY is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyEqualTo(String value) {
            addCriterion("INVOICE_COMPANY =", value, "invoiceCompany");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNotEqualTo(String value) {
            addCriterion("INVOICE_COMPANY <>", value, "invoiceCompany");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyGreaterThan(String value) {
            addCriterion("INVOICE_COMPANY >", value, "invoiceCompany");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("INVOICE_COMPANY >=", value, "invoiceCompany");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyLessThan(String value) {
            addCriterion("INVOICE_COMPANY <", value, "invoiceCompany");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyLessThanOrEqualTo(String value) {
            addCriterion("INVOICE_COMPANY <=", value, "invoiceCompany");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyLike(String value) {
            addCriterion("INVOICE_COMPANY like", value, "invoiceCompany");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNotLike(String value) {
            addCriterion("INVOICE_COMPANY not like", value, "invoiceCompany");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyIn(List<String> values) {
            addCriterion("INVOICE_COMPANY in", values, "invoiceCompany");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNotIn(List<String> values) {
            addCriterion("INVOICE_COMPANY not in", values, "invoiceCompany");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyBetween(String value1, String value2) {
            addCriterion("INVOICE_COMPANY between", value1, value2, "invoiceCompany");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNotBetween(String value1, String value2) {
            addCriterion("INVOICE_COMPANY not between", value1, value2, "invoiceCompany");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtIsNull() {
            addCriterion("PRE_LEFT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtIsNotNull() {
            addCriterion("PRE_LEFT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtEqualTo(BigDecimal value) {
            addCriterion("PRE_LEFT_AMT =", value, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtNotEqualTo(BigDecimal value) {
            addCriterion("PRE_LEFT_AMT <>", value, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtGreaterThan(BigDecimal value) {
            addCriterion("PRE_LEFT_AMT >", value, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRE_LEFT_AMT >=", value, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtLessThan(BigDecimal value) {
            addCriterion("PRE_LEFT_AMT <", value, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRE_LEFT_AMT <=", value, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtIn(List<BigDecimal> values) {
            addCriterion("PRE_LEFT_AMT in", values, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtNotIn(List<BigDecimal> values) {
            addCriterion("PRE_LEFT_AMT not in", values, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRE_LEFT_AMT between", value1, value2, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRE_LEFT_AMT not between", value1, value2, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtIsNull() {
            addCriterion("DAY_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtIsNotNull() {
            addCriterion("DAY_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtEqualTo(BigDecimal value) {
            addCriterion("DAY_PROFIT_AMT =", value, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("DAY_PROFIT_AMT <>", value, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("DAY_PROFIT_AMT >", value, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DAY_PROFIT_AMT >=", value, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtLessThan(BigDecimal value) {
            addCriterion("DAY_PROFIT_AMT <", value, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DAY_PROFIT_AMT <=", value, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtIn(List<BigDecimal> values) {
            addCriterion("DAY_PROFIT_AMT in", values, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("DAY_PROFIT_AMT not in", values, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAY_PROFIT_AMT between", value1, value2, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAY_PROFIT_AMT not between", value1, value2, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtIsNull() {
            addCriterion("DAY_BACK_AMT is null");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtIsNotNull() {
            addCriterion("DAY_BACK_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtEqualTo(BigDecimal value) {
            addCriterion("DAY_BACK_AMT =", value, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtNotEqualTo(BigDecimal value) {
            addCriterion("DAY_BACK_AMT <>", value, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtGreaterThan(BigDecimal value) {
            addCriterion("DAY_BACK_AMT >", value, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DAY_BACK_AMT >=", value, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtLessThan(BigDecimal value) {
            addCriterion("DAY_BACK_AMT <", value, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DAY_BACK_AMT <=", value, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtIn(List<BigDecimal> values) {
            addCriterion("DAY_BACK_AMT in", values, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtNotIn(List<BigDecimal> values) {
            addCriterion("DAY_BACK_AMT not in", values, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAY_BACK_AMT between", value1, value2, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAY_BACK_AMT not between", value1, value2, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtIsNull() {
            addCriterion("PRE_PROFIT_MONTH_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtIsNotNull() {
            addCriterion("PRE_PROFIT_MONTH_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtEqualTo(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_AMT =", value, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtNotEqualTo(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_AMT <>", value, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtGreaterThan(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_AMT >", value, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_AMT >=", value, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtLessThan(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_AMT <", value, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_AMT <=", value, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtIn(List<BigDecimal> values) {
            addCriterion("PRE_PROFIT_MONTH_AMT in", values, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtNotIn(List<BigDecimal> values) {
            addCriterion("PRE_PROFIT_MONTH_AMT not in", values, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRE_PROFIT_MONTH_AMT between", value1, value2, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRE_PROFIT_MONTH_AMT not between", value1, value2, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtIsNull() {
            addCriterion("ADD_INVOICE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtIsNotNull() {
            addCriterion("ADD_INVOICE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtEqualTo(BigDecimal value) {
            addCriterion("ADD_INVOICE_AMT =", value, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtNotEqualTo(BigDecimal value) {
            addCriterion("ADD_INVOICE_AMT <>", value, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtGreaterThan(BigDecimal value) {
            addCriterion("ADD_INVOICE_AMT >", value, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ADD_INVOICE_AMT >=", value, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtLessThan(BigDecimal value) {
            addCriterion("ADD_INVOICE_AMT <", value, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ADD_INVOICE_AMT <=", value, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtIn(List<BigDecimal> values) {
            addCriterion("ADD_INVOICE_AMT in", values, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtNotIn(List<BigDecimal> values) {
            addCriterion("ADD_INVOICE_AMT not in", values, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADD_INVOICE_AMT between", value1, value2, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADD_INVOICE_AMT not between", value1, value2, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceIsNull() {
            addCriterion("OWN_INVOICE is null");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceIsNotNull() {
            addCriterion("OWN_INVOICE is not null");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceEqualTo(BigDecimal value) {
            addCriterion("OWN_INVOICE =", value, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceNotEqualTo(BigDecimal value) {
            addCriterion("OWN_INVOICE <>", value, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceGreaterThan(BigDecimal value) {
            addCriterion("OWN_INVOICE >", value, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OWN_INVOICE >=", value, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceLessThan(BigDecimal value) {
            addCriterion("OWN_INVOICE <", value, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OWN_INVOICE <=", value, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceIn(List<BigDecimal> values) {
            addCriterion("OWN_INVOICE in", values, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceNotIn(List<BigDecimal> values) {
            addCriterion("OWN_INVOICE not in", values, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OWN_INVOICE between", value1, value2, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OWN_INVOICE not between", value1, value2, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtIsNull() {
            addCriterion("ADJUST_AMT is null");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtIsNotNull() {
            addCriterion("ADJUST_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtEqualTo(BigDecimal value) {
            addCriterion("ADJUST_AMT =", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtNotEqualTo(BigDecimal value) {
            addCriterion("ADJUST_AMT <>", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtGreaterThan(BigDecimal value) {
            addCriterion("ADJUST_AMT >", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ADJUST_AMT >=", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtLessThan(BigDecimal value) {
            addCriterion("ADJUST_AMT <", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ADJUST_AMT <=", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtIn(List<BigDecimal> values) {
            addCriterion("ADJUST_AMT in", values, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtNotIn(List<BigDecimal> values) {
            addCriterion("ADJUST_AMT not in", values, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADJUST_AMT between", value1, value2, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADJUST_AMT not between", value1, value2, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountIsNull() {
            addCriterion("ADJUST_ACCOUNT is null");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountIsNotNull() {
            addCriterion("ADJUST_ACCOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountEqualTo(String value) {
            addCriterion("ADJUST_ACCOUNT =", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountNotEqualTo(String value) {
            addCriterion("ADJUST_ACCOUNT <>", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountGreaterThan(String value) {
            addCriterion("ADJUST_ACCOUNT >", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_ACCOUNT >=", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountLessThan(String value) {
            addCriterion("ADJUST_ACCOUNT <", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_ACCOUNT <=", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountLike(String value) {
            addCriterion("ADJUST_ACCOUNT like", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountNotLike(String value) {
            addCriterion("ADJUST_ACCOUNT not like", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountIn(List<String> values) {
            addCriterion("ADJUST_ACCOUNT in", values, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountNotIn(List<String> values) {
            addCriterion("ADJUST_ACCOUNT not in", values, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountBetween(String value1, String value2) {
            addCriterion("ADJUST_ACCOUNT between", value1, value2, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountNotBetween(String value1, String value2) {
            addCriterion("ADJUST_ACCOUNT not between", value1, value2, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustResonIsNull() {
            addCriterion("ADJUST_RESON is null");
            return (Criteria) this;
        }

        public Criteria andAdjustResonIsNotNull() {
            addCriterion("ADJUST_RESON is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustResonEqualTo(String value) {
            addCriterion("ADJUST_RESON =", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonNotEqualTo(String value) {
            addCriterion("ADJUST_RESON <>", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonGreaterThan(String value) {
            addCriterion("ADJUST_RESON >", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_RESON >=", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonLessThan(String value) {
            addCriterion("ADJUST_RESON <", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_RESON <=", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonLike(String value) {
            addCriterion("ADJUST_RESON like", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonNotLike(String value) {
            addCriterion("ADJUST_RESON not like", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonIn(List<String> values) {
            addCriterion("ADJUST_RESON in", values, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonNotIn(List<String> values) {
            addCriterion("ADJUST_RESON not in", values, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonBetween(String value1, String value2) {
            addCriterion("ADJUST_RESON between", value1, value2, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonNotBetween(String value1, String value2) {
            addCriterion("ADJUST_RESON not between", value1, value2, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeIsNull() {
            addCriterion("ADJUST_TIME is null");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeIsNotNull() {
            addCriterion("ADJUST_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeEqualTo(String value) {
            addCriterion("ADJUST_TIME =", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeNotEqualTo(String value) {
            addCriterion("ADJUST_TIME <>", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeGreaterThan(String value) {
            addCriterion("ADJUST_TIME >", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_TIME >=", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeLessThan(String value) {
            addCriterion("ADJUST_TIME <", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_TIME <=", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeLike(String value) {
            addCriterion("ADJUST_TIME like", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeNotLike(String value) {
            addCriterion("ADJUST_TIME not like", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeIn(List<String> values) {
            addCriterion("ADJUST_TIME in", values, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeNotIn(List<String> values) {
            addCriterion("ADJUST_TIME not in", values, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeBetween(String value1, String value2) {
            addCriterion("ADJUST_TIME between", value1, value2, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeNotBetween(String value1, String value2) {
            addCriterion("ADJUST_TIME not between", value1, value2, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdIsNull() {
            addCriterion("TOP_ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdIsNotNull() {
            addCriterion("TOP_ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdEqualTo(String value) {
            addCriterion("TOP_ORG_ID =", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdNotEqualTo(String value) {
            addCriterion("TOP_ORG_ID <>", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdGreaterThan(String value) {
            addCriterion("TOP_ORG_ID >", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("TOP_ORG_ID >=", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdLessThan(String value) {
            addCriterion("TOP_ORG_ID <", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdLessThanOrEqualTo(String value) {
            addCriterion("TOP_ORG_ID <=", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdLike(String value) {
            addCriterion("TOP_ORG_ID like", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdNotLike(String value) {
            addCriterion("TOP_ORG_ID not like", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdIn(List<String> values) {
            addCriterion("TOP_ORG_ID in", values, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdNotIn(List<String> values) {
            addCriterion("TOP_ORG_ID not in", values, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdBetween(String value1, String value2) {
            addCriterion("TOP_ORG_ID between", value1, value2, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdNotBetween(String value1, String value2) {
            addCriterion("TOP_ORG_ID not between", value1, value2, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgNameIsNull() {
            addCriterion("TOP_ORG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andTopOrgNameIsNotNull() {
            addCriterion("TOP_ORG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andTopOrgNameEqualTo(String value) {
            addCriterion("TOP_ORG_NAME =", value, "topOrgName");
            return (Criteria) this;
        }

        public Criteria andTopOrgNameNotEqualTo(String value) {
            addCriterion("TOP_ORG_NAME <>", value, "topOrgName");
            return (Criteria) this;
        }

        public Criteria andTopOrgNameGreaterThan(String value) {
            addCriterion("TOP_ORG_NAME >", value, "topOrgName");
            return (Criteria) this;
        }

        public Criteria andTopOrgNameGreaterThanOrEqualTo(String value) {
            addCriterion("TOP_ORG_NAME >=", value, "topOrgName");
            return (Criteria) this;
        }

        public Criteria andTopOrgNameLessThan(String value) {
            addCriterion("TOP_ORG_NAME <", value, "topOrgName");
            return (Criteria) this;
        }

        public Criteria andTopOrgNameLessThanOrEqualTo(String value) {
            addCriterion("TOP_ORG_NAME <=", value, "topOrgName");
            return (Criteria) this;
        }

        public Criteria andTopOrgNameLike(String value) {
            addCriterion("TOP_ORG_NAME like", value, "topOrgName");
            return (Criteria) this;
        }

        public Criteria andTopOrgNameNotLike(String value) {
            addCriterion("TOP_ORG_NAME not like", value, "topOrgName");
            return (Criteria) this;
        }

        public Criteria andTopOrgNameIn(List<String> values) {
            addCriterion("TOP_ORG_NAME in", values, "topOrgName");
            return (Criteria) this;
        }

        public Criteria andTopOrgNameNotIn(List<String> values) {
            addCriterion("TOP_ORG_NAME not in", values, "topOrgName");
            return (Criteria) this;
        }

        public Criteria andTopOrgNameBetween(String value1, String value2) {
            addCriterion("TOP_ORG_NAME between", value1, value2, "topOrgName");
            return (Criteria) this;
        }

        public Criteria andTopOrgNameNotBetween(String value1, String value2) {
            addCriterion("TOP_ORG_NAME not between", value1, value2, "topOrgName");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusIsNull() {
            addCriterion("INVOICE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusIsNotNull() {
            addCriterion("INVOICE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusEqualTo(String value) {
            addCriterion("INVOICE_STATUS =", value, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusNotEqualTo(String value) {
            addCriterion("INVOICE_STATUS <>", value, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusGreaterThan(String value) {
            addCriterion("INVOICE_STATUS >", value, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusGreaterThanOrEqualTo(String value) {
            addCriterion("INVOICE_STATUS >=", value, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusLessThan(String value) {
            addCriterion("INVOICE_STATUS <", value, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusLessThanOrEqualTo(String value) {
            addCriterion("INVOICE_STATUS <=", value, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusLike(String value) {
            addCriterion("INVOICE_STATUS like", value, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusNotLike(String value) {
            addCriterion("INVOICE_STATUS not like", value, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusIn(List<String> values) {
            addCriterion("INVOICE_STATUS in", values, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusNotIn(List<String> values) {
            addCriterion("INVOICE_STATUS not in", values, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusBetween(String value1, String value2) {
            addCriterion("INVOICE_STATUS between", value1, value2, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceStatusNotBetween(String value1, String value2) {
            addCriterion("INVOICE_STATUS not between", value1, value2, "invoiceStatus");
            return (Criteria) this;
        }

        public Criteria andSubAddInvoiceAmtIsNull() {
            addCriterion("SUB_ADD_INVOICE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andSubAddInvoiceAmtIsNotNull() {
            addCriterion("SUB_ADD_INVOICE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andSubAddInvoiceAmtEqualTo(BigDecimal value) {
            addCriterion("SUB_ADD_INVOICE_AMT =", value, "subAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andSubAddInvoiceAmtNotEqualTo(BigDecimal value) {
            addCriterion("SUB_ADD_INVOICE_AMT <>", value, "subAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andSubAddInvoiceAmtGreaterThan(BigDecimal value) {
            addCriterion("SUB_ADD_INVOICE_AMT >", value, "subAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andSubAddInvoiceAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUB_ADD_INVOICE_AMT >=", value, "subAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andSubAddInvoiceAmtLessThan(BigDecimal value) {
            addCriterion("SUB_ADD_INVOICE_AMT <", value, "subAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andSubAddInvoiceAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUB_ADD_INVOICE_AMT <=", value, "subAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andSubAddInvoiceAmtIn(List<BigDecimal> values) {
            addCriterion("SUB_ADD_INVOICE_AMT in", values, "subAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andSubAddInvoiceAmtNotIn(List<BigDecimal> values) {
            addCriterion("SUB_ADD_INVOICE_AMT not in", values, "subAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andSubAddInvoiceAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUB_ADD_INVOICE_AMT between", value1, value2, "subAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andSubAddInvoiceAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUB_ADD_INVOICE_AMT not between", value1, value2, "subAddInvoiceAmt");
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