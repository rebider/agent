package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FreezeRequestExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public FreezeRequestExample() {
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

        public Criteria andReqTypeIsNull() {
            addCriterion("REQ_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andReqTypeIsNotNull() {
            addCriterion("REQ_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andReqTypeEqualTo(BigDecimal value) {
            addCriterion("REQ_TYPE =", value, "reqType");
            return (Criteria) this;
        }

        public Criteria andReqTypeNotEqualTo(BigDecimal value) {
            addCriterion("REQ_TYPE <>", value, "reqType");
            return (Criteria) this;
        }

        public Criteria andReqTypeGreaterThan(BigDecimal value) {
            addCriterion("REQ_TYPE >", value, "reqType");
            return (Criteria) this;
        }

        public Criteria andReqTypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REQ_TYPE >=", value, "reqType");
            return (Criteria) this;
        }

        public Criteria andReqTypeLessThan(BigDecimal value) {
            addCriterion("REQ_TYPE <", value, "reqType");
            return (Criteria) this;
        }

        public Criteria andReqTypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REQ_TYPE <=", value, "reqType");
            return (Criteria) this;
        }

        public Criteria andReqTypeIn(List<BigDecimal> values) {
            addCriterion("REQ_TYPE in", values, "reqType");
            return (Criteria) this;
        }

        public Criteria andReqTypeNotIn(List<BigDecimal> values) {
            addCriterion("REQ_TYPE not in", values, "reqType");
            return (Criteria) this;
        }

        public Criteria andReqTypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REQ_TYPE between", value1, value2, "reqType");
            return (Criteria) this;
        }

        public Criteria andReqTypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REQ_TYPE not between", value1, value2, "reqType");
            return (Criteria) this;
        }

        public Criteria andCTmIsNull() {
            addCriterion("C_TM is null");
            return (Criteria) this;
        }

        public Criteria andCTmIsNotNull() {
            addCriterion("C_TM is not null");
            return (Criteria) this;
        }

        public Criteria andCTmEqualTo(Date value) {
            addCriterion("C_TM =", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmNotEqualTo(Date value) {
            addCriterion("C_TM <>", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmGreaterThan(Date value) {
            addCriterion("C_TM >", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmGreaterThanOrEqualTo(Date value) {
            addCriterion("C_TM >=", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmLessThan(Date value) {
            addCriterion("C_TM <", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmLessThanOrEqualTo(Date value) {
            addCriterion("C_TM <=", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmIn(List<Date> values) {
            addCriterion("C_TM in", values, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmNotIn(List<Date> values) {
            addCriterion("C_TM not in", values, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmBetween(Date value1, Date value2) {
            addCriterion("C_TM between", value1, value2, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmNotBetween(Date value1, Date value2) {
            addCriterion("C_TM not between", value1, value2, "cTm");
            return (Criteria) this;
        }

        public Criteria andCUserIdIsNull() {
            addCriterion("C_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andCUserIdIsNotNull() {
            addCriterion("C_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCUserIdEqualTo(String value) {
            addCriterion("C_USER_ID =", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdNotEqualTo(String value) {
            addCriterion("C_USER_ID <>", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdGreaterThan(String value) {
            addCriterion("C_USER_ID >", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("C_USER_ID >=", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdLessThan(String value) {
            addCriterion("C_USER_ID <", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdLessThanOrEqualTo(String value) {
            addCriterion("C_USER_ID <=", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdLike(String value) {
            addCriterion("C_USER_ID like", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdNotLike(String value) {
            addCriterion("C_USER_ID not like", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdIn(List<String> values) {
            addCriterion("C_USER_ID in", values, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdNotIn(List<String> values) {
            addCriterion("C_USER_ID not in", values, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdBetween(String value1, String value2) {
            addCriterion("C_USER_ID between", value1, value2, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdNotBetween(String value1, String value2) {
            addCriterion("C_USER_ID not between", value1, value2, "cUserId");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseIsNull() {
            addCriterion("FREEZE_CAUSE is null");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseIsNotNull() {
            addCriterion("FREEZE_CAUSE is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseEqualTo(String value) {
            addCriterion("FREEZE_CAUSE =", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseNotEqualTo(String value) {
            addCriterion("FREEZE_CAUSE <>", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseGreaterThan(String value) {
            addCriterion("FREEZE_CAUSE >", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_CAUSE >=", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseLessThan(String value) {
            addCriterion("FREEZE_CAUSE <", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_CAUSE <=", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseLike(String value) {
            addCriterion("FREEZE_CAUSE like", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseNotLike(String value) {
            addCriterion("FREEZE_CAUSE not like", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseIn(List<String> values) {
            addCriterion("FREEZE_CAUSE in", values, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseNotIn(List<String> values) {
            addCriterion("FREEZE_CAUSE not in", values, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseBetween(String value1, String value2) {
            addCriterion("FREEZE_CAUSE between", value1, value2, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseNotBetween(String value1, String value2) {
            addCriterion("FREEZE_CAUSE not between", value1, value2, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andReqReasonIsNull() {
            addCriterion("REQ_REASON is null");
            return (Criteria) this;
        }

        public Criteria andReqReasonIsNotNull() {
            addCriterion("REQ_REASON is not null");
            return (Criteria) this;
        }

        public Criteria andReqReasonEqualTo(String value) {
            addCriterion("REQ_REASON =", value, "reqReason");
            return (Criteria) this;
        }

        public Criteria andReqReasonNotEqualTo(String value) {
            addCriterion("REQ_REASON <>", value, "reqReason");
            return (Criteria) this;
        }

        public Criteria andReqReasonGreaterThan(String value) {
            addCriterion("REQ_REASON >", value, "reqReason");
            return (Criteria) this;
        }

        public Criteria andReqReasonGreaterThanOrEqualTo(String value) {
            addCriterion("REQ_REASON >=", value, "reqReason");
            return (Criteria) this;
        }

        public Criteria andReqReasonLessThan(String value) {
            addCriterion("REQ_REASON <", value, "reqReason");
            return (Criteria) this;
        }

        public Criteria andReqReasonLessThanOrEqualTo(String value) {
            addCriterion("REQ_REASON <=", value, "reqReason");
            return (Criteria) this;
        }

        public Criteria andReqReasonLike(String value) {
            addCriterion("REQ_REASON like", value, "reqReason");
            return (Criteria) this;
        }

        public Criteria andReqReasonNotLike(String value) {
            addCriterion("REQ_REASON not like", value, "reqReason");
            return (Criteria) this;
        }

        public Criteria andReqReasonIn(List<String> values) {
            addCriterion("REQ_REASON in", values, "reqReason");
            return (Criteria) this;
        }

        public Criteria andReqReasonNotIn(List<String> values) {
            addCriterion("REQ_REASON not in", values, "reqReason");
            return (Criteria) this;
        }

        public Criteria andReqReasonBetween(String value1, String value2) {
            addCriterion("REQ_REASON between", value1, value2, "reqReason");
            return (Criteria) this;
        }

        public Criteria andReqReasonNotBetween(String value1, String value2) {
            addCriterion("REQ_REASON not between", value1, value2, "reqReason");
            return (Criteria) this;
        }

        public Criteria andReviewsStatIsNull() {
            addCriterion("REVIEWS_STAT is null");
            return (Criteria) this;
        }

        public Criteria andReviewsStatIsNotNull() {
            addCriterion("REVIEWS_STAT is not null");
            return (Criteria) this;
        }

        public Criteria andReviewsStatEqualTo(BigDecimal value) {
            addCriterion("REVIEWS_STAT =", value, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatNotEqualTo(BigDecimal value) {
            addCriterion("REVIEWS_STAT <>", value, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatGreaterThan(BigDecimal value) {
            addCriterion("REVIEWS_STAT >", value, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REVIEWS_STAT >=", value, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatLessThan(BigDecimal value) {
            addCriterion("REVIEWS_STAT <", value, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REVIEWS_STAT <=", value, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatIn(List<BigDecimal> values) {
            addCriterion("REVIEWS_STAT in", values, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatNotIn(List<BigDecimal> values) {
            addCriterion("REVIEWS_STAT not in", values, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REVIEWS_STAT between", value1, value2, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsStatNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REVIEWS_STAT not between", value1, value2, "reviewsStat");
            return (Criteria) this;
        }

        public Criteria andReviewsDateIsNull() {
            addCriterion("REVIEWS_DATE is null");
            return (Criteria) this;
        }

        public Criteria andReviewsDateIsNotNull() {
            addCriterion("REVIEWS_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andReviewsDateEqualTo(Date value) {
            addCriterion("REVIEWS_DATE =", value, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateNotEqualTo(Date value) {
            addCriterion("REVIEWS_DATE <>", value, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateGreaterThan(Date value) {
            addCriterion("REVIEWS_DATE >", value, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateGreaterThanOrEqualTo(Date value) {
            addCriterion("REVIEWS_DATE >=", value, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateLessThan(Date value) {
            addCriterion("REVIEWS_DATE <", value, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateLessThanOrEqualTo(Date value) {
            addCriterion("REVIEWS_DATE <=", value, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateIn(List<Date> values) {
            addCriterion("REVIEWS_DATE in", values, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateNotIn(List<Date> values) {
            addCriterion("REVIEWS_DATE not in", values, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateBetween(Date value1, Date value2) {
            addCriterion("REVIEWS_DATE between", value1, value2, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsDateNotBetween(Date value1, Date value2) {
            addCriterion("REVIEWS_DATE not between", value1, value2, "reviewsDate");
            return (Criteria) this;
        }

        public Criteria andReviewsUserIsNull() {
            addCriterion("REVIEWS_USER is null");
            return (Criteria) this;
        }

        public Criteria andReviewsUserIsNotNull() {
            addCriterion("REVIEWS_USER is not null");
            return (Criteria) this;
        }

        public Criteria andReviewsUserEqualTo(String value) {
            addCriterion("REVIEWS_USER =", value, "reviewsUser");
            return (Criteria) this;
        }

        public Criteria andReviewsUserNotEqualTo(String value) {
            addCriterion("REVIEWS_USER <>", value, "reviewsUser");
            return (Criteria) this;
        }

        public Criteria andReviewsUserGreaterThan(String value) {
            addCriterion("REVIEWS_USER >", value, "reviewsUser");
            return (Criteria) this;
        }

        public Criteria andReviewsUserGreaterThanOrEqualTo(String value) {
            addCriterion("REVIEWS_USER >=", value, "reviewsUser");
            return (Criteria) this;
        }

        public Criteria andReviewsUserLessThan(String value) {
            addCriterion("REVIEWS_USER <", value, "reviewsUser");
            return (Criteria) this;
        }

        public Criteria andReviewsUserLessThanOrEqualTo(String value) {
            addCriterion("REVIEWS_USER <=", value, "reviewsUser");
            return (Criteria) this;
        }

        public Criteria andReviewsUserLike(String value) {
            addCriterion("REVIEWS_USER like", value, "reviewsUser");
            return (Criteria) this;
        }

        public Criteria andReviewsUserNotLike(String value) {
            addCriterion("REVIEWS_USER not like", value, "reviewsUser");
            return (Criteria) this;
        }

        public Criteria andReviewsUserIn(List<String> values) {
            addCriterion("REVIEWS_USER in", values, "reviewsUser");
            return (Criteria) this;
        }

        public Criteria andReviewsUserNotIn(List<String> values) {
            addCriterion("REVIEWS_USER not in", values, "reviewsUser");
            return (Criteria) this;
        }

        public Criteria andReviewsUserBetween(String value1, String value2) {
            addCriterion("REVIEWS_USER between", value1, value2, "reviewsUser");
            return (Criteria) this;
        }

        public Criteria andReviewsUserNotBetween(String value1, String value2) {
            addCriterion("REVIEWS_USER not between", value1, value2, "reviewsUser");
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