package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OInvoiceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OInvoiceExample() {
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

        public Criteria andSrcTypeIsNull() {
            addCriterion("SRC_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSrcTypeIsNotNull() {
            addCriterion("SRC_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSrcTypeEqualTo(BigDecimal value) {
            addCriterion("SRC_TYPE =", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeNotEqualTo(BigDecimal value) {
            addCriterion("SRC_TYPE <>", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeGreaterThan(BigDecimal value) {
            addCriterion("SRC_TYPE >", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SRC_TYPE >=", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeLessThan(BigDecimal value) {
            addCriterion("SRC_TYPE <", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SRC_TYPE <=", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeIn(List<BigDecimal> values) {
            addCriterion("SRC_TYPE in", values, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeNotIn(List<BigDecimal> values) {
            addCriterion("SRC_TYPE not in", values, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SRC_TYPE between", value1, value2, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SRC_TYPE not between", value1, value2, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcIdIsNull() {
            addCriterion("SRC_ID is null");
            return (Criteria) this;
        }

        public Criteria andSrcIdIsNotNull() {
            addCriterion("SRC_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSrcIdEqualTo(String value) {
            addCriterion("SRC_ID =", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdNotEqualTo(String value) {
            addCriterion("SRC_ID <>", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdGreaterThan(String value) {
            addCriterion("SRC_ID >", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdGreaterThanOrEqualTo(String value) {
            addCriterion("SRC_ID >=", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdLessThan(String value) {
            addCriterion("SRC_ID <", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdLessThanOrEqualTo(String value) {
            addCriterion("SRC_ID <=", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdLike(String value) {
            addCriterion("SRC_ID like", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdNotLike(String value) {
            addCriterion("SRC_ID not like", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdIn(List<String> values) {
            addCriterion("SRC_ID in", values, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdNotIn(List<String> values) {
            addCriterion("SRC_ID not in", values, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdBetween(String value1, String value2) {
            addCriterion("SRC_ID between", value1, value2, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdNotBetween(String value1, String value2) {
            addCriterion("SRC_ID not between", value1, value2, "srcId");
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

        public Criteria andInvoiceProjectIsNull() {
            addCriterion("INVOICE_PROJECT is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceProjectIsNotNull() {
            addCriterion("INVOICE_PROJECT is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceProjectEqualTo(String value) {
            addCriterion("INVOICE_PROJECT =", value, "invoiceProject");
            return (Criteria) this;
        }

        public Criteria andInvoiceProjectNotEqualTo(String value) {
            addCriterion("INVOICE_PROJECT <>", value, "invoiceProject");
            return (Criteria) this;
        }

        public Criteria andInvoiceProjectGreaterThan(String value) {
            addCriterion("INVOICE_PROJECT >", value, "invoiceProject");
            return (Criteria) this;
        }

        public Criteria andInvoiceProjectGreaterThanOrEqualTo(String value) {
            addCriterion("INVOICE_PROJECT >=", value, "invoiceProject");
            return (Criteria) this;
        }

        public Criteria andInvoiceProjectLessThan(String value) {
            addCriterion("INVOICE_PROJECT <", value, "invoiceProject");
            return (Criteria) this;
        }

        public Criteria andInvoiceProjectLessThanOrEqualTo(String value) {
            addCriterion("INVOICE_PROJECT <=", value, "invoiceProject");
            return (Criteria) this;
        }

        public Criteria andInvoiceProjectLike(String value) {
            addCriterion("INVOICE_PROJECT like", value, "invoiceProject");
            return (Criteria) this;
        }

        public Criteria andInvoiceProjectNotLike(String value) {
            addCriterion("INVOICE_PROJECT not like", value, "invoiceProject");
            return (Criteria) this;
        }

        public Criteria andInvoiceProjectIn(List<String> values) {
            addCriterion("INVOICE_PROJECT in", values, "invoiceProject");
            return (Criteria) this;
        }

        public Criteria andInvoiceProjectNotIn(List<String> values) {
            addCriterion("INVOICE_PROJECT not in", values, "invoiceProject");
            return (Criteria) this;
        }

        public Criteria andInvoiceProjectBetween(String value1, String value2) {
            addCriterion("INVOICE_PROJECT between", value1, value2, "invoiceProject");
            return (Criteria) this;
        }

        public Criteria andInvoiceProjectNotBetween(String value1, String value2) {
            addCriterion("INVOICE_PROJECT not between", value1, value2, "invoiceProject");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtIsNull() {
            addCriterion("INVOICE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtIsNotNull() {
            addCriterion("INVOICE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtEqualTo(BigDecimal value) {
            addCriterion("INVOICE_AMT =", value, "invoiceAmt");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtNotEqualTo(BigDecimal value) {
            addCriterion("INVOICE_AMT <>", value, "invoiceAmt");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtGreaterThan(BigDecimal value) {
            addCriterion("INVOICE_AMT >", value, "invoiceAmt");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("INVOICE_AMT >=", value, "invoiceAmt");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtLessThan(BigDecimal value) {
            addCriterion("INVOICE_AMT <", value, "invoiceAmt");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("INVOICE_AMT <=", value, "invoiceAmt");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtIn(List<BigDecimal> values) {
            addCriterion("INVOICE_AMT in", values, "invoiceAmt");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtNotIn(List<BigDecimal> values) {
            addCriterion("INVOICE_AMT not in", values, "invoiceAmt");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("INVOICE_AMT between", value1, value2, "invoiceAmt");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("INVOICE_AMT not between", value1, value2, "invoiceAmt");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumIsNull() {
            addCriterion("INVOICE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumIsNotNull() {
            addCriterion("INVOICE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumEqualTo(String value) {
            addCriterion("INVOICE_NUM =", value, "invoiceNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumNotEqualTo(String value) {
            addCriterion("INVOICE_NUM <>", value, "invoiceNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumGreaterThan(String value) {
            addCriterion("INVOICE_NUM >", value, "invoiceNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumGreaterThanOrEqualTo(String value) {
            addCriterion("INVOICE_NUM >=", value, "invoiceNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumLessThan(String value) {
            addCriterion("INVOICE_NUM <", value, "invoiceNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumLessThanOrEqualTo(String value) {
            addCriterion("INVOICE_NUM <=", value, "invoiceNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumLike(String value) {
            addCriterion("INVOICE_NUM like", value, "invoiceNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumNotLike(String value) {
            addCriterion("INVOICE_NUM not like", value, "invoiceNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumIn(List<String> values) {
            addCriterion("INVOICE_NUM in", values, "invoiceNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumNotIn(List<String> values) {
            addCriterion("INVOICE_NUM not in", values, "invoiceNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumBetween(String value1, String value2) {
            addCriterion("INVOICE_NUM between", value1, value2, "invoiceNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumNotBetween(String value1, String value2) {
            addCriterion("INVOICE_NUM not between", value1, value2, "invoiceNum");
            return (Criteria) this;
        }

        public Criteria andInvoiceCodeIsNull() {
            addCriterion("INVOICE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceCodeIsNotNull() {
            addCriterion("INVOICE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceCodeEqualTo(String value) {
            addCriterion("INVOICE_CODE =", value, "invoiceCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCodeNotEqualTo(String value) {
            addCriterion("INVOICE_CODE <>", value, "invoiceCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCodeGreaterThan(String value) {
            addCriterion("INVOICE_CODE >", value, "invoiceCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCodeGreaterThanOrEqualTo(String value) {
            addCriterion("INVOICE_CODE >=", value, "invoiceCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCodeLessThan(String value) {
            addCriterion("INVOICE_CODE <", value, "invoiceCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCodeLessThanOrEqualTo(String value) {
            addCriterion("INVOICE_CODE <=", value, "invoiceCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCodeLike(String value) {
            addCriterion("INVOICE_CODE like", value, "invoiceCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCodeNotLike(String value) {
            addCriterion("INVOICE_CODE not like", value, "invoiceCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCodeIn(List<String> values) {
            addCriterion("INVOICE_CODE in", values, "invoiceCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCodeNotIn(List<String> values) {
            addCriterion("INVOICE_CODE not in", values, "invoiceCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCodeBetween(String value1, String value2) {
            addCriterion("INVOICE_CODE between", value1, value2, "invoiceCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCodeNotBetween(String value1, String value2) {
            addCriterion("INVOICE_CODE not between", value1, value2, "invoiceCode");
            return (Criteria) this;
        }

        public Criteria andExpressNumIsNull() {
            addCriterion("EXPRESS_NUM is null");
            return (Criteria) this;
        }

        public Criteria andExpressNumIsNotNull() {
            addCriterion("EXPRESS_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andExpressNumEqualTo(String value) {
            addCriterion("EXPRESS_NUM =", value, "expressNum");
            return (Criteria) this;
        }

        public Criteria andExpressNumNotEqualTo(String value) {
            addCriterion("EXPRESS_NUM <>", value, "expressNum");
            return (Criteria) this;
        }

        public Criteria andExpressNumGreaterThan(String value) {
            addCriterion("EXPRESS_NUM >", value, "expressNum");
            return (Criteria) this;
        }

        public Criteria andExpressNumGreaterThanOrEqualTo(String value) {
            addCriterion("EXPRESS_NUM >=", value, "expressNum");
            return (Criteria) this;
        }

        public Criteria andExpressNumLessThan(String value) {
            addCriterion("EXPRESS_NUM <", value, "expressNum");
            return (Criteria) this;
        }

        public Criteria andExpressNumLessThanOrEqualTo(String value) {
            addCriterion("EXPRESS_NUM <=", value, "expressNum");
            return (Criteria) this;
        }

        public Criteria andExpressNumLike(String value) {
            addCriterion("EXPRESS_NUM like", value, "expressNum");
            return (Criteria) this;
        }

        public Criteria andExpressNumNotLike(String value) {
            addCriterion("EXPRESS_NUM not like", value, "expressNum");
            return (Criteria) this;
        }

        public Criteria andExpressNumIn(List<String> values) {
            addCriterion("EXPRESS_NUM in", values, "expressNum");
            return (Criteria) this;
        }

        public Criteria andExpressNumNotIn(List<String> values) {
            addCriterion("EXPRESS_NUM not in", values, "expressNum");
            return (Criteria) this;
        }

        public Criteria andExpressNumBetween(String value1, String value2) {
            addCriterion("EXPRESS_NUM between", value1, value2, "expressNum");
            return (Criteria) this;
        }

        public Criteria andExpressNumNotBetween(String value1, String value2) {
            addCriterion("EXPRESS_NUM not between", value1, value2, "expressNum");
            return (Criteria) this;
        }

        public Criteria andExpressCompIsNull() {
            addCriterion("EXPRESS_COMP is null");
            return (Criteria) this;
        }

        public Criteria andExpressCompIsNotNull() {
            addCriterion("EXPRESS_COMP is not null");
            return (Criteria) this;
        }

        public Criteria andExpressCompEqualTo(String value) {
            addCriterion("EXPRESS_COMP =", value, "expressComp");
            return (Criteria) this;
        }

        public Criteria andExpressCompNotEqualTo(String value) {
            addCriterion("EXPRESS_COMP <>", value, "expressComp");
            return (Criteria) this;
        }

        public Criteria andExpressCompGreaterThan(String value) {
            addCriterion("EXPRESS_COMP >", value, "expressComp");
            return (Criteria) this;
        }

        public Criteria andExpressCompGreaterThanOrEqualTo(String value) {
            addCriterion("EXPRESS_COMP >=", value, "expressComp");
            return (Criteria) this;
        }

        public Criteria andExpressCompLessThan(String value) {
            addCriterion("EXPRESS_COMP <", value, "expressComp");
            return (Criteria) this;
        }

        public Criteria andExpressCompLessThanOrEqualTo(String value) {
            addCriterion("EXPRESS_COMP <=", value, "expressComp");
            return (Criteria) this;
        }

        public Criteria andExpressCompLike(String value) {
            addCriterion("EXPRESS_COMP like", value, "expressComp");
            return (Criteria) this;
        }

        public Criteria andExpressCompNotLike(String value) {
            addCriterion("EXPRESS_COMP not like", value, "expressComp");
            return (Criteria) this;
        }

        public Criteria andExpressCompIn(List<String> values) {
            addCriterion("EXPRESS_COMP in", values, "expressComp");
            return (Criteria) this;
        }

        public Criteria andExpressCompNotIn(List<String> values) {
            addCriterion("EXPRESS_COMP not in", values, "expressComp");
            return (Criteria) this;
        }

        public Criteria andExpressCompBetween(String value1, String value2) {
            addCriterion("EXPRESS_COMP between", value1, value2, "expressComp");
            return (Criteria) this;
        }

        public Criteria andExpressCompNotBetween(String value1, String value2) {
            addCriterion("EXPRESS_COMP not between", value1, value2, "expressComp");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNull() {
            addCriterion("SEND_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNotNull() {
            addCriterion("SEND_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSendTimeEqualTo(Date value) {
            addCriterion("SEND_TIME =", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotEqualTo(Date value) {
            addCriterion("SEND_TIME <>", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThan(Date value) {
            addCriterion("SEND_TIME >", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("SEND_TIME >=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThan(Date value) {
            addCriterion("SEND_TIME <", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThanOrEqualTo(Date value) {
            addCriterion("SEND_TIME <=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIn(List<Date> values) {
            addCriterion("SEND_TIME in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotIn(List<Date> values) {
            addCriterion("SEND_TIME not in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeBetween(Date value1, Date value2) {
            addCriterion("SEND_TIME between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotBetween(Date value1, Date value2) {
            addCriterion("SEND_TIME not between", value1, value2, "sendTime");
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