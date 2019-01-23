package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.ProfitDay;
import com.ryx.credit.profit.pojo.ProfitDayExample;

import java.util.List;
import java.util.Map;


/**
 * IProfitDService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang y
 * @Date 2017/7/31
 * @Time: 16:42
 * To change this template use File | Settings | File Templates.
 */

public interface IProfitDService {

    int countByExample(ProfitDayExample example);

    int deleteByExample(ProfitDayExample example);

    int insert(ProfitDay record);

    int insertSelective(ProfitDay record);

    List<ProfitDay> selectByExample(ProfitDayExample example);

    ProfitDay selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitDay record);

    int updateByPrimaryKey(ProfitDay record);

    PageInfo profitDList(Map<String,String> map, Page page);

    public List<ProfitDay> exportProfitD(ProfitDay profitD);
}
