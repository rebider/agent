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
import com.ryx.credit.pojo.admin.bank.DPosRegion;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.IResourceService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.bank.PosRegionService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.RegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public PageInfo queryBusinessPlatformListManager(AgentBusInfo agentBusInfo, Agent agent, Page page, Long userId,String approveTimeStart,String approveTimeEnd) {
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
        if ( StringUtils.isNotBlank(approveTimeStart)) {
            reqMap.put("approveTimeStart", approveTimeStart);
        }
        if ( StringUtils.isNotBlank(approveTimeEnd)) {
            reqMap.put("approveTimeEnd", approveTimeEnd);
        }
        reqMap.put("status", Status.STATUS_1.status);
        List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
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
            PlatForm platForm = platFormService.selectByPlatformNum(agentBusInfo.getBusPlatform());
            if(null!=platForm){
                agentBusInfo.setBusPlatformType(platForm.getPlatformType());
            }
            Map<String,Object> parentInfo = agentBusInfoMapper.queryBusInfoParent(FastMap.fastMap("id",agentBusInfo.getId()));
            agentBusInfo.setParentInfo(parentInfo);
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
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busInfoVoList.get(0).getId());
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByAgenId(agentBusInfo.getAgentId());
            for (AgentBusInfoVo item : busInfoVoList) {
                if (StringUtils.isBlank(item.getBusPlatform())){
                    logger.info("请选择业务平台");
                    throw new ProcessException("请选择业务平台");
                }
                if(item.getBusType().equals(BusType.ZQZF.key) || item.getBusType().equals(BusType.ZQBZF.key) || item.getBusType().equals(BusType.ZQ.key) ){
                    if(StringUtils.isBlank(item.getBusParent()))
                        throw new ProcessException("直签上级不能为空");
                }
                //代理商业务平台类型限制 当前代理商已有标准一代/机构类型的业务平台，不可再次选择直签类型业务平台
                if (StringUtils.isNotBlank(agentBusInfo.getAgentId())) {
                    List<AgentBusInfo> agentBusInfoList = agentBusinfoService.selectByAgenId(agentBusInfo.getAgentId());
                    for (AgentBusInfo busInfo : agentBusInfoList) {
                        if (item.getBusType().equals(BusType.BZYD.key) || item.getBusType().equals(BusType.JG.key)) {
                            if (!busInfo.getBusType().equals(BusType.BZYD.key) || !busInfo.getBusType().equals(BusType.JG.key)) {
                                throw new ProcessException("当前代理商已有标准一代/机构类型的业务平台，不可再次选择直签类型业务平台");
                            }
                        }
                    }
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
                        if (!busInfo.getBusType().equals(BusType.JG.key) || !busInfo.getBusType().equals(BusType.BZYD.key) || !busInfo.getBusType().equals(BusType.JGYD.key)) {
                            throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                        }
                    }
                    if (item.getBusType().equals(BusType.JGYD.key)) {
                        if (!busInfo.getBusType().equals(BusType.JG.key)) {
                            throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                        }
                    }
                }
                agentBusInfos.add(item);
            }
            String json = JsonUtil.objectToJson(agentBusInfos);
            List<AgentBusInfoVo> agentBusInfoVos = JsonUtil.jsonToList(json, AgentBusInfoVo.class);
            agentEnterService.verifyOrgAndBZYD(agentBusInfoVos);

            for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
                AgentBusInfo agbus = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
                Dict debitRateLower = dictOptionsService.findDictByName(DictGroup.AGENT.name(), agentBusInfoVo.getBusPlatform(), "debitRateLower");//借记费率下限（%）
                Dict debitCapping = dictOptionsService.findDictByName(DictGroup.AGENT.name(), agentBusInfoVo.getBusPlatform(), "debitCapping");//借记封顶额（元）
                Dict debitAppearRate = dictOptionsService.findDictByName(DictGroup.AGENT.name(), agentBusInfoVo.getBusPlatform(), "debitAppearRate");//借记出款费率（%）
                if(debitRateLower!=null){
                    agentBusInfoVo.setDebitRateLower(debitRateLower.getdItemvalue());
                }
                if(debitCapping!=null){
                    agentBusInfoVo.setDebitCapping(debitCapping.getdItemvalue());
                }
                if(debitAppearRate!=null){
                    agentBusInfoVo.setDebitAppearRate(debitAppearRate.getdItemvalue());
                }
                agentBusInfoVo.setVersion(agbus.getVersion());
                int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfoVo);
                if (i!=1) {
                    throw new MessageException("更新失败");
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
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public void updateBusinfoData(List<AgentBusInfoVo> busInfoVoList) throws Exception {
        if (busInfoVoList==null) {
            throw new MessageException("信息错误");
        }
        if (busInfoVoList.size()==0) {
            throw new MessageException("信息错误");
        }
        try{
            for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
                agentBusInfo.setBusType(agentBusInfoVo.getBusType());
                agentBusInfo.setAgDocDistrict(agentBusInfoVo.getAgDocDistrict());
                agentBusInfo.setAgDocPro(agentBusInfoVo.getAgDocPro());
                agentBusInfo.setBusContact(agentBusInfoVo.getBusContact());
                agentBusInfo.setBusContactMobile(agentBusInfoVo.getBusContactMobile());
                agentBusInfo.setBusContactEmail(agentBusInfoVo.getBusContactEmail());
                agentBusInfo.setBusContactPerson(agentBusInfoVo.getBusContactPerson());
                agentBusInfo.setBusLoginNum(agentBusInfoVo.getBusLoginNum());
                agentBusInfo.setBusStatus(agentBusInfoVo.getBusStatus());
                if(StringUtils.isNotBlank(agentBusInfoVo.getOrganNum()))
                 agentBusInfo.setOrganNum(agentBusInfoVo.getOrganNum());
                agentBusInfo.setVersion(agentBusInfo.getVersion());
                int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
                if (i != 1) {
                    throw new MessageException("更新失败");
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
            Agent agent = agentVo.getAgent();
            agent.setId(agentVo.getAgentId());
            //先查询业务是否已添加 有个添加过 全部返回
            for (AgentBusInfoVo item : agentVo.getBusInfoVoList()) {
                if(item.getBusType().equals(BusType.ZQZF.key) || item.getBusType().equals(BusType.ZQBZF.key) || item.getBusType().equals(BusType.ZQ.key) ){
                    if(StringUtils.isBlank(item.getBusParent()))
                        throw new ProcessException("直签上级不能为空");
                }
                //代理商业务平台类型限制 当前代理商已有标准一代/机构类型的业务平台，不可再次选择直签类型业务平台
                if (StringUtils.isNotBlank(agent.getId())) {
                    List<AgentBusInfo> agentBusInfoList = agentBusinfoService.selectByAgenId(agent.getId());
                    for (AgentBusInfo agentBusInfos : agentBusInfoList) {
                        if (agentBusInfos.getBusType().equals(BusType.BZYD.key) || agentBusInfos.getBusType().equals(BusType.JG.key)) {
                            if (!item.getBusType().equals(BusType.BZYD.key) || !item.getBusType().equals(BusType.JG.key)) {
                                throw new ProcessException("当前代理商已有标准一代/机构类型的业务平台，不可再次选择直签类型业务平台");
                            }
                        }
                    }
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
                        if (!busInfo.getBusType().equals(BusType.JG.key) || !busInfo.getBusType().equals(BusType.BZYD.key) || !busInfo.getBusType().equals(BusType.JGYD.key)) {
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
                        throw new ProcessException("瑞大宝登录账号不能为空");
                    }
                    Boolean exist = selectByBusLoginNumExist(item.getBusLoginNum(), agent.getId());
                    if(!exist){
                        throw new ProcessException("瑞大宝登录账号已入网,请勿重复入网");
                    }
                    if(!RegexUtil.checkInt(item.getBusLoginNum())){
                        throw new ProcessException("瑞大宝登录账号必须为数字");
                    }
                }
                if(PlatformType.RHPOS.code.equals(platformType.getValue())){
                    //检查手机号是否填写
                    if(StringUtils.isBlank(item.getBusLoginNum())){
                        throw new ProcessException("瑞花宝登录账号不能为空");
                    }
                    if(!RegexUtil.checkInt(item.getBusLoginNum())){
                        throw new ProcessException("瑞花宝登录账号必须是数字");
                    }
                }
            }
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByAgenId(agent.getId());
            for (AgentBusInfoVo item : agentVo.getBusInfoVoList()) {
                agentBusInfos.add(item);
            }
            String json = JsonUtil.objectToJson(agentBusInfos);
            List<AgentBusInfoVo> agentBusInfoVos = JsonUtil.jsonToList(json, AgentBusInfoVo.class);
            agentEnterService.verifyOrgAndBZYD(agentBusInfoVos);

            for (AgentContractVo item : agentVo.getContractVoList()) {
                if (StringUtils.isNotBlank(agent.getcUser()) && StringUtils.isNotBlank(agent.getId())) {
                    item.setcUser(agent.getcUser());
                    item.setAgentId(agent.getId());
                    AgentContract agentContract = agentContractService.insertAgentContract(item, item.getContractTableFile(), agent.getcUser());
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
                if(item.getcPayType().equals(PayType.YHHK.getValue())){
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
                    AgentResult result = accountPaidItemService.insertAccountPaid(item, item.getCapitalTableFile(), agentVo.getAgent().getcUser(),false);
                    if (!result.isOK()) {
                        throw new ProcessException("缴纳款项信息录入失败");
                    }
                }
            }
            if (null != agentVo.getColinfoVoList()) {
                for (AgentColinfoVo item : agentVo.getColinfoVoList()) {
                    item.setAgentId(agent.getId());
                    item.setcUser(agent.getcUser());
                    agentColinfoService.agentColinfoInsert(item, item.getColinfoTableFile());
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

        List<BusinessOutVo> agentoutVos = agentBusInfoMapper.excelAgent(map);
        List<Dict> BUS_TYPE = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name());
        List<Dict> BUS_SCOPE = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_SCOPE.name());
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

                if (StringUtils.isNotBlank(agentoutVo.getBusScope()) && !agentoutVo.getBusScope().equals("null")) {
                    for (Dict dict : BUS_SCOPE) {//业务范围
                        if (null!=dict  &&  agentoutVo.getBusScope().equals(dict.getdItemvalue())){
                            agentoutVo.setBusScope(dict.getdItemname());
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

    @Autowired
    private COrganizationMapper organizationMapper;

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
}