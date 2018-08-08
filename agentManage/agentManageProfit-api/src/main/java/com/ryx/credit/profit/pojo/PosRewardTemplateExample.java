package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PosRewardTemplateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PosRewardTemplateExample() {
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

        public Criteria andTranContrastMonthIsNull() {
            addCriterion("TRAN_CONTRAST_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andTranContrastMonthIsNotNull() {
            addCriterion("TRAN_CONTRAST_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andTranContrastMonthEqualTo(String value) {
            addCriterion("TRAN_CONTRAST_MONTH =", value, "tranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andTranContrastMonthNotEqualTo(String value) {
            addCriterion("TRAN_CONTRAST_MONTH <>", value, "tranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andTranContrastMonthGreaterThan(String value) {
            addCriterion("TRAN_CONTRAST_MONTH >", value, "tranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andTranContrastMonthGreaterThanOrEqualTo(String value) {
            addCriterion("TRAN_CONTRAST_MONTH >=", value, "tranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andTranContrastMonthLessThan(String value) {
            addCriterion("TRAN_CONTRAST_MONTH <", value, "tranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andTranContrastMonthLessThanOrEqualTo(String value) {
            addCriterion("TRAN_CONTRAST_MONTH <=", value, "tranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andTranContrastMonthLike(String value) {
            addCriterion("TRAN_CONTRAST_MONTH like", value, "tranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andTranContrastMonthNotLike(String value) {
            addCriterion("TRAN_CONTRAST_MONTH not like", value, "tranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andTranContrastMonthIn(List<String> values) {
            addCriterion("TRAN_CONTRAST_MONTH in", values, "tranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andTranContrastMonthNotIn(List<String> values) {
            addCriterion("TRAN_CONTRAST_MONTH not in", values, "tranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andTranContrastMonthBetween(String value1, String value2) {
            addCriterion("TRAN_CONTRAST_MONTH between", value1, value2, "tranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andTranContrastMonthNotBetween(String value1, String value2) {
            addCriterion("TRAN_CONTRAST_MONTH not between", value1, value2, "tranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andTranTotalIsNull() {
            addCriterion("TRAN_TOTAL is null");
            return (Criteria) this;
        }

        public Criteria andTranTotalIsNotNull() {
            addCriterion("TRAN_TOTAL is not null");
            return (Criteria) this;
        }

        public Criteria andTranTotalEqualTo(String value) {
            addCriterion("TRAN_TOTAL =", value, "tranTotal");
            return (Criteria) this;
        }

        public Criteria andTranTotalNotEqualTo(String value) {
            addCriterion("TRAN_TOTAL <>", value, "tranTotal");
            return (Criteria) this;
        }

        public Criteria andTranTotalGreaterThan(String value) {
            addCriterion("TRAN_TOTAL >", value, "tranTotal");
            return (Criteria) this;
        }

        public Criteria andTranTotalGreaterThanOrEqualTo(String value) {
            addCriterion("TRAN_TOTAL >=", value, "tranTotal");
            return (Criteria) this;
        }

        public Criteria andTranTotalLessThan(String value) {
            addCriterion("TRAN_TOTAL <", value, "tranTotal");
            return (Criteria) this;
        }

        public Criteria andTranTotalLessThanOrEqualTo(String value) {
            addCriterion("TRAN_TOTAL <=", value, "tranTotal");
            return (Criteria) this;
        }

        public Criteria andTranTotalLike(String value) {
            addCriterion("TRAN_TOTAL like", value, "tranTotal");
            return (Criteria) this;
        }

        public Criteria andTranTotalNotLike(String value) {
            addCriterion("TRAN_TOTAL not like", value, "tranTotal");
            return (Criteria) this;
        }

        public Criteria andTranTotalIn(List<String> values) {
            addCriterion("TRAN_TOTAL in", values, "tranTotal");
            return (Criteria) this;
        }

        public Criteria andTranTotalNotIn(List<String> values) {
            addCriterion("TRAN_TOTAL not in", values, "tranTotal");
            return (Criteria) this;
        }

        public Criteria andTranTotalBetween(String value1, String value2) {
            addCriterion("TRAN_TOTAL between", value1, value2, "tranTotal");
            return (Criteria) this;
        }

        public Criteria andTranTotalNotBetween(String value1, String value2) {
            addCriterion("TRAN_TOTAL not between", value1, value2, "tranTotal");
            return (Criteria) this;
        }

        public Criteria andCreditTranContrastMonthIsNull() {
            addCriterion("CREDIT_TRAN_CONTRAST_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andCreditTranContrastMonthIsNotNull() {
            addCriterion("CREDIT_TRAN_CONTRAST_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andCreditTranContrastMonthEqualTo(String value) {
            addCriterion("CREDIT_TRAN_CONTRAST_MONTH =", value, "creditTranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andCreditTranContrastMonthNotEqualTo(String value) {
            addCriterion("CREDIT_TRAN_CONTRAST_MONTH <>", value, "creditTranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andCreditTranContrastMonthGreaterThan(String value) {
            addCriterion("CREDIT_TRAN_CONTRAST_MONTH >", value, "creditTranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andCreditTranContrastMonthGreaterThanOrEqualTo(String value) {
            addCriterion("CREDIT_TRAN_CONTRAST_MONTH >=", value, "creditTranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andCreditTranContrastMonthLessThan(String value) {
            addCriterion("CREDIT_TRAN_CONTRAST_MONTH <", value, "creditTranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andCreditTranContrastMonthLessThanOrEqualTo(String value) {
            addCriterion("CREDIT_TRAN_CONTRAST_MONTH <=", value, "creditTranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andCreditTranContrastMonthLike(String value) {
            addCriterion("CREDIT_TRAN_CONTRAST_MONTH like", value, "creditTranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andCreditTranContrastMonthNotLike(String value) {
            addCriterion("CREDIT_TRAN_CONTRAST_MONTH not like", value, "creditTranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andCreditTranContrastMonthIn(List<String> values) {
            addCriterion("CREDIT_TRAN_CONTRAST_MONTH in", values, "creditTranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andCreditTranContrastMonthNotIn(List<String> values) {
            addCriterion("CREDIT_TRAN_CONTRAST_MONTH not in", values, "creditTranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andCreditTranContrastMonthBetween(String value1, String value2) {
            addCriterion("CREDIT_TRAN_CONTRAST_MONTH between", value1, value2, "creditTranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andCreditTranContrastMonthNotBetween(String value1, String value2) {
            addCriterion("CREDIT_TRAN_CONTRAST_MONTH not between", value1, value2, "creditTranContrastMonth");
            return (Criteria) this;
        }

        public Criteria andProportionIsNull() {
            addCriterion("PROPORTION is null");
            return (Criteria) this;
        }

        public Criteria andProportionIsNotNull() {
            addCriterion("PROPORTION is not null");
            return (Criteria) this;
        }

        public Criteria andProportionEqualTo(BigDecimal value) {
            addCriterion("PROPORTION =", value, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionNotEqualTo(BigDecimal value) {
            addCriterion("PROPORTION <>", value, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionGreaterThan(BigDecimal value) {
            addCriterion("PROPORTION >", value, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PROPORTION >=", value, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionLessThan(BigDecimal value) {
            addCriterion("PROPORTION <", value, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PROPORTION <=", value, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionIn(List<BigDecimal> values) {
            addCriterion("PROPORTION in", values, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionNotIn(List<BigDecimal> values) {
            addCriterion("PROPORTION not in", values, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROPORTION between", value1, value2, "proportion");
            return (Criteria) this;
        }

        public Criteria andProportionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROPORTION not between", value1, value2, "proportion");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andOperUserIsNull() {
            addCriterion("OPER_USER is null");
            return (Criteria) this;
        }

        public Criteria andOperUserIsNotNull() {
            addCriterion("OPER_USER is not null");
            return (Criteria) this;
        }

        public Criteria andOperUserEqualTo(String value) {
            addCriterion("OPER_USER =", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserNotEqualTo(String value) {
            addCriterion("OPER_USER <>", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserGreaterThan(String value) {
            addCriterion("OPER_USER >", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserGreaterThanOrEqualTo(String value) {
            addCriterion("OPER_USER >=", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserLessThan(String value) {
            addCriterion("OPER_USER <", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserLessThanOrEqualTo(String value) {
            addCriterion("OPER_USER <=", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserLike(String value) {
            addCriterion("OPER_USER like", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserNotLike(String value) {
            addCriterion("OPER_USER not like", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserIn(List<String> values) {
            addCriterion("OPER_USER in", values, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserNotIn(List<String> values) {
            addCriterion("OPER_USER not in", values, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserBetween(String value1, String value2) {
            addCriterion("OPER_USER between", value1, value2, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserNotBetween(String value1, String value2) {
            addCriterion("OPER_USER not between", value1, value2, "operUser");
            return (Criteria) this;
        }

        public Criteria andActivityValidIsNull() {
            addCriterion("ACTIVITY_VALID is null");
            return (Criteria) this;
        }

        public Criteria andActivityValidIsNotNull() {
            addCriterion("ACTIVITY_VALID is not null");
            return (Criteria) this;
        }

        public Criteria andActivityValidEqualTo(String value) {
            addCriterion("ACTIVITY_VALID =", value, "activityValid");
            return (Criteria) this;
        }

        public Criteria andActivityValidNotEqualTo(String value) {
            addCriterion("ACTIVITY_VALID <>", value, "activityValid");
            return (Criteria) this;
        }

        public Criteria andActivityValidGreaterThan(String value) {
            addCriterion("ACTIVITY_VALID >", value, "activityValid");
            return (Criteria) this;
        }

        public Criteria andActivityValidGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_VALID >=", value, "activityValid");
            return (Criteria) this;
        }

        public Criteria andActivityValidLessThan(String value) {
            addCriterion("ACTIVITY_VALID <", value, "activityValid");
            return (Criteria) this;
        }

        public Criteria andActivityValidLessThanOrEqualTo(String value) {
            addCriterion("ACTIVITY_VALID <=", value, "activityValid");
            return (Criteria) this;
        }

        public Criteria andActivityValidLike(String value) {
            addCriterion("ACTIVITY_VALID like", value, "activityValid");
            return (Criteria) this;
        }

        public Criteria andActivityValidNotLike(String value) {
            addCriterion("ACTIVITY_VALID not like", value, "activityValid");
            return (Criteria) this;
        }

        public Criteria andActivityValidIn(List<String> values) {
            addCriterion("ACTIVITY_VALID in", values, "activityValid");
            return (Criteria) this;
        }

        public Criteria andActivityValidNotIn(List<String> values) {
            addCriterion("ACTIVITY_VALID not in", values, "activityValid");
            return (Criteria) this;
        }

        public Criteria andActivityValidBetween(String value1, String value2) {
            addCriterion("ACTIVITY_VALID between", value1, value2, "activityValid");
            return (Criteria) this;
        }

        public Criteria andActivityValidNotBetween(String value1, String value2) {
            addCriterion("ACTIVITY_VALID not between", value1, value2, "activityValid");
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