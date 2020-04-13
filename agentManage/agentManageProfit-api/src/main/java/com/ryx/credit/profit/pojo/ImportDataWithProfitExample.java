package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ImportDataWithProfitExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ImportDataWithProfitExample() {
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

        public Criteria andMonthIsNull() {
            addCriterion("MONTH is null");
            return (Criteria) this;
        }

        public Criteria andMonthIsNotNull() {
            addCriterion("MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andMonthEqualTo(String value) {
            addCriterion("MONTH =", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotEqualTo(String value) {
            addCriterion("MONTH <>", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThan(String value) {
            addCriterion("MONTH >", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThanOrEqualTo(String value) {
            addCriterion("MONTH >=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThan(String value) {
            addCriterion("MONTH <", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThanOrEqualTo(String value) {
            addCriterion("MONTH <=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLike(String value) {
            addCriterion("MONTH like", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotLike(String value) {
            addCriterion("MONTH not like", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthIn(List<String> values) {
            addCriterion("MONTH in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotIn(List<String> values) {
            addCriterion("MONTH not in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthBetween(String value1, String value2) {
            addCriterion("MONTH between", value1, value2, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotBetween(String value1, String value2) {
            addCriterion("MONTH not between", value1, value2, "month");
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

        public Criteria andAgentNameIsNull() {
            addCriterion("AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAgentNameIsNotNull() {
            addCriterion("AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAgentNameEqualTo(String value) {
            addCriterion("AGENT_NAME =", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotEqualTo(String value) {
            addCriterion("AGENT_NAME <>", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThan(String value) {
            addCriterion("AGENT_NAME >", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_NAME >=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThan(String value) {
            addCriterion("AGENT_NAME <", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThanOrEqualTo(String value) {
            addCriterion("AGENT_NAME <=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLike(String value) {
            addCriterion("AGENT_NAME like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotLike(String value) {
            addCriterion("AGENT_NAME not like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameIn(List<String> values) {
            addCriterion("AGENT_NAME in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotIn(List<String> values) {
            addCriterion("AGENT_NAME not in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameBetween(String value1, String value2) {
            addCriterion("AGENT_NAME between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotBetween(String value1, String value2) {
            addCriterion("AGENT_NAME not between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andBusNumIsNull() {
            addCriterion("BUS_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBusNumIsNotNull() {
            addCriterion("BUS_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBusNumEqualTo(String value) {
            addCriterion("BUS_NUM =", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotEqualTo(String value) {
            addCriterion("BUS_NUM <>", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumGreaterThan(String value) {
            addCriterion("BUS_NUM >", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_NUM >=", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumLessThan(String value) {
            addCriterion("BUS_NUM <", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumLessThanOrEqualTo(String value) {
            addCriterion("BUS_NUM <=", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumLike(String value) {
            addCriterion("BUS_NUM like", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotLike(String value) {
            addCriterion("BUS_NUM not like", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumIn(List<String> values) {
            addCriterion("BUS_NUM in", values, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotIn(List<String> values) {
            addCriterion("BUS_NUM not in", values, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumBetween(String value1, String value2) {
            addCriterion("BUS_NUM between", value1, value2, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotBetween(String value1, String value2) {
            addCriterion("BUS_NUM not between", value1, value2, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusCodeIsNull() {
            addCriterion("BUS_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBusCodeIsNotNull() {
            addCriterion("BUS_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBusCodeEqualTo(String value) {
            addCriterion("BUS_CODE =", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotEqualTo(String value) {
            addCriterion("BUS_CODE <>", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeGreaterThan(String value) {
            addCriterion("BUS_CODE >", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_CODE >=", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeLessThan(String value) {
            addCriterion("BUS_CODE <", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeLessThanOrEqualTo(String value) {
            addCriterion("BUS_CODE <=", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeLike(String value) {
            addCriterion("BUS_CODE like", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotLike(String value) {
            addCriterion("BUS_CODE not like", value, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeIn(List<String> values) {
            addCriterion("BUS_CODE in", values, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotIn(List<String> values) {
            addCriterion("BUS_CODE not in", values, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeBetween(String value1, String value2) {
            addCriterion("BUS_CODE between", value1, value2, "busCode");
            return (Criteria) this;
        }

        public Criteria andBusCodeNotBetween(String value1, String value2) {
            addCriterion("BUS_CODE not between", value1, value2, "busCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeIsNull() {
            addCriterion("BRAND_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBrandCodeIsNotNull() {
            addCriterion("BRAND_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBrandCodeEqualTo(String value) {
            addCriterion("BRAND_CODE =", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeNotEqualTo(String value) {
            addCriterion("BRAND_CODE <>", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeGreaterThan(String value) {
            addCriterion("BRAND_CODE >", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BRAND_CODE >=", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeLessThan(String value) {
            addCriterion("BRAND_CODE <", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeLessThanOrEqualTo(String value) {
            addCriterion("BRAND_CODE <=", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeLike(String value) {
            addCriterion("BRAND_CODE like", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeNotLike(String value) {
            addCriterion("BRAND_CODE not like", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeIn(List<String> values) {
            addCriterion("BRAND_CODE in", values, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeNotIn(List<String> values) {
            addCriterion("BRAND_CODE not in", values, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeBetween(String value1, String value2) {
            addCriterion("BRAND_CODE between", value1, value2, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeNotBetween(String value1, String value2) {
            addCriterion("BRAND_CODE not between", value1, value2, "brandCode");
            return (Criteria) this;
        }

        public Criteria andImportTypeIsNull() {
            addCriterion("IMPORT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andImportTypeIsNotNull() {
            addCriterion("IMPORT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andImportTypeEqualTo(String value) {
            addCriterion("IMPORT_TYPE =", value, "importType");
            return (Criteria) this;
        }

        public Criteria andImportTypeNotEqualTo(String value) {
            addCriterion("IMPORT_TYPE <>", value, "importType");
            return (Criteria) this;
        }

        public Criteria andImportTypeGreaterThan(String value) {
            addCriterion("IMPORT_TYPE >", value, "importType");
            return (Criteria) this;
        }

        public Criteria andImportTypeGreaterThanOrEqualTo(String value) {
            addCriterion("IMPORT_TYPE >=", value, "importType");
            return (Criteria) this;
        }

        public Criteria andImportTypeLessThan(String value) {
            addCriterion("IMPORT_TYPE <", value, "importType");
            return (Criteria) this;
        }

        public Criteria andImportTypeLessThanOrEqualTo(String value) {
            addCriterion("IMPORT_TYPE <=", value, "importType");
            return (Criteria) this;
        }

        public Criteria andImportTypeLike(String value) {
            addCriterion("IMPORT_TYPE like", value, "importType");
            return (Criteria) this;
        }

        public Criteria andImportTypeNotLike(String value) {
            addCriterion("IMPORT_TYPE not like", value, "importType");
            return (Criteria) this;
        }

        public Criteria andImportTypeIn(List<String> values) {
            addCriterion("IMPORT_TYPE in", values, "importType");
            return (Criteria) this;
        }

        public Criteria andImportTypeNotIn(List<String> values) {
            addCriterion("IMPORT_TYPE not in", values, "importType");
            return (Criteria) this;
        }

        public Criteria andImportTypeBetween(String value1, String value2) {
            addCriterion("IMPORT_TYPE between", value1, value2, "importType");
            return (Criteria) this;
        }

        public Criteria andImportTypeNotBetween(String value1, String value2) {
            addCriterion("IMPORT_TYPE not between", value1, value2, "importType");
            return (Criteria) this;
        }

        public Criteria andSheetNameIsNull() {
            addCriterion("SHEET_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSheetNameIsNotNull() {
            addCriterion("SHEET_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSheetNameEqualTo(String value) {
            addCriterion("SHEET_NAME =", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameNotEqualTo(String value) {
            addCriterion("SHEET_NAME <>", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameGreaterThan(String value) {
            addCriterion("SHEET_NAME >", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameGreaterThanOrEqualTo(String value) {
            addCriterion("SHEET_NAME >=", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameLessThan(String value) {
            addCriterion("SHEET_NAME <", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameLessThanOrEqualTo(String value) {
            addCriterion("SHEET_NAME <=", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameLike(String value) {
            addCriterion("SHEET_NAME like", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameNotLike(String value) {
            addCriterion("SHEET_NAME not like", value, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameIn(List<String> values) {
            addCriterion("SHEET_NAME in", values, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameNotIn(List<String> values) {
            addCriterion("SHEET_NAME not in", values, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameBetween(String value1, String value2) {
            addCriterion("SHEET_NAME between", value1, value2, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetNameNotBetween(String value1, String value2) {
            addCriterion("SHEET_NAME not between", value1, value2, "sheetName");
            return (Criteria) this;
        }

        public Criteria andSheetColumnIsNull() {
            addCriterion("SHEET_COLUMN is null");
            return (Criteria) this;
        }

        public Criteria andSheetColumnIsNotNull() {
            addCriterion("SHEET_COLUMN is not null");
            return (Criteria) this;
        }

        public Criteria andSheetColumnEqualTo(BigDecimal value) {
            addCriterion("SHEET_COLUMN =", value, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnNotEqualTo(BigDecimal value) {
            addCriterion("SHEET_COLUMN <>", value, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnGreaterThan(BigDecimal value) {
            addCriterion("SHEET_COLUMN >", value, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SHEET_COLUMN >=", value, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnLessThan(BigDecimal value) {
            addCriterion("SHEET_COLUMN <", value, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SHEET_COLUMN <=", value, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnIn(List<BigDecimal> values) {
            addCriterion("SHEET_COLUMN in", values, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnNotIn(List<BigDecimal> values) {
            addCriterion("SHEET_COLUMN not in", values, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SHEET_COLUMN between", value1, value2, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andSheetColumnNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SHEET_COLUMN not between", value1, value2, "sheetColumn");
            return (Criteria) this;
        }

        public Criteria andUploadUserIsNull() {
            addCriterion("UPLOAD_USER is null");
            return (Criteria) this;
        }

        public Criteria andUploadUserIsNotNull() {
            addCriterion("UPLOAD_USER is not null");
            return (Criteria) this;
        }

        public Criteria andUploadUserEqualTo(String value) {
            addCriterion("UPLOAD_USER =", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserNotEqualTo(String value) {
            addCriterion("UPLOAD_USER <>", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserGreaterThan(String value) {
            addCriterion("UPLOAD_USER >", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserGreaterThanOrEqualTo(String value) {
            addCriterion("UPLOAD_USER >=", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserLessThan(String value) {
            addCriterion("UPLOAD_USER <", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserLessThanOrEqualTo(String value) {
            addCriterion("UPLOAD_USER <=", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserLike(String value) {
            addCriterion("UPLOAD_USER like", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserNotLike(String value) {
            addCriterion("UPLOAD_USER not like", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserIn(List<String> values) {
            addCriterion("UPLOAD_USER in", values, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserNotIn(List<String> values) {
            addCriterion("UPLOAD_USER not in", values, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserBetween(String value1, String value2) {
            addCriterion("UPLOAD_USER between", value1, value2, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserNotBetween(String value1, String value2) {
            addCriterion("UPLOAD_USER not between", value1, value2, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNull() {
            addCriterion("UPLOAD_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNotNull() {
            addCriterion("UPLOAD_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeEqualTo(String value) {
            addCriterion("UPLOAD_TIME =", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotEqualTo(String value) {
            addCriterion("UPLOAD_TIME <>", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThan(String value) {
            addCriterion("UPLOAD_TIME >", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThanOrEqualTo(String value) {
            addCriterion("UPLOAD_TIME >=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThan(String value) {
            addCriterion("UPLOAD_TIME <", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThanOrEqualTo(String value) {
            addCriterion("UPLOAD_TIME <=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLike(String value) {
            addCriterion("UPLOAD_TIME like", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotLike(String value) {
            addCriterion("UPLOAD_TIME not like", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIn(List<String> values) {
            addCriterion("UPLOAD_TIME in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotIn(List<String> values) {
            addCriterion("UPLOAD_TIME not in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeBetween(String value1, String value2) {
            addCriterion("UPLOAD_TIME between", value1, value2, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotBetween(String value1, String value2) {
            addCriterion("UPLOAD_TIME not between", value1, value2, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andSheetOrderIsNull() {
            addCriterion("SHEET_ORDER is null");
            return (Criteria) this;
        }

        public Criteria andSheetOrderIsNotNull() {
            addCriterion("SHEET_ORDER is not null");
            return (Criteria) this;
        }

        public Criteria andSheetOrderEqualTo(BigDecimal value) {
            addCriterion("SHEET_ORDER =", value, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderNotEqualTo(BigDecimal value) {
            addCriterion("SHEET_ORDER <>", value, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderGreaterThan(BigDecimal value) {
            addCriterion("SHEET_ORDER >", value, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SHEET_ORDER >=", value, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderLessThan(BigDecimal value) {
            addCriterion("SHEET_ORDER <", value, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SHEET_ORDER <=", value, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderIn(List<BigDecimal> values) {
            addCriterion("SHEET_ORDER in", values, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderNotIn(List<BigDecimal> values) {
            addCriterion("SHEET_ORDER not in", values, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SHEET_ORDER between", value1, value2, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andSheetOrderNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SHEET_ORDER not between", value1, value2, "sheetOrder");
            return (Criteria) this;
        }

        public Criteria andRowOrderIsNull() {
            addCriterion("ROW_ORDER is null");
            return (Criteria) this;
        }

        public Criteria andRowOrderIsNotNull() {
            addCriterion("ROW_ORDER is not null");
            return (Criteria) this;
        }

        public Criteria andRowOrderEqualTo(BigDecimal value) {
            addCriterion("ROW_ORDER =", value, "rowOrder");
            return (Criteria) this;
        }

        public Criteria andRowOrderNotEqualTo(BigDecimal value) {
            addCriterion("ROW_ORDER <>", value, "rowOrder");
            return (Criteria) this;
        }

        public Criteria andRowOrderGreaterThan(BigDecimal value) {
            addCriterion("ROW_ORDER >", value, "rowOrder");
            return (Criteria) this;
        }

        public Criteria andRowOrderGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ROW_ORDER >=", value, "rowOrder");
            return (Criteria) this;
        }

        public Criteria andRowOrderLessThan(BigDecimal value) {
            addCriterion("ROW_ORDER <", value, "rowOrder");
            return (Criteria) this;
        }

        public Criteria andRowOrderLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ROW_ORDER <=", value, "rowOrder");
            return (Criteria) this;
        }

        public Criteria andRowOrderIn(List<BigDecimal> values) {
            addCriterion("ROW_ORDER in", values, "rowOrder");
            return (Criteria) this;
        }

        public Criteria andRowOrderNotIn(List<BigDecimal> values) {
            addCriterion("ROW_ORDER not in", values, "rowOrder");
            return (Criteria) this;
        }

        public Criteria andRowOrderBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROW_ORDER between", value1, value2, "rowOrder");
            return (Criteria) this;
        }

        public Criteria andRowOrderNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROW_ORDER not between", value1, value2, "rowOrder");
            return (Criteria) this;
        }

        public Criteria andF7IsNull() {
            addCriterion("F7 is null");
            return (Criteria) this;
        }

        public Criteria andF7IsNotNull() {
            addCriterion("F7 is not null");
            return (Criteria) this;
        }

        public Criteria andF7EqualTo(String value) {
            addCriterion("F7 =", value, "f7");
            return (Criteria) this;
        }

        public Criteria andF7NotEqualTo(String value) {
            addCriterion("F7 <>", value, "f7");
            return (Criteria) this;
        }

        public Criteria andF7GreaterThan(String value) {
            addCriterion("F7 >", value, "f7");
            return (Criteria) this;
        }

        public Criteria andF7GreaterThanOrEqualTo(String value) {
            addCriterion("F7 >=", value, "f7");
            return (Criteria) this;
        }

        public Criteria andF7LessThan(String value) {
            addCriterion("F7 <", value, "f7");
            return (Criteria) this;
        }

        public Criteria andF7LessThanOrEqualTo(String value) {
            addCriterion("F7 <=", value, "f7");
            return (Criteria) this;
        }

        public Criteria andF7Like(String value) {
            addCriterion("F7 like", value, "f7");
            return (Criteria) this;
        }

        public Criteria andF7NotLike(String value) {
            addCriterion("F7 not like", value, "f7");
            return (Criteria) this;
        }

        public Criteria andF7In(List<String> values) {
            addCriterion("F7 in", values, "f7");
            return (Criteria) this;
        }

        public Criteria andF7NotIn(List<String> values) {
            addCriterion("F7 not in", values, "f7");
            return (Criteria) this;
        }

        public Criteria andF7Between(String value1, String value2) {
            addCriterion("F7 between", value1, value2, "f7");
            return (Criteria) this;
        }

        public Criteria andF7NotBetween(String value1, String value2) {
            addCriterion("F7 not between", value1, value2, "f7");
            return (Criteria) this;
        }

        public Criteria andF8IsNull() {
            addCriterion("F8 is null");
            return (Criteria) this;
        }

        public Criteria andF8IsNotNull() {
            addCriterion("F8 is not null");
            return (Criteria) this;
        }

        public Criteria andF8EqualTo(String value) {
            addCriterion("F8 =", value, "f8");
            return (Criteria) this;
        }

        public Criteria andF8NotEqualTo(String value) {
            addCriterion("F8 <>", value, "f8");
            return (Criteria) this;
        }

        public Criteria andF8GreaterThan(String value) {
            addCriterion("F8 >", value, "f8");
            return (Criteria) this;
        }

        public Criteria andF8GreaterThanOrEqualTo(String value) {
            addCriterion("F8 >=", value, "f8");
            return (Criteria) this;
        }

        public Criteria andF8LessThan(String value) {
            addCriterion("F8 <", value, "f8");
            return (Criteria) this;
        }

        public Criteria andF8LessThanOrEqualTo(String value) {
            addCriterion("F8 <=", value, "f8");
            return (Criteria) this;
        }

        public Criteria andF8Like(String value) {
            addCriterion("F8 like", value, "f8");
            return (Criteria) this;
        }

        public Criteria andF8NotLike(String value) {
            addCriterion("F8 not like", value, "f8");
            return (Criteria) this;
        }

        public Criteria andF8In(List<String> values) {
            addCriterion("F8 in", values, "f8");
            return (Criteria) this;
        }

        public Criteria andF8NotIn(List<String> values) {
            addCriterion("F8 not in", values, "f8");
            return (Criteria) this;
        }

        public Criteria andF8Between(String value1, String value2) {
            addCriterion("F8 between", value1, value2, "f8");
            return (Criteria) this;
        }

        public Criteria andF8NotBetween(String value1, String value2) {
            addCriterion("F8 not between", value1, value2, "f8");
            return (Criteria) this;
        }

        public Criteria andF9IsNull() {
            addCriterion("F9 is null");
            return (Criteria) this;
        }

        public Criteria andF9IsNotNull() {
            addCriterion("F9 is not null");
            return (Criteria) this;
        }

        public Criteria andF9EqualTo(String value) {
            addCriterion("F9 =", value, "f9");
            return (Criteria) this;
        }

        public Criteria andF9NotEqualTo(String value) {
            addCriterion("F9 <>", value, "f9");
            return (Criteria) this;
        }

        public Criteria andF9GreaterThan(String value) {
            addCriterion("F9 >", value, "f9");
            return (Criteria) this;
        }

        public Criteria andF9GreaterThanOrEqualTo(String value) {
            addCriterion("F9 >=", value, "f9");
            return (Criteria) this;
        }

        public Criteria andF9LessThan(String value) {
            addCriterion("F9 <", value, "f9");
            return (Criteria) this;
        }

        public Criteria andF9LessThanOrEqualTo(String value) {
            addCriterion("F9 <=", value, "f9");
            return (Criteria) this;
        }

        public Criteria andF9Like(String value) {
            addCriterion("F9 like", value, "f9");
            return (Criteria) this;
        }

        public Criteria andF9NotLike(String value) {
            addCriterion("F9 not like", value, "f9");
            return (Criteria) this;
        }

        public Criteria andF9In(List<String> values) {
            addCriterion("F9 in", values, "f9");
            return (Criteria) this;
        }

        public Criteria andF9NotIn(List<String> values) {
            addCriterion("F9 not in", values, "f9");
            return (Criteria) this;
        }

        public Criteria andF9Between(String value1, String value2) {
            addCriterion("F9 between", value1, value2, "f9");
            return (Criteria) this;
        }

        public Criteria andF9NotBetween(String value1, String value2) {
            addCriterion("F9 not between", value1, value2, "f9");
            return (Criteria) this;
        }

        public Criteria andF10IsNull() {
            addCriterion("F10 is null");
            return (Criteria) this;
        }

        public Criteria andF10IsNotNull() {
            addCriterion("F10 is not null");
            return (Criteria) this;
        }

        public Criteria andF10EqualTo(String value) {
            addCriterion("F10 =", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10NotEqualTo(String value) {
            addCriterion("F10 <>", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10GreaterThan(String value) {
            addCriterion("F10 >", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10GreaterThanOrEqualTo(String value) {
            addCriterion("F10 >=", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10LessThan(String value) {
            addCriterion("F10 <", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10LessThanOrEqualTo(String value) {
            addCriterion("F10 <=", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10Like(String value) {
            addCriterion("F10 like", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10NotLike(String value) {
            addCriterion("F10 not like", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10In(List<String> values) {
            addCriterion("F10 in", values, "f10");
            return (Criteria) this;
        }

        public Criteria andF10NotIn(List<String> values) {
            addCriterion("F10 not in", values, "f10");
            return (Criteria) this;
        }

        public Criteria andF10Between(String value1, String value2) {
            addCriterion("F10 between", value1, value2, "f10");
            return (Criteria) this;
        }

        public Criteria andF10NotBetween(String value1, String value2) {
            addCriterion("F10 not between", value1, value2, "f10");
            return (Criteria) this;
        }

        public Criteria andF11IsNull() {
            addCriterion("F11 is null");
            return (Criteria) this;
        }

        public Criteria andF11IsNotNull() {
            addCriterion("F11 is not null");
            return (Criteria) this;
        }

        public Criteria andF11EqualTo(String value) {
            addCriterion("F11 =", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11NotEqualTo(String value) {
            addCriterion("F11 <>", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11GreaterThan(String value) {
            addCriterion("F11 >", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11GreaterThanOrEqualTo(String value) {
            addCriterion("F11 >=", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11LessThan(String value) {
            addCriterion("F11 <", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11LessThanOrEqualTo(String value) {
            addCriterion("F11 <=", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11Like(String value) {
            addCriterion("F11 like", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11NotLike(String value) {
            addCriterion("F11 not like", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11In(List<String> values) {
            addCriterion("F11 in", values, "f11");
            return (Criteria) this;
        }

        public Criteria andF11NotIn(List<String> values) {
            addCriterion("F11 not in", values, "f11");
            return (Criteria) this;
        }

        public Criteria andF11Between(String value1, String value2) {
            addCriterion("F11 between", value1, value2, "f11");
            return (Criteria) this;
        }

        public Criteria andF11NotBetween(String value1, String value2) {
            addCriterion("F11 not between", value1, value2, "f11");
            return (Criteria) this;
        }

        public Criteria andF12IsNull() {
            addCriterion("F12 is null");
            return (Criteria) this;
        }

        public Criteria andF12IsNotNull() {
            addCriterion("F12 is not null");
            return (Criteria) this;
        }

        public Criteria andF12EqualTo(String value) {
            addCriterion("F12 =", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12NotEqualTo(String value) {
            addCriterion("F12 <>", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12GreaterThan(String value) {
            addCriterion("F12 >", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12GreaterThanOrEqualTo(String value) {
            addCriterion("F12 >=", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12LessThan(String value) {
            addCriterion("F12 <", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12LessThanOrEqualTo(String value) {
            addCriterion("F12 <=", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12Like(String value) {
            addCriterion("F12 like", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12NotLike(String value) {
            addCriterion("F12 not like", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12In(List<String> values) {
            addCriterion("F12 in", values, "f12");
            return (Criteria) this;
        }

        public Criteria andF12NotIn(List<String> values) {
            addCriterion("F12 not in", values, "f12");
            return (Criteria) this;
        }

        public Criteria andF12Between(String value1, String value2) {
            addCriterion("F12 between", value1, value2, "f12");
            return (Criteria) this;
        }

        public Criteria andF12NotBetween(String value1, String value2) {
            addCriterion("F12 not between", value1, value2, "f12");
            return (Criteria) this;
        }

        public Criteria andF13IsNull() {
            addCriterion("F13 is null");
            return (Criteria) this;
        }

        public Criteria andF13IsNotNull() {
            addCriterion("F13 is not null");
            return (Criteria) this;
        }

        public Criteria andF13EqualTo(String value) {
            addCriterion("F13 =", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13NotEqualTo(String value) {
            addCriterion("F13 <>", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13GreaterThan(String value) {
            addCriterion("F13 >", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13GreaterThanOrEqualTo(String value) {
            addCriterion("F13 >=", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13LessThan(String value) {
            addCriterion("F13 <", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13LessThanOrEqualTo(String value) {
            addCriterion("F13 <=", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13Like(String value) {
            addCriterion("F13 like", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13NotLike(String value) {
            addCriterion("F13 not like", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13In(List<String> values) {
            addCriterion("F13 in", values, "f13");
            return (Criteria) this;
        }

        public Criteria andF13NotIn(List<String> values) {
            addCriterion("F13 not in", values, "f13");
            return (Criteria) this;
        }

        public Criteria andF13Between(String value1, String value2) {
            addCriterion("F13 between", value1, value2, "f13");
            return (Criteria) this;
        }

        public Criteria andF13NotBetween(String value1, String value2) {
            addCriterion("F13 not between", value1, value2, "f13");
            return (Criteria) this;
        }

        public Criteria andF14IsNull() {
            addCriterion("F14 is null");
            return (Criteria) this;
        }

        public Criteria andF14IsNotNull() {
            addCriterion("F14 is not null");
            return (Criteria) this;
        }

        public Criteria andF14EqualTo(String value) {
            addCriterion("F14 =", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14NotEqualTo(String value) {
            addCriterion("F14 <>", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14GreaterThan(String value) {
            addCriterion("F14 >", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14GreaterThanOrEqualTo(String value) {
            addCriterion("F14 >=", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14LessThan(String value) {
            addCriterion("F14 <", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14LessThanOrEqualTo(String value) {
            addCriterion("F14 <=", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14Like(String value) {
            addCriterion("F14 like", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14NotLike(String value) {
            addCriterion("F14 not like", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14In(List<String> values) {
            addCriterion("F14 in", values, "f14");
            return (Criteria) this;
        }

        public Criteria andF14NotIn(List<String> values) {
            addCriterion("F14 not in", values, "f14");
            return (Criteria) this;
        }

        public Criteria andF14Between(String value1, String value2) {
            addCriterion("F14 between", value1, value2, "f14");
            return (Criteria) this;
        }

        public Criteria andF14NotBetween(String value1, String value2) {
            addCriterion("F14 not between", value1, value2, "f14");
            return (Criteria) this;
        }

        public Criteria andF15IsNull() {
            addCriterion("F15 is null");
            return (Criteria) this;
        }

        public Criteria andF15IsNotNull() {
            addCriterion("F15 is not null");
            return (Criteria) this;
        }

        public Criteria andF15EqualTo(String value) {
            addCriterion("F15 =", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15NotEqualTo(String value) {
            addCriterion("F15 <>", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15GreaterThan(String value) {
            addCriterion("F15 >", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15GreaterThanOrEqualTo(String value) {
            addCriterion("F15 >=", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15LessThan(String value) {
            addCriterion("F15 <", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15LessThanOrEqualTo(String value) {
            addCriterion("F15 <=", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15Like(String value) {
            addCriterion("F15 like", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15NotLike(String value) {
            addCriterion("F15 not like", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15In(List<String> values) {
            addCriterion("F15 in", values, "f15");
            return (Criteria) this;
        }

        public Criteria andF15NotIn(List<String> values) {
            addCriterion("F15 not in", values, "f15");
            return (Criteria) this;
        }

        public Criteria andF15Between(String value1, String value2) {
            addCriterion("F15 between", value1, value2, "f15");
            return (Criteria) this;
        }

        public Criteria andF15NotBetween(String value1, String value2) {
            addCriterion("F15 not between", value1, value2, "f15");
            return (Criteria) this;
        }

        public Criteria andF16IsNull() {
            addCriterion("F16 is null");
            return (Criteria) this;
        }

        public Criteria andF16IsNotNull() {
            addCriterion("F16 is not null");
            return (Criteria) this;
        }

        public Criteria andF16EqualTo(String value) {
            addCriterion("F16 =", value, "f16");
            return (Criteria) this;
        }

        public Criteria andF16NotEqualTo(String value) {
            addCriterion("F16 <>", value, "f16");
            return (Criteria) this;
        }

        public Criteria andF16GreaterThan(String value) {
            addCriterion("F16 >", value, "f16");
            return (Criteria) this;
        }

        public Criteria andF16GreaterThanOrEqualTo(String value) {
            addCriterion("F16 >=", value, "f16");
            return (Criteria) this;
        }

        public Criteria andF16LessThan(String value) {
            addCriterion("F16 <", value, "f16");
            return (Criteria) this;
        }

        public Criteria andF16LessThanOrEqualTo(String value) {
            addCriterion("F16 <=", value, "f16");
            return (Criteria) this;
        }

        public Criteria andF16Like(String value) {
            addCriterion("F16 like", value, "f16");
            return (Criteria) this;
        }

        public Criteria andF16NotLike(String value) {
            addCriterion("F16 not like", value, "f16");
            return (Criteria) this;
        }

        public Criteria andF16In(List<String> values) {
            addCriterion("F16 in", values, "f16");
            return (Criteria) this;
        }

        public Criteria andF16NotIn(List<String> values) {
            addCriterion("F16 not in", values, "f16");
            return (Criteria) this;
        }

        public Criteria andF16Between(String value1, String value2) {
            addCriterion("F16 between", value1, value2, "f16");
            return (Criteria) this;
        }

        public Criteria andF16NotBetween(String value1, String value2) {
            addCriterion("F16 not between", value1, value2, "f16");
            return (Criteria) this;
        }

        public Criteria andF17IsNull() {
            addCriterion("F17 is null");
            return (Criteria) this;
        }

        public Criteria andF17IsNotNull() {
            addCriterion("F17 is not null");
            return (Criteria) this;
        }

        public Criteria andF17EqualTo(String value) {
            addCriterion("F17 =", value, "f17");
            return (Criteria) this;
        }

        public Criteria andF17NotEqualTo(String value) {
            addCriterion("F17 <>", value, "f17");
            return (Criteria) this;
        }

        public Criteria andF17GreaterThan(String value) {
            addCriterion("F17 >", value, "f17");
            return (Criteria) this;
        }

        public Criteria andF17GreaterThanOrEqualTo(String value) {
            addCriterion("F17 >=", value, "f17");
            return (Criteria) this;
        }

        public Criteria andF17LessThan(String value) {
            addCriterion("F17 <", value, "f17");
            return (Criteria) this;
        }

        public Criteria andF17LessThanOrEqualTo(String value) {
            addCriterion("F17 <=", value, "f17");
            return (Criteria) this;
        }

        public Criteria andF17Like(String value) {
            addCriterion("F17 like", value, "f17");
            return (Criteria) this;
        }

        public Criteria andF17NotLike(String value) {
            addCriterion("F17 not like", value, "f17");
            return (Criteria) this;
        }

        public Criteria andF17In(List<String> values) {
            addCriterion("F17 in", values, "f17");
            return (Criteria) this;
        }

        public Criteria andF17NotIn(List<String> values) {
            addCriterion("F17 not in", values, "f17");
            return (Criteria) this;
        }

        public Criteria andF17Between(String value1, String value2) {
            addCriterion("F17 between", value1, value2, "f17");
            return (Criteria) this;
        }

        public Criteria andF17NotBetween(String value1, String value2) {
            addCriterion("F17 not between", value1, value2, "f17");
            return (Criteria) this;
        }

        public Criteria andF18IsNull() {
            addCriterion("F18 is null");
            return (Criteria) this;
        }

        public Criteria andF18IsNotNull() {
            addCriterion("F18 is not null");
            return (Criteria) this;
        }

        public Criteria andF18EqualTo(String value) {
            addCriterion("F18 =", value, "f18");
            return (Criteria) this;
        }

        public Criteria andF18NotEqualTo(String value) {
            addCriterion("F18 <>", value, "f18");
            return (Criteria) this;
        }

        public Criteria andF18GreaterThan(String value) {
            addCriterion("F18 >", value, "f18");
            return (Criteria) this;
        }

        public Criteria andF18GreaterThanOrEqualTo(String value) {
            addCriterion("F18 >=", value, "f18");
            return (Criteria) this;
        }

        public Criteria andF18LessThan(String value) {
            addCriterion("F18 <", value, "f18");
            return (Criteria) this;
        }

        public Criteria andF18LessThanOrEqualTo(String value) {
            addCriterion("F18 <=", value, "f18");
            return (Criteria) this;
        }

        public Criteria andF18Like(String value) {
            addCriterion("F18 like", value, "f18");
            return (Criteria) this;
        }

        public Criteria andF18NotLike(String value) {
            addCriterion("F18 not like", value, "f18");
            return (Criteria) this;
        }

        public Criteria andF18In(List<String> values) {
            addCriterion("F18 in", values, "f18");
            return (Criteria) this;
        }

        public Criteria andF18NotIn(List<String> values) {
            addCriterion("F18 not in", values, "f18");
            return (Criteria) this;
        }

        public Criteria andF18Between(String value1, String value2) {
            addCriterion("F18 between", value1, value2, "f18");
            return (Criteria) this;
        }

        public Criteria andF18NotBetween(String value1, String value2) {
            addCriterion("F18 not between", value1, value2, "f18");
            return (Criteria) this;
        }

        public Criteria andF19IsNull() {
            addCriterion("F19 is null");
            return (Criteria) this;
        }

        public Criteria andF19IsNotNull() {
            addCriterion("F19 is not null");
            return (Criteria) this;
        }

        public Criteria andF19EqualTo(String value) {
            addCriterion("F19 =", value, "f19");
            return (Criteria) this;
        }

        public Criteria andF19NotEqualTo(String value) {
            addCriterion("F19 <>", value, "f19");
            return (Criteria) this;
        }

        public Criteria andF19GreaterThan(String value) {
            addCriterion("F19 >", value, "f19");
            return (Criteria) this;
        }

        public Criteria andF19GreaterThanOrEqualTo(String value) {
            addCriterion("F19 >=", value, "f19");
            return (Criteria) this;
        }

        public Criteria andF19LessThan(String value) {
            addCriterion("F19 <", value, "f19");
            return (Criteria) this;
        }

        public Criteria andF19LessThanOrEqualTo(String value) {
            addCriterion("F19 <=", value, "f19");
            return (Criteria) this;
        }

        public Criteria andF19Like(String value) {
            addCriterion("F19 like", value, "f19");
            return (Criteria) this;
        }

        public Criteria andF19NotLike(String value) {
            addCriterion("F19 not like", value, "f19");
            return (Criteria) this;
        }

        public Criteria andF19In(List<String> values) {
            addCriterion("F19 in", values, "f19");
            return (Criteria) this;
        }

        public Criteria andF19NotIn(List<String> values) {
            addCriterion("F19 not in", values, "f19");
            return (Criteria) this;
        }

        public Criteria andF19Between(String value1, String value2) {
            addCriterion("F19 between", value1, value2, "f19");
            return (Criteria) this;
        }

        public Criteria andF19NotBetween(String value1, String value2) {
            addCriterion("F19 not between", value1, value2, "f19");
            return (Criteria) this;
        }

        public Criteria andF20IsNull() {
            addCriterion("F20 is null");
            return (Criteria) this;
        }

        public Criteria andF20IsNotNull() {
            addCriterion("F20 is not null");
            return (Criteria) this;
        }

        public Criteria andF20EqualTo(String value) {
            addCriterion("F20 =", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20NotEqualTo(String value) {
            addCriterion("F20 <>", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20GreaterThan(String value) {
            addCriterion("F20 >", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20GreaterThanOrEqualTo(String value) {
            addCriterion("F20 >=", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20LessThan(String value) {
            addCriterion("F20 <", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20LessThanOrEqualTo(String value) {
            addCriterion("F20 <=", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20Like(String value) {
            addCriterion("F20 like", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20NotLike(String value) {
            addCriterion("F20 not like", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20In(List<String> values) {
            addCriterion("F20 in", values, "f20");
            return (Criteria) this;
        }

        public Criteria andF20NotIn(List<String> values) {
            addCriterion("F20 not in", values, "f20");
            return (Criteria) this;
        }

        public Criteria andF20Between(String value1, String value2) {
            addCriterion("F20 between", value1, value2, "f20");
            return (Criteria) this;
        }

        public Criteria andF20NotBetween(String value1, String value2) {
            addCriterion("F20 not between", value1, value2, "f20");
            return (Criteria) this;
        }

        public Criteria andF21IsNull() {
            addCriterion("F21 is null");
            return (Criteria) this;
        }

        public Criteria andF21IsNotNull() {
            addCriterion("F21 is not null");
            return (Criteria) this;
        }

        public Criteria andF21EqualTo(String value) {
            addCriterion("F21 =", value, "f21");
            return (Criteria) this;
        }

        public Criteria andF21NotEqualTo(String value) {
            addCriterion("F21 <>", value, "f21");
            return (Criteria) this;
        }

        public Criteria andF21GreaterThan(String value) {
            addCriterion("F21 >", value, "f21");
            return (Criteria) this;
        }

        public Criteria andF21GreaterThanOrEqualTo(String value) {
            addCriterion("F21 >=", value, "f21");
            return (Criteria) this;
        }

        public Criteria andF21LessThan(String value) {
            addCriterion("F21 <", value, "f21");
            return (Criteria) this;
        }

        public Criteria andF21LessThanOrEqualTo(String value) {
            addCriterion("F21 <=", value, "f21");
            return (Criteria) this;
        }

        public Criteria andF21Like(String value) {
            addCriterion("F21 like", value, "f21");
            return (Criteria) this;
        }

        public Criteria andF21NotLike(String value) {
            addCriterion("F21 not like", value, "f21");
            return (Criteria) this;
        }

        public Criteria andF21In(List<String> values) {
            addCriterion("F21 in", values, "f21");
            return (Criteria) this;
        }

        public Criteria andF21NotIn(List<String> values) {
            addCriterion("F21 not in", values, "f21");
            return (Criteria) this;
        }

        public Criteria andF21Between(String value1, String value2) {
            addCriterion("F21 between", value1, value2, "f21");
            return (Criteria) this;
        }

        public Criteria andF21NotBetween(String value1, String value2) {
            addCriterion("F21 not between", value1, value2, "f21");
            return (Criteria) this;
        }

        public Criteria andF22IsNull() {
            addCriterion("F22 is null");
            return (Criteria) this;
        }

        public Criteria andF22IsNotNull() {
            addCriterion("F22 is not null");
            return (Criteria) this;
        }

        public Criteria andF22EqualTo(String value) {
            addCriterion("F22 =", value, "f22");
            return (Criteria) this;
        }

        public Criteria andF22NotEqualTo(String value) {
            addCriterion("F22 <>", value, "f22");
            return (Criteria) this;
        }

        public Criteria andF22GreaterThan(String value) {
            addCriterion("F22 >", value, "f22");
            return (Criteria) this;
        }

        public Criteria andF22GreaterThanOrEqualTo(String value) {
            addCriterion("F22 >=", value, "f22");
            return (Criteria) this;
        }

        public Criteria andF22LessThan(String value) {
            addCriterion("F22 <", value, "f22");
            return (Criteria) this;
        }

        public Criteria andF22LessThanOrEqualTo(String value) {
            addCriterion("F22 <=", value, "f22");
            return (Criteria) this;
        }

        public Criteria andF22Like(String value) {
            addCriterion("F22 like", value, "f22");
            return (Criteria) this;
        }

        public Criteria andF22NotLike(String value) {
            addCriterion("F22 not like", value, "f22");
            return (Criteria) this;
        }

        public Criteria andF22In(List<String> values) {
            addCriterion("F22 in", values, "f22");
            return (Criteria) this;
        }

        public Criteria andF22NotIn(List<String> values) {
            addCriterion("F22 not in", values, "f22");
            return (Criteria) this;
        }

        public Criteria andF22Between(String value1, String value2) {
            addCriterion("F22 between", value1, value2, "f22");
            return (Criteria) this;
        }

        public Criteria andF22NotBetween(String value1, String value2) {
            addCriterion("F22 not between", value1, value2, "f22");
            return (Criteria) this;
        }

        public Criteria andF23IsNull() {
            addCriterion("F23 is null");
            return (Criteria) this;
        }

        public Criteria andF23IsNotNull() {
            addCriterion("F23 is not null");
            return (Criteria) this;
        }

        public Criteria andF23EqualTo(String value) {
            addCriterion("F23 =", value, "f23");
            return (Criteria) this;
        }

        public Criteria andF23NotEqualTo(String value) {
            addCriterion("F23 <>", value, "f23");
            return (Criteria) this;
        }

        public Criteria andF23GreaterThan(String value) {
            addCriterion("F23 >", value, "f23");
            return (Criteria) this;
        }

        public Criteria andF23GreaterThanOrEqualTo(String value) {
            addCriterion("F23 >=", value, "f23");
            return (Criteria) this;
        }

        public Criteria andF23LessThan(String value) {
            addCriterion("F23 <", value, "f23");
            return (Criteria) this;
        }

        public Criteria andF23LessThanOrEqualTo(String value) {
            addCriterion("F23 <=", value, "f23");
            return (Criteria) this;
        }

        public Criteria andF23Like(String value) {
            addCriterion("F23 like", value, "f23");
            return (Criteria) this;
        }

        public Criteria andF23NotLike(String value) {
            addCriterion("F23 not like", value, "f23");
            return (Criteria) this;
        }

        public Criteria andF23In(List<String> values) {
            addCriterion("F23 in", values, "f23");
            return (Criteria) this;
        }

        public Criteria andF23NotIn(List<String> values) {
            addCriterion("F23 not in", values, "f23");
            return (Criteria) this;
        }

        public Criteria andF23Between(String value1, String value2) {
            addCriterion("F23 between", value1, value2, "f23");
            return (Criteria) this;
        }

        public Criteria andF23NotBetween(String value1, String value2) {
            addCriterion("F23 not between", value1, value2, "f23");
            return (Criteria) this;
        }

        public Criteria andF24IsNull() {
            addCriterion("F24 is null");
            return (Criteria) this;
        }

        public Criteria andF24IsNotNull() {
            addCriterion("F24 is not null");
            return (Criteria) this;
        }

        public Criteria andF24EqualTo(String value) {
            addCriterion("F24 =", value, "f24");
            return (Criteria) this;
        }

        public Criteria andF24NotEqualTo(String value) {
            addCriterion("F24 <>", value, "f24");
            return (Criteria) this;
        }

        public Criteria andF24GreaterThan(String value) {
            addCriterion("F24 >", value, "f24");
            return (Criteria) this;
        }

        public Criteria andF24GreaterThanOrEqualTo(String value) {
            addCriterion("F24 >=", value, "f24");
            return (Criteria) this;
        }

        public Criteria andF24LessThan(String value) {
            addCriterion("F24 <", value, "f24");
            return (Criteria) this;
        }

        public Criteria andF24LessThanOrEqualTo(String value) {
            addCriterion("F24 <=", value, "f24");
            return (Criteria) this;
        }

        public Criteria andF24Like(String value) {
            addCriterion("F24 like", value, "f24");
            return (Criteria) this;
        }

        public Criteria andF24NotLike(String value) {
            addCriterion("F24 not like", value, "f24");
            return (Criteria) this;
        }

        public Criteria andF24In(List<String> values) {
            addCriterion("F24 in", values, "f24");
            return (Criteria) this;
        }

        public Criteria andF24NotIn(List<String> values) {
            addCriterion("F24 not in", values, "f24");
            return (Criteria) this;
        }

        public Criteria andF24Between(String value1, String value2) {
            addCriterion("F24 between", value1, value2, "f24");
            return (Criteria) this;
        }

        public Criteria andF24NotBetween(String value1, String value2) {
            addCriterion("F24 not between", value1, value2, "f24");
            return (Criteria) this;
        }

        public Criteria andF25IsNull() {
            addCriterion("F25 is null");
            return (Criteria) this;
        }

        public Criteria andF25IsNotNull() {
            addCriterion("F25 is not null");
            return (Criteria) this;
        }

        public Criteria andF25EqualTo(String value) {
            addCriterion("F25 =", value, "f25");
            return (Criteria) this;
        }

        public Criteria andF25NotEqualTo(String value) {
            addCriterion("F25 <>", value, "f25");
            return (Criteria) this;
        }

        public Criteria andF25GreaterThan(String value) {
            addCriterion("F25 >", value, "f25");
            return (Criteria) this;
        }

        public Criteria andF25GreaterThanOrEqualTo(String value) {
            addCriterion("F25 >=", value, "f25");
            return (Criteria) this;
        }

        public Criteria andF25LessThan(String value) {
            addCriterion("F25 <", value, "f25");
            return (Criteria) this;
        }

        public Criteria andF25LessThanOrEqualTo(String value) {
            addCriterion("F25 <=", value, "f25");
            return (Criteria) this;
        }

        public Criteria andF25Like(String value) {
            addCriterion("F25 like", value, "f25");
            return (Criteria) this;
        }

        public Criteria andF25NotLike(String value) {
            addCriterion("F25 not like", value, "f25");
            return (Criteria) this;
        }

        public Criteria andF25In(List<String> values) {
            addCriterion("F25 in", values, "f25");
            return (Criteria) this;
        }

        public Criteria andF25NotIn(List<String> values) {
            addCriterion("F25 not in", values, "f25");
            return (Criteria) this;
        }

        public Criteria andF25Between(String value1, String value2) {
            addCriterion("F25 between", value1, value2, "f25");
            return (Criteria) this;
        }

        public Criteria andF25NotBetween(String value1, String value2) {
            addCriterion("F25 not between", value1, value2, "f25");
            return (Criteria) this;
        }

        public Criteria andF26IsNull() {
            addCriterion("F26 is null");
            return (Criteria) this;
        }

        public Criteria andF26IsNotNull() {
            addCriterion("F26 is not null");
            return (Criteria) this;
        }

        public Criteria andF26EqualTo(String value) {
            addCriterion("F26 =", value, "f26");
            return (Criteria) this;
        }

        public Criteria andF26NotEqualTo(String value) {
            addCriterion("F26 <>", value, "f26");
            return (Criteria) this;
        }

        public Criteria andF26GreaterThan(String value) {
            addCriterion("F26 >", value, "f26");
            return (Criteria) this;
        }

        public Criteria andF26GreaterThanOrEqualTo(String value) {
            addCriterion("F26 >=", value, "f26");
            return (Criteria) this;
        }

        public Criteria andF26LessThan(String value) {
            addCriterion("F26 <", value, "f26");
            return (Criteria) this;
        }

        public Criteria andF26LessThanOrEqualTo(String value) {
            addCriterion("F26 <=", value, "f26");
            return (Criteria) this;
        }

        public Criteria andF26Like(String value) {
            addCriterion("F26 like", value, "f26");
            return (Criteria) this;
        }

        public Criteria andF26NotLike(String value) {
            addCriterion("F26 not like", value, "f26");
            return (Criteria) this;
        }

        public Criteria andF26In(List<String> values) {
            addCriterion("F26 in", values, "f26");
            return (Criteria) this;
        }

        public Criteria andF26NotIn(List<String> values) {
            addCriterion("F26 not in", values, "f26");
            return (Criteria) this;
        }

        public Criteria andF26Between(String value1, String value2) {
            addCriterion("F26 between", value1, value2, "f26");
            return (Criteria) this;
        }

        public Criteria andF26NotBetween(String value1, String value2) {
            addCriterion("F26 not between", value1, value2, "f26");
            return (Criteria) this;
        }

        public Criteria andF27IsNull() {
            addCriterion("F27 is null");
            return (Criteria) this;
        }

        public Criteria andF27IsNotNull() {
            addCriterion("F27 is not null");
            return (Criteria) this;
        }

        public Criteria andF27EqualTo(String value) {
            addCriterion("F27 =", value, "f27");
            return (Criteria) this;
        }

        public Criteria andF27NotEqualTo(String value) {
            addCriterion("F27 <>", value, "f27");
            return (Criteria) this;
        }

        public Criteria andF27GreaterThan(String value) {
            addCriterion("F27 >", value, "f27");
            return (Criteria) this;
        }

        public Criteria andF27GreaterThanOrEqualTo(String value) {
            addCriterion("F27 >=", value, "f27");
            return (Criteria) this;
        }

        public Criteria andF27LessThan(String value) {
            addCriterion("F27 <", value, "f27");
            return (Criteria) this;
        }

        public Criteria andF27LessThanOrEqualTo(String value) {
            addCriterion("F27 <=", value, "f27");
            return (Criteria) this;
        }

        public Criteria andF27Like(String value) {
            addCriterion("F27 like", value, "f27");
            return (Criteria) this;
        }

        public Criteria andF27NotLike(String value) {
            addCriterion("F27 not like", value, "f27");
            return (Criteria) this;
        }

        public Criteria andF27In(List<String> values) {
            addCriterion("F27 in", values, "f27");
            return (Criteria) this;
        }

        public Criteria andF27NotIn(List<String> values) {
            addCriterion("F27 not in", values, "f27");
            return (Criteria) this;
        }

        public Criteria andF27Between(String value1, String value2) {
            addCriterion("F27 between", value1, value2, "f27");
            return (Criteria) this;
        }

        public Criteria andF27NotBetween(String value1, String value2) {
            addCriterion("F27 not between", value1, value2, "f27");
            return (Criteria) this;
        }

        public Criteria andF28IsNull() {
            addCriterion("F28 is null");
            return (Criteria) this;
        }

        public Criteria andF28IsNotNull() {
            addCriterion("F28 is not null");
            return (Criteria) this;
        }

        public Criteria andF28EqualTo(String value) {
            addCriterion("F28 =", value, "f28");
            return (Criteria) this;
        }

        public Criteria andF28NotEqualTo(String value) {
            addCriterion("F28 <>", value, "f28");
            return (Criteria) this;
        }

        public Criteria andF28GreaterThan(String value) {
            addCriterion("F28 >", value, "f28");
            return (Criteria) this;
        }

        public Criteria andF28GreaterThanOrEqualTo(String value) {
            addCriterion("F28 >=", value, "f28");
            return (Criteria) this;
        }

        public Criteria andF28LessThan(String value) {
            addCriterion("F28 <", value, "f28");
            return (Criteria) this;
        }

        public Criteria andF28LessThanOrEqualTo(String value) {
            addCriterion("F28 <=", value, "f28");
            return (Criteria) this;
        }

        public Criteria andF28Like(String value) {
            addCriterion("F28 like", value, "f28");
            return (Criteria) this;
        }

        public Criteria andF28NotLike(String value) {
            addCriterion("F28 not like", value, "f28");
            return (Criteria) this;
        }

        public Criteria andF28In(List<String> values) {
            addCriterion("F28 in", values, "f28");
            return (Criteria) this;
        }

        public Criteria andF28NotIn(List<String> values) {
            addCriterion("F28 not in", values, "f28");
            return (Criteria) this;
        }

        public Criteria andF28Between(String value1, String value2) {
            addCriterion("F28 between", value1, value2, "f28");
            return (Criteria) this;
        }

        public Criteria andF28NotBetween(String value1, String value2) {
            addCriterion("F28 not between", value1, value2, "f28");
            return (Criteria) this;
        }

        public Criteria andF29IsNull() {
            addCriterion("F29 is null");
            return (Criteria) this;
        }

        public Criteria andF29IsNotNull() {
            addCriterion("F29 is not null");
            return (Criteria) this;
        }

        public Criteria andF29EqualTo(String value) {
            addCriterion("F29 =", value, "f29");
            return (Criteria) this;
        }

        public Criteria andF29NotEqualTo(String value) {
            addCriterion("F29 <>", value, "f29");
            return (Criteria) this;
        }

        public Criteria andF29GreaterThan(String value) {
            addCriterion("F29 >", value, "f29");
            return (Criteria) this;
        }

        public Criteria andF29GreaterThanOrEqualTo(String value) {
            addCriterion("F29 >=", value, "f29");
            return (Criteria) this;
        }

        public Criteria andF29LessThan(String value) {
            addCriterion("F29 <", value, "f29");
            return (Criteria) this;
        }

        public Criteria andF29LessThanOrEqualTo(String value) {
            addCriterion("F29 <=", value, "f29");
            return (Criteria) this;
        }

        public Criteria andF29Like(String value) {
            addCriterion("F29 like", value, "f29");
            return (Criteria) this;
        }

        public Criteria andF29NotLike(String value) {
            addCriterion("F29 not like", value, "f29");
            return (Criteria) this;
        }

        public Criteria andF29In(List<String> values) {
            addCriterion("F29 in", values, "f29");
            return (Criteria) this;
        }

        public Criteria andF29NotIn(List<String> values) {
            addCriterion("F29 not in", values, "f29");
            return (Criteria) this;
        }

        public Criteria andF29Between(String value1, String value2) {
            addCriterion("F29 between", value1, value2, "f29");
            return (Criteria) this;
        }

        public Criteria andF29NotBetween(String value1, String value2) {
            addCriterion("F29 not between", value1, value2, "f29");
            return (Criteria) this;
        }

        public Criteria andF30IsNull() {
            addCriterion("F30 is null");
            return (Criteria) this;
        }

        public Criteria andF30IsNotNull() {
            addCriterion("F30 is not null");
            return (Criteria) this;
        }

        public Criteria andF30EqualTo(String value) {
            addCriterion("F30 =", value, "f30");
            return (Criteria) this;
        }

        public Criteria andF30NotEqualTo(String value) {
            addCriterion("F30 <>", value, "f30");
            return (Criteria) this;
        }

        public Criteria andF30GreaterThan(String value) {
            addCriterion("F30 >", value, "f30");
            return (Criteria) this;
        }

        public Criteria andF30GreaterThanOrEqualTo(String value) {
            addCriterion("F30 >=", value, "f30");
            return (Criteria) this;
        }

        public Criteria andF30LessThan(String value) {
            addCriterion("F30 <", value, "f30");
            return (Criteria) this;
        }

        public Criteria andF30LessThanOrEqualTo(String value) {
            addCriterion("F30 <=", value, "f30");
            return (Criteria) this;
        }

        public Criteria andF30Like(String value) {
            addCriterion("F30 like", value, "f30");
            return (Criteria) this;
        }

        public Criteria andF30NotLike(String value) {
            addCriterion("F30 not like", value, "f30");
            return (Criteria) this;
        }

        public Criteria andF30In(List<String> values) {
            addCriterion("F30 in", values, "f30");
            return (Criteria) this;
        }

        public Criteria andF30NotIn(List<String> values) {
            addCriterion("F30 not in", values, "f30");
            return (Criteria) this;
        }

        public Criteria andF30Between(String value1, String value2) {
            addCriterion("F30 between", value1, value2, "f30");
            return (Criteria) this;
        }

        public Criteria andF30NotBetween(String value1, String value2) {
            addCriterion("F30 not between", value1, value2, "f30");
            return (Criteria) this;
        }

        public Criteria andF31IsNull() {
            addCriterion("F31 is null");
            return (Criteria) this;
        }

        public Criteria andF31IsNotNull() {
            addCriterion("F31 is not null");
            return (Criteria) this;
        }

        public Criteria andF31EqualTo(String value) {
            addCriterion("F31 =", value, "f31");
            return (Criteria) this;
        }

        public Criteria andF31NotEqualTo(String value) {
            addCriterion("F31 <>", value, "f31");
            return (Criteria) this;
        }

        public Criteria andF31GreaterThan(String value) {
            addCriterion("F31 >", value, "f31");
            return (Criteria) this;
        }

        public Criteria andF31GreaterThanOrEqualTo(String value) {
            addCriterion("F31 >=", value, "f31");
            return (Criteria) this;
        }

        public Criteria andF31LessThan(String value) {
            addCriterion("F31 <", value, "f31");
            return (Criteria) this;
        }

        public Criteria andF31LessThanOrEqualTo(String value) {
            addCriterion("F31 <=", value, "f31");
            return (Criteria) this;
        }

        public Criteria andF31Like(String value) {
            addCriterion("F31 like", value, "f31");
            return (Criteria) this;
        }

        public Criteria andF31NotLike(String value) {
            addCriterion("F31 not like", value, "f31");
            return (Criteria) this;
        }

        public Criteria andF31In(List<String> values) {
            addCriterion("F31 in", values, "f31");
            return (Criteria) this;
        }

        public Criteria andF31NotIn(List<String> values) {
            addCriterion("F31 not in", values, "f31");
            return (Criteria) this;
        }

        public Criteria andF31Between(String value1, String value2) {
            addCriterion("F31 between", value1, value2, "f31");
            return (Criteria) this;
        }

        public Criteria andF31NotBetween(String value1, String value2) {
            addCriterion("F31 not between", value1, value2, "f31");
            return (Criteria) this;
        }

        public Criteria andF32IsNull() {
            addCriterion("F32 is null");
            return (Criteria) this;
        }

        public Criteria andF32IsNotNull() {
            addCriterion("F32 is not null");
            return (Criteria) this;
        }

        public Criteria andF32EqualTo(String value) {
            addCriterion("F32 =", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32NotEqualTo(String value) {
            addCriterion("F32 <>", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32GreaterThan(String value) {
            addCriterion("F32 >", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32GreaterThanOrEqualTo(String value) {
            addCriterion("F32 >=", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32LessThan(String value) {
            addCriterion("F32 <", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32LessThanOrEqualTo(String value) {
            addCriterion("F32 <=", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32Like(String value) {
            addCriterion("F32 like", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32NotLike(String value) {
            addCriterion("F32 not like", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32In(List<String> values) {
            addCriterion("F32 in", values, "f32");
            return (Criteria) this;
        }

        public Criteria andF32NotIn(List<String> values) {
            addCriterion("F32 not in", values, "f32");
            return (Criteria) this;
        }

        public Criteria andF32Between(String value1, String value2) {
            addCriterion("F32 between", value1, value2, "f32");
            return (Criteria) this;
        }

        public Criteria andF32NotBetween(String value1, String value2) {
            addCriterion("F32 not between", value1, value2, "f32");
            return (Criteria) this;
        }

        public Criteria andF33IsNull() {
            addCriterion("F33 is null");
            return (Criteria) this;
        }

        public Criteria andF33IsNotNull() {
            addCriterion("F33 is not null");
            return (Criteria) this;
        }

        public Criteria andF33EqualTo(String value) {
            addCriterion("F33 =", value, "f33");
            return (Criteria) this;
        }

        public Criteria andF33NotEqualTo(String value) {
            addCriterion("F33 <>", value, "f33");
            return (Criteria) this;
        }

        public Criteria andF33GreaterThan(String value) {
            addCriterion("F33 >", value, "f33");
            return (Criteria) this;
        }

        public Criteria andF33GreaterThanOrEqualTo(String value) {
            addCriterion("F33 >=", value, "f33");
            return (Criteria) this;
        }

        public Criteria andF33LessThan(String value) {
            addCriterion("F33 <", value, "f33");
            return (Criteria) this;
        }

        public Criteria andF33LessThanOrEqualTo(String value) {
            addCriterion("F33 <=", value, "f33");
            return (Criteria) this;
        }

        public Criteria andF33Like(String value) {
            addCriterion("F33 like", value, "f33");
            return (Criteria) this;
        }

        public Criteria andF33NotLike(String value) {
            addCriterion("F33 not like", value, "f33");
            return (Criteria) this;
        }

        public Criteria andF33In(List<String> values) {
            addCriterion("F33 in", values, "f33");
            return (Criteria) this;
        }

        public Criteria andF33NotIn(List<String> values) {
            addCriterion("F33 not in", values, "f33");
            return (Criteria) this;
        }

        public Criteria andF33Between(String value1, String value2) {
            addCriterion("F33 between", value1, value2, "f33");
            return (Criteria) this;
        }

        public Criteria andF33NotBetween(String value1, String value2) {
            addCriterion("F33 not between", value1, value2, "f33");
            return (Criteria) this;
        }

        public Criteria andF34IsNull() {
            addCriterion("F34 is null");
            return (Criteria) this;
        }

        public Criteria andF34IsNotNull() {
            addCriterion("F34 is not null");
            return (Criteria) this;
        }

        public Criteria andF34EqualTo(String value) {
            addCriterion("F34 =", value, "f34");
            return (Criteria) this;
        }

        public Criteria andF34NotEqualTo(String value) {
            addCriterion("F34 <>", value, "f34");
            return (Criteria) this;
        }

        public Criteria andF34GreaterThan(String value) {
            addCriterion("F34 >", value, "f34");
            return (Criteria) this;
        }

        public Criteria andF34GreaterThanOrEqualTo(String value) {
            addCriterion("F34 >=", value, "f34");
            return (Criteria) this;
        }

        public Criteria andF34LessThan(String value) {
            addCriterion("F34 <", value, "f34");
            return (Criteria) this;
        }

        public Criteria andF34LessThanOrEqualTo(String value) {
            addCriterion("F34 <=", value, "f34");
            return (Criteria) this;
        }

        public Criteria andF34Like(String value) {
            addCriterion("F34 like", value, "f34");
            return (Criteria) this;
        }

        public Criteria andF34NotLike(String value) {
            addCriterion("F34 not like", value, "f34");
            return (Criteria) this;
        }

        public Criteria andF34In(List<String> values) {
            addCriterion("F34 in", values, "f34");
            return (Criteria) this;
        }

        public Criteria andF34NotIn(List<String> values) {
            addCriterion("F34 not in", values, "f34");
            return (Criteria) this;
        }

        public Criteria andF34Between(String value1, String value2) {
            addCriterion("F34 between", value1, value2, "f34");
            return (Criteria) this;
        }

        public Criteria andF34NotBetween(String value1, String value2) {
            addCriterion("F34 not between", value1, value2, "f34");
            return (Criteria) this;
        }

        public Criteria andF35IsNull() {
            addCriterion("F35 is null");
            return (Criteria) this;
        }

        public Criteria andF35IsNotNull() {
            addCriterion("F35 is not null");
            return (Criteria) this;
        }

        public Criteria andF35EqualTo(String value) {
            addCriterion("F35 =", value, "f35");
            return (Criteria) this;
        }

        public Criteria andF35NotEqualTo(String value) {
            addCriterion("F35 <>", value, "f35");
            return (Criteria) this;
        }

        public Criteria andF35GreaterThan(String value) {
            addCriterion("F35 >", value, "f35");
            return (Criteria) this;
        }

        public Criteria andF35GreaterThanOrEqualTo(String value) {
            addCriterion("F35 >=", value, "f35");
            return (Criteria) this;
        }

        public Criteria andF35LessThan(String value) {
            addCriterion("F35 <", value, "f35");
            return (Criteria) this;
        }

        public Criteria andF35LessThanOrEqualTo(String value) {
            addCriterion("F35 <=", value, "f35");
            return (Criteria) this;
        }

        public Criteria andF35Like(String value) {
            addCriterion("F35 like", value, "f35");
            return (Criteria) this;
        }

        public Criteria andF35NotLike(String value) {
            addCriterion("F35 not like", value, "f35");
            return (Criteria) this;
        }

        public Criteria andF35In(List<String> values) {
            addCriterion("F35 in", values, "f35");
            return (Criteria) this;
        }

        public Criteria andF35NotIn(List<String> values) {
            addCriterion("F35 not in", values, "f35");
            return (Criteria) this;
        }

        public Criteria andF35Between(String value1, String value2) {
            addCriterion("F35 between", value1, value2, "f35");
            return (Criteria) this;
        }

        public Criteria andF35NotBetween(String value1, String value2) {
            addCriterion("F35 not between", value1, value2, "f35");
            return (Criteria) this;
        }

        public Criteria andF36IsNull() {
            addCriterion("F36 is null");
            return (Criteria) this;
        }

        public Criteria andF36IsNotNull() {
            addCriterion("F36 is not null");
            return (Criteria) this;
        }

        public Criteria andF36EqualTo(String value) {
            addCriterion("F36 =", value, "f36");
            return (Criteria) this;
        }

        public Criteria andF36NotEqualTo(String value) {
            addCriterion("F36 <>", value, "f36");
            return (Criteria) this;
        }

        public Criteria andF36GreaterThan(String value) {
            addCriterion("F36 >", value, "f36");
            return (Criteria) this;
        }

        public Criteria andF36GreaterThanOrEqualTo(String value) {
            addCriterion("F36 >=", value, "f36");
            return (Criteria) this;
        }

        public Criteria andF36LessThan(String value) {
            addCriterion("F36 <", value, "f36");
            return (Criteria) this;
        }

        public Criteria andF36LessThanOrEqualTo(String value) {
            addCriterion("F36 <=", value, "f36");
            return (Criteria) this;
        }

        public Criteria andF36Like(String value) {
            addCriterion("F36 like", value, "f36");
            return (Criteria) this;
        }

        public Criteria andF36NotLike(String value) {
            addCriterion("F36 not like", value, "f36");
            return (Criteria) this;
        }

        public Criteria andF36In(List<String> values) {
            addCriterion("F36 in", values, "f36");
            return (Criteria) this;
        }

        public Criteria andF36NotIn(List<String> values) {
            addCriterion("F36 not in", values, "f36");
            return (Criteria) this;
        }

        public Criteria andF36Between(String value1, String value2) {
            addCriterion("F36 between", value1, value2, "f36");
            return (Criteria) this;
        }

        public Criteria andF36NotBetween(String value1, String value2) {
            addCriterion("F36 not between", value1, value2, "f36");
            return (Criteria) this;
        }

        public Criteria andF37IsNull() {
            addCriterion("F37 is null");
            return (Criteria) this;
        }

        public Criteria andF37IsNotNull() {
            addCriterion("F37 is not null");
            return (Criteria) this;
        }

        public Criteria andF37EqualTo(String value) {
            addCriterion("F37 =", value, "f37");
            return (Criteria) this;
        }

        public Criteria andF37NotEqualTo(String value) {
            addCriterion("F37 <>", value, "f37");
            return (Criteria) this;
        }

        public Criteria andF37GreaterThan(String value) {
            addCriterion("F37 >", value, "f37");
            return (Criteria) this;
        }

        public Criteria andF37GreaterThanOrEqualTo(String value) {
            addCriterion("F37 >=", value, "f37");
            return (Criteria) this;
        }

        public Criteria andF37LessThan(String value) {
            addCriterion("F37 <", value, "f37");
            return (Criteria) this;
        }

        public Criteria andF37LessThanOrEqualTo(String value) {
            addCriterion("F37 <=", value, "f37");
            return (Criteria) this;
        }

        public Criteria andF37Like(String value) {
            addCriterion("F37 like", value, "f37");
            return (Criteria) this;
        }

        public Criteria andF37NotLike(String value) {
            addCriterion("F37 not like", value, "f37");
            return (Criteria) this;
        }

        public Criteria andF37In(List<String> values) {
            addCriterion("F37 in", values, "f37");
            return (Criteria) this;
        }

        public Criteria andF37NotIn(List<String> values) {
            addCriterion("F37 not in", values, "f37");
            return (Criteria) this;
        }

        public Criteria andF37Between(String value1, String value2) {
            addCriterion("F37 between", value1, value2, "f37");
            return (Criteria) this;
        }

        public Criteria andF37NotBetween(String value1, String value2) {
            addCriterion("F37 not between", value1, value2, "f37");
            return (Criteria) this;
        }

        public Criteria andF38IsNull() {
            addCriterion("F38 is null");
            return (Criteria) this;
        }

        public Criteria andF38IsNotNull() {
            addCriterion("F38 is not null");
            return (Criteria) this;
        }

        public Criteria andF38EqualTo(String value) {
            addCriterion("F38 =", value, "f38");
            return (Criteria) this;
        }

        public Criteria andF38NotEqualTo(String value) {
            addCriterion("F38 <>", value, "f38");
            return (Criteria) this;
        }

        public Criteria andF38GreaterThan(String value) {
            addCriterion("F38 >", value, "f38");
            return (Criteria) this;
        }

        public Criteria andF38GreaterThanOrEqualTo(String value) {
            addCriterion("F38 >=", value, "f38");
            return (Criteria) this;
        }

        public Criteria andF38LessThan(String value) {
            addCriterion("F38 <", value, "f38");
            return (Criteria) this;
        }

        public Criteria andF38LessThanOrEqualTo(String value) {
            addCriterion("F38 <=", value, "f38");
            return (Criteria) this;
        }

        public Criteria andF38Like(String value) {
            addCriterion("F38 like", value, "f38");
            return (Criteria) this;
        }

        public Criteria andF38NotLike(String value) {
            addCriterion("F38 not like", value, "f38");
            return (Criteria) this;
        }

        public Criteria andF38In(List<String> values) {
            addCriterion("F38 in", values, "f38");
            return (Criteria) this;
        }

        public Criteria andF38NotIn(List<String> values) {
            addCriterion("F38 not in", values, "f38");
            return (Criteria) this;
        }

        public Criteria andF38Between(String value1, String value2) {
            addCriterion("F38 between", value1, value2, "f38");
            return (Criteria) this;
        }

        public Criteria andF38NotBetween(String value1, String value2) {
            addCriterion("F38 not between", value1, value2, "f38");
            return (Criteria) this;
        }

        public Criteria andF39IsNull() {
            addCriterion("F39 is null");
            return (Criteria) this;
        }

        public Criteria andF39IsNotNull() {
            addCriterion("F39 is not null");
            return (Criteria) this;
        }

        public Criteria andF39EqualTo(String value) {
            addCriterion("F39 =", value, "f39");
            return (Criteria) this;
        }

        public Criteria andF39NotEqualTo(String value) {
            addCriterion("F39 <>", value, "f39");
            return (Criteria) this;
        }

        public Criteria andF39GreaterThan(String value) {
            addCriterion("F39 >", value, "f39");
            return (Criteria) this;
        }

        public Criteria andF39GreaterThanOrEqualTo(String value) {
            addCriterion("F39 >=", value, "f39");
            return (Criteria) this;
        }

        public Criteria andF39LessThan(String value) {
            addCriterion("F39 <", value, "f39");
            return (Criteria) this;
        }

        public Criteria andF39LessThanOrEqualTo(String value) {
            addCriterion("F39 <=", value, "f39");
            return (Criteria) this;
        }

        public Criteria andF39Like(String value) {
            addCriterion("F39 like", value, "f39");
            return (Criteria) this;
        }

        public Criteria andF39NotLike(String value) {
            addCriterion("F39 not like", value, "f39");
            return (Criteria) this;
        }

        public Criteria andF39In(List<String> values) {
            addCriterion("F39 in", values, "f39");
            return (Criteria) this;
        }

        public Criteria andF39NotIn(List<String> values) {
            addCriterion("F39 not in", values, "f39");
            return (Criteria) this;
        }

        public Criteria andF39Between(String value1, String value2) {
            addCriterion("F39 between", value1, value2, "f39");
            return (Criteria) this;
        }

        public Criteria andF39NotBetween(String value1, String value2) {
            addCriterion("F39 not between", value1, value2, "f39");
            return (Criteria) this;
        }

        public Criteria andF40IsNull() {
            addCriterion("F40 is null");
            return (Criteria) this;
        }

        public Criteria andF40IsNotNull() {
            addCriterion("F40 is not null");
            return (Criteria) this;
        }

        public Criteria andF40EqualTo(String value) {
            addCriterion("F40 =", value, "f40");
            return (Criteria) this;
        }

        public Criteria andF40NotEqualTo(String value) {
            addCriterion("F40 <>", value, "f40");
            return (Criteria) this;
        }

        public Criteria andF40GreaterThan(String value) {
            addCriterion("F40 >", value, "f40");
            return (Criteria) this;
        }

        public Criteria andF40GreaterThanOrEqualTo(String value) {
            addCriterion("F40 >=", value, "f40");
            return (Criteria) this;
        }

        public Criteria andF40LessThan(String value) {
            addCriterion("F40 <", value, "f40");
            return (Criteria) this;
        }

        public Criteria andF40LessThanOrEqualTo(String value) {
            addCriterion("F40 <=", value, "f40");
            return (Criteria) this;
        }

        public Criteria andF40Like(String value) {
            addCriterion("F40 like", value, "f40");
            return (Criteria) this;
        }

        public Criteria andF40NotLike(String value) {
            addCriterion("F40 not like", value, "f40");
            return (Criteria) this;
        }

        public Criteria andF40In(List<String> values) {
            addCriterion("F40 in", values, "f40");
            return (Criteria) this;
        }

        public Criteria andF40NotIn(List<String> values) {
            addCriterion("F40 not in", values, "f40");
            return (Criteria) this;
        }

        public Criteria andF40Between(String value1, String value2) {
            addCriterion("F40 between", value1, value2, "f40");
            return (Criteria) this;
        }

        public Criteria andF40NotBetween(String value1, String value2) {
            addCriterion("F40 not between", value1, value2, "f40");
            return (Criteria) this;
        }

        public Criteria andF41IsNull() {
            addCriterion("F41 is null");
            return (Criteria) this;
        }

        public Criteria andF41IsNotNull() {
            addCriterion("F41 is not null");
            return (Criteria) this;
        }

        public Criteria andF41EqualTo(String value) {
            addCriterion("F41 =", value, "f41");
            return (Criteria) this;
        }

        public Criteria andF41NotEqualTo(String value) {
            addCriterion("F41 <>", value, "f41");
            return (Criteria) this;
        }

        public Criteria andF41GreaterThan(String value) {
            addCriterion("F41 >", value, "f41");
            return (Criteria) this;
        }

        public Criteria andF41GreaterThanOrEqualTo(String value) {
            addCriterion("F41 >=", value, "f41");
            return (Criteria) this;
        }

        public Criteria andF41LessThan(String value) {
            addCriterion("F41 <", value, "f41");
            return (Criteria) this;
        }

        public Criteria andF41LessThanOrEqualTo(String value) {
            addCriterion("F41 <=", value, "f41");
            return (Criteria) this;
        }

        public Criteria andF41Like(String value) {
            addCriterion("F41 like", value, "f41");
            return (Criteria) this;
        }

        public Criteria andF41NotLike(String value) {
            addCriterion("F41 not like", value, "f41");
            return (Criteria) this;
        }

        public Criteria andF41In(List<String> values) {
            addCriterion("F41 in", values, "f41");
            return (Criteria) this;
        }

        public Criteria andF41NotIn(List<String> values) {
            addCriterion("F41 not in", values, "f41");
            return (Criteria) this;
        }

        public Criteria andF41Between(String value1, String value2) {
            addCriterion("F41 between", value1, value2, "f41");
            return (Criteria) this;
        }

        public Criteria andF41NotBetween(String value1, String value2) {
            addCriterion("F41 not between", value1, value2, "f41");
            return (Criteria) this;
        }

        public Criteria andF42IsNull() {
            addCriterion("F42 is null");
            return (Criteria) this;
        }

        public Criteria andF42IsNotNull() {
            addCriterion("F42 is not null");
            return (Criteria) this;
        }

        public Criteria andF42EqualTo(String value) {
            addCriterion("F42 =", value, "f42");
            return (Criteria) this;
        }

        public Criteria andF42NotEqualTo(String value) {
            addCriterion("F42 <>", value, "f42");
            return (Criteria) this;
        }

        public Criteria andF42GreaterThan(String value) {
            addCriterion("F42 >", value, "f42");
            return (Criteria) this;
        }

        public Criteria andF42GreaterThanOrEqualTo(String value) {
            addCriterion("F42 >=", value, "f42");
            return (Criteria) this;
        }

        public Criteria andF42LessThan(String value) {
            addCriterion("F42 <", value, "f42");
            return (Criteria) this;
        }

        public Criteria andF42LessThanOrEqualTo(String value) {
            addCriterion("F42 <=", value, "f42");
            return (Criteria) this;
        }

        public Criteria andF42Like(String value) {
            addCriterion("F42 like", value, "f42");
            return (Criteria) this;
        }

        public Criteria andF42NotLike(String value) {
            addCriterion("F42 not like", value, "f42");
            return (Criteria) this;
        }

        public Criteria andF42In(List<String> values) {
            addCriterion("F42 in", values, "f42");
            return (Criteria) this;
        }

        public Criteria andF42NotIn(List<String> values) {
            addCriterion("F42 not in", values, "f42");
            return (Criteria) this;
        }

        public Criteria andF42Between(String value1, String value2) {
            addCriterion("F42 between", value1, value2, "f42");
            return (Criteria) this;
        }

        public Criteria andF42NotBetween(String value1, String value2) {
            addCriterion("F42 not between", value1, value2, "f42");
            return (Criteria) this;
        }

        public Criteria andF43IsNull() {
            addCriterion("F43 is null");
            return (Criteria) this;
        }

        public Criteria andF43IsNotNull() {
            addCriterion("F43 is not null");
            return (Criteria) this;
        }

        public Criteria andF43EqualTo(String value) {
            addCriterion("F43 =", value, "f43");
            return (Criteria) this;
        }

        public Criteria andF43NotEqualTo(String value) {
            addCriterion("F43 <>", value, "f43");
            return (Criteria) this;
        }

        public Criteria andF43GreaterThan(String value) {
            addCriterion("F43 >", value, "f43");
            return (Criteria) this;
        }

        public Criteria andF43GreaterThanOrEqualTo(String value) {
            addCriterion("F43 >=", value, "f43");
            return (Criteria) this;
        }

        public Criteria andF43LessThan(String value) {
            addCriterion("F43 <", value, "f43");
            return (Criteria) this;
        }

        public Criteria andF43LessThanOrEqualTo(String value) {
            addCriterion("F43 <=", value, "f43");
            return (Criteria) this;
        }

        public Criteria andF43Like(String value) {
            addCriterion("F43 like", value, "f43");
            return (Criteria) this;
        }

        public Criteria andF43NotLike(String value) {
            addCriterion("F43 not like", value, "f43");
            return (Criteria) this;
        }

        public Criteria andF43In(List<String> values) {
            addCriterion("F43 in", values, "f43");
            return (Criteria) this;
        }

        public Criteria andF43NotIn(List<String> values) {
            addCriterion("F43 not in", values, "f43");
            return (Criteria) this;
        }

        public Criteria andF43Between(String value1, String value2) {
            addCriterion("F43 between", value1, value2, "f43");
            return (Criteria) this;
        }

        public Criteria andF43NotBetween(String value1, String value2) {
            addCriterion("F43 not between", value1, value2, "f43");
            return (Criteria) this;
        }

        public Criteria andF44IsNull() {
            addCriterion("F44 is null");
            return (Criteria) this;
        }

        public Criteria andF44IsNotNull() {
            addCriterion("F44 is not null");
            return (Criteria) this;
        }

        public Criteria andF44EqualTo(String value) {
            addCriterion("F44 =", value, "f44");
            return (Criteria) this;
        }

        public Criteria andF44NotEqualTo(String value) {
            addCriterion("F44 <>", value, "f44");
            return (Criteria) this;
        }

        public Criteria andF44GreaterThan(String value) {
            addCriterion("F44 >", value, "f44");
            return (Criteria) this;
        }

        public Criteria andF44GreaterThanOrEqualTo(String value) {
            addCriterion("F44 >=", value, "f44");
            return (Criteria) this;
        }

        public Criteria andF44LessThan(String value) {
            addCriterion("F44 <", value, "f44");
            return (Criteria) this;
        }

        public Criteria andF44LessThanOrEqualTo(String value) {
            addCriterion("F44 <=", value, "f44");
            return (Criteria) this;
        }

        public Criteria andF44Like(String value) {
            addCriterion("F44 like", value, "f44");
            return (Criteria) this;
        }

        public Criteria andF44NotLike(String value) {
            addCriterion("F44 not like", value, "f44");
            return (Criteria) this;
        }

        public Criteria andF44In(List<String> values) {
            addCriterion("F44 in", values, "f44");
            return (Criteria) this;
        }

        public Criteria andF44NotIn(List<String> values) {
            addCriterion("F44 not in", values, "f44");
            return (Criteria) this;
        }

        public Criteria andF44Between(String value1, String value2) {
            addCriterion("F44 between", value1, value2, "f44");
            return (Criteria) this;
        }

        public Criteria andF44NotBetween(String value1, String value2) {
            addCriterion("F44 not between", value1, value2, "f44");
            return (Criteria) this;
        }

        public Criteria andF45IsNull() {
            addCriterion("F45 is null");
            return (Criteria) this;
        }

        public Criteria andF45IsNotNull() {
            addCriterion("F45 is not null");
            return (Criteria) this;
        }

        public Criteria andF45EqualTo(String value) {
            addCriterion("F45 =", value, "f45");
            return (Criteria) this;
        }

        public Criteria andF45NotEqualTo(String value) {
            addCriterion("F45 <>", value, "f45");
            return (Criteria) this;
        }

        public Criteria andF45GreaterThan(String value) {
            addCriterion("F45 >", value, "f45");
            return (Criteria) this;
        }

        public Criteria andF45GreaterThanOrEqualTo(String value) {
            addCriterion("F45 >=", value, "f45");
            return (Criteria) this;
        }

        public Criteria andF45LessThan(String value) {
            addCriterion("F45 <", value, "f45");
            return (Criteria) this;
        }

        public Criteria andF45LessThanOrEqualTo(String value) {
            addCriterion("F45 <=", value, "f45");
            return (Criteria) this;
        }

        public Criteria andF45Like(String value) {
            addCriterion("F45 like", value, "f45");
            return (Criteria) this;
        }

        public Criteria andF45NotLike(String value) {
            addCriterion("F45 not like", value, "f45");
            return (Criteria) this;
        }

        public Criteria andF45In(List<String> values) {
            addCriterion("F45 in", values, "f45");
            return (Criteria) this;
        }

        public Criteria andF45NotIn(List<String> values) {
            addCriterion("F45 not in", values, "f45");
            return (Criteria) this;
        }

        public Criteria andF45Between(String value1, String value2) {
            addCriterion("F45 between", value1, value2, "f45");
            return (Criteria) this;
        }

        public Criteria andF45NotBetween(String value1, String value2) {
            addCriterion("F45 not between", value1, value2, "f45");
            return (Criteria) this;
        }

        public Criteria andF46IsNull() {
            addCriterion("F46 is null");
            return (Criteria) this;
        }

        public Criteria andF46IsNotNull() {
            addCriterion("F46 is not null");
            return (Criteria) this;
        }

        public Criteria andF46EqualTo(String value) {
            addCriterion("F46 =", value, "f46");
            return (Criteria) this;
        }

        public Criteria andF46NotEqualTo(String value) {
            addCriterion("F46 <>", value, "f46");
            return (Criteria) this;
        }

        public Criteria andF46GreaterThan(String value) {
            addCriterion("F46 >", value, "f46");
            return (Criteria) this;
        }

        public Criteria andF46GreaterThanOrEqualTo(String value) {
            addCriterion("F46 >=", value, "f46");
            return (Criteria) this;
        }

        public Criteria andF46LessThan(String value) {
            addCriterion("F46 <", value, "f46");
            return (Criteria) this;
        }

        public Criteria andF46LessThanOrEqualTo(String value) {
            addCriterion("F46 <=", value, "f46");
            return (Criteria) this;
        }

        public Criteria andF46Like(String value) {
            addCriterion("F46 like", value, "f46");
            return (Criteria) this;
        }

        public Criteria andF46NotLike(String value) {
            addCriterion("F46 not like", value, "f46");
            return (Criteria) this;
        }

        public Criteria andF46In(List<String> values) {
            addCriterion("F46 in", values, "f46");
            return (Criteria) this;
        }

        public Criteria andF46NotIn(List<String> values) {
            addCriterion("F46 not in", values, "f46");
            return (Criteria) this;
        }

        public Criteria andF46Between(String value1, String value2) {
            addCriterion("F46 between", value1, value2, "f46");
            return (Criteria) this;
        }

        public Criteria andF46NotBetween(String value1, String value2) {
            addCriterion("F46 not between", value1, value2, "f46");
            return (Criteria) this;
        }

        public Criteria andF47IsNull() {
            addCriterion("F47 is null");
            return (Criteria) this;
        }

        public Criteria andF47IsNotNull() {
            addCriterion("F47 is not null");
            return (Criteria) this;
        }

        public Criteria andF47EqualTo(String value) {
            addCriterion("F47 =", value, "f47");
            return (Criteria) this;
        }

        public Criteria andF47NotEqualTo(String value) {
            addCriterion("F47 <>", value, "f47");
            return (Criteria) this;
        }

        public Criteria andF47GreaterThan(String value) {
            addCriterion("F47 >", value, "f47");
            return (Criteria) this;
        }

        public Criteria andF47GreaterThanOrEqualTo(String value) {
            addCriterion("F47 >=", value, "f47");
            return (Criteria) this;
        }

        public Criteria andF47LessThan(String value) {
            addCriterion("F47 <", value, "f47");
            return (Criteria) this;
        }

        public Criteria andF47LessThanOrEqualTo(String value) {
            addCriterion("F47 <=", value, "f47");
            return (Criteria) this;
        }

        public Criteria andF47Like(String value) {
            addCriterion("F47 like", value, "f47");
            return (Criteria) this;
        }

        public Criteria andF47NotLike(String value) {
            addCriterion("F47 not like", value, "f47");
            return (Criteria) this;
        }

        public Criteria andF47In(List<String> values) {
            addCriterion("F47 in", values, "f47");
            return (Criteria) this;
        }

        public Criteria andF47NotIn(List<String> values) {
            addCriterion("F47 not in", values, "f47");
            return (Criteria) this;
        }

        public Criteria andF47Between(String value1, String value2) {
            addCriterion("F47 between", value1, value2, "f47");
            return (Criteria) this;
        }

        public Criteria andF47NotBetween(String value1, String value2) {
            addCriterion("F47 not between", value1, value2, "f47");
            return (Criteria) this;
        }

        public Criteria andF48IsNull() {
            addCriterion("F48 is null");
            return (Criteria) this;
        }

        public Criteria andF48IsNotNull() {
            addCriterion("F48 is not null");
            return (Criteria) this;
        }

        public Criteria andF48EqualTo(String value) {
            addCriterion("F48 =", value, "f48");
            return (Criteria) this;
        }

        public Criteria andF48NotEqualTo(String value) {
            addCriterion("F48 <>", value, "f48");
            return (Criteria) this;
        }

        public Criteria andF48GreaterThan(String value) {
            addCriterion("F48 >", value, "f48");
            return (Criteria) this;
        }

        public Criteria andF48GreaterThanOrEqualTo(String value) {
            addCriterion("F48 >=", value, "f48");
            return (Criteria) this;
        }

        public Criteria andF48LessThan(String value) {
            addCriterion("F48 <", value, "f48");
            return (Criteria) this;
        }

        public Criteria andF48LessThanOrEqualTo(String value) {
            addCriterion("F48 <=", value, "f48");
            return (Criteria) this;
        }

        public Criteria andF48Like(String value) {
            addCriterion("F48 like", value, "f48");
            return (Criteria) this;
        }

        public Criteria andF48NotLike(String value) {
            addCriterion("F48 not like", value, "f48");
            return (Criteria) this;
        }

        public Criteria andF48In(List<String> values) {
            addCriterion("F48 in", values, "f48");
            return (Criteria) this;
        }

        public Criteria andF48NotIn(List<String> values) {
            addCriterion("F48 not in", values, "f48");
            return (Criteria) this;
        }

        public Criteria andF48Between(String value1, String value2) {
            addCriterion("F48 between", value1, value2, "f48");
            return (Criteria) this;
        }

        public Criteria andF48NotBetween(String value1, String value2) {
            addCriterion("F48 not between", value1, value2, "f48");
            return (Criteria) this;
        }

        public Criteria andF49IsNull() {
            addCriterion("F49 is null");
            return (Criteria) this;
        }

        public Criteria andF49IsNotNull() {
            addCriterion("F49 is not null");
            return (Criteria) this;
        }

        public Criteria andF49EqualTo(String value) {
            addCriterion("F49 =", value, "f49");
            return (Criteria) this;
        }

        public Criteria andF49NotEqualTo(String value) {
            addCriterion("F49 <>", value, "f49");
            return (Criteria) this;
        }

        public Criteria andF49GreaterThan(String value) {
            addCriterion("F49 >", value, "f49");
            return (Criteria) this;
        }

        public Criteria andF49GreaterThanOrEqualTo(String value) {
            addCriterion("F49 >=", value, "f49");
            return (Criteria) this;
        }

        public Criteria andF49LessThan(String value) {
            addCriterion("F49 <", value, "f49");
            return (Criteria) this;
        }

        public Criteria andF49LessThanOrEqualTo(String value) {
            addCriterion("F49 <=", value, "f49");
            return (Criteria) this;
        }

        public Criteria andF49Like(String value) {
            addCriterion("F49 like", value, "f49");
            return (Criteria) this;
        }

        public Criteria andF49NotLike(String value) {
            addCriterion("F49 not like", value, "f49");
            return (Criteria) this;
        }

        public Criteria andF49In(List<String> values) {
            addCriterion("F49 in", values, "f49");
            return (Criteria) this;
        }

        public Criteria andF49NotIn(List<String> values) {
            addCriterion("F49 not in", values, "f49");
            return (Criteria) this;
        }

        public Criteria andF49Between(String value1, String value2) {
            addCriterion("F49 between", value1, value2, "f49");
            return (Criteria) this;
        }

        public Criteria andF49NotBetween(String value1, String value2) {
            addCriterion("F49 not between", value1, value2, "f49");
            return (Criteria) this;
        }

        public Criteria andF50IsNull() {
            addCriterion("F50 is null");
            return (Criteria) this;
        }

        public Criteria andF50IsNotNull() {
            addCriterion("F50 is not null");
            return (Criteria) this;
        }

        public Criteria andF50EqualTo(String value) {
            addCriterion("F50 =", value, "f50");
            return (Criteria) this;
        }

        public Criteria andF50NotEqualTo(String value) {
            addCriterion("F50 <>", value, "f50");
            return (Criteria) this;
        }

        public Criteria andF50GreaterThan(String value) {
            addCriterion("F50 >", value, "f50");
            return (Criteria) this;
        }

        public Criteria andF50GreaterThanOrEqualTo(String value) {
            addCriterion("F50 >=", value, "f50");
            return (Criteria) this;
        }

        public Criteria andF50LessThan(String value) {
            addCriterion("F50 <", value, "f50");
            return (Criteria) this;
        }

        public Criteria andF50LessThanOrEqualTo(String value) {
            addCriterion("F50 <=", value, "f50");
            return (Criteria) this;
        }

        public Criteria andF50Like(String value) {
            addCriterion("F50 like", value, "f50");
            return (Criteria) this;
        }

        public Criteria andF50NotLike(String value) {
            addCriterion("F50 not like", value, "f50");
            return (Criteria) this;
        }

        public Criteria andF50In(List<String> values) {
            addCriterion("F50 in", values, "f50");
            return (Criteria) this;
        }

        public Criteria andF50NotIn(List<String> values) {
            addCriterion("F50 not in", values, "f50");
            return (Criteria) this;
        }

        public Criteria andF50Between(String value1, String value2) {
            addCriterion("F50 between", value1, value2, "f50");
            return (Criteria) this;
        }

        public Criteria andF50NotBetween(String value1, String value2) {
            addCriterion("F50 not between", value1, value2, "f50");
            return (Criteria) this;
        }

        public Criteria andF51IsNull() {
            addCriterion("F51 is null");
            return (Criteria) this;
        }

        public Criteria andF51IsNotNull() {
            addCriterion("F51 is not null");
            return (Criteria) this;
        }

        public Criteria andF51EqualTo(String value) {
            addCriterion("F51 =", value, "f51");
            return (Criteria) this;
        }

        public Criteria andF51NotEqualTo(String value) {
            addCriterion("F51 <>", value, "f51");
            return (Criteria) this;
        }

        public Criteria andF51GreaterThan(String value) {
            addCriterion("F51 >", value, "f51");
            return (Criteria) this;
        }

        public Criteria andF51GreaterThanOrEqualTo(String value) {
            addCriterion("F51 >=", value, "f51");
            return (Criteria) this;
        }

        public Criteria andF51LessThan(String value) {
            addCriterion("F51 <", value, "f51");
            return (Criteria) this;
        }

        public Criteria andF51LessThanOrEqualTo(String value) {
            addCriterion("F51 <=", value, "f51");
            return (Criteria) this;
        }

        public Criteria andF51Like(String value) {
            addCriterion("F51 like", value, "f51");
            return (Criteria) this;
        }

        public Criteria andF51NotLike(String value) {
            addCriterion("F51 not like", value, "f51");
            return (Criteria) this;
        }

        public Criteria andF51In(List<String> values) {
            addCriterion("F51 in", values, "f51");
            return (Criteria) this;
        }

        public Criteria andF51NotIn(List<String> values) {
            addCriterion("F51 not in", values, "f51");
            return (Criteria) this;
        }

        public Criteria andF51Between(String value1, String value2) {
            addCriterion("F51 between", value1, value2, "f51");
            return (Criteria) this;
        }

        public Criteria andF51NotBetween(String value1, String value2) {
            addCriterion("F51 not between", value1, value2, "f51");
            return (Criteria) this;
        }

        public Criteria andF52IsNull() {
            addCriterion("F52 is null");
            return (Criteria) this;
        }

        public Criteria andF52IsNotNull() {
            addCriterion("F52 is not null");
            return (Criteria) this;
        }

        public Criteria andF52EqualTo(String value) {
            addCriterion("F52 =", value, "f52");
            return (Criteria) this;
        }

        public Criteria andF52NotEqualTo(String value) {
            addCriterion("F52 <>", value, "f52");
            return (Criteria) this;
        }

        public Criteria andF52GreaterThan(String value) {
            addCriterion("F52 >", value, "f52");
            return (Criteria) this;
        }

        public Criteria andF52GreaterThanOrEqualTo(String value) {
            addCriterion("F52 >=", value, "f52");
            return (Criteria) this;
        }

        public Criteria andF52LessThan(String value) {
            addCriterion("F52 <", value, "f52");
            return (Criteria) this;
        }

        public Criteria andF52LessThanOrEqualTo(String value) {
            addCriterion("F52 <=", value, "f52");
            return (Criteria) this;
        }

        public Criteria andF52Like(String value) {
            addCriterion("F52 like", value, "f52");
            return (Criteria) this;
        }

        public Criteria andF52NotLike(String value) {
            addCriterion("F52 not like", value, "f52");
            return (Criteria) this;
        }

        public Criteria andF52In(List<String> values) {
            addCriterion("F52 in", values, "f52");
            return (Criteria) this;
        }

        public Criteria andF52NotIn(List<String> values) {
            addCriterion("F52 not in", values, "f52");
            return (Criteria) this;
        }

        public Criteria andF52Between(String value1, String value2) {
            addCriterion("F52 between", value1, value2, "f52");
            return (Criteria) this;
        }

        public Criteria andF52NotBetween(String value1, String value2) {
            addCriterion("F52 not between", value1, value2, "f52");
            return (Criteria) this;
        }

        public Criteria andF53IsNull() {
            addCriterion("F53 is null");
            return (Criteria) this;
        }

        public Criteria andF53IsNotNull() {
            addCriterion("F53 is not null");
            return (Criteria) this;
        }

        public Criteria andF53EqualTo(String value) {
            addCriterion("F53 =", value, "f53");
            return (Criteria) this;
        }

        public Criteria andF53NotEqualTo(String value) {
            addCriterion("F53 <>", value, "f53");
            return (Criteria) this;
        }

        public Criteria andF53GreaterThan(String value) {
            addCriterion("F53 >", value, "f53");
            return (Criteria) this;
        }

        public Criteria andF53GreaterThanOrEqualTo(String value) {
            addCriterion("F53 >=", value, "f53");
            return (Criteria) this;
        }

        public Criteria andF53LessThan(String value) {
            addCriterion("F53 <", value, "f53");
            return (Criteria) this;
        }

        public Criteria andF53LessThanOrEqualTo(String value) {
            addCriterion("F53 <=", value, "f53");
            return (Criteria) this;
        }

        public Criteria andF53Like(String value) {
            addCriterion("F53 like", value, "f53");
            return (Criteria) this;
        }

        public Criteria andF53NotLike(String value) {
            addCriterion("F53 not like", value, "f53");
            return (Criteria) this;
        }

        public Criteria andF53In(List<String> values) {
            addCriterion("F53 in", values, "f53");
            return (Criteria) this;
        }

        public Criteria andF53NotIn(List<String> values) {
            addCriterion("F53 not in", values, "f53");
            return (Criteria) this;
        }

        public Criteria andF53Between(String value1, String value2) {
            addCriterion("F53 between", value1, value2, "f53");
            return (Criteria) this;
        }

        public Criteria andF53NotBetween(String value1, String value2) {
            addCriterion("F53 not between", value1, value2, "f53");
            return (Criteria) this;
        }

        public Criteria andF54IsNull() {
            addCriterion("F54 is null");
            return (Criteria) this;
        }

        public Criteria andF54IsNotNull() {
            addCriterion("F54 is not null");
            return (Criteria) this;
        }

        public Criteria andF54EqualTo(String value) {
            addCriterion("F54 =", value, "f54");
            return (Criteria) this;
        }

        public Criteria andF54NotEqualTo(String value) {
            addCriterion("F54 <>", value, "f54");
            return (Criteria) this;
        }

        public Criteria andF54GreaterThan(String value) {
            addCriterion("F54 >", value, "f54");
            return (Criteria) this;
        }

        public Criteria andF54GreaterThanOrEqualTo(String value) {
            addCriterion("F54 >=", value, "f54");
            return (Criteria) this;
        }

        public Criteria andF54LessThan(String value) {
            addCriterion("F54 <", value, "f54");
            return (Criteria) this;
        }

        public Criteria andF54LessThanOrEqualTo(String value) {
            addCriterion("F54 <=", value, "f54");
            return (Criteria) this;
        }

        public Criteria andF54Like(String value) {
            addCriterion("F54 like", value, "f54");
            return (Criteria) this;
        }

        public Criteria andF54NotLike(String value) {
            addCriterion("F54 not like", value, "f54");
            return (Criteria) this;
        }

        public Criteria andF54In(List<String> values) {
            addCriterion("F54 in", values, "f54");
            return (Criteria) this;
        }

        public Criteria andF54NotIn(List<String> values) {
            addCriterion("F54 not in", values, "f54");
            return (Criteria) this;
        }

        public Criteria andF54Between(String value1, String value2) {
            addCriterion("F54 between", value1, value2, "f54");
            return (Criteria) this;
        }

        public Criteria andF54NotBetween(String value1, String value2) {
            addCriterion("F54 not between", value1, value2, "f54");
            return (Criteria) this;
        }

        public Criteria andF55IsNull() {
            addCriterion("F55 is null");
            return (Criteria) this;
        }

        public Criteria andF55IsNotNull() {
            addCriterion("F55 is not null");
            return (Criteria) this;
        }

        public Criteria andF55EqualTo(String value) {
            addCriterion("F55 =", value, "f55");
            return (Criteria) this;
        }

        public Criteria andF55NotEqualTo(String value) {
            addCriterion("F55 <>", value, "f55");
            return (Criteria) this;
        }

        public Criteria andF55GreaterThan(String value) {
            addCriterion("F55 >", value, "f55");
            return (Criteria) this;
        }

        public Criteria andF55GreaterThanOrEqualTo(String value) {
            addCriterion("F55 >=", value, "f55");
            return (Criteria) this;
        }

        public Criteria andF55LessThan(String value) {
            addCriterion("F55 <", value, "f55");
            return (Criteria) this;
        }

        public Criteria andF55LessThanOrEqualTo(String value) {
            addCriterion("F55 <=", value, "f55");
            return (Criteria) this;
        }

        public Criteria andF55Like(String value) {
            addCriterion("F55 like", value, "f55");
            return (Criteria) this;
        }

        public Criteria andF55NotLike(String value) {
            addCriterion("F55 not like", value, "f55");
            return (Criteria) this;
        }

        public Criteria andF55In(List<String> values) {
            addCriterion("F55 in", values, "f55");
            return (Criteria) this;
        }

        public Criteria andF55NotIn(List<String> values) {
            addCriterion("F55 not in", values, "f55");
            return (Criteria) this;
        }

        public Criteria andF55Between(String value1, String value2) {
            addCriterion("F55 between", value1, value2, "f55");
            return (Criteria) this;
        }

        public Criteria andF55NotBetween(String value1, String value2) {
            addCriterion("F55 not between", value1, value2, "f55");
            return (Criteria) this;
        }

        public Criteria andF56IsNull() {
            addCriterion("F56 is null");
            return (Criteria) this;
        }

        public Criteria andF56IsNotNull() {
            addCriterion("F56 is not null");
            return (Criteria) this;
        }

        public Criteria andF56EqualTo(String value) {
            addCriterion("F56 =", value, "f56");
            return (Criteria) this;
        }

        public Criteria andF56NotEqualTo(String value) {
            addCriterion("F56 <>", value, "f56");
            return (Criteria) this;
        }

        public Criteria andF56GreaterThan(String value) {
            addCriterion("F56 >", value, "f56");
            return (Criteria) this;
        }

        public Criteria andF56GreaterThanOrEqualTo(String value) {
            addCriterion("F56 >=", value, "f56");
            return (Criteria) this;
        }

        public Criteria andF56LessThan(String value) {
            addCriterion("F56 <", value, "f56");
            return (Criteria) this;
        }

        public Criteria andF56LessThanOrEqualTo(String value) {
            addCriterion("F56 <=", value, "f56");
            return (Criteria) this;
        }

        public Criteria andF56Like(String value) {
            addCriterion("F56 like", value, "f56");
            return (Criteria) this;
        }

        public Criteria andF56NotLike(String value) {
            addCriterion("F56 not like", value, "f56");
            return (Criteria) this;
        }

        public Criteria andF56In(List<String> values) {
            addCriterion("F56 in", values, "f56");
            return (Criteria) this;
        }

        public Criteria andF56NotIn(List<String> values) {
            addCriterion("F56 not in", values, "f56");
            return (Criteria) this;
        }

        public Criteria andF56Between(String value1, String value2) {
            addCriterion("F56 between", value1, value2, "f56");
            return (Criteria) this;
        }

        public Criteria andF56NotBetween(String value1, String value2) {
            addCriterion("F56 not between", value1, value2, "f56");
            return (Criteria) this;
        }

        public Criteria andF57IsNull() {
            addCriterion("F57 is null");
            return (Criteria) this;
        }

        public Criteria andF57IsNotNull() {
            addCriterion("F57 is not null");
            return (Criteria) this;
        }

        public Criteria andF57EqualTo(String value) {
            addCriterion("F57 =", value, "f57");
            return (Criteria) this;
        }

        public Criteria andF57NotEqualTo(String value) {
            addCriterion("F57 <>", value, "f57");
            return (Criteria) this;
        }

        public Criteria andF57GreaterThan(String value) {
            addCriterion("F57 >", value, "f57");
            return (Criteria) this;
        }

        public Criteria andF57GreaterThanOrEqualTo(String value) {
            addCriterion("F57 >=", value, "f57");
            return (Criteria) this;
        }

        public Criteria andF57LessThan(String value) {
            addCriterion("F57 <", value, "f57");
            return (Criteria) this;
        }

        public Criteria andF57LessThanOrEqualTo(String value) {
            addCriterion("F57 <=", value, "f57");
            return (Criteria) this;
        }

        public Criteria andF57Like(String value) {
            addCriterion("F57 like", value, "f57");
            return (Criteria) this;
        }

        public Criteria andF57NotLike(String value) {
            addCriterion("F57 not like", value, "f57");
            return (Criteria) this;
        }

        public Criteria andF57In(List<String> values) {
            addCriterion("F57 in", values, "f57");
            return (Criteria) this;
        }

        public Criteria andF57NotIn(List<String> values) {
            addCriterion("F57 not in", values, "f57");
            return (Criteria) this;
        }

        public Criteria andF57Between(String value1, String value2) {
            addCriterion("F57 between", value1, value2, "f57");
            return (Criteria) this;
        }

        public Criteria andF57NotBetween(String value1, String value2) {
            addCriterion("F57 not between", value1, value2, "f57");
            return (Criteria) this;
        }

        public Criteria andF58IsNull() {
            addCriterion("F58 is null");
            return (Criteria) this;
        }

        public Criteria andF58IsNotNull() {
            addCriterion("F58 is not null");
            return (Criteria) this;
        }

        public Criteria andF58EqualTo(String value) {
            addCriterion("F58 =", value, "f58");
            return (Criteria) this;
        }

        public Criteria andF58NotEqualTo(String value) {
            addCriterion("F58 <>", value, "f58");
            return (Criteria) this;
        }

        public Criteria andF58GreaterThan(String value) {
            addCriterion("F58 >", value, "f58");
            return (Criteria) this;
        }

        public Criteria andF58GreaterThanOrEqualTo(String value) {
            addCriterion("F58 >=", value, "f58");
            return (Criteria) this;
        }

        public Criteria andF58LessThan(String value) {
            addCriterion("F58 <", value, "f58");
            return (Criteria) this;
        }

        public Criteria andF58LessThanOrEqualTo(String value) {
            addCriterion("F58 <=", value, "f58");
            return (Criteria) this;
        }

        public Criteria andF58Like(String value) {
            addCriterion("F58 like", value, "f58");
            return (Criteria) this;
        }

        public Criteria andF58NotLike(String value) {
            addCriterion("F58 not like", value, "f58");
            return (Criteria) this;
        }

        public Criteria andF58In(List<String> values) {
            addCriterion("F58 in", values, "f58");
            return (Criteria) this;
        }

        public Criteria andF58NotIn(List<String> values) {
            addCriterion("F58 not in", values, "f58");
            return (Criteria) this;
        }

        public Criteria andF58Between(String value1, String value2) {
            addCriterion("F58 between", value1, value2, "f58");
            return (Criteria) this;
        }

        public Criteria andF58NotBetween(String value1, String value2) {
            addCriterion("F58 not between", value1, value2, "f58");
            return (Criteria) this;
        }

        public Criteria andF59IsNull() {
            addCriterion("F59 is null");
            return (Criteria) this;
        }

        public Criteria andF59IsNotNull() {
            addCriterion("F59 is not null");
            return (Criteria) this;
        }

        public Criteria andF59EqualTo(String value) {
            addCriterion("F59 =", value, "f59");
            return (Criteria) this;
        }

        public Criteria andF59NotEqualTo(String value) {
            addCriterion("F59 <>", value, "f59");
            return (Criteria) this;
        }

        public Criteria andF59GreaterThan(String value) {
            addCriterion("F59 >", value, "f59");
            return (Criteria) this;
        }

        public Criteria andF59GreaterThanOrEqualTo(String value) {
            addCriterion("F59 >=", value, "f59");
            return (Criteria) this;
        }

        public Criteria andF59LessThan(String value) {
            addCriterion("F59 <", value, "f59");
            return (Criteria) this;
        }

        public Criteria andF59LessThanOrEqualTo(String value) {
            addCriterion("F59 <=", value, "f59");
            return (Criteria) this;
        }

        public Criteria andF59Like(String value) {
            addCriterion("F59 like", value, "f59");
            return (Criteria) this;
        }

        public Criteria andF59NotLike(String value) {
            addCriterion("F59 not like", value, "f59");
            return (Criteria) this;
        }

        public Criteria andF59In(List<String> values) {
            addCriterion("F59 in", values, "f59");
            return (Criteria) this;
        }

        public Criteria andF59NotIn(List<String> values) {
            addCriterion("F59 not in", values, "f59");
            return (Criteria) this;
        }

        public Criteria andF59Between(String value1, String value2) {
            addCriterion("F59 between", value1, value2, "f59");
            return (Criteria) this;
        }

        public Criteria andF59NotBetween(String value1, String value2) {
            addCriterion("F59 not between", value1, value2, "f59");
            return (Criteria) this;
        }

        public Criteria andF60IsNull() {
            addCriterion("F60 is null");
            return (Criteria) this;
        }

        public Criteria andF60IsNotNull() {
            addCriterion("F60 is not null");
            return (Criteria) this;
        }

        public Criteria andF60EqualTo(String value) {
            addCriterion("F60 =", value, "f60");
            return (Criteria) this;
        }

        public Criteria andF60NotEqualTo(String value) {
            addCriterion("F60 <>", value, "f60");
            return (Criteria) this;
        }

        public Criteria andF60GreaterThan(String value) {
            addCriterion("F60 >", value, "f60");
            return (Criteria) this;
        }

        public Criteria andF60GreaterThanOrEqualTo(String value) {
            addCriterion("F60 >=", value, "f60");
            return (Criteria) this;
        }

        public Criteria andF60LessThan(String value) {
            addCriterion("F60 <", value, "f60");
            return (Criteria) this;
        }

        public Criteria andF60LessThanOrEqualTo(String value) {
            addCriterion("F60 <=", value, "f60");
            return (Criteria) this;
        }

        public Criteria andF60Like(String value) {
            addCriterion("F60 like", value, "f60");
            return (Criteria) this;
        }

        public Criteria andF60NotLike(String value) {
            addCriterion("F60 not like", value, "f60");
            return (Criteria) this;
        }

        public Criteria andF60In(List<String> values) {
            addCriterion("F60 in", values, "f60");
            return (Criteria) this;
        }

        public Criteria andF60NotIn(List<String> values) {
            addCriterion("F60 not in", values, "f60");
            return (Criteria) this;
        }

        public Criteria andF60Between(String value1, String value2) {
            addCriterion("F60 between", value1, value2, "f60");
            return (Criteria) this;
        }

        public Criteria andF60NotBetween(String value1, String value2) {
            addCriterion("F60 not between", value1, value2, "f60");
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