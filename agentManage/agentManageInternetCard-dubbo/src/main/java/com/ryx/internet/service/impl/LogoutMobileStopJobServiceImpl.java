package com.ryx.internet.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.MailUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.internet.dao.InternetLogoutDetailMapper;
import com.ryx.internet.dao.OInternetCardMapper;
import com.ryx.internet.pojo.InternetLogoutDetail;
import com.ryx.internet.pojo.InternetLogoutDetailExample;
import com.ryx.internet.pojo.OInternetCard;
import com.ryx.internet.service.LogoutMobileStopJobService;
import com.ryx.internet.service.impl.api.ChinaMobileForJYHttpReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/***
 * 揭阳移动通知移动关停Job
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/12/9 16:12
 * @Param
 * @return
 **/
@Service("logoutMobileStopJobService")
public class LogoutMobileStopJobServiceImpl implements LogoutMobileStopJobService {

    private static Logger log = LoggerFactory.getLogger(LogoutMobileStopJobServiceImpl.class);
    @Autowired
    private OInternetCardMapper internetCardMapper;
    @Autowired
    private InternetLogoutDetailMapper internetLogoutDetailMapper;
    @Autowired
    private RedisService redisService;


    @Override
    public void logoutMobileStopJob(){

        log.info("logoutMobileStopJob申请注销明细通知移动关停开始");
        String retIdentifier = "";
        try {
            retIdentifier = redisService.lockWithTimeout(RedisCachKey.LOGOUT_MOBILE_STOP_JOB.code, RedisService.ACQUIRE_TIME_OUT, RedisService.TIME_OUT);
            if (StringUtils.isBlank(retIdentifier)) {
                log.info("申请注销明细通知移动关停重复执行");
                AppConfig.sendEmails("申请注销明细通知移动关停，定时任务重复执行", "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                return;
            }
            InternetLogoutDetailExample internetLogoutDetailExample = new InternetLogoutDetailExample();
            InternetLogoutDetailExample.Criteria criteria = internetLogoutDetailExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andLogoutStatusEqualTo(InternetLogoutStatus.TJCLZ.getValue());
            internetLogoutDetailExample.setOrderByClause(" c_time asc ");
            internetLogoutDetailExample.setPage(new Page(0,20));
            List<InternetLogoutDetail> internetLogoutDetails = internetLogoutDetailMapper.selectByExample(internetLogoutDetailExample);
            for (InternetLogoutDetail internetLogoutDetail : internetLogoutDetails) {
                try {
                    if(StringUtils.isBlank(internetLogoutDetail.getInternetCardNum())){
                        log.info("logoutMobileStopJob申请注销明细通知移动，缺少物联网卡号，iccid：{}",internetLogoutDetail.getIccidNum());
                        AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，缺少物联网卡号，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                        continue;
                    }
                    String mobileResult = ChinaMobileForJYHttpReq.msisdnSwitch(internetLogoutDetail.getInternetCardNum(), JyMobileOptType.STOP.getValue());
                    OInternetCard oInternetCard = internetCardMapper.selectByPrimaryKey(internetLogoutDetail.getIccidNum());
                    if(StringUtils.isBlank(mobileResult)){
                        log.info("logoutMobileStopJob申请注销明细通知移动，mobileResult：{}",mobileResult);
                        internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.TJSB.getValue());
                        internetLogoutDetail.setFailCause("请求异常,结果未知");
                        internetLogoutDetail.setuTime(new Date());
                        int i = internetLogoutDetailMapper.updateByPrimaryKey(internetLogoutDetail);
                        if(i!=1){
                            AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常1，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                            continue;
                        }
                        oInternetCard.setRenewStatus(InternetRenewStatus.WXF.getValue());
                        oInternetCard.setuTime(new Date());
                        int k = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
                        if(k!=1){
                            AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常2，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                            continue;
                        }
                    }
                    JSONObject jsonObj = JSONObject.parseObject(mobileResult);
                    String code = jsonObj.getString("code");
                    //所有非0返回结果码均表示请求处理失败
                    if(!code.equals("0")){
                        log.info("logoutMobileStopJob申请注销明细通知移动,code!=0,error:{}",jsonObj.getString("error"));
                        internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.TJSB.getValue());
                        internetLogoutDetail.setFailCause(jsonObj.getString("error"));
                        internetLogoutDetail.setuTime(new Date());
                        int i = internetLogoutDetailMapper.updateByPrimaryKey(internetLogoutDetail);
                        if(i!=1){
                            AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常3，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                            continue;
                        }
                        oInternetCard.setRenewStatus(InternetRenewStatus.WXF.getValue());
                        oInternetCard.setuTime(new Date());
                        int k = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
                        if(k!=1){
                            AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常4，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                            continue;
                        }
                    }else {
                        JSONObject jsonData = JSONObject.parseObject(jsonObj.getString("data"));
                        String dataCode = jsonData.getString("code");
                        if(dataCode.equals("0")){
                            internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.DZX.getValue());
                            oInternetCard.setRenewStatus(InternetRenewStatus.YZX.getValue());
                        }else{
                            internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.TJSB.getValue());
                            oInternetCard.setRenewStatus(InternetRenewStatus.WXF.getValue());
                        }
                        if(StringUtils.isNotBlank(jsonData.getString("orderNo")))
                            internetLogoutDetail.setMobileOrderNo(jsonData.getString("orderNo"));
                        if(StringUtils.isNotBlank(jsonData.getString("error")))
                            internetLogoutDetail.setFailCause(jsonData.getString("error"));
                        internetLogoutDetail.setuTime(new Date());
                        int i = internetLogoutDetailMapper.updateByPrimaryKey(internetLogoutDetail);
                        if(i!=1){
                            AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常5，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                            continue;
                        }
                        oInternetCard.setuTime(new Date());
                        int k = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
                        if(k!=1){
                            AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常6，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                            continue;
                        }
                    }
                } catch (Exception e) {
                    AppConfig.sendEmails(MailUtil.printStackTrace(e), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                    e.printStackTrace();
                }
            }
        }finally {
            if(StringUtils.isNotBlank(retIdentifier)){
                redisService.releaseLock(RedisCachKey.LOGOUT_MOBILE_STOP_JOB.code, retIdentifier);
            }
        }
        log.info("logoutMobileStopJob申请注销明细通知移动关停结束");
    }

}
