package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class imsOrganExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public imsOrganExample() {
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

        public Criteria andOrgIdIsNull() {
            addCriterion("ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNotNull() {
            addCriterion("ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrgIdEqualTo(String value) {
            addCriterion("ORG_ID =", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotEqualTo(String value) {
            addCriterion("ORG_ID <>", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThan(String value) {
            addCriterion("ORG_ID >", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_ID >=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThan(String value) {
            addCriterion("ORG_ID <", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThanOrEqualTo(String value) {
            addCriterion("ORG_ID <=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLike(String value) {
            addCriterion("ORG_ID like", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotLike(String value) {
            addCriterion("ORG_ID not like", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIn(List<String> values) {
            addCriterion("ORG_ID in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotIn(List<String> values) {
            addCriterion("ORG_ID not in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdBetween(String value1, String value2) {
            addCriterion("ORG_ID between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotBetween(String value1, String value2) {
            addCriterion("ORG_ID not between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andSupOrgIdIsNull() {
            addCriterion("SUP_ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andSupOrgIdIsNotNull() {
            addCriterion("SUP_ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSupOrgIdEqualTo(String value) {
            addCriterion("SUP_ORG_ID =", value, "supOrgId");
            return (Criteria) this;
        }

        public Criteria andSupOrgIdNotEqualTo(String value) {
            addCriterion("SUP_ORG_ID <>", value, "supOrgId");
            return (Criteria) this;
        }

        public Criteria andSupOrgIdGreaterThan(String value) {
            addCriterion("SUP_ORG_ID >", value, "supOrgId");
            return (Criteria) this;
        }

        public Criteria andSupOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUP_ORG_ID >=", value, "supOrgId");
            return (Criteria) this;
        }

        public Criteria andSupOrgIdLessThan(String value) {
            addCriterion("SUP_ORG_ID <", value, "supOrgId");
            return (Criteria) this;
        }

        public Criteria andSupOrgIdLessThanOrEqualTo(String value) {
            addCriterion("SUP_ORG_ID <=", value, "supOrgId");
            return (Criteria) this;
        }

        public Criteria andSupOrgIdLike(String value) {
            addCriterion("SUP_ORG_ID like", value, "supOrgId");
            return (Criteria) this;
        }

        public Criteria andSupOrgIdNotLike(String value) {
            addCriterion("SUP_ORG_ID not like", value, "supOrgId");
            return (Criteria) this;
        }

        public Criteria andSupOrgIdIn(List<String> values) {
            addCriterion("SUP_ORG_ID in", values, "supOrgId");
            return (Criteria) this;
        }

        public Criteria andSupOrgIdNotIn(List<String> values) {
            addCriterion("SUP_ORG_ID not in", values, "supOrgId");
            return (Criteria) this;
        }

        public Criteria andSupOrgIdBetween(String value1, String value2) {
            addCriterion("SUP_ORG_ID between", value1, value2, "supOrgId");
            return (Criteria) this;
        }

        public Criteria andSupOrgIdNotBetween(String value1, String value2) {
            addCriterion("SUP_ORG_ID not between", value1, value2, "supOrgId");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIsNull() {
            addCriterion("ORG_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIsNotNull() {
            addCriterion("ORG_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andOrgLevelEqualTo(Integer value) {
            addCriterion("ORG_LEVEL =", value, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelNotEqualTo(Integer value) {
            addCriterion("ORG_LEVEL <>", value, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelGreaterThan(Integer value) {
            addCriterion("ORG_LEVEL >", value, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("ORG_LEVEL >=", value, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelLessThan(Integer value) {
            addCriterion("ORG_LEVEL <", value, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelLessThanOrEqualTo(Integer value) {
            addCriterion("ORG_LEVEL <=", value, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIn(List<Integer> values) {
            addCriterion("ORG_LEVEL in", values, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelNotIn(List<Integer> values) {
            addCriterion("ORG_LEVEL not in", values, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelBetween(Integer value1, Integer value2) {
            addCriterion("ORG_LEVEL between", value1, value2, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andOrgLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("ORG_LEVEL not between", value1, value2, "orgLevel");
            return (Criteria) this;
        }

        public Criteria andUseOrganIsNull() {
            addCriterion("USE_ORGAN is null");
            return (Criteria) this;
        }

        public Criteria andUseOrganIsNotNull() {
            addCriterion("USE_ORGAN is not null");
            return (Criteria) this;
        }

        public Criteria andUseOrganEqualTo(String value) {
            addCriterion("USE_ORGAN =", value, "useOrgan");
            return (Criteria) this;
        }

        public Criteria andUseOrganNotEqualTo(String value) {
            addCriterion("USE_ORGAN <>", value, "useOrgan");
            return (Criteria) this;
        }

        public Criteria andUseOrganGreaterThan(String value) {
            addCriterion("USE_ORGAN >", value, "useOrgan");
            return (Criteria) this;
        }

        public Criteria andUseOrganGreaterThanOrEqualTo(String value) {
            addCriterion("USE_ORGAN >=", value, "useOrgan");
            return (Criteria) this;
        }

        public Criteria andUseOrganLessThan(String value) {
            addCriterion("USE_ORGAN <", value, "useOrgan");
            return (Criteria) this;
        }

        public Criteria andUseOrganLessThanOrEqualTo(String value) {
            addCriterion("USE_ORGAN <=", value, "useOrgan");
            return (Criteria) this;
        }

        public Criteria andUseOrganLike(String value) {
            addCriterion("USE_ORGAN like", value, "useOrgan");
            return (Criteria) this;
        }

        public Criteria andUseOrganNotLike(String value) {
            addCriterion("USE_ORGAN not like", value, "useOrgan");
            return (Criteria) this;
        }

        public Criteria andUseOrganIn(List<String> values) {
            addCriterion("USE_ORGAN in", values, "useOrgan");
            return (Criteria) this;
        }

        public Criteria andUseOrganNotIn(List<String> values) {
            addCriterion("USE_ORGAN not in", values, "useOrgan");
            return (Criteria) this;
        }

        public Criteria andUseOrganBetween(String value1, String value2) {
            addCriterion("USE_ORGAN between", value1, value2, "useOrgan");
            return (Criteria) this;
        }

        public Criteria andUseOrganNotBetween(String value1, String value2) {
            addCriterion("USE_ORGAN not between", value1, value2, "useOrgan");
            return (Criteria) this;
        }

        public Criteria andOrgNameIsNull() {
            addCriterion("ORG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andOrgNameIsNotNull() {
            addCriterion("ORG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andOrgNameEqualTo(String value) {
            addCriterion("ORG_NAME =", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotEqualTo(String value) {
            addCriterion("ORG_NAME <>", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameGreaterThan(String value) {
            addCriterion("ORG_NAME >", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_NAME >=", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLessThan(String value) {
            addCriterion("ORG_NAME <", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLessThanOrEqualTo(String value) {
            addCriterion("ORG_NAME <=", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLike(String value) {
            addCriterion("ORG_NAME like", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotLike(String value) {
            addCriterion("ORG_NAME not like", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameIn(List<String> values) {
            addCriterion("ORG_NAME in", values, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotIn(List<String> values) {
            addCriterion("ORG_NAME not in", values, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameBetween(String value1, String value2) {
            addCriterion("ORG_NAME between", value1, value2, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotBetween(String value1, String value2) {
            addCriterion("ORG_NAME not between", value1, value2, "orgName");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("PROVINCE is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("PROVINCE is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("PROVINCE =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("PROVINCE <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("PROVINCE >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("PROVINCE >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("PROVINCE <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("PROVINCE <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("PROVINCE like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("PROVINCE not like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("PROVINCE in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("PROVINCE not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("PROVINCE between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("PROVINCE not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("CITY is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("CITY is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("CITY =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("CITY <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("CITY >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("CITY >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("CITY <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("CITY <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("CITY like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("CITY not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("CITY in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("CITY not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("CITY between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("CITY not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityAreaIsNull() {
            addCriterion("CITY_AREA is null");
            return (Criteria) this;
        }

        public Criteria andCityAreaIsNotNull() {
            addCriterion("CITY_AREA is not null");
            return (Criteria) this;
        }

        public Criteria andCityAreaEqualTo(String value) {
            addCriterion("CITY_AREA =", value, "cityArea");
            return (Criteria) this;
        }

        public Criteria andCityAreaNotEqualTo(String value) {
            addCriterion("CITY_AREA <>", value, "cityArea");
            return (Criteria) this;
        }

        public Criteria andCityAreaGreaterThan(String value) {
            addCriterion("CITY_AREA >", value, "cityArea");
            return (Criteria) this;
        }

        public Criteria andCityAreaGreaterThanOrEqualTo(String value) {
            addCriterion("CITY_AREA >=", value, "cityArea");
            return (Criteria) this;
        }

        public Criteria andCityAreaLessThan(String value) {
            addCriterion("CITY_AREA <", value, "cityArea");
            return (Criteria) this;
        }

        public Criteria andCityAreaLessThanOrEqualTo(String value) {
            addCriterion("CITY_AREA <=", value, "cityArea");
            return (Criteria) this;
        }

        public Criteria andCityAreaLike(String value) {
            addCriterion("CITY_AREA like", value, "cityArea");
            return (Criteria) this;
        }

        public Criteria andCityAreaNotLike(String value) {
            addCriterion("CITY_AREA not like", value, "cityArea");
            return (Criteria) this;
        }

        public Criteria andCityAreaIn(List<String> values) {
            addCriterion("CITY_AREA in", values, "cityArea");
            return (Criteria) this;
        }

        public Criteria andCityAreaNotIn(List<String> values) {
            addCriterion("CITY_AREA not in", values, "cityArea");
            return (Criteria) this;
        }

        public Criteria andCityAreaBetween(String value1, String value2) {
            addCriterion("CITY_AREA between", value1, value2, "cityArea");
            return (Criteria) this;
        }

        public Criteria andCityAreaNotBetween(String value1, String value2) {
            addCriterion("CITY_AREA not between", value1, value2, "cityArea");
            return (Criteria) this;
        }

        public Criteria andIsLeafIsNull() {
            addCriterion("IS_LEAF is null");
            return (Criteria) this;
        }

        public Criteria andIsLeafIsNotNull() {
            addCriterion("IS_LEAF is not null");
            return (Criteria) this;
        }

        public Criteria andIsLeafEqualTo(String value) {
            addCriterion("IS_LEAF =", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafNotEqualTo(String value) {
            addCriterion("IS_LEAF <>", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafGreaterThan(String value) {
            addCriterion("IS_LEAF >", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafGreaterThanOrEqualTo(String value) {
            addCriterion("IS_LEAF >=", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafLessThan(String value) {
            addCriterion("IS_LEAF <", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafLessThanOrEqualTo(String value) {
            addCriterion("IS_LEAF <=", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafLike(String value) {
            addCriterion("IS_LEAF like", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafNotLike(String value) {
            addCriterion("IS_LEAF not like", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafIn(List<String> values) {
            addCriterion("IS_LEAF in", values, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafNotIn(List<String> values) {
            addCriterion("IS_LEAF not in", values, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafBetween(String value1, String value2) {
            addCriterion("IS_LEAF between", value1, value2, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafNotBetween(String value1, String value2) {
            addCriterion("IS_LEAF not between", value1, value2, "isLeaf");
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

        public Criteria andCancelTimeIsNull() {
            addCriterion("CANCEL_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCancelTimeIsNotNull() {
            addCriterion("CANCEL_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCancelTimeEqualTo(String value) {
            addCriterion("CANCEL_TIME =", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotEqualTo(String value) {
            addCriterion("CANCEL_TIME <>", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeGreaterThan(String value) {
            addCriterion("CANCEL_TIME >", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeGreaterThanOrEqualTo(String value) {
            addCriterion("CANCEL_TIME >=", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeLessThan(String value) {
            addCriterion("CANCEL_TIME <", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeLessThanOrEqualTo(String value) {
            addCriterion("CANCEL_TIME <=", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeLike(String value) {
            addCriterion("CANCEL_TIME like", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotLike(String value) {
            addCriterion("CANCEL_TIME not like", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeIn(List<String> values) {
            addCriterion("CANCEL_TIME in", values, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotIn(List<String> values) {
            addCriterion("CANCEL_TIME not in", values, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeBetween(String value1, String value2) {
            addCriterion("CANCEL_TIME between", value1, value2, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotBetween(String value1, String value2) {
            addCriterion("CANCEL_TIME not between", value1, value2, "cancelTime");
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

        public Criteria andTopOrgIdIsNull() {
            addCriterion("TOP_ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdIsNotNull() {
            addCriterion("TOP_ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdEqualTo(String value) {
            addCriterion("TOP_ORG_ID =", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdNotEqualTo(String value) {
            addCriterion("TOP_ORG_ID <>", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdGreaterThan(String value) {
            addCriterion("TOP_ORG_ID >", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("TOP_ORG_ID >=", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdLessThan(String value) {
            addCriterion("TOP_ORG_ID <", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdLessThanOrEqualTo(String value) {
            addCriterion("TOP_ORG_ID <=", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdLike(String value) {
            addCriterion("TOP_ORG_ID like", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdNotLike(String value) {
            addCriterion("TOP_ORG_ID not like", value, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdIn(List<String> values) {
            addCriterion("TOP_ORG_ID in", values, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdNotIn(List<String> values) {
            addCriterion("TOP_ORG_ID not in", values, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdBetween(String value1, String value2) {
            addCriterion("TOP_ORG_ID between", value1, value2, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andTopOrgIdNotBetween(String value1, String value2) {
            addCriterion("TOP_ORG_ID not between", value1, value2, "topOrgId");
            return (Criteria) this;
        }

        public Criteria andAtmLimitIsNull() {
            addCriterion("ATM_LIMIT is null");
            return (Criteria) this;
        }

        public Criteria andAtmLimitIsNotNull() {
            addCriterion("ATM_LIMIT is not null");
            return (Criteria) this;
        }

        public Criteria andAtmLimitEqualTo(BigDecimal value) {
            addCriterion("ATM_LIMIT =", value, "atmLimit");
            return (Criteria) this;
        }

        public Criteria andAtmLimitNotEqualTo(BigDecimal value) {
            addCriterion("ATM_LIMIT <>", value, "atmLimit");
            return (Criteria) this;
        }

        public Criteria andAtmLimitGreaterThan(BigDecimal value) {
            addCriterion("ATM_LIMIT >", value, "atmLimit");
            return (Criteria) this;
        }

        public Criteria andAtmLimitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ATM_LIMIT >=", value, "atmLimit");
            return (Criteria) this;
        }

        public Criteria andAtmLimitLessThan(BigDecimal value) {
            addCriterion("ATM_LIMIT <", value, "atmLimit");
            return (Criteria) this;
        }

        public Criteria andAtmLimitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ATM_LIMIT <=", value, "atmLimit");
            return (Criteria) this;
        }

        public Criteria andAtmLimitIn(List<BigDecimal> values) {
            addCriterion("ATM_LIMIT in", values, "atmLimit");
            return (Criteria) this;
        }

        public Criteria andAtmLimitNotIn(List<BigDecimal> values) {
            addCriterion("ATM_LIMIT not in", values, "atmLimit");
            return (Criteria) this;
        }

        public Criteria andAtmLimitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ATM_LIMIT between", value1, value2, "atmLimit");
            return (Criteria) this;
        }

        public Criteria andAtmLimitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ATM_LIMIT not between", value1, value2, "atmLimit");
            return (Criteria) this;
        }

        public Criteria andOldOrgIdIsNull() {
            addCriterion("OLD_ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andOldOrgIdIsNotNull() {
            addCriterion("OLD_ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOldOrgIdEqualTo(String value) {
            addCriterion("OLD_ORG_ID =", value, "oldOrgId");
            return (Criteria) this;
        }

        public Criteria andOldOrgIdNotEqualTo(String value) {
            addCriterion("OLD_ORG_ID <>", value, "oldOrgId");
            return (Criteria) this;
        }

        public Criteria andOldOrgIdGreaterThan(String value) {
            addCriterion("OLD_ORG_ID >", value, "oldOrgId");
            return (Criteria) this;
        }

        public Criteria andOldOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("OLD_ORG_ID >=", value, "oldOrgId");
            return (Criteria) this;
        }

        public Criteria andOldOrgIdLessThan(String value) {
            addCriterion("OLD_ORG_ID <", value, "oldOrgId");
            return (Criteria) this;
        }

        public Criteria andOldOrgIdLessThanOrEqualTo(String value) {
            addCriterion("OLD_ORG_ID <=", value, "oldOrgId");
            return (Criteria) this;
        }

        public Criteria andOldOrgIdLike(String value) {
            addCriterion("OLD_ORG_ID like", value, "oldOrgId");
            return (Criteria) this;
        }

        public Criteria andOldOrgIdNotLike(String value) {
            addCriterion("OLD_ORG_ID not like", value, "oldOrgId");
            return (Criteria) this;
        }

        public Criteria andOldOrgIdIn(List<String> values) {
            addCriterion("OLD_ORG_ID in", values, "oldOrgId");
            return (Criteria) this;
        }

        public Criteria andOldOrgIdNotIn(List<String> values) {
            addCriterion("OLD_ORG_ID not in", values, "oldOrgId");
            return (Criteria) this;
        }

        public Criteria andOldOrgIdBetween(String value1, String value2) {
            addCriterion("OLD_ORG_ID between", value1, value2, "oldOrgId");
            return (Criteria) this;
        }

        public Criteria andOldOrgIdNotBetween(String value1, String value2) {
            addCriterion("OLD_ORG_ID not between", value1, value2, "oldOrgId");
            return (Criteria) this;
        }

        public Criteria andMerSeqIsNull() {
            addCriterion("MER_SEQ is null");
            return (Criteria) this;
        }

        public Criteria andMerSeqIsNotNull() {
            addCriterion("MER_SEQ is not null");
            return (Criteria) this;
        }

        public Criteria andMerSeqEqualTo(String value) {
            addCriterion("MER_SEQ =", value, "merSeq");
            return (Criteria) this;
        }

        public Criteria andMerSeqNotEqualTo(String value) {
            addCriterion("MER_SEQ <>", value, "merSeq");
            return (Criteria) this;
        }

        public Criteria andMerSeqGreaterThan(String value) {
            addCriterion("MER_SEQ >", value, "merSeq");
            return (Criteria) this;
        }

        public Criteria andMerSeqGreaterThanOrEqualTo(String value) {
            addCriterion("MER_SEQ >=", value, "merSeq");
            return (Criteria) this;
        }

        public Criteria andMerSeqLessThan(String value) {
            addCriterion("MER_SEQ <", value, "merSeq");
            return (Criteria) this;
        }

        public Criteria andMerSeqLessThanOrEqualTo(String value) {
            addCriterion("MER_SEQ <=", value, "merSeq");
            return (Criteria) this;
        }

        public Criteria andMerSeqLike(String value) {
            addCriterion("MER_SEQ like", value, "merSeq");
            return (Criteria) this;
        }

        public Criteria andMerSeqNotLike(String value) {
            addCriterion("MER_SEQ not like", value, "merSeq");
            return (Criteria) this;
        }

        public Criteria andMerSeqIn(List<String> values) {
            addCriterion("MER_SEQ in", values, "merSeq");
            return (Criteria) this;
        }

        public Criteria andMerSeqNotIn(List<String> values) {
            addCriterion("MER_SEQ not in", values, "merSeq");
            return (Criteria) this;
        }

        public Criteria andMerSeqBetween(String value1, String value2) {
            addCriterion("MER_SEQ between", value1, value2, "merSeq");
            return (Criteria) this;
        }

        public Criteria andMerSeqNotBetween(String value1, String value2) {
            addCriterion("MER_SEQ not between", value1, value2, "merSeq");
            return (Criteria) this;
        }

        public Criteria andMerPrefixIsNull() {
            addCriterion("MER_PREFIX is null");
            return (Criteria) this;
        }

        public Criteria andMerPrefixIsNotNull() {
            addCriterion("MER_PREFIX is not null");
            return (Criteria) this;
        }

        public Criteria andMerPrefixEqualTo(String value) {
            addCriterion("MER_PREFIX =", value, "merPrefix");
            return (Criteria) this;
        }

        public Criteria andMerPrefixNotEqualTo(String value) {
            addCriterion("MER_PREFIX <>", value, "merPrefix");
            return (Criteria) this;
        }

        public Criteria andMerPrefixGreaterThan(String value) {
            addCriterion("MER_PREFIX >", value, "merPrefix");
            return (Criteria) this;
        }

        public Criteria andMerPrefixGreaterThanOrEqualTo(String value) {
            addCriterion("MER_PREFIX >=", value, "merPrefix");
            return (Criteria) this;
        }

        public Criteria andMerPrefixLessThan(String value) {
            addCriterion("MER_PREFIX <", value, "merPrefix");
            return (Criteria) this;
        }

        public Criteria andMerPrefixLessThanOrEqualTo(String value) {
            addCriterion("MER_PREFIX <=", value, "merPrefix");
            return (Criteria) this;
        }

        public Criteria andMerPrefixLike(String value) {
            addCriterion("MER_PREFIX like", value, "merPrefix");
            return (Criteria) this;
        }

        public Criteria andMerPrefixNotLike(String value) {
            addCriterion("MER_PREFIX not like", value, "merPrefix");
            return (Criteria) this;
        }

        public Criteria andMerPrefixIn(List<String> values) {
            addCriterion("MER_PREFIX in", values, "merPrefix");
            return (Criteria) this;
        }

        public Criteria andMerPrefixNotIn(List<String> values) {
            addCriterion("MER_PREFIX not in", values, "merPrefix");
            return (Criteria) this;
        }

        public Criteria andMerPrefixBetween(String value1, String value2) {
            addCriterion("MER_PREFIX between", value1, value2, "merPrefix");
            return (Criteria) this;
        }

        public Criteria andMerPrefixNotBetween(String value1, String value2) {
            addCriterion("MER_PREFIX not between", value1, value2, "merPrefix");
            return (Criteria) this;
        }

        public Criteria andOrgTypeIsNull() {
            addCriterion("ORG_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andOrgTypeIsNotNull() {
            addCriterion("ORG_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andOrgTypeEqualTo(String value) {
            addCriterion("ORG_TYPE =", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeNotEqualTo(String value) {
            addCriterion("ORG_TYPE <>", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeGreaterThan(String value) {
            addCriterion("ORG_TYPE >", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_TYPE >=", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeLessThan(String value) {
            addCriterion("ORG_TYPE <", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeLessThanOrEqualTo(String value) {
            addCriterion("ORG_TYPE <=", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeLike(String value) {
            addCriterion("ORG_TYPE like", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeNotLike(String value) {
            addCriterion("ORG_TYPE not like", value, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeIn(List<String> values) {
            addCriterion("ORG_TYPE in", values, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeNotIn(List<String> values) {
            addCriterion("ORG_TYPE not in", values, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeBetween(String value1, String value2) {
            addCriterion("ORG_TYPE between", value1, value2, "orgType");
            return (Criteria) this;
        }

        public Criteria andOrgTypeNotBetween(String value1, String value2) {
            addCriterion("ORG_TYPE not between", value1, value2, "orgType");
            return (Criteria) this;
        }

        public Criteria andSupDorgIdIsNull() {
            addCriterion("SUP_DORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andSupDorgIdIsNotNull() {
            addCriterion("SUP_DORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSupDorgIdEqualTo(String value) {
            addCriterion("SUP_DORG_ID =", value, "supDorgId");
            return (Criteria) this;
        }

        public Criteria andSupDorgIdNotEqualTo(String value) {
            addCriterion("SUP_DORG_ID <>", value, "supDorgId");
            return (Criteria) this;
        }

        public Criteria andSupDorgIdGreaterThan(String value) {
            addCriterion("SUP_DORG_ID >", value, "supDorgId");
            return (Criteria) this;
        }

        public Criteria andSupDorgIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUP_DORG_ID >=", value, "supDorgId");
            return (Criteria) this;
        }

        public Criteria andSupDorgIdLessThan(String value) {
            addCriterion("SUP_DORG_ID <", value, "supDorgId");
            return (Criteria) this;
        }

        public Criteria andSupDorgIdLessThanOrEqualTo(String value) {
            addCriterion("SUP_DORG_ID <=", value, "supDorgId");
            return (Criteria) this;
        }

        public Criteria andSupDorgIdLike(String value) {
            addCriterion("SUP_DORG_ID like", value, "supDorgId");
            return (Criteria) this;
        }

        public Criteria andSupDorgIdNotLike(String value) {
            addCriterion("SUP_DORG_ID not like", value, "supDorgId");
            return (Criteria) this;
        }

        public Criteria andSupDorgIdIn(List<String> values) {
            addCriterion("SUP_DORG_ID in", values, "supDorgId");
            return (Criteria) this;
        }

        public Criteria andSupDorgIdNotIn(List<String> values) {
            addCriterion("SUP_DORG_ID not in", values, "supDorgId");
            return (Criteria) this;
        }

        public Criteria andSupDorgIdBetween(String value1, String value2) {
            addCriterion("SUP_DORG_ID between", value1, value2, "supDorgId");
            return (Criteria) this;
        }

        public Criteria andSupDorgIdNotBetween(String value1, String value2) {
            addCriterion("SUP_DORG_ID not between", value1, value2, "supDorgId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdIsNull() {
            addCriterion("UNIQUE_ID is null");
            return (Criteria) this;
        }

        public Criteria andUniqueIdIsNotNull() {
            addCriterion("UNIQUE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUniqueIdEqualTo(String value) {
            addCriterion("UNIQUE_ID =", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdNotEqualTo(String value) {
            addCriterion("UNIQUE_ID <>", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdGreaterThan(String value) {
            addCriterion("UNIQUE_ID >", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdGreaterThanOrEqualTo(String value) {
            addCriterion("UNIQUE_ID >=", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdLessThan(String value) {
            addCriterion("UNIQUE_ID <", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdLessThanOrEqualTo(String value) {
            addCriterion("UNIQUE_ID <=", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdLike(String value) {
            addCriterion("UNIQUE_ID like", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdNotLike(String value) {
            addCriterion("UNIQUE_ID not like", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdIn(List<String> values) {
            addCriterion("UNIQUE_ID in", values, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdNotIn(List<String> values) {
            addCriterion("UNIQUE_ID not in", values, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdBetween(String value1, String value2) {
            addCriterion("UNIQUE_ID between", value1, value2, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdNotBetween(String value1, String value2) {
            addCriterion("UNIQUE_ID not between", value1, value2, "uniqueId");
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