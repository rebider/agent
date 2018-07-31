package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.order.OPaymentDetailExample;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Zhang Lei
 * @Description: 代理商欠款
 * @Date: 16:31 2018/7/24
 */
@Service("paymentDetailService")
public class PaymentDetailServiceImpl implements IPaymentDetailService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(PaymentDetailServiceImpl.class);

    @Autowired
    OPaymentDetailMapper oPaymentDetailMapper;

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
            maps= oPaymentDetailMapper.selectShareMoney(map);
        }
        if (method.equals(GetMethod.AGENTDATE.code)) {
            //所有当月分期---只需要时间
            if (StringUtils.isBlank(time)) {
                logger.info("分润查询:{}", "时间为空");
                throw new ProcessException("时间为空");
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("time", time);
            maps= oPaymentDetailMapper.selectShareMoney(map);
        }
        return maps;
    }

    private List<OPaymentDetail> selectAll(String agentId, String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = simpleDateFormat.parse(time);

        OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
        OPaymentDetailExample.Criteria criteria = oPaymentDetailExample.createCriteria();
        if (StringUtils.isNotBlank(agentId))
            criteria.andAgentIdEqualTo(agentId);
        criteria.andCDateEqualTo(parse);//时间查询如何使用这个实例
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andPaymentStatusEqualTo(PaymentStatus.DF.code);
        criteria.andPayTypeEqualTo(PaymentType.FRFQ.code);//有个分润分期和打款分期的
        List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
        if (null == oPaymentDetails && oPaymentDetails.size() < 0) {
            return Arrays.asList();
        }
        return oPaymentDetails;
    }
}
