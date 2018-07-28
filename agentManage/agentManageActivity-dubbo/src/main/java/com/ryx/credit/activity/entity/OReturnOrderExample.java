package com.ryx.credit.activity.entity;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OReturnOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OReturnOrderExample() {
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

        public Criteria andApplyRemarkIsNull() {
            addCriterion("APPLY_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkIsNotNull() {
            addCriterion("APPLY_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkEqualTo(String value) {
            addCriterion("APPLY_REMARK =", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkNotEqualTo(String value) {
            addCriterion("APPLY_REMARK <>", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkGreaterThan(String value) {
            addCriterion("APPLY_REMARK >", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("APPLY_REMARK >=", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkLessThan(String value) {
            addCriterion("APPLY_REMARK <", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkLessThanOrEqualTo(String value) {
            addCriterion("APPLY_REMARK <=", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkLike(String value) {
            addCriterion("APPLY_REMARK like", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkNotLike(String value) {
            addCriterion("APPLY_REMARK not like", value, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkIn(List<String> values) {
            addCriterion("APPLY_REMARK in", values, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkNotIn(List<String> values) {
            addCriterion("APPLY_REMARK not in", values, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkBetween(String value1, String value2) {
            addCriterion("APPLY_REMARK between", value1, value2, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andApplyRemarkNotBetween(String value1, String value2) {
            addCriterion("APPLY_REMARK not between", value1, value2, "applyRemark");
            return (Criteria) this;
        }

        public Criteria andRetInvoiceIsNull() {
            addCriterion("RET_INVOICE is null");
            return (Criteria) this;
        }

        public Criteria andRetInvoiceIsNotNull() {
            addCriterion("RET_INVOICE is not null");
            return (Criteria) this;
        }

        public Criteria andRetInvoiceEqualTo(BigDecimal value) {
            addCriterion("RET_INVOICE =", value, "retInvoice");
            return (Criteria) this;
        }

        public Criteria andRetInvoiceNotEqualTo(BigDecimal value) {
            addCriterion("RET_INVOICE <>", value, "retInvoice");
            return (Criteria) this;
        }

        public Criteria andRetInvoiceGreaterThan(BigDecimal value) {
            addCriterion("RET_INVOICE >", value, "retInvoice");
            return (Criteria) this;
        }

        public Criteria andRetInvoiceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RET_INVOICE >=", value, "retInvoice");
            return (Criteria) this;
        }

        public Criteria andRetInvoiceLessThan(BigDecimal value) {
            addCriterion("RET_INVOICE <", value, "retInvoice");
            return (Criteria) this;
        }

        public Criteria andRetInvoiceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RET_INVOICE <=", value, "retInvoice");
            return (Criteria) this;
        }

        public Criteria andRetInvoiceIn(List<BigDecimal> values) {
            addCriterion("RET_INVOICE in", values, "retInvoice");
            return (Criteria) this;
        }

        public Criteria andRetInvoiceNotIn(List<BigDecimal> values) {
            addCriterion("RET_INVOICE not in", values, "retInvoice");
            return (Criteria) this;
        }

        public Criteria andRetInvoiceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RET_INVOICE between", value1, value2, "retInvoice");
            return (Criteria) this;
        }

        public Criteria andRetInvoiceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RET_INVOICE not between", value1, value2, "retInvoice");
            return (Criteria) this;
        }

        public Criteria andRetReceiptIsNull() {
            addCriterion("RET_RECEIPT is null");
            return (Criteria) this;
        }

        public Criteria andRetReceiptIsNotNull() {
            addCriterion("RET_RECEIPT is not null");
            return (Criteria) this;
        }

        public Criteria andRetReceiptEqualTo(BigDecimal value) {
            addCriterion("RET_RECEIPT =", value, "retReceipt");
            return (Criteria) this;
        }

        public Criteria andRetReceiptNotEqualTo(BigDecimal value) {
            addCriterion("RET_RECEIPT <>", value, "retReceipt");
            return (Criteria) this;
        }

        public Criteria andRetReceiptGreaterThan(BigDecimal value) {
            addCriterion("RET_RECEIPT >", value, "retReceipt");
            return (Criteria) this;
        }

        public Criteria andRetReceiptGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RET_RECEIPT >=", value, "retReceipt");
            return (Criteria) this;
        }

        public Criteria andRetReceiptLessThan(BigDecimal value) {
            addCriterion("RET_RECEIPT <", value, "retReceipt");
            return (Criteria) this;
        }

        public Criteria andRetReceiptLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RET_RECEIPT <=", value, "retReceipt");
            return (Criteria) this;
        }

        public Criteria andRetReceiptIn(List<BigDecimal> values) {
            addCriterion("RET_RECEIPT in", values, "retReceipt");
            return (Criteria) this;
        }

        public Criteria andRetReceiptNotIn(List<BigDecimal> values) {
            addCriterion("RET_RECEIPT not in", values, "retReceipt");
            return (Criteria) this;
        }

        public Criteria andRetReceiptBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RET_RECEIPT between", value1, value2, "retReceipt");
            return (Criteria) this;
        }

        public Criteria andRetReceiptNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RET_RECEIPT not between", value1, value2, "retReceipt");
            return (Criteria) this;
        }

        public Criteria andRetScheduleIsNull() {
            addCriterion("RET_SCHEDULE is null");
            return (Criteria) this;
        }

        public Criteria andRetScheduleIsNotNull() {
            addCriterion("RET_SCHEDULE is not null");
            return (Criteria) this;
        }

        public Criteria andRetScheduleEqualTo(BigDecimal value) {
            addCriterion("RET_SCHEDULE =", value, "retSchedule");
            return (Criteria) this;
        }

        public Criteria andRetScheduleNotEqualTo(BigDecimal value) {
            addCriterion("RET_SCHEDULE <>", value, "retSchedule");
            return (Criteria) this;
        }

        public Criteria andRetScheduleGreaterThan(BigDecimal value) {
            addCriterion("RET_SCHEDULE >", value, "retSchedule");
            return (Criteria) this;
        }

        public Criteria andRetScheduleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RET_SCHEDULE >=", value, "retSchedule");
            return (Criteria) this;
        }

        public Criteria andRetScheduleLessThan(BigDecimal value) {
            addCriterion("RET_SCHEDULE <", value, "retSchedule");
            return (Criteria) this;
        }

        public Criteria andRetScheduleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RET_SCHEDULE <=", value, "retSchedule");
            return (Criteria) this;
        }

        public Criteria andRetScheduleIn(List<BigDecimal> values) {
            addCriterion("RET_SCHEDULE in", values, "retSchedule");
            return (Criteria) this;
        }

        public Criteria andRetScheduleNotIn(List<BigDecimal> values) {
            addCriterion("RET_SCHEDULE not in", values, "retSchedule");
            return (Criteria) this;
        }

        public Criteria andRetScheduleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RET_SCHEDULE between", value1, value2, "retSchedule");
            return (Criteria) this;
        }

        public Criteria andRetScheduleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RET_SCHEDULE not between", value1, value2, "retSchedule");
            return (Criteria) this;
        }

        public Criteria andReturnAmoIsNull() {
            addCriterion("RETURN_AMO is null");
            return (Criteria) this;
        }

        public Criteria andReturnAmoIsNotNull() {
            addCriterion("RETURN_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andReturnAmoEqualTo(BigDecimal value) {
            addCriterion("RETURN_AMO =", value, "returnAmo");
            return (Criteria) this;
        }

        public Criteria andReturnAmoNotEqualTo(BigDecimal value) {
            addCriterion("RETURN_AMO <>", value, "returnAmo");
            return (Criteria) this;
        }

        public Criteria andReturnAmoGreaterThan(BigDecimal value) {
            addCriterion("RETURN_AMO >", value, "returnAmo");
            return (Criteria) this;
        }

        public Criteria andReturnAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RETURN_AMO >=", value, "returnAmo");
            return (Criteria) this;
        }

        public Criteria andReturnAmoLessThan(BigDecimal value) {
            addCriterion("RETURN_AMO <", value, "returnAmo");
            return (Criteria) this;
        }

        public Criteria andReturnAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RETURN_AMO <=", value, "returnAmo");
            return (Criteria) this;
        }

        public Criteria andReturnAmoIn(List<BigDecimal> values) {
            addCriterion("RETURN_AMO in", values, "returnAmo");
            return (Criteria) this;
        }

        public Criteria andReturnAmoNotIn(List<BigDecimal> values) {
            addCriterion("RETURN_AMO not in", values, "returnAmo");
            return (Criteria) this;
        }

        public Criteria andReturnAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RETURN_AMO between", value1, value2, "returnAmo");
            return (Criteria) this;
        }

        public Criteria andReturnAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RETURN_AMO not between", value1, value2, "returnAmo");
            return (Criteria) this;
        }

        public Criteria andGoodsReturnAmoIsNull() {
            addCriterion("GOODS_RETURN_AMO is null");
            return (Criteria) this;
        }

        public Criteria andGoodsReturnAmoIsNotNull() {
            addCriterion("GOODS_RETURN_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsReturnAmoEqualTo(BigDecimal value) {
            addCriterion("GOODS_RETURN_AMO =", value, "goodsReturnAmo");
            return (Criteria) this;
        }

        public Criteria andGoodsReturnAmoNotEqualTo(BigDecimal value) {
            addCriterion("GOODS_RETURN_AMO <>", value, "goodsReturnAmo");
            return (Criteria) this;
        }

        public Criteria andGoodsReturnAmoGreaterThan(BigDecimal value) {
            addCriterion("GOODS_RETURN_AMO >", value, "goodsReturnAmo");
            return (Criteria) this;
        }

        public Criteria andGoodsReturnAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("GOODS_RETURN_AMO >=", value, "goodsReturnAmo");
            return (Criteria) this;
        }

        public Criteria andGoodsReturnAmoLessThan(BigDecimal value) {
            addCriterion("GOODS_RETURN_AMO <", value, "goodsReturnAmo");
            return (Criteria) this;
        }

        public Criteria andGoodsReturnAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("GOODS_RETURN_AMO <=", value, "goodsReturnAmo");
            return (Criteria) this;
        }

        public Criteria andGoodsReturnAmoIn(List<BigDecimal> values) {
            addCriterion("GOODS_RETURN_AMO in", values, "goodsReturnAmo");
            return (Criteria) this;
        }

        public Criteria andGoodsReturnAmoNotIn(List<BigDecimal> values) {
            addCriterion("GOODS_RETURN_AMO not in", values, "goodsReturnAmo");
            return (Criteria) this;
        }

        public Criteria andGoodsReturnAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("GOODS_RETURN_AMO between", value1, value2, "goodsReturnAmo");
            return (Criteria) this;
        }

        public Criteria andGoodsReturnAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("GOODS_RETURN_AMO not between", value1, value2, "goodsReturnAmo");
            return (Criteria) this;
        }

        public Criteria andCutAmoIsNull() {
            addCriterion("CUT_AMO is null");
            return (Criteria) this;
        }

        public Criteria andCutAmoIsNotNull() {
            addCriterion("CUT_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andCutAmoEqualTo(BigDecimal value) {
            addCriterion("CUT_AMO =", value, "cutAmo");
            return (Criteria) this;
        }

        public Criteria andCutAmoNotEqualTo(BigDecimal value) {
            addCriterion("CUT_AMO <>", value, "cutAmo");
            return (Criteria) this;
        }

        public Criteria andCutAmoGreaterThan(BigDecimal value) {
            addCriterion("CUT_AMO >", value, "cutAmo");
            return (Criteria) this;
        }

        public Criteria andCutAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CUT_AMO >=", value, "cutAmo");
            return (Criteria) this;
        }

        public Criteria andCutAmoLessThan(BigDecimal value) {
            addCriterion("CUT_AMO <", value, "cutAmo");
            return (Criteria) this;
        }

        public Criteria andCutAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CUT_AMO <=", value, "cutAmo");
            return (Criteria) this;
        }

        public Criteria andCutAmoIn(List<BigDecimal> values) {
            addCriterion("CUT_AMO in", values, "cutAmo");
            return (Criteria) this;
        }

        public Criteria andCutAmoNotIn(List<BigDecimal> values) {
            addCriterion("CUT_AMO not in", values, "cutAmo");
            return (Criteria) this;
        }

        public Criteria andCutAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CUT_AMO between", value1, value2, "cutAmo");
            return (Criteria) this;
        }

        public Criteria andCutAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CUT_AMO not between", value1, value2, "cutAmo");
            return (Criteria) this;
        }

        public Criteria andRelReturnAmoIsNull() {
            addCriterion("REL_RETURN_AMO is null");
            return (Criteria) this;
        }

        public Criteria andRelReturnAmoIsNotNull() {
            addCriterion("REL_RETURN_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andRelReturnAmoEqualTo(BigDecimal value) {
            addCriterion("REL_RETURN_AMO =", value, "relReturnAmo");
            return (Criteria) this;
        }

        public Criteria andRelReturnAmoNotEqualTo(BigDecimal value) {
            addCriterion("REL_RETURN_AMO <>", value, "relReturnAmo");
            return (Criteria) this;
        }

        public Criteria andRelReturnAmoGreaterThan(BigDecimal value) {
            addCriterion("REL_RETURN_AMO >", value, "relReturnAmo");
            return (Criteria) this;
        }

        public Criteria andRelReturnAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REL_RETURN_AMO >=", value, "relReturnAmo");
            return (Criteria) this;
        }

        public Criteria andRelReturnAmoLessThan(BigDecimal value) {
            addCriterion("REL_RETURN_AMO <", value, "relReturnAmo");
            return (Criteria) this;
        }

        public Criteria andRelReturnAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REL_RETURN_AMO <=", value, "relReturnAmo");
            return (Criteria) this;
        }

        public Criteria andRelReturnAmoIn(List<BigDecimal> values) {
            addCriterion("REL_RETURN_AMO in", values, "relReturnAmo");
            return (Criteria) this;
        }

        public Criteria andRelReturnAmoNotIn(List<BigDecimal> values) {
            addCriterion("REL_RETURN_AMO not in", values, "relReturnAmo");
            return (Criteria) this;
        }

        public Criteria andRelReturnAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REL_RETURN_AMO between", value1, value2, "relReturnAmo");
            return (Criteria) this;
        }

        public Criteria andRelReturnAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REL_RETURN_AMO not between", value1, value2, "relReturnAmo");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmoIsNull() {
            addCriterion("TAKE_OUT_AMO is null");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmoIsNotNull() {
            addCriterion("TAKE_OUT_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmoEqualTo(BigDecimal value) {
            addCriterion("TAKE_OUT_AMO =", value, "takeOutAmo");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmoNotEqualTo(BigDecimal value) {
            addCriterion("TAKE_OUT_AMO <>", value, "takeOutAmo");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmoGreaterThan(BigDecimal value) {
            addCriterion("TAKE_OUT_AMO >", value, "takeOutAmo");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TAKE_OUT_AMO >=", value, "takeOutAmo");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmoLessThan(BigDecimal value) {
            addCriterion("TAKE_OUT_AMO <", value, "takeOutAmo");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TAKE_OUT_AMO <=", value, "takeOutAmo");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmoIn(List<BigDecimal> values) {
            addCriterion("TAKE_OUT_AMO in", values, "takeOutAmo");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmoNotIn(List<BigDecimal> values) {
            addCriterion("TAKE_OUT_AMO not in", values, "takeOutAmo");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAKE_OUT_AMO between", value1, value2, "takeOutAmo");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAKE_OUT_AMO not between", value1, value2, "takeOutAmo");
            return (Criteria) this;
        }

        public Criteria andReturnAddressIsNull() {
            addCriterion("RETURN_ADDRESS is null");
            return (Criteria) this;
        }

        public Criteria andReturnAddressIsNotNull() {
            addCriterion("RETURN_ADDRESS is not null");
            return (Criteria) this;
        }

        public Criteria andReturnAddressEqualTo(String value) {
            addCriterion("RETURN_ADDRESS =", value, "returnAddress");
            return (Criteria) this;
        }

        public Criteria andReturnAddressNotEqualTo(String value) {
            addCriterion("RETURN_ADDRESS <>", value, "returnAddress");
            return (Criteria) this;
        }

        public Criteria andReturnAddressGreaterThan(String value) {
            addCriterion("RETURN_ADDRESS >", value, "returnAddress");
            return (Criteria) this;
        }

        public Criteria andReturnAddressGreaterThanOrEqualTo(String value) {
            addCriterion("RETURN_ADDRESS >=", value, "returnAddress");
            return (Criteria) this;
        }

        public Criteria andReturnAddressLessThan(String value) {
            addCriterion("RETURN_ADDRESS <", value, "returnAddress");
            return (Criteria) this;
        }

        public Criteria andReturnAddressLessThanOrEqualTo(String value) {
            addCriterion("RETURN_ADDRESS <=", value, "returnAddress");
            return (Criteria) this;
        }

        public Criteria andReturnAddressLike(String value) {
            addCriterion("RETURN_ADDRESS like", value, "returnAddress");
            return (Criteria) this;
        }

        public Criteria andReturnAddressNotLike(String value) {
            addCriterion("RETURN_ADDRESS not like", value, "returnAddress");
            return (Criteria) this;
        }

        public Criteria andReturnAddressIn(List<String> values) {
            addCriterion("RETURN_ADDRESS in", values, "returnAddress");
            return (Criteria) this;
        }

        public Criteria andReturnAddressNotIn(List<String> values) {
            addCriterion("RETURN_ADDRESS not in", values, "returnAddress");
            return (Criteria) this;
        }

        public Criteria andReturnAddressBetween(String value1, String value2) {
            addCriterion("RETURN_ADDRESS between", value1, value2, "returnAddress");
            return (Criteria) this;
        }

        public Criteria andReturnAddressNotBetween(String value1, String value2) {
            addCriterion("RETURN_ADDRESS not between", value1, value2, "returnAddress");
            return (Criteria) this;
        }

        public Criteria andRetTimeIsNull() {
            addCriterion("RET_TIME is null");
            return (Criteria) this;
        }

        public Criteria andRetTimeIsNotNull() {
            addCriterion("RET_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andRetTimeEqualTo(Date value) {
            addCriterion("RET_TIME =", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeNotEqualTo(Date value) {
            addCriterion("RET_TIME <>", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeGreaterThan(Date value) {
            addCriterion("RET_TIME >", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("RET_TIME >=", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeLessThan(Date value) {
            addCriterion("RET_TIME <", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeLessThanOrEqualTo(Date value) {
            addCriterion("RET_TIME <=", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeIn(List<Date> values) {
            addCriterion("RET_TIME in", values, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeNotIn(List<Date> values) {
            addCriterion("RET_TIME not in", values, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeBetween(Date value1, Date value2) {
            addCriterion("RET_TIME between", value1, value2, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeNotBetween(Date value1, Date value2) {
            addCriterion("RET_TIME not between", value1, value2, "retTime");
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

        public Criteria andBatchCodeIsNull() {
            addCriterion("BATCH_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBatchCodeIsNotNull() {
            addCriterion("BATCH_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBatchCodeEqualTo(String value) {
            addCriterion("BATCH_CODE =", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNotEqualTo(String value) {
            addCriterion("BATCH_CODE <>", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeGreaterThan(String value) {
            addCriterion("BATCH_CODE >", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BATCH_CODE >=", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeLessThan(String value) {
            addCriterion("BATCH_CODE <", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeLessThanOrEqualTo(String value) {
            addCriterion("BATCH_CODE <=", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeLike(String value) {
            addCriterion("BATCH_CODE like", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNotLike(String value) {
            addCriterion("BATCH_CODE not like", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeIn(List<String> values) {
            addCriterion("BATCH_CODE in", values, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNotIn(List<String> values) {
            addCriterion("BATCH_CODE not in", values, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeBetween(String value1, String value2) {
            addCriterion("BATCH_CODE between", value1, value2, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNotBetween(String value1, String value2) {
            addCriterion("BATCH_CODE not between", value1, value2, "batchCode");
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