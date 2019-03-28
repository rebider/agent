package com.ryx.credit.machine.entity;

import com.ryx.credit.common.util.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImsPosExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ImsPosExample() {
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

        public Criteria andPosModelIsNull() {
            addCriterion("POS_MODEL is null");
            return (Criteria) this;
        }

        public Criteria andPosModelIsNotNull() {
            addCriterion("POS_MODEL is not null");
            return (Criteria) this;
        }

        public Criteria andPosModelEqualTo(String value) {
            addCriterion("POS_MODEL =", value, "posModel");
            return (Criteria) this;
        }

        public Criteria andPosModelNotEqualTo(String value) {
            addCriterion("POS_MODEL <>", value, "posModel");
            return (Criteria) this;
        }

        public Criteria andPosModelGreaterThan(String value) {
            addCriterion("POS_MODEL >", value, "posModel");
            return (Criteria) this;
        }

        public Criteria andPosModelGreaterThanOrEqualTo(String value) {
            addCriterion("POS_MODEL >=", value, "posModel");
            return (Criteria) this;
        }

        public Criteria andPosModelLessThan(String value) {
            addCriterion("POS_MODEL <", value, "posModel");
            return (Criteria) this;
        }

        public Criteria andPosModelLessThanOrEqualTo(String value) {
            addCriterion("POS_MODEL <=", value, "posModel");
            return (Criteria) this;
        }

        public Criteria andPosModelLike(String value) {
            addCriterion("POS_MODEL like", value, "posModel");
            return (Criteria) this;
        }

        public Criteria andPosModelNotLike(String value) {
            addCriterion("POS_MODEL not like", value, "posModel");
            return (Criteria) this;
        }

        public Criteria andPosModelIn(List<String> values) {
            addCriterion("POS_MODEL in", values, "posModel");
            return (Criteria) this;
        }

        public Criteria andPosModelNotIn(List<String> values) {
            addCriterion("POS_MODEL not in", values, "posModel");
            return (Criteria) this;
        }

        public Criteria andPosModelBetween(String value1, String value2) {
            addCriterion("POS_MODEL between", value1, value2, "posModel");
            return (Criteria) this;
        }

        public Criteria andPosModelNotBetween(String value1, String value2) {
            addCriterion("POS_MODEL not between", value1, value2, "posModel");
            return (Criteria) this;
        }

        public Criteria andManufIsNull() {
            addCriterion("MANUF is null");
            return (Criteria) this;
        }

        public Criteria andManufIsNotNull() {
            addCriterion("MANUF is not null");
            return (Criteria) this;
        }

        public Criteria andManufEqualTo(String value) {
            addCriterion("MANUF =", value, "manuf");
            return (Criteria) this;
        }

        public Criteria andManufNotEqualTo(String value) {
            addCriterion("MANUF <>", value, "manuf");
            return (Criteria) this;
        }

        public Criteria andManufGreaterThan(String value) {
            addCriterion("MANUF >", value, "manuf");
            return (Criteria) this;
        }

        public Criteria andManufGreaterThanOrEqualTo(String value) {
            addCriterion("MANUF >=", value, "manuf");
            return (Criteria) this;
        }

        public Criteria andManufLessThan(String value) {
            addCriterion("MANUF <", value, "manuf");
            return (Criteria) this;
        }

        public Criteria andManufLessThanOrEqualTo(String value) {
            addCriterion("MANUF <=", value, "manuf");
            return (Criteria) this;
        }

        public Criteria andManufLike(String value) {
            addCriterion("MANUF like", value, "manuf");
            return (Criteria) this;
        }

        public Criteria andManufNotLike(String value) {
            addCriterion("MANUF not like", value, "manuf");
            return (Criteria) this;
        }

        public Criteria andManufIn(List<String> values) {
            addCriterion("MANUF in", values, "manuf");
            return (Criteria) this;
        }

        public Criteria andManufNotIn(List<String> values) {
            addCriterion("MANUF not in", values, "manuf");
            return (Criteria) this;
        }

        public Criteria andManufBetween(String value1, String value2) {
            addCriterion("MANUF between", value1, value2, "manuf");
            return (Criteria) this;
        }

        public Criteria andManufNotBetween(String value1, String value2) {
            addCriterion("MANUF not between", value1, value2, "manuf");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIsNull() {
            addCriterion("DEVICE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIsNotNull() {
            addCriterion("DEVICE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeEqualTo(String value) {
            addCriterion("DEVICE_TYPE =", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotEqualTo(String value) {
            addCriterion("DEVICE_TYPE <>", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeGreaterThan(String value) {
            addCriterion("DEVICE_TYPE >", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("DEVICE_TYPE >=", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLessThan(String value) {
            addCriterion("DEVICE_TYPE <", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLessThanOrEqualTo(String value) {
            addCriterion("DEVICE_TYPE <=", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLike(String value) {
            addCriterion("DEVICE_TYPE like", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotLike(String value) {
            addCriterion("DEVICE_TYPE not like", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIn(List<String> values) {
            addCriterion("DEVICE_TYPE in", values, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotIn(List<String> values) {
            addCriterion("DEVICE_TYPE not in", values, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeBetween(String value1, String value2) {
            addCriterion("DEVICE_TYPE between", value1, value2, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotBetween(String value1, String value2) {
            addCriterion("DEVICE_TYPE not between", value1, value2, "deviceType");
            return (Criteria) this;
        }

        public Criteria andCommTypeIsNull() {
            addCriterion("COMM_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCommTypeIsNotNull() {
            addCriterion("COMM_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCommTypeEqualTo(String value) {
            addCriterion("COMM_TYPE =", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeNotEqualTo(String value) {
            addCriterion("COMM_TYPE <>", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeGreaterThan(String value) {
            addCriterion("COMM_TYPE >", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeGreaterThanOrEqualTo(String value) {
            addCriterion("COMM_TYPE >=", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeLessThan(String value) {
            addCriterion("COMM_TYPE <", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeLessThanOrEqualTo(String value) {
            addCriterion("COMM_TYPE <=", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeLike(String value) {
            addCriterion("COMM_TYPE like", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeNotLike(String value) {
            addCriterion("COMM_TYPE not like", value, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeIn(List<String> values) {
            addCriterion("COMM_TYPE in", values, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeNotIn(List<String> values) {
            addCriterion("COMM_TYPE not in", values, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeBetween(String value1, String value2) {
            addCriterion("COMM_TYPE between", value1, value2, "commType");
            return (Criteria) this;
        }

        public Criteria andCommTypeNotBetween(String value1, String value2) {
            addCriterion("COMM_TYPE not between", value1, value2, "commType");
            return (Criteria) this;
        }

        public Criteria andTmsInfoIsNull() {
            addCriterion("TMS_INFO is null");
            return (Criteria) this;
        }

        public Criteria andTmsInfoIsNotNull() {
            addCriterion("TMS_INFO is not null");
            return (Criteria) this;
        }

        public Criteria andTmsInfoEqualTo(String value) {
            addCriterion("TMS_INFO =", value, "tmsInfo");
            return (Criteria) this;
        }

        public Criteria andTmsInfoNotEqualTo(String value) {
            addCriterion("TMS_INFO <>", value, "tmsInfo");
            return (Criteria) this;
        }

        public Criteria andTmsInfoGreaterThan(String value) {
            addCriterion("TMS_INFO >", value, "tmsInfo");
            return (Criteria) this;
        }

        public Criteria andTmsInfoGreaterThanOrEqualTo(String value) {
            addCriterion("TMS_INFO >=", value, "tmsInfo");
            return (Criteria) this;
        }

        public Criteria andTmsInfoLessThan(String value) {
            addCriterion("TMS_INFO <", value, "tmsInfo");
            return (Criteria) this;
        }

        public Criteria andTmsInfoLessThanOrEqualTo(String value) {
            addCriterion("TMS_INFO <=", value, "tmsInfo");
            return (Criteria) this;
        }

        public Criteria andTmsInfoLike(String value) {
            addCriterion("TMS_INFO like", value, "tmsInfo");
            return (Criteria) this;
        }

        public Criteria andTmsInfoNotLike(String value) {
            addCriterion("TMS_INFO not like", value, "tmsInfo");
            return (Criteria) this;
        }

        public Criteria andTmsInfoIn(List<String> values) {
            addCriterion("TMS_INFO in", values, "tmsInfo");
            return (Criteria) this;
        }

        public Criteria andTmsInfoNotIn(List<String> values) {
            addCriterion("TMS_INFO not in", values, "tmsInfo");
            return (Criteria) this;
        }

        public Criteria andTmsInfoBetween(String value1, String value2) {
            addCriterion("TMS_INFO between", value1, value2, "tmsInfo");
            return (Criteria) this;
        }

        public Criteria andTmsInfoNotBetween(String value1, String value2) {
            addCriterion("TMS_INFO not between", value1, value2, "tmsInfo");
            return (Criteria) this;
        }

        public Criteria andTmsTimeIsNull() {
            addCriterion("TMS_TIME is null");
            return (Criteria) this;
        }

        public Criteria andTmsTimeIsNotNull() {
            addCriterion("TMS_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andTmsTimeEqualTo(Date value) {
            addCriterion("TMS_TIME =", value, "tmsTime");
            return (Criteria) this;
        }

        public Criteria andTmsTimeNotEqualTo(Date value) {
            addCriterion("TMS_TIME <>", value, "tmsTime");
            return (Criteria) this;
        }

        public Criteria andTmsTimeGreaterThan(Date value) {
            addCriterion("TMS_TIME >", value, "tmsTime");
            return (Criteria) this;
        }

        public Criteria andTmsTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("TMS_TIME >=", value, "tmsTime");
            return (Criteria) this;
        }

        public Criteria andTmsTimeLessThan(Date value) {
            addCriterion("TMS_TIME <", value, "tmsTime");
            return (Criteria) this;
        }

        public Criteria andTmsTimeLessThanOrEqualTo(Date value) {
            addCriterion("TMS_TIME <=", value, "tmsTime");
            return (Criteria) this;
        }

        public Criteria andTmsTimeIn(List<Date> values) {
            addCriterion("TMS_TIME in", values, "tmsTime");
            return (Criteria) this;
        }

        public Criteria andTmsTimeNotIn(List<Date> values) {
            addCriterion("TMS_TIME not in", values, "tmsTime");
            return (Criteria) this;
        }

        public Criteria andTmsTimeBetween(Date value1, Date value2) {
            addCriterion("TMS_TIME between", value1, value2, "tmsTime");
            return (Criteria) this;
        }

        public Criteria andTmsTimeNotBetween(Date value1, Date value2) {
            addCriterion("TMS_TIME not between", value1, value2, "tmsTime");
            return (Criteria) this;
        }

        public Criteria andTmsModelIsNull() {
            addCriterion("TMS_MODEL is null");
            return (Criteria) this;
        }

        public Criteria andTmsModelIsNotNull() {
            addCriterion("TMS_MODEL is not null");
            return (Criteria) this;
        }

        public Criteria andTmsModelEqualTo(String value) {
            addCriterion("TMS_MODEL =", value, "tmsModel");
            return (Criteria) this;
        }

        public Criteria andTmsModelNotEqualTo(String value) {
            addCriterion("TMS_MODEL <>", value, "tmsModel");
            return (Criteria) this;
        }

        public Criteria andTmsModelGreaterThan(String value) {
            addCriterion("TMS_MODEL >", value, "tmsModel");
            return (Criteria) this;
        }

        public Criteria andTmsModelGreaterThanOrEqualTo(String value) {
            addCriterion("TMS_MODEL >=", value, "tmsModel");
            return (Criteria) this;
        }

        public Criteria andTmsModelLessThan(String value) {
            addCriterion("TMS_MODEL <", value, "tmsModel");
            return (Criteria) this;
        }

        public Criteria andTmsModelLessThanOrEqualTo(String value) {
            addCriterion("TMS_MODEL <=", value, "tmsModel");
            return (Criteria) this;
        }

        public Criteria andTmsModelLike(String value) {
            addCriterion("TMS_MODEL like", value, "tmsModel");
            return (Criteria) this;
        }

        public Criteria andTmsModelNotLike(String value) {
            addCriterion("TMS_MODEL not like", value, "tmsModel");
            return (Criteria) this;
        }

        public Criteria andTmsModelIn(List<String> values) {
            addCriterion("TMS_MODEL in", values, "tmsModel");
            return (Criteria) this;
        }

        public Criteria andTmsModelNotIn(List<String> values) {
            addCriterion("TMS_MODEL not in", values, "tmsModel");
            return (Criteria) this;
        }

        public Criteria andTmsModelBetween(String value1, String value2) {
            addCriterion("TMS_MODEL between", value1, value2, "tmsModel");
            return (Criteria) this;
        }

        public Criteria andTmsModelNotBetween(String value1, String value2) {
            addCriterion("TMS_MODEL not between", value1, value2, "tmsModel");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("STATUS like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("STATUS not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andJdModelIsNull() {
            addCriterion("JD_MODEL is null");
            return (Criteria) this;
        }

        public Criteria andJdModelIsNotNull() {
            addCriterion("JD_MODEL is not null");
            return (Criteria) this;
        }

        public Criteria andJdModelEqualTo(String value) {
            addCriterion("JD_MODEL =", value, "jdModel");
            return (Criteria) this;
        }

        public Criteria andJdModelNotEqualTo(String value) {
            addCriterion("JD_MODEL <>", value, "jdModel");
            return (Criteria) this;
        }

        public Criteria andJdModelGreaterThan(String value) {
            addCriterion("JD_MODEL >", value, "jdModel");
            return (Criteria) this;
        }

        public Criteria andJdModelGreaterThanOrEqualTo(String value) {
            addCriterion("JD_MODEL >=", value, "jdModel");
            return (Criteria) this;
        }

        public Criteria andJdModelLessThan(String value) {
            addCriterion("JD_MODEL <", value, "jdModel");
            return (Criteria) this;
        }

        public Criteria andJdModelLessThanOrEqualTo(String value) {
            addCriterion("JD_MODEL <=", value, "jdModel");
            return (Criteria) this;
        }

        public Criteria andJdModelLike(String value) {
            addCriterion("JD_MODEL like", value, "jdModel");
            return (Criteria) this;
        }

        public Criteria andJdModelNotLike(String value) {
            addCriterion("JD_MODEL not like", value, "jdModel");
            return (Criteria) this;
        }

        public Criteria andJdModelIn(List<String> values) {
            addCriterion("JD_MODEL in", values, "jdModel");
            return (Criteria) this;
        }

        public Criteria andJdModelNotIn(List<String> values) {
            addCriterion("JD_MODEL not in", values, "jdModel");
            return (Criteria) this;
        }

        public Criteria andJdModelBetween(String value1, String value2) {
            addCriterion("JD_MODEL between", value1, value2, "jdModel");
            return (Criteria) this;
        }

        public Criteria andJdModelNotBetween(String value1, String value2) {
            addCriterion("JD_MODEL not between", value1, value2, "jdModel");
            return (Criteria) this;
        }

        public Criteria andIsOwnerVersionIsNull() {
            addCriterion("IS_OWNER_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andIsOwnerVersionIsNotNull() {
            addCriterion("IS_OWNER_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andIsOwnerVersionEqualTo(String value) {
            addCriterion("IS_OWNER_VERSION =", value, "isOwnerVersion");
            return (Criteria) this;
        }

        public Criteria andIsOwnerVersionNotEqualTo(String value) {
            addCriterion("IS_OWNER_VERSION <>", value, "isOwnerVersion");
            return (Criteria) this;
        }

        public Criteria andIsOwnerVersionGreaterThan(String value) {
            addCriterion("IS_OWNER_VERSION >", value, "isOwnerVersion");
            return (Criteria) this;
        }

        public Criteria andIsOwnerVersionGreaterThanOrEqualTo(String value) {
            addCriterion("IS_OWNER_VERSION >=", value, "isOwnerVersion");
            return (Criteria) this;
        }

        public Criteria andIsOwnerVersionLessThan(String value) {
            addCriterion("IS_OWNER_VERSION <", value, "isOwnerVersion");
            return (Criteria) this;
        }

        public Criteria andIsOwnerVersionLessThanOrEqualTo(String value) {
            addCriterion("IS_OWNER_VERSION <=", value, "isOwnerVersion");
            return (Criteria) this;
        }

        public Criteria andIsOwnerVersionLike(String value) {
            addCriterion("IS_OWNER_VERSION like", value, "isOwnerVersion");
            return (Criteria) this;
        }

        public Criteria andIsOwnerVersionNotLike(String value) {
            addCriterion("IS_OWNER_VERSION not like", value, "isOwnerVersion");
            return (Criteria) this;
        }

        public Criteria andIsOwnerVersionIn(List<String> values) {
            addCriterion("IS_OWNER_VERSION in", values, "isOwnerVersion");
            return (Criteria) this;
        }

        public Criteria andIsOwnerVersionNotIn(List<String> values) {
            addCriterion("IS_OWNER_VERSION not in", values, "isOwnerVersion");
            return (Criteria) this;
        }

        public Criteria andIsOwnerVersionBetween(String value1, String value2) {
            addCriterion("IS_OWNER_VERSION between", value1, value2, "isOwnerVersion");
            return (Criteria) this;
        }

        public Criteria andIsOwnerVersionNotBetween(String value1, String value2) {
            addCriterion("IS_OWNER_VERSION not between", value1, value2, "isOwnerVersion");
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