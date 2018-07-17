package com.ryx.credit.pojo.admin.bank;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EtbSysCardBinNoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public EtbSysCardBinNoExample() {
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

        public Criteria andBankIdIsNull() {
            addCriterion("BANK_ID is null");
            return (Criteria) this;
        }

        public Criteria andBankIdIsNotNull() {
            addCriterion("BANK_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBankIdEqualTo(String value) {
            addCriterion("BANK_ID =", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotEqualTo(String value) {
            addCriterion("BANK_ID <>", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThan(String value) {
            addCriterion("BANK_ID >", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_ID >=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThan(String value) {
            addCriterion("BANK_ID <", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThanOrEqualTo(String value) {
            addCriterion("BANK_ID <=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLike(String value) {
            addCriterion("BANK_ID like", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotLike(String value) {
            addCriterion("BANK_ID not like", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdIn(List<String> values) {
            addCriterion("BANK_ID in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotIn(List<String> values) {
            addCriterion("BANK_ID not in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdBetween(String value1, String value2) {
            addCriterion("BANK_ID between", value1, value2, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotBetween(String value1, String value2) {
            addCriterion("BANK_ID not between", value1, value2, "bankId");
            return (Criteria) this;
        }

        public Criteria andIssUsersIsNull() {
            addCriterion("ISS_USERS is null");
            return (Criteria) this;
        }

        public Criteria andIssUsersIsNotNull() {
            addCriterion("ISS_USERS is not null");
            return (Criteria) this;
        }

        public Criteria andIssUsersEqualTo(String value) {
            addCriterion("ISS_USERS =", value, "issUsers");
            return (Criteria) this;
        }

        public Criteria andIssUsersNotEqualTo(String value) {
            addCriterion("ISS_USERS <>", value, "issUsers");
            return (Criteria) this;
        }

        public Criteria andIssUsersGreaterThan(String value) {
            addCriterion("ISS_USERS >", value, "issUsers");
            return (Criteria) this;
        }

        public Criteria andIssUsersGreaterThanOrEqualTo(String value) {
            addCriterion("ISS_USERS >=", value, "issUsers");
            return (Criteria) this;
        }

        public Criteria andIssUsersLessThan(String value) {
            addCriterion("ISS_USERS <", value, "issUsers");
            return (Criteria) this;
        }

        public Criteria andIssUsersLessThanOrEqualTo(String value) {
            addCriterion("ISS_USERS <=", value, "issUsers");
            return (Criteria) this;
        }

        public Criteria andIssUsersLike(String value) {
            addCriterion("ISS_USERS like", value, "issUsers");
            return (Criteria) this;
        }

        public Criteria andIssUsersNotLike(String value) {
            addCriterion("ISS_USERS not like", value, "issUsers");
            return (Criteria) this;
        }

        public Criteria andIssUsersIn(List<String> values) {
            addCriterion("ISS_USERS in", values, "issUsers");
            return (Criteria) this;
        }

        public Criteria andIssUsersNotIn(List<String> values) {
            addCriterion("ISS_USERS not in", values, "issUsers");
            return (Criteria) this;
        }

        public Criteria andIssUsersBetween(String value1, String value2) {
            addCriterion("ISS_USERS between", value1, value2, "issUsers");
            return (Criteria) this;
        }

        public Criteria andIssUsersNotBetween(String value1, String value2) {
            addCriterion("ISS_USERS not between", value1, value2, "issUsers");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNull() {
            addCriterion("CARD_NO is null");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNotNull() {
            addCriterion("CARD_NO is not null");
            return (Criteria) this;
        }

        public Criteria andCardNoEqualTo(String value) {
            addCriterion("CARD_NO =", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotEqualTo(String value) {
            addCriterion("CARD_NO <>", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThan(String value) {
            addCriterion("CARD_NO >", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThanOrEqualTo(String value) {
            addCriterion("CARD_NO >=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThan(String value) {
            addCriterion("CARD_NO <", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThanOrEqualTo(String value) {
            addCriterion("CARD_NO <=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLike(String value) {
            addCriterion("CARD_NO like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotLike(String value) {
            addCriterion("CARD_NO not like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoIn(List<String> values) {
            addCriterion("CARD_NO in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotIn(List<String> values) {
            addCriterion("CARD_NO not in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoBetween(String value1, String value2) {
            addCriterion("CARD_NO between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotBetween(String value1, String value2) {
            addCriterion("CARD_NO not between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardLenIsNull() {
            addCriterion("CARD_LEN is null");
            return (Criteria) this;
        }

        public Criteria andCardLenIsNotNull() {
            addCriterion("CARD_LEN is not null");
            return (Criteria) this;
        }

        public Criteria andCardLenEqualTo(BigDecimal value) {
            addCriterion("CARD_LEN =", value, "cardLen");
            return (Criteria) this;
        }

        public Criteria andCardLenNotEqualTo(BigDecimal value) {
            addCriterion("CARD_LEN <>", value, "cardLen");
            return (Criteria) this;
        }

        public Criteria andCardLenGreaterThan(BigDecimal value) {
            addCriterion("CARD_LEN >", value, "cardLen");
            return (Criteria) this;
        }

        public Criteria andCardLenGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CARD_LEN >=", value, "cardLen");
            return (Criteria) this;
        }

        public Criteria andCardLenLessThan(BigDecimal value) {
            addCriterion("CARD_LEN <", value, "cardLen");
            return (Criteria) this;
        }

        public Criteria andCardLenLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CARD_LEN <=", value, "cardLen");
            return (Criteria) this;
        }

        public Criteria andCardLenIn(List<BigDecimal> values) {
            addCriterion("CARD_LEN in", values, "cardLen");
            return (Criteria) this;
        }

        public Criteria andCardLenNotIn(List<BigDecimal> values) {
            addCriterion("CARD_LEN not in", values, "cardLen");
            return (Criteria) this;
        }

        public Criteria andCardLenBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CARD_LEN between", value1, value2, "cardLen");
            return (Criteria) this;
        }

        public Criteria andCardLenNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CARD_LEN not between", value1, value2, "cardLen");
            return (Criteria) this;
        }

        public Criteria andCardBinIsNull() {
            addCriterion("CARD_BIN is null");
            return (Criteria) this;
        }

        public Criteria andCardBinIsNotNull() {
            addCriterion("CARD_BIN is not null");
            return (Criteria) this;
        }

        public Criteria andCardBinEqualTo(String value) {
            addCriterion("CARD_BIN =", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinNotEqualTo(String value) {
            addCriterion("CARD_BIN <>", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinGreaterThan(String value) {
            addCriterion("CARD_BIN >", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinGreaterThanOrEqualTo(String value) {
            addCriterion("CARD_BIN >=", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinLessThan(String value) {
            addCriterion("CARD_BIN <", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinLessThanOrEqualTo(String value) {
            addCriterion("CARD_BIN <=", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinLike(String value) {
            addCriterion("CARD_BIN like", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinNotLike(String value) {
            addCriterion("CARD_BIN not like", value, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinIn(List<String> values) {
            addCriterion("CARD_BIN in", values, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinNotIn(List<String> values) {
            addCriterion("CARD_BIN not in", values, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinBetween(String value1, String value2) {
            addCriterion("CARD_BIN between", value1, value2, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardBinNotBetween(String value1, String value2) {
            addCriterion("CARD_BIN not between", value1, value2, "cardBin");
            return (Criteria) this;
        }

        public Criteria andCardNameIsNull() {
            addCriterion("CARD_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCardNameIsNotNull() {
            addCriterion("CARD_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCardNameEqualTo(String value) {
            addCriterion("CARD_NAME =", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameNotEqualTo(String value) {
            addCriterion("CARD_NAME <>", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameGreaterThan(String value) {
            addCriterion("CARD_NAME >", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameGreaterThanOrEqualTo(String value) {
            addCriterion("CARD_NAME >=", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameLessThan(String value) {
            addCriterion("CARD_NAME <", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameLessThanOrEqualTo(String value) {
            addCriterion("CARD_NAME <=", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameLike(String value) {
            addCriterion("CARD_NAME like", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameNotLike(String value) {
            addCriterion("CARD_NAME not like", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameIn(List<String> values) {
            addCriterion("CARD_NAME in", values, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameNotIn(List<String> values) {
            addCriterion("CARD_NAME not in", values, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameBetween(String value1, String value2) {
            addCriterion("CARD_NAME between", value1, value2, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameNotBetween(String value1, String value2) {
            addCriterion("CARD_NAME not between", value1, value2, "cardName");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNull() {
            addCriterion("BANK_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNotNull() {
            addCriterion("BANK_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBankNameEqualTo(String value) {
            addCriterion("BANK_NAME =", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotEqualTo(String value) {
            addCriterion("BANK_NAME <>", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThan(String value) {
            addCriterion("BANK_NAME >", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_NAME >=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThan(String value) {
            addCriterion("BANK_NAME <", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThanOrEqualTo(String value) {
            addCriterion("BANK_NAME <=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLike(String value) {
            addCriterion("BANK_NAME like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotLike(String value) {
            addCriterion("BANK_NAME not like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameIn(List<String> values) {
            addCriterion("BANK_NAME in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotIn(List<String> values) {
            addCriterion("BANK_NAME not in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameBetween(String value1, String value2) {
            addCriterion("BANK_NAME between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotBetween(String value1, String value2) {
            addCriterion("BANK_NAME not between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNull() {
            addCriterion("BRANCH_ID is null");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNotNull() {
            addCriterion("BRANCH_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBranchIdEqualTo(String value) {
            addCriterion("BRANCH_ID =", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotEqualTo(String value) {
            addCriterion("BRANCH_ID <>", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThan(String value) {
            addCriterion("BRANCH_ID >", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThanOrEqualTo(String value) {
            addCriterion("BRANCH_ID >=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThan(String value) {
            addCriterion("BRANCH_ID <", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThanOrEqualTo(String value) {
            addCriterion("BRANCH_ID <=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLike(String value) {
            addCriterion("BRANCH_ID like", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotLike(String value) {
            addCriterion("BRANCH_ID not like", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdIn(List<String> values) {
            addCriterion("BRANCH_ID in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotIn(List<String> values) {
            addCriterion("BRANCH_ID not in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdBetween(String value1, String value2) {
            addCriterion("BRANCH_ID between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotBetween(String value1, String value2) {
            addCriterion("BRANCH_ID not between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchId2IsNull() {
            addCriterion("BRANCH_ID2 is null");
            return (Criteria) this;
        }

        public Criteria andBranchId2IsNotNull() {
            addCriterion("BRANCH_ID2 is not null");
            return (Criteria) this;
        }

        public Criteria andBranchId2EqualTo(String value) {
            addCriterion("BRANCH_ID2 =", value, "branchId2");
            return (Criteria) this;
        }

        public Criteria andBranchId2NotEqualTo(String value) {
            addCriterion("BRANCH_ID2 <>", value, "branchId2");
            return (Criteria) this;
        }

        public Criteria andBranchId2GreaterThan(String value) {
            addCriterion("BRANCH_ID2 >", value, "branchId2");
            return (Criteria) this;
        }

        public Criteria andBranchId2GreaterThanOrEqualTo(String value) {
            addCriterion("BRANCH_ID2 >=", value, "branchId2");
            return (Criteria) this;
        }

        public Criteria andBranchId2LessThan(String value) {
            addCriterion("BRANCH_ID2 <", value, "branchId2");
            return (Criteria) this;
        }

        public Criteria andBranchId2LessThanOrEqualTo(String value) {
            addCriterion("BRANCH_ID2 <=", value, "branchId2");
            return (Criteria) this;
        }

        public Criteria andBranchId2Like(String value) {
            addCriterion("BRANCH_ID2 like", value, "branchId2");
            return (Criteria) this;
        }

        public Criteria andBranchId2NotLike(String value) {
            addCriterion("BRANCH_ID2 not like", value, "branchId2");
            return (Criteria) this;
        }

        public Criteria andBranchId2In(List<String> values) {
            addCriterion("BRANCH_ID2 in", values, "branchId2");
            return (Criteria) this;
        }

        public Criteria andBranchId2NotIn(List<String> values) {
            addCriterion("BRANCH_ID2 not in", values, "branchId2");
            return (Criteria) this;
        }

        public Criteria andBranchId2Between(String value1, String value2) {
            addCriterion("BRANCH_ID2 between", value1, value2, "branchId2");
            return (Criteria) this;
        }

        public Criteria andBranchId2NotBetween(String value1, String value2) {
            addCriterion("BRANCH_ID2 not between", value1, value2, "branchId2");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNull() {
            addCriterion("CARD_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNotNull() {
            addCriterion("CARD_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCardTypeEqualTo(String value) {
            addCriterion("CARD_TYPE =", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotEqualTo(String value) {
            addCriterion("CARD_TYPE <>", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThan(String value) {
            addCriterion("CARD_TYPE >", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThanOrEqualTo(String value) {
            addCriterion("CARD_TYPE >=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThan(String value) {
            addCriterion("CARD_TYPE <", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThanOrEqualTo(String value) {
            addCriterion("CARD_TYPE <=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLike(String value) {
            addCriterion("CARD_TYPE like", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotLike(String value) {
            addCriterion("CARD_TYPE not like", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeIn(List<String> values) {
            addCriterion("CARD_TYPE in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotIn(List<String> values) {
            addCriterion("CARD_TYPE not in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeBetween(String value1, String value2) {
            addCriterion("CARD_TYPE between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotBetween(String value1, String value2) {
            addCriterion("CARD_TYPE not between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardOrgIsNull() {
            addCriterion("CARD_ORG is null");
            return (Criteria) this;
        }

        public Criteria andCardOrgIsNotNull() {
            addCriterion("CARD_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andCardOrgEqualTo(String value) {
            addCriterion("CARD_ORG =", value, "cardOrg");
            return (Criteria) this;
        }

        public Criteria andCardOrgNotEqualTo(String value) {
            addCriterion("CARD_ORG <>", value, "cardOrg");
            return (Criteria) this;
        }

        public Criteria andCardOrgGreaterThan(String value) {
            addCriterion("CARD_ORG >", value, "cardOrg");
            return (Criteria) this;
        }

        public Criteria andCardOrgGreaterThanOrEqualTo(String value) {
            addCriterion("CARD_ORG >=", value, "cardOrg");
            return (Criteria) this;
        }

        public Criteria andCardOrgLessThan(String value) {
            addCriterion("CARD_ORG <", value, "cardOrg");
            return (Criteria) this;
        }

        public Criteria andCardOrgLessThanOrEqualTo(String value) {
            addCriterion("CARD_ORG <=", value, "cardOrg");
            return (Criteria) this;
        }

        public Criteria andCardOrgLike(String value) {
            addCriterion("CARD_ORG like", value, "cardOrg");
            return (Criteria) this;
        }

        public Criteria andCardOrgNotLike(String value) {
            addCriterion("CARD_ORG not like", value, "cardOrg");
            return (Criteria) this;
        }

        public Criteria andCardOrgIn(List<String> values) {
            addCriterion("CARD_ORG in", values, "cardOrg");
            return (Criteria) this;
        }

        public Criteria andCardOrgNotIn(List<String> values) {
            addCriterion("CARD_ORG not in", values, "cardOrg");
            return (Criteria) this;
        }

        public Criteria andCardOrgBetween(String value1, String value2) {
            addCriterion("CARD_ORG between", value1, value2, "cardOrg");
            return (Criteria) this;
        }

        public Criteria andCardOrgNotBetween(String value1, String value2) {
            addCriterion("CARD_ORG not between", value1, value2, "cardOrg");
            return (Criteria) this;
        }

        public Criteria andCardTagIsNull() {
            addCriterion("CARD_TAG is null");
            return (Criteria) this;
        }

        public Criteria andCardTagIsNotNull() {
            addCriterion("CARD_TAG is not null");
            return (Criteria) this;
        }

        public Criteria andCardTagEqualTo(BigDecimal value) {
            addCriterion("CARD_TAG =", value, "cardTag");
            return (Criteria) this;
        }

        public Criteria andCardTagNotEqualTo(BigDecimal value) {
            addCriterion("CARD_TAG <>", value, "cardTag");
            return (Criteria) this;
        }

        public Criteria andCardTagGreaterThan(BigDecimal value) {
            addCriterion("CARD_TAG >", value, "cardTag");
            return (Criteria) this;
        }

        public Criteria andCardTagGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CARD_TAG >=", value, "cardTag");
            return (Criteria) this;
        }

        public Criteria andCardTagLessThan(BigDecimal value) {
            addCriterion("CARD_TAG <", value, "cardTag");
            return (Criteria) this;
        }

        public Criteria andCardTagLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CARD_TAG <=", value, "cardTag");
            return (Criteria) this;
        }

        public Criteria andCardTagIn(List<BigDecimal> values) {
            addCriterion("CARD_TAG in", values, "cardTag");
            return (Criteria) this;
        }

        public Criteria andCardTagNotIn(List<BigDecimal> values) {
            addCriterion("CARD_TAG not in", values, "cardTag");
            return (Criteria) this;
        }

        public Criteria andCardTagBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CARD_TAG between", value1, value2, "cardTag");
            return (Criteria) this;
        }

        public Criteria andCardTagNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CARD_TAG not between", value1, value2, "cardTag");
            return (Criteria) this;
        }

        public Criteria andCardTag2IsNull() {
            addCriterion("CARD_TAG2 is null");
            return (Criteria) this;
        }

        public Criteria andCardTag2IsNotNull() {
            addCriterion("CARD_TAG2 is not null");
            return (Criteria) this;
        }

        public Criteria andCardTag2EqualTo(BigDecimal value) {
            addCriterion("CARD_TAG2 =", value, "cardTag2");
            return (Criteria) this;
        }

        public Criteria andCardTag2NotEqualTo(BigDecimal value) {
            addCriterion("CARD_TAG2 <>", value, "cardTag2");
            return (Criteria) this;
        }

        public Criteria andCardTag2GreaterThan(BigDecimal value) {
            addCriterion("CARD_TAG2 >", value, "cardTag2");
            return (Criteria) this;
        }

        public Criteria andCardTag2GreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CARD_TAG2 >=", value, "cardTag2");
            return (Criteria) this;
        }

        public Criteria andCardTag2LessThan(BigDecimal value) {
            addCriterion("CARD_TAG2 <", value, "cardTag2");
            return (Criteria) this;
        }

        public Criteria andCardTag2LessThanOrEqualTo(BigDecimal value) {
            addCriterion("CARD_TAG2 <=", value, "cardTag2");
            return (Criteria) this;
        }

        public Criteria andCardTag2In(List<BigDecimal> values) {
            addCriterion("CARD_TAG2 in", values, "cardTag2");
            return (Criteria) this;
        }

        public Criteria andCardTag2NotIn(List<BigDecimal> values) {
            addCriterion("CARD_TAG2 not in", values, "cardTag2");
            return (Criteria) this;
        }

        public Criteria andCardTag2Between(BigDecimal value1, BigDecimal value2) {
            addCriterion("CARD_TAG2 between", value1, value2, "cardTag2");
            return (Criteria) this;
        }

        public Criteria andCardTag2NotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CARD_TAG2 not between", value1, value2, "cardTag2");
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