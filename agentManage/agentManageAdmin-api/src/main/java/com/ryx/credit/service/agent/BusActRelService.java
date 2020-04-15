package com.ryx.credit.service.agent;

import com.ryx.credit.pojo.admin.agent.BusActRel;

import java.util.List;

/**
 * Created by RYX on 2018/7/6.
 */
public interface BusActRelService {

    BusActRel findById(String activId);

    BusActRel findByBusIdAndType(String busId,String type);

    BusActRel findByProIns(String proIns);

    int updateByPrimaryKey(BusActRel busActRel);

    /**
     * 根据类型及审批状态查询实例
     * @param busType
     * @param actStatus
     * @return
     */
    List<BusActRel> queryBysBusTypeAndStatus(String busType,String actStatus);
}
