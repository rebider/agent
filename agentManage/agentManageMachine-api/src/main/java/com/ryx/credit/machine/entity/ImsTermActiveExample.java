package com.ryx.credit.machine.entity;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ImsTermActiveExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ImsTermActiveExample() {
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

        public Criteria andPosSnIsNull() {
            addCriterion("POS_SN is null");
            return (Criteria) this;
        }

        public Criteria andPosSnIsNotNull() {
            addCriterion("POS_SN is not null");
            return (Criteria) this;
        }

        public Criteria andPosSnEqualTo(String value) {
            addCriterion("POS_SN =", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnNotEqualTo(String value) {
            addCriterion("POS_SN <>", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnGreaterThan(String value) {
            addCriterion("POS_SN >", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnGreaterThanOrEqualTo(String value) {
            addCriterion("POS_SN >=", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnLessThan(String value) {
            addCriterion("POS_SN <", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnLessThanOrEqualTo(String value) {
            addCriterion("POS_SN <=", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnLike(String value) {
            addCriterion("POS_SN like", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnNotLike(String value) {
            addCriterion("POS_SN not like", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnIn(List<String> values) {
            addCriterion("POS_SN in", values, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnNotIn(List<String> values) {
            addCriterion("POS_SN not in", values, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnBetween(String value1, String value2) {
            addCriterion("POS_SN between", value1, value2, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnNotBetween(String value1, String value2) {
            addCriterion("POS_SN not between", value1, value2, "posSn");
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

        public Criteria andTermIdIsNull() {
            addCriterion("TERM_ID is null");
            return (Criteria) this;
        }

        public Criteria andTermIdIsNotNull() {
            addCriterion("TERM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTermIdEqualTo(String value) {
            addCriterion("TERM_ID =", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdNotEqualTo(String value) {
            addCriterion("TERM_ID <>", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdGreaterThan(String value) {
            addCriterion("TERM_ID >", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdGreaterThanOrEqualTo(String value) {
            addCriterion("TERM_ID >=", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdLessThan(String value) {
            addCriterion("TERM_ID <", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdLessThanOrEqualTo(String value) {
            addCriterion("TERM_ID <=", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdLike(String value) {
            addCriterion("TERM_ID like", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdNotLike(String value) {
            addCriterion("TERM_ID not like", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdIn(List<String> values) {
            addCriterion("TERM_ID in", values, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdNotIn(List<String> values) {
            addCriterion("TERM_ID not in", values, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdBetween(String value1, String value2) {
            addCriterion("TERM_ID between", value1, value2, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdNotBetween(String value1, String value2) {
            addCriterion("TERM_ID not between", value1, value2, "termId");
            return (Criteria) this;
        }

        public Criteria andMachineIdIsNull() {
            addCriterion("MACHINE_ID is null");
            return (Criteria) this;
        }

        public Criteria andMachineIdIsNotNull() {
            addCriterion("MACHINE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMachineIdEqualTo(String value) {
            addCriterion("MACHINE_ID =", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdNotEqualTo(String value) {
            addCriterion("MACHINE_ID <>", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdGreaterThan(String value) {
            addCriterion("MACHINE_ID >", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdGreaterThanOrEqualTo(String value) {
            addCriterion("MACHINE_ID >=", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdLessThan(String value) {
            addCriterion("MACHINE_ID <", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdLessThanOrEqualTo(String value) {
            addCriterion("MACHINE_ID <=", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdLike(String value) {
            addCriterion("MACHINE_ID like", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdNotLike(String value) {
            addCriterion("MACHINE_ID not like", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdIn(List<String> values) {
            addCriterion("MACHINE_ID in", values, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdNotIn(List<String> values) {
            addCriterion("MACHINE_ID not in", values, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdBetween(String value1, String value2) {
            addCriterion("MACHINE_ID between", value1, value2, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdNotBetween(String value1, String value2) {
            addCriterion("MACHINE_ID not between", value1, value2, "machineId");
            return (Criteria) this;
        }

        public Criteria andActiveTimeIsNull() {
            addCriterion("ACTIVE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andActiveTimeIsNotNull() {
            addCriterion("ACTIVE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andActiveTimeEqualTo(String value) {
            addCriterion("ACTIVE_TIME =", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeNotEqualTo(String value) {
            addCriterion("ACTIVE_TIME <>", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeGreaterThan(String value) {
            addCriterion("ACTIVE_TIME >", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIVE_TIME >=", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeLessThan(String value) {
            addCriterion("ACTIVE_TIME <", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeLessThanOrEqualTo(String value) {
            addCriterion("ACTIVE_TIME <=", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeLike(String value) {
            addCriterion("ACTIVE_TIME like", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeNotLike(String value) {
            addCriterion("ACTIVE_TIME not like", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeIn(List<String> values) {
            addCriterion("ACTIVE_TIME in", values, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeNotIn(List<String> values) {
            addCriterion("ACTIVE_TIME not in", values, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeBetween(String value1, String value2) {
            addCriterion("ACTIVE_TIME between", value1, value2, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeNotBetween(String value1, String value2) {
            addCriterion("ACTIVE_TIME not between", value1, value2, "activeTime");
            return (Criteria) this;
        }

        public Criteria andAmtIsNull() {
            addCriterion("AMT is null");
            return (Criteria) this;
        }

        public Criteria andAmtIsNotNull() {
            addCriterion("AMT is not null");
            return (Criteria) this;
        }

        public Criteria andAmtEqualTo(BigDecimal value) {
            addCriterion("AMT =", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotEqualTo(BigDecimal value) {
            addCriterion("AMT <>", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtGreaterThan(BigDecimal value) {
            addCriterion("AMT >", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AMT >=", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtLessThan(BigDecimal value) {
            addCriterion("AMT <", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AMT <=", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtIn(List<BigDecimal> values) {
            addCriterion("AMT in", values, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotIn(List<BigDecimal> values) {
            addCriterion("AMT not in", values, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AMT between", value1, value2, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AMT not between", value1, value2, "amt");
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

        public Criteria andUpdateTimeEqualTo(String value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(String value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(String value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(String value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLike(String value) {
            addCriterion("UPDATE_TIME like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotLike(String value) {
            addCriterion("UPDATE_TIME not like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<String> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<String> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(String value1, String value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(String value1, String value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("STATUS like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("STATUS not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andAmtTimeIsNull() {
            addCriterion("AMT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andAmtTimeIsNotNull() {
            addCriterion("AMT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andAmtTimeEqualTo(String value) {
            addCriterion("AMT_TIME =", value, "amtTime");
            return (Criteria) this;
        }

        public Criteria andAmtTimeNotEqualTo(String value) {
            addCriterion("AMT_TIME <>", value, "amtTime");
            return (Criteria) this;
        }

        public Criteria andAmtTimeGreaterThan(String value) {
            addCriterion("AMT_TIME >", value, "amtTime");
            return (Criteria) this;
        }

        public Criteria andAmtTimeGreaterThanOrEqualTo(String value) {
            addCriterion("AMT_TIME >=", value, "amtTime");
            return (Criteria) this;
        }

        public Criteria andAmtTimeLessThan(String value) {
            addCriterion("AMT_TIME <", value, "amtTime");
            return (Criteria) this;
        }

        public Criteria andAmtTimeLessThanOrEqualTo(String value) {
            addCriterion("AMT_TIME <=", value, "amtTime");
            return (Criteria) this;
        }

        public Criteria andAmtTimeLike(String value) {
            addCriterion("AMT_TIME like", value, "amtTime");
            return (Criteria) this;
        }

        public Criteria andAmtTimeNotLike(String value) {
            addCriterion("AMT_TIME not like", value, "amtTime");
            return (Criteria) this;
        }

        public Criteria andAmtTimeIn(List<String> values) {
            addCriterion("AMT_TIME in", values, "amtTime");
            return (Criteria) this;
        }

        public Criteria andAmtTimeNotIn(List<String> values) {
            addCriterion("AMT_TIME not in", values, "amtTime");
            return (Criteria) this;
        }

        public Criteria andAmtTimeBetween(String value1, String value2) {
            addCriterion("AMT_TIME between", value1, value2, "amtTime");
            return (Criteria) this;
        }

        public Criteria andAmtTimeNotBetween(String value1, String value2) {
            addCriterion("AMT_TIME not between", value1, value2, "amtTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIsNull() {
            addCriterion("INSERT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIsNotNull() {
            addCriterion("INSERT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andInsertTimeEqualTo(String value) {
            addCriterion("INSERT_TIME =", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotEqualTo(String value) {
            addCriterion("INSERT_TIME <>", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeGreaterThan(String value) {
            addCriterion("INSERT_TIME >", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeGreaterThanOrEqualTo(String value) {
            addCriterion("INSERT_TIME >=", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeLessThan(String value) {
            addCriterion("INSERT_TIME <", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeLessThanOrEqualTo(String value) {
            addCriterion("INSERT_TIME <=", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeLike(String value) {
            addCriterion("INSERT_TIME like", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotLike(String value) {
            addCriterion("INSERT_TIME not like", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIn(List<String> values) {
            addCriterion("INSERT_TIME in", values, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotIn(List<String> values) {
            addCriterion("INSERT_TIME not in", values, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeBetween(String value1, String value2) {
            addCriterion("INSERT_TIME between", value1, value2, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotBetween(String value1, String value2) {
            addCriterion("INSERT_TIME not between", value1, value2, "insertTime");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNull() {
            addCriterion("SERIAL_NO is null");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNotNull() {
            addCriterion("SERIAL_NO is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNoEqualTo(String value) {
            addCriterion("SERIAL_NO =", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotEqualTo(String value) {
            addCriterion("SERIAL_NO <>", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThan(String value) {
            addCriterion("SERIAL_NO >", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThanOrEqualTo(String value) {
            addCriterion("SERIAL_NO >=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThan(String value) {
            addCriterion("SERIAL_NO <", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThanOrEqualTo(String value) {
            addCriterion("SERIAL_NO <=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLike(String value) {
            addCriterion("SERIAL_NO like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotLike(String value) {
            addCriterion("SERIAL_NO not like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoIn(List<String> values) {
            addCriterion("SERIAL_NO in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotIn(List<String> values) {
            addCriterion("SERIAL_NO not in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoBetween(String value1, String value2) {
            addCriterion("SERIAL_NO between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotBetween(String value1, String value2) {
            addCriterion("SERIAL_NO not between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andCkStatusIsNull() {
            addCriterion("CK_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andCkStatusIsNotNull() {
            addCriterion("CK_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andCkStatusEqualTo(String value) {
            addCriterion("CK_STATUS =", value, "ckStatus");
            return (Criteria) this;
        }

        public Criteria andCkStatusNotEqualTo(String value) {
            addCriterion("CK_STATUS <>", value, "ckStatus");
            return (Criteria) this;
        }

        public Criteria andCkStatusGreaterThan(String value) {
            addCriterion("CK_STATUS >", value, "ckStatus");
            return (Criteria) this;
        }

        public Criteria andCkStatusGreaterThanOrEqualTo(String value) {
            addCriterion("CK_STATUS >=", value, "ckStatus");
            return (Criteria) this;
        }

        public Criteria andCkStatusLessThan(String value) {
            addCriterion("CK_STATUS <", value, "ckStatus");
            return (Criteria) this;
        }

        public Criteria andCkStatusLessThanOrEqualTo(String value) {
            addCriterion("CK_STATUS <=", value, "ckStatus");
            return (Criteria) this;
        }

        public Criteria andCkStatusLike(String value) {
            addCriterion("CK_STATUS like", value, "ckStatus");
            return (Criteria) this;
        }

        public Criteria andCkStatusNotLike(String value) {
            addCriterion("CK_STATUS not like", value, "ckStatus");
            return (Criteria) this;
        }

        public Criteria andCkStatusIn(List<String> values) {
            addCriterion("CK_STATUS in", values, "ckStatus");
            return (Criteria) this;
        }

        public Criteria andCkStatusNotIn(List<String> values) {
            addCriterion("CK_STATUS not in", values, "ckStatus");
            return (Criteria) this;
        }

        public Criteria andCkStatusBetween(String value1, String value2) {
            addCriterion("CK_STATUS between", value1, value2, "ckStatus");
            return (Criteria) this;
        }

        public Criteria andCkStatusNotBetween(String value1, String value2) {
            addCriterion("CK_STATUS not between", value1, value2, "ckStatus");
            return (Criteria) this;
        }

        public Criteria andCkResultIsNull() {
            addCriterion("CK_RESULT is null");
            return (Criteria) this;
        }

        public Criteria andCkResultIsNotNull() {
            addCriterion("CK_RESULT is not null");
            return (Criteria) this;
        }

        public Criteria andCkResultEqualTo(String value) {
            addCriterion("CK_RESULT =", value, "ckResult");
            return (Criteria) this;
        }

        public Criteria andCkResultNotEqualTo(String value) {
            addCriterion("CK_RESULT <>", value, "ckResult");
            return (Criteria) this;
        }

        public Criteria andCkResultGreaterThan(String value) {
            addCriterion("CK_RESULT >", value, "ckResult");
            return (Criteria) this;
        }

        public Criteria andCkResultGreaterThanOrEqualTo(String value) {
            addCriterion("CK_RESULT >=", value, "ckResult");
            return (Criteria) this;
        }

        public Criteria andCkResultLessThan(String value) {
            addCriterion("CK_RESULT <", value, "ckResult");
            return (Criteria) this;
        }

        public Criteria andCkResultLessThanOrEqualTo(String value) {
            addCriterion("CK_RESULT <=", value, "ckResult");
            return (Criteria) this;
        }

        public Criteria andCkResultLike(String value) {
            addCriterion("CK_RESULT like", value, "ckResult");
            return (Criteria) this;
        }

        public Criteria andCkResultNotLike(String value) {
            addCriterion("CK_RESULT not like", value, "ckResult");
            return (Criteria) this;
        }

        public Criteria andCkResultIn(List<String> values) {
            addCriterion("CK_RESULT in", values, "ckResult");
            return (Criteria) this;
        }

        public Criteria andCkResultNotIn(List<String> values) {
            addCriterion("CK_RESULT not in", values, "ckResult");
            return (Criteria) this;
        }

        public Criteria andCkResultBetween(String value1, String value2) {
            addCriterion("CK_RESULT between", value1, value2, "ckResult");
            return (Criteria) this;
        }

        public Criteria andCkResultNotBetween(String value1, String value2) {
            addCriterion("CK_RESULT not between", value1, value2, "ckResult");
            return (Criteria) this;
        }

        public Criteria andPaymentCardIsNull() {
            addCriterion("PAYMENT_CARD is null");
            return (Criteria) this;
        }

        public Criteria andPaymentCardIsNotNull() {
            addCriterion("PAYMENT_CARD is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentCardEqualTo(String value) {
            addCriterion("PAYMENT_CARD =", value, "paymentCard");
            return (Criteria) this;
        }

        public Criteria andPaymentCardNotEqualTo(String value) {
            addCriterion("PAYMENT_CARD <>", value, "paymentCard");
            return (Criteria) this;
        }

        public Criteria andPaymentCardGreaterThan(String value) {
            addCriterion("PAYMENT_CARD >", value, "paymentCard");
            return (Criteria) this;
        }

        public Criteria andPaymentCardGreaterThanOrEqualTo(String value) {
            addCriterion("PAYMENT_CARD >=", value, "paymentCard");
            return (Criteria) this;
        }

        public Criteria andPaymentCardLessThan(String value) {
            addCriterion("PAYMENT_CARD <", value, "paymentCard");
            return (Criteria) this;
        }

        public Criteria andPaymentCardLessThanOrEqualTo(String value) {
            addCriterion("PAYMENT_CARD <=", value, "paymentCard");
            return (Criteria) this;
        }

        public Criteria andPaymentCardLike(String value) {
            addCriterion("PAYMENT_CARD like", value, "paymentCard");
            return (Criteria) this;
        }

        public Criteria andPaymentCardNotLike(String value) {
            addCriterion("PAYMENT_CARD not like", value, "paymentCard");
            return (Criteria) this;
        }

        public Criteria andPaymentCardIn(List<String> values) {
            addCriterion("PAYMENT_CARD in", values, "paymentCard");
            return (Criteria) this;
        }

        public Criteria andPaymentCardNotIn(List<String> values) {
            addCriterion("PAYMENT_CARD not in", values, "paymentCard");
            return (Criteria) this;
        }

        public Criteria andPaymentCardBetween(String value1, String value2) {
            addCriterion("PAYMENT_CARD between", value1, value2, "paymentCard");
            return (Criteria) this;
        }

        public Criteria andPaymentCardNotBetween(String value1, String value2) {
            addCriterion("PAYMENT_CARD not between", value1, value2, "paymentCard");
            return (Criteria) this;
        }

        public Criteria andPaymentOpenBankIsNull() {
            addCriterion("PAYMENT_OPEN_BANK is null");
            return (Criteria) this;
        }

        public Criteria andPaymentOpenBankIsNotNull() {
            addCriterion("PAYMENT_OPEN_BANK is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentOpenBankEqualTo(String value) {
            addCriterion("PAYMENT_OPEN_BANK =", value, "paymentOpenBank");
            return (Criteria) this;
        }

        public Criteria andPaymentOpenBankNotEqualTo(String value) {
            addCriterion("PAYMENT_OPEN_BANK <>", value, "paymentOpenBank");
            return (Criteria) this;
        }

        public Criteria andPaymentOpenBankGreaterThan(String value) {
            addCriterion("PAYMENT_OPEN_BANK >", value, "paymentOpenBank");
            return (Criteria) this;
        }

        public Criteria andPaymentOpenBankGreaterThanOrEqualTo(String value) {
            addCriterion("PAYMENT_OPEN_BANK >=", value, "paymentOpenBank");
            return (Criteria) this;
        }

        public Criteria andPaymentOpenBankLessThan(String value) {
            addCriterion("PAYMENT_OPEN_BANK <", value, "paymentOpenBank");
            return (Criteria) this;
        }

        public Criteria andPaymentOpenBankLessThanOrEqualTo(String value) {
            addCriterion("PAYMENT_OPEN_BANK <=", value, "paymentOpenBank");
            return (Criteria) this;
        }

        public Criteria andPaymentOpenBankLike(String value) {
            addCriterion("PAYMENT_OPEN_BANK like", value, "paymentOpenBank");
            return (Criteria) this;
        }

        public Criteria andPaymentOpenBankNotLike(String value) {
            addCriterion("PAYMENT_OPEN_BANK not like", value, "paymentOpenBank");
            return (Criteria) this;
        }

        public Criteria andPaymentOpenBankIn(List<String> values) {
            addCriterion("PAYMENT_OPEN_BANK in", values, "paymentOpenBank");
            return (Criteria) this;
        }

        public Criteria andPaymentOpenBankNotIn(List<String> values) {
            addCriterion("PAYMENT_OPEN_BANK not in", values, "paymentOpenBank");
            return (Criteria) this;
        }

        public Criteria andPaymentOpenBankBetween(String value1, String value2) {
            addCriterion("PAYMENT_OPEN_BANK between", value1, value2, "paymentOpenBank");
            return (Criteria) this;
        }

        public Criteria andPaymentOpenBankNotBetween(String value1, String value2) {
            addCriterion("PAYMENT_OPEN_BANK not between", value1, value2, "paymentOpenBank");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeIsNull() {
            addCriterion("PAYMENT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeIsNotNull() {
            addCriterion("PAYMENT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeEqualTo(String value) {
            addCriterion("PAYMENT_TIME =", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeNotEqualTo(String value) {
            addCriterion("PAYMENT_TIME <>", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeGreaterThan(String value) {
            addCriterion("PAYMENT_TIME >", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeGreaterThanOrEqualTo(String value) {
            addCriterion("PAYMENT_TIME >=", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeLessThan(String value) {
            addCriterion("PAYMENT_TIME <", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeLessThanOrEqualTo(String value) {
            addCriterion("PAYMENT_TIME <=", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeLike(String value) {
            addCriterion("PAYMENT_TIME like", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeNotLike(String value) {
            addCriterion("PAYMENT_TIME not like", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeIn(List<String> values) {
            addCriterion("PAYMENT_TIME in", values, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeNotIn(List<String> values) {
            addCriterion("PAYMENT_TIME not in", values, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeBetween(String value1, String value2) {
            addCriterion("PAYMENT_TIME between", value1, value2, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeNotBetween(String value1, String value2) {
            addCriterion("PAYMENT_TIME not between", value1, value2, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceIsNull() {
            addCriterion("POS_SPE_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceIsNotNull() {
            addCriterion("POS_SPE_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceEqualTo(BigDecimal value) {
            addCriterion("POS_SPE_PRICE =", value, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceNotEqualTo(BigDecimal value) {
            addCriterion("POS_SPE_PRICE <>", value, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceGreaterThan(BigDecimal value) {
            addCriterion("POS_SPE_PRICE >", value, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_SPE_PRICE >=", value, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceLessThan(BigDecimal value) {
            addCriterion("POS_SPE_PRICE <", value, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POS_SPE_PRICE <=", value, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceIn(List<BigDecimal> values) {
            addCriterion("POS_SPE_PRICE in", values, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceNotIn(List<BigDecimal> values) {
            addCriterion("POS_SPE_PRICE not in", values, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_SPE_PRICE between", value1, value2, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andPosSpePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POS_SPE_PRICE not between", value1, value2, "posSpePrice");
            return (Criteria) this;
        }

        public Criteria andStandAmtIsNull() {
            addCriterion("STAND_AMT is null");
            return (Criteria) this;
        }

        public Criteria andStandAmtIsNotNull() {
            addCriterion("STAND_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andStandAmtEqualTo(BigDecimal value) {
            addCriterion("STAND_AMT =", value, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtNotEqualTo(BigDecimal value) {
            addCriterion("STAND_AMT <>", value, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtGreaterThan(BigDecimal value) {
            addCriterion("STAND_AMT >", value, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STAND_AMT >=", value, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtLessThan(BigDecimal value) {
            addCriterion("STAND_AMT <", value, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STAND_AMT <=", value, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtIn(List<BigDecimal> values) {
            addCriterion("STAND_AMT in", values, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtNotIn(List<BigDecimal> values) {
            addCriterion("STAND_AMT not in", values, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STAND_AMT between", value1, value2, "standAmt");
            return (Criteria) this;
        }

        public Criteria andStandAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STAND_AMT not between", value1, value2, "standAmt");
            return (Criteria) this;
        }

        public Criteria andBackTypeIsNull() {
            addCriterion("BACK_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBackTypeIsNotNull() {
            addCriterion("BACK_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBackTypeEqualTo(String value) {
            addCriterion("BACK_TYPE =", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeNotEqualTo(String value) {
            addCriterion("BACK_TYPE <>", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeGreaterThan(String value) {
            addCriterion("BACK_TYPE >", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeGreaterThanOrEqualTo(String value) {
            addCriterion("BACK_TYPE >=", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeLessThan(String value) {
            addCriterion("BACK_TYPE <", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeLessThanOrEqualTo(String value) {
            addCriterion("BACK_TYPE <=", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeLike(String value) {
            addCriterion("BACK_TYPE like", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeNotLike(String value) {
            addCriterion("BACK_TYPE not like", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeIn(List<String> values) {
            addCriterion("BACK_TYPE in", values, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeNotIn(List<String> values) {
            addCriterion("BACK_TYPE not in", values, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeBetween(String value1, String value2) {
            addCriterion("BACK_TYPE between", value1, value2, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeNotBetween(String value1, String value2) {
            addCriterion("BACK_TYPE not between", value1, value2, "backType");
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