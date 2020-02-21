package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.order.OInvoice;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.credit.pojo.admin.order.OReturnOrder;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.ReturnOrderVo;

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

    OReturnOrder selectById(String id);

    Map<String, Object> apply(String agentId, OReturnOrder returnOrder, String productsJson,String userid,String invoiceList) throws ProcessException;

    Map<String, Object> applyEdit(String agentId, OReturnOrder returnOrder, String productsJson,String userid,String invoiceList) throws ProcessException;

    Map<String, Object> view(String returnId) throws ProcessException;

    Map<String, Object> saveCut(String returnId, String amt, String ctype) throws MessageException;

    Map<String, Object> delCut(String returnId,String cutId,String userid)throws ProcessException;

    public AgentResult savePlans(AgentVo agentVo, String userid)throws Exception;


    AgentResult approvalTaskAjustPeople(OReturnOrder oReturnOrder) throws ProcessException;

    AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException;

    void doPlan(String returnId, BigDecimal takeAmt, String userid)throws Exception;


    void approvalReject(String processInstanceId, String activityName)throws Exception;

    void approvalFinish(String processInstanceId, String activityName)throws Exception;

    /**
     * 查询退货所有数据&查询代理商退货数据
     * @param param
     * @param pageInfo
     * @return
     */
    PageInfo orderReturnList(Map<String, Object> param, PageInfo pageInfo);

    /**
     * 查询省区退货数据
     * @param page
     * @param map
     * @param userId
     * @return
     */
    PageInfo queryOrderReturnList(Page page, Map map, Long userId);

    List<String> addList(List<List<Object>> data, String user) throws Exception;
    AgentResult addListItem(List<Object> objectList, String user) throws Exception;

    AgentBusInfo queryBusInfoByLogDetail(OLogisticsDetail oLogisticsDetail);

    Map selectByReturnDeId(String returnDetailsId);
    public AgentResult checkRecordPlan(List<Object> excel,Map<String,Object> db);


    public AgentResult saveAttachments(AgentVo agentVo, String userid);
    /**
     * 退货发货
     * @param lgcId
     * @param userId
     * @return
     * @throws Exception
     */
    public AgentResult sendReturnLgcInfoToBusSystem(String lgcId,String userId)throws Exception ;


    public List<OInvoice> findInvoiceById(String id);


    BigDecimal selectOrderDetails(String orderId);


    List<ReturnOrderVo> exportRetForDetail(Map map);

    Map<String, Object> deductDetail(String returnId) throws ProcessException;
}
