package com.ryx.credit.profit.jobs;

import com.ryx.credit.common.enumc.GetMethod;
import com.ryx.credit.profit.service.IContributionsService;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 代理商缴纳款数据获取
 * @Author chenqiutian
 * @Create 2019/1/24
 *
 */
@Service
public class ContributionsJob {

    private static Logger logger = LoggerFactory.getLogger(ContributionsJob.class);

    @Autowired
    private IPaymentDetailService iPaymentDetailService;
    @Autowired
    private IContributionsService contributionsServiceImpl;


    /**
     * 每月6日凌晨1点
     */
    @Scheduled(cron = "0 0 1 6 * ?")
    public void getEarnestMoney(){
        //获取系统当前时间
        String deductDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE).substring(0,7);
        try{

            List<Map<String,Object>> list = iPaymentDetailService.getShareMoney(GetMethod.ALLCAPITAL.code,"",deductDate);
            logger.info("从订单系统，获取到缴纳款数据总计：{}条",list != null && !list.isEmpty() ?list.size() :0 );

            if(list != null && !list.isEmpty()){
                deductDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_LOCAL_DATE).substring(0,7);
                deductDate = deductDate.replaceAll("-","");
                //保存数据
                contributionsServiceImpl.batchInsertDeduction(list,deductDate);
            }
            logger.info("获取0条");
        }catch (Exception e){
            logger.error("初始化缴纳款数据失败"+e.getMessage());
           e.printStackTrace();
        }

    }


}
