package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitMonth;
import java.util.List;

/**
 * @author yangmx
 * @desc 月分润报表服务API
 */
public interface ProfitMonthService {

    /**
     * 查询月分润数据
     * @param page
     * @param profitMonth
     * @return
     */
    public List<ProfitMonth> getProfitMonthList(Page page, ProfitMonth profitMonth);

    /**
     * 获取月分润总条数
     * @param profitMonth
     * @return
     */
    public int getProfitMonthCount(ProfitMonth profitMonth);

    /**
     * 查询月分润明细
     * @param page
     * @param profitDetailMonth
     * @return
     */
    public List<ProfitDetailMonth> getProfitDetailMonthList(Page page, ProfitDetailMonth profitDetailMonth);

    /**
     * 查询月分润明细总数
     * @param profitDetailMonth
     * @return
     */
    public int getProfitDetailMonthCount(ProfitDetailMonth profitDetailMonth);

    /**
     * 查询一条数据
     * @param id
     * @return
     */
    public ProfitMonth getProfitMonth(String id);

    /**
     * 修改数据
     * @param profitMonth
     */
    public void updateProfitMonth(ProfitMonth profitMonth);

    ProfitMonth selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitMonth record);
}
