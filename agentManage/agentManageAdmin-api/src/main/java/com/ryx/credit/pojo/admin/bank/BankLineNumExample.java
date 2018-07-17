package com.ryx.credit.pojo.admin.bank;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankLineNumExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public BankLineNumExample() {
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

        public Criteria andAllLineNumIsNull() {
            addCriterion("ALL_LINE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andAllLineNumIsNotNull() {
            addCriterion("ALL_LINE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andAllLineNumEqualTo(String value) {
            addCriterion("ALL_LINE_NUM =", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotEqualTo(String value) {
            addCriterion("ALL_LINE_NUM <>", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumGreaterThan(String value) {
            addCriterion("ALL_LINE_NUM >", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumGreaterThanOrEqualTo(String value) {
            addCriterion("ALL_LINE_NUM >=", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumLessThan(String value) {
            addCriterion("ALL_LINE_NUM <", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumLessThanOrEqualTo(String value) {
            addCriterion("ALL_LINE_NUM <=", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumLike(String value) {
            addCriterion("ALL_LINE_NUM like", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotLike(String value) {
            addCriterion("ALL_LINE_NUM not like", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumIn(List<String> values) {
            addCriterion("ALL_LINE_NUM in", values, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotIn(List<String> values) {
            addCriterion("ALL_LINE_NUM not in", values, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumBetween(String value1, String value2) {
            addCriterion("ALL_LINE_NUM between", value1, value2, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotBetween(String value1, String value2) {
            addCriterion("ALL_LINE_NUM not between", value1, value2, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumIsNull() {
            addCriterion("BRANCH_LINE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumIsNotNull() {
            addCriterion("BRANCH_LINE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM =", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM <>", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumGreaterThan(String value) {
            addCriterion("BRANCH_LINE_NUM >", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumGreaterThanOrEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM >=", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumLessThan(String value) {
            addCriterion("BRANCH_LINE_NUM <", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumLessThanOrEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM <=", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumLike(String value) {
            addCriterion("BRANCH_LINE_NUM like", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotLike(String value) {
            addCriterion("BRANCH_LINE_NUM not like", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumIn(List<String> values) {
            addCriterion("BRANCH_LINE_NUM in", values, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotIn(List<String> values) {
            addCriterion("BRANCH_LINE_NUM not in", values, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumBetween(String value1, String value2) {
            addCriterion("BRANCH_LINE_NUM between", value1, value2, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotBetween(String value1, String value2) {
            addCriterion("BRANCH_LINE_NUM not between", value1, value2, "branchLineNum");
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

        public Criteria andRegionCodeIsNull() {
            addCriterion("REGION_CODE is null");
            return (Criteria) this;
        }

        public Criteria andRegionCodeIsNotNull() {
            addCriterion("REGION_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andRegionCodeEqualTo(String value) {
            addCriterion("REGION_CODE =", value, "regionCode");
            return (Criteria) this;
        }

        public Criteria andRegionCodeNotEqualTo(String value) {
            addCriterion("REGION_CODE <>", value, "regionCode");
            return (Criteria) this;
        }

        public Criteria andRegionCodeGreaterThan(String value) {
            addCriterion("REGION_CODE >", value, "regionCode");
            return (Criteria) this;
        }

        public Criteria andRegionCodeGreaterThanOrEqualTo(String value) {
            addCriterion("REGION_CODE >=", value, "regionCode");
            return (Criteria) this;
        }

        public Criteria andRegionCodeLessThan(String value) {
            addCriterion("REGION_CODE <", value, "regionCode");
            return (Criteria) this;
        }

        public Criteria andRegionCodeLessThanOrEqualTo(String value) {
            addCriterion("REGION_CODE <=", value, "regionCode");
            return (Criteria) this;
        }

        public Criteria andRegionCodeLike(String value) {
            addCriterion("REGION_CODE like", value, "regionCode");
            return (Criteria) this;
        }

        public Criteria andRegionCodeNotLike(String value) {
            addCriterion("REGION_CODE not like", value, "regionCode");
            return (Criteria) this;
        }

        public Criteria andRegionCodeIn(List<String> values) {
            addCriterion("REGION_CODE in", values, "regionCode");
            return (Criteria) this;
        }

        public Criteria andRegionCodeNotIn(List<String> values) {
            addCriterion("REGION_CODE not in", values, "regionCode");
            return (Criteria) this;
        }

        public Criteria andRegionCodeBetween(String value1, String value2) {
            addCriterion("REGION_CODE between", value1, value2, "regionCode");
            return (Criteria) this;
        }

        public Criteria andRegionCodeNotBetween(String value1, String value2) {
            addCriterion("REGION_CODE not between", value1, value2, "regionCode");
            return (Criteria) this;
        }

        public Criteria andOrgFullNameIsNull() {
            addCriterion("ORG_FULL_NAME is null");
            return (Criteria) this;
        }

        public Criteria andOrgFullNameIsNotNull() {
            addCriterion("ORG_FULL_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andOrgFullNameEqualTo(String value) {
            addCriterion("ORG_FULL_NAME =", value, "orgFullName");
            return (Criteria) this;
        }

        public Criteria andOrgFullNameNotEqualTo(String value) {
            addCriterion("ORG_FULL_NAME <>", value, "orgFullName");
            return (Criteria) this;
        }

        public Criteria andOrgFullNameGreaterThan(String value) {
            addCriterion("ORG_FULL_NAME >", value, "orgFullName");
            return (Criteria) this;
        }

        public Criteria andOrgFullNameGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_FULL_NAME >=", value, "orgFullName");
            return (Criteria) this;
        }

        public Criteria andOrgFullNameLessThan(String value) {
            addCriterion("ORG_FULL_NAME <", value, "orgFullName");
            return (Criteria) this;
        }

        public Criteria andOrgFullNameLessThanOrEqualTo(String value) {
            addCriterion("ORG_FULL_NAME <=", value, "orgFullName");
            return (Criteria) this;
        }

        public Criteria andOrgFullNameLike(String value) {
            addCriterion("ORG_FULL_NAME like", value, "orgFullName");
            return (Criteria) this;
        }

        public Criteria andOrgFullNameNotLike(String value) {
            addCriterion("ORG_FULL_NAME not like", value, "orgFullName");
            return (Criteria) this;
        }

        public Criteria andOrgFullNameIn(List<String> values) {
            addCriterion("ORG_FULL_NAME in", values, "orgFullName");
            return (Criteria) this;
        }

        public Criteria andOrgFullNameNotIn(List<String> values) {
            addCriterion("ORG_FULL_NAME not in", values, "orgFullName");
            return (Criteria) this;
        }

        public Criteria andOrgFullNameBetween(String value1, String value2) {
            addCriterion("ORG_FULL_NAME between", value1, value2, "orgFullName");
            return (Criteria) this;
        }

        public Criteria andOrgFullNameNotBetween(String value1, String value2) {
            addCriterion("ORG_FULL_NAME not between", value1, value2, "orgFullName");
            return (Criteria) this;
        }

        public Criteria andOrgShortNameIsNull() {
            addCriterion("ORG_SHORT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andOrgShortNameIsNotNull() {
            addCriterion("ORG_SHORT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andOrgShortNameEqualTo(String value) {
            addCriterion("ORG_SHORT_NAME =", value, "orgShortName");
            return (Criteria) this;
        }

        public Criteria andOrgShortNameNotEqualTo(String value) {
            addCriterion("ORG_SHORT_NAME <>", value, "orgShortName");
            return (Criteria) this;
        }

        public Criteria andOrgShortNameGreaterThan(String value) {
            addCriterion("ORG_SHORT_NAME >", value, "orgShortName");
            return (Criteria) this;
        }

        public Criteria andOrgShortNameGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_SHORT_NAME >=", value, "orgShortName");
            return (Criteria) this;
        }

        public Criteria andOrgShortNameLessThan(String value) {
            addCriterion("ORG_SHORT_NAME <", value, "orgShortName");
            return (Criteria) this;
        }

        public Criteria andOrgShortNameLessThanOrEqualTo(String value) {
            addCriterion("ORG_SHORT_NAME <=", value, "orgShortName");
            return (Criteria) this;
        }

        public Criteria andOrgShortNameLike(String value) {
            addCriterion("ORG_SHORT_NAME like", value, "orgShortName");
            return (Criteria) this;
        }

        public Criteria andOrgShortNameNotLike(String value) {
            addCriterion("ORG_SHORT_NAME not like", value, "orgShortName");
            return (Criteria) this;
        }

        public Criteria andOrgShortNameIn(List<String> values) {
            addCriterion("ORG_SHORT_NAME in", values, "orgShortName");
            return (Criteria) this;
        }

        public Criteria andOrgShortNameNotIn(List<String> values) {
            addCriterion("ORG_SHORT_NAME not in", values, "orgShortName");
            return (Criteria) this;
        }

        public Criteria andOrgShortNameBetween(String value1, String value2) {
            addCriterion("ORG_SHORT_NAME between", value1, value2, "orgShortName");
            return (Criteria) this;
        }

        public Criteria andOrgShortNameNotBetween(String value1, String value2) {
            addCriterion("ORG_SHORT_NAME not between", value1, value2, "orgShortName");
            return (Criteria) this;
        }

        public Criteria andBankIdIsNull() {
            addCriterion("BANK_ID is null");
            return (Criteria) this;
        }

        public Criteria andBankIdIsNotNull() {
            addCriterion("BANK_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBankIdEqualTo(BigDecimal value) {
            addCriterion("BANK_ID =", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotEqualTo(BigDecimal value) {
            addCriterion("BANK_ID <>", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThan(BigDecimal value) {
            addCriterion("BANK_ID >", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BANK_ID >=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThan(BigDecimal value) {
            addCriterion("BANK_ID <", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BANK_ID <=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdIn(List<BigDecimal> values) {
            addCriterion("BANK_ID in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotIn(List<BigDecimal> values) {
            addCriterion("BANK_ID not in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BANK_ID between", value1, value2, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BANK_ID not between", value1, value2, "bankId");
            return (Criteria) this;
        }

        public Criteria andPaySystemIsNull() {
            addCriterion("PAY_SYSTEM is null");
            return (Criteria) this;
        }

        public Criteria andPaySystemIsNotNull() {
            addCriterion("PAY_SYSTEM is not null");
            return (Criteria) this;
        }

        public Criteria andPaySystemEqualTo(String value) {
            addCriterion("PAY_SYSTEM =", value, "paySystem");
            return (Criteria) this;
        }

        public Criteria andPaySystemNotEqualTo(String value) {
            addCriterion("PAY_SYSTEM <>", value, "paySystem");
            return (Criteria) this;
        }

        public Criteria andPaySystemGreaterThan(String value) {
            addCriterion("PAY_SYSTEM >", value, "paySystem");
            return (Criteria) this;
        }

        public Criteria andPaySystemGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_SYSTEM >=", value, "paySystem");
            return (Criteria) this;
        }

        public Criteria andPaySystemLessThan(String value) {
            addCriterion("PAY_SYSTEM <", value, "paySystem");
            return (Criteria) this;
        }

        public Criteria andPaySystemLessThanOrEqualTo(String value) {
            addCriterion("PAY_SYSTEM <=", value, "paySystem");
            return (Criteria) this;
        }

        public Criteria andPaySystemLike(String value) {
            addCriterion("PAY_SYSTEM like", value, "paySystem");
            return (Criteria) this;
        }

        public Criteria andPaySystemNotLike(String value) {
            addCriterion("PAY_SYSTEM not like", value, "paySystem");
            return (Criteria) this;
        }

        public Criteria andPaySystemIn(List<String> values) {
            addCriterion("PAY_SYSTEM in", values, "paySystem");
            return (Criteria) this;
        }

        public Criteria andPaySystemNotIn(List<String> values) {
            addCriterion("PAY_SYSTEM not in", values, "paySystem");
            return (Criteria) this;
        }

        public Criteria andPaySystemBetween(String value1, String value2) {
            addCriterion("PAY_SYSTEM between", value1, value2, "paySystem");
            return (Criteria) this;
        }

        public Criteria andPaySystemNotBetween(String value1, String value2) {
            addCriterion("PAY_SYSTEM not between", value1, value2, "paySystem");
            return (Criteria) this;
        }

        public Criteria andBankSiteIsNull() {
            addCriterion("BANK_SITE is null");
            return (Criteria) this;
        }

        public Criteria andBankSiteIsNotNull() {
            addCriterion("BANK_SITE is not null");
            return (Criteria) this;
        }

        public Criteria andBankSiteEqualTo(String value) {
            addCriterion("BANK_SITE =", value, "bankSite");
            return (Criteria) this;
        }

        public Criteria andBankSiteNotEqualTo(String value) {
            addCriterion("BANK_SITE <>", value, "bankSite");
            return (Criteria) this;
        }

        public Criteria andBankSiteGreaterThan(String value) {
            addCriterion("BANK_SITE >", value, "bankSite");
            return (Criteria) this;
        }

        public Criteria andBankSiteGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_SITE >=", value, "bankSite");
            return (Criteria) this;
        }

        public Criteria andBankSiteLessThan(String value) {
            addCriterion("BANK_SITE <", value, "bankSite");
            return (Criteria) this;
        }

        public Criteria andBankSiteLessThanOrEqualTo(String value) {
            addCriterion("BANK_SITE <=", value, "bankSite");
            return (Criteria) this;
        }

        public Criteria andBankSiteLike(String value) {
            addCriterion("BANK_SITE like", value, "bankSite");
            return (Criteria) this;
        }

        public Criteria andBankSiteNotLike(String value) {
            addCriterion("BANK_SITE not like", value, "bankSite");
            return (Criteria) this;
        }

        public Criteria andBankSiteIn(List<String> values) {
            addCriterion("BANK_SITE in", values, "bankSite");
            return (Criteria) this;
        }

        public Criteria andBankSiteNotIn(List<String> values) {
            addCriterion("BANK_SITE not in", values, "bankSite");
            return (Criteria) this;
        }

        public Criteria andBankSiteBetween(String value1, String value2) {
            addCriterion("BANK_SITE between", value1, value2, "bankSite");
            return (Criteria) this;
        }

        public Criteria andBankSiteNotBetween(String value1, String value2) {
            addCriterion("BANK_SITE not between", value1, value2, "bankSite");
            return (Criteria) this;
        }

        public Criteria andPostCodeIsNull() {
            addCriterion("POST_CODE is null");
            return (Criteria) this;
        }

        public Criteria andPostCodeIsNotNull() {
            addCriterion("POST_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andPostCodeEqualTo(String value) {
            addCriterion("POST_CODE =", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotEqualTo(String value) {
            addCriterion("POST_CODE <>", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeGreaterThan(String value) {
            addCriterion("POST_CODE >", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeGreaterThanOrEqualTo(String value) {
            addCriterion("POST_CODE >=", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeLessThan(String value) {
            addCriterion("POST_CODE <", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeLessThanOrEqualTo(String value) {
            addCriterion("POST_CODE <=", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeLike(String value) {
            addCriterion("POST_CODE like", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotLike(String value) {
            addCriterion("POST_CODE not like", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeIn(List<String> values) {
            addCriterion("POST_CODE in", values, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotIn(List<String> values) {
            addCriterion("POST_CODE not in", values, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeBetween(String value1, String value2) {
            addCriterion("POST_CODE between", value1, value2, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotBetween(String value1, String value2) {
            addCriterion("POST_CODE not between", value1, value2, "postCode");
            return (Criteria) this;
        }

        public Criteria andPhoneNumIsNull() {
            addCriterion("PHONE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumIsNotNull() {
            addCriterion("PHONE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumEqualTo(String value) {
            addCriterion("PHONE_NUM =", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumNotEqualTo(String value) {
            addCriterion("PHONE_NUM <>", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumGreaterThan(String value) {
            addCriterion("PHONE_NUM >", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumGreaterThanOrEqualTo(String value) {
            addCriterion("PHONE_NUM >=", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumLessThan(String value) {
            addCriterion("PHONE_NUM <", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumLessThanOrEqualTo(String value) {
            addCriterion("PHONE_NUM <=", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumLike(String value) {
            addCriterion("PHONE_NUM like", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumNotLike(String value) {
            addCriterion("PHONE_NUM not like", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumIn(List<String> values) {
            addCriterion("PHONE_NUM in", values, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumNotIn(List<String> values) {
            addCriterion("PHONE_NUM not in", values, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumBetween(String value1, String value2) {
            addCriterion("PHONE_NUM between", value1, value2, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumNotBetween(String value1, String value2) {
            addCriterion("PHONE_NUM not between", value1, value2, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andDataSourcesIsNull() {
            addCriterion("DATA_SOURCES is null");
            return (Criteria) this;
        }

        public Criteria andDataSourcesIsNotNull() {
            addCriterion("DATA_SOURCES is not null");
            return (Criteria) this;
        }

        public Criteria andDataSourcesEqualTo(String value) {
            addCriterion("DATA_SOURCES =", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesNotEqualTo(String value) {
            addCriterion("DATA_SOURCES <>", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesGreaterThan(String value) {
            addCriterion("DATA_SOURCES >", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesGreaterThanOrEqualTo(String value) {
            addCriterion("DATA_SOURCES >=", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesLessThan(String value) {
            addCriterion("DATA_SOURCES <", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesLessThanOrEqualTo(String value) {
            addCriterion("DATA_SOURCES <=", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesLike(String value) {
            addCriterion("DATA_SOURCES like", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesNotLike(String value) {
            addCriterion("DATA_SOURCES not like", value, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesIn(List<String> values) {
            addCriterion("DATA_SOURCES in", values, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesNotIn(List<String> values) {
            addCriterion("DATA_SOURCES not in", values, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesBetween(String value1, String value2) {
            addCriterion("DATA_SOURCES between", value1, value2, "dataSources");
            return (Criteria) this;
        }

        public Criteria andDataSourcesNotBetween(String value1, String value2) {
            addCriterion("DATA_SOURCES not between", value1, value2, "dataSources");
            return (Criteria) this;
        }

        public Criteria andTaskNumIsNull() {
            addCriterion("TASK_NUM is null");
            return (Criteria) this;
        }

        public Criteria andTaskNumIsNotNull() {
            addCriterion("TASK_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andTaskNumEqualTo(String value) {
            addCriterion("TASK_NUM =", value, "taskNum");
            return (Criteria) this;
        }

        public Criteria andTaskNumNotEqualTo(String value) {
            addCriterion("TASK_NUM <>", value, "taskNum");
            return (Criteria) this;
        }

        public Criteria andTaskNumGreaterThan(String value) {
            addCriterion("TASK_NUM >", value, "taskNum");
            return (Criteria) this;
        }

        public Criteria andTaskNumGreaterThanOrEqualTo(String value) {
            addCriterion("TASK_NUM >=", value, "taskNum");
            return (Criteria) this;
        }

        public Criteria andTaskNumLessThan(String value) {
            addCriterion("TASK_NUM <", value, "taskNum");
            return (Criteria) this;
        }

        public Criteria andTaskNumLessThanOrEqualTo(String value) {
            addCriterion("TASK_NUM <=", value, "taskNum");
            return (Criteria) this;
        }

        public Criteria andTaskNumLike(String value) {
            addCriterion("TASK_NUM like", value, "taskNum");
            return (Criteria) this;
        }

        public Criteria andTaskNumNotLike(String value) {
            addCriterion("TASK_NUM not like", value, "taskNum");
            return (Criteria) this;
        }

        public Criteria andTaskNumIn(List<String> values) {
            addCriterion("TASK_NUM in", values, "taskNum");
            return (Criteria) this;
        }

        public Criteria andTaskNumNotIn(List<String> values) {
            addCriterion("TASK_NUM not in", values, "taskNum");
            return (Criteria) this;
        }

        public Criteria andTaskNumBetween(String value1, String value2) {
            addCriterion("TASK_NUM between", value1, value2, "taskNum");
            return (Criteria) this;
        }

        public Criteria andTaskNumNotBetween(String value1, String value2) {
            addCriterion("TASK_NUM not between", value1, value2, "taskNum");
            return (Criteria) this;
        }

        public Criteria andTakeTimeIsNull() {
            addCriterion("TAKE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andTakeTimeIsNotNull() {
            addCriterion("TAKE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andTakeTimeEqualTo(Date value) {
            addCriterion("TAKE_TIME =", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeNotEqualTo(Date value) {
            addCriterion("TAKE_TIME <>", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeGreaterThan(Date value) {
            addCriterion("TAKE_TIME >", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("TAKE_TIME >=", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeLessThan(Date value) {
            addCriterion("TAKE_TIME <", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeLessThanOrEqualTo(Date value) {
            addCriterion("TAKE_TIME <=", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeIn(List<Date> values) {
            addCriterion("TAKE_TIME in", values, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeNotIn(List<Date> values) {
            addCriterion("TAKE_TIME not in", values, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeBetween(Date value1, Date value2) {
            addCriterion("TAKE_TIME between", value1, value2, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeNotBetween(Date value1, Date value2) {
            addCriterion("TAKE_TIME not between", value1, value2, "takeTime");
            return (Criteria) this;
        }

        public Criteria andLoseTimeIsNull() {
            addCriterion("LOSE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andLoseTimeIsNotNull() {
            addCriterion("LOSE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andLoseTimeEqualTo(Date value) {
            addCriterion("LOSE_TIME =", value, "loseTime");
            return (Criteria) this;
        }

        public Criteria andLoseTimeNotEqualTo(Date value) {
            addCriterion("LOSE_TIME <>", value, "loseTime");
            return (Criteria) this;
        }

        public Criteria andLoseTimeGreaterThan(Date value) {
            addCriterion("LOSE_TIME >", value, "loseTime");
            return (Criteria) this;
        }

        public Criteria andLoseTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("LOSE_TIME >=", value, "loseTime");
            return (Criteria) this;
        }

        public Criteria andLoseTimeLessThan(Date value) {
            addCriterion("LOSE_TIME <", value, "loseTime");
            return (Criteria) this;
        }

        public Criteria andLoseTimeLessThanOrEqualTo(Date value) {
            addCriterion("LOSE_TIME <=", value, "loseTime");
            return (Criteria) this;
        }

        public Criteria andLoseTimeIn(List<Date> values) {
            addCriterion("LOSE_TIME in", values, "loseTime");
            return (Criteria) this;
        }

        public Criteria andLoseTimeNotIn(List<Date> values) {
            addCriterion("LOSE_TIME not in", values, "loseTime");
            return (Criteria) this;
        }

        public Criteria andLoseTimeBetween(Date value1, Date value2) {
            addCriterion("LOSE_TIME between", value1, value2, "loseTime");
            return (Criteria) this;
        }

        public Criteria andLoseTimeNotBetween(Date value1, Date value2) {
            addCriterion("LOSE_TIME not between", value1, value2, "loseTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIsNull() {
            addCriterion("BEGIN_TIME is null");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIsNotNull() {
            addCriterion("BEGIN_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andBeginTimeEqualTo(Date value) {
            addCriterion("BEGIN_TIME =", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotEqualTo(Date value) {
            addCriterion("BEGIN_TIME <>", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeGreaterThan(Date value) {
            addCriterion("BEGIN_TIME >", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("BEGIN_TIME >=", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLessThan(Date value) {
            addCriterion("BEGIN_TIME <", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLessThanOrEqualTo(Date value) {
            addCriterion("BEGIN_TIME <=", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIn(List<Date> values) {
            addCriterion("BEGIN_TIME in", values, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotIn(List<Date> values) {
            addCriterion("BEGIN_TIME not in", values, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeBetween(Date value1, Date value2) {
            addCriterion("BEGIN_TIME between", value1, value2, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotBetween(Date value1, Date value2) {
            addCriterion("BEGIN_TIME not between", value1, value2, "beginTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("END_TIME is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("END_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("END_TIME =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("END_TIME <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("END_TIME >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("END_TIME >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("END_TIME <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("END_TIME <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("END_TIME in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("END_TIME not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("END_TIME between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("END_TIME not between", value1, value2, "endTime");
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