package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AColinfoPayment;

/**
 * Created by RYX on 2018/9/17.
 */
public interface AColinfoPaymentService {

    PageInfo olinfoPaymentList(AColinfoPayment colinfoPayment, Page page);
    
}
