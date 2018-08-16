package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitMonth;
import com.ryx.credit.profit.pojo.ProfitUnfreeze;

import java.math.BigDecimal;
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
     * 修改月分润数据
     * @param profitMonth
     */
    public void updateProfitMonth(ProfitMonth profitMonth);

    /**
     * 新增月分润数据
     * @author zhaodw
     * @param profitMonth
     */
    void insertProfitMonth(ProfitMonth profitMonth);

    /**
     *查找
     * @param id
     * @return
     */
    ProfitMonth selectByPrimaryKey(String id);

    /**
     *更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ProfitMonth record);

    /**
     * 新增解冻记录
     */
    public ProfitUnfreeze insertProfitUnfreeze(ProfitUnfreeze profitUnfreeze);


    void editProfitUnfreeze(ProfitUnfreeze profitUnfreeze);

    /**
     * 申请解冻，启动审批流
     * @param profitUnfreeze
     */
    public void apptlyProfitUnfreeze(ProfitUnfreeze profitUnfreeze, String userId, String workId) throws ProcessException;

    /**
     * @author zhaodw
     * @param id 解冻审批对象id 不能为空
     * @return 解冻审批对象
     */
    ProfitUnfreeze getProfitUnfreezeById(String id);

    /**
     * @author zhaodw
     * @param profitId 分润id不能为空
     * @return 解冻审批对象
     */
    ProfitUnfreeze getProfitUnfreezeByProfitId(String profitId);

    /**
     *
     * 功能描述: 任务完成执行业务接口方法
     *
     * @param: insid 流程实例id status 状态
     * @auther: zhaodw
     * @date: 2018/7/28
     */
    void completeTaskEnterActivity(String insid, String status );

    /**
     * 获取代理商分润
     * @param agentId
     * @param profitDate
     * @return
     */
    public ProfitDetailMonth getAgentProfit(String agentId, String profitDate);

    /***
    * @Description: 计算代理商分润金额
    * @Author: zhaodw
    * @Date: 2018/8/12
    */
    void computeProfitAmt();

    /***
    * @Description: 根据代理商唯一码获取交易金额
    * @Param:  agentId 代理商唯一编码
    * @return:  交易金额
    * @Author: zhaodw
    * @Date: 2018/8/15
    */
    BigDecimal getTranByAgentId(String agentId);
}
