package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.profit.pojo.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author yangmx
 * @desc 月分润报表服务API
 */
public interface ProfitMonthService {

    /**
     * 查询月分润数据
     * @param page
     * @param ProfitDetailMonth
     * @return
     */
   // public List<ProfitMonth> getProfitMonthList(Page page, ProfitMonth profitMonth);
    public List<ProfitDetailMonth> getProfitMonthList(Page page, ProfitDetailMonth ProfitDetailMonth);
    /**
     * 获取月分润总条数
     * @param profitDetailMonth
     * @return
     */
    //public int getProfitMonthCount(ProfitMonth profitMonth);
    public int getProfitMonthCount(ProfitDetailMonth profitDetailMonth);
    /**
     * 查询月分润明细
     * @param page
     * @param profitDetailMonth
     * @return
     */
    public List<ProfitDetailMonth> getProfitDetailMonthList(Map<String, Object> department, Page page, ProfitDetailMonth profitDetailMonth);

    /**
     * 查询月分润明细总数
     * @param profitDetailMonth
     * @return
     */
    public int getProfitDetailMonthCount(Map<String, Object> department, ProfitDetailMonth profitDetailMonth);

    /**
     * 获取月度交易明细
     * @param transProfitDetail
     * @return
     */
    public List<TransProfitDetail> getTransProfitDetail(Page page, TransProfitDetail transProfitDetail);

    /**
     * 获取月度交易明细
     * @param transProfitDetail
     * @return
     */
    public long getTransProfitDetailCount(TransProfitDetail transProfitDetail);

    /**
     * 查询一条数据
     * @param id
     * @return
     */
//    public ProfitMonth getProfitMonth(String id);
    public ProfitDetailMonth getProfitDetailMonth(String id);

    /**
     * 修改月分润数据
     * @param profitMonth
     */
    public void updateProfitMonth(ProfitDetailMonth profitMonth);

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
    ProfitDetailMonth selectByPrimaryKey(String id);

    /**
     *更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ProfitDetailMonth record);

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
    public List<ProfitDetailMonth> getAgentProfit(String agentId, String profitDate, String parentAgentId);

    /*** 
    * @Description: 获取担保代理商信息
    * @Param:  
    * @return:  
    * @Author: zhaodw 
    * @Date: 2018/10/17 
    */ 
    Map<String, Object> getDbProfitAmt(String agentId, String parentAgentId, String computType);

    /***
    * @Description: 计算代理商分润金额
    * @Author: zhaodw
    * @Date: 2018/8/12
    */
    void computeProfitAmt();

    /***
     * @Description: 试算计算代理商分润金额
     * @Author: zhaodw
     * @Date: 2018/8/12
     */
    void testComputeProfitAmt();

    /***
    * @Description: 出款
    * @Author: zhaodw
    * @Date: 2018/8/24
    */
    void payMoney();

    /**
     * 导出数据
     * @param profitDetailMonth
     * @return
     */
    List<ProfitDirect> exportByFinance(ProfitDetailMonth profitDetailMonth);

    /**
     * 修改分润展示补款
     * */
    //public void updateProfitMonthDetail(ProfitDetailMonth  profitDetailMonth);

    /**
     * 初始化Pos奖励明细数据
     */
    void initPosRowardDetail()throws Exception;
}
