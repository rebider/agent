package com.ryx.jobOrder.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.service.dict.IdService;
import com.ryx.jobOrder.dao.JoTaskMapper;
import com.ryx.jobOrder.pojo.JoTask;
import com.ryx.jobOrder.pojo.JoTaskExample;
import com.ryx.jobOrder.service.JobOrderTaskService;
import com.ryx.jobOrder.vo.JoTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service("jobOrderTaskService")
public class JobOrderTaskServiceImpl implements JobOrderTaskService {

    private final BigDecimal version = new BigDecimal(0);

    @Autowired
    private JoTaskMapper joTaskMapper;

    @Autowired
    private IdService idService;

    @Override
    public FastMap createJobOrderTask(JoTask joTask) throws Exception{
        joTask.setId( idService.genId(TabId.jo_task) );
        joTask.setVersion(version);
        int status = joTaskMapper.insert(joTask);
        if(status != 1){
            throw new MessageException("插入失败" + joTask.getId());
        }
        return FastMap.fastSuccessMap();
    }

    public PageInfo queryJobOrderTaskVo(JoTaskVo joTaskVo, Page page){
        List jotaskVolist = joTaskMapper.selectByJoTaskVo(joTaskVo, page);
        PageInfo pageInfo = new PageInfo();
        int count = jotaskVolist.size();
        pageInfo.setTotal(count);
        pageInfo.setRows(jotaskVolist);
        return  pageInfo;
    }

    @Override
    public List<JoTask> queryJobOrderTask(JoTask joTask) {
        JoTaskExample joTaskExample = queryParam(joTask);
        return joTaskMapper.selectByExample(joTaskExample);
    }

    @Override
    public FastMap updateJobOrderTask(JoTask joTask) throws Exception{
        int status = joTaskMapper.updateByPrimaryKeySelective(joTask);
        if(status != 1){
            throw new MessageException("更新失败");
        }
        return FastMap.fastSuccessMap();
    }

    private JoTaskExample queryParam(JoTask joTask){
        JoTaskExample joTaskExample = new JoTaskExample();
        JoTaskExample.Criteria criteria = joTaskExample.createCriteria();
        if(StringUtils.isNotBlank(joTask.getId())){
            criteria.andIdEqualTo(joTask.getId());
        }
        if(StringUtils.isNotBlank(joTask.getJoId())){
            criteria.andIdEqualTo(joTask.getJoId());
        }
        if(StringUtils.isNotBlank(joTask.getDealGroupId())){
            criteria.andDealGroupIdEqualTo(joTask.getDealGroupId());
        }
        if(StringUtils.isNotBlank(joTask.getDealPersonId())){
            criteria.andDealPersonIdEqualTo(joTask.getDealPersonId());
        }
        return joTaskExample;
    }
}
