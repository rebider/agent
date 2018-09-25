package com.ryx.credit.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.ColinfoPayStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TransFlag;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AColinfoPaymentMapper;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentColinfoMapper;
import com.ryx.credit.pojo.admin.agent.AColinfoPayment;
import com.ryx.credit.pojo.admin.agent.AColinfoPaymentExample;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentBusInfoExample;
import com.ryx.credit.service.agent.AgentColinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 收款账户同步出款表
 * Created by RYX on 2018/9/14.
 */
@Service
public class ColinfoTask {

    private static final Logger log = LoggerFactory.getLogger(ColinfoTask.class);

    private static final  String COLINFO_URL =  AppConfig.getProperty("colinfo_out_money");

    @Autowired
    private AgentColinfoMapper agentColinfoMapper;
    @Autowired
    private AColinfoPaymentMapper colinfoPaymentMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentColinfoService agentColinfoService;

    /**
     * 21:50
     */
//    @Scheduled(cron = "0/30 * * * * ?")
//    @Scheduled(cron = "0 30 13 * * ?")
    @Scheduled(cron = "0 0/30 9-21 * * ? ")
    public void synColinfoToPayment() {
        log.info("synColinfoToPayment定时任务启动:{}",new Date());
        Map<String,Object> params = new HashMap<>();
        params.put("payStatus", ColinfoPayStatus.A.getValue());
        params.put("agStatus",AgStatus.Approved.name());
        List<Map<String, Object>> synList = agentColinfoMapper.synConinfo(params);
        if(null==synList){
            log.info("synColinfoToPayment,synList is null");
            return;
        }
        if(synList.size()==0){
            log.info("synColinfoToPayment,synList为空暂不同步size：0");
            return;
        }
        String merchId = "";
        try {
            for (Map<String, Object> row : synList) {
                AColinfoPayment payment = new AColinfoPayment();
                Date nowDate = new Date();
                payment.setMerchId(String.valueOf(row.get("AG_UNIQ_NUM")));
                merchId = payment.getMerchId();
                payment.setColinfoId(String.valueOf(row.get("ID")));
                payment.setMerchName(String.valueOf(row.get("AG_NAME")));
                payment.setBalanceRcvAcc(String.valueOf(row.get("CLO_BANK_ACCOUNT")));
                payment.setBalanceRcvBank(String.valueOf(row.get("CLO_REALNAME")));
                payment.setBalanceRcvName(String.valueOf(row.get("CLO_BANK")));
                payment.setBalanceRcvCode(String.valueOf(row.get("BRANCH_LINE_NUM")));
                payment.setBalanceRcvType(cloTypeToPayment(String.valueOf(row.get("CLO_TYPE"))));
                payment.setFlag(TransFlag.A.getValue());   //待处理
                payment.setcUser(String.valueOf(row.get("C_USER")));
                payment.setuUser(String.valueOf(row.get("C_USER")));
                payment.setCreateTime(nowDate);
                payment.setTranDate(DateUtil.format(nowDate, DateUtil.DATE_FORMAT_3));
                payment.setInputTime(DateUtil.format(nowDate, DateUtil.DATE_FORMAT_2));
                payment.setBalanceAmt(getRandomAmt());
                payment.setStatus(Status.STATUS_1.status);
                payment.setVersion(Status.STATUS_0.status);
                AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
                criteria.andAgentIdEqualTo(payment.getMerchId());
                List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
                payment.setAccountId(agentBusInfos.get(0).getCloPayCompany());  //出款
                try {
                    agentColinfoService.insertByPayment(payment);
                } catch (Exception e) {
                    log.info("synColinfoToPayment同步insert,merchId:{},异常:{}", payment.getMerchId(), e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            log.info("synColinfoToPayment同步出现异常:{},代理商ID:{}",e.getMessage(),merchId);
            e.printStackTrace();
        }
    }

    private String cloTypeToPayment(String colType){
        // 0对私 2对公
        if(colType.equals("1")){
            return "2";
        }
        if(colType.equals("2")){
            return "0";
        }
        return "";
    }

    private BigDecimal getRandomAmt(){
        DecimalFormat dcmFmt = new DecimalFormat("0.00");
        Random rand = new Random();
        float f = rand.nextFloat() * 0.1f;
        String format = dcmFmt.format(f);
        BigDecimal bigDecimal = new BigDecimal(format);
        return bigDecimal;
    }

//  @Scheduled(cron = "0/30 * * * * ?")
    @Scheduled(cron = "0 0/30 * * * ?")
    public void synColinfoToQueryPayment() {
        try {
            log.info("synColinfoToQueryPayment定时查询启动:{}",new Date());
            AColinfoPaymentExample aColinfoPaymentExample = new AColinfoPaymentExample();
            AColinfoPaymentExample.Criteria criteria = aColinfoPaymentExample.createCriteria();
            criteria.andFlagEqualTo(TransFlag.A.getValue());
            List<AColinfoPayment> aColinfoPayments = colinfoPaymentMapper.selectByExample(aColinfoPaymentExample);
            if(null==aColinfoPayments){
                log.info("synColinfoToQueryPayment,aColinfoPayments is null");
                return;
            }
            if(aColinfoPayments.size()==0){
                log.info("synColinfoToQueryPayment,aColinfoPayments为空暂不查询");
                return;
            }
            for (AColinfoPayment aColinfoPayment : aColinfoPayments) {
                String httpResult = httpForColinfo(aColinfoPayment);
                Map<String, Object> resultMap = JsonUtil.jsonToMap(httpResult);
                String code = String.valueOf(resultMap.get("code"));
                if(!code.equals("01")){
                    log.info("synColinfoToQueryPayment,查询交易接口不成功,balanceLs：{},resultMap:{}",aColinfoPayment.getBalanceLs(),resultMap);
                    continue;
                }
                String info = String.valueOf(resultMap.get("info"));
                if(StringUtils.isBlank(info) || info.equals("null") || info.equals("[]")){
                    log.info("synColinfoToQueryPayment,查询info为空,balanceLs：{},info:{}",aColinfoPayment.getBalanceLs(),info);
                    continue;
                }
                JSONArray jsonArray = JSONObject.parseArray(String.valueOf(resultMap.get("info")));
                Map<String, Object> resultInfoMap = JsonUtil.jsonToMap(JsonUtil.objectToJson(jsonArray.get(0)));
                if(!String.valueOf(resultInfoMap.get("flag")).equals(TransFlag.A.getValue())){
                    agentColinfoService.updateByPaymentResult(aColinfoPayment,resultInfoMap);
                }else{
                    log.info("synColinfoToQueryPayment,清结算平台未对账暂不处理,balanceLs：{}",aColinfoPayment.getBalanceLs());
                    continue;
                }
            }
        } catch (Exception e) {
            log.info("synColinfoToQueryPayment处理异常:{}",e.getMessage());
            e.printStackTrace();
        }
    }

    private String httpForColinfo(AColinfoPayment colinfoPayment) throws Exception{
        Map<String, String> map = new HashMap<>();
        map.put("id", colinfoPayment.getBalanceLs());
        String params = JsonUtil.objectToJson(map);
        log.info("balanceLs:{},收款账户出款请求参数：{}",colinfoPayment.getBalanceLs(),params);
        String httpResult = HttpClientUtil.doPostJson(COLINFO_URL, params);
        log.info("balanceLs:{},收款账户出款返回参数：{}",colinfoPayment.getBalanceLs(),httpResult);
        if(StringUtils.isBlank(httpResult)){
            throw new Exception("返回参数为空");
        }
        return httpResult;
    }

}
