package com.ryx.jobOrder.service.impl;

import com.ryx.credit.common.enumc.JoOrderStatus;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.result.Result;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.service.IUserService;
import com.ryx.jobOrder.dao.JoOrderMapper;
import com.ryx.jobOrder.dao.JobOrderAuthMapper;
import com.ryx.jobOrder.pojo.JoOrder;
import com.ryx.jobOrder.pojo.JoOrderExample;
import com.ryx.jobOrder.service.JobOrderQueryService;
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


    @Override
    public PageInfo jobOrderQueryList(Map map, Page page) {
        logger.info("------我收到的工单列表查询------");
        PageInfo pageInfo = new PageInfo();
        map.put("page", page);
//        if(null != map.get("userId")) {
//            Long userId = (Long) map.get("userId");
//            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
//            if (orgCodeRes==null && orgCodeRes.size()!=1) {
//                return null;
//            }
//            Map<String, Object> resultMap = orgCodeRes.get(0);
//            String organizationCode = String.valueOf(resultMap.get("ORGANIZATIONCODE"));
//            map.put("organizationCode", organizationCode);
//        }
        int listCount = joOrderMapper.queryJobOrderListCount(map);
        List<Map<String, Object>> jobOrderList = joOrderMapper.queryJobOrderList(map);
        pageInfo.setTotal(listCount);
        pageInfo.setRows(jobOrderList);
        return pageInfo;
    }


    @Override
    public JoOrder getByJobId(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        JoOrder jobOrder = joOrderMapper.selectByPrimaryKey(id);
        return jobOrder;
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
    public AgentResult jobOrderCancle(Map map) {
        logger.info("--查找取消的工单--"+map.get("jobId"));
        AgentResult agentResult = AgentResult.fail("撤销工单失败!");
        JoOrderExample joOrderExample = new JoOrderExample();
        joOrderExample.or().andIdEqualTo(String.valueOf(map.get("jobId")))
        .andJoProgressEqualTo(JoOrderStatus.WCL.key)
        .andLaunchPersonIdEqualTo(String.valueOf(map.get("userId")));
        List<JoOrder> joOrders = joOrderMapper.selectByExample(joOrderExample);
        if (joOrders != null && joOrders.size() == 1){
            JoOrder joOrder = joOrders.get(0);
            joOrder.setJoProgress(JoOrderStatus.CANCLE.key);
            if (joOrderMapper.updateByPrimaryKeySelective(joOrder)==1){
                agentResult.setStatus(200);
                agentResult.setMsg("撤销工单成功");
                logger.error("--撤销工单成功--"+map.get("jobId"));
                return agentResult;
            }else {
                logger.error("--撤销工单失败--"+map.get("jobId"));
                return agentResult;
            }
        }

        return agentResult;
    }

}
