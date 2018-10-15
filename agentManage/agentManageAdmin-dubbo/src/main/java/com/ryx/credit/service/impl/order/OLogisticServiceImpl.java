package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.machine.entity.ImsTermWarehouseDetail;
import com.ryx.credit.machine.service.ImsTermWarehouseDetailService;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.LowerHairMachineVo;
import com.ryx.credit.machine.vo.MposSnVo;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OLogisticsService;
import org.apache.commons.lang.time.DateFormatUtils;
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
    public final static SimpleDateFormat sdfyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private OOrderMapper oOrderMapper;
    @Autowired
    private OLogisticsMapper oLogisticsMapper;
    @Autowired
    private OSubOrderActivityMapper oSubOrderActivityMapper;
    @Autowired
    private OSubOrderMapper oSubOrderMapper;
    @Autowired
    private OReceiptProMapper oReceiptProMapper;
    @Autowired
    private OLogisticsDetailMapper oLogisticsDetailMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private ImsTermWarehouseDetailService imsTermWarehouseDetailService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private OSubOrderActivityMapper subOrderActivityMapper;
    @Autowired
    private TermMachineService termMachineService;

    /**
     * 物流信息:
     * 1、列表查询
     * 2、导出物流信息
     */
    @Override
    public PageInfo getOLogisticsList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = oLogisticsMapper.getOLogisticsCount(param);
        List<Map<String, Object>> list = oLogisticsMapper.getOLogisticsList(param);
        for (Map<String, Object> stringObjectMap : list) {
            Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(),String.valueOf(stringObjectMap.get("PRO_COM")));
            if(dict!=null){
                stringObjectMap.put("PRO_COM",dict.getdItemname());
            }
            Dict modelType = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(),String.valueOf(stringObjectMap.get("PRO_TYPE")));
            if (null!=modelType){
                stringObjectMap.put("PRO_TYPE",modelType.getdItemname());
            }

        }
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
     * 订单发货上传物流信息
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public List<String> addList(List<List<Object>> data, String user) throws Exception {
        List<String> list = new ArrayList<>();
        for (List<Object> objectList : data) {

            String planNum = "";
            String orderId = "";
            String proCode = "";
            String proId = "";
            String proName = "";
            String sendDate = "";
            String sendProNum = "";
            String beginSn = "";
            String endSn = "";
            String beginSnCount = "";
            String endSnCount = "";
            String logCom = "";
            String wNumber = "";
            String proType="";
            try {

                List col = Arrays.asList(ReceiptPlanExportColum.ReceiptPlanExportColum_column.col);
                planNum = String.valueOf(objectList.get(col.indexOf("PLAN_NUM")));
                orderId = String.valueOf(objectList.get(col.indexOf("ORDER_ID")));
                proCode = String.valueOf(objectList.get(col.indexOf("PRO_CODE")));
                proId = String.valueOf(objectList.get(col.indexOf("PRO_ID")));
                proName = String.valueOf(objectList.get(col.indexOf("PRO_NAME")));

                sendDate = String.valueOf(objectList.get(col.indexOf("h")));
                sendProNum = String.valueOf(objectList.get(col.indexOf("g")));
                logCom = String.valueOf(objectList.get(col.indexOf("a")));
                wNumber = String.valueOf(objectList.get(col.indexOf("b")));
                beginSn = String.valueOf(objectList.get(col.indexOf("c")));
                endSn = String.valueOf(objectList.get(col.indexOf("d")));
                beginSnCount = String.valueOf(objectList.get(col.indexOf("e")));
                endSnCount = String.valueOf(objectList.get(col.indexOf("f")));
                proType = String.valueOf(objectList.get(col.indexOf("PRO_TYPE")));

                if (StringUtils.isBlank(sendDate)) {
                    logger.info("发货日期不能为空");
                    throw new MessageException("发货日期不能为空");
                }
                if (StringUtils.isBlank(planNum)) {
                    logger.info("排单编号为空");
                    throw new MessageException("排单编号为空");
                }
                if (StringUtils.isBlank(orderId)){
                    logger.info("订单编号为空");
                    throw new MessageException("订单编号为空");
                }
                if (StringUtils.isBlank(proCode)) {
                    logger.info("商品编号为空");
                    throw new MessageException("商品编号为空");
                }
                if (StringUtils.isBlank(proId)) {
                    logger.info("商品ID为空");
                    throw new MessageException("商品ID为空");
                }
                if (StringUtils.isBlank(sendProNum)) {
                    logger.info("请填写发货数量");
                    throw new MessageException("请填写发货数量");
                }
                if (StringUtils.isBlank(beginSn)) {
                    logger.info("请填写起始SN序列号");
                    throw new MessageException("请填写起始SN序列号");
                }
                if (StringUtils.isBlank(endSn)){
                    logger.info("请填写结束SN序列号");
                    throw new MessageException("请填写结束SN序列号");
                }
                if (StringUtils.isBlank(beginSnCount)) {
                    logger.info("请填写起始SN位数");
                    throw new MessageException("请填写起始SN位数");
                }
                if (StringUtils.isBlank(endSnCount)) {
                    logger.info("请填写结束SN位数");
                    throw new MessageException("请填写结束SN位数");
                }
                if (StringUtils.isBlank(logCom)) {
                    logger.info("请填写物流公司");
                    throw new MessageException("请填写物流公司");
                }
                if (StringUtils.isBlank(wNumber)) {
                    logger.info("请填写物流单号");
                    throw new MessageException("请填写物流单号");
                }
                OSubOrderExample example = new OSubOrderExample();
                example.or().andStatusEqualTo(Status.STATUS_1.status).andProIdEqualTo(proId).andOrderIdEqualTo(orderId);
                List<OSubOrder>  subOrders = oSubOrderMapper.selectByExample(example);
                if(subOrders.size()!=1){
                    logger.info("请填写物流单号");
                    throw new MessageException("订单["+orderId+"]的商品["+proId+"]数量大于1");
                }
                OSubOrder subOrderItem = subOrders.get(0);
                //校验文档不能更改
                List<Map<String,Object>> listItem = receiptPlanMapper.getReceipPlanList(FastMap.fastMap("PLAN_NUM",planNum));
                if(listItem.size()>0){
                       //检查列是否有更改
                       AgentResult agentResult = checkRecordPlan(objectList,listItem.get(0));
                        if(!agentResult.isOK()){
                            logger.info("校验Excel文档失败：[],[]",planNum,agentResult.getMsg());
                            throw new MessageException(agentResult.getMsg());
                        }
                }else{
                    throw new MessageException("排单信息未找到");
                }

                OLogisticsExample oLogisticsExample = new OLogisticsExample();
                OLogisticsExample.Criteria criteria1 = oLogisticsExample.createCriteria();
                criteria1.andSnBeginNumEqualTo(beginSn);
                criteria1.andSnEndNumEqualTo(endSn);
                criteria1.andWNumberEqualTo(wNumber);
                criteria1.andLogComEqualTo(logCom);
                List<OLogistics> oLogistics1 = oLogisticsMapper.selectByExample(oLogisticsExample);
                if(null==oLogistics1){
                    logger.info("该商品已发货请勿重复提交1");
                    throw new MessageException("该商品已发货请勿重复提交");
                }
                if(oLogistics1.size()!=0){
                    logger.info("该商品已发货请勿重复提交2");
                    throw new MessageException("该商品已发货请勿重复提交");
                }

                //IDlist检查
                List<String> stringList = idList(beginSn, endSn,Integer.parseInt(beginSnCount),Integer.parseInt(endSnCount));
                if (Integer.valueOf(sendProNum) != stringList.size()) {
                    logger.info("请仔细核对发货数量");
                    throw new MessageException("请仔细核对发货数量");
                }

                //物流信息
                OLogistics oLogistics = new OLogistics();
                oLogistics.setId(idService.genId(TabId.o_logistics));           // 物流ID序列号
                oLogistics.setcUser(user);                                      // 创建人
                oLogistics.setStatus(Status.STATUS_1.status);                   // 默认记录状态为1
                oLogistics.setLogType(LogType.Deliver.getValue());              // 默认物流类型为1
                try {
                    oLogistics.setSendDate(sdfyyyyMMdd.parse(sendDate));
                }catch (Exception e){
                    try {
                        oLogistics.setSendDate(sdf.parse(sendDate));// 物流日期
                    }catch (Exception m){
                        throw new MessageException("日期格式支持yyyyMMdd 或者yyyy-MM-dd");
                    }
                }
                oLogistics.setcTime(Calendar.getInstance().getTime());          // 创建时间
                oLogistics.setIsdeall(Status.STATUS_1.status);

                //ID信息
                oLogistics.setReceiptPlanId(planNum); // 排单编号
                oLogistics.setOrderId(orderId);       // 订单编号
                oLogistics.setProId(proId);         // 商品ID
                oLogistics.setProName(proName);       // 商品名称
                oLogistics.setProPrice(subOrderItem.getProRelPrice());//商品单价
                //排单信息
                ReceiptPlan planVo = receiptPlanMapper.selectByPrimaryKey(oLogistics.getReceiptPlanId());
                if(planVo==null)throw new MessageException("排单信息未找到");

                //商品信息从排单表里查
                oLogistics.setProCom(planVo.getProCom());// 厂家
                oLogistics.setProType(planVo.getProType());//排单添加商品类型
                oLogistics.setProModel(planVo.getModel());//型号

                oLogistics.setSendNum(new BigDecimal(sendProNum));  // 发货数量
                oLogistics.setLogCom(logCom);       // 物流公司
                oLogistics.setwNumber(wNumber);      // 物流单号
                oLogistics.setSnBeginNum(beginSn);   // 起始SN序列号
                oLogistics.setSnEndNum(endSn);     // 结束SN序列号

                logger.info("导入物流数据============================================{}" , JSONObject.toJSON(oLogistics));
                if (1 != insertImportData(oLogistics)) {
                    throw new MessageException("排单编号为:"+planNum+"处理，插入物流信息失败");
                }
                list.add(oLogistics.getId());
                //调用明细接口之前需要先去数据库进行查询是否已有数据
                if (null != stringList && stringList.size() > 0) {
                    for (String snNum : stringList) {
                        //检查sn是否存在物流状态和记录状态
                        OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                        OLogisticsDetailExample.Criteria criteria = oLogisticsDetailExample.createCriteria();
                        criteria.andStatusEqualTo(OLogisticsDetailStatus.STATUS_FH.code);
                        criteria.andRecordStatusEqualTo(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                        criteria.andSnNumEqualTo(snNum);
                        List<OLogisticsDetail> oLogisticsDetails = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                        if (null != oLogisticsDetails && oLogisticsDetails.size() > 0) {
                            //说明已经存在数据
                            logger.info(snNum+"此物流已经存在,正在发货中!!!");
                            throw new MessageException(snNum+"此物流已经存在,正在发货中!!!");
                        }
                    }
                }
                ResultVO resultVO=new ResultVO();
                //遍历查询库里是否存在sn码
                if (proType.equals(PlatformType.MPOS.msg)){
                    //首刷发货 更新库存记录
                    resultVO = updateLogisticsDetail(stringList, oLogistics.getId(), user, planVo.getId());
                }else{
                    //POS发货生成物流明细
                    resultVO = insertLogisticsDetail(oLogistics.getSnBeginNum(), oLogistics.getSnEndNum(),Integer.parseInt(beginSnCount),Integer.parseInt(endSnCount), oLogistics.getId(), user, planVo.getId());
                }

                //插入成功更新排单信息
                if (resultVO.isSuccess()) {
                    String id =  oLogistics.getReceiptPlanId();   // 排单编号
                    if (null == id) {
                        throw new MessageException("排单ID查询失败！");
                    } else {
                        ReceiptPlan receiptPlan = receiptPlanMapper.selectByPrimaryKey(id);
                        if (receiptPlan != null) {
                            if(receiptPlan.getSendProNum()==null || receiptPlan.getSendProNum().compareTo(BigDecimal.ZERO)==0) {// 发货数量
                                receiptPlan.setSendProNum(new BigDecimal(sendProNum));
                            }else{
                                receiptPlan.setSendProNum(receiptPlan.getSendProNum().add(new BigDecimal(sendProNum)));
                            }
                            receiptPlan.setRealSendDate(Calendar.getInstance().getTime());                          // 实际发货时间
                            receiptPlan.setPlanOrderStatus(new BigDecimal(PlannerStatus.YesDeliver.getValue()));    // 排单状态为已发货
                            if (receiptPlanMapper.updateByPrimaryKeySelective(receiptPlan)!= 1) {
                                throw new MessageException("更新排单数据失败！");
                            }
                            System.out.println("更新排单数据============================================" + JSONObject.toJSON(receiptPlan));
                        }
                        //商品活动
                        OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
                        OSubOrderActivityExample.Criteria criteria = oSubOrderActivityExample.createCriteria();
                        criteria.andSubOrderIdEqualTo(subOrderItem.getId());
                        List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
                        if(null==oSubOrderActivities){
                            logger.info("查询活动数据错误1");
                            throw new MessageException("查询活动数据错误");
                        }
                        if(0==oSubOrderActivities.size()){
                            logger.info("查询活动数据错误2");
                            throw new MessageException("查询活动数据错误");
                        }
                        OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);

                        //进行入库、机具划拨操作 POS下发业务系统
                        if (proType.equals(PlatformType.POS.msg) || proType.equals(PlatformType.ZPOS.msg)){

                            List<String> snList = JsonUtil.jsonToPojo(JsonUtil.objectToJson(resultVO.getObj()), List.class);
                            ImsTermWarehouseDetail imsTermWarehouseDetail = new ImsTermWarehouseDetail();
                            OOrder oOrder = oOrderMapper.selectByPrimaryKey(subOrderItem.getOrderId());
                            if (null==oOrder) {
                                throw new MessageException("查询订单数据失败！");
                            }
                            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oOrder.getBusId());
                            if (null==agentBusInfo) {
                                throw new MessageException("查询业务数据失败！");
                            }
                            imsTermWarehouseDetail.setOrgId(agentBusInfo.getBusNum());
                            imsTermWarehouseDetail.setMachineId(oSubOrderActivity.getBusProCode());
                            imsTermWarehouseDetailService.insertWarehouseAndTransfer(snList,imsTermWarehouseDetail);

                        //首刷洗发业务系统
                        }else if(proType.equals(PlatformType.MPOS.msg)){

                            List<OLogisticsDetail> forsendSns = (List<OLogisticsDetail>)resultVO.getObj();
                            OOrder oOrder = oOrderMapper.selectByPrimaryKey(subOrderItem.getOrderId());
                            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oOrder.getBusId());
                            //sn号码段
                            LowerHairMachineVo lowerHairMachineVo = new LowerHairMachineVo();
                            lowerHairMachineVo.setBusNum(agentBusInfo.getBusNum());
                            lowerHairMachineVo.setOptUser(user);
                            lowerHairMachineVo.setSnStart(oLogistics.getSnBeginNum());
                            lowerHairMachineVo.setSnEnd(oLogistics.getSnEndNum());
                            //sn明细
                            List<MposSnVo> listSn = new ArrayList<MposSnVo>();
                            for (OLogisticsDetail forsendSn : forsendSns) {
                                listSn.add(new MposSnVo(forsendSn.getTermBatchcode()
                                        ,forsendSn.getSnNum()
                                        ,forsendSn.getTerminalidKey()
                                        ,forsendSn.getBusProCode()
                                        ,forsendSn.getTermtype()));
                            }
                            lowerHairMachineVo.setListSn(listSn);

                            //机具下发接口
                            AgentResult lowerHairMachineRes = termMachineService.lowerHairMachine(lowerHairMachineVo);
                            logger.info("导入物流：洗发到首刷平台结果:{}",lowerHairMachineRes.getMsg());

                        }else{
                            logger.info("导入物流：平台类型错误");
                            throw new MessageException("平台类型错误");
                        }
                    }
                }
            } catch (MessageException e) {
                e.printStackTrace();
                throw e;
            }catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return list;
    }

    /**
     * 检查上传物流excel列是否修改过
     * @param excel
     * @param db
     * @return
     */
    private AgentResult checkRecordPlan(List<Object> excel,Map<String,Object> db){
        Object PLAN_NUM = db.get("PLAN_NUM");
        String [] col= ReceiptPlanExportColum.ReceiptPlanExportColum_column.code.split(",");
        String [] title= ReceiptPlanExportColum.ReceiptPlanExportColum_title.code.split(",");
        for (int i=0;i<18;i++){
            if(null==db.get(col[i]) || db.get(col[i]).toString().length()==0){
               continue;
            }
            if(excel.get(i)==null || StringUtils.isBlank(excel.get(i).toString())){
                return AgentResult.fail(PLAN_NUM+title[i]+"有改动");
            }
            if(!(excel.get(i)+"").equals((db.get(col[i])+""))){
                return AgentResult.fail(PLAN_NUM+title[i]+"有改动");
            }
        }
        return AgentResult.ok();
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
     * @Description: 退货时根据Sn号查询订单、物流信息
     * @Date: 14:13 2018/7/26
     */
    @Override
    public Map<String, Object> getLogisticsBySn(String sn, String agentId) throws ProcessException {
        Map<String, Object> map = oLogisticsMapper.getOrderAndLogisticsBySn(sn, agentId);
        if (map == null || map.size() <= 0) {
            throw new ProcessException("sn号" + sn + "不是有效的发货状态，请核实是否属于您的订单或是否发起过退货");
        }
        return map;
    }

    @Override
    public List<String> addSn(List<List<String>> data, String user) throws Exception {
        List<String> snList = new ArrayList<>();
        if (null == data && data.size() == 0) {
            logger.info("导入的数据为空");
            throw new MessageException("导入的数据为空");
        }
        for (List<String> list : data) {
            String terminalid="";
            String terminalid_key="";
            String terminalid_seq="";
            String sn_num="";
                if (StringUtils.isBlank(list.get(0))) {
                    logger.info("终端号不能为空");
                    throw new MessageException("终端号不能为空");
                }
                if (StringUtils.isBlank(list.get(1))) {
                    logger.info("SN码不能为空");
                    throw new MessageException("SN码不能为空");
                }
                if (StringUtils.isBlank(list.get(2))) {
                    logger.info("秘钥不能为空");
                    throw new MessageException("秘钥不能为空");
                }
                if (StringUtils.isBlank(list.get(3))) {
                    logger.info("序列不能为空");
                    throw new MessageException("序列不能为空");
                }
                terminalid=String.valueOf(list.get(0));
                sn_num=String.valueOf(list.get(1));
                terminalid_key= String.valueOf(list.get(2));
                terminalid_seq=String.valueOf(list.get(3));
                OLogisticsDetail oLogisticsDetail = new OLogisticsDetail();
                oLogisticsDetail.setId(idService.genId(TabId.o_logistics_detail));
                oLogisticsDetail.setcTime(Calendar.getInstance().getTime());
                oLogisticsDetail.setcUser(user);
                oLogisticsDetail.setTerminalid(terminalid);
                oLogisticsDetail.setTerminalidKey(terminalid_key);
                oLogisticsDetail.setTerminalidSeq(terminalid_seq);
                oLogisticsDetail.setSnNum(sn_num);
                oLogisticsDetail.setStatus(Status.STATUS_0.status);
                oLogisticsDetail.setRecordStatus(Status.STATUS_1.status);
                oLogisticsDetail.setVersion(Status.STATUS_0.status);
                oLogisticsDetail.setTerminalidType(PlatformType.MPOS.code);
                if ( oLogisticsDetailMapper.insertSelective(oLogisticsDetail)==0){
                    logger.info("导入sn码失败");
                    throw new MessageException("导入sn码失败");
                }
                snList.add(oLogisticsDetail.getId());

        }
        return snList;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 更新状态
     * @Date: 20:18 2018/8/3
     */
    @Override
    public void updateSnStatus(String orderId, String startSn, String endSn, BigDecimal status, BigDecimal recordStatus,String returnId) throws Exception {
        oLogisticsMapper.updateSnStatus(orderId, startSn, endSn, status, recordStatus,returnId);
    }


    /**
     * 插入物流明细
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO insertLogisticsDetail(String startSn, String endSn, Integer begins, Integer finish, String logisticsId, String cUser, String planId) throws MessageException {
        ReceiptPlan planVo = receiptPlanMapper.selectByPrimaryKey(planId);
        String orderId = planVo.getOrderId();//订单ID
        String proId = planVo.getProId();//收货单商品id
        OReceiptPro oReceiptPro  = oReceiptProMapper.selectByPrimaryKey(proId);
        OSubOrderExample example = new OSubOrderExample();
        example.or().andOrderIdEqualTo(orderId).andProIdEqualTo(oReceiptPro.getProId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OSubOrder> oSubOrders = oSubOrderMapper.selectByExample(example);
        if(oSubOrders.size()==0){
            throw new MessageException("商品价格未能锁定");
        }
        OSubOrder oSubOrder = oSubOrders.get(0);
        OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
        oSubOrderActivityExample.or().andSubOrderIdEqualTo(oSubOrder.getId()).andProIdEqualTo(oSubOrder.getProId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OSubOrderActivity>  OSubOrderActivitylist = oSubOrderActivityMapper.selectByExample(oSubOrderActivityExample);
        OOrder order = oOrderMapper.selectByPrimaryKey(oSubOrder.getOrderId());
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
                detail.setOrderId(oSubOrder.getOrderId());
                detail.setOrderNum(order.getoNum());
                detail.setLogisticsId(logisticsId);
                detail.setProId(oSubOrder.getProId());
                detail.setProName(oSubOrder.getProName());
                detail.setSettlementPrice(oSubOrder.getProRelPrice());
                if(OSubOrderActivitylist.size()>0){
                    OSubOrderActivity oSubOrderActivity = OSubOrderActivitylist.get(0);
                    detail.setActivityId(oSubOrderActivity.getActivityId());
                    detail.setActivityName(oSubOrderActivity.getActivityName());
                    detail.setgTime(oSubOrderActivity.getgTime());
                    detail.setBusProCode(oSubOrderActivity.getBusProCode());
                    detail.setBusProName(oSubOrderActivity.getBusProName());
                    detail.setTermBatchcode(oSubOrderActivity.getTermBatchcode());
                    detail.setTermBatchname(oSubOrderActivity.getTermBatchname());
                    detail.setTermtype(oSubOrderActivity.getTermtype());
                    detail.setTermtypename(oSubOrderActivity.getTermtypename());
                }
                detail.setSnNum(idSn);
                detail.setAgentId(order.getAgentId());
                detail.setcUser(cUser);
                detail.setuUser(cUser);
                detail.setcTime(Calendar.getInstance().getTime());
                detail.setuTime(Calendar.getInstance().getTime());
                detail.setOptType(OLogisticsDetailOptType.ORDER.code);
                detail.setOptId(orderId);
                if(StringUtils.isNotBlank(planVo.getReturnOrderDetailId())) {
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
                }else{
                    detail.setReturnOrderId(planVo.getReturnOrderDetailId());
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                }
                detail.setVersion(Status.STATUS_1.status);
                if (1 != oLogisticsDetailMapper.insertSelective(detail)) {
                    logger.info("添加失败");
                    throw new ProcessException("添加失败");
                }
            }
        }
        return ResultVO.success(idList);
    }

    @Override
    public ResultVO updateLogisticsDetail(List<String> idList , String logisticsId, String cUser, String planId) throws MessageException {

        ReceiptPlan planVo = receiptPlanMapper.selectByPrimaryKey(planId);
        String orderId = planVo.getOrderId();//订单ID
        String proId = planVo.getProId();//收货单商品id
        OReceiptPro oReceiptPro  = oReceiptProMapper.selectByPrimaryKey(proId);
        OSubOrderExample example = new OSubOrderExample();
        example.or().andOrderIdEqualTo(orderId).andProIdEqualTo(oReceiptPro.getProId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OSubOrder> oSubOrders = oSubOrderMapper.selectByExample(example);
        if(oSubOrders.size()==0){
            throw new MessageException("商品价格未能锁定");
        }
        OSubOrder oSubOrder = oSubOrders.get(0);
        OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
        oSubOrderActivityExample.or().andSubOrderIdEqualTo(oSubOrder.getId()).andProIdEqualTo(oSubOrder.getProId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OSubOrderActivity>  OSubOrderActivitylist = oSubOrderActivityMapper.selectByExample(oSubOrderActivityExample);
        OOrder order = oOrderMapper.selectByPrimaryKey(oSubOrder.getOrderId());

        List<OLogisticsDetail> resOLogisticsDetail = new ArrayList();
        if (null != idList && idList.size() > 0) {
            for (String idSn : idList) {

                OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                oLogisticsDetailExample.or().andStatusEqualTo(Status.STATUS_0.status).andRecordStatusEqualTo(Status.STATUS_1.status).andSnNumEqualTo(idSn).andTerminalidTypeEqualTo(PlatformType.MPOS.code);
                List<OLogisticsDetail>  listOLogisticsDetailSn = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                if (listOLogisticsDetailSn==null){
                    logger.info("此SN码不存在");
                    throw new MessageException("此SN码不存在");
                }else if(listOLogisticsDetailSn.size()!=1){
                    logger.info("此SN码不存在");
                    throw new MessageException("此SN码不存在");
                }

                OLogisticsDetail detail = listOLogisticsDetailSn.get(0);
                //id，物流id，创建人，更新人，状态
                detail.setOrderId(oSubOrder.getOrderId());
                detail.setOrderNum(order.getoNum());
                detail.setLogisticsId(logisticsId);
                detail.setProId(oSubOrder.getProId());
                detail.setProName(oSubOrder.getProName());
                detail.setSettlementPrice(oSubOrder.getProRelPrice());
                if(OSubOrderActivitylist.size()>0){
                    OSubOrderActivity oSubOrderActivity = OSubOrderActivitylist.get(0);
                    detail.setActivityId(oSubOrderActivity.getActivityId());
                    detail.setActivityName(oSubOrderActivity.getActivityName());
                    detail.setgTime(oSubOrderActivity.getgTime());
                    detail.setBusProCode(oSubOrderActivity.getBusProCode());
                    detail.setBusProName(oSubOrderActivity.getBusProName());
                    detail.setTermBatchcode(oSubOrderActivity.getTermBatchcode());
                    detail.setTermBatchname(oSubOrderActivity.getTermBatchname());
                    detail.setTermtype(oSubOrderActivity.getTermtype());
                    detail.setTermtypename(oSubOrderActivity.getTermtypename());
                }
                detail.setAgentId(order.getAgentId());
                detail.setcUser(cUser);
                detail.setuUser(cUser);
                detail.setcTime(Calendar.getInstance().getTime());
                detail.setuTime(Calendar.getInstance().getTime());
                detail.setOptType(OLogisticsDetailOptType.ORDER.code);
                detail.setOptId(orderId);
                if(StringUtils.isNotBlank(planVo.getReturnOrderDetailId())) {
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
                }else{
                    detail.setReturnOrderId(planVo.getReturnOrderDetailId());
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                }
                if (1 != oLogisticsDetailMapper.updateByPrimaryKeySelective(detail)) {
                    logger.info("修改失败");
                    throw new ProcessException("修改失败");
                }else{
                    resOLogisticsDetail.add(detail);
                }
            }
        }

        return ResultVO.success(resOLogisticsDetail);
    }


    public List<String> idList(String startSn, String endSn, Integer begins, Integer finish) throws MessageException {
        //1.startSn  2.endSn  3.开始截取的位数   4.结束截取的位数
        int begin = begins - 1;
        ArrayList<String> list = new ArrayList<>();
        String start = startSn;
        String end = endSn;
        if (startSn.length() < begins || endSn.length() <finish) {
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
