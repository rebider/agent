package com.ryx.credit.service.agent;

import com.ryx.credit.pojo.admin.agent.BusActRel;

/**
 * Created by RYX on 2018/7/6.
 */
public interface BusActRelService {

    BusActRel findById(String activId);

    BusActRel findByBusIdAndType(String busId,String type);

    BusActRel findByProIns(String proIns);

    int updateByPrimaryKey(BusActRel busActRel);
}
