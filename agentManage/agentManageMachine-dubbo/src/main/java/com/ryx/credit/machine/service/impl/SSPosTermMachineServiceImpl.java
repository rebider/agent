package com.ryx.credit.machine.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.DeliveryTimeType;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.machine.dao.*;
import com.ryx.credit.machine.entity.*;
import com.ryx.credit.machine.service.ImsTermActiveService;
import com.ryx.credit.machine.service.ImsTermMachineService;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.*;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.order.OActivity;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import com.ryx.credit.service.order.IOrderReturnService;
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
 * 描述：实时分润极具相关接口
 */
@Service("sPosTermMachineServiceImpl")
public class SSPosTermMachineServiceImpl implements TermMachineService {

    private final static String ZHYY_CREATE_PERSON = AppConfig.getProperty("zhyy_create_person");
    private final static String ZHYY_ROOT_ORG_ID = AppConfig.getProperty("zhyy_root_org_id");
    private static Logger log = LoggerFactory.getLogger(SSPosTermMachineServiceImpl.class);
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
    @Autowired
    private IOrderReturnService orderReturnService;
    @Autowired
    private ImsOrganReturnTemplateMapper imsOrganReturnTemplateMapper;
    @Autowired
    private OrderActivityService orderActivityService;

    @Override
    public List<TermMachineVo> queryTermMachine(PlatformType platformType,Map<String,String> par) throws Exception{
        List<Map> list =  imsTermMachineMapper.querySSIMS_TERM_MACHINE(par);
        List<TermMachineVo> termMachineVoList = new ArrayList<>();
        for (Map imsTermMachine : list) {
            TermMachineVo newvo = new TermMachineVo();
            newvo.setId(imsTermMachine.get("ID")+"");
            newvo.setMechineName(imsTermMachine.get("NAME")+"");
            Object STAND_AMT = imsTermMachine.get("STAND_AMT");
            Object BACK_TYPE = imsTermMachine.get("BACK_TYPE");
            newvo.setStandAmt(STAND_AMT==null?null:(STAND_AMT+""));
            newvo.setBackType(BACK_TYPE==null?null:(BACK_TYPE+""));
            Object TMS_MODEL = imsTermMachine.get("TMS_MODEL");
            Object MANUFACTOR = imsTermMachine.get("MANUFACTOR");
            newvo.setModel(TMS_MODEL==null?null:TMS_MODEL+"");
            newvo.setManufactor(MANUFACTOR==null?null:MANUFACTOR+"");

            Object STAND_TIME = imsTermMachine.get("STAND_TIME");
            Object ACTIVITY_START_TIME = imsTermMachine.get("ACTIVITY_START_TIME");
            Object ACTIVITY_END_TIME = imsTermMachine.get("ACTIVITY_END_TIME");
            Object PRICE = imsTermMachine.get("PRICE");
            newvo.setStandTime(STAND_TIME==null?null:STAND_TIME+"");
            newvo.setActivityStartTime(ACTIVITY_START_TIME==null?null:ACTIVITY_START_TIME+"");
            newvo.setActivityEndTime(ACTIVITY_END_TIME==null?null:ACTIVITY_END_TIME+"");
            newvo.setPrice(PRICE==null?null:PRICE+"");
            Object POSTYPE = imsTermMachine.get("POSTYPE");
            newvo.setPosType(POSTYPE==null?null:POSTYPE+"");
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



    /**
     * IMS_ORGAN_RETURN_TEMPLATE
     * ORG_ID 和 ACTIVITY_ID 这个去找对应的模板
     * @param lowerHairMachineVo
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult lowerHairMachine(LowerHairMachineVo lowerHairMachineVo)throws Exception {
        log.info("同步SSPOS入库划拨数据开始:snList:{},请求参数:{}",lowerHairMachineVo.getSnList().size(),JSONObject.toJSONString(lowerHairMachineVo.getImsTermWarehouseDetail()));
        ImsTermWarehouseDetail imsTermWarehouseDetail = lowerHairMachineVo.getImsTermWarehouseDetail();
        Map<String,String> posInfo = imsTermMachineMapper.queryIMS_POS_ACTIVITY(imsTermWarehouseDetail.getMachineId());
        String POS_ID      =   posInfo.get("POS_ID");
        String ACTIVITY_ID =   posInfo.get("ACTIVITY_ID");
        ImsMachineActivity activity = imsMachineActivityMapper.selectByPrimaryKey(ACTIVITY_ID);
        //判断是否设置返现模板
        ImsOrganReturnTemplateExample imsOrganReturnTemplateCheck = new ImsOrganReturnTemplateExample();
        imsOrganReturnTemplateCheck.or().andOrgIdEqualTo(imsTermWarehouseDetail.getOrgId()).andActivityIdEqualTo(ACTIVITY_ID);
        if(imsOrganReturnTemplateMapper.countByExample(imsOrganReturnTemplateCheck)<=0){
            log.info("同步SSPOS入库划拨数据异常:snList:{},请求参数:{},错误消息:{}",lowerHairMachineVo.getSnList().size(),JSONObject.toJSONString(lowerHairMachineVo.getImsTermWarehouseDetail()),"没有设置返现模板");
            return AgentResult.fail("没有设置返现模板么["+imsTermWarehouseDetail.getOrgId()+"]");
        }
        //检查商户是否有分润模板
        log.info("同步POS入库划拨:POS编号:{},活动编号:{},活动名称:{},sn：{}",POS_ID,ACTIVITY_ID,activity==null?"null":activity.getActivityName(),lowerHairMachineVo.getSnList());
        if(null==lowerHairMachineVo.getSnList()){
            throw new Exception("sn列表异常");
        }
        if(lowerHairMachineVo.getSnList().size()==0){
            throw new MessageException("sn数据有误");
        }

        for (String sn : lowerHairMachineVo.getSnList()) {
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
            imsTermWarehouseDetail.setMachineId(POS_ID);
            imsTermWarehouseDetail.setOrgId(imsTermWarehouseDetail.getOrgId());
            imsTermWarehouseDetail.setActivityId(activity.getActivityId());
            imsTermWarehouseDetail.setBrandCode(activity.getBrandCode());
            imsTermWarehouseDetail.setWdId(IDUtils.genImsTermId());
            imsTermWarehouseDetail.setPosSn(sn);
            imsTermWarehouseDetail.setUseStatus("1"); //未使用
            imsTermWarehouseDetail.setStatus("0");  //正常
            imsTermWarehouseDetail.setPosType(imsTermWarehouseDetail.getPosType());//根据押金 POS_TYPE IS 'pos类型 0普通级，1：特价机，2特价机无押金';
            imsTermWarehouseDetail.setPayStatus("1");//
            imsTermWarehouseDetail.setDeliveryTime(imsTermWarehouseDetail.getDeliveryTime());//发货时间
            imsTermWarehouseDetail.setStandTime(imsTermWarehouseDetail.getStandTime()==null?activity.getStandTime():imsTermWarehouseDetail.getStandTime());//达标时间
            imsTermWarehouseDetail.setCreateTime(createTime);
            imsTermWarehouseDetail.setCreatePerson(ZHYY_CREATE_PERSON);
            imsTermWarehouseDetail.setUpdateTime(createTime);
            imsTermWarehouseDetail.setPayStatus("1");  //支付状态 0 已付 1 未付
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

        Map<String,String> posInfo = imsTermMachineMapper.queryIMS_POS_ACTIVITY(imsTermAdjustDetail.getMachineId());
        String POS_ID =  posInfo.get("POS_ID");
        String ACTIVITY_ID =   posInfo.get("ACTIVITY_ID");

        ImsMachineActivity activity = imsMachineActivityMapper.selectByPrimaryKey(ACTIVITY_ID);

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
            AgentBusInfo agentBusInfo = orderReturnService.queryBusInfoByLogDetail(oLogisticsDetail);
            imsTermAdjustDetail.setyOrgId(imsTermAdjustDetail.getyOrgId());
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

    @Override
    public AgentResult queryTerminalTransfer(List<TerminalTransferDetail> terminalTransferDetailList, String operation) throws Exception {
        return AgentResult.fail("未联动");
    }

    @Override
    public AgentResult queryTerminalTransferResult(String serialNumber, String type) throws Exception {
        return AgentResult.fail("未联动");
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
            if(org.apache.commons.lang.StringUtils.isBlank(new_activity.getPosType())){
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



            if(org.apache.commons.lang.StringUtils.isNotBlank(refundPriceDiffDetail.getDeliveryTimeType())){
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
                if (!"000000".equals(res_data.getString("result_code")) && org.apache.commons.lang.StringUtils.isNotBlank(res_data.getString("result_msg"))) {
                    log.info("http请求返回错误:{}", res_data.getString("result_msg"));
                    return AgentResult.fail(res_data.getString("result_msg"));
                } else {
                    return AgentResult.ok(res_data);
                }
            } else {
                if (org.apache.commons.lang.StringUtils.isNotBlank(respXMLObj.getString("respMsg"))) {
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
                    if (!"000000".equals(res_data.getString("result_code")) && org.apache.commons.lang.StringUtils.isNotBlank(res_data.getString("result_msg"))) {
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
    public AgentResult queryCompensateResult(String serialNumber,String platformType) throws Exception {
        return AgentResult.ok("04");
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
}
