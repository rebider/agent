package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AColinfoPaymentMapper;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentColinfoMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.AimportService;
import com.ryx.credit.service.agent.ColinfoTaskService;
import com.ryx.credit.service.agent.netInPort.AgentNetInNotityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 收款账户同步出款表
 * Created by RYX on 2018/9/14.
 */
@Service("colinfoTaskService")
public class ColinfoTaskServiceImpl implements ColinfoTaskService {

    private static final Logger log = LoggerFactory.getLogger(ColinfoTaskServiceImpl.class);

    private static final  String COLINFO_URL =  AppConfig.getProperty("colinfo_out_money");

    @Autowired
    private AgentColinfoMapper agentColinfoMapper;
    @Autowired
    private AColinfoPaymentMapper colinfoPaymentMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AimportService aimportService;
    @Autowired
    private AgentNetInNotityService agentNetInNotityService;


    /**
     * 9:00 - 21:30
     */
//    @Scheduled(cron = "0/30 * * * * ?")
//    @Scheduled(cron = "0 0/30 9-21 * * ? ")
    @Override
    public void synColinfoToPayment() {
        log.info("synColinfoToPayment定时任务启动:{}",new Date());
        String indentifier = null;
        String merchId = "";
        try {
            indentifier = redisService.lockWithTimeout(RedisCachKey.INSERT_SYS_KEY.code,RedisService.ACQUIRE_TIME_OUT,RedisService.TIME_OUT);
            if(StringUtils.isBlank(indentifier)){
                log.info("synColinfoToPayment,lock锁定中");
                return;
            }
            Map<String,Object> params = new HashMap<>();
            params.put("payStatus", ColinfoPayStatus.A.getValue());
            params.put("agStatus",AgStatus.Approved.name());
            params.put("cloReviewStatus",AgStatus.Approved.status);
            List<Map<String, Object>> synList = agentColinfoMapper.synConinfo(params);
            if(null==synList){
                log.info("synColinfoToPayment,synList is null");
                return;
            }
            if(synList.size()==0){
                log.info("synColinfoToPayment,synList为空暂不同步size：0");
                return;
            }
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
                payment.setFlag(TransFlag.WCL.getValue());   //待处理
                payment.setcUser(String.valueOf(row.get("C_USER")));
                payment.setuUser(String.valueOf(row.get("C_USER")));
                payment.setCreateTime(nowDate);
                String tranDate = DateUtil.format(nowDate, DateUtil.DATE_FORMAT_3);
                payment.setTranDate(tranDate);
                payment.setInputTime(DateUtil.format(nowDate, DateUtil.DATE_FORMAT_2));
                payment.setBalanceAmt(new BigDecimal(0.01));
                payment.setStatus(Status.STATUS_1.status);
                payment.setVersion(Status.STATUS_0.status);
                payment.setSynchronizeDate(tranDate);
                payment.setDatasource("13");  //收款账户同步清结算类型
                AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
                String agentId = String.valueOf(row.get("AGENT_ID"));
                criteria.andAgentIdEqualTo(agentId);
                criteria.andCloReviewStatusEqualTo(AgStatus.Approved.status);
                List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
                if(agentBusInfos==null){
                    log.info("synColinfoToPayment,未分配打款公司,暂不出款,agentId：{}",agentId);
                    continue;
                }
                if(agentBusInfos.size()==0){
                    log.info("synColinfoToPayment,未分配打款公司,暂不出款,agentId：{}",agentId);
                    continue;
                }
                payment.setAccountId(agentBusInfos.get(0).getCloPayCompany());  //出款
                try {
                    agentColinfoService.insertByPayment(payment);
                } catch (Exception e) {
                    log.info("synColinfoToPayment同步insert,merchId:{},异常:{}", payment.getMerchId(), e.getMessage());
                    AppConfig.sendEmails("银行卡同步插入出款表出现异常：agentId:"+payment.getMerchId()+","+MailUtil.printStackTrace(e), "银行卡同步出现异常,synColinfoToPayment方法1");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            log.error("synColinfoToPayment同步出现异常:{},代理商ID:{}",e,merchId);
            AppConfig.sendEmails("银行卡同步插入出款表出现异常：agentId:"+merchId+","+MailUtil.printStackTrace(e), "银行卡同步出现异常,synColinfoToPayment方法2");
            e.printStackTrace();
        } finally {
            if(StringUtils.isNotBlank(indentifier)){
                redisService.releaseLock(RedisCachKey.INSERT_SYS_KEY.code, indentifier);
            }
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

    /**
     * 定时查询交易
     * 30分钟执行一次
     */
//  @Scheduled(cron = "0/30 * * * * ?")
//    @Scheduled(cron = "0 0/30 * * * ?")
    @Override
    public void synColinfoToQueryPayment() {
        String indentifier = null;
        try {
            log.info("synColinfoToQueryPayment定时查询启动:{}",new Date());
            indentifier = redisService.lockWithTimeout(RedisCachKey.QUERY_SYS_KEY.code,RedisService.ACQUIRE_TIME_OUT,RedisService.TIME_OUT);
            if(StringUtils.isBlank(indentifier)){
                log.info("synColinfoToQueryPayment,lock锁定中");
                return;
            }
            AColinfoPaymentExample aColinfoPaymentExample = new AColinfoPaymentExample();
            AColinfoPaymentExample.Criteria criteria = aColinfoPaymentExample.createCriteria();
            List<String> flagList = new ArrayList<>();
            flagList.add(TransFlag.CG.getValue());
            flagList.add(TransFlag.SB.getValue());
            flagList.add(TransFlag.YCX.getValue());
            flagList.add(TransFlag.FXLJ.getValue());
            flagList.add(TransFlag.NEWCG.getValue());
            flagList.add(TransFlag.NEWSB.getValue());
            flagList.add(TransFlag.NEWYCX.getValue());
            flagList.add(TransFlag.NEWFXLJ.getValue());
            criteria.andFlagNotIn(flagList);
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
                if(!String.valueOf(resultInfoMap.get("flag")).equals(TransFlag.WCL.getValue()) && !String.valueOf(resultInfoMap.get("flag")).equals(TransFlag.NEWWCL.getValue())){
                    agentColinfoService.updateByPaymentResult(aColinfoPayment,resultInfoMap);
                    if(String.valueOf(resultInfoMap.get("flag")).equals(TransFlag.CG.getValue())){
                        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                        AgentBusInfoExample.Criteria criteria1 = agentBusInfoExample.createCriteria();
                        criteria1.andAgentIdEqualTo(aColinfoPayment.getMerchId());
                        List<String> busPlatformList = new ArrayList<>();
                        busPlatformList.add(Platform.RYX.getValue());
                        busPlatformList.add(Platform.RS.getValue());
                        busPlatformList.add(Platform.TP.getValue());
                        busPlatformList.add(Platform.RHB.getValue());
                        busPlatformList.add(Platform.RYXHD.getValue());
                        busPlatformList.add(Platform.RSHD.getValue());
                        busPlatformList.add(Platform.RHBZF.getValue());
                        busPlatformList.add(Platform.RZT.getValue());
                        criteria1.andBusPlatformIn(busPlatformList);
                        criteria1.andStatusEqualTo(Status.STATUS_1.status);
                        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
                        for (AgentBusInfo agentBusInfo : agentBusInfos) {
//                            try {
//                                ImportAgent importAgent = new ImportAgent();
//                                //代理商ID
//                                importAgent.setDataid(agentBusInfo.getId());
//                                importAgent.setDatatype(AgImportType.DATACHANGEAPP.name());
//                                importAgent.setBatchcode(DateUtil.format(new Date()));
//                                importAgent.setcUser(aColinfoPayment.getcUser());
//                                if (1 != aimportService.insertAgentImportData(importAgent)) {
//                                    log.info("synColinfoToQueryPayment代理商账户修改同步业务平台失败");
//                                } else {
//                                    log.info("synColinfoToQueryPayment代理商账户修改同步业务平台!");
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                            //通知业务平台修改数据
                            agentNetInNotityService.asynNotifyPlatform(agentBusInfo.getId(),NotifyType.NetInEdit.getValue());
                        }
                    }
                }else{
                    log.info("synColinfoToQueryPayment,清结算平台未对账暂不处理,balanceLs：{}",aColinfoPayment.getBalanceLs());
                    continue;
                }
            }
        } catch (Exception e) {
            AppConfig.sendEmails("银行卡查询结果出现异常"+MailUtil.printStackTrace(e), "银行卡查询结果出现异常,synColinfoToQueryPayment方法");
            log.info("synColinfoToQueryPayment处理异常:{}",e.getMessage());
            e.printStackTrace();
        }finally {
            if(StringUtils.isNotBlank(indentifier)){
                redisService.releaseLock(RedisCachKey.INSERT_SYS_KEY.code, indentifier);
            }
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
