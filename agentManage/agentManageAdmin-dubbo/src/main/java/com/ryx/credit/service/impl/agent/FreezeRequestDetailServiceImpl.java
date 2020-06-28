package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.agent.FreezeRequestDetailMapper;
import com.ryx.credit.service.agent.FreezeRequestDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("freezeRequestDetailService")
public class FreezeRequestDetailServiceImpl implements FreezeRequestDetailService {

    @Autowired
    private FreezeRequestDetailMapper freezeRequestDetailMapper;

    @Override
    public PageInfo agentFreezeDetailList(Map map, Page page) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(freezeRequestDetailMapper.queryFreezeDetials(map, page));
        pageInfo.setTotal(freezeRequestDetailMapper.queryFreezeDetialsCount(map,page));
        return pageInfo;
    }

    @Override
    public PageInfo queryModifyDetail(Map map,Page page) {
        PageInfo pageInfo = new PageInfo();

        List<Map<String,Object>> freezeRequestDetails = freezeRequestDetailMapper.queryFreezeDetials(map,page);
        if (freezeRequestDetails == null ){
            return null;
        }
        pageInfo.setRows(freezeRequestDetails);
        return pageInfo;
    }
}
