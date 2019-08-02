package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InvoiceApplyExample {
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

    public InvoiceApplyExample() {
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

        public Criteria andInvoiceDateIsNull() {
            addCriterion("INVOICE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateIsNotNull() {
            addCriterion("INVOICE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateEqualTo(String value) {
            addCriterion("INVOICE_DATE =", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateNotEqualTo(String value) {
            addCriterion("INVOICE_DATE <>", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateGreaterThan(String value) {
            addCriterion("INVOICE_DATE >", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateGreaterThanOrEqualTo(String value) {
            addCriterion("INVOICE_DATE >=", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateLessThan(String value) {
            addCriterion("INVOICE_DATE <", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateLessThanOrEqualTo(String value) {
            addCriterion("INVOICE_DATE <=", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateLike(String value) {
            addCriterion("INVOICE_DATE like", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateNotLike(String value) {
            addCriterion("INVOICE_DATE not like", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateIn(List<String> values) {
            addCriterion("INVOICE_DATE in", values, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateNotIn(List<String> values) {
            addCriterion("INVOICE_DATE not in", values, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateBetween(String value1, String value2) {
            addCriterion("INVOICE_DATE between", value1, value2, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateNotBetween(String value1, String value2) {
            addCriterion("INVOICE_DATE not between", value1, value2, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberIsNull() {
            addCriterion("INVOICE_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberIsNotNull() {
            addCriterion("INVOICE_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberEqualTo(String value) {
            addCriterion("INVOICE_NUMBER =", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberNotEqualTo(String value) {
            addCriterion("INVOICE_NUMBER <>", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberGreaterThan(String value) {
            addCriterion("INVOICE_NUMBER >", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberGreaterThanOrEqualTo(String value) {
            addCriterion("INVOICE_NUMBER >=", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberLessThan(String value) {
            addCriterion("INVOICE_NUMBER <", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberLessThanOrEqualTo(String value) {
            addCriterion("INVOICE_NUMBER <=", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberLike(String value) {
            addCriterion("INVOICE_NUMBER like", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberNotLike(String value) {
            addCriterion("INVOICE_NUMBER not like", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberIn(List<String> values) {
            addCriterion("INVOICE_NUMBER in", values, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberNotIn(List<String> values) {
            addCriterion("INVOICE_NUMBER not in", values, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberBetween(String value1, String value2) {
            addCriterion("INVOICE_NUMBER between", value1, value2, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberNotBetween(String value1, String value2) {
            addCriterion("INVOICE_NUMBER not between", value1, value2, "invoiceNumber");
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

        public Criteria andInvoiceItemIsNull() {
            addCriterion("INVOICE_ITEM is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceItemIsNotNull() {
            addCriterion("INVOICE_ITEM is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceItemEqualTo(String value) {
            addCriterion("INVOICE_ITEM =", value, "invoiceItem");
            return (Criteria) this;
        }

        public Criteria andInvoiceItemNotEqualTo(String value) {
            addCriterion("INVOICE_ITEM <>", value, "invoiceItem");
            return (Criteria) this;
        }

        public Criteria andInvoiceItemGreaterThan(String value) {
            addCriterion("INVOICE_ITEM >", value, "invoiceItem");
            return (Criteria) this;
        }

        public Criteria andInvoiceItemGreaterThanOrEqualTo(String value) {
            addCriterion("INVOICE_ITEM >=", value, "invoiceItem");
            return (Criteria) this;
        }

        public Criteria andInvoiceItemLessThan(String value) {
            addCriterion("INVOICE_ITEM <", value, "invoiceItem");
            return (Criteria) this;
        }

        public Criteria andInvoiceItemLessThanOrEqualTo(String value) {
            addCriterion("INVOICE_ITEM <=", value, "invoiceItem");
            return (Criteria) this;
        }

        public Criteria andInvoiceItemLike(String value) {
            addCriterion("INVOICE_ITEM like", value, "invoiceItem");
            return (Criteria) this;
        }

        public Criteria andInvoiceItemNotLike(String value) {
            addCriterion("INVOICE_ITEM not like", value, "invoiceItem");
            return (Criteria) this;
        }

        public Criteria andInvoiceItemIn(List<String> values) {
            addCriterion("INVOICE_ITEM in", values, "invoiceItem");
            return (Criteria) this;
        }

        public Criteria andInvoiceItemNotIn(List<String> values) {
            addCriterion("INVOICE_ITEM not in", values, "invoiceItem");
            return (Criteria) this;
        }

        public Criteria andInvoiceItemBetween(String value1, String value2) {
            addCriterion("INVOICE_ITEM between", value1, value2, "invoiceItem");
            return (Criteria) this;
        }

        public Criteria andInvoiceItemNotBetween(String value1, String value2) {
            addCriterion("INVOICE_ITEM not between", value1, value2, "invoiceItem");
            return (Criteria) this;
        }

        public Criteria andUnitPriceIsNull() {
            addCriterion("UNIT_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andUnitPriceIsNotNull() {
            addCriterion("UNIT_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andUnitPriceEqualTo(BigDecimal value) {
            addCriterion("UNIT_PRICE =", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceNotEqualTo(BigDecimal value) {
            addCriterion("UNIT_PRICE <>", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceGreaterThan(BigDecimal value) {
            addCriterion("UNIT_PRICE >", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("UNIT_PRICE >=", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceLessThan(BigDecimal value) {
            addCriterion("UNIT_PRICE <", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("UNIT_PRICE <=", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceIn(List<BigDecimal> values) {
            addCriterion("UNIT_PRICE in", values, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceNotIn(List<BigDecimal> values) {
            addCriterion("UNIT_PRICE not in", values, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("UNIT_PRICE between", value1, value2, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("UNIT_PRICE not between", value1, value2, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andNumberSlIsNull() {
            addCriterion("NUMBER_SL is null");
            return (Criteria) this;
        }

        public Criteria andNumberSlIsNotNull() {
            addCriterion("NUMBER_SL is not null");
            return (Criteria) this;
        }

        public Criteria andNumberSlEqualTo(Long value) {
            addCriterion("NUMBER_SL =", value, "numberSl");
            return (Criteria) this;
        }

        public Criteria andNumberSlNotEqualTo(Long value) {
            addCriterion("NUMBER_SL <>", value, "numberSl");
            return (Criteria) this;
        }

        public Criteria andNumberSlGreaterThan(Long value) {
            addCriterion("NUMBER_SL >", value, "numberSl");
            return (Criteria) this;
        }

        public Criteria andNumberSlGreaterThanOrEqualTo(Long value) {
            addCriterion("NUMBER_SL >=", value, "numberSl");
            return (Criteria) this;
        }

        public Criteria andNumberSlLessThan(Long value) {
            addCriterion("NUMBER_SL <", value, "numberSl");
            return (Criteria) this;
        }

        public Criteria andNumberSlLessThanOrEqualTo(Long value) {
            addCriterion("NUMBER_SL <=", value, "numberSl");
            return (Criteria) this;
        }

        public Criteria andNumberSlIn(List<Long> values) {
            addCriterion("NUMBER_SL in", values, "numberSl");
            return (Criteria) this;
        }

        public Criteria andNumberSlNotIn(List<Long> values) {
            addCriterion("NUMBER_SL not in", values, "numberSl");
            return (Criteria) this;
        }

        public Criteria andNumberSlBetween(Long value1, Long value2) {
            addCriterion("NUMBER_SL between", value1, value2, "numberSl");
            return (Criteria) this;
        }

        public Criteria andNumberSlNotBetween(Long value1, Long value2) {
            addCriterion("NUMBER_SL not between", value1, value2, "numberSl");
            return (Criteria) this;
        }

        public Criteria andSumAmtIsNull() {
            addCriterion("SUM_AMT is null");
            return (Criteria) this;
        }

        public Criteria andSumAmtIsNotNull() {
            addCriterion("SUM_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andSumAmtEqualTo(BigDecimal value) {
            addCriterion("SUM_AMT =", value, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtNotEqualTo(BigDecimal value) {
            addCriterion("SUM_AMT <>", value, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtGreaterThan(BigDecimal value) {
            addCriterion("SUM_AMT >", value, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUM_AMT >=", value, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtLessThan(BigDecimal value) {
            addCriterion("SUM_AMT <", value, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUM_AMT <=", value, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtIn(List<BigDecimal> values) {
            addCriterion("SUM_AMT in", values, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtNotIn(List<BigDecimal> values) {
            addCriterion("SUM_AMT not in", values, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUM_AMT between", value1, value2, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUM_AMT not between", value1, value2, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andTaxIsNull() {
            addCriterion("TAX is null");
            return (Criteria) this;
        }

        public Criteria andTaxIsNotNull() {
            addCriterion("TAX is not null");
            return (Criteria) this;
        }

        public Criteria andTaxEqualTo(BigDecimal value) {
            addCriterion("TAX =", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotEqualTo(BigDecimal value) {
            addCriterion("TAX <>", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxGreaterThan(BigDecimal value) {
            addCriterion("TAX >", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX >=", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxLessThan(BigDecimal value) {
            addCriterion("TAX <", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX <=", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxIn(List<BigDecimal> values) {
            addCriterion("TAX in", values, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotIn(List<BigDecimal> values) {
            addCriterion("TAX not in", values, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX between", value1, value2, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX not between", value1, value2, "tax");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyIsNull() {
            addCriterion("EXPRESS_COMPANY is null");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyIsNotNull() {
            addCriterion("EXPRESS_COMPANY is not null");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyEqualTo(String value) {
            addCriterion("EXPRESS_COMPANY =", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotEqualTo(String value) {
            addCriterion("EXPRESS_COMPANY <>", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyGreaterThan(String value) {
            addCriterion("EXPRESS_COMPANY >", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("EXPRESS_COMPANY >=", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyLessThan(String value) {
            addCriterion("EXPRESS_COMPANY <", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyLessThanOrEqualTo(String value) {
            addCriterion("EXPRESS_COMPANY <=", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyLike(String value) {
            addCriterion("EXPRESS_COMPANY like", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotLike(String value) {
            addCriterion("EXPRESS_COMPANY not like", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyIn(List<String> values) {
            addCriterion("EXPRESS_COMPANY in", values, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotIn(List<String> values) {
            addCriterion("EXPRESS_COMPANY not in", values, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyBetween(String value1, String value2) {
            addCriterion("EXPRESS_COMPANY between", value1, value2, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotBetween(String value1, String value2) {
            addCriterion("EXPRESS_COMPANY not between", value1, value2, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressNumberIsNull() {
            addCriterion("EXPRESS_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andExpressNumberIsNotNull() {
            addCriterion("EXPRESS_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andExpressNumberEqualTo(String value) {
            addCriterion("EXPRESS_NUMBER =", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberNotEqualTo(String value) {
            addCriterion("EXPRESS_NUMBER <>", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberGreaterThan(String value) {
            addCriterion("EXPRESS_NUMBER >", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberGreaterThanOrEqualTo(String value) {
            addCriterion("EXPRESS_NUMBER >=", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberLessThan(String value) {
            addCriterion("EXPRESS_NUMBER <", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberLessThanOrEqualTo(String value) {
            addCriterion("EXPRESS_NUMBER <=", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberLike(String value) {
            addCriterion("EXPRESS_NUMBER like", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberNotLike(String value) {
            addCriterion("EXPRESS_NUMBER not like", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberIn(List<String> values) {
            addCriterion("EXPRESS_NUMBER in", values, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberNotIn(List<String> values) {
            addCriterion("EXPRESS_NUMBER not in", values, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberBetween(String value1, String value2) {
            addCriterion("EXPRESS_NUMBER between", value1, value2, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberNotBetween(String value1, String value2) {
            addCriterion("EXPRESS_NUMBER not between", value1, value2, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressDateIsNull() {
            addCriterion("EXPRESS_DATE is null");
            return (Criteria) this;
        }

        public Criteria andExpressDateIsNotNull() {
            addCriterion("EXPRESS_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andExpressDateEqualTo(String value) {
            addCriterion("EXPRESS_DATE =", value, "expressDate");
            return (Criteria) this;
        }

        public Criteria andExpressDateNotEqualTo(String value) {
            addCriterion("EXPRESS_DATE <>", value, "expressDate");
            return (Criteria) this;
        }

        public Criteria andExpressDateGreaterThan(String value) {
            addCriterion("EXPRESS_DATE >", value, "expressDate");
            return (Criteria) this;
        }

        public Criteria andExpressDateGreaterThanOrEqualTo(String value) {
            addCriterion("EXPRESS_DATE >=", value, "expressDate");
            return (Criteria) this;
        }

        public Criteria andExpressDateLessThan(String value) {
            addCriterion("EXPRESS_DATE <", value, "expressDate");
            return (Criteria) this;
        }

        public Criteria andExpressDateLessThanOrEqualTo(String value) {
            addCriterion("EXPRESS_DATE <=", value, "expressDate");
            return (Criteria) this;
        }

        public Criteria andExpressDateLike(String value) {
            addCriterion("EXPRESS_DATE like", value, "expressDate");
            return (Criteria) this;
        }

        public Criteria andExpressDateNotLike(String value) {
            addCriterion("EXPRESS_DATE not like", value, "expressDate");
            return (Criteria) this;
        }

        public Criteria andExpressDateIn(List<String> values) {
            addCriterion("EXPRESS_DATE in", values, "expressDate");
            return (Criteria) this;
        }

        public Criteria andExpressDateNotIn(List<String> values) {
            addCriterion("EXPRESS_DATE not in", values, "expressDate");
            return (Criteria) this;
        }

        public Criteria andExpressDateBetween(String value1, String value2) {
            addCriterion("EXPRESS_DATE between", value1, value2, "expressDate");
            return (Criteria) this;
        }

        public Criteria andExpressDateNotBetween(String value1, String value2) {
            addCriterion("EXPRESS_DATE not between", value1, value2, "expressDate");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkIsNull() {
            addCriterion("EXPRESS_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkIsNotNull() {
            addCriterion("EXPRESS_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkEqualTo(String value) {
            addCriterion("EXPRESS_REMARK =", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkNotEqualTo(String value) {
            addCriterion("EXPRESS_REMARK <>", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkGreaterThan(String value) {
            addCriterion("EXPRESS_REMARK >", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("EXPRESS_REMARK >=", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkLessThan(String value) {
            addCriterion("EXPRESS_REMARK <", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkLessThanOrEqualTo(String value) {
            addCriterion("EXPRESS_REMARK <=", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkLike(String value) {
            addCriterion("EXPRESS_REMARK like", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkNotLike(String value) {
            addCriterion("EXPRESS_REMARK not like", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkIn(List<String> values) {
            addCriterion("EXPRESS_REMARK in", values, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkNotIn(List<String> values) {
            addCriterion("EXPRESS_REMARK not in", values, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkBetween(String value1, String value2) {
            addCriterion("EXPRESS_REMARK between", value1, value2, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkNotBetween(String value1, String value2) {
            addCriterion("EXPRESS_REMARK not between", value1, value2, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andYsDateIsNull() {
            addCriterion("YS_DATE is null");
            return (Criteria) this;
        }

        public Criteria andYsDateIsNotNull() {
            addCriterion("YS_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andYsDateEqualTo(String value) {
            addCriterion("YS_DATE =", value, "ysDate");
            return (Criteria) this;
        }

        public Criteria andYsDateNotEqualTo(String value) {
            addCriterion("YS_DATE <>", value, "ysDate");
            return (Criteria) this;
        }

        public Criteria andYsDateGreaterThan(String value) {
            addCriterion("YS_DATE >", value, "ysDate");
            return (Criteria) this;
        }

        public Criteria andYsDateGreaterThanOrEqualTo(String value) {
            addCriterion("YS_DATE >=", value, "ysDate");
            return (Criteria) this;
        }

        public Criteria andYsDateLessThan(String value) {
            addCriterion("YS_DATE <", value, "ysDate");
            return (Criteria) this;
        }

        public Criteria andYsDateLessThanOrEqualTo(String value) {
            addCriterion("YS_DATE <=", value, "ysDate");
            return (Criteria) this;
        }

        public Criteria andYsDateLike(String value) {
            addCriterion("YS_DATE like", value, "ysDate");
            return (Criteria) this;
        }

        public Criteria andYsDateNotLike(String value) {
            addCriterion("YS_DATE not like", value, "ysDate");
            return (Criteria) this;
        }

        public Criteria andYsDateIn(List<String> values) {
            addCriterion("YS_DATE in", values, "ysDate");
            return (Criteria) this;
        }

        public Criteria andYsDateNotIn(List<String> values) {
            addCriterion("YS_DATE not in", values, "ysDate");
            return (Criteria) this;
        }

        public Criteria andYsDateBetween(String value1, String value2) {
            addCriterion("YS_DATE between", value1, value2, "ysDate");
            return (Criteria) this;
        }

        public Criteria andYsDateNotBetween(String value1, String value2) {
            addCriterion("YS_DATE not between", value1, value2, "ysDate");
            return (Criteria) this;
        }

        public Criteria andEsDateIsNull() {
            addCriterion("ES_DATE is null");
            return (Criteria) this;
        }

        public Criteria andEsDateIsNotNull() {
            addCriterion("ES_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andEsDateEqualTo(String value) {
            addCriterion("ES_DATE =", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateNotEqualTo(String value) {
            addCriterion("ES_DATE <>", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateGreaterThan(String value) {
            addCriterion("ES_DATE >", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateGreaterThanOrEqualTo(String value) {
            addCriterion("ES_DATE >=", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateLessThan(String value) {
            addCriterion("ES_DATE <", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateLessThanOrEqualTo(String value) {
            addCriterion("ES_DATE <=", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateLike(String value) {
            addCriterion("ES_DATE like", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateNotLike(String value) {
            addCriterion("ES_DATE not like", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateIn(List<String> values) {
            addCriterion("ES_DATE in", values, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateNotIn(List<String> values) {
            addCriterion("ES_DATE not in", values, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateBetween(String value1, String value2) {
            addCriterion("ES_DATE between", value1, value2, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateNotBetween(String value1, String value2) {
            addCriterion("ES_DATE not between", value1, value2, "esDate");
            return (Criteria) this;
        }

        public Criteria andYsResultIsNull() {
            addCriterion("YS_RESULT is null");
            return (Criteria) this;
        }

        public Criteria andYsResultIsNotNull() {
            addCriterion("YS_RESULT is not null");
            return (Criteria) this;
        }

        public Criteria andYsResultEqualTo(String value) {
            addCriterion("YS_RESULT =", value, "ysResult");
            return (Criteria) this;
        }

        public Criteria andYsResultNotEqualTo(String value) {
            addCriterion("YS_RESULT <>", value, "ysResult");
            return (Criteria) this;
        }

        public Criteria andYsResultGreaterThan(String value) {
            addCriterion("YS_RESULT >", value, "ysResult");
            return (Criteria) this;
        }

        public Criteria andYsResultGreaterThanOrEqualTo(String value) {
            addCriterion("YS_RESULT >=", value, "ysResult");
            return (Criteria) this;
        }

        public Criteria andYsResultLessThan(String value) {
            addCriterion("YS_RESULT <", value, "ysResult");
            return (Criteria) this;
        }

        public Criteria andYsResultLessThanOrEqualTo(String value) {
            addCriterion("YS_RESULT <=", value, "ysResult");
            return (Criteria) this;
        }

        public Criteria andYsResultLike(String value) {
            addCriterion("YS_RESULT like", value, "ysResult");
            return (Criteria) this;
        }

        public Criteria andYsResultNotLike(String value) {
            addCriterion("YS_RESULT not like", value, "ysResult");
            return (Criteria) this;
        }

        public Criteria andYsResultIn(List<String> values) {
            addCriterion("YS_RESULT in", values, "ysResult");
            return (Criteria) this;
        }

        public Criteria andYsResultNotIn(List<String> values) {
            addCriterion("YS_RESULT not in", values, "ysResult");
            return (Criteria) this;
        }

        public Criteria andYsResultBetween(String value1, String value2) {
            addCriterion("YS_RESULT between", value1, value2, "ysResult");
            return (Criteria) this;
        }

        public Criteria andYsResultNotBetween(String value1, String value2) {
            addCriterion("YS_RESULT not between", value1, value2, "ysResult");
            return (Criteria) this;
        }

        public Criteria andReturnReasonIsNull() {
            addCriterion("RETURN_REASON is null");
            return (Criteria) this;
        }

        public Criteria andReturnReasonIsNotNull() {
            addCriterion("RETURN_REASON is not null");
            return (Criteria) this;
        }

        public Criteria andReturnReasonEqualTo(String value) {
            addCriterion("RETURN_REASON =", value, "returnReason");
            return (Criteria) this;
        }

        public Criteria andReturnReasonNotEqualTo(String value) {
            addCriterion("RETURN_REASON <>", value, "returnReason");
            return (Criteria) this;
        }

        public Criteria andReturnReasonGreaterThan(String value) {
            addCriterion("RETURN_REASON >", value, "returnReason");
            return (Criteria) this;
        }

        public Criteria andReturnReasonGreaterThanOrEqualTo(String value) {
            addCriterion("RETURN_REASON >=", value, "returnReason");
            return (Criteria) this;
        }

        public Criteria andReturnReasonLessThan(String value) {
            addCriterion("RETURN_REASON <", value, "returnReason");
            return (Criteria) this;
        }

        public Criteria andReturnReasonLessThanOrEqualTo(String value) {
            addCriterion("RETURN_REASON <=", value, "returnReason");
            return (Criteria) this;
        }

        public Criteria andReturnReasonLike(String value) {
            addCriterion("RETURN_REASON like", value, "returnReason");
            return (Criteria) this;
        }

        public Criteria andReturnReasonNotLike(String value) {
            addCriterion("RETURN_REASON not like", value, "returnReason");
            return (Criteria) this;
        }

        public Criteria andReturnReasonIn(List<String> values) {
            addCriterion("RETURN_REASON in", values, "returnReason");
            return (Criteria) this;
        }

        public Criteria andReturnReasonNotIn(List<String> values) {
            addCriterion("RETURN_REASON not in", values, "returnReason");
            return (Criteria) this;
        }

        public Criteria andReturnReasonBetween(String value1, String value2) {
            addCriterion("RETURN_REASON between", value1, value2, "returnReason");
            return (Criteria) this;
        }

        public Criteria andReturnReasonNotBetween(String value1, String value2) {
            addCriterion("RETURN_REASON not between", value1, value2, "returnReason");
            return (Criteria) this;
        }

        public Criteria andReturnDateIsNull() {
            addCriterion("RETURN_DATE is null");
            return (Criteria) this;
        }

        public Criteria andReturnDateIsNotNull() {
            addCriterion("RETURN_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andReturnDateEqualTo(String value) {
            addCriterion("RETURN_DATE =", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateNotEqualTo(String value) {
            addCriterion("RETURN_DATE <>", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateGreaterThan(String value) {
            addCriterion("RETURN_DATE >", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateGreaterThanOrEqualTo(String value) {
            addCriterion("RETURN_DATE >=", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateLessThan(String value) {
            addCriterion("RETURN_DATE <", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateLessThanOrEqualTo(String value) {
            addCriterion("RETURN_DATE <=", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateLike(String value) {
            addCriterion("RETURN_DATE like", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateNotLike(String value) {
            addCriterion("RETURN_DATE not like", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateIn(List<String> values) {
            addCriterion("RETURN_DATE in", values, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateNotIn(List<String> values) {
            addCriterion("RETURN_DATE not in", values, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateBetween(String value1, String value2) {
            addCriterion("RETURN_DATE between", value1, value2, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateNotBetween(String value1, String value2) {
            addCriterion("RETURN_DATE not between", value1, value2, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnExpressNumberIsNull() {
            addCriterion("RETURN_EXPRESS_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andReturnExpressNumberIsNotNull() {
            addCriterion("RETURN_EXPRESS_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andReturnExpressNumberEqualTo(String value) {
            addCriterion("RETURN_EXPRESS_NUMBER =", value, "returnExpressNumber");
            return (Criteria) this;
        }

        public Criteria andReturnExpressNumberNotEqualTo(String value) {
            addCriterion("RETURN_EXPRESS_NUMBER <>", value, "returnExpressNumber");
            return (Criteria) this;
        }

        public Criteria andReturnExpressNumberGreaterThan(String value) {
            addCriterion("RETURN_EXPRESS_NUMBER >", value, "returnExpressNumber");
            return (Criteria) this;
        }

        public Criteria andReturnExpressNumberGreaterThanOrEqualTo(String value) {
            addCriterion("RETURN_EXPRESS_NUMBER >=", value, "returnExpressNumber");
            return (Criteria) this;
        }

        public Criteria andReturnExpressNumberLessThan(String value) {
            addCriterion("RETURN_EXPRESS_NUMBER <", value, "returnExpressNumber");
            return (Criteria) this;
        }

        public Criteria andReturnExpressNumberLessThanOrEqualTo(String value) {
            addCriterion("RETURN_EXPRESS_NUMBER <=", value, "returnExpressNumber");
            return (Criteria) this;
        }

        public Criteria andReturnExpressNumberLike(String value) {
            addCriterion("RETURN_EXPRESS_NUMBER like", value, "returnExpressNumber");
            return (Criteria) this;
        }

        public Criteria andReturnExpressNumberNotLike(String value) {
            addCriterion("RETURN_EXPRESS_NUMBER not like", value, "returnExpressNumber");
            return (Criteria) this;
        }

        public Criteria andReturnExpressNumberIn(List<String> values) {
            addCriterion("RETURN_EXPRESS_NUMBER in", values, "returnExpressNumber");
            return (Criteria) this;
        }

        public Criteria andReturnExpressNumberNotIn(List<String> values) {
            addCriterion("RETURN_EXPRESS_NUMBER not in", values, "returnExpressNumber");
            return (Criteria) this;
        }

        public Criteria andReturnExpressNumberBetween(String value1, String value2) {
            addCriterion("RETURN_EXPRESS_NUMBER between", value1, value2, "returnExpressNumber");
            return (Criteria) this;
        }

        public Criteria andReturnExpressNumberNotBetween(String value1, String value2) {
            addCriterion("RETURN_EXPRESS_NUMBER not between", value1, value2, "returnExpressNumber");
            return (Criteria) this;
        }

        public Criteria andReturnExpressCompanyIsNull() {
            addCriterion("RETURN_EXPRESS_COMPANY is null");
            return (Criteria) this;
        }

        public Criteria andReturnExpressCompanyIsNotNull() {
            addCriterion("RETURN_EXPRESS_COMPANY is not null");
            return (Criteria) this;
        }

        public Criteria andReturnExpressCompanyEqualTo(String value) {
            addCriterion("RETURN_EXPRESS_COMPANY =", value, "returnExpressCompany");
            return (Criteria) this;
        }

        public Criteria andReturnExpressCompanyNotEqualTo(String value) {
            addCriterion("RETURN_EXPRESS_COMPANY <>", value, "returnExpressCompany");
            return (Criteria) this;
        }

        public Criteria andReturnExpressCompanyGreaterThan(String value) {
            addCriterion("RETURN_EXPRESS_COMPANY >", value, "returnExpressCompany");
            return (Criteria) this;
        }

        public Criteria andReturnExpressCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("RETURN_EXPRESS_COMPANY >=", value, "returnExpressCompany");
            return (Criteria) this;
        }

        public Criteria andReturnExpressCompanyLessThan(String value) {
            addCriterion("RETURN_EXPRESS_COMPANY <", value, "returnExpressCompany");
            return (Criteria) this;
        }

        public Criteria andReturnExpressCompanyLessThanOrEqualTo(String value) {
            addCriterion("RETURN_EXPRESS_COMPANY <=", value, "returnExpressCompany");
            return (Criteria) this;
        }

        public Criteria andReturnExpressCompanyLike(String value) {
            addCriterion("RETURN_EXPRESS_COMPANY like", value, "returnExpressCompany");
            return (Criteria) this;
        }

        public Criteria andReturnExpressCompanyNotLike(String value) {
            addCriterion("RETURN_EXPRESS_COMPANY not like", value, "returnExpressCompany");
            return (Criteria) this;
        }

        public Criteria andReturnExpressCompanyIn(List<String> values) {
            addCriterion("RETURN_EXPRESS_COMPANY in", values, "returnExpressCompany");
            return (Criteria) this;
        }

        public Criteria andReturnExpressCompanyNotIn(List<String> values) {
            addCriterion("RETURN_EXPRESS_COMPANY not in", values, "returnExpressCompany");
            return (Criteria) this;
        }

        public Criteria andReturnExpressCompanyBetween(String value1, String value2) {
            addCriterion("RETURN_EXPRESS_COMPANY between", value1, value2, "returnExpressCompany");
            return (Criteria) this;
        }

        public Criteria andReturnExpressCompanyNotBetween(String value1, String value2) {
            addCriterion("RETURN_EXPRESS_COMPANY not between", value1, value2, "returnExpressCompany");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("CREATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("CREATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(String value) {
            addCriterion("CREATE_DATE =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(String value) {
            addCriterion("CREATE_DATE <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(String value) {
            addCriterion("CREATE_DATE >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_DATE >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(String value) {
            addCriterion("CREATE_DATE <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(String value) {
            addCriterion("CREATE_DATE <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLike(String value) {
            addCriterion("CREATE_DATE like", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotLike(String value) {
            addCriterion("CREATE_DATE not like", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<String> values) {
            addCriterion("CREATE_DATE in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<String> values) {
            addCriterion("CREATE_DATE not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(String value1, String value2) {
            addCriterion("CREATE_DATE between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(String value1, String value2) {
            addCriterion("CREATE_DATE not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNull() {
            addCriterion("CREATE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNotNull() {
            addCriterion("CREATE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateNameEqualTo(String value) {
            addCriterion("CREATE_NAME =", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotEqualTo(String value) {
            addCriterion("CREATE_NAME <>", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThan(String value) {
            addCriterion("CREATE_NAME >", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_NAME >=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThan(String value) {
            addCriterion("CREATE_NAME <", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThanOrEqualTo(String value) {
            addCriterion("CREATE_NAME <=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLike(String value) {
            addCriterion("CREATE_NAME like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotLike(String value) {
            addCriterion("CREATE_NAME not like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameIn(List<String> values) {
            addCriterion("CREATE_NAME in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotIn(List<String> values) {
            addCriterion("CREATE_NAME not in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameBetween(String value1, String value2) {
            addCriterion("CREATE_NAME between", value1, value2, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotBetween(String value1, String value2) {
            addCriterion("CREATE_NAME not between", value1, value2, "createName");
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

        public Criteria andInvoiceAmtMonthIsNull() {
            addCriterion("INVOICE_AMT_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtMonthIsNotNull() {
            addCriterion("INVOICE_AMT_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtMonthEqualTo(String value) {
            addCriterion("INVOICE_AMT_MONTH =", value, "invoiceAmtMonth");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtMonthNotEqualTo(String value) {
            addCriterion("INVOICE_AMT_MONTH <>", value, "invoiceAmtMonth");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtMonthGreaterThan(String value) {
            addCriterion("INVOICE_AMT_MONTH >", value, "invoiceAmtMonth");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtMonthGreaterThanOrEqualTo(String value) {
            addCriterion("INVOICE_AMT_MONTH >=", value, "invoiceAmtMonth");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtMonthLessThan(String value) {
            addCriterion("INVOICE_AMT_MONTH <", value, "invoiceAmtMonth");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtMonthLessThanOrEqualTo(String value) {
            addCriterion("INVOICE_AMT_MONTH <=", value, "invoiceAmtMonth");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtMonthLike(String value) {
            addCriterion("INVOICE_AMT_MONTH like", value, "invoiceAmtMonth");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtMonthNotLike(String value) {
            addCriterion("INVOICE_AMT_MONTH not like", value, "invoiceAmtMonth");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtMonthIn(List<String> values) {
            addCriterion("INVOICE_AMT_MONTH in", values, "invoiceAmtMonth");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtMonthNotIn(List<String> values) {
            addCriterion("INVOICE_AMT_MONTH not in", values, "invoiceAmtMonth");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtMonthBetween(String value1, String value2) {
            addCriterion("INVOICE_AMT_MONTH between", value1, value2, "invoiceAmtMonth");
            return (Criteria) this;
        }

        public Criteria andInvoiceAmtMonthNotBetween(String value1, String value2) {
            addCriterion("INVOICE_AMT_MONTH not between", value1, value2, "invoiceAmtMonth");
            return (Criteria) this;
        }

        public Criteria andEsResultIsNull() {
            addCriterion("ES_RESULT is null");
            return (Criteria) this;
        }

        public Criteria andEsResultIsNotNull() {
            addCriterion("ES_RESULT is not null");
            return (Criteria) this;
        }

        public Criteria andEsResultEqualTo(String value) {
            addCriterion("ES_RESULT =", value, "esResult");
            return (Criteria) this;
        }

        public Criteria andEsResultNotEqualTo(String value) {
            addCriterion("ES_RESULT <>", value, "esResult");
            return (Criteria) this;
        }

        public Criteria andEsResultGreaterThan(String value) {
            addCriterion("ES_RESULT >", value, "esResult");
            return (Criteria) this;
        }

        public Criteria andEsResultGreaterThanOrEqualTo(String value) {
            addCriterion("ES_RESULT >=", value, "esResult");
            return (Criteria) this;
        }

        public Criteria andEsResultLessThan(String value) {
            addCriterion("ES_RESULT <", value, "esResult");
            return (Criteria) this;
        }

        public Criteria andEsResultLessThanOrEqualTo(String value) {
            addCriterion("ES_RESULT <=", value, "esResult");
            return (Criteria) this;
        }

        public Criteria andEsResultLike(String value) {
            addCriterion("ES_RESULT like", value, "esResult");
            return (Criteria) this;
        }

        public Criteria andEsResultNotLike(String value) {
            addCriterion("ES_RESULT not like", value, "esResult");
            return (Criteria) this;
        }

        public Criteria andEsResultIn(List<String> values) {
            addCriterion("ES_RESULT in", values, "esResult");
            return (Criteria) this;
        }

        public Criteria andEsResultNotIn(List<String> values) {
            addCriterion("ES_RESULT not in", values, "esResult");
            return (Criteria) this;
        }

        public Criteria andEsResultBetween(String value1, String value2) {
            addCriterion("ES_RESULT between", value1, value2, "esResult");
            return (Criteria) this;
        }

        public Criteria andEsResultNotBetween(String value1, String value2) {
            addCriterion("ES_RESULT not between", value1, value2, "esResult");
            return (Criteria) this;
        }

        public Criteria andAmountTaxIsNull() {
            addCriterion("AMOUNT_TAX is null");
            return (Criteria) this;
        }

        public Criteria andAmountTaxIsNotNull() {
            addCriterion("AMOUNT_TAX is not null");
            return (Criteria) this;
        }

        public Criteria andAmountTaxEqualTo(BigDecimal value) {
            addCriterion("AMOUNT_TAX =", value, "amountTax");
            return (Criteria) this;
        }

        public Criteria andAmountTaxNotEqualTo(BigDecimal value) {
            addCriterion("AMOUNT_TAX <>", value, "amountTax");
            return (Criteria) this;
        }

        public Criteria andAmountTaxGreaterThan(BigDecimal value) {
            addCriterion("AMOUNT_TAX >", value, "amountTax");
            return (Criteria) this;
        }

        public Criteria andAmountTaxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AMOUNT_TAX >=", value, "amountTax");
            return (Criteria) this;
        }

        public Criteria andAmountTaxLessThan(BigDecimal value) {
            addCriterion("AMOUNT_TAX <", value, "amountTax");
            return (Criteria) this;
        }

        public Criteria andAmountTaxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AMOUNT_TAX <=", value, "amountTax");
            return (Criteria) this;
        }

        public Criteria andAmountTaxIn(List<BigDecimal> values) {
            addCriterion("AMOUNT_TAX in", values, "amountTax");
            return (Criteria) this;
        }

        public Criteria andAmountTaxNotIn(List<BigDecimal> values) {
            addCriterion("AMOUNT_TAX not in", values, "amountTax");
            return (Criteria) this;
        }

        public Criteria andAmountTaxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AMOUNT_TAX between", value1, value2, "amountTax");
            return (Criteria) this;
        }

        public Criteria andAmountTaxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AMOUNT_TAX not between", value1, value2, "amountTax");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("STATUS like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("STATUS not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNull() {
            addCriterion("SERIAL_NO is null");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNotNull() {
            addCriterion("SERIAL_NO is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNoEqualTo(String value) {
            addCriterion("SERIAL_NO =", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotEqualTo(String value) {
            addCriterion("SERIAL_NO <>", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThan(String value) {
            addCriterion("SERIAL_NO >", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThanOrEqualTo(String value) {
            addCriterion("SERIAL_NO >=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThan(String value) {
            addCriterion("SERIAL_NO <", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThanOrEqualTo(String value) {
            addCriterion("SERIAL_NO <=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLike(String value) {
            addCriterion("SERIAL_NO like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotLike(String value) {
            addCriterion("SERIAL_NO not like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoIn(List<String> values) {
            addCriterion("SERIAL_NO in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotIn(List<String> values) {
            addCriterion("SERIAL_NO not in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoBetween(String value1, String value2) {
            addCriterion("SERIAL_NO between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotBetween(String value1, String value2) {
            addCriterion("SERIAL_NO not between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("AMOUNT =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("AMOUNT <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("AMOUNT >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AMOUNT >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("AMOUNT <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AMOUNT <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("AMOUNT in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("AMOUNT not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AMOUNT between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AMOUNT not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andExpenseStatusIsNull() {
            addCriterion("EXPENSE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andExpenseStatusIsNotNull() {
            addCriterion("EXPENSE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andExpenseStatusEqualTo(String value) {
            addCriterion("EXPENSE_STATUS =", value, "expenseStatus");
            return (Criteria) this;
        }

        public Criteria andExpenseStatusNotEqualTo(String value) {
            addCriterion("EXPENSE_STATUS <>", value, "expenseStatus");
            return (Criteria) this;
        }

        public Criteria andExpenseStatusGreaterThan(String value) {
            addCriterion("EXPENSE_STATUS >", value, "expenseStatus");
            return (Criteria) this;
        }

        public Criteria andExpenseStatusGreaterThanOrEqualTo(String value) {
            addCriterion("EXPENSE_STATUS >=", value, "expenseStatus");
            return (Criteria) this;
        }

        public Criteria andExpenseStatusLessThan(String value) {
            addCriterion("EXPENSE_STATUS <", value, "expenseStatus");
            return (Criteria) this;
        }

        public Criteria andExpenseStatusLessThanOrEqualTo(String value) {
            addCriterion("EXPENSE_STATUS <=", value, "expenseStatus");
            return (Criteria) this;
        }

        public Criteria andExpenseStatusLike(String value) {
            addCriterion("EXPENSE_STATUS like", value, "expenseStatus");
            return (Criteria) this;
        }

        public Criteria andExpenseStatusNotLike(String value) {
            addCriterion("EXPENSE_STATUS not like", value, "expenseStatus");
            return (Criteria) this;
        }

        public Criteria andExpenseStatusIn(List<String> values) {
            addCriterion("EXPENSE_STATUS in", values, "expenseStatus");
            return (Criteria) this;
        }

        public Criteria andExpenseStatusNotIn(List<String> values) {
            addCriterion("EXPENSE_STATUS not in", values, "expenseStatus");
            return (Criteria) this;
        }

        public Criteria andExpenseStatusBetween(String value1, String value2) {
            addCriterion("EXPENSE_STATUS between", value1, value2, "expenseStatus");
            return (Criteria) this;
        }

        public Criteria andExpenseStatusNotBetween(String value1, String value2) {
            addCriterion("EXPENSE_STATUS not between", value1, value2, "expenseStatus");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeIsNull() {
            addCriterion("INVOICE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeIsNotNull() {
            addCriterion("INVOICE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeEqualTo(String value) {
            addCriterion("INVOICE_TYPE =", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeNotEqualTo(String value) {
            addCriterion("INVOICE_TYPE <>", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeGreaterThan(String value) {
            addCriterion("INVOICE_TYPE >", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("INVOICE_TYPE >=", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeLessThan(String value) {
            addCriterion("INVOICE_TYPE <", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeLessThanOrEqualTo(String value) {
            addCriterion("INVOICE_TYPE <=", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeLike(String value) {
            addCriterion("INVOICE_TYPE like", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeNotLike(String value) {
            addCriterion("INVOICE_TYPE not like", value, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeIn(List<String> values) {
            addCriterion("INVOICE_TYPE in", values, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeNotIn(List<String> values) {
            addCriterion("INVOICE_TYPE not in", values, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeBetween(String value1, String value2) {
            addCriterion("INVOICE_TYPE between", value1, value2, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andInvoiceTypeNotBetween(String value1, String value2) {
            addCriterion("INVOICE_TYPE not between", value1, value2, "invoiceType");
            return (Criteria) this;
        }

        public Criteria andSallerNameIsNull() {
            addCriterion("SALLER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSallerNameIsNotNull() {
            addCriterion("SALLER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSallerNameEqualTo(String value) {
            addCriterion("SALLER_NAME =", value, "sallerName");
            return (Criteria) this;
        }

        public Criteria andSallerNameNotEqualTo(String value) {
            addCriterion("SALLER_NAME <>", value, "sallerName");
            return (Criteria) this;
        }

        public Criteria andSallerNameGreaterThan(String value) {
            addCriterion("SALLER_NAME >", value, "sallerName");
            return (Criteria) this;
        }

        public Criteria andSallerNameGreaterThanOrEqualTo(String value) {
            addCriterion("SALLER_NAME >=", value, "sallerName");
            return (Criteria) this;
        }

        public Criteria andSallerNameLessThan(String value) {
            addCriterion("SALLER_NAME <", value, "sallerName");
            return (Criteria) this;
        }

        public Criteria andSallerNameLessThanOrEqualTo(String value) {
            addCriterion("SALLER_NAME <=", value, "sallerName");
            return (Criteria) this;
        }

        public Criteria andSallerNameLike(String value) {
            addCriterion("SALLER_NAME like", value, "sallerName");
            return (Criteria) this;
        }

        public Criteria andSallerNameNotLike(String value) {
            addCriterion("SALLER_NAME not like", value, "sallerName");
            return (Criteria) this;
        }

        public Criteria andSallerNameIn(List<String> values) {
            addCriterion("SALLER_NAME in", values, "sallerName");
            return (Criteria) this;
        }

        public Criteria andSallerNameNotIn(List<String> values) {
            addCriterion("SALLER_NAME not in", values, "sallerName");
            return (Criteria) this;
        }

        public Criteria andSallerNameBetween(String value1, String value2) {
            addCriterion("SALLER_NAME between", value1, value2, "sallerName");
            return (Criteria) this;
        }

        public Criteria andSallerNameNotBetween(String value1, String value2) {
            addCriterion("SALLER_NAME not between", value1, value2, "sallerName");
            return (Criteria) this;
        }

        public Criteria andSallerNoIsNull() {
            addCriterion("SALLER_NO is null");
            return (Criteria) this;
        }

        public Criteria andSallerNoIsNotNull() {
            addCriterion("SALLER_NO is not null");
            return (Criteria) this;
        }

        public Criteria andSallerNoEqualTo(String value) {
            addCriterion("SALLER_NO =", value, "sallerNo");
            return (Criteria) this;
        }

        public Criteria andSallerNoNotEqualTo(String value) {
            addCriterion("SALLER_NO <>", value, "sallerNo");
            return (Criteria) this;
        }

        public Criteria andSallerNoGreaterThan(String value) {
            addCriterion("SALLER_NO >", value, "sallerNo");
            return (Criteria) this;
        }

        public Criteria andSallerNoGreaterThanOrEqualTo(String value) {
            addCriterion("SALLER_NO >=", value, "sallerNo");
            return (Criteria) this;
        }

        public Criteria andSallerNoLessThan(String value) {
            addCriterion("SALLER_NO <", value, "sallerNo");
            return (Criteria) this;
        }

        public Criteria andSallerNoLessThanOrEqualTo(String value) {
            addCriterion("SALLER_NO <=", value, "sallerNo");
            return (Criteria) this;
        }

        public Criteria andSallerNoLike(String value) {
            addCriterion("SALLER_NO like", value, "sallerNo");
            return (Criteria) this;
        }

        public Criteria andSallerNoNotLike(String value) {
            addCriterion("SALLER_NO not like", value, "sallerNo");
            return (Criteria) this;
        }

        public Criteria andSallerNoIn(List<String> values) {
            addCriterion("SALLER_NO in", values, "sallerNo");
            return (Criteria) this;
        }

        public Criteria andSallerNoNotIn(List<String> values) {
            addCriterion("SALLER_NO not in", values, "sallerNo");
            return (Criteria) this;
        }

        public Criteria andSallerNoBetween(String value1, String value2) {
            addCriterion("SALLER_NO between", value1, value2, "sallerNo");
            return (Criteria) this;
        }

        public Criteria andSallerNoNotBetween(String value1, String value2) {
            addCriterion("SALLER_NO not between", value1, value2, "sallerNo");
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

        public Criteria andRev4IsNull() {
            addCriterion("REV4 is null");
            return (Criteria) this;
        }

        public Criteria andRev4IsNotNull() {
            addCriterion("REV4 is not null");
            return (Criteria) this;
        }

        public Criteria andRev4EqualTo(String value) {
            addCriterion("REV4 =", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4NotEqualTo(String value) {
            addCriterion("REV4 <>", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4GreaterThan(String value) {
            addCriterion("REV4 >", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4GreaterThanOrEqualTo(String value) {
            addCriterion("REV4 >=", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4LessThan(String value) {
            addCriterion("REV4 <", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4LessThanOrEqualTo(String value) {
            addCriterion("REV4 <=", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4Like(String value) {
            addCriterion("REV4 like", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4NotLike(String value) {
            addCriterion("REV4 not like", value, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4In(List<String> values) {
            addCriterion("REV4 in", values, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4NotIn(List<String> values) {
            addCriterion("REV4 not in", values, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4Between(String value1, String value2) {
            addCriterion("REV4 between", value1, value2, "rev4");
            return (Criteria) this;
        }

        public Criteria andRev4NotBetween(String value1, String value2) {
            addCriterion("REV4 not between", value1, value2, "rev4");
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