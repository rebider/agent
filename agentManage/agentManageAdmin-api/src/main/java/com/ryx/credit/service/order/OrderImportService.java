package com.ryx.credit.service.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.agent.ImportAgent;
import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.OPayment;
import com.ryx.credit.pojo.admin.vo.OrderImportBaseInfo;
import com.ryx.credit.pojo.admin.vo.OrderImportReturnInfo;

import java.util.List;

/**
 * 作者：cx
 * 时间：2019/1/29
 * 描述：订单系统老订单导入功能
 */
public interface OrderImportService {






    /**
     * 将Excel
     * @param data
     * @param dataType
     * @param user
     * @param batch
     * @return
     * @throws Exception
     */
    public List<String> addOrderInfoList(List<List<Object>> data, String dataType, String user, String batch)throws Exception;


    /**
     * 处理单个订单
     * @return
     * @throws Exception
     */
    public AgentResult pareseOrder(ImportAgent importAgent,String User)throws MessageException;

    /**
     * 接卸订单对象
     * @param orderImportBaseInfo
     * @param User
     * @return
     * @throws Exception
     */
    public AgentResult pareseOrderImportBaseInfo(OrderImportBaseInfo orderImportBaseInfo, String User)throws MessageException;

    /**
     * 解析生成订单子订单，子订单活动信息
     * @param orderImportBaseInfo
     * @param User
     * @return
     * @throws Exception
     */
    public AgentResult pareseOrderImportSubOrderInfo(OrderImportBaseInfo orderImportBaseInfo, OOrder order, OPayment oPayment, String User)throws MessageException;



    /**
     * 处理订单物流
     * 处理订单物流
     * @return
     * @throws Exception
     */
    public AgentResult pareseOrderLogic(String value)throws MessageException;

    /**
     * 处理退货单
     * @return
     * @throws Exception
     */
    public AgentResult pareseReturn(ImportAgent importAgent,String User)throws MessageException;





}
