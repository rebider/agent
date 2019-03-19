package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OPdSumExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OPdSumExample() {
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

        public Criteria andAgentidIsNull() {
            addCriterion("AGENTID is null");
            return (Criteria) this;
        }

        public Criteria andAgentidIsNotNull() {
            addCriterion("AGENTID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentidEqualTo(String value) {
            addCriterion("AGENTID =", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidNotEqualTo(String value) {
            addCriterion("AGENTID <>", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidGreaterThan(String value) {
            addCriterion("AGENTID >", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidGreaterThanOrEqualTo(String value) {
            addCriterion("AGENTID >=", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidLessThan(String value) {
            addCriterion("AGENTID <", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidLessThanOrEqualTo(String value) {
            addCriterion("AGENTID <=", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidLike(String value) {
            addCriterion("AGENTID like", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidNotLike(String value) {
            addCriterion("AGENTID not like", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidIn(List<String> values) {
            addCriterion("AGENTID in", values, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidNotIn(List<String> values) {
            addCriterion("AGENTID not in", values, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidBetween(String value1, String value2) {
            addCriterion("AGENTID between", value1, value2, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidNotBetween(String value1, String value2) {
            addCriterion("AGENTID not between", value1, value2, "agentid");
            return (Criteria) this;
        }

        public Criteria andPlatformIsNull() {
            addCriterion("PLATFORM is null");
            return (Criteria) this;
        }

        public Criteria andPlatformIsNotNull() {
            addCriterion("PLATFORM is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformEqualTo(String value) {
            addCriterion("PLATFORM =", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotEqualTo(String value) {
            addCriterion("PLATFORM <>", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformGreaterThan(String value) {
            addCriterion("PLATFORM >", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformGreaterThanOrEqualTo(String value) {
            addCriterion("PLATFORM >=", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLessThan(String value) {
            addCriterion("PLATFORM <", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLessThanOrEqualTo(String value) {
            addCriterion("PLATFORM <=", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLike(String value) {
            addCriterion("PLATFORM like", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotLike(String value) {
            addCriterion("PLATFORM not like", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformIn(List<String> values) {
            addCriterion("PLATFORM in", values, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotIn(List<String> values) {
            addCriterion("PLATFORM not in", values, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformBetween(String value1, String value2) {
            addCriterion("PLATFORM between", value1, value2, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotBetween(String value1, String value2) {
            addCriterion("PLATFORM not between", value1, value2, "platform");
            return (Criteria) this;
        }

        public Criteria andSumMouthIsNull() {
            addCriterion("SUM_MOUTH is null");
            return (Criteria) this;
        }

        public Criteria andSumMouthIsNotNull() {
            addCriterion("SUM_MOUTH is not null");
            return (Criteria) this;
        }

        public Criteria andSumMouthEqualTo(String value) {
            addCriterion("SUM_MOUTH =", value, "sumMouth");
            return (Criteria) this;
        }

        public Criteria andSumMouthNotEqualTo(String value) {
            addCriterion("SUM_MOUTH <>", value, "sumMouth");
            return (Criteria) this;
        }

        public Criteria andSumMouthGreaterThan(String value) {
            addCriterion("SUM_MOUTH >", value, "sumMouth");
            return (Criteria) this;
        }

        public Criteria andSumMouthGreaterThanOrEqualTo(String value) {
            addCriterion("SUM_MOUTH >=", value, "sumMouth");
            return (Criteria) this;
        }

        public Criteria andSumMouthLessThan(String value) {
            addCriterion("SUM_MOUTH <", value, "sumMouth");
            return (Criteria) this;
        }

        public Criteria andSumMouthLessThanOrEqualTo(String value) {
            addCriterion("SUM_MOUTH <=", value, "sumMouth");
            return (Criteria) this;
        }

        public Criteria andSumMouthLike(String value) {
            addCriterion("SUM_MOUTH like", value, "sumMouth");
            return (Criteria) this;
        }

        public Criteria andSumMouthNotLike(String value) {
            addCriterion("SUM_MOUTH not like", value, "sumMouth");
            return (Criteria) this;
        }

        public Criteria andSumMouthIn(List<String> values) {
            addCriterion("SUM_MOUTH in", values, "sumMouth");
            return (Criteria) this;
        }

        public Criteria andSumMouthNotIn(List<String> values) {
            addCriterion("SUM_MOUTH not in", values, "sumMouth");
            return (Criteria) this;
        }

        public Criteria andSumMouthBetween(String value1, String value2) {
            addCriterion("SUM_MOUTH between", value1, value2, "sumMouth");
            return (Criteria) this;
        }

        public Criteria andSumMouthNotBetween(String value1, String value2) {
            addCriterion("SUM_MOUTH not between", value1, value2, "sumMouth");
            return (Criteria) this;
        }

        public Criteria andRealAmountIsNull() {
            addCriterion("REAL_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andRealAmountIsNotNull() {
            addCriterion("REAL_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andRealAmountEqualTo(BigDecimal value) {
            addCriterion("REAL_AMOUNT =", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountNotEqualTo(BigDecimal value) {
            addCriterion("REAL_AMOUNT <>", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountGreaterThan(BigDecimal value) {
            addCriterion("REAL_AMOUNT >", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_AMOUNT >=", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountLessThan(BigDecimal value) {
            addCriterion("REAL_AMOUNT <", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_AMOUNT <=", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountIn(List<BigDecimal> values) {
            addCriterion("REAL_AMOUNT in", values, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountNotIn(List<BigDecimal> values) {
            addCriterion("REAL_AMOUNT not in", values, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_AMOUNT between", value1, value2, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_AMOUNT not between", value1, value2, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealDatetimeIsNull() {
            addCriterion("REAL_DATETIME is null");
            return (Criteria) this;
        }

        public Criteria andRealDatetimeIsNotNull() {
            addCriterion("REAL_DATETIME is not null");
            return (Criteria) this;
        }

        public Criteria andRealDatetimeEqualTo(Date value) {
            addCriterion("REAL_DATETIME =", value, "realDatetime");
            return (Criteria) this;
        }

        public Criteria andRealDatetimeNotEqualTo(Date value) {
            addCriterion("REAL_DATETIME <>", value, "realDatetime");
            return (Criteria) this;
        }

        public Criteria andRealDatetimeGreaterThan(Date value) {
            addCriterion("REAL_DATETIME >", value, "realDatetime");
            return (Criteria) this;
        }

        public Criteria andRealDatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("REAL_DATETIME >=", value, "realDatetime");
            return (Criteria) this;
        }

        public Criteria andRealDatetimeLessThan(Date value) {
            addCriterion("REAL_DATETIME <", value, "realDatetime");
            return (Criteria) this;
        }

        public Criteria andRealDatetimeLessThanOrEqualTo(Date value) {
            addCriterion("REAL_DATETIME <=", value, "realDatetime");
            return (Criteria) this;
        }

        public Criteria andRealDatetimeIn(List<Date> values) {
            addCriterion("REAL_DATETIME in", values, "realDatetime");
            return (Criteria) this;
        }

        public Criteria andRealDatetimeNotIn(List<Date> values) {
            addCriterion("REAL_DATETIME not in", values, "realDatetime");
            return (Criteria) this;
        }

        public Criteria andRealDatetimeBetween(Date value1, Date value2) {
            addCriterion("REAL_DATETIME between", value1, value2, "realDatetime");
            return (Criteria) this;
        }

        public Criteria andRealDatetimeNotBetween(Date value1, Date value2) {
            addCriterion("REAL_DATETIME not between", value1, value2, "realDatetime");
            return (Criteria) this;
        }

        public Criteria andRealUserIsNull() {
            addCriterion("REAL_USER is null");
            return (Criteria) this;
        }

        public Criteria andRealUserIsNotNull() {
            addCriterion("REAL_USER is not null");
            return (Criteria) this;
        }

        public Criteria andRealUserEqualTo(String value) {
            addCriterion("REAL_USER =", value, "realUser");
            return (Criteria) this;
        }

        public Criteria andRealUserNotEqualTo(String value) {
            addCriterion("REAL_USER <>", value, "realUser");
            return (Criteria) this;
        }

        public Criteria andRealUserGreaterThan(String value) {
            addCriterion("REAL_USER >", value, "realUser");
            return (Criteria) this;
        }

        public Criteria andRealUserGreaterThanOrEqualTo(String value) {
            addCriterion("REAL_USER >=", value, "realUser");
            return (Criteria) this;
        }

        public Criteria andRealUserLessThan(String value) {
            addCriterion("REAL_USER <", value, "realUser");
            return (Criteria) this;
        }

        public Criteria andRealUserLessThanOrEqualTo(String value) {
            addCriterion("REAL_USER <=", value, "realUser");
            return (Criteria) this;
        }

        public Criteria andRealUserLike(String value) {
            addCriterion("REAL_USER like", value, "realUser");
            return (Criteria) this;
        }

        public Criteria andRealUserNotLike(String value) {
            addCriterion("REAL_USER not like", value, "realUser");
            return (Criteria) this;
        }

        public Criteria andRealUserIn(List<String> values) {
            addCriterion("REAL_USER in", values, "realUser");
            return (Criteria) this;
        }

        public Criteria andRealUserNotIn(List<String> values) {
            addCriterion("REAL_USER not in", values, "realUser");
            return (Criteria) this;
        }

        public Criteria andRealUserBetween(String value1, String value2) {
            addCriterion("REAL_USER between", value1, value2, "realUser");
            return (Criteria) this;
        }

        public Criteria andRealUserNotBetween(String value1, String value2) {
            addCriterion("REAL_USER not between", value1, value2, "realUser");
            return (Criteria) this;
        }

        public Criteria andPaySrcIsNull() {
            addCriterion("PAY_SRC is null");
            return (Criteria) this;
        }

        public Criteria andPaySrcIsNotNull() {
            addCriterion("PAY_SRC is not null");
            return (Criteria) this;
        }

        public Criteria andPaySrcEqualTo(String value) {
            addCriterion("PAY_SRC =", value, "paySrc");
            return (Criteria) this;
        }

        public Criteria andPaySrcNotEqualTo(String value) {
            addCriterion("PAY_SRC <>", value, "paySrc");
            return (Criteria) this;
        }

        public Criteria andPaySrcGreaterThan(String value) {
            addCriterion("PAY_SRC >", value, "paySrc");
            return (Criteria) this;
        }

        public Criteria andPaySrcGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_SRC >=", value, "paySrc");
            return (Criteria) this;
        }

        public Criteria andPaySrcLessThan(String value) {
            addCriterion("PAY_SRC <", value, "paySrc");
            return (Criteria) this;
        }

        public Criteria andPaySrcLessThanOrEqualTo(String value) {
            addCriterion("PAY_SRC <=", value, "paySrc");
            return (Criteria) this;
        }

        public Criteria andPaySrcLike(String value) {
            addCriterion("PAY_SRC like", value, "paySrc");
            return (Criteria) this;
        }

        public Criteria andPaySrcNotLike(String value) {
            addCriterion("PAY_SRC not like", value, "paySrc");
            return (Criteria) this;
        }

        public Criteria andPaySrcIn(List<String> values) {
            addCriterion("PAY_SRC in", values, "paySrc");
            return (Criteria) this;
        }

        public Criteria andPaySrcNotIn(List<String> values) {
            addCriterion("PAY_SRC not in", values, "paySrc");
            return (Criteria) this;
        }

        public Criteria andPaySrcBetween(String value1, String value2) {
            addCriterion("PAY_SRC between", value1, value2, "paySrc");
            return (Criteria) this;
        }

        public Criteria andPaySrcNotBetween(String value1, String value2) {
            addCriterion("PAY_SRC not between", value1, value2, "paySrc");
            return (Criteria) this;
        }

        public Criteria andPaySrcTypeIsNull() {
            addCriterion("PAY_SRC_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPaySrcTypeIsNotNull() {
            addCriterion("PAY_SRC_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPaySrcTypeEqualTo(String value) {
            addCriterion("PAY_SRC_TYPE =", value, "paySrcType");
            return (Criteria) this;
        }

        public Criteria andPaySrcTypeNotEqualTo(String value) {
            addCriterion("PAY_SRC_TYPE <>", value, "paySrcType");
            return (Criteria) this;
        }

        public Criteria andPaySrcTypeGreaterThan(String value) {
            addCriterion("PAY_SRC_TYPE >", value, "paySrcType");
            return (Criteria) this;
        }

        public Criteria andPaySrcTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_SRC_TYPE >=", value, "paySrcType");
            return (Criteria) this;
        }

        public Criteria andPaySrcTypeLessThan(String value) {
            addCriterion("PAY_SRC_TYPE <", value, "paySrcType");
            return (Criteria) this;
        }

        public Criteria andPaySrcTypeLessThanOrEqualTo(String value) {
            addCriterion("PAY_SRC_TYPE <=", value, "paySrcType");
            return (Criteria) this;
        }

        public Criteria andPaySrcTypeLike(String value) {
            addCriterion("PAY_SRC_TYPE like", value, "paySrcType");
            return (Criteria) this;
        }

        public Criteria andPaySrcTypeNotLike(String value) {
            addCriterion("PAY_SRC_TYPE not like", value, "paySrcType");
            return (Criteria) this;
        }

        public Criteria andPaySrcTypeIn(List<String> values) {
            addCriterion("PAY_SRC_TYPE in", values, "paySrcType");
            return (Criteria) this;
        }

        public Criteria andPaySrcTypeNotIn(List<String> values) {
            addCriterion("PAY_SRC_TYPE not in", values, "paySrcType");
            return (Criteria) this;
        }

        public Criteria andPaySrcTypeBetween(String value1, String value2) {
            addCriterion("PAY_SRC_TYPE between", value1, value2, "paySrcType");
            return (Criteria) this;
        }

        public Criteria andPaySrcTypeNotBetween(String value1, String value2) {
            addCriterion("PAY_SRC_TYPE not between", value1, value2, "paySrcType");
            return (Criteria) this;
        }

        public Criteria andSumStatusIsNull() {
            addCriterion("SUM_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andSumStatusIsNotNull() {
            addCriterion("SUM_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andSumStatusEqualTo(String value) {
            addCriterion("SUM_STATUS =", value, "sumStatus");
            return (Criteria) this;
        }

        public Criteria andSumStatusNotEqualTo(String value) {
            addCriterion("SUM_STATUS <>", value, "sumStatus");
            return (Criteria) this;
        }

        public Criteria andSumStatusGreaterThan(String value) {
            addCriterion("SUM_STATUS >", value, "sumStatus");
            return (Criteria) this;
        }

        public Criteria andSumStatusGreaterThanOrEqualTo(String value) {
            addCriterion("SUM_STATUS >=", value, "sumStatus");
            return (Criteria) this;
        }

        public Criteria andSumStatusLessThan(String value) {
            addCriterion("SUM_STATUS <", value, "sumStatus");
            return (Criteria) this;
        }

        public Criteria andSumStatusLessThanOrEqualTo(String value) {
            addCriterion("SUM_STATUS <=", value, "sumStatus");
            return (Criteria) this;
        }

        public Criteria andSumStatusLike(String value) {
            addCriterion("SUM_STATUS like", value, "sumStatus");
            return (Criteria) this;
        }

        public Criteria andSumStatusNotLike(String value) {
            addCriterion("SUM_STATUS not like", value, "sumStatus");
            return (Criteria) this;
        }

        public Criteria andSumStatusIn(List<String> values) {
            addCriterion("SUM_STATUS in", values, "sumStatus");
            return (Criteria) this;
        }

        public Criteria andSumStatusNotIn(List<String> values) {
            addCriterion("SUM_STATUS not in", values, "sumStatus");
            return (Criteria) this;
        }

        public Criteria andSumStatusBetween(String value1, String value2) {
            addCriterion("SUM_STATUS between", value1, value2, "sumStatus");
            return (Criteria) this;
        }

        public Criteria andSumStatusNotBetween(String value1, String value2) {
            addCriterion("SUM_STATUS not between", value1, value2, "sumStatus");
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

        public Criteria andSumAmountIsNull() {
            addCriterion("SUM_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andSumAmountIsNotNull() {
            addCriterion("SUM_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andSumAmountEqualTo(BigDecimal value) {
            addCriterion("SUM_AMOUNT =", value, "sumAmount");
            return (Criteria) this;
        }

        public Criteria andSumAmountNotEqualTo(BigDecimal value) {
            addCriterion("SUM_AMOUNT <>", value, "sumAmount");
            return (Criteria) this;
        }

        public Criteria andSumAmountGreaterThan(BigDecimal value) {
            addCriterion("SUM_AMOUNT >", value, "sumAmount");
            return (Criteria) this;
        }

        public Criteria andSumAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUM_AMOUNT >=", value, "sumAmount");
            return (Criteria) this;
        }

        public Criteria andSumAmountLessThan(BigDecimal value) {
            addCriterion("SUM_AMOUNT <", value, "sumAmount");
            return (Criteria) this;
        }

        public Criteria andSumAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUM_AMOUNT <=", value, "sumAmount");
            return (Criteria) this;
        }

        public Criteria andSumAmountIn(List<BigDecimal> values) {
            addCriterion("SUM_AMOUNT in", values, "sumAmount");
            return (Criteria) this;
        }

        public Criteria andSumAmountNotIn(List<BigDecimal> values) {
            addCriterion("SUM_AMOUNT not in", values, "sumAmount");
            return (Criteria) this;
        }

        public Criteria andSumAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUM_AMOUNT between", value1, value2, "sumAmount");
            return (Criteria) this;
        }

        public Criteria andSumAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUM_AMOUNT not between", value1, value2, "sumAmount");
            return (Criteria) this;
        }

        public Criteria andRevIsNull() {
            addCriterion("REV is null");
            return (Criteria) this;
        }

        public Criteria andRevIsNotNull() {
            addCriterion("REV is not null");
            return (Criteria) this;
        }

        public Criteria andRevEqualTo(String value) {
            addCriterion("REV =", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevNotEqualTo(String value) {
            addCriterion("REV <>", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevGreaterThan(String value) {
            addCriterion("REV >", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevGreaterThanOrEqualTo(String value) {
            addCriterion("REV >=", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevLessThan(String value) {
            addCriterion("REV <", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevLessThanOrEqualTo(String value) {
            addCriterion("REV <=", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevLike(String value) {
            addCriterion("REV like", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevNotLike(String value) {
            addCriterion("REV not like", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevIn(List<String> values) {
            addCriterion("REV in", values, "rev");
            return (Criteria) this;
        }

        public Criteria andRevNotIn(List<String> values) {
            addCriterion("REV not in", values, "rev");
            return (Criteria) this;
        }

        public Criteria andRevBetween(String value1, String value2) {
            addCriterion("REV between", value1, value2, "rev");
            return (Criteria) this;
        }

        public Criteria andRevNotBetween(String value1, String value2) {
            addCriterion("REV not between", value1, value2, "rev");
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