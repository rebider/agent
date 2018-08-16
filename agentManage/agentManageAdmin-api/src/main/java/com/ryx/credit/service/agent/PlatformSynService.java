package com.ryx.credit.service.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.result.AgentResult;

import java.util.Map;

/**
 * Created by cx on 2018/8/11.
 * 平台接口调用
 */
public interface PlatformSynService {


    public Boolean isMyPlatform(String id);

    public Boolean isMyPlatformByPlatformCode(String platformCode);


    /**
     * 代理商升级 进入入网管理
     * @return
     */
    public AgentResult agencyLevelUpdateChange(Map data)throws Exception;

    /**
     * 理商升级 进入入网管理 入网数据准备
     * 接口调用参数使用
     * @param data
     * @return
     */
    public Map agencyLevelUpdateChangeData(Map data);

    /**
     * 发送请求
     * @param data
     * @param url
     * @return
     * @throws Exception
     */
    public JSONObject request(Map data,String url)throws Exception;



}
