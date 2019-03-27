package com.ryx.credit.activity.service.impl;

import com.ryx.credit.activity.dao.ActRuTaskMapper;
import com.ryx.credit.activity.entity.ActRuTask;
import com.ryx.credit.activity.entity.ActRuTaskExample;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.service.ActRuTaskService;
import com.ryx.credit.service.CRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ActRuTaskServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2018/5/22
 * @Time: 15:17
 * @description: ActRuTaskServiceImpl
 * To change this template use File | Settings | File Templates.
 */
@Service("actRuTaskService")
public class ActRuTaskServiceImpl implements ActRuTaskService {
    @Autowired
    private ActRuTaskMapper  actRuTaskMapper;
    @Autowired
    private CRoleService roleService;

    @Override
    public int insert(ActRuTask record) {
        return actRuTaskMapper.insert(record);
    }

    @Override
    public int insertSelective(ActRuTask record) {
        return actRuTaskMapper.insertSelective(record);
    }

    @Override
    public ActRuTask selectByPrimaryKey(Object id) {
        return actRuTaskMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ActRuTask record) {
        return actRuTaskMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ActRuTask record) {
        return actRuTaskMapper.updateByPrimaryKey(record);
    }

    @Override
    public HashMap<String, Object> configExample(Page page, ActRuTask actRuTask) {
        if (actRuTask != null && page != null) {
            ActRuTaskExample actRuTaskExample = new ActRuTaskExample();
            ActRuTaskExample.Criteria criteria = actRuTaskExample.or();
            if (actRuTask.getId() != null) {
                criteria.andIdEqualTo(actRuTask.getId());
            }

            if (actRuTask.getExecutionId() != null) {
                criteria.andExecutionIdEqualTo(actRuTask.getExecutionId());
            }
            if (actRuTask.getProcInstId() != null) {
                criteria.andProcInstIdEqualTo(actRuTask.getProcInstId());
            }

            if (actRuTask.getProcDefId() != null) {
                criteria.andProcDefIdEqualTo(actRuTask.getProcDefId());
            }

            if (actRuTask.getName() != null) {
                criteria.andNameEqualTo(actRuTask.getName());
            }

            if (actRuTask.getAssignee() != null) {
                criteria.andAssigneeEqualTo(actRuTask.getAssignee());
            }

            int count = actRuTaskMapper.countByExample(actRuTaskExample);
            page.setCount(count);
            actRuTaskExample.setPage(page);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("list", actRuTaskMapper.selectByExample(actRuTaskExample));
            hashMap.put("page", page);
            return hashMap;
        } else {
            return null;
        }
    }


    @Override
    public List<Map<String, Object>>  queryMyTask(Map<String,Object> param){
        List<Map<String, Object>> taskList = actRuTaskMapper.queryMyTask(param);
        return taskList;
    }


    @Override
    public PageInfo queryMyTaskPage(Page page, Map<String,Object> param){

        Set<String> dbUrls = roleService.selectShiroUrl((Long) param.get("userId"),"451143","/BusActRelBusType");
        Set<String> netInUrls = roleService.selectShiroUrl((Long) param.get("userId"),"672403","");

        param.put("dbUrls",dbUrls);
        param.put("netInUrls",netInUrls);
        List<Map<String, Object>> taskList = actRuTaskMapper.queryMyTaskPage(param,page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(taskList);
        pageInfo.setTotal(actRuTaskMapper.queryMyTaskCount(param));

        return pageInfo;
    }
}
