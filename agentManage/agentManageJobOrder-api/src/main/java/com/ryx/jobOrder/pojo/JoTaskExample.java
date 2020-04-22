package com.ryx.jobOrder.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JoTaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public JoTaskExample() {
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

        public Criteria andJoIdIsNull() {
            addCriterion("JO_ID is null");
            return (Criteria) this;
        }

        public Criteria andJoIdIsNotNull() {
            addCriterion("JO_ID is not null");
            return (Criteria) this;
        }

        public Criteria andJoIdEqualTo(String value) {
            addCriterion("JO_ID =", value, "joId");
            return (Criteria) this;
        }

        public Criteria andJoIdNotEqualTo(String value) {
            addCriterion("JO_ID <>", value, "joId");
            return (Criteria) this;
        }

        public Criteria andJoIdGreaterThan(String value) {
            addCriterion("JO_ID >", value, "joId");
            return (Criteria) this;
        }

        public Criteria andJoIdGreaterThanOrEqualTo(String value) {
            addCriterion("JO_ID >=", value, "joId");
            return (Criteria) this;
        }

        public Criteria andJoIdLessThan(String value) {
            addCriterion("JO_ID <", value, "joId");
            return (Criteria) this;
        }

        public Criteria andJoIdLessThanOrEqualTo(String value) {
            addCriterion("JO_ID <=", value, "joId");
            return (Criteria) this;
        }

        public Criteria andJoIdLike(String value) {
            addCriterion("JO_ID like", value, "joId");
            return (Criteria) this;
        }

        public Criteria andJoIdNotLike(String value) {
            addCriterion("JO_ID not like", value, "joId");
            return (Criteria) this;
        }

        public Criteria andJoIdIn(List<String> values) {
            addCriterion("JO_ID in", values, "joId");
            return (Criteria) this;
        }

        public Criteria andJoIdNotIn(List<String> values) {
            addCriterion("JO_ID not in", values, "joId");
            return (Criteria) this;
        }

        public Criteria andJoIdBetween(String value1, String value2) {
            addCriterion("JO_ID between", value1, value2, "joId");
            return (Criteria) this;
        }

        public Criteria andJoIdNotBetween(String value1, String value2) {
            addCriterion("JO_ID not between", value1, value2, "joId");
            return (Criteria) this;
        }

        public Criteria andJoTaskStatusIsNull() {
            addCriterion("JO_TASK_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andJoTaskStatusIsNotNull() {
            addCriterion("JO_TASK_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andJoTaskStatusEqualTo(String value) {
            addCriterion("JO_TASK_STATUS =", value, "joTaskStatus");
            return (Criteria) this;
        }

        public Criteria andJoTaskStatusNotEqualTo(String value) {
            addCriterion("JO_TASK_STATUS <>", value, "joTaskStatus");
            return (Criteria) this;
        }

        public Criteria andJoTaskStatusGreaterThan(String value) {
            addCriterion("JO_TASK_STATUS >", value, "joTaskStatus");
            return (Criteria) this;
        }

        public Criteria andJoTaskStatusGreaterThanOrEqualTo(String value) {
            addCriterion("JO_TASK_STATUS >=", value, "joTaskStatus");
            return (Criteria) this;
        }

        public Criteria andJoTaskStatusLessThan(String value) {
            addCriterion("JO_TASK_STATUS <", value, "joTaskStatus");
            return (Criteria) this;
        }

        public Criteria andJoTaskStatusLessThanOrEqualTo(String value) {
            addCriterion("JO_TASK_STATUS <=", value, "joTaskStatus");
            return (Criteria) this;
        }

        public Criteria andJoTaskStatusLike(String value) {
            addCriterion("JO_TASK_STATUS like", value, "joTaskStatus");
            return (Criteria) this;
        }

        public Criteria andJoTaskStatusNotLike(String value) {
            addCriterion("JO_TASK_STATUS not like", value, "joTaskStatus");
            return (Criteria) this;
        }

        public Criteria andJoTaskStatusIn(List<String> values) {
            addCriterion("JO_TASK_STATUS in", values, "joTaskStatus");
            return (Criteria) this;
        }

        public Criteria andJoTaskStatusNotIn(List<String> values) {
            addCriterion("JO_TASK_STATUS not in", values, "joTaskStatus");
            return (Criteria) this;
        }

        public Criteria andJoTaskStatusBetween(String value1, String value2) {
            addCriterion("JO_TASK_STATUS between", value1, value2, "joTaskStatus");
            return (Criteria) this;
        }

        public Criteria andJoTaskStatusNotBetween(String value1, String value2) {
            addCriterion("JO_TASK_STATUS not between", value1, value2, "joTaskStatus");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeIsNull() {
            addCriterion("JO_TASK_TIME is null");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeIsNotNull() {
            addCriterion("JO_TASK_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeEqualTo(Date value) {
            addCriterion("JO_TASK_TIME =", value, "joTaskTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeNotEqualTo(Date value) {
            addCriterion("JO_TASK_TIME <>", value, "joTaskTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeGreaterThan(Date value) {
            addCriterion("JO_TASK_TIME >", value, "joTaskTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("JO_TASK_TIME >=", value, "joTaskTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeLessThan(Date value) {
            addCriterion("JO_TASK_TIME <", value, "joTaskTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeLessThanOrEqualTo(Date value) {
            addCriterion("JO_TASK_TIME <=", value, "joTaskTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeIn(List<Date> values) {
            addCriterion("JO_TASK_TIME in", values, "joTaskTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeNotIn(List<Date> values) {
            addCriterion("JO_TASK_TIME not in", values, "joTaskTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeBetween(Date value1, Date value2) {
            addCriterion("JO_TASK_TIME between", value1, value2, "joTaskTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeNotBetween(Date value1, Date value2) {
            addCriterion("JO_TASK_TIME not between", value1, value2, "joTaskTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskAcceptTimeIsNull() {
            addCriterion("JO_TASK_ACCEPT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andJoTaskAcceptTimeIsNotNull() {
            addCriterion("JO_TASK_ACCEPT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andJoTaskAcceptTimeEqualTo(Date value) {
            addCriterion("JO_TASK_ACCEPT_TIME =", value, "joTaskAcceptTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskAcceptTimeNotEqualTo(Date value) {
            addCriterion("JO_TASK_ACCEPT_TIME <>", value, "joTaskAcceptTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskAcceptTimeGreaterThan(Date value) {
            addCriterion("JO_TASK_ACCEPT_TIME >", value, "joTaskAcceptTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskAcceptTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("JO_TASK_ACCEPT_TIME >=", value, "joTaskAcceptTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskAcceptTimeLessThan(Date value) {
            addCriterion("JO_TASK_ACCEPT_TIME <", value, "joTaskAcceptTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskAcceptTimeLessThanOrEqualTo(Date value) {
            addCriterion("JO_TASK_ACCEPT_TIME <=", value, "joTaskAcceptTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskAcceptTimeIn(List<Date> values) {
            addCriterion("JO_TASK_ACCEPT_TIME in", values, "joTaskAcceptTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskAcceptTimeNotIn(List<Date> values) {
            addCriterion("JO_TASK_ACCEPT_TIME not in", values, "joTaskAcceptTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskAcceptTimeBetween(Date value1, Date value2) {
            addCriterion("JO_TASK_ACCEPT_TIME between", value1, value2, "joTaskAcceptTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskAcceptTimeNotBetween(Date value1, Date value2) {
            addCriterion("JO_TASK_ACCEPT_TIME not between", value1, value2, "joTaskAcceptTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskNextTimeIsNull() {
            addCriterion("JO_TASK_NEXT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andJoTaskNextTimeIsNotNull() {
            addCriterion("JO_TASK_NEXT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andJoTaskNextTimeEqualTo(Date value) {
            addCriterion("JO_TASK_NEXT_TIME =", value, "joTaskNextTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskNextTimeNotEqualTo(Date value) {
            addCriterion("JO_TASK_NEXT_TIME <>", value, "joTaskNextTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskNextTimeGreaterThan(Date value) {
            addCriterion("JO_TASK_NEXT_TIME >", value, "joTaskNextTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskNextTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("JO_TASK_NEXT_TIME >=", value, "joTaskNextTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskNextTimeLessThan(Date value) {
            addCriterion("JO_TASK_NEXT_TIME <", value, "joTaskNextTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskNextTimeLessThanOrEqualTo(Date value) {
            addCriterion("JO_TASK_NEXT_TIME <=", value, "joTaskNextTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskNextTimeIn(List<Date> values) {
            addCriterion("JO_TASK_NEXT_TIME in", values, "joTaskNextTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskNextTimeNotIn(List<Date> values) {
            addCriterion("JO_TASK_NEXT_TIME not in", values, "joTaskNextTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskNextTimeBetween(Date value1, Date value2) {
            addCriterion("JO_TASK_NEXT_TIME between", value1, value2, "joTaskNextTime");
            return (Criteria) this;
        }

        public Criteria andJoTaskNextTimeNotBetween(Date value1, Date value2) {
            addCriterion("JO_TASK_NEXT_TIME not between", value1, value2, "joTaskNextTime");
            return (Criteria) this;
        }

        public Criteria andDealGroupIdIsNull() {
            addCriterion("DEAL_GROUP_ID is null");
            return (Criteria) this;
        }

        public Criteria andDealGroupIdIsNotNull() {
            addCriterion("DEAL_GROUP_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDealGroupIdEqualTo(String value) {
            addCriterion("DEAL_GROUP_ID =", value, "dealGroupId");
            return (Criteria) this;
        }

        public Criteria andDealGroupIdNotEqualTo(String value) {
            addCriterion("DEAL_GROUP_ID <>", value, "dealGroupId");
            return (Criteria) this;
        }

        public Criteria andDealGroupIdGreaterThan(String value) {
            addCriterion("DEAL_GROUP_ID >", value, "dealGroupId");
            return (Criteria) this;
        }

        public Criteria andDealGroupIdGreaterThanOrEqualTo(String value) {
            addCriterion("DEAL_GROUP_ID >=", value, "dealGroupId");
            return (Criteria) this;
        }

        public Criteria andDealGroupIdLessThan(String value) {
            addCriterion("DEAL_GROUP_ID <", value, "dealGroupId");
            return (Criteria) this;
        }

        public Criteria andDealGroupIdLessThanOrEqualTo(String value) {
            addCriterion("DEAL_GROUP_ID <=", value, "dealGroupId");
            return (Criteria) this;
        }

        public Criteria andDealGroupIdLike(String value) {
            addCriterion("DEAL_GROUP_ID like", value, "dealGroupId");
            return (Criteria) this;
        }

        public Criteria andDealGroupIdNotLike(String value) {
            addCriterion("DEAL_GROUP_ID not like", value, "dealGroupId");
            return (Criteria) this;
        }

        public Criteria andDealGroupIdIn(List<String> values) {
            addCriterion("DEAL_GROUP_ID in", values, "dealGroupId");
            return (Criteria) this;
        }

        public Criteria andDealGroupIdNotIn(List<String> values) {
            addCriterion("DEAL_GROUP_ID not in", values, "dealGroupId");
            return (Criteria) this;
        }

        public Criteria andDealGroupIdBetween(String value1, String value2) {
            addCriterion("DEAL_GROUP_ID between", value1, value2, "dealGroupId");
            return (Criteria) this;
        }

        public Criteria andDealGroupIdNotBetween(String value1, String value2) {
            addCriterion("DEAL_GROUP_ID not between", value1, value2, "dealGroupId");
            return (Criteria) this;
        }

        public Criteria andDealGroupIsNull() {
            addCriterion("DEAL_GROUP is null");
            return (Criteria) this;
        }

        public Criteria andDealGroupIsNotNull() {
            addCriterion("DEAL_GROUP is not null");
            return (Criteria) this;
        }

        public Criteria andDealGroupEqualTo(String value) {
            addCriterion("DEAL_GROUP =", value, "dealGroup");
            return (Criteria) this;
        }

        public Criteria andDealGroupNotEqualTo(String value) {
            addCriterion("DEAL_GROUP <>", value, "dealGroup");
            return (Criteria) this;
        }

        public Criteria andDealGroupGreaterThan(String value) {
            addCriterion("DEAL_GROUP >", value, "dealGroup");
            return (Criteria) this;
        }

        public Criteria andDealGroupGreaterThanOrEqualTo(String value) {
            addCriterion("DEAL_GROUP >=", value, "dealGroup");
            return (Criteria) this;
        }

        public Criteria andDealGroupLessThan(String value) {
            addCriterion("DEAL_GROUP <", value, "dealGroup");
            return (Criteria) this;
        }

        public Criteria andDealGroupLessThanOrEqualTo(String value) {
            addCriterion("DEAL_GROUP <=", value, "dealGroup");
            return (Criteria) this;
        }

        public Criteria andDealGroupLike(String value) {
            addCriterion("DEAL_GROUP like", value, "dealGroup");
            return (Criteria) this;
        }

        public Criteria andDealGroupNotLike(String value) {
            addCriterion("DEAL_GROUP not like", value, "dealGroup");
            return (Criteria) this;
        }

        public Criteria andDealGroupIn(List<String> values) {
            addCriterion("DEAL_GROUP in", values, "dealGroup");
            return (Criteria) this;
        }

        public Criteria andDealGroupNotIn(List<String> values) {
            addCriterion("DEAL_GROUP not in", values, "dealGroup");
            return (Criteria) this;
        }

        public Criteria andDealGroupBetween(String value1, String value2) {
            addCriterion("DEAL_GROUP between", value1, value2, "dealGroup");
            return (Criteria) this;
        }

        public Criteria andDealGroupNotBetween(String value1, String value2) {
            addCriterion("DEAL_GROUP not between", value1, value2, "dealGroup");
            return (Criteria) this;
        }

        public Criteria andDealPersonIdIsNull() {
            addCriterion("DEAL_PERSON_ID is null");
            return (Criteria) this;
        }

        public Criteria andDealPersonIdIsNotNull() {
            addCriterion("DEAL_PERSON_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDealPersonIdEqualTo(String value) {
            addCriterion("DEAL_PERSON_ID =", value, "dealPersonId");
            return (Criteria) this;
        }

        public Criteria andDealPersonIdNotEqualTo(String value) {
            addCriterion("DEAL_PERSON_ID <>", value, "dealPersonId");
            return (Criteria) this;
        }

        public Criteria andDealPersonIdGreaterThan(String value) {
            addCriterion("DEAL_PERSON_ID >", value, "dealPersonId");
            return (Criteria) this;
        }

        public Criteria andDealPersonIdGreaterThanOrEqualTo(String value) {
            addCriterion("DEAL_PERSON_ID >=", value, "dealPersonId");
            return (Criteria) this;
        }

        public Criteria andDealPersonIdLessThan(String value) {
            addCriterion("DEAL_PERSON_ID <", value, "dealPersonId");
            return (Criteria) this;
        }

        public Criteria andDealPersonIdLessThanOrEqualTo(String value) {
            addCriterion("DEAL_PERSON_ID <=", value, "dealPersonId");
            return (Criteria) this;
        }

        public Criteria andDealPersonIdLike(String value) {
            addCriterion("DEAL_PERSON_ID like", value, "dealPersonId");
            return (Criteria) this;
        }

        public Criteria andDealPersonIdNotLike(String value) {
            addCriterion("DEAL_PERSON_ID not like", value, "dealPersonId");
            return (Criteria) this;
        }

        public Criteria andDealPersonIdIn(List<String> values) {
            addCriterion("DEAL_PERSON_ID in", values, "dealPersonId");
            return (Criteria) this;
        }

        public Criteria andDealPersonIdNotIn(List<String> values) {
            addCriterion("DEAL_PERSON_ID not in", values, "dealPersonId");
            return (Criteria) this;
        }

        public Criteria andDealPersonIdBetween(String value1, String value2) {
            addCriterion("DEAL_PERSON_ID between", value1, value2, "dealPersonId");
            return (Criteria) this;
        }

        public Criteria andDealPersonIdNotBetween(String value1, String value2) {
            addCriterion("DEAL_PERSON_ID not between", value1, value2, "dealPersonId");
            return (Criteria) this;
        }

        public Criteria andDealPersonNameIsNull() {
            addCriterion("DEAL_PERSON_NAME is null");
            return (Criteria) this;
        }

        public Criteria andDealPersonNameIsNotNull() {
            addCriterion("DEAL_PERSON_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andDealPersonNameEqualTo(String value) {
            addCriterion("DEAL_PERSON_NAME =", value, "dealPersonName");
            return (Criteria) this;
        }

        public Criteria andDealPersonNameNotEqualTo(String value) {
            addCriterion("DEAL_PERSON_NAME <>", value, "dealPersonName");
            return (Criteria) this;
        }

        public Criteria andDealPersonNameGreaterThan(String value) {
            addCriterion("DEAL_PERSON_NAME >", value, "dealPersonName");
            return (Criteria) this;
        }

        public Criteria andDealPersonNameGreaterThanOrEqualTo(String value) {
            addCriterion("DEAL_PERSON_NAME >=", value, "dealPersonName");
            return (Criteria) this;
        }

        public Criteria andDealPersonNameLessThan(String value) {
            addCriterion("DEAL_PERSON_NAME <", value, "dealPersonName");
            return (Criteria) this;
        }

        public Criteria andDealPersonNameLessThanOrEqualTo(String value) {
            addCriterion("DEAL_PERSON_NAME <=", value, "dealPersonName");
            return (Criteria) this;
        }

        public Criteria andDealPersonNameLike(String value) {
            addCriterion("DEAL_PERSON_NAME like", value, "dealPersonName");
            return (Criteria) this;
        }

        public Criteria andDealPersonNameNotLike(String value) {
            addCriterion("DEAL_PERSON_NAME not like", value, "dealPersonName");
            return (Criteria) this;
        }

        public Criteria andDealPersonNameIn(List<String> values) {
            addCriterion("DEAL_PERSON_NAME in", values, "dealPersonName");
            return (Criteria) this;
        }

        public Criteria andDealPersonNameNotIn(List<String> values) {
            addCriterion("DEAL_PERSON_NAME not in", values, "dealPersonName");
            return (Criteria) this;
        }

        public Criteria andDealPersonNameBetween(String value1, String value2) {
            addCriterion("DEAL_PERSON_NAME between", value1, value2, "dealPersonName");
            return (Criteria) this;
        }

        public Criteria andDealPersonNameNotBetween(String value1, String value2) {
            addCriterion("DEAL_PERSON_NAME not between", value1, value2, "dealPersonName");
            return (Criteria) this;
        }

        public Criteria andSecondDealGroupIsNull() {
            addCriterion("SECOND_DEAL_GROUP is null");
            return (Criteria) this;
        }

        public Criteria andSecondDealGroupIsNotNull() {
            addCriterion("SECOND_DEAL_GROUP is not null");
            return (Criteria) this;
        }

        public Criteria andSecondDealGroupEqualTo(String value) {
            addCriterion("SECOND_DEAL_GROUP =", value, "secondDealGroup");
            return (Criteria) this;
        }

        public Criteria andSecondDealGroupNotEqualTo(String value) {
            addCriterion("SECOND_DEAL_GROUP <>", value, "secondDealGroup");
            return (Criteria) this;
        }

        public Criteria andSecondDealGroupGreaterThan(String value) {
            addCriterion("SECOND_DEAL_GROUP >", value, "secondDealGroup");
            return (Criteria) this;
        }

        public Criteria andSecondDealGroupGreaterThanOrEqualTo(String value) {
            addCriterion("SECOND_DEAL_GROUP >=", value, "secondDealGroup");
            return (Criteria) this;
        }

        public Criteria andSecondDealGroupLessThan(String value) {
            addCriterion("SECOND_DEAL_GROUP <", value, "secondDealGroup");
            return (Criteria) this;
        }

        public Criteria andSecondDealGroupLessThanOrEqualTo(String value) {
            addCriterion("SECOND_DEAL_GROUP <=", value, "secondDealGroup");
            return (Criteria) this;
        }

        public Criteria andSecondDealGroupLike(String value) {
            addCriterion("SECOND_DEAL_GROUP like", value, "secondDealGroup");
            return (Criteria) this;
        }

        public Criteria andSecondDealGroupNotLike(String value) {
            addCriterion("SECOND_DEAL_GROUP not like", value, "secondDealGroup");
            return (Criteria) this;
        }

        public Criteria andSecondDealGroupIn(List<String> values) {
            addCriterion("SECOND_DEAL_GROUP in", values, "secondDealGroup");
            return (Criteria) this;
        }

        public Criteria andSecondDealGroupNotIn(List<String> values) {
            addCriterion("SECOND_DEAL_GROUP not in", values, "secondDealGroup");
            return (Criteria) this;
        }

        public Criteria andSecondDealGroupBetween(String value1, String value2) {
            addCriterion("SECOND_DEAL_GROUP between", value1, value2, "secondDealGroup");
            return (Criteria) this;
        }

        public Criteria andSecondDealGroupNotBetween(String value1, String value2) {
            addCriterion("SECOND_DEAL_GROUP not between", value1, value2, "secondDealGroup");
            return (Criteria) this;
        }

        public Criteria andBackDealGroupIsNull() {
            addCriterion("BACK_DEAL_GROUP is null");
            return (Criteria) this;
        }

        public Criteria andBackDealGroupIsNotNull() {
            addCriterion("BACK_DEAL_GROUP is not null");
            return (Criteria) this;
        }

        public Criteria andBackDealGroupEqualTo(String value) {
            addCriterion("BACK_DEAL_GROUP =", value, "backDealGroup");
            return (Criteria) this;
        }

        public Criteria andBackDealGroupNotEqualTo(String value) {
            addCriterion("BACK_DEAL_GROUP <>", value, "backDealGroup");
            return (Criteria) this;
        }

        public Criteria andBackDealGroupGreaterThan(String value) {
            addCriterion("BACK_DEAL_GROUP >", value, "backDealGroup");
            return (Criteria) this;
        }

        public Criteria andBackDealGroupGreaterThanOrEqualTo(String value) {
            addCriterion("BACK_DEAL_GROUP >=", value, "backDealGroup");
            return (Criteria) this;
        }

        public Criteria andBackDealGroupLessThan(String value) {
            addCriterion("BACK_DEAL_GROUP <", value, "backDealGroup");
            return (Criteria) this;
        }

        public Criteria andBackDealGroupLessThanOrEqualTo(String value) {
            addCriterion("BACK_DEAL_GROUP <=", value, "backDealGroup");
            return (Criteria) this;
        }

        public Criteria andBackDealGroupLike(String value) {
            addCriterion("BACK_DEAL_GROUP like", value, "backDealGroup");
            return (Criteria) this;
        }

        public Criteria andBackDealGroupNotLike(String value) {
            addCriterion("BACK_DEAL_GROUP not like", value, "backDealGroup");
            return (Criteria) this;
        }

        public Criteria andBackDealGroupIn(List<String> values) {
            addCriterion("BACK_DEAL_GROUP in", values, "backDealGroup");
            return (Criteria) this;
        }

        public Criteria andBackDealGroupNotIn(List<String> values) {
            addCriterion("BACK_DEAL_GROUP not in", values, "backDealGroup");
            return (Criteria) this;
        }

        public Criteria andBackDealGroupBetween(String value1, String value2) {
            addCriterion("BACK_DEAL_GROUP between", value1, value2, "backDealGroup");
            return (Criteria) this;
        }

        public Criteria andBackDealGroupNotBetween(String value1, String value2) {
            addCriterion("BACK_DEAL_GROUP not between", value1, value2, "backDealGroup");
            return (Criteria) this;
        }

        public Criteria andBackDealPersonIsNull() {
            addCriterion("BACK_DEAL_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andBackDealPersonIsNotNull() {
            addCriterion("BACK_DEAL_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andBackDealPersonEqualTo(String value) {
            addCriterion("BACK_DEAL_PERSON =", value, "backDealPerson");
            return (Criteria) this;
        }

        public Criteria andBackDealPersonNotEqualTo(String value) {
            addCriterion("BACK_DEAL_PERSON <>", value, "backDealPerson");
            return (Criteria) this;
        }

        public Criteria andBackDealPersonGreaterThan(String value) {
            addCriterion("BACK_DEAL_PERSON >", value, "backDealPerson");
            return (Criteria) this;
        }

        public Criteria andBackDealPersonGreaterThanOrEqualTo(String value) {
            addCriterion("BACK_DEAL_PERSON >=", value, "backDealPerson");
            return (Criteria) this;
        }

        public Criteria andBackDealPersonLessThan(String value) {
            addCriterion("BACK_DEAL_PERSON <", value, "backDealPerson");
            return (Criteria) this;
        }

        public Criteria andBackDealPersonLessThanOrEqualTo(String value) {
            addCriterion("BACK_DEAL_PERSON <=", value, "backDealPerson");
            return (Criteria) this;
        }

        public Criteria andBackDealPersonLike(String value) {
            addCriterion("BACK_DEAL_PERSON like", value, "backDealPerson");
            return (Criteria) this;
        }

        public Criteria andBackDealPersonNotLike(String value) {
            addCriterion("BACK_DEAL_PERSON not like", value, "backDealPerson");
            return (Criteria) this;
        }

        public Criteria andBackDealPersonIn(List<String> values) {
            addCriterion("BACK_DEAL_PERSON in", values, "backDealPerson");
            return (Criteria) this;
        }

        public Criteria andBackDealPersonNotIn(List<String> values) {
            addCriterion("BACK_DEAL_PERSON not in", values, "backDealPerson");
            return (Criteria) this;
        }

        public Criteria andBackDealPersonBetween(String value1, String value2) {
            addCriterion("BACK_DEAL_PERSON between", value1, value2, "backDealPerson");
            return (Criteria) this;
        }

        public Criteria andBackDealPersonNotBetween(String value1, String value2) {
            addCriterion("BACK_DEAL_PERSON not between", value1, value2, "backDealPerson");
            return (Criteria) this;
        }

        public Criteria andJoTaskContentIsNull() {
            addCriterion("JO_TASK_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andJoTaskContentIsNotNull() {
            addCriterion("JO_TASK_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andJoTaskContentEqualTo(String value) {
            addCriterion("JO_TASK_CONTENT =", value, "joTaskContent");
            return (Criteria) this;
        }

        public Criteria andJoTaskContentNotEqualTo(String value) {
            addCriterion("JO_TASK_CONTENT <>", value, "joTaskContent");
            return (Criteria) this;
        }

        public Criteria andJoTaskContentGreaterThan(String value) {
            addCriterion("JO_TASK_CONTENT >", value, "joTaskContent");
            return (Criteria) this;
        }

        public Criteria andJoTaskContentGreaterThanOrEqualTo(String value) {
            addCriterion("JO_TASK_CONTENT >=", value, "joTaskContent");
            return (Criteria) this;
        }

        public Criteria andJoTaskContentLessThan(String value) {
            addCriterion("JO_TASK_CONTENT <", value, "joTaskContent");
            return (Criteria) this;
        }

        public Criteria andJoTaskContentLessThanOrEqualTo(String value) {
            addCriterion("JO_TASK_CONTENT <=", value, "joTaskContent");
            return (Criteria) this;
        }

        public Criteria andJoTaskContentLike(String value) {
            addCriterion("JO_TASK_CONTENT like", value, "joTaskContent");
            return (Criteria) this;
        }

        public Criteria andJoTaskContentNotLike(String value) {
            addCriterion("JO_TASK_CONTENT not like", value, "joTaskContent");
            return (Criteria) this;
        }

        public Criteria andJoTaskContentIn(List<String> values) {
            addCriterion("JO_TASK_CONTENT in", values, "joTaskContent");
            return (Criteria) this;
        }

        public Criteria andJoTaskContentNotIn(List<String> values) {
            addCriterion("JO_TASK_CONTENT not in", values, "joTaskContent");
            return (Criteria) this;
        }

        public Criteria andJoTaskContentBetween(String value1, String value2) {
            addCriterion("JO_TASK_CONTENT between", value1, value2, "joTaskContent");
            return (Criteria) this;
        }

        public Criteria andJoTaskContentNotBetween(String value1, String value2) {
            addCriterion("JO_TASK_CONTENT not between", value1, value2, "joTaskContent");
            return (Criteria) this;
        }

        public Criteria andJoTaskAnnexIdIsNull() {
            addCriterion("JO_TASK_ANNEX_ID is null");
            return (Criteria) this;
        }

        public Criteria andJoTaskAnnexIdIsNotNull() {
            addCriterion("JO_TASK_ANNEX_ID is not null");
            return (Criteria) this;
        }

        public Criteria andJoTaskAnnexIdEqualTo(String value) {
            addCriterion("JO_TASK_ANNEX_ID =", value, "joTaskAnnexId");
            return (Criteria) this;
        }

        public Criteria andJoTaskAnnexIdNotEqualTo(String value) {
            addCriterion("JO_TASK_ANNEX_ID <>", value, "joTaskAnnexId");
            return (Criteria) this;
        }

        public Criteria andJoTaskAnnexIdGreaterThan(String value) {
            addCriterion("JO_TASK_ANNEX_ID >", value, "joTaskAnnexId");
            return (Criteria) this;
        }

        public Criteria andJoTaskAnnexIdGreaterThanOrEqualTo(String value) {
            addCriterion("JO_TASK_ANNEX_ID >=", value, "joTaskAnnexId");
            return (Criteria) this;
        }

        public Criteria andJoTaskAnnexIdLessThan(String value) {
            addCriterion("JO_TASK_ANNEX_ID <", value, "joTaskAnnexId");
            return (Criteria) this;
        }

        public Criteria andJoTaskAnnexIdLessThanOrEqualTo(String value) {
            addCriterion("JO_TASK_ANNEX_ID <=", value, "joTaskAnnexId");
            return (Criteria) this;
        }

        public Criteria andJoTaskAnnexIdLike(String value) {
            addCriterion("JO_TASK_ANNEX_ID like", value, "joTaskAnnexId");
            return (Criteria) this;
        }

        public Criteria andJoTaskAnnexIdNotLike(String value) {
            addCriterion("JO_TASK_ANNEX_ID not like", value, "joTaskAnnexId");
            return (Criteria) this;
        }

        public Criteria andJoTaskAnnexIdIn(List<String> values) {
            addCriterion("JO_TASK_ANNEX_ID in", values, "joTaskAnnexId");
            return (Criteria) this;
        }

        public Criteria andJoTaskAnnexIdNotIn(List<String> values) {
            addCriterion("JO_TASK_ANNEX_ID not in", values, "joTaskAnnexId");
            return (Criteria) this;
        }

        public Criteria andJoTaskAnnexIdBetween(String value1, String value2) {
            addCriterion("JO_TASK_ANNEX_ID between", value1, value2, "joTaskAnnexId");
            return (Criteria) this;
        }

        public Criteria andJoTaskAnnexIdNotBetween(String value1, String value2) {
            addCriterion("JO_TASK_ANNEX_ID not between", value1, value2, "joTaskAnnexId");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeLengthIsNull() {
            addCriterion("JO_TASK_TIME_LENGTH is null");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeLengthIsNotNull() {
            addCriterion("JO_TASK_TIME_LENGTH is not null");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeLengthEqualTo(BigDecimal value) {
            addCriterion("JO_TASK_TIME_LENGTH =", value, "joTaskTimeLength");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeLengthNotEqualTo(BigDecimal value) {
            addCriterion("JO_TASK_TIME_LENGTH <>", value, "joTaskTimeLength");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeLengthGreaterThan(BigDecimal value) {
            addCriterion("JO_TASK_TIME_LENGTH >", value, "joTaskTimeLength");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeLengthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("JO_TASK_TIME_LENGTH >=", value, "joTaskTimeLength");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeLengthLessThan(BigDecimal value) {
            addCriterion("JO_TASK_TIME_LENGTH <", value, "joTaskTimeLength");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeLengthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("JO_TASK_TIME_LENGTH <=", value, "joTaskTimeLength");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeLengthIn(List<BigDecimal> values) {
            addCriterion("JO_TASK_TIME_LENGTH in", values, "joTaskTimeLength");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeLengthNotIn(List<BigDecimal> values) {
            addCriterion("JO_TASK_TIME_LENGTH not in", values, "joTaskTimeLength");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeLengthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("JO_TASK_TIME_LENGTH between", value1, value2, "joTaskTimeLength");
            return (Criteria) this;
        }

        public Criteria andJoTaskTimeLengthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("JO_TASK_TIME_LENGTH not between", value1, value2, "joTaskTimeLength");
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