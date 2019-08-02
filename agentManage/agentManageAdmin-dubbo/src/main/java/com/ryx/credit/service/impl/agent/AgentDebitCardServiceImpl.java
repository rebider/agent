package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.agent.AgentDebitCardMapper;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;
import com.ryx.credit.service.agent.AgentDebitCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结算卡管理实现雷
 * Created by Chen Qiutian on 2019/7/31.
 */
@Service("agentDebitCardService")
public class AgentDebitCardServiceImpl implements AgentDebitCardService {

    @Autowired
    private AgentDebitCardMapper agentDebitCardMapper;

    @Override
    public PageInfo getDebitCardList(Map<String, String> map, Page page) {
        PageInfo pageInfo = new PageInfo();
        List<Map<String,String>> list = agentDebitCardMapper.queryForList(map,page);
        pageInfo.setRows(list);
        pageInfo.setTotal(agentDebitCardMapper.countQuery(map));
        return pageInfo;
    }

    @Override
    public List<Map<String,String>> exports(Map<String,String> map){
        return  agentDebitCardMapper.exports(map);
    }

    @Override
    public List<Map<String,String>> getBusInfoById(String id){
        return agentDebitCardMapper.getBusInfoById(id);
    }

    @Override
    public Map<String,String> getColAndAgentById(String id){
        Map<String,String> stringMap = new HashMap<String,String>();
        stringMap.put("agentId",id);
        return agentDebitCardMapper.queryForList(stringMap,null).get(0);

    }

    @Override
    public void updateSuggestStatusById(String id,String statu)throws MessageException{
        try {
            agentDebitCardMapper.updateSuggestStatusById(id,statu);
        }catch (Exception e){
            throw new MessageException("修改失败");
        }
    }


}
