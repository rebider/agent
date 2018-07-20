package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.order.OReceiptOrderMapper;
import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.OOrderExample;
import com.ryx.credit.pojo.admin.order.OReceiptOrder;
import com.ryx.credit.pojo.admin.order.OReceiptPro;
import com.ryx.credit.service.order.PlannerService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 排单
 * Created by RYX on 2018/7/20.
 */
@Service("plannerService")
public class PlannerServiceImpl implements PlannerService {

    @Autowired
    private OReceiptOrderMapper receiptOrderMapper;

    @Override
    public PageInfo queryPlannerList(OReceiptOrder receiptOrder, OReceiptPro receiptPro, Page page){

        Map<String,Object> reqMap = new HashMap<>();
        List<Map<String,Object>> plannerList = receiptOrderMapper.queryPlannerList(reqMap,page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(plannerList);
        pageInfo.setTotal(receiptOrderMapper.queryPlannerCount(reqMap));
        return pageInfo;
    }
}
