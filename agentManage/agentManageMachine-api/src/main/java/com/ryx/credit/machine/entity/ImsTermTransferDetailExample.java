package com.ryx.credit.machine.entity;

import com.ryx.credit.common.util.Page;

import java.util.ArrayList;
import java.util.List;

public class ImsTermTransferDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ImsTermTransferDetailExample() {
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

        public Criteria andMachineIdIsNull() {
            addCriterion("MACHINE_ID is null");
            return (Criteria) this;
        }

        public Criteria andMachineIdIsNotNull() {
            addCriterion("MACHINE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMachineIdEqualTo(String value) {
            addCriterion("MACHINE_ID =", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdNotEqualTo(String value) {
            addCriterion("MACHINE_ID <>", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdGreaterThan(String value) {
            addCriterion("MACHINE_ID >", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdGreaterThanOrEqualTo(String value) {
            addCriterion("MACHINE_ID >=", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdLessThan(String value) {
            addCriterion("MACHINE_ID <", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdLessThanOrEqualTo(String value) {
            addCriterion("MACHINE_ID <=", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdLike(String value) {
            addCriterion("MACHINE_ID like", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdNotLike(String value) {
            addCriterion("MACHINE_ID not like", value, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdIn(List<String> values) {
            addCriterion("MACHINE_ID in", values, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdNotIn(List<String> values) {
            addCriterion("MACHINE_ID not in", values, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdBetween(String value1, String value2) {
            addCriterion("MACHINE_ID between", value1, value2, "machineId");
            return (Criteria) this;
        }

        public Criteria andMachineIdNotBetween(String value1, String value2) {
            addCriterion("MACHINE_ID not between", value1, value2, "machineId");
            return (Criteria) this;
        }

        public Criteria andPosSnIsNull() {
            addCriterion("POS_SN is null");
            return (Criteria) this;
        }

        public Criteria andPosSnIsNotNull() {
            addCriterion("POS_SN is not null");
            return (Criteria) this;
        }

        public Criteria andPosSnEqualTo(String value) {
            addCriterion("POS_SN =", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnNotEqualTo(String value) {
            addCriterion("POS_SN <>", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnGreaterThan(String value) {
            addCriterion("POS_SN >", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnGreaterThanOrEqualTo(String value) {
            addCriterion("POS_SN >=", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnLessThan(String value) {
            addCriterion("POS_SN <", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnLessThanOrEqualTo(String value) {
            addCriterion("POS_SN <=", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnLike(String value) {
            addCriterion("POS_SN like", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnNotLike(String value) {
            addCriterion("POS_SN not like", value, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnIn(List<String> values) {
            addCriterion("POS_SN in", values, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnNotIn(List<String> values) {
            addCriterion("POS_SN not in", values, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnBetween(String value1, String value2) {
            addCriterion("POS_SN between", value1, value2, "posSn");
            return (Criteria) this;
        }

        public Criteria andPosSnNotBetween(String value1, String value2) {
            addCriterion("POS_SN not between", value1, value2, "posSn");
            return (Criteria) this;
        }

        public Criteria andYOrgIdIsNull() {
            addCriterion("Y_ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andYOrgIdIsNotNull() {
            addCriterion("Y_ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andYOrgIdEqualTo(String value) {
            addCriterion("Y_ORG_ID =", value, "yOrgId");
            return (Criteria) this;
        }

        public Criteria andYOrgIdNotEqualTo(String value) {
            addCriterion("Y_ORG_ID <>", value, "yOrgId");
            return (Criteria) this;
        }

        public Criteria andYOrgIdGreaterThan(String value) {
            addCriterion("Y_ORG_ID >", value, "yOrgId");
            return (Criteria) this;
        }

        public Criteria andYOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("Y_ORG_ID >=", value, "yOrgId");
            return (Criteria) this;
        }

        public Criteria andYOrgIdLessThan(String value) {
            addCriterion("Y_ORG_ID <", value, "yOrgId");
            return (Criteria) this;
        }

        public Criteria andYOrgIdLessThanOrEqualTo(String value) {
            addCriterion("Y_ORG_ID <=", value, "yOrgId");
            return (Criteria) this;
        }

        public Criteria andYOrgIdLike(String value) {
            addCriterion("Y_ORG_ID like", value, "yOrgId");
            return (Criteria) this;
        }

        public Criteria andYOrgIdNotLike(String value) {
            addCriterion("Y_ORG_ID not like", value, "yOrgId");
            return (Criteria) this;
        }

        public Criteria andYOrgIdIn(List<String> values) {
            addCriterion("Y_ORG_ID in", values, "yOrgId");
            return (Criteria) this;
        }

        public Criteria andYOrgIdNotIn(List<String> values) {
            addCriterion("Y_ORG_ID not in", values, "yOrgId");
            return (Criteria) this;
        }

        public Criteria andYOrgIdBetween(String value1, String value2) {
            addCriterion("Y_ORG_ID between", value1, value2, "yOrgId");
            return (Criteria) this;
        }

        public Criteria andYOrgIdNotBetween(String value1, String value2) {
            addCriterion("Y_ORG_ID not between", value1, value2, "yOrgId");
            return (Criteria) this;
        }

        public Criteria andNOrgIdIsNull() {
            addCriterion("N_ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andNOrgIdIsNotNull() {
            addCriterion("N_ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andNOrgIdEqualTo(String value) {
            addCriterion("N_ORG_ID =", value, "nOrgId");
            return (Criteria) this;
        }

        public Criteria andNOrgIdNotEqualTo(String value) {
            addCriterion("N_ORG_ID <>", value, "nOrgId");
            return (Criteria) this;
        }

        public Criteria andNOrgIdGreaterThan(String value) {
            addCriterion("N_ORG_ID >", value, "nOrgId");
            return (Criteria) this;
        }

        public Criteria andNOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("N_ORG_ID >=", value, "nOrgId");
            return (Criteria) this;
        }

        public Criteria andNOrgIdLessThan(String value) {
            addCriterion("N_ORG_ID <", value, "nOrgId");
            return (Criteria) this;
        }

        public Criteria andNOrgIdLessThanOrEqualTo(String value) {
            addCriterion("N_ORG_ID <=", value, "nOrgId");
            return (Criteria) this;
        }

        public Criteria andNOrgIdLike(String value) {
            addCriterion("N_ORG_ID like", value, "nOrgId");
            return (Criteria) this;
        }

        public Criteria andNOrgIdNotLike(String value) {
            addCriterion("N_ORG_ID not like", value, "nOrgId");
            return (Criteria) this;
        }

        public Criteria andNOrgIdIn(List<String> values) {
            addCriterion("N_ORG_ID in", values, "nOrgId");
            return (Criteria) this;
        }

        public Criteria andNOrgIdNotIn(List<String> values) {
            addCriterion("N_ORG_ID not in", values, "nOrgId");
            return (Criteria) this;
        }

        public Criteria andNOrgIdBetween(String value1, String value2) {
            addCriterion("N_ORG_ID between", value1, value2, "nOrgId");
            return (Criteria) this;
        }

        public Criteria andNOrgIdNotBetween(String value1, String value2) {
            addCriterion("N_ORG_ID not between", value1, value2, "nOrgId");
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

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("CREATE_TIME like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("CREATE_TIME not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreatePersonIsNull() {
            addCriterion("CREATE_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andCreatePersonIsNotNull() {
            addCriterion("CREATE_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andCreatePersonEqualTo(String value) {
            addCriterion("CREATE_PERSON =", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotEqualTo(String value) {
            addCriterion("CREATE_PERSON <>", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonGreaterThan(String value) {
            addCriterion("CREATE_PERSON >", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_PERSON >=", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonLessThan(String value) {
            addCriterion("CREATE_PERSON <", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonLessThanOrEqualTo(String value) {
            addCriterion("CREATE_PERSON <=", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonLike(String value) {
            addCriterion("CREATE_PERSON like", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotLike(String value) {
            addCriterion("CREATE_PERSON not like", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonIn(List<String> values) {
            addCriterion("CREATE_PERSON in", values, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotIn(List<String> values) {
            addCriterion("CREATE_PERSON not in", values, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonBetween(String value1, String value2) {
            addCriterion("CREATE_PERSON between", value1, value2, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotBetween(String value1, String value2) {
            addCriterion("CREATE_PERSON not between", value1, value2, "createPerson");
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

        public Criteria andOperOrgIdIsNull() {
            addCriterion("OPER_ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andOperOrgIdIsNotNull() {
            addCriterion("OPER_ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOperOrgIdEqualTo(String value) {
            addCriterion("OPER_ORG_ID =", value, "operOrgId");
            return (Criteria) this;
        }

        public Criteria andOperOrgIdNotEqualTo(String value) {
            addCriterion("OPER_ORG_ID <>", value, "operOrgId");
            return (Criteria) this;
        }

        public Criteria andOperOrgIdGreaterThan(String value) {
            addCriterion("OPER_ORG_ID >", value, "operOrgId");
            return (Criteria) this;
        }

        public Criteria andOperOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("OPER_ORG_ID >=", value, "operOrgId");
            return (Criteria) this;
        }

        public Criteria andOperOrgIdLessThan(String value) {
            addCriterion("OPER_ORG_ID <", value, "operOrgId");
            return (Criteria) this;
        }

        public Criteria andOperOrgIdLessThanOrEqualTo(String value) {
            addCriterion("OPER_ORG_ID <=", value, "operOrgId");
            return (Criteria) this;
        }

        public Criteria andOperOrgIdLike(String value) {
            addCriterion("OPER_ORG_ID like", value, "operOrgId");
            return (Criteria) this;
        }

        public Criteria andOperOrgIdNotLike(String value) {
            addCriterion("OPER_ORG_ID not like", value, "operOrgId");
            return (Criteria) this;
        }

        public Criteria andOperOrgIdIn(List<String> values) {
            addCriterion("OPER_ORG_ID in", values, "operOrgId");
            return (Criteria) this;
        }

        public Criteria andOperOrgIdNotIn(List<String> values) {
            addCriterion("OPER_ORG_ID not in", values, "operOrgId");
            return (Criteria) this;
        }

        public Criteria andOperOrgIdBetween(String value1, String value2) {
            addCriterion("OPER_ORG_ID between", value1, value2, "operOrgId");
            return (Criteria) this;
        }

        public Criteria andOperOrgIdNotBetween(String value1, String value2) {
            addCriterion("OPER_ORG_ID not between", value1, value2, "operOrgId");
            return (Criteria) this;
        }

        public Criteria andTransferIdIsNull() {
            addCriterion("TRANSFER_ID is null");
            return (Criteria) this;
        }

        public Criteria andTransferIdIsNotNull() {
            addCriterion("TRANSFER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTransferIdEqualTo(String value) {
            addCriterion("TRANSFER_ID =", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdNotEqualTo(String value) {
            addCriterion("TRANSFER_ID <>", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdGreaterThan(String value) {
            addCriterion("TRANSFER_ID >", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdGreaterThanOrEqualTo(String value) {
            addCriterion("TRANSFER_ID >=", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdLessThan(String value) {
            addCriterion("TRANSFER_ID <", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdLessThanOrEqualTo(String value) {
            addCriterion("TRANSFER_ID <=", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdLike(String value) {
            addCriterion("TRANSFER_ID like", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdNotLike(String value) {
            addCriterion("TRANSFER_ID not like", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdIn(List<String> values) {
            addCriterion("TRANSFER_ID in", values, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdNotIn(List<String> values) {
            addCriterion("TRANSFER_ID not in", values, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdBetween(String value1, String value2) {
            addCriterion("TRANSFER_ID between", value1, value2, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdNotBetween(String value1, String value2) {
            addCriterion("TRANSFER_ID not between", value1, value2, "transferId");
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