package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;

import java.util.List;
import java.util.Map;

/**
 * @Author cl
 * @Date 2020/6/10 16:40
 * @Version 1.0
 */
public interface TerminalTransferFactory {

    /**
     * 平台校验
     * @param terminalTransferDetail
     */
    void  platformSameCheck(TerminalTransferDetail terminalTransferDetail) throws MessageException;
    /**
     * 划拨检验
     *
     * @param terminalTransferDetails
     */
    void check(List<TerminalTransferDetail> terminalTransferDetails)throws MessageException;

    /**
     * 终端划拨解锁
     *
     * @param taskId
     * @param serialNumber
     * @param type
     * @return
     */
     AgentResult terminalTransferunlock(String taskId, String serialNumber, String type);

    /**
     * 终端划拨重新划拨
     *
     * @param id 明细Id
     * @return
     */
     AgentResult adjustAgain(String id);

    /**
     * 划拨执行
     *
     * @param terminalTransferDetails
     */
    void adjust(List<TerminalTransferDetail> terminalTransferDetails)throws MessageException;

    /**
     * 获取划拨结果并更新
     * @param terminalTransferDetail
     * @throws Exception
     */
     String queryTerminalTransferResult(TerminalTransferDetail terminalTransferDetail ) throws Exception ;

}
