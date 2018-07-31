package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.PosCheck;
import com.ryx.credit.profit.pojo.PosCheckExample;
import com.ryx.credit.profit.pojo.ProfitDay;
import com.ryx.credit.profit.pojo.ProfitDayExample;

import java.util.List;


/**
 * IPosCheckService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang y
 * @Date 2017/7/31
 * @Time: 16:42
 * To change this template use File | Settings | File Templates.
 */

public interface IPosCheckService {

    long countByExample(PosCheckExample example);

    int deleteByExample(PosCheckExample example);

    int insert(PosCheck record);

    int insertSelective(PosCheck record);

    List<PosCheck> selectByExample(PosCheckExample example);

    PosCheck selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PosCheck record);

    int updateByPrimaryKey(PosCheck record);

    PageInfo PosCheckList(PosCheck record, Page page);
}
