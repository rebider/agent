package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AdjustStatus;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
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
 * @Author chenliang
 * @Date 2020/6/10 15:34
 * @Version 1.0
 */
@Service("terminalTransferPosService")
public class TerminalTransferPosServiceImpl extends TerminalTransferBase implements TerminalTransferFactory {
    private static Logger log = LoggerFactory.getLogger(TerminalTransferPosServiceImpl.class);

    @Autowired
    private TermMachineService termMachineService;
    @Autowired
    private TerminalTransferMapper terminalTransferMapper;
    @Autowired
    private TerminalTransferDetailMapper terminalTransferDetailMapper;


    @Override
    public void platformSameCheck(TerminalTransferDetail terminalTransferDetail) throws MessageException{
        String platformType = disBusCode(terminalTransferDetail);
        if (!(platformType.equals(PlatformType.POS.getValue()) || platformType.equals(PlatformType.ZPOS.getValue()) || platformType.equals(PlatformType.ZHPOS.getValue()) || platformType.equals(PlatformType.SSPOS.getValue()))) {
            log.info("您的机构码不属于pos平台请选择：原：" + terminalTransferDetail.getOriginalOrgId() + "目标：" + terminalTransferDetail.getGoalOrgId());
            throw new MessageException("您的机构码不属于pos平台请选择：原：" + terminalTransferDetail.getOriginalOrgId() + "目标：" + terminalTransferDetail.getGoalOrgId());
        }
    }

    @Override
    public void check(List<TerminalTransferDetail> terminalTransferDetails) throws MessageException {
        //检查提交数据是否可执行
            AgentResult agentResult = null;
            JSONObject jsonObject;
            JSONObject data;
            try {
                agentResult = termMachineService.queryTerminalTransfer(terminalTransferDetails, TRANSFER_ZG_CHECK, terminalTransferMapper.selectByPrimaryKey(terminalTransferDetails.get(0).getTerminalTransferId()).getTaskId());
                jsonObject = JSONObject.parseObject(agentResult.getMsg());
                data = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
            } catch (Exception e) {
                log.error("POS获得检验结果异常");
                throw new MessageException("POS获得检验结果异常");
            }
            if (agentResult.isOK()) {
                String result_code = String.valueOf(data.get("result_code"));
                if (!"000000".equals(result_code)) {
                    log.info("调用POS接口查询验证接口返回异常");
                    throw new MessageException("调用POS接口查询验证接口返回异常");
                }
            } else {
                String result_msg = String.valueOf(data.get("result_msg"));
                if (result_msg.indexOf("TTD") != -1) {
                    String ttd = result_msg.substring(result_msg.indexOf("TTD"), Integer.valueOf(result_msg.indexOf("TTD")) + 26);
                    TerminalTransferDetail terminalTransferDetail = terminalTransferDetailMapper.selectByPrimaryKey(ttd);
                    if (terminalTransferDetail != null) {
                        log.info("POS未连通查询检测接口" + terminalTransferDetail.getSnBeginNum() + "--------" + terminalTransferDetail.getSnEndNum());
                        throw new MessageException("在区间" + terminalTransferDetail.getSnBeginNum() + "--------" + terminalTransferDetail.getSnEndNum() + "   " + result_msg);
                    } else {
                        log.info("POS未连通查询检测接口" + result_msg);
                        throw new MessageException(result_msg);
                    }
                }
                log.info("POS未连通查询检测接口" + result_msg);
                throw new MessageException(result_msg);
            }
    }

    @Override
    public AgentResult terminalTransferunlock(String taskId, String serialNumber, String type) {
        return AgentResult.ok();
    }

    @Override
    public AgentResult adjustAgain(String id) {
        return null;
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
            log.info("pos划拨开始查询:" + terminalTransferDetail.getId());
            agentResult = termMachineService.queryTerminalTransferResult(terminalTransferDetail.getId(), terminalTransferDetail.getPlatformType().toString());
            log.info("POS划拨返回：" + JSONObject.toJSONString(agentResult));
        } catch (Exception e) {
            e.printStackTrace();
            log.info("POS调用远程接口时异常==================：" + JSONObject.toJSONString(terminalTransferDetail));
            agentResult = AgentResult.fail();
        }

        if (agentResult != null) {
            if (agentResult.isOK()) {
                JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
                JSONObject data = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
                String result_code = String.valueOf(data.get("result_code"));
                String resMsg = String.valueOf(data.get("resMsg"));
                if ("000000".equals(result_code)) {
                    String transferStatus = String.valueOf(data.get("transferStatus"));
                    if ("00".equals(transferStatus)) {
                        log.info("POS划拨成功请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                        terminalTransferDetail.setAdjustStatus(AdjustStatus.YTZ.getValue());
                        terminalTransferDetail.setRemark(resMsg);
                        terminalTransferDetail.setAdjustTime(new Date());
                        terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                        return String.valueOf(AdjustStatus.YTZ.getValue());
                    } else if ("01".equals(transferStatus)) {
                        log.info("POS划拨中请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                        log.info("POS划拨中请求结果：{}", JSONObject.toJSON(agentResult));
                        return String.valueOf(AdjustStatus.TZZ.getValue());
                    } else if ("02".equals(transferStatus)) {
                        log.info("POS划拨失败请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                        log.info("POS划拨失败请求结果：{}", JSONObject.toJSON(agentResult));
                        terminalTransferDetail.setRemark(resMsg);
                        terminalTransferDetail.setAdjustStatus(AdjustStatus.TZSB.getValue());
                        terminalTransferDetail.setAdjustTime(new Date());
                        terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                        return String.valueOf(AdjustStatus.TZSB.getValue());
                    }
                } else {
                    log.info("POS未查到划拨结果请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                    log.info("POS未查到划拨结果请求结果：{}", JSONObject.toJSON(agentResult));
                    terminalTransferDetail.setRemark(resMsg);
                    terminalTransferDetail.setAdjustStatus(AdjustStatus.WLDTZ.getValue());
                    return String.valueOf(AdjustStatus.WLDTZ.getValue());
                }

            } else {
                log.info("POS未连通查询请求参数：{}", JSONObject.toJSON(terminalTransferDetail));
                log.info("POS未连通查询请求结果：{}", JSONObject.toJSON(agentResult));
                return String.valueOf(AdjustStatus.TZZ.getValue());
            }

        }else {
            log.info("POS查询获取数据失败，获取结果为空");
            throw new MessageException("POS查询获取数据失败，获取结果为空");
        }
        return "";
    }

}
