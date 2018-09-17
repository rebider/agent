package com.ryx.credit.task;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.ColinfoPayStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.dao.agent.AColinfoPaymentMapper;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentColinfoMapper;
import com.ryx.credit.pojo.admin.agent.AColinfoPayment;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentBusInfoExample;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收款账户同步出款表
 * Created by RYX on 2018/9/14.
 */
@Service
@Transactional(rollbackFor=RuntimeException.class)
public class ColinfoTask {

    private static final Logger log = LoggerFactory.getLogger(ColinfoTask.class);
    @Autowired
    private AgentColinfoMapper agentColinfoMapper;
    @Autowired
    private AColinfoPaymentMapper colinfoPaymentMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private IdService idService;
    /**
     * 21:50
     */
//    @Scheduled(cron = "0 50 21 * * ? *")
//    @Scheduled(cron = "0/30 * * * * ?")
    public void synColinfoToPayment() {
        log.info("synColinfoToPayment定时任务启动");
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

        try {
            synList.forEach(row->{
                AColinfoPayment payment = new AColinfoPayment();
                Date nowDate = new Date();
                String id = idService.genId(TabId.A_COLINFO_PAYMENT);
                payment.setId(id);
                payment.setColinfoId(String.valueOf(row.get("ID")));
                payment.setMerchId(String.valueOf(row.get("AG_UNIQ_NUM")));
                payment.setMerchName(String.valueOf(row.get("AG_NAME")));
                payment.setBalanceRcvAcc(String.valueOf(row.get("CLO_BANK_ACCOUNT")));
                payment.setBalanceRcvBank(String.valueOf(row.get("CLO_REALNAME")));
                payment.setBalanceRcvName(String.valueOf(row.get("CLO_BANK")));
                payment.setBalanceRcvCode(String.valueOf(row.get("CLO_BANK_CODE")));
                payment.setBalanceRcvType(cloTypeToPayment(String.valueOf(row.get("CLO_TYPE"))));
                payment.setBalanceRcvType(String.valueOf(row.get("CLO_TYPE")));
                payment.setFlag(Status.STATUS_0.status.toString());   //待处理
                payment.setcUser(String.valueOf(row.get("C_USER")));
                payment.setuUser(String.valueOf(row.get("C_USER")));
                payment.setCreateTime(DateUtil.format(nowDate,DateUtil.DATE_FORMAT_2));
                payment.setBalanceLs(id);  //流水号
                payment.setTranDate(DateUtil.format(nowDate,DateUtil.DATE_FORMAT_3));
                payment.setInputTime(DateUtil.format(nowDate,DateUtil.DATE_FORMAT_2));
                payment.setBalanceAmt(new BigDecimal(0.01));
                payment.setStatus(Status.STATUS_1.status);
                payment.setVersion(Status.STATUS_0.status);
                AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
                criteria.andAgentIdEqualTo(payment.getMerchId());
                List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
                payment.setAccountId(agentBusInfos.get(0).getCloPayCompany());  //出款
                int insert = colinfoPaymentMapper.insert(payment);
                if(insert==1){
                    AgentColinfo agentColinfo = new AgentColinfo();
                    agentColinfo.setId(String.valueOf(row.get("ID")));
                    agentColinfo.setPayStatus(ColinfoPayStatus.B.getValue());
                    agentColinfoMapper.updateByPrimaryKeySelective(agentColinfo);
                }
            });
        } catch (Exception e) {
            log.info("synColinfoToPayment同步出现异常:{}",e.getMessage());
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
}
