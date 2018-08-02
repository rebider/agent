package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.LogType;
import com.ryx.credit.common.enumc.PlannerStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.dao.order.OLogisticsDetailMapper;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.order.OLogisticsMapper;
import com.ryx.credit.dao.order.ReceiptPlanMapper;
import com.ryx.credit.pojo.admin.order.OLogistics;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.credit.pojo.admin.order.OReceiptPro;
import com.ryx.credit.pojo.admin.order.ReceiptPlan;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OLogisticsService;
import com.ryx.credit.service.order.ReceiptPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Lihl
 * @Date 2018/07/23
 * 排单管理：物流信息
 */
@Service("oLogisticService")
public class OLogisticServiceImpl implements OLogisticsService {
    private static Logger logger = LoggerFactory.getLogger(OLogisticServiceImpl.class);
    public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private OLogisticsMapper oLogisticsMapper;
    @Autowired
    private OLogisticsDetailMapper oLogisticsDetailMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;

    /**
     * 物流信息:
     * 1、列表查询
     * 2、导出物流信息
     */
    @Override
    public PageInfo getOLogisticsList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = oLogisticsMapper.getOLogisticsCount(param);
        List<Map<String, Object>> list = oLogisticsMapper.getOLogisticsList(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        System.out.println("查询/导出============================================" + JSONObject.toJSON(list));
        return pageInfo;
    }

    /**
     * 物流信息：
     * 1、导入物流信息
     * 2、调用明细接口并插入信息
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public List<String> addList(List<List<Object>> data, String user, Integer begins, Integer finish) throws Exception {
        List<String> list = new ArrayList<>();
        for (List<Object> objectList : data) {
            if(objectList == null || objectList.size() == 0 || StringUtils.isBlank(objectList.get(0) + ""))break;
            OLogistics oLogistics = new OLogistics();
            oLogistics.setcUser(user);                                      // 创建人
            oLogistics.setStatus(Status.STATUS_1.status);                   // 默认记录状态为1
//            oLogistics.setLogType(LogType.Deliver.getValue());              // 默认物流类型为1
            oLogistics.setId(idService.genId(TabId.o_logistics));           // 物流ID序列号
            oLogistics.setSendDate(Calendar.getInstance().getTime());       // 物流日期
            oLogistics.setcTime(Calendar.getInstance().getTime());          // 创建时间
            oLogistics.setReceiptPlanId(String.valueOf(objectList.get(0))); // 排单编号
            oLogistics.setOrderId(String.valueOf(objectList.get(1)));       // 订单编号
            oLogistics.setProId(String.valueOf(objectList.get(3)));         // 商品ID
            oLogistics.setProName(String.valueOf(objectList.get(4)));       // 商品名称
            oLogistics.setProType(String.valueOf(objectList.get(5)));       // 商品类型
            oLogistics.setProPrice(new BigDecimal(String.valueOf(objectList.get(6))));   // 商品单价
            oLogistics.setProCom(String.valueOf(objectList.get(7)));        // 厂家
            oLogistics.setProModel(String.valueOf(objectList.get(9)));      // 机型
            oLogistics.setSendNum(new BigDecimal(String.valueOf(objectList.get(23))));   // 发货数量

            String logCom = String.valueOf(objectList.get(24));
            if(null != logCom){
                oLogistics.setLogType(logCom);
            }
            String logType = LogType.Deliver.getValue();
            if(null != logType){
                oLogistics.setLogCom(logType);
            }
            String wNumber = String.valueOf(objectList.get(26));
            if(null != wNumber){
                oLogistics.setwNumber(wNumber);
            }
            String snBeginNum = String.valueOf(objectList.get(27));
            if(null != snBeginNum){
                oLogistics.setSnBeginNum(snBeginNum);
            }
            String snEndNum = String.valueOf(objectList.get(28));
            if(null != snEndNum){
                oLogistics.setSnEndNum(snEndNum);
            }

            System.out.println("导入物流数据============================================" + JSONObject.toJSON(oLogistics));
            if(1 != insertImportData(oLogistics)){
                throw new ProcessException("插入失败");
            }
            list.add(oLogistics.getId());

            begins = Integer.valueOf(String.valueOf(objectList.get(29)));   // 起始SN位数
            finish = Integer.valueOf(String.valueOf(objectList.get(30)));   // 结束SN位数
            ResultVO resultVO = insertLogisticsDetail(oLogistics.getSnBeginNum(), oLogistics.getSnEndNum(), begins, finish, oLogistics.getId(), user, user);
            if(resultVO.isSuccess()) {
                String id = "";
                id = oLogistics.getReceiptPlanId();   // 排单编号
                if (null == id) {
                    throw new ProcessException("排单ID查询失败");
                } else {
                    ReceiptPlan receiptPlan = receiptPlanMapper.selectByPrimaryKey(id);
                    if (receiptPlan != null) {
                        receiptPlan.setSendProNum(new BigDecimal(String.valueOf(objectList.get(21))));          // 发货数量
                        receiptPlan.setRealSendDate(Calendar.getInstance().getTime());                          // 实际发货时间
                        receiptPlan.setPlanOrderStatus(new BigDecimal(PlannerStatus.YesDeliver.getValue()));    // 排单状态为已发货
                        int i = receiptPlanMapper.updateByPrimaryKeySelective(receiptPlan);
                        if(i != 1){
                            throw new ProcessException("更新排单数据失败");
                        }
                        System.out.println("更新排单数据============================================" + JSONObject.toJSON(receiptPlan));
                    }
                }
            }
        }
        return list;
    }

    @Override
    public int insertImportData(OLogistics oLogistics) {
        return oLogisticsMapper.insertSelective(oLogistics);
    }

    /**
     * @Author: Zhang Lei
     * @Description: 退货时根据起止Sn号查询订单、物流信息
     * @Date: 14:13 2018/7/26
     */
    @Override
    public List<Map<String, Object>> getLogisticsBySn(String startSn, String endSn, String agentId) throws ProcessException {
        //查询起始SN、终止SN是否存在
        Map<String, Object> map = oLogisticsMapper.getOrderAndLogisticsBySn(startSn, agentId);
        if (map == null || map.size() <= 0) {
            throw new ProcessException("sn号" + startSn + "不在您的订单内");
        }

        if (!startSn.equals(endSn)) {
            Map<String, Object> map2 = oLogisticsMapper.getOrderAndLogisticsBySn(endSn, agentId);
            if (map2 == null || map2.size() <= 0) {
                throw new ProcessException("SN[" + endSn + "]不在您的发货订单中");
            }
        }

        List<Map<String, Object>> list = oLogisticsMapper.getOrderAndLogisticsBySns(startSn, endSn, agentId);
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO insertLogisticsDetail(String startSn, String endSn, Integer begins, Integer finish, String logisticsId, String cUser, String uUser) {
        //1.起始SN序列号  2.结束SN序列号  3.开始截取的位数   4.结束截取的位数
        if (StringUtils.isBlank(startSn)) {
            logger.info("起始SN序列号为空{}:", startSn);
            throw new ProcessException("起始SN序列号为空");
        }
        if (StringUtils.isBlank(endSn)) {
            logger.info("结束SN序列号为空{}:", endSn);
            throw new ProcessException("结束SN序列号为空");
        }
        if (null == begins) {
            logger.info("开始截取的位数为空{}:", begins);
            throw new ProcessException("开始截取的位数为空");
        }
        if (null == finish) {
            logger.info("结束截取的位数为空{}:", finish);
            throw new ProcessException("结束截取的位数为空");
        }
        List<String> idList = idList(startSn, endSn, begins, finish);
        if (null != idList && idList.size() > 0) {
            for (String idSn : idList) {
                OLogisticsDetail detail = new OLogisticsDetail();
                //id，物流id，创建人，更新人，状态
                detail.setId(idService.genId(TabId.o_logistics_detail));
                detail.setSnNum(idSn);
                detail.setLogisticsId(logisticsId);
                detail.setcUser(cUser);
                detail.setuUser(uUser);
                detail.setcTime(Calendar.getInstance().getTime());
                detail.setuTime(Calendar.getInstance().getTime());
                detail.setStatus(Status.STATUS_1.status);
                detail.setVersion(Status.STATUS_1.status);
                if (1 != oLogisticsDetailMapper.insertSelective(detail)) {
                    logger.info("添加失败");
                    throw new ProcessException("添加失败");
                }
            }
        }

        return ResultVO.success(null);
    }

    public static List<String> idList(String startSn, String endSn, Integer begins, Integer finish) {
        //1.startSn  2.endSn  3.开始截取的位数   4.结束截取的位数
        int begin = begins - 1;
        ArrayList<String> list = new ArrayList<>();
        String start = startSn;
        String end = endSn;
        String sSub = start.substring(begin, finish);
        String eSub = end.substring(begin, finish);
        int num = Integer.parseInt(sSub);
        int w = finish - begin;
        for (int j = Integer.parseInt(eSub) - Integer.parseInt(sSub); j >= 0; j--) {
            int x = num++;
            String format = String.format("%0" + w + "d", x);
            String c = start.substring(0, begin) + format + start.substring(finish);
            list.add(c);
        }
        return list;
    }

}
