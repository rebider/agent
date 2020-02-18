package com.ryx.jobOrder.pojo;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JoCustomKeyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public JoCustomKeyExample() {
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

        public Criteria andJoFirstKeyNumIsNull() {
            addCriterion("JO_FIRST_KEY_NUM is null");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumIsNotNull() {
            addCriterion("JO_FIRST_KEY_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumEqualTo(String value) {
            addCriterion("JO_FIRST_KEY_NUM =", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumNotEqualTo(String value) {
            addCriterion("JO_FIRST_KEY_NUM <>", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumGreaterThan(String value) {
            addCriterion("JO_FIRST_KEY_NUM >", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumGreaterThanOrEqualTo(String value) {
            addCriterion("JO_FIRST_KEY_NUM >=", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumLessThan(String value) {
            addCriterion("JO_FIRST_KEY_NUM <", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumLessThanOrEqualTo(String value) {
            addCriterion("JO_FIRST_KEY_NUM <=", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumLike(String value) {
            addCriterion("JO_FIRST_KEY_NUM like", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumNotLike(String value) {
            addCriterion("JO_FIRST_KEY_NUM not like", value, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumIn(List<String> values) {
            addCriterion("JO_FIRST_KEY_NUM in", values, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumNotIn(List<String> values) {
            addCriterion("JO_FIRST_KEY_NUM not in", values, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumBetween(String value1, String value2) {
            addCriterion("JO_FIRST_KEY_NUM between", value1, value2, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoFirstKeyNumNotBetween(String value1, String value2) {
            addCriterion("JO_FIRST_KEY_NUM not between", value1, value2, "joFirstKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumIsNull() {
            addCriterion("JO_SECOND_KEY_NUM is null");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumIsNotNull() {
            addCriterion("JO_SECOND_KEY_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumEqualTo(String value) {
            addCriterion("JO_SECOND_KEY_NUM =", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumNotEqualTo(String value) {
            addCriterion("JO_SECOND_KEY_NUM <>", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumGreaterThan(String value) {
            addCriterion("JO_SECOND_KEY_NUM >", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumGreaterThanOrEqualTo(String value) {
            addCriterion("JO_SECOND_KEY_NUM >=", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumLessThan(String value) {
            addCriterion("JO_SECOND_KEY_NUM <", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumLessThanOrEqualTo(String value) {
            addCriterion("JO_SECOND_KEY_NUM <=", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumLike(String value) {
            addCriterion("JO_SECOND_KEY_NUM like", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumNotLike(String value) {
            addCriterion("JO_SECOND_KEY_NUM not like", value, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumIn(List<String> values) {
            addCriterion("JO_SECOND_KEY_NUM in", values, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumNotIn(List<String> values) {
            addCriterion("JO_SECOND_KEY_NUM not in", values, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumBetween(String value1, String value2) {
            addCriterion("JO_SECOND_KEY_NUM between", value1, value2, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoSecondKeyNumNotBetween(String value1, String value2) {
            addCriterion("JO_SECOND_KEY_NUM not between", value1, value2, "joSecondKeyNum");
            return (Criteria) this;
        }

        public Criteria andJoKeyIdIsNull() {
            addCriterion("JO_KEY_ID is null");
            return (Criteria) this;
        }

        public Criteria andJoKeyIdIsNotNull() {
            addCriterion("JO_KEY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andJoKeyIdEqualTo(String value) {
            addCriterion("JO_KEY_ID =", value, "joKeyId");
            return (Criteria) this;
        }

        public Criteria andJoKeyIdNotEqualTo(String value) {
            addCriterion("JO_KEY_ID <>", value, "joKeyId");
            return (Criteria) this;
        }

        public Criteria andJoKeyIdGreaterThan(String value) {
            addCriterion("JO_KEY_ID >", value, "joKeyId");
            return (Criteria) this;
        }

        public Criteria andJoKeyIdGreaterThanOrEqualTo(String value) {
            addCriterion("JO_KEY_ID >=", value, "joKeyId");
            return (Criteria) this;
        }

        public Criteria andJoKeyIdLessThan(String value) {
            addCriterion("JO_KEY_ID <", value, "joKeyId");
            return (Criteria) this;
        }

        public Criteria andJoKeyIdLessThanOrEqualTo(String value) {
            addCriterion("JO_KEY_ID <=", value, "joKeyId");
            return (Criteria) this;
        }

        public Criteria andJoKeyIdLike(String value) {
            addCriterion("JO_KEY_ID like", value, "joKeyId");
            return (Criteria) this;
        }

        public Criteria andJoKeyIdNotLike(String value) {
            addCriterion("JO_KEY_ID not like", value, "joKeyId");
            return (Criteria) this;
        }

        public Criteria andJoKeyIdIn(List<String> values) {
            addCriterion("JO_KEY_ID in", values, "joKeyId");
            return (Criteria) this;
        }

        public Criteria andJoKeyIdNotIn(List<String> values) {
            addCriterion("JO_KEY_ID not in", values, "joKeyId");
            return (Criteria) this;
        }

        public Criteria andJoKeyIdBetween(String value1, String value2) {
            addCriterion("JO_KEY_ID between", value1, value2, "joKeyId");
            return (Criteria) this;
        }

        public Criteria andJoKeyIdNotBetween(String value1, String value2) {
            addCriterion("JO_KEY_ID not between", value1, value2, "joKeyId");
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

        public Criteria andJoKeyNullIsNull() {
            addCriterion("JO_KEY_NULL is null");
            return (Criteria) this;
        }

        public Criteria andJoKeyNullIsNotNull() {
            addCriterion("JO_KEY_NULL is not null");
            return (Criteria) this;
        }

        public Criteria andJoKeyNullEqualTo(String value) {
            addCriterion("JO_KEY_NULL =", value, "joKeyNull");
            return (Criteria) this;
        }

        public Criteria andJoKeyNullNotEqualTo(String value) {
            addCriterion("JO_KEY_NULL <>", value, "joKeyNull");
            return (Criteria) this;
        }

        public Criteria andJoKeyNullGreaterThan(String value) {
            addCriterion("JO_KEY_NULL >", value, "joKeyNull");
            return (Criteria) this;
        }

        public Criteria andJoKeyNullGreaterThanOrEqualTo(String value) {
            addCriterion("JO_KEY_NULL >=", value, "joKeyNull");
            return (Criteria) this;
        }

        public Criteria andJoKeyNullLessThan(String value) {
            addCriterion("JO_KEY_NULL <", value, "joKeyNull");
            return (Criteria) this;
        }

        public Criteria andJoKeyNullLessThanOrEqualTo(String value) {
            addCriterion("JO_KEY_NULL <=", value, "joKeyNull");
            return (Criteria) this;
        }

        public Criteria andJoKeyNullLike(String value) {
            addCriterion("JO_KEY_NULL like", value, "joKeyNull");
            return (Criteria) this;
        }

        public Criteria andJoKeyNullNotLike(String value) {
            addCriterion("JO_KEY_NULL not like", value, "joKeyNull");
            return (Criteria) this;
        }

        public Criteria andJoKeyNullIn(List<String> values) {
            addCriterion("JO_KEY_NULL in", values, "joKeyNull");
            return (Criteria) this;
        }

        public Criteria andJoKeyNullNotIn(List<String> values) {
            addCriterion("JO_KEY_NULL not in", values, "joKeyNull");
            return (Criteria) this;
        }

        public Criteria andJoKeyNullBetween(String value1, String value2) {
            addCriterion("JO_KEY_NULL between", value1, value2, "joKeyNull");
            return (Criteria) this;
        }

        public Criteria andJoKeyNullNotBetween(String value1, String value2) {
            addCriterion("JO_KEY_NULL not between", value1, value2, "joKeyNull");
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