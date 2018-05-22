package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DictExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public DictExample() {
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

        public Criteria andDGroupIsNull() {
            addCriterion("D_GROUP is null");
            return (Criteria) this;
        }

        public Criteria andDGroupIsNotNull() {
            addCriterion("D_GROUP is not null");
            return (Criteria) this;
        }

        public Criteria andDGroupEqualTo(String value) {
            addCriterion("D_GROUP =", value, "dGroup");
            return (Criteria) this;
        }

        public Criteria andDGroupNotEqualTo(String value) {
            addCriterion("D_GROUP <>", value, "dGroup");
            return (Criteria) this;
        }

        public Criteria andDGroupGreaterThan(String value) {
            addCriterion("D_GROUP >", value, "dGroup");
            return (Criteria) this;
        }

        public Criteria andDGroupGreaterThanOrEqualTo(String value) {
            addCriterion("D_GROUP >=", value, "dGroup");
            return (Criteria) this;
        }

        public Criteria andDGroupLessThan(String value) {
            addCriterion("D_GROUP <", value, "dGroup");
            return (Criteria) this;
        }

        public Criteria andDGroupLessThanOrEqualTo(String value) {
            addCriterion("D_GROUP <=", value, "dGroup");
            return (Criteria) this;
        }

        public Criteria andDGroupLike(String value) {
            addCriterion("D_GROUP like", value, "dGroup");
            return (Criteria) this;
        }

        public Criteria andDGroupNotLike(String value) {
            addCriterion("D_GROUP not like", value, "dGroup");
            return (Criteria) this;
        }

        public Criteria andDGroupIn(List<String> values) {
            addCriterion("D_GROUP in", values, "dGroup");
            return (Criteria) this;
        }

        public Criteria andDGroupNotIn(List<String> values) {
            addCriterion("D_GROUP not in", values, "dGroup");
            return (Criteria) this;
        }

        public Criteria andDGroupBetween(String value1, String value2) {
            addCriterion("D_GROUP between", value1, value2, "dGroup");
            return (Criteria) this;
        }

        public Criteria andDGroupNotBetween(String value1, String value2) {
            addCriterion("D_GROUP not between", value1, value2, "dGroup");
            return (Criteria) this;
        }

        public Criteria andDArtifactIsNull() {
            addCriterion("D_ARTIFACT is null");
            return (Criteria) this;
        }

        public Criteria andDArtifactIsNotNull() {
            addCriterion("D_ARTIFACT is not null");
            return (Criteria) this;
        }

        public Criteria andDArtifactEqualTo(String value) {
            addCriterion("D_ARTIFACT =", value, "dArtifact");
            return (Criteria) this;
        }

        public Criteria andDArtifactNotEqualTo(String value) {
            addCriterion("D_ARTIFACT <>", value, "dArtifact");
            return (Criteria) this;
        }

        public Criteria andDArtifactGreaterThan(String value) {
            addCriterion("D_ARTIFACT >", value, "dArtifact");
            return (Criteria) this;
        }

        public Criteria andDArtifactGreaterThanOrEqualTo(String value) {
            addCriterion("D_ARTIFACT >=", value, "dArtifact");
            return (Criteria) this;
        }

        public Criteria andDArtifactLessThan(String value) {
            addCriterion("D_ARTIFACT <", value, "dArtifact");
            return (Criteria) this;
        }

        public Criteria andDArtifactLessThanOrEqualTo(String value) {
            addCriterion("D_ARTIFACT <=", value, "dArtifact");
            return (Criteria) this;
        }

        public Criteria andDArtifactLike(String value) {
            addCriterion("D_ARTIFACT like", value, "dArtifact");
            return (Criteria) this;
        }

        public Criteria andDArtifactNotLike(String value) {
            addCriterion("D_ARTIFACT not like", value, "dArtifact");
            return (Criteria) this;
        }

        public Criteria andDArtifactIn(List<String> values) {
            addCriterion("D_ARTIFACT in", values, "dArtifact");
            return (Criteria) this;
        }

        public Criteria andDArtifactNotIn(List<String> values) {
            addCriterion("D_ARTIFACT not in", values, "dArtifact");
            return (Criteria) this;
        }

        public Criteria andDArtifactBetween(String value1, String value2) {
            addCriterion("D_ARTIFACT between", value1, value2, "dArtifact");
            return (Criteria) this;
        }

        public Criteria andDArtifactNotBetween(String value1, String value2) {
            addCriterion("D_ARTIFACT not between", value1, value2, "dArtifact");
            return (Criteria) this;
        }

        public Criteria andDItemvalueIsNull() {
            addCriterion("D_ITEMVALUE is null");
            return (Criteria) this;
        }

        public Criteria andDItemvalueIsNotNull() {
            addCriterion("D_ITEMVALUE is not null");
            return (Criteria) this;
        }

        public Criteria andDItemvalueEqualTo(String value) {
            addCriterion("D_ITEMVALUE =", value, "dItemvalue");
            return (Criteria) this;
        }

        public Criteria andDItemvalueNotEqualTo(String value) {
            addCriterion("D_ITEMVALUE <>", value, "dItemvalue");
            return (Criteria) this;
        }

        public Criteria andDItemvalueGreaterThan(String value) {
            addCriterion("D_ITEMVALUE >", value, "dItemvalue");
            return (Criteria) this;
        }

        public Criteria andDItemvalueGreaterThanOrEqualTo(String value) {
            addCriterion("D_ITEMVALUE >=", value, "dItemvalue");
            return (Criteria) this;
        }

        public Criteria andDItemvalueLessThan(String value) {
            addCriterion("D_ITEMVALUE <", value, "dItemvalue");
            return (Criteria) this;
        }

        public Criteria andDItemvalueLessThanOrEqualTo(String value) {
            addCriterion("D_ITEMVALUE <=", value, "dItemvalue");
            return (Criteria) this;
        }

        public Criteria andDItemvalueLike(String value) {
            addCriterion("D_ITEMVALUE like", value, "dItemvalue");
            return (Criteria) this;
        }

        public Criteria andDItemvalueNotLike(String value) {
            addCriterion("D_ITEMVALUE not like", value, "dItemvalue");
            return (Criteria) this;
        }

        public Criteria andDItemvalueIn(List<String> values) {
            addCriterion("D_ITEMVALUE in", values, "dItemvalue");
            return (Criteria) this;
        }

        public Criteria andDItemvalueNotIn(List<String> values) {
            addCriterion("D_ITEMVALUE not in", values, "dItemvalue");
            return (Criteria) this;
        }

        public Criteria andDItemvalueBetween(String value1, String value2) {
            addCriterion("D_ITEMVALUE between", value1, value2, "dItemvalue");
            return (Criteria) this;
        }

        public Criteria andDItemvalueNotBetween(String value1, String value2) {
            addCriterion("D_ITEMVALUE not between", value1, value2, "dItemvalue");
            return (Criteria) this;
        }

        public Criteria andDItemnameIsNull() {
            addCriterion("D_ITEMNAME is null");
            return (Criteria) this;
        }

        public Criteria andDItemnameIsNotNull() {
            addCriterion("D_ITEMNAME is not null");
            return (Criteria) this;
        }

        public Criteria andDItemnameEqualTo(String value) {
            addCriterion("D_ITEMNAME =", value, "dItemname");
            return (Criteria) this;
        }

        public Criteria andDItemnameNotEqualTo(String value) {
            addCriterion("D_ITEMNAME <>", value, "dItemname");
            return (Criteria) this;
        }

        public Criteria andDItemnameGreaterThan(String value) {
            addCriterion("D_ITEMNAME >", value, "dItemname");
            return (Criteria) this;
        }

        public Criteria andDItemnameGreaterThanOrEqualTo(String value) {
            addCriterion("D_ITEMNAME >=", value, "dItemname");
            return (Criteria) this;
        }

        public Criteria andDItemnameLessThan(String value) {
            addCriterion("D_ITEMNAME <", value, "dItemname");
            return (Criteria) this;
        }

        public Criteria andDItemnameLessThanOrEqualTo(String value) {
            addCriterion("D_ITEMNAME <=", value, "dItemname");
            return (Criteria) this;
        }

        public Criteria andDItemnameLike(String value) {
            addCriterion("D_ITEMNAME like", value, "dItemname");
            return (Criteria) this;
        }

        public Criteria andDItemnameNotLike(String value) {
            addCriterion("D_ITEMNAME not like", value, "dItemname");
            return (Criteria) this;
        }

        public Criteria andDItemnameIn(List<String> values) {
            addCriterion("D_ITEMNAME in", values, "dItemname");
            return (Criteria) this;
        }

        public Criteria andDItemnameNotIn(List<String> values) {
            addCriterion("D_ITEMNAME not in", values, "dItemname");
            return (Criteria) this;
        }

        public Criteria andDItemnameBetween(String value1, String value2) {
            addCriterion("D_ITEMNAME between", value1, value2, "dItemname");
            return (Criteria) this;
        }

        public Criteria andDItemnameNotBetween(String value1, String value2) {
            addCriterion("D_ITEMNAME not between", value1, value2, "dItemname");
            return (Criteria) this;
        }

        public Criteria andDItemnremarkIsNull() {
            addCriterion("D_ITEMNREMARK is null");
            return (Criteria) this;
        }

        public Criteria andDItemnremarkIsNotNull() {
            addCriterion("D_ITEMNREMARK is not null");
            return (Criteria) this;
        }

        public Criteria andDItemnremarkEqualTo(String value) {
            addCriterion("D_ITEMNREMARK =", value, "dItemnremark");
            return (Criteria) this;
        }

        public Criteria andDItemnremarkNotEqualTo(String value) {
            addCriterion("D_ITEMNREMARK <>", value, "dItemnremark");
            return (Criteria) this;
        }

        public Criteria andDItemnremarkGreaterThan(String value) {
            addCriterion("D_ITEMNREMARK >", value, "dItemnremark");
            return (Criteria) this;
        }

        public Criteria andDItemnremarkGreaterThanOrEqualTo(String value) {
            addCriterion("D_ITEMNREMARK >=", value, "dItemnremark");
            return (Criteria) this;
        }

        public Criteria andDItemnremarkLessThan(String value) {
            addCriterion("D_ITEMNREMARK <", value, "dItemnremark");
            return (Criteria) this;
        }

        public Criteria andDItemnremarkLessThanOrEqualTo(String value) {
            addCriterion("D_ITEMNREMARK <=", value, "dItemnremark");
            return (Criteria) this;
        }

        public Criteria andDItemnremarkLike(String value) {
            addCriterion("D_ITEMNREMARK like", value, "dItemnremark");
            return (Criteria) this;
        }

        public Criteria andDItemnremarkNotLike(String value) {
            addCriterion("D_ITEMNREMARK not like", value, "dItemnremark");
            return (Criteria) this;
        }

        public Criteria andDItemnremarkIn(List<String> values) {
            addCriterion("D_ITEMNREMARK in", values, "dItemnremark");
            return (Criteria) this;
        }

        public Criteria andDItemnremarkNotIn(List<String> values) {
            addCriterion("D_ITEMNREMARK not in", values, "dItemnremark");
            return (Criteria) this;
        }

        public Criteria andDItemnremarkBetween(String value1, String value2) {
            addCriterion("D_ITEMNREMARK between", value1, value2, "dItemnremark");
            return (Criteria) this;
        }

        public Criteria andDItemnremarkNotBetween(String value1, String value2) {
            addCriterion("D_ITEMNREMARK not between", value1, value2, "dItemnremark");
            return (Criteria) this;
        }

        public Criteria andDSortIsNull() {
            addCriterion("D_SORT is null");
            return (Criteria) this;
        }

        public Criteria andDSortIsNotNull() {
            addCriterion("D_SORT is not null");
            return (Criteria) this;
        }

        public Criteria andDSortEqualTo(BigDecimal value) {
            addCriterion("D_SORT =", value, "dSort");
            return (Criteria) this;
        }

        public Criteria andDSortNotEqualTo(BigDecimal value) {
            addCriterion("D_SORT <>", value, "dSort");
            return (Criteria) this;
        }

        public Criteria andDSortGreaterThan(BigDecimal value) {
            addCriterion("D_SORT >", value, "dSort");
            return (Criteria) this;
        }

        public Criteria andDSortGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("D_SORT >=", value, "dSort");
            return (Criteria) this;
        }

        public Criteria andDSortLessThan(BigDecimal value) {
            addCriterion("D_SORT <", value, "dSort");
            return (Criteria) this;
        }

        public Criteria andDSortLessThanOrEqualTo(BigDecimal value) {
            addCriterion("D_SORT <=", value, "dSort");
            return (Criteria) this;
        }

        public Criteria andDSortIn(List<BigDecimal> values) {
            addCriterion("D_SORT in", values, "dSort");
            return (Criteria) this;
        }

        public Criteria andDSortNotIn(List<BigDecimal> values) {
            addCriterion("D_SORT not in", values, "dSort");
            return (Criteria) this;
        }

        public Criteria andDSortBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("D_SORT between", value1, value2, "dSort");
            return (Criteria) this;
        }

        public Criteria andDSortNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("D_SORT not between", value1, value2, "dSort");
            return (Criteria) this;
        }

        public Criteria andDStatusIsNull() {
            addCriterion("D_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andDStatusIsNotNull() {
            addCriterion("D_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andDStatusEqualTo(BigDecimal value) {
            addCriterion("D_STATUS =", value, "dStatus");
            return (Criteria) this;
        }

        public Criteria andDStatusNotEqualTo(BigDecimal value) {
            addCriterion("D_STATUS <>", value, "dStatus");
            return (Criteria) this;
        }

        public Criteria andDStatusGreaterThan(BigDecimal value) {
            addCriterion("D_STATUS >", value, "dStatus");
            return (Criteria) this;
        }

        public Criteria andDStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("D_STATUS >=", value, "dStatus");
            return (Criteria) this;
        }

        public Criteria andDStatusLessThan(BigDecimal value) {
            addCriterion("D_STATUS <", value, "dStatus");
            return (Criteria) this;
        }

        public Criteria andDStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("D_STATUS <=", value, "dStatus");
            return (Criteria) this;
        }

        public Criteria andDStatusIn(List<BigDecimal> values) {
            addCriterion("D_STATUS in", values, "dStatus");
            return (Criteria) this;
        }

        public Criteria andDStatusNotIn(List<BigDecimal> values) {
            addCriterion("D_STATUS not in", values, "dStatus");
            return (Criteria) this;
        }

        public Criteria andDStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("D_STATUS between", value1, value2, "dStatus");
            return (Criteria) this;
        }

        public Criteria andDStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("D_STATUS not between", value1, value2, "dStatus");
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