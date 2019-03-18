package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.order.OActivityMapper;
import com.ryx.credit.dao.order.ORefundPriceDiffDetailMapper;
import com.ryx.credit.dao.order.ORefundPriceDiffMapper;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OCashReceivablesService;
import com.ryx.credit.service.order.OldCompensateService;
import com.ryx.credit.service.order.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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



    @Override
    public List<Map<String,Object>> getOrderMsgByExcel(List<List<Object>> excelList){

        List<Map<String,Object>> resultList = new ArrayList<>();
        for (List<Object> excel : excelList) {
            Map<String,Object> resultMap = new HashMap();
            String snBegin = "";
            String snEnd = "";
            String count = "";
            String proModel = "";
            try {
                snBegin =  String.valueOf(excel.get(0));
                snEnd =  String.valueOf(excel.get(1));
                count =  String.valueOf(excel.get(2));
                proModel =  String.valueOf(excel.get(3));
            } catch (Exception e) {
                throw new ProcessException("导入解析文件失败");
            }
            Dict modelType = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(),proModel);
            if(modelType==null){
                throw new ProcessException("导入类型错误");
            }
            if(proModel.equals("MPOS")){
                try {
                    AgentResult agentResult = termMachineService.querySnMsg(PlatformType.MPOS,snBegin, snEnd);
                    if(!agentResult.isOK()){
                        throw new ProcessException("查询手刷失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                try {
//                    AgentResult agentResult = termMachineService.querySnMsg(PlatformType.POS,snBegin, snEnd);
//                    if(!agentResult.isOK()){
//                        throw new ProcessException("查询pos失败");
//                    }
//                    JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
//                    JSONObject data = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
//                    System.out.println(String.valueOf(data.get("termMachineList")));
//                    List<Map<String,Object>> termMachineListMap = (List<Map<String,Object>>)JSONArray.parse(String.valueOf(data.get("termMachineList")));
//                    if(termMachineListMap.size()!=Integer.parseInt(count)){
//                        log.info("查询pos根据SN号段查询机具信息数量：{},count:{}",termMachineListMap.size(),count);
//                        throw new ProcessException("请检查sn有效性");
//                    }
//                    Map<String,String> actMap = new HashMap<>();
//                    Map<String,String> act111Map = new HashMap<>();
//                    String a = "";
//                    for (Map<String, Object> map : termMachineListMap) {
//                        String posSn = String.valueOf(map.get("posSn"));
//                        String posModel = String.valueOf(map.get("posModel"));
//                        String machineManufName = String.valueOf(map.get("machineManufName"));
//                        String machineId = String.valueOf(map.get("machineId"));
//                        Dict manufaName = dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), machineManufName);
//                        if (manufaName == null) {
//                            throw new ProcessException(machineManufName+"厂商不存在");
//                        }
//                        String manufaValue = manufaName.getdItemvalue();//厂商
//                        actMap.put(posSn,machineId);
//
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ProcessException(e.getMessage());
                }
            }

            OProduct product = new OProduct();
            product.setProType(modelType.getdItemvalue());
            List<Map> proMaps = productService.queryGroupByProCode(product);
            for (Map proMap : proMaps) {
                if(String.valueOf(proMap.get("proName")).equals("流量卡")){
                    proMaps.remove(proMap);
                    break;
                }
            }
            resultMap.put("snBegin",snBegin);
            resultMap.put("snEnd",snEnd);
            resultMap.put("count",count);
            resultMap.put("proMaps",proMaps);
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
    public AgentResult compensateAmtSave(ORefundPriceDiff oRefundPriceDiff, List<ORefundPriceDiffDetail> refundPriceDiffDetailList,
                                         List<String> refundPriceDiffFile, String cUser, List<OCashReceivablesVo> oCashReceivablesVoList){

        try {
//            if(PriceDiffType.DETAIN_AMT.code.equals(oRefundPriceDiff.getApplyCompType())){
//                if(refundPriceDiffFile.size()==0){
//                    return AgentResult.fail("代理商打款必须上传打款凭证");
//                }
//            }
            String priceDiffId = idService.genId(TabId.o_Refund_price_diff);
            oRefundPriceDiff.setId(priceDiffId);
            Date nowDate = new Date();
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
            oRefundPriceDiff.setOrderType(Status.STATUS_2.status);
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
                throw new ProcessException("保存失败");
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
                log.info("退补差价保存打款记录失败1");
                throw new ProcessException("保存打款记录失败");
            }

            refundPriceDiffDetailList.forEach(refundPriceDiffDetail->{
                refundPriceDiffDetail.setAgentId(oRefundPriceDiff.getAgentId());
                refundPriceDiffDetail.setId(idService.genId(TabId.o_Refund_price_diff_d));
                refundPriceDiffDetail.setRefundPriceDiffId(priceDiffId);
//                refundPriceDiffDetail.setFrontPrice(logisticsDetail!=null?new BigDecimal(logisticsDetail.get("SETTLEMENT_PRICE").toString()):new BigDecimal(0));
//                refundPriceDiffDetail.setActivityName(oActivity.getActivityName());
//                refundPriceDiffDetail.setActivityWay(oActivity.getActivityWay());
//                refundPriceDiffDetail.setActivityRule(oActivity.getActivityRule());
//                refundPriceDiffDetail.setPrice(oActivity.getPrice());
//                refundPriceDiffDetail.setVender(oActivity.getVender());
//                refundPriceDiffDetail.setProModel(oActivity.getProModel());
                refundPriceDiffDetail.setcTime(nowDate);
                refundPriceDiffDetail.setuTime(nowDate);
                refundPriceDiffDetail.setsTime(nowDate);
                refundPriceDiffDetail.setcUser(cUser);
                refundPriceDiffDetail.setuUser(cUser);
                refundPriceDiffDetail.setStatus(Status.STATUS_1.status);
                refundPriceDiffDetail.setVersion(Status.STATUS_0.status);
                refundPriceDiffDetail.setOrderType(Status.STATUS_2.status);
                int priceDiffDetailInsert = refundPriceDiffDetailMapper.insert(refundPriceDiffDetail);
                if(priceDiffDetailInsert!=1){
                    log.info("插入补退差价详情表异常");
                    throw new ProcessException("保存失败");
                }
            });

            return AgentResult.ok(priceDiffId);
        } catch (Exception e) {
            log.info("退补差价保存失败");
            throw new ProcessException("退补差价保存失败");
        }
    }



    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startCompensateActiviy(String id, String cuser) throws Exception {

        if (StringUtils.isBlank(id)) {
            log.info("退补差价提交审批,订单ID为空{}:{}", id, cuser);
            return AgentResult.fail("退补差价提交审批，订单ID为空");
        }
        if (StringUtils.isBlank(cuser)) {
            log.info("退补差价提交审批,操作用户为空{}:{}", id, cuser);
            return AgentResult.fail("退补差价审批中，操作用户为空");
        }
        //更新审批中
        ORefundPriceDiff oRefundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(id);
        if (oRefundPriceDiff.getReviewStatus().equals(AgStatus.Approving.name())) {
            log.info("退补差价提交审批,禁止重复提交审批{}:{}", id, cuser);
            return AgentResult.fail("退补差价提交审批，禁止重复提交审批");
        }

        if (!oRefundPriceDiff.getStatus().equals(Status.STATUS_1.status)) {
            log.info("退补差价提交审批,代理商信息已失效{}:{}", id, cuser);
            return AgentResult.fail("退补差价信息已失效");
        }

        ORefundPriceDiff updateRefundPriceDiff = new ORefundPriceDiff();
        updateRefundPriceDiff.setVersion(oRefundPriceDiff.getVersion());
        updateRefundPriceDiff.setId(id);
        updateRefundPriceDiff.setReviewStatus(AgStatus.Approving.status);
        int i = refundPriceDiffMapper.updateByPrimaryKeySelective(updateRefundPriceDiff);
        if (1 != i) {
            log.info("退补差价提交审批，更新订单基本信息失败{}:{}", id, cuser);
            throw new MessageException("退补差价提交审批，更新退补差价基本信息失败");
        }

        Map startPar = agentEnterService.startPar(cuser);
        if (null == startPar) {
            log.info("========用户{}{}启动部门参数为空", id, cuser);
            throw new MessageException("启动部门参数为空!");
        }
        Object party = startPar.get("party");
        //不同的业务类型找到不同的启动流程
        List<Dict> actlist = dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ACT_COMPENSATE.name());
        String workId = null;
        for (Dict dict : actlist) {
            //根据不同的部门信息启动不同的流程
            if(party.equals(dict.getdItemvalue())) {
                workId = dict.getdItemname();
            }
        }
        //启动审批
        String proce = activityService.createDeloyFlow(null, workId, null, null, startPar);
        if (proce == null) {
            log.info("退补差价提交审批，审批流启动失败{}:{}", id, cuser);
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

}
