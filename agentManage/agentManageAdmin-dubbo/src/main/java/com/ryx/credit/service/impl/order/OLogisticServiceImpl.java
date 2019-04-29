package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.machine.entity.ImsTermWarehouseDetail;
import com.ryx.credit.machine.service.ImsTermWarehouseDetailService;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.AdjustmentMachineVo;
import com.ryx.credit.machine.vo.LowerHairMachineVo;
import com.ryx.credit.machine.vo.MposSnVo;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.IOrderReturnService;
import com.ryx.credit.service.order.OLogisticsService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.zookeeper.data.Stat;
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
import java.util.regex.Pattern;

import static com.ryx.credit.common.util.Conver10ToConver33Utils.getBetweenValues;

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
    public final static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy/MM/dd");
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
    @Autowired
    private OLogisticsService oLogisticsService;
    @Autowired
    private OActivityMapper oActivityMapper;
    @Autowired
    private IOrderReturnService iOrderReturnService;
    @Autowired
    private OReturnOrderMapper returnOrderMapper;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private OReturnOrderDetailMapper oReturnOrderDetailMapper;

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

    @Override
    public List<String> addList(List<List<Object>> data, String user) throws Exception {
        List<String> listRes = new ArrayList<>();
        for (List<Object> objectList : data) {
            try {
                AgentResult agentResult =  oLogisticsService.addListItem(objectList,user);
                if(agentResult.isOK()) {
                    logger.info("导入物流{}成功", objectList.toString());
                    listRes.add("物流[" + objectList.toString() + "]导入成功");
                }else{
                    listRes.add("物流[" + objectList.toString() + "]导入失败:"+agentResult.getData());
                }
            }catch (MessageException e) {
                e.printStackTrace();
                logger.info("导入物流{}抛出异常",objectList.toString());
                listRes.add("物流["+objectList.toString()+"]导入失败:"+e.getMsg());
            }catch (Exception e) {
                e.printStackTrace();
                logger.info("导入物流{}抛出异常",objectList.toString());
                listRes.add("物流["+objectList.toString()+"]导入失败:"+e.getMessage());
            }
        }
        logger.info("user{}导入物流抛出异常的数据有{}",user,listRes.toString());
        return listRes;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult  addListItem(List<Object> objectList, String user) throws Exception{
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
            String planProNum="";
            String proComString="";
            List col = Arrays.asList(ReceiptPlanExportColum.ReceiptPlanExportColum_column.col);
            planNum = String.valueOf(objectList.get(col.indexOf("PLAN_NUM"))).trim();
            orderId = String.valueOf(objectList.get(col.indexOf("ORDER_ID"))).trim();
            proCode = String.valueOf(objectList.get(col.indexOf("PRO_CODE"))).trim();
            proId = String.valueOf(objectList.get(col.indexOf("PRO_ID"))).trim();
            proName = String.valueOf(objectList.get(col.indexOf("PRO_NAME"))).trim();

            sendDate = String.valueOf(objectList.get(col.indexOf("h"))).trim();
            sendProNum = String.valueOf(objectList.get(col.indexOf("g"))).trim();
            logCom = String.valueOf(objectList.get(col.indexOf("a"))).trim();
            wNumber = String.valueOf(objectList.get(col.indexOf("b"))).trim();
            beginSn = String.valueOf(objectList.get(col.indexOf("c"))).trim();
            endSn = String.valueOf(objectList.get(col.indexOf("d"))).trim();
            beginSnCount = String.valueOf(objectList.get(col.indexOf("e"))).trim();
            endSnCount = String.valueOf(objectList.get(col.indexOf("f"))).trim();
            proType = String.valueOf(objectList.get(col.indexOf("PRO_TYPE"))).trim();
            planProNum = String.valueOf(objectList.get(col.indexOf("PLAN_PRO_NUM"))).trim();
            proComString = String.valueOf(objectList.get(col.indexOf("PRO_COM_STRING"))).trim();

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
         /*  if (!proComString.equals(CardImportType.LD.msg)) {
               if (StringUtils.isBlank(beginSnCount)) {
                logger.info("请填写起始SN位数");
                throw new MessageException("请填写起始SN位数");
              }
               if (StringUtils.isBlank(endSnCount)) {
                logger.info("请填写结束SN位数");
                throw new MessageException("请填写结束SN位数");
              }
               //需要验证截取sn前面的字符是否一致
               String startSnString = beginSn.substring(0,Integer.parseInt(beginSnCount)-1);
               String endSnString = endSn.substring(0,Integer.parseInt(beginSnCount)-1);
               HashSet<Object> set = new HashSet<>();
               set.add(startSnString);
               set.add(endSnString);
               if (set.size()>1){
                   logger.info("请检查开始SN码与结束SN码截取之前的是否一致");
                   throw new MessageException("请检查开始SN码与结束SN码截取之前的是否一致");
               }
          }*/


            if (StringUtils.isBlank(logCom)) {
                logger.info("请填写物流公司");
                throw new MessageException("请填写物流公司");
            }
            if (StringUtils.isBlank(wNumber)) {
                logger.info("请填写物流单号");
                throw new MessageException("请填写物流单号");
            }
            if (StringUtils.isBlank(proType)) {
                logger.info("商品类型不能为空");
                throw new MessageException("商品类型不能为空");
            }
            if (new BigDecimal(sendProNum).compareTo(new BigDecimal(planProNum))==1) {
                logger.info("发货数量不能大于排单数量");
                throw new MessageException("发货数量不能大于排单数量");
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
            if (beginSnCount.equals("") || endSnCount.equals("")){
                beginSnCount="0";
                endSnCount="0";
            }

            //IDlist检查
            List<String> stringList = idList(beginSn, endSn,Integer.parseInt(beginSnCount),Integer.parseInt(endSnCount),proComString);
            if (Integer.valueOf(sendProNum) != stringList.size()) {
                logger.info("请仔细核对发货数量");
                throw new MessageException("请仔细核对发货数量");
            }

            //商品活动
            OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
            OSubOrderActivityExample.Criteria oSubOrderActivityExample_criteria = oSubOrderActivityExample.createCriteria();
            oSubOrderActivityExample_criteria.andSubOrderIdEqualTo(subOrderItem.getId()).andStatusEqualTo(Status.STATUS_1.status);
            List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
            if(null==oSubOrderActivities){
                logger.info("查询活动数据错误1");
                throw new MessageException("查询活动数据错误");
            }
            if(0==oSubOrderActivities.size()){
                logger.info("查询活动数据错误2");
                throw new MessageException("查询活动数据错误");
            }
            //商品活动临时表
            OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);
            OActivity oActivity = oActivityMapper.selectByPrimaryKey(oSubOrderActivity.getActivityId());

            //物流检查
            OLogisticsExample oLogisticsExample = new OLogisticsExample();
            OLogisticsExample.Criteria OLogisticsExample_criteria1 = oLogisticsExample.createCriteria();
            OLogisticsExample_criteria1.andSnBeginNumEqualTo(beginSn);
            OLogisticsExample_criteria1.andSnEndNumEqualTo(endSn);
            OLogisticsExample_criteria1.andWNumberEqualTo(wNumber);
            OLogisticsExample_criteria1.andLogComEqualTo(logCom);
            List<OLogistics> oLogistics1 = oLogisticsMapper.selectByExample(oLogisticsExample);
            if(null==oLogistics1){
                logger.info("该商品已发货请勿重复提交1");
                throw new MessageException("该商品已发货请勿重复提交");
            }
            if(oLogistics1.size()!=0){
                logger.info("该商品已发货请勿重复提交2");
                throw new MessageException("该商品已发货请勿重复提交");
            }

            //排单信息
            ReceiptPlan planVo = receiptPlanMapper.selectByPrimaryKey(planNum);
            if(planVo==null)throw new MessageException("排单信息未找到");
            //查询排单数量和已发送数量。如果
            if(planVo.getSendProNum()!=null){
                if(planVo.getSendProNum().add(new BigDecimal(sendProNum)).compareTo(planVo.getPlanProNum())>0){
                    throw new MessageException("发货数量已大于排单数量");
                }
            }else{
                if(new BigDecimal(sendProNum).compareTo(planVo.getPlanProNum())>0){
                    throw new MessageException("发货数量已大于排单数量");
                }
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
                    oLogistics.setSendDate(yyyyMMdd.parse(sendDate));// 物流日期
                }catch (Exception m){
                    try {
                        oLogistics.setSendDate(sdf.parse(sendDate));
                    }catch (Exception c){
                        throw new MessageException("日期格式支持yyyyMMdd 或者yyyy-MM-dd 或者 yyyy/MM/dd");
                    }

                }
            }
            oLogistics.setcTime(Calendar.getInstance().getTime());          // 创建时间
            oLogistics.setIsdeall(Status.STATUS_1.status);
            oLogistics.setReceiptPlanId(planNum); // 排单编号
            oLogistics.setOrderId(orderId);       // 订单编号
            oLogistics.setProId(proId);         // 商品ID
            oLogistics.setProName(proName);       // 商品名称
            oLogistics.setProPrice(subOrderItem.getProRelPrice());//商品单价
            oLogistics.setProCom(planVo.getProCom());// 厂家
            oLogistics.setProType(planVo.getProType());//排单添加商品类型
            oLogistics.setProModel(planVo.getModel());//型号
            oLogistics.setSendNum(new BigDecimal(sendProNum));  // 发货数量
            oLogistics.setLogCom(logCom);       // 物流公司
            oLogistics.setwNumber(wNumber);      // 物流单号
            oLogistics.setSnBeginNum(beginSn);   // 起始SN序列号
            oLogistics.setSnEndNum(endSn);     // 结束SN序列号


            logger.info("导入物流数据:{}={}" , oLogistics.getId(),JSONObject.toJSON(oLogistics));
            //调用明细接口之前需要先去数据库进行查询是否已有数据
            if (null != stringList && stringList.size() > 0) {
                for (String snNum : stringList) {
                    //检查sn是否存在物流状态和记录状态
                    OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                    OLogisticsDetailExample.Criteria oLogisticsDetailExample_criteria = oLogisticsDetailExample.createCriteria();
                    oLogisticsDetailExample_criteria.andStatusEqualTo(OLogisticsDetailStatus.STATUS_FH.code);
                    oLogisticsDetailExample_criteria.andRecordStatusEqualTo(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                    oLogisticsDetailExample_criteria.andSnNumEqualTo(snNum);
                    List<OLogisticsDetail> oLogisticsDetails = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                    if (null != oLogisticsDetails && oLogisticsDetails.size() > 0) {
                        //说明已经存在数据
                        logger.info(snNum+"此物流已经存在,正在发货中!!!");
                        throw new MessageException(snNum+"此物流已经存在,正在发货中!!!");
                    }
                }
            }


            //更新排单表发货数量
            ReceiptPlan receiptPlan = receiptPlanMapper.selectByPrimaryKey(planVo.getId());
            if (receiptPlan != null) {
                if(receiptPlan.getSendProNum()==null || receiptPlan.getSendProNum().compareTo(BigDecimal.ZERO)==0) {// 发货数量
                    receiptPlan.setSendProNum(oLogistics.getSendNum());
                }else{
                    receiptPlan.setSendProNum(receiptPlan.getSendProNum().add(oLogistics.getSendNum()));
                }
                receiptPlan.setRealSendDate(Calendar.getInstance().getTime());                          // 实际发货时间
                receiptPlan.setPlanOrderStatus(new BigDecimal(PlannerStatus.YesDeliver.getValue()));    // 排单状态为已发货
                if (receiptPlanMapper.updateByPrimaryKeySelective(receiptPlan)!= 1) {
                    throw new MessageException("更新排单数据失败！");
                }
                logger.info("更新排单数据=" + JSONObject.toJSON(receiptPlan));
            }

            //平台查询，根据不同的平台走不同的逻辑
            OOrder order = oOrderMapper.selectByPrimaryKey(orderId);
            PlatForm platForm =platFormMapper.selectByPlatFormNum(order.getOrderPlatform());

            //如果发货数量大于200-此处大量数据走任务
            if(oLogistics.getSendNum().compareTo(new BigDecimal(200))>0) {
                //物流为未发送状态
                oLogistics.setSendStatus(LogisticsSendStatus.none_send.code);
                if (1 != insertImportData(oLogistics)) {
                    throw new MessageException("排单编号为:"+planNum+"处理，插入物流信息失败,事物回滚");
                }else{
                    logger.info("导入物流数据,活动代码{}={}==========================================={}" ,oActivity.getActCode(),oLogistics.getId(), JSONObject.toJSON(oLogistics));
                }
                //如果是首刷进行sn检查库存中是否存在
                if (platForm.getPlatformType().equals(PlatformType.MPOS.msg) || platForm.getPlatformType().equals(PlatformType.MPOS.code)){
                    for (String idSn : stringList) {
                        OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                        oLogisticsDetailExample.or().andStatusEqualTo(Status.STATUS_0.status).andRecordStatusEqualTo(Status.STATUS_1.status).andSnNumEqualTo(idSn).andTerminalidTypeEqualTo(PlatformType.MPOS.code);
                        List<OLogisticsDetail> listOLogisticsDetailSn = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                        if (listOLogisticsDetailSn == null) {
                            logger.info("此SN码不存在");
                            throw new MessageException("此SN码不存在："+idSn);
                        } else if (listOLogisticsDetailSn.size() != 1) {
                            logger.info("此SN码不存在");
                            throw new MessageException("此SN库存数量有误："+idSn);
                        }
                    }
                }
                logger.info("物流机具数量大于200，采用任务来处理：物流-{}，数量-{}" ,oLogistics.getId(), oLogistics.getSendNum());
                return AgentResult.ok();
            }else{
                //物流为未发送状态
                oLogistics.setSendStatus(LogisticsSendStatus.send_ing.code);
                if (1 != insertImportData(oLogistics)) {
                    throw new MessageException("排单编号为:"+planNum+"处理，插入物流信息失败,事物回滚");
                }else{
                    logger.info("导入物流数据,活动代码{}={}==========================================={}" ,oActivity.getActCode(),oLogistics.getId(), JSONObject.toJSON(oLogistics));
                }
            }

            //直接发送逻辑-此处少量数据直接走接口
            ResultVO resultVO=new ResultVO();
            //遍历查询库里是否存在sn码
            if (platForm.getPlatformType().equals(PlatformType.MPOS.msg) || platForm.getPlatformType().equals(PlatformType.MPOS.code)){
                //首刷发货 更新库存记录
                logger.info("首刷发货 更新库存记录:{}:{}",proType,stringList);
                resultVO = updateLogisticsDetail(stringList, oLogistics.getId(), user, planVo.getId());
            }else if (platForm.getPlatformType().equals(PlatformType.ZPOS.code) || platForm.getPlatformType().equals(PlatformType.POS.code)){
                //POS发货生成物流明细
                logger.info("POS发货生成物流明细:{},{},{}",proType,oLogistics.getSnBeginNum(),oLogistics.getSnEndNum());
                resultVO = insertLogisticsDetail(oLogistics.getSnBeginNum(), oLogistics.getSnEndNum(),Integer.parseInt(beginSnCount),Integer.parseInt(endSnCount), oLogistics.getId(), user, planVo.getId());
            }else{
                //POS发货生成物流明细
                logger.info("POS发货生成物流明细:{},{},{}",proType,oLogistics.getSnBeginNum(),oLogistics.getSnEndNum());
                resultVO = insertLogisticsDetail(oLogistics.getSnBeginNum(), oLogistics.getSnEndNum(),Integer.parseInt(beginSnCount),Integer.parseInt(endSnCount), oLogistics.getId(), user, planVo.getId());
            }

            //插入成功更新排单信息
            if (resultVO.isSuccess()) {
                String id =  oLogistics.getReceiptPlanId();   // 排单编号
                if (null == id) {
                    throw new MessageException("排单ID查询失败！");
                } else {
                    //流量卡不进行下发操作
                    if(oActivity!=null && StringUtils.isNotBlank(oActivity.getActCode()) && "2204".equals(oActivity.getActCode())){
                        logger.info("导入物流数据,流量卡不进行下发操作，活动代码{}={}={}" ,oActivity.getActCode(),oLogistics.getId(), JSONObject.toJSON(oLogistics));
                        return AgentResult.ok("流量卡不进行下发操作");
                    }
                    //进行入库、机具划拨操作 POS下发业务系统
                    if (platForm.getPlatformType().equals(PlatformType.POS.code) || platForm.getPlatformType().equals(PlatformType.ZPOS.code)){

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
                        imsTermWarehouseDetail.setPosSpePrice(oSubOrderActivity.getPosSpePrice());
                        imsTermWarehouseDetail.setPosType(oSubOrderActivity.getPosType());
                        imsTermWarehouseDetail.setStandTime(oSubOrderActivity.getStandTime());
                        OLogistics logistics_send = oLogisticsMapper.selectByPrimaryKey(oLogistics.getId());
                        try {
                            //机具下发接口
                            AgentResult posSendRes = imsTermWarehouseDetailService.insertWarehouseAndTransfer(snList,imsTermWarehouseDetail);
                            if(posSendRes.isOK()){
                                logistics_send.setSendMsg(posSendRes.getMsg());
                                logistics_send.setSendStatus(Status.STATUS_1.status);
                                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics_send)){
                                    logger.info("pos下发物流更新失败STATUS_1成功{}",JSONObject.toJSONString(oLogistics));
                                }
                            }else{
                                logistics_send.setSendMsg(posSendRes.getMsg());
                                logistics_send.setSendStatus(Status.STATUS_2.status);
                                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics_send)){
                                    logger.info("pos下发物流更新失败STATUS_2失败{}",JSONObject.toJSONString(oLogistics));
                                }
                            }

                        } catch (MessageException e) {
                            e.printStackTrace();
                            logistics_send.setSendMsg(e.getMsg());
                            logistics_send.setSendStatus(Status.STATUS_2.status);
                            if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics_send)){
                                logger.info("pos下发物流更新失败MessageException{}",JSONObject.toJSONString(oLogistics));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            logistics_send.setSendMsg("下发异常");
                            logistics_send.setSendStatus(Status.STATUS_2.status);
                            if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics_send)){
                                logger.info("pos下发物流更新失败Exception{}",JSONObject.toJSONString(oLogistics));
                            }
                        }
                        //首刷下发业务系统
                    }else if(platForm.getPlatformType().equals(PlatformType.ZPOS.code) || platForm.getPlatformType().equals(PlatformType.ZPOS.msg)){

                        List<OLogisticsDetail> forsendSns = (List<OLogisticsDetail>)resultVO.getObj();
                        logger.info("物流下发发货数量sn查询获取数据：{}", JSONObject.toJSONString(forsendSns));

                        if(forsendSns.size()!=oLogistics.getSendNum().intValue()){
                            logger.info("物流下发发货数量不匹配");
                            throw new MessageException("物流下发发货数量不匹配");
                        }

                        OOrder oOrder = oOrderMapper.selectByPrimaryKey(subOrderItem.getOrderId());
                        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oOrder.getBusId());

                        //起始sn
                        OLogisticsDetailExample exampleOLogisticsDetailExamplestart = new OLogisticsDetailExample();
                        exampleOLogisticsDetailExamplestart.or().andSnNumEqualTo(oLogistics.getSnBeginNum()).andTerminalidTypeEqualTo(PlatformType.MPOS.code);
                        List<OLogisticsDetail> logisticsDetailsstart = oLogisticsDetailMapper.selectByExample(exampleOLogisticsDetailExamplestart);
                        OLogisticsDetail detailstart = logisticsDetailsstart.get(0);
                        //结束sn
                        OLogisticsDetailExample exampleOLogisticsDetailExampleend = new OLogisticsDetailExample();
                        exampleOLogisticsDetailExampleend.or().andSnNumEqualTo(oLogistics.getSnEndNum()).andTerminalidTypeEqualTo(PlatformType.MPOS.code);
                        List<OLogisticsDetail> logisticsDetailsend = oLogisticsDetailMapper.selectByExample(exampleOLogisticsDetailExampleend);
                        OLogisticsDetail detailend = logisticsDetailsend.get(0);

                        //sn号码段
                        LowerHairMachineVo lowerHairMachineVo = new LowerHairMachineVo();
                        lowerHairMachineVo.setBusNum(agentBusInfo.getBusNum());
                        lowerHairMachineVo.setOptUser(user);
                        lowerHairMachineVo.setSnStart(detailstart.getSnNum()+detailstart.getTerminalidCheck());
                        lowerHairMachineVo.setSnEnd(detailend.getSnNum()+detailend.getTerminalidCheck());
                        lowerHairMachineVo.setoLogisticsId(oLogistics.getId());

                        //sn明细
                        List<MposSnVo> listSn = new ArrayList<MposSnVo>();
                        String sBusProCode= "";
                        for (OLogisticsDetail forsendSn : forsendSns) {
                            listSn.add(new MposSnVo(forsendSn.getTermBatchcode()
                                    ,forsendSn.getSnNum()+forsendSn.getTerminalidCheck()
                                    ,forsendSn.getTerminalidKey()
                                    ,forsendSn.getBusProCode()
                                    ,forsendSn.getTermtype()));
                            if(org.apache.commons.lang.StringUtils.isEmpty(sBusProCode)) {
                                sBusProCode = forsendSn.getBusProCode();
                            }
                        }
                        lowerHairMachineVo.setListSn(listSn);
                        lowerHairMachineVo.setActCode(sBusProCode);
                        lowerHairMachineVo.setPlatFormNum(agentBusInfo.getBusPlatform());
                        //机具下发接口
                        OLogistics logistics_send = oLogisticsMapper.selectByPrimaryKey(lowerHairMachineVo.getoLogisticsId());
                        try {
                            logger.info("导入物流：下发到首刷平台请求参数:{}",JSONObject.toJSONString(lowerHairMachineVo));
                            AgentResult lowerHairMachineRes = termMachineService.lowerHairMachine(lowerHairMachineVo);
                            logger.info("导入物流：下发到首刷平台结果:{}",lowerHairMachineRes.getMsg());
                            if(lowerHairMachineRes.isOK()) {
                                logistics_send.setSendStatus(Status.STATUS_1.status);
                                logistics_send.setSendMsg(lowerHairMachineRes.getMsg());
                                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics_send)){
                                    logger.info("pos下发物流更新记录STATUS_1失败{}",JSONObject.toJSONString(oLogistics));
                                }
                            }else{
                                logistics_send.setSendStatus(Status.STATUS_2.status);
                                logistics_send.setSendMsg(lowerHairMachineRes.getMsg());
                                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics_send)){
                                    logger.info("pos下发物流更新记录STATUS_2失败{}",JSONObject.toJSONString(oLogistics));
                                }
                            }
                        }catch (MessageException e) {
                            e.printStackTrace();
                            logistics_send.setSendStatus(Status.STATUS_2.status);
                            logistics_send.setSendMsg(e.getMsg());
                            if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics_send)){
                                logger.info("pos下发物流更新记录MessageException失败{}",JSONObject.toJSONString(oLogistics));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            logistics_send.setSendStatus(Status.STATUS_2.status);
                            logistics_send.setSendMsg("下发异常");
                            if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics_send)){
                                logger.info("pos下发物流更新记录Exception失败{}",JSONObject.toJSONString(oLogistics));
                            }
                        }
                    }
                }
            }else{
                return AgentResult.fail(resultVO.getResInfo());
            }
        return AgentResult.ok();
    }

    /**
     * 检查上传物流excel列是否修改过
     * @param excel
     * @param db
     * @return
     */
    @Override
    public AgentResult checkRecordPlan(List<Object> excel,Map<String,Object> db){
        Object PLAN_NUM = db.get("PLAN_NUM");
        String [] col= ReceiptPlanExportColum.ReceiptPlanExportColum_column.code.split(",");
        String [] title= ReceiptPlanExportColum.ReceiptPlanExportColum_title.code.split(",");
        for (int i=0;i<18;i++){
            if(!col[i].equals("SEND_PRO_NUM")){
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

    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
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
            String terminalidCheck="";
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
                String sn = list.get(1);
                sn_num=String.valueOf(sn.substring(0, sn.length() - 1));
                terminalid_key= String.valueOf(list.get(2));
                terminalid_seq=String.valueOf(list.get(3));
                terminalidCheck=String.valueOf(sn.substring(sn.length()-1));
                OLogisticsDetail oLogisticsDetail = new OLogisticsDetail();
                oLogisticsDetail.setId(idService.genId(TabId.o_logistics_detail));
                oLogisticsDetail.setcTime(Calendar.getInstance().getTime());
                oLogisticsDetail.setcUser(user);
                oLogisticsDetail.setTerminalid(terminalid);
                oLogisticsDetail.setTerminalidKey(terminalid_key);
                oLogisticsDetail.setTerminalidSeq(terminalid_seq);
                oLogisticsDetail.setTerminalidCheck(terminalidCheck);
                oLogisticsDetail.setSnNum(sn_num);
                oLogisticsDetail.setStatus(Status.STATUS_0.status);
                oLogisticsDetail.setRecordStatus(Status.STATUS_1.status);
                oLogisticsDetail.setVersion(Status.STATUS_0.status);
                oLogisticsDetail.setTerminalidType(PlatformType.MPOS.code);

                OLogisticsDetailExample example = new OLogisticsDetailExample();
                example.or().andSnNumEqualTo(sn_num);
                if(oLogisticsDetailMapper.selectByExample(example).size()>0){
                    logger.info("导入sn{}已存在",sn_num);
                    throw new MessageException(sn_num+"已存在");
                }

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
    public int updateSnStatus(String orderId, String startSn, String endSn, BigDecimal status, BigDecimal recordStatus,String returnId) throws Exception {
        int res = oLogisticsMapper.updateSnStatus(orderId, startSn, endSn, status, recordStatus,returnId);
        return res;
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

        if (StringUtils.isBlank(logisticsId)){
            logger.info("物流id为空{}:", logisticsId);
            throw new ProcessException("物流id为空");
        }
        OLogisticsExample oLogisticsExample = new OLogisticsExample();
        OLogisticsExample.Criteria criteria = oLogisticsExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andIdEqualTo(logisticsId);
        List<OLogistics> oLogistics = oLogisticsMapper.selectByExample(oLogisticsExample);
        if (null==oLogistics || oLogistics.size()==0){
            logger.info("无此物流信息{}:", logisticsId);
            throw new ProcessException("无此物流信息");
        }
        OLogistics ol = oLogistics.get(0);
        if (!ol.getProCom().equals(CardImportType.LD.msg)) {
            if (null == begins) {
                logger.info("开始截取的位数为空{}:", begins);
                throw new ProcessException("开始截取的位数为空");
            }
            if (null == finish) {
                logger.info("结束截取的位数为空{}:", finish);
                throw new ProcessException("结束截取的位数为空");
            }
        }
        List<String> idList = idList(startSn, endSn, begins, finish,ol.getProCom());

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
                    detail.setSettlementPrice(oSubOrderActivity.getPrice());
                    detail.setPosType(oSubOrderActivity.getPosType());
                    detail.setPosSpePrice(oSubOrderActivity.getPosSpePrice());
                    detail.setStandTime(oSubOrderActivity.getStandTime());
                }
                detail.setSnNum(idSn);
                detail.setAgentId(order.getAgentId());
                detail.setcUser(cUser);
                detail.setuUser(cUser);
                detail.setcTime(Calendar.getInstance().getTime());
                detail.setuTime(Calendar.getInstance().getTime());
                detail.setOptType(OLogisticsDetailOptType.ORDER.code);
                detail.setOptId(orderId);
                OOrder oOrder = oOrderMapper.selectByPrimaryKey(orderId);
                detail.setBusId(oOrder.getBusId());
                if(StringUtils.isNotBlank(planVo.getReturnOrderDetailId())) {
                    OReturnOrderDetail detail1 = oReturnOrderDetailMapper.selectByPrimaryKey(planVo.getReturnOrderDetailId());
                    detail.setReturnOrderId(detail1.getReturnId());
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
                }else{
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                }
                detail.setSendStatus(LogisticsDetailSendStatus.send_success.code);
                detail.setVersion(Status.STATUS_1.status);
                if (1 != oLogisticsDetailMapper.insertSelective(detail)) {
                    logger.info("添加失败");
                    throw new ProcessException("添加失败");
                }
            }
        }
        return ResultVO.success(idList);
    }

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
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
                oLogisticsDetailExample.or()
                        .andStatusEqualTo(Status.STATUS_0.status)
                        .andRecordStatusEqualTo(Status.STATUS_1.status)
                        .andSnNumEqualTo(idSn)
                        .andTerminalidTypeEqualTo(PlatformType.MPOS.code);
                List<OLogisticsDetail>  listOLogisticsDetailSn = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                if (listOLogisticsDetailSn==null){
                    logger.info("此SN码不存在");
                    throw new MessageException("此SN码不存在");
                }else if(listOLogisticsDetailSn.size()!=1){
                    logger.info("此SN码不存在");
                    throw new MessageException("此SN码不唯一");
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
                    detail.setSettlementPrice(oSubOrderActivity.getPrice());
                    detail.setPosType(oSubOrderActivity.getPosType());
                    detail.setPosSpePrice(oSubOrderActivity.getPosSpePrice());
                    detail.setStandTime(oSubOrderActivity.getStandTime());
                }
                detail.setAgentId(order.getAgentId());
                detail.setcUser(cUser);
                detail.setuUser(cUser);
                detail.setcTime(Calendar.getInstance().getTime());
                detail.setuTime(Calendar.getInstance().getTime());
                detail.setOptType(OLogisticsDetailOptType.ORDER.code);
                detail.setOptId(orderId);
                OOrder oOrder = oOrderMapper.selectByPrimaryKey(orderId);
                detail.setBusId(oOrder.getBusId());
                if(StringUtils.isNotBlank(planVo.getReturnOrderDetailId())) {
                    OReturnOrderDetail detail1 = oReturnOrderDetailMapper.selectByPrimaryKey(planVo.getReturnOrderDetailId());
                    detail.setReturnOrderId(detail1.getReturnId());
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
                }else{
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                }
                detail.setSendStatus(LogisticsDetailSendStatus.send_success.code);
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


    @Override
    public List<String> idList(String startSn, String endSn, Integer begins, Integer finish,String proCom) throws MessageException {
        //1.startSn  2.endSn  3.开始截取的位数   4.结束截取的位数
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(proCom)){
            logger.info("厂家为空");
            throw new MessageException("厂家为空");
        }
        if (CardImportType.LD.name().equals(proCom) || proCom.equals(CardImportType.LD.msg) || proCom.equals(CardImportType.LD.code)) {
            list= getBetweenValues(startSn, endSn);
        }else {
            Map digit = Conver10ToConver33Utils.getDigit(startSn, endSn);
            //SN码
            String end = (String) digit.get("lastSn");
            String start = (String) digit.get("firstSn");
            //位数
            finish = (Integer) digit.get("length");
            begins = (Integer) digit.get("num");
            if(!begins.equals(Integer.valueOf(0))){
                    int begin = begins - 1;
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
            }else{
                list.add(startSn);
            }
        }
        return list;
    }

    /**
     * 查詢物流明细
     * @param param
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo getOLogisticsDetailList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = oLogisticsDetailMapper.getOLogisticsDetailCount(param);
        List<Map<String, Object>> list = oLogisticsDetailMapper.getOLogisticsDetailList(param);
    /*    for (Map<String, Object> stringObjectMap : list) {
            Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(),String.valueOf(stringObjectMap.get("PRO_COM")));
            if(dict!=null){
                stringObjectMap.put("PRO_COM",dict.getdItemname());
            }
            Dict modelType = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(),String.valueOf(stringObjectMap.get("PRO_TYPE")));
            if (null!=modelType){
                stringObjectMap.put("PRO_TYPE",modelType.getdItemname());
            }
        }*/
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        return pageInfo;
    }


    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Override
    public AgentResult sendLgcInfoToBusSystem(String lgcId,String userId)throws Exception {
        OLogistics logistics = oLogisticsMapper.selectByPrimaryKey(lgcId);
        if(logistics.getSendStatus()!=null){
            if(logistics.getSendStatus().compareTo(Status.STATUS_2.status)!=0){
                return AgentResult.fail("只发送联动失败的物流");
            }
        }
        //排单信息
        ReceiptPlan receiptPlan = receiptPlanMapper.selectByPrimaryKey(logistics.getReceiptPlanId());
        //退货的话调用退货下发
        if(org.apache.commons.lang.StringUtils.isNotEmpty(receiptPlan.getReturnOrderDetailId())){
           return iOrderReturnService.sendReturnLgcInfoToBusSystem(lgcId,userId);
        }
        OLogisticsDetailExample example = new OLogisticsDetailExample();
        example.or().andLogisticsIdEqualTo(logistics.getId()).andRecordStatusEqualTo(Status.STATUS_1.status).andStatusEqualTo(Status.STATUS_1.status);
        List<OLogisticsDetail>  listDetails = oLogisticsDetailMapper.selectByExample(example);
        if(listDetails.size()<=0){
            return AgentResult.fail("物流明细为空");
        }
        if(listDetails.size()!=logistics.getSendNum().intValue()){
            logger.info("物流下发发货数量不匹配");
            return AgentResult.fail("物流下发发货数量不匹配");
        }
        if (!logistics.getProType().equals(PlatformType.MPOS.msg) && !logistics.getProType().equals(PlatformType.MPOS.code)){
            List<String> ids = new ArrayList<>();
            for (OLogisticsDetail listDetail : listDetails) {
                ids.add(listDetail.getSnNum());
            }
            OLogisticsDetail detail = listDetails.get(0);
            ImsTermWarehouseDetail imsTermWarehouseDetail = new ImsTermWarehouseDetail();
            OOrder oOrder = oOrderMapper.selectByPrimaryKey(logistics.getOrderId());
            if (null==oOrder) {
                throw new MessageException("查询订单数据失败！");
            }
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oOrder.getBusId());
            if (null==agentBusInfo) {
                throw new MessageException("查询业务数据失败！");
            }
            imsTermWarehouseDetail.setOrgId(agentBusInfo.getBusNum());
            imsTermWarehouseDetail.setMachineId(detail.getBusProCode());
            imsTermWarehouseDetail.setPosSpePrice(detail.getPosSpePrice());
            imsTermWarehouseDetail.setPosType(detail.getPosType());
            imsTermWarehouseDetail.setStandTime(detail.getStandTime());
            try {
                //机具下发接口
                AgentResult posSendRes = imsTermWarehouseDetailService.insertWarehouseAndTransfer(ids,imsTermWarehouseDetail);
                if(posSendRes.isOK()){
                    logistics.setSendMsg(posSendRes.getMsg());
                    logistics.setSendStatus(Status.STATUS_1.status);
                    if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                        logger.info("pos下发物流更新失败STATUS_1成功{}",JSONObject.toJSONString(logistics));
                    }
                    return AgentResult.ok();
                }else{
                    logistics.setSendMsg(posSendRes.getMsg());
                    logistics.setSendStatus(Status.STATUS_2.status);
                    if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                        logger.info("pos下发物流更新失败STATUS_2失败{}",JSONObject.toJSONString(logistics));
                    }
                    return AgentResult.fail(posSendRes.getMsg());
                }
            } catch (MessageException e) {
                e.printStackTrace();
                logistics.setSendMsg(e.getMsg());
                logistics.setSendStatus(Status.STATUS_2.status);
                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                    logger.info("pos下发物流更新失败MessageException{}",JSONObject.toJSONString(logistics));
                }
                return AgentResult.fail(e.getMsg());
            }catch (Exception e){
                e.printStackTrace();
                logistics.setSendMsg("下发异常");
                logistics.setSendStatus(Status.STATUS_2.status);
                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                    logger.info("pos下发物流更新失败Exception{}",JSONObject.toJSONString(logistics));
                }
                return AgentResult.fail(e.getLocalizedMessage());
            }
            //首刷下发业务系统
        }else{
            OOrder oOrder = oOrderMapper.selectByPrimaryKey(logistics.getOrderId());
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oOrder.getBusId());
            //起始sn
            OLogisticsDetailExample exampleOLogisticsDetailExamplestart = new OLogisticsDetailExample();
            exampleOLogisticsDetailExamplestart.or().andSnNumEqualTo(logistics.getSnBeginNum()).andTerminalidTypeEqualTo(PlatformType.MPOS.code);
            List<OLogisticsDetail> logisticsDetailsstart = oLogisticsDetailMapper.selectByExample(exampleOLogisticsDetailExamplestart);
            OLogisticsDetail detailstart = logisticsDetailsstart.get(0);
            //结束sn
            OLogisticsDetailExample exampleOLogisticsDetailExampleend = new OLogisticsDetailExample();
            exampleOLogisticsDetailExampleend.or().andSnNumEqualTo(logistics.getSnEndNum()).andTerminalidTypeEqualTo(PlatformType.MPOS.code);
            List<OLogisticsDetail> logisticsDetailsend = oLogisticsDetailMapper.selectByExample(exampleOLogisticsDetailExampleend);
            OLogisticsDetail detailend = logisticsDetailsend.get(0);

            //sn号码段
            LowerHairMachineVo lowerHairMachineVo = new LowerHairMachineVo();
            lowerHairMachineVo.setBusNum(agentBusInfo.getBusNum());
            lowerHairMachineVo.setOptUser(userId);
            lowerHairMachineVo.setSnStart(detailstart.getSnNum()+detailstart.getTerminalidCheck());
            lowerHairMachineVo.setSnEnd(detailend.getSnNum()+detailend.getTerminalidCheck());
            lowerHairMachineVo.setoLogisticsId(logistics.getId());
            //sn明细
            List<MposSnVo> listSn = new ArrayList<MposSnVo>();
            String sBusProCode= "";
            for (OLogisticsDetail forsendSn : listDetails) {
                listSn.add(new MposSnVo(forsendSn.getTermBatchcode()
                        ,forsendSn.getSnNum()+(forsendSn.getTerminalidCheck()==null?"":forsendSn.getTerminalidCheck())
                        ,forsendSn.getTerminalidKey()
                        ,forsendSn.getBusProCode()
                        ,forsendSn.getTermtype()));
                if(org.apache.commons.lang.StringUtils.isEmpty(sBusProCode)) {
                    sBusProCode = forsendSn.getBusProCode();
                }
            }
            lowerHairMachineVo.setListSn(listSn);
            lowerHairMachineVo.setActCode(sBusProCode);
            lowerHairMachineVo.setPlatFormNum(agentBusInfo.getBusPlatform());

            //机具下发接口
            OLogistics logistics_send = oLogisticsMapper.selectByPrimaryKey(lowerHairMachineVo.getoLogisticsId());
            try {
                AgentResult lowerHairMachineRes = termMachineService.lowerHairMachine(lowerHairMachineVo);
                logger.info("导入物流：下发到首刷平台结果:{}",lowerHairMachineRes.getMsg());
                if(lowerHairMachineRes.isOK()) {
                    logistics_send.setSendStatus(Status.STATUS_1.status);
                    logistics_send.setSendMsg(lowerHairMachineRes.getMsg());
                    if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics_send)){
                        logger.info("pos下发物流更新记录STATUS_1失败{}",JSONObject.toJSONString(logistics));
                    }
                    return AgentResult.ok();
                }else{
                    logistics_send.setSendStatus(Status.STATUS_2.status);
                    logistics_send.setSendMsg(lowerHairMachineRes.getMsg());
                    if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics_send)){
                        logger.info("pos下发物流更新记录STATUS_2失败{}",JSONObject.toJSONString(logistics));
                    }
                    return AgentResult.fail(lowerHairMachineRes.getMsg());
                }
            }catch (MessageException e) {
                e.printStackTrace();
                logistics_send.setSendStatus(Status.STATUS_2.status);
                logistics_send.setSendMsg(e.getMsg());
                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics_send)){
                    logger.info("pos下发物流更新记录MessageException失败{}",JSONObject.toJSONString(logistics));
                }
                return AgentResult.fail(e.getMsg());
            } catch (Exception e) {
                e.printStackTrace();
                logistics_send.setSendStatus(Status.STATUS_2.status);
                logistics_send.setSendMsg("下发异常");
                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics_send)){
                    logger.info("pos下发物流更新记录Exception失败{}",JSONObject.toJSONString(logistics));
                }
                return AgentResult.fail(e.getLocalizedMessage());
            }
        }
    }
}
