package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.util.ArrayList;
import java.util.List;

public class ProfitAdjustMExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ProfitAdjustMExample() {
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

        public Criteria andAdjustMainIdIsNull() {
            addCriterion("ADJUST_MAIN_ID is null");
            return (Criteria) this;
        }

        public Criteria andAdjustMainIdIsNotNull() {
            addCriterion("ADJUST_MAIN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustMainIdEqualTo(String value) {
            addCriterion("ADJUST_MAIN_ID =", value, "adjustMainId");
            return (Criteria) this;
        }

        public Criteria andAdjustMainIdNotEqualTo(String value) {
            addCriterion("ADJUST_MAIN_ID <>", value, "adjustMainId");
            return (Criteria) this;
        }

        public Criteria andAdjustMainIdGreaterThan(String value) {
            addCriterion("ADJUST_MAIN_ID >", value, "adjustMainId");
            return (Criteria) this;
        }

        public Criteria andAdjustMainIdGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_MAIN_ID >=", value, "adjustMainId");
            return (Criteria) this;
        }

        public Criteria andAdjustMainIdLessThan(String value) {
            addCriterion("ADJUST_MAIN_ID <", value, "adjustMainId");
            return (Criteria) this;
        }

        public Criteria andAdjustMainIdLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_MAIN_ID <=", value, "adjustMainId");
            return (Criteria) this;
        }

        public Criteria andAdjustMainIdLike(String value) {
            addCriterion("ADJUST_MAIN_ID like", value, "adjustMainId");
            return (Criteria) this;
        }

        public Criteria andAdjustMainIdNotLike(String value) {
            addCriterion("ADJUST_MAIN_ID not like", value, "adjustMainId");
            return (Criteria) this;
        }

        public Criteria andAdjustMainIdIn(List<String> values) {
            addCriterion("ADJUST_MAIN_ID in", values, "adjustMainId");
            return (Criteria) this;
        }

        public Criteria andAdjustMainIdNotIn(List<String> values) {
            addCriterion("ADJUST_MAIN_ID not in", values, "adjustMainId");
            return (Criteria) this;
        }

        public Criteria andAdjustMainIdBetween(String value1, String value2) {
            addCriterion("ADJUST_MAIN_ID between", value1, value2, "adjustMainId");
            return (Criteria) this;
        }

        public Criteria andAdjustMainIdNotBetween(String value1, String value2) {
            addCriterion("ADJUST_MAIN_ID not between", value1, value2, "adjustMainId");
            return (Criteria) this;
        }

        public Criteria andAdjustMainNameIsNull() {
            addCriterion("ADJUST_MAIN_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAdjustMainNameIsNotNull() {
            addCriterion("ADJUST_MAIN_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustMainNameEqualTo(String value) {
            addCriterion("ADJUST_MAIN_NAME =", value, "adjustMainName");
            return (Criteria) this;
        }

        public Criteria andAdjustMainNameNotEqualTo(String value) {
            addCriterion("ADJUST_MAIN_NAME <>", value, "adjustMainName");
            return (Criteria) this;
        }

        public Criteria andAdjustMainNameGreaterThan(String value) {
            addCriterion("ADJUST_MAIN_NAME >", value, "adjustMainName");
            return (Criteria) this;
        }

        public Criteria andAdjustMainNameGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_MAIN_NAME >=", value, "adjustMainName");
            return (Criteria) this;
        }

        public Criteria andAdjustMainNameLessThan(String value) {
            addCriterion("ADJUST_MAIN_NAME <", value, "adjustMainName");
            return (Criteria) this;
        }

        public Criteria andAdjustMainNameLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_MAIN_NAME <=", value, "adjustMainName");
            return (Criteria) this;
        }

        public Criteria andAdjustMainNameLike(String value) {
            addCriterion("ADJUST_MAIN_NAME like", value, "adjustMainName");
            return (Criteria) this;
        }

        public Criteria andAdjustMainNameNotLike(String value) {
            addCriterion("ADJUST_MAIN_NAME not like", value, "adjustMainName");
            return (Criteria) this;
        }

        public Criteria andAdjustMainNameIn(List<String> values) {
            addCriterion("ADJUST_MAIN_NAME in", values, "adjustMainName");
            return (Criteria) this;
        }

        public Criteria andAdjustMainNameNotIn(List<String> values) {
            addCriterion("ADJUST_MAIN_NAME not in", values, "adjustMainName");
            return (Criteria) this;
        }

        public Criteria andAdjustMainNameBetween(String value1, String value2) {
            addCriterion("ADJUST_MAIN_NAME between", value1, value2, "adjustMainName");
            return (Criteria) this;
        }

        public Criteria andAdjustMainNameNotBetween(String value1, String value2) {
            addCriterion("ADJUST_MAIN_NAME not between", value1, value2, "adjustMainName");
            return (Criteria) this;
        }

        public Criteria andAdjustSubIdIsNull() {
            addCriterion("ADJUST_SUB_ID is null");
            return (Criteria) this;
        }

        public Criteria andAdjustSubIdIsNotNull() {
            addCriterion("ADJUST_SUB_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustSubIdEqualTo(String value) {
            addCriterion("ADJUST_SUB_ID =", value, "adjustSubId");
            return (Criteria) this;
        }

        public Criteria andAdjustSubIdNotEqualTo(String value) {
            addCriterion("ADJUST_SUB_ID <>", value, "adjustSubId");
            return (Criteria) this;
        }

        public Criteria andAdjustSubIdGreaterThan(String value) {
            addCriterion("ADJUST_SUB_ID >", value, "adjustSubId");
            return (Criteria) this;
        }

        public Criteria andAdjustSubIdGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_SUB_ID >=", value, "adjustSubId");
            return (Criteria) this;
        }

        public Criteria andAdjustSubIdLessThan(String value) {
            addCriterion("ADJUST_SUB_ID <", value, "adjustSubId");
            return (Criteria) this;
        }

        public Criteria andAdjustSubIdLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_SUB_ID <=", value, "adjustSubId");
            return (Criteria) this;
        }

        public Criteria andAdjustSubIdLike(String value) {
            addCriterion("ADJUST_SUB_ID like", value, "adjustSubId");
            return (Criteria) this;
        }

        public Criteria andAdjustSubIdNotLike(String value) {
            addCriterion("ADJUST_SUB_ID not like", value, "adjustSubId");
            return (Criteria) this;
        }

        public Criteria andAdjustSubIdIn(List<String> values) {
            addCriterion("ADJUST_SUB_ID in", values, "adjustSubId");
            return (Criteria) this;
        }

        public Criteria andAdjustSubIdNotIn(List<String> values) {
            addCriterion("ADJUST_SUB_ID not in", values, "adjustSubId");
            return (Criteria) this;
        }

        public Criteria andAdjustSubIdBetween(String value1, String value2) {
            addCriterion("ADJUST_SUB_ID between", value1, value2, "adjustSubId");
            return (Criteria) this;
        }

        public Criteria andAdjustSubIdNotBetween(String value1, String value2) {
            addCriterion("ADJUST_SUB_ID not between", value1, value2, "adjustSubId");
            return (Criteria) this;
        }

        public Criteria andAdjustSubNameIsNull() {
            addCriterion("ADJUST_SUB_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAdjustSubNameIsNotNull() {
            addCriterion("ADJUST_SUB_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustSubNameEqualTo(String value) {
            addCriterion("ADJUST_SUB_NAME =", value, "adjustSubName");
            return (Criteria) this;
        }

        public Criteria andAdjustSubNameNotEqualTo(String value) {
            addCriterion("ADJUST_SUB_NAME <>", value, "adjustSubName");
            return (Criteria) this;
        }

        public Criteria andAdjustSubNameGreaterThan(String value) {
            addCriterion("ADJUST_SUB_NAME >", value, "adjustSubName");
            return (Criteria) this;
        }

        public Criteria andAdjustSubNameGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_SUB_NAME >=", value, "adjustSubName");
            return (Criteria) this;
        }

        public Criteria andAdjustSubNameLessThan(String value) {
            addCriterion("ADJUST_SUB_NAME <", value, "adjustSubName");
            return (Criteria) this;
        }

        public Criteria andAdjustSubNameLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_SUB_NAME <=", value, "adjustSubName");
            return (Criteria) this;
        }

        public Criteria andAdjustSubNameLike(String value) {
            addCriterion("ADJUST_SUB_NAME like", value, "adjustSubName");
            return (Criteria) this;
        }

        public Criteria andAdjustSubNameNotLike(String value) {
            addCriterion("ADJUST_SUB_NAME not like", value, "adjustSubName");
            return (Criteria) this;
        }

        public Criteria andAdjustSubNameIn(List<String> values) {
            addCriterion("ADJUST_SUB_NAME in", values, "adjustSubName");
            return (Criteria) this;
        }

        public Criteria andAdjustSubNameNotIn(List<String> values) {
            addCriterion("ADJUST_SUB_NAME not in", values, "adjustSubName");
            return (Criteria) this;
        }

        public Criteria andAdjustSubNameBetween(String value1, String value2) {
            addCriterion("ADJUST_SUB_NAME between", value1, value2, "adjustSubName");
            return (Criteria) this;
        }

        public Criteria andAdjustSubNameNotBetween(String value1, String value2) {
            addCriterion("ADJUST_SUB_NAME not between", value1, value2, "adjustSubName");
            return (Criteria) this;
        }

        public Criteria andAdjustMtypeIsNull() {
            addCriterion("ADJUST_MTYPE is null");
            return (Criteria) this;
        }

        public Criteria andAdjustMtypeIsNotNull() {
            addCriterion("ADJUST_MTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustMtypeEqualTo(String value) {
            addCriterion("ADJUST_MTYPE =", value, "adjustMtype");
            return (Criteria) this;
        }

        public Criteria andAdjustMtypeNotEqualTo(String value) {
            addCriterion("ADJUST_MTYPE <>", value, "adjustMtype");
            return (Criteria) this;
        }

        public Criteria andAdjustMtypeGreaterThan(String value) {
            addCriterion("ADJUST_MTYPE >", value, "adjustMtype");
            return (Criteria) this;
        }

        public Criteria andAdjustMtypeGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_MTYPE >=", value, "adjustMtype");
            return (Criteria) this;
        }

        public Criteria andAdjustMtypeLessThan(String value) {
            addCriterion("ADJUST_MTYPE <", value, "adjustMtype");
            return (Criteria) this;
        }

        public Criteria andAdjustMtypeLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_MTYPE <=", value, "adjustMtype");
            return (Criteria) this;
        }

        public Criteria andAdjustMtypeLike(String value) {
            addCriterion("ADJUST_MTYPE like", value, "adjustMtype");
            return (Criteria) this;
        }

        public Criteria andAdjustMtypeNotLike(String value) {
            addCriterion("ADJUST_MTYPE not like", value, "adjustMtype");
            return (Criteria) this;
        }

        public Criteria andAdjustMtypeIn(List<String> values) {
            addCriterion("ADJUST_MTYPE in", values, "adjustMtype");
            return (Criteria) this;
        }

        public Criteria andAdjustMtypeNotIn(List<String> values) {
            addCriterion("ADJUST_MTYPE not in", values, "adjustMtype");
            return (Criteria) this;
        }

        public Criteria andAdjustMtypeBetween(String value1, String value2) {
            addCriterion("ADJUST_MTYPE between", value1, value2, "adjustMtype");
            return (Criteria) this;
        }

        public Criteria andAdjustMtypeNotBetween(String value1, String value2) {
            addCriterion("ADJUST_MTYPE not between", value1, value2, "adjustMtype");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtIsNull() {
            addCriterion("ADJUST_AMT is null");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtIsNotNull() {
            addCriterion("ADJUST_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtEqualTo(String value) {
            addCriterion("ADJUST_AMT =", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtNotEqualTo(String value) {
            addCriterion("ADJUST_AMT <>", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtGreaterThan(String value) {
            addCriterion("ADJUST_AMT >", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_AMT >=", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtLessThan(String value) {
            addCriterion("ADJUST_AMT <", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_AMT <=", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtLike(String value) {
            addCriterion("ADJUST_AMT like", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtNotLike(String value) {
            addCriterion("ADJUST_AMT not like", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtIn(List<String> values) {
            addCriterion("ADJUST_AMT in", values, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtNotIn(List<String> values) {
            addCriterion("ADJUST_AMT not in", values, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtBetween(String value1, String value2) {
            addCriterion("ADJUST_AMT between", value1, value2, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtNotBetween(String value1, String value2) {
            addCriterion("ADJUST_AMT not between", value1, value2, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustDateIsNull() {
            addCriterion("ADJUST_DATE is null");
            return (Criteria) this;
        }

        public Criteria andAdjustDateIsNotNull() {
            addCriterion("ADJUST_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustDateEqualTo(String value) {
            addCriterion("ADJUST_DATE =", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateNotEqualTo(String value) {
            addCriterion("ADJUST_DATE <>", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateGreaterThan(String value) {
            addCriterion("ADJUST_DATE >", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_DATE >=", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateLessThan(String value) {
            addCriterion("ADJUST_DATE <", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_DATE <=", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateLike(String value) {
            addCriterion("ADJUST_DATE like", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateNotLike(String value) {
            addCriterion("ADJUST_DATE not like", value, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateIn(List<String> values) {
            addCriterion("ADJUST_DATE in", values, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateNotIn(List<String> values) {
            addCriterion("ADJUST_DATE not in", values, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateBetween(String value1, String value2) {
            addCriterion("ADJUST_DATE between", value1, value2, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustDateNotBetween(String value1, String value2) {
            addCriterion("ADJUST_DATE not between", value1, value2, "adjustDate");
            return (Criteria) this;
        }

        public Criteria andAdjustContentIsNull() {
            addCriterion("ADJUST_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andAdjustContentIsNotNull() {
            addCriterion("ADJUST_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustContentEqualTo(String value) {
            addCriterion("ADJUST_CONTENT =", value, "adjustContent");
            return (Criteria) this;
        }

        public Criteria andAdjustContentNotEqualTo(String value) {
            addCriterion("ADJUST_CONTENT <>", value, "adjustContent");
            return (Criteria) this;
        }

        public Criteria andAdjustContentGreaterThan(String value) {
            addCriterion("ADJUST_CONTENT >", value, "adjustContent");
            return (Criteria) this;
        }

        public Criteria andAdjustContentGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_CONTENT >=", value, "adjustContent");
            return (Criteria) this;
        }

        public Criteria andAdjustContentLessThan(String value) {
            addCriterion("ADJUST_CONTENT <", value, "adjustContent");
            return (Criteria) this;
        }

        public Criteria andAdjustContentLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_CONTENT <=", value, "adjustContent");
            return (Criteria) this;
        }

        public Criteria andAdjustContentLike(String value) {
            addCriterion("ADJUST_CONTENT like", value, "adjustContent");
            return (Criteria) this;
        }

        public Criteria andAdjustContentNotLike(String value) {
            addCriterion("ADJUST_CONTENT not like", value, "adjustContent");
            return (Criteria) this;
        }

        public Criteria andAdjustContentIn(List<String> values) {
            addCriterion("ADJUST_CONTENT in", values, "adjustContent");
            return (Criteria) this;
        }

        public Criteria andAdjustContentNotIn(List<String> values) {
            addCriterion("ADJUST_CONTENT not in", values, "adjustContent");
            return (Criteria) this;
        }

        public Criteria andAdjustContentBetween(String value1, String value2) {
            addCriterion("ADJUST_CONTENT between", value1, value2, "adjustContent");
            return (Criteria) this;
        }

        public Criteria andAdjustContentNotBetween(String value1, String value2) {
            addCriterion("ADJUST_CONTENT not between", value1, value2, "adjustContent");
            return (Criteria) this;
        }

        public Criteria andAdjustPersonIsNull() {
            addCriterion("ADJUST_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andAdjustPersonIsNotNull() {
            addCriterion("ADJUST_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustPersonEqualTo(String value) {
            addCriterion("ADJUST_PERSON =", value, "adjustPerson");
            return (Criteria) this;
        }

        public Criteria andAdjustPersonNotEqualTo(String value) {
            addCriterion("ADJUST_PERSON <>", value, "adjustPerson");
            return (Criteria) this;
        }

        public Criteria andAdjustPersonGreaterThan(String value) {
            addCriterion("ADJUST_PERSON >", value, "adjustPerson");
            return (Criteria) this;
        }

        public Criteria andAdjustPersonGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_PERSON >=", value, "adjustPerson");
            return (Criteria) this;
        }

        public Criteria andAdjustPersonLessThan(String value) {
            addCriterion("ADJUST_PERSON <", value, "adjustPerson");
            return (Criteria) this;
        }

        public Criteria andAdjustPersonLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_PERSON <=", value, "adjustPerson");
            return (Criteria) this;
        }

        public Criteria andAdjustPersonLike(String value) {
            addCriterion("ADJUST_PERSON like", value, "adjustPerson");
            return (Criteria) this;
        }

        public Criteria andAdjustPersonNotLike(String value) {
            addCriterion("ADJUST_PERSON not like", value, "adjustPerson");
            return (Criteria) this;
        }

        public Criteria andAdjustPersonIn(List<String> values) {
            addCriterion("ADJUST_PERSON in", values, "adjustPerson");
            return (Criteria) this;
        }

        public Criteria andAdjustPersonNotIn(List<String> values) {
            addCriterion("ADJUST_PERSON not in", values, "adjustPerson");
            return (Criteria) this;
        }

        public Criteria andAdjustPersonBetween(String value1, String value2) {
            addCriterion("ADJUST_PERSON between", value1, value2, "adjustPerson");
            return (Criteria) this;
        }

        public Criteria andAdjustPersonNotBetween(String value1, String value2) {
            addCriterion("ADJUST_PERSON not between", value1, value2, "adjustPerson");
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