package com.ryx.credit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.CRule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ICRuleService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/31
 * @Time: 16:42
 * To change this template use File | Settings | File Templates.
 */

public interface ICRuleService {

    int insert(CRule record);

    int insertSelective(CRule record);

    CRule selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(CRule record);

    int updateByPrimaryKey(CRule record);

    Map configExample(Page page, CRule cRule);

    public String computeDiscountPrice(Object product, String field, Object user, Map<String, Object> params);

}
