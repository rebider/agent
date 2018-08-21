package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.profit.dao.PAgentPidLinkMapper;
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.dao.TransProfitDetailMapper;
import com.ryx.credit.profit.pojo.PAgentPidLink;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 小汇
 */
@Service
public class ProfitSummaryDataJob {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private TransProfitDetailMapper transProfitDetailMapper;
    @Autowired
    private ProfitDetailMonthMapper detailMonthMapper;
    private int index = 1;


    public static void main(String agrs[]){
        HashMap<String,String> map = new HashMap<String,String>();
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.link"),params);
        System.out.println(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            //logger.error("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败","日分润同步失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data,List.class);
        System.out.println(data);
    }

    public void excute(){
        MPos_Summary(null);
    }

    public void MPos_Summary(String transDate){
        //
        transDate = transDate==null?DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1)).substring(0,6):transDate;
        List<TransProfitDetail> details = transProfitDetailMapper.selectListByDate(transDate);//手刷同步过来的小汇数据
        for(TransProfitDetail detail:details){
            ProfitDetailMonth where = new ProfitDetailMonth();
            where.setAgentId(detail.getAgentId());
            where.setParentAgentId(detail.getParentAgentId());
            where.setProfitDate(transDate);
            ProfitDetailMonth detailMonth = detailMonthMapper.selectByIdAndParent(where);
            if(null==detailMonth){//新增汇总
                
            }else{//更新汇总

            }
        }
    }

}
