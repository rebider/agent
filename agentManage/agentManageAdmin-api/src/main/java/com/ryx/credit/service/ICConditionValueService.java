package com.ryx.credit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.CConditionValue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ICConditionValueService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/31
 * @Time: 15:44
 * To change this template use File | Settings | File Templates.
 */

public interface ICConditionValueService {
    int insert(CConditionValue record);

    int insertSelective(CConditionValue record);

    CConditionValue selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(CConditionValue record);

    int updateByPrimaryKey(CConditionValue record);

    Map configExample(Page page, CConditionValue cConditionValue);
}
