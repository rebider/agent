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
import com.ryx.credit.pojo.admin.order.OActivity;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import com.ryx.credit.service.order.OrderActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 作者：zyd
 * 时间：2019/8/13
 * 描述：瑞大宝 接口
 */
@Service("rdbTermMachineServiceImpl")
public class RDBPosTermMachineServiceImpl implements TermMachineService {

    private static Logger logger = LoggerFactory.getLogger(RDBPosTermMachineServiceImpl.class);

    @Autowired
    private OrderActivityService orderActivityService;

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
                logger.info("RDB退货查询下发参数:" + retJson);
                String retString = HttpClientUtil.doPostJsonWithException(AppConfig.getProperty("rdbpos.checkTermResult"), retJson);
                if (!StringUtils.isNotBlank(retString)) {
                    agentResult.setStatus(2);
                    agentResult.setMsg("RDB退货下发查询接口返回空！");
                    return agentResult;
                }

                JSONObject resJson = JSONObject.parseObject(retString);
                logger.info("RDB退货下发查询接口返回值:" + retString);
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

    /**
     * RDB换活动(查询，更换)
     * @param refundPriceDiffDetailList
     * @param operation
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult synOrVerifyCompensate(List<ORefundPriceDiffDetail> refundPriceDiffDetailList, String operation) throws Exception {

        //封装参数
        String currentAgencyId = refundPriceDiffDetailList.get(0).getAgentId();
        String taskId = refundPriceDiffDetailList.get(0).getRefundPriceDiffId();
        List<Map<String, Object>> reqList = new ArrayList<>();
        for (ORefundPriceDiffDetail refundPriceDiffDetail : refundPriceDiffDetailList) {

            //判断机构编不能为空
            if (null == refundPriceDiffDetail.getOldOrgId() || "".equals(refundPriceDiffDetail.getOldOrgId())) return AgentResult.fail("请输入正确的机构编码");
            if (null == refundPriceDiffDetail.getNewOrgId() || "".equals(refundPriceDiffDetail.getNewOrgId())) return AgentResult.fail("请输入正确的机构编码");

            Map<String, Object> reqMap = new HashMap<>();
            //查询，新旧活动代码
            OActivity oldActivity = orderActivityService.findById(refundPriceDiffDetail.getActivityFrontId());
            OActivity newActivity = orderActivityService.findById(refundPriceDiffDetail.getActivityRealId());
            reqMap.put("terminalNoStart", refundPriceDiffDetail.getBeginSn());
            reqMap.put("terminalNoEnd", refundPriceDiffDetail.getEndSn());
            reqMap.put("terminalPolicyId", newActivity.getBusProCode());
            reqMap.put("oldTerminalPolicyId", oldActivity.getBusProCode());
            reqMap.put("currentBranchId", refundPriceDiffDetail.getOldOrgId().substring(refundPriceDiffDetail.getOldOrgId().length()-8));
            reqMap.put("currentAgencyId", refundPriceDiffDetail.getOldOrgId());
            reqList.add(reqMap);
        }

        try {
            String httpString = JSONObject.toJSONString(FastMap.fastMap("taskId", taskId)
                    .putKeyV("needCheck", !"adjust".equals(operation))//如果是调整不传递true
                    .putKeyV("terminalNos", reqList));
            logger.info("RDB换活动查询参数:{}", httpString);

            //查询是否可以更换活动
            String retString = HttpClientUtil.doPostJsonWithException(AppConfig.getProperty("rdbpos.checkActivity"), httpString);
            logger.info("RDB换活动查询返回值:{}", retString);

            //验证返回值
            if (!StringUtils.isNotBlank(retString)) return AgentResult.fail("RDB查询换活动接口，返回值为空。");
            JSONObject resJson = JSONObject.parseObject(retString);

            //返回最终查询结果
            if (null != resJson.getString("code") && resJson.getString("code").equals("0000")) {
                //可以更换活动
                return  AgentResult.ok();
            } else if (null != resJson.getString("code") && resJson.getString("code").equals("9999") && null != resJson.getString("msg")) {
                //不可以更换活动
                return AgentResult.fail(resJson.getString("msg") + "，不可以更换活动！");
            } else {
                //异常结果
                return AgentResult.fail("查询RDB换活动返回值异常！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 活动调整结果查询
     * @param serialNumber
     * @param platformType
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult queryCompensateResult(String serialNumber,String platformType) throws Exception {

        //查询taskID
        Map<String, Object> detailMap = orderActivityService.queryTaskIdForChangeActive(serialNumber);
        if (null == detailMap.get("REFUND_PRICE_DIFF_ID")) throw new Exception("查询退补差价明细失败！");

        try {
            String json = JSONObject.toJSONString(FastMap.fastMap("taskId", detailMap.get("REFUND_PRICE_DIFF_ID")));
            logger.info("RDB换活动查询结果请求:{}", json);
            String respResult = HttpClientUtil.doPostJsonWithException(AppConfig.getProperty("rdbpos.checkResult"), json);
            logger.info("RDB换活动查询结果返回:{}", respResult);

            if (!StringUtils.isNotBlank(respResult)) {
                logger.info("RDB换活动查询结果返回值为空");
                return AgentResult.ok("03");
            }

            JSONObject resJson = JSONObject.parseObject(respResult);
            if (null != resJson.getString("code") && resJson.getString("code").equals("0000") && null != resJson.getBoolean("success") && resJson.getBoolean("success")) {
                //处理成功
                logger.info("RDB活动调整成功:{} {}",serialNumber,platformType);
                return AgentResult.ok("00");
            } else if (null != resJson.getString("code") && resJson.getString("code").equals("2001") && null != resJson.getBoolean("success") && !resJson.getBoolean("success")) {
                //处理中
                logger.info("RDB活动调整中:{} {}",serialNumber,platformType);
                return AgentResult.ok("01");
            } else if (null != resJson.getString("code") && resJson.getString("code").equals("9999") && null != resJson.getBoolean("success") && !resJson.getBoolean("success") && null !=  resJson.getString("msg")) {
                //处理失败
                logger.info("RDB活动调整失败:{} {}",serialNumber,platformType);
                return AgentResult.ok("02");
            } else {
                return AgentResult.ok("03");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * RDB直接返回成功即可，POS平台用的校验
     * @param data
     * @param platformType
     * @return
     */
    @Override
    public boolean checkModleIsEq(Map<String, String> data, String platformType) {
        return true;
    }
}
