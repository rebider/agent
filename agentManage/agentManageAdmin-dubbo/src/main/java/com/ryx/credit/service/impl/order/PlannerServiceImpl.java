package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.OReceiptStatus;
import com.ryx.credit.common.enumc.PlannerStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.order.OReceiptOrderMapper;
import com.ryx.credit.dao.order.OReceiptProMapper;
import com.ryx.credit.dao.order.OSubOrderMapper;
import com.ryx.credit.dao.order.ReceiptPlanMapper;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.PlannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 排单
 * Created by RYX on 2018/7/20.
 */
@Service("plannerService")
public class PlannerServiceImpl implements PlannerService {

    private static Logger log = LoggerFactory.getLogger(PlannerServiceImpl.class);
    @Autowired
    private OReceiptOrderMapper receiptOrderMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;
    @Autowired
    private OReceiptProMapper receiptProMapper;
    @Autowired
    OSubOrderMapper oSubOrderMapper;


    @Override
    public PageInfo queryPlannerList(OReceiptOrder receiptOrder, OReceiptPro receiptPro, Page page) {

        Map<String, Object> reqMap = new HashMap<>();
//        reqMap.put("receiptStatus", OReceiptStatus.WAITING_LIST.code);
        reqMap.put("receiptProStatus", OReceiptStatus.WAITING_LIST.code);
        if (StringUtils.isNotBlank(receiptOrder.getOrderId())) {
            reqMap.put("orderId", receiptOrder.getOrderId());
        }
        if (StringUtils.isNotBlank(receiptOrder.getReceiptNum())) {
            reqMap.put("receiptNum", receiptOrder.getReceiptNum());
        }
        if (StringUtils.isNotBlank(receiptOrder.getAddrRealname())) {
            reqMap.put("addrRealname", receiptOrder.getAddrRealname());
        }
        List<Map<String, Object>> plannerList = receiptOrderMapper.queryPlannerList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(plannerList);
        pageInfo.setTotal(receiptOrderMapper.queryPlannerCount(reqMap));
        return pageInfo;
    }

    /**
     * 分配排单
     *
     * @param receiptPlan
     * @param receiptProId
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult savePlanner(ReceiptPlan receiptPlan, String receiptProId) throws Exception {
        AgentResult result = new AgentResult(500, "系统异常", "");
        try {

            OReceiptPro oReceiptPro = receiptProMapper.selectByPrimaryKey(receiptProId);
            if (oReceiptPro == null) {
                throw new ProcessException("收货单商品未找到");
            }

            String planId = idService.genId(TabId.o_receipt_plan);
            receiptPlan.setId(planId);
            receiptPlan.setPlanNum(planId);
            Date nowDate = new Date();
            receiptPlan.setcDate(nowDate);
            receiptPlan.setStatus(Status.STATUS_1.status);
            receiptPlan.setVersion(Status.STATUS_1.status);
            receiptPlan.setPlanOrderStatus(new BigDecimal(PlannerStatus.YesPlanner.getValue()));

            //采购单商品
            OSubOrderExample example = new OSubOrderExample();
            example.or().andOrderIdEqualTo(oReceiptPro.getOrderid()).andProIdEqualTo(oReceiptPro.getProId()).andStatusEqualTo(Status.STATUS_1.status);
            List<OSubOrder>  oSubOrders = oSubOrderMapper.selectByExample(example);
            if(oSubOrders.size()==0){
                throw new ProcessException("订购商品未找到");
            }
            OSubOrder  oSubOrderItem = oSubOrders.get(0);
            receiptPlan.setProType(oSubOrderItem.getProType());
            int receiptInsert = receiptPlanMapper.insert(receiptPlan);

            OReceiptPro receiptPro = new OReceiptPro();
            receiptPro.setId(receiptProId);
            receiptPro.setSendNum(oReceiptPro.getSendNum().add(receiptPlan.getPlanProNum()));
            if (receiptPro.getSendNum().equals(oReceiptPro.getProNum())) {
                receiptPro.setReceiptProStatus(OReceiptStatus.DISPATCHED_ORDER.code);
            }
            int receiptProUpdate = receiptProMapper.updateByPrimaryKeySelective(receiptPro);
            if (receiptInsert != 1 || receiptProUpdate != 1) {
                log.info("保存排单异常");
                throw new ProcessException("保存排单异常");
            }
            //没有待排单的商品更新收货单状态
            OReceiptProExample oReceiptProExample = new OReceiptProExample();
            OReceiptProExample.Criteria criteria = oReceiptProExample.createCriteria();
            criteria.andReceiptIdEqualTo(receiptPlan.getReceiptId());
            criteria.andReceiptProStatusEqualTo(OReceiptStatus.WAITING_LIST.code);
            List<OReceiptPro> oReceiptPros = receiptProMapper.selectByExample(oReceiptProExample);
            if (oReceiptPros.size() == 0) {
                OReceiptOrder receiptOrder = new OReceiptOrder();
                receiptOrder.setId(receiptPlan.getReceiptId());
                receiptOrder.setReceiptStatus(OReceiptStatus.DISPATCHED_ORDER.code);
                int receiptOrdUpdate = receiptOrderMapper.updateByPrimaryKeySelective(receiptOrder);
                if (receiptOrdUpdate != 1) {
                    throw new ProcessException("保存排单异常");
                }
            }
            result.setStatus(200);
            result.setMsg("处理成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProcessException("保存排单异常");
        }
        return result;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 查询订单/退货单的排单详细情况
     * @Date: 15:58 2018/7/27
     */
    public List<Map<String, Object>> queryOrderReceiptPlanInfo(Map<String, String> params) throws ProcessException {
        List<Map<String, Object>> list = null;
        list = receiptPlanMapper.queryOrderReceiptPlanInfo(params);
        return list;
    }

}
