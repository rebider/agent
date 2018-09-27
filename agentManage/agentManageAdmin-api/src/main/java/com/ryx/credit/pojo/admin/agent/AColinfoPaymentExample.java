package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AColinfoPaymentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AColinfoPaymentExample() {
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

        public Criteria andColinfoIdIsNull() {
            addCriterion("COLINFO_ID is null");
            return (Criteria) this;
        }

        public Criteria andColinfoIdIsNotNull() {
            addCriterion("COLINFO_ID is not null");
            return (Criteria) this;
        }

        public Criteria andColinfoIdEqualTo(String value) {
            addCriterion("COLINFO_ID =", value, "colinfoId");
            return (Criteria) this;
        }

        public Criteria andColinfoIdNotEqualTo(String value) {
            addCriterion("COLINFO_ID <>", value, "colinfoId");
            return (Criteria) this;
        }

        public Criteria andColinfoIdGreaterThan(String value) {
            addCriterion("COLINFO_ID >", value, "colinfoId");
            return (Criteria) this;
        }

        public Criteria andColinfoIdGreaterThanOrEqualTo(String value) {
            addCriterion("COLINFO_ID >=", value, "colinfoId");
            return (Criteria) this;
        }

        public Criteria andColinfoIdLessThan(String value) {
            addCriterion("COLINFO_ID <", value, "colinfoId");
            return (Criteria) this;
        }

        public Criteria andColinfoIdLessThanOrEqualTo(String value) {
            addCriterion("COLINFO_ID <=", value, "colinfoId");
            return (Criteria) this;
        }

        public Criteria andColinfoIdLike(String value) {
            addCriterion("COLINFO_ID like", value, "colinfoId");
            return (Criteria) this;
        }

        public Criteria andColinfoIdNotLike(String value) {
            addCriterion("COLINFO_ID not like", value, "colinfoId");
            return (Criteria) this;
        }

        public Criteria andColinfoIdIn(List<String> values) {
            addCriterion("COLINFO_ID in", values, "colinfoId");
            return (Criteria) this;
        }

        public Criteria andColinfoIdNotIn(List<String> values) {
            addCriterion("COLINFO_ID not in", values, "colinfoId");
            return (Criteria) this;
        }

        public Criteria andColinfoIdBetween(String value1, String value2) {
            addCriterion("COLINFO_ID between", value1, value2, "colinfoId");
            return (Criteria) this;
        }

        public Criteria andColinfoIdNotBetween(String value1, String value2) {
            addCriterion("COLINFO_ID not between", value1, value2, "colinfoId");
            return (Criteria) this;
        }

        public Criteria andMerchIdIsNull() {
            addCriterion("MERCH_ID is null");
            return (Criteria) this;
        }

        public Criteria andMerchIdIsNotNull() {
            addCriterion("MERCH_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMerchIdEqualTo(String value) {
            addCriterion("MERCH_ID =", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdNotEqualTo(String value) {
            addCriterion("MERCH_ID <>", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdGreaterThan(String value) {
            addCriterion("MERCH_ID >", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdGreaterThanOrEqualTo(String value) {
            addCriterion("MERCH_ID >=", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdLessThan(String value) {
            addCriterion("MERCH_ID <", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdLessThanOrEqualTo(String value) {
            addCriterion("MERCH_ID <=", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdLike(String value) {
            addCriterion("MERCH_ID like", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdNotLike(String value) {
            addCriterion("MERCH_ID not like", value, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdIn(List<String> values) {
            addCriterion("MERCH_ID in", values, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdNotIn(List<String> values) {
            addCriterion("MERCH_ID not in", values, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdBetween(String value1, String value2) {
            addCriterion("MERCH_ID between", value1, value2, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchIdNotBetween(String value1, String value2) {
            addCriterion("MERCH_ID not between", value1, value2, "merchId");
            return (Criteria) this;
        }

        public Criteria andMerchNameIsNull() {
            addCriterion("MERCH_NAME is null");
            return (Criteria) this;
        }

        public Criteria andMerchNameIsNotNull() {
            addCriterion("MERCH_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andMerchNameEqualTo(String value) {
            addCriterion("MERCH_NAME =", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameNotEqualTo(String value) {
            addCriterion("MERCH_NAME <>", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameGreaterThan(String value) {
            addCriterion("MERCH_NAME >", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameGreaterThanOrEqualTo(String value) {
            addCriterion("MERCH_NAME >=", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameLessThan(String value) {
            addCriterion("MERCH_NAME <", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameLessThanOrEqualTo(String value) {
            addCriterion("MERCH_NAME <=", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameLike(String value) {
            addCriterion("MERCH_NAME like", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameNotLike(String value) {
            addCriterion("MERCH_NAME not like", value, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameIn(List<String> values) {
            addCriterion("MERCH_NAME in", values, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameNotIn(List<String> values) {
            addCriterion("MERCH_NAME not in", values, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameBetween(String value1, String value2) {
            addCriterion("MERCH_NAME between", value1, value2, "merchName");
            return (Criteria) this;
        }

        public Criteria andMerchNameNotBetween(String value1, String value2) {
            addCriterion("MERCH_NAME not between", value1, value2, "merchName");
            return (Criteria) this;
        }

        public Criteria andTranDateIsNull() {
            addCriterion("TRAN_DATE is null");
            return (Criteria) this;
        }

        public Criteria andTranDateIsNotNull() {
            addCriterion("TRAN_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andTranDateEqualTo(String value) {
            addCriterion("TRAN_DATE =", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotEqualTo(String value) {
            addCriterion("TRAN_DATE <>", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateGreaterThan(String value) {
            addCriterion("TRAN_DATE >", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateGreaterThanOrEqualTo(String value) {
            addCriterion("TRAN_DATE >=", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateLessThan(String value) {
            addCriterion("TRAN_DATE <", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateLessThanOrEqualTo(String value) {
            addCriterion("TRAN_DATE <=", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateLike(String value) {
            addCriterion("TRAN_DATE like", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotLike(String value) {
            addCriterion("TRAN_DATE not like", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateIn(List<String> values) {
            addCriterion("TRAN_DATE in", values, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotIn(List<String> values) {
            addCriterion("TRAN_DATE not in", values, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateBetween(String value1, String value2) {
            addCriterion("TRAN_DATE between", value1, value2, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotBetween(String value1, String value2) {
            addCriterion("TRAN_DATE not between", value1, value2, "tranDate");
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

        public Criteria andBalanceRcvAccIsNull() {
            addCriterion("BALANCE_RCV_ACC is null");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvAccIsNotNull() {
            addCriterion("BALANCE_RCV_ACC is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvAccEqualTo(String value) {
            addCriterion("BALANCE_RCV_ACC =", value, "balanceRcvAcc");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvAccNotEqualTo(String value) {
            addCriterion("BALANCE_RCV_ACC <>", value, "balanceRcvAcc");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvAccGreaterThan(String value) {
            addCriterion("BALANCE_RCV_ACC >", value, "balanceRcvAcc");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvAccGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_RCV_ACC >=", value, "balanceRcvAcc");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvAccLessThan(String value) {
            addCriterion("BALANCE_RCV_ACC <", value, "balanceRcvAcc");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvAccLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_RCV_ACC <=", value, "balanceRcvAcc");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvAccLike(String value) {
            addCriterion("BALANCE_RCV_ACC like", value, "balanceRcvAcc");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvAccNotLike(String value) {
            addCriterion("BALANCE_RCV_ACC not like", value, "balanceRcvAcc");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvAccIn(List<String> values) {
            addCriterion("BALANCE_RCV_ACC in", values, "balanceRcvAcc");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvAccNotIn(List<String> values) {
            addCriterion("BALANCE_RCV_ACC not in", values, "balanceRcvAcc");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvAccBetween(String value1, String value2) {
            addCriterion("BALANCE_RCV_ACC between", value1, value2, "balanceRcvAcc");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvAccNotBetween(String value1, String value2) {
            addCriterion("BALANCE_RCV_ACC not between", value1, value2, "balanceRcvAcc");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvBankIsNull() {
            addCriterion("BALANCE_RCV_BANK is null");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvBankIsNotNull() {
            addCriterion("BALANCE_RCV_BANK is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvBankEqualTo(String value) {
            addCriterion("BALANCE_RCV_BANK =", value, "balanceRcvBank");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvBankNotEqualTo(String value) {
            addCriterion("BALANCE_RCV_BANK <>", value, "balanceRcvBank");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvBankGreaterThan(String value) {
            addCriterion("BALANCE_RCV_BANK >", value, "balanceRcvBank");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvBankGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_RCV_BANK >=", value, "balanceRcvBank");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvBankLessThan(String value) {
            addCriterion("BALANCE_RCV_BANK <", value, "balanceRcvBank");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvBankLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_RCV_BANK <=", value, "balanceRcvBank");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvBankLike(String value) {
            addCriterion("BALANCE_RCV_BANK like", value, "balanceRcvBank");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvBankNotLike(String value) {
            addCriterion("BALANCE_RCV_BANK not like", value, "balanceRcvBank");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvBankIn(List<String> values) {
            addCriterion("BALANCE_RCV_BANK in", values, "balanceRcvBank");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvBankNotIn(List<String> values) {
            addCriterion("BALANCE_RCV_BANK not in", values, "balanceRcvBank");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvBankBetween(String value1, String value2) {
            addCriterion("BALANCE_RCV_BANK between", value1, value2, "balanceRcvBank");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvBankNotBetween(String value1, String value2) {
            addCriterion("BALANCE_RCV_BANK not between", value1, value2, "balanceRcvBank");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvNameIsNull() {
            addCriterion("BALANCE_RCV_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvNameIsNotNull() {
            addCriterion("BALANCE_RCV_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvNameEqualTo(String value) {
            addCriterion("BALANCE_RCV_NAME =", value, "balanceRcvName");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvNameNotEqualTo(String value) {
            addCriterion("BALANCE_RCV_NAME <>", value, "balanceRcvName");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvNameGreaterThan(String value) {
            addCriterion("BALANCE_RCV_NAME >", value, "balanceRcvName");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvNameGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_RCV_NAME >=", value, "balanceRcvName");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvNameLessThan(String value) {
            addCriterion("BALANCE_RCV_NAME <", value, "balanceRcvName");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvNameLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_RCV_NAME <=", value, "balanceRcvName");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvNameLike(String value) {
            addCriterion("BALANCE_RCV_NAME like", value, "balanceRcvName");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvNameNotLike(String value) {
            addCriterion("BALANCE_RCV_NAME not like", value, "balanceRcvName");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvNameIn(List<String> values) {
            addCriterion("BALANCE_RCV_NAME in", values, "balanceRcvName");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvNameNotIn(List<String> values) {
            addCriterion("BALANCE_RCV_NAME not in", values, "balanceRcvName");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvNameBetween(String value1, String value2) {
            addCriterion("BALANCE_RCV_NAME between", value1, value2, "balanceRcvName");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvNameNotBetween(String value1, String value2) {
            addCriterion("BALANCE_RCV_NAME not between", value1, value2, "balanceRcvName");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvCodeIsNull() {
            addCriterion("BALANCE_RCV_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvCodeIsNotNull() {
            addCriterion("BALANCE_RCV_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvCodeEqualTo(String value) {
            addCriterion("BALANCE_RCV_CODE =", value, "balanceRcvCode");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvCodeNotEqualTo(String value) {
            addCriterion("BALANCE_RCV_CODE <>", value, "balanceRcvCode");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvCodeGreaterThan(String value) {
            addCriterion("BALANCE_RCV_CODE >", value, "balanceRcvCode");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_RCV_CODE >=", value, "balanceRcvCode");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvCodeLessThan(String value) {
            addCriterion("BALANCE_RCV_CODE <", value, "balanceRcvCode");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvCodeLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_RCV_CODE <=", value, "balanceRcvCode");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvCodeLike(String value) {
            addCriterion("BALANCE_RCV_CODE like", value, "balanceRcvCode");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvCodeNotLike(String value) {
            addCriterion("BALANCE_RCV_CODE not like", value, "balanceRcvCode");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvCodeIn(List<String> values) {
            addCriterion("BALANCE_RCV_CODE in", values, "balanceRcvCode");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvCodeNotIn(List<String> values) {
            addCriterion("BALANCE_RCV_CODE not in", values, "balanceRcvCode");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvCodeBetween(String value1, String value2) {
            addCriterion("BALANCE_RCV_CODE between", value1, value2, "balanceRcvCode");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvCodeNotBetween(String value1, String value2) {
            addCriterion("BALANCE_RCV_CODE not between", value1, value2, "balanceRcvCode");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvTypeIsNull() {
            addCriterion("BALANCE_RCV_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvTypeIsNotNull() {
            addCriterion("BALANCE_RCV_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvTypeEqualTo(String value) {
            addCriterion("BALANCE_RCV_TYPE =", value, "balanceRcvType");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvTypeNotEqualTo(String value) {
            addCriterion("BALANCE_RCV_TYPE <>", value, "balanceRcvType");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvTypeGreaterThan(String value) {
            addCriterion("BALANCE_RCV_TYPE >", value, "balanceRcvType");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvTypeGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_RCV_TYPE >=", value, "balanceRcvType");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvTypeLessThan(String value) {
            addCriterion("BALANCE_RCV_TYPE <", value, "balanceRcvType");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvTypeLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_RCV_TYPE <=", value, "balanceRcvType");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvTypeLike(String value) {
            addCriterion("BALANCE_RCV_TYPE like", value, "balanceRcvType");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvTypeNotLike(String value) {
            addCriterion("BALANCE_RCV_TYPE not like", value, "balanceRcvType");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvTypeIn(List<String> values) {
            addCriterion("BALANCE_RCV_TYPE in", values, "balanceRcvType");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvTypeNotIn(List<String> values) {
            addCriterion("BALANCE_RCV_TYPE not in", values, "balanceRcvType");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvTypeBetween(String value1, String value2) {
            addCriterion("BALANCE_RCV_TYPE between", value1, value2, "balanceRcvType");
            return (Criteria) this;
        }

        public Criteria andBalanceRcvTypeNotBetween(String value1, String value2) {
            addCriterion("BALANCE_RCV_TYPE not between", value1, value2, "balanceRcvType");
            return (Criteria) this;
        }

        public Criteria andInputTimeIsNull() {
            addCriterion("INPUT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andInputTimeIsNotNull() {
            addCriterion("INPUT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andInputTimeEqualTo(String value) {
            addCriterion("INPUT_TIME =", value, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeNotEqualTo(String value) {
            addCriterion("INPUT_TIME <>", value, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeGreaterThan(String value) {
            addCriterion("INPUT_TIME >", value, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeGreaterThanOrEqualTo(String value) {
            addCriterion("INPUT_TIME >=", value, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeLessThan(String value) {
            addCriterion("INPUT_TIME <", value, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeLessThanOrEqualTo(String value) {
            addCriterion("INPUT_TIME <=", value, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeLike(String value) {
            addCriterion("INPUT_TIME like", value, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeNotLike(String value) {
            addCriterion("INPUT_TIME not like", value, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeIn(List<String> values) {
            addCriterion("INPUT_TIME in", values, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeNotIn(List<String> values) {
            addCriterion("INPUT_TIME not in", values, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeBetween(String value1, String value2) {
            addCriterion("INPUT_TIME between", value1, value2, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeNotBetween(String value1, String value2) {
            addCriterion("INPUT_TIME not between", value1, value2, "inputTime");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNull() {
            addCriterion("CHANNEL_ID is null");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNotNull() {
            addCriterion("CHANNEL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andChannelIdEqualTo(String value) {
            addCriterion("CHANNEL_ID =", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotEqualTo(String value) {
            addCriterion("CHANNEL_ID <>", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThan(String value) {
            addCriterion("CHANNEL_ID >", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThanOrEqualTo(String value) {
            addCriterion("CHANNEL_ID >=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThan(String value) {
            addCriterion("CHANNEL_ID <", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThanOrEqualTo(String value) {
            addCriterion("CHANNEL_ID <=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLike(String value) {
            addCriterion("CHANNEL_ID like", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotLike(String value) {
            addCriterion("CHANNEL_ID not like", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdIn(List<String> values) {
            addCriterion("CHANNEL_ID in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotIn(List<String> values) {
            addCriterion("CHANNEL_ID not in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdBetween(String value1, String value2) {
            addCriterion("CHANNEL_ID between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotBetween(String value1, String value2) {
            addCriterion("CHANNEL_ID not between", value1, value2, "channelId");
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

        public Criteria andBalanceLsIsNull() {
            addCriterion("BALANCE_LS is null");
            return (Criteria) this;
        }

        public Criteria andBalanceLsIsNotNull() {
            addCriterion("BALANCE_LS is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceLsEqualTo(String value) {
            addCriterion("BALANCE_LS =", value, "balanceLs");
            return (Criteria) this;
        }

        public Criteria andBalanceLsNotEqualTo(String value) {
            addCriterion("BALANCE_LS <>", value, "balanceLs");
            return (Criteria) this;
        }

        public Criteria andBalanceLsGreaterThan(String value) {
            addCriterion("BALANCE_LS >", value, "balanceLs");
            return (Criteria) this;
        }

        public Criteria andBalanceLsGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_LS >=", value, "balanceLs");
            return (Criteria) this;
        }

        public Criteria andBalanceLsLessThan(String value) {
            addCriterion("BALANCE_LS <", value, "balanceLs");
            return (Criteria) this;
        }

        public Criteria andBalanceLsLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_LS <=", value, "balanceLs");
            return (Criteria) this;
        }

        public Criteria andBalanceLsLike(String value) {
            addCriterion("BALANCE_LS like", value, "balanceLs");
            return (Criteria) this;
        }

        public Criteria andBalanceLsNotLike(String value) {
            addCriterion("BALANCE_LS not like", value, "balanceLs");
            return (Criteria) this;
        }

        public Criteria andBalanceLsIn(List<String> values) {
            addCriterion("BALANCE_LS in", values, "balanceLs");
            return (Criteria) this;
        }

        public Criteria andBalanceLsNotIn(List<String> values) {
            addCriterion("BALANCE_LS not in", values, "balanceLs");
            return (Criteria) this;
        }

        public Criteria andBalanceLsBetween(String value1, String value2) {
            addCriterion("BALANCE_LS between", value1, value2, "balanceLs");
            return (Criteria) this;
        }

        public Criteria andBalanceLsNotBetween(String value1, String value2) {
            addCriterion("BALANCE_LS not between", value1, value2, "balanceLs");
            return (Criteria) this;
        }

        public Criteria andCreateBatchTimeIsNull() {
            addCriterion("CREATE_BATCH_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateBatchTimeIsNotNull() {
            addCriterion("CREATE_BATCH_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateBatchTimeEqualTo(String value) {
            addCriterion("CREATE_BATCH_TIME =", value, "createBatchTime");
            return (Criteria) this;
        }

        public Criteria andCreateBatchTimeNotEqualTo(String value) {
            addCriterion("CREATE_BATCH_TIME <>", value, "createBatchTime");
            return (Criteria) this;
        }

        public Criteria andCreateBatchTimeGreaterThan(String value) {
            addCriterion("CREATE_BATCH_TIME >", value, "createBatchTime");
            return (Criteria) this;
        }

        public Criteria andCreateBatchTimeGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_BATCH_TIME >=", value, "createBatchTime");
            return (Criteria) this;
        }

        public Criteria andCreateBatchTimeLessThan(String value) {
            addCriterion("CREATE_BATCH_TIME <", value, "createBatchTime");
            return (Criteria) this;
        }

        public Criteria andCreateBatchTimeLessThanOrEqualTo(String value) {
            addCriterion("CREATE_BATCH_TIME <=", value, "createBatchTime");
            return (Criteria) this;
        }

        public Criteria andCreateBatchTimeLike(String value) {
            addCriterion("CREATE_BATCH_TIME like", value, "createBatchTime");
            return (Criteria) this;
        }

        public Criteria andCreateBatchTimeNotLike(String value) {
            addCriterion("CREATE_BATCH_TIME not like", value, "createBatchTime");
            return (Criteria) this;
        }

        public Criteria andCreateBatchTimeIn(List<String> values) {
            addCriterion("CREATE_BATCH_TIME in", values, "createBatchTime");
            return (Criteria) this;
        }

        public Criteria andCreateBatchTimeNotIn(List<String> values) {
            addCriterion("CREATE_BATCH_TIME not in", values, "createBatchTime");
            return (Criteria) this;
        }

        public Criteria andCreateBatchTimeBetween(String value1, String value2) {
            addCriterion("CREATE_BATCH_TIME between", value1, value2, "createBatchTime");
            return (Criteria) this;
        }

        public Criteria andCreateBatchTimeNotBetween(String value1, String value2) {
            addCriterion("CREATE_BATCH_TIME not between", value1, value2, "createBatchTime");
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

        public Criteria andFlagIsNull() {
            addCriterion("FLAG is null");
            return (Criteria) this;
        }

        public Criteria andFlagIsNotNull() {
            addCriterion("FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andFlagEqualTo(String value) {
            addCriterion("FLAG =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(String value) {
            addCriterion("FLAG <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(String value) {
            addCriterion("FLAG >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(String value) {
            addCriterion("FLAG >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(String value) {
            addCriterion("FLAG <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(String value) {
            addCriterion("FLAG <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLike(String value) {
            addCriterion("FLAG like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotLike(String value) {
            addCriterion("FLAG not like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<String> values) {
            addCriterion("FLAG in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<String> values) {
            addCriterion("FLAG not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(String value1, String value2) {
            addCriterion("FLAG between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(String value1, String value2) {
            addCriterion("FLAG not between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andErrDescIsNull() {
            addCriterion("ERR_DESC is null");
            return (Criteria) this;
        }

        public Criteria andErrDescIsNotNull() {
            addCriterion("ERR_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andErrDescEqualTo(String value) {
            addCriterion("ERR_DESC =", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescNotEqualTo(String value) {
            addCriterion("ERR_DESC <>", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescGreaterThan(String value) {
            addCriterion("ERR_DESC >", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescGreaterThanOrEqualTo(String value) {
            addCriterion("ERR_DESC >=", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescLessThan(String value) {
            addCriterion("ERR_DESC <", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescLessThanOrEqualTo(String value) {
            addCriterion("ERR_DESC <=", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescLike(String value) {
            addCriterion("ERR_DESC like", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescNotLike(String value) {
            addCriterion("ERR_DESC not like", value, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescIn(List<String> values) {
            addCriterion("ERR_DESC in", values, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescNotIn(List<String> values) {
            addCriterion("ERR_DESC not in", values, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescBetween(String value1, String value2) {
            addCriterion("ERR_DESC between", value1, value2, "errDesc");
            return (Criteria) this;
        }

        public Criteria andErrDescNotBetween(String value1, String value2) {
            addCriterion("ERR_DESC not between", value1, value2, "errDesc");
            return (Criteria) this;
        }

        public Criteria andDatasourceIsNull() {
            addCriterion("DATASOURCE is null");
            return (Criteria) this;
        }

        public Criteria andDatasourceIsNotNull() {
            addCriterion("DATASOURCE is not null");
            return (Criteria) this;
        }

        public Criteria andDatasourceEqualTo(String value) {
            addCriterion("DATASOURCE =", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceNotEqualTo(String value) {
            addCriterion("DATASOURCE <>", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceGreaterThan(String value) {
            addCriterion("DATASOURCE >", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceGreaterThanOrEqualTo(String value) {
            addCriterion("DATASOURCE >=", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceLessThan(String value) {
            addCriterion("DATASOURCE <", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceLessThanOrEqualTo(String value) {
            addCriterion("DATASOURCE <=", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceLike(String value) {
            addCriterion("DATASOURCE like", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceNotLike(String value) {
            addCriterion("DATASOURCE not like", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceIn(List<String> values) {
            addCriterion("DATASOURCE in", values, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceNotIn(List<String> values) {
            addCriterion("DATASOURCE not in", values, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceBetween(String value1, String value2) {
            addCriterion("DATASOURCE between", value1, value2, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceNotBetween(String value1, String value2) {
            addCriterion("DATASOURCE not between", value1, value2, "datasource");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNull() {
            addCriterion("ACCOUNT_ID is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNotNull() {
            addCriterion("ACCOUNT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(String value) {
            addCriterion("ACCOUNT_ID =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotEqualTo(String value) {
            addCriterion("ACCOUNT_ID <>", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThan(String value) {
            addCriterion("ACCOUNT_ID >", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_ID >=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThan(String value) {
            addCriterion("ACCOUNT_ID <", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_ID <=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLike(String value) {
            addCriterion("ACCOUNT_ID like", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotLike(String value) {
            addCriterion("ACCOUNT_ID not like", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIn(List<String> values) {
            addCriterion("ACCOUNT_ID in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotIn(List<String> values) {
            addCriterion("ACCOUNT_ID not in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdBetween(String value1, String value2) {
            addCriterion("ACCOUNT_ID between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotBetween(String value1, String value2) {
            addCriterion("ACCOUNT_ID not between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andPayDateIsNull() {
            addCriterion("PAY_DATE is null");
            return (Criteria) this;
        }

        public Criteria andPayDateIsNotNull() {
            addCriterion("PAY_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andPayDateEqualTo(String value) {
            addCriterion("PAY_DATE =", value, "payDate");
            return (Criteria) this;
        }

        public Criteria andPayDateNotEqualTo(String value) {
            addCriterion("PAY_DATE <>", value, "payDate");
            return (Criteria) this;
        }

        public Criteria andPayDateGreaterThan(String value) {
            addCriterion("PAY_DATE >", value, "payDate");
            return (Criteria) this;
        }

        public Criteria andPayDateGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_DATE >=", value, "payDate");
            return (Criteria) this;
        }

        public Criteria andPayDateLessThan(String value) {
            addCriterion("PAY_DATE <", value, "payDate");
            return (Criteria) this;
        }

        public Criteria andPayDateLessThanOrEqualTo(String value) {
            addCriterion("PAY_DATE <=", value, "payDate");
            return (Criteria) this;
        }

        public Criteria andPayDateLike(String value) {
            addCriterion("PAY_DATE like", value, "payDate");
            return (Criteria) this;
        }

        public Criteria andPayDateNotLike(String value) {
            addCriterion("PAY_DATE not like", value, "payDate");
            return (Criteria) this;
        }

        public Criteria andPayDateIn(List<String> values) {
            addCriterion("PAY_DATE in", values, "payDate");
            return (Criteria) this;
        }

        public Criteria andPayDateNotIn(List<String> values) {
            addCriterion("PAY_DATE not in", values, "payDate");
            return (Criteria) this;
        }

        public Criteria andPayDateBetween(String value1, String value2) {
            addCriterion("PAY_DATE between", value1, value2, "payDate");
            return (Criteria) this;
        }

        public Criteria andPayDateNotBetween(String value1, String value2) {
            addCriterion("PAY_DATE not between", value1, value2, "payDate");
            return (Criteria) this;
        }

        public Criteria andOrgAccountIdIsNull() {
            addCriterion("ORG_ACCOUNT_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrgAccountIdIsNotNull() {
            addCriterion("ORG_ACCOUNT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrgAccountIdEqualTo(String value) {
            addCriterion("ORG_ACCOUNT_ID =", value, "orgAccountId");
            return (Criteria) this;
        }

        public Criteria andOrgAccountIdNotEqualTo(String value) {
            addCriterion("ORG_ACCOUNT_ID <>", value, "orgAccountId");
            return (Criteria) this;
        }

        public Criteria andOrgAccountIdGreaterThan(String value) {
            addCriterion("ORG_ACCOUNT_ID >", value, "orgAccountId");
            return (Criteria) this;
        }

        public Criteria andOrgAccountIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_ACCOUNT_ID >=", value, "orgAccountId");
            return (Criteria) this;
        }

        public Criteria andOrgAccountIdLessThan(String value) {
            addCriterion("ORG_ACCOUNT_ID <", value, "orgAccountId");
            return (Criteria) this;
        }

        public Criteria andOrgAccountIdLessThanOrEqualTo(String value) {
            addCriterion("ORG_ACCOUNT_ID <=", value, "orgAccountId");
            return (Criteria) this;
        }

        public Criteria andOrgAccountIdLike(String value) {
            addCriterion("ORG_ACCOUNT_ID like", value, "orgAccountId");
            return (Criteria) this;
        }

        public Criteria andOrgAccountIdNotLike(String value) {
            addCriterion("ORG_ACCOUNT_ID not like", value, "orgAccountId");
            return (Criteria) this;
        }

        public Criteria andOrgAccountIdIn(List<String> values) {
            addCriterion("ORG_ACCOUNT_ID in", values, "orgAccountId");
            return (Criteria) this;
        }

        public Criteria andOrgAccountIdNotIn(List<String> values) {
            addCriterion("ORG_ACCOUNT_ID not in", values, "orgAccountId");
            return (Criteria) this;
        }

        public Criteria andOrgAccountIdBetween(String value1, String value2) {
            addCriterion("ORG_ACCOUNT_ID between", value1, value2, "orgAccountId");
            return (Criteria) this;
        }

        public Criteria andOrgAccountIdNotBetween(String value1, String value2) {
            addCriterion("ORG_ACCOUNT_ID not between", value1, value2, "orgAccountId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(String value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(String value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(String value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(String value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLike(String value) {
            addCriterion("UPDATE_TIME like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotLike(String value) {
            addCriterion("UPDATE_TIME not like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<String> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<String> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(String value1, String value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(String value1, String value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andReserveIsNull() {
            addCriterion("RESERVE is null");
            return (Criteria) this;
        }

        public Criteria andReserveIsNotNull() {
            addCriterion("RESERVE is not null");
            return (Criteria) this;
        }

        public Criteria andReserveEqualTo(String value) {
            addCriterion("RESERVE =", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveNotEqualTo(String value) {
            addCriterion("RESERVE <>", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveGreaterThan(String value) {
            addCriterion("RESERVE >", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveGreaterThanOrEqualTo(String value) {
            addCriterion("RESERVE >=", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveLessThan(String value) {
            addCriterion("RESERVE <", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveLessThanOrEqualTo(String value) {
            addCriterion("RESERVE <=", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveLike(String value) {
            addCriterion("RESERVE like", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveNotLike(String value) {
            addCriterion("RESERVE not like", value, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveIn(List<String> values) {
            addCriterion("RESERVE in", values, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveNotIn(List<String> values) {
            addCriterion("RESERVE not in", values, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveBetween(String value1, String value2) {
            addCriterion("RESERVE between", value1, value2, "reserve");
            return (Criteria) this;
        }

        public Criteria andReserveNotBetween(String value1, String value2) {
            addCriterion("RESERVE not between", value1, value2, "reserve");
            return (Criteria) this;
        }

        public Criteria andMemoIsNull() {
            addCriterion("MEMO is null");
            return (Criteria) this;
        }

        public Criteria andMemoIsNotNull() {
            addCriterion("MEMO is not null");
            return (Criteria) this;
        }

        public Criteria andMemoEqualTo(String value) {
            addCriterion("MEMO =", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotEqualTo(String value) {
            addCriterion("MEMO <>", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThan(String value) {
            addCriterion("MEMO >", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThanOrEqualTo(String value) {
            addCriterion("MEMO >=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThan(String value) {
            addCriterion("MEMO <", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThanOrEqualTo(String value) {
            addCriterion("MEMO <=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLike(String value) {
            addCriterion("MEMO like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotLike(String value) {
            addCriterion("MEMO not like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoIn(List<String> values) {
            addCriterion("MEMO in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotIn(List<String> values) {
            addCriterion("MEMO not in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoBetween(String value1, String value2) {
            addCriterion("MEMO between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotBetween(String value1, String value2) {
            addCriterion("MEMO not between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andSysflowsourceIsNull() {
            addCriterion("SYSFLOWSOURCE is null");
            return (Criteria) this;
        }

        public Criteria andSysflowsourceIsNotNull() {
            addCriterion("SYSFLOWSOURCE is not null");
            return (Criteria) this;
        }

        public Criteria andSysflowsourceEqualTo(String value) {
            addCriterion("SYSFLOWSOURCE =", value, "sysflowsource");
            return (Criteria) this;
        }

        public Criteria andSysflowsourceNotEqualTo(String value) {
            addCriterion("SYSFLOWSOURCE <>", value, "sysflowsource");
            return (Criteria) this;
        }

        public Criteria andSysflowsourceGreaterThan(String value) {
            addCriterion("SYSFLOWSOURCE >", value, "sysflowsource");
            return (Criteria) this;
        }

        public Criteria andSysflowsourceGreaterThanOrEqualTo(String value) {
            addCriterion("SYSFLOWSOURCE >=", value, "sysflowsource");
            return (Criteria) this;
        }

        public Criteria andSysflowsourceLessThan(String value) {
            addCriterion("SYSFLOWSOURCE <", value, "sysflowsource");
            return (Criteria) this;
        }

        public Criteria andSysflowsourceLessThanOrEqualTo(String value) {
            addCriterion("SYSFLOWSOURCE <=", value, "sysflowsource");
            return (Criteria) this;
        }

        public Criteria andSysflowsourceLike(String value) {
            addCriterion("SYSFLOWSOURCE like", value, "sysflowsource");
            return (Criteria) this;
        }

        public Criteria andSysflowsourceNotLike(String value) {
            addCriterion("SYSFLOWSOURCE not like", value, "sysflowsource");
            return (Criteria) this;
        }

        public Criteria andSysflowsourceIn(List<String> values) {
            addCriterion("SYSFLOWSOURCE in", values, "sysflowsource");
            return (Criteria) this;
        }

        public Criteria andSysflowsourceNotIn(List<String> values) {
            addCriterion("SYSFLOWSOURCE not in", values, "sysflowsource");
            return (Criteria) this;
        }

        public Criteria andSysflowsourceBetween(String value1, String value2) {
            addCriterion("SYSFLOWSOURCE between", value1, value2, "sysflowsource");
            return (Criteria) this;
        }

        public Criteria andSysflowsourceNotBetween(String value1, String value2) {
            addCriterion("SYSFLOWSOURCE not between", value1, value2, "sysflowsource");
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