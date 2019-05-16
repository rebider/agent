package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.util.ArrayList;
import java.util.List;

public class OInternetCardMerchExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OInternetCardMerchExample() {
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

        public Criteria andIccidIsNull() {
            addCriterion("ICCID is null");
            return (Criteria) this;
        }

        public Criteria andIccidIsNotNull() {
            addCriterion("ICCID is not null");
            return (Criteria) this;
        }

        public Criteria andIccidEqualTo(String value) {
            addCriterion("ICCID =", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotEqualTo(String value) {
            addCriterion("ICCID <>", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidGreaterThan(String value) {
            addCriterion("ICCID >", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidGreaterThanOrEqualTo(String value) {
            addCriterion("ICCID >=", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidLessThan(String value) {
            addCriterion("ICCID <", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidLessThanOrEqualTo(String value) {
            addCriterion("ICCID <=", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidLike(String value) {
            addCriterion("ICCID like", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotLike(String value) {
            addCriterion("ICCID not like", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidIn(List<String> values) {
            addCriterion("ICCID in", values, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotIn(List<String> values) {
            addCriterion("ICCID not in", values, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidBetween(String value1, String value2) {
            addCriterion("ICCID between", value1, value2, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotBetween(String value1, String value2) {
            addCriterion("ICCID not between", value1, value2, "iccid");
            return (Criteria) this;
        }

        public Criteria andMerchIdIsNull() {
            addCriterion("MERCH_ID is null");
            return (Criteria) this;
        }

        public Criteria andMerchIdIsNotNull() {
            addCriterion("MERCH_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMerchIdEqualTo(String value) {
            addCriterion("MERCH_ID =", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdNotEqualTo(String value) {
            addCriterion("MERCH_ID <>", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdGreaterThan(String value) {
            addCriterion("MERCH_ID >", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdGreaterThanOrEqualTo(String value) {
            addCriterion("MERCH_ID >=", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdLessThan(String value) {
            addCriterion("MERCH_ID <", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdLessThanOrEqualTo(String value) {
            addCriterion("MERCH_ID <=", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdLike(String value) {
            addCriterion("MERCH_ID like", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdNotLike(String value) {
            addCriterion("MERCH_ID not like", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdIn(List<String> values) {
            addCriterion("MERCH_ID in", values, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdNotIn(List<String> values) {
            addCriterion("MERCH_ID not in", values, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdBetween(String value1, String value2) {
            addCriterion("MERCH_ID between", value1, value2, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdNotBetween(String value1, String value2) {
            addCriterion("MERCH_ID not between", value1, value2, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchNameIsNull() {
            addCriterion("MERCH_NAME is null");
            return (Criteria) this;
        }

        public Criteria andMerchNameIsNotNull() {
            addCriterion("MERCH_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andMerchNameEqualTo(String value) {
            addCriterion("MERCH_NAME =", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameNotEqualTo(String value) {
            addCriterion("MERCH_NAME <>", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameGreaterThan(String value) {
            addCriterion("MERCH_NAME >", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameGreaterThanOrEqualTo(String value) {
            addCriterion("MERCH_NAME >=", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameLessThan(String value) {
            addCriterion("MERCH_NAME <", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameLessThanOrEqualTo(String value) {
            addCriterion("MERCH_NAME <=", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameLike(String value) {
            addCriterion("MERCH_NAME like", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameNotLike(String value) {
            addCriterion("MERCH_NAME not like", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameIn(List<String> values) {
            addCriterion("MERCH_NAME in", values, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameNotIn(List<String> values) {
            addCriterion("MERCH_NAME not in", values, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameBetween(String value1, String value2) {
            addCriterion("MERCH_NAME between", value1, value2, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameNotBetween(String value1, String value2) {
            addCriterion("MERCH_NAME not between", value1, value2, "merchName");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumIsNull() {
            addCriterion("INTERNET_CARD_NUM is null");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumIsNotNull() {
            addCriterion("INTERNET_CARD_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumEqualTo(String value) {
            addCriterion("INTERNET_CARD_NUM =", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumNotEqualTo(String value) {
            addCriterion("INTERNET_CARD_NUM <>", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumGreaterThan(String value) {
            addCriterion("INTERNET_CARD_NUM >", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumGreaterThanOrEqualTo(String value) {
            addCriterion("INTERNET_CARD_NUM >=", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumLessThan(String value) {
            addCriterion("INTERNET_CARD_NUM <", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumLessThanOrEqualTo(String value) {
            addCriterion("INTERNET_CARD_NUM <=", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumLike(String value) {
            addCriterion("INTERNET_CARD_NUM like", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumNotLike(String value) {
            addCriterion("INTERNET_CARD_NUM not like", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumIn(List<String> values) {
            addCriterion("INTERNET_CARD_NUM in", values, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumNotIn(List<String> values) {
            addCriterion("INTERNET_CARD_NUM not in", values, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumBetween(String value1, String value2) {
            addCriterion("INTERNET_CARD_NUM between", value1, value2, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumNotBetween(String value1, String value2) {
            addCriterion("INTERNET_CARD_NUM not between", value1, value2, "internetCardNum");
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