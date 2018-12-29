package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.pojo.ProfitBalanceSerial;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.service.ProfitBalanceSerialService;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author zhaodw
 * @Title: BalanceSerialDealJob
 * @ProjectName agentManage
 * @Description: 出款流水回盘处理
 * @date 2018/7/2911:34
 */
@Service("balanceSerialDealJob")
@Transactional(rollbackFor=RuntimeException.class)
public class BalanceSerialDealJob {

    private static final Logger LOG = Logger.getLogger(BalanceSerialDealJob.class);

    private static final  String URL =  AppConfig.getProperty("balance_url");


    @Autowired
    private ProfitDetailMonthService profitDetailMonthServiceImpl;

    @Autowired
    private ProfitBalanceSerialService profitBalanceSerialServiceImpl;

//    @Scheduled(cron = "0 0 15 * * ?")
    public void deal() {
        LOG.info("获取出款失败数据。");
        JSONObject result =  getFailureList();
        if (result.containsKey("info") ) {
            JSONArray array = result.getJSONArray("info");
            LOG.info("修改出款流水状态。");
            updateBalanceSerail(array);
        }

    }

    /***
    * @Description:  修改出款失败流水状态
    * @Param:  array
    * @Author: zhaodw
    * @Date: 2018/8/29
    */
    private void updateBalanceSerail(JSONArray array) {
        array.forEach(json->{
                 ProfitBalanceSerial serial = new ProfitBalanceSerial();
                 serial.setBalanceId( ((JSONObject)json).getString("id"));
                serial = profitBalanceSerialServiceImpl.getProfitBalanceSerialById(serial.getBalanceId());
                if (serial != null) {
                    serial.setStatus("1");//出款失败
                    serial.setErrDesc(((JSONObject)json).getString("errDesc"));//失败原因
                    profitBalanceSerialServiceImpl.updateById(serial);

                    ProfitDetailMonth profitDetailMonth = new ProfitDetailMonth();
                    profitDetailMonth.setId(serial.getProfitId());
                    profitDetailMonth.setStatus("6");
                    profitDetailMonth.setRemark(((JSONObject)json).getString("errDesc"));
                    profitDetailMonthServiceImpl.update(profitDetailMonth);
                }
           }
        );
    }

    /***
     * @Description:  获取出款失败数据
     * @return:  返回的出款失败数据列表
     * @Author: zhaodw
     * @Date: 2018/7/29
     */
    private JSONObject getFailureList() {
        String payDate = LocalDate.now().plusDays(-1).format(DateTimeFormatter.BASIC_ISO_DATE);
        LOG.info("出款日期"+payDate);
        JSONObject param = new JSONObject();
        param.put("tranDate", payDate);
        param.put("flag", "12");
        String result = HttpClientUtil.doPostJson(URL, param.toJSONString());
        if (StringUtils.isNotBlank(result)) {
            return  JSONObject.parseObject(result);
        }
        return null;
    }
}
