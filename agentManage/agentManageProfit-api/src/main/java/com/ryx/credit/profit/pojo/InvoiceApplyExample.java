package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InvoiceApplyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

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

        public Criteria andAttachmentIsNull() {
            addCriterion("ATTACHMENT is null");
            return (Criteria) this;
        }

        public Criteria andAttachmentIsNotNull() {
            addCriterion("ATTACHMENT is not null");
            return (Criteria) this;
        }

        public Criteria andAttachmentEqualTo(String value) {
            addCriterion("ATTACHMENT =", value, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentNotEqualTo(String value) {
            addCriterion("ATTACHMENT <>", value, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentGreaterThan(String value) {
            addCriterion("ATTACHMENT >", value, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentGreaterThanOrEqualTo(String value) {
            addCriterion("ATTACHMENT >=", value, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentLessThan(String value) {
            addCriterion("ATTACHMENT <", value, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentLessThanOrEqualTo(String value) {
            addCriterion("ATTACHMENT <=", value, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentLike(String value) {
            addCriterion("ATTACHMENT like", value, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentNotLike(String value) {
            addCriterion("ATTACHMENT not like", value, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentIn(List<String> values) {
            addCriterion("ATTACHMENT in", values, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentNotIn(List<String> values) {
            addCriterion("ATTACHMENT not in", values, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentBetween(String value1, String value2) {
            addCriterion("ATTACHMENT between", value1, value2, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentNotBetween(String value1, String value2) {
            addCriterion("ATTACHMENT not between", value1, value2, "attachment");
            return (Criteria) this;
        }

        public Criteria andShDateIsNull() {
            addCriterion("SH_DATE is null");
            return (Criteria) this;
        }

        public Criteria andShDateIsNotNull() {
            addCriterion("SH_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andShDateEqualTo(String value) {
            addCriterion("SH_DATE =", value, "shDate");
            return (Criteria) this;
        }

        public Criteria andShDateNotEqualTo(String value) {
            addCriterion("SH_DATE <>", value, "shDate");
            return (Criteria) this;
        }

        public Criteria andShDateGreaterThan(String value) {
            addCriterion("SH_DATE >", value, "shDate");
            return (Criteria) this;
        }

        public Criteria andShDateGreaterThanOrEqualTo(String value) {
            addCriterion("SH_DATE >=", value, "shDate");
            return (Criteria) this;
        }

        public Criteria andShDateLessThan(String value) {
            addCriterion("SH_DATE <", value, "shDate");
            return (Criteria) this;
        }

        public Criteria andShDateLessThanOrEqualTo(String value) {
            addCriterion("SH_DATE <=", value, "shDate");
            return (Criteria) this;
        }

        public Criteria andShDateLike(String value) {
            addCriterion("SH_DATE like", value, "shDate");
            return (Criteria) this;
        }

        public Criteria andShDateNotLike(String value) {
            addCriterion("SH_DATE not like", value, "shDate");
            return (Criteria) this;
        }

        public Criteria andShDateIn(List<String> values) {
            addCriterion("SH_DATE in", values, "shDate");
            return (Criteria) this;
        }

        public Criteria andShDateNotIn(List<String> values) {
            addCriterion("SH_DATE not in", values, "shDate");
            return (Criteria) this;
        }

        public Criteria andShDateBetween(String value1, String value2) {
            addCriterion("SH_DATE between", value1, value2, "shDate");
            return (Criteria) this;
        }

        public Criteria andShDateNotBetween(String value1, String value2) {
            addCriterion("SH_DATE not between", value1, value2, "shDate");
            return (Criteria) this;
        }

        public Criteria andShNameIsNull() {
            addCriterion("SH_NAME is null");
            return (Criteria) this;
        }

        public Criteria andShNameIsNotNull() {
            addCriterion("SH_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andShNameEqualTo(String value) {
            addCriterion("SH_NAME =", value, "shName");
            return (Criteria) this;
        }

        public Criteria andShNameNotEqualTo(String value) {
            addCriterion("SH_NAME <>", value, "shName");
            return (Criteria) this;
        }

        public Criteria andShNameGreaterThan(String value) {
            addCriterion("SH_NAME >", value, "shName");
            return (Criteria) this;
        }

        public Criteria andShNameGreaterThanOrEqualTo(String value) {
            addCriterion("SH_NAME >=", value, "shName");
            return (Criteria) this;
        }

        public Criteria andShNameLessThan(String value) {
            addCriterion("SH_NAME <", value, "shName");
            return (Criteria) this;
        }

        public Criteria andShNameLessThanOrEqualTo(String value) {
            addCriterion("SH_NAME <=", value, "shName");
            return (Criteria) this;
        }

        public Criteria andShNameLike(String value) {
            addCriterion("SH_NAME like", value, "shName");
            return (Criteria) this;
        }

        public Criteria andShNameNotLike(String value) {
            addCriterion("SH_NAME not like", value, "shName");
            return (Criteria) this;
        }

        public Criteria andShNameIn(List<String> values) {
            addCriterion("SH_NAME in", values, "shName");
            return (Criteria) this;
        }

        public Criteria andShNameNotIn(List<String> values) {
            addCriterion("SH_NAME not in", values, "shName");
            return (Criteria) this;
        }

        public Criteria andShNameBetween(String value1, String value2) {
            addCriterion("SH_NAME between", value1, value2, "shName");
            return (Criteria) this;
        }

        public Criteria andShNameNotBetween(String value1, String value2) {
            addCriterion("SH_NAME not between", value1, value2, "shName");
            return (Criteria) this;
        }

        public Criteria andCurrentInvoiceIsNull() {
            addCriterion("CURRENT_INVOICE is null");
            return (Criteria) this;
        }

        public Criteria andCurrentInvoiceIsNotNull() {
            addCriterion("CURRENT_INVOICE is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentInvoiceEqualTo(BigDecimal value) {
            addCriterion("CURRENT_INVOICE =", value, "currentInvoice");
            return (Criteria) this;
        }

        public Criteria andCurrentInvoiceNotEqualTo(BigDecimal value) {
            addCriterion("CURRENT_INVOICE <>", value, "currentInvoice");
            return (Criteria) this;
        }

        public Criteria andCurrentInvoiceGreaterThan(BigDecimal value) {
            addCriterion("CURRENT_INVOICE >", value, "currentInvoice");
            return (Criteria) this;
        }

        public Criteria andCurrentInvoiceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CURRENT_INVOICE >=", value, "currentInvoice");
            return (Criteria) this;
        }

        public Criteria andCurrentInvoiceLessThan(BigDecimal value) {
            addCriterion("CURRENT_INVOICE <", value, "currentInvoice");
            return (Criteria) this;
        }

        public Criteria andCurrentInvoiceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CURRENT_INVOICE <=", value, "currentInvoice");
            return (Criteria) this;
        }

        public Criteria andCurrentInvoiceIn(List<BigDecimal> values) {
            addCriterion("CURRENT_INVOICE in", values, "currentInvoice");
            return (Criteria) this;
        }

        public Criteria andCurrentInvoiceNotIn(List<BigDecimal> values) {
            addCriterion("CURRENT_INVOICE not in", values, "currentInvoice");
            return (Criteria) this;
        }

        public Criteria andCurrentInvoiceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CURRENT_INVOICE between", value1, value2, "currentInvoice");
            return (Criteria) this;
        }

        public Criteria andCurrentInvoiceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CURRENT_INVOICE not between", value1, value2, "currentInvoice");
            return (Criteria) this;
        }

        public Criteria andShResultIsNull() {
            addCriterion("SH_RESULT is null");
            return (Criteria) this;
        }

        public Criteria andShResultIsNotNull() {
            addCriterion("SH_RESULT is not null");
            return (Criteria) this;
        }

        public Criteria andShResultEqualTo(String value) {
            addCriterion("SH_RESULT =", value, "shResult");
            return (Criteria) this;
        }

        public Criteria andShResultNotEqualTo(String value) {
            addCriterion("SH_RESULT <>", value, "shResult");
            return (Criteria) this;
        }

        public Criteria andShResultGreaterThan(String value) {
            addCriterion("SH_RESULT >", value, "shResult");
            return (Criteria) this;
        }

        public Criteria andShResultGreaterThanOrEqualTo(String value) {
            addCriterion("SH_RESULT >=", value, "shResult");
            return (Criteria) this;
        }

        public Criteria andShResultLessThan(String value) {
            addCriterion("SH_RESULT <", value, "shResult");
            return (Criteria) this;
        }

        public Criteria andShResultLessThanOrEqualTo(String value) {
            addCriterion("SH_RESULT <=", value, "shResult");
            return (Criteria) this;
        }

        public Criteria andShResultLike(String value) {
            addCriterion("SH_RESULT like", value, "shResult");
            return (Criteria) this;
        }

        public Criteria andShResultNotLike(String value) {
            addCriterion("SH_RESULT not like", value, "shResult");
            return (Criteria) this;
        }

        public Criteria andShResultIn(List<String> values) {
            addCriterion("SH_RESULT in", values, "shResult");
            return (Criteria) this;
        }

        public Criteria andShResultNotIn(List<String> values) {
            addCriterion("SH_RESULT not in", values, "shResult");
            return (Criteria) this;
        }

        public Criteria andShResultBetween(String value1, String value2) {
            addCriterion("SH_RESULT between", value1, value2, "shResult");
            return (Criteria) this;
        }

        public Criteria andShResultNotBetween(String value1, String value2) {
            addCriterion("SH_RESULT not between", value1, value2, "shResult");
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

        public Criteria andFilenameIsNull() {
            addCriterion("FILENAME is null");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNotNull() {
            addCriterion("FILENAME is not null");
            return (Criteria) this;
        }

        public Criteria andFilenameEqualTo(String value) {
            addCriterion("FILENAME =", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotEqualTo(String value) {
            addCriterion("FILENAME <>", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThan(String value) {
            addCriterion("FILENAME >", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThanOrEqualTo(String value) {
            addCriterion("FILENAME >=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThan(String value) {
            addCriterion("FILENAME <", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThanOrEqualTo(String value) {
            addCriterion("FILENAME <=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLike(String value) {
            addCriterion("FILENAME like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotLike(String value) {
            addCriterion("FILENAME not like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameIn(List<String> values) {
            addCriterion("FILENAME in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotIn(List<String> values) {
            addCriterion("FILENAME not in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameBetween(String value1, String value2) {
            addCriterion("FILENAME between", value1, value2, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotBetween(String value1, String value2) {
            addCriterion("FILENAME not between", value1, value2, "filename");
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