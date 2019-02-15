package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PCityApplicationDetailExample  implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PCityApplicationDetailExample() {
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

        public Criteria andApplicationTypeIsNull() {
            addCriterion("APPLICATION_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andApplicationTypeIsNotNull() {
            addCriterion("APPLICATION_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andApplicationTypeEqualTo(String value) {
            addCriterion("APPLICATION_TYPE =", value, "applicationType");
            return (Criteria) this;
        }

        public Criteria andApplicationTypeNotEqualTo(String value) {
            addCriterion("APPLICATION_TYPE <>", value, "applicationType");
            return (Criteria) this;
        }

        public Criteria andApplicationTypeGreaterThan(String value) {
            addCriterion("APPLICATION_TYPE >", value, "applicationType");
            return (Criteria) this;
        }

        public Criteria andApplicationTypeGreaterThanOrEqualTo(String value) {
            addCriterion("APPLICATION_TYPE >=", value, "applicationType");
            return (Criteria) this;
        }

        public Criteria andApplicationTypeLessThan(String value) {
            addCriterion("APPLICATION_TYPE <", value, "applicationType");
            return (Criteria) this;
        }

        public Criteria andApplicationTypeLessThanOrEqualTo(String value) {
            addCriterion("APPLICATION_TYPE <=", value, "applicationType");
            return (Criteria) this;
        }

        public Criteria andApplicationTypeLike(String value) {
            addCriterion("APPLICATION_TYPE like", value, "applicationType");
            return (Criteria) this;
        }

        public Criteria andApplicationTypeNotLike(String value) {
            addCriterion("APPLICATION_TYPE not like", value, "applicationType");
            return (Criteria) this;
        }

        public Criteria andApplicationTypeIn(List<String> values) {
            addCriterion("APPLICATION_TYPE in", values, "applicationType");
            return (Criteria) this;
        }

        public Criteria andApplicationTypeNotIn(List<String> values) {
            addCriterion("APPLICATION_TYPE not in", values, "applicationType");
            return (Criteria) this;
        }

        public Criteria andApplicationTypeBetween(String value1, String value2) {
            addCriterion("APPLICATION_TYPE between", value1, value2, "applicationType");
            return (Criteria) this;
        }

        public Criteria andApplicationTypeNotBetween(String value1, String value2) {
            addCriterion("APPLICATION_TYPE not between", value1, value2, "applicationType");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNull() {
            addCriterion("AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNotNull() {
            addCriterion("AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentIdEqualTo(String value) {
            addCriterion("AGENT_ID =", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotEqualTo(String value) {
            addCriterion("AGENT_ID <>", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThan(String value) {
            addCriterion("AGENT_ID >", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_ID >=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThan(String value) {
            addCriterion("AGENT_ID <", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThanOrEqualTo(String value) {
            addCriterion("AGENT_ID <=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLike(String value) {
            addCriterion("AGENT_ID like", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotLike(String value) {
            addCriterion("AGENT_ID not like", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdIn(List<String> values) {
            addCriterion("AGENT_ID in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotIn(List<String> values) {
            addCriterion("AGENT_ID not in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdBetween(String value1, String value2) {
            addCriterion("AGENT_ID between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotBetween(String value1, String value2) {
            addCriterion("AGENT_ID not between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentNameIsNull() {
            addCriterion("AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAgentNameIsNotNull() {
            addCriterion("AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAgentNameEqualTo(String value) {
            addCriterion("AGENT_NAME =", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotEqualTo(String value) {
            addCriterion("AGENT_NAME <>", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThan(String value) {
            addCriterion("AGENT_NAME >", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_NAME >=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThan(String value) {
            addCriterion("AGENT_NAME <", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThanOrEqualTo(String value) {
            addCriterion("AGENT_NAME <=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLike(String value) {
            addCriterion("AGENT_NAME like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotLike(String value) {
            addCriterion("AGENT_NAME not like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameIn(List<String> values) {
            addCriterion("AGENT_NAME in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotIn(List<String> values) {
            addCriterion("AGENT_NAME not in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameBetween(String value1, String value2) {
            addCriterion("AGENT_NAME between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotBetween(String value1, String value2) {
            addCriterion("AGENT_NAME not between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdIsNull() {
            addCriterion("PARENT_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdIsNotNull() {
            addCriterion("PARENT_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID =", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID <>", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdGreaterThan(String value) {
            addCriterion("PARENT_AGENT_ID >", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID >=", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdLessThan(String value) {
            addCriterion("PARENT_AGENT_ID <", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdLessThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID <=", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdLike(String value) {
            addCriterion("PARENT_AGENT_ID like", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotLike(String value) {
            addCriterion("PARENT_AGENT_ID not like", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdIn(List<String> values) {
            addCriterion("PARENT_AGENT_ID in", values, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotIn(List<String> values) {
            addCriterion("PARENT_AGENT_ID not in", values, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_ID between", value1, value2, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_ID not between", value1, value2, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameIsNull() {
            addCriterion("PARENT_AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameIsNotNull() {
            addCriterion("PARENT_AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME =", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME <>", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameGreaterThan(String value) {
            addCriterion("PARENT_AGENT_NAME >", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME >=", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameLessThan(String value) {
            addCriterion("PARENT_AGENT_NAME <", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameLessThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME <=", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameLike(String value) {
            addCriterion("PARENT_AGENT_NAME like", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotLike(String value) {
            addCriterion("PARENT_AGENT_NAME not like", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameIn(List<String> values) {
            addCriterion("PARENT_AGENT_NAME in", values, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotIn(List<String> values) {
            addCriterion("PARENT_AGENT_NAME not in", values, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_NAME between", value1, value2, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_NAME not between", value1, value2, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andApplicationMonthIsNull() {
            addCriterion("APPLICATION_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andApplicationMonthIsNotNull() {
            addCriterion("APPLICATION_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andApplicationMonthEqualTo(String value) {
            addCriterion("APPLICATION_MONTH =", value, "applicationMonth");
            return (Criteria) this;
        }

        public Criteria andApplicationMonthNotEqualTo(String value) {
            addCriterion("APPLICATION_MONTH <>", value, "applicationMonth");
            return (Criteria) this;
        }

        public Criteria andApplicationMonthGreaterThan(String value) {
            addCriterion("APPLICATION_MONTH >", value, "applicationMonth");
            return (Criteria) this;
        }

        public Criteria andApplicationMonthGreaterThanOrEqualTo(String value) {
            addCriterion("APPLICATION_MONTH >=", value, "applicationMonth");
            return (Criteria) this;
        }

        public Criteria andApplicationMonthLessThan(String value) {
            addCriterion("APPLICATION_MONTH <", value, "applicationMonth");
            return (Criteria) this;
        }

        public Criteria andApplicationMonthLessThanOrEqualTo(String value) {
            addCriterion("APPLICATION_MONTH <=", value, "applicationMonth");
            return (Criteria) this;
        }

        public Criteria andApplicationMonthLike(String value) {
            addCriterion("APPLICATION_MONTH like", value, "applicationMonth");
            return (Criteria) this;
        }

        public Criteria andApplicationMonthNotLike(String value) {
            addCriterion("APPLICATION_MONTH not like", value, "applicationMonth");
            return (Criteria) this;
        }

        public Criteria andApplicationMonthIn(List<String> values) {
            addCriterion("APPLICATION_MONTH in", values, "applicationMonth");
            return (Criteria) this;
        }

        public Criteria andApplicationMonthNotIn(List<String> values) {
            addCriterion("APPLICATION_MONTH not in", values, "applicationMonth");
            return (Criteria) this;
        }

        public Criteria andApplicationMonthBetween(String value1, String value2) {
            addCriterion("APPLICATION_MONTH between", value1, value2, "applicationMonth");
            return (Criteria) this;
        }

        public Criteria andApplicationMonthNotBetween(String value1, String value2) {
            addCriterion("APPLICATION_MONTH not between", value1, value2, "applicationMonth");
            return (Criteria) this;
        }

        public Criteria andApplicationAmtIsNull() {
            addCriterion("APPLICATION_AMT is null");
            return (Criteria) this;
        }

        public Criteria andApplicationAmtIsNotNull() {
            addCriterion("APPLICATION_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andApplicationAmtEqualTo(BigDecimal value) {
            addCriterion("APPLICATION_AMT =", value, "applicationAmt");
            return (Criteria) this;
        }

        public Criteria andApplicationAmtNotEqualTo(BigDecimal value) {
            addCriterion("APPLICATION_AMT <>", value, "applicationAmt");
            return (Criteria) this;
        }

        public Criteria andApplicationAmtGreaterThan(BigDecimal value) {
            addCriterion("APPLICATION_AMT >", value, "applicationAmt");
            return (Criteria) this;
        }

        public Criteria andApplicationAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("APPLICATION_AMT >=", value, "applicationAmt");
            return (Criteria) this;
        }

        public Criteria andApplicationAmtLessThan(BigDecimal value) {
            addCriterion("APPLICATION_AMT <", value, "applicationAmt");
            return (Criteria) this;
        }

        public Criteria andApplicationAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("APPLICATION_AMT <=", value, "applicationAmt");
            return (Criteria) this;
        }

        public Criteria andApplicationAmtIn(List<BigDecimal> values) {
            addCriterion("APPLICATION_AMT in", values, "applicationAmt");
            return (Criteria) this;
        }

        public Criteria andApplicationAmtNotIn(List<BigDecimal> values) {
            addCriterion("APPLICATION_AMT not in", values, "applicationAmt");
            return (Criteria) this;
        }

        public Criteria andApplicationAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APPLICATION_AMT between", value1, value2, "applicationAmt");
            return (Criteria) this;
        }

        public Criteria andApplicationAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APPLICATION_AMT not between", value1, value2, "applicationAmt");
            return (Criteria) this;
        }

        public Criteria andDeductionRemarkIsNull() {
            addCriterion("DEDUCTION_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andDeductionRemarkIsNotNull() {
            addCriterion("DEDUCTION_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andDeductionRemarkEqualTo(String value) {
            addCriterion("DEDUCTION_REMARK =", value, "deductionRemark");
            return (Criteria) this;
        }

        public Criteria andDeductionRemarkNotEqualTo(String value) {
            addCriterion("DEDUCTION_REMARK <>", value, "deductionRemark");
            return (Criteria) this;
        }

        public Criteria andDeductionRemarkGreaterThan(String value) {
            addCriterion("DEDUCTION_REMARK >", value, "deductionRemark");
            return (Criteria) this;
        }

        public Criteria andDeductionRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("DEDUCTION_REMARK >=", value, "deductionRemark");
            return (Criteria) this;
        }

        public Criteria andDeductionRemarkLessThan(String value) {
            addCriterion("DEDUCTION_REMARK <", value, "deductionRemark");
            return (Criteria) this;
        }

        public Criteria andDeductionRemarkLessThanOrEqualTo(String value) {
            addCriterion("DEDUCTION_REMARK <=", value, "deductionRemark");
            return (Criteria) this;
        }

        public Criteria andDeductionRemarkLike(String value) {
            addCriterion("DEDUCTION_REMARK like", value, "deductionRemark");
            return (Criteria) this;
        }

        public Criteria andDeductionRemarkNotLike(String value) {
            addCriterion("DEDUCTION_REMARK not like", value, "deductionRemark");
            return (Criteria) this;
        }

        public Criteria andDeductionRemarkIn(List<String> values) {
            addCriterion("DEDUCTION_REMARK in", values, "deductionRemark");
            return (Criteria) this;
        }

        public Criteria andDeductionRemarkNotIn(List<String> values) {
            addCriterion("DEDUCTION_REMARK not in", values, "deductionRemark");
            return (Criteria) this;
        }

        public Criteria andDeductionRemarkBetween(String value1, String value2) {
            addCriterion("DEDUCTION_REMARK between", value1, value2, "deductionRemark");
            return (Criteria) this;
        }

        public Criteria andDeductionRemarkNotBetween(String value1, String value2) {
            addCriterion("DEDUCTION_REMARK not between", value1, value2, "deductionRemark");
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

        public Criteria andApplicationStatusIsNull() {
            addCriterion("APPLICATION_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusIsNotNull() {
            addCriterion("APPLICATION_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusEqualTo(String value) {
            addCriterion("APPLICATION_STATUS =", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusNotEqualTo(String value) {
            addCriterion("APPLICATION_STATUS <>", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusGreaterThan(String value) {
            addCriterion("APPLICATION_STATUS >", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusGreaterThanOrEqualTo(String value) {
            addCriterion("APPLICATION_STATUS >=", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusLessThan(String value) {
            addCriterion("APPLICATION_STATUS <", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusLessThanOrEqualTo(String value) {
            addCriterion("APPLICATION_STATUS <=", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusLike(String value) {
            addCriterion("APPLICATION_STATUS like", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusNotLike(String value) {
            addCriterion("APPLICATION_STATUS not like", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusIn(List<String> values) {
            addCriterion("APPLICATION_STATUS in", values, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusNotIn(List<String> values) {
            addCriterion("APPLICATION_STATUS not in", values, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusBetween(String value1, String value2) {
            addCriterion("APPLICATION_STATUS between", value1, value2, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusNotBetween(String value1, String value2) {
            addCriterion("APPLICATION_STATUS not between", value1, value2, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNull() {
            addCriterion("CREATE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNotNull() {
            addCriterion("CREATE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateNameEqualTo(String value) {
            addCriterion("CREATE_NAME =", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotEqualTo(String value) {
            addCriterion("CREATE_NAME <>", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThan(String value) {
            addCriterion("CREATE_NAME >", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_NAME >=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThan(String value) {
            addCriterion("CREATE_NAME <", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThanOrEqualTo(String value) {
            addCriterion("CREATE_NAME <=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLike(String value) {
            addCriterion("CREATE_NAME like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotLike(String value) {
            addCriterion("CREATE_NAME not like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameIn(List<String> values) {
            addCriterion("CREATE_NAME in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotIn(List<String> values) {
            addCriterion("CREATE_NAME not in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameBetween(String value1, String value2) {
            addCriterion("CREATE_NAME between", value1, value2, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotBetween(String value1, String value2) {
            addCriterion("CREATE_NAME not between", value1, value2, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("CREATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("CREATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("CREATE_DATE =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("CREATE_DATE <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("CREATE_DATE >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("CREATE_DATE <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("CREATE_DATE in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("CREATE_DATE not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE not between", value1, value2, "createDate");
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