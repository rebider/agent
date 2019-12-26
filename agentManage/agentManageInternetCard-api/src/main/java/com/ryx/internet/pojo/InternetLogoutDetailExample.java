package com.ryx.internet.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InternetLogoutDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public InternetLogoutDetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
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

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page=page;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
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

        public Criteria andRenewIdIsNull() {
            addCriterion("RENEW_ID is null");
            return (Criteria) this;
        }

        public Criteria andRenewIdIsNotNull() {
            addCriterion("RENEW_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRenewIdEqualTo(String value) {
            addCriterion("RENEW_ID =", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdNotEqualTo(String value) {
            addCriterion("RENEW_ID <>", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdGreaterThan(String value) {
            addCriterion("RENEW_ID >", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdGreaterThanOrEqualTo(String value) {
            addCriterion("RENEW_ID >=", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdLessThan(String value) {
            addCriterion("RENEW_ID <", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdLessThanOrEqualTo(String value) {
            addCriterion("RENEW_ID <=", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdLike(String value) {
            addCriterion("RENEW_ID like", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdNotLike(String value) {
            addCriterion("RENEW_ID not like", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdIn(List<String> values) {
            addCriterion("RENEW_ID in", values, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdNotIn(List<String> values) {
            addCriterion("RENEW_ID not in", values, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdBetween(String value1, String value2) {
            addCriterion("RENEW_ID between", value1, value2, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdNotBetween(String value1, String value2) {
            addCriterion("RENEW_ID not between", value1, value2, "renewId");
            return (Criteria) this;
        }

        public Criteria andIccidNumIsNull() {
            addCriterion("ICCID_NUM is null");
            return (Criteria) this;
        }

        public Criteria andIccidNumIsNotNull() {
            addCriterion("ICCID_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andIccidNumEqualTo(String value) {
            addCriterion("ICCID_NUM =", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumNotEqualTo(String value) {
            addCriterion("ICCID_NUM <>", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumGreaterThan(String value) {
            addCriterion("ICCID_NUM >", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumGreaterThanOrEqualTo(String value) {
            addCriterion("ICCID_NUM >=", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumLessThan(String value) {
            addCriterion("ICCID_NUM <", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumLessThanOrEqualTo(String value) {
            addCriterion("ICCID_NUM <=", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumLike(String value) {
            addCriterion("ICCID_NUM like", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumNotLike(String value) {
            addCriterion("ICCID_NUM not like", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumIn(List<String> values) {
            addCriterion("ICCID_NUM in", values, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumNotIn(List<String> values) {
            addCriterion("ICCID_NUM not in", values, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumBetween(String value1, String value2) {
            addCriterion("ICCID_NUM between", value1, value2, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumNotBetween(String value1, String value2) {
            addCriterion("ICCID_NUM not between", value1, value2, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardStatusIsNull() {
            addCriterion("INTERNET_CARD_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andInternetCardStatusIsNotNull() {
            addCriterion("INTERNET_CARD_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andInternetCardStatusEqualTo(BigDecimal value) {
            addCriterion("INTERNET_CARD_STATUS =", value, "internetCardStatus");
            return (Criteria) this;
        }

        public Criteria andInternetCardStatusNotEqualTo(BigDecimal value) {
            addCriterion("INTERNET_CARD_STATUS <>", value, "internetCardStatus");
            return (Criteria) this;
        }

        public Criteria andInternetCardStatusGreaterThan(BigDecimal value) {
            addCriterion("INTERNET_CARD_STATUS >", value, "internetCardStatus");
            return (Criteria) this;
        }

        public Criteria andInternetCardStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("INTERNET_CARD_STATUS >=", value, "internetCardStatus");
            return (Criteria) this;
        }

        public Criteria andInternetCardStatusLessThan(BigDecimal value) {
            addCriterion("INTERNET_CARD_STATUS <", value, "internetCardStatus");
            return (Criteria) this;
        }

        public Criteria andInternetCardStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("INTERNET_CARD_STATUS <=", value, "internetCardStatus");
            return (Criteria) this;
        }

        public Criteria andInternetCardStatusIn(List<BigDecimal> values) {
            addCriterion("INTERNET_CARD_STATUS in", values, "internetCardStatus");
            return (Criteria) this;
        }

        public Criteria andInternetCardStatusNotIn(List<BigDecimal> values) {
            addCriterion("INTERNET_CARD_STATUS not in", values, "internetCardStatus");
            return (Criteria) this;
        }

        public Criteria andInternetCardStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("INTERNET_CARD_STATUS between", value1, value2, "internetCardStatus");
            return (Criteria) this;
        }

        public Criteria andInternetCardStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("INTERNET_CARD_STATUS not between", value1, value2, "internetCardStatus");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("ORDER_ID like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("ORDER_ID not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("ORDER_ID in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("ORDER_ID not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andSnNumIsNull() {
            addCriterion("SN_NUM is null");
            return (Criteria) this;
        }

        public Criteria andSnNumIsNotNull() {
            addCriterion("SN_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andSnNumEqualTo(String value) {
            addCriterion("SN_NUM =", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumNotEqualTo(String value) {
            addCriterion("SN_NUM <>", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumGreaterThan(String value) {
            addCriterion("SN_NUM >", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumGreaterThanOrEqualTo(String value) {
            addCriterion("SN_NUM >=", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumLessThan(String value) {
            addCriterion("SN_NUM <", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumLessThanOrEqualTo(String value) {
            addCriterion("SN_NUM <=", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumLike(String value) {
            addCriterion("SN_NUM like", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumNotLike(String value) {
            addCriterion("SN_NUM not like", value, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumIn(List<String> values) {
            addCriterion("SN_NUM in", values, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumNotIn(List<String> values) {
            addCriterion("SN_NUM not in", values, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumBetween(String value1, String value2) {
            addCriterion("SN_NUM between", value1, value2, "snNum");
            return (Criteria) this;
        }

        public Criteria andSnNumNotBetween(String value1, String value2) {
            addCriterion("SN_NUM not between", value1, value2, "snNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumIsNull() {
            addCriterion("INTERNET_CARD_NUM is null");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumIsNotNull() {
            addCriterion("INTERNET_CARD_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumEqualTo(String value) {
            addCriterion("INTERNET_CARD_NUM =", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumNotEqualTo(String value) {
            addCriterion("INTERNET_CARD_NUM <>", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumGreaterThan(String value) {
            addCriterion("INTERNET_CARD_NUM >", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumGreaterThanOrEqualTo(String value) {
            addCriterion("INTERNET_CARD_NUM >=", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumLessThan(String value) {
            addCriterion("INTERNET_CARD_NUM <", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumLessThanOrEqualTo(String value) {
            addCriterion("INTERNET_CARD_NUM <=", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumLike(String value) {
            addCriterion("INTERNET_CARD_NUM like", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumNotLike(String value) {
            addCriterion("INTERNET_CARD_NUM not like", value, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumIn(List<String> values) {
            addCriterion("INTERNET_CARD_NUM in", values, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumNotIn(List<String> values) {
            addCriterion("INTERNET_CARD_NUM not in", values, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumBetween(String value1, String value2) {
            addCriterion("INTERNET_CARD_NUM between", value1, value2, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andInternetCardNumNotBetween(String value1, String value2) {
            addCriterion("INTERNET_CARD_NUM not between", value1, value2, "internetCardNum");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeIsNull() {
            addCriterion("OPEN_ACCOUNT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeIsNotNull() {
            addCriterion("OPEN_ACCOUNT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeEqualTo(Date value) {
            addCriterion("OPEN_ACCOUNT_TIME =", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeNotEqualTo(Date value) {
            addCriterion("OPEN_ACCOUNT_TIME <>", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeGreaterThan(Date value) {
            addCriterion("OPEN_ACCOUNT_TIME >", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("OPEN_ACCOUNT_TIME >=", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeLessThan(Date value) {
            addCriterion("OPEN_ACCOUNT_TIME <", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeLessThanOrEqualTo(Date value) {
            addCriterion("OPEN_ACCOUNT_TIME <=", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeIn(List<Date> values) {
            addCriterion("OPEN_ACCOUNT_TIME in", values, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeNotIn(List<Date> values) {
            addCriterion("OPEN_ACCOUNT_TIME not in", values, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeBetween(Date value1, Date value2) {
            addCriterion("OPEN_ACCOUNT_TIME between", value1, value2, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeNotBetween(Date value1, Date value2) {
            addCriterion("OPEN_ACCOUNT_TIME not between", value1, value2, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIsNull() {
            addCriterion("EXPIRE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIsNotNull() {
            addCriterion("EXPIRE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeEqualTo(Date value) {
            addCriterion("EXPIRE_TIME =", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotEqualTo(Date value) {
            addCriterion("EXPIRE_TIME <>", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThan(Date value) {
            addCriterion("EXPIRE_TIME >", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("EXPIRE_TIME >=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThan(Date value) {
            addCriterion("EXPIRE_TIME <", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThanOrEqualTo(Date value) {
            addCriterion("EXPIRE_TIME <=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIn(List<Date> values) {
            addCriterion("EXPIRE_TIME in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotIn(List<Date> values) {
            addCriterion("EXPIRE_TIME not in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeBetween(Date value1, Date value2) {
            addCriterion("EXPIRE_TIME between", value1, value2, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotBetween(Date value1, Date value2) {
            addCriterion("EXPIRE_TIME not between", value1, value2, "expireTime");
            return (Criteria) this;
        }

        public Criteria andMerNameIsNull() {
            addCriterion("MER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andMerNameIsNotNull() {
            addCriterion("MER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andMerNameEqualTo(String value) {
            addCriterion("MER_NAME =", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameNotEqualTo(String value) {
            addCriterion("MER_NAME <>", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameGreaterThan(String value) {
            addCriterion("MER_NAME >", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameGreaterThanOrEqualTo(String value) {
            addCriterion("MER_NAME >=", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameLessThan(String value) {
            addCriterion("MER_NAME <", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameLessThanOrEqualTo(String value) {
            addCriterion("MER_NAME <=", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameLike(String value) {
            addCriterion("MER_NAME like", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameNotLike(String value) {
            addCriterion("MER_NAME not like", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameIn(List<String> values) {
            addCriterion("MER_NAME in", values, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameNotIn(List<String> values) {
            addCriterion("MER_NAME not in", values, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameBetween(String value1, String value2) {
            addCriterion("MER_NAME between", value1, value2, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameNotBetween(String value1, String value2) {
            addCriterion("MER_NAME not between", value1, value2, "merName");
            return (Criteria) this;
        }

        public Criteria andMerIdIsNull() {
            addCriterion("MER_ID is null");
            return (Criteria) this;
        }

        public Criteria andMerIdIsNotNull() {
            addCriterion("MER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMerIdEqualTo(String value) {
            addCriterion("MER_ID =", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotEqualTo(String value) {
            addCriterion("MER_ID <>", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdGreaterThan(String value) {
            addCriterion("MER_ID >", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdGreaterThanOrEqualTo(String value) {
            addCriterion("MER_ID >=", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdLessThan(String value) {
            addCriterion("MER_ID <", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdLessThanOrEqualTo(String value) {
            addCriterion("MER_ID <=", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdLike(String value) {
            addCriterion("MER_ID like", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotLike(String value) {
            addCriterion("MER_ID not like", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdIn(List<String> values) {
            addCriterion("MER_ID in", values, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotIn(List<String> values) {
            addCriterion("MER_ID not in", values, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdBetween(String value1, String value2) {
            addCriterion("MER_ID between", value1, value2, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotBetween(String value1, String value2) {
            addCriterion("MER_ID not between", value1, value2, "merId");
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

        public Criteria andLogoutStatusIsNull() {
            addCriterion("LOGOUT_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andLogoutStatusIsNotNull() {
            addCriterion("LOGOUT_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andLogoutStatusEqualTo(String value) {
            addCriterion("LOGOUT_STATUS =", value, "logoutStatus");
            return (Criteria) this;
        }

        public Criteria andLogoutStatusNotEqualTo(String value) {
            addCriterion("LOGOUT_STATUS <>", value, "logoutStatus");
            return (Criteria) this;
        }

        public Criteria andLogoutStatusGreaterThan(String value) {
            addCriterion("LOGOUT_STATUS >", value, "logoutStatus");
            return (Criteria) this;
        }

        public Criteria andLogoutStatusGreaterThanOrEqualTo(String value) {
            addCriterion("LOGOUT_STATUS >=", value, "logoutStatus");
            return (Criteria) this;
        }

        public Criteria andLogoutStatusLessThan(String value) {
            addCriterion("LOGOUT_STATUS <", value, "logoutStatus");
            return (Criteria) this;
        }

        public Criteria andLogoutStatusLessThanOrEqualTo(String value) {
            addCriterion("LOGOUT_STATUS <=", value, "logoutStatus");
            return (Criteria) this;
        }

        public Criteria andLogoutStatusLike(String value) {
            addCriterion("LOGOUT_STATUS like", value, "logoutStatus");
            return (Criteria) this;
        }

        public Criteria andLogoutStatusNotLike(String value) {
            addCriterion("LOGOUT_STATUS not like", value, "logoutStatus");
            return (Criteria) this;
        }

        public Criteria andLogoutStatusIn(List<String> values) {
            addCriterion("LOGOUT_STATUS in", values, "logoutStatus");
            return (Criteria) this;
        }

        public Criteria andLogoutStatusNotIn(List<String> values) {
            addCriterion("LOGOUT_STATUS not in", values, "logoutStatus");
            return (Criteria) this;
        }

        public Criteria andLogoutStatusBetween(String value1, String value2) {
            addCriterion("LOGOUT_STATUS between", value1, value2, "logoutStatus");
            return (Criteria) this;
        }

        public Criteria andLogoutStatusNotBetween(String value1, String value2) {
            addCriterion("LOGOUT_STATUS not between", value1, value2, "logoutStatus");
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

        public Criteria andBusNumIsNull() {
            addCriterion("BUS_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBusNumIsNotNull() {
            addCriterion("BUS_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBusNumEqualTo(String value) {
            addCriterion("BUS_NUM =", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotEqualTo(String value) {
            addCriterion("BUS_NUM <>", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumGreaterThan(String value) {
            addCriterion("BUS_NUM >", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_NUM >=", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumLessThan(String value) {
            addCriterion("BUS_NUM <", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumLessThanOrEqualTo(String value) {
            addCriterion("BUS_NUM <=", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumLike(String value) {
            addCriterion("BUS_NUM like", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotLike(String value) {
            addCriterion("BUS_NUM not like", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumIn(List<String> values) {
            addCriterion("BUS_NUM in", values, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotIn(List<String> values) {
            addCriterion("BUS_NUM not in", values, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumBetween(String value1, String value2) {
            addCriterion("BUS_NUM between", value1, value2, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotBetween(String value1, String value2) {
            addCriterion("BUS_NUM not between", value1, value2, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusPlatformIsNull() {
            addCriterion("BUS_PLATFORM is null");
            return (Criteria) this;
        }

        public Criteria andBusPlatformIsNotNull() {
            addCriterion("BUS_PLATFORM is not null");
            return (Criteria) this;
        }

        public Criteria andBusPlatformEqualTo(String value) {
            addCriterion("BUS_PLATFORM =", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformNotEqualTo(String value) {
            addCriterion("BUS_PLATFORM <>", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformGreaterThan(String value) {
            addCriterion("BUS_PLATFORM >", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_PLATFORM >=", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformLessThan(String value) {
            addCriterion("BUS_PLATFORM <", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformLessThanOrEqualTo(String value) {
            addCriterion("BUS_PLATFORM <=", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformLike(String value) {
            addCriterion("BUS_PLATFORM like", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformNotLike(String value) {
            addCriterion("BUS_PLATFORM not like", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformIn(List<String> values) {
            addCriterion("BUS_PLATFORM in", values, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformNotIn(List<String> values) {
            addCriterion("BUS_PLATFORM not in", values, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformBetween(String value1, String value2) {
            addCriterion("BUS_PLATFORM between", value1, value2, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformNotBetween(String value1, String value2) {
            addCriterion("BUS_PLATFORM not between", value1, value2, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictIsNull() {
            addCriterion("AG_DOC_DISTRICT is null");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictIsNotNull() {
            addCriterion("AG_DOC_DISTRICT is not null");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictEqualTo(String value) {
            addCriterion("AG_DOC_DISTRICT =", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictNotEqualTo(String value) {
            addCriterion("AG_DOC_DISTRICT <>", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictGreaterThan(String value) {
            addCriterion("AG_DOC_DISTRICT >", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictGreaterThanOrEqualTo(String value) {
            addCriterion("AG_DOC_DISTRICT >=", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictLessThan(String value) {
            addCriterion("AG_DOC_DISTRICT <", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictLessThanOrEqualTo(String value) {
            addCriterion("AG_DOC_DISTRICT <=", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictLike(String value) {
            addCriterion("AG_DOC_DISTRICT like", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictNotLike(String value) {
            addCriterion("AG_DOC_DISTRICT not like", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictIn(List<String> values) {
            addCriterion("AG_DOC_DISTRICT in", values, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictNotIn(List<String> values) {
            addCriterion("AG_DOC_DISTRICT not in", values, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictBetween(String value1, String value2) {
            addCriterion("AG_DOC_DISTRICT between", value1, value2, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictNotBetween(String value1, String value2) {
            addCriterion("AG_DOC_DISTRICT not between", value1, value2, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocProIsNull() {
            addCriterion("AG_DOC_PRO is null");
            return (Criteria) this;
        }

        public Criteria andAgDocProIsNotNull() {
            addCriterion("AG_DOC_PRO is not null");
            return (Criteria) this;
        }

        public Criteria andAgDocProEqualTo(String value) {
            addCriterion("AG_DOC_PRO =", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProNotEqualTo(String value) {
            addCriterion("AG_DOC_PRO <>", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProGreaterThan(String value) {
            addCriterion("AG_DOC_PRO >", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProGreaterThanOrEqualTo(String value) {
            addCriterion("AG_DOC_PRO >=", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProLessThan(String value) {
            addCriterion("AG_DOC_PRO <", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProLessThanOrEqualTo(String value) {
            addCriterion("AG_DOC_PRO <=", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProLike(String value) {
            addCriterion("AG_DOC_PRO like", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProNotLike(String value) {
            addCriterion("AG_DOC_PRO not like", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProIn(List<String> values) {
            addCriterion("AG_DOC_PRO in", values, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProNotIn(List<String> values) {
            addCriterion("AG_DOC_PRO not in", values, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProBetween(String value1, String value2) {
            addCriterion("AG_DOC_PRO between", value1, value2, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProNotBetween(String value1, String value2) {
            addCriterion("AG_DOC_PRO not between", value1, value2, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonIsNull() {
            addCriterion("BUS_CONTACT_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonIsNotNull() {
            addCriterion("BUS_CONTACT_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonEqualTo(String value) {
            addCriterion("BUS_CONTACT_PERSON =", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonNotEqualTo(String value) {
            addCriterion("BUS_CONTACT_PERSON <>", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonGreaterThan(String value) {
            addCriterion("BUS_CONTACT_PERSON >", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_CONTACT_PERSON >=", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonLessThan(String value) {
            addCriterion("BUS_CONTACT_PERSON <", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonLessThanOrEqualTo(String value) {
            addCriterion("BUS_CONTACT_PERSON <=", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonLike(String value) {
            addCriterion("BUS_CONTACT_PERSON like", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonNotLike(String value) {
            addCriterion("BUS_CONTACT_PERSON not like", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonIn(List<String> values) {
            addCriterion("BUS_CONTACT_PERSON in", values, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonNotIn(List<String> values) {
            addCriterion("BUS_CONTACT_PERSON not in", values, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonBetween(String value1, String value2) {
            addCriterion("BUS_CONTACT_PERSON between", value1, value2, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonNotBetween(String value1, String value2) {
            addCriterion("BUS_CONTACT_PERSON not between", value1, value2, "busContactPerson");
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

        public Criteria andIssuerIsNull() {
            addCriterion("ISSUER is null");
            return (Criteria) this;
        }

        public Criteria andIssuerIsNotNull() {
            addCriterion("ISSUER is not null");
            return (Criteria) this;
        }

        public Criteria andIssuerEqualTo(String value) {
            addCriterion("ISSUER =", value, "issuer");
            return (Criteria) this;
        }

        public Criteria andIssuerNotEqualTo(String value) {
            addCriterion("ISSUER <>", value, "issuer");
            return (Criteria) this;
        }

        public Criteria andIssuerGreaterThan(String value) {
            addCriterion("ISSUER >", value, "issuer");
            return (Criteria) this;
        }

        public Criteria andIssuerGreaterThanOrEqualTo(String value) {
            addCriterion("ISSUER >=", value, "issuer");
            return (Criteria) this;
        }

        public Criteria andIssuerLessThan(String value) {
            addCriterion("ISSUER <", value, "issuer");
            return (Criteria) this;
        }

        public Criteria andIssuerLessThanOrEqualTo(String value) {
            addCriterion("ISSUER <=", value, "issuer");
            return (Criteria) this;
        }

        public Criteria andIssuerLike(String value) {
            addCriterion("ISSUER like", value, "issuer");
            return (Criteria) this;
        }

        public Criteria andIssuerNotLike(String value) {
            addCriterion("ISSUER not like", value, "issuer");
            return (Criteria) this;
        }

        public Criteria andIssuerIn(List<String> values) {
            addCriterion("ISSUER in", values, "issuer");
            return (Criteria) this;
        }

        public Criteria andIssuerNotIn(List<String> values) {
            addCriterion("ISSUER not in", values, "issuer");
            return (Criteria) this;
        }

        public Criteria andIssuerBetween(String value1, String value2) {
            addCriterion("ISSUER between", value1, value2, "issuer");
            return (Criteria) this;
        }

        public Criteria andIssuerNotBetween(String value1, String value2) {
            addCriterion("ISSUER not between", value1, value2, "issuer");
            return (Criteria) this;
        }

        public Criteria andFailCauseIsNull() {
            addCriterion("FAIL_CAUSE is null");
            return (Criteria) this;
        }

        public Criteria andFailCauseIsNotNull() {
            addCriterion("FAIL_CAUSE is not null");
            return (Criteria) this;
        }

        public Criteria andFailCauseEqualTo(String value) {
            addCriterion("FAIL_CAUSE =", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseNotEqualTo(String value) {
            addCriterion("FAIL_CAUSE <>", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseGreaterThan(String value) {
            addCriterion("FAIL_CAUSE >", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseGreaterThanOrEqualTo(String value) {
            addCriterion("FAIL_CAUSE >=", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseLessThan(String value) {
            addCriterion("FAIL_CAUSE <", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseLessThanOrEqualTo(String value) {
            addCriterion("FAIL_CAUSE <=", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseLike(String value) {
            addCriterion("FAIL_CAUSE like", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseNotLike(String value) {
            addCriterion("FAIL_CAUSE not like", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseIn(List<String> values) {
            addCriterion("FAIL_CAUSE in", values, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseNotIn(List<String> values) {
            addCriterion("FAIL_CAUSE not in", values, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseBetween(String value1, String value2) {
            addCriterion("FAIL_CAUSE between", value1, value2, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseNotBetween(String value1, String value2) {
            addCriterion("FAIL_CAUSE not between", value1, value2, "failCause");
            return (Criteria) this;
        }

        public Criteria andMobileOrderNoIsNull() {
            addCriterion("MOBILE_ORDER_NO is null");
            return (Criteria) this;
        }

        public Criteria andMobileOrderNoIsNotNull() {
            addCriterion("MOBILE_ORDER_NO is not null");
            return (Criteria) this;
        }

        public Criteria andMobileOrderNoEqualTo(String value) {
            addCriterion("MOBILE_ORDER_NO =", value, "mobileOrderNo");
            return (Criteria) this;
        }

        public Criteria andMobileOrderNoNotEqualTo(String value) {
            addCriterion("MOBILE_ORDER_NO <>", value, "mobileOrderNo");
            return (Criteria) this;
        }

        public Criteria andMobileOrderNoGreaterThan(String value) {
            addCriterion("MOBILE_ORDER_NO >", value, "mobileOrderNo");
            return (Criteria) this;
        }

        public Criteria andMobileOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("MOBILE_ORDER_NO >=", value, "mobileOrderNo");
            return (Criteria) this;
        }

        public Criteria andMobileOrderNoLessThan(String value) {
            addCriterion("MOBILE_ORDER_NO <", value, "mobileOrderNo");
            return (Criteria) this;
        }

        public Criteria andMobileOrderNoLessThanOrEqualTo(String value) {
            addCriterion("MOBILE_ORDER_NO <=", value, "mobileOrderNo");
            return (Criteria) this;
        }

        public Criteria andMobileOrderNoLike(String value) {
            addCriterion("MOBILE_ORDER_NO like", value, "mobileOrderNo");
            return (Criteria) this;
        }

        public Criteria andMobileOrderNoNotLike(String value) {
            addCriterion("MOBILE_ORDER_NO not like", value, "mobileOrderNo");
            return (Criteria) this;
        }

        public Criteria andMobileOrderNoIn(List<String> values) {
            addCriterion("MOBILE_ORDER_NO in", values, "mobileOrderNo");
            return (Criteria) this;
        }

        public Criteria andMobileOrderNoNotIn(List<String> values) {
            addCriterion("MOBILE_ORDER_NO not in", values, "mobileOrderNo");
            return (Criteria) this;
        }

        public Criteria andMobileOrderNoBetween(String value1, String value2) {
            addCriterion("MOBILE_ORDER_NO between", value1, value2, "mobileOrderNo");
            return (Criteria) this;
        }

        public Criteria andMobileOrderNoNotBetween(String value1, String value2) {
            addCriterion("MOBILE_ORDER_NO not between", value1, value2, "mobileOrderNo");
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
    }
}