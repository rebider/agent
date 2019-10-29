package com.ryx.credit.pojo.admin;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CBranchInnerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CBranchInnerExample() {
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

        public Criteria andBranchLoginIsNull() {
            addCriterion("BRANCH_LOGIN is null");
            return (Criteria) this;
        }

        public Criteria andBranchLoginIsNotNull() {
            addCriterion("BRANCH_LOGIN is not null");
            return (Criteria) this;
        }

        public Criteria andBranchLoginEqualTo(String value) {
            addCriterion("BRANCH_LOGIN =", value, "branchLogin");
            return (Criteria) this;
        }

        public Criteria andBranchLoginNotEqualTo(String value) {
            addCriterion("BRANCH_LOGIN <>", value, "branchLogin");
            return (Criteria) this;
        }

        public Criteria andBranchLoginGreaterThan(String value) {
            addCriterion("BRANCH_LOGIN >", value, "branchLogin");
            return (Criteria) this;
        }

        public Criteria andBranchLoginGreaterThanOrEqualTo(String value) {
            addCriterion("BRANCH_LOGIN >=", value, "branchLogin");
            return (Criteria) this;
        }

        public Criteria andBranchLoginLessThan(String value) {
            addCriterion("BRANCH_LOGIN <", value, "branchLogin");
            return (Criteria) this;
        }

        public Criteria andBranchLoginLessThanOrEqualTo(String value) {
            addCriterion("BRANCH_LOGIN <=", value, "branchLogin");
            return (Criteria) this;
        }

        public Criteria andBranchLoginLike(String value) {
            addCriterion("BRANCH_LOGIN like", value, "branchLogin");
            return (Criteria) this;
        }

        public Criteria andBranchLoginNotLike(String value) {
            addCriterion("BRANCH_LOGIN not like", value, "branchLogin");
            return (Criteria) this;
        }

        public Criteria andBranchLoginIn(List<String> values) {
            addCriterion("BRANCH_LOGIN in", values, "branchLogin");
            return (Criteria) this;
        }

        public Criteria andBranchLoginNotIn(List<String> values) {
            addCriterion("BRANCH_LOGIN not in", values, "branchLogin");
            return (Criteria) this;
        }

        public Criteria andBranchLoginBetween(String value1, String value2) {
            addCriterion("BRANCH_LOGIN between", value1, value2, "branchLogin");
            return (Criteria) this;
        }

        public Criteria andBranchLoginNotBetween(String value1, String value2) {
            addCriterion("BRANCH_LOGIN not between", value1, value2, "branchLogin");
            return (Criteria) this;
        }

        public Criteria andInnerLoginIsNull() {
            addCriterion("INNER_LOGIN is null");
            return (Criteria) this;
        }

        public Criteria andInnerLoginIsNotNull() {
            addCriterion("INNER_LOGIN is not null");
            return (Criteria) this;
        }

        public Criteria andInnerLoginEqualTo(String value) {
            addCriterion("INNER_LOGIN =", value, "innerLogin");
            return (Criteria) this;
        }

        public Criteria andInnerLoginNotEqualTo(String value) {
            addCriterion("INNER_LOGIN <>", value, "innerLogin");
            return (Criteria) this;
        }

        public Criteria andInnerLoginGreaterThan(String value) {
            addCriterion("INNER_LOGIN >", value, "innerLogin");
            return (Criteria) this;
        }

        public Criteria andInnerLoginGreaterThanOrEqualTo(String value) {
            addCriterion("INNER_LOGIN >=", value, "innerLogin");
            return (Criteria) this;
        }

        public Criteria andInnerLoginLessThan(String value) {
            addCriterion("INNER_LOGIN <", value, "innerLogin");
            return (Criteria) this;
        }

        public Criteria andInnerLoginLessThanOrEqualTo(String value) {
            addCriterion("INNER_LOGIN <=", value, "innerLogin");
            return (Criteria) this;
        }

        public Criteria andInnerLoginLike(String value) {
            addCriterion("INNER_LOGIN like", value, "innerLogin");
            return (Criteria) this;
        }

        public Criteria andInnerLoginNotLike(String value) {
            addCriterion("INNER_LOGIN not like", value, "innerLogin");
            return (Criteria) this;
        }

        public Criteria andInnerLoginIn(List<String> values) {
            addCriterion("INNER_LOGIN in", values, "innerLogin");
            return (Criteria) this;
        }

        public Criteria andInnerLoginNotIn(List<String> values) {
            addCriterion("INNER_LOGIN not in", values, "innerLogin");
            return (Criteria) this;
        }

        public Criteria andInnerLoginBetween(String value1, String value2) {
            addCriterion("INNER_LOGIN between", value1, value2, "innerLogin");
            return (Criteria) this;
        }

        public Criteria andInnerLoginNotBetween(String value1, String value2) {
            addCriterion("INNER_LOGIN not between", value1, value2, "innerLogin");
            return (Criteria) this;
        }

        public Criteria andCUserIdIsNull() {
            addCriterion("C_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andCUserIdIsNotNull() {
            addCriterion("C_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCUserIdEqualTo(String value) {
            addCriterion("C_USER_ID =", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdNotEqualTo(String value) {
            addCriterion("C_USER_ID <>", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdGreaterThan(String value) {
            addCriterion("C_USER_ID >", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("C_USER_ID >=", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdLessThan(String value) {
            addCriterion("C_USER_ID <", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdLessThanOrEqualTo(String value) {
            addCriterion("C_USER_ID <=", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdLike(String value) {
            addCriterion("C_USER_ID like", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdNotLike(String value) {
            addCriterion("C_USER_ID not like", value, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdIn(List<String> values) {
            addCriterion("C_USER_ID in", values, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdNotIn(List<String> values) {
            addCriterion("C_USER_ID not in", values, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdBetween(String value1, String value2) {
            addCriterion("C_USER_ID between", value1, value2, "cUserId");
            return (Criteria) this;
        }

        public Criteria andCUserIdNotBetween(String value1, String value2) {
            addCriterion("C_USER_ID not between", value1, value2, "cUserId");
            return (Criteria) this;
        }

        public Criteria andBranchNameIsNull() {
            addCriterion("BRANCH_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBranchNameIsNotNull() {
            addCriterion("BRANCH_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBranchNameEqualTo(String value) {
            addCriterion("BRANCH_NAME =", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotEqualTo(String value) {
            addCriterion("BRANCH_NAME <>", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameGreaterThan(String value) {
            addCriterion("BRANCH_NAME >", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameGreaterThanOrEqualTo(String value) {
            addCriterion("BRANCH_NAME >=", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameLessThan(String value) {
            addCriterion("BRANCH_NAME <", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameLessThanOrEqualTo(String value) {
            addCriterion("BRANCH_NAME <=", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameLike(String value) {
            addCriterion("BRANCH_NAME like", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotLike(String value) {
            addCriterion("BRANCH_NAME not like", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameIn(List<String> values) {
            addCriterion("BRANCH_NAME in", values, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotIn(List<String> values) {
            addCriterion("BRANCH_NAME not in", values, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameBetween(String value1, String value2) {
            addCriterion("BRANCH_NAME between", value1, value2, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotBetween(String value1, String value2) {
            addCriterion("BRANCH_NAME not between", value1, value2, "branchName");
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

        public Criteria andCUserNameIsNull() {
            addCriterion("C_USER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCUserNameIsNotNull() {
            addCriterion("C_USER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCUserNameEqualTo(String value) {
            addCriterion("C_USER_NAME =", value, "cUserName");
            return (Criteria) this;
        }

        public Criteria andCUserNameNotEqualTo(String value) {
            addCriterion("C_USER_NAME <>", value, "cUserName");
            return (Criteria) this;
        }

        public Criteria andCUserNameGreaterThan(String value) {
            addCriterion("C_USER_NAME >", value, "cUserName");
            return (Criteria) this;
        }

        public Criteria andCUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("C_USER_NAME >=", value, "cUserName");
            return (Criteria) this;
        }

        public Criteria andCUserNameLessThan(String value) {
            addCriterion("C_USER_NAME <", value, "cUserName");
            return (Criteria) this;
        }

        public Criteria andCUserNameLessThanOrEqualTo(String value) {
            addCriterion("C_USER_NAME <=", value, "cUserName");
            return (Criteria) this;
        }

        public Criteria andCUserNameLike(String value) {
            addCriterion("C_USER_NAME like", value, "cUserName");
            return (Criteria) this;
        }

        public Criteria andCUserNameNotLike(String value) {
            addCriterion("C_USER_NAME not like", value, "cUserName");
            return (Criteria) this;
        }

        public Criteria andCUserNameIn(List<String> values) {
            addCriterion("C_USER_NAME in", values, "cUserName");
            return (Criteria) this;
        }

        public Criteria andCUserNameNotIn(List<String> values) {
            addCriterion("C_USER_NAME not in", values, "cUserName");
            return (Criteria) this;
        }

        public Criteria andCUserNameBetween(String value1, String value2) {
            addCriterion("C_USER_NAME between", value1, value2, "cUserName");
            return (Criteria) this;
        }

        public Criteria andCUserNameNotBetween(String value1, String value2) {
            addCriterion("C_USER_NAME not between", value1, value2, "cUserName");
            return (Criteria) this;
        }

        public Criteria andInnerNameIsNull() {
            addCriterion("INNER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andInnerNameIsNotNull() {
            addCriterion("INNER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andInnerNameEqualTo(String value) {
            addCriterion("INNER_NAME =", value, "innerName");
            return (Criteria) this;
        }

        public Criteria andInnerNameNotEqualTo(String value) {
            addCriterion("INNER_NAME <>", value, "innerName");
            return (Criteria) this;
        }

        public Criteria andInnerNameGreaterThan(String value) {
            addCriterion("INNER_NAME >", value, "innerName");
            return (Criteria) this;
        }

        public Criteria andInnerNameGreaterThanOrEqualTo(String value) {
            addCriterion("INNER_NAME >=", value, "innerName");
            return (Criteria) this;
        }

        public Criteria andInnerNameLessThan(String value) {
            addCriterion("INNER_NAME <", value, "innerName");
            return (Criteria) this;
        }

        public Criteria andInnerNameLessThanOrEqualTo(String value) {
            addCriterion("INNER_NAME <=", value, "innerName");
            return (Criteria) this;
        }

        public Criteria andInnerNameLike(String value) {
            addCriterion("INNER_NAME like", value, "innerName");
            return (Criteria) this;
        }

        public Criteria andInnerNameNotLike(String value) {
            addCriterion("INNER_NAME not like", value, "innerName");
            return (Criteria) this;
        }

        public Criteria andInnerNameIn(List<String> values) {
            addCriterion("INNER_NAME in", values, "innerName");
            return (Criteria) this;
        }

        public Criteria andInnerNameNotIn(List<String> values) {
            addCriterion("INNER_NAME not in", values, "innerName");
            return (Criteria) this;
        }

        public Criteria andInnerNameBetween(String value1, String value2) {
            addCriterion("INNER_NAME between", value1, value2, "innerName");
            return (Criteria) this;
        }

        public Criteria andInnerNameNotBetween(String value1, String value2) {
            addCriterion("INNER_NAME not between", value1, value2, "innerName");
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