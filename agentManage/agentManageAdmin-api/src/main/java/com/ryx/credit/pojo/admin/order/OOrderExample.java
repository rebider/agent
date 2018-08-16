package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OOrderExample() {
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

        public Criteria andONumIsNull() {
            addCriterion("O_NUM is null");
            return (Criteria) this;
        }

        public Criteria andONumIsNotNull() {
            addCriterion("O_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andONumEqualTo(String value) {
            addCriterion("O_NUM =", value, "oNum");
            return (Criteria) this;
        }

        public Criteria andONumNotEqualTo(String value) {
            addCriterion("O_NUM <>", value, "oNum");
            return (Criteria) this;
        }

        public Criteria andONumGreaterThan(String value) {
            addCriterion("O_NUM >", value, "oNum");
            return (Criteria) this;
        }

        public Criteria andONumGreaterThanOrEqualTo(String value) {
            addCriterion("O_NUM >=", value, "oNum");
            return (Criteria) this;
        }

        public Criteria andONumLessThan(String value) {
            addCriterion("O_NUM <", value, "oNum");
            return (Criteria) this;
        }

        public Criteria andONumLessThanOrEqualTo(String value) {
            addCriterion("O_NUM <=", value, "oNum");
            return (Criteria) this;
        }

        public Criteria andONumLike(String value) {
            addCriterion("O_NUM like", value, "oNum");
            return (Criteria) this;
        }

        public Criteria andONumNotLike(String value) {
            addCriterion("O_NUM not like", value, "oNum");
            return (Criteria) this;
        }

        public Criteria andONumIn(List<String> values) {
            addCriterion("O_NUM in", values, "oNum");
            return (Criteria) this;
        }

        public Criteria andONumNotIn(List<String> values) {
            addCriterion("O_NUM not in", values, "oNum");
            return (Criteria) this;
        }

        public Criteria andONumBetween(String value1, String value2) {
            addCriterion("O_NUM between", value1, value2, "oNum");
            return (Criteria) this;
        }

        public Criteria andONumNotBetween(String value1, String value2) {
            addCriterion("O_NUM not between", value1, value2, "oNum");
            return (Criteria) this;
        }

        public Criteria andOApytimeIsNull() {
            addCriterion("O_APYTIME is null");
            return (Criteria) this;
        }

        public Criteria andOApytimeIsNotNull() {
            addCriterion("O_APYTIME is not null");
            return (Criteria) this;
        }

        public Criteria andOApytimeEqualTo(Date value) {
            addCriterion("O_APYTIME =", value, "oApytime");
            return (Criteria) this;
        }

        public Criteria andOApytimeNotEqualTo(Date value) {
            addCriterion("O_APYTIME <>", value, "oApytime");
            return (Criteria) this;
        }

        public Criteria andOApytimeGreaterThan(Date value) {
            addCriterion("O_APYTIME >", value, "oApytime");
            return (Criteria) this;
        }

        public Criteria andOApytimeGreaterThanOrEqualTo(Date value) {
            addCriterion("O_APYTIME >=", value, "oApytime");
            return (Criteria) this;
        }

        public Criteria andOApytimeLessThan(Date value) {
            addCriterion("O_APYTIME <", value, "oApytime");
            return (Criteria) this;
        }

        public Criteria andOApytimeLessThanOrEqualTo(Date value) {
            addCriterion("O_APYTIME <=", value, "oApytime");
            return (Criteria) this;
        }

        public Criteria andOApytimeIn(List<Date> values) {
            addCriterion("O_APYTIME in", values, "oApytime");
            return (Criteria) this;
        }

        public Criteria andOApytimeNotIn(List<Date> values) {
            addCriterion("O_APYTIME not in", values, "oApytime");
            return (Criteria) this;
        }

        public Criteria andOApytimeBetween(Date value1, Date value2) {
            addCriterion("O_APYTIME between", value1, value2, "oApytime");
            return (Criteria) this;
        }

        public Criteria andOApytimeNotBetween(Date value1, Date value2) {
            addCriterion("O_APYTIME not between", value1, value2, "oApytime");
            return (Criteria) this;
        }

        public Criteria andOInuretimeIsNull() {
            addCriterion("O_INURETIME is null");
            return (Criteria) this;
        }

        public Criteria andOInuretimeIsNotNull() {
            addCriterion("O_INURETIME is not null");
            return (Criteria) this;
        }

        public Criteria andOInuretimeEqualTo(Date value) {
            addCriterion("O_INURETIME =", value, "oInuretime");
            return (Criteria) this;
        }

        public Criteria andOInuretimeNotEqualTo(Date value) {
            addCriterion("O_INURETIME <>", value, "oInuretime");
            return (Criteria) this;
        }

        public Criteria andOInuretimeGreaterThan(Date value) {
            addCriterion("O_INURETIME >", value, "oInuretime");
            return (Criteria) this;
        }

        public Criteria andOInuretimeGreaterThanOrEqualTo(Date value) {
            addCriterion("O_INURETIME >=", value, "oInuretime");
            return (Criteria) this;
        }

        public Criteria andOInuretimeLessThan(Date value) {
            addCriterion("O_INURETIME <", value, "oInuretime");
            return (Criteria) this;
        }

        public Criteria andOInuretimeLessThanOrEqualTo(Date value) {
            addCriterion("O_INURETIME <=", value, "oInuretime");
            return (Criteria) this;
        }

        public Criteria andOInuretimeIn(List<Date> values) {
            addCriterion("O_INURETIME in", values, "oInuretime");
            return (Criteria) this;
        }

        public Criteria andOInuretimeNotIn(List<Date> values) {
            addCriterion("O_INURETIME not in", values, "oInuretime");
            return (Criteria) this;
        }

        public Criteria andOInuretimeBetween(Date value1, Date value2) {
            addCriterion("O_INURETIME between", value1, value2, "oInuretime");
            return (Criteria) this;
        }

        public Criteria andOInuretimeNotBetween(Date value1, Date value2) {
            addCriterion("O_INURETIME not between", value1, value2, "oInuretime");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("USER_ID like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("USER_ID not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andPaymentMethodIsNull() {
            addCriterion("PAYMENT_METHOD is null");
            return (Criteria) this;
        }

        public Criteria andPaymentMethodIsNotNull() {
            addCriterion("PAYMENT_METHOD is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentMethodEqualTo(String value) {
            addCriterion("PAYMENT_METHOD =", value, "paymentMethod");
            return (Criteria) this;
        }

        public Criteria andPaymentMethodNotEqualTo(String value) {
            addCriterion("PAYMENT_METHOD <>", value, "paymentMethod");
            return (Criteria) this;
        }

        public Criteria andPaymentMethodGreaterThan(String value) {
            addCriterion("PAYMENT_METHOD >", value, "paymentMethod");
            return (Criteria) this;
        }

        public Criteria andPaymentMethodGreaterThanOrEqualTo(String value) {
            addCriterion("PAYMENT_METHOD >=", value, "paymentMethod");
            return (Criteria) this;
        }

        public Criteria andPaymentMethodLessThan(String value) {
            addCriterion("PAYMENT_METHOD <", value, "paymentMethod");
            return (Criteria) this;
        }

        public Criteria andPaymentMethodLessThanOrEqualTo(String value) {
            addCriterion("PAYMENT_METHOD <=", value, "paymentMethod");
            return (Criteria) this;
        }

        public Criteria andPaymentMethodLike(String value) {
            addCriterion("PAYMENT_METHOD like", value, "paymentMethod");
            return (Criteria) this;
        }

        public Criteria andPaymentMethodNotLike(String value) {
            addCriterion("PAYMENT_METHOD not like", value, "paymentMethod");
            return (Criteria) this;
        }

        public Criteria andPaymentMethodIn(List<String> values) {
            addCriterion("PAYMENT_METHOD in", values, "paymentMethod");
            return (Criteria) this;
        }

        public Criteria andPaymentMethodNotIn(List<String> values) {
            addCriterion("PAYMENT_METHOD not in", values, "paymentMethod");
            return (Criteria) this;
        }

        public Criteria andPaymentMethodBetween(String value1, String value2) {
            addCriterion("PAYMENT_METHOD between", value1, value2, "paymentMethod");
            return (Criteria) this;
        }

        public Criteria andPaymentMethodNotBetween(String value1, String value2) {
            addCriterion("PAYMENT_METHOD not between", value1, value2, "paymentMethod");
            return (Criteria) this;
        }

        public Criteria andOAmoIsNull() {
            addCriterion("O_AMO is null");
            return (Criteria) this;
        }

        public Criteria andOAmoIsNotNull() {
            addCriterion("O_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andOAmoEqualTo(BigDecimal value) {
            addCriterion("O_AMO =", value, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoNotEqualTo(BigDecimal value) {
            addCriterion("O_AMO <>", value, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoGreaterThan(BigDecimal value) {
            addCriterion("O_AMO >", value, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("O_AMO >=", value, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoLessThan(BigDecimal value) {
            addCriterion("O_AMO <", value, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("O_AMO <=", value, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoIn(List<BigDecimal> values) {
            addCriterion("O_AMO in", values, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoNotIn(List<BigDecimal> values) {
            addCriterion("O_AMO not in", values, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("O_AMO between", value1, value2, "oAmo");
            return (Criteria) this;
        }

        public Criteria andOAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("O_AMO not between", value1, value2, "oAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoIsNull() {
            addCriterion("INCENTIVE_AMO is null");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoIsNotNull() {
            addCriterion("INCENTIVE_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoEqualTo(BigDecimal value) {
            addCriterion("INCENTIVE_AMO =", value, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoNotEqualTo(BigDecimal value) {
            addCriterion("INCENTIVE_AMO <>", value, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoGreaterThan(BigDecimal value) {
            addCriterion("INCENTIVE_AMO >", value, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("INCENTIVE_AMO >=", value, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoLessThan(BigDecimal value) {
            addCriterion("INCENTIVE_AMO <", value, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("INCENTIVE_AMO <=", value, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoIn(List<BigDecimal> values) {
            addCriterion("INCENTIVE_AMO in", values, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoNotIn(List<BigDecimal> values) {
            addCriterion("INCENTIVE_AMO not in", values, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("INCENTIVE_AMO between", value1, value2, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andIncentiveAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("INCENTIVE_AMO not between", value1, value2, "incentiveAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoIsNull() {
            addCriterion("PAY_AMO is null");
            return (Criteria) this;
        }

        public Criteria andPayAmoIsNotNull() {
            addCriterion("PAY_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andPayAmoEqualTo(BigDecimal value) {
            addCriterion("PAY_AMO =", value, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoNotEqualTo(BigDecimal value) {
            addCriterion("PAY_AMO <>", value, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoGreaterThan(BigDecimal value) {
            addCriterion("PAY_AMO >", value, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_AMO >=", value, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoLessThan(BigDecimal value) {
            addCriterion("PAY_AMO <", value, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_AMO <=", value, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoIn(List<BigDecimal> values) {
            addCriterion("PAY_AMO in", values, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoNotIn(List<BigDecimal> values) {
            addCriterion("PAY_AMO not in", values, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_AMO between", value1, value2, "payAmo");
            return (Criteria) this;
        }

        public Criteria andPayAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_AMO not between", value1, value2, "payAmo");
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

        public Criteria andRuleidIsNull() {
            addCriterion("RULEID is null");
            return (Criteria) this;
        }

        public Criteria andRuleidIsNotNull() {
            addCriterion("RULEID is not null");
            return (Criteria) this;
        }

        public Criteria andRuleidEqualTo(String value) {
            addCriterion("RULEID =", value, "ruleid");
            return (Criteria) this;
        }

        public Criteria andRuleidNotEqualTo(String value) {
            addCriterion("RULEID <>", value, "ruleid");
            return (Criteria) this;
        }

        public Criteria andRuleidGreaterThan(String value) {
            addCriterion("RULEID >", value, "ruleid");
            return (Criteria) this;
        }

        public Criteria andRuleidGreaterThanOrEqualTo(String value) {
            addCriterion("RULEID >=", value, "ruleid");
            return (Criteria) this;
        }

        public Criteria andRuleidLessThan(String value) {
            addCriterion("RULEID <", value, "ruleid");
            return (Criteria) this;
        }

        public Criteria andRuleidLessThanOrEqualTo(String value) {
            addCriterion("RULEID <=", value, "ruleid");
            return (Criteria) this;
        }

        public Criteria andRuleidLike(String value) {
            addCriterion("RULEID like", value, "ruleid");
            return (Criteria) this;
        }

        public Criteria andRuleidNotLike(String value) {
            addCriterion("RULEID not like", value, "ruleid");
            return (Criteria) this;
        }

        public Criteria andRuleidIn(List<String> values) {
            addCriterion("RULEID in", values, "ruleid");
            return (Criteria) this;
        }

        public Criteria andRuleidNotIn(List<String> values) {
            addCriterion("RULEID not in", values, "ruleid");
            return (Criteria) this;
        }

        public Criteria andRuleidBetween(String value1, String value2) {
            addCriterion("RULEID between", value1, value2, "ruleid");
            return (Criteria) this;
        }

        public Criteria andRuleidNotBetween(String value1, String value2) {
            addCriterion("RULEID not between", value1, value2, "ruleid");
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

        public Criteria andOrderPlatformIsNull() {
            addCriterion("ORDER_PLATFORM is null");
            return (Criteria) this;
        }

        public Criteria andOrderPlatformIsNotNull() {
            addCriterion("ORDER_PLATFORM is not null");
            return (Criteria) this;
        }

        public Criteria andOrderPlatformEqualTo(String value) {
            addCriterion("ORDER_PLATFORM =", value, "orderPlatform");
            return (Criteria) this;
        }

        public Criteria andOrderPlatformNotEqualTo(String value) {
            addCriterion("ORDER_PLATFORM <>", value, "orderPlatform");
            return (Criteria) this;
        }

        public Criteria andOrderPlatformGreaterThan(String value) {
            addCriterion("ORDER_PLATFORM >", value, "orderPlatform");
            return (Criteria) this;
        }

        public Criteria andOrderPlatformGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_PLATFORM >=", value, "orderPlatform");
            return (Criteria) this;
        }

        public Criteria andOrderPlatformLessThan(String value) {
            addCriterion("ORDER_PLATFORM <", value, "orderPlatform");
            return (Criteria) this;
        }

        public Criteria andOrderPlatformLessThanOrEqualTo(String value) {
            addCriterion("ORDER_PLATFORM <=", value, "orderPlatform");
            return (Criteria) this;
        }

        public Criteria andOrderPlatformLike(String value) {
            addCriterion("ORDER_PLATFORM like", value, "orderPlatform");
            return (Criteria) this;
        }

        public Criteria andOrderPlatformNotLike(String value) {
            addCriterion("ORDER_PLATFORM not like", value, "orderPlatform");
            return (Criteria) this;
        }

        public Criteria andOrderPlatformIn(List<String> values) {
            addCriterion("ORDER_PLATFORM in", values, "orderPlatform");
            return (Criteria) this;
        }

        public Criteria andOrderPlatformNotIn(List<String> values) {
            addCriterion("ORDER_PLATFORM not in", values, "orderPlatform");
            return (Criteria) this;
        }

        public Criteria andOrderPlatformBetween(String value1, String value2) {
            addCriterion("ORDER_PLATFORM between", value1, value2, "orderPlatform");
            return (Criteria) this;
        }

        public Criteria andOrderPlatformNotBetween(String value1, String value2) {
            addCriterion("ORDER_PLATFORM not between", value1, value2, "orderPlatform");
            return (Criteria) this;
        }

        public Criteria andReviewStatusIsNull() {
            addCriterion("REVIEW_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andReviewStatusIsNotNull() {
            addCriterion("REVIEW_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andReviewStatusEqualTo(BigDecimal value) {
            addCriterion("REVIEW_STATUS =", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusNotEqualTo(BigDecimal value) {
            addCriterion("REVIEW_STATUS <>", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusGreaterThan(BigDecimal value) {
            addCriterion("REVIEW_STATUS >", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REVIEW_STATUS >=", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusLessThan(BigDecimal value) {
            addCriterion("REVIEW_STATUS <", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REVIEW_STATUS <=", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusIn(List<BigDecimal> values) {
            addCriterion("REVIEW_STATUS in", values, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusNotIn(List<BigDecimal> values) {
            addCriterion("REVIEW_STATUS not in", values, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REVIEW_STATUS between", value1, value2, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REVIEW_STATUS not between", value1, value2, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNull() {
            addCriterion("ORDER_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNotNull() {
            addCriterion("ORDER_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusEqualTo(BigDecimal value) {
            addCriterion("ORDER_STATUS =", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotEqualTo(BigDecimal value) {
            addCriterion("ORDER_STATUS <>", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThan(BigDecimal value) {
            addCriterion("ORDER_STATUS >", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDER_STATUS >=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThan(BigDecimal value) {
            addCriterion("ORDER_STATUS <", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDER_STATUS <=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIn(List<BigDecimal> values) {
            addCriterion("ORDER_STATUS in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotIn(List<BigDecimal> values) {
            addCriterion("ORDER_STATUS not in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDER_STATUS between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDER_STATUS not between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andClearStatusIsNull() {
            addCriterion("CLEAR_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andClearStatusIsNotNull() {
            addCriterion("CLEAR_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andClearStatusEqualTo(BigDecimal value) {
            addCriterion("CLEAR_STATUS =", value, "clearStatus");
            return (Criteria) this;
        }

        public Criteria andClearStatusNotEqualTo(BigDecimal value) {
            addCriterion("CLEAR_STATUS <>", value, "clearStatus");
            return (Criteria) this;
        }

        public Criteria andClearStatusGreaterThan(BigDecimal value) {
            addCriterion("CLEAR_STATUS >", value, "clearStatus");
            return (Criteria) this;
        }

        public Criteria andClearStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLEAR_STATUS >=", value, "clearStatus");
            return (Criteria) this;
        }

        public Criteria andClearStatusLessThan(BigDecimal value) {
            addCriterion("CLEAR_STATUS <", value, "clearStatus");
            return (Criteria) this;
        }

        public Criteria andClearStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLEAR_STATUS <=", value, "clearStatus");
            return (Criteria) this;
        }

        public Criteria andClearStatusIn(List<BigDecimal> values) {
            addCriterion("CLEAR_STATUS in", values, "clearStatus");
            return (Criteria) this;
        }

        public Criteria andClearStatusNotIn(List<BigDecimal> values) {
            addCriterion("CLEAR_STATUS not in", values, "clearStatus");
            return (Criteria) this;
        }

        public Criteria andClearStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLEAR_STATUS between", value1, value2, "clearStatus");
            return (Criteria) this;
        }

        public Criteria andClearStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLEAR_STATUS not between", value1, value2, "clearStatus");
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

        public Criteria andUUserIsNull() {
            addCriterion("U_USER is null");
            return (Criteria) this;
        }

        public Criteria andUUserIsNotNull() {
            addCriterion("U_USER is not null");
            return (Criteria) this;
        }

        public Criteria andUUserEqualTo(String value) {
            addCriterion("U_USER =", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotEqualTo(String value) {
            addCriterion("U_USER <>", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserGreaterThan(String value) {
            addCriterion("U_USER >", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserGreaterThanOrEqualTo(String value) {
            addCriterion("U_USER >=", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserLessThan(String value) {
            addCriterion("U_USER <", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserLessThanOrEqualTo(String value) {
            addCriterion("U_USER <=", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserLike(String value) {
            addCriterion("U_USER like", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotLike(String value) {
            addCriterion("U_USER not like", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserIn(List<String> values) {
            addCriterion("U_USER in", values, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotIn(List<String> values) {
            addCriterion("U_USER not in", values, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserBetween(String value1, String value2) {
            addCriterion("U_USER between", value1, value2, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotBetween(String value1, String value2) {
            addCriterion("U_USER not between", value1, value2, "uUser");
            return (Criteria) this;
        }

        public Criteria andUTimeIsNull() {
            addCriterion("U_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUTimeIsNotNull() {
            addCriterion("U_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUTimeEqualTo(Date value) {
            addCriterion("U_TIME =", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeNotEqualTo(Date value) {
            addCriterion("U_TIME <>", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeGreaterThan(Date value) {
            addCriterion("U_TIME >", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("U_TIME >=", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeLessThan(Date value) {
            addCriterion("U_TIME <", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeLessThanOrEqualTo(Date value) {
            addCriterion("U_TIME <=", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeIn(List<Date> values) {
            addCriterion("U_TIME in", values, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeNotIn(List<Date> values) {
            addCriterion("U_TIME not in", values, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeBetween(Date value1, Date value2) {
            addCriterion("U_TIME between", value1, value2, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeNotBetween(Date value1, Date value2) {
            addCriterion("U_TIME not between", value1, value2, "uTime");
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

        public Criteria andBusIdIsNull() {
            addCriterion("BUS_ID is null");
            return (Criteria) this;
        }

        public Criteria andBusIdIsNotNull() {
            addCriterion("BUS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBusIdEqualTo(String value) {
            addCriterion("BUS_ID =", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotEqualTo(String value) {
            addCriterion("BUS_ID <>", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdGreaterThan(String value) {
            addCriterion("BUS_ID >", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_ID >=", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdLessThan(String value) {
            addCriterion("BUS_ID <", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdLessThanOrEqualTo(String value) {
            addCriterion("BUS_ID <=", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdLike(String value) {
            addCriterion("BUS_ID like", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotLike(String value) {
            addCriterion("BUS_ID not like", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdIn(List<String> values) {
            addCriterion("BUS_ID in", values, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotIn(List<String> values) {
            addCriterion("BUS_ID not in", values, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdBetween(String value1, String value2) {
            addCriterion("BUS_ID between", value1, value2, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotBetween(String value1, String value2) {
            addCriterion("BUS_ID not between", value1, value2, "busId");
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