package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.vo.AgentVo;

/**
 * @ClassName DateChangeReqService
 * @Description 数据变更记录
 * @Author RYX
 * @Date 2018/6/1
 **/
public interface DateChangeReqService {
    /**
     * 数据变更添加
     * @param agentVo
     * @return
     */
    public ResultVO dateChangeReqIn(String json);
}
