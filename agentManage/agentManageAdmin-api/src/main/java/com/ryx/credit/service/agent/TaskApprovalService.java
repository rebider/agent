package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentVo;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/30 16:51
 */
public interface TaskApprovalService {

    /**
     * 添加
     * @param busActRel
     * @return
     */
    int addABusActRel(BusActRel busActRel)throws Exception;

    /**
     * 更新关系
     * @param busActRel
     * @return
     */
    int updateABusActRel(BusActRel busActRel)throws Exception;

    /**
     * 查询
     * @param busActRel
     * @return
     */
    BusActRel queryBusActRel(BusActRel busActRel)throws Exception;


    List<Map<String, Object>> queryBusInfoAndRemit(AgentBusInfo agentBusInfo);

    List<Map<String, Object>> queryBusInfoAndRemitByBusId(String busId);

    AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception;

    AgentResult updateApproval(AgentVo agentVo,String userId) throws Exception;

    Map findBusActByBusId(String busId,String busType,String activStatus);

    BusActRel queryBusActRel(String busId, String busType,String activStatus);

    List<Map<String, Object>> queryById(AgentBusInfo agentBusInfo);

    void updateShrioBusActRel();
}
