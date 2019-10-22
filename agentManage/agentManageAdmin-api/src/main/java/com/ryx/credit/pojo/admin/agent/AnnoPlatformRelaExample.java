package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;
import java.util.ArrayList;
import java.util.List;

public class AnnoPlatformRelaExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AnnoPlatformRelaExample() {
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

        public Criteria andAnnoIdIsNull() {
            addCriterion("ANNO_ID is null");
            return (Criteria) this;
        }

        public Criteria andAnnoIdIsNotNull() {
            addCriterion("ANNO_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAnnoIdEqualTo(String value) {
            addCriterion("ANNO_ID =", value, "annoId");
            return (Criteria) this;
        }

        public Criteria andAnnoIdNotEqualTo(String value) {
            addCriterion("ANNO_ID <>", value, "annoId");
            return (Criteria) this;
        }

        public Criteria andAnnoIdGreaterThan(String value) {
            addCriterion("ANNO_ID >", value, "annoId");
            return (Criteria) this;
        }

        public Criteria andAnnoIdGreaterThanOrEqualTo(String value) {
            addCriterion("ANNO_ID >=", value, "annoId");
            return (Criteria) this;
        }

        public Criteria andAnnoIdLessThan(String value) {
            addCriterion("ANNO_ID <", value, "annoId");
            return (Criteria) this;
        }

        public Criteria andAnnoIdLessThanOrEqualTo(String value) {
            addCriterion("ANNO_ID <=", value, "annoId");
            return (Criteria) this;
        }

        public Criteria andAnnoIdLike(String value) {
            addCriterion("ANNO_ID like", value, "annoId");
            return (Criteria) this;
        }

        public Criteria andAnnoIdNotLike(String value) {
            addCriterion("ANNO_ID not like", value, "annoId");
            return (Criteria) this;
        }

        public Criteria andAnnoIdIn(List<String> values) {
            addCriterion("ANNO_ID in", values, "annoId");
            return (Criteria) this;
        }

        public Criteria andAnnoIdNotIn(List<String> values) {
            addCriterion("ANNO_ID not in", values, "annoId");
            return (Criteria) this;
        }

        public Criteria andAnnoIdBetween(String value1, String value2) {
            addCriterion("ANNO_ID between", value1, value2, "annoId");
            return (Criteria) this;
        }

        public Criteria andAnnoIdNotBetween(String value1, String value2) {
            addCriterion("ANNO_ID not between", value1, value2, "annoId");
            return (Criteria) this;
        }

        public Criteria andRangTypeIsNull() {
            addCriterion("RANG_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andRangTypeIsNotNull() {
            addCriterion("RANG_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andRangTypeEqualTo(String value) {
            addCriterion("RANG_TYPE =", value, "rangType");
            return (Criteria) this;
        }

        public Criteria andRangTypeNotEqualTo(String value) {
            addCriterion("RANG_TYPE <>", value, "rangType");
            return (Criteria) this;
        }

        public Criteria andRangTypeGreaterThan(String value) {
            addCriterion("RANG_TYPE >", value, "rangType");
            return (Criteria) this;
        }

        public Criteria andRangTypeGreaterThanOrEqualTo(String value) {
            addCriterion("RANG_TYPE >=", value, "rangType");
            return (Criteria) this;
        }

        public Criteria andRangTypeLessThan(String value) {
            addCriterion("RANG_TYPE <", value, "rangType");
            return (Criteria) this;
        }

        public Criteria andRangTypeLessThanOrEqualTo(String value) {
            addCriterion("RANG_TYPE <=", value, "rangType");
            return (Criteria) this;
        }

        public Criteria andRangTypeLike(String value) {
            addCriterion("RANG_TYPE like", value, "rangType");
            return (Criteria) this;
        }

        public Criteria andRangTypeNotLike(String value) {
            addCriterion("RANG_TYPE not like", value, "rangType");
            return (Criteria) this;
        }

        public Criteria andRangTypeIn(List<String> values) {
            addCriterion("RANG_TYPE in", values, "rangType");
            return (Criteria) this;
        }

        public Criteria andRangTypeNotIn(List<String> values) {
            addCriterion("RANG_TYPE not in", values, "rangType");
            return (Criteria) this;
        }

        public Criteria andRangTypeBetween(String value1, String value2) {
            addCriterion("RANG_TYPE between", value1, value2, "rangType");
            return (Criteria) this;
        }

        public Criteria andRangTypeNotBetween(String value1, String value2) {
            addCriterion("RANG_TYPE not between", value1, value2, "rangType");
            return (Criteria) this;
        }

        public Criteria andRangValueIsNull() {
            addCriterion("RANG_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andRangValueIsNotNull() {
            addCriterion("RANG_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andRangValueEqualTo(String value) {
            addCriterion("RANG_VALUE =", value, "rangValue");
            return (Criteria) this;
        }

        public Criteria andRangValueNotEqualTo(String value) {
            addCriterion("RANG_VALUE <>", value, "rangValue");
            return (Criteria) this;
        }

        public Criteria andRangValueGreaterThan(String value) {
            addCriterion("RANG_VALUE >", value, "rangValue");
            return (Criteria) this;
        }

        public Criteria andRangValueGreaterThanOrEqualTo(String value) {
            addCriterion("RANG_VALUE >=", value, "rangValue");
            return (Criteria) this;
        }

        public Criteria andRangValueLessThan(String value) {
            addCriterion("RANG_VALUE <", value, "rangValue");
            return (Criteria) this;
        }

        public Criteria andRangValueLessThanOrEqualTo(String value) {
            addCriterion("RANG_VALUE <=", value, "rangValue");
            return (Criteria) this;
        }

        public Criteria andRangValueLike(String value) {
            addCriterion("RANG_VALUE like", value, "rangValue");
            return (Criteria) this;
        }

        public Criteria andRangValueNotLike(String value) {
            addCriterion("RANG_VALUE not like", value, "rangValue");
            return (Criteria) this;
        }

        public Criteria andRangValueIn(List<String> values) {
            addCriterion("RANG_VALUE in", values, "rangValue");
            return (Criteria) this;
        }

        public Criteria andRangValueNotIn(List<String> values) {
            addCriterion("RANG_VALUE not in", values, "rangValue");
            return (Criteria) this;
        }

        public Criteria andRangValueBetween(String value1, String value2) {
            addCriterion("RANG_VALUE between", value1, value2, "rangValue");
            return (Criteria) this;
        }

        public Criteria andRangValueNotBetween(String value1, String value2) {
            addCriterion("RANG_VALUE not between", value1, value2, "rangValue");
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