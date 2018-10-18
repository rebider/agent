package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OLogisticsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OLogisticsExample() {
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

        public Criteria andReceiptPlanIdIsNull() {
            addCriterion("RECEIPT_PLAN_ID is null");
            return (Criteria) this;
        }

        public Criteria andReceiptPlanIdIsNotNull() {
            addCriterion("RECEIPT_PLAN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andReceiptPlanIdEqualTo(String value) {
            addCriterion("RECEIPT_PLAN_ID =", value, "receiptPlanId");
            return (Criteria) this;
        }

        public Criteria andReceiptPlanIdNotEqualTo(String value) {
            addCriterion("RECEIPT_PLAN_ID <>", value, "receiptPlanId");
            return (Criteria) this;
        }

        public Criteria andReceiptPlanIdGreaterThan(String value) {
            addCriterion("RECEIPT_PLAN_ID >", value, "receiptPlanId");
            return (Criteria) this;
        }

        public Criteria andReceiptPlanIdGreaterThanOrEqualTo(String value) {
            addCriterion("RECEIPT_PLAN_ID >=", value, "receiptPlanId");
            return (Criteria) this;
        }

        public Criteria andReceiptPlanIdLessThan(String value) {
            addCriterion("RECEIPT_PLAN_ID <", value, "receiptPlanId");
            return (Criteria) this;
        }

        public Criteria andReceiptPlanIdLessThanOrEqualTo(String value) {
            addCriterion("RECEIPT_PLAN_ID <=", value, "receiptPlanId");
            return (Criteria) this;
        }

        public Criteria andReceiptPlanIdLike(String value) {
            addCriterion("RECEIPT_PLAN_ID like", value, "receiptPlanId");
            return (Criteria) this;
        }

        public Criteria andReceiptPlanIdNotLike(String value) {
            addCriterion("RECEIPT_PLAN_ID not like", value, "receiptPlanId");
            return (Criteria) this;
        }

        public Criteria andReceiptPlanIdIn(List<String> values) {
            addCriterion("RECEIPT_PLAN_ID in", values, "receiptPlanId");
            return (Criteria) this;
        }

        public Criteria andReceiptPlanIdNotIn(List<String> values) {
            addCriterion("RECEIPT_PLAN_ID not in", values, "receiptPlanId");
            return (Criteria) this;
        }

        public Criteria andReceiptPlanIdBetween(String value1, String value2) {
            addCriterion("RECEIPT_PLAN_ID between", value1, value2, "receiptPlanId");
            return (Criteria) this;
        }

        public Criteria andReceiptPlanIdNotBetween(String value1, String value2) {
            addCriterion("RECEIPT_PLAN_ID not between", value1, value2, "receiptPlanId");
            return (Criteria) this;
        }

        public Criteria andProComIsNull() {
            addCriterion("PRO_COM is null");
            return (Criteria) this;
        }

        public Criteria andProComIsNotNull() {
            addCriterion("PRO_COM is not null");
            return (Criteria) this;
        }

        public Criteria andProComEqualTo(String value) {
            addCriterion("PRO_COM =", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComNotEqualTo(String value) {
            addCriterion("PRO_COM <>", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComGreaterThan(String value) {
            addCriterion("PRO_COM >", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComGreaterThanOrEqualTo(String value) {
            addCriterion("PRO_COM >=", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComLessThan(String value) {
            addCriterion("PRO_COM <", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComLessThanOrEqualTo(String value) {
            addCriterion("PRO_COM <=", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComLike(String value) {
            addCriterion("PRO_COM like", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComNotLike(String value) {
            addCriterion("PRO_COM not like", value, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComIn(List<String> values) {
            addCriterion("PRO_COM in", values, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComNotIn(List<String> values) {
            addCriterion("PRO_COM not in", values, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComBetween(String value1, String value2) {
            addCriterion("PRO_COM between", value1, value2, "proCom");
            return (Criteria) this;
        }

        public Criteria andProComNotBetween(String value1, String value2) {
            addCriterion("PRO_COM not between", value1, value2, "proCom");
            return (Criteria) this;
        }

        public Criteria andProIdIsNull() {
            addCriterion("PRO_ID is null");
            return (Criteria) this;
        }

        public Criteria andProIdIsNotNull() {
            addCriterion("PRO_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProIdEqualTo(String value) {
            addCriterion("PRO_ID =", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdNotEqualTo(String value) {
            addCriterion("PRO_ID <>", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdGreaterThan(String value) {
            addCriterion("PRO_ID >", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdGreaterThanOrEqualTo(String value) {
            addCriterion("PRO_ID >=", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdLessThan(String value) {
            addCriterion("PRO_ID <", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdLessThanOrEqualTo(String value) {
            addCriterion("PRO_ID <=", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdLike(String value) {
            addCriterion("PRO_ID like", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdNotLike(String value) {
            addCriterion("PRO_ID not like", value, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdIn(List<String> values) {
            addCriterion("PRO_ID in", values, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdNotIn(List<String> values) {
            addCriterion("PRO_ID not in", values, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdBetween(String value1, String value2) {
            addCriterion("PRO_ID between", value1, value2, "proId");
            return (Criteria) this;
        }

        public Criteria andProIdNotBetween(String value1, String value2) {
            addCriterion("PRO_ID not between", value1, value2, "proId");
            return (Criteria) this;
        }

        public Criteria andProNameIsNull() {
            addCriterion("PRO_NAME is null");
            return (Criteria) this;
        }

        public Criteria andProNameIsNotNull() {
            addCriterion("PRO_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andProNameEqualTo(String value) {
            addCriterion("PRO_NAME =", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotEqualTo(String value) {
            addCriterion("PRO_NAME <>", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameGreaterThan(String value) {
            addCriterion("PRO_NAME >", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameGreaterThanOrEqualTo(String value) {
            addCriterion("PRO_NAME >=", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameLessThan(String value) {
            addCriterion("PRO_NAME <", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameLessThanOrEqualTo(String value) {
            addCriterion("PRO_NAME <=", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameLike(String value) {
            addCriterion("PRO_NAME like", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotLike(String value) {
            addCriterion("PRO_NAME not like", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameIn(List<String> values) {
            addCriterion("PRO_NAME in", values, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotIn(List<String> values) {
            addCriterion("PRO_NAME not in", values, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameBetween(String value1, String value2) {
            addCriterion("PRO_NAME between", value1, value2, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotBetween(String value1, String value2) {
            addCriterion("PRO_NAME not between", value1, value2, "proName");
            return (Criteria) this;
        }

        public Criteria andProTypeIsNull() {
            addCriterion("PRO_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andProTypeIsNotNull() {
            addCriterion("PRO_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andProTypeEqualTo(String value) {
            addCriterion("PRO_TYPE =", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeNotEqualTo(String value) {
            addCriterion("PRO_TYPE <>", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeGreaterThan(String value) {
            addCriterion("PRO_TYPE >", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PRO_TYPE >=", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeLessThan(String value) {
            addCriterion("PRO_TYPE <", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeLessThanOrEqualTo(String value) {
            addCriterion("PRO_TYPE <=", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeLike(String value) {
            addCriterion("PRO_TYPE like", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeNotLike(String value) {
            addCriterion("PRO_TYPE not like", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeIn(List<String> values) {
            addCriterion("PRO_TYPE in", values, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeNotIn(List<String> values) {
            addCriterion("PRO_TYPE not in", values, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeBetween(String value1, String value2) {
            addCriterion("PRO_TYPE between", value1, value2, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeNotBetween(String value1, String value2) {
            addCriterion("PRO_TYPE not between", value1, value2, "proType");
            return (Criteria) this;
        }

        public Criteria andSendNumIsNull() {
            addCriterion("SEND_NUM is null");
            return (Criteria) this;
        }

        public Criteria andSendNumIsNotNull() {
            addCriterion("SEND_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andSendNumEqualTo(BigDecimal value) {
            addCriterion("SEND_NUM =", value, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumNotEqualTo(BigDecimal value) {
            addCriterion("SEND_NUM <>", value, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumGreaterThan(BigDecimal value) {
            addCriterion("SEND_NUM >", value, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SEND_NUM >=", value, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumLessThan(BigDecimal value) {
            addCriterion("SEND_NUM <", value, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SEND_NUM <=", value, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumIn(List<BigDecimal> values) {
            addCriterion("SEND_NUM in", values, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumNotIn(List<BigDecimal> values) {
            addCriterion("SEND_NUM not in", values, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SEND_NUM between", value1, value2, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SEND_NUM not between", value1, value2, "sendNum");
            return (Criteria) this;
        }

        public Criteria andProPriceIsNull() {
            addCriterion("PRO_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andProPriceIsNotNull() {
            addCriterion("PRO_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andProPriceEqualTo(BigDecimal value) {
            addCriterion("PRO_PRICE =", value, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceNotEqualTo(BigDecimal value) {
            addCriterion("PRO_PRICE <>", value, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceGreaterThan(BigDecimal value) {
            addCriterion("PRO_PRICE >", value, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRO_PRICE >=", value, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceLessThan(BigDecimal value) {
            addCriterion("PRO_PRICE <", value, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRO_PRICE <=", value, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceIn(List<BigDecimal> values) {
            addCriterion("PRO_PRICE in", values, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceNotIn(List<BigDecimal> values) {
            addCriterion("PRO_PRICE not in", values, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRO_PRICE between", value1, value2, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRO_PRICE not between", value1, value2, "proPrice");
            return (Criteria) this;
        }

        public Criteria andProModelIsNull() {
            addCriterion("PRO_MODEL is null");
            return (Criteria) this;
        }

        public Criteria andProModelIsNotNull() {
            addCriterion("PRO_MODEL is not null");
            return (Criteria) this;
        }

        public Criteria andProModelEqualTo(String value) {
            addCriterion("PRO_MODEL =", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelNotEqualTo(String value) {
            addCriterion("PRO_MODEL <>", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelGreaterThan(String value) {
            addCriterion("PRO_MODEL >", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelGreaterThanOrEqualTo(String value) {
            addCriterion("PRO_MODEL >=", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelLessThan(String value) {
            addCriterion("PRO_MODEL <", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelLessThanOrEqualTo(String value) {
            addCriterion("PRO_MODEL <=", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelLike(String value) {
            addCriterion("PRO_MODEL like", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelNotLike(String value) {
            addCriterion("PRO_MODEL not like", value, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelIn(List<String> values) {
            addCriterion("PRO_MODEL in", values, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelNotIn(List<String> values) {
            addCriterion("PRO_MODEL not in", values, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelBetween(String value1, String value2) {
            addCriterion("PRO_MODEL between", value1, value2, "proModel");
            return (Criteria) this;
        }

        public Criteria andProModelNotBetween(String value1, String value2) {
            addCriterion("PRO_MODEL not between", value1, value2, "proModel");
            return (Criteria) this;
        }

        public Criteria andSendDateIsNull() {
            addCriterion("SEND_DATE is null");
            return (Criteria) this;
        }

        public Criteria andSendDateIsNotNull() {
            addCriterion("SEND_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andSendDateEqualTo(Date value) {
            addCriterion("SEND_DATE =", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotEqualTo(Date value) {
            addCriterion("SEND_DATE <>", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateGreaterThan(Date value) {
            addCriterion("SEND_DATE >", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateGreaterThanOrEqualTo(Date value) {
            addCriterion("SEND_DATE >=", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateLessThan(Date value) {
            addCriterion("SEND_DATE <", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateLessThanOrEqualTo(Date value) {
            addCriterion("SEND_DATE <=", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateIn(List<Date> values) {
            addCriterion("SEND_DATE in", values, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotIn(List<Date> values) {
            addCriterion("SEND_DATE not in", values, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateBetween(Date value1, Date value2) {
            addCriterion("SEND_DATE between", value1, value2, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotBetween(Date value1, Date value2) {
            addCriterion("SEND_DATE not between", value1, value2, "sendDate");
            return (Criteria) this;
        }

        public Criteria andLogComIsNull() {
            addCriterion("LOG_COM is null");
            return (Criteria) this;
        }

        public Criteria andLogComIsNotNull() {
            addCriterion("LOG_COM is not null");
            return (Criteria) this;
        }

        public Criteria andLogComEqualTo(String value) {
            addCriterion("LOG_COM =", value, "logCom");
            return (Criteria) this;
        }

        public Criteria andLogComNotEqualTo(String value) {
            addCriterion("LOG_COM <>", value, "logCom");
            return (Criteria) this;
        }

        public Criteria andLogComGreaterThan(String value) {
            addCriterion("LOG_COM >", value, "logCom");
            return (Criteria) this;
        }

        public Criteria andLogComGreaterThanOrEqualTo(String value) {
            addCriterion("LOG_COM >=", value, "logCom");
            return (Criteria) this;
        }

        public Criteria andLogComLessThan(String value) {
            addCriterion("LOG_COM <", value, "logCom");
            return (Criteria) this;
        }

        public Criteria andLogComLessThanOrEqualTo(String value) {
            addCriterion("LOG_COM <=", value, "logCom");
            return (Criteria) this;
        }

        public Criteria andLogComLike(String value) {
            addCriterion("LOG_COM like", value, "logCom");
            return (Criteria) this;
        }

        public Criteria andLogComNotLike(String value) {
            addCriterion("LOG_COM not like", value, "logCom");
            return (Criteria) this;
        }

        public Criteria andLogComIn(List<String> values) {
            addCriterion("LOG_COM in", values, "logCom");
            return (Criteria) this;
        }

        public Criteria andLogComNotIn(List<String> values) {
            addCriterion("LOG_COM not in", values, "logCom");
            return (Criteria) this;
        }

        public Criteria andLogComBetween(String value1, String value2) {
            addCriterion("LOG_COM between", value1, value2, "logCom");
            return (Criteria) this;
        }

        public Criteria andLogComNotBetween(String value1, String value2) {
            addCriterion("LOG_COM not between", value1, value2, "logCom");
            return (Criteria) this;
        }

        public Criteria andWNumberIsNull() {
            addCriterion("W_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andWNumberIsNotNull() {
            addCriterion("W_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andWNumberEqualTo(String value) {
            addCriterion("W_NUMBER =", value, "wNumber");
            return (Criteria) this;
        }

        public Criteria andWNumberNotEqualTo(String value) {
            addCriterion("W_NUMBER <>", value, "wNumber");
            return (Criteria) this;
        }

        public Criteria andWNumberGreaterThan(String value) {
            addCriterion("W_NUMBER >", value, "wNumber");
            return (Criteria) this;
        }

        public Criteria andWNumberGreaterThanOrEqualTo(String value) {
            addCriterion("W_NUMBER >=", value, "wNumber");
            return (Criteria) this;
        }

        public Criteria andWNumberLessThan(String value) {
            addCriterion("W_NUMBER <", value, "wNumber");
            return (Criteria) this;
        }

        public Criteria andWNumberLessThanOrEqualTo(String value) {
            addCriterion("W_NUMBER <=", value, "wNumber");
            return (Criteria) this;
        }

        public Criteria andWNumberLike(String value) {
            addCriterion("W_NUMBER like", value, "wNumber");
            return (Criteria) this;
        }

        public Criteria andWNumberNotLike(String value) {
            addCriterion("W_NUMBER not like", value, "wNumber");
            return (Criteria) this;
        }

        public Criteria andWNumberIn(List<String> values) {
            addCriterion("W_NUMBER in", values, "wNumber");
            return (Criteria) this;
        }

        public Criteria andWNumberNotIn(List<String> values) {
            addCriterion("W_NUMBER not in", values, "wNumber");
            return (Criteria) this;
        }

        public Criteria andWNumberBetween(String value1, String value2) {
            addCriterion("W_NUMBER between", value1, value2, "wNumber");
            return (Criteria) this;
        }

        public Criteria andWNumberNotBetween(String value1, String value2) {
            addCriterion("W_NUMBER not between", value1, value2, "wNumber");
            return (Criteria) this;
        }

        public Criteria andIsdeallIsNull() {
            addCriterion("ISDEALL is null");
            return (Criteria) this;
        }

        public Criteria andIsdeallIsNotNull() {
            addCriterion("ISDEALL is not null");
            return (Criteria) this;
        }

        public Criteria andIsdeallEqualTo(BigDecimal value) {
            addCriterion("ISDEALL =", value, "isdeall");
            return (Criteria) this;
        }

        public Criteria andIsdeallNotEqualTo(BigDecimal value) {
            addCriterion("ISDEALL <>", value, "isdeall");
            return (Criteria) this;
        }

        public Criteria andIsdeallGreaterThan(BigDecimal value) {
            addCriterion("ISDEALL >", value, "isdeall");
            return (Criteria) this;
        }

        public Criteria andIsdeallGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ISDEALL >=", value, "isdeall");
            return (Criteria) this;
        }

        public Criteria andIsdeallLessThan(BigDecimal value) {
            addCriterion("ISDEALL <", value, "isdeall");
            return (Criteria) this;
        }

        public Criteria andIsdeallLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ISDEALL <=", value, "isdeall");
            return (Criteria) this;
        }

        public Criteria andIsdeallIn(List<BigDecimal> values) {
            addCriterion("ISDEALL in", values, "isdeall");
            return (Criteria) this;
        }

        public Criteria andIsdeallNotIn(List<BigDecimal> values) {
            addCriterion("ISDEALL not in", values, "isdeall");
            return (Criteria) this;
        }

        public Criteria andIsdeallBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ISDEALL between", value1, value2, "isdeall");
            return (Criteria) this;
        }

        public Criteria andIsdeallNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ISDEALL not between", value1, value2, "isdeall");
            return (Criteria) this;
        }

        public Criteria andSnBeginNumIsNull() {
            addCriterion("SN_BEGIN_NUM is null");
            return (Criteria) this;
        }

        public Criteria andSnBeginNumIsNotNull() {
            addCriterion("SN_BEGIN_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andSnBeginNumEqualTo(String value) {
            addCriterion("SN_BEGIN_NUM =", value, "snBeginNum");
            return (Criteria) this;
        }

        public Criteria andSnBeginNumNotEqualTo(String value) {
            addCriterion("SN_BEGIN_NUM <>", value, "snBeginNum");
            return (Criteria) this;
        }

        public Criteria andSnBeginNumGreaterThan(String value) {
            addCriterion("SN_BEGIN_NUM >", value, "snBeginNum");
            return (Criteria) this;
        }

        public Criteria andSnBeginNumGreaterThanOrEqualTo(String value) {
            addCriterion("SN_BEGIN_NUM >=", value, "snBeginNum");
            return (Criteria) this;
        }

        public Criteria andSnBeginNumLessThan(String value) {
            addCriterion("SN_BEGIN_NUM <", value, "snBeginNum");
            return (Criteria) this;
        }

        public Criteria andSnBeginNumLessThanOrEqualTo(String value) {
            addCriterion("SN_BEGIN_NUM <=", value, "snBeginNum");
            return (Criteria) this;
        }

        public Criteria andSnBeginNumLike(String value) {
            addCriterion("SN_BEGIN_NUM like", value, "snBeginNum");
            return (Criteria) this;
        }

        public Criteria andSnBeginNumNotLike(String value) {
            addCriterion("SN_BEGIN_NUM not like", value, "snBeginNum");
            return (Criteria) this;
        }

        public Criteria andSnBeginNumIn(List<String> values) {
            addCriterion("SN_BEGIN_NUM in", values, "snBeginNum");
            return (Criteria) this;
        }

        public Criteria andSnBeginNumNotIn(List<String> values) {
            addCriterion("SN_BEGIN_NUM not in", values, "snBeginNum");
            return (Criteria) this;
        }

        public Criteria andSnBeginNumBetween(String value1, String value2) {
            addCriterion("SN_BEGIN_NUM between", value1, value2, "snBeginNum");
            return (Criteria) this;
        }

        public Criteria andSnBeginNumNotBetween(String value1, String value2) {
            addCriterion("SN_BEGIN_NUM not between", value1, value2, "snBeginNum");
            return (Criteria) this;
        }

        public Criteria andSnEndNumIsNull() {
            addCriterion("SN_END_NUM is null");
            return (Criteria) this;
        }

        public Criteria andSnEndNumIsNotNull() {
            addCriterion("SN_END_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andSnEndNumEqualTo(String value) {
            addCriterion("SN_END_NUM =", value, "snEndNum");
            return (Criteria) this;
        }

        public Criteria andSnEndNumNotEqualTo(String value) {
            addCriterion("SN_END_NUM <>", value, "snEndNum");
            return (Criteria) this;
        }

        public Criteria andSnEndNumGreaterThan(String value) {
            addCriterion("SN_END_NUM >", value, "snEndNum");
            return (Criteria) this;
        }

        public Criteria andSnEndNumGreaterThanOrEqualTo(String value) {
            addCriterion("SN_END_NUM >=", value, "snEndNum");
            return (Criteria) this;
        }

        public Criteria andSnEndNumLessThan(String value) {
            addCriterion("SN_END_NUM <", value, "snEndNum");
            return (Criteria) this;
        }

        public Criteria andSnEndNumLessThanOrEqualTo(String value) {
            addCriterion("SN_END_NUM <=", value, "snEndNum");
            return (Criteria) this;
        }

        public Criteria andSnEndNumLike(String value) {
            addCriterion("SN_END_NUM like", value, "snEndNum");
            return (Criteria) this;
        }

        public Criteria andSnEndNumNotLike(String value) {
            addCriterion("SN_END_NUM not like", value, "snEndNum");
            return (Criteria) this;
        }

        public Criteria andSnEndNumIn(List<String> values) {
            addCriterion("SN_END_NUM in", values, "snEndNum");
            return (Criteria) this;
        }

        public Criteria andSnEndNumNotIn(List<String> values) {
            addCriterion("SN_END_NUM not in", values, "snEndNum");
            return (Criteria) this;
        }

        public Criteria andSnEndNumBetween(String value1, String value2) {
            addCriterion("SN_END_NUM between", value1, value2, "snEndNum");
            return (Criteria) this;
        }

        public Criteria andSnEndNumNotBetween(String value1, String value2) {
            addCriterion("SN_END_NUM not between", value1, value2, "snEndNum");
            return (Criteria) this;
        }

        public Criteria andLogTypeIsNull() {
            addCriterion("LOG_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andLogTypeIsNotNull() {
            addCriterion("LOG_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andLogTypeEqualTo(String value) {
            addCriterion("LOG_TYPE =", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeNotEqualTo(String value) {
            addCriterion("LOG_TYPE <>", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeGreaterThan(String value) {
            addCriterion("LOG_TYPE >", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeGreaterThanOrEqualTo(String value) {
            addCriterion("LOG_TYPE >=", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeLessThan(String value) {
            addCriterion("LOG_TYPE <", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeLessThanOrEqualTo(String value) {
            addCriterion("LOG_TYPE <=", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeLike(String value) {
            addCriterion("LOG_TYPE like", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeNotLike(String value) {
            addCriterion("LOG_TYPE not like", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeIn(List<String> values) {
            addCriterion("LOG_TYPE in", values, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeNotIn(List<String> values) {
            addCriterion("LOG_TYPE not in", values, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeBetween(String value1, String value2) {
            addCriterion("LOG_TYPE between", value1, value2, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeNotBetween(String value1, String value2) {
            addCriterion("LOG_TYPE not between", value1, value2, "logType");
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

        public Criteria andSendStatusIsNull() {
            addCriterion("SEND_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andSendStatusIsNotNull() {
            addCriterion("SEND_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andSendStatusEqualTo(BigDecimal value) {
            addCriterion("SEND_STATUS =", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotEqualTo(BigDecimal value) {
            addCriterion("SEND_STATUS <>", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusGreaterThan(BigDecimal value) {
            addCriterion("SEND_STATUS >", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SEND_STATUS >=", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusLessThan(BigDecimal value) {
            addCriterion("SEND_STATUS <", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SEND_STATUS <=", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusIn(List<BigDecimal> values) {
            addCriterion("SEND_STATUS in", values, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotIn(List<BigDecimal> values) {
            addCriterion("SEND_STATUS not in", values, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SEND_STATUS between", value1, value2, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SEND_STATUS not between", value1, value2, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendMsgIsNull() {
            addCriterion("SEND_MSG is null");
            return (Criteria) this;
        }

        public Criteria andSendMsgIsNotNull() {
            addCriterion("SEND_MSG is not null");
            return (Criteria) this;
        }

        public Criteria andSendMsgEqualTo(String value) {
            addCriterion("SEND_MSG =", value, "sendMsg");
            return (Criteria) this;
        }

        public Criteria andSendMsgNotEqualTo(String value) {
            addCriterion("SEND_MSG <>", value, "sendMsg");
            return (Criteria) this;
        }

        public Criteria andSendMsgGreaterThan(String value) {
            addCriterion("SEND_MSG >", value, "sendMsg");
            return (Criteria) this;
        }

        public Criteria andSendMsgGreaterThanOrEqualTo(String value) {
            addCriterion("SEND_MSG >=", value, "sendMsg");
            return (Criteria) this;
        }

        public Criteria andSendMsgLessThan(String value) {
            addCriterion("SEND_MSG <", value, "sendMsg");
            return (Criteria) this;
        }

        public Criteria andSendMsgLessThanOrEqualTo(String value) {
            addCriterion("SEND_MSG <=", value, "sendMsg");
            return (Criteria) this;
        }

        public Criteria andSendMsgLike(String value) {
            addCriterion("SEND_MSG like", value, "sendMsg");
            return (Criteria) this;
        }

        public Criteria andSendMsgNotLike(String value) {
            addCriterion("SEND_MSG not like", value, "sendMsg");
            return (Criteria) this;
        }

        public Criteria andSendMsgIn(List<String> values) {
            addCriterion("SEND_MSG in", values, "sendMsg");
            return (Criteria) this;
        }

        public Criteria andSendMsgNotIn(List<String> values) {
            addCriterion("SEND_MSG not in", values, "sendMsg");
            return (Criteria) this;
        }

        public Criteria andSendMsgBetween(String value1, String value2) {
            addCriterion("SEND_MSG between", value1, value2, "sendMsg");
            return (Criteria) this;
        }

        public Criteria andSendMsgNotBetween(String value1, String value2) {
            addCriterion("SEND_MSG not between", value1, value2, "sendMsg");
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