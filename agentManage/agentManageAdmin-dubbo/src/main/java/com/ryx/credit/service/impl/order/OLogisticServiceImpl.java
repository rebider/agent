package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.dao.order.OLogisticsDetailMapper;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.order.OLogisticsMapper;
import com.ryx.credit.dao.order.ReceiptPlanMapper;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.OLogistics;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.credit.pojo.admin.order.OLogisticsDetailExample;
import com.ryx.credit.pojo.admin.order.ReceiptPlan;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OLogisticsService;
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
    @Autowired
    private DictOptionsService dictOptionsService;

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

    @Override
    public int insertImportData(OLogistics oLogistics) {
        return oLogisticsMapper.insertSelective(oLogistics);
    }


    /**
     * 物流信息：
     * 1、导入物流信息
     * 2、调用明细接口
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public List<String> addList(List<List<Object>> data, String user, Integer begins, Integer finish) throws Exception {
        List<String> list = new ArrayList<>();
        for (List<Object> objectList : data) {
            if (StringUtils.isBlank(String.valueOf(objectList.get(23)))) {
                logger.info("排单编号为空");
                throw new MessageException("排单编号为空");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(23)))) {
                logger.info("订单编号为空");
                throw new MessageException("订单编号为空");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(23)))) {
                logger.info("商品编号为空");
                throw new MessageException("商品编号为空");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(23)))) {
                logger.info("商品ID为空");
                throw new MessageException("商品ID为空");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(23)))) {
                logger.info("请填写发货数量");
                throw new MessageException("请填写发货数量");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(26)))) {
                logger.info("请填写起始SN序列号");
                throw new MessageException("请填写起始SN序列号");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(27)))) {
                logger.info("请填写结束SN序列号");
                throw new MessageException("请填写结束SN序列号");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(28)))) {
                logger.info("请填写起始SN位数");
                throw new MessageException("请填写起始SN位数");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(29)))) {
                logger.info("请填写结束SN位数");
                throw new MessageException("请填写结束SN位数");
            }
            String snBeginNum = String.valueOf(String.valueOf(objectList.get(26)));   // 起始SN序列号
            String snEndNum = String.valueOf(String.valueOf(objectList.get(27)));   // 结束SN序列号
            begins = Integer.valueOf(String.valueOf(objectList.get(28)));   // 起始SN位数
            finish = Integer.valueOf(String.valueOf(objectList.get(29)));   // 结束SN位数
            List<String> stringList = idList(snBeginNum, snEndNum, begins, finish);
            if (Integer.valueOf(String.valueOf(objectList.get(23))) != stringList.size()) {
                logger.info("请仔细核对发货数量");
                throw new MessageException("请仔细核对发货数量");
            }
            OLogistics oLogistics = new OLogistics();
            oLogistics.setcUser(user);                                      // 创建人
            oLogistics.setStatus(Status.STATUS_1.status);                   // 默认记录状态为1
            oLogistics.setLogType(LogType.Deliver.getValue());              // 默认物流类型为1
            oLogistics.setId(idService.genId(TabId.o_logistics));           // 物流ID序列号
            oLogistics.setSendDate(Calendar.getInstance().getTime());       // 物流日期
            oLogistics.setcTime(Calendar.getInstance().getTime());          // 创建时间
            oLogistics.setIsdeall(Status.STATUS_1.status);
            oLogistics.setReceiptPlanId(String.valueOf(objectList.get(0))); // 排单编号
            oLogistics.setOrderId(String.valueOf(objectList.get(1)));       // 订单编号
            oLogistics.setProId(String.valueOf(objectList.get(3)));         // 商品ID
            oLogistics.setProName(String.valueOf(objectList.get(4)));       // 商品名称
            if (StringUtils.isNotBlank(String.valueOf(objectList.get(5)))) {
                Dict dictByName = dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(), String.valueOf(objectList.get(5)));
                if (null != dictByName && !dictByName.getdItemname().equals(""))
                    oLogistics.setProType(dictByName.getdItemvalue());      // 商品类型
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(9)))) {
                logger.info("未匹配到厂家:{}", String.valueOf(objectList.get(9)));
                throw new MessageException("未匹配到厂家");
            }
            Dict dictByName = dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), String.valueOf(objectList.get(9)));
            if (null != dictByName && !dictByName.getdItemname().equals(""))
                oLogistics.setProCom(dictByName.getdItemvalue());// 厂家
            try {

                oLogistics.setProModel(String.valueOf(objectList.get(11)));      // 机型
                oLogistics.setProPrice(new BigDecimal(String.valueOf(objectList.get(6))));   // 商品单价
                oLogistics.setSendNum(new BigDecimal(String.valueOf(objectList.get(23))));  // 发货数量
                oLogistics.setLogCom(null != objectList.get(24) ? String.valueOf(objectList.get(24)) : "");       // 物流公司
                oLogistics.setwNumber(null != objectList.get(25) ? String.valueOf(objectList.get(25)) : "");      // 物流单号
                oLogistics.setSnBeginNum(null != objectList.get(26) ? String.valueOf(objectList.get(26)) : "");   // 起始SN序列号
                oLogistics.setSnEndNum(null != objectList.get(27) ? String.valueOf(objectList.get(27)) : "");     // 结束SN序列号
            } catch (Exception e) {
                throw new MessageException("Excel参数错误！");
            }
            System.out.println("导入物流数据============================================" + JSONObject.toJSON(oLogistics));
            if (1 != insertImportData(oLogistics)) {
                throw new MessageException("插入失败！");
            }
            list.add(oLogistics.getId());

            //调用明细接口之前需要先去数据库进行查询是否已有数据
            List<String> idList = idList(oLogistics.getSnBeginNum(), oLogistics.getSnEndNum(), begins, finish);
            if (null != idList && idList.size() > 0) {
                for (String snNum : idList) {
                    OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                    OLogisticsDetailExample.Criteria criteria = oLogisticsDetailExample.createCriteria();
                    criteria.andStatusEqualTo(Status.STATUS_1.status);
                    criteria.andSnNumEqualTo(snNum);
                    List<OLogisticsDetail> oLogisticsDetails = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                    if (null != oLogisticsDetails && oLogisticsDetails.size() > 0) {
                        //说明已经存在数据
                        logger.info("此物流已经存在,正在发货中!!!");
                        throw new MessageException("此物流已经存在,正在发货中!!!");
                    }
                }
            }
            // 调用明细接口
            ResultVO resultVO = insertLogisticsDetail(oLogistics.getSnBeginNum(), oLogistics.getSnEndNum(), begins, finish, oLogistics.getId(), user, user);
            if (resultVO.isSuccess()) {
                String id = "";
                id = oLogistics.getReceiptPlanId();   // 排单编号
                if (null == id) {
                    throw new MessageException("排单ID查询失败！");
                } else {
                    ReceiptPlan receiptPlan = receiptPlanMapper.selectByPrimaryKey(id);
                    if (receiptPlan != null) {
                        receiptPlan.setSendProNum(new BigDecimal(String.valueOf(objectList.get(21))));          // 发货数量
                        receiptPlan.setRealSendDate(Calendar.getInstance().getTime());                          // 实际发货时间
                        receiptPlan.setPlanOrderStatus(new BigDecimal(PlannerStatus.YesDeliver.getValue()));    // 排单状态为已发货
                        int i = receiptPlanMapper.updateByPrimaryKeySelective(receiptPlan);
                        if (i != 1) {
                            throw new MessageException("更新排单数据失败！");
                        }
                        System.out.println("更新排单数据============================================" + JSONObject.toJSON(receiptPlan));
                    }
                }
            }
        }

        return list;
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
            throw new ProcessException("sn号" + startSn + "不在您的订单中，或已发起过退货");
        }

        if (!startSn.equals(endSn)) {
            Map<String, Object> map2 = oLogisticsMapper.getOrderAndLogisticsBySn(endSn, agentId);
            if (map2 == null || map2.size() <= 0) {
                throw new ProcessException("SN[" + endSn + "]不在您的发货订单中，或已发起过退货");
            }
        }

        List<Map<String, Object>> list = oLogisticsMapper.getOrderAndLogisticsBySns(startSn, endSn, agentId);
        return list;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 更新状态
     * @Date: 20:18 2018/8/3
     */
    @Override
    public void updateSnStatus(String orderId, String startSn, String endSn, BigDecimal status) throws Exception {
        oLogisticsMapper.updateSnStatus(orderId, startSn, endSn, status);
    }


    /**
     * 插入物流明细
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO insertLogisticsDetail(String startSn, String endSn, Integer begins, Integer finish, String logisticsId, String cUser, String uUser) throws MessageException {
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

    public static List<String> idList(String startSn, String endSn, Integer begins, Integer finish) throws MessageException {
        //1.startSn  2.endSn  3.开始截取的位数   4.结束截取的位数
        int begin = begins - 1;
        ArrayList<String> list = new ArrayList<>();
        String start = startSn;
        String end = endSn;
        if (startSn.length() != begins || endSn.length() != finish) {
            logger.info("请输入正确的起始和结束SN号位数");
            throw new MessageException("请输入正确的起始和结束SN号位数");
        }
        String sSub = start.substring(begin, finish);
        String eSub = end.substring(begin, finish);
        if ("".equals(eSub) || "".equals(sSub)) {
            logger.info("请输入正确的起始和结束SN号位数");
            throw new MessageException("请输入正确的起始和结束SN号位数");
        }
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
