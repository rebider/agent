package com.ryx.credit.machine.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.*;
import org.apache.commons.collections.FastHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：cx
 * 时间：2018/10/10
 * 描述：手刷极具相关接口
 */
@Service("mposTermMachineServiceImpl")
public class MposTermMachineServiceImpl implements TermMachineService {

    private Logger logger = LoggerFactory.getLogger(MposTermMachineServiceImpl.class);

    public static final String MPOS_SUCESS_respCode = "000000";//000000-成功，000002-系统报错，000003-缺失参数，000004-其他, 100000-失败
    public static final String MPOS_SUCESS_respType = "S";//S-成功，E-报错，R-重复请求

    @Override
    public List<TermMachineVo> queryTermMachine(PlatformType platformType) throws Exception{
        JSONObject res = request(FastMap.fastSuccessMap(), AppConfig.getProperty("mpos.queryTermActive"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            JSONArray data =  res.getJSONArray("data");
            List<TermMachineVo> resData = new ArrayList<TermMachineVo>();
            for (int i = 0; i < data.size(); i++) {

                JSONObject item =  data.getJSONObject(i);
                String name = item.getString("termActiveName");
                String id = item.getString("termActiveId");

                TermMachineVo machineVo =  new TermMachineVo();
                machineVo.setId(id);
                machineVo.setMechineName(name);
                resData.add(machineVo);

            }
            return resData;
        }else{
            throw new MessageException(res.getString("respMsg"));
        }

    }

    @Override
    public List<MposTermBatchVo> queryMposTermBatch(PlatformType platformType) throws Exception{
        JSONObject res = request(FastMap.fastSuccessMap(), AppConfig.getProperty("mpos.queryTermBatch"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            JSONArray data =  res.getJSONArray("data");
            List<MposTermBatchVo> resData = new ArrayList<MposTermBatchVo>();
            for (int i = 0; i < data.size(); i++) {

                JSONObject item =  data.getJSONObject(i);
                String batchName = item.getString("batchName");
                String batchId = item.getString("batchId");

                MposTermBatchVo batchVo =  new MposTermBatchVo();
                batchVo.setBatchId(batchId);
                batchVo.setBatchName(batchName);
                resData.add(batchVo);

            }
            return resData;
        }else{
            throw new MessageException(res.getString("respMsg"));
        }
    }

    @Override
    public List<MposTermTypeVo> queryMposTermType(PlatformType platformType) throws Exception{
        JSONObject res = request(FastMap.fastSuccessMap(), AppConfig.getProperty("mpos.queryTermType"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            JSONArray data =  res.getJSONArray("data");
            List<MposTermTypeVo> resData = new ArrayList<MposTermTypeVo>();
            for (int i = 0; i < data.size(); i++) {

                JSONObject item =  data.getJSONObject(i);
                String name = item.getString("termTypeName");
                String id = item.getString("termTypeId");

                MposTermTypeVo termTypeVo =  new MposTermTypeVo();
                termTypeVo.setTermTypeId(id);
                termTypeVo.setTermTypeName(name);
                resData.add(termTypeVo);

            }
            return resData;
        }else{
            throw new MessageException(res.getString("respMsg"));
        }
    }


    /**
     * 机具的下发
     * @param lowerHairMachineVo
     * @return
     */
    @Override
    public AgentResult lowerHairMachine(LowerHairMachineVo lowerHairMachineVo) throws Exception{
        logger.info("Mpos机具的下发:");
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(lowerHairMachineVo));
        JSONObject res = request(data, AppConfig.getProperty("mpos.lowerHairMachine"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            JSONArray respdata =  res.getJSONArray("data");
            return AgentResult.ok();
        }else{
            throw new MessageException(res.getString("respMsg"));
        }
    }

    /**
     * 机具的调整，，退货是使用
     * @param adjustmentMachineVoList
     * @return
     */
    @Override
    public AgentResult adjustmentMachine(List<AdjustmentMachineVo> adjustmentMachineVoList) throws Exception{
        logger.info("Mpos机具的调整，退货是使用");
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(adjustmentMachineVoList));
        JSONObject res = request(data, AppConfig.getProperty("mpos.lowerHairMachine"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            JSONArray respdata =  res.getJSONArray("data");
            return AgentResult.ok();
        }else{
            throw new MessageException(res.getString("respMsg"));
        }
    }

    /**
     * 机具活动的变更
     * @param changeActMachineVoList
     * @return
     */
    @Override
    public AgentResult changeActMachine(List<ChangeActMachineVo> changeActMachineVoList) throws Exception{
        logger.info("Mpos机具的调整，，退货是使用");
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(changeActMachineVoList));
        JSONObject res = request(data, AppConfig.getProperty("mpos.lowerHairMachine"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            JSONArray respdata =  res.getJSONArray("data");
            return AgentResult.ok();
        }else{
            throw new MessageException(res.getString("respMsg"));
        }
    }

    @Override
    public JSONObject request(Map data, String url) throws Exception {
        String json = JsonUtil.objectToJson(data);
        logger.info("通知手刷请求参数：{},{}",url,json);
        String httpResult = HttpClientUtil.doPostJsonWithException(url, json);
        logger.info("通知手刷返回参数：{},{}",url,httpResult);
        if(StringUtils.isNotBlank(httpResult)) {
            JSONObject respXMLObj = JSONObject.parseObject(httpResult);
            return respXMLObj;
        }else{
            throw new Exception("请求出错");
        }

    }
}
