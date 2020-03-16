package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiff;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.OSubOrder;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.credit.pojo.admin.vo.ORefundPriceDiffVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/7/24.
 */
public interface CompensateService {

    ORefundPriceDiff selectByPrimaryKey(String id);

    PageInfo compensateList(ORefundPriceDiffVo refundPriceDiff, Page page, String dataRole ,long userId);

    List<Map<String,Object>> getOrderMsgByExcel(List<Object> excelList,Long userId,String agentId)throws ProcessException;

    BigDecimal calculateTotalPrice(String activityId, BigDecimal count);

    BigDecimal calculatePriceDiff(String beginSn,String endSn,String oldActivityId,String activityId,BigDecimal proNum,ORefundPriceDiff oRefundPriceDiff);

    AgentResult compensateAmtSave(ORefundPriceDiff oRefundPriceDiff, List<ORefundPriceDiffDetail> refundPriceDiffDetailList,List<String> refundPriceDiffFile, String cUser ,List<OCashReceivablesVo> oCashReceivablesVoList,AgentVo agentVo)throws ProcessException;

    AgentResult startCompensateActiviy(String id, String cuser) throws Exception;

    AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException;

    AgentResult compressCompensateActivity(String proIns,BigDecimal agStatus)throws Exception;

    ORefundPriceDiff queryRefDiffDetail(String id) throws MessageException;

    AgentResult updateTask(AgentVo agentVo,BigDecimal deductAmt,String userId,List<OCashReceivablesVo> cashReceivablesVoList)throws Exception;

    AgentResult compensateAmtEdit(ORefundPriceDiff oRefundPriceDiff, List<ORefundPriceDiffDetail> refundPriceDiffDetailList,List<String> refundPriceDiffFile, String cUser, List<OCashReceivablesVo> cashReceivablesVoList);

    AgentResult compensateAmtDel(String busId, String cUser) throws Exception;

    PageInfo compensateDetailList(ORefundPriceDiffDetail refundPriceDiffDetail, Page page, String dataRole,long userId);

    void manualDispose(String id)throws ProcessException;

    /**
     * 查询处理中的明细去业务平台查询处理结果
     * @return
     */
    List<String> querySendingOrefundPriceDiffDetail();

    /**
     * 接口调用获取返回结果
     * @param id
     * @return
     */
    AgentResult dealQeruySendingReault(String id)throws Exception;

    PageInfo exportRefundPriceDiff(ORefundPriceDiffVo refundPriceDiff, PageInfo pageInfo, Boolean isPlan);

    PageInfo exportRefundPriceDiffDetail(ORefundPriceDiffDetail refundPriceDiffDetail, PageInfo pageInfo, Boolean isPlan);
    AgentResult updateoRefundPriceDiff(ORefundPriceDiff oRefundPriceDiff);

}

