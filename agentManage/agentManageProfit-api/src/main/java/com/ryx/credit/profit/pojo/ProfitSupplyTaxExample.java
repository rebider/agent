package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProfitSupplyTaxExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ProfitSupplyTaxExample() {
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

        public Criteria andSupplyTaxDateIsNull() {
            addCriterion("SUPPLY_TAX_DATE is null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxDateIsNotNull() {
            addCriterion("SUPPLY_TAX_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxDateEqualTo(String value) {
            addCriterion("SUPPLY_TAX_DATE =", value, "supplyTaxDate");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxDateNotEqualTo(String value) {
            addCriterion("SUPPLY_TAX_DATE <>", value, "supplyTaxDate");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxDateGreaterThan(String value) {
            addCriterion("SUPPLY_TAX_DATE >", value, "supplyTaxDate");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxDateGreaterThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TAX_DATE >=", value, "supplyTaxDate");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxDateLessThan(String value) {
            addCriterion("SUPPLY_TAX_DATE <", value, "supplyTaxDate");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxDateLessThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TAX_DATE <=", value, "supplyTaxDate");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxDateLike(String value) {
            addCriterion("SUPPLY_TAX_DATE like", value, "supplyTaxDate");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxDateNotLike(String value) {
            addCriterion("SUPPLY_TAX_DATE not like", value, "supplyTaxDate");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxDateIn(List<String> values) {
            addCriterion("SUPPLY_TAX_DATE in", values, "supplyTaxDate");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxDateNotIn(List<String> values) {
            addCriterion("SUPPLY_TAX_DATE not in", values, "supplyTaxDate");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxDateBetween(String value1, String value2) {
            addCriterion("SUPPLY_TAX_DATE between", value1, value2, "supplyTaxDate");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxDateNotBetween(String value1, String value2) {
            addCriterion("SUPPLY_TAX_DATE not between", value1, value2, "supplyTaxDate");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentIdIsNull() {
            addCriterion("SUPPLY_TAX_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentIdIsNotNull() {
            addCriterion("SUPPLY_TAX_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentIdEqualTo(String value) {
            addCriterion("SUPPLY_TAX_AGENT_ID =", value, "supplyTaxAgentId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentIdNotEqualTo(String value) {
            addCriterion("SUPPLY_TAX_AGENT_ID <>", value, "supplyTaxAgentId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentIdGreaterThan(String value) {
            addCriterion("SUPPLY_TAX_AGENT_ID >", value, "supplyTaxAgentId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TAX_AGENT_ID >=", value, "supplyTaxAgentId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentIdLessThan(String value) {
            addCriterion("SUPPLY_TAX_AGENT_ID <", value, "supplyTaxAgentId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentIdLessThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TAX_AGENT_ID <=", value, "supplyTaxAgentId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentIdLike(String value) {
            addCriterion("SUPPLY_TAX_AGENT_ID like", value, "supplyTaxAgentId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentIdNotLike(String value) {
            addCriterion("SUPPLY_TAX_AGENT_ID not like", value, "supplyTaxAgentId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentIdIn(List<String> values) {
            addCriterion("SUPPLY_TAX_AGENT_ID in", values, "supplyTaxAgentId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentIdNotIn(List<String> values) {
            addCriterion("SUPPLY_TAX_AGENT_ID not in", values, "supplyTaxAgentId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentIdBetween(String value1, String value2) {
            addCriterion("SUPPLY_TAX_AGENT_ID between", value1, value2, "supplyTaxAgentId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentIdNotBetween(String value1, String value2) {
            addCriterion("SUPPLY_TAX_AGENT_ID not between", value1, value2, "supplyTaxAgentId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentNameIsNull() {
            addCriterion("SUPPLY_TAX_AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentNameIsNotNull() {
            addCriterion("SUPPLY_TAX_AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentNameEqualTo(String value) {
            addCriterion("SUPPLY_TAX_AGENT_NAME =", value, "supplyTaxAgentName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentNameNotEqualTo(String value) {
            addCriterion("SUPPLY_TAX_AGENT_NAME <>", value, "supplyTaxAgentName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentNameGreaterThan(String value) {
            addCriterion("SUPPLY_TAX_AGENT_NAME >", value, "supplyTaxAgentName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TAX_AGENT_NAME >=", value, "supplyTaxAgentName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentNameLessThan(String value) {
            addCriterion("SUPPLY_TAX_AGENT_NAME <", value, "supplyTaxAgentName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentNameLessThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TAX_AGENT_NAME <=", value, "supplyTaxAgentName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentNameLike(String value) {
            addCriterion("SUPPLY_TAX_AGENT_NAME like", value, "supplyTaxAgentName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentNameNotLike(String value) {
            addCriterion("SUPPLY_TAX_AGENT_NAME not like", value, "supplyTaxAgentName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentNameIn(List<String> values) {
            addCriterion("SUPPLY_TAX_AGENT_NAME in", values, "supplyTaxAgentName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentNameNotIn(List<String> values) {
            addCriterion("SUPPLY_TAX_AGENT_NAME not in", values, "supplyTaxAgentName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentNameBetween(String value1, String value2) {
            addCriterion("SUPPLY_TAX_AGENT_NAME between", value1, value2, "supplyTaxAgentName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAgentNameNotBetween(String value1, String value2) {
            addCriterion("SUPPLY_TAX_AGENT_NAME not between", value1, value2, "supplyTaxAgentName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubIdIsNull() {
            addCriterion("SUPPLY_TAX_SUB_ID is null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubIdIsNotNull() {
            addCriterion("SUPPLY_TAX_SUB_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubIdEqualTo(String value) {
            addCriterion("SUPPLY_TAX_SUB_ID =", value, "supplyTaxSubId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubIdNotEqualTo(String value) {
            addCriterion("SUPPLY_TAX_SUB_ID <>", value, "supplyTaxSubId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubIdGreaterThan(String value) {
            addCriterion("SUPPLY_TAX_SUB_ID >", value, "supplyTaxSubId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TAX_SUB_ID >=", value, "supplyTaxSubId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubIdLessThan(String value) {
            addCriterion("SUPPLY_TAX_SUB_ID <", value, "supplyTaxSubId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubIdLessThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TAX_SUB_ID <=", value, "supplyTaxSubId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubIdLike(String value) {
            addCriterion("SUPPLY_TAX_SUB_ID like", value, "supplyTaxSubId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubIdNotLike(String value) {
            addCriterion("SUPPLY_TAX_SUB_ID not like", value, "supplyTaxSubId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubIdIn(List<String> values) {
            addCriterion("SUPPLY_TAX_SUB_ID in", values, "supplyTaxSubId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubIdNotIn(List<String> values) {
            addCriterion("SUPPLY_TAX_SUB_ID not in", values, "supplyTaxSubId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubIdBetween(String value1, String value2) {
            addCriterion("SUPPLY_TAX_SUB_ID between", value1, value2, "supplyTaxSubId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubIdNotBetween(String value1, String value2) {
            addCriterion("SUPPLY_TAX_SUB_ID not between", value1, value2, "supplyTaxSubId");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubNameIsNull() {
            addCriterion("SUPPLY_TAX_SUB_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubNameIsNotNull() {
            addCriterion("SUPPLY_TAX_SUB_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubNameEqualTo(String value) {
            addCriterion("SUPPLY_TAX_SUB_NAME =", value, "supplyTaxSubName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubNameNotEqualTo(String value) {
            addCriterion("SUPPLY_TAX_SUB_NAME <>", value, "supplyTaxSubName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubNameGreaterThan(String value) {
            addCriterion("SUPPLY_TAX_SUB_NAME >", value, "supplyTaxSubName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubNameGreaterThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TAX_SUB_NAME >=", value, "supplyTaxSubName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubNameLessThan(String value) {
            addCriterion("SUPPLY_TAX_SUB_NAME <", value, "supplyTaxSubName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubNameLessThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TAX_SUB_NAME <=", value, "supplyTaxSubName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubNameLike(String value) {
            addCriterion("SUPPLY_TAX_SUB_NAME like", value, "supplyTaxSubName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubNameNotLike(String value) {
            addCriterion("SUPPLY_TAX_SUB_NAME not like", value, "supplyTaxSubName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubNameIn(List<String> values) {
            addCriterion("SUPPLY_TAX_SUB_NAME in", values, "supplyTaxSubName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubNameNotIn(List<String> values) {
            addCriterion("SUPPLY_TAX_SUB_NAME not in", values, "supplyTaxSubName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubNameBetween(String value1, String value2) {
            addCriterion("SUPPLY_TAX_SUB_NAME between", value1, value2, "supplyTaxSubName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxSubNameNotBetween(String value1, String value2) {
            addCriterion("SUPPLY_TAX_SUB_NAME not between", value1, value2, "supplyTaxSubName");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtIsNull() {
            addCriterion("SUPPLY_TAX_AMT is null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtIsNotNull() {
            addCriterion("SUPPLY_TAX_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_TAX_AMT =", value, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtNotEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_TAX_AMT <>", value, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtGreaterThan(BigDecimal value) {
            addCriterion("SUPPLY_TAX_AMT >", value, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_TAX_AMT >=", value, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtLessThan(BigDecimal value) {
            addCriterion("SUPPLY_TAX_AMT <", value, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUPPLY_TAX_AMT <=", value, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtIn(List<BigDecimal> values) {
            addCriterion("SUPPLY_TAX_AMT in", values, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtNotIn(List<BigDecimal> values) {
            addCriterion("SUPPLY_TAX_AMT not in", values, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPPLY_TAX_AMT between", value1, value2, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUPPLY_TAX_AMT not between", value1, value2, "supplyTaxAmt");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxTypeIsNull() {
            addCriterion("SUPPLY_TAX_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxTypeIsNotNull() {
            addCriterion("SUPPLY_TAX_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxTypeEqualTo(String value) {
            addCriterion("SUPPLY_TAX_TYPE =", value, "supplyTaxType");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxTypeNotEqualTo(String value) {
            addCriterion("SUPPLY_TAX_TYPE <>", value, "supplyTaxType");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxTypeGreaterThan(String value) {
            addCriterion("SUPPLY_TAX_TYPE >", value, "supplyTaxType");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxTypeGreaterThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TAX_TYPE >=", value, "supplyTaxType");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxTypeLessThan(String value) {
            addCriterion("SUPPLY_TAX_TYPE <", value, "supplyTaxType");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxTypeLessThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TAX_TYPE <=", value, "supplyTaxType");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxTypeLike(String value) {
            addCriterion("SUPPLY_TAX_TYPE like", value, "supplyTaxType");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxTypeNotLike(String value) {
            addCriterion("SUPPLY_TAX_TYPE not like", value, "supplyTaxType");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxTypeIn(List<String> values) {
            addCriterion("SUPPLY_TAX_TYPE in", values, "supplyTaxType");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxTypeNotIn(List<String> values) {
            addCriterion("SUPPLY_TAX_TYPE not in", values, "supplyTaxType");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxTypeBetween(String value1, String value2) {
            addCriterion("SUPPLY_TAX_TYPE between", value1, value2, "supplyTaxType");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxTypeNotBetween(String value1, String value2) {
            addCriterion("SUPPLY_TAX_TYPE not between", value1, value2, "supplyTaxType");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxPlatformIsNull() {
            addCriterion("SUPPLY_TAX_PLATFORM is null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxPlatformIsNotNull() {
            addCriterion("SUPPLY_TAX_PLATFORM is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxPlatformEqualTo(String value) {
            addCriterion("SUPPLY_TAX_PLATFORM =", value, "supplyTaxPlatform");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxPlatformNotEqualTo(String value) {
            addCriterion("SUPPLY_TAX_PLATFORM <>", value, "supplyTaxPlatform");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxPlatformGreaterThan(String value) {
            addCriterion("SUPPLY_TAX_PLATFORM >", value, "supplyTaxPlatform");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxPlatformGreaterThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TAX_PLATFORM >=", value, "supplyTaxPlatform");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxPlatformLessThan(String value) {
            addCriterion("SUPPLY_TAX_PLATFORM <", value, "supplyTaxPlatform");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxPlatformLessThanOrEqualTo(String value) {
            addCriterion("SUPPLY_TAX_PLATFORM <=", value, "supplyTaxPlatform");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxPlatformLike(String value) {
            addCriterion("SUPPLY_TAX_PLATFORM like", value, "supplyTaxPlatform");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxPlatformNotLike(String value) {
            addCriterion("SUPPLY_TAX_PLATFORM not like", value, "supplyTaxPlatform");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxPlatformIn(List<String> values) {
            addCriterion("SUPPLY_TAX_PLATFORM in", values, "supplyTaxPlatform");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxPlatformNotIn(List<String> values) {
            addCriterion("SUPPLY_TAX_PLATFORM not in", values, "supplyTaxPlatform");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxPlatformBetween(String value1, String value2) {
            addCriterion("SUPPLY_TAX_PLATFORM between", value1, value2, "supplyTaxPlatform");
            return (Criteria) this;
        }

        public Criteria andSupplyTaxPlatformNotBetween(String value1, String value2) {
            addCriterion("SUPPLY_TAX_PLATFORM not between", value1, value2, "supplyTaxPlatform");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("CREATE_TIME like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("CREATE_TIME not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
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