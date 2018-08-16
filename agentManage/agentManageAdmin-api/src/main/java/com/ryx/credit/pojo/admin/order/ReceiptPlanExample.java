package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReceiptPlanExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ReceiptPlanExample() {
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

        public Criteria andPlanNumIsNull() {
            addCriterion("PLAN_NUM is null");
            return (Criteria) this;
        }

        public Criteria andPlanNumIsNotNull() {
            addCriterion("PLAN_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andPlanNumEqualTo(String value) {
            addCriterion("PLAN_NUM =", value, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumNotEqualTo(String value) {
            addCriterion("PLAN_NUM <>", value, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumGreaterThan(String value) {
            addCriterion("PLAN_NUM >", value, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumGreaterThanOrEqualTo(String value) {
            addCriterion("PLAN_NUM >=", value, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumLessThan(String value) {
            addCriterion("PLAN_NUM <", value, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumLessThanOrEqualTo(String value) {
            addCriterion("PLAN_NUM <=", value, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumLike(String value) {
            addCriterion("PLAN_NUM like", value, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumNotLike(String value) {
            addCriterion("PLAN_NUM not like", value, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumIn(List<String> values) {
            addCriterion("PLAN_NUM in", values, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumNotIn(List<String> values) {
            addCriterion("PLAN_NUM not in", values, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumBetween(String value1, String value2) {
            addCriterion("PLAN_NUM between", value1, value2, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumNotBetween(String value1, String value2) {
            addCriterion("PLAN_NUM not between", value1, value2, "planNum");
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

        public Criteria andReceiptIdIsNull() {
            addCriterion("RECEIPT_ID is null");
            return (Criteria) this;
        }

        public Criteria andReceiptIdIsNotNull() {
            addCriterion("RECEIPT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andReceiptIdEqualTo(String value) {
            addCriterion("RECEIPT_ID =", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdNotEqualTo(String value) {
            addCriterion("RECEIPT_ID <>", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdGreaterThan(String value) {
            addCriterion("RECEIPT_ID >", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdGreaterThanOrEqualTo(String value) {
            addCriterion("RECEIPT_ID >=", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdLessThan(String value) {
            addCriterion("RECEIPT_ID <", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdLessThanOrEqualTo(String value) {
            addCriterion("RECEIPT_ID <=", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdLike(String value) {
            addCriterion("RECEIPT_ID like", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdNotLike(String value) {
            addCriterion("RECEIPT_ID not like", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdIn(List<String> values) {
            addCriterion("RECEIPT_ID in", values, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdNotIn(List<String> values) {
            addCriterion("RECEIPT_ID not in", values, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdBetween(String value1, String value2) {
            addCriterion("RECEIPT_ID between", value1, value2, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdNotBetween(String value1, String value2) {
            addCriterion("RECEIPT_ID not between", value1, value2, "receiptId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("USER_ID like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("USER_ID not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
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

        public Criteria andPlanProNumIsNull() {
            addCriterion("PLAN_PRO_NUM is null");
            return (Criteria) this;
        }

        public Criteria andPlanProNumIsNotNull() {
            addCriterion("PLAN_PRO_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andPlanProNumEqualTo(BigDecimal value) {
            addCriterion("PLAN_PRO_NUM =", value, "planProNum");
            return (Criteria) this;
        }

        public Criteria andPlanProNumNotEqualTo(BigDecimal value) {
            addCriterion("PLAN_PRO_NUM <>", value, "planProNum");
            return (Criteria) this;
        }

        public Criteria andPlanProNumGreaterThan(BigDecimal value) {
            addCriterion("PLAN_PRO_NUM >", value, "planProNum");
            return (Criteria) this;
        }

        public Criteria andPlanProNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PLAN_PRO_NUM >=", value, "planProNum");
            return (Criteria) this;
        }

        public Criteria andPlanProNumLessThan(BigDecimal value) {
            addCriterion("PLAN_PRO_NUM <", value, "planProNum");
            return (Criteria) this;
        }

        public Criteria andPlanProNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PLAN_PRO_NUM <=", value, "planProNum");
            return (Criteria) this;
        }

        public Criteria andPlanProNumIn(List<BigDecimal> values) {
            addCriterion("PLAN_PRO_NUM in", values, "planProNum");
            return (Criteria) this;
        }

        public Criteria andPlanProNumNotIn(List<BigDecimal> values) {
            addCriterion("PLAN_PRO_NUM not in", values, "planProNum");
            return (Criteria) this;
        }

        public Criteria andPlanProNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLAN_PRO_NUM between", value1, value2, "planProNum");
            return (Criteria) this;
        }

        public Criteria andPlanProNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLAN_PRO_NUM not between", value1, value2, "planProNum");
            return (Criteria) this;
        }

        public Criteria andSendProNumIsNull() {
            addCriterion("SEND_PRO_NUM is null");
            return (Criteria) this;
        }

        public Criteria andSendProNumIsNotNull() {
            addCriterion("SEND_PRO_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andSendProNumEqualTo(BigDecimal value) {
            addCriterion("SEND_PRO_NUM =", value, "sendProNum");
            return (Criteria) this;
        }

        public Criteria andSendProNumNotEqualTo(BigDecimal value) {
            addCriterion("SEND_PRO_NUM <>", value, "sendProNum");
            return (Criteria) this;
        }

        public Criteria andSendProNumGreaterThan(BigDecimal value) {
            addCriterion("SEND_PRO_NUM >", value, "sendProNum");
            return (Criteria) this;
        }

        public Criteria andSendProNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SEND_PRO_NUM >=", value, "sendProNum");
            return (Criteria) this;
        }

        public Criteria andSendProNumLessThan(BigDecimal value) {
            addCriterion("SEND_PRO_NUM <", value, "sendProNum");
            return (Criteria) this;
        }

        public Criteria andSendProNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SEND_PRO_NUM <=", value, "sendProNum");
            return (Criteria) this;
        }

        public Criteria andSendProNumIn(List<BigDecimal> values) {
            addCriterion("SEND_PRO_NUM in", values, "sendProNum");
            return (Criteria) this;
        }

        public Criteria andSendProNumNotIn(List<BigDecimal> values) {
            addCriterion("SEND_PRO_NUM not in", values, "sendProNum");
            return (Criteria) this;
        }

        public Criteria andSendProNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SEND_PRO_NUM between", value1, value2, "sendProNum");
            return (Criteria) this;
        }

        public Criteria andSendProNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SEND_PRO_NUM not between", value1, value2, "sendProNum");
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

        public Criteria andRealSendDateIsNull() {
            addCriterion("REAL_SEND_DATE is null");
            return (Criteria) this;
        }

        public Criteria andRealSendDateIsNotNull() {
            addCriterion("REAL_SEND_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andRealSendDateEqualTo(Date value) {
            addCriterion("REAL_SEND_DATE =", value, "realSendDate");
            return (Criteria) this;
        }

        public Criteria andRealSendDateNotEqualTo(Date value) {
            addCriterion("REAL_SEND_DATE <>", value, "realSendDate");
            return (Criteria) this;
        }

        public Criteria andRealSendDateGreaterThan(Date value) {
            addCriterion("REAL_SEND_DATE >", value, "realSendDate");
            return (Criteria) this;
        }

        public Criteria andRealSendDateGreaterThanOrEqualTo(Date value) {
            addCriterion("REAL_SEND_DATE >=", value, "realSendDate");
            return (Criteria) this;
        }

        public Criteria andRealSendDateLessThan(Date value) {
            addCriterion("REAL_SEND_DATE <", value, "realSendDate");
            return (Criteria) this;
        }

        public Criteria andRealSendDateLessThanOrEqualTo(Date value) {
            addCriterion("REAL_SEND_DATE <=", value, "realSendDate");
            return (Criteria) this;
        }

        public Criteria andRealSendDateIn(List<Date> values) {
            addCriterion("REAL_SEND_DATE in", values, "realSendDate");
            return (Criteria) this;
        }

        public Criteria andRealSendDateNotIn(List<Date> values) {
            addCriterion("REAL_SEND_DATE not in", values, "realSendDate");
            return (Criteria) this;
        }

        public Criteria andRealSendDateBetween(Date value1, Date value2) {
            addCriterion("REAL_SEND_DATE between", value1, value2, "realSendDate");
            return (Criteria) this;
        }

        public Criteria andRealSendDateNotBetween(Date value1, Date value2) {
            addCriterion("REAL_SEND_DATE not between", value1, value2, "realSendDate");
            return (Criteria) this;
        }

        public Criteria andReturnOrderDetailIdIsNull() {
            addCriterion("RETURN_ORDER_DETAIL_ID is null");
            return (Criteria) this;
        }

        public Criteria andReturnOrderDetailIdIsNotNull() {
            addCriterion("RETURN_ORDER_DETAIL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andReturnOrderDetailIdEqualTo(String value) {
            addCriterion("RETURN_ORDER_DETAIL_ID =", value, "returnOrderDetailId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderDetailIdNotEqualTo(String value) {
            addCriterion("RETURN_ORDER_DETAIL_ID <>", value, "returnOrderDetailId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderDetailIdGreaterThan(String value) {
            addCriterion("RETURN_ORDER_DETAIL_ID >", value, "returnOrderDetailId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderDetailIdGreaterThanOrEqualTo(String value) {
            addCriterion("RETURN_ORDER_DETAIL_ID >=", value, "returnOrderDetailId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderDetailIdLessThan(String value) {
            addCriterion("RETURN_ORDER_DETAIL_ID <", value, "returnOrderDetailId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderDetailIdLessThanOrEqualTo(String value) {
            addCriterion("RETURN_ORDER_DETAIL_ID <=", value, "returnOrderDetailId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderDetailIdLike(String value) {
            addCriterion("RETURN_ORDER_DETAIL_ID like", value, "returnOrderDetailId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderDetailIdNotLike(String value) {
            addCriterion("RETURN_ORDER_DETAIL_ID not like", value, "returnOrderDetailId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderDetailIdIn(List<String> values) {
            addCriterion("RETURN_ORDER_DETAIL_ID in", values, "returnOrderDetailId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderDetailIdNotIn(List<String> values) {
            addCriterion("RETURN_ORDER_DETAIL_ID not in", values, "returnOrderDetailId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderDetailIdBetween(String value1, String value2) {
            addCriterion("RETURN_ORDER_DETAIL_ID between", value1, value2, "returnOrderDetailId");
            return (Criteria) this;
        }

        public Criteria andReturnOrderDetailIdNotBetween(String value1, String value2) {
            addCriterion("RETURN_ORDER_DETAIL_ID not between", value1, value2, "returnOrderDetailId");
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

        public Criteria andPlanOrderStatusIsNull() {
            addCriterion("PLAN_ORDER_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andPlanOrderStatusIsNotNull() {
            addCriterion("PLAN_ORDER_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andPlanOrderStatusEqualTo(BigDecimal value) {
            addCriterion("PLAN_ORDER_STATUS =", value, "planOrderStatus");
            return (Criteria) this;
        }

        public Criteria andPlanOrderStatusNotEqualTo(BigDecimal value) {
            addCriterion("PLAN_ORDER_STATUS <>", value, "planOrderStatus");
            return (Criteria) this;
        }

        public Criteria andPlanOrderStatusGreaterThan(BigDecimal value) {
            addCriterion("PLAN_ORDER_STATUS >", value, "planOrderStatus");
            return (Criteria) this;
        }

        public Criteria andPlanOrderStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PLAN_ORDER_STATUS >=", value, "planOrderStatus");
            return (Criteria) this;
        }

        public Criteria andPlanOrderStatusLessThan(BigDecimal value) {
            addCriterion("PLAN_ORDER_STATUS <", value, "planOrderStatus");
            return (Criteria) this;
        }

        public Criteria andPlanOrderStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PLAN_ORDER_STATUS <=", value, "planOrderStatus");
            return (Criteria) this;
        }

        public Criteria andPlanOrderStatusIn(List<BigDecimal> values) {
            addCriterion("PLAN_ORDER_STATUS in", values, "planOrderStatus");
            return (Criteria) this;
        }

        public Criteria andPlanOrderStatusNotIn(List<BigDecimal> values) {
            addCriterion("PLAN_ORDER_STATUS not in", values, "planOrderStatus");
            return (Criteria) this;
        }

        public Criteria andPlanOrderStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLAN_ORDER_STATUS between", value1, value2, "planOrderStatus");
            return (Criteria) this;
        }

        public Criteria andPlanOrderStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLAN_ORDER_STATUS not between", value1, value2, "planOrderStatus");
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

        public Criteria andModelIsNull() {
            addCriterion("MODEL is null");
            return (Criteria) this;
        }

        public Criteria andModelIsNotNull() {
            addCriterion("MODEL is not null");
            return (Criteria) this;
        }

        public Criteria andModelEqualTo(String value) {
            addCriterion("MODEL =", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotEqualTo(String value) {
            addCriterion("MODEL <>", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThan(String value) {
            addCriterion("MODEL >", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThanOrEqualTo(String value) {
            addCriterion("MODEL >=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThan(String value) {
            addCriterion("MODEL <", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThanOrEqualTo(String value) {
            addCriterion("MODEL <=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLike(String value) {
            addCriterion("MODEL like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotLike(String value) {
            addCriterion("MODEL not like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelIn(List<String> values) {
            addCriterion("MODEL in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotIn(List<String> values) {
            addCriterion("MODEL not in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelBetween(String value1, String value2) {
            addCriterion("MODEL between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotBetween(String value1, String value2) {
            addCriterion("MODEL not between", value1, value2, "model");
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