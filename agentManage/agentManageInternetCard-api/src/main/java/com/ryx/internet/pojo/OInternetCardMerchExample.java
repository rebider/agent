package com.ryx.internet.pojo;

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

        public Criteria andChnTermposiIsNull() {
            addCriterion("CHN_TERMPOSI is null");
            return (Criteria) this;
        }

        public Criteria andChnTermposiIsNotNull() {
            addCriterion("CHN_TERMPOSI is not null");
            return (Criteria) this;
        }

        public Criteria andChnTermposiEqualTo(String value) {
            addCriterion("CHN_TERMPOSI =", value, "chnTermposi");
            return (Criteria) this;
        }

        public Criteria andChnTermposiNotEqualTo(String value) {
            addCriterion("CHN_TERMPOSI <>", value, "chnTermposi");
            return (Criteria) this;
        }

        public Criteria andChnTermposiGreaterThan(String value) {
            addCriterion("CHN_TERMPOSI >", value, "chnTermposi");
            return (Criteria) this;
        }

        public Criteria andChnTermposiGreaterThanOrEqualTo(String value) {
            addCriterion("CHN_TERMPOSI >=", value, "chnTermposi");
            return (Criteria) this;
        }

        public Criteria andChnTermposiLessThan(String value) {
            addCriterion("CHN_TERMPOSI <", value, "chnTermposi");
            return (Criteria) this;
        }

        public Criteria andChnTermposiLessThanOrEqualTo(String value) {
            addCriterion("CHN_TERMPOSI <=", value, "chnTermposi");
            return (Criteria) this;
        }

        public Criteria andChnTermposiLike(String value) {
            addCriterion("CHN_TERMPOSI like", value, "chnTermposi");
            return (Criteria) this;
        }

        public Criteria andChnTermposiNotLike(String value) {
            addCriterion("CHN_TERMPOSI not like", value, "chnTermposi");
            return (Criteria) this;
        }

        public Criteria andChnTermposiIn(List<String> values) {
            addCriterion("CHN_TERMPOSI in", values, "chnTermposi");
            return (Criteria) this;
        }

        public Criteria andChnTermposiNotIn(List<String> values) {
            addCriterion("CHN_TERMPOSI not in", values, "chnTermposi");
            return (Criteria) this;
        }

        public Criteria andChnTermposiBetween(String value1, String value2) {
            addCriterion("CHN_TERMPOSI between", value1, value2, "chnTermposi");
            return (Criteria) this;
        }

        public Criteria andChnTermposiNotBetween(String value1, String value2) {
            addCriterion("CHN_TERMPOSI not between", value1, value2, "chnTermposi");
            return (Criteria) this;
        }

        public Criteria andChnMerchIdIsNull() {
            addCriterion("CHN_MERCH_ID is null");
            return (Criteria) this;
        }

        public Criteria andChnMerchIdIsNotNull() {
            addCriterion("CHN_MERCH_ID is not null");
            return (Criteria) this;
        }

        public Criteria andChnMerchIdEqualTo(String value) {
            addCriterion("CHN_MERCH_ID =", value, "chnMerchId");
            return (Criteria) this;
        }

        public Criteria andChnMerchIdNotEqualTo(String value) {
            addCriterion("CHN_MERCH_ID <>", value, "chnMerchId");
            return (Criteria) this;
        }

        public Criteria andChnMerchIdGreaterThan(String value) {
            addCriterion("CHN_MERCH_ID >", value, "chnMerchId");
            return (Criteria) this;
        }

        public Criteria andChnMerchIdGreaterThanOrEqualTo(String value) {
            addCriterion("CHN_MERCH_ID >=", value, "chnMerchId");
            return (Criteria) this;
        }

        public Criteria andChnMerchIdLessThan(String value) {
            addCriterion("CHN_MERCH_ID <", value, "chnMerchId");
            return (Criteria) this;
        }

        public Criteria andChnMerchIdLessThanOrEqualTo(String value) {
            addCriterion("CHN_MERCH_ID <=", value, "chnMerchId");
            return (Criteria) this;
        }

        public Criteria andChnMerchIdLike(String value) {
            addCriterion("CHN_MERCH_ID like", value, "chnMerchId");
            return (Criteria) this;
        }

        public Criteria andChnMerchIdNotLike(String value) {
            addCriterion("CHN_MERCH_ID not like", value, "chnMerchId");
            return (Criteria) this;
        }

        public Criteria andChnMerchIdIn(List<String> values) {
            addCriterion("CHN_MERCH_ID in", values, "chnMerchId");
            return (Criteria) this;
        }

        public Criteria andChnMerchIdNotIn(List<String> values) {
            addCriterion("CHN_MERCH_ID not in", values, "chnMerchId");
            return (Criteria) this;
        }

        public Criteria andChnMerchIdBetween(String value1, String value2) {
            addCriterion("CHN_MERCH_ID between", value1, value2, "chnMerchId");
            return (Criteria) this;
        }

        public Criteria andChnMerchIdNotBetween(String value1, String value2) {
            addCriterion("CHN_MERCH_ID not between", value1, value2, "chnMerchId");
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

        public Criteria andTranDateIsNull() {
            addCriterion("TRAN_DATE is null");
            return (Criteria) this;
        }

        public Criteria andTranDateIsNotNull() {
            addCriterion("TRAN_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andTranDateEqualTo(String value) {
            addCriterion("TRAN_DATE =", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotEqualTo(String value) {
            addCriterion("TRAN_DATE <>", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateGreaterThan(String value) {
            addCriterion("TRAN_DATE >", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateGreaterThanOrEqualTo(String value) {
            addCriterion("TRAN_DATE >=", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateLessThan(String value) {
            addCriterion("TRAN_DATE <", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateLessThanOrEqualTo(String value) {
            addCriterion("TRAN_DATE <=", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateLike(String value) {
            addCriterion("TRAN_DATE like", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotLike(String value) {
            addCriterion("TRAN_DATE not like", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateIn(List<String> values) {
            addCriterion("TRAN_DATE in", values, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotIn(List<String> values) {
            addCriterion("TRAN_DATE not in", values, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateBetween(String value1, String value2) {
            addCriterion("TRAN_DATE between", value1, value2, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotBetween(String value1, String value2) {
            addCriterion("TRAN_DATE not between", value1, value2, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranTimeIsNull() {
            addCriterion("TRAN_TIME is null");
            return (Criteria) this;
        }

        public Criteria andTranTimeIsNotNull() {
            addCriterion("TRAN_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andTranTimeEqualTo(String value) {
            addCriterion("TRAN_TIME =", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeNotEqualTo(String value) {
            addCriterion("TRAN_TIME <>", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeGreaterThan(String value) {
            addCriterion("TRAN_TIME >", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeGreaterThanOrEqualTo(String value) {
            addCriterion("TRAN_TIME >=", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeLessThan(String value) {
            addCriterion("TRAN_TIME <", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeLessThanOrEqualTo(String value) {
            addCriterion("TRAN_TIME <=", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeLike(String value) {
            addCriterion("TRAN_TIME like", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeNotLike(String value) {
            addCriterion("TRAN_TIME not like", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeIn(List<String> values) {
            addCriterion("TRAN_TIME in", values, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeNotIn(List<String> values) {
            addCriterion("TRAN_TIME not in", values, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeBetween(String value1, String value2) {
            addCriterion("TRAN_TIME between", value1, value2, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeNotBetween(String value1, String value2) {
            addCriterion("TRAN_TIME not between", value1, value2, "tranTime");
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