package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PBalanceSerialExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public PBalanceSerialExample() {
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

        public Criteria andBalanceIdIsNull() {
            addCriterion("BALANCE_ID is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIdIsNotNull() {
            addCriterion("BALANCE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceIdEqualTo(String value) {
            addCriterion("BALANCE_ID =", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdNotEqualTo(String value) {
            addCriterion("BALANCE_ID <>", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdGreaterThan(String value) {
            addCriterion("BALANCE_ID >", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_ID >=", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdLessThan(String value) {
            addCriterion("BALANCE_ID <", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_ID <=", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdLike(String value) {
            addCriterion("BALANCE_ID like", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdNotLike(String value) {
            addCriterion("BALANCE_ID not like", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdIn(List<String> values) {
            addCriterion("BALANCE_ID in", values, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdNotIn(List<String> values) {
            addCriterion("BALANCE_ID not in", values, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdBetween(String value1, String value2) {
            addCriterion("BALANCE_ID between", value1, value2, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdNotBetween(String value1, String value2) {
            addCriterion("BALANCE_ID not between", value1, value2, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceBatchNoIsNull() {
            addCriterion("BALANCE_BATCH_NO is null");
            return (Criteria) this;
        }

        public Criteria andBalanceBatchNoIsNotNull() {
            addCriterion("BALANCE_BATCH_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceBatchNoEqualTo(String value) {
            addCriterion("BALANCE_BATCH_NO =", value, "balanceBatchNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBatchNoNotEqualTo(String value) {
            addCriterion("BALANCE_BATCH_NO <>", value, "balanceBatchNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBatchNoGreaterThan(String value) {
            addCriterion("BALANCE_BATCH_NO >", value, "balanceBatchNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBatchNoGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_BATCH_NO >=", value, "balanceBatchNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBatchNoLessThan(String value) {
            addCriterion("BALANCE_BATCH_NO <", value, "balanceBatchNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBatchNoLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_BATCH_NO <=", value, "balanceBatchNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBatchNoLike(String value) {
            addCriterion("BALANCE_BATCH_NO like", value, "balanceBatchNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBatchNoNotLike(String value) {
            addCriterion("BALANCE_BATCH_NO not like", value, "balanceBatchNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBatchNoIn(List<String> values) {
            addCriterion("BALANCE_BATCH_NO in", values, "balanceBatchNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBatchNoNotIn(List<String> values) {
            addCriterion("BALANCE_BATCH_NO not in", values, "balanceBatchNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBatchNoBetween(String value1, String value2) {
            addCriterion("BALANCE_BATCH_NO between", value1, value2, "balanceBatchNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBatchNoNotBetween(String value1, String value2) {
            addCriterion("BALANCE_BATCH_NO not between", value1, value2, "balanceBatchNo");
            return (Criteria) this;
        }

        public Criteria andSettleMonthIsNull() {
            addCriterion("SETTLE_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andSettleMonthIsNotNull() {
            addCriterion("SETTLE_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andSettleMonthEqualTo(String value) {
            addCriterion("SETTLE_MONTH =", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthNotEqualTo(String value) {
            addCriterion("SETTLE_MONTH <>", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthGreaterThan(String value) {
            addCriterion("SETTLE_MONTH >", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthGreaterThanOrEqualTo(String value) {
            addCriterion("SETTLE_MONTH >=", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthLessThan(String value) {
            addCriterion("SETTLE_MONTH <", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthLessThanOrEqualTo(String value) {
            addCriterion("SETTLE_MONTH <=", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthLike(String value) {
            addCriterion("SETTLE_MONTH like", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthNotLike(String value) {
            addCriterion("SETTLE_MONTH not like", value, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthIn(List<String> values) {
            addCriterion("SETTLE_MONTH in", values, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthNotIn(List<String> values) {
            addCriterion("SETTLE_MONTH not in", values, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthBetween(String value1, String value2) {
            addCriterion("SETTLE_MONTH between", value1, value2, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleMonthNotBetween(String value1, String value2) {
            addCriterion("SETTLE_MONTH not between", value1, value2, "settleMonth");
            return (Criteria) this;
        }

        public Criteria andSettleDateIsNull() {
            addCriterion("SETTLE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andSettleDateIsNotNull() {
            addCriterion("SETTLE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andSettleDateEqualTo(String value) {
            addCriterion("SETTLE_DATE =", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotEqualTo(String value) {
            addCriterion("SETTLE_DATE <>", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateGreaterThan(String value) {
            addCriterion("SETTLE_DATE >", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateGreaterThanOrEqualTo(String value) {
            addCriterion("SETTLE_DATE >=", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLessThan(String value) {
            addCriterion("SETTLE_DATE <", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLessThanOrEqualTo(String value) {
            addCriterion("SETTLE_DATE <=", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLike(String value) {
            addCriterion("SETTLE_DATE like", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotLike(String value) {
            addCriterion("SETTLE_DATE not like", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateIn(List<String> values) {
            addCriterion("SETTLE_DATE in", values, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotIn(List<String> values) {
            addCriterion("SETTLE_DATE not in", values, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateBetween(String value1, String value2) {
            addCriterion("SETTLE_DATE between", value1, value2, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotBetween(String value1, String value2) {
            addCriterion("SETTLE_DATE not between", value1, value2, "settleDate");
            return (Criteria) this;
        }

        public Criteria andAgIdIsNull() {
            addCriterion("AG_ID is null");
            return (Criteria) this;
        }

        public Criteria andAgIdIsNotNull() {
            addCriterion("AG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAgIdEqualTo(String value) {
            addCriterion("AG_ID =", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdNotEqualTo(String value) {
            addCriterion("AG_ID <>", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdGreaterThan(String value) {
            addCriterion("AG_ID >", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdGreaterThanOrEqualTo(String value) {
            addCriterion("AG_ID >=", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdLessThan(String value) {
            addCriterion("AG_ID <", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdLessThanOrEqualTo(String value) {
            addCriterion("AG_ID <=", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdLike(String value) {
            addCriterion("AG_ID like", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdNotLike(String value) {
            addCriterion("AG_ID not like", value, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdIn(List<String> values) {
            addCriterion("AG_ID in", values, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdNotIn(List<String> values) {
            addCriterion("AG_ID not in", values, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdBetween(String value1, String value2) {
            addCriterion("AG_ID between", value1, value2, "agId");
            return (Criteria) this;
        }

        public Criteria andAgIdNotBetween(String value1, String value2) {
            addCriterion("AG_ID not between", value1, value2, "agId");
            return (Criteria) this;
        }

        public Criteria andAgNameIsNull() {
            addCriterion("AG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAgNameIsNotNull() {
            addCriterion("AG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAgNameEqualTo(String value) {
            addCriterion("AG_NAME =", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameNotEqualTo(String value) {
            addCriterion("AG_NAME <>", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameGreaterThan(String value) {
            addCriterion("AG_NAME >", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameGreaterThanOrEqualTo(String value) {
            addCriterion("AG_NAME >=", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameLessThan(String value) {
            addCriterion("AG_NAME <", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameLessThanOrEqualTo(String value) {
            addCriterion("AG_NAME <=", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameLike(String value) {
            addCriterion("AG_NAME like", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameNotLike(String value) {
            addCriterion("AG_NAME not like", value, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameIn(List<String> values) {
            addCriterion("AG_NAME in", values, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameNotIn(List<String> values) {
            addCriterion("AG_NAME not in", values, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameBetween(String value1, String value2) {
            addCriterion("AG_NAME between", value1, value2, "agName");
            return (Criteria) this;
        }

        public Criteria andAgNameNotBetween(String value1, String value2) {
            addCriterion("AG_NAME not between", value1, value2, "agName");
            return (Criteria) this;
        }

        public Criteria andBrandNoIsNull() {
            addCriterion("BRAND_NO is null");
            return (Criteria) this;
        }

        public Criteria andBrandNoIsNotNull() {
            addCriterion("BRAND_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBrandNoEqualTo(String value) {
            addCriterion("BRAND_NO =", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoNotEqualTo(String value) {
            addCriterion("BRAND_NO <>", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoGreaterThan(String value) {
            addCriterion("BRAND_NO >", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoGreaterThanOrEqualTo(String value) {
            addCriterion("BRAND_NO >=", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoLessThan(String value) {
            addCriterion("BRAND_NO <", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoLessThanOrEqualTo(String value) {
            addCriterion("BRAND_NO <=", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoLike(String value) {
            addCriterion("BRAND_NO like", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoNotLike(String value) {
            addCriterion("BRAND_NO not like", value, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoIn(List<String> values) {
            addCriterion("BRAND_NO in", values, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoNotIn(List<String> values) {
            addCriterion("BRAND_NO not in", values, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoBetween(String value1, String value2) {
            addCriterion("BRAND_NO between", value1, value2, "brandNo");
            return (Criteria) this;
        }

        public Criteria andBrandNoNotBetween(String value1, String value2) {
            addCriterion("BRAND_NO not between", value1, value2, "brandNo");
            return (Criteria) this;
        }

        public Criteria andImportBatchIsNull() {
            addCriterion("IMPORT_BATCH is null");
            return (Criteria) this;
        }

        public Criteria andImportBatchIsNotNull() {
            addCriterion("IMPORT_BATCH is not null");
            return (Criteria) this;
        }

        public Criteria andImportBatchEqualTo(String value) {
            addCriterion("IMPORT_BATCH =", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchNotEqualTo(String value) {
            addCriterion("IMPORT_BATCH <>", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchGreaterThan(String value) {
            addCriterion("IMPORT_BATCH >", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchGreaterThanOrEqualTo(String value) {
            addCriterion("IMPORT_BATCH >=", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchLessThan(String value) {
            addCriterion("IMPORT_BATCH <", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchLessThanOrEqualTo(String value) {
            addCriterion("IMPORT_BATCH <=", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchLike(String value) {
            addCriterion("IMPORT_BATCH like", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchNotLike(String value) {
            addCriterion("IMPORT_BATCH not like", value, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchIn(List<String> values) {
            addCriterion("IMPORT_BATCH in", values, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchNotIn(List<String> values) {
            addCriterion("IMPORT_BATCH not in", values, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchBetween(String value1, String value2) {
            addCriterion("IMPORT_BATCH between", value1, value2, "importBatch");
            return (Criteria) this;
        }

        public Criteria andImportBatchNotBetween(String value1, String value2) {
            addCriterion("IMPORT_BATCH not between", value1, value2, "importBatch");
            return (Criteria) this;
        }

        public Criteria andRealAgIdIsNull() {
            addCriterion("REAL_AG_ID is null");
            return (Criteria) this;
        }

        public Criteria andRealAgIdIsNotNull() {
            addCriterion("REAL_AG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRealAgIdEqualTo(String value) {
            addCriterion("REAL_AG_ID =", value, "realAgId");
            return (Criteria) this;
        }

        public Criteria andRealAgIdNotEqualTo(String value) {
            addCriterion("REAL_AG_ID <>", value, "realAgId");
            return (Criteria) this;
        }

        public Criteria andRealAgIdGreaterThan(String value) {
            addCriterion("REAL_AG_ID >", value, "realAgId");
            return (Criteria) this;
        }

        public Criteria andRealAgIdGreaterThanOrEqualTo(String value) {
            addCriterion("REAL_AG_ID >=", value, "realAgId");
            return (Criteria) this;
        }

        public Criteria andRealAgIdLessThan(String value) {
            addCriterion("REAL_AG_ID <", value, "realAgId");
            return (Criteria) this;
        }

        public Criteria andRealAgIdLessThanOrEqualTo(String value) {
            addCriterion("REAL_AG_ID <=", value, "realAgId");
            return (Criteria) this;
        }

        public Criteria andRealAgIdLike(String value) {
            addCriterion("REAL_AG_ID like", value, "realAgId");
            return (Criteria) this;
        }

        public Criteria andRealAgIdNotLike(String value) {
            addCriterion("REAL_AG_ID not like", value, "realAgId");
            return (Criteria) this;
        }

        public Criteria andRealAgIdIn(List<String> values) {
            addCriterion("REAL_AG_ID in", values, "realAgId");
            return (Criteria) this;
        }

        public Criteria andRealAgIdNotIn(List<String> values) {
            addCriterion("REAL_AG_ID not in", values, "realAgId");
            return (Criteria) this;
        }

        public Criteria andRealAgIdBetween(String value1, String value2) {
            addCriterion("REAL_AG_ID between", value1, value2, "realAgId");
            return (Criteria) this;
        }

        public Criteria andRealAgIdNotBetween(String value1, String value2) {
            addCriterion("REAL_AG_ID not between", value1, value2, "realAgId");
            return (Criteria) this;
        }

        public Criteria andRealAgNameIsNull() {
            addCriterion("REAL_AG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andRealAgNameIsNotNull() {
            addCriterion("REAL_AG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andRealAgNameEqualTo(String value) {
            addCriterion("REAL_AG_NAME =", value, "realAgName");
            return (Criteria) this;
        }

        public Criteria andRealAgNameNotEqualTo(String value) {
            addCriterion("REAL_AG_NAME <>", value, "realAgName");
            return (Criteria) this;
        }

        public Criteria andRealAgNameGreaterThan(String value) {
            addCriterion("REAL_AG_NAME >", value, "realAgName");
            return (Criteria) this;
        }

        public Criteria andRealAgNameGreaterThanOrEqualTo(String value) {
            addCriterion("REAL_AG_NAME >=", value, "realAgName");
            return (Criteria) this;
        }

        public Criteria andRealAgNameLessThan(String value) {
            addCriterion("REAL_AG_NAME <", value, "realAgName");
            return (Criteria) this;
        }

        public Criteria andRealAgNameLessThanOrEqualTo(String value) {
            addCriterion("REAL_AG_NAME <=", value, "realAgName");
            return (Criteria) this;
        }

        public Criteria andRealAgNameLike(String value) {
            addCriterion("REAL_AG_NAME like", value, "realAgName");
            return (Criteria) this;
        }

        public Criteria andRealAgNameNotLike(String value) {
            addCriterion("REAL_AG_NAME not like", value, "realAgName");
            return (Criteria) this;
        }

        public Criteria andRealAgNameIn(List<String> values) {
            addCriterion("REAL_AG_NAME in", values, "realAgName");
            return (Criteria) this;
        }

        public Criteria andRealAgNameNotIn(List<String> values) {
            addCriterion("REAL_AG_NAME not in", values, "realAgName");
            return (Criteria) this;
        }

        public Criteria andRealAgNameBetween(String value1, String value2) {
            addCriterion("REAL_AG_NAME between", value1, value2, "realAgName");
            return (Criteria) this;
        }

        public Criteria andRealAgNameNotBetween(String value1, String value2) {
            addCriterion("REAL_AG_NAME not between", value1, value2, "realAgName");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtIsNull() {
            addCriterion("BALANCE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtIsNotNull() {
            addCriterion("BALANCE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT =", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtNotEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT <>", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtGreaterThan(BigDecimal value) {
            addCriterion("BALANCE_AMT >", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT >=", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtLessThan(BigDecimal value) {
            addCriterion("BALANCE_AMT <", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BALANCE_AMT <=", value, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtIn(List<BigDecimal> values) {
            addCriterion("BALANCE_AMT in", values, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtNotIn(List<BigDecimal> values) {
            addCriterion("BALANCE_AMT not in", values, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BALANCE_AMT between", value1, value2, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BALANCE_AMT not between", value1, value2, "balanceAmt");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoIsNull() {
            addCriterion("BALANCE_BANK_NO is null");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoIsNotNull() {
            addCriterion("BALANCE_BANK_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoEqualTo(String value) {
            addCriterion("BALANCE_BANK_NO =", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoNotEqualTo(String value) {
            addCriterion("BALANCE_BANK_NO <>", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoGreaterThan(String value) {
            addCriterion("BALANCE_BANK_NO >", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_BANK_NO >=", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoLessThan(String value) {
            addCriterion("BALANCE_BANK_NO <", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_BANK_NO <=", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoLike(String value) {
            addCriterion("BALANCE_BANK_NO like", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoNotLike(String value) {
            addCriterion("BALANCE_BANK_NO not like", value, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoIn(List<String> values) {
            addCriterion("BALANCE_BANK_NO in", values, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoNotIn(List<String> values) {
            addCriterion("BALANCE_BANK_NO not in", values, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoBetween(String value1, String value2) {
            addCriterion("BALANCE_BANK_NO between", value1, value2, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNoNotBetween(String value1, String value2) {
            addCriterion("BALANCE_BANK_NO not between", value1, value2, "balanceBankNo");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameIsNull() {
            addCriterion("BALANCE_BANK_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameIsNotNull() {
            addCriterion("BALANCE_BANK_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameEqualTo(String value) {
            addCriterion("BALANCE_BANK_NAME =", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameNotEqualTo(String value) {
            addCriterion("BALANCE_BANK_NAME <>", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameGreaterThan(String value) {
            addCriterion("BALANCE_BANK_NAME >", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_BANK_NAME >=", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameLessThan(String value) {
            addCriterion("BALANCE_BANK_NAME <", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_BANK_NAME <=", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameLike(String value) {
            addCriterion("BALANCE_BANK_NAME like", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameNotLike(String value) {
            addCriterion("BALANCE_BANK_NAME not like", value, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameIn(List<String> values) {
            addCriterion("BALANCE_BANK_NAME in", values, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameNotIn(List<String> values) {
            addCriterion("BALANCE_BANK_NAME not in", values, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameBetween(String value1, String value2) {
            addCriterion("BALANCE_BANK_NAME between", value1, value2, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceBankNameNotBetween(String value1, String value2) {
            addCriterion("BALANCE_BANK_NAME not between", value1, value2, "balanceBankName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctTypeIsNull() {
            addCriterion("BALANCE_ACCT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctTypeIsNotNull() {
            addCriterion("BALANCE_ACCT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctTypeEqualTo(String value) {
            addCriterion("BALANCE_ACCT_TYPE =", value, "balanceAcctType");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctTypeNotEqualTo(String value) {
            addCriterion("BALANCE_ACCT_TYPE <>", value, "balanceAcctType");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctTypeGreaterThan(String value) {
            addCriterion("BALANCE_ACCT_TYPE >", value, "balanceAcctType");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctTypeGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_ACCT_TYPE >=", value, "balanceAcctType");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctTypeLessThan(String value) {
            addCriterion("BALANCE_ACCT_TYPE <", value, "balanceAcctType");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctTypeLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_ACCT_TYPE <=", value, "balanceAcctType");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctTypeLike(String value) {
            addCriterion("BALANCE_ACCT_TYPE like", value, "balanceAcctType");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctTypeNotLike(String value) {
            addCriterion("BALANCE_ACCT_TYPE not like", value, "balanceAcctType");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctTypeIn(List<String> values) {
            addCriterion("BALANCE_ACCT_TYPE in", values, "balanceAcctType");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctTypeNotIn(List<String> values) {
            addCriterion("BALANCE_ACCT_TYPE not in", values, "balanceAcctType");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctTypeBetween(String value1, String value2) {
            addCriterion("BALANCE_ACCT_TYPE between", value1, value2, "balanceAcctType");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctTypeNotBetween(String value1, String value2) {
            addCriterion("BALANCE_ACCT_TYPE not between", value1, value2, "balanceAcctType");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoIsNull() {
            addCriterion("BALANCE_ACCT_NO is null");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoIsNotNull() {
            addCriterion("BALANCE_ACCT_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NO =", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoNotEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NO <>", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoGreaterThan(String value) {
            addCriterion("BALANCE_ACCT_NO >", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NO >=", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoLessThan(String value) {
            addCriterion("BALANCE_ACCT_NO <", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NO <=", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoLike(String value) {
            addCriterion("BALANCE_ACCT_NO like", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoNotLike(String value) {
            addCriterion("BALANCE_ACCT_NO not like", value, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoIn(List<String> values) {
            addCriterion("BALANCE_ACCT_NO in", values, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoNotIn(List<String> values) {
            addCriterion("BALANCE_ACCT_NO not in", values, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoBetween(String value1, String value2) {
            addCriterion("BALANCE_ACCT_NO between", value1, value2, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNoNotBetween(String value1, String value2) {
            addCriterion("BALANCE_ACCT_NO not between", value1, value2, "balanceAcctNo");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameIsNull() {
            addCriterion("BALANCE_ACCT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameIsNotNull() {
            addCriterion("BALANCE_ACCT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NAME =", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameNotEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NAME <>", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameGreaterThan(String value) {
            addCriterion("BALANCE_ACCT_NAME >", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NAME >=", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameLessThan(String value) {
            addCriterion("BALANCE_ACCT_NAME <", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_ACCT_NAME <=", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameLike(String value) {
            addCriterion("BALANCE_ACCT_NAME like", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameNotLike(String value) {
            addCriterion("BALANCE_ACCT_NAME not like", value, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameIn(List<String> values) {
            addCriterion("BALANCE_ACCT_NAME in", values, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameNotIn(List<String> values) {
            addCriterion("BALANCE_ACCT_NAME not in", values, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameBetween(String value1, String value2) {
            addCriterion("BALANCE_ACCT_NAME between", value1, value2, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceAcctNameNotBetween(String value1, String value2) {
            addCriterion("BALANCE_ACCT_NAME not between", value1, value2, "balanceAcctName");
            return (Criteria) this;
        }

        public Criteria andBalanceStatusIsNull() {
            addCriterion("BALANCE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andBalanceStatusIsNotNull() {
            addCriterion("BALANCE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceStatusEqualTo(String value) {
            addCriterion("BALANCE_STATUS =", value, "balanceStatus");
            return (Criteria) this;
        }

        public Criteria andBalanceStatusNotEqualTo(String value) {
            addCriterion("BALANCE_STATUS <>", value, "balanceStatus");
            return (Criteria) this;
        }

        public Criteria andBalanceStatusGreaterThan(String value) {
            addCriterion("BALANCE_STATUS >", value, "balanceStatus");
            return (Criteria) this;
        }

        public Criteria andBalanceStatusGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_STATUS >=", value, "balanceStatus");
            return (Criteria) this;
        }

        public Criteria andBalanceStatusLessThan(String value) {
            addCriterion("BALANCE_STATUS <", value, "balanceStatus");
            return (Criteria) this;
        }

        public Criteria andBalanceStatusLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_STATUS <=", value, "balanceStatus");
            return (Criteria) this;
        }

        public Criteria andBalanceStatusLike(String value) {
            addCriterion("BALANCE_STATUS like", value, "balanceStatus");
            return (Criteria) this;
        }

        public Criteria andBalanceStatusNotLike(String value) {
            addCriterion("BALANCE_STATUS not like", value, "balanceStatus");
            return (Criteria) this;
        }

        public Criteria andBalanceStatusIn(List<String> values) {
            addCriterion("BALANCE_STATUS in", values, "balanceStatus");
            return (Criteria) this;
        }

        public Criteria andBalanceStatusNotIn(List<String> values) {
            addCriterion("BALANCE_STATUS not in", values, "balanceStatus");
            return (Criteria) this;
        }

        public Criteria andBalanceStatusBetween(String value1, String value2) {
            addCriterion("BALANCE_STATUS between", value1, value2, "balanceStatus");
            return (Criteria) this;
        }

        public Criteria andBalanceStatusNotBetween(String value1, String value2) {
            addCriterion("BALANCE_STATUS not between", value1, value2, "balanceStatus");
            return (Criteria) this;
        }

        public Criteria andBalanceDescIsNull() {
            addCriterion("BALANCE_DESC is null");
            return (Criteria) this;
        }

        public Criteria andBalanceDescIsNotNull() {
            addCriterion("BALANCE_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceDescEqualTo(String value) {
            addCriterion("BALANCE_DESC =", value, "balanceDesc");
            return (Criteria) this;
        }

        public Criteria andBalanceDescNotEqualTo(String value) {
            addCriterion("BALANCE_DESC <>", value, "balanceDesc");
            return (Criteria) this;
        }

        public Criteria andBalanceDescGreaterThan(String value) {
            addCriterion("BALANCE_DESC >", value, "balanceDesc");
            return (Criteria) this;
        }

        public Criteria andBalanceDescGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_DESC >=", value, "balanceDesc");
            return (Criteria) this;
        }

        public Criteria andBalanceDescLessThan(String value) {
            addCriterion("BALANCE_DESC <", value, "balanceDesc");
            return (Criteria) this;
        }

        public Criteria andBalanceDescLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_DESC <=", value, "balanceDesc");
            return (Criteria) this;
        }

        public Criteria andBalanceDescLike(String value) {
            addCriterion("BALANCE_DESC like", value, "balanceDesc");
            return (Criteria) this;
        }

        public Criteria andBalanceDescNotLike(String value) {
            addCriterion("BALANCE_DESC not like", value, "balanceDesc");
            return (Criteria) this;
        }

        public Criteria andBalanceDescIn(List<String> values) {
            addCriterion("BALANCE_DESC in", values, "balanceDesc");
            return (Criteria) this;
        }

        public Criteria andBalanceDescNotIn(List<String> values) {
            addCriterion("BALANCE_DESC not in", values, "balanceDesc");
            return (Criteria) this;
        }

        public Criteria andBalanceDescBetween(String value1, String value2) {
            addCriterion("BALANCE_DESC between", value1, value2, "balanceDesc");
            return (Criteria) this;
        }

        public Criteria andBalanceDescNotBetween(String value1, String value2) {
            addCriterion("BALANCE_DESC not between", value1, value2, "balanceDesc");
            return (Criteria) this;
        }

        public Criteria andSubmitTerIsNull() {
            addCriterion("SUBMIT_TER is null");
            return (Criteria) this;
        }

        public Criteria andSubmitTerIsNotNull() {
            addCriterion("SUBMIT_TER is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitTerEqualTo(String value) {
            addCriterion("SUBMIT_TER =", value, "submitTer");
            return (Criteria) this;
        }

        public Criteria andSubmitTerNotEqualTo(String value) {
            addCriterion("SUBMIT_TER <>", value, "submitTer");
            return (Criteria) this;
        }

        public Criteria andSubmitTerGreaterThan(String value) {
            addCriterion("SUBMIT_TER >", value, "submitTer");
            return (Criteria) this;
        }

        public Criteria andSubmitTerGreaterThanOrEqualTo(String value) {
            addCriterion("SUBMIT_TER >=", value, "submitTer");
            return (Criteria) this;
        }

        public Criteria andSubmitTerLessThan(String value) {
            addCriterion("SUBMIT_TER <", value, "submitTer");
            return (Criteria) this;
        }

        public Criteria andSubmitTerLessThanOrEqualTo(String value) {
            addCriterion("SUBMIT_TER <=", value, "submitTer");
            return (Criteria) this;
        }

        public Criteria andSubmitTerLike(String value) {
            addCriterion("SUBMIT_TER like", value, "submitTer");
            return (Criteria) this;
        }

        public Criteria andSubmitTerNotLike(String value) {
            addCriterion("SUBMIT_TER not like", value, "submitTer");
            return (Criteria) this;
        }

        public Criteria andSubmitTerIn(List<String> values) {
            addCriterion("SUBMIT_TER in", values, "submitTer");
            return (Criteria) this;
        }

        public Criteria andSubmitTerNotIn(List<String> values) {
            addCriterion("SUBMIT_TER not in", values, "submitTer");
            return (Criteria) this;
        }

        public Criteria andSubmitTerBetween(String value1, String value2) {
            addCriterion("SUBMIT_TER between", value1, value2, "submitTer");
            return (Criteria) this;
        }

        public Criteria andSubmitTerNotBetween(String value1, String value2) {
            addCriterion("SUBMIT_TER not between", value1, value2, "submitTer");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIsNull() {
            addCriterion("SUBMIT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIsNotNull() {
            addCriterion("SUBMIT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeEqualTo(String value) {
            addCriterion("SUBMIT_TIME =", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotEqualTo(String value) {
            addCriterion("SUBMIT_TIME <>", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeGreaterThan(String value) {
            addCriterion("SUBMIT_TIME >", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeGreaterThanOrEqualTo(String value) {
            addCriterion("SUBMIT_TIME >=", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLessThan(String value) {
            addCriterion("SUBMIT_TIME <", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLessThanOrEqualTo(String value) {
            addCriterion("SUBMIT_TIME <=", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLike(String value) {
            addCriterion("SUBMIT_TIME like", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotLike(String value) {
            addCriterion("SUBMIT_TIME not like", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIn(List<String> values) {
            addCriterion("SUBMIT_TIME in", values, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotIn(List<String> values) {
            addCriterion("SUBMIT_TIME not in", values, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeBetween(String value1, String value2) {
            addCriterion("SUBMIT_TIME between", value1, value2, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotBetween(String value1, String value2) {
            addCriterion("SUBMIT_TIME not between", value1, value2, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeIsNull() {
            addCriterion("SYNC_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSyncTimeIsNotNull() {
            addCriterion("SYNC_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSyncTimeEqualTo(String value) {
            addCriterion("SYNC_TIME =", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeNotEqualTo(String value) {
            addCriterion("SYNC_TIME <>", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeGreaterThan(String value) {
            addCriterion("SYNC_TIME >", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeGreaterThanOrEqualTo(String value) {
            addCriterion("SYNC_TIME >=", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeLessThan(String value) {
            addCriterion("SYNC_TIME <", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeLessThanOrEqualTo(String value) {
            addCriterion("SYNC_TIME <=", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeLike(String value) {
            addCriterion("SYNC_TIME like", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeNotLike(String value) {
            addCriterion("SYNC_TIME not like", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeIn(List<String> values) {
            addCriterion("SYNC_TIME in", values, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeNotIn(List<String> values) {
            addCriterion("SYNC_TIME not in", values, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeBetween(String value1, String value2) {
            addCriterion("SYNC_TIME between", value1, value2, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeNotBetween(String value1, String value2) {
            addCriterion("SYNC_TIME not between", value1, value2, "syncTime");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNull() {
            addCriterion("CHECK_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("CHECK_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(String value) {
            addCriterion("CHECK_STATUS =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(String value) {
            addCriterion("CHECK_STATUS <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(String value) {
            addCriterion("CHECK_STATUS >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(String value) {
            addCriterion("CHECK_STATUS >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(String value) {
            addCriterion("CHECK_STATUS <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(String value) {
            addCriterion("CHECK_STATUS <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLike(String value) {
            addCriterion("CHECK_STATUS like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotLike(String value) {
            addCriterion("CHECK_STATUS not like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<String> values) {
            addCriterion("CHECK_STATUS in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<String> values) {
            addCriterion("CHECK_STATUS not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(String value1, String value2) {
            addCriterion("CHECK_STATUS between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(String value1, String value2) {
            addCriterion("CHECK_STATUS not between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andReconStatusIsNull() {
            addCriterion("RECON_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andReconStatusIsNotNull() {
            addCriterion("RECON_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andReconStatusEqualTo(String value) {
            addCriterion("RECON_STATUS =", value, "reconStatus");
            return (Criteria) this;
        }

        public Criteria andReconStatusNotEqualTo(String value) {
            addCriterion("RECON_STATUS <>", value, "reconStatus");
            return (Criteria) this;
        }

        public Criteria andReconStatusGreaterThan(String value) {
            addCriterion("RECON_STATUS >", value, "reconStatus");
            return (Criteria) this;
        }

        public Criteria andReconStatusGreaterThanOrEqualTo(String value) {
            addCriterion("RECON_STATUS >=", value, "reconStatus");
            return (Criteria) this;
        }

        public Criteria andReconStatusLessThan(String value) {
            addCriterion("RECON_STATUS <", value, "reconStatus");
            return (Criteria) this;
        }

        public Criteria andReconStatusLessThanOrEqualTo(String value) {
            addCriterion("RECON_STATUS <=", value, "reconStatus");
            return (Criteria) this;
        }

        public Criteria andReconStatusLike(String value) {
            addCriterion("RECON_STATUS like", value, "reconStatus");
            return (Criteria) this;
        }

        public Criteria andReconStatusNotLike(String value) {
            addCriterion("RECON_STATUS not like", value, "reconStatus");
            return (Criteria) this;
        }

        public Criteria andReconStatusIn(List<String> values) {
            addCriterion("RECON_STATUS in", values, "reconStatus");
            return (Criteria) this;
        }

        public Criteria andReconStatusNotIn(List<String> values) {
            addCriterion("RECON_STATUS not in", values, "reconStatus");
            return (Criteria) this;
        }

        public Criteria andReconStatusBetween(String value1, String value2) {
            addCriterion("RECON_STATUS between", value1, value2, "reconStatus");
            return (Criteria) this;
        }

        public Criteria andReconStatusNotBetween(String value1, String value2) {
            addCriterion("RECON_STATUS not between", value1, value2, "reconStatus");
            return (Criteria) this;
        }

        public Criteria andReconRemarkIsNull() {
            addCriterion("RECON_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andReconRemarkIsNotNull() {
            addCriterion("RECON_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andReconRemarkEqualTo(String value) {
            addCriterion("RECON_REMARK =", value, "reconRemark");
            return (Criteria) this;
        }

        public Criteria andReconRemarkNotEqualTo(String value) {
            addCriterion("RECON_REMARK <>", value, "reconRemark");
            return (Criteria) this;
        }

        public Criteria andReconRemarkGreaterThan(String value) {
            addCriterion("RECON_REMARK >", value, "reconRemark");
            return (Criteria) this;
        }

        public Criteria andReconRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("RECON_REMARK >=", value, "reconRemark");
            return (Criteria) this;
        }

        public Criteria andReconRemarkLessThan(String value) {
            addCriterion("RECON_REMARK <", value, "reconRemark");
            return (Criteria) this;
        }

        public Criteria andReconRemarkLessThanOrEqualTo(String value) {
            addCriterion("RECON_REMARK <=", value, "reconRemark");
            return (Criteria) this;
        }

        public Criteria andReconRemarkLike(String value) {
            addCriterion("RECON_REMARK like", value, "reconRemark");
            return (Criteria) this;
        }

        public Criteria andReconRemarkNotLike(String value) {
            addCriterion("RECON_REMARK not like", value, "reconRemark");
            return (Criteria) this;
        }

        public Criteria andReconRemarkIn(List<String> values) {
            addCriterion("RECON_REMARK in", values, "reconRemark");
            return (Criteria) this;
        }

        public Criteria andReconRemarkNotIn(List<String> values) {
            addCriterion("RECON_REMARK not in", values, "reconRemark");
            return (Criteria) this;
        }

        public Criteria andReconRemarkBetween(String value1, String value2) {
            addCriterion("RECON_REMARK between", value1, value2, "reconRemark");
            return (Criteria) this;
        }

        public Criteria andReconRemarkNotBetween(String value1, String value2) {
            addCriterion("RECON_REMARK not between", value1, value2, "reconRemark");
            return (Criteria) this;
        }

        public Criteria andBalanceBankVersionIsNull() {
            addCriterion("BALANCE_BANK_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andBalanceBankVersionIsNotNull() {
            addCriterion("BALANCE_BANK_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceBankVersionEqualTo(String value) {
            addCriterion("BALANCE_BANK_VERSION =", value, "balanceBankVersion");
            return (Criteria) this;
        }

        public Criteria andBalanceBankVersionNotEqualTo(String value) {
            addCriterion("BALANCE_BANK_VERSION <>", value, "balanceBankVersion");
            return (Criteria) this;
        }

        public Criteria andBalanceBankVersionGreaterThan(String value) {
            addCriterion("BALANCE_BANK_VERSION >", value, "balanceBankVersion");
            return (Criteria) this;
        }

        public Criteria andBalanceBankVersionGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_BANK_VERSION >=", value, "balanceBankVersion");
            return (Criteria) this;
        }

        public Criteria andBalanceBankVersionLessThan(String value) {
            addCriterion("BALANCE_BANK_VERSION <", value, "balanceBankVersion");
            return (Criteria) this;
        }

        public Criteria andBalanceBankVersionLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_BANK_VERSION <=", value, "balanceBankVersion");
            return (Criteria) this;
        }

        public Criteria andBalanceBankVersionLike(String value) {
            addCriterion("BALANCE_BANK_VERSION like", value, "balanceBankVersion");
            return (Criteria) this;
        }

        public Criteria andBalanceBankVersionNotLike(String value) {
            addCriterion("BALANCE_BANK_VERSION not like", value, "balanceBankVersion");
            return (Criteria) this;
        }

        public Criteria andBalanceBankVersionIn(List<String> values) {
            addCriterion("BALANCE_BANK_VERSION in", values, "balanceBankVersion");
            return (Criteria) this;
        }

        public Criteria andBalanceBankVersionNotIn(List<String> values) {
            addCriterion("BALANCE_BANK_VERSION not in", values, "balanceBankVersion");
            return (Criteria) this;
        }

        public Criteria andBalanceBankVersionBetween(String value1, String value2) {
            addCriterion("BALANCE_BANK_VERSION between", value1, value2, "balanceBankVersion");
            return (Criteria) this;
        }

        public Criteria andBalanceBankVersionNotBetween(String value1, String value2) {
            addCriterion("BALANCE_BANK_VERSION not between", value1, value2, "balanceBankVersion");
            return (Criteria) this;
        }

        public Criteria andCkwcTimeIsNull() {
            addCriterion("CKWC_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCkwcTimeIsNotNull() {
            addCriterion("CKWC_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCkwcTimeEqualTo(String value) {
            addCriterion("CKWC_TIME =", value, "ckwcTime");
            return (Criteria) this;
        }

        public Criteria andCkwcTimeNotEqualTo(String value) {
            addCriterion("CKWC_TIME <>", value, "ckwcTime");
            return (Criteria) this;
        }

        public Criteria andCkwcTimeGreaterThan(String value) {
            addCriterion("CKWC_TIME >", value, "ckwcTime");
            return (Criteria) this;
        }

        public Criteria andCkwcTimeGreaterThanOrEqualTo(String value) {
            addCriterion("CKWC_TIME >=", value, "ckwcTime");
            return (Criteria) this;
        }

        public Criteria andCkwcTimeLessThan(String value) {
            addCriterion("CKWC_TIME <", value, "ckwcTime");
            return (Criteria) this;
        }

        public Criteria andCkwcTimeLessThanOrEqualTo(String value) {
            addCriterion("CKWC_TIME <=", value, "ckwcTime");
            return (Criteria) this;
        }

        public Criteria andCkwcTimeLike(String value) {
            addCriterion("CKWC_TIME like", value, "ckwcTime");
            return (Criteria) this;
        }

        public Criteria andCkwcTimeNotLike(String value) {
            addCriterion("CKWC_TIME not like", value, "ckwcTime");
            return (Criteria) this;
        }

        public Criteria andCkwcTimeIn(List<String> values) {
            addCriterion("CKWC_TIME in", values, "ckwcTime");
            return (Criteria) this;
        }

        public Criteria andCkwcTimeNotIn(List<String> values) {
            addCriterion("CKWC_TIME not in", values, "ckwcTime");
            return (Criteria) this;
        }

        public Criteria andCkwcTimeBetween(String value1, String value2) {
            addCriterion("CKWC_TIME between", value1, value2, "ckwcTime");
            return (Criteria) this;
        }

        public Criteria andCkwcTimeNotBetween(String value1, String value2) {
            addCriterion("CKWC_TIME not between", value1, value2, "ckwcTime");
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