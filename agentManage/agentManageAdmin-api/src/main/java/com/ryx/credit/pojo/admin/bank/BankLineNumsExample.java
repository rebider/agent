package com.ryx.credit.pojo.admin.bank;

import com.ryx.credit.common.util.Page;

import java.util.ArrayList;
import java.util.List;

public class BankLineNumsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public BankLineNumsExample() {
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

        public Criteria andBanktypeIsNull() {
            addCriterion("BANKTYPE is null");
            return (Criteria) this;
        }

        public Criteria andBanktypeIsNotNull() {
            addCriterion("BANKTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBanktypeEqualTo(String value) {
            addCriterion("BANKTYPE =", value, "banktype");
            return (Criteria) this;
        }

        public Criteria andBanktypeNotEqualTo(String value) {
            addCriterion("BANKTYPE <>", value, "banktype");
            return (Criteria) this;
        }

        public Criteria andBanktypeGreaterThan(String value) {
            addCriterion("BANKTYPE >", value, "banktype");
            return (Criteria) this;
        }

        public Criteria andBanktypeGreaterThanOrEqualTo(String value) {
            addCriterion("BANKTYPE >=", value, "banktype");
            return (Criteria) this;
        }

        public Criteria andBanktypeLessThan(String value) {
            addCriterion("BANKTYPE <", value, "banktype");
            return (Criteria) this;
        }

        public Criteria andBanktypeLessThanOrEqualTo(String value) {
            addCriterion("BANKTYPE <=", value, "banktype");
            return (Criteria) this;
        }

        public Criteria andBanktypeLike(String value) {
            addCriterion("BANKTYPE like", value, "banktype");
            return (Criteria) this;
        }

        public Criteria andBanktypeNotLike(String value) {
            addCriterion("BANKTYPE not like", value, "banktype");
            return (Criteria) this;
        }

        public Criteria andBanktypeIn(List<String> values) {
            addCriterion("BANKTYPE in", values, "banktype");
            return (Criteria) this;
        }

        public Criteria andBanktypeNotIn(List<String> values) {
            addCriterion("BANKTYPE not in", values, "banktype");
            return (Criteria) this;
        }

        public Criteria andBanktypeBetween(String value1, String value2) {
            addCriterion("BANKTYPE between", value1, value2, "banktype");
            return (Criteria) this;
        }

        public Criteria andBanktypeNotBetween(String value1, String value2) {
            addCriterion("BANKTYPE not between", value1, value2, "banktype");
            return (Criteria) this;
        }

        public Criteria andProvinceidIsNull() {
            addCriterion("PROVINCEID is null");
            return (Criteria) this;
        }

        public Criteria andProvinceidIsNotNull() {
            addCriterion("PROVINCEID is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceidEqualTo(String value) {
            addCriterion("PROVINCEID =", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidNotEqualTo(String value) {
            addCriterion("PROVINCEID <>", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidGreaterThan(String value) {
            addCriterion("PROVINCEID >", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidGreaterThanOrEqualTo(String value) {
            addCriterion("PROVINCEID >=", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidLessThan(String value) {
            addCriterion("PROVINCEID <", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidLessThanOrEqualTo(String value) {
            addCriterion("PROVINCEID <=", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidLike(String value) {
            addCriterion("PROVINCEID like", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidNotLike(String value) {
            addCriterion("PROVINCEID not like", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidIn(List<String> values) {
            addCriterion("PROVINCEID in", values, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidNotIn(List<String> values) {
            addCriterion("PROVINCEID not in", values, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidBetween(String value1, String value2) {
            addCriterion("PROVINCEID between", value1, value2, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidNotBetween(String value1, String value2) {
            addCriterion("PROVINCEID not between", value1, value2, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvincenameIsNull() {
            addCriterion("PROVINCENAME is null");
            return (Criteria) this;
        }

        public Criteria andProvincenameIsNotNull() {
            addCriterion("PROVINCENAME is not null");
            return (Criteria) this;
        }

        public Criteria andProvincenameEqualTo(String value) {
            addCriterion("PROVINCENAME =", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameNotEqualTo(String value) {
            addCriterion("PROVINCENAME <>", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameGreaterThan(String value) {
            addCriterion("PROVINCENAME >", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameGreaterThanOrEqualTo(String value) {
            addCriterion("PROVINCENAME >=", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameLessThan(String value) {
            addCriterion("PROVINCENAME <", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameLessThanOrEqualTo(String value) {
            addCriterion("PROVINCENAME <=", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameLike(String value) {
            addCriterion("PROVINCENAME like", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameNotLike(String value) {
            addCriterion("PROVINCENAME not like", value, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameIn(List<String> values) {
            addCriterion("PROVINCENAME in", values, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameNotIn(List<String> values) {
            addCriterion("PROVINCENAME not in", values, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameBetween(String value1, String value2) {
            addCriterion("PROVINCENAME between", value1, value2, "provincename");
            return (Criteria) this;
        }

        public Criteria andProvincenameNotBetween(String value1, String value2) {
            addCriterion("PROVINCENAME not between", value1, value2, "provincename");
            return (Criteria) this;
        }

        public Criteria andCityidIsNull() {
            addCriterion("CITYID is null");
            return (Criteria) this;
        }

        public Criteria andCityidIsNotNull() {
            addCriterion("CITYID is not null");
            return (Criteria) this;
        }

        public Criteria andCityidEqualTo(String value) {
            addCriterion("CITYID =", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidNotEqualTo(String value) {
            addCriterion("CITYID <>", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidGreaterThan(String value) {
            addCriterion("CITYID >", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidGreaterThanOrEqualTo(String value) {
            addCriterion("CITYID >=", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidLessThan(String value) {
            addCriterion("CITYID <", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidLessThanOrEqualTo(String value) {
            addCriterion("CITYID <=", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidLike(String value) {
            addCriterion("CITYID like", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidNotLike(String value) {
            addCriterion("CITYID not like", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidIn(List<String> values) {
            addCriterion("CITYID in", values, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidNotIn(List<String> values) {
            addCriterion("CITYID not in", values, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidBetween(String value1, String value2) {
            addCriterion("CITYID between", value1, value2, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidNotBetween(String value1, String value2) {
            addCriterion("CITYID not between", value1, value2, "cityid");
            return (Criteria) this;
        }

        public Criteria andCitynameIsNull() {
            addCriterion("CITYNAME is null");
            return (Criteria) this;
        }

        public Criteria andCitynameIsNotNull() {
            addCriterion("CITYNAME is not null");
            return (Criteria) this;
        }

        public Criteria andCitynameEqualTo(String value) {
            addCriterion("CITYNAME =", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameNotEqualTo(String value) {
            addCriterion("CITYNAME <>", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameGreaterThan(String value) {
            addCriterion("CITYNAME >", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameGreaterThanOrEqualTo(String value) {
            addCriterion("CITYNAME >=", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameLessThan(String value) {
            addCriterion("CITYNAME <", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameLessThanOrEqualTo(String value) {
            addCriterion("CITYNAME <=", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameLike(String value) {
            addCriterion("CITYNAME like", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameNotLike(String value) {
            addCriterion("CITYNAME not like", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameIn(List<String> values) {
            addCriterion("CITYNAME in", values, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameNotIn(List<String> values) {
            addCriterion("CITYNAME not in", values, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameBetween(String value1, String value2) {
            addCriterion("CITYNAME between", value1, value2, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameNotBetween(String value1, String value2) {
            addCriterion("CITYNAME not between", value1, value2, "cityname");
            return (Criteria) this;
        }

        public Criteria andBankidIsNull() {
            addCriterion("BANKID is null");
            return (Criteria) this;
        }

        public Criteria andBankidIsNotNull() {
            addCriterion("BANKID is not null");
            return (Criteria) this;
        }

        public Criteria andBankidEqualTo(String value) {
            addCriterion("BANKID =", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidNotEqualTo(String value) {
            addCriterion("BANKID <>", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidGreaterThan(String value) {
            addCriterion("BANKID >", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidGreaterThanOrEqualTo(String value) {
            addCriterion("BANKID >=", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidLessThan(String value) {
            addCriterion("BANKID <", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidLessThanOrEqualTo(String value) {
            addCriterion("BANKID <=", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidLike(String value) {
            addCriterion("BANKID like", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidNotLike(String value) {
            addCriterion("BANKID not like", value, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidIn(List<String> values) {
            addCriterion("BANKID in", values, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidNotIn(List<String> values) {
            addCriterion("BANKID not in", values, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidBetween(String value1, String value2) {
            addCriterion("BANKID between", value1, value2, "bankid");
            return (Criteria) this;
        }

        public Criteria andBankidNotBetween(String value1, String value2) {
            addCriterion("BANKID not between", value1, value2, "bankid");
            return (Criteria) this;
        }

        public Criteria andBanknameIsNull() {
            addCriterion("BANKNAME is null");
            return (Criteria) this;
        }

        public Criteria andBanknameIsNotNull() {
            addCriterion("BANKNAME is not null");
            return (Criteria) this;
        }

        public Criteria andBanknameEqualTo(String value) {
            addCriterion("BANKNAME =", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameNotEqualTo(String value) {
            addCriterion("BANKNAME <>", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameGreaterThan(String value) {
            addCriterion("BANKNAME >", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameGreaterThanOrEqualTo(String value) {
            addCriterion("BANKNAME >=", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameLessThan(String value) {
            addCriterion("BANKNAME <", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameLessThanOrEqualTo(String value) {
            addCriterion("BANKNAME <=", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameLike(String value) {
            addCriterion("BANKNAME like", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameNotLike(String value) {
            addCriterion("BANKNAME not like", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameIn(List<String> values) {
            addCriterion("BANKNAME in", values, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameNotIn(List<String> values) {
            addCriterion("BANKNAME not in", values, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameBetween(String value1, String value2) {
            addCriterion("BANKNAME between", value1, value2, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameNotBetween(String value1, String value2) {
            addCriterion("BANKNAME not between", value1, value2, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameshortIsNull() {
            addCriterion("BANKNAMESHORT is null");
            return (Criteria) this;
        }

        public Criteria andBanknameshortIsNotNull() {
            addCriterion("BANKNAMESHORT is not null");
            return (Criteria) this;
        }

        public Criteria andBanknameshortEqualTo(String value) {
            addCriterion("BANKNAMESHORT =", value, "banknameshort");
            return (Criteria) this;
        }

        public Criteria andBanknameshortNotEqualTo(String value) {
            addCriterion("BANKNAMESHORT <>", value, "banknameshort");
            return (Criteria) this;
        }

        public Criteria andBanknameshortGreaterThan(String value) {
            addCriterion("BANKNAMESHORT >", value, "banknameshort");
            return (Criteria) this;
        }

        public Criteria andBanknameshortGreaterThanOrEqualTo(String value) {
            addCriterion("BANKNAMESHORT >=", value, "banknameshort");
            return (Criteria) this;
        }

        public Criteria andBanknameshortLessThan(String value) {
            addCriterion("BANKNAMESHORT <", value, "banknameshort");
            return (Criteria) this;
        }

        public Criteria andBanknameshortLessThanOrEqualTo(String value) {
            addCriterion("BANKNAMESHORT <=", value, "banknameshort");
            return (Criteria) this;
        }

        public Criteria andBanknameshortLike(String value) {
            addCriterion("BANKNAMESHORT like", value, "banknameshort");
            return (Criteria) this;
        }

        public Criteria andBanknameshortNotLike(String value) {
            addCriterion("BANKNAMESHORT not like", value, "banknameshort");
            return (Criteria) this;
        }

        public Criteria andBanknameshortIn(List<String> values) {
            addCriterion("BANKNAMESHORT in", values, "banknameshort");
            return (Criteria) this;
        }

        public Criteria andBanknameshortNotIn(List<String> values) {
            addCriterion("BANKNAMESHORT not in", values, "banknameshort");
            return (Criteria) this;
        }

        public Criteria andBanknameshortBetween(String value1, String value2) {
            addCriterion("BANKNAMESHORT between", value1, value2, "banknameshort");
            return (Criteria) this;
        }

        public Criteria andBanknameshortNotBetween(String value1, String value2) {
            addCriterion("BANKNAMESHORT not between", value1, value2, "banknameshort");
            return (Criteria) this;
        }

        public Criteria andBankbranchidIsNull() {
            addCriterion("BANKBRANCHID is null");
            return (Criteria) this;
        }

        public Criteria andBankbranchidIsNotNull() {
            addCriterion("BANKBRANCHID is not null");
            return (Criteria) this;
        }

        public Criteria andBankbranchidEqualTo(String value) {
            addCriterion("BANKBRANCHID =", value, "bankbranchid");
            return (Criteria) this;
        }

        public Criteria andBankbranchidNotEqualTo(String value) {
            addCriterion("BANKBRANCHID <>", value, "bankbranchid");
            return (Criteria) this;
        }

        public Criteria andBankbranchidGreaterThan(String value) {
            addCriterion("BANKBRANCHID >", value, "bankbranchid");
            return (Criteria) this;
        }

        public Criteria andBankbranchidGreaterThanOrEqualTo(String value) {
            addCriterion("BANKBRANCHID >=", value, "bankbranchid");
            return (Criteria) this;
        }

        public Criteria andBankbranchidLessThan(String value) {
            addCriterion("BANKBRANCHID <", value, "bankbranchid");
            return (Criteria) this;
        }

        public Criteria andBankbranchidLessThanOrEqualTo(String value) {
            addCriterion("BANKBRANCHID <=", value, "bankbranchid");
            return (Criteria) this;
        }

        public Criteria andBankbranchidLike(String value) {
            addCriterion("BANKBRANCHID like", value, "bankbranchid");
            return (Criteria) this;
        }

        public Criteria andBankbranchidNotLike(String value) {
            addCriterion("BANKBRANCHID not like", value, "bankbranchid");
            return (Criteria) this;
        }

        public Criteria andBankbranchidIn(List<String> values) {
            addCriterion("BANKBRANCHID in", values, "bankbranchid");
            return (Criteria) this;
        }

        public Criteria andBankbranchidNotIn(List<String> values) {
            addCriterion("BANKBRANCHID not in", values, "bankbranchid");
            return (Criteria) this;
        }

        public Criteria andBankbranchidBetween(String value1, String value2) {
            addCriterion("BANKBRANCHID between", value1, value2, "bankbranchid");
            return (Criteria) this;
        }

        public Criteria andBankbranchidNotBetween(String value1, String value2) {
            addCriterion("BANKBRANCHID not between", value1, value2, "bankbranchid");
            return (Criteria) this;
        }

        public Criteria andBankbranchnameIsNull() {
            addCriterion("BANKBRANCHNAME is null");
            return (Criteria) this;
        }

        public Criteria andBankbranchnameIsNotNull() {
            addCriterion("BANKBRANCHNAME is not null");
            return (Criteria) this;
        }

        public Criteria andBankbranchnameEqualTo(String value) {
            addCriterion("BANKBRANCHNAME =", value, "bankbranchname");
            return (Criteria) this;
        }

        public Criteria andBankbranchnameNotEqualTo(String value) {
            addCriterion("BANKBRANCHNAME <>", value, "bankbranchname");
            return (Criteria) this;
        }

        public Criteria andBankbranchnameGreaterThan(String value) {
            addCriterion("BANKBRANCHNAME >", value, "bankbranchname");
            return (Criteria) this;
        }

        public Criteria andBankbranchnameGreaterThanOrEqualTo(String value) {
            addCriterion("BANKBRANCHNAME >=", value, "bankbranchname");
            return (Criteria) this;
        }

        public Criteria andBankbranchnameLessThan(String value) {
            addCriterion("BANKBRANCHNAME <", value, "bankbranchname");
            return (Criteria) this;
        }

        public Criteria andBankbranchnameLessThanOrEqualTo(String value) {
            addCriterion("BANKBRANCHNAME <=", value, "bankbranchname");
            return (Criteria) this;
        }

        public Criteria andBankbranchnameLike(String value) {
            addCriterion("BANKBRANCHNAME like", value, "bankbranchname");
            return (Criteria) this;
        }

        public Criteria andBankbranchnameNotLike(String value) {
            addCriterion("BANKBRANCHNAME not like", value, "bankbranchname");
            return (Criteria) this;
        }

        public Criteria andBankbranchnameIn(List<String> values) {
            addCriterion("BANKBRANCHNAME in", values, "bankbranchname");
            return (Criteria) this;
        }

        public Criteria andBankbranchnameNotIn(List<String> values) {
            addCriterion("BANKBRANCHNAME not in", values, "bankbranchname");
            return (Criteria) this;
        }

        public Criteria andBankbranchnameBetween(String value1, String value2) {
            addCriterion("BANKBRANCHNAME between", value1, value2, "bankbranchname");
            return (Criteria) this;
        }

        public Criteria andBankbranchnameNotBetween(String value1, String value2) {
            addCriterion("BANKBRANCHNAME not between", value1, value2, "bankbranchname");
            return (Criteria) this;
        }

        public Criteria andBankaddressIsNull() {
            addCriterion("BANKADDRESS is null");
            return (Criteria) this;
        }

        public Criteria andBankaddressIsNotNull() {
            addCriterion("BANKADDRESS is not null");
            return (Criteria) this;
        }

        public Criteria andBankaddressEqualTo(String value) {
            addCriterion("BANKADDRESS =", value, "bankaddress");
            return (Criteria) this;
        }

        public Criteria andBankaddressNotEqualTo(String value) {
            addCriterion("BANKADDRESS <>", value, "bankaddress");
            return (Criteria) this;
        }

        public Criteria andBankaddressGreaterThan(String value) {
            addCriterion("BANKADDRESS >", value, "bankaddress");
            return (Criteria) this;
        }

        public Criteria andBankaddressGreaterThanOrEqualTo(String value) {
            addCriterion("BANKADDRESS >=", value, "bankaddress");
            return (Criteria) this;
        }

        public Criteria andBankaddressLessThan(String value) {
            addCriterion("BANKADDRESS <", value, "bankaddress");
            return (Criteria) this;
        }

        public Criteria andBankaddressLessThanOrEqualTo(String value) {
            addCriterion("BANKADDRESS <=", value, "bankaddress");
            return (Criteria) this;
        }

        public Criteria andBankaddressLike(String value) {
            addCriterion("BANKADDRESS like", value, "bankaddress");
            return (Criteria) this;
        }

        public Criteria andBankaddressNotLike(String value) {
            addCriterion("BANKADDRESS not like", value, "bankaddress");
            return (Criteria) this;
        }

        public Criteria andBankaddressIn(List<String> values) {
            addCriterion("BANKADDRESS in", values, "bankaddress");
            return (Criteria) this;
        }

        public Criteria andBankaddressNotIn(List<String> values) {
            addCriterion("BANKADDRESS not in", values, "bankaddress");
            return (Criteria) this;
        }

        public Criteria andBankaddressBetween(String value1, String value2) {
            addCriterion("BANKADDRESS between", value1, value2, "bankaddress");
            return (Criteria) this;
        }

        public Criteria andBankaddressNotBetween(String value1, String value2) {
            addCriterion("BANKADDRESS not between", value1, value2, "bankaddress");
            return (Criteria) this;
        }

        public Criteria andLiqbankidIsNull() {
            addCriterion("LIQBANKID is null");
            return (Criteria) this;
        }

        public Criteria andLiqbankidIsNotNull() {
            addCriterion("LIQBANKID is not null");
            return (Criteria) this;
        }

        public Criteria andLiqbankidEqualTo(String value) {
            addCriterion("LIQBANKID =", value, "liqbankid");
            return (Criteria) this;
        }

        public Criteria andLiqbankidNotEqualTo(String value) {
            addCriterion("LIQBANKID <>", value, "liqbankid");
            return (Criteria) this;
        }

        public Criteria andLiqbankidGreaterThan(String value) {
            addCriterion("LIQBANKID >", value, "liqbankid");
            return (Criteria) this;
        }

        public Criteria andLiqbankidGreaterThanOrEqualTo(String value) {
            addCriterion("LIQBANKID >=", value, "liqbankid");
            return (Criteria) this;
        }

        public Criteria andLiqbankidLessThan(String value) {
            addCriterion("LIQBANKID <", value, "liqbankid");
            return (Criteria) this;
        }

        public Criteria andLiqbankidLessThanOrEqualTo(String value) {
            addCriterion("LIQBANKID <=", value, "liqbankid");
            return (Criteria) this;
        }

        public Criteria andLiqbankidLike(String value) {
            addCriterion("LIQBANKID like", value, "liqbankid");
            return (Criteria) this;
        }

        public Criteria andLiqbankidNotLike(String value) {
            addCriterion("LIQBANKID not like", value, "liqbankid");
            return (Criteria) this;
        }

        public Criteria andLiqbankidIn(List<String> values) {
            addCriterion("LIQBANKID in", values, "liqbankid");
            return (Criteria) this;
        }

        public Criteria andLiqbankidNotIn(List<String> values) {
            addCriterion("LIQBANKID not in", values, "liqbankid");
            return (Criteria) this;
        }

        public Criteria andLiqbankidBetween(String value1, String value2) {
            addCriterion("LIQBANKID between", value1, value2, "liqbankid");
            return (Criteria) this;
        }

        public Criteria andLiqbankidNotBetween(String value1, String value2) {
            addCriterion("LIQBANKID not between", value1, value2, "liqbankid");
            return (Criteria) this;
        }

        public Criteria andLiqbankcityidIsNull() {
            addCriterion("LIQBANKCITYID is null");
            return (Criteria) this;
        }

        public Criteria andLiqbankcityidIsNotNull() {
            addCriterion("LIQBANKCITYID is not null");
            return (Criteria) this;
        }

        public Criteria andLiqbankcityidEqualTo(String value) {
            addCriterion("LIQBANKCITYID =", value, "liqbankcityid");
            return (Criteria) this;
        }

        public Criteria andLiqbankcityidNotEqualTo(String value) {
            addCriterion("LIQBANKCITYID <>", value, "liqbankcityid");
            return (Criteria) this;
        }

        public Criteria andLiqbankcityidGreaterThan(String value) {
            addCriterion("LIQBANKCITYID >", value, "liqbankcityid");
            return (Criteria) this;
        }

        public Criteria andLiqbankcityidGreaterThanOrEqualTo(String value) {
            addCriterion("LIQBANKCITYID >=", value, "liqbankcityid");
            return (Criteria) this;
        }

        public Criteria andLiqbankcityidLessThan(String value) {
            addCriterion("LIQBANKCITYID <", value, "liqbankcityid");
            return (Criteria) this;
        }

        public Criteria andLiqbankcityidLessThanOrEqualTo(String value) {
            addCriterion("LIQBANKCITYID <=", value, "liqbankcityid");
            return (Criteria) this;
        }

        public Criteria andLiqbankcityidLike(String value) {
            addCriterion("LIQBANKCITYID like", value, "liqbankcityid");
            return (Criteria) this;
        }

        public Criteria andLiqbankcityidNotLike(String value) {
            addCriterion("LIQBANKCITYID not like", value, "liqbankcityid");
            return (Criteria) this;
        }

        public Criteria andLiqbankcityidIn(List<String> values) {
            addCriterion("LIQBANKCITYID in", values, "liqbankcityid");
            return (Criteria) this;
        }

        public Criteria andLiqbankcityidNotIn(List<String> values) {
            addCriterion("LIQBANKCITYID not in", values, "liqbankcityid");
            return (Criteria) this;
        }

        public Criteria andLiqbankcityidBetween(String value1, String value2) {
            addCriterion("LIQBANKCITYID between", value1, value2, "liqbankcityid");
            return (Criteria) this;
        }

        public Criteria andLiqbankcityidNotBetween(String value1, String value2) {
            addCriterion("LIQBANKCITYID not between", value1, value2, "liqbankcityid");
            return (Criteria) this;
        }

        public Criteria andFlagIsNull() {
            addCriterion("FLAG is null");
            return (Criteria) this;
        }

        public Criteria andFlagIsNotNull() {
            addCriterion("FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andFlagEqualTo(String value) {
            addCriterion("FLAG =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(String value) {
            addCriterion("FLAG <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(String value) {
            addCriterion("FLAG >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(String value) {
            addCriterion("FLAG >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(String value) {
            addCriterion("FLAG <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(String value) {
            addCriterion("FLAG <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLike(String value) {
            addCriterion("FLAG like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotLike(String value) {
            addCriterion("FLAG not like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<String> values) {
            addCriterion("FLAG in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<String> values) {
            addCriterion("FLAG not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(String value1, String value2) {
            addCriterion("FLAG between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(String value1, String value2) {
            addCriterion("FLAG not between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andBankcityidIsNull() {
            addCriterion("BANKCITYID is null");
            return (Criteria) this;
        }

        public Criteria andBankcityidIsNotNull() {
            addCriterion("BANKCITYID is not null");
            return (Criteria) this;
        }

        public Criteria andBankcityidEqualTo(String value) {
            addCriterion("BANKCITYID =", value, "bankcityid");
            return (Criteria) this;
        }

        public Criteria andBankcityidNotEqualTo(String value) {
            addCriterion("BANKCITYID <>", value, "bankcityid");
            return (Criteria) this;
        }

        public Criteria andBankcityidGreaterThan(String value) {
            addCriterion("BANKCITYID >", value, "bankcityid");
            return (Criteria) this;
        }

        public Criteria andBankcityidGreaterThanOrEqualTo(String value) {
            addCriterion("BANKCITYID >=", value, "bankcityid");
            return (Criteria) this;
        }

        public Criteria andBankcityidLessThan(String value) {
            addCriterion("BANKCITYID <", value, "bankcityid");
            return (Criteria) this;
        }

        public Criteria andBankcityidLessThanOrEqualTo(String value) {
            addCriterion("BANKCITYID <=", value, "bankcityid");
            return (Criteria) this;
        }

        public Criteria andBankcityidLike(String value) {
            addCriterion("BANKCITYID like", value, "bankcityid");
            return (Criteria) this;
        }

        public Criteria andBankcityidNotLike(String value) {
            addCriterion("BANKCITYID not like", value, "bankcityid");
            return (Criteria) this;
        }

        public Criteria andBankcityidIn(List<String> values) {
            addCriterion("BANKCITYID in", values, "bankcityid");
            return (Criteria) this;
        }

        public Criteria andBankcityidNotIn(List<String> values) {
            addCriterion("BANKCITYID not in", values, "bankcityid");
            return (Criteria) this;
        }

        public Criteria andBankcityidBetween(String value1, String value2) {
            addCriterion("BANKCITYID between", value1, value2, "bankcityid");
            return (Criteria) this;
        }

        public Criteria andBankcityidNotBetween(String value1, String value2) {
            addCriterion("BANKCITYID not between", value1, value2, "bankcityid");
            return (Criteria) this;
        }

        public Criteria andBankpostcodeIsNull() {
            addCriterion("BANKPOSTCODE is null");
            return (Criteria) this;
        }

        public Criteria andBankpostcodeIsNotNull() {
            addCriterion("BANKPOSTCODE is not null");
            return (Criteria) this;
        }

        public Criteria andBankpostcodeEqualTo(String value) {
            addCriterion("BANKPOSTCODE =", value, "bankpostcode");
            return (Criteria) this;
        }

        public Criteria andBankpostcodeNotEqualTo(String value) {
            addCriterion("BANKPOSTCODE <>", value, "bankpostcode");
            return (Criteria) this;
        }

        public Criteria andBankpostcodeGreaterThan(String value) {
            addCriterion("BANKPOSTCODE >", value, "bankpostcode");
            return (Criteria) this;
        }

        public Criteria andBankpostcodeGreaterThanOrEqualTo(String value) {
            addCriterion("BANKPOSTCODE >=", value, "bankpostcode");
            return (Criteria) this;
        }

        public Criteria andBankpostcodeLessThan(String value) {
            addCriterion("BANKPOSTCODE <", value, "bankpostcode");
            return (Criteria) this;
        }

        public Criteria andBankpostcodeLessThanOrEqualTo(String value) {
            addCriterion("BANKPOSTCODE <=", value, "bankpostcode");
            return (Criteria) this;
        }

        public Criteria andBankpostcodeLike(String value) {
            addCriterion("BANKPOSTCODE like", value, "bankpostcode");
            return (Criteria) this;
        }

        public Criteria andBankpostcodeNotLike(String value) {
            addCriterion("BANKPOSTCODE not like", value, "bankpostcode");
            return (Criteria) this;
        }

        public Criteria andBankpostcodeIn(List<String> values) {
            addCriterion("BANKPOSTCODE in", values, "bankpostcode");
            return (Criteria) this;
        }

        public Criteria andBankpostcodeNotIn(List<String> values) {
            addCriterion("BANKPOSTCODE not in", values, "bankpostcode");
            return (Criteria) this;
        }

        public Criteria andBankpostcodeBetween(String value1, String value2) {
            addCriterion("BANKPOSTCODE between", value1, value2, "bankpostcode");
            return (Criteria) this;
        }

        public Criteria andBankpostcodeNotBetween(String value1, String value2) {
            addCriterion("BANKPOSTCODE not between", value1, value2, "bankpostcode");
            return (Criteria) this;
        }

        public Criteria andTelnumIsNull() {
            addCriterion("TELNUM is null");
            return (Criteria) this;
        }

        public Criteria andTelnumIsNotNull() {
            addCriterion("TELNUM is not null");
            return (Criteria) this;
        }

        public Criteria andTelnumEqualTo(String value) {
            addCriterion("TELNUM =", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumNotEqualTo(String value) {
            addCriterion("TELNUM <>", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumGreaterThan(String value) {
            addCriterion("TELNUM >", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumGreaterThanOrEqualTo(String value) {
            addCriterion("TELNUM >=", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumLessThan(String value) {
            addCriterion("TELNUM <", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumLessThanOrEqualTo(String value) {
            addCriterion("TELNUM <=", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumLike(String value) {
            addCriterion("TELNUM like", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumNotLike(String value) {
            addCriterion("TELNUM not like", value, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumIn(List<String> values) {
            addCriterion("TELNUM in", values, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumNotIn(List<String> values) {
            addCriterion("TELNUM not in", values, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumBetween(String value1, String value2) {
            addCriterion("TELNUM between", value1, value2, "telnum");
            return (Criteria) this;
        }

        public Criteria andTelnumNotBetween(String value1, String value2) {
            addCriterion("TELNUM not between", value1, value2, "telnum");
            return (Criteria) this;
        }

        public Criteria andSetupdateIsNull() {
            addCriterion("SETUPDATE is null");
            return (Criteria) this;
        }

        public Criteria andSetupdateIsNotNull() {
            addCriterion("SETUPDATE is not null");
            return (Criteria) this;
        }

        public Criteria andSetupdateEqualTo(String value) {
            addCriterion("SETUPDATE =", value, "setupdate");
            return (Criteria) this;
        }

        public Criteria andSetupdateNotEqualTo(String value) {
            addCriterion("SETUPDATE <>", value, "setupdate");
            return (Criteria) this;
        }

        public Criteria andSetupdateGreaterThan(String value) {
            addCriterion("SETUPDATE >", value, "setupdate");
            return (Criteria) this;
        }

        public Criteria andSetupdateGreaterThanOrEqualTo(String value) {
            addCriterion("SETUPDATE >=", value, "setupdate");
            return (Criteria) this;
        }

        public Criteria andSetupdateLessThan(String value) {
            addCriterion("SETUPDATE <", value, "setupdate");
            return (Criteria) this;
        }

        public Criteria andSetupdateLessThanOrEqualTo(String value) {
            addCriterion("SETUPDATE <=", value, "setupdate");
            return (Criteria) this;
        }

        public Criteria andSetupdateLike(String value) {
            addCriterion("SETUPDATE like", value, "setupdate");
            return (Criteria) this;
        }

        public Criteria andSetupdateNotLike(String value) {
            addCriterion("SETUPDATE not like", value, "setupdate");
            return (Criteria) this;
        }

        public Criteria andSetupdateIn(List<String> values) {
            addCriterion("SETUPDATE in", values, "setupdate");
            return (Criteria) this;
        }

        public Criteria andSetupdateNotIn(List<String> values) {
            addCriterion("SETUPDATE not in", values, "setupdate");
            return (Criteria) this;
        }

        public Criteria andSetupdateBetween(String value1, String value2) {
            addCriterion("SETUPDATE between", value1, value2, "setupdate");
            return (Criteria) this;
        }

        public Criteria andSetupdateNotBetween(String value1, String value2) {
            addCriterion("SETUPDATE not between", value1, value2, "setupdate");
            return (Criteria) this;
        }

        public Criteria andUpbankidIsNull() {
            addCriterion("UPBANKID is null");
            return (Criteria) this;
        }

        public Criteria andUpbankidIsNotNull() {
            addCriterion("UPBANKID is not null");
            return (Criteria) this;
        }

        public Criteria andUpbankidEqualTo(String value) {
            addCriterion("UPBANKID =", value, "upbankid");
            return (Criteria) this;
        }

        public Criteria andUpbankidNotEqualTo(String value) {
            addCriterion("UPBANKID <>", value, "upbankid");
            return (Criteria) this;
        }

        public Criteria andUpbankidGreaterThan(String value) {
            addCriterion("UPBANKID >", value, "upbankid");
            return (Criteria) this;
        }

        public Criteria andUpbankidGreaterThanOrEqualTo(String value) {
            addCriterion("UPBANKID >=", value, "upbankid");
            return (Criteria) this;
        }

        public Criteria andUpbankidLessThan(String value) {
            addCriterion("UPBANKID <", value, "upbankid");
            return (Criteria) this;
        }

        public Criteria andUpbankidLessThanOrEqualTo(String value) {
            addCriterion("UPBANKID <=", value, "upbankid");
            return (Criteria) this;
        }

        public Criteria andUpbankidLike(String value) {
            addCriterion("UPBANKID like", value, "upbankid");
            return (Criteria) this;
        }

        public Criteria andUpbankidNotLike(String value) {
            addCriterion("UPBANKID not like", value, "upbankid");
            return (Criteria) this;
        }

        public Criteria andUpbankidIn(List<String> values) {
            addCriterion("UPBANKID in", values, "upbankid");
            return (Criteria) this;
        }

        public Criteria andUpbankidNotIn(List<String> values) {
            addCriterion("UPBANKID not in", values, "upbankid");
            return (Criteria) this;
        }

        public Criteria andUpbankidBetween(String value1, String value2) {
            addCriterion("UPBANKID between", value1, value2, "upbankid");
            return (Criteria) this;
        }

        public Criteria andUpbankidNotBetween(String value1, String value2) {
            addCriterion("UPBANKID not between", value1, value2, "upbankid");
            return (Criteria) this;
        }

        public Criteria andHeadbankidIsNull() {
            addCriterion("HEADBANKID is null");
            return (Criteria) this;
        }

        public Criteria andHeadbankidIsNotNull() {
            addCriterion("HEADBANKID is not null");
            return (Criteria) this;
        }

        public Criteria andHeadbankidEqualTo(String value) {
            addCriterion("HEADBANKID =", value, "headbankid");
            return (Criteria) this;
        }

        public Criteria andHeadbankidNotEqualTo(String value) {
            addCriterion("HEADBANKID <>", value, "headbankid");
            return (Criteria) this;
        }

        public Criteria andHeadbankidGreaterThan(String value) {
            addCriterion("HEADBANKID >", value, "headbankid");
            return (Criteria) this;
        }

        public Criteria andHeadbankidGreaterThanOrEqualTo(String value) {
            addCriterion("HEADBANKID >=", value, "headbankid");
            return (Criteria) this;
        }

        public Criteria andHeadbankidLessThan(String value) {
            addCriterion("HEADBANKID <", value, "headbankid");
            return (Criteria) this;
        }

        public Criteria andHeadbankidLessThanOrEqualTo(String value) {
            addCriterion("HEADBANKID <=", value, "headbankid");
            return (Criteria) this;
        }

        public Criteria andHeadbankidLike(String value) {
            addCriterion("HEADBANKID like", value, "headbankid");
            return (Criteria) this;
        }

        public Criteria andHeadbankidNotLike(String value) {
            addCriterion("HEADBANKID not like", value, "headbankid");
            return (Criteria) this;
        }

        public Criteria andHeadbankidIn(List<String> values) {
            addCriterion("HEADBANKID in", values, "headbankid");
            return (Criteria) this;
        }

        public Criteria andHeadbankidNotIn(List<String> values) {
            addCriterion("HEADBANKID not in", values, "headbankid");
            return (Criteria) this;
        }

        public Criteria andHeadbankidBetween(String value1, String value2) {
            addCriterion("HEADBANKID between", value1, value2, "headbankid");
            return (Criteria) this;
        }

        public Criteria andHeadbankidNotBetween(String value1, String value2) {
            addCriterion("HEADBANKID not between", value1, value2, "headbankid");
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