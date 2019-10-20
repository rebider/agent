package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnnounceMentInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AnnounceMentInfoExample() {
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

        public Criteria andAnnIdIsNull() {
            addCriterion("ANN_ID is null");
            return (Criteria) this;
        }

        public Criteria andAnnIdIsNotNull() {
            addCriterion("ANN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAnnIdEqualTo(String value) {
            addCriterion("ANN_ID =", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdNotEqualTo(String value) {
            addCriterion("ANN_ID <>", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdGreaterThan(String value) {
            addCriterion("ANN_ID >", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdGreaterThanOrEqualTo(String value) {
            addCriterion("ANN_ID >=", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdLessThan(String value) {
            addCriterion("ANN_ID <", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdLessThanOrEqualTo(String value) {
            addCriterion("ANN_ID <=", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdLike(String value) {
            addCriterion("ANN_ID like", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdNotLike(String value) {
            addCriterion("ANN_ID not like", value, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdIn(List<String> values) {
            addCriterion("ANN_ID in", values, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdNotIn(List<String> values) {
            addCriterion("ANN_ID not in", values, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdBetween(String value1, String value2) {
            addCriterion("ANN_ID between", value1, value2, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnIdNotBetween(String value1, String value2) {
            addCriterion("ANN_ID not between", value1, value2, "annId");
            return (Criteria) this;
        }

        public Criteria andAnnTitleIsNull() {
            addCriterion("ANN_TITLE is null");
            return (Criteria) this;
        }

        public Criteria andAnnTitleIsNotNull() {
            addCriterion("ANN_TITLE is not null");
            return (Criteria) this;
        }

        public Criteria andAnnTitleEqualTo(String value) {
            addCriterion("ANN_TITLE =", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleNotEqualTo(String value) {
            addCriterion("ANN_TITLE <>", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleGreaterThan(String value) {
            addCriterion("ANN_TITLE >", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleGreaterThanOrEqualTo(String value) {
            addCriterion("ANN_TITLE >=", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleLessThan(String value) {
            addCriterion("ANN_TITLE <", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleLessThanOrEqualTo(String value) {
            addCriterion("ANN_TITLE <=", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleLike(String value) {
            addCriterion("ANN_TITLE like", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleNotLike(String value) {
            addCriterion("ANN_TITLE not like", value, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleIn(List<String> values) {
            addCriterion("ANN_TITLE in", values, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleNotIn(List<String> values) {
            addCriterion("ANN_TITLE not in", values, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleBetween(String value1, String value2) {
            addCriterion("ANN_TITLE between", value1, value2, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTitleNotBetween(String value1, String value2) {
            addCriterion("ANN_TITLE not between", value1, value2, "annTitle");
            return (Criteria) this;
        }

        public Criteria andAnnTypeIsNull() {
            addCriterion("ANN_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAnnTypeIsNotNull() {
            addCriterion("ANN_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAnnTypeEqualTo(BigDecimal value) {
            addCriterion("ANN_TYPE =", value, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeNotEqualTo(BigDecimal value) {
            addCriterion("ANN_TYPE <>", value, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeGreaterThan(BigDecimal value) {
            addCriterion("ANN_TYPE >", value, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ANN_TYPE >=", value, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeLessThan(BigDecimal value) {
            addCriterion("ANN_TYPE <", value, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ANN_TYPE <=", value, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeIn(List<BigDecimal> values) {
            addCriterion("ANN_TYPE in", values, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeNotIn(List<BigDecimal> values) {
            addCriterion("ANN_TYPE not in", values, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ANN_TYPE between", value1, value2, "annType");
            return (Criteria) this;
        }

        public Criteria andAnnTypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ANN_TYPE not between", value1, value2, "annType");
            return (Criteria) this;
        }

        public Criteria andEffectTmIsNull() {
            addCriterion("EFFECT_TM is null");
            return (Criteria) this;
        }

        public Criteria andEffectTmIsNotNull() {
            addCriterion("EFFECT_TM is not null");
            return (Criteria) this;
        }

        public Criteria andEffectTmEqualTo(Date value) {
            addCriterion("EFFECT_TM =", value, "effectTm");
            return (Criteria) this;
        }

        public Criteria andEffectTmNotEqualTo(Date value) {
            addCriterion("EFFECT_TM <>", value, "effectTm");
            return (Criteria) this;
        }

        public Criteria andEffectTmGreaterThan(Date value) {
            addCriterion("EFFECT_TM >", value, "effectTm");
            return (Criteria) this;
        }

        public Criteria andEffectTmGreaterThanOrEqualTo(Date value) {
            addCriterion("EFFECT_TM >=", value, "effectTm");
            return (Criteria) this;
        }

        public Criteria andEffectTmLessThan(Date value) {
            addCriterion("EFFECT_TM <", value, "effectTm");
            return (Criteria) this;
        }

        public Criteria andEffectTmLessThanOrEqualTo(Date value) {
            addCriterion("EFFECT_TM <=", value, "effectTm");
            return (Criteria) this;
        }

        public Criteria andEffectTmIn(List<Date> values) {
            addCriterion("EFFECT_TM in", values, "effectTm");
            return (Criteria) this;
        }

        public Criteria andEffectTmNotIn(List<Date> values) {
            addCriterion("EFFECT_TM not in", values, "effectTm");
            return (Criteria) this;
        }

        public Criteria andEffectTmBetween(Date value1, Date value2) {
            addCriterion("EFFECT_TM between", value1, value2, "effectTm");
            return (Criteria) this;
        }

        public Criteria andEffectTmNotBetween(Date value1, Date value2) {
            addCriterion("EFFECT_TM not between", value1, value2, "effectTm");
            return (Criteria) this;
        }

        public Criteria andExpireTmIsNull() {
            addCriterion("EXPIRE_TM is null");
            return (Criteria) this;
        }

        public Criteria andExpireTmIsNotNull() {
            addCriterion("EXPIRE_TM is not null");
            return (Criteria) this;
        }

        public Criteria andExpireTmEqualTo(Date value) {
            addCriterion("EXPIRE_TM =", value, "expireTm");
            return (Criteria) this;
        }

        public Criteria andExpireTmNotEqualTo(Date value) {
            addCriterion("EXPIRE_TM <>", value, "expireTm");
            return (Criteria) this;
        }

        public Criteria andExpireTmGreaterThan(Date value) {
            addCriterion("EXPIRE_TM >", value, "expireTm");
            return (Criteria) this;
        }

        public Criteria andExpireTmGreaterThanOrEqualTo(Date value) {
            addCriterion("EXPIRE_TM >=", value, "expireTm");
            return (Criteria) this;
        }

        public Criteria andExpireTmLessThan(Date value) {
            addCriterion("EXPIRE_TM <", value, "expireTm");
            return (Criteria) this;
        }

        public Criteria andExpireTmLessThanOrEqualTo(Date value) {
            addCriterion("EXPIRE_TM <=", value, "expireTm");
            return (Criteria) this;
        }

        public Criteria andExpireTmIn(List<Date> values) {
            addCriterion("EXPIRE_TM in", values, "expireTm");
            return (Criteria) this;
        }

        public Criteria andExpireTmNotIn(List<Date> values) {
            addCriterion("EXPIRE_TM not in", values, "expireTm");
            return (Criteria) this;
        }

        public Criteria andExpireTmBetween(Date value1, Date value2) {
            addCriterion("EXPIRE_TM between", value1, value2, "expireTm");
            return (Criteria) this;
        }

        public Criteria andExpireTmNotBetween(Date value1, Date value2) {
            addCriterion("EXPIRE_TM not between", value1, value2, "expireTm");
            return (Criteria) this;
        }

        public Criteria andAnnoStatIsNull() {
            addCriterion("ANNO_STAT is null");
            return (Criteria) this;
        }

        public Criteria andAnnoStatIsNotNull() {
            addCriterion("ANNO_STAT is not null");
            return (Criteria) this;
        }

        public Criteria andAnnoStatEqualTo(BigDecimal value) {
            addCriterion("ANNO_STAT =", value, "annoStat");
            return (Criteria) this;
        }

        public Criteria andAnnoStatNotEqualTo(BigDecimal value) {
            addCriterion("ANNO_STAT <>", value, "annoStat");
            return (Criteria) this;
        }

        public Criteria andAnnoStatGreaterThan(BigDecimal value) {
            addCriterion("ANNO_STAT >", value, "annoStat");
            return (Criteria) this;
        }

        public Criteria andAnnoStatGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ANNO_STAT >=", value, "annoStat");
            return (Criteria) this;
        }

        public Criteria andAnnoStatLessThan(BigDecimal value) {
            addCriterion("ANNO_STAT <", value, "annoStat");
            return (Criteria) this;
        }

        public Criteria andAnnoStatLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ANNO_STAT <=", value, "annoStat");
            return (Criteria) this;
        }

        public Criteria andAnnoStatIn(List<BigDecimal> values) {
            addCriterion("ANNO_STAT in", values, "annoStat");
            return (Criteria) this;
        }

        public Criteria andAnnoStatNotIn(List<BigDecimal> values) {
            addCriterion("ANNO_STAT not in", values, "annoStat");
            return (Criteria) this;
        }

        public Criteria andAnnoStatBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ANNO_STAT between", value1, value2, "annoStat");
            return (Criteria) this;
        }

        public Criteria andAnnoStatNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ANNO_STAT not between", value1, value2, "annoStat");
            return (Criteria) this;
        }

        public Criteria andPubTmIsNull() {
            addCriterion("PUB_TM is null");
            return (Criteria) this;
        }

        public Criteria andPubTmIsNotNull() {
            addCriterion("PUB_TM is not null");
            return (Criteria) this;
        }

        public Criteria andPubTmEqualTo(Date value) {
            addCriterion("PUB_TM =", value, "pubTm");
            return (Criteria) this;
        }

        public Criteria andPubTmNotEqualTo(Date value) {
            addCriterion("PUB_TM <>", value, "pubTm");
            return (Criteria) this;
        }

        public Criteria andPubTmGreaterThan(Date value) {
            addCriterion("PUB_TM >", value, "pubTm");
            return (Criteria) this;
        }

        public Criteria andPubTmGreaterThanOrEqualTo(Date value) {
            addCriterion("PUB_TM >=", value, "pubTm");
            return (Criteria) this;
        }

        public Criteria andPubTmLessThan(Date value) {
            addCriterion("PUB_TM <", value, "pubTm");
            return (Criteria) this;
        }

        public Criteria andPubTmLessThanOrEqualTo(Date value) {
            addCriterion("PUB_TM <=", value, "pubTm");
            return (Criteria) this;
        }

        public Criteria andPubTmIn(List<Date> values) {
            addCriterion("PUB_TM in", values, "pubTm");
            return (Criteria) this;
        }

        public Criteria andPubTmNotIn(List<Date> values) {
            addCriterion("PUB_TM not in", values, "pubTm");
            return (Criteria) this;
        }

        public Criteria andPubTmBetween(Date value1, Date value2) {
            addCriterion("PUB_TM between", value1, value2, "pubTm");
            return (Criteria) this;
        }

        public Criteria andPubTmNotBetween(Date value1, Date value2) {
            addCriterion("PUB_TM not between", value1, value2, "pubTm");
            return (Criteria) this;
        }

        public Criteria andPubOrgIsNull() {
            addCriterion("PUB_ORG is null");
            return (Criteria) this;
        }

        public Criteria andPubOrgIsNotNull() {
            addCriterion("PUB_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andPubOrgEqualTo(String value) {
            addCriterion("PUB_ORG =", value, "pubOrg");
            return (Criteria) this;
        }

        public Criteria andPubOrgNotEqualTo(String value) {
            addCriterion("PUB_ORG <>", value, "pubOrg");
            return (Criteria) this;
        }

        public Criteria andPubOrgGreaterThan(String value) {
            addCriterion("PUB_ORG >", value, "pubOrg");
            return (Criteria) this;
        }

        public Criteria andPubOrgGreaterThanOrEqualTo(String value) {
            addCriterion("PUB_ORG >=", value, "pubOrg");
            return (Criteria) this;
        }

        public Criteria andPubOrgLessThan(String value) {
            addCriterion("PUB_ORG <", value, "pubOrg");
            return (Criteria) this;
        }

        public Criteria andPubOrgLessThanOrEqualTo(String value) {
            addCriterion("PUB_ORG <=", value, "pubOrg");
            return (Criteria) this;
        }

        public Criteria andPubOrgLike(String value) {
            addCriterion("PUB_ORG like", value, "pubOrg");
            return (Criteria) this;
        }

        public Criteria andPubOrgNotLike(String value) {
            addCriterion("PUB_ORG not like", value, "pubOrg");
            return (Criteria) this;
        }

        public Criteria andPubOrgIn(List<String> values) {
            addCriterion("PUB_ORG in", values, "pubOrg");
            return (Criteria) this;
        }

        public Criteria andPubOrgNotIn(List<String> values) {
            addCriterion("PUB_ORG not in", values, "pubOrg");
            return (Criteria) this;
        }

        public Criteria andPubOrgBetween(String value1, String value2) {
            addCriterion("PUB_ORG between", value1, value2, "pubOrg");
            return (Criteria) this;
        }

        public Criteria andPubOrgNotBetween(String value1, String value2) {
            addCriterion("PUB_ORG not between", value1, value2, "pubOrg");
            return (Criteria) this;
        }

        public Criteria andPublisherIsNull() {
            addCriterion("PUBLISHER is null");
            return (Criteria) this;
        }

        public Criteria andPublisherIsNotNull() {
            addCriterion("PUBLISHER is not null");
            return (Criteria) this;
        }

        public Criteria andPublisherEqualTo(String value) {
            addCriterion("PUBLISHER =", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotEqualTo(String value) {
            addCriterion("PUBLISHER <>", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThan(String value) {
            addCriterion("PUBLISHER >", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThanOrEqualTo(String value) {
            addCriterion("PUBLISHER >=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThan(String value) {
            addCriterion("PUBLISHER <", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThanOrEqualTo(String value) {
            addCriterion("PUBLISHER <=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLike(String value) {
            addCriterion("PUBLISHER like", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotLike(String value) {
            addCriterion("PUBLISHER not like", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherIn(List<String> values) {
            addCriterion("PUBLISHER in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotIn(List<String> values) {
            addCriterion("PUBLISHER not in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherBetween(String value1, String value2) {
            addCriterion("PUBLISHER between", value1, value2, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotBetween(String value1, String value2) {
            addCriterion("PUBLISHER not between", value1, value2, "publisher");
            return (Criteria) this;
        }

        public Criteria andUpStatUserIsNull() {
            addCriterion("UP_STAT_USER is null");
            return (Criteria) this;
        }

        public Criteria andUpStatUserIsNotNull() {
            addCriterion("UP_STAT_USER is not null");
            return (Criteria) this;
        }

        public Criteria andUpStatUserEqualTo(String value) {
            addCriterion("UP_STAT_USER =", value, "upStatUser");
            return (Criteria) this;
        }

        public Criteria andUpStatUserNotEqualTo(String value) {
            addCriterion("UP_STAT_USER <>", value, "upStatUser");
            return (Criteria) this;
        }

        public Criteria andUpStatUserGreaterThan(String value) {
            addCriterion("UP_STAT_USER >", value, "upStatUser");
            return (Criteria) this;
        }

        public Criteria andUpStatUserGreaterThanOrEqualTo(String value) {
            addCriterion("UP_STAT_USER >=", value, "upStatUser");
            return (Criteria) this;
        }

        public Criteria andUpStatUserLessThan(String value) {
            addCriterion("UP_STAT_USER <", value, "upStatUser");
            return (Criteria) this;
        }

        public Criteria andUpStatUserLessThanOrEqualTo(String value) {
            addCriterion("UP_STAT_USER <=", value, "upStatUser");
            return (Criteria) this;
        }

        public Criteria andUpStatUserLike(String value) {
            addCriterion("UP_STAT_USER like", value, "upStatUser");
            return (Criteria) this;
        }

        public Criteria andUpStatUserNotLike(String value) {
            addCriterion("UP_STAT_USER not like", value, "upStatUser");
            return (Criteria) this;
        }

        public Criteria andUpStatUserIn(List<String> values) {
            addCriterion("UP_STAT_USER in", values, "upStatUser");
            return (Criteria) this;
        }

        public Criteria andUpStatUserNotIn(List<String> values) {
            addCriterion("UP_STAT_USER not in", values, "upStatUser");
            return (Criteria) this;
        }

        public Criteria andUpStatUserBetween(String value1, String value2) {
            addCriterion("UP_STAT_USER between", value1, value2, "upStatUser");
            return (Criteria) this;
        }

        public Criteria andUpStatUserNotBetween(String value1, String value2) {
            addCriterion("UP_STAT_USER not between", value1, value2, "upStatUser");
            return (Criteria) this;
        }

        public Criteria andUpStatTmIsNull() {
            addCriterion("UP_STAT_TM is null");
            return (Criteria) this;
        }

        public Criteria andUpStatTmIsNotNull() {
            addCriterion("UP_STAT_TM is not null");
            return (Criteria) this;
        }

        public Criteria andUpStatTmEqualTo(Date value) {
            addCriterion("UP_STAT_TM =", value, "upStatTm");
            return (Criteria) this;
        }

        public Criteria andUpStatTmNotEqualTo(Date value) {
            addCriterion("UP_STAT_TM <>", value, "upStatTm");
            return (Criteria) this;
        }

        public Criteria andUpStatTmGreaterThan(Date value) {
            addCriterion("UP_STAT_TM >", value, "upStatTm");
            return (Criteria) this;
        }

        public Criteria andUpStatTmGreaterThanOrEqualTo(Date value) {
            addCriterion("UP_STAT_TM >=", value, "upStatTm");
            return (Criteria) this;
        }

        public Criteria andUpStatTmLessThan(Date value) {
            addCriterion("UP_STAT_TM <", value, "upStatTm");
            return (Criteria) this;
        }

        public Criteria andUpStatTmLessThanOrEqualTo(Date value) {
            addCriterion("UP_STAT_TM <=", value, "upStatTm");
            return (Criteria) this;
        }

        public Criteria andUpStatTmIn(List<Date> values) {
            addCriterion("UP_STAT_TM in", values, "upStatTm");
            return (Criteria) this;
        }

        public Criteria andUpStatTmNotIn(List<Date> values) {
            addCriterion("UP_STAT_TM not in", values, "upStatTm");
            return (Criteria) this;
        }

        public Criteria andUpStatTmBetween(Date value1, Date value2) {
            addCriterion("UP_STAT_TM between", value1, value2, "upStatTm");
            return (Criteria) this;
        }

        public Criteria andUpStatTmNotBetween(Date value1, Date value2) {
            addCriterion("UP_STAT_TM not between", value1, value2, "upStatTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmIsNull() {
            addCriterion("CREATE_TM is null");
            return (Criteria) this;
        }

        public Criteria andCreateTmIsNotNull() {
            addCriterion("CREATE_TM is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTmEqualTo(Date value) {
            addCriterion("CREATE_TM =", value, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmNotEqualTo(Date value) {
            addCriterion("CREATE_TM <>", value, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmGreaterThan(Date value) {
            addCriterion("CREATE_TM >", value, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TM >=", value, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmLessThan(Date value) {
            addCriterion("CREATE_TM <", value, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TM <=", value, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmIn(List<Date> values) {
            addCriterion("CREATE_TM in", values, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmNotIn(List<Date> values) {
            addCriterion("CREATE_TM not in", values, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmBetween(Date value1, Date value2) {
            addCriterion("CREATE_TM between", value1, value2, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateTmNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TM not between", value1, value2, "createTm");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("CREATE_USER is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("CREATE_USER is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("CREATE_USER =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("CREATE_USER <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("CREATE_USER >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_USER >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("CREATE_USER <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("CREATE_USER <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("CREATE_USER like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("CREATE_USER not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("CREATE_USER in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("CREATE_USER not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("CREATE_USER between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("CREATE_USER not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andToAgentIsNull() {
            addCriterion("TO_AGENT is null");
            return (Criteria) this;
        }

        public Criteria andToAgentIsNotNull() {
            addCriterion("TO_AGENT is not null");
            return (Criteria) this;
        }

        public Criteria andToAgentEqualTo(BigDecimal value) {
            addCriterion("TO_AGENT =", value, "toAgent");
            return (Criteria) this;
        }

        public Criteria andToAgentNotEqualTo(BigDecimal value) {
            addCriterion("TO_AGENT <>", value, "toAgent");
            return (Criteria) this;
        }

        public Criteria andToAgentGreaterThan(BigDecimal value) {
            addCriterion("TO_AGENT >", value, "toAgent");
            return (Criteria) this;
        }

        public Criteria andToAgentGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TO_AGENT >=", value, "toAgent");
            return (Criteria) this;
        }

        public Criteria andToAgentLessThan(BigDecimal value) {
            addCriterion("TO_AGENT <", value, "toAgent");
            return (Criteria) this;
        }

        public Criteria andToAgentLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TO_AGENT <=", value, "toAgent");
            return (Criteria) this;
        }

        public Criteria andToAgentIn(List<BigDecimal> values) {
            addCriterion("TO_AGENT in", values, "toAgent");
            return (Criteria) this;
        }

        public Criteria andToAgentNotIn(List<BigDecimal> values) {
            addCriterion("TO_AGENT not in", values, "toAgent");
            return (Criteria) this;
        }

        public Criteria andToAgentBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TO_AGENT between", value1, value2, "toAgent");
            return (Criteria) this;
        }

        public Criteria andToAgentNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TO_AGENT not between", value1, value2, "toAgent");
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