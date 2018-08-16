package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TemplateAgreementExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public TemplateAgreementExample() {
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

        public Criteria andAgreNameIsNull() {
            addCriterion("AGRE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAgreNameIsNotNull() {
            addCriterion("AGRE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAgreNameEqualTo(String value) {
            addCriterion("AGRE_NAME =", value, "agreName");
            return (Criteria) this;
        }

        public Criteria andAgreNameNotEqualTo(String value) {
            addCriterion("AGRE_NAME <>", value, "agreName");
            return (Criteria) this;
        }

        public Criteria andAgreNameGreaterThan(String value) {
            addCriterion("AGRE_NAME >", value, "agreName");
            return (Criteria) this;
        }

        public Criteria andAgreNameGreaterThanOrEqualTo(String value) {
            addCriterion("AGRE_NAME >=", value, "agreName");
            return (Criteria) this;
        }

        public Criteria andAgreNameLessThan(String value) {
            addCriterion("AGRE_NAME <", value, "agreName");
            return (Criteria) this;
        }

        public Criteria andAgreNameLessThanOrEqualTo(String value) {
            addCriterion("AGRE_NAME <=", value, "agreName");
            return (Criteria) this;
        }

        public Criteria andAgreNameLike(String value) {
            addCriterion("AGRE_NAME like", value, "agreName");
            return (Criteria) this;
        }

        public Criteria andAgreNameNotLike(String value) {
            addCriterion("AGRE_NAME not like", value, "agreName");
            return (Criteria) this;
        }

        public Criteria andAgreNameIn(List<String> values) {
            addCriterion("AGRE_NAME in", values, "agreName");
            return (Criteria) this;
        }

        public Criteria andAgreNameNotIn(List<String> values) {
            addCriterion("AGRE_NAME not in", values, "agreName");
            return (Criteria) this;
        }

        public Criteria andAgreNameBetween(String value1, String value2) {
            addCriterion("AGRE_NAME between", value1, value2, "agreName");
            return (Criteria) this;
        }

        public Criteria andAgreNameNotBetween(String value1, String value2) {
            addCriterion("AGRE_NAME not between", value1, value2, "agreName");
            return (Criteria) this;
        }

        public Criteria andAgreVersionIsNull() {
            addCriterion("AGRE_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andAgreVersionIsNotNull() {
            addCriterion("AGRE_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andAgreVersionEqualTo(String value) {
            addCriterion("AGRE_VERSION =", value, "agreVersion");
            return (Criteria) this;
        }

        public Criteria andAgreVersionNotEqualTo(String value) {
            addCriterion("AGRE_VERSION <>", value, "agreVersion");
            return (Criteria) this;
        }

        public Criteria andAgreVersionGreaterThan(String value) {
            addCriterion("AGRE_VERSION >", value, "agreVersion");
            return (Criteria) this;
        }

        public Criteria andAgreVersionGreaterThanOrEqualTo(String value) {
            addCriterion("AGRE_VERSION >=", value, "agreVersion");
            return (Criteria) this;
        }

        public Criteria andAgreVersionLessThan(String value) {
            addCriterion("AGRE_VERSION <", value, "agreVersion");
            return (Criteria) this;
        }

        public Criteria andAgreVersionLessThanOrEqualTo(String value) {
            addCriterion("AGRE_VERSION <=", value, "agreVersion");
            return (Criteria) this;
        }

        public Criteria andAgreVersionLike(String value) {
            addCriterion("AGRE_VERSION like", value, "agreVersion");
            return (Criteria) this;
        }

        public Criteria andAgreVersionNotLike(String value) {
            addCriterion("AGRE_VERSION not like", value, "agreVersion");
            return (Criteria) this;
        }

        public Criteria andAgreVersionIn(List<String> values) {
            addCriterion("AGRE_VERSION in", values, "agreVersion");
            return (Criteria) this;
        }

        public Criteria andAgreVersionNotIn(List<String> values) {
            addCriterion("AGRE_VERSION not in", values, "agreVersion");
            return (Criteria) this;
        }

        public Criteria andAgreVersionBetween(String value1, String value2) {
            addCriterion("AGRE_VERSION between", value1, value2, "agreVersion");
            return (Criteria) this;
        }

        public Criteria andAgreVersionNotBetween(String value1, String value2) {
            addCriterion("AGRE_VERSION not between", value1, value2, "agreVersion");
            return (Criteria) this;
        }

        public Criteria andAgreTypeIsNull() {
            addCriterion("AGRE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAgreTypeIsNotNull() {
            addCriterion("AGRE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAgreTypeEqualTo(String value) {
            addCriterion("AGRE_TYPE =", value, "agreType");
            return (Criteria) this;
        }

        public Criteria andAgreTypeNotEqualTo(String value) {
            addCriterion("AGRE_TYPE <>", value, "agreType");
            return (Criteria) this;
        }

        public Criteria andAgreTypeGreaterThan(String value) {
            addCriterion("AGRE_TYPE >", value, "agreType");
            return (Criteria) this;
        }

        public Criteria andAgreTypeGreaterThanOrEqualTo(String value) {
            addCriterion("AGRE_TYPE >=", value, "agreType");
            return (Criteria) this;
        }

        public Criteria andAgreTypeLessThan(String value) {
            addCriterion("AGRE_TYPE <", value, "agreType");
            return (Criteria) this;
        }

        public Criteria andAgreTypeLessThanOrEqualTo(String value) {
            addCriterion("AGRE_TYPE <=", value, "agreType");
            return (Criteria) this;
        }

        public Criteria andAgreTypeLike(String value) {
            addCriterion("AGRE_TYPE like", value, "agreType");
            return (Criteria) this;
        }

        public Criteria andAgreTypeNotLike(String value) {
            addCriterion("AGRE_TYPE not like", value, "agreType");
            return (Criteria) this;
        }

        public Criteria andAgreTypeIn(List<String> values) {
            addCriterion("AGRE_TYPE in", values, "agreType");
            return (Criteria) this;
        }

        public Criteria andAgreTypeNotIn(List<String> values) {
            addCriterion("AGRE_TYPE not in", values, "agreType");
            return (Criteria) this;
        }

        public Criteria andAgreTypeBetween(String value1, String value2) {
            addCriterion("AGRE_TYPE between", value1, value2, "agreType");
            return (Criteria) this;
        }

        public Criteria andAgreTypeNotBetween(String value1, String value2) {
            addCriterion("AGRE_TYPE not between", value1, value2, "agreType");
            return (Criteria) this;
        }

        public Criteria andAttridIsNull() {
            addCriterion("ATTRID is null");
            return (Criteria) this;
        }

        public Criteria andAttridIsNotNull() {
            addCriterion("ATTRID is not null");
            return (Criteria) this;
        }

        public Criteria andAttridEqualTo(String value) {
            addCriterion("ATTRID =", value, "attrid");
            return (Criteria) this;
        }

        public Criteria andAttridNotEqualTo(String value) {
            addCriterion("ATTRID <>", value, "attrid");
            return (Criteria) this;
        }

        public Criteria andAttridGreaterThan(String value) {
            addCriterion("ATTRID >", value, "attrid");
            return (Criteria) this;
        }

        public Criteria andAttridGreaterThanOrEqualTo(String value) {
            addCriterion("ATTRID >=", value, "attrid");
            return (Criteria) this;
        }

        public Criteria andAttridLessThan(String value) {
            addCriterion("ATTRID <", value, "attrid");
            return (Criteria) this;
        }

        public Criteria andAttridLessThanOrEqualTo(String value) {
            addCriterion("ATTRID <=", value, "attrid");
            return (Criteria) this;
        }

        public Criteria andAttridLike(String value) {
            addCriterion("ATTRID like", value, "attrid");
            return (Criteria) this;
        }

        public Criteria andAttridNotLike(String value) {
            addCriterion("ATTRID not like", value, "attrid");
            return (Criteria) this;
        }

        public Criteria andAttridIn(List<String> values) {
            addCriterion("ATTRID in", values, "attrid");
            return (Criteria) this;
        }

        public Criteria andAttridNotIn(List<String> values) {
            addCriterion("ATTRID not in", values, "attrid");
            return (Criteria) this;
        }

        public Criteria andAttridBetween(String value1, String value2) {
            addCriterion("ATTRID between", value1, value2, "attrid");
            return (Criteria) this;
        }

        public Criteria andAttridNotBetween(String value1, String value2) {
            addCriterion("ATTRID not between", value1, value2, "attrid");
            return (Criteria) this;
        }

        public Criteria andAgreViewTypeIsNull() {
            addCriterion("AGRE_VIEW_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAgreViewTypeIsNotNull() {
            addCriterion("AGRE_VIEW_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAgreViewTypeEqualTo(BigDecimal value) {
            addCriterion("AGRE_VIEW_TYPE =", value, "agreViewType");
            return (Criteria) this;
        }

        public Criteria andAgreViewTypeNotEqualTo(BigDecimal value) {
            addCriterion("AGRE_VIEW_TYPE <>", value, "agreViewType");
            return (Criteria) this;
        }

        public Criteria andAgreViewTypeGreaterThan(BigDecimal value) {
            addCriterion("AGRE_VIEW_TYPE >", value, "agreViewType");
            return (Criteria) this;
        }

        public Criteria andAgreViewTypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AGRE_VIEW_TYPE >=", value, "agreViewType");
            return (Criteria) this;
        }

        public Criteria andAgreViewTypeLessThan(BigDecimal value) {
            addCriterion("AGRE_VIEW_TYPE <", value, "agreViewType");
            return (Criteria) this;
        }

        public Criteria andAgreViewTypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AGRE_VIEW_TYPE <=", value, "agreViewType");
            return (Criteria) this;
        }

        public Criteria andAgreViewTypeIn(List<BigDecimal> values) {
            addCriterion("AGRE_VIEW_TYPE in", values, "agreViewType");
            return (Criteria) this;
        }

        public Criteria andAgreViewTypeNotIn(List<BigDecimal> values) {
            addCriterion("AGRE_VIEW_TYPE not in", values, "agreViewType");
            return (Criteria) this;
        }

        public Criteria andAgreViewTypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AGRE_VIEW_TYPE between", value1, value2, "agreViewType");
            return (Criteria) this;
        }

        public Criteria andAgreViewTypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AGRE_VIEW_TYPE not between", value1, value2, "agreViewType");
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

        public Criteria andCUtimeIsNull() {
            addCriterion("C_UTIME is null");
            return (Criteria) this;
        }

        public Criteria andCUtimeIsNotNull() {
            addCriterion("C_UTIME is not null");
            return (Criteria) this;
        }

        public Criteria andCUtimeEqualTo(Date value) {
            addCriterion("C_UTIME =", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeNotEqualTo(Date value) {
            addCriterion("C_UTIME <>", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeGreaterThan(Date value) {
            addCriterion("C_UTIME >", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("C_UTIME >=", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeLessThan(Date value) {
            addCriterion("C_UTIME <", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeLessThanOrEqualTo(Date value) {
            addCriterion("C_UTIME <=", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeIn(List<Date> values) {
            addCriterion("C_UTIME in", values, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeNotIn(List<Date> values) {
            addCriterion("C_UTIME not in", values, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeBetween(Date value1, Date value2) {
            addCriterion("C_UTIME between", value1, value2, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeNotBetween(Date value1, Date value2) {
            addCriterion("C_UTIME not between", value1, value2, "cUtime");
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