package com.ryx.credit.machine.entity;

import com.ryx.credit.common.util.Page;
import java.util.ArrayList;
import java.util.List;

public class ImsTermWarehouseLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ImsTermWarehouseLogExample() {
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

        public Criteria andWdIdIsNull() {
            addCriterion("WD_ID is null");
            return (Criteria) this;
        }

        public Criteria andWdIdIsNotNull() {
            addCriterion("WD_ID is not null");
            return (Criteria) this;
        }

        public Criteria andWdIdEqualTo(String value) {
            addCriterion("WD_ID =", value, "wdId");
            return (Criteria) this;
        }

        public Criteria andWdIdNotEqualTo(String value) {
            addCriterion("WD_ID <>", value, "wdId");
            return (Criteria) this;
        }

        public Criteria andWdIdGreaterThan(String value) {
            addCriterion("WD_ID >", value, "wdId");
            return (Criteria) this;
        }

        public Criteria andWdIdGreaterThanOrEqualTo(String value) {
            addCriterion("WD_ID >=", value, "wdId");
            return (Criteria) this;
        }

        public Criteria andWdIdLessThan(String value) {
            addCriterion("WD_ID <", value, "wdId");
            return (Criteria) this;
        }

        public Criteria andWdIdLessThanOrEqualTo(String value) {
            addCriterion("WD_ID <=", value, "wdId");
            return (Criteria) this;
        }

        public Criteria andWdIdLike(String value) {
            addCriterion("WD_ID like", value, "wdId");
            return (Criteria) this;
        }

        public Criteria andWdIdNotLike(String value) {
            addCriterion("WD_ID not like", value, "wdId");
            return (Criteria) this;
        }

        public Criteria andWdIdIn(List<String> values) {
            addCriterion("WD_ID in", values, "wdId");
            return (Criteria) this;
        }

        public Criteria andWdIdNotIn(List<String> values) {
            addCriterion("WD_ID not in", values, "wdId");
            return (Criteria) this;
        }

        public Criteria andWdIdBetween(String value1, String value2) {
            addCriterion("WD_ID between", value1, value2, "wdId");
            return (Criteria) this;
        }

        public Criteria andWdIdNotBetween(String value1, String value2) {
            addCriterion("WD_ID not between", value1, value2, "wdId");
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

        public Criteria andOldWarehouseDetailIsNull() {
            addCriterion("OLD_WAREHOUSE_DETAIL is null");
            return (Criteria) this;
        }

        public Criteria andOldWarehouseDetailIsNotNull() {
            addCriterion("OLD_WAREHOUSE_DETAIL is not null");
            return (Criteria) this;
        }

        public Criteria andOldWarehouseDetailEqualTo(String value) {
            addCriterion("OLD_WAREHOUSE_DETAIL =", value, "oldWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andOldWarehouseDetailNotEqualTo(String value) {
            addCriterion("OLD_WAREHOUSE_DETAIL <>", value, "oldWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andOldWarehouseDetailGreaterThan(String value) {
            addCriterion("OLD_WAREHOUSE_DETAIL >", value, "oldWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andOldWarehouseDetailGreaterThanOrEqualTo(String value) {
            addCriterion("OLD_WAREHOUSE_DETAIL >=", value, "oldWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andOldWarehouseDetailLessThan(String value) {
            addCriterion("OLD_WAREHOUSE_DETAIL <", value, "oldWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andOldWarehouseDetailLessThanOrEqualTo(String value) {
            addCriterion("OLD_WAREHOUSE_DETAIL <=", value, "oldWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andOldWarehouseDetailLike(String value) {
            addCriterion("OLD_WAREHOUSE_DETAIL like", value, "oldWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andOldWarehouseDetailNotLike(String value) {
            addCriterion("OLD_WAREHOUSE_DETAIL not like", value, "oldWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andOldWarehouseDetailIn(List<String> values) {
            addCriterion("OLD_WAREHOUSE_DETAIL in", values, "oldWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andOldWarehouseDetailNotIn(List<String> values) {
            addCriterion("OLD_WAREHOUSE_DETAIL not in", values, "oldWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andOldWarehouseDetailBetween(String value1, String value2) {
            addCriterion("OLD_WAREHOUSE_DETAIL between", value1, value2, "oldWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andOldWarehouseDetailNotBetween(String value1, String value2) {
            addCriterion("OLD_WAREHOUSE_DETAIL not between", value1, value2, "oldWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andNewWarehouseDetailIsNull() {
            addCriterion("NEW_WAREHOUSE_DETAIL is null");
            return (Criteria) this;
        }

        public Criteria andNewWarehouseDetailIsNotNull() {
            addCriterion("NEW_WAREHOUSE_DETAIL is not null");
            return (Criteria) this;
        }

        public Criteria andNewWarehouseDetailEqualTo(String value) {
            addCriterion("NEW_WAREHOUSE_DETAIL =", value, "newWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andNewWarehouseDetailNotEqualTo(String value) {
            addCriterion("NEW_WAREHOUSE_DETAIL <>", value, "newWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andNewWarehouseDetailGreaterThan(String value) {
            addCriterion("NEW_WAREHOUSE_DETAIL >", value, "newWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andNewWarehouseDetailGreaterThanOrEqualTo(String value) {
            addCriterion("NEW_WAREHOUSE_DETAIL >=", value, "newWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andNewWarehouseDetailLessThan(String value) {
            addCriterion("NEW_WAREHOUSE_DETAIL <", value, "newWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andNewWarehouseDetailLessThanOrEqualTo(String value) {
            addCriterion("NEW_WAREHOUSE_DETAIL <=", value, "newWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andNewWarehouseDetailLike(String value) {
            addCriterion("NEW_WAREHOUSE_DETAIL like", value, "newWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andNewWarehouseDetailNotLike(String value) {
            addCriterion("NEW_WAREHOUSE_DETAIL not like", value, "newWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andNewWarehouseDetailIn(List<String> values) {
            addCriterion("NEW_WAREHOUSE_DETAIL in", values, "newWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andNewWarehouseDetailNotIn(List<String> values) {
            addCriterion("NEW_WAREHOUSE_DETAIL not in", values, "newWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andNewWarehouseDetailBetween(String value1, String value2) {
            addCriterion("NEW_WAREHOUSE_DETAIL between", value1, value2, "newWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andNewWarehouseDetailNotBetween(String value1, String value2) {
            addCriterion("NEW_WAREHOUSE_DETAIL not between", value1, value2, "newWarehouseDetail");
            return (Criteria) this;
        }

        public Criteria andOperDescribeIsNull() {
            addCriterion("OPER_DESCRIBE is null");
            return (Criteria) this;
        }

        public Criteria andOperDescribeIsNotNull() {
            addCriterion("OPER_DESCRIBE is not null");
            return (Criteria) this;
        }

        public Criteria andOperDescribeEqualTo(String value) {
            addCriterion("OPER_DESCRIBE =", value, "operDescribe");
            return (Criteria) this;
        }

        public Criteria andOperDescribeNotEqualTo(String value) {
            addCriterion("OPER_DESCRIBE <>", value, "operDescribe");
            return (Criteria) this;
        }

        public Criteria andOperDescribeGreaterThan(String value) {
            addCriterion("OPER_DESCRIBE >", value, "operDescribe");
            return (Criteria) this;
        }

        public Criteria andOperDescribeGreaterThanOrEqualTo(String value) {
            addCriterion("OPER_DESCRIBE >=", value, "operDescribe");
            return (Criteria) this;
        }

        public Criteria andOperDescribeLessThan(String value) {
            addCriterion("OPER_DESCRIBE <", value, "operDescribe");
            return (Criteria) this;
        }

        public Criteria andOperDescribeLessThanOrEqualTo(String value) {
            addCriterion("OPER_DESCRIBE <=", value, "operDescribe");
            return (Criteria) this;
        }

        public Criteria andOperDescribeLike(String value) {
            addCriterion("OPER_DESCRIBE like", value, "operDescribe");
            return (Criteria) this;
        }

        public Criteria andOperDescribeNotLike(String value) {
            addCriterion("OPER_DESCRIBE not like", value, "operDescribe");
            return (Criteria) this;
        }

        public Criteria andOperDescribeIn(List<String> values) {
            addCriterion("OPER_DESCRIBE in", values, "operDescribe");
            return (Criteria) this;
        }

        public Criteria andOperDescribeNotIn(List<String> values) {
            addCriterion("OPER_DESCRIBE not in", values, "operDescribe");
            return (Criteria) this;
        }

        public Criteria andOperDescribeBetween(String value1, String value2) {
            addCriterion("OPER_DESCRIBE between", value1, value2, "operDescribe");
            return (Criteria) this;
        }

        public Criteria andOperDescribeNotBetween(String value1, String value2) {
            addCriterion("OPER_DESCRIBE not between", value1, value2, "operDescribe");
            return (Criteria) this;
        }

        public Criteria andOperTypeIsNull() {
            addCriterion("OPER_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andOperTypeIsNotNull() {
            addCriterion("OPER_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andOperTypeEqualTo(String value) {
            addCriterion("OPER_TYPE =", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeNotEqualTo(String value) {
            addCriterion("OPER_TYPE <>", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeGreaterThan(String value) {
            addCriterion("OPER_TYPE >", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeGreaterThanOrEqualTo(String value) {
            addCriterion("OPER_TYPE >=", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeLessThan(String value) {
            addCriterion("OPER_TYPE <", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeLessThanOrEqualTo(String value) {
            addCriterion("OPER_TYPE <=", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeLike(String value) {
            addCriterion("OPER_TYPE like", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeNotLike(String value) {
            addCriterion("OPER_TYPE not like", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeIn(List<String> values) {
            addCriterion("OPER_TYPE in", values, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeNotIn(List<String> values) {
            addCriterion("OPER_TYPE not in", values, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeBetween(String value1, String value2) {
            addCriterion("OPER_TYPE between", value1, value2, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeNotBetween(String value1, String value2) {
            addCriterion("OPER_TYPE not between", value1, value2, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTimeIsNull() {
            addCriterion("OPER_TIME is null");
            return (Criteria) this;
        }

        public Criteria andOperTimeIsNotNull() {
            addCriterion("OPER_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andOperTimeEqualTo(String value) {
            addCriterion("OPER_TIME =", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeNotEqualTo(String value) {
            addCriterion("OPER_TIME <>", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeGreaterThan(String value) {
            addCriterion("OPER_TIME >", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeGreaterThanOrEqualTo(String value) {
            addCriterion("OPER_TIME >=", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeLessThan(String value) {
            addCriterion("OPER_TIME <", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeLessThanOrEqualTo(String value) {
            addCriterion("OPER_TIME <=", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeLike(String value) {
            addCriterion("OPER_TIME like", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeNotLike(String value) {
            addCriterion("OPER_TIME not like", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeIn(List<String> values) {
            addCriterion("OPER_TIME in", values, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeNotIn(List<String> values) {
            addCriterion("OPER_TIME not in", values, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeBetween(String value1, String value2) {
            addCriterion("OPER_TIME between", value1, value2, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeNotBetween(String value1, String value2) {
            addCriterion("OPER_TIME not between", value1, value2, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperUserIsNull() {
            addCriterion("OPER_USER is null");
            return (Criteria) this;
        }

        public Criteria andOperUserIsNotNull() {
            addCriterion("OPER_USER is not null");
            return (Criteria) this;
        }

        public Criteria andOperUserEqualTo(String value) {
            addCriterion("OPER_USER =", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserNotEqualTo(String value) {
            addCriterion("OPER_USER <>", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserGreaterThan(String value) {
            addCriterion("OPER_USER >", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserGreaterThanOrEqualTo(String value) {
            addCriterion("OPER_USER >=", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserLessThan(String value) {
            addCriterion("OPER_USER <", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserLessThanOrEqualTo(String value) {
            addCriterion("OPER_USER <=", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserLike(String value) {
            addCriterion("OPER_USER like", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserNotLike(String value) {
            addCriterion("OPER_USER not like", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserIn(List<String> values) {
            addCriterion("OPER_USER in", values, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserNotIn(List<String> values) {
            addCriterion("OPER_USER not in", values, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserBetween(String value1, String value2) {
            addCriterion("OPER_USER between", value1, value2, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserNotBetween(String value1, String value2) {
            addCriterion("OPER_USER not between", value1, value2, "operUser");
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