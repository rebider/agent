package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.PCityApplicationDetail;
import com.ryx.credit.profit.pojo.PosCheck;

import java.util.List;
import java.util.Map;

/**
 * @Decribute 省区发起代理商其他补扣款申请 接口
 * @Author chenqiutian
 * @Create 2019/1/30
 */
public interface IProfitCityApplicationService {

    /**
     * 获得其他扣款扣款申请数据
     * @return
     */
    PageInfo getDeductionAppList(Page page, String userId, PCityApplicationDetail pCityApplicationDetail);

    /**
     * 省区其他扣款申请：进行审批流
     */
    void applyOtherDeduction(PCityApplicationDetail pCityApplicationDetail,String userId, String workId,String cUser)throws Exception ;


    /**
     * 根据id获得数据
     */
    PCityApplicationDetail getDataById(String id);

    /**
     * 处理审批任务
     */
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException;

    /**
     * 审批流调回方法
     */
    void completeTaskEnterActivity(String insid, String status);

    /**
     * 审批拒绝，修改审批信息
     */
    void editCheckRegect(PCityApplicationDetail pCityApplicationDetail)throws Exception;

}
