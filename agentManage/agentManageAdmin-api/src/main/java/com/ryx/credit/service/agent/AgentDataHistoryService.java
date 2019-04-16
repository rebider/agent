package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.DataHistory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/6/8 10:38
 */
public interface AgentDataHistoryService {

    AgentResult saveDataHistory(Object object,String dataType);

    AgentResult saveDataHistory(Object object, String id, String dataType, String user, BigDecimal version);

    /**
     *查询所有的历史数据
     * @return
     */
    PageInfo selectAll(Page page, DataHistory dataHistory,String time);

    /**
     * 查询数据历史
     * @param dataId
     * @param dataType
     * @return
     */
    List<DataHistory> selectHistory(String dataId,String dataType);
}
