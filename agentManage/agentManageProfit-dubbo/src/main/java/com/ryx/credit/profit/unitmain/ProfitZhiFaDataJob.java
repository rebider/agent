package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.profit.pojo.ProfitDay;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitDirect;
import com.ryx.credit.profit.service.IProfitDService;
import com.ryx.credit.profit.service.IProfitDirectService;
import com.ryx.credit.profit.service.ProfitComputerService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.omg.CosNaming.BindingHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 直发分润数据同步、定时
 */
public class ProfitZhiFaDataJob {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private IProfitDirectService profitDirectService;
    @Autowired
    private ProfitComputerService computerService;
    @Autowired
    private IdService idService;


    public static void main(String agrs[]){
        /*String transDate = null;
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("transDate",transDate==null?DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1)).substring(0,6):transDate);
        map.put("pageNumber","1");
        map.put("pageSize","20");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.zhifa"),params);
        System.out.println(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            //logger.error("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败","日分润同步失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<HashMap> list = JSONObject.parseObject(data,List.class);
        System.out.println(data);*/
        BigDecimal a = new BigDecimal("58773.32");
        BigDecimal b = new BigDecimal("58773.32");
        System.out.println(b.compareTo(a));
    }

    /**
     * 同步直发分润数据
     * @param transDate 交易月份（空则为上一月）
     */
    public void synchroProfitDirect(String transDate){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("transDate",transDate==null?DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1)).substring(0,6):transDate);
        map.put("pageNumber","1");
        map.put("pageSize","20");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.zhifa"),params);
        System.out.println(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            logger.error("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败","日分润同步失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<HashMap> list = JSONObject.parseObject(data,List.class);
        try {
            insertProfitDirect(list);
        } catch (Exception e) {
            logger.error("同步插入数据失败！");
            e.printStackTrace();
        }
    }

    public void insertProfitDirect(List<HashMap> profitDirects){
        for(HashMap map:profitDirects){
            ProfitDirect where = new ProfitDirect();
            where.setAgentId((String)map.get("AGENTID"));
            where.setTransMonth((String)map.get("TRANSMONTH"));
            ProfitDirect profitDirect = profitDirectService.selectByAgentAndMon(where);
            long subLong = profitDirectService.getSubBuckleByMonth(where);
            BigDecimal total_day2 = computerService.total_day2((String)map.get("AGENTID"),(String)map.get("TRANSMONTH"));//日结分润
            BigDecimal profit = (BigDecimal) map.get("PROFITAMT");//直发分润
            BigDecimal tax = profit.add(total_day2).multiply(new BigDecimal("0.06"));//应扣税额
            BigDecimal sub = new BigDecimal(subLong);//下级欠扣款
            BigDecimal supply = null;//退单补款
            BigDecimal buckle = null;//退单扣款
            BigDecimal should = profit.add(supply).subtract(buckle).subtract(sub).subtract(tax);//应发分润 = 直发分润+退单补款-退单扣款-下级欠款-应扣税额
            BigDecimal parent = BigDecimal.ZERO;//应找上级扣款
            if(should.compareTo(BigDecimal.ZERO)<0){ //应发系负数
                should = BigDecimal.ZERO;
                parent = should.multiply(new BigDecimal("-1"));//此时该代理商上级如果是一代？
            }
            if(null!=profitDirect){//已存在本月该代理商分润
                profitDirect.setSupplyAmt(supply);
                profitDirect.setBuckleAmt(buckle);
                profitDirect.setShouldProfit(should);
                profitDirect.setActualProfit(BigDecimal.ZERO);//实发分润
                profitDirect.setParentBuckle(parent);
            }else{
                profitDirect = new ProfitDirect();
                profitDirect.setId(idService.genId(TabId.P_PROFIT_DIRECT));
            }
        }
    }
}
