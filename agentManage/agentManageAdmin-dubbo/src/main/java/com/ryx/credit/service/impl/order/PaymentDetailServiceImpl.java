package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.PaymentStatus;
import com.ryx.credit.common.enumc.PaymentType;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.dao.order.OPaymentMapper;
import com.ryx.credit.pojo.admin.order.OPayment;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.order.OPaymentDetailExample;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zhang Lei
 * @Description: 代理商欠款(包含机具订单欠款 、 其他欠款)
 * @Date: 16:31 2018/7/24
 */
@Service("paymentDetailService")
public class PaymentDetailServiceImpl implements IPaymentDetailService {

    @Autowired
    OPaymentDetailMapper oPaymentDetailMapper;
    @Autowired
    OPaymentMapper paymentMapper;

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
}
