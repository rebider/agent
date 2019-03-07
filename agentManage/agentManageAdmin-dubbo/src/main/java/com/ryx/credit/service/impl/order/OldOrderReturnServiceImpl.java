package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.machine.service.ImsTermAdjustDetailService;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.order.OReturnOrder;
import com.ryx.credit.pojo.admin.order.OReturnOrderDetail;
import com.ryx.credit.pojo.admin.vo.OldOrderReturnSubmitProVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.BusActRelService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;


/**
 * 作者：cx
 * 时间：2019/3/6
 * 描述：历史订单退货服务service
 */
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
    private DictOptionsService dictOptionsService;
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


    /**
     * 提交历史订单退货申请
     * @param userId 操作用户
     * @param agentId 代理商编号
     * @param oldOrderReturnSubmitProVoList sn号段列表
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public AgentResult saveOldReturnOrder(String userId, String agentId, String remark, List<OldOrderReturnSubmitProVo> oldOrderReturnSubmitProVoList)throws Exception{

        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        //保存审批中的退货申请单
        OReturnOrder oReturnOrder = new OReturnOrder();
        oReturnOrder.setId(idService.genId(TabId.o_return_order));
        oReturnOrder.setRetSchedule(new BigDecimal(RetSchedule.SPZ.code));
        oReturnOrder.setAgentId(agentId);
        oReturnOrder.setApplyRemark(remark);
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
        oReturnOrder.setcUser(userId);
        oReturnOrder.setuUser(userId);
        oReturnOrder.setVersion(BigDecimal.ONE);
        oReturnOrder.setOreturntype(Oreturntype.OLD.code);
        logger.info("历史退货单申请 {} {} {} {}",userId,agentId,remark,oReturnOrder.getId());
        if(1!=returnOrderMapper.insertSelective(oReturnOrder)){
            logger.error("保存历史退货单明细失败 {} {}",userId,agentId);
            throw new MessageException("退货单申请失败");
        }
        //保存提货申请明细
        OReturnOrderDetail oReturnOrderDetail = new OReturnOrderDetail();
        for (OldOrderReturnSubmitProVo oldOrderReturnSubmitProVo : oldOrderReturnSubmitProVoList) {
            oReturnOrderDetail.setId(idService.genId(TabId.o_return_order_detail));
            oReturnOrderDetail.setReturnId(oReturnOrder.getId());
            oReturnOrderDetail.setAgentId(agentId);
            oReturnOrderDetail.setProId(oldOrderReturnSubmitProVo.getProId());
            oReturnOrderDetail.setProName(oldOrderReturnSubmitProVo.getProName());
            oReturnOrderDetail.setProType(oldOrderReturnSubmitProVo.getProType());
            oReturnOrderDetail.setReturnCount(oldOrderReturnSubmitProVo.getReturnCount());
            oReturnOrderDetail.setBeginSn(oldOrderReturnSubmitProVo.getSnStart());
            oReturnOrderDetail.setEndSn(oldOrderReturnSubmitProVo.getSnEnd());
            if(1!=returnOrderDetailMapper.insertSelective(oReturnOrderDetail)){
                logger.error("保存历史退货单明细失败 {} {}",userId,agentId);
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
        record.setcUser(userId);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.hisrefund.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(agent.getId());
        record.setAgentName(agent.getAgName());
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("历史订单退货流程审批，启动审批异常，添加审批关系失败{}:{}", oReturnOrder.getId(), processingId);
            throw new MessageException("审批流启动失败:添加审批关系失败");
        }
        return AgentResult.ok();
    }





}
