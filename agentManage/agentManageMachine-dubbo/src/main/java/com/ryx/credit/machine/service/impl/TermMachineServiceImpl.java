package com.ryx.credit.machine.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.enumc.TerminalPlatformType;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.*;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：cx
 * 时间：2018/10/10
 * 描述： 业务系统极具终端操作接口
 */
@Service("termMachineService")
public class TermMachineServiceImpl  implements TermMachineService {

    private Logger logger = LoggerFactory.getLogger(TermMachineServiceImpl.class);

    @Resource(name = "posTermMachineServiceImpl")
    private TermMachineService posTermMachineServiceImpl;
    @Resource(name = "mposTermMachineServiceImpl")
    private TermMachineService mposTermMachineServiceImpl;
    @Resource(name = "sPosTermMachineServiceImpl")
    private TermMachineService sPosTermMachineServiceImpl;
    @Resource(name = "rdbTermMachineServiceImpl")
    private TermMachineService rdbTermMachineServiceImpl;
    @Resource(name = "rjTermMachineServiceImpl")
    private TermMachineService rjTermMachineServiceImpl;

    @Override
    public List<TermMachineVo> queryTermMachine(PlatformType platformType,Map<String,String> par) throws Exception{
        //封装异常返回，或者未实现的平台查询
        List<TermMachineVo> list = new ArrayList<>();
        TermMachineVo vo = new TermMachineVo();
        vo.setId("");
        vo.setMechineName("无配置");
        list.add(vo);

        //分平台查询
        try {
            if(PlatformType.whetherPOS(platformType.code)){
                return posTermMachineServiceImpl.queryTermMachine(platformType,par);
            }else  if(PlatformType.MPOS.code.equals(platformType.code)){
                return mposTermMachineServiceImpl.queryTermMachine(platformType,par);
            }else  if(PlatformType.SSPOS.code.equals(platformType.code)){
                return sPosTermMachineServiceImpl.queryTermMachine(platformType,par);
            }else  if(PlatformType.RDBPOS.code.equals(platformType.code)) {
                return rdbTermMachineServiceImpl.queryTermMachine(platformType, par);
            }else if (PlatformType.RJPOS.code.equals(platformType.code)){
                return rjTermMachineServiceImpl.queryTermMachine(platformType, par);
            }else {
                return list;
            }
        }catch (Exception e){
            vo.setMechineName(e.getLocalizedMessage());
            list.add(vo);
            return list;
        }
    }

    @Override
    public List<MposTermBatchVo> queryMposTermBatch(PlatformType platformType) throws Exception{
        if(PlatformType.whetherPOS(platformType.code)){
            return new ArrayList<>();
        }else  if(PlatformType.MPOS.code.equals(platformType.code)){
            return mposTermMachineServiceImpl.queryMposTermBatch(platformType);
        }else  if(PlatformType.SSPOS.code.equals(platformType.code)){
            return sPosTermMachineServiceImpl.queryMposTermBatch(platformType);
        }
        return new ArrayList<>();
    }

    @Override
    public List<MposTermTypeVo> queryMposTermType(PlatformType platformType) throws Exception{

        if(PlatformType.whetherPOS(platformType.code)){
            return new ArrayList<>();
        }else if(PlatformType.MPOS.code.equals(platformType.code)){
            return mposTermMachineServiceImpl.queryMposTermType(platformType);
        }else if(PlatformType.SSPOS.code.equals(platformType.code)){
            return sPosTermMachineServiceImpl.queryMposTermType(platformType);
        }
        return new ArrayList<>();
    }

    /**
     * 机具的下发
     * @param lowerHairMachineVo
     * @return
     */
    @Override
    public AgentResult lowerHairMachine(LowerHairMachineVo lowerHairMachineVo) throws Exception{
        if(PlatformType.whetherPOS(lowerHairMachineVo.getPlatformType())){
            return AgentResult.ok();
        }else if(PlatformType.MPOS.code.equals(lowerHairMachineVo.getPlatformType())){
            return mposTermMachineServiceImpl.lowerHairMachine(lowerHairMachineVo);
        }else if(PlatformType.SSPOS.code.equals(lowerHairMachineVo.getPlatformType())){
            return sPosTermMachineServiceImpl.lowerHairMachine(lowerHairMachineVo);
        } else if(PlatformType.RJPOS.code.equals(lowerHairMachineVo.getPlatformType())) {
            return rjTermMachineServiceImpl.lowerHairMachine(lowerHairMachineVo);
        } else if (PlatformType.RDBPOS.code.equals(lowerHairMachineVo.getPlatformType())) {
            return rdbTermMachineServiceImpl.lowerHairMachine(lowerHairMachineVo);
        }
        return AgentResult.fail("未实现的业务平台");
    }

    /**
     * 机具的调整，，退货是使用
     * @param adjustmentMachineVo
     * @return
     */
    @Override
    public AgentResult adjustmentMachine(AdjustmentMachineVo adjustmentMachineVo) throws Exception{
        if(PlatformType.whetherPOS(adjustmentMachineVo.getPlatformType())){
            return AgentResult.ok();
        }else if(PlatformType.MPOS.code.equals(adjustmentMachineVo.getPlatformType())){
            return mposTermMachineServiceImpl.adjustmentMachine(adjustmentMachineVo);
        }else if(PlatformType.SSPOS.code.equals(adjustmentMachineVo.getPlatformType())){
            return sPosTermMachineServiceImpl.adjustmentMachine(adjustmentMachineVo);
        }else if (PlatformType.RDBPOS.code.equals(adjustmentMachineVo.getPlatformType())){
            return rdbTermMachineServiceImpl.adjustmentMachine(adjustmentMachineVo);
        }
        return AgentResult.fail("未实现的业务");
    }

    /**
     * 机具活动的变更
     * @param changeActMachine
     * @return
     */
    @Override
    public AgentResult changeActMachine(ChangeActMachineVo changeActMachine) throws Exception{

        if(PlatformType.whetherPOS(changeActMachine.getPlatformType())){
            AgentResult res =   posTermMachineServiceImpl.changeActMachine(changeActMachine);
            logger.info("pos机具的互动变更接口:{}",res.getMsg());
            return res;
        }else if(PlatformType.MPOS.code.equals(changeActMachine.getPlatformType())){
            AgentResult res =   mposTermMachineServiceImpl.changeActMachine(changeActMachine);
            logger.info("mpos机具的互动变更接口:{}",res.getMsg());
            return res;
        }else if(PlatformType.SSPOS.code.equals(changeActMachine.getPlatformType())){
            AgentResult res =   sPosTermMachineServiceImpl.changeActMachine(changeActMachine);
            logger.info("mpos机具的互动变更接口:{}",res.getMsg());
            return res;
        }
        return AgentResult.fail("未知业务平台");
    }

    @Override
    public JSONObject request(Map data, String url) throws Exception {
        return null;
    }

    @Override
    public AgentResult querySnMsg(PlatformType platformType,String snBegin,String snEnd)throws Exception{
        if(PlatformType.whetherPOS(platformType.name())){
            return posTermMachineServiceImpl.querySnMsg(platformType,snBegin,snEnd);
        }else  if (PlatformType.MPOS.code.equals(platformType.name())){
            return mposTermMachineServiceImpl.querySnMsg(platformType,snBegin,snEnd);
        } else if (PlatformType.RDBPOS.code.equals(platformType.name())){
            return rdbTermMachineServiceImpl.querySnMsg(platformType,snBegin,snEnd);
        }
        return AgentResult.fail("未知业务平台");
    }

    @Override
    public AgentResult queryTerminalTransfer(List<TerminalTransferDetail> terminalTransferDetailLists, String operation) throws Exception {
        String type = terminalTransferDetailLists.get(0).getPlatformType().toString();
        logger.info("本次联动请求类型为:{}",type);
      if(String.valueOf(TerminalPlatformType.POS.getValue()).equals(type)||String.valueOf(TerminalPlatformType.ZHPOS.getValue()).equals(type)){//pos
            return   posTermMachineServiceImpl.queryTerminalTransfer(terminalTransferDetailLists,operation);
        }else if(String.valueOf(TerminalPlatformType.MPOS.getValue()).equals(type)){//手刷
            return   mposTermMachineServiceImpl.queryTerminalTransfer(terminalTransferDetailLists,operation);
        }else if(String.valueOf(TerminalPlatformType.RDBPOS.getValue()).equals(type)){//瑞大宝
            return   AgentResult.fail("未联动");
        }
        return AgentResult.fail("未知业务平台");
    }

    @Override
    public AgentResult queryTerminalTransferResult(String serialNumber,String type) throws Exception {
        AgentResult agentResult=null;
        logger.info("本次联动查询结果类型为:{}",type);
        if(String.valueOf(TerminalPlatformType.POS.getValue()).equals(type)||String.valueOf(TerminalPlatformType.ZHPOS.getValue()).equals(type)){//pos
         agentResult =  posTermMachineServiceImpl.queryTerminalTransferResult(serialNumber,type);
        }else if(String.valueOf(TerminalPlatformType.MPOS.getValue()).equals(type)){//手刷
            agentResult =  mposTermMachineServiceImpl.queryTerminalTransferResult(serialNumber,type);
        }else if(String.valueOf(TerminalPlatformType.RDBPOS.getValue()).equals(type)){//瑞大宝

        }
        return agentResult;
    }

    @Override
    public AgentResult synOrVerifyCompensate(List<ORefundPriceDiffDetail> refundPriceDiffDetailList, String operation, String isFreeze) throws ProcessException {
        String platformType = refundPriceDiffDetailList.get(0).getPlatformType();
        if (PlatformType.whetherPOS(platformType)) {
            return posTermMachineServiceImpl.synOrVerifyCompensate(refundPriceDiffDetailList, operation, isFreeze);
        } else if (PlatformType.SSPOS.getValue().equals(platformType)) {
            return sPosTermMachineServiceImpl.synOrVerifyCompensate(refundPriceDiffDetailList, operation, isFreeze);
        } else if (PlatformType.MPOS.getValue().equals(platformType)) {
            return mposTermMachineServiceImpl.synOrVerifyCompensate(refundPriceDiffDetailList, operation, isFreeze);
        } else if (PlatformType.RDBPOS.getValue().equals(platformType)) {
            return rdbTermMachineServiceImpl.synOrVerifyCompensate(refundPriceDiffDetailList, operation, isFreeze);
        } else {
            return AgentResult.fail(platformType + "平台活动调整功能未实现");
        }
    }

    @Override
    public AgentResult queryCompensateResult(Map<String, Object> map, String platformType) throws Exception {
        AgentResult agentResult = AgentResult.fail();
        if(PlatformType.whetherPOS(platformType)){
            agentResult =  posTermMachineServiceImpl.queryCompensateResult(map,platformType);
        }else if(PlatformType.SSPOS.getValue().equals(platformType)){
            agentResult =  sPosTermMachineServiceImpl.queryCompensateResult(map,platformType);
        }else if(PlatformType.MPOS.getValue().equals(platformType)){
            agentResult =  mposTermMachineServiceImpl.queryCompensateResult(map,platformType);
        }else if(PlatformType.RDBPOS.getValue().equals(platformType)){
            agentResult =  rdbTermMachineServiceImpl.queryCompensateResult(map,platformType);
        }else if(PlatformType.RJPOS.getValue().equals(platformType)){
            agentResult =  rjTermMachineServiceImpl.queryCompensateResult(map,platformType);
        }else {
            //未调整
            return AgentResult.ok("04");
        }
        return agentResult;
    }

    @Override
    public boolean checkModleIsEq(Map<String, String> data, String platformType) {
        logger.info("checkModleIsEq:{},{}", data, platformType);
        if (PlatformType.whetherPOS(platformType)) {
            return posTermMachineServiceImpl.checkModleIsEq(data, platformType);
        } else if (PlatformType.SSPOS.getValue().equals(platformType)) {
            return sPosTermMachineServiceImpl.checkModleIsEq(data, platformType);
        } else if (PlatformType.MPOS.getValue().equals(platformType)) {
            return mposTermMachineServiceImpl.checkModleIsEq(data, platformType);
        } else if (PlatformType.RDBPOS.getValue().equals(platformType)) {
            return rdbTermMachineServiceImpl.checkModleIsEq(data, platformType);
        } else {
            return true;
        }
    }

    @Override
    public AgentResult checkOrderReturnSN(List<Map<String, Object>> list, String platformType) throws Exception {
        logger.info("查询业务平台sn可否退转发list:{},paltformType:{}", JSONObject.toJSONString(list), platformType);
        if (PlatformType.whetherPOS(platformType)) {
            return posTermMachineServiceImpl.checkOrderReturnSN(list, platformType);
        } else if (PlatformType.SSPOS.getValue().equals(platformType)) {
            return sPosTermMachineServiceImpl.checkOrderReturnSN(list, platformType);
        } else if (PlatformType.MPOS.getValue().equals(platformType)) {
            return mposTermMachineServiceImpl.checkOrderReturnSN(list, platformType);
        } else if (PlatformType.RDBPOS.getValue().equals(platformType)) {
            return rdbTermMachineServiceImpl.checkOrderReturnSN(list, platformType);
        } else {
            return AgentResult.ok();
        }
    }

    @Override
    public AgentResult unfreezeOrderReturnSN(List<Map<String, Object>> list, String platformType) throws Exception {
        logger.info("业务平台sn解冻list:{},paltformType:{}", JSONObject.toJSONString(list), platformType);
        if (PlatformType.whetherPOS(platformType)) {
            return posTermMachineServiceImpl.unfreezeOrderReturnSN(list, platformType);
        } else if (PlatformType.SSPOS.getValue().equals(platformType)) {
            return sPosTermMachineServiceImpl.unfreezeOrderReturnSN(list, platformType);
        } else if (PlatformType.MPOS.getValue().equals(platformType)) {
            return mposTermMachineServiceImpl.unfreezeOrderReturnSN(list, platformType);
        } else if (PlatformType.RDBPOS.getValue().equals(platformType)) {
            return rdbTermMachineServiceImpl.unfreezeOrderReturnSN(list, platformType);
        } else {
            return AgentResult.ok();
        }
    }

    @Override
    public AgentResult queryLogisticsResult(Map<String, Object> pamMap, String platformType) throws Exception {
        if (PlatformType.RJPOS.getValue().equals(platformType)) {
            return rjTermMachineServiceImpl.queryLogisticsResult(pamMap, platformType);
        }else if (PlatformType.RDBPOS.getValue().equals(platformType)){
            return rdbTermMachineServiceImpl.queryLogisticsResult(pamMap, platformType);
        } else {
            return AgentResult.fail("未实现的物流平台。");
        }
    }

    @Override
    public AgentResult unFreezeCompensate(Map<String, Object> pamMap, String platformType) throws Exception {
        logger.info("业务平台解锁SN：{},{}", pamMap, platformType);
        if (PlatformType.whetherPOS(platformType)) {
            return posTermMachineServiceImpl.unFreezeCompensate(pamMap, platformType);
        } else if (PlatformType.SSPOS.getValue().equals(platformType)) {
            return sPosTermMachineServiceImpl.unFreezeCompensate(pamMap, platformType);
        } else if (PlatformType.MPOS.getValue().equals(platformType)) {
            return mposTermMachineServiceImpl.unFreezeCompensate(pamMap, platformType);
        } else if (PlatformType.RDBPOS.getValue().equals(platformType)) {
            return rdbTermMachineServiceImpl.unFreezeCompensate(pamMap, platformType);
        } else {
            return AgentResult.ok();
        }
    }

    @Override
    public AgentResult resendFailedCompensate(Map<String, Object> pamMap, String platformType) throws Exception {
        logger.info("活动调整，重新发送失败的SN：{},{}", pamMap, platformType);
        if (PlatformType.whetherPOS(platformType)) {
            return posTermMachineServiceImpl.resendFailedCompensate(pamMap, platformType);
        } else if (PlatformType.SSPOS.getValue().equals(platformType)) {
            return sPosTermMachineServiceImpl.resendFailedCompensate(pamMap, platformType);
        } else if (PlatformType.MPOS.getValue().equals(platformType)) {
            return mposTermMachineServiceImpl.resendFailedCompensate(pamMap, platformType);
        } else if (PlatformType.RDBPOS.getValue().equals(platformType)) {
            return rdbTermMachineServiceImpl.resendFailedCompensate(pamMap, platformType);
        } else {
            return AgentResult.fail("平台有误，请再次发送！");
        }
    }
}
