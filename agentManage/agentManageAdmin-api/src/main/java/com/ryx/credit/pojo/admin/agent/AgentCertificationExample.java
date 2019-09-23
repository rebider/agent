package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentCertificationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AgentCertificationExample() {
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

        public Criteria andReqRegNoIsNull() {
            addCriterion("REQ_REG_NO is null");
            return (Criteria) this;
        }

        public Criteria andReqRegNoIsNotNull() {
            addCriterion("REQ_REG_NO is not null");
            return (Criteria) this;
        }

        public Criteria andReqRegNoEqualTo(String value) {
            addCriterion("REQ_REG_NO =", value, "reqRegNo");
            return (Criteria) this;
        }

        public Criteria andReqRegNoNotEqualTo(String value) {
            addCriterion("REQ_REG_NO <>", value, "reqRegNo");
            return (Criteria) this;
        }

        public Criteria andReqRegNoGreaterThan(String value) {
            addCriterion("REQ_REG_NO >", value, "reqRegNo");
            return (Criteria) this;
        }

        public Criteria andReqRegNoGreaterThanOrEqualTo(String value) {
            addCriterion("REQ_REG_NO >=", value, "reqRegNo");
            return (Criteria) this;
        }

        public Criteria andReqRegNoLessThan(String value) {
            addCriterion("REQ_REG_NO <", value, "reqRegNo");
            return (Criteria) this;
        }

        public Criteria andReqRegNoLessThanOrEqualTo(String value) {
            addCriterion("REQ_REG_NO <=", value, "reqRegNo");
            return (Criteria) this;
        }

        public Criteria andReqRegNoLike(String value) {
            addCriterion("REQ_REG_NO like", value, "reqRegNo");
            return (Criteria) this;
        }

        public Criteria andReqRegNoNotLike(String value) {
            addCriterion("REQ_REG_NO not like", value, "reqRegNo");
            return (Criteria) this;
        }

        public Criteria andReqRegNoIn(List<String> values) {
            addCriterion("REQ_REG_NO in", values, "reqRegNo");
            return (Criteria) this;
        }

        public Criteria andReqRegNoNotIn(List<String> values) {
            addCriterion("REQ_REG_NO not in", values, "reqRegNo");
            return (Criteria) this;
        }

        public Criteria andReqRegNoBetween(String value1, String value2) {
            addCriterion("REQ_REG_NO between", value1, value2, "reqRegNo");
            return (Criteria) this;
        }

        public Criteria andReqRegNoNotBetween(String value1, String value2) {
            addCriterion("REQ_REG_NO not between", value1, value2, "reqRegNo");
            return (Criteria) this;
        }

        public Criteria andReqEntNameIsNull() {
            addCriterion("REQ_ENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andReqEntNameIsNotNull() {
            addCriterion("REQ_ENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andReqEntNameEqualTo(String value) {
            addCriterion("REQ_ENT_NAME =", value, "reqEntName");
            return (Criteria) this;
        }

        public Criteria andReqEntNameNotEqualTo(String value) {
            addCriterion("REQ_ENT_NAME <>", value, "reqEntName");
            return (Criteria) this;
        }

        public Criteria andReqEntNameGreaterThan(String value) {
            addCriterion("REQ_ENT_NAME >", value, "reqEntName");
            return (Criteria) this;
        }

        public Criteria andReqEntNameGreaterThanOrEqualTo(String value) {
            addCriterion("REQ_ENT_NAME >=", value, "reqEntName");
            return (Criteria) this;
        }

        public Criteria andReqEntNameLessThan(String value) {
            addCriterion("REQ_ENT_NAME <", value, "reqEntName");
            return (Criteria) this;
        }

        public Criteria andReqEntNameLessThanOrEqualTo(String value) {
            addCriterion("REQ_ENT_NAME <=", value, "reqEntName");
            return (Criteria) this;
        }

        public Criteria andReqEntNameLike(String value) {
            addCriterion("REQ_ENT_NAME like", value, "reqEntName");
            return (Criteria) this;
        }

        public Criteria andReqEntNameNotLike(String value) {
            addCriterion("REQ_ENT_NAME not like", value, "reqEntName");
            return (Criteria) this;
        }

        public Criteria andReqEntNameIn(List<String> values) {
            addCriterion("REQ_ENT_NAME in", values, "reqEntName");
            return (Criteria) this;
        }

        public Criteria andReqEntNameNotIn(List<String> values) {
            addCriterion("REQ_ENT_NAME not in", values, "reqEntName");
            return (Criteria) this;
        }

        public Criteria andReqEntNameBetween(String value1, String value2) {
            addCriterion("REQ_ENT_NAME between", value1, value2, "reqEntName");
            return (Criteria) this;
        }

        public Criteria andReqEntNameNotBetween(String value1, String value2) {
            addCriterion("REQ_ENT_NAME not between", value1, value2, "reqEntName");
            return (Criteria) this;
        }

        public Criteria andIsCacheIsNull() {
            addCriterion("IS_CACHE is null");
            return (Criteria) this;
        }

        public Criteria andIsCacheIsNotNull() {
            addCriterion("IS_CACHE is not null");
            return (Criteria) this;
        }

        public Criteria andIsCacheEqualTo(String value) {
            addCriterion("IS_CACHE =", value, "isCache");
            return (Criteria) this;
        }

        public Criteria andIsCacheNotEqualTo(String value) {
            addCriterion("IS_CACHE <>", value, "isCache");
            return (Criteria) this;
        }

        public Criteria andIsCacheGreaterThan(String value) {
            addCriterion("IS_CACHE >", value, "isCache");
            return (Criteria) this;
        }

        public Criteria andIsCacheGreaterThanOrEqualTo(String value) {
            addCriterion("IS_CACHE >=", value, "isCache");
            return (Criteria) this;
        }

        public Criteria andIsCacheLessThan(String value) {
            addCriterion("IS_CACHE <", value, "isCache");
            return (Criteria) this;
        }

        public Criteria andIsCacheLessThanOrEqualTo(String value) {
            addCriterion("IS_CACHE <=", value, "isCache");
            return (Criteria) this;
        }

        public Criteria andIsCacheLike(String value) {
            addCriterion("IS_CACHE like", value, "isCache");
            return (Criteria) this;
        }

        public Criteria andIsCacheNotLike(String value) {
            addCriterion("IS_CACHE not like", value, "isCache");
            return (Criteria) this;
        }

        public Criteria andIsCacheIn(List<String> values) {
            addCriterion("IS_CACHE in", values, "isCache");
            return (Criteria) this;
        }

        public Criteria andIsCacheNotIn(List<String> values) {
            addCriterion("IS_CACHE not in", values, "isCache");
            return (Criteria) this;
        }

        public Criteria andIsCacheBetween(String value1, String value2) {
            addCriterion("IS_CACHE between", value1, value2, "isCache");
            return (Criteria) this;
        }

        public Criteria andIsCacheNotBetween(String value1, String value2) {
            addCriterion("IS_CACHE not between", value1, value2, "isCache");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameIsNull() {
            addCriterion("ENTERPRISE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameIsNotNull() {
            addCriterion("ENTERPRISE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameEqualTo(String value) {
            addCriterion("ENTERPRISE_NAME =", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameNotEqualTo(String value) {
            addCriterion("ENTERPRISE_NAME <>", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameGreaterThan(String value) {
            addCriterion("ENTERPRISE_NAME >", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameGreaterThanOrEqualTo(String value) {
            addCriterion("ENTERPRISE_NAME >=", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameLessThan(String value) {
            addCriterion("ENTERPRISE_NAME <", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameLessThanOrEqualTo(String value) {
            addCriterion("ENTERPRISE_NAME <=", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameLike(String value) {
            addCriterion("ENTERPRISE_NAME like", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameNotLike(String value) {
            addCriterion("ENTERPRISE_NAME not like", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameIn(List<String> values) {
            addCriterion("ENTERPRISE_NAME in", values, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameNotIn(List<String> values) {
            addCriterion("ENTERPRISE_NAME not in", values, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameBetween(String value1, String value2) {
            addCriterion("ENTERPRISE_NAME between", value1, value2, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameNotBetween(String value1, String value2) {
            addCriterion("ENTERPRISE_NAME not between", value1, value2, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andFrNameIsNull() {
            addCriterion("FR_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFrNameIsNotNull() {
            addCriterion("FR_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFrNameEqualTo(String value) {
            addCriterion("FR_NAME =", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameNotEqualTo(String value) {
            addCriterion("FR_NAME <>", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameGreaterThan(String value) {
            addCriterion("FR_NAME >", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameGreaterThanOrEqualTo(String value) {
            addCriterion("FR_NAME >=", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameLessThan(String value) {
            addCriterion("FR_NAME <", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameLessThanOrEqualTo(String value) {
            addCriterion("FR_NAME <=", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameLike(String value) {
            addCriterion("FR_NAME like", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameNotLike(String value) {
            addCriterion("FR_NAME not like", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameIn(List<String> values) {
            addCriterion("FR_NAME in", values, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameNotIn(List<String> values) {
            addCriterion("FR_NAME not in", values, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameBetween(String value1, String value2) {
            addCriterion("FR_NAME between", value1, value2, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameNotBetween(String value1, String value2) {
            addCriterion("FR_NAME not between", value1, value2, "frName");
            return (Criteria) this;
        }

        public Criteria andRegNoIsNull() {
            addCriterion("REG_NO is null");
            return (Criteria) this;
        }

        public Criteria andRegNoIsNotNull() {
            addCriterion("REG_NO is not null");
            return (Criteria) this;
        }

        public Criteria andRegNoEqualTo(String value) {
            addCriterion("REG_NO =", value, "regNo");
            return (Criteria) this;
        }

        public Criteria andRegNoNotEqualTo(String value) {
            addCriterion("REG_NO <>", value, "regNo");
            return (Criteria) this;
        }

        public Criteria andRegNoGreaterThan(String value) {
            addCriterion("REG_NO >", value, "regNo");
            return (Criteria) this;
        }

        public Criteria andRegNoGreaterThanOrEqualTo(String value) {
            addCriterion("REG_NO >=", value, "regNo");
            return (Criteria) this;
        }

        public Criteria andRegNoLessThan(String value) {
            addCriterion("REG_NO <", value, "regNo");
            return (Criteria) this;
        }

        public Criteria andRegNoLessThanOrEqualTo(String value) {
            addCriterion("REG_NO <=", value, "regNo");
            return (Criteria) this;
        }

        public Criteria andRegNoLike(String value) {
            addCriterion("REG_NO like", value, "regNo");
            return (Criteria) this;
        }

        public Criteria andRegNoNotLike(String value) {
            addCriterion("REG_NO not like", value, "regNo");
            return (Criteria) this;
        }

        public Criteria andRegNoIn(List<String> values) {
            addCriterion("REG_NO in", values, "regNo");
            return (Criteria) this;
        }

        public Criteria andRegNoNotIn(List<String> values) {
            addCriterion("REG_NO not in", values, "regNo");
            return (Criteria) this;
        }

        public Criteria andRegNoBetween(String value1, String value2) {
            addCriterion("REG_NO between", value1, value2, "regNo");
            return (Criteria) this;
        }

        public Criteria andRegNoNotBetween(String value1, String value2) {
            addCriterion("REG_NO not between", value1, value2, "regNo");
            return (Criteria) this;
        }

        public Criteria andRegCapIsNull() {
            addCriterion("REG_CAP is null");
            return (Criteria) this;
        }

        public Criteria andRegCapIsNotNull() {
            addCriterion("REG_CAP is not null");
            return (Criteria) this;
        }

        public Criteria andRegCapEqualTo(BigDecimal value) {
            addCriterion("REG_CAP =", value, "regCap");
            return (Criteria) this;
        }

        public Criteria andRegCapNotEqualTo(BigDecimal value) {
            addCriterion("REG_CAP <>", value, "regCap");
            return (Criteria) this;
        }

        public Criteria andRegCapGreaterThan(BigDecimal value) {
            addCriterion("REG_CAP >", value, "regCap");
            return (Criteria) this;
        }

        public Criteria andRegCapGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REG_CAP >=", value, "regCap");
            return (Criteria) this;
        }

        public Criteria andRegCapLessThan(BigDecimal value) {
            addCriterion("REG_CAP <", value, "regCap");
            return (Criteria) this;
        }

        public Criteria andRegCapLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REG_CAP <=", value, "regCap");
            return (Criteria) this;
        }

        public Criteria andRegCapIn(List<BigDecimal> values) {
            addCriterion("REG_CAP in", values, "regCap");
            return (Criteria) this;
        }

        public Criteria andRegCapNotIn(List<BigDecimal> values) {
            addCriterion("REG_CAP not in", values, "regCap");
            return (Criteria) this;
        }

        public Criteria andRegCapBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REG_CAP between", value1, value2, "regCap");
            return (Criteria) this;
        }

        public Criteria andRegCapNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REG_CAP not between", value1, value2, "regCap");
            return (Criteria) this;
        }

        public Criteria andRegCapCurIsNull() {
            addCriterion("REG_CAP_CUR is null");
            return (Criteria) this;
        }

        public Criteria andRegCapCurIsNotNull() {
            addCriterion("REG_CAP_CUR is not null");
            return (Criteria) this;
        }

        public Criteria andRegCapCurEqualTo(String value) {
            addCriterion("REG_CAP_CUR =", value, "regCapCur");
            return (Criteria) this;
        }

        public Criteria andRegCapCurNotEqualTo(String value) {
            addCriterion("REG_CAP_CUR <>", value, "regCapCur");
            return (Criteria) this;
        }

        public Criteria andRegCapCurGreaterThan(String value) {
            addCriterion("REG_CAP_CUR >", value, "regCapCur");
            return (Criteria) this;
        }

        public Criteria andRegCapCurGreaterThanOrEqualTo(String value) {
            addCriterion("REG_CAP_CUR >=", value, "regCapCur");
            return (Criteria) this;
        }

        public Criteria andRegCapCurLessThan(String value) {
            addCriterion("REG_CAP_CUR <", value, "regCapCur");
            return (Criteria) this;
        }

        public Criteria andRegCapCurLessThanOrEqualTo(String value) {
            addCriterion("REG_CAP_CUR <=", value, "regCapCur");
            return (Criteria) this;
        }

        public Criteria andRegCapCurLike(String value) {
            addCriterion("REG_CAP_CUR like", value, "regCapCur");
            return (Criteria) this;
        }

        public Criteria andRegCapCurNotLike(String value) {
            addCriterion("REG_CAP_CUR not like", value, "regCapCur");
            return (Criteria) this;
        }

        public Criteria andRegCapCurIn(List<String> values) {
            addCriterion("REG_CAP_CUR in", values, "regCapCur");
            return (Criteria) this;
        }

        public Criteria andRegCapCurNotIn(List<String> values) {
            addCriterion("REG_CAP_CUR not in", values, "regCapCur");
            return (Criteria) this;
        }

        public Criteria andRegCapCurBetween(String value1, String value2) {
            addCriterion("REG_CAP_CUR between", value1, value2, "regCapCur");
            return (Criteria) this;
        }

        public Criteria andRegCapCurNotBetween(String value1, String value2) {
            addCriterion("REG_CAP_CUR not between", value1, value2, "regCapCur");
            return (Criteria) this;
        }

        public Criteria andEsDateIsNull() {
            addCriterion("ES_DATE is null");
            return (Criteria) this;
        }

        public Criteria andEsDateIsNotNull() {
            addCriterion("ES_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andEsDateEqualTo(String value) {
            addCriterion("ES_DATE =", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateNotEqualTo(String value) {
            addCriterion("ES_DATE <>", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateGreaterThan(String value) {
            addCriterion("ES_DATE >", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateGreaterThanOrEqualTo(String value) {
            addCriterion("ES_DATE >=", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateLessThan(String value) {
            addCriterion("ES_DATE <", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateLessThanOrEqualTo(String value) {
            addCriterion("ES_DATE <=", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateLike(String value) {
            addCriterion("ES_DATE like", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateNotLike(String value) {
            addCriterion("ES_DATE not like", value, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateIn(List<String> values) {
            addCriterion("ES_DATE in", values, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateNotIn(List<String> values) {
            addCriterion("ES_DATE not in", values, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateBetween(String value1, String value2) {
            addCriterion("ES_DATE between", value1, value2, "esDate");
            return (Criteria) this;
        }

        public Criteria andEsDateNotBetween(String value1, String value2) {
            addCriterion("ES_DATE not between", value1, value2, "esDate");
            return (Criteria) this;
        }

        public Criteria andAgBusLicIsNull() {
            addCriterion("AG_BUS_LIC is null");
            return (Criteria) this;
        }

        public Criteria andAgBusLicIsNotNull() {
            addCriterion("AG_BUS_LIC is not null");
            return (Criteria) this;
        }

        public Criteria andAgBusLicEqualTo(String value) {
            addCriterion("AG_BUS_LIC =", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicNotEqualTo(String value) {
            addCriterion("AG_BUS_LIC <>", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicGreaterThan(String value) {
            addCriterion("AG_BUS_LIC >", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicGreaterThanOrEqualTo(String value) {
            addCriterion("AG_BUS_LIC >=", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicLessThan(String value) {
            addCriterion("AG_BUS_LIC <", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicLessThanOrEqualTo(String value) {
            addCriterion("AG_BUS_LIC <=", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicLike(String value) {
            addCriterion("AG_BUS_LIC like", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicNotLike(String value) {
            addCriterion("AG_BUS_LIC not like", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicIn(List<String> values) {
            addCriterion("AG_BUS_LIC in", values, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicNotIn(List<String> values) {
            addCriterion("AG_BUS_LIC not in", values, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicBetween(String value1, String value2) {
            addCriterion("AG_BUS_LIC between", value1, value2, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicNotBetween(String value1, String value2) {
            addCriterion("AG_BUS_LIC not between", value1, value2, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andOpenFromIsNull() {
            addCriterion("OPEN_FROM is null");
            return (Criteria) this;
        }

        public Criteria andOpenFromIsNotNull() {
            addCriterion("OPEN_FROM is not null");
            return (Criteria) this;
        }

        public Criteria andOpenFromEqualTo(Date value) {
            addCriterion("OPEN_FROM =", value, "openFrom");
            return (Criteria) this;
        }

        public Criteria andOpenFromNotEqualTo(Date value) {
            addCriterion("OPEN_FROM <>", value, "openFrom");
            return (Criteria) this;
        }

        public Criteria andOpenFromGreaterThan(Date value) {
            addCriterion("OPEN_FROM >", value, "openFrom");
            return (Criteria) this;
        }

        public Criteria andOpenFromGreaterThanOrEqualTo(Date value) {
            addCriterion("OPEN_FROM >=", value, "openFrom");
            return (Criteria) this;
        }

        public Criteria andOpenFromLessThan(Date value) {
            addCriterion("OPEN_FROM <", value, "openFrom");
            return (Criteria) this;
        }

        public Criteria andOpenFromLessThanOrEqualTo(Date value) {
            addCriterion("OPEN_FROM <=", value, "openFrom");
            return (Criteria) this;
        }

        public Criteria andOpenFromIn(List<Date> values) {
            addCriterion("OPEN_FROM in", values, "openFrom");
            return (Criteria) this;
        }

        public Criteria andOpenFromNotIn(List<Date> values) {
            addCriterion("OPEN_FROM not in", values, "openFrom");
            return (Criteria) this;
        }

        public Criteria andOpenFromBetween(Date value1, Date value2) {
            addCriterion("OPEN_FROM between", value1, value2, "openFrom");
            return (Criteria) this;
        }

        public Criteria andOpenFromNotBetween(Date value1, Date value2) {
            addCriterion("OPEN_FROM not between", value1, value2, "openFrom");
            return (Criteria) this;
        }

        public Criteria andOpenToIsNull() {
            addCriterion("OPEN_TO is null");
            return (Criteria) this;
        }

        public Criteria andOpenToIsNotNull() {
            addCriterion("OPEN_TO is not null");
            return (Criteria) this;
        }

        public Criteria andOpenToEqualTo(Date value) {
            addCriterion("OPEN_TO =", value, "openTo");
            return (Criteria) this;
        }

        public Criteria andOpenToNotEqualTo(Date value) {
            addCriterion("OPEN_TO <>", value, "openTo");
            return (Criteria) this;
        }

        public Criteria andOpenToGreaterThan(Date value) {
            addCriterion("OPEN_TO >", value, "openTo");
            return (Criteria) this;
        }

        public Criteria andOpenToGreaterThanOrEqualTo(Date value) {
            addCriterion("OPEN_TO >=", value, "openTo");
            return (Criteria) this;
        }

        public Criteria andOpenToLessThan(Date value) {
            addCriterion("OPEN_TO <", value, "openTo");
            return (Criteria) this;
        }

        public Criteria andOpenToLessThanOrEqualTo(Date value) {
            addCriterion("OPEN_TO <=", value, "openTo");
            return (Criteria) this;
        }

        public Criteria andOpenToIn(List<Date> values) {
            addCriterion("OPEN_TO in", values, "openTo");
            return (Criteria) this;
        }

        public Criteria andOpenToNotIn(List<Date> values) {
            addCriterion("OPEN_TO not in", values, "openTo");
            return (Criteria) this;
        }

        public Criteria andOpenToBetween(Date value1, Date value2) {
            addCriterion("OPEN_TO between", value1, value2, "openTo");
            return (Criteria) this;
        }

        public Criteria andOpenToNotBetween(Date value1, Date value2) {
            addCriterion("OPEN_TO not between", value1, value2, "openTo");
            return (Criteria) this;
        }

        public Criteria andEnterpriseTypeIsNull() {
            addCriterion("ENTERPRISE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andEnterpriseTypeIsNotNull() {
            addCriterion("ENTERPRISE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andEnterpriseTypeEqualTo(String value) {
            addCriterion("ENTERPRISE_TYPE =", value, "enterpriseType");
            return (Criteria) this;
        }

        public Criteria andEnterpriseTypeNotEqualTo(String value) {
            addCriterion("ENTERPRISE_TYPE <>", value, "enterpriseType");
            return (Criteria) this;
        }

        public Criteria andEnterpriseTypeGreaterThan(String value) {
            addCriterion("ENTERPRISE_TYPE >", value, "enterpriseType");
            return (Criteria) this;
        }

        public Criteria andEnterpriseTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ENTERPRISE_TYPE >=", value, "enterpriseType");
            return (Criteria) this;
        }

        public Criteria andEnterpriseTypeLessThan(String value) {
            addCriterion("ENTERPRISE_TYPE <", value, "enterpriseType");
            return (Criteria) this;
        }

        public Criteria andEnterpriseTypeLessThanOrEqualTo(String value) {
            addCriterion("ENTERPRISE_TYPE <=", value, "enterpriseType");
            return (Criteria) this;
        }

        public Criteria andEnterpriseTypeLike(String value) {
            addCriterion("ENTERPRISE_TYPE like", value, "enterpriseType");
            return (Criteria) this;
        }

        public Criteria andEnterpriseTypeNotLike(String value) {
            addCriterion("ENTERPRISE_TYPE not like", value, "enterpriseType");
            return (Criteria) this;
        }

        public Criteria andEnterpriseTypeIn(List<String> values) {
            addCriterion("ENTERPRISE_TYPE in", values, "enterpriseType");
            return (Criteria) this;
        }

        public Criteria andEnterpriseTypeNotIn(List<String> values) {
            addCriterion("ENTERPRISE_TYPE not in", values, "enterpriseType");
            return (Criteria) this;
        }

        public Criteria andEnterpriseTypeBetween(String value1, String value2) {
            addCriterion("ENTERPRISE_TYPE between", value1, value2, "enterpriseType");
            return (Criteria) this;
        }

        public Criteria andEnterpriseTypeNotBetween(String value1, String value2) {
            addCriterion("ENTERPRISE_TYPE not between", value1, value2, "enterpriseType");
            return (Criteria) this;
        }

        public Criteria andEnterpriseStatusIsNull() {
            addCriterion("ENTERPRISE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andEnterpriseStatusIsNotNull() {
            addCriterion("ENTERPRISE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andEnterpriseStatusEqualTo(String value) {
            addCriterion("ENTERPRISE_STATUS =", value, "enterpriseStatus");
            return (Criteria) this;
        }

        public Criteria andEnterpriseStatusNotEqualTo(String value) {
            addCriterion("ENTERPRISE_STATUS <>", value, "enterpriseStatus");
            return (Criteria) this;
        }

        public Criteria andEnterpriseStatusGreaterThan(String value) {
            addCriterion("ENTERPRISE_STATUS >", value, "enterpriseStatus");
            return (Criteria) this;
        }

        public Criteria andEnterpriseStatusGreaterThanOrEqualTo(String value) {
            addCriterion("ENTERPRISE_STATUS >=", value, "enterpriseStatus");
            return (Criteria) this;
        }

        public Criteria andEnterpriseStatusLessThan(String value) {
            addCriterion("ENTERPRISE_STATUS <", value, "enterpriseStatus");
            return (Criteria) this;
        }

        public Criteria andEnterpriseStatusLessThanOrEqualTo(String value) {
            addCriterion("ENTERPRISE_STATUS <=", value, "enterpriseStatus");
            return (Criteria) this;
        }

        public Criteria andEnterpriseStatusLike(String value) {
            addCriterion("ENTERPRISE_STATUS like", value, "enterpriseStatus");
            return (Criteria) this;
        }

        public Criteria andEnterpriseStatusNotLike(String value) {
            addCriterion("ENTERPRISE_STATUS not like", value, "enterpriseStatus");
            return (Criteria) this;
        }

        public Criteria andEnterpriseStatusIn(List<String> values) {
            addCriterion("ENTERPRISE_STATUS in", values, "enterpriseStatus");
            return (Criteria) this;
        }

        public Criteria andEnterpriseStatusNotIn(List<String> values) {
            addCriterion("ENTERPRISE_STATUS not in", values, "enterpriseStatus");
            return (Criteria) this;
        }

        public Criteria andEnterpriseStatusBetween(String value1, String value2) {
            addCriterion("ENTERPRISE_STATUS between", value1, value2, "enterpriseStatus");
            return (Criteria) this;
        }

        public Criteria andEnterpriseStatusNotBetween(String value1, String value2) {
            addCriterion("ENTERPRISE_STATUS not between", value1, value2, "enterpriseStatus");
            return (Criteria) this;
        }

        public Criteria andCancelDateIsNull() {
            addCriterion("CANCEL_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCancelDateIsNotNull() {
            addCriterion("CANCEL_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCancelDateEqualTo(Date value) {
            addCriterion("CANCEL_DATE =", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateNotEqualTo(Date value) {
            addCriterion("CANCEL_DATE <>", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateGreaterThan(Date value) {
            addCriterion("CANCEL_DATE >", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateGreaterThanOrEqualTo(Date value) {
            addCriterion("CANCEL_DATE >=", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateLessThan(Date value) {
            addCriterion("CANCEL_DATE <", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateLessThanOrEqualTo(Date value) {
            addCriterion("CANCEL_DATE <=", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateIn(List<Date> values) {
            addCriterion("CANCEL_DATE in", values, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateNotIn(List<Date> values) {
            addCriterion("CANCEL_DATE not in", values, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateBetween(Date value1, Date value2) {
            addCriterion("CANCEL_DATE between", value1, value2, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateNotBetween(Date value1, Date value2) {
            addCriterion("CANCEL_DATE not between", value1, value2, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andRevokeDateIsNull() {
            addCriterion("REVOKE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andRevokeDateIsNotNull() {
            addCriterion("REVOKE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andRevokeDateEqualTo(Date value) {
            addCriterion("REVOKE_DATE =", value, "revokeDate");
            return (Criteria) this;
        }

        public Criteria andRevokeDateNotEqualTo(Date value) {
            addCriterion("REVOKE_DATE <>", value, "revokeDate");
            return (Criteria) this;
        }

        public Criteria andRevokeDateGreaterThan(Date value) {
            addCriterion("REVOKE_DATE >", value, "revokeDate");
            return (Criteria) this;
        }

        public Criteria andRevokeDateGreaterThanOrEqualTo(Date value) {
            addCriterion("REVOKE_DATE >=", value, "revokeDate");
            return (Criteria) this;
        }

        public Criteria andRevokeDateLessThan(Date value) {
            addCriterion("REVOKE_DATE <", value, "revokeDate");
            return (Criteria) this;
        }

        public Criteria andRevokeDateLessThanOrEqualTo(Date value) {
            addCriterion("REVOKE_DATE <=", value, "revokeDate");
            return (Criteria) this;
        }

        public Criteria andRevokeDateIn(List<Date> values) {
            addCriterion("REVOKE_DATE in", values, "revokeDate");
            return (Criteria) this;
        }

        public Criteria andRevokeDateNotIn(List<Date> values) {
            addCriterion("REVOKE_DATE not in", values, "revokeDate");
            return (Criteria) this;
        }

        public Criteria andRevokeDateBetween(Date value1, Date value2) {
            addCriterion("REVOKE_DATE between", value1, value2, "revokeDate");
            return (Criteria) this;
        }

        public Criteria andRevokeDateNotBetween(Date value1, Date value2) {
            addCriterion("REVOKE_DATE not between", value1, value2, "revokeDate");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("ADDRESS is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("ADDRESS is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("ADDRESS =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("ADDRESS <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("ADDRESS >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("ADDRESS >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("ADDRESS <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("ADDRESS <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("ADDRESS like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("ADDRESS not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("ADDRESS in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("ADDRESS not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("ADDRESS between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("ADDRESS not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAbuItemIsNull() {
            addCriterion("ABU_ITEM is null");
            return (Criteria) this;
        }

        public Criteria andAbuItemIsNotNull() {
            addCriterion("ABU_ITEM is not null");
            return (Criteria) this;
        }

        public Criteria andAbuItemEqualTo(String value) {
            addCriterion("ABU_ITEM =", value, "abuItem");
            return (Criteria) this;
        }

        public Criteria andAbuItemNotEqualTo(String value) {
            addCriterion("ABU_ITEM <>", value, "abuItem");
            return (Criteria) this;
        }

        public Criteria andAbuItemGreaterThan(String value) {
            addCriterion("ABU_ITEM >", value, "abuItem");
            return (Criteria) this;
        }

        public Criteria andAbuItemGreaterThanOrEqualTo(String value) {
            addCriterion("ABU_ITEM >=", value, "abuItem");
            return (Criteria) this;
        }

        public Criteria andAbuItemLessThan(String value) {
            addCriterion("ABU_ITEM <", value, "abuItem");
            return (Criteria) this;
        }

        public Criteria andAbuItemLessThanOrEqualTo(String value) {
            addCriterion("ABU_ITEM <=", value, "abuItem");
            return (Criteria) this;
        }

        public Criteria andAbuItemLike(String value) {
            addCriterion("ABU_ITEM like", value, "abuItem");
            return (Criteria) this;
        }

        public Criteria andAbuItemNotLike(String value) {
            addCriterion("ABU_ITEM not like", value, "abuItem");
            return (Criteria) this;
        }

        public Criteria andAbuItemIn(List<String> values) {
            addCriterion("ABU_ITEM in", values, "abuItem");
            return (Criteria) this;
        }

        public Criteria andAbuItemNotIn(List<String> values) {
            addCriterion("ABU_ITEM not in", values, "abuItem");
            return (Criteria) this;
        }

        public Criteria andAbuItemBetween(String value1, String value2) {
            addCriterion("ABU_ITEM between", value1, value2, "abuItem");
            return (Criteria) this;
        }

        public Criteria andAbuItemNotBetween(String value1, String value2) {
            addCriterion("ABU_ITEM not between", value1, value2, "abuItem");
            return (Criteria) this;
        }

        public Criteria andCbuItemIsNull() {
            addCriterion("CBU_ITEM is null");
            return (Criteria) this;
        }

        public Criteria andCbuItemIsNotNull() {
            addCriterion("CBU_ITEM is not null");
            return (Criteria) this;
        }

        public Criteria andCbuItemEqualTo(String value) {
            addCriterion("CBU_ITEM =", value, "cbuItem");
            return (Criteria) this;
        }

        public Criteria andCbuItemNotEqualTo(String value) {
            addCriterion("CBU_ITEM <>", value, "cbuItem");
            return (Criteria) this;
        }

        public Criteria andCbuItemGreaterThan(String value) {
            addCriterion("CBU_ITEM >", value, "cbuItem");
            return (Criteria) this;
        }

        public Criteria andCbuItemGreaterThanOrEqualTo(String value) {
            addCriterion("CBU_ITEM >=", value, "cbuItem");
            return (Criteria) this;
        }

        public Criteria andCbuItemLessThan(String value) {
            addCriterion("CBU_ITEM <", value, "cbuItem");
            return (Criteria) this;
        }

        public Criteria andCbuItemLessThanOrEqualTo(String value) {
            addCriterion("CBU_ITEM <=", value, "cbuItem");
            return (Criteria) this;
        }

        public Criteria andCbuItemLike(String value) {
            addCriterion("CBU_ITEM like", value, "cbuItem");
            return (Criteria) this;
        }

        public Criteria andCbuItemNotLike(String value) {
            addCriterion("CBU_ITEM not like", value, "cbuItem");
            return (Criteria) this;
        }

        public Criteria andCbuItemIn(List<String> values) {
            addCriterion("CBU_ITEM in", values, "cbuItem");
            return (Criteria) this;
        }

        public Criteria andCbuItemNotIn(List<String> values) {
            addCriterion("CBU_ITEM not in", values, "cbuItem");
            return (Criteria) this;
        }

        public Criteria andCbuItemBetween(String value1, String value2) {
            addCriterion("CBU_ITEM between", value1, value2, "cbuItem");
            return (Criteria) this;
        }

        public Criteria andCbuItemNotBetween(String value1, String value2) {
            addCriterion("CBU_ITEM not between", value1, value2, "cbuItem");
            return (Criteria) this;
        }

        public Criteria andOperateScopeIsNull() {
            addCriterion("OPERATE_SCOPE is null");
            return (Criteria) this;
        }

        public Criteria andOperateScopeIsNotNull() {
            addCriterion("OPERATE_SCOPE is not null");
            return (Criteria) this;
        }

        public Criteria andOperateScopeEqualTo(String value) {
            addCriterion("OPERATE_SCOPE =", value, "operateScope");
            return (Criteria) this;
        }

        public Criteria andOperateScopeNotEqualTo(String value) {
            addCriterion("OPERATE_SCOPE <>", value, "operateScope");
            return (Criteria) this;
        }

        public Criteria andOperateScopeGreaterThan(String value) {
            addCriterion("OPERATE_SCOPE >", value, "operateScope");
            return (Criteria) this;
        }

        public Criteria andOperateScopeGreaterThanOrEqualTo(String value) {
            addCriterion("OPERATE_SCOPE >=", value, "operateScope");
            return (Criteria) this;
        }

        public Criteria andOperateScopeLessThan(String value) {
            addCriterion("OPERATE_SCOPE <", value, "operateScope");
            return (Criteria) this;
        }

        public Criteria andOperateScopeLessThanOrEqualTo(String value) {
            addCriterion("OPERATE_SCOPE <=", value, "operateScope");
            return (Criteria) this;
        }

        public Criteria andOperateScopeLike(String value) {
            addCriterion("OPERATE_SCOPE like", value, "operateScope");
            return (Criteria) this;
        }

        public Criteria andOperateScopeNotLike(String value) {
            addCriterion("OPERATE_SCOPE not like", value, "operateScope");
            return (Criteria) this;
        }

        public Criteria andOperateScopeIn(List<String> values) {
            addCriterion("OPERATE_SCOPE in", values, "operateScope");
            return (Criteria) this;
        }

        public Criteria andOperateScopeNotIn(List<String> values) {
            addCriterion("OPERATE_SCOPE not in", values, "operateScope");
            return (Criteria) this;
        }

        public Criteria andOperateScopeBetween(String value1, String value2) {
            addCriterion("OPERATE_SCOPE between", value1, value2, "operateScope");
            return (Criteria) this;
        }

        public Criteria andOperateScopeNotBetween(String value1, String value2) {
            addCriterion("OPERATE_SCOPE not between", value1, value2, "operateScope");
            return (Criteria) this;
        }

        public Criteria andOperateScopeAndFormIsNull() {
            addCriterion("OPERATE_SCOPE_AND_FORM is null");
            return (Criteria) this;
        }

        public Criteria andOperateScopeAndFormIsNotNull() {
            addCriterion("OPERATE_SCOPE_AND_FORM is not null");
            return (Criteria) this;
        }

        public Criteria andOperateScopeAndFormEqualTo(String value) {
            addCriterion("OPERATE_SCOPE_AND_FORM =", value, "operateScopeAndForm");
            return (Criteria) this;
        }

        public Criteria andOperateScopeAndFormNotEqualTo(String value) {
            addCriterion("OPERATE_SCOPE_AND_FORM <>", value, "operateScopeAndForm");
            return (Criteria) this;
        }

        public Criteria andOperateScopeAndFormGreaterThan(String value) {
            addCriterion("OPERATE_SCOPE_AND_FORM >", value, "operateScopeAndForm");
            return (Criteria) this;
        }

        public Criteria andOperateScopeAndFormGreaterThanOrEqualTo(String value) {
            addCriterion("OPERATE_SCOPE_AND_FORM >=", value, "operateScopeAndForm");
            return (Criteria) this;
        }

        public Criteria andOperateScopeAndFormLessThan(String value) {
            addCriterion("OPERATE_SCOPE_AND_FORM <", value, "operateScopeAndForm");
            return (Criteria) this;
        }

        public Criteria andOperateScopeAndFormLessThanOrEqualTo(String value) {
            addCriterion("OPERATE_SCOPE_AND_FORM <=", value, "operateScopeAndForm");
            return (Criteria) this;
        }

        public Criteria andOperateScopeAndFormLike(String value) {
            addCriterion("OPERATE_SCOPE_AND_FORM like", value, "operateScopeAndForm");
            return (Criteria) this;
        }

        public Criteria andOperateScopeAndFormNotLike(String value) {
            addCriterion("OPERATE_SCOPE_AND_FORM not like", value, "operateScopeAndForm");
            return (Criteria) this;
        }

        public Criteria andOperateScopeAndFormIn(List<String> values) {
            addCriterion("OPERATE_SCOPE_AND_FORM in", values, "operateScopeAndForm");
            return (Criteria) this;
        }

        public Criteria andOperateScopeAndFormNotIn(List<String> values) {
            addCriterion("OPERATE_SCOPE_AND_FORM not in", values, "operateScopeAndForm");
            return (Criteria) this;
        }

        public Criteria andOperateScopeAndFormBetween(String value1, String value2) {
            addCriterion("OPERATE_SCOPE_AND_FORM between", value1, value2, "operateScopeAndForm");
            return (Criteria) this;
        }

        public Criteria andOperateScopeAndFormNotBetween(String value1, String value2) {
            addCriterion("OPERATE_SCOPE_AND_FORM not between", value1, value2, "operateScopeAndForm");
            return (Criteria) this;
        }

        public Criteria andRegOrgIsNull() {
            addCriterion("REG_ORG is null");
            return (Criteria) this;
        }

        public Criteria andRegOrgIsNotNull() {
            addCriterion("REG_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andRegOrgEqualTo(String value) {
            addCriterion("REG_ORG =", value, "regOrg");
            return (Criteria) this;
        }

        public Criteria andRegOrgNotEqualTo(String value) {
            addCriterion("REG_ORG <>", value, "regOrg");
            return (Criteria) this;
        }

        public Criteria andRegOrgGreaterThan(String value) {
            addCriterion("REG_ORG >", value, "regOrg");
            return (Criteria) this;
        }

        public Criteria andRegOrgGreaterThanOrEqualTo(String value) {
            addCriterion("REG_ORG >=", value, "regOrg");
            return (Criteria) this;
        }

        public Criteria andRegOrgLessThan(String value) {
            addCriterion("REG_ORG <", value, "regOrg");
            return (Criteria) this;
        }

        public Criteria andRegOrgLessThanOrEqualTo(String value) {
            addCriterion("REG_ORG <=", value, "regOrg");
            return (Criteria) this;
        }

        public Criteria andRegOrgLike(String value) {
            addCriterion("REG_ORG like", value, "regOrg");
            return (Criteria) this;
        }

        public Criteria andRegOrgNotLike(String value) {
            addCriterion("REG_ORG not like", value, "regOrg");
            return (Criteria) this;
        }

        public Criteria andRegOrgIn(List<String> values) {
            addCriterion("REG_ORG in", values, "regOrg");
            return (Criteria) this;
        }

        public Criteria andRegOrgNotIn(List<String> values) {
            addCriterion("REG_ORG not in", values, "regOrg");
            return (Criteria) this;
        }

        public Criteria andRegOrgBetween(String value1, String value2) {
            addCriterion("REG_ORG between", value1, value2, "regOrg");
            return (Criteria) this;
        }

        public Criteria andRegOrgNotBetween(String value1, String value2) {
            addCriterion("REG_ORG not between", value1, value2, "regOrg");
            return (Criteria) this;
        }

        public Criteria andAncheYearIsNull() {
            addCriterion("ANCHE_YEAR is null");
            return (Criteria) this;
        }

        public Criteria andAncheYearIsNotNull() {
            addCriterion("ANCHE_YEAR is not null");
            return (Criteria) this;
        }

        public Criteria andAncheYearEqualTo(String value) {
            addCriterion("ANCHE_YEAR =", value, "ancheYear");
            return (Criteria) this;
        }

        public Criteria andAncheYearNotEqualTo(String value) {
            addCriterion("ANCHE_YEAR <>", value, "ancheYear");
            return (Criteria) this;
        }

        public Criteria andAncheYearGreaterThan(String value) {
            addCriterion("ANCHE_YEAR >", value, "ancheYear");
            return (Criteria) this;
        }

        public Criteria andAncheYearGreaterThanOrEqualTo(String value) {
            addCriterion("ANCHE_YEAR >=", value, "ancheYear");
            return (Criteria) this;
        }

        public Criteria andAncheYearLessThan(String value) {
            addCriterion("ANCHE_YEAR <", value, "ancheYear");
            return (Criteria) this;
        }

        public Criteria andAncheYearLessThanOrEqualTo(String value) {
            addCriterion("ANCHE_YEAR <=", value, "ancheYear");
            return (Criteria) this;
        }

        public Criteria andAncheYearLike(String value) {
            addCriterion("ANCHE_YEAR like", value, "ancheYear");
            return (Criteria) this;
        }

        public Criteria andAncheYearNotLike(String value) {
            addCriterion("ANCHE_YEAR not like", value, "ancheYear");
            return (Criteria) this;
        }

        public Criteria andAncheYearIn(List<String> values) {
            addCriterion("ANCHE_YEAR in", values, "ancheYear");
            return (Criteria) this;
        }

        public Criteria andAncheYearNotIn(List<String> values) {
            addCriterion("ANCHE_YEAR not in", values, "ancheYear");
            return (Criteria) this;
        }

        public Criteria andAncheYearBetween(String value1, String value2) {
            addCriterion("ANCHE_YEAR between", value1, value2, "ancheYear");
            return (Criteria) this;
        }

        public Criteria andAncheYearNotBetween(String value1, String value2) {
            addCriterion("ANCHE_YEAR not between", value1, value2, "ancheYear");
            return (Criteria) this;
        }

        public Criteria andAncheDateIsNull() {
            addCriterion("ANCHE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andAncheDateIsNotNull() {
            addCriterion("ANCHE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andAncheDateEqualTo(Date value) {
            addCriterion("ANCHE_DATE =", value, "ancheDate");
            return (Criteria) this;
        }

        public Criteria andAncheDateNotEqualTo(Date value) {
            addCriterion("ANCHE_DATE <>", value, "ancheDate");
            return (Criteria) this;
        }

        public Criteria andAncheDateGreaterThan(Date value) {
            addCriterion("ANCHE_DATE >", value, "ancheDate");
            return (Criteria) this;
        }

        public Criteria andAncheDateGreaterThanOrEqualTo(Date value) {
            addCriterion("ANCHE_DATE >=", value, "ancheDate");
            return (Criteria) this;
        }

        public Criteria andAncheDateLessThan(Date value) {
            addCriterion("ANCHE_DATE <", value, "ancheDate");
            return (Criteria) this;
        }

        public Criteria andAncheDateLessThanOrEqualTo(Date value) {
            addCriterion("ANCHE_DATE <=", value, "ancheDate");
            return (Criteria) this;
        }

        public Criteria andAncheDateIn(List<Date> values) {
            addCriterion("ANCHE_DATE in", values, "ancheDate");
            return (Criteria) this;
        }

        public Criteria andAncheDateNotIn(List<Date> values) {
            addCriterion("ANCHE_DATE not in", values, "ancheDate");
            return (Criteria) this;
        }

        public Criteria andAncheDateBetween(Date value1, Date value2) {
            addCriterion("ANCHE_DATE between", value1, value2, "ancheDate");
            return (Criteria) this;
        }

        public Criteria andAncheDateNotBetween(Date value1, Date value2) {
            addCriterion("ANCHE_DATE not between", value1, value2, "ancheDate");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyCodeIsNull() {
            addCriterion("INDUSTRY_PHY_CODE is null");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyCodeIsNotNull() {
            addCriterion("INDUSTRY_PHY_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyCodeEqualTo(String value) {
            addCriterion("INDUSTRY_PHY_CODE =", value, "industryPhyCode");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyCodeNotEqualTo(String value) {
            addCriterion("INDUSTRY_PHY_CODE <>", value, "industryPhyCode");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyCodeGreaterThan(String value) {
            addCriterion("INDUSTRY_PHY_CODE >", value, "industryPhyCode");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyCodeGreaterThanOrEqualTo(String value) {
            addCriterion("INDUSTRY_PHY_CODE >=", value, "industryPhyCode");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyCodeLessThan(String value) {
            addCriterion("INDUSTRY_PHY_CODE <", value, "industryPhyCode");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyCodeLessThanOrEqualTo(String value) {
            addCriterion("INDUSTRY_PHY_CODE <=", value, "industryPhyCode");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyCodeLike(String value) {
            addCriterion("INDUSTRY_PHY_CODE like", value, "industryPhyCode");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyCodeNotLike(String value) {
            addCriterion("INDUSTRY_PHY_CODE not like", value, "industryPhyCode");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyCodeIn(List<String> values) {
            addCriterion("INDUSTRY_PHY_CODE in", values, "industryPhyCode");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyCodeNotIn(List<String> values) {
            addCriterion("INDUSTRY_PHY_CODE not in", values, "industryPhyCode");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyCodeBetween(String value1, String value2) {
            addCriterion("INDUSTRY_PHY_CODE between", value1, value2, "industryPhyCode");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyCodeNotBetween(String value1, String value2) {
            addCriterion("INDUSTRY_PHY_CODE not between", value1, value2, "industryPhyCode");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyNameIsNull() {
            addCriterion("INDUSTRY_PHY_NAME is null");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyNameIsNotNull() {
            addCriterion("INDUSTRY_PHY_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyNameEqualTo(String value) {
            addCriterion("INDUSTRY_PHY_NAME =", value, "industryPhyName");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyNameNotEqualTo(String value) {
            addCriterion("INDUSTRY_PHY_NAME <>", value, "industryPhyName");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyNameGreaterThan(String value) {
            addCriterion("INDUSTRY_PHY_NAME >", value, "industryPhyName");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyNameGreaterThanOrEqualTo(String value) {
            addCriterion("INDUSTRY_PHY_NAME >=", value, "industryPhyName");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyNameLessThan(String value) {
            addCriterion("INDUSTRY_PHY_NAME <", value, "industryPhyName");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyNameLessThanOrEqualTo(String value) {
            addCriterion("INDUSTRY_PHY_NAME <=", value, "industryPhyName");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyNameLike(String value) {
            addCriterion("INDUSTRY_PHY_NAME like", value, "industryPhyName");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyNameNotLike(String value) {
            addCriterion("INDUSTRY_PHY_NAME not like", value, "industryPhyName");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyNameIn(List<String> values) {
            addCriterion("INDUSTRY_PHY_NAME in", values, "industryPhyName");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyNameNotIn(List<String> values) {
            addCriterion("INDUSTRY_PHY_NAME not in", values, "industryPhyName");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyNameBetween(String value1, String value2) {
            addCriterion("INDUSTRY_PHY_NAME between", value1, value2, "industryPhyName");
            return (Criteria) this;
        }

        public Criteria andIndustryPhyNameNotBetween(String value1, String value2) {
            addCriterion("INDUSTRY_PHY_NAME not between", value1, value2, "industryPhyName");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeIsNull() {
            addCriterion("INDUSTRY_CODE is null");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeIsNotNull() {
            addCriterion("INDUSTRY_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeEqualTo(String value) {
            addCriterion("INDUSTRY_CODE =", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeNotEqualTo(String value) {
            addCriterion("INDUSTRY_CODE <>", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeGreaterThan(String value) {
            addCriterion("INDUSTRY_CODE >", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeGreaterThanOrEqualTo(String value) {
            addCriterion("INDUSTRY_CODE >=", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeLessThan(String value) {
            addCriterion("INDUSTRY_CODE <", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeLessThanOrEqualTo(String value) {
            addCriterion("INDUSTRY_CODE <=", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeLike(String value) {
            addCriterion("INDUSTRY_CODE like", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeNotLike(String value) {
            addCriterion("INDUSTRY_CODE not like", value, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeIn(List<String> values) {
            addCriterion("INDUSTRY_CODE in", values, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeNotIn(List<String> values) {
            addCriterion("INDUSTRY_CODE not in", values, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeBetween(String value1, String value2) {
            addCriterion("INDUSTRY_CODE between", value1, value2, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryCodeNotBetween(String value1, String value2) {
            addCriterion("INDUSTRY_CODE not between", value1, value2, "industryCode");
            return (Criteria) this;
        }

        public Criteria andIndustryNameIsNull() {
            addCriterion("INDUSTRY_NAME is null");
            return (Criteria) this;
        }

        public Criteria andIndustryNameIsNotNull() {
            addCriterion("INDUSTRY_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryNameEqualTo(String value) {
            addCriterion("INDUSTRY_NAME =", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameNotEqualTo(String value) {
            addCriterion("INDUSTRY_NAME <>", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameGreaterThan(String value) {
            addCriterion("INDUSTRY_NAME >", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameGreaterThanOrEqualTo(String value) {
            addCriterion("INDUSTRY_NAME >=", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameLessThan(String value) {
            addCriterion("INDUSTRY_NAME <", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameLessThanOrEqualTo(String value) {
            addCriterion("INDUSTRY_NAME <=", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameLike(String value) {
            addCriterion("INDUSTRY_NAME like", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameNotLike(String value) {
            addCriterion("INDUSTRY_NAME not like", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameIn(List<String> values) {
            addCriterion("INDUSTRY_NAME in", values, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameNotIn(List<String> values) {
            addCriterion("INDUSTRY_NAME not in", values, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameBetween(String value1, String value2) {
            addCriterion("INDUSTRY_NAME between", value1, value2, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameNotBetween(String value1, String value2) {
            addCriterion("INDUSTRY_NAME not between", value1, value2, "industryName");
            return (Criteria) this;
        }

        public Criteria andRecCapIsNull() {
            addCriterion("REC_CAP is null");
            return (Criteria) this;
        }

        public Criteria andRecCapIsNotNull() {
            addCriterion("REC_CAP is not null");
            return (Criteria) this;
        }

        public Criteria andRecCapEqualTo(BigDecimal value) {
            addCriterion("REC_CAP =", value, "recCap");
            return (Criteria) this;
        }

        public Criteria andRecCapNotEqualTo(BigDecimal value) {
            addCriterion("REC_CAP <>", value, "recCap");
            return (Criteria) this;
        }

        public Criteria andRecCapGreaterThan(BigDecimal value) {
            addCriterion("REC_CAP >", value, "recCap");
            return (Criteria) this;
        }

        public Criteria andRecCapGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REC_CAP >=", value, "recCap");
            return (Criteria) this;
        }

        public Criteria andRecCapLessThan(BigDecimal value) {
            addCriterion("REC_CAP <", value, "recCap");
            return (Criteria) this;
        }

        public Criteria andRecCapLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REC_CAP <=", value, "recCap");
            return (Criteria) this;
        }

        public Criteria andRecCapIn(List<BigDecimal> values) {
            addCriterion("REC_CAP in", values, "recCap");
            return (Criteria) this;
        }

        public Criteria andRecCapNotIn(List<BigDecimal> values) {
            addCriterion("REC_CAP not in", values, "recCap");
            return (Criteria) this;
        }

        public Criteria andRecCapBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REC_CAP between", value1, value2, "recCap");
            return (Criteria) this;
        }

        public Criteria andRecCapNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REC_CAP not between", value1, value2, "recCap");
            return (Criteria) this;
        }

        public Criteria andOriRegNoIsNull() {
            addCriterion("ORI_REG_NO is null");
            return (Criteria) this;
        }

        public Criteria andOriRegNoIsNotNull() {
            addCriterion("ORI_REG_NO is not null");
            return (Criteria) this;
        }

        public Criteria andOriRegNoEqualTo(String value) {
            addCriterion("ORI_REG_NO =", value, "oriRegNo");
            return (Criteria) this;
        }

        public Criteria andOriRegNoNotEqualTo(String value) {
            addCriterion("ORI_REG_NO <>", value, "oriRegNo");
            return (Criteria) this;
        }

        public Criteria andOriRegNoGreaterThan(String value) {
            addCriterion("ORI_REG_NO >", value, "oriRegNo");
            return (Criteria) this;
        }

        public Criteria andOriRegNoGreaterThanOrEqualTo(String value) {
            addCriterion("ORI_REG_NO >=", value, "oriRegNo");
            return (Criteria) this;
        }

        public Criteria andOriRegNoLessThan(String value) {
            addCriterion("ORI_REG_NO <", value, "oriRegNo");
            return (Criteria) this;
        }

        public Criteria andOriRegNoLessThanOrEqualTo(String value) {
            addCriterion("ORI_REG_NO <=", value, "oriRegNo");
            return (Criteria) this;
        }

        public Criteria andOriRegNoLike(String value) {
            addCriterion("ORI_REG_NO like", value, "oriRegNo");
            return (Criteria) this;
        }

        public Criteria andOriRegNoNotLike(String value) {
            addCriterion("ORI_REG_NO not like", value, "oriRegNo");
            return (Criteria) this;
        }

        public Criteria andOriRegNoIn(List<String> values) {
            addCriterion("ORI_REG_NO in", values, "oriRegNo");
            return (Criteria) this;
        }

        public Criteria andOriRegNoNotIn(List<String> values) {
            addCriterion("ORI_REG_NO not in", values, "oriRegNo");
            return (Criteria) this;
        }

        public Criteria andOriRegNoBetween(String value1, String value2) {
            addCriterion("ORI_REG_NO between", value1, value2, "oriRegNo");
            return (Criteria) this;
        }

        public Criteria andOriRegNoNotBetween(String value1, String value2) {
            addCriterion("ORI_REG_NO not between", value1, value2, "oriRegNo");
            return (Criteria) this;
        }

        public Criteria andCreditCodeIsNull() {
            addCriterion("CREDIT_CODE is null");
            return (Criteria) this;
        }

        public Criteria andCreditCodeIsNotNull() {
            addCriterion("CREDIT_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andCreditCodeEqualTo(String value) {
            addCriterion("CREDIT_CODE =", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeNotEqualTo(String value) {
            addCriterion("CREDIT_CODE <>", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeGreaterThan(String value) {
            addCriterion("CREDIT_CODE >", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeGreaterThanOrEqualTo(String value) {
            addCriterion("CREDIT_CODE >=", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeLessThan(String value) {
            addCriterion("CREDIT_CODE <", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeLessThanOrEqualTo(String value) {
            addCriterion("CREDIT_CODE <=", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeLike(String value) {
            addCriterion("CREDIT_CODE like", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeNotLike(String value) {
            addCriterion("CREDIT_CODE not like", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeIn(List<String> values) {
            addCriterion("CREDIT_CODE in", values, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeNotIn(List<String> values) {
            addCriterion("CREDIT_CODE not in", values, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeBetween(String value1, String value2) {
            addCriterion("CREDIT_CODE between", value1, value2, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeNotBetween(String value1, String value2) {
            addCriterion("CREDIT_CODE not between", value1, value2, "creditCode");
            return (Criteria) this;
        }

        public Criteria andApprDateIsNull() {
            addCriterion("APPR_DATE is null");
            return (Criteria) this;
        }

        public Criteria andApprDateIsNotNull() {
            addCriterion("APPR_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andApprDateEqualTo(Date value) {
            addCriterion("APPR_DATE =", value, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateNotEqualTo(Date value) {
            addCriterion("APPR_DATE <>", value, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateGreaterThan(Date value) {
            addCriterion("APPR_DATE >", value, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateGreaterThanOrEqualTo(Date value) {
            addCriterion("APPR_DATE >=", value, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateLessThan(Date value) {
            addCriterion("APPR_DATE <", value, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateLessThanOrEqualTo(Date value) {
            addCriterion("APPR_DATE <=", value, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateIn(List<Date> values) {
            addCriterion("APPR_DATE in", values, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateNotIn(List<Date> values) {
            addCriterion("APPR_DATE not in", values, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateBetween(Date value1, Date value2) {
            addCriterion("APPR_DATE between", value1, value2, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateNotBetween(Date value1, Date value2) {
            addCriterion("APPR_DATE not between", value1, value2, "apprDate");
            return (Criteria) this;
        }

        public Criteria andOrgNoIsNull() {
            addCriterion("ORG_NO is null");
            return (Criteria) this;
        }

        public Criteria andOrgNoIsNotNull() {
            addCriterion("ORG_NO is not null");
            return (Criteria) this;
        }

        public Criteria andOrgNoEqualTo(String value) {
            addCriterion("ORG_NO =", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoNotEqualTo(String value) {
            addCriterion("ORG_NO <>", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoGreaterThan(String value) {
            addCriterion("ORG_NO >", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_NO >=", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoLessThan(String value) {
            addCriterion("ORG_NO <", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoLessThanOrEqualTo(String value) {
            addCriterion("ORG_NO <=", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoLike(String value) {
            addCriterion("ORG_NO like", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoNotLike(String value) {
            addCriterion("ORG_NO not like", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoIn(List<String> values) {
            addCriterion("ORG_NO in", values, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoNotIn(List<String> values) {
            addCriterion("ORG_NO not in", values, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoBetween(String value1, String value2) {
            addCriterion("ORG_NO between", value1, value2, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoNotBetween(String value1, String value2) {
            addCriterion("ORG_NO not between", value1, value2, "orgNo");
            return (Criteria) this;
        }

        public Criteria andUsciIsNull() {
            addCriterion("USCI is null");
            return (Criteria) this;
        }

        public Criteria andUsciIsNotNull() {
            addCriterion("USCI is not null");
            return (Criteria) this;
        }

        public Criteria andUsciEqualTo(String value) {
            addCriterion("USCI =", value, "usci");
            return (Criteria) this;
        }

        public Criteria andUsciNotEqualTo(String value) {
            addCriterion("USCI <>", value, "usci");
            return (Criteria) this;
        }

        public Criteria andUsciGreaterThan(String value) {
            addCriterion("USCI >", value, "usci");
            return (Criteria) this;
        }

        public Criteria andUsciGreaterThanOrEqualTo(String value) {
            addCriterion("USCI >=", value, "usci");
            return (Criteria) this;
        }

        public Criteria andUsciLessThan(String value) {
            addCriterion("USCI <", value, "usci");
            return (Criteria) this;
        }

        public Criteria andUsciLessThanOrEqualTo(String value) {
            addCriterion("USCI <=", value, "usci");
            return (Criteria) this;
        }

        public Criteria andUsciLike(String value) {
            addCriterion("USCI like", value, "usci");
            return (Criteria) this;
        }

        public Criteria andUsciNotLike(String value) {
            addCriterion("USCI not like", value, "usci");
            return (Criteria) this;
        }

        public Criteria andUsciIn(List<String> values) {
            addCriterion("USCI in", values, "usci");
            return (Criteria) this;
        }

        public Criteria andUsciNotIn(List<String> values) {
            addCriterion("USCI not in", values, "usci");
            return (Criteria) this;
        }

        public Criteria andUsciBetween(String value1, String value2) {
            addCriterion("USCI between", value1, value2, "usci");
            return (Criteria) this;
        }

        public Criteria andUsciNotBetween(String value1, String value2) {
            addCriterion("USCI not between", value1, value2, "usci");
            return (Criteria) this;
        }

        public Criteria andReqCerTmIsNull() {
            addCriterion("REQ_CER_TM is null");
            return (Criteria) this;
        }

        public Criteria andReqCerTmIsNotNull() {
            addCriterion("REQ_CER_TM is not null");
            return (Criteria) this;
        }

        public Criteria andReqCerTmEqualTo(Date value) {
            addCriterion("REQ_CER_TM =", value, "reqCerTm");
            return (Criteria) this;
        }

        public Criteria andReqCerTmNotEqualTo(Date value) {
            addCriterion("REQ_CER_TM <>", value, "reqCerTm");
            return (Criteria) this;
        }

        public Criteria andReqCerTmGreaterThan(Date value) {
            addCriterion("REQ_CER_TM >", value, "reqCerTm");
            return (Criteria) this;
        }

        public Criteria andReqCerTmGreaterThanOrEqualTo(Date value) {
            addCriterion("REQ_CER_TM >=", value, "reqCerTm");
            return (Criteria) this;
        }

        public Criteria andReqCerTmLessThan(Date value) {
            addCriterion("REQ_CER_TM <", value, "reqCerTm");
            return (Criteria) this;
        }

        public Criteria andReqCerTmLessThanOrEqualTo(Date value) {
            addCriterion("REQ_CER_TM <=", value, "reqCerTm");
            return (Criteria) this;
        }

        public Criteria andReqCerTmIn(List<Date> values) {
            addCriterion("REQ_CER_TM in", values, "reqCerTm");
            return (Criteria) this;
        }

        public Criteria andReqCerTmNotIn(List<Date> values) {
            addCriterion("REQ_CER_TM not in", values, "reqCerTm");
            return (Criteria) this;
        }

        public Criteria andReqCerTmBetween(Date value1, Date value2) {
            addCriterion("REQ_CER_TM between", value1, value2, "reqCerTm");
            return (Criteria) this;
        }

        public Criteria andReqCerTmNotBetween(Date value1, Date value2) {
            addCriterion("REQ_CER_TM not between", value1, value2, "reqCerTm");
            return (Criteria) this;
        }

        public Criteria andReqCerUserIsNull() {
            addCriterion("REQ_CER_USER is null");
            return (Criteria) this;
        }

        public Criteria andReqCerUserIsNotNull() {
            addCriterion("REQ_CER_USER is not null");
            return (Criteria) this;
        }

        public Criteria andReqCerUserEqualTo(String value) {
            addCriterion("REQ_CER_USER =", value, "reqCerUser");
            return (Criteria) this;
        }

        public Criteria andReqCerUserNotEqualTo(String value) {
            addCriterion("REQ_CER_USER <>", value, "reqCerUser");
            return (Criteria) this;
        }

        public Criteria andReqCerUserGreaterThan(String value) {
            addCriterion("REQ_CER_USER >", value, "reqCerUser");
            return (Criteria) this;
        }

        public Criteria andReqCerUserGreaterThanOrEqualTo(String value) {
            addCriterion("REQ_CER_USER >=", value, "reqCerUser");
            return (Criteria) this;
        }

        public Criteria andReqCerUserLessThan(String value) {
            addCriterion("REQ_CER_USER <", value, "reqCerUser");
            return (Criteria) this;
        }

        public Criteria andReqCerUserLessThanOrEqualTo(String value) {
            addCriterion("REQ_CER_USER <=", value, "reqCerUser");
            return (Criteria) this;
        }

        public Criteria andReqCerUserLike(String value) {
            addCriterion("REQ_CER_USER like", value, "reqCerUser");
            return (Criteria) this;
        }

        public Criteria andReqCerUserNotLike(String value) {
            addCriterion("REQ_CER_USER not like", value, "reqCerUser");
            return (Criteria) this;
        }

        public Criteria andReqCerUserIn(List<String> values) {
            addCriterion("REQ_CER_USER in", values, "reqCerUser");
            return (Criteria) this;
        }

        public Criteria andReqCerUserNotIn(List<String> values) {
            addCriterion("REQ_CER_USER not in", values, "reqCerUser");
            return (Criteria) this;
        }

        public Criteria andReqCerUserBetween(String value1, String value2) {
            addCriterion("REQ_CER_USER between", value1, value2, "reqCerUser");
            return (Criteria) this;
        }

        public Criteria andReqCerUserNotBetween(String value1, String value2) {
            addCriterion("REQ_CER_USER not between", value1, value2, "reqCerUser");
            return (Criteria) this;
        }

        public Criteria andCerSuccessTmIsNull() {
            addCriterion("CER_SUCCESS_TM is null");
            return (Criteria) this;
        }

        public Criteria andCerSuccessTmIsNotNull() {
            addCriterion("CER_SUCCESS_TM is not null");
            return (Criteria) this;
        }

        public Criteria andCerSuccessTmEqualTo(Date value) {
            addCriterion("CER_SUCCESS_TM =", value, "cerSuccessTm");
            return (Criteria) this;
        }

        public Criteria andCerSuccessTmNotEqualTo(Date value) {
            addCriterion("CER_SUCCESS_TM <>", value, "cerSuccessTm");
            return (Criteria) this;
        }

        public Criteria andCerSuccessTmGreaterThan(Date value) {
            addCriterion("CER_SUCCESS_TM >", value, "cerSuccessTm");
            return (Criteria) this;
        }

        public Criteria andCerSuccessTmGreaterThanOrEqualTo(Date value) {
            addCriterion("CER_SUCCESS_TM >=", value, "cerSuccessTm");
            return (Criteria) this;
        }

        public Criteria andCerSuccessTmLessThan(Date value) {
            addCriterion("CER_SUCCESS_TM <", value, "cerSuccessTm");
            return (Criteria) this;
        }

        public Criteria andCerSuccessTmLessThanOrEqualTo(Date value) {
            addCriterion("CER_SUCCESS_TM <=", value, "cerSuccessTm");
            return (Criteria) this;
        }

        public Criteria andCerSuccessTmIn(List<Date> values) {
            addCriterion("CER_SUCCESS_TM in", values, "cerSuccessTm");
            return (Criteria) this;
        }

        public Criteria andCerSuccessTmNotIn(List<Date> values) {
            addCriterion("CER_SUCCESS_TM not in", values, "cerSuccessTm");
            return (Criteria) this;
        }

        public Criteria andCerSuccessTmBetween(Date value1, Date value2) {
            addCriterion("CER_SUCCESS_TM between", value1, value2, "cerSuccessTm");
            return (Criteria) this;
        }

        public Criteria andCerSuccessTmNotBetween(Date value1, Date value2) {
            addCriterion("CER_SUCCESS_TM not between", value1, value2, "cerSuccessTm");
            return (Criteria) this;
        }

        public Criteria andCerNumIsNull() {
            addCriterion("CER_NUM is null");
            return (Criteria) this;
        }

        public Criteria andCerNumIsNotNull() {
            addCriterion("CER_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andCerNumEqualTo(BigDecimal value) {
            addCriterion("CER_NUM =", value, "cerNum");
            return (Criteria) this;
        }

        public Criteria andCerNumNotEqualTo(BigDecimal value) {
            addCriterion("CER_NUM <>", value, "cerNum");
            return (Criteria) this;
        }

        public Criteria andCerNumGreaterThan(BigDecimal value) {
            addCriterion("CER_NUM >", value, "cerNum");
            return (Criteria) this;
        }

        public Criteria andCerNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CER_NUM >=", value, "cerNum");
            return (Criteria) this;
        }

        public Criteria andCerNumLessThan(BigDecimal value) {
            addCriterion("CER_NUM <", value, "cerNum");
            return (Criteria) this;
        }

        public Criteria andCerNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CER_NUM <=", value, "cerNum");
            return (Criteria) this;
        }

        public Criteria andCerNumIn(List<BigDecimal> values) {
            addCriterion("CER_NUM in", values, "cerNum");
            return (Criteria) this;
        }

        public Criteria andCerNumNotIn(List<BigDecimal> values) {
            addCriterion("CER_NUM not in", values, "cerNum");
            return (Criteria) this;
        }

        public Criteria andCerNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CER_NUM between", value1, value2, "cerNum");
            return (Criteria) this;
        }

        public Criteria andCerNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CER_NUM not between", value1, value2, "cerNum");
            return (Criteria) this;
        }

        public Criteria andCerProStatIsNull() {
            addCriterion("CER_PRO_STAT is null");
            return (Criteria) this;
        }

        public Criteria andCerProStatIsNotNull() {
            addCriterion("CER_PRO_STAT is not null");
            return (Criteria) this;
        }

        public Criteria andCerProStatEqualTo(BigDecimal value) {
            addCriterion("CER_PRO_STAT =", value, "cerProStat");
            return (Criteria) this;
        }

        public Criteria andCerProStatNotEqualTo(BigDecimal value) {
            addCriterion("CER_PRO_STAT <>", value, "cerProStat");
            return (Criteria) this;
        }

        public Criteria andCerProStatGreaterThan(BigDecimal value) {
            addCriterion("CER_PRO_STAT >", value, "cerProStat");
            return (Criteria) this;
        }

        public Criteria andCerProStatGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CER_PRO_STAT >=", value, "cerProStat");
            return (Criteria) this;
        }

        public Criteria andCerProStatLessThan(BigDecimal value) {
            addCriterion("CER_PRO_STAT <", value, "cerProStat");
            return (Criteria) this;
        }

        public Criteria andCerProStatLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CER_PRO_STAT <=", value, "cerProStat");
            return (Criteria) this;
        }

        public Criteria andCerProStatIn(List<BigDecimal> values) {
            addCriterion("CER_PRO_STAT in", values, "cerProStat");
            return (Criteria) this;
        }

        public Criteria andCerProStatNotIn(List<BigDecimal> values) {
            addCriterion("CER_PRO_STAT not in", values, "cerProStat");
            return (Criteria) this;
        }

        public Criteria andCerProStatBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CER_PRO_STAT between", value1, value2, "cerProStat");
            return (Criteria) this;
        }

        public Criteria andCerProStatNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CER_PRO_STAT not between", value1, value2, "cerProStat");
            return (Criteria) this;
        }

        public Criteria andCerResIsNull() {
            addCriterion("CER_RES is null");
            return (Criteria) this;
        }

        public Criteria andCerResIsNotNull() {
            addCriterion("CER_RES is not null");
            return (Criteria) this;
        }

        public Criteria andCerResEqualTo(BigDecimal value) {
            addCriterion("CER_RES =", value, "cerRes");
            return (Criteria) this;
        }

        public Criteria andCerResNotEqualTo(BigDecimal value) {
            addCriterion("CER_RES <>", value, "cerRes");
            return (Criteria) this;
        }

        public Criteria andCerResGreaterThan(BigDecimal value) {
            addCriterion("CER_RES >", value, "cerRes");
            return (Criteria) this;
        }

        public Criteria andCerResGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CER_RES >=", value, "cerRes");
            return (Criteria) this;
        }

        public Criteria andCerResLessThan(BigDecimal value) {
            addCriterion("CER_RES <", value, "cerRes");
            return (Criteria) this;
        }

        public Criteria andCerResLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CER_RES <=", value, "cerRes");
            return (Criteria) this;
        }

        public Criteria andCerResIn(List<BigDecimal> values) {
            addCriterion("CER_RES in", values, "cerRes");
            return (Criteria) this;
        }

        public Criteria andCerResNotIn(List<BigDecimal> values) {
            addCriterion("CER_RES not in", values, "cerRes");
            return (Criteria) this;
        }

        public Criteria andCerResBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CER_RES between", value1, value2, "cerRes");
            return (Criteria) this;
        }

        public Criteria andCerResNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CER_RES not between", value1, value2, "cerRes");
            return (Criteria) this;
        }

        public Criteria andOrgAgNameIsNull() {
            addCriterion("ORG_AG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andOrgAgNameIsNotNull() {
            addCriterion("ORG_AG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andOrgAgNameEqualTo(String value) {
            addCriterion("ORG_AG_NAME =", value, "orgAgName");
            return (Criteria) this;
        }

        public Criteria andOrgAgNameNotEqualTo(String value) {
            addCriterion("ORG_AG_NAME <>", value, "orgAgName");
            return (Criteria) this;
        }

        public Criteria andOrgAgNameGreaterThan(String value) {
            addCriterion("ORG_AG_NAME >", value, "orgAgName");
            return (Criteria) this;
        }

        public Criteria andOrgAgNameGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_AG_NAME >=", value, "orgAgName");
            return (Criteria) this;
        }

        public Criteria andOrgAgNameLessThan(String value) {
            addCriterion("ORG_AG_NAME <", value, "orgAgName");
            return (Criteria) this;
        }

        public Criteria andOrgAgNameLessThanOrEqualTo(String value) {
            addCriterion("ORG_AG_NAME <=", value, "orgAgName");
            return (Criteria) this;
        }

        public Criteria andOrgAgNameLike(String value) {
            addCriterion("ORG_AG_NAME like", value, "orgAgName");
            return (Criteria) this;
        }

        public Criteria andOrgAgNameNotLike(String value) {
            addCriterion("ORG_AG_NAME not like", value, "orgAgName");
            return (Criteria) this;
        }

        public Criteria andOrgAgNameIn(List<String> values) {
            addCriterion("ORG_AG_NAME in", values, "orgAgName");
            return (Criteria) this;
        }

        public Criteria andOrgAgNameNotIn(List<String> values) {
            addCriterion("ORG_AG_NAME not in", values, "orgAgName");
            return (Criteria) this;
        }

        public Criteria andOrgAgNameBetween(String value1, String value2) {
            addCriterion("ORG_AG_NAME between", value1, value2, "orgAgName");
            return (Criteria) this;
        }

        public Criteria andOrgAgNameNotBetween(String value1, String value2) {
            addCriterion("ORG_AG_NAME not between", value1, value2, "orgAgName");
            return (Criteria) this;
        }

        public Criteria andOrgAgNatureIsNull() {
            addCriterion("ORG_AG_NATURE is null");
            return (Criteria) this;
        }

        public Criteria andOrgAgNatureIsNotNull() {
            addCriterion("ORG_AG_NATURE is not null");
            return (Criteria) this;
        }

        public Criteria andOrgAgNatureEqualTo(BigDecimal value) {
            addCriterion("ORG_AG_NATURE =", value, "orgAgNature");
            return (Criteria) this;
        }

        public Criteria andOrgAgNatureNotEqualTo(BigDecimal value) {
            addCriterion("ORG_AG_NATURE <>", value, "orgAgNature");
            return (Criteria) this;
        }

        public Criteria andOrgAgNatureGreaterThan(BigDecimal value) {
            addCriterion("ORG_AG_NATURE >", value, "orgAgNature");
            return (Criteria) this;
        }

        public Criteria andOrgAgNatureGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORG_AG_NATURE >=", value, "orgAgNature");
            return (Criteria) this;
        }

        public Criteria andOrgAgNatureLessThan(BigDecimal value) {
            addCriterion("ORG_AG_NATURE <", value, "orgAgNature");
            return (Criteria) this;
        }

        public Criteria andOrgAgNatureLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORG_AG_NATURE <=", value, "orgAgNature");
            return (Criteria) this;
        }

        public Criteria andOrgAgNatureIn(List<BigDecimal> values) {
            addCriterion("ORG_AG_NATURE in", values, "orgAgNature");
            return (Criteria) this;
        }

        public Criteria andOrgAgNatureNotIn(List<BigDecimal> values) {
            addCriterion("ORG_AG_NATURE not in", values, "orgAgNature");
            return (Criteria) this;
        }

        public Criteria andOrgAgNatureBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORG_AG_NATURE between", value1, value2, "orgAgNature");
            return (Criteria) this;
        }

        public Criteria andOrgAgNatureNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORG_AG_NATURE not between", value1, value2, "orgAgNature");
            return (Criteria) this;
        }

        public Criteria andOrgAgCapitalIsNull() {
            addCriterion("ORG_AG_CAPITAL is null");
            return (Criteria) this;
        }

        public Criteria andOrgAgCapitalIsNotNull() {
            addCriterion("ORG_AG_CAPITAL is not null");
            return (Criteria) this;
        }

        public Criteria andOrgAgCapitalEqualTo(BigDecimal value) {
            addCriterion("ORG_AG_CAPITAL =", value, "orgAgCapital");
            return (Criteria) this;
        }

        public Criteria andOrgAgCapitalNotEqualTo(BigDecimal value) {
            addCriterion("ORG_AG_CAPITAL <>", value, "orgAgCapital");
            return (Criteria) this;
        }

        public Criteria andOrgAgCapitalGreaterThan(BigDecimal value) {
            addCriterion("ORG_AG_CAPITAL >", value, "orgAgCapital");
            return (Criteria) this;
        }

        public Criteria andOrgAgCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORG_AG_CAPITAL >=", value, "orgAgCapital");
            return (Criteria) this;
        }

        public Criteria andOrgAgCapitalLessThan(BigDecimal value) {
            addCriterion("ORG_AG_CAPITAL <", value, "orgAgCapital");
            return (Criteria) this;
        }

        public Criteria andOrgAgCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORG_AG_CAPITAL <=", value, "orgAgCapital");
            return (Criteria) this;
        }

        public Criteria andOrgAgCapitalIn(List<BigDecimal> values) {
            addCriterion("ORG_AG_CAPITAL in", values, "orgAgCapital");
            return (Criteria) this;
        }

        public Criteria andOrgAgCapitalNotIn(List<BigDecimal> values) {
            addCriterion("ORG_AG_CAPITAL not in", values, "orgAgCapital");
            return (Criteria) this;
        }

        public Criteria andOrgAgCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORG_AG_CAPITAL between", value1, value2, "orgAgCapital");
            return (Criteria) this;
        }

        public Criteria andOrgAgCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORG_AG_CAPITAL not between", value1, value2, "orgAgCapital");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicIsNull() {
            addCriterion("ORG_AG_BUS_LIC is null");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicIsNotNull() {
            addCriterion("ORG_AG_BUS_LIC is not null");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicEqualTo(String value) {
            addCriterion("ORG_AG_BUS_LIC =", value, "orgAgBusLic");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicNotEqualTo(String value) {
            addCriterion("ORG_AG_BUS_LIC <>", value, "orgAgBusLic");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicGreaterThan(String value) {
            addCriterion("ORG_AG_BUS_LIC >", value, "orgAgBusLic");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_AG_BUS_LIC >=", value, "orgAgBusLic");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicLessThan(String value) {
            addCriterion("ORG_AG_BUS_LIC <", value, "orgAgBusLic");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicLessThanOrEqualTo(String value) {
            addCriterion("ORG_AG_BUS_LIC <=", value, "orgAgBusLic");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicLike(String value) {
            addCriterion("ORG_AG_BUS_LIC like", value, "orgAgBusLic");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicNotLike(String value) {
            addCriterion("ORG_AG_BUS_LIC not like", value, "orgAgBusLic");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicIn(List<String> values) {
            addCriterion("ORG_AG_BUS_LIC in", values, "orgAgBusLic");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicNotIn(List<String> values) {
            addCriterion("ORG_AG_BUS_LIC not in", values, "orgAgBusLic");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicBetween(String value1, String value2) {
            addCriterion("ORG_AG_BUS_LIC between", value1, value2, "orgAgBusLic");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicNotBetween(String value1, String value2) {
            addCriterion("ORG_AG_BUS_LIC not between", value1, value2, "orgAgBusLic");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicbIsNull() {
            addCriterion("ORG_AG_BUS_LICB is null");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicbIsNotNull() {
            addCriterion("ORG_AG_BUS_LICB is not null");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicbEqualTo(Date value) {
            addCriterion("ORG_AG_BUS_LICB =", value, "orgAgBusLicb");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicbNotEqualTo(Date value) {
            addCriterion("ORG_AG_BUS_LICB <>", value, "orgAgBusLicb");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicbGreaterThan(Date value) {
            addCriterion("ORG_AG_BUS_LICB >", value, "orgAgBusLicb");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicbGreaterThanOrEqualTo(Date value) {
            addCriterion("ORG_AG_BUS_LICB >=", value, "orgAgBusLicb");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicbLessThan(Date value) {
            addCriterion("ORG_AG_BUS_LICB <", value, "orgAgBusLicb");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicbLessThanOrEqualTo(Date value) {
            addCriterion("ORG_AG_BUS_LICB <=", value, "orgAgBusLicb");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicbIn(List<Date> values) {
            addCriterion("ORG_AG_BUS_LICB in", values, "orgAgBusLicb");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicbNotIn(List<Date> values) {
            addCriterion("ORG_AG_BUS_LICB not in", values, "orgAgBusLicb");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicbBetween(Date value1, Date value2) {
            addCriterion("ORG_AG_BUS_LICB between", value1, value2, "orgAgBusLicb");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLicbNotBetween(Date value1, Date value2) {
            addCriterion("ORG_AG_BUS_LICB not between", value1, value2, "orgAgBusLicb");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLiceIsNull() {
            addCriterion("ORG_AG_BUS_LICE is null");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLiceIsNotNull() {
            addCriterion("ORG_AG_BUS_LICE is not null");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLiceEqualTo(Date value) {
            addCriterion("ORG_AG_BUS_LICE =", value, "orgAgBusLice");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLiceNotEqualTo(Date value) {
            addCriterion("ORG_AG_BUS_LICE <>", value, "orgAgBusLice");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLiceGreaterThan(Date value) {
            addCriterion("ORG_AG_BUS_LICE >", value, "orgAgBusLice");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLiceGreaterThanOrEqualTo(Date value) {
            addCriterion("ORG_AG_BUS_LICE >=", value, "orgAgBusLice");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLiceLessThan(Date value) {
            addCriterion("ORG_AG_BUS_LICE <", value, "orgAgBusLice");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLiceLessThanOrEqualTo(Date value) {
            addCriterion("ORG_AG_BUS_LICE <=", value, "orgAgBusLice");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLiceIn(List<Date> values) {
            addCriterion("ORG_AG_BUS_LICE in", values, "orgAgBusLice");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLiceNotIn(List<Date> values) {
            addCriterion("ORG_AG_BUS_LICE not in", values, "orgAgBusLice");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLiceBetween(Date value1, Date value2) {
            addCriterion("ORG_AG_BUS_LICE between", value1, value2, "orgAgBusLice");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusLiceNotBetween(Date value1, Date value2) {
            addCriterion("ORG_AG_BUS_LICE not between", value1, value2, "orgAgBusLice");
            return (Criteria) this;
        }

        public Criteria andOrgAgLegalIsNull() {
            addCriterion("ORG_AG_LEGAL is null");
            return (Criteria) this;
        }

        public Criteria andOrgAgLegalIsNotNull() {
            addCriterion("ORG_AG_LEGAL is not null");
            return (Criteria) this;
        }

        public Criteria andOrgAgLegalEqualTo(String value) {
            addCriterion("ORG_AG_LEGAL =", value, "orgAgLegal");
            return (Criteria) this;
        }

        public Criteria andOrgAgLegalNotEqualTo(String value) {
            addCriterion("ORG_AG_LEGAL <>", value, "orgAgLegal");
            return (Criteria) this;
        }

        public Criteria andOrgAgLegalGreaterThan(String value) {
            addCriterion("ORG_AG_LEGAL >", value, "orgAgLegal");
            return (Criteria) this;
        }

        public Criteria andOrgAgLegalGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_AG_LEGAL >=", value, "orgAgLegal");
            return (Criteria) this;
        }

        public Criteria andOrgAgLegalLessThan(String value) {
            addCriterion("ORG_AG_LEGAL <", value, "orgAgLegal");
            return (Criteria) this;
        }

        public Criteria andOrgAgLegalLessThanOrEqualTo(String value) {
            addCriterion("ORG_AG_LEGAL <=", value, "orgAgLegal");
            return (Criteria) this;
        }

        public Criteria andOrgAgLegalLike(String value) {
            addCriterion("ORG_AG_LEGAL like", value, "orgAgLegal");
            return (Criteria) this;
        }

        public Criteria andOrgAgLegalNotLike(String value) {
            addCriterion("ORG_AG_LEGAL not like", value, "orgAgLegal");
            return (Criteria) this;
        }

        public Criteria andOrgAgLegalIn(List<String> values) {
            addCriterion("ORG_AG_LEGAL in", values, "orgAgLegal");
            return (Criteria) this;
        }

        public Criteria andOrgAgLegalNotIn(List<String> values) {
            addCriterion("ORG_AG_LEGAL not in", values, "orgAgLegal");
            return (Criteria) this;
        }

        public Criteria andOrgAgLegalBetween(String value1, String value2) {
            addCriterion("ORG_AG_LEGAL between", value1, value2, "orgAgLegal");
            return (Criteria) this;
        }

        public Criteria andOrgAgLegalNotBetween(String value1, String value2) {
            addCriterion("ORG_AG_LEGAL not between", value1, value2, "orgAgLegal");
            return (Criteria) this;
        }

        public Criteria andOrgAgRegAddIsNull() {
            addCriterion("ORG_AG_REG_ADD is null");
            return (Criteria) this;
        }

        public Criteria andOrgAgRegAddIsNotNull() {
            addCriterion("ORG_AG_REG_ADD is not null");
            return (Criteria) this;
        }

        public Criteria andOrgAgRegAddEqualTo(String value) {
            addCriterion("ORG_AG_REG_ADD =", value, "orgAgRegAdd");
            return (Criteria) this;
        }

        public Criteria andOrgAgRegAddNotEqualTo(String value) {
            addCriterion("ORG_AG_REG_ADD <>", value, "orgAgRegAdd");
            return (Criteria) this;
        }

        public Criteria andOrgAgRegAddGreaterThan(String value) {
            addCriterion("ORG_AG_REG_ADD >", value, "orgAgRegAdd");
            return (Criteria) this;
        }

        public Criteria andOrgAgRegAddGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_AG_REG_ADD >=", value, "orgAgRegAdd");
            return (Criteria) this;
        }

        public Criteria andOrgAgRegAddLessThan(String value) {
            addCriterion("ORG_AG_REG_ADD <", value, "orgAgRegAdd");
            return (Criteria) this;
        }

        public Criteria andOrgAgRegAddLessThanOrEqualTo(String value) {
            addCriterion("ORG_AG_REG_ADD <=", value, "orgAgRegAdd");
            return (Criteria) this;
        }

        public Criteria andOrgAgRegAddLike(String value) {
            addCriterion("ORG_AG_REG_ADD like", value, "orgAgRegAdd");
            return (Criteria) this;
        }

        public Criteria andOrgAgRegAddNotLike(String value) {
            addCriterion("ORG_AG_REG_ADD not like", value, "orgAgRegAdd");
            return (Criteria) this;
        }

        public Criteria andOrgAgRegAddIn(List<String> values) {
            addCriterion("ORG_AG_REG_ADD in", values, "orgAgRegAdd");
            return (Criteria) this;
        }

        public Criteria andOrgAgRegAddNotIn(List<String> values) {
            addCriterion("ORG_AG_REG_ADD not in", values, "orgAgRegAdd");
            return (Criteria) this;
        }

        public Criteria andOrgAgRegAddBetween(String value1, String value2) {
            addCriterion("ORG_AG_REG_ADD between", value1, value2, "orgAgRegAdd");
            return (Criteria) this;
        }

        public Criteria andOrgAgRegAddNotBetween(String value1, String value2) {
            addCriterion("ORG_AG_REG_ADD not between", value1, value2, "orgAgRegAdd");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusScopeIsNull() {
            addCriterion("ORG_AG_BUS_SCOPE is null");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusScopeIsNotNull() {
            addCriterion("ORG_AG_BUS_SCOPE is not null");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusScopeEqualTo(String value) {
            addCriterion("ORG_AG_BUS_SCOPE =", value, "orgAgBusScope");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusScopeNotEqualTo(String value) {
            addCriterion("ORG_AG_BUS_SCOPE <>", value, "orgAgBusScope");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusScopeGreaterThan(String value) {
            addCriterion("ORG_AG_BUS_SCOPE >", value, "orgAgBusScope");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusScopeGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_AG_BUS_SCOPE >=", value, "orgAgBusScope");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusScopeLessThan(String value) {
            addCriterion("ORG_AG_BUS_SCOPE <", value, "orgAgBusScope");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusScopeLessThanOrEqualTo(String value) {
            addCriterion("ORG_AG_BUS_SCOPE <=", value, "orgAgBusScope");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusScopeLike(String value) {
            addCriterion("ORG_AG_BUS_SCOPE like", value, "orgAgBusScope");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusScopeNotLike(String value) {
            addCriterion("ORG_AG_BUS_SCOPE not like", value, "orgAgBusScope");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusScopeIn(List<String> values) {
            addCriterion("ORG_AG_BUS_SCOPE in", values, "orgAgBusScope");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusScopeNotIn(List<String> values) {
            addCriterion("ORG_AG_BUS_SCOPE not in", values, "orgAgBusScope");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusScopeBetween(String value1, String value2) {
            addCriterion("ORG_AG_BUS_SCOPE between", value1, value2, "orgAgBusScope");
            return (Criteria) this;
        }

        public Criteria andOrgAgBusScopeNotBetween(String value1, String value2) {
            addCriterion("ORG_AG_BUS_SCOPE not between", value1, value2, "orgAgBusScope");
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