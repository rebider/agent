package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.order.OInvoice;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.credit.pojo.admin.order.OReturnOrder;
import com.ryx.credit.pojo.admin.vo.AgentVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: Zhang Lei
 * @Description: 退货
 * @Date: 14:16 2018/7/25
 */
public interface IOrderReturnService {

    PageInfo orderList(OReturnOrder returnOrder, PageInfo page);

    Map<String, Object> apply(String agentId, OReturnOrder returnOrder, String productsJson,String userid,String invoiceList) throws ProcessException;

    Map<String, Object> applyEdit(String agentId, OReturnOrder returnOrder, String productsJson,String userid,String invoiceList) throws ProcessException;

    Map<String, Object> view(String returnId) throws ProcessException;

    Map<String, Object> saveCut(String returnId, String amt, String ctype);

    Map<String, Object> delCut(String returnId,String cutId,String userid)throws ProcessException;



    AgentResult approvalTaskAjustPeople(OReturnOrder oReturnOrder) throws ProcessException;

    AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException;

    void doPlan(String returnId, BigDecimal takeAmt, String userid)throws Exception;


    void approvalReject(String processInstanceId, String activityName)throws Exception;

    void approvalFinish(String processInstanceId, String activityName)throws Exception;

    /**
     * 退货列表及导出
     * @param param
     * @param pageInfo
     * @return
     */
    PageInfo orderReturnList(Map<String, Object> param, PageInfo pageInfo);


    List<String> addList(List<List<Object>> data, String user) throws Exception;
    AgentResult addListItem(List<Object> objectList, String user) throws Exception;

    AgentBusInfo queryBusInfoByLogDetail(OLogisticsDetail oLogisticsDetail);

    Map selectByReturnDeId(String returnDetailsId);

    /**
     * 退货发货
     * @param lgcId
     * @param userId
     * @return
     * @throws Exception
     */
    public AgentResult sendReturnLgcInfoToBusSystem(String lgcId,String userId)throws Exception ;


    public List<OInvoice> findInvoiceById(String id);
}
