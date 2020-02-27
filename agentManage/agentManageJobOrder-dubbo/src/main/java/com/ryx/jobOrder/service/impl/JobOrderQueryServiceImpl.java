package com.ryx.jobOrder.service.impl;

import com.ryx.credit.common.enumc.JoOrderStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.result.Result;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.service.IUserService;
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

import java.util.List;
import java.util.Map;

@Service("jobOrderQueryService")
public class JobOrderQueryServiceImpl implements JobOrderQueryService {

    private static Logger logger = LoggerFactory.getLogger(JobOrderQueryServiceImpl.class);
    @Autowired
    private JoOrderMapper joOrderMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private JoTaskMapper joTaskMapper;
    @Autowired
    private JoExpandKeyMapper joExpandKeyMapper;
    @Autowired
    private JoKeyManageMapper joKeyManageMapper;

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
        long listCount = joOrderMapper.queryJobOrderListCount(map);
        List<JoTaskVo> jobOrderList = joOrderMapper.queryJobOrderList(map);
        pageInfo.setTotal(jobOrderList.size());
        pageInfo.setRows(jobOrderList);
        return pageInfo;
    }

    @Override
    public PageInfo jobTaskInfo(Map map, Page page) throws MessageException {
        logger.info("------任务消息查看------");
        PageInfo pageInfo = new PageInfo();
        map.put("page", page);
        if (null != map.get("id")) {
            logger.error("---工单编号为空,获取不到任务信息---");
            throw new MessageException("未获取到工单编号");
        }
        int listCount = joTaskMapper.queryJobTaskInfoCount(map);
        List<Map<String, Object>> jobOrderList = joTaskMapper.queryJobTaskInfo(map);
        pageInfo.setTotal(listCount);
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
    public JoExpandKey getByJobExpandId(String jobId) {
        if (StringUtils.isBlank(jobId)) {
            logger.error("---工单id为空,获取不到数据---");
            return null;
        }
        JoExpandKey joExpandKey = joExpandKeyMapper.selectByPrimaryKey(jobId);
        return joExpandKey;
    }

    @Override
    public List<JoKeyManage> queryManageList(String keyType) {
        JoKeyManageExample joKeyManageExample = new JoKeyManageExample();
        joKeyManageExample.createCriteria().andJoStatusEqualTo(String.valueOf(Status.STATUS_1.status));
        if (StringUtils.isNotBlank(keyType)) {
            joKeyManageExample.createCriteria().andJoKeyTypeEqualTo(keyType);
        }
        List<JoKeyManage> selectByExample = joKeyManageMapper.selectByExample(joKeyManageExample);
        return selectByExample;
    }

    @Override
    public PageInfo jobOrderQueryLaunchList(Map map, Page page) {
        logger.info("------我发起的工单列表查询------");
        PageInfo pageInfo = new PageInfo();
        map.put("page", page);
        int listCount = joOrderMapper.queryJobOrderLaunchListCount(map);
        List<Map<String, Object>> jobOrderList = joOrderMapper.queryJobOrderLaunchList(map);
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

}
