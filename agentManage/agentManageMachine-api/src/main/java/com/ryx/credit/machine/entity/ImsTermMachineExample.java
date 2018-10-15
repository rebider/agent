package com.ryx.credit.machine.entity;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ImsTermMachineExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ImsTermMachineExample() {
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

        public Criteria andModelIsNull() {
            addCriterion("MODEL is null");
            return (Criteria) this;
        }

        public Criteria andModelIsNotNull() {
            addCriterion("MODEL is not null");
            return (Criteria) this;
        }

        public Criteria andModelEqualTo(String value) {
            addCriterion("MODEL =", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotEqualTo(String value) {
            addCriterion("MODEL <>", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThan(String value) {
            addCriterion("MODEL >", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThanOrEqualTo(String value) {
            addCriterion("MODEL >=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThan(String value) {
            addCriterion("MODEL <", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThanOrEqualTo(String value) {
            addCriterion("MODEL <=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLike(String value) {
            addCriterion("MODEL like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotLike(String value) {
            addCriterion("MODEL not like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelIn(List<String> values) {
            addCriterion("MODEL in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotIn(List<String> values) {
            addCriterion("MODEL not in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelBetween(String value1, String value2) {
            addCriterion("MODEL between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotBetween(String value1, String value2) {
            addCriterion("MODEL not between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("PRICE is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("PRICE =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("PRICE <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("PRICE >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRICE >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("PRICE <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRICE <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("PRICE in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("PRICE not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRICE between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRICE not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andMinOrdQtyIsNull() {
            addCriterion("MIN_ORD_QTY is null");
            return (Criteria) this;
        }

        public Criteria andMinOrdQtyIsNotNull() {
            addCriterion("MIN_ORD_QTY is not null");
            return (Criteria) this;
        }

        public Criteria andMinOrdQtyEqualTo(BigDecimal value) {
            addCriterion("MIN_ORD_QTY =", value, "minOrdQty");
            return (Criteria) this;
        }

        public Criteria andMinOrdQtyNotEqualTo(BigDecimal value) {
            addCriterion("MIN_ORD_QTY <>", value, "minOrdQty");
            return (Criteria) this;
        }

        public Criteria andMinOrdQtyGreaterThan(BigDecimal value) {
            addCriterion("MIN_ORD_QTY >", value, "minOrdQty");
            return (Criteria) this;
        }

        public Criteria andMinOrdQtyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MIN_ORD_QTY >=", value, "minOrdQty");
            return (Criteria) this;
        }

        public Criteria andMinOrdQtyLessThan(BigDecimal value) {
            addCriterion("MIN_ORD_QTY <", value, "minOrdQty");
            return (Criteria) this;
        }

        public Criteria andMinOrdQtyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MIN_ORD_QTY <=", value, "minOrdQty");
            return (Criteria) this;
        }

        public Criteria andMinOrdQtyIn(List<BigDecimal> values) {
            addCriterion("MIN_ORD_QTY in", values, "minOrdQty");
            return (Criteria) this;
        }

        public Criteria andMinOrdQtyNotIn(List<BigDecimal> values) {
            addCriterion("MIN_ORD_QTY not in", values, "minOrdQty");
            return (Criteria) this;
        }

        public Criteria andMinOrdQtyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MIN_ORD_QTY between", value1, value2, "minOrdQty");
            return (Criteria) this;
        }

        public Criteria andMinOrdQtyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MIN_ORD_QTY not between", value1, value2, "minOrdQty");
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

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("CREATE_TIME like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("CREATE_TIME not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreatePersonIsNull() {
            addCriterion("CREATE_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andCreatePersonIsNotNull() {
            addCriterion("CREATE_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andCreatePersonEqualTo(String value) {
            addCriterion("CREATE_PERSON =", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotEqualTo(String value) {
            addCriterion("CREATE_PERSON <>", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonGreaterThan(String value) {
            addCriterion("CREATE_PERSON >", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_PERSON >=", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonLessThan(String value) {
            addCriterion("CREATE_PERSON <", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonLessThanOrEqualTo(String value) {
            addCriterion("CREATE_PERSON <=", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonLike(String value) {
            addCriterion("CREATE_PERSON like", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotLike(String value) {
            addCriterion("CREATE_PERSON not like", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonIn(List<String> values) {
            addCriterion("CREATE_PERSON in", values, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotIn(List<String> values) {
            addCriterion("CREATE_PERSON not in", values, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonBetween(String value1, String value2) {
            addCriterion("CREATE_PERSON between", value1, value2, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotBetween(String value1, String value2) {
            addCriterion("CREATE_PERSON not between", value1, value2, "createPerson");
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

        public Criteria andUpdatePersonIsNull() {
            addCriterion("UPDATE_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonIsNotNull() {
            addCriterion("UPDATE_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonEqualTo(String value) {
            addCriterion("UPDATE_PERSON =", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonNotEqualTo(String value) {
            addCriterion("UPDATE_PERSON <>", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonGreaterThan(String value) {
            addCriterion("UPDATE_PERSON >", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_PERSON >=", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonLessThan(String value) {
            addCriterion("UPDATE_PERSON <", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_PERSON <=", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonLike(String value) {
            addCriterion("UPDATE_PERSON like", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonNotLike(String value) {
            addCriterion("UPDATE_PERSON not like", value, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonIn(List<String> values) {
            addCriterion("UPDATE_PERSON in", values, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonNotIn(List<String> values) {
            addCriterion("UPDATE_PERSON not in", values, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonBetween(String value1, String value2) {
            addCriterion("UPDATE_PERSON between", value1, value2, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andUpdatePersonNotBetween(String value1, String value2) {
            addCriterion("UPDATE_PERSON not between", value1, value2, "updatePerson");
            return (Criteria) this;
        }

        public Criteria andTermTypeIsNull() {
            addCriterion("TERM_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTermTypeIsNotNull() {
            addCriterion("TERM_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTermTypeEqualTo(String value) {
            addCriterion("TERM_TYPE =", value, "termType");
            return (Criteria) this;
        }

        public Criteria andTermTypeNotEqualTo(String value) {
            addCriterion("TERM_TYPE <>", value, "termType");
            return (Criteria) this;
        }

        public Criteria andTermTypeGreaterThan(String value) {
            addCriterion("TERM_TYPE >", value, "termType");
            return (Criteria) this;
        }

        public Criteria andTermTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TERM_TYPE >=", value, "termType");
            return (Criteria) this;
        }

        public Criteria andTermTypeLessThan(String value) {
            addCriterion("TERM_TYPE <", value, "termType");
            return (Criteria) this;
        }

        public Criteria andTermTypeLessThanOrEqualTo(String value) {
            addCriterion("TERM_TYPE <=", value, "termType");
            return (Criteria) this;
        }

        public Criteria andTermTypeLike(String value) {
            addCriterion("TERM_TYPE like", value, "termType");
            return (Criteria) this;
        }

        public Criteria andTermTypeNotLike(String value) {
            addCriterion("TERM_TYPE not like", value, "termType");
            return (Criteria) this;
        }

        public Criteria andTermTypeIn(List<String> values) {
            addCriterion("TERM_TYPE in", values, "termType");
            return (Criteria) this;
        }

        public Criteria andTermTypeNotIn(List<String> values) {
            addCriterion("TERM_TYPE not in", values, "termType");
            return (Criteria) this;
        }

        public Criteria andTermTypeBetween(String value1, String value2) {
            addCriterion("TERM_TYPE between", value1, value2, "termType");
            return (Criteria) this;
        }

        public Criteria andTermTypeNotBetween(String value1, String value2) {
            addCriterion("TERM_TYPE not between", value1, value2, "termType");
            return (Criteria) this;
        }

        public Criteria andCommTypeIsNull() {
            addCriterion("COMM_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCommTypeIsNotNull() {
            addCriterion("COMM_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCommTypeEqualTo(String value) {
            addCriterion("COMM_TYPE =", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeNotEqualTo(String value) {
            addCriterion("COMM_TYPE <>", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeGreaterThan(String value) {
            addCriterion("COMM_TYPE >", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeGreaterThanOrEqualTo(String value) {
            addCriterion("COMM_TYPE >=", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeLessThan(String value) {
            addCriterion("COMM_TYPE <", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeLessThanOrEqualTo(String value) {
            addCriterion("COMM_TYPE <=", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeLike(String value) {
            addCriterion("COMM_TYPE like", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeNotLike(String value) {
            addCriterion("COMM_TYPE not like", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeIn(List<String> values) {
            addCriterion("COMM_TYPE in", values, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeNotIn(List<String> values) {
            addCriterion("COMM_TYPE not in", values, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeBetween(String value1, String value2) {
            addCriterion("COMM_TYPE between", value1, value2, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeNotBetween(String value1, String value2) {
            addCriterion("COMM_TYPE not between", value1, value2, "commType");
            return (Criteria) this;
        }

        public Criteria andMachineManufIsNull() {
            addCriterion("MACHINE_MANUF is null");
            return (Criteria) this;
        }

        public Criteria andMachineManufIsNotNull() {
            addCriterion("MACHINE_MANUF is not null");
            return (Criteria) this;
        }

        public Criteria andMachineManufEqualTo(String value) {
            addCriterion("MACHINE_MANUF =", value, "machineManuf");
            return (Criteria) this;
        }

        public Criteria andMachineManufNotEqualTo(String value) {
            addCriterion("MACHINE_MANUF <>", value, "machineManuf");
            return (Criteria) this;
        }

        public Criteria andMachineManufGreaterThan(String value) {
            addCriterion("MACHINE_MANUF >", value, "machineManuf");
            return (Criteria) this;
        }

        public Criteria andMachineManufGreaterThanOrEqualTo(String value) {
            addCriterion("MACHINE_MANUF >=", value, "machineManuf");
            return (Criteria) this;
        }

        public Criteria andMachineManufLessThan(String value) {
            addCriterion("MACHINE_MANUF <", value, "machineManuf");
            return (Criteria) this;
        }

        public Criteria andMachineManufLessThanOrEqualTo(String value) {
            addCriterion("MACHINE_MANUF <=", value, "machineManuf");
            return (Criteria) this;
        }

        public Criteria andMachineManufLike(String value) {
            addCriterion("MACHINE_MANUF like", value, "machineManuf");
            return (Criteria) this;
        }

        public Criteria andMachineManufNotLike(String value) {
            addCriterion("MACHINE_MANUF not like", value, "machineManuf");
            return (Criteria) this;
        }

        public Criteria andMachineManufIn(List<String> values) {
            addCriterion("MACHINE_MANUF in", values, "machineManuf");
            return (Criteria) this;
        }

        public Criteria andMachineManufNotIn(List<String> values) {
            addCriterion("MACHINE_MANUF not in", values, "machineManuf");
            return (Criteria) this;
        }

        public Criteria andMachineManufBetween(String value1, String value2) {
            addCriterion("MACHINE_MANUF between", value1, value2, "machineManuf");
            return (Criteria) this;
        }

        public Criteria andMachineManufNotBetween(String value1, String value2) {
            addCriterion("MACHINE_MANUF not between", value1, value2, "machineManuf");
            return (Criteria) this;
        }

        public Criteria andIsOuterPinpadIsNull() {
            addCriterion("IS_OUTER_PINPAD is null");
            return (Criteria) this;
        }

        public Criteria andIsOuterPinpadIsNotNull() {
            addCriterion("IS_OUTER_PINPAD is not null");
            return (Criteria) this;
        }

        public Criteria andIsOuterPinpadEqualTo(String value) {
            addCriterion("IS_OUTER_PINPAD =", value, "isOuterPinpad");
            return (Criteria) this;
        }

        public Criteria andIsOuterPinpadNotEqualTo(String value) {
            addCriterion("IS_OUTER_PINPAD <>", value, "isOuterPinpad");
            return (Criteria) this;
        }

        public Criteria andIsOuterPinpadGreaterThan(String value) {
            addCriterion("IS_OUTER_PINPAD >", value, "isOuterPinpad");
            return (Criteria) this;
        }

        public Criteria andIsOuterPinpadGreaterThanOrEqualTo(String value) {
            addCriterion("IS_OUTER_PINPAD >=", value, "isOuterPinpad");
            return (Criteria) this;
        }

        public Criteria andIsOuterPinpadLessThan(String value) {
            addCriterion("IS_OUTER_PINPAD <", value, "isOuterPinpad");
            return (Criteria) this;
        }

        public Criteria andIsOuterPinpadLessThanOrEqualTo(String value) {
            addCriterion("IS_OUTER_PINPAD <=", value, "isOuterPinpad");
            return (Criteria) this;
        }

        public Criteria andIsOuterPinpadLike(String value) {
            addCriterion("IS_OUTER_PINPAD like", value, "isOuterPinpad");
            return (Criteria) this;
        }

        public Criteria andIsOuterPinpadNotLike(String value) {
            addCriterion("IS_OUTER_PINPAD not like", value, "isOuterPinpad");
            return (Criteria) this;
        }

        public Criteria andIsOuterPinpadIn(List<String> values) {
            addCriterion("IS_OUTER_PINPAD in", values, "isOuterPinpad");
            return (Criteria) this;
        }

        public Criteria andIsOuterPinpadNotIn(List<String> values) {
            addCriterion("IS_OUTER_PINPAD not in", values, "isOuterPinpad");
            return (Criteria) this;
        }

        public Criteria andIsOuterPinpadBetween(String value1, String value2) {
            addCriterion("IS_OUTER_PINPAD between", value1, value2, "isOuterPinpad");
            return (Criteria) this;
        }

        public Criteria andIsOuterPinpadNotBetween(String value1, String value2) {
            addCriterion("IS_OUTER_PINPAD not between", value1, value2, "isOuterPinpad");
            return (Criteria) this;
        }

        public Criteria andIsNonConnIsNull() {
            addCriterion("IS_NON_CONN is null");
            return (Criteria) this;
        }

        public Criteria andIsNonConnIsNotNull() {
            addCriterion("IS_NON_CONN is not null");
            return (Criteria) this;
        }

        public Criteria andIsNonConnEqualTo(String value) {
            addCriterion("IS_NON_CONN =", value, "isNonConn");
            return (Criteria) this;
        }

        public Criteria andIsNonConnNotEqualTo(String value) {
            addCriterion("IS_NON_CONN <>", value, "isNonConn");
            return (Criteria) this;
        }

        public Criteria andIsNonConnGreaterThan(String value) {
            addCriterion("IS_NON_CONN >", value, "isNonConn");
            return (Criteria) this;
        }

        public Criteria andIsNonConnGreaterThanOrEqualTo(String value) {
            addCriterion("IS_NON_CONN >=", value, "isNonConn");
            return (Criteria) this;
        }

        public Criteria andIsNonConnLessThan(String value) {
            addCriterion("IS_NON_CONN <", value, "isNonConn");
            return (Criteria) this;
        }

        public Criteria andIsNonConnLessThanOrEqualTo(String value) {
            addCriterion("IS_NON_CONN <=", value, "isNonConn");
            return (Criteria) this;
        }

        public Criteria andIsNonConnLike(String value) {
            addCriterion("IS_NON_CONN like", value, "isNonConn");
            return (Criteria) this;
        }

        public Criteria andIsNonConnNotLike(String value) {
            addCriterion("IS_NON_CONN not like", value, "isNonConn");
            return (Criteria) this;
        }

        public Criteria andIsNonConnIn(List<String> values) {
            addCriterion("IS_NON_CONN in", values, "isNonConn");
            return (Criteria) this;
        }

        public Criteria andIsNonConnNotIn(List<String> values) {
            addCriterion("IS_NON_CONN not in", values, "isNonConn");
            return (Criteria) this;
        }

        public Criteria andIsNonConnBetween(String value1, String value2) {
            addCriterion("IS_NON_CONN between", value1, value2, "isNonConn");
            return (Criteria) this;
        }

        public Criteria andIsNonConnNotBetween(String value1, String value2) {
            addCriterion("IS_NON_CONN not between", value1, value2, "isNonConn");
            return (Criteria) this;
        }

        public Criteria andIsElectSignIsNull() {
            addCriterion("IS_ELECT_SIGN is null");
            return (Criteria) this;
        }

        public Criteria andIsElectSignIsNotNull() {
            addCriterion("IS_ELECT_SIGN is not null");
            return (Criteria) this;
        }

        public Criteria andIsElectSignEqualTo(String value) {
            addCriterion("IS_ELECT_SIGN =", value, "isElectSign");
            return (Criteria) this;
        }

        public Criteria andIsElectSignNotEqualTo(String value) {
            addCriterion("IS_ELECT_SIGN <>", value, "isElectSign");
            return (Criteria) this;
        }

        public Criteria andIsElectSignGreaterThan(String value) {
            addCriterion("IS_ELECT_SIGN >", value, "isElectSign");
            return (Criteria) this;
        }

        public Criteria andIsElectSignGreaterThanOrEqualTo(String value) {
            addCriterion("IS_ELECT_SIGN >=", value, "isElectSign");
            return (Criteria) this;
        }

        public Criteria andIsElectSignLessThan(String value) {
            addCriterion("IS_ELECT_SIGN <", value, "isElectSign");
            return (Criteria) this;
        }

        public Criteria andIsElectSignLessThanOrEqualTo(String value) {
            addCriterion("IS_ELECT_SIGN <=", value, "isElectSign");
            return (Criteria) this;
        }

        public Criteria andIsElectSignLike(String value) {
            addCriterion("IS_ELECT_SIGN like", value, "isElectSign");
            return (Criteria) this;
        }

        public Criteria andIsElectSignNotLike(String value) {
            addCriterion("IS_ELECT_SIGN not like", value, "isElectSign");
            return (Criteria) this;
        }

        public Criteria andIsElectSignIn(List<String> values) {
            addCriterion("IS_ELECT_SIGN in", values, "isElectSign");
            return (Criteria) this;
        }

        public Criteria andIsElectSignNotIn(List<String> values) {
            addCriterion("IS_ELECT_SIGN not in", values, "isElectSign");
            return (Criteria) this;
        }

        public Criteria andIsElectSignBetween(String value1, String value2) {
            addCriterion("IS_ELECT_SIGN between", value1, value2, "isElectSign");
            return (Criteria) this;
        }

        public Criteria andIsElectSignNotBetween(String value1, String value2) {
            addCriterion("IS_ELECT_SIGN not between", value1, value2, "isElectSign");
            return (Criteria) this;
        }

        public Criteria andDepositIsNull() {
            addCriterion("DEPOSIT is null");
            return (Criteria) this;
        }

        public Criteria andDepositIsNotNull() {
            addCriterion("DEPOSIT is not null");
            return (Criteria) this;
        }

        public Criteria andDepositEqualTo(BigDecimal value) {
            addCriterion("DEPOSIT =", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotEqualTo(BigDecimal value) {
            addCriterion("DEPOSIT <>", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositGreaterThan(BigDecimal value) {
            addCriterion("DEPOSIT >", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DEPOSIT >=", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositLessThan(BigDecimal value) {
            addCriterion("DEPOSIT <", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DEPOSIT <=", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositIn(List<BigDecimal> values) {
            addCriterion("DEPOSIT in", values, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotIn(List<BigDecimal> values) {
            addCriterion("DEPOSIT not in", values, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEPOSIT between", value1, value2, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEPOSIT not between", value1, value2, "deposit");
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