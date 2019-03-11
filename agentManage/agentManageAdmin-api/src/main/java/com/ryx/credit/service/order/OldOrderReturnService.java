package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OldOrderReturnSubmitProVo;
import com.ryx.credit.pojo.admin.vo.OldOrderReturnVo;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 作者：cx
 * 时间：2019/3/6
 * 描述：
 */
public interface OldOrderReturnService {

    AgentResult saveOldReturnOrder(OldOrderReturnVo oldOrderReturnVo)throws Exception;

    /**
     * 加载审批界面数据
     * @param returnId
     * @return
     */
    public AgentResult loadOldOrderApproveData(String returnId);


    /**
     * 审批任务
     * @param agentVo
     * @param userId
     * @return
     */
    public AgentResult taskApprove(AgentVo agentVo,String userId)throws MessageException;


    /**
     * 抓取订单中指定的商品的信息，对业务订单审批界面进行补全
     * @param orderId
     * @param proId
     * @return
     */
    public AgentResult loadOldOrderReturnDetailInfo(String orderId,String proId);
}
