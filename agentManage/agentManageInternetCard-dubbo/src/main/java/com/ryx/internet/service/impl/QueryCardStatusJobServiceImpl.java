package com.ryx.internet.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.MailUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.internet.dao.InternetLogoutDetailMapper;
import com.ryx.internet.dao.OInternetCardMapper;
import com.ryx.internet.pojo.InternetLogoutDetail;
import com.ryx.internet.pojo.InternetLogoutDetailExample;
import com.ryx.internet.pojo.OInternetCard;
import com.ryx.internet.pojo.OInternetCardExample;
import com.ryx.internet.service.QueryCardStatusJobService;
import com.ryx.internet.service.impl.api.ChinaMobileForJYHttpReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/***
 * 揭阳移动同步更新状态Job
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/12/9 16:12
 * @Param
 * @return
 **/
@Service("queryCardStatusJobService")
public class QueryCardStatusJobServiceImpl implements QueryCardStatusJobService {

    private static Logger log = LoggerFactory.getLogger(QueryCardStatusJobServiceImpl.class);
    @Autowired
    private OInternetCardMapper internetCardMapper;
    @Autowired
    private InternetLogoutDetailMapper internetLogoutDetailMapper;


    /**
     * 查询要更新状态的数据
     * @param type
     * @return
     */
    @Override
    public List<OInternetCard> fetchDataUpdateCardStatus(String type){
        log.info("fetchDataUpdateCardStatus查询流量卡更新卡状态开始");
        Map<String,Object> reqMap = new HashMap<>();
        List<String> expireTimeList = new ArrayList<>();
        expireTimeList.add(DateUtil.getPerDayOfMonth(0));
        expireTimeList.add(DateUtil.getPerDayOfMonth(1));
        expireTimeList.add(DateUtil.getPerDayOfMonth(2));
        expireTimeList.add(DateUtil.getPerDayOfMonth(3));
        reqMap.put("expireTimeList",expireTimeList);
        reqMap.put("issuer",Issuerstatus.JY_MOBILE.getValue());
        reqMap.put("type",type);
        if(!type.equals("selectNull")){
            reqMap.put("tasksStatusTime",DateUtil.format(new Date(),DateUtil.DATE_FORMAT_yyyy_MM_dd));
        }
        List<OInternetCard> internetCards = internetCardMapper.selectUpdateCardStatus(reqMap, new Page(0, 100));
        log.info("fetchDataUpdateCardStatus查询流量卡更新卡状态数量为:{}",internetCards.size());
        return internetCards;
    }


    @Override
    public void processDataUpdateCardStatus(List<OInternetCard> internetCardList){

        log.info("揭阳移动返回数据处理,开始");
        StringBuffer iccids = new StringBuffer();
        for (OInternetCard oInternetCard : internetCardList) {
            iccids.append(oInternetCard.getIccidNum());
            iccids.append(",");
        }
        log.info("揭阳移动返回数据处理,iccids：{}",iccids);
        String mobileResult = ChinaMobileForJYHttpReq.batchQueryCardStatus(iccids.toString().substring(0, iccids.toString().length() - 1));
        if(StringUtils.isBlank(mobileResult)){
            log.info("揭阳移动返回数据处理,接口请求异常");
            AppConfig.sendEmails("接口请求异常：mobileResult:"+mobileResult, "物联网移动数据异常,方法processDataUpdateCardStatus");
            return;
        }
        JSONObject jsonObj = JSONObject.parseObject(mobileResult);
        String code = jsonObj.getString("code");
        //所有非0返回结果码均表示请求处理失败
        if(!code.equals("0")){
            log.info("揭阳移动返回数据处理,code!=0,error:{}",jsonObj.getString("error"));
            return;
        }

        JSONObject jsonData = JSONObject.parseObject(jsonObj.getString("data"));
        JSONObject statusList = JSONObject.parseObject(jsonData.getString("statusList"));
        List<Map<String,String>> resultListMap = (List)statusList.getJSONArray("list");
        StringBuffer sb = new StringBuffer();
        for (Map<String, String> resultMap : resultListMap) {
            try {
                String iccid = resultMap.get("iccid");
                OInternetCard oInternetCard = internetCardMapper.selectByPrimaryKey(iccid);
                if(null==oInternetCard){
                    AppConfig.sendEmails("物联网移动返回：iccid:"+iccid+",不存在", "物联网移动数据异常,方法processDataUpdateCardStatus");
                    continue;
                }
                //查询结果：0 成功；1 失败
                if(resultMap.get("code").equals("0")){
                    String status = resultMap.get("status");
                    String statusTime = resultMap.get("statusTime");
                    BigDecimal cardStatusByJYMobile = InternetCardStatus.getCardStatusByJYMobile(status);
                    if(cardStatusByJYMobile.compareTo(new BigDecimal(-1))==0){
                        AppConfig.sendEmails("物联网移动返回：iccid:"+iccid+",status:+"+status+"状态不存在", "物联网移动数据异常,方法processDataUpdateCardStatus");
                        continue;
                    }
                    if(null!=oInternetCard.getInternetCardStatus() && StringUtils.isNotBlank(oInternetCard.getStatusTime())
                       && cardStatusByJYMobile.compareTo(oInternetCard.getInternetCardStatus())==0 && oInternetCard.getStatusTime().equals(status)){
                        log.info("揭阳移动返回数据处理,状态一致无需更新");
                        continue;
                    }
                    if(oInternetCard.getInternetCardStatus().equals(InternetCardStatus.LOGOUT.getValue())
                            && cardStatusByJYMobile.compareTo(InternetCardStatus.LOGOUT.getValue())!=0){
                        // 已经是注销状态了，不再更新， 明细也不需要更新
                        log.info("物联网卡已经是注销状态，不用再更新"+oInternetCard.getIccidNum());
                        continue;
                    }
                    oInternetCard.setInternetCardStatus(cardStatusByJYMobile);// 修改卡状态
                    if(StringUtils.isNotBlank(statusTime)){
                        oInternetCard.setStatusTime(statusTime);
                    }
                    // 如果是注销状态  更新申请注销明细
                    if(cardStatusByJYMobile.compareTo(InternetCardStatus.LOGOUT.getValue())==0){
                        InternetLogoutDetailExample internetLogoutDetailExample = new InternetLogoutDetailExample();
                        InternetLogoutDetailExample.Criteria criteria = internetLogoutDetailExample.createCriteria();
                        criteria.andStatusEqualTo(Status.STATUS_1.status);
                        criteria.andIccidNumEqualTo(oInternetCard.getIccidNum());
                        criteria.andLogoutStatusEqualTo(InternetLogoutStatus.DZX.getValue());// 已注销 更改为 已注销
                        List<InternetLogoutDetail> internetLogoutDetails = internetLogoutDetailMapper.selectByExample(internetLogoutDetailExample);
                        for (InternetLogoutDetail internetLogoutDetail : internetLogoutDetails) {
                            internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.ZXCG.getValue());
                            internetLogoutDetailMapper.updateByPrimaryKey(internetLogoutDetail);
                        }
                        oInternetCard.setRenew(Status.STATUS_0.status);
                        oInternetCard.setRenewStatus(InternetRenewStatus.YZX.getValue());
                    }
                }else{
                    if(iccid.length()==20){
                        String error = resultMap.get("error");
                        sb.append("iccid:"+iccid+",error:"+error+"\n");
                    }
                }
                oInternetCard.setTaskStatusTime(new Date());
                int i = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
                if(i!=1){
                    AppConfig.sendEmails("物联网移动返回：iccid:"+iccid+",更新处理失败", "物联网移动数据异常,方法processDataUpdateCardStatus");
                    continue;
                }
            } catch (Exception e) {
                AppConfig.sendEmails(MailUtil.printStackTrace(e), "物联网移动数据异常,方法processDataUpdateCardStatus");
                e.printStackTrace();
            }
        }
        if(StringUtils.isNotBlank(sb)){
            AppConfig.sendEmails(sb.toString(), "物联网移动数据异常,方法processDataUpdateCardStatus");
        }
        log.info("揭阳移动返回数据处理,结束");
    }


}
