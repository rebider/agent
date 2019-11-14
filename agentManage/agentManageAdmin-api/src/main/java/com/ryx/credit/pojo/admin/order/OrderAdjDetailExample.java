package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderAdjDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OrderAdjDetailExample() {
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

        public Criteria andAdjIdIsNull() {
            addCriterion("ADJ_ID is null");
            return (Criteria) this;
        }

        public Criteria andAdjIdIsNotNull() {
            addCriterion("ADJ_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAdjIdEqualTo(String value) {
            addCriterion("ADJ_ID =", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdNotEqualTo(String value) {
            addCriterion("ADJ_ID <>", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdGreaterThan(String value) {
            addCriterion("ADJ_ID >", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdGreaterThanOrEqualTo(String value) {
            addCriterion("ADJ_ID >=", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdLessThan(String value) {
            addCriterion("ADJ_ID <", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdLessThanOrEqualTo(String value) {
            addCriterion("ADJ_ID <=", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdLike(String value) {
            addCriterion("ADJ_ID like", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdNotLike(String value) {
            addCriterion("ADJ_ID not like", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdIn(List<String> values) {
            addCriterion("ADJ_ID in", values, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdNotIn(List<String> values) {
            addCriterion("ADJ_ID not in", values, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdBetween(String value1, String value2) {
            addCriterion("ADJ_ID between", value1, value2, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdNotBetween(String value1, String value2) {
            addCriterion("ADJ_ID not between", value1, value2, "adjId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdIsNull() {
            addCriterion("SUB_ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdIsNotNull() {
            addCriterion("SUB_ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdEqualTo(String value) {
            addCriterion("SUB_ORDER_ID =", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdNotEqualTo(String value) {
            addCriterion("SUB_ORDER_ID <>", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdGreaterThan(String value) {
            addCriterion("SUB_ORDER_ID >", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUB_ORDER_ID >=", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdLessThan(String value) {
            addCriterion("SUB_ORDER_ID <", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdLessThanOrEqualTo(String value) {
            addCriterion("SUB_ORDER_ID <=", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdLike(String value) {
            addCriterion("SUB_ORDER_ID like", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdNotLike(String value) {
            addCriterion("SUB_ORDER_ID not like", value, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdIn(List<String> values) {
            addCriterion("SUB_ORDER_ID in", values, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdNotIn(List<String> values) {
            addCriterion("SUB_ORDER_ID not in", values, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdBetween(String value1, String value2) {
            addCriterion("SUB_ORDER_ID between", value1, value2, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andSubOrderIdNotBetween(String value1, String value2) {
            addCriterion("SUB_ORDER_ID not between", value1, value2, "subOrderId");
            return (Criteria) this;
        }

        public Criteria andAdjNumIsNull() {
            addCriterion("ADJ_NUM is null");
            return (Criteria) this;
        }

        public Criteria andAdjNumIsNotNull() {
            addCriterion("ADJ_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andAdjNumEqualTo(BigDecimal value) {
            addCriterion("ADJ_NUM =", value, "adjNum");
            return (Criteria) this;
        }

        public Criteria andAdjNumNotEqualTo(BigDecimal value) {
            addCriterion("ADJ_NUM <>", value, "adjNum");
            return (Criteria) this;
        }

        public Criteria andAdjNumGreaterThan(BigDecimal value) {
            addCriterion("ADJ_NUM >", value, "adjNum");
            return (Criteria) this;
        }

        public Criteria andAdjNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ADJ_NUM >=", value, "adjNum");
            return (Criteria) this;
        }

        public Criteria andAdjNumLessThan(BigDecimal value) {
            addCriterion("ADJ_NUM <", value, "adjNum");
            return (Criteria) this;
        }

        public Criteria andAdjNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ADJ_NUM <=", value, "adjNum");
            return (Criteria) this;
        }

        public Criteria andAdjNumIn(List<BigDecimal> values) {
            addCriterion("ADJ_NUM in", values, "adjNum");
            return (Criteria) this;
        }

        public Criteria andAdjNumNotIn(List<BigDecimal> values) {
            addCriterion("ADJ_NUM not in", values, "adjNum");
            return (Criteria) this;
        }

        public Criteria andAdjNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADJ_NUM between", value1, value2, "adjNum");
            return (Criteria) this;
        }

        public Criteria andAdjNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADJ_NUM not between", value1, value2, "adjNum");
            return (Criteria) this;
        }

        public Criteria andDifAmountIsNull() {
            addCriterion("DIF_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andDifAmountIsNotNull() {
            addCriterion("DIF_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andDifAmountEqualTo(BigDecimal value) {
            addCriterion("DIF_AMOUNT =", value, "difAmount");
            return (Criteria) this;
        }

        public Criteria andDifAmountNotEqualTo(BigDecimal value) {
            addCriterion("DIF_AMOUNT <>", value, "difAmount");
            return (Criteria) this;
        }

        public Criteria andDifAmountGreaterThan(BigDecimal value) {
            addCriterion("DIF_AMOUNT >", value, "difAmount");
            return (Criteria) this;
        }

        public Criteria andDifAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DIF_AMOUNT >=", value, "difAmount");
            return (Criteria) this;
        }

        public Criteria andDifAmountLessThan(BigDecimal value) {
            addCriterion("DIF_AMOUNT <", value, "difAmount");
            return (Criteria) this;
        }

        public Criteria andDifAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DIF_AMOUNT <=", value, "difAmount");
            return (Criteria) this;
        }

        public Criteria andDifAmountIn(List<BigDecimal> values) {
            addCriterion("DIF_AMOUNT in", values, "difAmount");
            return (Criteria) this;
        }

        public Criteria andDifAmountNotIn(List<BigDecimal> values) {
            addCriterion("DIF_AMOUNT not in", values, "difAmount");
            return (Criteria) this;
        }

        public Criteria andDifAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DIF_AMOUNT between", value1, value2, "difAmount");
            return (Criteria) this;
        }

        public Criteria andDifAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DIF_AMOUNT not between", value1, value2, "difAmount");
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

        public Criteria andStatusEqualTo(BigDecimal value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(BigDecimal value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(BigDecimal value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(BigDecimal value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<BigDecimal> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<BigDecimal> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("VERSION is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(BigDecimal value) {
            addCriterion("VERSION =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(BigDecimal value) {
            addCriterion("VERSION <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(BigDecimal value) {
            addCriterion("VERSION >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("VERSION >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(BigDecimal value) {
            addCriterion("VERSION <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("VERSION <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<BigDecimal> values) {
            addCriterion("VERSION in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<BigDecimal> values) {
            addCriterion("VERSION not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("VERSION between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("VERSION not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andCTmIsNull() {
            addCriterion("C_TM is null");
            return (Criteria) this;
        }

        public Criteria andCTmIsNotNull() {
            addCriterion("C_TM is not null");
            return (Criteria) this;
        }

        public Criteria andCTmEqualTo(Date value) {
            addCriterion("C_TM =", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmNotEqualTo(Date value) {
            addCriterion("C_TM <>", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmGreaterThan(Date value) {
            addCriterion("C_TM >", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmGreaterThanOrEqualTo(Date value) {
            addCriterion("C_TM >=", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmLessThan(Date value) {
            addCriterion("C_TM <", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmLessThanOrEqualTo(Date value) {
            addCriterion("C_TM <=", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmIn(List<Date> values) {
            addCriterion("C_TM in", values, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmNotIn(List<Date> values) {
            addCriterion("C_TM not in", values, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmBetween(Date value1, Date value2) {
            addCriterion("C_TM between", value1, value2, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmNotBetween(Date value1, Date value2) {
            addCriterion("C_TM not between", value1, value2, "cTm");
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