package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.profit.dao.PosKickBackRewardMapper;
import com.ryx.credit.profit.dao.PosRewardDetailMapper;
import com.ryx.credit.profit.pojo.PosKickBackReward;
import com.ryx.credit.profit.pojo.PosKickBackRewardExample;
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
    @Autowired
    PosKickBackRewardMapper posKickBackRewardMapper;

    /**
     * 拉取数据并存入数据库
     * @return
     */
    public  Map<String, Object> posRewardData(String pftMonth ,String tranCode){

        LOG.info("================"+pftMonth+"POS奖励拉取数据开始================");
       Map<String, Object> resultMap=new HashMap<>();

        //POS奖励获取数据:
        List<Map<String, Object>> posRewadDatas = doProfitTradingVolume(pftMonth,tranCode);
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

        Map<String, Object> posRewardDataMaps = posRewardData(pftMonth,"PFT006");
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


    public Map<String, Object> savePosKickBackData(String pftMonth){

        Map<String, Object> KickBackDataMaps = posRewardData(pftMonth,"PFT005");
        if(KickBackDataMaps.get("posRewadDatas")==null){
            return KickBackDataMaps;
        }
        List<Map<String, Object>> posRewadDatas = (List<Map<String, Object>>) KickBackDataMaps.get("posRewadDatas");
        for (Map<String, Object> posRewadData:posRewadDatas) {
            try {
                PosKickBackReward posKickBackReward = new PosKickBackReward();
                posKickBackReward.setId(UUID.randomUUID().toString());
                posKickBackReward.setBusName(posRewadData.get("TRAN_GROUP_NAME")==null?"":posRewadData.get("TRAN_GROUP_NAME").toString());
                posKickBackReward.setAgentName(posRewadData.get("ORG_NAME")==null?"":posRewadData.get("ORG_NAME").toString());
                AgentBusInfo agentBusinfo =agentBusinfoService.selectBusInfo(posRewadData.get("ORG_ID").toString());
                //if(agentBusinfo==null){
                //    posKickBackReward.setAgentId("集团代理商没有查到对应AG码");
                //}else{
                    posKickBackReward.setAgentId(agentBusinfo.getAgentId());
                    posKickBackReward.setRev1(agentBusinfo.getBusNum());
                //}
                posKickBackReward.setRewardType(posRewadData.get("IS_ASSESS_TYPE")==null?"":posRewadData.get("IS_ASSESS_TYPE").toString());
                posKickBackReward.setPreteastCycle(posRewadData.get("START_END")==null?"":posRewadData.get("START_END").toString());
                posKickBackReward.setCheckMonth(posRewadData.get("ASSESS_MONTH")==null?"":posRewadData.get("ASSESS_MONTH").toString());
                posKickBackReward.setCreditCardNewAmt(new BigDecimal(String.valueOf(posRewadData.get("NEW_CREDIT_AMT")==null?0:posRewadData.get("NEW_CREDIT_AMT"))));
                posKickBackReward.setOneMonthCount(new BigDecimal(String.valueOf(posRewadData.get("TOTAL_TRANS")==null?0:posRewadData.get("TOTAL_TRANS"))));
                posKickBackReward.setToolsCount(new BigDecimal(String.valueOf(posRewadData.get("TOTAL_ORDER")==null?0:posRewadData.get("TOTAL_ORDER"))));
                posKickBackReward.setCheckStatus(posRewadData.get("IS_STANDARD")==null?"":posRewadData.get("IS_STANDARD").toString());
                posKickBackReward.setPretestAmt(new BigDecimal(String.valueOf (posRewadData.get("SPECIALREWARD")==null?0:posRewadData.get("SPECIALREWARD"))));
                posKickBackReward.setShouldAmt(new BigDecimal(String.valueOf(posRewadData.get("YFTRANS")==null?0:posRewadData.get("YFTRANS"))));
                posKickBackReward.setChargeAmt(new BigDecimal(String.valueOf(posRewadData.get("HKTRANS")==null?0:posRewadData.get("HKTRANS"))));


                PosKickBackRewardExample posKickBackRewardExample = new PosKickBackRewardExample();
                PosKickBackRewardExample.Criteria criteria = posKickBackRewardExample.createCriteria();
                Calendar calendar = Calendar.getInstance();
                String searchTime=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(calendar.getTime());
                calendar.add(Calendar.MONTH, -1);
                String Month2= new SimpleDateFormat("yyyyMM").format(calendar.getTime());
                criteria.andRev1EqualTo(posKickBackReward.getRev1()==null?"":posKickBackReward.getRev1());
                criteria.andCheckMonthEqualTo(Month2);
                List<PosKickBackReward> posKickBackRewardList =  posKickBackRewardMapper.selectByExample(posKickBackRewardExample);
                if(posKickBackRewardList ==null||posKickBackRewardList.size()==0){
                    posKickBackReward.setLastMonthAmt(BigDecimal.ZERO);
                }else {
                    posKickBackReward.setLastMonthAmt(posKickBackRewardList.get(0).getLastMonthAmt().add(posKickBackRewardList.get(0).getChargeAmt()));
                }
                posKickBackRewardMapper.insertSelective(posKickBackReward);
            }catch (Exception e){
                KickBackDataMaps.put("resultCode","error");
                KickBackDataMaps.put("msg","POS回扣获取数据失败异常");
                e.printStackTrace();
                LOG.error("================pos回扣获取或者插入数据时失败================");
                return KickBackDataMaps;
            }

        }

        KickBackDataMaps.put("resultCode","success");
        KickBackDataMaps.put("msg","POS回扣获取数据成功");
        return KickBackDataMaps;

    }





    public  List<Map<String, Object>> doProfitTradingVolume(String pftMonth, String tranCode){


      Map<String, Object> ResultData;
        List<Map<String,Object>> agentResultData;
        try{
            AgentResult agentResult = posProfitDataService.getPOSRewardDatas(pftMonth,tranCode);
            if(200==agentResult.getStatus()){
                //ResultData = ( Map<String, Object>) agentResult.getData();
                // agentResultData = (List<Map<String,Object>>) JSONArray.parse(String.valueOf(ResultData.get("pftData")));

                JSONObject json = JSONObject.parseObject(agentResult.getData().toString());
                JSONArray array = json.getJSONArray("pftData");
                agentResultData = JSONObject.parseObject(array.toJSONString(), List.class);

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
