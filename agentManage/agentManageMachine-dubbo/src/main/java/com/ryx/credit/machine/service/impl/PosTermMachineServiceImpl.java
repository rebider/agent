package com.ryx.credit.machine.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.MposTermBatchVo;
import com.ryx.credit.machine.vo.MposTermTypeVo;
import com.ryx.credit.machine.vo.TermMachineVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：cx
 * 时间：2018/10/10
 * 描述：POS极具相关接口
 */
@Service("posTermMachineServiceImpl")
public class PosTermMachineServiceImpl  implements TermMachineService {

    @Override
    public List<TermMachineVo> queryTermMachine(PlatformType platformType) throws Exception{
        return null;
    }

    @Override
    public List<MposTermBatchVo> queryMposTermBatch(PlatformType platformType) throws Exception{
        return new ArrayList<>();
    }

    @Override
    public List<MposTermTypeVo> queryMposTermType(PlatformType platformType) throws Exception{
        return new ArrayList<>();
    }

    @Override
    public JSONObject request(Map data, String url) throws Exception {
        return new JSONObject();
    }
}
