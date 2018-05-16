package com.ryx.credit.pojo.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CCondition implements Serializable{
    private BigDecimal id;

    private Object name;

    private Object conditionKey;

    private Object type;

    private Date createTime;

    private BigDecimal operator;

    private Date updateTime;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getConditionKey() {
        return conditionKey;
    }

    public void setConditionKey(Object conditionKey) {
        this.conditionKey = conditionKey;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getOperator() {
        return operator;
    }

    public void setOperator(BigDecimal operator) {
        this.operator = operator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public enum PropertyType {
        S(String.class), I(Integer.class), D(Date.class), B(Boolean.class);

        private Class<?> clazz;

        private PropertyType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Class<?> getValue() {
            return clazz;
        }
    }

    public static final PropertyType INT_TYPE = PropertyType.I;

    public static final PropertyType STRING_TYPE = PropertyType.S;

    public static final PropertyType DATE_TYPE = PropertyType.D;

    public static final PropertyType BOOLEAN_TYPE = PropertyType.B;


    public static Map<PropertyType, String> getPropertyTypeMap() {
        Map<PropertyType, String> hashMap = new HashMap<PropertyType, String>();
        hashMap.put(INT_TYPE, "int型");
        hashMap.put(STRING_TYPE, "string型");
        hashMap.put(DATE_TYPE, "date型");
        hashMap.put(BOOLEAN_TYPE, "boolean型");
        return hashMap;
    }
}