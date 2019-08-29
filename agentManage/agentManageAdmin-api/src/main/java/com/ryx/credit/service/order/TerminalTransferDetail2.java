package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;

import java.util.List;

public interface TerminalTransferDetail2 {
void  updateIsNoPay(List<TerminalTransferDetail> terminalTransferDetails, List<String> detailIds,String userId) throws MessageException;
}
