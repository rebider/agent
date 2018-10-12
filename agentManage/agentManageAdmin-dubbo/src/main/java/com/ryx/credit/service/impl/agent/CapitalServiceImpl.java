package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.CapitalMapper;
import com.ryx.credit.pojo.admin.agent.Capital;
import com.ryx.credit.pojo.admin.agent.CapitalExample;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.service.agent.CapitalService;
import com.ryx.credit.service.dict.DictOptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Lihl
 * @Date 2018/10/11
 * 资金记录：查询保证金信息
 */
@Service("capitalService")
public class CapitalServiceImpl implements CapitalService{

    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    private DictOptionsService dictOptionsService;

    @Override
    public List<Capital> queryCapital(String agentId) {
        if(StringUtils.isNotBlank(agentId)){
            CapitalExample capitalExample = new CapitalExample();
            CapitalExample.Criteria criteria = capitalExample.createCriteria();
            criteria.andCAgentIdEqualTo(agentId);
            List<Capital> capitals = capitalMapper.selectByExample(capitalExample);
            for (Capital capital : capitals) {
                Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.CAPITAL_TYPE.name(), capital.getcType());
                if(null!=dictByValue)
                capital.setcType(dictByValue.getdItemname());
            }
            return capitals;
        }
        return null;
    }

}
