package com.ryx.credit.profit.service;/**
 * @Auther: zhaodw
 * @Date: 2018/8/6 09:06
 * @Description:
 */

import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitDetailMonthExample;
import com.ryx.credit.profit.pojo.TransProfitDetail;

import java.util.List;

/**
 * 月度分润明细业务接口
 *
 * @author zhaodw
 * @create 2018/8/6
 * @since 1.0.0
 */
public interface ProfitDetailMonthService {

    /***
    * @Description: 新增分润明细
    * @Param:  分润明细对象
    * @Author: zhaodw
    * @Date: 2018/8/6
    */
    void insert(ProfitDetailMonth profitDetailMonth);

    /***
     * @Description: 修改分润明细
     * @Param:  分润明细对象
     * @Author: zhaodw
     * @Date: 2018/8/6
     */
    void update(ProfitDetailMonth profitDetailMonth);

    int countByExample(ProfitDetailMonthExample example);

    int deleteByExample(ProfitDetailMonthExample example);

    int insertSelective(ProfitDetailMonth record);

    List<ProfitDetailMonth> selectByExample(ProfitDetailMonthExample example);

    ProfitDetailMonth selectByPrimaryKey(String id);

    ProfitDetailMonth selectByPIdAndMonth(ProfitDetailMonth record);

    int updateByPrimaryKeySelective(ProfitDetailMonth record);

    int updateByPrimaryKey(ProfitDetailMonth record);

    /**
     * 根据业务编码、AG编码、月份查询明细
     * @param agentId
     * @param agentPid
     * @param profitDate
     * @return
     */
    List<TransProfitDetail> getTransProfitDetailList(String agentId, String agentPid, String profitDate);
}
