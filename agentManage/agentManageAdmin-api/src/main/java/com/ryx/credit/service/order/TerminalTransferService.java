package com.ryx.credit.service.order;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.TerminalTransfer;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;

/**
 * Created by RYX on 2018/12/20.
 */
public interface TerminalTransferService {

    PageInfo terminalTransferList(TerminalTransfer terminalTransfer, Page page);

    PageInfo terminalTransferDetailList(TerminalTransferDetail terminalTransferDetail, Page page);


}
