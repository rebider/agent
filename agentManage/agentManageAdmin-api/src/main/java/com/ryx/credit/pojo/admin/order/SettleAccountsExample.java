package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SettleAccountsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public SettleAccountsExample() {
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

        public Criteria andSTypeIsNull() {
            addCriterion("S_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSTypeIsNotNull() {
            addCriterion("S_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSTypeEqualTo(BigDecimal value) {
            addCriterion("S_TYPE =", value, "sType");
            return (Criteria) this;
        }

        public Criteria andSTypeNotEqualTo(BigDecimal value) {
            addCriterion("S_TYPE <>", value, "sType");
            return (Criteria) this;
        }

        public Criteria andSTypeGreaterThan(BigDecimal value) {
            addCriterion("S_TYPE >", value, "sType");
            return (Criteria) this;
        }

        public Criteria andSTypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("S_TYPE >=", value, "sType");
            return (Criteria) this;
        }

        public Criteria andSTypeLessThan(BigDecimal value) {
            addCriterion("S_TYPE <", value, "sType");
            return (Criteria) this;
        }

        public Criteria andSTypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("S_TYPE <=", value, "sType");
            return (Criteria) this;
        }

        public Criteria andSTypeIn(List<BigDecimal> values) {
            addCriterion("S_TYPE in", values, "sType");
            return (Criteria) this;
        }

        public Criteria andSTypeNotIn(List<BigDecimal> values) {
            addCriterion("S_TYPE not in", values, "sType");
            return (Criteria) this;
        }

        public Criteria andSTypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("S_TYPE between", value1, value2, "sType");
            return (Criteria) this;
        }

        public Criteria andSTypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("S_TYPE not between", value1, value2, "sType");
            return (Criteria) this;
        }

        public Criteria andSAmountIsNull() {
            addCriterion("S_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andSAmountIsNotNull() {
            addCriterion("S_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andSAmountEqualTo(BigDecimal value) {
            addCriterion("S_AMOUNT =", value, "sAmount");
            return (Criteria) this;
        }

        public Criteria andSAmountNotEqualTo(BigDecimal value) {
            addCriterion("S_AMOUNT <>", value, "sAmount");
            return (Criteria) this;
        }

        public Criteria andSAmountGreaterThan(BigDecimal value) {
            addCriterion("S_AMOUNT >", value, "sAmount");
            return (Criteria) this;
        }

        public Criteria andSAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("S_AMOUNT >=", value, "sAmount");
            return (Criteria) this;
        }

        public Criteria andSAmountLessThan(BigDecimal value) {
            addCriterion("S_AMOUNT <", value, "sAmount");
            return (Criteria) this;
        }

        public Criteria andSAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("S_AMOUNT <=", value, "sAmount");
            return (Criteria) this;
        }

        public Criteria andSAmountIn(List<BigDecimal> values) {
            addCriterion("S_AMOUNT in", values, "sAmount");
            return (Criteria) this;
        }

        public Criteria andSAmountNotIn(List<BigDecimal> values) {
            addCriterion("S_AMOUNT not in", values, "sAmount");
            return (Criteria) this;
        }

        public Criteria andSAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("S_AMOUNT between", value1, value2, "sAmount");
            return (Criteria) this;
        }

        public Criteria andSAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("S_AMOUNT not between", value1, value2, "sAmount");
            return (Criteria) this;
        }

        public Criteria andSTmIsNull() {
            addCriterion("S_TM is null");
            return (Criteria) this;
        }

        public Criteria andSTmIsNotNull() {
            addCriterion("S_TM is not null");
            return (Criteria) this;
        }

        public Criteria andSTmEqualTo(Date value) {
            addCriterion("S_TM =", value, "sTm");
            return (Criteria) this;
        }

        public Criteria andSTmNotEqualTo(Date value) {
            addCriterion("S_TM <>", value, "sTm");
            return (Criteria) this;
        }

        public Criteria andSTmGreaterThan(Date value) {
            addCriterion("S_TM >", value, "sTm");
            return (Criteria) this;
        }

        public Criteria andSTmGreaterThanOrEqualTo(Date value) {
            addCriterion("S_TM >=", value, "sTm");
            return (Criteria) this;
        }

        public Criteria andSTmLessThan(Date value) {
            addCriterion("S_TM <", value, "sTm");
            return (Criteria) this;
        }

        public Criteria andSTmLessThanOrEqualTo(Date value) {
            addCriterion("S_TM <=", value, "sTm");
            return (Criteria) this;
        }

        public Criteria andSTmIn(List<Date> values) {
            addCriterion("S_TM in", values, "sTm");
            return (Criteria) this;
        }

        public Criteria andSTmNotIn(List<Date> values) {
            addCriterion("S_TM not in", values, "sTm");
            return (Criteria) this;
        }

        public Criteria andSTmBetween(Date value1, Date value2) {
            addCriterion("S_TM between", value1, value2, "sTm");
            return (Criteria) this;
        }

        public Criteria andSTmNotBetween(Date value1, Date value2) {
            addCriterion("S_TM not between", value1, value2, "sTm");
            return (Criteria) this;
        }

        public Criteria andSStatusIsNull() {
            addCriterion("S_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andSStatusIsNotNull() {
            addCriterion("S_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andSStatusEqualTo(BigDecimal value) {
            addCriterion("S_STATUS =", value, "sStatus");
            return (Criteria) this;
        }

        public Criteria andSStatusNotEqualTo(BigDecimal value) {
            addCriterion("S_STATUS <>", value, "sStatus");
            return (Criteria) this;
        }

        public Criteria andSStatusGreaterThan(BigDecimal value) {
            addCriterion("S_STATUS >", value, "sStatus");
            return (Criteria) this;
        }

        public Criteria andSStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("S_STATUS >=", value, "sStatus");
            return (Criteria) this;
        }

        public Criteria andSStatusLessThan(BigDecimal value) {
            addCriterion("S_STATUS <", value, "sStatus");
            return (Criteria) this;
        }

        public Criteria andSStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("S_STATUS <=", value, "sStatus");
            return (Criteria) this;
        }

        public Criteria andSStatusIn(List<BigDecimal> values) {
            addCriterion("S_STATUS in", values, "sStatus");
            return (Criteria) this;
        }

        public Criteria andSStatusNotIn(List<BigDecimal> values) {
            addCriterion("S_STATUS not in", values, "sStatus");
            return (Criteria) this;
        }

        public Criteria andSStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("S_STATUS between", value1, value2, "sStatus");
            return (Criteria) this;
        }

        public Criteria andSStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("S_STATUS not between", value1, value2, "sStatus");
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

        public Criteria andSrcIdIsNull() {
            addCriterion("SRC_ID is null");
            return (Criteria) this;
        }

        public Criteria andSrcIdIsNotNull() {
            addCriterion("SRC_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSrcIdEqualTo(String value) {
            addCriterion("SRC_ID =", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdNotEqualTo(String value) {
            addCriterion("SRC_ID <>", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdGreaterThan(String value) {
            addCriterion("SRC_ID >", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdGreaterThanOrEqualTo(String value) {
            addCriterion("SRC_ID >=", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdLessThan(String value) {
            addCriterion("SRC_ID <", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdLessThanOrEqualTo(String value) {
            addCriterion("SRC_ID <=", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdLike(String value) {
            addCriterion("SRC_ID like", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdNotLike(String value) {
            addCriterion("SRC_ID not like", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdIn(List<String> values) {
            addCriterion("SRC_ID in", values, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdNotIn(List<String> values) {
            addCriterion("SRC_ID not in", values, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdBetween(String value1, String value2) {
            addCriterion("SRC_ID between", value1, value2, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdNotBetween(String value1, String value2) {
            addCriterion("SRC_ID not between", value1, value2, "srcId");
            return (Criteria) this;
        }

        public Criteria andTarIdIsNull() {
            addCriterion("TAR_ID is null");
            return (Criteria) this;
        }

        public Criteria andTarIdIsNotNull() {
            addCriterion("TAR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTarIdEqualTo(String value) {
            addCriterion("TAR_ID =", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdNotEqualTo(String value) {
            addCriterion("TAR_ID <>", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdGreaterThan(String value) {
            addCriterion("TAR_ID >", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdGreaterThanOrEqualTo(String value) {
            addCriterion("TAR_ID >=", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdLessThan(String value) {
            addCriterion("TAR_ID <", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdLessThanOrEqualTo(String value) {
            addCriterion("TAR_ID <=", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdLike(String value) {
            addCriterion("TAR_ID like", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdNotLike(String value) {
            addCriterion("TAR_ID not like", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdIn(List<String> values) {
            addCriterion("TAR_ID in", values, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdNotIn(List<String> values) {
            addCriterion("TAR_ID not in", values, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdBetween(String value1, String value2) {
            addCriterion("TAR_ID between", value1, value2, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdNotBetween(String value1, String value2) {
            addCriterion("TAR_ID not between", value1, value2, "tarId");
            return (Criteria) this;
        }

        public Criteria andCTmIsNull() {
            addCriterion("C_TM is null");
            return (Criteria) this;
        }

        public Criteria andCTmIsNotNull() {
            addCriterion("C_TM is not null");
            return (Criteria) this;
        }

        public Criteria andCTmEqualTo(Date value) {
            addCriterion("C_TM =", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmNotEqualTo(Date value) {
            addCriterion("C_TM <>", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmGreaterThan(Date value) {
            addCriterion("C_TM >", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmGreaterThanOrEqualTo(Date value) {
            addCriterion("C_TM >=", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmLessThan(Date value) {
            addCriterion("C_TM <", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmLessThanOrEqualTo(Date value) {
            addCriterion("C_TM <=", value, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmIn(List<Date> values) {
            addCriterion("C_TM in", values, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmNotIn(List<Date> values) {
            addCriterion("C_TM not in", values, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmBetween(Date value1, Date value2) {
            addCriterion("C_TM between", value1, value2, "cTm");
            return (Criteria) this;
        }

        public Criteria andCTmNotBetween(Date value1, Date value2) {
            addCriterion("C_TM not between", value1, value2, "cTm");
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

        public Criteria andCUtmIsNull() {
            addCriterion("C_UTM is null");
            return (Criteria) this;
        }

        public Criteria andCUtmIsNotNull() {
            addCriterion("C_UTM is not null");
            return (Criteria) this;
        }

        public Criteria andCUtmEqualTo(Date value) {
            addCriterion("C_UTM =", value, "cUtm");
            return (Criteria) this;
        }

        public Criteria andCUtmNotEqualTo(Date value) {
            addCriterion("C_UTM <>", value, "cUtm");
            return (Criteria) this;
        }

        public Criteria andCUtmGreaterThan(Date value) {
            addCriterion("C_UTM >", value, "cUtm");
            return (Criteria) this;
        }

        public Criteria andCUtmGreaterThanOrEqualTo(Date value) {
            addCriterion("C_UTM >=", value, "cUtm");
            return (Criteria) this;
        }

        public Criteria andCUtmLessThan(Date value) {
            addCriterion("C_UTM <", value, "cUtm");
            return (Criteria) this;
        }

        public Criteria andCUtmLessThanOrEqualTo(Date value) {
            addCriterion("C_UTM <=", value, "cUtm");
            return (Criteria) this;
        }

        public Criteria andCUtmIn(List<Date> values) {
            addCriterion("C_UTM in", values, "cUtm");
            return (Criteria) this;
        }

        public Criteria andCUtmNotIn(List<Date> values) {
            addCriterion("C_UTM not in", values, "cUtm");
            return (Criteria) this;
        }

        public Criteria andCUtmBetween(Date value1, Date value2) {
            addCriterion("C_UTM between", value1, value2, "cUtm");
            return (Criteria) this;
        }

        public Criteria andCUtmNotBetween(Date value1, Date value2) {
            addCriterion("C_UTM not between", value1, value2, "cUtm");
            return (Criteria) this;
        }

        public Criteria andCOperatorIsNull() {
            addCriterion("C_OPERATOR is null");
            return (Criteria) this;
        }

        public Criteria andCOperatorIsNotNull() {
            addCriterion("C_OPERATOR is not null");
            return (Criteria) this;
        }

        public Criteria andCOperatorEqualTo(String value) {
            addCriterion("C_OPERATOR =", value, "cOperator");
            return (Criteria) this;
        }

        public Criteria andCOperatorNotEqualTo(String value) {
            addCriterion("C_OPERATOR <>", value, "cOperator");
            return (Criteria) this;
        }

        public Criteria andCOperatorGreaterThan(String value) {
            addCriterion("C_OPERATOR >", value, "cOperator");
            return (Criteria) this;
        }

        public Criteria andCOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("C_OPERATOR >=", value, "cOperator");
            return (Criteria) this;
        }

        public Criteria andCOperatorLessThan(String value) {
            addCriterion("C_OPERATOR <", value, "cOperator");
            return (Criteria) this;
        }

        public Criteria andCOperatorLessThanOrEqualTo(String value) {
            addCriterion("C_OPERATOR <=", value, "cOperator");
            return (Criteria) this;
        }

        public Criteria andCOperatorLike(String value) {
            addCriterion("C_OPERATOR like", value, "cOperator");
            return (Criteria) this;
        }

        public Criteria andCOperatorNotLike(String value) {
            addCriterion("C_OPERATOR not like", value, "cOperator");
            return (Criteria) this;
        }

        public Criteria andCOperatorIn(List<String> values) {
            addCriterion("C_OPERATOR in", values, "cOperator");
            return (Criteria) this;
        }

        public Criteria andCOperatorNotIn(List<String> values) {
            addCriterion("C_OPERATOR not in", values, "cOperator");
            return (Criteria) this;
        }

        public Criteria andCOperatorBetween(String value1, String value2) {
            addCriterion("C_OPERATOR between", value1, value2, "cOperator");
            return (Criteria) this;
        }

        public Criteria andCOperatorNotBetween(String value1, String value2) {
            addCriterion("C_OPERATOR not between", value1, value2, "cOperator");
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