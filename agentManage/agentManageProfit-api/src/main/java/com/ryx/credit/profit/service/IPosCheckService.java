package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
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

    /**
     * 分润比例考核申请，进行审批流
     * @param posCheck
     * @param userId
     * @param workId
     */
    public void applyPosCheck(PosCheck posCheck, String userId, String workId) throws ProcessException;

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
     * 获取分润比例考核信息
     * @param id 不能为空
     * @return POS奖励信息
     */
    PosCheck getPosCheckById(String id);

    /**
     * 根据ID 查询数据信息
     * @param id
     * @return
     */
    public List<PosCheck> getPosCheckByDataId(String id);

    /**
     * 审批退回，修改申请信息
     * @param posCheck
     * @throws Exception
     */
    public void editCheckRegect(PosCheck posCheck)throws Exception;
}
