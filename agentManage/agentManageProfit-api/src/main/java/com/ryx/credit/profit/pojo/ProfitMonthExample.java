package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProfitMonthExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ProfitMonthExample() {
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

        public Criteria andAgentPidIsNull() {
            addCriterion("AGENT_PID is null");
            return (Criteria) this;
        }

        public Criteria andAgentPidIsNotNull() {
            addCriterion("AGENT_PID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentPidEqualTo(String value) {
            addCriterion("AGENT_PID =", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotEqualTo(String value) {
            addCriterion("AGENT_PID <>", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidGreaterThan(String value) {
            addCriterion("AGENT_PID >", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_PID >=", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLessThan(String value) {
            addCriterion("AGENT_PID <", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLessThanOrEqualTo(String value) {
            addCriterion("AGENT_PID <=", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLike(String value) {
            addCriterion("AGENT_PID like", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotLike(String value) {
            addCriterion("AGENT_PID not like", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidIn(List<String> values) {
            addCriterion("AGENT_PID in", values, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotIn(List<String> values) {
            addCriterion("AGENT_PID not in", values, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidBetween(String value1, String value2) {
            addCriterion("AGENT_PID between", value1, value2, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotBetween(String value1, String value2) {
            addCriterion("AGENT_PID not between", value1, value2, "agentPid");
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

        public Criteria andProfitDateIsNull() {
            addCriterion("PROFIT_DATE is null");
            return (Criteria) this;
        }

        public Criteria andProfitDateIsNotNull() {
            addCriterion("PROFIT_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andProfitDateEqualTo(String value) {
            addCriterion("PROFIT_DATE =", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateNotEqualTo(String value) {
            addCriterion("PROFIT_DATE <>", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateGreaterThan(String value) {
            addCriterion("PROFIT_DATE >", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateGreaterThanOrEqualTo(String value) {
            addCriterion("PROFIT_DATE >=", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateLessThan(String value) {
            addCriterion("PROFIT_DATE <", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateLessThanOrEqualTo(String value) {
            addCriterion("PROFIT_DATE <=", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateLike(String value) {
            addCriterion("PROFIT_DATE like", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateNotLike(String value) {
            addCriterion("PROFIT_DATE not like", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateIn(List<String> values) {
            addCriterion("PROFIT_DATE in", values, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateNotIn(List<String> values) {
            addCriterion("PROFIT_DATE not in", values, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateBetween(String value1, String value2) {
            addCriterion("PROFIT_DATE between", value1, value2, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateNotBetween(String value1, String value2) {
            addCriterion("PROFIT_DATE not between", value1, value2, "profitDate");
            return (Criteria) this;
        }

        public Criteria andTransProfitPosIsNull() {
            addCriterion("TRANS_PROFIT_POS is null");
            return (Criteria) this;
        }

        public Criteria andTransProfitPosIsNotNull() {
            addCriterion("TRANS_PROFIT_POS is not null");
            return (Criteria) this;
        }

        public Criteria andTransProfitPosEqualTo(BigDecimal value) {
            addCriterion("TRANS_PROFIT_POS =", value, "transProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransProfitPosNotEqualTo(BigDecimal value) {
            addCriterion("TRANS_PROFIT_POS <>", value, "transProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransProfitPosGreaterThan(BigDecimal value) {
            addCriterion("TRANS_PROFIT_POS >", value, "transProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransProfitPosGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_PROFIT_POS >=", value, "transProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransProfitPosLessThan(BigDecimal value) {
            addCriterion("TRANS_PROFIT_POS <", value, "transProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransProfitPosLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_PROFIT_POS <=", value, "transProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransProfitPosIn(List<BigDecimal> values) {
            addCriterion("TRANS_PROFIT_POS in", values, "transProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransProfitPosNotIn(List<BigDecimal> values) {
            addCriterion("TRANS_PROFIT_POS not in", values, "transProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransProfitPosBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_PROFIT_POS between", value1, value2, "transProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransProfitPosNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_PROFIT_POS not between", value1, value2, "transProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitPosIsNull() {
            addCriterion("TRANS_SUPPLY_PROFIT_POS is null");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitPosIsNotNull() {
            addCriterion("TRANS_SUPPLY_PROFIT_POS is not null");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitPosEqualTo(BigDecimal value) {
            addCriterion("TRANS_SUPPLY_PROFIT_POS =", value, "transSupplyProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitPosNotEqualTo(BigDecimal value) {
            addCriterion("TRANS_SUPPLY_PROFIT_POS <>", value, "transSupplyProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitPosGreaterThan(BigDecimal value) {
            addCriterion("TRANS_SUPPLY_PROFIT_POS >", value, "transSupplyProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitPosGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_SUPPLY_PROFIT_POS >=", value, "transSupplyProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitPosLessThan(BigDecimal value) {
            addCriterion("TRANS_SUPPLY_PROFIT_POS <", value, "transSupplyProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitPosLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_SUPPLY_PROFIT_POS <=", value, "transSupplyProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitPosIn(List<BigDecimal> values) {
            addCriterion("TRANS_SUPPLY_PROFIT_POS in", values, "transSupplyProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitPosNotIn(List<BigDecimal> values) {
            addCriterion("TRANS_SUPPLY_PROFIT_POS not in", values, "transSupplyProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitPosBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_SUPPLY_PROFIT_POS between", value1, value2, "transSupplyProfitPos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitPosNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_SUPPLY_PROFIT_POS not between", value1, value2, "transSupplyProfitPos");
            return (Criteria) this;
        }

        public Criteria andPosRewardIsNull() {
            addCriterion("POS_REWARD is null");
            return (Criteria) this;
        }

        public Criteria andPosRewardIsNotNull() {
            addCriterion("POS_REWARD is not null");
            return (Criteria) this;
        }

        public Criteria andPosRewardEqualTo(Long value) {
            addCriterion("POS_REWARD =", value, "posReward");
            return (Criteria) this;
        }

        public Criteria andPosRewardNotEqualTo(Long value) {
            addCriterion("POS_REWARD <>", value, "posReward");
            return (Criteria) this;
        }

        public Criteria andPosRewardGreaterThan(Long value) {
            addCriterion("POS_REWARD >", value, "posReward");
            return (Criteria) this;
        }

        public Criteria andPosRewardGreaterThanOrEqualTo(Long value) {
            addCriterion("POS_REWARD >=", value, "posReward");
            return (Criteria) this;
        }

        public Criteria andPosRewardLessThan(Long value) {
            addCriterion("POS_REWARD <", value, "posReward");
            return (Criteria) this;
        }

        public Criteria andPosRewardLessThanOrEqualTo(Long value) {
            addCriterion("POS_REWARD <=", value, "posReward");
            return (Criteria) this;
        }

        public Criteria andPosRewardIn(List<Long> values) {
            addCriterion("POS_REWARD in", values, "posReward");
            return (Criteria) this;
        }

        public Criteria andPosRewardNotIn(List<Long> values) {
            addCriterion("POS_REWARD not in", values, "posReward");
            return (Criteria) this;
        }

        public Criteria andPosRewardBetween(Long value1, Long value2) {
            addCriterion("POS_REWARD between", value1, value2, "posReward");
            return (Criteria) this;
        }

        public Criteria andPosRewardNotBetween(Long value1, Long value2) {
            addCriterion("POS_REWARD not between", value1, value2, "posReward");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitMposIsNull() {
            addCriterion("TRANS_SUPPLY_PROFIT_MPOS is null");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitMposIsNotNull() {
            addCriterion("TRANS_SUPPLY_PROFIT_MPOS is not null");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitMposEqualTo(BigDecimal value) {
            addCriterion("TRANS_SUPPLY_PROFIT_MPOS =", value, "transSupplyProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitMposNotEqualTo(BigDecimal value) {
            addCriterion("TRANS_SUPPLY_PROFIT_MPOS <>", value, "transSupplyProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitMposGreaterThan(BigDecimal value) {
            addCriterion("TRANS_SUPPLY_PROFIT_MPOS >", value, "transSupplyProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitMposGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_SUPPLY_PROFIT_MPOS >=", value, "transSupplyProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitMposLessThan(BigDecimal value) {
            addCriterion("TRANS_SUPPLY_PROFIT_MPOS <", value, "transSupplyProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitMposLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_SUPPLY_PROFIT_MPOS <=", value, "transSupplyProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitMposIn(List<BigDecimal> values) {
            addCriterion("TRANS_SUPPLY_PROFIT_MPOS in", values, "transSupplyProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitMposNotIn(List<BigDecimal> values) {
            addCriterion("TRANS_SUPPLY_PROFIT_MPOS not in", values, "transSupplyProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitMposBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_SUPPLY_PROFIT_MPOS between", value1, value2, "transSupplyProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransSupplyProfitMposNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_SUPPLY_PROFIT_MPOS not between", value1, value2, "transSupplyProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransProfitMposIsNull() {
            addCriterion("TRANS_PROFIT_MPOS is null");
            return (Criteria) this;
        }

        public Criteria andTransProfitMposIsNotNull() {
            addCriterion("TRANS_PROFIT_MPOS is not null");
            return (Criteria) this;
        }

        public Criteria andTransProfitMposEqualTo(BigDecimal value) {
            addCriterion("TRANS_PROFIT_MPOS =", value, "transProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransProfitMposNotEqualTo(BigDecimal value) {
            addCriterion("TRANS_PROFIT_MPOS <>", value, "transProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransProfitMposGreaterThan(BigDecimal value) {
            addCriterion("TRANS_PROFIT_MPOS >", value, "transProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransProfitMposGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_PROFIT_MPOS >=", value, "transProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransProfitMposLessThan(BigDecimal value) {
            addCriterion("TRANS_PROFIT_MPOS <", value, "transProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransProfitMposLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_PROFIT_MPOS <=", value, "transProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransProfitMposIn(List<BigDecimal> values) {
            addCriterion("TRANS_PROFIT_MPOS in", values, "transProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransProfitMposNotIn(List<BigDecimal> values) {
            addCriterion("TRANS_PROFIT_MPOS not in", values, "transProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransProfitMposBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_PROFIT_MPOS between", value1, value2, "transProfitMpos");
            return (Criteria) this;
        }

        public Criteria andTransProfitMposNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_PROFIT_MPOS not between", value1, value2, "transProfitMpos");
            return (Criteria) this;
        }

        public Criteria andProfitDeductionIsNull() {
            addCriterion("PROFIT_DEDUCTION is null");
            return (Criteria) this;
        }

        public Criteria andProfitDeductionIsNotNull() {
            addCriterion("PROFIT_DEDUCTION is not null");
            return (Criteria) this;
        }

        public Criteria andProfitDeductionEqualTo(BigDecimal value) {
            addCriterion("PROFIT_DEDUCTION =", value, "profitDeduction");
            return (Criteria) this;
        }

        public Criteria andProfitDeductionNotEqualTo(BigDecimal value) {
            addCriterion("PROFIT_DEDUCTION <>", value, "profitDeduction");
            return (Criteria) this;
        }

        public Criteria andProfitDeductionGreaterThan(BigDecimal value) {
            addCriterion("PROFIT_DEDUCTION >", value, "profitDeduction");
            return (Criteria) this;
        }

        public Criteria andProfitDeductionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_DEDUCTION >=", value, "profitDeduction");
            return (Criteria) this;
        }

        public Criteria andProfitDeductionLessThan(BigDecimal value) {
            addCriterion("PROFIT_DEDUCTION <", value, "profitDeduction");
            return (Criteria) this;
        }

        public Criteria andProfitDeductionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_DEDUCTION <=", value, "profitDeduction");
            return (Criteria) this;
        }

        public Criteria andProfitDeductionIn(List<BigDecimal> values) {
            addCriterion("PROFIT_DEDUCTION in", values, "profitDeduction");
            return (Criteria) this;
        }

        public Criteria andProfitDeductionNotIn(List<BigDecimal> values) {
            addCriterion("PROFIT_DEDUCTION not in", values, "profitDeduction");
            return (Criteria) this;
        }

        public Criteria andProfitDeductionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_DEDUCTION between", value1, value2, "profitDeduction");
            return (Criteria) this;
        }

        public Criteria andProfitDeductionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_DEDUCTION not between", value1, value2, "profitDeduction");
            return (Criteria) this;
        }

        public Criteria andProfitSupplyIsNull() {
            addCriterion("PROFIT_SUPPLY is null");
            return (Criteria) this;
        }

        public Criteria andProfitSupplyIsNotNull() {
            addCriterion("PROFIT_SUPPLY is not null");
            return (Criteria) this;
        }

        public Criteria andProfitSupplyEqualTo(BigDecimal value) {
            addCriterion("PROFIT_SUPPLY =", value, "profitSupply");
            return (Criteria) this;
        }

        public Criteria andProfitSupplyNotEqualTo(BigDecimal value) {
            addCriterion("PROFIT_SUPPLY <>", value, "profitSupply");
            return (Criteria) this;
        }

        public Criteria andProfitSupplyGreaterThan(BigDecimal value) {
            addCriterion("PROFIT_SUPPLY >", value, "profitSupply");
            return (Criteria) this;
        }

        public Criteria andProfitSupplyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_SUPPLY >=", value, "profitSupply");
            return (Criteria) this;
        }

        public Criteria andProfitSupplyLessThan(BigDecimal value) {
            addCriterion("PROFIT_SUPPLY <", value, "profitSupply");
            return (Criteria) this;
        }

        public Criteria andProfitSupplyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_SUPPLY <=", value, "profitSupply");
            return (Criteria) this;
        }

        public Criteria andProfitSupplyIn(List<BigDecimal> values) {
            addCriterion("PROFIT_SUPPLY in", values, "profitSupply");
            return (Criteria) this;
        }

        public Criteria andProfitSupplyNotIn(List<BigDecimal> values) {
            addCriterion("PROFIT_SUPPLY not in", values, "profitSupply");
            return (Criteria) this;
        }

        public Criteria andProfitSupplyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_SUPPLY between", value1, value2, "profitSupply");
            return (Criteria) this;
        }

        public Criteria andProfitSupplyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_SUPPLY not between", value1, value2, "profitSupply");
            return (Criteria) this;
        }

        public Criteria andTaxDeductionIsNull() {
            addCriterion("TAX_DEDUCTION is null");
            return (Criteria) this;
        }

        public Criteria andTaxDeductionIsNotNull() {
            addCriterion("TAX_DEDUCTION is not null");
            return (Criteria) this;
        }

        public Criteria andTaxDeductionEqualTo(BigDecimal value) {
            addCriterion("TAX_DEDUCTION =", value, "taxDeduction");
            return (Criteria) this;
        }

        public Criteria andTaxDeductionNotEqualTo(BigDecimal value) {
            addCriterion("TAX_DEDUCTION <>", value, "taxDeduction");
            return (Criteria) this;
        }

        public Criteria andTaxDeductionGreaterThan(BigDecimal value) {
            addCriterion("TAX_DEDUCTION >", value, "taxDeduction");
            return (Criteria) this;
        }

        public Criteria andTaxDeductionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_DEDUCTION >=", value, "taxDeduction");
            return (Criteria) this;
        }

        public Criteria andTaxDeductionLessThan(BigDecimal value) {
            addCriterion("TAX_DEDUCTION <", value, "taxDeduction");
            return (Criteria) this;
        }

        public Criteria andTaxDeductionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_DEDUCTION <=", value, "taxDeduction");
            return (Criteria) this;
        }

        public Criteria andTaxDeductionIn(List<BigDecimal> values) {
            addCriterion("TAX_DEDUCTION in", values, "taxDeduction");
            return (Criteria) this;
        }

        public Criteria andTaxDeductionNotIn(List<BigDecimal> values) {
            addCriterion("TAX_DEDUCTION not in", values, "taxDeduction");
            return (Criteria) this;
        }

        public Criteria andTaxDeductionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_DEDUCTION between", value1, value2, "taxDeduction");
            return (Criteria) this;
        }

        public Criteria andTaxDeductionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_DEDUCTION not between", value1, value2, "taxDeduction");
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

        public Criteria andPayProfitIsNull() {
            addCriterion("PAY_PROFIT is null");
            return (Criteria) this;
        }

        public Criteria andPayProfitIsNotNull() {
            addCriterion("PAY_PROFIT is not null");
            return (Criteria) this;
        }

        public Criteria andPayProfitEqualTo(BigDecimal value) {
            addCriterion("PAY_PROFIT =", value, "payProfit");
            return (Criteria) this;
        }

        public Criteria andPayProfitNotEqualTo(BigDecimal value) {
            addCriterion("PAY_PROFIT <>", value, "payProfit");
            return (Criteria) this;
        }

        public Criteria andPayProfitGreaterThan(BigDecimal value) {
            addCriterion("PAY_PROFIT >", value, "payProfit");
            return (Criteria) this;
        }

        public Criteria andPayProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_PROFIT >=", value, "payProfit");
            return (Criteria) this;
        }

        public Criteria andPayProfitLessThan(BigDecimal value) {
            addCriterion("PAY_PROFIT <", value, "payProfit");
            return (Criteria) this;
        }

        public Criteria andPayProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_PROFIT <=", value, "payProfit");
            return (Criteria) this;
        }

        public Criteria andPayProfitIn(List<BigDecimal> values) {
            addCriterion("PAY_PROFIT in", values, "payProfit");
            return (Criteria) this;
        }

        public Criteria andPayProfitNotIn(List<BigDecimal> values) {
            addCriterion("PAY_PROFIT not in", values, "payProfit");
            return (Criteria) this;
        }

        public Criteria andPayProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_PROFIT between", value1, value2, "payProfit");
            return (Criteria) this;
        }

        public Criteria andPayProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_PROFIT not between", value1, value2, "payProfit");
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