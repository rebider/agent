package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CapitalExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CapitalExample() {
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

        public Criteria andCTypeIsNull() {
            addCriterion("C_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCTypeIsNotNull() {
            addCriterion("C_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCTypeEqualTo(String value) {
            addCriterion("C_TYPE =", value, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeNotEqualTo(String value) {
            addCriterion("C_TYPE <>", value, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeGreaterThan(String value) {
            addCriterion("C_TYPE >", value, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeGreaterThanOrEqualTo(String value) {
            addCriterion("C_TYPE >=", value, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeLessThan(String value) {
            addCriterion("C_TYPE <", value, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeLessThanOrEqualTo(String value) {
            addCriterion("C_TYPE <=", value, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeLike(String value) {
            addCriterion("C_TYPE like", value, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeNotLike(String value) {
            addCriterion("C_TYPE not like", value, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeIn(List<String> values) {
            addCriterion("C_TYPE in", values, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeNotIn(List<String> values) {
            addCriterion("C_TYPE not in", values, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeBetween(String value1, String value2) {
            addCriterion("C_TYPE between", value1, value2, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeNotBetween(String value1, String value2) {
            addCriterion("C_TYPE not between", value1, value2, "cType");
            return (Criteria) this;
        }

        public Criteria andCAmountIsNull() {
            addCriterion("C_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andCAmountIsNotNull() {
            addCriterion("C_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andCAmountEqualTo(BigDecimal value) {
            addCriterion("C_AMOUNT =", value, "cAmount");
            return (Criteria) this;
        }

        public Criteria andCAmountNotEqualTo(BigDecimal value) {
            addCriterion("C_AMOUNT <>", value, "cAmount");
            return (Criteria) this;
        }

        public Criteria andCAmountGreaterThan(BigDecimal value) {
            addCriterion("C_AMOUNT >", value, "cAmount");
            return (Criteria) this;
        }

        public Criteria andCAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("C_AMOUNT >=", value, "cAmount");
            return (Criteria) this;
        }

        public Criteria andCAmountLessThan(BigDecimal value) {
            addCriterion("C_AMOUNT <", value, "cAmount");
            return (Criteria) this;
        }

        public Criteria andCAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("C_AMOUNT <=", value, "cAmount");
            return (Criteria) this;
        }

        public Criteria andCAmountIn(List<BigDecimal> values) {
            addCriterion("C_AMOUNT in", values, "cAmount");
            return (Criteria) this;
        }

        public Criteria andCAmountNotIn(List<BigDecimal> values) {
            addCriterion("C_AMOUNT not in", values, "cAmount");
            return (Criteria) this;
        }

        public Criteria andCAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_AMOUNT between", value1, value2, "cAmount");
            return (Criteria) this;
        }

        public Criteria andCAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_AMOUNT not between", value1, value2, "cAmount");
            return (Criteria) this;
        }

        public Criteria andCIsinIsNull() {
            addCriterion("C_ISIN is null");
            return (Criteria) this;
        }

        public Criteria andCIsinIsNotNull() {
            addCriterion("C_ISIN is not null");
            return (Criteria) this;
        }

        public Criteria andCIsinEqualTo(BigDecimal value) {
            addCriterion("C_ISIN =", value, "cIsin");
            return (Criteria) this;
        }

        public Criteria andCIsinNotEqualTo(BigDecimal value) {
            addCriterion("C_ISIN <>", value, "cIsin");
            return (Criteria) this;
        }

        public Criteria andCIsinGreaterThan(BigDecimal value) {
            addCriterion("C_ISIN >", value, "cIsin");
            return (Criteria) this;
        }

        public Criteria andCIsinGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("C_ISIN >=", value, "cIsin");
            return (Criteria) this;
        }

        public Criteria andCIsinLessThan(BigDecimal value) {
            addCriterion("C_ISIN <", value, "cIsin");
            return (Criteria) this;
        }

        public Criteria andCIsinLessThanOrEqualTo(BigDecimal value) {
            addCriterion("C_ISIN <=", value, "cIsin");
            return (Criteria) this;
        }

        public Criteria andCIsinIn(List<BigDecimal> values) {
            addCriterion("C_ISIN in", values, "cIsin");
            return (Criteria) this;
        }

        public Criteria andCIsinNotIn(List<BigDecimal> values) {
            addCriterion("C_ISIN not in", values, "cIsin");
            return (Criteria) this;
        }

        public Criteria andCIsinBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_ISIN between", value1, value2, "cIsin");
            return (Criteria) this;
        }

        public Criteria andCIsinNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_ISIN not between", value1, value2, "cIsin");
            return (Criteria) this;
        }

        public Criteria andCInAmountIsNull() {
            addCriterion("C_IN_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andCInAmountIsNotNull() {
            addCriterion("C_IN_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andCInAmountEqualTo(BigDecimal value) {
            addCriterion("C_IN_AMOUNT =", value, "cInAmount");
            return (Criteria) this;
        }

        public Criteria andCInAmountNotEqualTo(BigDecimal value) {
            addCriterion("C_IN_AMOUNT <>", value, "cInAmount");
            return (Criteria) this;
        }

        public Criteria andCInAmountGreaterThan(BigDecimal value) {
            addCriterion("C_IN_AMOUNT >", value, "cInAmount");
            return (Criteria) this;
        }

        public Criteria andCInAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("C_IN_AMOUNT >=", value, "cInAmount");
            return (Criteria) this;
        }

        public Criteria andCInAmountLessThan(BigDecimal value) {
            addCriterion("C_IN_AMOUNT <", value, "cInAmount");
            return (Criteria) this;
        }

        public Criteria andCInAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("C_IN_AMOUNT <=", value, "cInAmount");
            return (Criteria) this;
        }

        public Criteria andCInAmountIn(List<BigDecimal> values) {
            addCriterion("C_IN_AMOUNT in", values, "cInAmount");
            return (Criteria) this;
        }

        public Criteria andCInAmountNotIn(List<BigDecimal> values) {
            addCriterion("C_IN_AMOUNT not in", values, "cInAmount");
            return (Criteria) this;
        }

        public Criteria andCInAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_IN_AMOUNT between", value1, value2, "cInAmount");
            return (Criteria) this;
        }

        public Criteria andCInAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_IN_AMOUNT not between", value1, value2, "cInAmount");
            return (Criteria) this;
        }

        public Criteria andCBusStatusIsNull() {
            addCriterion("C_BUS_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andCBusStatusIsNotNull() {
            addCriterion("C_BUS_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andCBusStatusEqualTo(BigDecimal value) {
            addCriterion("C_BUS_STATUS =", value, "cBusStatus");
            return (Criteria) this;
        }

        public Criteria andCBusStatusNotEqualTo(BigDecimal value) {
            addCriterion("C_BUS_STATUS <>", value, "cBusStatus");
            return (Criteria) this;
        }

        public Criteria andCBusStatusGreaterThan(BigDecimal value) {
            addCriterion("C_BUS_STATUS >", value, "cBusStatus");
            return (Criteria) this;
        }

        public Criteria andCBusStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("C_BUS_STATUS >=", value, "cBusStatus");
            return (Criteria) this;
        }

        public Criteria andCBusStatusLessThan(BigDecimal value) {
            addCriterion("C_BUS_STATUS <", value, "cBusStatus");
            return (Criteria) this;
        }

        public Criteria andCBusStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("C_BUS_STATUS <=", value, "cBusStatus");
            return (Criteria) this;
        }

        public Criteria andCBusStatusIn(List<BigDecimal> values) {
            addCriterion("C_BUS_STATUS in", values, "cBusStatus");
            return (Criteria) this;
        }

        public Criteria andCBusStatusNotIn(List<BigDecimal> values) {
            addCriterion("C_BUS_STATUS not in", values, "cBusStatus");
            return (Criteria) this;
        }

        public Criteria andCBusStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_BUS_STATUS between", value1, value2, "cBusStatus");
            return (Criteria) this;
        }

        public Criteria andCBusStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_BUS_STATUS not between", value1, value2, "cBusStatus");
            return (Criteria) this;
        }

        public Criteria andCSrcIsNull() {
            addCriterion("C_SRC is null");
            return (Criteria) this;
        }

        public Criteria andCSrcIsNotNull() {
            addCriterion("C_SRC is not null");
            return (Criteria) this;
        }

        public Criteria andCSrcEqualTo(String value) {
            addCriterion("C_SRC =", value, "cSrc");
            return (Criteria) this;
        }

        public Criteria andCSrcNotEqualTo(String value) {
            addCriterion("C_SRC <>", value, "cSrc");
            return (Criteria) this;
        }

        public Criteria andCSrcGreaterThan(String value) {
            addCriterion("C_SRC >", value, "cSrc");
            return (Criteria) this;
        }

        public Criteria andCSrcGreaterThanOrEqualTo(String value) {
            addCriterion("C_SRC >=", value, "cSrc");
            return (Criteria) this;
        }

        public Criteria andCSrcLessThan(String value) {
            addCriterion("C_SRC <", value, "cSrc");
            return (Criteria) this;
        }

        public Criteria andCSrcLessThanOrEqualTo(String value) {
            addCriterion("C_SRC <=", value, "cSrc");
            return (Criteria) this;
        }

        public Criteria andCSrcLike(String value) {
            addCriterion("C_SRC like", value, "cSrc");
            return (Criteria) this;
        }

        public Criteria andCSrcNotLike(String value) {
            addCriterion("C_SRC not like", value, "cSrc");
            return (Criteria) this;
        }

        public Criteria andCSrcIn(List<String> values) {
            addCriterion("C_SRC in", values, "cSrc");
            return (Criteria) this;
        }

        public Criteria andCSrcNotIn(List<String> values) {
            addCriterion("C_SRC not in", values, "cSrc");
            return (Criteria) this;
        }

        public Criteria andCSrcBetween(String value1, String value2) {
            addCriterion("C_SRC between", value1, value2, "cSrc");
            return (Criteria) this;
        }

        public Criteria andCSrcNotBetween(String value1, String value2) {
            addCriterion("C_SRC not between", value1, value2, "cSrc");
            return (Criteria) this;
        }

        public Criteria andCPlanintimeIsNull() {
            addCriterion("C_PLANINTIME is null");
            return (Criteria) this;
        }

        public Criteria andCPlanintimeIsNotNull() {
            addCriterion("C_PLANINTIME is not null");
            return (Criteria) this;
        }

        public Criteria andCPlanintimeEqualTo(Date value) {
            addCriterion("C_PLANINTIME =", value, "cPlanintime");
            return (Criteria) this;
        }

        public Criteria andCPlanintimeNotEqualTo(Date value) {
            addCriterion("C_PLANINTIME <>", value, "cPlanintime");
            return (Criteria) this;
        }

        public Criteria andCPlanintimeGreaterThan(Date value) {
            addCriterion("C_PLANINTIME >", value, "cPlanintime");
            return (Criteria) this;
        }

        public Criteria andCPlanintimeGreaterThanOrEqualTo(Date value) {
            addCriterion("C_PLANINTIME >=", value, "cPlanintime");
            return (Criteria) this;
        }

        public Criteria andCPlanintimeLessThan(Date value) {
            addCriterion("C_PLANINTIME <", value, "cPlanintime");
            return (Criteria) this;
        }

        public Criteria andCPlanintimeLessThanOrEqualTo(Date value) {
            addCriterion("C_PLANINTIME <=", value, "cPlanintime");
            return (Criteria) this;
        }

        public Criteria andCPlanintimeIn(List<Date> values) {
            addCriterion("C_PLANINTIME in", values, "cPlanintime");
            return (Criteria) this;
        }

        public Criteria andCPlanintimeNotIn(List<Date> values) {
            addCriterion("C_PLANINTIME not in", values, "cPlanintime");
            return (Criteria) this;
        }

        public Criteria andCPlanintimeBetween(Date value1, Date value2) {
            addCriterion("C_PLANINTIME between", value1, value2, "cPlanintime");
            return (Criteria) this;
        }

        public Criteria andCPlanintimeNotBetween(Date value1, Date value2) {
            addCriterion("C_PLANINTIME not between", value1, value2, "cPlanintime");
            return (Criteria) this;
        }

        public Criteria andCIntimeIsNull() {
            addCriterion("C_INTIME is null");
            return (Criteria) this;
        }

        public Criteria andCIntimeIsNotNull() {
            addCriterion("C_INTIME is not null");
            return (Criteria) this;
        }

        public Criteria andCIntimeEqualTo(Date value) {
            addCriterion("C_INTIME =", value, "cIntime");
            return (Criteria) this;
        }

        public Criteria andCIntimeNotEqualTo(Date value) {
            addCriterion("C_INTIME <>", value, "cIntime");
            return (Criteria) this;
        }

        public Criteria andCIntimeGreaterThan(Date value) {
            addCriterion("C_INTIME >", value, "cIntime");
            return (Criteria) this;
        }

        public Criteria andCIntimeGreaterThanOrEqualTo(Date value) {
            addCriterion("C_INTIME >=", value, "cIntime");
            return (Criteria) this;
        }

        public Criteria andCIntimeLessThan(Date value) {
            addCriterion("C_INTIME <", value, "cIntime");
            return (Criteria) this;
        }

        public Criteria andCIntimeLessThanOrEqualTo(Date value) {
            addCriterion("C_INTIME <=", value, "cIntime");
            return (Criteria) this;
        }

        public Criteria andCIntimeIn(List<Date> values) {
            addCriterion("C_INTIME in", values, "cIntime");
            return (Criteria) this;
        }

        public Criteria andCIntimeNotIn(List<Date> values) {
            addCriterion("C_INTIME not in", values, "cIntime");
            return (Criteria) this;
        }

        public Criteria andCIntimeBetween(Date value1, Date value2) {
            addCriterion("C_INTIME between", value1, value2, "cIntime");
            return (Criteria) this;
        }

        public Criteria andCIntimeNotBetween(Date value1, Date value2) {
            addCriterion("C_INTIME not between", value1, value2, "cIntime");
            return (Criteria) this;
        }

        public Criteria andCPaytimeIsNull() {
            addCriterion("C_PAYTIME is null");
            return (Criteria) this;
        }

        public Criteria andCPaytimeIsNotNull() {
            addCriterion("C_PAYTIME is not null");
            return (Criteria) this;
        }

        public Criteria andCPaytimeEqualTo(Date value) {
            addCriterion("C_PAYTIME =", value, "cPaytime");
            return (Criteria) this;
        }

        public Criteria andCPaytimeNotEqualTo(Date value) {
            addCriterion("C_PAYTIME <>", value, "cPaytime");
            return (Criteria) this;
        }

        public Criteria andCPaytimeGreaterThan(Date value) {
            addCriterion("C_PAYTIME >", value, "cPaytime");
            return (Criteria) this;
        }

        public Criteria andCPaytimeGreaterThanOrEqualTo(Date value) {
            addCriterion("C_PAYTIME >=", value, "cPaytime");
            return (Criteria) this;
        }

        public Criteria andCPaytimeLessThan(Date value) {
            addCriterion("C_PAYTIME <", value, "cPaytime");
            return (Criteria) this;
        }

        public Criteria andCPaytimeLessThanOrEqualTo(Date value) {
            addCriterion("C_PAYTIME <=", value, "cPaytime");
            return (Criteria) this;
        }

        public Criteria andCPaytimeIn(List<Date> values) {
            addCriterion("C_PAYTIME in", values, "cPaytime");
            return (Criteria) this;
        }

        public Criteria andCPaytimeNotIn(List<Date> values) {
            addCriterion("C_PAYTIME not in", values, "cPaytime");
            return (Criteria) this;
        }

        public Criteria andCPaytimeBetween(Date value1, Date value2) {
            addCriterion("C_PAYTIME between", value1, value2, "cPaytime");
            return (Criteria) this;
        }

        public Criteria andCPaytimeNotBetween(Date value1, Date value2) {
            addCriterion("C_PAYTIME not between", value1, value2, "cPaytime");
            return (Criteria) this;
        }

        public Criteria andCTimeIsNull() {
            addCriterion("C_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCTimeIsNotNull() {
            addCriterion("C_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCTimeEqualTo(Date value) {
            addCriterion("C_TIME =", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotEqualTo(Date value) {
            addCriterion("C_TIME <>", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeGreaterThan(Date value) {
            addCriterion("C_TIME >", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("C_TIME >=", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeLessThan(Date value) {
            addCriterion("C_TIME <", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeLessThanOrEqualTo(Date value) {
            addCriterion("C_TIME <=", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeIn(List<Date> values) {
            addCriterion("C_TIME in", values, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotIn(List<Date> values) {
            addCriterion("C_TIME not in", values, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeBetween(Date value1, Date value2) {
            addCriterion("C_TIME between", value1, value2, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotBetween(Date value1, Date value2) {
            addCriterion("C_TIME not between", value1, value2, "cTime");
            return (Criteria) this;
        }

        public Criteria andCUtimeIsNull() {
            addCriterion("C_UTIME is null");
            return (Criteria) this;
        }

        public Criteria andCUtimeIsNotNull() {
            addCriterion("C_UTIME is not null");
            return (Criteria) this;
        }

        public Criteria andCUtimeEqualTo(Date value) {
            addCriterion("C_UTIME =", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeNotEqualTo(Date value) {
            addCriterion("C_UTIME <>", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeGreaterThan(Date value) {
            addCriterion("C_UTIME >", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("C_UTIME >=", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeLessThan(Date value) {
            addCriterion("C_UTIME <", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeLessThanOrEqualTo(Date value) {
            addCriterion("C_UTIME <=", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeIn(List<Date> values) {
            addCriterion("C_UTIME in", values, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeNotIn(List<Date> values) {
            addCriterion("C_UTIME not in", values, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeBetween(Date value1, Date value2) {
            addCriterion("C_UTIME between", value1, value2, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeNotBetween(Date value1, Date value2) {
            addCriterion("C_UTIME not between", value1, value2, "cUtime");
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

        public Criteria andCAgentIdIsNull() {
            addCriterion("C_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andCAgentIdIsNotNull() {
            addCriterion("C_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCAgentIdEqualTo(String value) {
            addCriterion("C_AGENT_ID =", value, "cAgentId");
            return (Criteria) this;
        }

        public Criteria andCAgentIdNotEqualTo(String value) {
            addCriterion("C_AGENT_ID <>", value, "cAgentId");
            return (Criteria) this;
        }

        public Criteria andCAgentIdGreaterThan(String value) {
            addCriterion("C_AGENT_ID >", value, "cAgentId");
            return (Criteria) this;
        }

        public Criteria andCAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("C_AGENT_ID >=", value, "cAgentId");
            return (Criteria) this;
        }

        public Criteria andCAgentIdLessThan(String value) {
            addCriterion("C_AGENT_ID <", value, "cAgentId");
            return (Criteria) this;
        }

        public Criteria andCAgentIdLessThanOrEqualTo(String value) {
            addCriterion("C_AGENT_ID <=", value, "cAgentId");
            return (Criteria) this;
        }

        public Criteria andCAgentIdLike(String value) {
            addCriterion("C_AGENT_ID like", value, "cAgentId");
            return (Criteria) this;
        }

        public Criteria andCAgentIdNotLike(String value) {
            addCriterion("C_AGENT_ID not like", value, "cAgentId");
            return (Criteria) this;
        }

        public Criteria andCAgentIdIn(List<String> values) {
            addCriterion("C_AGENT_ID in", values, "cAgentId");
            return (Criteria) this;
        }

        public Criteria andCAgentIdNotIn(List<String> values) {
            addCriterion("C_AGENT_ID not in", values, "cAgentId");
            return (Criteria) this;
        }

        public Criteria andCAgentIdBetween(String value1, String value2) {
            addCriterion("C_AGENT_ID between", value1, value2, "cAgentId");
            return (Criteria) this;
        }

        public Criteria andCAgentIdNotBetween(String value1, String value2) {
            addCriterion("C_AGENT_ID not between", value1, value2, "cAgentId");
            return (Criteria) this;
        }

        public Criteria andCUserIsNull() {
            addCriterion("C_USER is null");
            return (Criteria) this;
        }

        public Criteria andCUserIsNotNull() {
            addCriterion("C_USER is not null");
            return (Criteria) this;
        }

        public Criteria andCUserEqualTo(String value) {
            addCriterion("C_USER =", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotEqualTo(String value) {
            addCriterion("C_USER <>", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserGreaterThan(String value) {
            addCriterion("C_USER >", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserGreaterThanOrEqualTo(String value) {
            addCriterion("C_USER >=", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserLessThan(String value) {
            addCriterion("C_USER <", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserLessThanOrEqualTo(String value) {
            addCriterion("C_USER <=", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserLike(String value) {
            addCriterion("C_USER like", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotLike(String value) {
            addCriterion("C_USER not like", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserIn(List<String> values) {
            addCriterion("C_USER in", values, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotIn(List<String> values) {
            addCriterion("C_USER not in", values, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserBetween(String value1, String value2) {
            addCriterion("C_USER between", value1, value2, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotBetween(String value1, String value2) {
            addCriterion("C_USER not between", value1, value2, "cUser");
            return (Criteria) this;
        }

        public Criteria andCPayuserIsNull() {
            addCriterion("C_PAYUSER is null");
            return (Criteria) this;
        }

        public Criteria andCPayuserIsNotNull() {
            addCriterion("C_PAYUSER is not null");
            return (Criteria) this;
        }

        public Criteria andCPayuserEqualTo(String value) {
            addCriterion("C_PAYUSER =", value, "cPayuser");
            return (Criteria) this;
        }

        public Criteria andCPayuserNotEqualTo(String value) {
            addCriterion("C_PAYUSER <>", value, "cPayuser");
            return (Criteria) this;
        }

        public Criteria andCPayuserGreaterThan(String value) {
            addCriterion("C_PAYUSER >", value, "cPayuser");
            return (Criteria) this;
        }

        public Criteria andCPayuserGreaterThanOrEqualTo(String value) {
            addCriterion("C_PAYUSER >=", value, "cPayuser");
            return (Criteria) this;
        }

        public Criteria andCPayuserLessThan(String value) {
            addCriterion("C_PAYUSER <", value, "cPayuser");
            return (Criteria) this;
        }

        public Criteria andCPayuserLessThanOrEqualTo(String value) {
            addCriterion("C_PAYUSER <=", value, "cPayuser");
            return (Criteria) this;
        }

        public Criteria andCPayuserLike(String value) {
            addCriterion("C_PAYUSER like", value, "cPayuser");
            return (Criteria) this;
        }

        public Criteria andCPayuserNotLike(String value) {
            addCriterion("C_PAYUSER not like", value, "cPayuser");
            return (Criteria) this;
        }

        public Criteria andCPayuserIn(List<String> values) {
            addCriterion("C_PAYUSER in", values, "cPayuser");
            return (Criteria) this;
        }

        public Criteria andCPayuserNotIn(List<String> values) {
            addCriterion("C_PAYUSER not in", values, "cPayuser");
            return (Criteria) this;
        }

        public Criteria andCPayuserBetween(String value1, String value2) {
            addCriterion("C_PAYUSER between", value1, value2, "cPayuser");
            return (Criteria) this;
        }

        public Criteria andCPayuserNotBetween(String value1, String value2) {
            addCriterion("C_PAYUSER not between", value1, value2, "cPayuser");
            return (Criteria) this;
        }

        public Criteria andCFqCountIsNull() {
            addCriterion("C_FQ_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andCFqCountIsNotNull() {
            addCriterion("C_FQ_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andCFqCountEqualTo(BigDecimal value) {
            addCriterion("C_FQ_COUNT =", value, "cFqCount");
            return (Criteria) this;
        }

        public Criteria andCFqCountNotEqualTo(BigDecimal value) {
            addCriterion("C_FQ_COUNT <>", value, "cFqCount");
            return (Criteria) this;
        }

        public Criteria andCFqCountGreaterThan(BigDecimal value) {
            addCriterion("C_FQ_COUNT >", value, "cFqCount");
            return (Criteria) this;
        }

        public Criteria andCFqCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("C_FQ_COUNT >=", value, "cFqCount");
            return (Criteria) this;
        }

        public Criteria andCFqCountLessThan(BigDecimal value) {
            addCriterion("C_FQ_COUNT <", value, "cFqCount");
            return (Criteria) this;
        }

        public Criteria andCFqCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("C_FQ_COUNT <=", value, "cFqCount");
            return (Criteria) this;
        }

        public Criteria andCFqCountIn(List<BigDecimal> values) {
            addCriterion("C_FQ_COUNT in", values, "cFqCount");
            return (Criteria) this;
        }

        public Criteria andCFqCountNotIn(List<BigDecimal> values) {
            addCriterion("C_FQ_COUNT not in", values, "cFqCount");
            return (Criteria) this;
        }

        public Criteria andCFqCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_FQ_COUNT between", value1, value2, "cFqCount");
            return (Criteria) this;
        }

        public Criteria andCFqCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_FQ_COUNT not between", value1, value2, "cFqCount");
            return (Criteria) this;
        }

        public Criteria andCPayTypeIsNull() {
            addCriterion("C_PAY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCPayTypeIsNotNull() {
            addCriterion("C_PAY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCPayTypeEqualTo(String value) {
            addCriterion("C_PAY_TYPE =", value, "cPayType");
            return (Criteria) this;
        }

        public Criteria andCPayTypeNotEqualTo(String value) {
            addCriterion("C_PAY_TYPE <>", value, "cPayType");
            return (Criteria) this;
        }

        public Criteria andCPayTypeGreaterThan(String value) {
            addCriterion("C_PAY_TYPE >", value, "cPayType");
            return (Criteria) this;
        }

        public Criteria andCPayTypeGreaterThanOrEqualTo(String value) {
            addCriterion("C_PAY_TYPE >=", value, "cPayType");
            return (Criteria) this;
        }

        public Criteria andCPayTypeLessThan(String value) {
            addCriterion("C_PAY_TYPE <", value, "cPayType");
            return (Criteria) this;
        }

        public Criteria andCPayTypeLessThanOrEqualTo(String value) {
            addCriterion("C_PAY_TYPE <=", value, "cPayType");
            return (Criteria) this;
        }

        public Criteria andCPayTypeLike(String value) {
            addCriterion("C_PAY_TYPE like", value, "cPayType");
            return (Criteria) this;
        }

        public Criteria andCPayTypeNotLike(String value) {
            addCriterion("C_PAY_TYPE not like", value, "cPayType");
            return (Criteria) this;
        }

        public Criteria andCPayTypeIn(List<String> values) {
            addCriterion("C_PAY_TYPE in", values, "cPayType");
            return (Criteria) this;
        }

        public Criteria andCPayTypeNotIn(List<String> values) {
            addCriterion("C_PAY_TYPE not in", values, "cPayType");
            return (Criteria) this;
        }

        public Criteria andCPayTypeBetween(String value1, String value2) {
            addCriterion("C_PAY_TYPE between", value1, value2, "cPayType");
            return (Criteria) this;
        }

        public Criteria andCPayTypeNotBetween(String value1, String value2) {
            addCriterion("C_PAY_TYPE not between", value1, value2, "cPayType");
            return (Criteria) this;
        }

        public Criteria andCInComIsNull() {
            addCriterion("C_IN_COM is null");
            return (Criteria) this;
        }

        public Criteria andCInComIsNotNull() {
            addCriterion("C_IN_COM is not null");
            return (Criteria) this;
        }

        public Criteria andCInComEqualTo(String value) {
            addCriterion("C_IN_COM =", value, "cInCom");
            return (Criteria) this;
        }

        public Criteria andCInComNotEqualTo(String value) {
            addCriterion("C_IN_COM <>", value, "cInCom");
            return (Criteria) this;
        }

        public Criteria andCInComGreaterThan(String value) {
            addCriterion("C_IN_COM >", value, "cInCom");
            return (Criteria) this;
        }

        public Criteria andCInComGreaterThanOrEqualTo(String value) {
            addCriterion("C_IN_COM >=", value, "cInCom");
            return (Criteria) this;
        }

        public Criteria andCInComLessThan(String value) {
            addCriterion("C_IN_COM <", value, "cInCom");
            return (Criteria) this;
        }

        public Criteria andCInComLessThanOrEqualTo(String value) {
            addCriterion("C_IN_COM <=", value, "cInCom");
            return (Criteria) this;
        }

        public Criteria andCInComLike(String value) {
            addCriterion("C_IN_COM like", value, "cInCom");
            return (Criteria) this;
        }

        public Criteria andCInComNotLike(String value) {
            addCriterion("C_IN_COM not like", value, "cInCom");
            return (Criteria) this;
        }

        public Criteria andCInComIn(List<String> values) {
            addCriterion("C_IN_COM in", values, "cInCom");
            return (Criteria) this;
        }

        public Criteria andCInComNotIn(List<String> values) {
            addCriterion("C_IN_COM not in", values, "cInCom");
            return (Criteria) this;
        }

        public Criteria andCInComBetween(String value1, String value2) {
            addCriterion("C_IN_COM between", value1, value2, "cInCom");
            return (Criteria) this;
        }

        public Criteria andCInComNotBetween(String value1, String value2) {
            addCriterion("C_IN_COM not between", value1, value2, "cInCom");
            return (Criteria) this;
        }

        public Criteria andCFqInAmountIsNull() {
            addCriterion("C_FQ_IN_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andCFqInAmountIsNotNull() {
            addCriterion("C_FQ_IN_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andCFqInAmountEqualTo(BigDecimal value) {
            addCriterion("C_FQ_IN_AMOUNT =", value, "cFqInAmount");
            return (Criteria) this;
        }

        public Criteria andCFqInAmountNotEqualTo(BigDecimal value) {
            addCriterion("C_FQ_IN_AMOUNT <>", value, "cFqInAmount");
            return (Criteria) this;
        }

        public Criteria andCFqInAmountGreaterThan(BigDecimal value) {
            addCriterion("C_FQ_IN_AMOUNT >", value, "cFqInAmount");
            return (Criteria) this;
        }

        public Criteria andCFqInAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("C_FQ_IN_AMOUNT >=", value, "cFqInAmount");
            return (Criteria) this;
        }

        public Criteria andCFqInAmountLessThan(BigDecimal value) {
            addCriterion("C_FQ_IN_AMOUNT <", value, "cFqInAmount");
            return (Criteria) this;
        }

        public Criteria andCFqInAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("C_FQ_IN_AMOUNT <=", value, "cFqInAmount");
            return (Criteria) this;
        }

        public Criteria andCFqInAmountIn(List<BigDecimal> values) {
            addCriterion("C_FQ_IN_AMOUNT in", values, "cFqInAmount");
            return (Criteria) this;
        }

        public Criteria andCFqInAmountNotIn(List<BigDecimal> values) {
            addCriterion("C_FQ_IN_AMOUNT not in", values, "cFqInAmount");
            return (Criteria) this;
        }

        public Criteria andCFqInAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_FQ_IN_AMOUNT between", value1, value2, "cFqInAmount");
            return (Criteria) this;
        }

        public Criteria andCFqInAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_FQ_IN_AMOUNT not between", value1, value2, "cFqInAmount");
            return (Criteria) this;
        }

        public Criteria andCFqDateIsNull() {
            addCriterion("C_FQ_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCFqDateIsNotNull() {
            addCriterion("C_FQ_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCFqDateEqualTo(Date value) {
            addCriterion("C_FQ_DATE =", value, "cFqDate");
            return (Criteria) this;
        }

        public Criteria andCFqDateNotEqualTo(Date value) {
            addCriterion("C_FQ_DATE <>", value, "cFqDate");
            return (Criteria) this;
        }

        public Criteria andCFqDateGreaterThan(Date value) {
            addCriterion("C_FQ_DATE >", value, "cFqDate");
            return (Criteria) this;
        }

        public Criteria andCFqDateGreaterThanOrEqualTo(Date value) {
            addCriterion("C_FQ_DATE >=", value, "cFqDate");
            return (Criteria) this;
        }

        public Criteria andCFqDateLessThan(Date value) {
            addCriterion("C_FQ_DATE <", value, "cFqDate");
            return (Criteria) this;
        }

        public Criteria andCFqDateLessThanOrEqualTo(Date value) {
            addCriterion("C_FQ_DATE <=", value, "cFqDate");
            return (Criteria) this;
        }

        public Criteria andCFqDateIn(List<Date> values) {
            addCriterion("C_FQ_DATE in", values, "cFqDate");
            return (Criteria) this;
        }

        public Criteria andCFqDateNotIn(List<Date> values) {
            addCriterion("C_FQ_DATE not in", values, "cFqDate");
            return (Criteria) this;
        }

        public Criteria andCFqDateBetween(Date value1, Date value2) {
            addCriterion("C_FQ_DATE between", value1, value2, "cFqDate");
            return (Criteria) this;
        }

        public Criteria andCFqDateNotBetween(Date value1, Date value2) {
            addCriterion("C_FQ_DATE not between", value1, value2, "cFqDate");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusIsNull() {
            addCriterion("CLO_REVIEW_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusIsNotNull() {
            addCriterion("CLO_REVIEW_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS =", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS <>", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusGreaterThan(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS >", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS >=", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusLessThan(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS <", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS <=", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusIn(List<BigDecimal> values) {
            addCriterion("CLO_REVIEW_STATUS in", values, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotIn(List<BigDecimal> values) {
            addCriterion("CLO_REVIEW_STATUS not in", values, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_REVIEW_STATUS between", value1, value2, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_REVIEW_STATUS not between", value1, value2, "cloReviewStatus");
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