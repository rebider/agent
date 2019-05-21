package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ServerAmtDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ServerAmtDetailExample() {
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

        public Criteria andPsaIdIsNull() {
            addCriterion("PSA_ID is null");
            return (Criteria) this;
        }

        public Criteria andPsaIdIsNotNull() {
            addCriterion("PSA_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPsaIdEqualTo(String value) {
            addCriterion("PSA_ID =", value, "psaId");
            return (Criteria) this;
        }

        public Criteria andPsaIdNotEqualTo(String value) {
            addCriterion("PSA_ID <>", value, "psaId");
            return (Criteria) this;
        }

        public Criteria andPsaIdGreaterThan(String value) {
            addCriterion("PSA_ID >", value, "psaId");
            return (Criteria) this;
        }

        public Criteria andPsaIdGreaterThanOrEqualTo(String value) {
            addCriterion("PSA_ID >=", value, "psaId");
            return (Criteria) this;
        }

        public Criteria andPsaIdLessThan(String value) {
            addCriterion("PSA_ID <", value, "psaId");
            return (Criteria) this;
        }

        public Criteria andPsaIdLessThanOrEqualTo(String value) {
            addCriterion("PSA_ID <=", value, "psaId");
            return (Criteria) this;
        }

        public Criteria andPsaIdLike(String value) {
            addCriterion("PSA_ID like", value, "psaId");
            return (Criteria) this;
        }

        public Criteria andPsaIdNotLike(String value) {
            addCriterion("PSA_ID not like", value, "psaId");
            return (Criteria) this;
        }

        public Criteria andPsaIdIn(List<String> values) {
            addCriterion("PSA_ID in", values, "psaId");
            return (Criteria) this;
        }

        public Criteria andPsaIdNotIn(List<String> values) {
            addCriterion("PSA_ID not in", values, "psaId");
            return (Criteria) this;
        }

        public Criteria andPsaIdBetween(String value1, String value2) {
            addCriterion("PSA_ID between", value1, value2, "psaId");
            return (Criteria) this;
        }

        public Criteria andPsaIdNotBetween(String value1, String value2) {
            addCriterion("PSA_ID not between", value1, value2, "psaId");
            return (Criteria) this;
        }

        public Criteria andServerDateIsNull() {
            addCriterion("SERVER_DATE is null");
            return (Criteria) this;
        }

        public Criteria andServerDateIsNotNull() {
            addCriterion("SERVER_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andServerDateEqualTo(String value) {
            addCriterion("SERVER_DATE =", value, "serverDate");
            return (Criteria) this;
        }

        public Criteria andServerDateNotEqualTo(String value) {
            addCriterion("SERVER_DATE <>", value, "serverDate");
            return (Criteria) this;
        }

        public Criteria andServerDateGreaterThan(String value) {
            addCriterion("SERVER_DATE >", value, "serverDate");
            return (Criteria) this;
        }

        public Criteria andServerDateGreaterThanOrEqualTo(String value) {
            addCriterion("SERVER_DATE >=", value, "serverDate");
            return (Criteria) this;
        }

        public Criteria andServerDateLessThan(String value) {
            addCriterion("SERVER_DATE <", value, "serverDate");
            return (Criteria) this;
        }

        public Criteria andServerDateLessThanOrEqualTo(String value) {
            addCriterion("SERVER_DATE <=", value, "serverDate");
            return (Criteria) this;
        }

        public Criteria andServerDateLike(String value) {
            addCriterion("SERVER_DATE like", value, "serverDate");
            return (Criteria) this;
        }

        public Criteria andServerDateNotLike(String value) {
            addCriterion("SERVER_DATE not like", value, "serverDate");
            return (Criteria) this;
        }

        public Criteria andServerDateIn(List<String> values) {
            addCriterion("SERVER_DATE in", values, "serverDate");
            return (Criteria) this;
        }

        public Criteria andServerDateNotIn(List<String> values) {
            addCriterion("SERVER_DATE not in", values, "serverDate");
            return (Criteria) this;
        }

        public Criteria andServerDateBetween(String value1, String value2) {
            addCriterion("SERVER_DATE between", value1, value2, "serverDate");
            return (Criteria) this;
        }

        public Criteria andServerDateNotBetween(String value1, String value2) {
            addCriterion("SERVER_DATE not between", value1, value2, "serverDate");
            return (Criteria) this;
        }

        public Criteria andSumAmtIsNull() {
            addCriterion("SUM_AMT is null");
            return (Criteria) this;
        }

        public Criteria andSumAmtIsNotNull() {
            addCriterion("SUM_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andSumAmtEqualTo(BigDecimal value) {
            addCriterion("SUM_AMT =", value, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtNotEqualTo(BigDecimal value) {
            addCriterion("SUM_AMT <>", value, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtGreaterThan(BigDecimal value) {
            addCriterion("SUM_AMT >", value, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUM_AMT >=", value, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtLessThan(BigDecimal value) {
            addCriterion("SUM_AMT <", value, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUM_AMT <=", value, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtIn(List<BigDecimal> values) {
            addCriterion("SUM_AMT in", values, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtNotIn(List<BigDecimal> values) {
            addCriterion("SUM_AMT not in", values, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUM_AMT between", value1, value2, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andSumAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUM_AMT not between", value1, value2, "sumAmt");
            return (Criteria) this;
        }

        public Criteria andServerAmtIsNull() {
            addCriterion("SERVER_AMT is null");
            return (Criteria) this;
        }

        public Criteria andServerAmtIsNotNull() {
            addCriterion("SERVER_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andServerAmtEqualTo(BigDecimal value) {
            addCriterion("SERVER_AMT =", value, "serverAmt");
            return (Criteria) this;
        }

        public Criteria andServerAmtNotEqualTo(BigDecimal value) {
            addCriterion("SERVER_AMT <>", value, "serverAmt");
            return (Criteria) this;
        }

        public Criteria andServerAmtGreaterThan(BigDecimal value) {
            addCriterion("SERVER_AMT >", value, "serverAmt");
            return (Criteria) this;
        }

        public Criteria andServerAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SERVER_AMT >=", value, "serverAmt");
            return (Criteria) this;
        }

        public Criteria andServerAmtLessThan(BigDecimal value) {
            addCriterion("SERVER_AMT <", value, "serverAmt");
            return (Criteria) this;
        }

        public Criteria andServerAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SERVER_AMT <=", value, "serverAmt");
            return (Criteria) this;
        }

        public Criteria andServerAmtIn(List<BigDecimal> values) {
            addCriterion("SERVER_AMT in", values, "serverAmt");
            return (Criteria) this;
        }

        public Criteria andServerAmtNotIn(List<BigDecimal> values) {
            addCriterion("SERVER_AMT not in", values, "serverAmt");
            return (Criteria) this;
        }

        public Criteria andServerAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SERVER_AMT between", value1, value2, "serverAmt");
            return (Criteria) this;
        }

        public Criteria andServerAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SERVER_AMT not between", value1, value2, "serverAmt");
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