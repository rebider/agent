package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.BusActRelExample;
import com.ryx.credit.service.agent.BusActRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public BusActRel findByBusIdAndType(String busId, String type) {
        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(busId).andBusTypeEqualTo(type).andStatusEqualTo(Status.STATUS_1.status);
        example.setOrderByClause(" c_time desc ");
        List<BusActRel> rel = busActRelMapper.selectByExample(example);
        return rel.size()>0?rel.get(0):null;
    }

    @Override
    public BusActRel findByProIns(String proIns) {
        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(proIns).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() != 1) {
            return null;
        }
        BusActRel rel = list.get(0);
        return rel;
    }

    @Override
    public int updateByPrimaryKey(BusActRel busActRel){
       return busActRelMapper.updateByPrimaryKey(busActRel);
    }
}
