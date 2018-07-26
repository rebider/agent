package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ORefundPriceDiffExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ORefundPriceDiffExample() {
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

        public Criteria andApplyCompTypeIsNull() {
            addCriterion("APPLY_COMP_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andApplyCompTypeIsNotNull() {
            addCriterion("APPLY_COMP_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andApplyCompTypeEqualTo(String value) {
            addCriterion("APPLY_COMP_TYPE =", value, "applyCompType");
            return (Criteria) this;
        }

        public Criteria andApplyCompTypeNotEqualTo(String value) {
            addCriterion("APPLY_COMP_TYPE <>", value, "applyCompType");
            return (Criteria) this;
        }

        public Criteria andApplyCompTypeGreaterThan(String value) {
            addCriterion("APPLY_COMP_TYPE >", value, "applyCompType");
            return (Criteria) this;
        }

        public Criteria andApplyCompTypeGreaterThanOrEqualTo(String value) {
            addCriterion("APPLY_COMP_TYPE >=", value, "applyCompType");
            return (Criteria) this;
        }

        public Criteria andApplyCompTypeLessThan(String value) {
            addCriterion("APPLY_COMP_TYPE <", value, "applyCompType");
            return (Criteria) this;
        }

        public Criteria andApplyCompTypeLessThanOrEqualTo(String value) {
            addCriterion("APPLY_COMP_TYPE <=", value, "applyCompType");
            return (Criteria) this;
        }

        public Criteria andApplyCompTypeLike(String value) {
            addCriterion("APPLY_COMP_TYPE like", value, "applyCompType");
            return (Criteria) this;
        }

        public Criteria andApplyCompTypeNotLike(String value) {
            addCriterion("APPLY_COMP_TYPE not like", value, "applyCompType");
            return (Criteria) this;
        }

        public Criteria andApplyCompTypeIn(List<String> values) {
            addCriterion("APPLY_COMP_TYPE in", values, "applyCompType");
            return (Criteria) this;
        }

        public Criteria andApplyCompTypeNotIn(List<String> values) {
            addCriterion("APPLY_COMP_TYPE not in", values, "applyCompType");
            return (Criteria) this;
        }

        public Criteria andApplyCompTypeBetween(String value1, String value2) {
            addCriterion("APPLY_COMP_TYPE between", value1, value2, "applyCompType");
            return (Criteria) this;
        }

        public Criteria andApplyCompTypeNotBetween(String value1, String value2) {
            addCriterion("APPLY_COMP_TYPE not between", value1, value2, "applyCompType");
            return (Criteria) this;
        }

        public Criteria andApplyCompAmtIsNull() {
            addCriterion("APPLY_COMP_AMT is null");
            return (Criteria) this;
        }

        public Criteria andApplyCompAmtIsNotNull() {
            addCriterion("APPLY_COMP_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andApplyCompAmtEqualTo(BigDecimal value) {
            addCriterion("APPLY_COMP_AMT =", value, "applyCompAmt");
            return (Criteria) this;
        }

        public Criteria andApplyCompAmtNotEqualTo(BigDecimal value) {
            addCriterion("APPLY_COMP_AMT <>", value, "applyCompAmt");
            return (Criteria) this;
        }

        public Criteria andApplyCompAmtGreaterThan(BigDecimal value) {
            addCriterion("APPLY_COMP_AMT >", value, "applyCompAmt");
            return (Criteria) this;
        }

        public Criteria andApplyCompAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("APPLY_COMP_AMT >=", value, "applyCompAmt");
            return (Criteria) this;
        }

        public Criteria andApplyCompAmtLessThan(BigDecimal value) {
            addCriterion("APPLY_COMP_AMT <", value, "applyCompAmt");
            return (Criteria) this;
        }

        public Criteria andApplyCompAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("APPLY_COMP_AMT <=", value, "applyCompAmt");
            return (Criteria) this;
        }

        public Criteria andApplyCompAmtIn(List<BigDecimal> values) {
            addCriterion("APPLY_COMP_AMT in", values, "applyCompAmt");
            return (Criteria) this;
        }

        public Criteria andApplyCompAmtNotIn(List<BigDecimal> values) {
            addCriterion("APPLY_COMP_AMT not in", values, "applyCompAmt");
            return (Criteria) this;
        }

        public Criteria andApplyCompAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APPLY_COMP_AMT between", value1, value2, "applyCompAmt");
            return (Criteria) this;
        }

        public Criteria andApplyCompAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APPLY_COMP_AMT not between", value1, value2, "applyCompAmt");
            return (Criteria) this;
        }

        public Criteria andRelCompTypeIsNull() {
            addCriterion("REL_COMP_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andRelCompTypeIsNotNull() {
            addCriterion("REL_COMP_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andRelCompTypeEqualTo(String value) {
            addCriterion("REL_COMP_TYPE =", value, "relCompType");
            return (Criteria) this;
        }

        public Criteria andRelCompTypeNotEqualTo(String value) {
            addCriterion("REL_COMP_TYPE <>", value, "relCompType");
            return (Criteria) this;
        }

        public Criteria andRelCompTypeGreaterThan(String value) {
            addCriterion("REL_COMP_TYPE >", value, "relCompType");
            return (Criteria) this;
        }

        public Criteria andRelCompTypeGreaterThanOrEqualTo(String value) {
            addCriterion("REL_COMP_TYPE >=", value, "relCompType");
            return (Criteria) this;
        }

        public Criteria andRelCompTypeLessThan(String value) {
            addCriterion("REL_COMP_TYPE <", value, "relCompType");
            return (Criteria) this;
        }

        public Criteria andRelCompTypeLessThanOrEqualTo(String value) {
            addCriterion("REL_COMP_TYPE <=", value, "relCompType");
            return (Criteria) this;
        }

        public Criteria andRelCompTypeLike(String value) {
            addCriterion("REL_COMP_TYPE like", value, "relCompType");
            return (Criteria) this;
        }

        public Criteria andRelCompTypeNotLike(String value) {
            addCriterion("REL_COMP_TYPE not like", value, "relCompType");
            return (Criteria) this;
        }

        public Criteria andRelCompTypeIn(List<String> values) {
            addCriterion("REL_COMP_TYPE in", values, "relCompType");
            return (Criteria) this;
        }

        public Criteria andRelCompTypeNotIn(List<String> values) {
            addCriterion("REL_COMP_TYPE not in", values, "relCompType");
            return (Criteria) this;
        }

        public Criteria andRelCompTypeBetween(String value1, String value2) {
            addCriterion("REL_COMP_TYPE between", value1, value2, "relCompType");
            return (Criteria) this;
        }

        public Criteria andRelCompTypeNotBetween(String value1, String value2) {
            addCriterion("REL_COMP_TYPE not between", value1, value2, "relCompType");
            return (Criteria) this;
        }

        public Criteria andRelCompAmtIsNull() {
            addCriterion("REL_COMP_AMT is null");
            return (Criteria) this;
        }

        public Criteria andRelCompAmtIsNotNull() {
            addCriterion("REL_COMP_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andRelCompAmtEqualTo(BigDecimal value) {
            addCriterion("REL_COMP_AMT =", value, "relCompAmt");
            return (Criteria) this;
        }

        public Criteria andRelCompAmtNotEqualTo(BigDecimal value) {
            addCriterion("REL_COMP_AMT <>", value, "relCompAmt");
            return (Criteria) this;
        }

        public Criteria andRelCompAmtGreaterThan(BigDecimal value) {
            addCriterion("REL_COMP_AMT >", value, "relCompAmt");
            return (Criteria) this;
        }

        public Criteria andRelCompAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REL_COMP_AMT >=", value, "relCompAmt");
            return (Criteria) this;
        }

        public Criteria andRelCompAmtLessThan(BigDecimal value) {
            addCriterion("REL_COMP_AMT <", value, "relCompAmt");
            return (Criteria) this;
        }

        public Criteria andRelCompAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REL_COMP_AMT <=", value, "relCompAmt");
            return (Criteria) this;
        }

        public Criteria andRelCompAmtIn(List<BigDecimal> values) {
            addCriterion("REL_COMP_AMT in", values, "relCompAmt");
            return (Criteria) this;
        }

        public Criteria andRelCompAmtNotIn(List<BigDecimal> values) {
            addCriterion("REL_COMP_AMT not in", values, "relCompAmt");
            return (Criteria) this;
        }

        public Criteria andRelCompAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REL_COMP_AMT between", value1, value2, "relCompAmt");
            return (Criteria) this;
        }

        public Criteria andRelCompAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REL_COMP_AMT not between", value1, value2, "relCompAmt");
            return (Criteria) this;
        }

        public Criteria andRefundTypeIsNull() {
            addCriterion("REFUND_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andRefundTypeIsNotNull() {
            addCriterion("REFUND_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andRefundTypeEqualTo(String value) {
            addCriterion("REFUND_TYPE =", value, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeNotEqualTo(String value) {
            addCriterion("REFUND_TYPE <>", value, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeGreaterThan(String value) {
            addCriterion("REFUND_TYPE >", value, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeGreaterThanOrEqualTo(String value) {
            addCriterion("REFUND_TYPE >=", value, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeLessThan(String value) {
            addCriterion("REFUND_TYPE <", value, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeLessThanOrEqualTo(String value) {
            addCriterion("REFUND_TYPE <=", value, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeLike(String value) {
            addCriterion("REFUND_TYPE like", value, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeNotLike(String value) {
            addCriterion("REFUND_TYPE not like", value, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeIn(List<String> values) {
            addCriterion("REFUND_TYPE in", values, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeNotIn(List<String> values) {
            addCriterion("REFUND_TYPE not in", values, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeBetween(String value1, String value2) {
            addCriterion("REFUND_TYPE between", value1, value2, "refundType");
            return (Criteria) this;
        }

        public Criteria andRefundTypeNotBetween(String value1, String value2) {
            addCriterion("REFUND_TYPE not between", value1, value2, "refundType");
            return (Criteria) this;
        }

        public Criteria andReviewStatusIsNull() {
            addCriterion("REVIEW_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andReviewStatusIsNotNull() {
            addCriterion("REVIEW_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andReviewStatusEqualTo(BigDecimal value) {
            addCriterion("REVIEW_STATUS =", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusNotEqualTo(BigDecimal value) {
            addCriterion("REVIEW_STATUS <>", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusGreaterThan(BigDecimal value) {
            addCriterion("REVIEW_STATUS >", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REVIEW_STATUS >=", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusLessThan(BigDecimal value) {
            addCriterion("REVIEW_STATUS <", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REVIEW_STATUS <=", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusIn(List<BigDecimal> values) {
            addCriterion("REVIEW_STATUS in", values, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusNotIn(List<BigDecimal> values) {
            addCriterion("REVIEW_STATUS not in", values, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REVIEW_STATUS between", value1, value2, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REVIEW_STATUS not between", value1, value2, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andSTimeIsNull() {
            addCriterion("S_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSTimeIsNotNull() {
            addCriterion("S_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSTimeEqualTo(Date value) {
            addCriterion("S_TIME =", value, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeNotEqualTo(Date value) {
            addCriterion("S_TIME <>", value, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeGreaterThan(Date value) {
            addCriterion("S_TIME >", value, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("S_TIME >=", value, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeLessThan(Date value) {
            addCriterion("S_TIME <", value, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeLessThanOrEqualTo(Date value) {
            addCriterion("S_TIME <=", value, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeIn(List<Date> values) {
            addCriterion("S_TIME in", values, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeNotIn(List<Date> values) {
            addCriterion("S_TIME not in", values, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeBetween(Date value1, Date value2) {
            addCriterion("S_TIME between", value1, value2, "sTime");
            return (Criteria) this;
        }

        public Criteria andSTimeNotBetween(Date value1, Date value2) {
            addCriterion("S_TIME not between", value1, value2, "sTime");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkIsNull() {
            addCriterion("APPLY_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkIsNotNull() {
            addCriterion("APPLY_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkEqualTo(String value) {
            addCriterion("APPLY_REMARK =", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkNotEqualTo(String value) {
            addCriterion("APPLY_REMARK <>", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkGreaterThan(String value) {
            addCriterion("APPLY_REMARK >", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("APPLY_REMARK >=", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkLessThan(String value) {
            addCriterion("APPLY_REMARK <", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkLessThanOrEqualTo(String value) {
            addCriterion("APPLY_REMARK <=", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkLike(String value) {
            addCriterion("APPLY_REMARK like", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkNotLike(String value) {
            addCriterion("APPLY_REMARK not like", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkIn(List<String> values) {
            addCriterion("APPLY_REMARK in", values, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkNotIn(List<String> values) {
            addCriterion("APPLY_REMARK not in", values, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkBetween(String value1, String value2) {
            addCriterion("APPLY_REMARK between", value1, value2, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkNotBetween(String value1, String value2) {
            addCriterion("APPLY_REMARK not between", value1, value2, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andDeductAmtIsNull() {
            addCriterion("DEDUCT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andDeductAmtIsNotNull() {
            addCriterion("DEDUCT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andDeductAmtEqualTo(BigDecimal value) {
            addCriterion("DEDUCT_AMT =", value, "deductAmt");
            return (Criteria) this;
        }

        public Criteria andDeductAmtNotEqualTo(BigDecimal value) {
            addCriterion("DEDUCT_AMT <>", value, "deductAmt");
            return (Criteria) this;
        }

        public Criteria andDeductAmtGreaterThan(BigDecimal value) {
            addCriterion("DEDUCT_AMT >", value, "deductAmt");
            return (Criteria) this;
        }

        public Criteria andDeductAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DEDUCT_AMT >=", value, "deductAmt");
            return (Criteria) this;
        }

        public Criteria andDeductAmtLessThan(BigDecimal value) {
            addCriterion("DEDUCT_AMT <", value, "deductAmt");
            return (Criteria) this;
        }

        public Criteria andDeductAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DEDUCT_AMT <=", value, "deductAmt");
            return (Criteria) this;
        }

        public Criteria andDeductAmtIn(List<BigDecimal> values) {
            addCriterion("DEDUCT_AMT in", values, "deductAmt");
            return (Criteria) this;
        }

        public Criteria andDeductAmtNotIn(List<BigDecimal> values) {
            addCriterion("DEDUCT_AMT not in", values, "deductAmt");
            return (Criteria) this;
        }

        public Criteria andDeductAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEDUCT_AMT between", value1, value2, "deductAmt");
            return (Criteria) this;
        }

        public Criteria andDeductAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEDUCT_AMT not between", value1, value2, "deductAmt");
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