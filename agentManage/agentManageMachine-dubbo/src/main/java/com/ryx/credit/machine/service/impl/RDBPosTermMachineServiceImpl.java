package com.ryx.credit.machine.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.*;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：zyd
 * 时间：2019/8/13
 * 描述：瑞大宝 接口
 */
@Service("rdbTermMachineServiceImpl")
public class RDBPosTermMachineServiceImpl implements TermMachineService {

    private static final String RDB_SUCESS_RESPCODE = "0000";//000000-成功，000002-系统报错，000003-缺失参数，000004-其他, 100000-失败

    /**
     * 获取瑞大宝 相关数据具体操作
     * @param platformType
     * @return
     * @throws Exception
     */
    @Override
    public List<TermMachineVo> queryTermMachine(PlatformType platformType, Map<String, String> map) throws Exception {

        Map<String, String> reqMap = new HashMap<String, String>();

        // 封装参数
        if (null != map.get("busplatform")) {
            reqMap.put("branchId", map.get("busplatform"));
        } else {
            throw new MessageException("瑞大宝活动缺少必要参数");
        }

        JSONObject res = request(reqMap, AppConfig.getProperty("rdbpos.queryTermActive"));
        if(null!=res && RDB_SUCESS_RESPCODE.equals(res.getString("code"))){

            JSONArray data = res.getJSONObject("result").getJSONArray("termPolicyList");
            List<TermMachineVo> resData = new ArrayList<TermMachineVo>();

            for (int i = 0; i < data.size(); i++) {
                JSONObject item =  data.getJSONObject(i);
                String name = item.getString("terminalPolicyName");
                String id = item.getString("terminalPolicyId");
                TermMachineVo machineVo =  new TermMachineVo();
                machineVo.setId(id);
                machineVo.setMechineName(name);
                resData.add(machineVo);
            }
            return resData;
        }else{
            AppConfig.sendEmails(res.getString("respMsg"), "瑞大宝活动异常");
            throw new MessageException(res.getString("respMsg"));
        }
    }

    /**
     * 调用瑞大宝 接口获取活动相关数据
     * @param data
     * @param url
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject request(Map data, String url) throws Exception {
        try {
            String json = JsonUtil.objectToJson(data);
            String httpResult = HttpClientUtil.doPostJsonWithException(url, json);
            if(StringUtils.isNotBlank(httpResult)) {
                JSONObject respXMLObj = JSONObject.parseObject(httpResult);
                return respXMLObj;
            }else{
                AppConfig.sendEmails("错误信息："+httpResult, "瑞大宝接口异常");
                throw new Exception("请求出错");
            }
        } catch (Exception e) {
            e.printStackTrace();
            AppConfig.sendEmails("首刷发送请求失败："+ MailUtil.printStackTrace(e), "瑞大宝接口异常");
            throw e;
        }
    }

    @Override
    public List<MposTermBatchVo> queryMposTermBatch(PlatformType platformType) throws Exception {
        return null;
    }

    @Override
    public List<MposTermTypeVo> queryMposTermType(PlatformType platformType) throws Exception {
        return null;
    }

    @Override
    public AgentResult lowerHairMachine(LowerHairMachineVo lowerHairMachineVo) throws Exception {
        return null;
    }

    @Override
    public AgentResult adjustmentMachine(AdjustmentMachineVo adjustmentMachineVo) throws Exception {
        return null;
    }

    @Override
    public AgentResult changeActMachine(ChangeActMachineVo changeActMachineVo) throws Exception {
        return null;
    }

    @Override
    public AgentResult querySnMsg(PlatformType platformType, String snBegin, String snEnd) throws Exception {
        return null;
    }

    @Override
    public AgentResult queryTerminalTransfer(List<TerminalTransferDetail> terminalTransferDetailList, String operation) throws Exception {
        return null;
    }

    @Override
    public AgentResult queryTerminalTransferResult(String serialNumber, String type) throws Exception {
        return null;
    }

    @Override
    public AgentResult synOrVerifyCompensate(List<ORefundPriceDiffDetail> refundPriceDiffDetailList, String operation) throws Exception {
        return  AgentResult.ok("未联动");
    }

    @Override
    public AgentResult queryCompensateResult(String serialNumber,String platformType) throws Exception {
        return AgentResult.ok("04");
    }
}
