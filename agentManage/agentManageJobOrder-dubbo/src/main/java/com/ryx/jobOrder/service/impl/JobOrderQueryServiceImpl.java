package com.ryx.jobOrder.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.result.Result;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.service.IUserService;
import com.ryx.jobOrder.dao.JoOrderMapper;
import com.ryx.jobOrder.pojo.JoOrder;
import com.ryx.jobOrder.pojo.JoOrderExample;
import com.ryx.jobOrder.service.JobOrderQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Result jobOrderCancle(Map map) {
        JoOrder joOrder = joOrderMapper.selectByPrimaryKey(String.valueOf(map.get("jobId")));
        JoOrderExample joOrderExample = new JoOrderExample();
        joOrderExample.or().andIdEqualTo(joOrder.getId());
        int i = joOrderMapper.updateByPrimaryKeySelective(joOrder);
        return null;
    }

}
