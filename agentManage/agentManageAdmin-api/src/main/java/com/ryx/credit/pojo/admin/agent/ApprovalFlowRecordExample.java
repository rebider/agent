package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApprovalFlowRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ApprovalFlowRecordExample() {
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

        public Criteria andTaskIdIsNull() {
            addCriterion("TASK_ID is null");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNotNull() {
            addCriterion("TASK_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIdEqualTo(String value) {
            addCriterion("TASK_ID =", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotEqualTo(String value) {
            addCriterion("TASK_ID <>", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThan(String value) {
            addCriterion("TASK_ID >", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThanOrEqualTo(String value) {
            addCriterion("TASK_ID >=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThan(String value) {
            addCriterion("TASK_ID <", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThanOrEqualTo(String value) {
            addCriterion("TASK_ID <=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLike(String value) {
            addCriterion("TASK_ID like", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotLike(String value) {
            addCriterion("TASK_ID not like", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIn(List<String> values) {
            addCriterion("TASK_ID in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotIn(List<String> values) {
            addCriterion("TASK_ID not in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdBetween(String value1, String value2) {
            addCriterion("TASK_ID between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotBetween(String value1, String value2) {
            addCriterion("TASK_ID not between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdIsNull() {
            addCriterion("EXECUTION_ID is null");
            return (Criteria) this;
        }

        public Criteria andExecutionIdIsNotNull() {
            addCriterion("EXECUTION_ID is not null");
            return (Criteria) this;
        }

        public Criteria andExecutionIdEqualTo(String value) {
            addCriterion("EXECUTION_ID =", value, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdNotEqualTo(String value) {
            addCriterion("EXECUTION_ID <>", value, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdGreaterThan(String value) {
            addCriterion("EXECUTION_ID >", value, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdGreaterThanOrEqualTo(String value) {
            addCriterion("EXECUTION_ID >=", value, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdLessThan(String value) {
            addCriterion("EXECUTION_ID <", value, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdLessThanOrEqualTo(String value) {
            addCriterion("EXECUTION_ID <=", value, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdLike(String value) {
            addCriterion("EXECUTION_ID like", value, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdNotLike(String value) {
            addCriterion("EXECUTION_ID not like", value, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdIn(List<String> values) {
            addCriterion("EXECUTION_ID in", values, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdNotIn(List<String> values) {
            addCriterion("EXECUTION_ID not in", values, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdBetween(String value1, String value2) {
            addCriterion("EXECUTION_ID between", value1, value2, "executionId");
            return (Criteria) this;
        }

        public Criteria andExecutionIdNotBetween(String value1, String value2) {
            addCriterion("EXECUTION_ID not between", value1, value2, "executionId");
            return (Criteria) this;
        }

        public Criteria andTaskNameIsNull() {
            addCriterion("TASK_NAME is null");
            return (Criteria) this;
        }

        public Criteria andTaskNameIsNotNull() {
            addCriterion("TASK_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andTaskNameEqualTo(String value) {
            addCriterion("TASK_NAME =", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotEqualTo(String value) {
            addCriterion("TASK_NAME <>", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameGreaterThan(String value) {
            addCriterion("TASK_NAME >", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameGreaterThanOrEqualTo(String value) {
            addCriterion("TASK_NAME >=", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLessThan(String value) {
            addCriterion("TASK_NAME <", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLessThanOrEqualTo(String value) {
            addCriterion("TASK_NAME <=", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLike(String value) {
            addCriterion("TASK_NAME like", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotLike(String value) {
            addCriterion("TASK_NAME not like", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameIn(List<String> values) {
            addCriterion("TASK_NAME in", values, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotIn(List<String> values) {
            addCriterion("TASK_NAME not in", values, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameBetween(String value1, String value2) {
            addCriterion("TASK_NAME between", value1, value2, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotBetween(String value1, String value2) {
            addCriterion("TASK_NAME not between", value1, value2, "taskName");
            return (Criteria) this;
        }

        public Criteria andBusIdIsNull() {
            addCriterion("BUS_ID is null");
            return (Criteria) this;
        }

        public Criteria andBusIdIsNotNull() {
            addCriterion("BUS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBusIdEqualTo(String value) {
            addCriterion("BUS_ID =", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotEqualTo(String value) {
            addCriterion("BUS_ID <>", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdGreaterThan(String value) {
            addCriterion("BUS_ID >", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_ID >=", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdLessThan(String value) {
            addCriterion("BUS_ID <", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdLessThanOrEqualTo(String value) {
            addCriterion("BUS_ID <=", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdLike(String value) {
            addCriterion("BUS_ID like", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotLike(String value) {
            addCriterion("BUS_ID not like", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdIn(List<String> values) {
            addCriterion("BUS_ID in", values, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotIn(List<String> values) {
            addCriterion("BUS_ID not in", values, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdBetween(String value1, String value2) {
            addCriterion("BUS_ID between", value1, value2, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotBetween(String value1, String value2) {
            addCriterion("BUS_ID not between", value1, value2, "busId");
            return (Criteria) this;
        }

        public Criteria andBusTypeIsNull() {
            addCriterion("BUS_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBusTypeIsNotNull() {
            addCriterion("BUS_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBusTypeEqualTo(String value) {
            addCriterion("BUS_TYPE =", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeNotEqualTo(String value) {
            addCriterion("BUS_TYPE <>", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeGreaterThan(String value) {
            addCriterion("BUS_TYPE >", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_TYPE >=", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeLessThan(String value) {
            addCriterion("BUS_TYPE <", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeLessThanOrEqualTo(String value) {
            addCriterion("BUS_TYPE <=", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeLike(String value) {
            addCriterion("BUS_TYPE like", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeNotLike(String value) {
            addCriterion("BUS_TYPE not like", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeIn(List<String> values) {
            addCriterion("BUS_TYPE in", values, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeNotIn(List<String> values) {
            addCriterion("BUS_TYPE not in", values, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeBetween(String value1, String value2) {
            addCriterion("BUS_TYPE between", value1, value2, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeNotBetween(String value1, String value2) {
            addCriterion("BUS_TYPE not between", value1, value2, "busType");
            return (Criteria) this;
        }

        public Criteria andApprovalPersonIsNull() {
            addCriterion("APPROVAL_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andApprovalPersonIsNotNull() {
            addCriterion("APPROVAL_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalPersonEqualTo(String value) {
            addCriterion("APPROVAL_PERSON =", value, "approvalPerson");
            return (Criteria) this;
        }

        public Criteria andApprovalPersonNotEqualTo(String value) {
            addCriterion("APPROVAL_PERSON <>", value, "approvalPerson");
            return (Criteria) this;
        }

        public Criteria andApprovalPersonGreaterThan(String value) {
            addCriterion("APPROVAL_PERSON >", value, "approvalPerson");
            return (Criteria) this;
        }

        public Criteria andApprovalPersonGreaterThanOrEqualTo(String value) {
            addCriterion("APPROVAL_PERSON >=", value, "approvalPerson");
            return (Criteria) this;
        }

        public Criteria andApprovalPersonLessThan(String value) {
            addCriterion("APPROVAL_PERSON <", value, "approvalPerson");
            return (Criteria) this;
        }

        public Criteria andApprovalPersonLessThanOrEqualTo(String value) {
            addCriterion("APPROVAL_PERSON <=", value, "approvalPerson");
            return (Criteria) this;
        }

        public Criteria andApprovalPersonLike(String value) {
            addCriterion("APPROVAL_PERSON like", value, "approvalPerson");
            return (Criteria) this;
        }

        public Criteria andApprovalPersonNotLike(String value) {
            addCriterion("APPROVAL_PERSON not like", value, "approvalPerson");
            return (Criteria) this;
        }

        public Criteria andApprovalPersonIn(List<String> values) {
            addCriterion("APPROVAL_PERSON in", values, "approvalPerson");
            return (Criteria) this;
        }

        public Criteria andApprovalPersonNotIn(List<String> values) {
            addCriterion("APPROVAL_PERSON not in", values, "approvalPerson");
            return (Criteria) this;
        }

        public Criteria andApprovalPersonBetween(String value1, String value2) {
            addCriterion("APPROVAL_PERSON between", value1, value2, "approvalPerson");
            return (Criteria) this;
        }

        public Criteria andApprovalPersonNotBetween(String value1, String value2) {
            addCriterion("APPROVAL_PERSON not between", value1, value2, "approvalPerson");
            return (Criteria) this;
        }

        public Criteria andApprovalDepIsNull() {
            addCriterion("APPROVAL_DEP is null");
            return (Criteria) this;
        }

        public Criteria andApprovalDepIsNotNull() {
            addCriterion("APPROVAL_DEP is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalDepEqualTo(String value) {
            addCriterion("APPROVAL_DEP =", value, "approvalDep");
            return (Criteria) this;
        }

        public Criteria andApprovalDepNotEqualTo(String value) {
            addCriterion("APPROVAL_DEP <>", value, "approvalDep");
            return (Criteria) this;
        }

        public Criteria andApprovalDepGreaterThan(String value) {
            addCriterion("APPROVAL_DEP >", value, "approvalDep");
            return (Criteria) this;
        }

        public Criteria andApprovalDepGreaterThanOrEqualTo(String value) {
            addCriterion("APPROVAL_DEP >=", value, "approvalDep");
            return (Criteria) this;
        }

        public Criteria andApprovalDepLessThan(String value) {
            addCriterion("APPROVAL_DEP <", value, "approvalDep");
            return (Criteria) this;
        }

        public Criteria andApprovalDepLessThanOrEqualTo(String value) {
            addCriterion("APPROVAL_DEP <=", value, "approvalDep");
            return (Criteria) this;
        }

        public Criteria andApprovalDepLike(String value) {
            addCriterion("APPROVAL_DEP like", value, "approvalDep");
            return (Criteria) this;
        }

        public Criteria andApprovalDepNotLike(String value) {
            addCriterion("APPROVAL_DEP not like", value, "approvalDep");
            return (Criteria) this;
        }

        public Criteria andApprovalDepIn(List<String> values) {
            addCriterion("APPROVAL_DEP in", values, "approvalDep");
            return (Criteria) this;
        }

        public Criteria andApprovalDepNotIn(List<String> values) {
            addCriterion("APPROVAL_DEP not in", values, "approvalDep");
            return (Criteria) this;
        }

        public Criteria andApprovalDepBetween(String value1, String value2) {
            addCriterion("APPROVAL_DEP between", value1, value2, "approvalDep");
            return (Criteria) this;
        }

        public Criteria andApprovalDepNotBetween(String value1, String value2) {
            addCriterion("APPROVAL_DEP not between", value1, value2, "approvalDep");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeIsNull() {
            addCriterion("APPROVAL_TIME is null");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeIsNotNull() {
            addCriterion("APPROVAL_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeEqualTo(Date value) {
            addCriterion("APPROVAL_TIME =", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotEqualTo(Date value) {
            addCriterion("APPROVAL_TIME <>", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeGreaterThan(Date value) {
            addCriterion("APPROVAL_TIME >", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("APPROVAL_TIME >=", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeLessThan(Date value) {
            addCriterion("APPROVAL_TIME <", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeLessThanOrEqualTo(Date value) {
            addCriterion("APPROVAL_TIME <=", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeIn(List<Date> values) {
            addCriterion("APPROVAL_TIME in", values, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotIn(List<Date> values) {
            addCriterion("APPROVAL_TIME not in", values, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeBetween(Date value1, Date value2) {
            addCriterion("APPROVAL_TIME between", value1, value2, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotBetween(Date value1, Date value2) {
            addCriterion("APPROVAL_TIME not between", value1, value2, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalResultIsNull() {
            addCriterion("APPROVAL_RESULT is null");
            return (Criteria) this;
        }

        public Criteria andApprovalResultIsNotNull() {
            addCriterion("APPROVAL_RESULT is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalResultEqualTo(String value) {
            addCriterion("APPROVAL_RESULT =", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultNotEqualTo(String value) {
            addCriterion("APPROVAL_RESULT <>", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultGreaterThan(String value) {
            addCriterion("APPROVAL_RESULT >", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultGreaterThanOrEqualTo(String value) {
            addCriterion("APPROVAL_RESULT >=", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultLessThan(String value) {
            addCriterion("APPROVAL_RESULT <", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultLessThanOrEqualTo(String value) {
            addCriterion("APPROVAL_RESULT <=", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultLike(String value) {
            addCriterion("APPROVAL_RESULT like", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultNotLike(String value) {
            addCriterion("APPROVAL_RESULT not like", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultIn(List<String> values) {
            addCriterion("APPROVAL_RESULT in", values, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultNotIn(List<String> values) {
            addCriterion("APPROVAL_RESULT not in", values, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultBetween(String value1, String value2) {
            addCriterion("APPROVAL_RESULT between", value1, value2, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultNotBetween(String value1, String value2) {
            addCriterion("APPROVAL_RESULT not between", value1, value2, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalOpinionIsNull() {
            addCriterion("APPROVAL_OPINION is null");
            return (Criteria) this;
        }

        public Criteria andApprovalOpinionIsNotNull() {
            addCriterion("APPROVAL_OPINION is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalOpinionEqualTo(String value) {
            addCriterion("APPROVAL_OPINION =", value, "approvalOpinion");
            return (Criteria) this;
        }

        public Criteria andApprovalOpinionNotEqualTo(String value) {
            addCriterion("APPROVAL_OPINION <>", value, "approvalOpinion");
            return (Criteria) this;
        }

        public Criteria andApprovalOpinionGreaterThan(String value) {
            addCriterion("APPROVAL_OPINION >", value, "approvalOpinion");
            return (Criteria) this;
        }

        public Criteria andApprovalOpinionGreaterThanOrEqualTo(String value) {
            addCriterion("APPROVAL_OPINION >=", value, "approvalOpinion");
            return (Criteria) this;
        }

        public Criteria andApprovalOpinionLessThan(String value) {
            addCriterion("APPROVAL_OPINION <", value, "approvalOpinion");
            return (Criteria) this;
        }

        public Criteria andApprovalOpinionLessThanOrEqualTo(String value) {
            addCriterion("APPROVAL_OPINION <=", value, "approvalOpinion");
            return (Criteria) this;
        }

        public Criteria andApprovalOpinionLike(String value) {
            addCriterion("APPROVAL_OPINION like", value, "approvalOpinion");
            return (Criteria) this;
        }

        public Criteria andApprovalOpinionNotLike(String value) {
            addCriterion("APPROVAL_OPINION not like", value, "approvalOpinion");
            return (Criteria) this;
        }

        public Criteria andApprovalOpinionIn(List<String> values) {
            addCriterion("APPROVAL_OPINION in", values, "approvalOpinion");
            return (Criteria) this;
        }

        public Criteria andApprovalOpinionNotIn(List<String> values) {
            addCriterion("APPROVAL_OPINION not in", values, "approvalOpinion");
            return (Criteria) this;
        }

        public Criteria andApprovalOpinionBetween(String value1, String value2) {
            addCriterion("APPROVAL_OPINION between", value1, value2, "approvalOpinion");
            return (Criteria) this;
        }

        public Criteria andApprovalOpinionNotBetween(String value1, String value2) {
            addCriterion("APPROVAL_OPINION not between", value1, value2, "approvalOpinion");
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

        public Criteria andActivityStatusIsNull() {
            addCriterion("ACTIVITY_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andActivityStatusIsNotNull() {
            addCriterion("ACTIVITY_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andActivityStatusEqualTo(BigDecimal value) {
            addCriterion("ACTIVITY_STATUS =", value, "activityStatus");
            return (Criteria) this;
        }

        public Criteria andActivityStatusNotEqualTo(BigDecimal value) {
            addCriterion("ACTIVITY_STATUS <>", value, "activityStatus");
            return (Criteria) this;
        }

        public Criteria andActivityStatusGreaterThan(BigDecimal value) {
            addCriterion("ACTIVITY_STATUS >", value, "activityStatus");
            return (Criteria) this;
        }

        public Criteria andActivityStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTIVITY_STATUS >=", value, "activityStatus");
            return (Criteria) this;
        }

        public Criteria andActivityStatusLessThan(BigDecimal value) {
            addCriterion("ACTIVITY_STATUS <", value, "activityStatus");
            return (Criteria) this;
        }

        public Criteria andActivityStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ACTIVITY_STATUS <=", value, "activityStatus");
            return (Criteria) this;
        }

        public Criteria andActivityStatusIn(List<BigDecimal> values) {
            addCriterion("ACTIVITY_STATUS in", values, "activityStatus");
            return (Criteria) this;
        }

        public Criteria andActivityStatusNotIn(List<BigDecimal> values) {
            addCriterion("ACTIVITY_STATUS not in", values, "activityStatus");
            return (Criteria) this;
        }

        public Criteria andActivityStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTIVITY_STATUS between", value1, value2, "activityStatus");
            return (Criteria) this;
        }

        public Criteria andActivityStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACTIVITY_STATUS not between", value1, value2, "activityStatus");
            return (Criteria) this;
        }

        public Criteria andErrorMsgIsNull() {
            addCriterion("ERROR_MSG is null");
            return (Criteria) this;
        }

        public Criteria andErrorMsgIsNotNull() {
            addCriterion("ERROR_MSG is not null");
            return (Criteria) this;
        }

        public Criteria andErrorMsgEqualTo(String value) {
            addCriterion("ERROR_MSG =", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotEqualTo(String value) {
            addCriterion("ERROR_MSG <>", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgGreaterThan(String value) {
            addCriterion("ERROR_MSG >", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgGreaterThanOrEqualTo(String value) {
            addCriterion("ERROR_MSG >=", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgLessThan(String value) {
            addCriterion("ERROR_MSG <", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgLessThanOrEqualTo(String value) {
            addCriterion("ERROR_MSG <=", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgLike(String value) {
            addCriterion("ERROR_MSG like", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotLike(String value) {
            addCriterion("ERROR_MSG not like", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgIn(List<String> values) {
            addCriterion("ERROR_MSG in", values, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotIn(List<String> values) {
            addCriterion("ERROR_MSG not in", values, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgBetween(String value1, String value2) {
            addCriterion("ERROR_MSG between", value1, value2, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotBetween(String value1, String value2) {
            addCriterion("ERROR_MSG not between", value1, value2, "errorMsg");
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