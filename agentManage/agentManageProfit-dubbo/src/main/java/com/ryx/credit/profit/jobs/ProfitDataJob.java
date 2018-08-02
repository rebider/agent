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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    @Autowired
    private IPosProfitDataService posProfitDataService;

    @Autowired
    private ProfitOrganTranMonthService profitOrganTranMonthService;

    @Autowired
    private IdService idService;

//    @Scheduled(cron = "0 0 12 10 * ?")
    public void deal() {
        String settleMonth = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        AgentResult agentResult = posProfitDataService.getPosProfitDate(settleMonth);
        if (agentResult != null && agentResult.getData() != null) {
            JSONObject json = JSONObject.parseObject(agentResult.getData().toString());
            addQr(json);//新增二维码
            addPos(json);//新增pos
        }
    }
    /***
    * @Description: 增加 二维码核对数据
    * @Param: json
    * @Author: zhaodw
    * @Date: 2018/8/1
    */
    private void addQr(JSONObject json) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(json.getString("SETTLE_MONTH"));
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
    private void addPos(JSONObject json) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(json.getString("SETTLE_MONTH"));
        profitOrganTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        profitOrganTranMonth.setId(idService.genId(TabId.P_ORGAN_TRAN_MONTH));
        profitOrganTranMonth.setProductType("01");
        profitOrganTranMonth.setProductName("POS");
        profitOrganTranMonth.setSettleAmt(json.getBigDecimal("PFT_POS_TRAN_AMT"));
        profitOrganTranMonthService.insert(profitOrganTranMonth);
    }

}
