package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.pojo.ImportDeductionDetail;
import com.ryx.credit.profit.pojo.ProfitOrganTranMonth;
import com.ryx.credit.profit.service.ImportDeductionDetailService;
import com.ryx.credit.profit.service.ProfitOrganTranMonthService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.profit.IPosProfitDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author zhaodw
 * @Title: CheckTranJob
 * @ProjectName agentManage
 * @Description: huoq
 * @date 2018/7/2911:34
 */
@Service("profitDataJob")
public class ProfitDataJob {

    private static final Logger LOG = Logger.getLogger(ProfitDataJob.class);

    private static final  String URL =  AppConfig.getProperty("check_tran_url");

    @Autowired
    private IPosProfitDataService posProfitDataService;

    @Autowired
    private ProfitOrganTranMonthService profitOrganTranMonthService;

    @Autowired
    private IdService idService;

//    @Scheduled(cron = "0 0 12 10 * ?")
    public void deal() {
        String settleMonth = "201806";//LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        AgentResult agentResult = posProfitDataService.getPosProfitDate(settleMonth);
        JSONObject jsonObject = getTranData(settleMonth);
        if (agentResult != null && agentResult.getData() != null) {
            JSONObject json = JSONObject.parseObject(agentResult.getData().toString());
            if (json != null) {
                addQr(json, settleMonth);//新增二维码
                BigDecimal tranAmt = BigDecimal.ZERO;
                if (json != null && json.containsKey("info")) {
                    JSONObject tranData = json.getJSONObject("info");
                    BigDecimal zyssAmt = tranData.getBigDecimal("zyssAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("zyssAmt");;// 自营代理手刷总金额
                    BigDecimal zydlPosAmt = tranData.getBigDecimal("zydlPosAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("zydlPosAmt");;// 自营代理pos总金额
                    BigDecimal zyPosAmt = tranData.getBigDecimal("zyPosAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("zyPosAmt");//自营交易总金额
                    BigDecimal hyxJwAmt = tranData.getBigDecimal("hyxJwAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("hyxJwAmt");//汇银讯境外卡交易总金额
                    BigDecimal orgJwAmt = tranData.getBigDecimal("orgJwAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("orgJwAmt");//代理商境外卡交易总金额
                    tranAmt = zydlPosAmt.subtract(zyPosAmt).subtract(hyxJwAmt).subtract(orgJwAmt);
                }
                addPos(json, settleMonth, tranAmt);//新增pos
            }else{
                LOG.error("月份："+settleMonth+"，二维码提供的没有获取到数据");
            }
        }
    }

    private void validateQr(String settleMonth, BigDecimal zyssAmt) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonth.setProductType("02");
        PageInfo pageInfo = profitOrganTranMonthService.getProfitOrganTranMonthList(profitOrganTranMonth, null);
        boolean hasQr = false;
        if(pageInfo != null && pageInfo.getTotal() > 0) {
            List<ProfitOrganTranMonth> profitOrganTranMonths = pageInfo.getRows();
            for (ProfitOrganTranMonth organTranMonth : profitOrganTranMonths) {
                    hasQr = true;
                    organTranMonth.setTranAmt(zyssAmt);
                    organTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
                    organTranMonth.setDifferenceAmt(organTranMonth.getSettleAmt().subtract(zyssAmt));
                    profitOrganTranMonthService.update(organTranMonth);
            }
        }
        if (!hasQr) {
            addMpos(settleMonth, zyssAmt);
        }
    }

    /***
     * @Description: 增加MPOS数据
     * @Param: json
     * @Author: zhaodw
     * @Date: 2018/8/1
     */
    private void addMpos(String settleMonth, BigDecimal tranAmt) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        profitOrganTranMonth.setId(idService.genId(TabId.P_ORGAN_TRAN_MONTH));
        profitOrganTranMonth.setProductType("02");
        profitOrganTranMonth.setProductName("MPOS");
        profitOrganTranMonth.setTranAmt(tranAmt);
        profitOrganTranMonthService.insert(profitOrganTranMonth);
    }

    /***
    * @Description: 增加 二维码核对数据
    * @Param: json
    * @Author: zhaodw
    * @Date: 2018/8/1
    */
    private void addQr(JSONObject json, String settleMonth) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        profitOrganTranMonth.setId(idService.genId(TabId.P_ORGAN_TRAN_MONTH));
        profitOrganTranMonth.setProductType("03");
        profitOrganTranMonth.setProductName("二维码");
        profitOrganTranMonth.setTranAmt(json.getBigDecimal("QR_TRAN_AMT"));
        profitOrganTranMonth.setSettleAmt(json.getBigDecimal("PFT_QR_TRAN_AMT"));
        profitOrganTranMonth.setDifferenceAmt(json.getBigDecimal("PFT_QR_TRAN_AMT").subtract(json.getBigDecimal("QR_TRAN_AMT")));
        profitOrganTranMonthService.insert(profitOrganTranMonth);
    }

    /***
     * @Description: 增加POS数据
     * @Param: json
     * @Author: zhaodw
     * @Date: 2018/8/1
     */
    private void addPos(JSONObject json, String settleMonth, BigDecimal tranAmt) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        profitOrganTranMonth.setId(idService.genId(TabId.P_ORGAN_TRAN_MONTH));
        profitOrganTranMonth.setProductType("01");
        profitOrganTranMonth.setProductName("POS");
        profitOrganTranMonth.setSettleAmt(json.getBigDecimal("PFT_POS_TRAN_AMT"));
        profitOrganTranMonth.setTranAmt(tranAmt);
        profitOrganTranMonth.setDifferenceAmt(profitOrganTranMonth.getSettleAmt().subtract(tranAmt));
        profitOrganTranMonthService.insert(profitOrganTranMonth);
    }


    /***
     * @Description: 获取POS交易数据
     * @Param:  settleMonth 分润月份
     * @return:  数据json对象
     * @Author: zhaodw
     * @Date: 2018/8/2
     */
    private JSONObject getTranData(String settleMonth) {
        JSONObject json = new JSONObject();
        json.put("tranType","22");
        json.put("tranMon",   settleMonth);
        String result = HttpClientUtil.doPostJson(URL, json.toJSONString());
        if (StringUtils.isNotBlank(result)) {
            return  JSONObject.parseObject(result);
        }
        return null;
    }

}
