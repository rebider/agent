package com.ryx.jobOrder.service;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.jobOrder.pojo.JoOrder;

import java.util.List;
import java.util.Map;

public interface JobOrderQueryService {

    PageInfo jobOrderQueryList(Map map, Page page);

    JoOrder getByJobId(String id);


    PageInfo jobOrderQueryLaunchList(Map map, Page page);

    AgentResult jobOrderCancle(Map map) throws MessageException;

    /**
     * 结束工单
     * @param map
     * @return
     * @throws MessageException
     */
    AgentResult finishJobOrder(Map map) throws MessageException;

}
