package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.profit.dao.PAgentPidLinkMapper;
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.dao.TransProfitDetailMapper;
import com.ryx.credit.profit.pojo.PAgentPidLink;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 汇总
 */
@Service
public class ProfitSummaryDataJob {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private TransProfitDetailMapper transProfitDetailMapper;
    @Autowired
    private ProfitDetailMonthMapper detailMonthMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentService agentService;
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
        transDate = transDate==null?DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1)).substring(0,6):transDate;
        List<TransProfitDetail> details = transProfitDetailMapper.selectListByDate(transDate);//手刷同步过来的小汇数据
        for(TransProfitDetail detail:details){
            Agent agent = agentService.getAgentById(detail.getAgentId());
            ProfitDetailMonth where = new ProfitDetailMonth();
            where.setAgentId(detail.getAgentId());
            where.setParentAgentId(detail.getParentAgentId());
            where.setProfitDate(transDate);
            ProfitDetailMonth detailMonth = detailMonthMapper.selectByIdAndParent(where);
            if(null==detailMonth){
                detailMonth = new ProfitDetailMonth();
            }
            detailMonth.setProfitDate(transDate);
            BigDecimal transAmt = detailMonth.getTranAmt()==null?BigDecimal.ZERO:detailMonth.getTranAmt();
            detailMonth.setTranAmt(detail.getInTransAmt().add(transAmt));
            if(detail.getBusCode().equals("0001")){//瑞银信
                detailMonth.setRyxProfitAmt(detail.getProfitAmt());
            }else if(detail.getBusCode().equals("3000")){//瑞刷活动
                detailMonth.setRsHdProfitAmt(detail.getProfitAmt());
            }else if(detail.getBusCode().equals("4000")){//瑞众通
                //detailMonth
            }else if(detail.getBusCode().equals("5000")){//瑞和宝
                BigDecimal rhbDay = null;//瑞和宝日结分润
                rhbDay = rhbDay==null?BigDecimal.ZERO:rhbDay;
                detailMonth.setRhbProfitAmt(detail.getProfitAmt().subtract(rhbDay));
            }else if(detail.getBusCode().equals("6000")){//直发
                detailMonth.setZfProfitAmt(detail.getProfitAmt());
            }else if(detail.getBusCode().equals("2000")){//瑞刷
                detailMonth.setRsProfitAmt(detail.getProfitAmt());
            }else if(detail.getBusCode().equals("1111")){//瑞银信活动
                detailMonth.setRyxHdProfitAmt(detail.getProfitAmt());
            }else {//贴牌
                detailMonth.setTpProfitAmt(detail.getProfitAmt());
            }

            if(null==detailMonth){//新增汇总
                detailMonth.setId(idService.genId(TabId.P_PROFIT_DETAIL_M));
                detailMonth.setAgentName(agent.getAgName());
                detailMonth.setAgentId(detail.getAgentId());
                detailMonth.setParentAgentId(detail.getParentAgentId());
                detailMonth.setAgentPid(detail.getAgentId());
                detailMonthMapper.insertSelective(detailMonth);

            }else{//更新汇总
                detailMonthMapper.updateByPrimaryKeySelective(detailMonth);
            }
        }
    }

}
