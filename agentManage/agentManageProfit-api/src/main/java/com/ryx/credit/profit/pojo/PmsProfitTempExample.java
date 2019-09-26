package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PmsProfitTempExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PmsProfitTempExample() {
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

        public Criteria andMonthIsNull() {
            addCriterion("MONTH is null");
            return (Criteria) this;
        }

        public Criteria andMonthIsNotNull() {
            addCriterion("MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andMonthEqualTo(String value) {
            addCriterion("MONTH =", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotEqualTo(String value) {
            addCriterion("MONTH <>", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThan(String value) {
            addCriterion("MONTH >", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThanOrEqualTo(String value) {
            addCriterion("MONTH >=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThan(String value) {
            addCriterion("MONTH <", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThanOrEqualTo(String value) {
            addCriterion("MONTH <=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLike(String value) {
            addCriterion("MONTH like", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotLike(String value) {
            addCriterion("MONTH not like", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthIn(List<String> values) {
            addCriterion("MONTH in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotIn(List<String> values) {
            addCriterion("MONTH not in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthBetween(String value1, String value2) {
            addCriterion("MONTH between", value1, value2, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotBetween(String value1, String value2) {
            addCriterion("MONTH not between", value1, value2, "month");
            return (Criteria) this;
        }

        public Criteria andOrderNumberIsNull() {
            addCriterion("ORDER_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andOrderNumberIsNotNull() {
            addCriterion("ORDER_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNumberEqualTo(BigDecimal value) {
            addCriterion("ORDER_NUMBER =", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotEqualTo(BigDecimal value) {
            addCriterion("ORDER_NUMBER <>", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberGreaterThan(BigDecimal value) {
            addCriterion("ORDER_NUMBER >", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDER_NUMBER >=", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberLessThan(BigDecimal value) {
            addCriterion("ORDER_NUMBER <", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDER_NUMBER <=", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberIn(List<BigDecimal> values) {
            addCriterion("ORDER_NUMBER in", values, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotIn(List<BigDecimal> values) {
            addCriterion("ORDER_NUMBER not in", values, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDER_NUMBER between", value1, value2, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDER_NUMBER not between", value1, value2, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andSheetNameIsNull() {
            addCriterion("SHEET_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSheetNameIsNotNull() {
            addCriterion("SHEET_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSheetNameEqualTo(String value) {
            addCriterion("SHEET_NAME =", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameNotEqualTo(String value) {
            addCriterion("SHEET_NAME <>", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameGreaterThan(String value) {
            addCriterion("SHEET_NAME >", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameGreaterThanOrEqualTo(String value) {
            addCriterion("SHEET_NAME >=", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameLessThan(String value) {
            addCriterion("SHEET_NAME <", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameLessThanOrEqualTo(String value) {
            addCriterion("SHEET_NAME <=", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameLike(String value) {
            addCriterion("SHEET_NAME like", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameNotLike(String value) {
            addCriterion("SHEET_NAME not like", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameIn(List<String> values) {
            addCriterion("SHEET_NAME in", values, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameNotIn(List<String> values) {
            addCriterion("SHEET_NAME not in", values, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameBetween(String value1, String value2) {
            addCriterion("SHEET_NAME between", value1, value2, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameNotBetween(String value1, String value2) {
            addCriterion("SHEET_NAME not between", value1, value2, "sheetName");
            return (Criteria) this;
        }

        public Criteria andImportPersonIsNull() {
            addCriterion("IMPORT_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andImportPersonIsNotNull() {
            addCriterion("IMPORT_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andImportPersonEqualTo(String value) {
            addCriterion("IMPORT_PERSON =", value, "importPerson");
            return (Criteria) this;
        }

        public Criteria andImportPersonNotEqualTo(String value) {
            addCriterion("IMPORT_PERSON <>", value, "importPerson");
            return (Criteria) this;
        }

        public Criteria andImportPersonGreaterThan(String value) {
            addCriterion("IMPORT_PERSON >", value, "importPerson");
            return (Criteria) this;
        }

        public Criteria andImportPersonGreaterThanOrEqualTo(String value) {
            addCriterion("IMPORT_PERSON >=", value, "importPerson");
            return (Criteria) this;
        }

        public Criteria andImportPersonLessThan(String value) {
            addCriterion("IMPORT_PERSON <", value, "importPerson");
            return (Criteria) this;
        }

        public Criteria andImportPersonLessThanOrEqualTo(String value) {
            addCriterion("IMPORT_PERSON <=", value, "importPerson");
            return (Criteria) this;
        }

        public Criteria andImportPersonLike(String value) {
            addCriterion("IMPORT_PERSON like", value, "importPerson");
            return (Criteria) this;
        }

        public Criteria andImportPersonNotLike(String value) {
            addCriterion("IMPORT_PERSON not like", value, "importPerson");
            return (Criteria) this;
        }

        public Criteria andImportPersonIn(List<String> values) {
            addCriterion("IMPORT_PERSON in", values, "importPerson");
            return (Criteria) this;
        }

        public Criteria andImportPersonNotIn(List<String> values) {
            addCriterion("IMPORT_PERSON not in", values, "importPerson");
            return (Criteria) this;
        }

        public Criteria andImportPersonBetween(String value1, String value2) {
            addCriterion("IMPORT_PERSON between", value1, value2, "importPerson");
            return (Criteria) this;
        }

        public Criteria andImportPersonNotBetween(String value1, String value2) {
            addCriterion("IMPORT_PERSON not between", value1, value2, "importPerson");
            return (Criteria) this;
        }

        public Criteria andUniqueFlagIsNull() {
            addCriterion("UNIQUE_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andUniqueFlagIsNotNull() {
            addCriterion("UNIQUE_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andUniqueFlagEqualTo(String value) {
            addCriterion("UNIQUE_FLAG =", value, "uniqueFlag");
            return (Criteria) this;
        }

        public Criteria andUniqueFlagNotEqualTo(String value) {
            addCriterion("UNIQUE_FLAG <>", value, "uniqueFlag");
            return (Criteria) this;
        }

        public Criteria andUniqueFlagGreaterThan(String value) {
            addCriterion("UNIQUE_FLAG >", value, "uniqueFlag");
            return (Criteria) this;
        }

        public Criteria andUniqueFlagGreaterThanOrEqualTo(String value) {
            addCriterion("UNIQUE_FLAG >=", value, "uniqueFlag");
            return (Criteria) this;
        }

        public Criteria andUniqueFlagLessThan(String value) {
            addCriterion("UNIQUE_FLAG <", value, "uniqueFlag");
            return (Criteria) this;
        }

        public Criteria andUniqueFlagLessThanOrEqualTo(String value) {
            addCriterion("UNIQUE_FLAG <=", value, "uniqueFlag");
            return (Criteria) this;
        }

        public Criteria andUniqueFlagLike(String value) {
            addCriterion("UNIQUE_FLAG like", value, "uniqueFlag");
            return (Criteria) this;
        }

        public Criteria andUniqueFlagNotLike(String value) {
            addCriterion("UNIQUE_FLAG not like", value, "uniqueFlag");
            return (Criteria) this;
        }

        public Criteria andUniqueFlagIn(List<String> values) {
            addCriterion("UNIQUE_FLAG in", values, "uniqueFlag");
            return (Criteria) this;
        }

        public Criteria andUniqueFlagNotIn(List<String> values) {
            addCriterion("UNIQUE_FLAG not in", values, "uniqueFlag");
            return (Criteria) this;
        }

        public Criteria andUniqueFlagBetween(String value1, String value2) {
            addCriterion("UNIQUE_FLAG between", value1, value2, "uniqueFlag");
            return (Criteria) this;
        }

        public Criteria andUniqueFlagNotBetween(String value1, String value2) {
            addCriterion("UNIQUE_FLAG not between", value1, value2, "uniqueFlag");
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

        public Criteria andBusCodeIsNull() {
            addCriterion("BUS_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBusCodeIsNotNull() {
            addCriterion("BUS_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBusCodeEqualTo(String value) {
            addCriterion("BUS_CODE =", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotEqualTo(String value) {
            addCriterion("BUS_CODE <>", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeGreaterThan(String value) {
            addCriterion("BUS_CODE >", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_CODE >=", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeLessThan(String value) {
            addCriterion("BUS_CODE <", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeLessThanOrEqualTo(String value) {
            addCriterion("BUS_CODE <=", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeLike(String value) {
            addCriterion("BUS_CODE like", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotLike(String value) {
            addCriterion("BUS_CODE not like", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeIn(List<String> values) {
            addCriterion("BUS_CODE in", values, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotIn(List<String> values) {
            addCriterion("BUS_CODE not in", values, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeBetween(String value1, String value2) {
            addCriterion("BUS_CODE between", value1, value2, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotBetween(String value1, String value2) {
            addCriterion("BUS_CODE not between", value1, value2, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusNameIsNull() {
            addCriterion("BUS_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBusNameIsNotNull() {
            addCriterion("BUS_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBusNameEqualTo(String value) {
            addCriterion("BUS_NAME =", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameNotEqualTo(String value) {
            addCriterion("BUS_NAME <>", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameGreaterThan(String value) {
            addCriterion("BUS_NAME >", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_NAME >=", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameLessThan(String value) {
            addCriterion("BUS_NAME <", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameLessThanOrEqualTo(String value) {
            addCriterion("BUS_NAME <=", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameLike(String value) {
            addCriterion("BUS_NAME like", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameNotLike(String value) {
            addCriterion("BUS_NAME not like", value, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameIn(List<String> values) {
            addCriterion("BUS_NAME in", values, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameNotIn(List<String> values) {
            addCriterion("BUS_NAME not in", values, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameBetween(String value1, String value2) {
            addCriterion("BUS_NAME between", value1, value2, "busName");
            return (Criteria) this;
        }

        public Criteria andBusNameNotBetween(String value1, String value2) {
            addCriterion("BUS_NAME not between", value1, value2, "busName");
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