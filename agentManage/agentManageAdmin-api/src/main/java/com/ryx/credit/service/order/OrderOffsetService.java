package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;

import java.math.BigDecimal;
import java.util.List;

public interface OrderOffsetService {
    /**
     * 根据欠款明细与金额保存入库付款明细
     * @param opaymentDetailList
     * @param amount
     * @param OffsetPaytype
     * @param srcId
     * @return
     */
    AgentResult OffsetArrears(List<OPaymentDetail> opaymentDetailList, BigDecimal amount, String OffsetPaytype, String srcId) throws MessageException;

    /**
     * 根据欠款明细与金额更新入库付款明细
     * @param amount
     * @param OffsetPaytype
     * @param srcId
     * @return
     */
    AgentResult OffsetArrearsComit(BigDecimal amount, String OffsetPaytype, String srcId)throws MessageException;

    /**
     * 根据欠款明细与金额删除入库付款明细
     * @param amount
     * @param OffsetPaytype
     * @param srcId
     * @return
     */
    AgentResult OffsetArrearsCancle(BigDecimal amount, String OffsetPaytype, String srcId)throws MessageException;

    /**
     * 根据欠款明细与金额查询入库付款明细
     * @param OffsetPaytype
     * @param srcId
     * @return
     */
    AgentResult OffsetArrearsQuery(String OffsetPaytype, String srcId);
}
