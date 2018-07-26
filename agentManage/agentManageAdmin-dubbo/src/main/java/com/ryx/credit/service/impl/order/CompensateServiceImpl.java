package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.dao.order.OLogisticsMapper;
import com.ryx.credit.dao.order.OSubOrderActivityMapper;
import com.ryx.credit.dao.order.OSubOrderMapper;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.impl.agent.AccountPaidItemServiceImpl;
import com.ryx.credit.service.order.CompensateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 补差价处理
 * Created by liudh on 2018/7/24.
 */
@Service("compensateService")
public class CompensateServiceImpl implements CompensateService {

    private static Logger log = LoggerFactory.getLogger(AccountPaidItemServiceImpl.class);
    @Autowired
    private OLogisticsMapper logisticsMapper;
    @Autowired
    private OSubOrderMapper subOrderMapper;
    @Autowired
    private OSubOrderActivityMapper subOrderActivityMapper;


    @Override
    public OSubOrder getOrderMsgByExcel(List<Object> excelList){
        String agentName =  String.valueOf(excelList.get(0));
        String proCom =  String.valueOf(excelList.get(1));
        String model =  String.valueOf(excelList.get(2));
        String snBegin =  String.valueOf(excelList.get(3));
        String snEnd =  String.valueOf(excelList.get(4));
        String count =  String.valueOf(excelList.get(5));
        String subOrderNum =  String.valueOf(excelList.get(6));
        String orderNum =  String.valueOf(excelList.get(7));

        Map<String, Object> reqParam = new HashMap<>();
        reqParam.put("snBegin","888gg1000");
        reqParam.put("snEnd","888gg1005");
        reqParam.put("status",Status.STATUS_1.status);
        List<Map<String,Object>> oLogistics = logisticsMapper.queryLogisticsList(reqParam);
        if(oLogistics==null && oLogistics.size()==0 && oLogistics.size()!=1){
            log.info("数据有误异常返回01");
            return null;
        }
        Map<String, Object> logisticMap = oLogistics.get(0);
        String proId = String.valueOf(logisticMap.get("PRO_ID"));
        String orderId = String.valueOf(logisticMap.get("ORDER_ID"));

        OSubOrderExample oSubOrderExample = new OSubOrderExample();
        OSubOrderExample.Criteria criteria1 = oSubOrderExample.createCriteria();
        criteria1.andOrderIdEqualTo(orderId);
        criteria1.andProIdEqualTo(proId);
        List<OSubOrder> oSubOrders = subOrderMapper.selectByExample(oSubOrderExample);
        if(oSubOrders==null && oSubOrders.size()==0 && oLogistics.size()!=1){
            log.info("数据有误异常返回02");
            return null;
        }
        OSubOrder oSubOrder = oSubOrders.get(0);
        OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
        OSubOrderActivityExample.Criteria criteria2 = oSubOrderActivityExample.createCriteria();
        criteria2.andSubOrderIdEqualTo(oSubOrder.getId());
        List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
        if(null!=oSubOrderActivities && oSubOrderActivities.size()==1){
            oSubOrder.setSubOrderActivity(oSubOrderActivities.get(0));
        }
        return oSubOrder;
    }
}
