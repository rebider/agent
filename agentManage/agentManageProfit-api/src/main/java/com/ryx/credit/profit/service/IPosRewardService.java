package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.*;

import java.util.List;


/**
 * IPosRewardService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang y
 * @Date 2017/7/31
 * @Time: 16:42
 * To change this template use File | Settings | File Templates.
 */

public interface IPosRewardService {

    PageInfo posRewardList(PosReward record, Page page);

    /**
     * POS奖励申请，进行审批流
     * @param posReward
     * @param userId
     * @param workId
     */
    public void applyPosReward(PosReward posReward, String userId, String workId) throws ProcessException;

    /**
     * 处理审批任务
     * @param agentVo
     * @param userId
     * @throws ProcessException
     */
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException;

    /**
     * 审批流回调方法
     * @param insid
     * @param status
     */
    public void completeTaskEnterActivity(String insid, String status);

    /**
     * 获取POS奖励信息
     * @param id 不能为空
     * @return POS奖励信息
     */
    PosReward getPosRewardById(String id);

    /**
     * 根据ID 查询数据信息
     * @param id
     * @return
     */
    public List<PosReward> getPosRewardByDataId(String id);

    /**
     * 审批退回，修改申请信息
     * @param posReward
     * @throws Exception
     */
    public void editRewardRegect(PosReward posReward)throws Exception;

    /**
     * 查询此交易月份是否已申请
     * @param posReward
     * @return
     */
    List<PosReward> selectRewardByMonth(PosReward posReward);

    /**
     * 按照考核月查询pos奖励申请
     * @param posReward
     * @return
     */
    List<PosReward> selectByEndMonth(PosReward posReward);



}
