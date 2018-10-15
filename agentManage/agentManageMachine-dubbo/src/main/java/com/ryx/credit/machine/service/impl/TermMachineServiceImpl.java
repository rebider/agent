package com.ryx.credit.machine.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.*;
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


    @Resource(name = "posTermMachineServiceImpl")
    private TermMachineService posTermMachineServiceImpl;

    @Resource(name = "mposTermMachineServiceImpl")
    private TermMachineService mposTermMachineServiceImpl;




    @Override
    public List<TermMachineVo> queryTermMachine(PlatformType platformType) throws Exception{
        switch (platformType.name()){
            case "POS":
                return posTermMachineServiceImpl.queryTermMachine(platformType);
            case "ZPOS":
                return posTermMachineServiceImpl.queryTermMachine(platformType);
            case "MPOS":
                return mposTermMachineServiceImpl.queryTermMachine(platformType);
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public List<MposTermBatchVo> queryMposTermBatch(PlatformType platformType) throws Exception{
        switch (platformType.name()){
            case "MPOS":
                return mposTermMachineServiceImpl.queryMposTermBatch(platformType);
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public List<MposTermTypeVo> queryMposTermType(PlatformType platformType) throws Exception{
        switch (platformType.name()){
            case "MPOS":
                return mposTermMachineServiceImpl.queryMposTermType(platformType);
            default:
                return new ArrayList<>();
        }
    }

    /**
     * 机具的下发
     * @param lowerHairMachineVo
     * @return
     */
    @Override
    public AgentResult lowerHairMachine(LowerHairMachineVo lowerHairMachineVo) throws Exception{
        return mposTermMachineServiceImpl.lowerHairMachine(lowerHairMachineVo);
    }

    /**
     * 机具的调整，，退货是使用
     * @param adjustmentMachineVo
     * @return
     */
    @Override
    public AgentResult adjustmentMachine(AdjustmentMachineVo adjustmentMachineVo) throws Exception{
        return mposTermMachineServiceImpl.adjustmentMachine(adjustmentMachineVo);
    }

    /**
     * 机具活动的变更
     * @param changeActMachineVoList
     * @return
     */
    @Override
    public AgentResult changeActMachine(List<ChangeActMachineVo> changeActMachineVoList) throws Exception{
        return mposTermMachineServiceImpl.changeActMachine(changeActMachineVoList);
    }

    @Override
    public JSONObject request(Map data, String url) throws Exception {
        return null;
    }
}
