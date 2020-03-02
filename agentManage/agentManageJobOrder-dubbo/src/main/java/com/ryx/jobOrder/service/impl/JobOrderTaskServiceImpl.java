package com.ryx.jobOrder.service.impl;

import com.ryx.credit.common.enumc.JoTaskStatus;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.service.dict.IdService;
import com.ryx.jobOrder.dao.JoTaskMapper;
import com.ryx.jobOrder.pojo.JoOrder;
import com.ryx.jobOrder.pojo.JoTask;
import com.ryx.jobOrder.pojo.JoTaskExample;
import com.ryx.jobOrder.service.JobOrderTaskService;
import com.ryx.jobOrder.vo.JoTaskVo;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Service("jobOrderTaskService")
public class JobOrderTaskServiceImpl implements JobOrderTaskService {

    private final BigDecimal version = new BigDecimal(0);

    @Autowired
    private JoTaskMapper joTaskMapper;

    @Autowired
    private IdService idService;

    /**
     * 创建 工单任务 记录
     * @param joTask
     * @return
     * @throws Exception
     */
    @Override
    public FastMap createJobOrderTask(JoTask joTask) throws Exception{
        joTask.setId( idService.genId(TabId.jo_task) );
        joTask.setJoTaskStatus(JoTaskStatus.WSL.getValue());
        joTask.setJoTaskTime(new Date());
        joTask.setVersion(version);
        int status = joTaskMapper.insert(joTask);
        if(status != 1){
            throw new MessageException("插入失败" + joTask.getId());
        }
        return FastMap.fastSuccessMap();
    }

    /**
     * 查询 工单任务VO 列表
     * @param joTaskVo
     * @param page
     * @return
     */
    public PageInfo queryJobOrderTaskVo(JoTaskVo joTaskVo, Page page){
        String joAcceptTimeBeginStr = joTaskVo.getJoAcceptTimeBeginStr();
        String joAcceptTimeEndStr = joTaskVo.getJoAcceptTimeEndStr();
        String joStartTimeBeginStr = joTaskVo.getJoStartTimeBeginStr();
        String joStartTimeEndStr = joTaskVo.getJoStartTimeEndStr();
        if((StringUtils.isNotBlank(joAcceptTimeBeginStr) && StringUtils.isNotBlank(joAcceptTimeEndStr))
        || (StringUtils.isNotBlank(joStartTimeBeginStr) && StringUtils.isNotBlank(joStartTimeEndStr))){
            if(StringUtils.isNotBlank(joAcceptTimeBeginStr)){
                joAcceptTimeBeginStr =joAcceptTimeBeginStr.substring(0,10);
                joAcceptTimeEndStr =joAcceptTimeEndStr.substring(0,10);
                joTaskVo.setJoAcceptTimeBegin(DateUtil.format(joAcceptTimeBeginStr,DateUtil.DATE_FORMAT_yyyy_MM_dd));
                joTaskVo.setJoAcceptTimeEnd(DateUtil.format(joAcceptTimeEndStr,DateUtil.DATE_FORMAT_yyyy_MM_dd));
            }else{
                joStartTimeBeginStr =joStartTimeBeginStr.substring(0,10);
                joStartTimeEndStr =joStartTimeEndStr.substring(0,10);
                joTaskVo.setJoStartTimeBegin(DateUtil.format(joStartTimeBeginStr,DateUtil.DATE_FORMAT_yyyy_MM_dd));
                joTaskVo.setJoStartTimeEnd(DateUtil.format(joStartTimeEndStr,DateUtil.DATE_FORMAT_yyyy_MM_dd));
            }
        }
        List jotaskVolist = joTaskMapper.selectByJoTaskVo(joTaskVo, page);
        PageInfo pageInfo = new PageInfo();
        int count = jotaskVolist.size();
        pageInfo.setTotal(count);
        pageInfo.setRows(jotaskVolist);
        return  pageInfo;
    }

    /**
     * 查询 工单任务 列表
     *      工单详情等使用
     * @param joTask
     * @return
     */
    @Override
    public List<JoTask> queryJobOrderTask(JoTask joTask) {
        JoTaskExample joTaskExample = queryParam(joTask);
        return joTaskMapper.selectByExample(joTaskExample);
    }

    /**
     * 更新工单任务
     * @param joTask
     * @return
     * @throws Exception
     */
    @Override
    public FastMap updateJobOrderTask(JoTask joTask) throws Exception{
        int status = joTaskMapper.updateByPrimaryKeySelective(joTask);
        if(status != 1){
            throw new MessageException("更新失败");
        }
        return FastMap.fastSuccessMap();
    }

    /**
     * 配置查询条件
     * @param joTask
     * @return
     */
    private JoTaskExample queryParam(JoTask joTask){
        JoTaskExample joTaskExample = new JoTaskExample();
        JoTaskExample.Criteria criteria = joTaskExample.createCriteria();
        if(StringUtils.isNotBlank(joTask.getId())){
            criteria.andIdEqualTo(joTask.getId());
        }
        if(StringUtils.isNotBlank(joTask.getJoId())){
            criteria.andJoIdEqualTo(joTask.getJoId());
        }
        if(StringUtils.isNotBlank(joTask.getDealGroupId())){
            criteria.andDealGroupIdEqualTo(joTask.getDealGroupId());
        }
        if(StringUtils.isNotBlank(joTask.getDealPersonId())){
            criteria.andDealPersonIdEqualTo(joTask.getDealPersonId());
        }
        joTaskExample.setOrderByClause("jo_task_time desc");
        return joTaskExample;
    }

    /**
     * 查询最后的一条工单
     * 1. 查询上条工单
     * 2. 查询指派部门
     * 3. 生成新工单任务
     * @param joTask
     * @return
     * @throws Exception
     */
    @Override
    public JoTask queryLastJobOrderTask(JoTask joTask) {
        List<JoTask> joTaskList = queryJobOrderTask(joTask);
        JoTask joTaskOld = null;
        if(joTaskList!= null && joTaskList.size()>=1){
            joTaskOld = joTaskList.get(0);
        }else{
            return null;
        }
        return joTaskOld;
    }

    /**
     * 工单受理服务
     * 1. 查询上条工单
     * 2. 查询指派部门
     * 3. 生成新工单任务
     * @param joTask
     * @return
     * @throws Exception
     */
    @Override
    public FastMap receiveJobOrderTask(JoTask joTask) throws Exception {
        JoTask joTaskOld = queryLastJobOrderTask(joTask);
        // 结束工单
        endJoTask( joTaskOld );
        // 查询工单类型受理部门
        joTask.setDealGroupId("");
        //
        JoTask joTaskNew = new JoTask();
        joTaskNew.setJoId(joTask.getJoId());
        joTaskNew.setJoTaskStatus(JoTaskStatus.WSL.getValue());
        joTaskNew.setDealGroup("");
        joTaskNew.setDealGroupId("");
        joTaskNew.setDealPersonId("");
        joTaskNew.setDealPersonName("");
        joTaskNew.setBackDealGroup(joTaskOld.getDealGroup());
        joTaskNew.setBackDealPerson(joTaskOld.getDealPersonName());
        joTaskNew.setJoTaskContent(joTask.getDealGroup() + "转发:" + joTask.getJoTaskContent());
        FastMap status = createJobOrderTask(joTaskNew);
        if(FastMap.isSuc(status)){
            return FastMap.fastSuccessMap();
        }
        return FastMap.fastFailMap();
    }

    /**
     * 结束工单任务
     * @param joTask
     * @return
     * @throws Exception
     */
    @Override
    public FastMap endJoTask(JoTask joTask) throws MessageException {
        joTask.setJoTaskNextTime(new Date());
        joTask.setJoTaskStatus(JoTaskStatus.YHF.getValue());
        double dLength = (joTask.getJoTaskNextTime().getTime() - joTask.getJoTaskTime().getTime() ) / 3600 / 60;
        joTask.setJoTaskTimeLength(new BigDecimal(dLength).setScale(2, BigDecimal.ROUND_HALF_UP));
        int status = joTaskMapper.updateByPrimaryKeySelective(joTask);
        if(status != 1){
            throw new MessageException("更新失败");
        }
        return FastMap.fastSuccessMap();
    }

    /**
     * 工单受理服务
     * 1. 将工单任务状态 受理中
     * @param joTask
     * @return
     */
    @Override
    public FastMap acceptOrderByTaskId(JoTask joTask) throws Exception {
        joTask.setJoTaskAcceptTime(new Date());
        joTask.setJoTaskStatus(JoTaskStatus.SLZ.getValue());
        FastMap fastMap = updateJobOrderTask(joTask);
        if(FastMap.isSuc(fastMap)){
            return FastMap.fastSuccessMap();
        }
        return null;
    }
}
