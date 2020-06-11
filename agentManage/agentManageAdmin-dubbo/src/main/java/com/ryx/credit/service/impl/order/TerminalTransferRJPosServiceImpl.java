package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONArray;
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
 * @Date 2020/6/10 15:36
 * @Version 1.0
 */
@Service("terminalTransferRJPosService")
public class TerminalTransferRJPosServiceImpl extends TerminalTransferBase implements TerminalTransferFactory {

    private static Logger log = LoggerFactory.getLogger(TerminalTransferRJPosServiceImpl.class);

    @Autowired
    private TermMachineService termMachineService;
    @Autowired
    private TerminalTransferMapper terminalTransferMapper;
    @Autowired
    private TerminalTransferDetailMapper terminalTransferDetailMapper;


    @Override
    public void platformSameCheck(TerminalTransferDetail terminalTransferDetail) throws MessageException {
        String platformType = disBusCode(terminalTransferDetail);
        if (!platformType.equals(PlatformType.RJPOS.getValue())) {
            log.info("您的机构码不属于瑞+平台请选择：原：" + terminalTransferDetail.getOriginalOrgId() + "目标：" + terminalTransferDetail.getGoalOrgId());
            throw new MessageException("您的机构码不属于瑞+平台请选择：原：" + terminalTransferDetail.getOriginalOrgId() + "目标：" + terminalTransferDetail.getGoalOrgId());
        }

    }

    @Override
    public void check(List<TerminalTransferDetail> terminalTransferDetails) throws MessageException {
        AgentResult agentResult = null;
        JSONObject jsonObject;
        JSONObject jsonData;
        List<Map<String, String>> datalist;
        try {
            agentResult = termMachineService.queryTerminalTransfer(terminalTransferDetails, TRANSFER_ZG_CHECK, terminalTransferMapper.selectByPrimaryKey(terminalTransferDetails.get(0).getTerminalTransferId()).getTaskId());
            log.info("RJ校验返回结果：" + JSONObject.toJSON(agentResult));
            jsonObject = JSONObject.parseObject(agentResult.getMsg());
            jsonData = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
            datalist = (List<Map<String, String>>) JSONArray.parse(String.valueOf(jsonData.get("data")));
        } catch (Exception e) {
            log.error("RJPOS获得检验结果异常");
            throw new MessageException("RJPOS获得检验结果异常");
        }
        if (agentResult.isOK()) {
            for (Map<String, String> data : datalist) {
                String serialNumberStatus = String.valueOf(data.get("serialNumberStatus"));
                if (!"00".equals(serialNumberStatus)) {
                    String serialNumberMsg = String.valueOf(data.get("serialNumberMsg"));
                    log.info("调用RJ接口查询验证接口返回异常");
                    throw new MessageException(serialNumberMsg);
                }
            }
        } else {
            String respMsg = String.valueOf(jsonObject.get("respMsg"));
            log.info("RJ未连通查询检测接口" + respMsg);
            throw new MessageException("RJ未连通查询检测接口" + respMsg);
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
            String respCode = String.valueOf(jsonObject.get("respCode"));
            JSONObject JSONObjectData = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
            /* JSONObject data = JSONObject.parseObject(String.valueOf(JSONObjectData.get("data")));*/
            List<Map<String, String>> maps = (List<Map<String, String>>) JSONArray.parse(String.valueOf(JSONObjectData.get("data")));
            if ("000000".equals(respCode)) {
                for (Map<String, String> data : maps) {
                    String resultCode = String.valueOf(data.get("resultCode"));
                    String resultMsg = String.valueOf(data.get("resultMsg"));
                    if ("00".equals(resultCode)) {
                        agentResult = AgentResult.ok(resultMsg);
                    } else if ("01".equals(resultCode)) {
                        log.info("终端划拨解锁失败：" + String.valueOf(data.get("serialNumber")) + "解锁失败" + resultMsg);
                        agentResult = AgentResult.fail(String.valueOf(data.get("serialNumber")) + "解锁失败" + resultMsg);
                    }
                }

            } else {
                agentResult = AgentResult.fail("划拨解锁处理失败");
            }

        } else {
            agentResult = AgentResult.fail("划拨解锁调用接口失败");
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
            agentResult = termMachineService.terminalTransAgain(FastMap.fastMap("taskId", terminalTransferMapper.selectByPrimaryKey(terminalTransferDetailMapper.selectByPrimaryKey(id).getTerminalTransferId()).getTaskId()).putKeyV("serialNumber", id).putKeyV("type", String.valueOf(terminalTransferDetailMapper.selectByPrimaryKey(id).getPlatformType())));
            log.info("划拨解锁结束：" + JSONObject.toJSON(agentResult));

        } catch (Exception e) {
            log.error("划拨重新发起异常明细id：" + id);
            e.printStackTrace();
            agentResult = AgentResult.fail("划拨重新发起异常");
        }

        if (agentResult.isOK()) {
            JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
            String respCode = String.valueOf(jsonObject.get("respCode"));
            JSONObject JSONObjectData = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
            List<Map<String, String>> maps = (List<Map<String, String>>) JSONArray.parse(String.valueOf(JSONObjectData.get("data")));
            if ("000000".equals(respCode)) {
                for (Map<String, String> data : maps) {
                    String serialNumberStatus = String.valueOf(data.get("serialNumberStatus"));
                    String serialNumberMsg = String.valueOf(data.get("serialNumberMsg"));
                    String serialNumber = String.valueOf(data.get("serialNumber"));
                    if ("00".equals(serialNumberStatus)) {
                        TerminalTransferDetail terminalTransferDetail = new TerminalTransferDetail();
                        terminalTransferDetail.setId(serialNumber.trim());
                        terminalTransferDetail.setAdjustStatus(AdjustStatus.TZZ.getValue());
                        terminalTransferDetail.setRemark("");
                        terminalTransferDetail.setuTime(new Date());
                        terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                        agentResult = AgentResult.ok(serialNumberMsg);
                    } else if ("01".equals(serialNumberStatus)) {
                        agentResult = AgentResult.fail(serialNumberMsg);
                    }
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
            log.info("瑞+划拨开始查询:" + terminalTransferDetail.getId());
            agentResult = termMachineService.queryTerminalTransferResult(terminalTransferDetail.getId(), terminalTransferDetail.getPlatformType().toString());
            log.info("划拨开始查询返回：" + JSONObject.toJSONString(agentResult));
        } catch (Exception e) {
            e.printStackTrace();
            log.info("划拨开始查询调用远程接口时异常==================：" + JSONObject.toJSONString(terminalTransferDetail));
            agentResult = AgentResult.fail();
        }

        if (agentResult != null) {
            if (agentResult.isOK()) {
                JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
                String respCode = (String) jsonObject.get("respCode");
                JSONObject JSONObjectData = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
                JSONObject data = JSONObject.parseObject(String.valueOf(JSONObjectData.get("data")));
                String resMsg = String.valueOf(data.get("resMsg"));
                if ("000000".equals(respCode)) {
                    String transferStatus = String.valueOf(data.get("transferStatus"));
                    if ("00".equals(transferStatus)) {
                        log.info("瑞+划拨成功请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                        terminalTransferDetail.setAdjustStatus(AdjustStatus.YTZ.getValue());
                        terminalTransferDetail.setRemark(resMsg);
                        terminalTransferDetail.setAdjustTime(new Date());
                        terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                        return String.valueOf(AdjustStatus.YTZ.getValue());
                    } else if ("01".equals(transferStatus)) {
                        log.info("瑞+划拨中请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                        log.info("瑞+划拨中请求结果：{}", JSONObject.toJSON(agentResult));
                        return String.valueOf(AdjustStatus.TZZ.getValue());
                    } else if ("02".equals(transferStatus)) {
                        log.info("瑞+划拨失败请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                        log.info("瑞+划拨失败请求结果：{}", JSONObject.toJSON(agentResult));
                        terminalTransferDetail.setRemark(resMsg);
                        terminalTransferDetail.setAdjustStatus(AdjustStatus.TZSB.getValue());
                        terminalTransferDetail.setAdjustTime(new Date());
                        terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                        return String.valueOf(AdjustStatus.TZSB.getValue());
                    }
                } else {
                    log.info("瑞+未查到划拨结果的请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                    log.info("瑞+未查到划拨结果的请求结果：{}", JSONObject.toJSON(agentResult));
                    terminalTransferDetail.setRemark(resMsg);
                    terminalTransferDetail.setAdjustStatus(AdjustStatus.WLDTZ.getValue());
                    terminalTransferDetail.setAdjustTime(new Date());
                    terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                    return String.valueOf(AdjustStatus.WLDTZ.getValue());
                }

            } else {
                log.info("瑞+未连通查询请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                log.info("瑞+未连通查询请求结果：{}", JSONObject.toJSON(agentResult));
                return String.valueOf(AdjustStatus.TZZ.getValue());
            }

        } else {
            log.info("RJPOS查询获取数据失败，获取结果为空");
            throw new MessageException("RJPOS查询获取数据失败，获取结果为空");
        }
        return "";
    }
}
