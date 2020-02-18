package com.ryx.jobOrder.pojo;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JoKeyManageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public JoKeyManageExample() {
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

        public Criteria andJoKeyTypeIsNull() {
            addCriterion("JO_KEY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andJoKeyTypeIsNotNull() {
            addCriterion("JO_KEY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andJoKeyTypeEqualTo(String value) {
            addCriterion("JO_KEY_TYPE =", value, "joKeyType");
            return (Criteria) this;
        }

        public Criteria andJoKeyTypeNotEqualTo(String value) {
            addCriterion("JO_KEY_TYPE <>", value, "joKeyType");
            return (Criteria) this;
        }

        public Criteria andJoKeyTypeGreaterThan(String value) {
            addCriterion("JO_KEY_TYPE >", value, "joKeyType");
            return (Criteria) this;
        }

        public Criteria andJoKeyTypeGreaterThanOrEqualTo(String value) {
            addCriterion("JO_KEY_TYPE >=", value, "joKeyType");
            return (Criteria) this;
        }

        public Criteria andJoKeyTypeLessThan(String value) {
            addCriterion("JO_KEY_TYPE <", value, "joKeyType");
            return (Criteria) this;
        }

        public Criteria andJoKeyTypeLessThanOrEqualTo(String value) {
            addCriterion("JO_KEY_TYPE <=", value, "joKeyType");
            return (Criteria) this;
        }

        public Criteria andJoKeyTypeLike(String value) {
            addCriterion("JO_KEY_TYPE like", value, "joKeyType");
            return (Criteria) this;
        }

        public Criteria andJoKeyTypeNotLike(String value) {
            addCriterion("JO_KEY_TYPE not like", value, "joKeyType");
            return (Criteria) this;
        }

        public Criteria andJoKeyTypeIn(List<String> values) {
            addCriterion("JO_KEY_TYPE in", values, "joKeyType");
            return (Criteria) this;
        }

        public Criteria andJoKeyTypeNotIn(List<String> values) {
            addCriterion("JO_KEY_TYPE not in", values, "joKeyType");
            return (Criteria) this;
        }

        public Criteria andJoKeyTypeBetween(String value1, String value2) {
            addCriterion("JO_KEY_TYPE between", value1, value2, "joKeyType");
            return (Criteria) this;
        }

        public Criteria andJoKeyTypeNotBetween(String value1, String value2) {
            addCriterion("JO_KEY_TYPE not between", value1, value2, "joKeyType");
            return (Criteria) this;
        }

        public Criteria andJoKeyIsNull() {
            addCriterion("JO_KEY is null");
            return (Criteria) this;
        }

        public Criteria andJoKeyIsNotNull() {
            addCriterion("JO_KEY is not null");
            return (Criteria) this;
        }

        public Criteria andJoKeyEqualTo(String value) {
            addCriterion("JO_KEY =", value, "joKey");
            return (Criteria) this;
        }

        public Criteria andJoKeyNotEqualTo(String value) {
            addCriterion("JO_KEY <>", value, "joKey");
            return (Criteria) this;
        }

        public Criteria andJoKeyGreaterThan(String value) {
            addCriterion("JO_KEY >", value, "joKey");
            return (Criteria) this;
        }

        public Criteria andJoKeyGreaterThanOrEqualTo(String value) {
            addCriterion("JO_KEY >=", value, "joKey");
            return (Criteria) this;
        }

        public Criteria andJoKeyLessThan(String value) {
            addCriterion("JO_KEY <", value, "joKey");
            return (Criteria) this;
        }

        public Criteria andJoKeyLessThanOrEqualTo(String value) {
            addCriterion("JO_KEY <=", value, "joKey");
            return (Criteria) this;
        }

        public Criteria andJoKeyLike(String value) {
            addCriterion("JO_KEY like", value, "joKey");
            return (Criteria) this;
        }

        public Criteria andJoKeyNotLike(String value) {
            addCriterion("JO_KEY not like", value, "joKey");
            return (Criteria) this;
        }

        public Criteria andJoKeyIn(List<String> values) {
            addCriterion("JO_KEY in", values, "joKey");
            return (Criteria) this;
        }

        public Criteria andJoKeyNotIn(List<String> values) {
            addCriterion("JO_KEY not in", values, "joKey");
            return (Criteria) this;
        }

        public Criteria andJoKeyBetween(String value1, String value2) {
            addCriterion("JO_KEY between", value1, value2, "joKey");
            return (Criteria) this;
        }

        public Criteria andJoKeyNotBetween(String value1, String value2) {
            addCriterion("JO_KEY not between", value1, value2, "joKey");
            return (Criteria) this;
        }

        public Criteria andJoKeyNameIsNull() {
            addCriterion("JO_KEY_NAME is null");
            return (Criteria) this;
        }

        public Criteria andJoKeyNameIsNotNull() {
            addCriterion("JO_KEY_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andJoKeyNameEqualTo(String value) {
            addCriterion("JO_KEY_NAME =", value, "joKeyName");
            return (Criteria) this;
        }

        public Criteria andJoKeyNameNotEqualTo(String value) {
            addCriterion("JO_KEY_NAME <>", value, "joKeyName");
            return (Criteria) this;
        }

        public Criteria andJoKeyNameGreaterThan(String value) {
            addCriterion("JO_KEY_NAME >", value, "joKeyName");
            return (Criteria) this;
        }

        public Criteria andJoKeyNameGreaterThanOrEqualTo(String value) {
            addCriterion("JO_KEY_NAME >=", value, "joKeyName");
            return (Criteria) this;
        }

        public Criteria andJoKeyNameLessThan(String value) {
            addCriterion("JO_KEY_NAME <", value, "joKeyName");
            return (Criteria) this;
        }

        public Criteria andJoKeyNameLessThanOrEqualTo(String value) {
            addCriterion("JO_KEY_NAME <=", value, "joKeyName");
            return (Criteria) this;
        }

        public Criteria andJoKeyNameLike(String value) {
            addCriterion("JO_KEY_NAME like", value, "joKeyName");
            return (Criteria) this;
        }

        public Criteria andJoKeyNameNotLike(String value) {
            addCriterion("JO_KEY_NAME not like", value, "joKeyName");
            return (Criteria) this;
        }

        public Criteria andJoKeyNameIn(List<String> values) {
            addCriterion("JO_KEY_NAME in", values, "joKeyName");
            return (Criteria) this;
        }

        public Criteria andJoKeyNameNotIn(List<String> values) {
            addCriterion("JO_KEY_NAME not in", values, "joKeyName");
            return (Criteria) this;
        }

        public Criteria andJoKeyNameBetween(String value1, String value2) {
            addCriterion("JO_KEY_NAME between", value1, value2, "joKeyName");
            return (Criteria) this;
        }

        public Criteria andJoKeyNameNotBetween(String value1, String value2) {
            addCriterion("JO_KEY_NAME not between", value1, value2, "joKeyName");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueTypeIsNull() {
            addCriterion("JO_KEY_VALUE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueTypeIsNotNull() {
            addCriterion("JO_KEY_VALUE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueTypeEqualTo(String value) {
            addCriterion("JO_KEY_VALUE_TYPE =", value, "joKeyValueType");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueTypeNotEqualTo(String value) {
            addCriterion("JO_KEY_VALUE_TYPE <>", value, "joKeyValueType");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueTypeGreaterThan(String value) {
            addCriterion("JO_KEY_VALUE_TYPE >", value, "joKeyValueType");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueTypeGreaterThanOrEqualTo(String value) {
            addCriterion("JO_KEY_VALUE_TYPE >=", value, "joKeyValueType");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueTypeLessThan(String value) {
            addCriterion("JO_KEY_VALUE_TYPE <", value, "joKeyValueType");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueTypeLessThanOrEqualTo(String value) {
            addCriterion("JO_KEY_VALUE_TYPE <=", value, "joKeyValueType");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueTypeLike(String value) {
            addCriterion("JO_KEY_VALUE_TYPE like", value, "joKeyValueType");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueTypeNotLike(String value) {
            addCriterion("JO_KEY_VALUE_TYPE not like", value, "joKeyValueType");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueTypeIn(List<String> values) {
            addCriterion("JO_KEY_VALUE_TYPE in", values, "joKeyValueType");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueTypeNotIn(List<String> values) {
            addCriterion("JO_KEY_VALUE_TYPE not in", values, "joKeyValueType");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueTypeBetween(String value1, String value2) {
            addCriterion("JO_KEY_VALUE_TYPE between", value1, value2, "joKeyValueType");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueTypeNotBetween(String value1, String value2) {
            addCriterion("JO_KEY_VALUE_TYPE not between", value1, value2, "joKeyValueType");
            return (Criteria) this;
        }

        public Criteria andJoStatusIsNull() {
            addCriterion("JO_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andJoStatusIsNotNull() {
            addCriterion("JO_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andJoStatusEqualTo(String value) {
            addCriterion("JO_STATUS =", value, "joStatus");
            return (Criteria) this;
        }

        public Criteria andJoStatusNotEqualTo(String value) {
            addCriterion("JO_STATUS <>", value, "joStatus");
            return (Criteria) this;
        }

        public Criteria andJoStatusGreaterThan(String value) {
            addCriterion("JO_STATUS >", value, "joStatus");
            return (Criteria) this;
        }

        public Criteria andJoStatusGreaterThanOrEqualTo(String value) {
            addCriterion("JO_STATUS >=", value, "joStatus");
            return (Criteria) this;
        }

        public Criteria andJoStatusLessThan(String value) {
            addCriterion("JO_STATUS <", value, "joStatus");
            return (Criteria) this;
        }

        public Criteria andJoStatusLessThanOrEqualTo(String value) {
            addCriterion("JO_STATUS <=", value, "joStatus");
            return (Criteria) this;
        }

        public Criteria andJoStatusLike(String value) {
            addCriterion("JO_STATUS like", value, "joStatus");
            return (Criteria) this;
        }

        public Criteria andJoStatusNotLike(String value) {
            addCriterion("JO_STATUS not like", value, "joStatus");
            return (Criteria) this;
        }

        public Criteria andJoStatusIn(List<String> values) {
            addCriterion("JO_STATUS in", values, "joStatus");
            return (Criteria) this;
        }

        public Criteria andJoStatusNotIn(List<String> values) {
            addCriterion("JO_STATUS not in", values, "joStatus");
            return (Criteria) this;
        }

        public Criteria andJoStatusBetween(String value1, String value2) {
            addCriterion("JO_STATUS between", value1, value2, "joStatus");
            return (Criteria) this;
        }

        public Criteria andJoStatusNotBetween(String value1, String value2) {
            addCriterion("JO_STATUS not between", value1, value2, "joStatus");
            return (Criteria) this;
        }

        public Criteria andJoKeyBackNumIsNull() {
            addCriterion("JO_KEY_BACK_NUM is null");
            return (Criteria) this;
        }

        public Criteria andJoKeyBackNumIsNotNull() {
            addCriterion("JO_KEY_BACK_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andJoKeyBackNumEqualTo(String value) {
            addCriterion("JO_KEY_BACK_NUM =", value, "joKeyBackNum");
            return (Criteria) this;
        }

        public Criteria andJoKeyBackNumNotEqualTo(String value) {
            addCriterion("JO_KEY_BACK_NUM <>", value, "joKeyBackNum");
            return (Criteria) this;
        }

        public Criteria andJoKeyBackNumGreaterThan(String value) {
            addCriterion("JO_KEY_BACK_NUM >", value, "joKeyBackNum");
            return (Criteria) this;
        }

        public Criteria andJoKeyBackNumGreaterThanOrEqualTo(String value) {
            addCriterion("JO_KEY_BACK_NUM >=", value, "joKeyBackNum");
            return (Criteria) this;
        }

        public Criteria andJoKeyBackNumLessThan(String value) {
            addCriterion("JO_KEY_BACK_NUM <", value, "joKeyBackNum");
            return (Criteria) this;
        }

        public Criteria andJoKeyBackNumLessThanOrEqualTo(String value) {
            addCriterion("JO_KEY_BACK_NUM <=", value, "joKeyBackNum");
            return (Criteria) this;
        }

        public Criteria andJoKeyBackNumLike(String value) {
            addCriterion("JO_KEY_BACK_NUM like", value, "joKeyBackNum");
            return (Criteria) this;
        }

        public Criteria andJoKeyBackNumNotLike(String value) {
            addCriterion("JO_KEY_BACK_NUM not like", value, "joKeyBackNum");
            return (Criteria) this;
        }

        public Criteria andJoKeyBackNumIn(List<String> values) {
            addCriterion("JO_KEY_BACK_NUM in", values, "joKeyBackNum");
            return (Criteria) this;
        }

        public Criteria andJoKeyBackNumNotIn(List<String> values) {
            addCriterion("JO_KEY_BACK_NUM not in", values, "joKeyBackNum");
            return (Criteria) this;
        }

        public Criteria andJoKeyBackNumBetween(String value1, String value2) {
            addCriterion("JO_KEY_BACK_NUM between", value1, value2, "joKeyBackNum");
            return (Criteria) this;
        }

        public Criteria andJoKeyBackNumNotBetween(String value1, String value2) {
            addCriterion("JO_KEY_BACK_NUM not between", value1, value2, "joKeyBackNum");
            return (Criteria) this;
        }

        public Criteria andJoKeySortIsNull() {
            addCriterion("JO_KEY_SORT is null");
            return (Criteria) this;
        }

        public Criteria andJoKeySortIsNotNull() {
            addCriterion("JO_KEY_SORT is not null");
            return (Criteria) this;
        }

        public Criteria andJoKeySortEqualTo(BigDecimal value) {
            addCriterion("JO_KEY_SORT =", value, "joKeySort");
            return (Criteria) this;
        }

        public Criteria andJoKeySortNotEqualTo(BigDecimal value) {
            addCriterion("JO_KEY_SORT <>", value, "joKeySort");
            return (Criteria) this;
        }

        public Criteria andJoKeySortGreaterThan(BigDecimal value) {
            addCriterion("JO_KEY_SORT >", value, "joKeySort");
            return (Criteria) this;
        }

        public Criteria andJoKeySortGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("JO_KEY_SORT >=", value, "joKeySort");
            return (Criteria) this;
        }

        public Criteria andJoKeySortLessThan(BigDecimal value) {
            addCriterion("JO_KEY_SORT <", value, "joKeySort");
            return (Criteria) this;
        }

        public Criteria andJoKeySortLessThanOrEqualTo(BigDecimal value) {
            addCriterion("JO_KEY_SORT <=", value, "joKeySort");
            return (Criteria) this;
        }

        public Criteria andJoKeySortIn(List<BigDecimal> values) {
            addCriterion("JO_KEY_SORT in", values, "joKeySort");
            return (Criteria) this;
        }

        public Criteria andJoKeySortNotIn(List<BigDecimal> values) {
            addCriterion("JO_KEY_SORT not in", values, "joKeySort");
            return (Criteria) this;
        }

        public Criteria andJoKeySortBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("JO_KEY_SORT between", value1, value2, "joKeySort");
            return (Criteria) this;
        }

        public Criteria andJoKeySortNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("JO_KEY_SORT not between", value1, value2, "joKeySort");
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