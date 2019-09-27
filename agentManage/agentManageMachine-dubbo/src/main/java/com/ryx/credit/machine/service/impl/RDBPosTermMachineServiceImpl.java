package com.ryx.credit.machine.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.*;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：zyd
 * 时间：2019/8/13
 * 描述：瑞大宝 接口
 */
@Service("rdbTermMachineServiceImpl")
public class RDBPosTermMachineServiceImpl implements TermMachineService {

    private static Logger logger = LoggerFactory.getLogger(RDBPosTermMachineServiceImpl.class);

    /**
     * 获取瑞大宝 相关数据具体操作
     * @param platformType
     * @return
     * @throws Exception
     */
    @Override
    public List<TermMachineVo> queryTermMachine(PlatformType platformType, Map<String, String> map) throws Exception {

        Map<String, String> reqMap = new HashMap<String, String>();

        // 封装参数
        if (null != map.get("busplatform")) {
            reqMap.put("branchId", map.get("busplatform"));
        } else {
            throw new MessageException("瑞大宝活动缺少必要参数");
        }

        JSONObject res = request(reqMap, AppConfig.getProperty("rdbpos.queryTermActive"));
        if(null!=res && "0000".equals(res.getString("code"))){

            JSONArray data = res.getJSONObject("result").getJSONArray("termPolicyList");
            List<TermMachineVo> resData = new ArrayList<TermMachineVo>();

            for (int i = 0; i < data.size(); i++) {
                JSONObject item =  data.getJSONObject(i);
                String name = item.getString("terminalPolicyName");
                String id = item.getString("terminalPolicyId");
                TermMachineVo machineVo =  new TermMachineVo();
                machineVo.setId(id);
                machineVo.setMechineName(name);
                resData.add(machineVo);
            }
            return resData;
        }else{
            AppConfig.sendEmails(res.getString("respMsg"), "瑞大宝活动异常");
            throw new MessageException(res.getString("respMsg"));
        }
    }

    /**
     * 瑞大宝活动调整
     * @param data
     * @param url
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject request(Map data, String url) throws Exception {
        try {
            String json = JsonUtil.objectToJson(data);
            String httpResult = HttpClientUtil.doPostJsonWithException(url, json);
            if(StringUtils.isNotBlank(httpResult)) {
                JSONObject respXMLObj = JSONObject.parseObject(httpResult);
                return respXMLObj;
            }else{
                AppConfig.sendEmails("错误信息："+httpResult, "瑞大宝接口异常");
                throw new Exception("请求出错");
            }
        } catch (Exception e) {
            e.printStackTrace();
            AppConfig.sendEmails("首刷发送请求失败："+ MailUtil.printStackTrace(e), "瑞大宝接口异常");
            throw e;
        }
    }

    @Override
    public List<MposTermBatchVo> queryMposTermBatch(PlatformType platformType) throws Exception {
        return null;
    }

    @Override
    public List<MposTermTypeVo> queryMposTermType(PlatformType platformType) throws Exception {
        return null;
    }

    @Override
    public AgentResult lowerHairMachine(LowerHairMachineVo lowerHairMachineVo) throws Exception {
        return null;
    }

    /**
     * 机具调整
     * @param adjustmentMachineVo
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult adjustmentMachine(AdjustmentMachineVo adjustmentMachineVo) throws Exception {

        AgentResult agentResult = new AgentResult();
        Map<String, Object> reqMap = adjustmentMachineVo.getParamMap();

        try {
            String json = JsonUtil.objectToJson(reqMap);
            logger.info("RDB退货下发请求参数:" + json);
            String respResult = HttpClientUtil.doPostJsonWithException(AppConfig.getProperty("rdbpos.returnOfGoods"), json);

            if (!StringUtils.isNotBlank(respResult)) {
                agentResult.setStatus(2);
                agentResult.setMsg("RDB退货下发接口返回空！");
                return agentResult;
            }
            logger.info("RDB退货下发返回参数:" + respResult);

            JSONObject respJson = JSONObject.parseObject(respResult);
            if (!(null != respJson.getString("code") && null != respJson.getString("success") && respJson.getString("code").equals("0000") && respJson.getBoolean("success"))) {
                agentResult.setStatus(2);
                agentResult.setMsg(null != respJson.getString("msg") ? respJson.getString("msg") : "RDB退货下发接口返回值异常！请联系管理员！");
                return agentResult;
            }

            //发送成功，查询结果
            try {
                String retJson = JSONObject.toJSONString(FastMap.fastMap("taskId", reqMap.get("taskId")));
                logger.info("RDB查询下发参数",retJson);
                String retString = HttpClientUtil.doPostJsonWithException(AppConfig.getProperty("rdbpos.checkTermResult"), retJson);
                if (!StringUtils.isNotBlank(retString)) {
                    agentResult.setStatus(2);
                    agentResult.setMsg("RDB退货下发查询接口返回空！");
                    return agentResult;
                }

                JSONObject resJson = JSONObject.parseObject(retString);
                logger.info("------------------------------------------>>>RDB退货下发查询接口返回值:" + retString);
                if (null != resJson.getString("code") && resJson.getString("code").equals("0000") && null != resJson.getBoolean("success") && resJson.getBoolean("success")) {
                    //处理成功
                    agentResult.setStatus(1);
                    agentResult.setMsg("联动成功");
                    return agentResult;
                } else if (null != resJson.getString("code") && resJson.getString("code").equals("2001") && null != resJson.getBoolean("success") && !resJson.getBoolean("success")) {
                    //处理中
                    agentResult.setStatus(0);
                    agentResult.setMsg("RDB退货下发，处理中");
                    return agentResult;
                } else if (null != resJson.getString("code") && resJson.getString("code").equals("9999") && null != resJson.getBoolean("success") && !resJson.getBoolean("success") && null !=  resJson.getString("msg")) {
                    //处理失败
                    agentResult.setStatus(2);
                    agentResult.setMsg(resJson.getString("result"));
                    return agentResult;
                } else {
                    throw new Exception("瑞大宝，查询下发接口，返回值不符合要求，请联系管理员！");
                }
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public AgentResult changeActMachine(ChangeActMachineVo changeActMachineVo) throws Exception {
        return null;
    }

    @Override
    public AgentResult querySnMsg(PlatformType platformType, String snBegin, String snEnd) throws Exception {
        return null;
    }

    @Override
    public AgentResult queryTerminalTransfer(List<TerminalTransferDetail> terminalTransferDetailList, String operation) throws Exception {
        return null;
    }

    @Override
    public AgentResult queryTerminalTransferResult(String serialNumber, String type) throws Exception {
        return null;
    }

    @Override
    public AgentResult synOrVerifyCompensate(List<ORefundPriceDiffDetail> refundPriceDiffDetailList, String operation) throws Exception {
        return  AgentResult.ok("未联动");
    }

    @Override
    public AgentResult queryCompensateResult(String serialNumber,String platformType) throws Exception {
        return AgentResult.ok("04");
    }
}
