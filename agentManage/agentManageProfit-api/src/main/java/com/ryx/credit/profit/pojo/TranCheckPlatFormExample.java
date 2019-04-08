package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TranCheckPlatFormExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public TranCheckPlatFormExample() {
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

        public Criteria andIdEqualTo(BigDecimal value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(BigDecimal value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(BigDecimal value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(BigDecimal value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<BigDecimal> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<BigDecimal> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeIsNull() {
            addCriterion("PLATFORM_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeIsNotNull() {
            addCriterion("PLATFORM_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeEqualTo(String value) {
            addCriterion("PLATFORM_TYPE =", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotEqualTo(String value) {
            addCriterion("PLATFORM_TYPE <>", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeGreaterThan(String value) {
            addCriterion("PLATFORM_TYPE >", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PLATFORM_TYPE >=", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLessThan(String value) {
            addCriterion("PLATFORM_TYPE <", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLessThanOrEqualTo(String value) {
            addCriterion("PLATFORM_TYPE <=", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLike(String value) {
            addCriterion("PLATFORM_TYPE like", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotLike(String value) {
            addCriterion("PLATFORM_TYPE not like", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeIn(List<String> values) {
            addCriterion("PLATFORM_TYPE in", values, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotIn(List<String> values) {
            addCriterion("PLATFORM_TYPE not in", values, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeBetween(String value1, String value2) {
            addCriterion("PLATFORM_TYPE between", value1, value2, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotBetween(String value1, String value2) {
            addCriterion("PLATFORM_TYPE not between", value1, value2, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformNumIsNull() {
            addCriterion("PLATFORM_NUM is null");
            return (Criteria) this;
        }

        public Criteria andPlatformNumIsNotNull() {
            addCriterion("PLATFORM_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformNumEqualTo(String value) {
            addCriterion("PLATFORM_NUM =", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumNotEqualTo(String value) {
            addCriterion("PLATFORM_NUM <>", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumGreaterThan(String value) {
            addCriterion("PLATFORM_NUM >", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumGreaterThanOrEqualTo(String value) {
            addCriterion("PLATFORM_NUM >=", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumLessThan(String value) {
            addCriterion("PLATFORM_NUM <", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumLessThanOrEqualTo(String value) {
            addCriterion("PLATFORM_NUM <=", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumLike(String value) {
            addCriterion("PLATFORM_NUM like", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumNotLike(String value) {
            addCriterion("PLATFORM_NUM not like", value, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumIn(List<String> values) {
            addCriterion("PLATFORM_NUM in", values, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumNotIn(List<String> values) {
            addCriterion("PLATFORM_NUM not in", values, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumBetween(String value1, String value2) {
            addCriterion("PLATFORM_NUM between", value1, value2, "platformNum");
            return (Criteria) this;
        }

        public Criteria andPlatformNumNotBetween(String value1, String value2) {
            addCriterion("PLATFORM_NUM not between", value1, value2, "platformNum");
            return (Criteria) this;
        }

        public Criteria andTechnologyAddressIsNull() {
            addCriterion("TECHNOLOGY_ADDRESS is null");
            return (Criteria) this;
        }

        public Criteria andTechnologyAddressIsNotNull() {
            addCriterion("TECHNOLOGY_ADDRESS is not null");
            return (Criteria) this;
        }

        public Criteria andTechnologyAddressEqualTo(String value) {
            addCriterion("TECHNOLOGY_ADDRESS =", value, "technologyAddress");
            return (Criteria) this;
        }

        public Criteria andTechnologyAddressNotEqualTo(String value) {
            addCriterion("TECHNOLOGY_ADDRESS <>", value, "technologyAddress");
            return (Criteria) this;
        }

        public Criteria andTechnologyAddressGreaterThan(String value) {
            addCriterion("TECHNOLOGY_ADDRESS >", value, "technologyAddress");
            return (Criteria) this;
        }

        public Criteria andTechnologyAddressGreaterThanOrEqualTo(String value) {
            addCriterion("TECHNOLOGY_ADDRESS >=", value, "technologyAddress");
            return (Criteria) this;
        }

        public Criteria andTechnologyAddressLessThan(String value) {
            addCriterion("TECHNOLOGY_ADDRESS <", value, "technologyAddress");
            return (Criteria) this;
        }

        public Criteria andTechnologyAddressLessThanOrEqualTo(String value) {
            addCriterion("TECHNOLOGY_ADDRESS <=", value, "technologyAddress");
            return (Criteria) this;
        }

        public Criteria andTechnologyAddressLike(String value) {
            addCriterion("TECHNOLOGY_ADDRESS like", value, "technologyAddress");
            return (Criteria) this;
        }

        public Criteria andTechnologyAddressNotLike(String value) {
            addCriterion("TECHNOLOGY_ADDRESS not like", value, "technologyAddress");
            return (Criteria) this;
        }

        public Criteria andTechnologyAddressIn(List<String> values) {
            addCriterion("TECHNOLOGY_ADDRESS in", values, "technologyAddress");
            return (Criteria) this;
        }

        public Criteria andTechnologyAddressNotIn(List<String> values) {
            addCriterion("TECHNOLOGY_ADDRESS not in", values, "technologyAddress");
            return (Criteria) this;
        }

        public Criteria andTechnologyAddressBetween(String value1, String value2) {
            addCriterion("TECHNOLOGY_ADDRESS between", value1, value2, "technologyAddress");
            return (Criteria) this;
        }

        public Criteria andTechnologyAddressNotBetween(String value1, String value2) {
            addCriterion("TECHNOLOGY_ADDRESS not between", value1, value2, "technologyAddress");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtIsNull() {
            addCriterion("TECHNOLOGY_AMT is null");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtIsNotNull() {
            addCriterion("TECHNOLOGY_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtEqualTo(String value) {
            addCriterion("TECHNOLOGY_AMT =", value, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtNotEqualTo(String value) {
            addCriterion("TECHNOLOGY_AMT <>", value, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtGreaterThan(String value) {
            addCriterion("TECHNOLOGY_AMT >", value, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtGreaterThanOrEqualTo(String value) {
            addCriterion("TECHNOLOGY_AMT >=", value, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtLessThan(String value) {
            addCriterion("TECHNOLOGY_AMT <", value, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtLessThanOrEqualTo(String value) {
            addCriterion("TECHNOLOGY_AMT <=", value, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtLike(String value) {
            addCriterion("TECHNOLOGY_AMT like", value, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtNotLike(String value) {
            addCriterion("TECHNOLOGY_AMT not like", value, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtIn(List<String> values) {
            addCriterion("TECHNOLOGY_AMT in", values, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtNotIn(List<String> values) {
            addCriterion("TECHNOLOGY_AMT not in", values, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtBetween(String value1, String value2) {
            addCriterion("TECHNOLOGY_AMT between", value1, value2, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyAmtNotBetween(String value1, String value2) {
            addCriterion("TECHNOLOGY_AMT not between", value1, value2, "technologyAmt");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeIsNull() {
            addCriterion("TECHNOLOGY_FEE is null");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeIsNotNull() {
            addCriterion("TECHNOLOGY_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeEqualTo(String value) {
            addCriterion("TECHNOLOGY_FEE =", value, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeNotEqualTo(String value) {
            addCriterion("TECHNOLOGY_FEE <>", value, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeGreaterThan(String value) {
            addCriterion("TECHNOLOGY_FEE >", value, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeGreaterThanOrEqualTo(String value) {
            addCriterion("TECHNOLOGY_FEE >=", value, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeLessThan(String value) {
            addCriterion("TECHNOLOGY_FEE <", value, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeLessThanOrEqualTo(String value) {
            addCriterion("TECHNOLOGY_FEE <=", value, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeLike(String value) {
            addCriterion("TECHNOLOGY_FEE like", value, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeNotLike(String value) {
            addCriterion("TECHNOLOGY_FEE not like", value, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeIn(List<String> values) {
            addCriterion("TECHNOLOGY_FEE in", values, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeNotIn(List<String> values) {
            addCriterion("TECHNOLOGY_FEE not in", values, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeBetween(String value1, String value2) {
            addCriterion("TECHNOLOGY_FEE between", value1, value2, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andTechnologyFeeNotBetween(String value1, String value2) {
            addCriterion("TECHNOLOGY_FEE not between", value1, value2, "technologyFee");
            return (Criteria) this;
        }

        public Criteria andClearAddressIsNull() {
            addCriterion("CLEAR_ADDRESS is null");
            return (Criteria) this;
        }

        public Criteria andClearAddressIsNotNull() {
            addCriterion("CLEAR_ADDRESS is not null");
            return (Criteria) this;
        }

        public Criteria andClearAddressEqualTo(String value) {
            addCriterion("CLEAR_ADDRESS =", value, "clearAddress");
            return (Criteria) this;
        }

        public Criteria andClearAddressNotEqualTo(String value) {
            addCriterion("CLEAR_ADDRESS <>", value, "clearAddress");
            return (Criteria) this;
        }

        public Criteria andClearAddressGreaterThan(String value) {
            addCriterion("CLEAR_ADDRESS >", value, "clearAddress");
            return (Criteria) this;
        }

        public Criteria andClearAddressGreaterThanOrEqualTo(String value) {
            addCriterion("CLEAR_ADDRESS >=", value, "clearAddress");
            return (Criteria) this;
        }

        public Criteria andClearAddressLessThan(String value) {
            addCriterion("CLEAR_ADDRESS <", value, "clearAddress");
            return (Criteria) this;
        }

        public Criteria andClearAddressLessThanOrEqualTo(String value) {
            addCriterion("CLEAR_ADDRESS <=", value, "clearAddress");
            return (Criteria) this;
        }

        public Criteria andClearAddressLike(String value) {
            addCriterion("CLEAR_ADDRESS like", value, "clearAddress");
            return (Criteria) this;
        }

        public Criteria andClearAddressNotLike(String value) {
            addCriterion("CLEAR_ADDRESS not like", value, "clearAddress");
            return (Criteria) this;
        }

        public Criteria andClearAddressIn(List<String> values) {
            addCriterion("CLEAR_ADDRESS in", values, "clearAddress");
            return (Criteria) this;
        }

        public Criteria andClearAddressNotIn(List<String> values) {
            addCriterion("CLEAR_ADDRESS not in", values, "clearAddress");
            return (Criteria) this;
        }

        public Criteria andClearAddressBetween(String value1, String value2) {
            addCriterion("CLEAR_ADDRESS between", value1, value2, "clearAddress");
            return (Criteria) this;
        }

        public Criteria andClearAddressNotBetween(String value1, String value2) {
            addCriterion("CLEAR_ADDRESS not between", value1, value2, "clearAddress");
            return (Criteria) this;
        }

        public Criteria andClearAmtIsNull() {
            addCriterion("CLEAR_AMT is null");
            return (Criteria) this;
        }

        public Criteria andClearAmtIsNotNull() {
            addCriterion("CLEAR_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andClearAmtEqualTo(String value) {
            addCriterion("CLEAR_AMT =", value, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtNotEqualTo(String value) {
            addCriterion("CLEAR_AMT <>", value, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtGreaterThan(String value) {
            addCriterion("CLEAR_AMT >", value, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtGreaterThanOrEqualTo(String value) {
            addCriterion("CLEAR_AMT >=", value, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtLessThan(String value) {
            addCriterion("CLEAR_AMT <", value, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtLessThanOrEqualTo(String value) {
            addCriterion("CLEAR_AMT <=", value, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtLike(String value) {
            addCriterion("CLEAR_AMT like", value, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtNotLike(String value) {
            addCriterion("CLEAR_AMT not like", value, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtIn(List<String> values) {
            addCriterion("CLEAR_AMT in", values, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtNotIn(List<String> values) {
            addCriterion("CLEAR_AMT not in", values, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtBetween(String value1, String value2) {
            addCriterion("CLEAR_AMT between", value1, value2, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearAmtNotBetween(String value1, String value2) {
            addCriterion("CLEAR_AMT not between", value1, value2, "clearAmt");
            return (Criteria) this;
        }

        public Criteria andClearFeeIsNull() {
            addCriterion("CLEAR_FEE is null");
            return (Criteria) this;
        }

        public Criteria andClearFeeIsNotNull() {
            addCriterion("CLEAR_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andClearFeeEqualTo(String value) {
            addCriterion("CLEAR_FEE =", value, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeNotEqualTo(String value) {
            addCriterion("CLEAR_FEE <>", value, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeGreaterThan(String value) {
            addCriterion("CLEAR_FEE >", value, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeGreaterThanOrEqualTo(String value) {
            addCriterion("CLEAR_FEE >=", value, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeLessThan(String value) {
            addCriterion("CLEAR_FEE <", value, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeLessThanOrEqualTo(String value) {
            addCriterion("CLEAR_FEE <=", value, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeLike(String value) {
            addCriterion("CLEAR_FEE like", value, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeNotLike(String value) {
            addCriterion("CLEAR_FEE not like", value, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeIn(List<String> values) {
            addCriterion("CLEAR_FEE in", values, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeNotIn(List<String> values) {
            addCriterion("CLEAR_FEE not in", values, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeBetween(String value1, String value2) {
            addCriterion("CLEAR_FEE between", value1, value2, "clearFee");
            return (Criteria) this;
        }

        public Criteria andClearFeeNotBetween(String value1, String value2) {
            addCriterion("CLEAR_FEE not between", value1, value2, "clearFee");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNull() {
            addCriterion("ORDER_NUM is null");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNotNull() {
            addCriterion("ORDER_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNumEqualTo(BigDecimal value) {
            addCriterion("ORDER_NUM =", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotEqualTo(BigDecimal value) {
            addCriterion("ORDER_NUM <>", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThan(BigDecimal value) {
            addCriterion("ORDER_NUM >", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDER_NUM >=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThan(BigDecimal value) {
            addCriterion("ORDER_NUM <", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDER_NUM <=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumIn(List<BigDecimal> values) {
            addCriterion("ORDER_NUM in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotIn(List<BigDecimal> values) {
            addCriterion("ORDER_NUM not in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDER_NUM between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDER_NUM not between", value1, value2, "orderNum");
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

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreatePersonIsNull() {
            addCriterion("CREATE_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andCreatePersonIsNotNull() {
            addCriterion("CREATE_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andCreatePersonEqualTo(String value) {
            addCriterion("CREATE_PERSON =", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotEqualTo(String value) {
            addCriterion("CREATE_PERSON <>", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonGreaterThan(String value) {
            addCriterion("CREATE_PERSON >", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_PERSON >=", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonLessThan(String value) {
            addCriterion("CREATE_PERSON <", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonLessThanOrEqualTo(String value) {
            addCriterion("CREATE_PERSON <=", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonLike(String value) {
            addCriterion("CREATE_PERSON like", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotLike(String value) {
            addCriterion("CREATE_PERSON not like", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonIn(List<String> values) {
            addCriterion("CREATE_PERSON in", values, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotIn(List<String> values) {
            addCriterion("CREATE_PERSON not in", values, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonBetween(String value1, String value2) {
            addCriterion("CREATE_PERSON between", value1, value2, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotBetween(String value1, String value2) {
            addCriterion("CREATE_PERSON not between", value1, value2, "createPerson");
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