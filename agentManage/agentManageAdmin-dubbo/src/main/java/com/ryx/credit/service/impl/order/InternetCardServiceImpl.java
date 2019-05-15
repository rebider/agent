package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.order.OInternetCardImportMapper;
import com.ryx.credit.dao.order.OInternetCardMapper;
import com.ryx.credit.pojo.admin.order.OInternetCard;
import com.ryx.credit.pojo.admin.order.OInternetCardExample;
import com.ryx.credit.pojo.admin.order.OInternetCardImport;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.InternetCardService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2018/12/4 14:38
 * @Param
 * @return
 **/
@Service("internetCardService")
public class InternetCardServiceImpl implements InternetCardService {

    @Autowired
    private OInternetCardMapper internetCardMapper;
    @Autowired
    private OInternetCardImportMapper internetCardImportMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private DictOptionsService dictOptionsService;

    private static final String[] dateFormat = new String[]{DateUtil.DATE_FORMAT_yyyy_MM_dd,DateUtil.DATE_FORMAT_yyyy_MM_dd2};

    @Override
    public PageInfo internetCardList(OInternetCard internetCard, Page page){

        OInternetCardExample oInternetCardExample = new OInternetCardExample();
        OInternetCardExample.Criteria criteria = oInternetCardExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        oInternetCardExample.setPage(page);
        oInternetCardExample.setOrderByClause(" c_time desc ");
        List<OInternetCard> oInternetCards = internetCardMapper.selectByExample(oInternetCardExample);
        for (OInternetCard oInternetCard : oInternetCards) {
//            Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(),oInternetCard.getProCom());
//            if(null!=dict)
//            oInternetCard.setProCom(dict.getdItemname());
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(oInternetCards);
        pageInfo.setTotal((int)internetCardMapper.countByExample(oInternetCardExample));
        return pageInfo;
    }



    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void importInternetCard(List<List<Object>> excelList, String importType, String userId)throws Exception{

        if(StringUtils.isBlank(CardImportType.getContentByValue(importType))){
             throw new MessageException("导入类型错误");
        }
        if(null==excelList && excelList.size()==0){
            throw new MessageException("excel列表为空");
        }
        try {
            int index = 1;
            List<OInternetCard> cardList = new ArrayList<>();
            String batchNo = IDUtils.getBatchNo();
            OInternetCard oInternetCard = new OInternetCard();
            for (List<Object> object : excelList) {
                if(importType.equals(CardImportType.A.getValue())){
                    String issuer = String.valueOf(object.get(0));//发卡方
                    String InternetCardNum = String.valueOf(object.get(1));//物联卡号
                    String iccidNum = String.valueOf(object.get(2));//ICCID
                    String openAccountTime = String.valueOf(object.get(3));//开户日期

                    oInternetCard.setIccidNum(iccidNum);
                    oInternetCard.setIssuer(issuer);
                    oInternetCard.setInternetCardNum(InternetCardNum);
                    if(StringUtils.isNotBlank(openAccountTime))
                    oInternetCard.setOpenAccountTime(DateUtils.parseDate(openAccountTime,dateFormat));
                }else if(importType.equals(CardImportType.B.getValue())){
                    String consigner = String.valueOf(object.get(0));//发货方
                    String deliverTime = String.valueOf(object.get(1));//发货日期
                    String orderId = String.valueOf(object.get(2));//订单号
                    String agentName = String.valueOf(object.get(3));//代理商名称
                    String snNum = String.valueOf(object.get(4));//机具SN
                    String iccidNum = String.valueOf(object.get(5));//iccid
                    String consignee = String.valueOf(object.get(6));//收货人

                    oInternetCard.setConsigner(consigner);
                    if(StringUtils.isNotBlank(deliverTime))
                    oInternetCard.setDeliverTime(DateUtils.parseDate(deliverTime,dateFormat));
                    oInternetCard.setOrderId(orderId);
                    oInternetCard.setAgentName(agentName);
                    oInternetCard.setSnNum(snNum);
                    oInternetCard.setIccidNum(iccidNum);
                    oInternetCard.setConsignee(consignee);
                }else if(importType.equals(CardImportType.C.getValue())){
                    String orderId = String.valueOf(object.get(0));//订单编号
                    String agentName = String.valueOf(object.get(1));//代理商名称
                    String snCount = String.valueOf(object.get(2));//数量
                    String deliverTime = String.valueOf(object.get(3));//发货日期
                    String beginSn = String.valueOf(object.get(4));//iccid开始号段
                    String endSn = String.valueOf(object.get(5));//iccid结束号段

                    oInternetCard.setOrderId(orderId);
                    oInternetCard.setAgentName(agentName);
                    oInternetCard.setSnCount(snCount);
                    if(StringUtils.isNotBlank(deliverTime))
                    oInternetCard.setDeliverTime(DateUtils.parseDate(deliverTime,dateFormat));
                    oInternetCard.setBeginSn(beginSn);
                    oInternetCard.setEndSn(endSn);
                }else if(importType.equals(CardImportType.D.getValue())){
                    String orderId = String.valueOf(object.get(0));//订单号
                    String agentName = String.valueOf(object.get(1));//公司名称
                    String manufacturer = String.valueOf(object.get(2));//厂家
                    String beginSn = String.valueOf(object.get(3));//机具sn起始编号
                    String endSn = String.valueOf(object.get(4));//机具sn终端编号
                    String snCount = String.valueOf(object.get(5));//数量
                    String deliverTime = String.valueOf(object.get(6));//发货日期

                    oInternetCard.setOrderId(orderId);
                    oInternetCard.setAgentName(agentName);
                    oInternetCard.setManufacturer(manufacturer);
                    oInternetCard.setBeginSn(beginSn);
                    oInternetCard.setEndSn(endSn);
                    oInternetCard.setSnCount(snCount);
                    if(StringUtils.isNotBlank(deliverTime))
                    oInternetCard.setDeliverTime(DateUtils.parseDate(deliverTime,dateFormat));
                }else if(importType.equals(CardImportType.E.getValue())){
                    String iccidNum = String.valueOf(object.get(0));//ICCID
                    String internetCardStatus = String.valueOf(object.get(1));//物联卡状态
                    String openAccountTime = String.valueOf(object.get(2));//开户日期
                    String merId = String.valueOf(object.get(3));//商户编号
                    String latelyPayTime = String.valueOf(object.get(4));//最近交易日期
                    String merName = String.valueOf(object.get(5));//商户名称
                    String agentName = String.valueOf(object.get(6));//代理商名称

                    oInternetCard.setIccidNum(iccidNum);
                    BigDecimal contentByMsg = InternetCardStatus.getContentByMsg(internetCardStatus);
                    if(contentByMsg==null){
                        contentByMsg = InternetCardStatus.UNKNOWN.getValue();
                    }
                    oInternetCard.setInternetCardStatus(contentByMsg);
                    oInternetCard.setLatelyPayTime(latelyPayTime);
                    if(StringUtils.isNotBlank(openAccountTime))
                    oInternetCard.setOpenAccountTime(DateUtils.parseDate(openAccountTime,dateFormat));
                    oInternetCard.setMerId(merId);
                    oInternetCard.setMerName(merName);
                    oInternetCard.setAgentName(agentName);
                }
                cardList.add(oInternetCard);
                String jsonList = JsonUtil.objectToJson(cardList);
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
            throw new MessageException("excel解析失败");
        }
    }

}
