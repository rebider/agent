package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnnouncementReadInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AnnouncementReadInfoExample() {
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

        public Criteria andAnnIdIsNull() {
            addCriterion("ANN_ID is null");
            return (Criteria) this;
        }

        public Criteria andAnnIdIsNotNull() {
            addCriterion("ANN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAnnIdEqualTo(String value) {
            addCriterion("ANN_ID =", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdNotEqualTo(String value) {
            addCriterion("ANN_ID <>", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdGreaterThan(String value) {
            addCriterion("ANN_ID >", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdGreaterThanOrEqualTo(String value) {
            addCriterion("ANN_ID >=", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdLessThan(String value) {
            addCriterion("ANN_ID <", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdLessThanOrEqualTo(String value) {
            addCriterion("ANN_ID <=", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdLike(String value) {
            addCriterion("ANN_ID like", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdNotLike(String value) {
            addCriterion("ANN_ID not like", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdIn(List<String> values) {
            addCriterion("ANN_ID in", values, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdNotIn(List<String> values) {
            addCriterion("ANN_ID not in", values, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdBetween(String value1, String value2) {
            addCriterion("ANN_ID between", value1, value2, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdNotBetween(String value1, String value2) {
            addCriterion("ANN_ID not between", value1, value2, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnTitleIsNull() {
            addCriterion("ANN_TITLE is null");
            return (Criteria) this;
        }

        public Criteria andAnnTitleIsNotNull() {
            addCriterion("ANN_TITLE is not null");
            return (Criteria) this;
        }

        public Criteria andAnnTitleEqualTo(String value) {
            addCriterion("ANN_TITLE =", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleNotEqualTo(String value) {
            addCriterion("ANN_TITLE <>", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleGreaterThan(String value) {
            addCriterion("ANN_TITLE >", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleGreaterThanOrEqualTo(String value) {
            addCriterion("ANN_TITLE >=", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleLessThan(String value) {
            addCriterion("ANN_TITLE <", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleLessThanOrEqualTo(String value) {
            addCriterion("ANN_TITLE <=", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleLike(String value) {
            addCriterion("ANN_TITLE like", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleNotLike(String value) {
            addCriterion("ANN_TITLE not like", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleIn(List<String> values) {
            addCriterion("ANN_TITLE in", values, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleNotIn(List<String> values) {
            addCriterion("ANN_TITLE not in", values, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleBetween(String value1, String value2) {
            addCriterion("ANN_TITLE between", value1, value2, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleNotBetween(String value1, String value2) {
            addCriterion("ANN_TITLE not between", value1, value2, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTypeIsNull() {
            addCriterion("ANN_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAnnTypeIsNotNull() {
            addCriterion("ANN_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAnnTypeEqualTo(BigDecimal value) {
            addCriterion("ANN_TYPE =", value, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeNotEqualTo(BigDecimal value) {
            addCriterion("ANN_TYPE <>", value, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeGreaterThan(BigDecimal value) {
            addCriterion("ANN_TYPE >", value, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ANN_TYPE >=", value, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeLessThan(BigDecimal value) {
            addCriterion("ANN_TYPE <", value, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ANN_TYPE <=", value, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeIn(List<BigDecimal> values) {
            addCriterion("ANN_TYPE in", values, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeNotIn(List<BigDecimal> values) {
            addCriterion("ANN_TYPE not in", values, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ANN_TYPE between", value1, value2, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ANN_TYPE not between", value1, value2, "annType");
            return (Criteria) this;
        }

        public Criteria andReadTmIsNull() {
            addCriterion("READ_TM is null");
            return (Criteria) this;
        }

        public Criteria andReadTmIsNotNull() {
            addCriterion("READ_TM is not null");
            return (Criteria) this;
        }

        public Criteria andReadTmEqualTo(Date value) {
            addCriterion("READ_TM =", value, "readTm");
            return (Criteria) this;
        }

        public Criteria andReadTmNotEqualTo(Date value) {
            addCriterion("READ_TM <>", value, "readTm");
            return (Criteria) this;
        }

        public Criteria andReadTmGreaterThan(Date value) {
            addCriterion("READ_TM >", value, "readTm");
            return (Criteria) this;
        }

        public Criteria andReadTmGreaterThanOrEqualTo(Date value) {
            addCriterion("READ_TM >=", value, "readTm");
            return (Criteria) this;
        }

        public Criteria andReadTmLessThan(Date value) {
            addCriterion("READ_TM <", value, "readTm");
            return (Criteria) this;
        }

        public Criteria andReadTmLessThanOrEqualTo(Date value) {
            addCriterion("READ_TM <=", value, "readTm");
            return (Criteria) this;
        }

        public Criteria andReadTmIn(List<Date> values) {
            addCriterion("READ_TM in", values, "readTm");
            return (Criteria) this;
        }

        public Criteria andReadTmNotIn(List<Date> values) {
            addCriterion("READ_TM not in", values, "readTm");
            return (Criteria) this;
        }

        public Criteria andReadTmBetween(Date value1, Date value2) {
            addCriterion("READ_TM between", value1, value2, "readTm");
            return (Criteria) this;
        }

        public Criteria andReadTmNotBetween(Date value1, Date value2) {
            addCriterion("READ_TM not between", value1, value2, "readTm");
            return (Criteria) this;
        }

        public Criteria andReadUserIdIsNull() {
            addCriterion("READ_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andReadUserIdIsNotNull() {
            addCriterion("READ_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andReadUserIdEqualTo(String value) {
            addCriterion("READ_USER_ID =", value, "readUserId");
            return (Criteria) this;
        }

        public Criteria andReadUserIdNotEqualTo(String value) {
            addCriterion("READ_USER_ID <>", value, "readUserId");
            return (Criteria) this;
        }

        public Criteria andReadUserIdGreaterThan(String value) {
            addCriterion("READ_USER_ID >", value, "readUserId");
            return (Criteria) this;
        }

        public Criteria andReadUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("READ_USER_ID >=", value, "readUserId");
            return (Criteria) this;
        }

        public Criteria andReadUserIdLessThan(String value) {
            addCriterion("READ_USER_ID <", value, "readUserId");
            return (Criteria) this;
        }

        public Criteria andReadUserIdLessThanOrEqualTo(String value) {
            addCriterion("READ_USER_ID <=", value, "readUserId");
            return (Criteria) this;
        }

        public Criteria andReadUserIdLike(String value) {
            addCriterion("READ_USER_ID like", value, "readUserId");
            return (Criteria) this;
        }

        public Criteria andReadUserIdNotLike(String value) {
            addCriterion("READ_USER_ID not like", value, "readUserId");
            return (Criteria) this;
        }

        public Criteria andReadUserIdIn(List<String> values) {
            addCriterion("READ_USER_ID in", values, "readUserId");
            return (Criteria) this;
        }

        public Criteria andReadUserIdNotIn(List<String> values) {
            addCriterion("READ_USER_ID not in", values, "readUserId");
            return (Criteria) this;
        }

        public Criteria andReadUserIdBetween(String value1, String value2) {
            addCriterion("READ_USER_ID between", value1, value2, "readUserId");
            return (Criteria) this;
        }

        public Criteria andReadUserIdNotBetween(String value1, String value2) {
            addCriterion("READ_USER_ID not between", value1, value2, "readUserId");
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