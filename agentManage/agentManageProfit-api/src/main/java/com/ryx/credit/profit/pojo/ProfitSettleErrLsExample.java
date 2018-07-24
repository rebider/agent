package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProfitSettleErrLsExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ProfitSettleErrLsExample() {
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

        public Criteria andErrCodeIsNull() {
            addCriterion("ERR_CODE is null");
            return (Criteria) this;
        }

        public Criteria andErrCodeIsNotNull() {
            addCriterion("ERR_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andErrCodeEqualTo(String value) {
            addCriterion("ERR_CODE =", value, "errCode");
            return (Criteria) this;
        }

        public Criteria andErrCodeNotEqualTo(String value) {
            addCriterion("ERR_CODE <>", value, "errCode");
            return (Criteria) this;
        }

        public Criteria andErrCodeGreaterThan(String value) {
            addCriterion("ERR_CODE >", value, "errCode");
            return (Criteria) this;
        }

        public Criteria andErrCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ERR_CODE >=", value, "errCode");
            return (Criteria) this;
        }

        public Criteria andErrCodeLessThan(String value) {
            addCriterion("ERR_CODE <", value, "errCode");
            return (Criteria) this;
        }

        public Criteria andErrCodeLessThanOrEqualTo(String value) {
            addCriterion("ERR_CODE <=", value, "errCode");
            return (Criteria) this;
        }

        public Criteria andErrCodeLike(String value) {
            addCriterion("ERR_CODE like", value, "errCode");
            return (Criteria) this;
        }

        public Criteria andErrCodeNotLike(String value) {
            addCriterion("ERR_CODE not like", value, "errCode");
            return (Criteria) this;
        }

        public Criteria andErrCodeIn(List<String> values) {
            addCriterion("ERR_CODE in", values, "errCode");
            return (Criteria) this;
        }

        public Criteria andErrCodeNotIn(List<String> values) {
            addCriterion("ERR_CODE not in", values, "errCode");
            return (Criteria) this;
        }

        public Criteria andErrCodeBetween(String value1, String value2) {
            addCriterion("ERR_CODE between", value1, value2, "errCode");
            return (Criteria) this;
        }

        public Criteria andErrCodeNotBetween(String value1, String value2) {
            addCriterion("ERR_CODE not between", value1, value2, "errCode");
            return (Criteria) this;
        }

        public Criteria andTranLsIsNull() {
            addCriterion("TRAN_LS is null");
            return (Criteria) this;
        }

        public Criteria andTranLsIsNotNull() {
            addCriterion("TRAN_LS is not null");
            return (Criteria) this;
        }

        public Criteria andTranLsEqualTo(String value) {
            addCriterion("TRAN_LS =", value, "tranLs");
            return (Criteria) this;
        }

        public Criteria andTranLsNotEqualTo(String value) {
            addCriterion("TRAN_LS <>", value, "tranLs");
            return (Criteria) this;
        }

        public Criteria andTranLsGreaterThan(String value) {
            addCriterion("TRAN_LS >", value, "tranLs");
            return (Criteria) this;
        }

        public Criteria andTranLsGreaterThanOrEqualTo(String value) {
            addCriterion("TRAN_LS >=", value, "tranLs");
            return (Criteria) this;
        }

        public Criteria andTranLsLessThan(String value) {
            addCriterion("TRAN_LS <", value, "tranLs");
            return (Criteria) this;
        }

        public Criteria andTranLsLessThanOrEqualTo(String value) {
            addCriterion("TRAN_LS <=", value, "tranLs");
            return (Criteria) this;
        }

        public Criteria andTranLsLike(String value) {
            addCriterion("TRAN_LS like", value, "tranLs");
            return (Criteria) this;
        }

        public Criteria andTranLsNotLike(String value) {
            addCriterion("TRAN_LS not like", value, "tranLs");
            return (Criteria) this;
        }

        public Criteria andTranLsIn(List<String> values) {
            addCriterion("TRAN_LS in", values, "tranLs");
            return (Criteria) this;
        }

        public Criteria andTranLsNotIn(List<String> values) {
            addCriterion("TRAN_LS not in", values, "tranLs");
            return (Criteria) this;
        }

        public Criteria andTranLsBetween(String value1, String value2) {
            addCriterion("TRAN_LS between", value1, value2, "tranLs");
            return (Criteria) this;
        }

        public Criteria andTranLsNotBetween(String value1, String value2) {
            addCriterion("TRAN_LS not between", value1, value2, "tranLs");
            return (Criteria) this;
        }

        public Criteria andTranDateIsNull() {
            addCriterion("TRAN_DATE is null");
            return (Criteria) this;
        }

        public Criteria andTranDateIsNotNull() {
            addCriterion("TRAN_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andTranDateEqualTo(String value) {
            addCriterion("TRAN_DATE =", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotEqualTo(String value) {
            addCriterion("TRAN_DATE <>", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateGreaterThan(String value) {
            addCriterion("TRAN_DATE >", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateGreaterThanOrEqualTo(String value) {
            addCriterion("TRAN_DATE >=", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateLessThan(String value) {
            addCriterion("TRAN_DATE <", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateLessThanOrEqualTo(String value) {
            addCriterion("TRAN_DATE <=", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateLike(String value) {
            addCriterion("TRAN_DATE like", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotLike(String value) {
            addCriterion("TRAN_DATE not like", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateIn(List<String> values) {
            addCriterion("TRAN_DATE in", values, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotIn(List<String> values) {
            addCriterion("TRAN_DATE not in", values, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateBetween(String value1, String value2) {
            addCriterion("TRAN_DATE between", value1, value2, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotBetween(String value1, String value2) {
            addCriterion("TRAN_DATE not between", value1, value2, "tranDate");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeIsNull() {
            addCriterion("BUSINESS_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeIsNotNull() {
            addCriterion("BUSINESS_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeEqualTo(String value) {
            addCriterion("BUSINESS_TYPE =", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotEqualTo(String value) {
            addCriterion("BUSINESS_TYPE <>", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeGreaterThan(String value) {
            addCriterion("BUSINESS_TYPE >", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeGreaterThanOrEqualTo(String value) {
            addCriterion("BUSINESS_TYPE >=", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeLessThan(String value) {
            addCriterion("BUSINESS_TYPE <", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeLessThanOrEqualTo(String value) {
            addCriterion("BUSINESS_TYPE <=", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeLike(String value) {
            addCriterion("BUSINESS_TYPE like", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotLike(String value) {
            addCriterion("BUSINESS_TYPE not like", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeIn(List<String> values) {
            addCriterion("BUSINESS_TYPE in", values, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotIn(List<String> values) {
            addCriterion("BUSINESS_TYPE not in", values, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeBetween(String value1, String value2) {
            addCriterion("BUSINESS_TYPE between", value1, value2, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotBetween(String value1, String value2) {
            addCriterion("BUSINESS_TYPE not between", value1, value2, "businessType");
            return (Criteria) this;
        }

        public Criteria andCooperationModeIsNull() {
            addCriterion("COOPERATION_MODE is null");
            return (Criteria) this;
        }

        public Criteria andCooperationModeIsNotNull() {
            addCriterion("COOPERATION_MODE is not null");
            return (Criteria) this;
        }

        public Criteria andCooperationModeEqualTo(String value) {
            addCriterion("COOPERATION_MODE =", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeNotEqualTo(String value) {
            addCriterion("COOPERATION_MODE <>", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeGreaterThan(String value) {
            addCriterion("COOPERATION_MODE >", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeGreaterThanOrEqualTo(String value) {
            addCriterion("COOPERATION_MODE >=", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeLessThan(String value) {
            addCriterion("COOPERATION_MODE <", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeLessThanOrEqualTo(String value) {
            addCriterion("COOPERATION_MODE <=", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeLike(String value) {
            addCriterion("COOPERATION_MODE like", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeNotLike(String value) {
            addCriterion("COOPERATION_MODE not like", value, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeIn(List<String> values) {
            addCriterion("COOPERATION_MODE in", values, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeNotIn(List<String> values) {
            addCriterion("COOPERATION_MODE not in", values, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeBetween(String value1, String value2) {
            addCriterion("COOPERATION_MODE between", value1, value2, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andCooperationModeNotBetween(String value1, String value2) {
            addCriterion("COOPERATION_MODE not between", value1, value2, "cooperationMode");
            return (Criteria) this;
        }

        public Criteria andMerchIdIsNull() {
            addCriterion("MERCH_ID is null");
            return (Criteria) this;
        }

        public Criteria andMerchIdIsNotNull() {
            addCriterion("MERCH_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMerchIdEqualTo(String value) {
            addCriterion("MERCH_ID =", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdNotEqualTo(String value) {
            addCriterion("MERCH_ID <>", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdGreaterThan(String value) {
            addCriterion("MERCH_ID >", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdGreaterThanOrEqualTo(String value) {
            addCriterion("MERCH_ID >=", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdLessThan(String value) {
            addCriterion("MERCH_ID <", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdLessThanOrEqualTo(String value) {
            addCriterion("MERCH_ID <=", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdLike(String value) {
            addCriterion("MERCH_ID like", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdNotLike(String value) {
            addCriterion("MERCH_ID not like", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdIn(List<String> values) {
            addCriterion("MERCH_ID in", values, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdNotIn(List<String> values) {
            addCriterion("MERCH_ID not in", values, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdBetween(String value1, String value2) {
            addCriterion("MERCH_ID between", value1, value2, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdNotBetween(String value1, String value2) {
            addCriterion("MERCH_ID not between", value1, value2, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchTypeIsNull() {
            addCriterion("MERCH_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andMerchTypeIsNotNull() {
            addCriterion("MERCH_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andMerchTypeEqualTo(String value) {
            addCriterion("MERCH_TYPE =", value, "merchType");
            return (Criteria) this;
        }

        public Criteria andMerchTypeNotEqualTo(String value) {
            addCriterion("MERCH_TYPE <>", value, "merchType");
            return (Criteria) this;
        }

        public Criteria andMerchTypeGreaterThan(String value) {
            addCriterion("MERCH_TYPE >", value, "merchType");
            return (Criteria) this;
        }

        public Criteria andMerchTypeGreaterThanOrEqualTo(String value) {
            addCriterion("MERCH_TYPE >=", value, "merchType");
            return (Criteria) this;
        }

        public Criteria andMerchTypeLessThan(String value) {
            addCriterion("MERCH_TYPE <", value, "merchType");
            return (Criteria) this;
        }

        public Criteria andMerchTypeLessThanOrEqualTo(String value) {
            addCriterion("MERCH_TYPE <=", value, "merchType");
            return (Criteria) this;
        }

        public Criteria andMerchTypeLike(String value) {
            addCriterion("MERCH_TYPE like", value, "merchType");
            return (Criteria) this;
        }

        public Criteria andMerchTypeNotLike(String value) {
            addCriterion("MERCH_TYPE not like", value, "merchType");
            return (Criteria) this;
        }

        public Criteria andMerchTypeIn(List<String> values) {
            addCriterion("MERCH_TYPE in", values, "merchType");
            return (Criteria) this;
        }

        public Criteria andMerchTypeNotIn(List<String> values) {
            addCriterion("MERCH_TYPE not in", values, "merchType");
            return (Criteria) this;
        }

        public Criteria andMerchTypeBetween(String value1, String value2) {
            addCriterion("MERCH_TYPE between", value1, value2, "merchType");
            return (Criteria) this;
        }

        public Criteria andMerchTypeNotBetween(String value1, String value2) {
            addCriterion("MERCH_TYPE not between", value1, value2, "merchType");
            return (Criteria) this;
        }

        public Criteria andErrDateIsNull() {
            addCriterion("ERR_DATE is null");
            return (Criteria) this;
        }

        public Criteria andErrDateIsNotNull() {
            addCriterion("ERR_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andErrDateEqualTo(String value) {
            addCriterion("ERR_DATE =", value, "errDate");
            return (Criteria) this;
        }

        public Criteria andErrDateNotEqualTo(String value) {
            addCriterion("ERR_DATE <>", value, "errDate");
            return (Criteria) this;
        }

        public Criteria andErrDateGreaterThan(String value) {
            addCriterion("ERR_DATE >", value, "errDate");
            return (Criteria) this;
        }

        public Criteria andErrDateGreaterThanOrEqualTo(String value) {
            addCriterion("ERR_DATE >=", value, "errDate");
            return (Criteria) this;
        }

        public Criteria andErrDateLessThan(String value) {
            addCriterion("ERR_DATE <", value, "errDate");
            return (Criteria) this;
        }

        public Criteria andErrDateLessThanOrEqualTo(String value) {
            addCriterion("ERR_DATE <=", value, "errDate");
            return (Criteria) this;
        }

        public Criteria andErrDateLike(String value) {
            addCriterion("ERR_DATE like", value, "errDate");
            return (Criteria) this;
        }

        public Criteria andErrDateNotLike(String value) {
            addCriterion("ERR_DATE not like", value, "errDate");
            return (Criteria) this;
        }

        public Criteria andErrDateIn(List<String> values) {
            addCriterion("ERR_DATE in", values, "errDate");
            return (Criteria) this;
        }

        public Criteria andErrDateNotIn(List<String> values) {
            addCriterion("ERR_DATE not in", values, "errDate");
            return (Criteria) this;
        }

        public Criteria andErrDateBetween(String value1, String value2) {
            addCriterion("ERR_DATE between", value1, value2, "errDate");
            return (Criteria) this;
        }

        public Criteria andErrDateNotBetween(String value1, String value2) {
            addCriterion("ERR_DATE not between", value1, value2, "errDate");
            return (Criteria) this;
        }

        public Criteria andChargebackDateIsNull() {
            addCriterion("CHARGEBACK_DATE is null");
            return (Criteria) this;
        }

        public Criteria andChargebackDateIsNotNull() {
            addCriterion("CHARGEBACK_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andChargebackDateEqualTo(String value) {
            addCriterion("CHARGEBACK_DATE =", value, "chargebackDate");
            return (Criteria) this;
        }

        public Criteria andChargebackDateNotEqualTo(String value) {
            addCriterion("CHARGEBACK_DATE <>", value, "chargebackDate");
            return (Criteria) this;
        }

        public Criteria andChargebackDateGreaterThan(String value) {
            addCriterion("CHARGEBACK_DATE >", value, "chargebackDate");
            return (Criteria) this;
        }

        public Criteria andChargebackDateGreaterThanOrEqualTo(String value) {
            addCriterion("CHARGEBACK_DATE >=", value, "chargebackDate");
            return (Criteria) this;
        }

        public Criteria andChargebackDateLessThan(String value) {
            addCriterion("CHARGEBACK_DATE <", value, "chargebackDate");
            return (Criteria) this;
        }

        public Criteria andChargebackDateLessThanOrEqualTo(String value) {
            addCriterion("CHARGEBACK_DATE <=", value, "chargebackDate");
            return (Criteria) this;
        }

        public Criteria andChargebackDateLike(String value) {
            addCriterion("CHARGEBACK_DATE like", value, "chargebackDate");
            return (Criteria) this;
        }

        public Criteria andChargebackDateNotLike(String value) {
            addCriterion("CHARGEBACK_DATE not like", value, "chargebackDate");
            return (Criteria) this;
        }

        public Criteria andChargebackDateIn(List<String> values) {
            addCriterion("CHARGEBACK_DATE in", values, "chargebackDate");
            return (Criteria) this;
        }

        public Criteria andChargebackDateNotIn(List<String> values) {
            addCriterion("CHARGEBACK_DATE not in", values, "chargebackDate");
            return (Criteria) this;
        }

        public Criteria andChargebackDateBetween(String value1, String value2) {
            addCriterion("CHARGEBACK_DATE between", value1, value2, "chargebackDate");
            return (Criteria) this;
        }

        public Criteria andChargebackDateNotBetween(String value1, String value2) {
            addCriterion("CHARGEBACK_DATE not between", value1, value2, "chargebackDate");
            return (Criteria) this;
        }

        public Criteria andErrTypeIsNull() {
            addCriterion("ERR_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andErrTypeIsNotNull() {
            addCriterion("ERR_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andErrTypeEqualTo(String value) {
            addCriterion("ERR_TYPE =", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeNotEqualTo(String value) {
            addCriterion("ERR_TYPE <>", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeGreaterThan(String value) {
            addCriterion("ERR_TYPE >", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ERR_TYPE >=", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeLessThan(String value) {
            addCriterion("ERR_TYPE <", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeLessThanOrEqualTo(String value) {
            addCriterion("ERR_TYPE <=", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeLike(String value) {
            addCriterion("ERR_TYPE like", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeNotLike(String value) {
            addCriterion("ERR_TYPE not like", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeIn(List<String> values) {
            addCriterion("ERR_TYPE in", values, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeNotIn(List<String> values) {
            addCriterion("ERR_TYPE not in", values, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeBetween(String value1, String value2) {
            addCriterion("ERR_TYPE between", value1, value2, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeNotBetween(String value1, String value2) {
            addCriterion("ERR_TYPE not between", value1, value2, "errType");
            return (Criteria) this;
        }

        public Criteria andSettleDateIsNull() {
            addCriterion("SETTLE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andSettleDateIsNotNull() {
            addCriterion("SETTLE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andSettleDateEqualTo(String value) {
            addCriterion("SETTLE_DATE =", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotEqualTo(String value) {
            addCriterion("SETTLE_DATE <>", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateGreaterThan(String value) {
            addCriterion("SETTLE_DATE >", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateGreaterThanOrEqualTo(String value) {
            addCriterion("SETTLE_DATE >=", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLessThan(String value) {
            addCriterion("SETTLE_DATE <", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLessThanOrEqualTo(String value) {
            addCriterion("SETTLE_DATE <=", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLike(String value) {
            addCriterion("SETTLE_DATE like", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotLike(String value) {
            addCriterion("SETTLE_DATE not like", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateIn(List<String> values) {
            addCriterion("SETTLE_DATE in", values, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotIn(List<String> values) {
            addCriterion("SETTLE_DATE not in", values, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateBetween(String value1, String value2) {
            addCriterion("SETTLE_DATE between", value1, value2, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotBetween(String value1, String value2) {
            addCriterion("SETTLE_DATE not between", value1, value2, "settleDate");
            return (Criteria) this;
        }

        public Criteria andTranTimeIsNull() {
            addCriterion("TRAN_TIME is null");
            return (Criteria) this;
        }

        public Criteria andTranTimeIsNotNull() {
            addCriterion("TRAN_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andTranTimeEqualTo(String value) {
            addCriterion("TRAN_TIME =", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeNotEqualTo(String value) {
            addCriterion("TRAN_TIME <>", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeGreaterThan(String value) {
            addCriterion("TRAN_TIME >", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeGreaterThanOrEqualTo(String value) {
            addCriterion("TRAN_TIME >=", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeLessThan(String value) {
            addCriterion("TRAN_TIME <", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeLessThanOrEqualTo(String value) {
            addCriterion("TRAN_TIME <=", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeLike(String value) {
            addCriterion("TRAN_TIME like", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeNotLike(String value) {
            addCriterion("TRAN_TIME not like", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeIn(List<String> values) {
            addCriterion("TRAN_TIME in", values, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeNotIn(List<String> values) {
            addCriterion("TRAN_TIME not in", values, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeBetween(String value1, String value2) {
            addCriterion("TRAN_TIME between", value1, value2, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeNotBetween(String value1, String value2) {
            addCriterion("TRAN_TIME not between", value1, value2, "tranTime");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNull() {
            addCriterion("CARD_NO is null");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNotNull() {
            addCriterion("CARD_NO is not null");
            return (Criteria) this;
        }

        public Criteria andCardNoEqualTo(String value) {
            addCriterion("CARD_NO =", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotEqualTo(String value) {
            addCriterion("CARD_NO <>", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThan(String value) {
            addCriterion("CARD_NO >", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThanOrEqualTo(String value) {
            addCriterion("CARD_NO >=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThan(String value) {
            addCriterion("CARD_NO <", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThanOrEqualTo(String value) {
            addCriterion("CARD_NO <=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLike(String value) {
            addCriterion("CARD_NO like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotLike(String value) {
            addCriterion("CARD_NO not like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoIn(List<String> values) {
            addCriterion("CARD_NO in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotIn(List<String> values) {
            addCriterion("CARD_NO not in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoBetween(String value1, String value2) {
            addCriterion("CARD_NO between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotBetween(String value1, String value2) {
            addCriterion("CARD_NO not between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andTranAmtIsNull() {
            addCriterion("TRAN_AMT is null");
            return (Criteria) this;
        }

        public Criteria andTranAmtIsNotNull() {
            addCriterion("TRAN_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andTranAmtEqualTo(BigDecimal value) {
            addCriterion("TRAN_AMT =", value, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtNotEqualTo(BigDecimal value) {
            addCriterion("TRAN_AMT <>", value, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtGreaterThan(BigDecimal value) {
            addCriterion("TRAN_AMT >", value, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TRAN_AMT >=", value, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtLessThan(BigDecimal value) {
            addCriterion("TRAN_AMT <", value, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TRAN_AMT <=", value, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtIn(List<BigDecimal> values) {
            addCriterion("TRAN_AMT in", values, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtNotIn(List<BigDecimal> values) {
            addCriterion("TRAN_AMT not in", values, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRAN_AMT between", value1, value2, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andTranAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRAN_AMT not between", value1, value2, "tranAmt");
            return (Criteria) this;
        }

        public Criteria andHostLsIsNull() {
            addCriterion("HOST_LS is null");
            return (Criteria) this;
        }

        public Criteria andHostLsIsNotNull() {
            addCriterion("HOST_LS is not null");
            return (Criteria) this;
        }

        public Criteria andHostLsEqualTo(String value) {
            addCriterion("HOST_LS =", value, "hostLs");
            return (Criteria) this;
        }

        public Criteria andHostLsNotEqualTo(String value) {
            addCriterion("HOST_LS <>", value, "hostLs");
            return (Criteria) this;
        }

        public Criteria andHostLsGreaterThan(String value) {
            addCriterion("HOST_LS >", value, "hostLs");
            return (Criteria) this;
        }

        public Criteria andHostLsGreaterThanOrEqualTo(String value) {
            addCriterion("HOST_LS >=", value, "hostLs");
            return (Criteria) this;
        }

        public Criteria andHostLsLessThan(String value) {
            addCriterion("HOST_LS <", value, "hostLs");
            return (Criteria) this;
        }

        public Criteria andHostLsLessThanOrEqualTo(String value) {
            addCriterion("HOST_LS <=", value, "hostLs");
            return (Criteria) this;
        }

        public Criteria andHostLsLike(String value) {
            addCriterion("HOST_LS like", value, "hostLs");
            return (Criteria) this;
        }

        public Criteria andHostLsNotLike(String value) {
            addCriterion("HOST_LS not like", value, "hostLs");
            return (Criteria) this;
        }

        public Criteria andHostLsIn(List<String> values) {
            addCriterion("HOST_LS in", values, "hostLs");
            return (Criteria) this;
        }

        public Criteria andHostLsNotIn(List<String> values) {
            addCriterion("HOST_LS not in", values, "hostLs");
            return (Criteria) this;
        }

        public Criteria andHostLsBetween(String value1, String value2) {
            addCriterion("HOST_LS between", value1, value2, "hostLs");
            return (Criteria) this;
        }

        public Criteria andHostLsNotBetween(String value1, String value2) {
            addCriterion("HOST_LS not between", value1, value2, "hostLs");
            return (Criteria) this;
        }

        public Criteria andTranTypeIsNull() {
            addCriterion("TRAN_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTranTypeIsNotNull() {
            addCriterion("TRAN_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTranTypeEqualTo(String value) {
            addCriterion("TRAN_TYPE =", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeNotEqualTo(String value) {
            addCriterion("TRAN_TYPE <>", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeGreaterThan(String value) {
            addCriterion("TRAN_TYPE >", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TRAN_TYPE >=", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeLessThan(String value) {
            addCriterion("TRAN_TYPE <", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeLessThanOrEqualTo(String value) {
            addCriterion("TRAN_TYPE <=", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeLike(String value) {
            addCriterion("TRAN_TYPE like", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeNotLike(String value) {
            addCriterion("TRAN_TYPE not like", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeIn(List<String> values) {
            addCriterion("TRAN_TYPE in", values, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeNotIn(List<String> values) {
            addCriterion("TRAN_TYPE not in", values, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeBetween(String value1, String value2) {
            addCriterion("TRAN_TYPE between", value1, value2, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeNotBetween(String value1, String value2) {
            addCriterion("TRAN_TYPE not between", value1, value2, "tranType");
            return (Criteria) this;
        }

        public Criteria andErrDescIsNull() {
            addCriterion("ERR_DESC is null");
            return (Criteria) this;
        }

        public Criteria andErrDescIsNotNull() {
            addCriterion("ERR_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andErrDescEqualTo(String value) {
            addCriterion("ERR_DESC =", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescNotEqualTo(String value) {
            addCriterion("ERR_DESC <>", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescGreaterThan(String value) {
            addCriterion("ERR_DESC >", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescGreaterThanOrEqualTo(String value) {
            addCriterion("ERR_DESC >=", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescLessThan(String value) {
            addCriterion("ERR_DESC <", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescLessThanOrEqualTo(String value) {
            addCriterion("ERR_DESC <=", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescLike(String value) {
            addCriterion("ERR_DESC like", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescNotLike(String value) {
            addCriterion("ERR_DESC not like", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescIn(List<String> values) {
            addCriterion("ERR_DESC in", values, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescNotIn(List<String> values) {
            addCriterion("ERR_DESC not in", values, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescBetween(String value1, String value2) {
            addCriterion("ERR_DESC between", value1, value2, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescNotBetween(String value1, String value2) {
            addCriterion("ERR_DESC not between", value1, value2, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrLsIsNull() {
            addCriterion("ERR_LS is null");
            return (Criteria) this;
        }

        public Criteria andErrLsIsNotNull() {
            addCriterion("ERR_LS is not null");
            return (Criteria) this;
        }

        public Criteria andErrLsEqualTo(String value) {
            addCriterion("ERR_LS =", value, "errLs");
            return (Criteria) this;
        }

        public Criteria andErrLsNotEqualTo(String value) {
            addCriterion("ERR_LS <>", value, "errLs");
            return (Criteria) this;
        }

        public Criteria andErrLsGreaterThan(String value) {
            addCriterion("ERR_LS >", value, "errLs");
            return (Criteria) this;
        }

        public Criteria andErrLsGreaterThanOrEqualTo(String value) {
            addCriterion("ERR_LS >=", value, "errLs");
            return (Criteria) this;
        }

        public Criteria andErrLsLessThan(String value) {
            addCriterion("ERR_LS <", value, "errLs");
            return (Criteria) this;
        }

        public Criteria andErrLsLessThanOrEqualTo(String value) {
            addCriterion("ERR_LS <=", value, "errLs");
            return (Criteria) this;
        }

        public Criteria andErrLsLike(String value) {
            addCriterion("ERR_LS like", value, "errLs");
            return (Criteria) this;
        }

        public Criteria andErrLsNotLike(String value) {
            addCriterion("ERR_LS not like", value, "errLs");
            return (Criteria) this;
        }

        public Criteria andErrLsIn(List<String> values) {
            addCriterion("ERR_LS in", values, "errLs");
            return (Criteria) this;
        }

        public Criteria andErrLsNotIn(List<String> values) {
            addCriterion("ERR_LS not in", values, "errLs");
            return (Criteria) this;
        }

        public Criteria andErrLsBetween(String value1, String value2) {
            addCriterion("ERR_LS between", value1, value2, "errLs");
            return (Criteria) this;
        }

        public Criteria andErrLsNotBetween(String value1, String value2) {
            addCriterion("ERR_LS not between", value1, value2, "errLs");
            return (Criteria) this;
        }

        public Criteria andOldErrLsIsNull() {
            addCriterion("OLD_ERR_LS is null");
            return (Criteria) this;
        }

        public Criteria andOldErrLsIsNotNull() {
            addCriterion("OLD_ERR_LS is not null");
            return (Criteria) this;
        }

        public Criteria andOldErrLsEqualTo(String value) {
            addCriterion("OLD_ERR_LS =", value, "oldErrLs");
            return (Criteria) this;
        }

        public Criteria andOldErrLsNotEqualTo(String value) {
            addCriterion("OLD_ERR_LS <>", value, "oldErrLs");
            return (Criteria) this;
        }

        public Criteria andOldErrLsGreaterThan(String value) {
            addCriterion("OLD_ERR_LS >", value, "oldErrLs");
            return (Criteria) this;
        }

        public Criteria andOldErrLsGreaterThanOrEqualTo(String value) {
            addCriterion("OLD_ERR_LS >=", value, "oldErrLs");
            return (Criteria) this;
        }

        public Criteria andOldErrLsLessThan(String value) {
            addCriterion("OLD_ERR_LS <", value, "oldErrLs");
            return (Criteria) this;
        }

        public Criteria andOldErrLsLessThanOrEqualTo(String value) {
            addCriterion("OLD_ERR_LS <=", value, "oldErrLs");
            return (Criteria) this;
        }

        public Criteria andOldErrLsLike(String value) {
            addCriterion("OLD_ERR_LS like", value, "oldErrLs");
            return (Criteria) this;
        }

        public Criteria andOldErrLsNotLike(String value) {
            addCriterion("OLD_ERR_LS not like", value, "oldErrLs");
            return (Criteria) this;
        }

        public Criteria andOldErrLsIn(List<String> values) {
            addCriterion("OLD_ERR_LS in", values, "oldErrLs");
            return (Criteria) this;
        }

        public Criteria andOldErrLsNotIn(List<String> values) {
            addCriterion("OLD_ERR_LS not in", values, "oldErrLs");
            return (Criteria) this;
        }

        public Criteria andOldErrLsBetween(String value1, String value2) {
            addCriterion("OLD_ERR_LS between", value1, value2, "oldErrLs");
            return (Criteria) this;
        }

        public Criteria andOldErrLsNotBetween(String value1, String value2) {
            addCriterion("OLD_ERR_LS not between", value1, value2, "oldErrLs");
            return (Criteria) this;
        }

        public Criteria andErrFlagIsNull() {
            addCriterion("ERR_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andErrFlagIsNotNull() {
            addCriterion("ERR_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andErrFlagEqualTo(String value) {
            addCriterion("ERR_FLAG =", value, "errFlag");
            return (Criteria) this;
        }

        public Criteria andErrFlagNotEqualTo(String value) {
            addCriterion("ERR_FLAG <>", value, "errFlag");
            return (Criteria) this;
        }

        public Criteria andErrFlagGreaterThan(String value) {
            addCriterion("ERR_FLAG >", value, "errFlag");
            return (Criteria) this;
        }

        public Criteria andErrFlagGreaterThanOrEqualTo(String value) {
            addCriterion("ERR_FLAG >=", value, "errFlag");
            return (Criteria) this;
        }

        public Criteria andErrFlagLessThan(String value) {
            addCriterion("ERR_FLAG <", value, "errFlag");
            return (Criteria) this;
        }

        public Criteria andErrFlagLessThanOrEqualTo(String value) {
            addCriterion("ERR_FLAG <=", value, "errFlag");
            return (Criteria) this;
        }

        public Criteria andErrFlagLike(String value) {
            addCriterion("ERR_FLAG like", value, "errFlag");
            return (Criteria) this;
        }

        public Criteria andErrFlagNotLike(String value) {
            addCriterion("ERR_FLAG not like", value, "errFlag");
            return (Criteria) this;
        }

        public Criteria andErrFlagIn(List<String> values) {
            addCriterion("ERR_FLAG in", values, "errFlag");
            return (Criteria) this;
        }

        public Criteria andErrFlagNotIn(List<String> values) {
            addCriterion("ERR_FLAG not in", values, "errFlag");
            return (Criteria) this;
        }

        public Criteria andErrFlagBetween(String value1, String value2) {
            addCriterion("ERR_FLAG between", value1, value2, "errFlag");
            return (Criteria) this;
        }

        public Criteria andErrFlagNotBetween(String value1, String value2) {
            addCriterion("ERR_FLAG not between", value1, value2, "errFlag");
            return (Criteria) this;
        }

        public Criteria andFreezeDateIsNull() {
            addCriterion("FREEZE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andFreezeDateIsNotNull() {
            addCriterion("FREEZE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeDateEqualTo(String value) {
            addCriterion("FREEZE_DATE =", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateNotEqualTo(String value) {
            addCriterion("FREEZE_DATE <>", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateGreaterThan(String value) {
            addCriterion("FREEZE_DATE >", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_DATE >=", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateLessThan(String value) {
            addCriterion("FREEZE_DATE <", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_DATE <=", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateLike(String value) {
            addCriterion("FREEZE_DATE like", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateNotLike(String value) {
            addCriterion("FREEZE_DATE not like", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateIn(List<String> values) {
            addCriterion("FREEZE_DATE in", values, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateNotIn(List<String> values) {
            addCriterion("FREEZE_DATE not in", values, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateBetween(String value1, String value2) {
            addCriterion("FREEZE_DATE between", value1, value2, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateNotBetween(String value1, String value2) {
            addCriterion("FREEZE_DATE not between", value1, value2, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andOffsetBalanceAmtIsNull() {
            addCriterion("OFFSET_BALANCE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andOffsetBalanceAmtIsNotNull() {
            addCriterion("OFFSET_BALANCE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andOffsetBalanceAmtEqualTo(BigDecimal value) {
            addCriterion("OFFSET_BALANCE_AMT =", value, "offsetBalanceAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetBalanceAmtNotEqualTo(BigDecimal value) {
            addCriterion("OFFSET_BALANCE_AMT <>", value, "offsetBalanceAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetBalanceAmtGreaterThan(BigDecimal value) {
            addCriterion("OFFSET_BALANCE_AMT >", value, "offsetBalanceAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetBalanceAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OFFSET_BALANCE_AMT >=", value, "offsetBalanceAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetBalanceAmtLessThan(BigDecimal value) {
            addCriterion("OFFSET_BALANCE_AMT <", value, "offsetBalanceAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetBalanceAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OFFSET_BALANCE_AMT <=", value, "offsetBalanceAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetBalanceAmtIn(List<BigDecimal> values) {
            addCriterion("OFFSET_BALANCE_AMT in", values, "offsetBalanceAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetBalanceAmtNotIn(List<BigDecimal> values) {
            addCriterion("OFFSET_BALANCE_AMT not in", values, "offsetBalanceAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetBalanceAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OFFSET_BALANCE_AMT between", value1, value2, "offsetBalanceAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetBalanceAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OFFSET_BALANCE_AMT not between", value1, value2, "offsetBalanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtIsNull() {
            addCriterion("BALANCE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtIsNotNull() {
            addCriterion("BALANCE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT =", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtNotEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT <>", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtGreaterThan(BigDecimal value) {
            addCriterion("BALANCE_AMT >", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT >=", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtLessThan(BigDecimal value) {
            addCriterion("BALANCE_AMT <", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT <=", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtIn(List<BigDecimal> values) {
            addCriterion("BALANCE_AMT in", values, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtNotIn(List<BigDecimal> values) {
            addCriterion("BALANCE_AMT not in", values, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BALANCE_AMT between", value1, value2, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BALANCE_AMT not between", value1, value2, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andNetAmtIsNull() {
            addCriterion("NET_AMT is null");
            return (Criteria) this;
        }

        public Criteria andNetAmtIsNotNull() {
            addCriterion("NET_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andNetAmtEqualTo(BigDecimal value) {
            addCriterion("NET_AMT =", value, "netAmt");
            return (Criteria) this;
        }

        public Criteria andNetAmtNotEqualTo(BigDecimal value) {
            addCriterion("NET_AMT <>", value, "netAmt");
            return (Criteria) this;
        }

        public Criteria andNetAmtGreaterThan(BigDecimal value) {
            addCriterion("NET_AMT >", value, "netAmt");
            return (Criteria) this;
        }

        public Criteria andNetAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NET_AMT >=", value, "netAmt");
            return (Criteria) this;
        }

        public Criteria andNetAmtLessThan(BigDecimal value) {
            addCriterion("NET_AMT <", value, "netAmt");
            return (Criteria) this;
        }

        public Criteria andNetAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NET_AMT <=", value, "netAmt");
            return (Criteria) this;
        }

        public Criteria andNetAmtIn(List<BigDecimal> values) {
            addCriterion("NET_AMT in", values, "netAmt");
            return (Criteria) this;
        }

        public Criteria andNetAmtNotIn(List<BigDecimal> values) {
            addCriterion("NET_AMT not in", values, "netAmt");
            return (Criteria) this;
        }

        public Criteria andNetAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NET_AMT between", value1, value2, "netAmt");
            return (Criteria) this;
        }

        public Criteria andNetAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NET_AMT not between", value1, value2, "netAmt");
            return (Criteria) this;
        }

        public Criteria andRepaymentTypeIsNull() {
            addCriterion("REPAYMENT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andRepaymentTypeIsNotNull() {
            addCriterion("REPAYMENT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andRepaymentTypeEqualTo(String value) {
            addCriterion("REPAYMENT_TYPE =", value, "repaymentType");
            return (Criteria) this;
        }

        public Criteria andRepaymentTypeNotEqualTo(String value) {
            addCriterion("REPAYMENT_TYPE <>", value, "repaymentType");
            return (Criteria) this;
        }

        public Criteria andRepaymentTypeGreaterThan(String value) {
            addCriterion("REPAYMENT_TYPE >", value, "repaymentType");
            return (Criteria) this;
        }

        public Criteria andRepaymentTypeGreaterThanOrEqualTo(String value) {
            addCriterion("REPAYMENT_TYPE >=", value, "repaymentType");
            return (Criteria) this;
        }

        public Criteria andRepaymentTypeLessThan(String value) {
            addCriterion("REPAYMENT_TYPE <", value, "repaymentType");
            return (Criteria) this;
        }

        public Criteria andRepaymentTypeLessThanOrEqualTo(String value) {
            addCriterion("REPAYMENT_TYPE <=", value, "repaymentType");
            return (Criteria) this;
        }

        public Criteria andRepaymentTypeLike(String value) {
            addCriterion("REPAYMENT_TYPE like", value, "repaymentType");
            return (Criteria) this;
        }

        public Criteria andRepaymentTypeNotLike(String value) {
            addCriterion("REPAYMENT_TYPE not like", value, "repaymentType");
            return (Criteria) this;
        }

        public Criteria andRepaymentTypeIn(List<String> values) {
            addCriterion("REPAYMENT_TYPE in", values, "repaymentType");
            return (Criteria) this;
        }

        public Criteria andRepaymentTypeNotIn(List<String> values) {
            addCriterion("REPAYMENT_TYPE not in", values, "repaymentType");
            return (Criteria) this;
        }

        public Criteria andRepaymentTypeBetween(String value1, String value2) {
            addCriterion("REPAYMENT_TYPE between", value1, value2, "repaymentType");
            return (Criteria) this;
        }

        public Criteria andRepaymentTypeNotBetween(String value1, String value2) {
            addCriterion("REPAYMENT_TYPE not between", value1, value2, "repaymentType");
            return (Criteria) this;
        }

        public Criteria andLongShortTypeIsNull() {
            addCriterion("LONG_SHORT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andLongShortTypeIsNotNull() {
            addCriterion("LONG_SHORT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andLongShortTypeEqualTo(String value) {
            addCriterion("LONG_SHORT_TYPE =", value, "longShortType");
            return (Criteria) this;
        }

        public Criteria andLongShortTypeNotEqualTo(String value) {
            addCriterion("LONG_SHORT_TYPE <>", value, "longShortType");
            return (Criteria) this;
        }

        public Criteria andLongShortTypeGreaterThan(String value) {
            addCriterion("LONG_SHORT_TYPE >", value, "longShortType");
            return (Criteria) this;
        }

        public Criteria andLongShortTypeGreaterThanOrEqualTo(String value) {
            addCriterion("LONG_SHORT_TYPE >=", value, "longShortType");
            return (Criteria) this;
        }

        public Criteria andLongShortTypeLessThan(String value) {
            addCriterion("LONG_SHORT_TYPE <", value, "longShortType");
            return (Criteria) this;
        }

        public Criteria andLongShortTypeLessThanOrEqualTo(String value) {
            addCriterion("LONG_SHORT_TYPE <=", value, "longShortType");
            return (Criteria) this;
        }

        public Criteria andLongShortTypeLike(String value) {
            addCriterion("LONG_SHORT_TYPE like", value, "longShortType");
            return (Criteria) this;
        }

        public Criteria andLongShortTypeNotLike(String value) {
            addCriterion("LONG_SHORT_TYPE not like", value, "longShortType");
            return (Criteria) this;
        }

        public Criteria andLongShortTypeIn(List<String> values) {
            addCriterion("LONG_SHORT_TYPE in", values, "longShortType");
            return (Criteria) this;
        }

        public Criteria andLongShortTypeNotIn(List<String> values) {
            addCriterion("LONG_SHORT_TYPE not in", values, "longShortType");
            return (Criteria) this;
        }

        public Criteria andLongShortTypeBetween(String value1, String value2) {
            addCriterion("LONG_SHORT_TYPE between", value1, value2, "longShortType");
            return (Criteria) this;
        }

        public Criteria andLongShortTypeNotBetween(String value1, String value2) {
            addCriterion("LONG_SHORT_TYPE not between", value1, value2, "longShortType");
            return (Criteria) this;
        }

        public Criteria andRecordStatusIsNull() {
            addCriterion("RECORD_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andRecordStatusIsNotNull() {
            addCriterion("RECORD_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andRecordStatusEqualTo(String value) {
            addCriterion("RECORD_STATUS =", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusNotEqualTo(String value) {
            addCriterion("RECORD_STATUS <>", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusGreaterThan(String value) {
            addCriterion("RECORD_STATUS >", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusGreaterThanOrEqualTo(String value) {
            addCriterion("RECORD_STATUS >=", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusLessThan(String value) {
            addCriterion("RECORD_STATUS <", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusLessThanOrEqualTo(String value) {
            addCriterion("RECORD_STATUS <=", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusLike(String value) {
            addCriterion("RECORD_STATUS like", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusNotLike(String value) {
            addCriterion("RECORD_STATUS not like", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusIn(List<String> values) {
            addCriterion("RECORD_STATUS in", values, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusNotIn(List<String> values) {
            addCriterion("RECORD_STATUS not in", values, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusBetween(String value1, String value2) {
            addCriterion("RECORD_STATUS between", value1, value2, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusNotBetween(String value1, String value2) {
            addCriterion("RECORD_STATUS not between", value1, value2, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andChargeoffTypeIsNull() {
            addCriterion("CHARGEOFF_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andChargeoffTypeIsNotNull() {
            addCriterion("CHARGEOFF_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andChargeoffTypeEqualTo(String value) {
            addCriterion("CHARGEOFF_TYPE =", value, "chargeoffType");
            return (Criteria) this;
        }

        public Criteria andChargeoffTypeNotEqualTo(String value) {
            addCriterion("CHARGEOFF_TYPE <>", value, "chargeoffType");
            return (Criteria) this;
        }

        public Criteria andChargeoffTypeGreaterThan(String value) {
            addCriterion("CHARGEOFF_TYPE >", value, "chargeoffType");
            return (Criteria) this;
        }

        public Criteria andChargeoffTypeGreaterThanOrEqualTo(String value) {
            addCriterion("CHARGEOFF_TYPE >=", value, "chargeoffType");
            return (Criteria) this;
        }

        public Criteria andChargeoffTypeLessThan(String value) {
            addCriterion("CHARGEOFF_TYPE <", value, "chargeoffType");
            return (Criteria) this;
        }

        public Criteria andChargeoffTypeLessThanOrEqualTo(String value) {
            addCriterion("CHARGEOFF_TYPE <=", value, "chargeoffType");
            return (Criteria) this;
        }

        public Criteria andChargeoffTypeLike(String value) {
            addCriterion("CHARGEOFF_TYPE like", value, "chargeoffType");
            return (Criteria) this;
        }

        public Criteria andChargeoffTypeNotLike(String value) {
            addCriterion("CHARGEOFF_TYPE not like", value, "chargeoffType");
            return (Criteria) this;
        }

        public Criteria andChargeoffTypeIn(List<String> values) {
            addCriterion("CHARGEOFF_TYPE in", values, "chargeoffType");
            return (Criteria) this;
        }

        public Criteria andChargeoffTypeNotIn(List<String> values) {
            addCriterion("CHARGEOFF_TYPE not in", values, "chargeoffType");
            return (Criteria) this;
        }

        public Criteria andChargeoffTypeBetween(String value1, String value2) {
            addCriterion("CHARGEOFF_TYPE between", value1, value2, "chargeoffType");
            return (Criteria) this;
        }

        public Criteria andChargeoffTypeNotBetween(String value1, String value2) {
            addCriterion("CHARGEOFF_TYPE not between", value1, value2, "chargeoffType");
            return (Criteria) this;
        }

        public Criteria andHbMerchIdIsNull() {
            addCriterion("HB_MERCH_ID is null");
            return (Criteria) this;
        }

        public Criteria andHbMerchIdIsNotNull() {
            addCriterion("HB_MERCH_ID is not null");
            return (Criteria) this;
        }

        public Criteria andHbMerchIdEqualTo(String value) {
            addCriterion("HB_MERCH_ID =", value, "hbMerchId");
            return (Criteria) this;
        }

        public Criteria andHbMerchIdNotEqualTo(String value) {
            addCriterion("HB_MERCH_ID <>", value, "hbMerchId");
            return (Criteria) this;
        }

        public Criteria andHbMerchIdGreaterThan(String value) {
            addCriterion("HB_MERCH_ID >", value, "hbMerchId");
            return (Criteria) this;
        }

        public Criteria andHbMerchIdGreaterThanOrEqualTo(String value) {
            addCriterion("HB_MERCH_ID >=", value, "hbMerchId");
            return (Criteria) this;
        }

        public Criteria andHbMerchIdLessThan(String value) {
            addCriterion("HB_MERCH_ID <", value, "hbMerchId");
            return (Criteria) this;
        }

        public Criteria andHbMerchIdLessThanOrEqualTo(String value) {
            addCriterion("HB_MERCH_ID <=", value, "hbMerchId");
            return (Criteria) this;
        }

        public Criteria andHbMerchIdLike(String value) {
            addCriterion("HB_MERCH_ID like", value, "hbMerchId");
            return (Criteria) this;
        }

        public Criteria andHbMerchIdNotLike(String value) {
            addCriterion("HB_MERCH_ID not like", value, "hbMerchId");
            return (Criteria) this;
        }

        public Criteria andHbMerchIdIn(List<String> values) {
            addCriterion("HB_MERCH_ID in", values, "hbMerchId");
            return (Criteria) this;
        }

        public Criteria andHbMerchIdNotIn(List<String> values) {
            addCriterion("HB_MERCH_ID not in", values, "hbMerchId");
            return (Criteria) this;
        }

        public Criteria andHbMerchIdBetween(String value1, String value2) {
            addCriterion("HB_MERCH_ID between", value1, value2, "hbMerchId");
            return (Criteria) this;
        }

        public Criteria andHbMerchIdNotBetween(String value1, String value2) {
            addCriterion("HB_MERCH_ID not between", value1, value2, "hbMerchId");
            return (Criteria) this;
        }

        public Criteria andHbOrgIsNull() {
            addCriterion("HB_ORG is null");
            return (Criteria) this;
        }

        public Criteria andHbOrgIsNotNull() {
            addCriterion("HB_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andHbOrgEqualTo(String value) {
            addCriterion("HB_ORG =", value, "hbOrg");
            return (Criteria) this;
        }

        public Criteria andHbOrgNotEqualTo(String value) {
            addCriterion("HB_ORG <>", value, "hbOrg");
            return (Criteria) this;
        }

        public Criteria andHbOrgGreaterThan(String value) {
            addCriterion("HB_ORG >", value, "hbOrg");
            return (Criteria) this;
        }

        public Criteria andHbOrgGreaterThanOrEqualTo(String value) {
            addCriterion("HB_ORG >=", value, "hbOrg");
            return (Criteria) this;
        }

        public Criteria andHbOrgLessThan(String value) {
            addCriterion("HB_ORG <", value, "hbOrg");
            return (Criteria) this;
        }

        public Criteria andHbOrgLessThanOrEqualTo(String value) {
            addCriterion("HB_ORG <=", value, "hbOrg");
            return (Criteria) this;
        }

        public Criteria andHbOrgLike(String value) {
            addCriterion("HB_ORG like", value, "hbOrg");
            return (Criteria) this;
        }

        public Criteria andHbOrgNotLike(String value) {
            addCriterion("HB_ORG not like", value, "hbOrg");
            return (Criteria) this;
        }

        public Criteria andHbOrgIn(List<String> values) {
            addCriterion("HB_ORG in", values, "hbOrg");
            return (Criteria) this;
        }

        public Criteria andHbOrgNotIn(List<String> values) {
            addCriterion("HB_ORG not in", values, "hbOrg");
            return (Criteria) this;
        }

        public Criteria andHbOrgBetween(String value1, String value2) {
            addCriterion("HB_ORG between", value1, value2, "hbOrg");
            return (Criteria) this;
        }

        public Criteria andHbOrgNotBetween(String value1, String value2) {
            addCriterion("HB_ORG not between", value1, value2, "hbOrg");
            return (Criteria) this;
        }

        public Criteria andHbPhoneIsNull() {
            addCriterion("HB_PHONE is null");
            return (Criteria) this;
        }

        public Criteria andHbPhoneIsNotNull() {
            addCriterion("HB_PHONE is not null");
            return (Criteria) this;
        }

        public Criteria andHbPhoneEqualTo(String value) {
            addCriterion("HB_PHONE =", value, "hbPhone");
            return (Criteria) this;
        }

        public Criteria andHbPhoneNotEqualTo(String value) {
            addCriterion("HB_PHONE <>", value, "hbPhone");
            return (Criteria) this;
        }

        public Criteria andHbPhoneGreaterThan(String value) {
            addCriterion("HB_PHONE >", value, "hbPhone");
            return (Criteria) this;
        }

        public Criteria andHbPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("HB_PHONE >=", value, "hbPhone");
            return (Criteria) this;
        }

        public Criteria andHbPhoneLessThan(String value) {
            addCriterion("HB_PHONE <", value, "hbPhone");
            return (Criteria) this;
        }

        public Criteria andHbPhoneLessThanOrEqualTo(String value) {
            addCriterion("HB_PHONE <=", value, "hbPhone");
            return (Criteria) this;
        }

        public Criteria andHbPhoneLike(String value) {
            addCriterion("HB_PHONE like", value, "hbPhone");
            return (Criteria) this;
        }

        public Criteria andHbPhoneNotLike(String value) {
            addCriterion("HB_PHONE not like", value, "hbPhone");
            return (Criteria) this;
        }

        public Criteria andHbPhoneIn(List<String> values) {
            addCriterion("HB_PHONE in", values, "hbPhone");
            return (Criteria) this;
        }

        public Criteria andHbPhoneNotIn(List<String> values) {
            addCriterion("HB_PHONE not in", values, "hbPhone");
            return (Criteria) this;
        }

        public Criteria andHbPhoneBetween(String value1, String value2) {
            addCriterion("HB_PHONE between", value1, value2, "hbPhone");
            return (Criteria) this;
        }

        public Criteria andHbPhoneNotBetween(String value1, String value2) {
            addCriterion("HB_PHONE not between", value1, value2, "hbPhone");
            return (Criteria) this;
        }

        public Criteria andHbNameIsNull() {
            addCriterion("HB_NAME is null");
            return (Criteria) this;
        }

        public Criteria andHbNameIsNotNull() {
            addCriterion("HB_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andHbNameEqualTo(String value) {
            addCriterion("HB_NAME =", value, "hbName");
            return (Criteria) this;
        }

        public Criteria andHbNameNotEqualTo(String value) {
            addCriterion("HB_NAME <>", value, "hbName");
            return (Criteria) this;
        }

        public Criteria andHbNameGreaterThan(String value) {
            addCriterion("HB_NAME >", value, "hbName");
            return (Criteria) this;
        }

        public Criteria andHbNameGreaterThanOrEqualTo(String value) {
            addCriterion("HB_NAME >=", value, "hbName");
            return (Criteria) this;
        }

        public Criteria andHbNameLessThan(String value) {
            addCriterion("HB_NAME <", value, "hbName");
            return (Criteria) this;
        }

        public Criteria andHbNameLessThanOrEqualTo(String value) {
            addCriterion("HB_NAME <=", value, "hbName");
            return (Criteria) this;
        }

        public Criteria andHbNameLike(String value) {
            addCriterion("HB_NAME like", value, "hbName");
            return (Criteria) this;
        }

        public Criteria andHbNameNotLike(String value) {
            addCriterion("HB_NAME not like", value, "hbName");
            return (Criteria) this;
        }

        public Criteria andHbNameIn(List<String> values) {
            addCriterion("HB_NAME in", values, "hbName");
            return (Criteria) this;
        }

        public Criteria andHbNameNotIn(List<String> values) {
            addCriterion("HB_NAME not in", values, "hbName");
            return (Criteria) this;
        }

        public Criteria andHbNameBetween(String value1, String value2) {
            addCriterion("HB_NAME between", value1, value2, "hbName");
            return (Criteria) this;
        }

        public Criteria andHbNameNotBetween(String value1, String value2) {
            addCriterion("HB_NAME not between", value1, value2, "hbName");
            return (Criteria) this;
        }

        public Criteria andHbTermIdIsNull() {
            addCriterion("HB_TERM_ID is null");
            return (Criteria) this;
        }

        public Criteria andHbTermIdIsNotNull() {
            addCriterion("HB_TERM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andHbTermIdEqualTo(String value) {
            addCriterion("HB_TERM_ID =", value, "hbTermId");
            return (Criteria) this;
        }

        public Criteria andHbTermIdNotEqualTo(String value) {
            addCriterion("HB_TERM_ID <>", value, "hbTermId");
            return (Criteria) this;
        }

        public Criteria andHbTermIdGreaterThan(String value) {
            addCriterion("HB_TERM_ID >", value, "hbTermId");
            return (Criteria) this;
        }

        public Criteria andHbTermIdGreaterThanOrEqualTo(String value) {
            addCriterion("HB_TERM_ID >=", value, "hbTermId");
            return (Criteria) this;
        }

        public Criteria andHbTermIdLessThan(String value) {
            addCriterion("HB_TERM_ID <", value, "hbTermId");
            return (Criteria) this;
        }

        public Criteria andHbTermIdLessThanOrEqualTo(String value) {
            addCriterion("HB_TERM_ID <=", value, "hbTermId");
            return (Criteria) this;
        }

        public Criteria andHbTermIdLike(String value) {
            addCriterion("HB_TERM_ID like", value, "hbTermId");
            return (Criteria) this;
        }

        public Criteria andHbTermIdNotLike(String value) {
            addCriterion("HB_TERM_ID not like", value, "hbTermId");
            return (Criteria) this;
        }

        public Criteria andHbTermIdIn(List<String> values) {
            addCriterion("HB_TERM_ID in", values, "hbTermId");
            return (Criteria) this;
        }

        public Criteria andHbTermIdNotIn(List<String> values) {
            addCriterion("HB_TERM_ID not in", values, "hbTermId");
            return (Criteria) this;
        }

        public Criteria andHbTermIdBetween(String value1, String value2) {
            addCriterion("HB_TERM_ID between", value1, value2, "hbTermId");
            return (Criteria) this;
        }

        public Criteria andHbTermIdNotBetween(String value1, String value2) {
            addCriterion("HB_TERM_ID not between", value1, value2, "hbTermId");
            return (Criteria) this;
        }

        public Criteria andBalanceDateIsNull() {
            addCriterion("BALANCE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andBalanceDateIsNotNull() {
            addCriterion("BALANCE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceDateEqualTo(String value) {
            addCriterion("BALANCE_DATE =", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateNotEqualTo(String value) {
            addCriterion("BALANCE_DATE <>", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateGreaterThan(String value) {
            addCriterion("BALANCE_DATE >", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_DATE >=", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateLessThan(String value) {
            addCriterion("BALANCE_DATE <", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_DATE <=", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateLike(String value) {
            addCriterion("BALANCE_DATE like", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateNotLike(String value) {
            addCriterion("BALANCE_DATE not like", value, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateIn(List<String> values) {
            addCriterion("BALANCE_DATE in", values, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateNotIn(List<String> values) {
            addCriterion("BALANCE_DATE not in", values, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateBetween(String value1, String value2) {
            addCriterion("BALANCE_DATE between", value1, value2, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andBalanceDateNotBetween(String value1, String value2) {
            addCriterion("BALANCE_DATE not between", value1, value2, "balanceDate");
            return (Criteria) this;
        }

        public Criteria andNettingStatusIsNull() {
            addCriterion("NETTING_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andNettingStatusIsNotNull() {
            addCriterion("NETTING_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andNettingStatusEqualTo(String value) {
            addCriterion("NETTING_STATUS =", value, "nettingStatus");
            return (Criteria) this;
        }

        public Criteria andNettingStatusNotEqualTo(String value) {
            addCriterion("NETTING_STATUS <>", value, "nettingStatus");
            return (Criteria) this;
        }

        public Criteria andNettingStatusGreaterThan(String value) {
            addCriterion("NETTING_STATUS >", value, "nettingStatus");
            return (Criteria) this;
        }

        public Criteria andNettingStatusGreaterThanOrEqualTo(String value) {
            addCriterion("NETTING_STATUS >=", value, "nettingStatus");
            return (Criteria) this;
        }

        public Criteria andNettingStatusLessThan(String value) {
            addCriterion("NETTING_STATUS <", value, "nettingStatus");
            return (Criteria) this;
        }

        public Criteria andNettingStatusLessThanOrEqualTo(String value) {
            addCriterion("NETTING_STATUS <=", value, "nettingStatus");
            return (Criteria) this;
        }

        public Criteria andNettingStatusLike(String value) {
            addCriterion("NETTING_STATUS like", value, "nettingStatus");
            return (Criteria) this;
        }

        public Criteria andNettingStatusNotLike(String value) {
            addCriterion("NETTING_STATUS not like", value, "nettingStatus");
            return (Criteria) this;
        }

        public Criteria andNettingStatusIn(List<String> values) {
            addCriterion("NETTING_STATUS in", values, "nettingStatus");
            return (Criteria) this;
        }

        public Criteria andNettingStatusNotIn(List<String> values) {
            addCriterion("NETTING_STATUS not in", values, "nettingStatus");
            return (Criteria) this;
        }

        public Criteria andNettingStatusBetween(String value1, String value2) {
            addCriterion("NETTING_STATUS between", value1, value2, "nettingStatus");
            return (Criteria) this;
        }

        public Criteria andNettingStatusNotBetween(String value1, String value2) {
            addCriterion("NETTING_STATUS not between", value1, value2, "nettingStatus");
            return (Criteria) this;
        }

        public Criteria andRealDeductAmtIsNull() {
            addCriterion("REAL_DEDUCT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andRealDeductAmtIsNotNull() {
            addCriterion("REAL_DEDUCT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andRealDeductAmtEqualTo(BigDecimal value) {
            addCriterion("REAL_DEDUCT_AMT =", value, "realDeductAmt");
            return (Criteria) this;
        }

        public Criteria andRealDeductAmtNotEqualTo(BigDecimal value) {
            addCriterion("REAL_DEDUCT_AMT <>", value, "realDeductAmt");
            return (Criteria) this;
        }

        public Criteria andRealDeductAmtGreaterThan(BigDecimal value) {
            addCriterion("REAL_DEDUCT_AMT >", value, "realDeductAmt");
            return (Criteria) this;
        }

        public Criteria andRealDeductAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_DEDUCT_AMT >=", value, "realDeductAmt");
            return (Criteria) this;
        }

        public Criteria andRealDeductAmtLessThan(BigDecimal value) {
            addCriterion("REAL_DEDUCT_AMT <", value, "realDeductAmt");
            return (Criteria) this;
        }

        public Criteria andRealDeductAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_DEDUCT_AMT <=", value, "realDeductAmt");
            return (Criteria) this;
        }

        public Criteria andRealDeductAmtIn(List<BigDecimal> values) {
            addCriterion("REAL_DEDUCT_AMT in", values, "realDeductAmt");
            return (Criteria) this;
        }

        public Criteria andRealDeductAmtNotIn(List<BigDecimal> values) {
            addCriterion("REAL_DEDUCT_AMT not in", values, "realDeductAmt");
            return (Criteria) this;
        }

        public Criteria andRealDeductAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_DEDUCT_AMT between", value1, value2, "realDeductAmt");
            return (Criteria) this;
        }

        public Criteria andRealDeductAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_DEDUCT_AMT not between", value1, value2, "realDeductAmt");
            return (Criteria) this;
        }

        public Criteria andMakeAmtIsNull() {
            addCriterion("MAKE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andMakeAmtIsNotNull() {
            addCriterion("MAKE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andMakeAmtEqualTo(BigDecimal value) {
            addCriterion("MAKE_AMT =", value, "makeAmt");
            return (Criteria) this;
        }

        public Criteria andMakeAmtNotEqualTo(BigDecimal value) {
            addCriterion("MAKE_AMT <>", value, "makeAmt");
            return (Criteria) this;
        }

        public Criteria andMakeAmtGreaterThan(BigDecimal value) {
            addCriterion("MAKE_AMT >", value, "makeAmt");
            return (Criteria) this;
        }

        public Criteria andMakeAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MAKE_AMT >=", value, "makeAmt");
            return (Criteria) this;
        }

        public Criteria andMakeAmtLessThan(BigDecimal value) {
            addCriterion("MAKE_AMT <", value, "makeAmt");
            return (Criteria) this;
        }

        public Criteria andMakeAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MAKE_AMT <=", value, "makeAmt");
            return (Criteria) this;
        }

        public Criteria andMakeAmtIn(List<BigDecimal> values) {
            addCriterion("MAKE_AMT in", values, "makeAmt");
            return (Criteria) this;
        }

        public Criteria andMakeAmtNotIn(List<BigDecimal> values) {
            addCriterion("MAKE_AMT not in", values, "makeAmt");
            return (Criteria) this;
        }

        public Criteria andMakeAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MAKE_AMT between", value1, value2, "makeAmt");
            return (Criteria) this;
        }

        public Criteria andMakeAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MAKE_AMT not between", value1, value2, "makeAmt");
            return (Criteria) this;
        }

        public Criteria andLossAmtIsNull() {
            addCriterion("LOSS_AMT is null");
            return (Criteria) this;
        }

        public Criteria andLossAmtIsNotNull() {
            addCriterion("LOSS_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andLossAmtEqualTo(BigDecimal value) {
            addCriterion("LOSS_AMT =", value, "lossAmt");
            return (Criteria) this;
        }

        public Criteria andLossAmtNotEqualTo(BigDecimal value) {
            addCriterion("LOSS_AMT <>", value, "lossAmt");
            return (Criteria) this;
        }

        public Criteria andLossAmtGreaterThan(BigDecimal value) {
            addCriterion("LOSS_AMT >", value, "lossAmt");
            return (Criteria) this;
        }

        public Criteria andLossAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("LOSS_AMT >=", value, "lossAmt");
            return (Criteria) this;
        }

        public Criteria andLossAmtLessThan(BigDecimal value) {
            addCriterion("LOSS_AMT <", value, "lossAmt");
            return (Criteria) this;
        }

        public Criteria andLossAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("LOSS_AMT <=", value, "lossAmt");
            return (Criteria) this;
        }

        public Criteria andLossAmtIn(List<BigDecimal> values) {
            addCriterion("LOSS_AMT in", values, "lossAmt");
            return (Criteria) this;
        }

        public Criteria andLossAmtNotIn(List<BigDecimal> values) {
            addCriterion("LOSS_AMT not in", values, "lossAmt");
            return (Criteria) this;
        }

        public Criteria andLossAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LOSS_AMT between", value1, value2, "lossAmt");
            return (Criteria) this;
        }

        public Criteria andLossAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LOSS_AMT not between", value1, value2, "lossAmt");
            return (Criteria) this;
        }

        public Criteria andHbOrgIdIsNull() {
            addCriterion("HB_ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andHbOrgIdIsNotNull() {
            addCriterion("HB_ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andHbOrgIdEqualTo(String value) {
            addCriterion("HB_ORG_ID =", value, "hbOrgId");
            return (Criteria) this;
        }

        public Criteria andHbOrgIdNotEqualTo(String value) {
            addCriterion("HB_ORG_ID <>", value, "hbOrgId");
            return (Criteria) this;
        }

        public Criteria andHbOrgIdGreaterThan(String value) {
            addCriterion("HB_ORG_ID >", value, "hbOrgId");
            return (Criteria) this;
        }

        public Criteria andHbOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("HB_ORG_ID >=", value, "hbOrgId");
            return (Criteria) this;
        }

        public Criteria andHbOrgIdLessThan(String value) {
            addCriterion("HB_ORG_ID <", value, "hbOrgId");
            return (Criteria) this;
        }

        public Criteria andHbOrgIdLessThanOrEqualTo(String value) {
            addCriterion("HB_ORG_ID <=", value, "hbOrgId");
            return (Criteria) this;
        }

        public Criteria andHbOrgIdLike(String value) {
            addCriterion("HB_ORG_ID like", value, "hbOrgId");
            return (Criteria) this;
        }

        public Criteria andHbOrgIdNotLike(String value) {
            addCriterion("HB_ORG_ID not like", value, "hbOrgId");
            return (Criteria) this;
        }

        public Criteria andHbOrgIdIn(List<String> values) {
            addCriterion("HB_ORG_ID in", values, "hbOrgId");
            return (Criteria) this;
        }

        public Criteria andHbOrgIdNotIn(List<String> values) {
            addCriterion("HB_ORG_ID not in", values, "hbOrgId");
            return (Criteria) this;
        }

        public Criteria andHbOrgIdBetween(String value1, String value2) {
            addCriterion("HB_ORG_ID between", value1, value2, "hbOrgId");
            return (Criteria) this;
        }

        public Criteria andHbOrgIdNotBetween(String value1, String value2) {
            addCriterion("HB_ORG_ID not between", value1, value2, "hbOrgId");
            return (Criteria) this;
        }

        public Criteria andOffsetLossAmtIsNull() {
            addCriterion("OFFSET_LOSS_AMT is null");
            return (Criteria) this;
        }

        public Criteria andOffsetLossAmtIsNotNull() {
            addCriterion("OFFSET_LOSS_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andOffsetLossAmtEqualTo(BigDecimal value) {
            addCriterion("OFFSET_LOSS_AMT =", value, "offsetLossAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetLossAmtNotEqualTo(BigDecimal value) {
            addCriterion("OFFSET_LOSS_AMT <>", value, "offsetLossAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetLossAmtGreaterThan(BigDecimal value) {
            addCriterion("OFFSET_LOSS_AMT >", value, "offsetLossAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetLossAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OFFSET_LOSS_AMT >=", value, "offsetLossAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetLossAmtLessThan(BigDecimal value) {
            addCriterion("OFFSET_LOSS_AMT <", value, "offsetLossAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetLossAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OFFSET_LOSS_AMT <=", value, "offsetLossAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetLossAmtIn(List<BigDecimal> values) {
            addCriterion("OFFSET_LOSS_AMT in", values, "offsetLossAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetLossAmtNotIn(List<BigDecimal> values) {
            addCriterion("OFFSET_LOSS_AMT not in", values, "offsetLossAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetLossAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OFFSET_LOSS_AMT between", value1, value2, "offsetLossAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetLossAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OFFSET_LOSS_AMT not between", value1, value2, "offsetLossAmt");
            return (Criteria) this;
        }

        public Criteria andProvincesIsNull() {
            addCriterion("PROVINCES is null");
            return (Criteria) this;
        }

        public Criteria andProvincesIsNotNull() {
            addCriterion("PROVINCES is not null");
            return (Criteria) this;
        }

        public Criteria andProvincesEqualTo(String value) {
            addCriterion("PROVINCES =", value, "provinces");
            return (Criteria) this;
        }

        public Criteria andProvincesNotEqualTo(String value) {
            addCriterion("PROVINCES <>", value, "provinces");
            return (Criteria) this;
        }

        public Criteria andProvincesGreaterThan(String value) {
            addCriterion("PROVINCES >", value, "provinces");
            return (Criteria) this;
        }

        public Criteria andProvincesGreaterThanOrEqualTo(String value) {
            addCriterion("PROVINCES >=", value, "provinces");
            return (Criteria) this;
        }

        public Criteria andProvincesLessThan(String value) {
            addCriterion("PROVINCES <", value, "provinces");
            return (Criteria) this;
        }

        public Criteria andProvincesLessThanOrEqualTo(String value) {
            addCriterion("PROVINCES <=", value, "provinces");
            return (Criteria) this;
        }

        public Criteria andProvincesLike(String value) {
            addCriterion("PROVINCES like", value, "provinces");
            return (Criteria) this;
        }

        public Criteria andProvincesNotLike(String value) {
            addCriterion("PROVINCES not like", value, "provinces");
            return (Criteria) this;
        }

        public Criteria andProvincesIn(List<String> values) {
            addCriterion("PROVINCES in", values, "provinces");
            return (Criteria) this;
        }

        public Criteria andProvincesNotIn(List<String> values) {
            addCriterion("PROVINCES not in", values, "provinces");
            return (Criteria) this;
        }

        public Criteria andProvincesBetween(String value1, String value2) {
            addCriterion("PROVINCES between", value1, value2, "provinces");
            return (Criteria) this;
        }

        public Criteria andProvincesNotBetween(String value1, String value2) {
            addCriterion("PROVINCES not between", value1, value2, "provinces");
            return (Criteria) this;
        }

        public Criteria andFenrunFlagIsNull() {
            addCriterion("FENRUN_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andFenrunFlagIsNotNull() {
            addCriterion("FENRUN_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andFenrunFlagEqualTo(String value) {
            addCriterion("FENRUN_FLAG =", value, "fenrunFlag");
            return (Criteria) this;
        }

        public Criteria andFenrunFlagNotEqualTo(String value) {
            addCriterion("FENRUN_FLAG <>", value, "fenrunFlag");
            return (Criteria) this;
        }

        public Criteria andFenrunFlagGreaterThan(String value) {
            addCriterion("FENRUN_FLAG >", value, "fenrunFlag");
            return (Criteria) this;
        }

        public Criteria andFenrunFlagGreaterThanOrEqualTo(String value) {
            addCriterion("FENRUN_FLAG >=", value, "fenrunFlag");
            return (Criteria) this;
        }

        public Criteria andFenrunFlagLessThan(String value) {
            addCriterion("FENRUN_FLAG <", value, "fenrunFlag");
            return (Criteria) this;
        }

        public Criteria andFenrunFlagLessThanOrEqualTo(String value) {
            addCriterion("FENRUN_FLAG <=", value, "fenrunFlag");
            return (Criteria) this;
        }

        public Criteria andFenrunFlagLike(String value) {
            addCriterion("FENRUN_FLAG like", value, "fenrunFlag");
            return (Criteria) this;
        }

        public Criteria andFenrunFlagNotLike(String value) {
            addCriterion("FENRUN_FLAG not like", value, "fenrunFlag");
            return (Criteria) this;
        }

        public Criteria andFenrunFlagIn(List<String> values) {
            addCriterion("FENRUN_FLAG in", values, "fenrunFlag");
            return (Criteria) this;
        }

        public Criteria andFenrunFlagNotIn(List<String> values) {
            addCriterion("FENRUN_FLAG not in", values, "fenrunFlag");
            return (Criteria) this;
        }

        public Criteria andFenrunFlagBetween(String value1, String value2) {
            addCriterion("FENRUN_FLAG between", value1, value2, "fenrunFlag");
            return (Criteria) this;
        }

        public Criteria andFenrunFlagNotBetween(String value1, String value2) {
            addCriterion("FENRUN_FLAG not between", value1, value2, "fenrunFlag");
            return (Criteria) this;
        }

        public Criteria andInstIdIsNull() {
            addCriterion("INST_ID is null");
            return (Criteria) this;
        }

        public Criteria andInstIdIsNotNull() {
            addCriterion("INST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andInstIdEqualTo(String value) {
            addCriterion("INST_ID =", value, "instId");
            return (Criteria) this;
        }

        public Criteria andInstIdNotEqualTo(String value) {
            addCriterion("INST_ID <>", value, "instId");
            return (Criteria) this;
        }

        public Criteria andInstIdGreaterThan(String value) {
            addCriterion("INST_ID >", value, "instId");
            return (Criteria) this;
        }

        public Criteria andInstIdGreaterThanOrEqualTo(String value) {
            addCriterion("INST_ID >=", value, "instId");
            return (Criteria) this;
        }

        public Criteria andInstIdLessThan(String value) {
            addCriterion("INST_ID <", value, "instId");
            return (Criteria) this;
        }

        public Criteria andInstIdLessThanOrEqualTo(String value) {
            addCriterion("INST_ID <=", value, "instId");
            return (Criteria) this;
        }

        public Criteria andInstIdLike(String value) {
            addCriterion("INST_ID like", value, "instId");
            return (Criteria) this;
        }

        public Criteria andInstIdNotLike(String value) {
            addCriterion("INST_ID not like", value, "instId");
            return (Criteria) this;
        }

        public Criteria andInstIdIn(List<String> values) {
            addCriterion("INST_ID in", values, "instId");
            return (Criteria) this;
        }

        public Criteria andInstIdNotIn(List<String> values) {
            addCriterion("INST_ID not in", values, "instId");
            return (Criteria) this;
        }

        public Criteria andInstIdBetween(String value1, String value2) {
            addCriterion("INST_ID between", value1, value2, "instId");
            return (Criteria) this;
        }

        public Criteria andInstIdNotBetween(String value1, String value2) {
            addCriterion("INST_ID not between", value1, value2, "instId");
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