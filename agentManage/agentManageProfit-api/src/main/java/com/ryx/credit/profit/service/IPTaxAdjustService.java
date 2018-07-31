package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.PTaxAdjust;
import com.ryx.credit.profit.pojo.PTaxAdjustExample;
import com.ryx.credit.profit.pojo.ProfitDay;
import com.ryx.credit.profit.pojo.ProfitDayExample;

import java.util.List;


/**
 * IPTaxAdjustService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang y
 * @Date 2017/7/31
 * @Time: 16:42
 * To change this template use File | Settings | File Templates.
 */

public interface IPTaxAdjustService {

    long countByExample(PTaxAdjustExample example);

    int deleteByExample(PTaxAdjustExample example);

    int insert(PTaxAdjust record);

    int insertSelective(PTaxAdjust record);

    List<PTaxAdjust> selectByExample(PTaxAdjustExample example);

    PTaxAdjust selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PTaxAdjust record);

    int updateByPrimaryKey(PTaxAdjust record);

    PageInfo PTaxAdjustList(PTaxAdjust record, Page page);
}
