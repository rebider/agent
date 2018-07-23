package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProfitStagingDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ProfitStagingDetailExample() {
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

        public Criteria andStagIdIsNull() {
            addCriterion("STAG_ID is null");
            return (Criteria) this;
        }

        public Criteria andStagIdIsNotNull() {
            addCriterion("STAG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andStagIdEqualTo(String value) {
            addCriterion("STAG_ID =", value, "stagId");
            return (Criteria) this;
        }

        public Criteria andStagIdNotEqualTo(String value) {
            addCriterion("STAG_ID <>", value, "stagId");
            return (Criteria) this;
        }

        public Criteria andStagIdGreaterThan(String value) {
            addCriterion("STAG_ID >", value, "stagId");
            return (Criteria) this;
        }

        public Criteria andStagIdGreaterThanOrEqualTo(String value) {
            addCriterion("STAG_ID >=", value, "stagId");
            return (Criteria) this;
        }

        public Criteria andStagIdLessThan(String value) {
            addCriterion("STAG_ID <", value, "stagId");
            return (Criteria) this;
        }

        public Criteria andStagIdLessThanOrEqualTo(String value) {
            addCriterion("STAG_ID <=", value, "stagId");
            return (Criteria) this;
        }

        public Criteria andStagIdLike(String value) {
            addCriterion("STAG_ID like", value, "stagId");
            return (Criteria) this;
        }

        public Criteria andStagIdNotLike(String value) {
            addCriterion("STAG_ID not like", value, "stagId");
            return (Criteria) this;
        }

        public Criteria andStagIdIn(List<String> values) {
            addCriterion("STAG_ID in", values, "stagId");
            return (Criteria) this;
        }

        public Criteria andStagIdNotIn(List<String> values) {
            addCriterion("STAG_ID not in", values, "stagId");
            return (Criteria) this;
        }

        public Criteria andStagIdBetween(String value1, String value2) {
            addCriterion("STAG_ID between", value1, value2, "stagId");
            return (Criteria) this;
        }

        public Criteria andStagIdNotBetween(String value1, String value2) {
            addCriterion("STAG_ID not between", value1, value2, "stagId");
            return (Criteria) this;
        }

        public Criteria andCurrentStagCountIsNull() {
            addCriterion("CURRENT_STAG_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andCurrentStagCountIsNotNull() {
            addCriterion("CURRENT_STAG_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentStagCountEqualTo(Short value) {
            addCriterion("CURRENT_STAG_COUNT =", value, "currentStagCount");
            return (Criteria) this;
        }

        public Criteria andCurrentStagCountNotEqualTo(Short value) {
            addCriterion("CURRENT_STAG_COUNT <>", value, "currentStagCount");
            return (Criteria) this;
        }

        public Criteria andCurrentStagCountGreaterThan(Short value) {
            addCriterion("CURRENT_STAG_COUNT >", value, "currentStagCount");
            return (Criteria) this;
        }

        public Criteria andCurrentStagCountGreaterThanOrEqualTo(Short value) {
            addCriterion("CURRENT_STAG_COUNT >=", value, "currentStagCount");
            return (Criteria) this;
        }

        public Criteria andCurrentStagCountLessThan(Short value) {
            addCriterion("CURRENT_STAG_COUNT <", value, "currentStagCount");
            return (Criteria) this;
        }

        public Criteria andCurrentStagCountLessThanOrEqualTo(Short value) {
            addCriterion("CURRENT_STAG_COUNT <=", value, "currentStagCount");
            return (Criteria) this;
        }

        public Criteria andCurrentStagCountIn(List<Short> values) {
            addCriterion("CURRENT_STAG_COUNT in", values, "currentStagCount");
            return (Criteria) this;
        }

        public Criteria andCurrentStagCountNotIn(List<Short> values) {
            addCriterion("CURRENT_STAG_COUNT not in", values, "currentStagCount");
            return (Criteria) this;
        }

        public Criteria andCurrentStagCountBetween(Short value1, Short value2) {
            addCriterion("CURRENT_STAG_COUNT between", value1, value2, "currentStagCount");
            return (Criteria) this;
        }

        public Criteria andCurrentStagCountNotBetween(Short value1, Short value2) {
            addCriterion("CURRENT_STAG_COUNT not between", value1, value2, "currentStagCount");
            return (Criteria) this;
        }

        public Criteria andMustAmtIsNull() {
            addCriterion("MUST_AMT is null");
            return (Criteria) this;
        }

        public Criteria andMustAmtIsNotNull() {
            addCriterion("MUST_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andMustAmtEqualTo(BigDecimal value) {
            addCriterion("MUST_AMT =", value, "mustAmt");
            return (Criteria) this;
        }

        public Criteria andMustAmtNotEqualTo(BigDecimal value) {
            addCriterion("MUST_AMT <>", value, "mustAmt");
            return (Criteria) this;
        }

        public Criteria andMustAmtGreaterThan(BigDecimal value) {
            addCriterion("MUST_AMT >", value, "mustAmt");
            return (Criteria) this;
        }

        public Criteria andMustAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MUST_AMT >=", value, "mustAmt");
            return (Criteria) this;
        }

        public Criteria andMustAmtLessThan(BigDecimal value) {
            addCriterion("MUST_AMT <", value, "mustAmt");
            return (Criteria) this;
        }

        public Criteria andMustAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MUST_AMT <=", value, "mustAmt");
            return (Criteria) this;
        }

        public Criteria andMustAmtIn(List<BigDecimal> values) {
            addCriterion("MUST_AMT in", values, "mustAmt");
            return (Criteria) this;
        }

        public Criteria andMustAmtNotIn(List<BigDecimal> values) {
            addCriterion("MUST_AMT not in", values, "mustAmt");
            return (Criteria) this;
        }

        public Criteria andMustAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MUST_AMT between", value1, value2, "mustAmt");
            return (Criteria) this;
        }

        public Criteria andMustAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MUST_AMT not between", value1, value2, "mustAmt");
            return (Criteria) this;
        }

        public Criteria andRealAmtIsNull() {
            addCriterion("REAL_AMT is null");
            return (Criteria) this;
        }

        public Criteria andRealAmtIsNotNull() {
            addCriterion("REAL_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andRealAmtEqualTo(BigDecimal value) {
            addCriterion("REAL_AMT =", value, "realAmt");
            return (Criteria) this;
        }

        public Criteria andRealAmtNotEqualTo(BigDecimal value) {
            addCriterion("REAL_AMT <>", value, "realAmt");
            return (Criteria) this;
        }

        public Criteria andRealAmtGreaterThan(BigDecimal value) {
            addCriterion("REAL_AMT >", value, "realAmt");
            return (Criteria) this;
        }

        public Criteria andRealAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_AMT >=", value, "realAmt");
            return (Criteria) this;
        }

        public Criteria andRealAmtLessThan(BigDecimal value) {
            addCriterion("REAL_AMT <", value, "realAmt");
            return (Criteria) this;
        }

        public Criteria andRealAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_AMT <=", value, "realAmt");
            return (Criteria) this;
        }

        public Criteria andRealAmtIn(List<BigDecimal> values) {
            addCriterion("REAL_AMT in", values, "realAmt");
            return (Criteria) this;
        }

        public Criteria andRealAmtNotIn(List<BigDecimal> values) {
            addCriterion("REAL_AMT not in", values, "realAmt");
            return (Criteria) this;
        }

        public Criteria andRealAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_AMT between", value1, value2, "realAmt");
            return (Criteria) this;
        }

        public Criteria andRealAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_AMT not between", value1, value2, "realAmt");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("STATUS like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("STATUS not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNull() {
            addCriterion("SOURCE_ID is null");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNotNull() {
            addCriterion("SOURCE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSourceIdEqualTo(String value) {
            addCriterion("SOURCE_ID =", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotEqualTo(String value) {
            addCriterion("SOURCE_ID <>", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThan(String value) {
            addCriterion("SOURCE_ID >", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThanOrEqualTo(String value) {
            addCriterion("SOURCE_ID >=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThan(String value) {
            addCriterion("SOURCE_ID <", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThanOrEqualTo(String value) {
            addCriterion("SOURCE_ID <=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLike(String value) {
            addCriterion("SOURCE_ID like", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotLike(String value) {
            addCriterion("SOURCE_ID not like", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdIn(List<String> values) {
            addCriterion("SOURCE_ID in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotIn(List<String> values) {
            addCriterion("SOURCE_ID not in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdBetween(String value1, String value2) {
            addCriterion("SOURCE_ID between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotBetween(String value1, String value2) {
            addCriterion("SOURCE_ID not between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andDeductionDateIsNull() {
            addCriterion("DEDUCTION_DATE is null");
            return (Criteria) this;
        }

        public Criteria andDeductionDateIsNotNull() {
            addCriterion("DEDUCTION_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andDeductionDateEqualTo(String value) {
            addCriterion("DEDUCTION_DATE =", value, "deductionDate");
            return (Criteria) this;
        }

        public Criteria andDeductionDateNotEqualTo(String value) {
            addCriterion("DEDUCTION_DATE <>", value, "deductionDate");
            return (Criteria) this;
        }

        public Criteria andDeductionDateGreaterThan(String value) {
            addCriterion("DEDUCTION_DATE >", value, "deductionDate");
            return (Criteria) this;
        }

        public Criteria andDeductionDateGreaterThanOrEqualTo(String value) {
            addCriterion("DEDUCTION_DATE >=", value, "deductionDate");
            return (Criteria) this;
        }

        public Criteria andDeductionDateLessThan(String value) {
            addCriterion("DEDUCTION_DATE <", value, "deductionDate");
            return (Criteria) this;
        }

        public Criteria andDeductionDateLessThanOrEqualTo(String value) {
            addCriterion("DEDUCTION_DATE <=", value, "deductionDate");
            return (Criteria) this;
        }

        public Criteria andDeductionDateLike(String value) {
            addCriterion("DEDUCTION_DATE like", value, "deductionDate");
            return (Criteria) this;
        }

        public Criteria andDeductionDateNotLike(String value) {
            addCriterion("DEDUCTION_DATE not like", value, "deductionDate");
            return (Criteria) this;
        }

        public Criteria andDeductionDateIn(List<String> values) {
            addCriterion("DEDUCTION_DATE in", values, "deductionDate");
            return (Criteria) this;
        }

        public Criteria andDeductionDateNotIn(List<String> values) {
            addCriterion("DEDUCTION_DATE not in", values, "deductionDate");
            return (Criteria) this;
        }

        public Criteria andDeductionDateBetween(String value1, String value2) {
            addCriterion("DEDUCTION_DATE between", value1, value2, "deductionDate");
            return (Criteria) this;
        }

        public Criteria andDeductionDateNotBetween(String value1, String value2) {
            addCriterion("DEDUCTION_DATE not between", value1, value2, "deductionDate");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
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