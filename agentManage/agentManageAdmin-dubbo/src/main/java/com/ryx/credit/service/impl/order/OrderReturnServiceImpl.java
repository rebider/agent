package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AttachmentRelType;
import com.ryx.credit.common.enumc.RetSchedule;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.MapUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.order.ODeductCapitalMapper;
import com.ryx.credit.dao.order.OReturnOrderDetailMapper;
import com.ryx.credit.dao.order.OReturnOrderMapper;
import com.ryx.credit.dao.order.OReturnOrderRelMapper;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.IOrderReturnService;
import com.ryx.credit.service.order.PlannerService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: Zhang Lei
 * @Description: 退货
 * @Date: 14:23 2018/7/25
 */
@Service("orderReturnService")
public class OrderReturnServiceImpl implements IOrderReturnService {

    private static Logger log = LoggerFactory.getLogger(OrderReturnServiceImpl.class);

    @Autowired
    OReturnOrderMapper returnOrderMapper;
    @Resource
    IdService idService;
    @Autowired
    OReturnOrderDetailMapper returnOrderDetailMapper;
    @Autowired
    OReturnOrderRelMapper returnOrderRelMapper;
    @Autowired
    ODeductCapitalMapper deductCapitalMapper;
    @Resource
    PlannerService plannerService;

    /**
     * @Author: Zhang Lei
     * @Description: 退货列表查询
     * @Date: 14:24 2018/7/25
     */
    @Override
    public PageInfo orderList(OReturnOrder returnOrder, PageInfo page) {
        OReturnOrderExample example = new OReturnOrderExample();
        OReturnOrderExample.Criteria c = example.or();
        if (returnOrder != null) {
            if (StringUtils.isNotEmpty(returnOrder.getId())) {
                c.andIdEqualTo(returnOrder.getId());
            }
            if (StringUtils.isNotEmpty(returnOrder.getAgentId())) {
                c.andAgentIdEqualTo(returnOrder.getAgentId());
            }
        }
        example.setPage(new Page(page.getFrom(), page.getPagesize()));
        long count = returnOrderMapper.countByExample(example);
        example.setOrderByClause(" u_time desc ");
        List<OReturnOrder> list = returnOrderMapper.selectByExample(example);
        page.setRows(list);
        page.setTotal(Integer.parseInt(count + ""));
        return page;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 查询退货详情
     * @Date: 15:27 2018/7/27
     */
    public Map<String, Object> view(String returnId) throws ProcessException {
        Map<String, Object> map = new HashMap<>();

        //查询退货单
        OReturnOrder returnOrder = returnOrderMapper.selectByPrimaryKey(returnId);
        if (returnOrder == null) {
            throw new ProcessException("退货单不存在");
        }

        //查询退货明细
        OReturnOrderDetailExample example = new OReturnOrderDetailExample();
        example.or().andReturnIdEqualTo(returnId);
        List<OReturnOrderDetail> returnDetails = returnOrderDetailMapper.selectByExample(example);

        //查询扣款款项
        ODeductCapitalExample deductCapitalExample = new ODeductCapitalExample();
        deductCapitalExample.or().andSourceIdEqualTo(returnId);
        List<ODeductCapital> deductCapitals = deductCapitalMapper.selectByExample(deductCapitalExample);

        //查询已排单列表
        Map<String, String> params = new HashMap<String, String>();
        params.put("returnId", returnId);
        List<Map<String, Object>> receiptPlans = plannerService.queryOrderReceiptPlanInfo(params);

        map.put("returnOrder", returnOrder);
        map.put("returnDetails", returnDetails);
        map.put("deductCapitals", deductCapitals);
        map.put("receiptPlans", receiptPlans);

        return map;
    }

    /**
     * @Author: Zhang Lei
     * @Description: 保存扣款款项
     * @Date: 20:25 2018/7/27
     */
    @Override
    @Transactional
    public Map<String, Object> saveCut(String returnId, String amt, String ctype) {

        Map<String, Object> map = new HashMap<>();

        OReturnOrder returnOrder = returnOrderMapper.selectByPrimaryKey(returnId);

        ODeductCapital deductCapital = new ODeductCapital();
        deductCapital.setId(idService.genId(TabId.o_deduct_capital));
        deductCapital.setcAmount(new BigDecimal(amt));
        deductCapital.setcType(ctype);
        deductCapital.setcAgentId(returnOrder.getAgentId());
        deductCapital.setSourceId(returnId);
        deductCapital.setcTime(new Date());
        deductCapitalMapper.insertSelective(deductCapital);

        returnOrder.setCutAmo(returnOrder.getCutAmo().subtract(new BigDecimal(amt)));
        returnOrder.setReturnAmo(returnOrder.getReturnAmo().add(new BigDecimal(amt)));
        returnOrder.setuTime(new Date());
        returnOrderMapper.updateByPrimaryKeySelective(returnOrder);

        map.put("goodsReturnAmo", returnOrder.getGoodsReturnAmo());
        map.put("returnAmo", returnOrder.getReturnAmo());
        map.put("cutAmo", returnOrder.getCutAmo());

        return map;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 业务审批
     * @Date: 10:20 2018/7/30
     */
    @Override
    @Transactional
    public Map<String, Object> bizAudit(String returnId, String plans, String remark, String userid, String auditResult) {

        if (auditResult.equals("no")) {

            return null;
        }

        JSONArray jsonArray = JSONObject.parseArray(plans);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            ReceiptPlan receiptPlan = new ReceiptPlan();
            receiptPlan.setProId(jsonObject.getString("receiptProId"));
            receiptPlan.setcUser(userid);
            receiptPlan.setUserId(userid);
            receiptPlan.setOrderId(jsonObject.getString("orderId"));
            receiptPlan.setReceiptId(jsonObject.getString("receiptId"));
            receiptPlan.setProCom(jsonObject.getString("proCom"));
            receiptPlan.setModel(jsonObject.getString("model"));
            receiptPlan.setPlanProNum(jsonObject.getBigDecimal("planProNum"));
            String receiptProId = jsonObject.getString("receiptProId");
            try {
                plannerService.savePlanner(receiptPlan, receiptProId);
            } catch (Exception e) {
                throw new ProcessException("保存排单信息失败");
            }
        }

        return null;
    }

    /**
     * @Author: Zhang Lei
     * @Description: 财务审核
     * @Date: 9:08 2018/7/31
     */
    @Override
    public Map<String, Object> cwAudit(String returnId, String remark, String userid, String auditResult, String[] attachments) throws ProcessException {

        //审核拒绝
        if (auditResult.equals("no")) {
            return null;
        }

        //审核通过
        //保存附件
        for (String attach : attachments) {
            AttachmentRel attachmentRel = new AttachmentRel();
            attachmentRel.setId(idService.genId(TabId.a_attachment_rel));
            attachmentRel.setSrcId(returnId);
            attachmentRel.setAttId(attach);
            attachmentRel.setBusType(AttachmentRelType.Return.name());
            attachmentRel.setcTime(new Date());
            attachmentRel.setcUser(userid);
            attachmentRel.setStatus(Status.STATUS_1.status);
        }

        return null;
    }

    /**
     * @Author: Zhang Lei
     * @Description: 退貨申請
     * @Date: 20:43 2018/7/26
     */
    @Override
    @Transactional
    public Map<String, Object> apply(String agentId, OReturnOrder returnOrder, String productsJson) throws ProcessException {

        List<Map> list = null;
        try {
            list = JSONObject.parseArray(productsJson, Map.class);
        } catch (Exception e) {
            log.error("解析退货商品失败", e);
            throw new ProcessException("解析退货商品失败");
        }

        String returnId = null;

        try {
            //生成退货单
            returnId = idService.genId(TabId.o_return_order);
            returnOrder.setId(returnId);
            returnOrder.setRetSchedule(new BigDecimal(RetSchedule.SPZ.code));
            returnOrder.setcTime(new Date());
            returnOrder.setcUser(agentId);
            returnOrder.setCutAmo(BigDecimal.ZERO);
            returnOrder.setRelReturnAmo(returnOrder.getGoodsReturnAmo());
            returnOrderMapper.insertSelective(returnOrder);
        } catch (Exception e) {
            log.error("生成退货单失败", e);
            throw new ProcessException("生成退货单失败");
        }

        //退货单和订单关系
        Set<String> relSet = new HashSet<>();

        for (Map<String, Object> map : list) {

            //生成退货明细
            OReturnOrderDetail returnOrderDetail = null;
            try {
                returnOrderDetail = new OReturnOrderDetail();
                returnOrderDetail.setId(idService.genId(TabId.o_return_order_detail));
                returnOrderDetail.setReturnId(returnId);
                returnOrderDetail.setAgentId(agentId);
                returnOrderDetail.setOrderId((String) map.get("orderId"));
                returnOrderDetail.setSubOrderId((String) map.get("receiptId"));
                returnOrderDetail.setProId((String) map.get("proId"));
                returnOrderDetail.setProName((String) map.get("proName"));
                returnOrderDetail.setProType((String) map.get("proType"));
                returnOrderDetail.setProCom((String) map.get("proCom"));
                returnOrderDetail.setProPrice(MapUtil.getBigDecimal(map, "proPrice"));
                returnOrderDetail.setModel((String) map.get("proModel"));
                returnOrderDetail.setBeginSn((String) map.get("startSn"));
                returnOrderDetail.setEndSn((String) map.get("endSn"));
                returnOrderDetail.setOrderPrice(MapUtil.getBigDecimal(map, "proPrice"));
                returnOrderDetail.setReturnCount(MapUtil.getBigDecimal(map, "count"));
                returnOrderDetail.setReturnAmt(MapUtil.getBigDecimal(map, "totalPrice"));
                returnOrderDetail.setReturnTime(new Date());
                returnOrderDetail.setcTime(new Date());
                returnOrderDetail.setcUser(agentId);
                returnOrderDetailMapper.insertSelective(returnOrderDetail);
            } catch (Exception e) {
                log.error("生成退货明细失败", e);
                throw new ProcessException("生成退货明细失败,SN[" + (String) map.get("startSn") + "--" + (String) map.get("endSn") + "]");
            }

            String setstr = returnOrderDetail.getOrderId() + "_" + returnOrderDetail.getSubOrderId() + "_" + returnOrderDetail.getReturnId();
            relSet.add(setstr);
        }

        //生成退货和订单关系
        for (String realId : relSet) {
            try {
                String[] ids = realId.split("_");
                OReturnOrderRel returnOrderRel = new OReturnOrderRel();
                returnOrderRel.setId(idService.genId(TabId.o_return_order_rel));
                returnOrderRel.setOrderId(ids[0]);
                returnOrderRel.setSubOrderId(ids[1]);
                returnOrderRel.setReturnOrderId(ids[2]);
                returnOrderRel.setcTime(new Date());
                returnOrderRel.setcUser(agentId);
                returnOrderRelMapper.insertSelective(returnOrderRel);
            } catch (Exception e) {
                log.error("生成退货关系失败", e);
                throw new ProcessException("生成退货关系失败");
            }
        }

        return null;
    }
}
