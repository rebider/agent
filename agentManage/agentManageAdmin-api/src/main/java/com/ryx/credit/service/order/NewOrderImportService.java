package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.ImportAgent;
import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.OPayment;
import com.ryx.credit.pojo.admin.vo.NewOrderImportBaseInfo;
import java.util.List;

/**
 * Created by lhl on 2019/3/5.
 * 无SN订单解析
 */
public interface NewOrderImportService {

    /**
     * 解析订单数据
     * @param user
     * @return
     */
    public ResultVO newPareseOrderEnter(String user);


    /**
     * 解析退货订单数据
     * @param user
     * @return
     */
    public ResultVO newPareseReturnOrderEnter(String user);


    /**
     * 将Excel
     * @param data
     * @param dataType
     * @param user
     * @param batch
     * @return
     * @throws Exception
     */
    public List<String> newAddOrderInfoList(List<List<Object>> data, String dataType, String user, String batch)throws Exception;


    /**
     * 处理单个订单
     * @return
     * @throws Exception
     */
    public AgentResult newPareseOrder(ImportAgent importAgent, String User)throws MessageException;


    /**
     * 解析生成订单及付款单信息
     * @param newOrderImportBaseInfo
     * @param User
     * @return
     * @throws Exception
     */
    public AgentResult newPareseOrderImportBaseInfo(NewOrderImportBaseInfo newOrderImportBaseInfo, String User)throws MessageException;


    /**
     * 解析生成订单子订单，子订单活动信息
     * @param newOrderImportBaseInfo
     * @param User
     * @return
     * @throws Exception
     */
    public AgentResult newPareseOrderImportSubOrderInfo(NewOrderImportBaseInfo newOrderImportBaseInfo, OOrder order, OPayment oPayment, String User)throws MessageException;


    /**
     * 处理订单物流
     * 处理订单物流
     * @return
     * @throws Exception
     */
    public AgentResult newPareseOrderLogic(String value)throws MessageException;


    /**
     * 处理退货单
     * @return
     * @throws Exception
     */
    public AgentResult newPareseReturn(ImportAgent importAgent, String User)throws MessageException;


    public AgentResult newDeleteFailImportAgentReturn(ImportAgent importAgent, String user)throws Exception;


    public AgentResult newDeleteFailImportAgentOrder(ImportAgent importAgent, String user)throws Exception;
}
