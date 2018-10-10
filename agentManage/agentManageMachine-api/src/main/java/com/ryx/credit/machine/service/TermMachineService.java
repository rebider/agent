package com.ryx.credit.machine.service;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.machine.vo.MposTermBatchVo;
import com.ryx.credit.machine.vo.MposTermTypeVo;
import com.ryx.credit.machine.vo.TermMachineVo;

import java.util.List;
import java.util.Map;

/**
 * 作者：cx
 * 时间：2018/10/10
 * 描述：业务系统极具终端操作接口
 */
public interface TermMachineService {



    /**
     * 查询平台极具，手刷查询手刷活动，pos查询pos极具
     * @param platformType
     * @return
     */
    public List<TermMachineVo> queryTermMachine(PlatformType platformType)throws Exception;


    /**
     * MPOS查询终端批次
     * @param platformType
     * @return
     */
    public List<MposTermBatchVo> queryMposTermBatch(PlatformType platformType)throws Exception;


    /**
     * MPOS查询终端类型
     * @param platformType
     * @return
     */
    public List<MposTermTypeVo> queryMposTermType(PlatformType platformType)throws Exception;

    /**
     * 发送请求
     * @param data
     * @param url
     * @return
     * @throws Exception
     */
     JSONObject request(Map data, String url)throws Exception;


}
