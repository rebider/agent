package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.PAgentMerge;

import java.util.List;
import java.util.Map;

/**
 * 代理商合并Service
 * @version V1.0
 * @Description:
 * @author: LiuQY
 * @date: 2018/9/27 09:30
 */
public interface IProfitAgentMergerService {
    /**代理商合并查询*/
    PageInfo getProfitAgentMergeList(Map<String, Object> param, PageInfo pageInfo);
    /**代理商合并提交存库*/
    ResultVO agentMergeTaxEnterIn(PAgentMerge record,Long  userId) throws ProcessException, MessageException;
    /**代理商合并查询一条数据*/
    PAgentMerge getMergeById(String var1);

    /**
     * 处理审批任务
     * @param agentVo
     * @param userId
     * @throws Exception
     */
    AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception;

    /**
     * 审批流回调方法
     * @param insid
     * @param status
     */
    AgentResult approveFinish(String insid, String status) throws Exception;

    /**
     * 审批退回，修改申请信息
     * @param pAgentMerge
     * @throws Exception
     */
    void editMergeRegect(PAgentMerge pAgentMerge) throws Exception;

    /**
     * 附代理商是否已经存在
     * */
    List<PAgentMerge> selectBySubAgenId(String subAgentId);

    /**
     * 手动更改手刷、POS代理商名称
     * @param insid
     */
    AgentResult updateAgentName(String insid) throws Exception;
}
