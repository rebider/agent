package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.machine.entity.ImsTermAdjustDetail;
import com.ryx.credit.machine.entity.ImsTermWarehouseDetail;
import com.ryx.credit.machine.service.ImsTermAdjustDetailService;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.AdjustmentMachineVo;
import com.ryx.credit.machine.vo.LowerHairMachineVo;
import com.ryx.credit.machine.vo.MposSnVo;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OldOrderReturnBusEditVo;
import com.ryx.credit.pojo.admin.vo.OldOrderReturnSubmitProVo;
import com.ryx.credit.pojo.admin.vo.OldOrderReturnVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.BusActRelService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.*;
import org.apache.commons.collections.FastHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


/**
 * 作者：cx
 * 时间：2019/3/6
 * 描述：历史订单退货服务service
 */
@Service("oldOrderReturnService")
public class OldOrderReturnServiceImpl implements OldOrderReturnService {

    private static final Logger logger = LoggerFactory.getLogger(OldOrderReturnServiceImpl.class);


    @Resource(name = "oldOrderReturnService")
    private OldOrderReturnService oldOrderReturnService;
    /**
     * 服务引用
     */
    @Autowired
    private OReturnOrderMapper returnOrderMapper;
    @Resource
    private IdService idService;
    @Autowired
    private OReturnOrderDetailMapper returnOrderDetailMapper;
    @Autowired
    private OReturnOrderRelMapper returnOrderRelMapper;
    @Autowired
    private ODeductCapitalMapper deductCapitalMapper;
    @Resource
    private PlannerService plannerService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Resource
    private OLogisticsService oLogisticsService;
    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;
    @Autowired
    private OLogisticsMapper logisticsMapper;
    @Autowired
    private BusActRelService busActRelService;
    @Autowired
    private OLogisticsDetailMapper logisticsDetailMapper;
    @Autowired
    private OAccountAdjustMapper accountAdjustMapper;
    @Autowired
    private OReceiptProMapper receiptProMapper;
    @Autowired
    private OLogisticsDetailService logisticsDetailService;
    @Autowired
    private OSubOrderMapper oSubOrderMapper;
    @Autowired
    private OLogisticsMapper oLogisticsMapper;
    @Autowired
    private TermMachineService termMachineService;
    @Autowired
    private OOrderMapper oOrderMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private ImsTermAdjustDetailService imsTermAdjustDetailService;
    @Autowired
    private OSubOrderActivityMapper subOrderActivityMapper;
    @Autowired
    private OReceiptProMapper oReceiptProMapper;
    @Autowired
    private IOrderReturnService iOrderReturnService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private OActivityMapper oActivityMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private OSubOrderActivityMapper oSubOrderActivityMapper;
    @Autowired
    private OProductMapper oProductMapper;
    @Autowired
    private RedisService  redisService;



    /**
     * 提交历史订单退货申请
     * @param oldOrderReturnVo sn号段列表
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public AgentResult saveOldReturnOrder(OldOrderReturnVo oldOrderReturnVo)throws Exception{
        List<OldOrderReturnSubmitProVo> oldOrderReturnSubmitProVos = oldOrderReturnVo.getOldOrderReturnSubmitProVoList();
        if(oldOrderReturnSubmitProVos==null || oldOrderReturnSubmitProVos.size()==0){
            return AgentResult.fail("请填写退货sn号码段");
        }
        for (OldOrderReturnSubmitProVo oldOrderReturnSubmitProVo : oldOrderReturnVo.getOldOrderReturnSubmitProVoList()) {
            if(StringUtils.isBlank(oldOrderReturnSubmitProVo.getSnStart())||
               StringUtils.isBlank(oldOrderReturnSubmitProVo.getSnEnd())){
                return AgentResult.fail("SN号码不能为空");
            }
            if(oldOrderReturnSubmitProVo.getReturnCount()==null||
                    oldOrderReturnSubmitProVo.getReturnCount().compareTo(BigDecimal.ZERO)<=0){
                return AgentResult.fail("数量必须大于0");
            }
        }

        Agent agent = agentMapper.selectByPrimaryKey(oldOrderReturnVo.getAgentId());
        //保存审批中的退货申请单
        OReturnOrder oReturnOrder = new OReturnOrder();
        oReturnOrder.setId(idService.genId(TabId.o_return_order));
        oReturnOrder.setRetSchedule(new BigDecimal(RetSchedule.SPZ.code));
        oReturnOrder.setAgentId(agent.getId());
        oReturnOrder.setApplyRemark(oldOrderReturnVo.getRemark());
        oReturnOrder.setRetInvoice(Status.STATUS_0.status);
        oReturnOrder.setRetReceipt(Status.STATUS_0.status);
        oReturnOrder.setReturnAmo(BigDecimal.ZERO);
        oReturnOrder.setGoodsReturnAmo(BigDecimal.ZERO);
        oReturnOrder.setCutAmo(BigDecimal.ZERO);
        oReturnOrder.setRelReturnAmo(BigDecimal.ZERO);
        oReturnOrder.setTakeOutAmo(BigDecimal.ZERO);
        oReturnOrder.setBatchCode(oReturnOrder.getId());
        oReturnOrder.setcTime(Calendar.getInstance().getTime());
        oReturnOrder.setuTime(Calendar.getInstance().getTime());
        oReturnOrder.setcUser(oldOrderReturnVo.getUserId());
        oReturnOrder.setuUser(oldOrderReturnVo.getUserId());
        oReturnOrder.setVersion(BigDecimal.ONE);
        oReturnOrder.setStatus(Status.STATUS_1.status);
        oReturnOrder.setOreturntype(Oreturntype.OLD.code);
        logger.info("历史退货单申请 {} {} {} {}",oldOrderReturnVo.getUserId(),oldOrderReturnVo.getAgentId(),oldOrderReturnVo.getRemark(),oReturnOrder.getId());
        if(1!=returnOrderMapper.insertSelective(oReturnOrder)){
            logger.error("保存历史退货单明细失败 {} {}",oldOrderReturnVo.getUserId(),oldOrderReturnVo.getAgentId());
            throw new MessageException("退货单申请失败");
        }
        //保存提货申请明细
        OReturnOrderDetail oReturnOrderDetail = new OReturnOrderDetail();
        for (OldOrderReturnSubmitProVo oldOrderReturnSubmitProVo : oldOrderReturnVo.getOldOrderReturnSubmitProVoList()) {
            oReturnOrderDetail.setId(idService.genId(TabId.o_return_order_detail));
            oReturnOrderDetail.setReturnId(oReturnOrder.getId());
            oReturnOrderDetail.setAgentId(oldOrderReturnVo.getAgentId());
            oReturnOrderDetail.setProId(oldOrderReturnSubmitProVo.getProId());
            oReturnOrderDetail.setProName(oldOrderReturnSubmitProVo.getProName());
            oReturnOrderDetail.setProType(oldOrderReturnSubmitProVo.getProType());
            oReturnOrderDetail.setReturnCount(oldOrderReturnSubmitProVo.getReturnCount());
            oReturnOrderDetail.setBeginSn(oldOrderReturnSubmitProVo.getSnStart());
            oReturnOrderDetail.setEndSn(oldOrderReturnSubmitProVo.getSnEnd());
            oReturnOrderDetail.setcTime(Calendar.getInstance().getTime());
            oReturnOrderDetail.setuTime(Calendar.getInstance().getTime());
            oReturnOrderDetail.setuUser(oldOrderReturnVo.getUserId());
            oReturnOrderDetail.setStatus(Status.STATUS_1.status);
            oReturnOrderDetail.setVersion(Status.STATUS_1.status);
            if(1!=returnOrderDetailMapper.insertSelective(oReturnOrderDetail)){
                logger.error("保存历史退货单明细失败 {} {}",oldOrderReturnVo.getUserId(),oldOrderReturnVo.getAgentId());
                throw new MessageException("退货单申请失败");
            }
        }
        //启动历史订单退货流程审批
        String processingId = null;
        try {
            processingId = activityService.createDeloyFlow("","historyrefund","","",null);
            if(StringUtils.isBlank(processingId)){
                logger.info("审批流启动失败");
                throw new MessageException("审批流启动失败");
            }
        } catch (ProcessException e) {
            logger.info("审批流启动失败:"+e.getLocalizedMessage());
            e.printStackTrace();
            throw new MessageException("审批流启动失败:"+e.getLocalizedMessage());
        }
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(oReturnOrder.getId());
        record.setActivId(processingId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(oldOrderReturnVo.getUserId());
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.refund.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(agent.getId());
        record.setAgentName(agent.getAgName());
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("历史订单退货流程审批，启动审批异常，添加审批关系失败{}:{}", oReturnOrder.getId(), processingId);
            throw new MessageException("审批流启动失败:添加审批关系失败");
        }
        return AgentResult.ok();
    }

    @Override
    public AgentResult loadOldOrderApproveData(String returnId){
        OReturnOrder oReturnOrder = returnOrderMapper.selectByPrimaryKey(returnId);
        OReturnOrderDetailExample oReturnOrderDetailExample = new OReturnOrderDetailExample();
        oReturnOrderDetailExample.or().andReturnIdEqualTo(returnId).andStatusEqualTo(Status.STATUS_1.status);
        List<OReturnOrderDetail> details  =returnOrderDetailMapper.selectByExample(oReturnOrderDetailExample);
        AgentResult agentResult = AgentResult.ok();
        agentResult.setMapData(FastMap.fastMap("details",details).putKeyV("oReturnOrder",oReturnOrder));
        return agentResult;
    }


    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AgentResult taskApprove(AgentVo agentVo, String userId)throws MessageException {

        //业务部审批提交排单信息，fixme 业务部如果没有排单信息提示必须进行排单
        OReturnOrder oReturnOrder = returnOrderMapper.selectByPrimaryKey(agentVo.getReturnId());
        if(agentVo.getSid().equals(AppConfig.getProperty("old_refund_business1_id",""))) {
            if(StringUtils.isBlank(agentVo.getPlans())){
                throw new MessageException("排单信息不能为空");
            }
            if (StringUtils.isNotBlank(agentVo.getPlans())) {
                try {
                    //保存排单信息
                    AgentResult savePlans_agentResult = iOrderReturnService.savePlans(agentVo, userId);
                    logger.info("历史订单退货保存排单结果:" + savePlans_agentResult.getMsg());
                } catch (MessageException e) {
                    e.printStackTrace();
                    throw new MessageException(e.getMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new MessageException(e.getLocalizedMessage());
                }
            }
        }
        //保存抵扣信息
        if(agentVo.getDeductCapitalList()!=null && agentVo.getDeductCapitalList().size()>0){
            for (ODeductCapital oDeductCapital : agentVo.getDeductCapitalList()) {
                if(null!=oDeductCapital.getcAmount() && oDeductCapital.getcAmount().compareTo(BigDecimal.ZERO)>0) {
                    iOrderReturnService.saveCut(agentVo.getReturnId(), oDeductCapital.getcAmount().toString(), oDeductCapital.getcType());
                }
            }
        }
        //财务二级审批
        if(agentVo.getSid().equals(AppConfig.getProperty("old_refund_finc2_id",""))) {
            //财务最后审批时上传打款凭证,并且是已经执行退款方案
            if (agentVo.getApprovalResult()!=null && ApprovalType.PASS.getValue().equals(agentVo.getApprovalResult())) {
                OAccountAdjustExample oAccountAdjustExample = new OAccountAdjustExample();
                oAccountAdjustExample.or().andSrcIdEqualTo(agentVo.getReturnId()).andAdjustTypeEqualTo(AdjustType.TKTH.adjustType);
                List<OAccountAdjust> oAccountAdjusts = accountAdjustMapper.selectByExample(oAccountAdjustExample);
                if (oAccountAdjusts == null || oAccountAdjusts.size() <= 0) {
                    throw new MessageException("您还未执行退款方案");
                }

                if (oReturnOrder.getRelReturnAmo().compareTo(BigDecimal.ZERO) > 0 && agentVo.getAttachments().length <= 0) {
                    throw new MessageException("有线下退款金额时，必须上传打款凭证");
                }
                AgentResult agentResult = iOrderReturnService.saveAttachments(agentVo, userId);
                if (!agentResult.isOK()) {
                    throw new MessageException(agentResult.getMsg());
                }
            }
        }
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("rs", agentVo.getApprovalResult());
        reqMap.put("approvalOpinion", agentVo.getApprovalOpinion());
        reqMap.put("approvalPerson", userId);
        reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
        reqMap.put("taskId", agentVo.getTaskId());
        //下一个节点参数
        if (org.apache.commons.lang.StringUtils.isNotEmpty(agentVo.getDept())) {
            reqMap.put("dept", agentVo.getDept());
        }
        //传递部门信息
        Map startPar = agentEnterService.startPar(userId);
        if (null != startPar) {
            reqMap.put("party", startPar.get("party"));
        }
        try {
           Map<String,Object> res = activityService.completeTask(agentVo.getTaskId(),reqMap);
           if(res!=null && Boolean.valueOf(res.get("rs")+"")){
               return AgentResult.ok();
           }else{
               logger.info("审批失败{}!",res.get("msg"));
               throw new MessageException(res.get("msg")+"");
           }
        } catch (ProcessException e) {
            e.printStackTrace();
            logger.error("审批失败!",e);
            throw new MessageException("审批任务处理失败！");
        }
    }


    /**
     * 抓取订单中指定的商品的信息，对业务订单审批界面进行补全
     * @param orderId
     * @param proId
     * @return
     */
    @Override
    public AgentResult loadOldOrderReturnDetailInfo(String orderId, String proId) {
        OOrder order = oOrderMapper.selectByPrimaryKey(orderId);
        if(order==null)return AgentResult.fail("未找到该订单");
        OSubOrderExample example = new OSubOrderExample();
        example.or().andOrderIdEqualTo(orderId).andProIdEqualTo(proId).andStatusEqualTo(Status.STATUS_1.status);
        List<OSubOrder> oSubOrderList = oSubOrderMapper.selectByExample(example);
        if(oSubOrderList.size()!=1)return AgentResult.fail("未找到订单["+orderId+"]里的商品");
        OSubOrder subOrder = oSubOrderList.get(0);
        OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
        oSubOrderActivityExample.or().andSubOrderIdEqualTo(subOrder.getId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OSubOrderActivity> oSubOrderActivities = oSubOrderActivityMapper.selectByExample(oSubOrderActivityExample);
        if(oSubOrderActivities.size()!=1)return AgentResult.fail("未找到订单["+orderId+"]里的商品"+subOrder.getProName()+"的活动");
        OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);
        return AgentResult.ok(FastMap
                .fastMap("subOrder",subOrder)
                .putKeyV("order",order)
                .putKeyV("oSubOrderActivity",oSubOrderActivity)
        );
    }


    /**
     * 业务部门完善信息
     * @param oldOrderReturnBusEditVos
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public AgentResult completOldOrderReturnInfo(List<OldOrderReturnBusEditVo> oldOrderReturnBusEditVos,String user)throws Exception{
        String returnid = null;
        BigDecimal all_return_amt = BigDecimal.ZERO;
        for (OldOrderReturnBusEditVo oldOrderReturnBusEditVo : oldOrderReturnBusEditVos) {
            if(StringUtils.isBlank(oldOrderReturnBusEditVo.getOrderid()))throw new MessageException("请先补全订单信息");
            if(StringUtils.isBlank(oldOrderReturnBusEditVo.getActivity()))throw new MessageException("未获取到活动");
            if(StringUtils.isBlank(oldOrderReturnBusEditVo.getProprice()))throw new MessageException("未获取到采购单价");
            if(StringUtils.isBlank(oldOrderReturnBusEditVo.getReturndetailid()))throw new MessageException("未获取到退货明细");
            OProduct product = oProductMapper.selectByPrimaryKey(oldOrderReturnBusEditVo.getProductid());
            OReturnOrderDetail oReturnOrderDetail = returnOrderDetailMapper.selectByPrimaryKey(oldOrderReturnBusEditVo.getReturndetailid());
            oReturnOrderDetail.setOrderId(oldOrderReturnBusEditVo.getOrderid());
            oReturnOrderDetail.setProId(oldOrderReturnBusEditVo.getProductid());
            oReturnOrderDetail.setProName(product.getProName());
            oReturnOrderDetail.setProCode(product.getProCode());
            oReturnOrderDetail.setProType(oldOrderReturnBusEditVo.getModeltype());
            oReturnOrderDetail.setProCom(oldOrderReturnBusEditVo.getManufacturer());
            oReturnOrderDetail.setProPrice(new BigDecimal(oldOrderReturnBusEditVo.getProprice()));
            oReturnOrderDetail.setModel(oldOrderReturnBusEditVo.getPromode());
            oReturnOrderDetail.setOrderPrice(new BigDecimal(oldOrderReturnBusEditVo.getProprice()));
            oReturnOrderDetail.setReturnPrice(new BigDecimal(oldOrderReturnBusEditVo.getProprice()));
            oReturnOrderDetail.setReturnAmt(oReturnOrderDetail.getReturnCount().multiply(oReturnOrderDetail.getReturnPrice()).setScale(2,BigDecimal.ROUND_HALF_UP));
            oReturnOrderDetail.setReturnTime(new Date());
            if(StringUtils.isBlank(returnid))returnid = oldOrderReturnBusEditVo.getReturnid();
             if(1!=returnOrderDetailMapper.updateByPrimaryKeySelective(oReturnOrderDetail)){
                 throw new MessageException("更新明细失败");
             }
            all_return_amt = all_return_amt.add(oReturnOrderDetail.getReturnAmt());
        }
        if(StringUtils.isBlank(returnid))return AgentResult.fail("退货单信息未获取到");
        OReturnOrder oReturnOrder = returnOrderMapper.selectByPrimaryKey(returnid);
        oReturnOrder.setGoodsReturnAmo(all_return_amt);
        oReturnOrder.setReturnAmo(oReturnOrder.getGoodsReturnAmo().add(oReturnOrder.getCutAmo()));
        if(1!=returnOrderMapper.updateByPrimaryKeySelective(oReturnOrder)){
            throw new MessageException("更新退货单失败");
        }
        return AgentResult.ok(FastMap.fastMap("oReturnOrder",oReturnOrder));

    }

    @Transactional(readOnly = false,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public List<String> uploadSnFileList(List<List<Object>> data, String user) throws Exception {
        List<String> list = new ArrayList<>();
        for (List<Object> objectList : data) {
            try {
                AgentResult result =   oldOrderReturnService.uploadSnFileListItem(objectList,user);
                logger.info("导入物流{}成功",objectList.toString());
                list.add("物流["+objectList.toString()+"]"+result.getMsg());
            }catch (MessageException e) {
                e.printStackTrace();
                logger.info("导入物流{}抛出异常{}",objectList.toString(),e.getMsg());
                list.add("物流["+objectList.toString()+"]"+e.getMsg());
                throw e;
            }
            catch (Exception e) {
                e.printStackTrace();
                logger.info("导入物流{}抛出异常",objectList.toString());
                list.add("物流["+objectList.toString()+"]导入异常"+e.getMessage());
                throw new MessageException("物流["+objectList.toString()+"]导入异常");
            }
        }
        return list;
    }

    /**
     * 插入物流
     * @param objectList
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AgentResult uploadSnFileListItem(List<Object> objectList, String user) throws Exception {

        List col = Arrays.asList(ReceiptPlanReturnExportColum.ReceiptPlanExportColum_column.col);
        String planNum = String.valueOf(objectList.get(col.indexOf("PLAN_NUM")));
        String orderId = String.valueOf(objectList.get(col.indexOf("ORDER_ID")));
        String proCode = String.valueOf(objectList.get(col.indexOf("PRO_CODE")));
        String proId = String.valueOf(objectList.get(col.indexOf("PRO_ID")));
        String proName = String.valueOf(objectList.get(col.indexOf("PRO_NAME")));
        String  proType = String.valueOf(objectList.get(col.indexOf("PRO_TYPE")));
        String ACTIVITY_NAME = String.valueOf(objectList.get(col.indexOf("ACTIVITY_NAME")));
        String PLAN_PRO_NUM = String.valueOf(objectList.get(col.indexOf("PLAN_PRO_NUM")));
        String PRO_COM_STRING = String.valueOf(objectList.get(col.indexOf("PRO_COM_STRING")));
        String proCom = String.valueOf(objectList.get(col.indexOf("PRO_COM_STRING")));
        String  sendDate = String.valueOf(objectList.get(col.indexOf("h")));
        String  sendProNum = String.valueOf(objectList.get(col.indexOf("g")));
        String  logCom = String.valueOf(objectList.get(col.indexOf("a")));
        String  wNumber = String.valueOf(objectList.get(col.indexOf("b")));
        String beginSn = String.valueOf(objectList.get(col.indexOf("c")));
        String endSn = String.valueOf(objectList.get(col.indexOf("d")));
        String beginSnCount = String.valueOf(objectList.get(col.indexOf("e")));
        String endSnCount = String.valueOf(objectList.get(col.indexOf("f")));


        if (com.ryx.credit.commons.utils.StringUtils.isBlank(sendDate)) {
            logger.info("发货日期不能为空");
            throw new MessageException("发货日期不能为空");
        }
        if (com.ryx.credit.commons.utils.StringUtils.isBlank(planNum)) {
            logger.info("排单编号为空");
            throw new MessageException("排单编号为空");
        }
        if (com.ryx.credit.commons.utils.StringUtils.isBlank(orderId)){
            logger.info("订单编号为空");
            throw new MessageException("订单编号为空");
        }
        if (com.ryx.credit.commons.utils.StringUtils.isBlank(proCode)) {
            logger.info("商品编号为空");
            throw new MessageException("商品编号为空");
        }
        if (com.ryx.credit.commons.utils.StringUtils.isBlank(proId)) {
            logger.info("商品ID为空");
            throw new MessageException("商品ID为空");
        }
        if (com.ryx.credit.commons.utils.StringUtils.isBlank(sendProNum)) {
            logger.info("请填写发货数量");
            throw new MessageException("请填写发货数量");
        }
        if (com.ryx.credit.commons.utils.StringUtils.isBlank(beginSn)) {
            logger.info("请填写起始SN序列号");
            throw new MessageException("请填写起始SN序列号");
        }
        if (com.ryx.credit.commons.utils.StringUtils.isBlank(endSn)){
            logger.info("请填写结束SN序列号");
            throw new MessageException("请填写结束SN序列号");
        }
        if (!proCom.equals(CardImportType.LD.msg)) {
            if (com.ryx.credit.commons.utils.StringUtils.isBlank(beginSnCount)) {
                throw new MessageException("请填写起始SN位数");
            }
            if (com.ryx.credit.commons.utils.StringUtils.isBlank(endSnCount)) {
                throw new MessageException("请填写结束SN位数");
            }
        }

        if (com.ryx.credit.commons.utils.StringUtils.isBlank(logCom)) {
            logger.info("请填写物流公司");
            throw new MessageException("请填写物流公司");
        }
        if (com.ryx.credit.commons.utils.StringUtils.isBlank(wNumber)) {
            logger.info("请填写物流单号");
            throw new MessageException("请填写物流单号");
        }
        if (com.ryx.credit.commons.utils.StringUtils.isBlank(proType)) {
            logger.info("商品类型不能为空");
            throw new MessageException("商品类型不能为空");
        }
        //校验文档不能更改
        List<Map<String,Object>> listItem = receiptPlanMapper.getReceipPlanList(FastMap.fastMap("PLAN_NUM",planNum));
        if(listItem.size()>0){
            //检查列是否有更改
            AgentResult agentResult = iOrderReturnService.checkRecordPlan(objectList,listItem.get(0));
            if(!agentResult.isOK()){
                logger.info("校验Excel文档失败：[],[]",planNum,agentResult.getMsg());
                throw new MessageException(agentResult.getMsg());
            }
        }else{
            throw new MessageException("排单信息未找到");
        }
        if (beginSnCount.equals("") || endSnCount.equals("")){
            beginSnCount="0";
            endSnCount="0";
        }
        //IDlist检查
        List<String> stringList = oLogisticsService.idList(beginSn, endSn,Integer.parseInt(beginSnCount),Integer.parseInt(endSnCount),proCom);
        if (Integer.valueOf(sendProNum) != stringList.size()) {
            logger.info("请仔细核对发货数量");
            throw new MessageException("请仔细核对发货数量");
        }
        OSubOrderExample example = new OSubOrderExample();
        example.or().andStatusEqualTo(Status.STATUS_1.status).andProIdEqualTo(proId).andOrderIdEqualTo(orderId);
        List<OSubOrder>  subOrders = oSubOrderMapper.selectByExample(example);
        if(subOrders.size()!=1){
            logger.info("订单["+orderId+"]的商品["+proId+"]数量大于1");
            throw new MessageException("订单["+orderId+"]的商品["+proId+"]数量大于1");
        }
        OSubOrder subOrderItem = subOrders.get(0);

        //退货商品活动
        OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
        oSubOrderActivityExample.or().andSubOrderIdEqualTo(subOrderItem.getId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
        if(null==oSubOrderActivities){
            logger.info("查询活动数据错误1");
            throw new MessageException("订单活动查询失败");
        }
        if(0==oSubOrderActivities.size()){
            logger.info("查询活动数据错误2");
            throw new MessageException("订单活动查询失败");
        }
        //退货商品活动临时表
        OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);
        OActivity oActivity = oActivityMapper.selectByPrimaryKey(oSubOrderActivity.getActivityId());
        //物流检查
        OLogisticsExample oLogisticsExample = new OLogisticsExample();
        OLogisticsExample.Criteria OLogisticsExample_criteria1 = oLogisticsExample.createCriteria();
        OLogisticsExample_criteria1.andSnBeginNumEqualTo(beginSn);
        OLogisticsExample_criteria1.andSnEndNumEqualTo(endSn);
        OLogisticsExample_criteria1.andWNumberEqualTo(wNumber);
        OLogisticsExample_criteria1.andLogComEqualTo(logCom);
        List<OLogistics> oLogistics1 = oLogisticsMapper.selectByExample(oLogisticsExample);
        if(oLogistics1.size()!=0){
            logger.info("该商品已发货请勿重复提交");
            throw new MessageException("该商品已发货请勿重复提交");
        }
        //物流信息
        OLogistics oLogistics = new OLogistics();
        oLogistics.setId(idService.genId(TabId.o_logistics));           // 物流ID序列号
        oLogistics.setcUser(user);                                      // 创建人
        oLogistics.setStatus(Status.STATUS_1.status);                   // 默认记录状态为1
        oLogistics.setLogType(LogType.Deliver.getValue());              // 默认物流类型为1
        try {
            oLogistics.setSendDate(DateUtil.sdfDays.parse(sendDate));
        }catch (Exception e){
            try {
                oLogistics.setSendDate(DateUtil.sdf_Days.parse(sendDate));// 物流日期
            }catch (Exception m){
                try {
                    oLogistics.setSendDate(DateUtil.sdf_g_Days.parse(sendDate));
                }catch (Exception c){
                    throw new MessageException("日期格式支持yyyyMMdd 或者yyyy-MM-dd 或者 yyyy/MM/dd");
                }

            }
        }
        oLogistics.setcTime(Calendar.getInstance().getTime());          // 创建时间
        oLogistics.setIsdeall(Status.STATUS_1.status);
        //ID信息
        oLogistics.setReceiptPlanId(planNum); // 排单编号
        oLogistics.setOrderId(orderId);       // 订单编号
        oLogistics.setProId(proId);         // 商品ID
        oLogistics.setProName(proName);       // 商品名称
        oLogistics.setProPrice(subOrderItem.getProRelPrice());//商品单价
        //排单信息
        ReceiptPlan planVo = receiptPlanMapper.selectByPrimaryKey(oLogistics.getReceiptPlanId());
        if(planVo==null)throw new MessageException("排单信息未找到");

        //查询排单数量和已发送数量。如果
        if(planVo.getSendProNum()!=null){
            if(planVo.getSendProNum().add(new BigDecimal(sendProNum)).compareTo(planVo.getPlanProNum())>0){
                throw new MessageException("发货数量已大于排单数量");
            }
        }else{
            if(new BigDecimal(sendProNum).compareTo(planVo.getPlanProNum())>0){
                throw new MessageException("发货数量已大于排单数量");
            }
        }
        //商品信息从排单表里查
        oLogistics.setProCom(planVo.getProCom());// 厂家
        oLogistics.setProType(planVo.getProType());//排单添加商品类型
        oLogistics.setProModel(planVo.getModel());//型号
        oLogistics.setSendNum(new BigDecimal(sendProNum));  // 发货数量
        oLogistics.setLogCom(logCom);       // 物流公司
        oLogistics.setwNumber(wNumber);      // 物流单号
        oLogistics.setSnBeginNum(beginSn);   // 起始SN序列号
        oLogistics.setSnEndNum(endSn);     // 结束SN序列号
        logger.info("导入物流数据============================================{}" , oLogistics.getId(),JSONObject.toJSON(oLogistics));
        if (1 != oLogisticsMapper.insertSelective(oLogistics)) {
            throw new MessageException("排单编号为:"+planNum+"处理，插入物流信息失败,事物回滚");
        }else{
            logger.info("导入物流数据,活动代码{}={}==========================================={}" ,oActivity.getActCode(),oLogistics.getId(), JSONObject.toJSON(oLogistics));
        }
        //调用明细接口之前需要先去数据库进行查询是否已有数据
        if (null != stringList && stringList.size() > 0) {
            for (String snNum : stringList) {
                //历史sn不能存在于系统中
                OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                OLogisticsDetailExample.Criteria oLogisticsDetailExample_criteria = oLogisticsDetailExample.createCriteria();
                //oLogisticsDetailExample_criteria.andStatusEqualTo(OLogisticsDetailStatus.STATUS_FH.code);
                //oLogisticsDetailExample_criteria.andRecordStatusEqualTo(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                oLogisticsDetailExample_criteria.andSnNumEqualTo(snNum);
                List<OLogisticsDetail> oLogisticsDetails = logisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                if (null != oLogisticsDetails && oLogisticsDetails.size() > 0) {
                    //说明已经存在数据
                    logger.info(snNum+"此物流已经存在,正在发货中!!!");
                    throw new MessageException(snNum+"此物流已经存在,正在发货中!!!");
                }
            }
        }
        String id =  oLogistics.getReceiptPlanId();   // 排单编号
        if (null == id) {
            throw new MessageException("排单ID查询失败！");
        } else {
            ReceiptPlan receiptPlan = receiptPlanMapper.selectByPrimaryKey(id);
            if (receiptPlan != null) {
                if(receiptPlan.getSendProNum()==null || receiptPlan.getSendProNum().compareTo(BigDecimal.ZERO)==0) {// 发货数量
                    receiptPlan.setSendProNum(new BigDecimal(sendProNum));
                }else{
                    receiptPlan.setSendProNum(receiptPlan.getSendProNum().add(new BigDecimal(sendProNum)));
                }
                receiptPlan.setRealSendDate(Calendar.getInstance().getTime());                          // 实际发货时间
                receiptPlan.setPlanOrderStatus(new BigDecimal(PlannerStatus.YesDeliver.getValue()));    // 排单状态为已发货
                if (receiptPlanMapper.updateByPrimaryKeySelective(receiptPlan)!= 1) {
                    throw new MessageException("更新排单数据失败！");
                }
                logger.info("更新排单数据============================================" + JSONObject.toJSON(receiptPlan));
            }
            //流量卡不进行下发操作
            if(oActivity!=null && StringUtils.isNotBlank(oActivity.getActCode()) && "2204".equals(oActivity.getActCode())){
                logger.info("导入物流数据,流量卡不进行下发操作，活动代码{}={}==========================================={}" ,oActivity.getActCode(),oLogistics.getId(), JSONObject.toJSON(oLogistics));
                return AgentResult.ok("流量卡不进行下发操作");
            }

            //进行机具调整操作
            if (!proType.equals(PlatformType.MPOS.msg) && !proType.equals(PlatformType.MPOS.code)){
                logger.info("======pos发货 更新库存记录:{}:{}",proType,stringList);
                List<OLogisticsDetail> snList = null; //fixme 使用缓存中的数据进行添加
                OOrder oOrder = oOrderMapper.selectByPrimaryKey(orderId);
                if(null==oOrder){
                    throw new MessageException("查询订单数据失败！");
                }
                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oOrder.getBusId());
                if(null==agentBusInfo){
                    throw new MessageException("查询订单业务数据失败！");
                }
                ImsTermAdjustDetail imsTermAdjustDetail = new ImsTermAdjustDetail();
                imsTermAdjustDetail.setnOrgId(agentBusInfo.getBusNum());
                imsTermAdjustDetail.setMachineId(oSubOrderActivity.getBusProCode());
                OLogistics logistics =  oLogisticsMapper.selectByPrimaryKey(oLogistics.getId());
                logger.info("退货上传物流下发到POS系统:{}:{}:{}",user,logistics.getId(),snList.toString());
                try {
                    AgentResult ar =  imsTermAdjustDetailService.insertImsTermAdjustDetail(snList,imsTermAdjustDetail);
                    if(ar.isOK()){
                        logistics.setSendStatus(Status.STATUS_1.status);
                        logistics.setSendMsg(ar.getMsg());
                        oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                    }else{
                        logistics.setSendStatus(Status.STATUS_2.status);
                        logistics.setSendMsg(ar.getMsg());
                        oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                    }
                } catch (MessageException e) {
                    e.printStackTrace();
                    logger.error("机具退货调整POS接口调用异常"+logistics.getId(),e);
                    logistics.setSendStatus(Status.STATUS_2.status);
                    logistics.setSendMsg(e.getMsg());
                    oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                }catch (Exception e) {
                    e.printStackTrace();
                    logger.error("机具退货调整POS接口调用异常"+logistics.getId(),e);
                    logistics.setSendStatus(Status.STATUS_2.status);
                    logistics.setSendMsg(e.getLocalizedMessage());
                    oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                }
            //cxinfo 机具退货调整首刷接口调用
            }else{

                logger.info("======首刷发货 更新库存记录:{}:{}",proType,stringList);
                //起始sn fixme 首刷历史订单退货，无sn需要从缓存中拿明细数据
                OLogisticsDetailExample exampleOLogisticsDetailExamplestart = new OLogisticsDetailExample();
                exampleOLogisticsDetailExamplestart.or()
                        .andSnNumEqualTo(oLogistics.getSnBeginNum())
                        .andTerminalidTypeEqualTo(PlatformType.MPOS.code);
                List<OLogisticsDetail> logisticsDetailsstart = logisticsDetailMapper.selectByExample(exampleOLogisticsDetailExamplestart);
                OLogisticsDetail detailstart = logisticsDetailsstart.get(0);

                //结束sn
                OLogisticsDetailExample exampleOLogisticsDetailExampleend = new OLogisticsDetailExample();
                exampleOLogisticsDetailExampleend.or().andSnNumEqualTo(oLogistics.getSnEndNum()).andTerminalidTypeEqualTo(PlatformType.MPOS.code);
                List<OLogisticsDetail> logisticsDetailsend = logisticsDetailMapper.selectByExample(exampleOLogisticsDetailExampleend);
                OLogisticsDetail detailend = logisticsDetailsend.get(0);

                AdjustmentMachineVo vo = new AdjustmentMachineVo();
                vo.setOptUser(user);
                vo.setSnStart(detailstart.getSnNum()+detailstart.getTerminalidCheck());
                vo.setSnEnd(detailend.getSnNum()+detailend.getTerminalidCheck());
                vo.setSnNum(oLogistics.getSendNum().toString());

                //发货订单的业务编号
                OOrder order =  oOrderMapper.selectByPrimaryKey(oLogistics.getOrderId());
                AgentBusInfo busInfo = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
                vo.setNewBusNum(busInfo.getBusNum());


                //退货订单的业务编号
                OReturnOrderDetail oReturnOrderDetail = returnOrderDetailMapper.selectByPrimaryKey(receiptPlan.getReturnOrderDetailId());
                OOrder orderreturn =  oOrderMapper.selectByPrimaryKey(oReturnOrderDetail.getOrderId());
                AgentBusInfo returnbusInfo = agentBusInfoMapper.selectByPrimaryKey(orderreturn.getBusId());
                vo.setOldBusNum(returnbusInfo.getBusNum());
                vo.setPlatformNum(returnbusInfo.getBusPlatform());

                //新活动
                vo.setNewAct(oSubOrderActivity.getBusProCode());

                //老活动查询
                OSubOrderExample old_OSubOrder = new OSubOrderExample();
                old_OSubOrder.or()
                        .andOrderIdEqualTo(oReturnOrderDetail.getOrderId())
                        .andProIdEqualTo(oReturnOrderDetail.getProId())
                        .andStatusEqualTo(Status.STATUS_1.status);
                List<OSubOrder> list_osub_old = oSubOrderMapper.selectByExample(old_OSubOrder);

                if(list_osub_old.size()==0){
                    throw new MessageException("退货机具活动信息未找到");
                }
                OSubOrder old_suborder  = list_osub_old.get(0);
                OSubOrderActivityExample example_old_activity = new OSubOrderActivityExample();
                example_old_activity.or().andSubOrderIdEqualTo(old_suborder.getId()).andStatusEqualTo(Status.STATUS_1.status);
                List<OSubOrderActivity>  list_old_act = subOrderActivityMapper.selectByExample(example_old_activity);
                if(list_old_act.size()==0){
                    throw new MessageException("退货机具活动信息未找到");
                }
                OSubOrderActivity old_act = list_old_act.get(0);
                vo.setOldAct(old_act.getBusProCode());

                //cxinfo 机具退货调整首刷接口调用
                OLogistics logistics =  oLogisticsMapper.selectByPrimaryKey(oLogistics.getId());
                //同平台下发，不同平台不下发
                if(busInfo.getBusPlatform().equals(returnbusInfo.getBusPlatform())) {
                    try {
                        logger.info("退货上传物流下发到首刷系统:{}:{}:{}:{}",user,logistics.getId(),vo.getSnStart(),vo.getSnEnd());
                        AgentResult mposXF = termMachineService.adjustmentMachine(vo);
                        logger.info("机具退货调整首刷接口调用:{}:{}:{}:{}:{}",user,logistics.getId(),vo.getSnStart(),vo.getSnEnd(),mposXF.getMsg());
                        if (!mposXF.isOK()) {
                            logistics.setSendStatus(Status.STATUS_2.status);
                            logistics.setSendMsg(mposXF.getMsg());
                            if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                                logger.info("机具退货调整首刷接口调用STATUS_2更新数据库失败:{}:{}:{}:{}:{}",user,logistics.getId(),vo.getSnStart(),vo.getSnEnd(),mposXF.getMsg());
                            }
                        }else{
                            logistics.setSendStatus(Status.STATUS_1.status);
                            logistics.setSendMsg(mposXF.getMsg());
                            if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                                logger.info("机具退货调整首刷接口调用STATUS_1更新数据库失败:{}:{}:{}:{}:{}",user,logistics.getId(),vo.getSnStart(),vo.getSnEnd(),mposXF.getMsg());
                            }
                        }
                    }catch (MessageException e) {
                        e.printStackTrace();
                        logistics.setSendStatus(Status.STATUS_2.status);
                        logistics.setSendMsg(e.getMsg());
                        if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                            logger.info("机具退货调整首刷接口调用Exception更新数据库失败:{}",JSONObject.toJSONString(logistics));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logistics.setSendStatus(Status.STATUS_2.status);
                        logistics.setSendMsg(e.getLocalizedMessage());
                        if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                            logger.info("机具退货调整首刷接口调用Exception更新数据库失败:{}",JSONObject.toJSONString(logistics));
                        }
                    }
                }else{
                    logistics.setSendStatus(Status.STATUS_0.status);
                    logistics.setSendMsg("不同平台不下发，手动调整");
                    if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                        logger.info("机具退货调整首刷接口调用Exception更新数据库失败:{}",JSONObject.toJSONString(logistics));
                    }
                }
            }



        }

        return AgentResult.ok();
    }


    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Override
    public void approvalFinish(String processInstanceId, String activityName) throws Exception{
        try {
            logger.info("历史退货审批完成回调:{},{}", processInstanceId, activityName);
            //审批流关系
            BusActRel rel = busActRelService.findById(processInstanceId);
            //退货编号
            String returnId = rel.getBusId();
            //更新退货单
            OReturnOrder returnOrder = returnOrderMapper.selectByPrimaryKey(returnId);
            returnOrder.setRetSchedule(new BigDecimal(RetSchedule.WC.code));
            returnOrder.setuTime(new Date());
            if( returnOrderMapper.updateByPrimaryKeySelective(returnOrder)!=1){
                logger.info("退货审批完成回调:{},{},更新退货单失败", processInstanceId, activityName);
                throw new MessageException("更新退货单失败");
            }
        } catch (Exception e) {
            logger.error("退货审批完成回调异常", e);
            throw e;
        }
    }
}
