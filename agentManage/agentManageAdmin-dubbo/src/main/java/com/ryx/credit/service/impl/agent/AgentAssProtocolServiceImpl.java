package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AssProtoColMapper;
import com.ryx.credit.dao.agent.AssProtoColRelMapper;
import com.ryx.credit.pojo.admin.agent.AssProtoCol;
import com.ryx.credit.pojo.admin.agent.AssProtoColExample;
import com.ryx.credit.pojo.admin.agent.AssProtoColRel;
import com.ryx.credit.pojo.admin.agent.AssProtoColRelExample;
import com.ryx.credit.service.agent.AgentAssProtocolService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by cx on 2018/6/5.
 */
@Service("agentAssProtocolService")
public class AgentAssProtocolServiceImpl implements AgentAssProtocolService {

    @Autowired
    private AssProtoColMapper assProtoColMapper;
    @Autowired
    private AssProtoColRelMapper assProtoColRelMapper;
    @Autowired
    private IdService idService;


    @Override
    public List<AssProtoCol> queryProtocol(String id, String plat) {
        AssProtoColExample example = new AssProtoColExample();
        AssProtoColExample.Criteria c = example.or().andProtocolStatusEqualTo(Status.STATUS_1.status);
        if(StringUtils.isNotBlank(id))c.andIdEqualTo(id);
        if(StringUtils.isNotBlank(plat))c.andPlatformEqualTo(plat);
        return assProtoColMapper.selectByExample(example);
    }

    @Override
    public int addProtocolRel(AssProtoColRel rel, String userId) {
        if(StringUtils.isEmpty(rel.getAgentBusinfoId()))return 0;
        if(StringUtils.isEmpty(rel.getAssProtocolId()))return 0;
        rel.setcTime(Calendar.getInstance().getTime());
        rel.setcUser(userId);
        rel.setStatus(Status.STATUS_1.status);
        return assProtoColRelMapper.insertSelective(rel);
    }

    @Override
    public List<AssProtoCol> queryProtoColByBusId(String busId) {
        AssProtoColRelExample example = new AssProtoColRelExample();
        example.or().andStatusEqualTo(Status.STATUS_1.status).andAgentBusinfoIdEqualTo(busId);
        List<AssProtoColRel>  rel = assProtoColRelMapper.selectByExample(example);
        List<AssProtoCol> proList = new ArrayList<>();
        for (AssProtoColRel assProtoColRel : rel) {
            AssProtoCol pro = assProtoColMapper.selectByPrimaryKey(assProtoColRel.getAssProtocolId());
            if(null!= pro)proList.add(pro);
        }
        return proList;
    }

    @Override
    public List<AssProtoColRel> queryProtoColByBusIds(List<String> busId) {
        if(busId == null || busId.size()==0)return new ArrayList<AssProtoColRel>();
        AssProtoColRelExample example = new AssProtoColRelExample();
        example.or().andStatusEqualTo(Status.STATUS_1.status).andAgentBusinfoIdIn(busId);
        List<AssProtoColRel>  rel = assProtoColRelMapper.selectByExample(example);
        return rel;
    }


    @Override
    public int updateAssProtoColRel(AssProtoColRel rel) {
        if(StringUtils.isEmpty(rel.getAssProtocolId())) return 0;
        if(StringUtils.isEmpty(rel.getAgentBusinfoId())) return 0;
        AssProtoColRelExample example =  new AssProtoColRelExample();
        example.or().andAgentBusinfoIdEqualTo(rel.getAgentBusinfoId()).andAssProtocolIdEqualTo(rel.getAssProtocolId());
        return assProtoColRelMapper.deleteByExample(example);
    }
}
