package com.ryx.credit.service.impl.order;

import com.ryx.credit.dao.order.OLogisticsMapper;
import com.ryx.credit.dao.order.OSubOrderActivityMapper;
import com.ryx.credit.dao.order.OSubOrderMapper;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.order.CompensateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 补差价处理
 * Created by RYX on 2018/7/24.
 */
@Service("compensateService")
public class CompensateServiceImpl implements CompensateService {

    @Autowired
    private OLogisticsMapper logisticsMapper;
    @Autowired
    private OSubOrderMapper subOrderMapper;
    @Autowired
    private OSubOrderActivityMapper subOrderActivityMapper;


    @Override
    public List<OSubOrder> getOrderMsgByExcel(List<Object> excelList){
        String agentName =  String.valueOf(excelList.get(0));
        String proCom =  String.valueOf(excelList.get(1));
        String model =  String.valueOf(excelList.get(2));
        String snBegin =  String.valueOf(excelList.get(3));
        String snEnd =  String.valueOf(excelList.get(4));
        String count =  String.valueOf(excelList.get(5));
        String subOrderNum =  String.valueOf(excelList.get(6));
        String orderNum =  String.valueOf(excelList.get(7));

        OLogisticsExample oLogisticsExample = new OLogisticsExample();
        OLogisticsExample.Criteria criteria = oLogisticsExample.createCriteria();
        criteria.andSnBeginNumGreaterThanOrEqualTo(snBegin);
        criteria.andSnEndNumLessThanOrEqualTo(snEnd);
        criteria.andOrderIdEqualTo(orderNum);
        List<OLogistics> oLogistics = logisticsMapper.selectByExample(oLogisticsExample);
        List<OSubOrder> oSubOrders = new ArrayList<>();
        for (OLogistics oLogistic : oLogistics) {
            OSubOrderExample oSubOrderExample = new OSubOrderExample();
            OSubOrderExample.Criteria criteria1 = oSubOrderExample.createCriteria();
            criteria1.andOrderIdEqualTo(orderNum);
            criteria1.andProIdEqualTo(oLogistic.getProId());
            oSubOrders = subOrderMapper.selectByExample(oSubOrderExample);
            for (OSubOrder oSubOrder : oSubOrders) {
                OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
                OSubOrderActivityExample.Criteria criteria2 = oSubOrderActivityExample.createCriteria();
                criteria2.andSubOrderIdEqualTo(oSubOrder.getId());
                List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
                if(null!=oSubOrderActivities && oSubOrderActivities.size()==1){
                    oSubOrder.setSubOrderActivity(oSubOrderActivities.get(0));
                }
            }
        }
        return oSubOrders;
    }
}
