package com.ryx.jobOrder.service.impl;

import com.ryx.credit.common.enumc.JoOrderStatus;
import com.ryx.credit.common.enumc.JoTaskStatus;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.result.Result;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.jobOrder.dao.*;
import com.ryx.jobOrder.pojo.*;
import com.ryx.jobOrder.service.JobOrderQueryService;
import com.ryx.jobOrder.service.JobOrderTaskService;
import com.ryx.jobOrder.vo.JoTaskVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("jobOrderQueryService")
public class JobOrderQueryServiceImpl implements JobOrderQueryService {

    private static Logger logger = LoggerFactory.getLogger(JobOrderQueryServiceImpl.class);
    private final BigDecimal version = new BigDecimal(0);
    @Autowired
    private JoOrderMapper joOrderMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private JoTaskMapper joTaskMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private JobOrderTaskService jobOrderTaskService;

    @Override
    public PageInfo jobOrderQueryList(Map map, Page page) {
        logger.info("------总部收到的工单-查询所有------");
        PageInfo pageInfo = new PageInfo();
        map.put("page", page);
        String launchBeginTime = (String) map.get("launchBeginTime");
        String launchEndTime = (String) map.get("launchEndTime");
        String taskBeginTime = (String) map.get("taskBeginTime");
        String taskEndTime = (String) map.get("taskEndTime");
        if (StringUtils.isNotBlank(launchBeginTime) && StringUtils.isNotBlank(launchEndTime)) {
            launchBeginTime =launchBeginTime.substring(0,19);
            launchEndTime =launchEndTime.substring(0,19);
            map.put("launchBeginTime", DateUtil.format(launchBeginTime,DateUtil.DATE_FORMAT_1));
            map.put("launchEndTime", DateUtil.format(launchEndTime,DateUtil.DATE_FORMAT_1));
        }
        if (StringUtils.isNotBlank(taskBeginTime) && StringUtils.isNotBlank(taskEndTime)) {
            taskBeginTime =taskBeginTime.substring(0,10);
            taskEndTime =taskEndTime.substring(0,10);
            map.put("taskBeginTime", DateUtil.format(taskBeginTime,DateUtil.DATE_FORMAT_1));
            map.put("taskEndTime", DateUtil.format(taskEndTime,DateUtil.DATE_FORMAT_1));
        }
        int jobOrderListCount = joOrderMapper.queryJobOrderListCount(map);
        List<JoTaskVo> jobOrderList = joOrderMapper.queryJobOrderList(map);
        pageInfo.setTotal(jobOrderListCount);
        pageInfo.setRows(jobOrderList);
        return pageInfo;
    }

    @Override
    public PageInfo agentJobOrderQueryList(Map map, Page page) {
        logger.info("------代理商收到的工单-代理商工单组------");
        PageInfo pageInfo = new PageInfo();
        map.put("page", page);
        String launchBeginTime = (String) map.get("launchBeginTime");
        String launchEndTime = (String) map.get("launchEndTime");
        String taskBeginTime = (String) map.get("taskBeginTime");
        String taskEndTime = (String) map.get("taskEndTime");
        if (StringUtils.isNotBlank(launchBeginTime) && StringUtils.isNotBlank(launchEndTime)) {
            launchBeginTime =launchBeginTime.substring(0,19);
            launchEndTime =launchEndTime.substring(0,19);
            map.put("launchBeginTime", DateUtil.format(launchBeginTime,DateUtil.DATE_FORMAT_1));
            map.put("launchEndTime", DateUtil.format(launchEndTime,DateUtil.DATE_FORMAT_1));
        }
        if (StringUtils.isNotBlank(taskBeginTime) && StringUtils.isNotBlank(taskEndTime)) {
            taskBeginTime =taskBeginTime.substring(0,19);
            taskEndTime =taskEndTime.substring(0,19);
            map.put("taskBeginTime", DateUtil.format(taskBeginTime,DateUtil.DATE_FORMAT_1));
            map.put("taskEndTime", DateUtil.format(taskEndTime,DateUtil.DATE_FORMAT_1));
        }
        int jobOrderListCount = joOrderMapper.queryAgentJobOrderListCount(map);
        List<JoTaskVo> jobOrderList = joOrderMapper.queryAgentJobOrderList(map);
        pageInfo.setTotal(jobOrderListCount);
        pageInfo.setRows(jobOrderList);
        return pageInfo;
    }

    @Override
    public PageInfo cityJobOrderQueryList(Map map, Page page) {
        logger.info("------省区收到的工单-省区工单组------");
        PageInfo pageInfo = new PageInfo();
        map.put("page", page);
        String launchBeginTime = (String) map.get("launchBeginTime");
        String launchEndTime = (String) map.get("launchEndTime");
        String taskBeginTime = (String) map.get("taskBeginTime");
        String taskEndTime = (String) map.get("taskEndTime");
        if (StringUtils.isNotBlank(launchBeginTime) && StringUtils.isNotBlank(launchEndTime)) {
            launchBeginTime =launchBeginTime.substring(0,19);
            launchEndTime =launchEndTime.substring(0,19);
            map.put("launchBeginTime", DateUtil.format(launchBeginTime,DateUtil.DATE_FORMAT_1));
            map.put("launchEndTime", DateUtil.format(launchEndTime,DateUtil.DATE_FORMAT_1));
        }
        if (StringUtils.isNotBlank(taskBeginTime) && StringUtils.isNotBlank(taskEndTime)) {
            taskBeginTime =taskBeginTime.substring(0,19);
            taskEndTime =taskEndTime.substring(0,19);
            map.put("taskBeginTime", DateUtil.format(taskBeginTime,DateUtil.DATE_FORMAT_1));
            map.put("taskEndTime", DateUtil.format(taskEndTime,DateUtil.DATE_FORMAT_1));
        }
        int jobOrderListCount = joOrderMapper.queryCityJobOrderListCount(map);
        List<JoTaskVo> jobOrderList = joOrderMapper.queryCityJobOrderList(map);
        pageInfo.setTotal(jobOrderListCount);
        pageInfo.setRows(jobOrderList);
        return pageInfo;
    }

    @Override
    public PageInfo groupJobOrderQueryList(Map map, Page page) {
        logger.info("------工单组收到的工单-总部工单组------");
        PageInfo pageInfo = new PageInfo();
        map.put("page", page);
        String launchBeginTime = (String) map.get("launchBeginTime");
        String launchEndTime = (String) map.get("launchEndTime");
        String taskBeginTime = (String) map.get("taskBeginTime");
        String taskEndTime = (String) map.get("taskEndTime");
        if (StringUtils.isNotBlank(launchBeginTime) && StringUtils.isNotBlank(launchEndTime)) {
            launchBeginTime =launchBeginTime.substring(0,19);
            launchEndTime =launchEndTime.substring(0,19);
            map.put("launchBeginTime", DateUtil.format(launchBeginTime,DateUtil.DATE_FORMAT_1));
            map.put("launchEndTime", DateUtil.format(launchEndTime,DateUtil.DATE_FORMAT_1));
        }
        if (StringUtils.isNotBlank(taskBeginTime) && StringUtils.isNotBlank(taskEndTime)) {
            taskBeginTime =taskBeginTime.substring(0,19);
            taskEndTime =taskEndTime.substring(0,19);
            map.put("taskBeginTime", DateUtil.format(taskBeginTime,DateUtil.DATE_FORMAT_1));
            map.put("taskEndTime", DateUtil.format(taskEndTime,DateUtil.DATE_FORMAT_1));
        }
        int jobOrderListCount = joOrderMapper.queryGroupJobOrderListCount(map);
        List<JoTaskVo> jobOrderList = joOrderMapper.queryGroupJobOrderList(map);
        pageInfo.setTotal(jobOrderListCount);
        pageInfo.setRows(jobOrderList);
        return pageInfo;
    }

    @Override
    public JoOrder getByJobId(String id) {
        if (StringUtils.isBlank(id)) {
            logger.error("---工单id为空,获取不到数据---");
            return null;
        }
        JoOrder jobOrder = joOrderMapper.selectByPrimaryKey(id);
        return jobOrder;
    }

    @Override
    public PageInfo jobOrderQueryLaunchList(Map map, Page page) {
        logger.info("------我发起的工单列表查询------");
        String joAcceptTimeBeginStr = (String) map.get("launchBeginTime");
        String joAcceptTimeEndStr = (String) map.get("launchEndTime");
        if((StringUtils.isNotBlank(joAcceptTimeBeginStr) && StringUtils.isNotBlank(joAcceptTimeEndStr))){
                joAcceptTimeBeginStr =joAcceptTimeBeginStr.substring(0,19);
                joAcceptTimeEndStr =joAcceptTimeEndStr.substring(0,19);
                map.put("launchBeginTime",DateUtil.format(joAcceptTimeBeginStr,DateUtil.DATE_FORMAT_1));
                map.put("launchEndTime",DateUtil.format(joAcceptTimeEndStr,DateUtil.DATE_FORMAT_1));
        }else {
            map.put("launchBeginTime",null);
            map.put("launchEndTime",null);
        }
        PageInfo pageInfo = new PageInfo();
        int listCount = joOrderMapper.queryJobOrderLaunchListCount(map);
        List<JoTaskVo> jobOrderList = joOrderMapper.queryJobOrderLaunchList(map,page);
        pageInfo.setTotal(listCount);
        pageInfo.setRows(jobOrderList);
        return pageInfo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public AgentResult jobOrderCancle(Map map) throws MessageException{
        logger.info("--查找取消的工单--"+map.get("jobId"));
        AgentResult agentResult = AgentResult.ok("撤销工单成功!");
        JoOrderExample joOrderExample = new JoOrderExample();
        joOrderExample.or().andIdEqualTo(String.valueOf(map.get("jobId")))
        .andJoProgressEqualTo(JoOrderStatus.WCL.key)
        .andLaunchPersonIdEqualTo(String.valueOf(map.get("userId")));
        List<JoOrder> joOrders = joOrderMapper.selectByExample(joOrderExample);
        if (joOrders != null && joOrders.size() == 1){
            JoOrder joOrder = joOrders.get(0);
            joOrder.setJoProgress(JoOrderStatus.CANCLE.key);
            if (joOrderMapper.updateByPrimaryKeySelective(joOrder)==1){
                JoTaskExample joTaskExample = new JoTaskExample();
                joTaskExample.or().andJoIdEqualTo(joOrder.getId())
                        .andJoTaskStatusEqualTo(JoOrderStatus.WCL.key);
                if (joTaskMapper.deleteByExample(joTaskExample) < 0 ){
                    logger.error("--撤销工单失败--"+map.get("jobId"));
                    throw new MessageException("撤销工单失败");
                };
                logger.info("--撤销工单成功--"+map.get("jobId"));
                return agentResult;
            }else {
                logger.error("--撤销工单失败--"+map.get("jobId"));
                throw new MessageException("注销中请勿重复提交");
            }
        }

        return agentResult;
    }

    @Override
    public AgentResult finishJobOrder(Map map) throws MessageException {
        logger.info("结束工单,JoId:"+map.get("jobId"));
        AgentResult agentResult = AgentResult.fail("结束工单失败!");
        try {
            JoOrder joOrder = joOrderMapper.selectByPrimaryKey(String.valueOf(map.get("jobId")));
            if (JoOrderStatus.YCL.key.equals(joOrder.getJoProgress())){
                joOrder.setJoProgress(JoOrderStatus.END.key);
                joOrder.setDealTimeLength(BigDecimal.valueOf(getMinutes(joOrder.getDealTimeStart(), joOrder.getDealTimeEnd())));
                if (joOrderMapper.updateByPrimaryKeySelective(joOrder)!=1){
                    logger.error("工单更新为结束失败!");
                }else {
                    agentResult.setStatus(200);
                    agentResult.setMsg("结束工单成功!");
                }
            }else {
                agentResult.setMsg("工单还未处理，不允许结束!");
            }
        }catch (Exception e){
            throw new MessageException("结束工单失败!");
        }

        return agentResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public AgentResult reStartTask(Map map) throws MessageException {
        AgentResult agentResult = AgentResult.fail("重新发起提问失败!");
        try {
            JoOrder joOrder = joOrderMapper.selectByPrimaryKey(String.valueOf(map.get("joId")));
            if (JoOrderStatus.YCL.key.equals(joOrder.getJoProgress())){
                joOrder.setJoProgress(JoOrderStatus.CLZ.key);
                if (joOrderMapper.updateByPrimaryKeySelective(joOrder)!=1){
                    logger.error("工单更新为[处理中]失败!");
                }else {
                    JoTask joTask = new JoTask();
                    joTask.setJoId(String.valueOf(map.get("joId")));
                    List<JoTask> joTaskList = jobOrderTaskService.queryJobOrderTask(joTask);
                    JoTask joTask1 = joTaskList.get(0);
                    joTask.setId( idService.genId(TabId.jo_task) );
                    joTask.setJoId(String.valueOf(map.get("joId")));
                    joTask.setJoTaskStatus(JoTaskStatus.WSL.getValue());
                    joTask.setJoTaskTime(new Date());
                    joTask.setJoTaskContent(String.valueOf(map.get("joContent")));
                    joTask.setDealGroup(joTask1.getDealGroup());
                    joTask.setDealGroupId(joTask1.getDealGroupId());
                    joTask.setDealPersonId("");
                    joTask.setDealPersonName("");
                    joTask.setId( idService.genId(TabId.jo_task) );
                    joTask.setVersion(version);
                    if(joTaskMapper.insert(joTask) != 1){
                        throw new MessageException("重新提问失败" + joTask.getId());
                    }
                    agentResult.setStatus(200);
                    agentResult.setMsg("再次提问成功!");
                }
            }else {
                agentResult.setMsg("工单还未处理，不需要再次发起!");
            }
        }catch (Exception e){
            throw new MessageException("工单再次发起提问失败!");
        }
        return agentResult;
    }


    public static int getMinutes(Date startDate,Date endDate) throws Exception{

//        Instant i1=startDate.toInstant();
//
//        LocalDate startt=i1.atZone(ZoneId.systemDefault()).toLocalDate();
//
//        Instant i2=endDate.toInstant();
//
//        LocalDate end=i2.atZone(ZoneId.systemDefault()).toLocalDate();
//
//
//        Period p = Period.between(startt, end);
//
//        int minutes = p.getDays()*1440;
        try {
            long from3 = startDate.getTime();
            long to3 = endDate.getTime();
            int minutes = (int) ((to3 - from3) / (1000 * 60));
            return minutes;
        }catch (Exception e){
            logger.error(e.toString());
            return -1;
        }

    }



}
