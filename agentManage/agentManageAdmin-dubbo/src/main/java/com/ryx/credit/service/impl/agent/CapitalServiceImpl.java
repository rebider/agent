package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.CapitalMapper;
import com.ryx.credit.pojo.admin.agent.Capital;
import com.ryx.credit.pojo.admin.agent.CapitalExample;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.CapitalService;
import com.ryx.credit.service.dict.DictOptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/10/11
 * 保证金
 */
@Service("capitalService")
public class CapitalServiceImpl implements CapitalService {

    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private IUserService iUserService;

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


    /**
     * 汇总列表
     * @param param
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo getCapitalSummaryList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = capitalMapper.getCapitalSummaryCount(param);
        List<Map<String, Object>> list = capitalMapper.getCapitalSummaryList(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        return pageInfo;
    }

    /**
     * 缴纳款记录
     * @param capital
     * @param page
     * @param userId
     * @return
     */
    @Override
    public PageInfo queryCapitalList(Capital capital, Page page, String dataRole, Long userId) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(capital.getId())) {
            reqMap.put("id", capital.getId());
        }
        if (StringUtils.isNotBlank(capital.getcAgentId())) {
            reqMap.put("agentId", capital.getcAgentId());
        }
        if(StringUtils.isBlank(dataRole)){
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
            if(orgCodeRes == null && orgCodeRes.size() != 1){
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            reqMap.put("orgId", String.valueOf(stringObjectMap.get("ORGID")));
        }
        List<Map<String, Object>> capitalChangeList = capitalMapper.queryCapitalList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(capitalChangeList);
        pageInfo.setTotal(capitalMapper.queryCapitalCount(reqMap));
        return pageInfo;
    }

}
