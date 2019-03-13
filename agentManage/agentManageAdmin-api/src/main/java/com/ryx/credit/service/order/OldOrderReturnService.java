package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OldOrderReturnBusEditVo;
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

    /**
     * 业务部门完善信息
     * @param oldOrderReturnBusEditVos
     * @param user
     * @return
     * @throws Exception
     */
    public AgentResult completOldOrderReturnInfo(List<OldOrderReturnBusEditVo> oldOrderReturnBusEditVos, String user)throws Exception;

    /**
     * 物流上传处理
     * @param data
     * @param user
     * @return
     * @throws Exception
     */
    List<String> uploadSnFileList(List<List<Object>> data, String user) throws Exception;

    /**
     * 物流上传处理单个号码段
     * @param objectList
     * @param user
     * @return
     * @throws Exception
     */
    AgentResult uploadSnFileListItem(List<Object> objectList, String user) throws Exception;
}
