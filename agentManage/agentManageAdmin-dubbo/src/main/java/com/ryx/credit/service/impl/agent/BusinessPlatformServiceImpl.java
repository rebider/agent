package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.IResourceService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.agent.netInPort.AgentNetInNotityService;
import com.ryx.credit.service.bank.PosRegionService;
import com.ryx.credit.service.dict.DictOptionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.management.resources.agent;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 业务平台管理
 *
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/22 9:26
 */
@Service("businessPlatformService")
public class BusinessPlatformServiceImpl implements BusinessPlatformService {
    private Logger logger = LoggerFactory.getLogger(BusinessPlatformServiceImpl.class);
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private AgentContractService agentContractService;
    @Autowired
    private AccountPaidItemService accountPaidItemService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private AgentDataHistoryService agentDataHistoryService;
    @Autowired
    private PayCompMapper payCompMapper;
    @Autowired
    private AimportService aimportService;
    @Autowired
    private AgentNotifyService agentNotifyService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private PosRegionService posRegionService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private IResourceService iResourceService;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private AssProtoColMapper assProtoColMapper;
    @Autowired
    private AgentAssProtocolService agentAssProtocolService;
    @Autowired
    private COrganizationMapper organizationMapper;
    @Autowired
    private AgentNetInNotityService agentNetInNotityService;


    @Override
    public PageInfo queryBusinessPlatformList(Map map, Agent agent, Page page, Long userId) {
        Map<String, Object> reqMap = new HashMap<>();

        reqMap.put("agStatus", AgStatus.Approved.name());
        if (!StringUtils.isBlank(agent.getId())) {
            reqMap.put("id", agent.getId());
        }
        if (!StringUtils.isBlank((String)map.get("ag"))) {
            reqMap.put("ag", (String)map.get("ag"));
        }
        if (!StringUtils.isBlank(agent.getAgName())) {
            reqMap.put("agName", agent.getAgName());
        }
        if (!StringUtils.isBlank(agent.getAgUniqNum())) {
            reqMap.put("agUniqNum", agent.getAgUniqNum());
        }
        if (!StringUtils.isBlank((String)map.get("busNum"))) {
            reqMap.put("busNum", map.get("busNum"));
        }
        if (!StringUtils.isBlank((String)map.get("busPlatformList"))) {
            reqMap.put("busPlatformList", Arrays.asList( ((String)map.get("busPlatformList")).split(",")));
        }
        if (!StringUtils.isBlank((String)map.get("cloReviewStatusList"))) {
            List<String> list = Arrays.asList( ((String)map.get("cloReviewStatusList")).split(","));
            List<BigDecimal> voList = list.stream().map(str -> new BigDecimal(str.trim())).collect(Collectors.toList());
            if( voList!=null && voList.size()>0)
                reqMap.put("cloReviewStatusList", voList);
        }
        if (!StringUtils.isBlank((String)map.get("busTypeList"))) {
            reqMap.put("busTypeList", Arrays.asList( ((String)map.get("busTypeList")).split(",")));
        }
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf((String)map.get("cUser")));
        if(orgCodeRes==null && orgCodeRes.size()!=1){
            return null;
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String orgId = String.valueOf(stringObjectMap.get("ORGID"));
        String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        reqMap.put("orgId",orgId);
        reqMap.put("userId",Long.valueOf((String)map.get("cUser")));
        reqMap.put("organizationCode", organizationCode);
        reqMap.put("status", Status.STATUS_1.status);
        List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
        reqMap.put("platfromPerm",platfromPerm);
        List<Map<String, Object>> agentBusInfoList = agentBusInfoMapper.queryBusinessPlatformList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentBusInfoList);
        pageInfo.setTotal(agentBusInfoMapper.queryBusinessPlatformCount(reqMap));
        return pageInfo;
    }

    @Override
    public PageInfo queryBusinessPlatformList(AgentBusInfo agentBusInfo, Agent agent, Page page,Long userId) {
        Map<String, Object> reqMap = new HashMap<>();

        reqMap.put("agStatus", AgStatus.Approved.name());
        if (!StringUtils.isBlank(agent.getId())) {
            reqMap.put("id", agent.getId());
        }
        if (!StringUtils.isBlank(agent.getAgName())) {
            reqMap.put("agName", agent.getAgName());
        }
        if (!StringUtils.isBlank(agent.getAgUniqNum())) {
            reqMap.put("agUniqNum", agent.getAgUniqNum());
        }
        if (!StringUtils.isBlank(agentBusInfo.getBusNum())) {
            reqMap.put("busNum", agentBusInfo.getBusNum());
        }
        if (!StringUtils.isBlank(agentBusInfo.getBusPlatform())) {
            reqMap.put("busPlatform", agentBusInfo.getBusPlatform());
        }
        if (agentBusInfo.getCloReviewStatus() != null) {
            reqMap.put("cloReviewStatus", agentBusInfo.getCloReviewStatus());
        }
        if (agentBusInfo.getBusType() != null) {
            reqMap.put("busType", agentBusInfo.getBusType());
        }
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(agentBusInfo.getcUser()));
        if(orgCodeRes==null && orgCodeRes.size()!=1){
            return null;
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String orgId = String.valueOf(stringObjectMap.get("ORGID"));
        String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        reqMap.put("orgId",orgId);
        reqMap.put("userId",Long.valueOf(agentBusInfo.getcUser()));
        reqMap.put("organizationCode", organizationCode);
        reqMap.put("status", Status.STATUS_1.status);
        List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
        reqMap.put("platfromPerm",platfromPerm);
        List<Map<String, Object>> agentBusInfoList = agentBusInfoMapper.queryBusinessPlatformList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentBusInfoList);
        pageInfo.setTotal(agentBusInfoMapper.queryBusinessPlatformCount(reqMap));
        return pageInfo;
    }

    @Override
    public PageInfo queryBusinessPlatformListManager(Page page, Map map) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("agStatus", AgStatus.Approved.name());
        if (!StringUtils.isBlank((String)map.get("id"))) {
            reqMap.put("id", map.get("id"));
        }
        if (!StringUtils.isBlank((String)map.get("ag"))) {
            reqMap.put("ag", (String)map.get("ag"));
        }
        if (!StringUtils.isBlank((String)map.get("agName"))) {
            reqMap.put("agName", (String)map.get("agName"));
        }
        if (!StringUtils.isBlank((String)map.get("agUniqNum"))) {
            reqMap.put("agUniqNum", (String)map.get("agUniqNum"));
        }
        if (!StringUtils.isBlank((String)map.get("busNum"))) {
            reqMap.put("busNum", (String)map.get("busNum"));
        }
        if (!StringUtils.isBlank((String)map.get("busPlatformList"))) {
            reqMap.put("busPlatformList", Arrays.asList(((String) map.get("busPlatformList")).split(",")));
        }
        if (!StringUtils.isBlank((String)map.get("cloReviewStatusList"))) {// bigdecimal 处理
            List<String> list = Arrays.asList( ((String)map.get("cloReviewStatusList")).split(","));
            List<BigDecimal> voList = list.stream().map(str -> new BigDecimal(str.trim())).collect(Collectors.toList());
            if( voList!=null && voList.size()>0)
                reqMap.put("cloReviewStatusList", voList);
        }
        if (StringUtils.isNotBlank((String)map.get("busTypeList"))) {
            reqMap.put("busTypeList", Arrays.asList(((String)map.get("busTypeList")).split(",")));
        }
        if ( StringUtils.isNotBlank((String)map.get("approveTimeStart"))) {
            reqMap.put("approveTimeStart", (String)map.get("approveTimeStart"));
        }
        if ( StringUtils.isNotBlank((String)map.get("approveTimeEnd"))) {
            reqMap.put("approveTimeEnd", (String)map.get("approveTimeEnd"));
        }
        if ((String)map.get("busStatus") != null) {
            reqMap.put("busStatus", new BigDecimal((String)map.get("busStatus")));
        }
        reqMap.put("status", Status.STATUS_1.status);
        List<Map> platfromPerm = iResourceService.userHasPlatfromPerm((Long)map.get("userId"));
        reqMap.put("platfromPerm",platfromPerm);
        List<Map<String, Object>> agentBusInfoList = agentBusInfoMapper.queryBusinessPlatformList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentBusInfoList);
        pageInfo.setTotal(agentBusInfoMapper.queryBusinessPlatformCount(reqMap));
        return pageInfo;
    }

    /**
     * 根据代理商唯一编号检索
     * @param agUniqNum
     * @return
     */
    @Override
    public AgentResult verifyAgent(String agUniqNum,List<String> agStatusList) {
        AgentResult result = new AgentResult(500,"参数错误","");
        if (StringUtils.isBlank(agUniqNum)) {
            return result;
        }
        AgentExample example = new AgentExample();
        AgentExample.Criteria criteria = example.createCriteria();
        criteria.andAgUniqNumEqualTo(agUniqNum);
        criteria.andAgStatusIn(agStatusList);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<Agent> agents = agentMapper.selectByExample(example);
        if(agents.size()==1){
            return AgentResult.ok(agents.get(0));
        }
        if (agents.size()>1) {
            result.setMsg("代理商不唯一");
            return result;
        }
        AgentExample exampleName = new AgentExample();
        AgentExample.Criteria criteriaName = exampleName.createCriteria();
        criteriaName.andAgNameEqualTo(agUniqNum);
        criteriaName.andAgStatusIn(agStatusList);
        criteriaName.andStatusEqualTo(Status.STATUS_1.status);
        List<Agent> agentsName = agentMapper.selectByExample(exampleName);
        if(agentsName.size()==1){
            return AgentResult.ok(agentsName.get(0));
        }
        if (agentsName.size()>1) {
            result.setMsg("代理商不唯一");
            return result;
        }
        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria agentBusInfoCriteria = agentBusInfoExample.createCriteria();
        agentBusInfoCriteria.andBusNumEqualTo(agUniqNum);
        List<BigDecimal> busStatusList = new ArrayList<>();
        busStatusList.add(BusinessStatus.Enabled.status);
        busStatusList.add(BusinessStatus.inactive.status);
        agentBusInfoCriteria.andBusStatusIn(busStatusList);
        agentBusInfoCriteria.andStatusEqualTo(Status.STATUS_1.status);
        agentBusInfoCriteria.andCloReviewStatusEqualTo(AgStatus.Approved.status);
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
        if(agentBusInfos.size()==1){
            AgentBusInfo agentBusInfo = agentBusInfos.get(0);
            Agent agent = agentMapper.selectByPrimaryKey(agentBusInfo.getAgentId());
            return AgentResult.ok(agent);
        }
        if (agentsName.size()>1) {
            result.setMsg("代理商不唯一");
            return result;
        }
        result.setMsg("代理商不存在");
        return result;
    }

    @Override
    public AgentBusInfo findById(String id) {
        AgentBusInfo agentBusInfo = null;
        if (StringUtils.isBlank(id)) {
            return agentBusInfo;
        } else {
            agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(id);
            if(null!=agentBusInfo){
                PlatForm platForm = platFormService.selectByPlatformNum(agentBusInfo.getBusPlatform());
                if(null!=platForm){
                    agentBusInfo.setBusPlatformType(platForm.getPlatformType());
                }
                Map<String,Object> parentInfo = agentBusInfoMapper.queryBusInfoParent(FastMap.fastMap("id",agentBusInfo.getId()));
                agentBusInfo.setParentInfo(parentInfo);
            }
        }
        return agentBusInfo;
    }

    @Override
    public AgentBusInfo findByAgentId(String id) {
        AgentBusInfo agentBusInfo = null;
        if (StringUtils.isBlank(id)) {
            return agentBusInfo;
        } else {
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andAgentIdEqualTo(id);
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            if (null!=agentBusInfos && agentBusInfos.size()>0){
                agentBusInfo=agentBusInfos.get(0);
            }

        }
        return agentBusInfo;
    }

    @Override
    public int updateByPrimaryKeySelective(AgentBusInfo agentBusInfo) {
        if (StringUtils.isBlank(agentBusInfo.getId())) {
            return 0;
        }
        AgentBusInfo agbus = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getId());
        agentBusInfo.setVersion(agbus.getVersion());
        int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
        return i;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public void updateBusInfoList(List<AgentBusInfoVo> busInfoVoList)throws Exception{
        if (busInfoVoList==null) {
            throw new MessageException("信息错误");
        }
        if (busInfoVoList.size()==0) {
            throw new MessageException("信息错误");
        }
        try{
            AgentVo agentVo = new AgentVo();
            agentVo.setBusInfoVoList(busInfoVoList);
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busInfoVoList.get(0).getId());
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByAgenId(agentBusInfo.getAgentId());
            for (AgentBusInfoVo item : busInfoVoList) {
                if (StringUtils.isBlank(item.getBusPlatform())){
                    logger.info("请选择业务平台");
                    throw new ProcessException("请选择业务平台");
                }
                //校验实时分润不能升级
                List platformList = platFormMapper.selectPlatformNumByPlatformType();
                boolean checkBusPlatform = platformList.contains(item.getBusPlatform()) && (null != item.getBusNum() && !"".equals(item.getBusNum()));
                if (checkBusPlatform) throw new ProcessException("实时分润品牌暂不支持升级！");

                if(StringUtils.isNotBlank(item.getBusNum())) {
                    if (!OrgType.zQ(item.getBusType())) {
                        throw new ProcessException("升级类型必须是直签");
                    }
                    if (StringUtils.isBlank(item.getBusParent())){
                        throw new ProcessException("升级直签上级不能为空");
                    }
                    Map<String, Object> reqMap = new HashMap<>();
                    reqMap.put("busInfo",item);
                    reqMap.put("agentVo",agentVo);
                    AgentResult agentResult = agentNetInNotityService.agencyLevelCheck(reqMap);
                    if(!agentResult.isOK()){
                        throw new ProcessException(agentResult.getMsg());
                    }
                }
                if(OrgType.zQ(item.getBusType())){
                    if(StringUtils.isBlank(item.getBusParent()))
                        throw new ProcessException("直签上级不能为空");
                }
                //代理商选择上级代理商时添加限制 不能选择同级别代理商为上级
                if (StringUtils.isNotBlank(item.getBusParent())) {
                    //获取上级代理商类型
                    AgentBusInfo busInfo = agentBusinfoService.getById(item.getBusParent());
                    if (item.getBusType().equals(BusType.ZQ.key) || item.getBusType().equals(BusType.ZQBZF.key) || item.getBusType().equals(BusType.ZQZF.key)) {
                        if (busInfo.getBusType().equals(BusType.ZQ.key) || busInfo.getBusType().equals(BusType.ZQZF.key) || busInfo.getBusType().equals(BusType.ZQBZF.key)) {
                            throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                        }
                    }
                    if (item.getBusType().equals(BusType.YDX.key)) {
                        if (busInfo.getBusType().equals(BusType.ZQ.key) || busInfo.getBusType().equals(BusType.YDX.key)
                                || busInfo.getBusType().equals(BusType.ZQZF.key) || busInfo.getBusType().equals(BusType.ZQBZF.key)) {
                            throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                        }
                    }
                    if (item.getBusType().equals(BusType.JGYD.key)) {
                        if (!busInfo.getBusType().equals(BusType.JG.key)) {
                            throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                        }
                    }
                }

                //判断标准一代、机构只能有一个业务对接省区
                if(StringUtils.isNotBlank(item.getBusType()) && StringUtils.isNotBlank(item.getAgentId()) && StringUtils.isNotBlank(item.getAgDocPro()) &&StringUtils.isNotBlank(item.getAgDocDistrict())){

                    if(item.getBusType().equals(BusType.JG.key) ||item.getBusType().equals(BusType.BZYD.key)){
                        Map<String, String> hashMap = new HashMap<>();
                        hashMap.put("agentId",item.getAgentId());
                        hashMap.put("busType",BusType.JG.key);
                        hashMap.put("busTypeOne",BusType.BZYD.key);
                        List<AgentBusInfo> busInfoList = agentBusInfoMapper.queryBusinfo(hashMap);
                        Set<String> hashSetDocDistrict = new HashSet<>();
                        Set<String> hashSetocPro = new HashSet<>();
                        if(null!=busInfoList && busInfoList.size()>0){
                            for (AgentBusInfo busInfo : busInfoList) {
                                hashSetocPro.add(busInfo.getAgDocPro());
                                hashSetDocDistrict.add(busInfo.getAgDocDistrict());
                            }
                            if(hashSetDocDistrict.size()==1 && hashSetocPro.size()==1){
                                //如果只有一个则进行更改大区省区
                                for (AgentBusInfo busInfo : busInfoList) {
                                    if(StringUtils.isNotBlank(busInfo.getAgDocPro()) && StringUtils.isNotBlank(busInfo.getAgDocDistrict())){
                                        if(!busInfo.getId().equals(item.getId())){
                                            if(!busInfo.getAgDocPro().equals(item.getAgDocPro()) || !busInfo.getAgDocDistrict().equals(item.getAgDocDistrict())) {
                                                logger.info("机构/标准一代省区必须一致，不能修改");
                                                throw new ProcessException("机构/标准一代省区必须一致，不能修改");
                                            }
                                        }
                                    }
                                }
                            }else if(hashSetDocDistrict.size()>1 || hashSetocPro.size()>1){
                                logger.info("请联系市场部统一更改后再开通业务或修改业务");
                                throw new ProcessException("请联系市场部统一更改后再开通业务或修改业务");
                            }
                        }
                    }
                }else{
                    if(StringUtils.isBlank(item.getAgDocDistrict()) || StringUtils.isBlank(item.getAgDocPro())){
                        logger.info("请填写大区或者省区");
                        throw new ProcessException("请填写大区或者省区");
                    }
                }

                //激活返现代理商为空默认自己
                if (StringUtils.isBlank(item.getBusActivationParent())) {
                    item.setBusActivationParent(item.getId());
                }
                //借贷记费率封顶额默认值
                AgentBusInfo agbus = agentBusInfoMapper.selectByPrimaryKey(item.getId());
                Dict debitRateLower = dictOptionsService.findDictByName(DictGroup.AGENT.name(), item.getBusPlatform(), "debitRateLower");//借记费率下限（%）
                Dict debitCapping = dictOptionsService.findDictByName(DictGroup.AGENT.name(), item.getBusPlatform(), "debitCapping");//借记封顶额上限（元）
                Dict debitAppearRate = dictOptionsService.findDictByName(DictGroup.AGENT.name(), item.getBusPlatform(), "debitAppearRate");//借记出款费率（%）
                Dict creditRateFloor = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), item.getBusPlatform(), "creditRateFloor");//贷记费率下限（%）
                Dict creditRateCeiling = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), item.getBusPlatform(), "creditRateCeiling");//贷记费率上限（%）
                Dict debitRateCapping = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), item.getBusPlatform(), "debitRateCapping");//借记费率上限（%）
                Dict debitCappingLower = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), item.getBusPlatform(), "debitCappingLower");//借记封顶额下限（元）
                if (debitRateLower==null) {
                    item.setDebitRateLower("");
                } else {
                    item.setDebitRateLower(debitRateLower.getdItemvalue());
                }
                if (debitCapping==null) {
                    item.setDebitCapping("");
                } else {
                    item.setDebitCapping(debitCapping.getdItemvalue());
                }
                if (debitAppearRate==null) {
                    item.setDebitAppearRate("");
                } else {
                    item.setDebitAppearRate(debitAppearRate.getdItemvalue());
                }
                if (creditRateFloor==null) {
                    item.setCreditRateFloor("");
                } else {
                    item.setCreditRateFloor(creditRateFloor.getdItemname());
                }
                if (creditRateCeiling==null) {
                    item.setCreditRateCeiling("");
                } else {
                    item.setCreditRateCeiling(creditRateCeiling.getdItemname());
                }
                if (debitRateCapping==null) {
                    item.setDebitRateCapping("");
                } else {
                    item.setDebitRateCapping(debitRateCapping.getdItemname());
                }
                if (debitCappingLower==null) {
                    item.setDebitCappingLower("");
                } else {
                    item.setDebitCappingLower(debitCappingLower.getdItemname());
                }
                item.setVersion(agbus.getVersion());
                int i = agentBusInfoMapper.updateByPrimaryKeySelective(item);
                if (i!=1) {
                    throw new MessageException("更新失败");
                }
            }
            String json = JsonUtil.objectToJson(agentBusInfos);
            List<AgentBusInfoVo> agentBusInfoVos = JsonUtil.jsonToList(json, AgentBusInfoVo.class);
            agentEnterService.verifyOrgAndBZYD(agentBusInfoVos, busInfoVoList);
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    //业务类型更改限制
    @Override
    public void verifyBusinfoType(AgentBusInfo preBusInfoVo, AgentBusInfoVo newBusInfoVo) throws Exception {
        if (StringUtils.isNotBlank(preBusInfoVo.getBusType())) {
            AgentBusInfo busInfoParent = agentBusInfoMapper.selectByPrimaryKey(newBusInfoVo.getBusParent());//上级代理商
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andBusParentEqualTo(newBusInfoVo.getId());
            criteria.andBusStatusNotIn(Arrays.asList(BusStatus.YWTC.status, BusStatus.TZQ.status, BusStatus.YWQY.status));
            List<AgentBusInfo> busInfoLowerList = agentBusInfoMapper.selectByExample(agentBusInfoExample);//下级代理商
            List<String> stringList = new ArrayList<String>();
            for (AgentBusInfo busInfoLower : busInfoLowerList) {
                String busTypeLower = busInfoLower.getBusType();
                stringList.add(busTypeLower);
            }
            if (preBusInfoVo.getBusType().equals(BusType.ZQBZF.key)) {
                if (!newBusInfoVo.getBusType().equals(BusType.ZQBZF.key)) {
                    throw new MessageException("直签不直发类型不允许更改！");
                }
            } else if (preBusInfoVo.getBusType().equals(BusType.JG.key) || preBusInfoVo.getBusType().equals(BusType.BZYD.key)) {
                if (newBusInfoVo.getBusType().equals(BusType.JG.key)!=preBusInfoVo.getBusType().equals(BusType.JG.key)
                        && newBusInfoVo.getBusType().equals(BusType.BZYD.key)!=preBusInfoVo.getBusType().equals(BusType.BZYD.key)) {
                    throw new MessageException("机构/标准一代类型不允许修改！");
                }
            } else if (newBusInfoVo.getBusType().equals(BusType.JG.key)  || newBusInfoVo.getBusType().equals(BusType.BZYD.key)) {
                if (preBusInfoVo.getBusType().equals(BusType.ZQZF.key) || preBusInfoVo.getBusType().equals(BusType.YDX.key)
                        || preBusInfoVo.getBusType().equals(BusType.JGYD.key)) {
                    throw new MessageException("直签代理商类型不允许修改为机构或标准一代！");
                }
            } else if (newBusInfoVo.getBusType().equals(BusType.YDX.key)) {
                if (preBusInfoVo.getBusType().equals(BusType.JGYD.key)) {
                    if (stringList.size()>0 && stringList!=null) {
                        for (String strType : stringList) {
                            if (strType.equals(BusType.YDX.key)) {
                                throw new MessageException("下级中有一代X类型，不能更改！");
                            }
                        }
                    }
                } else if (preBusInfoVo.getBusType().equals(BusType.ZQZF.key)) {
                    if (StringUtils.isNotBlank(busInfoParent.getBusType())) {
                        if (!busInfoParent.getBusType().equals(BusType.JG.key) && !busInfoParent.getBusType().equals(BusType.JGYD.key)
                                && !busInfoParent.getBusType().equals(BusType.BZYD.key)) {
                            String busTypeByValue = BusType.getContentByValue(busInfoParent.getBusType());
                            throw new MessageException("上级代理商类型为["+busTypeByValue+"]，不允许修改！");
                        }
                    }
                }
            } else if (newBusInfoVo.getBusType().equals(BusType.ZQZF.key)) {
                if (preBusInfoVo.getBusType().equals(BusType.JGYD.key) || preBusInfoVo.getBusType().equals(BusType.YDX.key)) {
                    if (stringList.size()>0 && stringList!=null) {
                        throw new MessageException("有下级不能修改！");
                    }
                }
            } else if (newBusInfoVo.getBusType().equals(BusType.JGYD.key)) {
                if (preBusInfoVo.getBusType().equals(BusType.ZQZF.key) || preBusInfoVo.getBusType().equals(BusType.YDX.key)) {
                    if (StringUtils.isNotBlank(busInfoParent.getBusType())) {
                        if (!busInfoParent.getBusType().equals(BusType.JG.key)) {
                            throw new MessageException("修改为机构一代上级必须是机构！");
                        }
                    }
                }
            }
        }
    }

    /**
     *【业务平台编号】【平台登陆账号】是否为空校验，入网及新增业务平台限制
     * @param agentVo
     * @throws Exception
     */
    @Override
    public void verifyAgentBusinfo(AgentVo agentVo) throws Exception {
        if (agentVo.getBusInfoVoList() != null) {
            for (AgentBusInfo item : agentVo.getBusInfoVoList()) {
                PlatformType platformType = platFormService.byPlatformCode(item.getBusPlatform());
                if (platformType != null) {
                    //智慧POS、智慧plus、瑞+（条码前置）、实时POS品牌
                    if (platformType.code.equals(PlatformType.ZHPOS.code) || platformType.code.equals(PlatformType.RJQZ.code) || platformType.code.equals(PlatformType.SSPOS.code)) {
                        if (StringUtils.isNotBlank(item.getBusNum()) || StringUtils.isNotBlank(item.getBusLoginNum())) {
                            logger.info("日结平台不允许升级");
                            throw new MessageException("日结平台不允许升级");
                        }
                    }
                    //POS平台、瑞+、智能POS
                    if (platformType.code.equals(PlatformType.POS.code) || platformType.code.equals(PlatformType.RJPOS.code) || platformType.code.equals(PlatformType.ZPOS.code)) {
                        if (StringUtils.isBlank(item.getBusNum()) && StringUtils.isNotBlank(item.getBusLoginNum())
                                || StringUtils.isNotBlank(item.getBusNum()) && StringUtils.isBlank(item.getBusLoginNum())) {
                            logger.info("[业务平台编号][平台登录账号]，平台升级，则需要同时输入；平台新签，则均不允许填写");
                            throw new MessageException("[业务平台编号][平台登录账号]，平台升级，则需要同时输入；平台新签，则均不允许填写");
                        }
                    }
                }
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public void updateBusinfoData(List<AgentBusInfoVo> busInfoVoList) throws Exception {
        if (busInfoVoList==null) {
            throw new MessageException("信息错误");
        }
        if (busInfoVoList.size()==0) {
            throw new MessageException("信息错误");
        }
        try{
            AgentBusInfo agentBusInfo = null;
            for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
                agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
                if (StringUtils.isBlank(agentBusInfoVo.getBusParent())) {
                    agentBusInfoVo.setBusParent(agentBusInfo.getBusParent());
                }
                if (StringUtils.isBlank(agentBusInfoVo.getBusType())) {
                    agentBusInfoVo.setBusType(agentBusInfo.getBusType());
                }
                //校验代理商类型更改规则
                verifyBusinfoType(agentBusInfo, agentBusInfoVo);
                //校验业务编码是否存在
                if (StringUtils.isNotBlank(agentBusInfoVo.getBusNum())) {
                    AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                    agentBusInfoExample.createCriteria()
                            .andStatusEqualTo(Status.STATUS_1.status)
                            .andBusNumEqualTo(agentBusInfoVo.getBusNum())
                            .andIdNotEqualTo(agentBusInfo.getId());
                    List<AgentBusInfo> agentBusInfoList = agentBusInfoMapper.selectByExample(agentBusInfoExample);
                    if (agentBusInfoList.size() > 0) {
                        throw new MessageException("业务平台编码已存在！");
                    }
                }
                //校验智慧POS登录账号是否存在
                if (StringUtils.isNotBlank(agentBusInfoVo.getPosPlatCode())) {
                    AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                    agentBusInfoExample.createCriteria()
                            .andStatusEqualTo(Status.STATUS_1.status)
                            .andPosPlatCodeEqualTo(agentBusInfoVo.getPosPlatCode())
                            .andIdNotEqualTo(agentBusInfo.getId());
                    List<AgentBusInfo> agentBusInfoList = agentBusInfoMapper.selectByExample(agentBusInfoExample);
                    if (agentBusInfoList.size() > 0) {
                        throw new MessageException("智慧POS登录账号已存在！");
                    }
                }
                //检查业务平台数据
                PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfoVo.getBusPlatform());
                if(platForm==null){
                    throw new MessageException("业务平台不存在");
                }
                PlatformType platformType = platFormService.byPlatformCode(agentBusInfoVo.getBusPlatform());
                if(PlatformType.RDBPOS.code.equals(platformType.getValue())){
                    //检查手机号是否填写
                    if(StringUtils.isBlank(agentBusInfoVo.getBusLoginNum())){
                        throw new MessageException("瑞大宝平台登录账号不能为空");
                    }
                    if(!RegexUtil.checkInt(agentBusInfoVo.getBusLoginNum())){
                        throw new MessageException("瑞大宝平台登录账号必须为数字");
                    }
                    if(agentBusInfoVo.getBusLoginNum().length()!=11){
                        throw new MessageException("手机位数不正确");
                    }
                }
                if(PlatformType.RHPOS.code.equals(platformType.getValue())){
                    //检查手机号是否填写
                    if(StringUtils.isBlank(agentBusInfoVo.getBusLoginNum())){
                        throw new MessageException("瑞花宝平台登录账号不能为空");
                    }
                    if(!RegexUtil.checkInt(agentBusInfoVo.getBusLoginNum())){
                        throw new MessageException("瑞花宝平台登录账号必须是数字");
                    }
                    if(agentBusInfoVo.getBusLoginNum().length()!=11){
                        throw new MessageException("手机位数不正确");
                    }
                }
                if(StringUtils.isNotBlank(agentBusInfoVo.getBusType())){
                    if(agentBusInfoVo.getBusType().equals(BusType.JG.key) || agentBusInfoVo.getBusType().equals(BusType.BZYD.key)){
                        Map<String, String> hashMap = new HashMap<>();
                        hashMap.put("agentId",agentBusInfoVo.getAgentId());
                        hashMap.put("busType",BusType.JG.key);
                        hashMap.put("busTypeOne",BusType.BZYD.key);
                        List<AgentBusInfo> busInfosList=agentBusInfoMapper.queryBusinfo(hashMap);
                        if(null!=busInfosList && busInfosList.size()>0){
                            for (AgentBusInfo busInfo : busInfosList) {
                                busInfo.setAgDocDistrict(agentBusInfoVo.getAgDocDistrict());
                                busInfo.setAgDocPro(agentBusInfoVo.getAgDocPro());
                                busInfo.setVersion(busInfo.getVersion());
                                if(1!=agentBusInfoMapper.updateByPrimaryKey(busInfo)){
                                    logger.info("业务修改大区省区更新失败");
                                    throw new MessageException("业务修改大区省区更新失败");
                                }
                            }
                        }
                    }
                }
                //更新值
                agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
                agentBusInfo.setBusType(agentBusInfoVo.getBusType());
                agentBusInfo.setAgDocDistrict(agentBusInfoVo.getAgDocDistrict());
                agentBusInfo.setAgDocPro(agentBusInfoVo.getAgDocPro());
                agentBusInfo.setBusContact(agentBusInfoVo.getBusContact());
                agentBusInfo.setBusContactMobile(agentBusInfoVo.getBusContactMobile());
//                agentBusInfo.setBusContactEmail(agentBusInfoVo.getBusContactEmail());
                agentBusInfo.setBusContactPerson(agentBusInfoVo.getBusContactPerson());
                agentBusInfo.setBusNum(agentBusInfoVo.getBusNum());
                agentBusInfo.setBusLoginNum(agentBusInfoVo.getBusLoginNum());
                agentBusInfo.setBusStatus(agentBusInfoVo.getBusStatus());
                agentBusInfo.setBusParent(agentBusInfoVo.getBusParent());
                if(StringUtils.isNotBlank(agentBusInfoVo.getOrganNum()))
                 agentBusInfo.setOrganNum(agentBusInfoVo.getOrganNum());
                agentBusInfo.setVersion(agentBusInfo.getVersion());
                agentBusInfo.setBusUseOrgan(agentBusInfoVo.getBusUseOrgan());
                agentBusInfo.setBusScope(agentBusInfoVo.getBusScope());
                agentBusInfo.setPosPlatCode(agentBusInfoVo.getPosPlatCode());
                int updateAgentBusinfo = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
                if (updateAgentBusinfo != 1) {
                    logger.info("业务数据-更新失败");
                    throw new MessageException("更新失败");
                } else {
                    if (StringUtils.isNotBlank(agentBusInfoVo.getBusNum())) {
                        Agent agent = agentMapper.selectByPrimaryKey(agentBusInfo.getAgentId());
                        agent.setcIncomStatus(AgentInStatus.IN.status);
                        agent.setcUtime(new Date());
                        int updateAgent = agentMapper.updateByPrimaryKeySelective(agent);
                        if (updateAgent != 1) {
                            logger.info("代理商数据-更新失败");
                            throw new MessageException("更新失败");
                        }
                    }
                }
            }
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<PlatForm> queryAblePlatForm() {
        PlatFormExample example = new PlatFormExample();
        example.or().andStatusEqualTo(Status.STATUS_1.status).andPlatformStatusEqualTo(Status.STATUS_1.status);
        example.setOrderByClause(" platform_type desc,c_time asc");
        return platFormMapper.selectByExample(example);
    }

    /**
     * 平台业务保存
     *
     * @param agentVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult saveBusinessPlatform(AgentVo agentVo) throws ProcessException {
        try {
            Map<String, Object> reqMap = new HashMap<>();
            Agent agent = agentVo.getAgent();
            agent.setId(agentVo.getAgentId());
            //新增业务必须保证基础信息完整 检查基础信息是否完整
            FastMap checkAgentInfoIsComplet = agentQueryService.checkAgentInfoIsComplet(agent.getId());
            if(!FastMap.isSuc(checkAgentInfoIsComplet)){
                throw new ProcessException(checkAgentInfoIsComplet.get("msg")+"");
            }
            //先查询业务是否已添加 有个添加过 全部返回
            for (AgentBusInfoVo item : agentVo.getBusInfoVoList()) {
                //校验实时分润不能升级
                List platformList = platFormMapper.selectPlatformNumByPlatformType();
                boolean checkBusPlatform = platformList.contains(item.getBusPlatform()) && (null != item.getBusNum() && !"".equals(item.getBusNum()));
                if (checkBusPlatform) throw new ProcessException("实时分润品牌暂不支持升级！");

                if (StringUtils.isBlank(item.getBusPlatform())) {
                    throw new ProcessException("业务平台不能为空");
                }
                if(StringUtils.isNotBlank(item.getBusNum())) {
                    if (!OrgType.zQ(item.getBusType())) {
                        throw new ProcessException("升级类型必须是直签");
                    }
                    if (StringUtils.isBlank(item.getBusParent())){
                        throw new ProcessException("升级直签上级不能为空");
                    }

                    reqMap.put("busInfo",item);
                    reqMap.put("agentVo",agentVo);
                    AgentResult agentResult = agentNetInNotityService.agencyLevelCheck(reqMap);
                    if(!agentResult.isOK()){
                        throw new ProcessException(agentResult.getMsg());
                    }
                }
                if(StringUtils.isBlank(item.getBusScope())){
                    throw new ProcessException("业务范围不能为空");
                }
                if(StringUtils.isBlank(item.getBusUseOrgan())){
                    throw new ProcessException("使用范围不能为空");
                }
                if(OrgType.zQ(item.getBusType())){
                    if(StringUtils.isBlank(item.getBusParent()))
                        throw new ProcessException("直签上级不能为空");
                }
                //代理商选择上级代理商时添加限制 不能选择同级别代理商为上级
                if (StringUtils.isNotBlank(item.getBusParent())) {
                    //获取上级代理商类型
                    AgentBusInfo busInfo = agentBusinfoService.getById(item.getBusParent());
                    if (item.getBusType().equals(BusType.ZQ.key) || item.getBusType().equals(BusType.ZQBZF.key) || item.getBusType().equals(BusType.ZQZF.key)) {
                        if (busInfo.getBusType().equals(BusType.ZQ.key) || busInfo.getBusType().equals(BusType.ZQZF.key) || busInfo.getBusType().equals(BusType.ZQBZF.key)) {
                            throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                        }
                    }
                    if (item.getBusType().equals(BusType.YDX.key)) {
                        if (busInfo.getBusType().equals(BusType.ZQ.key) || busInfo.getBusType().equals(BusType.YDX.key)
                                || busInfo.getBusType().equals(BusType.ZQZF.key) || busInfo.getBusType().equals(BusType.ZQBZF.key)) {
                            throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                        }
                    }
                    if (item.getBusType().equals(BusType.JGYD.key)) {
                        if (!busInfo.getBusType().equals(BusType.JG.key)) {
                            throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                        }
                    }
                }
                item.setAgentId(agent.getId());
                Boolean busPlatExist = findBusPlatExist(item);
                if (busPlatExist) {
                    return new AgentResult(500, "业务已添加,请勿重复添加", "");
                }
                //瑞大包校验
                PlatformType platformType = platFormService.byPlatformCode(item.getBusPlatform());
                if(PlatformType.RDBPOS.code.equals(platformType.getValue())){
                    //检查手机号是否填写
                    if(StringUtils.isBlank(item.getBusLoginNum())){
                        throw new ProcessException("瑞大宝平台登录账号不能为空");
                    }
                    Boolean exist = selectByBusLoginNumExist(item.getBusLoginNum(), agent.getId());
                    if(!exist){
                        throw new ProcessException("瑞大宝平台登录账号已入网,请勿重复入网");
                    }
                    if(!RegexUtil.checkInt(item.getBusLoginNum())){
                        throw new ProcessException("瑞大宝平台登录账号必须为数字");
                    }
                    if(item.getBusLoginNum().length()!=11){
                        throw new ProcessException("手机位数不正确");
                    }
                }
                if(PlatformType.RHPOS.code.equals(platformType.getValue())){
                    //检查手机号是否填写
                    if(StringUtils.isBlank(item.getBusLoginNum())){
                        throw new ProcessException("瑞花宝平台登录账号不能为空");
                    }
                    if(!RegexUtil.checkInt(item.getBusLoginNum())){
                        throw new ProcessException("瑞花宝平台登录账号必须是数字");
                    }
                    if(item.getBusLoginNum().length()!=11){
                        throw new ProcessException("手机位数不正确");
                    }
                }
                AgentBusInfo agentBusInfo = agentBusinfoService.agentPlatformNum(agentVo.getAgentId(),item.getBusPlatform());
                if(null!=agentBusInfo && StringUtils.isNotBlank(agentBusInfo.getBusType())){
                    if(!agentBusInfo.getBusType().equals( item.getBusType())){
                        throw new ProcessException("此业务的类型与月结相同品牌不一致");
                    }
                }
                if(null!=agentBusInfo && StringUtils.isNotBlank(agentBusInfo.getBusRegion())){
                    if(!agentBusInfo.getBusRegion().equals( item.getBusRegion())){
                        throw new ProcessException("此业务的业务区域与月结相同品牌不一致");
                    }
                }
                if(null!=agentBusInfo && StringUtils.isNotBlank(agentBusInfo.getBusScope())){
                    if(!agentBusInfo.getBusScope().equals( item.getBusScope())){
                        throw new ProcessException("此业务的业务范围与月结相同品牌不一致");
                    }
                }

                if(null!=agentBusInfo && StringUtils.isNotBlank(agentBusInfo.getAgDocDistrict())){
                    if(!agentBusInfo.getAgDocDistrict().equals( item.getAgDocDistrict())){
                        throw new ProcessException("此业务的大区与月结相同品牌不一致");
                    }
                }
                if(null!=agentBusInfo && StringUtils.isNotBlank(agentBusInfo.getAgDocPro())){
                    if(!agentBusInfo.getAgDocPro().equals( item.getAgDocPro())){
                        throw new ProcessException("此业务的省区与月结相同品牌不一致");
                    }
                }
                //判断标准一代、机构只能有一个业务对接省区
                if(StringUtils.isNotBlank(item.getBusType()) && StringUtils.isNotBlank(item.getAgentId()) && StringUtils.isNotBlank(item.getAgDocPro()) &&StringUtils.isNotBlank(item.getAgDocDistrict())){

                    if(item.getBusType().equals(BusType.JG.key) ||item.getBusType().equals(BusType.BZYD.key)){
                        Map<String, String> hashMap = new HashMap<>();
                        hashMap.put("agentId",item.getAgentId());
                        hashMap.put("busType",BusType.JG.key);
                        hashMap.put("busTypeOne",BusType.BZYD.key);
                        List<AgentBusInfo> busInfoList = agentBusInfoMapper.queryBusinfo(hashMap);
                        Set<String> hashSetDocDistrict = new HashSet<>();
                        Set<String> hashSetocPro = new HashSet<>();
                        if(null!=busInfoList && busInfoList.size()>0){
                            for (AgentBusInfo busInfo : busInfoList) {
                                hashSetocPro.add(busInfo.getAgDocPro());
                                hashSetDocDistrict.add(busInfo.getAgDocDistrict());
                            }
                           if(hashSetDocDistrict.size()==1 || hashSetocPro.size()==1){
                                //如果只有一个则进行更改大区省区
                                AgentBusInfo agent_businfo = busInfoList.get(0);
                                if(StringUtils.isNotBlank(agent_businfo.getAgDocPro()) && StringUtils.isNotBlank(agent_businfo.getAgDocDistrict())){
                                    if(!agent_businfo.getAgDocPro().equals(item.getAgDocPro()) || !agent_businfo.getAgDocDistrict().equals(item.getAgDocDistrict())){
                                        logger.info("机构/标准一代的省区只能为相同省区");
                                        throw new ProcessException("机构/标准一代的省区只能为相同省区");
                                    }
                                }
                            }else if(hashSetDocDistrict.size()>1 || hashSetocPro.size()>1){
                                logger.info("请联系市场部统一更改后再开通业务或修改业务");
                                throw new ProcessException("请联系市场部统一更改后再开通业务或修改业务");
                            }
                         }
                    }
                }else{
                    if(StringUtils.isBlank(item.getAgDocDistrict()) || StringUtils.isBlank(item.getAgDocPro())){
                        logger.info("请填写大区或者省区");
                        throw new ProcessException("请填写大区或者省区");
                    }
                }
            }
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByAgenId(agent.getId());
            String json = JsonUtil.objectToJson(agentBusInfos);
            List<AgentBusInfoVo> agentBusInfoVos = JsonUtil.jsonToList(json, AgentBusInfoVo.class);
            agentEnterService.verifyOrgAndBZYD(agentBusInfoVos, agentVo.getBusInfoVoList());

            for (AgentContractVo item : agentVo.getContractVoList()) {
                if (StringUtils.isNotBlank(agent.getcUser()) && StringUtils.isNotBlank(agent.getId())) {
                    item.setcUser(agent.getcUser());
                    item.setAgentId(agent.getId());
                    AgentContract agentContract = agentContractService.insertAgentContract(item, item.getContractTableFile(), agent.getcUser(),null);
                    //添加分管协议
                    if (StringUtils.isNotBlank(item.getAgentAssProtocol())) {
                        AssProtoColRel rel = new AssProtoColRel();
                        rel.setAgentBusinfoId(agentContract.getId());
                        rel.setAssProtocolId(item.getAgentAssProtocol());
                        AssProtoCol assProtoCol = assProtoColMapper.selectByPrimaryKey(item.getAgentAssProtocol());
                        if(org.apache.commons.lang.StringUtils.isNotBlank(item.getProtocolRuleValue())){
                            String ruleReplace = assProtoCol.getProtocolRule().replace("{}", item.getProtocolRuleValue());
                            rel.setProtocolRule(ruleReplace);
                        }else{
                            rel.setProtocolRule(assProtoCol.getProtocolRule());
                        }
                        rel.setProtocolRuleValue(item.getProtocolRuleValue());
                        if (1 != agentAssProtocolService.addProtocolRel(rel, agent.getcUser())) {
                            throw new ProcessException("业务分管协议添加失败");
                        }
                    }
                }
            }
            for (CapitalVo item : agentVo.getCapitalVoList()) {
                if(agentVo.getCapitalVoList().size()!=0){
                    if(StringUtils.isBlank(item.getcPayType())){
                        throw new ProcessException("请选择打款方式");
                    }
                }
                if(item.getcPayType().equals(PayType.YHHK.getValue())){
                    if(StringUtils.isBlank(item.getcPayuser())){
                        throw new ProcessException("请选择打款人");
                    }
                    if(item.getcPaytime()==null){
                        throw new ProcessException("请选择打款时间");
                    }
                    if(item.getCapitalTableFile()==null){
                        throw new ProcessException("银行汇款方式必须上传打款凭据");
                    }
                    if(item.getCapitalTableFile().size()==0){
                        throw new ProcessException("银行汇款方式必须上传打款凭据");
                    }
                }
                if (StringUtils.isNotBlank(agent.getcUser()) && StringUtils.isNotBlank(agent.getId())) {
                    item.setcAgentId(agent.getId());
                    item.setcUser(agent.getcUser());
                    AgentResult result = accountPaidItemService.insertAccountPaid(item, item.getCapitalTableFile(), agentVo.getAgent().getcUser(),false,null);
                    if (!result.isOK()) {
                        throw new ProcessException("缴纳款项信息录入失败");
                    }
                }
            }
            if (null != agentVo.getColinfoVoList()) {
                for (AgentColinfoVo item : agentVo.getColinfoVoList()) {
                    item.setAgentId(agent.getId());
                    item.setcUser(agent.getcUser());
                    agentColinfoService.agentColinfoInsert(item, item.getColinfoTableFile(),null);
                }
            }
            List<AgentBusInfo> agentBusInfoList = new ArrayList<>();
            for (AgentBusInfoVo item : agentVo.getBusInfoVoList()) {
                item.setcUser(agent.getcUser());
                item.setAgentId(agent.getId());
                item.setCloReviewStatus(AgStatus.Create.status);
                AgentBusInfo agentBusInfo = agentBusinfoService.agentBusInfoInsert(item);
                agentBusInfoList.add(agentBusInfo);
            }
            return AgentResult.ok(agentBusInfoList);
        } catch (MessageException e) {
            e.printStackTrace();
            throw new ProcessException(e.getMsg());
        }catch (ProcessException e) {
            e.printStackTrace();
            throw new ProcessException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProcessException(e.getLocalizedMessage());
        }
    }

    /**
     * 查询代理上次业务是否添加过
     *
     * @param agentBusInfo
     * @return
     */
    private Boolean findBusPlatExist(AgentBusInfo agentBusInfo) {
        AgentBusInfoExample example = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria criteria = example.createCriteria();
        criteria.andAgentIdEqualTo(agentBusInfo.getAgentId());
        criteria.andBusPlatformEqualTo(agentBusInfo.getBusPlatform());
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andCloReviewStatusIn(Arrays.asList(AgStatus.Approved.status,AgStatus.Approving.status));
        criteria.andBusStatusIn(Arrays.asList(BusStatus.WQY.status,BusStatus.QY.status,BusStatus.WJH.status,BusStatus.SD.status));
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(example);
        if (null == agentBusInfos) {
            return true;
        }
        if (agentBusInfos.size() == 0) {
            return false;
        }
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public List<String> addList(List<List<Object>> list, String userid) throws Exception {
        List<String> busList = new ArrayList<>();
        if (null == list && list.size() == 0) {
            logger.info("导入的数据为空");
            throw new MessageException("导入的数据为空");
        }
        for (List<Object> objectList : list) {
            if (StringUtils.isBlank(String.valueOf(objectList.get(0)))) {
                logger.info("代理商ID为空:{}", String.valueOf(objectList.get(0)));
                throw new MessageException("请填写编号");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(1)))) {
                logger.info("业务平台编号为空:{}", String.valueOf(objectList.get(1)));
                throw new MessageException("请填写业务平台编号");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(2)))) {
                logger.info("打款公司为空:{}", String.valueOf(objectList.get(2)));
                throw new MessageException("请填写打款公司");
            }
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
            criteria.andAgentIdEqualTo(String.valueOf(objectList.get(0)));
            criteria.andBusNumEqualTo(String.valueOf(objectList.get(1)));
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            if (1 != agentBusInfos.size()) {
                logger.info("不匹配,请检查数据是否正确");
                throw new MessageException("不匹配,请检查数据是否正确");
            }
            //到这说明已经存在这条数据
            PayCompExample payCompExample = new PayCompExample();
            PayCompExample.Criteria criteria1 = payCompExample.or().andComNameEqualTo(String.valueOf(objectList.get(2)));
            List<PayComp> payComps = payCompMapper.selectByExample(payCompExample);
            if (null == payComps && payComps.size() == 0) {
                logger.info("没有此公司");
                throw new MessageException("没有此公司");
            }
            AgentBusInfo agentBus = agentBusInfos.get(0);
            agentBus.setCloPayCompany(payComps.get(0).getId());
            if (1 != agentBusInfoMapper.updateByPrimaryKeySelective(agentBus)) {
                logger.info("更新失败");
                throw new MessageException("更新失败");
            }

            try {
                ImportAgent importAgent = new ImportAgent();
                importAgent.setDataid(agentBus.getId());
                importAgent.setDatatype(AgImportType.DATACHANGEAPP.name());
                importAgent.setBatchcode(Calendar.getInstance().getTime().toString());
                importAgent.setcUser(userid);
                if (1 != aimportService.insertAgentImportData(importAgent)) {
                    logger.info("代理商审批通过-添加开户任务失败");
                } else {
                    logger.info("代理商审批通过-添加开户任务成功!{},{}", AgImportType.BUSAPP.getValue(), agentBus.getId());
                }

                agentDataHistoryService.saveDataHistory(agentBus, DataHistoryType.BUSINESS.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                agentNotifyService.asynNotifyPlatform();
            }
            busList.add(agentBus.getId());
        }

        return busList;
    }

    @Override
    public int updateBusPlatDkgsBySelective(AgentBusInfo agentBusInfo,String userId) {
        if (StringUtils.isBlank(agentBusInfo.getId())) {
            return 0;
        }
        AgentBusInfo agbus = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getId());
        agentBusInfo.setVersion(agbus.getVersion());
        int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
        try {
            ImportAgent importAgent = new ImportAgent();
            importAgent.setDataid(agbus.getId());
            importAgent.setDatatype(AgImportType.DATACHANGEAPP.name());
            importAgent.setBatchcode(Calendar.getInstance().getTime().toString());
            importAgent.setcUser(userId);
            if (1 != aimportService.insertAgentImportData(importAgent)) {
                logger.info("代理商审批通过-添加开户任务失败");
            } else {
                logger.info("代理商审批通过-添加开户任务成功!{},{}", AgImportType.BUSAPP.getValue(), agbus.getId());
            }

            agentDataHistoryService.saveDataHistory(agbus, DataHistoryType.BUSINESS.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            agentNotifyService.asynNotifyPlatform();
        }
        return i;
    }

    @Override
    public int updateCompany(AgentBusInfo agentBusInfo, String userId) throws MessageException {
        int i=0;
        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andAgentIdEqualTo(agentBusInfo.getAgentId());
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
        if (null!=agentBusInfos && agentBusInfos.size()>0){
            for (AgentBusInfo busInfo : agentBusInfos) {
                AgentBusInfo agBusInfo = new AgentBusInfo();
                agBusInfo.setId(busInfo.getId());
                agBusInfo.setcUtime(Calendar.getInstance().getTime());
                agBusInfo.setVersion(busInfo.getVersion());
                agBusInfo.setCloPayCompany(agentBusInfo.getCloPayCompany());
                 i=agentBusInfoMapper.updateByPrimaryKeySelective(agBusInfo);
                agentDataHistoryService.saveDataHistory(busInfo, DataHistoryType.BUSINESS.getValue());
            }
        }
        return i;
    }

    @Override
    public List<BusinessOutVo> exportAgent(Map map, Long userId) throws ParseException {
        List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
        map.put("platfromPerm",platfromPerm);
        map.put("status", Status.STATUS_1.status);
        map.put("agStatus", AgStatus.Approved.name());

        if (!StringUtils.isBlank((String)map.get("busPlatformList"))) {
            map.put("busPlatformList", Arrays.asList(((String) map.get("busPlatformList")).split(",")));
        }else{
            map.put("busPlatformList",new ArrayList<BigDecimal>());
        }

        List<BigDecimal> voList= new ArrayList<BigDecimal>();
        if (!StringUtils.isBlank((String)map.get("cloReviewStatusList"))) {// bigdecimal 处理
            List<String> list = Arrays.asList( ((String)map.get("cloReviewStatusList")).split(","));
            voList = list.stream().map(str -> new BigDecimal(str.trim())).collect(Collectors.toList());
            if( voList!=null && voList.size()>0)
                map.put("cloReviewStatusList", voList);
        }else{
            map.put("cloReviewStatusList", new ArrayList<BigDecimal>());
        }

        if (StringUtils.isNotBlank((String)map.get("busTypeList"))) {
            map.put("busTypeList", Arrays.asList(((String)map.get("busTypeList")).split(",")));
        }else{
            map.put("busTypeList", new ArrayList<BigDecimal>());
        }
        List<BusinessOutVo> agentoutVos = agentBusInfoMapper.excelAgent(map);
        List<Dict> BUS_TYPE = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name());
        List<Dict> BUS_SCOPE = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_SCOPE.name());
        List<Dict> BUS_STATUS = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_STATUS.name());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (null != agentoutVos && agentoutVos.size() > 0)
            for (BusinessOutVo agentoutVo : agentoutVos) {//类型
                if(null!=agentoutVo.getApproveTime()){
                    agentoutVo.setTime(df.format(agentoutVo.getApproveTime()));
                }
                if (StringUtils.isNotBlank(agentoutVo.getBusType()) && !agentoutVo.getBusType().equals("null")) {
                    for (Dict dict : BUS_TYPE) {
                        if (null!=dict  &&  agentoutVo.getBusType().equals(dict.getdItemvalue())){
                            agentoutVo.setBusType(dict.getdItemname());
                            break;
                        }
                    }
                }
                if (StringUtils.isNotBlank(agentoutVo.getBusIndeAss()) && !agentoutVo.getBusIndeAss().equals("null")) {
                    if (agentoutVo.getBusIndeAss().equals("1")) {
                        agentoutVo.setBusIndeAss("是");
                    } else
                        agentoutVo.setBusIndeAss("否");
                }
                //业务区域
               if (StringUtils.isNotBlank(agentoutVo.getBusRegion()) && !agentoutVo.getBusRegion().equals("null")){
                    String busRegion = agentoutVo.getBusRegion();
                    if (StringUtils.isNotBlank(busRegion) &&!busRegion.equals("null")){
                        String[] arr = busRegion.split(",");
                        String name = agentQueryService.dPosRegionNameFromDposIds(arr);
                        if (StringUtils.isNotBlank(name) && !name.equals("null"))
                            agentoutVo.setBusRegion(name);
                    }
                }
                //业务范围
                if (StringUtils.isNotBlank(agentoutVo.getBusScope()) && !agentoutVo.getBusScope().equals("null")) {
                    for (Dict dict : BUS_SCOPE) {
                        if (null!=dict  &&  agentoutVo.getBusScope().equals(dict.getdItemvalue())){
                            agentoutVo.setBusScope(dict.getdItemname());
                            break;
                        }
                    }
                }
                //业务状态
                if (StringUtils.isNotBlank(agentoutVo.getBusStatus()) && !agentoutVo.getBusStatus().equals("null")) {
                    for (Dict dict : BUS_STATUS) {
                        if (null!=dict && agentoutVo.getBusStatus().equals(dict.getdItemvalue())){
                            agentoutVo.setBusStatus(dict.getdItemname());
                            break;
                        }
                    }
                }
            }
        return agentoutVos;
    }

    @Override
    public List<Map<String, Object>> queryByBusNum(String busNum){
        if(StringUtils.isBlank(busNum)){
            return null;
        }
        return agentBusInfoMapper.queryByBusNum(busNum);
    }

    /**
     * 查询代理商是否有标准一代的
     * @param agBusLic
     * @param busInfoVoList
     * @return
     */
    @Override
    public Map<String,Object> queryIsBZYD(String agBusLic,List<AgentBusInfoVo> busInfoVoList){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code","200");
        resultMap.put("msg","成功");
        AgentExample agentExample = new AgentExample();
        AgentExample.Criteria criteria = agentExample.createCriteria();
        criteria.andAgBusLicEqualTo(agBusLic);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<Agent> agents = agentMapper.selectByExample(agentExample);
        for (Agent agent : agents) {
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            AgentBusInfoExample.Criteria busCriteria = agentBusInfoExample.createCriteria();
            busCriteria.andAgentIdEqualTo(agent.getId());
            busCriteria.andStatusEqualTo(Status.STATUS_1.status);
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            for (AgentBusInfo agentBusInfo : agentBusInfos) {
                for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
                    if(agentBusInfo.getBusPlatform().equals(agentBusInfoVo.getBusPlatform())){
                        if(agentBusInfo.getBusType().equals(BusType.BZYD.key)){
                            resultMap.put("code","500");
                            resultMap.put("msg","唯一编号："+agentBusInfo.getAgentId()+",已有标准一代");
                            return resultMap;
                        }
                        if(StringUtils.isBlank(agentBusInfo.getBusParent()) && StringUtils.isBlank(agentBusInfo.getBusParent())){
                            continue;
                        }
                        if((StringUtils.isBlank(agentBusInfo.getBusParent()) && StringUtils.isNotBlank(agentBusInfoVo.getBusParent())) ||
                                (StringUtils.isNotBlank(agentBusInfo.getBusParent()) && StringUtils.isBlank(agentBusInfoVo.getBusParent())) ){
                            resultMap.put("code","500");
                            resultMap.put("msg","平台："+Platform.getContentByValue(agentBusInfo.getBusPlatform())+",上级不一致");
                            return resultMap;
                        }
                        if(!agentBusInfo.getBusParent().equals(agentBusInfoVo.getBusParent())){
                            resultMap.put("code","500");
                            AgentBusInfo agentBus = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
                            Agent agent1 = agentMapper.selectByPrimaryKey(agentBus.getAgentId());
                            resultMap.put("msg","平台："+Platform.getContentByValue(agentBusInfo.getBusPlatform())+",上级不一致,上级名称:"+agent1.getAgName());
                            return resultMap;
                        }
                    }
                }
            }
        }
        return resultMap;
    }

    @Override
    public List<Map<String,Object>> queryIsBZYDList(String agBusLic,List<AgentBusInfo> busInfoVoList){
        List<Map<String,Object>> resultList = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        busInfoVoList.forEach(row->{
            idList.add(row.getAgentId());
        });
        AgentExample agentExample = new AgentExample();
        AgentExample.Criteria criteria = agentExample.createCriteria();
        criteria.andAgBusLicEqualTo(agBusLic);
        criteria.andIdNotIn(idList);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<Agent> agents = agentMapper.selectByExample(agentExample);
        for (Agent agent : agents) {
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            AgentBusInfoExample.Criteria busCriteria = agentBusInfoExample.createCriteria();
            busCriteria.andAgentIdEqualTo(agent.getId());
            busCriteria.andStatusEqualTo(Status.STATUS_1.status);
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            for (AgentBusInfo agentBusInfo : agentBusInfos) {
                for (AgentBusInfo agentBusInfoVo : busInfoVoList) {
                    if(agentBusInfo.getBusPlatform().equals(agentBusInfoVo.getBusPlatform())){
                        if(agentBusInfo.getBusType().equals(BusType.BZYD.key)){
                            Map<String,Object> resultMap = new HashMap<>();
                            resultMap.put("msg","存在标准一代");
                            resultMap.put("agentId",agentBusInfo.getAgentId());
                            resultMap.put("agentName",agent.getAgName());
                            resultList.add(resultMap);
                        }
                        if(StringUtils.isBlank(agentBusInfo.getBusParent()) && StringUtils.isBlank(agentBusInfo.getBusParent())){
                             continue;
                        }
                        if((StringUtils.isBlank(agentBusInfo.getBusParent()) && StringUtils.isNotBlank(agentBusInfo.getBusParent())) ||
                            (StringUtils.isNotBlank(agentBusInfo.getBusParent()) && StringUtils.isBlank(agentBusInfo.getBusParent())) ){
                            Map<String,Object> resultMap = new HashMap<>();
                            resultMap.put("msg","上级不一致");
                            resultMap.put("agentId",agentBusInfo.getAgentId());
                            resultMap.put("agentName",agent.getAgName());
                            resultMap.put("busPlatform",Platform.getContentByValue(agentBusInfo.getBusPlatform()));
                        }else if(!agentBusInfo.getBusParent().equals(agentBusInfoVo.getBusParent())){
                            Map<String,Object> resultMap = new HashMap<>();
                            AgentBusInfo agentBus = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
                            Agent agent1 = agentMapper.selectByPrimaryKey(agentBus.getAgentId());
                            resultMap.put("msg","上级不一致");
                            resultMap.put("agentId",agentBusInfo.getAgentId());
                            resultMap.put("agentName",agent.getAgName());
                            resultMap.put("busPlatform",Platform.getContentByValue(agentBusInfo.getBusPlatform()));
                            resultMap.put("busParent",agent1.getAgName());
                            resultList.add(resultMap);
                        }
                    }
                }
            }
        }
        return resultList;
    }

    @Override
    public AgentResult selectByAgentApproved(String id) {
        AgentResult result = new AgentResult(500,"参数错误","");
        if (StringUtils.isBlank(id)) {
            return result;
        }
        AgentExample example = new AgentExample();
        AgentExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andAgStatusEqualTo(AgStatus.Approved.name());
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<Agent> agents = agentMapper.selectByExample(example);
        if (null==agents) {
            result.setMsg("代理商查询失败");
            return result;
        }
        if(agents.size()==1){
            return AgentResult.ok(agents.get(0));
        }
        return result;
    }

    @Override
    public List<AgentBusInfo> selectByAgentId(String agentId) {
        if (StringUtils.isBlank(agentId)) {
            return null;
        } else {
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            AgentBusInfoExample.Criteria criteria =  agentBusInfoExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andAgentIdEqualTo(agentId);
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            for (AgentBusInfo agentBusInfo : agentBusInfos) {
                if(StringUtils.isNotBlank(agentBusInfo.getAgDocPro())){
                    COrganization cOrganization = organizationMapper.selectByPrimaryKey(Integer.parseInt(agentBusInfo.getAgDocPro()));
                    if(cOrganization!=null)
                    agentBusInfo.setAgDocPro(cOrganization.getName());
                }
                if(StringUtils.isNotBlank(agentBusInfo.getAgDocDistrict())){
                    COrganization cOrganization = organizationMapper.selectByPrimaryKey(Integer.parseInt(agentBusInfo.getAgDocDistrict()));
                    if(cOrganization!=null)
                    agentBusInfo.setAgDocDistrict(cOrganization.getName());
                }
                if(StringUtils.isNotBlank(agentBusInfo.getBusPlatform())){
                    PlatForm platForm = platFormService.selectByPlatformNum(agentBusInfo.getBusPlatform());
                    if(platForm!=null)
                    agentBusInfo.setBusPlatform(platForm.getPlatformName());
                }
            }
            return agentBusInfos;
        }
    }

    @Override
    public List<PlatForm> queryAblePlatFormPro() {
       String ryx_pro = AppConfig.getProperty("ryx_pro");
       String ryx_pro1 = AppConfig.getProperty("ryx_pro1");
        ArrayList<String> platList = new ArrayList<>();
        platList.add(ryx_pro);
        platList.add(ryx_pro1);
        PlatFormExample example = new PlatFormExample();
        example.or().andStatusEqualTo(Status.STATUS_1.status).andPlatformStatusEqualTo(Status.STATUS_1.status).andPlatformNumIn(platList);
        example.setOrderByClause(" platform_type desc,c_time asc");
        return platFormMapper.selectByExample(example);
    }

    @Override
    public List<AgentBusInfo> queryBusinfo(Map hashMap) {
        return agentBusInfoMapper.queryBusinfo(hashMap);
    }

    /**
     * 根据登陆账号查询是否存在，存在查询跟agentid是否相符
     * @param busLoginNum
     * @param agentId
     * @return
     */
    @Override
    public Boolean selectByBusLoginNumExist(String busLoginNum,String agentId){
        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andBusLoginNumEqualTo(busLoginNum);
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
        if(agentBusInfos!=null && agentBusInfos.size()>0){
            String sAgentId = agentBusInfos.get(0).getAgentId();
            if(!sAgentId.equals(agentId)){
                return false;
            }
        }
        return true;
    }

    /**
     * 顶级菜单客服服务-列表查询
     * @param page
     * @param map
     * @return
     */
    @Override
    public PageInfo queryBusinfoTopMenuList(Page page, Map map) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        reqMap.put("agStatus", AgStatus.Approved.name());
        if (!StringUtils.isBlank((String)map.get("busNum"))) {
            reqMap.put("busNum", (String)map.get("busNum"));
        }
        if (!StringUtils.isBlank((String)map.get("busPlatformList"))) {
            reqMap.put("busPlatformList", Arrays.asList(((String)map.get("busPlatformList")).split(",")));
        }
        if (!StringUtils.isBlank((String)map.get("agName"))) {
            reqMap.put("agName", (String)map.get("agName"));
        }
        if (!StringUtils.isBlank((String)map.get("agDocPro"))) {
            reqMap.put("agDocPro", (String)map.get("agDocPro"));
        }
        if (!StringUtils.isBlank((String)map.get("busContactPerson"))) {
            reqMap.put("busContactPerson", (String)map.get("busContactPerson"));
        }
        if (!StringUtils.isBlank((String)map.get("busStatus"))) {
            reqMap.put("busStatus", (String)map.get("busStatus"));
        }
        if (!StringUtils.isBlank((String)map.get("cloReviewStatusList"))) {
            List<String> list = Arrays.asList(((String)map.get("cloReviewStatusList")).split(","));
            List<BigDecimal> voList = list.stream().map(str -> new BigDecimal(str.trim())).collect(Collectors.toList());
            if(voList!=null && voList.size()>0)
                reqMap.put("cloReviewStatusList", voList);
        }
        List<Map> platfromPerm = iResourceService.userHasPlatfromPerm((Long)map.get("userId"));
        reqMap.put("platfromPerm", platfromPerm);
        List<Map<String, Object>> agentBusInfoList = agentBusInfoMapper.queryBusinfoTopMenuList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentBusInfoList);
        pageInfo.setTotal(agentBusInfoMapper.queryBusinfoTopMenuCount(reqMap));
        return pageInfo;
    }
}