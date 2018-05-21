package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssProtoColExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AssProtoColExample() {
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

        public Criteria andProtocolDesIsNull() {
            addCriterion("PROTOCOL_DES is null");
            return (Criteria) this;
        }

        public Criteria andProtocolDesIsNotNull() {
            addCriterion("PROTOCOL_DES is not null");
            return (Criteria) this;
        }

        public Criteria andProtocolDesEqualTo(String value) {
            addCriterion("PROTOCOL_DES =", value, "protocolDes");
            return (Criteria) this;
        }

        public Criteria andProtocolDesNotEqualTo(String value) {
            addCriterion("PROTOCOL_DES <>", value, "protocolDes");
            return (Criteria) this;
        }

        public Criteria andProtocolDesGreaterThan(String value) {
            addCriterion("PROTOCOL_DES >", value, "protocolDes");
            return (Criteria) this;
        }

        public Criteria andProtocolDesGreaterThanOrEqualTo(String value) {
            addCriterion("PROTOCOL_DES >=", value, "protocolDes");
            return (Criteria) this;
        }

        public Criteria andProtocolDesLessThan(String value) {
            addCriterion("PROTOCOL_DES <", value, "protocolDes");
            return (Criteria) this;
        }

        public Criteria andProtocolDesLessThanOrEqualTo(String value) {
            addCriterion("PROTOCOL_DES <=", value, "protocolDes");
            return (Criteria) this;
        }

        public Criteria andProtocolDesLike(String value) {
            addCriterion("PROTOCOL_DES like", value, "protocolDes");
            return (Criteria) this;
        }

        public Criteria andProtocolDesNotLike(String value) {
            addCriterion("PROTOCOL_DES not like", value, "protocolDes");
            return (Criteria) this;
        }

        public Criteria andProtocolDesIn(List<String> values) {
            addCriterion("PROTOCOL_DES in", values, "protocolDes");
            return (Criteria) this;
        }

        public Criteria andProtocolDesNotIn(List<String> values) {
            addCriterion("PROTOCOL_DES not in", values, "protocolDes");
            return (Criteria) this;
        }

        public Criteria andProtocolDesBetween(String value1, String value2) {
            addCriterion("PROTOCOL_DES between", value1, value2, "protocolDes");
            return (Criteria) this;
        }

        public Criteria andProtocolDesNotBetween(String value1, String value2) {
            addCriterion("PROTOCOL_DES not between", value1, value2, "protocolDes");
            return (Criteria) this;
        }

        public Criteria andProtocolRuleIsNull() {
            addCriterion("PROTOCOL_RULE is null");
            return (Criteria) this;
        }

        public Criteria andProtocolRuleIsNotNull() {
            addCriterion("PROTOCOL_RULE is not null");
            return (Criteria) this;
        }

        public Criteria andProtocolRuleEqualTo(String value) {
            addCriterion("PROTOCOL_RULE =", value, "protocolRule");
            return (Criteria) this;
        }

        public Criteria andProtocolRuleNotEqualTo(String value) {
            addCriterion("PROTOCOL_RULE <>", value, "protocolRule");
            return (Criteria) this;
        }

        public Criteria andProtocolRuleGreaterThan(String value) {
            addCriterion("PROTOCOL_RULE >", value, "protocolRule");
            return (Criteria) this;
        }

        public Criteria andProtocolRuleGreaterThanOrEqualTo(String value) {
            addCriterion("PROTOCOL_RULE >=", value, "protocolRule");
            return (Criteria) this;
        }

        public Criteria andProtocolRuleLessThan(String value) {
            addCriterion("PROTOCOL_RULE <", value, "protocolRule");
            return (Criteria) this;
        }

        public Criteria andProtocolRuleLessThanOrEqualTo(String value) {
            addCriterion("PROTOCOL_RULE <=", value, "protocolRule");
            return (Criteria) this;
        }

        public Criteria andProtocolRuleLike(String value) {
            addCriterion("PROTOCOL_RULE like", value, "protocolRule");
            return (Criteria) this;
        }

        public Criteria andProtocolRuleNotLike(String value) {
            addCriterion("PROTOCOL_RULE not like", value, "protocolRule");
            return (Criteria) this;
        }

        public Criteria andProtocolRuleIn(List<String> values) {
            addCriterion("PROTOCOL_RULE in", values, "protocolRule");
            return (Criteria) this;
        }

        public Criteria andProtocolRuleNotIn(List<String> values) {
            addCriterion("PROTOCOL_RULE not in", values, "protocolRule");
            return (Criteria) this;
        }

        public Criteria andProtocolRuleBetween(String value1, String value2) {
            addCriterion("PROTOCOL_RULE between", value1, value2, "protocolRule");
            return (Criteria) this;
        }

        public Criteria andProtocolRuleNotBetween(String value1, String value2) {
            addCriterion("PROTOCOL_RULE not between", value1, value2, "protocolRule");
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

        public Criteria andCStartIsNull() {
            addCriterion("C_START is null");
            return (Criteria) this;
        }

        public Criteria andCStartIsNotNull() {
            addCriterion("C_START is not null");
            return (Criteria) this;
        }

        public Criteria andCStartEqualTo(Date value) {
            addCriterion("C_START =", value, "cStart");
            return (Criteria) this;
        }

        public Criteria andCStartNotEqualTo(Date value) {
            addCriterion("C_START <>", value, "cStart");
            return (Criteria) this;
        }

        public Criteria andCStartGreaterThan(Date value) {
            addCriterion("C_START >", value, "cStart");
            return (Criteria) this;
        }

        public Criteria andCStartGreaterThanOrEqualTo(Date value) {
            addCriterion("C_START >=", value, "cStart");
            return (Criteria) this;
        }

        public Criteria andCStartLessThan(Date value) {
            addCriterion("C_START <", value, "cStart");
            return (Criteria) this;
        }

        public Criteria andCStartLessThanOrEqualTo(Date value) {
            addCriterion("C_START <=", value, "cStart");
            return (Criteria) this;
        }

        public Criteria andCStartIn(List<Date> values) {
            addCriterion("C_START in", values, "cStart");
            return (Criteria) this;
        }

        public Criteria andCStartNotIn(List<Date> values) {
            addCriterion("C_START not in", values, "cStart");
            return (Criteria) this;
        }

        public Criteria andCStartBetween(Date value1, Date value2) {
            addCriterion("C_START between", value1, value2, "cStart");
            return (Criteria) this;
        }

        public Criteria andCStartNotBetween(Date value1, Date value2) {
            addCriterion("C_START not between", value1, value2, "cStart");
            return (Criteria) this;
        }

        public Criteria andCEndIsNull() {
            addCriterion("C_END is null");
            return (Criteria) this;
        }

        public Criteria andCEndIsNotNull() {
            addCriterion("C_END is not null");
            return (Criteria) this;
        }

        public Criteria andCEndEqualTo(Date value) {
            addCriterion("C_END =", value, "cEnd");
            return (Criteria) this;
        }

        public Criteria andCEndNotEqualTo(Date value) {
            addCriterion("C_END <>", value, "cEnd");
            return (Criteria) this;
        }

        public Criteria andCEndGreaterThan(Date value) {
            addCriterion("C_END >", value, "cEnd");
            return (Criteria) this;
        }

        public Criteria andCEndGreaterThanOrEqualTo(Date value) {
            addCriterion("C_END >=", value, "cEnd");
            return (Criteria) this;
        }

        public Criteria andCEndLessThan(Date value) {
            addCriterion("C_END <", value, "cEnd");
            return (Criteria) this;
        }

        public Criteria andCEndLessThanOrEqualTo(Date value) {
            addCriterion("C_END <=", value, "cEnd");
            return (Criteria) this;
        }

        public Criteria andCEndIn(List<Date> values) {
            addCriterion("C_END in", values, "cEnd");
            return (Criteria) this;
        }

        public Criteria andCEndNotIn(List<Date> values) {
            addCriterion("C_END not in", values, "cEnd");
            return (Criteria) this;
        }

        public Criteria andCEndBetween(Date value1, Date value2) {
            addCriterion("C_END between", value1, value2, "cEnd");
            return (Criteria) this;
        }

        public Criteria andCEndNotBetween(Date value1, Date value2) {
            addCriterion("C_END not between", value1, value2, "cEnd");
            return (Criteria) this;
        }

        public Criteria andCSortIsNull() {
            addCriterion("C_SORT is null");
            return (Criteria) this;
        }

        public Criteria andCSortIsNotNull() {
            addCriterion("C_SORT is not null");
            return (Criteria) this;
        }

        public Criteria andCSortEqualTo(BigDecimal value) {
            addCriterion("C_SORT =", value, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortNotEqualTo(BigDecimal value) {
            addCriterion("C_SORT <>", value, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortGreaterThan(BigDecimal value) {
            addCriterion("C_SORT >", value, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("C_SORT >=", value, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortLessThan(BigDecimal value) {
            addCriterion("C_SORT <", value, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortLessThanOrEqualTo(BigDecimal value) {
            addCriterion("C_SORT <=", value, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortIn(List<BigDecimal> values) {
            addCriterion("C_SORT in", values, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortNotIn(List<BigDecimal> values) {
            addCriterion("C_SORT not in", values, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_SORT between", value1, value2, "cSort");
            return (Criteria) this;
        }

        public Criteria andCSortNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_SORT not between", value1, value2, "cSort");
            return (Criteria) this;
        }

        public Criteria andProtocolStatusIsNull() {
            addCriterion("PROTOCOL_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andProtocolStatusIsNotNull() {
            addCriterion("PROTOCOL_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andProtocolStatusEqualTo(BigDecimal value) {
            addCriterion("PROTOCOL_STATUS =", value, "protocolStatus");
            return (Criteria) this;
        }

        public Criteria andProtocolStatusNotEqualTo(BigDecimal value) {
            addCriterion("PROTOCOL_STATUS <>", value, "protocolStatus");
            return (Criteria) this;
        }

        public Criteria andProtocolStatusGreaterThan(BigDecimal value) {
            addCriterion("PROTOCOL_STATUS >", value, "protocolStatus");
            return (Criteria) this;
        }

        public Criteria andProtocolStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PROTOCOL_STATUS >=", value, "protocolStatus");
            return (Criteria) this;
        }

        public Criteria andProtocolStatusLessThan(BigDecimal value) {
            addCriterion("PROTOCOL_STATUS <", value, "protocolStatus");
            return (Criteria) this;
        }

        public Criteria andProtocolStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PROTOCOL_STATUS <=", value, "protocolStatus");
            return (Criteria) this;
        }

        public Criteria andProtocolStatusIn(List<BigDecimal> values) {
            addCriterion("PROTOCOL_STATUS in", values, "protocolStatus");
            return (Criteria) this;
        }

        public Criteria andProtocolStatusNotIn(List<BigDecimal> values) {
            addCriterion("PROTOCOL_STATUS not in", values, "protocolStatus");
            return (Criteria) this;
        }

        public Criteria andProtocolStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROTOCOL_STATUS between", value1, value2, "protocolStatus");
            return (Criteria) this;
        }

        public Criteria andProtocolStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROTOCOL_STATUS not between", value1, value2, "protocolStatus");
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