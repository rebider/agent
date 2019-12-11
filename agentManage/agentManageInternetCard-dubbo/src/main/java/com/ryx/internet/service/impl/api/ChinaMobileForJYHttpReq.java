package com.ryx.internet.service.impl.api;

import com.ryx.credit.common.enumc.JyMobileOptType;
import com.ryx.credit.common.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/***
 * 揭阳移动接口
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/12/9 11:34
 * @Param
 * @return
 **/
public class ChinaMobileForJYHttpReq {

    private final static String appKey = "cbjyup47ic";//
    private final static String secretKey = "1568dd3b0b5bd5bf50516839edb60337"; //密钥
    private final static String ryxCode = "2001841663"; //集团编码
    private final static String version = "3.0"; //接口版本
    private final static String format = "json"; //通信报文格式
    private final static String JY_MOBILE_CARD_URL = "http://120.197.89.173:8081/openapi/router";

    private static Logger log = LoggerFactory.getLogger(ChinaMobileForJYHttpReq.class);

    /**
     * 获取报文流水号
     * @return
     */
    private static String getTransId(){
        String dateTime = DateUtil.format(new Date(), DateUtil.DATE_FORMAT_6);
        Random random = new Random();
        int end = random.nextInt(9999);
        //如果不足四位前面补0
        String randomNum = String.format("%04d", end);
        return ryxCode+dateTime+randomNum;
    }

    /**
     * 批量查询卡状态
     * @param iccids
     * @return
     */
    public static String batchQueryCardStatus(String iccids){
        try {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appKey",appKey);
            paramMap.put("format",format);
            paramMap.put("method","triopi.member.lifecycle.batch.query");
            paramMap.put("transID",getTransId());
            paramMap.put("v",version);
            paramMap.put("iccids",iccids);
            String sign = ApiUtils.sign(paramMap, secretKey);
            paramMap.put("sign",sign);
            paramMap= ApiUtils.sortMap(paramMap);
            log.info("揭阳移动接口查询状态请求参数：{}",paramMap);
            String result = HttpClientUtil.doPost(JY_MOBILE_CARD_URL, paramMap);
            String decrypt = DESUtils.decrypt(result, secretKey);
            log.info("揭阳移动接口查询状态返回参数：{}",decrypt);
            return decrypt;
        } catch (Exception e) {
            AppConfig.sendEmails(MailUtil.printStackTrace(e), "物联网移动接口请求异常,方法batchQueryCardStatus");
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 批量号码停开机
     * @param msisdns
     * @param optType
     * @return
     */
    public static String batchMsisdnSwitch(String msisdns,String optType){
        try {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appKey",appKey);
            paramMap.put("format",format);
            paramMap.put("method","triopi.business.member.batch.switch");
            paramMap.put("transID",getTransId());
            paramMap.put("v",version);
            paramMap.put("msisdns",msisdns);//物联卡号
            paramMap.put("optType",optType);
            String sign = ApiUtils.sign(paramMap, secretKey);
            paramMap.put("sign",sign);
            paramMap= ApiUtils.sortMap(paramMap);
            log.info("揭阳移动接口批量号码停开机请求参数：{}",paramMap);
            String result = HttpClientUtil.doPost(JY_MOBILE_CARD_URL, paramMap);
            String decrypt = DESUtils.decrypt(result, secretKey);
            log.info("揭阳移动接口批量号码停开机返回参数：{}",decrypt);
            return decrypt;
        } catch (Exception e) {
            AppConfig.sendEmails(MailUtil.printStackTrace(e), "物联网移动接口请求异常,方法batchIccidsSwitch");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据订单号查询结果
     * @param orderNo
     * @return
     */
    public static String queryByOrderNo(String orderNo){
        try {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appKey",appKey);
            paramMap.put("format",format);
            paramMap.put("method","triopi.business.orderno.query");
            paramMap.put("transID",getTransId());
            paramMap.put("v",version);
            paramMap.put("orderNo",orderNo);
            String sign = ApiUtils.sign(paramMap, secretKey);
            paramMap.put("sign",sign);
            paramMap= ApiUtils.sortMap(paramMap);
            log.info("揭阳移动接口根据订单号查询返回结果请求参数：{}",paramMap);
            String result = HttpClientUtil.doPost(JY_MOBILE_CARD_URL, paramMap);
            String decrypt = DESUtils.decrypt(result, secretKey);
            log.info("揭阳移动接口根据订单号查询返回结果返回参数：{}",decrypt);
            return decrypt;
        } catch (Exception e) {
            AppConfig.sendEmails(MailUtil.printStackTrace(e), "物联网移动接口请求异常,方法queryByOrderNo");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 单个号码停开机
     * @param msisdn
     * @param optType
     * @return
     */
    public static String msisdnSwitch(String msisdn,String optType){
        try {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appKey",appKey);
            paramMap.put("format",format);
            paramMap.put("method","triopi.business.member.switch");
            paramMap.put("transID",getTransId());
            paramMap.put("v",version);
            paramMap.put("msisdn",msisdn);//物联卡号
            paramMap.put("optType",optType);
            String sign = ApiUtils.sign(paramMap, secretKey);
            paramMap.put("sign",sign);
            paramMap= ApiUtils.sortMap(paramMap);
            log.info("揭阳移动接口批量号码停开机请求参数：{}",paramMap);
            String result = HttpClientUtil.doPost(JY_MOBILE_CARD_URL, paramMap);
            String decrypt = DESUtils.decrypt(result, secretKey);
            log.info("揭阳移动接口批量号码停开机返回参数：{}",decrypt);
            return decrypt;
        } catch (Exception e) {
            AppConfig.sendEmails(MailUtil.printStackTrace(e), "物联网移动接口请求异常,方法msisdnSwitch");
            e.printStackTrace();
        }
        return "";
    }


    public static void main(String[] args){
//        queryByOrderNo("B_OI_CARDOFF_20191211105335");

//        msisdnSwitch("1440420214213", JyMobileOptType.STOP.getValue());
        msisdnSwitch("1440420214213", JyMobileOptType.OPEN.getValue());

//        batchQueryCardStatus("898604421919C0184213");
    }


}
