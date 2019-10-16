package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ORemoveAccountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ORemoveAccountExample() {
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

        public Criteria andRmonthIsNull() {
            addCriterion("RMONTH is null");
            return (Criteria) this;
        }

        public Criteria andRmonthIsNotNull() {
            addCriterion("RMONTH is not null");
            return (Criteria) this;
        }

        public Criteria andRmonthEqualTo(Date value) {
            addCriterion("RMONTH =", value, "rmonth");
            return (Criteria) this;
        }

        public Criteria andRmonthNotEqualTo(Date value) {
            addCriterion("RMONTH <>", value, "rmonth");
            return (Criteria) this;
        }

        public Criteria andRmonthGreaterThan(Date value) {
            addCriterion("RMONTH >", value, "rmonth");
            return (Criteria) this;
        }

        public Criteria andRmonthGreaterThanOrEqualTo(Date value) {
            addCriterion("RMONTH >=", value, "rmonth");
            return (Criteria) this;
        }

        public Criteria andRmonthLessThan(Date value) {
            addCriterion("RMONTH <", value, "rmonth");
            return (Criteria) this;
        }

        public Criteria andRmonthLessThanOrEqualTo(Date value) {
            addCriterion("RMONTH <=", value, "rmonth");
            return (Criteria) this;
        }

        public Criteria andRmonthIn(List<Date> values) {
            addCriterion("RMONTH in", values, "rmonth");
            return (Criteria) this;
        }

        public Criteria andRmonthNotIn(List<Date> values) {
            addCriterion("RMONTH not in", values, "rmonth");
            return (Criteria) this;
        }

        public Criteria andRmonthBetween(Date value1, Date value2) {
            addCriterion("RMONTH between", value1, value2, "rmonth");
            return (Criteria) this;
        }

        public Criteria andRmonthNotBetween(Date value1, Date value2) {
            addCriterion("RMONTH not between", value1, value2, "rmonth");
            return (Criteria) this;
        }

        public Criteria andAgIdIsNull() {
            addCriterion("AG_ID is null");
            return (Criteria) this;
        }

        public Criteria andAgIdIsNotNull() {
            addCriterion("AG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAgIdEqualTo(String value) {
            addCriterion("AG_ID =", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdNotEqualTo(String value) {
            addCriterion("AG_ID <>", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdGreaterThan(String value) {
            addCriterion("AG_ID >", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdGreaterThanOrEqualTo(String value) {
            addCriterion("AG_ID >=", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdLessThan(String value) {
            addCriterion("AG_ID <", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdLessThanOrEqualTo(String value) {
            addCriterion("AG_ID <=", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdLike(String value) {
            addCriterion("AG_ID like", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdNotLike(String value) {
            addCriterion("AG_ID not like", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdIn(List<String> values) {
            addCriterion("AG_ID in", values, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdNotIn(List<String> values) {
            addCriterion("AG_ID not in", values, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdBetween(String value1, String value2) {
            addCriterion("AG_ID between", value1, value2, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdNotBetween(String value1, String value2) {
            addCriterion("AG_ID not between", value1, value2, "agId");
            return (Criteria) this;
        }

        public Criteria andAgNameIsNull() {
            addCriterion("AG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAgNameIsNotNull() {
            addCriterion("AG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAgNameEqualTo(String value) {
            addCriterion("AG_NAME =", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameNotEqualTo(String value) {
            addCriterion("AG_NAME <>", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameGreaterThan(String value) {
            addCriterion("AG_NAME >", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameGreaterThanOrEqualTo(String value) {
            addCriterion("AG_NAME >=", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameLessThan(String value) {
            addCriterion("AG_NAME <", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameLessThanOrEqualTo(String value) {
            addCriterion("AG_NAME <=", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameLike(String value) {
            addCriterion("AG_NAME like", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameNotLike(String value) {
            addCriterion("AG_NAME not like", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameIn(List<String> values) {
            addCriterion("AG_NAME in", values, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameNotIn(List<String> values) {
            addCriterion("AG_NAME not in", values, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameBetween(String value1, String value2) {
            addCriterion("AG_NAME between", value1, value2, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameNotBetween(String value1, String value2) {
            addCriterion("AG_NAME not between", value1, value2, "agName");
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

        public Criteria andMachinesAmountIsNull() {
            addCriterion("MACHINES_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andMachinesAmountIsNotNull() {
            addCriterion("MACHINES_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andMachinesAmountEqualTo(BigDecimal value) {
            addCriterion("MACHINES_AMOUNT =", value, "machinesAmount");
            return (Criteria) this;
        }

        public Criteria andMachinesAmountNotEqualTo(BigDecimal value) {
            addCriterion("MACHINES_AMOUNT <>", value, "machinesAmount");
            return (Criteria) this;
        }

        public Criteria andMachinesAmountGreaterThan(BigDecimal value) {
            addCriterion("MACHINES_AMOUNT >", value, "machinesAmount");
            return (Criteria) this;
        }

        public Criteria andMachinesAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MACHINES_AMOUNT >=", value, "machinesAmount");
            return (Criteria) this;
        }

        public Criteria andMachinesAmountLessThan(BigDecimal value) {
            addCriterion("MACHINES_AMOUNT <", value, "machinesAmount");
            return (Criteria) this;
        }

        public Criteria andMachinesAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MACHINES_AMOUNT <=", value, "machinesAmount");
            return (Criteria) this;
        }

        public Criteria andMachinesAmountIn(List<BigDecimal> values) {
            addCriterion("MACHINES_AMOUNT in", values, "machinesAmount");
            return (Criteria) this;
        }

        public Criteria andMachinesAmountNotIn(List<BigDecimal> values) {
            addCriterion("MACHINES_AMOUNT not in", values, "machinesAmount");
            return (Criteria) this;
        }

        public Criteria andMachinesAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MACHINES_AMOUNT between", value1, value2, "machinesAmount");
            return (Criteria) this;
        }

        public Criteria andMachinesAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MACHINES_AMOUNT not between", value1, value2, "machinesAmount");
            return (Criteria) this;
        }

        public Criteria andSubmitPersonIsNull() {
            addCriterion("SUBMIT_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andSubmitPersonIsNotNull() {
            addCriterion("SUBMIT_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitPersonEqualTo(String value) {
            addCriterion("SUBMIT_PERSON =", value, "submitPerson");
            return (Criteria) this;
        }

        public Criteria andSubmitPersonNotEqualTo(String value) {
            addCriterion("SUBMIT_PERSON <>", value, "submitPerson");
            return (Criteria) this;
        }

        public Criteria andSubmitPersonGreaterThan(String value) {
            addCriterion("SUBMIT_PERSON >", value, "submitPerson");
            return (Criteria) this;
        }

        public Criteria andSubmitPersonGreaterThanOrEqualTo(String value) {
            addCriterion("SUBMIT_PERSON >=", value, "submitPerson");
            return (Criteria) this;
        }

        public Criteria andSubmitPersonLessThan(String value) {
            addCriterion("SUBMIT_PERSON <", value, "submitPerson");
            return (Criteria) this;
        }

        public Criteria andSubmitPersonLessThanOrEqualTo(String value) {
            addCriterion("SUBMIT_PERSON <=", value, "submitPerson");
            return (Criteria) this;
        }

        public Criteria andSubmitPersonLike(String value) {
            addCriterion("SUBMIT_PERSON like", value, "submitPerson");
            return (Criteria) this;
        }

        public Criteria andSubmitPersonNotLike(String value) {
            addCriterion("SUBMIT_PERSON not like", value, "submitPerson");
            return (Criteria) this;
        }

        public Criteria andSubmitPersonIn(List<String> values) {
            addCriterion("SUBMIT_PERSON in", values, "submitPerson");
            return (Criteria) this;
        }

        public Criteria andSubmitPersonNotIn(List<String> values) {
            addCriterion("SUBMIT_PERSON not in", values, "submitPerson");
            return (Criteria) this;
        }

        public Criteria andSubmitPersonBetween(String value1, String value2) {
            addCriterion("SUBMIT_PERSON between", value1, value2, "submitPerson");
            return (Criteria) this;
        }

        public Criteria andSubmitPersonNotBetween(String value1, String value2) {
            addCriterion("SUBMIT_PERSON not between", value1, value2, "submitPerson");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIsNull() {
            addCriterion("SUBMIT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIsNotNull() {
            addCriterion("SUBMIT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeEqualTo(String value) {
            addCriterion("SUBMIT_TIME =", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotEqualTo(String value) {
            addCriterion("SUBMIT_TIME <>", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeGreaterThan(String value) {
            addCriterion("SUBMIT_TIME >", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeGreaterThanOrEqualTo(String value) {
            addCriterion("SUBMIT_TIME >=", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLessThan(String value) {
            addCriterion("SUBMIT_TIME <", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLessThanOrEqualTo(String value) {
            addCriterion("SUBMIT_TIME <=", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLike(String value) {
            addCriterion("SUBMIT_TIME like", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotLike(String value) {
            addCriterion("SUBMIT_TIME not like", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIn(List<String> values) {
            addCriterion("SUBMIT_TIME in", values, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotIn(List<String> values) {
            addCriterion("SUBMIT_TIME not in", values, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeBetween(String value1, String value2) {
            addCriterion("SUBMIT_TIME between", value1, value2, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotBetween(String value1, String value2) {
            addCriterion("SUBMIT_TIME not between", value1, value2, "submitTime");
            return (Criteria) this;
        }

        public Criteria andPayMethodIsNull() {
            addCriterion("PAY_METHOD is null");
            return (Criteria) this;
        }

        public Criteria andPayMethodIsNotNull() {
            addCriterion("PAY_METHOD is not null");
            return (Criteria) this;
        }

        public Criteria andPayMethodEqualTo(String value) {
            addCriterion("PAY_METHOD =", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodNotEqualTo(String value) {
            addCriterion("PAY_METHOD <>", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodGreaterThan(String value) {
            addCriterion("PAY_METHOD >", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_METHOD >=", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodLessThan(String value) {
            addCriterion("PAY_METHOD <", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodLessThanOrEqualTo(String value) {
            addCriterion("PAY_METHOD <=", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodLike(String value) {
            addCriterion("PAY_METHOD like", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodNotLike(String value) {
            addCriterion("PAY_METHOD not like", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodIn(List<String> values) {
            addCriterion("PAY_METHOD in", values, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodNotIn(List<String> values) {
            addCriterion("PAY_METHOD not in", values, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodBetween(String value1, String value2) {
            addCriterion("PAY_METHOD between", value1, value2, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodNotBetween(String value1, String value2) {
            addCriterion("PAY_METHOD not between", value1, value2, "payMethod");
            return (Criteria) this;
        }

        public Criteria andRamountIsNull() {
            addCriterion("RAMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andRamountIsNotNull() {
            addCriterion("RAMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andRamountEqualTo(BigDecimal value) {
            addCriterion("RAMOUNT =", value, "ramount");
            return (Criteria) this;
        }

        public Criteria andRamountNotEqualTo(BigDecimal value) {
            addCriterion("RAMOUNT <>", value, "ramount");
            return (Criteria) this;
        }

        public Criteria andRamountGreaterThan(BigDecimal value) {
            addCriterion("RAMOUNT >", value, "ramount");
            return (Criteria) this;
        }

        public Criteria andRamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RAMOUNT >=", value, "ramount");
            return (Criteria) this;
        }

        public Criteria andRamountLessThan(BigDecimal value) {
            addCriterion("RAMOUNT <", value, "ramount");
            return (Criteria) this;
        }

        public Criteria andRamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RAMOUNT <=", value, "ramount");
            return (Criteria) this;
        }

        public Criteria andRamountIn(List<BigDecimal> values) {
            addCriterion("RAMOUNT in", values, "ramount");
            return (Criteria) this;
        }

        public Criteria andRamountNotIn(List<BigDecimal> values) {
            addCriterion("RAMOUNT not in", values, "ramount");
            return (Criteria) this;
        }

        public Criteria andRamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RAMOUNT between", value1, value2, "ramount");
            return (Criteria) this;
        }

        public Criteria andRamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RAMOUNT not between", value1, value2, "ramount");
            return (Criteria) this;
        }

        public Criteria andRealRamountIsNull() {
            addCriterion("REAL_RAMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andRealRamountIsNotNull() {
            addCriterion("REAL_RAMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andRealRamountEqualTo(BigDecimal value) {
            addCriterion("REAL_RAMOUNT =", value, "realRamount");
            return (Criteria) this;
        }

        public Criteria andRealRamountNotEqualTo(BigDecimal value) {
            addCriterion("REAL_RAMOUNT <>", value, "realRamount");
            return (Criteria) this;
        }

        public Criteria andRealRamountGreaterThan(BigDecimal value) {
            addCriterion("REAL_RAMOUNT >", value, "realRamount");
            return (Criteria) this;
        }

        public Criteria andRealRamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_RAMOUNT >=", value, "realRamount");
            return (Criteria) this;
        }

        public Criteria andRealRamountLessThan(BigDecimal value) {
            addCriterion("REAL_RAMOUNT <", value, "realRamount");
            return (Criteria) this;
        }

        public Criteria andRealRamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_RAMOUNT <=", value, "realRamount");
            return (Criteria) this;
        }

        public Criteria andRealRamountIn(List<BigDecimal> values) {
            addCriterion("REAL_RAMOUNT in", values, "realRamount");
            return (Criteria) this;
        }

        public Criteria andRealRamountNotIn(List<BigDecimal> values) {
            addCriterion("REAL_RAMOUNT not in", values, "realRamount");
            return (Criteria) this;
        }

        public Criteria andRealRamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_RAMOUNT between", value1, value2, "realRamount");
            return (Criteria) this;
        }

        public Criteria andRealRamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_RAMOUNT not between", value1, value2, "realRamount");
            return (Criteria) this;
        }

        public Criteria andRstatusIsNull() {
            addCriterion("RSTATUS is null");
            return (Criteria) this;
        }

        public Criteria andRstatusIsNotNull() {
            addCriterion("RSTATUS is not null");
            return (Criteria) this;
        }

        public Criteria andRstatusEqualTo(BigDecimal value) {
            addCriterion("RSTATUS =", value, "rstatus");
            return (Criteria) this;
        }

        public Criteria andRstatusNotEqualTo(BigDecimal value) {
            addCriterion("RSTATUS <>", value, "rstatus");
            return (Criteria) this;
        }

        public Criteria andRstatusGreaterThan(BigDecimal value) {
            addCriterion("RSTATUS >", value, "rstatus");
            return (Criteria) this;
        }

        public Criteria andRstatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RSTATUS >=", value, "rstatus");
            return (Criteria) this;
        }

        public Criteria andRstatusLessThan(BigDecimal value) {
            addCriterion("RSTATUS <", value, "rstatus");
            return (Criteria) this;
        }

        public Criteria andRstatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RSTATUS <=", value, "rstatus");
            return (Criteria) this;
        }

        public Criteria andRstatusIn(List<BigDecimal> values) {
            addCriterion("RSTATUS in", values, "rstatus");
            return (Criteria) this;
        }

        public Criteria andRstatusNotIn(List<BigDecimal> values) {
            addCriterion("RSTATUS not in", values, "rstatus");
            return (Criteria) this;
        }

        public Criteria andRstatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RSTATUS between", value1, value2, "rstatus");
            return (Criteria) this;
        }

        public Criteria andRstatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RSTATUS not between", value1, value2, "rstatus");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNull() {
            addCriterion("FINISH_TIME is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("FINISH_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(Date value) {
            addCriterion("FINISH_TIME =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(Date value) {
            addCriterion("FINISH_TIME <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(Date value) {
            addCriterion("FINISH_TIME >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("FINISH_TIME >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(Date value) {
            addCriterion("FINISH_TIME <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(Date value) {
            addCriterion("FINISH_TIME <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<Date> values) {
            addCriterion("FINISH_TIME in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<Date> values) {
            addCriterion("FINISH_TIME not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(Date value1, Date value2) {
            addCriterion("FINISH_TIME between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(Date value1, Date value2) {
            addCriterion("FINISH_TIME not between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andBatchNumIsNull() {
            addCriterion("BATCH_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBatchNumIsNotNull() {
            addCriterion("BATCH_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNumEqualTo(String value) {
            addCriterion("BATCH_NUM =", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotEqualTo(String value) {
            addCriterion("BATCH_NUM <>", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumGreaterThan(String value) {
            addCriterion("BATCH_NUM >", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumGreaterThanOrEqualTo(String value) {
            addCriterion("BATCH_NUM >=", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumLessThan(String value) {
            addCriterion("BATCH_NUM <", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumLessThanOrEqualTo(String value) {
            addCriterion("BATCH_NUM <=", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumLike(String value) {
            addCriterion("BATCH_NUM like", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotLike(String value) {
            addCriterion("BATCH_NUM not like", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumIn(List<String> values) {
            addCriterion("BATCH_NUM in", values, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotIn(List<String> values) {
            addCriterion("BATCH_NUM not in", values, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumBetween(String value1, String value2) {
            addCriterion("BATCH_NUM between", value1, value2, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotBetween(String value1, String value2) {
            addCriterion("BATCH_NUM not between", value1, value2, "batchNum");
            return (Criteria) this;
        }

        public Criteria andRtypeIsNull() {
            addCriterion("RTYPE is null");
            return (Criteria) this;
        }

        public Criteria andRtypeIsNotNull() {
            addCriterion("RTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andRtypeEqualTo(BigDecimal value) {
            addCriterion("RTYPE =", value, "rtype");
            return (Criteria) this;
        }

        public Criteria andRtypeNotEqualTo(BigDecimal value) {
            addCriterion("RTYPE <>", value, "rtype");
            return (Criteria) this;
        }

        public Criteria andRtypeGreaterThan(BigDecimal value) {
            addCriterion("RTYPE >", value, "rtype");
            return (Criteria) this;
        }

        public Criteria andRtypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RTYPE >=", value, "rtype");
            return (Criteria) this;
        }

        public Criteria andRtypeLessThan(BigDecimal value) {
            addCriterion("RTYPE <", value, "rtype");
            return (Criteria) this;
        }

        public Criteria andRtypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RTYPE <=", value, "rtype");
            return (Criteria) this;
        }

        public Criteria andRtypeIn(List<BigDecimal> values) {
            addCriterion("RTYPE in", values, "rtype");
            return (Criteria) this;
        }

        public Criteria andRtypeNotIn(List<BigDecimal> values) {
            addCriterion("RTYPE not in", values, "rtype");
            return (Criteria) this;
        }

        public Criteria andRtypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RTYPE between", value1, value2, "rtype");
            return (Criteria) this;
        }

        public Criteria andRtypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RTYPE not between", value1, value2, "rtype");
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