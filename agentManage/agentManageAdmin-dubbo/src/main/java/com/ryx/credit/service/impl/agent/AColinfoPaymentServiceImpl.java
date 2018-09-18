package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AColinfoPaymentMapper;
import com.ryx.credit.pojo.admin.agent.AColinfoPayment;
import com.ryx.credit.pojo.admin.agent.AColinfoPaymentExample;
import com.ryx.credit.pojo.admin.order.OProduct;
import com.ryx.credit.service.agent.AColinfoPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收款账户认证
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/9/17 16:20
 */
@Service("aColinfoPaymentService")
public class AColinfoPaymentServiceImpl implements AColinfoPaymentService {

    @Autowired
    private AColinfoPaymentMapper colinfoPaymentMapper;

    @Override
    public PageInfo olinfoPaymentList(AColinfoPayment colinfoPayment, Page page) {

        AColinfoPaymentExample aColinfoPaymentExample = new AColinfoPaymentExample();
        AColinfoPaymentExample.Criteria criteria = aColinfoPaymentExample.createCriteria();
        if(StringUtils.isNotBlank(colinfoPayment.getMerchId())){
            criteria.andMerchIdEqualTo(colinfoPayment.getMerchId());
        }
        if(StringUtils.isNotBlank(colinfoPayment.getMerchName())){
            criteria.andMerchNameEqualTo(colinfoPayment.getMerchName());
        }
        if(StringUtils.isNotBlank(colinfoPayment.getBalanceLs())){
            criteria.andBalanceLsEqualTo(colinfoPayment.getBalanceLs());
        }
        if(StringUtils.isNotBlank(colinfoPayment.getColinfoId())){
            criteria.andColinfoIdEqualTo(colinfoPayment.getColinfoId());
        }
        if(StringUtils.isNotBlank(colinfoPayment.getFlag())){
            criteria.andFlagEqualTo( colinfoPayment.getFlag());
        }
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        aColinfoPaymentExample.setPage(page);
        aColinfoPaymentExample.setOrderByClause(" create_time desc");
        List<AColinfoPayment> aColinfoPayments = colinfoPaymentMapper.selectByExample(aColinfoPaymentExample);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(aColinfoPayments);
        pageInfo.setTotal((int)colinfoPaymentMapper.countByExample(aColinfoPaymentExample));
        return pageInfo;
    }
}
