package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.ApprovalFlowRecordService;
import com.ryx.credit.service.agent.BusActRelService;
import com.ryx.credit.service.bank.PosRegionService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.font.CreatedFontTracker;
import sun.rmi.runtime.Log;

import java.util.*;

/**
 * 已审批待办任务
 * Created by RYX on 2018/9/4.
 */
@Service("approvalFlowRecordService")
public class ApprovalFlowRecordServiceImpl implements ApprovalFlowRecordService {

    private static Logger logger = LoggerFactory.getLogger(ApprovalFlowRecordServiceImpl.class);
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
    @Autowired
    private AgentMergeMapper agentMergeMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private PosRegionService posRegionService;
    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    AgentBusinfoService agentBusinfoService;


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
            if(null!=busActRel && null!=busActRel.getcTime())
            row.setSubMitDate(DateUtil.format(busActRel.getcTime(),"yyyy-MM-dd"));
        });
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(approvalFlowRecords);
        pageInfo.setTotal((int)approvalFlowRecordMapper.selectByExampleWithBusActRelCount(par));
        return pageInfo;
    }

    /**
     * 导出代理商合并数据
     * @param approvalFlowRecord
     * @return
     */
    @Override
    public List<Map<String, Object>> exportAgentMerge(ApprovalFlowRecord approvalFlowRecord) throws Exception {
        List<Map<String, Object>> resultList = new ArrayList<>();
        ApprovalFlowRecordExample approvalFlowRecordExample = new ApprovalFlowRecordExample();
        ApprovalFlowRecordExample.Criteria criteria = approvalFlowRecordExample.createCriteria();
        Date beginTime = DateUtil.format(approvalFlowRecord.getBeginTime());
        Date endTime = DateUtil.format(approvalFlowRecord.getEndTime());

        if (StringUtils.isNotBlank(approvalFlowRecord.getApprovalPerson())) {
            String[] split = approvalFlowRecord.getApprovalPerson().split(",");
            List<String> personList = new ArrayList<>();
            for (int i=0; i<split.length; i++) {
                personList.add(split[i]);
            }
            criteria.andApprovalPersonIn(personList);
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getApprovalDep())) {
            criteria.andApprovalDepEqualTo(approvalFlowRecord.getApprovalDep());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getAgentId())) {
            criteria.andAgentIdEqualTo(approvalFlowRecord.getAgentId());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getAgentName())) {
            criteria.andAgentNameEqualTo(approvalFlowRecord.getAgentName());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getApprovalResult())) {
            criteria.andApprovalResultEqualTo(approvalFlowRecord.getApprovalResult());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getBusId())) {
            criteria.andBusIdEqualTo(approvalFlowRecord.getBusId());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getBusType())) {
            criteria.andBusTypeEqualTo(approvalFlowRecord.getBusType());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getBeginTime())) {
            criteria.andApprovalTimeGreaterThanOrEqualTo(beginTime);
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getEndTime())) {
            criteria.andApprovalTimeLessThanOrEqualTo(endTime);
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getBeginTime()) && StringUtils.isNotBlank(approvalFlowRecord.getEndTime())) {
            criteria.andApprovalTimeBetween(beginTime, endTime);
        }
        criteria.andStatusEqualTo(Status.STATUS_1.status);

        List<ApprovalFlowRecord> approvalFlowRecords = approvalFlowRecordMapper.selectByExample(approvalFlowRecordExample);
        if (null != approvalFlowRecords && approvalFlowRecords.size() != 0) {
            for (ApprovalFlowRecord flowRecord : approvalFlowRecords) {
                Map<String, Object> resultMap = new HashMap<>();
                AgentMerge agentMerge = agentMergeMapper.selectByPrimaryKey(flowRecord.getBusId());
                resultMap.put("uTime", DateUtil.format(agentMerge.getuTime()));
                resultMap.put("mainAgentId", agentMerge.getMainAgentId());
                resultMap.put("mainAgentName", agentMerge.getMainAgentName());
                resultMap.put("subAgentId", agentMerge.getSubAgentId());
                resultMap.put("subAgentName", agentMerge.getSubAgentName());

                Agent agentMain = agentMapper.selectByPrimaryKey(agentMerge.getMainAgentId());
                COrganization cOrganizationMain = cOrganizationMapper.selectByPrimaryKey(agentMain.getAgDocPro());
                resultMap.put("agDocProMain", cOrganizationMain.getName());
                Agent agentSub = agentMapper.selectByPrimaryKey(agentMerge.getSubAgentId());
                COrganization cOrganizationSub = cOrganizationMapper.selectByPrimaryKey(agentSub.getAgDocPro());
                resultMap.put("agDocProSub", cOrganizationSub.getName());

                List<AgentBusInfo> agentBusInfosMain = agentBusInfoMapper.selectByAgenId(agentMain.getId());
                if (null != agentBusInfosMain && agentBusInfosMain.size() != 0) {
                    String busTypeMain = "";
                    String busPlatformMain = "";
                    String busRegionMain = "";
                    for (AgentBusInfo agentBusInfo : agentBusInfosMain) {
                        busRegionMain += posRegionService.queryNameByCodes(agentBusInfo.getBusRegion()) + ",";
                        busTypeMain += BusType.getContentByValue(agentBusInfo.getBusType()) + ",";
                        busPlatformMain += Platform.getContentByValue(agentBusInfo.getBusPlatform()) + ",";

                    }
                    resultMap.put("busTypeMain", StringUtils.isNotBlank(busTypeMain) ? busTypeMain.substring(0, busTypeMain.length() -1) : busTypeMain);
                    resultMap.put("busPlatformMain", StringUtils.isNotBlank(busPlatformMain) ? busPlatformMain.substring(0, busPlatformMain.length() -1) : busPlatformMain);
                    resultMap.put("busRegionMain", StringUtils.isNotBlank(busRegionMain) ? busRegionMain.substring(0, busRegionMain.length() -1) : busRegionMain);
                }

                List<AgentBusInfo> agentBusInfosSub = agentBusInfoMapper.selectByAgenId(agentSub.getId());
                String busTypeSub = "";
                String busPlatformSub = "";
                String busRegionSub = "";
                if (null != agentBusInfosSub && agentBusInfosSub.size() != 0) {
                    for (AgentBusInfo agentBusInfo : agentBusInfosSub) {
                        busTypeSub += posRegionService.queryNameByCodes(agentBusInfo.getBusRegion()) + ",";
                        busPlatformSub += BusType.getContentByValue(agentBusInfo.getBusType()) + ",";
                        busRegionSub += Platform.getContentByValue(agentBusInfo.getBusPlatform()) + ",";
                    }
                    resultMap.put("busTypeSub", StringUtils.isNotBlank(busTypeSub) ? busTypeSub.substring(0, busTypeSub.length() -1 ) : busTypeSub);
                    resultMap.put("busPlatformSub", StringUtils.isNotBlank(busPlatformSub) ? busPlatformSub.substring(0, busPlatformSub.length() -1) : busPlatformSub);
                    resultMap.put("busRegionSub", StringUtils.isNotBlank(busRegionSub) ? busRegionSub.substring(0, busRegionSub.length() -1) : busRegionSub);
                }
                resultList.add(resultMap);
            }
        }
        return resultList;
    }

    /**
     * 导出代理商入网数据
     * @param approvalFlowRecord
     * @return
     */
    @Override
    public List<Map<String, Object>> exportAgentNetln(ApprovalFlowRecord approvalFlowRecord) throws Exception {
        List<Map<String, Object>> resultList = new ArrayList<>();
        ApprovalFlowRecordExample approvalFlowRecordExample = new ApprovalFlowRecordExample();
        ApprovalFlowRecordExample.Criteria criteria = approvalFlowRecordExample.createCriteria();
        Date beginTime = DateUtil.format(approvalFlowRecord.getBeginTime());
        Date endTime = DateUtil.format(approvalFlowRecord.getEndTime());

        if (StringUtils.isNotBlank(approvalFlowRecord.getApprovalPerson())) {
            String[] split = approvalFlowRecord.getApprovalPerson().split(",");
            List<String> personList = new ArrayList<>();
            for (int i=0; i<split.length; i++) {
                personList.add(split[i]);
            }
            criteria.andApprovalPersonIn(personList);
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getApprovalDep())) {
            criteria.andApprovalDepEqualTo(approvalFlowRecord.getApprovalDep());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getAgentId())) {
            criteria.andAgentIdEqualTo(approvalFlowRecord.getAgentId());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getAgentName())) {
            criteria.andAgentNameEqualTo(approvalFlowRecord.getAgentName());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getApprovalResult())) {
            criteria.andApprovalResultEqualTo(approvalFlowRecord.getApprovalResult());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getBusId())) {
            criteria.andBusIdEqualTo(approvalFlowRecord.getBusId());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getBusType())) {
            criteria.andBusTypeEqualTo(approvalFlowRecord.getBusType());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getBeginTime())) {
            criteria.andApprovalTimeGreaterThanOrEqualTo(beginTime);
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getEndTime())) {
            criteria.andApprovalTimeLessThanOrEqualTo(endTime);
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getBeginTime()) && StringUtils.isNotBlank(approvalFlowRecord.getEndTime())) {
            criteria.andApprovalTimeBetween(beginTime, endTime);
        }
        criteria.andStatusEqualTo(Status.STATUS_1.status);

        List<ApprovalFlowRecord> approvalFlowRecords = approvalFlowRecordMapper.selectByExample(approvalFlowRecordExample);
        if (null != approvalFlowRecords && approvalFlowRecords.size() != 0) {
            for (ApprovalFlowRecord flowRecord : approvalFlowRecords) {
                Map<String, Object> resultMap = new HashMap<>();
                Agent agent = agentMapper.selectByPrimaryKey(flowRecord.getBusId());
                resultMap.put("cUtime", DateUtil.format(agent.getcUtime()));
                resultMap.put("agentId", agent.getId());
                resultMap.put("agentName", agent.getAgName());

                COrganization cOrganization = cOrganizationMapper.selectByPrimaryKey(agent.getAgDocPro());
                resultMap.put("agDocPro", cOrganization.getName());

                List<AgentBusInfo> agentBusInfosList = agentBusInfoMapper.selectByAgenId(agent.getId());
                if (null != agentBusInfosList && agentBusInfosList.size() != 0) {
                    String busType = "";
                    String busPlatform = "";
                    String busRegion = "";
                    String parentBusInfo = "";
                    for (AgentBusInfo agentBusInfos : agentBusInfosList) {
                        busRegion += posRegionService.queryNameByCodes(agentBusInfos.getBusRegion()) + ",";
                        busType += BusType.getContentByValue(agentBusInfos.getBusType()) + ",";
                        busPlatform += Platform.getContentByValue(agentBusInfos.getBusPlatform()) + ",";

                        List<Map> parentListFromBusInfo = agentBusinfoService.getParentListFromBusInfo(null, agentBusInfos.getId());
                        parentBusInfo += Platform.getContentByValue(agentBusInfos.getBusPlatform())+"：";
                        for (Map map : parentListFromBusInfo) {
                            parentBusInfo += BusType.getContentByValue(String.valueOf(map.get("BUS_TYPE"))) + "-" + map.get("AG_NAME") + ",";
                        }
                        if (StringUtils.isNotBlank(parentBusInfo)) {
                            parentBusInfo = parentBusInfo.substring(0, parentBusInfo.length() -1);
                        }
                        parentBusInfo += "&";
                    }
                    resultMap.put("busType", StringUtils.isNotBlank(busType) ? busType.substring(0, busType.length() -1) : busType);
                    resultMap.put("busPlatform", StringUtils.isNotBlank(busPlatform) ? busPlatform.substring(0, busPlatform.length() -1) : busPlatform);
                    resultMap.put("busRegion", StringUtils.isNotBlank(busRegion) ? busRegion.substring(0, busRegion.length() -1) : busRegion);
                    resultMap.put("parentBusInfo", StringUtils.isNotBlank(parentBusInfo) ? parentBusInfo.substring(0, parentBusInfo.length() -1) : parentBusInfo);
                }

                String cPayType = "";
                String cpayuser = "";
                String cPaytime = "";
                List<Capital> capitalsList = capitalMapper.selectByAgenId(agent.getId());
                if (null != capitalsList && capitalsList.size() != 0) {
                    for (Capital capital : capitalsList) {
                        cPayType += PayType.getPayType(capital.getcPayType()) + ",";
                        cpayuser += capital.getcPayuser() + ",";
                        cPaytime += DateUtil.format(capital.getcPaytime()) + ",";
                    }
                }
                resultMap.put("cPayType", StringUtils.isNotBlank(cPayType) ? cPayType.substring(0, cPayType.length() -1 ) : cPayType);
                resultMap.put("cpayuser", StringUtils.isNotBlank(cpayuser) ? cpayuser.substring(0, cpayuser.length() -1) : cpayuser);
                resultMap.put("cPaytime", StringUtils.isNotBlank(cPaytime) ? cPaytime.substring(0, cPaytime.length() -1) : cPaytime);

                resultList.add(resultMap);
            }
        }
        return resultList;
    }


}
