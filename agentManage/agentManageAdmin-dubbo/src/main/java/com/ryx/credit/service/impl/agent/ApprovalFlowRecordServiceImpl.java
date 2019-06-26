package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.common.FieldTranslate;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.ApaycompService;
import com.ryx.credit.service.agent.ApprovalFlowRecordService;
import com.ryx.credit.service.agent.BusActRelService;
import com.ryx.credit.service.bank.PosRegionService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private DateChangeRequestMapper dateChangeRequestMapper;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private ApaycompService apaycompService;
    @Autowired
    private IUserService  iUserService;



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
            criteria.andAgentNameLike("%"+approvalFlowRecord.getAgentName()+"%");
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getAppResult())){
            criteria.andApprovalResultEqualTo(approvalFlowRecord.getAppResult());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getBusId())){
            criteria.andBusIdEqualTo(approvalFlowRecord.getBusId());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getBusType())){
            criteria.andBusTypeEqualTo(approvalFlowRecord.getBusType());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getBeginTime()) && StringUtils.isNotBlank(approvalFlowRecord.getEndTime())){
            Date beginTime = DateUtil.format(approvalFlowRecord.getBeginTime(),"yyyy-MM-dd");
            Date endTime = DateUtil.format(approvalFlowRecord.getEndTime(),"yyyy-MM-dd");
            criteria.andApprovalTimeBetween(beginTime,endTime);
        }else{
            if(StringUtils.isNotBlank(approvalFlowRecord.getBeginTime())){
                Date beginTime = DateUtil.format(approvalFlowRecord.getBeginTime(),"yyyy-MM-dd");
                criteria.andApprovalTimeGreaterThanOrEqualTo(beginTime);
            }
            if(StringUtils.isNotBlank(approvalFlowRecord.getEndTime())){
                Date endTime = DateUtil.format(approvalFlowRecord.getEndTime(),"yyyy-MM-dd");
                criteria.andApprovalTimeLessThanOrEqualTo(endTime);
            }
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
     * 导出查询
     * @param approvalFlowRecord
     * @return
     */
    private List<ApprovalFlowRecord> exprotCommon(ApprovalFlowRecord approvalFlowRecord){

        ApprovalFlowRecordExample approvalFlowRecordExample = new ApprovalFlowRecordExample();
        ApprovalFlowRecordExample.Criteria criteria = approvalFlowRecordExample.createCriteria();
        Date beginTime = DateUtil.format(approvalFlowRecord.getBeginTime(),"yyyy-MM-dd");
        Date endTime = DateUtil.format(approvalFlowRecord.getEndTime(),"yyyy-MM-dd");

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
        if (StringUtils.isNotBlank(approvalFlowRecord.getAppResult())) {
            criteria.andApprovalResultEqualTo(approvalFlowRecord.getAppResult());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getBusId())) {
            criteria.andBusIdEqualTo(approvalFlowRecord.getBusId());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getBusType())) {
            criteria.andBusTypeEqualTo(approvalFlowRecord.getBusType());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getBeginTime()) && StringUtils.isNotBlank(approvalFlowRecord.getEndTime())) {
            criteria.andApprovalTimeBetween(beginTime, endTime);
        }else{
            if (StringUtils.isNotBlank(approvalFlowRecord.getBeginTime())) {
                criteria.andApprovalTimeGreaterThanOrEqualTo(beginTime);
            }
            if (StringUtils.isNotBlank(approvalFlowRecord.getEndTime())) {
                criteria.andApprovalTimeLessThanOrEqualTo(endTime);
            }
        }
        criteria.andStatusEqualTo(Status.STATUS_1.status);

        List<ApprovalFlowRecord> approvalFlowRecords = approvalFlowRecordMapper.selectByExample(approvalFlowRecordExample);
        return approvalFlowRecords;
    }

    /**
     * 导出代理商合并数据
     * @param approvalFlowRecord
     * @return
     */
    @Override
    public List<Map<String, Object>> exportAgentMerge(ApprovalFlowRecord approvalFlowRecord) throws Exception {

        List<Map<String, Object>> resultList = new ArrayList<>();
        List<ApprovalFlowRecord> approvalFlowRecords = exprotCommon(approvalFlowRecord);
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
                COrganization cOrganizationMain = cOrganizationMapper.selectByPrimaryKey(Integer.valueOf(agentMain.getAgDocPro()));
                resultMap.put("agDocProMain", cOrganizationMain.getName());
                Agent agentSub = agentMapper.selectByPrimaryKey(agentMerge.getSubAgentId());
                COrganization cOrganizationSub = cOrganizationMapper.selectByPrimaryKey(Integer.valueOf(agentSub.getAgDocPro()));
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
        List<ApprovalFlowRecord> approvalFlowRecords = exprotCommon(approvalFlowRecord);
        if (null != approvalFlowRecords && approvalFlowRecords.size() != 0) {
            for (ApprovalFlowRecord flowRecord : approvalFlowRecords) {
                Map<String, Object> resultMap = new HashMap<>();
                Agent agent = agentMapper.selectByPrimaryKey(flowRecord.getBusId());
                if(agent==null){
                    continue;
                }
                resultMap.put("cUtime",agent.getcUtime()!=null?DateUtil.format(agent.getcUtime()):"");
                resultMap.put("agentId", agent.getId());
                resultMap.put("agentName", agent.getAgName());

                if(StringUtils.isNotBlank(agent.getAgDocPro())){
                    COrganization cOrganization = cOrganizationMapper.selectByPrimaryKey(Integer.valueOf(agent.getAgDocPro()));
                    if(cOrganization!=null)
                    resultMap.put("agDocPro", cOrganization.getName());
                }

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


    @Override
    public List<Map<String, Object>> exportAgentEdit(ApprovalFlowRecord approvalFlowRecord) throws Exception {

        List<Map<String, Object>> resultList = new ArrayList<>();
        List<ApprovalFlowRecord> approvalFlowRecords = exprotCommon(approvalFlowRecord);
        for (ApprovalFlowRecord flowRecord : approvalFlowRecords) {
            Map<String, Object> resultMap = new HashMap<>();
            List<String> diffList = new ArrayList<>();
            String busId = flowRecord.getBusId();
            DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(busId);
            AgentVo dataPreAgentVo = JsonUtil.jsonToPojo(dateChangeRequest.getDataPreContent(), AgentVo.class);
            AgentVo dataAgentVo = JsonUtil.jsonToPojo(dateChangeRequest.getDataContent(), AgentVo.class);
            contrastObj(dataPreAgentVo.getAgent(), dataAgentVo.getAgent(),diffList);

            List<CapitalVo> preCapitalVoList = dataPreAgentVo.getCapitalVoList();
            List<CapitalVo> capitalVoList = dataAgentVo.getCapitalVoList();
            if (preCapitalVoList != null && capitalVoList != null) {
                if(capitalVoList.size()>preCapitalVoList.size() && preCapitalVoList.size()==0){
                    for (CapitalVo capitalVo : capitalVoList) {
                        if(StringUtils.isNotBlank(capitalVo.getcType())){
                            Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.CAPITAL_TYPE.name(), capitalVo.getcType());
                            diffList.add("新增缴纳款项："+dictByValue.getdItemname());
                        }
                        if(null!=capitalVo.getcAmount())
                        diffList.add("新增缴纳金额："+capitalVo.getcAmount());
                        if(StringUtils.isNotBlank(capitalVo.getcPayType())){
                            Dict payType = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.PAY_TYPE.name(),capitalVo.getcPayType());
                            diffList.add("新增打款方式："+payType.getdItemname());
                        }
                        if(null!=capitalVo.getcFqCount())
                        diffList.add("新增分期期数："+capitalVo.getcFqCount());
                        if(null!=capitalVo.getcPaytime())
                        diffList.add("新增打款时间："+capitalVo.getcPaytime());
                        if(StringUtils.isNotBlank(capitalVo.getcPayuser()))
                        diffList.add("新增打款人："+capitalVo.getcPayuser());
                        if(StringUtils.isNotBlank(capitalVo.getcInCom())){
                            PayComp payComp = apaycompService.selectById(capitalVo.getcInCom());
                            diffList.add("新增收款地方："+payComp.getComName());
                        }
                        if(StringUtils.isNotBlank(capitalVo.getRemark()))
                        diffList.add("新增备注："+capitalVo.getRemark());
                    }
                }else{
                    int i = 0;
                    for (CapitalVo preCapitalVo : preCapitalVoList) {
                        CapitalVo capitalVo = capitalVoList.get(i);
                        contrastObj(preCapitalVo, capitalVo,diffList);
                        i++;
                    }
                }
            }

            List<AgentContractVo> preContractVoList = dataPreAgentVo.getContractVoList();
            List<AgentContractVo> contractVoList = dataAgentVo.getContractVoList();
            if (preContractVoList != null && contractVoList != null) {
                if (contractVoList.size() > preContractVoList.size() && preContractVoList.size() == 0) {
                    for (AgentContractVo agentContractVo : contractVoList) {
                        Dict contractType = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.CONTRACT_TYPE.name(),String.valueOf(agentContractVo.getContType()));
                        diffList.add("新增合同类型："+contractType.getdItemname());
                        diffList.add("新增合同号："+agentContractVo.getContNum());
                        diffList.add("新增合同签约时间："+agentContractVo.getContDate());
                        diffList.add("新增合同到期时间："+agentContractVo.getContEndDate());
                        Dict yesOrNo = dictOptionsService.findDictByValue(DictGroup.ALL.name(), DictGroup.YESORNO.name(),String.valueOf(agentContractVo.getAppendAgree()));
                        diffList.add("新增是否附加协议："+yesOrNo.getdItemname());
                        diffList.add("新增备注："+agentContractVo.getRemark());
                    }
                }
            }else{
                int j = 0;
                for (AgentContractVo preContractVo : preContractVoList) {
                    AgentContractVo contractVo = contractVoList.get(j);
                    contrastObj(preContractVo, contractVo,diffList);
                    j++;
                }
            }

            List<AgentBusInfoVo> preBusInfoVoList = dataPreAgentVo.getBusInfoVoList();
            List<AgentBusInfoVo> busInfoVoList = dataAgentVo.getBusInfoVoList();
            if(busInfoVoList!=null && preBusInfoVoList!=null && busInfoVoList.size()!=0 && preBusInfoVoList.size()!=0){
                int k = 0;
                for (AgentBusInfoVo preBusInfoVo : preBusInfoVoList) {
                    AgentBusInfoVo busInfoVo = busInfoVoList.get(k);
                    contrastObj(preBusInfoVo, busInfoVo,diffList);
                    k++;
                }
            }
            resultMap.put("dateChange",diffList.toString());
            resultList.add(resultMap);
            resultMap.put("agentId",dateChangeRequest.getDataId());
            Agent agent = agentMapper.selectByPrimaryKey(dateChangeRequest.getDataId());
            resultMap.put("agentName",agent.getAgName());
            COrganization cOrganizationSub = cOrganizationMapper.selectByPrimaryKey(Integer.valueOf(agent.getAgDocPro()));
            resultMap.put("agDocPro",cOrganizationSub.getName());
        }
        return resultList;
    }

    private static List<String> contrastObj(Object obj1, Object obj2,List<String> textList) {
        try {
            Class clazz = obj1.getClass();
            Field[] fields = obj1.getClass().getDeclaredFields();
            for (Field field : fields) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(obj1);
                Object o2 = getMethod.invoke(obj2);
                String s1 = o1 == null ? "" : o1.toString();
                String s2 = o2 == null ? "" : o2.toString();
                if (!s1.equals(s2)) {
                    if(!field.getName().equals("agUniqNum") && !field.getName().equals("agStatus") ){
                        textList.add(FieldTranslate.getNameByField(field.getName())+" 修改前：" + s1 + ",修改后：" + s2);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return textList;
    }



    @Override
    public List<Map<String,Object>> queryFlowByExecutionId(String executionId){

        List<Map<String,Object>> resultList = new ArrayList<>();
        ApprovalFlowRecordExample approvalFlowRecordExample = new ApprovalFlowRecordExample();
        ApprovalFlowRecordExample.Criteria criteria = approvalFlowRecordExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andActivityStatusEqualTo(Status.STATUS_1.status);
        criteria.andExecutionIdEqualTo(executionId);
        approvalFlowRecordExample.setOrderByClause(" APPROVAL_TIME ");
        List<ApprovalFlowRecord> approvalFlowRecords = approvalFlowRecordMapper.selectByExample(approvalFlowRecordExample);
        for (ApprovalFlowRecord approvalFlowRecord : approvalFlowRecords) {
            Map<String,Object> resultMap = new HashMap<>();
            CUser cUser = iUserService.selectById(approvalFlowRecord.getApprovalPerson());
            if(null!=cUser){
                resultMap.put("approvalPerson",cUser.getName());
            }else{
                resultMap.put("approvalPerson",approvalFlowRecord.getApprovalPerson());
            }
            resultMap.put("createTime",DateUtil.format(approvalFlowRecord.getApprovalTime(),DateUtil.DATE_FORMAT_1));
            resultMap.put("rs",approvalFlowRecord.getApprovalResult());
            resultMap.put("approvalOpinion",approvalFlowRecord.getApprovalOpinion());
            resultList.add(resultMap);
        }
        return resultList;
    }
}
