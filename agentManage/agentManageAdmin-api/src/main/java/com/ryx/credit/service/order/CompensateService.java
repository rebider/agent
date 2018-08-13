package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiff;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.OSubOrder;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.ORefundPriceDiffVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/7/24.
 */
public interface CompensateService {

    ORefundPriceDiff selectByPrimaryKey(String id);

    PageInfo compensateList(ORefundPriceDiffVo refundPriceDiff, Page page);

    List<Map<String,Object>> getOrderMsgByExcel(List<Object> excelList,Long userId)throws ProcessException;

    BigDecimal calculateTotalPrice(String activityId, BigDecimal count);

    BigDecimal calculatePriceDiff(String beginSn,String endSn,String oldActivityId,String activityId,BigDecimal proNum);

    AgentResult compensateAmtSave(ORefundPriceDiff oRefundPriceDiff, List<ORefundPriceDiffDetail> refundPriceDiffDetailList,List<String> refundPriceDiffFile, String cUser);

    AgentResult startCompensateActiviy(String id, String cuser) throws Exception;

    AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception;

    AgentResult compressCompensateActivity(String proIns,BigDecimal agStatus);

    ORefundPriceDiff queryRefDiffDetail(String id);

    AgentResult updateTask(AgentVo agentVo,BigDecimal deductAmt);

    AgentResult compensateAmtEdit(ORefundPriceDiff oRefundPriceDiff, List<ORefundPriceDiffDetail> refundPriceDiffDetailList,List<String> refundPriceDiffFile, String cUser);
}

