package com.ryx.credit.service.impl.agent;

import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.service.agent.BusActRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by RYX on 2018/7/6.
 */
@Service("busActRelService")
public class BusActRelServiceImpl implements BusActRelService {

    @Autowired
    private BusActRelMapper busActRelMapper;

    @Override
    public BusActRel findById(String activId){
        return busActRelMapper.findById(activId);
    }

}
