package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttachmentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AttachmentExample() {
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

        public Criteria andAttNameIsNull() {
            addCriterion("ATT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAttNameIsNotNull() {
            addCriterion("ATT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAttNameEqualTo(String value) {
            addCriterion("ATT_NAME =", value, "attName");
            return (Criteria) this;
        }

        public Criteria andAttNameNotEqualTo(String value) {
            addCriterion("ATT_NAME <>", value, "attName");
            return (Criteria) this;
        }

        public Criteria andAttNameGreaterThan(String value) {
            addCriterion("ATT_NAME >", value, "attName");
            return (Criteria) this;
        }

        public Criteria andAttNameGreaterThanOrEqualTo(String value) {
            addCriterion("ATT_NAME >=", value, "attName");
            return (Criteria) this;
        }

        public Criteria andAttNameLessThan(String value) {
            addCriterion("ATT_NAME <", value, "attName");
            return (Criteria) this;
        }

        public Criteria andAttNameLessThanOrEqualTo(String value) {
            addCriterion("ATT_NAME <=", value, "attName");
            return (Criteria) this;
        }

        public Criteria andAttNameLike(String value) {
            addCriterion("ATT_NAME like", value, "attName");
            return (Criteria) this;
        }

        public Criteria andAttNameNotLike(String value) {
            addCriterion("ATT_NAME not like", value, "attName");
            return (Criteria) this;
        }

        public Criteria andAttNameIn(List<String> values) {
            addCriterion("ATT_NAME in", values, "attName");
            return (Criteria) this;
        }

        public Criteria andAttNameNotIn(List<String> values) {
            addCriterion("ATT_NAME not in", values, "attName");
            return (Criteria) this;
        }

        public Criteria andAttNameBetween(String value1, String value2) {
            addCriterion("ATT_NAME between", value1, value2, "attName");
            return (Criteria) this;
        }

        public Criteria andAttNameNotBetween(String value1, String value2) {
            addCriterion("ATT_NAME not between", value1, value2, "attName");
            return (Criteria) this;
        }

        public Criteria andAttSizeIsNull() {
            addCriterion("ATT_SIZE is null");
            return (Criteria) this;
        }

        public Criteria andAttSizeIsNotNull() {
            addCriterion("ATT_SIZE is not null");
            return (Criteria) this;
        }

        public Criteria andAttSizeEqualTo(BigDecimal value) {
            addCriterion("ATT_SIZE =", value, "attSize");
            return (Criteria) this;
        }

        public Criteria andAttSizeNotEqualTo(BigDecimal value) {
            addCriterion("ATT_SIZE <>", value, "attSize");
            return (Criteria) this;
        }

        public Criteria andAttSizeGreaterThan(BigDecimal value) {
            addCriterion("ATT_SIZE >", value, "attSize");
            return (Criteria) this;
        }

        public Criteria andAttSizeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ATT_SIZE >=", value, "attSize");
            return (Criteria) this;
        }

        public Criteria andAttSizeLessThan(BigDecimal value) {
            addCriterion("ATT_SIZE <", value, "attSize");
            return (Criteria) this;
        }

        public Criteria andAttSizeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ATT_SIZE <=", value, "attSize");
            return (Criteria) this;
        }

        public Criteria andAttSizeIn(List<BigDecimal> values) {
            addCriterion("ATT_SIZE in", values, "attSize");
            return (Criteria) this;
        }

        public Criteria andAttSizeNotIn(List<BigDecimal> values) {
            addCriterion("ATT_SIZE not in", values, "attSize");
            return (Criteria) this;
        }

        public Criteria andAttSizeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ATT_SIZE between", value1, value2, "attSize");
            return (Criteria) this;
        }

        public Criteria andAttSizeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ATT_SIZE not between", value1, value2, "attSize");
            return (Criteria) this;
        }

        public Criteria andAttTypeIsNull() {
            addCriterion("ATT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAttTypeIsNotNull() {
            addCriterion("ATT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAttTypeEqualTo(String value) {
            addCriterion("ATT_TYPE =", value, "attType");
            return (Criteria) this;
        }

        public Criteria andAttTypeNotEqualTo(String value) {
            addCriterion("ATT_TYPE <>", value, "attType");
            return (Criteria) this;
        }

        public Criteria andAttTypeGreaterThan(String value) {
            addCriterion("ATT_TYPE >", value, "attType");
            return (Criteria) this;
        }

        public Criteria andAttTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ATT_TYPE >=", value, "attType");
            return (Criteria) this;
        }

        public Criteria andAttTypeLessThan(String value) {
            addCriterion("ATT_TYPE <", value, "attType");
            return (Criteria) this;
        }

        public Criteria andAttTypeLessThanOrEqualTo(String value) {
            addCriterion("ATT_TYPE <=", value, "attType");
            return (Criteria) this;
        }

        public Criteria andAttTypeLike(String value) {
            addCriterion("ATT_TYPE like", value, "attType");
            return (Criteria) this;
        }

        public Criteria andAttTypeNotLike(String value) {
            addCriterion("ATT_TYPE not like", value, "attType");
            return (Criteria) this;
        }

        public Criteria andAttTypeIn(List<String> values) {
            addCriterion("ATT_TYPE in", values, "attType");
            return (Criteria) this;
        }

        public Criteria andAttTypeNotIn(List<String> values) {
            addCriterion("ATT_TYPE not in", values, "attType");
            return (Criteria) this;
        }

        public Criteria andAttTypeBetween(String value1, String value2) {
            addCriterion("ATT_TYPE between", value1, value2, "attType");
            return (Criteria) this;
        }

        public Criteria andAttTypeNotBetween(String value1, String value2) {
            addCriterion("ATT_TYPE not between", value1, value2, "attType");
            return (Criteria) this;
        }

        public Criteria andAttDbpathIsNull() {
            addCriterion("ATT_DBPATH is null");
            return (Criteria) this;
        }

        public Criteria andAttDbpathIsNotNull() {
            addCriterion("ATT_DBPATH is not null");
            return (Criteria) this;
        }

        public Criteria andAttDbpathEqualTo(String value) {
            addCriterion("ATT_DBPATH =", value, "attDbpath");
            return (Criteria) this;
        }

        public Criteria andAttDbpathNotEqualTo(String value) {
            addCriterion("ATT_DBPATH <>", value, "attDbpath");
            return (Criteria) this;
        }

        public Criteria andAttDbpathGreaterThan(String value) {
            addCriterion("ATT_DBPATH >", value, "attDbpath");
            return (Criteria) this;
        }

        public Criteria andAttDbpathGreaterThanOrEqualTo(String value) {
            addCriterion("ATT_DBPATH >=", value, "attDbpath");
            return (Criteria) this;
        }

        public Criteria andAttDbpathLessThan(String value) {
            addCriterion("ATT_DBPATH <", value, "attDbpath");
            return (Criteria) this;
        }

        public Criteria andAttDbpathLessThanOrEqualTo(String value) {
            addCriterion("ATT_DBPATH <=", value, "attDbpath");
            return (Criteria) this;
        }

        public Criteria andAttDbpathLike(String value) {
            addCriterion("ATT_DBPATH like", value, "attDbpath");
            return (Criteria) this;
        }

        public Criteria andAttDbpathNotLike(String value) {
            addCriterion("ATT_DBPATH not like", value, "attDbpath");
            return (Criteria) this;
        }

        public Criteria andAttDbpathIn(List<String> values) {
            addCriterion("ATT_DBPATH in", values, "attDbpath");
            return (Criteria) this;
        }

        public Criteria andAttDbpathNotIn(List<String> values) {
            addCriterion("ATT_DBPATH not in", values, "attDbpath");
            return (Criteria) this;
        }

        public Criteria andAttDbpathBetween(String value1, String value2) {
            addCriterion("ATT_DBPATH between", value1, value2, "attDbpath");
            return (Criteria) this;
        }

        public Criteria andAttDbpathNotBetween(String value1, String value2) {
            addCriterion("ATT_DBPATH not between", value1, value2, "attDbpath");
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