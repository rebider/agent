package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.profit.pojo.OrganTranMonthDetail;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.service.OrganTranMonthDetailService;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.profit.service.ProfitMonthService;
import com.ryx.credit.service.profit.PosOrganDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaodw
 * @Title: CheckTranJob
 * @ProjectName agentManage
 * @Description: 交易金额汇总任务
 * @date 2018/7/2911:34
 */
@Service("tranAmtSumJob")
@Transactional(rollbackFor=RuntimeException.class)
public class TranAmtSumJob {

    private static final Logger LOG = Logger.getLogger(TranAmtSumJob.class);



    @Autowired
    private OrganTranMonthDetailService organTranMonthDetailServiceImpl;

    @Autowired
    private PosOrganDataService posOrganDataService;


//    @Scheduled(cron = "0 0 12 10 * ?")
    public void deal() {
        String settleMonth = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        LOG.info("分润月份"+settleMonth);
        LOG.info("获取交易明细数据");
        try {
            OrganTranMonthDetail organTranMonthDetail = new OrganTranMonthDetail();
            organTranMonthDetail.setProfitDate(settleMonth);
            // 获取分润明细数据对分润汇总进行求和赋值
            List<OrganTranMonthDetail> organTranMonthDetails = organTranMonthDetailServiceImpl.getOrganTranMonthDetailList(organTranMonthDetail);
            organTranMonthDetails.parallelStream().forEach(organTranMonthDetail1 -> {
                // 获取所有下级的代理商编号
                AgentResult agentResult = posOrganDataService.getAllChild(organTranMonthDetail1.getAgentId());
                if (agentResult != null && agentResult.getData() != null) {
                    JSONObject json = JSONObject.parseObject(agentResult.getData().toString());
                    if (json != null && json.size() > 0) {
                        JSONArray array = json.getJSONArray("orgList");
                        if (array != null && array.size() > 0) {
                            // 获取所有下级的交易金额汇总
                            Map<String, Object> param = new HashMap<>();
                            param.put("agentIdList", array.toJavaList(String.class));
                            param.put("settleMonth", settleMonth);
                            OrganTranMonthDetail sum = organTranMonthDetailServiceImpl.getChildSumTranAmt(param);
                            if (sum != null) {
                                organTranMonthDetail1.setAllchildJlTranAmt(sum.getAllchildJlTranAmt());
                                organTranMonthDetail1.setAllchildTranAmt(sum.getAllchildTranAmt());
                                organTranMonthDetailServiceImpl.update(organTranMonthDetail1);
                            }
                            param = null;
                        }
                    }
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
            LOG.error("分润数据处理失败");
            throw new RuntimeException("分润数据处理失败");
        }

    }
}
