package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONArray;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.profit.dao.PosRewardDetailMapper;
import com.ryx.credit.profit.pojo.PosRewardDetail;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.profit.IPosProfitDataService;
import netscape.javascript.JSObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenliang
 * @Title: POSRewardAndRebate
 * @ProjectName agentManage
 * @Description:获取pos奖励以及回扣
 * @date 2019.10.28
 */
@Service("posRewardAndRebateJob")
@Transactional(rollbackFor = Exception.class)
public class POSRewardAndRebateJob {

    private org.slf4j.Logger LOG = LoggerFactory.getLogger(POSRewardAndRebateJob.class);


    @Autowired
    private IPosProfitDataService posProfitDataService;
    @Autowired
    AgentBusinfoService agentBusinfoService;
    @Autowired
    PosRewardDetailMapper posRewardDetailMapper;

    /**
     * 拉取数据并存入数据库
     * @return
     */
    public  Map<String, Object> posRewardData(String pftMonth){

        LOG.info("================"+pftMonth+"POS奖励拉取数据开始================");
       Map<String, Object> resultMap=new HashMap<>();

        //POS奖励获取数据:
        List<Map<String, Object>> posRewadDatas = doProfitTradingVolume(pftMonth,"PFT006");
        if(posRewadDatas==null||posRewadDatas.size()==0){
            LOG.error("================POS奖励获取数据失败================");
            resultMap.put("resultCode","error");
            resultMap.put("msg","POS奖励获取数据失败异常");
            return resultMap;
        }
        resultMap.put("posRewadDatas",posRewadDatas);
        return resultMap;

    }
    public Map<String, Object> savePosRewardData( String pftMonth){

        Map<String, Object> posRewardDataMaps = posRewardData(pftMonth);
        if(posRewardDataMaps.get("posRewadDatas")==null){
            return posRewardDataMaps;
        }
        List<Map<String, Object>> posRewadDatas = (List<Map<String, Object>>) posRewardDataMaps.get("posRewadDatas");
        for (Map<String, Object> posRewadData:posRewadDatas) {
            try {
                PosRewardDetail posRewardDetail = new PosRewardDetail();
                posRewardDetail.setId(UUID.randomUUID().toString());
                posRewardDetail.setProfitPosDate(posRewadData.get("SETTLE_MONTH")==null?"":posRewadData.get("SETTLE_MONTH").toString());
                posRewardDetail.setPosAgentName(posRewadData.get("ORG_NAME")==null?"":posRewadData.get("ORG_NAME").toString());
                posRewardDetail.setOrgid(posRewadData.get("ORG_ID")==null?"":posRewadData.get("ORG_ID").toString());
                AgentBusInfo agentBusinfo =agentBusinfoService.selectBusInfo(posRewadData.get("ORG_ID").toString());
                posRewardDetail.setSettlemonth1812totaltrans(new BigDecimal(String.valueOf(posRewadData.get("SETTLE_MONTH1812_TOTAL_TRANS")==null?0:posRewadData.get("SETTLE_MONTH1812_TOTAL_TRANS"))));
                posRewardDetail.setSettlemonthtotaltrans(new BigDecimal(String.valueOf(posRewadData.get("SETTLE_MONTH_TOTAL_TRANS")==null?0:posRewadData.get("SETTLE_MONTH_TOTAL_TRANS"))));
                posRewardDetail.setIsstandard(posRewadData.get("IS_STANDARD")==null?"":posRewadData.get("IS_STANDARD").toString());
                posRewardDetail.setTotalorder(new BigDecimal(String.valueOf(posRewadData.get("TOTAL_ORDER")==null?0:posRewadData.get("TOTAL_ORDER"))));
                posRewardDetail.setYearafter19totaltrans(new BigDecimal(String.valueOf (posRewadData.get("YEARAFTER19_TOTAL_TRANS")==null?0:posRewadData.get("YEARAFTER19_TOTAL_TRANS"))));
                posRewardDetail.setPosAgentId(agentBusinfo.getAgentId());
                posRewardDetail.setContrastmonth(posRewadData.get("CONTRAST_MONTH")==null?"":posRewadData.get("CONTRAST_MONTH").toString());
                posRewardDetail.setNewtransamount(new BigDecimal(String.valueOf(posRewadData.get("NEW_TRANS_AMOUNT")==null?0:posRewadData.get("NEW_TRANS_AMOUNT"))));
                posRewardDetail.setYearafter19credittrans(new BigDecimal(String.valueOf(posRewadData.get("YEARAFTER19_CREDIT_TRANS")==null?0:posRewadData.get("YEARAFTER19_CREDIT_TRANS"))));
                posRewardDetail.setPosStandard( posRewadData.get("REWARD_STANDARD")==null?"":posRewadData.get("REWARD_STANDARD").toString());
                posRewardDetail.setPosReawrdProfit(posRewadData.get("REWARD")==null?"":posRewadData.get("REWARD").toString());
                posRewardDetailMapper.insertSelective(posRewardDetail);
            }catch (Exception e){
                posRewardDataMaps.put("resultCode","error");
                posRewardDataMaps.put("msg","POS奖励获取数据失败异常");
                e.printStackTrace();
                LOG.error("================pos奖励获取或者插入数据时失败================");
                return posRewardDataMaps;
            }

        }

        posRewardDataMaps.put("resultCode","success");
        posRewardDataMaps.put("msg","POS奖励获取数据成功");
        return posRewardDataMaps;

    }


    public  List<Map<String, Object>> doProfitTradingVolume(String pftMonth, String tranCode){


      Map<String, Object> ResultData;
        List<Map<String,Object>> agentResultData;
        try{
            AgentResult agentResult = posProfitDataService.getPOSRewardDatas(pftMonth,tranCode);
            if(200==agentResult.getStatus()){
                ResultData = ( Map<String, Object>) agentResult.getData();
                 agentResultData = (List<Map<String,Object>>) JSONArray.parse(ResultData.get("pftData").toString());
            }else{
                LOG.info("POS奖励获取数据获取失败:"+agentResult.getMsg());
                agentResultData=null;
            }
        }catch (Exception e){
            LOG.info("POS奖励获取数据异常:"+e.getMessage());
            throw new RuntimeException("POS奖励获取数据异常！");
        }
        return agentResultData;
    }

}
