package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.pojo.FreezeAgent;
import com.ryx.credit.profit.pojo.FreezeOperationRecord;
import com.ryx.credit.profit.pojo.FreezeOperationRecordExample;
import com.ryx.credit.profit.service.IFreezeAgentSercice;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("freezeDayJob")
@Transactional(rollbackFor = RuntimeException.class)
public class FreezeDayJob {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(FreezeDayJob.class);
    @Autowired
    IFreezeAgentSercice freezeAgentSercice;

    /**
     * 同步日冻结数据(ProfitDay)
     * 分润月份（空则为当前日期上2天）yyyymmdd
     * 每日凌晨2点：@Scheduled(cron = "0 0 2 * * ?")
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void queryDayFreeze() {
        logger.info("===================================开始同步每日冻结=======================================");
        logger.info("获取日冻结总数");
        int count = freezeAgentSercice.queryDayFreezeCount();
        int num = count / 100 + 1;
        int startNum = 0;
        int endNum = 100;
        for (int i = 0; i < num; i++) {
            List<FreezeAgent> freezeAgents = freezeAgentSercice.queryDayFreezeDate(startNum, endNum);
            excut(freezeAgents);
            startNum += 100;
            endNum += 100;
        }

    }

    public void excut(List<FreezeAgent> freezeAgents) {
        List<String> agents = freezeAgents.stream().map((ff) -> (ff.getAgentId())).collect(Collectors.toList());
        Map<String, Object> map = new HashMap();
        map.put("batchIds", agents);
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson("http://12.3.10.161:8007/qtfr-inter/agencynew/upAgencyProfitbyAgentId.do", params);
        if (!JSONObject.parseObject(res).get("respCode").equals("000000")) {
            logger.info("同步冻结数据失败");
            return;
        }

        String data = JSONObject.parseObject(res).get("data").toString();
        List<Map> list = JSONObject.parseObject(data, List.class);
        for (Map dateMap : list) {
            for (FreezeAgent freezeAgent : freezeAgents) {
                dateMap.get("uniqueId").toString().equals(freezeAgent.getAgentId());
                FreezeOperationRecordExample freezeOperationRecordExample = new FreezeOperationRecordExample();
                FreezeOperationRecordExample.Criteria criteria = freezeOperationRecordExample.createCriteria();
                criteria.andAgentIdEqualTo(freezeAgent.getAgentId());
                if (StringUtils.isNotBlank(freezeAgent.getParentAgentId())) {
                    criteria.andParentAgentIdEqualTo(freezeAgent.getParentAgentId());
                }
                criteria.andFreezeBatchEqualTo(freezeAgent.getOperationBatch());
                List<FreezeOperationRecord> freezeOperationRecords = freezeAgentSercice.selectByExample(freezeOperationRecordExample);
                for (FreezeOperationRecord freezeOperationRecord:freezeOperationRecords) {
                    if("01".equals(freezeOperationRecord.getFreezeType())){
                        freezeOperationRecord.setFreezeAmt(new BigDecimal(dateMap.get("profit").toString()));
                    }else if("02".equals(freezeOperationRecord.getFreezeType())){
                        freezeOperationRecord.setFreezeAmt(new BigDecimal(dateMap.get("cashback").toString()));
                    }
                    try {
                        freezeAgentSercice.updateByPrimaryKeySelective(freezeOperationRecord);
                    }catch (Exception e ){
                        e.printStackTrace();
                        logger.error(e.getMessage());
                        logger.info("同步冻结日分润和日返现失败");
                    }
                }
            }
        }

    }




}