package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.agent.ApprovalFlowRecordMapper;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.ApprovalFlowRecord;
import com.ryx.credit.pojo.admin.agent.ApprovalFlowRecordExample;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.ApprovalFlowRecordService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by RYX on 2018/9/4.
 */
@Service("approvalFlowRecordService")
public class ApprovalFlowRecordServiceImpl implements ApprovalFlowRecordService {

    @Autowired
    private ApprovalFlowRecordMapper approvalFlowRecordMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private COrganizationMapper cOrganizationMapper;
    @Autowired
    private IUserService userService;

    @Override
    public int insert(ApprovalFlowRecord record)throws Exception{

        record.setId(idService.genId(TabId.a_approval_flow_record));
        record.setStatus(Status.STATUS_1.status);
        record.setVersion(Status.STATUS_1.status);
        int insert = approvalFlowRecordMapper.insert(record);
        return insert;
    }


    @Override
    public PageInfo approvalFlowList(ApprovalFlowRecord approvalFlowRecord, Page page) {

        ApprovalFlowRecordExample example = new ApprovalFlowRecordExample();
        ApprovalFlowRecordExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(approvalFlowRecord.getApprovalPerson())){
            criteria.andApprovalPersonEqualTo(approvalFlowRecord.getApprovalPerson());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getApprovalDep())){
            criteria.andApprovalDepEqualTo(approvalFlowRecord.getApprovalDep());
        }
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        example.setPage(page);
        example.setOrderByClause("APPROVAL_TIME desc");
        List<ApprovalFlowRecord> approvalFlowRecords = approvalFlowRecordMapper.selectByExample(example);
        approvalFlowRecords.forEach(row->{
            row.setBusTypeName(BusActRelBusType.getItemString(row.getBusType()));
            COrganization cOrganization = cOrganizationMapper.selectById(Integer.valueOf(row.getApprovalDep()));
            if(null!=cOrganization){
                row.setApprovalDep(cOrganization.getName());
            }
            CUser cUser = userService.selectById(Integer.valueOf(row.getApprovalPerson()));
            if(null!=cUser){
                row.setApprovalPerson(cUser.getName());
            }
        });
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(approvalFlowRecords);
        pageInfo.setTotal((int)approvalFlowRecordMapper.countByExample(example));
        return pageInfo;
    }
}
