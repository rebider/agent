package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PtaxHistoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PtaxHistoryExample() {
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

        public Criteria andTaxMonthIsNull() {
            addCriterion("TAX_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andTaxMonthIsNotNull() {
            addCriterion("TAX_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andTaxMonthEqualTo(String value) {
            addCriterion("TAX_MONTH =", value, "taxMonth");
            return (Criteria) this;
        }

        public Criteria andTaxMonthNotEqualTo(String value) {
            addCriterion("TAX_MONTH <>", value, "taxMonth");
            return (Criteria) this;
        }

        public Criteria andTaxMonthGreaterThan(String value) {
            addCriterion("TAX_MONTH >", value, "taxMonth");
            return (Criteria) this;
        }

        public Criteria andTaxMonthGreaterThanOrEqualTo(String value) {
            addCriterion("TAX_MONTH >=", value, "taxMonth");
            return (Criteria) this;
        }

        public Criteria andTaxMonthLessThan(String value) {
            addCriterion("TAX_MONTH <", value, "taxMonth");
            return (Criteria) this;
        }

        public Criteria andTaxMonthLessThanOrEqualTo(String value) {
            addCriterion("TAX_MONTH <=", value, "taxMonth");
            return (Criteria) this;
        }

        public Criteria andTaxMonthLike(String value) {
            addCriterion("TAX_MONTH like", value, "taxMonth");
            return (Criteria) this;
        }

        public Criteria andTaxMonthNotLike(String value) {
            addCriterion("TAX_MONTH not like", value, "taxMonth");
            return (Criteria) this;
        }

        public Criteria andTaxMonthIn(List<String> values) {
            addCriterion("TAX_MONTH in", values, "taxMonth");
            return (Criteria) this;
        }

        public Criteria andTaxMonthNotIn(List<String> values) {
            addCriterion("TAX_MONTH not in", values, "taxMonth");
            return (Criteria) this;
        }

        public Criteria andTaxMonthBetween(String value1, String value2) {
            addCriterion("TAX_MONTH between", value1, value2, "taxMonth");
            return (Criteria) this;
        }

        public Criteria andTaxMonthNotBetween(String value1, String value2) {
            addCriterion("TAX_MONTH not between", value1, value2, "taxMonth");
            return (Criteria) this;
        }

        public Criteria andTaxAmountIsNull() {
            addCriterion("TAX_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andTaxAmountIsNotNull() {
            addCriterion("TAX_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andTaxAmountEqualTo(BigDecimal value) {
            addCriterion("TAX_AMOUNT =", value, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountNotEqualTo(BigDecimal value) {
            addCriterion("TAX_AMOUNT <>", value, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountGreaterThan(BigDecimal value) {
            addCriterion("TAX_AMOUNT >", value, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_AMOUNT >=", value, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountLessThan(BigDecimal value) {
            addCriterion("TAX_AMOUNT <", value, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_AMOUNT <=", value, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountIn(List<BigDecimal> values) {
            addCriterion("TAX_AMOUNT in", values, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountNotIn(List<BigDecimal> values) {
            addCriterion("TAX_AMOUNT not in", values, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_AMOUNT between", value1, value2, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andTaxAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_AMOUNT not between", value1, value2, "taxAmount");
            return (Criteria) this;
        }

        public Criteria andAgentPidIsNull() {
            addCriterion("AGENT_PID is null");
            return (Criteria) this;
        }

        public Criteria andAgentPidIsNotNull() {
            addCriterion("AGENT_PID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentPidEqualTo(String value) {
            addCriterion("AGENT_PID =", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotEqualTo(String value) {
            addCriterion("AGENT_PID <>", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidGreaterThan(String value) {
            addCriterion("AGENT_PID >", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_PID >=", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLessThan(String value) {
            addCriterion("AGENT_PID <", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLessThanOrEqualTo(String value) {
            addCriterion("AGENT_PID <=", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLike(String value) {
            addCriterion("AGENT_PID like", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotLike(String value) {
            addCriterion("AGENT_PID not like", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidIn(List<String> values) {
            addCriterion("AGENT_PID in", values, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotIn(List<String> values) {
            addCriterion("AGENT_PID not in", values, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidBetween(String value1, String value2) {
            addCriterion("AGENT_PID between", value1, value2, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotBetween(String value1, String value2) {
            addCriterion("AGENT_PID not between", value1, value2, "agentPid");
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