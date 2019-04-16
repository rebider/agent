package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PRemitInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PRemitInfoExample() {
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

        public Criteria andInAccountTypeIsNull() {
            addCriterion("IN_ACCOUNT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andInAccountTypeIsNotNull() {
            addCriterion("IN_ACCOUNT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andInAccountTypeEqualTo(String value) {
            addCriterion("IN_ACCOUNT_TYPE =", value, "inAccountType");
            return (Criteria) this;
        }

        public Criteria andInAccountTypeNotEqualTo(String value) {
            addCriterion("IN_ACCOUNT_TYPE <>", value, "inAccountType");
            return (Criteria) this;
        }

        public Criteria andInAccountTypeGreaterThan(String value) {
            addCriterion("IN_ACCOUNT_TYPE >", value, "inAccountType");
            return (Criteria) this;
        }

        public Criteria andInAccountTypeGreaterThanOrEqualTo(String value) {
            addCriterion("IN_ACCOUNT_TYPE >=", value, "inAccountType");
            return (Criteria) this;
        }

        public Criteria andInAccountTypeLessThan(String value) {
            addCriterion("IN_ACCOUNT_TYPE <", value, "inAccountType");
            return (Criteria) this;
        }

        public Criteria andInAccountTypeLessThanOrEqualTo(String value) {
            addCriterion("IN_ACCOUNT_TYPE <=", value, "inAccountType");
            return (Criteria) this;
        }

        public Criteria andInAccountTypeLike(String value) {
            addCriterion("IN_ACCOUNT_TYPE like", value, "inAccountType");
            return (Criteria) this;
        }

        public Criteria andInAccountTypeNotLike(String value) {
            addCriterion("IN_ACCOUNT_TYPE not like", value, "inAccountType");
            return (Criteria) this;
        }

        public Criteria andInAccountTypeIn(List<String> values) {
            addCriterion("IN_ACCOUNT_TYPE in", values, "inAccountType");
            return (Criteria) this;
        }

        public Criteria andInAccountTypeNotIn(List<String> values) {
            addCriterion("IN_ACCOUNT_TYPE not in", values, "inAccountType");
            return (Criteria) this;
        }

        public Criteria andInAccountTypeBetween(String value1, String value2) {
            addCriterion("IN_ACCOUNT_TYPE between", value1, value2, "inAccountType");
            return (Criteria) this;
        }

        public Criteria andInAccountTypeNotBetween(String value1, String value2) {
            addCriterion("IN_ACCOUNT_TYPE not between", value1, value2, "inAccountType");
            return (Criteria) this;
        }

        public Criteria andInAccountNameIsNull() {
            addCriterion("IN_ACCOUNT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andInAccountNameIsNotNull() {
            addCriterion("IN_ACCOUNT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andInAccountNameEqualTo(String value) {
            addCriterion("IN_ACCOUNT_NAME =", value, "inAccountName");
            return (Criteria) this;
        }

        public Criteria andInAccountNameNotEqualTo(String value) {
            addCriterion("IN_ACCOUNT_NAME <>", value, "inAccountName");
            return (Criteria) this;
        }

        public Criteria andInAccountNameGreaterThan(String value) {
            addCriterion("IN_ACCOUNT_NAME >", value, "inAccountName");
            return (Criteria) this;
        }

        public Criteria andInAccountNameGreaterThanOrEqualTo(String value) {
            addCriterion("IN_ACCOUNT_NAME >=", value, "inAccountName");
            return (Criteria) this;
        }

        public Criteria andInAccountNameLessThan(String value) {
            addCriterion("IN_ACCOUNT_NAME <", value, "inAccountName");
            return (Criteria) this;
        }

        public Criteria andInAccountNameLessThanOrEqualTo(String value) {
            addCriterion("IN_ACCOUNT_NAME <=", value, "inAccountName");
            return (Criteria) this;
        }

        public Criteria andInAccountNameLike(String value) {
            addCriterion("IN_ACCOUNT_NAME like", value, "inAccountName");
            return (Criteria) this;
        }

        public Criteria andInAccountNameNotLike(String value) {
            addCriterion("IN_ACCOUNT_NAME not like", value, "inAccountName");
            return (Criteria) this;
        }

        public Criteria andInAccountNameIn(List<String> values) {
            addCriterion("IN_ACCOUNT_NAME in", values, "inAccountName");
            return (Criteria) this;
        }

        public Criteria andInAccountNameNotIn(List<String> values) {
            addCriterion("IN_ACCOUNT_NAME not in", values, "inAccountName");
            return (Criteria) this;
        }

        public Criteria andInAccountNameBetween(String value1, String value2) {
            addCriterion("IN_ACCOUNT_NAME between", value1, value2, "inAccountName");
            return (Criteria) this;
        }

        public Criteria andInAccountNameNotBetween(String value1, String value2) {
            addCriterion("IN_ACCOUNT_NAME not between", value1, value2, "inAccountName");
            return (Criteria) this;
        }

        public Criteria andOutAccountIsNull() {
            addCriterion("OUT_ACCOUNT is null");
            return (Criteria) this;
        }

        public Criteria andOutAccountIsNotNull() {
            addCriterion("OUT_ACCOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andOutAccountEqualTo(String value) {
            addCriterion("OUT_ACCOUNT =", value, "outAccount");
            return (Criteria) this;
        }

        public Criteria andOutAccountNotEqualTo(String value) {
            addCriterion("OUT_ACCOUNT <>", value, "outAccount");
            return (Criteria) this;
        }

        public Criteria andOutAccountGreaterThan(String value) {
            addCriterion("OUT_ACCOUNT >", value, "outAccount");
            return (Criteria) this;
        }

        public Criteria andOutAccountGreaterThanOrEqualTo(String value) {
            addCriterion("OUT_ACCOUNT >=", value, "outAccount");
            return (Criteria) this;
        }

        public Criteria andOutAccountLessThan(String value) {
            addCriterion("OUT_ACCOUNT <", value, "outAccount");
            return (Criteria) this;
        }

        public Criteria andOutAccountLessThanOrEqualTo(String value) {
            addCriterion("OUT_ACCOUNT <=", value, "outAccount");
            return (Criteria) this;
        }

        public Criteria andOutAccountLike(String value) {
            addCriterion("OUT_ACCOUNT like", value, "outAccount");
            return (Criteria) this;
        }

        public Criteria andOutAccountNotLike(String value) {
            addCriterion("OUT_ACCOUNT not like", value, "outAccount");
            return (Criteria) this;
        }

        public Criteria andOutAccountIn(List<String> values) {
            addCriterion("OUT_ACCOUNT in", values, "outAccount");
            return (Criteria) this;
        }

        public Criteria andOutAccountNotIn(List<String> values) {
            addCriterion("OUT_ACCOUNT not in", values, "outAccount");
            return (Criteria) this;
        }

        public Criteria andOutAccountBetween(String value1, String value2) {
            addCriterion("OUT_ACCOUNT between", value1, value2, "outAccount");
            return (Criteria) this;
        }

        public Criteria andOutAccountNotBetween(String value1, String value2) {
            addCriterion("OUT_ACCOUNT not between", value1, value2, "outAccount");
            return (Criteria) this;
        }

        public Criteria andOutAccountBankIsNull() {
            addCriterion("OUT_ACCOUNT_BANK is null");
            return (Criteria) this;
        }

        public Criteria andOutAccountBankIsNotNull() {
            addCriterion("OUT_ACCOUNT_BANK is not null");
            return (Criteria) this;
        }

        public Criteria andOutAccountBankEqualTo(String value) {
            addCriterion("OUT_ACCOUNT_BANK =", value, "outAccountBank");
            return (Criteria) this;
        }

        public Criteria andOutAccountBankNotEqualTo(String value) {
            addCriterion("OUT_ACCOUNT_BANK <>", value, "outAccountBank");
            return (Criteria) this;
        }

        public Criteria andOutAccountBankGreaterThan(String value) {
            addCriterion("OUT_ACCOUNT_BANK >", value, "outAccountBank");
            return (Criteria) this;
        }

        public Criteria andOutAccountBankGreaterThanOrEqualTo(String value) {
            addCriterion("OUT_ACCOUNT_BANK >=", value, "outAccountBank");
            return (Criteria) this;
        }

        public Criteria andOutAccountBankLessThan(String value) {
            addCriterion("OUT_ACCOUNT_BANK <", value, "outAccountBank");
            return (Criteria) this;
        }

        public Criteria andOutAccountBankLessThanOrEqualTo(String value) {
            addCriterion("OUT_ACCOUNT_BANK <=", value, "outAccountBank");
            return (Criteria) this;
        }

        public Criteria andOutAccountBankLike(String value) {
            addCriterion("OUT_ACCOUNT_BANK like", value, "outAccountBank");
            return (Criteria) this;
        }

        public Criteria andOutAccountBankNotLike(String value) {
            addCriterion("OUT_ACCOUNT_BANK not like", value, "outAccountBank");
            return (Criteria) this;
        }

        public Criteria andOutAccountBankIn(List<String> values) {
            addCriterion("OUT_ACCOUNT_BANK in", values, "outAccountBank");
            return (Criteria) this;
        }

        public Criteria andOutAccountBankNotIn(List<String> values) {
            addCriterion("OUT_ACCOUNT_BANK not in", values, "outAccountBank");
            return (Criteria) this;
        }

        public Criteria andOutAccountBankBetween(String value1, String value2) {
            addCriterion("OUT_ACCOUNT_BANK between", value1, value2, "outAccountBank");
            return (Criteria) this;
        }

        public Criteria andOutAccountBankNotBetween(String value1, String value2) {
            addCriterion("OUT_ACCOUNT_BANK not between", value1, value2, "outAccountBank");
            return (Criteria) this;
        }

        public Criteria andRemitAmtIsNull() {
            addCriterion("REMIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andRemitAmtIsNotNull() {
            addCriterion("REMIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andRemitAmtEqualTo(BigDecimal value) {
            addCriterion("REMIT_AMT =", value, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtNotEqualTo(BigDecimal value) {
            addCriterion("REMIT_AMT <>", value, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtGreaterThan(BigDecimal value) {
            addCriterion("REMIT_AMT >", value, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REMIT_AMT >=", value, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtLessThan(BigDecimal value) {
            addCriterion("REMIT_AMT <", value, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REMIT_AMT <=", value, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtIn(List<BigDecimal> values) {
            addCriterion("REMIT_AMT in", values, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtNotIn(List<BigDecimal> values) {
            addCriterion("REMIT_AMT not in", values, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REMIT_AMT between", value1, value2, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andRemitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REMIT_AMT not between", value1, value2, "remitAmt");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNull() {
            addCriterion("FILE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNotNull() {
            addCriterion("FILE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFileNameEqualTo(String value) {
            addCriterion("FILE_NAME =", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotEqualTo(String value) {
            addCriterion("FILE_NAME <>", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThan(String value) {
            addCriterion("FILE_NAME >", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_NAME >=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThan(String value) {
            addCriterion("FILE_NAME <", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThanOrEqualTo(String value) {
            addCriterion("FILE_NAME <=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLike(String value) {
            addCriterion("FILE_NAME like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotLike(String value) {
            addCriterion("FILE_NAME not like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameIn(List<String> values) {
            addCriterion("FILE_NAME in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotIn(List<String> values) {
            addCriterion("FILE_NAME not in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameBetween(String value1, String value2) {
            addCriterion("FILE_NAME between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotBetween(String value1, String value2) {
            addCriterion("FILE_NAME not between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNull() {
            addCriterion("FILE_PATH is null");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNotNull() {
            addCriterion("FILE_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andFilePathEqualTo(String value) {
            addCriterion("FILE_PATH =", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotEqualTo(String value) {
            addCriterion("FILE_PATH <>", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThan(String value) {
            addCriterion("FILE_PATH >", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_PATH >=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThan(String value) {
            addCriterion("FILE_PATH <", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThanOrEqualTo(String value) {
            addCriterion("FILE_PATH <=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLike(String value) {
            addCriterion("FILE_PATH like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotLike(String value) {
            addCriterion("FILE_PATH not like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathIn(List<String> values) {
            addCriterion("FILE_PATH in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotIn(List<String> values) {
            addCriterion("FILE_PATH not in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathBetween(String value1, String value2) {
            addCriterion("FILE_PATH between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotBetween(String value1, String value2) {
            addCriterion("FILE_PATH not between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andRemitDateIsNull() {
            addCriterion("REMIT_DATE is null");
            return (Criteria) this;
        }

        public Criteria andRemitDateIsNotNull() {
            addCriterion("REMIT_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andRemitDateEqualTo(Date value) {
            addCriterion("REMIT_DATE =", value, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateNotEqualTo(Date value) {
            addCriterion("REMIT_DATE <>", value, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateGreaterThan(Date value) {
            addCriterion("REMIT_DATE >", value, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateGreaterThanOrEqualTo(Date value) {
            addCriterion("REMIT_DATE >=", value, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateLessThan(Date value) {
            addCriterion("REMIT_DATE <", value, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateLessThanOrEqualTo(Date value) {
            addCriterion("REMIT_DATE <=", value, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateIn(List<Date> values) {
            addCriterion("REMIT_DATE in", values, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateNotIn(List<Date> values) {
            addCriterion("REMIT_DATE not in", values, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateBetween(Date value1, Date value2) {
            addCriterion("REMIT_DATE between", value1, value2, "remitDate");
            return (Criteria) this;
        }

        public Criteria andRemitDateNotBetween(Date value1, Date value2) {
            addCriterion("REMIT_DATE not between", value1, value2, "remitDate");
            return (Criteria) this;
        }

        public Criteria andCitySupplyIdIsNull() {
            addCriterion("CITY_SUPPLY_ID is null");
            return (Criteria) this;
        }

        public Criteria andCitySupplyIdIsNotNull() {
            addCriterion("CITY_SUPPLY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCitySupplyIdEqualTo(String value) {
            addCriterion("CITY_SUPPLY_ID =", value, "citySupplyId");
            return (Criteria) this;
        }

        public Criteria andCitySupplyIdNotEqualTo(String value) {
            addCriterion("CITY_SUPPLY_ID <>", value, "citySupplyId");
            return (Criteria) this;
        }

        public Criteria andCitySupplyIdGreaterThan(String value) {
            addCriterion("CITY_SUPPLY_ID >", value, "citySupplyId");
            return (Criteria) this;
        }

        public Criteria andCitySupplyIdGreaterThanOrEqualTo(String value) {
            addCriterion("CITY_SUPPLY_ID >=", value, "citySupplyId");
            return (Criteria) this;
        }

        public Criteria andCitySupplyIdLessThan(String value) {
            addCriterion("CITY_SUPPLY_ID <", value, "citySupplyId");
            return (Criteria) this;
        }

        public Criteria andCitySupplyIdLessThanOrEqualTo(String value) {
            addCriterion("CITY_SUPPLY_ID <=", value, "citySupplyId");
            return (Criteria) this;
        }

        public Criteria andCitySupplyIdLike(String value) {
            addCriterion("CITY_SUPPLY_ID like", value, "citySupplyId");
            return (Criteria) this;
        }

        public Criteria andCitySupplyIdNotLike(String value) {
            addCriterion("CITY_SUPPLY_ID not like", value, "citySupplyId");
            return (Criteria) this;
        }

        public Criteria andCitySupplyIdIn(List<String> values) {
            addCriterion("CITY_SUPPLY_ID in", values, "citySupplyId");
            return (Criteria) this;
        }

        public Criteria andCitySupplyIdNotIn(List<String> values) {
            addCriterion("CITY_SUPPLY_ID not in", values, "citySupplyId");
            return (Criteria) this;
        }

        public Criteria andCitySupplyIdBetween(String value1, String value2) {
            addCriterion("CITY_SUPPLY_ID between", value1, value2, "citySupplyId");
            return (Criteria) this;
        }

        public Criteria andCitySupplyIdNotBetween(String value1, String value2) {
            addCriterion("CITY_SUPPLY_ID not between", value1, value2, "citySupplyId");
            return (Criteria) this;
        }

        public Criteria andRev1IsNull() {
            addCriterion("REV1 is null");
            return (Criteria) this;
        }

        public Criteria andRev1IsNotNull() {
            addCriterion("REV1 is not null");
            return (Criteria) this;
        }

        public Criteria andRev1EqualTo(String value) {
            addCriterion("REV1 =", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotEqualTo(String value) {
            addCriterion("REV1 <>", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1GreaterThan(String value) {
            addCriterion("REV1 >", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1GreaterThanOrEqualTo(String value) {
            addCriterion("REV1 >=", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1LessThan(String value) {
            addCriterion("REV1 <", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1LessThanOrEqualTo(String value) {
            addCriterion("REV1 <=", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1Like(String value) {
            addCriterion("REV1 like", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotLike(String value) {
            addCriterion("REV1 not like", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1In(List<String> values) {
            addCriterion("REV1 in", values, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotIn(List<String> values) {
            addCriterion("REV1 not in", values, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1Between(String value1, String value2) {
            addCriterion("REV1 between", value1, value2, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotBetween(String value1, String value2) {
            addCriterion("REV1 not between", value1, value2, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev2IsNull() {
            addCriterion("REV2 is null");
            return (Criteria) this;
        }

        public Criteria andRev2IsNotNull() {
            addCriterion("REV2 is not null");
            return (Criteria) this;
        }

        public Criteria andRev2EqualTo(String value) {
            addCriterion("REV2 =", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotEqualTo(String value) {
            addCriterion("REV2 <>", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2GreaterThan(String value) {
            addCriterion("REV2 >", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2GreaterThanOrEqualTo(String value) {
            addCriterion("REV2 >=", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2LessThan(String value) {
            addCriterion("REV2 <", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2LessThanOrEqualTo(String value) {
            addCriterion("REV2 <=", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2Like(String value) {
            addCriterion("REV2 like", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotLike(String value) {
            addCriterion("REV2 not like", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2In(List<String> values) {
            addCriterion("REV2 in", values, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotIn(List<String> values) {
            addCriterion("REV2 not in", values, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2Between(String value1, String value2) {
            addCriterion("REV2 between", value1, value2, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotBetween(String value1, String value2) {
            addCriterion("REV2 not between", value1, value2, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev3IsNull() {
            addCriterion("REV3 is null");
            return (Criteria) this;
        }

        public Criteria andRev3IsNotNull() {
            addCriterion("REV3 is not null");
            return (Criteria) this;
        }

        public Criteria andRev3EqualTo(String value) {
            addCriterion("REV3 =", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3NotEqualTo(String value) {
            addCriterion("REV3 <>", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3GreaterThan(String value) {
            addCriterion("REV3 >", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3GreaterThanOrEqualTo(String value) {
            addCriterion("REV3 >=", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3LessThan(String value) {
            addCriterion("REV3 <", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3LessThanOrEqualTo(String value) {
            addCriterion("REV3 <=", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3Like(String value) {
            addCriterion("REV3 like", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3NotLike(String value) {
            addCriterion("REV3 not like", value, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3In(List<String> values) {
            addCriterion("REV3 in", values, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3NotIn(List<String> values) {
            addCriterion("REV3 not in", values, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3Between(String value1, String value2) {
            addCriterion("REV3 between", value1, value2, "rev3");
            return (Criteria) this;
        }

        public Criteria andRev3NotBetween(String value1, String value2) {
            addCriterion("REV3 not between", value1, value2, "rev3");
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