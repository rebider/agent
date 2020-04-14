package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderAdjAccountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OrderAdjAccountExample() {
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

        public Criteria andAdjIdIsNull() {
            addCriterion("ADJ_ID is null");
            return (Criteria) this;
        }

        public Criteria andAdjIdIsNotNull() {
            addCriterion("ADJ_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAdjIdEqualTo(String value) {
            addCriterion("ADJ_ID =", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdNotEqualTo(String value) {
            addCriterion("ADJ_ID <>", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdGreaterThan(String value) {
            addCriterion("ADJ_ID >", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdGreaterThanOrEqualTo(String value) {
            addCriterion("ADJ_ID >=", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdLessThan(String value) {
            addCriterion("ADJ_ID <", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdLessThanOrEqualTo(String value) {
            addCriterion("ADJ_ID <=", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdLike(String value) {
            addCriterion("ADJ_ID like", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdNotLike(String value) {
            addCriterion("ADJ_ID not like", value, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdIn(List<String> values) {
            addCriterion("ADJ_ID in", values, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdNotIn(List<String> values) {
            addCriterion("ADJ_ID not in", values, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdBetween(String value1, String value2) {
            addCriterion("ADJ_ID between", value1, value2, "adjId");
            return (Criteria) this;
        }

        public Criteria andAdjIdNotBetween(String value1, String value2) {
            addCriterion("ADJ_ID not between", value1, value2, "adjId");
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

        public Criteria andTypeIsNull() {
            addCriterion("TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(BigDecimal value) {
            addCriterion("TYPE =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(BigDecimal value) {
            addCriterion("TYPE <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(BigDecimal value) {
            addCriterion("TYPE >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TYPE >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(BigDecimal value) {
            addCriterion("TYPE <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TYPE <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<BigDecimal> values) {
            addCriterion("TYPE in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<BigDecimal> values) {
            addCriterion("TYPE not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TYPE between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TYPE not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andRefundAccountIsNull() {
            addCriterion("REFUND_ACCOUNT is null");
            return (Criteria) this;
        }

        public Criteria andRefundAccountIsNotNull() {
            addCriterion("REFUND_ACCOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andRefundAccountEqualTo(String value) {
            addCriterion("REFUND_ACCOUNT =", value, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountNotEqualTo(String value) {
            addCriterion("REFUND_ACCOUNT <>", value, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountGreaterThan(String value) {
            addCriterion("REFUND_ACCOUNT >", value, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountGreaterThanOrEqualTo(String value) {
            addCriterion("REFUND_ACCOUNT >=", value, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountLessThan(String value) {
            addCriterion("REFUND_ACCOUNT <", value, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountLessThanOrEqualTo(String value) {
            addCriterion("REFUND_ACCOUNT <=", value, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountLike(String value) {
            addCriterion("REFUND_ACCOUNT like", value, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountNotLike(String value) {
            addCriterion("REFUND_ACCOUNT not like", value, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountIn(List<String> values) {
            addCriterion("REFUND_ACCOUNT in", values, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountNotIn(List<String> values) {
            addCriterion("REFUND_ACCOUNT not in", values, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountBetween(String value1, String value2) {
            addCriterion("REFUND_ACCOUNT between", value1, value2, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountNotBetween(String value1, String value2) {
            addCriterion("REFUND_ACCOUNT not between", value1, value2, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andAccountNameIsNull() {
            addCriterion("ACCOUNT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAccountNameIsNotNull() {
            addCriterion("ACCOUNT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAccountNameEqualTo(String value) {
            addCriterion("ACCOUNT_NAME =", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotEqualTo(String value) {
            addCriterion("ACCOUNT_NAME <>", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameGreaterThan(String value) {
            addCriterion("ACCOUNT_NAME >", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameGreaterThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_NAME >=", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLessThan(String value) {
            addCriterion("ACCOUNT_NAME <", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLessThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_NAME <=", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLike(String value) {
            addCriterion("ACCOUNT_NAME like", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotLike(String value) {
            addCriterion("ACCOUNT_NAME not like", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameIn(List<String> values) {
            addCriterion("ACCOUNT_NAME in", values, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotIn(List<String> values) {
            addCriterion("ACCOUNT_NAME not in", values, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameBetween(String value1, String value2) {
            addCriterion("ACCOUNT_NAME between", value1, value2, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotBetween(String value1, String value2) {
            addCriterion("ACCOUNT_NAME not between", value1, value2, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountBankIsNull() {
            addCriterion("ACCOUNT_BANK is null");
            return (Criteria) this;
        }

        public Criteria andAccountBankIsNotNull() {
            addCriterion("ACCOUNT_BANK is not null");
            return (Criteria) this;
        }

        public Criteria andAccountBankEqualTo(String value) {
            addCriterion("ACCOUNT_BANK =", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankNotEqualTo(String value) {
            addCriterion("ACCOUNT_BANK <>", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankGreaterThan(String value) {
            addCriterion("ACCOUNT_BANK >", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankGreaterThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_BANK >=", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankLessThan(String value) {
            addCriterion("ACCOUNT_BANK <", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankLessThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_BANK <=", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankLike(String value) {
            addCriterion("ACCOUNT_BANK like", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankNotLike(String value) {
            addCriterion("ACCOUNT_BANK not like", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankIn(List<String> values) {
            addCriterion("ACCOUNT_BANK in", values, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankNotIn(List<String> values) {
            addCriterion("ACCOUNT_BANK not in", values, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankBetween(String value1, String value2) {
            addCriterion("ACCOUNT_BANK between", value1, value2, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankNotBetween(String value1, String value2) {
            addCriterion("ACCOUNT_BANK not between", value1, value2, "accountBank");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumIsNull() {
            addCriterion("BRANCH_LINE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumIsNotNull() {
            addCriterion("BRANCH_LINE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM =", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM <>", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumGreaterThan(String value) {
            addCriterion("BRANCH_LINE_NUM >", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumGreaterThanOrEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM >=", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumLessThan(String value) {
            addCriterion("BRANCH_LINE_NUM <", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumLessThanOrEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM <=", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumLike(String value) {
            addCriterion("BRANCH_LINE_NUM like", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotLike(String value) {
            addCriterion("BRANCH_LINE_NUM not like", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumIn(List<String> values) {
            addCriterion("BRANCH_LINE_NUM in", values, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotIn(List<String> values) {
            addCriterion("BRANCH_LINE_NUM not in", values, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumBetween(String value1, String value2) {
            addCriterion("BRANCH_LINE_NUM between", value1, value2, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotBetween(String value1, String value2) {
            addCriterion("BRANCH_LINE_NUM not between", value1, value2, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumIsNull() {
            addCriterion("ALL_LINE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andAllLineNumIsNotNull() {
            addCriterion("ALL_LINE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andAllLineNumEqualTo(String value) {
            addCriterion("ALL_LINE_NUM =", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotEqualTo(String value) {
            addCriterion("ALL_LINE_NUM <>", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumGreaterThan(String value) {
            addCriterion("ALL_LINE_NUM >", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumGreaterThanOrEqualTo(String value) {
            addCriterion("ALL_LINE_NUM >=", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumLessThan(String value) {
            addCriterion("ALL_LINE_NUM <", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumLessThanOrEqualTo(String value) {
            addCriterion("ALL_LINE_NUM <=", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumLike(String value) {
            addCriterion("ALL_LINE_NUM like", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotLike(String value) {
            addCriterion("ALL_LINE_NUM not like", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumIn(List<String> values) {
            addCriterion("ALL_LINE_NUM in", values, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotIn(List<String> values) {
            addCriterion("ALL_LINE_NUM not in", values, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumBetween(String value1, String value2) {
            addCriterion("ALL_LINE_NUM between", value1, value2, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotBetween(String value1, String value2) {
            addCriterion("ALL_LINE_NUM not between", value1, value2, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andRefundAmoIsNull() {
            addCriterion("REFUND_AMO is null");
            return (Criteria) this;
        }

        public Criteria andRefundAmoIsNotNull() {
            addCriterion("REFUND_AMO is not null");
            return (Criteria) this;
        }

        public Criteria andRefundAmoEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMO =", value, "refundAmo");
            return (Criteria) this;
        }

        public Criteria andRefundAmoNotEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMO <>", value, "refundAmo");
            return (Criteria) this;
        }

        public Criteria andRefundAmoGreaterThan(BigDecimal value) {
            addCriterion("REFUND_AMO >", value, "refundAmo");
            return (Criteria) this;
        }

        public Criteria andRefundAmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMO >=", value, "refundAmo");
            return (Criteria) this;
        }

        public Criteria andRefundAmoLessThan(BigDecimal value) {
            addCriterion("REFUND_AMO <", value, "refundAmo");
            return (Criteria) this;
        }

        public Criteria andRefundAmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMO <=", value, "refundAmo");
            return (Criteria) this;
        }

        public Criteria andRefundAmoIn(List<BigDecimal> values) {
            addCriterion("REFUND_AMO in", values, "refundAmo");
            return (Criteria) this;
        }

        public Criteria andRefundAmoNotIn(List<BigDecimal> values) {
            addCriterion("REFUND_AMO not in", values, "refundAmo");
            return (Criteria) this;
        }

        public Criteria andRefundAmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_AMO between", value1, value2, "refundAmo");
            return (Criteria) this;
        }

        public Criteria andRefundAmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_AMO not between", value1, value2, "refundAmo");
            return (Criteria) this;
        }

        public Criteria andRefundTmIsNull() {
            addCriterion("REFUND_TM is null");
            return (Criteria) this;
        }

        public Criteria andRefundTmIsNotNull() {
            addCriterion("REFUND_TM is not null");
            return (Criteria) this;
        }

        public Criteria andRefundTmEqualTo(Date value) {
            addCriterion("REFUND_TM =", value, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmNotEqualTo(Date value) {
            addCriterion("REFUND_TM <>", value, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmGreaterThan(Date value) {
            addCriterion("REFUND_TM >", value, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmGreaterThanOrEqualTo(Date value) {
            addCriterion("REFUND_TM >=", value, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmLessThan(Date value) {
            addCriterion("REFUND_TM <", value, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmLessThanOrEqualTo(Date value) {
            addCriterion("REFUND_TM <=", value, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmIn(List<Date> values) {
            addCriterion("REFUND_TM in", values, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmNotIn(List<Date> values) {
            addCriterion("REFUND_TM not in", values, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmBetween(Date value1, Date value2) {
            addCriterion("REFUND_TM between", value1, value2, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundTmNotBetween(Date value1, Date value2) {
            addCriterion("REFUND_TM not between", value1, value2, "refundTm");
            return (Criteria) this;
        }

        public Criteria andRefundStatIsNull() {
            addCriterion("REFUND_STAT is null");
            return (Criteria) this;
        }

        public Criteria andRefundStatIsNotNull() {
            addCriterion("REFUND_STAT is not null");
            return (Criteria) this;
        }

        public Criteria andRefundStatEqualTo(BigDecimal value) {
            addCriterion("REFUND_STAT =", value, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatNotEqualTo(BigDecimal value) {
            addCriterion("REFUND_STAT <>", value, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatGreaterThan(BigDecimal value) {
            addCriterion("REFUND_STAT >", value, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_STAT >=", value, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatLessThan(BigDecimal value) {
            addCriterion("REFUND_STAT <", value, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_STAT <=", value, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatIn(List<BigDecimal> values) {
            addCriterion("REFUND_STAT in", values, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatNotIn(List<BigDecimal> values) {
            addCriterion("REFUND_STAT not in", values, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_STAT between", value1, value2, "refundStat");
            return (Criteria) this;
        }

        public Criteria andRefundStatNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_STAT not between", value1, value2, "refundStat");
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