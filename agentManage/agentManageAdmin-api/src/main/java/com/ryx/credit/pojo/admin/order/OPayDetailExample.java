package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OPayDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OPayDetailExample() {
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

        public Criteria andArrIdIsNull() {
            addCriterion("ARR_ID is null");
            return (Criteria) this;
        }

        public Criteria andArrIdIsNotNull() {
            addCriterion("ARR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andArrIdEqualTo(String value) {
            addCriterion("ARR_ID =", value, "arrId");
            return (Criteria) this;
        }

        public Criteria andArrIdNotEqualTo(String value) {
            addCriterion("ARR_ID <>", value, "arrId");
            return (Criteria) this;
        }

        public Criteria andArrIdGreaterThan(String value) {
            addCriterion("ARR_ID >", value, "arrId");
            return (Criteria) this;
        }

        public Criteria andArrIdGreaterThanOrEqualTo(String value) {
            addCriterion("ARR_ID >=", value, "arrId");
            return (Criteria) this;
        }

        public Criteria andArrIdLessThan(String value) {
            addCriterion("ARR_ID <", value, "arrId");
            return (Criteria) this;
        }

        public Criteria andArrIdLessThanOrEqualTo(String value) {
            addCriterion("ARR_ID <=", value, "arrId");
            return (Criteria) this;
        }

        public Criteria andArrIdLike(String value) {
            addCriterion("ARR_ID like", value, "arrId");
            return (Criteria) this;
        }

        public Criteria andArrIdNotLike(String value) {
            addCriterion("ARR_ID not like", value, "arrId");
            return (Criteria) this;
        }

        public Criteria andArrIdIn(List<String> values) {
            addCriterion("ARR_ID in", values, "arrId");
            return (Criteria) this;
        }

        public Criteria andArrIdNotIn(List<String> values) {
            addCriterion("ARR_ID not in", values, "arrId");
            return (Criteria) this;
        }

        public Criteria andArrIdBetween(String value1, String value2) {
            addCriterion("ARR_ID between", value1, value2, "arrId");
            return (Criteria) this;
        }

        public Criteria andArrIdNotBetween(String value1, String value2) {
            addCriterion("ARR_ID not between", value1, value2, "arrId");
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

        public Criteria andPayTypeIsNull() {
            addCriterion("PAY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            addCriterion("PAY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(String value) {
            addCriterion("PAY_TYPE =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(String value) {
            addCriterion("PAY_TYPE <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(String value) {
            addCriterion("PAY_TYPE >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_TYPE >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(String value) {
            addCriterion("PAY_TYPE <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(String value) {
            addCriterion("PAY_TYPE <=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLike(String value) {
            addCriterion("PAY_TYPE like", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotLike(String value) {
            addCriterion("PAY_TYPE not like", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(List<String> values) {
            addCriterion("PAY_TYPE in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(List<String> values) {
            addCriterion("PAY_TYPE not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(String value1, String value2) {
            addCriterion("PAY_TYPE between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(String value1, String value2) {
            addCriterion("PAY_TYPE not between", value1, value2, "payType");
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

        public Criteria andBusStatIsNull() {
            addCriterion("BUS_STAT is null");
            return (Criteria) this;
        }

        public Criteria andBusStatIsNotNull() {
            addCriterion("BUS_STAT is not null");
            return (Criteria) this;
        }

        public Criteria andBusStatEqualTo(BigDecimal value) {
            addCriterion("BUS_STAT =", value, "busStat");
            return (Criteria) this;
        }

        public Criteria andBusStatNotEqualTo(BigDecimal value) {
            addCriterion("BUS_STAT <>", value, "busStat");
            return (Criteria) this;
        }

        public Criteria andBusStatGreaterThan(BigDecimal value) {
            addCriterion("BUS_STAT >", value, "busStat");
            return (Criteria) this;
        }

        public Criteria andBusStatGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_STAT >=", value, "busStat");
            return (Criteria) this;
        }

        public Criteria andBusStatLessThan(BigDecimal value) {
            addCriterion("BUS_STAT <", value, "busStat");
            return (Criteria) this;
        }

        public Criteria andBusStatLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_STAT <=", value, "busStat");
            return (Criteria) this;
        }

        public Criteria andBusStatIn(List<BigDecimal> values) {
            addCriterion("BUS_STAT in", values, "busStat");
            return (Criteria) this;
        }

        public Criteria andBusStatNotIn(List<BigDecimal> values) {
            addCriterion("BUS_STAT not in", values, "busStat");
            return (Criteria) this;
        }

        public Criteria andBusStatBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_STAT between", value1, value2, "busStat");
            return (Criteria) this;
        }

        public Criteria andBusStatNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_STAT not between", value1, value2, "busStat");
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

        public Criteria andSuccessTmIsNull() {
            addCriterion("SUCCESS_TM is null");
            return (Criteria) this;
        }

        public Criteria andSuccessTmIsNotNull() {
            addCriterion("SUCCESS_TM is not null");
            return (Criteria) this;
        }

        public Criteria andSuccessTmEqualTo(Date value) {
            addCriterion("SUCCESS_TM =", value, "successTm");
            return (Criteria) this;
        }

        public Criteria andSuccessTmNotEqualTo(Date value) {
            addCriterion("SUCCESS_TM <>", value, "successTm");
            return (Criteria) this;
        }

        public Criteria andSuccessTmGreaterThan(Date value) {
            addCriterion("SUCCESS_TM >", value, "successTm");
            return (Criteria) this;
        }

        public Criteria andSuccessTmGreaterThanOrEqualTo(Date value) {
            addCriterion("SUCCESS_TM >=", value, "successTm");
            return (Criteria) this;
        }

        public Criteria andSuccessTmLessThan(Date value) {
            addCriterion("SUCCESS_TM <", value, "successTm");
            return (Criteria) this;
        }

        public Criteria andSuccessTmLessThanOrEqualTo(Date value) {
            addCriterion("SUCCESS_TM <=", value, "successTm");
            return (Criteria) this;
        }

        public Criteria andSuccessTmIn(List<Date> values) {
            addCriterion("SUCCESS_TM in", values, "successTm");
            return (Criteria) this;
        }

        public Criteria andSuccessTmNotIn(List<Date> values) {
            addCriterion("SUCCESS_TM not in", values, "successTm");
            return (Criteria) this;
        }

        public Criteria andSuccessTmBetween(Date value1, Date value2) {
            addCriterion("SUCCESS_TM between", value1, value2, "successTm");
            return (Criteria) this;
        }

        public Criteria andSuccessTmNotBetween(Date value1, Date value2) {
            addCriterion("SUCCESS_TM not between", value1, value2, "successTm");
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