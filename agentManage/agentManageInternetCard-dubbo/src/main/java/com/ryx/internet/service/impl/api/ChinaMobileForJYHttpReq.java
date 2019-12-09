package com.ryx.internet.service.impl.api;

import com.ryx.credit.common.util.ApiUtils;
import com.ryx.credit.common.util.DESUtils;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
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
    private final static String batchQueryCardUrl = "http://120.197.89.173:8081/openapi/router";

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
     * 请求
     * @param iccids
     * @return
     */
    public static String batchQueryCardStatus(String iccids){
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
        String result = HttpClientUtil.doPost(batchQueryCardUrl, paramMap);
        String decrypt = DESUtils.decrypt(result, secretKey);
        log.info("揭阳移动接口查询状态返回参数：{}",decrypt);
        return decrypt;
    }


    public static void main(String[] args){
        System.out.println(batchQueryCardStatus("898604051918918485771"));
    }


}
