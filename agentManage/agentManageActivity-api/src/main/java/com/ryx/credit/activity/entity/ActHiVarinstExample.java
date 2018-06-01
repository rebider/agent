package com.ryx.credit.activity.entity;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActHiVarinstExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ActHiVarinstExample() {
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
            addCriterion("ID_ is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID_ is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Object value) {
            addCriterion("ID_ =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Object value) {
            addCriterion("ID_ <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Object value) {
            addCriterion("ID_ >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Object value) {
            addCriterion("ID_ >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Object value) {
            addCriterion("ID_ <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Object value) {
            addCriterion("ID_ <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Object> values) {
            addCriterion("ID_ in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Object> values) {
            addCriterion("ID_ not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Object value1, Object value2) {
            addCriterion("ID_ between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Object value1, Object value2) {
            addCriterion("ID_ not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andProcInstIdIsNull() {
            addCriterion("PROC_INST_ID_ is null");
            return (Criteria) this;
        }

        public Criteria andProcInstIdIsNotNull() {
            addCriterion("PROC_INST_ID_ is not null");
            return (Criteria) this;
        }

        public Criteria andProcInstIdEqualTo(Object value) {
            addCriterion("PROC_INST_ID_ =", value, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdNotEqualTo(Object value) {
            addCriterion("PROC_INST_ID_ <>", value, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdGreaterThan(Object value) {
            addCriterion("PROC_INST_ID_ >", value, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdGreaterThanOrEqualTo(Object value) {
            addCriterion("PROC_INST_ID_ >=", value, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdLessThan(Object value) {
            addCriterion("PROC_INST_ID_ <", value, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdLessThanOrEqualTo(Object value) {
            addCriterion("PROC_INST_ID_ <=", value, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdIn(List<Object> values) {
            addCriterion("PROC_INST_ID_ in", values, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdNotIn(List<Object> values) {
            addCriterion("PROC_INST_ID_ not in", values, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdBetween(Object value1, Object value2) {
            addCriterion("PROC_INST_ID_ between", value1, value2, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdNotBetween(Object value1, Object value2) {
            addCriterion("PROC_INST_ID_ not between", value1, value2, "procInstId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdIsNull() {
            addCriterion("EXECUTION_ID_ is null");
            return (Criteria) this;
        }

        public Criteria andExecutionIdIsNotNull() {
            addCriterion("EXECUTION_ID_ is not null");
            return (Criteria) this;
        }

        public Criteria andExecutionIdEqualTo(Object value) {
            addCriterion("EXECUTION_ID_ =", value, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdNotEqualTo(Object value) {
            addCriterion("EXECUTION_ID_ <>", value, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdGreaterThan(Object value) {
            addCriterion("EXECUTION_ID_ >", value, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdGreaterThanOrEqualTo(Object value) {
            addCriterion("EXECUTION_ID_ >=", value, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdLessThan(Object value) {
            addCriterion("EXECUTION_ID_ <", value, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdLessThanOrEqualTo(Object value) {
            addCriterion("EXECUTION_ID_ <=", value, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdIn(List<Object> values) {
            addCriterion("EXECUTION_ID_ in", values, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdNotIn(List<Object> values) {
            addCriterion("EXECUTION_ID_ not in", values, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdBetween(Object value1, Object value2) {
            addCriterion("EXECUTION_ID_ between", value1, value2, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdNotBetween(Object value1, Object value2) {
            addCriterion("EXECUTION_ID_ not between", value1, value2, "executionId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNull() {
            addCriterion("TASK_ID_ is null");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNotNull() {
            addCriterion("TASK_ID_ is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIdEqualTo(Object value) {
            addCriterion("TASK_ID_ =", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotEqualTo(Object value) {
            addCriterion("TASK_ID_ <>", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThan(Object value) {
            addCriterion("TASK_ID_ >", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThanOrEqualTo(Object value) {
            addCriterion("TASK_ID_ >=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThan(Object value) {
            addCriterion("TASK_ID_ <", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThanOrEqualTo(Object value) {
            addCriterion("TASK_ID_ <=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIn(List<Object> values) {
            addCriterion("TASK_ID_ in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotIn(List<Object> values) {
            addCriterion("TASK_ID_ not in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdBetween(Object value1, Object value2) {
            addCriterion("TASK_ID_ between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotBetween(Object value1, Object value2) {
            addCriterion("TASK_ID_ not between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("NAME_ is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("NAME_ is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(Object value) {
            addCriterion("NAME_ =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(Object value) {
            addCriterion("NAME_  like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(Object value) {
            addCriterion("NAME_ <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(Object value) {
            addCriterion("NAME_ >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(Object value) {
            addCriterion("NAME_ >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(Object value) {
            addCriterion("NAME_ <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(Object value) {
            addCriterion("NAME_ <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<Object> values) {
            addCriterion("NAME_ in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<Object> values) {
            addCriterion("NAME_ not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(Object value1, Object value2) {
            addCriterion("NAME_ between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(Object value1, Object value2) {
            addCriterion("NAME_ not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andVarTypeIsNull() {
            addCriterion("VAR_TYPE_ is null");
            return (Criteria) this;
        }

        public Criteria andVarTypeIsNotNull() {
            addCriterion("VAR_TYPE_ is not null");
            return (Criteria) this;
        }

        public Criteria andVarTypeEqualTo(Object value) {
            addCriterion("VAR_TYPE_ =", value, "varType");
            return (Criteria) this;
        }

        public Criteria andVarTypeNotEqualTo(Object value) {
            addCriterion("VAR_TYPE_ <>", value, "varType");
            return (Criteria) this;
        }

        public Criteria andVarTypeGreaterThan(Object value) {
            addCriterion("VAR_TYPE_ >", value, "varType");
            return (Criteria) this;
        }

        public Criteria andVarTypeGreaterThanOrEqualTo(Object value) {
            addCriterion("VAR_TYPE_ >=", value, "varType");
            return (Criteria) this;
        }

        public Criteria andVarTypeLessThan(Object value) {
            addCriterion("VAR_TYPE_ <", value, "varType");
            return (Criteria) this;
        }

        public Criteria andVarTypeLessThanOrEqualTo(Object value) {
            addCriterion("VAR_TYPE_ <=", value, "varType");
            return (Criteria) this;
        }

        public Criteria andVarTypeIn(List<Object> values) {
            addCriterion("VAR_TYPE_ in", values, "varType");
            return (Criteria) this;
        }

        public Criteria andVarTypeNotIn(List<Object> values) {
            addCriterion("VAR_TYPE_ not in", values, "varType");
            return (Criteria) this;
        }

        public Criteria andVarTypeBetween(Object value1, Object value2) {
            addCriterion("VAR_TYPE_ between", value1, value2, "varType");
            return (Criteria) this;
        }

        public Criteria andVarTypeNotBetween(Object value1, Object value2) {
            addCriterion("VAR_TYPE_ not between", value1, value2, "varType");
            return (Criteria) this;
        }

        public Criteria andRevIsNull() {
            addCriterion("REV_ is null");
            return (Criteria) this;
        }

        public Criteria andRevIsNotNull() {
            addCriterion("REV_ is not null");
            return (Criteria) this;
        }

        public Criteria andRevEqualTo(BigDecimal value) {
            addCriterion("REV_ =", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevNotEqualTo(BigDecimal value) {
            addCriterion("REV_ <>", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevGreaterThan(BigDecimal value) {
            addCriterion("REV_ >", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REV_ >=", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevLessThan(BigDecimal value) {
            addCriterion("REV_ <", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REV_ <=", value, "rev");
            return (Criteria) this;
        }

        public Criteria andRevIn(List<BigDecimal> values) {
            addCriterion("REV_ in", values, "rev");
            return (Criteria) this;
        }

        public Criteria andRevNotIn(List<BigDecimal> values) {
            addCriterion("REV_ not in", values, "rev");
            return (Criteria) this;
        }

        public Criteria andRevBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REV_ between", value1, value2, "rev");
            return (Criteria) this;
        }

        public Criteria andRevNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REV_ not between", value1, value2, "rev");
            return (Criteria) this;
        }

        public Criteria andBytearrayIdIsNull() {
            addCriterion("BYTEARRAY_ID_ is null");
            return (Criteria) this;
        }

        public Criteria andBytearrayIdIsNotNull() {
            addCriterion("BYTEARRAY_ID_ is not null");
            return (Criteria) this;
        }

        public Criteria andBytearrayIdEqualTo(Object value) {
            addCriterion("BYTEARRAY_ID_ =", value, "bytearrayId");
            return (Criteria) this;
        }

        public Criteria andBytearrayIdNotEqualTo(Object value) {
            addCriterion("BYTEARRAY_ID_ <>", value, "bytearrayId");
            return (Criteria) this;
        }

        public Criteria andBytearrayIdGreaterThan(Object value) {
            addCriterion("BYTEARRAY_ID_ >", value, "bytearrayId");
            return (Criteria) this;
        }

        public Criteria andBytearrayIdGreaterThanOrEqualTo(Object value) {
            addCriterion("BYTEARRAY_ID_ >=", value, "bytearrayId");
            return (Criteria) this;
        }

        public Criteria andBytearrayIdLessThan(Object value) {
            addCriterion("BYTEARRAY_ID_ <", value, "bytearrayId");
            return (Criteria) this;
        }

        public Criteria andBytearrayIdLessThanOrEqualTo(Object value) {
            addCriterion("BYTEARRAY_ID_ <=", value, "bytearrayId");
            return (Criteria) this;
        }

        public Criteria andBytearrayIdIn(List<Object> values) {
            addCriterion("BYTEARRAY_ID_ in", values, "bytearrayId");
            return (Criteria) this;
        }

        public Criteria andBytearrayIdNotIn(List<Object> values) {
            addCriterion("BYTEARRAY_ID_ not in", values, "bytearrayId");
            return (Criteria) this;
        }

        public Criteria andBytearrayIdBetween(Object value1, Object value2) {
            addCriterion("BYTEARRAY_ID_ between", value1, value2, "bytearrayId");
            return (Criteria) this;
        }

        public Criteria andBytearrayIdNotBetween(Object value1, Object value2) {
            addCriterion("BYTEARRAY_ID_ not between", value1, value2, "bytearrayId");
            return (Criteria) this;
        }

        public Criteria andDoubleIsNull() {
            addCriterion("DOUBLE_ is null");
            return (Criteria) this;
        }

        public Criteria andDoubleIsNotNull() {
            addCriterion("DOUBLE_ is not null");
            return (Criteria) this;
        }

        public Criteria andDoubleEqualTo(BigDecimal value) {
            addCriterion("DOUBLE_ =", value, "double");
            return (Criteria) this;
        }

        public Criteria andDoubleNotEqualTo(BigDecimal value) {
            addCriterion("DOUBLE_ <>", value, "double");
            return (Criteria) this;
        }

        public Criteria andDoubleGreaterThan(BigDecimal value) {
            addCriterion("DOUBLE_ >", value, "double");
            return (Criteria) this;
        }

        public Criteria andDoubleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DOUBLE_ >=", value, "double");
            return (Criteria) this;
        }

        public Criteria andDoubleLessThan(BigDecimal value) {
            addCriterion("DOUBLE_ <", value, "double");
            return (Criteria) this;
        }

        public Criteria andDoubleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DOUBLE_ <=", value, "double");
            return (Criteria) this;
        }

        public Criteria andDoubleIn(List<BigDecimal> values) {
            addCriterion("DOUBLE_ in", values, "double");
            return (Criteria) this;
        }

        public Criteria andDoubleNotIn(List<BigDecimal> values) {
            addCriterion("DOUBLE_ not in", values, "double");
            return (Criteria) this;
        }

        public Criteria andDoubleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DOUBLE_ between", value1, value2, "double");
            return (Criteria) this;
        }

        public Criteria andDoubleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DOUBLE_ not between", value1, value2, "double");
            return (Criteria) this;
        }

        public Criteria andLongIsNull() {
            addCriterion("LONG_ is null");
            return (Criteria) this;
        }

        public Criteria andLongIsNotNull() {
            addCriterion("LONG_ is not null");
            return (Criteria) this;
        }

        public Criteria andLongEqualTo(BigDecimal value) {
            addCriterion("LONG_ =", value, "long");
            return (Criteria) this;
        }

        public Criteria andLongNotEqualTo(BigDecimal value) {
            addCriterion("LONG_ <>", value, "long");
            return (Criteria) this;
        }

        public Criteria andLongGreaterThan(BigDecimal value) {
            addCriterion("LONG_ >", value, "long");
            return (Criteria) this;
        }

        public Criteria andLongGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("LONG_ >=", value, "long");
            return (Criteria) this;
        }

        public Criteria andLongLessThan(BigDecimal value) {
            addCriterion("LONG_ <", value, "long");
            return (Criteria) this;
        }

        public Criteria andLongLessThanOrEqualTo(BigDecimal value) {
            addCriterion("LONG_ <=", value, "long");
            return (Criteria) this;
        }

        public Criteria andLongIn(List<BigDecimal> values) {
            addCriterion("LONG_ in", values, "long");
            return (Criteria) this;
        }

        public Criteria andLongNotIn(List<BigDecimal> values) {
            addCriterion("LONG_ not in", values, "long");
            return (Criteria) this;
        }

        public Criteria andLongBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LONG_ between", value1, value2, "long");
            return (Criteria) this;
        }

        public Criteria andLongNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LONG_ not between", value1, value2, "long");
            return (Criteria) this;
        }

        public Criteria andTextIsNull() {
            addCriterion("TEXT_ is null");
            return (Criteria) this;
        }

        public Criteria andTextIsNotNull() {
            addCriterion("TEXT_ is not null");
            return (Criteria) this;
        }

        public Criteria andTextEqualTo(Object value) {
            addCriterion("TEXT_ =", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotEqualTo(Object value) {
            addCriterion("TEXT_ <>", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextGreaterThan(Object value) {
            addCriterion("TEXT_ >", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextGreaterThanOrEqualTo(Object value) {
            addCriterion("TEXT_ >=", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLessThan(Object value) {
            addCriterion("TEXT_ <", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLessThanOrEqualTo(Object value) {
            addCriterion("TEXT_ <=", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextIn(List<Object> values) {
            addCriterion("TEXT_ in", values, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotIn(List<Object> values) {
            addCriterion("TEXT_ not in", values, "text");
            return (Criteria) this;
        }

        public Criteria andTextBetween(Object value1, Object value2) {
            addCriterion("TEXT_ between", value1, value2, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotBetween(Object value1, Object value2) {
            addCriterion("TEXT_ not between", value1, value2, "text");
            return (Criteria) this;
        }

        public Criteria andText2IsNull() {
            addCriterion("TEXT2_ is null");
            return (Criteria) this;
        }

        public Criteria andText2IsNotNull() {
            addCriterion("TEXT2_ is not null");
            return (Criteria) this;
        }

        public Criteria andText2EqualTo(Object value) {
            addCriterion("TEXT2_ =", value, "text2");
            return (Criteria) this;
        }

        public Criteria andText2NotEqualTo(Object value) {
            addCriterion("TEXT2_ <>", value, "text2");
            return (Criteria) this;
        }

        public Criteria andText2GreaterThan(Object value) {
            addCriterion("TEXT2_ >", value, "text2");
            return (Criteria) this;
        }

        public Criteria andText2GreaterThanOrEqualTo(Object value) {
            addCriterion("TEXT2_ >=", value, "text2");
            return (Criteria) this;
        }

        public Criteria andText2LessThan(Object value) {
            addCriterion("TEXT2_ <", value, "text2");
            return (Criteria) this;
        }

        public Criteria andText2LessThanOrEqualTo(Object value) {
            addCriterion("TEXT2_ <=", value, "text2");
            return (Criteria) this;
        }

        public Criteria andText2In(List<Object> values) {
            addCriterion("TEXT2_ in", values, "text2");
            return (Criteria) this;
        }

        public Criteria andText2NotIn(List<Object> values) {
            addCriterion("TEXT2_ not in", values, "text2");
            return (Criteria) this;
        }

        public Criteria andText2Between(Object value1, Object value2) {
            addCriterion("TEXT2_ between", value1, value2, "text2");
            return (Criteria) this;
        }

        public Criteria andText2NotBetween(Object value1, Object value2) {
            addCriterion("TEXT2_ not between", value1, value2, "text2");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME_ is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME_ is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME_ =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME_ <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME_ >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME_ >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME_ <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME_ <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME_ in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME_ not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME_ between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME_ not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeIsNull() {
            addCriterion("LAST_UPDATED_TIME_ is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeIsNotNull() {
            addCriterion("LAST_UPDATED_TIME_ is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeEqualTo(Date value) {
            addCriterion("LAST_UPDATED_TIME_ =", value, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeNotEqualTo(Date value) {
            addCriterion("LAST_UPDATED_TIME_ <>", value, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeGreaterThan(Date value) {
            addCriterion("LAST_UPDATED_TIME_ >", value, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("LAST_UPDATED_TIME_ >=", value, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeLessThan(Date value) {
            addCriterion("LAST_UPDATED_TIME_ <", value, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("LAST_UPDATED_TIME_ <=", value, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeIn(List<Date> values) {
            addCriterion("LAST_UPDATED_TIME_ in", values, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeNotIn(List<Date> values) {
            addCriterion("LAST_UPDATED_TIME_ not in", values, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeBetween(Date value1, Date value2) {
            addCriterion("LAST_UPDATED_TIME_ between", value1, value2, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("LAST_UPDATED_TIME_ not between", value1, value2, "lastUpdatedTime");
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