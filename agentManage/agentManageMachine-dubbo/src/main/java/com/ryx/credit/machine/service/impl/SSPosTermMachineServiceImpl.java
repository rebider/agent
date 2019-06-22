package com.ryx.credit.machine.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.BackType;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.machine.dao.*;
import com.ryx.credit.machine.entity.*;
import com.ryx.credit.machine.service.ImsTermActiveService;
import com.ryx.credit.machine.service.ImsTermMachineService;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.*;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
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
 * 描述：实时分润极具相关接口
 */
@Service("sPosTermMachineServiceImpl")
public class SSPosTermMachineServiceImpl implements TermMachineService {

    private static Logger log = LoggerFactory.getLogger(SSPosTermMachineServiceImpl.class);

    private final static String ZHYY_CREATE_PERSON = AppConfig.getProperty("zhyy_create_person");
    private final static String ZHYY_ROOT_ORG_ID = AppConfig.getProperty("zhyy_root_org_id");

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
    private ImsTermMachineMapper imsTermMachineMapper;
    @Autowired
    private ImsMachineActivityMapper imsMachineActivityMapper;
    @Autowired
    private ImsTermTransferMapper imsTermTransferMapper;
    @Autowired
    private ImsTermTransferDetailMapper imsTermTransferDetailMapper;
    @Autowired
    private ImsTermAdjustDetailMapper imsTermAdjustDetailMapper;
    @Autowired
    private ImsTermAdjustMapper imsTermAdjustMapper;

    @Override
    public List<TermMachineVo> queryTermMachine(PlatformType platformType) throws Exception{
        List<Map> list =  imsTermMachineMapper.querySSIMS_TERM_MACHINE();
        List<TermMachineVo> termMachineVoList = new ArrayList<>();
        for (Map imsTermMachine : list) {
            TermMachineVo newvo = new TermMachineVo();
            newvo.setId(imsTermMachine.get("ID")+"");
            newvo.setMechineName(imsTermMachine.get("NAME")+"");
            Object STAND_AMT = imsTermMachine.get("STAND_AMT");
            Object BACK_TYPE = imsTermMachine.get("BACK_TYPE");
            newvo.setStandAmt(STAND_AMT==null?null:(STAND_AMT+""));
            newvo.setBackType(BACK_TYPE==null?null:(BACK_TYPE+""));
            termMachineVoList.add(newvo);
        }
        return termMachineVoList;
    }

    @Override
    public List<MposTermBatchVo> queryMposTermBatch(PlatformType platformType) throws Exception{
        return new ArrayList<>();
    }

    @Override
    public List<MposTermTypeVo> queryMposTermType(PlatformType platformType) throws Exception{
        return new ArrayList<>();
    }

    @Override
    public AgentResult lowerHairMachine(LowerHairMachineVo lowerHairMachineVo)throws Exception {
        LowerHairMachineVo vo = lowerHairMachineVo;
        ImsTermWarehouseDetail imsTermWarehouseDetail = vo.getImsTermWarehouseDetail();
        Map<String,String> posInfo = imsTermMachineMapper.queryIMS_POS_ACTIVITY(imsTermWarehouseDetail.getMachineId());
        String POS_ID =  posInfo.get("POS_ID");
        String ACTIVITY_ID =   posInfo.get("ACTIVITY_ID");

        ImsMachineActivity activity = imsMachineActivityMapper.selectByPrimaryKey(ACTIVITY_ID);
        imsTermWarehouseDetail.setMachineId(POS_ID);
        imsTermWarehouseDetail.setActivityId(activity.getActivityId());
        imsTermWarehouseDetail.setBrandCode(activity.getBrandCode());

        if(null==vo.getSnList()){
            throw new Exception("sn列表异常");
        }
        if(vo.getSnList().size()==0){
            throw new MessageException("sn数据有误");
        }
        log.info("同步POS入库划拨数据开始:snList:{},请求参数:{}",vo.getSnList().size(),vo.getImsTermWarehouseDetail());
        for (String sn : vo.getSnList()) {
            ImsTermActive imsTermActive = imsTermActiveService.selectByPrimaryKey(sn);
            //有记录就表示已激活
            if(null!=imsTermActive){
                throw new MessageException("Sn机具已激活");
            }
            ImsTermWarehouseDetail updateTermWarehouseDetail = imsTermWarehouseDetailMapper.selectByPrimaryKey(sn);
            if(updateTermWarehouseDetail!=null){
                throw new MessageException("SN已存在,请检查sn");
            }
            String createTime = DateUtil.format(new Date());
            imsTermWarehouseDetail.setWdId(IDUtils.genImsTermId());
            imsTermWarehouseDetail.setPosSn(sn);
            imsTermWarehouseDetail.setUseStatus("1"); //未使用
            imsTermWarehouseDetail.setStatus("0");  //正常
            imsTermWarehouseDetail.setCreateTime(createTime);
            imsTermWarehouseDetail.setCreatePerson(ZHYY_CREATE_PERSON);
            imsTermWarehouseDetail.setUpdateTime(createTime);
            imsTermWarehouseDetail.setPayStatus("1");  //支付状态 0 已付 1 未付
            imsTermWarehouseDetail.setDeliveryTime(DateUtil.formatDay(new Date()));

            int i = imsTermWarehouseDetailMapper.insert(imsTermWarehouseDetail);
            log.info("同步POS入库返回结果:{}",i);
            if(i!=1){
                throw new MessageException("SN库存插入失败");
            }
            String transferId = IDUtils.genImsTermId();
            ImsTermTransfer imsTermTransfer = new ImsTermTransfer();
            imsTermTransfer.setTransferId(transferId);
            imsTermTransfer.setStatus("0");  //0：处理完成
            imsTermTransfer.setOrgId(ZHYY_ROOT_ORG_ID);
            imsTermTransfer.setCreateTime(createTime);
            imsTermTransfer.setCreatePerson(ZHYY_CREATE_PERSON);
            imsTermTransfer.setUpdateTime(createTime);
            imsTermTransfer.setUpdatePerson(ZHYY_CREATE_PERSON);
            imsTermTransfer.setTransferType("0");   //0:划拨

            int j = imsTermTransferMapper.insert(imsTermTransfer);
            log.info("同步POS划拨返回结果:{}",j);
            if(j!=1){
                throw new MessageException("SN划拨插入失败");
            }
            ImsTermTransferDetail imsTermTransferDetail = new ImsTermTransferDetail();
            imsTermTransferDetail.setId(IDUtils.genImsTermId());
            imsTermTransferDetail.setMachineId(imsTermWarehouseDetail.getMachineId());
            imsTermTransferDetail.setPosSn(sn);
            imsTermTransferDetail.setyOrgId(ZHYY_ROOT_ORG_ID);
            imsTermTransferDetail.setnOrgId(imsTermWarehouseDetail.getOrgId());
            imsTermTransferDetail.setCreateTime(createTime);
            imsTermTransferDetail.setCreatePerson(ZHYY_CREATE_PERSON);
            imsTermTransferDetail.setOperOrgId(imsTermWarehouseDetail.getOrgId());
            imsTermTransferDetail.setTransferId(transferId);

            int k = imsTermTransferDetailMapper.insert(imsTermTransferDetail);
            log.info("同步POS划拨明细返回结果:{}",k);
            if(k!=1){
                throw new MessageException("SN划拨明细插入失败");
            }
        }
        return AgentResult.ok();
    }

    @Override
    public AgentResult adjustmentMachine(AdjustmentMachineVo adjustmentMachineVo)throws Exception {
        List<OLogisticsDetail>  logisticsDetailList = adjustmentMachineVo.getLogisticsDetailList();
        ImsTermAdjustDetail imsTermAdjustDetail = adjustmentMachineVo.getImsTermAdjustDetail();
        if(null==logisticsDetailList){
            throw new MessageException("sn列表异常");
        }
        if(logisticsDetailList.size()==0){
            throw new MessageException("sn数据有误");
        }
        log.info("同步POS调整数据开始:snList:{},请求参数:{}",logisticsDetailList.size(),imsTermAdjustDetail);
        for (OLogisticsDetail oLogisticsDetail : logisticsDetailList) {
            ImsTermActive imsTermActive = imsTermActiveService.selectByPrimaryKey(oLogisticsDetail.getSnNum());
            //有记录就表示已激活
            if(null!=imsTermActive){
                throw new MessageException("Sn机具已激活");
            }
            String createTime = DateUtil.format(new Date());
            String adjustId = IDUtils.genImsTermId();
            ImsTermAdjust imsTermAdjust = new ImsTermAdjust();
            imsTermAdjust.setId(adjustId);
            imsTermAdjust.setCreateTime(createTime);
            imsTermAdjust.setCreatePerson(ZHYY_CREATE_PERSON);
            int i = imsTermAdjustMapper.insert(imsTermAdjust);
            log.info("同步POS调整返回结果:{}",i);
            if(i!=1){
                throw new MessageException("SN调整失败");
            }
            imsTermAdjustDetail.setId(IDUtils.genImsTermId());
            imsTermAdjustDetail.setPosSn(oLogisticsDetail.getSnNum());
            imsTermAdjustDetail.setAdId(adjustId);
            imsTermAdjustDetail.setCreateTime(createTime);
            imsTermAdjustDetail.setCreatePerson(ZHYY_CREATE_PERSON);
            imsTermAdjustDetail.setyOrgId(adjustmentMachineVo.getOldBusNum());
            int j = imsTermAdjustDetailMapper.insert(imsTermAdjustDetail);
            log.info("同步POS调整返回结果:{}",j);
            if(j!=1){
                throw new MessageException("SN调整失败");
            }
            ImsTermWarehouseDetail imsTermWarehouseDetail = new ImsTermWarehouseDetail();
            imsTermWarehouseDetail.setPosSn(oLogisticsDetail.getSnNum());
            imsTermWarehouseDetail.setOrgId(imsTermAdjustDetail.getnOrgId());
            int k = imsTermWarehouseDetailMapper.updateByPrimaryKeySelective(imsTermWarehouseDetail);
            log.info("同步POS调整更新库存明细返回结果:{}",k);
            if(k!=1){
                throw new MessageException("SN调整失败");
            }
        }
        return AgentResult.ok();
    }

    @Override
    public AgentResult changeActMachine(ChangeActMachineVo changeActMachine) throws MessageException{
        List<OLogisticsDetail> logisticsDetailList = changeActMachine.getLogisticsDetailList();
        if(null==logisticsDetailList){
            log.info("updateWarehouse请求参数错误1");
            throw new MessageException("请求参数错误");
        }
        if(logisticsDetailList.size()==0){
            log.info("updateWarehouse请求参数错误2");
            throw new MessageException("请求参数错误");
        }

        Map<String,String> posInfo = imsTermMachineMapper.queryIMS_POS_ACTIVITY(changeActMachine.getNewAct());
        String POS_ID =  posInfo.get("POS_ID");
        String ACTIVITY_ID =   posInfo.get("ACTIVITY_ID");
        ImsMachineActivity activity = imsMachineActivityMapper.selectByPrimaryKey(ACTIVITY_ID);

        for (OLogisticsDetail oLogisticsDetail : logisticsDetailList) {
            ImsTermWarehouseDetail imsTermWarehouseDetail = new ImsTermWarehouseDetail();
            imsTermWarehouseDetail.setPosSn(oLogisticsDetail.getSnNum());
            imsTermWarehouseDetail.setMachineId(POS_ID);
            imsTermWarehouseDetail.setActivityId(activity.getActivityId());
            imsTermWarehouseDetail.setBrandCode(activity.getBrandCode());
            ImsTermActive imsTermActive = imsTermActiveService.selectByPrimaryKey(imsTermWarehouseDetail.getPosSn());
            //有记录就表示已激活
            if(null!=imsTermActive){
                throw new MessageException("Sn机具已激活");
            }
            ImsTermWarehouseDetail queryImsTerm = imsTermWarehouseDetailMapper.selectByPrimaryKey(imsTermWarehouseDetail.getPosSn());
            if(queryImsTerm==null){
                throw new MessageException("Sn机具不存在");
            }
            //未使用
            if(!queryImsTerm.getUseStatus().equals("1")){
                throw new MessageException("Sn机具已使用");
            }


            int i = imsTermWarehouseDetailMapper.updateByPrimaryKeySelective(imsTermWarehouseDetail);
            if(i!=1){
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
            if(j!=1){
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
    public AgentResult querySnMsg(PlatformType platformType,String snBegin,String snEnd)throws Exception{
        JSONObject data = new JSONObject();
        data.put("posSnStart",snBegin);
        data.put("posSnEnd",snEnd);
        return request("ORG009", data);
    }


    private AgentResult request(String tranCode,JSONObject data)throws Exception{
        try{
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

            log.info("pos机具信息请求参数:{}",map);
            String httpResult = HttpClientUtil.doPost(AppConfig.getProperty("industryAuth_url"), map);
            JSONObject jsonObject = JSONObject.parseObject(httpResult);
            if (!jsonObject.containsKey("encryptData") || !jsonObject.containsKey("encryptKey")) {
                log.info("请求异常======" + httpResult);
                throw new Exception("http请求异常");
            } else {
                String resEncryptData = jsonObject.getString("encryptData");
                String resEncryptKey = jsonObject.getString("encryptKey");
                byte[] decodeBase64KeyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(resEncryptKey.getBytes(charset));
                byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, rsaPrivateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
                byte[] decodeBase64DataBytes = org.apache.commons.codec.binary.Base64.decodeBase64(resEncryptData.getBytes(charset));
                byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
                String respXML = new String(merchantXmlDataBytes, charset);
                log.info("pos机具信息返回参数：{}",respXML);

                // 报文验签
                String resSignData = jsonObject.getString("signData");
                byte[] signBytes = org.apache.commons.codec.binary.Base64.decodeBase64(resSignData);
                if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, rsaPublicKey, "SHA1WithRSA")) {
                    log.info("签名验证失败");
                } else {
                    log.info("签名验证成功");
                    JSONObject respXMLObj = JSONObject.parseObject(respXML);
                    String respCode = String.valueOf(respXMLObj.get("respCode"));
                    if (respCode.equals("000000")){
                        return AgentResult.build(200,respXMLObj.toString());
                    }else{
                        log.info("http请求超时返回错误:{}",respXML);
                        return AgentResult.fail(respXMLObj.toString());
                    }
                }
                return new AgentResult(500,"http请求异常",respXML);
            }
        } catch (Exception e) {
            log.info("http请求超时:{}",e.getMessage());
            throw e;
        }
    }
}
