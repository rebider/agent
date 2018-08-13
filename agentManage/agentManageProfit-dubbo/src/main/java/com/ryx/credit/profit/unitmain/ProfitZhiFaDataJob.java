package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.profit.dao.BuckleRunMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.*;
import com.ryx.credit.service.dict.IdService;
import jdk.nashorn.internal.objects.annotations.Where;
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
    @Autowired
    private ProfitDeductionService profitDeductionService;
    @Autowired
    private ProfitSupplyService profitSupplyService;

    private String month = "";

    public static void main(String agrs[]){
        String transDate = null;
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
        System.out.println(data);
    }

    /**
     * 同步直发分润数据
     * @param transDate 交易月份（空则为上一月）
     */
    public void synchroProfitDirect(String transDate){
        HashMap<String,String> map = new HashMap<String,String>();
        month = DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1)).substring(0,6);
        map.put("transDate",transDate==null?month:transDate);
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
        List<JSONObject> list = JSONObject.parseObject(data,List.class);
        try {
            insertProfitDirect(list);
        } catch (Exception e) {
            logger.error("同步插入数据失败！");
            e.printStackTrace();
        }
        try {
            //计算直发补款
            computerService.computer_Supply_ZhiFa();
            //计算直发实际分润
            computerService.computer_ZhiFa();
            //计算不足扣款找上级代扣，并记录
            computerService.computer_Buckle_ZhiFa();
        } catch (Exception e) {
            logger.error("直发分润计算出错！");
            e.printStackTrace();
        }
    }

    public void insertProfitDirect(List<JSONObject> profitDirects){
        for(JSONObject json:profitDirects){
            ProfitDeduction where = new ProfitDeduction();
            where.setAgentPid(json.getString("AGENTID"));
            where.setDeductionType("01");
            where.setDeductionDate(DateUtil.sdf_Days.format(DateUtil.addMonth(new Date() , -1)).substring(0,7));
            BigDecimal buckle = profitDeductionService.totalBuckleByMonth(where);//退单扣款
            ProfitDirect profitDirect = new ProfitDirect();
            profitDirect.setId(idService.genId(TabId.P_PROFIT_DIRECT));
            profitDirect.setAgentName(json.getString("AGENTNAME"));//代理商名称
            profitDirect.setAgentId(json.getString("AGENTID"));//代理商编号
            profitDirect.setParentAgentId(json.getString("PARENTAGENTID"));//上级代理商编号
            profitDirect.setParentAgentName(json.getString("PARENTAGENTNAME"));//上级代理商名称
            profitDirect.setFristAgentId(json.getString("FRISTAGENTID"));//一级代理商编号
            profitDirect.setFristAgentName(json.getString("FRISTAGENTNAME"));//一级代理商名称
            profitDirect.setFristAgentPid(json.getString("FRISTAGENTPID"));//一级代理商唯一码
            profitDirect.setTransAmt(json.getBigDecimal("TRANSAMT"));//直发交易金额
            profitDirect.setTransMonth(json.getString("TRANSMONTH"));//月份
            profitDirect.setTransFee(json.getBigDecimal("TRANSFEE"));//直发交易手续费
            profitDirect.setProfitAmt(json.getBigDecimal("PROFITAMT"));//直发分润
            profitDirect.setActualProfit(BigDecimal.ZERO);//实发分润
            profitDirect.setAgentEmail(json.getString("AGENTEMAIL"));//邮箱
            profitDirect.setAccountCode(json.getString("ACCOUNTCODE"));//账号
            profitDirect.setAccountName(json.getString("ACCOUNTNAME"));//户名
            profitDirect.setBankOpen(json.getString("BANKOPEN"));//开户行
            profitDirect.setBankCode(json.getString("BANKCODE"));//银行号
            profitDirect.setBossCode(json.getString("BOSSCODE"));//总行行号
            profitDirect.setBuckleAmt(buckle==null?BigDecimal.ZERO:buckle);//退单扣款
            //退单补款、应发分润、应找上级扣款需计算赋值
            profitDirectService.insertSelective(profitDirect);
        }
    }
}
