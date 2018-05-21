package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AgentExample() {
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

        public Criteria andIdEqualTo(BigDecimal value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(BigDecimal value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(BigDecimal value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(BigDecimal value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<BigDecimal> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<BigDecimal> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumIsNull() {
            addCriterion("AG_UNIQ_NUM is null");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumIsNotNull() {
            addCriterion("AG_UNIQ_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumEqualTo(String value) {
            addCriterion("AG_UNIQ_NUM =", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumNotEqualTo(String value) {
            addCriterion("AG_UNIQ_NUM <>", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumGreaterThan(String value) {
            addCriterion("AG_UNIQ_NUM >", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumGreaterThanOrEqualTo(String value) {
            addCriterion("AG_UNIQ_NUM >=", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumLessThan(String value) {
            addCriterion("AG_UNIQ_NUM <", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumLessThanOrEqualTo(String value) {
            addCriterion("AG_UNIQ_NUM <=", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumLike(String value) {
            addCriterion("AG_UNIQ_NUM like", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumNotLike(String value) {
            addCriterion("AG_UNIQ_NUM not like", value, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumIn(List<String> values) {
            addCriterion("AG_UNIQ_NUM in", values, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumNotIn(List<String> values) {
            addCriterion("AG_UNIQ_NUM not in", values, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumBetween(String value1, String value2) {
            addCriterion("AG_UNIQ_NUM between", value1, value2, "agUniqNum");
            return (Criteria) this;
        }

        public Criteria andAgUniqNumNotBetween(String value1, String value2) {
            addCriterion("AG_UNIQ_NUM not between", value1, value2, "agUniqNum");
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

        public Criteria andAgNatureIsNull() {
            addCriterion("AG_NATURE is null");
            return (Criteria) this;
        }

        public Criteria andAgNatureIsNotNull() {
            addCriterion("AG_NATURE is not null");
            return (Criteria) this;
        }

        public Criteria andAgNatureEqualTo(BigDecimal value) {
            addCriterion("AG_NATURE =", value, "agNature");
            return (Criteria) this;
        }

        public Criteria andAgNatureNotEqualTo(BigDecimal value) {
            addCriterion("AG_NATURE <>", value, "agNature");
            return (Criteria) this;
        }

        public Criteria andAgNatureGreaterThan(BigDecimal value) {
            addCriterion("AG_NATURE >", value, "agNature");
            return (Criteria) this;
        }

        public Criteria andAgNatureGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AG_NATURE >=", value, "agNature");
            return (Criteria) this;
        }

        public Criteria andAgNatureLessThan(BigDecimal value) {
            addCriterion("AG_NATURE <", value, "agNature");
            return (Criteria) this;
        }

        public Criteria andAgNatureLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AG_NATURE <=", value, "agNature");
            return (Criteria) this;
        }

        public Criteria andAgNatureIn(List<BigDecimal> values) {
            addCriterion("AG_NATURE in", values, "agNature");
            return (Criteria) this;
        }

        public Criteria andAgNatureNotIn(List<BigDecimal> values) {
            addCriterion("AG_NATURE not in", values, "agNature");
            return (Criteria) this;
        }

        public Criteria andAgNatureBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AG_NATURE between", value1, value2, "agNature");
            return (Criteria) this;
        }

        public Criteria andAgNatureNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AG_NATURE not between", value1, value2, "agNature");
            return (Criteria) this;
        }

        public Criteria andAgCapitalIsNull() {
            addCriterion("AG_CAPITAL is null");
            return (Criteria) this;
        }

        public Criteria andAgCapitalIsNotNull() {
            addCriterion("AG_CAPITAL is not null");
            return (Criteria) this;
        }

        public Criteria andAgCapitalEqualTo(BigDecimal value) {
            addCriterion("AG_CAPITAL =", value, "agCapital");
            return (Criteria) this;
        }

        public Criteria andAgCapitalNotEqualTo(BigDecimal value) {
            addCriterion("AG_CAPITAL <>", value, "agCapital");
            return (Criteria) this;
        }

        public Criteria andAgCapitalGreaterThan(BigDecimal value) {
            addCriterion("AG_CAPITAL >", value, "agCapital");
            return (Criteria) this;
        }

        public Criteria andAgCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AG_CAPITAL >=", value, "agCapital");
            return (Criteria) this;
        }

        public Criteria andAgCapitalLessThan(BigDecimal value) {
            addCriterion("AG_CAPITAL <", value, "agCapital");
            return (Criteria) this;
        }

        public Criteria andAgCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AG_CAPITAL <=", value, "agCapital");
            return (Criteria) this;
        }

        public Criteria andAgCapitalIn(List<BigDecimal> values) {
            addCriterion("AG_CAPITAL in", values, "agCapital");
            return (Criteria) this;
        }

        public Criteria andAgCapitalNotIn(List<BigDecimal> values) {
            addCriterion("AG_CAPITAL not in", values, "agCapital");
            return (Criteria) this;
        }

        public Criteria andAgCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AG_CAPITAL between", value1, value2, "agCapital");
            return (Criteria) this;
        }

        public Criteria andAgCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AG_CAPITAL not between", value1, value2, "agCapital");
            return (Criteria) this;
        }

        public Criteria andAgBusLicIsNull() {
            addCriterion("AG_BUS_LIC is null");
            return (Criteria) this;
        }

        public Criteria andAgBusLicIsNotNull() {
            addCriterion("AG_BUS_LIC is not null");
            return (Criteria) this;
        }

        public Criteria andAgBusLicEqualTo(String value) {
            addCriterion("AG_BUS_LIC =", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicNotEqualTo(String value) {
            addCriterion("AG_BUS_LIC <>", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicGreaterThan(String value) {
            addCriterion("AG_BUS_LIC >", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicGreaterThanOrEqualTo(String value) {
            addCriterion("AG_BUS_LIC >=", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicLessThan(String value) {
            addCriterion("AG_BUS_LIC <", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicLessThanOrEqualTo(String value) {
            addCriterion("AG_BUS_LIC <=", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicLike(String value) {
            addCriterion("AG_BUS_LIC like", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicNotLike(String value) {
            addCriterion("AG_BUS_LIC not like", value, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicIn(List<String> values) {
            addCriterion("AG_BUS_LIC in", values, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicNotIn(List<String> values) {
            addCriterion("AG_BUS_LIC not in", values, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicBetween(String value1, String value2) {
            addCriterion("AG_BUS_LIC between", value1, value2, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicNotBetween(String value1, String value2) {
            addCriterion("AG_BUS_LIC not between", value1, value2, "agBusLic");
            return (Criteria) this;
        }

        public Criteria andAgBusLicbIsNull() {
            addCriterion("AG_BUS_LICB is null");
            return (Criteria) this;
        }

        public Criteria andAgBusLicbIsNotNull() {
            addCriterion("AG_BUS_LICB is not null");
            return (Criteria) this;
        }

        public Criteria andAgBusLicbEqualTo(Date value) {
            addCriterion("AG_BUS_LICB =", value, "agBusLicb");
            return (Criteria) this;
        }

        public Criteria andAgBusLicbNotEqualTo(Date value) {
            addCriterion("AG_BUS_LICB <>", value, "agBusLicb");
            return (Criteria) this;
        }

        public Criteria andAgBusLicbGreaterThan(Date value) {
            addCriterion("AG_BUS_LICB >", value, "agBusLicb");
            return (Criteria) this;
        }

        public Criteria andAgBusLicbGreaterThanOrEqualTo(Date value) {
            addCriterion("AG_BUS_LICB >=", value, "agBusLicb");
            return (Criteria) this;
        }

        public Criteria andAgBusLicbLessThan(Date value) {
            addCriterion("AG_BUS_LICB <", value, "agBusLicb");
            return (Criteria) this;
        }

        public Criteria andAgBusLicbLessThanOrEqualTo(Date value) {
            addCriterion("AG_BUS_LICB <=", value, "agBusLicb");
            return (Criteria) this;
        }

        public Criteria andAgBusLicbIn(List<Date> values) {
            addCriterion("AG_BUS_LICB in", values, "agBusLicb");
            return (Criteria) this;
        }

        public Criteria andAgBusLicbNotIn(List<Date> values) {
            addCriterion("AG_BUS_LICB not in", values, "agBusLicb");
            return (Criteria) this;
        }

        public Criteria andAgBusLicbBetween(Date value1, Date value2) {
            addCriterion("AG_BUS_LICB between", value1, value2, "agBusLicb");
            return (Criteria) this;
        }

        public Criteria andAgBusLicbNotBetween(Date value1, Date value2) {
            addCriterion("AG_BUS_LICB not between", value1, value2, "agBusLicb");
            return (Criteria) this;
        }

        public Criteria andAgBusLiceIsNull() {
            addCriterion("AG_BUS_LICE is null");
            return (Criteria) this;
        }

        public Criteria andAgBusLiceIsNotNull() {
            addCriterion("AG_BUS_LICE is not null");
            return (Criteria) this;
        }

        public Criteria andAgBusLiceEqualTo(Date value) {
            addCriterion("AG_BUS_LICE =", value, "agBusLice");
            return (Criteria) this;
        }

        public Criteria andAgBusLiceNotEqualTo(Date value) {
            addCriterion("AG_BUS_LICE <>", value, "agBusLice");
            return (Criteria) this;
        }

        public Criteria andAgBusLiceGreaterThan(Date value) {
            addCriterion("AG_BUS_LICE >", value, "agBusLice");
            return (Criteria) this;
        }

        public Criteria andAgBusLiceGreaterThanOrEqualTo(Date value) {
            addCriterion("AG_BUS_LICE >=", value, "agBusLice");
            return (Criteria) this;
        }

        public Criteria andAgBusLiceLessThan(Date value) {
            addCriterion("AG_BUS_LICE <", value, "agBusLice");
            return (Criteria) this;
        }

        public Criteria andAgBusLiceLessThanOrEqualTo(Date value) {
            addCriterion("AG_BUS_LICE <=", value, "agBusLice");
            return (Criteria) this;
        }

        public Criteria andAgBusLiceIn(List<Date> values) {
            addCriterion("AG_BUS_LICE in", values, "agBusLice");
            return (Criteria) this;
        }

        public Criteria andAgBusLiceNotIn(List<Date> values) {
            addCriterion("AG_BUS_LICE not in", values, "agBusLice");
            return (Criteria) this;
        }

        public Criteria andAgBusLiceBetween(Date value1, Date value2) {
            addCriterion("AG_BUS_LICE between", value1, value2, "agBusLice");
            return (Criteria) this;
        }

        public Criteria andAgBusLiceNotBetween(Date value1, Date value2) {
            addCriterion("AG_BUS_LICE not between", value1, value2, "agBusLice");
            return (Criteria) this;
        }

        public Criteria andAgLegalIsNull() {
            addCriterion("AG_LEGAL is null");
            return (Criteria) this;
        }

        public Criteria andAgLegalIsNotNull() {
            addCriterion("AG_LEGAL is not null");
            return (Criteria) this;
        }

        public Criteria andAgLegalEqualTo(String value) {
            addCriterion("AG_LEGAL =", value, "agLegal");
            return (Criteria) this;
        }

        public Criteria andAgLegalNotEqualTo(String value) {
            addCriterion("AG_LEGAL <>", value, "agLegal");
            return (Criteria) this;
        }

        public Criteria andAgLegalGreaterThan(String value) {
            addCriterion("AG_LEGAL >", value, "agLegal");
            return (Criteria) this;
        }

        public Criteria andAgLegalGreaterThanOrEqualTo(String value) {
            addCriterion("AG_LEGAL >=", value, "agLegal");
            return (Criteria) this;
        }

        public Criteria andAgLegalLessThan(String value) {
            addCriterion("AG_LEGAL <", value, "agLegal");
            return (Criteria) this;
        }

        public Criteria andAgLegalLessThanOrEqualTo(String value) {
            addCriterion("AG_LEGAL <=", value, "agLegal");
            return (Criteria) this;
        }

        public Criteria andAgLegalLike(String value) {
            addCriterion("AG_LEGAL like", value, "agLegal");
            return (Criteria) this;
        }

        public Criteria andAgLegalNotLike(String value) {
            addCriterion("AG_LEGAL not like", value, "agLegal");
            return (Criteria) this;
        }

        public Criteria andAgLegalIn(List<String> values) {
            addCriterion("AG_LEGAL in", values, "agLegal");
            return (Criteria) this;
        }

        public Criteria andAgLegalNotIn(List<String> values) {
            addCriterion("AG_LEGAL not in", values, "agLegal");
            return (Criteria) this;
        }

        public Criteria andAgLegalBetween(String value1, String value2) {
            addCriterion("AG_LEGAL between", value1, value2, "agLegal");
            return (Criteria) this;
        }

        public Criteria andAgLegalNotBetween(String value1, String value2) {
            addCriterion("AG_LEGAL not between", value1, value2, "agLegal");
            return (Criteria) this;
        }

        public Criteria andAgLegalCertypeIsNull() {
            addCriterion("AG_LEGAL_CERTYPE is null");
            return (Criteria) this;
        }

        public Criteria andAgLegalCertypeIsNotNull() {
            addCriterion("AG_LEGAL_CERTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAgLegalCertypeEqualTo(BigDecimal value) {
            addCriterion("AG_LEGAL_CERTYPE =", value, "agLegalCertype");
            return (Criteria) this;
        }

        public Criteria andAgLegalCertypeNotEqualTo(BigDecimal value) {
            addCriterion("AG_LEGAL_CERTYPE <>", value, "agLegalCertype");
            return (Criteria) this;
        }

        public Criteria andAgLegalCertypeGreaterThan(BigDecimal value) {
            addCriterion("AG_LEGAL_CERTYPE >", value, "agLegalCertype");
            return (Criteria) this;
        }

        public Criteria andAgLegalCertypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AG_LEGAL_CERTYPE >=", value, "agLegalCertype");
            return (Criteria) this;
        }

        public Criteria andAgLegalCertypeLessThan(BigDecimal value) {
            addCriterion("AG_LEGAL_CERTYPE <", value, "agLegalCertype");
            return (Criteria) this;
        }

        public Criteria andAgLegalCertypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AG_LEGAL_CERTYPE <=", value, "agLegalCertype");
            return (Criteria) this;
        }

        public Criteria andAgLegalCertypeIn(List<BigDecimal> values) {
            addCriterion("AG_LEGAL_CERTYPE in", values, "agLegalCertype");
            return (Criteria) this;
        }

        public Criteria andAgLegalCertypeNotIn(List<BigDecimal> values) {
            addCriterion("AG_LEGAL_CERTYPE not in", values, "agLegalCertype");
            return (Criteria) this;
        }

        public Criteria andAgLegalCertypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AG_LEGAL_CERTYPE between", value1, value2, "agLegalCertype");
            return (Criteria) this;
        }

        public Criteria andAgLegalCertypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AG_LEGAL_CERTYPE not between", value1, value2, "agLegalCertype");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumIsNull() {
            addCriterion("AG_LEGAL_CERNUM is null");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumIsNotNull() {
            addCriterion("AG_LEGAL_CERNUM is not null");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumEqualTo(String value) {
            addCriterion("AG_LEGAL_CERNUM =", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumNotEqualTo(String value) {
            addCriterion("AG_LEGAL_CERNUM <>", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumGreaterThan(String value) {
            addCriterion("AG_LEGAL_CERNUM >", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumGreaterThanOrEqualTo(String value) {
            addCriterion("AG_LEGAL_CERNUM >=", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumLessThan(String value) {
            addCriterion("AG_LEGAL_CERNUM <", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumLessThanOrEqualTo(String value) {
            addCriterion("AG_LEGAL_CERNUM <=", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumLike(String value) {
            addCriterion("AG_LEGAL_CERNUM like", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumNotLike(String value) {
            addCriterion("AG_LEGAL_CERNUM not like", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumIn(List<String> values) {
            addCriterion("AG_LEGAL_CERNUM in", values, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumNotIn(List<String> values) {
            addCriterion("AG_LEGAL_CERNUM not in", values, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumBetween(String value1, String value2) {
            addCriterion("AG_LEGAL_CERNUM between", value1, value2, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumNotBetween(String value1, String value2) {
            addCriterion("AG_LEGAL_CERNUM not between", value1, value2, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalMobileIsNull() {
            addCriterion("AG_LEGAL_MOBILE is null");
            return (Criteria) this;
        }

        public Criteria andAgLegalMobileIsNotNull() {
            addCriterion("AG_LEGAL_MOBILE is not null");
            return (Criteria) this;
        }

        public Criteria andAgLegalMobileEqualTo(String value) {
            addCriterion("AG_LEGAL_MOBILE =", value, "agLegalMobile");
            return (Criteria) this;
        }

        public Criteria andAgLegalMobileNotEqualTo(String value) {
            addCriterion("AG_LEGAL_MOBILE <>", value, "agLegalMobile");
            return (Criteria) this;
        }

        public Criteria andAgLegalMobileGreaterThan(String value) {
            addCriterion("AG_LEGAL_MOBILE >", value, "agLegalMobile");
            return (Criteria) this;
        }

        public Criteria andAgLegalMobileGreaterThanOrEqualTo(String value) {
            addCriterion("AG_LEGAL_MOBILE >=", value, "agLegalMobile");
            return (Criteria) this;
        }

        public Criteria andAgLegalMobileLessThan(String value) {
            addCriterion("AG_LEGAL_MOBILE <", value, "agLegalMobile");
            return (Criteria) this;
        }

        public Criteria andAgLegalMobileLessThanOrEqualTo(String value) {
            addCriterion("AG_LEGAL_MOBILE <=", value, "agLegalMobile");
            return (Criteria) this;
        }

        public Criteria andAgLegalMobileLike(String value) {
            addCriterion("AG_LEGAL_MOBILE like", value, "agLegalMobile");
            return (Criteria) this;
        }

        public Criteria andAgLegalMobileNotLike(String value) {
            addCriterion("AG_LEGAL_MOBILE not like", value, "agLegalMobile");
            return (Criteria) this;
        }

        public Criteria andAgLegalMobileIn(List<String> values) {
            addCriterion("AG_LEGAL_MOBILE in", values, "agLegalMobile");
            return (Criteria) this;
        }

        public Criteria andAgLegalMobileNotIn(List<String> values) {
            addCriterion("AG_LEGAL_MOBILE not in", values, "agLegalMobile");
            return (Criteria) this;
        }

        public Criteria andAgLegalMobileBetween(String value1, String value2) {
            addCriterion("AG_LEGAL_MOBILE between", value1, value2, "agLegalMobile");
            return (Criteria) this;
        }

        public Criteria andAgLegalMobileNotBetween(String value1, String value2) {
            addCriterion("AG_LEGAL_MOBILE not between", value1, value2, "agLegalMobile");
            return (Criteria) this;
        }

        public Criteria andAgHeadIsNull() {
            addCriterion("AG_HEAD is null");
            return (Criteria) this;
        }

        public Criteria andAgHeadIsNotNull() {
            addCriterion("AG_HEAD is not null");
            return (Criteria) this;
        }

        public Criteria andAgHeadEqualTo(String value) {
            addCriterion("AG_HEAD =", value, "agHead");
            return (Criteria) this;
        }

        public Criteria andAgHeadNotEqualTo(String value) {
            addCriterion("AG_HEAD <>", value, "agHead");
            return (Criteria) this;
        }

        public Criteria andAgHeadGreaterThan(String value) {
            addCriterion("AG_HEAD >", value, "agHead");
            return (Criteria) this;
        }

        public Criteria andAgHeadGreaterThanOrEqualTo(String value) {
            addCriterion("AG_HEAD >=", value, "agHead");
            return (Criteria) this;
        }

        public Criteria andAgHeadLessThan(String value) {
            addCriterion("AG_HEAD <", value, "agHead");
            return (Criteria) this;
        }

        public Criteria andAgHeadLessThanOrEqualTo(String value) {
            addCriterion("AG_HEAD <=", value, "agHead");
            return (Criteria) this;
        }

        public Criteria andAgHeadLike(String value) {
            addCriterion("AG_HEAD like", value, "agHead");
            return (Criteria) this;
        }

        public Criteria andAgHeadNotLike(String value) {
            addCriterion("AG_HEAD not like", value, "agHead");
            return (Criteria) this;
        }

        public Criteria andAgHeadIn(List<String> values) {
            addCriterion("AG_HEAD in", values, "agHead");
            return (Criteria) this;
        }

        public Criteria andAgHeadNotIn(List<String> values) {
            addCriterion("AG_HEAD not in", values, "agHead");
            return (Criteria) this;
        }

        public Criteria andAgHeadBetween(String value1, String value2) {
            addCriterion("AG_HEAD between", value1, value2, "agHead");
            return (Criteria) this;
        }

        public Criteria andAgHeadNotBetween(String value1, String value2) {
            addCriterion("AG_HEAD not between", value1, value2, "agHead");
            return (Criteria) this;
        }

        public Criteria andAgHeadMobileIsNull() {
            addCriterion("AG_HEAD_MOBILE is null");
            return (Criteria) this;
        }

        public Criteria andAgHeadMobileIsNotNull() {
            addCriterion("AG_HEAD_MOBILE is not null");
            return (Criteria) this;
        }

        public Criteria andAgHeadMobileEqualTo(String value) {
            addCriterion("AG_HEAD_MOBILE =", value, "agHeadMobile");
            return (Criteria) this;
        }

        public Criteria andAgHeadMobileNotEqualTo(String value) {
            addCriterion("AG_HEAD_MOBILE <>", value, "agHeadMobile");
            return (Criteria) this;
        }

        public Criteria andAgHeadMobileGreaterThan(String value) {
            addCriterion("AG_HEAD_MOBILE >", value, "agHeadMobile");
            return (Criteria) this;
        }

        public Criteria andAgHeadMobileGreaterThanOrEqualTo(String value) {
            addCriterion("AG_HEAD_MOBILE >=", value, "agHeadMobile");
            return (Criteria) this;
        }

        public Criteria andAgHeadMobileLessThan(String value) {
            addCriterion("AG_HEAD_MOBILE <", value, "agHeadMobile");
            return (Criteria) this;
        }

        public Criteria andAgHeadMobileLessThanOrEqualTo(String value) {
            addCriterion("AG_HEAD_MOBILE <=", value, "agHeadMobile");
            return (Criteria) this;
        }

        public Criteria andAgHeadMobileLike(String value) {
            addCriterion("AG_HEAD_MOBILE like", value, "agHeadMobile");
            return (Criteria) this;
        }

        public Criteria andAgHeadMobileNotLike(String value) {
            addCriterion("AG_HEAD_MOBILE not like", value, "agHeadMobile");
            return (Criteria) this;
        }

        public Criteria andAgHeadMobileIn(List<String> values) {
            addCriterion("AG_HEAD_MOBILE in", values, "agHeadMobile");
            return (Criteria) this;
        }

        public Criteria andAgHeadMobileNotIn(List<String> values) {
            addCriterion("AG_HEAD_MOBILE not in", values, "agHeadMobile");
            return (Criteria) this;
        }

        public Criteria andAgHeadMobileBetween(String value1, String value2) {
            addCriterion("AG_HEAD_MOBILE between", value1, value2, "agHeadMobile");
            return (Criteria) this;
        }

        public Criteria andAgHeadMobileNotBetween(String value1, String value2) {
            addCriterion("AG_HEAD_MOBILE not between", value1, value2, "agHeadMobile");
            return (Criteria) this;
        }

        public Criteria andAgRegAddIsNull() {
            addCriterion("AG_REG_ADD is null");
            return (Criteria) this;
        }

        public Criteria andAgRegAddIsNotNull() {
            addCriterion("AG_REG_ADD is not null");
            return (Criteria) this;
        }

        public Criteria andAgRegAddEqualTo(String value) {
            addCriterion("AG_REG_ADD =", value, "agRegAdd");
            return (Criteria) this;
        }

        public Criteria andAgRegAddNotEqualTo(String value) {
            addCriterion("AG_REG_ADD <>", value, "agRegAdd");
            return (Criteria) this;
        }

        public Criteria andAgRegAddGreaterThan(String value) {
            addCriterion("AG_REG_ADD >", value, "agRegAdd");
            return (Criteria) this;
        }

        public Criteria andAgRegAddGreaterThanOrEqualTo(String value) {
            addCriterion("AG_REG_ADD >=", value, "agRegAdd");
            return (Criteria) this;
        }

        public Criteria andAgRegAddLessThan(String value) {
            addCriterion("AG_REG_ADD <", value, "agRegAdd");
            return (Criteria) this;
        }

        public Criteria andAgRegAddLessThanOrEqualTo(String value) {
            addCriterion("AG_REG_ADD <=", value, "agRegAdd");
            return (Criteria) this;
        }

        public Criteria andAgRegAddLike(String value) {
            addCriterion("AG_REG_ADD like", value, "agRegAdd");
            return (Criteria) this;
        }

        public Criteria andAgRegAddNotLike(String value) {
            addCriterion("AG_REG_ADD not like", value, "agRegAdd");
            return (Criteria) this;
        }

        public Criteria andAgRegAddIn(List<String> values) {
            addCriterion("AG_REG_ADD in", values, "agRegAdd");
            return (Criteria) this;
        }

        public Criteria andAgRegAddNotIn(List<String> values) {
            addCriterion("AG_REG_ADD not in", values, "agRegAdd");
            return (Criteria) this;
        }

        public Criteria andAgRegAddBetween(String value1, String value2) {
            addCriterion("AG_REG_ADD between", value1, value2, "agRegAdd");
            return (Criteria) this;
        }

        public Criteria andAgRegAddNotBetween(String value1, String value2) {
            addCriterion("AG_REG_ADD not between", value1, value2, "agRegAdd");
            return (Criteria) this;
        }

        public Criteria andAgBusScopeIsNull() {
            addCriterion("AG_BUS_SCOPE is null");
            return (Criteria) this;
        }

        public Criteria andAgBusScopeIsNotNull() {
            addCriterion("AG_BUS_SCOPE is not null");
            return (Criteria) this;
        }

        public Criteria andAgBusScopeEqualTo(String value) {
            addCriterion("AG_BUS_SCOPE =", value, "agBusScope");
            return (Criteria) this;
        }

        public Criteria andAgBusScopeNotEqualTo(String value) {
            addCriterion("AG_BUS_SCOPE <>", value, "agBusScope");
            return (Criteria) this;
        }

        public Criteria andAgBusScopeGreaterThan(String value) {
            addCriterion("AG_BUS_SCOPE >", value, "agBusScope");
            return (Criteria) this;
        }

        public Criteria andAgBusScopeGreaterThanOrEqualTo(String value) {
            addCriterion("AG_BUS_SCOPE >=", value, "agBusScope");
            return (Criteria) this;
        }

        public Criteria andAgBusScopeLessThan(String value) {
            addCriterion("AG_BUS_SCOPE <", value, "agBusScope");
            return (Criteria) this;
        }

        public Criteria andAgBusScopeLessThanOrEqualTo(String value) {
            addCriterion("AG_BUS_SCOPE <=", value, "agBusScope");
            return (Criteria) this;
        }

        public Criteria andAgBusScopeLike(String value) {
            addCriterion("AG_BUS_SCOPE like", value, "agBusScope");
            return (Criteria) this;
        }

        public Criteria andAgBusScopeNotLike(String value) {
            addCriterion("AG_BUS_SCOPE not like", value, "agBusScope");
            return (Criteria) this;
        }

        public Criteria andAgBusScopeIn(List<String> values) {
            addCriterion("AG_BUS_SCOPE in", values, "agBusScope");
            return (Criteria) this;
        }

        public Criteria andAgBusScopeNotIn(List<String> values) {
            addCriterion("AG_BUS_SCOPE not in", values, "agBusScope");
            return (Criteria) this;
        }

        public Criteria andAgBusScopeBetween(String value1, String value2) {
            addCriterion("AG_BUS_SCOPE between", value1, value2, "agBusScope");
            return (Criteria) this;
        }

        public Criteria andAgBusScopeNotBetween(String value1, String value2) {
            addCriterion("AG_BUS_SCOPE not between", value1, value2, "agBusScope");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointIsNull() {
            addCriterion("CLO_TAX_POINT is null");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointIsNotNull() {
            addCriterion("CLO_TAX_POINT is not null");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointEqualTo(BigDecimal value) {
            addCriterion("CLO_TAX_POINT =", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointNotEqualTo(BigDecimal value) {
            addCriterion("CLO_TAX_POINT <>", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointGreaterThan(BigDecimal value) {
            addCriterion("CLO_TAX_POINT >", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_TAX_POINT >=", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointLessThan(BigDecimal value) {
            addCriterion("CLO_TAX_POINT <", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_TAX_POINT <=", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointIn(List<BigDecimal> values) {
            addCriterion("CLO_TAX_POINT in", values, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointNotIn(List<BigDecimal> values) {
            addCriterion("CLO_TAX_POINT not in", values, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_TAX_POINT between", value1, value2, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_TAX_POINT not between", value1, value2, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andAgStatusIsNull() {
            addCriterion("AG_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andAgStatusIsNotNull() {
            addCriterion("AG_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andAgStatusEqualTo(String value) {
            addCriterion("AG_STATUS =", value, "agStatus");
            return (Criteria) this;
        }

        public Criteria andAgStatusNotEqualTo(String value) {
            addCriterion("AG_STATUS <>", value, "agStatus");
            return (Criteria) this;
        }

        public Criteria andAgStatusGreaterThan(String value) {
            addCriterion("AG_STATUS >", value, "agStatus");
            return (Criteria) this;
        }

        public Criteria andAgStatusGreaterThanOrEqualTo(String value) {
            addCriterion("AG_STATUS >=", value, "agStatus");
            return (Criteria) this;
        }

        public Criteria andAgStatusLessThan(String value) {
            addCriterion("AG_STATUS <", value, "agStatus");
            return (Criteria) this;
        }

        public Criteria andAgStatusLessThanOrEqualTo(String value) {
            addCriterion("AG_STATUS <=", value, "agStatus");
            return (Criteria) this;
        }

        public Criteria andAgStatusLike(String value) {
            addCriterion("AG_STATUS like", value, "agStatus");
            return (Criteria) this;
        }

        public Criteria andAgStatusNotLike(String value) {
            addCriterion("AG_STATUS not like", value, "agStatus");
            return (Criteria) this;
        }

        public Criteria andAgStatusIn(List<String> values) {
            addCriterion("AG_STATUS in", values, "agStatus");
            return (Criteria) this;
        }

        public Criteria andAgStatusNotIn(List<String> values) {
            addCriterion("AG_STATUS not in", values, "agStatus");
            return (Criteria) this;
        }

        public Criteria andAgStatusBetween(String value1, String value2) {
            addCriterion("AG_STATUS between", value1, value2, "agStatus");
            return (Criteria) this;
        }

        public Criteria andAgStatusNotBetween(String value1, String value2) {
            addCriterion("AG_STATUS not between", value1, value2, "agStatus");
            return (Criteria) this;
        }

        public Criteria andAgDocProIsNull() {
            addCriterion("AG_DOC_PRO is null");
            return (Criteria) this;
        }

        public Criteria andAgDocProIsNotNull() {
            addCriterion("AG_DOC_PRO is not null");
            return (Criteria) this;
        }

        public Criteria andAgDocProEqualTo(String value) {
            addCriterion("AG_DOC_PRO =", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProNotEqualTo(String value) {
            addCriterion("AG_DOC_PRO <>", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProGreaterThan(String value) {
            addCriterion("AG_DOC_PRO >", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProGreaterThanOrEqualTo(String value) {
            addCriterion("AG_DOC_PRO >=", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProLessThan(String value) {
            addCriterion("AG_DOC_PRO <", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProLessThanOrEqualTo(String value) {
            addCriterion("AG_DOC_PRO <=", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProLike(String value) {
            addCriterion("AG_DOC_PRO like", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProNotLike(String value) {
            addCriterion("AG_DOC_PRO not like", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProIn(List<String> values) {
            addCriterion("AG_DOC_PRO in", values, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProNotIn(List<String> values) {
            addCriterion("AG_DOC_PRO not in", values, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProBetween(String value1, String value2) {
            addCriterion("AG_DOC_PRO between", value1, value2, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProNotBetween(String value1, String value2) {
            addCriterion("AG_DOC_PRO not between", value1, value2, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictIsNull() {
            addCriterion("AG_DOC_DISTRICT is null");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictIsNotNull() {
            addCriterion("AG_DOC_DISTRICT is not null");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictEqualTo(String value) {
            addCriterion("AG_DOC_DISTRICT =", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictNotEqualTo(String value) {
            addCriterion("AG_DOC_DISTRICT <>", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictGreaterThan(String value) {
            addCriterion("AG_DOC_DISTRICT >", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictGreaterThanOrEqualTo(String value) {
            addCriterion("AG_DOC_DISTRICT >=", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictLessThan(String value) {
            addCriterion("AG_DOC_DISTRICT <", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictLessThanOrEqualTo(String value) {
            addCriterion("AG_DOC_DISTRICT <=", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictLike(String value) {
            addCriterion("AG_DOC_DISTRICT like", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictNotLike(String value) {
            addCriterion("AG_DOC_DISTRICT not like", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictIn(List<String> values) {
            addCriterion("AG_DOC_DISTRICT in", values, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictNotIn(List<String> values) {
            addCriterion("AG_DOC_DISTRICT not in", values, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictBetween(String value1, String value2) {
            addCriterion("AG_DOC_DISTRICT between", value1, value2, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictNotBetween(String value1, String value2) {
            addCriterion("AG_DOC_DISTRICT not between", value1, value2, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgRemarkIsNull() {
            addCriterion("AG_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andAgRemarkIsNotNull() {
            addCriterion("AG_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andAgRemarkEqualTo(String value) {
            addCriterion("AG_REMARK =", value, "agRemark");
            return (Criteria) this;
        }

        public Criteria andAgRemarkNotEqualTo(String value) {
            addCriterion("AG_REMARK <>", value, "agRemark");
            return (Criteria) this;
        }

        public Criteria andAgRemarkGreaterThan(String value) {
            addCriterion("AG_REMARK >", value, "agRemark");
            return (Criteria) this;
        }

        public Criteria andAgRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("AG_REMARK >=", value, "agRemark");
            return (Criteria) this;
        }

        public Criteria andAgRemarkLessThan(String value) {
            addCriterion("AG_REMARK <", value, "agRemark");
            return (Criteria) this;
        }

        public Criteria andAgRemarkLessThanOrEqualTo(String value) {
            addCriterion("AG_REMARK <=", value, "agRemark");
            return (Criteria) this;
        }

        public Criteria andAgRemarkLike(String value) {
            addCriterion("AG_REMARK like", value, "agRemark");
            return (Criteria) this;
        }

        public Criteria andAgRemarkNotLike(String value) {
            addCriterion("AG_REMARK not like", value, "agRemark");
            return (Criteria) this;
        }

        public Criteria andAgRemarkIn(List<String> values) {
            addCriterion("AG_REMARK in", values, "agRemark");
            return (Criteria) this;
        }

        public Criteria andAgRemarkNotIn(List<String> values) {
            addCriterion("AG_REMARK not in", values, "agRemark");
            return (Criteria) this;
        }

        public Criteria andAgRemarkBetween(String value1, String value2) {
            addCriterion("AG_REMARK between", value1, value2, "agRemark");
            return (Criteria) this;
        }

        public Criteria andAgRemarkNotBetween(String value1, String value2) {
            addCriterion("AG_REMARK not between", value1, value2, "agRemark");
            return (Criteria) this;
        }

        public Criteria andCIncomTimeIsNull() {
            addCriterion("C_INCOM_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCIncomTimeIsNotNull() {
            addCriterion("C_INCOM_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCIncomTimeEqualTo(Date value) {
            addCriterion("C_INCOM_TIME =", value, "cIncomTime");
            return (Criteria) this;
        }

        public Criteria andCIncomTimeNotEqualTo(Date value) {
            addCriterion("C_INCOM_TIME <>", value, "cIncomTime");
            return (Criteria) this;
        }

        public Criteria andCIncomTimeGreaterThan(Date value) {
            addCriterion("C_INCOM_TIME >", value, "cIncomTime");
            return (Criteria) this;
        }

        public Criteria andCIncomTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("C_INCOM_TIME >=", value, "cIncomTime");
            return (Criteria) this;
        }

        public Criteria andCIncomTimeLessThan(Date value) {
            addCriterion("C_INCOM_TIME <", value, "cIncomTime");
            return (Criteria) this;
        }

        public Criteria andCIncomTimeLessThanOrEqualTo(Date value) {
            addCriterion("C_INCOM_TIME <=", value, "cIncomTime");
            return (Criteria) this;
        }

        public Criteria andCIncomTimeIn(List<Date> values) {
            addCriterion("C_INCOM_TIME in", values, "cIncomTime");
            return (Criteria) this;
        }

        public Criteria andCIncomTimeNotIn(List<Date> values) {
            addCriterion("C_INCOM_TIME not in", values, "cIncomTime");
            return (Criteria) this;
        }

        public Criteria andCIncomTimeBetween(Date value1, Date value2) {
            addCriterion("C_INCOM_TIME between", value1, value2, "cIncomTime");
            return (Criteria) this;
        }

        public Criteria andCIncomTimeNotBetween(Date value1, Date value2) {
            addCriterion("C_INCOM_TIME not between", value1, value2, "cIncomTime");
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

        public Criteria andCUtimeIsNull() {
            addCriterion("C_UTIME is null");
            return (Criteria) this;
        }

        public Criteria andCUtimeIsNotNull() {
            addCriterion("C_UTIME is not null");
            return (Criteria) this;
        }

        public Criteria andCUtimeEqualTo(Date value) {
            addCriterion("C_UTIME =", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeNotEqualTo(Date value) {
            addCriterion("C_UTIME <>", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeGreaterThan(Date value) {
            addCriterion("C_UTIME >", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("C_UTIME >=", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeLessThan(Date value) {
            addCriterion("C_UTIME <", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeLessThanOrEqualTo(Date value) {
            addCriterion("C_UTIME <=", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeIn(List<Date> values) {
            addCriterion("C_UTIME in", values, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeNotIn(List<Date> values) {
            addCriterion("C_UTIME not in", values, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeBetween(Date value1, Date value2) {
            addCriterion("C_UTIME between", value1, value2, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeNotBetween(Date value1, Date value2) {
            addCriterion("C_UTIME not between", value1, value2, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCIncomStatusIsNull() {
            addCriterion("C_INCOM_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andCIncomStatusIsNotNull() {
            addCriterion("C_INCOM_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andCIncomStatusEqualTo(BigDecimal value) {
            addCriterion("C_INCOM_STATUS =", value, "cIncomStatus");
            return (Criteria) this;
        }

        public Criteria andCIncomStatusNotEqualTo(BigDecimal value) {
            addCriterion("C_INCOM_STATUS <>", value, "cIncomStatus");
            return (Criteria) this;
        }

        public Criteria andCIncomStatusGreaterThan(BigDecimal value) {
            addCriterion("C_INCOM_STATUS >", value, "cIncomStatus");
            return (Criteria) this;
        }

        public Criteria andCIncomStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("C_INCOM_STATUS >=", value, "cIncomStatus");
            return (Criteria) this;
        }

        public Criteria andCIncomStatusLessThan(BigDecimal value) {
            addCriterion("C_INCOM_STATUS <", value, "cIncomStatus");
            return (Criteria) this;
        }

        public Criteria andCIncomStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("C_INCOM_STATUS <=", value, "cIncomStatus");
            return (Criteria) this;
        }

        public Criteria andCIncomStatusIn(List<BigDecimal> values) {
            addCriterion("C_INCOM_STATUS in", values, "cIncomStatus");
            return (Criteria) this;
        }

        public Criteria andCIncomStatusNotIn(List<BigDecimal> values) {
            addCriterion("C_INCOM_STATUS not in", values, "cIncomStatus");
            return (Criteria) this;
        }

        public Criteria andCIncomStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_INCOM_STATUS between", value1, value2, "cIncomStatus");
            return (Criteria) this;
        }

        public Criteria andCIncomStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("C_INCOM_STATUS not between", value1, value2, "cIncomStatus");
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

        public Criteria andAgZbhIsNull() {
            addCriterion("AG_ZBH is null");
            return (Criteria) this;
        }

        public Criteria andAgZbhIsNotNull() {
            addCriterion("AG_ZBH is not null");
            return (Criteria) this;
        }

        public Criteria andAgZbhEqualTo(String value) {
            addCriterion("AG_ZBH =", value, "agZbh");
            return (Criteria) this;
        }

        public Criteria andAgZbhNotEqualTo(String value) {
            addCriterion("AG_ZBH <>", value, "agZbh");
            return (Criteria) this;
        }

        public Criteria andAgZbhGreaterThan(String value) {
            addCriterion("AG_ZBH >", value, "agZbh");
            return (Criteria) this;
        }

        public Criteria andAgZbhGreaterThanOrEqualTo(String value) {
            addCriterion("AG_ZBH >=", value, "agZbh");
            return (Criteria) this;
        }

        public Criteria andAgZbhLessThan(String value) {
            addCriterion("AG_ZBH <", value, "agZbh");
            return (Criteria) this;
        }

        public Criteria andAgZbhLessThanOrEqualTo(String value) {
            addCriterion("AG_ZBH <=", value, "agZbh");
            return (Criteria) this;
        }

        public Criteria andAgZbhLike(String value) {
            addCriterion("AG_ZBH like", value, "agZbh");
            return (Criteria) this;
        }

        public Criteria andAgZbhNotLike(String value) {
            addCriterion("AG_ZBH not like", value, "agZbh");
            return (Criteria) this;
        }

        public Criteria andAgZbhIn(List<String> values) {
            addCriterion("AG_ZBH in", values, "agZbh");
            return (Criteria) this;
        }

        public Criteria andAgZbhNotIn(List<String> values) {
            addCriterion("AG_ZBH not in", values, "agZbh");
            return (Criteria) this;
        }

        public Criteria andAgZbhBetween(String value1, String value2) {
            addCriterion("AG_ZBH between", value1, value2, "agZbh");
            return (Criteria) this;
        }

        public Criteria andAgZbhNotBetween(String value1, String value2) {
            addCriterion("AG_ZBH not between", value1, value2, "agZbh");
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