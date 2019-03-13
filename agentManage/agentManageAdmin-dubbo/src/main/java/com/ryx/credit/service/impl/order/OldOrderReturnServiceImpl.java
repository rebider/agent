package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.machine.entity.ImsTermAdjustDetail;
import com.ryx.credit.machine.service.ImsTermAdjustDetailService;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.AdjustmentMachineVo;
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


    /**
     * 提交历史订单退货申请
     * @param oldOrderReturnVo sn号段列表
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public AgentResult saveOldReturnOrder(OldOrderReturnVo oldOrderReturnVo)throws Exception{

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
        if(StringUtils.isNotBlank(agentVo.getPlans())){

            try {
                //保存排单信息
                AgentResult savePlans_agentResult =  iOrderReturnService.savePlans(agentVo,userId);
            } catch (MessageException e) {
                e.printStackTrace();
                throw new MessageException(e.getMsg());
            }catch (Exception e) {
                e.printStackTrace();
                throw new MessageException(e.getLocalizedMessage());
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


    @Override
    public List<String> uploadSnFileList(List<List<Object>> data, String user) throws Exception {
        List<String> list = new ArrayList<>();
        for (List<Object> objectList : data) {
            try {
                AgentResult result =   iOrderReturnService.addListItem(objectList,user);
                logger.info("导入物流{}成功",objectList.toString());
                list.add("物流["+objectList.toString()+"]"+result.getMsg());
            }catch (MessageException e) {
                e.printStackTrace();
                logger.info("导入物流{}抛出异常{}",objectList.toString(),e.getMsg());
                list.add("物流["+objectList.toString()+"]"+e.getMsg());
            }
            catch (Exception e) {
                e.printStackTrace();
                logger.info("导入物流{}抛出异常",objectList.toString());
                list.add("物流["+objectList.toString()+"]导入异常"+e.getMessage());
            }
        }
        return list;
    }

    @Override
    public AgentResult uploadSnFileListItem(List<Object> objectList, String user) throws Exception {
        String planNum = "";
        String orderId = "";
        String proCode = "";
        String proId = "";
        String proName = "";
        String sendDate = "";
        String sendProNum = "";
        String beginSn = "";
        String endSn = "";
        String beginSnCount = "";
        String endSnCount = "";
        String logCom = "";
        String wNumber = "";
        String proType="";

        List col = Arrays.asList(ReceiptPlanReturnExportColum.ReceiptPlanExportColum_column.col);
        planNum = String.valueOf(objectList.get(col.indexOf("PLAN_NUM")));
        orderId = String.valueOf(objectList.get(col.indexOf("ORDER_ID")));
        proCode = String.valueOf(objectList.get(col.indexOf("PRO_CODE")));
        proId = String.valueOf(objectList.get(col.indexOf("PRO_ID")));
        proName = String.valueOf(objectList.get(col.indexOf("PRO_NAME")));
        String proCom = String.valueOf(objectList.get(col.indexOf("PRO_COM_STRING")));

        sendDate = String.valueOf(objectList.get(col.indexOf("h")));
        sendProNum = String.valueOf(objectList.get(col.indexOf("g")));
        logCom = String.valueOf(objectList.get(col.indexOf("a")));
        wNumber = String.valueOf(objectList.get(col.indexOf("b")));
        beginSn = String.valueOf(objectList.get(col.indexOf("c")));
        endSn = String.valueOf(objectList.get(col.indexOf("d")));
        beginSnCount = String.valueOf(objectList.get(col.indexOf("e")));
        endSnCount = String.valueOf(objectList.get(col.indexOf("f")));
        proType = String.valueOf(objectList.get(col.indexOf("PRO_TYPE")));

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
            AgentResult agentResult = oLogisticsService.checkRecordPlan(objectList,listItem.get(0));
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
        List<String> stringList = new OLogisticServiceImpl().idList(beginSn, endSn,Integer.parseInt(beginSnCount),Integer.parseInt(endSnCount),proCom);
        if (Integer.valueOf(sendProNum) != stringList.size()) {
            logger.info("请仔细核对发货数量");
            throw new MessageException("请仔细核对发货数量");
        }


        return AgentResult.ok();
    }
}
