package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.order.OOrderMapper;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.dao.order.OPaymentMapper;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Zhang Lei
 * @Description: 代理商欠款(包含机具订单欠款 、 其他欠款)
 * @Date: 16:31 2018/7/24
 */
@Service("paymentDetailService")
public class PaymentDetailServiceImpl implements IPaymentDetailService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(PaymentDetailServiceImpl.class);

    @Autowired
    OPaymentDetailMapper oPaymentDetailMapper;
    @Autowired
    OPaymentMapper paymentMapper;
    @Autowired
    OPaymentMapper oPaymentMapper;
    @Autowired
    OOrderMapper oOrderMapper;

    /**
     * @Author: Zhang Lei
     * @Description: 查询代理商可抵扣欠款, 先根据欠款类型排序，欠款类型相同的根据订单号排序
     * @Date: 17:01 2018/7/24
     */
    @Override
    public List<OPaymentDetail> getCanTakeoutPaymentsByAgentId(String agentId) throws ProcessException {
        OPaymentDetailExample example = new OPaymentDetailExample();

        //付款类型过滤条件
        List<String> payTypeList = new ArrayList<>();
        payTypeList.add(PaymentType.DKFQ.code);
        payTypeList.add(PaymentType.FRFQ.code);

        //订单状态过滤条件
        List<BigDecimal> paymentStatusList = new ArrayList<>();
        paymentStatusList.add(PaymentStatus.DF.code);
        paymentStatusList.add(PaymentStatus.BF.code);
        paymentStatusList.add(PaymentStatus.YQ.code);

        example.or().andAgentIdEqualTo(agentId).andPayTypeIn(payTypeList).andPaymentStatusIn(paymentStatusList);
        example.setOrderByClause("Payment_type asc");
        example.setOrderByClause("order_id asc");
        example.setOrderByClause("plan_pay_time asc");
        return oPaymentDetailMapper.selectByExample(example);
    }

    /**
     * @Author: Zhang Lei
     * @Description: 查询付款单
     * @Date: 9:32 2018/7/28
     */
    @Override
    public OPayment getPaymentById(String paymentId) throws ProcessException {
        return paymentMapper.selectByPrimaryKey(paymentId);
    }

    /**
     * @Author: Zhang Lei
     * @Description: 查询一个付款单下付款明细，可根据付款状态筛选
     * @Date: 9:32 2018/7/28
     */
    @Override
    public List<OPaymentDetail> getPaymentDetails(String paymentId, String... paymentStatus) throws ProcessException {
        OPaymentDetailExample example = new OPaymentDetailExample();
        OPaymentDetailExample.Criteria c = example.or();
        c.andPaymentIdEqualTo(paymentId);

        if (paymentStatus != null && paymentStatus.length > 0) {
            List<BigDecimal> paymentStatusList = new ArrayList<>();
            for (String status : paymentStatus) {
                paymentStatusList.add(PaymentStatus.valueOf(status).code);
                c.andPaymentStatusIn(paymentStatusList);
            }
        }
        example.setOrderByClause("plan_num asc");
        return oPaymentDetailMapper.selectByExample(example);
    }


    @Override
    public List<Map<String, Object>> getShareMoney(String method, String agentId, String time) throws ParseException {
        List<Map<String, Object>> maps = null;
        if (StringUtils.isBlank(method)) {
            logger.info("分润查询:{}", "获取方式为空");
            throw new ProcessException("获取方式为空");
        }
        if (method.equals(GetMethod.AGENTORDER.code)) {
            //代理商订单分期--需要agentId 和 time
            if (StringUtils.isBlank(agentId)) {
                logger.info("分润查询:{}", "代理商id为空");
                throw new ProcessException("代理商id为空");
            }
            if (StringUtils.isBlank(time)) {
                logger.info("分润查询:{}", "时间为空");
                throw new ProcessException("时间为空");
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("agentId", agentId);
            map.put("time", time);
            maps = oPaymentDetailMapper.selectShareMoney(map);
        } else if (method.equals(GetMethod.AGENTDATE.code)) {
            //所有当月分期---只需要时间
            if (StringUtils.isBlank(time)) {
                logger.info("分润查询:{}", "时间为空");
                throw new ProcessException("时间为空");
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("time", time);
            maps = oPaymentDetailMapper.selectShareMoney(map);
        }
        return maps;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO uploadStatus(List<Map<String, Object>> maps) {
        if (null == maps && maps.size() < 0) {
            logger.info("更新数据为空:{}", maps);
            return ResultVO.fail("更新数据为空");
        }
        for (Map<String, Object> map : maps) {
            String detailId = (String) map.get("detailId");//付款明细id
            String srcId = (String) map.get("srcId");//付款源id
            if (StringUtils.isBlank(detailId)) {
                logger.info("付款明细ID为空:{}", detailId);
                throw new ProcessException("付款明细ID为空");
            }
            if (StringUtils.isBlank(srcId)) {
                logger.info("付款源ID为空:{}", srcId);
                throw new ProcessException("付款源ID为空");
            }
            //分别去查询付款单数据  和    付款明细的数据
            OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
            OPaymentDetailExample.Criteria criteria = oPaymentDetailExample.createCriteria();
            criteria.andIdEqualTo(detailId);
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
            if (1 != oPaymentDetails.size())
                throw new ProcessException("没有查找到相关数据");
            OPaymentDetail oPaymentDetail = oPaymentDetails.get(0);
            OPaymentDetail detail = new OPaymentDetail();
            detail.setRealPayAmount(oPaymentDetail.getPayAmount());
            detail.setPayTime(Calendar.getInstance().getTime());
            detail.setPaymentStatus(PaymentStatus.JQ.code);
            detail.setSrcId(srcId);
            detail.setSrcType(PamentSrcType.FENRUN_DIKOU.code);
            detail.setId(oPaymentDetail.getId());
            if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(detail)) {
                logger.info("付款明细更新数据失败");
                throw new ProcessException("付款明细更新数据失败");
            }
            OPaymentExample oPaymentExample = new OPaymentExample();
            OPaymentExample.Criteria criteri = oPaymentExample.createCriteria();
            criteri.andStatusEqualTo(Status.STATUS_1.status);
            criteri.andIdEqualTo(oPaymentDetail.getPaymentId());
            List<OPayment> oPayments = oPaymentMapper.selectByExample(oPaymentExample);
            if (1 != oPayments.size())
                throw new ProcessException("没有查找到相关数据");
            OPayment oPaymentss = oPayments.get(0);
            OPayment oPayment = new OPayment();
            //已付款金额
            oPayment.setRealAmount(oPaymentss.getRealAmount().add(oPaymentDetail.getRealPayAmount()));
            //待付款金额
                oPayment.setOutstandingAmount(oPaymentss.getOutstandingAmount().subtract(oPaymentDetail.getRealPayAmount()));
            oPayment.setId(oPaymentDetail.getPaymentId());
            if (1 != oPaymentMapper.updateByPrimaryKeySelective(oPayment)) {
                logger.info("付款单更新数据失败");
                throw new ProcessException("付款单更新数据失败");
            }
            //更新订单的状态
            OOrder oOrder = new OOrder();
            oOrder.setId(oPaymentss.getOrderId());
            oOrder.setClearStatus(ClearStatus.CLEARED.status);
            if (1 != oOrderMapper.updateByPrimaryKeySelective(oOrder)) {
                logger.info("订单更新数据失败");
                throw new ProcessException("订单更新数据失败");
            }
            //查询当前订单是否还有未结清的订单
            OPaymentDetailExample detailExample = new OPaymentDetailExample();
            OPaymentDetailExample.Criteria riteria = detailExample.createCriteria();
            riteria.andPaymentIdEqualTo(oPaymentDetail.getPaymentId());
            riteria.andStatusEqualTo(Status.STATUS_1.status);
            riteria.andPaymentStatusIn(Arrays.asList(PaymentStatus.DF.code, PaymentStatus.YQ.code, PaymentStatus.FKING.code));
            List<OPaymentDetail> oPaymentDetai = oPaymentDetailMapper.selectByExample(detailExample);

            if (null == oPaymentDetai && oPaymentDetai.size() == 0 && oPayment.getOutstandingAmount().compareTo(BigDecimal.ZERO) == 0) {
                //说明没有未结清的订单
                OPayment payment = new OPayment();
                payment.setId(oPaymentDetail.getPaymentId());
                payment.setPayStatus(PayStatus.CLOSED.code);
                payment.setPayCompletTime(Calendar.getInstance().getTime());
                if (1 != paymentMapper.updateByPrimaryKeySelective(payment)) {
                    logger.info("付款明细更新数据失败");
                    throw new ProcessException("付款明细更新数据失败");
                }
            }
        }
        return ResultVO.success("");
    }
}
