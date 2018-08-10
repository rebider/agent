package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitStaging;
import com.ryx.credit.profit.pojo.ProfitStagingDetail;

import java.math.BigDecimal;

/**
 * @author zhaodw
 * @Title: ProfitDeductionService
 * @ProjectName agentManage
 * @Description: 分期业务逻辑接口
 * @date 2018/7/2417:27
 */
public interface StagingService {

    /**
     * 获取分期分页列表
     * @param profitStaging 分期信息
     * @param page 分页信息
     * @return 分页列表
     */
    PageInfo getStagingList(ProfitStaging profitStaging, Page page);


    /**
     * 获取分期明细分页列表
     * @param profitStagingDetail 分期明细信息
     * @param page 分页信息
     * @return 分页列表
     */
    PageInfo getStagingDetailList(ProfitStagingDetail profitStagingDetail, Page page);


    /**
     * 获取扣款分期信息
     * @param id 不能为空
     * @return 扣款信息
     */
    ProfitStaging getStagingById(String id);

    /**
     * 获取扣款分期信息
     * @param sourceId 扣款id 不能为空
     * @return 扣款信息
     */
    ProfitStaging getStagingBySourceId(String sourceId);

    /**
     * 新增分期
     *
     * @param profitStaging 分期对象
     */
    void addStaging(ProfitStaging profitStaging, String workId);

    /**
     * 流程结束修改业务数据
     * @param insid 实例id
     * @param status 状态
     */
    void completeTaskEnterActivity(String insid, String status, String remark);

    /*** 
    * @Description: 新增分期明细
    * @Param:  stagingDetail 分期明细
    * @Author: zhaodw
    * @Date: 2018/7/31 
    */ 
    void insetStagingDetail(ProfitStagingDetail stagingDetail);

    /*** 
    * @Description:  编辑分期明细
    * @Param:   stagingDetail 分期明细
    * @Author: zhaodw
    * @Date: 2018/8/8 
    */ 
    void editStagingDetail(ProfitStagingDetail stagingDetail);

    /*** 
    * @Description: 编辑分期
    * @Param:  profitStaging 分期对象
    * @Author: zhaodw
    * @Date: 2018/8/2 
    */ 
     void editStaging(ProfitStaging profitStaging);

     /***
     * @Description: 获取代理商分期未扣完金额
     * @Param:agentId 机构id
     * @return: 未分期总金额
     * @Author: zhaodw
     * @Date: 2018/8/9
     */
     BigDecimal getNotDeductionAmt(String agentId);

}
