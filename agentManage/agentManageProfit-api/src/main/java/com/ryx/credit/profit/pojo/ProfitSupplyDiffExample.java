package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProfitSupplyDiffExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ProfitSupplyDiffExample() {
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

        public Criteria andParentAgentpidIsNull() {
            addCriterion("PARENT_AGENTPID is null");
            return (Criteria) this;
        }

        public Criteria andParentAgentpidIsNotNull() {
            addCriterion("PARENT_AGENTPID is not null");
            return (Criteria) this;
        }

        public Criteria andParentAgentpidEqualTo(String value) {
            addCriterion("PARENT_AGENTPID =", value, "parentAgentpid");
            return (Criteria) this;
        }

        public Criteria andParentAgentpidNotEqualTo(String value) {
            addCriterion("PARENT_AGENTPID <>", value, "parentAgentpid");
            return (Criteria) this;
        }

        public Criteria andParentAgentpidGreaterThan(String value) {
            addCriterion("PARENT_AGENTPID >", value, "parentAgentpid");
            return (Criteria) this;
        }

        public Criteria andParentAgentpidGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENTPID >=", value, "parentAgentpid");
            return (Criteria) this;
        }

        public Criteria andParentAgentpidLessThan(String value) {
            addCriterion("PARENT_AGENTPID <", value, "parentAgentpid");
            return (Criteria) this;
        }

        public Criteria andParentAgentpidLessThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENTPID <=", value, "parentAgentpid");
            return (Criteria) this;
        }

        public Criteria andParentAgentpidLike(String value) {
            addCriterion("PARENT_AGENTPID like", value, "parentAgentpid");
            return (Criteria) this;
        }

        public Criteria andParentAgentpidNotLike(String value) {
            addCriterion("PARENT_AGENTPID not like", value, "parentAgentpid");
            return (Criteria) this;
        }

        public Criteria andParentAgentpidIn(List<String> values) {
            addCriterion("PARENT_AGENTPID in", values, "parentAgentpid");
            return (Criteria) this;
        }

        public Criteria andParentAgentpidNotIn(List<String> values) {
            addCriterion("PARENT_AGENTPID not in", values, "parentAgentpid");
            return (Criteria) this;
        }

        public Criteria andParentAgentpidBetween(String value1, String value2) {
            addCriterion("PARENT_AGENTPID between", value1, value2, "parentAgentpid");
            return (Criteria) this;
        }

        public Criteria andParentAgentpidNotBetween(String value1, String value2) {
            addCriterion("PARENT_AGENTPID not between", value1, value2, "parentAgentpid");
            return (Criteria) this;
        }

        public Criteria andParentAgentidIsNull() {
            addCriterion("PARENT_AGENTID is null");
            return (Criteria) this;
        }

        public Criteria andParentAgentidIsNotNull() {
            addCriterion("PARENT_AGENTID is not null");
            return (Criteria) this;
        }

        public Criteria andParentAgentidEqualTo(String value) {
            addCriterion("PARENT_AGENTID =", value, "parentAgentid");
            return (Criteria) this;
        }

        public Criteria andParentAgentidNotEqualTo(String value) {
            addCriterion("PARENT_AGENTID <>", value, "parentAgentid");
            return (Criteria) this;
        }

        public Criteria andParentAgentidGreaterThan(String value) {
            addCriterion("PARENT_AGENTID >", value, "parentAgentid");
            return (Criteria) this;
        }

        public Criteria andParentAgentidGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENTID >=", value, "parentAgentid");
            return (Criteria) this;
        }

        public Criteria andParentAgentidLessThan(String value) {
            addCriterion("PARENT_AGENTID <", value, "parentAgentid");
            return (Criteria) this;
        }

        public Criteria andParentAgentidLessThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENTID <=", value, "parentAgentid");
            return (Criteria) this;
        }

        public Criteria andParentAgentidLike(String value) {
            addCriterion("PARENT_AGENTID like", value, "parentAgentid");
            return (Criteria) this;
        }

        public Criteria andParentAgentidNotLike(String value) {
            addCriterion("PARENT_AGENTID not like", value, "parentAgentid");
            return (Criteria) this;
        }

        public Criteria andParentAgentidIn(List<String> values) {
            addCriterion("PARENT_AGENTID in", values, "parentAgentid");
            return (Criteria) this;
        }

        public Criteria andParentAgentidNotIn(List<String> values) {
            addCriterion("PARENT_AGENTID not in", values, "parentAgentid");
            return (Criteria) this;
        }

        public Criteria andParentAgentidBetween(String value1, String value2) {
            addCriterion("PARENT_AGENTID between", value1, value2, "parentAgentid");
            return (Criteria) this;
        }

        public Criteria andParentAgentidNotBetween(String value1, String value2) {
            addCriterion("PARENT_AGENTID not between", value1, value2, "parentAgentid");
            return (Criteria) this;
        }

        public Criteria andParentAgentnameIsNull() {
            addCriterion("PARENT_AGENTNAME is null");
            return (Criteria) this;
        }

        public Criteria andParentAgentnameIsNotNull() {
            addCriterion("PARENT_AGENTNAME is not null");
            return (Criteria) this;
        }

        public Criteria andParentAgentnameEqualTo(String value) {
            addCriterion("PARENT_AGENTNAME =", value, "parentAgentname");
            return (Criteria) this;
        }

        public Criteria andParentAgentnameNotEqualTo(String value) {
            addCriterion("PARENT_AGENTNAME <>", value, "parentAgentname");
            return (Criteria) this;
        }

        public Criteria andParentAgentnameGreaterThan(String value) {
            addCriterion("PARENT_AGENTNAME >", value, "parentAgentname");
            return (Criteria) this;
        }

        public Criteria andParentAgentnameGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENTNAME >=", value, "parentAgentname");
            return (Criteria) this;
        }

        public Criteria andParentAgentnameLessThan(String value) {
            addCriterion("PARENT_AGENTNAME <", value, "parentAgentname");
            return (Criteria) this;
        }

        public Criteria andParentAgentnameLessThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENTNAME <=", value, "parentAgentname");
            return (Criteria) this;
        }

        public Criteria andParentAgentnameLike(String value) {
            addCriterion("PARENT_AGENTNAME like", value, "parentAgentname");
            return (Criteria) this;
        }

        public Criteria andParentAgentnameNotLike(String value) {
            addCriterion("PARENT_AGENTNAME not like", value, "parentAgentname");
            return (Criteria) this;
        }

        public Criteria andParentAgentnameIn(List<String> values) {
            addCriterion("PARENT_AGENTNAME in", values, "parentAgentname");
            return (Criteria) this;
        }

        public Criteria andParentAgentnameNotIn(List<String> values) {
            addCriterion("PARENT_AGENTNAME not in", values, "parentAgentname");
            return (Criteria) this;
        }

        public Criteria andParentAgentnameBetween(String value1, String value2) {
            addCriterion("PARENT_AGENTNAME between", value1, value2, "parentAgentname");
            return (Criteria) this;
        }

        public Criteria andParentAgentnameNotBetween(String value1, String value2) {
            addCriterion("PARENT_AGENTNAME not between", value1, value2, "parentAgentname");
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

        public Criteria andAgentPidIsNull() {
            addCriterion("AGENT_PID is null");
            return (Criteria) this;
        }

        public Criteria andAgentPidIsNotNull() {
            addCriterion("AGENT_PID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentPidEqualTo(String value) {
            addCriterion("AGENT_PID =", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotEqualTo(String value) {
            addCriterion("AGENT_PID <>", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidGreaterThan(String value) {
            addCriterion("AGENT_PID >", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_PID >=", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLessThan(String value) {
            addCriterion("AGENT_PID <", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLessThanOrEqualTo(String value) {
            addCriterion("AGENT_PID <=", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLike(String value) {
            addCriterion("AGENT_PID like", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotLike(String value) {
            addCriterion("AGENT_PID not like", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidIn(List<String> values) {
            addCriterion("AGENT_PID in", values, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotIn(List<String> values) {
            addCriterion("AGENT_PID not in", values, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidBetween(String value1, String value2) {
            addCriterion("AGENT_PID between", value1, value2, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotBetween(String value1, String value2) {
            addCriterion("AGENT_PID not between", value1, value2, "agentPid");
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

        public Criteria andDiffAmtIsNull() {
            addCriterion("DIFF_AMT is null");
            return (Criteria) this;
        }

        public Criteria andDiffAmtIsNotNull() {
            addCriterion("DIFF_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andDiffAmtEqualTo(BigDecimal value) {
            addCriterion("DIFF_AMT =", value, "diffAmt");
            return (Criteria) this;
        }

        public Criteria andDiffAmtNotEqualTo(BigDecimal value) {
            addCriterion("DIFF_AMT <>", value, "diffAmt");
            return (Criteria) this;
        }

        public Criteria andDiffAmtGreaterThan(BigDecimal value) {
            addCriterion("DIFF_AMT >", value, "diffAmt");
            return (Criteria) this;
        }

        public Criteria andDiffAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DIFF_AMT >=", value, "diffAmt");
            return (Criteria) this;
        }

        public Criteria andDiffAmtLessThan(BigDecimal value) {
            addCriterion("DIFF_AMT <", value, "diffAmt");
            return (Criteria) this;
        }

        public Criteria andDiffAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DIFF_AMT <=", value, "diffAmt");
            return (Criteria) this;
        }

        public Criteria andDiffAmtIn(List<BigDecimal> values) {
            addCriterion("DIFF_AMT in", values, "diffAmt");
            return (Criteria) this;
        }

        public Criteria andDiffAmtNotIn(List<BigDecimal> values) {
            addCriterion("DIFF_AMT not in", values, "diffAmt");
            return (Criteria) this;
        }

        public Criteria andDiffAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DIFF_AMT between", value1, value2, "diffAmt");
            return (Criteria) this;
        }

        public Criteria andDiffAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DIFF_AMT not between", value1, value2, "diffAmt");
            return (Criteria) this;
        }

        public Criteria andDiffDateIsNull() {
            addCriterion("DIFF_DATE is null");
            return (Criteria) this;
        }

        public Criteria andDiffDateIsNotNull() {
            addCriterion("DIFF_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andDiffDateEqualTo(String value) {
            addCriterion("DIFF_DATE =", value, "diffDate");
            return (Criteria) this;
        }

        public Criteria andDiffDateNotEqualTo(String value) {
            addCriterion("DIFF_DATE <>", value, "diffDate");
            return (Criteria) this;
        }

        public Criteria andDiffDateGreaterThan(String value) {
            addCriterion("DIFF_DATE >", value, "diffDate");
            return (Criteria) this;
        }

        public Criteria andDiffDateGreaterThanOrEqualTo(String value) {
            addCriterion("DIFF_DATE >=", value, "diffDate");
            return (Criteria) this;
        }

        public Criteria andDiffDateLessThan(String value) {
            addCriterion("DIFF_DATE <", value, "diffDate");
            return (Criteria) this;
        }

        public Criteria andDiffDateLessThanOrEqualTo(String value) {
            addCriterion("DIFF_DATE <=", value, "diffDate");
            return (Criteria) this;
        }

        public Criteria andDiffDateLike(String value) {
            addCriterion("DIFF_DATE like", value, "diffDate");
            return (Criteria) this;
        }

        public Criteria andDiffDateNotLike(String value) {
            addCriterion("DIFF_DATE not like", value, "diffDate");
            return (Criteria) this;
        }

        public Criteria andDiffDateIn(List<String> values) {
            addCriterion("DIFF_DATE in", values, "diffDate");
            return (Criteria) this;
        }

        public Criteria andDiffDateNotIn(List<String> values) {
            addCriterion("DIFF_DATE not in", values, "diffDate");
            return (Criteria) this;
        }

        public Criteria andDiffDateBetween(String value1, String value2) {
            addCriterion("DIFF_DATE between", value1, value2, "diffDate");
            return (Criteria) this;
        }

        public Criteria andDiffDateNotBetween(String value1, String value2) {
            addCriterion("DIFF_DATE not between", value1, value2, "diffDate");
            return (Criteria) this;
        }

        public Criteria andDiffTypeIsNull() {
            addCriterion("DIFF_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andDiffTypeIsNotNull() {
            addCriterion("DIFF_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andDiffTypeEqualTo(String value) {
            addCriterion("DIFF_TYPE =", value, "diffType");
            return (Criteria) this;
        }

        public Criteria andDiffTypeNotEqualTo(String value) {
            addCriterion("DIFF_TYPE <>", value, "diffType");
            return (Criteria) this;
        }

        public Criteria andDiffTypeGreaterThan(String value) {
            addCriterion("DIFF_TYPE >", value, "diffType");
            return (Criteria) this;
        }

        public Criteria andDiffTypeGreaterThanOrEqualTo(String value) {
            addCriterion("DIFF_TYPE >=", value, "diffType");
            return (Criteria) this;
        }

        public Criteria andDiffTypeLessThan(String value) {
            addCriterion("DIFF_TYPE <", value, "diffType");
            return (Criteria) this;
        }

        public Criteria andDiffTypeLessThanOrEqualTo(String value) {
            addCriterion("DIFF_TYPE <=", value, "diffType");
            return (Criteria) this;
        }

        public Criteria andDiffTypeLike(String value) {
            addCriterion("DIFF_TYPE like", value, "diffType");
            return (Criteria) this;
        }

        public Criteria andDiffTypeNotLike(String value) {
            addCriterion("DIFF_TYPE not like", value, "diffType");
            return (Criteria) this;
        }

        public Criteria andDiffTypeIn(List<String> values) {
            addCriterion("DIFF_TYPE in", values, "diffType");
            return (Criteria) this;
        }

        public Criteria andDiffTypeNotIn(List<String> values) {
            addCriterion("DIFF_TYPE not in", values, "diffType");
            return (Criteria) this;
        }

        public Criteria andDiffTypeBetween(String value1, String value2) {
            addCriterion("DIFF_TYPE between", value1, value2, "diffType");
            return (Criteria) this;
        }

        public Criteria andDiffTypeNotBetween(String value1, String value2) {
            addCriterion("DIFF_TYPE not between", value1, value2, "diffType");
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