package com.ryx.jobOrder.service.impl;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.service.IResourceService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AttachmentRelService;
import com.ryx.credit.service.dict.DepartmentService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.jobOrder.dao.JoOrderMapper;
import com.ryx.jobOrder.dao.JoTaskMapper;
import com.ryx.jobOrder.pojo.JoOrder;
import com.ryx.jobOrder.pojo.JoTask;
import com.ryx.jobOrder.pojo.JoTaskExample;
import com.ryx.jobOrder.service.JobOrderTaskService;
import com.ryx.jobOrder.vo.JoTaskVo;
import javafx.print.PrinterJob;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("jobOrderTaskService")
public class JobOrderTaskServiceImpl implements JobOrderTaskService {
    private static Logger logger = LoggerFactory.getLogger(JobOrderTaskServiceImpl.class);
    private final BigDecimal version = new BigDecimal(0);

    @Autowired
    private JoTaskMapper joTaskMapper;

    @Autowired
    private JoOrderMapper joOrderMapper;

    @Autowired
    private IdService idService;

    @Autowired
    private AttachmentRelService attachmentRelService;
    @Autowired
    private IResourceService iResourceService;

    @Autowired
    private AgentBusinfoService agentBusinfoService;

    @Autowired
    private DepartmentService departmentService;
    /**
     * 创建 工单任务 记录
     * @param joTask
     * @return
     * @throws Exception
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public FastMap createJobOrderTask(JoTask joTask) throws Exception{
        logger.info("开始保存工单任务");
        joTask.setId( idService.genId(TabId.jo_task) );
        joTask.setJoTaskStatus(JoTaskStatus.WSL.getValue());
        joTask.setJoTaskTime(new Date());
        joTask.setVersion(version);
        int status = joTaskMapper.insert(joTask);
        if(status != 1){
            throw new MessageException("插入失败" + joTask.getId());
        }
        logger.info("工单任务保存成功，taskID"+joTask.getId());
        return FastMap.fastSuccessMap();
    }

    /**
     * 查询 工单任务VO 列表
     * @param joTaskVo
     * @param page
     * @return
     */
    public PageInfo queryJobOrderTaskVo(JoTaskVo joTaskVo, Page page){
        logger.info("查询 工单任务VO 列表，条件："+joTaskVo.toString());
        String joAcceptTimeBeginStr = joTaskVo.getJoAcceptTimeBeginStr();
        String joAcceptTimeEndStr = joTaskVo.getJoAcceptTimeEndStr();
        String joStartTimeBeginStr = joTaskVo.getJoStartTimeBeginStr();
        String joStartTimeEndStr = joTaskVo.getJoStartTimeEndStr();
        if((StringUtils.isNotBlank(joAcceptTimeBeginStr) && StringUtils.isNotBlank(joAcceptTimeEndStr))
        || (StringUtils.isNotBlank(joStartTimeBeginStr) && StringUtils.isNotBlank(joStartTimeEndStr))){
            if(StringUtils.isNotBlank(joAcceptTimeBeginStr)){
                joAcceptTimeBeginStr =joAcceptTimeBeginStr.substring(0,19);
                joAcceptTimeEndStr =joAcceptTimeEndStr.substring(0,19);
                joTaskVo.setJoAcceptTimeBegin(DateUtil.format(joAcceptTimeBeginStr,DateUtil.DATE_FORMAT_1));
                joTaskVo.setJoAcceptTimeEnd(DateUtil.format(joAcceptTimeEndStr,DateUtil.DATE_FORMAT_1));
            }
            if(StringUtils.isNotBlank(joStartTimeBeginStr)){
                joStartTimeBeginStr =joStartTimeBeginStr.substring(0,19);
                joStartTimeEndStr =joStartTimeEndStr.substring(0,19);
                joTaskVo.setJoStartTimeBegin(DateUtil.format(joStartTimeBeginStr,DateUtil.DATE_FORMAT_1));
                joTaskVo.setJoStartTimeEnd(DateUtil.format(joStartTimeEndStr,DateUtil.DATE_FORMAT_1));
            }
        }
        List jotaskVolist = joTaskMapper.selectByJoTaskVo(joTaskVo, page);
        PageInfo pageInfo = new PageInfo();
        List jotaskVolist2 = joTaskMapper.selectByJoTaskVo(joTaskVo, null);
        int count = jotaskVolist2.size();
        pageInfo.setTotal(count);
        pageInfo.setRows(jotaskVolist);
        return  pageInfo;
    }

    /**
     * 根据 id 查询任务
     * 查询 工单任务 列表
     *      工单详情等使用
     * @param taskId
     * @return
     */
    @Override
    public JoTask queryJobOrderTaskByTaskId(String taskId) {
        logger.info("根据 id 查询任务,taskid" + taskId);
        JoTask query = new JoTask();
        query.setId(taskId);
        List<JoTask> list= queryJobOrderTask(query);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
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
        joTaskExample.setOrderByClause("id desc");
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
     * @param joTask
     * @return
     * @throws Exception
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public FastMap receiveJobOrderTask(JoTask joTask) throws Exception {
        String files = joTask.getJoTaskAnnexId();
        joTask.setJoTaskAnnexId(null);
        String oldTaskId= joTask.getId();
        String joId = joTask.getJoId();
        String content = joTask.getJoTaskContent();
        JoTask joTaskOld = queryJobOrderTaskByTaskId(oldTaskId);
        if(files!=null){
            saveAttachments(joTaskOld.getId(), joTaskOld.getDealPersonId(), (files).split(","));
            joTaskOld.setJoTaskAnnexId(files);
        }
        joTaskOld.setDealPersonName(joTask.getDealPersonName());
        joTaskOld.setDealPersonId(joTask.getDealPersonId());
        String dealCode = joTask.getDealGroupId();
        JoOrder newOrder = joOrderMapper.selectByPrimaryKey(joId);
        String oldContent = StringUtils.isBlank(joTaskOld.getJoTaskContent())?"":joTaskOld.getJoTaskContent();
        if("0".equals(dealCode)){
            joTaskOld.setJoTaskContent(oldContent
                    + " 转发到发起人:" + content);
        }else{
            joTaskOld.setJoTaskContent(oldContent
                    + " 转发到" + joTask.getDealGroup()+":" + content);
        }
        // 结束工单任务
        endJoTask( joTaskOld );


        if("0".equals(dealCode)){ // 选择的是发起人
            // 如果 那就是返回发起人
            newOrder.setJoProgress(JoOrderStatus.YCL.getValue());
            newOrder.setDealTimeEnd(new Date());
            newOrder.setAcceptNowGroup(joTask.getDealGroup());
            double dLength = (newOrder.getDealTimeEnd().getTime() - newOrder.getDealTimeStart().getTime() ) / (60 * 1000);
            newOrder.setDealTimeLength(new BigDecimal(dLength).setScale(2, BigDecimal.ROUND_HALF_UP));
            joOrderMapper.updateByPrimaryKeySelective(newOrder);
            return FastMap.fastSuccessMap();
        }else{
            newOrder.setAcceptNowGroup(joTask.getDealGroup());
            joOrderMapper.updateByPrimaryKeySelective(newOrder);
        }
        // 转发其他部门  jotask 含有受理部门 处理人 处理人姓名
        JoTask joTaskNew = new JoTask();
        joTaskNew.setJoId(joId);
        joTaskNew.setJoTaskStatus(JoTaskStatus.WSL.getValue());
        joTaskNew.setDealGroup(joTask.getDealGroup());
        joTaskNew.setDealGroupId(joTask.getDealGroupId());
        joTaskNew.setSecondDealGroup(joTask.getSecondDealGroup());
        joTaskNew.setBackDealGroup(joTaskOld.getDealGroup());
        joTaskNew.setBackDealPerson(joTaskOld.getDealPersonName());
        if(JoPowerGroup.SQ.key.equals(dealCode)){
            String queryAgId = newOrder.getAgId();
            String dictItem = newOrder.getJoSecondKeyNum().substring(0,newOrder.getJoSecondKeyNum().indexOf("_"));
            FastMap reqMap = FastMap.fastMap("agentId", queryAgId)
                    .putKeyV("dictGroup","JOB")
                    .putKeyV("dictItem",dictItem ).putKeyV("busId", newOrder.getBusId());
            List<Map> listPlateform = agentBusinfoService.agentBusByDict(reqMap);
            Map item = listPlateform.get(0);
            String agPro =  (String)item.get("AG_DOC_PRO");
            if(StringUtils.isBlank(agPro)){
                throw  new MessageException(String.format("该代理商%s，业务%s未配置省区编码，不能转发省区", queryAgId, newOrder.getPlatformName()));
            }
            COrganization sqMap = departmentService.getById(agPro);
            joTaskNew.setSecondDealGroup(sqMap.getCode());
        }
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
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public FastMap endJoTask(JoTask joTask) throws MessageException {
        logger.info("结束工单任务,taskId:"+joTask.getId());
        // 更新工单任务
        joTask.setJoTaskNextTime(new Date());
        joTask.setJoTaskStatus(JoTaskStatus.YHF.getValue());
        double dLength = (joTask.getJoTaskNextTime().getTime() - joTask.getJoTaskTime().getTime() ) / (60 * 1000);
        joTask.setJoTaskTimeLength(new BigDecimal(dLength).setScale(2, BigDecimal.ROUND_HALF_UP));
        int status = joTaskMapper.updateByPrimaryKeySelective(joTask);
        if(status != 1){
            throw new MessageException("结束工单任务失败");
        }
        return FastMap.fastSuccessMap();
    }

    /**
     * 工单受理服务
     * 1. 工单首次处理，更新状态和当前处理组
     * 2. 将工单任务状态 更新为 受理中
     * @param joTask
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public FastMap acceptOrderByTaskId(JoTask joTask) throws Exception {
        logger.info("工单单受理服务,taskId:"+joTask.getId());
        // 获取joId
        String joId = joTask.getJoId();
        // 查询订单的状态
        JoOrder joOrder = joOrderMapper.selectByPrimaryKey(joId);
        if(joOrder.getJoProgress().equals(JoOrderStatus.WCL.getValue())){// 工单未处理状态
            joOrder.setJoProgress(JoOrderStatus.CLZ.getValue());
            // 更新当前处理组
            joOrder.setDealTimeStart(new Date());
            joOrderMapper.updateByPrimaryKeySelective(joOrder);
        }
//        joTask.setJoId(null);
//        joTask.setDealGroup(null);
//        joTask.setJoTaskAcceptTime(new Date());
//        joTask.setJoTaskStatus(JoTaskStatus.SLZ.getValue());
        JoTask joT = queryJobOrderTaskByTaskId(joTask.getId());
        joT.setDealPersonId(joTask.getDealPersonId());
        joT.setDealPersonName(joTask.getDealPersonName());
        joT.setJoTaskAcceptTime(new Date());
        joT.setJoTaskStatus(JoTaskStatus.SLZ.getValue());
        FastMap fastMap = updateJobOrderTask(joT);
        if(FastMap.isSuc(fastMap)){
            logger.info("工单受理成功,taskId:"+joTask.getId());
            return FastMap.fastSuccessMap();
        }
        return null;
    }

    /**
     * 取消工单受理服务
     * 1. 清空当前 工单任务 信息
     * 2. 判断该工单任务是否是 第一条任务
     * 3. 是否更新 工单信息
     * @param joTask
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public FastMap cancelOrderByTaskId(JoTask joTask) throws Exception {
        logger.info("取消工单任务受理,taskId:"+joTask.getId());
        // 获取joId
        String joId = joTask.getJoId();
        // 查询订单的状态
        JoTask query = new JoTask();
        query.setJoId(joId);
        List result = queryJobOrderTask(query);
        if(result.size() == 1){ // 首次工单任务
            // 清理工单的信息
            JoOrder joOrder = joOrderMapper.selectByPrimaryKey(joId);
            joOrder.setDealTimeStart(null);
            joOrder.setJoProgress(JoOrderStatus.WCL.getValue());
            joOrderMapper.updateByPrimaryKey(joOrder);
        }
        JoTask update = joTaskMapper.selectByPrimaryKey(joTask.getId());
        update.setDealPersonId(null);
        update.setDealPersonName(null);
        update.setJoTaskAcceptTime(null);
        update.setJoTaskStatus(JoTaskStatus.WSL.getValue());
        int status = joTaskMapper.updateByPrimaryKey(update);
        if(status == 1){
            logger.info("取消工单任务受理成功,taskId:"+joTask.getId());
            return FastMap.fastSuccessMap();
        }
        return FastMap.fastFailMap();
    }

    /**
     * 保存附件信息
     */
    public FastMap saveAttachments(String joId, String userid, String[] attachments) {
        try {
            for (String attach : attachments) {
                AttachmentRel attachmentRel = new AttachmentRel();
                attachmentRel.setId(idService.genId(TabId.a_attachment_rel));
                attachmentRel.setSrcId(joId);
                attachmentRel.setAttId(attach);
                attachmentRel.setBusType(AttachmentRelType.jobOrder.name());
                attachmentRel.setcTime(new Date());
                attachmentRel.setcUser(userid);
                attachmentRel.setStatus(Status.STATUS_1.status);
                attachmentRelService.insertSelective(attachmentRel);
            }
        } catch (Exception e) {
            return FastMap.fastFailMap("附件保存失败");
        }
        return FastMap.fastSuccessMap();
    }
}
