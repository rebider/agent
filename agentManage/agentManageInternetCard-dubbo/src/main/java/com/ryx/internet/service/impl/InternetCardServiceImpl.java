package com.ryx.internet.service.impl;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.BeanUtils;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.credit.service.order.OLogisticsService;
import com.ryx.internet.dao.*;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.internet.pojo.*;
import com.ryx.internet.service.InternetCardService;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 *
 * @Author liudh
 * @Description //TODO 
 * @Date 2018/12/4 14:38
 * @Param
 * @return
 **/
@Service("internetCardService")
public class InternetCardServiceImpl implements InternetCardService {

    private static final String[] dateFormat = new String[]{DateUtil.DATE_FORMAT_yyyy_MM_dd,DateUtil.DATE_FORMAT_yyyy_MM_dd2};
    private static Logger log = LoggerFactory.getLogger(InternetCardServiceImpl.class);

    private static final long TIME_OUT = 60000*5;      //锁的超时时间
    private static final long ACQUIRE_TIME_OUT = 5000;  //超时时间

    @Autowired
    private OInternetCardMapper internetCardMapper;
    @Autowired
    private OInternetCardImportMapper internetCardImportMapper;
    @Autowired
    private InternetCardImportHistoryMapper internetCardImportHistoryMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private AgentService agentService;
    @Autowired
    private OLogisticsService logisticsService;
    @Autowired
    private InternetCardService internetCardService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OInternetCardMerchMapper internetCardMerchMapper;
    @Autowired
    private OInternetRenewDetailMapper internetRenewDetailMapper;
    @Autowired
    private InternetRenewOffsetMapper internetRenewOffsetMapper;



    @Override
    public PageInfo internetCardList(OInternetCard internetCard, Page page,String agentId){

        OInternetCardExample oInternetCardExample = new OInternetCardExample();
        oInternetCardExample= queryParam(internetCard, oInternetCardExample,agentId);
        oInternetCardExample.setPage(page);
        List<OInternetCard> oInternetCards = internetCardMapper.selectByExample(oInternetCardExample);
        for (OInternetCard oInternetCard : oInternetCards) {
            Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(),oInternetCard.getManufacturer());
            if(null!=dict)
            oInternetCard.setManufacturer(dict.getdItemname());
            oInternetCard.setIccidNumId(oInternetCard.getIccidNum());
            if(null==oInternetCard.getInternetCardStatus()){
                oInternetCard.setRenewButton("0");
                continue;
            }
            if(null==oInternetCard.getRenew()){
                oInternetCard.setRenewButton("0");
                continue;
            }
            //是否需续费为是,才展示按钮
            if(oInternetCard.getRenew().compareTo(BigDecimal.ZERO)==0){
                oInternetCard.setRenewButton("0");
                continue;
            }
            if((oInternetCard.getInternetCardStatus().compareTo(InternetCardStatus.NORMAL.getValue())==0 || oInternetCard.getInternetCardStatus().compareTo(InternetCardStatus.NOACTIVATE.getValue())==0 )
                    && !oInternetCard.getRenewStatus().equals(InternetRenewStatus.XFZ.getValue())){
                if(null==oInternetCard.getExpireTime()){
                    oInternetCard.setRenewButton("0");
                    continue;
                }
                Date date = stepMonth(new Date(), 3);
                if(oInternetCard.getExpireTime().getTime()<date.getTime()){
                    oInternetCard.setRenewButton("1");
                }else{
                    oInternetCard.setRenewButton("0");
                }
            }else{
                oInternetCard.setRenewButton("0");
            }
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(oInternetCards);
        pageInfo.setTotal((int)internetCardMapper.countByExample(oInternetCardExample));
        return pageInfo;
    }


    public static Date stepMonth(Date sourceDate, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }


    @Override
    public List<OInternetCard> queryInternetCardList(OInternetCard internetCard, Page page,String agentId){
        OInternetCardExample oInternetCardExample = new OInternetCardExample();
        oInternetCardExample = queryParam(internetCard, oInternetCardExample,agentId);
        oInternetCardExample.setPage(page);
        List<OInternetCard> oInternetCards = internetCardMapper.queryInternetCardList(oInternetCardExample);
        return oInternetCards;
    }

    @Override
    public Integer queryInternetCardCount(OInternetCard internetCard,String agentId){
        OInternetCardExample oInternetCardExample = new OInternetCardExample();
        oInternetCardExample = queryParam(internetCard, oInternetCardExample,agentId);
        Integer count = Integer.valueOf((int)internetCardMapper.countByExample(oInternetCardExample));
        return count;
    }

    /**
     * 查询和导出的条件
     * @param internetCard
     * @param oInternetCardExample
     * @return
     */
    private OInternetCardExample queryParam(OInternetCard internetCard, OInternetCardExample oInternetCardExample,String agentId){

        OInternetCardExample.Criteria criteria = oInternetCardExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        if(StringUtils.isNotBlank(internetCard.getBatchNum())){
            criteria.andBatchNumEqualTo(internetCard.getBatchNum());
        }
        if(StringUtils.isNotBlank(internetCard.getIccidNum())){
            criteria.andIccidNumEqualTo(internetCard.getIccidNum());
        }
        if(StringUtils.isNotBlank(internetCard.getSnNum())){
            criteria.andSnNumEqualTo(internetCard.getSnNum());
        }
        if(StringUtils.isNotBlank(internetCard.getCardImportId())){
            criteria.andCardImportIdEqualTo(internetCard.getCardImportId());
        }
        if(null!=internetCard.getRenew()){
            criteria.andRenewEqualTo(internetCard.getRenew());
        }
        //如果代理商登陆执行此查询条件
        if(StringUtils.isNotBlank(agentId)){
            criteria.andAgentIdEqualTo(agentId);
        }else if(StringUtils.isNotBlank(internetCard.getAgentId())){
            criteria.andAgentIdEqualTo(internetCard.getAgentId());
        }
        if(StringUtils.isNotBlank(internetCard.getAgentName())){
            criteria.andAgentNameEqualTo(internetCard.getAgentName());
        }
        if(StringUtils.isNotBlank(internetCard.getOrderId())){
            criteria.andOrderIdEqualTo(internetCard.getOrderId());
        }
        if(null!=internetCard.getStop()){
            criteria.andStopEqualTo(internetCard.getStop());
        }
        if(StringUtils.isNotBlank(internetCard.getRenewStatus())){
            criteria.andRenewStatusEqualTo(internetCard.getRenewStatus());
        }
        if(null!=internetCard.getInternetCardStatus()){
            criteria.andInternetCardStatusEqualTo(internetCard.getInternetCardStatus());
        }
        if(StringUtils.isNotBlank(internetCard.getOpenAccountTimeBeginStr())){
            Date format = DateUtil.format(internetCard.getOpenAccountTimeBeginStr(), DateUtil.DATE_FORMAT_yyyy_MM_dd);
            criteria.andOpenAccountTimeGreaterThanOrEqualTo(format);
        }
        if(StringUtils.isNotBlank(internetCard.getOpenAccountTimeEndStr())){
            Date format = DateUtil.format(internetCard.getOpenAccountTimeEndStr(), DateUtil.DATE_FORMAT_yyyy_MM_dd);
            criteria.andOpenAccountTimeLessThanOrEqualTo(format);
        }
        if(StringUtils.isNotBlank(internetCard.getExpireTimeBeginStr())){
            Date format = DateUtil.format(internetCard.getExpireTimeBeginStr(), DateUtil.DATE_FORMAT_yyyy_MM_dd);
            criteria.andExpireTimeGreaterThanOrEqualTo(format);
        }
        if(StringUtils.isNotBlank(internetCard.getExpireTimeEndStr())){
            Date format = DateUtil.format(internetCard.getExpireTimeEndStr(), DateUtil.DATE_FORMAT_yyyy_MM_dd);
            criteria.andExpireTimeLessThanOrEqualTo(format);
        }
        oInternetCardExample.setOrderByClause(" c_time desc ");

        return oInternetCardExample;
    }


    @Override
    public PageInfo internetCardImportList(OInternetCardImport internetCardImport, Page page){

        OInternetCardImportExample internetCardImportExample = new OInternetCardImportExample();
        OInternetCardImportExample.Criteria criteria = internetCardImportExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        if(null!=internetCardImport.getImportStatus()){
            criteria.andImportStatusEqualTo(internetCardImport.getImportStatus());
        }
        if(StringUtils.isNotBlank(internetCardImport.getImportType())){
            criteria.andImportTypeEqualTo(internetCardImport.getImportType());
        }
        if(StringUtils.isNotBlank(internetCardImport.getBatchNum())){
            criteria.andBatchNumEqualTo(internetCardImport.getBatchNum());
        }
        internetCardImportExample.setPage(page);
        internetCardImportExample.setOrderByClause(" c_time desc ");
        List<OInternetCardImport> oInternetCards = internetCardImportMapper.selectByExample(internetCardImportExample);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(oInternetCards);
        pageInfo.setTotal((int)internetCardImportMapper.countByExample(internetCardImportExample));
        return pageInfo;
    }



    @Override
    public void importInternetCard(String fileUrl, String importType, String userId,String batchNo)throws Exception{

        if(StringUtils.isBlank(CardImportType.getContentByValue(importType))){
             throw new MessageException("导入类型错误");
        }
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<List<String>> excelList = BigDataExcelUtils.bigDataGetExcel(fileUrl);
                    if(null==excelList){
                        log.error("importInternetCard,导入文件为空,用户id:{},batchNo:{}",userId,batchNo);
                        return;
                    }
                    if(excelList.size()==0){
                        log.error("importInternetCard,导入文件为空,用户id:{},batchNo:{}",userId,batchNo);
                        return;
                    }
                    for (List<String> string : excelList) {
                        OInternetCard oInternetCard = new OInternetCard();
                        String jsonList = "";
                        if(importType.equals(CardImportType.A.getValue())){
                            String issuer = String.valueOf(string.size()>=1?string.get(0):"");//发卡方
                            String InternetCardNum = String.valueOf(string.size()>=2?string.get(1):"");//物联卡号
                            String iccidNum = String.valueOf(string.size()>=3?string.get(2):"");//ICCID
                            String openAccountTime = String.valueOf(string.size()>=4?string.get(3):"");//开户日期

                            oInternetCard.setIccidNum(iccidNum.equals("null")?"":iccidNum);
                            oInternetCard.setIssuer(issuer.equals("null")?"":issuer);
                            oInternetCard.setInternetCardNum(InternetCardNum.equals("null")?"":InternetCardNum);
                            if(StringUtils.isNotBlank(openAccountTime.equals("null")?"":openAccountTime))
                            oInternetCard.setOpenAccountTime(DateUtils.parseDate(openAccountTime,dateFormat));
                            jsonList = JsonUtil.objectToJson(oInternetCard);
                        }else if(importType.equals(CardImportType.B.getValue())){
                            String manufacturer = String.valueOf(string.size()>=1?string.get(0):"");//发货方/厂商
                            String deliverTime = String.valueOf(string.size()>=2?string.get(1):"");//发货日期
                            String orderId = String.valueOf(string.size()>=3?string.get(2):"");//订单号
                            String agentName = String.valueOf(string.size()>=4?string.get(3):"");//代理商名称
                            String snNum = String.valueOf(string.size()>=5?string.get(4):"");//机具SN
                            String iccidNum = String.valueOf(string.size()>=6?string.get(5):"");//iccid
                            String consignee = String.valueOf(string.size()>=7?string.get(6):"");//收货人

                            oInternetCard.setManufacturer(manufacturer.equals("null")?"":manufacturer);
                            if(StringUtils.isNotBlank(deliverTime) && !deliverTime.equals("null"))
                            oInternetCard.setDeliverTime(DateUtils.parseDate(deliverTime,dateFormat));
                            oInternetCard.setOrderId(orderId.equals("null")?"":orderId);
                            oInternetCard.setAgentName(agentName.equals("null")?"":agentName);
                            oInternetCard.setSnNum(snNum.equals("null")?"":snNum);
                            oInternetCard.setIccidNum(iccidNum.equals("null")?"":iccidNum);
                            oInternetCard.setConsignee(consignee.equals("null")?"":consignee);
                            jsonList = JsonUtil.objectToJson(oInternetCard);
                        }else if(importType.equals(CardImportType.C.getValue())){
                            String orderId = String.valueOf(string.size()>=1?string.get(0):"");//订单编号
                            String agentName = String.valueOf(string.size()>=2?string.get(1):"");//代理商名称
                            String snCount = String.valueOf(string.size()>=3?string.get(2):"");//数量
                            String deliverTime = String.valueOf(string.size()>=4?string.get(3):"");//发货日期
                            String beginSn = String.valueOf(string.size()>=5?string.get(4):"");//iccid开始号段
                            String endSn = String.valueOf(string.size()>=6?string.get(5):"");//iccid结束号段

                            oInternetCard.setOrderId(orderId.equals("null")?"":orderId);
                            oInternetCard.setAgentName(agentName.equals("null")?"":agentName);
                            oInternetCard.setSnCount(snCount.equals("null")?"":snCount);
                            if(StringUtils.isNotBlank(deliverTime) && !deliverTime.equals("null"))
                            oInternetCard.setDeliverTime(DateUtils.parseDate(deliverTime,dateFormat));
                            oInternetCard.setBeginSn(beginSn.equals("null")?"":beginSn);
                            oInternetCard.setEndSn(endSn.equals("null")?"":endSn);
                            jsonList = JsonUtil.objectToJson(oInternetCard);
                        }else if(importType.equals(CardImportType.D.getValue())){
                            String orderId = String.valueOf(string.size()>=1?string.get(0):"");//订单号
                            String agentName = String.valueOf(string.size()>=2?string.get(1):"");//公司名称
                            String manufacturer = String.valueOf(string.size()>=3?string.get(2):"");//厂家
                            String beginSn = String.valueOf(string.size()>=4?string.get(3):"");//机具sn起始编号
                            String endSn = String.valueOf(string.size()>=5?string.get(4):"");//机具sn终端编号
                            String snCount = String.valueOf(string.size()>=6?string.get(5):"");//数量
                            String deliverTime = String.valueOf(string.size()>=7?string.get(6):"");//发货日期

                            oInternetCard.setOrderId(orderId.equals("null")?"":orderId);
                            oInternetCard.setAgentName(agentName.equals("null")?"":agentName);
                            oInternetCard.setManufacturer(manufacturer.equals("null")?"":manufacturer);
                            oInternetCard.setBeginSn(beginSn.equals("null")?"":beginSn);
                            oInternetCard.setEndSn(endSn.equals("null")?"":endSn);
                            oInternetCard.setSnCount(snCount.equals("null")?"":snCount);
                            if(StringUtils.isNotBlank(deliverTime)  && !deliverTime.equals("null"))
                            oInternetCard.setDeliverTime(DateUtils.parseDate(deliverTime,dateFormat));
                            jsonList = JsonUtil.objectToJson(oInternetCard);
                        }else if(importType.equals(CardImportType.E.getValue())){
                            String iccidNum = String.valueOf(string.size()>=1?string.get(0):"");//ICCID
                            String internetCardStatus = String.valueOf(string.size()>=2?string.get(1):"");//物联卡状态
                            String openAccountTime = String.valueOf(string.size()>=3?string.get(2):"");//开户日期
                            String merId = String.valueOf(string.size()>=4?string.get(3):"");//商户编号
                            String latelyPayTime = String.valueOf(string.size()>=5?string.get(4):"");//最近交易日期
                            String merName = String.valueOf(string.size()>=6?string.get(5):"");//商户名称
                            String agentName = String.valueOf(string.size()>=7?string.get(6):"");//代理商名称
                            String stopReason = String.valueOf(string.size()>=8?string.get(7):"");//关停原因

                            oInternetCard.setIccidNum(iccidNum.equals("null")?"":iccidNum);
                            BigDecimal contentByMsg = InternetCardStatus.getContentByMsg(internetCardStatus);
                            if(contentByMsg==null){
                                contentByMsg = InternetCardStatus.UNKNOWN.getValue();
                            }
                            oInternetCard.setInternetCardStatus(contentByMsg);
                            oInternetCard.setLatelyPayTime(latelyPayTime.equals("null")?"":latelyPayTime);
                            if(StringUtils.isNotBlank(openAccountTime) && !openAccountTime.equals("null"))
                            oInternetCard.setOpenAccountTime(DateUtils.parseDate(openAccountTime,dateFormat));
                            oInternetCard.setMerId(merId.equals("null")?"":merId);
                            oInternetCard.setMerName(merName.equals("null")?"":merName);
                            oInternetCard.setAgentName(agentName.equals("null")?"":agentName);
                            oInternetCard.setStopReason(stopReason.equals("null")?"":stopReason);
                            jsonList = JsonUtil.objectToJson(oInternetCard);
                        }else if(importType.equals(CardImportType.F.getValue())){
                            OInternetRenewDetail oInternetRenewDetail = new OInternetRenewDetail();
                            String id = String.valueOf(string.size()>=1?string.get(0):"");//ID
                            String realityAmt = String.valueOf(string.size()>=20?string.get(19):"");//实扣金额
                            Boolean regOperationAmt = realityAmt.matches(RegExpression.Amount);
                            if(!regOperationAmt){
                                oInternetRenewDetail.setRealityAmt(null);
                            }else{
                                oInternetRenewDetail.setRealityAmt(StringUtils.isBlank(realityAmt)?null:new BigDecimal(realityAmt));
                            }
                            oInternetRenewDetail.setId(id);
                            jsonList = JsonUtil.objectToJson(oInternetRenewDetail);
                        }
                        OInternetCardImport oInternetCardImport = new OInternetCardImport();
                        oInternetCardImport.setId(idService.genId(TabId.O_INTERNET_CARD_IMPORT));
                        oInternetCardImport.setImportMsg(jsonList);
                        oInternetCardImport.setImportStatus(OInternetCardImportStatus.UNTREATED.getValue());
                        oInternetCardImport.setImportType(importType);
                        oInternetCardImport.setBatchNum(batchNo);
                        Date date = new Date();
                        oInternetCardImport.setcTime(date);
                        oInternetCardImport.setuTime(date);
                        oInternetCardImport.setcUser(userId);
                        oInternetCardImport.setuUser(userId);
                        oInternetCardImport.setStatus(Status.STATUS_1.status);
                        oInternetCardImport.setVersion(BigDecimal.ONE);
                        internetCardImportMapper.insert(oInternetCardImport);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 处理导入表数据
     * @param batchNo
     */
    public void analysisImport(String batchNo){

        String retIdentifier = "";
        try {
            retIdentifier = redisService.lockWithTimeout(RedisCachKey.INSERT_SYS_KEY.code+":"+batchNo,ACQUIRE_TIME_OUT,TIME_OUT);
            if(StringUtils.isBlank(retIdentifier)){
                log.info("处理导入表数据该批次处理中,batchNo:{}",batchNo);
            }
            log.info("analysisImport处理导入表处理开始,batchNo:{}",batchNo);
            OInternetCardImportExample oInternetCardImportExample = new OInternetCardImportExample();
            OInternetCardImportExample.Criteria criteria = oInternetCardImportExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andBatchNumEqualTo(batchNo);
            criteria.andImportStatusEqualTo(OInternetCardImportStatus.UNTREATED.getValue());
            oInternetCardImportExample.setPage(new Page(0,10000));
            List<OInternetCardImport> oInternetCardImports = internetCardImportMapper.selectByExample(oInternetCardImportExample);
            for (OInternetCardImport oInternetCardImport : oInternetCardImports) {
                try {
                    log.info("analysisImport处理导入表数据,oInternetCardImport:{}",oInternetCardImport.toString());
                    String importType = oInternetCardImport.getImportType();

                    if(importType.equals(CardImportType.A.getValue()) || importType.equals(CardImportType.B.getValue()) || importType.equals(CardImportType.E.getValue())){
                        OInternetCard internetCard = JsonUtil.jsonToPojo(oInternetCardImport.getImportMsg(), OInternetCard.class);
                        disposeInternetCard(oInternetCardImport,internetCard);
                    }else if(importType.equals(CardImportType.C.getValue())){
                        OInternetCard internetCard = JsonUtil.jsonToPojo(oInternetCardImport.getImportMsg(), OInternetCard.class);
                        if(StringUtils.isBlank(internetCard.getBeginSn()) || StringUtils.isBlank(internetCard.getSnCount())){
                            throw new MessageException("缺少iccid开始号段或总数量");
                        }
                        List<String> iccidList = logisticsService.idList(internetCard.getBeginSn(), StringUtils.isBlank(internetCard.getEndSn())?internetCard.getBeginSn():internetCard.getEndSn());
                        if(iccidList.size()!=Integer.parseInt(RegexUtil.rvZeroAndDot(internetCard.getSnCount()))){
                            throw new MessageException("iccid号段与数量不匹配");
                        }
                        for (String iccId : iccidList) {
                            internetCard.setIccidNum(iccId);
                            disposeInternetCard(oInternetCardImport,internetCard);
                        }
                    }else if(importType.equals(CardImportType.D.getValue())){
                        OInternetCard internetCard = JsonUtil.jsonToPojo(oInternetCardImport.getImportMsg(), OInternetCard.class);
                        if(StringUtils.isBlank(internetCard.getBeginSn()) || StringUtils.isBlank(internetCard.getSnCount())){
                            throw new MessageException("缺少SN开始号段或总数量");
                        }
                        List<String> snList = logisticsService.idList(internetCard.getBeginSn(), StringUtils.isBlank(internetCard.getEndSn())?internetCard.getBeginSn():internetCard.getEndSn());
                        if(snList.size()!=Integer.parseInt(RegexUtil.rvZeroAndDot(internetCard.getSnCount()))){
                            throw new MessageException("SN号段与数量不匹配");
                        }
                        internetCardService.disposeSn(snList,internetCard,oInternetCardImport);
                    }else if(importType.equals(CardImportType.F.getValue())){
                        OInternetRenewDetail internetRenewDetail = JsonUtil.jsonToPojo(oInternetCardImport.getImportMsg(), OInternetRenewDetail.class);
                        if(null==internetRenewDetail.getRealityAmt()){
                            throw new MessageException("实际付款金额为空或格式不正确");
                        }
                        if(null==internetRenewDetail.getId()){
                            throw new MessageException("明细编号为空");
                        }
                        Boolean regOperationAmt = RegExpression.regAmount(internetRenewDetail.getRealityAmt());
                        if(!regOperationAmt){
                            throw new MessageException("操作金额不正确,保留小数点后两位");
                        }
                        OInternetRenewDetail qInternetRenewDetail = internetRenewDetailMapper.selectByPrimaryKey(internetRenewDetail.getId());
                        if(null==qInternetRenewDetail){
                            throw new MessageException("明细不存在");
                        }
                        if(!qInternetRenewDetail.getRenewWay().equals(InternetRenewWay.FRDK.getValue()) && !qInternetRenewDetail.getRenewWay().equals(InternetRenewWay.FRDKGC.getValue())){
                            throw new MessageException("只能导入分润抵扣的数据");
                        }
                        BigDecimal realityAmt = internetRenewDetail.getRealityAmt().add(qInternetRenewDetail.getRealityAmt());
                        if(realityAmt.compareTo(qInternetRenewDetail.getOughtAmt())==1){
                            throw new MessageException("实扣金额不能大于应扣金额");
                        }
                        //等于更新状态已续费
                        if(realityAmt.compareTo(qInternetRenewDetail.getOughtAmt())==0){
                            qInternetRenewDetail.setRenewStatus(InternetRenewStatus.YXF.getValue());
                        }
                        qInternetRenewDetail.setRealityAmt(realityAmt);
                        int i = internetRenewDetailMapper.updateByPrimaryKeySelective(qInternetRenewDetail);
                        if(i!=1){
                            throw new MessageException("更新续费明细失败");
                        }
                        oInternetCardImport.setImportStatus(OInternetCardImportStatus.SUCCESS.getValue());
                        updateInternetCardImport(oInternetCardImport);
                    }
                } catch (MessageException e) {
                    log.info("analysisImport处理导入表数据,MessageException:{}",e.getLocalizedMessage());
                    e.printStackTrace();
                    oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
                    oInternetCardImport.setErrorMsg(e.getMsg());
                    //更新导入记录
                    try {
                        updateInternetCardImport(oInternetCardImport);
                    } catch (MessageException e1) {
                    }
                } catch (Exception e) {
                    log.info("analysisImport处理导入表数据,Exception:{}",e.getLocalizedMessage());
                    e.printStackTrace();
                    oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
                    oInternetCardImport.setErrorMsg(e.getLocalizedMessage());
                    //更新导入记录
                    try {
                        updateInternetCardImport(oInternetCardImport);
                    } catch (MessageException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } finally {
            if(StringUtils.isNotBlank(retIdentifier)){
                redisService.releaseLock(RedisCachKey.INSERT_SYS_KEY.code+":"+batchNo, retIdentifier);
            }
        }
    }

    /**
     * 处理业务逻辑
     * @param oInternetCardImport
     * @param internetCard
     * @throws Exception
     */
    public void disposeInternetCard(OInternetCardImport oInternetCardImport, OInternetCard internetCard)throws Exception{
        if(StringUtils.isBlank(internetCard.getIccidNum())){
            oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
            oInternetCardImport.setErrorMsg("缺少iccid");
            //更新导入记录
            updateInternetCardImport(oInternetCardImport);
            return;
        }
        OInternetCard oInternetCard = internetCardMapper.selectByPrimaryKey(internetCard.getIccidNum());
        internetCard.setBatchNum(oInternetCardImport.getBatchNum());
        internetCard.setCardImportId(oInternetCardImport.getId());
        if(internetCard.getOpenAccountTime()!=null){
            Date date = DateUtil.aYearAgoDate(internetCard.getOpenAccountTime());
            internetCard.setExpireTime(date);
            internetCard.setRenew(BigDecimal.ZERO);
            internetCard.setStop(BigDecimal.ZERO);
        }
        if(StringUtils.isNotBlank(internetCard.getAgentName())){
            Agent agent = agentService.getAgentByName(internetCard.getAgentName());
            if(agent!=null){
                internetCard.setAgentId(agent.getId());
            }
        }
        if(StringUtils.isNotBlank(internetCard.getManufacturer())){
            Dict dict = dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(),internetCard.getManufacturer());
            if(null==dict){
                oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
                oInternetCardImport.setErrorMsg(internetCard.getManufacturer()+"厂商不存在");
                //更新导入记录
                updateInternetCardImport(oInternetCardImport);
                return;
            }
            internetCard.setManufacturer(dict.getdItemvalue());
        }
        if(oInternetCard==null){
            internetCard.setcUser(oInternetCardImport.getcUser());
            internetCard.setRenew(Status.STATUS_0.status); //否
            insertInternetCard(internetCard);
        }else{
            updateInternetCard(internetCard);
        }
        oInternetCardImport.setImportStatus(OInternetCardImportStatus.SUCCESS.getValue());
        updateInternetCardImport(oInternetCardImport);
    }

    /**
     * 退货转发sn 处理
     * @param snList
     * @param internetCard
     * @param oInternetCardImport
     * @throws MessageException
     */
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    @Override
    public void disposeSn(List<String> snList, OInternetCard internetCard, OInternetCardImport oInternetCardImport)throws MessageException{
        for (String snNum : snList) {
            OInternetCardExample oInternetCardExample = new OInternetCardExample();
            OInternetCardExample.Criteria criteria = oInternetCardExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andSnNumEqualTo(snNum);
            List<OInternetCard> oInternetCards = internetCardMapper.selectByExample(oInternetCardExample);
            if(oInternetCards.size()==0){
                throw new MessageException("sn:"+snNum+"不存在");
            }
            if(oInternetCards.size()!=1){
                throw new MessageException("sn:"+snNum+"不唯一");
            }
            OInternetCard oInternetCard = oInternetCards.get(0);
            oInternetCard.setOrderId(internetCard.getOrderId());
            oInternetCard.setAgentName(internetCard.getAgentName());
            oInternetCard.setManufacturer(internetCard.getManufacturer());
            oInternetCard.setDeliverTime(internetCard.getDeliverTime());
            if(StringUtils.isNotBlank(internetCard.getManufacturer())){
                Dict dict = dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(),internetCard.getManufacturer());
                if(null==dict){
                    throw new MessageException(snNum+"厂商不存在");
                }
                oInternetCard.setManufacturer(dict.getdItemvalue());
            }
            updateInternetCard(oInternetCard);
            oInternetCardImport.setImportStatus(OInternetCardImportStatus.SUCCESS.getValue());
            updateInternetCardImport(oInternetCardImport);
        }
    }



    public void insertInternetCard(OInternetCard internetCard)throws Exception{
        internetCard.setuUser(internetCard.getcUser());
        Date date = new Date();
        internetCard.setcTime(date);
        internetCard.setuTime(date);
        internetCard.setStatus(Status.STATUS_1.status);
        internetCard.setVersion(BigDecimal.ONE);
        internetCard.setStop(Status.STATUS_0.status);
        internetCard.setRenewStatus(InternetRenewStatus.WXF.getValue());
        internetCardMapper.insert(internetCard);
    }

    public void updateInternetCard(OInternetCard internetCard)throws MessageException{
        if(StringUtils.isBlank(internetCard.getIccidNum())){
            throw new MessageException("iccid为空");
        }
        internetCard.setuTime(new Date());
        int i = internetCardMapper.updateByPrimaryKeySelectiveNotNull(internetCard);
        if(i!=1){
            throw new MessageException("更新失败");
        }
    }

    public void updateInternetCardImport(OInternetCardImport internetCardImport)throws MessageException{
        internetCardImport.setuTime(new Date());
        internetCardImportMapper.updateByPrimaryKeySelective(internetCardImport);
    }


    /**
     * 导出错误数据
     * @param internetCardImport
     * @return
     * @throws MessageException
     */
    @Override
    public List<OInternetCardImport>  exportErrorExcel(OInternetCardImport internetCardImport){
        OInternetCardImportExample oInternetCardImportExample = new OInternetCardImportExample();
        OInternetCardImportExample.Criteria criteria = oInternetCardImportExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andBatchNumEqualTo(internetCardImport.getBatchNum());
        criteria.andImportTypeEqualTo(internetCardImport.getImportType());
        criteria.andImportStatusEqualTo(OInternetCardImportStatus.FAIL.code);
        List<OInternetCardImport> oInternetCardImports = internetCardImportMapper.selectByExample(oInternetCardImportExample);
        return oInternetCardImports;
    }


    /**
     * 定时任务，
     * 1. 检测是否续费为否，状态为正常的，当月的，更新“是否需续费”为是
     * 2. 到期日减去5天  还未续费的 更新“是否需关停”为是
     * 3. 预轧差金额等于已轧差金额 清算状态等于2（轧差完毕） 更新续费明细表续费状态等于YXF（已续费）
     * 4. 处理未处理的导入记录
     */
    @Override
    public void taskDisposeInternetCard(){
        log.info("taskDisposeInternetCard定时任务,开始执行");
        String retIdentifier = "";
        try {
            retIdentifier = redisService.lockWithTimeout(RedisCachKey.TASK_DISPOSEIN_TERNET_CARD.code, ACQUIRE_TIME_OUT, TIME_OUT);
            if (StringUtils.isBlank(retIdentifier)) {
                log.info("物联网卡定时任务处理中");
            }
           // 1. 检测是否续费为否，状态为正常的，下个月的，更新“是否需续费”为是
            Map<String,Object> reqMap = new HashMap<>();
            reqMap.put("renew",Status.STATUS_0.status);//否
            reqMap.put("newRenew",Status.STATUS_1.status);
            reqMap.put("expireTime",DateUtil.getPerFirstDayOfMonth());
            int i = internetCardMapper.selectInternetCardExpireCount(reqMap);
            if(i>0){
                int updateCount = internetCardMapper.updateInternetCardExpire(reqMap);
                log.info("taskDisposeInternetCard：1检测是否续费,本次更次了数据条数:{}",updateCount);
            }else{
                log.info("taskDisposeInternetCard：1检测是否续费,暂无更新数据:{}",i);
            }
            //2. 到期日减去5天  还未续费的 更新“是否需关停”为是
            Map<String,Object> reqRenewMap = new HashMap<>();
            reqRenewMap.put("renewStatus",InternetRenewStatus.WXF.getValue());
            reqRenewMap.put("expireTime",DateUtil.getDateAfter(new Date(),-5));
            reqRenewMap.put("nowTime",DateUtil.format(new Date(),"yyyy-MM-dd"));
            reqRenewMap.put("stop",Status.STATUS_1.status);
            reqRenewMap.put("oldStop",Status.STATUS_0.status);
            int j = internetCardMapper.selectInternetCardStopCount(reqRenewMap);
            if(j>0){
                int updateCount = internetCardMapper.updateInternetCardStop(reqRenewMap);
                log.info("taskDisposeInternetCard：2到期日减去5天还未续费的,本次更次了数据条数:{}",updateCount);
            }else{
                log.info("taskDisposeInternetCard：2到期日减去5天还未续费的,暂无更新数据:{}",i);
            }
            //3. 预轧差金额等于已轧差金额 清算状态等于2（轧差完毕） 更新续费明细表续费状态等于YXF（已续费）
            Map<String,Object> offsetreqMap = new HashMap<>();
            offsetreqMap.put("cleanStatus",InternetCleanStatus.TWO.getValue());
            offsetreqMap.put("processDate",DateUtil.format(new Date(), DateUtil.DATE_FORMAT_3));//处理时间为今天的
            List<InternetRenewOffset> internetRenewOffsets = internetRenewOffsetMapper.selectOffsetFinish(offsetreqMap);
            if(internetRenewOffsets!=null && internetRenewOffsets.size()!=0){
                for (InternetRenewOffset internetRenewOffset : internetRenewOffsets) {
                    OInternetRenewDetail oInternetRenewDetail1 = internetRenewDetailMapper.selectByPrimaryKey(internetRenewOffset.getRenewDetailId());
                    if(oInternetRenewDetail1.getRenewStatus().equals(InternetRenewStatus.YXF.getValue())){
                        log.info("taskDisposeInternetCard:3.已是续费状态,退出");
                        continue;
                    }
                    OInternetRenewDetail oInternetRenewDetail = new OInternetRenewDetail();
                    oInternetRenewDetail.setId(internetRenewOffset.getRenewDetailId());
                    oInternetRenewDetail.setRenewStatus(InternetRenewStatus.YXF.getValue());
                    internetRenewDetailMapper.updateByPrimaryKeySelective(oInternetRenewDetail);
                }
            }else{
                log.info("taskDisposeInternetCard:3.没有轧差数据,退出");
            }
            //4.处理未处理的导入记录
            Map map = new HashMap<>();
            map.put("importStatus",OInternetCardImportStatus.UNTREATED.getValue());
            Page page = new Page(0, 10);
            map.put("page",page);
            List<String> batchNums = internetCardImportMapper.selectBatchNum(map);
            if(batchNums.size()==0){
                log.info("taskDisposeInternetCard暂无未处理,退出");
                return;
            }
            for (String batchNum : batchNums) {
                if(StringUtils.isBlank(batchNum)){
                    log.info("taskDisposeInternetCard处理未处理的导入记录，批次号未空");
                    continue;
                }
                log.info("taskDisposeInternetCard处理未处理的导入记录，批次号:{}",batchNum);
                analysisImport(batchNum);
            }

        } finally {
            if(StringUtils.isNotBlank(retIdentifier)){
                redisService.releaseLock(RedisCachKey.TASK_DISPOSEIN_TERNET_CARD.code, retIdentifier);
            }
        }
    }

    /**
     * 定时任务
     * 更新已修改的商户信息（是续费，正常）
     */
    @Override
    public void taskUpdateMech(){
        try {
            //更新已修改的商户信息（是续费，正常）
            log.info("更新已修改的商户信息开始");
            long t1 = System.currentTimeMillis();
            Map<String,Object> reqMap = new HashMap<>();
            reqMap.put("renew",Status.STATUS_0.status);//否
            reqMap.put("newRenew",Status.STATUS_1.status);//是续费
//            reqMap.put("internetCardStatus",InternetCardStatus.NORMAL.getValue());
            reqMap.put("expireTime",DateUtil.getPerFirstDayOfMonth());
            List<OInternetCard> oInternetCards = internetCardMapper.selectInternetCardRenew(reqMap);
            for (OInternetCard oInternetCard : oInternetCards) {
                OInternetCardMerch oInternetCardMerch = internetCardMerchMapper.selectChnTermposi(BigDataEncode.encode(oInternetCard.getIccidNum()));
                if(null!=oInternetCardMerch){
                    //之前未获取当商户信息的直接更新
                    if(StringUtils.isBlank(oInternetCard.getMerId()) || StringUtils.isBlank(oInternetCard.getMerName())){
                        oInternetCard.setMerId(oInternetCardMerch.getChnMerchId());
                        oInternetCard.setMerName(oInternetCardMerch.getMerchName());
                        int i = internetCardMapper.updateByPrimaryKeySelectiveNotNull(oInternetCard);
                        if(i!=1){
                            log.error("1定时任务更新商户信息失败:IccidNum:{},商户编号:{},商户名称:{}",oInternetCard.getIccidNum(),oInternetCardMerch.getChnMerchId(),oInternetCardMerch.getMerchName());
                        }
                        continue;
                    }
                    //只要有一个不相等就执行更新操作
                    if(!oInternetCardMerch.getChnMerchId().equals(oInternetCard.getMerId()) || !oInternetCardMerch.getMerchName().equals(oInternetCard.getMerName())){
                        oInternetCard.setMerId(oInternetCardMerch.getChnMerchId());
                        oInternetCard.setMerName(oInternetCardMerch.getMerchName());
                        int i = internetCardMapper.updateByPrimaryKeySelectiveNotNull(oInternetCard);
                        if(i!=1){
                            log.error("2定时任务更新商户信息失败:IccidNum:{},商户编号:{},商户名称:{}",oInternetCard.getIccidNum(),oInternetCardMerch.getChnMerchId(),oInternetCardMerch.getMerchName());
                        }
                        continue;
                    }
                }
            }
            long t2 = System.currentTimeMillis();
            log.info("更新已修改的商户信息，处理时间:{} ms", (t2 - t1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 1. 查询为空的商户信息更新
     */
    @Override
    public void taskUpdateMechIsNull(){
        threadPoolTaskExecutor.execute(new Runnable() {
           @Override
           public void run() {
               List<OInternetCard> oInternetCards = fetchDataMechIsNull();
               for (OInternetCard oInternetCard : oInternetCards) {
                   processDataUpdateMechIsNull(oInternetCard);
               }
           }
        });
    }

    /**
     * 定时任务 ： 更新商户为空的数据 1.查询数据
     * @return
     */
    @Override
    public List<OInternetCard> fetchDataMechIsNull(){
        log.info("查询为空的商户信息开始");
        OInternetCardExample oInternetCardExample = new OInternetCardExample();
        OInternetCardExample.Criteria criteria = oInternetCardExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andMerIdIsNull();
        criteria.andMerNameIsNull();
        oInternetCardExample.setPage(new Page(0,100));
        List<OInternetCard> internetCards = internetCardMapper.selectByExample(oInternetCardExample);
        log.info("查询为空的商户信息数量为:{}",internetCards.size());
        return internetCards;
    }

    /**
     * 定时任务 ：更新商户为空的数据 2.执行更新数据
     * @param internetCard
     */
    @Override
    public void processDataUpdateMechIsNull(OInternetCard internetCard){
        try {
            log.info("为空定时任务更新商户信息，执行更新数据开始");
            OInternetCardMerch oInternetCardMerch = internetCardMerchMapper.selectChnTermposi(BigDataEncode.encode(internetCard.getIccidNum()));
            if(null!=oInternetCardMerch){
                internetCard.setMerId(oInternetCardMerch.getChnMerchId());
                internetCard.setMerName(oInternetCardMerch.getMerchName());
            }else{
                internetCard.setMerId("无");
                internetCard.setMerName("无");
            }
            int i = internetCardMapper.updateByPrimaryKeySelectiveNotNull(internetCard);
            if(i!=1){
                log.error("为空定时任务更新商户信息失败:IccidNum:{},商户编号:{},商户名称:{}",internetCard.getIccidNum(),oInternetCardMerch.getChnMerchId(),oInternetCardMerch.getMerchName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 订单发货 如果是流量卡插入信息表
     * @param logisticsDetailList
     * @throws Exception
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void orderInsertInternetCard(List<OLogisticsDetail> logisticsDetailList,String manuFacturer)throws Exception{

        String batchNo = IDUtils.getBatchNo();
        for (OLogisticsDetail oLogisticsDetail : logisticsDetailList) {
            OInternetCard oInternetCard = new OInternetCard();
            oInternetCard.setIccidNum(oLogisticsDetail.getSnNum());
            oInternetCard.setBatchNum(batchNo);
            oInternetCard.setOrderId(oLogisticsDetail.getOrderId());
            oInternetCard.setDeliverTime(oLogisticsDetail.getcTime());
            oInternetCard.setAgentId(oLogisticsDetail.getAgentId());
            Agent agent = agentService.getAgentById(oLogisticsDetail.getAgentId());
            if(agent!=null)
            oInternetCard.setAgentName(agent.getAgName());
            oInternetCard.setManufacturer(manuFacturer);
            oInternetCard.setRenewStatus(InternetRenewStatus.WXF.getValue());
            oInternetCard.setStop(Status.STATUS_0.status);
            oInternetCard.setRenew(Status.STATUS_0.status);
            oInternetCard.setInternetCardStatus(InternetCardStatus.NOACTIVATE.code);
            Date date = new Date();
            oInternetCard.setcTime(date);
            oInternetCard.setuTime(date);
            oInternetCard.setuUser(oInternetCard.getcUser());
            oInternetCard.setStatus(Status.STATUS_1.status);
            oInternetCard.setVersion(BigDecimal.ONE);
            internetCardMapper.insert(oInternetCard);
        }
    }


    /**
     * 查询要迁移的数据
     * @return
     */
    @Override
    public List<OInternetCardImport> queryMigrationHistory(){
        OInternetCardImportExample oInternetCardImportExample = new OInternetCardImportExample();
        OInternetCardImportExample.Criteria criteria = oInternetCardImportExample.createCriteria();
        Date dateAfter = DateUtil.getDateAfterReturnDate(new Date(), -5);
        criteria.andCTimeLessThanOrEqualTo(dateAfter);
        oInternetCardImportExample.setPage(new Page(0,100));
        return internetCardImportMapper.selectByExample(oInternetCardImportExample);
    }


    /**
     * 导入迁移到历史表
     * @param oInternetCardImport
     * @throws MessageException
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void migrationHistory(OInternetCardImport oInternetCardImport)throws MessageException{
        InternetCardImportHistory internetCardImportHistory = new InternetCardImportHistory();
        BeanUtils.copyProperties(oInternetCardImport,internetCardImportHistory);
        internetCardImportHistoryMapper.insert(internetCardImportHistory);
        OInternetCardImportExample internetCardImportExample = new OInternetCardImportExample();
        OInternetCardImportExample.Criteria criteria = internetCardImportExample.createCriteria();
        criteria.andIdEqualTo(oInternetCardImport.getId());
        int i = internetCardImportMapper.deleteByExample(internetCardImportExample);
        if(i!=1){
            throw new MessageException("删除失败");
        }
    }
}
