package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AdjustStatus;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.dao.order.TerminalTransferDetailMapper;
import com.ryx.credit.dao.order.TerminalTransferMapper;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;

import com.ryx.credit.service.order.TerminalTransferFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author cl
 * @Date 2020/6/10 15:35
 * @Version 1.0
 */
@Service("terminalTransferMPosService")
public class TerminalTransferMPosServiceImpl extends TerminalTransferBase implements TerminalTransferFactory {
    private static Logger log = LoggerFactory.getLogger(TerminalTransferMPosServiceImpl.class);


    @Autowired
    private TermMachineService termMachineService;
    @Autowired
    private TerminalTransferMapper terminalTransferMapper;
    @Autowired
    private TerminalTransferDetailMapper terminalTransferDetailMapper;


    @Override
    public void platformSameCheck(TerminalTransferDetail terminalTransferDetail) throws MessageException {
        String platformType = disBusCode(terminalTransferDetail);
        if (!platformType.equals(PlatformType.MPOS.getValue())) {
            log.info("您的机构码不属于手刷平台请选择：原：" + terminalTransferDetail.getOriginalOrgId() + "目标：" + terminalTransferDetail.getGoalOrgId());
            throw new MessageException("您的机构码不属于手刷平台请选择：原：" + terminalTransferDetail.getOriginalOrgId() + "目标：" + terminalTransferDetail.getGoalOrgId());
        }
    }

    @Override
    public void check(List<TerminalTransferDetail> terminalTransferDetails) throws MessageException {
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
            String originalOrgId = terminalTransferDetail.getOriginalOrgId().trim();
            Map<String, Object> mapAgentType = getAgentType(originalOrgId);
            /* Map<String, Object> platFromMap = terminalTransferMapper.queryPlatFrom(mapAgentType.get("BUS_PLATFORM").toString());*/
            terminalTransferDetail.setPlatFrom(mapAgentType.get("BUS_PLATFORM").toString());
        }

        AgentResult agentResult = null;
        try {
            agentResult = termMachineService.queryTerminalTransfer(terminalTransferDetails, TRANSFER_ZG_CHECK, terminalTransferMapper.selectByPrimaryKey(terminalTransferDetails.get(0).getTerminalTransferId()).getTaskId());
        } catch (Exception e) {
            log.error("MPOS获得检验结果异常");
            throw new MessageException("MPOS获得检验结果异常");
        }
        if (agentResult.isOK()) {
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) agentResult.getData();
            if (mapList == null || mapList.size() == 0) {
                log.info("调用MPOS接口查询验证接口返回异常，返回结果为：" + mapList);
                throw new MessageException("调用MPOS接口查询验证接口返回异常，返回结果为：" + mapList);
            }
            for (Map<String, Object> map : mapList) {
                if ("code".equals(map.get("code").toString())) {
                    continue;
                } else {
                    log.info("MPOS划拨查询失败" + map.get("posSnBegin").toString() + "-------" + map.get("posSnEnd").toString() + ":" + map.get("msg"));
                    throw new MessageException(map.get("posSnBegin").toString() + "-------" + map.get("posSnEnd").toString() + map.get("msg"));
                }

            }

        } else {
            log.info("MPOS未连通查询检测接口：" + agentResult);
            throw new MessageException("MPOS未连通查询检测接口：" + agentResult);
        }

    }

    @Override
    public AgentResult terminalTransferunlock(String taskId, String serialNumber, String type) {
        /**
         * 调用接口远程解锁
         */
        AgentResult agentResult = null;
        try {
            log.info("划拨解锁开始：" + taskId + "明细id：" + serialNumber);
            agentResult = termMachineService.terminalTransferunlock(taskId, null, type);
            log.info("划拨解锁结束：" + JSONObject.toJSON(agentResult));
        } catch (Exception e) {
            log.error("划拨解锁异常：" + taskId + "明细id：" + serialNumber);
            e.printStackTrace();
            agentResult = AgentResult.fail("划拨解锁异常");
        }
        if (agentResult.isOK()) {
            JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
            if ("00".equals(jsonObject.getString("resultCode"))) {
                agentResult = AgentResult.ok(jsonObject.getString("resultMsg"));
            } else {
                agentResult = AgentResult.fail(jsonObject.getString("resultMsg"));
            }
        } else {
            agentResult = AgentResult.fail(agentResult.getMsg());
        }
        return agentResult;

    }

    @Override
    public AgentResult adjustAgain(String id) {
        /**
         * 调用接口远程
         */
        AgentResult agentResult = null;
        try {
            log.info("划拨重新开始明细id：" + id);
            agentResult = termMachineService.terminalTransAgain(FastMap.fastMap("taskId", terminalTransferMapper.selectByPrimaryKey(terminalTransferDetailMapper.selectByPrimaryKey(id).getTerminalTransferId()).getTaskId())
                    .putKeyV("serialNumber", id)
                    .putKeyV("type", String.valueOf(terminalTransferDetailMapper.selectByPrimaryKey(id).getPlatformType()))
                    .putKeyV("posNum", String.valueOf(terminalTransferDetailMapper.selectByPrimaryKey(id).getComSnNum()))
                    .putKeyV("busPlatForm", String.valueOf(getAgentType(terminalTransferDetailMapper.selectByPrimaryKey(id).getOriginalOrgId()).get("BUS_PLATFORM"))));
            log.info("划拨解锁结束：" + JSONObject.toJSON(agentResult));

        } catch (Exception e) {
            log.error("划拨重新发起异常明细id：" + id);
            e.printStackTrace();
            agentResult = AgentResult.fail("划拨重新发起异常");
        }

        if (agentResult.isOK()) {
            JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
            String respCode = String.valueOf(jsonObject.get("respCode"));
            if ("000000".equals(respCode)) {
                String respType = String.valueOf(jsonObject.get("respType"));
                if ("S".equals(respType)) {
                    TerminalTransferDetail terminalTransferDetail = new TerminalTransferDetail();
                    terminalTransferDetail.setId(id);
                    terminalTransferDetail.setAdjustStatus(AdjustStatus.TZZ.getValue());
                    terminalTransferDetail.setRemark("");
                    terminalTransferDetail.setuTime(new Date());
                    terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                    agentResult = AgentResult.ok(jsonObject.get("respMsg"));
                } else {
                    agentResult = AgentResult.fail((String) jsonObject.get("respMsg"));
                }
            } else {
                agentResult = AgentResult.fail("划拨重新发起失败");
            }

        } else {
            agentResult = AgentResult.fail("划拨重新发起调用接口失败");
        }
        return agentResult;
    }

    @Override
    public void adjust(List<TerminalTransferDetail> terminalTransferDetails) throws MessageException {
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
            String originalOrgId = terminalTransferDetail.getOriginalOrgId().trim();
            Map<String, Object> map1 = getAgentType(originalOrgId);
            terminalTransferDetail.setPlatFrom(map1.get("BUS_PLATFORM").toString());
        }
        try {
            termMachineService.queryTerminalTransfer(terminalTransferDetails, TRANSFER_ZG_ADJUST, terminalTransferMapper.selectByPrimaryKey(terminalTransferDetails.get(0).getTerminalTransferId()).getTaskId());
        } catch (Exception e) {
            log.info("调用开始划拨接口时报错参数为 {}", JSONObject.toJSON(terminalTransferDetails));
        }
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
            terminalTransferDetail.setuTime(Calendar.getInstance().getTime());
            terminalTransferDetail.setAdjustStatus(AdjustStatus.TZZ.getValue());
            terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
        }
    }

    @Override
    public String queryTerminalTransferResult(TerminalTransferDetail terminalTransferDetail) throws Exception {
        AgentResult agentResult = null;
        try {
            log.info("手刷划拨开始查询" + terminalTransferDetail.getId());
            agentResult = termMachineService.queryTerminalTransferResult(terminalTransferDetail.getId(), terminalTransferDetail.getPlatformType().toString());
            log.info("手刷划拨返回：" + JSONObject.toJSONString(agentResult));
        } catch (Exception e) {
            e.printStackTrace();
            log.info("MPOS调用远程接口时异常==================：" + JSONObject.toJSONString(terminalTransferDetail));
            agentResult = AgentResult.fail();
        }


        if (agentResult != null) {
            if (agentResult.isOK()) {
                List<Map<String, Object>> mapList = (List<Map<String, Object>>) agentResult.getData();
                if (mapList == null || mapList.size() == 0) {
                    return String.valueOf(AdjustStatus.TZZ.getValue());
                }
                for (Map<String, Object> map : mapList) {
                    if ("2".equals(String.valueOf(map.get("operaStatus")))) {
                        if ("00".equals(map.get("status").toString())) {
                            log.info("MPOS划拨成功请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                            log.info("MPOS划拨成功请求结果：{}", JSONObject.toJSON(agentResult));
                            terminalTransferDetail.setAdjustStatus(AdjustStatus.YTZ.getValue());
                            terminalTransferDetail.setRemark(map.get("reason").toString());
                            terminalTransferDetail.setAdjustTime(new Date());
                            terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                            return String.valueOf(AdjustStatus.YTZ.getValue());
                        } else if ("01".equals(map.get("status").toString())) {
                            log.info("MPOS划拨处理中请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                            log.info("MPOS划拨处理中请求结果：{}", JSONObject.toJSON(agentResult));
                            return String.valueOf(AdjustStatus.TZZ.getValue());
                        } else {
                            log.info("MPOS划拨失败请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                            log.info("MPOS划拨失败请求结果：{}", JSONObject.toJSON(agentResult));
                            terminalTransferDetail.setRemark(map.get("reason").toString());
                            terminalTransferDetail.setAdjustStatus(AdjustStatus.TZSB.getValue());
                            terminalTransferDetail.setAdjustTime(new Date());
                            terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                            return String.valueOf(AdjustStatus.TZSB.getValue());
                        }
                    } else {
                        return String.valueOf(AdjustStatus.TZZ.getValue());
                    }
                }
            }
        } else {
            log.info("MPOS手刷划拨未联动请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
            log.info("MPOS查询获取数据失败，获取结果为空");
            return String.valueOf(AdjustStatus.TZZ.getValue());
        }
        return "";
    }

}
