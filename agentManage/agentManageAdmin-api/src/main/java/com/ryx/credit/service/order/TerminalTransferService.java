package com.ryx.credit.service.order;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.TerminalTransfer;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import com.ryx.credit.pojo.admin.vo.AgentVo;

import java.math.BigDecimal;

/**
 * Created by RYX on 2018/12/20.
 */
public interface TerminalTransferService {

    PageInfo terminalTransferList(TerminalTransfer terminalTransfer, Page page);

    PageInfo terminalTransferDetailList(TerminalTransferDetail terminalTransferDetail, Page page);

    AgentResult startTerminalTransferActivity(String id, String cuser, String agentId) throws Exception;

    AgentResult approvalTerminalTransferTask(AgentVo agentVo, String userId) throws Exception;

    AgentResult compressTerminalTransferActivity(String proIns, BigDecimal agStatus)throws Exception;
}
