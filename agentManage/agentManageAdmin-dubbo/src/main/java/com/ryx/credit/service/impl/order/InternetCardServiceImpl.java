package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.order.OInternetCardImportMapper;
import com.ryx.credit.dao.order.OInternetCardMapper;
import com.ryx.credit.dao.order.OInternetCardMerchMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentExample;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.impl.agent.PosOrgStatisticsServiceImpl;
import com.ryx.credit.service.order.InternetCardService;
import com.ryx.credit.service.order.OLogisticsService;
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
    private IdService idService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private OLogisticsService logisticsService;
    @Autowired
    private InternetCardService internetCardService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OInternetCardMerchMapper internetCardMerchMapper;


    @Override
    public PageInfo internetCardList(OInternetCard internetCard, Page page){

        OInternetCardExample oInternetCardExample = new OInternetCardExample();
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
        if(StringUtils.isNotBlank(internetCard.getAgentId())){
            criteria.andAgentIdEqualTo(internetCard.getAgentId());
        }
        if(StringUtils.isNotBlank(internetCard.getAgentName())){
            criteria.andAgentNameEqualTo(internetCard.getAgentName());
        }
        if(StringUtils.isNotBlank(internetCard.getOrderId())){
            criteria.andOrderIdEqualTo(internetCard.getOrderId());
        }
        oInternetCardExample.setPage(page);
        oInternetCardExample.setOrderByClause(" c_time desc ");
        List<OInternetCard> oInternetCards = internetCardMapper.selectByExample(oInternetCardExample);
        for (OInternetCard oInternetCard : oInternetCards) {
            Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(),oInternetCard.getManufacturer());
            if(null!=dict)
            oInternetCard.setManufacturer(dict.getdItemname());
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(oInternetCards);
        pageInfo.setTotal((int)internetCardMapper.countByExample(oInternetCardExample));
        return pageInfo;
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
                    OInternetCard oInternetCard = new OInternetCard();
                    for (List<String> string : excelList) {
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
                        }else if(importType.equals(CardImportType.B.getValue())){
                            String manufacturer = String.valueOf(string.size()>=1?string.get(0):"");//发货方/厂商
                            String deliverTime = String.valueOf(string.size()>=2?string.get(1):"");//发货日期
                            String orderId = String.valueOf(string.size()>=3?string.get(2):"");//订单号
                            String agentName = String.valueOf(string.size()>=4?string.get(3):"");//代理商名称
                            String snNum = String.valueOf(string.size()>=5?string.get(4):"");//机具SN
                            String iccidNum = String.valueOf(string.size()>=6?string.get(5):"");//iccid
                            String consignee = String.valueOf(string.size()>=7?string.get(6):"");//收货人

                            oInternetCard.setManufacturer(manufacturer.equals("null")?"":manufacturer);
                            if(StringUtils.isNotBlank(deliverTime))
                            oInternetCard.setDeliverTime(DateUtils.parseDate(deliverTime,dateFormat));
                            oInternetCard.setOrderId(orderId.equals("null")?"":orderId);
                            oInternetCard.setAgentName(agentName.equals("null")?"":agentName);
                            oInternetCard.setSnNum(snNum.equals("null")?"":snNum);
                            oInternetCard.setIccidNum(iccidNum.equals("null")?"":iccidNum);
                            oInternetCard.setConsignee(consignee.equals("null")?"":consignee);
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
                            if(StringUtils.isNotBlank(deliverTime))
                            oInternetCard.setDeliverTime(DateUtils.parseDate(deliverTime,dateFormat));
                            oInternetCard.setBeginSn(beginSn.equals("null")?"":beginSn);
                            oInternetCard.setEndSn(endSn.equals("null")?"":endSn);
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
                            if(StringUtils.isNotBlank(deliverTime))
                            oInternetCard.setDeliverTime(DateUtils.parseDate(deliverTime,dateFormat));
                        }else if(importType.equals(CardImportType.E.getValue())){
                            String iccidNum = String.valueOf(string.size()>=1?string.get(0):"");//ICCID
                            String internetCardStatus = String.valueOf(string.size()>=2?string.get(1):"");//物联卡状态
                            String openAccountTime = String.valueOf(string.size()>=3?string.get(2):"");//开户日期
                            String merId = String.valueOf(string.size()>=4?string.get(3):"");//商户编号
                            String latelyPayTime = String.valueOf(string.size()>=5?string.get(4):"");//最近交易日期
                            String merName = String.valueOf(string.size()>=6?string.get(5):"");//商户名称
                            String agentName = String.valueOf(string.size()>=7?string.get(6):"");//代理商名称

                            oInternetCard.setIccidNum(iccidNum.equals("null")?"":iccidNum);
                            BigDecimal contentByMsg = InternetCardStatus.getContentByMsg(internetCardStatus);
                            if(contentByMsg==null){
                                contentByMsg = InternetCardStatus.UNKNOWN.getValue();
                            }
                            oInternetCard.setInternetCardStatus(contentByMsg);
                            oInternetCard.setLatelyPayTime(latelyPayTime.equals("null")?"":latelyPayTime);
                            if(StringUtils.isNotBlank(openAccountTime))
                            oInternetCard.setOpenAccountTime(DateUtils.parseDate(openAccountTime,dateFormat));
                            oInternetCard.setMerId(merId.equals("null")?"":merId);
                            oInternetCard.setMerName(merName.equals("null")?"":merName);
                            oInternetCard.setAgentName(agentName.equals("null")?"":agentName);
                        }
                        String jsonList = JsonUtil.objectToJson(oInternetCard);
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
            List<OInternetCardImport> oInternetCardImports = internetCardImportMapper.selectByExample(oInternetCardImportExample);
            for (OInternetCardImport oInternetCardImport : oInternetCardImports) {
                try {
                    log.info("analysisImport处理导入表数据,oInternetCardImport:{}",oInternetCardImport.toString());
                    String importType = oInternetCardImport.getImportType();
                    OInternetCard internetCard = JsonUtil.jsonToPojo(oInternetCardImport.getImportMsg(), OInternetCard.class);
                    if(importType.equals(CardImportType.A.getValue()) || importType.equals(CardImportType.B.getValue()) || importType.equals(CardImportType.E.getValue())){
                        disposeInternetCard(oInternetCardImport,internetCard);
                    }else if(importType.equals(CardImportType.C.getValue())){
                        if(StringUtils.isBlank(internetCard.getBeginSn()) || StringUtils.isBlank(internetCard.getSnCount())){
                            oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
                            oInternetCardImport.setErrorMsg("缺少iccid开始号段或总数量");
                            //更新导入记录
                            updateInternetCardImport(oInternetCardImport);
                            continue;
                        }
                        List<String> iccidList = logisticsService.idList(internetCard.getBeginSn(), StringUtils.isBlank(internetCard.getEndSn())?internetCard.getBeginSn():internetCard.getEndSn());
                        if(iccidList.size()!=Integer.parseInt(RegexUtil.rvZeroAndDot(internetCard.getSnCount()))){
                            oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
                            oInternetCardImport.setErrorMsg("iccid号段与数量不匹配");
                            //更新导入记录
                            updateInternetCardImport(oInternetCardImport);
                            continue;
                        }
                        for (String iccId : iccidList) {
                            internetCard.setIccidNum(iccId);
                            disposeInternetCard(oInternetCardImport,internetCard);
                        }
                    }else if(importType.equals(CardImportType.D.getValue())){
                        if(StringUtils.isBlank(internetCard.getBeginSn()) || StringUtils.isBlank(internetCard.getSnCount())){
                            oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
                            oInternetCardImport.setErrorMsg("缺少SN开始号段或总数量");
                            //更新导入记录
                            updateInternetCardImport(oInternetCardImport);
                            continue;
                        }
                        List<String> snList = logisticsService.idList(internetCard.getBeginSn(), StringUtils.isBlank(internetCard.getEndSn())?internetCard.getBeginSn():internetCard.getEndSn());
                        if(snList.size()!=Integer.parseInt(RegexUtil.rvZeroAndDot(internetCard.getSnCount()))){
                            oInternetCardImport.setImportStatus(OInternetCardImportStatus.FAIL.getValue());
                            oInternetCardImport.setErrorMsg("SN号段与数量不匹配");
                            //更新导入记录
                            updateInternetCardImport(oInternetCardImport);
                            continue;
                        }
                        internetCardService.disposeSn(snList,internetCard,oInternetCardImport);
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
    public void disposeInternetCard(OInternetCardImport oInternetCardImport,OInternetCard internetCard)throws Exception{
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
        }
        if(StringUtils.isNotBlank(internetCard.getAgentName())){
            AgentExample agentExample = new AgentExample();
            AgentExample.Criteria criteria1 = agentExample.createCriteria();
            criteria1.andStatusEqualTo(Status.STATUS_1.status);
            criteria1.andAgNameEqualTo(internetCard.getAgentName());
            List<Agent> agents = agentMapper.selectByExample(agentExample);
            if(agents.size()!=0){
                Agent agent = agents.get(0);
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
    public void disposeSn(List<String> snList,OInternetCard internetCard,OInternetCardImport oInternetCardImport)throws MessageException{
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
        internetCardMapper.insert(internetCard);
    }

    public void updateInternetCard(OInternetCard internetCard)throws MessageException{
        if(StringUtils.isBlank(internetCard.getIccidNum())){
            throw new MessageException("iccid为空");
        }
        internetCard.setuTime(new Date());
        int i = internetCardMapper.updateByPrimaryKeySelective(internetCard);
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
     * 1. 检测是否续费为否，状态为正常的，当月的，更新是否续费为是
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
            Map<String,Object> reqMap = new HashMap<>();
            reqMap.put("renew",Status.STATUS_0.status);//否
            reqMap.put("newRenew",Status.STATUS_1.status);
            reqMap.put("internetCardStatus",InternetCardStatus.NORMAL.getValue());
            reqMap.put("expireTime",DateUtil.getPerFirstDayOfMonth());
            int i = internetCardMapper.selectInternetCardExpireCount(reqMap);
            if(i>0){
                int updateCount = internetCardMapper.updateInternetCardExpire(reqMap);
                log.info("taskDisposeInternetCard检测是否续费,本次更次了数据条数:{}",updateCount);
            }
            log.info("taskDisposeInternetCard检测是否续费,暂无更新数据:{}",i);
            OInternetCardImportExample oInternetCardImportExample = new OInternetCardImportExample();
            OInternetCardImportExample.Criteria criteria = oInternetCardImportExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andImportStatusEqualTo(OInternetCardImportStatus.UNTREATED.getValue());
            List<OInternetCardImport> oInternetCardImports = internetCardImportMapper.selectByExample(oInternetCardImportExample);
            if(oInternetCardImports.size()==0){
                log.info("taskDisposeInternetCard暂无未处理,退出");
                return;
            }
            for (OInternetCardImport oInternetCardImport : oInternetCardImports) {
                if(StringUtils.isBlank(oInternetCardImport.getBatchNum())){
                    log.info("taskDisposeInternetCard处理未处理的导入记录，批次号未空");
                    continue;
                }
                log.info("taskDisposeInternetCard处理未处理的导入记录，批次号:{}",oInternetCardImport.getBatchNum());
                analysisImport(oInternetCardImport.getBatchNum());
            }
        } finally {
            if(StringUtils.isNotBlank(retIdentifier)){
                redisService.releaseLock(RedisCachKey.TASK_DISPOSEIN_TERNET_CARD.code, retIdentifier);
            }
        }
    }

    /**
     * 定时任务
     * 1. 查询为空的商户信息更新
     * 2. 更新已修改的商户信息（是续费，正常）
     */
    @Autowired
    public void taskUpdateMech(){
        try {
            //1. 查询为空的商户信息更新
            OInternetCardExample oInternetCardExample = new OInternetCardExample();
            OInternetCardExample.Criteria criteria = oInternetCardExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andMerIdIsNull();
            criteria.andMerNameIsNull();
            List<OInternetCard> internetCards = internetCardMapper.selectByExample(oInternetCardExample);
            for (OInternetCard internetCard : internetCards) {
                OInternetCardMerch oInternetCardMerch = internetCardMerchMapper.selectChnTermposi(BigDataEncode.encode(internetCard.getIccidNum()));
                if(null!=oInternetCardMerch){
                    internetCard.setMerId(oInternetCardMerch.getChnMerchId());
                    internetCard.setMerName(oInternetCardMerch.getMerchName());
                    int i = internetCardMapper.updateByPrimaryKeySelective(internetCard);
                    if(i!=1){
                        log.error("为空定时任务更新商户信息失败:IccidNum:{},商户编号:{},商户名称:{}",internetCard.getIccidNum(),oInternetCardMerch.getChnMerchId(),oInternetCardMerch.getMerchName());
                    }
                }
            }

            //2. 更新已修改的商户信息（是续费，正常）
            Map<String,Object> reqMap = new HashMap<>();
            reqMap.put("renew",Status.STATUS_0.status);//否
            reqMap.put("newRenew",Status.STATUS_1.status);//是续费
            reqMap.put("internetCardStatus",InternetCardStatus.NORMAL.getValue());
            reqMap.put("expireTime",DateUtil.getPerFirstDayOfMonth());
            List<OInternetCard> oInternetCards = internetCardMapper.selectInternetCardRenew(reqMap);
            for (OInternetCard oInternetCard : oInternetCards) {
                OInternetCardMerch oInternetCardMerch = internetCardMerchMapper.selectChnTermposi(BigDataEncode.encode(oInternetCard.getIccidNum()));
                if(null!=oInternetCardMerch){
                    //之前未获取当商户信息的直接更新
                    if(StringUtils.isBlank(oInternetCard.getMerId()) || StringUtils.isBlank(oInternetCard.getMerName())){
                        oInternetCard.setMerId(oInternetCardMerch.getChnMerchId());
                        oInternetCard.setMerName(oInternetCardMerch.getMerchName());
                        int i = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
                        if(i!=1){
                            log.error("1定时任务更新商户信息失败:IccidNum:{},商户编号:{},商户名称:{}",oInternetCard.getIccidNum(),oInternetCardMerch.getChnMerchId(),oInternetCardMerch.getMerchName());
                        }
                        continue;
                    }
                    //只要有一个不相等就执行更新操作
                    if(!oInternetCardMerch.getChnMerchId().equals(oInternetCard.getMerId()) || !oInternetCardMerch.getMerchName().equals(oInternetCard.getMerName())){
                        oInternetCard.setMerId(oInternetCardMerch.getChnMerchId());
                        oInternetCard.setMerName(oInternetCardMerch.getMerchName());
                        int i = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
                        if(i!=1){
                            log.error("2定时任务更新商户信息失败:IccidNum:{},商户编号:{},商户名称:{}",oInternetCard.getIccidNum(),oInternetCardMerch.getChnMerchId(),oInternetCardMerch.getMerchName());
                        }
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
