package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.service.ProfitComputerService;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 手刷月分润数据同步、定时
 */
@Service
@Transactional(rollbackFor=RuntimeException.class)
public class ProfitMonthMposDataJob {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private ProfitDetailMonthService profitDetailMonthService;
    @Autowired
    private ProfitComputerService computerService;
    @Autowired
    private IdService idService;

    private String month="";
    private int index=1;
    private static BigDecimal allAmount= BigDecimal.ZERO;

    public static void main(String agrs[]){
        String transDate = null;
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("transDate",transDate==null?DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1)).substring(0,6):transDate);
        map.put("pageNumber","1");
        map.put("pageSize","50");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                ("http://12.3.10.161:8003/qtfr/agentInterface/queryfrbymonth.do",params);
        System.out.println(res);
        JSONObject json = JSONObject.parseObject(res);
        if(!json.get("respCode").equals("000000")){
            System.out.println("请求同步失败！");
            AppConfig.sendEmails("月分润同步失败","月分润同步失败");
            return;
        }
        BigDecimal fxAmount = json.getBigDecimal("fxAmount");//分销系统交易汇总
        BigDecimal wjrAmount = json.getBigDecimal("wjrAmount");//未计入分润汇总
        BigDecimal wtbAmount = json.getBigDecimal("wtbAmount");//未同步到分润
        System.out.println(fxAmount);
        System.out.println(wjrAmount);
        System.out.println(wtbAmount);

        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data,List.class);
        try {
            //testList(list);
        } catch (Exception e) {
            System.out.println("同步月分润数据失败！");
            e.printStackTrace();
        }
        System.out.println(allAmount);
        System.out.println(data);

    }

    public static void testList(List<JSONObject> profitMonths){
        for(JSONObject json:profitMonths){
            allAmount = allAmount.add(json.getBigDecimal("TRANAMT"));
            System.out.print("唯一码:"+json.getString("UNIQUECODE"));
            System.out.print("||名称:"+json.getString("AGENTNAME"));
            System.out.print("||瑞和宝分润:"+json.getBigDecimal("RHBPROFITAMT"));
            System.out.print("||瑞银信分润:"+json.getBigDecimal("RYXPROFITAMT"));
            System.out.print("||瑞银信活动:"+json.getBigDecimal("RYXHDPROFITAMT"));
            System.out.print("||贴牌:"+json.getBigDecimal("TPPROFITAMT"));
            System.out.print("||瑞刷:"+json.getBigDecimal("RSPROFITAMT"));
            System.out.print("||瑞刷活动:"+json.getBigDecimal("RSHDPROFITAMT"));
            System.out.print("||直发:"+json.getBigDecimal("ZFPROFITAMT"));
            System.out.print("||交易额:"+json.getBigDecimal("TRANAMT"));
            System.out.println();
        }
    }

    /**
     * 同步手刷月分润明细数据
     * @param transDate 交易日期（空则为上一月）
     */
    public void synchroProfitMonth(String transDate){
        HashMap<String,String> map = new HashMap<String,String>();
        month=transDate==null?DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1)).substring(0,6):transDate;
        map.put("transDate",month);
        map.put("pageNumber",index++ +"");
        map.put("pageSize","50");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.month"),params);
        System.out.println(res);
        JSONObject json = JSONObject.parseObject(res);
        if(!json.get("respCode").equals("000000")){
            logger.error("请求同步失败！");
            AppConfig.sendEmails("月分润同步失败","月分润同步失败");
            return;
        }

        //BigDecimal fxAmount = json.getBigDecimal("fxAmount")==null?BigDecimal.ZERO:json.getBigDecimal("fxAmount");//分销系统交易汇总
        //BigDecimal wjrAmount = json.getBigDecimal("wjrAmount")==null?BigDecimal.ZERO:json.getBigDecimal("wjrAmount");//未计入分润汇总
        //BigDecimal wtbAmount = json.getBigDecimal("wtbAmount")==null?BigDecimal.ZERO:json.getBigDecimal("wtbAmount");//未同步到分润

        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data,List.class);
        try {
            if(list.size()>0){
                insertProfitMonth(list,transDate);
            }
        } catch (Exception e) {
            logger.error("同步月分润数据失败！");
            e.printStackTrace();
            throw new RuntimeException("分润数据处理失败");
        }

    }

    public void insertProfitMonth(List<JSONObject> profitMonths,String transDate){
        for(JSONObject json:profitMonths){
            allAmount = allAmount.add(json.getBigDecimal("TRANAMT"));//付款交易额-汇总所有
            ProfitDetailMonth where = new ProfitDetailMonth();
            where.setAgentPid(json.getString("UNIQUECODE"));//代理商唯一编号
            where.setProfitDate(month);
            ProfitDetailMonth detailMonth = profitDetailMonthService.selectByPIdAndMonth(where);
            BigDecimal totalDay = computerService.totalP_day_RHB(json.getString("UNIQUECODE"),json.getString("PROFITDATE"));//瑞和宝日结分润汇总
            //BigDecimal factor = computerService.total_factor(json.getString("UNIQUECODE"),json.getString("PROFITDATE"));//商业保理扣款
            //BigDecimal otherSupply = computerService.total_supply(json.getString("UNIQUECODE"),json.getString("PROFITDATE"));//其他补款汇总
            if(null!=detailMonth){//已存在该分润
                BigDecimal rhbProfit = json.getBigDecimal("RHBPROFITAMT");//瑞和宝分润
                detailMonth.setProfitDate(month);
                detailMonth.setRyxProfitAmt(json.getBigDecimal("RYXPROFITAMT"));//瑞银信分润
                detailMonth.setRyxHdProfitAmt(json.getBigDecimal("RYXHDPROFITAMT"));//瑞银信活动分润
                detailMonth.setTpProfitAmt(json.getBigDecimal("TPPROFITAMT"));//贴牌分润
                detailMonth.setRsProfitAmt(json.getBigDecimal("RSPROFITAMT"));//瑞刷分润
                detailMonth.setRsHdProfitAmt(json.getBigDecimal("RSHDPROFITAMT"));//瑞刷活动分润
                detailMonth.setZfProfitAmt(json.getBigDecimal("ZFPROFITAMT"));//直发分润
                BigDecimal tranAmt = detailMonth.getTranAmt()==null?BigDecimal.ZERO:detailMonth.getTranAmt();
                detailMonth.setTranAmt(json.getBigDecimal("TRANAMT").add(tranAmt));
                detailMonth.setRhbProfitAmt(rhbProfit.subtract(totalDay));//瑞和宝分润=瑞和宝分润-日结分润
                profitDetailMonthService.updateByPrimaryKeySelective(detailMonth);
            }else{
                detailMonth = new ProfitDetailMonth();
                detailMonth.setId(idService.genId(TabId.P_PROFIT_DETAIL_M));
                detailMonth.setProfitDate(month);
                detailMonth.setAgentPid(json.getString("UNIQUECODE"));
                //detailMonth.setAgentName(json.getString("AGENTNAME"));
                BigDecimal rhbProfit = json.getBigDecimal("RHBPROFITAMT");//瑞和宝分润
                detailMonth.setRyxProfitAmt(json.getBigDecimal("RYXPROFITAMT"));//瑞银信分润
                detailMonth.setRyxHdProfitAmt(json.getBigDecimal("RYXHDPROFITAMT"));//瑞银信活动分润
                detailMonth.setTpProfitAmt(json.getBigDecimal("TPPROFITAMT"));//贴牌分润
                detailMonth.setRsProfitAmt(json.getBigDecimal("RSPROFITAMT"));//瑞刷分润
                detailMonth.setRsHdProfitAmt(json.getBigDecimal("RSHDPROFITAMT"));//瑞刷活动分润
                detailMonth.setZfProfitAmt(json.getBigDecimal("ZFPROFITAMT"));//直发分润
                detailMonth.setTranAmt(json.getBigDecimal("TRANAMT"));
                detailMonth.setRhbProfitAmt(rhbProfit.subtract(totalDay));//瑞和宝分润得减去日结分润
                profitDetailMonthService.insertSelective(detailMonth);
            }
        }
        synchroProfitMonth(transDate);
    }

}
