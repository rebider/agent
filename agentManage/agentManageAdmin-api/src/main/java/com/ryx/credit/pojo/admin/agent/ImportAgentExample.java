package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImportAgentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ImportAgentExample() {
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

        public Criteria andDataidIsNull() {
            addCriterion("DATAID is null");
            return (Criteria) this;
        }

        public Criteria andDataidIsNotNull() {
            addCriterion("DATAID is not null");
            return (Criteria) this;
        }

        public Criteria andDataidEqualTo(String value) {
            addCriterion("DATAID =", value, "dataid");
            return (Criteria) this;
        }

        public Criteria andDataidNotEqualTo(String value) {
            addCriterion("DATAID <>", value, "dataid");
            return (Criteria) this;
        }

        public Criteria andDataidGreaterThan(String value) {
            addCriterion("DATAID >", value, "dataid");
            return (Criteria) this;
        }

        public Criteria andDataidGreaterThanOrEqualTo(String value) {
            addCriterion("DATAID >=", value, "dataid");
            return (Criteria) this;
        }

        public Criteria andDataidLessThan(String value) {
            addCriterion("DATAID <", value, "dataid");
            return (Criteria) this;
        }

        public Criteria andDataidLessThanOrEqualTo(String value) {
            addCriterion("DATAID <=", value, "dataid");
            return (Criteria) this;
        }

        public Criteria andDataidLike(String value) {
            addCriterion("DATAID like", value, "dataid");
            return (Criteria) this;
        }

        public Criteria andDataidNotLike(String value) {
            addCriterion("DATAID not like", value, "dataid");
            return (Criteria) this;
        }

        public Criteria andDataidIn(List<String> values) {
            addCriterion("DATAID in", values, "dataid");
            return (Criteria) this;
        }

        public Criteria andDataidNotIn(List<String> values) {
            addCriterion("DATAID not in", values, "dataid");
            return (Criteria) this;
        }

        public Criteria andDataidBetween(String value1, String value2) {
            addCriterion("DATAID between", value1, value2, "dataid");
            return (Criteria) this;
        }

        public Criteria andDataidNotBetween(String value1, String value2) {
            addCriterion("DATAID not between", value1, value2, "dataid");
            return (Criteria) this;
        }

        public Criteria andDatatypeIsNull() {
            addCriterion("DATATYPE is null");
            return (Criteria) this;
        }

        public Criteria andDatatypeIsNotNull() {
            addCriterion("DATATYPE is not null");
            return (Criteria) this;
        }

        public Criteria andDatatypeEqualTo(String value) {
            addCriterion("DATATYPE =", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotEqualTo(String value) {
            addCriterion("DATATYPE <>", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeGreaterThan(String value) {
            addCriterion("DATATYPE >", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeGreaterThanOrEqualTo(String value) {
            addCriterion("DATATYPE >=", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeLessThan(String value) {
            addCriterion("DATATYPE <", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeLessThanOrEqualTo(String value) {
            addCriterion("DATATYPE <=", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeLike(String value) {
            addCriterion("DATATYPE like", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotLike(String value) {
            addCriterion("DATATYPE not like", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeIn(List<String> values) {
            addCriterion("DATATYPE in", values, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotIn(List<String> values) {
            addCriterion("DATATYPE not in", values, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeBetween(String value1, String value2) {
            addCriterion("DATATYPE between", value1, value2, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotBetween(String value1, String value2) {
            addCriterion("DATATYPE not between", value1, value2, "datatype");
            return (Criteria) this;
        }

        public Criteria andBatchcodeIsNull() {
            addCriterion("BATCHCODE is null");
            return (Criteria) this;
        }

        public Criteria andBatchcodeIsNotNull() {
            addCriterion("BATCHCODE is not null");
            return (Criteria) this;
        }

        public Criteria andBatchcodeEqualTo(String value) {
            addCriterion("BATCHCODE =", value, "batchcode");
            return (Criteria) this;
        }

        public Criteria andBatchcodeNotEqualTo(String value) {
            addCriterion("BATCHCODE <>", value, "batchcode");
            return (Criteria) this;
        }

        public Criteria andBatchcodeGreaterThan(String value) {
            addCriterion("BATCHCODE >", value, "batchcode");
            return (Criteria) this;
        }

        public Criteria andBatchcodeGreaterThanOrEqualTo(String value) {
            addCriterion("BATCHCODE >=", value, "batchcode");
            return (Criteria) this;
        }

        public Criteria andBatchcodeLessThan(String value) {
            addCriterion("BATCHCODE <", value, "batchcode");
            return (Criteria) this;
        }

        public Criteria andBatchcodeLessThanOrEqualTo(String value) {
            addCriterion("BATCHCODE <=", value, "batchcode");
            return (Criteria) this;
        }

        public Criteria andBatchcodeLike(String value) {
            addCriterion("BATCHCODE like", value, "batchcode");
            return (Criteria) this;
        }

        public Criteria andBatchcodeNotLike(String value) {
            addCriterion("BATCHCODE not like", value, "batchcode");
            return (Criteria) this;
        }

        public Criteria andBatchcodeIn(List<String> values) {
            addCriterion("BATCHCODE in", values, "batchcode");
            return (Criteria) this;
        }

        public Criteria andBatchcodeNotIn(List<String> values) {
            addCriterion("BATCHCODE not in", values, "batchcode");
            return (Criteria) this;
        }

        public Criteria andBatchcodeBetween(String value1, String value2) {
            addCriterion("BATCHCODE between", value1, value2, "batchcode");
            return (Criteria) this;
        }

        public Criteria andBatchcodeNotBetween(String value1, String value2) {
            addCriterion("BATCHCODE not between", value1, value2, "batchcode");
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

        public Criteria andDealTimeIsNull() {
            addCriterion("DEAL_TIME is null");
            return (Criteria) this;
        }

        public Criteria andDealTimeIsNotNull() {
            addCriterion("DEAL_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andDealTimeEqualTo(Date value) {
            addCriterion("DEAL_TIME =", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeNotEqualTo(Date value) {
            addCriterion("DEAL_TIME <>", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeGreaterThan(Date value) {
            addCriterion("DEAL_TIME >", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("DEAL_TIME >=", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeLessThan(Date value) {
            addCriterion("DEAL_TIME <", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeLessThanOrEqualTo(Date value) {
            addCriterion("DEAL_TIME <=", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeIn(List<Date> values) {
            addCriterion("DEAL_TIME in", values, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeNotIn(List<Date> values) {
            addCriterion("DEAL_TIME not in", values, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeBetween(Date value1, Date value2) {
            addCriterion("DEAL_TIME between", value1, value2, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeNotBetween(Date value1, Date value2) {
            addCriterion("DEAL_TIME not between", value1, value2, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealUserIsNull() {
            addCriterion("DEAL_USER is null");
            return (Criteria) this;
        }

        public Criteria andDealUserIsNotNull() {
            addCriterion("DEAL_USER is not null");
            return (Criteria) this;
        }

        public Criteria andDealUserEqualTo(String value) {
            addCriterion("DEAL_USER =", value, "dealUser");
            return (Criteria) this;
        }

        public Criteria andDealUserNotEqualTo(String value) {
            addCriterion("DEAL_USER <>", value, "dealUser");
            return (Criteria) this;
        }

        public Criteria andDealUserGreaterThan(String value) {
            addCriterion("DEAL_USER >", value, "dealUser");
            return (Criteria) this;
        }

        public Criteria andDealUserGreaterThanOrEqualTo(String value) {
            addCriterion("DEAL_USER >=", value, "dealUser");
            return (Criteria) this;
        }

        public Criteria andDealUserLessThan(String value) {
            addCriterion("DEAL_USER <", value, "dealUser");
            return (Criteria) this;
        }

        public Criteria andDealUserLessThanOrEqualTo(String value) {
            addCriterion("DEAL_USER <=", value, "dealUser");
            return (Criteria) this;
        }

        public Criteria andDealUserLike(String value) {
            addCriterion("DEAL_USER like", value, "dealUser");
            return (Criteria) this;
        }

        public Criteria andDealUserNotLike(String value) {
            addCriterion("DEAL_USER not like", value, "dealUser");
            return (Criteria) this;
        }

        public Criteria andDealUserIn(List<String> values) {
            addCriterion("DEAL_USER in", values, "dealUser");
            return (Criteria) this;
        }

        public Criteria andDealUserNotIn(List<String> values) {
            addCriterion("DEAL_USER not in", values, "dealUser");
            return (Criteria) this;
        }

        public Criteria andDealUserBetween(String value1, String value2) {
            addCriterion("DEAL_USER between", value1, value2, "dealUser");
            return (Criteria) this;
        }

        public Criteria andDealUserNotBetween(String value1, String value2) {
            addCriterion("DEAL_USER not between", value1, value2, "dealUser");
            return (Criteria) this;
        }

        public Criteria andDealstatusIsNull() {
            addCriterion("DEALSTATUS is null");
            return (Criteria) this;
        }

        public Criteria andDealstatusIsNotNull() {
            addCriterion("DEALSTATUS is not null");
            return (Criteria) this;
        }

        public Criteria andDealstatusEqualTo(BigDecimal value) {
            addCriterion("DEALSTATUS =", value, "dealstatus");
            return (Criteria) this;
        }

        public Criteria andDealstatusNotEqualTo(BigDecimal value) {
            addCriterion("DEALSTATUS <>", value, "dealstatus");
            return (Criteria) this;
        }

        public Criteria andDealstatusGreaterThan(BigDecimal value) {
            addCriterion("DEALSTATUS >", value, "dealstatus");
            return (Criteria) this;
        }

        public Criteria andDealstatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DEALSTATUS >=", value, "dealstatus");
            return (Criteria) this;
        }

        public Criteria andDealstatusLessThan(BigDecimal value) {
            addCriterion("DEALSTATUS <", value, "dealstatus");
            return (Criteria) this;
        }

        public Criteria andDealstatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DEALSTATUS <=", value, "dealstatus");
            return (Criteria) this;
        }

        public Criteria andDealstatusIn(List<BigDecimal> values) {
            addCriterion("DEALSTATUS in", values, "dealstatus");
            return (Criteria) this;
        }

        public Criteria andDealstatusNotIn(List<BigDecimal> values) {
            addCriterion("DEALSTATUS not in", values, "dealstatus");
            return (Criteria) this;
        }

        public Criteria andDealstatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEALSTATUS between", value1, value2, "dealstatus");
            return (Criteria) this;
        }

        public Criteria andDealstatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEALSTATUS not between", value1, value2, "dealstatus");
            return (Criteria) this;
        }

        public Criteria andDealmsgIsNull() {
            addCriterion("DEALMSG is null");
            return (Criteria) this;
        }

        public Criteria andDealmsgIsNotNull() {
            addCriterion("DEALMSG is not null");
            return (Criteria) this;
        }

        public Criteria andDealmsgEqualTo(String value) {
            addCriterion("DEALMSG =", value, "dealmsg");
            return (Criteria) this;
        }

        public Criteria andDealmsgNotEqualTo(String value) {
            addCriterion("DEALMSG <>", value, "dealmsg");
            return (Criteria) this;
        }

        public Criteria andDealmsgGreaterThan(String value) {
            addCriterion("DEALMSG >", value, "dealmsg");
            return (Criteria) this;
        }

        public Criteria andDealmsgGreaterThanOrEqualTo(String value) {
            addCriterion("DEALMSG >=", value, "dealmsg");
            return (Criteria) this;
        }

        public Criteria andDealmsgLessThan(String value) {
            addCriterion("DEALMSG <", value, "dealmsg");
            return (Criteria) this;
        }

        public Criteria andDealmsgLessThanOrEqualTo(String value) {
            addCriterion("DEALMSG <=", value, "dealmsg");
            return (Criteria) this;
        }

        public Criteria andDealmsgLike(String value) {
            addCriterion("DEALMSG like", value, "dealmsg");
            return (Criteria) this;
        }

        public Criteria andDealmsgNotLike(String value) {
            addCriterion("DEALMSG not like", value, "dealmsg");
            return (Criteria) this;
        }

        public Criteria andDealmsgIn(List<String> values) {
            addCriterion("DEALMSG in", values, "dealmsg");
            return (Criteria) this;
        }

        public Criteria andDealmsgNotIn(List<String> values) {
            addCriterion("DEALMSG not in", values, "dealmsg");
            return (Criteria) this;
        }

        public Criteria andDealmsgBetween(String value1, String value2) {
            addCriterion("DEALMSG between", value1, value2, "dealmsg");
            return (Criteria) this;
        }

        public Criteria andDealmsgNotBetween(String value1, String value2) {
            addCriterion("DEALMSG not between", value1, value2, "dealmsg");
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