package com.ryx.jobOrder.pojo;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JoExpandKeyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public JoExpandKeyExample() {
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

        public Criteria andJidIsNull() {
            addCriterion("JID is null");
            return (Criteria) this;
        }

        public Criteria andJidIsNotNull() {
            addCriterion("JID is not null");
            return (Criteria) this;
        }

        public Criteria andJidEqualTo(String value) {
            addCriterion("JID =", value, "jid");
            return (Criteria) this;
        }

        public Criteria andJidNotEqualTo(String value) {
            addCriterion("JID <>", value, "jid");
            return (Criteria) this;
        }

        public Criteria andJidGreaterThan(String value) {
            addCriterion("JID >", value, "jid");
            return (Criteria) this;
        }

        public Criteria andJidGreaterThanOrEqualTo(String value) {
            addCriterion("JID >=", value, "jid");
            return (Criteria) this;
        }

        public Criteria andJidLessThan(String value) {
            addCriterion("JID <", value, "jid");
            return (Criteria) this;
        }

        public Criteria andJidLessThanOrEqualTo(String value) {
            addCriterion("JID <=", value, "jid");
            return (Criteria) this;
        }

        public Criteria andJidLike(String value) {
            addCriterion("JID like", value, "jid");
            return (Criteria) this;
        }

        public Criteria andJidNotLike(String value) {
            addCriterion("JID not like", value, "jid");
            return (Criteria) this;
        }

        public Criteria andJidIn(List<String> values) {
            addCriterion("JID in", values, "jid");
            return (Criteria) this;
        }

        public Criteria andJidNotIn(List<String> values) {
            addCriterion("JID not in", values, "jid");
            return (Criteria) this;
        }

        public Criteria andJidBetween(String value1, String value2) {
            addCriterion("JID between", value1, value2, "jid");
            return (Criteria) this;
        }

        public Criteria andJidNotBetween(String value1, String value2) {
            addCriterion("JID not between", value1, value2, "jid");
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

        public Criteria andJoKeyValueIsNull() {
            addCriterion("JO_KEY_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueIsNotNull() {
            addCriterion("JO_KEY_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueEqualTo(String value) {
            addCriterion("JO_KEY_VALUE =", value, "joKeyValue");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueNotEqualTo(String value) {
            addCriterion("JO_KEY_VALUE <>", value, "joKeyValue");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueGreaterThan(String value) {
            addCriterion("JO_KEY_VALUE >", value, "joKeyValue");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueGreaterThanOrEqualTo(String value) {
            addCriterion("JO_KEY_VALUE >=", value, "joKeyValue");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueLessThan(String value) {
            addCriterion("JO_KEY_VALUE <", value, "joKeyValue");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueLessThanOrEqualTo(String value) {
            addCriterion("JO_KEY_VALUE <=", value, "joKeyValue");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueLike(String value) {
            addCriterion("JO_KEY_VALUE like", value, "joKeyValue");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueNotLike(String value) {
            addCriterion("JO_KEY_VALUE not like", value, "joKeyValue");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueIn(List<String> values) {
            addCriterion("JO_KEY_VALUE in", values, "joKeyValue");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueNotIn(List<String> values) {
            addCriterion("JO_KEY_VALUE not in", values, "joKeyValue");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueBetween(String value1, String value2) {
            addCriterion("JO_KEY_VALUE between", value1, value2, "joKeyValue");
            return (Criteria) this;
        }

        public Criteria andJoKeyValueNotBetween(String value1, String value2) {
            addCriterion("JO_KEY_VALUE not between", value1, value2, "joKeyValue");
            return (Criteria) this;
        }

        public Criteria andJoExpandSortIsNull() {
            addCriterion("JO_EXPAND_SORT is null");
            return (Criteria) this;
        }

        public Criteria andJoExpandSortIsNotNull() {
            addCriterion("JO_EXPAND_SORT is not null");
            return (Criteria) this;
        }

        public Criteria andJoExpandSortEqualTo(BigDecimal value) {
            addCriterion("JO_EXPAND_SORT =", value, "joExpandSort");
            return (Criteria) this;
        }

        public Criteria andJoExpandSortNotEqualTo(BigDecimal value) {
            addCriterion("JO_EXPAND_SORT <>", value, "joExpandSort");
            return (Criteria) this;
        }

        public Criteria andJoExpandSortGreaterThan(BigDecimal value) {
            addCriterion("JO_EXPAND_SORT >", value, "joExpandSort");
            return (Criteria) this;
        }

        public Criteria andJoExpandSortGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("JO_EXPAND_SORT >=", value, "joExpandSort");
            return (Criteria) this;
        }

        public Criteria andJoExpandSortLessThan(BigDecimal value) {
            addCriterion("JO_EXPAND_SORT <", value, "joExpandSort");
            return (Criteria) this;
        }

        public Criteria andJoExpandSortLessThanOrEqualTo(BigDecimal value) {
            addCriterion("JO_EXPAND_SORT <=", value, "joExpandSort");
            return (Criteria) this;
        }

        public Criteria andJoExpandSortIn(List<BigDecimal> values) {
            addCriterion("JO_EXPAND_SORT in", values, "joExpandSort");
            return (Criteria) this;
        }

        public Criteria andJoExpandSortNotIn(List<BigDecimal> values) {
            addCriterion("JO_EXPAND_SORT not in", values, "joExpandSort");
            return (Criteria) this;
        }

        public Criteria andJoExpandSortBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("JO_EXPAND_SORT between", value1, value2, "joExpandSort");
            return (Criteria) this;
        }

        public Criteria andJoExpandSortNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("JO_EXPAND_SORT not between", value1, value2, "joExpandSort");
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