package com.ryx.credit.activity.entity;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OAccountAdjustDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OAccountAdjustDetailExample() {
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

        public Criteria andAdjustIdIsNull() {
            addCriterion("ADJUST_ID is null");
            return (Criteria) this;
        }

        public Criteria andAdjustIdIsNotNull() {
            addCriterion("ADJUST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustIdEqualTo(String value) {
            addCriterion("ADJUST_ID =", value, "adjustId");
            return (Criteria) this;
        }

        public Criteria andAdjustIdNotEqualTo(String value) {
            addCriterion("ADJUST_ID <>", value, "adjustId");
            return (Criteria) this;
        }

        public Criteria andAdjustIdGreaterThan(String value) {
            addCriterion("ADJUST_ID >", value, "adjustId");
            return (Criteria) this;
        }

        public Criteria andAdjustIdGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_ID >=", value, "adjustId");
            return (Criteria) this;
        }

        public Criteria andAdjustIdLessThan(String value) {
            addCriterion("ADJUST_ID <", value, "adjustId");
            return (Criteria) this;
        }

        public Criteria andAdjustIdLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_ID <=", value, "adjustId");
            return (Criteria) this;
        }

        public Criteria andAdjustIdLike(String value) {
            addCriterion("ADJUST_ID like", value, "adjustId");
            return (Criteria) this;
        }

        public Criteria andAdjustIdNotLike(String value) {
            addCriterion("ADJUST_ID not like", value, "adjustId");
            return (Criteria) this;
        }

        public Criteria andAdjustIdIn(List<String> values) {
            addCriterion("ADJUST_ID in", values, "adjustId");
            return (Criteria) this;
        }

        public Criteria andAdjustIdNotIn(List<String> values) {
            addCriterion("ADJUST_ID not in", values, "adjustId");
            return (Criteria) this;
        }

        public Criteria andAdjustIdBetween(String value1, String value2) {
            addCriterion("ADJUST_ID between", value1, value2, "adjustId");
            return (Criteria) this;
        }

        public Criteria andAdjustIdNotBetween(String value1, String value2) {
            addCriterion("ADJUST_ID not between", value1, value2, "adjustId");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeIsNull() {
            addCriterion("ADJUST_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeIsNotNull() {
            addCriterion("ADJUST_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeEqualTo(String value) {
            addCriterion("ADJUST_TYPE =", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeNotEqualTo(String value) {
            addCriterion("ADJUST_TYPE <>", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeGreaterThan(String value) {
            addCriterion("ADJUST_TYPE >", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_TYPE >=", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeLessThan(String value) {
            addCriterion("ADJUST_TYPE <", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_TYPE <=", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeLike(String value) {
            addCriterion("ADJUST_TYPE like", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeNotLike(String value) {
            addCriterion("ADJUST_TYPE not like", value, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeIn(List<String> values) {
            addCriterion("ADJUST_TYPE in", values, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeNotIn(List<String> values) {
            addCriterion("ADJUST_TYPE not in", values, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeBetween(String value1, String value2) {
            addCriterion("ADJUST_TYPE between", value1, value2, "adjustType");
            return (Criteria) this;
        }

        public Criteria andAdjustTypeNotBetween(String value1, String value2) {
            addCriterion("ADJUST_TYPE not between", value1, value2, "adjustType");
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

        public Criteria andTakeOutAmountIsNull() {
            addCriterion("TAKE_OUT_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmountIsNotNull() {
            addCriterion("TAKE_OUT_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmountEqualTo(BigDecimal value) {
            addCriterion("TAKE_OUT_AMOUNT =", value, "takeOutAmount");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmountNotEqualTo(BigDecimal value) {
            addCriterion("TAKE_OUT_AMOUNT <>", value, "takeOutAmount");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmountGreaterThan(BigDecimal value) {
            addCriterion("TAKE_OUT_AMOUNT >", value, "takeOutAmount");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TAKE_OUT_AMOUNT >=", value, "takeOutAmount");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmountLessThan(BigDecimal value) {
            addCriterion("TAKE_OUT_AMOUNT <", value, "takeOutAmount");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TAKE_OUT_AMOUNT <=", value, "takeOutAmount");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmountIn(List<BigDecimal> values) {
            addCriterion("TAKE_OUT_AMOUNT in", values, "takeOutAmount");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmountNotIn(List<BigDecimal> values) {
            addCriterion("TAKE_OUT_AMOUNT not in", values, "takeOutAmount");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAKE_OUT_AMOUNT between", value1, value2, "takeOutAmount");
            return (Criteria) this;
        }

        public Criteria andTakeOutAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAKE_OUT_AMOUNT not between", value1, value2, "takeOutAmount");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("ORDER_ID like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("ORDER_ID not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("ORDER_ID in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("ORDER_ID not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
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

        public Criteria andPaymentDetailIdIsNull() {
            addCriterion("PAYMENT_DETAIL_ID is null");
            return (Criteria) this;
        }

        public Criteria andPaymentDetailIdIsNotNull() {
            addCriterion("PAYMENT_DETAIL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentDetailIdEqualTo(String value) {
            addCriterion("PAYMENT_DETAIL_ID =", value, "paymentDetailId");
            return (Criteria) this;
        }

        public Criteria andPaymentDetailIdNotEqualTo(String value) {
            addCriterion("PAYMENT_DETAIL_ID <>", value, "paymentDetailId");
            return (Criteria) this;
        }

        public Criteria andPaymentDetailIdGreaterThan(String value) {
            addCriterion("PAYMENT_DETAIL_ID >", value, "paymentDetailId");
            return (Criteria) this;
        }

        public Criteria andPaymentDetailIdGreaterThanOrEqualTo(String value) {
            addCriterion("PAYMENT_DETAIL_ID >=", value, "paymentDetailId");
            return (Criteria) this;
        }

        public Criteria andPaymentDetailIdLessThan(String value) {
            addCriterion("PAYMENT_DETAIL_ID <", value, "paymentDetailId");
            return (Criteria) this;
        }

        public Criteria andPaymentDetailIdLessThanOrEqualTo(String value) {
            addCriterion("PAYMENT_DETAIL_ID <=", value, "paymentDetailId");
            return (Criteria) this;
        }

        public Criteria andPaymentDetailIdLike(String value) {
            addCriterion("PAYMENT_DETAIL_ID like", value, "paymentDetailId");
            return (Criteria) this;
        }

        public Criteria andPaymentDetailIdNotLike(String value) {
            addCriterion("PAYMENT_DETAIL_ID not like", value, "paymentDetailId");
            return (Criteria) this;
        }

        public Criteria andPaymentDetailIdIn(List<String> values) {
            addCriterion("PAYMENT_DETAIL_ID in", values, "paymentDetailId");
            return (Criteria) this;
        }

        public Criteria andPaymentDetailIdNotIn(List<String> values) {
            addCriterion("PAYMENT_DETAIL_ID not in", values, "paymentDetailId");
            return (Criteria) this;
        }

        public Criteria andPaymentDetailIdBetween(String value1, String value2) {
            addCriterion("PAYMENT_DETAIL_ID between", value1, value2, "paymentDetailId");
            return (Criteria) this;
        }

        public Criteria andPaymentDetailIdNotBetween(String value1, String value2) {
            addCriterion("PAYMENT_DETAIL_ID not between", value1, value2, "paymentDetailId");
            return (Criteria) this;
        }

        public Criteria andBatchCodeOldIsNull() {
            addCriterion("BATCH_CODE_OLD is null");
            return (Criteria) this;
        }

        public Criteria andBatchCodeOldIsNotNull() {
            addCriterion("BATCH_CODE_OLD is not null");
            return (Criteria) this;
        }

        public Criteria andBatchCodeOldEqualTo(String value) {
            addCriterion("BATCH_CODE_OLD =", value, "batchCodeOld");
            return (Criteria) this;
        }

        public Criteria andBatchCodeOldNotEqualTo(String value) {
            addCriterion("BATCH_CODE_OLD <>", value, "batchCodeOld");
            return (Criteria) this;
        }

        public Criteria andBatchCodeOldGreaterThan(String value) {
            addCriterion("BATCH_CODE_OLD >", value, "batchCodeOld");
            return (Criteria) this;
        }

        public Criteria andBatchCodeOldGreaterThanOrEqualTo(String value) {
            addCriterion("BATCH_CODE_OLD >=", value, "batchCodeOld");
            return (Criteria) this;
        }

        public Criteria andBatchCodeOldLessThan(String value) {
            addCriterion("BATCH_CODE_OLD <", value, "batchCodeOld");
            return (Criteria) this;
        }

        public Criteria andBatchCodeOldLessThanOrEqualTo(String value) {
            addCriterion("BATCH_CODE_OLD <=", value, "batchCodeOld");
            return (Criteria) this;
        }

        public Criteria andBatchCodeOldLike(String value) {
            addCriterion("BATCH_CODE_OLD like", value, "batchCodeOld");
            return (Criteria) this;
        }

        public Criteria andBatchCodeOldNotLike(String value) {
            addCriterion("BATCH_CODE_OLD not like", value, "batchCodeOld");
            return (Criteria) this;
        }

        public Criteria andBatchCodeOldIn(List<String> values) {
            addCriterion("BATCH_CODE_OLD in", values, "batchCodeOld");
            return (Criteria) this;
        }

        public Criteria andBatchCodeOldNotIn(List<String> values) {
            addCriterion("BATCH_CODE_OLD not in", values, "batchCodeOld");
            return (Criteria) this;
        }

        public Criteria andBatchCodeOldBetween(String value1, String value2) {
            addCriterion("BATCH_CODE_OLD between", value1, value2, "batchCodeOld");
            return (Criteria) this;
        }

        public Criteria andBatchCodeOldNotBetween(String value1, String value2) {
            addCriterion("BATCH_CODE_OLD not between", value1, value2, "batchCodeOld");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNewIsNull() {
            addCriterion("BATCH_CODE_NEW is null");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNewIsNotNull() {
            addCriterion("BATCH_CODE_NEW is not null");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNewEqualTo(String value) {
            addCriterion("BATCH_CODE_NEW =", value, "batchCodeNew");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNewNotEqualTo(String value) {
            addCriterion("BATCH_CODE_NEW <>", value, "batchCodeNew");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNewGreaterThan(String value) {
            addCriterion("BATCH_CODE_NEW >", value, "batchCodeNew");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNewGreaterThanOrEqualTo(String value) {
            addCriterion("BATCH_CODE_NEW >=", value, "batchCodeNew");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNewLessThan(String value) {
            addCriterion("BATCH_CODE_NEW <", value, "batchCodeNew");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNewLessThanOrEqualTo(String value) {
            addCriterion("BATCH_CODE_NEW <=", value, "batchCodeNew");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNewLike(String value) {
            addCriterion("BATCH_CODE_NEW like", value, "batchCodeNew");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNewNotLike(String value) {
            addCriterion("BATCH_CODE_NEW not like", value, "batchCodeNew");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNewIn(List<String> values) {
            addCriterion("BATCH_CODE_NEW in", values, "batchCodeNew");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNewNotIn(List<String> values) {
            addCriterion("BATCH_CODE_NEW not in", values, "batchCodeNew");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNewBetween(String value1, String value2) {
            addCriterion("BATCH_CODE_NEW between", value1, value2, "batchCodeNew");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNewNotBetween(String value1, String value2) {
            addCriterion("BATCH_CODE_NEW not between", value1, value2, "batchCodeNew");
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