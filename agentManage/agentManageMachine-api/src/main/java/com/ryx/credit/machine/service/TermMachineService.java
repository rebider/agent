package com.ryx.credit.machine.service;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.machine.vo.*;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;

import java.util.List;
import java.util.Map;

/**
 * 作者：cx
 * 时间：2018/10/10
 * 描述：业务系统极具终端操作接口
 */
public interface TermMachineService {

    /**
     * 查询平台极具，手刷查询手刷活动，pos查询pos极具
     * @param platformType
     * @return
     */
    public List<TermMachineVo> queryTermMachine(PlatformType platformType,Map<String,String> par)throws Exception;


    /**
     * MPOS查询终端批次
     * @param platformType
     * @return
     */
    public List<MposTermBatchVo> queryMposTermBatch(PlatformType platformType)throws Exception;


    /**
     * MPOS查询终端类型
     * @param platformType
     * @return
     */
    public List<MposTermTypeVo> queryMposTermType(PlatformType platformType)throws Exception;

    /**
     * 机具的下发
     * @param lowerHairMachineVo
     * @return
     */
    public AgentResult lowerHairMachine(LowerHairMachineVo lowerHairMachineVo)throws Exception;


    /**
     * 机具的调整
     * @param adjustmentMachineVo
     * @return
     */
    public AgentResult adjustmentMachine(AdjustmentMachineVo adjustmentMachineVo )throws Exception;


    /**
     * 机具活动的变更
     * @param changeActMachineVo
     * @return
     */
    public AgentResult changeActMachine(ChangeActMachineVo changeActMachineVo)throws Exception;


    /**
     * 发送请求
     * @param data
     * @param url
     * @return
     * @throws Exception
     */
     JSONObject request(Map data, String url)throws Exception;


     AgentResult querySnMsg(PlatformType platformType,String snBegin,String snEnd)throws Exception;

    /**
     * 划拨调整查询
     *chenliang
     * @param terminalTransferDetailList
     * @return
     */
   AgentResult  queryTerminalTransfer(List<TerminalTransferDetail> terminalTransferDetailList,String operation,String taskId)throws Exception;

    /**
     * 查询划拨结果
     * chenliang
     * @param serialNumber
     * @return
     * @throws Exception
     */

    AgentResult queryTerminalTransferResult(String serialNumber,String type) throws Exception;


    AgentResult synOrVerifyCompensate(List<ORefundPriceDiffDetail> refundPriceDiffDetailList, String operation) throws ProcessException;


    AgentResult queryCompensateResult(String serialNumber,String platformType) throws Exception;

    /**
     * 校验机具是否可变更
     * @param data
     * @param platformType
     * @return
     */
    boolean checkModleIsEq(Map<String,String> data,String platformType);

    /**
     * 校验各个业务平台是否可以退转发,如果可以执行冻结操作
     * @param list
     * @return
     * @throws Exception
     */
    AgentResult checkOrderReturnSN(List<Map<String, Object>> list, String platformType) throws Exception;

    /**
     * 校验各个业务平台是否可以退转发,如果可以执行冻结操作
     * @param list
     * @return
     * @throws Exception
     */
    AgentResult unfreezeOrderReturnSN(List<Map<String, Object>> list, String platformType) throws Exception;

    /**
     * 查询物流发送结果（异步）
     * @param pamMap
     * @return
     * @throws Exception
     */
    AgentResult queryLogisticsResult(Map<String, Object> pamMap, String platformType) throws Exception;


    /**
     * 终端划拨解锁
     * @param taskId  总批次号
     * @param serialNumber  单个批次号
     * @return
     * @throws Exception
     */
    AgentResult terminalTransferunlock(String taskId, String serialNumber,String type) throws Exception;


    /**
     * 终端划拨重新发起
     * @param param
     * @return
     * @throws Exception
     */
    AgentResult terminalTransAgain(Map<String,Object> param) throws Exception;


    /**
     * 判断代理商是否被禁用
     * @param pamMap
     * @param platformType
     * @return
     * @throws Exception
     */
    AgentResult agentFNoorbidde (List<String> pamMap, String platformType) throws Exception;
}
