package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiff;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.OSubOrder;
import com.ryx.credit.pojo.admin.order.OSubOrderActivity;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2019/3/13.
 */
public interface OldCompensateService {

    List<Map<String,Object>> getOrderMsgByExcel(List<List<Object>> excelList,String agentId)throws MessageException;

    AgentResult compensateAmtSave(ORefundPriceDiff oRefundPriceDiff, List<ORefundPriceDiffDetail> refundPriceDiffDetailList,
                                  List<String> refundPriceDiffFile, String cUser, List<OCashReceivablesVo> oCashReceivablesVoList,AgentVo agentVo)throws ProcessException;

    AgentResult startCompensateActiviy(String id, String cuser) throws Exception;

    OSubOrderActivity queryActivity(String subOrderId)throws MessageException;

    List<OSubOrder> queryOrder(String orderId)throws MessageException;

    BigDecimal calculatePriceDiff(String oldActivityId, String activityId, BigDecimal proNum)throws Exception;

    AgentResult compressCompensateActivity(String proIns, BigDecimal agStatus)throws Exception;

    AgentResult compensateAmtEdit(ORefundPriceDiff oRefundPriceDiff, List<ORefundPriceDiffDetail> refundPriceDiffDetailList,
                                  List<String> refundPriceDiffFile, String cUser, List<OCashReceivablesVo> cashReceivablesVoList);

}
