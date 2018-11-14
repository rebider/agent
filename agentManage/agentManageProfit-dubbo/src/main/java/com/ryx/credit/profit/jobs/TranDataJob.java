package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.pojo.ProfitOrganTranMonth;
import com.ryx.credit.profit.service.ProfitComputerService;
import com.ryx.credit.profit.service.ProfitOrganTranMonthService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.profit.IPosProfitDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author zhaodw
 * @Title: TranDataJob
 * @ProjectName agentManage
 * @Description: 获取交易数据，对数据进行差异核对
 * @date 2018/7/2911:34
 */
@Service("tranDataJob")
@Transactional(rollbackFor = Exception.class)
public class TranDataJob {

    private static final Logger LOG = Logger.getLogger(TranDataJob.class);

    private static final  String URL =  AppConfig.getProperty("check_tran_url");

    @Autowired
    private IPosProfitDataService posProfitDataService;

    @Autowired
    private ProfitOrganTranMonthService profitOrganTranMonthService;

    @Autowired
    private ProfitComputerService profitComputerService;

    @Autowired
    private IdService idService;

    @Scheduled(cron = "0 0 12 10 * ?")
    public void deal() {
        String settleMonth = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        LOG.info("分润月份"+settleMonth);
        LOG.info("获取分润数据");
        try {
            AgentResult agentResult = posProfitDataService.getPosProfitDate(settleMonth);
            if (agentResult != null && agentResult.getData() != null) {
                JSONObject json = JSONObject.parseObject(agentResult.getData().toString());
                if (json != null) {
                    BigDecimal tranAmt = BigDecimal.ZERO;
                    BigDecimal zyssAmt = BigDecimal.ZERO;
                    BigDecimal posAmt = BigDecimal.ZERO;
                    JSONObject jsonObject = getTranData(settleMonth);
                    if (jsonObject != null && jsonObject.containsKey("info")) {
                        JSONObject tranData = jsonObject.getJSONObject("info");
                        zyssAmt = tranData.getBigDecimal("zyssAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("zyssAmt");;// 自营代理手刷总金额
                        BigDecimal zydlPosDjAmt = tranData.getBigDecimal("zydlPosDjAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("zydlPosDjAmt");;// 贷记金额
                        BigDecimal zydlPosJjAmt = tranData.getBigDecimal("zydlPosJjAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("zydlPosJjAmt");//借记金额
                        BigDecimal jgPosAmt = tranData.getBigDecimal("jgPosAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("jgPosAmt");//借记金额
                        BigDecimal zyPosAmt = tranData.getBigDecimal("zyPosAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("zyPosAmt");//自营pos金额
                        BigDecimal hyxPosJwAmt = tranData.getBigDecimal("hyxPosJwAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("hyxPosJwAmt");//汇银讯境外金额
                        BigDecimal dlPosJwAmt = tranData.getBigDecimal("dlPosJwAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("dlPosJwAmt");//代理商境外
                        tranAmt = zydlPosDjAmt.add(zydlPosJjAmt).add(jgPosAmt);
                        //t.hyxpos_jw_amt+t.dlpos_jw_amt
                        posAmt =zyPosAmt.add(hyxPosJwAmt).add(dlPosJwAmt);
                    }
                    insertOrUpdate(json, settleMonth, tranAmt, zyssAmt, posAmt);//新增二维码
                }else{
                    LOG.error("月份："+settleMonth+"，二维码提供的没有获取到数据");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            LOG.error("分润数据处理失败");
            throw new RuntimeException("分润数据处理失败");
        }
    }


    /***
    * @Description:新增或修改数据
    * @Param:json 分润数据
    * @Param:settleMonth 分润月份
    * @Param:tranAmt pos交易金额
    * @Param:zyssAmt 手刷交易金额
    * @Author: zhaodw
    * @Date: 2018/8/3
    */
    private void insertOrUpdate(JSONObject json, String settleMonth, BigDecimal tranAmt, BigDecimal zyssAmt, BigDecimal posAmt) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonthService.delete(profitOrganTranMonth);
        addPos(json, settleMonth, tranAmt,posAmt);
        addMpos(json, settleMonth, zyssAmt);
        addQr(json, settleMonth);
    }

    /***
     * @Description: 增加MPOS数据
     * @Param: json
     * @Author: zhaodw
     * @Date: 2018/8/1
     */
    private void addMpos(JSONObject json, String settleMonth, BigDecimal tranAmt) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        profitOrganTranMonth.setId(idService.genId(TabId.P_ORGAN_TRAN_MONTH));
        profitOrganTranMonth.setProductType("02");
        profitOrganTranMonth.setProductName("MPOS");
        profitOrganTranMonth.setTranAmt(tranAmt.add(json.getBigDecimal("QR_SS_TOTAL_AMT")));
        try {
            BigDecimal settleAmt = profitComputerService.synchroSSTotalTransAmt(null);
            profitOrganTranMonth.setSettleAmt(settleAmt);
        }catch(Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("获取手刷分润交易数据失败");
        }
        profitOrganTranMonth.setDifferenceAmt(profitOrganTranMonth.getSettleAmt().subtract(tranAmt));
        profitOrganTranMonthService.insert(profitOrganTranMonth);
    }

    /***
    * @Description: 增加 二维码核对数据
    * @Param: json
    * @Author: zhaodw
    * @Date: 2018/8/1
    */
    private void addQr(JSONObject json, String settleMonth) {
        BigDecimal tranAmt = json.getBigDecimal("QR_POS_TOTAL_AMT");
        BigDecimal qrPos886 = json.getBigDecimal("QR_POS_886_WXZFB_AMT")==null?BigDecimal.ZERO: json.getBigDecimal("QR_POS_886_WXZFB_AMT");
        BigDecimal qrNpos886 = json.getBigDecimal("QR_NPOS_886_WXZFB_AMT ")==null?BigDecimal.ZERO: json.getBigDecimal("QR_NPOS_886_WXZFB_AMT");
        BigDecimal qrPosUp886 = json.getBigDecimal("QR_POS_886_UPZF_AMT")==null?BigDecimal.ZERO: json.getBigDecimal("QR_POS_886_UPZF_AMT");
        BigDecimal qrNposUp886 = json.getBigDecimal("QR_NPOS_886_UPZF_AMT")==null?BigDecimal.ZERO: json.getBigDecimal("QR_NPOS_886_UPZF_AMT");
        BigDecimal qrPos885 = json.getBigDecimal("QR_POS_885_WXZFB_AMT")==null?BigDecimal.ZERO: json.getBigDecimal("QR_POS_885_WXZFB_AMT");
        BigDecimal qrPosUp885 = json.getBigDecimal("QR_POS_885_UPZF_AMT")==null?BigDecimal.ZERO: json.getBigDecimal("QR_POS_885_UPZF_AMT");
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        profitOrganTranMonth.setId(idService.genId(TabId.P_ORGAN_TRAN_MONTH));
        profitOrganTranMonth.setProductType("03");
        profitOrganTranMonth.setProductName("二维码");
        profitOrganTranMonth.setTranAmt(tranAmt);
        profitOrganTranMonth.setSettleAmt(qrPos886.add(qrNpos886).add(qrPosUp886).add(qrNposUp886).add(qrPos885));
        profitOrganTranMonth.setDifferenceAmt(profitOrganTranMonth.getTranAmt().subtract(profitOrganTranMonth.getSettleAmt()));
        profitOrganTranMonthService.insert(profitOrganTranMonth);
    }

    /***
     * @Description: 增加POS数据
     * @Param: json
     * @Author: zhaodw
     * @Date: 2018/8/1
     */
    private void addPos(JSONObject json, String settleMonth, BigDecimal tranAmt, BigDecimal posAmt) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        profitOrganTranMonth.setId(idService.genId(TabId.P_ORGAN_TRAN_MONTH));
        profitOrganTranMonth.setProductType("01");
        profitOrganTranMonth.setProductName("POS");
        profitOrganTranMonth.setSettleAmt(json.getBigDecimal("POS_TOTAL_AMT").add(posAmt));
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
        LOG.info("获取POS交易数据");
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
