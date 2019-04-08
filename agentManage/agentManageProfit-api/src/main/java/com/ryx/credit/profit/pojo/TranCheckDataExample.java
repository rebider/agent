package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TranCheckDataExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public TranCheckDataExample() {
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

        public Criteria andPlatformTypeIsNull() {
            addCriterion("PLATFORM_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeIsNotNull() {
            addCriterion("PLATFORM_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeEqualTo(String value) {
            addCriterion("PLATFORM_TYPE =", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotEqualTo(String value) {
            addCriterion("PLATFORM_TYPE <>", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeGreaterThan(String value) {
            addCriterion("PLATFORM_TYPE >", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PLATFORM_TYPE >=", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLessThan(String value) {
            addCriterion("PLATFORM_TYPE <", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLessThanOrEqualTo(String value) {
            addCriterion("PLATFORM_TYPE <=", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLike(String value) {
            addCriterion("PLATFORM_TYPE like", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotLike(String value) {
            addCriterion("PLATFORM_TYPE not like", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeIn(List<String> values) {
            addCriterion("PLATFORM_TYPE in", values, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotIn(List<String> values) {
            addCriterion("PLATFORM_TYPE not in", values, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeBetween(String value1, String value2) {
            addCriterion("PLATFORM_TYPE between", value1, value2, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotBetween(String value1, String value2) {
            addCriterion("PLATFORM_TYPE not between", value1, value2, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformNumIsNull() {
            addCriterion("PLATFORM_NUM is null");
            return (Criteria) this;
        }

        public Criteria andPlatformNumIsNotNull() {
            addCriterion("PLATFORM_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformNumEqualTo(String value) {
            addCriterion("PLATFORM_NUM =", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumNotEqualTo(String value) {
            addCriterion("PLATFORM_NUM <>", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumGreaterThan(String value) {
            addCriterion("PLATFORM_NUM >", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumGreaterThanOrEqualTo(String value) {
            addCriterion("PLATFORM_NUM >=", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumLessThan(String value) {
            addCriterion("PLATFORM_NUM <", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumLessThanOrEqualTo(String value) {
            addCriterion("PLATFORM_NUM <=", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumLike(String value) {
            addCriterion("PLATFORM_NUM like", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumNotLike(String value) {
            addCriterion("PLATFORM_NUM not like", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumIn(List<String> values) {
            addCriterion("PLATFORM_NUM in", values, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumNotIn(List<String> values) {
            addCriterion("PLATFORM_NUM not in", values, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumBetween(String value1, String value2) {
            addCriterion("PLATFORM_NUM between", value1, value2, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumNotBetween(String value1, String value2) {
            addCriterion("PLATFORM_NUM not between", value1, value2, "platformNum");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtIsNull() {
            addCriterion("TECHNOLOGY_AMT is null");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtIsNotNull() {
            addCriterion("TECHNOLOGY_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtEqualTo(BigDecimal value) {
            addCriterion("TECHNOLOGY_AMT =", value, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtNotEqualTo(BigDecimal value) {
            addCriterion("TECHNOLOGY_AMT <>", value, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtGreaterThan(BigDecimal value) {
            addCriterion("TECHNOLOGY_AMT >", value, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TECHNOLOGY_AMT >=", value, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtLessThan(BigDecimal value) {
            addCriterion("TECHNOLOGY_AMT <", value, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TECHNOLOGY_AMT <=", value, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtIn(List<BigDecimal> values) {
            addCriterion("TECHNOLOGY_AMT in", values, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtNotIn(List<BigDecimal> values) {
            addCriterion("TECHNOLOGY_AMT not in", values, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TECHNOLOGY_AMT between", value1, value2, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TECHNOLOGY_AMT not between", value1, value2, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeIsNull() {
            addCriterion("TECHNOLOGY_FEE is null");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeIsNotNull() {
            addCriterion("TECHNOLOGY_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeEqualTo(BigDecimal value) {
            addCriterion("TECHNOLOGY_FEE =", value, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeNotEqualTo(BigDecimal value) {
            addCriterion("TECHNOLOGY_FEE <>", value, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeGreaterThan(BigDecimal value) {
            addCriterion("TECHNOLOGY_FEE >", value, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TECHNOLOGY_FEE >=", value, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeLessThan(BigDecimal value) {
            addCriterion("TECHNOLOGY_FEE <", value, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TECHNOLOGY_FEE <=", value, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeIn(List<BigDecimal> values) {
            addCriterion("TECHNOLOGY_FEE in", values, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeNotIn(List<BigDecimal> values) {
            addCriterion("TECHNOLOGY_FEE not in", values, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TECHNOLOGY_FEE between", value1, value2, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TECHNOLOGY_FEE not between", value1, value2, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andClearAmtIsNull() {
            addCriterion("CLEAR_AMT is null");
            return (Criteria) this;
        }

        public Criteria andClearAmtIsNotNull() {
            addCriterion("CLEAR_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andClearAmtEqualTo(BigDecimal value) {
            addCriterion("CLEAR_AMT =", value, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtNotEqualTo(BigDecimal value) {
            addCriterion("CLEAR_AMT <>", value, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtGreaterThan(BigDecimal value) {
            addCriterion("CLEAR_AMT >", value, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLEAR_AMT >=", value, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtLessThan(BigDecimal value) {
            addCriterion("CLEAR_AMT <", value, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLEAR_AMT <=", value, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtIn(List<BigDecimal> values) {
            addCriterion("CLEAR_AMT in", values, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtNotIn(List<BigDecimal> values) {
            addCriterion("CLEAR_AMT not in", values, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLEAR_AMT between", value1, value2, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLEAR_AMT not between", value1, value2, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearFeeIsNull() {
            addCriterion("CLEAR_FEE is null");
            return (Criteria) this;
        }

        public Criteria andClearFeeIsNotNull() {
            addCriterion("CLEAR_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andClearFeeEqualTo(BigDecimal value) {
            addCriterion("CLEAR_FEE =", value, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeNotEqualTo(BigDecimal value) {
            addCriterion("CLEAR_FEE <>", value, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeGreaterThan(BigDecimal value) {
            addCriterion("CLEAR_FEE >", value, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLEAR_FEE >=", value, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeLessThan(BigDecimal value) {
            addCriterion("CLEAR_FEE <", value, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLEAR_FEE <=", value, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeIn(List<BigDecimal> values) {
            addCriterion("CLEAR_FEE in", values, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeNotIn(List<BigDecimal> values) {
            addCriterion("CLEAR_FEE not in", values, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLEAR_FEE between", value1, value2, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLEAR_FEE not between", value1, value2, "clearFee");
            return (Criteria) this;
        }

        public Criteria andSearchTimeIsNull() {
            addCriterion("SEARCH_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSearchTimeIsNotNull() {
            addCriterion("SEARCH_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSearchTimeEqualTo(String value) {
            addCriterion("SEARCH_TIME =", value, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeNotEqualTo(String value) {
            addCriterion("SEARCH_TIME <>", value, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeGreaterThan(String value) {
            addCriterion("SEARCH_TIME >", value, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeGreaterThanOrEqualTo(String value) {
            addCriterion("SEARCH_TIME >=", value, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeLessThan(String value) {
            addCriterion("SEARCH_TIME <", value, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeLessThanOrEqualTo(String value) {
            addCriterion("SEARCH_TIME <=", value, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeLike(String value) {
            addCriterion("SEARCH_TIME like", value, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeNotLike(String value) {
            addCriterion("SEARCH_TIME not like", value, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeIn(List<String> values) {
            addCriterion("SEARCH_TIME in", values, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeNotIn(List<String> values) {
            addCriterion("SEARCH_TIME not in", values, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeBetween(String value1, String value2) {
            addCriterion("SEARCH_TIME between", value1, value2, "searchTime");
            return (Criteria) this;
        }

        public Criteria andSearchTimeNotBetween(String value1, String value2) {
            addCriterion("SEARCH_TIME not between", value1, value2, "searchTime");
            return (Criteria) this;
        }

        public Criteria andPlatformIdIsNull() {
            addCriterion("PLATFORM_ID is null");
            return (Criteria) this;
        }

        public Criteria andPlatformIdIsNotNull() {
            addCriterion("PLATFORM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformIdEqualTo(BigDecimal value) {
            addCriterion("PLATFORM_ID =", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotEqualTo(BigDecimal value) {
            addCriterion("PLATFORM_ID <>", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdGreaterThan(BigDecimal value) {
            addCriterion("PLATFORM_ID >", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PLATFORM_ID >=", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdLessThan(BigDecimal value) {
            addCriterion("PLATFORM_ID <", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PLATFORM_ID <=", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdIn(List<BigDecimal> values) {
            addCriterion("PLATFORM_ID in", values, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotIn(List<BigDecimal> values) {
            addCriterion("PLATFORM_ID not in", values, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLATFORM_ID between", value1, value2, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLATFORM_ID not between", value1, value2, "platformId");
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