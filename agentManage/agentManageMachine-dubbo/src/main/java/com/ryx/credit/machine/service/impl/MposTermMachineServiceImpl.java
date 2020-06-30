package com.ryx.credit.machine.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.*;
import com.ryx.credit.pojo.admin.order.OActivity;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import com.ryx.credit.service.order.OrderActivityService;
import org.apache.commons.collections.FastHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：cx
 * 时间：2018/10/10
 * 描述：手刷极具相关接口
 */
@Service("mposTermMachineServiceImpl")
public class MposTermMachineServiceImpl implements TermMachineService {

    public static final String MPOS_SUCESS_respCode = "000000";//000000-成功，000002-系统报错，000003-缺失参数，000004-其他, 100000-失败
    public static final String MPOS_SUCESS_respType = "S";//S-成功，E-报错，R-重复请求
    private Logger logger = LoggerFactory.getLogger(MposTermMachineServiceImpl.class);

    @Autowired
    private OrderActivityService orderActivityService;

    @Override
    public List<TermMachineVo> queryTermMachine(PlatformType platformType,Map<String,String> par) throws Exception{
        JSONObject res = request(FastMap.fastSuccessMap(), AppConfig.getProperty("mpos.queryTermActive"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            JSONArray data =  res.getJSONArray("data");
            List<TermMachineVo> resData = new ArrayList<TermMachineVo>();
            for (int i = 0; i < data.size(); i++) {

                JSONObject item =  data.getJSONObject(i);
                String name = item.getString("termActiveName");
                String id = item.getString("termActiveId");

                TermMachineVo machineVo =  new TermMachineVo();
                machineVo.setId(id);
                machineVo.setMechineName(name);
                resData.add(machineVo);

            }
            return resData;
        }else{
            AppConfig.sendEmails(res.getString("respMsg"), "机具划拨通知手刷失败报警");
            throw new MessageException(res.getString("respMsg"));
        }

    }

    @Override
    public List<MposTermBatchVo> queryMposTermBatch(PlatformType platformType) throws Exception{
        JSONObject res = request(FastMap.fastSuccessMap(), AppConfig.getProperty("mpos.queryTermBatch"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            JSONArray data =  res.getJSONArray("data");
            List<MposTermBatchVo> resData = new ArrayList<MposTermBatchVo>();
            for (int i = 0; i < data.size(); i++) {

                JSONObject item =  data.getJSONObject(i);
                String batchName = item.getString("batchName");
                String batchId = item.getString("batchId");
                String source = item.getString("source");

                MposTermBatchVo batchVo =  new MposTermBatchVo();
                batchVo.setBatchId(batchId);
                batchVo.setBatchName(batchName+"-"+source);
                resData.add(batchVo);

            }
            return resData;
        }else{
            AppConfig.sendEmails(res.getString("respMsg"), "机具划拨通知手刷失败报警");
            throw new MessageException(res.getString("respMsg"));
        }
    }

    @Override
    public List<MposTermTypeVo> queryMposTermType(PlatformType platformType) throws Exception{
        JSONObject res = request(FastMap.fastSuccessMap(), AppConfig.getProperty("mpos.queryTermType"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            JSONArray data =  res.getJSONArray("data");
            List<MposTermTypeVo> resData = new ArrayList<MposTermTypeVo>();
            for (int i = 0; i < data.size(); i++) {

                JSONObject item =  data.getJSONObject(i);
                String name = item.getString("termTypeName");
                String id = item.getString("termTypeId");

                MposTermTypeVo termTypeVo =  new MposTermTypeVo();
                termTypeVo.setTermTypeId(id);
                termTypeVo.setTermTypeName(name);
                resData.add(termTypeVo);

            }
            return resData;
        }else{
            AppConfig.sendEmails(res.getString("respMsg"), "机具划拨通知手刷失败报警");
            throw new MessageException(res.getString("respMsg"));
        }
    }


    /**
     * 机具的下发
     * @param lowerHairMachineVo
     * @return
     */
    @Override
    public AgentResult lowerHairMachine(LowerHairMachineVo lowerHairMachineVo) throws Exception{
        logger.info("Mpos机具的下发:");
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(lowerHairMachineVo));
        JSONObject res = request(data, AppConfig.getProperty("mpos.lowerHairMachine"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            logger.info("Mpos机具的调整，下发:{}{}{}",AppConfig.getProperty("mpos.lowerHairMachine"),res.toJSONString(),res.getString("respMsg"));
            return AgentResult.ok();
        }else{
            AppConfig.sendEmails(res.getString("respMsg"), "机具划拨通知手刷失败报警");
            throw new MessageException(res.getString("respMsg"));
        }
    }

    /**
     * 机具的调整，，退货是使用
     * @param adjustmentMachineVo
     * @return
     */
    @Override
    public AgentResult adjustmentMachine(AdjustmentMachineVo adjustmentMachineVo) throws Exception{
        logger.info("Mpos机具的调整，退货是使用");
        adjustmentMachineVo.setPlatformNum(adjustmentMachineVo.getPlatformNum());
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(adjustmentMachineVo));
        JSONObject res = request(data, AppConfig.getProperty("mpos.adjustmentMachine"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            logger.info("Mpos机具的调整，退货是使用:{}{}{}",AppConfig.getProperty("mpos.adjustmentMachine"),res.toJSONString(),res.getString("respMsg"));
            return AgentResult.ok();
        }else{
            AppConfig.sendEmails(res.getString("respMsg"), "机具划拨通知手刷失败报警");
            throw new MessageException(res.getString("respMsg"));
        }
    }

    /**
     * 机具活动的变更
     * @param changeActMachineVo
     * @return
     */
    @Override
    public AgentResult changeActMachine(ChangeActMachineVo changeActMachineVo) throws Exception{
        changeActMachineVo.setLogisticsDetailList(null);
        logger.info("Mpos机具的调整，，机具活动的变更");
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(changeActMachineVo));
        JSONObject res = request(data, AppConfig.getProperty("mpos.changeActMachine"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            logger.info("Mpos机具的调整，机具活动的变更:{}{}{}",AppConfig.getProperty("mpos.changeActMachine"),res.getString("respMsg"),res.toJSONString());
            return AgentResult.ok();
        }else{
            AppConfig.sendEmails(res.getString("respMsg"), "机具划拨通知手刷失败报警");
            throw new MessageException(res.getString("respMsg"));
        }
    }

    @Override
    public JSONObject request(Map data, String url) throws Exception {
        try {
            String json = JsonUtil.objectToJson(data);
            logger.info("通知手刷请求参数：{},{}",url,json);
            String httpResult = HttpClientUtil.doPostJsonWithException(url, json);
            logger.info("通知手刷返回参数：{},{}",url,httpResult);
            if(StringUtils.isNotBlank(httpResult)) {
                JSONObject respXMLObj = JSONObject.parseObject(httpResult);
                return respXMLObj;
            }else{
                AppConfig.sendEmails("错误信息："+httpResult, "机具划拨通知手刷失败报警");
                throw new Exception("请求出错");
            }
        } catch (Exception e) {
            logger.error("首刷发送请求失败：",e);
            e.printStackTrace();
            AppConfig.sendEmails("首刷发送请求失败："+ MailUtil.printStackTrace(e), "机具划拨通知手刷失败报警");
            throw e;
        }

    }


    @Override
    public AgentResult querySnMsg(PlatformType platformType,String snBegin,String snEnd)throws Exception{

        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("beginTermNum",snBegin);
        resultMap.put("endTermNum",snEnd);

        logger.info("老订单Mpos请求参数：{}",resultMap.toString());
        JSONObject res = request(resultMap, AppConfig.getProperty("mpos.oldChangeActMachine"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            logger.info("老订单Mpos机具查询:{}{}{}",AppConfig.getProperty("mpos.oldChangeActMachine"),res.getString("respMsg"),res.toJSONString());
            List<Map<String,Object>> termMachineListMap = (List<Map<String,Object>>) JSONArray.parse(String.valueOf(res.get("data")));
            return AgentResult.ok(termMachineListMap);
        }else{
            throw new MessageException(res.getString("respMsg"));
        }
    }

    @Override
    public AgentResult queryTerminalTransfer(List<TerminalTransferDetail> terminalTransferDetailList, String operation, String taskId) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operation", operation);
        jsonObject.put("taskId", taskId);

        List<Map<String, Object>> listDetail = new ArrayList<>();
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {
            Map<String, Object> mapDetail = new HashMap<>();
            mapDetail.put("agentOrgId", terminalTransferDetail.getOriginalOrgId());
            mapDetail.put("newOrgId", terminalTransferDetail.getGoalOrgId());
            mapDetail.put("posNum", String.valueOf(terminalTransferDetail.getSnCount()));
            mapDetail.put("posSnBegin", terminalTransferDetail.getSnBeginNum());
            mapDetail.put("posSnEnd", terminalTransferDetail.getSnEndNum());
            mapDetail.put("serialNumber", terminalTransferDetail.getId());
            mapDetail.put("num", String.valueOf(terminalTransferDetail.getComSnNum()));
            mapDetail.put("busPlatform", terminalTransferDetail.getPlatFrom());
            listDetail.add(mapDetail);
        }
        jsonObject.put("snList", listDetail);
        JSONObject res = null;
        if ("check".equals(operation)) {
            jsonObject.put("isfeeze", "1");
            logger.info("MPOS的请求参数==请求参数:{}", JSONObject.toJSON(jsonObject));
            res = request(jsonObject, AppConfig.getProperty("mpos.termMachine"));
        } else {
            logger.info("MPOS的请求参数==请求参数:{}", JSONObject.toJSON(jsonObject));
            res = request(jsonObject, AppConfig.getProperty("mpos.termMachine"));
        }

        if (null != res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))) {
            logger.info("机具终端划拨通知查询成功:{}{}{}", AppConfig.getProperty("mpos.termMachine"), res.getString("respMsg"), res.toJSONString());
            List<Map<String, Object>> codeType = (List<Map<String, Object>>) JSONArray.parse(String.valueOf(res.get("data")));
            return AgentResult.ok(codeType);
        } else {
            logger.info("机具终端划拨通知查询失败:{}{}{}", AppConfig.getProperty("mpos.termMachine"), res.getString("respMsg"), res.toJSONString());
            return AgentResult.fail(res.getString("respMsg"));
        }
    }

    @Override
    public AgentResult queryTerminalTransferResult(String serialNumber, String type) throws Exception {
        JSONObject data = new JSONObject();
        data.put("serialnumber", serialNumber);
        JSONObject res = null;
        try {
            res = request(data, AppConfig.getProperty("mpos.termMachineResult"));
        } catch (Exception e) {
            logger.info("MPOS机具终端划拨查询结果调用远程接口失败:{}", AppConfig.getProperty("mpos.termMachineResult"));
            return AgentResult.fail("MPOS机具终端划拨查询结果调用远程接口失败");
        }
        if (null != res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))) {
            logger.info("MPOS机具终端划拨结果查询成功:{}{}{}", AppConfig.getProperty("mpos.termMachineResult"), res.getString("respMsg"), res.toJSONString());
            List<Map<String,Object>> codeType = (List<Map<String, Object>>) JSONArray.parse(String.valueOf(res.get("data")));
            return AgentResult.ok(codeType);
        } else {
            logger.info("MPOS机具终端划拨结果查询失败:{}{}{}", AppConfig.getProperty("mpos.termMachineResult"), res.getString("respMsg"), res.toJSONString());
            return AgentResult.fail(res.getString("respMsg"));
        }
    }

    /**
     * 终端划拨解锁
     *
     * @param taskId       总批次号
     * @param serialNumber 单个批次号
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult terminalTransferunlock(String taskId, String serialNumber, String type) throws Exception {
        JSONObject data = new JSONObject();
        data.put("taskId", taskId);
        data.put("serialNumber", serialNumber);
        logger.info("MPOS终端划拨解锁参数" + JSONObject.toJSON(data));
        JSONObject res = null;
        try {
            res = request(data, AppConfig.getProperty("mpos.terminalTransferunlock"));
        } catch (Exception e) {
            logger.info("MPOS机具终端划拨解锁调用远程接口:{}失败", AppConfig.getProperty("mpos.terminalTransferunlock"));
            return AgentResult.fail("MPOS机具终端划拨解锁调用远程接口失败");
        }
        logger.info("MPOS机具终端划拨解锁调用远程接口返回:{}{}", AppConfig.getProperty("mpos.terminalTransferunlock"), res.toJSONString());
        return AgentResult.build(200, res.toString(),res.toString());
    }

    /**
     *重新发起
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult terminalTransAgain(Map<String,Object>  param) throws Exception {

        JSONObject data = new JSONObject();
        data.put("taskId",param.get("taskId"));
        data.put("serialNumber",param.get("serialNumber"));
        data.put("posNum",param.get("posNum"));
        data.put("busPlatForm",param.get("busPlatForm"));
        logger.info("MPOS终端划拨重新发起参数"+JSONObject.toJSON(data));
        JSONObject res = null;
        try {
            res = request(data, AppConfig.getProperty("mpos.terminalTransferAgain"));
        } catch (Exception e) {
            logger.info("MPOS机具终端划拨重新发起调用远程接口:{}失败", AppConfig.getProperty("mpos.terminalTransferAgain"));
            return AgentResult.fail("MPOS机具终端划拨重新发起调用远程接口失败");
        }
        logger.info("MPOS机具终端划拨重新发起调用远程接口返回:{}{}", AppConfig.getProperty("mpos.terminalTransferAgain"), res.toJSONString());
        return AgentResult.build(200, res.toString(),res.toString());
    }

    @Override
    public AgentResult synOrVerifyCompensate(List<ORefundPriceDiffDetail> refundPriceDiffDetailList, String operation, String isFreeze) throws ProcessException {
        String taskId = refundPriceDiffDetailList.get(0).getRefundPriceDiffId();
        List<Map<String, Object>> reqList = new ArrayList<>();
        for (ORefundPriceDiffDetail refundPriceDiffDetail : refundPriceDiffDetailList) {

            //判断机构编不能为空
            if (null == refundPriceDiffDetail.getOldOrgId() || "".equals(refundPriceDiffDetail.getOldOrgId()))
                return AgentResult.fail("请输入正确的机构编码");
            if (null == refundPriceDiffDetail.getNewOrgId() || "".equals(refundPriceDiffDetail.getNewOrgId()))
                return AgentResult.fail("请输入正确的机构编码");

            Map<String, Object> reqMap = new HashMap<>();
            //查询，新旧活动代码
            OActivity oldActivity = orderActivityService.findById(refundPriceDiffDetail.getActivityFrontId());
            OActivity newActivity = orderActivityService.findById(refundPriceDiffDetail.getActivityRealId());
            reqMap.put("serialNumber", refundPriceDiffDetail.getId());
            reqMap.put("posSnEnd", refundPriceDiffDetail.getEndSn());
            reqMap.put("posSnBegin", refundPriceDiffDetail.getBeginSn());
            reqMap.put("oldOrgId", refundPriceDiffDetail.getOldOrgId());
            reqMap.put("newOrgId", refundPriceDiffDetail.getNewOrgId());
            reqMap.put("newMachineId", newActivity.getBusProCode());
            reqMap.put("oldMachineId", oldActivity.getBusProCode());
            reqMap.put("posType", newActivity.getPosType());
            if (null == newActivity.getStandTime()) {
                reqMap.put("standTime", "0");
            } else {
                reqMap.put("standTime",  newActivity.getStandTime().setScale(0, BigDecimal.ROUND_HALF_UP).toString());
            }
            if (null == newActivity.getStandTime()) {
                reqMap.put("deposit", "0");
            } else {
                reqMap.put("deposit", newActivity.getPosSpePrice().setScale(0,BigDecimal.ROUND_HALF_UP).toString());
            }
            reqList.add(reqMap);
        }

        try {
            String httpString = JSONObject.toJSONString(FastMap.fastMap("taskId", taskId)
                    .putKeyV("operation", operation)
                    .putKeyV("snList", reqList)
                    .putKeyV("isFreeze", isFreeze));


            logger.info("手刷换活动查询参数:{},{}", AppConfig.getProperty("mpos.termCheckAndExecution"), httpString);
            String retString = HttpClientUtil.doPostJson(AppConfig.getProperty("mpos.termCheckAndExecution"), httpString);
            logger.info("手刷换活动查询返回值:{}", retString);

            //验证返回值
            if (!StringUtils.isNotBlank(retString)) return AgentResult.fail("手刷查询换活动接口，返回值为空。");
            JSONObject resJson = JSONObject.parseObject(retString);

            //返回最终查询结果
            if (null != resJson.getString("resultCode") && resJson.getString("resultCode").equals("000000")) {
                //可以更换活动，封装参参数返回
                return AgentResult.ok(resJson.get("resultList"));
            } else if (null != resJson.getString("resultCode") && resJson.getString("resultCode").equals("100007") && null != resJson.getString("resultMsg")) {
                //不可以更换活动
                return AgentResult.fail(resJson.getString("resultMsg") + "，不能调整活动！");
            } else {
                //异常结果
                return AgentResult.fail("手刷活动调整校验返回值异常！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public AgentResult queryCompensateResult(Map<String, Object> map, String platformType) throws Exception {

        logger.info("手刷活动调整结果查询参数:{},{}", AppConfig.getProperty("mpos.termCheckAndExecutionInquire"), JSONObject.toJSONString(map));
        String httpResult = HttpClientUtil.doPostJson(AppConfig.getProperty("mpos.termCheckAndExecutionInquire"), JSONObject.toJSONString(map));
        logger.info("手刷活动调整结果查询返回参数:{}", httpResult);

        JSONObject resJsonObj = JSONObject.parseObject(httpResult);
        if (resJsonObj != null) {
            String resultCode = resJsonObj.getString("resultCode");
            String resultMsg = resJsonObj.getString("resultMsg");
            String serialNumber = resJsonObj.getString("serialNumber");
            String taskId = resJsonObj.getString("taskId");
            if ("00".equals(resultCode)) {
                //调整成功
                logger.info("活动调整成功:{} {}", serialNumber, platformType);
                return AgentResult.ok("00");
            } else if ("01".equals(resultCode)) {
                //调整中
                logger.info("活动调整中:{} {}", serialNumber, platformType);
                AgentResult result = AgentResult.ok("01");
                result.setMsg(resultMsg);
                return result;
            } else if ("02".equals(resultCode)) {
                //调整失败
                logger.info("活动调整失败:{} {}", serialNumber, platformType);
                AgentResult result = AgentResult.ok("02");
                result.setMsg(resultMsg);
                return result;
            } else {
                //未知结果
                AgentResult result = AgentResult.ok("03");
                result.setMsg(resultMsg);
                return result;
            }
        } else {
            //未知结果
            return AgentResult.ok("03");
        }
    }

    @Override
    public boolean checkModleIsEq(Map<String, String> data, String platformType) {
        return true;
    }

    @Override
    public AgentResult checkOrderReturnSN(List<Map<String, Object>> list, String platformType) throws Exception {
        return AgentResult.ok();
    }

    @Override
    public AgentResult unfreezeOrderReturnSN(List<Map<String, Object>> list, String platformType) throws Exception {
        return AgentResult.ok();
    }

    @Override
    public AgentResult queryLogisticsResult(Map<String, Object> pamMap, String platformType) throws Exception {
        return null;
    }

    @Override
    public AgentResult unFreezeCompensate(Map<String, Object> pamMap, String platformType) throws Exception {
        return AgentResult.ok();
        /*try {
            String httpString = JSONObject.toJSONString(pamMap);
            logger.info("手刷换活动解锁参数:{},{}", AppConfig.getProperty("mpos.termUnlock"), httpString);
            String retString = HttpClientUtil.doPostJson(AppConfig.getProperty("mpos.termUnlock"), httpString);
            logger.info("手刷换活动解锁返回值:{}", retString);

            //验证返回值
            if (!StringUtils.isNotBlank(retString)) return AgentResult.fail("手刷解锁换活动接口，返回值为空。");
            JSONObject resJson = JSONObject.parseObject(retString);

            //返回最终查询结果
            if (null != resJson.getString("code") && resJson.getString("code").equals("0000")) {
                //可以更换活动，封装参参数返回
                return AgentResult.ok(resJson.get("result"));
            } else if (null != resJson.getString("code") && resJson.getString("code").equals("9999") && null != resJson.getString("msg")) {
                //不可以更换活动
                return AgentResult.fail(resJson.getString("msg") + "，解锁失败");
            } else {
                //异常结果
                return AgentResult.fail("查询手刷换活动返回值异常！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }*/
    }

    @Override
    public AgentResult resendFailedCompensate(Map<String, Object> pamMap, String platformType) throws Exception {
        return AgentResult.fail("手刷平台暂不支持重新发送！");
        /*try {
            String httpString = JSONObject.toJSONString(pamMap);
            logger.info("手刷换活动失败重新发送参数:{},{}", AppConfig.getProperty("mpos.termUnlock"), httpString);
            String retString = HttpClientUtil.doPostJson(AppConfig.getProperty("mpos.termUnlock"), httpString);
            logger.info("手刷换活动失败重新发送返回值:{}", retString);

            //验证返回值
            if (!StringUtils.isNotBlank(retString)) return AgentResult.fail("手刷换活动失败重新发送，返回值为空。");
            JSONObject resJson = JSONObject.parseObject(retString);

            //返回最终查询结果
            if (null != resJson.getString("code") && resJson.getString("code").equals("00")) {
                //可以更换活动，封装参参数返回
                return AgentResult.ok(resJson.get("result"));
            } else if (null != resJson.getString("code") && resJson.getString("code").equals("9999") && null != resJson.getString("msg")) {
                //不可以更换活动
                return AgentResult.fail(resJson.getString("msg") + "，不可以更换活动！");
            } else {
                //异常结果
                return AgentResult.fail("查询手刷换活动返回值异常！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }*/
    }

    @Override
    public AgentResult agentFNoorbidde(List<String> pamMap, String platformType) throws Exception {
        return null;
    }
}
