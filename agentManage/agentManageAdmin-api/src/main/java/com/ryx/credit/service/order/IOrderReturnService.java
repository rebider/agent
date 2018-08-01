package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OReturnOrder;

import java.util.Map;

/**
 * @Author: Zhang Lei
 * @Description: 退货
 * @Date: 14:16 2018/7/25
 */
public interface IOrderReturnService {

    PageInfo orderList(OReturnOrder returnOrder, PageInfo page);

    Map<String, Object> apply(String agentId, OReturnOrder returnOrder, String productsJson,String userid) throws ProcessException;

    Map<String, Object> view(String returnId) throws ProcessException;

    Map<String, Object> saveCut(String returnId, String amt, String ctype);

    Map<String, Object> bizAudit(String returnId, String plans, String remark, String userid, String auditResult) throws ProcessException;

    Map<String, Object> cwAudit(String returnId, String remark, String userid, String auditResult, String[] attachments) throws ProcessException;
}
