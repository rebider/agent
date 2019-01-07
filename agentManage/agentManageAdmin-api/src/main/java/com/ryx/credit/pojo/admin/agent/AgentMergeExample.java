package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentMergeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AgentMergeExample() {
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

        public Criteria andMainAgentIdIsNull() {
            addCriterion("MAIN_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdIsNotNull() {
            addCriterion("MAIN_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdEqualTo(String value) {
            addCriterion("MAIN_AGENT_ID =", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdNotEqualTo(String value) {
            addCriterion("MAIN_AGENT_ID <>", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdGreaterThan(String value) {
            addCriterion("MAIN_AGENT_ID >", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("MAIN_AGENT_ID >=", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdLessThan(String value) {
            addCriterion("MAIN_AGENT_ID <", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdLessThanOrEqualTo(String value) {
            addCriterion("MAIN_AGENT_ID <=", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdLike(String value) {
            addCriterion("MAIN_AGENT_ID like", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdNotLike(String value) {
            addCriterion("MAIN_AGENT_ID not like", value, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdIn(List<String> values) {
            addCriterion("MAIN_AGENT_ID in", values, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdNotIn(List<String> values) {
            addCriterion("MAIN_AGENT_ID not in", values, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdBetween(String value1, String value2) {
            addCriterion("MAIN_AGENT_ID between", value1, value2, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentIdNotBetween(String value1, String value2) {
            addCriterion("MAIN_AGENT_ID not between", value1, value2, "mainAgentId");
            return (Criteria) this;
        }

        public Criteria andMainAgentNameIsNull() {
            addCriterion("MAIN_AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andMainAgentNameIsNotNull() {
            addCriterion("MAIN_AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andMainAgentNameEqualTo(String value) {
            addCriterion("MAIN_AGENT_NAME =", value, "mainAgentName");
            return (Criteria) this;
        }

        public Criteria andMainAgentNameNotEqualTo(String value) {
            addCriterion("MAIN_AGENT_NAME <>", value, "mainAgentName");
            return (Criteria) this;
        }

        public Criteria andMainAgentNameGreaterThan(String value) {
            addCriterion("MAIN_AGENT_NAME >", value, "mainAgentName");
            return (Criteria) this;
        }

        public Criteria andMainAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("MAIN_AGENT_NAME >=", value, "mainAgentName");
            return (Criteria) this;
        }

        public Criteria andMainAgentNameLessThan(String value) {
            addCriterion("MAIN_AGENT_NAME <", value, "mainAgentName");
            return (Criteria) this;
        }

        public Criteria andMainAgentNameLessThanOrEqualTo(String value) {
            addCriterion("MAIN_AGENT_NAME <=", value, "mainAgentName");
            return (Criteria) this;
        }

        public Criteria andMainAgentNameLike(String value) {
            addCriterion("MAIN_AGENT_NAME like", value, "mainAgentName");
            return (Criteria) this;
        }

        public Criteria andMainAgentNameNotLike(String value) {
            addCriterion("MAIN_AGENT_NAME not like", value, "mainAgentName");
            return (Criteria) this;
        }

        public Criteria andMainAgentNameIn(List<String> values) {
            addCriterion("MAIN_AGENT_NAME in", values, "mainAgentName");
            return (Criteria) this;
        }

        public Criteria andMainAgentNameNotIn(List<String> values) {
            addCriterion("MAIN_AGENT_NAME not in", values, "mainAgentName");
            return (Criteria) this;
        }

        public Criteria andMainAgentNameBetween(String value1, String value2) {
            addCriterion("MAIN_AGENT_NAME between", value1, value2, "mainAgentName");
            return (Criteria) this;
        }

        public Criteria andMainAgentNameNotBetween(String value1, String value2) {
            addCriterion("MAIN_AGENT_NAME not between", value1, value2, "mainAgentName");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdIsNull() {
            addCriterion("SUB_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdIsNotNull() {
            addCriterion("SUB_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdEqualTo(String value) {
            addCriterion("SUB_AGENT_ID =", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdNotEqualTo(String value) {
            addCriterion("SUB_AGENT_ID <>", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdGreaterThan(String value) {
            addCriterion("SUB_AGENT_ID >", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUB_AGENT_ID >=", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdLessThan(String value) {
            addCriterion("SUB_AGENT_ID <", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdLessThanOrEqualTo(String value) {
            addCriterion("SUB_AGENT_ID <=", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdLike(String value) {
            addCriterion("SUB_AGENT_ID like", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdNotLike(String value) {
            addCriterion("SUB_AGENT_ID not like", value, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdIn(List<String> values) {
            addCriterion("SUB_AGENT_ID in", values, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdNotIn(List<String> values) {
            addCriterion("SUB_AGENT_ID not in", values, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdBetween(String value1, String value2) {
            addCriterion("SUB_AGENT_ID between", value1, value2, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentIdNotBetween(String value1, String value2) {
            addCriterion("SUB_AGENT_ID not between", value1, value2, "subAgentId");
            return (Criteria) this;
        }

        public Criteria andSubAgentNameIsNull() {
            addCriterion("SUB_AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSubAgentNameIsNotNull() {
            addCriterion("SUB_AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSubAgentNameEqualTo(String value) {
            addCriterion("SUB_AGENT_NAME =", value, "subAgentName");
            return (Criteria) this;
        }

        public Criteria andSubAgentNameNotEqualTo(String value) {
            addCriterion("SUB_AGENT_NAME <>", value, "subAgentName");
            return (Criteria) this;
        }

        public Criteria andSubAgentNameGreaterThan(String value) {
            addCriterion("SUB_AGENT_NAME >", value, "subAgentName");
            return (Criteria) this;
        }

        public Criteria andSubAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("SUB_AGENT_NAME >=", value, "subAgentName");
            return (Criteria) this;
        }

        public Criteria andSubAgentNameLessThan(String value) {
            addCriterion("SUB_AGENT_NAME <", value, "subAgentName");
            return (Criteria) this;
        }

        public Criteria andSubAgentNameLessThanOrEqualTo(String value) {
            addCriterion("SUB_AGENT_NAME <=", value, "subAgentName");
            return (Criteria) this;
        }

        public Criteria andSubAgentNameLike(String value) {
            addCriterion("SUB_AGENT_NAME like", value, "subAgentName");
            return (Criteria) this;
        }

        public Criteria andSubAgentNameNotLike(String value) {
            addCriterion("SUB_AGENT_NAME not like", value, "subAgentName");
            return (Criteria) this;
        }

        public Criteria andSubAgentNameIn(List<String> values) {
            addCriterion("SUB_AGENT_NAME in", values, "subAgentName");
            return (Criteria) this;
        }

        public Criteria andSubAgentNameNotIn(List<String> values) {
            addCriterion("SUB_AGENT_NAME not in", values, "subAgentName");
            return (Criteria) this;
        }

        public Criteria andSubAgentNameBetween(String value1, String value2) {
            addCriterion("SUB_AGENT_NAME between", value1, value2, "subAgentName");
            return (Criteria) this;
        }

        public Criteria andSubAgentNameNotBetween(String value1, String value2) {
            addCriterion("SUB_AGENT_NAME not between", value1, value2, "subAgentName");
            return (Criteria) this;
        }

        public Criteria andSubAgentDebtIsNull() {
            addCriterion("SUB_AGENT_DEBT is null");
            return (Criteria) this;
        }

        public Criteria andSubAgentDebtIsNotNull() {
            addCriterion("SUB_AGENT_DEBT is not null");
            return (Criteria) this;
        }

        public Criteria andSubAgentDebtEqualTo(BigDecimal value) {
            addCriterion("SUB_AGENT_DEBT =", value, "subAgentDebt");
            return (Criteria) this;
        }

        public Criteria andSubAgentDebtNotEqualTo(BigDecimal value) {
            addCriterion("SUB_AGENT_DEBT <>", value, "subAgentDebt");
            return (Criteria) this;
        }

        public Criteria andSubAgentDebtGreaterThan(BigDecimal value) {
            addCriterion("SUB_AGENT_DEBT >", value, "subAgentDebt");
            return (Criteria) this;
        }

        public Criteria andSubAgentDebtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUB_AGENT_DEBT >=", value, "subAgentDebt");
            return (Criteria) this;
        }

        public Criteria andSubAgentDebtLessThan(BigDecimal value) {
            addCriterion("SUB_AGENT_DEBT <", value, "subAgentDebt");
            return (Criteria) this;
        }

        public Criteria andSubAgentDebtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUB_AGENT_DEBT <=", value, "subAgentDebt");
            return (Criteria) this;
        }

        public Criteria andSubAgentDebtIn(List<BigDecimal> values) {
            addCriterion("SUB_AGENT_DEBT in", values, "subAgentDebt");
            return (Criteria) this;
        }

        public Criteria andSubAgentDebtNotIn(List<BigDecimal> values) {
            addCriterion("SUB_AGENT_DEBT not in", values, "subAgentDebt");
            return (Criteria) this;
        }

        public Criteria andSubAgentDebtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUB_AGENT_DEBT between", value1, value2, "subAgentDebt");
            return (Criteria) this;
        }

        public Criteria andSubAgentDebtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUB_AGENT_DEBT not between", value1, value2, "subAgentDebt");
            return (Criteria) this;
        }

        public Criteria andSubAgentOweTicketIsNull() {
            addCriterion("SUB_AGENT_OWE_TICKET is null");
            return (Criteria) this;
        }

        public Criteria andSubAgentOweTicketIsNotNull() {
            addCriterion("SUB_AGENT_OWE_TICKET is not null");
            return (Criteria) this;
        }

        public Criteria andSubAgentOweTicketEqualTo(BigDecimal value) {
            addCriterion("SUB_AGENT_OWE_TICKET =", value, "subAgentOweTicket");
            return (Criteria) this;
        }

        public Criteria andSubAgentOweTicketNotEqualTo(BigDecimal value) {
            addCriterion("SUB_AGENT_OWE_TICKET <>", value, "subAgentOweTicket");
            return (Criteria) this;
        }

        public Criteria andSubAgentOweTicketGreaterThan(BigDecimal value) {
            addCriterion("SUB_AGENT_OWE_TICKET >", value, "subAgentOweTicket");
            return (Criteria) this;
        }

        public Criteria andSubAgentOweTicketGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SUB_AGENT_OWE_TICKET >=", value, "subAgentOweTicket");
            return (Criteria) this;
        }

        public Criteria andSubAgentOweTicketLessThan(BigDecimal value) {
            addCriterion("SUB_AGENT_OWE_TICKET <", value, "subAgentOweTicket");
            return (Criteria) this;
        }

        public Criteria andSubAgentOweTicketLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SUB_AGENT_OWE_TICKET <=", value, "subAgentOweTicket");
            return (Criteria) this;
        }

        public Criteria andSubAgentOweTicketIn(List<BigDecimal> values) {
            addCriterion("SUB_AGENT_OWE_TICKET in", values, "subAgentOweTicket");
            return (Criteria) this;
        }

        public Criteria andSubAgentOweTicketNotIn(List<BigDecimal> values) {
            addCriterion("SUB_AGENT_OWE_TICKET not in", values, "subAgentOweTicket");
            return (Criteria) this;
        }

        public Criteria andSubAgentOweTicketBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUB_AGENT_OWE_TICKET between", value1, value2, "subAgentOweTicket");
            return (Criteria) this;
        }

        public Criteria andSubAgentOweTicketNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SUB_AGENT_OWE_TICKET not between", value1, value2, "subAgentOweTicket");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusIsNull() {
            addCriterion("CLO_REVIEW_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusIsNotNull() {
            addCriterion("CLO_REVIEW_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusEqualTo(String value) {
            addCriterion("CLO_REVIEW_STATUS =", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotEqualTo(String value) {
            addCriterion("CLO_REVIEW_STATUS <>", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusGreaterThan(String value) {
            addCriterion("CLO_REVIEW_STATUS >", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusGreaterThanOrEqualTo(String value) {
            addCriterion("CLO_REVIEW_STATUS >=", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusLessThan(String value) {
            addCriterion("CLO_REVIEW_STATUS <", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusLessThanOrEqualTo(String value) {
            addCriterion("CLO_REVIEW_STATUS <=", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusLike(String value) {
            addCriterion("CLO_REVIEW_STATUS like", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotLike(String value) {
            addCriterion("CLO_REVIEW_STATUS not like", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusIn(List<String> values) {
            addCriterion("CLO_REVIEW_STATUS in", values, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotIn(List<String> values) {
            addCriterion("CLO_REVIEW_STATUS not in", values, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusBetween(String value1, String value2) {
            addCriterion("CLO_REVIEW_STATUS between", value1, value2, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotBetween(String value1, String value2) {
            addCriterion("CLO_REVIEW_STATUS not between", value1, value2, "cloReviewStatus");
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