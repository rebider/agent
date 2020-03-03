package com.ryx.jobOrder.service.impl;

import com.ryx.credit.common.enumc.JoOrderStatus;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.result.Result;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.jobOrder.dao.*;
import com.ryx.jobOrder.pojo.*;
import com.ryx.jobOrder.service.JobOrderQueryService;
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
    @Override
    public PageInfo jobOrderQueryList(Map map, Page page) {
        logger.info("------我收到的工单列表查询------");
        PageInfo pageInfo = new PageInfo();
        map.put("page", page);
//        if(null != map.get("dealPersonId")) {
//            Long userId = (Long) map.get("dealPersonId");
//            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
//            if (orgCodeRes==null && orgCodeRes.size()!=1) {
//                return null;
//            }
//            Map<String, Object> resultMap = orgCodeRes.get(0);
//            String organizationCode = String.valueOf(resultMap.get("ORGANIZATIONCODE"));
//            map.put("organizationCode", organizationCode);
//        }
        int jobOrderListCount = joOrderMapper.queryJobOrderListCount(map);
        List<JoTaskVo> jobOrderList = joOrderMapper.queryJobOrderList(map);
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
                logger.error("--撤销工单成功--"+map.get("jobId"));
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

        JoTask joTask = new JoTask();
        joTask.setId( idService.genId(TabId.jo_task) );
        joTask.setJoId(String.valueOf(map.get("joId")));
        joTask.setDealGroup("");
        joTask.setDealGroupId("");
        joTask.setDealPersonId("");
        joTask.setDealPersonName("");
        joTask.setJoTaskContent("");
        joTask.setId( idService.genId(TabId.jo_task) );
        joTask.setVersion(version);
        if(joTaskMapper.insert(joTask) != 1){
            throw new MessageException("插入失败" + joTask.getId());
        }

        return null;
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
            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
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
