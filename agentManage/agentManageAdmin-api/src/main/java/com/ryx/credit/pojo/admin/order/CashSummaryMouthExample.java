package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CashSummaryMouthExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CashSummaryMouthExample() {
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

        public Criteria andBusidIsNull() {
            addCriterion("BUSID is null");
            return (Criteria) this;
        }

        public Criteria andBusidIsNotNull() {
            addCriterion("BUSID is not null");
            return (Criteria) this;
        }

        public Criteria andBusidEqualTo(String value) {
            addCriterion("BUSID =", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidNotEqualTo(String value) {
            addCriterion("BUSID <>", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidGreaterThan(String value) {
            addCriterion("BUSID >", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidGreaterThanOrEqualTo(String value) {
            addCriterion("BUSID >=", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidLessThan(String value) {
            addCriterion("BUSID <", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidLessThanOrEqualTo(String value) {
            addCriterion("BUSID <=", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidLike(String value) {
            addCriterion("BUSID like", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidNotLike(String value) {
            addCriterion("BUSID not like", value, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidIn(List<String> values) {
            addCriterion("BUSID in", values, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidNotIn(List<String> values) {
            addCriterion("BUSID not in", values, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidBetween(String value1, String value2) {
            addCriterion("BUSID between", value1, value2, "busid");
            return (Criteria) this;
        }

        public Criteria andBusidNotBetween(String value1, String value2) {
            addCriterion("BUSID not between", value1, value2, "busid");
            return (Criteria) this;
        }

        public Criteria andDayStrIsNull() {
            addCriterion("DAY_STR is null");
            return (Criteria) this;
        }

        public Criteria andDayStrIsNotNull() {
            addCriterion("DAY_STR is not null");
            return (Criteria) this;
        }

        public Criteria andDayStrEqualTo(String value) {
            addCriterion("DAY_STR =", value, "dayStr");
            return (Criteria) this;
        }

        public Criteria andDayStrNotEqualTo(String value) {
            addCriterion("DAY_STR <>", value, "dayStr");
            return (Criteria) this;
        }

        public Criteria andDayStrGreaterThan(String value) {
            addCriterion("DAY_STR >", value, "dayStr");
            return (Criteria) this;
        }

        public Criteria andDayStrGreaterThanOrEqualTo(String value) {
            addCriterion("DAY_STR >=", value, "dayStr");
            return (Criteria) this;
        }

        public Criteria andDayStrLessThan(String value) {
            addCriterion("DAY_STR <", value, "dayStr");
            return (Criteria) this;
        }

        public Criteria andDayStrLessThanOrEqualTo(String value) {
            addCriterion("DAY_STR <=", value, "dayStr");
            return (Criteria) this;
        }

        public Criteria andDayStrLike(String value) {
            addCriterion("DAY_STR like", value, "dayStr");
            return (Criteria) this;
        }

        public Criteria andDayStrNotLike(String value) {
            addCriterion("DAY_STR not like", value, "dayStr");
            return (Criteria) this;
        }

        public Criteria andDayStrIn(List<String> values) {
            addCriterion("DAY_STR in", values, "dayStr");
            return (Criteria) this;
        }

        public Criteria andDayStrNotIn(List<String> values) {
            addCriterion("DAY_STR not in", values, "dayStr");
            return (Criteria) this;
        }

        public Criteria andDayStrBetween(String value1, String value2) {
            addCriterion("DAY_STR between", value1, value2, "dayStr");
            return (Criteria) this;
        }

        public Criteria andDayStrNotBetween(String value1, String value2) {
            addCriterion("DAY_STR not between", value1, value2, "dayStr");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNull() {
            addCriterion("PAY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            addCriterion("PAY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(String value) {
            addCriterion("PAY_TYPE =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(String value) {
            addCriterion("PAY_TYPE <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(String value) {
            addCriterion("PAY_TYPE >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_TYPE >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(String value) {
            addCriterion("PAY_TYPE <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(String value) {
            addCriterion("PAY_TYPE <=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLike(String value) {
            addCriterion("PAY_TYPE like", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotLike(String value) {
            addCriterion("PAY_TYPE not like", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(List<String> values) {
            addCriterion("PAY_TYPE in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(List<String> values) {
            addCriterion("PAY_TYPE not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(String value1, String value2) {
            addCriterion("PAY_TYPE between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(String value1, String value2) {
            addCriterion("PAY_TYPE not between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceIsNull() {
            addCriterion("IS_CLO_INVOICE is null");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceIsNotNull() {
            addCriterion("IS_CLO_INVOICE is not null");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceEqualTo(String value) {
            addCriterion("IS_CLO_INVOICE =", value, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceNotEqualTo(String value) {
            addCriterion("IS_CLO_INVOICE <>", value, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceGreaterThan(String value) {
            addCriterion("IS_CLO_INVOICE >", value, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceGreaterThanOrEqualTo(String value) {
            addCriterion("IS_CLO_INVOICE >=", value, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceLessThan(String value) {
            addCriterion("IS_CLO_INVOICE <", value, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceLessThanOrEqualTo(String value) {
            addCriterion("IS_CLO_INVOICE <=", value, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceLike(String value) {
            addCriterion("IS_CLO_INVOICE like", value, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceNotLike(String value) {
            addCriterion("IS_CLO_INVOICE not like", value, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceIn(List<String> values) {
            addCriterion("IS_CLO_INVOICE in", values, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceNotIn(List<String> values) {
            addCriterion("IS_CLO_INVOICE not in", values, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceBetween(String value1, String value2) {
            addCriterion("IS_CLO_INVOICE between", value1, value2, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andIsCloInvoiceNotBetween(String value1, String value2) {
            addCriterion("IS_CLO_INVOICE not between", value1, value2, "isCloInvoice");
            return (Criteria) this;
        }

        public Criteria andPayAmountIsNull() {
            addCriterion("PAY_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andPayAmountIsNotNull() {
            addCriterion("PAY_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andPayAmountEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT =", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountNotEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT <>", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountGreaterThan(BigDecimal value) {
            addCriterion("PAY_AMOUNT >", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT >=", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountLessThan(BigDecimal value) {
            addCriterion("PAY_AMOUNT <", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT <=", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountIn(List<BigDecimal> values) {
            addCriterion("PAY_AMOUNT in", values, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountNotIn(List<BigDecimal> values) {
            addCriterion("PAY_AMOUNT not in", values, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_AMOUNT between", value1, value2, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_AMOUNT not between", value1, value2, "payAmount");
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

        public Criteria andAgUniqNumIsNull() {
            addCriterion("AG_UNIQ_NUM is null");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumIsNotNull() {
            addCriterion("AG_UNIQ_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumEqualTo(String value) {
            addCriterion("AG_UNIQ_NUM =", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumNotEqualTo(String value) {
            addCriterion("AG_UNIQ_NUM <>", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumGreaterThan(String value) {
            addCriterion("AG_UNIQ_NUM >", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumGreaterThanOrEqualTo(String value) {
            addCriterion("AG_UNIQ_NUM >=", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumLessThan(String value) {
            addCriterion("AG_UNIQ_NUM <", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumLessThanOrEqualTo(String value) {
            addCriterion("AG_UNIQ_NUM <=", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumLike(String value) {
            addCriterion("AG_UNIQ_NUM like", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumNotLike(String value) {
            addCriterion("AG_UNIQ_NUM not like", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumIn(List<String> values) {
            addCriterion("AG_UNIQ_NUM in", values, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumNotIn(List<String> values) {
            addCriterion("AG_UNIQ_NUM not in", values, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumBetween(String value1, String value2) {
            addCriterion("AG_UNIQ_NUM between", value1, value2, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumNotBetween(String value1, String value2) {
            addCriterion("AG_UNIQ_NUM not between", value1, value2, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andBusParentIdIsNull() {
            addCriterion("BUS_PARENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andBusParentIdIsNotNull() {
            addCriterion("BUS_PARENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBusParentIdEqualTo(String value) {
            addCriterion("BUS_PARENT_ID =", value, "busParentId");
            return (Criteria) this;
        }

        public Criteria andBusParentIdNotEqualTo(String value) {
            addCriterion("BUS_PARENT_ID <>", value, "busParentId");
            return (Criteria) this;
        }

        public Criteria andBusParentIdGreaterThan(String value) {
            addCriterion("BUS_PARENT_ID >", value, "busParentId");
            return (Criteria) this;
        }

        public Criteria andBusParentIdGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_PARENT_ID >=", value, "busParentId");
            return (Criteria) this;
        }

        public Criteria andBusParentIdLessThan(String value) {
            addCriterion("BUS_PARENT_ID <", value, "busParentId");
            return (Criteria) this;
        }

        public Criteria andBusParentIdLessThanOrEqualTo(String value) {
            addCriterion("BUS_PARENT_ID <=", value, "busParentId");
            return (Criteria) this;
        }

        public Criteria andBusParentIdLike(String value) {
            addCriterion("BUS_PARENT_ID like", value, "busParentId");
            return (Criteria) this;
        }

        public Criteria andBusParentIdNotLike(String value) {
            addCriterion("BUS_PARENT_ID not like", value, "busParentId");
            return (Criteria) this;
        }

        public Criteria andBusParentIdIn(List<String> values) {
            addCriterion("BUS_PARENT_ID in", values, "busParentId");
            return (Criteria) this;
        }

        public Criteria andBusParentIdNotIn(List<String> values) {
            addCriterion("BUS_PARENT_ID not in", values, "busParentId");
            return (Criteria) this;
        }

        public Criteria andBusParentIdBetween(String value1, String value2) {
            addCriterion("BUS_PARENT_ID between", value1, value2, "busParentId");
            return (Criteria) this;
        }

        public Criteria andBusParentIdNotBetween(String value1, String value2) {
            addCriterion("BUS_PARENT_ID not between", value1, value2, "busParentId");
            return (Criteria) this;
        }

        public Criteria andBusParentBusNumIsNull() {
            addCriterion("BUS_PARENT_BUS_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBusParentBusNumIsNotNull() {
            addCriterion("BUS_PARENT_BUS_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBusParentBusNumEqualTo(String value) {
            addCriterion("BUS_PARENT_BUS_NUM =", value, "busParentBusNum");
            return (Criteria) this;
        }

        public Criteria andBusParentBusNumNotEqualTo(String value) {
            addCriterion("BUS_PARENT_BUS_NUM <>", value, "busParentBusNum");
            return (Criteria) this;
        }

        public Criteria andBusParentBusNumGreaterThan(String value) {
            addCriterion("BUS_PARENT_BUS_NUM >", value, "busParentBusNum");
            return (Criteria) this;
        }

        public Criteria andBusParentBusNumGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_PARENT_BUS_NUM >=", value, "busParentBusNum");
            return (Criteria) this;
        }

        public Criteria andBusParentBusNumLessThan(String value) {
            addCriterion("BUS_PARENT_BUS_NUM <", value, "busParentBusNum");
            return (Criteria) this;
        }

        public Criteria andBusParentBusNumLessThanOrEqualTo(String value) {
            addCriterion("BUS_PARENT_BUS_NUM <=", value, "busParentBusNum");
            return (Criteria) this;
        }

        public Criteria andBusParentBusNumLike(String value) {
            addCriterion("BUS_PARENT_BUS_NUM like", value, "busParentBusNum");
            return (Criteria) this;
        }

        public Criteria andBusParentBusNumNotLike(String value) {
            addCriterion("BUS_PARENT_BUS_NUM not like", value, "busParentBusNum");
            return (Criteria) this;
        }

        public Criteria andBusParentBusNumIn(List<String> values) {
            addCriterion("BUS_PARENT_BUS_NUM in", values, "busParentBusNum");
            return (Criteria) this;
        }

        public Criteria andBusParentBusNumNotIn(List<String> values) {
            addCriterion("BUS_PARENT_BUS_NUM not in", values, "busParentBusNum");
            return (Criteria) this;
        }

        public Criteria andBusParentBusNumBetween(String value1, String value2) {
            addCriterion("BUS_PARENT_BUS_NUM between", value1, value2, "busParentBusNum");
            return (Criteria) this;
        }

        public Criteria andBusParentBusNumNotBetween(String value1, String value2) {
            addCriterion("BUS_PARENT_BUS_NUM not between", value1, value2, "busParentBusNum");
            return (Criteria) this;
        }

        public Criteria andBusParentAgentIdIsNull() {
            addCriterion("BUS_PARENT_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andBusParentAgentIdIsNotNull() {
            addCriterion("BUS_PARENT_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBusParentAgentIdEqualTo(String value) {
            addCriterion("BUS_PARENT_AGENT_ID =", value, "busParentAgentId");
            return (Criteria) this;
        }

        public Criteria andBusParentAgentIdNotEqualTo(String value) {
            addCriterion("BUS_PARENT_AGENT_ID <>", value, "busParentAgentId");
            return (Criteria) this;
        }

        public Criteria andBusParentAgentIdGreaterThan(String value) {
            addCriterion("BUS_PARENT_AGENT_ID >", value, "busParentAgentId");
            return (Criteria) this;
        }

        public Criteria andBusParentAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_PARENT_AGENT_ID >=", value, "busParentAgentId");
            return (Criteria) this;
        }

        public Criteria andBusParentAgentIdLessThan(String value) {
            addCriterion("BUS_PARENT_AGENT_ID <", value, "busParentAgentId");
            return (Criteria) this;
        }

        public Criteria andBusParentAgentIdLessThanOrEqualTo(String value) {
            addCriterion("BUS_PARENT_AGENT_ID <=", value, "busParentAgentId");
            return (Criteria) this;
        }

        public Criteria andBusParentAgentIdLike(String value) {
            addCriterion("BUS_PARENT_AGENT_ID like", value, "busParentAgentId");
            return (Criteria) this;
        }

        public Criteria andBusParentAgentIdNotLike(String value) {
            addCriterion("BUS_PARENT_AGENT_ID not like", value, "busParentAgentId");
            return (Criteria) this;
        }

        public Criteria andBusParentAgentIdIn(List<String> values) {
            addCriterion("BUS_PARENT_AGENT_ID in", values, "busParentAgentId");
            return (Criteria) this;
        }

        public Criteria andBusParentAgentIdNotIn(List<String> values) {
            addCriterion("BUS_PARENT_AGENT_ID not in", values, "busParentAgentId");
            return (Criteria) this;
        }

        public Criteria andBusParentAgentIdBetween(String value1, String value2) {
            addCriterion("BUS_PARENT_AGENT_ID between", value1, value2, "busParentAgentId");
            return (Criteria) this;
        }

        public Criteria andBusParentAgentIdNotBetween(String value1, String value2) {
            addCriterion("BUS_PARENT_AGENT_ID not between", value1, value2, "busParentAgentId");
            return (Criteria) this;
        }

        public Criteria andCDateIsNull() {
            addCriterion("C_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCDateIsNotNull() {
            addCriterion("C_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCDateEqualTo(Date value) {
            addCriterion("C_DATE =", value, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateNotEqualTo(Date value) {
            addCriterion("C_DATE <>", value, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateGreaterThan(Date value) {
            addCriterion("C_DATE >", value, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateGreaterThanOrEqualTo(Date value) {
            addCriterion("C_DATE >=", value, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateLessThan(Date value) {
            addCriterion("C_DATE <", value, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateLessThanOrEqualTo(Date value) {
            addCriterion("C_DATE <=", value, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateIn(List<Date> values) {
            addCriterion("C_DATE in", values, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateNotIn(List<Date> values) {
            addCriterion("C_DATE not in", values, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateBetween(Date value1, Date value2) {
            addCriterion("C_DATE between", value1, value2, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateNotBetween(Date value1, Date value2) {
            addCriterion("C_DATE not between", value1, value2, "cDate");
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