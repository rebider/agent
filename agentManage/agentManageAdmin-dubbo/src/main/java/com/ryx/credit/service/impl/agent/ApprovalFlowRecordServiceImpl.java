package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.agent.ApprovalFlowRecordMapper;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.ApprovalFlowRecord;
import com.ryx.credit.pojo.admin.agent.ApprovalFlowRecordExample;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.ApprovalFlowRecordService;
import com.ryx.credit.service.agent.BusActRelService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 已审批待办任务
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
    @Autowired
    private BusActRelService busActRelService;

    @Override
    public String insert(ApprovalFlowRecord record)throws Exception{
        record.setId(idService.genId(TabId.a_approval_flow_record));
        record.setStatus(Status.STATUS_1.status);
        record.setVersion(Status.STATUS_1.status);
        approvalFlowRecordMapper.insert(record);
        return record.getId();
    }

    @Override
    public int update(ApprovalFlowRecord record){
        int i = approvalFlowRecordMapper.updateByPrimaryKeySelective(record);
        return i;
    }

    @Override
    public PageInfo approvalFlowList(ApprovalFlowRecord approvalFlowRecord, Page page) {

        ApprovalFlowRecordExample example = new ApprovalFlowRecordExample();
        ApprovalFlowRecordExample.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(approvalFlowRecord.getApprovalPerson())){
            String[] split = approvalFlowRecord.getApprovalPerson().split(",");
            List<String> personList = new ArrayList<>();
            for(int i=0;i<split.length;i++){
                personList.add(split[i]);
            }
            criteria.andApprovalPersonIn(personList);
        }

        if(StringUtils.isNotBlank(approvalFlowRecord.getApprovalDep())){
            criteria.andApprovalDepEqualTo(approvalFlowRecord.getApprovalDep());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getAgentId())){
            criteria.andAgentIdEqualTo(approvalFlowRecord.getAgentId());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getAgentName())){
            criteria.andAgentNameEqualTo(approvalFlowRecord.getAgentName());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getApprovalResult())){
            criteria.andApprovalResultEqualTo(approvalFlowRecord.getApprovalResult());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getBusId())){
            criteria.andBusIdEqualTo(approvalFlowRecord.getBusId());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getBusType())){
            criteria.andBusTypeEqualTo(approvalFlowRecord.getBusType());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getBeginTime())){
            Date beginTime = DateUtil.format(approvalFlowRecord.getBeginTime());
            criteria.andApprovalTimeGreaterThanOrEqualTo(beginTime);
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getEndTime())){
            Date endTime = DateUtil.format(approvalFlowRecord.getEndTime());
            criteria.andApprovalTimeLessThanOrEqualTo(endTime);
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getBeginTime()) && StringUtils.isNotBlank(approvalFlowRecord.getEndTime())){
            Date beginTime = DateUtil.format(approvalFlowRecord.getBeginTime());
            Date endTime = DateUtil.format(approvalFlowRecord.getEndTime());
            criteria.andApprovalTimeBetween(beginTime,endTime);
        }

        criteria.andStatusEqualTo(Status.STATUS_1.status);
        example.setPage(page);
        example.setOrderByClause("A_APPROVAL_FLOW_RECORD.APPROVAL_TIME desc");

        FastMap par = FastMap.fastMap("ApprovalFlowRecordExample",example);

        if(StringUtils.isNotBlank(approvalFlowRecord.getSubMitDateSta()) && StringUtils.isNotBlank(approvalFlowRecord.getSubMitDateEnd()) ){
            par.putKeyV("subMitDateSta",approvalFlowRecord.getSubMitDateSta());
            par.putKeyV("subMitDateEnd",approvalFlowRecord.getSubMitDateEnd());
        }

        List<ApprovalFlowRecord> approvalFlowRecords = approvalFlowRecordMapper.selectByExampleWithBusActRel(par);
        if(null!=approvalFlowRecords && approvalFlowRecords.size()!=0)
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
            BusActRel busActRel = busActRelService.findById(row.getExecutionId());
            if(null!=busActRel.getcTime())
            row.setSubMitDate(DateUtil.format(busActRel.getcTime(),"yyyy-MM-dd"));
        });
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(approvalFlowRecords);
        pageInfo.setTotal((int)approvalFlowRecordMapper.selectByExampleWithBusActRelCount(par));
        return pageInfo;
    }
}
