package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FreezeAgentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public FreezeAgentExample() {
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

        public Criteria andAgentNameIsNull() {
            addCriterion("AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAgentNameIsNotNull() {
            addCriterion("AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAgentNameEqualTo(String value) {
            addCriterion("AGENT_NAME =", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotEqualTo(String value) {
            addCriterion("AGENT_NAME <>", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThan(String value) {
            addCriterion("AGENT_NAME >", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_NAME >=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThan(String value) {
            addCriterion("AGENT_NAME <", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThanOrEqualTo(String value) {
            addCriterion("AGENT_NAME <=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLike(String value) {
            addCriterion("AGENT_NAME like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotLike(String value) {
            addCriterion("AGENT_NAME not like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameIn(List<String> values) {
            addCriterion("AGENT_NAME in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotIn(List<String> values) {
            addCriterion("AGENT_NAME not in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameBetween(String value1, String value2) {
            addCriterion("AGENT_NAME between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotBetween(String value1, String value2) {
            addCriterion("AGENT_NAME not between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNull() {
            addCriterion("AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNotNull() {
            addCriterion("AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentIdEqualTo(String value) {
            addCriterion("AGENT_ID =", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotEqualTo(String value) {
            addCriterion("AGENT_ID <>", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThan(String value) {
            addCriterion("AGENT_ID >", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_ID >=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThan(String value) {
            addCriterion("AGENT_ID <", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThanOrEqualTo(String value) {
            addCriterion("AGENT_ID <=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLike(String value) {
            addCriterion("AGENT_ID like", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotLike(String value) {
            addCriterion("AGENT_ID not like", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdIn(List<String> values) {
            addCriterion("AGENT_ID in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotIn(List<String> values) {
            addCriterion("AGENT_ID not in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdBetween(String value1, String value2) {
            addCriterion("AGENT_ID between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotBetween(String value1, String value2) {
            addCriterion("AGENT_ID not between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameIsNull() {
            addCriterion("PARENT_AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameIsNotNull() {
            addCriterion("PARENT_AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME =", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME <>", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameGreaterThan(String value) {
            addCriterion("PARENT_AGENT_NAME >", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME >=", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameLessThan(String value) {
            addCriterion("PARENT_AGENT_NAME <", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameLessThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME <=", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameLike(String value) {
            addCriterion("PARENT_AGENT_NAME like", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotLike(String value) {
            addCriterion("PARENT_AGENT_NAME not like", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameIn(List<String> values) {
            addCriterion("PARENT_AGENT_NAME in", values, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotIn(List<String> values) {
            addCriterion("PARENT_AGENT_NAME not in", values, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_NAME between", value1, value2, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_NAME not between", value1, value2, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdIsNull() {
            addCriterion("PARENT_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdIsNotNull() {
            addCriterion("PARENT_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID =", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID <>", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdGreaterThan(String value) {
            addCriterion("PARENT_AGENT_ID >", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID >=", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdLessThan(String value) {
            addCriterion("PARENT_AGENT_ID <", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdLessThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID <=", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdLike(String value) {
            addCriterion("PARENT_AGENT_ID like", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotLike(String value) {
            addCriterion("PARENT_AGENT_ID not like", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdIn(List<String> values) {
            addCriterion("PARENT_AGENT_ID in", values, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotIn(List<String> values) {
            addCriterion("PARENT_AGENT_ID not in", values, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_ID between", value1, value2, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_ID not between", value1, value2, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeIsNull() {
            addCriterion("FREEZE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeIsNotNull() {
            addCriterion("FREEZE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeEqualTo(String value) {
            addCriterion("FREEZE_TYPE =", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeNotEqualTo(String value) {
            addCriterion("FREEZE_TYPE <>", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeGreaterThan(String value) {
            addCriterion("FREEZE_TYPE >", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_TYPE >=", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeLessThan(String value) {
            addCriterion("FREEZE_TYPE <", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_TYPE <=", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeLike(String value) {
            addCriterion("FREEZE_TYPE like", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeNotLike(String value) {
            addCriterion("FREEZE_TYPE not like", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeIn(List<String> values) {
            addCriterion("FREEZE_TYPE in", values, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeNotIn(List<String> values) {
            addCriterion("FREEZE_TYPE not in", values, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeBetween(String value1, String value2) {
            addCriterion("FREEZE_TYPE between", value1, value2, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeNotBetween(String value1, String value2) {
            addCriterion("FREEZE_TYPE not between", value1, value2, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtIsNull() {
            addCriterion("FREEZE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtIsNotNull() {
            addCriterion("FREEZE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtEqualTo(BigDecimal value) {
            addCriterion("FREEZE_AMT =", value, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtNotEqualTo(BigDecimal value) {
            addCriterion("FREEZE_AMT <>", value, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtGreaterThan(BigDecimal value) {
            addCriterion("FREEZE_AMT >", value, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FREEZE_AMT >=", value, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtLessThan(BigDecimal value) {
            addCriterion("FREEZE_AMT <", value, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FREEZE_AMT <=", value, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtIn(List<BigDecimal> values) {
            addCriterion("FREEZE_AMT in", values, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtNotIn(List<BigDecimal> values) {
            addCriterion("FREEZE_AMT not in", values, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FREEZE_AMT between", value1, value2, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andFreezeAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FREEZE_AMT not between", value1, value2, "freezeAmt");
            return (Criteria) this;
        }

        public Criteria andRev1IsNull() {
            addCriterion("REV1 is null");
            return (Criteria) this;
        }

        public Criteria andRev1IsNotNull() {
            addCriterion("REV1 is not null");
            return (Criteria) this;
        }

        public Criteria andRev1EqualTo(String value) {
            addCriterion("REV1 =", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotEqualTo(String value) {
            addCriterion("REV1 <>", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1GreaterThan(String value) {
            addCriterion("REV1 >", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1GreaterThanOrEqualTo(String value) {
            addCriterion("REV1 >=", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1LessThan(String value) {
            addCriterion("REV1 <", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1LessThanOrEqualTo(String value) {
            addCriterion("REV1 <=", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1Like(String value) {
            addCriterion("REV1 like", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotLike(String value) {
            addCriterion("REV1 not like", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1In(List<String> values) {
            addCriterion("REV1 in", values, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotIn(List<String> values) {
            addCriterion("REV1 not in", values, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1Between(String value1, String value2) {
            addCriterion("REV1 between", value1, value2, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotBetween(String value1, String value2) {
            addCriterion("REV1 not between", value1, value2, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev2IsNull() {
            addCriterion("REV2 is null");
            return (Criteria) this;
        }

        public Criteria andRev2IsNotNull() {
            addCriterion("REV2 is not null");
            return (Criteria) this;
        }

        public Criteria andRev2EqualTo(String value) {
            addCriterion("REV2 =", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotEqualTo(String value) {
            addCriterion("REV2 <>", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2GreaterThan(String value) {
            addCriterion("REV2 >", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2GreaterThanOrEqualTo(String value) {
            addCriterion("REV2 >=", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2LessThan(String value) {
            addCriterion("REV2 <", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2LessThanOrEqualTo(String value) {
            addCriterion("REV2 <=", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2Like(String value) {
            addCriterion("REV2 like", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotLike(String value) {
            addCriterion("REV2 not like", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2In(List<String> values) {
            addCriterion("REV2 in", values, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotIn(List<String> values) {
            addCriterion("REV2 not in", values, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2Between(String value1, String value2) {
            addCriterion("REV2 between", value1, value2, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotBetween(String value1, String value2) {
            addCriterion("REV2 not between", value1, value2, "rev2");
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