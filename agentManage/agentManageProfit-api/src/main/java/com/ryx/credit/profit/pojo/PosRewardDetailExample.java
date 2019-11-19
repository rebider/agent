package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PosRewardDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PosRewardDetailExample() {
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

        public Criteria andProfitPosDateIsNull() {
            addCriterion("PROFIT_POS_DATE is null");
            return (Criteria) this;
        }

        public Criteria andProfitPosDateIsNotNull() {
            addCriterion("PROFIT_POS_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andProfitPosDateEqualTo(String value) {
            addCriterion("PROFIT_POS_DATE =", value, "profitPosDate");
            return (Criteria) this;
        }

        public Criteria andProfitPosDateNotEqualTo(String value) {
            addCriterion("PROFIT_POS_DATE <>", value, "profitPosDate");
            return (Criteria) this;
        }

        public Criteria andProfitPosDateGreaterThan(String value) {
            addCriterion("PROFIT_POS_DATE >", value, "profitPosDate");
            return (Criteria) this;
        }

        public Criteria andProfitPosDateGreaterThanOrEqualTo(String value) {
            addCriterion("PROFIT_POS_DATE >=", value, "profitPosDate");
            return (Criteria) this;
        }

        public Criteria andProfitPosDateLessThan(String value) {
            addCriterion("PROFIT_POS_DATE <", value, "profitPosDate");
            return (Criteria) this;
        }

        public Criteria andProfitPosDateLessThanOrEqualTo(String value) {
            addCriterion("PROFIT_POS_DATE <=", value, "profitPosDate");
            return (Criteria) this;
        }

        public Criteria andProfitPosDateLike(String value) {
            addCriterion("PROFIT_POS_DATE like", value, "profitPosDate");
            return (Criteria) this;
        }

        public Criteria andProfitPosDateNotLike(String value) {
            addCriterion("PROFIT_POS_DATE not like", value, "profitPosDate");
            return (Criteria) this;
        }

        public Criteria andProfitPosDateIn(List<String> values) {
            addCriterion("PROFIT_POS_DATE in", values, "profitPosDate");
            return (Criteria) this;
        }

        public Criteria andProfitPosDateNotIn(List<String> values) {
            addCriterion("PROFIT_POS_DATE not in", values, "profitPosDate");
            return (Criteria) this;
        }

        public Criteria andProfitPosDateBetween(String value1, String value2) {
            addCriterion("PROFIT_POS_DATE between", value1, value2, "profitPosDate");
            return (Criteria) this;
        }

        public Criteria andProfitPosDateNotBetween(String value1, String value2) {
            addCriterion("PROFIT_POS_DATE not between", value1, value2, "profitPosDate");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdIsNull() {
            addCriterion("POS_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdIsNotNull() {
            addCriterion("POS_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdEqualTo(String value) {
            addCriterion("POS_AGENT_ID =", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdNotEqualTo(String value) {
            addCriterion("POS_AGENT_ID <>", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdGreaterThan(String value) {
            addCriterion("POS_AGENT_ID >", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("POS_AGENT_ID >=", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdLessThan(String value) {
            addCriterion("POS_AGENT_ID <", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdLessThanOrEqualTo(String value) {
            addCriterion("POS_AGENT_ID <=", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdLike(String value) {
            addCriterion("POS_AGENT_ID like", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdNotLike(String value) {
            addCriterion("POS_AGENT_ID not like", value, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdIn(List<String> values) {
            addCriterion("POS_AGENT_ID in", values, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdNotIn(List<String> values) {
            addCriterion("POS_AGENT_ID not in", values, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdBetween(String value1, String value2) {
            addCriterion("POS_AGENT_ID between", value1, value2, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentIdNotBetween(String value1, String value2) {
            addCriterion("POS_AGENT_ID not between", value1, value2, "posAgentId");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameIsNull() {
            addCriterion("POS_AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameIsNotNull() {
            addCriterion("POS_AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameEqualTo(String value) {
            addCriterion("POS_AGENT_NAME =", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameNotEqualTo(String value) {
            addCriterion("POS_AGENT_NAME <>", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameGreaterThan(String value) {
            addCriterion("POS_AGENT_NAME >", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("POS_AGENT_NAME >=", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameLessThan(String value) {
            addCriterion("POS_AGENT_NAME <", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameLessThanOrEqualTo(String value) {
            addCriterion("POS_AGENT_NAME <=", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameLike(String value) {
            addCriterion("POS_AGENT_NAME like", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameNotLike(String value) {
            addCriterion("POS_AGENT_NAME not like", value, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameIn(List<String> values) {
            addCriterion("POS_AGENT_NAME in", values, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameNotIn(List<String> values) {
            addCriterion("POS_AGENT_NAME not in", values, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameBetween(String value1, String value2) {
            addCriterion("POS_AGENT_NAME between", value1, value2, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosAgentNameNotBetween(String value1, String value2) {
            addCriterion("POS_AGENT_NAME not between", value1, value2, "posAgentName");
            return (Criteria) this;
        }

        public Criteria andPosMechanismTypeIsNull() {
            addCriterion("POS_MECHANISM_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPosMechanismTypeIsNotNull() {
            addCriterion("POS_MECHANISM_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPosMechanismTypeEqualTo(String value) {
            addCriterion("POS_MECHANISM_TYPE =", value, "posMechanismType");
            return (Criteria) this;
        }

        public Criteria andPosMechanismTypeNotEqualTo(String value) {
            addCriterion("POS_MECHANISM_TYPE <>", value, "posMechanismType");
            return (Criteria) this;
        }

        public Criteria andPosMechanismTypeGreaterThan(String value) {
            addCriterion("POS_MECHANISM_TYPE >", value, "posMechanismType");
            return (Criteria) this;
        }

        public Criteria andPosMechanismTypeGreaterThanOrEqualTo(String value) {
            addCriterion("POS_MECHANISM_TYPE >=", value, "posMechanismType");
            return (Criteria) this;
        }

        public Criteria andPosMechanismTypeLessThan(String value) {
            addCriterion("POS_MECHANISM_TYPE <", value, "posMechanismType");
            return (Criteria) this;
        }

        public Criteria andPosMechanismTypeLessThanOrEqualTo(String value) {
            addCriterion("POS_MECHANISM_TYPE <=", value, "posMechanismType");
            return (Criteria) this;
        }

        public Criteria andPosMechanismTypeLike(String value) {
            addCriterion("POS_MECHANISM_TYPE like", value, "posMechanismType");
            return (Criteria) this;
        }

        public Criteria andPosMechanismTypeNotLike(String value) {
            addCriterion("POS_MECHANISM_TYPE not like", value, "posMechanismType");
            return (Criteria) this;
        }

        public Criteria andPosMechanismTypeIn(List<String> values) {
            addCriterion("POS_MECHANISM_TYPE in", values, "posMechanismType");
            return (Criteria) this;
        }

        public Criteria andPosMechanismTypeNotIn(List<String> values) {
            addCriterion("POS_MECHANISM_TYPE not in", values, "posMechanismType");
            return (Criteria) this;
        }

        public Criteria andPosMechanismTypeBetween(String value1, String value2) {
            addCriterion("POS_MECHANISM_TYPE between", value1, value2, "posMechanismType");
            return (Criteria) this;
        }

        public Criteria andPosMechanismTypeNotBetween(String value1, String value2) {
            addCriterion("POS_MECHANISM_TYPE not between", value1, value2, "posMechanismType");
            return (Criteria) this;
        }

        public Criteria andPosMechanismIdIsNull() {
            addCriterion("POS_MECHANISM_ID is null");
            return (Criteria) this;
        }

        public Criteria andPosMechanismIdIsNotNull() {
            addCriterion("POS_MECHANISM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPosMechanismIdEqualTo(String value) {
            addCriterion("POS_MECHANISM_ID =", value, "posMechanismId");
            return (Criteria) this;
        }

        public Criteria andPosMechanismIdNotEqualTo(String value) {
            addCriterion("POS_MECHANISM_ID <>", value, "posMechanismId");
            return (Criteria) this;
        }

        public Criteria andPosMechanismIdGreaterThan(String value) {
            addCriterion("POS_MECHANISM_ID >", value, "posMechanismId");
            return (Criteria) this;
        }

        public Criteria andPosMechanismIdGreaterThanOrEqualTo(String value) {
            addCriterion("POS_MECHANISM_ID >=", value, "posMechanismId");
            return (Criteria) this;
        }

        public Criteria andPosMechanismIdLessThan(String value) {
            addCriterion("POS_MECHANISM_ID <", value, "posMechanismId");
            return (Criteria) this;
        }

        public Criteria andPosMechanismIdLessThanOrEqualTo(String value) {
            addCriterion("POS_MECHANISM_ID <=", value, "posMechanismId");
            return (Criteria) this;
        }

        public Criteria andPosMechanismIdLike(String value) {
            addCriterion("POS_MECHANISM_ID like", value, "posMechanismId");
            return (Criteria) this;
        }

        public Criteria andPosMechanismIdNotLike(String value) {
            addCriterion("POS_MECHANISM_ID not like", value, "posMechanismId");
            return (Criteria) this;
        }

        public Criteria andPosMechanismIdIn(List<String> values) {
            addCriterion("POS_MECHANISM_ID in", values, "posMechanismId");
            return (Criteria) this;
        }

        public Criteria andPosMechanismIdNotIn(List<String> values) {
            addCriterion("POS_MECHANISM_ID not in", values, "posMechanismId");
            return (Criteria) this;
        }

        public Criteria andPosMechanismIdBetween(String value1, String value2) {
            addCriterion("POS_MECHANISM_ID between", value1, value2, "posMechanismId");
            return (Criteria) this;
        }

        public Criteria andPosMechanismIdNotBetween(String value1, String value2) {
            addCriterion("POS_MECHANISM_ID not between", value1, value2, "posMechanismId");
            return (Criteria) this;
        }

        public Criteria andPosCompareCountIsNull() {
            addCriterion("POS_COMPARE_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andPosCompareCountIsNotNull() {
            addCriterion("POS_COMPARE_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andPosCompareCountEqualTo(String value) {
            addCriterion("POS_COMPARE_COUNT =", value, "posCompareCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareCountNotEqualTo(String value) {
            addCriterion("POS_COMPARE_COUNT <>", value, "posCompareCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareCountGreaterThan(String value) {
            addCriterion("POS_COMPARE_COUNT >", value, "posCompareCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareCountGreaterThanOrEqualTo(String value) {
            addCriterion("POS_COMPARE_COUNT >=", value, "posCompareCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareCountLessThan(String value) {
            addCriterion("POS_COMPARE_COUNT <", value, "posCompareCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareCountLessThanOrEqualTo(String value) {
            addCriterion("POS_COMPARE_COUNT <=", value, "posCompareCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareCountLike(String value) {
            addCriterion("POS_COMPARE_COUNT like", value, "posCompareCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareCountNotLike(String value) {
            addCriterion("POS_COMPARE_COUNT not like", value, "posCompareCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareCountIn(List<String> values) {
            addCriterion("POS_COMPARE_COUNT in", values, "posCompareCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareCountNotIn(List<String> values) {
            addCriterion("POS_COMPARE_COUNT not in", values, "posCompareCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareCountBetween(String value1, String value2) {
            addCriterion("POS_COMPARE_COUNT between", value1, value2, "posCompareCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareCountNotBetween(String value1, String value2) {
            addCriterion("POS_COMPARE_COUNT not between", value1, value2, "posCompareCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentCountIsNull() {
            addCriterion("POS_CURRENT_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andPosCurrentCountIsNotNull() {
            addCriterion("POS_CURRENT_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andPosCurrentCountEqualTo(String value) {
            addCriterion("POS_CURRENT_COUNT =", value, "posCurrentCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentCountNotEqualTo(String value) {
            addCriterion("POS_CURRENT_COUNT <>", value, "posCurrentCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentCountGreaterThan(String value) {
            addCriterion("POS_CURRENT_COUNT >", value, "posCurrentCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentCountGreaterThanOrEqualTo(String value) {
            addCriterion("POS_CURRENT_COUNT >=", value, "posCurrentCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentCountLessThan(String value) {
            addCriterion("POS_CURRENT_COUNT <", value, "posCurrentCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentCountLessThanOrEqualTo(String value) {
            addCriterion("POS_CURRENT_COUNT <=", value, "posCurrentCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentCountLike(String value) {
            addCriterion("POS_CURRENT_COUNT like", value, "posCurrentCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentCountNotLike(String value) {
            addCriterion("POS_CURRENT_COUNT not like", value, "posCurrentCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentCountIn(List<String> values) {
            addCriterion("POS_CURRENT_COUNT in", values, "posCurrentCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentCountNotIn(List<String> values) {
            addCriterion("POS_CURRENT_COUNT not in", values, "posCurrentCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentCountBetween(String value1, String value2) {
            addCriterion("POS_CURRENT_COUNT between", value1, value2, "posCurrentCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentCountNotBetween(String value1, String value2) {
            addCriterion("POS_CURRENT_COUNT not between", value1, value2, "posCurrentCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareLoanCountIsNull() {
            addCriterion("POS_COMPARE_LOAN_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andPosCompareLoanCountIsNotNull() {
            addCriterion("POS_COMPARE_LOAN_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andPosCompareLoanCountEqualTo(String value) {
            addCriterion("POS_COMPARE_LOAN_COUNT =", value, "posCompareLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareLoanCountNotEqualTo(String value) {
            addCriterion("POS_COMPARE_LOAN_COUNT <>", value, "posCompareLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareLoanCountGreaterThan(String value) {
            addCriterion("POS_COMPARE_LOAN_COUNT >", value, "posCompareLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareLoanCountGreaterThanOrEqualTo(String value) {
            addCriterion("POS_COMPARE_LOAN_COUNT >=", value, "posCompareLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareLoanCountLessThan(String value) {
            addCriterion("POS_COMPARE_LOAN_COUNT <", value, "posCompareLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareLoanCountLessThanOrEqualTo(String value) {
            addCriterion("POS_COMPARE_LOAN_COUNT <=", value, "posCompareLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareLoanCountLike(String value) {
            addCriterion("POS_COMPARE_LOAN_COUNT like", value, "posCompareLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareLoanCountNotLike(String value) {
            addCriterion("POS_COMPARE_LOAN_COUNT not like", value, "posCompareLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareLoanCountIn(List<String> values) {
            addCriterion("POS_COMPARE_LOAN_COUNT in", values, "posCompareLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareLoanCountNotIn(List<String> values) {
            addCriterion("POS_COMPARE_LOAN_COUNT not in", values, "posCompareLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareLoanCountBetween(String value1, String value2) {
            addCriterion("POS_COMPARE_LOAN_COUNT between", value1, value2, "posCompareLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCompareLoanCountNotBetween(String value1, String value2) {
            addCriterion("POS_COMPARE_LOAN_COUNT not between", value1, value2, "posCompareLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentLoanCountIsNull() {
            addCriterion("POS_CURRENT_LOAN_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andPosCurrentLoanCountIsNotNull() {
            addCriterion("POS_CURRENT_LOAN_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andPosCurrentLoanCountEqualTo(String value) {
            addCriterion("POS_CURRENT_LOAN_COUNT =", value, "posCurrentLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentLoanCountNotEqualTo(String value) {
            addCriterion("POS_CURRENT_LOAN_COUNT <>", value, "posCurrentLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentLoanCountGreaterThan(String value) {
            addCriterion("POS_CURRENT_LOAN_COUNT >", value, "posCurrentLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentLoanCountGreaterThanOrEqualTo(String value) {
            addCriterion("POS_CURRENT_LOAN_COUNT >=", value, "posCurrentLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentLoanCountLessThan(String value) {
            addCriterion("POS_CURRENT_LOAN_COUNT <", value, "posCurrentLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentLoanCountLessThanOrEqualTo(String value) {
            addCriterion("POS_CURRENT_LOAN_COUNT <=", value, "posCurrentLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentLoanCountLike(String value) {
            addCriterion("POS_CURRENT_LOAN_COUNT like", value, "posCurrentLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentLoanCountNotLike(String value) {
            addCriterion("POS_CURRENT_LOAN_COUNT not like", value, "posCurrentLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentLoanCountIn(List<String> values) {
            addCriterion("POS_CURRENT_LOAN_COUNT in", values, "posCurrentLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentLoanCountNotIn(List<String> values) {
            addCriterion("POS_CURRENT_LOAN_COUNT not in", values, "posCurrentLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentLoanCountBetween(String value1, String value2) {
            addCriterion("POS_CURRENT_LOAN_COUNT between", value1, value2, "posCurrentLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosCurrentLoanCountNotBetween(String value1, String value2) {
            addCriterion("POS_CURRENT_LOAN_COUNT not between", value1, value2, "posCurrentLoanCount");
            return (Criteria) this;
        }

        public Criteria andPosAmtIsNull() {
            addCriterion("POS_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPosAmtIsNotNull() {
            addCriterion("POS_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPosAmtEqualTo(String value) {
            addCriterion("POS_AMT =", value, "posAmt");
            return (Criteria) this;
        }

        public Criteria andPosAmtNotEqualTo(String value) {
            addCriterion("POS_AMT <>", value, "posAmt");
            return (Criteria) this;
        }

        public Criteria andPosAmtGreaterThan(String value) {
            addCriterion("POS_AMT >", value, "posAmt");
            return (Criteria) this;
        }

        public Criteria andPosAmtGreaterThanOrEqualTo(String value) {
            addCriterion("POS_AMT >=", value, "posAmt");
            return (Criteria) this;
        }

        public Criteria andPosAmtLessThan(String value) {
            addCriterion("POS_AMT <", value, "posAmt");
            return (Criteria) this;
        }

        public Criteria andPosAmtLessThanOrEqualTo(String value) {
            addCriterion("POS_AMT <=", value, "posAmt");
            return (Criteria) this;
        }

        public Criteria andPosAmtLike(String value) {
            addCriterion("POS_AMT like", value, "posAmt");
            return (Criteria) this;
        }

        public Criteria andPosAmtNotLike(String value) {
            addCriterion("POS_AMT not like", value, "posAmt");
            return (Criteria) this;
        }

        public Criteria andPosAmtIn(List<String> values) {
            addCriterion("POS_AMT in", values, "posAmt");
            return (Criteria) this;
        }

        public Criteria andPosAmtNotIn(List<String> values) {
            addCriterion("POS_AMT not in", values, "posAmt");
            return (Criteria) this;
        }

        public Criteria andPosAmtBetween(String value1, String value2) {
            addCriterion("POS_AMT between", value1, value2, "posAmt");
            return (Criteria) this;
        }

        public Criteria andPosAmtNotBetween(String value1, String value2) {
            addCriterion("POS_AMT not between", value1, value2, "posAmt");
            return (Criteria) this;
        }

        public Criteria andPosStandardIsNull() {
            addCriterion("POS_STANDARD is null");
            return (Criteria) this;
        }

        public Criteria andPosStandardIsNotNull() {
            addCriterion("POS_STANDARD is not null");
            return (Criteria) this;
        }

        public Criteria andPosStandardEqualTo(String value) {
            addCriterion("POS_STANDARD =", value, "posStandard");
            return (Criteria) this;
        }

        public Criteria andPosStandardNotEqualTo(String value) {
            addCriterion("POS_STANDARD <>", value, "posStandard");
            return (Criteria) this;
        }

        public Criteria andPosStandardGreaterThan(String value) {
            addCriterion("POS_STANDARD >", value, "posStandard");
            return (Criteria) this;
        }

        public Criteria andPosStandardGreaterThanOrEqualTo(String value) {
            addCriterion("POS_STANDARD >=", value, "posStandard");
            return (Criteria) this;
        }

        public Criteria andPosStandardLessThan(String value) {
            addCriterion("POS_STANDARD <", value, "posStandard");
            return (Criteria) this;
        }

        public Criteria andPosStandardLessThanOrEqualTo(String value) {
            addCriterion("POS_STANDARD <=", value, "posStandard");
            return (Criteria) this;
        }

        public Criteria andPosStandardLike(String value) {
            addCriterion("POS_STANDARD like", value, "posStandard");
            return (Criteria) this;
        }

        public Criteria andPosStandardNotLike(String value) {
            addCriterion("POS_STANDARD not like", value, "posStandard");
            return (Criteria) this;
        }

        public Criteria andPosStandardIn(List<String> values) {
            addCriterion("POS_STANDARD in", values, "posStandard");
            return (Criteria) this;
        }

        public Criteria andPosStandardNotIn(List<String> values) {
            addCriterion("POS_STANDARD not in", values, "posStandard");
            return (Criteria) this;
        }

        public Criteria andPosStandardBetween(String value1, String value2) {
            addCriterion("POS_STANDARD between", value1, value2, "posStandard");
            return (Criteria) this;
        }

        public Criteria andPosStandardNotBetween(String value1, String value2) {
            addCriterion("POS_STANDARD not between", value1, value2, "posStandard");
            return (Criteria) this;
        }

        public Criteria andPosOwnRewardIsNull() {
            addCriterion("POS_OWN_REWARD is null");
            return (Criteria) this;
        }

        public Criteria andPosOwnRewardIsNotNull() {
            addCriterion("POS_OWN_REWARD is not null");
            return (Criteria) this;
        }

        public Criteria andPosOwnRewardEqualTo(String value) {
            addCriterion("POS_OWN_REWARD =", value, "posOwnReward");
            return (Criteria) this;
        }

        public Criteria andPosOwnRewardNotEqualTo(String value) {
            addCriterion("POS_OWN_REWARD <>", value, "posOwnReward");
            return (Criteria) this;
        }

        public Criteria andPosOwnRewardGreaterThan(String value) {
            addCriterion("POS_OWN_REWARD >", value, "posOwnReward");
            return (Criteria) this;
        }

        public Criteria andPosOwnRewardGreaterThanOrEqualTo(String value) {
            addCriterion("POS_OWN_REWARD >=", value, "posOwnReward");
            return (Criteria) this;
        }

        public Criteria andPosOwnRewardLessThan(String value) {
            addCriterion("POS_OWN_REWARD <", value, "posOwnReward");
            return (Criteria) this;
        }

        public Criteria andPosOwnRewardLessThanOrEqualTo(String value) {
            addCriterion("POS_OWN_REWARD <=", value, "posOwnReward");
            return (Criteria) this;
        }

        public Criteria andPosOwnRewardLike(String value) {
            addCriterion("POS_OWN_REWARD like", value, "posOwnReward");
            return (Criteria) this;
        }

        public Criteria andPosOwnRewardNotLike(String value) {
            addCriterion("POS_OWN_REWARD not like", value, "posOwnReward");
            return (Criteria) this;
        }

        public Criteria andPosOwnRewardIn(List<String> values) {
            addCriterion("POS_OWN_REWARD in", values, "posOwnReward");
            return (Criteria) this;
        }

        public Criteria andPosOwnRewardNotIn(List<String> values) {
            addCriterion("POS_OWN_REWARD not in", values, "posOwnReward");
            return (Criteria) this;
        }

        public Criteria andPosOwnRewardBetween(String value1, String value2) {
            addCriterion("POS_OWN_REWARD between", value1, value2, "posOwnReward");
            return (Criteria) this;
        }

        public Criteria andPosOwnRewardNotBetween(String value1, String value2) {
            addCriterion("POS_OWN_REWARD not between", value1, value2, "posOwnReward");
            return (Criteria) this;
        }

        public Criteria andPosDownRewardIsNull() {
            addCriterion("POS_DOWN_REWARD is null");
            return (Criteria) this;
        }

        public Criteria andPosDownRewardIsNotNull() {
            addCriterion("POS_DOWN_REWARD is not null");
            return (Criteria) this;
        }

        public Criteria andPosDownRewardEqualTo(String value) {
            addCriterion("POS_DOWN_REWARD =", value, "posDownReward");
            return (Criteria) this;
        }

        public Criteria andPosDownRewardNotEqualTo(String value) {
            addCriterion("POS_DOWN_REWARD <>", value, "posDownReward");
            return (Criteria) this;
        }

        public Criteria andPosDownRewardGreaterThan(String value) {
            addCriterion("POS_DOWN_REWARD >", value, "posDownReward");
            return (Criteria) this;
        }

        public Criteria andPosDownRewardGreaterThanOrEqualTo(String value) {
            addCriterion("POS_DOWN_REWARD >=", value, "posDownReward");
            return (Criteria) this;
        }

        public Criteria andPosDownRewardLessThan(String value) {
            addCriterion("POS_DOWN_REWARD <", value, "posDownReward");
            return (Criteria) this;
        }

        public Criteria andPosDownRewardLessThanOrEqualTo(String value) {
            addCriterion("POS_DOWN_REWARD <=", value, "posDownReward");
            return (Criteria) this;
        }

        public Criteria andPosDownRewardLike(String value) {
            addCriterion("POS_DOWN_REWARD like", value, "posDownReward");
            return (Criteria) this;
        }

        public Criteria andPosDownRewardNotLike(String value) {
            addCriterion("POS_DOWN_REWARD not like", value, "posDownReward");
            return (Criteria) this;
        }

        public Criteria andPosDownRewardIn(List<String> values) {
            addCriterion("POS_DOWN_REWARD in", values, "posDownReward");
            return (Criteria) this;
        }

        public Criteria andPosDownRewardNotIn(List<String> values) {
            addCriterion("POS_DOWN_REWARD not in", values, "posDownReward");
            return (Criteria) this;
        }

        public Criteria andPosDownRewardBetween(String value1, String value2) {
            addCriterion("POS_DOWN_REWARD between", value1, value2, "posDownReward");
            return (Criteria) this;
        }

        public Criteria andPosDownRewardNotBetween(String value1, String value2) {
            addCriterion("POS_DOWN_REWARD not between", value1, value2, "posDownReward");
            return (Criteria) this;
        }

        public Criteria andPosReawrdProfitIsNull() {
            addCriterion("POS_REAWRD_PROFIT is null");
            return (Criteria) this;
        }

        public Criteria andPosReawrdProfitIsNotNull() {
            addCriterion("POS_REAWRD_PROFIT is not null");
            return (Criteria) this;
        }

        public Criteria andPosReawrdProfitEqualTo(String value) {
            addCriterion("POS_REAWRD_PROFIT =", value, "posReawrdProfit");
            return (Criteria) this;
        }

        public Criteria andPosReawrdProfitNotEqualTo(String value) {
            addCriterion("POS_REAWRD_PROFIT <>", value, "posReawrdProfit");
            return (Criteria) this;
        }

        public Criteria andPosReawrdProfitGreaterThan(String value) {
            addCriterion("POS_REAWRD_PROFIT >", value, "posReawrdProfit");
            return (Criteria) this;
        }

        public Criteria andPosReawrdProfitGreaterThanOrEqualTo(String value) {
            addCriterion("POS_REAWRD_PROFIT >=", value, "posReawrdProfit");
            return (Criteria) this;
        }

        public Criteria andPosReawrdProfitLessThan(String value) {
            addCriterion("POS_REAWRD_PROFIT <", value, "posReawrdProfit");
            return (Criteria) this;
        }

        public Criteria andPosReawrdProfitLessThanOrEqualTo(String value) {
            addCriterion("POS_REAWRD_PROFIT <=", value, "posReawrdProfit");
            return (Criteria) this;
        }

        public Criteria andPosReawrdProfitLike(String value) {
            addCriterion("POS_REAWRD_PROFIT like", value, "posReawrdProfit");
            return (Criteria) this;
        }

        public Criteria andPosReawrdProfitNotLike(String value) {
            addCriterion("POS_REAWRD_PROFIT not like", value, "posReawrdProfit");
            return (Criteria) this;
        }

        public Criteria andPosReawrdProfitIn(List<String> values) {
            addCriterion("POS_REAWRD_PROFIT in", values, "posReawrdProfit");
            return (Criteria) this;
        }

        public Criteria andPosReawrdProfitNotIn(List<String> values) {
            addCriterion("POS_REAWRD_PROFIT not in", values, "posReawrdProfit");
            return (Criteria) this;
        }

        public Criteria andPosReawrdProfitBetween(String value1, String value2) {
            addCriterion("POS_REAWRD_PROFIT between", value1, value2, "posReawrdProfit");
            return (Criteria) this;
        }

        public Criteria andPosReawrdProfitNotBetween(String value1, String value2) {
            addCriterion("POS_REAWRD_PROFIT not between", value1, value2, "posReawrdProfit");
            return (Criteria) this;
        }

        public Criteria andPosRemarkIsNull() {
            addCriterion("POS_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andPosRemarkIsNotNull() {
            addCriterion("POS_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andPosRemarkEqualTo(String value) {
            addCriterion("POS_REMARK =", value, "posRemark");
            return (Criteria) this;
        }

        public Criteria andPosRemarkNotEqualTo(String value) {
            addCriterion("POS_REMARK <>", value, "posRemark");
            return (Criteria) this;
        }

        public Criteria andPosRemarkGreaterThan(String value) {
            addCriterion("POS_REMARK >", value, "posRemark");
            return (Criteria) this;
        }

        public Criteria andPosRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("POS_REMARK >=", value, "posRemark");
            return (Criteria) this;
        }

        public Criteria andPosRemarkLessThan(String value) {
            addCriterion("POS_REMARK <", value, "posRemark");
            return (Criteria) this;
        }

        public Criteria andPosRemarkLessThanOrEqualTo(String value) {
            addCriterion("POS_REMARK <=", value, "posRemark");
            return (Criteria) this;
        }

        public Criteria andPosRemarkLike(String value) {
            addCriterion("POS_REMARK like", value, "posRemark");
            return (Criteria) this;
        }

        public Criteria andPosRemarkNotLike(String value) {
            addCriterion("POS_REMARK not like", value, "posRemark");
            return (Criteria) this;
        }

        public Criteria andPosRemarkIn(List<String> values) {
            addCriterion("POS_REMARK in", values, "posRemark");
            return (Criteria) this;
        }

        public Criteria andPosRemarkNotIn(List<String> values) {
            addCriterion("POS_REMARK not in", values, "posRemark");
            return (Criteria) this;
        }

        public Criteria andPosRemarkBetween(String value1, String value2) {
            addCriterion("POS_REMARK between", value1, value2, "posRemark");
            return (Criteria) this;
        }

        public Criteria andPosRemarkNotBetween(String value1, String value2) {
            addCriterion("POS_REMARK not between", value1, value2, "posRemark");
            return (Criteria) this;
        }

        public Criteria andPosCheckDeductAmtIsNull() {
            addCriterion("POS_CHECK_DEDUCT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPosCheckDeductAmtIsNotNull() {
            addCriterion("POS_CHECK_DEDUCT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPosCheckDeductAmtEqualTo(String value) {
            addCriterion("POS_CHECK_DEDUCT_AMT =", value, "posCheckDeductAmt");
            return (Criteria) this;
        }

        public Criteria andPosCheckDeductAmtNotEqualTo(String value) {
            addCriterion("POS_CHECK_DEDUCT_AMT <>", value, "posCheckDeductAmt");
            return (Criteria) this;
        }

        public Criteria andPosCheckDeductAmtGreaterThan(String value) {
            addCriterion("POS_CHECK_DEDUCT_AMT >", value, "posCheckDeductAmt");
            return (Criteria) this;
        }

        public Criteria andPosCheckDeductAmtGreaterThanOrEqualTo(String value) {
            addCriterion("POS_CHECK_DEDUCT_AMT >=", value, "posCheckDeductAmt");
            return (Criteria) this;
        }

        public Criteria andPosCheckDeductAmtLessThan(String value) {
            addCriterion("POS_CHECK_DEDUCT_AMT <", value, "posCheckDeductAmt");
            return (Criteria) this;
        }

        public Criteria andPosCheckDeductAmtLessThanOrEqualTo(String value) {
            addCriterion("POS_CHECK_DEDUCT_AMT <=", value, "posCheckDeductAmt");
            return (Criteria) this;
        }

        public Criteria andPosCheckDeductAmtLike(String value) {
            addCriterion("POS_CHECK_DEDUCT_AMT like", value, "posCheckDeductAmt");
            return (Criteria) this;
        }

        public Criteria andPosCheckDeductAmtNotLike(String value) {
            addCriterion("POS_CHECK_DEDUCT_AMT not like", value, "posCheckDeductAmt");
            return (Criteria) this;
        }

        public Criteria andPosCheckDeductAmtIn(List<String> values) {
            addCriterion("POS_CHECK_DEDUCT_AMT in", values, "posCheckDeductAmt");
            return (Criteria) this;
        }

        public Criteria andPosCheckDeductAmtNotIn(List<String> values) {
            addCriterion("POS_CHECK_DEDUCT_AMT not in", values, "posCheckDeductAmt");
            return (Criteria) this;
        }

        public Criteria andPosCheckDeductAmtBetween(String value1, String value2) {
            addCriterion("POS_CHECK_DEDUCT_AMT between", value1, value2, "posCheckDeductAmt");
            return (Criteria) this;
        }

        public Criteria andPosCheckDeductAmtNotBetween(String value1, String value2) {
            addCriterion("POS_CHECK_DEDUCT_AMT not between", value1, value2, "posCheckDeductAmt");
            return (Criteria) this;
        }

        public Criteria andOrgidIsNull() {
            addCriterion("ORGID is null");
            return (Criteria) this;
        }

        public Criteria andOrgidIsNotNull() {
            addCriterion("ORGID is not null");
            return (Criteria) this;
        }

        public Criteria andOrgidEqualTo(String value) {
            addCriterion("ORGID =", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotEqualTo(String value) {
            addCriterion("ORGID <>", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidGreaterThan(String value) {
            addCriterion("ORGID >", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidGreaterThanOrEqualTo(String value) {
            addCriterion("ORGID >=", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidLessThan(String value) {
            addCriterion("ORGID <", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidLessThanOrEqualTo(String value) {
            addCriterion("ORGID <=", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidLike(String value) {
            addCriterion("ORGID like", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotLike(String value) {
            addCriterion("ORGID not like", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidIn(List<String> values) {
            addCriterion("ORGID in", values, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotIn(List<String> values) {
            addCriterion("ORGID not in", values, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidBetween(String value1, String value2) {
            addCriterion("ORGID between", value1, value2, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotBetween(String value1, String value2) {
            addCriterion("ORGID not between", value1, value2, "orgid");
            return (Criteria) this;
        }

        public Criteria andSettlemonth1812totaltransIsNull() {
            addCriterion("SETTLEMONTH1812TOTALTRANS is null");
            return (Criteria) this;
        }

        public Criteria andSettlemonth1812totaltransIsNotNull() {
            addCriterion("SETTLEMONTH1812TOTALTRANS is not null");
            return (Criteria) this;
        }

        public Criteria andSettlemonth1812totaltransEqualTo(BigDecimal value) {
            addCriterion("SETTLEMONTH1812TOTALTRANS =", value, "settlemonth1812totaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonth1812totaltransNotEqualTo(BigDecimal value) {
            addCriterion("SETTLEMONTH1812TOTALTRANS <>", value, "settlemonth1812totaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonth1812totaltransGreaterThan(BigDecimal value) {
            addCriterion("SETTLEMONTH1812TOTALTRANS >", value, "settlemonth1812totaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonth1812totaltransGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SETTLEMONTH1812TOTALTRANS >=", value, "settlemonth1812totaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonth1812totaltransLessThan(BigDecimal value) {
            addCriterion("SETTLEMONTH1812TOTALTRANS <", value, "settlemonth1812totaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonth1812totaltransLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SETTLEMONTH1812TOTALTRANS <=", value, "settlemonth1812totaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonth1812totaltransIn(List<BigDecimal> values) {
            addCriterion("SETTLEMONTH1812TOTALTRANS in", values, "settlemonth1812totaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonth1812totaltransNotIn(List<BigDecimal> values) {
            addCriterion("SETTLEMONTH1812TOTALTRANS not in", values, "settlemonth1812totaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonth1812totaltransBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SETTLEMONTH1812TOTALTRANS between", value1, value2, "settlemonth1812totaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonth1812totaltransNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SETTLEMONTH1812TOTALTRANS not between", value1, value2, "settlemonth1812totaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonthtotaltransIsNull() {
            addCriterion("SETTLEMONTHTOTALTRANS is null");
            return (Criteria) this;
        }

        public Criteria andSettlemonthtotaltransIsNotNull() {
            addCriterion("SETTLEMONTHTOTALTRANS is not null");
            return (Criteria) this;
        }

        public Criteria andSettlemonthtotaltransEqualTo(BigDecimal value) {
            addCriterion("SETTLEMONTHTOTALTRANS =", value, "settlemonthtotaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonthtotaltransNotEqualTo(BigDecimal value) {
            addCriterion("SETTLEMONTHTOTALTRANS <>", value, "settlemonthtotaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonthtotaltransGreaterThan(BigDecimal value) {
            addCriterion("SETTLEMONTHTOTALTRANS >", value, "settlemonthtotaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonthtotaltransGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SETTLEMONTHTOTALTRANS >=", value, "settlemonthtotaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonthtotaltransLessThan(BigDecimal value) {
            addCriterion("SETTLEMONTHTOTALTRANS <", value, "settlemonthtotaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonthtotaltransLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SETTLEMONTHTOTALTRANS <=", value, "settlemonthtotaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonthtotaltransIn(List<BigDecimal> values) {
            addCriterion("SETTLEMONTHTOTALTRANS in", values, "settlemonthtotaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonthtotaltransNotIn(List<BigDecimal> values) {
            addCriterion("SETTLEMONTHTOTALTRANS not in", values, "settlemonthtotaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonthtotaltransBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SETTLEMONTHTOTALTRANS between", value1, value2, "settlemonthtotaltrans");
            return (Criteria) this;
        }

        public Criteria andSettlemonthtotaltransNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SETTLEMONTHTOTALTRANS not between", value1, value2, "settlemonthtotaltrans");
            return (Criteria) this;
        }

        public Criteria andIsstandardIsNull() {
            addCriterion("ISSTANDARD is null");
            return (Criteria) this;
        }

        public Criteria andIsstandardIsNotNull() {
            addCriterion("ISSTANDARD is not null");
            return (Criteria) this;
        }

        public Criteria andIsstandardEqualTo(String value) {
            addCriterion("ISSTANDARD =", value, "isstandard");
            return (Criteria) this;
        }

        public Criteria andIsstandardNotEqualTo(String value) {
            addCriterion("ISSTANDARD <>", value, "isstandard");
            return (Criteria) this;
        }

        public Criteria andIsstandardGreaterThan(String value) {
            addCriterion("ISSTANDARD >", value, "isstandard");
            return (Criteria) this;
        }

        public Criteria andIsstandardGreaterThanOrEqualTo(String value) {
            addCriterion("ISSTANDARD >=", value, "isstandard");
            return (Criteria) this;
        }

        public Criteria andIsstandardLessThan(String value) {
            addCriterion("ISSTANDARD <", value, "isstandard");
            return (Criteria) this;
        }

        public Criteria andIsstandardLessThanOrEqualTo(String value) {
            addCriterion("ISSTANDARD <=", value, "isstandard");
            return (Criteria) this;
        }

        public Criteria andIsstandardLike(String value) {
            addCriterion("ISSTANDARD like", value, "isstandard");
            return (Criteria) this;
        }

        public Criteria andIsstandardNotLike(String value) {
            addCriterion("ISSTANDARD not like", value, "isstandard");
            return (Criteria) this;
        }

        public Criteria andIsstandardIn(List<String> values) {
            addCriterion("ISSTANDARD in", values, "isstandard");
            return (Criteria) this;
        }

        public Criteria andIsstandardNotIn(List<String> values) {
            addCriterion("ISSTANDARD not in", values, "isstandard");
            return (Criteria) this;
        }

        public Criteria andIsstandardBetween(String value1, String value2) {
            addCriterion("ISSTANDARD between", value1, value2, "isstandard");
            return (Criteria) this;
        }

        public Criteria andIsstandardNotBetween(String value1, String value2) {
            addCriterion("ISSTANDARD not between", value1, value2, "isstandard");
            return (Criteria) this;
        }

        public Criteria andTotalorderIsNull() {
            addCriterion("TOTALORDER is null");
            return (Criteria) this;
        }

        public Criteria andTotalorderIsNotNull() {
            addCriterion("TOTALORDER is not null");
            return (Criteria) this;
        }

        public Criteria andTotalorderEqualTo(BigDecimal value) {
            addCriterion("TOTALORDER =", value, "totalorder");
            return (Criteria) this;
        }

        public Criteria andTotalorderNotEqualTo(BigDecimal value) {
            addCriterion("TOTALORDER <>", value, "totalorder");
            return (Criteria) this;
        }

        public Criteria andTotalorderGreaterThan(BigDecimal value) {
            addCriterion("TOTALORDER >", value, "totalorder");
            return (Criteria) this;
        }

        public Criteria andTotalorderGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TOTALORDER >=", value, "totalorder");
            return (Criteria) this;
        }

        public Criteria andTotalorderLessThan(BigDecimal value) {
            addCriterion("TOTALORDER <", value, "totalorder");
            return (Criteria) this;
        }

        public Criteria andTotalorderLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TOTALORDER <=", value, "totalorder");
            return (Criteria) this;
        }

        public Criteria andTotalorderIn(List<BigDecimal> values) {
            addCriterion("TOTALORDER in", values, "totalorder");
            return (Criteria) this;
        }

        public Criteria andTotalorderNotIn(List<BigDecimal> values) {
            addCriterion("TOTALORDER not in", values, "totalorder");
            return (Criteria) this;
        }

        public Criteria andTotalorderBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TOTALORDER between", value1, value2, "totalorder");
            return (Criteria) this;
        }

        public Criteria andTotalorderNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TOTALORDER not between", value1, value2, "totalorder");
            return (Criteria) this;
        }

        public Criteria andYearafter19totaltransIsNull() {
            addCriterion("YEARAFTER19TOTALTRANS is null");
            return (Criteria) this;
        }

        public Criteria andYearafter19totaltransIsNotNull() {
            addCriterion("YEARAFTER19TOTALTRANS is not null");
            return (Criteria) this;
        }

        public Criteria andYearafter19totaltransEqualTo(BigDecimal value) {
            addCriterion("YEARAFTER19TOTALTRANS =", value, "yearafter19totaltrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19totaltransNotEqualTo(BigDecimal value) {
            addCriterion("YEARAFTER19TOTALTRANS <>", value, "yearafter19totaltrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19totaltransGreaterThan(BigDecimal value) {
            addCriterion("YEARAFTER19TOTALTRANS >", value, "yearafter19totaltrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19totaltransGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("YEARAFTER19TOTALTRANS >=", value, "yearafter19totaltrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19totaltransLessThan(BigDecimal value) {
            addCriterion("YEARAFTER19TOTALTRANS <", value, "yearafter19totaltrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19totaltransLessThanOrEqualTo(BigDecimal value) {
            addCriterion("YEARAFTER19TOTALTRANS <=", value, "yearafter19totaltrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19totaltransIn(List<BigDecimal> values) {
            addCriterion("YEARAFTER19TOTALTRANS in", values, "yearafter19totaltrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19totaltransNotIn(List<BigDecimal> values) {
            addCriterion("YEARAFTER19TOTALTRANS not in", values, "yearafter19totaltrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19totaltransBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("YEARAFTER19TOTALTRANS between", value1, value2, "yearafter19totaltrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19totaltransNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("YEARAFTER19TOTALTRANS not between", value1, value2, "yearafter19totaltrans");
            return (Criteria) this;
        }

        public Criteria andContrastmonthIsNull() {
            addCriterion("CONTRASTMONTH is null");
            return (Criteria) this;
        }

        public Criteria andContrastmonthIsNotNull() {
            addCriterion("CONTRASTMONTH is not null");
            return (Criteria) this;
        }

        public Criteria andContrastmonthEqualTo(String value) {
            addCriterion("CONTRASTMONTH =", value, "contrastmonth");
            return (Criteria) this;
        }

        public Criteria andContrastmonthNotEqualTo(String value) {
            addCriterion("CONTRASTMONTH <>", value, "contrastmonth");
            return (Criteria) this;
        }

        public Criteria andContrastmonthGreaterThan(String value) {
            addCriterion("CONTRASTMONTH >", value, "contrastmonth");
            return (Criteria) this;
        }

        public Criteria andContrastmonthGreaterThanOrEqualTo(String value) {
            addCriterion("CONTRASTMONTH >=", value, "contrastmonth");
            return (Criteria) this;
        }

        public Criteria andContrastmonthLessThan(String value) {
            addCriterion("CONTRASTMONTH <", value, "contrastmonth");
            return (Criteria) this;
        }

        public Criteria andContrastmonthLessThanOrEqualTo(String value) {
            addCriterion("CONTRASTMONTH <=", value, "contrastmonth");
            return (Criteria) this;
        }

        public Criteria andContrastmonthLike(String value) {
            addCriterion("CONTRASTMONTH like", value, "contrastmonth");
            return (Criteria) this;
        }

        public Criteria andContrastmonthNotLike(String value) {
            addCriterion("CONTRASTMONTH not like", value, "contrastmonth");
            return (Criteria) this;
        }

        public Criteria andContrastmonthIn(List<String> values) {
            addCriterion("CONTRASTMONTH in", values, "contrastmonth");
            return (Criteria) this;
        }

        public Criteria andContrastmonthNotIn(List<String> values) {
            addCriterion("CONTRASTMONTH not in", values, "contrastmonth");
            return (Criteria) this;
        }

        public Criteria andContrastmonthBetween(String value1, String value2) {
            addCriterion("CONTRASTMONTH between", value1, value2, "contrastmonth");
            return (Criteria) this;
        }

        public Criteria andContrastmonthNotBetween(String value1, String value2) {
            addCriterion("CONTRASTMONTH not between", value1, value2, "contrastmonth");
            return (Criteria) this;
        }

        public Criteria andNewtransamountIsNull() {
            addCriterion("NEWTRANSAMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andNewtransamountIsNotNull() {
            addCriterion("NEWTRANSAMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andNewtransamountEqualTo(BigDecimal value) {
            addCriterion("NEWTRANSAMOUNT =", value, "newtransamount");
            return (Criteria) this;
        }

        public Criteria andNewtransamountNotEqualTo(BigDecimal value) {
            addCriterion("NEWTRANSAMOUNT <>", value, "newtransamount");
            return (Criteria) this;
        }

        public Criteria andNewtransamountGreaterThan(BigDecimal value) {
            addCriterion("NEWTRANSAMOUNT >", value, "newtransamount");
            return (Criteria) this;
        }

        public Criteria andNewtransamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NEWTRANSAMOUNT >=", value, "newtransamount");
            return (Criteria) this;
        }

        public Criteria andNewtransamountLessThan(BigDecimal value) {
            addCriterion("NEWTRANSAMOUNT <", value, "newtransamount");
            return (Criteria) this;
        }

        public Criteria andNewtransamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NEWTRANSAMOUNT <=", value, "newtransamount");
            return (Criteria) this;
        }

        public Criteria andNewtransamountIn(List<BigDecimal> values) {
            addCriterion("NEWTRANSAMOUNT in", values, "newtransamount");
            return (Criteria) this;
        }

        public Criteria andNewtransamountNotIn(List<BigDecimal> values) {
            addCriterion("NEWTRANSAMOUNT not in", values, "newtransamount");
            return (Criteria) this;
        }

        public Criteria andNewtransamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NEWTRANSAMOUNT between", value1, value2, "newtransamount");
            return (Criteria) this;
        }

        public Criteria andNewtransamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NEWTRANSAMOUNT not between", value1, value2, "newtransamount");
            return (Criteria) this;
        }

        public Criteria andYearafter19credittransIsNull() {
            addCriterion("YEARAFTER19CREDITTRANS is null");
            return (Criteria) this;
        }

        public Criteria andYearafter19credittransIsNotNull() {
            addCriterion("YEARAFTER19CREDITTRANS is not null");
            return (Criteria) this;
        }

        public Criteria andYearafter19credittransEqualTo(BigDecimal value) {
            addCriterion("YEARAFTER19CREDITTRANS =", value, "yearafter19credittrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19credittransNotEqualTo(BigDecimal value) {
            addCriterion("YEARAFTER19CREDITTRANS <>", value, "yearafter19credittrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19credittransGreaterThan(BigDecimal value) {
            addCriterion("YEARAFTER19CREDITTRANS >", value, "yearafter19credittrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19credittransGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("YEARAFTER19CREDITTRANS >=", value, "yearafter19credittrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19credittransLessThan(BigDecimal value) {
            addCriterion("YEARAFTER19CREDITTRANS <", value, "yearafter19credittrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19credittransLessThanOrEqualTo(BigDecimal value) {
            addCriterion("YEARAFTER19CREDITTRANS <=", value, "yearafter19credittrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19credittransIn(List<BigDecimal> values) {
            addCriterion("YEARAFTER19CREDITTRANS in", values, "yearafter19credittrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19credittransNotIn(List<BigDecimal> values) {
            addCriterion("YEARAFTER19CREDITTRANS not in", values, "yearafter19credittrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19credittransBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("YEARAFTER19CREDITTRANS between", value1, value2, "yearafter19credittrans");
            return (Criteria) this;
        }

        public Criteria andYearafter19credittransNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("YEARAFTER19CREDITTRANS not between", value1, value2, "yearafter19credittrans");
            return (Criteria) this;
        }

        public Criteria andSettleMonthCreditTransIsNull() {
            addCriterion("SETTLE_MONTH_CREDIT_TRANS is null");
            return (Criteria) this;
        }

        public Criteria andSettleMonthCreditTransIsNotNull() {
            addCriterion("SETTLE_MONTH_CREDIT_TRANS is not null");
            return (Criteria) this;
        }

        public Criteria andSettleMonthCreditTransEqualTo(BigDecimal value) {
            addCriterion("SETTLE_MONTH_CREDIT_TRANS =", value, "settleMonthCreditTrans");
            return (Criteria) this;
        }

        public Criteria andSettleMonthCreditTransNotEqualTo(BigDecimal value) {
            addCriterion("SETTLE_MONTH_CREDIT_TRANS <>", value, "settleMonthCreditTrans");
            return (Criteria) this;
        }

        public Criteria andSettleMonthCreditTransGreaterThan(BigDecimal value) {
            addCriterion("SETTLE_MONTH_CREDIT_TRANS >", value, "settleMonthCreditTrans");
            return (Criteria) this;
        }

        public Criteria andSettleMonthCreditTransGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SETTLE_MONTH_CREDIT_TRANS >=", value, "settleMonthCreditTrans");
            return (Criteria) this;
        }

        public Criteria andSettleMonthCreditTransLessThan(BigDecimal value) {
            addCriterion("SETTLE_MONTH_CREDIT_TRANS <", value, "settleMonthCreditTrans");
            return (Criteria) this;
        }

        public Criteria andSettleMonthCreditTransLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SETTLE_MONTH_CREDIT_TRANS <=", value, "settleMonthCreditTrans");
            return (Criteria) this;
        }

        public Criteria andSettleMonthCreditTransIn(List<BigDecimal> values) {
            addCriterion("SETTLE_MONTH_CREDIT_TRANS in", values, "settleMonthCreditTrans");
            return (Criteria) this;
        }

        public Criteria andSettleMonthCreditTransNotIn(List<BigDecimal> values) {
            addCriterion("SETTLE_MONTH_CREDIT_TRANS not in", values, "settleMonthCreditTrans");
            return (Criteria) this;
        }

        public Criteria andSettleMonthCreditTransBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SETTLE_MONTH_CREDIT_TRANS between", value1, value2, "settleMonthCreditTrans");
            return (Criteria) this;
        }

        public Criteria andSettleMonthCreditTransNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SETTLE_MONTH_CREDIT_TRANS not between", value1, value2, "settleMonthCreditTrans");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardStandardIsNull() {
            addCriterion("SPECIAL_REWARD_STANDARD is null");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardStandardIsNotNull() {
            addCriterion("SPECIAL_REWARD_STANDARD is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardStandardEqualTo(BigDecimal value) {
            addCriterion("SPECIAL_REWARD_STANDARD =", value, "specialRewardStandard");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardStandardNotEqualTo(BigDecimal value) {
            addCriterion("SPECIAL_REWARD_STANDARD <>", value, "specialRewardStandard");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardStandardGreaterThan(BigDecimal value) {
            addCriterion("SPECIAL_REWARD_STANDARD >", value, "specialRewardStandard");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardStandardGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SPECIAL_REWARD_STANDARD >=", value, "specialRewardStandard");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardStandardLessThan(BigDecimal value) {
            addCriterion("SPECIAL_REWARD_STANDARD <", value, "specialRewardStandard");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardStandardLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SPECIAL_REWARD_STANDARD <=", value, "specialRewardStandard");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardStandardIn(List<BigDecimal> values) {
            addCriterion("SPECIAL_REWARD_STANDARD in", values, "specialRewardStandard");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardStandardNotIn(List<BigDecimal> values) {
            addCriterion("SPECIAL_REWARD_STANDARD not in", values, "specialRewardStandard");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardStandardBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SPECIAL_REWARD_STANDARD between", value1, value2, "specialRewardStandard");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardStandardNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SPECIAL_REWARD_STANDARD not between", value1, value2, "specialRewardStandard");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardIsNull() {
            addCriterion("SPECIAL_REWARD is null");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardIsNotNull() {
            addCriterion("SPECIAL_REWARD is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardEqualTo(BigDecimal value) {
            addCriterion("SPECIAL_REWARD =", value, "specialReward");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardNotEqualTo(BigDecimal value) {
            addCriterion("SPECIAL_REWARD <>", value, "specialReward");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardGreaterThan(BigDecimal value) {
            addCriterion("SPECIAL_REWARD >", value, "specialReward");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SPECIAL_REWARD >=", value, "specialReward");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardLessThan(BigDecimal value) {
            addCriterion("SPECIAL_REWARD <", value, "specialReward");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SPECIAL_REWARD <=", value, "specialReward");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardIn(List<BigDecimal> values) {
            addCriterion("SPECIAL_REWARD in", values, "specialReward");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardNotIn(List<BigDecimal> values) {
            addCriterion("SPECIAL_REWARD not in", values, "specialReward");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SPECIAL_REWARD between", value1, value2, "specialReward");
            return (Criteria) this;
        }

        public Criteria andSpecialRewardNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SPECIAL_REWARD not between", value1, value2, "specialReward");
            return (Criteria) this;
        }

        public Criteria andAssessStatusIsNull() {
            addCriterion("ASSESS_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andAssessStatusIsNotNull() {
            addCriterion("ASSESS_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andAssessStatusEqualTo(String value) {
            addCriterion("ASSESS_STATUS =", value, "assessStatus");
            return (Criteria) this;
        }

        public Criteria andAssessStatusNotEqualTo(String value) {
            addCriterion("ASSESS_STATUS <>", value, "assessStatus");
            return (Criteria) this;
        }

        public Criteria andAssessStatusGreaterThan(String value) {
            addCriterion("ASSESS_STATUS >", value, "assessStatus");
            return (Criteria) this;
        }

        public Criteria andAssessStatusGreaterThanOrEqualTo(String value) {
            addCriterion("ASSESS_STATUS >=", value, "assessStatus");
            return (Criteria) this;
        }

        public Criteria andAssessStatusLessThan(String value) {
            addCriterion("ASSESS_STATUS <", value, "assessStatus");
            return (Criteria) this;
        }

        public Criteria andAssessStatusLessThanOrEqualTo(String value) {
            addCriterion("ASSESS_STATUS <=", value, "assessStatus");
            return (Criteria) this;
        }

        public Criteria andAssessStatusLike(String value) {
            addCriterion("ASSESS_STATUS like", value, "assessStatus");
            return (Criteria) this;
        }

        public Criteria andAssessStatusNotLike(String value) {
            addCriterion("ASSESS_STATUS not like", value, "assessStatus");
            return (Criteria) this;
        }

        public Criteria andAssessStatusIn(List<String> values) {
            addCriterion("ASSESS_STATUS in", values, "assessStatus");
            return (Criteria) this;
        }

        public Criteria andAssessStatusNotIn(List<String> values) {
            addCriterion("ASSESS_STATUS not in", values, "assessStatus");
            return (Criteria) this;
        }

        public Criteria andAssessStatusBetween(String value1, String value2) {
            addCriterion("ASSESS_STATUS between", value1, value2, "assessStatus");
            return (Criteria) this;
        }

        public Criteria andAssessStatusNotBetween(String value1, String value2) {
            addCriterion("ASSESS_STATUS not between", value1, value2, "assessStatus");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardIsNull() {
            addCriterion("EXECUTE_REWARD is null");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardIsNotNull() {
            addCriterion("EXECUTE_REWARD is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardEqualTo(BigDecimal value) {
            addCriterion("EXECUTE_REWARD =", value, "executeReward");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardNotEqualTo(BigDecimal value) {
            addCriterion("EXECUTE_REWARD <>", value, "executeReward");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardGreaterThan(BigDecimal value) {
            addCriterion("EXECUTE_REWARD >", value, "executeReward");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("EXECUTE_REWARD >=", value, "executeReward");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardLessThan(BigDecimal value) {
            addCriterion("EXECUTE_REWARD <", value, "executeReward");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardLessThanOrEqualTo(BigDecimal value) {
            addCriterion("EXECUTE_REWARD <=", value, "executeReward");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardIn(List<BigDecimal> values) {
            addCriterion("EXECUTE_REWARD in", values, "executeReward");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardNotIn(List<BigDecimal> values) {
            addCriterion("EXECUTE_REWARD not in", values, "executeReward");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EXECUTE_REWARD between", value1, value2, "executeReward");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EXECUTE_REWARD not between", value1, value2, "executeReward");
            return (Criteria) this;
        }

        public Criteria andDeductedRewardIsNull() {
            addCriterion("DEDUCTED_REWARD is null");
            return (Criteria) this;
        }

        public Criteria andDeductedRewardIsNotNull() {
            addCriterion("DEDUCTED_REWARD is not null");
            return (Criteria) this;
        }

        public Criteria andDeductedRewardEqualTo(BigDecimal value) {
            addCriterion("DEDUCTED_REWARD =", value, "deductedReward");
            return (Criteria) this;
        }

        public Criteria andDeductedRewardNotEqualTo(BigDecimal value) {
            addCriterion("DEDUCTED_REWARD <>", value, "deductedReward");
            return (Criteria) this;
        }

        public Criteria andDeductedRewardGreaterThan(BigDecimal value) {
            addCriterion("DEDUCTED_REWARD >", value, "deductedReward");
            return (Criteria) this;
        }

        public Criteria andDeductedRewardGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DEDUCTED_REWARD >=", value, "deductedReward");
            return (Criteria) this;
        }

        public Criteria andDeductedRewardLessThan(BigDecimal value) {
            addCriterion("DEDUCTED_REWARD <", value, "deductedReward");
            return (Criteria) this;
        }

        public Criteria andDeductedRewardLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DEDUCTED_REWARD <=", value, "deductedReward");
            return (Criteria) this;
        }

        public Criteria andDeductedRewardIn(List<BigDecimal> values) {
            addCriterion("DEDUCTED_REWARD in", values, "deductedReward");
            return (Criteria) this;
        }

        public Criteria andDeductedRewardNotIn(List<BigDecimal> values) {
            addCriterion("DEDUCTED_REWARD not in", values, "deductedReward");
            return (Criteria) this;
        }

        public Criteria andDeductedRewardBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEDUCTED_REWARD between", value1, value2, "deductedReward");
            return (Criteria) this;
        }

        public Criteria andDeductedRewardNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEDUCTED_REWARD not between", value1, value2, "deductedReward");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardStandardIsNull() {
            addCriterion("EXECUTE_REWARD_STANDARD is null");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardStandardIsNotNull() {
            addCriterion("EXECUTE_REWARD_STANDARD is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardStandardEqualTo(BigDecimal value) {
            addCriterion("EXECUTE_REWARD_STANDARD =", value, "executeRewardStandard");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardStandardNotEqualTo(BigDecimal value) {
            addCriterion("EXECUTE_REWARD_STANDARD <>", value, "executeRewardStandard");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardStandardGreaterThan(BigDecimal value) {
            addCriterion("EXECUTE_REWARD_STANDARD >", value, "executeRewardStandard");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardStandardGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("EXECUTE_REWARD_STANDARD >=", value, "executeRewardStandard");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardStandardLessThan(BigDecimal value) {
            addCriterion("EXECUTE_REWARD_STANDARD <", value, "executeRewardStandard");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardStandardLessThanOrEqualTo(BigDecimal value) {
            addCriterion("EXECUTE_REWARD_STANDARD <=", value, "executeRewardStandard");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardStandardIn(List<BigDecimal> values) {
            addCriterion("EXECUTE_REWARD_STANDARD in", values, "executeRewardStandard");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardStandardNotIn(List<BigDecimal> values) {
            addCriterion("EXECUTE_REWARD_STANDARD not in", values, "executeRewardStandard");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardStandardBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EXECUTE_REWARD_STANDARD between", value1, value2, "executeRewardStandard");
            return (Criteria) this;
        }

        public Criteria andExecuteRewardStandardNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EXECUTE_REWARD_STANDARD not between", value1, value2, "executeRewardStandard");
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