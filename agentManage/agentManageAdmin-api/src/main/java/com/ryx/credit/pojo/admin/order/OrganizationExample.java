package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrganizationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OrganizationExample() {
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

        public Criteria andOrgIdIsNull() {
            addCriterion("ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNotNull() {
            addCriterion("ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrgIdEqualTo(String value) {
            addCriterion("ORG_ID =", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotEqualTo(String value) {
            addCriterion("ORG_ID <>", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThan(String value) {
            addCriterion("ORG_ID >", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_ID >=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThan(String value) {
            addCriterion("ORG_ID <", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThanOrEqualTo(String value) {
            addCriterion("ORG_ID <=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLike(String value) {
            addCriterion("ORG_ID like", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotLike(String value) {
            addCriterion("ORG_ID not like", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIn(List<String> values) {
            addCriterion("ORG_ID in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotIn(List<String> values) {
            addCriterion("ORG_ID not in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdBetween(String value1, String value2) {
            addCriterion("ORG_ID between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotBetween(String value1, String value2) {
            addCriterion("ORG_ID not between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgNickIsNull() {
            addCriterion("ORG_NICK is null");
            return (Criteria) this;
        }

        public Criteria andOrgNickIsNotNull() {
            addCriterion("ORG_NICK is not null");
            return (Criteria) this;
        }

        public Criteria andOrgNickEqualTo(String value) {
            addCriterion("ORG_NICK =", value, "orgNick");
            return (Criteria) this;
        }

        public Criteria andOrgNickNotEqualTo(String value) {
            addCriterion("ORG_NICK <>", value, "orgNick");
            return (Criteria) this;
        }

        public Criteria andOrgNickGreaterThan(String value) {
            addCriterion("ORG_NICK >", value, "orgNick");
            return (Criteria) this;
        }

        public Criteria andOrgNickGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_NICK >=", value, "orgNick");
            return (Criteria) this;
        }

        public Criteria andOrgNickLessThan(String value) {
            addCriterion("ORG_NICK <", value, "orgNick");
            return (Criteria) this;
        }

        public Criteria andOrgNickLessThanOrEqualTo(String value) {
            addCriterion("ORG_NICK <=", value, "orgNick");
            return (Criteria) this;
        }

        public Criteria andOrgNickLike(String value) {
            addCriterion("ORG_NICK like", value, "orgNick");
            return (Criteria) this;
        }

        public Criteria andOrgNickNotLike(String value) {
            addCriterion("ORG_NICK not like", value, "orgNick");
            return (Criteria) this;
        }

        public Criteria andOrgNickIn(List<String> values) {
            addCriterion("ORG_NICK in", values, "orgNick");
            return (Criteria) this;
        }

        public Criteria andOrgNickNotIn(List<String> values) {
            addCriterion("ORG_NICK not in", values, "orgNick");
            return (Criteria) this;
        }

        public Criteria andOrgNickBetween(String value1, String value2) {
            addCriterion("ORG_NICK between", value1, value2, "orgNick");
            return (Criteria) this;
        }

        public Criteria andOrgNickNotBetween(String value1, String value2) {
            addCriterion("ORG_NICK not between", value1, value2, "orgNick");
            return (Criteria) this;
        }

        public Criteria andOrgNameIsNull() {
            addCriterion("ORG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andOrgNameIsNotNull() {
            addCriterion("ORG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andOrgNameEqualTo(String value) {
            addCriterion("ORG_NAME =", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotEqualTo(String value) {
            addCriterion("ORG_NAME <>", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameGreaterThan(String value) {
            addCriterion("ORG_NAME >", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_NAME >=", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLessThan(String value) {
            addCriterion("ORG_NAME <", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLessThanOrEqualTo(String value) {
            addCriterion("ORG_NAME <=", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLike(String value) {
            addCriterion("ORG_NAME like", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotLike(String value) {
            addCriterion("ORG_NAME not like", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameIn(List<String> values) {
            addCriterion("ORG_NAME in", values, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotIn(List<String> values) {
            addCriterion("ORG_NAME not in", values, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameBetween(String value1, String value2) {
            addCriterion("ORG_NAME between", value1, value2, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotBetween(String value1, String value2) {
            addCriterion("ORG_NAME not between", value1, value2, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIsNull() {
            addCriterion("ORG_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIsNotNull() {
            addCriterion("ORG_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andOrgLevelEqualTo(String value) {
            addCriterion("ORG_LEVEL =", value, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelNotEqualTo(String value) {
            addCriterion("ORG_LEVEL <>", value, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelGreaterThan(String value) {
            addCriterion("ORG_LEVEL >", value, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_LEVEL >=", value, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelLessThan(String value) {
            addCriterion("ORG_LEVEL <", value, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelLessThanOrEqualTo(String value) {
            addCriterion("ORG_LEVEL <=", value, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelLike(String value) {
            addCriterion("ORG_LEVEL like", value, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelNotLike(String value) {
            addCriterion("ORG_LEVEL not like", value, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIn(List<String> values) {
            addCriterion("ORG_LEVEL in", values, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelNotIn(List<String> values) {
            addCriterion("ORG_LEVEL not in", values, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelBetween(String value1, String value2) {
            addCriterion("ORG_LEVEL between", value1, value2, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelNotBetween(String value1, String value2) {
            addCriterion("ORG_LEVEL not between", value1, value2, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgTypeIsNull() {
            addCriterion("ORG_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andOrgTypeIsNotNull() {
            addCriterion("ORG_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andOrgTypeEqualTo(String value) {
            addCriterion("ORG_TYPE =", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeNotEqualTo(String value) {
            addCriterion("ORG_TYPE <>", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeGreaterThan(String value) {
            addCriterion("ORG_TYPE >", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_TYPE >=", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeLessThan(String value) {
            addCriterion("ORG_TYPE <", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeLessThanOrEqualTo(String value) {
            addCriterion("ORG_TYPE <=", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeLike(String value) {
            addCriterion("ORG_TYPE like", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeNotLike(String value) {
            addCriterion("ORG_TYPE not like", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeIn(List<String> values) {
            addCriterion("ORG_TYPE in", values, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeNotIn(List<String> values) {
            addCriterion("ORG_TYPE not in", values, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeBetween(String value1, String value2) {
            addCriterion("ORG_TYPE between", value1, value2, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeNotBetween(String value1, String value2) {
            addCriterion("ORG_TYPE not between", value1, value2, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgParentIsNull() {
            addCriterion("ORG_PARENT is null");
            return (Criteria) this;
        }

        public Criteria andOrgParentIsNotNull() {
            addCriterion("ORG_PARENT is not null");
            return (Criteria) this;
        }

        public Criteria andOrgParentEqualTo(String value) {
            addCriterion("ORG_PARENT =", value, "orgParent");
            return (Criteria) this;
        }

        public Criteria andOrgParentNotEqualTo(String value) {
            addCriterion("ORG_PARENT <>", value, "orgParent");
            return (Criteria) this;
        }

        public Criteria andOrgParentGreaterThan(String value) {
            addCriterion("ORG_PARENT >", value, "orgParent");
            return (Criteria) this;
        }

        public Criteria andOrgParentGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_PARENT >=", value, "orgParent");
            return (Criteria) this;
        }

        public Criteria andOrgParentLessThan(String value) {
            addCriterion("ORG_PARENT <", value, "orgParent");
            return (Criteria) this;
        }

        public Criteria andOrgParentLessThanOrEqualTo(String value) {
            addCriterion("ORG_PARENT <=", value, "orgParent");
            return (Criteria) this;
        }

        public Criteria andOrgParentLike(String value) {
            addCriterion("ORG_PARENT like", value, "orgParent");
            return (Criteria) this;
        }

        public Criteria andOrgParentNotLike(String value) {
            addCriterion("ORG_PARENT not like", value, "orgParent");
            return (Criteria) this;
        }

        public Criteria andOrgParentIn(List<String> values) {
            addCriterion("ORG_PARENT in", values, "orgParent");
            return (Criteria) this;
        }

        public Criteria andOrgParentNotIn(List<String> values) {
            addCriterion("ORG_PARENT not in", values, "orgParent");
            return (Criteria) this;
        }

        public Criteria andOrgParentBetween(String value1, String value2) {
            addCriterion("ORG_PARENT between", value1, value2, "orgParent");
            return (Criteria) this;
        }

        public Criteria andOrgParentNotBetween(String value1, String value2) {
            addCriterion("ORG_PARENT not between", value1, value2, "orgParent");
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

        public Criteria andPlatIdIsNull() {
            addCriterion("PLAT_ID is null");
            return (Criteria) this;
        }

        public Criteria andPlatIdIsNotNull() {
            addCriterion("PLAT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPlatIdEqualTo(String value) {
            addCriterion("PLAT_ID =", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdNotEqualTo(String value) {
            addCriterion("PLAT_ID <>", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdGreaterThan(String value) {
            addCriterion("PLAT_ID >", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdGreaterThanOrEqualTo(String value) {
            addCriterion("PLAT_ID >=", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdLessThan(String value) {
            addCriterion("PLAT_ID <", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdLessThanOrEqualTo(String value) {
            addCriterion("PLAT_ID <=", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdLike(String value) {
            addCriterion("PLAT_ID like", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdNotLike(String value) {
            addCriterion("PLAT_ID not like", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdIn(List<String> values) {
            addCriterion("PLAT_ID in", values, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdNotIn(List<String> values) {
            addCriterion("PLAT_ID not in", values, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdBetween(String value1, String value2) {
            addCriterion("PLAT_ID between", value1, value2, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdNotBetween(String value1, String value2) {
            addCriterion("PLAT_ID not between", value1, value2, "platId");
            return (Criteria) this;
        }

        public Criteria andBankCardIsNull() {
            addCriterion("BANK_CARD is null");
            return (Criteria) this;
        }

        public Criteria andBankCardIsNotNull() {
            addCriterion("BANK_CARD is not null");
            return (Criteria) this;
        }

        public Criteria andBankCardEqualTo(String value) {
            addCriterion("BANK_CARD =", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotEqualTo(String value) {
            addCriterion("BANK_CARD <>", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardGreaterThan(String value) {
            addCriterion("BANK_CARD >", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_CARD >=", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardLessThan(String value) {
            addCriterion("BANK_CARD <", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardLessThanOrEqualTo(String value) {
            addCriterion("BANK_CARD <=", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardLike(String value) {
            addCriterion("BANK_CARD like", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotLike(String value) {
            addCriterion("BANK_CARD not like", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardIn(List<String> values) {
            addCriterion("BANK_CARD in", values, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotIn(List<String> values) {
            addCriterion("BANK_CARD not in", values, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardBetween(String value1, String value2) {
            addCriterion("BANK_CARD between", value1, value2, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotBetween(String value1, String value2) {
            addCriterion("BANK_CARD not between", value1, value2, "bankCard");
            return (Criteria) this;
        }

        public Criteria andCloRealnameIsNull() {
            addCriterion("CLO_REALNAME is null");
            return (Criteria) this;
        }

        public Criteria andCloRealnameIsNotNull() {
            addCriterion("CLO_REALNAME is not null");
            return (Criteria) this;
        }

        public Criteria andCloRealnameEqualTo(String value) {
            addCriterion("CLO_REALNAME =", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameNotEqualTo(String value) {
            addCriterion("CLO_REALNAME <>", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameGreaterThan(String value) {
            addCriterion("CLO_REALNAME >", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("CLO_REALNAME >=", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameLessThan(String value) {
            addCriterion("CLO_REALNAME <", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameLessThanOrEqualTo(String value) {
            addCriterion("CLO_REALNAME <=", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameLike(String value) {
            addCriterion("CLO_REALNAME like", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameNotLike(String value) {
            addCriterion("CLO_REALNAME not like", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameIn(List<String> values) {
            addCriterion("CLO_REALNAME in", values, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameNotIn(List<String> values) {
            addCriterion("CLO_REALNAME not in", values, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameBetween(String value1, String value2) {
            addCriterion("CLO_REALNAME between", value1, value2, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameNotBetween(String value1, String value2) {
            addCriterion("CLO_REALNAME not between", value1, value2, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloTypeIsNull() {
            addCriterion("CLO_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCloTypeIsNotNull() {
            addCriterion("CLO_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCloTypeEqualTo(BigDecimal value) {
            addCriterion("CLO_TYPE =", value, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeNotEqualTo(BigDecimal value) {
            addCriterion("CLO_TYPE <>", value, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeGreaterThan(BigDecimal value) {
            addCriterion("CLO_TYPE >", value, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_TYPE >=", value, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeLessThan(BigDecimal value) {
            addCriterion("CLO_TYPE <", value, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_TYPE <=", value, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeIn(List<BigDecimal> values) {
            addCriterion("CLO_TYPE in", values, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeNotIn(List<BigDecimal> values) {
            addCriterion("CLO_TYPE not in", values, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_TYPE between", value1, value2, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_TYPE not between", value1, value2, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloBankIsNull() {
            addCriterion("CLO_BANK is null");
            return (Criteria) this;
        }

        public Criteria andCloBankIsNotNull() {
            addCriterion("CLO_BANK is not null");
            return (Criteria) this;
        }

        public Criteria andCloBankEqualTo(String value) {
            addCriterion("CLO_BANK =", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankNotEqualTo(String value) {
            addCriterion("CLO_BANK <>", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankGreaterThan(String value) {
            addCriterion("CLO_BANK >", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankGreaterThanOrEqualTo(String value) {
            addCriterion("CLO_BANK >=", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankLessThan(String value) {
            addCriterion("CLO_BANK <", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankLessThanOrEqualTo(String value) {
            addCriterion("CLO_BANK <=", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankLike(String value) {
            addCriterion("CLO_BANK like", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankNotLike(String value) {
            addCriterion("CLO_BANK not like", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankIn(List<String> values) {
            addCriterion("CLO_BANK in", values, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankNotIn(List<String> values) {
            addCriterion("CLO_BANK not in", values, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankBetween(String value1, String value2) {
            addCriterion("CLO_BANK between", value1, value2, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankNotBetween(String value1, String value2) {
            addCriterion("CLO_BANK not between", value1, value2, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchIsNull() {
            addCriterion("CLO_BANK_BRANCH is null");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchIsNotNull() {
            addCriterion("CLO_BANK_BRANCH is not null");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchEqualTo(String value) {
            addCriterion("CLO_BANK_BRANCH =", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchNotEqualTo(String value) {
            addCriterion("CLO_BANK_BRANCH <>", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchGreaterThan(String value) {
            addCriterion("CLO_BANK_BRANCH >", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchGreaterThanOrEqualTo(String value) {
            addCriterion("CLO_BANK_BRANCH >=", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchLessThan(String value) {
            addCriterion("CLO_BANK_BRANCH <", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchLessThanOrEqualTo(String value) {
            addCriterion("CLO_BANK_BRANCH <=", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchLike(String value) {
            addCriterion("CLO_BANK_BRANCH like", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchNotLike(String value) {
            addCriterion("CLO_BANK_BRANCH not like", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchIn(List<String> values) {
            addCriterion("CLO_BANK_BRANCH in", values, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchNotIn(List<String> values) {
            addCriterion("CLO_BANK_BRANCH not in", values, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchBetween(String value1, String value2) {
            addCriterion("CLO_BANK_BRANCH between", value1, value2, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchNotBetween(String value1, String value2) {
            addCriterion("CLO_BANK_BRANCH not between", value1, value2, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumIsNull() {
            addCriterion("BRANCH_LINE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumIsNotNull() {
            addCriterion("BRANCH_LINE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM =", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM <>", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumGreaterThan(String value) {
            addCriterion("BRANCH_LINE_NUM >", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumGreaterThanOrEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM >=", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumLessThan(String value) {
            addCriterion("BRANCH_LINE_NUM <", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumLessThanOrEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM <=", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumLike(String value) {
            addCriterion("BRANCH_LINE_NUM like", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotLike(String value) {
            addCriterion("BRANCH_LINE_NUM not like", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumIn(List<String> values) {
            addCriterion("BRANCH_LINE_NUM in", values, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotIn(List<String> values) {
            addCriterion("BRANCH_LINE_NUM not in", values, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumBetween(String value1, String value2) {
            addCriterion("BRANCH_LINE_NUM between", value1, value2, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotBetween(String value1, String value2) {
            addCriterion("BRANCH_LINE_NUM not between", value1, value2, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumIsNull() {
            addCriterion("ALL_LINE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andAllLineNumIsNotNull() {
            addCriterion("ALL_LINE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andAllLineNumEqualTo(String value) {
            addCriterion("ALL_LINE_NUM =", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotEqualTo(String value) {
            addCriterion("ALL_LINE_NUM <>", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumGreaterThan(String value) {
            addCriterion("ALL_LINE_NUM >", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumGreaterThanOrEqualTo(String value) {
            addCriterion("ALL_LINE_NUM >=", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumLessThan(String value) {
            addCriterion("ALL_LINE_NUM <", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumLessThanOrEqualTo(String value) {
            addCriterion("ALL_LINE_NUM <=", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumLike(String value) {
            addCriterion("ALL_LINE_NUM like", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotLike(String value) {
            addCriterion("ALL_LINE_NUM not like", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumIn(List<String> values) {
            addCriterion("ALL_LINE_NUM in", values, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotIn(List<String> values) {
            addCriterion("ALL_LINE_NUM not in", values, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumBetween(String value1, String value2) {
            addCriterion("ALL_LINE_NUM between", value1, value2, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotBetween(String value1, String value2) {
            addCriterion("ALL_LINE_NUM not between", value1, value2, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andBankRegionIsNull() {
            addCriterion("BANK_REGION is null");
            return (Criteria) this;
        }

        public Criteria andBankRegionIsNotNull() {
            addCriterion("BANK_REGION is not null");
            return (Criteria) this;
        }

        public Criteria andBankRegionEqualTo(String value) {
            addCriterion("BANK_REGION =", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionNotEqualTo(String value) {
            addCriterion("BANK_REGION <>", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionGreaterThan(String value) {
            addCriterion("BANK_REGION >", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_REGION >=", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionLessThan(String value) {
            addCriterion("BANK_REGION <", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionLessThanOrEqualTo(String value) {
            addCriterion("BANK_REGION <=", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionLike(String value) {
            addCriterion("BANK_REGION like", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionNotLike(String value) {
            addCriterion("BANK_REGION not like", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionIn(List<String> values) {
            addCriterion("BANK_REGION in", values, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionNotIn(List<String> values) {
            addCriterion("BANK_REGION not in", values, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionBetween(String value1, String value2) {
            addCriterion("BANK_REGION between", value1, value2, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionNotBetween(String value1, String value2) {
            addCriterion("BANK_REGION not between", value1, value2, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andCUserIsNull() {
            addCriterion("C_USER is null");
            return (Criteria) this;
        }

        public Criteria andCUserIsNotNull() {
            addCriterion("C_USER is not null");
            return (Criteria) this;
        }

        public Criteria andCUserEqualTo(String value) {
            addCriterion("C_USER =", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotEqualTo(String value) {
            addCriterion("C_USER <>", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserGreaterThan(String value) {
            addCriterion("C_USER >", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserGreaterThanOrEqualTo(String value) {
            addCriterion("C_USER >=", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserLessThan(String value) {
            addCriterion("C_USER <", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserLessThanOrEqualTo(String value) {
            addCriterion("C_USER <=", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserLike(String value) {
            addCriterion("C_USER like", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotLike(String value) {
            addCriterion("C_USER not like", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserIn(List<String> values) {
            addCriterion("C_USER in", values, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotIn(List<String> values) {
            addCriterion("C_USER not in", values, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserBetween(String value1, String value2) {
            addCriterion("C_USER between", value1, value2, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotBetween(String value1, String value2) {
            addCriterion("C_USER not between", value1, value2, "cUser");
            return (Criteria) this;
        }

        public Criteria andUUserIsNull() {
            addCriterion("U_USER is null");
            return (Criteria) this;
        }

        public Criteria andUUserIsNotNull() {
            addCriterion("U_USER is not null");
            return (Criteria) this;
        }

        public Criteria andUUserEqualTo(String value) {
            addCriterion("U_USER =", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotEqualTo(String value) {
            addCriterion("U_USER <>", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserGreaterThan(String value) {
            addCriterion("U_USER >", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserGreaterThanOrEqualTo(String value) {
            addCriterion("U_USER >=", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserLessThan(String value) {
            addCriterion("U_USER <", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserLessThanOrEqualTo(String value) {
            addCriterion("U_USER <=", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserLike(String value) {
            addCriterion("U_USER like", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotLike(String value) {
            addCriterion("U_USER not like", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserIn(List<String> values) {
            addCriterion("U_USER in", values, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotIn(List<String> values) {
            addCriterion("U_USER not in", values, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserBetween(String value1, String value2) {
            addCriterion("U_USER between", value1, value2, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotBetween(String value1, String value2) {
            addCriterion("U_USER not between", value1, value2, "uUser");
            return (Criteria) this;
        }

        public Criteria andCTimeIsNull() {
            addCriterion("C_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCTimeIsNotNull() {
            addCriterion("C_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCTimeEqualTo(Date value) {
            addCriterion("C_TIME =", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotEqualTo(Date value) {
            addCriterion("C_TIME <>", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeGreaterThan(Date value) {
            addCriterion("C_TIME >", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("C_TIME >=", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeLessThan(Date value) {
            addCriterion("C_TIME <", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeLessThanOrEqualTo(Date value) {
            addCriterion("C_TIME <=", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeIn(List<Date> values) {
            addCriterion("C_TIME in", values, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotIn(List<Date> values) {
            addCriterion("C_TIME not in", values, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeBetween(Date value1, Date value2) {
            addCriterion("C_TIME between", value1, value2, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotBetween(Date value1, Date value2) {
            addCriterion("C_TIME not between", value1, value2, "cTime");
            return (Criteria) this;
        }

        public Criteria andUTimeIsNull() {
            addCriterion("U_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUTimeIsNotNull() {
            addCriterion("U_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUTimeEqualTo(Date value) {
            addCriterion("U_TIME =", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeNotEqualTo(Date value) {
            addCriterion("U_TIME <>", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeGreaterThan(Date value) {
            addCriterion("U_TIME >", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("U_TIME >=", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeLessThan(Date value) {
            addCriterion("U_TIME <", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeLessThanOrEqualTo(Date value) {
            addCriterion("U_TIME <=", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeIn(List<Date> values) {
            addCriterion("U_TIME in", values, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeNotIn(List<Date> values) {
            addCriterion("U_TIME not in", values, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeBetween(Date value1, Date value2) {
            addCriterion("U_TIME between", value1, value2, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeNotBetween(Date value1, Date value2) {
            addCriterion("U_TIME not between", value1, value2, "uTime");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("RESULT is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("RESULT is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(BigDecimal value) {
            addCriterion("RESULT =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(BigDecimal value) {
            addCriterion("RESULT <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(BigDecimal value) {
            addCriterion("RESULT >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RESULT >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(BigDecimal value) {
            addCriterion("RESULT <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RESULT <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<BigDecimal> values) {
            addCriterion("RESULT in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<BigDecimal> values) {
            addCriterion("RESULT not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RESULT between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RESULT not between", value1, value2, "result");
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