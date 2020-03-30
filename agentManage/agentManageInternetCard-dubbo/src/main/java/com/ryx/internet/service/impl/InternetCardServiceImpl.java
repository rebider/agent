package com.ryx.internet.service.impl;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.BeanUtils;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.PlatFormService;
import com.ryx.credit.service.order.OLogisticsService;
import com.ryx.internet.dao.*;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.internet.pojo.*;
import com.ryx.internet.service.InternetCardService;
import com.ryx.internet.service.OInternetRenewService;
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
    private OInternetCardPostponeMapper internetCardPostponeMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private InternetLogoutDetailMapper internetLogoutDetailMapper;
    @Autowired
    private OInternetRenewService oInternetRenewService;

    @Override
    public PageInfo internetCardList(OInternetCard internetCard, Page page,String agentId,Long userId){

        OInternetCardExample oInternetCardExample = new OInternetCardExample();
        oInternetCardExample= queryParam(internetCard, oInternetCardExample,agentId,userId);
        oInternetCardExample.setPage(page);
        List<OInternetCard> oInternetCards = internetCardMapper.internetCardList(oInternetCardExample);
        for (OInternetCard oInternetCard : oInternetCards) {
            if(StringUtils.isNotBlank(oInternetCard.getIccidNum()))
            oInternetCard.setIccidNumId(oInternetCard.getIccidNum());
            if(StringUtils.isNotBlank(oInternetCard.getIssuer()))
            oInternetCard.setIssuer(Issuerstatus.getContentByValue(oInternetCard.getIssuer()));
            if(null!=oInternetCard.getInternetCardStatus())
            oInternetCard.setInternetCardStatusName(InternetCardStatus.getContentByValue(oInternetCard.getInternetCardStatus()));
            if(StringUtils.isNotBlank(oInternetCard.getRenewStatus()))
            oInternetCard.setRenewStatus(InternetRenewStatus.getContentByValue(oInternetCard.getRenewStatus()));
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(oInternetCards);
        pageInfo.setTotal(internetCardMapper.internetCardCount(oInternetCardExample));
        return pageInfo;
    }

    @Override
    public List<OInternetCard> queryInternetCardList(OInternetCard internetCard, Page page,String agentId,Long userId){
        OInternetCardExample oInternetCardExample = new OInternetCardExample();
        oInternetCardExample = queryParam(internetCard, oInternetCardExample,agentId,userId);
        oInternetCardExample.setPage(page);
        List<OInternetCard> oInternetCards = internetCardMapper.internetCardList(oInternetCardExample);
        return oInternetCards;
    }

    @Override
    public Integer queryInternetCardCount(OInternetCard internetCard,String agentId,Long userId){
        OInternetCardExample oInternetCardExample = new OInternetCardExample();
        oInternetCardExample = queryParam(internetCard, oInternetCardExample,agentId,userId);
        Integer count = Integer.valueOf(internetCardMapper.internetCardCount(oInternetCardExample));
        return count;
    }

    /**
     * 查询和导出的条件
     * @param internetCard
     * @param oInternetCardExample
     * @return
     */
    private OInternetCardExample queryParam(OInternetCard internetCard, OInternetCardExample oInternetCardExample,String agentId,Long userId){

        OInternetCardExample.Criteria criteria = oInternetCardExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        if(StringUtils.isNotBlank(internetCard.getBatchNum())){
            criteria.andBatchNumEqualTo(internetCard.getBatchNum());
        }
        if(StringUtils.isNotBlank(internetCard.getIccidNum())){
            boolean contains = internetCard.getIccidNum().contains(",");
            if(contains){
                String[] iccidNumStr = internetCard.getIccidNum().split(",");
                List<String> iccidNumList = new ArrayList<>();
                for (String iccidNum : iccidNumStr) {
                    iccidNumList.add(iccidNum);
                }
                criteria.andIccidNumIn(iccidNumList);
            }else{
                criteria.andIccidNumLike(internetCard.getIccidNum()+"%");
            }
        }else if(StringUtils.isNotBlank(internetCard.getIccidNumBegin()) && StringUtils.isNotBlank(internetCard.getIccidNumEnd())){
            criteria.andIccidNumBetween(internetCard.getIccidNumBegin(),internetCard.getIccidNumEnd());
        }
        if(StringUtils.isNotBlank(internetCard.getSnNum())){
            criteria.andSnNumLike(internetCard.getSnNum()+"%");
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

        if(StringUtils.isNotBlank(internetCard.getOrderId())){
            criteria.andOrderIdEqualTo(internetCard.getOrderId());
        }
        if(null!=internetCard.getStop()){
            criteria.andStopEqualTo(internetCard.getStop());
        }
        if(StringUtils.isNotBlank(internetCard.getRenewStatus())){
            criteria.andRenewStatusEqualTo(internetCard.getRenewStatus());
        }
        if(StringUtils.isNotBlank(internetCard.getInternetCardNum())){
            criteria.andInternetCardNumLike(internetCard.getInternetCardNum()+"%");
        }
        if(null!=internetCard.getInternetCardStatus()){
            criteria.andInternetCardStatusEqualTo(internetCard.getInternetCardStatus());
        }
        if(StringUtils.isNotBlank(internetCard.getBusNum())){
            criteria.andBusNumEqualTo(internetCard.getBusNum());
        }
        if(StringUtils.isNotBlank(internetCard.getOpenAccountTimeBeginStr())){
            Date format = DateUtil.format(internetCard.getOpenAccountTimeBeginStr(), DateUtil.DATE_FORMAT_yyyy_MM_dd);
            criteria.andOpenAccountTimeGreaterThanOrEqualTo(format);
        }
        if(StringUtils.isNotBlank(internetCard.getOpenAccountTimeEndStr())){
            Date format = DateUtil.format(internetCard.getOpenAccountTimeEndStr(), DateUtil.DATE_FORMAT_yyyy_MM_dd);
            criteria.andOpenAccountTimeLessThanOrEqualTo(format);
        }
        if(StringUtils.isNotBlank(internetCard.getExpireTimeBeginStr()) && StringUtils.isNotBlank(internetCard.getExpireTimeEndStr())){
            Date begin = DateUtil.format(internetCard.getExpireTimeBeginStr(), DateUtil.DATE_FORMAT_yyyy_MM_dd);
            Date end = DateUtil.format(internetCard.getExpireTimeEndStr(), DateUtil.DATE_FORMAT_yyyy_MM_dd);
            criteria.andExpireTimeBetween(begin,end);
        }
//        else{
//            Date begin = DateUtil.format(DateUtil.getFirstDayOfMonth(DateUtil.getYearMonthOfMonthType(0, "Year"),DateUtil.getYearMonthOfMonthType(0, "Month")),DateUtil.DATE_FORMAT_yyyy_MM_dd);
//            Date end = DateUtil.format(DateUtil.getLastDayOfMonth(DateUtil.getYearMonthOfMonthType(3, "Year"),DateUtil.getYearMonthOfMonthType(3, "Month")), DateUtil.DATE_FORMAT_yyyy_MM_dd);
//            criteria.andExpireTimeBetween(begin,end);
//        }
        if(StringUtils.isNotBlank(internetCard.getMerId())){
            criteria.andMerIdLike(internetCard.getMerId()+"%");
        }
        if(StringUtils.isNotBlank(internetCard.getMerName())){
            criteria.andMerNameEqualTo(internetCard.getMerName());
        }

        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
        if(orgCodeRes==null && orgCodeRes.size()!=1){
            return null;
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        Map<String,Object> reqMap = new HashMap<>();
        //省区大区查看自己的代理商 部门权限
        if(StringUtils.isNotBlank(organizationCode) && (organizationCode.contains("region") || organizationCode.contains("beijing"))) {
            reqMap.put("orgCode", organizationCode);
        }
        //内部人员根据名称查询指定流量卡
        List<String> agentNameList = dictOptionsService.getAgentNameList(userId);
        if(agentNameList.size()!=0){
            reqMap.put("agentNameList", agentNameList);
        }else if(StringUtils.isNotBlank(internetCard.getAgentName())){
            criteria.andAgentNameLike("%"+internetCard.getAgentName()+"%");
        }
        oInternetCardExample.setReqMap(reqMap);
//        oInternetCardExample.setOrderByClause("expire_time asc ");
        return oInternetCardExample;
    }


    @Override
    public PageInfo internetCardImportList(OInternetCardImport internetCardImport, Page page){
        OInternetCardImportExample internetCardImportExample = commonImport(internetCardImport);
        internetCardImportExample.setPage(page);
        internetCardImportExample.setOrderByClause(" c_time desc ");
        List<OInternetCardImport> oInternetCards = internetCardImportMapper.selectByExample(internetCardImportExample);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(oInternetCards);
        pageInfo.setTotal((int)internetCardImportMapper.countByExample(internetCardImportExample));
        return pageInfo;
    }

    /**
     * 导出查询个数
     * @param internetCardImport
     * @return
     */
    @Override
    public int internetCardImportCount(OInternetCardImport internetCardImport){
        OInternetCardImportExample internetCardImportExample = commonImport(internetCardImport);
        return (int)internetCardImportMapper.countByExample(internetCardImportExample);
    }

    /**
     * 查询和导出
     * @param internetCardImport
     * @return
     */
    private OInternetCardImportExample commonImport(OInternetCardImport internetCardImport){
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
        return internetCardImportExample;
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
                            String internetCardNum = String.valueOf(string.size()>=2?string.get(1):"");//物联卡号
                            String iccidNum = String.valueOf(string.size()>=3?string.get(2):"");//ICCID
                            String openAccountTime = String.valueOf(string.size()>=4?string.get(3):"");//开户日期
                            String busNum = String.valueOf(string.size()>=5?string.get(4):"");//平台编号
                            String busPlatform = String.valueOf(string.size()>=6?string.get(5):"");//平台

                            oInternetCard.setIccidNum(iccidNum.equals("null")?"":iccidNum);
                            oInternetCard.setIssuer(issuer.equals("null")?"":issuer);
                            oInternetCard.setInternetCardNum(internetCardNum.equals("null")?"":internetCardNum);
                            oInternetCard.setBusNum(busNum.equals("null")?"":busNum);
                            oInternetCard.setBusPlatform(busPlatform.equals("null")?"":busPlatform);
//                            if(StringUtils.isNotBlank(openAccountTime.equals("null")?"":openAccountTime))
//                            oInternetCard.setOpenAccountTime(DateUtils.parseDate(openAccountTime,dateFormat)); // 可空，开户日期不更新
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
                            if(StringUtils.isNotBlank(deliverTime) && !deliverTime.equals("null")) {
                                String str = null;
                                if (deliverTime.contains("/")) {
                                    str = deliverTime.substring(0, deliverTime.indexOf("/"));
                                } else if (deliverTime.contains("-")){
                                    str = deliverTime.substring(0, deliverTime.indexOf("-"));
                                }
                                if(str.length() != 4)
                                    oInternetCard.setDeliverTime(null);
                                else
                                    oInternetCard.setDeliverTime(DateUtils.parseDate(deliverTime, dateFormat));// DateUtils.stringToDate(deliverTime)
                            }
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
//                            if(StringUtils.isNotBlank(openAccountTime) && !openAccountTime.equals("null")) // 可空 开户日期不更新
//                            oInternetCard.setOpenAccountTime(DateUtils.parseDate(openAccountTime,dateFormat));
                            oInternetCard.setMerId(merId.equals("null")?"":merId);
                            oInternetCard.setMerName(merName.equals("null")?"":merName);
                            oInternetCard.setAgentName(agentName.equals("null")?"":agentName);
                            oInternetCard.setStopReason(stopReason.equals("null")?"":stopReason);
                            jsonList = JsonUtil.objectToJson(oInternetCard);
                        }else if(importType.equals(CardImportType.F.getValue())){
//                            OInternetRenewDetail oInternetRenewDetail = new OInternetRenewDetail();
//                            String id = String.valueOf(string.size()>=1?string.get(0):"");//ID
//                            String theRealityAmt = String.valueOf(string.size()>=20?string.get(19):"");//实扣金额
//                            Boolean regOperationAmt = theRealityAmt.matches(RegExpression.Amount);
//                            if(!regOperationAmt){
//                                oInternetRenewDetail.setTheRealityAmt(null);
//                            }else{
//                                oInternetRenewDetail.setTheRealityAmt(StringUtils.isBlank(theRealityAmt)?null:new BigDecimal(theRealityAmt));
//                            }
//                            oInternetRenewDetail.setId(id);
//                            oInternetRenewDetail.setRenewId(String.valueOf(string.size()>=2?string.get(1):""));
//                            oInternetRenewDetail.setIccidNum(String.valueOf(string.size()>=3?string.get(2):""));
//                            oInternetRenewDetail.setOrderId(String.valueOf(string.size()>=4?string.get(3):""));
//                            oInternetRenewDetail.setSnNum(String.valueOf(string.size()>=5?string.get(4):""));
//                            oInternetRenewDetail.setAgentId(String.valueOf(string.size()>=6?string.get(5):""));
//                            oInternetRenewDetail.setAgentName(String.valueOf(string.size()>=7?string.get(6):""));
//                            oInternetRenewDetail.setMerName(String.valueOf(string.size()>=8?string.get(7):""));
//                            oInternetRenewDetail.setMerId(String.valueOf(string.size()>=9?string.get(8):""));
//                            oInternetRenewDetail.setInternetCardNum(String.valueOf(string.size()>=10?string.get(9):""));
//                            String openAccountTime = String.valueOf(string.size() >= 11 ? string.get(10) : "");
//                            if(StringUtils.isNotBlank(openAccountTime) && !openAccountTime.equals("null"))
//                            oInternetRenewDetail.setOpenAccountTime(DateUtils.parseDate(openAccountTime,dateFormat));
//                            String expireTime = String.valueOf(string.size() >= 12 ? string.get(11) : "");
//                            if(StringUtils.isNotBlank(expireTime) && !expireTime.equals("null"))
//                            oInternetRenewDetail.setExpireTime(DateUtils.parseDate(expireTime,dateFormat));
//                            oInternetRenewDetail.setRenewWay(InternetRenewWay.getValueByContent(String.valueOf(string.size()>=13?string.get(12):"")));
//                            oInternetRenewDetail.setOffsetAmt(string.size()>=14?new BigDecimal(string.get(13)):null);
//                            oInternetRenewDetail.setRenewAmt(string.size()>=15?new BigDecimal(string.get(14)):null);
//                            oInternetRenewDetail.setOughtAmt(string.size()>=16?new BigDecimal(string.get(15)):null);
//                            oInternetRenewDetail.setRealityAmt(string.size()>=17?new BigDecimal(string.get(16)):null);
//                            oInternetRenewDetail.setRenewStatus(InternetRenewStatus.getValueByContent(String.valueOf(string.size()>=18?string.get(17):"")));
//                            String cTime = String.valueOf(string.size() >= 19 ? string.get(18) : "");
//                            if(StringUtils.isNotBlank(cTime) && !cTime.equals("null"))
//                            oInternetRenewDetail.setcTime(DateUtils.parseDate(cTime,dateFormat));
//                            jsonList = JsonUtil.objectToJson(oInternetRenewDetail);
                        }else if(importType.equals(CardImportType.G.getValue())){
                            String iccid = String.valueOf(string.size()>=1?string.get(0):"");//iccid
                            String postponeTime = String.valueOf(string.size()>=2?string.get(1):"");//延期月份
                            String postponeCause = String.valueOf(string.size()>=3?string.get(2):"");//延期原因
                            oInternetCard.setIccidNum(iccid);
                            oInternetCard.setPostponeTime(postponeTime);
                            oInternetCard.setPostponeCause(postponeCause.equals("null")?"":postponeCause);
                            jsonList = JsonUtil.objectToJson(oInternetCard);
                        }else if(importType.equals(CardImportType.H.getValue())){
                            OInternetRenew oInternetRenew = new OInternetRenew();
                            String iccid = String.valueOf(string.size()>=1?string.get(0):"");// iccid
                            String internetRenewWay = String.valueOf(string.size()>=2?string.get(1):"");// 续费方式
                            String internetRenewWayCode = InternetRenewWay.getValueByContent(internetRenewWay);
                            oInternetRenew.setIccidNumIds(iccid);
                            oInternetRenew.setcUser(userId);
                            oInternetRenew.setRenewWay(internetRenewWayCode);
                            oInternetRenew.setRenewWayName(internetRenewWay);
                            jsonList = JsonUtil.objectToJson(oInternetRenew);
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
                    AppConfig.sendEmails(MailUtil.printStackTrace(e), "流量卡导入出现异常");
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 处理导入表数据
     * @param batchNo
     */
    private void analysisImport(String batchNo){

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
                    if(internetCard.getOpenAccountTime() !=null ){// 不更新开户日期
                        internetCard.setOpenAccountTime(null);
                    }
                    disposeInternetCard(oInternetCardImport,internetCard);
                }else if(importType.equals(CardImportType.C.getValue())){
                    OInternetCard internetCard = JsonUtil.jsonToPojo(oInternetCardImport.getImportMsg(), OInternetCard.class);
                    if(StringUtils.isBlank(internetCard.getBeginSn()) || StringUtils.isBlank(internetCard.getSnCount())){
                        throw new MessageException("缺少iccid开始号段或总数量");
                    }
                    if(importType.equals(CardImportType.C.getValue()) && internetCard.getDeliverTime() == null){
                        throw new MessageException("发货日期不能为空或将日期单元格格式为文本且内容格式为年-月-日,iccid开始号"+internetCard.getBeginSn());
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
//                    OInternetRenewDetail internetRenewDetail = JsonUtil.jsonToPojo(oInternetCardImport.getImportMsg(), OInternetRenewDetail.class);
//                    if(null==internetRenewDetail.getTheRealityAmt()){
//                        throw new MessageException("本次抵扣金额为空或格式不正确");
//                    }
//                    if(null==internetRenewDetail.getId()){
//                        throw new MessageException("明细编号为空");
//                    }
//                    Boolean regOperationAmt = RegExpression.regAmount(internetRenewDetail.getTheRealityAmt());
//                    if(!regOperationAmt){
//                        throw new MessageException("操作金额不正确,保留小数点后两位");
//                    }
//                    if(internetRenewDetail.getTheRealityAmt().compareTo(BigDecimal.ZERO)==0 || internetRenewDetail.getTheRealityAmt().compareTo(new BigDecimal("0.00"))==0){
//                        throw new MessageException("操作金额不正确,不能为0");
//                    }
//                    OInternetRenewDetail qInternetRenewDetail = internetRenewDetailMapper.selectByPrimaryKey(internetRenewDetail.getId());
//                    if(null==qInternetRenewDetail){
//                        throw new MessageException("明细不存在");
//                    }
//                    if(!qInternetRenewDetail.getRenewWay().equals(InternetRenewWay.FRDK.getValue()) && !qInternetRenewDetail.getRenewWay().equals(InternetRenewWay.FRDKGC.getValue())){
//                        throw new MessageException("只能导入分润抵扣的数据");
//                    }
//                    BigDecimal realityAmt = internetRenewDetail.getTheRealityAmt().add(qInternetRenewDetail.getRealityAmt());
//                    if(realityAmt.compareTo(qInternetRenewDetail.getOughtAmt())==1){
//                        throw new MessageException("实扣金额不能大于应扣金额");
//                    }
//                    OInternetCard oInternetCard = internetCardMapper.selectByPrimaryKey(qInternetRenewDetail.getIccidNum());
//                    //等于更新状态已续费
//                    if(realityAmt.compareTo(qInternetRenewDetail.getOughtAmt())==0){
//                        qInternetRenewDetail.setRenewStatus(InternetRenewStatus.YXF.getValue());
//                        oInternetCard.setRenewStatus(InternetRenewStatus.YXF.getValue());
//                        //扣足到期时间加一年
//                        oInternetCard.setExpireTime(DateUtil.getOneYearLater(oInternetCard.getExpireTime()));
//                    }else{
//                        qInternetRenewDetail.setRenewStatus(InternetRenewStatus.BFXF.getValue());
//                        oInternetCard.setRenewStatus(InternetRenewStatus.BFXF.getValue());
//                    }
//                    qInternetRenewDetail.setRealityAmt(realityAmt);
//                    qInternetRenewDetail.setuTime(new Date());
//                    int i = internetRenewDetailMapper.updateByPrimaryKeySelective(qInternetRenewDetail);
//                    if(i!=1){
//                        throw new MessageException("更新续费明细失败");
//                    }
//                    oInternetCard.setuTime(new Date());
//                    int k = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
//                    if(k!=1){
//                        throw new MessageException("更新物联网卡信息失败");
//                    }
//                    oInternetCardImport.setImportStatus(OInternetCardImportStatus.SUCCESS.getValue());
//                    updateInternetCardImport(oInternetCardImport);
                }else if(importType.equals(CardImportType.G.getValue())){
                    OInternetCard internetCard = JsonUtil.jsonToPojo(oInternetCardImport.getImportMsg(), OInternetCard.class);
                    if(null==internetCard){
                        throw new MessageException("导入信息不存在");
                    }
                    if(StringUtils.isBlank(internetCard.getIccidNum()) || StringUtils.isBlank(internetCard.getPostponeTime())){
                        throw new MessageException("缺少ICCID或延期月份");
                    }
                    BigDecimal postponeTime = null;
                    try {
                        postponeTime = new BigDecimal(internetCard.getPostponeTime());
                    }catch (Exception e){
                        throw new MessageException("延期月份有误.");
                    }
                    if(postponeTime==null){
                        throw new MessageException("延期月份有误");
                    }
                    OInternetCardPostpone internetCardPostpone = new OInternetCardPostpone();
                    internetCardPostpone.setIccid(internetCard.getIccidNum());
                    internetCardPostpone.setPostponeTime(postponeTime);
                    internetCardPostpone.setPostponeCause(internetCard.getPostponeCause());
                    //更新导入
                    oInternetCardImport.setImportStatus(OInternetCardImportStatus.SUCCESS.getValue());
                    updateInternetCardImport(oInternetCardImport);
                    //延期
                    internetCardService.internetCardPostpone(internetCardPostpone,oInternetCardImport.getcUser(),oInternetCardImport.getId(),oInternetCardImport.getBatchNum());
                }else if(importType.equals(CardImportType.H.getValue())){ // 批量续费
                    OInternetRenew oInternetRenew = JsonUtil.jsonToPojo(oInternetCardImport.getImportMsg(), OInternetRenew.class);
                    if(null==oInternetRenew){
                        throw new MessageException("导入信息不存在");
                    }
                    if(StringUtils.isBlank(oInternetRenew.getIccidNumIds())){
                        throw new MessageException("缺少ICCID");
                    }
                    if(StringUtils.isBlank(oInternetRenew.getRenewWay())){
                        throw new MessageException("缺少续费方式或不正确");
                    }
                    // 更新导入
                    oInternetCardImport.setImportStatus(OInternetCardImportStatus.SUCCESS.getValue());
                    updateInternetCardImport(oInternetCardImport);
                    // 延期
                    oInternetRenew.setRenewWay(oInternetRenew.getRenewWay());// 续费方式获取
                    String iccids = oInternetRenew.getIccidNumIds();// ICCIDS
                    oInternetRenewService.batchRenewInsert(oInternetRenew, iccids, batchNo);
                }
            } catch (MessageException e) {
                log.info("analysisImport处理导入表数据,MessageException:{}",e.getLocalizedMessage());
                e.printStackTrace();
                oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
                oInternetCardImport.setErrorMsg(e.getMsg());
                //更新导入记录
                try {
                    updateInternetCardImport(oInternetCardImport);
                } catch (Exception e1) {
                    AppConfig.sendEmails(MailUtil.printStackTrace(e1), "流量卡导入出现异常,analysisImport方法1");
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
                    AppConfig.sendEmails(MailUtil.printStackTrace(e1), "流量卡导入出现异常,analysisImport方法2");
                    e1.printStackTrace();
                }
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
        if(StringUtils.isNotBlank(internetCard.getBusNum())){
            if(StringUtils.isBlank(internetCard.getBusPlatform())){
                oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
                oInternetCardImport.setErrorMsg("缺少业务平台");
                //更新导入记录
                updateInternetCardImport(oInternetCardImport);
                return;
            }
            PlatForm platForm = platFormService.selectByPlatformName(internetCard.getBusPlatform());
            if(null==platForm){
                oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
                oInternetCardImport.setErrorMsg("业务平台不存在,请仔细检查");
                //更新导入记录
                updateInternetCardImport(oInternetCardImport);
                return;
            }
            AgentBusInfo agentBusInfo = new AgentBusInfo();
            agentBusInfo.setBusNum(internetCard.getBusNum().trim());
            agentBusInfo.setBusPlatform(platForm.getPlatformNum());
            List<BigDecimal> busStatusList = new ArrayList<>();
            busStatusList.add(BusStatus.QY.getValue());// 启用
            busStatusList.add(BusStatus.WJH.getValue());// 未激活
            busStatusList.add(BusStatus.WQY.getValue());// 未启用
            agentBusInfo.setBusStatusList(busStatusList);
            agentBusInfo.setCloReviewStatus(AgStatus.Approved.getValue());
            List<AgentBusInfo> agentBusInfos = agentBusinfoService.selectByAgentBusInfo(agentBusInfo);
            if(agentBusInfos.size()!=1){
                oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
                oInternetCardImport.setErrorMsg("业务平台和编号不匹配,请仔细检查");
                //更新导入记录
                updateInternetCardImport(oInternetCardImport);
                return;
            }
            internetCard.setBusPlatform(platForm.getPlatformNum());
            internetCard.setBusNum(internetCard.getBusNum().trim());
        }
        if(StringUtils.isNotBlank(internetCard.getBusPlatform()) && StringUtils.isBlank(internetCard.getBusNum())){
            oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
            oInternetCardImport.setErrorMsg("缺少业务平台编号");
            //更新导入记录
            updateInternetCardImport(oInternetCardImport);
            return;
        }
        if(StringUtils.isNotBlank(internetCard.getIssuer())){
            String issuerCode = Issuerstatus.getContentByMsg(internetCard.getIssuer());
            if(StringUtils.isBlank(issuerCode)){
                oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
                oInternetCardImport.setErrorMsg("发卡方不存在,请联系管理员");
                //更新导入记录
                updateInternetCardImport(oInternetCardImport);
                return;
            }
            internetCard.setIssuer(issuerCode);
        }
        OInternetCard oInternetCard = internetCardMapper.selectByPrimaryKey(internetCard.getIccidNum());
        internetCard.setBatchNum(oInternetCardImport.getBatchNum());
        internetCard.setCardImportId(oInternetCardImport.getId());
        if(internetCard.getDeliverTime()!=null){ // 取其发货日期为开户日期
            Date date = DateUtil.getOneYearLater(internetCard.getDeliverTime());
            internetCard.setOpenAccountTime(internetCard.getDeliverTime());
            internetCard.setExpireTime(date);
            internetCard.setRenew(BigDecimal.ZERO);
            internetCard.setStop(BigDecimal.ZERO);
        }
        if(internetCard.getInternetCardStatus()!=null && (internetCard.getInternetCardStatus().compareTo(InternetCardStatus.STOP.getValue())==0
            || internetCard.getInternetCardStatus().compareTo(InternetCardStatus.LOGOUT.getValue())==0)){
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
            insertInternetCard(internetCard);
        }else{
            //如果之前是注销状态，不能在导入其他状态
            if(oInternetCard.getInternetCardStatus()!=null && oInternetCard.getInternetCardStatus().compareTo(InternetCardStatus.LOGOUT.getValue())==0){
                if(null!=internetCard.getInternetCardStatus()){
                    oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
                    oInternetCardImport.setErrorMsg("卡状态已是注销,不可在更新状态");
                    //更新导入记录
                    updateInternetCardImport(oInternetCardImport);
                    return;
                }
            }
            if(internetCard.getInternetCardStatus()!=null && internetCard.getInternetCardStatus().compareTo(InternetCardStatus.LOGOUT.getValue())==0){
                InternetLogoutDetailExample internetLogoutDetailEx = new InternetLogoutDetailExample();
                InternetLogoutDetailExample.Criteria logoutCriteria = internetLogoutDetailEx.createCriteria();
                logoutCriteria.andStatusEqualTo(Status.STATUS_1.status);
                logoutCriteria.andIccidNumEqualTo(internetCard.getIccidNum());
                List<String> logoutStatusList = new ArrayList<>();
                if(oInternetCard.getIssuer().equals(Issuerstatus.YT_MOBILE.code) ){
                    logoutStatusList.add(InternetLogoutStatus.ZXZ.getValue());// 待注销
                }else if(oInternetCard.getIssuer().equals(Issuerstatus.JY_MOBILE.code)){
                    logoutStatusList.add(InternetLogoutStatus.ZXZ.getValue());// 待注销
                    logoutStatusList.add(InternetLogoutStatus.TJCLZ.getValue()); // 注销中  烟台是注销中需要人工处理  揭阳处理中需要自动处理
                }
                logoutCriteria.andLogoutStatusIn(logoutStatusList);// 查询是否有在处理中的 条件： 烟台查询 待注销的  ，揭阳查询 待注销和 注销中的
                List<InternetLogoutDetail> internetLogoutDetailList = internetLogoutDetailMapper.selectByExample(internetLogoutDetailEx);
                if(internetLogoutDetailList.size()!=0){
                    oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
                    oInternetCardImport.setErrorMsg("iccid:"+internetCard.getIccidNum()+",正在注销中,不可在更新卡状态");
                    //更新导入记录
                    updateInternetCardImport(oInternetCardImport);
                    return;
                }
                InternetLogoutDetailExample internetLogoutDetailExample = new InternetLogoutDetailExample();
                InternetLogoutDetailExample.Criteria criteria = internetLogoutDetailExample.createCriteria();
                criteria.andStatusEqualTo(Status.STATUS_1.status);
                criteria.andIccidNumEqualTo(internetCard.getIccidNum());
                if(oInternetCard.getIssuer().equals(Issuerstatus.YT_MOBILE.code) ){
                    criteria.andLogoutStatusEqualTo(InternetLogoutStatus.TJCLZ.getValue());// 烟台移动 查询注销中的  更新为注销成功（人工导入处理）
                }else if(oInternetCard.getIssuer().equals(Issuerstatus.JY_MOBILE.code)){
                    criteria.andLogoutStatusEqualTo(InternetLogoutStatus.TJSB.getValue());// 揭阳移动 查询注销失败的  更新为注销成功（人工导入处理）
                }
                List<InternetLogoutDetail> internetLogoutDetails = internetLogoutDetailMapper.selectByExample(internetLogoutDetailExample);
                for (InternetLogoutDetail internetLogoutDetail : internetLogoutDetails) {
                    internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.ZXCG.getValue());// 更改为已注销
                    int i = internetLogoutDetailMapper.updateByPrimaryKey(internetLogoutDetail);
                    if(i!=1){
                        oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
                        oInternetCardImport.setErrorMsg("注销更新记录失败");
                        //更新导入记录
                        updateInternetCardImport(oInternetCardImport);
                        return;
                    }
                }
                internetCard.setRenew(Status.STATUS_0.status);
                internetCard.setRenewStatus(InternetRenewStatus.YZX.getValue());
            }
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
            if(StringUtils.isNotBlank(internetCard.getAgentName())){
                Agent agent = agentService.getAgentByName(internetCard.getAgentName());
                if(agent!=null){
                    oInternetCard.setAgentId(agent.getId());
                }
            }
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
        internetCard.setRenew(Status.STATUS_0.status); //否
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

//    /**
//     * 导出错误数据
//     * @param internetCardImport
//     * @return
//     * @throws MessageException
//     */
//    @Override
//    public List<OInternetCardImport>  exportErrorExcel(OInternetCardImport internetCardImport){
//        OInternetCardImportExample oInternetCardImportExample = new OInternetCardImportExample();
//        OInternetCardImportExample.Criteria criteria = oInternetCardImportExample.createCriteria();
//        criteria.andStatusEqualTo(Status.STATUS_1.status);
//        criteria.andBatchNumEqualTo(internetCardImport.getBatchNum());
//        criteria.andImportTypeEqualTo(internetCardImport.getImportType());
//        criteria.andImportStatusEqualTo(OInternetCardImportStatus.FAIL.code);
//        List<OInternetCardImport> oInternetCardImports = internetCardImportMapper.selectByExample(oInternetCardImportExample);
//        return oInternetCardImports;
//    }


    public void updateInternetCardImport(OInternetCardImport internetCardImport)throws MessageException{
        internetCardImport.setuTime(new Date());
        internetCardImportMapper.updateByPrimaryKeySelective(internetCardImport);
    }


    /**
     * 定时任务，
     * 1. 检测是否续费为否，状态为正常的，当月的，更新“是否需续费”为是
     * 2. 到期日减去5天  还未续费的 更新“是否需关停”为是
     * 3. 处理未处理的导入记录
     */
    @Override
    public void taskDisposeInternetCard(){
        log.info("taskDisposeInternetCard定时任务,开始执行");
        String retIdentifier = "";
        try {
            retIdentifier = redisService.lockWithTimeout(RedisCachKey.TASK_DISPOSEIN_TERNET_CARD.code, RedisService.ACQUIRE_TIME_OUT, RedisService.TIME_OUT);
            if (StringUtils.isBlank(retIdentifier)) {
                log.info("物联网卡定时任务处理中");
                return;
            }
            //1. 检测是否续费为否，状态为正常或待激活的，下个月的，更新“是否需续费”为是
            Map<String,Object> reqMap = new HashMap<>();
            reqMap.put("renew",Status.STATUS_0.status);//否
            reqMap.put("newRenew",Status.STATUS_1.status);
            reqMap.put("renewStatus",InternetRenewStatus.WXF.getValue());
            List<String> expireTimeList = new ArrayList<>();
            expireTimeList.add(DateUtil.getPerDayOfMonth(0));
            expireTimeList.add(DateUtil.getPerDayOfMonth(1));
            expireTimeList.add(DateUtil.getPerDayOfMonth(2));
            expireTimeList.add(DateUtil.getPerDayOfMonth(3));
            reqMap.put("expireTimeList",expireTimeList);
            // 待激活和正常 测试期 沉默期
            reqMap.put("cardStaus1",InternetCardStatus.NORMAL.getValue());
            reqMap.put("cardStaus2",InternetCardStatus.NOACTIVATE.getValue());
            reqMap.put("cardStaus3",InternetCardStatus.test.getValue());
            reqMap.put("cardStaus4",InternetCardStatus.silent.getValue());
            List<String> renewStatusList = new ArrayList<>();
            renewStatusList.add(InternetRenewStatus.WXF.getValue());
            renewStatusList.add(InternetRenewStatus.YXF.getValue());
            reqMap.put("renewStatusList",renewStatusList);
            int i = internetCardMapper.selectInternetCardExpireCount(reqMap);//TODO 这里是查几个月呢？
            if(i>0){
                int updateCount = internetCardMapper.updateInternetCardExpire(reqMap);
                log.info("taskDisposeInternetCard：1检测是否续费,本次更次了数据条数:{}",updateCount);
            }else{
                log.info("taskDisposeInternetCard：1检测是否续费,暂无更新数据:{}",i);
            }
            //2. 到期日提前5天  还未续费的 更新“是否需关停”为是
//            Map<String,Object> reqRenewMap = new HashMap<>();
//            reqRenewMap.put("renewStatus",InternetRenewStatus.WXF.getValue());
//            reqRenewMap.put("expireTime",DateUtil.getDateAfter(new Date(),+5));
//            reqRenewMap.put("nowTime",DateUtil.format(new Date(),"yyyy-MM-dd"));
//            reqRenewMap.put("stop",Status.STATUS_1.status);
//            reqRenewMap.put("oldStop",Status.STATUS_0.status);
//            //待激活和正常
//            reqRenewMap.put("cardStaus1",InternetCardStatus.NORMAL.getValue());
//            reqRenewMap.put("cardStaus2",InternetCardStatus.NOACTIVATE.getValue());
//            int j = internetCardMapper.selectInternetCardStopCount(reqRenewMap);
//            if(j>0){
//                int updateCount = internetCardMapper.updateInternetCardStop(reqRenewMap);
//                log.info("taskDisposeInternetCard：2到期日减去5天还未续费的,本次更次了数据条数:{}",updateCount);
//            }else{
//                log.info("taskDisposeInternetCard：2到期日减去5天还未续费的,暂无更新数据:{}",i);
//            }
            //3.处理未处理的导入记录
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
            List<String> expireTimeList = new ArrayList<>();
            expireTimeList.add(DateUtil.getPerDayOfMonth(0));
            expireTimeList.add(DateUtil.getPerDayOfMonth(1));
            expireTimeList.add(DateUtil.getPerDayOfMonth(2));
            expireTimeList.add(DateUtil.getPerDayOfMonth(3));
            reqMap.put("expireTimeList",expireTimeList);
            List<BigDecimal> cardStatusList = new ArrayList<>();
            cardStatusList.add(InternetCardStatus.NORMAL.getValue());
            cardStatusList.add(InternetCardStatus.NOACTIVATE.getValue());
            reqMap.put("cardStatusList",cardStatusList);
            List<OInternetCard> oInternetCards = internetCardMapper.selectInternetCardRenew(reqMap);
            log.info("更新已修改的商户信息数量:{}",oInternetCards.size());
            for (OInternetCard oInternetCard : oInternetCards) {
                try {
                    log.info("更新已修改的商户信息,OInternetCard:{}",oInternetCard.toString());
                    OInternetCardMerch oInternetCardMerch = internetCardMerchMapper.selectChnTermposi(BigDataEncode.encode(oInternetCard.getIccidNum()));
                    if(null!=oInternetCardMerch){
                        log.info("更新已修改的商户信息,OInternetCardMerch:{}",oInternetCardMerch.toString());
                        //之前未获取当商户信息的直接更新
                        if(StringUtils.isBlank(oInternetCard.getMerId()) || StringUtils.isBlank(oInternetCard.getMerName())){
                            oInternetCard.setMerId(oInternetCardMerch.getChnMerchId());
                            oInternetCard.setMerName(oInternetCardMerch.getMerchName());
                            log.info("更新已修改的商户信息,1:{}",oInternetCard.getIccidNum());
                        }
                        else if(StringUtils.isNotBlank(oInternetCardMerch.getChnMerchId()) && StringUtils.isNotBlank(oInternetCardMerch.getMerchName()) &&
                               (!oInternetCardMerch.getChnMerchId().equals(oInternetCard.getMerId()) || !oInternetCardMerch.getMerchName().equals(oInternetCard.getMerName()))){
                            oInternetCard.setMerId(oInternetCardMerch.getChnMerchId());
                            oInternetCard.setMerName(oInternetCardMerch.getMerchName());
                            log.info("更新已修改的商户信息,2:{}",oInternetCard.getIccidNum());
                        }
                        else if(StringUtils.isNotBlank(oInternetCardMerch.getChnMerchId()) && !oInternetCardMerch.getChnMerchId().equals(oInternetCard.getMerId())){
                            oInternetCard.setMerId(oInternetCardMerch.getChnMerchId());
                            oInternetCard.setMerName("无");
                            log.info("更新已修改的商户信息,3:{}",oInternetCard.getIccidNum());
                        }
                        else if(StringUtils.isNotBlank(oInternetCardMerch.getMerchName()) && !oInternetCardMerch.getMerchName().equals(oInternetCard.getMerName())){
                            oInternetCard.setMerId("无");
                            oInternetCard.setMerName(oInternetCardMerch.getMerchName());
                            log.info("更新已修改的商户信息,4:{}",oInternetCard.getIccidNum());
                        }else{
                            log.info("更新已修改的商户信息,已是最新数据:{}",oInternetCard.getIccidNum());
                            continue;
                        }
                        int i = internetCardMapper.updateByPrimaryKeySelectiveNotNull(oInternetCard);
                        if(i!=1){
                            log.error("更新已修改的商户信息失败:IccidNum:{},商户编号:{},商户名称:{}",oInternetCard.getIccidNum(),oInternetCardMerch.getChnMerchId(),oInternetCardMerch.getMerchName());
                        }
                        log.info("更新已修改的商户信息成功,iccid:{}",oInternetCard.getIccidNum());
                    }
                } catch (Exception e) {
                    log.info("更新已修改的商户信息异常,iccid:{}",oInternetCard.getIccidNum());
                    AppConfig.sendEmails(MailUtil.printStackTrace(e), "更新已修改的商户信息出现异常,taskUpdateMech方法");
                    e.printStackTrace();
                }
            }
            long t2 = System.currentTimeMillis();
            log.info("更新已修改的商户信息，处理时间:{} ms", (t2 - t1));
        } catch (Exception e) {
            AppConfig.sendEmails(MailUtil.printStackTrace(e), "更新已修改的商户信息出现异常,taskUpdateMech方法2");
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
            AppConfig.sendEmails(MailUtil.printStackTrace(e), "为空定时任务更新商户信息出现异常,processDataUpdateMechIsNull方法");
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
        log.info("订单发货同步到流量卡信息表中开始:{},个数{}",batchNo,logisticsDetailList.size());
        for (OLogisticsDetail oLogisticsDetail : logisticsDetailList) {
            log.info("订单发货同步到流量卡信息表中开始:sn:{}",oLogisticsDetail.getSnNum());
            OInternetCard internetCard = internetCardMapper.selectBySnNum(oLogisticsDetail.getSnNum());
            AgentBusInfo agentBusInfo = agentBusinfoService.getById(oLogisticsDetail.getBusId());
            if (null != internetCard){
                log.info("订单发货同步到流量卡更新开始:sn:{}",oLogisticsDetail.getSnNum());
                internetCard.setBatchNum(batchNo);
                internetCard.setOrderId(oLogisticsDetail.getOrderId());
                internetCard.setDeliverTime(oLogisticsDetail.getcTime());
                internetCard.setAgentId(oLogisticsDetail.getAgentId());
                Agent agent = agentService.getAgentById(oLogisticsDetail.getAgentId());
                if(agent!=null) internetCard.setAgentName(agent.getAgName());
                internetCard.setManufacturer(manuFacturer);
                internetCard.setRenewStatus(InternetRenewStatus.WXF.getValue());
                internetCard.setStop(Status.STATUS_0.status);
                internetCard.setRenew(Status.STATUS_0.status);
                internetCard.setInternetCardStatus(InternetCardStatus.NOACTIVATE.code);
                Date date = new Date();
                internetCard.setcTime(date);
                internetCard.setuTime(date);
                internetCard.setuUser(internetCard.getcUser());
                internetCard.setStatus(Status.STATUS_1.status);
                internetCard.setVersion(BigDecimal.ONE);
                if(agentBusInfo!=null){
                    internetCard.setBusNum(agentBusInfo.getBusNum());
                    internetCard.setBusPlatform(agentBusInfo.getBusPlatform());
                }
                int i = internetCardMapper.updateByPrimaryKeySelective(internetCard);
                if(i!=1){
                    AppConfig.sendEmails("订单发货流量卡信息更新失败", "订单发货流量卡信息更新出现异常,orderInsertInternetCard方法");
                    throw new MessageException("订单发货流量卡信息更新失败");
                }
                log.info("订单发货同步到流量卡更新结束:i:{}",i);
            }else {
                log.info("订单发货同步到流量卡插入开始:sn:{}",oLogisticsDetail.getSnNum());
                OInternetCard oInternetCard = new OInternetCard();
                oInternetCard.setIccidNum(oLogisticsDetail.getSnNum());
                oInternetCard.setBatchNum(batchNo);
                oInternetCard.setOrderId(oLogisticsDetail.getOrderId());
                oInternetCard.setDeliverTime(oLogisticsDetail.getcTime());// oLogisticsDetail  物流明细
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
                oInternetCard.setcTime(date); // ctime  创建
                oInternetCard.setuTime(date); // utime  更新
                // 发货时间
                oInternetCard.setuUser(oInternetCard.getcUser());
                oInternetCard.setStatus(Status.STATUS_1.status);
                oInternetCard.setVersion(BigDecimal.ONE);
                if(agentBusInfo!=null){
                    oInternetCard.setBusNum(agentBusInfo.getBusNum());
                    oInternetCard.setBusPlatform(agentBusInfo.getBusPlatform());
                }
                internetCardMapper.insert(oInternetCard);
                log.info("订单发货同步到流量卡插入结束sn:{}",oLogisticsDetail.getSnNum());
            }
        }
        log.info("订单发货同步到流量卡信息表中结束");
    }


    /**
     * 查询要迁移的数据
     * @return
     */
    @Override
    public List<OInternetCardImport> queryMigrationHistory(){
        OInternetCardImportExample oInternetCardImportExample = new OInternetCardImportExample();
        OInternetCardImportExample.Criteria criteria = oInternetCardImportExample.createCriteria();
        Date dateAfter = DateUtil.getDateAfterReturnDate(new Date(), -30);
        criteria.andCTimeLessThanOrEqualTo(dateAfter);
        oInternetCardImportExample.setPage(new Page(0,10000));
        List<OInternetCardImport> oInternetCardImports = internetCardImportMapper.selectByExample(oInternetCardImportExample);
        log.info("查询要迁移的数据个数{}",oInternetCardImports.size());
        return oInternetCardImports;
    }


    /**
     * 导入迁移到历史表
     * @param oInternetCardImport
     * @throws MessageException
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void migrationHistory(OInternetCardImport oInternetCardImport)throws MessageException{
        log.info("导入迁移到历史表开始{}",oInternetCardImport.toString());
        InternetCardImportHistory internetCardImportHistory = new InternetCardImportHistory();
        BeanUtils.copyProperties(oInternetCardImport,internetCardImportHistory);
        internetCardImportHistoryMapper.insert(internetCardImportHistory);
        OInternetCardImportExample internetCardImportExample = new OInternetCardImportExample();
        OInternetCardImportExample.Criteria criteria = internetCardImportExample.createCriteria();
        criteria.andIdEqualTo(oInternetCardImport.getId());
        int i = internetCardImportMapper.deleteByExample(internetCardImportExample);
        if(i!=1){
            AppConfig.sendEmails("","导入迁移到历史表出现异常,migrationHistory方法");
            throw new MessageException("删除失败");
        }
    }


    /**
     * 延期记录
     * @param internetCardPostpone
     * @return
     */
    @Override
    public PageInfo queryInternetCardPostponeList(OInternetCardPostpone internetCardPostpone,Page page,String agentId,Long userId){
        OInternetCardPostponeExample internetCardPostponeExample = new OInternetCardPostponeExample();
        OInternetCardPostponeExample.Criteria criteria = internetCardPostponeExample.createCriteria();
        internetCardPostponeExample.setPage(page);
        internetCardPostponeExample.setOrderByClause(" c_time desc ");
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        if(StringUtils.isNotBlank(internetCardPostpone.getOrderId())){
            criteria.andOrderIdEqualTo(internetCardPostpone.getOrderId());
        }
        if(StringUtils.isNotBlank(internetCardPostpone.getBatchNum())){
            criteria.andBatchNumEqualTo(internetCardPostpone.getBatchNum());
        }
        //代理商只查询自己的
        if(StringUtils.isNotBlank(agentId)){
            criteria.andAgentIdEqualTo(agentId);
        }else if(StringUtils.isNotBlank(internetCardPostpone.getAgentId())){
            criteria.andAgentIdEqualTo(internetCardPostpone.getAgentId());
        }
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
        if(orgCodeRes==null && orgCodeRes.size()!=1){
            return null;
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        Map<String,Object> reqMap = new HashMap<>();
        //省区大区查看自己的代理商 部门权限
        if(StringUtils.isNotBlank(organizationCode) && (organizationCode.contains("region") || organizationCode.contains("beijing"))) {
            reqMap.put("orgCode", organizationCode);
        }
        //内部人员根据名称查询指定流量卡
        List<String> agentNameList = dictOptionsService.getAgentNameList(userId);
        if(agentNameList.size()!=0) {
            reqMap.put("agentNameList", agentNameList);
        }else if(StringUtils.isNotBlank(internetCardPostpone.getAgentName())){
            criteria.andAgentNameLike("%"+internetCardPostpone.getAgentName()+"%");
        }
        if(StringUtils.isNotBlank(internetCardPostpone.getMerId())){
            criteria.andMerIdEqualTo(internetCardPostpone.getMerId());
        }
        if(StringUtils.isNotBlank(internetCardPostpone.getMerName())){
            criteria.andMerNameEqualTo(internetCardPostpone.getMerName());
        }
        if(StringUtils.isNotBlank(internetCardPostpone.getBusNum())){
            criteria.andBusNumEqualTo(internetCardPostpone.getBusNum());
        }
        if(StringUtils.isNotBlank(internetCardPostpone.getIccid())){
            criteria.andIccidEqualTo(internetCardPostpone.getIccid());
        }else if(StringUtils.isNotBlank(internetCardPostpone.getIccidNum())){
            boolean contains = internetCardPostpone.getIccidNum().contains(",");
            if(contains){
                String[] iccidNumStr = internetCardPostpone.getIccidNum().split(",");
                List<String> iccidNumList = new ArrayList<>();
                for (String iccidNum : iccidNumStr) {
                    iccidNumList.add(iccidNum);
                }
                criteria.andIccidIn(iccidNumList);
            }else{
                criteria.andIccidLike(internetCardPostpone.getIccidNum()+"%");
            }
        }
        internetCardPostponeExample.setReqMap(reqMap);
        List<OInternetCardPostpone> internetCardPostpones = internetCardPostponeMapper.queryInternetCardPostponeList(internetCardPostponeExample);
        for (OInternetCardPostpone cardPostpone : internetCardPostpones) {
            CUser cUser = iUserService.selectById(cardPostpone.getcUser());
            if(null!=cUser)cardPostpone.setcUser(cUser.getName());
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(internetCardPostpones);
        pageInfo.setTotal(internetCardPostponeMapper.queryInternetCardPostponeCount(internetCardPostponeExample));
        return pageInfo;
    }

    /**
     * 延期
     * @param internetCardPostpone
     * @param cUser
     * @throws MessageException
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void internetCardPostpone(OInternetCardPostpone internetCardPostpone,String cUser,String importId,String batchNum)throws MessageException{

        log.info("延期处理开始");
        if(internetCardPostpone.getPostponeTime()==null){
            throw new MessageException("请填写延期月份");
        }
        if(internetCardPostpone.getIccid()==null){
            throw new MessageException("请选择要延期的数据");
        }
        boolean checkInt = RegexUtil.checkInt(String.valueOf(internetCardPostpone.getPostponeTime()));
        if(!checkInt){
            throw new MessageException("延期月份必须为数字");
        }

        OInternetCard oInternetCard = internetCardMapper.selectByPrimaryKey(internetCardPostpone.getIccid());
        if(oInternetCard==null){
            throw new MessageException("请选择要延期的数据");
        }
        if(oInternetCard.getInternetCardStatus().compareTo(InternetCardStatus.LOGOUT.getValue())==0){
            throw new MessageException("注销不可以设置延期");
        }
        internetCardPostpone.setId(idService.genId(TabId.O_INTERNET_CARD_POSTPONE));
        internetCardPostpone.setAgentId(oInternetCard.getAgentId());
        internetCardPostpone.setAgentName(oInternetCard.getAgentName());
        internetCardPostpone.setMerId(oInternetCard.getMerId());
        internetCardPostpone.setMerName(oInternetCard.getMerName());
        internetCardPostpone.setOrderId(oInternetCard.getOrderId());
        internetCardPostpone.setOpenAccountTime(oInternetCard.getOpenAccountTime());
        internetCardPostpone.setExpireTime(oInternetCard.getExpireTime());
        Date mondayLater = DateUtil.getMondayLater(oInternetCard.getExpireTime(), internetCardPostpone.getPostponeTime().intValue());
        internetCardPostpone.setPostponeExpireTime(mondayLater);
        internetCardPostpone.setcUser(cUser);
        internetCardPostpone.setuUser(cUser);
        Date date = new Date();
        internetCardPostpone.setcTime(date);
        internetCardPostpone.setuTime(date);
        internetCardPostpone.setStatus(Status.STATUS_1.status);
        internetCardPostpone.setVersion(BigDecimal.ONE);
        if(StringUtils.isNotBlank(batchNum))
        internetCardPostpone.setBatchNum(batchNum);
        internetCardPostpone.setBusNum(oInternetCard.getBusNum());
        internetCardPostpone.setBusPlatform(oInternetCard.getBusPlatform());

        if(StringUtils.isNotBlank(oInternetCard.getBusNum()) && StringUtils.isNotBlank(oInternetCard.getBusPlatform())){
            //查询最新对接省区大区对接人
            AgentBusInfo agentBusInfo = new AgentBusInfo();
            agentBusInfo.setBusNum(oInternetCard.getBusNum());
            agentBusInfo.setBusPlatform(oInternetCard.getBusPlatform());
            List<BigDecimal> busStatusList = new ArrayList<>();
            busStatusList.add(BusStatus.QY.getValue());
            busStatusList.add(BusStatus.WJH.getValue());
            busStatusList.add(BusStatus.WQY.getValue());
            agentBusInfo.setBusStatusList(busStatusList);
            agentBusInfo.setCloReviewStatus(AgStatus.Approved.getValue());
            List<AgentBusInfo> agentBusInfos = agentBusinfoService.selectByAgentBusInfo(agentBusInfo);
            if(agentBusInfos.size()!=1){
                throw new MessageException("平台码或平台错误,请联系管理员");
            }
            AgentBusInfo queryAgentBusInfo = agentBusInfos.get(0);
            internetCardPostpone.setAgDocDistrict(queryAgentBusInfo.getAgDocDistrict());
            internetCardPostpone.setAgDocPro(queryAgentBusInfo.getAgDocPro());
            internetCardPostpone.setBusContactPerson(queryAgentBusInfo.getBusContactPerson());
        }
        internetCardPostponeMapper.insert(internetCardPostpone);

        BigDecimal sumPostponeTime = oInternetCard.getSumPostponeTime();
        if(sumPostponeTime==null){
            oInternetCard.setSumPostponeTime(internetCardPostpone.getPostponeTime());
        }else {
            oInternetCard.setSumPostponeTime(internetCardPostpone.getPostponeTime().add(sumPostponeTime));
        }
        oInternetCard.setStop(Status.STATUS_0.status);
        oInternetCard.setRenew(Status.STATUS_0.status);
        oInternetCard.setExpireTime(mondayLater);
        if(StringUtils.isNotBlank(internetCardPostpone.getPostponeCause()))
        oInternetCard.setPostponeCause(internetCardPostpone.getPostponeCause());
        if(StringUtils.isNotBlank(importId)){
            oInternetCard.setCardImportId(importId);
        }
        if(StringUtils.isNotBlank(batchNum)){
            oInternetCard.setBatchNum(batchNum);
        }
        int i = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
        if(i!=1){
            throw new MessageException("处理延期月份失败");
        }
    }

}
