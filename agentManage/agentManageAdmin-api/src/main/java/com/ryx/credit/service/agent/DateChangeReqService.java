package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.DateChangeRequest;

import java.util.Map;

/**
 * @ClassName DateChangeReqService
 * @Description 数据变更记录
 * @Author RYX
 * @Date 2018/6/1
 **/
public interface DateChangeReqService {
    /**
     * 数据变更添加
     * @param
     * @return
     */
    public ResultVO dateChangeReqIn(String json,String oldJson,String srcId,String type,String userId);

    public DateChangeRequest getById(String id);

    /**
     * 数据变更的查询
     */
    public PageInfo queryData( Page page,Map map);
}
