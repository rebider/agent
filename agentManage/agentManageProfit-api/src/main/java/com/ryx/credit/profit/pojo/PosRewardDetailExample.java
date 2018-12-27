package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;
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