package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateChangeRequestExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public DateChangeRequestExample() {
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

        public Criteria andDataIdIsNull() {
            addCriterion("DATA_ID is null");
            return (Criteria) this;
        }

        public Criteria andDataIdIsNotNull() {
            addCriterion("DATA_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDataIdEqualTo(String value) {
            addCriterion("DATA_ID =", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotEqualTo(String value) {
            addCriterion("DATA_ID <>", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdGreaterThan(String value) {
            addCriterion("DATA_ID >", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdGreaterThanOrEqualTo(String value) {
            addCriterion("DATA_ID >=", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdLessThan(String value) {
            addCriterion("DATA_ID <", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdLessThanOrEqualTo(String value) {
            addCriterion("DATA_ID <=", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdLike(String value) {
            addCriterion("DATA_ID like", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotLike(String value) {
            addCriterion("DATA_ID not like", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdIn(List<String> values) {
            addCriterion("DATA_ID in", values, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotIn(List<String> values) {
            addCriterion("DATA_ID not in", values, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdBetween(String value1, String value2) {
            addCriterion("DATA_ID between", value1, value2, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotBetween(String value1, String value2) {
            addCriterion("DATA_ID not between", value1, value2, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataTypeIsNull() {
            addCriterion("DATA_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andDataTypeIsNotNull() {
            addCriterion("DATA_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andDataTypeEqualTo(String value) {
            addCriterion("DATA_TYPE =", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotEqualTo(String value) {
            addCriterion("DATA_TYPE <>", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThan(String value) {
            addCriterion("DATA_TYPE >", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThanOrEqualTo(String value) {
            addCriterion("DATA_TYPE >=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThan(String value) {
            addCriterion("DATA_TYPE <", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThanOrEqualTo(String value) {
            addCriterion("DATA_TYPE <=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLike(String value) {
            addCriterion("DATA_TYPE like", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotLike(String value) {
            addCriterion("DATA_TYPE not like", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeIn(List<String> values) {
            addCriterion("DATA_TYPE in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotIn(List<String> values) {
            addCriterion("DATA_TYPE not in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeBetween(String value1, String value2) {
            addCriterion("DATA_TYPE between", value1, value2, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotBetween(String value1, String value2) {
            addCriterion("DATA_TYPE not between", value1, value2, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataContentIsNull() {
            addCriterion("DATA_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andDataContentIsNotNull() {
            addCriterion("DATA_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andDataContentEqualTo(String value) {
            addCriterion("DATA_CONTENT =", value, "dataContent");
            return (Criteria) this;
        }

        public Criteria andDataContentNotEqualTo(String value) {
            addCriterion("DATA_CONTENT <>", value, "dataContent");
            return (Criteria) this;
        }

        public Criteria andDataContentGreaterThan(String value) {
            addCriterion("DATA_CONTENT >", value, "dataContent");
            return (Criteria) this;
        }

        public Criteria andDataContentGreaterThanOrEqualTo(String value) {
            addCriterion("DATA_CONTENT >=", value, "dataContent");
            return (Criteria) this;
        }

        public Criteria andDataContentLessThan(String value) {
            addCriterion("DATA_CONTENT <", value, "dataContent");
            return (Criteria) this;
        }

        public Criteria andDataContentLessThanOrEqualTo(String value) {
            addCriterion("DATA_CONTENT <=", value, "dataContent");
            return (Criteria) this;
        }

        public Criteria andDataContentLike(String value) {
            addCriterion("DATA_CONTENT like", value, "dataContent");
            return (Criteria) this;
        }

        public Criteria andDataContentNotLike(String value) {
            addCriterion("DATA_CONTENT not like", value, "dataContent");
            return (Criteria) this;
        }

        public Criteria andDataContentIn(List<String> values) {
            addCriterion("DATA_CONTENT in", values, "dataContent");
            return (Criteria) this;
        }

        public Criteria andDataContentNotIn(List<String> values) {
            addCriterion("DATA_CONTENT not in", values, "dataContent");
            return (Criteria) this;
        }

        public Criteria andDataContentBetween(String value1, String value2) {
            addCriterion("DATA_CONTENT between", value1, value2, "dataContent");
            return (Criteria) this;
        }

        public Criteria andDataContentNotBetween(String value1, String value2) {
            addCriterion("DATA_CONTENT not between", value1, value2, "dataContent");
            return (Criteria) this;
        }

        public Criteria andDataPreContentIsNull() {
            addCriterion("DATA_PRE_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andDataPreContentIsNotNull() {
            addCriterion("DATA_PRE_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andDataPreContentEqualTo(String value) {
            addCriterion("DATA_PRE_CONTENT =", value, "dataPreContent");
            return (Criteria) this;
        }

        public Criteria andDataPreContentNotEqualTo(String value) {
            addCriterion("DATA_PRE_CONTENT <>", value, "dataPreContent");
            return (Criteria) this;
        }

        public Criteria andDataPreContentGreaterThan(String value) {
            addCriterion("DATA_PRE_CONTENT >", value, "dataPreContent");
            return (Criteria) this;
        }

        public Criteria andDataPreContentGreaterThanOrEqualTo(String value) {
            addCriterion("DATA_PRE_CONTENT >=", value, "dataPreContent");
            return (Criteria) this;
        }

        public Criteria andDataPreContentLessThan(String value) {
            addCriterion("DATA_PRE_CONTENT <", value, "dataPreContent");
            return (Criteria) this;
        }

        public Criteria andDataPreContentLessThanOrEqualTo(String value) {
            addCriterion("DATA_PRE_CONTENT <=", value, "dataPreContent");
            return (Criteria) this;
        }

        public Criteria andDataPreContentLike(String value) {
            addCriterion("DATA_PRE_CONTENT like", value, "dataPreContent");
            return (Criteria) this;
        }

        public Criteria andDataPreContentNotLike(String value) {
            addCriterion("DATA_PRE_CONTENT not like", value, "dataPreContent");
            return (Criteria) this;
        }

        public Criteria andDataPreContentIn(List<String> values) {
            addCriterion("DATA_PRE_CONTENT in", values, "dataPreContent");
            return (Criteria) this;
        }

        public Criteria andDataPreContentNotIn(List<String> values) {
            addCriterion("DATA_PRE_CONTENT not in", values, "dataPreContent");
            return (Criteria) this;
        }

        public Criteria andDataPreContentBetween(String value1, String value2) {
            addCriterion("DATA_PRE_CONTENT between", value1, value2, "dataPreContent");
            return (Criteria) this;
        }

        public Criteria andDataPreContentNotBetween(String value1, String value2) {
            addCriterion("DATA_PRE_CONTENT not between", value1, value2, "dataPreContent");
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

        public Criteria andCUpdateIsNull() {
            addCriterion("C_UPDATE is null");
            return (Criteria) this;
        }

        public Criteria andCUpdateIsNotNull() {
            addCriterion("C_UPDATE is not null");
            return (Criteria) this;
        }

        public Criteria andCUpdateEqualTo(Date value) {
            addCriterion("C_UPDATE =", value, "cUpdate");
            return (Criteria) this;
        }

        public Criteria andCUpdateNotEqualTo(Date value) {
            addCriterion("C_UPDATE <>", value, "cUpdate");
            return (Criteria) this;
        }

        public Criteria andCUpdateGreaterThan(Date value) {
            addCriterion("C_UPDATE >", value, "cUpdate");
            return (Criteria) this;
        }

        public Criteria andCUpdateGreaterThanOrEqualTo(Date value) {
            addCriterion("C_UPDATE >=", value, "cUpdate");
            return (Criteria) this;
        }

        public Criteria andCUpdateLessThan(Date value) {
            addCriterion("C_UPDATE <", value, "cUpdate");
            return (Criteria) this;
        }

        public Criteria andCUpdateLessThanOrEqualTo(Date value) {
            addCriterion("C_UPDATE <=", value, "cUpdate");
            return (Criteria) this;
        }

        public Criteria andCUpdateIn(List<Date> values) {
            addCriterion("C_UPDATE in", values, "cUpdate");
            return (Criteria) this;
        }

        public Criteria andCUpdateNotIn(List<Date> values) {
            addCriterion("C_UPDATE not in", values, "cUpdate");
            return (Criteria) this;
        }

        public Criteria andCUpdateBetween(Date value1, Date value2) {
            addCriterion("C_UPDATE between", value1, value2, "cUpdate");
            return (Criteria) this;
        }

        public Criteria andCUpdateNotBetween(Date value1, Date value2) {
            addCriterion("C_UPDATE not between", value1, value2, "cUpdate");
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

        public Criteria andAppyStatusIsNull() {
            addCriterion("APPY_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andAppyStatusIsNotNull() {
            addCriterion("APPY_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andAppyStatusEqualTo(BigDecimal value) {
            addCriterion("APPY_STATUS =", value, "appyStatus");
            return (Criteria) this;
        }

        public Criteria andAppyStatusNotEqualTo(BigDecimal value) {
            addCriterion("APPY_STATUS <>", value, "appyStatus");
            return (Criteria) this;
        }

        public Criteria andAppyStatusGreaterThan(BigDecimal value) {
            addCriterion("APPY_STATUS >", value, "appyStatus");
            return (Criteria) this;
        }

        public Criteria andAppyStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("APPY_STATUS >=", value, "appyStatus");
            return (Criteria) this;
        }

        public Criteria andAppyStatusLessThan(BigDecimal value) {
            addCriterion("APPY_STATUS <", value, "appyStatus");
            return (Criteria) this;
        }

        public Criteria andAppyStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("APPY_STATUS <=", value, "appyStatus");
            return (Criteria) this;
        }

        public Criteria andAppyStatusIn(List<BigDecimal> values) {
            addCriterion("APPY_STATUS in", values, "appyStatus");
            return (Criteria) this;
        }

        public Criteria andAppyStatusNotIn(List<BigDecimal> values) {
            addCriterion("APPY_STATUS not in", values, "appyStatus");
            return (Criteria) this;
        }

        public Criteria andAppyStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APPY_STATUS between", value1, value2, "appyStatus");
            return (Criteria) this;
        }

        public Criteria andAppyStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APPY_STATUS not between", value1, value2, "appyStatus");
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