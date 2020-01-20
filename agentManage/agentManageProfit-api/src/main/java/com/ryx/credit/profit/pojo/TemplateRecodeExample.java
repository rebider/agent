package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.util.ArrayList;
import java.util.List;

public class TemplateRecodeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public TemplateRecodeExample() {
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

        public Criteria andCreateDateIsNull() {
            addCriterion("CREATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("CREATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(String value) {
            addCriterion("CREATE_DATE =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(String value) {
            addCriterion("CREATE_DATE <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(String value) {
            addCriterion("CREATE_DATE >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_DATE >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(String value) {
            addCriterion("CREATE_DATE <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(String value) {
            addCriterion("CREATE_DATE <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLike(String value) {
            addCriterion("CREATE_DATE like", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotLike(String value) {
            addCriterion("CREATE_DATE not like", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<String> values) {
            addCriterion("CREATE_DATE in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<String> values) {
            addCriterion("CREATE_DATE not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(String value1, String value2) {
            addCriterion("CREATE_DATE between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(String value1, String value2) {
            addCriterion("CREATE_DATE not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andApplyResultIsNull() {
            addCriterion("APPLY_RESULT is null");
            return (Criteria) this;
        }

        public Criteria andApplyResultIsNotNull() {
            addCriterion("APPLY_RESULT is not null");
            return (Criteria) this;
        }

        public Criteria andApplyResultEqualTo(String value) {
            addCriterion("APPLY_RESULT =", value, "applyResult");
            return (Criteria) this;
        }

        public Criteria andApplyResultNotEqualTo(String value) {
            addCriterion("APPLY_RESULT <>", value, "applyResult");
            return (Criteria) this;
        }

        public Criteria andApplyResultGreaterThan(String value) {
            addCriterion("APPLY_RESULT >", value, "applyResult");
            return (Criteria) this;
        }

        public Criteria andApplyResultGreaterThanOrEqualTo(String value) {
            addCriterion("APPLY_RESULT >=", value, "applyResult");
            return (Criteria) this;
        }

        public Criteria andApplyResultLessThan(String value) {
            addCriterion("APPLY_RESULT <", value, "applyResult");
            return (Criteria) this;
        }

        public Criteria andApplyResultLessThanOrEqualTo(String value) {
            addCriterion("APPLY_RESULT <=", value, "applyResult");
            return (Criteria) this;
        }

        public Criteria andApplyResultLike(String value) {
            addCriterion("APPLY_RESULT like", value, "applyResult");
            return (Criteria) this;
        }

        public Criteria andApplyResultNotLike(String value) {
            addCriterion("APPLY_RESULT not like", value, "applyResult");
            return (Criteria) this;
        }

        public Criteria andApplyResultIn(List<String> values) {
            addCriterion("APPLY_RESULT in", values, "applyResult");
            return (Criteria) this;
        }

        public Criteria andApplyResultNotIn(List<String> values) {
            addCriterion("APPLY_RESULT not in", values, "applyResult");
            return (Criteria) this;
        }

        public Criteria andApplyResultBetween(String value1, String value2) {
            addCriterion("APPLY_RESULT between", value1, value2, "applyResult");
            return (Criteria) this;
        }

        public Criteria andApplyResultNotBetween(String value1, String value2) {
            addCriterion("APPLY_RESULT not between", value1, value2, "applyResult");
            return (Criteria) this;
        }

        public Criteria andTemplateNameIsNull() {
            addCriterion("TEMPLATE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andTemplateNameIsNotNull() {
            addCriterion("TEMPLATE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateNameEqualTo(String value) {
            addCriterion("TEMPLATE_NAME =", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameNotEqualTo(String value) {
            addCriterion("TEMPLATE_NAME <>", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameGreaterThan(String value) {
            addCriterion("TEMPLATE_NAME >", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameGreaterThanOrEqualTo(String value) {
            addCriterion("TEMPLATE_NAME >=", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameLessThan(String value) {
            addCriterion("TEMPLATE_NAME <", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameLessThanOrEqualTo(String value) {
            addCriterion("TEMPLATE_NAME <=", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameLike(String value) {
            addCriterion("TEMPLATE_NAME like", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameNotLike(String value) {
            addCriterion("TEMPLATE_NAME not like", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameIn(List<String> values) {
            addCriterion("TEMPLATE_NAME in", values, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameNotIn(List<String> values) {
            addCriterion("TEMPLATE_NAME not in", values, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameBetween(String value1, String value2) {
            addCriterion("TEMPLATE_NAME between", value1, value2, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameNotBetween(String value1, String value2) {
            addCriterion("TEMPLATE_NAME not between", value1, value2, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNull() {
            addCriterion("TEMPLATE_ID is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("TEMPLATE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(String value) {
            addCriterion("TEMPLATE_ID =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(String value) {
            addCriterion("TEMPLATE_ID <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(String value) {
            addCriterion("TEMPLATE_ID >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(String value) {
            addCriterion("TEMPLATE_ID >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(String value) {
            addCriterion("TEMPLATE_ID <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(String value) {
            addCriterion("TEMPLATE_ID <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLike(String value) {
            addCriterion("TEMPLATE_ID like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotLike(String value) {
            addCriterion("TEMPLATE_ID not like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<String> values) {
            addCriterion("TEMPLATE_ID in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<String> values) {
            addCriterion("TEMPLATE_ID not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(String value1, String value2) {
            addCriterion("TEMPLATE_ID between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(String value1, String value2) {
            addCriterion("TEMPLATE_ID not between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("CREATE_USER is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("CREATE_USER is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("CREATE_USER =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("CREATE_USER <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("CREATE_USER >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_USER >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("CREATE_USER <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("CREATE_USER <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("CREATE_USER like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("CREATE_USER not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("CREATE_USER in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("CREATE_USER not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("CREATE_USER between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("CREATE_USER not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andAssignResultIsNull() {
            addCriterion("ASSIGN_RESULT is null");
            return (Criteria) this;
        }

        public Criteria andAssignResultIsNotNull() {
            addCriterion("ASSIGN_RESULT is not null");
            return (Criteria) this;
        }

        public Criteria andAssignResultEqualTo(String value) {
            addCriterion("ASSIGN_RESULT =", value, "assignResult");
            return (Criteria) this;
        }

        public Criteria andAssignResultNotEqualTo(String value) {
            addCriterion("ASSIGN_RESULT <>", value, "assignResult");
            return (Criteria) this;
        }

        public Criteria andAssignResultGreaterThan(String value) {
            addCriterion("ASSIGN_RESULT >", value, "assignResult");
            return (Criteria) this;
        }

        public Criteria andAssignResultGreaterThanOrEqualTo(String value) {
            addCriterion("ASSIGN_RESULT >=", value, "assignResult");
            return (Criteria) this;
        }

        public Criteria andAssignResultLessThan(String value) {
            addCriterion("ASSIGN_RESULT <", value, "assignResult");
            return (Criteria) this;
        }

        public Criteria andAssignResultLessThanOrEqualTo(String value) {
            addCriterion("ASSIGN_RESULT <=", value, "assignResult");
            return (Criteria) this;
        }

        public Criteria andAssignResultLike(String value) {
            addCriterion("ASSIGN_RESULT like", value, "assignResult");
            return (Criteria) this;
        }

        public Criteria andAssignResultNotLike(String value) {
            addCriterion("ASSIGN_RESULT not like", value, "assignResult");
            return (Criteria) this;
        }

        public Criteria andAssignResultIn(List<String> values) {
            addCriterion("ASSIGN_RESULT in", values, "assignResult");
            return (Criteria) this;
        }

        public Criteria andAssignResultNotIn(List<String> values) {
            addCriterion("ASSIGN_RESULT not in", values, "assignResult");
            return (Criteria) this;
        }

        public Criteria andAssignResultBetween(String value1, String value2) {
            addCriterion("ASSIGN_RESULT between", value1, value2, "assignResult");
            return (Criteria) this;
        }

        public Criteria andAssignResultNotBetween(String value1, String value2) {
            addCriterion("ASSIGN_RESULT not between", value1, value2, "assignResult");
            return (Criteria) this;
        }

        public Criteria andAssignReasonIsNull() {
            addCriterion("ASSIGN_REASON is null");
            return (Criteria) this;
        }

        public Criteria andAssignReasonIsNotNull() {
            addCriterion("ASSIGN_REASON is not null");
            return (Criteria) this;
        }

        public Criteria andAssignReasonEqualTo(String value) {
            addCriterion("ASSIGN_REASON =", value, "assignReason");
            return (Criteria) this;
        }

        public Criteria andAssignReasonNotEqualTo(String value) {
            addCriterion("ASSIGN_REASON <>", value, "assignReason");
            return (Criteria) this;
        }

        public Criteria andAssignReasonGreaterThan(String value) {
            addCriterion("ASSIGN_REASON >", value, "assignReason");
            return (Criteria) this;
        }

        public Criteria andAssignReasonGreaterThanOrEqualTo(String value) {
            addCriterion("ASSIGN_REASON >=", value, "assignReason");
            return (Criteria) this;
        }

        public Criteria andAssignReasonLessThan(String value) {
            addCriterion("ASSIGN_REASON <", value, "assignReason");
            return (Criteria) this;
        }

        public Criteria andAssignReasonLessThanOrEqualTo(String value) {
            addCriterion("ASSIGN_REASON <=", value, "assignReason");
            return (Criteria) this;
        }

        public Criteria andAssignReasonLike(String value) {
            addCriterion("ASSIGN_REASON like", value, "assignReason");
            return (Criteria) this;
        }

        public Criteria andAssignReasonNotLike(String value) {
            addCriterion("ASSIGN_REASON not like", value, "assignReason");
            return (Criteria) this;
        }

        public Criteria andAssignReasonIn(List<String> values) {
            addCriterion("ASSIGN_REASON in", values, "assignReason");
            return (Criteria) this;
        }

        public Criteria andAssignReasonNotIn(List<String> values) {
            addCriterion("ASSIGN_REASON not in", values, "assignReason");
            return (Criteria) this;
        }

        public Criteria andAssignReasonBetween(String value1, String value2) {
            addCriterion("ASSIGN_REASON between", value1, value2, "assignReason");
            return (Criteria) this;
        }

        public Criteria andAssignReasonNotBetween(String value1, String value2) {
            addCriterion("ASSIGN_REASON not between", value1, value2, "assignReason");
            return (Criteria) this;
        }

        public Criteria andCreTypeIsNull() {
            addCriterion("CRE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCreTypeIsNotNull() {
            addCriterion("CRE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCreTypeEqualTo(String value) {
            addCriterion("CRE_TYPE =", value, "creType");
            return (Criteria) this;
        }

        public Criteria andCreTypeNotEqualTo(String value) {
            addCriterion("CRE_TYPE <>", value, "creType");
            return (Criteria) this;
        }

        public Criteria andCreTypeGreaterThan(String value) {
            addCriterion("CRE_TYPE >", value, "creType");
            return (Criteria) this;
        }

        public Criteria andCreTypeGreaterThanOrEqualTo(String value) {
            addCriterion("CRE_TYPE >=", value, "creType");
            return (Criteria) this;
        }

        public Criteria andCreTypeLessThan(String value) {
            addCriterion("CRE_TYPE <", value, "creType");
            return (Criteria) this;
        }

        public Criteria andCreTypeLessThanOrEqualTo(String value) {
            addCriterion("CRE_TYPE <=", value, "creType");
            return (Criteria) this;
        }

        public Criteria andCreTypeLike(String value) {
            addCriterion("CRE_TYPE like", value, "creType");
            return (Criteria) this;
        }

        public Criteria andCreTypeNotLike(String value) {
            addCriterion("CRE_TYPE not like", value, "creType");
            return (Criteria) this;
        }

        public Criteria andCreTypeIn(List<String> values) {
            addCriterion("CRE_TYPE in", values, "creType");
            return (Criteria) this;
        }

        public Criteria andCreTypeNotIn(List<String> values) {
            addCriterion("CRE_TYPE not in", values, "creType");
            return (Criteria) this;
        }

        public Criteria andCreTypeBetween(String value1, String value2) {
            addCriterion("CRE_TYPE between", value1, value2, "creType");
            return (Criteria) this;
        }

        public Criteria andCreTypeNotBetween(String value1, String value2) {
            addCriterion("CRE_TYPE not between", value1, value2, "creType");
            return (Criteria) this;
        }

        public Criteria andTemTypeIsNull() {
            addCriterion("TEM_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTemTypeIsNotNull() {
            addCriterion("TEM_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTemTypeEqualTo(String value) {
            addCriterion("TEM_TYPE =", value, "temType");
            return (Criteria) this;
        }

        public Criteria andTemTypeNotEqualTo(String value) {
            addCriterion("TEM_TYPE <>", value, "temType");
            return (Criteria) this;
        }

        public Criteria andTemTypeGreaterThan(String value) {
            addCriterion("TEM_TYPE >", value, "temType");
            return (Criteria) this;
        }

        public Criteria andTemTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TEM_TYPE >=", value, "temType");
            return (Criteria) this;
        }

        public Criteria andTemTypeLessThan(String value) {
            addCriterion("TEM_TYPE <", value, "temType");
            return (Criteria) this;
        }

        public Criteria andTemTypeLessThanOrEqualTo(String value) {
            addCriterion("TEM_TYPE <=", value, "temType");
            return (Criteria) this;
        }

        public Criteria andTemTypeLike(String value) {
            addCriterion("TEM_TYPE like", value, "temType");
            return (Criteria) this;
        }

        public Criteria andTemTypeNotLike(String value) {
            addCriterion("TEM_TYPE not like", value, "temType");
            return (Criteria) this;
        }

        public Criteria andTemTypeIn(List<String> values) {
            addCriterion("TEM_TYPE in", values, "temType");
            return (Criteria) this;
        }

        public Criteria andTemTypeNotIn(List<String> values) {
            addCriterion("TEM_TYPE not in", values, "temType");
            return (Criteria) this;
        }

        public Criteria andTemTypeBetween(String value1, String value2) {
            addCriterion("TEM_TYPE between", value1, value2, "temType");
            return (Criteria) this;
        }

        public Criteria andTemTypeNotBetween(String value1, String value2) {
            addCriterion("TEM_TYPE not between", value1, value2, "temType");
            return (Criteria) this;
        }

        public Criteria andRev1IsNull() {
            addCriterion("REV1 is null");
            return (Criteria) this;
        }

        public Criteria andRev1IsNotNull() {
            addCriterion("REV1 is not null");
            return (Criteria) this;
        }

        public Criteria andRev1EqualTo(String value) {
            addCriterion("REV1 =", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotEqualTo(String value) {
            addCriterion("REV1 <>", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1GreaterThan(String value) {
            addCriterion("REV1 >", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1GreaterThanOrEqualTo(String value) {
            addCriterion("REV1 >=", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1LessThan(String value) {
            addCriterion("REV1 <", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1LessThanOrEqualTo(String value) {
            addCriterion("REV1 <=", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1Like(String value) {
            addCriterion("REV1 like", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotLike(String value) {
            addCriterion("REV1 not like", value, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1In(List<String> values) {
            addCriterion("REV1 in", values, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotIn(List<String> values) {
            addCriterion("REV1 not in", values, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1Between(String value1, String value2) {
            addCriterion("REV1 between", value1, value2, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev1NotBetween(String value1, String value2) {
            addCriterion("REV1 not between", value1, value2, "rev1");
            return (Criteria) this;
        }

        public Criteria andRev2IsNull() {
            addCriterion("REV2 is null");
            return (Criteria) this;
        }

        public Criteria andRev2IsNotNull() {
            addCriterion("REV2 is not null");
            return (Criteria) this;
        }

        public Criteria andRev2EqualTo(String value) {
            addCriterion("REV2 =", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotEqualTo(String value) {
            addCriterion("REV2 <>", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2GreaterThan(String value) {
            addCriterion("REV2 >", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2GreaterThanOrEqualTo(String value) {
            addCriterion("REV2 >=", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2LessThan(String value) {
            addCriterion("REV2 <", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2LessThanOrEqualTo(String value) {
            addCriterion("REV2 <=", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2Like(String value) {
            addCriterion("REV2 like", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotLike(String value) {
            addCriterion("REV2 not like", value, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2In(List<String> values) {
            addCriterion("REV2 in", values, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotIn(List<String> values) {
            addCriterion("REV2 not in", values, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2Between(String value1, String value2) {
            addCriterion("REV2 between", value1, value2, "rev2");
            return (Criteria) this;
        }

        public Criteria andRev2NotBetween(String value1, String value2) {
            addCriterion("REV2 not between", value1, value2, "rev2");
            return (Criteria) this;
        }

        public Criteria andBusNumSIsNull() {
            addCriterion("BUS_NUM_S is null");
            return (Criteria) this;
        }

        public Criteria andBusNumSIsNotNull() {
            addCriterion("BUS_NUM_S is not null");
            return (Criteria) this;
        }

        public Criteria andBusNumSEqualTo(String value) {
            addCriterion("BUS_NUM_S =", value, "busNumS");
            return (Criteria) this;
        }

        public Criteria andBusNumSNotEqualTo(String value) {
            addCriterion("BUS_NUM_S <>", value, "busNumS");
            return (Criteria) this;
        }

        public Criteria andBusNumSGreaterThan(String value) {
            addCriterion("BUS_NUM_S >", value, "busNumS");
            return (Criteria) this;
        }

        public Criteria andBusNumSGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_NUM_S >=", value, "busNumS");
            return (Criteria) this;
        }

        public Criteria andBusNumSLessThan(String value) {
            addCriterion("BUS_NUM_S <", value, "busNumS");
            return (Criteria) this;
        }

        public Criteria andBusNumSLessThanOrEqualTo(String value) {
            addCriterion("BUS_NUM_S <=", value, "busNumS");
            return (Criteria) this;
        }

        public Criteria andBusNumSLike(String value) {
            addCriterion("BUS_NUM_S like", value, "busNumS");
            return (Criteria) this;
        }

        public Criteria andBusNumSNotLike(String value) {
            addCriterion("BUS_NUM_S not like", value, "busNumS");
            return (Criteria) this;
        }

        public Criteria andBusNumSIn(List<String> values) {
            addCriterion("BUS_NUM_S in", values, "busNumS");
            return (Criteria) this;
        }

        public Criteria andBusNumSNotIn(List<String> values) {
            addCriterion("BUS_NUM_S not in", values, "busNumS");
            return (Criteria) this;
        }

        public Criteria andBusNumSBetween(String value1, String value2) {
            addCriterion("BUS_NUM_S between", value1, value2, "busNumS");
            return (Criteria) this;
        }

        public Criteria andBusNumSNotBetween(String value1, String value2) {
            addCriterion("BUS_NUM_S not between", value1, value2, "busNumS");
            return (Criteria) this;
        }

        public Criteria andChangeflagIsNull() {
            addCriterion("CHANGEFLAG is null");
            return (Criteria) this;
        }

        public Criteria andChangeflagIsNotNull() {
            addCriterion("CHANGEFLAG is not null");
            return (Criteria) this;
        }

        public Criteria andChangeflagEqualTo(String value) {
            addCriterion("CHANGEFLAG =", value, "changeflag");
            return (Criteria) this;
        }

        public Criteria andChangeflagNotEqualTo(String value) {
            addCriterion("CHANGEFLAG <>", value, "changeflag");
            return (Criteria) this;
        }

        public Criteria andChangeflagGreaterThan(String value) {
            addCriterion("CHANGEFLAG >", value, "changeflag");
            return (Criteria) this;
        }

        public Criteria andChangeflagGreaterThanOrEqualTo(String value) {
            addCriterion("CHANGEFLAG >=", value, "changeflag");
            return (Criteria) this;
        }

        public Criteria andChangeflagLessThan(String value) {
            addCriterion("CHANGEFLAG <", value, "changeflag");
            return (Criteria) this;
        }

        public Criteria andChangeflagLessThanOrEqualTo(String value) {
            addCriterion("CHANGEFLAG <=", value, "changeflag");
            return (Criteria) this;
        }

        public Criteria andChangeflagLike(String value) {
            addCriterion("CHANGEFLAG like", value, "changeflag");
            return (Criteria) this;
        }

        public Criteria andChangeflagNotLike(String value) {
            addCriterion("CHANGEFLAG not like", value, "changeflag");
            return (Criteria) this;
        }

        public Criteria andChangeflagIn(List<String> values) {
            addCriterion("CHANGEFLAG in", values, "changeflag");
            return (Criteria) this;
        }

        public Criteria andChangeflagNotIn(List<String> values) {
            addCriterion("CHANGEFLAG not in", values, "changeflag");
            return (Criteria) this;
        }

        public Criteria andChangeflagBetween(String value1, String value2) {
            addCriterion("CHANGEFLAG between", value1, value2, "changeflag");
            return (Criteria) this;
        }

        public Criteria andChangeflagNotBetween(String value1, String value2) {
            addCriterion("CHANGEFLAG not between", value1, value2, "changeflag");
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