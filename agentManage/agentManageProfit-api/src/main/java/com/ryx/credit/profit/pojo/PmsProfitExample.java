package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PmsProfitExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PmsProfitExample() {
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

        public Criteria andSheetColumnIsNull() {
            addCriterion("SHEET_COLUMN is null");
            return (Criteria) this;
        }

        public Criteria andSheetColumnIsNotNull() {
            addCriterion("SHEET_COLUMN is not null");
            return (Criteria) this;
        }

        public Criteria andSheetColumnEqualTo(BigDecimal value) {
            addCriterion("SHEET_COLUMN =", value, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnNotEqualTo(BigDecimal value) {
            addCriterion("SHEET_COLUMN <>", value, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnGreaterThan(BigDecimal value) {
            addCriterion("SHEET_COLUMN >", value, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SHEET_COLUMN >=", value, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnLessThan(BigDecimal value) {
            addCriterion("SHEET_COLUMN <", value, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SHEET_COLUMN <=", value, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnIn(List<BigDecimal> values) {
            addCriterion("SHEET_COLUMN in", values, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnNotIn(List<BigDecimal> values) {
            addCriterion("SHEET_COLUMN not in", values, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SHEET_COLUMN between", value1, value2, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SHEET_COLUMN not between", value1, value2, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetHeadIsNull() {
            addCriterion("SHEET_HEAD is null");
            return (Criteria) this;
        }

        public Criteria andSheetHeadIsNotNull() {
            addCriterion("SHEET_HEAD is not null");
            return (Criteria) this;
        }

        public Criteria andSheetHeadEqualTo(Object value) {
            addCriterion("SHEET_HEAD =", value, "sheetHead");
            return (Criteria) this;
        }

        public Criteria andSheetHeadNotEqualTo(Object value) {
            addCriterion("SHEET_HEAD <>", value, "sheetHead");
            return (Criteria) this;
        }

        public Criteria andSheetHeadGreaterThan(Object value) {
            addCriterion("SHEET_HEAD >", value, "sheetHead");
            return (Criteria) this;
        }

        public Criteria andSheetHeadGreaterThanOrEqualTo(Object value) {
            addCriterion("SHEET_HEAD >=", value, "sheetHead");
            return (Criteria) this;
        }

        public Criteria andSheetHeadLessThan(Object value) {
            addCriterion("SHEET_HEAD <", value, "sheetHead");
            return (Criteria) this;
        }

        public Criteria andSheetHeadLessThanOrEqualTo(Object value) {
            addCriterion("SHEET_HEAD <=", value, "sheetHead");
            return (Criteria) this;
        }

        public Criteria andSheetHeadIn(List<Object> values) {
            addCriterion("SHEET_HEAD in", values, "sheetHead");
            return (Criteria) this;
        }

        public Criteria andSheetHeadNotIn(List<Object> values) {
            addCriterion("SHEET_HEAD not in", values, "sheetHead");
            return (Criteria) this;
        }

        public Criteria andSheetHeadBetween(Object value1, Object value2) {
            addCriterion("SHEET_HEAD between", value1, value2, "sheetHead");
            return (Criteria) this;
        }

        public Criteria andSheetHeadNotBetween(Object value1, Object value2) {
            addCriterion("SHEET_HEAD not between", value1, value2, "sheetHead");
            return (Criteria) this;
        }

        public Criteria andSheetDataIsNull() {
            addCriterion("SHEET_DATA is null");
            return (Criteria) this;
        }

        public Criteria andSheetDataIsNotNull() {
            addCriterion("SHEET_DATA is not null");
            return (Criteria) this;
        }

        public Criteria andSheetDataEqualTo(Object value) {
            addCriterion("SHEET_DATA =", value, "sheetData");
            return (Criteria) this;
        }

        public Criteria andSheetDataNotEqualTo(Object value) {
            addCriterion("SHEET_DATA <>", value, "sheetData");
            return (Criteria) this;
        }

        public Criteria andSheetDataGreaterThan(Object value) {
            addCriterion("SHEET_DATA >", value, "sheetData");
            return (Criteria) this;
        }

        public Criteria andSheetDataGreaterThanOrEqualTo(Object value) {
            addCriterion("SHEET_DATA >=", value, "sheetData");
            return (Criteria) this;
        }

        public Criteria andSheetDataLessThan(Object value) {
            addCriterion("SHEET_DATA <", value, "sheetData");
            return (Criteria) this;
        }

        public Criteria andSheetDataLessThanOrEqualTo(Object value) {
            addCriterion("SHEET_DATA <=", value, "sheetData");
            return (Criteria) this;
        }

        public Criteria andSheetDataIn(List<Object> values) {
            addCriterion("SHEET_DATA in", values, "sheetData");
            return (Criteria) this;
        }

        public Criteria andSheetDataNotIn(List<Object> values) {
            addCriterion("SHEET_DATA not in", values, "sheetData");
            return (Criteria) this;
        }

        public Criteria andSheetDataBetween(Object value1, Object value2) {
            addCriterion("SHEET_DATA between", value1, value2, "sheetData");
            return (Criteria) this;
        }

        public Criteria andSheetDataNotBetween(Object value1, Object value2) {
            addCriterion("SHEET_DATA not between", value1, value2, "sheetData");
            return (Criteria) this;
        }

        public Criteria andProfitTypeIsNull() {
            addCriterion("PROFIT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andProfitTypeIsNotNull() {
            addCriterion("PROFIT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andProfitTypeEqualTo(String value) {
            addCriterion("PROFIT_TYPE =", value, "profitType");
            return (Criteria) this;
        }

        public Criteria andProfitTypeNotEqualTo(String value) {
            addCriterion("PROFIT_TYPE <>", value, "profitType");
            return (Criteria) this;
        }

        public Criteria andProfitTypeGreaterThan(String value) {
            addCriterion("PROFIT_TYPE >", value, "profitType");
            return (Criteria) this;
        }

        public Criteria andProfitTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PROFIT_TYPE >=", value, "profitType");
            return (Criteria) this;
        }

        public Criteria andProfitTypeLessThan(String value) {
            addCriterion("PROFIT_TYPE <", value, "profitType");
            return (Criteria) this;
        }

        public Criteria andProfitTypeLessThanOrEqualTo(String value) {
            addCriterion("PROFIT_TYPE <=", value, "profitType");
            return (Criteria) this;
        }

        public Criteria andProfitTypeLike(String value) {
            addCriterion("PROFIT_TYPE like", value, "profitType");
            return (Criteria) this;
        }

        public Criteria andProfitTypeNotLike(String value) {
            addCriterion("PROFIT_TYPE not like", value, "profitType");
            return (Criteria) this;
        }

        public Criteria andProfitTypeIn(List<String> values) {
            addCriterion("PROFIT_TYPE in", values, "profitType");
            return (Criteria) this;
        }

        public Criteria andProfitTypeNotIn(List<String> values) {
            addCriterion("PROFIT_TYPE not in", values, "profitType");
            return (Criteria) this;
        }

        public Criteria andProfitTypeBetween(String value1, String value2) {
            addCriterion("PROFIT_TYPE between", value1, value2, "profitType");
            return (Criteria) this;
        }

        public Criteria andProfitTypeNotBetween(String value1, String value2) {
            addCriterion("PROFIT_TYPE not between", value1, value2, "profitType");
            return (Criteria) this;
        }

        public Criteria andProfitHzIsNull() {
            addCriterion("PROFIT_HZ is null");
            return (Criteria) this;
        }

        public Criteria andProfitHzIsNotNull() {
            addCriterion("PROFIT_HZ is not null");
            return (Criteria) this;
        }

        public Criteria andProfitHzEqualTo(String value) {
            addCriterion("PROFIT_HZ =", value, "profitHz");
            return (Criteria) this;
        }

        public Criteria andProfitHzNotEqualTo(String value) {
            addCriterion("PROFIT_HZ <>", value, "profitHz");
            return (Criteria) this;
        }

        public Criteria andProfitHzGreaterThan(String value) {
            addCriterion("PROFIT_HZ >", value, "profitHz");
            return (Criteria) this;
        }

        public Criteria andProfitHzGreaterThanOrEqualTo(String value) {
            addCriterion("PROFIT_HZ >=", value, "profitHz");
            return (Criteria) this;
        }

        public Criteria andProfitHzLessThan(String value) {
            addCriterion("PROFIT_HZ <", value, "profitHz");
            return (Criteria) this;
        }

        public Criteria andProfitHzLessThanOrEqualTo(String value) {
            addCriterion("PROFIT_HZ <=", value, "profitHz");
            return (Criteria) this;
        }

        public Criteria andProfitHzLike(String value) {
            addCriterion("PROFIT_HZ like", value, "profitHz");
            return (Criteria) this;
        }

        public Criteria andProfitHzNotLike(String value) {
            addCriterion("PROFIT_HZ not like", value, "profitHz");
            return (Criteria) this;
        }

        public Criteria andProfitHzIn(List<String> values) {
            addCriterion("PROFIT_HZ in", values, "profitHz");
            return (Criteria) this;
        }

        public Criteria andProfitHzNotIn(List<String> values) {
            addCriterion("PROFIT_HZ not in", values, "profitHz");
            return (Criteria) this;
        }

        public Criteria andProfitHzBetween(String value1, String value2) {
            addCriterion("PROFIT_HZ between", value1, value2, "profitHz");
            return (Criteria) this;
        }

        public Criteria andProfitHzNotBetween(String value1, String value2) {
            addCriterion("PROFIT_HZ not between", value1, value2, "profitHz");
            return (Criteria) this;
        }

        public Criteria andPayConditionIsNull() {
            addCriterion("PAY_CONDITION is null");
            return (Criteria) this;
        }

        public Criteria andPayConditionIsNotNull() {
            addCriterion("PAY_CONDITION is not null");
            return (Criteria) this;
        }

        public Criteria andPayConditionEqualTo(String value) {
            addCriterion("PAY_CONDITION =", value, "payCondition");
            return (Criteria) this;
        }

        public Criteria andPayConditionNotEqualTo(String value) {
            addCriterion("PAY_CONDITION <>", value, "payCondition");
            return (Criteria) this;
        }

        public Criteria andPayConditionGreaterThan(String value) {
            addCriterion("PAY_CONDITION >", value, "payCondition");
            return (Criteria) this;
        }

        public Criteria andPayConditionGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_CONDITION >=", value, "payCondition");
            return (Criteria) this;
        }

        public Criteria andPayConditionLessThan(String value) {
            addCriterion("PAY_CONDITION <", value, "payCondition");
            return (Criteria) this;
        }

        public Criteria andPayConditionLessThanOrEqualTo(String value) {
            addCriterion("PAY_CONDITION <=", value, "payCondition");
            return (Criteria) this;
        }

        public Criteria andPayConditionLike(String value) {
            addCriterion("PAY_CONDITION like", value, "payCondition");
            return (Criteria) this;
        }

        public Criteria andPayConditionNotLike(String value) {
            addCriterion("PAY_CONDITION not like", value, "payCondition");
            return (Criteria) this;
        }

        public Criteria andPayConditionIn(List<String> values) {
            addCriterion("PAY_CONDITION in", values, "payCondition");
            return (Criteria) this;
        }

        public Criteria andPayConditionNotIn(List<String> values) {
            addCriterion("PAY_CONDITION not in", values, "payCondition");
            return (Criteria) this;
        }

        public Criteria andPayConditionBetween(String value1, String value2) {
            addCriterion("PAY_CONDITION between", value1, value2, "payCondition");
            return (Criteria) this;
        }

        public Criteria andPayConditionNotBetween(String value1, String value2) {
            addCriterion("PAY_CONDITION not between", value1, value2, "payCondition");
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

        public Criteria andFreeReasonIsNull() {
            addCriterion("FREE_REASON is null");
            return (Criteria) this;
        }

        public Criteria andFreeReasonIsNotNull() {
            addCriterion("FREE_REASON is not null");
            return (Criteria) this;
        }

        public Criteria andFreeReasonEqualTo(String value) {
            addCriterion("FREE_REASON =", value, "freeReason");
            return (Criteria) this;
        }

        public Criteria andFreeReasonNotEqualTo(String value) {
            addCriterion("FREE_REASON <>", value, "freeReason");
            return (Criteria) this;
        }

        public Criteria andFreeReasonGreaterThan(String value) {
            addCriterion("FREE_REASON >", value, "freeReason");
            return (Criteria) this;
        }

        public Criteria andFreeReasonGreaterThanOrEqualTo(String value) {
            addCriterion("FREE_REASON >=", value, "freeReason");
            return (Criteria) this;
        }

        public Criteria andFreeReasonLessThan(String value) {
            addCriterion("FREE_REASON <", value, "freeReason");
            return (Criteria) this;
        }

        public Criteria andFreeReasonLessThanOrEqualTo(String value) {
            addCriterion("FREE_REASON <=", value, "freeReason");
            return (Criteria) this;
        }

        public Criteria andFreeReasonLike(String value) {
            addCriterion("FREE_REASON like", value, "freeReason");
            return (Criteria) this;
        }

        public Criteria andFreeReasonNotLike(String value) {
            addCriterion("FREE_REASON not like", value, "freeReason");
            return (Criteria) this;
        }

        public Criteria andFreeReasonIn(List<String> values) {
            addCriterion("FREE_REASON in", values, "freeReason");
            return (Criteria) this;
        }

        public Criteria andFreeReasonNotIn(List<String> values) {
            addCriterion("FREE_REASON not in", values, "freeReason");
            return (Criteria) this;
        }

        public Criteria andFreeReasonBetween(String value1, String value2) {
            addCriterion("FREE_REASON between", value1, value2, "freeReason");
            return (Criteria) this;
        }

        public Criteria andFreeReasonNotBetween(String value1, String value2) {
            addCriterion("FREE_REASON not between", value1, value2, "freeReason");
            return (Criteria) this;
        }

        public Criteria andPayTranMoneyIsNull() {
            addCriterion("PAY_TRAN_MONEY is null");
            return (Criteria) this;
        }

        public Criteria andPayTranMoneyIsNotNull() {
            addCriterion("PAY_TRAN_MONEY is not null");
            return (Criteria) this;
        }

        public Criteria andPayTranMoneyEqualTo(BigDecimal value) {
            addCriterion("PAY_TRAN_MONEY =", value, "payTranMoney");
            return (Criteria) this;
        }

        public Criteria andPayTranMoneyNotEqualTo(BigDecimal value) {
            addCriterion("PAY_TRAN_MONEY <>", value, "payTranMoney");
            return (Criteria) this;
        }

        public Criteria andPayTranMoneyGreaterThan(BigDecimal value) {
            addCriterion("PAY_TRAN_MONEY >", value, "payTranMoney");
            return (Criteria) this;
        }

        public Criteria andPayTranMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_TRAN_MONEY >=", value, "payTranMoney");
            return (Criteria) this;
        }

        public Criteria andPayTranMoneyLessThan(BigDecimal value) {
            addCriterion("PAY_TRAN_MONEY <", value, "payTranMoney");
            return (Criteria) this;
        }

        public Criteria andPayTranMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_TRAN_MONEY <=", value, "payTranMoney");
            return (Criteria) this;
        }

        public Criteria andPayTranMoneyIn(List<BigDecimal> values) {
            addCriterion("PAY_TRAN_MONEY in", values, "payTranMoney");
            return (Criteria) this;
        }

        public Criteria andPayTranMoneyNotIn(List<BigDecimal> values) {
            addCriterion("PAY_TRAN_MONEY not in", values, "payTranMoney");
            return (Criteria) this;
        }

        public Criteria andPayTranMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_TRAN_MONEY between", value1, value2, "payTranMoney");
            return (Criteria) this;
        }

        public Criteria andPayTranMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_TRAN_MONEY not between", value1, value2, "payTranMoney");
            return (Criteria) this;
        }

        public Criteria andImportTimeIsNull() {
            addCriterion("IMPORT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andImportTimeIsNotNull() {
            addCriterion("IMPORT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andImportTimeEqualTo(String value) {
            addCriterion("IMPORT_TIME =", value, "importTime");
            return (Criteria) this;
        }

        public Criteria andImportTimeNotEqualTo(String value) {
            addCriterion("IMPORT_TIME <>", value, "importTime");
            return (Criteria) this;
        }

        public Criteria andImportTimeGreaterThan(String value) {
            addCriterion("IMPORT_TIME >", value, "importTime");
            return (Criteria) this;
        }

        public Criteria andImportTimeGreaterThanOrEqualTo(String value) {
            addCriterion("IMPORT_TIME >=", value, "importTime");
            return (Criteria) this;
        }

        public Criteria andImportTimeLessThan(String value) {
            addCriterion("IMPORT_TIME <", value, "importTime");
            return (Criteria) this;
        }

        public Criteria andImportTimeLessThanOrEqualTo(String value) {
            addCriterion("IMPORT_TIME <=", value, "importTime");
            return (Criteria) this;
        }

        public Criteria andImportTimeLike(String value) {
            addCriterion("IMPORT_TIME like", value, "importTime");
            return (Criteria) this;
        }

        public Criteria andImportTimeNotLike(String value) {
            addCriterion("IMPORT_TIME not like", value, "importTime");
            return (Criteria) this;
        }

        public Criteria andImportTimeIn(List<String> values) {
            addCriterion("IMPORT_TIME in", values, "importTime");
            return (Criteria) this;
        }

        public Criteria andImportTimeNotIn(List<String> values) {
            addCriterion("IMPORT_TIME not in", values, "importTime");
            return (Criteria) this;
        }

        public Criteria andImportTimeBetween(String value1, String value2) {
            addCriterion("IMPORT_TIME between", value1, value2, "importTime");
            return (Criteria) this;
        }

        public Criteria andImportTimeNotBetween(String value1, String value2) {
            addCriterion("IMPORT_TIME not between", value1, value2, "importTime");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(String value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(String value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(String value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(String value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLike(String value) {
            addCriterion("UPDATE_TIME like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotLike(String value) {
            addCriterion("UPDATE_TIME not like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<String> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<String> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(String value1, String value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(String value1, String value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonIsNull() {
            addCriterion("UPDATE_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonIsNotNull() {
            addCriterion("UPDATE_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonEqualTo(String value) {
            addCriterion("UPDATE_PERSON =", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonNotEqualTo(String value) {
            addCriterion("UPDATE_PERSON <>", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonGreaterThan(String value) {
            addCriterion("UPDATE_PERSON >", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_PERSON >=", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonLessThan(String value) {
            addCriterion("UPDATE_PERSON <", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_PERSON <=", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonLike(String value) {
            addCriterion("UPDATE_PERSON like", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonNotLike(String value) {
            addCriterion("UPDATE_PERSON not like", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonIn(List<String> values) {
            addCriterion("UPDATE_PERSON in", values, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonNotIn(List<String> values) {
            addCriterion("UPDATE_PERSON not in", values, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonBetween(String value1, String value2) {
            addCriterion("UPDATE_PERSON between", value1, value2, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonNotBetween(String value1, String value2) {
            addCriterion("UPDATE_PERSON not between", value1, value2, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andFreeTimeIsNull() {
            addCriterion("FREE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andFreeTimeIsNotNull() {
            addCriterion("FREE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andFreeTimeEqualTo(String value) {
            addCriterion("FREE_TIME =", value, "freeTime");
            return (Criteria) this;
        }

        public Criteria andFreeTimeNotEqualTo(String value) {
            addCriterion("FREE_TIME <>", value, "freeTime");
            return (Criteria) this;
        }

        public Criteria andFreeTimeGreaterThan(String value) {
            addCriterion("FREE_TIME >", value, "freeTime");
            return (Criteria) this;
        }

        public Criteria andFreeTimeGreaterThanOrEqualTo(String value) {
            addCriterion("FREE_TIME >=", value, "freeTime");
            return (Criteria) this;
        }

        public Criteria andFreeTimeLessThan(String value) {
            addCriterion("FREE_TIME <", value, "freeTime");
            return (Criteria) this;
        }

        public Criteria andFreeTimeLessThanOrEqualTo(String value) {
            addCriterion("FREE_TIME <=", value, "freeTime");
            return (Criteria) this;
        }

        public Criteria andFreeTimeLike(String value) {
            addCriterion("FREE_TIME like", value, "freeTime");
            return (Criteria) this;
        }

        public Criteria andFreeTimeNotLike(String value) {
            addCriterion("FREE_TIME not like", value, "freeTime");
            return (Criteria) this;
        }

        public Criteria andFreeTimeIn(List<String> values) {
            addCriterion("FREE_TIME in", values, "freeTime");
            return (Criteria) this;
        }

        public Criteria andFreeTimeNotIn(List<String> values) {
            addCriterion("FREE_TIME not in", values, "freeTime");
            return (Criteria) this;
        }

        public Criteria andFreeTimeBetween(String value1, String value2) {
            addCriterion("FREE_TIME between", value1, value2, "freeTime");
            return (Criteria) this;
        }

        public Criteria andFreeTimeNotBetween(String value1, String value2) {
            addCriterion("FREE_TIME not between", value1, value2, "freeTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNull() {
            addCriterion("PAY_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("PAY_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(String value) {
            addCriterion("PAY_TIME =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(String value) {
            addCriterion("PAY_TIME <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(String value) {
            addCriterion("PAY_TIME >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_TIME >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(String value) {
            addCriterion("PAY_TIME <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(String value) {
            addCriterion("PAY_TIME <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLike(String value) {
            addCriterion("PAY_TIME like", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotLike(String value) {
            addCriterion("PAY_TIME not like", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<String> values) {
            addCriterion("PAY_TIME in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<String> values) {
            addCriterion("PAY_TIME not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(String value1, String value2) {
            addCriterion("PAY_TIME between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(String value1, String value2) {
            addCriterion("PAY_TIME not between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andSheetOrderIsNull() {
            addCriterion("SHEET_ORDER is null");
            return (Criteria) this;
        }

        public Criteria andSheetOrderIsNotNull() {
            addCriterion("SHEET_ORDER is not null");
            return (Criteria) this;
        }

        public Criteria andSheetOrderEqualTo(BigDecimal value) {
            addCriterion("SHEET_ORDER =", value, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderNotEqualTo(BigDecimal value) {
            addCriterion("SHEET_ORDER <>", value, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderGreaterThan(BigDecimal value) {
            addCriterion("SHEET_ORDER >", value, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SHEET_ORDER >=", value, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderLessThan(BigDecimal value) {
            addCriterion("SHEET_ORDER <", value, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SHEET_ORDER <=", value, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderIn(List<BigDecimal> values) {
            addCriterion("SHEET_ORDER in", values, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderNotIn(List<BigDecimal> values) {
            addCriterion("SHEET_ORDER not in", values, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SHEET_ORDER between", value1, value2, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SHEET_ORDER not between", value1, value2, "sheetOrder");
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