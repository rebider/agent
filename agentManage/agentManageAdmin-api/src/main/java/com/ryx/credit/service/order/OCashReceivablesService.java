package com.ryx.credit.service.order;

import com.ryx.credit.common.enumc.CashPayType;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.order.OCashReceivables;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 作者：cx
 * 时间：2018/10/29
 * 描述：现款付款明细服务类
 */
public interface OCashReceivablesService {


    /**
     * 检查属性
     * @param oCashReceivables
     * @return
     * @throws Exception
     */
    public AgentResult check(OCashReceivables oCashReceivables)throws Exception;

    /**
     * 审批业务
     * @param cpt
     * @param srcId
     * @param userId
     * @return
     */
    public AgentResult approveTashBusiness(CashPayType cpt,
                                           String srcId,
                                           String userId,
                                           Date checkDate,List<OCashReceivablesVo> list)throws Exception;

    /**
     * 添加或修改现款收款明细
     * @param oCashReceivablesList
     * @return
     */
    public AgentResult addOCashReceivables(List<OCashReceivablesVo> oCashReceivablesList, String user, String agentId, CashPayType cpt, String srcId)throws Exception;


    /**
     * 启动审批中
     * @param cpt
     * @param srcId
     * @return
     */
    public AgentResult startProcing(CashPayType cpt,String srcId,String userId)throws Exception;

    /**
     * 拒绝审批
     * @param cpt
     * @param srcId
     * @return
     */
    public AgentResult refuseProcing(CashPayType cpt,String srcId,String userId)throws Exception;

    /**
     * 拒绝审批
     * @param cpt
     * @param srcId
     * @return
     */
    public AgentResult finishProcing(CashPayType cpt,String srcId,String userId)throws Exception;

    /**
     * 添加
     * @param oCashReceivables
     * @return
     */
    public AgentResult add(OCashReceivables oCashReceivables,String user)throws Exception;


    /**
     * 修改
     * @param oCashReceivables
     * @return
     */
    public AgentResult update(OCashReceivables oCashReceivables,String user)throws Exception;


    /**
     * 删除
     * @param oCashReceivables
     * @return
     */
    public AgentResult dele(OCashReceivables oCashReceivables,String user)throws Exception;


    /**
     * 查询现付
     * @param id
     * @param cpt
     * @param srcId
     * @param reviewStatus
     * @return
     */
    public List<OCashReceivables> query(String id,String agentId, CashPayType cpt, String srcId, List<BigDecimal> reviewStatus);

}
