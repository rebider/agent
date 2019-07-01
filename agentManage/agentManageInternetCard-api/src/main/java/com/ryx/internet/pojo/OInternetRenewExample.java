package com.ryx.internet.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OInternetRenewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OInternetRenewExample() {
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

        public Criteria andRenewWayIsNull() {
            addCriterion("RENEW_WAY is null");
            return (Criteria) this;
        }

        public Criteria andRenewWayIsNotNull() {
            addCriterion("RENEW_WAY is not null");
            return (Criteria) this;
        }

        public Criteria andRenewWayEqualTo(String value) {
            addCriterion("RENEW_WAY =", value, "renewWay");
            return (Criteria) this;
        }

        public Criteria andRenewWayNotEqualTo(String value) {
            addCriterion("RENEW_WAY <>", value, "renewWay");
            return (Criteria) this;
        }

        public Criteria andRenewWayGreaterThan(String value) {
            addCriterion("RENEW_WAY >", value, "renewWay");
            return (Criteria) this;
        }

        public Criteria andRenewWayGreaterThanOrEqualTo(String value) {
            addCriterion("RENEW_WAY >=", value, "renewWay");
            return (Criteria) this;
        }

        public Criteria andRenewWayLessThan(String value) {
            addCriterion("RENEW_WAY <", value, "renewWay");
            return (Criteria) this;
        }

        public Criteria andRenewWayLessThanOrEqualTo(String value) {
            addCriterion("RENEW_WAY <=", value, "renewWay");
            return (Criteria) this;
        }

        public Criteria andRenewWayLike(String value) {
            addCriterion("RENEW_WAY like", value, "renewWay");
            return (Criteria) this;
        }

        public Criteria andRenewWayNotLike(String value) {
            addCriterion("RENEW_WAY not like", value, "renewWay");
            return (Criteria) this;
        }

        public Criteria andRenewWayIn(List<String> values) {
            addCriterion("RENEW_WAY in", values, "renewWay");
            return (Criteria) this;
        }

        public Criteria andRenewWayNotIn(List<String> values) {
            addCriterion("RENEW_WAY not in", values, "renewWay");
            return (Criteria) this;
        }

        public Criteria andRenewWayBetween(String value1, String value2) {
            addCriterion("RENEW_WAY between", value1, value2, "renewWay");
            return (Criteria) this;
        }

        public Criteria andRenewWayNotBetween(String value1, String value2) {
            addCriterion("RENEW_WAY not between", value1, value2, "renewWay");
            return (Criteria) this;
        }

        public Criteria andRenewCardCountIsNull() {
            addCriterion("RENEW_CARD_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andRenewCardCountIsNotNull() {
            addCriterion("RENEW_CARD_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andRenewCardCountEqualTo(BigDecimal value) {
            addCriterion("RENEW_CARD_COUNT =", value, "renewCardCount");
            return (Criteria) this;
        }

        public Criteria andRenewCardCountNotEqualTo(BigDecimal value) {
            addCriterion("RENEW_CARD_COUNT <>", value, "renewCardCount");
            return (Criteria) this;
        }

        public Criteria andRenewCardCountGreaterThan(BigDecimal value) {
            addCriterion("RENEW_CARD_COUNT >", value, "renewCardCount");
            return (Criteria) this;
        }

        public Criteria andRenewCardCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RENEW_CARD_COUNT >=", value, "renewCardCount");
            return (Criteria) this;
        }

        public Criteria andRenewCardCountLessThan(BigDecimal value) {
            addCriterion("RENEW_CARD_COUNT <", value, "renewCardCount");
            return (Criteria) this;
        }

        public Criteria andRenewCardCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RENEW_CARD_COUNT <=", value, "renewCardCount");
            return (Criteria) this;
        }

        public Criteria andRenewCardCountIn(List<BigDecimal> values) {
            addCriterion("RENEW_CARD_COUNT in", values, "renewCardCount");
            return (Criteria) this;
        }

        public Criteria andRenewCardCountNotIn(List<BigDecimal> values) {
            addCriterion("RENEW_CARD_COUNT not in", values, "renewCardCount");
            return (Criteria) this;
        }

        public Criteria andRenewCardCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RENEW_CARD_COUNT between", value1, value2, "renewCardCount");
            return (Criteria) this;
        }

        public Criteria andRenewCardCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RENEW_CARD_COUNT not between", value1, value2, "renewCardCount");
            return (Criteria) this;
        }

        public Criteria andSumOffsetAmtIsNull() {
            addCriterion("SUM_OFFSET_AMT is null");
            return (Criteria) this;
        }

        public Criteria andSumOffsetAmtIsNotNull() {
            addCriterion("SUM_OFFSET_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andSumOffsetAmtEqualTo(BigDecimal value) {
            addCriterion("SUM_OFFSET_AMT =", value, "sumOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andSumOffsetAmtNotEqualTo(BigDecimal value) {
            addCriterion("SUM_OFFSET_AMT <>", value, "sumOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andSumOffsetAmtGreaterThan(BigDecimal value) {
            addCriterion("SUM_OFFSET_AMT >", value, "sumOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andSumOffsetAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUM_OFFSET_AMT >=", value, "sumOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andSumOffsetAmtLessThan(BigDecimal value) {
            addCriterion("SUM_OFFSET_AMT <", value, "sumOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andSumOffsetAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUM_OFFSET_AMT <=", value, "sumOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andSumOffsetAmtIn(List<BigDecimal> values) {
            addCriterion("SUM_OFFSET_AMT in", values, "sumOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andSumOffsetAmtNotIn(List<BigDecimal> values) {
            addCriterion("SUM_OFFSET_AMT not in", values, "sumOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andSumOffsetAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUM_OFFSET_AMT between", value1, value2, "sumOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andSumOffsetAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUM_OFFSET_AMT not between", value1, value2, "sumOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andSuppAmtIsNull() {
            addCriterion("SUPP_AMT is null");
            return (Criteria) this;
        }

        public Criteria andSuppAmtIsNotNull() {
            addCriterion("SUPP_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andSuppAmtEqualTo(BigDecimal value) {
            addCriterion("SUPP_AMT =", value, "suppAmt");
            return (Criteria) this;
        }

        public Criteria andSuppAmtNotEqualTo(BigDecimal value) {
            addCriterion("SUPP_AMT <>", value, "suppAmt");
            return (Criteria) this;
        }

        public Criteria andSuppAmtGreaterThan(BigDecimal value) {
            addCriterion("SUPP_AMT >", value, "suppAmt");
            return (Criteria) this;
        }

        public Criteria andSuppAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPP_AMT >=", value, "suppAmt");
            return (Criteria) this;
        }

        public Criteria andSuppAmtLessThan(BigDecimal value) {
            addCriterion("SUPP_AMT <", value, "suppAmt");
            return (Criteria) this;
        }

        public Criteria andSuppAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPP_AMT <=", value, "suppAmt");
            return (Criteria) this;
        }

        public Criteria andSuppAmtIn(List<BigDecimal> values) {
            addCriterion("SUPP_AMT in", values, "suppAmt");
            return (Criteria) this;
        }

        public Criteria andSuppAmtNotIn(List<BigDecimal> values) {
            addCriterion("SUPP_AMT not in", values, "suppAmt");
            return (Criteria) this;
        }

        public Criteria andSuppAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPP_AMT between", value1, value2, "suppAmt");
            return (Criteria) this;
        }

        public Criteria andSuppAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPP_AMT not between", value1, value2, "suppAmt");
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

        public Criteria andReviewPassTimeIsNull() {
            addCriterion("REVIEW_PASS_TIME is null");
            return (Criteria) this;
        }

        public Criteria andReviewPassTimeIsNotNull() {
            addCriterion("REVIEW_PASS_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andReviewPassTimeEqualTo(Date value) {
            addCriterion("REVIEW_PASS_TIME =", value, "reviewPassTime");
            return (Criteria) this;
        }

        public Criteria andReviewPassTimeNotEqualTo(Date value) {
            addCriterion("REVIEW_PASS_TIME <>", value, "reviewPassTime");
            return (Criteria) this;
        }

        public Criteria andReviewPassTimeGreaterThan(Date value) {
            addCriterion("REVIEW_PASS_TIME >", value, "reviewPassTime");
            return (Criteria) this;
        }

        public Criteria andReviewPassTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("REVIEW_PASS_TIME >=", value, "reviewPassTime");
            return (Criteria) this;
        }

        public Criteria andReviewPassTimeLessThan(Date value) {
            addCriterion("REVIEW_PASS_TIME <", value, "reviewPassTime");
            return (Criteria) this;
        }

        public Criteria andReviewPassTimeLessThanOrEqualTo(Date value) {
            addCriterion("REVIEW_PASS_TIME <=", value, "reviewPassTime");
            return (Criteria) this;
        }

        public Criteria andReviewPassTimeIn(List<Date> values) {
            addCriterion("REVIEW_PASS_TIME in", values, "reviewPassTime");
            return (Criteria) this;
        }

        public Criteria andReviewPassTimeNotIn(List<Date> values) {
            addCriterion("REVIEW_PASS_TIME not in", values, "reviewPassTime");
            return (Criteria) this;
        }

        public Criteria andReviewPassTimeBetween(Date value1, Date value2) {
            addCriterion("REVIEW_PASS_TIME between", value1, value2, "reviewPassTime");
            return (Criteria) this;
        }

        public Criteria andReviewPassTimeNotBetween(Date value1, Date value2) {
            addCriterion("REVIEW_PASS_TIME not between", value1, value2, "reviewPassTime");
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