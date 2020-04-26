package com.ryx.credit.machine.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.BackType;
import com.ryx.credit.common.enumc.DeliveryTimeType;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.machine.dao.ImsPosMapper;
import com.ryx.credit.machine.dao.ImsTermWarehouseDetailMapper;
import com.ryx.credit.machine.dao.ImsTermWarehouseLogMapper;
import com.ryx.credit.machine.entity.*;
import com.ryx.credit.machine.service.ImsTermActiveService;
import com.ryx.credit.machine.service.ImsTermMachineService;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.*;
import com.ryx.credit.pojo.admin.order.OActivity;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import com.ryx.credit.service.agent.PlatFormService;
import com.ryx.credit.service.order.OrderActivityService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

/**
 * 作者：cx
 * 时间：2018/10/10
 * 描述：POS极具相关接口
 */
@Service("posTermMachineServiceImpl")
public class PosTermMachineServiceImpl  implements TermMachineService {

    private final static String ZHYY_CREATE_PERSON = AppConfig.getProperty("zhyy_create_person");
    private static Logger log = LoggerFactory.getLogger(PosTermMachineServiceImpl.class);
    @Resource(name = "imsTermMachineService")
    private ImsTermMachineService imsTermMachineService;
    @Autowired
    private ImsTermActiveService imsTermActiveService;
    @Autowired
    private ImsTermWarehouseDetailMapper imsTermWarehouseDetailMapper;
    @Autowired
    private ImsTermWarehouseLogMapper imsTermWarehouseLogMapper;
    @Autowired
    private ImsPosMapper imsPosMapper;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private OrderActivityService orderActivityService;


    @Override
    public List<TermMachineVo> queryTermMachine(PlatformType platformType,Map<String,String> par) throws Exception {
        List<ImsTermMachine> list = imsTermMachineService.selectByExample();
        List<TermMachineVo> termMachineVoList = new ArrayList<>();
        for (ImsTermMachine imsTermMachine : list) {
            TermMachineVo newvo = new TermMachineVo();
            newvo.setId(imsTermMachine.getMachineId());
            String model = imsTermMachine.getModel();
            ImsPos imsPos = imsPosMapper.selectByPrimaryKey(model);
            newvo.setMechineName("型号代码:" + model + "|厂商型号:" + imsPos.getPosModel() + "|价格:" + imsTermMachine.getPrice()
                    + "|达标金额:" + imsTermMachine.getStandAmt() + "|返还类型:" + BackType.getContentByValue(imsTermMachine.getBackType())
                    + "|备注:" + imsTermMachine.getRemark());
            newvo.setStandAmt(String.valueOf(imsTermMachine.getStandAmt()));
            newvo.setBackType(imsTermMachine.getBackType());
            termMachineVoList.add(newvo);
        }
        return termMachineVoList;
    }

    @Override
    public List<MposTermBatchVo> queryMposTermBatch(PlatformType platformType) throws Exception {
        return new ArrayList<>();
    }

    @Override
    public List<MposTermTypeVo> queryMposTermType(PlatformType platformType) throws Exception {
        return new ArrayList<>();
    }

    @Override
    public AgentResult lowerHairMachine(LowerHairMachineVo lowerHairMachineVo) {
        return null;
    }

    @Override
    public AgentResult adjustmentMachine(AdjustmentMachineVo adjustmentMachineVo)throws Exception {
        return null;
    }

    @Override
    public AgentResult changeActMachine(ChangeActMachineVo changeActMachine) throws MessageException {
        if(1==1) {
            log.info("POS平台调整活动走接口取消，手动下发，{}",changeActMachine);
            return AgentResult.fail();
        }

        List<OLogisticsDetail> logisticsDetailList = changeActMachine.getLogisticsDetailList();
        if (null == logisticsDetailList) {
            log.info("updateWarehouse请求参数错误1");
            throw new MessageException("请求参数错误");
        }
        if (logisticsDetailList.size() == 0) {
            log.info("updateWarehouse请求参数错误2");
            throw new MessageException("请求参数错误");
        }
        for (OLogisticsDetail oLogisticsDetail : logisticsDetailList) {
            ImsTermWarehouseDetail imsTermWarehouseDetail = new ImsTermWarehouseDetail();
            imsTermWarehouseDetail.setPosSn(oLogisticsDetail.getSnNum());
            imsTermWarehouseDetail.setMachineId(oLogisticsDetail.getBusProCode());
            imsTermWarehouseDetail.setStandTime(oLogisticsDetail.getStandTime());
            imsTermWarehouseDetail.setPosSpePrice(oLogisticsDetail.getPosSpePrice());
            ImsTermActive imsTermActive = imsTermActiveService.selectByPrimaryKey(imsTermWarehouseDetail.getPosSn());
            //有记录就表示已激活
            if (null != imsTermActive) {
                throw new MessageException("Sn机具已激活");
            }
            ImsTermWarehouseDetail queryImsTerm = imsTermWarehouseDetailMapper.selectByPrimaryKey(imsTermWarehouseDetail.getPosSn());
            if (queryImsTerm == null) {
                throw new MessageException("Sn机具不存在");
            }
            //未使用
            if (!queryImsTerm.getUseStatus().equals("1")) {
                throw new MessageException("Sn机具已使用");
            }
            int i = imsTermWarehouseDetailMapper.updateByPrimaryKeySelective(imsTermWarehouseDetail);
            if (i != 1) {
                throw new MessageException("SN机具变更更新失败");
            }
            ImsTermWarehouseLog imsTermWarehouseLog = new ImsTermWarehouseLog();
            imsTermWarehouseLog.setWdId(queryImsTerm.getWdId());
            imsTermWarehouseLog.setMachineId(oLogisticsDetail.getBusProCode());
            imsTermWarehouseLog.setPosSn(oLogisticsDetail.getSnNum());
            imsTermWarehouseLog.setOldWarehouseDetail(JsonUtil.objectToJson(queryImsTerm));
            queryImsTerm.setPosSn(oLogisticsDetail.getSnNum());
            queryImsTerm.setMachineId(oLogisticsDetail.getBusProCode());
            imsTermWarehouseLog.setNewWarehouseDetail(JsonUtil.objectToJson(queryImsTerm));
            imsTermWarehouseLog.setOperDescribe("修改");
            imsTermWarehouseLog.setOperType("1");  //修改
            imsTermWarehouseLog.setOperTime(DateUtil.format(new Date()));
            imsTermWarehouseLog.setOperUser(ZHYY_CREATE_PERSON);
            int j = imsTermWarehouseLogMapper.insert(imsTermWarehouseLog);
            if (j != 1) {
                throw new MessageException("SN机具变更插入日志失败");
            }
        }
        return AgentResult.ok();
    }

    @Override
    public JSONObject request(Map data, String url) throws Exception {
        return new JSONObject();
    }

    @Override
    public AgentResult querySnMsg(PlatformType platformType, String snBegin, String snEnd) throws Exception{
        JSONObject data = new JSONObject();
        data.put("posSnStart", snBegin);
        data.put("posSnEnd", snEnd);
        return request("ORG009", data);
    }


    private AgentResult request(String tranCode, JSONObject data) throws Exception {
        try {
            log.info("pos机具信息请求参数明文:{}", data);
            PrivateKey rsaPrivateKey = RSAUtil.getRSAPrivateKey(AppConfig.getProperty("industryAuth_local_private_key"), "pem", null, "RSA");
            PublicKey rsaPublicKey = RSAUtil.getRSAPublicKey(AppConfig.getProperty("industryAuth_cooper_public_key"), "pem", "RSA");
            String cooperator = AppConfig.getProperty("industryAuth_cooperator");
            String charset = "UTF-8"; // 字符集
//            String tranCode = "ORG009"; // 交易码
            String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
            String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间

            JSONObject jsonParams = new JSONObject();
            jsonParams.put("version", "1.0.0");
            jsonParams.put("msgType", "01");
            jsonParams.put("reqDate", reqDate);
            jsonParams.put("data", data);
            String plainXML = jsonParams.toString();
            // 请求报文加密开始
            String keyStr = AESUtil.getAESKey();
            byte[] plainBytes = plainXML.getBytes(charset);
            byte[] keyBytes = keyStr.getBytes(charset);
            String encryptData = new String(org.apache.commons.codec.binary.Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, rsaPrivateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.encrypt(keyBytes, rsaPublicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
            // 请求报文加密结束

            Map<String, String> map = new HashMap<>();
            map.put("encryptData", encryptData);
            map.put("encryptKey", encrtptKey);
            map.put("cooperator", cooperator);
            map.put("signData", signData);
            map.put("tranCode", tranCode);
            map.put("reqMsgId", reqMsgId);

            log.info("pos机具信息请求参数:{}", map);
            String httpResult = HttpClientUtil.doPost(AppConfig.getProperty("industryAuth_url"), map);
            JSONObject jsonObject = JSONObject.parseObject(httpResult);
            if (!jsonObject.containsKey("encryptData") || !jsonObject.containsKey("encryptKey")) {
                log.info("请求异常======" + httpResult);
                return AgentResult.fail("接口调用失败");
            } else {
                String resEncryptData = jsonObject.getString("encryptData");
                String resEncryptKey = jsonObject.getString("encryptKey");
                byte[] decodeBase64KeyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(resEncryptKey.getBytes(charset));
                byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, rsaPrivateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
                byte[] decodeBase64DataBytes = org.apache.commons.codec.binary.Base64.decodeBase64(resEncryptData.getBytes(charset));
                byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
                String respXML = new String(merchantXmlDataBytes, charset);
                log.info("pos机具信息返回参数：{}", respXML);

                // 报文验签
                String resSignData = jsonObject.getString("signData");
                byte[] signBytes = org.apache.commons.codec.binary.Base64.decodeBase64(resSignData);
                if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, rsaPublicKey, "SHA1WithRSA")) {
                    log.info("签名验证失败");
                } else {
                    log.info("签名验证成功");
                    JSONObject respXMLObj = JSONObject.parseObject(respXML);
                    String respCode = String.valueOf(respXMLObj.get("respCode"));
                    if (respCode.equals("000000")) {
                        return AgentResult.build(200, respXMLObj.toString(),respXMLObj.toString());
                    } else {
                        log.info("http请求超时返回错误:{}", respXML);
                        return AgentResult.fail(respXMLObj.toString());
                    }
                }
                return new AgentResult(500, "http请求异常", respXML);
            }
        } catch (Exception e) {
            log.info("通知失败:{}", e.getMessage());
            throw e;
        }
    }


    @Override
    public AgentResult queryTerminalTransfer(List<TerminalTransferDetail> terminalTransferDetailLists, String operation,String taskId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operation", operation);
        List<Map<String, Object>> listDetail = new ArrayList<>();
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailLists) {
            Map<String, Object> mapDetail = new HashMap<>();
            mapDetail.put("agentOrgId", terminalTransferDetail.getOriginalOrgId());
            mapDetail.put("newOrgId", terminalTransferDetail.getGoalOrgId());
            mapDetail.put("posNum", terminalTransferDetail.getSnCount());
            mapDetail.put("posSnBegin", terminalTransferDetail.getSnBeginNum());
            mapDetail.put("posSnEnd", terminalTransferDetail.getSnEndNum());
            mapDetail.put("serialNumber", terminalTransferDetail.getId());
            mapDetail.put("checkPayStatus", terminalTransferDetail.getIsNoPay());
            listDetail.add(mapDetail);
        }
        jsonObject.put("snList", listDetail);
        log.info("pos的请求参数==请求参数:{}",JSONObject.toJSON(jsonObject));
        return  request("ORG014", jsonObject);
    }


    @Override
    public AgentResult queryTerminalTransferResult(String serialNumber,String type) throws Exception{
        JSONObject data = new JSONObject();
        data.put("serialNumber", serialNumber);
        return request("ORG015", data);
    }


    @Override
    public AgentResult synOrVerifyCompensate(List<ORefundPriceDiffDetail> refundPriceDiffDetailList, String operation) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operation", operation);
        List<Map<String, Object>> listDetail = new ArrayList<>();
        for (ORefundPriceDiffDetail refundPriceDiffDetail : refundPriceDiffDetailList) {

            //校验新活动是否存在
            OActivity new_activity = orderActivityService.findById(refundPriceDiffDetail.getActivityRealId());
            if(new_activity==null){
                log.error("新活动未找到:{} snbegin:{} snend:{}",refundPriceDiffDetail.getActivityRealId(),refundPriceDiffDetail.getBeginSn(),refundPriceDiffDetail.getEndSn());
                throw new ProcessException("新活动未找到");
            }
            if(StringUtils.isBlank(new_activity.getPosType())){
                log.error("POS类型未配置:{} snbegin:{} snend:{}",refundPriceDiffDetail.getActivityRealId(),refundPriceDiffDetail.getBeginSn(),refundPriceDiffDetail.getEndSn());
                throw new ProcessException("POS类型未配置");
            }
            if(null==new_activity.getPosSpePrice() || BigDecimal.ZERO.compareTo(new_activity.getPosSpePrice())>0){
                log.error("特价机价格配置错误:{} snbegin:{} snend:{}",refundPriceDiffDetail.getActivityRealId(),refundPriceDiffDetail.getBeginSn(),refundPriceDiffDetail.getEndSn());
                throw new ProcessException("特价机价格配置错误");
            }
            if(null==new_activity.getStandTime() || BigDecimal.ZERO.compareTo(new_activity.getStandTime())>0){
                log.error("达标时间配置错误:{} snbegin:{} snend:{}",refundPriceDiffDetail.getActivityRealId(),refundPriceDiffDetail.getBeginSn(),refundPriceDiffDetail.getEndSn());
                throw new ProcessException("达标时间配置错误");
            }
            Map<String, Object> mapDetail = new HashMap<>();
            mapDetail.put("serialNumber", refundPriceDiffDetail.getId());
            mapDetail.put("newOrgId", refundPriceDiffDetail.getNewOrgId());
            mapDetail.put("oldOrgId", refundPriceDiffDetail.getOldOrgId());
            mapDetail.put("posSnBegin", refundPriceDiffDetail.getBeginSn());
            mapDetail.put("posSnEnd", refundPriceDiffDetail.getEndSn());
            mapDetail.put("reqPayStatus", "1");
            mapDetail.put("newMachineId", refundPriceDiffDetail.getNewMachineId());
            mapDetail.put("oldMachineId", refundPriceDiffDetail.getOldMachineId());
            mapDetail.put("oldBrandCode", refundPriceDiffDetail.getOldBrandCode());
            mapDetail.put("newBrandCode", refundPriceDiffDetail.getNewBrandCode());
            if(StringUtils.isNotBlank(refundPriceDiffDetail.getDeliveryTimeType())){
                mapDetail.put("deliveryTimeType", refundPriceDiffDetail.getDeliveryTimeType());
                if(refundPriceDiffDetail.getDeliveryTimeType().equals(DeliveryTimeType.ZERO.getValue())){
                    mapDetail.put("deliveryTime", refundPriceDiffDetail.getDeliveryTime());
                }else{
                    mapDetail.put("deliveryTime", "");
                }
                if(refundPriceDiffDetail.getDeliveryTimeType().equals(DeliveryTimeType.ONE.getValue())){
                    mapDetail.put("delayDay", refundPriceDiffDetail.getDelayDay());
                }else{
                    mapDetail.put("delayDay", "");
                }
            }

            //活动转换添加参数
            mapDetail.put("standTime", new_activity.getStandTime().setScale(0,BigDecimal.ROUND_HALF_UP).toString());
            mapDetail.put("posType", new_activity.getPosType());
            mapDetail.put("deposit", new_activity.getPosSpePrice().setScale(0,BigDecimal.ROUND_HALF_UP).toString());

            listDetail.add(mapDetail);
        }
        jsonObject.put("snList", listDetail);
        log.info("活动调整POS请求参数:{}",JSONObject.toJSON(jsonObject));
        AgentResult res = request("ORG016", jsonObject);
        if(res.isOK()) {
            JSONObject respXMLObj = JSONObject.parseObject(res.getMsg());
            JSONObject res_data = respXMLObj.getJSONObject("data");
            if (res_data != null && res_data.size() > 0) {
                if (!"000000".equals(res_data.getString("result_code")) && StringUtils.isNotBlank(res_data.getString("result_msg"))) {
                    log.info("http请求返回错误:{}", res_data.getString("result_msg"));
                    return AgentResult.fail(res_data.getString("result_msg"));
                } else {
                    return AgentResult.ok(res_data);
                }
            } else {
                if (StringUtils.isNotBlank(respXMLObj.getString("respMsg"))) {
                    log.info("http请求返回错误:{}", respXMLObj.getString("respMsg"));
                    return AgentResult.fail(respXMLObj.getString("respMsg"));
                } else {
                    log.info("http请求返回错误:{}", respXMLObj);
                    return AgentResult.fail("服务失败");
                }
            }
        }else{
            try {
                log.info("活动调整POS返回参数:{}", res.getMsg());
                JSONObject respXMLObj = JSONObject.parseObject(res.getMsg());
                JSONObject res_data = respXMLObj.getJSONObject("data");
                if (res_data != null && res_data.size() > 0) {
                    if (!"000000".equals(res_data.getString("result_code")) && StringUtils.isNotBlank(res_data.getString("result_msg"))) {
                        log.info("http请求返回错误:{}", res_data.getString("result_msg"));
                        return AgentResult.fail(res_data.getString("result_msg"));
                    } else {
                        return AgentResult.ok(res_data);
                    }
                } else {
                    if (StringUtils.isNotBlank(respXMLObj.getString("respMsg"))) {
                        log.info("http请求返回错误:{}", respXMLObj.getString("respMsg"));
                        return AgentResult.fail(respXMLObj.getString("respMsg"));
                    } else {
                        log.info("http请求返回错误:{}", respXMLObj);
                        return AgentResult.fail("服务失败");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("活动调整POS请求参数失敗:",e);
                return AgentResult.fail("服务失败");
            }
        }
    }


    @Override
    public AgentResult queryCompensateResult(String serialNumber,String platformType) throws Exception{
        JSONObject data = new JSONObject();
        data.put("serialNumber", serialNumber);
        log.info("活动调整结果查询返回：{},{}",serialNumber);
        AgentResult agentResult = request("ORG017", data);
        log.info("活动调整结果查询返回：{},{}",serialNumber,agentResult);
        if(agentResult.isOK()){
            String resmsg = agentResult.getMsg();
            if(resmsg!=null) {
                JSONObject res_obj = JSONObject.parseObject(resmsg) ;
                JSONObject res  = res_obj.getJSONObject("data");
                String result_code = res.getString("result_code");
                String snAdjStatus = res.getString("transferStatus");
                String serialNumber_res = res.getString("serialNumber");
                String resMsg = res.getString("resMsg");
                if(serialNumber.equals(serialNumber_res) && "00".equals(snAdjStatus) && "000000".equals(result_code)){
                     //调整成功
                     log.info("活动调整成功:{} {}",serialNumber,platformType);
                     return AgentResult.ok("00");
                }else if(serialNumber.equals(serialNumber_res) && "01".equals(snAdjStatus) && "000000".equals(result_code)) {
                    //调整中
                    log.info("活动调整中:{} {}",serialNumber,platformType);
                    AgentResult result = AgentResult.ok("01");
                    result.setMsg(resMsg);
                    return result;
                }else if(serialNumber.equals(serialNumber_res) && "02".equals(snAdjStatus) && "000000".equals(result_code)) {
                    //调整失败
                    log.info("活动调整失败:{} {}",serialNumber,platformType);
                    AgentResult result = AgentResult.ok("02");
                    result.setMsg(resMsg);
                    return result;
                }else{
                    //未知结果
                    AgentResult result = AgentResult.ok("03");
                    result.setMsg(resMsg);
                    return result;
                }
            }else{
                //未知结果
                return AgentResult.ok("03");
            }
        }else{
            return AgentResult.ok("03");
        }
    }


    @Override
    public boolean checkModleIsEq(Map<String, String> data, String platformType) {
        log.info("checkModleIsEq:{},{}",data,platformType);
        return imsTermMachineService.checkModleIsEq(data.get("oldMerid"),data.get("newMerId"));
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

    /**
     * 终端划拨解锁
     * @param taskId  总批次号
     * @param serialNumber  单个批次号
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult terminalTransferunlock(String taskId, String serialNumber, String type) throws Exception {
        return null;
    }

    @Override
    public AgentResult terminalTransAgain(String taskId, String serialNumber, String type) throws Exception {
        return null;
    }
}