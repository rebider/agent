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

        public Criteria andBalanceIdIsNull() {
            addCriterion("BALANCE_ID is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIdIsNotNull() {
            addCriterion("BALANCE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceIdEqualTo(String value) {
            addCriterion("BALANCE_ID =", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdNotEqualTo(String value) {
            addCriterion("BALANCE_ID <>", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdGreaterThan(String value) {
            addCriterion("BALANCE_ID >", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_ID >=", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdLessThan(String value) {
            addCriterion("BALANCE_ID <", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_ID <=", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdLike(String value) {
            addCriterion("BALANCE_ID like", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdNotLike(String value) {
            addCriterion("BALANCE_ID not like", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdIn(List<String> values) {
            addCriterion("BALANCE_ID in", values, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdNotIn(List<String> values) {
            addCriterion("BALANCE_ID not in", values, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdBetween(String value1, String value2) {
            addCriterion("BALANCE_ID between", value1, value2, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdNotBetween(String value1, String value2) {
            addCriterion("BALANCE_ID not between", value1, value2, "balanceId");
            return (Criteria) this;
        }

        public Criteria andSettleMonthIsNull() {
            addCriterion("SETTLE_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andSettleMonthIsNotNull() {
            addCriterion("SETTLE_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andSettleMonthEqualTo(String value) {
            addCriterion("SETTLE_MONTH =", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthNotEqualTo(String value) {
            addCriterion("SETTLE_MONTH <>", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthGreaterThan(String value) {
            addCriterion("SETTLE_MONTH >", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthGreaterThanOrEqualTo(String value) {
            addCriterion("SETTLE_MONTH >=", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthLessThan(String value) {
            addCriterion("SETTLE_MONTH <", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthLessThanOrEqualTo(String value) {
            addCriterion("SETTLE_MONTH <=", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthLike(String value) {
            addCriterion("SETTLE_MONTH like", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthNotLike(String value) {
            addCriterion("SETTLE_MONTH not like", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthIn(List<String> values) {
            addCriterion("SETTLE_MONTH in", values, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthNotIn(List<String> values) {
            addCriterion("SETTLE_MONTH not in", values, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthBetween(String value1, String value2) {
            addCriterion("SETTLE_MONTH between", value1, value2, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthNotBetween(String value1, String value2) {
            addCriterion("SETTLE_MONTH not between", value1, value2, "settleMonth");
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

        public Criteria andBillStatusIsNull() {
            addCriterion("BILL_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andBillStatusIsNotNull() {
            addCriterion("BILL_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andBillStatusEqualTo(String value) {
            addCriterion("BILL_STATUS =", value, "billStatus");
            return (Criteria) this;
        }

        public Criteria andBillStatusNotEqualTo(String value) {
            addCriterion("BILL_STATUS <>", value, "billStatus");
            return (Criteria) this;
        }

        public Criteria andBillStatusGreaterThan(String value) {
            addCriterion("BILL_STATUS >", value, "billStatus");
            return (Criteria) this;
        }

        public Criteria andBillStatusGreaterThanOrEqualTo(String value) {
            addCriterion("BILL_STATUS >=", value, "billStatus");
            return (Criteria) this;
        }

        public Criteria andBillStatusLessThan(String value) {
            addCriterion("BILL_STATUS <", value, "billStatus");
            return (Criteria) this;
        }

        public Criteria andBillStatusLessThanOrEqualTo(String value) {
            addCriterion("BILL_STATUS <=", value, "billStatus");
            return (Criteria) this;
        }

        public Criteria andBillStatusLike(String value) {
            addCriterion("BILL_STATUS like", value, "billStatus");
            return (Criteria) this;
        }

        public Criteria andBillStatusNotLike(String value) {
            addCriterion("BILL_STATUS not like", value, "billStatus");
            return (Criteria) this;
        }

        public Criteria andBillStatusIn(List<String> values) {
            addCriterion("BILL_STATUS in", values, "billStatus");
            return (Criteria) this;
        }

        public Criteria andBillStatusNotIn(List<String> values) {
            addCriterion("BILL_STATUS not in", values, "billStatus");
            return (Criteria) this;
        }

        public Criteria andBillStatusBetween(String value1, String value2) {
            addCriterion("BILL_STATUS between", value1, value2, "billStatus");
            return (Criteria) this;
        }

        public Criteria andBillStatusNotBetween(String value1, String value2) {
            addCriterion("BILL_STATUS not between", value1, value2, "billStatus");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtIsNull() {
            addCriterion("BALANCE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtIsNotNull() {
            addCriterion("BALANCE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT =", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtNotEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT <>", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtGreaterThan(BigDecimal value) {
            addCriterion("BALANCE_AMT >", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT >=", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtLessThan(BigDecimal value) {
            addCriterion("BALANCE_AMT <", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT <=", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtIn(List<BigDecimal> values) {
            addCriterion("BALANCE_AMT in", values, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtNotIn(List<BigDecimal> values) {
            addCriterion("BALANCE_AMT not in", values, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BALANCE_AMT between", value1, value2, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BALANCE_AMT not between", value1, value2, "balanceAmt");
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

        public Criteria andBatchNoIsNull() {
            addCriterion("BATCH_NO is null");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNotNull() {
            addCriterion("BATCH_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNoEqualTo(String value) {
            addCriterion("BATCH_NO =", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotEqualTo(String value) {
            addCriterion("BATCH_NO <>", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThan(String value) {
            addCriterion("BATCH_NO >", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThanOrEqualTo(String value) {
            addCriterion("BATCH_NO >=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThan(String value) {
            addCriterion("BATCH_NO <", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThanOrEqualTo(String value) {
            addCriterion("BATCH_NO <=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLike(String value) {
            addCriterion("BATCH_NO like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotLike(String value) {
            addCriterion("BATCH_NO not like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoIn(List<String> values) {
            addCriterion("BATCH_NO in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotIn(List<String> values) {
            addCriterion("BATCH_NO not in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoBetween(String value1, String value2) {
            addCriterion("BATCH_NO between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotBetween(String value1, String value2) {
            addCriterion("BATCH_NO not between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andRemitFailReasonIsNull() {
            addCriterion("REMIT_FAIL_REASON is null");
            return (Criteria) this;
        }

        public Criteria andRemitFailReasonIsNotNull() {
            addCriterion("REMIT_FAIL_REASON is not null");
            return (Criteria) this;
        }

        public Criteria andRemitFailReasonEqualTo(String value) {
            addCriterion("REMIT_FAIL_REASON =", value, "remitFailReason");
            return (Criteria) this;
        }

        public Criteria andRemitFailReasonNotEqualTo(String value) {
            addCriterion("REMIT_FAIL_REASON <>", value, "remitFailReason");
            return (Criteria) this;
        }

        public Criteria andRemitFailReasonGreaterThan(String value) {
            addCriterion("REMIT_FAIL_REASON >", value, "remitFailReason");
            return (Criteria) this;
        }

        public Criteria andRemitFailReasonGreaterThanOrEqualTo(String value) {
            addCriterion("REMIT_FAIL_REASON >=", value, "remitFailReason");
            return (Criteria) this;
        }

        public Criteria andRemitFailReasonLessThan(String value) {
            addCriterion("REMIT_FAIL_REASON <", value, "remitFailReason");
            return (Criteria) this;
        }

        public Criteria andRemitFailReasonLessThanOrEqualTo(String value) {
            addCriterion("REMIT_FAIL_REASON <=", value, "remitFailReason");
            return (Criteria) this;
        }

        public Criteria andRemitFailReasonLike(String value) {
            addCriterion("REMIT_FAIL_REASON like", value, "remitFailReason");
            return (Criteria) this;
        }

        public Criteria andRemitFailReasonNotLike(String value) {
            addCriterion("REMIT_FAIL_REASON not like", value, "remitFailReason");
            return (Criteria) this;
        }

        public Criteria andRemitFailReasonIn(List<String> values) {
            addCriterion("REMIT_FAIL_REASON in", values, "remitFailReason");
            return (Criteria) this;
        }

        public Criteria andRemitFailReasonNotIn(List<String> values) {
            addCriterion("REMIT_FAIL_REASON not in", values, "remitFailReason");
            return (Criteria) this;
        }

        public Criteria andRemitFailReasonBetween(String value1, String value2) {
            addCriterion("REMIT_FAIL_REASON between", value1, value2, "remitFailReason");
            return (Criteria) this;
        }

        public Criteria andRemitFailReasonNotBetween(String value1, String value2) {
            addCriterion("REMIT_FAIL_REASON not between", value1, value2, "remitFailReason");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoIsNull() {
            addCriterion("BALANCE_BANK_NO is null");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoIsNotNull() {
            addCriterion("BALANCE_BANK_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoEqualTo(String value) {
            addCriterion("BALANCE_BANK_NO =", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoNotEqualTo(String value) {
            addCriterion("BALANCE_BANK_NO <>", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoGreaterThan(String value) {
            addCriterion("BALANCE_BANK_NO >", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_BANK_NO >=", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoLessThan(String value) {
            addCriterion("BALANCE_BANK_NO <", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_BANK_NO <=", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoLike(String value) {
            addCriterion("BALANCE_BANK_NO like", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoNotLike(String value) {
            addCriterion("BALANCE_BANK_NO not like", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoIn(List<String> values) {
            addCriterion("BALANCE_BANK_NO in", values, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoNotIn(List<String> values) {
            addCriterion("BALANCE_BANK_NO not in", values, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoBetween(String value1, String value2) {
            addCriterion("BALANCE_BANK_NO between", value1, value2, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoNotBetween(String value1, String value2) {
            addCriterion("BALANCE_BANK_NO not between", value1, value2, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameIsNull() {
            addCriterion("BALANCE_BANK_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameIsNotNull() {
            addCriterion("BALANCE_BANK_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameEqualTo(String value) {
            addCriterion("BALANCE_BANK_NAME =", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameNotEqualTo(String value) {
            addCriterion("BALANCE_BANK_NAME <>", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameGreaterThan(String value) {
            addCriterion("BALANCE_BANK_NAME >", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_BANK_NAME >=", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameLessThan(String value) {
            addCriterion("BALANCE_BANK_NAME <", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_BANK_NAME <=", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameLike(String value) {
            addCriterion("BALANCE_BANK_NAME like", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameNotLike(String value) {
            addCriterion("BALANCE_BANK_NAME not like", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameIn(List<String> values) {
            addCriterion("BALANCE_BANK_NAME in", values, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameNotIn(List<String> values) {
            addCriterion("BALANCE_BANK_NAME not in", values, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameBetween(String value1, String value2) {
            addCriterion("BALANCE_BANK_NAME between", value1, value2, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameNotBetween(String value1, String value2) {
            addCriterion("BALANCE_BANK_NAME not between", value1, value2, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameIsNull() {
            addCriterion("BALANCE_ACCT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameIsNotNull() {
            addCriterion("BALANCE_ACCT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NAME =", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameNotEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NAME <>", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameGreaterThan(String value) {
            addCriterion("BALANCE_ACCT_NAME >", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NAME >=", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameLessThan(String value) {
            addCriterion("BALANCE_ACCT_NAME <", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NAME <=", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameLike(String value) {
            addCriterion("BALANCE_ACCT_NAME like", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameNotLike(String value) {
            addCriterion("BALANCE_ACCT_NAME not like", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameIn(List<String> values) {
            addCriterion("BALANCE_ACCT_NAME in", values, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameNotIn(List<String> values) {
            addCriterion("BALANCE_ACCT_NAME not in", values, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameBetween(String value1, String value2) {
            addCriterion("BALANCE_ACCT_NAME between", value1, value2, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameNotBetween(String value1, String value2) {
            addCriterion("BALANCE_ACCT_NAME not between", value1, value2, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoIsNull() {
            addCriterion("BALANCE_ACCT_NO is null");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoIsNotNull() {
            addCriterion("BALANCE_ACCT_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NO =", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoNotEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NO <>", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoGreaterThan(String value) {
            addCriterion("BALANCE_ACCT_NO >", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NO >=", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoLessThan(String value) {
            addCriterion("BALANCE_ACCT_NO <", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NO <=", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoLike(String value) {
            addCriterion("BALANCE_ACCT_NO like", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoNotLike(String value) {
            addCriterion("BALANCE_ACCT_NO not like", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoIn(List<String> values) {
            addCriterion("BALANCE_ACCT_NO in", values, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoNotIn(List<String> values) {
            addCriterion("BALANCE_ACCT_NO not in", values, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoBetween(String value1, String value2) {
            addCriterion("BALANCE_ACCT_NO between", value1, value2, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoNotBetween(String value1, String value2) {
            addCriterion("BALANCE_ACCT_NO not between", value1, value2, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andSunbmitCheckTimeIsNull() {
            addCriterion("SUNBMIT_CHECK_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSunbmitCheckTimeIsNotNull() {
            addCriterion("SUNBMIT_CHECK_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSunbmitCheckTimeEqualTo(String value) {
            addCriterion("SUNBMIT_CHECK_TIME =", value, "sunbmitCheckTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitCheckTimeNotEqualTo(String value) {
            addCriterion("SUNBMIT_CHECK_TIME <>", value, "sunbmitCheckTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitCheckTimeGreaterThan(String value) {
            addCriterion("SUNBMIT_CHECK_TIME >", value, "sunbmitCheckTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitCheckTimeGreaterThanOrEqualTo(String value) {
            addCriterion("SUNBMIT_CHECK_TIME >=", value, "sunbmitCheckTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitCheckTimeLessThan(String value) {
            addCriterion("SUNBMIT_CHECK_TIME <", value, "sunbmitCheckTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitCheckTimeLessThanOrEqualTo(String value) {
            addCriterion("SUNBMIT_CHECK_TIME <=", value, "sunbmitCheckTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitCheckTimeLike(String value) {
            addCriterion("SUNBMIT_CHECK_TIME like", value, "sunbmitCheckTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitCheckTimeNotLike(String value) {
            addCriterion("SUNBMIT_CHECK_TIME not like", value, "sunbmitCheckTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitCheckTimeIn(List<String> values) {
            addCriterion("SUNBMIT_CHECK_TIME in", values, "sunbmitCheckTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitCheckTimeNotIn(List<String> values) {
            addCriterion("SUNBMIT_CHECK_TIME not in", values, "sunbmitCheckTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitCheckTimeBetween(String value1, String value2) {
            addCriterion("SUNBMIT_CHECK_TIME between", value1, value2, "sunbmitCheckTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitCheckTimeNotBetween(String value1, String value2) {
            addCriterion("SUNBMIT_CHECK_TIME not between", value1, value2, "sunbmitCheckTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitRemitTimeIsNull() {
            addCriterion("SUNBMIT_REMIT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSunbmitRemitTimeIsNotNull() {
            addCriterion("SUNBMIT_REMIT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSunbmitRemitTimeEqualTo(String value) {
            addCriterion("SUNBMIT_REMIT_TIME =", value, "sunbmitRemitTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitRemitTimeNotEqualTo(String value) {
            addCriterion("SUNBMIT_REMIT_TIME <>", value, "sunbmitRemitTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitRemitTimeGreaterThan(String value) {
            addCriterion("SUNBMIT_REMIT_TIME >", value, "sunbmitRemitTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitRemitTimeGreaterThanOrEqualTo(String value) {
            addCriterion("SUNBMIT_REMIT_TIME >=", value, "sunbmitRemitTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitRemitTimeLessThan(String value) {
            addCriterion("SUNBMIT_REMIT_TIME <", value, "sunbmitRemitTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitRemitTimeLessThanOrEqualTo(String value) {
            addCriterion("SUNBMIT_REMIT_TIME <=", value, "sunbmitRemitTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitRemitTimeLike(String value) {
            addCriterion("SUNBMIT_REMIT_TIME like", value, "sunbmitRemitTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitRemitTimeNotLike(String value) {
            addCriterion("SUNBMIT_REMIT_TIME not like", value, "sunbmitRemitTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitRemitTimeIn(List<String> values) {
            addCriterion("SUNBMIT_REMIT_TIME in", values, "sunbmitRemitTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitRemitTimeNotIn(List<String> values) {
            addCriterion("SUNBMIT_REMIT_TIME not in", values, "sunbmitRemitTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitRemitTimeBetween(String value1, String value2) {
            addCriterion("SUNBMIT_REMIT_TIME between", value1, value2, "sunbmitRemitTime");
            return (Criteria) this;
        }

        public Criteria andSunbmitRemitTimeNotBetween(String value1, String value2) {
            addCriterion("SUNBMIT_REMIT_TIME not between", value1, value2, "sunbmitRemitTime");
            return (Criteria) this;
        }

        public Criteria andSettleDateIsNull() {
            addCriterion("SETTLE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andSettleDateIsNotNull() {
            addCriterion("SETTLE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andSettleDateEqualTo(String value) {
            addCriterion("SETTLE_DATE =", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotEqualTo(String value) {
            addCriterion("SETTLE_DATE <>", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateGreaterThan(String value) {
            addCriterion("SETTLE_DATE >", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateGreaterThanOrEqualTo(String value) {
            addCriterion("SETTLE_DATE >=", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLessThan(String value) {
            addCriterion("SETTLE_DATE <", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLessThanOrEqualTo(String value) {
            addCriterion("SETTLE_DATE <=", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLike(String value) {
            addCriterion("SETTLE_DATE like", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotLike(String value) {
            addCriterion("SETTLE_DATE not like", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateIn(List<String> values) {
            addCriterion("SETTLE_DATE in", values, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotIn(List<String> values) {
            addCriterion("SETTLE_DATE not in", values, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateBetween(String value1, String value2) {
            addCriterion("SETTLE_DATE between", value1, value2, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotBetween(String value1, String value2) {
            addCriterion("SETTLE_DATE not between", value1, value2, "settleDate");
            return (Criteria) this;
        }

        public Criteria andImportBatchIsNull() {
            addCriterion("IMPORT_BATCH is null");
            return (Criteria) this;
        }

        public Criteria andImportBatchIsNotNull() {
            addCriterion("IMPORT_BATCH is not null");
            return (Criteria) this;
        }

        public Criteria andImportBatchEqualTo(String value) {
            addCriterion("IMPORT_BATCH =", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchNotEqualTo(String value) {
            addCriterion("IMPORT_BATCH <>", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchGreaterThan(String value) {
            addCriterion("IMPORT_BATCH >", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchGreaterThanOrEqualTo(String value) {
            addCriterion("IMPORT_BATCH >=", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchLessThan(String value) {
            addCriterion("IMPORT_BATCH <", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchLessThanOrEqualTo(String value) {
            addCriterion("IMPORT_BATCH <=", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchLike(String value) {
            addCriterion("IMPORT_BATCH like", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchNotLike(String value) {
            addCriterion("IMPORT_BATCH not like", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchIn(List<String> values) {
            addCriterion("IMPORT_BATCH in", values, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchNotIn(List<String> values) {
            addCriterion("IMPORT_BATCH not in", values, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchBetween(String value1, String value2) {
            addCriterion("IMPORT_BATCH between", value1, value2, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchNotBetween(String value1, String value2) {
            addCriterion("IMPORT_BATCH not between", value1, value2, "importBatch");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdIsNull() {
            addCriterion("REALITY_AG_ID is null");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdIsNotNull() {
            addCriterion("REALITY_AG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdEqualTo(String value) {
            addCriterion("REALITY_AG_ID =", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdNotEqualTo(String value) {
            addCriterion("REALITY_AG_ID <>", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdGreaterThan(String value) {
            addCriterion("REALITY_AG_ID >", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdGreaterThanOrEqualTo(String value) {
            addCriterion("REALITY_AG_ID >=", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdLessThan(String value) {
            addCriterion("REALITY_AG_ID <", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdLessThanOrEqualTo(String value) {
            addCriterion("REALITY_AG_ID <=", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdLike(String value) {
            addCriterion("REALITY_AG_ID like", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdNotLike(String value) {
            addCriterion("REALITY_AG_ID not like", value, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdIn(List<String> values) {
            addCriterion("REALITY_AG_ID in", values, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdNotIn(List<String> values) {
            addCriterion("REALITY_AG_ID not in", values, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdBetween(String value1, String value2) {
            addCriterion("REALITY_AG_ID between", value1, value2, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgIdNotBetween(String value1, String value2) {
            addCriterion("REALITY_AG_ID not between", value1, value2, "realityAgId");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameIsNull() {
            addCriterion("REALITY_AG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameIsNotNull() {
            addCriterion("REALITY_AG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameEqualTo(String value) {
            addCriterion("REALITY_AG_NAME =", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameNotEqualTo(String value) {
            addCriterion("REALITY_AG_NAME <>", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameGreaterThan(String value) {
            addCriterion("REALITY_AG_NAME >", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameGreaterThanOrEqualTo(String value) {
            addCriterion("REALITY_AG_NAME >=", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameLessThan(String value) {
            addCriterion("REALITY_AG_NAME <", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameLessThanOrEqualTo(String value) {
            addCriterion("REALITY_AG_NAME <=", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameLike(String value) {
            addCriterion("REALITY_AG_NAME like", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameNotLike(String value) {
            addCriterion("REALITY_AG_NAME not like", value, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameIn(List<String> values) {
            addCriterion("REALITY_AG_NAME in", values, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameNotIn(List<String> values) {
            addCriterion("REALITY_AG_NAME not in", values, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameBetween(String value1, String value2) {
            addCriterion("REALITY_AG_NAME between", value1, value2, "realityAgName");
            return (Criteria) this;
        }

        public Criteria andRealityAgNameNotBetween(String value1, String value2) {
            addCriterion("REALITY_AG_NAME not between", value1, value2, "realityAgName");
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