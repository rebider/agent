package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.machine.service.ImsTermMachineService;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.PlatFormService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.*;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 旧订单补差价处理
 * Created by liudh on 2019/3/13.
 */
@Service("oldCompensateService")
public class OldCompensateServiceImpl implements OldCompensateService {

    private static Logger log = LoggerFactory.getLogger(OldCompensateServiceImpl.class);

    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private TermMachineService termMachineService;
    @Autowired
    private IdService idService;
    @Autowired
    private ORefundPriceDiffMapper refundPriceDiffMapper;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private OCashReceivablesService cashReceivablesService;
    @Autowired
    private ORefundPriceDiffDetailMapper refundPriceDiffDetailMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private OOrderMapper orderMapper;
    @Autowired
    private OSubOrderMapper subOrderMapper;
    @Autowired
    private OSubOrderActivityMapper subOrderActivityMapper;
    @Autowired
    private OActivityMapper activityMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private OrderActivityService orderActivityService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ImsTermMachineService imsTermMachineService;
    @Autowired
    private OActivityVisibleMapper activityVisibleMapper;
    @Autowired
    private OsnOperateService osnOperateService;
    @Autowired
    private OrderOffsetService orderOffsetService;
    @Autowired
    private CompensateService compensateService;
    @Autowired
    private PlatFormMapper platFormMapper;

    /**
     * 解析提交过来的sn,
     * @param excelList
     * @return
     * @throws MessageException
     */
    @Override
    public List<Map<String,Object>> getOrderMsgByExcel(List<List<Object>> excelList,String agentId)throws MessageException{

        List<Map<String,Object>> resultList = new ArrayList<>();
        for (List<Object> excel : excelList) {
            Map<String,Object> resultMap = new HashMap();
            String snBegin = "";
            String snEnd = "";
            String count = "";
            String proModel = "";
            try {
                snBegin = String.valueOf(excel.get(0)).trim();
                snEnd = String.valueOf(excel.get(1)).trim();
                count = String.valueOf(excel.get(2)).trim();
                proModel = String.valueOf(excel.get(3)).trim();
            } catch (Exception e) {
                throw new MessageException(snBegin+"-"+snEnd+", 导入解析文件失败");
            }
            AgentResult agentResult = null;
            try {
                agentResult = orderActivityService.querySnInfoFromBusSystem(snBegin, snEnd, count, proModel);
            } catch (MessageException e) {
                throw new MessageException(snBegin+"-"+snEnd+","+e.getMsg());
            }
            FastMap fastMap = (FastMap)agentResult.getMapData();

            resultMap.put("snBegin",snBegin);
            resultMap.put("snEnd",snEnd);
            resultMap.put("count",count);
            List list = new ArrayList((Set)fastMap.get("activity"));
            OActivity oActivity = (OActivity)list.get(0);
            resultMap.put("activity",oActivity);

            OProduct product_activity =  productService.findById(oActivity.getProductId());
            resultMap.put("proMaps",Arrays.asList(product_activity));

            //源平台可跨平台变更的平台
            List<Dict> listCanChange =  dictOptionsService.dictList(DictGroup.COMPENSATE_PLATFORM_TYPE.name(),oActivity.getPlatform());
            List<String>  listCanChangePlat =new ArrayList<>();
            if(listCanChange.size()>0) {
                listCanChangePlat = listCanChange.stream().map(item -> {
                    return item.getdItemvalue();
                }).collect(Collectors.toList());
                listCanChangePlat.add(oActivity.getPlatform());
            }
            //查询可变更的活动
            OActivityExample oActivityExample = new OActivityExample();
            OActivityExample.Criteria activityCriteria = oActivityExample.createCriteria();
            activityCriteria.andStatusEqualTo(Status.STATUS_1.status);
            activityCriteria.andActCodeNotEqualTo(oActivity.getActCode());
            activityCriteria.andVenderEqualTo(oActivity.getVender());//厂商
            activityCriteria.andProModelEqualTo(oActivity.getProModel());//型号
            if(listCanChangePlat.size()==0) {
                activityCriteria.andPlatformEqualTo(oActivity.getPlatform());//平台
            }else{
                activityCriteria.andPlatformIn(listCanChangePlat);
            }
            Date date = new Date();
            activityCriteria.andBeginTimeLessThanOrEqualTo(date);
            activityCriteria.andEndTimeGreaterThanOrEqualTo(date);
            List<OActivity> oActivities = activityMapper.selectByExample(oActivityExample);
            if(oActivities.size()==0){
                throw new MessageException(snBegin+"到"+snEnd+":无可变更活动,当前活动"+oActivity.getActivityName());
            }

            //可变更的活动
            List<JSONObject> oActivitiesObj = new ArrayList<>();
            for (OActivity activity : oActivities) {
                PlatForm platForm =  platFormService.selectByPlatformNum(activity.getPlatform());
                String obj = JSONObject.toJSONString(activity);
                JSONObject activityJsonObject = JSONObject.parseObject(obj);
                activityJsonObject.put("platFormObj",platForm);
                if (null != activity.getVisible()) {
                    if(activity.getVisible().equals(VisibleStatus.TWO.getValue())){
                        OActivityVisibleExample oActivityVisibleExample = new OActivityVisibleExample();
                        OActivityVisibleExample.Criteria visibleCriteria = oActivityVisibleExample.createCriteria();
                        visibleCriteria.andActivityIdEqualTo(activity.getActCode());
                        List<OActivityVisible> oActivityVisibles = activityVisibleMapper.selectByExample(oActivityVisibleExample);
                        for (OActivityVisible oActivityVisible : oActivityVisibles) {
                            if(oActivityVisible.getAgentId().equals(agentId)){
                                oActivitiesObj.add(activityJsonObject);
                                break;
                            }
                        }
                    }else{
                        oActivitiesObj.add(activityJsonObject);
                    }
                }
            }
            resultMap.put("changeActivitys",oActivitiesObj); //可变更的活动

            //代理商可用的业务平台
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            agentBusInfoExample.or()
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andAgentIdEqualTo(agentId)
                    .andBusStatusIn(Arrays.asList(BusStatus.QY.getValue(),BusStatus.WJH.getValue(),BusStatus.WQY.getValue()))
                    .andBusPlatformEqualTo(oActivity.getPlatform());
            List<AgentBusInfo>  agentBusInfoList = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            resultMap.put("agentBusInfoListOld",agentBusInfoList); //原平台

            //代理商可用的目标平台
            if(listCanChange.size()==0) {
                resultMap.put("agentBusInfoListNew", agentBusInfoList); //目标平台
            }else{
                 agentBusInfoExample = new AgentBusInfoExample();
                agentBusInfoExample.or()
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andAgentIdEqualTo(agentId)
                        .andBusStatusIn(Arrays.asList(BusStatus.QY.getValue(),BusStatus.WJH.getValue(),BusStatus.WQY.getValue()))
                        .andBusPlatformIn(listCanChangePlat);
                List<AgentBusInfo>  canchange = agentBusInfoMapper.selectByExample(agentBusInfoExample);
                resultMap.put("agentBusInfoListNew",canchange); //目标平台
            }
            resultList.add(resultMap);
        }
        return resultList;

    }


    /**
     * save
     * @param oRefundPriceDiff
     * @param refundPriceDiffDetailList
     * @param cUser
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult compensateAmtSave(ORefundPriceDiff oRefundPriceDiff,
                                         List<ORefundPriceDiffDetail> refundPriceDiffDetailList,
                                         List<String> refundPriceDiffFile,
                                         String cUser,
                                         List<OCashReceivablesVo> oCashReceivablesVoList,
                                         AgentVo agentVo)throws ProcessException{

        try {
            //pos提交标志，ture第二次提交,false是第一次提交
            boolean submitFlag = (null != agentVo.getPosPaidFlag() && agentVo.getPosPaidFlag().equals("1"));
            if(PriceDiffType.DETAIN_AMT.code.equals(oRefundPriceDiff.getApplyCompType())){
                if(refundPriceDiffFile.size()==0){
                    Boolean ispz = false;
                    for (OCashReceivablesVo oCashReceivablesVo : oCashReceivablesVoList) {
                        if(oCashReceivablesVo.getPayType().equals(PayType.YHHK.getValue())){
                            ispz = true;
                            break;
                        }
                    }
                    //有一个下线下打款的就必须上传打款凭证
                    //if(ispz) return AgentResult.fail("代理商打款必须上传打款凭证");
                }
                //if(oCashReceivablesVoList==null || oCashReceivablesVoList.size()==0) return AgentResult.fail("代理商打款必须填写打款记录");
            }
            String priceDiffId = idService.genId(TabId.o_Refund_price_diff);
            oRefundPriceDiff.setId(priceDiffId);
            Date nowDate = new Date();
            oRefundPriceDiff.setRelCompType(oRefundPriceDiff.getApplyCompType());
            oRefundPriceDiff.setRelCompAmt(oRefundPriceDiff.getApplyCompAmt());
            oRefundPriceDiff.setMachOweAmt(new BigDecimal(0));
            oRefundPriceDiff.setDeductAmt(new BigDecimal(0));
            oRefundPriceDiff.setGatherAmt(new BigDecimal(0));
            oRefundPriceDiff.setcTime(nowDate);
            oRefundPriceDiff.setuTime(nowDate);
            oRefundPriceDiff.setsTime(nowDate);
            oRefundPriceDiff.setcUser(cUser);
            oRefundPriceDiff.setuUser(cUser);
            oRefundPriceDiff.setReviewStatus(AgStatus.Create.status);
            oRefundPriceDiff.setStatus(Status.STATUS_1.status);
            oRefundPriceDiff.setVersion(Status.STATUS_0.status);
            oRefundPriceDiff.setOrderType(OrderType.OLD.getValue());
            BigDecimal belowPayAmt = new BigDecimal(0);
            BigDecimal shareDeductAmt = new BigDecimal(0);
            for (OCashReceivablesVo oCashReceivablesVo : oCashReceivablesVoList) {
                if(oCashReceivablesVo.getPayType().equals(PayType.YHHK.code)){
                    belowPayAmt = belowPayAmt.add(oCashReceivablesVo.getAmount());
                }else if(oCashReceivablesVo.getPayType().equals(PayType.FRDK.code)){
                    shareDeductAmt = shareDeductAmt.add(oCashReceivablesVo.getAmount());
                }
            }
            oRefundPriceDiff.setBelowPayAmt(belowPayAmt);
            oRefundPriceDiff.setShareDeductAmt(shareDeductAmt);
            int refundDiffInsert = refundPriceDiffMapper.insert(oRefundPriceDiff);
            if(refundDiffInsert!=1){
                log.info("插入补退差价申请表异常");
                throw new MessageException("保存失败");
            }

            //添加新的附件
            if (refundPriceDiffFile != null) {
                refundPriceDiffFile.forEach(fileId->{
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(fileId);
                    record.setSrcId(priceDiffId);
                    record.setcUser(cUser);
                    record.setcTime(Calendar.getInstance().getTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.ActivityEdit.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    int i = attachmentRelMapper.insertSelective(record);
                    if (1 != i) {
                        log.info("活动变更附件关系失败");
                        throw new ProcessException("保存失败");
                    }
                });
            }

            //打款记录
            AgentResult agentResult = cashReceivablesService.addOCashReceivables(oCashReceivablesVoList,cUser,oRefundPriceDiff.getAgentId(),CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId());
            if(!agentResult.isOK()){
                log.info("活动调整保存打款记录失败1");
                throw new MessageException("保存打款记录失败");
            }
            //代理商打款，代理商打款不能小于活动差价。
            if (null != oRefundPriceDiff.getApplyCompType() && oRefundPriceDiff.getApplyCompType().equals("1")) {
                //状态为1说明代理商要打款
                if (oRefundPriceDiff.getApplyCompAmt().compareTo(belowPayAmt.add(shareDeductAmt)) != 0){
                    //throw new ProcessException("应打款金额："+oRefundPriceDiff.getRelCompAmt());
                }
            }
            Set<String> setOldOrgId = new HashSet<>();
            Set<String> setPlatform = new HashSet<>();
            for (ORefundPriceDiffDetail refundPriceDiffDetail : refundPriceDiffDetailList) {
                if(StringUtils.isBlank(refundPriceDiffDetail.getOldOrgId())){
                    throw new MessageException("原机构编号不能为空");
                }
                if(StringUtils.isBlank(refundPriceDiffDetail.getNewOrgId())){
                    throw new MessageException("目标机构编号不能为空");
                }
                if(StringUtils.isBlank(refundPriceDiffDetail.getProId())){
                    throw new MessageException("请选择商品");
                }
                if(StringUtils.isBlank(refundPriceDiffDetail.getActivityRealId())){
                    throw new MessageException("请选择要变更的活动");
                }
                if(StringUtils.isBlank(refundPriceDiffDetail.getBeginSn())){
                    throw new MessageException("起始SN为空");
                }if(StringUtils.isBlank(refundPriceDiffDetail.getEndSn())){
                    throw new MessageException("结束SN为空");
                }

                //检查sn是否在划拨，换活动，退货审批中
                FastMap fastMap = osnOperateService.checkSNApproval(FastMap
                        .fastMap("beginSN", refundPriceDiffDetail.getBeginSn())
                        .putKeyV("endSN", refundPriceDiffDetail.getEndSn()));
                if (!FastMap.isSuc(fastMap)) throw new ProcessException(fastMap.get("msg").toString());

                OActivity oldActivity = activityMapper.selectByPrimaryKey(refundPriceDiffDetail.getActivityFrontId());
                if(oldActivity==null){
                    throw new MessageException("旧活动不存在");
                }
                OProduct product_old  = productService.findById(oldActivity.getProductId());
                PlatForm platForm = platFormService.selectByPlatformNum(oldActivity.getPlatform());

                OActivity newActivity = activityMapper.selectByPrimaryKey(refundPriceDiffDetail.getActivityRealId());
                if(newActivity==null){
                    throw new MessageException("新活动不存在");
                }
                OProduct product  = productService.findById(newActivity.getProductId());

                //检查目标活动和代理商平台码是否一致
                if(refundPriceDiffDetail.getNewOrgId().equals(refundPriceDiffDetail.getOldOrgId())){
                    //平台号没有跨平台，活动也不允许跨平台
                    if(!oldActivity.getPlatform().equals(newActivity.getPlatform())) {
                        throw new MessageException("目标平台编号与调整活动平台不匹配");
                    }
                }

                refundPriceDiffDetail.setAgentId(oRefundPriceDiff.getAgentId());
                refundPriceDiffDetail.setId(idService.genId(TabId.o_Refund_price_diff_d));
                refundPriceDiffDetail.setRefundPriceDiffId(priceDiffId);

                refundPriceDiffDetail.setProId(product.getId());
                refundPriceDiffDetail.setProName(product.getProName());
                refundPriceDiffDetail.setPrice(newActivity.getPrice());

                refundPriceDiffDetail.setFrontProId(oldActivity.getProductId());
                refundPriceDiffDetail.setFrontPrice(oldActivity.getPrice());
                refundPriceDiffDetail.setFrontProName(product_old.getProName());
                refundPriceDiffDetail.setOldMachineId(oldActivity.getBusProCode());

                refundPriceDiffDetail.setActivityName(newActivity.getActivityName());
                refundPriceDiffDetail.setActivityWay(newActivity.getActivityWay());
                refundPriceDiffDetail.setActivityRule(newActivity.getActivityRule());
                refundPriceDiffDetail.setPrice(newActivity.getPrice());
                refundPriceDiffDetail.setVender(newActivity.getVender());
                refundPriceDiffDetail.setProModel(newActivity.getProModel());
                refundPriceDiffDetail.setNewMachineId(newActivity.getBusProCode());

                refundPriceDiffDetail.setPlatformType(platForm.getPlatformType());

                refundPriceDiffDetail.setcTime(nowDate);
                refundPriceDiffDetail.setuTime(nowDate);
                refundPriceDiffDetail.setsTime(nowDate);
                refundPriceDiffDetail.setcUser(cUser);
                refundPriceDiffDetail.setuUser(cUser);
                refundPriceDiffDetail.setStatus(Status.STATUS_1.status);
                refundPriceDiffDetail.setVersion(Status.STATUS_0.status);
                refundPriceDiffDetail.setOrderType(OrderType.OLD.getValue());
                refundPriceDiffDetail.setSendStatus(Status.STATUS_0.status);

                //特殊平台增加个校验（智慧POS，智能POS）
                if (PlatformType.ZHPOS.code.equals(platForm.getPlatformType()) || PlatformType.ZPOS.code.equals(platForm.getPlatformType())) {
                    List<AgentBusInfo> oldList = agentBusInfoMapper.queryBusinfo(FastMap.fastMap("posPlatCode", refundPriceDiffDetail.getOldOrgId()));
                    if (oldList.size() == 1 && null != oldList.get(0).getBusNum()) {
                        refundPriceDiffDetail.setOldOrgId(String.valueOf(oldList.get(0).getBusNum()));
                    } else {
                        throw new ProcessException("原机构S码有误，未找到业务平台编码！");
                    }
                    List<AgentBusInfo> newList = agentBusInfoMapper.queryBusinfo(FastMap.fastMap("posPlatCode", refundPriceDiffDetail.getNewOrgId()));
                    if (newList.size() == 1 && null != newList.get(0).getBusNum()) {
                        refundPriceDiffDetail.setNewOrgId(String.valueOf(newList.get(0).getBusNum()));
                    } else {
                        throw new ProcessException("目标机构S码有误，未找到业务平台编码！");
                    }
                } else if (PlatformType.SSPOS.getValue().equals(platForm.getPlatformType())) {
                    List<PlatForm> oldPlatForm =  platFormMapper.queryPlatFormByMap(FastMap.fastMap("platformType", platForm.getPlatformType()).putKeyV("busNum",refundPriceDiffDetail.getOldOrgId()));
                    List<PlatForm> newPlatForm =  platFormMapper.queryPlatFormByMap(FastMap.fastMap("platformType", platForm.getPlatformType()).putKeyV("busNum",refundPriceDiffDetail.getNewOrgId()));
                    if (null == oldPlatForm || oldPlatForm.size() != 1 || null == oldPlatForm.get(0).getBusplatform()) {
                        throw new ProcessException("未找到原目标业务平台，请核查原目标业务平台！");
                    }
                    if (null == newPlatForm || newPlatForm.size() != 1 || null == newPlatForm.get(0).getBusplatform()) {
                        throw new ProcessException("未找到目标业务平台，请核查目标业务平台！");
                    }
                    refundPriceDiffDetail.setOldBrandCode(oldPlatForm.get(0).getBusplatform());
                    refundPriceDiffDetail.setNewBrandCode(newPlatForm.get(0).getBusplatform());
                } else if (PlatformType.POS.getValue().equals(platForm.getPlatformType())) {
                    List<PlatForm> oldPlatForm =  platFormMapper.queryPlatFormByMap(FastMap.fastMap("platformType", platForm.getPlatformType()).putKeyV("busNum",refundPriceDiffDetail.getOldOrgId()));
                    List<PlatForm> newPlatForm =  platFormMapper.queryPlatFormByMap(FastMap.fastMap("platformType", platForm.getPlatformType()).putKeyV("busNum",refundPriceDiffDetail.getNewOrgId()));
                    if (null == oldPlatForm || oldPlatForm.size() != 1 || null == oldPlatForm.get(0).getBusplatform()) {
                        throw new ProcessException("未找到原目标业务平台，请核查原目标业务平台！");
                    }
                    if (null == newPlatForm || newPlatForm.size() != 1 || null == newPlatForm.get(0).getBusplatform()) {
                        throw new ProcessException("未找到目标业务平台，请核查目标业务平台！");
                    }
                    refundPriceDiffDetail.setOldBrandCode(oldPlatForm.get(0).getBusplatform());
                    refundPriceDiffDetail.setNewBrandCode(newPlatForm.get(0).getBusplatform());
                }

                Map<String,String> par = new HashedMap();
                par.put("oldMerid",refundPriceDiffDetail.getOldMachineId());
                par.put("newMerId",refundPriceDiffDetail.getNewMachineId());
                par.put("newMerType",platFormMapper.selectPlatType(newActivity.getPlatform()));
                par.put("oldMerType",platFormMapper.selectPlatType(oldActivity.getPlatform()));
                if(!termMachineService.checkModleIsEq(par,refundPriceDiffDetail.getPlatformType())){
                    throw new MessageException(refundPriceDiffDetail.getBeginSn()+"--"+refundPriceDiffDetail.getEndSn()+":("+oldActivity.getActivityName()+")不支持互换("+newActivity.getActivityName()+")");
                }
                int priceDiffDetailInsert = refundPriceDiffDetailMapper.insert(refundPriceDiffDetail);
                if(priceDiffDetailInsert!=1){
                    log.info("插入补退差价详情表异常");
                    throw new MessageException("保存失败");
                }
                setPlatform.add(refundPriceDiffDetail.getPlatformType());
                setOldOrgId.add(refundPriceDiffDetail.getOldOrgId());
            }

            if(setPlatform.size()>1){
                log.info("申请sn所属平台必须一致：{}", setPlatform);
                throw new ProcessException("申请sn所属平台必须一致");
            }

            if(setOldOrgId.size() > 1){
                log.info("仅支持单品牌的活动调整申请：{}", setOldOrgId);
                throw new ProcessException("仅支持单品牌的活动调整申请。");
            }

            //校验是否可以调整
            AgentResult synOrVerifyResult = termMachineService.synOrVerifyCompensate(refundPriceDiffDetailList, "check", "1");
            if(!synOrVerifyResult.isOK()){
                throw new ProcessException(synOrVerifyResult.getMsg());
            }

            //业务系统锁定完成，后续异常问题需解锁业务系统锁定的SN
            try {
                String platformType = refundPriceDiffDetailList.get(0).getPlatformType();
                if (PlatformType.whetherPOS(platformType)) {
                    String paidSN = "";
                    boolean paidFlag = false;
                    JSONObject resData =  (JSONObject)synOrVerifyResult.getData();
                    List<Map<String,Object>> resultList = (List<Map<String,Object>>)resData.get("resultList");
                    for (Map<String, Object> stringObjectMap : resultList) {
                        String serialNumber = String.valueOf(stringObjectMap.get("serialNumber"));
                        for (ORefundPriceDiffDetail refundPriceDiffDetail : refundPriceDiffDetailList) {
                            if(serialNumber.equals(refundPriceDiffDetail.getId())){
                                Map<String, Object> oldOrganMap = JsonUtil.objectToMap(stringObjectMap.get("oldOrgan"));
                                Map<String, Object> newOrganMap = JsonUtil.objectToMap(stringObjectMap.get("newOrgan"));
                                String oldSupDorgId = String.valueOf(oldOrganMap.get("oldSupDorgId"));
                                String oldSupDorgName = String.valueOf(oldOrganMap.get("oldSupDorgName"));
                                String newSupDorgId = String.valueOf(newOrganMap.get("newSupDorgId"));
                                String newSupDorgName = String.valueOf(newOrganMap.get("newSupDorgName"));
                                String newOrgName = String.valueOf(newOrganMap.get("newOrgName"));
                                String oldOrgName = String.valueOf(oldOrganMap.get("oldOrgName"));
                                if(StringUtils.isNotBlank(oldSupDorgId) && !oldSupDorgId.equals("null")) {
                                    refundPriceDiffDetail.setOldSupdOrgId(oldSupDorgId);
                                }
                                if(StringUtils.isNotBlank(oldSupDorgName) && !oldSupDorgName.equals("null")) {
                                    refundPriceDiffDetail.setOldSupdOrgName(oldSupDorgName);
                                }
                                if(StringUtils.isNotBlank(newSupDorgId) && !newSupDorgId.equals("null")) {
                                    refundPriceDiffDetail.setNewSupdOrgId(newSupDorgId);
                                }
                                if(StringUtils.isNotBlank(newSupDorgName) && !newSupDorgName.equals("null")) {
                                    refundPriceDiffDetail.setNewSupdOrgName(newSupDorgName);
                                }
                                if(StringUtils.isNotBlank(newOrgName) && !newOrgName.equals("null")) {
                                    refundPriceDiffDetail.setNewOrgName(newOrgName);
                                }
                                if(StringUtils.isNotBlank(oldOrgName) && !oldOrgName.equals("null")) {
                                    refundPriceDiffDetail.setOldOrgName(oldOrgName);
                                }
                                if (null != stringObjectMap.get("allPayStatus") && String.valueOf(stringObjectMap.get("allPayStatus")).equals("1")) {
                                    paidSN += refundPriceDiffDetail.getBeginSn() + "-" + refundPriceDiffDetail.getEndSn() + ",";
                                    paidFlag = true;
                                    refundPriceDiffDetail.setPayStatus("1");
                                    if (refundPriceDiffDetail.getNewBrandCode().equals(refundPriceDiffDetail.getOldBrandCode())) {
                                        throw new ProcessException("SN"+refundPriceDiffDetail.getBeginSn()+"-"+refundPriceDiffDetail.getEndSn()+"已付状态下不允许转活动");
                                    }
                                    //品牌判断,已付状态下固定品牌可以划拨
                                    List<AgentBusInfo> oldBusInfoList = agentBusInfoMapper.queryBusinfo(FastMap.fastMap("busNum", refundPriceDiffDetail.getOldOrgId()));
                                    List<AgentBusInfo> newBusInfoList = agentBusInfoMapper.queryBusinfo(FastMap.fastMap("busNum", refundPriceDiffDetail.getNewOrgId()));
                                    Dict dict = dictOptionsService.findDictByValue(DictGroup.ACTIVITY_ADJUST.name(), oldBusInfoList.get(0).getBusPlatform(), newBusInfoList.get(0).getBusPlatform());
                                    if (null == dict) {
                                        throw new ProcessException("SN"+refundPriceDiffDetail.getBeginSn()+"-"+refundPriceDiffDetail.getEndSn()+"已付状态下不允许转活动");
                                    }
                                }
                                if(refundPriceDiffDetailMapper.updateByPrimaryKeySelective(refundPriceDiffDetail) != 1){
                                    throw new ProcessException("更新返回数据失败");
                                }
                            }
                        }
                    }
                    //第一次提交，已付。提示用户
                    if (paidFlag && !submitFlag) {
                        throw new ProcessException("POSTIPS", "SN:" + paidSN + "为已付，不允许换活动，本次支持划拨。点击确认后，执行下一步流程！");
                    }
                } else if (PlatformType.RDBPOS.getValue().equals(platformType)) {
                    List<Map<String, Object>> resultList = (List<Map<String, Object>>) synOrVerifyResult.getData();
                    for (Map<String, Object> stringObjectMap : resultList) {
                        String oldOrgId = String.valueOf(stringObjectMap.get("oldOrgId"));
                        for (ORefundPriceDiffDetail refundPriceDiffDetail : refundPriceDiffDetailList) {
                            if (oldOrgId.equals(refundPriceDiffDetail.getOldOrgId())) {
                                String oldSupDorgId = String.valueOf(stringObjectMap.get("oldSupDorgId"));
                                String oldSupDorgName = String.valueOf(stringObjectMap.get("oldSupDorgName"));
                                String oldOrgName = String.valueOf(stringObjectMap.get("oldOrgName"));
                                String newSupDorgId = String.valueOf(stringObjectMap.get("oldSupDorgId"));
                                String newSupDorgName = String.valueOf(stringObjectMap.get("oldSupDorgName"));
                                String newOrgName = String.valueOf(stringObjectMap.get("oldOrgName"));
                                if (StringUtils.isNotBlank(oldSupDorgId) && !oldSupDorgId.equals("null")) {
                                    refundPriceDiffDetail.setOldSupdOrgId(oldSupDorgId);
                                }
                                if (StringUtils.isNotBlank(oldSupDorgName) && !oldSupDorgName.equals("null")) {
                                    refundPriceDiffDetail.setOldSupdOrgName(oldSupDorgName);
                                }
                                if (StringUtils.isNotBlank(oldOrgName) && !oldOrgName.equals("null")) {
                                    refundPriceDiffDetail.setOldOrgName(oldOrgName);
                                }
                                if (StringUtils.isNotBlank(newSupDorgId) && !newSupDorgId.equals("null")) {
                                    refundPriceDiffDetail.setNewSupdOrgId(newSupDorgId);
                                }
                                if (StringUtils.isNotBlank(newSupDorgName) && !newSupDorgName.equals("null")) {
                                    refundPriceDiffDetail.setNewSupdOrgName(newSupDorgName);
                                }
                                if (StringUtils.isNotBlank(newOrgName) && !newOrgName.equals("null")) {
                                    refundPriceDiffDetail.setNewOrgName(newOrgName);
                                }
                                int i = refundPriceDiffDetailMapper.updateByPrimaryKeySelective(refundPriceDiffDetail);
                                if (i != 1) {
                                    throw new ProcessException("瑞大宝调整活动更新不差价明细失败！");
                                }
                            }
                        }
                    }
                } else if (PlatformType.SSPOS.getValue().equals(platformType)) {
                    String paidSN = "";
                    boolean paidFlag = false;
                    JSONObject resData =  (JSONObject)synOrVerifyResult.getData();
                    List<Map<String,Object>> resultList = (List<Map<String,Object>>)resData.get("resultList");
                    for (Map<String, Object> stringObjectMap : resultList) {
                        String serialNumber = String.valueOf(stringObjectMap.get("serialNumber"));
                        for (ORefundPriceDiffDetail refundPriceDiffDetail : refundPriceDiffDetailList) {
                            if(serialNumber.equals(refundPriceDiffDetail.getId())){
                                Map<String, Object> oldOrganMap = JsonUtil.objectToMap(stringObjectMap.get("oldOrgan"));
                                Map<String, Object> newOrganMap = JsonUtil.objectToMap(stringObjectMap.get("newOrgan"));
                                String oldSupDorgId = String.valueOf(oldOrganMap.get("oldSupDorgId"));
                                String oldSupDorgName = String.valueOf(oldOrganMap.get("oldSupDorgName"));
                                String newSupDorgId = String.valueOf(newOrganMap.get("newSupDorgId"));
                                String newSupDorgName = String.valueOf(newOrganMap.get("newSupDorgName"));
                                String newOrgName = String.valueOf(newOrganMap.get("newOrgName"));
                                String oldOrgName = String.valueOf(oldOrganMap.get("oldOrgName"));
                                if(StringUtils.isNotBlank(oldSupDorgId) && !oldSupDorgId.equals("null")) {
                                    refundPriceDiffDetail.setOldSupdOrgId(oldSupDorgId);
                                }
                                if(StringUtils.isNotBlank(oldSupDorgName) && !oldSupDorgName.equals("null")) {
                                    refundPriceDiffDetail.setOldSupdOrgName(oldSupDorgName);
                                }
                                if(StringUtils.isNotBlank(newSupDorgId) && !newSupDorgId.equals("null")) {
                                    refundPriceDiffDetail.setNewSupdOrgId(newSupDorgId);
                                }
                                if(StringUtils.isNotBlank(newSupDorgName) && !newSupDorgName.equals("null")) {
                                    refundPriceDiffDetail.setNewSupdOrgName(newSupDorgName);
                                }
                                if(StringUtils.isNotBlank(newOrgName) && !newOrgName.equals("null")) {
                                    refundPriceDiffDetail.setNewOrgName(newOrgName);
                                }
                                if(StringUtils.isNotBlank(oldOrgName) && !oldOrgName.equals("null")) {
                                    refundPriceDiffDetail.setOldOrgName(oldOrgName);
                                }
                                if (null != stringObjectMap.get("allPayStatus") && String.valueOf(stringObjectMap.get("allPayStatus")).equals("1")) {
                                    paidSN += refundPriceDiffDetail.getBeginSn() + "-" + refundPriceDiffDetail.getEndSn() + ",";
                                    paidFlag = true;
                                    refundPriceDiffDetail.setPayStatus("1");
                                    if (refundPriceDiffDetail.getNewBrandCode().equals(refundPriceDiffDetail.getOldBrandCode())) {
                                        throw new ProcessException("SN"+refundPriceDiffDetail.getBeginSn()+"-"+refundPriceDiffDetail.getEndSn()+"已付状态下不允许转活动");
                                    }
                                    //品牌判断,已付状态下固定品牌可以划拨
                                    List<AgentBusInfo> oldBusInfoList = agentBusInfoMapper.queryBusinfo(FastMap.fastMap("busNum", refundPriceDiffDetail.getOldOrgId()));
                                    List<AgentBusInfo> newBusInfoList = agentBusInfoMapper.queryBusinfo(FastMap.fastMap("busNum", refundPriceDiffDetail.getNewOrgId()));
                                    Dict dict = dictOptionsService.findDictByValue(DictGroup.ACTIVITY_ADJUST.name(), oldBusInfoList.get(0).getBusPlatform(), newBusInfoList.get(0).getBusPlatform());
                                    if (null == dict) {
                                        throw new ProcessException("SN"+refundPriceDiffDetail.getBeginSn()+"-"+refundPriceDiffDetail.getEndSn()+"已付状态下不允许转活动");
                                    }
                                }
                                if(refundPriceDiffDetailMapper.updateByPrimaryKeySelective(refundPriceDiffDetail) != 1){
                                    throw new ProcessException("更新返回数据失败");
                                }
                            }
                        }
                    }
                    //第一次提交，已付。提示用户
                    if (paidFlag && !submitFlag) {
                        throw new ProcessException("POSTIPS", "SN:" + paidSN + "为已付，不允许换活动，本次支持划拨。点击确认后，执行下一步流程！");
                    }
                }
            } catch (Exception e) {
                termMachineService.unFreezeCompensate(FastMap.fastMap("taskId", refundPriceDiffDetailList.get(0).getRefundPriceDiffId()), refundPriceDiffDetailList.get(0).getPlatformType());
                log.info("历史换活动解冻异常:{}", e.getMessage());
                e.printStackTrace();
                throw new ProcessException(e.getMessage());
            }
            return AgentResult.ok(priceDiffId);
        } catch (MessageException e) {
            log.info("活动调整保存失败");
            throw new ProcessException(e.getMessage());
        }catch (ProcessException e) {
            e.printStackTrace();
            log.info("活动调整保存失败");
            if (null != e.getCode() && e.getCode().equals("POSTIPS")) {
                throw new ProcessException(e.getCode(), e.getMsg());
            } else {
                throw new ProcessException(e.getMessage());
            }
        } catch (Exception e) {
            log.info("活动调整保存失败");
            throw new ProcessException(e.getMessage());
        }
    }



    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startCompensateActiviy(String id, String cuser) throws Exception {

        if (StringUtils.isBlank(id)) {
            log.info("活动调整提交审批,订单ID为空{}:{}", id, cuser);
            return AgentResult.fail("活动调整提交审批，订单ID为空");
        }
        if (StringUtils.isBlank(cuser)) {
            log.info("活动调整提交审批,操作用户为空{}:{}", id, cuser);
            return AgentResult.fail("活动调整审批中，操作用户为空");
        }
        //更新审批中
        ORefundPriceDiff oRefundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(id);
        if (oRefundPriceDiff.getReviewStatus().equals(AgStatus.Approving.name())) {
            log.info("活动调整提交审批,禁止重复提交审批{}:{}", id, cuser);
            return AgentResult.fail("活动调整提交审批，禁止重复提交审批");
        }

        if (!oRefundPriceDiff.getStatus().equals(Status.STATUS_1.status)) {
            log.info("活动调整提交审批,代理商信息已失效{}:{}", id, cuser);
            return AgentResult.fail("活动调整信息已失效");
        }

        ORefundPriceDiff updateRefundPriceDiff = new ORefundPriceDiff();
        updateRefundPriceDiff.setVersion(oRefundPriceDiff.getVersion());
        updateRefundPriceDiff.setId(id);
        updateRefundPriceDiff.setReviewStatus(AgStatus.Approving.status);
        int i = refundPriceDiffMapper.updateByPrimaryKeySelective(updateRefundPriceDiff);
        if (1 != i) {
            log.info("活动调整提交审批，更新订单基本信息失败{}:{}", id, cuser);
            throw new MessageException("活动调整提交审批，更新活动调整基本信息失败");
        }

        Map startPar = agentEnterService.startPar(cuser);
        if (null == startPar) {
            log.info("========用户{}{}启动部门参数为空", id, cuser);
            throw new MessageException("启动部门参数为空!");
        }
        String party = String.valueOf(startPar.get("party"));
        String workId;
        //根据不同的部门信息启动不同的流程
        if(agentService.isAgent(cuser).isOK()){
            workId = dictOptionsService.getApproveVersion("agentCompensation");
        }else{
            workId = dictOptionsService.getApproveVersion("compensation");
        }
        //启动审批
        String proce = activityService.createDeloyFlow(null, workId, null, null, startPar);
        if (proce == null) {
            log.info("活动调整提交审批，审批流启动失败{}:{}", id, cuser);
            throw new MessageException("审批流启动失败!");
        }
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(id);
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.COMPENSATE.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(oRefundPriceDiff.getAgentId());
        record.setDataShiro(BusActRelBusType.COMPENSATE.key);
        List<Map<String, Object>> maps = iUserService.orgCode(Long.valueOf(cuser));
        if(maps!=null){
            Map<String, Object> stringObjectMap = maps.get(0);
            record.setAgDocPro(stringObjectMap.get("ORGID")+"");
            if(null!=stringObjectMap.get("isRegion") && (Boolean)stringObjectMap.get("isRegion")) {
                record.setAgDocDistrict(stringObjectMap.get("ORGPID") + "");
            }else if(null!=stringObjectMap.get("ppidorgcodeisRegion") && (Boolean)stringObjectMap.get("ppidorgcodeisRegion")) {
                record.setAgDocDistrict(stringObjectMap.get("ORGPPID") + "");
            }
        }else{
            throw new MessageException("未获取到部门编号!");
        }
        if(StringUtils.isBlank(record.getAgDocDistrict())){
            throw new MessageException("未获取到部门编号!");
        }
        Agent agent = agentMapper.selectByPrimaryKey(oRefundPriceDiff.getAgentId());
        if(null!=agent)
            record.setAgentName(agent.getAgName());
        if (1 != busActRelMapper.insertSelective(record)) {
            log.info("订单提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
            throw new MessageException("审批流启动失败:添加审批关系失败");
        }

        AgentResult agentResult = cashReceivablesService.startProcing(CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId(),cuser);
        if(!agentResult.isOK()){
            log.info("插入补退差价更新打款信息失败");
            throw new MessageException("补退差价更新打款信息失败");
        }
        return AgentResult.ok();
    }


    /**
     * 查询当前商品列表
     * @param orderId
     * @return
     * @throws MessageException
     */
    @Override
    public List<OSubOrder> queryOrder(String orderId)throws MessageException{

        OOrder oOrder = orderMapper.selectByPrimaryKey(orderId);
        if(oOrder==null){
            throw new MessageException("订单不存在");
        }
        if(oOrder.getStatus().compareTo(Status.STATUS_0.status)==0){
            throw new MessageException("订单不存在");
        }
        OSubOrderExample oSubOrderExample = new OSubOrderExample();
        OSubOrderExample.Criteria criteria = oSubOrderExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andOrderIdEqualTo(oOrder.getId());
        List<OSubOrder> oSubOrders = subOrderMapper.selectByExample(oSubOrderExample);
        return oSubOrders;
    }



    /**
     * 查询当前活动和可变更的活动
     * @param subOrderId
     * @return
     * @throws MessageException
     */
    @Override
    public OSubOrderActivity queryActivity(String subOrderId)throws MessageException{

        OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
        OSubOrderActivityExample.Criteria criteria = oSubOrderActivityExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andSubOrderIdEqualTo(subOrderId);
        List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
        if(oSubOrderActivities==null){
            throw new MessageException("活动不存在");
        }
        if(oSubOrderActivities.size()==0){
            throw new MessageException("活动不存在");
        }
        if(oSubOrderActivities.size()!=1){
            throw new MessageException("采购和活动不唯一");
        }
        OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);
        return oSubOrderActivity;

    }

    /**
     * 计算变更差价
     * @param oldActivityId
     * @param activityId
     * @param proNum
     * @return
     */
    @Override
    public BigDecimal calculatePriceDiff(String oldActivityId,String activityId,BigDecimal proNum)throws Exception{
        BigDecimal calculatePrice = BigDecimal.ZERO;
        OActivity oldActivity = activityMapper.selectByPrimaryKey(oldActivityId);
        if(null==oldActivity){
            throw new MessageException("旧活动信息不存在");
        }
        OActivity newActivity = activityMapper.selectByPrimaryKey(activityId);
        if(null==newActivity){
            throw new MessageException("新活动信息不存在");
        }
        BigDecimal oldActivityPrice = oldActivity.getPrice().multiply(proNum);
        BigDecimal newActivityPrice = newActivity.getPrice().multiply(proNum);
        calculatePrice = oldActivityPrice.subtract(newActivityPrice);

        return calculatePrice;
    }


    /**
     * 完成处理
     * @param proIns
     * @param agStatus
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressCompensateActivity(String proIns, BigDecimal agStatus)throws Exception{

        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(proIns).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() != 1) {
            log.info("审批任务结束{}{}，未找到审批中的审批和数据关系", proIns, agStatus);
            return null;
        }
        BusActRel rel = list.get(0);
        ORefundPriceDiff oRefundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(rel.getBusId());
        if (null==oRefundPriceDiff) {
            log.info("审批任务结束{}{}，ORefundPriceDiff为null", proIns, agStatus);
            return null;
        }
        //更新补差价申请审批状态
        oRefundPriceDiff.setReviewStatus(agStatus);
        oRefundPriceDiff.setuTime(new Date());
        oRefundPriceDiff.setAppTime(oRefundPriceDiff.getuTime());
        int i = refundPriceDiffMapper.updateByPrimaryKeySelective(oRefundPriceDiff);
        if(i!=1){
            throw new ProcessException("更新活动调整数据申请失败");
        }

        ORefundPriceDiffDetailExample oRefundPriceDiffDetailExample = new ORefundPriceDiffDetailExample();
        ORefundPriceDiffDetailExample.Criteria criteria = oRefundPriceDiffDetailExample.createCriteria();
        criteria.andRefundPriceDiffIdEqualTo(rel.getBusId());
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<ORefundPriceDiffDetail> oRefundPriceDiffDetails = refundPriceDiffDetailMapper.selectByExample(oRefundPriceDiffDetailExample);

        AgentResult agentResult;
        //审批拒绝
        if(agStatus.compareTo(AgStatus.Refuse.getValue())==0){
            //处理缴款信息
            agentResult = cashReceivablesService.refuseProcing(CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId(),oRefundPriceDiff.getcUser());
            //取消抵扣
            agentResult = orderOffsetService.OffsetArrearsCancle(oRefundPriceDiff.getMachOweAmt(), OffsetPaytype.DDMD.code, oRefundPriceDiff.getId());
            if (!agentResult.isOK()) throw new MessageException("换活动抵扣欠款取消失败!id"+oRefundPriceDiff.getId());
            //审批拒绝，解锁业务系统sn
            /*AgentResult unFreezeRes = termMachineService.unFreezeCompensate(FastMap.fastMap("taskId", oRefundPriceDiffDetails.get(0).getRefundPriceDiffId()), oRefundPriceDiffDetails.get(0).getPlatformType());
            if (!unFreezeRes.isOK()) throw new MessageException(unFreezeRes.getMsg());*/
            termMachineService.unFreezeCompensate(FastMap.fastMap("taskId", oRefundPriceDiffDetails.get(0).getRefundPriceDiffId()), oRefundPriceDiffDetails.get(0).getPlatformType());
        }
        //审批通过
        if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
            agentResult = cashReceivablesService.finishProcing(CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId(),oRefundPriceDiff.getcUser());
            if(!agentResult.isOK()){
                throw new ProcessException("更新打款记录失败");
            }

            oRefundPriceDiffDetails.forEach(row->{
                try {
                    row.setAppTime(new Date());
                    row.setSendStatus(LogisticsSendStatus.send_ing.code);
                    OActivity activity_old = activityMapper.selectByPrimaryKey(row.getActivityFrontId());
                    OActivity activity_new = activityMapper.selectByPrimaryKey(row.getActivityRealId());
                    int j = refundPriceDiffDetailMapper.updateByPrimaryKeySelective(row);
                    if(j!=1){
                        throw new ProcessException("活动调整更新失败");
                    }
                    row.setOldMachineId(activity_old.getBusProCode());
                    row.setNewMachineId(activity_new.getBusProCode());
                    //变更后活动
                    OActivity newActivity = activityMapper.selectByPrimaryKey(row.getActivityRealId());
                    if (null == newActivity) throw new ProcessException("查询新活动失败");
                    //变更前活动
                    OActivity oldActivity = activityMapper.selectByPrimaryKey(row.getActivityFrontId());
                    if (oldActivity == null) throw new ProcessException("查询新活动失败");
                    //业务平台
                    PlatForm platForm = platFormService.selectByPlatformNum(oldActivity.getPlatform());
                    if (platForm == null) throw new ProcessException("未找到:" + oldActivity.getPlatform() + "业务平台");

                    //封装不同平台所需的不同参数
                    if (PlatformType.whetherPOS(platForm.getPlatformType()) || PlatformType.SSPOS.getValue().equals(platForm.getPlatformType())){
                        List<PlatForm> oldPlatForm =  platFormMapper.queryPlatFormByMap(FastMap.fastMap("platformType", platForm.getPlatformType()).putKeyV("busNum",row.getOldOrgId()));
                        List<PlatForm> newPlatForm =  platFormMapper.queryPlatFormByMap(FastMap.fastMap("platformType", platForm.getPlatformType()).putKeyV("busNum",row.getNewOrgId()));
                        if (null == oldPlatForm || oldPlatForm.size() != 1 || null == oldPlatForm.get(0).getBusplatform()) {
                            throw new ProcessException("未找到原目标业务平台，请核查原目标业务平台！");
                        }
                        if (null == newPlatForm || newPlatForm.size() != 1 || null == newPlatForm.get(0).getBusplatform()) {
                            throw new ProcessException("未找到目标业务平台，请核查目标业务平台！");
                        }
                        row.setOldBrandCode(oldPlatForm.get(0).getBusplatform());
                        row.setNewBrandCode(newPlatForm.get(0).getBusplatform());
                    }
                }catch (ProcessException e) {
                    e.printStackTrace();
                    throw new ProcessException(e.getMessage());
                }catch (Exception e) {
                    e.printStackTrace();
                    throw new ProcessException("处理失败");
                }
            });
            //提交抵扣
            agentResult = orderOffsetService.OffsetArrearsCommit(oRefundPriceDiff.getMachOweAmt(), OffsetPaytype.DDMD.code, oRefundPriceDiff.getId());
            if (!agentResult.isOK()){
                log.error("换活动抵扣欠款失败!id"+oRefundPriceDiff.getId());
                throw new MessageException("换活动抵扣欠款失败!id"+oRefundPriceDiff.getId()+","+agentResult.getMsg());
            }
            //调用接口调整
            AgentResult synOrVerifyResult = termMachineService.synOrVerifyCompensate(oRefundPriceDiffDetails, "adjust", "1");
            if(!synOrVerifyResult.isOK()) throw new ProcessException(synOrVerifyResult.getMsg());
        }
        return AgentResult.ok();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult compensateAmtEdit(ORefundPriceDiff oRefundPriceDiff, List<ORefundPriceDiffDetail> refundPriceDiffDetailList,
                                         List<String> refundPriceDiffFile, String cUser, List<OCashReceivablesVo> cashReceivablesVoList) {
        try {
            if(null==refundPriceDiffDetailList){
                throw new ProcessException("活动调整明细数据为空");
            }
            if(null==oRefundPriceDiff){
                throw new ProcessException("活动调整金额数据为空");
            }
            refundPriceDiffDetailList.forEach(row->{
                //查询最新活动
                OActivity oActivity = activityMapper.selectByPrimaryKey(row.getActivityRealId());
                ORefundPriceDiffDetail oRefundPriceDiffDetail = refundPriceDiffDetailMapper.selectByPrimaryKey(row.getId());
                oRefundPriceDiffDetail.setActivityRealId(row.getActivityRealId());
                oRefundPriceDiffDetail.setActivityName(oActivity.getActivityName());
                oRefundPriceDiffDetail.setActivityWay(oActivity.getActivityWay());
                oRefundPriceDiffDetail.setActivityRule(oActivity.getActivityRule());
                oRefundPriceDiffDetail.setPrice(oActivity.getPrice());
                oRefundPriceDiffDetail.setProId(row.getProId());
                oRefundPriceDiffDetail.setProName(row.getProName());
                int i = refundPriceDiffDetailMapper.updateByPrimaryKeySelective(oRefundPriceDiffDetail);
                if(i!=1){
                    throw new ProcessException("修改活动调整数据失败");
                }
            });
            if(null==oRefundPriceDiff.getId()){
                throw new ProcessException("活动调整数据id为空");
            }
            BigDecimal belowPayAmt = new BigDecimal(0);
            BigDecimal shareDeductAmt = new BigDecimal(0);
            for (OCashReceivablesVo oCashReceivablesVo : cashReceivablesVoList) {
                if(oCashReceivablesVo.getPayType().equals(PayType.YHHK.code)){
                    belowPayAmt = belowPayAmt.add(oCashReceivablesVo.getAmount());
                }else if(oCashReceivablesVo.getPayType().equals(PayType.FRDK.code)){
                    shareDeductAmt = shareDeductAmt.add(oCashReceivablesVo.getAmount());
                }
            }
            oRefundPriceDiff.setBelowPayAmt(belowPayAmt);
            oRefundPriceDiff.setShareDeductAmt(shareDeductAmt);
            int k = refundPriceDiffMapper.updateByPrimaryKeySelective(oRefundPriceDiff);
            if(k!=1){
                throw new ProcessException("更新活动调整数据失败");
            }
            //附件修改
            if(null!=refundPriceDiffFile){
                AttachmentRelExample attachmentRelExample = new AttachmentRelExample();
                AttachmentRelExample.Criteria criteria = attachmentRelExample.createCriteria();
                criteria.andSrcIdEqualTo(oRefundPriceDiff.getId());
                criteria.andBusTypeEqualTo(AttachmentRelType.ActivityEdit.name());
                List<AttachmentRel> attachmentRels = attachmentRelMapper.selectByExample(attachmentRelExample);
                attachmentRels.forEach(row->{
                    row.setStatus(Status.STATUS_0.status);
                    int i = attachmentRelMapper.updateByPrimaryKeySelective(row);
                    if (1 != i) {
                        log.info("删除活动变更附件关系失败");
                        throw new ProcessException("删除附件失败");
                    }
                });

                refundPriceDiffFile.forEach(row->{
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(row);
                    record.setSrcId(oRefundPriceDiff.getId());
                    record.setcUser(cUser);
                    record.setcTime(Calendar.getInstance().getTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.ActivityEdit.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    int i = attachmentRelMapper.insertSelective(record);
                    if (1 != i) {
                        log.info("活动变更附件关系失败");
                        throw new ProcessException("系统异常");
                    }
                });
            }
            ORefundPriceDiff refundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(oRefundPriceDiff.getId());
            cashReceivablesService.addOCashReceivables(cashReceivablesVoList,cUser,refundPriceDiff.getAgentId(),CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("活动调整修改失败");
            throw new ProcessException("活动调整修改失败");
        }
        return AgentResult.ok();
    }

}
