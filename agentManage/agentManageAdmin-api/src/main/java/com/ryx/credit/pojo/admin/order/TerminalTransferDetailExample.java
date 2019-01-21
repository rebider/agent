package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TerminalTransferDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public TerminalTransferDetailExample() {
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

        public Criteria andTerminalTransferIdIsNull() {
            addCriterion("TERMINAL_TRANSFER_ID is null");
            return (Criteria) this;
        }

        public Criteria andTerminalTransferIdIsNotNull() {
            addCriterion("TERMINAL_TRANSFER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTerminalTransferIdEqualTo(String value) {
            addCriterion("TERMINAL_TRANSFER_ID =", value, "terminalTransferId");
            return (Criteria) this;
        }

        public Criteria andTerminalTransferIdNotEqualTo(String value) {
            addCriterion("TERMINAL_TRANSFER_ID <>", value, "terminalTransferId");
            return (Criteria) this;
        }

        public Criteria andTerminalTransferIdGreaterThan(String value) {
            addCriterion("TERMINAL_TRANSFER_ID >", value, "terminalTransferId");
            return (Criteria) this;
        }

        public Criteria andTerminalTransferIdGreaterThanOrEqualTo(String value) {
            addCriterion("TERMINAL_TRANSFER_ID >=", value, "terminalTransferId");
            return (Criteria) this;
        }

        public Criteria andTerminalTransferIdLessThan(String value) {
            addCriterion("TERMINAL_TRANSFER_ID <", value, "terminalTransferId");
            return (Criteria) this;
        }

        public Criteria andTerminalTransferIdLessThanOrEqualTo(String value) {
            addCriterion("TERMINAL_TRANSFER_ID <=", value, "terminalTransferId");
            return (Criteria) this;
        }

        public Criteria andTerminalTransferIdLike(String value) {
            addCriterion("TERMINAL_TRANSFER_ID like", value, "terminalTransferId");
            return (Criteria) this;
        }

        public Criteria andTerminalTransferIdNotLike(String value) {
            addCriterion("TERMINAL_TRANSFER_ID not like", value, "terminalTransferId");
            return (Criteria) this;
        }

        public Criteria andTerminalTransferIdIn(List<String> values) {
            addCriterion("TERMINAL_TRANSFER_ID in", values, "terminalTransferId");
            return (Criteria) this;
        }

        public Criteria andTerminalTransferIdNotIn(List<String> values) {
            addCriterion("TERMINAL_TRANSFER_ID not in", values, "terminalTransferId");
            return (Criteria) this;
        }

        public Criteria andTerminalTransferIdBetween(String value1, String value2) {
            addCriterion("TERMINAL_TRANSFER_ID between", value1, value2, "terminalTransferId");
            return (Criteria) this;
        }

        public Criteria andTerminalTransferIdNotBetween(String value1, String value2) {
            addCriterion("TERMINAL_TRANSFER_ID not between", value1, value2, "terminalTransferId");
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

        public Criteria andOriginalOrgIdIsNull() {
            addCriterion("ORIGINAL_ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgIdIsNotNull() {
            addCriterion("ORIGINAL_ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgIdEqualTo(String value) {
            addCriterion("ORIGINAL_ORG_ID =", value, "originalOrgId");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgIdNotEqualTo(String value) {
            addCriterion("ORIGINAL_ORG_ID <>", value, "originalOrgId");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgIdGreaterThan(String value) {
            addCriterion("ORIGINAL_ORG_ID >", value, "originalOrgId");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORIGINAL_ORG_ID >=", value, "originalOrgId");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgIdLessThan(String value) {
            addCriterion("ORIGINAL_ORG_ID <", value, "originalOrgId");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgIdLessThanOrEqualTo(String value) {
            addCriterion("ORIGINAL_ORG_ID <=", value, "originalOrgId");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgIdLike(String value) {
            addCriterion("ORIGINAL_ORG_ID like", value, "originalOrgId");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgIdNotLike(String value) {
            addCriterion("ORIGINAL_ORG_ID not like", value, "originalOrgId");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgIdIn(List<String> values) {
            addCriterion("ORIGINAL_ORG_ID in", values, "originalOrgId");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgIdNotIn(List<String> values) {
            addCriterion("ORIGINAL_ORG_ID not in", values, "originalOrgId");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgIdBetween(String value1, String value2) {
            addCriterion("ORIGINAL_ORG_ID between", value1, value2, "originalOrgId");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgIdNotBetween(String value1, String value2) {
            addCriterion("ORIGINAL_ORG_ID not between", value1, value2, "originalOrgId");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgNameIsNull() {
            addCriterion("ORIGINAL_ORG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgNameIsNotNull() {
            addCriterion("ORIGINAL_ORG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgNameEqualTo(String value) {
            addCriterion("ORIGINAL_ORG_NAME =", value, "originalOrgName");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgNameNotEqualTo(String value) {
            addCriterion("ORIGINAL_ORG_NAME <>", value, "originalOrgName");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgNameGreaterThan(String value) {
            addCriterion("ORIGINAL_ORG_NAME >", value, "originalOrgName");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgNameGreaterThanOrEqualTo(String value) {
            addCriterion("ORIGINAL_ORG_NAME >=", value, "originalOrgName");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgNameLessThan(String value) {
            addCriterion("ORIGINAL_ORG_NAME <", value, "originalOrgName");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgNameLessThanOrEqualTo(String value) {
            addCriterion("ORIGINAL_ORG_NAME <=", value, "originalOrgName");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgNameLike(String value) {
            addCriterion("ORIGINAL_ORG_NAME like", value, "originalOrgName");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgNameNotLike(String value) {
            addCriterion("ORIGINAL_ORG_NAME not like", value, "originalOrgName");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgNameIn(List<String> values) {
            addCriterion("ORIGINAL_ORG_NAME in", values, "originalOrgName");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgNameNotIn(List<String> values) {
            addCriterion("ORIGINAL_ORG_NAME not in", values, "originalOrgName");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgNameBetween(String value1, String value2) {
            addCriterion("ORIGINAL_ORG_NAME between", value1, value2, "originalOrgName");
            return (Criteria) this;
        }

        public Criteria andOriginalOrgNameNotBetween(String value1, String value2) {
            addCriterion("ORIGINAL_ORG_NAME not between", value1, value2, "originalOrgName");
            return (Criteria) this;
        }

        public Criteria andOriginalBusIdIsNull() {
            addCriterion("ORIGINAL_BUS_ID is null");
            return (Criteria) this;
        }

        public Criteria andOriginalBusIdIsNotNull() {
            addCriterion("ORIGINAL_BUS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalBusIdEqualTo(String value) {
            addCriterion("ORIGINAL_BUS_ID =", value, "originalBusId");
            return (Criteria) this;
        }

        public Criteria andOriginalBusIdNotEqualTo(String value) {
            addCriterion("ORIGINAL_BUS_ID <>", value, "originalBusId");
            return (Criteria) this;
        }

        public Criteria andOriginalBusIdGreaterThan(String value) {
            addCriterion("ORIGINAL_BUS_ID >", value, "originalBusId");
            return (Criteria) this;
        }

        public Criteria andOriginalBusIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORIGINAL_BUS_ID >=", value, "originalBusId");
            return (Criteria) this;
        }

        public Criteria andOriginalBusIdLessThan(String value) {
            addCriterion("ORIGINAL_BUS_ID <", value, "originalBusId");
            return (Criteria) this;
        }

        public Criteria andOriginalBusIdLessThanOrEqualTo(String value) {
            addCriterion("ORIGINAL_BUS_ID <=", value, "originalBusId");
            return (Criteria) this;
        }

        public Criteria andOriginalBusIdLike(String value) {
            addCriterion("ORIGINAL_BUS_ID like", value, "originalBusId");
            return (Criteria) this;
        }

        public Criteria andOriginalBusIdNotLike(String value) {
            addCriterion("ORIGINAL_BUS_ID not like", value, "originalBusId");
            return (Criteria) this;
        }

        public Criteria andOriginalBusIdIn(List<String> values) {
            addCriterion("ORIGINAL_BUS_ID in", values, "originalBusId");
            return (Criteria) this;
        }

        public Criteria andOriginalBusIdNotIn(List<String> values) {
            addCriterion("ORIGINAL_BUS_ID not in", values, "originalBusId");
            return (Criteria) this;
        }

        public Criteria andOriginalBusIdBetween(String value1, String value2) {
            addCriterion("ORIGINAL_BUS_ID between", value1, value2, "originalBusId");
            return (Criteria) this;
        }

        public Criteria andOriginalBusIdNotBetween(String value1, String value2) {
            addCriterion("ORIGINAL_BUS_ID not between", value1, value2, "originalBusId");
            return (Criteria) this;
        }

        public Criteria andGoalOrgIdIsNull() {
            addCriterion("GOAL_ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andGoalOrgIdIsNotNull() {
            addCriterion("GOAL_ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andGoalOrgIdEqualTo(String value) {
            addCriterion("GOAL_ORG_ID =", value, "goalOrgId");
            return (Criteria) this;
        }

        public Criteria andGoalOrgIdNotEqualTo(String value) {
            addCriterion("GOAL_ORG_ID <>", value, "goalOrgId");
            return (Criteria) this;
        }

        public Criteria andGoalOrgIdGreaterThan(String value) {
            addCriterion("GOAL_ORG_ID >", value, "goalOrgId");
            return (Criteria) this;
        }

        public Criteria andGoalOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("GOAL_ORG_ID >=", value, "goalOrgId");
            return (Criteria) this;
        }

        public Criteria andGoalOrgIdLessThan(String value) {
            addCriterion("GOAL_ORG_ID <", value, "goalOrgId");
            return (Criteria) this;
        }

        public Criteria andGoalOrgIdLessThanOrEqualTo(String value) {
            addCriterion("GOAL_ORG_ID <=", value, "goalOrgId");
            return (Criteria) this;
        }

        public Criteria andGoalOrgIdLike(String value) {
            addCriterion("GOAL_ORG_ID like", value, "goalOrgId");
            return (Criteria) this;
        }

        public Criteria andGoalOrgIdNotLike(String value) {
            addCriterion("GOAL_ORG_ID not like", value, "goalOrgId");
            return (Criteria) this;
        }

        public Criteria andGoalOrgIdIn(List<String> values) {
            addCriterion("GOAL_ORG_ID in", values, "goalOrgId");
            return (Criteria) this;
        }

        public Criteria andGoalOrgIdNotIn(List<String> values) {
            addCriterion("GOAL_ORG_ID not in", values, "goalOrgId");
            return (Criteria) this;
        }

        public Criteria andGoalOrgIdBetween(String value1, String value2) {
            addCriterion("GOAL_ORG_ID between", value1, value2, "goalOrgId");
            return (Criteria) this;
        }

        public Criteria andGoalOrgIdNotBetween(String value1, String value2) {
            addCriterion("GOAL_ORG_ID not between", value1, value2, "goalOrgId");
            return (Criteria) this;
        }

        public Criteria andGoalOrgNameIsNull() {
            addCriterion("GOAL_ORG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andGoalOrgNameIsNotNull() {
            addCriterion("GOAL_ORG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andGoalOrgNameEqualTo(String value) {
            addCriterion("GOAL_ORG_NAME =", value, "goalOrgName");
            return (Criteria) this;
        }

        public Criteria andGoalOrgNameNotEqualTo(String value) {
            addCriterion("GOAL_ORG_NAME <>", value, "goalOrgName");
            return (Criteria) this;
        }

        public Criteria andGoalOrgNameGreaterThan(String value) {
            addCriterion("GOAL_ORG_NAME >", value, "goalOrgName");
            return (Criteria) this;
        }

        public Criteria andGoalOrgNameGreaterThanOrEqualTo(String value) {
            addCriterion("GOAL_ORG_NAME >=", value, "goalOrgName");
            return (Criteria) this;
        }

        public Criteria andGoalOrgNameLessThan(String value) {
            addCriterion("GOAL_ORG_NAME <", value, "goalOrgName");
            return (Criteria) this;
        }

        public Criteria andGoalOrgNameLessThanOrEqualTo(String value) {
            addCriterion("GOAL_ORG_NAME <=", value, "goalOrgName");
            return (Criteria) this;
        }

        public Criteria andGoalOrgNameLike(String value) {
            addCriterion("GOAL_ORG_NAME like", value, "goalOrgName");
            return (Criteria) this;
        }

        public Criteria andGoalOrgNameNotLike(String value) {
            addCriterion("GOAL_ORG_NAME not like", value, "goalOrgName");
            return (Criteria) this;
        }

        public Criteria andGoalOrgNameIn(List<String> values) {
            addCriterion("GOAL_ORG_NAME in", values, "goalOrgName");
            return (Criteria) this;
        }

        public Criteria andGoalOrgNameNotIn(List<String> values) {
            addCriterion("GOAL_ORG_NAME not in", values, "goalOrgName");
            return (Criteria) this;
        }

        public Criteria andGoalOrgNameBetween(String value1, String value2) {
            addCriterion("GOAL_ORG_NAME between", value1, value2, "goalOrgName");
            return (Criteria) this;
        }

        public Criteria andGoalOrgNameNotBetween(String value1, String value2) {
            addCriterion("GOAL_ORG_NAME not between", value1, value2, "goalOrgName");
            return (Criteria) this;
        }

        public Criteria andGoalBusIdIsNull() {
            addCriterion("GOAL_BUS_ID is null");
            return (Criteria) this;
        }

        public Criteria andGoalBusIdIsNotNull() {
            addCriterion("GOAL_BUS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andGoalBusIdEqualTo(String value) {
            addCriterion("GOAL_BUS_ID =", value, "goalBusId");
            return (Criteria) this;
        }

        public Criteria andGoalBusIdNotEqualTo(String value) {
            addCriterion("GOAL_BUS_ID <>", value, "goalBusId");
            return (Criteria) this;
        }

        public Criteria andGoalBusIdGreaterThan(String value) {
            addCriterion("GOAL_BUS_ID >", value, "goalBusId");
            return (Criteria) this;
        }

        public Criteria andGoalBusIdGreaterThanOrEqualTo(String value) {
            addCriterion("GOAL_BUS_ID >=", value, "goalBusId");
            return (Criteria) this;
        }

        public Criteria andGoalBusIdLessThan(String value) {
            addCriterion("GOAL_BUS_ID <", value, "goalBusId");
            return (Criteria) this;
        }

        public Criteria andGoalBusIdLessThanOrEqualTo(String value) {
            addCriterion("GOAL_BUS_ID <=", value, "goalBusId");
            return (Criteria) this;
        }

        public Criteria andGoalBusIdLike(String value) {
            addCriterion("GOAL_BUS_ID like", value, "goalBusId");
            return (Criteria) this;
        }

        public Criteria andGoalBusIdNotLike(String value) {
            addCriterion("GOAL_BUS_ID not like", value, "goalBusId");
            return (Criteria) this;
        }

        public Criteria andGoalBusIdIn(List<String> values) {
            addCriterion("GOAL_BUS_ID in", values, "goalBusId");
            return (Criteria) this;
        }

        public Criteria andGoalBusIdNotIn(List<String> values) {
            addCriterion("GOAL_BUS_ID not in", values, "goalBusId");
            return (Criteria) this;
        }

        public Criteria andGoalBusIdBetween(String value1, String value2) {
            addCriterion("GOAL_BUS_ID between", value1, value2, "goalBusId");
            return (Criteria) this;
        }

        public Criteria andGoalBusIdNotBetween(String value1, String value2) {
            addCriterion("GOAL_BUS_ID not between", value1, value2, "goalBusId");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeIsNull() {
            addCriterion("ADJUST_TIME is null");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeIsNotNull() {
            addCriterion("ADJUST_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeEqualTo(Date value) {
            addCriterion("ADJUST_TIME =", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeNotEqualTo(Date value) {
            addCriterion("ADJUST_TIME <>", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeGreaterThan(Date value) {
            addCriterion("ADJUST_TIME >", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ADJUST_TIME >=", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeLessThan(Date value) {
            addCriterion("ADJUST_TIME <", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeLessThanOrEqualTo(Date value) {
            addCriterion("ADJUST_TIME <=", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeIn(List<Date> values) {
            addCriterion("ADJUST_TIME in", values, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeNotIn(List<Date> values) {
            addCriterion("ADJUST_TIME not in", values, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeBetween(Date value1, Date value2) {
            addCriterion("ADJUST_TIME between", value1, value2, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeNotBetween(Date value1, Date value2) {
            addCriterion("ADJUST_TIME not between", value1, value2, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andSnCountIsNull() {
            addCriterion("SN_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andSnCountIsNotNull() {
            addCriterion("SN_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andSnCountEqualTo(BigDecimal value) {
            addCriterion("SN_COUNT =", value, "snCount");
            return (Criteria) this;
        }

        public Criteria andSnCountNotEqualTo(BigDecimal value) {
            addCriterion("SN_COUNT <>", value, "snCount");
            return (Criteria) this;
        }

        public Criteria andSnCountGreaterThan(BigDecimal value) {
            addCriterion("SN_COUNT >", value, "snCount");
            return (Criteria) this;
        }

        public Criteria andSnCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SN_COUNT >=", value, "snCount");
            return (Criteria) this;
        }

        public Criteria andSnCountLessThan(BigDecimal value) {
            addCriterion("SN_COUNT <", value, "snCount");
            return (Criteria) this;
        }

        public Criteria andSnCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SN_COUNT <=", value, "snCount");
            return (Criteria) this;
        }

        public Criteria andSnCountIn(List<BigDecimal> values) {
            addCriterion("SN_COUNT in", values, "snCount");
            return (Criteria) this;
        }

        public Criteria andSnCountNotIn(List<BigDecimal> values) {
            addCriterion("SN_COUNT not in", values, "snCount");
            return (Criteria) this;
        }

        public Criteria andSnCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SN_COUNT between", value1, value2, "snCount");
            return (Criteria) this;
        }

        public Criteria andSnCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SN_COUNT not between", value1, value2, "snCount");
            return (Criteria) this;
        }

        public Criteria andButtJointPersonIsNull() {
            addCriterion("BUTT_JOINT_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andButtJointPersonIsNotNull() {
            addCriterion("BUTT_JOINT_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andButtJointPersonEqualTo(String value) {
            addCriterion("BUTT_JOINT_PERSON =", value, "buttJointPerson");
            return (Criteria) this;
        }

        public Criteria andButtJointPersonNotEqualTo(String value) {
            addCriterion("BUTT_JOINT_PERSON <>", value, "buttJointPerson");
            return (Criteria) this;
        }

        public Criteria andButtJointPersonGreaterThan(String value) {
            addCriterion("BUTT_JOINT_PERSON >", value, "buttJointPerson");
            return (Criteria) this;
        }

        public Criteria andButtJointPersonGreaterThanOrEqualTo(String value) {
            addCriterion("BUTT_JOINT_PERSON >=", value, "buttJointPerson");
            return (Criteria) this;
        }

        public Criteria andButtJointPersonLessThan(String value) {
            addCriterion("BUTT_JOINT_PERSON <", value, "buttJointPerson");
            return (Criteria) this;
        }

        public Criteria andButtJointPersonLessThanOrEqualTo(String value) {
            addCriterion("BUTT_JOINT_PERSON <=", value, "buttJointPerson");
            return (Criteria) this;
        }

        public Criteria andButtJointPersonLike(String value) {
            addCriterion("BUTT_JOINT_PERSON like", value, "buttJointPerson");
            return (Criteria) this;
        }

        public Criteria andButtJointPersonNotLike(String value) {
            addCriterion("BUTT_JOINT_PERSON not like", value, "buttJointPerson");
            return (Criteria) this;
        }

        public Criteria andButtJointPersonIn(List<String> values) {
            addCriterion("BUTT_JOINT_PERSON in", values, "buttJointPerson");
            return (Criteria) this;
        }

        public Criteria andButtJointPersonNotIn(List<String> values) {
            addCriterion("BUTT_JOINT_PERSON not in", values, "buttJointPerson");
            return (Criteria) this;
        }

        public Criteria andButtJointPersonBetween(String value1, String value2) {
            addCriterion("BUTT_JOINT_PERSON between", value1, value2, "buttJointPerson");
            return (Criteria) this;
        }

        public Criteria andButtJointPersonNotBetween(String value1, String value2) {
            addCriterion("BUTT_JOINT_PERSON not between", value1, value2, "buttJointPerson");
            return (Criteria) this;
        }

        public Criteria andAdjustStatusIsNull() {
            addCriterion("ADJUST_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andAdjustStatusIsNotNull() {
            addCriterion("ADJUST_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustStatusEqualTo(BigDecimal value) {
            addCriterion("ADJUST_STATUS =", value, "adjustStatus");
            return (Criteria) this;
        }

        public Criteria andAdjustStatusNotEqualTo(BigDecimal value) {
            addCriterion("ADJUST_STATUS <>", value, "adjustStatus");
            return (Criteria) this;
        }

        public Criteria andAdjustStatusGreaterThan(BigDecimal value) {
            addCriterion("ADJUST_STATUS >", value, "adjustStatus");
            return (Criteria) this;
        }

        public Criteria andAdjustStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ADJUST_STATUS >=", value, "adjustStatus");
            return (Criteria) this;
        }

        public Criteria andAdjustStatusLessThan(BigDecimal value) {
            addCriterion("ADJUST_STATUS <", value, "adjustStatus");
            return (Criteria) this;
        }

        public Criteria andAdjustStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ADJUST_STATUS <=", value, "adjustStatus");
            return (Criteria) this;
        }

        public Criteria andAdjustStatusIn(List<BigDecimal> values) {
            addCriterion("ADJUST_STATUS in", values, "adjustStatus");
            return (Criteria) this;
        }

        public Criteria andAdjustStatusNotIn(List<BigDecimal> values) {
            addCriterion("ADJUST_STATUS not in", values, "adjustStatus");
            return (Criteria) this;
        }

        public Criteria andAdjustStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADJUST_STATUS between", value1, value2, "adjustStatus");
            return (Criteria) this;
        }

        public Criteria andAdjustStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADJUST_STATUS not between", value1, value2, "adjustStatus");
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

        public Criteria andBatchNumIsNull() {
            addCriterion("BATCH_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBatchNumIsNotNull() {
            addCriterion("BATCH_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNumEqualTo(String value) {
            addCriterion("BATCH_NUM =", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotEqualTo(String value) {
            addCriterion("BATCH_NUM <>", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumGreaterThan(String value) {
            addCriterion("BATCH_NUM >", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumGreaterThanOrEqualTo(String value) {
            addCriterion("BATCH_NUM >=", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumLessThan(String value) {
            addCriterion("BATCH_NUM <", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumLessThanOrEqualTo(String value) {
            addCriterion("BATCH_NUM <=", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumLike(String value) {
            addCriterion("BATCH_NUM like", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotLike(String value) {
            addCriterion("BATCH_NUM not like", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumIn(List<String> values) {
            addCriterion("BATCH_NUM in", values, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotIn(List<String> values) {
            addCriterion("BATCH_NUM not in", values, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumBetween(String value1, String value2) {
            addCriterion("BATCH_NUM between", value1, value2, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotBetween(String value1, String value2) {
            addCriterion("BATCH_NUM not between", value1, value2, "batchNum");
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

        public Criteria andPlatformTypeIsNull() {
            addCriterion("PLATFORM_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeIsNotNull() {
            addCriterion("PLATFORM_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeEqualTo(BigDecimal value) {
            addCriterion("PLATFORM_TYPE =", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotEqualTo(BigDecimal value) {
            addCriterion("PLATFORM_TYPE <>", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeGreaterThan(BigDecimal value) {
            addCriterion("PLATFORM_TYPE >", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PLATFORM_TYPE >=", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLessThan(BigDecimal value) {
            addCriterion("PLATFORM_TYPE <", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PLATFORM_TYPE <=", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeIn(List<BigDecimal> values) {
            addCriterion("PLATFORM_TYPE in", values, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotIn(List<BigDecimal> values) {
            addCriterion("PLATFORM_TYPE not in", values, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLATFORM_TYPE between", value1, value2, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLATFORM_TYPE not between", value1, value2, "platformType");
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