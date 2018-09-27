package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PAgentExitApplyforExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PAgentExitApplyforExample() {
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

        public Criteria andApplyPlatIsNull() {
            addCriterion("APPLY_PLAT is null");
            return (Criteria) this;
        }

        public Criteria andApplyPlatIsNotNull() {
            addCriterion("APPLY_PLAT is not null");
            return (Criteria) this;
        }

        public Criteria andApplyPlatEqualTo(String value) {
            addCriterion("APPLY_PLAT =", value, "applyPlat");
            return (Criteria) this;
        }

        public Criteria andApplyPlatNotEqualTo(String value) {
            addCriterion("APPLY_PLAT <>", value, "applyPlat");
            return (Criteria) this;
        }

        public Criteria andApplyPlatGreaterThan(String value) {
            addCriterion("APPLY_PLAT >", value, "applyPlat");
            return (Criteria) this;
        }

        public Criteria andApplyPlatGreaterThanOrEqualTo(String value) {
            addCriterion("APPLY_PLAT >=", value, "applyPlat");
            return (Criteria) this;
        }

        public Criteria andApplyPlatLessThan(String value) {
            addCriterion("APPLY_PLAT <", value, "applyPlat");
            return (Criteria) this;
        }

        public Criteria andApplyPlatLessThanOrEqualTo(String value) {
            addCriterion("APPLY_PLAT <=", value, "applyPlat");
            return (Criteria) this;
        }

        public Criteria andApplyPlatLike(String value) {
            addCriterion("APPLY_PLAT like", value, "applyPlat");
            return (Criteria) this;
        }

        public Criteria andApplyPlatNotLike(String value) {
            addCriterion("APPLY_PLAT not like", value, "applyPlat");
            return (Criteria) this;
        }

        public Criteria andApplyPlatIn(List<String> values) {
            addCriterion("APPLY_PLAT in", values, "applyPlat");
            return (Criteria) this;
        }

        public Criteria andApplyPlatNotIn(List<String> values) {
            addCriterion("APPLY_PLAT not in", values, "applyPlat");
            return (Criteria) this;
        }

        public Criteria andApplyPlatBetween(String value1, String value2) {
            addCriterion("APPLY_PLAT between", value1, value2, "applyPlat");
            return (Criteria) this;
        }

        public Criteria andApplyPlatNotBetween(String value1, String value2) {
            addCriterion("APPLY_PLAT not between", value1, value2, "applyPlat");
            return (Criteria) this;
        }

        public Criteria andKetubbahIdIsNull() {
            addCriterion("KETUBBAH_ID is null");
            return (Criteria) this;
        }

        public Criteria andKetubbahIdIsNotNull() {
            addCriterion("KETUBBAH_ID is not null");
            return (Criteria) this;
        }

        public Criteria andKetubbahIdEqualTo(String value) {
            addCriterion("KETUBBAH_ID =", value, "ketubbahId");
            return (Criteria) this;
        }

        public Criteria andKetubbahIdNotEqualTo(String value) {
            addCriterion("KETUBBAH_ID <>", value, "ketubbahId");
            return (Criteria) this;
        }

        public Criteria andKetubbahIdGreaterThan(String value) {
            addCriterion("KETUBBAH_ID >", value, "ketubbahId");
            return (Criteria) this;
        }

        public Criteria andKetubbahIdGreaterThanOrEqualTo(String value) {
            addCriterion("KETUBBAH_ID >=", value, "ketubbahId");
            return (Criteria) this;
        }

        public Criteria andKetubbahIdLessThan(String value) {
            addCriterion("KETUBBAH_ID <", value, "ketubbahId");
            return (Criteria) this;
        }

        public Criteria andKetubbahIdLessThanOrEqualTo(String value) {
            addCriterion("KETUBBAH_ID <=", value, "ketubbahId");
            return (Criteria) this;
        }

        public Criteria andKetubbahIdLike(String value) {
            addCriterion("KETUBBAH_ID like", value, "ketubbahId");
            return (Criteria) this;
        }

        public Criteria andKetubbahIdNotLike(String value) {
            addCriterion("KETUBBAH_ID not like", value, "ketubbahId");
            return (Criteria) this;
        }

        public Criteria andKetubbahIdIn(List<String> values) {
            addCriterion("KETUBBAH_ID in", values, "ketubbahId");
            return (Criteria) this;
        }

        public Criteria andKetubbahIdNotIn(List<String> values) {
            addCriterion("KETUBBAH_ID not in", values, "ketubbahId");
            return (Criteria) this;
        }

        public Criteria andKetubbahIdBetween(String value1, String value2) {
            addCriterion("KETUBBAH_ID between", value1, value2, "ketubbahId");
            return (Criteria) this;
        }

        public Criteria andKetubbahIdNotBetween(String value1, String value2) {
            addCriterion("KETUBBAH_ID not between", value1, value2, "ketubbahId");
            return (Criteria) this;
        }

        public Criteria andDebtAmtIsNull() {
            addCriterion("DEBT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andDebtAmtIsNotNull() {
            addCriterion("DEBT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andDebtAmtEqualTo(BigDecimal value) {
            addCriterion("DEBT_AMT =", value, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtNotEqualTo(BigDecimal value) {
            addCriterion("DEBT_AMT <>", value, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtGreaterThan(BigDecimal value) {
            addCriterion("DEBT_AMT >", value, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DEBT_AMT >=", value, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtLessThan(BigDecimal value) {
            addCriterion("DEBT_AMT <", value, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DEBT_AMT <=", value, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtIn(List<BigDecimal> values) {
            addCriterion("DEBT_AMT in", values, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtNotIn(List<BigDecimal> values) {
            addCriterion("DEBT_AMT not in", values, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEBT_AMT between", value1, value2, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEBT_AMT not between", value1, value2, "debtAmt");
            return (Criteria) this;
        }

        public Criteria andDebtBillIsNull() {
            addCriterion("DEBT_BILL is null");
            return (Criteria) this;
        }

        public Criteria andDebtBillIsNotNull() {
            addCriterion("DEBT_BILL is not null");
            return (Criteria) this;
        }

        public Criteria andDebtBillEqualTo(BigDecimal value) {
            addCriterion("DEBT_BILL =", value, "debtBill");
            return (Criteria) this;
        }

        public Criteria andDebtBillNotEqualTo(BigDecimal value) {
            addCriterion("DEBT_BILL <>", value, "debtBill");
            return (Criteria) this;
        }

        public Criteria andDebtBillGreaterThan(BigDecimal value) {
            addCriterion("DEBT_BILL >", value, "debtBill");
            return (Criteria) this;
        }

        public Criteria andDebtBillGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DEBT_BILL >=", value, "debtBill");
            return (Criteria) this;
        }

        public Criteria andDebtBillLessThan(BigDecimal value) {
            addCriterion("DEBT_BILL <", value, "debtBill");
            return (Criteria) this;
        }

        public Criteria andDebtBillLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DEBT_BILL <=", value, "debtBill");
            return (Criteria) this;
        }

        public Criteria andDebtBillIn(List<BigDecimal> values) {
            addCriterion("DEBT_BILL in", values, "debtBill");
            return (Criteria) this;
        }

        public Criteria andDebtBillNotIn(List<BigDecimal> values) {
            addCriterion("DEBT_BILL not in", values, "debtBill");
            return (Criteria) this;
        }

        public Criteria andDebtBillBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEBT_BILL between", value1, value2, "debtBill");
            return (Criteria) this;
        }

        public Criteria andDebtBillNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEBT_BILL not between", value1, value2, "debtBill");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtAmtIsNull() {
            addCriterion("SUPPLY_DEBT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtAmtIsNotNull() {
            addCriterion("SUPPLY_DEBT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtAmtEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_DEBT_AMT =", value, "supplyDebtAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtAmtNotEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_DEBT_AMT <>", value, "supplyDebtAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtAmtGreaterThan(BigDecimal value) {
            addCriterion("SUPPLY_DEBT_AMT >", value, "supplyDebtAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_DEBT_AMT >=", value, "supplyDebtAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtAmtLessThan(BigDecimal value) {
            addCriterion("SUPPLY_DEBT_AMT <", value, "supplyDebtAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_DEBT_AMT <=", value, "supplyDebtAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtAmtIn(List<BigDecimal> values) {
            addCriterion("SUPPLY_DEBT_AMT in", values, "supplyDebtAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtAmtNotIn(List<BigDecimal> values) {
            addCriterion("SUPPLY_DEBT_AMT not in", values, "supplyDebtAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPPLY_DEBT_AMT between", value1, value2, "supplyDebtAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPPLY_DEBT_AMT not between", value1, value2, "supplyDebtAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtBillIsNull() {
            addCriterion("SUPPLY_DEBT_BILL is null");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtBillIsNotNull() {
            addCriterion("SUPPLY_DEBT_BILL is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtBillEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_DEBT_BILL =", value, "supplyDebtBill");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtBillNotEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_DEBT_BILL <>", value, "supplyDebtBill");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtBillGreaterThan(BigDecimal value) {
            addCriterion("SUPPLY_DEBT_BILL >", value, "supplyDebtBill");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtBillGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_DEBT_BILL >=", value, "supplyDebtBill");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtBillLessThan(BigDecimal value) {
            addCriterion("SUPPLY_DEBT_BILL <", value, "supplyDebtBill");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtBillLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_DEBT_BILL <=", value, "supplyDebtBill");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtBillIn(List<BigDecimal> values) {
            addCriterion("SUPPLY_DEBT_BILL in", values, "supplyDebtBill");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtBillNotIn(List<BigDecimal> values) {
            addCriterion("SUPPLY_DEBT_BILL not in", values, "supplyDebtBill");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtBillBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPPLY_DEBT_BILL between", value1, value2, "supplyDebtBill");
            return (Criteria) this;
        }

        public Criteria andSupplyDebtBillNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPPLY_DEBT_BILL not between", value1, value2, "supplyDebtBill");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeIsNull() {
            addCriterion("SUPPLY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeIsNotNull() {
            addCriterion("SUPPLY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeEqualTo(String value) {
            addCriterion("SUPPLY_TYPE =", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeNotEqualTo(String value) {
            addCriterion("SUPPLY_TYPE <>", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeGreaterThan(String value) {
            addCriterion("SUPPLY_TYPE >", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeGreaterThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TYPE >=", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeLessThan(String value) {
            addCriterion("SUPPLY_TYPE <", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeLessThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TYPE <=", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeLike(String value) {
            addCriterion("SUPPLY_TYPE like", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeNotLike(String value) {
            addCriterion("SUPPLY_TYPE not like", value, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeIn(List<String> values) {
            addCriterion("SUPPLY_TYPE in", values, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeNotIn(List<String> values) {
            addCriterion("SUPPLY_TYPE not in", values, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeBetween(String value1, String value2) {
            addCriterion("SUPPLY_TYPE between", value1, value2, "supplyType");
            return (Criteria) this;
        }

        public Criteria andSupplyTypeNotBetween(String value1, String value2) {
            addCriterion("SUPPLY_TYPE not between", value1, value2, "supplyType");
            return (Criteria) this;
        }

        public Criteria andPayCompanyIsNull() {
            addCriterion("PAY_COMPANY is null");
            return (Criteria) this;
        }

        public Criteria andPayCompanyIsNotNull() {
            addCriterion("PAY_COMPANY is not null");
            return (Criteria) this;
        }

        public Criteria andPayCompanyEqualTo(String value) {
            addCriterion("PAY_COMPANY =", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyNotEqualTo(String value) {
            addCriterion("PAY_COMPANY <>", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyGreaterThan(String value) {
            addCriterion("PAY_COMPANY >", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_COMPANY >=", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyLessThan(String value) {
            addCriterion("PAY_COMPANY <", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyLessThanOrEqualTo(String value) {
            addCriterion("PAY_COMPANY <=", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyLike(String value) {
            addCriterion("PAY_COMPANY like", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyNotLike(String value) {
            addCriterion("PAY_COMPANY not like", value, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyIn(List<String> values) {
            addCriterion("PAY_COMPANY in", values, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyNotIn(List<String> values) {
            addCriterion("PAY_COMPANY not in", values, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyBetween(String value1, String value2) {
            addCriterion("PAY_COMPANY between", value1, value2, "payCompany");
            return (Criteria) this;
        }

        public Criteria andPayCompanyNotBetween(String value1, String value2) {
            addCriterion("PAY_COMPANY not between", value1, value2, "payCompany");
            return (Criteria) this;
        }

        public Criteria andRefundStatusIsNull() {
            addCriterion("REFUND_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andRefundStatusIsNotNull() {
            addCriterion("REFUND_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andRefundStatusEqualTo(String value) {
            addCriterion("REFUND_STATUS =", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusNotEqualTo(String value) {
            addCriterion("REFUND_STATUS <>", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusGreaterThan(String value) {
            addCriterion("REFUND_STATUS >", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusGreaterThanOrEqualTo(String value) {
            addCriterion("REFUND_STATUS >=", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusLessThan(String value) {
            addCriterion("REFUND_STATUS <", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusLessThanOrEqualTo(String value) {
            addCriterion("REFUND_STATUS <=", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusLike(String value) {
            addCriterion("REFUND_STATUS like", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusNotLike(String value) {
            addCriterion("REFUND_STATUS not like", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusIn(List<String> values) {
            addCriterion("REFUND_STATUS in", values, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusNotIn(List<String> values) {
            addCriterion("REFUND_STATUS not in", values, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusBetween(String value1, String value2) {
            addCriterion("REFUND_STATUS between", value1, value2, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusNotBetween(String value1, String value2) {
            addCriterion("REFUND_STATUS not between", value1, value2, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusIsNull() {
            addCriterion("FLOW_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andFlowStatusIsNotNull() {
            addCriterion("FLOW_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andFlowStatusEqualTo(String value) {
            addCriterion("FLOW_STATUS =", value, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusNotEqualTo(String value) {
            addCriterion("FLOW_STATUS <>", value, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusGreaterThan(String value) {
            addCriterion("FLOW_STATUS >", value, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusGreaterThanOrEqualTo(String value) {
            addCriterion("FLOW_STATUS >=", value, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusLessThan(String value) {
            addCriterion("FLOW_STATUS <", value, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusLessThanOrEqualTo(String value) {
            addCriterion("FLOW_STATUS <=", value, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusLike(String value) {
            addCriterion("FLOW_STATUS like", value, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusNotLike(String value) {
            addCriterion("FLOW_STATUS not like", value, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusIn(List<String> values) {
            addCriterion("FLOW_STATUS in", values, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusNotIn(List<String> values) {
            addCriterion("FLOW_STATUS not in", values, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusBetween(String value1, String value2) {
            addCriterion("FLOW_STATUS between", value1, value2, "flowStatus");
            return (Criteria) this;
        }

        public Criteria andFlowStatusNotBetween(String value1, String value2) {
            addCriterion("FLOW_STATUS not between", value1, value2, "flowStatus");
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

        public Criteria andPassDateIsNull() {
            addCriterion("PASS_DATE is null");
            return (Criteria) this;
        }

        public Criteria andPassDateIsNotNull() {
            addCriterion("PASS_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andPassDateEqualTo(String value) {
            addCriterion("PASS_DATE =", value, "passDate");
            return (Criteria) this;
        }

        public Criteria andPassDateNotEqualTo(String value) {
            addCriterion("PASS_DATE <>", value, "passDate");
            return (Criteria) this;
        }

        public Criteria andPassDateGreaterThan(String value) {
            addCriterion("PASS_DATE >", value, "passDate");
            return (Criteria) this;
        }

        public Criteria andPassDateGreaterThanOrEqualTo(String value) {
            addCriterion("PASS_DATE >=", value, "passDate");
            return (Criteria) this;
        }

        public Criteria andPassDateLessThan(String value) {
            addCriterion("PASS_DATE <", value, "passDate");
            return (Criteria) this;
        }

        public Criteria andPassDateLessThanOrEqualTo(String value) {
            addCriterion("PASS_DATE <=", value, "passDate");
            return (Criteria) this;
        }

        public Criteria andPassDateLike(String value) {
            addCriterion("PASS_DATE like", value, "passDate");
            return (Criteria) this;
        }

        public Criteria andPassDateNotLike(String value) {
            addCriterion("PASS_DATE not like", value, "passDate");
            return (Criteria) this;
        }

        public Criteria andPassDateIn(List<String> values) {
            addCriterion("PASS_DATE in", values, "passDate");
            return (Criteria) this;
        }

        public Criteria andPassDateNotIn(List<String> values) {
            addCriterion("PASS_DATE not in", values, "passDate");
            return (Criteria) this;
        }

        public Criteria andPassDateBetween(String value1, String value2) {
            addCriterion("PASS_DATE between", value1, value2, "passDate");
            return (Criteria) this;
        }

        public Criteria andPassDateNotBetween(String value1, String value2) {
            addCriterion("PASS_DATE not between", value1, value2, "passDate");
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