package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.order.OLogisticsDetailHMapper;
import com.ryx.credit.dao.order.OLogisticsDetailMapper;
import com.ryx.credit.dao.order.OLogisticsMapper;
import com.ryx.credit.pojo.admin.order.OLogistics;
import com.ryx.credit.pojo.admin.order.OLogisticsDetailExample;
import com.ryx.credit.pojo.admin.order.OLogisticsExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 作者：cx
 * 时间：2019/7/18
 * 描述：订单修复程序
 */
@Service("orderRepairService")
public class OrderRepairServiceImpl implements com.ryx.credit.service.order.OrderRepairService {


    private Logger logger = LoggerFactory.getLogger(OrderRepairServiceImpl.class);

    @Autowired
    private OLogisticsDetailHMapper oLogisticsDetailHMapper;
    @Autowired
    private OLogisticsMapper  oLogisticsMapper;
    @Autowired
    private OLogisticsDetailMapper oLogisticsDetailMapper;

    /**
     * 修复重复上传物流的信息的物流信息和物流明细信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public FastMap repairDumpOrderLogic(int flg,String orderIds)throws Exception{

        if(StringUtils.isBlank(orderIds))return FastMap.fastFailMap("订单号不能为空");
        List<String> orderIdArrays = Arrays.asList(orderIds.split(","));
        List<Map<String,Object>> multipleOrder = oLogisticsDetailHMapper.queryMultipleOrderLogicInfo();
        //查询重复的订单
        for (Map<String, Object> stringObjectMap : multipleOrder) {
            Object  ORDER_ID = stringObjectMap.get("ORDER_ID");
            logger.info("修复重复上传物流的信息的物流信息和物流明细信息:{},是否在要处理的订单中:{}",stringObjectMap,orderIdArrays.contains(ORDER_ID+""));
        }

        if(flg==1) {

            //查询重复的订单
            for (Map<String, Object> stringObjectMap : multipleOrder) {

                Object  ORDER_ID = stringObjectMap.get("ORDER_ID");
                Object  RECEIPT_PLAN_ID = stringObjectMap.get("RECEIPT_PLAN_ID");
                Object  W_NUMBER =  stringObjectMap.get("W_NUMBER");
                Object  SN_BEGIN_NUM =  stringObjectMap.get("SN_BEGIN_NUM");
                Object  SN_END_NUM =  stringObjectMap.get("SN_END_NUM");

                if(!orderIdArrays.contains(ORDER_ID+"")){
                   continue;
                }
                logger.info("处理重复物流："+ JSONObject.toJSONString(stringObjectMap));
                OLogisticsExample example = new OLogisticsExample();
                example.or().andOrderIdEqualTo(ORDER_ID+"")
                        .andReceiptPlanIdEqualTo(RECEIPT_PLAN_ID+"")
                        .andWNumberEqualTo(W_NUMBER+"")
                        .andSnBeginNumEqualTo(SN_BEGIN_NUM+"")
                        .andSnEndNumEqualTo(SN_END_NUM+"");

                List<OLogistics>  oLogisticsList = oLogisticsMapper.selectByExample(example);
                if(oLogisticsList.size()>1){
                    logger.info("物流个数大于1："+ JSONObject.toJSONString(stringObjectMap));
                    for (OLogistics logistics : oLogisticsList) {
                        OLogisticsExample delEx = new OLogisticsExample();
                        delEx.or().andIdEqualTo(logistics.getId());
                        oLogisticsMapper.deleteByExample(delEx);
                        logger.info("删除物流："+ JSONObject.toJSONString(logistics));

                        OLogisticsDetailExample oLogisticsDetailex = new OLogisticsDetailExample();
                        oLogisticsDetailex.or().andLogisticsIdEqualTo(logistics.getId());
                        int detailCount = oLogisticsDetailMapper.deleteByExample(oLogisticsDetailex);
                        logger.info("删除物流明细：{},count:{}", JSONObject.toJSONString(logistics),detailCount);
                        List<OLogistics>  shengyue = oLogisticsMapper.selectByExample(example);
                        if(shengyue.size()==1){
                            break;
                        }
                    }
                }else{
                    logger.info("物流个数小于等于1："+ JSONObject.toJSONString(stringObjectMap));
                }
            }
        }
        return FastMap.fastSuccessMap();
    }



}
